package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aaron.myviews.R;


public class ChartTabWidget extends LinearLayout implements View.OnClickListener {

    public static final int POS_LIGHTNING = 0;
    public static final int POS_TREND = 1;
    public static final int POS_KLINE = 2;
    public static final int POS_QUOTATION = 3;

    private FrameLayout mContainer;
    private LinearLayout mTabs;
    private PopupWindow mPopupWindow;

    public interface OnTabClickListener {
        void onTabClick(int position);
    }

    public interface OnDropDownItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnTabClickListener mOnTabClickListener;
    private OnDropDownItemClickListener mOnDropDownItemClickListener;

    public ChartTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartTabWidget(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_chart_tabs, this, true);

        mContainer = (FrameLayout) findViewById(R.id.container);
        initPopupWindow();
        initTabs();
    }

    private void initPopupWindow() {
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_window_kline, null);
        LinearLayout popupViewGroup = (LinearLayout) popupView;
        for (int i = 0; i < popupViewGroup.getChildCount(); i++) {
            View child = popupViewGroup.getChildAt(i);
            if (child instanceof TextView) {
                child.setOnClickListener(this);
            }
        }

        mPopupWindow = new PopupWindow(popupView,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setClippingEnabled(true);
    }

    private void initTabs() {
        mTabs = (LinearLayout) findViewById(R.id.tabs);
        for (int i = 0; i < mTabs.getChildCount(); i++) {
            LinearLayout tab = (LinearLayout) mTabs.getChildAt(i);
            tab.setOnClickListener(this);
        }
        setTabEnable(POS_KLINE, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lightning_tab) {
            onTabClick(POS_LIGHTNING);
            restoreKlineTab();
        } else if (v.getId() == R.id.trend_tab) {
            onTabClick(POS_TREND);
            restoreKlineTab();
        } else if (v.getId() == R.id.kline_tab) {
            onTabClick(POS_KLINE);
            showPopupWindow(v);
        } else if (v.getId() == R.id.quotation_tab) {
            onTabClick(POS_QUOTATION);
            restoreKlineTab();
        } else if (v.getId() == R.id.one_minute) {
            onDropDownItemClick(v, 0);
        } else if (v.getId() == R.id.three_minutes) {
            onDropDownItemClick(v, 1);
        } else if (v.getId() == R.id.five_minutes) {
            onDropDownItemClick(v, 2);
        } else if (v.getId() == R.id.fifteen_minutes) {
            onDropDownItemClick(v, 3);
        } else if (v.getId() == R.id.day_k) {
            onDropDownItemClick(v, 4);
        }
    }

    public void setTabEnable(int position, boolean enable) {
        mTabs = (LinearLayout) findViewById(R.id.tabs);
        LinearLayout tab = (LinearLayout) mTabs.getChildAt(position);
        tab.setEnabled(enable);
        tab.getChildAt(0).setEnabled(enable);
    }

    private void restoreKlineTab() {
        LinearLayout klineTab = (LinearLayout) findViewById(R.id.kline_tab);
        TextView tabText = (TextView) klineTab.getChildAt(0);
        tabText.setText(R.string.kline_chart);
    }

    private void onDropDownItemClick(View v, int index) {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }

        TextView clickedView = (TextView) v;
        LinearLayout klineTab = (LinearLayout) findViewById(R.id.kline_tab);
        TextView tabText = (TextView) klineTab.getChildAt(0);
        tabText.setText(clickedView.getText());

        if (mOnDropDownItemClickListener != null) {
            mOnDropDownItemClickListener.onItemClick(v, index);
        }
    }

    private void showPopupWindow(View v) {
        if (mPopupWindow != null) {
            if (!mPopupWindow.isShowing()) {
                View popupView = mPopupWindow.getContentView();
                popupView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int offsetX = v.getWidth() / 2 - popupView.getMeasuredWidth() / 2;
                mPopupWindow.showAsDropDown(v, offsetX, 0);
            } else {
                mPopupWindow.dismiss();
            }
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }

    public void setOnDropDownItemClickListener(OnDropDownItemClickListener onDropDownItemClickListener) {
        this.mOnDropDownItemClickListener = onDropDownItemClickListener;
    }

    private void onTabClick(int pos) {
        if (mOnTabClickListener != null) {
            mOnTabClickListener.onTabClick(pos);
        }
    }

    private void selectItem(int position) {
        if (mTabs != null) {
            unselectItems();
            LinearLayout tab = (LinearLayout) mTabs.getChildAt(position);
            tab.getChildAt(0).setSelected(true);
        }
    }

    private void unselectItems() {
        for (int i = 0; i < mTabs.getChildCount(); i++) {
            LinearLayout tab = (LinearLayout) mTabs.getChildAt(i);
            tab.getChildAt(0).setSelected(false);
        }
    }

    public void performTabClick(int position) {
        if (mTabs != null) {
            mTabs.getChildAt(position).performClick();
        }
    }

    public void addChart(View chartView, int position) {
        if (mContainer != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mContainer.addView(chartView, position, params);
        }
    }

    public void showChart(int position) {
        hideCharts();
        if (mContainer != null) {
            mContainer.getChildAt(position).setVisibility(VISIBLE);
            selectItem(position);
        }
    }

    private void hideCharts() {
        if (mContainer != null) {
            for (int i = 0; i < mContainer.getChildCount(); i++) {
                View view = mContainer.getChildAt(i);
                view.setVisibility(GONE);
            }
        }
    }
}