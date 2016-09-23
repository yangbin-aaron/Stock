package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.aaron.myviews.model.Product;
import com.aaron.myviews.model.chart.LightningViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LightningView extends ChartView {

    private static final int INTERVAL_OF_POINTS = 2; //px
    private static final int DEFAULT_PRICE_WIDTH = 27; //dp

    private List<LightningViewModel> mPointList;
    private boolean mNeedDraw;
    private int mMaxPoints;
    private int mDrawingLine;
    private int mRealTimeLines;
    protected Product mProduct;

    public LightningView(Context context) {
        super(context);
        init();
    }

    public LightningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPointList = new ArrayList<>();
        mNeedDraw = true;
    }

    public void setDrawable(boolean needDraw) {
        mNeedDraw = needDraw;
    }

    public void clearData() {
        if (mPointList != null) {
            mPointList.clear();
        } else {
            mPointList = new ArrayList<>();
        }
    }

    protected void setUpPointPaint(Paint paint) {
        paint.setColor(Color.parseColor("#F44306"));
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
    }

    protected void setUpYellowLinePaint(Paint paint) {
        paint.setColor(Color.parseColor("#EAC281"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(1));
        paint.setPathEffect(null);
    }

    protected void setUpBlackTextPaint(Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2Px(8));
    }

    protected void setUpYellowBgPaint(Paint paint) {
        paint.setColor(Color.parseColor("#EAC281"));
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
    }

    private void setUpDashLinePaint(Paint paint) {
        paint.setColor(Color.parseColor("#7D7D7D"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(0.5f));
        paint.setPathEffect(new DashPathEffect(new float[]{3, 8}, 1));
    }

    private void setUpTextPaint(Paint paint) {
        paint.setColor(Color.parseColor("#7D7D7D"));
        paint.setTextSize(sp2Px(9));
    }

    public void addData(LightningViewModel point) {
        if (point != null) {
            synchronized (mPointList) {
                if (mPointList != null) {
                    mPointList.add(point);
                }
                if (mNeedDraw) redraw();
            }
        }
    }

    public void setProduct(Product product) {
        mProduct = product;
        mRealTimeLines = mProduct.isDoubleLines() ? 2: 1;
        setNumberScale(mProduct.getPriceScale());
        setBaselines(mProduct.getBaseline());
    }

    protected void calculateBaseLines() {
        LightningViewModel point = getLastPoint();
        if (mProduct != null && point != null) {
            int areaCount = getBaselines().length - 1;
            float priceRange = mProduct.getPriceInterval() / areaCount;

            if (getFirstBaseLine() == 0 && getLastBaseLine() == 0) { // first
                BigDecimal bigMid = BigDecimal.valueOf(point.getLastPrice())
                        .setScale(mProduct.getPriceScale(), RoundingMode.HALF_EVEN);
                BigDecimal firstBaseLine = bigMid.add(new BigDecimal((areaCount / 2) * priceRange));

                if (getBaselines().length % 2 == 0) {
                    firstBaseLine = firstBaseLine.add(new BigDecimal(priceRange / 2));
                    setBaseLinesFromFirst(firstBaseLine.floatValue(), priceRange);
                } else {
                    setBaseLinesFromFirst(firstBaseLine.floatValue(), priceRange);
                }
                return;
            }

            if (point.getLastPrice() > getFirstBaseLine()) {
                float firstBaseLine = getFirstBaseLine();
                while (point.getLastPrice() > firstBaseLine) {
                    firstBaseLine += priceRange;
                }
                setBaseLinesFromFirst(firstBaseLine, priceRange);
                return;
            }

            if (point.getLastPrice() < getLastBaseLine()) {
                float lastBaseLine = getLastBaseLine();
                while (point.getLastPrice() < lastBaseLine) {
                    lastBaseLine -= priceRange;
                }
                setBaselinesFromLast(lastBaseLine, priceRange);
                return;
            }
        }
    }

    public float getFirstBaseLine() {
        return getBaselines()[0];
    }

    public float getLastBaseLine() {
        return getBaselines()[getBaselines().length - 1];
    }

    protected void setBaseLinesFromFirst(float firstBaseLine, float priceRange) {
        float[] baseLines = getBaselines();
        baseLines[0] = firstBaseLine;
        for (int i = 1; i < baseLines.length; i++) {
            baseLines[i] = BigDecimal.valueOf(baseLines[i - 1])
                    .subtract(new BigDecimal(priceRange))
                    .floatValue();
        }
    }

    protected void setBaselinesFromLast(float lastBaseLine, float priceRange) {
        float[] baseLines = getBaselines();
        baseLines[baseLines.length - 1] = lastBaseLine;
        for (int i = baseLines.length - 2; i >= 0; i--) {
            baseLines[i] = BigDecimal.valueOf(baseLines[i + 1])
                    .add(new BigDecimal(priceRange))
                    .floatValue();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = 2 * (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 3;
        mMaxPoints = maxWidth / INTERVAL_OF_POINTS + 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculateBaseLines();

        super.onDraw(canvas);
    }

    @Override
    protected void drawBaseLines(Canvas canvas) {
        setUpDashLinePaint(mPaint);
        int paddingForText = heightForText();

        int height = getMainHeight() - 2 * paddingForText;
        int width = getNoPaddingWidth();

        float startX = 0 + getPaddingLeft();
        float startY = 0 + getPaddingTop() + paddingForText;

        float[] baseLines = getBaselines();
        int verticalInterval = height / (baseLines.length - 1);
        for (int i = 0; i < baseLines.length; i++) {
            Path path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(startX + width - dp2Px(DEFAULT_PRICE_WIDTH), startY);
            canvas.drawPath(path, mPaint);

            setUpTextPaint(mTextPaint);
            float textWidth = mTextPaint.measureText(formatNumber(baseLines[i]));
            canvas.drawText(formatNumber(baseLines[i]), startX + width - textWidth,
                    startY + offsetY4TextCenter(), mTextPaint);
            startY += verticalInterval;
        }
    }

    @Override
    protected void drawRealTimeLines(Canvas canvas) {
        for (int i = 0; i < mRealTimeLines; i++) {
            mDrawingLine = i;
            drawRealTimeLine(canvas);
        }
    }

    @Override
    protected void drawTouchLines(Canvas canvas) {
    }

    private void drawRealTimeLine(Canvas canvas) {
        int width = getNoPaddingWidth();

        int pointListSize = mPointList.size();
        Path path = new Path();
        float chartX = getChartX(0);
        float chartY = getChartY(0);
        int pointIndex = 0;
        float currentPrice = 0;
        int offset = Math.max(pointListSize - mMaxPoints, 0);
        for (int i = 0; i < pointListSize && i < mMaxPoints; i++) {
            pointIndex = i + offset;
            currentPrice = getCurrentPrice(mPointList.get(pointIndex));
            chartX = getChartX(i);
            chartY = getChartY(currentPrice);
            if (i == 0) {
                path.moveTo(chartX, chartY);
            } else {
                path.lineTo(chartX, chartY);
            }
        }

        // draw black current price text with yellow background
        setUpBlackTextPaint(mTextPaint);
        Paint.FontMetrics metrics = new Paint.FontMetrics();
        mTextPaint.getFontMetrics(metrics);
        float priceTextWidth = mTextPaint.measureText(formatNumber(currentPrice));
        float priceTextDrawX = getPaddingLeft() + width - priceTextWidth - dp2Px(2);
        float priceTextDrawY = chartY + offsetY4TextCenter();
        // draw yellow rect for current price text
        setUpYellowBgPaint(mPaint);
        RectF rectF = getTextRectF(priceTextDrawX, priceTextDrawY, priceTextWidth);
        canvas.drawRoundRect(rectF, 5, 5, mPaint);
        canvas.drawText(formatNumber(currentPrice),
                priceTextDrawX, priceTextDrawY, mTextPaint);

        // draw yellow horizontal line to connect rect
        setUpYellowLinePaint(mPaint);
        path.lineTo(priceTextDrawX, chartY);
        canvas.drawPath(path, mPaint);

        // draw red circle split point
        setUpPointPaint(mPaint);
        canvas.drawCircle(chartX + 3, chartY, 4, mPaint);
    }

    @Override
    protected float getChartX(int index) {
        return index * INTERVAL_OF_POINTS + getPaddingLeft();
    }

    protected float getCurrentPrice(LightningViewModel point) {
        return point.getLastPrice();
    }

    public LightningViewModel getLastPoint() {
        if (mPointList != null && mPointList.size() > 0) {
            return mPointList.get(mPointList.size() - 1);
        }
        return null;
    }

    protected int getDrawingLine() {
        return mDrawingLine;
    }
}
