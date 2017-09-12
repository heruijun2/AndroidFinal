package rn.heruijun.com.androidfinal.view.myview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import rn.heruijun.com.androidfinal.utils.Utils;

/**
 * Created by heruijun on 2017/9/5.
 */

public class MyWebview extends WebView {

    private MyWebviewClient mMyWebviewClient;
    private MyWebChromeClient mMyWebChromeClient;

    public MyWebview(Context context) {
        super(context);
        setup();
    }

    public MyWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public MyWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setup() {
        WebSettings ws = getSettings();
        setupWebSettings(ws);

        if (null == mMyWebviewClient) {
            mMyWebviewClient = new MyWebviewClient();
        }
        if (null == mMyWebChromeClient) {
            mMyWebChromeClient = new MyWebChromeClient();
        }

        this.setWebViewClient(mMyWebviewClient);
        this.setWebChromeClient(mMyWebChromeClient);
    }

    @TargetApi(16)
    @SuppressLint("SetJavaScriptEnabled")
    protected void setupWebSettings(WebSettings ws) {
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // ws.setJavaScriptCanOpenWindowsAutomatically(true);
        // ws.setUserAgentString("MogoWebkit");
        ws.setAllowFileAccess(true);
        if (Utils.hasJellyBean()) {
            ws.setAllowFileAccessFromFileURLs(true);
            ws.setAllowUniversalAccessFromFileURLs(true);
        }

        // enable html cache
        ws.setDomStorageEnabled(true);
        // Set cache size to 8 mb by default. should be more than enough
        ws.setAppCacheMaxSize(1024 * 1024 * 16);
        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        ws.setAppCachePath("/data/data/" + getContext().getPackageName() + "/cache");
        ws.setAllowFileAccess(true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            ws.setUseWideViewPort(true);
//        }

        if (Utils.hasLollipop()) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

}
