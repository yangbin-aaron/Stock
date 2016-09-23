package com.aaron.yqb.config;

import android.text.TextUtils;

import com.aaron.yqb.model.local.Environment;

/**
 * API接口
 * @author bvin
 */
public class ApiConfig {

    public static final String SCHEME = "http://";

    public static String HOST = Environment.getEnvironment().getDomain();

    /**获取最新接口host*/
    public static String getHost() {
        return SCHEME + HOST;
    }

    public static String getFullUrl(String url) {
        return getHost() + url;
    }

    public static String getFullQuotaUrl(String instrumentCode) {
        return SCHEME + HOST + "/futurequota/" + instrumentCode + ".fst";
    }

    public static String getTradeRuleUrl(String instrumentCode) {
        return SCHEME + HOST + "/activity/" + instrumentCode + "TradeRule.html";
    }

    public static String transformRelativeUrl(String relativeUrl) {
        if (!TextUtils.isEmpty(relativeUrl) && relativeUrl.indexOf("..") >= 0) {
            int startIndex = relativeUrl.indexOf("/activity");
            return getFullUrl(relativeUrl.substring(startIndex));
        }
        return "";
    }

    // URL-Path
    public static final String PATH_RULE = "/rule";
    public static final String PATH_USER = "/user";
    public static final String PATH_FINANCY = "/financy";
    public static final String PATH_MARKET = "/market";
    public static final String PATH_ORDER = "/order";
    public static final String PATH_SMS = "/sms";
    public static final String PATH_COMMODITY = "/cots";

    public static final String API_VERSION = "0.0.1";

    /**默认起始页码*/
    public static final int DEFAULT_PAGE_INDEX_START = 1;
    /**默认一页显示10条*/
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**一页最大条数*/
    public static final int MAX_PAGE_SIZE = 10000;

    public static class ApiURL{

        // rule/...
        public static String RULE_RECHARGE_PROBLEM =PATH_RULE + "/question.html";
        /**玩法介绍,沪深A股资费标准*/
        public static String RULE_WANFA = PATH_RULE + "/wanfa.html";

        /**
         * 获取资费标准接口url
         * @param instrumentCode eg. au\ag\if
         * @return eg. /rule/aumCost.html
         */
        public static String getRuleWanfaUrl(String instrumentCode) {
            return PATH_RULE + "/" + instrumentCode + "mCost.html";
        }
        /**交易合作协议, 沪深A股合作协议*/
        public static String RULE_COOPERATION = PATH_RULE + "/t1.html";

        /**
         *
         * @param instrumentCode eg. au\ag\if
         * @return eg. /rule/aumProtocol.html
         */
//        public static String getRuleCooperationUrl(String instrumentCode) {
//            return PATH_RULE + "/" + instrumentCode + "mProtocol.html";
//        }

        /**充值服务协议*/
        public static String RULE_RECHARGE = PATH_RULE + "/recharge.html";
        /**T+1参与规则*/
        public static String RULE_T1 = PATH_RULE + "/t1Rule.html";
        /**财牛服务协议*/
        public static final String RULE_AGREEMENT = PATH_RULE + "/agreement.html";

        /**推广员*/
        public static String PROMOTE_SHARE = getHost() + "/m/hd0907.html";

        public static String COUPON_RULES = "/activity/coupon_rules.html";

        public static String SHARE_PATH = "http://www.100zjgl.com/m/promotion.html";

        // user/..
        public static final String USER_LOGIN = PATH_USER + "/login";//登录
        public static final String OPEN_ID_LOGIN = PATH_USER + "/openIDLogin";//第三方登录
        public static final String USER_REGISTER = PATH_USER + "/register"; // 注册
        public static final String USER_TOKEN = PATH_USER + "/token"; // 会话保持
        public static final String USER_APPLY_FUTURES = PATH_USER + "/account/getApply"; // 用户是否申请过期货开户

        // user/sys/...
        public static final String GET_SERVER_DOMAIN = PATH_USER + "/sys/getUrl"; //获取服务器地址
        public static final String SYSTEM_TIME = PATH_USER + "/sysTime"; // 获取服务器时间
        public static final String SERVER_DOMAIN = PATH_USER + "/sys/getUrl"; // 获取服务器地址
        public static final String VIEW_DISPLAY = PATH_USER + "/sys/display"; // 某些控件是否显示接口

