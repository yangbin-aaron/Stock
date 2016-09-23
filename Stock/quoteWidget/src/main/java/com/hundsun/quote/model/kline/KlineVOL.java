package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;

/**
 *源程序名称:KlineVOL.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:成交量指标。
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineVOL {

	public static int[] PARAM_VALUE = {5, 10,20};//

	private List<List<Float>> volDataList;
	private List<StockKlineItem> klineData = null;

	public KlineVOL(List<StockKlineItem> klineData){
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
	 * 获取指定周期的VOL-HS指标数据
	 * @param type
	 * @return
	 */
	public List<Float> getVOLList(int type) {
		if(volDataList==null)
			return null;
		for(int i=0;i<PARAM_VALUE.length;i++){
			if(type==PARAM_VALUE[i]){
				return volDataList.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取VOL-HS的数据
	 * @param type 类型
	 * @return VOL-HS
	 */
	public float getVOL(int type, int index) {
		List<Float> volData = getVOLList(type);
		if(volData==null){
			return 0;
		}
		if(index<0 || index>=volData.size()){
			return 0;
		}
		return volData.get(index);
	}

	/**
	 * 或者指定时间段内的VOL-HS最大值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	public float getVOLTopValue(int type, int begin, int end) {
		if(volDataList==null || volDataList.size()<=0){
			return 100;
		}
		List<Float> tempVOLList = getVOLList(type);
		return QuoteUtils.getTopValue(tempVOLList, begin, end).floatValue();
	}

	/**
	 * 或者指定时间段内的VOL-HS最小值
	 * @param type
	 * @param begin
	 * @param end
	 * @return
	 */
	public float getVOLBottomValue(int type, int begin, int end) {
		if(volDataList==null || volDataList.size()<=0){
			return 0;
		}
		List<Float> tempVOLList = getVOLList(type);
		return QuoteUtils.getBottomValue(tempVOLList, begin, end).floatValue();
	}

	/**
	 * 初始化VOL-HS指标
	 */
	private void init(){
		if(klineData==null)
			return;
		int dataSize = klineData.size();
		if(volDataList==null){
			volDataList = new ArrayList<List<Float>>();
		}else{
			volDataList.clear();
		}
		int paramTypeSize = PARAM_VALUE.length;
		//volDataList = new ArrayList<List<Float>>();
		for(int i=0;i<paramTypeSize;i++){
			volDataList.add( new ArrayList<Float>(dataSize));
		}
		long[] total = new long[dataSize];
		for (int i = 0; i < dataSize; i++){
			total[i] = klineData.get(i).getVolume();
			for(int j=0;j<paramTypeSize;j++){
				int parMType = PARAM_VALUE[j];
				List<Float> tempList = volDataList.get(j);
				tempList.add( i,calAvg(total, i, parMType) );
			}
		}
	}

	/**
	 * @param total 每日价格变化值
	 * @param index 位置
	 * @param dateNum 天数
	 * @return
	 */
	private float calAvg(long[] total, int index, int dateNum){
		if(total == null)
			return 0;
		if(index<0 || index>=total.length)
			return 0;
		int pos = 0;
		int len = dateNum;
		if(index < dateNum){
			pos = 0;
			len = index + 1;
		}else{
			pos = index + 1 - dateNum;
			len = dateNum;
		}
		long volhsMaSum = 0;
		for (int i = pos; i < index+1; i++){
			volhsMaSum += total[i];
		}
		return volhsMaSum/len;
	}
}
