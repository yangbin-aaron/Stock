package com.hundsun.quote.model;

/**
 * 自定义市场类型模型
 * @author 梁浩        2014-11-28-下午14:09:45
 *
 */
public class MarketTypeItem {
	private String name;
	private String h5Value;
	private String dtkValue;
	private String desc;
	private String tag;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getH5Value() {
		return h5Value;
	}
	public void setH5Value(String h5Value) {
		this.h5Value = h5Value;
	}
	public String getDtkValue() {
		return dtkValue;
	}
	public void setDtkValue(String dtkValue) {
		this.dtkValue = dtkValue;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

}
