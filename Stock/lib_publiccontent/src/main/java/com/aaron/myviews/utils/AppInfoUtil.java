package com.aaron.myviews.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.aaron.myviews.model.newmodel.local.Channel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AppInfoUtil {

    public static String getChannelNumber(Context context,String channelStr) {
        String channelJson = Storage.getInstance(context).readFromAssets("channel");
        if (!TextUtils.isEmpty(channelJson) && !TextUtils.isEmpty(channelStr)) {
            List<Channel> channelList = new Gson().fromJson(channelJson,
                    new TypeToken<List<Channel>>() {}.getType());
            for (int i = 0; i < channelList.size(); i++) {
                Channel channel = channelList.get(i);
                if (channelStr.equals(channel.getChannelStr())) {
                    return channel.getChannelNumber();
                }
            }
        }
        return "";
    }

    public static String getChannelStr(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return (appInfo.metaData.get("UMENG_CHANNEL")).toString();
        }  catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {

        }
        return versionName;
    }

}
