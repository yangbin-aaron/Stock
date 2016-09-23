package com.aaron.myviews.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myviews.R;

public class FoundItem extends RelativeLayout{
	
	private ColorStateList mTitleTextColor;
	private ColorStateList mCountentTextColor;
	private Drawable mFoundImgDrawable;
	private CharSequence mTitleText;
	private CharSequence mContentText;
	private boolean mIsShowLine;
	private Drawable mFoundRightImgDrawable;
	
	private View mFoundLine;
	private ImageView mFoundItemImg;
	private TextView mTitle;
	private TextView mContent;
	private ImageView mRightIv;
	
	public FoundItem(Context context){
		super(context);
        init(context);
	}

	public FoundItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		parseAttributeSet(context, attrs);
		init(context);
	}
	
	private void parseAttributeSet(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FoundItem);
		mTitleTextColor = typedArray.getColorStateList(R.styleable.FoundItem_found_titleColor);
		mCountentTextColor = typedArray.getColorStateList(R.styleable.FoundItem_contentColor);
		mTitleText = typedArray.getText(R.styleable.FoundItem_found_title);
		mContentText = typedArray.getText(R.styleable.FoundItem_content);
		mFoundImgDrawable = typedArray.getDrawable(R.styleable.FoundItem_img_left);
		mIsShowLine = typedArray.getBoolean(R.styleable.FoundItem_is_show_line, true);
		mFoundRightImgDrawable = typedArray.getDrawable(R.styleable.FoundItem_img_right);
		typedArray.recycle();
	}
	
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.layout_found_item,this, true);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mFoundItemImg = (ImageView) findViewById(R.id.iv_found_item);
		mTitle = (TextView) findViewById(R.id.tv_title_found);
		mContent = (TextView) findViewById(R.id.tv_content_found);
		mRightIv = (ImageView) findViewById(R.id.iv_new_message);
		mFoundLine = (View) findViewById(R.id.line_found);
		
		if(!mIsShowLine){
			mFoundLine.setVisibility(View.INVISIBLE);
		}
		mFoundItemImg.setImageDrawable(mFoundImgDrawable);
		if (mFoundImgDrawable != null) {
			mRightIv.setImageDrawable(mFoundRightImgDrawable);
		}
		mTitle.setTextColor(mTitleTextColor);
		mTitle.setText(mTitleText);
		mContent.setTextColor(mCountentTextColor);
		mContent.setText(mContentText);
	}

	public void setRightImgViewShow(int visibility){
		mRightIv.setVisibility(visibility);
	}

}
