package com.example;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by heruijun on 2017/9/25.
 */
@AutoService(Processor.class)
public class FactoryProcesser extends AbstractProcessor {

    private Types mTypeUtil;
    private Elements mElementUtil;
    private Filer mFiler;
    private Messager mMessager;

    private FactoryCodeBuilder mFactoryCodeBuilder = new FactoryCodeBuilder();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mTypeUtil = processingEnvironment.getTypeUtils();
        mElementUtil = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            String supperClsPath = "";
            mFactoryCodeBuilder.clear();
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Factory.class)) {
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    error(annotatedElement,
                            String.format("Only class can be annotated with @%s",
                                    Factory.class.getSimpleName()));
                }

                TypeElement typeElement = (TypeElement) annotatedElement;
                FactoryAnnotatedCls annotatedCls = new FactoryAnnotatedCls(typeElement);
                supperClsPath = annotatedCls.getSupperClsQualifiedName().toString();

                checkValidClass(annotatedCls);

                mFactoryCodeBuilder.add(annotatedCls);
            }
            if (supperClsPath != null && !supperClsPath.equals("")) {
                mFactoryCodeBuilder
                        .setSupperClsName(supperClsPath)
                        .generateCode(mMessager, mElementUtil, mFiler);
            }
        } catch (ProcessingException e) {
            error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            error(null, e.getMessage());
        }
        return true;
    }


    /**
     * 检查注解的类是否符合规则
     */
    private void checkValidClass(FactoryAnnotatedCls item) throws ProcessingException {

        // Cast to TypeElement, has more type specific methods
        TypeElement classElement = item.getAnnotatedClsElement();

        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {//检查类是否为public
            throw new ProcessingException(classElement, "The class %s is not public.",
                    classElement.getQualifiedName().toString());
        }

        // Check if it's an abstract class
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {//检查类是否为Abstract
            throw new ProcessingException(classElement,
                    "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
        }

        // Check inheritance: Class must be child class as specified in @Factory.type();
        TypeElement superClassElement = mElementUtil.getTypeElement(item.getSupperClsQualifiedName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {//检查父类是否为Interface
            // Check interface implemented
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {//检查类是否继承了父接口，并实现了其方法
                throw new ProcessingException(classElement,
                        "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getSupperClsQualifiedName());
            }
        } else {
            // Check subclassing
            TypeElement currentClass = classElement;
            while (true) {
                TypeMirror superClassType = currentClass.getSuperclass();

                if (superClassType.getKind() == TypeKind.NONE) {//类没有继承父类，抛出异常
                    // Basis class (java.lang.Object) reached, so exit
                    throw new ProcessingException(classElement,
                            "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            item.getSupperClsQualifiedName());
                }

                if (superClassType.toString().equals(item.getSupperClsQualifiedName())) {//找到继承的父类
                    // Required super class found
                    break;
                }

                // Moving up in inheritance tree
                currentClass = (TypeElement) mTypeUtil.asElement(superClassType);//父类
            }
        }

        // Check if an empty public constructor is given
        for (Element enclosed : classElement.getEnclosedElements()) {//检查是否有一个默认的公用构造函数
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 && constructorElement.getModifiers()
                        .contains(Modifier.PUBLIC)) {
                    // Found an empty constructor
                    return;
                }
            }
        }

        // No empty constructor found
        throw new ProcessingException(classElement,
                "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());
    }

    public void error(Element e, String msg) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
}