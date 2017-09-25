package rn.heruijun.com.androidfinal;

import com.facebook.stetho.Stetho;

import rn.heruijun.com.filedownload.DownloadConfig;
import rn.heruijun.com.filedownload.DownloadManager;
import rn.heruijun.com.filedownload.db.DownloadHelper;
import rn.heruijun.com.filedownload.file.FileStorageManager;
import rn.heruijun.com.filedownload.http.HttpManager;

/**
 * Created by heruijun on 2017/9/8.
 */

public class MyApplication extends CrashApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
        Stetho.initializeWithDefaults(this);
        DownloadHelper.getInstance().init(this);

        DownloadConfig config = new DownloadConfig.Builder()
                .setCoreThreadSize(2)
                .setMaxThreadSize(4)
                .setLocalProgressThreadSize(1)
                .builder();
        DownloadManager.getInstance().init(config);

        UncaughtExceptionHandlerImpl.getInstance().init(this, BuildConfig.DEBUG, true, 0, MainActivity.class);
    }
}
