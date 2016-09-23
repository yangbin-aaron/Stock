package com.hundsun.quote.viewmodel;

import java.util.ArrayList;

import android.text.TextUtils;

import com.hundsun.quote.model.Realtime;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockRealtime;
import com.hundsun.quote.model.StockRealtime.PriceVolumeItem;
import com.hundsun.quote.tools.FormatUtils;
import com.hundsun.quote.tools.QuoteUtils;

/**
 * 沪深快照
 * @author LiangHao
 *
 */
public class RealtimeViewModel extends ViewModel{
	StockRealtime mStockRealtime;
	private int mPricePrecision = QuoteUtils.getPricePrecision("");

	public RealtimeViewModel(Stock stock){
		super(stock);
	}
	
	public RealtimeViewModel(){
		super();
	}

	public StockRealtime getRealtime() {
		return mStockRealtime;
	}
	
	private void setStockInfo( Realtime realtime ){
		if (null == mStock) {
			mStock = new Stock();
		}
		String text = realtime.getCodeType();
		if (null != text && !text.isEmpty()) {
			mStock.setCodeType(text);
		}
		text = realtime.getCode();
		if (null != text && !text.isEmpty()) {
			mStock.setStockCode(text);
		}
		text = realtime.getName();
		if (null != text && !text.isEmpty()) {
			mStock.setStockName(text);
		}
		double price = realtime.getPreClosePrice();
		if ( 0 != price) {
			mStock.setPreClosePrice(price);
		}
	}
	
	public void setRealtime(Realtime realtime) {
		super.setRealtime(realtime);
		if ( realtime instanceof StockRealtime) {
			setRealtime((StockRealtime)realtime);
		}
		setStockInfo(realtime);
		
	}

	public void setRealtime(StockRealtime stockRealtime) {
		this.mStockRealtime = stockRealtime;
		this.mRealtime = stockRealtime;
		this.setStockInfo( stockRealtime );
	}

	public void reset(){
		mStockRealtime = null;
	}

	public double getHighPrice() {
		return mStockRealtime.getHighPrice();
	}

	public double getOpenPrice() {
		return mStockRealtime.getOpenPrice();
	}

	public double getLowPrice() {
		return mStockRealtime.getLowPrice();
	}

	public double getPreClosePrice() {
		if(mStockRealtime == null){
			return 0.00;
		}
		return mStockRealtime.getPreClosePrice();
	}

	public double getNewPrice() {
		return mStockRealtime.getNewPrice();
	}

	/**
	 * 获取总成交量 -返回字符值
	 * @return
	 */
	public String getTotalVolumeStr() {
		if(mStockRealtime == null){
			return "--";
		}
		float mStocksPerHand = getStocksPerHand(); //每股手数
		float totalShare = mStockRealtime.getTotalVolume()/mStocksPerHand;
		return FormatUtils.formatStockVolume(mStock, totalShare) ;
	}

	/**
	 * 获取总成交量 -手
	 * @return
	 */
	public long getTotalVolume() {
		float mStocksPerHand   = getStocksPerHand(); //每股手数
		return  (long) (mStockRealtime.getTotalVolume()/mStocksPerHand + 0.5);
	}

	/**
	 * 返回总成交金额
	 * @return
	 */
	public String getTotalMoneyStr() {
		if(mStockRealtime == null)
			return "--";
		double totalMoney = mStockRealtime.getTotalMoney();
		return FormatUtils.formatBigNumber(totalMoney);
	}

	/**
	 * 返回总成交金额
	 * @return
	 */
	public double getTotalMoney() {
		if(mStockRealtime == null)
			return 0;
		return mStockRealtime.getTotalMoney();
	}

	/**
	 * 获得最近成交量-现手
	 * @return
	 */
	public String getCurrent() {
		if(mStockRealtime == null){
			return "--";
		}
		long current = mStockRealtime.getCurrent();
		return FormatUtils.formatBigNumber(current) ;
	}

	/**
	 * 获得外盘
	 * 
	 * @return 外盘
	 */
	public String getOutside() {
		if(mStockRealtime == null){
			return "--";
		}
		double outSide = mStockRealtime.getOutside()/(double)getPerHandAmount();
		return FormatUtils.formatStockVolume(mStock, outSide);
	}

	/**
	 * 获得 内盘
	 * 
	 * @return 内盘
	 */
	public String getInside() {
		if(mStockRealtime == null){
			return "--";
		}
		double inSide = mStockRealtime.getInside()/(double)getPerHandAmount();
		return FormatUtils.formatStockVolume(mStock, inSide);
	}

	/**
	 * 获取每手股数
	 * 
	 * @return 每手股数
	 */
	public int getPerHandAmount() {
		int hand = 0;
		if(mStockRealtime !=null){
			hand = mStockRealtime.getHand();
		}
		if (0 == hand) {
			hand = 100;
		}
		return hand;
	}

