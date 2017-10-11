package rn.heruijun.com.androidfinal;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

import rn.heruijun.com.androidfinal.biz.inc.IFruitFactory;
import rn.heruijun.com.androidfinal.view.myview.CustomView;

/**
 * Created by heruijun on 2017/9/3.
 */

public class ViewMoveActivity extends AppCompatActivity {

    CustomView cView;

    // Button设置vector背景时需要
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_move);
        cView = (CustomView) findViewById(R.id.customview);
        // View动画：不改变位置参数，点击后还是在位移之前的位置触发点击事件
        // cView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
        // 属性动画，弥补了View动画的不足
        // ObjectAnimator.ofFloat(cView, "translationX", 0, 300).setDuration(1000).start();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(cView, "alpha", 0f, 1.5f);
//        // 通常我们只关心onAnimationEnd事件
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//        });
//        animator.setDuration(1000).start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(cView, "alpha", 0f, 1.5f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(cView, "translationX", 0.0f, 200.0f, 0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.play(animator1).with(animator2);    // with, after, before
        set.start();

        // cView.smoothScrollTo(-400, 0);

        // 编译时注解：http://www.jianshu.com/p/36a8f6335456?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
        produceFruit();
    }

    public void anim(View view) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    private void produceFruit() {
        IFruitFactory.create(1).produce();
        IFruitFactory.create(2).produce();
        IFruitFactory.create(3).produce();
    }
}
