package com.hundsun.quote.model;

import java.util.ArrayList;


/**
 * @author HuangCheng / LiangHao
 * 2014-09-30
 *
 */
public class StockRealtime extends Realtime {
	protected double  mWeightAveragePrice ;     //加权均价                   <增加 LiangHao 2014-10-17 17:38>
	protected int    mTradeNumber ;            //成交笔数                   <增加 LiangHao 2014-10-17 17:38>
	protected double  mHighLimitPrice;          //涨停价
	protected double  mLowLimitPrice;           //跌停价
	protected long   mOutside;                 //外盘 成交量
	protected long   mInside;                  //内盘 成交量
	
	
	protected FinancialItem mFinancial;        //财务数据
	protected ArrayList<PriceVolumeItem> mBuyPriceList; //买卖盘口 -委买
	protected ArrayList<PriceVolumeItem> mSellPriceList; //买卖盘口 -委卖
	
	
	public double getHighLimitPrice() {
		return mHighLimitPrice;
	}

	public void setHighLimitPrice(double highLimitPrice) {
		this.mHighLimitPrice = highLimitPrice;
	}

	public double getLowLimitPrice() {
		return mLowLimitPrice;
	}

	public void setLowLimitPrice(double lowLimitPrice) {
		this.mLowLimitPrice = lowLimitPrice;
	}
	public long getInside() {
		return mInside;
	}

	public void setInside(long inside) {
		this.mInside = inside;
	}

	public long getOutside() {
		return mOutside;
	}

	public void setOutside(long outside) {
		this.mOutside = outside;
	}

	public FinancialItem getFinancial() {
		return mFinancial;
	}

	public void setFinancial(FinancialItem financial) {
		this.mFinancial = financial;
	}
	
	public ArrayList<PriceVolumeItem> getBuyPriceList() {
		return mBuyPriceList;
	}

	public void setBuyPriceList(ArrayList<PriceVolumeItem> buyPriceList) {
		this.mBuyPriceList = buyPriceList;
	}

	public ArrayList<PriceVolumeItem> getSellPriceList() {
		return mSellPriceList;
	}

	public void setSellPriceList(ArrayList<PriceVolumeItem> sellPriceList) {
		this.mSellPriceList = sellPriceList;
	}

	
	public double getWeightAveragePrice() {
		return mWeightAveragePrice;
	}

	public void setWeightAveragePrice(double weightAveragePrice) {
		this.mWeightAveragePrice = weightAveragePrice;
	}

	public int getTradeNumber() {
		return mTradeNumber;
	}

	public void setTradeNumber(int tradeNumber) {
		this.mTradeNumber = tradeNumber;
	}


	public static class PriceVolumeItem{
		public double price;
		public int volume;
		
	}
	
	//临时存放，等指数跟个股的结构理清楚了再移除
	public int TotalStocks;//证券总数
	public int RiseCount; //上涨家数
	public int FallCount; //下跌家数
	public double PopcPrice; //盘前盘后价格
	
	public ArrayList<StockRealtime> RiseLeadingStocks;//领涨股
	public ArrayList<StockRealtime> FallLeadingStocks;//领涨股
}
