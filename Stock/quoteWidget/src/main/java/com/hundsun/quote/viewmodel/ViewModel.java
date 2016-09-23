package com.hundsun.quote.viewmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.hundsun.quote.model.Realtime;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockCategory;
import com.hundsun.quote.model.StockRealtime;

/**
 * 考虑快照数据重用
 * @author 梁浩        2015-2-12-上午8:59:58
 *
 */
public abstract class ViewModel {
	private LinkedList<IObserver> mObservers = new LinkedList<ViewModel.IObserver>();
	protected Stock mStock;
	protected Realtime mRealtime;
	/*当前股票所属板块信息*/
	protected ArrayList<StockCategory> mCategorys;
	
	public ViewModel(Stock stock){
		mStock = stock;
	}
	public ViewModel(){
	}
	
	
	public Realtime getRealtime() {
		return mRealtime;
	}


	public void setRealtime(Realtime mRealtime) {
		this.mRealtime = mRealtime;
	}


	public Stock getStock(){
		return mStock;
	}
	
	public void setStock(Stock stock){
		mStock = stock;
	}
	
	/**
	 * 主推订阅
	 */
	public void subscribeAutopush(){
		
	}
	/**
	 * 取消主推订阅
	 */
	public void unSubscribeAutopush(){
		
	}
	
	/**
	 * 接收到主推后触发
	 * @param realtime
	 */
	public void onAutoPush( StockRealtime realtime){
		
	}
	
	/**
	 * 获取关联板块信息
	 * @return
	 */
	public ArrayList<StockCategory> getCategorys(){
		return mCategorys;
	}
	
	
	public void register(IObserver observer)
    {
		mObservers.offer(observer);
    }
	
	public void unregister(IObserver observer)
    {
		mObservers.remove(observer);
    }
	
	public void notifyObserver(){
		Iterator<IObserver> iterator = mObservers.iterator();
		while (iterator.hasNext()) {
			ViewModel.IObserver observer = (ViewModel.IObserver) iterator.next();
			observer.Update();
		}
	}
	
	public static interface IObserver {
        public abstract void Update();
    }
	
	/**
	 * 返回每手股数
	 * @return
	 */
	public float getStocksPerHand(){
		if ( null == mRealtime ) {
			return 100;
		}
		int hand = mRealtime.getHand();
		return  hand == 0 ? 100 : hand;
	}
	
}
