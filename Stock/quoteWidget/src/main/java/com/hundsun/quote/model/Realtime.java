package com.hundsun.quote.model;

//LiangHao
public class Realtime {
	//交易状态定义
	public static final short TRADE_STATUS_ADD  	=			0		;//		新产品
	public static final short TRADE_STATUS_BETW 	=			1		;//	          交易间，禁止任何交易活动
	public static final short TRADE_STATUS_BREAK	=			2		;//		休市,如:午休.无撮合和市场内部信息披露
	public static final short TRADE_STATUS_CLOSE	=			3		;//		闭市，自动计算闭市价格
	public static final short TRADE_STATUS_DEL  	=			4		;//		产品待删除
	public static final short TRADE_STATUS_ENDTR	=			5		;//		交易结束
	public static final short TRADE_STATUS_FCALL	=			6		;//		固定价格集合竞价
	public static final short TRADE_STATUS_HALT 	=			7		;//		暂停，除了自有订单和交易的查询之外，任何交易活动都被禁止
	public static final short TRADE_STATUS_SUSP 	=			8		;//		停盘，与HALT的区别在于SUSP时可以撤单
	public static final short TRADE_STATUS_ICALL	=			9		;//		盘中集合竞价
	public static final short TRADE_STATUS_IOBB 	=			10		;//		盘中集合竞价订单簿平衡
	public static final short TRADE_STATUS_IPOBB	=			11		;//		盘中集合竞价
	public static final short TRADE_STATUS_OCALL	=			12		;//		开市集合竞价
	public static final short TRADE_STATUS_OOBB 	=			13		;//		开市集合竞价OBB
	public static final short TRADE_STATUS_OPOBB	=			14		;//		开市集合竞价订单簿平衡前期时段
	public static final short TRADE_STATUS_NOTRD	=			15		;//		非交易支持非交易服务
	public static final short TRADE_STATUS_POSTR	=			16		;//		盘后处理
	public static final short TRADE_STATUS_PRETR	=			17		;//		盘前处理
	public static final short TRADE_STATUS_START	=			18		;//		启动
	public static final short TRADE_STATUS_TRADE	=			19		;//		连续自动撮合
	public static final short TRADE_STATUS_VOLA 	=			20		;//		连续交易和集合竞价交易的波动性中断VOLA
	public static final short TRADE_STATUS_STOP		=			21		;//		长期停盘，停盘n天,n>=1
	
	/**
	 * 排名使用追加 code、name、codeType
	 * @author  梁浩
	 * @ Data 2014-10-29 15:11
	 * 
	 * */
	protected String mCode;             //股票【指数】代码
	protected String mCodeType;         //代码类型
	protected String mName;             //股票【指数】名称  
	
	protected long   mTimestamp;         //时间戳
	protected int    mTradeMinutes;      //交易分钟数
	protected int    mTradeStatus;       //交易状态 
	protected double mPreClosePrice;     //昨收价
	protected double mOpenPrice;         //今日开盘价
	protected double mNewPrice;          //最新成交价
	protected double mHighPrice;         //最高价
	protected double mLowPrice;          //最低价
	protected double mClosePrice;        //今日收盘价
	protected double mAveragePrice ;     //均价                   <增加 LiangHao 2014-10-17 17:38>
	protected long   mTotalVolume;       // 总成交量(单位:股)  
	protected double  mTotalMoney;        // 总成交额
	protected long   mCurrent;           // 最近成交量  - 现手
	protected long   mTotalBuy;          // 总委买量       -总买
	protected long   mTotalSell;         // 总委卖量       - 总卖
	protected double m52WeekHighPrice;   //52周高 -美股使用
	protected double m52WeekLowPrice;    //52周低 -美股使用
	protected double mPriceChange;       //涨跌额
	protected double mPriceChangePrecent; //涨跌幅
	protected double mBeforeAfterPrice;   //盘前盘后价
	
