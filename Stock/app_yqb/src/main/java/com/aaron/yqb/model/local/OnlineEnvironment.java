package com.aaron.yqb.model.local;

import android.text.TextUtils;

import com.aaron.myviews.AppPrefs;
import com.aaron.yqb.App;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.umeng.onlineconfig.OnlineConfigAgent;

/**
 *
 */
public class OnlineEnvironment {

    private static final String FIXED_DOMAINS = "http://xjdrxx.oss-cn-shenzhen.aliyuncs.com/meiniu.txt";

    private static final String KEY_HTTP_IP_ONLINE = "HTTP_IP_ONLINE";
    private static final String KEY_HTTP_IP_TEST = "HTTP_IP_TEST";
    private static final String KEY_HTTP_IP_SIMULATE = "HTTP_IP_SIMULATE";

    private static final String TAG = "TEST";

    @SerializedName("HTTP_IP_ONLINE")
    private String releaseIp;

    @SerializedName("HTTP_IP_TEST")
    private String testIp;

    @SerializedName("HTTP_IP_SIMULATE")
    private String simulateIp;

    public OnlineEnvironment(String releaseIp, String testIp, String simulateIp) {
        this.releaseIp = releaseIp;
        this.testIp = testIp;
        this.simulateIp = simulateIp;
    }

    public String getReleaseIp() {
        return releaseIp;
    }

    public String getTestIp() {
        return testIp;
    }

    public String getSimulateIp() {
        return simulateIp;
    }

    public Environment getEnvironment(int type) {
        switch (type) {
            case 1:
                return new Environment(getReleaseIp(), type, getReleaseIp());
            case 2:
                return new Environment(getTestIp(), type, getTestIp());
            case 3:
                return new Environment(getSimulateIp(), type, getSimulateIp());
        }
        return null;
    }

    public static void requestEnvironments() {
        // 友盟在线参数获取
        OnlineConfigAgent.getInstance().updateOnlineConfig(App.getAppContext());

        requestFixedDomains();
    }

    /**
     * 从固定地址 获取‘固定域名’文本
     */
    private static void requestFixedDomains() {
//        new RequestBuilder()
//                .url(FIXED_DOMAINS)
//                .method(Request.Method.GET)
//                .type(new TypeToken<OnlineEnvironment>() {
//                }.getType())
//                .listener(new Response.Listener<OnlineEnvironment>() {
//                    @Override
//                    public void onResponse(OnlineEnvironment onlineEnvironment) {
//                        if (onlineEnvironment != null) {
//                            saveOnlineEnvironment(onlineEnvironment);
//                            Environment.saveEnvironment(Environment.getEnvironment());
//                        }
//                    }
//                }).errorListener(new SimpleErrorListener(false))
//                .create().send();
    }

    public static void saveOnlineEnvironment(OnlineEnvironment environment) {
        String json = new Gson().toJson(environment);
        AppPrefs.getInstance(App.getAppContext()).setOnlineEnvironment(json);
    }

    public static OnlineEnvironment getOnlineEnvironment() {
        String json = AppPrefs.getInstance(App.getAppContext()).getOnlineEnvironment();

        OnlineEnvironment onlineEnvironment = null;

        if (!TextUtils.isEmpty(json)) {
            onlineEnvironment = new Gson().fromJson(json, OnlineEnvironment.class);
        }

        if (onlineEnvironment == null) {
            OnlineConfigAgent agent = OnlineConfigAgent.getInstance();
            String releaseIp = agent.getConfigParams(App.getAppContext(), KEY_HTTP_IP_ONLINE);
            String testIp = agent.getConfigParams(App.getAppContext(), KEY_HTTP_IP_TEST);
            String simulateIp = agent.getConfigParams(App.getAppContext(), KEY_HTTP_IP_SIMULATE);
            if (!TextUtils.isEmpty(releaseIp) && !TextUtils.isEmpty(testIp) && !TextUtils.isEmpty(simulateIp)) {
                onlineEnvironment = new OnlineEnvironment(releaseIp, testIp, simulateIp);
            }
        }

        return onlineEnvironment;
    }

    @Override
    public String toString() {
        return "OnlineEnvironment{" +
                "releaseIp='" + releaseIp + '\'' +
                ", testIp='" + testIp + '\'' +
                ", simulateIp='" + simulateIp + '\'' +
                '}';
    }
}
