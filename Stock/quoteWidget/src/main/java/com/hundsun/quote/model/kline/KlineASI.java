package com.hundsun.quote.model.kline;

import java.util.ArrayList;
import java.util.List;

import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.tools.QuoteUtils;
/**
 *源程序名称:KlineASI.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:振动升降指标(ASI)，由威尔斯·王尔德（Welles Wilder）所创。ASI指标以开盘、最高、最低、收盘价与前一交易日的各种价格相比较作为计算因子，研判市场的方向性。
 *作    者: 梁浩 
 *开发日期:2014-10-11 下午17:24:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class KlineASI {

	private List<Double> ASIList;
	private List<Double> ASIMAList;

	private List<StockKlineItem> klineData = null;
	private final int ASIMAPARAM = 6;

	public KlineASI(List<StockKlineItem> klineData){
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
	 * 获取ASI的数据
	 * @return K
	 */
	public double getASIData(int index) {
		if(ASIList==null)
			return 0;
		if(index<0 || index>=ASIList.size())
			return 0;
		return ASIList.get(index);
	}

	/**
	 * 获取ASIMA的数据
	 * @return D
	 */
	public double getASIMAData(int index) {
		if(ASIMAList==null)
			return 0;
		if(index<0 || index>=ASIMAList.size())
			return 0;
		return ASIMAList.get(index);
	}

	/**
	 * 获取ASI整个列表中的最大值
	 * @return 最大值
	 */
	public float getASITopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getASITopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASI在指定时间段内的最大值
	 * @param begin            起始索引
	 * @param end            结束索引
	 * @return 最大值
	 */
	public float getASITopValue(int begin, int end) {
		if(ASIList==null || ASIList.size()<=0) {
			return 0;
		}
		float top = QuoteUtils.getTopValue(ASIList, begin, end).floatValue();
		return top;
	}

	/**
	 * 获取ASI整个列表中的最小值
	 * @return 最小值
	 */
	public float getASIBottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getASIBottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASIM在指定时间段内的最小值
	 * @param begin            起始索引
	 * @param end            结束索引
	 * @return 最小值
	 */
	public float getASIBottomValue(int begin, int end) {
		if(ASIList==null || ASIList.size()<=0){
			return 0;
		}
		float bottom = QuoteUtils.getBottomValue(ASIList, begin, end).floatValue();
		return bottom;
	}

	/**
	 * 获取ASIMA整个列表中的最大值
	 * @return 最大值
	 */
	public float getASIMATopValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getASIMATopValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASIMA在指定时间段内的最大值
	 * @param begin            起始索引
	 * @param end             结束索引
	 * @return 最大值
	 */
	public float getASIMATopValue(int begin, int end) {
		if(ASIMAList==null || ASIMAList.size()<=0){
			return 0;
		}
		float top = QuoteUtils.getTopValue(ASIMAList, begin, end).floatValue();
		return top;
	}

	/**
	 * 获取ASIMA整个列表中的最小值
	 * @return 最小值
	 */
	public float getASIMABottomValue() {
		if(klineData==null || klineData.size()==0)
			return 0;
		return getASIMABottomValue(0, klineData.size() - 1);
	}

	/**
	 * 获取ASIMA在指定时间段内的最小值
	 * @param begin            起始索引
	 * @param end            结束索引
	 * @return 最小值
	 */
	public float getASIMABottomValue(int begin, int end) {
		if(ASIMAList==null || ASIMAList.size()<=0){
			return 0;
		}
		float bottom = QuoteUtils.getBottomValue(ASIMAList, begin, end).floatValue();
		return bottom;
	}

	/**
	 * 初始化ASI指标
	 * 1、  A=当天最高价—前一天收盘价
	 *    B=当天最低价—前一天收盘价
	 *    C=当天最高价—前一天最低价
	 *    D=前一天收盘价—前一天开盘价
	 *    A、B、C、D皆采用绝对值
	 * 2、  E=当天收盘价—前一天收盘价
	 *    F=当天收盘价—当天开盘价
	 *    G=前一天收盘价—前一天开盘价
	 *    E、F、G采用其±差值
	 * 3、 X=E+1/2F+G
	 * 4、 K=比较A、B二数值，选出其中最大值
	 * 5、 比较A、B、C三数值：
	 *    若A最大，则R=A+1/2B+1/4D
	 *    若B最大，则R=B+1/2A+1/4D
	 *    若C最大，则R=C+1/4D
	 * 6、 L=3
	 * 7、 SI=50*X/R*K/L
	 * 8、 ASI=累计每日之SI值
	 */
	private void init(){
		if(klineData==null)
			return;
		int dataSize = klineData.size();
		if(ASIList==null){
			ASIList = new ArrayList<Double>(dataSize);
		}
		ASIList.clear();
		if(ASIMAList==null){
			ASIMAList = new ArrayList<Double>(dataSize);
		}
		ASIMAList.clear();
		double asiA, asiB, asiC, asiD, asiE, asiF, asiG, asiX, asiR, asiK, asiSI;
		double asimaSum = 0;
		ASIList.add(0, (double)0);
		ASIMAList.add(0, (double)0);
		asimaSum = 0;
		for (int i = 1; i < dataSize; i++){
			asiA = Math.abs(klineData.get(i).getHighPrice() - klineData.get(i-1).getClosePrice());
			asiB = Math.abs(klineData.get(i).getLowPrice() - klineData.get(i-1).getClosePrice());
			asiC = Math.abs(klineData.get(i).getHighPrice() - klineData.get(i-1).getLowPrice());
			asiD = Math.abs(klineData.get(i).getClosePrice() - klineData.get(i-1).getOpenPrice());
			if (asiA > asiB && asiA > asiC) {
				asiR = asiA + asiB / 2 + asiD / 4;
			}else if (asiB > asiA && asiB > asiC){
				asiR = asiB + asiA / 2 + asiD / 4;
			}else{
				asiR = asiC + asiD / 4;
			}
			asiE = klineData.get(i).getClosePrice() - klineData.get(i-1).getClosePrice();
			asiF = klineData.get(i).getClosePrice() - klineData.get(i).getOpenPrice();
			asiG = klineData.get(i-1).getClosePrice() - klineData.get(i-1).getOpenPrice();
			asiX = asiE + asiF / 2 + asiG;
			asiK = asiA > asiB ? asiA : asiB;
			if (asiR == 0) {
				asiSI = (float)0.0160 * asiK;
			}else{
				asiSI = (float)0.0160 * asiX / asiR * asiK ;
			}
			ASIList.add(i, ASIList.get(i-1) + asiSI); 

			if (i < ASIMAPARAM) {
				asimaSum += ASIList.get(i);
				ASIMAList.add(i,(double)asimaSum / i);
			}else{
				asimaSum += ASIList.get(i) -  ASIList.get(i-ASIMAPARAM);
				ASIMAList.add(i,(double)asimaSum / ASIMAPARAM);
			}
		}
	}

}
