package com.aaron.yqb.network.http;


import com.aaron.yqb.model.local.Environment;

public class HttpConfig {
	public static final String HTTP_HEAD_STR					= "http://";

	public static String SERVER_DOMAIN = Environment.getEnvironment().getDomain(); //服务器域名

	public static final class HttpSendResult{
		public static final int HTTP_SEND_SUCCESS 				= 1;
		public static final int HTTP_SEND_FAILED 				= 2;
	}
	
	public static final class HttpVerifyType {
		public static final int VERIFY_TYPE_REG					= 1;
		public static final int VERIFY_TYPE_RESET_PWD			= 2;
	}
	
	public static final class HttpPort {
		public static final String STOCK_FINANCY				= "";//":19085";
		public static final String STOCK_USER					= "";//":19083";
		public static final String STOCK_ORDER					= "";//":19086";
		public static final String STOCK_TRADE					= "";//":19087";
		public static final String STOCK_TASK					= "";//":19084";
		public static final String STOCK_MARKET					= "";//":19088";
	}

	public class HttpURL{
		public static final String URL_USER_LOGIN               = HttpPort.STOCK_USER + "/user/login"; // 登录
		public static final String URL_USER_TOKEN				= HttpPort.STOCK_USER+"/user/token";					//会话保持
		public static final String URL_USER_SYSTIME           	= HttpPort.STOCK_USER+"/user/sysTime";                  //获取服务器时间
		
		public static final String URL_USER_REGISTER			= HttpPort.STOCK_USER+"/user/register";					//注册
		public static final String URL_USER_GET_REG_CODE		= HttpPort.STOCK_USER+"/user/sms/getRegCode";				//发送注册短信验证码
		public static final String URL_USER_AUTH_REG_CODE		= HttpPort.STOCK_USER+"/user/sms/authRegCode";			//注册短信验证码验证

		public static final String URL_USER_FIND_LOGIN_PWD_CODE	= HttpPort.STOCK_USER+"/user/sms/findLoginPwdCode";				//找回登录密码验证码
		public static final String URL_USER_AUTH_LOGIN_PWD_CODE	= HttpPort.STOCK_USER+"/user/sms/authLoginPwdCode";				//找回登录密码验证码
		public static final String URL_USER_FIND_LOGIN_PWD		= HttpPort.STOCK_USER+"/user/user/findLoginPwd";					//找回登录密码-修改用户登录密码
		
		public static final String URL_USER_RESET_PWD_CODE		= HttpPort.STOCK_USER+"/user/user/updLoginPwd";					//修改密码
		
		public static final String URL_ORDER_API_TRADER			= HttpPort.STOCK_ORDER+"/order/api/trader";						//股票操盘配置信息
		public static final String URL_ORDER_BUY				= HttpPort.STOCK_ORDER+"/order/order/buy";						//下单/购买股票
		public static final String URL_ORDER_RESULT_BUY			= HttpPort.STOCK_ORDER+"/order/order/orderStatus";				//查看订单/股票持仓状态
		public static final String URL_ORDER_SALE				= HttpPort.STOCK_ORDER+"/order/order/sale";						//股票卖出
		public static final String URL_ORDER_RESULT_SALE		= HttpPort.STOCK_ORDER+"/order/order/resultsale";				//股票售出结果查询
		public static final String URL_ORDER_POSIDETAIL         = HttpPort.STOCK_ORDER+"/order/order/posiDetail";
		
		public static final String URL_FINANCY_MAIN				= "/financy/financy/apiFinancyMain";							//用户资金账户
		public static final String URL_FINANCY_FLOW_LIST		= HttpPort.STOCK_FINANCY+"/financy/financy/apiFinancyFlowList";	//资金流水列表
		
		public static final String URL_STOCK_TASK_TEDIS_STOCKPOSI = HttpPort.STOCK_TASK + "/order/order/currentOrderList";				//个人持仓
		
		public static final String URL_ORDER_BENEFIT_RANK		= HttpPort.STOCK_TASK+"/order/order/hotProfitRank";				//收益排行
	    public static final String URL_STOCK_TASK_REDIS_POSIORDERDETAIL = HttpPort.STOCK_TASK+"/order/order/posiOrderDetail";	//持仓某只股票详情
	
