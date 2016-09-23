package com.aaron.myviews.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.aaron.myviews.R;

public class ProgressDialog {

    private static Dialog sDialog;

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, true);
    }

    public static void showProgressDialog(Context context, boolean cancelable) {
        if (context == null) return;

        if (sDialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.progress_hud, null);
            ImageView spinner = (ImageView) view.findViewById(R.id.spinner);
            AnimationDrawable drawable = (AnimationDrawable) spinner.getBackground();
            drawable.start();

            sDialog = new Dialog(context, R.style.ProgressDialog);
            sDialog.setContentView(view);
            sDialog.setCancelable(cancelable);
            sDialog.show();
        } else if (sDialog.isShowing()) {
            return;
        }
    }

    public static void dismissProgressDialog() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
            sDialog = null;
        }
    }
}