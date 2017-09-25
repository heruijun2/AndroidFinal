package rn.heruijun.com.androidfinal;

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
