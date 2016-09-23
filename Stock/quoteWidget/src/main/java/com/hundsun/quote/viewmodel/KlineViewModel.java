package com.hundsun.quote.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockKlineItem;
import com.hundsun.quote.model.kline.KlineASI;
import com.hundsun.quote.model.kline.KlineBIAS;
import com.hundsun.quote.model.kline.KlineBOLL;
import com.hundsun.quote.model.kline.KlineCCI;
import com.hundsun.quote.model.kline.KlineDMA;
import com.hundsun.quote.model.kline.KlineKDJ;
import com.hundsun.quote.model.kline.KlineMACD;
import com.hundsun.quote.model.kline.KlineOBV;
import com.hundsun.quote.model.kline.KlinePSY;
import com.hundsun.quote.model.kline.KlineRSI;
import com.hundsun.quote.model.kline.KlineVOL;
import com.hundsun.quote.model.kline.KlineVR;
import com.hundsun.quote.model.kline.KlineWR;
import com.hundsun.quote.tools.FormatUtils;

public class KlineViewModel extends ViewModel{
	public KlineViewModel(Stock stock) {
		super(stock);
	}

	public int mCount = 60;
	public int mPeriod = 6;
	
	private ArrayList<StockKlineItem> mStockDatas;
	
	public ArrayList<StockKlineItem> getStockDatas() {
		return mStockDatas;
	}
	public void setStockDatas(ArrayList<StockKlineItem> stockDatas) {
		this.mStockDatas = stockDatas;
		if ( stockDatas!= null ) {
			init();
		}
	}
	
	public String getDateTimeStr( int index ){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder .append(getDate(index));
		int time = getTime(index);
		if ( 0 != time) {
			stringBuilder.append("  ");
			stringBuilder.append(time);
			stringBuilder.insert(stringBuilder.length()- 2, ":");
		}
		
		return stringBuilder.toString();
	}
	
	public void init(){
		mAsi = null;
		mBias = null;
		mBoll = null;
		mCci = null;
		mDma = null;
		mKdj = null;
		mMacd = null;
		mObv = null;
		mPsy = null;
		mRsi = null;
		mVol = null;
		mVr = null;
		mWr = null;
		initMA();
	}

	private int mCurrentIdx;
	private KlineASI  mAsi;
	private KlineBIAS mBias;
	private KlineBOLL mBoll;
	private KlineCCI  mCci;
	private KlineDMA  mDma;
	private KlineKDJ  mKdj;
	private KlineMACD mMacd;
	private KlineOBV  mObv;
	private KlinePSY  mPsy;
	private KlineRSI  mRsi;
	private KlineVOL  mVol;
	private KlineVR   mVr;
	private KlineWR   mWr;
	/* MA列表 */
	public static int[] MA_PARAM = {5, 10, 30};
	private List<List<Double>> maDataList;

	/**
	 * 放大K线
	 */
	public void zoomIn(){
		
	}
	/**
	 * 缩小K线 ， 如果当前数据在缩小后不足，会触发数据请求
	 * 补足缩小后的K线显示
	 */
	public void zoomOut(){}
	
	/**
	 * 初始化MA5, MA10, MA20, MA60等值
	 */
	private void initMA() {
		int maSize = MA_PARAM.length;
		maDataList = new ArrayList<List<Double>>();
		for(int i=0;i<maSize;i++)
		{
			maDataList.add( new ArrayList<Double>() );
		}
		for (int i = 0; i < getDataSize(); i++) {
			for(int j=0;j<maSize;j++)
			{
				int maType = MA_PARAM[j];
				List<Double> tempMA = maDataList.get(j);
				tempMA.add(getMaPrice(maType, i));
			}
		}
	}
	
	/**
	 * 获取几日收盘价均线值
	 * 
	 * @param num 几日
	 * @param index 当前位置
	 * @return 几日收盘价均线
	 */
	private double getMaPrice(int num, int index) {
		if(getDataSize()<=0)
			return 0;
		if (index < num - 1) {
			return 0;
		}
		double ret = 0;
		for (int i = 0; i < num; i++) {
			ret += mStockDatas.get(index - i).getClosePrice();
		}
		return  ret / num;
	}

	public int getDataSize(){
		if (null != mStockDatas) {
			return mStockDatas.size();
		}
		return 0;
	}
	
	public void setIndex(int idx ){
		mCurrentIdx = idx;
	}

	public double getClosePrice() {
		return getClosePrice(mCurrentIdx);
	}
	public double getClosePrice( int idx ) {
		if (null != mStockDatas && idx >= 0 && idx< mStockDatas.size()) {
			return mStockDatas.get(idx).closePrice;
		}
		return 0;
	}

