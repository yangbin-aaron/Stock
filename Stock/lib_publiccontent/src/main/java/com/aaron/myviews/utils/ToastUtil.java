package com.aaron.myviews.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.myviews.R;

/**
 * ToastUtil工具类
 *
 * @Description: TODO
 */
public class ToastUtil {

    private static Toast mToast;
    private static TextView mMessageTv;

    public static void showShortToastMsg(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            makeText(context, msg, Toast.LENGTH_SHORT, 100).show();
        }
    }

    public static void showShortToastMsg(Context context, int resId) {
        makeText(context, resId, Toast.LENGTH_SHORT, 100).show();
    }

    public static void showLongToastMsg(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            makeText(context, msg, Toast.LENGTH_LONG, 100).show();
        }
    }

    public static void showLongToastMsg(Context context, int resId) {
        makeText(context, resId, Toast.LENGTH_SHORT, 100).show();
    }

    /**
     * 定位Toast
     *
     * @param text
     * @param duration
     * @param anchorY  锚控件在Window中的Y坐标
     * @return Toast
     */
    private static Toast makeText(Context context, CharSequence text, int duration, int anchorY) {
        initToast(context, duration, anchorY);
        mMessageTv.setText(text);
        return mToast;
    }

    /**
     * 在anchorView下方弹出Toast[可能有内存泄漏风险]
     *
     * @param text
     * @param anchor 锚，参考目标View
     */
    @Deprecated
    public static void makeText(Context context, CharSequence text, View anchor) {
        if (!TextUtils.isEmpty(text)) {
            int anchorY;
            int[] anchorLocation = new int[2];
            anchor.getLocationInWindow(anchorLocation);
            anchorY = anchorLocation[1] + anchor.getHeight();
            makeText(context, text, Toast.LENGTH_SHORT, anchorY).show();
        }
    }

    public static void makeText(Context context, int resid, View anchor) {
        int anchorY;
        int[] anchorLocation = new int[2];
        anchor.getLocationInWindow(anchorLocation);
        anchorY = anchorLocation[1] + anchor.getHeight();
        makeText(context, resid, Toast.LENGTH_SHORT, anchorY).show();
    }

    private static Toast makeText(Context context, int resid, int duration, int anchorY) {
        initToast(context, duration, anchorY);
        mMessageTv.setText(resid);
        return mToast;
    }

    private static void initToast(Context context, int duration, int anchorY) {
        if (mToast == null) {
            // 引用ApplicationContext，以免造成内存泄漏
            mToast = new Toast(context);
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.gravity_toast_layout, null);
            mMessageTv = (TextView) v.findViewById(android.R.id.message);
            mToast.setView(v);
            mToast.setMargin(0, 0);
        }

        mToast.setDuration(duration);
        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 1, (int) DisplayUtil.convertDp2Px(context, anchorY));
    }

}