	/**
	 * 获取委买-五档
	 * 
	 * @return 五档
	 */
	public ArrayList<PriceVolumeItem> getBuyPriceList() {
		if(mStockRealtime==null)
			return null;
		return mStockRealtime.getBuyPriceList();
	}
	/**
	 * 获取委卖-五档
	 * 
	 * @return 五档
	 */
	public ArrayList<PriceVolumeItem> getSellPriceList() {
		if(mStockRealtime==null)
			return null;
		return mStockRealtime.getSellPriceList();
	}

	/**
	 * 获得涨停板
	 * 
	 * @return 涨停板
	 */
	public double getUpperLimitPrice() {
		if(mStockRealtime == null)
			return 0;
		return mStockRealtime.getHighLimitPrice();
	}

	/**
	 * 获得跌停板
	 * 
	 * @return 跌停板
	 */
	public double getLowerLimitPrice() {
		if(mStockRealtime==null)
			return 0;
		return mStockRealtime.getLowLimitPrice();
	}
	/**
	 * 获取分钟数
	 * 
	 * @return 分钟数
	 */
	public int getTradeMinute() {
		if(mStockRealtime==null)
			return 0;
		return mStockRealtime.getTradeMinutes();
	}

	 /**
     * 获取涨跌幅
     * 
     * @return 涨跌幅
     */
    public String getPriceChangePercent(){
		if(mStockRealtime == null){
			return "0.00";
		}
		return mStockRealtime.getPriceChangePrecent() + "";
    }

    /**
     * 获取涨跌
     * 
     * @return 涨跌
     */
    public String getPriceChange(){
		if(mStockRealtime == null){
			return "0.00";
		}
		return mStockRealtime.getPriceChange() + "";
    }
	/**
	 * 获取当前时间点的涨跌幅
	 * 
	 * @param preClosePrice
	 *            昨收价
	 * @return 涨跌幅(字符串，已精确小数点)
	 *//*
	public String getPriceChangePercentStr() {
		if(mStockRealtime == null){
			return "0.00%";
		}
		if (QuoteUtils.isDoubleZero(mStockRealtime.getPreClosePrice())) {
			return "0.00%";
		}else {
			return FormatUtils.formatPercent(mStockRealtime.getPriceChangePrecent());
		}
	}
	  *//**
	  * 获取当前时间点的涨跌幅
	  * 
	  * @param preClosePrice
	  *            昨收价
	  * @return 涨跌幅(字符串，已精确小数点)
	  *//*
	public double getPriceChangePercent() {
		if(mStockRealtime == null){
			return 0;
		}
		if (QuoteUtils.isDoubleZero(mStockRealtime.getPreClosePrice())) {
			return 0;
		}else {
			return mStockRealtime.getPriceChangePrecent();
		}
	}
	   *//**
	   * 获取当前时间点的涨跌
	   * 
	   * @param preClosePrice
	   *            昨收价
	   * @return 涨跌(字符串，已精确小数点)
	   *//*
	public double getPriceChange() {
		if(mStockRealtime == null){
			return 0;
		}
		return  mStockRealtime.getPriceChange();
	}*/
	/**
	 * 获取委买量
	 * 
	 * @return 委买量
	 */
	public int getEntrustBuy() {
		if(mStockRealtime == null){
			return 0;
		}
		int buyCount = 0 ;
		ArrayList<PriceVolumeItem>  buyPriceList = mStockRealtime.getBuyPriceList();
		for(PriceVolumeItem priceVolumeItem : buyPriceList){
			buyCount +=  priceVolumeItem.volume;
		}
		return buyCount;
	}

	/**
	 * 获取委卖量
	 * 
	 * @return 委卖价量
	 */
	public int getEntrustSell() {
		if(mStockRealtime == null){
			return 0;
		}
		int sellCount = 0;
		ArrayList<PriceVolumeItem>  sellPriceList = mStockRealtime.getSellPriceList();
		for(PriceVolumeItem priceVolumeItem : sellPriceList){
			sellCount +=  priceVolumeItem.volume;
		}
		return sellCount;
	}

	/**
	 * 获取委比值 委比＝(委买手数－委卖手数)/(委买手数＋委卖手数)×100％
	 * 
	 * @return 委比值(字符串，已精确小数点)
	 */
	public String getEntrustRatio() {
		if(mStockRealtime == null){
			return "--";
		}
		String ret    = "--";
		float mStocksPerHand   = getStocksPerHand(); //每股手数
		int buyCount  = getEntrustBuy();
		int sellCount = getEntrustSell();
		if (buyCount + sellCount != 0) {
			double entrustRatio = (double) (buyCount - sellCount) / (buyCount + sellCount) * mStocksPerHand;
			ret = FormatUtils.format(mPricePrecision, entrustRatio) + "%";
		}
		return ret;
	}

	/**
	 * 获取委差
	 * 
	 * @return 委差(字符串，已精确小数点)
	 */
	public String getEntrustDifference() {
		int buyCount  = getEntrustBuy();
		int sellCount = getEntrustSell();
		return Integer.toString(buyCount - sellCount);
	}