        // user/sms/...
        public static final String GET_REG_CODE = PATH_USER + "/sms/getRegCode"; // 发送注册短信验证码
        public static final String AUTH_REG_CODE = PATH_USER + "/sms/authRegCode"; // 验证注册短信验证码
        public static final String FIND_LOGIN_PWD_CODE = PATH_USER + "/sms/findLoginPwdCode"; // 找回登录密码验证码
        public static final String AUTH_LOGIN_PWD_CODE = PATH_USER + "/sms/authLoginPwdCode"; // 验证登录密码验证码
        public static final String SEND_BIND_TEL_CODE = "/user/sms/sendBindTele";//获取绑定手机验证码

        // user/user/...
        public static final String FIND_LOGIN_PWD = PATH_USER + "/user/findLoginPwd"; // 找回登录密码-修改用户登录密码
        public static final String UPDATE_LOGIN_PASSWORD = PATH_USER + "/user/updLoginPwd";
        public static final String USER_CHECK = PATH_USER + "/user/check"; // 打开环境切换页面前的密码验证
        public static final String UPDATE_USER_NICK = PATH_USER + "/user/updUserNick"; // 修改昵称
        public static final String UPDATE_USER_SIGN = PATH_USER + "/user/updPersonalSign"; // 修改个性签名
        public static final String AUTH_BIND_TEL_CODE = "/user/sms/checkBindTeleCode";//校验绑定手机验证码
        public static final String AUPDATE_USER_PHOTO = PATH_USER+"/user/updPhoto";//更改头像

        /**验证手机号是否绑定*/
        public static String CHECK_TEL = PATH_USER + "/user/checkTele";
        /**验证是否实名认证*/
        public static String CHECK_USER_NAME = PATH_USER + "/user/checkUserName";
        /**验证是否绑定银行卡*/
        public static String CHECK_BANK_CARD = PATH_USER + "/user/checkBankCard";
        /**检验身份认证*/
        public static String CHECK_IDENTITY_INFO = PATH_USER + "/user/checkIdCardPic";
        /**上传身份认证照片*/
        public static String UPLOAD_IDENTITY_IMAGE = PATH_USER + "/audit/updateImg";

        public static final String  BIND_TEL = PATH_USER + "/user/bindTele"; // 绑定手机
        public static String AUTH_USER = PATH_USER + "/user/authUser"; // 实名认证
        public static String BIND_BANK = PATH_USER + "/user/bindBank"; // 绑定银行卡
        public static String UPDATE_BANK = PATH_USER + "/user/updatebank";
        /**完善银行卡*/
        public static String PERFECT_BANK = PATH_USER + "/user/perfectBank";

        /**根据银行卡号查询银行名称*/
        public static String FIND_BANK_NAME = PATH_USER + "/user/findBankName";
        /**查询支行列表*/
        public static String FIND_BRANCH_LIST = PATH_USER + "/user/findBranchList";

        /**账户开户列表信息*/
        public final static String USER_GETACCOUNT_INFO = PATH_USER + "/account/getAccountsInfo";

        /**开启模拟交易账户*/
        public static String USER_ACCOUNT_OPEN_SCORE = PATH_USER + "/account/openScoreAcc";

        /**快钱支持银行列表*/
        public static String USER_BANK_SELECTALL = PATH_USER + "/bank/selectAll";

        // user/favorites/...
        public static String FAV_STOCK_LIST = PATH_USER + "/favorites/favoritesList";
        public static String ADD_FAV_STOCK = PATH_USER + "/favorites/addFavorites";
        public static String DEL_FAV_STOCK = PATH_USER + "/favorites/deleteFavorites";

        // user/message/...
        public static String QUERY_MESSAGE_LIST = PATH_USER + "/message/queryMessageList"; //消息列表

        // user/newsNotice/...
        public static String NEWS_LIST = PATH_USER + "/newsNotice/newsList";//获取公告图接口链接地址
        //public static String NEWS_IMG_LIST = PATH_USER + "/newsNotice/newsImgList";//获取公告图片

