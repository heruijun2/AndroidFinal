package rn.heruijun.com.androidfinal.biz;

import android.util.Log;

import com.example.Factory;

import rn.heruijun.com.androidfinal.biz.inc.IFruit;

/**
 * Created by heruijun on 2017/9/25.
 */
@Factory(ids = {1}, superClass = IFruit.class)
public class Apple implements IFruit {

    @Override
    public void produce() {
        Log.d("AnnotationDemo", "生成苹果");
    }
}
