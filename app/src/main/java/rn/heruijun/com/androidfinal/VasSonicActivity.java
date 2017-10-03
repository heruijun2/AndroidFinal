package rn.heruijun.com.androidfinal;

/**
 * Created by heruijun on 2017/9/3.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;

import rn.heruijun.com.androidfinal.sonic.BrowserActivity;
import rn.heruijun.com.androidfinal.sonic.SonicJavaScriptInterface;
import rn.heruijun.com.androidfinal.sonic.SonicRuntimeImpl;

/**
 * main activity of this sample
 */
public class VasSonicActivity extends Activity {

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;

    private static final String DEMO_URL = "http://mc.vip.qq.com/demo/indexv3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_vas_sonic);

        // clean up cache btn
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SonicEngine.getInstance().cleanCache();
            }
        });

        // default btn
        Button btnDefault = (Button) findViewById(R.id.btn_default_mode);
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowserActivity(MODE_DEFAULT);
            }
        });

        // preload btn
        Button btnSonicPreload = (Button) findViewById(R.id.btn_sonic_preload);
        btnSonicPreload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SonicSessionConfig sessionConfig = new SonicSessionConfig.Builder().build();
                boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(DEMO_URL, sessionConfig);
                Toast.makeText(getApplicationContext(), preloadSuccess ? "Preload start up success!" : "Preload start up fail!", Toast.LENGTH_LONG).show();
            }
        });

        // sonic mode load btn
        Button btnSonic = (Button) findViewById(R.id.btn_sonic);
        btnSonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowserActivity(MODE_SONIC);
            }
        });

        // load sonic with offline cache
        Button btnSonicWithOfflineCache = (Button) findViewById(R.id.btn_sonic_with_offline);
        btnSonicWithOfflineCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowserActivity(MODE_SONIC_WITH_OFFLINE_CACHE);
            }
        });

        if (hasPermission()) {
            init();
        } else {
            requestPermission();
        }
    }

    private void init() {
        // init sonic engine
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }
    }


    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                init();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startBrowserActivity(int mode) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, DEMO_URL);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        startActivityForResult(intent, -1);
    }

}
