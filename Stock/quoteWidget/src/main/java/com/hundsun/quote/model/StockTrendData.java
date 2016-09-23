package com.hundsun.quote.model;

import java.util.ArrayList;
/**
 * 分时结构
 * @author lianghao
 *
 */
public class StockTrendData {
	private ArrayList<StockTrendItem> mItems = new ArrayList<StockTrendItem>();
	private int mDate;
	
	
	public ArrayList<StockTrendItem> getItems() {
		return mItems;
	}
	public void setItems(ArrayList<StockTrendItem> items) {
		this.mItems = items;
	}
	public int getDate() {
		return mDate;
	}
	public void setDate(int date) {
		this.mDate = date;
	}
	
	
}
