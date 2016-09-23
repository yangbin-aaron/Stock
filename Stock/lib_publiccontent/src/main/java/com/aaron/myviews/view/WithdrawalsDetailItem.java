package com.aaron.myviews.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myviews.R;

/**
 * Created by Administrator on 2015/10/23.
 */
public class WithdrawalsDetailItem extends RelativeLayout{

    private ColorStateList mTitleTextColor;
    private ColorStateList mContentTextColor;
    private CharSequence mTitleText;
    private CharSequence mContentText;
    private boolean mIsShowLine;

    private View mAboutLine;
    private TextView mTitle;
    private TextView mContent;

    public WithdrawalsDetailItem(Context context){
        super(context);
        init(context);
    }

    public WithdrawalsDetailItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
        init(context);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WithdrawalsDetailItem);
        mTitleTextColor = typedArray.getColorStateList(R.styleable.WithdrawalsDetailItem_detail_textColor);
        mContentTextColor = typedArray.getColorStateList(R.styleable.WithdrawalsDetailItem_detail_contentColor);
        mTitleText = typedArray.getText(R.styleable.WithdrawalsDetailItem_detail_text);
        mContentText = typedArray.getText(R.styleable.WithdrawalsDetailItem_detail_content);
        mIsShowLine = typedArray.getBoolean(R.styleable.WithdrawalsDetailItem_detail_is_show_line, true);
        typedArray.recycle();
    }

    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater.from(context).inflate(R.layout.layout_withdrawals_detail,this, true);
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.tv_withdrawals_detail_text);
        mContent = (TextView) findViewById(R.id.tv_withdrawals_detail_content);
        mAboutLine = (View) findViewById(R.id.line_detail);

        if(!mIsShowLine){
            mAboutLine.setVisibility(View.INVISIBLE);
        }
        if (mTitleTextColor!=null) {
            mTitle.setTextColor(mTitleTextColor);
        }
        mTitle.setText(mTitleText);
        if (mContentTextColor != null) {
            mContent.setTextColor(mContentTextColor);
        }
        mContent.setText(mContentText);
    }

    public void setContentText(String contentText) {
        if (!TextUtils.isEmpty(contentText)) {
            mContent.setText(contentText);
        }
    }

    public String getContentText() {
        return mContent.getText().toString();
    }
}
