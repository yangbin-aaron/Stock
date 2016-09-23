package com.aaron.myviews.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myviews.R;


/**
 * 标题栏
 *
 * @author bvin
 */
public class TitleBar extends RelativeLayout implements OnClickListener {

    private TextView mBack;
    private TextView mTitle;
    private ImageView mRightButton;
    private ImageView mRefreshButton;
    private Button mRightTitle;

    private int mBackgroundResId;
    private ColorStateList mTitleColor;
    private Drawable mRightDrawable;
    private Drawable mRightTextLeftDrawable;
    private Drawable mBackButtonDrawable;
    private Drawable mRefreshButtonDrawable;
    private CharSequence mTitleCharSequence;
    private CharSequence mBackText;
    private CharSequence mRightText;
    private boolean mHasBackButton;
    private int mRightDrawablePaddingRight = -1;
    private int mLeftPadding = 0;
    private int mTitleTextSize;
    private int mRightTextSize;

    public void setTitleTextSize(int titleTextSize) {
        if (titleTextSize != -1) {
            mTitleTextSize = titleTextSize;
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        }
    }

    public void setRightTextSize(int rightTextSize) {
        if (rightTextSize != -1) {
            mRightTextSize = rightTextSize;
            mRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
        }
    }


    public interface OnRightButtonClickListener {
        void onRightClick(View view);
    }

    OnRightButtonClickListener mOnRightButtonClickListener;

    public interface OnBackPressedListener {
        void onBackPressed(View view);
    }

