package com.hundsun.quote.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.hundsun.quote.model.DealDetails;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockTickItem;
import com.hundsun.quote.model.TradeTime;
import com.hundsun.quote.tools.QuoteUtils;

/**
 *源程序名称:ClinchDealDetails.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QIIPro-1.1
 *模块名称:成交明细 ViewMode
 *功能说明:
 *作    者: 梁浩 
 *开发日期:2014-9-28 下午5:02:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class TickViewModel extends ViewModel{
	private DealDetails mDealDetails;
	
	public TickViewModel(Stock stock) {
		super(stock);
	}

	public DealDetails getDealDetailsData(){
		return mDealDetails;
	}

	public void setDealDetailsData(DealDetails data){
		mDealDetails = data;
	}

	public StockTickItem getDealDetailsItem( int idx ){
		if (null == mDealDetails) {
			return null;
		}
		ArrayList<StockTickItem> items = mDealDetails.getDealDetails();
		if (idx >= 0 && idx < items.size()) {
			return items.get(idx);
		}
		return null;
	}

	public int getDataCount(){
		if (null != mDealDetails) {
			return mDealDetails.getDealDetails().size();
		}
		return 0;
	}
	
	/**
	 * 获取开闭市时间
	 * @return
	 */
	public List<TradeTime> getOpenCloseTime() {
		return QuoteUtils.getOpenCloseTradeTime(mStock);
	}

	
}
