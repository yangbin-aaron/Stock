package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineBOLL.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:布林指标
 *作    者: 梁浩 
 *开发日期:2014-10-10 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineBOLL {

	public static  int[] PARAM_VALUE = { 20, 2 };
	private List<Float> MBList;
	private List<Float> UPList;
	private List<Float> DOWNList;
	private List<StockKlineItem> klineData = null;
	private int _BOLLmaParam = 20;
	private int _BOLLwParam = 2;


	public KlineBOLL(List<StockKlineItem> klineData){
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
		 if(paramValue==null || paramValue.length<2)
			 return;
		 if(Arrays.equals(paramValue, PARAM_VALUE)){
			 return;
		 } else{
			 PARAM_VALUE = paramValue;
		 }
	 }

	 /**
	  * 获取MP的数据
	  * @return K
	  */
	 public float getMPData(int index) {
		 if(MBList==null)
			 return 0;
		 if(index<0 || index>=MBList.size())
			 return 0;
		 return MBList.get(index);
	 }

	 /**
	  * 获取UP的数据
	  * @return D
	  */
	 public float getUPData(int index) {
		 if(UPList==null)
			 return 0;
		 if(index<0 || index>=UPList.size())
			 return 0;
		 return UPList.get(index);
	 }
	 /**
	  * 获取DOWN的数据
	  * @return D
	  */
	 public float getDOWNData(int index) {
		 if(DOWNList==null)
			 return 0;
		 if(index<0 || index>=DOWNList.size())
			 return 0;
		 return DOWNList.get(index);
	 }
	 /**
	  * 获取MD整个列表中的最大值
	  * @return 最大值
	  */
	 public float getMBTopValue() {
		 if(klineData==null || klineData.size()==0)
			 return 0;
		 return getMBTopValue(0, klineData.size() - 1);
	 }

	 /**
	  * 获取Boll在指定时间段内的最大值
	  * @param begin       起始索引
	  * @param end         结束索引
	  * @return 最大值
	  */
	 public float getMBTopValue(int begin, int end) {
		 if(MBList==null || MBList.size()<=0){
			 return 0;
		 }
		 float top = QuoteUtils.getTopValue(MBList, begin, end).floatValue();
		 return top;
	 }

	 /**
	  * 获取MP整个列表中的最小值
	  * @return 最小值
	  */
	 public float getMBBottomValue() {
		 if(klineData==null || klineData.size()==0)
			 return 0;
		 return getMBBottomValue(0, klineData.size() - 1);
	 }

	 /**
	  * 获取MB在指定时间段内的最小值
	  * @param begin       起始索引
	  * @param end         结束索引
	  * @return 最大值
	  */
	 public float getMBBottomValue(int begin, int end) {
		 if(MBList==null || MBList.size()<=0){
			 return 0;
		 }
		 float bottom = QuoteUtils.getBottomValue(MBList, begin, end).floatValue();
		 return bottom;
	 }
	 /**
	  * 获取UP整个列表中的最大值
	  * @return 最大值
	  */
	 public float getUPTopValue() {
		 if(klineData==null || klineData.size()==0)
			 return 0;
		 return getUPTopValue(0, klineData.size() - 1);
	 }

	 /**
	  * 获取UP在指定时间段内的最大值
	  * @param begin         起始索引
	  * @param end           结束索引
	  * @return 最大值
	  */
	 public float getUPTopValue(int begin, int end) {
		 if(UPList==null || UPList.size()<=0) {
			 return 0;
		 }
		 float top = QuoteUtils.getTopValue(UPList, begin, end).floatValue();
		 return top;
	 }

	 /**
	  * 获取UP整个列表中的最小值
	  * @return 最小值
	  */
	 public float getUPBottomValue() {
		 if(klineData==null || klineData.size()==0)
			 return 0;
		 return getUPBottomValue(0, klineData.size() - 1);
	 }

	 /**
	  * 获取UP在指定时间段内的最小值
	  * @param begin          起始索引
	  * @param end            结束索引
	  * @return 最小值
	  */
	 public float getUPBottomValue(int begin, int end) {
		 if(UPList==null || UPList.size()<=0) {
			 return 0;
		 }
		 float bottom = QuoteUtils.getBottomValue(UPList, begin, end).floatValue();
		 return bottom;
	 }

	 /**
	  * 获取DOWN整个列表中的最大值
	  * @return 最大值
	  */
	 public float getDOWNTopValue() {
		 if(klineData==null || klineData.size()==0)
			 return 0;
		 return getUPTopValue(0, klineData.size() - 1);
	 }

	 /**
	  * 获取DN在指定时间段内的最大值
	  * @param begin         起始索引
	  * @param end           结束索引
	  * @return 最大值
	  */
	 public float getDOWNTopValue(int begin, int end) {
		 if(DOWNList==null || DOWNList.size()<=0){
			 return 0;
		 }
		 float top = QuoteUtils.getTopValue(DOWNList, begin, end).floatValue();
		 return top;
	 }

	 /**
	  * 获取down整个列表中的最小值
	  * @return 最小值
	  */
	 public float getDOWNBottomValue() {
		 if(klineData==null || klineData.size()==0)
			 return 0;
		 return getDOWNBottomValue(0, klineData.size() - 1);
	 }

	 /**
	  * 获取DOWN在指定时间段内的最小值
	  * @param begin           起始索引
	  * @param end             结束索引
	  * @return 最小值
	  */
	 public float getDOWNBottomValue(int begin, int end) {
		 if(DOWNList==null || DOWNList.size()<=0){
			 return 0;
		 }
		 float bottom = QuoteUtils.getBottomValue(DOWNList, begin, end).floatValue();
		 return bottom;
	 }
	 /**
	  * 初始化BOLL指标
	  * 中轨线=N日的移动平均线
	  * 上轨线=中轨线＋两倍的标准差
	  * 下轨线=中轨线－两倍的标准差
	  */
	 private void init() {
		 if(klineData==null)
			 return;
		 int dataSize = klineData.size();
		 if(MBList==null){
			 MBList = new ArrayList<Float>(dataSize);
		 }
		 MBList.clear();
		 if(UPList==null){
			 UPList = new ArrayList<Float>(dataSize);
		 }
		 UPList.clear();
		 if(DOWNList==null){
			 DOWNList = new ArrayList<Float>(dataSize);
		 }
		 DOWNList.clear();
		 double bollmaSum = klineData.get(0).getClosePrice();

		 MBList.add(0, (float)klineData.get(0).getClosePrice());
		 UPList.add(0, (float)klineData.get(0).getClosePrice());
		 DOWNList.add(0, (float)klineData.get(0).getClosePrice());

//		 bollmaSum = 0;

		 //BOLL，有意义实体线
		 double bollMS, bollMD;

		 _BOLLmaParam = PARAM_VALUE[0];
		 _BOLLwParam = PARAM_VALUE[1];
		 for (int i = 1; i < dataSize; i++) {
			 if (i < _BOLLmaParam) {
				 bollmaSum += klineData.get(i).getClosePrice();
				 MBList.add(i,(float)bollmaSum / (i+1));
				 bollMS = 0;
				 for (int j = 0; j < i; j++) {
					 bollMS += Math.pow((klineData.get(j).getClosePrice() - MBList.get(i)), 2);
				 }
				 bollMD = Math.sqrt(bollMS / i);
				 UPList.add(i,(float)(MBList.get(i) + (double)_BOLLwParam * bollMD));
				 DOWNList.add(i,(float)(MBList.get(i) - (double)_BOLLwParam * bollMD));
			 } else {
				 bollmaSum += klineData.get(i).getClosePrice() - klineData.get(i-_BOLLmaParam).getClosePrice();
				 MBList.add(i,(float)bollmaSum / _BOLLmaParam);
				 bollMS = 0;
				 for (int j = i - _BOLLmaParam; j < i; j++) {
					 bollMS += Math.pow(klineData.get(j).getClosePrice()-MBList.get(i), 2);
				 }
				 bollMD = Math.sqrt(bollMS / _BOLLmaParam);
				 UPList.add(i,(float)(MBList.get(i) + (double)_BOLLwParam * bollMD));
				 DOWNList.add(i,(float)(MBList.get(i) - (double)_BOLLwParam * bollMD));

			 }

		 }

	 }

}
