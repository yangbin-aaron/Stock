package com.aaron.yqb.activity.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.aaron.myviews.AppPrefs;
import com.aaron.myviews.model.AccountInfoListModel;
import com.aaron.myviews.model.newmodel.ListResponse;
import com.aaron.yqb.R;
import com.aaron.yqb.activity.MainActivity;
import com.aaron.yqb.config.ApiConfig;
import com.aaron.yqb.network.RequestBuilder;
import com.aaron.yqb.network.http.HttpKeys;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.List;

import cn.bvin.lib.debug.SimpleLogger;

public class SplashActivity extends Activity {

    private final Long SPLASH_DELAY = 2 * 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PushAgent.getInstance(this).onAppStart();

        skipActivity();
    }

    private void skipActivity() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() { //用户第一次使用应用
                if (AppPrefs.getInstance(SplashActivity.this).getIsFirstStart()) {
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    SimpleLogger.log_e("skipActivity", "进主页");
                    startActivity();
//                    if (UserInfoManager.getInstance().getToken() != null) {
//                        addAccountInfo(UserInfoManager.getInstance().getToken());
//                    }
                }
            }
        }, SPLASH_DELAY);
    }

    /**
     * 用户激活账户记录
     */
    private void addAccountInfo(String s) {

        new RequestBuilder()
                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.USER_GETACCOUNT_INFO))
                .put(HttpKeys.TOKEN, s)
                .type(new TypeToken<ListResponse<AccountInfoListModel>>() {
                }.getType())
                .listener(new com.android.volley.Response.Listener<ListResponse<AccountInfoListModel>>() {

                    @Override
                    public void onResponse(ListResponse<AccountInfoListModel> response) {
                        if (response.isSuccess() && response.hasData()) {
                            List<AccountInfoListModel> data = response.getData();
//                            UserInfoManager.getInstance().addAccountInfoList(data);
                        }
                    }
                })
                .errorListener(new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        ProgressDialog.dismissProgressDialog();
                    }
                })
                .create().send();
    }

    private void startActivity() {
        if (!AppPrefs.getInstance(this).isRunBackground()) {
            AppPrefs.getInstance(this).setCoveringInstallation(true);
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
