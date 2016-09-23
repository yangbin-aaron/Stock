package com.hundsun.quote.widget;


import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hundsun.quote.model.CommonStockRankTool;
import com.hundsun.quote.viewmodel.RankStockViewModel;
import com.hundsun.quote.widget.QwScrollTableListContentWidget.ScrollTableListItemOnClickListener;
import com.hundsun.quote.widget.QwScrollTableListHeadWidget.ScrollTableTitleSortOnClickListener;
import com.hundsun.quotewidget.R;

/**
 * 组合滑动头、刷新滑动体控件
 * @author 梁浩      
 * @date 2015-2-12-上午9:14:22
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwScrollTableListWidget extends QwScrollTableBaseRelativeLayout{

	/*滑动视图可排序表头*/
	private QwScrollTableListHeadWidget     mScrollTableListHeadWidget;
	/*滑动视图可左右上下移动表内容*/
	private QwScrollTableRefreshListWidget  mScrollTableListContentWidget;

	public QwScrollTableListWidget(Context context){
		super(context);
		init(context);
	}

	public QwScrollTableListWidget(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
		init(context);
	}

	public QwScrollTableListWidget(Context context, AttributeSet attributeSet, int paramInt){
		super(context, attributeSet, paramInt);
		init(context);
	}

	@SuppressWarnings("deprecation")
	protected void init(Context context){
		LayoutInflater localLayoutInflater = LayoutInflater.from(context);
		View localView = null;
		localView = localLayoutInflater.inflate(R.layout.qw_scroll_table_list_view_scrolls, this);
		mScrollTableListHeadWidget     = (QwScrollTableListHeadWidget) localView.findViewById(R.id.scroll_table_head_widget);
		mScrollTableListContentWidget =  (QwScrollTableRefreshListWidget) localView.findViewById(R.id.scroll_table_content_widget);
		//同步滑动控件设置
		final QwScrollTableHorizontalScrollView titleHsv   = mScrollTableListHeadWidget.mMoveableTitleHsv;
		final QwScrollTableHorizontalScrollView contentHsv = mScrollTableListContentWidget.getQwScrollTableHorizontalScrollView();
		if(titleHsv != null && contentHsv != null){
			titleHsv.setLinkageHorizontalScrollView(contentHsv);
			contentHsv.setLinkageHorizontalScrollView(titleHsv);
			titleHsv.setScrollTableSyncScrollListener(new QwScrollTableSyncScrollListener(){
				@Override
				public void onScrollChanged(QwScrollTableHorizontalScrollView scrollView, int x, int y,int oldx, int oldy) {
					if(scrollView == titleHsv) {
						contentHsv.scrollTo(x, y);
					} else if(scrollView == contentHsv) {
						titleHsv.scrollTo(x, y);
					}
				}} );
			contentHsv.setScrollTableSyncScrollListener(new QwScrollTableSyncScrollListener(){
				@Override
				public void onScrollChanged(QwScrollTableHorizontalScrollView scrollView, int x, int y,int oldx, int oldy) {
					if(scrollView == titleHsv) {
						contentHsv.scrollTo(x, y);
					} else if(scrollView == contentHsv) {
						titleHsv.scrollTo(x, y);
					}
				}} );
		}
	}

	/**
	 * 设置滑动表格控件标题
	 * @param titles
	 */
	public void setScrollTableTitle(List<CommonStockRankTool> sortColumnHeaders){
		if(mScrollTableListHeadWidget != null){
			mScrollTableListHeadWidget.setScrollTableTitle(sortColumnHeaders);	
		}
	}

	/**
	 * 构建双向滑动行情列表内容
	 * @param rankStockModels:双向滑动行情列表内容模型
	 * @param rankTableHeaders :双向滑动行情列表表头
	 */
	@SuppressWarnings("deprecation")
	public void setScrollTableContent(List<RankStockViewModel> rankStockModels,List<CommonStockRankTool> rankTableHeaders){
		if(mScrollTableListContentWidget != null){
			mScrollTableListContentWidget.setScrollTableContent(rankStockModels,rankTableHeaders);
		}
	}

	/**
	 * 排序监听接口
	 * @param scrollTableTitleSortOnClickListener
	 */
	public void setScrollTableTitleSortOnClickListener(ScrollTableTitleSortOnClickListener scrollTableTitleSortOnClickListener) {
		if(mScrollTableListHeadWidget != null){
			mScrollTableListHeadWidget.mScrollTableTitleSortOnClickListener = scrollTableTitleSortOnClickListener;
		}	
	}

	/**
	 * 滑动列表项单击接口设置
	 * @param scrollTableTitleSortOnClickListener
	 */
	public void setScrollTableListItemOnClickListener(ScrollTableListItemOnClickListener scrollTableTitleSortOnClickListener) {
		if(mScrollTableListContentWidget != null){
			mScrollTableListContentWidget.setScrollTableListItemOnClickListener(scrollTableTitleSortOnClickListener);
		}
	}

	/**
	 *清空刷新内容视图 
	 */
	public void clearScrollTableContentAllViews(){
		if(mScrollTableListContentWidget != null){
			mScrollTableListContentWidget.clearScrollTableContentAllViews();
		}
	}
	/**
	 *清空刷新内容视图 
	 */
	public void clearScrollTableContentAllViews(Context c){
		if(mScrollTableListContentWidget != null){
			mScrollTableListContentWidget.clearScrollTableContentAllViews(c);
		}
	}
	
	/**
	 *清空刷新内容视图 
	 */
	public void clearScrollTableHeader(Context c){
		if(mScrollTableListContentWidget != null){
			mScrollTableListContentWidget.clearScrollTableHeader(c);
		}
	}
	
	/**
	 * 获取刷新视图
	 */
	public QwScrollTableRefreshListWidget getScrollTableRefreshView(){
		if(mScrollTableListContentWidget != null){
			return mScrollTableListContentWidget;
		}
		return null;
	}

	/**
	 * 重置排序
	 */
	public void resetSortInfo() {
		if(mScrollTableListHeadWidget != null){
			mScrollTableListHeadWidget.resetSortInfo();
		}	
	}

	/**
	 * 重置排序状态
	 */
	public void resetSortStatus() {
		if(mScrollTableListHeadWidget != null){
			mScrollTableListHeadWidget.resetSortStatus();
		}	
	}
	
	/**
	 * 是否支持翻页
	 * @param isTurnPage : true 可以翻页；false 禁止翻页
	 */
	public void setTurnPage(boolean isTurnPage) {
		if(mScrollTableListContentWidget != null){
			mScrollTableListContentWidget.setTurnPage(isTurnPage);
		}
	}

	
}