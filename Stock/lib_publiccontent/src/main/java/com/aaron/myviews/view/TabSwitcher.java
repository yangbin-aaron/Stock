package com.aaron.myviews.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aaron.myviews.R;


public class TabSwitcher extends RadioGroup {
    
    private final static int ICON_POSITION_TOP = 0;
    private final static int ICON_POSITION_BOTTOM = 1;
    private final static int ICON_POSITION_LEFT = 2;
    private final static int ICON_POSITION_RIGHT = 3;
    
    private static final int CHECKED_PART_LEFT = 0;
    private static final int CHECKED_PART_RIGHT = 1;
    
    /**
     * TabSwitcher暂时只支持左右两部分
     * @author bvin
     */
    public enum Part{Left,Right}
    
    private Part mCheckedPart;
    
    /**
     * icon的位置
     * @author bvin
     */
    public enum IconPosition{Left,Right,Top,Bottom}
    
    private CharSequence mLeftText;
    private int mLeftTextSize;
    private ColorStateList mLeftTextColor;
    private Drawable mLeftBackground;
    private Drawable mLeftIcon;
    
    private CharSequence mRightText;
    private int mRightTextSize;
    private ColorStateList mRightTextColor;
    private Drawable mRightBackground;
    private Drawable mRightIcon;
    
    /*以下可以是左右统一的*/
    private ColorStateList mTextColor;
    private int mTextSize = 15;
    private int mIconPosition;
    
    private int mPadding;
    private int mPaddingLeft = -1;
    private int mPaddingTop = -1;
    private int mPaddingRight = -1;
    private int mPaddingBottom = -1;
    
    private int mLeftPadding;
    private int mLeftPaddingLeft = -1;
    private int mLeftPaddingTop = -1;
    private int mLeftPaddingRight = -1;
    private int mLeftPaddingBottom = -1;
    
    private int mRightPadding;
    private int mRightPaddingleft = -1;
    private int mRightPaddingTop = -1;
    private int mRightPaddingRight = -1;
    private int mRighttPaddingBottom = -1;
    
    private RadioGroup rgSelf;
    private RadioButton rbLeft;
    private RadioButton rbRight;
    private OnTabSwitchListener mOnTabSwitchListener;
    
    public TabSwitcher(Context context) {
        super(context);
        init(context);
    }

