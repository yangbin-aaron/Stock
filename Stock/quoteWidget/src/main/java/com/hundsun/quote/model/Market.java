package com.hundsun.quote.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Market implements Serializable{
	/**
	 * ID
	 */
	private static final long serialVersionUID = 8621453683338546527L;
	
	protected String mMarketCode; //交易所代码
	protected String mMarketName; //交易所名称
	protected int    mMarketDate;//存在跨日交易 -市场日期
	protected int    mTradeDate;  // 交易日期
	protected int    mTimezone; //确定用类型 -市场所在时区
	protected int    mSummerTimeFlag;//夏令时标志 ， 改为bool
	protected int    mPricePrecision; //价位小数精度
	
	protected ArrayList<TypeItem> mTypeItems; //不需要类型-证券分类Item
	
	protected String mTimeZoneCode;


	public String getTimeZoneCode() {
		return mTimeZoneCode;
	}
	public void setTimeZoneCode(String timeZone) {
		this.mTimeZoneCode = timeZone;
	}
	
	public String getMarketCode() {
		return mMarketCode;
	}
	public void setMarketCode(String marketCode) {
		this.mMarketCode = marketCode;
	}
	public String getMarketName() {
		return mMarketName;
	}
	public void setMarketName(String marketName) {
		this.mMarketName = marketName;
	}
	
	public int getMarketDate() {
		return mMarketDate;
	}
	public void setMarketDate(int marketDate) {
		this.mMarketDate = marketDate;
	}
	public int getTradeDate() {
		return mTradeDate;
	}
	public void setTradeDate(int tradeDate) {
		this.mTradeDate = tradeDate;
	}
	public ArrayList<TypeItem> getTypeItems() {
		return mTypeItems;
	}
	public void setTypeItems(ArrayList<TypeItem> typeItems) {
		this.mTypeItems = typeItems;
	}
	public int getTimezone() {
		return mTimezone;
	}
	public void setTimezone(int timezone) {
		this.mTimezone = timezone;
	}
	public int getSummerTimeFlag() {
		return mSummerTimeFlag;
	}
	public void setSummerTimeFlag(int summerTimeFlag) {
		this.mSummerTimeFlag = summerTimeFlag;
	}
	public int getPricePrecision() {
		return mPricePrecision;
	}
	public void setPricePrecision(int pricePrecision) {
		this.mPricePrecision = pricePrecision;
	}
	
	
}
