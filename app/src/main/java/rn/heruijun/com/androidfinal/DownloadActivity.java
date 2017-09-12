package rn.heruijun.com.androidfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import rn.heruijun.com.filedownload.DownloadManager;
import rn.heruijun.com.filedownload.file.FileStorageManager;
import rn.heruijun.com.filedownload.http.DownloadCallback;
import rn.heruijun.com.filedownload.utils.Logger;

/**
 * Created by heruijun on 2017/9/3.
 */

public class DownloadActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mDownloadFile;
    private ProgressBar mProgressBar;
    private Button mDownloadApk;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mDownloadFile = (TextView) findViewById(R.id.download_file);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mDownloadApk = (Button) findViewById(R.id.download_apk);

        File file = FileStorageManager.getInstance().getFileByName("http://www.baidu.com");
        mDownloadFile.setText(file.getAbsolutePath());

        // 普通下载
//        HttpManager.getInstance().asyncRequest("http://img.imooc.com/59af68590001340809360316.jpg", new DownloadCallback() {
//            @Override
//            public void success(File file) {
//                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mImageView.setImageBitmap(bitmap);
//                    }
//                });
//                Logger.debug("hrj", "success " + file.getAbsolutePath());
//            }
//
//            @Override
//            public void fail(int errorcode, String errorMessage) {
//                Logger.debug("hrj", "fail " + errorcode + " " + errorMessage);
//            }
//
//            @Override
//            public void Progress(int progess) {
//
//            }
//        });

        mDownloadApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.getInstance().download("http://ucdl.25pp.com/fs01/union_pack/Wandoujia_173762_web_seo_baidu_homepage.apk", new DownloadCallback() {
                    @Override
                    public void success(File file) {
                        if (count < 1) {
                            count++;
                            return;
                        }
                        Log.e("文件下载地址：", file.getAbsolutePath());
                        // installApk(file);
                    }

                    @Override
                    public void fail(int errorcode, String errorMessage) {

                    }

                    @Override
                    public void progress(int progess) {
                        mProgressBar.setProgress(progess);
                    }
                });
            }
        });

        // 分段下载
        DownloadManager.getInstance().download("http://img.imooc.com/59af68590001340809360316.jpg", new DownloadCallback() {
            @Override
            public void success(File file) {
                if (count < 1) {
                    count++;
                    return;
                }
                // Log.e("图片下载地址：", file.getAbsolutePath());
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
                Logger.debug("hrj", "success " + file.getAbsolutePath());
            }

            @Override
            public void fail(int errorcode, String errorMessage) {
                Logger.debug("hrj", "fail" + errorcode + " " + errorMessage);
            }

            @Override
            public void progress(int progess) {

            }
        });
    }

    /**
     * android 7.0下会报错
     *
     * @param file
     */
    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        DownloadActivity.this.startActivity(intent);
    }
}
