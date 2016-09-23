package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineCCI.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:专门测量股价是否已超出常态分布范围。属于超买超卖类指标中较特殊的一种，波动于正无穷大和负无穷大之间。
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineCCI {

	public static int[] PARAM_VALUE = {14};
	private List<Double> CCIList;
	private List<Double> TPList = null;
	private List<Double> ABSList = null;  //绝对偏差
	private List<StockKlineItem> klineData = null;
	private final int CCIPARAM = 14;
	private int _CCIParam = CCIPARAM;

	public KlineCCI(List<StockKlineItem> klineData){
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
		if(paramValue==null || paramValue.length<1)
			return;
		if(Arrays.equals(paramValue, PARAM_VALUE)){
			return;
		}else{
			PARAM_VALUE = paramValue;
		}
	}
	/**
	 * 获取CCI的数据
	 * @return K
	 */
	public Double getCCIData(int index) {
		if(CCIList==null)
			return (double) 0;
		if(index<0 || index>=CCIList.size())
			return (double) 0;
		return CCIList.get(index);
	}


	/**
	 * 获取CCI整个列表中的最大值
	 * @return 最大值
	 */
	public double getCCITopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getCCITopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取CCI在指定时间段内的最大值
	 * @param begin         起始索引
	 * @param end           结束索引
	 * @return 最大值
	 */
	public double getCCITopValue(int begin, int end) {
		if(CCIList==null || CCIList.size()<=0){
			return 0;
		}
		double top = QuoteUtils.getTopValue(CCIList, begin, end).doubleValue();
		return top;
	}

	/**
	 * 获取CCI整个列表中的最小值
	 * @return 最小值
	 */
	public double getCCIBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getCCIBottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASIM在指定时间段内的最小值
	 * @param begin            起始索引
	 * @param end              结束索引
	 * @return 最小值
	 */
	public double getCCIBottomValue(int begin, int end) {
		if(CCIList==null || CCIList.size()<=0){
			return 0;
		}
		double bottom = QuoteUtils.getBottomValue(CCIList, begin, end).doubleValue();
		return bottom;
	}

	/**
	 * 初始化CCI指标
	 * CCI（N日）=（TP－MA）÷MD÷0.015 
	 * 其中，TP=（最高价+最低价+收盘价）÷3 
	 *　MA=最近N日收盘价的累计之和÷N 
	 * MD=最近N日（MA－收盘价）的累计之和÷N 
	 *  0.015为计算系数，N为计算周期 
	 */
	private void init(){
		if(klineData==null)
			return;
		int dataSize = klineData.size();
		if(CCIList==null){
			CCIList = new ArrayList<Double>(dataSize);
		}
		CCIList.clear();
		if(TPList==null){
			TPList = new ArrayList<Double>(dataSize);
		}
		TPList.clear();
		if(ABSList==null){
			ABSList = new ArrayList<Double>(dataSize);
		}
		ABSList.clear();

		double tp = 0;
		double TPSum = 0,TPma= 0;
		double ABSSum = 0,ABSma= 0;
		_CCIParam  = PARAM_VALUE[0];
		//TODO 3000根据实际情况具体对待  ？美股是否不同，有待论证
		for (int i = 0; i < dataSize; i++){
			tp = (double) ((klineData.get(i).getHighPrice() + klineData.get(i).getLowPrice() + klineData.get(i).getClosePrice())/3000);
			TPList.add(i,(double)tp);
			if (i < _CCIParam) {
				TPSum += TPList.get(i);
				TPma = TPSum / (i+1);
				ABSList.add(i,Math.abs(TPList.get(i)-TPma));
				ABSSum += ABSList.get(i);
				ABSma = (double)((0.015 * ABSSum) / (i+1));
				if(i == 0){
					CCIList.add(0,(double)0);
				}else{
					CCIList.add(i,(TPList.get(i)-TPma)/ABSma);
				}
			}else {
				TPSum += TPList.get(i) - TPList.get(i- _CCIParam);
				TPma = TPSum / _CCIParam;
				ABSList.add(i,Math.abs(TPList.get(i)-TPma));
				ABSSum += ABSList.get(i) - ABSList.get(i-_CCIParam);
				ABSma = (double)((0.015 * ABSSum) / _CCIParam);
				CCIList.add(i,(TPList.get(i)-TPma)/ABSma);
			}
		}

	}
}
