package com.hundsun.quote.widget;

import com.hundsun.quote.tools.CommonTools;
import com.hundsun.quotewidget.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 加载更多控件
 * @author 梁浩        2015-1-9-上午5:23:49
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
@SuppressLint("NewApi")
public class QwLoadingRequestListViewWidget extends LinearLayout {
	/*请求刷新*/
	private LinearLayout        mLoadingRefreshLL;       
	/*加载请求提示*/
	private TextView            mLoadingRefreshReturnTV ;  
	/*请求刷新*/
	private ScrollView          mLoadingRequestScrollView;  
	/*请求刷新*/
	private ListView            mLoadingRequestListView;  
	/*加载更多数据*/
	private TextView            mLoadingMoreReturnTV;        
	private TextView            mLoadingMoreOnClickTV;        
	/*加载更多*/
	private LinearLayout        mLoadingMoreProgressBarLL ;

	private QwLoadingMoreOnClickListener     loadingMoreOnClickListener;
	private QwLoadingListItemClickListener   onItemClickListener;

	public QwLoadingRequestListViewWidget(Context context) {
		super(context);
		init(context);
	}
	public QwLoadingRequestListViewWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public QwLoadingRequestListViewWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater localLayoutInflater = LayoutInflater.from(context);
		View localView = localLayoutInflater.inflate(R.layout.common_loading_more_request_listview_widget, this);
		mLoadingRefreshLL         = (LinearLayout)localView.findViewById(R.id.RefreshLayout);
		mLoadingRefreshReturnTV   = (TextView)localView.findViewById(R.id.RefreshRequestReturnClewTV);
		mLoadingRequestScrollView = (ScrollView)localView.findViewById(R.id.RefreshScrollView);
		mLoadingRequestListView   = (ListView)localView.findViewById(R.id.RefreshListView);
		mLoadingMoreReturnTV      = (TextView)localView.findViewById(R.id.LoadingMoreReturnClewTV);
		mLoadingMoreOnClickTV     = (TextView)localView.findViewById(R.id.LoadingMoreOnClickTV);
		mLoadingMoreProgressBarLL = (LinearLayout)localView.findViewById(R.id.LoadingMoreProgressBarLayout);
		mLoadingMoreOnClickTV.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				loadingMoreOnClickListener.loadingMore();
			}});
		mLoadingRequestListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,long length) {
				onItemClickListener.onItemClickListener(view.getTag());
			}});
	}

	/**
	 * 请求开始
	 */
	public void loadingRequestStart(){
		mLoadingRefreshLL.setVisibility(View.VISIBLE); //显示加载进度
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.GONE);
	}

	/**
	 * 请求开始
	 */
	public void loadingRequestFinish(){
		mLoadingRefreshLL.setVisibility(View.GONE); //显示加载进度
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.VISIBLE);
		mLoadingRequestListView.setVisibility(View.VISIBLE);
		mLoadingMoreReturnTV.setVisibility(View.GONE);
		mLoadingMoreOnClickTV.setVisibility(View.VISIBLE);
		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
	}

	/**
	 * 请求结束-无数据返回
	 */
	public void loadingRequestDataRefailure(){
		mLoadingRefreshLL.setVisibility(View.GONE); //显示加载进度
		mLoadingRefreshReturnTV.setVisibility(View.VISIBLE);
		mLoadingRequestScrollView.setVisibility(View.GONE);
		mLoadingRequestListView.setVisibility(View.GONE);
		mLoadingMoreReturnTV.setVisibility(View.GONE);
		mLoadingMoreOnClickTV.setVisibility(View.GONE);
		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
	}

	/**
	 * 请求结束-返回异常
	 */
	public void loadingRequestDataRefailure(String error){
		mLoadingRefreshLL.setVisibility(View.GONE); //显示加载进度
		mLoadingRefreshReturnTV.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(error)){
			mLoadingRefreshReturnTV.setText(error);
		}
		mLoadingRequestScrollView.setVisibility(View.GONE);
		mLoadingRequestListView.setVisibility(View.GONE);
		mLoadingMoreReturnTV.setVisibility(View.GONE);
		mLoadingMoreOnClickTV.setVisibility(View.GONE);
		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
	}

	/**
	 * 加载更多开始
	 */
	public void loadingMoreStart(){
		mLoadingRefreshLL.setVisibility(View.GONE);
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.VISIBLE);
		mLoadingRequestListView.setVisibility(View.VISIBLE);

		mLoadingMoreReturnTV.setVisibility(View.GONE);
		mLoadingMoreOnClickTV.setVisibility(View.GONE);

		mLoadingMoreProgressBarLL.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载更多结束
	 */
	public void loadingMoreFinish(){
		mLoadingRefreshLL.setVisibility(View.GONE);
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.VISIBLE);
		mLoadingRequestListView.setVisibility(View.VISIBLE);

		mLoadingMoreReturnTV.setVisibility(View.GONE);
		mLoadingMoreOnClickTV.setVisibility(View.VISIBLE);

		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
	}

	/**
	 * 加载更多-关闭
	 */
	public void closeLoadingMoreWidget(){
		mLoadingRefreshLL.setVisibility(View.GONE);
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.VISIBLE);
		mLoadingRequestListView.setVisibility(View.VISIBLE);

		mLoadingMoreReturnTV.setVisibility(View.GONE);
		mLoadingMoreOnClickTV.setVisibility(View.GONE);

		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
	}


	/**
	 * 加载更多失败
	 */
	public void loadingMoreFailure(){
		mLoadingRefreshLL.setVisibility(View.GONE);
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.VISIBLE);
		mLoadingRequestListView.setVisibility(View.VISIBLE);

		mLoadingMoreReturnTV.setVisibility(View.VISIBLE);
		mLoadingMoreOnClickTV.setVisibility(View.GONE);

		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
	}


	/**
	 * 加载更多失败
	 */
	public void loadingMoreFailure(String error){
		mLoadingRefreshLL.setVisibility(View.GONE);
		mLoadingRefreshReturnTV.setVisibility(View.GONE);
		mLoadingRequestScrollView.setVisibility(View.VISIBLE);
		mLoadingRequestListView.setVisibility(View.VISIBLE);

		mLoadingMoreReturnTV.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(error)){
			mLoadingMoreReturnTV.setText(error);
		}
		mLoadingMoreOnClickTV.setVisibility(View.GONE);

		mLoadingMoreProgressBarLL.setVisibility(View.GONE);
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

	/**
	 * @return the onItemClickListener
	 */
	public QwLoadingListItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}
	/**
	 * @param onItemClickListener the onItemClickListener to set
	 */
	public void setOnItemClickListener(QwLoadingListItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}


	/**
	 * ListView 单击接口
	 * @author 梁浩        2015-1-13-下午2:32:41
	 *
	 */
	public interface QwLoadingListItemClickListener{
		void onItemClickListener(Object details);
	}

	/**
	 * 设置适配器
	 */
	public void setAdapter(BaseAdapter adapter) {
		this.mLoadingRequestListView.setAdapter(adapter);
		CommonTools.setListViewHeightBasedOnChildren(this.mLoadingRequestListView);

	}
}
