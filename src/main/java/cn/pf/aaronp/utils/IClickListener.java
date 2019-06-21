package cn.pf.aaronp.utils;

import android.view.View;

/**
 * 按键防多次点击
 * Created by aaron pan on 2019/5/28.
 */

public abstract class IClickListener implements View.OnClickListener {

    private long mLastClickTime = 0;
    public static final int TIME_INTERVAL = 1000;

    @Override
    public void onClick(View view) {
        if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
            onIClick(view);
            mLastClickTime = System.currentTimeMillis();
        } else {
            onIClickAgain(view);
        }
    }

    /**
     * 正常点击
     *
     * @param view view
     */
    protected abstract void onIClick(View view);

    /**
     * 重复点击
     *
     * @param view
     */
    protected void onIClickAgain(View view) {
    }

}
