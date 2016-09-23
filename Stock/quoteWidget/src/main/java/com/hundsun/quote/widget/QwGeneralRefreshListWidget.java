package com.hundsun.quote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;

import com.hundsun.quotewidget.R;

/**
 * 普通刷新控件
 * @author 梁浩        2015-2-16-上午10:07:15
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwGeneralRefreshListWidget extends QwGeneralRefreshListBaseWidget<ListView> {

	public QwGeneralRefreshListWidget(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public QwGeneralRefreshListWidget(Context context, int mode) {
		super(context, mode);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public QwGeneralRefreshListWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}
	
	/**
	 * 内部成员类 - 附加新功能【设置空视图】
	 * @author 梁浩        2015-2-16-上午9:39:02
	 *
	 */
	class InternalListView extends ListView implements QwRefreshListEmptyWidget {
		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			QwGeneralRefreshListWidget.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}


	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}
	
	@Override
	protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);
		lv.setId(R.id.widget_refresh_list_general);
		return lv;
	}

}
