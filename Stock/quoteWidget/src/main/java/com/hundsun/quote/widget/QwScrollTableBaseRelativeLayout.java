package com.hundsun.quote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.hundsun.quotewidget.R;

/**
 * @author 梁浩        2015-2-28-下午4:07:20
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public abstract class QwScrollTableBaseRelativeLayout extends RelativeLayout implements View.OnClickListener{

	protected Context         context;
	protected OnClickListener mOnClickListener;

	public QwScrollTableBaseRelativeLayout(Context context){
		super(context);
	}

	public QwScrollTableBaseRelativeLayout(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
	}

	public QwScrollTableBaseRelativeLayout(Context context, AttributeSet attributeSet, int paramInt) {
		super(context, attributeSet, paramInt); 
	}

	protected void init(Context context){
		this.context = context;
		initBgColor();
	}

	public void initBgColor(){
		setBackgroundColor(context.getResources().getColor(R.color.common_hsv_bg));
	}

	public void onClick(View view){
	}

	public void setOnClickListener(OnClickListener onClickListener){
		this.mOnClickListener = onClickListener;
	}

	/**
	 * 基接口
	 * @author 梁浩        2015-2-12-上午11:26:36
	 *
	 */
	public static abstract interface OnClickListener extends View.OnClickListener{
		public abstract void OnContentItemClickListener(View view);

		public abstract void OnTitleClickListener(View view);

		public abstract void onClick(View view);
	}
}