package com.hundsun.quote.widget;

import com.hundsun.quote.widget.QwScrollTableListContentWidget.OnScrollToBoundaryListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 滑动控件基类 - 用于左右、上下滑动
 * @author 梁浩      
 * @date 2015-2-16-上午1:02:08
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 * @param <T> 可刷新视图
 */
public abstract class QwScrollTableRefreshListBaseWidget<T extends QwScrollTableListContentWidget> extends QwRefreshListBodyWidget<T>{

	/*空视图容器*/
	private FrameLayout  mRefreshableViewHolder;
	/*适配器使用空视图*/
	private View         mEmptyView;
	/*是否滚动到了顶部*/
	private boolean      isScrollTopBoundary = false;
	/*是否滚动到了底部*/
	private boolean      isScrollBottomBoundary = false;
	/*双向滑动列表控件是否支持翻页:默认不支持*/
	public  boolean      isTurnPage = false;

	/**
	 * 滚动视图监听
	 */
	private OnScrollToBoundaryListener onScrollToBoundaryListener = new OnScrollToBoundaryListener(){
		@Override
		public void isScroolToTopBoundary() {
			isScrollTopBoundary = true;
		}
		@Override
		public void isScroolBottomTopBoundary() {
			isScrollBottomBoundary = true;
		}
		@Override
		public void isNotScroolToBoundary() {
			isScrollTopBoundary    = false;
			isScrollBottomBoundary = false;
		}
	};

	public QwScrollTableRefreshListBaseWidget(Context context) {
		super(context);
		mRefreshableView.setOnScrollToBoundaryListener(onScrollToBoundaryListener);
	}

	public QwScrollTableRefreshListBaseWidget(Context context,AttributeSet attrs) {
		super(context, attrs);
		mRefreshableView.setOnScrollToBoundaryListener(onScrollToBoundaryListener);
	}

	public QwScrollTableRefreshListBaseWidget(Context context, int mode) {
		super(context, mode);
		mRefreshableView.setOnScrollToBoundaryListener(onScrollToBoundaryListener);
	}

	abstract public ContextMenuInfo getContextMenuInfo();

	/**
	 * 构建主视图 - 覆盖基类方法
	 * @param context
	 * @param mRefreshableView
	 */
	@SuppressWarnings("deprecation")
	protected void addRefreshableView(Context context, T mRefreshableView) {
		mRefreshableViewHolder = new FrameLayout(context);
		mRefreshableViewHolder.addView(mRefreshableView, ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
		addView(mRefreshableViewHolder, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1.0f));
	}

	/**
	 * 【给适配器设置空视图】Sets the Empty View to be used by the Adapter View.
	 * 【在下拉刷新时显示空视图】We need it handle it ourselves so that we can Pull-to-Refresh when the Empty View is shown.
	 * 【通常情况下你不需要调用，适配器会自动帮你调用】Please note, you do <strong>not</strong> usually need to call this method yourself. Calling setEmptyView on the AdapterView will automatically call
	 * 【当Android框架自动设置基于Id的空视图时，这种设置方法很好】 this method and set everything up. This includes when the Android Framework automatically sets the Empty View based on it's ID.
	 *  @param newEmptyView - Empty View to be used
	 */
	@SuppressWarnings("deprecation")
	public void setEmptyView(View newEmptyView) {
		// 【如果当前空视图存在则删除它】If we already have an Empty View, remove it
		if (null != mEmptyView) {
			mRefreshableViewHolder.removeView(mEmptyView);
		}
		if (null != newEmptyView) {
			ViewParent newEmptyViewParent = newEmptyView.getParent();
			if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
				((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
			}
			this.mRefreshableViewHolder.addView(newEmptyView, ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
		}
		if (mRefreshableView instanceof QwRefreshListEmptyWidget) {
			((QwRefreshListEmptyWidget) mRefreshableView).setEmptyViewInternal(newEmptyView);
		} else {
			((QwRefreshListEmptyWidget) this.mRefreshableView).setEmptyView(newEmptyView);
		}
	}

	/* 
	 * 顶部边界判断
	 * (non-Javadoc)
	 * @see com.hundsun.quote.widget.QwRefreshListBodyWidget#isReadyForPullDown()
	 */
	@Override
	protected boolean isReadyForPullDown() {
		return isScrollTopBoundary && isTurnPage;
	}

	/* 
	 * 底部边界判断
	 * (non-Javadoc)
	 * @see com.hundsun.quote.widget.QwRefreshListBodyWidget#isReadyForPullUp()
	 */
	@Override
	protected boolean isReadyForPullUp() {
		return isScrollBottomBoundary && isTurnPage;
	}

	/**
	 * 设置滚动监听
	 * @param onScrollToBoundaryListener
	 */
	public void setOnScrollToBoundaryListener(OnScrollToBoundaryListener onScrollToBoundaryListener) {
		this.onScrollToBoundaryListener = onScrollToBoundaryListener;
	}
}
