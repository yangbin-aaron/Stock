package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineBIAS.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明: 乖离率（BIAS）是测量股价偏离均线大小程度的指标
 *　   6日BIAS＞＋4.5％，是卖出时机；＜-4％，为买入时机。
 *　  12日BIAS＞＋6％ 是卖出时机；＜-5.5％,为买入时机。
 *  24日BIAS＞＋9％是卖出时机；＜－8％，为买入时机
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */

public class KlineBIAS {

	public static int[] PARAM_VALUE = {6, 12, 24};//

	private List<List<Double>> biasDataList;
	private List<StockKlineItem> klineData = null;

	public KlineBIAS(List<StockKlineItem> klineData){
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
		if(paramValue==null || paramValue.length<3)
			return;
		if(Arrays.equals(paramValue, PARAM_VALUE)){
			return;
		}else{
			PARAM_VALUE = paramValue;
		}
	}

	/**
	 * 获取指定周期的BIAS指标数据
	 * @param type
	 * @return
	 */
	public List<Double> getBIASList(int type) {
		if(biasDataList==null)
			return null;
		for(int i=0;i<PARAM_VALUE.length;i++){
			if(type==PARAM_VALUE[i]){
				return biasDataList.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取BIAS的数据
	 * @param type 类型
	 * @return BIAS
	 */
	public Double getBIAS(int type, int index) {
		List<Double> biasData = getBIASList(type);
		if(biasData==null){
			return (double) 0;
		}
		if(index<0 || index>=biasData.size()){
			return (double) 0;
		}
		return biasData.get(index);
	}

	/**
	 * 或者指定时间段内的BIAS最大值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	public Double getBIASTopValue(int type, int begin, int end) {
		if(biasDataList==null || biasDataList.size()<=0){
			return (double) 100;
		}
		List<Double> tempBIASList = getBIASList(type);
		return QuoteUtils.getTopValue(tempBIASList, begin, end).doubleValue();
	}

	/**
	 * 或者指定时间段内的BIAS最小值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	public double getBIASBottomValue(int type, int begin, int end) {
		if(biasDataList==null || biasDataList.size()<=0){
			return 0;
		}
		List<Double> tempBIASList = getBIASList(type);
		return QuoteUtils.getBottomValue(tempBIASList, begin, end).doubleValue();
	}

	/**
	 * 初始化bias指标
	 */
	private void init() {
		if(biasDataList==null){
			biasDataList = new ArrayList<List<Double>>();
		}else{
			biasDataList.clear();
		}
		if(klineData==null)
			return;
		int dataSize = klineData.size();
		int biasTypeSize = PARAM_VALUE.length;

		for(int i=0;i<biasTypeSize;i++){
			biasDataList.add( new ArrayList<Double>(dataSize) );
		}

		Double[] closePrice = new Double[dataSize];
		for (int i = 0; i < dataSize; i++) {
			closePrice[i] = klineData.get(i).getClosePrice();
			for(int j=0;j<biasTypeSize;j++){
				int biasType = PARAM_VALUE[j];
				List<Double> tempList = biasDataList.get(j);
				tempList.add(i,calCloseAvg(closePrice, i, biasType) );
			}
		}
	}


	/** 
	 * 计算N日简单平均
	 * @param closePrice 收盘价
	 * @param index 位置
	 * @param dateNum 天数
	 * @return
	 */
	private Double calCloseAvg(Double[] closePrice, int index, int dateNum){
		int defVal = 0;//默认值
		if(null == closePrice || index < 0 )
			return (double) defVal;

		if(index>=closePrice.length)
			return (double) defVal;

		int pos = 0;
		int cnt = dateNum;
		if (index + 1 >= dateNum) {
			pos = index + 1 - dateNum;
		} else {
			pos = 0; //天数未达到
			cnt = index+1;
		}

		double sum = 0;
		for (int i = pos; i <= index && i<closePrice.length; i++) {
			sum += closePrice[i];
		}
		double average = sum/cnt;
		return ((closePrice[index] - average)*100)/average;
	}

	
	public double getMaxValue( int begin, int end ){
		double max = Double.MIN_VALUE;
		for (int i = 0; i < PARAM_VALUE.length; i++) {
			max = Math.max(max, getBIASTopValue( PARAM_VALUE[i], begin, end));
		}
		return max;
	}
	
	public double getMinValue( int begin, int end ){
		double min = Double.MIN_VALUE;
		for (int i = 0; i < PARAM_VALUE.length; i++) {
			min = Math.min(min, getBIASBottomValue( PARAM_VALUE[i], begin, end));
		}
		return min;
	}

}
