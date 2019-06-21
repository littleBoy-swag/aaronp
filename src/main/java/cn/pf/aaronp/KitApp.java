package cn.pf.aaronp;

import android.app.Application;

public class KitApp {
    private static Application mContext;

    public synchronized static Application getContext() {
        return mContext;
    }

    public synchronized static void setContext(Application context) {
        //防止多次调用，设置多次
        if (context == null || KitApp.mContext != null) {
            return;
        }
        KitApp.mContext = context;
    }

}
