package com.hundsun.quote.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.kline.KlineASI;
import com.hundsun.quote.model.kline.KlineBIAS;
import com.hundsun.quote.model.kline.KlineBOLL;
import com.hundsun.quote.model.kline.KlineCCI;
import com.hundsun.quote.model.kline.KlineKDJ;
import com.hundsun.quote.model.kline.KlineMACD;
import com.hundsun.quote.model.kline.KlineOBV;
import com.hundsun.quote.model.kline.KlinePSY;
import com.hundsun.quote.model.kline.KlineRSI;
import com.hundsun.quote.model.kline.KlineVOL;
import com.hundsun.quote.model.kline.KlineVR;
import com.hundsun.quote.model.kline.KlineWR;
import com.hundsun.quote.tools.ColorUtils;
import com.hundsun.quote.tools.FormatUtils;
import com.hundsun.quote.viewmodel.KlineViewModel;
import com.hundsun.quotewidget.R;

public class QwKlineView extends View{
	
	public enum TechnicalIndicatorType{
		MACD,
		RSI,
		KDJ,
		PSY,
		WR,
		VOMLUME,
		CCI , 
		BOLL ,
		
		BIAS,
		ASI ,
		VR  ,
		OBV ,
		VOL
	}
	private IKlineEvent mKlineEvent;
	private Handler mHandler = new Handler();
	public static final int KLINE_MSG = 9999;		//k线消息回调的类型
	public static final Typeface TEXT_FONT = Typeface.create(Typeface.DEFAULT,Typeface.BOLD); //使用的字体
	public static final Typeface REGULAR_TEXT_FONT = Typeface.create( Typeface.DEFAULT, Typeface.NORMAL);		//A text font for regular text, like the chart labels.
	
	private KlineViewModel mViewModel;		//K线数据包
	private boolean        mIsDrawAxisInside = true;//指示指标刻度是否是画在图形内部
	private TechnicalIndicatorType mTechnicalIndicatorType;						//指标的种类 (macd:0,rsi:1,wr:2,kdj:3,psy:4,boll:5)
	private Stock mStock;							//当前股票信息
	private int mChartWidth;                        //画图区域宽度
	private int mKlineBarWidth ;                    //k线蜡烛的宽度(像素)
	private Rect mKlineBarArea;
	private Rect mTechnicalArea;
	protected int mBorderWidth;
	protected int mLeftAxisWidth;
	private int mFocusIndex ;
	private int mStartIndex = -1;                   //k线数据起始位置,可见区域内第一条数据在dataPack中的位置 
	private int mVisibleKlineCount; 				//满屏幕可以显示的k线条数,有屏幕可用宽带/每条k线的宽度得到该值
	private int mTextSize = 10;						//绘制文本的字体大小
	private int mFontHeight;							//字体高度
	private int klineAreaHeight;	              
	private int mTechnicalIndicatorHeight; 					//指标图的范围height，以便触摸屏的实现
	private int mDateTextSpaceHeight;              
	private Context mContext = null;						//当前Context内容
	private boolean mEnableTouch;
	
	private String[] mCurrentValues;
	private int[]    mCurrentColors;

	public boolean isEnableTouch() {
		return mEnableTouch;
	}

	public void setEnableTouch(boolean enable) {
		this.mEnableTouch = enable;
	}

	private Paint mPaint;
	public QwKlineView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public QwKlineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	public void setTechnicalIndicatorType( TechnicalIndicatorType type ){
		mTechnicalIndicatorType = type;
		repaint();
	}
	public TechnicalIndicatorType getTechnicalIndicatorType(){
		return  mTechnicalIndicatorType;
	}
	
