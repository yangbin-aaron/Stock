package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class ChartView extends View {

    protected enum ChartColor {

        GRAY("#B3B2B3"),
        GREEN("#08BA42"),
        RED("#F3293B"),
        BLUE("#009CE3"),
        ORANGE("#F27A00"),
        PURPLE("#AE4085"),
        YELLOW("#EAC281");

        private String value;

        ChartColor(String color) {
            value = color;
        }

        public String get() {
            return value;
        }
    }

    private enum Action {
        NONE,
        DRAG,
        PRESSED,
        ZOOM;
    }

    private static final int HEIGHT_FOR_TEXT = 18; //dp
    private static final int AREA_FOR_TEXT_0 = 0; //dp

    private static final int WHAT_PRESSED = 1;
    private static final int DELAY = 300;

    private Handler mHandler;

    protected Paint mPaint;
    protected Paint mTextPaint;

    private float[] mBaselines;
    private float[] mReferenceBaseLines;
    private int mTotalPoints;
    private int mNumberScale;
    private boolean mReferenceIndexEnable;
    private PointF mTouchPoint;

    private Action mAction;
    private float mTransactionX;
    private float mPreviousTransactionX;
    private float mStartX;
    private boolean mDragged;
    private boolean mTouched;

    protected boolean mIsLoadingData;
    protected boolean mLoadDataFailed;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = createHandler();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBaselines = new float[4];

        mIsLoadingData = false;
        mLoadDataFailed = false;
    }

    protected Handler createHandler() {
        return new ChartHandler(this);
    }

    private class ChartHandler extends Handler {

        private ChartView chartView;

        public ChartHandler(ChartView chartView) {
            this.chartView = chartView;
        }

        @Override
        public void handleMessage(Message msg) {
            chartView.getParent().requestDisallowInterceptTouchEvent(true);
            mTouched = true;
            setTouchPoint((MotionEvent) msg.obj);
        }
    }

    public void setNumberScale(int numberScale) {
        mNumberScale = numberScale;
    }

    public void setTotalPoints(int totalPoints) {
        mTotalPoints = totalPoints;
    }

    public int getTotalPoints() {
        return mTotalPoints;
    }

    public void setBaselines(int baselines) {
        if (baselines < 2) baselines = 2;
        mBaselines = new float[baselines];
    }

    public void setReferenceBaseLines(int referenceBaseLines) {
        if (referenceBaseLines < 2) referenceBaseLines = 2;
        mReferenceBaseLines = new float[referenceBaseLines];
    }

    public void setReferenceIndexEnable(boolean enable) {
        mReferenceIndexEnable = enable;
    }

    public float[] getBaselines() {
        return mBaselines;
    }

    public float[] getReferenceBaseLines() {
        return mReferenceBaseLines;
    }

    public PointF getTouchPoint() {
        return mTouchPoint;
    }

    public float getTransX() {
        return mTransactionX;
    }

    public void restTransX() {
        mTransactionX = 0;
        mPreviousTransactionX = 0;
    }

    public void showDataLoadingView() {
        mIsLoadingData = true;
        redraw();
    }

    public void showDataLoadFailedView() {
        mIsLoadingData = false;
        mLoadDataFailed = true;
        redraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsLoadingData) {
            mTextPaint.setColor(Color.parseColor(ChartColor.GRAY.get()));
            mTextPaint.setTextSize(sp2Px(12));
            String loadingText = "正在努力加载行情图...";
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int textWidth = (int) mTextPaint.measureText(loadingText);
            canvas.drawText(loadingText, centerX - textWidth / 2, centerY, mTextPaint);
            return;
        }

        if (mLoadDataFailed) {
            mTextPaint.setColor(Color.parseColor(ChartColor.GRAY.get()));
            mTextPaint.setTextSize(sp2Px(12));
            String loadingText = "暂无数据显示";
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int textWidth = (int) mTextPaint.measureText(loadingText);
            canvas.drawText(loadingText, centerX - textWidth / 2, centerY, mTextPaint);
            return;
        }

        clearAll(canvas);
        drawBaseLines(canvas);
        drawRealTimeLines(canvas);
        drawTouchLines(canvas);
    }

    protected void clearAll(Canvas canvas) {
    }

    protected abstract void drawBaseLines(Canvas canvas);

    protected abstract void drawRealTimeLines(Canvas canvas);

    protected abstract void drawTouchLines(Canvas canvas);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Message message = mHandler.obtainMessage(WHAT_PRESSED, event);
                mHandler.sendMessageDelayed(message, DELAY);

                mAction = Action.DRAG;
                mStartX = event.getX() - mPreviousTransactionX;

                return true;
            case MotionEvent.ACTION_MOVE:
                if (mTouched) {
                    if (shouldUpdate(event)) {
                        setTouchPoint(event);
                        redraw();
                        return true;
                    }
                } else {
                    double distance = Math.abs(event.getX() - (mStartX + mPreviousTransactionX));
                    if (distance > getChartX(1)) {
                        mHandler.removeMessages(WHAT_PRESSED);
                        mDragged = true;
                        mTransactionX = event.getX() - mStartX;
                    }
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mHandler.removeMessages(WHAT_PRESSED);
                mAction = Action.NONE;
                mDragged = false;
                mTouched = false;
                mPreviousTransactionX = mTransactionX;
                mTouchPoint = null;
                redraw();
                return true;
        }

        if ((false && mAction == Action.DRAG && mDragged)) {

            if (mTransactionX < 0) {
                mTransactionX = 0;
                mPreviousTransactionX = 0;
                return true;
            }

            redraw();
        }
        return false;
    }

    protected boolean shouldUpdate(MotionEvent event) {
        return event != null;
    }

    private void setTouchPoint(MotionEvent event) {
        if (mTouchPoint == null) {
            mTouchPoint = new PointF();
        }
        mTouchPoint.x = event.getX();
        mTouchPoint.y = event.getY();
    }

    protected void redraw() {
        postInvalidate();
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
        int heightForText = (int) dp2Px(HEIGHT_FOR_TEXT);
        int height = getMainHeight() - 2 * heightForText;
        y = (mBaselines[0] - y) / (mBaselines[0] - mBaselines[mBaselines.length - 1]) * height;
        y += getPaddingTop() + heightForText;
        return y;
    }

    protected float getReferenceChartY(float y) {
        int heightForText = (int) dp2Px(HEIGHT_FOR_TEXT);
        int height = getReferenceHeight() - 3 * heightForText;
        y = (mReferenceBaseLines[0] - y) /
                (mReferenceBaseLines[0] - mReferenceBaseLines[mReferenceBaseLines.length - 1]) * height;
        y += getPaddingTop() + getMainHeight() + 2 * heightForText;
        return y;
    }

    protected float getChartX(int index) {
        int width = getNoPaddingWidth();
        return getPaddingLeft() + index * width * 1.0f / mTotalPoints;
    }

    protected int getIndexOfTotalPoints(float x) {
        int width = getNoPaddingWidth();
        x = x - getPaddingLeft();
        return (int) (x * mTotalPoints / width);
    }

    protected String formatNumber(float value) {
        return formatNumber(value, mNumberScale);
    }

    protected String formatNumber(float value, int numberScale) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        String pattern = "##0";
        for (int i = 1; i <= numberScale; i++) {
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

    protected int heightForText() {
        return (int) dp2Px(HEIGHT_FOR_TEXT);
    }

    /**
     * 返回view的宽度，去除了padding
     * @return
     */
    protected int getNoPaddingWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 返回view的高度，去除了padding
     * @return
     */
    protected int getNoPaddingHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 返回view的left，去除了padding
     * @return
     */
    protected int getNoPaddingLeft() {
        return 0 + getPaddingLeft();
    }

    /**
     * 返回view的top，去除了padding
     * @return
     */
    protected int getNoPaddingTop() {
        return 0 + getPaddingTop();
    }

    /**
     * 返回view的right，去除了padding
     * @return
     */
    protected int getNoPaddingRight() {
        return getNoPaddingLeft() + getNoPaddingWidth();
    }

    /**
     * 返回view的bottom，去除了padding
     * @return
     */
    protected int getNoPaddingBottom() {
        return getNoPaddingTop() + getNoPaddingHeight();
    }

    /**
     * 获取图表的主要部分高度，当 setReferenceIndexEnable(true) 时候会包含参考图表部分
     * @return 主要部分高度
     */
    protected int getMainHeight() {
        int height = getNoPaddingHeight();
        if (mReferenceIndexEnable) {
            return (int) (height * 0.67f);
        }
        return height;
    }

    /**
     * 当 setReferenceIndexEnable(true) 返回参考图表高度，不然返回0
     * @return
     */
    protected int getReferenceHeight() {
        return getNoPaddingHeight() - getMainHeight();
    }
}