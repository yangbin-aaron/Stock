package com.hundsun.quote.widget;

import java.util.List;

import com.hundsun.quote.model.CommonStockRankTool;
import com.hundsun.quote.viewmodel.RankStockViewModel;
import com.hundsun.quote.widget.QwScrollTableListContentWidget.ScrollTableListItemOnClickListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

/**
 * 滑动刷新控件
 * @author 梁浩      
 * @date 2015-2-16-上午1:00:56
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 * @param <QwScrollTableListContentWidget> 可刷新视图控件
 */

public class QwScrollTableRefreshListWidget extends QwScrollTableRefreshListBaseWidget<QwScrollTableListContentWidget> {
	/*刷新控件*/
	private QwScrollTableListContentWidget    mQwScrollTableListContentWidget;
	/*横向移动控件*/
	private QwScrollTableHorizontalScrollView mMoveableContentHsv ;

	public QwScrollTableRefreshListWidget(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public QwScrollTableRefreshListWidget(Context context, int mode) {
		super(context, mode);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public QwScrollTableRefreshListWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	/**
	 * 内部成员类 - 提供空视图设置功能
	 * @author 梁浩        2015-2-16-上午12:59:47
	 */
	class InternalScrollTableListView extends QwScrollTableListContentWidget implements QwRefreshListEmptyWidget {
		public InternalScrollTableListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			QwScrollTableRefreshListWidget.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyView(View emptyView) {
			super.setEmptyView(emptyView);
		}
	}

	@Override
	protected final QwScrollTableListContentWidget createRefreshableView(Context context, AttributeSet attrs) {
		QwScrollTableListContentWidget scrollTableLv = new InternalScrollTableListView(context, attrs);
		mQwScrollTableListContentWidget = scrollTableLv;
		if(mQwScrollTableListContentWidget != null){
			mMoveableContentHsv = mQwScrollTableListContentWidget.mMoveableContentHsv;
		}
		//scrollTableLv.setId(R.id.widget_refresh_list_scroll_table);
		return scrollTableLv;
	}

	public void setEmptyView(View emptyView) {

	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return null;
	}

	/**
	 * 获取滑动内容控件[左右水平方式滚动]
	 * @return
	 */
	public QwScrollTableHorizontalScrollView getQwScrollTableHorizontalScrollView() {
		return mMoveableContentHsv;
	}

	/**
	 * 构建双向滑动行情列表内容
	 * @param rankStockModels:双向滑动行情列表内容模型
	 * @param rankTableHeaders :双向滑动行情列表表头
	 */
	public void setScrollTableContent(List<RankStockViewModel> rankStockModels,List<CommonStockRankTool> rankTableHeaders) {
		if(mQwScrollTableListContentWidget != null){
			mQwScrollTableListContentWidget.setScrollTableContent(rankStockModels, rankTableHeaders);
		}
	}

	/**
	 *清空刷新内容视图 
	 */
	public void clearScrollTableContentAllViews(){
		if(mQwScrollTableListContentWidget != null){
			mQwScrollTableListContentWidget.clearScrollTableContentAllViews();
		}
	}

	/**
	 *清空刷新内容视图 
	 */
	public void clearScrollTableContentAllViews(Context c){
		if(mQwScrollTableListContentWidget != null){
			mQwScrollTableListContentWidget.clearScrollTableContentAllViews();
		}
		if(this.isTurnPage != false){
			this.resetScrollTableHeader(c);
		}
	}

	/**
	 * 重置滑动控件刷新条
	 * @param c
	 */
	public void clearScrollTableHeader(Context c) {
		if(this.isTurnPage != false){
			this.resetScrollTableHeader(c);
		}
	}

	/**
	 * 列表项单击接口
	 * @param scrollTableTitleSortOnClickListener
	 */
	public void setScrollTableListItemOnClickListener(ScrollTableListItemOnClickListener scrollTableTitleSortOnClickListener) {
		if(mQwScrollTableListContentWidget != null){
			mQwScrollTableListContentWidget.setScrollTableListItemOnClickListener(scrollTableTitleSortOnClickListener);
		}
	}

	/**
	 * 滑动控件是否支持翻页
	 * @param isSupportTurnPage : true 可以翻页；false 禁止翻页
	 */
	public void setTurnPage(boolean isSupportTurnPage) {
		this.isTurnPage = isSupportTurnPage;
	}
}