	/**
	 * 初始化控件
	 */
	private void init() {
		mTechnicalIndicatorType = TechnicalIndicatorType.VOMLUME;
		mTextSize = getResources().getDimensionPixelSize(R.dimen.kline_text_size);
		mPaint = new Paint();
		mPaint.setTextSize(mTextSize);
		mPaint.setTypeface(REGULAR_TEXT_FONT);
		mKlineBarArea = new Rect();
		mTechnicalArea = new Rect();
		mBorderWidth = 1;
		mFocusIndex = -1;
		mKlineBarWidth = 7;
		
		/**设置 字体， k线区域高度*/		
		this.mFontHeight = mContext.getResources().getDimensionPixelSize(R.dimen.kline_text_size_s);
		mDateTextSpaceHeight = mContext.getResources().getDimensionPixelSize(R.dimen.kline_spacetozhibiao_height);
		
		this.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				System.out.println("onLongClick");
				return false;
			}
		});
		mCurrentValues = new String[5];
		mCurrentColors = new int[5];
	}
	/**
	 * 设置数据包
	 * @param data：数据包  ; handler 
	 */
	public void setData(KlineViewModel klineViewModel ) {
		if(null == klineViewModel) {
			return;
		}
		mStock = klineViewModel.getStock();
		mStartIndex = -1;
		mViewModel = klineViewModel;
	}
	
	
	public void setKlineEvent( IKlineEvent klilneEvent ){
		mKlineEvent = klilneEvent;
	}
	
	public void dataAdded( int addedCount ){
		mStartIndex += addedCount;
	}
	
	/**
	 * 设置当前股票信息
	 * @param stock：股票信息
	 */	
	public void setStock(Stock stock) {
		mStock = stock;
	}
	
	public void setDrawAxisInside( boolean isDrawAxisInside ){
		mIsDrawAxisInside = isDrawAxisInside;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (null == mViewModel || mViewModel.getDataSize()<=0){
			return;
		}
		int width = this.getWidth();
		int height = this.getHeight();
		
		mTechnicalIndicatorHeight = height / 4;
		klineAreaHeight = height - mDateTextSpaceHeight - mTechnicalIndicatorHeight;
		
		mLeftAxisWidth = 0;
		if (!mIsDrawAxisInside){
			mLeftAxisWidth = (int) (mPaint.measureText("00000.0") + 2);
		}
		mKlineBarArea.left = mLeftAxisWidth ;
		mKlineBarArea.top = mBorderWidth;
		
		
		mChartWidth = width - mLeftAxisWidth;
		mKlineBarArea.bottom = mKlineBarArea.top+klineAreaHeight;
		mKlineBarArea.right = width - mBorderWidth;
		
		mTechnicalArea.left = mLeftAxisWidth ;
		mTechnicalArea.top = height - mTechnicalIndicatorHeight ;
		mTechnicalArea.right = width - mBorderWidth;
		mTechnicalArea.bottom = height - mBorderWidth;
		
		drawKlineBar( canvas );
		
		int dateAixs = mKlineBarArea.top + klineAreaHeight + (mDateTextSpaceHeight -mFontHeight)/2;
		drawDate( mKlineBarArea.left,  dateAixs ,  mChartWidth , mFontHeight, canvas, mPaint);
		
		drawTechnicalIndicator( canvas );
		drawCurrentMA(canvas);
	}
	
	private int getTextHeight(Paint paint){
		Rect bounds = new Rect();
		paint.getTextBounds("123", 0, 1, bounds );
		return Math.abs( bounds.bottom - bounds.top);
	}
	
	private void drawCurrentMA( Canvas canvas ){
		if (mIsDrawAxisInside) {
			return;
		}
		
		int pos = getCurrentIndex();
		int[] ma = KlineViewModel.MA_PARAM;
		int x = mKlineBarArea.left + 4;
		int y = getTextHeight(mPaint)+ 4;
		float textWidth = 4;
		for (int i = 0; i < ma.length; i++) {
			mCurrentColors[i] = ColorUtils.MA_COLOR[i];
			mCurrentValues[i] = "MA"+ ma[i] +" "+FormatUtils.formatPrice( mStock  ,mViewModel.getMA( ma[i], pos) );
			textWidth += mPaint.measureText( mCurrentValues[i] ) + 10;
		}
		
		if (mViewModel.getDataSize() < mVisibleKlineCount) {
			drawTexts(canvas, (int) (mKlineBarArea.right - textWidth - 4) ,0, y , mCurrentValues, mCurrentColors, ma.length , 0);
		}else{
			drawTexts(canvas, x ,0, y , mCurrentValues, mCurrentColors, ma.length ,0);
		}
	}

	private int getCurrentIndex() {
		int pos = mStartIndex + mVisibleKlineCount - 1;
		if ( -1 != mFocusIndex ){
			pos = mFocusIndex+mStartIndex;
		}else if (pos >= mViewModel.getDataSize()) {
			pos = mViewModel.getDataSize() -1;
		}
		return pos;
	}
	
	private void drawTexts(Canvas canvas, int x , int y , int height, String[] texts , int[] colors , int length , int startIndex){
		
		mPaint.setTextAlign(Align.LEFT);
		int baseLine = y + height;
		float textWidth = 4;
		for (int i = startIndex; i < length; i++) {
			textWidth += mPaint.measureText( mCurrentValues[i] ) + 10;
		}
		Style style = mPaint.getStyle();
		mPaint.setColor(ColorUtils.KLINE_PRICE_LINE_COLOR_BG);
		mPaint.setStyle(Style.FILL);
		canvas.drawRect(x, y+2, x+textWidth, baseLine, mPaint);
		
		for (int i = startIndex; i < length; i++) {
			textWidth = mPaint.measureText( mCurrentValues[i] );
			mPaint.setColor(mCurrentColors[i]);
			canvas.drawText( mCurrentValues[i], x, baseLine, mPaint);
			x += textWidth + 10;
		}
		mPaint.setStyle(style);
	}
	
	private void drawKlineBar( Canvas canvas ) {
		int x = mKlineBarArea.left;
		int y = mKlineBarArea.top;
		int width = mKlineBarArea.right - mKlineBarArea.left ;
		int height= mKlineBarArea.bottom - mKlineBarArea.top;
		
		//首次绘制判断蜡烛线宽度是否已经设置
		if (mKlineBarWidth <= 0) {
			int size = mViewModel.getDataSize();
			if( size < 45){
				size = 45;
			}
			mKlineBarWidth = width / size;
		}
		
//		if (mKlineBarWidth > width / 15){
//			mKlineBarWidth -= 2;
//		}
		mVisibleKlineCount = width / (mKlineBarWidth + 1);
		int dataSize = mViewModel.getDataSize();
		if (-1 == mStartIndex){
			if (dataSize - mVisibleKlineCount > 0){
				mStartIndex = dataSize - mVisibleKlineCount;
			}
			else{
				mStartIndex = 0;
			}
		}
		
		if ((mVisibleKlineCount+mStartIndex) > dataSize) {
			if (mVisibleKlineCount <= dataSize) {
				mStartIndex = dataSize-mVisibleKlineCount;
			}else{
				mStartIndex = 0;
			}
		}
		
		mPaint.setColor(ColorUtils.BORDER_COLOR);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(getResources().getDimension(R.dimen.qii_trend_line_width ));
		mPaint.setAntiAlias(true);
		//绘制矩形及3条横线
		canvas.drawRect(x, y, x + width -1, y + height, mPaint);
		PathEffect eff = mPaint.getPathEffect();
		PathEffect effects = new DashPathEffect(new float[] { 1, 2, }, 1); 
		mPaint.setPathEffect(effects);
		for (int i = 1; i < 4; i++){
			canvas.drawLine(x, y + height * i / 4, x + width, y + height * i/ 4, mPaint);
		}
		mPaint.setPathEffect(eff);
		
		if(0 == mViewModel.getDataSize()){
			return;
		}
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		
		
		//计算K线区域中的最大最小值
		double tempTopValue = 0;//临时K线范围内的最高价
		double tempBottomValue = 0;//临时K线范围内的最低价
		//获取屏幕显示内的最高价和最低价
		tempTopValue =  mViewModel.getTopPriceDuringPointedDays(mStartIndex, tempEndIndex);
		tempBottomValue = mViewModel.getBottomPriceDuringPointedDays(mStartIndex, tempEndIndex);
		//获取ma5 ma10 ma30最大值
		double tempTopMA5 = mViewModel.getMATopValue(5, mStartIndex, tempEndIndex);
		double tempTopMA10 = mViewModel.getMATopValue(10, mStartIndex, tempEndIndex);
		double tempTopMA30 = mViewModel.getMATopValue(30, mStartIndex, tempEndIndex);
		
		tempTopValue = (tempTopMA5 > tempTopValue)?tempTopMA5:tempTopValue;
		tempTopValue = (tempTopMA10 > tempTopValue)?tempTopMA10:tempTopValue;
		tempTopValue = (tempTopMA30 > tempTopValue)?tempTopMA30:tempTopValue;
	
		//获取ma5 ma10 ma30最小值
		double tempBottomMA5 = mViewModel.getMABottomValue(5, mStartIndex, tempEndIndex);
		double tempBottomMA10 = mViewModel.getMABottomValue(10, mStartIndex, tempEndIndex);
		double tempBottomMA30 = mViewModel.getMABottomValue(30, mStartIndex, tempEndIndex);
		if(tempBottomMA5 < tempBottomValue && tempBottomMA5 > 0){
			tempBottomValue = tempBottomMA5;
		}
		if(tempBottomMA10 < tempBottomValue && tempBottomMA10 > 0){
			tempBottomValue = tempBottomMA10;
		}
		if(tempBottomMA30 < tempBottomValue && tempBottomMA30 > 0){
			tempBottomValue = tempBottomMA30;
		}
		if (tempTopValue == tempBottomValue) {
			//新股第一天最高跟最低相同
			tempTopValue += 0.01;
			tempBottomValue -= 0.01;
		}
		
		double var = 0.02*(tempTopValue - tempBottomValue);
		tempBottomValue = tempBottomValue - var;
		tempTopValue = tempTopValue + var;
		
		//绘制蜡烛
		drawKlineBar(canvas, new Rect( x , y , x + width , y + height) , tempBottomValue , tempTopValue);
//		double openPrice = 0;
//		double closePrice = 0;
//		double maxPrice = 0;
//		double minPrice = 0;
//		float tempx;
//		float tempy;
//		float klineBarHeight = 0;
		float klineBarMiddlex = 0;
		double interval = tempTopValue-tempBottomValue;
//		mPaint.setStyle(Style.FILL);
//		for (int i = mStartIndex; i < mVisibleKlineCount + mStartIndex && i < mViewModel.getDataSize(); i++){
//			mViewModel.setIndex(i);//设置K线焦点位置
//			
//			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex) + (mKlineBarWidth + 1) / 2;
//			openPrice = mViewModel.getOpenPrice();
//			closePrice = mViewModel.getClosePrice();
//			maxPrice = mViewModel.getMaxPrice();
//			minPrice = mViewModel.getMinPrice();
//			klineBarHeight =   (float) (height * (closePrice - openPrice) / interval);
//			if (2 > klineBarHeight) {
//				klineBarHeight = 2;
//			}
//			
////			System.out.println("open: "+openPrice +" close: " +closePrice+" max: "+maxPrice +"  min:"+minPrice);
//			
////			mPaint.setStyle(Style.STROKE);
//			if (closePrice > openPrice){
//				mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
//				//画蜡烛上下的线
//				/*canvas.drawLine(klineBarMiddlex, 
//								(int) (y + height * (tempTopValue - maxPrice) / (tempTopValue-tempBottomValue)),
//								klineBarMiddlex, 
//								(int) (y + height * (tempTopValue - minPrice) / (tempTopValue-tempBottomValue)),
//								paint);*/
//				
//				
//				tempy = (float) (y + height * (tempTopValue - closePrice) / interval);
//				canvas.drawLine(klineBarMiddlex, 
//						(float) (y + height * (tempTopValue - maxPrice) / interval),
//						klineBarMiddlex, 
//						tempy,
//						mPaint);
//				
//				canvas.drawLine(klineBarMiddlex, 
//						tempy + klineBarHeight,
//						klineBarMiddlex, 
//						(float) (y + height * (tempTopValue - minPrice) / interval),
//						mPaint);
//				
//				//画蜡烛
//				if (mKlineBarWidth > 1){
//					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
//					tempy = (float) (y + height * (tempTopValue - closePrice)/ interval);
////					mPaint.setStyle(Style.STROKE);
//					canvas.drawRect(tempx,
//									tempy,
//									tempx + mKlineBarWidth,
//									tempy + (int) (height * (closePrice - openPrice) / interval),
//									mPaint);
//				}
//				
//				mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
//			} 
//			else if (closePrice == openPrice){ //开盘等于收
//				if (0 == i) {
//					mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
//				}
//				else if (closePrice < mViewModel.getClosePrice(i - 1)){
//					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
//				}
//				else{
//					mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
//				}
//				canvas.drawLine(klineBarMiddlex, 
//								(float) (y + height * (tempTopValue - maxPrice) / interval),
//								klineBarMiddlex, 
//								(float) (y + height * (tempTopValue - minPrice) / interval),
//								mPaint);
//				mPaint.setStyle(Style.FILL);
//				canvas.drawRect(klineBarMiddlex - (mKlineBarWidth - 1) / 2,
//								(float) (y + height * (tempTopValue - openPrice)/ interval), 
//								klineBarMiddlex + (mKlineBarWidth - 1) / 2, 
//								(float) (y + height * (tempTopValue - openPrice) / interval)+2,
//								mPaint);
//			}
//			else{
//				mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
//				canvas.drawLine(klineBarMiddlex, 
//								(float) (y + height * (tempTopValue - maxPrice) / interval),
//								klineBarMiddlex,
//								(float) (y + height * (tempTopValue - minPrice) / interval),
//								mPaint);
//				if (mKlineBarWidth > 1){
//					mPaint.setStyle(Style.FILL);
//					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
//					tempy = (float) (y + height * (tempTopValue - openPrice)
//							/ interval);
//					canvas.drawRect(tempx,
//									tempy,
//									tempx + mKlineBarWidth,
//									tempy + (float) (height * (openPrice - closePrice) / interval),
//									mPaint);
//				}
//			}
//		}

		//绘制m5 m10 m30三条线
		double ma5 = 0;
		double ma10 = 0;
		double ma30 = 0;
		double lma5 = 0;
		double lma10 = 0;
		double lma30 = 0;
		for (int i = mStartIndex; i < mVisibleKlineCount + mStartIndex && i < mViewModel.getDataSize(); i++) {
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex)
					+ (mKlineBarWidth + 1) / 2;
			ma5 = mViewModel.getMA(5 , i);
			ma10 = mViewModel.getMA( 10 , i);
			ma30 = mViewModel.getMA( 30 , i);
			if (i == mStartIndex)  {
				lma5 = ma5;
				lma10 = ma10;
				lma30 = ma30;
				continue;
			}
			if (0 != lma5 && 0 != ma5) {
				mPaint.setColor(ColorUtils.MA_COLOR[0]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (tempTopValue - lma5) / interval),
								klineBarMiddlex, 
								(int) (y + height * (tempTopValue - ma5) / interval),
								mPaint);
			}
			if (0 != lma10 && 0 != ma10) {
				mPaint.setColor(ColorUtils.MA_COLOR[1]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (tempTopValue - lma10)/ interval),
								klineBarMiddlex,
								(int) (y + height * (tempTopValue - ma10) / interval),
								mPaint);
			}
			if (0 != lma30 && 0 != ma30) {
				mPaint.setColor(ColorUtils.MA_COLOR[2]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (tempTopValue - lma30)/ interval),
								klineBarMiddlex,
								(int) (y + height * (tempTopValue - ma30) / interval),
								mPaint);
			}
			lma5 = ma5;
			lma10 = ma10;
			lma30 = ma30;
		}
		mPaint.setColor(ColorUtils.LEFT_DATA_COLOR);
		mPaint.setAntiAlias(true);
		//左侧最高，最低，平均值
		if (mIsDrawAxisInside) {
			mPaint.setTextAlign(Align.LEFT);
			canvas.drawText(FormatUtils.formatPrice( mStock , tempTopValue), x, y + mFontHeight,  mPaint);
		} 
		else {
			mPaint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.formatPrice( mStock , tempTopValue), x - 1, y + mFontHeight, mPaint);
		}

		//判断平均值是否可以画的下
		if(height >= (mFontHeight * 3)) {
			if (mIsDrawAxisInside) {
				canvas.drawText(FormatUtils.formatPrice( mStock , (interval / 2 + tempBottomValue)), x, y + height / 2 + mFontHeight/2, mPaint);
				canvas.drawText(FormatUtils.formatPrice( mStock ,tempBottomValue / 1),  x, y + height, mPaint);
			}
			else {
				canvas.drawText(FormatUtils.formatPrice( mStock ,(interval / 2 + tempBottomValue) / 1),
								x - 2, 
								y + height / 2 + mFontHeight/2, 
								mPaint);
				canvas.drawText(FormatUtils.formatPrice( mStock ,tempBottomValue / 1), x - 2,  y + height, mPaint);
			}
		}
		
		if ( -1 != mFocusIndex ) {
			mPaint.setColor(ColorUtils.FOCUS_LINE_COLOR);
			int barWidth = mKlineBarWidth + 1;
			int focusX =  x + barWidth * mFocusIndex  + barWidth / 2;
			canvas.drawLine( focusX, y, focusX, y+height, mPaint);
			double close = mViewModel.getClosePrice(mFocusIndex+mStartIndex);
			int focusY = (int) (y + height * (tempTopValue - close) / interval);
			canvas.drawLine( x, focusY, x+width, focusY, mPaint);
			
			canvas.drawCircle( focusX, focusY, 2, mPaint);
		}
			
	}

	/**
	 * 绘制当前K线区域内的成交量
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawAmountBar(int x, int y, int width, int height, Canvas canvas ) {
//		//绘制背景边框和线
//		paint.setColor(ColorUtils.BORDER_COLOR);
//		paint.setStyle(Style.STROKE);
//		paint.setStrokeWidth(0);
//		paint.setAntiAlias(false);
//		canvas.drawRect(x, y, x + width-1, y + height, paint);
//		canvas.drawLine(x, y + height / 2, x + width-1, y + height / 2, paint);		
		if(0 == mViewModel.getDataSize()) {
			return;
		}
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		//获取屏幕区域内对应时间的最高，最低成交量
		double amountTopValue = mViewModel.getTopDealAmountDuringPointedDays(mStartIndex, tempEndIndex);
//		double amountBottomValue = mViewModel.getBottomDealAmountDuringPointedDays(mStartIndex, tempEndIndex);

		int klineBarMiddlex = 0;
		long ta = 0;
		double openPrice = 0;
		double closePrice = 0;
		int PADDING_HEIGHT = 1;// 矩形框高度+1，避免出现高度为0的矩形。
		long tempx = 0;
		long tempy = 0;
		for (int i = mStartIndex; i < mVisibleKlineCount + mStartIndex && i < mViewModel.getDataSize(); i++) {
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex)
								+ (mKlineBarWidth + 1) / 2;
			ta = mViewModel.getTotalDealAmount();
			if(ta<=0)
				continue;
			openPrice = mViewModel.getOpenPrice();
			closePrice = mViewModel.getClosePrice();
			mPaint.setStyle(Style.FILL);
			//计算高度
			int tvdiff = (int) (height * (amountTopValue - ta)/amountTopValue - PADDING_HEIGHT);
			//开盘价大于昨收价，红色
			if (closePrice > openPrice) {
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
//				mPaint.setStyle(Style.STROKE);
				tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
				tempy = y + tvdiff;
				mPaint.setStyle(Style.FILL);
				canvas.drawRect(tempx, 
								tempy, 
								tempx + mKlineBarWidth - 1, 
								tempy + height - tvdiff, 
								mPaint);
			}
			else if (closePrice == openPrice) {//开盘价等于昨收价 
				if (0 == i)  {
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]); 
					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx, tempy, tempx + mKlineBarWidth, tempy
							+ height - tvdiff, mPaint);
				} 
				else if (closePrice < mViewModel.getClosePrice(i - 1) ){  //跟前一个昨收比较，小则绿色
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
					mPaint.setStyle(Style.FILL);
					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx,
									tempy, 
									tempx + mKlineBarWidth, 
									tempy + height - tvdiff, 
									mPaint);
				} 
				else {
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
//					mPaint.setStyle(Style.STROKE);
					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx, 
									tempy, 
									tempx + mKlineBarWidth - 1,
									tempy + height - tvdiff, 
									mPaint);
				}
			} 
			else  {
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				mPaint.setStyle(Style.FILL);
				tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
				tempy = y + tvdiff;
				canvas.drawRect(tempx, 
								tempy,
								tempx + mKlineBarWidth, 
								tempy + height - tvdiff, 
								mPaint);
			}
		}

		// 左侧最高最低成交量值
//		mPaint.setColor(ColorUtils.AMOUNT_COLOR);
//		mPaint.setAntiAlias(true);
//		if (mIsDrawAxisInside) {
//			mPaint.setTextAlign(Align.LEFT);
//			canvas.drawText( FormatUtils.formatBigNumber(  amountTopValue ) , x, y + mFontHeight, mPaint);
//			canvas.drawText( FormatUtils.formatBigNumber(  amountBottomValue ), x, y + height, mPaint);
//		}
//		else {
//			mPaint.setTextAlign(Align.RIGHT);
//			canvas.drawText( FormatUtils.formatBigNumber(  amountTopValue ), x - 2, y + mFontHeight,mPaint);
//			canvas.drawText( FormatUtils.formatBigNumber(  amountBottomValue ), x - 2, y + height, mPaint);
//		}
		float hand = mViewModel.getStocksPerHand();
		drawAxis(x, y, width, height, FormatUtils.formatBigNumber( (long) (amountTopValue/hand) ), "0", canvas);
		
	}
	
	private int getDrawCount(){
		int iEndIndex = getEndIndex();
		return iEndIndex - mStartIndex+1;
	}
	/**
	 * 获取当前K线显示的区域
	 * @return iEndIndex: 当前K线显示的区域
	 */
	private int getEndIndex(){
		int iEndIndex = mStartIndex+mVisibleKlineCount;
		if(iEndIndex > mViewModel.getDataSize()-1) {
			iEndIndex = mViewModel.getDataSize()-1;
		}
		
		return iEndIndex;
	}
	/**
	 * 绘制MACD技术指标
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawMacd(int x, int y, int width, int height, Canvas canvas ) {
		
		//绘制当前焦点macd diff dea值
		mPaint.setColor(ColorUtils.CHAR_COLOR);
		mPaint.setTextAlign(Align.LEFT);
		
		KlineMACD klineMacd = mViewModel.getMACD();
		
		int originY = y;
		y += (mDateTextSpaceHeight -mFontHeight)/2;
		
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		
		//获取MACD的最大值，用于绘制纵坐标
		double topMacdValue = klineMacd.getMACDTopValue(mStartIndex, tempEndIndex);
		double bottomMacdValue = klineMacd.getMACDBottomValue(mStartIndex, tempEndIndex);
		y = originY;
		drawAxis(x, y, width, height, FormatUtils.format( 2, topMacdValue ), FormatUtils.format( 2, bottomMacdValue ), canvas);
		
		
		
		double inval = height/( topMacdValue - bottomMacdValue);
		int barWidth = mKlineBarWidth + 1;
		int klineBarMiddlex = x + barWidth / 2;
		double tbdv = topMacdValue - bottomMacdValue;
		mPaint.setColor(ColorUtils.BORDER_COLOR);
		if (tbdv >= -0.00001 && tbdv <= 0.00001) {
			canvas.drawLine(x, y +height  / 2, x + width, y +height  / 2, mPaint);
		}
		else {	
			canvas.drawLine(x, (int) (y  + inval * topMacdValue ), x + width,
					(int) (y  + inval *  topMacdValue ), mPaint);
		}
		
		
		for (int i = mStartIndex; i <= tempEndIndex  ; i++) {
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			double macd = klineMacd.getMACD( i );
			if (macd > 0) {
				mPaint.setColor( ColorUtils.UPDOWN_COLOR[0] );
			} else {
				mPaint.setColor( ColorUtils.UPDOWN_COLOR[1] );
			}
			canvas.drawLine(klineBarMiddlex, 
					(int) (y +  (topMacdValue - macd)*inval), 
					klineBarMiddlex,
					(int) (y + (topMacdValue - 0)*inval), 
					mPaint);
		}
		
		Path path = new Path();
		klineBarMiddlex = x + barWidth / 2;
		path.moveTo( klineBarMiddlex , (float) (y + ( topMacdValue - klineMacd.getDIFF(mStartIndex))*inval));
		for (int i = mStartIndex; i <=tempEndIndex  ; i++) {
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			path.lineTo(klineBarMiddlex,  (float) (y + ( topMacdValue - klineMacd.getDIFF( i ))*inval));
		}
		mPaint.setColor( ColorUtils.TECHNICAL_INDICATOR_COLOR[1] );
		canvas.drawPath(path, mPaint);
		
		
		path= new Path();
		klineBarMiddlex = x + barWidth / 2;
		path.moveTo( klineBarMiddlex , (float) (y + ( topMacdValue - klineMacd.getDea(mStartIndex))*inval));
		for (int i = mStartIndex; i <= tempEndIndex  ; i++) {
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			path.lineTo(klineBarMiddlex,  (float) (y + ( topMacdValue - klineMacd.getDea( i ))*inval));
		}
		mPaint.setColor( ColorUtils.TECHNICAL_INDICATOR_COLOR[2] );
		canvas.drawPath(path, mPaint);

		//绘制MACD指标线
		/*float klineBarMiddlex = 0;
		double macd = 0;
		double diff = 0;
		double dea = 0;
		double lastDiff = 0;
		double lastDea = 0;
		double tbdv = topMacdValue - bottomMacdValue;
		
		if (tbdv >= -0.00001 && tbdv <= 0.00001) {
			canvas.drawLine(x, y +height  / 2, x + width, y +height  / 2, mPaint);
		}
		else {	
			canvas.drawLine(x, (int) (y  + height * (topMacdValue) / tbdv), x + width,
					(int) (y  + height * (topMacdValue) / tbdv), mPaint);
		}
		for (int i = mStartIndex; i < mVisibleKlineCount + mStartIndex && i < mViewModel.getDataSize(); i++) {
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex) + (mKlineBarWidth + 1) / 2;
			macd = klineMacd.getMACD(i);
			diff = klineMacd.getDIFF(i);
			dea = klineMacd.getDea(i);
			
			if (macd > 0) {
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
			} 
			else {
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
			}
			canvas.drawLine(klineBarMiddlex, 
							(int) (y + height* (topMacdValue - macd) / tbdv), 
							klineBarMiddlex,
							(int) (y + height * (topMacdValue - 0) / tbdv), 
							mPaint);

			if (i == mStartIndex) {
				lastDea = dea;
				lastDiff = diff;
				continue;
			}

			if (0 != lastDiff && 0 != diff) {
				mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (topMacdValue - lastDiff) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (topMacdValue - diff)/ tbdv),
								mPaint);
			}
			if (0 != lastDea && 0 !=dea) {
				mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (topMacdValue - lastDea) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (topMacdValue - dea)/ tbdv),
								mPaint);
			}
			lastDea = dea;
			lastDiff = diff;
		}*/
	}

	/**
	 * 绘制RSI指标（相对强弱指标）
	 * 注：RSI取值范围应该在0-100之间，公式为：N日RSI =N日内收盘涨幅的平均值/(N日内收盘涨幅均值+N日内收盘跌幅均值) ×100% 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawRsi(int x, int y, int width, int height, Canvas canvas ) {
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		KlineRSI rsi = mViewModel.getRSI();
		//绘制RSI，RSI1，RSI2，RSI3
		mPaint.setTextAlign(Align.LEFT);
		
		double maxRsiValue = rsi.getMaxRSIValue( mStartIndex, tempEndIndex);//区间内最高Rsi值
		double minRsiValue = rsi.getMinRSIValue( mStartIndex, tempEndIndex);//区间内最低Rsi值
		
		drawAxis(x, y, width, height, FormatUtils.format(  2 ,maxRsiValue ), FormatUtils.format(  2 ,minRsiValue), canvas);
		
		//绘制纵坐标上的数值
//		mPaint.setColor(ColorUtils.AMOUNT_COLOR);
//		if (mIsDrawAxisInside) {
//			mPaint.setTextAlign(Align.LEFT);
//			canvas.drawText(FormatUtils.format(  0 ,maxRsiValue), x, y  + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format(  0 ,minRsiValue), x, y + height, mPaint);
//		}
//		else {
//			mPaint.setTextAlign(Align.RIGHT);
//			canvas.drawText(FormatUtils.format(  0 ,maxRsiValue), x - 2, y  + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format(  0 ,minRsiValue), x - 2, y + height, mPaint);
//		}

		int klineBarMiddlex = 0;
		double lastRsi6 = 0;
		double lastRsi12 = 0;
		double lastRsi24 = 0;
		double rsi6;
		double rsi12;
		double rsi24;
		double tbdv = maxRsiValue - minRsiValue;
		for (int i = mStartIndex; i < mVisibleKlineCount + mStartIndex && i < mViewModel.getDataSize(); i++) {
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex) + (mKlineBarWidth + 1) / 2;
			//取6天 12天 24天的数据
			rsi6 = rsi.getRSI( 6 , i);
			rsi12 = rsi.getRSI( 12 , i);
			rsi24 = rsi.getRSI( 24 , i);

			if (i == mStartIndex) {
				lastRsi6 = rsi6;
				lastRsi12 = rsi12;
				lastRsi24 = rsi24;
				continue;
			}

			if (0 != lastRsi6 && 0 != rsi6) {
				mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (maxRsiValue - lastRsi6) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (maxRsiValue - rsi6)/ tbdv),
								mPaint);
			}
			if (0 != lastRsi12 && 0 != rsi12) {
				mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (maxRsiValue - lastRsi12) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (maxRsiValue - rsi12)/ tbdv),
								mPaint);
			}
			if (0 != lastRsi24 && 0 != rsi24 ) {
				mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[2]);
				canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
								(int) (y + height * (maxRsiValue - lastRsi24) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (maxRsiValue - rsi24)/ tbdv), 
								mPaint);
			}
			lastRsi6 = rsi6;
			lastRsi12 = rsi12;
			lastRsi24 = rsi24;
		}
	}
	
	/**
	 * 绘制Boll指标
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 */
	private void drawBoll( int x, int y, int width, int height, Canvas canvas  ){
		KlineBOLL boll = mViewModel.getBOLL();
		int endIndex = getEndIndex();
		float minValue = boll.getDOWNBottomValue(mStartIndex, endIndex);
		float maxValue = boll.getUPTopValue(mStartIndex, endIndex);
		
		double tempTopValue = mViewModel.getTopPriceDuringPointedDays(mStartIndex, endIndex);//临时K线范围内的最高价
		double tempBottomValue = mViewModel.getBottomPriceDuringPointedDays(mStartIndex, endIndex);//临时K线范围内的最低价
		tempTopValue = Math.max( maxValue ,tempTopValue);
		tempBottomValue = Math.min( minValue ,tempBottomValue);
		
		drawAxis(x, y, width, height, FormatUtils.formatPrice( mStock, tempTopValue ), FormatUtils.formatPrice( mStock,tempBottomValue), canvas);
		
		drawKlineBar(canvas, new Rect( x , y , x + width , y + height) , tempBottomValue , tempTopValue);
		
		mPaint.setStyle(Style.STROKE);
		
		double inval = height/( tempTopValue - tempBottomValue);
		int barWidth = mKlineBarWidth + 1;
		int klineBarMiddlex = x + barWidth / 2;
		
		Path path = new Path();
		path.moveTo( klineBarMiddlex , (float) (y + ( maxValue - boll.getUPData( mStartIndex ))*inval));
		for (int i = mStartIndex; i <= endIndex ; i++) {
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			path.lineTo(klineBarMiddlex,  (float) (y + ( maxValue - boll.getUPData( i ))*inval));
		}
		mPaint.setColor( ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
		canvas.drawPath(path, mPaint);
		
		path.reset();
		klineBarMiddlex = x+ barWidth / 2;
		path.moveTo( klineBarMiddlex , (float) (y + ( maxValue - boll.getDOWNData(  mStartIndex ))*inval));
		for (int i = mStartIndex; i <= endIndex ; i++) {
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			path.lineTo(klineBarMiddlex,  (float) (y + ( maxValue - boll.getDOWNData( i ))*inval));
		}
		mPaint.setColor( ColorUtils.TECHNICAL_INDICATOR_COLOR[1] );
		canvas.drawPath(path, mPaint);
		
		path.reset();
		klineBarMiddlex = x+ barWidth / 2;
		path.moveTo( klineBarMiddlex , (float) (y + ( maxValue - boll.getMPData( mStartIndex ))*inval));
		for (int i = mStartIndex; i <= endIndex ; i++) {
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			path.lineTo(klineBarMiddlex,  (float) (y + ( maxValue - boll.getMPData( i ))*inval));
		}
		mPaint.setColor( ColorUtils.TECHNICAL_INDICATOR_COLOR[2] );
		canvas.drawPath(path, mPaint);
		
	}

	/**
	 * 绘制威廉指标,注：DTK提供的WR指标为近14个交易日的指标
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawWr(int x, int y, int width, int height, Canvas canvas ) {
		KlineWR wrObj = mViewModel.getWR();
		
		//绘制当前WR值
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
		int endIndex = getEndIndex();
//		canvas.drawText("W%R " + FormatUtils.format( 2, quoteKlinePacket.getWR()), x + charWidth * 5, y + fontHeight, paint);

		//绘制纵坐标
		mPaint.setColor(ColorUtils.AMOUNT_COLOR);
		mPaint.setTextAlign(Align.RIGHT);
		double maxWrVlaue = wrObj.getWRMaxValue(mStartIndex, endIndex );
		double minWrVlaue = wrObj.getWRMinValue(mStartIndex, endIndex );
		if (maxWrVlaue == 0 ) {
			maxWrVlaue = 1;
		}
		double inval = height/(maxWrVlaue - minWrVlaue);
		
//		if (mIsDrawAxisInside) {
//			canvas.drawText(FormatUtils.format( 2, maxWrVlaue), x, y  + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format( 2, minWrVlaue), x, y + height, mPaint);
//		}
//		else {
//			canvas.drawText(FormatUtils.format( 2, maxWrVlaue), x - 2, y  + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format( 2, minWrVlaue), x - 2, y + height, mPaint);
//		}
		drawAxis(x, y, width, height, FormatUtils.format( 2,maxWrVlaue ), FormatUtils.format( 2, minWrVlaue ), canvas);
		
		//绘制WR线
		mPaint.setTextAlign(Align.LEFT);
		
		int len = KlineWR.PARAM_VALUE.length;
		
		for (int k = 0; k < len; k++) {
			int  type = KlineWR.PARAM_VALUE[k];
			double wr = wrObj.getWR(type , mStartIndex);
			Path path = new Path();
			float klineBarMiddlex = 0;
			path.moveTo(x +klineBarMiddlex, (float) (y + ( maxWrVlaue - wr)*inval));
			int dataSize = mViewModel.getDataSize();
			mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[k]);
			
			for (int i = mStartIndex+1; i <= endIndex && i < dataSize; i++){
				int barWidth = mKlineBarWidth + 1;
				klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
				wr = wrObj.getWR(type , i);
				path.lineTo(klineBarMiddlex, (float) (y + ( maxWrVlaue - wr)*inval));
			}
			canvas.drawPath(path, mPaint);
		}
	}

	private void drawDate(int x, int y, int width, int height, Canvas canvas, Paint paint){
		
		if(0 == mViewModel.getDataSize()) {
			return;
		}
		
		//绘制起点，终点日期
		paint.setColor(ColorUtils.K_DATE_COLOR);
		paint.setTextAlign(Align.LEFT);
		
		if (null != mViewModel) {
			
			canvas.drawText( mViewModel.getDateTimeStr(mStartIndex), x, y + height, paint);
			
			int li = mStartIndex + mVisibleKlineCount;
			if (li > mViewModel.getDataSize() - 1){
				li = mViewModel.getDataSize() - 1;
			}
			if (li >= mViewModel.getDataSize()){				
				li = mViewModel.getDataSize() - 1;
			}
			if (li < 0){
				li = 0;
			}
			paint.setTextAlign(Align.RIGHT);
			
			canvas.drawText( mViewModel.getDateTimeStr(li), x + width, y + height, paint);
		}
	}
	
	private void drawTechnicalIndicator( Canvas canvas ) {
		int pos = getCurrentIndex();
		int length = 0;
		int x = mTechnicalArea.left;
		int y = mTechnicalArea.top;
		int width = mTechnicalArea.right - mTechnicalArea.left ;
		int height= mTechnicalArea.bottom - mTechnicalArea.top - 3*mBorderWidth;
		
		
		mPaint.setColor(ColorUtils.BORDER_COLOR);
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true);
		canvas.drawRect(x, y - 2*mBorderWidth , x + width, y + height + mBorderWidth, mPaint);
		int ty = y  + height / 2;
		
		PathEffect eff = mPaint.getPathEffect();
		PathEffect effects = new DashPathEffect(new float[] { 1, 2, }, 1); 
		mPaint.setPathEffect(effects);
		canvas.drawLine(x, ty, x + width, ty, mPaint);
		mPaint.setPathEffect( eff );
		float hand = mViewModel.getStocksPerHand();
		
		switch (mTechnicalIndicatorType) {
		case MACD:	
			drawMacd(x, y, width, height, canvas );
			length = 4;
			KlineMACD klineMacd = mViewModel.getMACD();
			mCurrentValues[0]  = "MACD(12,26,9)";
			mCurrentValues[1]  = "MACD: "+FormatUtils.format( 2 , klineMacd.getMACD(pos) );
			mCurrentValues[2]  = "DIF: "+FormatUtils.format( 2 , klineMacd.getDIFF(pos) );
			mCurrentValues[3]  = "DEA: "+FormatUtils.format( 2 , klineMacd.getDea(pos) );
			break;
		case RSI:
			drawRsi(x, y, width, height, canvas);
			KlineRSI rsi = mViewModel.getRSI();
			length = 4;
			mCurrentValues[0]  = "RSI(6,12,24)";
			mCurrentValues[1]  = "RSI6: "+FormatUtils.format( 2 , rsi.getRSI( 6 , pos) );
			mCurrentValues[2]  = "RSI12: "+FormatUtils.format( 2 , rsi.getRSI( 12 , pos) );
			mCurrentValues[3]  = "RSI24: "+FormatUtils.format( 2 , rsi.getRSI( 24 , pos) );
			break;
		case WR:
			drawWr(x, y, width, height, canvas);
			KlineWR wr = mViewModel.getWR();
			length = KlineWR.PARAM_VALUE.length;
			mCurrentValues[0]  = "WR(14,28)";
			mCurrentValues[1]  = "WR14: "+FormatUtils.format( 2 , wr.getWR( 14 , pos) );
			mCurrentValues[2]  = "WR28: "+FormatUtils.format( 2 , wr.getWR( 28 , pos) );
			length += 1;
			break;
		case KDJ:
			drawKdj(x, y, width, height, canvas);
			KlineKDJ kdj = mViewModel.getKDJ();
			length = 4;
			mCurrentValues[0]  = "KDJ(9,3,3)";
			mCurrentValues[1]  = "K: "+FormatUtils.format( 2 , kdj.getKData(pos) );
			mCurrentValues[2]  = "D: "+FormatUtils.format( 2 , kdj.getDData(pos) );
			mCurrentValues[3]  = "J: "+FormatUtils.format( 2 , kdj.getJData(pos) );
			break;
		case PSY:
			drawPsy(x, y, width, height, canvas);
			KlinePSY psy = mViewModel.getPSY();
			length = 3;
			mCurrentValues[0]  = "PSY(12,6)";
			mCurrentValues[1]  = "PSY: "+FormatUtils.format( 2 , psy.getPSY( pos) );
			mCurrentValues[2]  = "PSYMA: "+FormatUtils.format( 2 , psy.getPSYMA( pos) );
			break;
		case VOMLUME:	
			drawAmountBar(x, y, width, height, canvas );
			length = 2;
			mViewModel.setIndex( pos );
			mCurrentValues[0]  = "成交量";
			mCurrentValues[1]  = FormatUtils.formatStockVolume(mStock ,mViewModel.getTotalDealAmount()/hand);
			break;
		case CCI:
			drawCci(x, y, width, height, canvas);
			KlineCCI cci = mViewModel.getCCI();
			length = 2;
			mCurrentValues[0]  = "CCI(14)";
			mCurrentValues[1]  = "CCI: "+FormatUtils.format( 2 , cci.getCCIData( pos) );
			break;
		case BOLL:
			drawBoll(x, y, width, height, canvas);
			KlineBOLL boll = mViewModel.getBOLL();
			length = 4;
			mCurrentValues[0]  = "BOLL(20,2)";
			mCurrentValues[1]  = "UP: "+FormatUtils.format( 2 , boll.getUPData( pos) );
			mCurrentValues[2]  = "LOWER: "+FormatUtils.format( 2 , boll.getDOWNData( pos) );
			mCurrentValues[3]  = "MID: "+FormatUtils.format( 2 , boll.getMPData( pos) );
			break;
		case BIAS:
			drawBIAS(x, y, width, height, canvas);
			KlineBIAS bias = mViewModel.getBIAS();
			length = 4;
			mCurrentValues[0]  = "BIAS(6,12,24)";
			mCurrentValues[1]  = "BIAS6: "+FormatUtils.format( 2 , bias.getBIAS( KlineBIAS.PARAM_VALUE[0],  pos) );
			mCurrentValues[2]  = "BIAS12: "+FormatUtils.format( 2 , bias.getBIAS( KlineBIAS.PARAM_VALUE[1],  pos) );
			mCurrentValues[3]  = "BIAS24: "+FormatUtils.format( 2 , bias.getBIAS( KlineBIAS.PARAM_VALUE[2],  pos) );
			break;
		case ASI:
			drawASI(x, y, width, height, canvas);
			KlineASI asi = mViewModel.getASI();
			length = 3;
			mCurrentValues[0]  = "ASI(6)";
			mCurrentValues[1]  = "ASI: "+FormatUtils.format( 2 , asi.getASIData( pos) );
			mCurrentValues[2]  = "ASIMA: "+FormatUtils.format( 2 , asi.getASIMAData( pos) );
			break;
		case VR:
			drawVR(x, y, width, height, canvas);
			KlineVR klineVR = mViewModel.getVR();
			length = 2;
			mCurrentValues[0]  = "VR(24)";
			mCurrentValues[1]  = "VR: "+FormatUtils.format( 2 , klineVR.getVRData( pos) );
			break;
		case OBV:
			drawOBV(x, y, width, height, canvas);
			KlineOBV klineOBV = mViewModel.getOBV();
			length = 2;
			mCurrentValues[0]  = "OBV";
			mCurrentValues[1]  = "OBV: "+FormatUtils.formatStockVolume(mStock ,klineOBV.getOBVData( pos)/hand );
			break;
		case VOL:
			drawVOL(x, y, width, height, canvas);
			KlineVOL klineVOL = mViewModel.getVOL();
			length = 4;
			mCurrentValues[0]  = "VOL(5,10,20)";
			mCurrentValues[1]  = "VOL5: "+FormatUtils.formatStockVolume(mStock ,klineVOL.getVOL( KlineVOL.PARAM_VALUE[0], pos)/hand );
			mCurrentValues[2]  = "VOL10: "+FormatUtils.formatStockVolume(mStock ,klineVOL.getVOL( KlineVOL.PARAM_VALUE[1], pos)/hand );
			mCurrentValues[3]  = "VOL20: "+FormatUtils.formatStockVolume(mStock ,klineVOL.getVOL( KlineVOL.PARAM_VALUE[2], pos)/hand );
			break;
		default: 
			length = 0;
			drawAmountBar( x, y, width, height, canvas );
			break;
		}
		int index = 0;
		if ( -1 != mFocusIndex ){
			mPaint.setColor(ColorUtils.FOCUS_LINE_COLOR);
			int barWidth = mKlineBarWidth + 1;
			int focusX =  x + barWidth * mFocusIndex  + barWidth / 2;
			canvas.drawLine( focusX, y, focusX, y+height, mPaint);
			index = 1;
		}
		
		if (mIsDrawAxisInside ||  -1 == mFocusIndex ) {
			mCurrentColors[0] = ColorUtils.LEFT_DATA_COLOR;
			int textWidth = (int) mPaint.measureText(mCurrentValues[0]);
			drawTexts(canvas, mTechnicalArea.right - textWidth - 15, y , getTextHeight(mPaint) + 4, mCurrentValues, mCurrentColors, 1 , 0);
		}else{
			mCurrentColors[1] = ColorUtils.TECHNICAL_INDICATOR_COLOR[0];
			mCurrentColors[2] = ColorUtils.TECHNICAL_INDICATOR_COLOR[1];
			mCurrentColors[3] = ColorUtils.TECHNICAL_INDICATOR_COLOR[2];
			mCurrentColors[0] = ColorUtils.LEFT_DATA_COLOR;
			drawTexts(canvas, x + 4, y , getTextHeight(mPaint) + 4, mCurrentValues, mCurrentColors, length , index);
		}
		
		
	}

	private void drawVOL(int x, int y, int width, int height, Canvas canvas) {

//		//绘制背景边框和线
//		paint.setColor(ColorUtils.BORDER_COLOR);
//		paint.setStyle(Style.STROKE);
//		paint.setStrokeWidth(0);
//		paint.setAntiAlias(false);
//		canvas.drawRect(x, y, x + width-1, y + height, paint);
//		canvas.drawLine(x, y + height / 2, x + width-1, y + height / 2, paint);		
		if(0 == mViewModel.getDataSize())
		{
			return;
		}
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		int drawCount = getDrawCount();
		//获取屏幕区域内对应时间的最高，最低成交量
		double max = mViewModel.getTopDealAmountDuringPointedDays(mStartIndex, tempEndIndex);
		double min = 0;// mViewModel.getBottomDealAmountDuringPointedDays(mStartIndex, tempEndIndex);

		int klineBarMiddlex = 0;
		int endIndex = getEndIndex();
		long ta = 0;
		double openPrice = 0;
		double closePrice = 0;
		int PADDING_HEIGHT = 1;// 矩形框高度+1，避免出现高度为0的矩形。
		long tempx = 0;
		long tempy = 0;
		mPaint.setStyle(Style.FILL);
		for (int i = mStartIndex; i <= endIndex && i < mViewModel.getDataSize(); i++) {
			mViewModel.setIndex(i);
			int barWidth = mKlineBarWidth + 1;
			klineBarMiddlex = x + barWidth * (i - mStartIndex) + barWidth / 2;
			ta = mViewModel.getTotalDealAmount();
			if(ta<=0){
				continue;
			}
			openPrice = mViewModel.getOpenPrice();
			closePrice = mViewModel.getClosePrice();
			//计算高度
			int tvdiff = (int) (height * (max - ta) / (max-min))
								- PADDING_HEIGHT;
			//开盘价大于昨收价，红色
			if (closePrice > openPrice){
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
				tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
				tempy = y + tvdiff;
				canvas.drawRect(tempx, 
								tempy, 
								tempx + mKlineBarWidth - 1, 
								tempy + height - tvdiff, 
								mPaint);
			}else if (closePrice == openPrice) {//开盘价等于昨收价
				if (0 == i)  {
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]); 
					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx, tempy, tempx + mKlineBarWidth, tempy + height - tvdiff, mPaint);
				} else if (closePrice < mViewModel.getClosePrice(i - 1) ){  //跟前一个昨收比较，小则绿色
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
					mPaint.setStyle(Style.FILL);
					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx,
									tempy, 
									tempx + mKlineBarWidth, 
									tempy + height - tvdiff, 
									mPaint);
				}  else{
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
					tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx, 
									tempy, 
									tempx + mKlineBarWidth - 1,
									tempy + height - tvdiff, 
									mPaint);
				}
			} 
			else {
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				tempx = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
				tempy = y + tvdiff;
				canvas.drawRect(tempx, 
								tempy,
								tempx + mKlineBarWidth, 
								tempy + height - tvdiff, 
								mPaint);
			}
		}
		mPaint.setStyle(Style.STROKE);

		// 左侧最高最低成交量值
		mPaint.setColor(ColorUtils.AMOUNT_COLOR);
		mPaint.setAntiAlias(true);
//		if (mIsDrawAxisInside) 
//		{
//			mPaint.setTextAlign(Align.LEFT);
//			canvas.drawText( String.valueOf(String.valueOf((long) max )), x, y + mFontHeight, mPaint);
//			canvas.drawText( String.valueOf(String.valueOf((long) min )), x, y + height, mPaint);
//		}
//		else
//		{
//			mPaint.setTextAlign(Align.RIGHT);
//			canvas.drawText( String.valueOf(String.valueOf((long) max )), x - 2, y + mFontHeight,mPaint);
//			canvas.drawText( String.valueOf(String.valueOf((long) min )), x - 2, y + height, mPaint);
//		}
		float hand = mViewModel.getStocksPerHand();
		drawAxis(x, y, width, height, FormatUtils.formatBigNumber( max/hand ), "0", canvas);
		
		KlineVOL klineOBV = mViewModel.getVOL();
		
		double[] datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  klineOBV.getVOL(KlineVOL.PARAM_VALUE[0] ,  i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, tempEndIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
		
		datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  klineOBV.getVOL(KlineVOL.PARAM_VALUE[1] ,  i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, tempEndIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[1] );
		
		datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  klineOBV.getVOL(KlineVOL.PARAM_VALUE[2] ,  i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, tempEndIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[2] );
	}

	private void drawOBV(int x, int y, int width, int height, Canvas canvas) {
		KlineOBV klineOBV = mViewModel.getOBV();
		int endIndex = getEndIndex();
		int drawCount = getDrawCount();
		double max = klineOBV.getOBVTopValue(mStartIndex, endIndex);
		double min = klineOBV.getOBVBottomValue(mStartIndex, endIndex);
		
		float hand = mViewModel.getStocksPerHand();
		drawAxis(x, y, width, height, FormatUtils.formatBigNumber( (long) (max/hand) ), FormatUtils.formatBigNumber( (long) (min/hand) ), canvas);
		
		double[] datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  klineOBV.getOBVData(i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
		
	}

	private void drawVR(int x, int y, int width, int height, Canvas canvas) {
		KlineVR klineVR = mViewModel.getVR();
		int endIndex = getEndIndex();
		int drawCount = getDrawCount();
		double max = klineVR.getVRTopValue(mStartIndex, endIndex);
		double min = klineVR.getVRBottomValue(mStartIndex, endIndex);
		
		drawAxis(x, y, width, height, FormatUtils.format( 2 , max ), FormatUtils.format( 2 , min), canvas);
		
		double[] datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  klineVR.getVRData(i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
	}

	private void drawCci(int x, int y, int width, int height, Canvas canvas) {
		KlineCCI cci = mViewModel.getCCI();
		int endIndex = getEndIndex();
		double min = cci.getCCIBottomValue( mStartIndex, endIndex);
		double max = cci.getCCITopValue( mStartIndex, endIndex);
		double inval = height/(max - min);
		
		drawAxis(x, y, width, height, FormatUtils.format( 2, max), FormatUtils.format( 2, min), canvas);
		
		mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
//		if (mIsDrawAxisInside) {
//			mPaint.setTextAlign(Align.LEFT);
//			canvas.drawText(FormatUtils.format( 2, max), x, y + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format( 2, min), x, y + height, mPaint);
//		}
//		else {
//			mPaint.setTextAlign(Align.RIGHT);
//			canvas.drawText(FormatUtils.format( 2, max), x - 2, y  + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format( 2, min), x - 2, y + height, mPaint);
//		}
		int klineBarMiddlex = x + (mKlineBarWidth + 1) / 2;
		double cciValue = cci.getCCIData(mStartIndex);
		Path path = new Path();
		path.moveTo(klineBarMiddlex, (float) (y + (max-cciValue)*inval));
		for (int i = mStartIndex+1; i < endIndex && i < mViewModel.getDataSize(); i++) {
			cciValue = cci.getCCIData(i);
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex) + (mKlineBarWidth + 1) / 2;
			path.lineTo(klineBarMiddlex, (float) (y + (max-cciValue)*inval));
		}
		canvas.drawPath(path, mPaint);
	}

	private void drawKdj(int x, int y, int width, int height, Canvas canvas ) {
		 //设置焦点线位置
		KlineKDJ kdj = mViewModel.getKDJ();
		mPaint.setColor(ColorUtils.CHAR_COLOR);
		mPaint.setTextAlign(Align.LEFT);
		int endIndex = getEndIndex();
		
		//绘制纵坐标的KDJ值
		double topKdj = kdj.getKDJTopValue( mStartIndex , endIndex);
		double bottomKdj = kdj.getKDJBottomValue(  mStartIndex , endIndex );
		if (bottomKdj > 90) {
			bottomKdj = 90;
		}
//		mPaint.setColor(ColorUtils.AMOUNT_COLOR);
//		if (mIsDrawAxisInside) {
//			canvas.drawText(FormatUtils.format( 2, topKdj), x, y + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format( 2, bottomKdj), x, y + height, mPaint);
//		}
//		else  {
//			mPaint.setTextAlign(Align.RIGHT);
//			canvas.drawText(FormatUtils.format( 2, topKdj), x - 2, y  + mFontHeight, mPaint);
//			canvas.drawText(FormatUtils.format( 2, bottomKdj), x - 2, y + height, mPaint);
//		}
		drawAxis(x, y, width, height, FormatUtils.format( 2, topKdj), FormatUtils.format( 2, bottomKdj), canvas);
		//绘制轨迹
		int klineBarMiddlex = 0;
//		y += mDateTextSpaceHeight;
//		height -=mDateTextSpaceHeight;
		double lastk = 0 , lastd = 0, lastj = 0;
		double k , d , j;
		double tbdv = topKdj - bottomKdj;
		for (int i = mStartIndex; i <= endIndex && i < mViewModel.getDataSize(); i++) {
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex)
					+ (mKlineBarWidth + 1) / 2;
			k = kdj.getKData(i);
			d = kdj.getDData(i);
			j = kdj.getJData(i);

			if (i == mStartIndex)
			{
				lastk = k;
				lastd = d;
				lastj = j;
				continue;
			}

			mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
			canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
							(int) (y + height * (topKdj - lastk) / tbdv),
							klineBarMiddlex, (int) (y + height * (topKdj - k) / tbdv),
							mPaint);
			mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
			canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
							(int) (y + height * (topKdj - lastd) / tbdv),
							klineBarMiddlex, (int) (y + height * (topKdj - d) / tbdv),
							mPaint);
			mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[2]);
			canvas.drawLine(klineBarMiddlex - mKlineBarWidth - 1,
							(int) (y + height * (topKdj - lastj) / tbdv),
							klineBarMiddlex, (int) (y + height * (topKdj - j) / tbdv),
							mPaint);
			lastk = k;
			lastd = d;
			lastj = j;
		}
	}
	
	private void drawBIAS(int x, int y, int width, int height, Canvas canvas ) {
		KlineBIAS bias = mViewModel.getBIAS();
		int endIndex = getEndIndex();
		int drawCount = getDrawCount();
		
		double max = bias.getMaxValue(mStartIndex, endIndex);
		double min = bias.getMinValue(mStartIndex, endIndex);
		
		drawAxis(x, y, width, height, FormatUtils.format( 2 , max), FormatUtils.format( 2 , min), canvas);
		
		double[] datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  bias.getBIAS( KlineBIAS.PARAM_VALUE[0], i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
		
		datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  bias.getBIAS( KlineBIAS.PARAM_VALUE[1], i+ mStartIndex);
		}
		drawPath(x, y, height,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[1] );
		
		datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  bias.getBIAS( KlineBIAS.PARAM_VALUE[2], i+ mStartIndex);
		}
		drawPath(x, y, height,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[2] );
		
		
	}

	private void drawASI(int x, int y, int width, int height, Canvas canvas ) {
		KlineASI asi = mViewModel.getASI();
		int endIndex = getEndIndex();
		int drawCount = getDrawCount();
		double max = Math.max( asi.getASITopValue(mStartIndex, endIndex) , asi.getASIMATopValue(mStartIndex, endIndex));
		double min = Math.min( asi.getASIBottomValue(mStartIndex, endIndex) , asi.getASIMABottomValue(mStartIndex, endIndex));
		
		drawAxis(x, y, width, height, FormatUtils.format( 2 , max), FormatUtils.format( 2 , min), canvas);
		
		double[] datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  asi.getASIData(i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
		
		datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  asi.getASIMAData(i+ mStartIndex);
		}
		drawPath(x, y, height,canvas, datas, endIndex, min , max, ColorUtils.TECHNICAL_INDICATOR_COLOR[1] );
	}

	private void drawPath(int x, int y, int height, Canvas canvas, double[] datas, int endIndex , double min , double max , int color) {
		int klineBarMiddlex;
		int barWidth = mKlineBarWidth + 1;
		klineBarMiddlex = x + barWidth / 2;
		Path path = new Path();
		double inval = height / (max - min);
		path .moveTo( klineBarMiddlex , y +(float) (inval  * (max - datas[0])));
		for (int i = 0; i < datas.length; i++) {
			klineBarMiddlex = x + barWidth * i + barWidth / 2;
			path.lineTo(klineBarMiddlex, (float) (y + (max- datas[i])*inval));
		}
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor( color );
		canvas.drawPath(path, mPaint);
	}
	
	private void drawAxis( int x, int y, int width, int height, String max , String min , Canvas canvas ){
		Style style = mPaint.getStyle();
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(ColorUtils.LEFT_DATA_COLOR);
		if (mIsDrawAxisInside) {
			canvas.drawText( max , x, y  + mFontHeight, mPaint);
			canvas.drawText( min , x, y + height, mPaint);
		}
		else  {
			mPaint.setTextAlign(Align.RIGHT);
			canvas.drawText( max , x - 2, y  + mFontHeight, mPaint);
			canvas.drawText( min , x - 2, y + height, mPaint);
		}
		mPaint.setStyle( style );
	}

	/**
	 * 绘制PSY指标（心理线）
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawPsy(int x, int y, int width, int height, Canvas canvas ) {
		
		KlinePSY psy = mViewModel.getPSY();
		//获取当前K线显示的区域
		int endIndex = getEndIndex();
		int drawCount = getDrawCount();
		//psy值
		mPaint.setColor(ColorUtils.CHAR_COLOR);
		mPaint.setTextAlign(Align.LEFT);
		
		//绘制纵坐标的psy最大，最小值
		double maxPsyValue = psy.getPSYAndPSYMATopValue(mStartIndex, endIndex);
		double minPsyValue = psy.getPSYAndPSYMABottomValue(mStartIndex, endIndex);
		if (maxPsyValue == 0 && minPsyValue == 0) {
			maxPsyValue = 1;
			minPsyValue = -1;
		}
		drawAxis(x, y, width, height, FormatUtils.format( 2 , maxPsyValue), FormatUtils.format( 2 , minPsyValue), canvas);
		
		//绘制轨迹
//		double tempPsy;
//		double bewteenPsyValue = maxPsyValue - minPsyValue;//最大与最小之间的差值
//		double proPositionX=0,prePosistionY=0;//前一点的坐标位置
//		double positionX, positionY;//当前点的坐标位置
//		mPaint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
//		for (int i = mStartIndex; i < tempEndIndex && i < mViewModel.getDataSize(); i++) 
//		{
//			mViewModel.setIndex(i);
//			tempPsy = (float) psy.getPSY(i);
//			positionX = x + (mKlineBarWidth + 1) * (i - mStartIndex) + (mKlineBarWidth + 1) / 2;
//			positionY = y + height * (maxPsyValue - tempPsy) / bewteenPsyValue;
//			if (i != mStartIndex) {//第一个点不画线
//				canvas.drawLine((float)proPositionX,
//						(float)prePosistionY,
//						(float)positionX,
//						(float)positionY, mPaint);
//			}
//			
//			proPositionX = positionX;
//			prePosistionY = positionY;
//		}
		
		double[] datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  psy.getPSY(i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, endIndex, minPsyValue , maxPsyValue, ColorUtils.TECHNICAL_INDICATOR_COLOR[0] );
		
		datas = new double[drawCount];
		for (int i = 0 ; i < drawCount; i++) {
			datas[i] =  psy.getPSYMA(i+ mStartIndex);
		}
		drawPath(x, y, height ,canvas, datas, endIndex, minPsyValue , maxPsyValue, ColorUtils.TECHNICAL_INDICATOR_COLOR[1] );
		
	}

	/**
	 * Schedule a user interface repaint.
	 */
	public void repaint() {
		if (mHandler.getLooper().getThread() == Thread.currentThread()) {
			invalidate();
		} else {
			mHandler.post(new Runnable() {
				public void run() {
					invalidate();
				}
			});
		}
	}
	
	private void drawKlineBar( Canvas canvas , Rect rect, double minValue, double maxValue) {
		int x = rect.left;
		int y = rect.top;
		int width = rect.right - rect.left ;
		int height= rect.bottom - rect.top;
		if(0 == mViewModel.getDataSize()){
			return;
		}
		
		//首次绘制判断蜡烛线宽度是否已经设置
		if (mKlineBarWidth <= 0) {
			int size = mViewModel.getDataSize();
			if( size < 45){
				size = 45;
			}
			mKlineBarWidth = width / size;
		}
		
		mVisibleKlineCount = width / (mKlineBarWidth + 1);
		int dataSize = mViewModel.getDataSize();
		if (-1 == mStartIndex){
			if (dataSize > mVisibleKlineCount ){
				mStartIndex = dataSize - mVisibleKlineCount;
			}
			else{
				mStartIndex = 0;
			}
		}
		
		if ((mVisibleKlineCount+mStartIndex) > dataSize) {
			if (mVisibleKlineCount <= dataSize) {
				mStartIndex = dataSize-mVisibleKlineCount;
			}else{
				mStartIndex = 0;
			}
		}
		
		
		//获取当前K线显示的区域
//		int tempEndIndex = getEndIndex();
		
		//绘制蜡烛
		double openPrice = 0 , closePrice = 0 , maxPrice = 0 ,minPrice = 0;
		float klineBarMiddlex = 0 ,tempx ;
		double interval = maxValue - minValue;
		
		mPaint.setStyle(Style.FILL);
		for (int i = mStartIndex; i < mVisibleKlineCount + mStartIndex && i < mViewModel.getDataSize(); i++){
			mViewModel.setIndex(i);//设置K线焦点位置
			
			klineBarMiddlex = x + (mKlineBarWidth + 1) * (i - mStartIndex) + (mKlineBarWidth + 1) / 2;
			tempx           = klineBarMiddlex - (mKlineBarWidth - 1) / 2;
			
			openPrice  = mViewModel.getOpenPrice();
			closePrice = mViewModel.getClosePrice();
			maxPrice   = mViewModel.getMaxPrice();
			minPrice   = mViewModel.getMinPrice();
			
			if (closePrice > openPrice){
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
			}else if (closePrice == openPrice){ //开盘等于昨收
				if (0 == i) {
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				} else if (closePrice < mViewModel.getClosePrice(i - 1)){
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				} else{
					mPaint.setColor(ColorUtils.UPDOWN_COLOR[0]);
				}
			}else{
				mPaint.setColor(ColorUtils.UPDOWN_COLOR[1]);
			}
			
			float startY = (float) (y + height * (maxValue - maxPrice) / interval);
			float stopY = (float) (y + height * (maxValue - minPrice) / interval);
			canvas.drawLine( klineBarMiddlex, startY, klineBarMiddlex, stopY, mPaint );
			
			startY = (float) (y + height * (maxValue - openPrice) / interval);
			stopY  = (float) (y + height * (maxValue - closePrice) / interval);
			float minY = Math.min(startY, stopY);
			stopY = Math.max(startY, stopY);
			startY = minY;
			if ( ( stopY - startY) < 2) {
				stopY += 0.5;
				startY -= 0.5;
			}
			
			canvas.drawRect( tempx , startY, tempx + mKlineBarWidth , stopY, mPaint);
		}
	}

	private PointF startPoint = new PointF();   
    private enum EventType{
    	NONE,DRAG,FOCUS_MOVE,ZOOM
    }
    private float oldDistance; 
    private EventType mTouchMode;
    float mLastTouchX;
    int oldKlineWidth;
	private CheckForTap mPendingCheckForTap;
    private boolean mIsActionUp;
    
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		System.out.println(mTouchMode+"Action:"+event.getAction() +" x :"+event.getX()+" y :"+event.getY());
		if (!mEnableTouch) {
			return false;
		}
		
		//获取触控的点数  
        int pointCount = event.getPointerCount();  
 
        switch(event.getAction() & MotionEvent.ACTION_MASK){   
        //单手指按下   
        case MotionEvent.ACTION_DOWN: 
        	mIsActionUp = false;
            //将当前的坐标保存为起始点    
            startPoint.set(event.getX(), event.getY());  
            mLastTouchX = event.getX();
            mTouchMode = EventType.NONE;  
            oldKlineWidth = mKlineBarWidth;
            
            if (mPendingCheckForTap == null) {
                mPendingCheckForTap = new CheckForTap();
            }
            postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
            
            break;   
            //第二根手指按下  
        case MotionEvent.ACTION_POINTER_DOWN:  
        	setPressed(false);  
            if (pointCount == 2) {
            	oldDistance = (float) Math.sqrt((event.getX(0) - event.getX(1)) * (event.getX(0) - event.getX(1)) + (event.getY(0) - event.getY(1)) * (event.getY(0) - event.getY(1)));
            	mTouchMode = EventType.ZOOM;
            }
            break;  
         
        case MotionEvent.ACTION_MOVE: 
        	float moveX = Math.abs( event.getX() - startPoint.x );
        	float moveY = Math.abs( event.getY() - startPoint.y);
        	
        	if (mTouchMode == EventType.NONE) {
        		if ( moveX > 5 || moveY > 5 ) {
    				setPressed(false);
    				mTouchMode = EventType.DRAG;
    			}
			}
        	
        	
            //拖拽模式  
            if (mTouchMode == EventType.DRAG) {
            	doDrag(event);
            	if (null != mKlineEvent) {
    				mKlineEvent.onLeftDataChanged( mStartIndex, mViewModel, this);
    			}
            }
            else if (mTouchMode == EventType.ZOOM)  
            {//缩放模式  
            	doZoom(event);
            	if (null != mKlineEvent) {
    				mKlineEvent.onLeftDataChanged( mStartIndex, mViewModel, this);
    			}
            } else if (mTouchMode == EventType.FOCUS_MOVE)  {
            	doMoveFocusPoint(event.getX());
            }
            break;   
        //有手指抬起，将模式设为NONE  
        case MotionEvent.ACTION_UP:   
        case MotionEvent.ACTION_POINTER_UP:
        	System.out.println("ACTION_UP");
        	mIsActionUp = true;
        	if ( null != mKlineEvent)  {
        		mKlineEvent.onUnFocus(mViewModel, this);
            }
            mTouchMode = EventType.NONE; 
            mFocusIndex = -1;
            setPressed(false);
            invalidate();
            break;   
        default: 
        }           
		return true;
	}

	private void doMoveFocusPoint( float x) {
		if ( mKlineBarArea.left >= x || mKlineBarArea.right <= x) {
			return;
		}
		int endIndex = getDrawCount()-1;
		x -= mKlineBarArea.left;
		int focusIdx = (int) (x/(1+mKlineBarWidth));
		if (endIndex < focusIdx) {
			focusIdx = endIndex;
		}
		if (focusIdx >= mVisibleKlineCount || mFocusIndex == focusIdx || focusIdx >= mViewModel.getDataSize() ) {
			return;
		}
		mFocusIndex = focusIdx ;
//		double close = mViewModel.getClosePrice(index);
		invalidate();
		if (null != mKlineEvent) {
			mKlineEvent.onFocus(mFocusIndex + mStartIndex, mViewModel, this);
		}
	}

	private void doZoom(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		float newDist =  (float) Math.sqrt( x*x + y*y);
		float scale = newDist / oldDistance;
		 
		int barWiddth = (int) (oldKlineWidth*scale);
		int visibleKlineCount = mChartWidth / (barWiddth + 1);
		int dataSize = mViewModel.getDataSize();
		
		if (visibleKlineCount< mVisibleKlineCount) {
			if (barWiddth >= 51) {
				return;
			}
			int inval = mVisibleKlineCount - visibleKlineCount;
			mStartIndex += inval/2;
			mKlineBarWidth = barWiddth;
			invalidate();
		}else if(visibleKlineCount > mVisibleKlineCount  ){
			if (barWiddth < 5) {
				return;
			}
			mKlineBarWidth = barWiddth;
			if ( visibleKlineCount <= dataSize ) {
				int inval =  visibleKlineCount - mVisibleKlineCount;
				if (inval/2 <= mStartIndex) {
					mStartIndex -= inval/2;
					if ((mStartIndex+visibleKlineCount) > dataSize) {
						mStartIndex = dataSize - visibleKlineCount;
					}
				}else{
					mStartIndex = 0;
				}
			}
			invalidate();
		}
		
		
//		System.out.println("scale: "+scale);
	}

	private void doDrag(MotionEvent event) {
		
		float dist = event.getX() - mLastTouchX;
		int moveCount = (int) (dist/mKlineBarWidth);
//		System.out.println("dist : "+dist);
		if (moveCount>0) {
			if (mStartIndex == 0) {
				return;
			}
			if (moveCount > mStartIndex) {
				mStartIndex = 0;
			}else{
				mStartIndex -= moveCount;
			}
			mLastTouchX = event.getX();
			mStartIndex = Math.max(0, mStartIndex);
			invalidate();
			
		}else if (moveCount<0){
			int newIndex = mStartIndex - moveCount;
			if ((newIndex+mVisibleKlineCount) > mViewModel.getDataSize()) {
				mStartIndex = mViewModel.getDataSize() - mVisibleKlineCount ;
			}else{
				mStartIndex = newIndex;
			}
			mLastTouchX = event.getX();
			mStartIndex = Math.max(0, mStartIndex);
			invalidate();
		}
	}
	
	private final class CheckForTap implements Runnable {
        public void run() {
        	if (mIsActionUp) {
				return;
			}
            setPressed(true);
            checkForLongClick(0);
        }
    }
	
	CheckForLongPress mPendingCheckForLongPress;
	private void checkForLongClick(int delayOffset) {
        if ( mEnableTouch ) {
            if (mPendingCheckForLongPress == null) {
                mPendingCheckForLongPress = new CheckForLongPress();
            }
            postDelayed(mPendingCheckForLongPress,  ViewConfiguration.getLongPressTimeout() - delayOffset);
        }
    }
	class CheckForLongPress implements Runnable {

        public void run() {
        	System.out.println("CheckForLongPress:"+isPressed() +" , Mode: "+mTouchMode.toString());
            if ( isPressed() && EventType.NONE == mTouchMode ) {
            	mTouchMode = EventType.FOCUS_MOVE;
            	doMoveFocusPoint( startPoint.x);
            }
        }
    }
	
	
	public interface IKlineEvent{
		public void onUnFocus( KlineViewModel viewModel , QwKlineView klineView );
		public void onFocus( int focusIndex , KlineViewModel viewModel , QwKlineView klineView );
		public void onLeftDataChanged( int left , KlineViewModel viewModel , QwKlineView klineView );
	}
}
