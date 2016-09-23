package com.hundsun.quote.widget;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.StockTrendItem;
import com.hundsun.quote.model.TradeTime;
import com.hundsun.quote.tools.ColorUtils;
import com.hundsun.quote.tools.FormatUtils;
import com.hundsun.quote.viewmodel.TrendViewModel;
import com.hundsun.quotewidget.R;

public class QwTrendView extends View{
	public static final Typeface REGULAR_TEXT_FONT = Typeface.create( Typeface.DEFAULT, Typeface.NORMAL);
	private static final int VERSION_CODES_HONEYCOMB = 11;
	private static final int LAYER_TYPE_SOFTWARE = 1;
	protected Handler mRepaintHandler;
	protected Paint mPaint ;
	protected DashPathEffect mDashPath;//绘制虚线实例句柄
	protected TrendViewModel mViewModel;
	protected Rect mTrendChartRect;
	protected Rect mVolumeChartRect;
	protected int mChartWidth;
	protected int mBorderWidth;
	protected int mLeftAxisWidth;
	protected int mRightAxisWidth;
	protected int mTotalCount;
	protected double mPriceIntervals;
	protected boolean  mIsDrawAxisInside ;//指示指标刻度是否是画在图形内部

	public QwTrendView( Context context ) {
		super(context);
		init(context);
	}
	
	public QwTrendView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public QwTrendView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public void setViewUseSoftwareLayerType(){
		if (Build.VERSION.SDK_INT >= VERSION_CODES_HONEYCOMB ) {
			try {
				Method method = getClass().getMethod("setLayerType", new Class[] { int.class, Paint.class });
				method.invoke( this, new Object[] { LAYER_TYPE_SOFTWARE, null });
			} catch (Throwable e) {
				Log.v("qii view", "not support setLayerType!");
			}
		}
	}
	
