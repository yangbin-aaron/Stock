package com.aaron.network;

/**
 * Intent配置常量.
 */
public class IntentConfig {

    /** Intent的Action常量(广播/服务/隐式Activity). */
    public static class Actions {
    //	public static final String ACTION_LOGIN_SUCCESS				= "com.action.login_success";		//登录成功
    //	public static final String ACTION_USER_EXIT					= "com.action.user_exit";			//注销 退出

        public static final String ACTION_UPDATE_PER_SECOND = "com.action.update_per_second";    //定时刷新每秒

    //	public static final String ACTION_SESSION_SUCCESS			= "com.action.session_success";		//session成功

        public static final String ACTION_NET = "android.net.conn.CONNECTIVITY_CHANGE";    //网络状态

        public static final String ACTION_RELOGIN = "com.magnifier.luckin.action.relogin";    //异地登陆

        public static final String ACTION_REGISTER_SUCCESS = "com.magnifier.luckin.action.register_success";//注册成功

        public static final String ACTION_REPORT_PROGRESS = "com.magnifier.luckin.action.relport_progress";//报告进度

        public static final String PAY_GIVE_UP = "PAY_GIVE_UP";

        public static final String COMMODITY_TOKEN_EXPIRE = "cash_commodity_token_expire";

        /** 更新头像Intent Action */
        public static final String ACTION_UPDATE_PORTRAIT = "com.luckin.magnifier.action.update_portrait";
    }

    /** Intent传递参数的key(包括Bundle/Argument等传递参数的键).*/
    public static class Keys {

        public static final String CLASS_NAME = "CLASS_NAME";

        /**手机号码*/
        public static final String PHONE_NUMBER = "PHONE_NUMBER";

        /*验证码*/
        public static final String AUTH_CODE = "AUTH_CODE";

        public static final String SHOW_PREV_LOGIN_NAME = "SHOW_PREV_LOGIN_NAME";

        /**是否允许返回*/
        public static final String ALLOW_BACK = "ALLOW_BACK";

        /**下一步是否进入主页*/
        public static final String NEXT_TO_MAIN_PAGE = "NEXT_TO_MAIN_PAGE";

        public static final String URL = "URL";
        public static final String TITLE = "TITLE";
        public static final String DATA = "DATA";
        public static final String BITMAP = "BITMAP";

        public static final String FILE_PATH_1 = "FILE_PATH_1";
        public static final String FILE_PATH_2 = "FILE_PATH_2";
        public static final String FILE_PATH_3 = "FILE_PATH_3";

        public static final String PROGRESS = "PROGRESS";
        public static final String TOTAL_LENGTH = "TOTAL_LENGTH";
        public static final String STATE = "STATE";
        public static final String MESSAGE = "MESSAGE";

        public static final String WITH_DRAW_RESULT = "WITH_DRAW_RESULT";

        public static final String UPDATE = "UPDATE";

        public static final String FROM = "FROM";

        public static final String POSITION = "position";

        public static final String REGISTER_SUCCESS = "REGISTER_SUCCESS";
        public static final String RESET_PASSWORD_SUCCESS = "RESET_PASSWORD_SUCCESS";

        public static final String BUNDLE = "BUNDLE";

        public static final String ORIENTATION = "ORIENTATION";
        public static final String STATUS = "STATUS";
        public static final String QUOTA_DATA = "QUOTA_DATA";
        public static final String COUNT = "COUNT";

        public static final String GO_TO_ACCOUNT = "GO_TO_ACCOUNT";
    }

}
