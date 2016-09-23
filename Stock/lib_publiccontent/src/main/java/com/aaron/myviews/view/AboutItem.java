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

public class AboutItem extends RelativeLayout{

	private ColorStateList mTitleTextColor;
	private ColorStateList mContentTextColor;
	private Drawable mAboutImgDrawable;
	private CharSequence mTitleText;
	private CharSequence mContentText;
	private boolean mIsShowLine;

	private View mAboutLine;
	private ImageView mAboutItemImg;
	private TextView mTitle;
	private TextView mContent;

	public AboutItem(Context context){
		super(context);
        init(context);
	}

	public AboutItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		parseAttributeSet(context, attrs);
		init(context);
	}
	
	private void parseAttributeSet(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AboutItem);
		mTitleTextColor = typedArray.getColorStateList(R.styleable.AboutItem_about_textColor);
		mContentTextColor = typedArray.getColorStateList(R.styleable.AboutItem_about_contentColor);
		mTitleText = typedArray.getText(R.styleable.AboutItem_about_text);
		mContentText = typedArray.getText(R.styleable.AboutItem_about_content);
		mAboutImgDrawable = typedArray.getDrawable(R.styleable.AboutItem_about_img_left);
		mIsShowLine = typedArray.getBoolean(R.styleable.AboutItem_about_is_show_line, true);
		typedArray.recycle();
	}
	
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.layout_about_item,this, true);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mAboutItemImg = (ImageView) findViewById(R.id.iv_about_icon);
		mTitle = (TextView) findViewById(R.id.tv_text_about);
		mContent = (TextView) findViewById(R.id.tv_content_about);
		mAboutLine = (View) findViewById(R.id.line_about);
		
		if(!mIsShowLine){
			mAboutLine.setVisibility(View.INVISIBLE);
		}
		mAboutItemImg.setImageDrawable(mAboutImgDrawable);
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
		mContent.setText(contentText);
	}

	public String getContentText() {
		return mContent.getText().toString();
	}
}
