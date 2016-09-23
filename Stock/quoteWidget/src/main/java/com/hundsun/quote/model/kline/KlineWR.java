package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineWR.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:WR指标，中文称威廉指标，股票术语，表示当天的收盘价在过去一段日子的全部价格范围内所处的相对位置，
 *      是一种兼具超买超卖和强弱分界的指标。它主要的作用在于辅助其他指标确认讯号
 *      WR指标计算方法:
 *        WR(T) = 100 * [ HIGH(T)-C ] / [ HIGH(T)-LOW(T) ]
 *        C：当日收盘价   HIGH(T)：T日内的最高价   LOW(T)：T日内的最低价
 *作    者: 梁浩 
 *开发日期:2014-10-10 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineWR {

	public static int[] PARAM_VALUE = {14, 28};//TODO LiangHao 建议调整为10,6
	private List<StockKlineItem> klineData = null;
	private List<List<Double>> wrDataList;

	public KlineWR(List<StockKlineItem> klineData){
		this.klineData = klineData;
		init();
	}

	/**
	 * 设置K线数据
	 * @param klineData
	 */
	public void setKlineData(List<StockKlineItem> klineData){
		this.klineData = klineData;
		init();
	}

	/**
	 * 参数设置
	 * @param paramValue
	 */
	public static void setParam(int[] paramValue){
		if(paramValue==null || paramValue.length<2)
			return;
		if(Arrays.equals(paramValue, PARAM_VALUE)){
			return;
		}else{
			PARAM_VALUE = paramValue;
		}
	}

	/**
	 * 初始化指标
	 */
	private void init() {
		if(wrDataList==null){
			wrDataList = new ArrayList<List<Double>>();
		}else{
			wrDataList.clear();
		}
		if(klineData==null){
			return;
		}
		int wrSize = PARAM_VALUE.length;
		for(int i=0;i<wrSize;i++){
			wrDataList.add( new ArrayList<Double>() );
		}

		int dataSize = klineData.size();
		List<Double> maxPrice = new ArrayList<Double>();
		List<Double> minPrice = new ArrayList<Double>();
		for (int i = 0; i < dataSize; i++) {
			maxPrice.add(klineData.get(i).getHighPrice());
			minPrice.add(klineData.get(i).getLowPrice());
			for(int j=0;j<wrSize;j++){
				int wrType = PARAM_VALUE[j];
				double tempMostHigh = QuoteUtils.getTopValue(maxPrice, i-wrType+1, i).doubleValue();
				double tempMostLow = QuoteUtils.getBottomValue_largerZero(minPrice, i-wrType+1, i).doubleValue();
				if(tempMostHigh<=tempMostLow){
					tempMostHigh = tempMostLow+1;//异常值保护，防止计算异常
				}
				List<Double> tempWr = wrDataList.get(j);
				tempWr.add( ((tempMostHigh-klineData.get(i).getClosePrice())*100 /(tempMostHigh-tempMostLow)) );
			}
		}
	}

	/**
	 * 获取WR数据
	 * @param type
	 * @param index
	 * @return
	 */
	public double getWR(int type, int index){
		List<Double> wrData = getWRList(type);
		if(wrData==null){
			return 0;
		}
		if(index<0 || index>=wrData.size()){
			return 0;
		}
		return wrData.get(index);
	}

	/**
	 * 获取指定周期的WR指标数据
	 * @param type
	 * @return
	 */
	public List<Double> getWRList(int type) {
		if(wrDataList==null)
			return null;
		for(int i=0;i<PARAM_VALUE.length;i++){
			if(type==PARAM_VALUE[i]){
				return wrDataList.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取Wr指标中的最小值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	private double getWrMinValue(int type, int begin, int end) {
		if(wrDataList==null || wrDataList.size()<=0){
			return 0;
		}
		List<Double> tempWrList = getWRList(type);
		return QuoteUtils.getBottomValue(tempWrList, begin, end).doubleValue();
	}

	/**
	 * 获取Wr指标中的最大值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	private double getWRMaxValue(int type, int begin, int end) {
		if(wrDataList==null || wrDataList.size()<=0){
			return 100;
		}
		List<Double> tempWrList = getWRList(type);
		return QuoteUtils.getTopValue(tempWrList, begin, end).doubleValue();
	}
	
	public double getWRMinValue( int begin, int end) {
		double min = Double.MAX_VALUE;
		for(int i=0;i<PARAM_VALUE.length;i++){
			double minvalue = getWrMinValue( PARAM_VALUE[i] , begin , end );
			min = Math.min( min, minvalue);
		}
		return min;
	}
	
	public double getWRMaxValue( int begin, int end) {
		double maxValue = Integer.MIN_VALUE;
		for(int i=0;i<PARAM_VALUE.length;i++){
			double max = getWRMaxValue( PARAM_VALUE[i] , begin , end );
			maxValue = Math.max( maxValue, max);
		}
		return maxValue;
	}
	
	
}