	public double getOpenPrice() {
		if (null != mStockDatas && mCurrentIdx >= 0 && mCurrentIdx< mStockDatas.size()) {
			return mStockDatas.get(mCurrentIdx).openPrice;
		}
		return 0;
	}

	public double getMaxPrice() {
		if (null != mStockDatas && mCurrentIdx >= 0 && mCurrentIdx< mStockDatas.size()) {
			return mStockDatas.get(mCurrentIdx).highPrice;
		}
		return 0;
	}

	public double getMinPrice() {
		if (null != mStockDatas && mCurrentIdx >= 0 && mCurrentIdx< mStockDatas.size()) {
			double minPrice = Double.MAX_VALUE;
			int size = mStockDatas.size();
			for (int i = 0; i < size; i++) {
				minPrice = Math.min( minPrice, mStockDatas.get(i).lowPrice);
			}
			return mStockDatas.get(mCurrentIdx).lowPrice;
		}
		return 0;
	}
	
	/**
	 * 获取屏幕显示的起止时间内的最高成交量
	 * 
	 * @param begin 起始索引
	 * @param end   结束索引
	 * @return 最高成交量
	 */
	public double getTopDealAmountDuringPointedDays(int begin, int end) {
		double topValue = 0;
		for (int i = begin; i <= end; i++) {
			if(i<0 || i>=getDataSize())
			{
				continue;//非法值保护
			}
			StockKlineItem scde = mStockDatas.get(i);
			if (topValue < scde.getVolume()) {
				topValue = scde.getVolume();
			}
		}
		return topValue;
	}
	
	/**
	 * 获取屏幕显示的起止时间内的最低成交量
	 * 
	 * @param begin   起始索引
	 * @param end     结束索引
	 * @return 最低成交量
	 */
	public double getBottomDealAmountDuringPointedDays(int begin, int end) {
		if(getDataSize()<=0){
			return 0;
		}
		if(begin<0){
			begin = 0;
		}
		if(begin>=getDataSize()) {
			begin = getDataSize()-1;
		}
		
		double bottomValue = mStockDatas.get(begin).getVolume();
		for (int i = begin; i<=end && i<getDataSize(); i++) {
			StockKlineItem scde = mStockDatas.get(i);
			if (bottomValue > scde.getVolume()) {
				bottomValue = scde.getVolume();
			}
		}
		return bottomValue;
	}

	public long getTotalDealAmount() {
		if (null != mStockDatas && mCurrentIdx >= 0 && mCurrentIdx< mStockDatas.size()) {
			return mStockDatas.get(mCurrentIdx).volume;
		}
		return 0;
	}

	public int getDate(int position) {
		if (null != mStockDatas && position >= 0 && position< mStockDatas.size()) {
			return mStockDatas.get(position).date;
		}
		return 0;
	}
	
	public int getTime(int position) {
		if (null != mStockDatas && position >= 0 && position< mStockDatas.size()) {
			return mStockDatas.get(position).time;
		}
		return 0;
	}
	
