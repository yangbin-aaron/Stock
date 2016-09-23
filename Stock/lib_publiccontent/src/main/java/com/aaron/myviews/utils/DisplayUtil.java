
/**
 * px,sp,dp工具类
 *
 * @Title: DisplayUtil.java
 * @Package com.luckin.magnifier.utils
 * @Description: TODO
 * @ClassName: DisplayUtil
 * @author 于泽坤
 * @date 2015-7-3 上午8:58:25
 */

package com.aaron.myviews.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DisplayUtil {

    /**
     * dp 转 px
     * @param value
     * @return
     */
    public static float convertDp2Px(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    /**
     * sp 转 px
     * @param value
     * @return
     */
    public static float convertSp2Px(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

    public static int getWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public static int getHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }
}

