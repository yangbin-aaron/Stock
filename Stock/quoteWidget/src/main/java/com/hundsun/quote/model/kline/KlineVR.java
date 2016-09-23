package com.hundsun.quote.model.kline;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;

/**
 *源程序名称:KlineVR.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:成交量变化率
 *作    者: 梁浩 
 *开发日期:2014-10-10 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineVR {
	public static int[] PARAM_VALUE = {24};
	private List<Float> VRList;
	private List<StockKlineItem> klineData = null;
	private final int VRPARAM = 24;
	private int _VRParam = VRPARAM;


	public KlineVR(List<StockKlineItem> klineData){
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
	 * 初始化vr指标
	 * 中轨线=N日的移动平均线
	 * 上轨线=中轨线＋两倍的标准差
	 * 下轨线=中轨线－两倍的标准差
	 */
	private void init() {
		if(klineData==null)
			return;
		int dataSize = klineData.size();

		if(VRList==null) {
			VRList = new ArrayList<Float>(dataSize);
		}
		VRList.clear();

		double vrAVS, vrBVS, vrCVS;

		VRList.add(0, (float)0);

		vrAVS = 0;
		vrBVS = 0;
		vrCVS = klineData.get(0).getVolume();        

		_VRParam = PARAM_VALUE[0];

		for (int i = 1; i < dataSize; i++){
			if (klineData.get(i).getClosePrice() > klineData.get(i-1).getClosePrice()) {
				vrAVS += klineData.get(i).getVolume();
			}else if (klineData.get(i).getClosePrice() < klineData.get(i-1).getClosePrice()) {
				vrBVS += klineData.get(i).getVolume();
			}else {
				vrCVS += klineData.get(i).getVolume();
			}

			if (i == _VRParam) {
				vrCVS += klineData.get(i-_VRParam).getVolume();
			}else if (i > _VRParam){
				if (klineData.get(i-_VRParam).getClosePrice() > klineData.get(i-_VRParam-1).getClosePrice()) {
					vrAVS -= klineData.get(i-_VRParam).getVolume();
				}else if (klineData.get(i-_VRParam).getClosePrice() < klineData.get(i-_VRParam-1).getClosePrice()) {
					vrBVS -= klineData.get(i-_VRParam).getVolume();
				}else {
					vrCVS -= klineData.get(i-_VRParam).getVolume();
				}
			}
			if (QuoteUtils.isFloatZero((float)(vrBVS + vrCVS / 2))){
				VRList.add(i,(float)100.0);
			} else{
				VRList.add(i,(float)((vrAVS + vrCVS / 2) / (vrBVS + vrCVS / 2) * 100.0));
			}
		}
	}

	/**
	 * 参数设置
	 * @param paramValue
	 */
	public static void setParam(int[] paramValue) {
		if(paramValue==null || paramValue.length<1)
			return;
		if(Arrays.equals(paramValue, PARAM_VALUE)){
			return;
		}else{
			PARAM_VALUE = paramValue;
		}
	}

	/**
	 * 获取VR的数据
	 * 
	 * @return K
	 */
	public float getVRData(int index) {
		if(VRList==null)
			return 0;
		if(index<0 || index>=VRList.size())
			return 0;

		return VRList.get(index);
	}

	/**
	 * 获取VR整个列表中的最大值
	 * 
	 * @return 最大值
	 */
	public float getVRTopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getVRTopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取VR在指定时间段内的最大值
	 * 
	 * @param begin 起始索引
	 * @param end   结束索引
	 * @return 最大值
	 */
	public float getVRTopValue(int begin, int end) {
		if(VRList==null || VRList.size()<=0) {
			return 0;
		}
		float top = QuoteUtils.getTopValue(VRList, begin, end).floatValue();
		return top;
	}

	/**
	 * 获取VR整个列表中的最小值
	 * 
	 * @return 最小值
	 */
	public float getVRBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getVRBottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASIM在指定时间段内的最小值
	 * 
	 * @param begin
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 最小值
	 */
	public float getVRBottomValue(int begin, int end) {
		if(VRList==null || VRList.size()<=0){
			return 0;
		}
		float bottom = QuoteUtils.getBottomValue(VRList, begin, end).floatValue();
		return bottom;
	}


}