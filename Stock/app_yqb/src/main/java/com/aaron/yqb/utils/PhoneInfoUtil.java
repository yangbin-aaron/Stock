
/**
 * @Title: PhoneInfoUtil.java
 * @Package com.luckin.magnifier.utils
 * @Description: TODO
 * @ClassName: PhoneInfoUtil
 * @author 于泽坤
 * @date 2015-7-2 上午10:14:08
 */

package com.aaron.yqb.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.aaron.myviews.utils.AppInfoUtil;
import com.aaron.yqb.App;
import com.aaron.yqb.config.ApiConfig;
import com.umeng.message.UmengRegistrar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PhoneInfoUtil {

    private static final String TAG = "PhoneInfoUtil";

    /**
     * 请求的附加信息,手机型号、IMEI、系统版本、app版本、运营商等
     * @return List<NameValuePair>
     */
    public static List<NameValuePair> getRequestExtra() {
        List<NameValuePair> extra = new ArrayList<NameValuePair>();
        extra.add(new BasicNameValuePair("deviceModel", android.os.Build.MODEL));
        extra.add(new BasicNameValuePair("deviceImei", getDeviceId()));
        extra.add(new BasicNameValuePair("deviceVersion", android.os.Build.VERSION.RELEASE));
        extra.add(new BasicNameValuePair("clientVersion", AppInfoUtil.getVersionName(App.getAppContext())));
        extra.add(new BasicNameValuePair("regSource", AppInfoUtil.getChannelNumber(App.getAppContext(), AppInfoUtil.getChannelStr(App.getAppContext()))));
        extra.add(new BasicNameValuePair("operator", getIMSI()));
        extra.add(new BasicNameValuePair("version", ApiConfig.API_VERSION));
        extra.add(new BasicNameValuePair("systemName", "1"));
        return extra;
    }


    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) App.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号 //中国移动
                imsi = "中国移动";
            } else if (imsi.startsWith("46001")) {
                imsi = "中国联通";
            } else if (imsi.startsWith("46003")) {
                imsi = "中国电信";
            }
        } else {//避免空指针，传空
            imsi = "";
        }
        return imsi;
    }

    /**
     * @return 友盟获取设备的唯一id
     */
    public static String getDeviceId() {
        return getDeviceInfo(App.getAppContext());
    }

    public static String getUmengDeviceId() {
        Log.d(TAG, "getUmengDeviceId: " + UmengRegistrar.getRegistrationId(App.getAppContext()));
        return UmengRegistrar.getRegistrationId(App.getAppContext());
    }

    public static String getDeviceInfo(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String typePrefix = "DEVICE_ID_";
            String deviceId = tm.getDeviceId();

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = android.os.Build.SERIAL;
                typePrefix = "SERIAL_";
            }

            if (TextUtils.isEmpty(deviceId)) {
                android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String mac = wifi.getConnectionInfo().getMacAddress();
                deviceId = mac;
                typePrefix = "MAC_";
            }

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                typePrefix = "ANDROID_ID_";
            }

            return typePrefix + deviceId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
