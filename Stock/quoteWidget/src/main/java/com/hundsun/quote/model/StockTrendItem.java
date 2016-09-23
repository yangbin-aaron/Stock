package com.hundsun.quote.model;

public class StockTrendItem {
	protected double mPrice;
	protected double mAvg;
	protected double mWavg;
	protected long mVol;
	protected double mMoney;

	public double getPrice() {
		return mPrice;
	}

	public void setPrice(double mPrice) {
		this.mPrice = mPrice;
	}

	public double getAvg() {
		return mAvg;
	}

	public void setAvg(double mAvg) {
		this.mAvg = mAvg;
	}

	public double getWavg() {
		return mWavg;
	}

	public void setWavg(double mWavg) {
		this.mWavg = mWavg;
	}

	public long getVol() {
		return mVol;
	}

	public void setVol(long vol) {
		this.mVol = vol;
	}

	public double getMoney() {
		return mMoney;
	}

	public void setMoney(double money) {
		this.mMoney = money;
	}

}
