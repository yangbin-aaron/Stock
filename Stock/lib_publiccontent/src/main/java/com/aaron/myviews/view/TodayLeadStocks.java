package com.aaron.myviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.myviews.R;

public class TodayLeadStocks extends FrameLayout {

    private TextView mMoreStocks;

    private LinearLayout mTop1Stock;
    private LinearLayout mTop2Stock;
    private LinearLayout mTop3Stock;
    
    public TodayLeadStocks(Context context) {
        super(context);
        init(context);
    }

    public TodayLeadStocks(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_today_lead_stocks, this, true);

        mMoreStocks = (TextView) findViewById(R.id.tv_more_stocks);
        mTop1Stock = (LinearLayout) findViewById(R.id.top1_stock);
        mTop2Stock = (LinearLayout) findViewById(R.id.top2_stock);
        mTop3Stock = (LinearLayout) findViewById(R.id.top3_stock);
        
    }

    public TextView getMoreStocks() {
        return mMoreStocks;
    }

    public LinearLayout getTop1Stock() {
        return mTop1Stock;
    }

    public LinearLayout getTop2Stock() {
        return mTop2Stock;
    }

    public LinearLayout getTop3Stock() {
        return mTop3Stock;
    }
}
