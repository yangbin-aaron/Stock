package com.aaron.myviews.config;

/**
 * 接口交互常量配置.
 */
public class RequestConfig {

    /**
     * 接口请求传往服务端的参数名
     */
    public static class RequestKey {
        public static final String TOKEN = "token"; //安全认证信息
        public static final String PHONE_NUMBER = "tele";
        public static final String SIGN = "sign";
        public static final String PASSWORD = "password";
        public static final String CODE = "code";
        public static final String AUTH_CODE = "authCode";
        public static final String NUMBER = "number";

        public static final String PAGE_SIZE = "pageSize";
        public static final String PAGE_INDEX = "pageNo";
        public static final String STATUS = "status";

        public static final String MONEY = "money";
        public static final String BANK_NUMBER = "bankNumber";
        public static final String ZFB_NUMBER = "zfbNumber";

        public static final String FUTURES_ID = "futuresId";
        public static final String TRADE_ID = "traderId";
        public static final String WARE_ID = "wareId";
    }

    /**
     * 接口返回的
     */
    public static class ResponseCode {

        public static final int OK = 200;//成功
        public static final int FORBIDDEN = 400;//访问被禁止
        public static final int PARAM_ERROR = 406;//参数错误
        public static final int REGISTERED = 407;//已注册
        public static final int NOT_REGISTERED = 408;//未注册
        public static final int COMMODITY_NOT_LOGIN = 6; // 南交所返回未登入

        /**
         * 提现(红包)失败错误码：实盘交易不够5手
         */
        public static final int NO_ENOUGH_5_TRADES = 43513;

        //0-90000 网络
        public static final int HTTP_SUCCESS = 200; //请求响应成功
        public static final int UNKNOWN_RESULT = 201; //结果正在处理中，并不返回期望的结果
        public static final int VISIT_FORBID = 400; //访问被禁止
        public static final int RESULT_CODE_SYSTEM_MAINTAIN = 401; //登录受限
        public static final int LOCATION_FAILED = 404; //资源定位失败
        public static final int RESULT_CODE_PARAM_ERROR = 406;
        public static final int RE_LOGIN = 41022; //用户在其它设备上登陆后，需要重新登陆
        //请求参数错误
        public static final int PHONE_REGISTERED = 407;
        public static final int PHONE_NOT_REG = 408;

        public static final int TOKEN_EXPIRE = 412; //会话过期
        public static final int REQUEST_PROCESS_FAILED = 500; //服务器请求处理失败
        public static final int SERVER_ERROR = 505; //服务器错误
        public static final int UNKOWN_ERROR = 999; //未知错误
        public static final int VERIFICATION_ERROR = 43001; //验证码
        public static final int FINANCY_NOENOUGH_ERROR = 44007; //资金账户余额不足
        public static final int PRICE_LIMIT = 44026; //涨跌幅限制
        public static final int A50_ONLY_SCORE = 49997; //期指暂时不支持现金
        public static final int NO_SUPPORT_MONEY_ERROR = 49999; //期指暂时不支持现金
        public static final int STOCK_NO_SUPPORT_MONEY = 49998; //需求暂时不支持现金
        public static final int BUY_PRICE_IS_ZERO = 44031; //用户提交买入价格为0或空
        public static final int QUOTATION_FLUCTUATION_HUGE = 44032; //行情波动大于等于4个点
    }

}
