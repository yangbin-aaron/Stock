package com.hundsun.quote.model;

import java.util.ArrayList;


public class StockKline {
	
	public static final short PERIOD_TYPE_DAY   = 0x0; // 周期：日
	public static final short PERIOD_TYPE_WEEK  = 0x1; // 周期：一周
	public static final short PERIOD_TYPE_MONTH = 0x2; // 周期：一个月
	public static final short PERIOD_TYPE_YEAR  = 0x3; // 周期：一个月
	
	public static final short PERIOD_TYPE_MINUTE1  = 0x4; // 周期：1分钟
	public static final short PERIOD_TYPE_MINUTE5  = 0x5; // 周期：5分钟
	public static final short PERIOD_TYPE_MINUTE15 = 0x6; // 周期：15分钟
	public static final short PERIOD_TYPE_MINUTE30 = 0x7; // 周期：30分钟
	public static final short PERIOD_TYPE_MINUTE60 = 0x8; // 周期：1小时
	
	
	protected ArrayList<StockKlineItem> mKlineDatas;
	protected int mPeroid;
	
	public ArrayList<StockKlineItem> getKlineDatas() {
		return mKlineDatas;
	}
	public void setKlineDatas(ArrayList<StockKlineItem> klineDatas) {
		this.mKlineDatas = klineDatas;
	}
	public int getPeroid() {
		return mPeroid;
	}
	public void setPeroid(int peroid) {
		this.mPeroid = peroid;
	}
}
