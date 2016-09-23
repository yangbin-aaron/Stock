package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineMACD.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:均线指标
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineMACD {

	public static int[] PARAM_VALUE = { 12, 26, 9 };
	
	private List<StockKlineItem> klineData = null;
	
	private List<Double> DIFFList; //DIFF=EMA（SHORT）－EMA（LONG）,既短日指数平滑移动平均线-长日指数平滑移动平均线
	private List<Double> DEAList; //M日指数平滑移动平均线
	private List<Double> MACDList; //DIFF减DEA
	
	public KlineMACD(List<StockKlineItem> klineData)
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
	 * 设置MACD参数
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
	 * 获取MACD的数据
	 * 
	 * @return MACD
	 */
	public double getMACD(int index) {
		if(MACDList==null)
			return 0;
		if(index<0 || index>=MACDList.size())
			return 0;
		
		return MACDList.get(index);
	}
	
	/**
	 * 获取DIFF的数据
	 * 
	 * @return DIFF
	 */
	public double getDIFF(int index) {
		if(DIFFList==null)
			return 0;
		if(index<0 || index>=DIFFList.size())
			return 0;
		
		return DIFFList.get(index);
	}
	
	/**
	 * 获取DEA的数据
	 * 
	 * @return DEA
	 */
	public double getDea(int index) {
		if(DEAList==null)
			return 0;
		if(index<0 || index>=DEAList.size())
			return 0;
		
		return DEAList.get(index);
	}
	
	/**
	 * 获取MACD等三个参数中整个列表中的最小值
	 * 
	 * @return 最小值
	 */
	public double getMACDBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		
		return getMACDBottomValue(0, klineData.size() - 1);
	}
	
	/**
	 * 获取MACD等三个参数中在指定时间段内的最小值
	 * 
	 * @param begin
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 最小值
	 */
	public double getMACDBottomValue(int begin, int end) {
		if(MACDList==null || MACDList.size()<=0)
		{
			return 0;
		}
		
		double bottomMACD = QuoteUtils.getBottomValue(MACDList, begin, end).floatValue();
		double bottomDEA =  QuoteUtils.getBottomValue(DEAList, begin, end).floatValue();
		double bottomDIFF = QuoteUtils.getBottomValue(DIFFList, begin, end).floatValue();
		
		double bottom = bottomMACD;
		if(bottomDEA<bottom)
			bottom = bottomDEA;
		if(bottomDIFF<bottom)
			bottom = bottomDIFF;
		
		return bottom;
	}

	/**
	 * 获取MACD等三个参数中在指定时间段内的最大值
	 * 
	 * @param begin
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 最大值
	 */
	public double getMACDTopValue(int begin, int end) {
		if(MACDList==null || MACDList.size()<=0)
		{
			return 0;
		}
		double topMACD = QuoteUtils.getTopValue(MACDList, begin, end).floatValue();
		double topDEA =  QuoteUtils.getTopValue(DEAList, begin, end).floatValue();
		double topDIFF = QuoteUtils.getTopValue(DIFFList, begin, end).floatValue();
		
		double top = topMACD;
		if(topDEA>top)
			top = topDEA;
		if(topDIFF>top)
			top = topDIFF;
		
		return top;
	}
	
	/**
	 * DIFF线　收盘价短期、长期指数平滑移动平均线间的差 DEA线　 DIFF线的M日指数平滑移动平均线
	 * MACD线　DIFF线与DEA线的差，彩色柱状线 参数：SHORT(短期)、LONG(长期)、M 天数，一般为12、26、9
	 * 加权平均指数（ＤＩ）=（当日最高指数+当日收盘指数+2倍的当日最低指数） 十二日平滑系数（Ｌ１２）=2/（12+1）=0.1538
	 * 二十六日平滑系数（Ｌ２６）=2/（26+1）=0.0741
	 * 十二日指数平均值（１２日ＥＭＡ）=L12×当日收盘指数+11/（12+1）×昨日的12日EMA
	 * 二十六日指数平均值（２６日ＥＭＡ）=L26×当日收盘指数+25/（26+1）×昨日的26日EMA 差离率（ＤＩＦ）=12日EMA-26日EMA
	 * 九日DIF平均值（DEA） =最近9日的DIF之和/9 柱状值（ＢＡＲ）=DIF-DEA
	 * 
	 * 第二种算法： 1.EMA（SHORT）=收市价SHORT日指数移动平均；EMA（LONG）=收市价LONG日指数移动平均
	 * 2.DIF=EMA（SHORT）-EMA（LONG） 3.DEA=DIF的MID日指数移动平均 4.MACD=DIF-DEA
	 * 5.参数SHORT为12，参数LONG为26，参数MID为9
	 */
	private void init()
	{
		if(MACDList==null)
		{
			MACDList = new ArrayList<Double>();
		}
		MACDList.clear();
		if(DIFFList==null)
		{
			DIFFList = new ArrayList<Double>();
		}
		DIFFList.clear();
		if(DEAList==null)
		{
			DEAList = new ArrayList<Double>();
		}
		DEAList.clear();
		
		if(klineData==null)
			return;

		int dataSize =  klineData.size();
		double dif = 0;
		double macd = 0;
		double dea = 0;
		double emaShort = 0;
		double emaLong = 0;
		for (int i = 0; i < dataSize; i++) {
			StockKlineItem data = klineData.get(i);
			if (i == 0) {
				emaShort = emaLong = data.getClosePrice();
			} else {
				double nowClosePrice = data.getClosePrice();
				emaShort = (2 * nowClosePrice + (PARAM_VALUE[0] - 1)
						* emaShort)
						/ (PARAM_VALUE[0] + 1);
				emaLong = (2 * nowClosePrice + (PARAM_VALUE[1] - 1)
						* emaLong)
						/ (PARAM_VALUE[1] + 1);
				dea = (2 * (emaShort - emaLong) + (PARAM_VALUE[2] - 1)
						* DEAList.get(i - 1))
						/ (PARAM_VALUE[2] + 1);
			}

			dif = emaShort - emaLong;
			macd = 2 * (dif - dea);

			DEAList.add(dea);
			DIFFList.add(dif);
			MACDList.add(macd);
		}
	}

}
