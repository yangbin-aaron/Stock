package com.aaron.yqb.model.local;

import android.text.TextUtils;

import com.aaron.myviews.AppPrefs;
import com.aaron.yqb.App;
import com.aaron.yqb.R;
import com.aaron.yqb.config.ApiConfig;
import com.aaron.yqb.network.http.HttpConfig;
import com.google.gson.Gson;

public class Environment {

//    public static final String DEFAULT_DOMAIN = "stock.cainiu.com";
    //private static final String DEFAULT_DOMAIN = "121.40.85.43";

    public static final int TYPE_RELEASE = 1;
    public static final int TYPE_DEBUG = 2;
    public static final int TYPE_SIMULATION = 3;
    //public static final int TYPE_PRE_RELEASE = 4;

    private static final String GROUP_DEBUG = "0";
    private static final String GROUP_RELEASE = "1";

    private String name;
    private Integer type;
    private String domain;
    private Integer position;

    public Environment(String name, Integer type, String domain) {
        this.name = name;
        this.type = type;
        this.domain = domain;
    }

    public Environment(String name, Integer type, String domain, Integer position) {
        this.name = name;
        this.type = type;
        this.domain = domain;
        this.position = position;
    }

    public Integer getType() {
        if (type != null) {
            return type;
        }
        return 1;
    }

    public Integer getPosition() {
        return position;
    }

    public String getDomain() {
        return domain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getGroup() {
        if (type != null && (type == TYPE_DEBUG || type == TYPE_SIMULATION)) {
            return GROUP_DEBUG;
        } else {
            return GROUP_RELEASE;
        }
    }

    public static void saveEnvironment(Environment environment) {
        String json = new Gson().toJson(environment);
        AppPrefs.getInstance(App.getAppContext()).setEnvironment(json);

        ApiConfig.HOST = environment.getDomain();
        HttpConfig.SERVER_DOMAIN = environment.getDomain();
    }

    public static Environment getEnvironment() {
        String json = AppPrefs.getInstance(App.getAppContext()).getEnvironment();
        Environment environment = null;
        if (!TextUtils.isEmpty(json)) {
            environment = new Gson().fromJson(json, Environment.class);
            OnlineEnvironment onlineEnvironment = OnlineEnvironment.getOnlineEnvironment();
            if (onlineEnvironment != null) {
                Environment curEnvironment = onlineEnvironment.getEnvironment(environment.getType());
                if (!environment.getDomain().equalsIgnoreCase(curEnvironment.getDomain())) {
                    environment.setDomain(curEnvironment.getDomain());
                }
            }
        } else {
            // 一般为重新安装或者第一次打开app的情况，本地存储下一个默认的环境
            environment = new Environment("正式环境", TYPE_RELEASE, App.getAppContext().getString(R.string.default_domain));
        }

        // book_text_ipadress
//        environment = new Environment("测式环境", TYPE_RELEASE, App.getAppContext().getString(R.string.default_domain_test));

        Environment.saveEnvironment(environment);
        return environment;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", domain='" + domain + '\'' +
                '}';
    }
}
