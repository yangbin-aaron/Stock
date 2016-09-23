package com.hundsun.quote.model;

/**
 *源程序名称:StockTickItem.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:成交明细  Item
 *功能说明:
 *作    者: 梁浩 
 *开发日期:2014-9-28 下午5:02:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class StockTickItem {
	private int minutes; // 当前时间（距开盘分钟数）
	private int  tradeFlag; // 是按价成交还是按卖价成交(1 按买价 0 按卖价)
	private double price; // 成交价
	private long  totalVolume; // 累计成交量

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getTradeFlag() {
		return tradeFlag;
	}

	public void setTradeFlag(int tradeFlag) {
		this.tradeFlag = tradeFlag;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(long totalVolume) {
		this.totalVolume = totalVolume;
	}

}