	protected String mCurrency;       //货币代码
	protected String mIndustryCode;      // 行业代码
	protected int   mRiseSpeed;         //涨速
	protected int   mHand;              //每手证券数量
	protected double mTurnoverRatio;          //换手率

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		this.mCode = code;
	}

	public String getCodeType() {
		return mCodeType;
	}

	public void setCodeType(String codeType) {
		this.mCodeType = codeType;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public double getOpenPrice() {
		return mOpenPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.mOpenPrice = openPrice;
	}

	public double getHighPrice() {
		return mHighPrice;
	}

	public void setHighPrice(double highPrice) {
		this.mHighPrice = highPrice;
	}

	public double getLowPrice() {
		return mLowPrice;
	}

	public void setLowPrice(double lowPrice) {
		this.mLowPrice = lowPrice;
	}

	public double getNewPrice() {
		return mNewPrice;
	}

	public void setNewPrice(double newPrice) {
		this.mNewPrice = newPrice;
	}

	public long getTotalVolume() {
		return mTotalVolume;
	}

	public void setTotalVolume(long total) {
		this.mTotalVolume = total;
	}

	public double getTotalMoney() {
		return mTotalMoney;
	}

	public void setTotalMoney(double money) {
		this.mTotalMoney = money;
	}

	public double getPreClosePrice() {
		return mPreClosePrice;
	}

	public void setPreClosePrice(double preClosePrice) {
		this.mPreClosePrice = preClosePrice;
	}

	public long getCurrent() {
		return mCurrent;
	}

	public void setCurrent(int current) {
		this.mCurrent = current;
	}
	
	/**
	 * 获取涨跌
	 * @return
	 */
	public double getPriceChange(){
		return mPriceChange;
	}
	
	public void setPriceChange(double change){
		mPriceChange = change;
	}
	
	/**
	 * 获取涨跌幅
	 * @return
	 */
	public double getPriceChangePrecent(){
		return mPriceChangePrecent;
	}
	
	public void setPriceChangePrecent(double changePrecent){
		mPriceChangePrecent = changePrecent;
	}

	public long getTimestamp() {
		return mTimestamp;
	}

	public void setTimestamp(long timestamp) {
		this.mTimestamp = timestamp;
	}

	public int getTradeMinutes() {
		return mTradeMinutes;
	}

	public void setTradeMinutes(int tradeMinutes) {
		this.mTradeMinutes = tradeMinutes;
	}

	public int getTradeStatus() {
		return mTradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.mTradeStatus = tradeStatus; 
	}

	public int getRiseSpeed() {
		return mRiseSpeed;
	}

	public void setRiseSpeed(int riseSpeed) {
		this.mRiseSpeed = riseSpeed;
	}

	public int getHand() {
		return mHand;
	}

	public void setHand(int hand) {
		this.mHand = hand;
	}

	public long getTotalBuy() {
		return mTotalBuy;
	}

	public void setTotalBuy(long totalBuy) {
		this.mTotalBuy = totalBuy;
	}

	public long getTotalSell() {
		return mTotalSell;
	}

	public void setTotalSell(long totalSell) {
		this.mTotalSell = totalSell;
	}

	/**
	 * 获取52周高价
	 * @return 52周高价
	 */
	public double get52WeekHighPrice() {
		return m52WeekHighPrice;
	}

	/**
	 * @param p52WeekHighPrice
	 */
	public void set52WeekHighPrice(double p52WeekHighPrice) {
		this.m52WeekHighPrice = p52WeekHighPrice;
	}

	/**
	 * 获取52周低价
	 * @return
	 */
	public double get52WeekLowPrice() {
		return m52WeekLowPrice;
	}

	/**
	 * @param p52WeekLowPrice
	 */
	public void set52WeekLowPrice(double p52WeekLowPrice) {
		this.m52WeekLowPrice = p52WeekLowPrice;
	}

	/**
	 * 获取盘后数据
	 * @return 盘后数据
	 */
	public double getClosePrice() {
		return mClosePrice;
	}

	public void setClosePrice(double closePrice) {
		this.mClosePrice = closePrice;
	}

	/**
	 * 获取盘前盘后价
	 * @return
	 */
	public double getBeforeAfterPrice() {
		return mBeforeAfterPrice;
	}

	public void setBeforeAfterPrice(double beforeAfterPrice) {
		this.mBeforeAfterPrice = beforeAfterPrice;
	}


	/**
	 * 均价
	 * @return 均价
	 */
	public double getAveragePrice() {
		return mAveragePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.mAveragePrice = averagePrice;
	}

	public String getCurrency() {
		return mCurrency;
	}

	public void setCurrency(String currency) {
		this.mCurrency = currency;
	}

	public String getIndustryCode() {
		return mIndustryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.mIndustryCode = industryCode;
	}

	public double getTurnoverRatio() {
		return mTurnoverRatio;
	}

	public void setTurnoverRatio(double turnoverRatio) {
		this.mTurnoverRatio = turnoverRatio;
	}
	
}
