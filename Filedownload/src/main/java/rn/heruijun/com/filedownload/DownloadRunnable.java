package rn.heruijun.com.filedownload;

import android.os.Process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;
import rn.heruijun.com.filedownload.db.DownloadEntity;
import rn.heruijun.com.filedownload.db.DownloadHelper;
import rn.heruijun.com.filedownload.file.FileStorageManager;
import rn.heruijun.com.filedownload.http.DownloadCallback;
import rn.heruijun.com.filedownload.http.HttpManager;
import rn.heruijun.com.filedownload.utils.Logger;

/**
 * Created by heruijun on 2017/9/8.
 */

public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallback mCallback;

    private DownloadEntity mEntity;

    public DownloadRunnable(long start, long end, String url, DownloadCallback callback, DownloadEntity entity) {
        mStart = start;
        mEnd = end;
        mUrl = url;
        mCallback = callback;
        mEntity = entity;
    }

    public DownloadRunnable(long start, long end, String url, DownloadCallback callback) {
        mStart = start;
        mEnd = end;
        mUrl = url;
        mCallback = callback;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Response response = HttpManager.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
        if (response == null && mCallback != null) {
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
            return;
        }
        File file = FileStorageManager.getInstance().getFileByName(mUrl);

        long progress = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024 * 500];
            int len;
            InputStream inStream = response.body().byteStream();
            while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, len);
                progress += len;
                mEntity.setProgress_position(progress);
                Logger.debug("hrj", "progress -----> " + progress);
            }
            randomAccessFile.close();
            mCallback.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DownloadHelper.getInstance().insert(mEntity);
        }
    }
}
