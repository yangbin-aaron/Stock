package com.hundsun.quote.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hundsun.quotewidget.R;


/**
 * 自定义 顶部、底部提示、刷新条
 * 用于 普通ListView | 自定义滑动列表 顶部状态改变及提示文字
 * @author 梁浩        2015-2-12-下午4:37:15
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwRefreshListHeadWidget extends FrameLayout {

	static  final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
	private final ImageView   mRefreshIv;
	private final ProgressBar mRefreshPb;
	private final TextView    mRefreshTv;
	private final Animation   mRotateAnimation, mResetRotateAnimation;
	private String            mPullLabel;
	private String            mRefreshingLabel;
	private String            mReleaseLabel;

	public QwRefreshListHeadWidget(Context context, final int mode, String releaseLabel, String pullLabel, String refreshingLabel) {
		super(context);
		ViewGroup refreshBar = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.common_list_refresh_header, this);
		mRefreshTv  = (TextView) refreshBar.findViewById(R.id.CommonListRefreshPullLable);
		mRefreshIv  = (ImageView) refreshBar.findViewById(R.id.CommonListRefreshImageView);
		mRefreshPb  = (ProgressBar) refreshBar.findViewById(R.id.CommonListRefreshProgressBar);
		
		final Interpolator interpolator = new LinearInterpolator();
		mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		mRotateAnimation.setInterpolator(interpolator);
		mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);
		
		mResetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
		mResetRotateAnimation.setInterpolator(interpolator);
		mResetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);
		
		this.mReleaseLabel    = releaseLabel;
		this.mPullLabel       = pullLabel;
		this.mRefreshingLabel = refreshingLabel;
		
		switch (mode) {
		case QwRefreshListBodyWidget.MODE_PULL_UP_TO_REFRESH:
		case QwRefreshListBodyWidget.MODE_PULL_DOWN_TO_REFRESH:
			mRefreshIv.setImageResource(R.drawable.common_list_refresh_header_arrow);
			break;
		default:
			mRefreshIv.setImageResource(R.drawable.common_list_refresh_header_arrow);
			break;
		}
	}

	/**
	 * 拖动提示语
	 * @param pullLabel
	 */
	public void setPullLabel(String pullLabel) {
		this.mPullLabel = pullLabel;
	}
	/**
	 * 正在刷新提示语
	 * @param refreshingLabel
	 */
	public void setRefreshingLabel(String refreshingLabel) {
		this.mRefreshingLabel = refreshingLabel;
	}

	/**
	 * 释放刷新提示语
	 * @param releaseLabel
	 */
	public void setReleaseLabel(String releaseLabel) {
		this.mReleaseLabel = releaseLabel;
	}

	/**
	 * 拖动（上下拉）去刷新
	 */
	public void pullToRefresh() {
		mRefreshTv.setText(mPullLabel);
		mRefreshIv.clearAnimation();
		mRefreshIv.startAnimation(mResetRotateAnimation);
	}

	/**
	 * 释放刷新<刷新完成后 >
	 */
	public void releaseToRefresh() {
		mRefreshTv.setText(mReleaseLabel);
		mRefreshIv.clearAnimation();
		mRefreshIv.startAnimation(mRotateAnimation);
	}

	/**
	 * 正在刷新
	 */
	public void refreshing() {
		mRefreshTv.setText(mRefreshingLabel);
		mRefreshIv.clearAnimation();
		mRefreshIv.setVisibility(View.INVISIBLE);
		mRefreshPb.setVisibility(View.VISIBLE);
	}
	public void setTextColor(int color) {
		mRefreshTv.setTextColor(color);
	}

	/**
	 * 重置控件
	 */
	public void reset() {
		mRefreshTv.setText(mPullLabel);
		mRefreshIv.setVisibility(View.VISIBLE);
		mRefreshPb.setVisibility(View.GONE);
	}
}
