package com.hundsun.quote.tools;

public class QuoteConstants {

	//排名类型
	public static final int PAIXU_ZHANGFU = 1;
	public static final int PAIXU_DIEFU = 2;
	public static final int PAIXU_JINE = 3;
	public static final int PAIXU_LIANGBI = 4;
	public static final int PAIXU_ZHENFU = 5;
	public static final int PAIXU_XIANJIA = 6;
	public static final int PAIXU_WEIBI = 7;
	public static final int PAIXU_CODE = 20;
	public static final int PAIXU_CHENGJIAOLIANG = 100;
	// --------------------------------------------------------------------------
	// 股票
	public static final short MARKET_STOCK = 0X1000;

	public static final short MARKET_BLOCKINDEX = (short) 0XA000;

	public static final short BOURSE_SHENWAN = 0x0100;

	// 上海
	public static final short BOURSE_STOCK_SH = 0x1100;
	// 深圳
	public static final short BOURSE_STOCK_SZ = 0x1200;
	//板块
	public static final short BOURSE_STOCK_BK = 0x1300;
	//系统板块
	public static final short BOURSE_STOCK_SYSBK = 0x1400;
	// 自定义（自选股或者自定义板块）
	public static final short BOURSE_STOCK_USERDEF = 0x1800;

	// 指数
	public static final short KIND_INDEX = 0x0000;
	// A股
	public static final short KIND_STOCKA = 0x0001;
	// B股
	public static final short KIND_STOCKB = 0x0002;
	// 债券
	public static final short KIND_BOND = 0x0003;
	// 基金
	public static final short KIND_FUND = 0x0004;
	// 三板
	public static final short KIND_THREEBOAD = 0x0005;
	// 中小盘股
	public static final short KIND_SMALLSTOCK = 0x0006;
	// 配售
	public static final short KIND_PLACE = 0x0007;
	// LOF
	public static final short KIND_LOF = 0x0008;
	// ETF
	public static final short KIND_ETF = 0x0009;
	// 权证
	public static final short KIND_QuanZhen = 0x000A;
	// 开放基金
	public static final short KIND_OPENFUND = 0x000B;
	// 创业板
	public static final short KIND_CYB = 0x000D;
	// 第三方行情分类，如:中信指数
	public static final short KIND_OtherIndex = 0x000E;
	// 其他 0x09
	public static final short SC_Others = 0x000F;
	// 自定义指数
	public static final short KIND_USERDEFINE = 0x0010;

	// 上海A
	public static final short BOURSE_STOCK_SH_A = 0x1101;
	// 上海B
	public static final short BOURSE_STOCK_SH_B = 0x1102;
	// 深圳A
	public static final short BOURSE_STOCK_SZ_A = 0x1201;
	// 深圳B
	public static final short BOURSE_STOCK_SZ_B = 0x1202;

	// --------------------------------------------------------------------------
	public static final int MARKET_HK = 0x2000; // 港股分类

	// 主板市场
	public static final short BOURSE_HKMAIN = 0x2100;
	// 创业板市场(Growth Enterprise Market)
	public static final short BOURSE_HKGE = 0x2200;
	// 指数市场
	public static final short BOURSE_HKINDEX = 0x2300;
	//港股板块(H股指数成份股，恒生指数成份股）。
	public static final short BOURSE_HK_SYSBK = 0x2400;
	// 自定义（自选股或者自定义板块）
	public static final short BOURSE_HK_USERDEF = 0x2800;

	// 港指
	public static final short HK_KIND_INDEX	= 0x0000;
	// 期指
	public static final short HK_KIND_FUTURES_INDEX	= 0x0001;
	// 港股期权
	//		public static final short HK_KIND_Option = 0x0002;

	// 预留
	public static final short HK_KIND_RES = 0x0000;
	// 债券
	public static final short HK_KIND_BOND = 0x0001;
	// 一篮子衍生权证
	public static final short HK_KIND_BWRT = 0x0002;
	// 公司股票
	public static final short HK_KIND_EQTY = 0x0003;
	// 信托
	public static final short HK_KIND_TRST = 0x0004;
	// 权证
	public static final short HK_KIND_WRNT = 0x0005;
	// ETS
	public static final short HK_KIND_ETS = 0x0006;
	// NADS
	public static final short HK_KIND_NADS = 0x0007;
	// 旅游
	public static final short HK_KIND_LY = 0x0008;
	// 工业
	public static final short HK_KIND_GY = 0x0009;
	// 公用
	public static final short HK_KIND_GG = 0x000A;
	// 其它
	public static final short HK_KIND_QT = 0x000F;


	// --------------------------------------------------------------------------
	// 文交所
	public static final short MARKET_CAE = 0x3000; //文交所
	public static final short MARKET_CAE_CHINA = 0x3300;//中华文交所

	// --------------------------------------------------------------------------
	// 期货
	public static final int MARKET_FUTURES = 0x4000; // 期货