        // user/adv/...
        public static String URL_ADV_IMG = PATH_USER + "/adv/imgAddr";//获取广告图片

        // user/feedback/...
        public static String FEEDBACK = PATH_USER + "/feedback/saveFeedbackMessage";//反馈

        // user/device/...
        public static final String CHECK_STAFF_DEVICE = PATH_USER + "/device/checkStaffDevice"; //检查是否为员工设备
        public static final String CHECK_STAFF_PASSWORD = PATH_USER + "/device/checkPwd"; // 验证后台密码

        // user/coupon/...
        public static final String  COUPON_LIST = PATH_USER + "/coupon/userCouponList";//优惠券列表
        public static final String  AVAILABLE_COUPON_LIST = PATH_USER + "/coupon/availableCouponList";//可用优惠券列表
        public static final String  AVAILABLE_COUPONS_COUNT = PATH_USER + "/coupon/usefulCouponCount";//可用优惠券数量
        public static final String  USED_AND_EXPIRED_COUPON_LIST = PATH_USER + "/coupon/usedAndExpiredCouponList";//使用过和过期优惠券列表
        public static final String  BIND_COUPON = PATH_USER + "/coupon/bindCoupon";//兑换抵用券

        // user/newsArticle
        public static final String NEWS_INFORMATION = PATH_USER + "/newsArticle/newsList";

        // financy/financy/...
        public static final String FINANCY_MAIN = PATH_FINANCY + "/financy/apiFinancyMain";//账户资金

        public static String FINANCY_FLOW_LIST = PATH_FINANCY + "/financy/apiFinancyFlowList"; //资金流水列表
        public static String SCORE_FINANCY_FLOW_LIST = PATH_FINANCY + "/financy/apiScoreFinancyFlowList";//积分流水列表

        public static String GET_STOCK_TRADER_LIST = PATH_FINANCY + "/financy/getStockTraderList";
        public static String GET_USER_STOCK_TRADER_LIST = PATH_FINANCY + "financy/getUserStockTraderList";//获取个人操盘配置信息

        public static String WITH_DRAW = PATH_FINANCY + "/financy/apiWithdraw";//用户提现申请
        public static String WITH_DRAW_DONE_DATE = PATH_FINANCY + "/financy/getWithdrawDoneDate";//到账时间
        public static final String WITH_DRAW_COUNT = PATH_FINANCY + "/financy/getWithdrawCount";//用户提现次数
        public static final String TODAY_WITH_DRAW_COUNT = PATH_FINANCY + "/financy/getTodayWithdrawCount";//当日用户提现次数
        public static final String TRADE_COUNT = PATH_FINANCY + "/financy/isTraderOrNot";//交易次数

        public static String TRANSFER_ACCOUNTS = PATH_FINANCY + "/financy/getZfbBankNumberInfo";//转账账号记录
        public static String ZFB_TRANSFER = PATH_FINANCY + "/financy/zfbTransfer";//支付宝转账
        public static String BANK_TRANSFER = PATH_FINANCY + "/financy/bankTransfer";//银行卡转账

        // financy/topup/...
        public static String GET_DYN_NUM = PATH_FINANCY + "/topup/getDynNum";//充值验证码
        public static String PAY = PATH_FINANCY + "/topup/pay";//充值
        /**快钱支付*/
        public static String RECHARGE_WAY_KUAIQIAN = PATH_FINANCY + "/topup/top";//快钱充值

        // financy/fs/...
        public static final String FINANCE_STATISTICS = PATH_FINANCY  + "/fs/financyStatistics"; // 平台金融数据
        public static final String PROFIT_REPORT_LIST = PATH_FINANCY + "/fs/profitStatisticsList"; // 持仓页面播报