    OnBackPressedListener mOnBackPressedListener;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
        init(context);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTitleCharSequence = typedArray.getText(R.styleable.TitleBar_title);
        mBackgroundResId = typedArray.getResourceId(R.styleable.TitleBar_background, 0);
        mTitleColor = typedArray.getColorStateList(R.styleable.TitleBar_titleColor);
        mRightDrawable = typedArray.getDrawable(R.styleable.TitleBar_rightDrawable);
        mRightTextLeftDrawable = typedArray.getDrawable(R.styleable.TitleBar_rightTextLeftDrawable);
        mBackButtonDrawable = typedArray.getDrawable(R.styleable.TitleBar_backButtonDrawable);
        mBackText = typedArray.getText(R.styleable.TitleBar_backButtonText);
        mRightText = typedArray.getText(R.styleable.TitleBar_rightText);
        mHasBackButton = typedArray.getBoolean(R.styleable.TitleBar_hasBackButton, true);
        mRightDrawablePaddingRight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_rightPaddingRight, 0);
        mRefreshButtonDrawable = typedArray.getDrawable(R.styleable.TitleBar_refreshButtonDrawable);
        mLeftPadding = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_leftPadding, 0);
        mTitleTextSize = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_titleTextSize, -1);
        mRightTextSize = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_rightTextSize, -1);
        typedArray.recycle();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBack = (TextView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mBack.setVisibility(mHasBackButton ? VISIBLE : GONE);
        mTitle = (TextView) findViewById(R.id.tvTitle);
        mRightButton = (ImageView) findViewById(R.id.right_button);
        mRightButton.setOnClickListener(this);
        mRightTitle = (Button) findViewById(R.id.right_title);
        mRightTitle.setOnClickListener(this);
        mRefreshButton = (ImageView) findViewById(R.id.btn_refresh);

        setTitle(mTitleCharSequence);
        setTitleColor(mTitleColor);
        setTitleTextSize(mTitleTextSize);
        setBackgroundResource(mBackgroundResId);
        setBackText(mBackText);
        setRightDrawable(mRightDrawable);
        setRightText(mRightText, null);
        setRightTextSize(mRightTextSize);
        setRightTextLeftDrawable(mRightTextLeftDrawable);
        setBackButtonDrawable(mBackButtonDrawable);
        setRefreshButtonDrawable(mRefreshButtonDrawable);
        setLeftPadding(mLeftPadding);
    }

    private void setRightTextLeftDrawable(Drawable rightTextLeftDrawable) {
        if (mRightTitle != null && rightTextLeftDrawable != null) {
            mRightTitle.setCompoundDrawablesWithIntrinsicBounds(rightTextLeftDrawable, null, null, null);
        }
    }

    private void setLeftPadding(int leftPadding) {
        if (mBack != null && leftPadding != 0) {
            mBack.setPadding(leftPadding, 0, 0, 0);
        }
    }

    private void setBackText(CharSequence backText) {
        if (mBack != null && backText != null) {
            mBack.setText(backText);
        }
    }

    public void setRefreshButtonDrawable(Drawable refreshButtonDrawable) {
        if (mRefreshButton != null && refreshButtonDrawable != null) {
            mRefreshButton.setVisibility(VISIBLE);
            mRefreshButton.setImageDrawable(refreshButtonDrawable);
        }
    }

    public void setBackButtonDrawable(Drawable backButtonDrawable) {
        if (mBack != null && backButtonDrawable != null) {
            mBack.setCompoundDrawablesWithIntrinsicBounds(backButtonDrawable, null, null, null);
        }
    }

    public String getTitleText() {
        return mTitle.getText().toString();
    }

    public void setTitle(CharSequence title) {
        if (title != null) {
            mTitle.setText(title);
            mTitleCharSequence = title;
        }
    }

    public void setTitle(int titleResId) {
        if (titleResId != 0) {
            mTitle.setText(titleResId);
        }
    }

    public void setTitleColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            mTitle.setTextColor(colorStateList);
        }
    }

    public void setLeftText(CharSequence text) {
        if (mBack != null) {
            mBack.setText(text);
        }
    }

    public void setLeftText(int textId) {
        if (mBack != null && textId > 0) {
            mBack.setText(textId);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            if (mOnBackPressedListener != null) {
                mOnBackPressedListener.onBackPressed(v);
            } else if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        } else if (v.getId() == R.id.right_button
                || v.getId() == R.id.right_title) {
            onRightButtonClicked(v);
        }
    }

    /**
     * 添加自定义标题，自定义View会在标题栏居中，并且会移除默认的文字标题
     *
     * @param v 自定义View
     */
    public void setCustomTitle(View v) {
        if (v != null) {
            getContainer().removeView(mTitle);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            getContainer().addView(v, layoutParams);
        }
    }

    /**
     * 设置标题，主标题和副标题水平居中
     *
     * @param title    主标题(白色，15sp)
     * @param subtitle 副标题(白色，10sp)
     */
    public void setTitle(CharSequence title, CharSequence subtitle) {
        LinearLayout verticalContainer = new LinearLayout(getContext());
        verticalContainer.setOrientation(LinearLayout.VERTICAL);
        verticalContainer.setGravity(Gravity.CENTER);
        int white = getContext().getResources().getColor(android.R.color.white);
        TextView tvTitle = new TextView(getContext());
        tvTitle.setText(title);
        tvTitle.setTextColor(white);
        tvTitle.setTextSize(15);
        TextView tvSubtitle = new TextView(getContext());
        tvSubtitle.setText(subtitle);
        tvSubtitle.setTextColor(white);
        tvSubtitle.setTextSize(10);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        verticalContainer.addView(tvTitle, lp);
        verticalContainer.addView(tvSubtitle, lp);
        setCustomTitle(verticalContainer);
    }

    public void setTitle(int titleId, CharSequence summary) {
        CharSequence title = getResources().getText(titleId);
        setTitle(title, summary);
    }

    @Override
    public void setBackgroundColor(int color) {
        getContainer().setBackgroundColor(color);
    }

    @Override
    public void setBackgroundResource(int resid) {
        if (resid > 0) {
            getContainer().setBackgroundResource(resid);
        }
    }

    /**
     * 设置背景颜色
     *
     * @param bacgroundColor 背景颜色
     * @param contentColor   内容颜色
     */
    public void setBackgroundColor(int bacgroundColor, int contentColor) {
        if (mTitle != null) {
            mTitle.setTextColor(contentColor);
        }
//        overrideColor(mBack.getDrawable(), contentColor);
        setBackgroundColor(bacgroundColor);
    }

    @SuppressLint("NewApi")
    private void overrideColor(Drawable drawable, int color) {
        if (VERSION.SDK_INT >= 21) {
            drawable.setTint(color);
            drawable.setTintMode(Mode.CLEAR);
        } else {
            drawable.setColorFilter(color, Mode.CLEAR);
        }
    }

    private ViewGroup getContainer() {
        return (ViewGroup) getChildAt(0);
    }

    public void setRightDrawable(Drawable rightButton) {
        if (mRightButton != null) {
            if (mRightTitle != null) {
                mRightTitle.setVisibility(View.GONE);
            }
            mRightButton.setVisibility(View.VISIBLE);
            mRightButton.setImageDrawable(rightButton);
            if (mRightDrawablePaddingRight != -1) {
                mRightButton.setPadding(18, 10, mRightDrawablePaddingRight, 10);
            }
            mRightDrawable = rightButton;
        }
    }

    public void setRightText(int textResId, OnRightButtonClickListener listener) {
        setRightText(getContext().getString(textResId), listener);
    }

    public void setRightText(CharSequence text, OnRightButtonClickListener listener) {
        if (mRightTitle != null && !TextUtils.isEmpty(text)) {
            if (mRightButton != null) {
                mRightButton.setVisibility(View.GONE);
            }
            mRightTitle.setVisibility(View.VISIBLE);
            mRightTitle.setText(text);
            mRightText = text;
        }
        if (listener != null)
            setOnButtonsClickListener(listener);
    }

    /**
     * 设置是否隐藏右边的文字
     *
     * @param gone 是否隐藏右边的文字
     */
    public void setRightTextGone(boolean gone) {
        if (mRightTitle != null && gone)
            mRightTitle.setVisibility(GONE);
    }

    /**
     * 设置是否隐藏右边的图片
     */
    public void setRightDrawableGone(boolean gone) {
        if (mRightButton != null) {
            if (mRightButton.getVisibility() == View.VISIBLE && gone) {
                mRightButton.setVisibility(View.GONE);
            } else if (mRightButton.getVisibility() == View.GONE && !gone) {
                mRightButton.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置是否隐藏返回按钮
     *
     * @param gone 是否隐藏返回按钮
     */
    public void setBackButtonGone(boolean gone) {
        if (mBack != null && gone)
            mBack.setVisibility(GONE);
    }

    public void setBackButtonDrawable(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        setBackButtonDrawable(drawable);
    }

    public void setOnButtonsClickListener(OnRightButtonClickListener listener) {
        mOnRightButtonClickListener = listener;
    }

    private void onRightButtonClicked(View view) {
        if (mOnRightButtonClickListener != null) {
            mOnRightButtonClickListener.onRightClick(view);
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        this.mOnBackPressedListener = listener;
    }

    public ImageView getRefreshButton() {
        return mRefreshButton;
    }

    public TextView getBackView(){
        return mBack;
    }
}
