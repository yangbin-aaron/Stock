package com.aaron.myviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.aaron.myviews.R;

public class ListViewLinearLayout extends LinearLayout {

    private BaseAdapter mAdapter;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object data);
    }

    public ListViewLinearLayout(Context context) {
        super(context);
        init();
    }

    public ListViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    public void setAdapter(BaseAdapter mBaseAdapter) {
        this.mAdapter = mBaseAdapter;
        bindLinearLayout();
    }

    private void bindLinearLayout() {
        int count = mAdapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = mAdapter.getView(i, null, null);
            Object data = mAdapter.getItem(i);
            v.setOnClickListener(mClickListener);
            v.setTag(R.string.key_position, i);
            v.setTag(R.string.key_data, data);
            if (getChildAt(i) != null) {
                addView(v, i);
            } else {
                addView(v);
            }
        }
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                int position = (Integer) v.getTag(R.string.key_position);
                Object data = v.getTag(R.string.key_data);
                mOnItemClickListener.onItemClick(v, position, data);
            }
        }
    };

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
