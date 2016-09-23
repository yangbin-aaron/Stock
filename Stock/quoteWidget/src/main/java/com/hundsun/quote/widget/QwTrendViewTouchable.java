package com.hundsun.quote.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hundsun.quote.model.StockTrendItem;
import com.hundsun.quote.tools.ColorUtils;
import com.hundsun.quote.viewmodel.TrendViewModel;

public class QwTrendViewTouchable extends QwTrendView {
	private ITrendEvent mTrendEvent;

	public QwTrendViewTouchable(Context context) {
		super(context );
	}
	
	public QwTrendViewTouchable(Context context, AttributeSet attrs ) {
		super(context, attrs);
	}
	
	public QwTrendViewTouchable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawFocusLine(canvas);
	}



	private void drawFocusLine(Canvas canvas) {
		if (!mIsShowFocusInfo) {
			return ;
		}
		StockTrendItem item = mViewModel.getTrendItem(mFocueIndex);
		if (null != item) {
			float y = getCorrectTrendY( item.getPrice() );
			float x = (float) (mChartWidth / (double)mTotalCount*mFocueIndex) + mTrendChartRect.left+3*mBorderWidth;
			
			mPaint.setColor(ColorUtils.FOCUS_LINE_COLOR);
			canvas.drawLine( mTrendChartRect.left , y, mTrendChartRect.right, y, mPaint);
			canvas.drawLine( x , mTrendChartRect.top , x, mTrendChartRect.bottom, mPaint);
			
			canvas.drawLine( x , mVolumeChartRect.top, x, mVolumeChartRect.bottom, mPaint);
			
			canvas.drawCircle(x, y, 2, mPaint);
		}
	}
	
	private void onMove(MotionEvent event) {
		float x = event.getX()  - mTrendChartRect.left;
		float y = event.getY();
		
		if ( 0 <= y && y <= getTrendChartHeight() ) {
			if (mBorderWidth >= x) {
				x = mBorderWidth;
			}else if (x >= (mChartWidth  +mBorderWidth)) {
				x = mChartWidth;
			}
			mFocueIndex = (int) (x*mTotalCount/mChartWidth);
			if (mFocueIndex >= mViewModel.getTrendsCount()) {
				mFocueIndex = mViewModel.getTrendsCount() -1;
			}
			repaint();
			if (null != mTrendEvent) {
				mTrendEvent.onFocus(mFocueIndex , mViewModel, this);
			}
		}
//		if ( 0 <= y && y <= getTrendChartHeight() && mBorderWidth <= x && x <= (mChartWidth  +mBorderWidth) ) {
//			mFocueIndex = (int) (x*mTotalCount/mChartWidth);
//			repaint();
//			if (null != mTrendEvent) {
//				mTrendEvent.onFocus(mFocueIndex , mViewModel, this);
//			}
//		}
	}
	
	private boolean onDown(MotionEvent event){
		float x = event.getX() - mTrendChartRect.left;
    	float y = event.getY();
    	float trendHeight = mTrendChartRect.bottom - mTrendChartRect.top;
    	
    	if ( 0 <= y && y <= trendHeight && mBorderWidth <= x && x <= (mChartWidth  +mBorderWidth) ) {
			mIsShowFocusInfo = true;
			mFocueIndex = (int) (x*mTotalCount/mChartWidth);
			if (mFocueIndex >= mViewModel.getTrendsCount()) {
				mFocueIndex = mViewModel.getTrendsCount() -1;
			}else if (mFocueIndex < 0) {
				mFocueIndex = 0;
			}
			repaint();
			if (null != mTrendEvent) {
				mTrendEvent.onFocus(mFocueIndex , mViewModel, this);
			}
		}else{
			mFocueIndex = -1;
			mIsShowFocusInfo = false;
		}
    	
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction() & MotionEvent.ACTION_MASK){   
        //单手指按下   
        case MotionEvent.ACTION_DOWN:
        	return onDown(event);
        case MotionEvent.ACTION_MOVE:
        	onMove( event );
        	break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_DOWN:
        	onUp();
        	break;
		}
		return true;
	}


	private void onUp() {
		mIsShowFocusInfo = false;
		mFocueIndex = -1;
		if (null != mTrendEvent) {
			mTrendEvent.onUnFocus(mViewModel, this);
		}
		repaint();
	}

	int mFocueIndex;
	protected boolean mIsShowFocusInfo;
	
	void init(Context context){
		super.init(context);
	}
	
	public ITrendEvent getTrendEvent() {
		return mTrendEvent;
	}

	public void setTrendEvent(ITrendEvent trendEvent) {
		this.mTrendEvent = trendEvent;
	}

	public interface ITrendEvent{
		public void onUnFocus( TrendViewModel  viewModel , QwTrendViewTouchable trendView );
		public void onFocus( int focusIndex , TrendViewModel  viewModel , QwTrendViewTouchable trendView );
	}
}
