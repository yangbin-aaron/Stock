
/**
 * 服务端时间
 *
 * @Title: ServerTimeInfo.java
 * @Package com.luckin.magnifier.model.newmodel
 * @Description: TODO
 * @ClassName: ServerTimeInfo
 * @author 于泽坤
 * @date 2015-7-6 上午10:58:55
 */

package com.aaron.yqb.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aaron.myviews.AppPrefs;
import com.aaron.myviews.model.newmodel.Response;
import com.aaron.yqb.App;
import com.aaron.yqb.config.ApiConfig;
import com.aaron.yqb.network.RequestBuilder;
import com.aaron.yqb.network.SimpleErrorListener;
import com.android.volley.DefaultRetryPolicy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ServerTime {

    private static final int PERIOD = 10 * 60 * 1000; // 10 min

    /**
     * "timeInMillis": "1436151489638",
     "	nowTime": "2015-07-06 10:58:09"
     */
    private Long timeInMillis;
    private String nowTime;
    private Date savedDate;

    private static ServerTime sInstance;
    private static Timer sTimer;

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public String getNowTime() {
        return nowTime;
    }

    public Date getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(Date savedDate) {
        this.savedDate = savedDate;
    }

    public static void setServerTime(Context context, ServerTime serverTime) {
        sInstance = serverTime;
        if (sInstance != null) {
            String json = new Gson().toJson(sInstance);
            Log.d("TEST", "serverT: " + json);
            AppPrefs.getInstance(context).setServerTime(json);
        }
    }

    public static ServerTime getServerTime(Context context) {
        if (sInstance == null) {
            String json = AppPrefs.getInstance(context).getServerTime();
            if (!TextUtils.isEmpty(json)) {
                sInstance = new Gson().fromJson(json, ServerTime.class);
            }
        }
        return sInstance;
    }

    /**
     * 执行周期性任务：同步服务器时间到本地
     */
    public static void scheduleSynchronize() {
        sTimer = new Timer();
        sTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronizeSysTime();
            }
        }, new Date(), PERIOD);
    }

    /**
     * 关闭周期性任务
     */
    public static void stop() {
        if (sTimer != null) {
            sTimer.cancel();
            sTimer = null;
        }
    }

    public static void synchronizeSysTime() {
        requestSysTimeFromServer();
    }

    private static void requestSysTimeFromServer() {
        new RequestBuilder()
                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.SYSTEM_TIME))
                .type(new TypeToken<Response<ServerTime>>() {
                }.getType())
                .retryPolicy(new DefaultRetryPolicy(10 * 1000, 3, 1))
                .listener(new com.android.volley.Response.Listener<Response<ServerTime>>() {
                    @Override
                    public void onResponse(Response<ServerTime> response) {
                        if (response.isSuccess()) {
                            saveServerTime(response.getData());
                        }
                    }
                }).errorListener(new SimpleErrorListener(false))
                .create().send();
    }

    private static void saveServerTime(ServerTime data) {
        data.setSavedDate(new Date());
        ServerTime.setServerTime(App.getAppContext(), data);
    }

    public static Long getCurrentTimestamp(Context context) {
        ServerTime serverTime = ServerTime.getServerTime(context);
        if (serverTime != null) {
            long savedDate = serverTime.getSavedDate().getTime();
            long currentDate = new Date().getTime();
            long calculatedDate = serverTime.getTimeInMillis() + (currentDate - savedDate);
            return calculatedDate;
        }
        return new Date().getTime();
    }

    @Override
    public String toString() {
        return "ServerTime{" +
                "timeInMillis=" + timeInMillis +
                ", nowTime='" + nowTime + '\'' +
                '}';
    }
}

