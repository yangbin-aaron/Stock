package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineRSI.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:相对强弱指数RSI是根据一定时期内上涨和下跌幅度之和的比率制作出的一种技术曲线。能够反映出市场在一定时期内的景气程度。
 * 计算公式
 * N日RSI =N日内收盘涨幅的平均值/(N日内收盘涨幅均值+N日内收盘跌幅均值) ×100%
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineRSI {

	private static int[] PARAM_VALUE = {6, 12, 24};//
	
	private List<List<Double>> rsiDataList;
	
	private List<StockKlineItem> klineData = null;
	
	public KlineRSI(List<StockKlineItem> klineData)
	{
		this.klineData = klineData;
		init();
	}
	
	/**
	 * 设置K线数据
	 * @param klineData
	 */
	public void setKlineData(List<StockKlineItem> klineData)
	{
		this.klineData = klineData;
		init();
	}
	
	/**
	 * 参数设置
	 * @param paramValue
	 */
	public static void setParam(int[] paramValue)
	{
		if(paramValue==null || paramValue.length<3)
			return;
		
		if(Arrays.equals(paramValue, PARAM_VALUE))
			return;
		else
			PARAM_VALUE = paramValue;
	}
	
	/**
	 * 获取指定周期的RSI指标数据
	 * @param type
	 * @return
	 */
	public List<Double> getRSIList(int type) {
		if(rsiDataList==null)
			return null;
		
		for(int i=0;i<PARAM_VALUE.length;i++)
		{
			if(type==PARAM_VALUE[i])
			{
				return rsiDataList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 获取RSI的数据
	 * @param type 类型
	 * @return RSI
	 */
	public double getRSI(int type, int index) {
		List<Double> rsiData = getRSIList(type);
		if(rsiData==null)
		{
			return 0;
		}
		if(index<0 || index>=rsiData.size())
		{
			return 0;
		}
		return rsiData.get(index);
	}
	
	public double getMaxRSIValue( int begin, int end){
		Double maxRsiValue = Double.MIN_VALUE;
		for (int i = 0; i < PARAM_VALUE.length; i++) {
			maxRsiValue = Math.max(maxRsiValue, getMaxRSIValue( PARAM_VALUE[i], begin, end));
		}
		return maxRsiValue;
	}
	
	public double getMinRSIValue( int begin, int end){
		double minRsiValue = Float.MAX_VALUE;
		for (int i = 0; i < PARAM_VALUE.length; i++) {
			minRsiValue = Math.min(minRsiValue, getMinRSIValue( PARAM_VALUE[i], begin, end));
		}
		return minRsiValue;
	}
	
	/**
	 * 或者指定时间段内的RSI最大值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	public double getMaxRSIValue(int type, int begin, int end) {
		if(rsiDataList==null || rsiDataList.size()<=0){
			return 100;
		}
		
		List<Double> tempRSIList = getRSIList(type);
		return QuoteUtils.getTopValue(tempRSIList, begin, end).floatValue();
	}
	
	/**
	 * 或者指定时间段内的RSI最小值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	public double getMinRSIValue(int type, int begin, int end) {
		if(rsiDataList==null || rsiDataList.size()<=0){
			return 0;
		}
		List<Double> tempRSIList = getRSIList(type);
		return QuoteUtils.getBottomValue(tempRSIList, begin, end).floatValue();
	}
	
	/**
	 * 初始化RSI指标
	 */
	private void init() {
		if(rsiDataList==null){
			rsiDataList = new ArrayList<List<Double>>();
		}else{
			rsiDataList.clear();
		}
		if(klineData==null)
			return;
		
		int rsiTypeSize = PARAM_VALUE.length;
		rsiDataList = new ArrayList<List<Double>>();
		List<List<Double>> raiseList = new ArrayList<List<Double>>();//上升均值列表
		List<List<Double>> dropList = new ArrayList<List<Double>>();//下降均值列表
		for(int i=0;i<rsiTypeSize;i++)
		{
			rsiDataList.add( new ArrayList<Double>() );
			raiseList.add(new ArrayList<Double>());
			dropList.add(new ArrayList<Double>());
		}
		
		int dataSize = klineData.size();
		double[] closeDiff = new double[dataSize];
		
		
		for (int i = 0; i < dataSize; i++) {
			if (i == 0) {
				//由于第一天获取不到昨收价，以开盘价代替
				closeDiff[i] = klineData.get(i).getOpenPrice() - klineData.get(i).getClosePrice();
			} else {
				closeDiff[i] = klineData.get(i).getClosePrice()
						- klineData.get(i - 1).getClosePrice();
			}
			for(int j=0;j<rsiTypeSize;j++){
				int rsiType = PARAM_VALUE[j];
				List<Double> tempRaiaeList = raiseList.get(j);
				List<Double> tempDropList = dropList.get(j);
				List<Double> tempRsi = rsiDataList.get(j);
				
				tempRaiaeList.add( calPriceAvgRaise(closeDiff, i, rsiType, tempRaiaeList) );//计算上升平均数
				tempDropList.add( calPriceAvgDrop(closeDiff, i, rsiType, tempDropList) );//计算下降平均数
				tempRsi.add(calRSI(tempRaiaeList, tempDropList, i, rsiType));
			}
		}
	}
	
	/**
	 * 计算上升平均数
	 * @param closeDiff 每日价格变化值
	 * @param index 位置
	 * @param dateNum 天数
	 * @param raiseList 上升平均数列表
	 * @return
	 */
	private double calPriceAvgRaise(double[] closeDiff, int index, int dateNum, List<Double> raiseList){
		int defVal = 50;//比较合理的默认值，代表相对强弱都不足
		if(closeDiff==null || raiseList==null)
			return defVal;
		if(index<0 || index>=closeDiff.length)
			return defVal;
		
		int pos = 0;
		if (index + 1 >= dateNum) {
			pos = index + 1 - dateNum;
		} else {
			return 0; //天算未达到，无法计算有效值
		}
		
		double totalRaiseVal = 0;
		if(raiseList.size()<=dateNum){
			for (int i = pos; i <= index && i<closeDiff.length; i++) {
				if (closeDiff[i] > 0) {
					totalRaiseVal += closeDiff[i];
				}
			}
		}else{
			if(index-1>=raiseList.size())
				return defVal;
			
			if (closeDiff[index] > 0) {//涨
				totalRaiseVal = raiseList.get(index-1)*(dateNum-1)+closeDiff[index];
			}
			else {//跌 
				totalRaiseVal = raiseList.get(index-1)*(dateNum-1);
			}
		}
		return totalRaiseVal/dateNum;
	}
	
	/**
	 * 计算下降平均数
	 * @param closeDiff 每日价格变化值
	 * @param index 位置
	 * @param dateNum 天数
	 * @param dropList 下降平均数列表
	 * @return
	 */
	private double calPriceAvgDrop(double[] closeDiff, int index, int dateNum, List<Double> dropList)
	{
		int defVal = 50;//比较合理的默认值，代表相对强弱都不足
		if(closeDiff==null || dropList==null)
			return defVal;
		if(index<0 || index>=closeDiff.length)
			return defVal;
		
		
		int pos = 0;
		if (index + 1 >= dateNum) {
			pos = index + 1 - dateNum;
		} else {
			return 0; //天算未达到，无法计算有效值
		}
		
		double totalDropVal = 0;
		if(dropList.size()<=dateNum){
			for (int i = pos; i <= index && i<closeDiff.length; i++) {
				if (closeDiff[i] < 0) {
					totalDropVal -= closeDiff[i];
				}
			}
		}else{
			if(index-1>=dropList.size())
				return defVal;
			
			if (closeDiff[index] > 0) {//涨
				totalDropVal = dropList.get(index-1)*(dateNum-1);
			}
			else {//跌 
				totalDropVal = dropList.get(index-1)*(dateNum-1)-closeDiff[index];
			}
		}
		return totalDropVal/dateNum;
	}
	
	/**
	 * 计算公式： N日RSI =N日内收盘涨幅的平均值/(N日内收盘涨幅均值+N日内收盘跌幅均值) ×100%
	 * @param value
	 * @param index
	 * @param dateNum
	 * @return
	 */
	private double calRSI(List<Double> raiseList, List<Double> dropList, int index, int dateNum) {
		if(raiseList==null || dropList==null)
			return 50;
		if(index<0 || index>=raiseList.size())
			return 50;
		if(raiseList.get(index)+dropList.get(index)<=0)
			return 50;
		
		return raiseList.get(index)*100/(raiseList.get(index)+dropList.get(index));
	}

}
