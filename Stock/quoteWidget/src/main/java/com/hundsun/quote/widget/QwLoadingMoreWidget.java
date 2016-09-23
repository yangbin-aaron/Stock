package com.hundsun.quote.widget;

import com.hundsun.quotewidget.R;

import android.annotation.SuppressLint;
import android.content.Context;
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
public class QwLoadingMoreWidget extends LinearLayout {

	private TextView     mLoadingMoreLable ;      //加载跟多数据
	private TextView     mLoadingMoreNoDataLable; //无更多数据
	private LinearLayout mLoadingMorePbLL ;
	private QwLoadingMoreOnClickListener loadingMoreOnClickListener;

	public QwLoadingMoreWidget(Context context) {
		super(context);
		init(context);
	}
	public QwLoadingMoreWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public QwLoadingMoreWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater localLayoutInflater = LayoutInflater.from(context);
		View localView = localLayoutInflater.inflate(R.layout.common_loading_more_bar, this);
		mLoadingMoreLable = (TextView)localView.findViewById(R.id.CommonLoadingMoreLable);
		mLoadingMoreNoDataLable = (TextView)localView.findViewById(R.id.CommonLoadingNoMoreData);
		mLoadingMorePbLL  = (LinearLayout)localView.findViewById(R.id.CommonLoadingMorePbLL);
		mLoadingMoreLable.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				loadingMoreOnClickListener.loadingMore();
			}});
	}

	/**
	 * 加载更多开始
	 */
	public void loadingMoreStart(){
		mLoadingMoreLable.setVisibility(View.GONE);
		mLoadingMoreNoDataLable.setVisibility(View.GONE);
		mLoadingMorePbLL.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载更多结束
	 */
	public void loadingMoreFinish(){
		mLoadingMoreLable.setVisibility(View.VISIBLE);
		mLoadingMoreNoDataLable.setVisibility(View.GONE);
		mLoadingMorePbLL.setVisibility(View.GONE);
	}
	
	/**
	 * 加载更多取消
	 */
	public void loadingMoreCancel(){
		mLoadingMoreLable.setVisibility(View.VISIBLE);
		mLoadingMoreNoDataLable.setVisibility(View.GONE);
		if(mLoadingMorePbLL.getVisibility() == View.VISIBLE){
			mLoadingMorePbLL.setVisibility(View.GONE);	
		}
	}

	/**
	 * 加载更多失败
	 */
	public void loadingMoreFailure(){
		mLoadingMoreNoDataLable.setVisibility(View.VISIBLE);
		mLoadingMoreLable.setVisibility(View.GONE);
		mLoadingMorePbLL.setVisibility(View.GONE);
	}
	
	/**
	 * 加载更多单击监听
	 * @author 梁浩        2015-1-9-上午5:35:47
	 *
	 */
	public interface QwLoadingMoreOnClickListener{
		void loadingMore();
	}

	public QwLoadingMoreOnClickListener getLoadingMoreOnClickListener() {
		return loadingMoreOnClickListener;
	}
	public void setLoadingMoreOnClickListener(QwLoadingMoreOnClickListener loadingMoreOnClickListener) {
		this.loadingMoreOnClickListener = loadingMoreOnClickListener;
	}

}
