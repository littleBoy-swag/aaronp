package cn.pf.aaronp.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import cn.pf.aaronp.BuildConfig;

/**
 * author: aaron.pf
 * date: 2019/3/8 17:35.
 * desc: 设备相关的信息
 */

public class DeviceHelper {

    /**
     * 判断是否是平板设备
     *
     * @param context context
     * @return
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 是否具备拨打电话的功能
     *
     * @param activity activity
     * @return
     */
    public static boolean isCallable(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager != null && telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE;
    }


    /**
     * 获取手机中sim卡的数量
     *
     * @param context context
     * @return -1代表没有获取到数据
     */
    public static int getPhoneCunt(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return telephonyManager.getPhoneCount();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 获取手机号
     *
     * @param context context
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    return telephonyManager.getLine1Number();
                }
            } else {
                return telephonyManager.getLine1Number();
            }
        }
        return "";
    }

    /**
     * 获取手机的IMEI，适用于双卡手机
     *
     * @param context context
     * @param slotId  slotId为卡槽Id，它的值为0,1
     * @return
     */
    public static String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                Method method = telephonyManager.getClass().getMethod("getImei", int.class);
                return (String) method.invoke(telephonyManager, slotId);
            }
            return "";
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 获取手机的IMEI
     *
     * @param context context
     * @return
     */
    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                Method method = telephonyManager.getClass().getMethod("getImei", int.class);
                return (String) method.invoke(telephonyManager);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 是否是小米
     *
     * @return
     */
    public static boolean isMIUI() {
        return OSHelper.isMIUI();
    }

    /**
     * 是否是魅族
     *
     * @return
     */
    public static boolean isFlyme() {
        return OSHelper.isFlyme();
    }

    /**
     * 是否是华为
     *
     * @return
     */
    public static boolean isEMUI() {
        return OSHelper.isEMUI();
    }

    /**
     * 跳转至miui的权限设置
     */
    public static void gotoMIUIPermission(Context context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppSettingIntent(context));
            }
        }
    }

    /**
     * 跳转至flyme的权限设置
     */
    public static void gotoFlymePermission(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppSettingIntent(context));
        }

    }

    /**
     * 跳转至EMUI的权限设置
     */
    public static void gotoEMUIPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //华为权限管理
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppSettingIntent(context));
        }
    }

    public static Intent getAppSettingIntent(Context context) {
        Intent localIntent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.Settings");
        localIntent.setComponent(cm);
        localIntent.setAction("android.intent.action.VIEW");
        return localIntent;
    }

}
