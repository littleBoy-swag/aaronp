package cn.pf.aaronp.imageloader.imageinterface;

import android.graphics.Bitmap;

/**
 * author: aaron.pf
 * date: 2019/3/12 14:52.
 * desc:
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
