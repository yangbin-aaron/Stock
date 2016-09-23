package com.aaron.myviews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可开启，关闭滑动的viewPager
 */
public class ScrollableViewPager extends ViewPager {

    private boolean mScrollable;

    public ScrollableViewPager(Context context) {
        super(context);
        init();
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScrollable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.mScrollable = scrollable;
    }
}
