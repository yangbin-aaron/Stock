package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineKDJ.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:通过一个特定的周期（常为9日、9周等）内出现过的最高价、最低价及最后一个计算周期的收盘价及这三者之间的比例关系，
 *      来计算最后一个计算周期的未成熟随机值RSV，
 *      然后根据平滑移动平均线的方法来计算K值、D值与J值，并绘成曲线图来研判股票走势
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineKDJ {

	private static int[] PARAM_VALUE = { 9, 3, 3 };
	
	private List<StockKlineItem> klineData = null;
	
	private List<Double> KList;
	private List<Double> DList;
	private List<Double> JList;
	
	public KlineKDJ(List<StockKlineItem> klineData)
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
	 * 设置KDJ参数
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
	 * 获取KDJ中K的数据
	 * 
	 * @return K
	 */
	public double getKData(int index) {
		if(KList==null)
			return 0;
		if(index<0 || index>=KList.size())
			return 0;
		
		return KList.get(index);
	}
	
	/**
	 * 获取KDJ中D的数据
	 * 
	 * @return D
	 */
	public double getDData(int index) {
		if(DList==null)
			return 0;
		if(index<0 || index>=DList.size())
			return 0;
		
		return DList.get(index);
	}
	
	/**
	 * 获取KDJ中J的数据
	 * 
	 * @return J
	 */
	public double getJData(int index) {
		if(JList==null)
			return 0;
		if(index<0 || index>=JList.size())
			return 0;
		
		return JList.get(index);
	}
	
	/**
	 * 获取KDJ三个参数中整个列表中的最大值
	 * 
	 * @return 最大值
	 */
	public double getKDJTopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		
		return getKDJTopValue(0, klineData.size() - 1);
	}
	
	/**
	 * 获取KDJ三个参数中在指定时间段内的最大值
	 * 
	 * @param begin
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 最大值
	 */
	public double getKDJTopValue(int begin, int end) {
		if(KList==null || KList.size()<=0)
		{
			return 0;
		}
		double topK = QuoteUtils.getTopValue(KList, begin, end).floatValue();
		double topD = QuoteUtils.getTopValue(DList, begin, end).floatValue();
		double topJ = QuoteUtils.getTopValue(JList, begin, end).floatValue();
		
		double top = topK;
		if(topD>top)
			top = topD;
		if(topJ>top)
			top = topJ;
		
		return top;
	}

	/**
	 * 获取KDJ三个参数中整个列表中的最小值
	 * 
	 * @return 最小值
	 */
	public double getKDJBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		
		return getKDJBottomValue(0, klineData.size() - 1);
	}
	
	/**
	 * 获取KDJ三个参数中在指定时间段内的最小值
	 * 
	 * @param begin
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 最小值
	 */
	public double getKDJBottomValue(int begin, int end) {
		if(KList==null || KList.size()<=0)
		{
			return 0;
		}
		
		double bottomK = QuoteUtils.getBottomValue(KList, begin, end).floatValue();
		double bottomD = QuoteUtils.getBottomValue(DList, begin, end).floatValue();
		double bottomJ = QuoteUtils.getBottomValue(JList, begin, end).floatValue();
		
		double bottom = bottomK;
		if(bottomD<bottom)
			bottom = bottomD;
		if(bottomJ<bottom)
			bottom = bottomJ;
		
		return bottom;
	}
	
	/**
	 * 初始化KDJ指标
	 * 今（N）日RSV＝(今日收盘价－N日内最低价)/ (N日内最高价－N日内最低价)×100 ；
	 * 今（N）日K值＝2/3×昨日K值＋1/3×今（N）日RSV ； 今（N）日D值＝2/3×昨日D值＋1/3×今（N）日K值；
	 * 今（N）日J值＝3×今（N）日K值－2×今（N）日D值。 K、D初始值取50
	 */
	private void init()
	{
		if(KList==null)
		{
			KList = new ArrayList<Double>();
		}
		KList.clear();
		if(DList==null)
		{
			DList = new ArrayList<Double>();
		}
		DList.clear();
		if(JList==null)
		{
			JList = new ArrayList<Double>();
		}
		JList.clear();
		
		if(klineData==null)
			return;
		
		int dataSize = klineData.size();
		double rsv = 0;
		double k = 0;
		double d = 0;
		double j = 0;
		for (int i = 0; i < dataSize; i++) {
			double closePrice = klineData.get(i).getClosePrice();
			int start = 0;
			int len = PARAM_VALUE[0];
			if (i < 8) {
				len = i + 1;
			} else {
				start = i - 8;
			}

			double max = klineData.get(start).getHighPrice();
			double min = klineData.get(start).getLowPrice();
			double dif = 0;
			for (int index = start; index < start + len && index<dataSize; index++) {
				StockKlineItem data = klineData.get(index);
				if (max < data.getHighPrice() && data.getHighPrice()>0)
					max = data.getHighPrice();
				if (min > data.getLowPrice() && data.getLowPrice()>0)
					min = data.getLowPrice();
			}
			dif = max - min;
			if (dif == 0) {
				k = 0;
			} else {
				rsv = (closePrice - min) * 100 / dif;
			}

			if (i == 0) {
				k = rsv;
				d = rsv;
			} else {
				if(i - 1 < KList.size()){
					k = ((PARAM_VALUE[1] - 1) * KList.get(i - 1) + rsv)
					/ PARAM_VALUE[1];
					d = ((PARAM_VALUE[2] - 1) * DList.get(i - 1) + k)
					/ PARAM_VALUE[2];
				}
			}

			j = 3 * k - 2 * d;
			
			if(k>100)
			{
				k = 100;
			}
			else if (k < 0)
			{
				k = 0;
			}
			KList.add(k);
			
			if (d>100)
			{
				d = 100;
			}
			else if(d<0)
			{
				d = 0;
			}
			DList.add(d);
			
			if(j>100)
			{
				j = 100;
			}
			else if(j<0)
			{
				j = 0;
			}
			JList.add(j);
		}
	}

}
