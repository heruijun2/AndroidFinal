package rn.heruijun.com.androidfinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rn.heruijun.com.androidfinal.view.myview.CustomView;

/**
 * Created by heruijun on 2017/9/3.
 */

public class ViewMoveActivity extends AppCompatActivity {

    CustomView cView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_move);
        cView = (CustomView) findViewById(R.id.customview);
    }
}
