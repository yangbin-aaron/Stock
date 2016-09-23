package com.hundsun.quote.model;

import java.util.ArrayList;

public class IndexRealtime extends Realtime{
	protected int mTotalStocks;//证券总数
	
	protected int mRiseCount; //上涨家数
	protected int mFallCount; //下跌家数
	
	protected int mRiseTrend; //上涨趋势
	protected int mFallTrend; //下跌趋势
	
	protected int mADL;//adl指标
	protected int mLead;//领先指标
	
	//股票信息需要股票代码、名称、类型、昨收
	protected ArrayList<Stock> mRiseLeading;//领涨股
	protected ArrayList<Stock> m5MinuteRiseLeading;//5分钟领涨股
	
	protected ArrayList<Stock> mFallLeading;//领跌股
	protected ArrayList<Stock> m5MinuteFallLeading;//5分钟领跌速
	
	public int getTotalStocks() {
		return mTotalStocks;
	}
	public void setTotalStocks(int totalStocks) {
		this.mTotalStocks = totalStocks;
	}
	public int getRiseCount() {
		return mRiseCount;
	}
	public void setRiseCount(int riseCount) {
		this.mRiseCount = riseCount;
	}
	public int getFallCount() {
		return mFallCount;
	}
	public void setFallCount(int fallCount) {
		this.mFallCount = fallCount;
	}

	public int getRiseTrend() {
		return mRiseTrend;
	}
	public void setRiseTrend(int riseTrend) {
		this.mRiseTrend = riseTrend;
	}
	public int getFallTrend() {
		return mFallTrend;
	}
	public void setFallTrend(int fallTrend) {
		this.mFallTrend = fallTrend;
	}
	public int getADL() {
		return mADL;
	}
	public void setADL(int aDL) {
		this.mADL = aDL;
	}
	public int getLead() {
		return mLead;
	}
	public void setLead(int lead) {
		this.mLead = lead;
	}
	public ArrayList<Stock> getRiseLeading() {
		return mRiseLeading;
	}
	public void setRiseLeading(ArrayList<Stock> riseLeading) {
		this.mRiseLeading = riseLeading;
	}
	public ArrayList<Stock> get5MinuteRiseLeading() {
		return m5MinuteRiseLeading;
	}
	public void set5MinuteRiseLeading(ArrayList<Stock> p5MinuteRiseLeading) {
		this.m5MinuteRiseLeading = p5MinuteRiseLeading;
	}
	public ArrayList<Stock> getFallLeading() {
		return mFallLeading;
	}
	public void setFallLeading(ArrayList<Stock> fallLeading) {
		this.mFallLeading = fallLeading;
	}
	public ArrayList<Stock> get5MinuteFallLeading() {
		return m5MinuteFallLeading;
	}
	public void set5MinuteFallLeading(ArrayList<Stock> p5MinuteFallLeading) {
		this.m5MinuteFallLeading = p5MinuteFallLeading;
	}
	
}
