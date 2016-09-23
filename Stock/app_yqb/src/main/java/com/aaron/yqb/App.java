package com.aaron.yqb;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.aaron.myviews.AppPrefs;
import com.aaron.myviews.TestLauncher.LaunchMeta;
import com.aaron.yqb.model.ServerTime;
import com.aaron.yqb.network.RequestManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;

import java.util.Map;

public class App extends Application {

    protected static final String TAG = "App";
    private static final String DB_NAME = "cainiu_db";

    public LaunchMeta mLaunchMeta;
    
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        RequestManager.init(sContext);
        ServerTime.synchronizeSysTime();

        MobclickAgent.setDebugMode(BuildConfig.DEBUG);

        if (!BuildConfig.DEBUG) {
            registerAppCrashHandler();
        }

        Log.i("device_token = ", UmengRegistrar.getRegistrationId(getApplicationContext()));
        PushAgent.getInstance(getApplicationContext()).setResourcePackageName("com.luckin.magnifier");
        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            @Override
            public void dealWithNotificationMessage(Context paramContext, UMessage paramUMessage) {
                try {
                    // 后台传过来的参数，开发人员可根据type来启动对应的activity，type只自己定义。
                    // type_id是需要将此参数传给对应的activity，需要传什么都行。
                    Map<String, String> map = paramUMessage.extra;

                    if (map.get("level").equals("1")) {
                        UTrack.getInstance(paramContext).trackMsgClick(paramUMessage);
                        if (!AppPrefs.getInstance(getAppContext()).isRunBackground()) {
//                            showPopupActivity(paramContext, paramUMessage);
                        } else {
                            super.dealWithNotificationMessage(paramContext, paramUMessage);
                        }
                    } else {
                        super.dealWithNotificationMessage(paramContext, paramUMessage);
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void dealWithCustomMessage(Context paramContext, UMessage paramUMessage) {
                super.dealWithCustomMessage(paramContext, paramUMessage);
            }
        };
        PushAgent.getInstance(sContext).setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void openActivity(Context context, UMessage msg) {
                Map<String, String> map = msg.extra;
//                if (map.get("message_type").equals("1")) {
//                    Intent intent = new Intent(sContext, SysMsgInfoActivity.class);
//                    intent.putExtra(ViewConfig.EXTRAS_KEY_STR.ID, map.get("messageId"));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    sContext.startActivity(intent);
//                } else if (map.get("message_type").equals("2")) {
//                    Intent intent = new Intent(sContext, TradingToRemindActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    sContext.startActivity(intent);
//                }
            }
        };
        PushAgent.getInstance(sContext).setNotificationClickHandler(notificationClickHandler);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

//    public void showPopupActivity(Context context, UMessage message) {
//        Map<String, String> map = message.extra;
//
//        Intent intent = new Intent();
//        intent.setClass(context, PushPopupActivity.class);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(ViewConfig.PUSH.MESSAGE, message.text);
//        bundle.putString(ViewConfig.PUSH.TITLE, message.title);
//        bundle.putString(ViewConfig.PUSH.MESSAGE_TYPE, map.get("message_type"));
//        bundle.putString(ViewConfig.PUSH.MESSAGEID, map.get("messageId"));
//
//        intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        /*ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo[] mi = am.getProcessMemoryInfo(new int[Process.myPid()]);
		if (mi != null && mi[0] != null) {
			int pss = mi[0].getTotalPss();
			String memoryInfo = "占用内存：" + pss + "kB";
			MobclickAgent.reportError(this, memoryInfo);
		}*/
    }

    public static Context getAppContext() {
        return sContext;
    }

    private void registerAppCrashHandler() {
//        AppCrashHandler crashHandler = AppCrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }
}
