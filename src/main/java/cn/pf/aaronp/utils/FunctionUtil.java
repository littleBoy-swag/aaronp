package cn.pf.aaronp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: aaron.pf
 * date: 2019/3/8 15:10.
 * desc: 工具类
 */

public class FunctionUtil {

    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return
     */
    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     * @return
     */
    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断是否是手机号
     *
     * @param num phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String num) {
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        String regEx = "^[1][345789]\\d{9}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    /**
     * 判断是否是身份证号 18位
     *
     * @param num identityNumber
     * @return
     */
    public static boolean isIdentityNumber(String num) {
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        String regEx = "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2]\\d)|(3[0-1]))((\\d{4})|(\\d{3}[Xx]))$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    /**
     * 对字符串做隐位处理
     *
     * @param origin 原字符串
     * @param start  起始位置（包含）
     * @param end    结束位置（包含）
     * @param holder 替换字符
     * @return
     */
    public static String getHiddenString(String origin, int start, int end, char holder) {
        if (origin == null || TextUtils.isEmpty(origin)) {
            return "";
        }
        if (origin.length() < start || end < start) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = origin.length();
        for (int i = 0; i < len; i++) {
            if (i < start) {
                sb.append(origin.charAt(i));
            } else if (i <= end) {
                sb.append(holder);
            } else {
                sb.append(origin.charAt(i));
            }

        }
        return sb.toString();
    }

    /**
     * 对字符串做隐位处理
     *
     * @param origin 原字符串
     * @param start  起始位置（包含）
     * @param end    结束位置（包含）
     * @return
     */
    public static String getHiddenString(String origin, int start, int end) {
        return getHiddenString(origin, start, end, '*');
    }

}
