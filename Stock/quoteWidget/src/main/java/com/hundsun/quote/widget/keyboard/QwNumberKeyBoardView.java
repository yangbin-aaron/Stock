package com.hundsun.quote.widget.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hundsun.quotewidget.R;

public class QwNumberKeyBoardView extends LinearLayout{

	public QwNumberKeyBoardView(Context context) {
		super(context);
		initView(context);
	}
	
	public QwNumberKeyBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	
	void initView(Context context){
		this.setOrientation( LinearLayout.VERTICAL);
		inflate(context, R.layout.keyboard_number, this);
	}

}
