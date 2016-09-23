package com.hundsun.quote.widget;

/**
 * HorizontalScrollView 同步滚动监听接口
 * @author 梁浩        2015-1-5-下午5:15:31
  *@copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public interface QwScrollTableSyncScrollListener {
	void onScrollChanged(QwScrollTableHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
}