    public TabSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
        init(context);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TabSwitcher);
        
        mLeftText = attributes.getText(R.styleable.TabSwitcher_left_text);
        mRightText = attributes.getText(R.styleable.TabSwitcher_right_text);
        
        mLeftTextColor = mRightTextColor = mTextColor = attributes.getColorStateList(R.styleable.TabSwitcher_textColor);
        if (mTextColor==null) {
            mLeftTextColor = attributes.getColorStateList(R.styleable.TabSwitcher_left_text_color);
            mRightTextColor = attributes.getColorStateList(R.styleable.TabSwitcher_right_text_color);
        }
        
        mLeftTextSize = mRightTextSize = mTextSize = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_textSize,-1);
        if (mTextSize==-1) {
            mLeftTextSize = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_left_text_size,-1);
            mRightTextSize = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_right_text_size,-1);
        }

        int checkedPartId = attributes.getInt(R.styleable.TabSwitcher_checkedPart, 0);
        if (checkedPartId == CHECKED_PART_LEFT) {
            mCheckedPart = Part.Left;
        } else if (checkedPartId == CHECKED_PART_RIGHT){
            mCheckedPart = Part.Right;
        }
        
        mLeftIcon = attributes.getDrawable(R.styleable.TabSwitcher_left_icon);
        mRightIcon = attributes.getDrawable(R.styleable.TabSwitcher_right_icon);
        if (mLeftIcon!=null) {
            mLeftIcon.setBounds(0, 0, mLeftIcon.getIntrinsicWidth(), mLeftIcon.getIntrinsicHeight());
        }
        if (mRightIcon!=null) {
            mRightIcon.setBounds(0, 0, mRightIcon.getIntrinsicWidth(), mRightIcon.getIntrinsicHeight());
        }
        
        mIconPosition = attributes.getInt(R.styleable.TabSwitcher_icon_position, mIconPosition);
        
        mLeftBackground = attributes.getDrawable(R.styleable.TabSwitcher_left_background);
        mRightBackground = attributes.getDrawable(R.styleable.TabSwitcher_right_background);
        
        mPadding = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_padding,-1);
        if (mPadding==-1) {//padding没填就取paddingV和PaddingH
            
            int paddingHorizontal =  attributes.getDimensionPixelSize(R.styleable.TabSwitcher_paddingHorizontal,-1);
            if (paddingHorizontal==-1) {//paddingHorizontal没填就去paddingLeft和PaddingRight
                mPaddingLeft = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_paddingLeft,-1);
                if (mPaddingLeft==-1) {//paddingLeft没填就去leftPaddingLeft和rightPaddingLeft
                    mLeftPaddingLeft = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_left_paddingLeft,-1);
                    mRightPaddingleft = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_right_paddingLeft,-1);
                }else {
                    mLeftPaddingLeft = mRightPaddingleft = mPaddingLeft;
                }
                mPaddingRight = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_paddingRight,-1);
                if (mPaddingRight==-1) {//paddingRight没填就去leftPaddingRight和rightPaddingRight
                    mLeftPaddingRight = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_left_paddingRight,-1);
                    mRightPaddingleft = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_right_paddingRight,-1);
                }else {
                    mLeftPaddingRight = mRightPaddingleft = mPaddingRight;
                } 
                
            }else {
                mLeftPaddingLeft = mRightPaddingleft = mPaddingLeft = paddingHorizontal;
                mLeftPaddingRight = mRightPaddingRight = mPaddingRight = paddingHorizontal;
            }
            int paddingVertical =  attributes.getDimensionPixelSize(R.styleable.TabSwitcher_paddingVertical,-1);
            if (paddingVertical==-1) {
                mPaddingTop = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_paddingLeft,-1);
                if (mPaddingTop==-1) {//paddingLeft没填就去leftPaddingTop和rightPaddingTop
                    mLeftPaddingTop = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_left_paddingTop,-1);
                    mRightPaddingTop = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_right_paddingTop,-1);
                }else {
                    mLeftPaddingTop = mRightPaddingTop = mPaddingTop;
                }
                mPaddingBottom = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_paddingBottom,-1);
                if (mPaddingBottom==-1) {//paddingBottom没填就去leftPaddingBottom和rightPaddingBottom
                    mLeftPaddingBottom = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_left_paddingBottom,-1);
                    mRighttPaddingBottom = attributes.getDimensionPixelSize(R.styleable.TabSwitcher_right_paddingBottom,-1);
                }else {
                    mLeftPaddingRight = mRightPaddingleft = mPaddingBottom;
                } 
            } else {
                mLeftPaddingTop = mRightPaddingTop = mPaddingTop = paddingVertical;
                mLeftPaddingBottom = mRighttPaddingBottom = mPaddingBottom = paddingVertical;
            }
            
        } else {
            mLeftPaddingLeft = mRightPaddingleft = mPaddingLeft = mPadding;
            mLeftPaddingRight = mRightPaddingRight = mPaddingRight = mPadding;
            mLeftPaddingTop = mRightPaddingTop = mPaddingTop = mPadding;
            mLeftPaddingBottom = mRighttPaddingBottom = mPaddingBottom = mPadding;
        }
        attributes.recycle();
    }
    
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.tab_switcher, this, true);
    }
    
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rgSelf = (RadioGroup) findViewById(R.id.rg_tab_switcher);
        rgSelf.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mOnTabSwitchListener!=null) {
                    mOnTabSwitchListener.onTabSwitch(TabSwitcher.this, getPart(checkedId));
                }
            }
        });
        rbLeft = (RadioButton) findViewById(R.id.tab_switcher_left);
        rbRight = (RadioButton) findViewById(R.id.tab_switcher_right);
        rbLeft.setText(mLeftText);
        rbLeft.setTextColor(mLeftTextColor);
        rbLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX,mLeftTextSize);
        setBackground(rbLeft, mLeftBackground);
        rbLeft.setPadding(mLeftPaddingLeft, mLeftPaddingTop, mLeftPaddingRight, mLeftPaddingBottom);
        
        rbRight.setText(mRightText);
        rbRight.setTextColor(mRightTextColor);
        rbRight.setTextSize(TypedValue.COMPLEX_UNIT_PX,mRightTextSize);
        setBackground(rbRight, mRightBackground);
        rbRight.setPadding(mRightPaddingleft, mRightPaddingTop, mRightPaddingRight, mRighttPaddingBottom);
        
        switch (mIconPosition) {
        case ICON_POSITION_TOP:
            rbLeft.setCompoundDrawables(null, mLeftIcon, null, null);
            rbRight.setCompoundDrawables(null, mRightIcon, null, null);
            break;
        case ICON_POSITION_BOTTOM:
            rbLeft.setCompoundDrawables(null, null, null, mLeftIcon);
            rbRight.setCompoundDrawables(null, null, null, mRightIcon);
            break;
        case ICON_POSITION_LEFT:
            rbLeft.setCompoundDrawables(mLeftIcon, null, null, null);
            rbRight.setCompoundDrawables(mRightIcon, null, null, null);
            break;
        case ICON_POSITION_RIGHT:
            rbLeft.setCompoundDrawables(null, null, mLeftIcon, null);
            rbRight.setCompoundDrawables(null, null, mRightIcon, null);
            break;
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void setBackground(View v,Drawable background) {
        if (v==null||background==null) {
            return;
        }
        if (VERSION.SDK_INT>=16) {
            v.setBackground(background);
        }else {
            v.setBackgroundDrawable(background);
        }
    }

    /**
     * 选项切换监听器
     * @param listener
     */
    public void setOnTabSwitchListener(OnTabSwitchListener listener) {
        mOnTabSwitchListener = listener;
    }
    
    
    /**
     * 请调用{@link #setOnTabSwitchListener(OnTabSwitchListener)}代替
     */
    @Deprecated
    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);
    }

    /**
     * 请调用 {@link #getCheckedPart()}代替
     */
    @Deprecated
    @Override
    public int getCheckedRadioButtonId() {
        return super.getCheckedRadioButtonId();
    }
    
    /**
     * 请使用{@link #switchPart(Part)}代替
     */
    @Deprecated
    @Override
    public void check(int id) {
        super.check(id);
    }

    public interface OnTabSwitchListener{
        /**
         * Tab切换
         * @param tabSwitcher TabSwitcher控件
         * @param checkedPart 选中的部分
         */
        public void onTabSwitch(TabSwitcher tabSwitcher, Part checkedPart);
    }
    
    private Part getPart(int checkedId) {
        if (checkedId == R.id.tab_switcher_left) {
            mCheckedPart = Part.Left;
        } else if (checkedId == R.id.tab_switcher_right){
            mCheckedPart = Part.Right;
        }
        return mCheckedPart;
    }
    
    private int getCheckedId(Part checkedPart) {
        int id = 0;
        switch (checkedPart) {
        case Left:
            id = R.id.tab_switcher_left;
            break; 

        case Right:
            id = R.id.tab_switcher_right;
            break; 
        }
        return id;
    }
    
    private RadioButton getCheckedView(Part checkedPart) {
        RadioButton rb = rbLeft;
        switch (checkedPart) {
        case Left:
            rb = rbLeft;
            break; 

        case Right:
            rb = rbRight;
            break; 
        }
        return rb;
    }
    
    /**
     * 获取选中的部分
     * @return
     */
    public Part getCheckedPart() {
        return mCheckedPart;
    }
    
    /**
     * 切换
     * @param part 左边部分或右边部分
     */
    public void switchPart(Part part) {
        rgSelf.check(getCheckedId(part));
    }
    
    @Override
    public void setBackgroundColor(int color) {
        if (getContainer()!=null) {
            getContainer().setBackgroundColor(color);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        if (getContainer()!=null) {
            getContainer().setBackgroundResource(resId);
        }
    }

    //获取跟布局
    private ViewGroup getContainer() {
        return (ViewGroup) getChildAt(0);
    }
    
    public void changeLeftCheckedView(){
        rbLeft.setChecked(true);
        rbRight.setChecked(false);
    }
    
    /**
     * 设置文字
     * @param part
     * @param charSequence
     */
    public void setText(Part part,CharSequence charSequence) {
        getCheckedView(part).setText(charSequence);
    }

    public void setText(CharSequence leftText,CharSequence rightText) {
        setText(Part.Left,leftText);
        setText(Part.Right,rightText);
    }

    public void setText(int leftText, int rightText) {
        setText(Part.Left, getContext().getString(leftText));
        setText(Part.Right, getContext().getString(rightText));
    }

    /**
     * 设置字体颜色
     * @param part
     * @param colors
     */
    private void setTextColor(Part part,ColorStateList colors) {
        getCheckedView(part).setTextColor(colors);
    }
    
    /**
     * 设置字体颜色
     * @param colors
     */
    public void setTextColor(ColorStateList colors) {
        mLeftTextColor = mRightTextColor = colors;
        rbLeft.setTextColor(colors);
        rbRight.setTextColor(colors);
    }

    public void setTextColor(int colors) {
        setTextColor(getResources().getColorStateList(colors));
    }
    
    private void setTextSize(Part part, float size) {
        getCheckedView(part).setTextSize(size);
    }
    
    /**
     * 设置字体大小
     * @param size
     */
    public void setTextSize(float size) {
        mLeftTextSize = mRightTextSize = (int) size;
        rbLeft.setTextSize(size);
        rbRight.setTextSize(size);
    }

    /**
     * 设置part背景
     * @param leftBackground 左边背景
     * @param rightBackground 右边背景
     */
    public void setPartBackground(int leftBackground, int rightBackground) {
        setBackground(getCheckedView(Part.Left), getResources().getDrawable(leftBackground));
        setBackground(getCheckedView(Part.Right), getResources().getDrawable(rightBackground));
    }
    
    /**
     * 设置part内边距 
     * @param padding 上下左右内边距都一致
     */
    public void setPartPadding(int padding) {
        setPartPadding(getPart(R.id.tab_switcher_left), padding, padding, padding, padding);
        setPartPadding(getPart(R.id.tab_switcher_right), padding, padding, padding, padding);
    }
    
    /**
     * 设置part内边距 
     * @param part
     * @param padding 上下左右内边距都一致
     */
    public void setPartPadding(Part part,int padding) {
        setPartPadding(part, padding, padding, padding, padding);
    }
    
    /**
     * 设置part内边距
     * @param part
     * @param paddingVertical 横向内边距，左右相同
     * @param paddingHorizontal 纵向内边距，上下相同
     */
    public void setPartPadding(Part part,int paddingVertical,int paddingHorizontal) {
        setPartPadding(part,paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
    }
    
    /**
     * 设置part内边距 
     * @param paddingVertical 横向内边距，左右相同
     * @param paddingHorizontal 纵向内边距，上下相同
     */
    public void setPartPadding(int paddingVertical,int paddingHorizontal) {
        setPartPadding(getPart(R.id.tab_switcher_left),paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        setPartPadding(getPart(R.id.tab_switcher_right),paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
    }
    
    public void setPartPadding(Part part, int left, int top, int right, int bottom) {
        getCheckedView(part).setPadding(left, top, right, bottom);
    }
    
    /**
     * 设置Part图标
     * @param part 部分
     * @param iconPosition icon位置
     * @param icon 图标
     */
    public void setPartIcon(Part part,IconPosition iconPosition,Drawable icon) {
        switch (iconPosition) {
        case Left:
            setPartIcon(part, icon, null, null, null);
            break;
        case Right:
            setPartIcon(part, null, null, icon, null);
            break;
        case Top:
            setPartIcon(part, null, icon, null, null);
            break;
        case Bottom:
            setPartIcon(part, null, null, null, icon);
            break;
        }
    }
    
    public void setPartIcon(Part part, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        getCheckedView(part).setCompoundDrawables(getIntrinsicDrawable(left), 
                getIntrinsicDrawable(top),getIntrinsicDrawable(right),
                getIntrinsicDrawable(bottom));
    }
    
    /**
     * 获取真实大小的图片
     * @param origin 原始图标
     * @return 返回setBounds后的Drawable
     */
    private Drawable getIntrinsicDrawable(Drawable origin) {
        if (origin!=null) {
            origin.setBounds(0,0,origin.getIntrinsicWidth(),origin.getIntrinsicHeight());
        }
        return origin;
    }
    
}
