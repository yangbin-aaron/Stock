package com.hundsun.quote.viewmodel;

import java.util.ArrayList;

import com.hundsun.quote.model.Stock;

public class QueryStockViewModel extends ViewModel {
	
	protected ArrayList<Stock> mStocks;
	
	public void setStocks( ArrayList<Stock> stocks ){
		mStocks = stocks;
	}
	
	public ArrayList<Stock> getStocks(){
		return mStocks;
	}

	public QueryStockViewModel(Stock stock) {
		super(stock);
	}
	

}
