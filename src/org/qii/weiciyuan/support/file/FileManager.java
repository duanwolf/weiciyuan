package org.qii.weiciyuan.support.file;

import android.os.Environment;
import org.qii.weiciyuan.support.utils.AppLogger;

import java.io.File;
import java.io.IOException;

/**
 * User: Jiang Qi
 * Date: 12-8-3
 * Time: 上午10:06
 */
public class FileManager {
    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String APP_NAME = "weiciyuan";
    private static final String AVATAR_CACHE = "avatar";
    private static final String PICTURE_THUMBNAIL_CACHE = "picture_thumbnail";
    private static final String PICTURE_BMIDDLE = "picture_bmiddle";
    private static final String PICTURE_LARGE = "picture_large";


    private static boolean isExternalStorageMounted() {

        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean unMounted = Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTED);

        return !(!canRead || onlyRead || unMounted);
    }

    private static String getFileAbsolutePathFromRelativePath(String relativePath) {
        String result = SDCARD_PATH + File.separator + APP_NAME + relativePath;
        ;
        return result;
    }

    public static String getFileAbsolutePathFromUrl(String url, FileLocationMethod method) {
        String oldRelativePath = getFileRelativePathFromUrl(url);
        String newRelativePath = "";
        switch (method) {
            case avatar:
                newRelativePath = File.separator + AVATAR_CACHE + oldRelativePath;
                break;
            case picture_thumbnail:
                newRelativePath = File.separator + PICTURE_THUMBNAIL_CACHE + oldRelativePath;
                break;

        }

        String absolutePath = getFileAbsolutePathFromRelativePath(newRelativePath);

        //AppLogger.d(absolutePath);

        return absolutePath;
    }

    private static String getFileRelativePathFromUrl(String url) {
        //AppLogger.d(url);
        int index = url.indexOf("//");

        String s = url.substring(index + 2);

        String result = s.substring(s.indexOf("/"));


        return result;
    }


    public static File createNewFileInSDCard(String absolutePath) {
        if (!isExternalStorageMounted()) {
            AppLogger.e("sdcard unavailiable");
            return null;
        }

        String absoluteFileDirPath = absolutePath.substring(0, absolutePath.length() - 1);
        File file = new File(absolutePath);
        if (file.exists()) {
            return file;
        } else {

            File dirFile = new File(absoluteFileDirPath);
            if (dirFile.mkdirs()) {

                try {
                    if (file.createNewFile()) {
                        return file;
                    }
                } catch (IOException e) {
                    AppLogger.d(e.getMessage());
                    return null;
                }
            }

        }
        return null;

    }
}