	/**
	 * 获取量比值 量比＝现成交总手/（过去5日平均每分钟成交量×当日累计开市时间（分））
	 * @return 量比值(字符串，已精确小数点)
	 */
	public String getLiangBi() {
		String ret = "--";
		return ret;
	}

	/**
	 * 获取股票的换手率 换手率=(某一段时间内的成交量/流通股数)×100%
	 * 
	 * @param circulateStockAmount
	 *            流通股数
	 * @return 换手率(字符串，已精确小数点)
	 */
	public String getChangedHandRatio() {
		String ret = "--";
		if(mStockRealtime == null || mStockRealtime.getNewPrice() == 0){
			return ret;
		}
		if(QuoteUtils.isDtk()){
			float mStocksPerHand   = getStocksPerHand(); //每股手数
			String circulationShares = mStockRealtime.getFinancial().getCirculationShares();
			double  circulationSharesf = FormatUtils.stringAmountReturnFNumberValue(circulationShares);
			double totalVolme = getTotalVolume();
			if (circulationSharesf != 0) {
				double changeHandRatio = totalVolme / circulationSharesf * mStocksPerHand ;
				ret = FormatUtils.format(mPricePrecision, changeHandRatio) + "%";
			}
		}else{
			double turnoverRatio = mStockRealtime.getTurnoverRatio();
			if (turnoverRatio != 0) {
				ret= FormatUtils.formatPercent( turnoverRatio);
			}
		}
		return ret;
	}
	
	/**
	 * 获取总股数
	 * @return 总股数
	 */
	public String getTotalShares() {
		String totalShare = mStockRealtime.getFinancial().getTotalShares();
		return FormatUtils.numberToStringWithUnit(totalShare,mPricePrecision) + "股";
	}

	/**
	 * 获取流通股
	 * @return 流通股
	 */
	public String getCirculationShares() {
		return mStockRealtime.getFinancial().getCirculationShares();
	}

	/**
	 * 获取美股净资产
	 * @return 美股净资产
	 */
	public String getNetAsset() {
		return String.valueOf( mStockRealtime.getFinancial().getNetAsset() );
	}

	/**
	 * 获取净资产收益率
	 * @return 净资产收益率
	 */
	public String getROE() {
		return String.valueOf( mStockRealtime.getFinancial().getROE() );
	}

	/**
	 * 获取市盈率
	 * @return 市盈率
	 */
	public String getPE() {
		return String.valueOf( mStockRealtime.getFinancial().getPE() );
	}

	/**
	 * 获取每股收益
	 * @return 每股收益
	 */
	public String getEPS() {
		return String.valueOf( mStockRealtime.getFinancial().getEPS() );
	}

	/**
	 * 获取股东人数
	 * @return 股东人数
	 */
	public String getStockHolders() {
		return String.valueOf( mStockRealtime.getFinancial().getStockHolders() );
	}

	/**
	 * 获取总市值
	 * @return 总市值
	 * 总股本*现价
	 */
	public String getTotalMarketValue() {
		if(QuoteUtils.isDtk()){
			String totalShare = mStockRealtime.getFinancial().getTotalShares();
			double totalShare_d = 0;
			if(!TextUtils.isEmpty(totalShare)){
				totalShare_d = Double.parseDouble(totalShare);
			}
			double newPrice  = getNewPrice();
			double marketValue = totalShare_d * newPrice ;
			return FormatUtils.formatBigNumber(marketValue);
		}else{
			return mStockRealtime.getFinancial().getMarketValue();
		}
	}

	/**
	 * 获取流通市值
	 * @return 流通市值
	 * 总股本*现价
	 */
	public String getCirculationMarketValue() {
//		String  circulationShares   = mStockRealtime.getFinancial().getCirculationShares();
//		double  circulationShares_d = FormatUtils.stringAmountReturnFNumberValue(circulationShares);
//		double newPrice  = getNewPrice();
//		double circulationmarketValue = circulationShares_d * newPrice ;
//		return FormatUtils.amountToString2(circulationmarketValue, mPricePrecision);
		return mStockRealtime.getFinancial().getCirculationValue();
	}

	/**
	 * 获取振幅  
	 * @return 振幅
	 * （当日最高价-当日最低价）/昨收价 * 100 
	 */
	public String getAmplitude(){
		double maxPrice = getHighPrice();
		double minPrice = getLowPrice();
		double preClosePrice = getPreClosePrice();
		
		if (maxPrice == 0 || minPrice == 0) {
			return "--";
		}
		
		double amplitude = ((maxPrice - minPrice)/preClosePrice)  * 100;
		
		return FormatUtils.format(mPricePrecision, amplitude) + "%";
	}
	
	public int getTradeStatus(){
		if (mStockRealtime == null) {
			return Realtime.TRADE_STATUS_TRADE;
		}
		return mStockRealtime.getTradeStatus();
	}

}
