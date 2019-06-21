package cn.pf.aaronp.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

/**
 * author: aaron.pf
 * date: 2019/3/8 11:58.
 * desc:
 */

public class ActLifecycleUtil {

    /**
     * activity是否销毁
     *
     * @param context context
     * @return
     */
    public static boolean isActDestroy(Context context) {
        if (context == null) {
            return true;
        }
        Activity activity = (Activity) context;
        return isActDestroy(activity);
    }

    /**
     * Activity是否销毁
     *
     * @param activity context
     * @return
     */
    public static boolean isActDestroy(Activity activity) {
        return activity == null || activity.isFinishing();
    }

    /**
     * fragment是否销毁
     *
     * @param activity activity
     * @param fragment fragment
     * @return
     */
    public static boolean isFragmentDestroy(Activity activity, Fragment fragment) {
        return isActDestroy(activity) || fragment.isDetached();
    }

    /**
     * fragment是否销毁
     *
     * @param activity activity
     * @param fragment fragment
     * @return
     */
    public static boolean isFragmentDestroy(Activity activity, android.support.v4.app.Fragment fragment) {
        return isActDestroy(activity) || fragment.isDetached();
    }

}
