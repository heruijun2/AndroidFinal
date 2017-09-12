package rn.heruijun.com.filedownload.http;

import java.io.File;

/**
 * Created by heruijun on 2017/9/8.
 */

public interface DownloadCallback {

    void success(File file);

    void fail(int errorcode, String errorMessage);

    void progress(int progess);
}
