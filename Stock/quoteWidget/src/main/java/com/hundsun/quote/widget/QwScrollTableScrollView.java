package com.hundsun.quote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author 梁浩        2015-2-28-下午4:06:22
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwScrollTableScrollView extends ScrollView {

	public QwScrollTableScrollView(Context context) {
		super(context);
	}

	public QwScrollTableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public QwScrollTableScrollView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context) {
		
	}

}


