package com.aaron.myviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aaron.myviews.model.gson.NewsNoticeModel;
import com.aaron.myviews.model.newmodel.FuturesPayOrderData;
import com.aaron.myviews.utils.SecurityUtil;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;

public class AppPrefs {
    private static Context mContext;

    private static final String SHARED_PREFERENCES_NAME = "cn_prefs";

    private static final String BEGINNERS_GUIDE_VERSION = "2"; //新手引导版本，只在新手引导有更改后才升级

    private interface Key {
        String FIRST_START = "first_start";
        String USER_JSON = "user_json";
        String RUN_BACKGROUND = "run_background";
        String ENVIRONMENT = "environment";
        String ONLINE_ENVIRONMENT = "online_environment";
        String LOGIN_NAME = "loginName";
        String ADVERTISING = "advertising";
        String SERVER_TIME = "server_time";
        String CASH_COMMODITY = "cashCommodity";
        String COVERING_INSTALLATION = "CoveringInstallation";
        String HAD_OPENED_ABOUT_US = "hadOpenedAboutUs";
        String TIMESTAMP = "timestamp";
        String HOLIDAY_GAME_TIMESTAMP = "holiday_game_timestamp";
        String TRADE_RULE_FLASH = "trade_rule_flash";
    }

    private static AppPrefs sInstance;

    private SharedPreferences mPrefs;

    public static AppPrefs getInstance(Context context) {
        mContext = context;
        if (sInstance == null) {
            sInstance = new AppPrefs(context);
        }
        return sInstance;
    }

    private AppPrefs(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return mPrefs.edit();
    }

    public void setFirstStart(boolean isfirstStart) {
        getEditor().putBoolean(Key.FIRST_START, isfirstStart).commit();
    }

    public boolean getIsFirstStart() {
        return mPrefs.getBoolean(Key.FIRST_START, true);
    }

    public void setUserJson(String userJson) {
        getEditor().putString(Key.USER_JSON, userJson).commit();
    }

    public String getUserJson() {
        return mPrefs.getString(Key.USER_JSON, null);
    }

    public void setCashCommodityUser(String secret, String userJson) {
        getEditor().putString(secret + Key.CASH_COMMODITY, userJson).commit();
    }

    public String getCashCommodityUser(String secret) {
        return mPrefs.getString(secret + Key.CASH_COMMODITY, null);
    }

    public void setOnlineEnvironment(String onlineEnvironment) {
        getEditor().putString(Key.ONLINE_ENVIRONMENT, onlineEnvironment).commit();
    }

    public String getOnlineEnvironment() {
        return mPrefs.getString(Key.ONLINE_ENVIRONMENT, null);
    }

    public void setEnvironment(String environment) {
        getEditor().putString(Key.ENVIRONMENT, environment).commit();
    }

    public String getEnvironment() {
        return mPrefs.getString(Key.ENVIRONMENT, null);
    }

    public void setUserAgreeProtocol(String userSecret, String type) {
        String key = userSecret + "———>" + type;
        getEditor().putBoolean(key, true).commit();
    }

    public boolean isUserAgreeProtocol(String userSecret, String type) {
        String key = userSecret + "———>" + type;
        return mPrefs.getBoolean(key, false);
    }

