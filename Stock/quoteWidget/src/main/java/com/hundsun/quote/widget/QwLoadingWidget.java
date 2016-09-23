package com.hundsun.quote.widget;

import com.hundsun.quotewidget.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 加载更多控件
 * @author 梁浩        2015-1-9-上午5:23:49
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
@SuppressLint("NewApi")
public class QwLoadingWidget extends LinearLayout {

	private TextView     mLoadingNoDataLable; //无更多数据
	private TextView     mLoadingRefreshClewLable;
	private LinearLayout mLoadingPbLL ;
	private QwLoadingOnClickListener loadingOnClickListener;

	public QwLoadingWidget(Context context) {
		super(context);
		init(context);
	}
	public QwLoadingWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public QwLoadingWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater localLayoutInflater = LayoutInflater.from(context);
		View localView = localLayoutInflater.inflate(R.layout.common_loading_request_widget, this);
		mLoadingNoDataLable = (TextView)localView.findViewById(R.id.RefreshRequestReturnClewTV);
		mLoadingRefreshClewLable = (TextView)localView.findViewById(R.id.RefreshClewTV);
		mLoadingPbLL  = (LinearLayout)localView.findViewById(R.id.RefreshLayout);
	}

	/**
	 * 加载更多开始
	 */
	public void loadingStart(){
		mLoadingNoDataLable.setVisibility(View.GONE);
		mLoadingPbLL.setVisibility(View.VISIBLE);
	}

	/**
	 * 判断视图是否正在刷新
	 */
	public boolean isShow(){
		if(mLoadingPbLL.getVisibility() == View.VISIBLE){
			return true;
		}
		return false;
	}

	/**
	 * 判断视图是否正在刷新
	 */
	public void closeRefreshView(){
		if(mLoadingPbLL.getVisibility() == View.VISIBLE){
			mLoadingPbLL.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 加载更多结束
	 */
	public void loadingFinish(){
		mLoadingNoDataLable.setVisibility(View.GONE);
		mLoadingPbLL.setVisibility(View.GONE);
	}

	/**
	 *影藏
	 */
	public void hide(){
		mLoadingNoDataLable.setVisibility(View.GONE);
		mLoadingPbLL.setVisibility(View.GONE);
	}
	/**
	 * 加载更多失败
	 */
	public void loadingFailure(){
		mLoadingNoDataLable.setVisibility(View.VISIBLE);
		mLoadingPbLL.setVisibility(View.GONE);
		mLoadingNoDataLable.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				loadingOnClickListener.loading();
			}});
	}

	/**
	 * 加载失败提示语句
	 */
	public void setLoadingFailureClew(String clew){
		mLoadingPbLL.setVisibility(View.GONE);
		mLoadingNoDataLable.setVisibility(View.VISIBLE);
		mLoadingNoDataLable.setText(clew);
		mLoadingNoDataLable.setTextColor(Color.RED);
		mLoadingNoDataLable.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				loadingOnClickListener.loading();
			}});
	}

	/**
	 * 修改提示信息
	 */
	public void modifyLoadingClewInfo(String clewInfo,int color){
		if(TextUtils.isEmpty(clewInfo)){
			return;
		}
		mLoadingRefreshClewLable.setVisibility(View.VISIBLE);
		mLoadingRefreshClewLable.setText(clewInfo.toString());
		if(color != 0){
			mLoadingRefreshClewLable.setTextColor(color);
		}
	}


	/**
	 * 加载更多单击监听
	 * @author 梁浩        2015-1-9-上午5:35:47
	 *
	 */
	public interface QwLoadingOnClickListener{
		void loading();
	}

	public QwLoadingOnClickListener getLoadingOnClickListener() {
		return loadingOnClickListener;
	}
	public void setLoadingOnClickListener(QwLoadingOnClickListener loadingOnClickListener) {
		this.loadingOnClickListener = loadingOnClickListener;
	}

}
