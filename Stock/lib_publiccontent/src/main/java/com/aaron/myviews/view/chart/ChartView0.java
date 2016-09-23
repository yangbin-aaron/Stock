package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class ChartView0 extends SurfaceView {

    protected static final int PADDING_FOR_TEXT = 18; //dp
    protected static final int PADDING_FOR_LINE = 10; //dp

    protected Paint mPaint;
    protected Paint mTextPaint;

    private SurfaceHolder mSurfaceHolder;
    private Thread mThread;

    private float[] mBaselines;
    private PointF mTouchPoint;
    private int mTotalPoints;
    private int mNumberScale;

    public ChartView0(Context context) {
        super(context);
        init();
    }

    public ChartView0(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBaselines = new float[4];

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                draw();
                Log.d("TEST", "surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mPaint = null;
                mTextPaint = null;
                Log.d("TEST", "surfaceDestroyed");
            }
        });
    }

    public void draw() {
        mThread = new Thread(new DrawTask());
        mThread.start();
    }

    public void clear() {
        mThread = new Thread(new ClearTask());
        mThread.start();
    }

    private class DrawTask implements Runnable {

        @Override
        public void run() {
            if (mSurfaceHolder.getSurface().isValid()) {
                Canvas canvas = mSurfaceHolder.lockCanvas();
                if (canvas != null) {
                    onDrawContent(canvas);
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    protected void onDrawContent(Canvas canvas) {
        clearAll(canvas);
        drawBaseLines(canvas);
        drawRealTimeLines(canvas);
        drawTouchLines(canvas);
    }

    private class ClearTask implements Runnable {
        @Override
        public void run() {
            if (mSurfaceHolder.getSurface().isValid()) {
                Canvas canvas = mSurfaceHolder.lockCanvas();
                if (canvas != null) {
                    clearAll(canvas);
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setNumberScale(int numberScale) {
        mNumberScale = numberScale;
    }

    public void setTotalPoints(int totalPoints) {
        mTotalPoints = totalPoints;
    }

    public void setBaselines(int baselines) {
        if (baselines < 2) baselines = 2;
        mBaselines = new float[baselines];
    }

    public float[] getBaselines() {
        return mBaselines;
    }

    public PointF getTouchPoint() {
        return mTouchPoint;
    }

    protected void clearAll(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#030014"));
    }

    protected abstract void drawBaseLines(Canvas canvas);

    protected abstract void drawRealTimeLines(Canvas canvas);

    protected abstract void drawTouchLines(Canvas canvas);

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (shouldUpdate(event)) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    setTouchPoint(event);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (shouldUpdate(event)) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    setTouchPoint(event);
                }
                return true;
            case MotionEvent.ACTION_UP:
                this.getParent().requestDisallowInterceptTouchEvent(true);
                mTouchPoint = null;
                return true;
        }
        return super.onTouchEvent(event);
    }*/

    protected boolean hasDataInThisCoordinate(MotionEvent event) {
        return event != null;
    }

    private void setTouchPoint(MotionEvent event) {
        if (mTouchPoint == null) {
            mTouchPoint = new PointF();
        }
        mTouchPoint.x = event.getX();
        mTouchPoint.y = event.getY();
    }

    /**
     * the startXY of drawText is at baseline, this is used to add some offsetY for text center
     * centerY = y + offsetY

     * @return offsetY
     */
    protected float offsetY4TextCenter() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        return fontHeight / 2 - fontMetrics.bottom;
    }

    /**
     * this method is used to calculate a rectF for the text that will be drew
     * we add some left-right padding for the text, just for nice
     *
     * @param textX
     * @param textY
     * @param textWidth
     * @return
     */
    protected RectF getTextRectF(float textX, float textY, float textWidth) {
        Paint.FontMetrics metrics = new Paint.FontMetrics();
        mTextPaint.getFontMetrics(metrics);

        RectF rectF = new RectF();
        int rectPadding = (int) dp2Px(2);
        rectF.left = textX - rectPadding;
        rectF.top = textY + metrics.top;
        rectF.right = rectF.left + textWidth + rectPadding * 2;
        rectF.bottom = textY + metrics.bottom;

        return rectF;
    }


    protected float getChartY(float y) {
        int paddingForText = (int) dp2Px(PADDING_FOR_TEXT);
        int height = getContentHeight() - 2 * paddingForText;
        y = (mBaselines[0] - y) / (mBaselines[0] - mBaselines[mBaselines.length - 1]) * height;
        y += getPaddingTop() + paddingForText;
        return y;
    }

    protected float getChartX(int index) {
        int width = getContentWidth();
        return getPaddingLeft() + index * width * 1.0f / mTotalPoints;
    }

    protected int getIndex(float x) {
        int width = getContentWidth();
        x = x - getPaddingLeft();
        return (int) (x * mTotalPoints / width);
    }

    protected String formatNumber(float value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        String pattern = "##0";
        for (int i = 1; i <= mNumberScale; i++) {
            if (i == 1) pattern += ".0";
            else pattern += "0";
        }
        decimalFormat.applyPattern(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        String v = decimalFormat.format(value);
        return v;
    }

    protected float dp2Px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    protected float sp2Px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }

    protected int getContentHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    protected int getContentWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

}