	/**
	 * 获取MA数据
	 * @param type
	 * @param pos
	 * @return
	 */
	public double getMA(int type, int pos)
	{
		if(MA_PARAM==null || MA_PARAM.length<=0 || maDataList==null || maDataList.size()<=0 )
			return 0;
		if(pos<0 || maDataList.get(0)==null || pos>=maDataList.get(0).size())
			return 0;
		
		for(int i=0;i<MA_PARAM.length;i++)
		{
			if(type==MA_PARAM[i] && maDataList.get(i)!=null)
			{
				if(pos>=0 && pos<maDataList.get(i).size())
				{
					return maDataList.get(i).get(pos) ;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 获取MA数据
	 * @param type
	 * @return
	 */
	private List<Double> getMAList(int type) {
		if(MA_PARAM==null || maDataList==null)
			return null;
		
		for(int i=0;i<MA_PARAM.length;i++)
		{
			if(type==MA_PARAM[i])
			{
				if(i<maDataList.size())
				{
					return maDataList.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取指定范围的MA最小值
	 * @param maList
	 * @param begin
	 * @param end
	 * @return
	 */
	private double getMABottomValue(List<Double> maList, int begin, int end) {
		if(null == maList || begin < 0 || end < 0 || end > maList.size()-1){
			return 0;
		}
		double ret = 0;
		
		if(begin>end)
		{
			begin = end;
		}
		for (int i = begin;i <= end; i++) {
			if (i == begin) {
				ret = maList.get(i);
			} else {
				double rsi = maList.get(i);
				if (rsi < ret) {
					ret = rsi;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 获取Ma在指定时间段内最大值
	 * @param maList
	 * @param begin
	 * @param end
	 * @return
	 */
	private double getMATopValue(List<Double> maList, int begin, int end) {
		if(null == maList || begin < 0 || end < 0 || end > maList.size()-1){
			return 0;
		}
		double ret = 0;
		for (int i = begin; i <= end; i++) {
			double rsi = maList.get(i);
			if (rsi > ret) {
				ret = rsi;
			}
		}
		return ret ;
	}

	public double getMATopValue(int MA, int startIndex, int tempEndIndex) {
		List<Double> maList = getMAList(MA);
		return getMATopValue(maList, startIndex, tempEndIndex);
	}

	public double getMABottomValue(int MA, int startIndex, int tempEndIndex) {
		List<Double> maList = getMAList(MA);
		
		return getMABottomValue(maList, startIndex, tempEndIndex);
	}

	/**
	 * 获取屏幕显示的起止时间内的最高价
	 * 
	 * @param begin
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 最高价
	 */
	public double getTopPriceDuringPointedDays(int begin, int end) {
		double topValue = 0;
		if(mStockDatas==null || mStockDatas.size()<=0)
		{
			return 0;
		}
		if(begin<0 || begin>=mStockDatas.size())
		{
			begin = 0;
		}
		if(end<0 || end>=mStockDatas.size())
		{
			end = mStockDatas.size()-1;
		}
		for (int i = begin; i <= end; i++) {
			if(i<0 || i>=getDataSize())
			{
				continue;//非法值保护
			}
			StockKlineItem scde = mStockDatas.get(i);
			if (topValue < scde.getClosePrice()) {
				topValue = scde.getClosePrice();
			}
			if (topValue < scde.getHighPrice()) {
				topValue = scde.getHighPrice();
			}
			if (topValue < scde.getOpenPrice()) {
				topValue = scde.getOpenPrice();
			}
			if ( topValue < getMaxMaValue_int(i)) {
				topValue = getMaxMaValue_int(i);
			}
		}
		return topValue ;
	}
	
	/**
	 * 获取指导位置MA的最大值
	 * @param index
	 * @return
	 */
	public int getMaxMaValue_int(int pos)
	{
		if(MA_PARAM==null || MA_PARAM.length<=0 || maDataList==null || maDataList.size()<=0 )
			return 0;
		if(pos<0 || maDataList.get(0)==null || pos>=maDataList.get(0).size())
			return 0;
		
		double maxValue = 0;
		for(int i=0;i<MA_PARAM.length;i++)
		{
			if(maDataList.get(i)!= null && pos < maDataList.get(i).size()){
				double tempValue = maDataList.get(i).get(pos);
				if(tempValue>maxValue)
				{
					maxValue = tempValue;
				}
			}
		}
		return (int) maxValue;
	}

	public double getBottomPriceDuringPointedDays(int begin, int end) {

		if(getDataSize()<=0)
			return 0;
		if(mStockDatas==null || mStockDatas.size()<=0)
		{
			return 0;
		}
		if(begin<0 || begin>=mStockDatas.size())
		{
			begin = 0;
		}
		if(end<0 || end>=mStockDatas.size())
		{
			end = mStockDatas.size()-1;
		}
		
		double bottomValue = mStockDatas.get(begin).getLowPrice();
		for (int i = begin; i <= end; i++) {
			if(i<0 || i>=getDataSize())
			{
				continue;//非法值保护
			}
			StockKlineItem scde = mStockDatas.get(i);
			if (bottomValue > scde.getClosePrice()) {
				bottomValue = scde.getClosePrice();
			}
			if (bottomValue > scde.getLowPrice() ) {
				bottomValue = scde.getLowPrice();
			}
			if (bottomValue > scde.getHighPrice()) {
				bottomValue = scde.getHighPrice();
			}
			if (bottomValue > scde.getOpenPrice()) {
				bottomValue = scde.getOpenPrice();
			}
			int min = getMinMaValue_int(i);
			if(min>0 && bottomValue >min ) {
				bottomValue = min;
			}
		}
		return bottomValue ;
	
	}
	
	/**
	 * 获取指导位置MA的最小值
	 * @param pos
	 * @return 0 可能是异常值
	 */
	public int getMinMaValue_int(int pos)
	{
		if(MA_PARAM==null || MA_PARAM.length<=0 || maDataList==null || maDataList.size()<=0 )
			return 0;
		if(pos<0 || maDataList.get(0)==null || pos>=maDataList.get(0).size())
			return 0;
		
		double minValue = 0;
		for(int i=0;i<MA_PARAM.length;i++)
		{
			if(maDataList.get(i)!= null && pos < maDataList.get(i).size()){
				double tempValue = maDataList.get(i).get(pos) ;
				if(tempValue!=0 && (tempValue<minValue || minValue==0) )
				{
					minValue = tempValue;
				}
			}
		}
		return (int) minValue;
	}

	/**
	 * 获取涨跌的幅度
	 * 
	 * @return 涨跌的幅度
	 */
	public double getUpDownPercent() {
		if( mCurrentIdx<0 || mCurrentIdx>=getDataSize())
			return 0;
		
		double updownPer = 0;
		if (mCurrentIdx >0){
			updownPer = (double) mStockDatas.get(mCurrentIdx).getClosePrice() * 10000 / mStockDatas.get(mCurrentIdx - 1).getClosePrice() - 10000;
		}
		return updownPer / 10000;
	}

	/**
	 * 获取涨跌的幅度
	 * 
	 * @return 涨跌的幅度 (字符串，已精确小数点)
	 */
	public String getUpDownPercentStr() {
	    if (mCurrentIdx  == 0)
	    {
	        return "--";
	    }
		return FormatUtils.formatPercent(getUpDownPercent());
	}

	public long getTotalDealAmountOfMoney() {
		if(mCurrentIdx<0 || mCurrentIdx>=getDataSize())
			return 0;
		
		long money = mStockDatas.get(mCurrentIdx).getMoney();
		
		return money;
	}

	/**
	 * 获取ASIK线 指标 
	 * @return
	 */
	public KlineASI getASI() {
		if (null == mAsi) {
			mAsi = new KlineASI(mStockDatas);
		}
		return mAsi;
	}
	/**
	 * 获取BIASK线 指标 
	 * @return
	 */
	public KlineBIAS getBIAS() {
		if (null == mBias) {
			mBias = new KlineBIAS(mStockDatas);
		}
		return mBias;
	}
	
	/**
	 * 获取   BOLL K线 指标 
	 * @return
	 */
	public KlineBOLL getBOLL() {
		if (null == mBoll) {
			mBoll = new KlineBOLL(mStockDatas);
		}
		return mBoll;
	}
	
	/**
	 * 获取   CCI K线 指标 
	 * @return
	 */
	public KlineCCI getCCI() {
		if (null == mCci) {
			mCci = new KlineCCI(mStockDatas);
		}
		return mCci;
	}
	
	/**
	 * 获取   CCI K线 指标 
	 * @return
	 */
	public KlineDMA getDMA() {
		if (null == mDma) {
			mDma = new KlineDMA(mStockDatas);
		}
		return mDma;
	}
	
	/**
	 * 获取   KDJ K线 指标 
	 * @return
	 */
	public KlineKDJ getKDJ() {
		if (null == mKdj) {
			mKdj = new KlineKDJ(mStockDatas);
		}
		return mKdj;
	}
	
	/**
	 * 获取   KDJ K线 指标 
	 * @return
	 */
	public KlineMACD getMACD() {
		if (null == mMacd) {
			mMacd = new KlineMACD(mStockDatas);
		}
		return mMacd;
	}
	/**
	 * 获取   OBV K线 指标 
	 * @return
	 */
	public KlineOBV getOBV() {
		if (null == mObv) {
			mObv = new KlineOBV(mStockDatas);
		}
		return mObv;
	}
	/**
	 * 获取   PSY K线 指标 
	 * @return
	 */
	public KlinePSY getPSY() {
		if (null == mPsy) {
			mPsy = new KlinePSY(mStockDatas);
		}
		return mPsy;
	}
	/**
	 * 获取   RSI K线 指标 
	 * @return
	 */
	public KlineRSI getRSI() {
		if (null == mRsi) {
			mRsi = new KlineRSI(mStockDatas);
		}
		return mRsi;
	}
	/**
	 * 获取   VOL K线 指标 
	 * @return
	 */
	public KlineVOL getVOL() {
		if (null == mVol) {
			mVol = new KlineVOL(mStockDatas);
		}
		return mVol;
	}
	/**
	 * 获取   VR K线 指标 
	 * @return
	 */
	public KlineVR getVR() {
		if (null == mVr) {
			mVr = new KlineVR(mStockDatas);
		}
		return mVr;
	}
	/**
	 * 获取   WR K线 指标 
	 * @return
	 */
	public KlineWR getWR() {
		if (null == mWr) {
			mWr = new KlineWR(mStockDatas);
		}
		return mWr;
	}
}