    public void closePopWindows(NewsNoticeModel newsNoticeModel) {
        StringBuilder homeAdsBuilder = new StringBuilder();
        for (int i = 0; i < newsNoticeModel.getNewsNoticeList().size(); i++) {
            NewsNoticeModel.New newAds = newsNoticeModel.getNewsNoticeList().get(i);
            homeAdsBuilder.append(newAds.getMiddleBanner());
        }
        String homeAds = null;
        try {
            homeAds = SecurityUtil.md5Encrypt(homeAdsBuilder.toString());
            if (!TextUtils.isEmpty(homeAds)) {
                getEditor().putString(Key.ADVERTISING, homeAds).commit();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * true显示广告图片，false不显示广告图片
     *
     * @return
     */
    public boolean shouldShowPopWindows(NewsNoticeModel newsNoticeModel) {
        StringBuilder homeAdsBuilder = new StringBuilder();
        for (int i = 0; i < newsNoticeModel.getNewsNoticeList().size(); i++) {
            NewsNoticeModel.New newAds = newsNoticeModel.getNewsNoticeList().get(i);
            homeAdsBuilder.append(newAds.getMiddleBanner());
        }
        String homeAds = null;
        try {
            homeAds = SecurityUtil.md5Encrypt(homeAdsBuilder.toString());
            String localHomeAds = mPrefs.getString(Key.ADVERTISING, "");
            if (!TextUtils.isEmpty(localHomeAds)) {
                if (!homeAds.equals(localHomeAds)) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置应用是否运行在后台
     *
     * @param pageName
     * @param runBackground false 前台，true 后台
     * @param time
     */
    public void setRunBackground(String pageName, boolean runBackground, long time) {
        getEditor().putString(Key.RUN_BACKGROUND, pageName + ":" + runBackground + ":" + time).commit();
    }

    public String getRunBackgroundPageName() {
        String valueStringArray = mPrefs.getString(Key.RUN_BACKGROUND, null);
        if (!TextUtils.isEmpty(valueStringArray)) {
            return valueStringArray.split(":")[0].trim();
        }

        return "";
    }

    /**
     * @return true 应用在后台，false 应用在前台
     */
    public boolean isRunBackground() {
        String valueStringArray = mPrefs.getString(Key.RUN_BACKGROUND, null);
        if (!TextUtils.isEmpty(valueStringArray)) {
            return Boolean.parseBoolean(valueStringArray.split(":")[1].trim());
        }

        return true;
    }

    /**
     * 是否在后台大于3个小时
     *
     * @return false 小于3个小时，true 大于3个小时
     */
    public boolean isRunBackgroundThreeHour() {
        long threeHour = 1000 * 60 * 60 * 3;
        String valueString = mPrefs.getString(Key.RUN_BACKGROUND, null);
        if (!TextUtils.isEmpty(valueString)) {
            String[] arrayString = valueString.split(":");
            if (Boolean.parseBoolean(arrayString[1])) {
                Long timeDifference = System.currentTimeMillis() - Long.parseLong(arrayString[2]);
                if (timeDifference > threeHour) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否开启快速下单
     *
     * @param userSecret   用户唯一标识
     * @param instrumentID 合约id
     * @return
     */
    public boolean isQuickBuyEnable(int lobby, String userSecret, String instrumentID) {
        String key = userSecret + ":" + lobby + ":" + instrumentID + "-state";
        if (mPrefs.contains(key))
            return mPrefs.getBoolean(key, false);
        else
            return false;
    }

    /**
     * 开启闪电下单
     *
     * @param userSecret   用户唯一标识
     * @param payOrderData 合约id
     * @return 是否开启成功
     */
    public boolean enableQuickBuy(int lobby, String userSecret, FuturesPayOrderData payOrderData) {
        return saveQuickBuyData(lobby, userSecret, payOrderData);
    }

    /**
     * 禁用快速下单，如果存在才能禁用，不存在key禁用失败
     *
     * @param userSecret   用户唯一标识
     * @param instrumentID 合约id
     * @return 是否禁用成功
     */
    public boolean disableQuickBuy(int lobby, String userSecret, String instrumentID) {
        String key = userSecret + ":" + lobby + ":" + instrumentID + "-state";
        if (mPrefs.contains(key))
            return getEditor().remove(key).commit();
        else
            return false;
    }

    /**
     * 保存用户选中的待下单数据，把快速下单开关打开
     *
     * @param lobby        类型
     * @param userSecret   用户唯一标识
     * @param payOrderData 必须保证传入内部参数
     * @return 是否保存成功
     */
    private boolean saveQuickBuyData(int lobby, String userSecret, FuturesPayOrderData payOrderData) {
        if (TextUtils.isEmpty(userSecret) || payOrderData == null) return false;
        String dtaKey = userSecret + ":" + lobby + ":" + payOrderData.getFuturesCode() + "-data";
        //数据(data)保存成功，再去把开关(state)打开
        if (getEditor().putString(dtaKey, new Gson().toJson(payOrderData)).commit()) {
            String stateKey = userSecret + ":" + lobby + ":" + payOrderData.getFuturesCode() + "-state";
            return getEditor().putBoolean(stateKey, true).commit();
        }
        return false;
    }

    /**
     * 获取用户闪电下单数据
     *
     * @param userSecret   用户唯一标识
     * @param instrumentID 合约id
     * @return 返回的FuturesPayOrderData不包含下单时间，买入价和买入时间，需要自行设置
     */
    public FuturesPayOrderData getQuickBuyData(int lobby, String userSecret, String instrumentID) {
        if (TextUtils.isEmpty(userSecret) || TextUtils.isEmpty(instrumentID)) return null;
        String key = userSecret + ":" + lobby + ":" + instrumentID + "-data";
        if (mPrefs.contains(key)) {
            String quickBuyData = mPrefs.getString(key, null);
            if (quickBuyData != null) {
                return new Gson().fromJson(quickBuyData, FuturesPayOrderData.class);
            }
        }
        return null;
    }

    public void saveLoginName(String loginName) {
        getEditor().putString(Key.LOGIN_NAME, loginName).commit();
    }

    /**
     * 获取之前登录过的登录名
     *
     * @return
     */
    public String getPrevLoginName() {
        return mPrefs.getString(Key.LOGIN_NAME, null);
    }

    public void setHasShowedBeginnersGuide(String userSecret, String pageName, boolean value) {
        // key: userSecret:pageName
        // value: version:value

        String key = userSecret + ":" + pageName;
        String valueStr = BEGINNERS_GUIDE_VERSION + ":" + value;
        getEditor().putString(key, valueStr).commit();
    }

    public boolean hasShowedBeginnersGuide(String userSecret, String pageName) {
        String key = userSecret + ":" + pageName;
        if (mPrefs.contains(key)) {
            String valueStrArray[] = mPrefs.getString(key, "").split(":");
            if (valueStrArray[0].equals(BEGINNERS_GUIDE_VERSION)) {
                return Boolean.parseBoolean(valueStrArray[1]);
            }
        }
        return false;
    }

    public void setServerTime(String serviceTime) {
        getEditor().putString(Key.SERVER_TIME, serviceTime).commit();
    }

    public String getServerTime() {
        return mPrefs.getString(Key.SERVER_TIME, null);
    }

//    public void setLightningOperCommodity(String userSecret,String instrumentID,Boolean isChecked) {
//        String key = userSecret + ":" + instrumentID;
//        getEditor().putBoolean(key, isChecked).commit();
//    }
//
//    public Boolean getLightningOperCommodity(String userSecret,String instrumentID) {
//        String key = userSecret + ":" + instrumentID;
//        return mPrefs.getBoolean(key,false);
//    }

    /**
     * 覆盖安装app
     *
     * @param value
     */
    public void setCoveringInstallation(boolean value) {
        getEditor().putBoolean(Key.COVERING_INSTALLATION, value).commit();
    }

    public Boolean getCoveringInstallation() {
        return mPrefs.getBoolean(Key.COVERING_INSTALLATION, false);
    }

    public void setHadOpenedAboutUs(boolean hadOpenedAboutUs) {
        getEditor().putBoolean(Key.HAD_OPENED_ABOUT_US, hadOpenedAboutUs).commit();
    }

    public boolean hadOpenedAboutUs() {
        return mPrefs.getBoolean(Key.HAD_OPENED_ABOUT_US, false);
    }

    public long getTimestamp(String userSecret) {
        return mPrefs.getLong(Key.TIMESTAMP + userSecret, 0);
    }

    public void setTimestamp(String userSecret, long timestamp) {
        getEditor().putLong(Key.TIMESTAMP + userSecret, timestamp).commit();
    }

    public long getHolidayGamePopupTimestamp() {
        return mPrefs.getLong(Key.HOLIDAY_GAME_TIMESTAMP, 0);
    }

    public void setHolidayGamePopupTimestamp(long timestamp) {
        getEditor().putLong(Key.HOLIDAY_GAME_TIMESTAMP, timestamp).commit();
    }

    public boolean isTradeRuleFlash(String instrumentCode, int lobbyType) {
        String key = Key.TRADE_RULE_FLASH + instrumentCode + lobbyType;
        return mPrefs.getBoolean(key, true);
    }

    public void disableTradeRuleFlash(String instrumentCode, int lobbyType) {
        String key = Key.TRADE_RULE_FLASH + instrumentCode + lobbyType;
        getEditor().putBoolean(key, false).commit();
    }
}
