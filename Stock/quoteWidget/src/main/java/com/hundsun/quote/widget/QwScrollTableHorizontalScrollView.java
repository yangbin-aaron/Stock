package com.hundsun.quote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * @author 梁浩        2015-2-28-下午4:07:10
  *@copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwScrollTableHorizontalScrollView extends HorizontalScrollView{
	
	private QwScrollTableSyncScrollListener mScrollTableSyncScrollListener = null; //同步滚动监听接口
	
	private QwScrollTableHorizontalScrollView mHsv;
	private boolean mTouchAble = true;

	public QwScrollTableHorizontalScrollView(Context context){
		super(context);
	}

	public QwScrollTableHorizontalScrollView(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
	}

	public QwScrollTableHorizontalScrollView(Context context, AttributeSet attributeSet, int paramInt){
		super(context, attributeSet, paramInt);
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(mScrollTableSyncScrollListener != null) {
        	mScrollTableSyncScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
		if (!this.mTouchAble){
			return;
		}else{
			if (this.mHsv != null){
				this.mHsv.scrollTo(x, y);
			}
		}
	}

	public void setLinkageHorizontalScrollView(QwScrollTableHorizontalScrollView hsv){
		this.mHsv = hsv;
	}

	public void setTouchAble(boolean touchable){
		this.mTouchAble = touchable;
		setFocusable(false);
		setFocusableInTouchMode(false);
	}
	
    public QwScrollTableSyncScrollListener getScrollTableSyncScrollListener() {
		return mScrollTableSyncScrollListener;
	}

	public void setScrollTableSyncScrollListener(QwScrollTableSyncScrollListener scrollTableSyncScrollListener) {
		this.mScrollTableSyncScrollListener = scrollTableSyncScrollListener;
	}

}