	public static final short BOURSE_FUTURES_DALIAN = 0x4100; // 大连
	public static final short KIND_BEAN = 0x0001; // 连豆
	public static final short KIND_YUMI = 0x0002; // 大连玉米
	public static final short KIND_SHIT = 0x0003; // 大宗食糖
	public static final short KIND_DZGY = 0x0004; // 大宗工业1
	public static final short KIND_DZGY2 = 0x0005; // 大宗工业2
	public static final short KIND_DOUYOU = 0x0006; // 豆油
	public static final short KIND_JYX = 0x0007; // 聚乙烯
	public static final short KIND_ZLY = 0x0008; // 棕榈油

	public static final short BOURSE_FUTURES_SH = 0x4200; // 上海
	public static final short KIND_METAL = 0x0001; // 上海金属
	public static final short KIND_RUBBER = 0x0002; // 上海橡胶
	public static final short KIND_FUEL = 0x0003; // 上海能源
	//		public static final short KIND_GUZHI = 0x0004;	// 股指期货
	public static final short KIND_GOLD = 0x0005; // 黄金期货

	public static final short BOURSE_FUTURES_ZHZHOU = 0x4300; // 郑州
	public static final short KIND_XIAOM = 0x0001; // 郑州小麦
	public static final short KIND_MIANH = 0x0002; // 郑州棉花
	public static final short KIND_BAITANG = 0x0003; // 郑州白糖
	public static final short KIND_PTA = 0x0004; // 郑州PTA
	public static final short KIND_CZY = 0x0005; // 菜籽油

	public static final short BOURSE_HUANGJIN = 0x4400;	// 黄金交易所
	public static final short KIND_HJ_GOLD = 0x0001;	// 上海黄金

	public static final short BOURSE_FUTURES_ZHJIN = 0x4500; // 中金
	public static final short KIND_GUZHI = 0x0001; // 股指期货
	public static final short KIND_GZ_BOND = 0x0004; //国债期货
	// --------------------------------------------------------------------------

	// 外盘 请求类型
	public static final int MARKET_WP = 0x5000; // 外盘
	public static final short BOURSE_WP_GLOBAL_INDEX = 0x5100;//外盘全球指数
	public static final short BOURSE_WP_GLOBAL_INDEX_LBOUND = 0x5100;//外盘全球指数上分界线
	public static final short BOURSE_WP_GLOBAL_INDEX_UBOUND = 0x52FF;//外盘全球指数下分界线
	public static final short BOURSE_WP_HSZS = 0x5110; //沪深指数
	public static final short BOURSE_WP_GZ = 0x5120;// 港指
	public static final short BOURSE_WP_TZ = 0x5130;//台指
	public static final short BOURSE_WP_HGZS = 0x5140;//韩国指数
	public static final short BOURSE_WP_TGZS = 0x5150;//泰国指数
	public static final short BOURSE_WP_AZZS = 0x5160;//澳洲指数
	public static final short BOURSE_WP_FLBZS = 0x5170;// 菲利宾指数
	public static final short BOURSE_WP_XJPHXSBZS = 0x5180;// 新加坡海峡时报指数
	public static final short BOURSE_WP_MLXYZS = 0x5190;//马来西亚指数
	public static final short BOURSE_WP_RJZS = 0x51A0;//日经指数
	public static final short BOURSE_WP_YNZS = 0x51B0;// 印尼指数
	public static final short BOURSE_WP_MZ = 0x51C0;//美指
	public static final short BOURSE_WP_LDZS = 0x51D0;//伦敦指数
	public static final short BOURSE_WP_DGZS = 0x51E0;// 德国指数
	public static final short BOURSE_WP_BLZS = 0x51F0;//巴黎指数
	public static final short BOURSE_WP_HLAEXZS = 0x5210;//荷兰AEX指数

	public static final short BOURSE_WP_FRGNFUTURES_LBOUND = 0x5300; //外盘期货上分界线
	public static final short BOURSE_WP_FRGNFUTURES_UBOUND = 0x5400; //外盘期货下分界线
	public static final short WP_FRGNFUTURES_CME = 0x0310; // CME
	public static final short WP_FRGNFUTURES_ICE = 0x0320; // ICE
	public static final short WP_FRGNFUTURES_IDEM = 0x0330; // IDEM
	public static final short WP_FRGNFUTURES_LIFFE = 0x0340; // Liffe
	public static final short WP_FRGNFUTURES_LME = 0x0350; // LME
	public static final short WP_FRGNFUTURES_MEFF = 0x0360; // Meff
	public static final short WP_FRGNFUTURES_MONTREAL = 0x0370; // Montreal
	public static final short WP_FRGNFUTURES_NYBOT = 0x0380; // NYBOT
	public static final short WP_FRGNFUTURES_OSE = 0x0390; // OSE
	public static final short WP_FRGNFUTURES_SFE = 0x03a0; // SFE
	public static final short WP_FRGNFUTURES_SGX = 0x03b0; // SGX
	public static final short WP_FRGNFUTURES_CME_E$ = 0x03c0; // CME_E$
	public static final short WP_FRGNFUTURES_TIFFE = 0x03d0; // Tiffe
	public static final short WP_FRGNFUTURES_TOCOM = 0x03e0; // TOCOM
	public static final short WP_FRGNFUTURES_TSE = 0x03f0; // TSE
	public static final short WP_FRGNFUTURES_CURRENEX = 0x0400; // Currenex
	public static final short WP_FRGNFUTURES_DGCX = 0x0410; // DGCX
	public static final short WP_FRGNFUTURES_DME = 0x0420; // DME
	public static final short WP_FRGNFUTURES_ECBOT = 0x0430; // eCBOT
	public static final short WP_FRGNFUTURES_ENEXTVF = 0x0440; // ENextVF
	public static final short WP_FRGNFUTURES_EUREX = 0x0450; // Eurex
	public static final short WP_FRGNFUTURES_GAIN_FX = 0x0460; // GAIN FX
	//---------------------------------------------------------------------

