package com.hundsun.quote.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.text.TextUtils;

import com.hundsun.quote.model.Market;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.TradeTime;
import com.hundsun.quote.model.TypeItem;

public class QuoteUtils {
	public static HashMap<String, Market> sMarketItem = new HashMap<String, Market>();
	public static HashMap<String, TypeItem> sMarketTypeMap = new HashMap<String, TypeItem>();
	
	public static String getMarketTypeTimeZone( String marketType ){
		String zone = "EST";
		
		if (null == marketType || marketType.length() == 0) {
			return zone;
		}
		marketType = marketType.toUpperCase(Locale.getDefault()).split("\\.")[0];
		if (sMarketItem.containsKey(marketType)) {
			zone = sMarketItem.get(marketType).getTimeZoneCode();
		}
		if (TextUtils.isEmpty(zone)) {
			zone = "EST";
		}
		
		return zone;
	}
	
	public static long getMarketTypeTradeDate( Stock stock ){
		if ( null == stock) {
			return 0;
		}
		return getMarketTypeTradeDate(stock.getCodeType());
	}
	
	public static long getMarketTypeTradeDate( String marketType ){
		long date = 0;
		
		if (null == marketType || marketType.length() == 0) {
			return date;
		}
		marketType = marketType.toUpperCase(Locale.getDefault());
		if (sMarketTypeMap.containsKey(marketType)) {
			date = sMarketTypeMap.get(marketType).getTradeDate();
		}
		
		return date;
	}

	public static boolean isDtk(){
		return false;
	}
	
	/**
	 * 根据codeType获取默认的手数单位
	 * @param codeType
	 * @author LiangHao
	 * @return
	 */
	public static int getPriceUnit(String codeType){
		if (TextUtils.isEmpty(codeType)) {
			return 1000;
		}
		codeType = codeType.toUpperCase(Locale.getDefault());
		if (sMarketTypeMap.containsKey(codeType)) {
			TypeItem marketType = sMarketTypeMap.get(codeType);
			return marketType.getPriceUnit();
		}
		return 1000;
	}
	/**
	 * 根据Stock获取默认的手数单位
	 * @param stock
	 * @author LiangHao
	 * @return
	 */
	public static int getPriceUnit(Stock stock){
		if (null != stock) {
			return getPriceUnit(stock.getCodeType());
		}
		return 1000;
	}

	/**
	 * 根据市场类型获取价格精度
	 * @param marketCode 证券代码  /默认2位精度 
	 * @return
	 */
	public static int getPricePrecision(String marketCode){
		if (!TextUtils.isEmpty(marketCode)) {
			marketCode = marketCode.toUpperCase(Locale.getDefault());
			if (sMarketTypeMap.containsKey(marketCode)) {
				return sMarketTypeMap.get(marketCode).getPricePrecision();
			}
		}
		return 2;
	}

	/**
	 * 根据市场类型获取开闭市时间
	 * @param stock
	 * @return
	 */
	public static List<TradeTime> getOpenCloseTradeTime(Stock stock) {
		if(sMarketTypeMap == null){
			return getDefaultOpenCloseTime();
		}
		if(null != stock) {
			String codeType = stock.getCodeType().toUpperCase(Locale.getDefault());
			if(!TextUtils.isEmpty(codeType)){
				if (sMarketTypeMap.containsKey(codeType)) {
					TypeItem marketType = sMarketTypeMap.get(codeType);
					return marketType.getTradeTimes();
				}else{
					getDefaultOpenCloseTime();
				}
			}

		}
		return getDefaultOpenCloseTime();
	}


	private static List<TradeTime> getDefaultOpenCloseTime() {
		List<TradeTime> openCloseTimes = new ArrayList<TradeTime>();
		openCloseTimes.add(new TradeTime((short)930, (short)1130));
		openCloseTimes.add(new TradeTime((short)1300, (short)1500));
		return openCloseTimes;
	}

	/**
	 * 计算指定开闭市时间内容的分时线数量
	 * @return
	 */
	public static int calculateOpenCloseTotalMinute(TradeTime[] openCloseTime)
	{
		int totalMinute = 241;
		if(openCloseTime==null){
			return totalMinute;
		}

		TradeTime time = null;
		totalMinute = 0;
		for (int i = 0; i < openCloseTime.length; i++) {
			time = openCloseTime[i];
			if(time!=null && time.getOpenTime() != -1 && time.getCloseTime() != -1) {
				totalMinute += time.getCloseTime() - time.getOpenTime();
			}
		}
		return totalMinute;
	}

