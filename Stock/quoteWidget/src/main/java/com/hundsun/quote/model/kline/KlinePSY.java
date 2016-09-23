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
 *功能说明:PSY指标又称心理线指标，是从英文Phycholoigical Line直译过来的，是研究投资者对股市涨跌产生心理波动的情绪指标，是一种能量类和涨跌类指标，它对股市短期走势的研判具有一定的参考意义。
 * 计算公式
 * 1.PSY=N日内上涨天数/N*100
 * 2.PSYMA=PSY的M日简单移动平均
 * 2.参数N设置为12日，参数M设置为6日
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlinePSY {

	private static int[] PARAM_VALUE = {12, 6};//
	
	private List<Double> PSYList;//psy数据 数值范围：0-100 
	private List<Double> PSYMAList;//psy移到平均线 数值范围：0-100
	
	private List<StockKlineItem> klineData = null;
	
	public KlinePSY(List<StockKlineItem> klineData)
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
		if(paramValue==null || paramValue.length<2)
			return;
		
		if(Arrays.equals(paramValue, PARAM_VALUE))
			return;
		else
			PARAM_VALUE = paramValue;
	}
	
	/**
	 * 获取PSY列表的大小
	 * @return
	 */
	public int getSize()
	{
		if(PSYList==null)
			return 0;
		
		return PSYList.size();
	}
	
	/**
	 * 获取PSY的数据
	 * 
	 * @return PSY
	 */
	public double getPSY(int index) {
		if(PSYList==null)
		{
			return 0;
		}
		if(index<0 || index>=PSYList.size() )
		{
			return 0;
		}
		
		return PSYList.get(index);
	}
	
	/**
	 * 获取PSYMA数据
	 * @return
	 */
	public double getPSYMA(int index)
	{
		if(PSYMAList==null)
		{
			return 0;
		}
		if(index<0 || index>=PSYMAList.size() )
		{
			return 0;
		}
		
		return PSYMAList.get(index);
	}
	
	/**
	 * 获取PSY与PSYMA列表中最小值
	 * @param begin
	 * @param end
	 * @return
	 */
	public double getPSYAndPSYMABottomValue(int begin, int end)
	{
		if(PSYList==null || PSYList.size()<=0)
		{
			return 0;
		}
		
		double bottomPSY = QuoteUtils.getBottomValue(PSYList, begin, end).floatValue();
		double bottomPSYMA = QuoteUtils.getBottomValue(PSYMAList, begin, end).floatValue();
		
		double bottom = bottomPSY;
		if(bottomPSYMA>0 && bottomPSYMA<bottom)
			bottom = bottomPSYMA;
		
		return bottom;
	}
	
	/**
	 * 获取PSY与PSYMA列表中最大值
	 * @param begin
	 * @param end
	 * @return
	 */
	public double getPSYAndPSYMATopValue(int begin, int end)
	{
		if(PSYList==null || PSYList.size()==0)
			return 0;
		
		double topPSY = QuoteUtils.getTopValue(PSYList, begin, end).floatValue();
		double topPSYMA = QuoteUtils.getTopValue(PSYMAList, begin, end).floatValue();
		
		double top = topPSY;
		if(topPSYMA>top)
			top = topPSYMA;
		
		return top;
	}
	
	
	/**
	 * 初始化psy数据
	 */
	private void init() {
		if(PSYList==null)
		{
			PSYList = new ArrayList<Double>();
		}
		PSYList.clear();
		if(PSYMAList==null)
		{
			PSYMAList = new ArrayList<Double>();
		}
		PSYMAList.clear();
		
		if(klineData==null)
			return;
		
		
		int psyParam = PARAM_VALUE[0];//
		int psymaParam = PARAM_VALUE[1];//
		
		int dataSize = klineData.size();
		double[] closePrice = new double[dataSize];
		double tempPsy = 0;
		double tempPsyma = 0;
		for (int i = 0; i < klineData.size(); i++) {
			closePrice[i] = klineData.get(i).getClosePrice();
			tempPsy = calPSY(closePrice, i, psyParam)*100 / psyParam;
			PSYList.add( tempPsy);
			tempPsyma = calPSYMA(PSYList, i, psymaParam);
			PSYMAList.add( tempPsyma);
		}
	}
	
	/**
	 * 计算PSY
	 * @param value
	 * @param index
	 * @param n
	 * @return
	 */
	private int calPSY(double[] value, int index, int n) {
		int s = 0;
		if (index + 1 >= n) {
			s = index + 1 - n;
		} else {
			return 0;
		}
		int r = 0;
		if (s > 0 && value[s - 1] < value[s]) {
			r++;
		}
		for (int i = s; i < index; i++) {
			if (value[i] < value[i + 1]) {
				r++;
			}
		}
		return r;
	}
	
	/**
	 * 计算PSY的简单移动值
	 * @param psyList
	 * @param index
	 * @param movingAverage
	 * @return
	 */
	private double calPSYMA(List<Double> psyList, int index, int movingAverage) {
		if(psyList==null || psyList.size() == 0)
			return 0;
		if(index>=psyList.size())
		{
			index = psyList.size()-1;
		}
		if(movingAverage>psyList.size())
		{
			movingAverage = psyList.size();
		}
		
		double psyTotal = 0;
		for(int i=index;i>=0 && index-i<movingAverage;i--)
		{
			psyTotal += psyList.get(i);
		}
		return psyTotal/movingAverage;
	}

}
