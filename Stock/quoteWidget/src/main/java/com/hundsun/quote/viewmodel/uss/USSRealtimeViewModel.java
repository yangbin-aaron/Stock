package com.hundsun.quote.viewmodel.uss;

import java.util.ArrayList;

import com.hundsun.quote.model.Realtime;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockRealtime;
import com.hundsun.quote.model.StockRealtime.PriceVolumeItem;
import com.hundsun.quote.tools.FormatUtils;
import com.hundsun.quote.tools.QuoteUtils;
import com.hundsun.quote.viewmodel.ViewModel;

/**
 * @author HuangCheng LiangHao
 *
 */
public class USSRealtimeViewModel extends ViewModel{
	private StockRealtime mStockRealtime;

	public USSRealtimeViewModel(Stock stock){
		super(stock);
	}

	public StockRealtime getRealtime() {
		return mStockRealtime;
	}

	public void setRealtime(Realtime mStockRealtime) {
		this.mStockRealtime = (StockRealtime) mStockRealtime;
		this.mRealtime = mStockRealtime;
	}

	public void reset(){
		mStockRealtime = null;
	}

	/**
	 * 获取开盘价
	 * @return 开盘价
	 */
	public double getOpenPrice() {
		return mStockRealtime.getOpenPrice();
	}

	/**
	 * 昨收价
	 * @return 
	 */
	public double getPreClosePrice() {
		return mStockRealtime.getPreClosePrice();
	}

	/**
	 * 获取收盘价
	 * @return 
	 */
	public double getClosePrice() {
		return mStockRealtime.getClosePrice();
	}

	/**
	 * 获取最高价
	 * @return
	 */
	public double getHighPrice() {
		return mStockRealtime.getHighPrice();
	}

	/**
	 * 获取最低价
	 * @return
	 */
	public double getLowPrice() {
		return mStockRealtime.getLowPrice();
	}

	/**
	 * 获取最新价
	 * @return
	 */
	public double getNewPrice() {
		return mStockRealtime.getNewPrice();
	}

	/**
	 * 获取均价
	 * @return
	 */
	public double getAvergePrice() {
		return mStockRealtime.getNewPrice();
	}

	/**
	 * 获取跌停价
	 * @return 跌停价
	 */
	public double getLowLimitPrice() {
		return mStockRealtime.getLowLimitPrice();
	}

	/**
	 * 获取涨停价
	 * @return 涨停价
	 */
	public double getHighLimitPrice() {
		return mStockRealtime.getHighLimitPrice();
	}
	/**
	 * 获取加权均价
	 * @return
	 */
	public double getWeightAvergePrice() {
		return mStockRealtime.getWeightAveragePrice();
	}

	/**
	 * 获取52周最高价
	 * @return
	 */
	public double get52WeekHighPrice() {
		return mStockRealtime.get52WeekHighPrice();
	}
	/**
	 * 获取52周最底价
	 * @return
	 */
	public double get52WeekLowPrice() {
		return mStockRealtime.get52WeekLowPrice();
	}