        // market/market/...
        public static final String GET_MARKET_INFO = PATH_MARKET + "/market/marketStatus"; //获取市场状态
        public static final String FUTURES_STATUS = PATH_MARKET + "/market/futuresStatus"; //期货状态查询
        public static final String STOCK_STATUS = PATH_MARKET + "/market/stockStatus"; //股票可售状态查询
        public static final String FUTURES_MARKET_STATUS = PATH_MARKET + "/market/getFuturesStatus"; //首页期货状态列表
        public static final String HOLDING_TIME = PATH_MARKET + "/market/getHoldTimeLast"; // 持仓时间至
        public static final String IS_HOLIDAY = PATH_MARKET + "/market/isHoliday"; //是否是节假日

        // market
        public static final String PRODUCT_LIST = PATH_MARKET + "/futureCommodity/select";
        public static final String NEW_PRODUCT_LIST = PATH_MARKET + "/futureCommodity/selectByType";

        // order/api/...
        public static final String URL_ORDER_API_TRADER = PATH_ORDER + "/api/trader"; // 股票操盘配置信息
        public static final String FUTURES_POSITION_COUNT = PATH_ORDER + "/posiOrderCount";          //用户持仓订单数量

        // order/order/...
        public static String BUY = PATH_ORDER + "/order/buy"; // 下单/购买股票
        public static String ORDER_STATUS = PATH_ORDER + "/order/orderStatus"; // 查看订单/股票持仓状态
        public static String SALE= PATH_ORDER + "/order/sale"; // 股票卖出
        public static String RESULT_SALE = PATH_ORDER + "/order/resultsale"; // 股票售出结果查询
        public static String POSI_DETAIL = PATH_ORDER + "/order/posiDetail";
        public static String CURRENT_ORDER_LIST = PATH_ORDER + "/order/currentOrderList";//个人持仓
        public static String HOT_PROFIT_RANK = PATH_ORDER + "/order/hotProfitRank";//收益排行
        public static String POSI_ORDER_DETAIL = PATH_ORDER + "/order/posiOrderDetail";//持仓某只股票详情
        public static String OTHER_POSI = PATH_ORDER + "/order/otherPosi";// 他人持仓列表
        public static String MARKET_VALUE = PATH_ORDER + "/order/marketValue";// 现金持仓市值

        public static String FROZEN_AMT = PATH_ORDER + "/order/frozenAmt";// 冻结资金
        public static String FROZEN_SCORE = PATH_ORDER + "/order/frozenScore";// 冻结积分
        public static String TOTAL_PROFILT = PATH_ORDER + "/order/moniTotalProfit"; //当前总盈利

        public static String SCORE_ORDER_LIST = PATH_ORDER + "/order/curScoreOrderList";// 积分交易记录-持有中
        public static String SCORE_FINISH_ORDER_LIST = PATH_ORDER + "/order/finishScoreOrderList";// 积分交易记录-已结算

        public static String CASH_SCORE_ORDER_LIST = PATH_ORDER + "/order/curCashOrderList";// 交易记录-持有中
        public static String CASH_FINISH_ORDER_LIST = PATH_ORDER + "/order/finishCashOrderList";// 交易记录-已结算

        // order/futures/...
        public static final String FUTURES_BUY = PATH_ORDER + "/futures/buy"; // 期货买入
        public static final String FUTURES_ORDER_STATUS = PATH_ORDER + "/futures/orderStatus"; // 查看期货订单状态
        public static final String FUTURES_ORDER_LIST = PATH_ORDER + "/futures/balancedList"; // 用户期货订单结算列表
        public static final String FUTURES_SALE = PATH_ORDER + "/futures/sale"; //用户出售期货订单
        public static final String FUTURES_SALE_RESULT = PATH_ORDER + "/futures/result";  //期货交易结果轮询
        public static final String ORDER_POSILIST = PATH_ORDER + "/futures/posiList";       //期货持仓订单列表

        /**
         * 持仓订单卖出
         */
        public static final String FUTURES_SELL_ORDER = PATH_ORDER + "/futures/sale";
        public static final String FUTURES_POLL_RESULT = PATH_ORDER + "/futures/result"; //期货交易结果轮询

        /** 持仓直播 */
        public static final String FUTURES_ORDER_GET_POSITION = PATH_ORDER + "/position/getPosition";

        // sms/message/...
        public static String TRADER_MESSAGES = PATH_SMS + "/message/traderMassages"; // 交易提醒

