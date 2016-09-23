package com.aaron.yqb.network;

import android.util.Log;

import com.aaron.myviews.utils.ToastUtil;
import com.aaron.yqb.App;
import com.aaron.yqb.R;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import cn.bvin.lib.module.net.volley.JsonParseError;

public class SimpleErrorListener implements Response.ErrorListener {

    private boolean mShowToast;

    public SimpleErrorListener(boolean showToast) {
        mShowToast = showToast;
    }

    public SimpleErrorListener() {
        mShowToast = false;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.d("RequestManager", "Error: " + volleyError);
        if (volleyError instanceof NoConnectionError) {
            onConnectionError(volleyError);
        } else if (volleyError instanceof TimeoutError) {
            onTimeoutError(volleyError);
        } else if (volleyError instanceof ParseError) {
            onParseError(volleyError);
        } else if (volleyError instanceof JsonParseError) {
            onJsonParseError(volleyError);
        } else if (volleyError instanceof ServerError) {
            onServerError(volleyError);
        } else {
            onError(volleyError);
        }
    }

    private void showToast(int msgId) {
        if (mShowToast) {
            ToastUtil.showShortToastMsg(App.getAppContext(), msgId);
        }
    }

    protected void onConnectionError(VolleyError volleyError) {
        showToast(R.string.network_error);
    }

    protected void onTimeoutError(VolleyError volleyError) {
        showToast(R.string.network_time_out);
    }

    protected void onParseError(VolleyError volleyError) {
        showToast(R.string.json_parse_error);
    }

    protected void onJsonParseError(VolleyError volleyError) {
        showToast(R.string.json_parse_error);
    }

    protected void onServerError(VolleyError volleyError) {
        showToast(R.string.network_error);
    }

    protected void onError(VolleyError volleyError) {
        showToast(R.string.network_error);
    }
}
