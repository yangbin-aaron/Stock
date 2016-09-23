/**
 * 网络类型判断
 * 
 * @Title: NetInfoUtil.java
 * @Package com.luckin.magnifier.utils
 * @Description: TODO
 * @ClassName: NetInfoUtil
 * 
 * @author 于泽坤
 * @date 2015-7-2 下午2:02:37
 */

package com.aaron.myviews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


public class NetInfoUtil {

    public static final int NET_NONE = -1; // 无网络连接
    public static final int NET_WIFI = 1; // WIFI
    public static final int NET_2G = 2; // 2G
    public static final int NET_3G = 3; // 3G
    public static final int NET_4G = 4; // 4G

    public static int getCurrentNetType(Context context) {
        int netType = -1;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            netType = NET_NONE;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            netType = NET_WIFI;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                netType = NET_2G;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                netType = NET_3G;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                netType = NET_4G;
            }
        }
        return netType;
    }
    
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}
/**
1、NETWORK_TYPE_1xRTT： 常量值：7 网络类型：1xRTT

2、NETWORK_TYPE_CDMA ： 常量值：4 网络类型： CDMA （电信2g）

3、NETWORK_TYPE_EDGE： 常量值：2 网络类型：EDGE（移动2g）

4、NETWORK_TYPE_EHRPD： 常量值：14 网络类型：eHRPD

5、NETWORK_TYPE_EVDO_0： 常量值：5 网络类型：EVDO 版本0.（电信3g）

6、NETWORK_TYPE_EVDO_A： 常量值：6 网络类型：EVDO 版本A （电信3g）

7、NETWORK_TYPE_EVDO_B： 常量值：12 网络类型：EVDO 版本B（电信3g）

8、NETWORK_TYPE_GPRS： 常量值：1 网络类型：GPRS （联通2g）

9、NETWORK_TYPE_HSDPA： 常量值：8 网络类型：HSDPA（联通3g）

10、NETWORK_TYPE_HSPA： 常量值：10 网络类型：HSPA

11、NETWORK_TYPE_HSPAP： 常量值：15 网络类型：HSPA+

12、NETWORK_TYPE_HSUPA： 常量值：9 网络类型：HSUPA

13、NETWORK_TYPE_IDEN： 常量值：11 网络类型：iDen

14、NETWORK_TYPE_LTE： 常量值：13 网络类型：LTE(3g到4g的一个过渡，称为准4g)

15、NETWORK_TYPE_UMTS： 常量值：3 网络类型：UMTS（联通3g）

16、NETWORK_TYPE_UNKNOWN：常量值：0 网络类型：未知
*/