	    public static final String URL_USER_CHANGE_NICK			= "/user/user/updUserNick"; 				//修改昵称
	    
	    public static final String URL_USER_CHANGE_SIGNATURE	= "/user/user/updPersonalSign";				//修改个性签名
		public static final String URL_USER_CHANGE_PHOTO	= "/user/user/updPhoto";				//修改头像

	    public static final String URL_USER_CHECK_TELE			= "/user/user/checkTele"; 			//验证手机号是否绑定
	    public static final String URL_USER_CHECK_USERNAME		= "/user/user/checkUserName"; 		// 验证是否实名认证
	    
	    public static final String URL_USER_CHECK_BANK_CARD		= "/user/user/checkBankCard";		//验证银行卡是否绑定
	    
		public static final String URL_USER_AUTH_USER			= "/user/user/authUser"; 			// 实名认证

		public static final String URL_USER_FIND_BRANCH_LIST	= "/user/user/findBranchList";		// 查询支行列表
		
		public static final String URL_USER_BIND_BANK			= "/user/user/bindBank";			// 绑定银行卡
		public static final String URL_USER_UPDATE_BANK         = "/user/user/updatebank";
		
		
		public static final String URL_USER_PERFECT_BANK		= "/user/user/perfectBank";			// 完善银行卡
		
		public static final String URL_USER_SEND_BIND_TELE_CODE	= "/user/sms/sendBindTele";			// 获取修改手机验证码
		
		public static final String URL_FINANCY_TOPUP_GET_DYN_NUM= "/financy/topup/getDynNum";		//充值验证码
		public static final String URL_FINANCY_TOPUP_PAY		= "/financy/topup/pay";				//充值
		
		public static final String URL_FINANCY_WITH_DRAW		= "/financy/financy/apiWithdraw";	//用户提现申请
		
		public static final String URL_ORDER_FINISH_CASH_LIST	= "/order/order/finishCashOrderList";	//交易记录-已结算
		public static final String URL_ORDER_FINISH_SCORE_CASH_LIST = "/order/order/finishScoreOrderList";   //积分交易记录-已结算
		
		public static final String URL_SCORE_FINANCY_FLOW_LIST	= "/financy/financy/apiScoreFinancyFlowList";	//积分流水列表
		
		public static final String URL_SCORE_FINISH_ORDER_LIST	= "/order/order/finishScoreOrderList";			//积分交易记录-已结算
		
		public static final String URL_OPEN_LOGIN				= "/user/openIDLogin";							//第三方登录
		
        public static final String GET_MARKET_INFO = "/market/market/marketStatus"; //获取市场状态
	
        public static final String GET_USER_STOCK_TRADER_LIST =  "/financy/financy/getUserStockTraderList";//获取个人操盘配置信息
        
        public static final String GET_STOCK_TRADER_LIST = "/financy/financy/getStockTraderList";

        public static final String GET_ORDER_POSILIST="/order/futures/posiList";       //期货持仓订单列表
        
        public static final String URL_GOLD_UNWIND = "/order/futures/sale";            //沪金平仓
        
        public static final String URL_FUTURES_RESULT = "/order/futures/result";            //期货交易结果轮询

		public static final String GET_MESSAGECOUNT = "/sms/message/messageCount";		//消息中心页面，返回各种消息的未读数量
		
		public static final String GET_SYSTEM_MESSAGES = "/sms/message/systemMessages";		//系统消息列表
		public static final String GET_SYSTEM_MESSAGES_INFO = "/sms/message/systemMessageInfo";		//系统消息详情
		
		public static final String SET_USER_UPDATEUMCODE = "/user/user/updateUmCode";		//更新友盟设备号
		
		public static final String PROMOTEID_BY_USERID = "/promotion/getPromoteIdByToken";	//获取个人邀请码
		
		public static final String PROMOTEST = "/promotion/getPromoteSt";		//用户推广信息（推广人数，下线消费金额）
		
		public static final String USER_TASK_STATUS = "/financy/financy/getUserTaskStatus";		//获取用户完成的完成状态
		
		public static final String Friend_Total_Consume = "/financy/financy/friendTotalConsume";	//推荐好友送现金
	}

}