	/**
	 * 计算指定开闭市时间内容的分时线数量
	 * @return
	 */
	public static int calculateOpenCloseTotalMinute(List<TradeTime> openCloseTime)
	{
		if(openCloseTime==null)
			return 241;

		TradeTime[] tempData = new TradeTime[openCloseTime.size()];
		int i = 0;
		for(TradeTime secuTypeTime:openCloseTime)
		{
			tempData[i++] = secuTypeTime;
		}
		return calculateOpenCloseTotalMinute(tempData);
	}

	/**
	 * 获取列表中的最大数值
	 * @param list
	 * @return
	 */
	public static Number getTopValue(List<?> list){
		if(list==null || list.size()<=0)
			return 0;

		return getTopValue(list, 0, list.size()-1);
	}

	/**
	 * 获取列表中的最大数值
	 * @param list
	 * @return
	 */
	public static Number getTopValue(List<?> list, int begin, int end){
		if(list==null)
			return 0;

		if(begin<0 ){
			begin = 0;
		}
		if(end<0 || end>=list.size()){
			end = list.size()-1;
		}
		if(begin>end){
			begin = end;
		}

		Number ret = (Number) list.get(begin);
		for (int i = begin; i<list.size() && i <= end; i++) {
			Object tmp = list.get(i);
			if(tmp instanceof Number && ((Number) tmp).doubleValue()>ret.doubleValue()){
				ret = (Number) tmp;
			}
		}
		return ret;
	}

	/**
	 * 获取列表中的最小值
	 * @param list
	 * @return
	 */
	public static Number getBottomValue(List<?> list)
	{
		if(list==null || list.size()<=0)
			return 0;

		return getBottomValue(list, 0, list.size());
	}

	/**
	 * 获取列表指定范围内的最小值
	 * @param list
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Number getBottomValue(List<?> list, int begin, int end){
		if(list==null || list.size()<=0)
			return 0;

		if(begin<0 )
			begin = 0;
		if(end<0 || end>=list.size())
			end = list.size()-1;
		if(begin>end)
			begin = end;

		if(!(list.get(begin) instanceof Number) )
			return 0;

		Number ret = (Number) list.get(begin);
		for (int i = begin; i < list.size() && i<=end; i++) {
			Object tmp = list.get(i);
			if(tmp instanceof Number 
					&& ((Number) tmp).doubleValue()<ret.doubleValue())
			{
				ret = (Number) tmp;
			}
		}
		return ret;
	}

	/**
	 * 获取列表中的满足大于0的最小数值
	 * @param list
	 * @return
	 */
	public static Number getBottomValue_largerZero(List<?> list)
	{
		if(list==null || list.size()<=0)
			return 0;

		return getBottomValue_largerZero(list, 0, list.size());
	}

	/**
	 * 获取列表中的满足大于0的最小数值
	 * @param list
	 * @return
	 */
	public static Number getBottomValue_largerZero(List<?> list, int begin, int end)
	{
		if(list==null || list.size()<=0)
			return 0;

		if(begin<0 )
			begin = 0;
		if(end<0 || end>=list.size())
			end = list.size()-1;
		if(begin>end)
			begin = end;

		if(!(list.get(begin) instanceof Number) )
			return 0;

		Number ret = (Number) list.get(begin);
		for (int i = begin; i < list.size() && i<=end; i++) {
			Object tmp = list.get(i);
			if( tmp instanceof Number )
			{
				if( ((Number) tmp).doubleValue()>0 )
				{
					if( ((Number) ret).doubleValue()==0 )
					{
						//初始有效值，第一个非零值
						ret = (Number) tmp;
					}
					else if ( ((Number) tmp).doubleValue() < ((Number) ret).doubleValue() ) {
						//更新最小值
						ret = (Number) tmp;
					}
				}
			}
		}
		return ret;
	}

	static final float min = 0.001f;
	static final float min_d = 0;

	/**
	 * 判断浮点是否为0
	 * 
	 *@author heke   2012-3-30
	 * @param float
	 * @return
	 */
	public static boolean isFloatZero(float num) {
		if (num < min && num > -min)
			return true;
		return false;
	}

	/**
	 * 判断浮点是否为0
	 * 
	 *@author heke   2012-3-30
	 * @param float
	 * @return
	 */
	public static boolean isDoubleZero(double num) {
		if (num < min_d && num > -min_d)
			return true;
		return false;
	}
	/**
	 * 对沪深行情传来的分钟数做显示格式化
	 * @param time
	 * @return
	 */
	public static String getFormatClinchDealDetailsTime(float time){
		DecimalFormat df = new DecimalFormat("00");
		df.setMaximumIntegerDigits(2);
		String hour = df.format((short)(time/60));
		String min = df.format((short)(time%60));
		return hour +":" + min; 
	}

	static DecimalFormat DECIMALFORMAT_0 = new DecimalFormat("#00");
	public static String  formatTime( int time){
		return DECIMALFORMAT_0.format( time/100) +":"+ DECIMALFORMAT_0.format( time%100);
	}

}
