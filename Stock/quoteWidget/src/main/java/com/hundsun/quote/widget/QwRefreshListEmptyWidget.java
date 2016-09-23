package com.hundsun.quote.widget;

import android.view.View;

/**
 * Interface that allows PullToRefreshBase to hijack the call to
 * AdapterView.setEmptyView()
 * 
 */
public interface QwRefreshListEmptyWidget {

	/**
	 * Calls upto AdapterView.setEmptyView()
	 * @param View to set as Empty View
	 */
	public void setEmptyViewInternal(View emptyView);

	/**
	 * Should call PullToRefreshBase.setEmptyView() which will then automatically call through to setEmptyViewInternal()
	 * @param View to set as Empty View
	 */
	public void setEmptyView(View emptyView);

}