	//商品
	public static final int MARKET_COMMODITIES = 0x6000;		///商品
	public static final short BOURSE_SGE = 0x0100;		///黄金白银
	//-------------------------------------------------------------------
	// 股票期权
	public static final short MARKET_OPTION = 0X7000;
	// 股票期权,上海市场
	public static final short BOURSE_OPTION_SH = 0X7100;
	// 股票期权，深圳市场
	public static final short BOURSE_OPTION_SZ = 0X7200;
	//-------------------------------------------------------------------
	public static final int MARKET_FOREIGN = 0x8000; // 外汇
	public static final short WH_BASE_RATE = 0x0100;	// 基本汇率
	public static final short WH_ACROSS_RATE = 0x0200;	// 交叉汇率
	public static final short FX_TYPE_AU = 0x0000; // AU	澳元
	public static final short FX_TYPE_CA = 0x0001; // CA	加元
	public static final short FX_TYPE_CN = 0x0002; // CN	人民币
	public static final short FX_TYPE_DM  = 0x0003; // DM	马克
	public static final short FX_TYPE_ER = 0x0004; // ER	欧元	 
	public static final short FX_TYPE_HK = 0x0005; // HK	港币
	public static final short FX_TYPE_SF = 0x0006; // SF	瑞士 
	public static final short FX_TYPE_UK = 0x0007; // UK	英镑
	public static final short FX_TYPE_YN = 0x0008; // YN	日元
	public static final short WH_FUTURES_RATE = 0x0300;  // 期汇
	//-------------------------------------------------------------------


	public static final int MARKET_FUND = 0x9000; // 基金 add by chenhl

	// --------------------------------------------------------------------------
	public static final int RT_COMPASKDATA = 0x8FFE; /* 组合请求类型 */
	public static final int RT_ZIPDATA = 0x8001; /* 压缩返回包类型 */

	public static final int RT_JAVA_MARK = 0x0010; /* JAVA登录 | RT_LOGIN_* */
	public static final int RT_WINCE_MARK = 0x0020; /* WINCE登录 | RT_LOGIN_* */
	public static final int RT_NOTZIP = 0x0040; /* 不使用压缩数据传输 */

	public static final int RT_INITIALINFO = 0x0101; /* 客户端数据所有初始化 */
	public static final int RT_LOGIN = 0x0102; /* 客户端登录行情服务器 */
	public static final int RT_SERVERINFO = 0x0103; /* 主站信息 */
	public static final int RT_BULLETIN = 0x0104; /* 紧急公告(主推) */
	public static final int RT_PARTINITIALINFO = 0x0105; /* 客户端数据部分初始化 */
	public static final int RT_ETF_INIT = 0x0106; /* 52只股票数据(ETF) */
	public static final int RT_LOGIN_HK = 0x0107; /* 客户端登录港股服务器 */
	public static final int RT_LOGIN_FUTURES = 0x0108; /* 客户端登录期货服务器 */
	public static final int RT_LOGIN_FOREIGN = 0x0109; /* 客户端登录外汇服务器 */
	public static final int RT_LOGIN_WP = 0x010A; /* 客户端登录外盘服务器 */
	public static final int RT_LOGIN_INFO = 0x010B; /* 客户端登录资讯服务器 */
	public static final int RT_CHANGE_PWD = 0x010C; /* 修改密码 */
	public static final int RT_SERVERINFO2 = 0x010E; /* 主站信息2 */
	public static final int RT_LOGIN_FOREIGN_FUTURES = 0x0121;	/* 外盘期货登录 */
	public static final int RT_INC_INITIALINFO = 0x0132; /* 外盘期货初始化主推 */

	public static final int RT_REALTIME_EXT = 0x020F;
	public static final int RT_REALTIME = 0x0201; // 行情报价表:1-6乾隆操作键
	public static final int RT_DYNREPORT = 0x0202; // 强弱分析;指标排序;热门板块分析;区间分析;跑马灯股票列表数据;预警
	public static final int RT_REPORTSORT = 0x0203; // 排名报价表:61-66、点击列排序
	public static final int RT_GENERALSORT = 0x0204; // 综合排名报表:81-86
	public static final int RT_GENERALSORT_EX = 0x0205; // 综合排名报表:81-86加入自定义分钟排名
	public static final int RT_SEVER_EMPTY = 0x0206; // 服务器无数据返回空包
	public static final int RT_SEVER_CALCULATE = 0x0207; // 服务器计算数据包,包括涨停、跌停
	public static final int RT_ANS_BYTYPE = 0x0208; // 根据类型返回数据包
	public static final int RT_QHMM_REALTIME = 0x0209; // 期货买卖盘
	public static final int RT_LEVEL_REALTIME = 0x020A; // level

