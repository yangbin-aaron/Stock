package com.hundsun.quote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * @author 梁浩        
 * @date 2015-2-28-下午4:05:28
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 * @param <T>
 */
public abstract class QwGeneralRefreshListBaseWidget<T extends AbsListView> extends QwRefreshListBodyWidget<T> implements OnScrollListener {
	/*最后一个视图序号*/
	private int                lastSavedFirstVisibleItem = -1;
	/*空视图容器*/
	private FrameLayout        mRefreshableViewHolder;
	/*适配器使用空视图*/
	private View               mEmptyView;
	private ImageView          mTopImageView;
	/*滚动监听*/
	private OnScrollListener   onScrollListener;
	/*最后一个视图监听*/
	private OnLastItemVisibleListener onLastItemVisibleListener;

	public QwGeneralRefreshListBaseWidget(Context context) {
		super(context);
		mRefreshableView.setOnScrollListener(this);
	}

	public QwGeneralRefreshListBaseWidget(Context context, int mode) {
		super(context, mode);
		mRefreshableView.setOnScrollListener(this);
	}

	public QwGeneralRefreshListBaseWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRefreshableView.setOnScrollListener(this);
	}

	abstract public ContextMenuInfo getContextMenuInfo();
	
	/**
	 * 构建主视图 - 覆盖基类方法
	 * @param context
	 * @param mRefreshableView
	 */
	protected void addmRefreshableView(Context context, T mRefreshableView) {
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
	public final void setEmptyView(View newEmptyView) {
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
			this.mRefreshableView.setEmptyView(newEmptyView);
		}
	}

	/* 
	 * 顶部边界判断 - 用于下拉时对可见视图的顶部做出判断
	 * (non-Javadoc)
	 * @see com.hundsun.quote.widget.QwRefreshListBodyWidget#isReadyForPullDown()
	 */
	protected boolean isReadyForPullDown() {
		return isFirstItemVisible();
	}

	/* 底部边界判断 - 用于下拉时对可见视图的底部做出判断
	 * (non-Javadoc)
	 * @see com.hundsun.quote.widget.QwRefreshListBodyWidget#isReadyForPullUp()
	 */
	protected boolean isReadyForPullUp() {
		return isLastItemVisible();
	}
	
	/*
	 * 顶部边界计算
	 * @return
	 */
	private boolean isFirstItemVisible() {
		if (this.mRefreshableView.getCount() == 0) {
			return true;
		}else if (mRefreshableView.getFirstVisiblePosition() == 0) {
			final View firstVisibleChild = mRefreshableView.getChildAt(0);
			if (firstVisibleChild != null) {
				return firstVisibleChild.getTop() >= mRefreshableView.getTop();
			}
		}
		return false;
	}
	/**
	 * 底部边界计算
	 * @return
	 */
	private boolean isLastItemVisible() {
		final int count = this.mRefreshableView.getCount();
		final int lastVisiblePosition = mRefreshableView.getLastVisiblePosition();
		if (count == 0) {
			return true;
		} else if (lastVisiblePosition == count - 1) {
			final int childIndex = lastVisiblePosition - mRefreshableView.getFirstVisiblePosition();
			final View lastVisibleChild = mRefreshableView.getChildAt(childIndex);
			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= mRefreshableView.getBottom();
			}
		}
		return false;
	}

	/*
	 * 滚动 事件
	 *  (non-Javadoc)
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
	 */
	public final void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,final int totalItemCount) {
		if (null != onLastItemVisibleListener) {
			// detect if last item is visible
			if (visibleItemCount > 0 && (firstVisibleItem + visibleItemCount == totalItemCount)) {
				// only process first event
				if (firstVisibleItem != lastSavedFirstVisibleItem) {
					lastSavedFirstVisibleItem = firstVisibleItem;
					onLastItemVisibleListener.onLastItemVisible();
				}
			}
		}
		if (null != onScrollListener) {
			onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android.widget.AbsListView, int)
	 */
	public final void onScrollStateChanged(final AbsListView view, final int scrollState) {
		if (null != onScrollListener) {
			onScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
	
	/**
	 * 将光标移到顶部元素
	 * @param mTopImageView
	 */
	public void setBackToTopView(ImageView mTopImageView){
		this.mTopImageView = mTopImageView;
		mTopImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mRefreshableView instanceof ListView ) {
					((ListView)mRefreshableView).setSelection(0);
				}else if(mRefreshableView instanceof GridView){
					((GridView)mRefreshableView).setSelection(0);
				}
			}
		});
	}

	/**
	 * 列表尾元素监听器
	 * @param listener
	 */
	public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
		onLastItemVisibleListener = listener;
	}

	/**
	 * 设置列表尾元素监听器
	 * @param listener
	 */
	public final void setOnScrollListener(OnScrollListener listener) {
		onScrollListener = listener;
	}
}
