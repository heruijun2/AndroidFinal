package rn.heruijun.com.androidfinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rn.heruijun.com.androidfinal.view.myview.MyWebview;

/**
 * Created by heruijun on 2017/9/5.
 */

public class MyWebviewActivity extends AppCompatActivity {

    private MyWebview mMyWebview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywebview);
        mMyWebview = (MyWebview) findViewById(R.id.mywebview);
        mMyWebview.loadUrl("http://192.168.74.146:8080/bossChart/bosschart.html");
    }

}