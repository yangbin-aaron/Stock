package com.aaron.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myutils.ScreenUtil;

/**
 * Created by toughegg on 16/4/22.
 * 自定义ActionBar
 */
public class MyActionBar extends RelativeLayout {

    private RelativeLayout mActionBarLayout;// 整个布局
    private RelativeLayout mLeftLayout, mRightLayout;// 左右的布局（点击事件）
    private TextView mLeftTextView, mTitleTextView, mRightTextView;// 左右和标题的文字
    private ImageView mLeftImageView, mRightImageView;// 左右的图片

    private OnActionBarClickListener mOnActionBarClickListener;// 点击事件回调

    public void setOnActionBarClickListener(OnActionBarClickListener onActionBarClickListener) {
        mOnActionBarClickListener = onActionBarClickListener;
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnActionBarClickListener != null) {
                if (v.getId() == R.id.left_layout) {
                    mOnActionBarClickListener.onActionBarLeftClicked();
                } else if (v.getId() == R.id.right_layout) {
                    mOnActionBarClickListener.onActionBarRightClicked();
                }
            }
        }
    };

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyActionBar);
        init(context, ta);
    }

    public MyActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyActionBar);
        init(context, ta);
    }

    private void init(Context context, TypedArray ta) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.view_myactionbar_layout, null);
        mActionBarLayout = (RelativeLayout) viewGroup.findViewById(R.id.my_actionbar_layout);
        mLeftLayout = (RelativeLayout) viewGroup.findViewById(R.id.left_layout);
        mRightLayout = (RelativeLayout) viewGroup.findViewById(R.id.right_layout);
        mLeftTextView = (TextView) viewGroup.findViewById(R.id.left_tv);
        mTitleTextView = (TextView) viewGroup.findViewById(R.id.title_tv);
        mRightTextView = (TextView) viewGroup.findViewById(R.id.right_tv);
        mLeftImageView = (ImageView) viewGroup.findViewById(R.id.left_iv);
        mRightImageView = (ImageView) viewGroup.findViewById(R.id.right_iv);
        setAttrs(context, ta);
        addView(viewGroup);
    }

    private void setAttrs(Context context, TypedArray ta) {
        // 背景颜色
        int backRes = ta.getResourceId(R.styleable.MyActionBar_bar_backgroundColor, 0);
        if (backRes != 0) {
            mActionBarLayout.setBackgroundResource(backRes);
        }
        // 设置标题
        String title = ta.getString(R.styleable.MyActionBar_title_text);
        if (title != null && !title.equals("")) {
            setBarTitleText(title);
        }
        float titleSize = ta.getDimension(R.styleable.MyActionBar_title_textSize, 0);
        if (titleSize != 0) {
            mTitleTextView.setTextSize(ScreenUtil.getFontSize(context, titleSize / 2.3f));// 2.3f 是根据测试计算出来的
        }
        int titleColor = ta.getColor(R.styleable.MyActionBar_title_textColor, 0);
        if (titleColor != 0) {
            mTitleTextView.setTextColor(titleColor);
        }
        // Left
        String left = ta.getString(R.styleable.MyActionBar_left_text);
        if (left != null && !left.equals("")) {
            setBarLeftText(left);
        }
        float leftSize = ta.getDimension(R.styleable.MyActionBar_left_textSize, 0);
        if (leftSize != 0) {
            mLeftTextView.setTextSize(ScreenUtil.getFontSize(context, leftSize / 2.3f));
        }
        int leftColor = ta.getColor(R.styleable.MyActionBar_left_textColor, 0);
        if (leftColor != 0) {
            mLeftTextView.setTextColor(leftColor);
        }
        int leftRes = ta.getResourceId(R.styleable.MyActionBar_left_image, 0);
        if (leftRes != 0) {
            setBarLeftImage(leftRes);
        }
        String right = ta.getString(R.styleable.MyActionBar_right_text);
        if (right != null && !right.equals("")) {
            setBarRightText(right);
        }
        float rightSize = ta.getDimension(R.styleable.MyActionBar_right_textSize, 0);
        if (rightSize != 0) {
            mRightTextView.setTextSize(ScreenUtil.getFontSize(context, rightSize / 2.3f));
        }
        int rightColor = ta.getColor(R.styleable.MyActionBar_right_textColor, 0);
        if (rightColor != 0) {
            mRightTextView.setTextColor(rightColor);
        }
        int rightRes = ta.getResourceId(R.styleable.MyActionBar_right_image, 0);
        if (rightRes != 0) {
            setBarRightImage(rightRes);
        }
        ta.recycle();
    }

    /**
     * 设置背景颜色（图片）
     *
     * @param resId
     */
    public void setBarBackGround(int resId) {
        mActionBarLayout.setBackgroundResource(resId);
    }

    /**
     * 设置文字颜色
     *
     * @param color
     */
    public void setBarTextColor(int color) {
        mTitleTextView.setTextColor(color);
        mLeftTextView.setTextColor(color);
        mRightTextView.setTextColor(color);
    }

    /**
     * 设置字体背景颜色
     *
     * @param resId
     */
    public void setBarTextBackground(int resId) {
        mLeftTextView.setBackgroundResource(resId);
        mRightTextView.setBackgroundResource(resId);
    }

    /**
     * 设置标题
     *
     * @param titleStr
     */
    public void setBarTitleText(String titleStr) {
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText(titleStr);
    }

    /**
     * 设置标题
     *
     * @param titleResId
     */
    public void setBarTitleText(int titleResId) {
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText(titleResId);
    }

    /**
     * 设置左边文字
     *
     * @param leftStr
     */
    public void setBarLeftText(String leftStr) {
        mLeftLayout.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(mClickListener);
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText(leftStr);
    }

    /**
     * 设置左边文字
     *
     * @param leftResId
     */
    public void setBarLeftText(int leftResId) {
        mLeftLayout.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(mClickListener);
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText(leftResId);
    }

    /**
     * 设置右边文字
     *
     * @param rightStr
     */
    public void setBarRightText(String rightStr) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(mClickListener);
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText(rightStr);
    }

    /**
     * 设置右边文字
     *
     * @param rightResId
     */
    public void setBarRightText(int rightResId) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(mClickListener);
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText(rightResId);
    }

    /**
     * 设置左边图片
     *
     * @param leftResId
     */
    public void setBarLeftImage(int leftResId) {
        mLeftLayout.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(mClickListener);
        mLeftImageView.setVisibility(View.VISIBLE);
        mLeftImageView.setImageResource(leftResId);
    }

    /**
     * 设置左边图片
     *
     * @param leftImage
     */
    public void setBarLeftImage(Drawable leftImage) {
        mLeftLayout.setVisibility(View.VISIBLE);
        mLeftLayout.setOnClickListener(mClickListener);
        mLeftImageView.setVisibility(View.VISIBLE);
        mLeftImageView.setImageDrawable(leftImage);
    }

    /**
     * 设置右边图片
     *
     * @param rightResId
     */
    public void setBarRightImage(int rightResId) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(mClickListener);
        mRightImageView.setVisibility(View.VISIBLE);
        mRightImageView.setImageResource(rightResId);
    }

    /**
     * 设置右边图片
     *
     * @param rightImage
     */
    public void setBarRightImage(Drawable rightImage) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightLayout.setOnClickListener(mClickListener);
        mRightImageView.setVisibility(View.VISIBLE);
        mRightImageView.setImageDrawable(rightImage);
    }

    /**
     * 左右按钮点击接口
     */
    public interface OnActionBarClickListener {
        void onActionBarLeftClicked();

        void onActionBarRightClicked();
    }
}
