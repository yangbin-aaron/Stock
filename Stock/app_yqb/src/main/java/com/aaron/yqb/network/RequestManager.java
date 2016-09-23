package com.aaron.yqb.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import cn.bvin.lib.module.net.volley.GsonRequest;

public class RequestManager  {

    private static RequestQueue sRequestQueue;

    public static void init(Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
    }

    public static void executeRequest(Request<?> request) {
        logRequest(request);
        if (sRequestQueue != null) {
            sRequestQueue.add(request);
        } else {
            throw new NullPointerException("Request queue isn't initialized.");
        }
    }

    public static void executeRequest(Request<?> request, String tag) {
        request.setTag(tag);
        executeRequest(request);
    }

    public static void cancelRequest(String tag) {
        if (sRequestQueue != null) {
            sRequestQueue.cancelAll(tag);
        }
    }

    private static final String TAG = RequestManager.class.getSimpleName();

    private static void logRequest(Request<?> request) {
        StringBuilder sb = new StringBuilder();
        switch (request.getMethod()) {
            case Request.Method.GET:
                sb.append("GET");
                break;
            case Request.Method.PUT:
                sb.append("PUT");
                break;
            case Request.Method.POST:
                sb.append("POST");
                break;
            case Request.Method.DELETE:
                sb.append("DELETE");
                break;
        }
        logHeader(sb, request);
        logParams(sb, request);
        Log.d(TAG, sb.toString());
    }

    private static void logParams(StringBuilder sb, Request<?> request) {
        if (request instanceof GsonRequest) {
            GsonRequest gsonRequest = (GsonRequest) request;
            sb.append(" ").append(gsonRequest.getUrlWithParams()).append(" ");
        }
    }

    private static void logHeader(StringBuilder sb, Request<?> request) {
        try {
            Map<String, String> headers = request.getHeaders();
            for (Object key : headers.keySet()) {
                sb.append(" -H ").append('\'')
                        .append((String) key).append(": ")
                        .append(headers.get(key)).append('\'');
            }
        } catch (AuthFailureError authFailureError) {
        }
        sb.append(" '").append(request.getUrl()).append("'");
    }
}
