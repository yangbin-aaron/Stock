package com.hundsun.quote.model;

import java.io.Serializable;

public class TradeTime implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -2536562569718650947L;

	// 开市时间
	private int mOpenTime;
	// 闭式时间
	private int mCloseTime;

	public TradeTime(int openTime, int closeTime) {
		mOpenTime = openTime;
		mCloseTime = closeTime;
	}

	public int getOpenTime() {
		return mOpenTime;
	}

	/**
	 * 获取开市时间
	 * 
	 * @return
	 */
	public void setOpenTime(int openTime) {
		mOpenTime = openTime;
	}

	/**
	 * 获取闭市时间
	 * 
	 * @return
	 */
	public int getCloseTime() {
		return mCloseTime;
	}

	public void setCloseTime(int closeTime) {
		mCloseTime = closeTime;
	}
}