	void init(Context context){
		setViewUseSoftwareLayerType();
		mPaint = new Paint();
		mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.kline_text_size));
		mPaint.setTypeface(REGULAR_TEXT_FONT);
		mBorderWidth = 1;
		mTotalCount = 241;
		mIsDrawAxisInside = false;
		mVolumeChartRect = new Rect();
		mTrendChartRect = new Rect();
		mRepaintHandler = new Handler();
		mDashPath = new DashPathEffect(new float[] {  1, 2}, 0);
	}
	
	public boolean isDrawAxisInside() {
		return mIsDrawAxisInside;
	}

	public void setIsDrawAxisInside(boolean isDrawAxisInside) {
		this.mIsDrawAxisInside = isDrawAxisInside;
		if (mViewModel != null) {
			repaint();
		}
	}

	public void setTrendViewModel( TrendViewModel viewModel ){
		
		mViewModel = viewModel;
		
		if (mViewModel != null &&mViewModel.getTrendsCount() > 0) {
			
			List<TradeTime> times = mViewModel.getOpenCloseTime();
			int count = 0;
			for (TradeTime time : times) {
				int closeTime = time.getCloseTime();
				int openTime  = time.getOpenTime();
				count +=  (closeTime/100 - openTime/100) * 60;
				count +=  (closeTime%100 - openTime%100);
			}
			if (0 < count) {
				mTotalCount = count+1;
			}
		}
		
		repaint();
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		System.out.println("Trend onDraw START");
		super.onDraw(canvas);
		
		measureChart();
		drawBorder(canvas);
		if ( null == mViewModel || mViewModel.getTrendsCount() <= 0) {
			return;
		}
		double highPrice = mViewModel.getMaxPrice() ;
		double lowPrice = mViewModel.getMinPrice();
		double preClose = mViewModel.getStock().getPreClosePrice();
		mPriceIntervals = 2* Math.max( Math.abs( highPrice - preClose) , Math.abs( lowPrice - preClose) ) ;
		if (0 == mPriceIntervals) {
			mPriceIntervals = preClose * 0.02;
		}
		
		System.out.println("Trend onDraw 1");
		drawTrend( canvas );
		System.out.println("Trend onDraw 1");
		drawVolume(canvas);
		System.out.println("Trend onDraw END");
	}
	
	protected void measureChart(){
		int width = getWidth();
		int height= getHeight();
		
		
		if (width <= 0 || height <= 0 || mViewModel == null) {
			return;
		}
		
		if (!mIsDrawAxisInside) {
			mRightAxisWidth = Math.round( mPaint.measureText("+10.00%")+0.5f );
			Stock stock = mViewModel.getStock();
			String price = FormatUtils.formatPrice(stock, mViewModel.getMaxPrice());
			int priceTextWidth = Math.round( mPaint.measureText(price)+0.5f );
			
			String volume =  mViewModel.getMaxVolume()+"";
			int volumeTextWidth = Math.round( mPaint.measureText(volume)+0.5f );
			mLeftAxisWidth = Math.max(volumeTextWidth, priceTextWidth);
		}else {
			mRightAxisWidth = 0;
			mLeftAxisWidth = 0;
		}
		
		
		mChartWidth  = width - 6*mBorderWidth - mLeftAxisWidth - mRightAxisWidth;
		
		int fontHeight = Math.max(30, getTextHeight(mPaint));
		mTrendChartRect.left = mBorderWidth+mLeftAxisWidth;
		mTrendChartRect.right = width - mBorderWidth - mRightAxisWidth;
		mTrendChartRect.top = mBorderWidth ;
		mTrendChartRect.bottom = ( height - fontHeight )*8/11 ;
		
		mVolumeChartRect.top = height - ( height - fontHeight )*3/11;
		mVolumeChartRect.bottom = height - mBorderWidth;
		mVolumeChartRect.left = mBorderWidth+mLeftAxisWidth;
		mVolumeChartRect.right = width - mBorderWidth - mRightAxisWidth;
	}
	
	protected float getCorrectTrendY( double price ){
		if (price == 0 || mPriceIntervals == 0) {
			return 0;
		}
		int trendHeight =  mTrendChartRect.bottom - mTrendChartRect.top;
		double scale = trendHeight / mPriceIntervals;
		double preClose = mViewModel.getStock().getPreClosePrice();
		double highPrice = preClose + mPriceIntervals/2;
		float y = (float)((highPrice  - price )*scale);
		int padding =  3*mBorderWidth;
		
		while(( y - mTrendChartRect.top )< padding){
			y += mBorderWidth;
		}
		
		while (( mTrendChartRect.bottom - y  )< padding) {
			y -= mBorderWidth;
		}
		
//		if (( y - mTrendChartRect.top )<= padding) {
//			y += padding;
//		}else if (( mTrendChartRect.bottom - y  )<= padding) {
//			y -= padding;
//		}
		return y;
	}
	
	
	/**
	 * 绘制分时
	 * @param canvas
	 */
	private void drawTrend(Canvas canvas) {
		if (null == mViewModel) {
			return;
		}
		int trendCount = mViewModel.getTrendsCount();
		if (trendCount<= 0) {
			return;
		}
		System.out.println("Trend drawTrend trendCount:"+trendCount);
		double preClose = mViewModel.getStock().getPreClosePrice();
		double x = mChartWidth  / (double)mTotalCount ;
		
		StockTrendItem stockTrendItem = mViewModel.getTrendItem( 0 );
		
		mPaint.setStrokeWidth(getResources().getDimension(R.dimen.qii_trend_line_width ));
		Path path = new Path();
		mPaint.setAntiAlias(true);
		
		mPaint.setColor(ColorUtils.TREND_AVERAGE_LINE_COLOR);
		double wavg = stockTrendItem.getWavg();
		float firstY = getCorrectTrendY(  wavg  );
		float lastX = mTrendChartRect.left + 3*mBorderWidth ;
		path.moveTo( mTrendChartRect.left+3*mBorderWidth, firstY);
		
		for (int i = 1; i < trendCount; i++) {
			stockTrendItem = mViewModel.getTrendItem(i);
			wavg = stockTrendItem.getWavg();
			if (0 == wavg) {
				continue;
			}
			float y = getCorrectTrendY(  wavg  );
			lastX = (float) (x*i)+ mTrendChartRect.left+3*mBorderWidth;
			path.lineTo( lastX, y);
		}
		canvas.drawPath(path, mPaint);
		
		path.reset();
		stockTrendItem = mViewModel.getTrendItem( 0 );
		mPaint.setColor(ColorUtils.TREND_PRICE_LINE_COLOR);
		firstY = getCorrectTrendY(  stockTrendItem.getPrice() );
		path.moveTo( mTrendChartRect.left, firstY);
		double price = 0;
		for (int i = 1; i < trendCount; i++) {
			stockTrendItem = mViewModel.getTrendItem(i);
			price =   stockTrendItem.getPrice() ;
			if (0 == price) {
				continue;
			}
			
			float y = getCorrectTrendY( price );
			lastX = (float) (x*i) + mTrendChartRect.left+3*mBorderWidth;
			path.lineTo( lastX, y);
		}
		canvas.drawPath(path, mPaint);
		float trendHeight = getTrendChartHeight();
		//开始填充
		path.lineTo(lastX, trendHeight );
		path.lineTo(mTrendChartRect.left, trendHeight);
		path.lineTo(mTrendChartRect.left, firstY);
		mPaint.setColor(ColorUtils.TREND_PRICE_LINE_COLOR_BG);
		mPaint.setStyle(Style.FILL);
		canvas.drawPath( path,mPaint );
//		mPaint.setStyle(Style.STROKE);
		
		price = preClose + mPriceIntervals/2;
		String percentString = FormatUtils.formatPercent( mPriceIntervals/2/preClose);
		mPaint.setColor( ColorUtils.getColor( price , preClose ) );
		String priceString = FormatUtils.formatPrice( mViewModel.getStock(), price  );
		
		int fontHeight = getTextHeight( mPaint );
		int priceTextAxisX = mBorderWidth;
		int percentTextAxisX = mTrendChartRect.right - Math.round( mPaint.measureText("-"+percentString)+0.5f );
		if (!mIsDrawAxisInside) {
			priceTextAxisX = mTrendChartRect.left - (int) mPaint.measureText(priceString) - mBorderWidth;
			percentTextAxisX  = mTrendChartRect.right + mBorderWidth;
		}
		int percentTextAxisY = fontHeight  + 2;
		canvas.drawText( priceString , priceTextAxisX, percentTextAxisY, mPaint);
		canvas.drawText( percentString , percentTextAxisX , percentTextAxisY , mPaint);
		
		mPaint.setColor( ColorUtils.getColor( preClose , preClose ) );
		priceString = FormatUtils.formatPrice( mViewModel.getStock(),  preClose           );
		canvas.drawText( priceString, priceTextAxisX, trendHeight/2 + fontHeight/2+2, mPaint);
		canvas.drawText( "0.00%" , percentTextAxisX , trendHeight/2 + fontHeight/2 + 2 , mPaint);
		
		price = preClose - mPriceIntervals/2;
		mPaint.setColor( ColorUtils.getColor( price , preClose ) );
		priceString = FormatUtils.formatPrice( mViewModel.getStock(),  preClose - mPriceIntervals/2 );
		canvas.drawText( FormatUtils.formatPrice( mViewModel.getStock(),  preClose - mPriceIntervals/2 ), priceTextAxisX, trendHeight - 2, mPaint);
		canvas.drawText( "-"+percentString , percentTextAxisX , trendHeight - 2 , mPaint);
		
	}

	/**
	 * 绘制边框以及分割线
	 * @param canvas
	 */
	void drawBorder( Canvas canvas ){
		
		mPaint.setColor(ColorUtils.BORDER_COLOR);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mBorderWidth);
		mPaint.setAntiAlias(true);
