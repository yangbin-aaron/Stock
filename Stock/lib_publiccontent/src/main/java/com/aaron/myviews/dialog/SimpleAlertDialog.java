package com.aaron.myviews.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.myviews.R;
import com.aaron.myviews.utils.DisplayUtil;

public class SimpleAlertDialog extends Dialog {

    private Context mContextReference;

    public SimpleAlertDialog(Context context, int theme) {
        super(context, theme);
        mContextReference = context;
    }

    public static class Builder {

        private Context mContext;
        private String mMessage;
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private View mContentView;
        private boolean mCancelable = true;

        private OnClickListener
                mPositiveButtonClickListener,
                mNegativeButtonClickListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setMessage(int message) {
            this.mMessage = (String) mContext.getText(message);
            return this;
        }
        
        public Builder setMessage(String message){
        	this.mMessage = message;
        	return this;
        }

        public Builder setContentView(View v) {
            this.mContentView = v;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.mPositiveButtonText = (String) mContext.getText(positiveButtonText);
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.mNegativeButtonText = (String) mContext.getText(negativeButtonText);
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonClickListener = listener;
            return this;
        }


        /**
         * Create the custom dialog
         */
        public SimpleAlertDialog create() {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final SimpleAlertDialog dialog = new SimpleAlertDialog(mContext, R.style.AppTheme_Dialog);
            View layout = inflater.inflate(R.layout.alert_dialog_simple, null);

            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCancelable(mCancelable);

            // set the positive button
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_positive))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    layout.findViewById(R.id.btn_positive)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_positive).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.divider).setVisibility(
                        View.GONE);
            }


            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_negative))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    layout.findViewById(R.id.btn_negative)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_negative).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.divider).setVisibility(
                        View.GONE);
            }

            // set the content message
            if (mMessage != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(mMessage);
            } else if (mContentView != null) {
                LinearLayout content = (LinearLayout) layout.findViewById(R.id.content);
                content.removeAllViews();

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                content.addView(mContentView, params);
            }

            dialog.setContentView(layout);
            return dialog;
        }
    }

    @Override
    public void show() {
        if (mContextReference != null && mContextReference instanceof Activity) {
            if (!((Activity) mContextReference).isFinishing()) {
                super.show();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int maxWidth = (int) (DisplayUtil.getWidth(mContextReference) * 0.85);
        int width = getWindow().getDecorView().getWidth();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        Log.e("AlertDialog", "onWindowFocusChanged>>"+width + "," + maxWidth);
        if (width > maxWidth) {
            lp.width = maxWidth;
            onWindowAttributesChanged(lp);
        }
    }
}
