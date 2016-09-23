package com.aaron.yqb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.aaron.myviews.TestLauncher;
import com.aaron.myviews.TestLauncher.LaunchMeta;
import com.aaron.yqb.App;
import com.aaron.yqb.BuildConfig;
import com.aaron.yqb.activity.guide.SplashActivity;

/**
 * 程序入口活动Activity，编者主要是想在此处启动一个TestLauncher类（快捷测试启动类）
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = (App) getApplication();
        if (BuildConfig.DEBUG && app.mLaunchMeta != null) {
            launch(app.mLaunchMeta);
        } else {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }

    private TestLauncher mTestLauncher;

    /** 此方法不能轻易用，只能在manifest中设置Main和Launch的Activity中调用 */
    protected void launch(LaunchMeta launchMeta) {
        if (mTestLauncher == null) {
            mTestLauncher = new TestLauncher(this);
        }
        if (launchMeta != null) {
            mTestLauncher.launch(launchMeta);
            finish();// 启动后将结束自己
        }
    }

    /**
     * 实现此方法，便可快速测试，启动app直接跳转至你所给定的目标 Activity或Service或广播
     * */
    protected static LaunchMeta buildLaunchMeta() {
        return null;
    }
}
