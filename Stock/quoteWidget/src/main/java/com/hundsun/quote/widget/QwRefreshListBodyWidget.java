package com.hundsun.quote.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.hundsun.quotewidget.R;

/**
 * ListView 翻页通用控件
 * @author 梁浩        2015-2-13-下午1:51:03
 * @param <T>
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public abstract class QwRefreshListBodyWidget<T extends View> extends LinearLayout {

	static final float       FRICTION                   = 2.0f;
	/*翻页请求状态【拖动准备刷新、正在刷新中、刷新完成后释放】*/
	static final int         PULL_TO_REFRESH            = 0x0;
	static final int         RELEASE_TO_REFRESH         = 0x1;
	static final int         REFRESHING                 = 0x2;
	static final int         MANUAL_REFRESHING          = 0x3;
	private      int         mState                     = PULL_TO_REFRESH;
	/*翻页模式【向上、向下、既可以上也可以下】*/
	public static final int  MODE_PULL_DOWN_TO_REFRESH  = 0x1;
	public static final int  MODE_PULL_UP_TO_REFRESH    = 0x2;
	public static final int  MODE_BOTH                  = 0x3;
	private int     mMode                               = MODE_PULL_DOWN_TO_REFRESH; //初始化模式【配置模式】
	private int     mCurrentMode;              //当前模式

	/*是否可以拖动、上拉、下拉*/
	private boolean isPullToRefreshEnabled              = true;
	/*正在刷新时禁止滚动、拖动*/
	private boolean disableScrollingWhileRefreshing     = true;
	/*是否正在拖动*/
	private boolean isBeingDragged                      = false;

	/* 顶部条、滑动视图层、底部条*/
	private QwRefreshListHeadWidget mHeaderLayout;
	protected T                     mRefreshableView;
	private QwRefreshListHeadWidget mFooterLayout;
	/*控件整体垂直移动线程*/
	private SmoothScrollRunnable    currentSmoothScrollRunnable;
	/*刷新请求接口监听*/
	private OnRefreshListener 	    onRefreshListener;
	/*Handler句柄*/
	private final Handler           handler = new Handler();
	/*上下文*/
	private Context 				mContext;

	/*顶部、底部 刷新状态条高度*/
	private int     mHeaderHeight;
	/*当前页号*/
	private int     mCurrentPage ;
	/*滑动屏幕时最小移动间距*/
	private int     mTouchSlop;
	/*开始滑动时纵坐标点*/
	private float   initialMotionY;
	/*滑动结束横坐标值*/
	private float   lastMotionX;
	/*滑动结束纵坐标值*/
	private float   lastMotionY;

	public QwRefreshListBodyWidget(Context context) {
		super(context);
		init(context, null);
	}
	public QwRefreshListBodyWidget(Context context, int mode) {
		super(context);
		this.mMode = mode;
		init(context, null);
	}
	public QwRefreshListBodyWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@SuppressWarnings("deprecation")
	private void init(Context context, AttributeSet attrs) {
		mContext = context;
		String releaseLable    = "";
		String pullLable       = "";
		String refreshingLable = context.getString(R.string.LoadingData);

		setOrientation(LinearLayout.VERTICAL);
		mTouchSlop = ViewConfiguration.getTouchSlop();

		mRefreshableView = this.createRefreshableView(context, attrs);
		this.addRefreshableView(context, mRefreshableView);
		mMode = MODE_BOTH;
		if (mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH) {
			pullLable    = context.getString(R.string.PullDownTurnPage);
			releaseLable = context.getString(R.string.ReleaseDownTurnPage);
			mHeaderLayout = new QwRefreshListHeadWidget(context, MODE_PULL_DOWN_TO_REFRESH, releaseLable, pullLable,refreshingLable);
			addView(mHeaderLayout, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)); 
			measureView(mHeaderLayout);
			mHeaderHeight = mHeaderLayout.getMeasuredHeight();
		}
		if (mMode == MODE_PULL_UP_TO_REFRESH || mMode == MODE_BOTH) {
			pullLable = context.getString(R.string.PullUpTurnPage);
			releaseLable = context.getString(R.string.ReleaseUpTurnPage);
			mFooterLayout = new QwRefreshListHeadWidget(context, MODE_PULL_UP_TO_REFRESH, releaseLable, pullLable, refreshingLable);
			addView(mFooterLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(mFooterLayout);
			mHeaderHeight = mFooterLayout.getMeasuredHeight();
		}
		switch (mMode) {
		case MODE_BOTH:
			setPadding(0, -mHeaderHeight, 0, -mHeaderHeight);
			break;
		case MODE_PULL_UP_TO_REFRESH:
			setPadding(0, 0, 0, -mHeaderHeight);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			setPadding(0, -mHeaderHeight, 0, 0);
			break;
		}
		if (mMode != MODE_BOTH) {
			mCurrentMode = mMode;
		}
	}

	/**
	 * 重置刷新条
	 * @param c
	 */
	@SuppressWarnings("deprecation")
	public void resetScrollTableHeader(Context c) {
		mMode = MODE_BOTH;
		String releaseLable    = "";
		String pullLable       = "";
		String refreshingLable = c.getString(R.string.LoadingData);
		if (mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH) {
			pullLable    = c.getString(R.string.PullDownTurnPage);
			releaseLable = c.getString(R.string.ReleaseDownTurnPage);
			if(mHeaderLayout != null){
				this.removeView(mHeaderLayout);
			}
			mHeaderLayout = new QwRefreshListHeadWidget(c, MODE_PULL_DOWN_TO_REFRESH, releaseLable, pullLable,refreshingLable);
			addView(mHeaderLayout, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)); 
			measureView(mHeaderLayout);
			mHeaderHeight = mHeaderLayout.getMeasuredHeight();
		}
		if (mMode == MODE_PULL_UP_TO_REFRESH || mMode == MODE_BOTH) {
			pullLable = c.getString(R.string.PullUpTurnPage);
			releaseLable = c.getString(R.string.ReleaseUpTurnPage);
			if(mFooterLayout != null){
				this.removeView(mFooterLayout);
			}
			mFooterLayout = new QwRefreshListHeadWidget(c, MODE_PULL_UP_TO_REFRESH, releaseLable, pullLable, refreshingLable);
			addView(mFooterLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(mFooterLayout);
			mHeaderHeight = mFooterLayout.getMeasuredHeight();
		}
		switch (mMode) {
		case MODE_BOTH:
			setPadding(0, -mHeaderHeight, 0, -mHeaderHeight);
			break;
		case MODE_PULL_UP_TO_REFRESH:
			setPadding(0, 0, 0, -mHeaderHeight);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			setPadding(0, -mHeaderHeight, 0, 0);
			break;
		}
		if (mMode != MODE_BOTH) {
			mCurrentMode = mMode;
		}
	}

	/**
	 * 【构建视图抽象方法】。。。。。。This is implemented by derived classes to return the created View.
	 * 【支持自定义视图,需要实现 该方法】 。。。。。If you need to use a custom View (such as a custom ListView),override this method and return an instance of your custom class.
	 * 【在这种方法中一定要设置视图的ID,特别是如果你使用ListActivity或ListFragment。】。。。。。。Be sure to set the ID of the view in this method, especially if you're using a ListActivity or ListFragment.
	 *  @param context
	 *  @param attrs 【AttributeSet 来自封装类。意味着任何你包含在XML布局声明将被路由到创建视图】 AttributeSet from wrapped class. Means that anything you include in the XML layout declaration will be routed to the created View
	 *  @return New instance of the Refreshable View
	 */
	protected abstract T createRefreshableView(Context context, AttributeSet attrs);

	/**
	 * 构建刷新视图
	 * @param context
	 * @param refreshableView
	 */
	@SuppressWarnings("deprecation")
	protected void addRefreshableView(Context context, T refreshableView) {
		addView(refreshableView, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1.0f));
	}

	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {
		if (!isPullToRefreshEnabled) {
			return false;
		}
		if (isRefreshing() && disableScrollingWhileRefreshing) {
			return true;
		}
		final int action = event.getAction();
		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			isBeingDragged = false;
			return false;
		}
		if (action != MotionEvent.ACTION_DOWN && isBeingDragged) {
			return true;
		}
		switch (action) {
		case MotionEvent.ACTION_MOVE: {
			if (isReadyForPull()) {
				final float y  = event.getY();
				final float dy = y - lastMotionY;
				final float yDiff = Math.abs(dy);
				final float xDiff = Math.abs(event.getX() - lastMotionX);
				if (yDiff > mTouchSlop && yDiff > xDiff) {
					if ((mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH) && dy >= 0.0001f && isReadyForPullDown()) {
						lastMotionY = y;
						isBeingDragged = true;
						if (mMode == MODE_BOTH) {
							mCurrentMode = MODE_PULL_DOWN_TO_REFRESH;
						}
					} else if ((mMode == MODE_PULL_UP_TO_REFRESH || mMode == MODE_BOTH) && dy <= 0.0001f && isReadyForPullUp()) {
						lastMotionY = y;
						isBeingDragged = true;
						if (mMode == MODE_BOTH) {
							mCurrentMode = MODE_PULL_UP_TO_REFRESH;
						}
					}
				}
			}
			break;
		}
		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				lastMotionY = initialMotionY = event.getY();
				lastMotionX = event.getX();
				isBeingDragged = false;
			}
			break;
		}
		}
		return isBeingDragged;
	}

	/*
	 * 本控件覆盖系统方法：仅对纵向滑动其效果，不妨碍左右滑动
	 *  (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public final boolean onTouchEvent(MotionEvent event) {
		/*不可以被拖动情况*/
		if (!isPullToRefreshEnabled) {
			return false;
		}
		/*正在刷新中情况*/
		if (isRefreshing() && disableScrollingWhileRefreshing) {
			return true;
		}
		/*滑动边缘情况*/
		if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			/*边界判断*/
			if (isReadyForPull()) {
				lastMotionY = initialMotionY = event.getY();
				return true;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (isBeingDragged) {
				lastMotionY = event.getY();
				this.pullEvent();
				return true;
			}
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			if (isBeingDragged) {
				isBeingDragged = false;
				if (mState == RELEASE_TO_REFRESH && null != onRefreshListener) {
					setRefreshingInternal(true);
					onRefreshListener.onRefresh();
				}else{
					smoothScrollTo(0);
				}
				return true;
			}
			break;
		}
		}
		return false;
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 当前视图是否正在刷新 
	 * @return 如果正在刷新则返回true
	 */
	public final boolean isRefreshing() {
		return mState == REFRESHING || mState == MANUAL_REFRESHING;
	}

	private boolean isReadyForPull() {
		switch (mMode) {
		case MODE_PULL_DOWN_TO_REFRESH:
			return isReadyForPullDown();
		case MODE_PULL_UP_TO_REFRESH:
			return isReadyForPullUp();
		case MODE_BOTH:
			return isReadyForPullUp() || isReadyForPullDown();
		}
		return false;
	}

	/**
	 * 【由派生类去实现 -> 返回一个用户可以通过滚动下拉刷新的状态】Implemented by derived class to return whether the View is in a state where the user can Pull to Refresh by scrolling down.
	 *  @return 【返回当前视图的正确状态 :如到达了视图的顶部】true if the View is currently the correct state (for example, top of a ListView)
	 */
	protected abstract boolean isReadyForPullDown();

	/**
	 * 【由派生类去实现 -> 返回一个用户可以通过滚动上拉刷新的状态】Implemented by derived class to return whether the View is in a state where the user can Pull to Refresh by scrolling up.
	 *  @return 【返回当前视图的正确状态 :如到达了视图的底部】@return true if the View is currently in the correct state (for example,bottom of a ListView)
	 */
	protected abstract boolean isReadyForPullUp();

	/**
	 * 【拖动事件的动作】Actions a Pull Event
	 *  @return 【true 事件已经触发且发出，false 没有做改变】true if the Event has been handled, false if there has been no change
	 */
	private boolean pullEvent() {
		final int newHeight;
		final int oldHeight = this.getScrollY();
		switch (mCurrentMode) {
		case MODE_PULL_UP_TO_REFRESH:
			newHeight = Math.round(Math.max(initialMotionY - lastMotionY, 0) / FRICTION);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			newHeight = Math.round(Math.min(initialMotionY - lastMotionY, 0) / FRICTION);
			break;
		}
		//顶部条下移或底部条上移
		setHeaderScroll(newHeight);
		if (newHeight != 0) {
			if(mState == PULL_TO_REFRESH && mHeaderHeight < Math.abs(newHeight)) {
				mState = RELEASE_TO_REFRESH;
				switch(mCurrentMode) {
				case MODE_PULL_UP_TO_REFRESH:
					mFooterLayout.releaseToRefresh();
					break;
				case MODE_PULL_DOWN_TO_REFRESH:
					mHeaderLayout.releaseToRefresh();
					break;
				}
				return true;
			}else if (mState == RELEASE_TO_REFRESH && mHeaderHeight >= Math.abs(newHeight)) {
				mState = PULL_TO_REFRESH;
				switch (mCurrentMode) {
				case MODE_PULL_UP_TO_REFRESH:
					mFooterLayout.pullToRefresh();
					break;
				case MODE_PULL_DOWN_TO_REFRESH:
					mHeaderLayout.pullToRefresh();
					break;
				}
				return true;
			}
		}
		return oldHeight != newHeight;
	}

	/**
	 * 顶部、可刷新视图区域、底部视图条滚动
	 * @param y
	 */
	protected final void setHeaderScroll(int y) {
		scrollTo(0, y);
	}

	/**
	 * 正在刷新控件设置
	 * @param doScroll
	 */
	protected void setRefreshingInternal(boolean doScroll) {
		mState = REFRESHING;
		if (null != mHeaderLayout) {
			mHeaderLayout.refreshing();
		}
		if (null != mFooterLayout) {
			mFooterLayout.refreshing();
		}
		//控件整体移动
		if (doScroll) {
			smoothScrollTo(mCurrentMode == MODE_PULL_DOWN_TO_REFRESH ? -mHeaderHeight : mHeaderHeight);
		}
	}

	/**
	 * 控件整体纵向垂直滚动
	 * @param y 纵向滚动距离
	 */
	protected final void smoothScrollTo(int y) {
		if (null != currentSmoothScrollRunnable) {
			currentSmoothScrollRunnable.stop();
		}
		if (this.getScrollY() != y) {
			this.currentSmoothScrollRunnable = new SmoothScrollRunnable(handler, getScrollY(), y);
			handler.post(currentSmoothScrollRunnable);
		}
	}

	public int getCurrentPage() {
		return mCurrentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.mCurrentPage = currentPage;
	}

	/**
	 * Deprecated. Use {@link #getRefreshableView()} from now on.
	 * @deprecated
	 * @return The Refreshable View which is currently wrapped
	 */
	public final T getAdapterView() {
		return mRefreshableView;
	}

	/**
	 * Get the Wrapped Refreshable View. Anything returned here has already been、 added to the content view.
	 * @return The View which is currently wrapped
	 */
	public final T getRefreshableView() {
		return mRefreshableView;
	}

	/**
	 * 是否可以上下拖动刷新
	 * @return enabled
	 */
	public final boolean isPullToRefreshEnabled() {
		return isPullToRefreshEnabled;
	}

	/**
	 * 当视图刷新时，是否禁止滚动
	 * @param true if the widget has disabled scrolling while refreshing
	 */
	public final boolean isDisableScrollingWhileRefreshing() {
		return disableScrollingWhileRefreshing;
	}

	/**
	 * 当前刷新标记为完成。将重置UI和隐藏刷新视图
	 */
	public final void onRefreshComplete() {
		if (mState != PULL_TO_REFRESH) {
			resetHeader();
		}
	}

	/**
	 * 重置刷新状态头、和底
	 */
	protected void resetHeader() {
		mState = PULL_TO_REFRESH;
		isBeingDragged = false;
		if (null != mHeaderLayout) {
			mHeaderLayout.reset();
		}
		if (null != mFooterLayout) {
			mFooterLayout.reset();
		}
		smoothScrollTo(0);
	}

	/**
	 * 【设置当前视图是否可以下拉刷新】A mutator to enable/disable Pull-to-Refresh for the current View
	 * @param enable Whether Pull-To-Refresh should be used
	 */
	public final void setPullToRefreshEnabled(boolean enable) {
		this.isPullToRefreshEnabled = enable;
	}

	/**
	 * 【默认情况下、可刷新视图禁止刷新时滚动,通过当前方法改变禁止滚动行为】By default the Widget disabled scrolling on the Refreshable View while refreshing. This method can change this behaviour.
	 *  @param disableScrollingWhileRefreshing - 【结果为True则表示禁用滚动】true if you want to disable scrolling while refreshing
	 */
	public final void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
		this.disableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
	}

	/**
	 * 【刷新监听接口设置】Set OnRefreshListener for the Widget
	 * @param listener - Listener to be used when the Widget is set to Refresh
	 */
	public final void setOnRefreshListener(OnRefreshListener listener) {
		onRefreshListener = listener;
	}

	/**
	 * 【释放刷新显示文字 - 方法重构】Set Text to show when the Widget is being pulled, and will refresh when released
	 * **这里一定要将上下条的文字都要计算，否则切换状态时不能动态改变**
	 * @param releaseLabel- String to display
	 */
	public void setReleaseLabel(int mode,int mSplitePages) {
		String releaseUpLable   = "";
		String releaseDownLable = "";
		if(mode == MODE_PULL_UP_TO_REFRESH){
			releaseUpLable   = "当前第 "+mSplitePages+"页,上拉翻到第"+ (mSplitePages +1)+"页";
			releaseDownLable = "当前第 "+mSplitePages+"页,下拉翻到第"+ (mSplitePages -1)+"页";
			if(mSplitePages == 1){
				releaseUpLable = mContext.getString(R.string.ReleaseUpTurnPage);
			}
		}else if(mode == MODE_PULL_DOWN_TO_REFRESH){
			releaseDownLable = "当前第 "+mSplitePages+"页,下拉翻到第"+ (mSplitePages -1)+"页";
			releaseUpLable   = "当前第 "+mSplitePages+"页,上拉翻到第"+ (mSplitePages +1)+"页";
			if(mSplitePages == 1){
				releaseDownLable = mContext.getString(R.string.ReleaseDownTurnPage);
			} 
		}
		if (null != mHeaderLayout) {
			mHeaderLayout.setReleaseLabel(releaseDownLable);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setReleaseLabel(releaseUpLable);
		}
	}

	/**
	 * 【拖动刷新显示文字 - 方法重构】Set Text to show when the Widget is being Pulled
	 * **这里一定要将上下条的文字都要计算，否则切换状态时不能动态改变**
	 * @param pullLabel- String to display
	 */
	public void setPullLabel(int mode,int mSplitePages) {
		String pullUpLable = "";
		String pullDownLable = "";
		if(mode == MODE_PULL_UP_TO_REFRESH){
			pullUpLable   = "当前第 "+mSplitePages+"页,上拉翻页";
			pullDownLable = "当前第 "+mSplitePages+"页,下拉翻页";
			if(mSplitePages == 1){
				pullUpLable = mContext.getString(R.string.PullUpTurnPage);
			}

		}else if(mode == MODE_PULL_DOWN_TO_REFRESH){
			pullUpLable   = "当前第 "+mSplitePages+"页,上拉翻页";
			pullDownLable = "当前第 "+mSplitePages+"页,下拉翻页";
			if(mSplitePages == 1){
				pullDownLable = mContext.getString(R.string.PullDownTurnPage);
			}
		}
		if (null != mHeaderLayout) {
			mHeaderLayout.setPullLabel(pullDownLable);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setPullLabel(pullUpLable);
		}
	}

	/**
	 * 【刷新显示文字 - 方法重构】Set Text to show when the Widget is refreshing
	 * @param refreshingLabel - String to display
	 */
	public void setRefreshingLabel(String refreshingLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setRefreshingLabel(refreshingLabel);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setRefreshingLabel(refreshingLabel);
		}
	}

	/**
	 * 【释放刷新条显示文字 - 方法重构】Set Text to show when the Widget is being pulled, and will refresh when released
	 * @param releaseLabel- String to display
	 */
	public void setReleaseLabel(String releaseLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setReleaseLabel(releaseLabel);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setReleaseLabel(releaseLabel);
		}
	}

	/**
	 *【拖动刷新条显示文字 - 方法重构】 Set Text to show when the Widget is being Pulled
	 * @param pullLabel - String to display
	 */
	public void setPullLabel(String pullLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setPullLabel(pullLabel);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setPullLabel(pullLabel);
		}
	}

	/**
	 * Set Text to show when the Widget is being Pulled
	 * @param pullLabel - String to display
	 */
	public void setBottomLabel(String pullLabel) {
		if (null != mFooterLayout) {
			mFooterLayout.setVisibility(View.VISIBLE);
			mFooterLayout.setPullLabel(pullLabel);
		}
	}

	public final void setRefreshing() {
		this.setRefreshing(true);
	}

	/**
	 * Sets the Widget to be in the refresh state. The UI will be updated to show the 'Refreshing' view.
	 * @param doScroll - true if you want to force a scroll to the Refreshing view.
	 */
	public final void setRefreshing(boolean doScroll) {
		if (!isRefreshing()) {
			setRefreshingInternal(doScroll);
			mState = MANUAL_REFRESHING;
		}
	}

	public final boolean hasPullFromTop() {
		return mCurrentMode != MODE_PULL_UP_TO_REFRESH;
	}

	/**
	 * 获取当前滚动模式
	 * @return
	 */
	public final int getCurrentMode() {
		return mCurrentMode;
	}

	protected final QwRefreshListHeadWidget getFooterLayout() {
		return mFooterLayout;
	}

	protected final QwRefreshListHeadWidget getHeaderLayout() {
		return mHeaderLayout;
	}

	protected final int getHeaderHeight() {
		return mHeaderHeight;
	}

	protected final int getMode() {
		return mMode;
	}

	/**
	 * 滑动线程
	 * @author 梁浩        2015-2-13-下午5:05:37
	 *
	 */
	final class SmoothScrollRunnable implements Runnable {
		private final Handler      handler;
		private final Interpolator interpolator;
		static  final int  ANIMATION_DURATION_MS = 190;       //动画刷新毫秒时间
		static  final int  ANIMATION_FPS         = 1000 / 60; //每秒刷新次数
		private final int  scrollFromY;
		private final int  scrollToY;
		private int        currentY  = -1;
		private long       startTime = -1;
		private boolean    continueRunning = true;

		public SmoothScrollRunnable(Handler handler, int fromY, int toY) {
			this.handler     = handler;
			this.scrollFromY = fromY;
			this.scrollToY   = toY;
			this.interpolator = new AccelerateDecelerateInterpolator();
		}

		@Override
		public void run() {
			//仅当首次运行该线程或者计算y方向的偏移度 时设置起开始时间
			if (startTime == -1) {
				startTime = System.currentTimeMillis();
			}else{
				//尽量多用Long型避免过多使用Float计算;这里设定1秒，可以提供很好 的四舍五入和精度
				long normalizedTime = (1000 * (System.currentTimeMillis() - startTime)) / ANIMATION_DURATION_MS;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
				final int deltaY = Math.round((scrollFromY - scrollToY)* interpolator.getInterpolation(normalizedTime / 1000f));
				this.currentY = scrollFromY - deltaY;
				setHeaderScroll(currentY);
			}
			//【如果没有到达滚动目标位置则继续】If we're not at the target Y, keep going...
			if (continueRunning && scrollToY != currentY) {
				handler.postDelayed(this, ANIMATION_FPS);
			}
		}

		/**
		 * 停止该滚动线程
		 */
		public void stop() {
			this.continueRunning = false;
			this.handler.removeCallbacks(this);
		}
	};

	/**
	 * 刷新接口
	 * @author 梁浩        2015-2-13-下午1:40:07
	 *
	 */
	public static interface OnRefreshListener {
		public void onRefresh();

	}

	/**
	 * 底部监听接口
	 * @author 梁浩        2015-2-13-下午1:40:35
	 */
	public static interface OnLastItemVisibleListener {
		public void onLastItemVisible();

	}

	@Override
	public void setLongClickable(boolean longClickable) {
		getRefreshableView().setLongClickable(longClickable);
	}
	/**
	 * 技术点备注
	 * AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
	 * AccelerateInterpolator           在动画开始的地方速率改变比较慢，然后开始加速
	 * DecelerateInterpolator           在动画开始的地方快然后慢
	 * AnticipateInterpolator           开始的时候向后然后向前甩
	 * AnticipateOvershootInterpolator  开始的时候向后然后向前甩一定值后返回最后的值
	 * BounceInterpolator               动画结束的时候弹起
	 * CycleInterpolator                动画循环播放特定的次数，速率改变沿着正弦曲线
	 * LinearInterpolator               以常量速率改变
	 * OvershootInterpolator            向前甩一定值后再回到原来位置
	 * 如果android定义的interpolators不符合你的效果也可以自定义interpolators
	 */
}

