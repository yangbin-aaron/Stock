package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineDMA.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:平行线差（DMA）指标是利用两条不同期间的平均线，来判断当前买卖能量的大小和未来价格趋势
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineDMA {

	public static int[] PARAM_VALUE = { 10, 50, 10 };
	private List<Float> DDDList;
	private List<Float> AMAList;
	private List<StockKlineItem> klineData = null;

	private int _DMAshortMaParam = 10;
	private int _DMAlongMaParam = 50;
	private int _DMAdddMaParam = 10;

	public KlineDMA(List<StockKlineItem> klineData){
		this.klineData = klineData;
		init();
	}

	/**
	 * 设置K线数据
	 * @param klineData
	 */
	public void setKlineData(List<StockKlineItem> klineData) {
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
	 * 获取DDD的数据
	 * @return K
	 */
	public float getDDDData(int index) {
		if(DDDList==null)
			return 0;
		if(index<0 || index>=DDDList.size())
			return 0;
		return DDDList.get(index);
	}

	/**
	 * 获取AMA的数据
	 * @return D
	 */
	public float getAMAData(int index) {
		if(AMAList==null)
			return 0;
		if(index<0 || index>=AMAList.size())
			return 0;
		return AMAList.get(index);
	}

	/**
	 * 获取AMA整个列表中的最大值
	 * @return 最大值
	 */
	public float getAMATopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getAMATopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ama在指定时间段内的最大值
	 * @param begin  起始索引
	 * @param end 结束索引
	 * @return 最大值
	 */
	public float getAMATopValue(int begin, int end) {
		if(AMAList==null || AMAList.size()<=0){
			return 0;
		}
		float top = QuoteUtils.getTopValue(AMAList, begin, end).floatValue();
		return top;
	}

	/**
	 * 获取AMA整个列表中的最小值
	 * @return 最小值
	 */
	public float getAMABottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getAMABottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取AMA在指定时间段内的最小值
	 * @param begin   起始索引
	 * @param end     结束索引
	 * @return 最小值
	 */
	public float getAMABottomValue(int begin, int end) {
		if(AMAList==null || AMAList.size()<=0){
			return 0;
		}
		float bottom = QuoteUtils.getBottomValue(AMAList, begin, end).floatValue();
		return bottom;
	}
	/**
	 * 获取DDD整个列表中的最大值
	 * @return 最大值
	 */
	public float getDDDTopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getDDDTopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取DDD在指定时间段内的最大值         起始索引
	 * @param end            结束索引
	 * @return 最大值
	 */
	public float getDDDTopValue(int begin, int end) {
		if(DDDList==null || DDDList.size()<=0){
			return 0;
		}
		float top = QuoteUtils.getTopValue(DDDList, begin, end).floatValue();
		return top;
	}

	/**
	 * 获取DDD整个列表中的最小值
	 * @return 最小值
	 */
	public float getDDDBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getDDDBottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取DDD在指定时间段内的最小值
	 * @param begin          起始索引
	 * @param end            结束索引
	 * @return 最小值
	 */
	public float getDDDBottomValue(int begin, int end) {
		if(DDDList==null || DDDList.size()<=0){
			return 0;
		}
		float bottom = QuoteUtils.getBottomValue(DDDList, begin, end).floatValue();
		return bottom;
	}
	/**
	 * 初始化DDD指标
   	 * DDD=短期平均值—长期平均值 
   	 * AMA=短期平均值 
   	 * 以求10日、50日为基准周期的DDD指标为例，其计算过程具体如下： 
   	 * DDD（10）=10日平均值—50日平均值 
   	 * AMA（10）=10日平均值 
	 */
	private void init(){
		if(klineData==null)
			return;
		int dataSize = klineData.size();
		if(DDDList==null){
			DDDList = new ArrayList<Float>(dataSize);
		}
		DDDList.clear();
		if(AMAList==null){
			AMAList = new ArrayList<Float>(dataSize);
		}
		AMAList.clear();

		_DMAshortMaParam = PARAM_VALUE[0];
		_DMAlongMaParam = PARAM_VALUE[1];
		_DMAdddMaParam = PARAM_VALUE[2];


		double dmaShortmaSum,dmaLongmaSum,dmaDDDmaSum,dmaShortma,dmaLongma;//DMA相关
		//dma基本值初始化
		dmaShortmaSum = dmaLongmaSum  =  klineData.get(0).getClosePrice()/1000.0;
		dmaDDDmaSum = 0;

		DDDList.add(0, (float)0);
		AMAList.add(0, (float)0);
		for (int i = 1; i < dataSize; i++){
			//DMA
			if (i < _DMAshortMaParam) {
				dmaShortmaSum += klineData.get(i).getClosePrice()/1000.0;
				dmaShortma = dmaShortmaSum / (i+1);
			}else{
				dmaShortmaSum += klineData.get(i).getClosePrice()/1000.0 - (klineData.get(i-_DMAshortMaParam).getClosePrice()/1000.0);
				dmaShortma = dmaShortmaSum / _DMAshortMaParam;
			}
			if (i < _DMAlongMaParam) {
				dmaLongmaSum += klineData.get(i).getClosePrice()/1000.0;
				dmaLongma = dmaLongmaSum / (i+1);
			}else{
				dmaLongmaSum += klineData.get(i).getClosePrice()/1000.0 - (klineData.get(i-_DMAlongMaParam).getClosePrice()/1000.0);
				dmaLongma = dmaLongmaSum / _DMAlongMaParam;
			}
			DDDList.add(i,(float)(dmaShortma - dmaLongma));
			if (i < _DMAdddMaParam) {
				dmaDDDmaSum += DDDList.get(i);
				AMAList.add(i,(float)(dmaDDDmaSum / (i+1)));
			}else{
				dmaDDDmaSum += DDDList.get(i) - DDDList.get(i-_DMAdddMaParam);
				AMAList.add(i,(float)(dmaDDDmaSum / _DMAshortMaParam));
			}
		}
	}

}
