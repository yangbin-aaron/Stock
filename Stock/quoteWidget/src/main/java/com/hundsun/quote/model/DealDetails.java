package com.hundsun.quote.model;

import java.util.ArrayList;
/**
 *源程序名称:ClinchDealDetails.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QIIPro-1.1
 *模块名称:成交明细数据模型
 *功能说明:
 *作    者: 梁浩 
 *开发日期:2014-9-28 下午5:02:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class DealDetails {
	
	private ArrayList<StockTickItem> mDealDetails;

	public ArrayList<StockTickItem> getDealDetails() {
		return mDealDetails;
	}

	public void setDealDetails(ArrayList<StockTickItem> ticks) {
		this.mDealDetails = ticks;
	}
	

}
