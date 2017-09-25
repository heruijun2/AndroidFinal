package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by heruijun on 2017/9/25.
 */
@Retention(RetentionPolicy.CLASS)   //该注解只保留到编译时
@Target(ElementType.TYPE)   //该注解只作用与类、接口、枚举
public @interface Factory {
    /**
     * 工厂对应的ID，可以多个ID对应一个生产线类
     */
    int[] ids();

    /**
     * 生产接口类
     */
    Class superClass();
}
