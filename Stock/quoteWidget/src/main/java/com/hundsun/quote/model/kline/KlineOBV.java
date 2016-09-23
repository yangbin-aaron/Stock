package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;

/**
 *源程序名称:KlineOBV.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:能量潮 【该指标通过统计成交量变动的趋势来推测股价趋势】
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineOBV {

	private List<Float> OBVList;
	private List<StockKlineItem> klineData = null;

	public KlineOBV(List<StockKlineItem> klineData){
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
	 * 获取OBV的数据
	 * @return K
	 */
	public float getOBVData(int index) {
		if(OBVList==null)
			return 0;
		if(index<0 || index>=OBVList.size())
			return 0;
		return OBVList.get(index);
	}


	/**
	 * 获取OBV整个列表中的最大值
	 * @return 最大值
	 */
	public float getOBVTopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getOBVTopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取OBV在指定时间段内的最大值
	 * @param begin 起始索引
	 * @param end   结束索引
	 * @return 最大值
	 */
	public float getOBVTopValue(int begin, int end) {
		if(OBVList==null || OBVList.size()<=0){
			return 0;
		}
		float top = QuoteUtils.getTopValue(OBVList, begin, end).floatValue();
		return top;
	}

	/**
	 * 获取OBV整个列表中的最小值
	 * @return 最小值
	 */
	public float getOBVBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getOBVBottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASIM在指定时间段内的最小值
	 * @param begin 起始索引
	 * @param end   结束索引
	 * @return 最小值
	 */
	public float getOBVBottomValue(int begin, int end) {
		if(OBVList==null || OBVList.size()<=0){
			return 0;
		}
		float bottom = QuoteUtils.getBottomValue(OBVList, begin, end).floatValue();
		return bottom;
	}

	/**
	 * 初始化OBV指标
	 * 中轨线=N日的移动平均线
	 * 上轨线=中轨线＋两倍的标准差
	 * 下轨线=中轨线－两倍的标准差
	 */
	private void init(){
		if(klineData==null)
			return;
		int dataSize = klineData.size();
		if(OBVList==null){
			OBVList = new ArrayList<Float>(dataSize);
		}
		OBVList.clear();
		OBVList.add(0, (float)klineData.get(0).getVolume());//基期成交量

		for (int i = 1; i < dataSize; i++){
			if(klineData.get(i).getClosePrice() > klineData.get(i-1).getClosePrice()){
				OBVList.add(i, OBVList.get(i-1) + klineData.get(i).getVolume());
			}else if(klineData.get(i).getClosePrice() < klineData.get(i-1).getClosePrice()){
				OBVList.add(i, OBVList.get(i-1) - klineData.get(i).getVolume());
			}else{
				OBVList.add(i, (float)OBVList.get(i-1));
			}
		}

	}
}
