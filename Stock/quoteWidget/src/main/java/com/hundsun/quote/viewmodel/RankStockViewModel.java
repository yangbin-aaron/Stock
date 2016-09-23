package com.hundsun.quote.viewmodel;

import com.hundsun.quote.model.Realtime;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockRealtime;
import com.hundsun.quote.tools.ColorUtils;
import com.hundsun.quote.tools.CommonTools;
import com.hundsun.quote.tools.FormatUtils;

/**
 * 滑动行情列表数据模型
 * @author 梁浩        2015-2-12-上午8:58:50
 *
 */
public class RankStockViewModel {

	public StockRealtime stockRealtime;

	public StockRealtime getStockRealtime() {
		return stockRealtime;
	}
	public void setStockRealtime(StockRealtime stockRealtime) {
		this.stockRealtime = stockRealtime;
	}

	public Stock getStock(){
		Stock stock = null;
		if(stockRealtime == null){
			return null;
		}
		stock = new Stock();
		stock.setCodeType(stockRealtime.getCodeType());
		stock.setPreClosePrice(stockRealtime.getPreClosePrice());
		stock.setStockCode(stockRealtime.getCode());
		stock.setStockName(stockRealtime.getName());
		return stock;
	}

	/**
	 * 根据关键字获取对应的格式化数据【显示名、显示值、字体颜色】
	 * @param key
	 * @return
	 */
	public RankStockResult getStockRankFieldValue(int key){
		RankStockResult result = new RankStockResult();
		if(stockRealtime == null){
			result.setColor(0x7f08003d);
			result.setValue("--");
			result.setKey(0);
			return result;
		}
		double newPrice = stockRealtime.getNewPrice();
		double prevClose = stockRealtime.getPreClosePrice();
		int status = stockRealtime.getTradeStatus();
		int priceColor = ColorUtils.getColor(newPrice, prevClose );
		Stock stock = getStock();
		switch(key){
		case CommonTools.KEY_SORT_STOCK_CODE:
			result.setColor(0x7f040011);//stock_code_color=0x7f080065; complex_ranking_code=0x7f040011;
			result.setValue(getShowCode());
			result.setKey(CommonTools.KEY_SORT_STOCK_CODE);
			break;
		case CommonTools.KEY_SORT_STOCK_NAME:
			result.setColor(0x7f040010);    //complex_ranking_name=0x7f040010;
			result.setValue(getShowName());
			result.setKey(CommonTools.KEY_SORT_STOCK_NAME);
			break;
		case CommonTools.KEY_SORT_NEW_PRICE:
			result.setColor(priceColor);
			result.setValue( getNewPrice( newPrice, status, stock) );
			result.setKey(CommonTools.KEY_SORT_NEW_PRICE);
			break;
		case CommonTools.KEY_SORT_PRICE_CHANGE:
			result.setColor(priceColor);
			result.setValue(stockRealtime.getPriceChange() == 0? "--": stockRealtime.getPriceChange() + "");
			result.setKey(CommonTools.KEY_SORT_PRICE_CHANGE);
			break;
		case CommonTools.KEY_SORT_PRICE_CHANGE_PERCENT:
			result.setColor(priceColor);
			result.setValue(stockRealtime.getPriceChangePrecent() == 0? "--": stockRealtime.getPriceChangePrecent() + "");
			result.setKey(CommonTools.KEY_SORT_PRICE_CHANGE_PERCENT);
			break;
		case CommonTools.KEY_SORT_TOTAL_VOLUME:
			result.setColor(0x7f08003d);//0x7f08003d -- qii_quote_nomal_text_color
			double hand = stockRealtime.getHand();
			if (hand <= 0) {
				hand = 1;
			}
			result.setValue(FormatUtils.formatStockVolume( stock,stockRealtime.getTotalVolume()/hand));
			result.setKey(CommonTools.KEY_SORT_PRICE_CHANGE_PERCENT);
			break;
		case CommonTools.KEY_SORT_CHANGE_HAND_RADIO:
			result.setColor(0x7f08003d);//0x7f08003d -- qii_quote_nomal_text_color
			if (stockRealtime == null || stockRealtime.getNewPrice() == 0 || stockRealtime.getTurnoverRatio() == 0) {
				result.setValue( "--" );
			} else {
				result.setValue(FormatUtils.formatPercent(stockRealtime.getTurnoverRatio()));
			}
			result.setKey(CommonTools.KEY_SORT_CHANGE_HAND_RADIO);
			break;
		case CommonTools.KEY_SORT_CURRENT:
			long current = stockRealtime.getCurrent();
			result.setColor(0x7f08003d);//0x7f08003d -- qii_quote_nomal_text_color
			result.setValue(FormatUtils.formatBigNumber(current));
			result.setKey(CommonTools.KEY_SORT_CURRENT);
			break;
		}
		return result;
	}

	/**
	 * 现价处理
	 * @param newPrice
	 * @param status
	 * @param stock
	 * @return
	 */
	protected String getNewPrice(  double newPrice, int status, Stock stock) {
		String result = "";
		if (status == Realtime.TRADE_STATUS_HALT) {
		}
		if(stock != null){
			result = (FormatUtils.formatPrice(stock, newPrice));
		}else{
			result = ("--");
		}
		return result;
	}

	/**
	 * 获取股票显示名称
	 * @return
	 */
	private String getShowName() {
		if(stockRealtime == null){
			return "--";
		}
		String name = stockRealtime.getName();
		return name;
	}

	/**
	 * 代码显示处理
	 * @return
	 */
	public String getShowCode() {
		if(stockRealtime == null){
			return "--";
		}
		return stockRealtime.getCode();
	}


}