//		Rect rect = new Rect(mTrendChartRect);
////		rect.bottom += 2;
//		rect.top -= 2;
		
		canvas.drawRect( mTrendChartRect , mPaint);
		canvas.drawRect( mVolumeChartRect , mPaint);
		
		
		//画均分虚线
		PathEffect oldPathEffect = mPaint.getPathEffect();
		mPaint.setPathEffect(mDashPath);
		
		int count = mTotalCount/30;
		float inval = (float) ( mChartWidth  / ( count*1.0) );
		for(int j=1;j<count;j++){
			float linex = j* inval + mTrendChartRect.left;
			canvas.drawLine( linex , mTrendChartRect.top, linex, mTrendChartRect.bottom , mPaint);
			canvas.drawLine( linex , mVolumeChartRect.top , linex, mVolumeChartRect.bottom , mPaint);
		}
		
		//还原线效果
		mPaint.setPathEffect(oldPathEffect);
		float y = (mTrendChartRect.bottom +mTrendChartRect.top)/2.0f;
		canvas.drawLine( mTrendChartRect.left , y , mTrendChartRect.right, y , mPaint);
		
		if (null == mViewModel) {
			return;
		}
		
		mPaint.setStrokeWidth(getResources().getDimension(R.dimen.qii_trend_line_width ));
		Style style = mPaint.getStyle();
		mPaint.setStyle(Style.FILL);
		int timeTextY = mTrendChartRect.bottom+20;
		List<TradeTime> times = mViewModel.getOpenCloseTime();
		String time = formatTime( times.get(0).getOpenTime());
		canvas.drawText( time, mTrendChartRect.left, timeTextY, mPaint);
		time = formatTime( times.get(times.size()-1).getCloseTime());
		canvas.drawText( time, mTrendChartRect.right-mPaint.measureText("16:00")-mBorderWidth, timeTextY, mPaint);
		mPaint.setStyle( style );
	}
	DecimalFormat DECIMALFORMAT_0 = new DecimalFormat("#00");
	String formatTime( int time){
		return DECIMALFORMAT_0.format( time/100) +":"+ DECIMALFORMAT_0.format( time%100);
	}
	
	void drawVolume(  Canvas canvas  ){
		if (null == mViewModel) {
			return;
		}
		int trendCount = mViewModel.getTrendsCount();
		if (trendCount<= 0) {
			return;
		}
		
		long maxVolume = mViewModel.getMaxVolume();
		float x = (float)mChartWidth / mTotalCount ;
		
		if (0 == maxVolume) {
			return;
		}
		
		mPaint.setColor(ColorUtils.TREND_AVERAGE_LINE_COLOR);
		mPaint.setStrokeWidth(1);
		
		StockTrendItem stockTrendItem = mViewModel.getTrendItem( 0 );
		float rate = (float) ((float)(mVolumeChartRect.bottom - mVolumeChartRect.top)/maxVolume);
		double preClose = mViewModel.getStock().getPreClosePrice();
		for( int i=0; i< trendCount ;i++ ) {
			float linex = (i)*x + mVolumeChartRect.left+3*mBorderWidth;
			stockTrendItem = mViewModel.getTrendItem(i);
			long volume = stockTrendItem.getVol();
			if (volume <= 0) {
//				Log.d(VIEW_LOG_TAG, "Trend Volume is : "+volume);
				continue;
			}
			float y = mVolumeChartRect.top+((maxVolume-volume)*rate);
			
			if (preClose > stockTrendItem.getPrice()) {
				mPaint.setColor(ColorUtils.COLOR_GREEN);
			} else {
				mPaint.setColor(ColorUtils.COLOR_RED);
			}
			preClose = stockTrendItem.getPrice();
			canvas.drawLine( linex , y, linex, mVolumeChartRect.bottom  , mPaint);
		}
		
//		mPaint.setTextSize(14);
		mPaint.setAntiAlias(true);
		float hand = mViewModel.getStocksPerHand();
		if (hand <= 0) {
			hand = 1;
		}
		if (mIsDrawAxisInside) {
			return;
		}
		String volumeString = FormatUtils.formatBigNumber( maxVolume/hand );
		float textWidth = mPaint.measureText(volumeString);
		canvas.drawText( volumeString, mVolumeChartRect.left - textWidth -4, mVolumeChartRect.top+getTextHeight( mPaint )+ 2, mPaint);
		textWidth = mPaint.measureText("0");
		canvas.drawText( "0", mVolumeChartRect.left - textWidth -4, mVolumeChartRect.bottom -2, mPaint);
	}
	
	private int getTextHeight(Paint paint){
		Rect bounds = new Rect();
		paint.getTextBounds("123", 0, 1, bounds );
		
		return Math.abs( bounds.bottom - bounds.top);
	}
	
	protected int getTrendChartHeight(){
		return mTrendChartRect.bottom - mTrendChartRect.top;
	}
	
	protected int getTrendChartWidth(){
		return mTrendChartRect.right - mTrendChartRect.left;
	}
	
	void repaint(){
		mRepaintHandler.post( new Runnable() {
			
			@Override
			public void run() {
				invalidate();
			}
		});
	}
}
