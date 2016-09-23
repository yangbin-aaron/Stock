package com.aaron.myviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.aaron.myviews.R;

public class NotificationTextView extends TextView {

    private Paint mPaint;
    private int mRedColor;

    public NotificationTextView(Context context) {
        super(context);
        init();
    }

    public NotificationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NotificationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRedColor = getResources().getColor(R.color.red_main);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mRedColor);
        setGravity(Gravity.CENTER_VERTICAL);
        if (isEnabled()) {
            setTextColor(mRedColor);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                getContext().getResources().getDisplayMetrics());
        setMeasuredDimension(width + offset, height + offset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isEnabled()) {
            int width = getWidth();
            int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                    getContext().getResources().getDisplayMetrics());
            canvas.drawCircle(width - radius, 0 + radius, radius, mPaint);
        }
        super.onDraw(canvas);
    }
}