	public static final int RT_TREND_EXT = 0x030B;
	public static final int RT_TREND = 0x0301; /* 分时走势 */
	public static final int RT_ADDTREND = 0x0302; /* 走势图叠加、多股同列 */
	public static final int RT_BUYSELLPOWER = 0x0303; /* 买卖力道 */
	public static final int RT_HISTREND = 0x0304; /* 历史回忆;多日分时;右小图下分时走势 */
	public static final int RT_TICK = 0x0305; /* TICK图 */
	public static final int RT_ETF_TREND = 0x0306; /* ETF分时走势 */
	public static final int RT_ETF_NOWDATA = 0x0307; /* ETF时时数据 */
	public static final int RT_ETF_TREND_TECH = 0x0308; /* ETFtech分时走势 */
	public static final int RT_HISTREND_INDEX = 0x0309; /*
	 * 对于大盘领先-历史回忆;多日分时;右小图下分时走势
	 */

	// public static final int RT_TECHDATA = 0x0400; /* 盘后分析 */
	public static final int RT_FILEDOWNLOAD = 0x0401; /* 文件请求（盘后数据下载） */
	public static final int RT_TECHDATA_EX = 0x0402; /* 盘后分析扩展 -- 支持基金净值 */
	public static final int RT_DATA_WEIHU = 0x0403; /* 数据维护处理 */
	public static final int RT_FILEDOWNLOAD2 = 0x0404; /* 下载服务器指定目录文件 */
	public static final int RT_FILED_CFG = 0x0405; /* 配置文件升级/更新 */
	public static final int RT_FILL_DATA = 0x0406; /* 补线处理 */
	public static final int RT_FIELD = 0x040f; /* 指定参数*/
	public static final int RT_TECHDATA_BIGINT = 0x0411;/*大K线*/

	public static final int RT_TEXTDATAWITHINDEX_PLUS = 0x0501; /* 正序资讯索引数据 */
	public static final int RT_TEXTDATAWITHINDEX_NEGATIVE = 0x0502; /* 倒序资讯索引数据 */
	public static final int RT_BYINDEXRETDATA = 0x0503; /* 资讯内容数据 */
	public static final int RT_USERTEXTDATA = 0x0504; /* 自定义资讯请求（如菜单等） */
	public static final int RT_FILEREQUEST = 0x0505; /* 配置文件文件 */
	public static final int RT_FILESimplify = 0x0506; /* 精简文件请求 */
	public static final int RT_ATTATCHDATA = 0x0507; // 附件数据

	public static final int RT_STOCKTICK = 0x0601; /* 个股分笔、个股详细的分笔数据 */
	public static final int RT_BUYSELLORDER = 0x0602; /* 个股买卖盘 */
	public static final int RT_LIMITTICK = 0x0603; /* 指定长度的分笔请求 */
	public static final int RT_HISTORYTICK = 0x0604; /* 历史的分笔请求 */
	public static final int RT_MAJORINDEXTICK = 0x0605; /* 大盘明细 */
	public static final int RT_VALUE = 0x0606; /* 右小图“值” */
	public static final int RT_BUYSELLORDER_HK = 0x0607; /* 个股买卖盘(港股） */
	public static final int RT_BUYSELLORDER_FUTURES = 0x0608; /* 个股买卖盘(期货） */
	public static final int RT_VALUE_HK = 0x0609; /* 右小图“值”(港股),右小图也发此请求 */
	public static final int RT_VALUE_FUTURES = 0x060A; /* 右小图“值”(期货),左下小图也发此请求 */
	public static final int RT_TOTAL = 0x060B; /* 总持请求包 */
	public final static short RT_DEAL_DETAIL = 0x060C; /* 成交明细功能号*/
	public static final int RT_CURRDAY_STOCKTICK = 0x0612; /* 当天成交明细按时间点查询 */ 


	public static final int RT_LEAD = 0x0702; /* 大盘领先指标 */
	public static final int RT_MAJORINDEXTREND = 0x0703; /* 大盘走势 */
	public static final int RT_MAJORINDEXADL = 0x0704; /* 大盘走势－ADL */
	public static final int RT_MAJORINDEXDBBI = 0x0705; /* 大盘走势－多空指标 */
	public static final int RT_MAJORINDEXBUYSELL = 0x0706; /* 大盘走势－买卖力道 */

	public static final int RT_CURRENTFINANCEDATA = 0x0801; /* 最新的财务数据 */
	public static final int RT_HISFINANCEDATA = 0x0802; /* 历史财务数据 */
	public static final int RT_EXRIGHT_DATA = 0x0803; /* 除权数据 */
	public static final int RT_HK_RECORDOPTION = 0x0804; /* 港股期权 */
	public static final int RT_BROKER_HK = 0x0805; /* 港股经纪席位下委托情况 */// 看我们的服务器是否生成此数据
	public static final int RT_BLOCK_DATA = 0x0806; /* 板块数据 */
	public static final int RT_STATIC_HK = 0x0807; /* 港股静态数据 */

