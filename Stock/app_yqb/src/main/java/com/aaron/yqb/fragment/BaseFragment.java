package com.aaron.yqb.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.aaron.myviews.dialog.SimpleAlertDialog;
import com.aaron.yqb.R;

import java.util.Timer;
import java.util.TimerTask;

public class BaseFragment extends Fragment {

    private Timer mTimer;

    protected void startScheduleJob(final Handler handler, long delay, long interval) {
        if (mTimer != null) cancelTimer();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (handler != null) {
                    handler.sendEmptyMessage(0);
                }
            }
        }, delay, interval);
    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    protected String getRequestTag() {
        return getClass().getSimpleName();
    }

    protected void showAlertDialog(int msgResId, int positiveResId, int negativeResId,
                                   DialogInterface.OnClickListener positiveButtonListener,
                                   DialogInterface.OnClickListener negativeButtonListener,
                                   boolean cancelable) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            new SimpleAlertDialog.Builder(getActivity())
                    .setMessage(msgResId)
                    .setPositiveButton(positiveResId, positiveButtonListener)
                    .setNegativeButton(negativeResId, negativeButtonListener)
                    .setCancelable(cancelable)
                    .create().show();
        }
    }

    protected void showAlertDialog(String message, int positiveResId, int negativeResId,
                                   DialogInterface.OnClickListener positiveButtonListener,
                                   DialogInterface.OnClickListener negativeButtonListener,
                                   boolean cancelable) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            new SimpleAlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setPositiveButton(positiveResId, positiveButtonListener)
                    .setNegativeButton(negativeResId, negativeButtonListener)
                    .setCancelable(cancelable)
                    .create().show();
        }
    }

    protected void showAlertDialog(View view, int positiveResId, int negativeResId,
                                   DialogInterface.OnClickListener positiveButtonListener,
                                   DialogInterface.OnClickListener negativeButtonListener,
                                   boolean cancelable) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            new SimpleAlertDialog.Builder(getActivity())
                    .setContentView(view)
                    .setPositiveButton(positiveResId, positiveButtonListener)
                    .setNegativeButton(negativeResId, negativeButtonListener)
                    .setCancelable(cancelable)
                    .create().show();
        }
    }

    protected void showAlertDialog(int msgResId, int positiveResId,
                                   DialogInterface.OnClickListener positiveButtonListener, boolean cancelable) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Dialog dialog = new SimpleAlertDialog.Builder(getActivity())
                    .setMessage(msgResId)
                    .setPositiveButton(positiveResId, positiveButtonListener)
                    .setCancelable(cancelable)
                    .create();
            dialog.show();
        }
    }

    protected void showAlertDialog(int msgResId, int positiveResId,
                                   DialogInterface.OnClickListener positiveButtonListener) {
        showAlertDialog(msgResId, positiveResId, positiveButtonListener, true);
    }

    protected void showAlertDialog(int msgResId) {
        showAlertDialog(msgResId, R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    protected void showAlertDialog(String message, int positiveResId,
                                   DialogInterface.OnClickListener positiveButtonListener, boolean cancelable) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            new SimpleAlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setCancelable(cancelable)
                    .setPositiveButton(positiveResId, positiveButtonListener)
                    .create().show();
        }
    }

    protected void showAlertDialog(String message, int positiveResId,
                                   DialogInterface.OnClickListener positiveButtonListener) {
        showAlertDialog(message, positiveResId, positiveButtonListener, true);
    }

    protected void showAlertDialog(String message) {
        showAlertDialog(message, R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, true);
    }

    protected void showAlertDialog(View view,int positiveResId,
                                   DialogInterface.OnClickListener positiveButtonListener) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            new SimpleAlertDialog.Builder(getActivity())
                    .setContentView(view)
                    .setPositiveButton(positiveResId, positiveButtonListener)
                    .create().show();
        }
    }

    protected void showAlertDialog(View view) {
        showAlertDialog(view, R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    protected void showActivateDialog() {
        new SimpleAlertDialog.Builder(getActivity())
                .setMessage(R.string.you_have_not_activated_cainiu_yet)
                .setPositiveButton(R.string.activate_right_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        WebActivity.cainiuActivate(getActivity());
                    }
                }).setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    protected void showLoginFirstDialog() {
//        new SimpleAlertDialog.Builder(getActivity())
//                .setMessage(R.string.not_login_tip)
//                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        LoginActivity.enter(getActivity());
//                    }
//                }).setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        }).create().show();
    }

    @Override
    public void onStop() {
        super.onStop();
//        RequestManager.cancelRequest(getRequestTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