        // futuresquota/
        public static final String FUTURES_QUOTA_SERVER = "/futuresquota/getQuotaUrl"; // 行情服务端口获取接口
        public static final String REAL_TIME_STRATEGY = "/futuresquota/tradeStrategy"; // 实时策略
        public static final String KLINE_DATA = "/futuresquota/list"; // k线数据获取，参数type&time(yyyyMMddHHmmss)
        public static final String KLINE_DISPLAY = "/futuresquota/display"; //k线显示
        public static final String PRODUCT_QUOTATION = "/futuresquota/getCacheData"; //不同产品行情缓存
        public static final String FIVE_LEVEL_QUOTA = "/futuresquota/getNewCotsData"; //现货5档行情
        public static final String QUOTATION_DATA = "/futuresquota/getQuotaData"; // 盘口数据

        //cainiu:goto
        public static String KUAIQIAN_RECHARGE_SUC = "cainiu:gotoMyAccount"; //快钱充值成功跳转URL
        public static String TASK_CENTER_REDIRECT_HALL = "cainiu:gotoHall";     //任务中心领取红包达不到条件则回到大厅
        public static String ADS_TASK_CENTER = "cainiu:gotoTaskCenter";     //从广告页跳转到任务中心

        /**热门推荐股票*/
        public static String HOT_RECOMMEND_STOCKS = "http://cainiu.oss-cn-hangzhou.aliyuncs.com/cainiu/stock.txt";

        //体现校验密码
        public static String CHECK_PWD = "/user/user/checkPwd";

        public static String FEED_BACK_REPLY = "/user/feedback/readFeedback";           //意见反馈回复列表

        public static String FEED_BACK_IS_READ = "/user/feedback/isRead";       //是否有未读的意见反馈

        public static String FEED_BACK_UPDATE_IS_READ = "/user/feedback/updateIsRead";  //将意见反馈标记为已读

        public static String WITHDRAWALS_RECORD = "/financy/financy/getWithdrawHistory";    //用户提现记录

        public static String WITHDRAW_HISTORY_DETAIL = "/financy/financy/getWithdrawHistoryDetail";

        public static String CANCLE_WITHDRAW = "/financy/financy/cancleWithdraw";  //用户体现撤回

        public static String FOUND_PROMOTE_STATUS = "/promotion/getPromoteStatus";  //是否是推广员

        public static String ROMOTE_REASON = "/promotion/app/promote/apply"; //推广员申请

        public static String ROMOTE_DETAIL = "/promotion/getPromoteDetail";  //推广员详细

        public static String COMMISSION_WATER = "/financy/commissions/getCommissionsToCashHistory"; //佣金流水

        public static String COMMISSION_TO_CASH = "/financy/commissions/transferCommissionsToCash";//佣金转现

        public static String PROMOTE_SUB_USERS = "/promotion/getPromoteSubUsers"; //推广员推广详细

        public static String TRANSACTION = "/rule/rule_transaction.html"; //用户交易合作协议
        public static String COST = "/rule/rule_cost.html"; //财牛用户参与期货交易合作涉及费用及资费标准

        /**
         * 现货持仓交易查询
         */
        public final static String QUERY_SINGLE_POSITION = PATH_COMMODITY + "/cots/querySinglePosition";

        // h5
        public static final String HELP_CENTER = "/activity/helpCenter.html"; //帮助中心
        public static final String TASK_MANAGE = "/activity/taskManage/display"; //任务中心，是否显示红包
        public static final String TASK_MANAGE_AWARD = "/activity/award.html"; //任务中心，点击后详情h5页面
        public static final String ABOUTUS ="/activity/aboutus.html"; //关于我们钱多多
        public static final String TRADE_COST = "/activity/tradeAndCost.html";     // 交易和费用协议签署
        public static final String FOUND_ACTIVITY = "/activity/hdCenter.html";    //发现活动中心
        public static final String CUSTOMER_SERVICE = "/activity/service.html";//客服中心
        public static final String INFORMATION_DETAIL = "/activity/newsDtl.html"; //资讯详情
        public static final String KNOW_ABOUT_US = "/activity/aboutus.html"; // 了解财牛
        public static final String SAHRE_FOR_GIFT = "/activity/spread.html"; // 分享有礼

