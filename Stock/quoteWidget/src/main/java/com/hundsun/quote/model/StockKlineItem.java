package com.hundsun.quote.model;

/**
 * 日线数据结构
 * 
 * @author huangcheng \ LiangHao 
 *  
 *  修改 高开低收 类型修改 int - > double
 * 
 */
public class StockKlineItem {
	// 日期
	public int date;
	// 时间
	public int time;
	// 开盘价
	public double openPrice;
	// 最高价
	public double highPrice;
	// 最低价
	public double lowPrice;
	// 收盘价
	public double closePrice;
	// 成交金额
	public long money;
	// 成交量
	public long volume;

	public long getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public double getOpenPrice() {
		return openPrice;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(double heighPrice) {
		this.highPrice = heighPrice;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long total) {
		this.volume = total;
	}
}
