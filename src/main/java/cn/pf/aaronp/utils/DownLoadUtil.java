package cn.pf.aaronp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by aaron pan on 2019/5/28.
 */

public class DownLoadUtil {

    /**
     * 下载功能
     * 并返回下载的id
     * 如果需要监听进度可配合监听下载进度的方法使用
     * 不需要则直接调用即可
     *
     * @param context  context
     * @param fileUrl  文件地址
     * @param fileName 文件名
     */
    public static long startDownLoad(Context context, String fileUrl, String fileName) {
        long downloadId = -1;
        //系统自带download功能
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(fileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //设置通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //下载的内容可以被手机扫描到
        request.setVisibleInDownloadsUi(true);
        request.setTitle("下载");
        request.setAllowedOverRoaming(false);
        //将下载的文件存在/Download目录下面
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        if (downloadManager != null) {
            downloadId = downloadManager.enqueue(request);
        }
        return downloadId;
    }

    /**
     * 获取当前的下载进度
     *
     * @param context    context
     * @param downloadId downloadId
     * @return 1 -> 100
     */
    public static int getDownloadPercent(Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        if (downloadManager != null) {
            cursor = downloadManager.query(query);
        }
        if (cursor != null && cursor.moveToFirst()) {
            int downloadBytesIdx = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int totalBytesIdx = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            long totalBytes = cursor.getLong(totalBytesIdx);
            long downloadBytes = cursor.getLong(downloadBytesIdx);
            return (int) (downloadBytes * 100 / totalBytes);
        }
        return 0;
    }

    /**
     * 取消下载
     *
     * @param context context
     */
    public static void cancelDownload(Context context, int downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.remove(downloadId);
    }

}