        // h5 Cash commodity 现货
        public static final String CASH_COMMODITY_REGISTER = "/activity/register.html"; // 现货注册
        public static final String EXCHANGE_INFORMATION = "/activity/njsInfor.html"; // 交易所信息
        public static final String CASH_COMMODITY_LOGIN = "/activity/njsLogin.html"; // 现货登入
        public static final String CASH_COMMODITY_DEPOSIT = "/activity/moneyIn.html"; // 现货入金
        public static final String CASH_COMMODITY_WITHDRAW = "/activity/moneyOut.html"; // 现货出金
        public static final String CASH_COMMODITY_TRANSFER = "/activity/transRecord.html"; // 转账记录

        // h5 Futures 期货
        public static final String FUTURES_EXCHANGE_INFORMATION = "/activity/openAccount.html"; // 期货交易所信息
        // h5 财牛开户
        public static final String CAINIU_ACCOUNT_ACTIVATE = "/account/openAccount.html";

        public static final String ACCOUNT_BIND = "/account/bindAccount.html";
        public static final String ACCOUNT_RECHARGE = "/account/banks.html"; //资金不足去下单，弹出提示去充值，点击充值后跳转至H5充值界面

        // Native Cash commodity
        public static final String BULLISH_BEARISH_REVOKE = PATH_COMMODITY + "/cots/bullishBearishRevoke";  //止盈，止损撤单

        /**
         * 闪电平仓
         */
        public static final String SALE_ORDER = PATH_COMMODITY + "/sale/saleOrder";

        /**
         * 现货：委托单状态查询
         */
        public static final String ENTRUST_CASH_ORDER = PATH_COMMODITY + "/cots/entrustCashOrder";

        /**
         * 现货：持仓列表用户委托单查询
         */
        public static final String QUERY_ENTRUST_LIST = PATH_COMMODITY + "/cots/newEntrustList";
        public static final String BARGAIN_LIST = PATH_COMMODITY + "/cots/bargainList"; //成交订单列表
        public static final String ENTRUST_LIST = PATH_COMMODITY + "/cots/entrustList"; //委托订单列表
        public static final String ENTRUST_REVOKE = PATH_COMMODITY + "/cots/entrustRevoke"; //委托订单撤单接口
        public static final String STOP_LOSS_PROFIT_LIST = PATH_COMMODITY + "/cots/upDownList"; //止盈止损列表
        public static final String STOP_LOSS_PROFIT_REVOKE = PATH_COMMODITY + "/cots/bullishBearishRevoke"; //止盈止损撤单
        public static final String COMMODITY_POSITIONS_COUNT = PATH_COMMODITY + "/cots/cotsPositionCount"; // 现货持仓数量
        public static final String COMMODITY_QUERY_FUND = PATH_COMMODITY + "/cots/queryFund"; //查询期货资金

        /**
         * 现货：查询单个品种信息
         */
        public static final String COMMODITY_QUERY_INFO = PATH_COMMODITY + "/cots/querySingleInfo";

        public static final String COMMODITY_PAY_MARKET = PATH_COMMODITY + "/cots/queryMarketEntrust";//现货市价下单
        public static final String COMMODITY_PAY_LIMIT = PATH_COMMODITY + "/cots/queryLimitEntrust";//现货限价下单
        public static final String COMMODITY_PAY_SET_STOP = PATH_COMMODITY + "/cots/bullishBearishEntrust";//止盈止损下单
        public static final String COMMODITY_TRADE_COUNT = PATH_COMMODITY + "/cots/getEntrustAvailble"; //最大可下单手数
        public static final String COMMODITY_LOGIN = PATH_COMMODITY + "/cots/tradeLogin"; //现货登入，token刷新
        public static final String YI_DU_PAY_CONFIRM = PATH_COMMODITY + "/pay/yiDuPayConfirm"; // 异度支付结果通知后台

        public static final String SYS_VERSION = "/market/sys/getVersion"; // 强制升级


        public static final String PREPARE_INFO_HFB = "/financy/hfbpay/payOrder";// 汇付宝支付预备信息获取
    }
}
