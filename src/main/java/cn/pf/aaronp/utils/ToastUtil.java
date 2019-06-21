package cn.pf.aaronp.utils;

import android.os.Handler;
import android.widget.Toast;

import cn.pf.aaronp.KitApp;

/**
 * Created by aaron pan on 2019/5/28.
 */

public class ToastUtil {
    private static ToastUtil instance;

    private Toast mToast;

    private Handler mHandler = new Handler();

    private ToastUtil() {
    }

    public static ToastUtil getInstance() {
        if (instance == null) {
            synchronized (ToastUtil.class) {
                if (instance == null) {
                    instance = new ToastUtil();
                }
            }
        }
        return instance;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            if (mToast != null) {
                mToast.cancel();
            }
        }
    };

    @Deprecated
    public void showToast(CharSequence msg) {
        //这样写可以去除自带应用名的问题
        mToast = Toast.makeText(KitApp.getContext(), null, Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();

    }

    /**
     * 防止toast重复出现
     *
     * @param msg
     */
    public void showToast(String msg) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(msg);
            //此时需要重新设置时长
            mToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            mToast = Toast.makeText(KitApp.getContext(), null, Toast.LENGTH_SHORT);
            mToast.setText(msg);
        }
        mHandler.postDelayed(r, 1500);
        mToast.show();
    }

}
