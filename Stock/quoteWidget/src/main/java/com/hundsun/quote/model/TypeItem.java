package com.hundsun.quote.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TypeItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1643346268414710767L;

	protected String mMarketCode;//交易所代码

	protected String mMarketTypeCode;
	protected String mMarketTypeName;
	protected int    mPriceUnit;
	protected int    mPricePrecision;
	protected int    mTradeDate; 
	protected int    mHand;//每手证券数量

	protected ArrayList<TradeTime> mTradeTimes;

	public String getMarketTypeCode() {
		return mMarketTypeCode;
	}
	public void setMarketTypeCode(String marketCode) {
		this.mMarketTypeCode = marketCode;
	}
	public String getMarketTypeName() {
		return mMarketTypeName;
	}
	public void setMarketTypeName(String marketName) {
		this.mMarketTypeName = marketName;
	}
	public int getPriceUnit() {
		return mPriceUnit;
	}
	public void setPriceUnit(int priceUnit) {
		this.mPriceUnit = priceUnit;
	}
	public int getTradeDate() {
		return mTradeDate;
	}
	public void setTradeDate(int tradeDate) {
		this.mTradeDate = tradeDate;
	}
	public ArrayList<TradeTime> getTradeTimes() {
		return mTradeTimes;
	}
	public void setTradeTimes(ArrayList<TradeTime> tradeTimes) {
		this.mTradeTimes = tradeTimes;
	}
	
	public  void setPricePrecision( int precision ){
		mPricePrecision = precision;
	}
	
	public int getPricePrecision(){
		return mPricePrecision;
	}

	//在此补充类型接口，股票、指数、债券、基金等
}