	public static final int RT_MASSDATA = 0x0901; /* 大单 */
	public static final int RT_SERVERTIME = 0x0902; /* 服务器当前时间 */
	public static final int RT_KEEPACTIVE = 0x0903; /* 保活通信包 */
	public static final int RT_TEST = 0x0904; /* 测试通信包 */
	public static final int RT_TESTSRV = 0x0905; /* 测试客户端到服务器是否通畅 */

	public static final int RT_AUTOPUSH_EXT = 0x0A0F;
	public static final int RT_AUTOPUSH = 0x0A01; /* 常用主推 */// 改RealTimeData 为

	// 退市整理板块/ 风险板块 代码请求
	public static final short RT_ASSIST_QUERY = 0x0218;

	// CommRealTimeData
	public static final int RT_AUTOPUSHSIMP = 0x0A02; /* 精简主推 */// 改为请求主推
	public static final int RT_REQAUTOPUSH = 0x0A03; /* 请求主推,应用于：预警、跑马灯 */// 改RealTimeData
	// 为
	// CommRealTimeData
	public static final int RT_ETF_AUTOPUSH = 0x0A04; /* ETF主推 */
	public static final int RT_AUTOBROKER_HK = 0x0A05; /* 经纪主推 */
	public static final int RT_AUTOTICK_HK = 0x0A06; /* 港股分笔主推 */
	public static final int RT_AUTOPUSH_QH = 0x0A07; /* 期货最小主推 */
	public static final int RT_PUSHREALTIMEINFO = 0x0A08; /* 实时解盘主推 */
	public static final int RT_RAW_AUTOPUSH = 0x0A09; /* 数据源原始数据主推 */

	public static final int RT_QHMM_AUTOPUSH = 0x0A0A; /* 期货买卖盘主推 */
	public static final int RT_LEVEL_AUTOPUSH = 0x0A0B; /* level主推 */

	public static final int RT_UPDATEDFINANCIALDATA = 0x0B01; /* 增量的财务报表数据 */
	public static final int RT_SYNCHRONIZATIONDATA = 0x0B02; /* 数据同步处理 */

	//
	public static final int RT_Send_Notice = 0x0C01; // 发表公告
	public static final int RT_Send_ScrollText = 0x0C02; // 发表滚动信息
	public static final int RT_Change_program = 0x0C03; // 更改服务器程序
	public static final int RT_Send_File_Data = 0x0C04; // 发送文件到服务器

	public static final int RT_INFODATA_TRANSMIT = 0x0C0A; //

	// wince 相关
	public static final int RT_WINCE_FIND = 0x0E01; // 查找代码
	public static final int RT_WINCE_UPDATE = 0x0E02; // CE版本升级
	public static final int RT_WINCE_ZIXUN = 0x0E03; // CE资讯请求

	public static final int RT_NoteMsgData = 0x0C09; /* 定制短信数据传送 */

	public static final int RT_ShouPanDone = 0x0D01; // 收盘数据下载

	public static final int RT_Srv_SrvStatus = 0x0F01; // 后台程序运行状态

	/* 精简初始化 */
	public final static short RT_SIMPLE_INITINFO = 0x040A;

	/* 精简昨收数据 */
	public static final short RT_SIMPLE_STOCKINFO = 0X040B;

	/* 精简财务数据请求 */
	public static final short RT_SIMPLE_FINANCE = 0x040C;
	public static final int RT_GENERAL_SORT = 0x138B; // 综合排名
	public static final int RT_SIMPLE_AH = 0x138D; // AH比较
	public static final int RT_TECHDATA_SEARCH = 0x138F; // 查询指定时间段K线

	/* 资金流相关 */
	public static final int RT_DDE_QUERY = 0x1390;// 大单买入净量占流通盘的比率  5008
	public static final int RT_MACS_SOCT = 0x1391;//MACS综合排名接口 5009
	public static final int RT_INVEST_DAY = 0X1392;//量眼看盘 5010
	public static final int RT_MACS_BLOCK = 0x1393;//MACS板块行情数据获取（可以获取相关行情与板块信息） 5011
	public static final int RT_MACS_STOCK_BLOCK = 0x1394;//MACS个股关联板块 5012

	/* 简化除权数据 */
	public static final int RT_SIMPLE_XR = 0x040D;//简化除权数据 1036

	// --------------------------------------------------------------------------

	public static final int YLS_HXTRANSTYPE_ = 0x0001; // 11
	public static final int YLS_HANDSOMETRANSTYPE_ = 0x0002; // 254
	public static final int YLS_HANDSOMESOURCETRANS_ = 0x0004; // 255
	public static final int YLS_CURTYPE = 0x0008; // 10

	public static final int YLS_NotSendPacketTest = 0x0100; // 不发送数据包，测试通讯
	public static final int YLS_DFXComm = 0x0200; // 1.5大福星通讯
	public static final int YLS_TestConnect = 0x0400; // 测试connect
	public static final int YLS_User_Pwd = 0x0800; // 使用通讯内部用户名和密码

