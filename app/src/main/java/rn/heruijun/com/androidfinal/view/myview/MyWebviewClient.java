package rn.heruijun.com.androidfinal.view.myview;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by heruijun on 2017/9/6.
 */

public class MyWebviewClient extends WebViewClient {

    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (request != null && request.getUrl() != null && request.getMethod().equalsIgnoreCase("get")) {
            String scheme = request.getUrl().getScheme().trim();
            Log.e("scheme: ", scheme);
            if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                try {
                    // URL url = new URL(checkEnv(request.getUrl().toString()));
                    URL url = new URL(request.getUrl().toString());
                    URLConnection connection = url.openConnection();
                    String contentType = connection.getContentType();
                    Log.e("contentType: ", contentType);
                    if (contentType.contains("application/javascript")) {
                        Log.e("ajax请求", "拦截");
                        return new WebResourceResponse("application/javascript", "urf-8", connection.getInputStream());
                    }
                    // return new WebResourceResponse(connection.getContentType(), connection.getContentEncoding(), connection.getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        String url = uri.getHost();
        //String queryParameter = uri.getQueryParameter("version");
        Log.e("HOST: ", url);
        //Log.e("queryParameter: ", queryParameter);
        return super.shouldOverrideUrlLoading(view, request);
    }

    public static String getJSContent(InputStream is) {
        if (is == null) return null;
        BufferedReader reader;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
            }
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(is);
        }
    }

    public static void closeIO(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
