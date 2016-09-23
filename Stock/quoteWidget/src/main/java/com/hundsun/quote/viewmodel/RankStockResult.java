package com.hundsun.quote.viewmodel;

/**
 * 排序股票处理结果
 * @author 梁浩        2015-1-3-上午1:10:44
 *
 */
public class RankStockResult {
	/*排序列表内容关键字*/
	private int key;
	/*排序列表内容*/
	private String value;
	/*排序列表内容颜色值*/
	private int color;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}

}