	public static final int YLS_NotUser = 0x1000; // 不检测用户
	public static final int YLS_NotZip = 0x2000; // 不发送时不做压缩处理
	public static final int YLS_RawRecSend = 0x4000; // 原始接收发送
	public static final int YLS_HttpRecSend = 0x8000; // HTTP发送接收

	public static final int YLS_RecRaw = 0x0010; // 接收数据原始发送
	public static final int YLS_RecDataWriteFile = 0x0020; // 接收数据写入到文件
	public static final int YLS_SendLoginData = 0x0040; // 发送登陆数据

	// 当前的主站设置
	public static final int YLS_OPEN_LOGIN = 0x0001; // 登录
	public static final int YLS_OPEN_SET = 0x0002; // 行情设置
	public static final int YLS_OPEN_REFRESH = 0x0004; // 刷新配置文件

	// 公告信息配置
	public static final byte Notice_Option_WinCE = 0x0001; // 公告信息只对WinCE用户//
	public static final byte Notice_Option_SaveSrv = 0x0002; // 公告信息在服务器自动保存。
	public static final byte Login_Option_Password = 0x0004; // 登陆时使用新的加密方式。
	public static final byte Login_Option_NotCheck = 0x0008; // 不检测用户。

	/* K线请求的周期类型 BEGIN */

	public static final short PERIOD_TYPE_DAY = 0x0010; // 分析周期：日
	public static final short PERIOD_TYPE_MINUTE1 = 0x00C0; // 分析周期：1分钟
	public static final short PERIOD_TYPE_MINUTE5 = 0x0030; // 分析周期：5分钟
	public static final short PERIOD_TYPE_MINUTE15 = 0x0040;
	public static final short PERIOD_TYPE_MINUTE30 = 0x0050;
	public static final short PERIOD_TYPE_MINUTE60 = 0x0060;
	public static final short PERIOD_TYPE_MINUTE120 = 0x0070;
	public static final short PERIOD_TYPE_WEEK = 0x0080;
	public static final short PERIOD_TYPE_MONTH = 0x0090;

	// 服务器配置信息
	public static final String SECTION_SPECIAL_PATH = "资讯路径-特色资讯";

	public static final String INFO_PATH_KEY_F10_SH = "上证F10资讯路径"; // F10资讯
	public static final String INFO_PATH_KEY_F10_SZ = "深证F10资讯路径"; // F10资讯
	public static final String INFO_PATH_KEY_F10_QH = "期货F10资讯路径"; // F10资讯
	public static final String INFO_PATH_KEY_TREND_SH = "上证分时资讯路径"; // 分时资讯
	public static final String INFO_PATH_KEY_TREND_SZ = "深证分时资讯路径"; // 分时资讯
	public static final String INFO_PATH_KEY_TECH_SH = "上证盘后资讯路径"; // 盘后资讯
	public static final String INFO_PATH_KEY_TECH_SZ = "深证盘后资讯路径"; // 盘后资讯

	// exchange_type 交易类别
	public static final String EXCHANGE_TYPE_UNDEFINED = "0"; // 前台未知交易所
	public static final String EXCHANGE_TYPE_SHANGHAI = "1"; // 上海
	public static final String EXCHANGE_TYPE_SHENZHEN = "2"; // 深圳
	public static final String EXCHANGE_TYPE_CYB = "2"; // 创业板
	public static final String EXCHANGE_TYPE_SHANGHAI_B = "D"; // 上海B
	public static final String EXCHANGE_TYPE_SHENZHEN_B = "H"; // 深圳B

	// 控制常量
	public static final boolean needAnalysis = true;

	/**************排序常量*******************/
	//升序/降序标识，1：升序 0：降序 
	public final static int COLUMN_BEGIN = 10000;//全部行情-列定义开始值
	public final static int COLUMN_END = COLUMN_BEGIN + 999;//全部行情-列定义结束值
	public final static int COLUMN_HQ_BASE_BEGIN = COLUMN_BEGIN; //基础行情-列定义开始值
	public final static int COLUMN_HQ_BASE_END = COLUMN_HQ_BASE_BEGIN + 100;//基础行情-列定义结束值
	public final static int COLUMN_HQ_EX_BEGIN = COLUMN_HQ_BASE_END + 1;//扩展行情-列定义开始值
	public final static int COLUMN_HQ_EX_END = COLUMN_HQ_EX_BEGIN + 50;//扩展行情-列定义结束值
	public final static int COLUMN_FUTURES_BEGIN = COLUMN_HQ_EX_END + 1;//期货行情-列定义开始值
	public final static int COLUMN_FUTURES_END = COLUMN_FUTURES_BEGIN + 50;//期货行情-列定义结束值
	public final static int COLUMN_INTERBANK_BEGIN = COLUMN_FUTURES_END + 1;//银行间债券-列定义开始值
	public final static int COLUMN_INTERBANK_END = COLUMN_INTERBANK_BEGIN + 50;//银行间债券-列定义结束值
	public final static int COLUMN_CAE_BEGIN = COLUMN_INTERBANK_END + 1;//文交所-列定义开始值
	public final static int COLUMN_CAE_END = COLUMN_CAE_BEGIN + 50;//文交所-列定义结束值
	public final static int COLUMN_FINANCIAL_STATEMENT_BEGIN = 50000;//财务数据-列定义开始值
	public final static int COLUMN_FINANCIAL_STATEMENT_END = COLUMN_FINANCIAL_STATEMENT_BEGIN + 999;//财务数据-列定义结束值

