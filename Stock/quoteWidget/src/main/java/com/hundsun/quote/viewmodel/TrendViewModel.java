package com.hundsun.quote.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockTrendData;
import com.hundsun.quote.model.StockTrendItem;
import com.hundsun.quote.model.TradeTime;
import com.hundsun.quote.tools.QuoteUtils;

public class TrendViewModel extends ViewModel{
	public TrendViewModel(Stock stock) {
		super(stock);
	}

	private StockTrendData mTrendData;

	public StockTrendData getTrends(){
		return mTrendData;
	}

	public void setTrends(StockTrendData data){
		mTrendData = data;
	}

	public StockTrendItem getTrendItem( int idx ){
		if (null == mTrendData) {
			return null;
		}
		ArrayList<StockTrendItem> items = mTrendData.getItems();
		if (idx >= 0 && idx < items.size()) {
			return items.get(idx);
		}
		return null;
	}
	
	public String getTime( int index ){
		String time = "";
		
		List<TradeTime> openCloseTimes = getOpenCloseTime();
		if (null == openCloseTimes) {
			return time;
		}
		
		int currentIndex = 0;
		for (int i = 0 , count = openCloseTimes.size(); i < count; i++) {
			TradeTime item = openCloseTimes.get(i);
			int closeTime = item.getCloseTime();
			int openTime  = item.getOpenTime();
			int currentTimes = 60*(closeTime/100 - openTime/100) + ( closeTime%100 - openTime%100 ) ;
			
			if ( ( currentTimes+currentIndex ) >= index) {
				int h = (index - currentIndex)/60;
				int m = (index - currentIndex)%60;
				m += openTime%100;
				h += openTime/100 + m/60;
				m %= 60;
				time = QuoteUtils.formatTime( h*100 + m);
				break;
			}
			currentIndex += currentTimes;
		}
		
		return time;
	}

	/**
	 * 获取开闭市时间
	 * @return
	 */
	public List<TradeTime> getOpenCloseTime() {
		return QuoteUtils.getOpenCloseTradeTime(mStock);
	}

	/*	
	 *去掉快照相关的数据，昨收已经移入
	 *public double getPrevClosePrice() {
		if (null != mRealtime) {
			return mRealtime.getPrevClosePrice()/QuoteUtils.getPriceUnit(mStock);
		}
		return 1030600;
	 *
	}*/

	public double getMaxPrice() {
		if (null == mTrendData) {
			return 0;
		}
		ArrayList<StockTrendItem> items = mTrendData.getItems();
		double max = 0;
		for (int i = 0; i < items.size(); i++) {
			StockTrendItem stockTrendItem = items.get(i);
			max = Math.max(max, stockTrendItem.getPrice());
			double wavg = stockTrendItem.getWavg();
			if (0.000001 < wavg) {
				max = Math.max(max, wavg);
			}
		}
		return max;
	}

	public double getMinPrice() {
		if (null == mTrendData) {
			return 0;
		}

		ArrayList<StockTrendItem> items = mTrendData.getItems();
		double min = Integer.MAX_VALUE;
		for (int i = 0; i < items.size(); i++) {
			min = Math.min(min, items.get(i).getPrice());
			StockTrendItem stockTrendItem = items.get(i);
			double wavg = stockTrendItem.getWavg();
			if (0.000001 < wavg) {
				min = Math.min(min, wavg);
			}
		}
		return min;

	}

	public long getMaxVolume() {
		if (null == mTrendData) {
			return 0;
		}
		
		ArrayList<StockTrendItem> items = mTrendData.getItems();
		if (items == null || items.size() <=0) {
			return 0;
		}

		long max = items.get(0).getVol();
		long lastVolume = items.get(0).getVol();
		for (int i = 1; i < items.size(); i++) {
			long volume = items.get(i).getVol() ;
			if (max <volume ) {
				max = volume ;
			}
			lastVolume = items.get(i).getVol();
		}
		return max;
	}

	public int getTrendsCount() {
		if (null == mTrendData) {
			return 0;
		}
		return mTrendData.getItems().size();
	}

}
