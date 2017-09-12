package rn.heruijun.com.filedownload.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import rn.heruijun.com.filedownload.utils.Md5Utils;

/**
 * Created by heruijun on 2017/9/8.
 */

public class FileStorageManager {

    private static final FileStorageManager sManager = new FileStorageManager();

    private Context mContext;

    public static FileStorageManager getInstance() {
        return sManager;
    }

    private FileStorageManager() {

    }

    public void init(Context context) {
        this.mContext = context;
    }

    public File getFileByName(String url) {
        File parent;
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
            parent = mContext.getExternalCacheDir();
        } else {
            parent = mContext.getCacheDir();
        }
        String fileName = Md5Utils.generateCode(url);
        File file = new File(parent, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