	//可排序行情字段定义
	public final static int COLUMN_HQ_BASE_NAME = COLUMN_HQ_BASE_BEGIN + 47; // 股票名称,Macs提供
	public final static int COLUMN_HQ_BASE_CODE = COLUMN_HQ_BASE_BEGIN + 58; // 股票代码,Macs提供
	public final static int COLUMN_HQ_BASE_OPEN = COLUMN_HQ_BASE_BEGIN + 48; // 开盘价格
	public final static int COLUMN_HQ_BASE_NEW_PRICE = COLUMN_HQ_BASE_BEGIN + 49; // 成交价格
	public final static int COLUMN_HQ_BASE_RISE_VALUE = COLUMN_HQ_BASE_BEGIN + 50; // 涨跌值
	public final static int COLUMN_HQ_BASE_TOTAL_HAND = COLUMN_HQ_BASE_BEGIN + 51; // 总手
	public final static int COLUMN_HQ_BASE_HAND = COLUMN_HQ_BASE_BEGIN + 52; // 现手
	public final static int COLUMN_HQ_BASE_MAX_PRICE = COLUMN_HQ_BASE_BEGIN + 53; // 最高价格
	public final static int COLUMN_HQ_BASE_MIN_PRICE = COLUMN_HQ_BASE_BEGIN + 54; // 最低价格
	public final static int COLUMN_HQ_BASE_RISE_RATIO = COLUMN_HQ_BASE_BEGIN + 57; // 涨跌幅
	public final static int COLUMN_HQ_BASE_PRECLOSE = COLUMN_HQ_BASE_BEGIN + 59; // 昨收
	public final static int COLUMN_HQ_BASE_VOLUME_RATIO = COLUMN_HQ_BASE_BEGIN + 60; // 量比
	public final static int COLUMN_HQ_BASE_BUY_PRICE = COLUMN_HQ_BASE_BEGIN + 55; // 买入价格，即委买价，买1价，相关之间等同
	public final static int COLUMN_HQ_BASE_ORDER_BUY_PRICE = COLUMN_HQ_BASE_BEGIN + 61; // 委买价，与COLUMN_HQ_BASE_BUY_PRICE等同
	public final static int COLUMN_HQ_BASE_ORDER_BUY_VOLUME = COLUMN_HQ_BASE_BEGIN + 62; // 委买量
	public final static int COLUMN_HQ_BASE_SELL_PRICE = COLUMN_HQ_BASE_BEGIN + 56; // 卖出价格，即委卖价，卖1价，相关之间等同
	public final static int COLUMN_HQ_BASE_ORDER_SELL_PRICE = COLUMN_HQ_BASE_BEGIN + 63; // 委卖价，与COLUMN_HQ_BASE_SELL_PRICE等同
	public final static int COLUMN_HQ_BASE_ORDER_SELL_VOLUME = COLUMN_HQ_BASE_BEGIN + 64; // 委卖量
	public final static int COLUMN_HQ_BASE_IN_HANDS = COLUMN_HQ_BASE_BEGIN + 65; // 内盘
	public final static int COLUMN_HQ_BASE_OUT_HANDS = COLUMN_HQ_BASE_BEGIN + 66; // 外盘
	public final static int COLUMN_HQ_BASE_MONEY = COLUMN_HQ_BASE_BEGIN + 67; // 成交金额
	public final static int COLUMN_HQ_BASE_SPEEDUP = COLUMN_HQ_BASE_BEGIN + 78;//涨速
	public final static int COLUMN_HQ_BASE_RISE_SPEED = COLUMN_HQ_BASE_BEGIN + 68; // 涨速，与COLUMN_HQ_BASE_SPEEDUP等同
	public final static int COLUMN_HQ_BASE_AVERAGE_PRICE = COLUMN_HQ_BASE_BEGIN + 69; // 均价
	public final static int COLUMN_HQ_BASE_RANGE = COLUMN_HQ_BASE_BEGIN + 70; // 振幅
	public final static int COLUMN_HQ_BASE_ORDER_RATIO = COLUMN_HQ_BASE_BEGIN + 71; // 委比
	public final static int COLUMN_HQ_BASE_ORDER_DIFF = COLUMN_HQ_BASE_BEGIN + 72; // 委差
	public final static int COLUMN_HQ_EX_EXHAND_RATIO = COLUMN_HQ_EX_BEGIN + 21; // 换手率
	public final static int COLUMN_HQ_EX_PE_RATIO = COLUMN_HQ_EX_BEGIN + 23; // 市盈率
	public final static int COLUMN_HQ_EX_BOND_ACCRUAL = COLUMN_HQ_EX_BEGIN + 26; // 国债利息
	public final static int COLUMN_HQ_EX_FUND_NETVALUE = COLUMN_HQ_EX_BEGIN + 25; // 基金净值

