package com.aaron.myviews.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by yangbin on 16/7/1.
 * 屏幕适配相关工具类
 */
public class ScreenUtil {

    /**
     * 根据手机分辨率转换字体大小
     */
    public static int getFontSize(Context context, float textSize) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        int rate = (int) (textSize * (float) screenHeight / 1280);
        return rate;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenW(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }
}