	/**
	 * 获取盘前盘后价
	 * @return
	 */
	public double getBeforeAfterPrice() {
		return mStockRealtime.getBeforeAfterPrice();
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
		if(buyPriceList == null){
			return 0;
		}
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
		if(sellPriceList == null){
			return 0;
		}
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
	    int mStocksPerHand   = mStockRealtime.getHand() == 0 ? 100 : mStockRealtime.getHand(); //每股手数
		int buyCount  = getEntrustBuy();
		int sellCount = getEntrustSell();
		if (buyCount + sellCount != 0) {
			double entrustRatio = (double) (buyCount - sellCount) / (buyCount + sellCount) * mStocksPerHand;
			ret = FormatUtils.formatPrice(mStock, entrustRatio);
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
	 * 获取总成交量 -手
	 * @return
	 */
	public long getTotalVolume() {
	    int mStocksPerHand   = mStockRealtime.getHand() == 0 ? 100 : mStockRealtime.getHand(); //每股手数
		return  mStockRealtime.getTotalVolume()/mStocksPerHand;
	}

	public String getTotalMoney() {
		return FormatUtils.formatBigNumber( mStockRealtime.getTotalMoney() );
	}

	/**
	 * 获取最近成交量 -现手
	 * @return
	 */
	public String getCurrent() {
		return FormatUtils.formatBigNumber( mStockRealtime.getCurrent() ) ;
	}

	/**
	 *获取美股外盘
	 * @return 美股外盘
	 */
	public String getOutside() {
		return FormatUtils.formatStockVolume(mStock,  mStockRealtime.getOutside() );
	}

	/**
	 *获取美股内盘
	 * @return 美股内盘
	 */
	public String getInside() {
		return FormatUtils.formatStockVolume(mStock,  mStockRealtime.getInside() );
	}

	/**
	 * 获取总股数
	 * @return 总股数
	 */
	public String getTotalShares() {
		String totalShares = mStockRealtime.getFinancial().getTotalShares();
		int mPricePrecision = QuoteUtils.getPricePrecision("XNAS");
		return FormatUtils.numberToStringWithUnit(totalShares ,mPricePrecision);
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
		return mStockRealtime.getFinancial().getPE();
	}

	/**
	 * 获取每股收益
	 * @return 每股收益
	 */
	public String getEPS() {
		return mStockRealtime.getFinancial().getEPS();
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
		String totalMarketValue = mStockRealtime.getFinancial().getMarketValue();
//		int mPricePrecision = QuoteUtils.getPricePrecision("XNAS");
//		return  FormatUtils.numberToStringWithUnit(totalMarketValue,mPricePrecision);
		return totalMarketValue;
	}

	/**
	 * 获取流通市值
	 * @return 流通市值
	 * 总股本*现价
	 */
	public String getCirculationMarketValue() {
		String circulationShare = getCirculationShares();
		long circulationShare_ = 0;
		try {
			circulationShare_ = Integer.parseInt(circulationShare);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		double newPrice  = getNewPrice();
		return Double.toString(circulationShare_* newPrice);
	}

	/**
	 * 获取振幅  
	 * @return 振幅
	 * （当日最高价-当日最低价）/昨收价 * 100 
	 */
	public String getAmplitude(){
	    int mStocksPerHand   = mStockRealtime.getHand() == 0 ? 100 : mStockRealtime.getHand(); //每股手数
		double maxPrice = getHighPrice();
		double minPrice = getLowPrice();
		double preClosePrice = getPreClosePrice();
		double amplitude = ((maxPrice - minPrice)/preClosePrice) * mStocksPerHand;
		return FormatUtils.formatPrice(mStock, amplitude);
	}
	
	/**
	 * 获取交易分钟数
	 * @return 交易分钟数
	 */
	public int getTradeMinutes() {
		return  mStockRealtime.getTradeMinutes();
	}
	
	/**
	 * 获取交易状态
	 * @return 交易状态
	 */
	public int getTradeStatus() {
		return  mStockRealtime.getTradeStatus() ;
	}
	
	/**
	 * 获取成交笔数
	 * @return 成交笔数
	 */
	public int getTradeNumber() {
		return  mStockRealtime.getTradeNumber();
	}
	
	/**
	 * 获取货币代码
	 * @return 货币代码
	 */
	public String getCurrency() {
		return  mStockRealtime.getCurrency();
	}
	
	/**
	 * 获取时间戳
	 * @return 时间戳
	 */
	public long getTimestamp() {
		return  mStockRealtime.getTimestamp();
	}
	
	/**
	 * 获取每股手数
	 * @return 交易分钟数
	 */
	public int getSharesPerHand() {
		return  mStockRealtime.getHand();
	}
	
	/**
	 * 获取盘前盘后价格
	 * @return
	 */
	public double getPopcPrice(){
		if (null != mStockRealtime) {
			return mStockRealtime.PopcPrice;
		}
		return 0;
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
		double turnoverRatio = mStockRealtime.getTurnoverRatio();
		if (turnoverRatio != 0) {
			ret= FormatUtils.formatPercent( turnoverRatio);
		}
		return ret;
	}
	
}