	// 期货排序字段 COLUMN_FUTURES_BEGIN = 10152
	public final static int COLUMN_FUTURES_CODE = COLUMN_FUTURES_BEGIN + 0; // 商品代码，macs提供
	public final static int COLUMN_FUTURES_NAME = COLUMN_FUTURES_BEGIN + 1; // 商品名称，macs提供
	public final static int COLUMN_FUTURES_OPEN = COLUMN_FUTURES_BEGIN + 2; // 开盘，与COLUMN_HQ_BASE_OPEN等同
	public final static int COLUMN_FUTURES_PRECLOSE = COLUMN_FUTURES_BEGIN + 3; // 昨收盘，与COLUMN_HQ_BASE_PRECLOSE等同
	public final static int COLUMN_FUTURES_HIGH = COLUMN_FUTURES_BEGIN + 4; // 最高，与COLUMN_HQ_BASE_MAX_PRICE等同
	public final static int COLUMN_FUTURES_LOW = COLUMN_FUTURES_BEGIN + 5; // 最低，与COLUMN_HQ_BASE_MIN_PRICE等同
	public final static int COLUMN_FUTURES_NEW_PRICE = COLUMN_FUTURES_BEGIN + 18;// 最新价，与COLUMN_HQ_BASE_NEW_PRICE等同
	public final static int COLUMN_FUTURES_BID1_PRICE = COLUMN_FUTURES_BEGIN + 6; // 买1价，与COLUMN_HQ_BASE_BUY_PRICE，COLUMN_HQ_BASE_ORDER_BUY_PRICE等同
	public final static int COLUMN_FUTURES_BID1_VOL = COLUMN_FUTURES_BEGIN + 8; // 买1量，与COLUMN_HQ_BASE_ORDER_BUY_VOLUME等同
	public final static int COLUMN_FUTURES_ASK1_PRICE = COLUMN_FUTURES_BEGIN + 7; // 卖1价，与COLUMN_HQ_BASE_SELL_PRICE，COLUMN_HQ_BASE_ORDER_SELL_PRICE等同
	public final static int COLUMN_FUTURES_ASK1_VOL = COLUMN_FUTURES_BEGIN + 9;// 卖1量，与COLUMN_HQ_BASE_ORDER_SELL_VOLUME等同
	public final static int COLUMN_FUTURES_PRESETTLE = COLUMN_FUTURES_BEGIN +28;// 昨结算，特别说明：该排序值采用了现结算价的列定义，与其他的列排序定义不一致
	public final static int COLUMN_FUTURES_SETTLE = COLUMN_FUTURES_BEGIN +21;// 结算价
	public final static int COLUMN_FUTURES_TOTAL_AMOUNT = COLUMN_FUTURES_BEGIN + 24;// 总持仓
	public final static int COLUMN_FUTURES_AMOUNT = COLUMN_FUTURES_BEGIN + 20;// 持仓量，与COLUMN_FUTURES_TOTAL_AMOUNT相同
	public final static int COLUMN_FUTURES_AMOUNT_SUB = COLUMN_FUTURES_BEGIN + 30;// 持仓差
	public final static int COLUMN_FUTURES_VOLUME = COLUMN_FUTURES_BEGIN + 19;// 成交量，与COLUMN_HQ_BASE_TOTAL_HAND相同
	public final static int COLUMN_FUTURES_RISE_VALUE = COLUMN_FUTURES_BEGIN + 25;// 涨跌值，与COLUMN_HQ_BASE_RISE_VALUE相同
	public final static int COLUMN_FUTURES_ORDER_RATIO = COLUMN_FUTURES_BEGIN + 26;// 涨跌幅，与COLUMN_HQ_BASE_RISE_RATIO相同
	public final static int COLUMN_FUTURES_NEW_VOL = COLUMN_FUTURES_BEGIN + 23;// 现量，与COLUMN_HQ_BASE_HAND相同
	public final static int COLUMN_FUTURES_MARKET = 10174;// 市场，H5暂不支持
	public final static int COLUMN_FUTURES_Current_SETTLE = 10179;// 现结算，H5暂不支持
	public final static int COLUMN_FUTURES_IO_SUB = 10181;// IO差，H5暂不支持
	public final static int COLUMN_FUTURES_OPEN_POSITION = 10182;// 现开仓，H5暂不支持
	public final static int COLUMN_FUTURES_CLEAR_POSITION = 10183;// 现平仓，H5暂不支持
	public final static int COLUMN_FUTURES_PRESETTLE_PRICE = 10184;// 前结价，H5暂不支持

	// 资金流相关排序
	public final static int COLUMN_DDX_DDX1 = 11000;//当日ddx排名,Macs自定义提供
	public final static int COLUMN_DDX_DDX3 = 11001;//3日ddx排名,Macs自定义提供
	public final static int COLUMN_DDX_DDX10 = 11002;//10日ddx排名,Macs自定义提供
	public final static int COLUMN_DDX_FUNDMAIN = 11003;//主力净流入排名,Macs自定义提供

}
