package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aaron.myviews.model.Product;
import com.aaron.myviews.model.chart.TrendViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendView extends ChartView {

    private List<TrendViewModel> mModelList;
    private TrendViewModel mFloatingModel;
    private Map<Integer, TrendViewModel> mVisibleModelList;

    private Product mProduct;
    private double mPreClosePrice;
    private float mMinRange;
    private boolean mIsAtNight;

    public TrendView(Context context) {
        super(context);
        init();
    }

    public TrendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mVisibleModelList = new HashMap<>();
    }

    protected void setUpPointPaint(Paint paint) {
        paint.setColor(Color.parseColor("#F44306"));
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
    }

    private void setUpDashPaint(Paint paint) {
        paint.setColor(Color.parseColor("#B3B2B3"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(0.5f));
        paint.setPathEffect(new DashPathEffect(new float[]{3, 8}, 1));
    }

    private void setUpTouchLinePaint(Paint paint) {
        paint.setColor(Color.parseColor("#7F7F7F"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(1));
        paint.setPathEffect(null);
    }

    private void setUpRealTimeLinePaint(Paint paint) {
        paint.setColor(Color.parseColor("#EAC281"));
        paint.setStrokeWidth(dp2Px(1));
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }

    protected void setUpYellowBgPaint(Paint paint) {
        paint.setColor(Color.parseColor("#EAC281"));
        paint.setStyle(Paint.Style.FILL);
    }

    protected void setUpBlackTextPaint(Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2Px(8));
    }

    private void setUpTextPaint(Paint paint) {
        paint.setColor(Color.parseColor("#B3B2B3"));
        paint.setTextSize(sp2Px(9));
    }

    private void setUpTimeLineTextPaint(Paint paint) {
        paint.setColor(Color.parseColor("#EAC281"));
        paint.setTextSize(sp2Px(7));
    }

    public void setProduct(Product product) {
        mProduct = product;
        setNumberScale(mProduct.getPriceScale());
        setBaselines(mProduct.getBaseline());
    }

    public void setPreClosePrice(Double preClosePrice) {
        if (preClosePrice != null) {
            mPreClosePrice = preClosePrice.doubleValue();
        } else {
            mPreClosePrice = 0;
        }
    }

    public Double getPreClosePrice() {
        return Double.valueOf(mPreClosePrice);
    }

    public void setChartModels(List<TrendViewModel> modelList) {
        mModelList = modelList;

        redraw();
    }

    public void addFloatingPrice(Double lastPrice) {
        if (mModelList != null && mModelList.size() > 0) {
            TrendViewModel lastModel = mModelList.get(mModelList.size() - 1);
            mFloatingModel = new TrendViewModel(lastModel, lastPrice);
            if (mProduct != null && mProduct.isValidDate(mFloatingModel.getDate())) {
                redraw();
            } else {
                mFloatingModel = null;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        determineMinimumRange();
        calculateBaselines();
        determineTotalPoints();

        super.onDraw(canvas);

        drawTimeLine(canvas);
    }

    private void determineMinimumRange() {
        if (mMinRange == 0 && mProduct != null) {
            double multiplicand = mProduct.getMinimumRangeFactor();
            mMinRange = new BigDecimal(mPreClosePrice)
                    .multiply(new BigDecimal(multiplicand))
                    .floatValue();
        }
    }

    private void calculateBaselines() {
        if (mModelList != null && mModelList.size() > 0) {
            mIsAtNight = false;
            int size = mModelList.size();
            float max = Float.MIN_VALUE;
            float min = Float.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                TrendViewModel model = mModelList.get(i);
                if (max < model.getLastPrice()) max = model.getLastPrice();
                if (min > model.getLastPrice()) min = model.getLastPrice();

                if (mProduct.getNightPointsNumber() > 0 && mProduct.isNightData(model)) {
                    mIsAtNight = true;
                }
            }

            if (mFloatingModel != null) {
                if (max < mFloatingModel.getLastPrice()) max = mFloatingModel.getLastPrice();
                if (min > mFloatingModel.getLastPrice()) min = mFloatingModel.getLastPrice();
            }

            // the chart need a min height
            float delta = new BigDecimal(max).subtract(new BigDecimal(min)).floatValue();
            if (delta < mMinRange) {
                max = new BigDecimal(min).add(new BigDecimal(mMinRange)).floatValue();
            }

            float[] baseLines = getBaselines();
            float priceRange = BigDecimal.valueOf(max).subtract(new BigDecimal(min))
                    .divide(new BigDecimal(baseLines.length - 1), RoundingMode.HALF_EVEN).floatValue();

            baseLines[0] = max;
            baseLines[baseLines.length - 1] = min;
            for (int i = baseLines.length - 2; i > 0; i--) {
                baseLines[i] = baseLines[i + 1] + priceRange;
            }
        }
    }

    private void determineTotalPoints() {
        if (mProduct != null) {
            setTotalPoints(mIsAtNight?
                    mProduct.getNightPointsNumber():
                    mProduct.getDayPointsNumber());
        }
    }

    @Override
    protected void drawBaseLines(Canvas canvas) {
        int paddingForText = heightForText();

        int height = getMainHeight() - 2 * paddingForText;
        int width = getNoPaddingWidth();

        float startX = 0 + getPaddingLeft();
        float startY = 0 + getPaddingTop() + paddingForText;

        setUpDashPaint(mPaint);
        float[] baseLines = getBaselines();
        int verticalInterval = height / (baseLines.length - 1);
        for (int i = 0; i < baseLines.length; i++) {
            Path path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(startX + width, startY);
            canvas.drawPath(path, mPaint);

            setUpTextPaint(mTextPaint);
            float textWidth = mTextPaint.measureText(formatNumber(baseLines[i]));
            canvas.drawText(formatNumber(baseLines[i]), startX + width - textWidth,
                    startY - offsetY4TextCenter(), mTextPaint);
            startY += verticalInterval;
        }
    }

    @Override
    protected void drawRealTimeLines(Canvas canvas) {
        if (mModelList != null && mModelList.size() > 0) {
            int size = mModelList.size();
            Path path = new Path();
            float chartX = getChartX(0);
            float chartY = getChartY(0);
            for (int i = 0; i < size; i++) {
                chartX = getChartX(mModelList.get(i));
                chartY = getChartY(mModelList.get(i).getLastPrice());
                if (i == 0) {
                    path.moveTo(chartX, chartY);
                } else {
                    path.lineTo(chartX, chartY);
                }
            }

            if (mFloatingModel != null) {
                chartX = getChartX(mFloatingModel);
                chartY = getChartY(mFloatingModel.getLastPrice());
                path.lineTo(chartX, chartY);
            }

            setUpRealTimeLinePaint(mPaint);
            canvas.drawPath(path, mPaint);

            setUpPointPaint(mPaint);
            canvas.drawCircle(chartX, chartY, 4, mPaint);
        }
    }

    private float getChartX(TrendViewModel model) {
        if (mProduct != null) {
            int indexOfTotalPoints
                    = mProduct.getIndexFromDate(model.getHhmmDate(), mIsAtNight);

            if (mVisibleModelList != null) {
                mVisibleModelList.put(indexOfTotalPoints, model);
            }

            return getChartX(indexOfTotalPoints);
        }
        return 0;
    }

    @Override
    protected boolean shouldUpdate(MotionEvent event) {
        int index = getIndexOfTotalPoints(event.getX());
        if (mVisibleModelList != null) {
            return mVisibleModelList.containsKey(index);
        }
        return false;
    }

    @Override
    protected void drawTouchLines(Canvas canvas) {
        PointF touchPoint = getTouchPoint();
        if (touchPoint != null) {
            float touchX = touchPoint.x;
            int index = getIndexOfTotalPoints(touchX);
            if (mVisibleModelList == null || !mVisibleModelList.containsKey(index)) return;

            TrendViewModel model = mVisibleModelList.get(index);
            float touchY = getChartY(model.getLastPrice());

            int paddingForText = heightForText();
            int height = getMainHeight() - 2 * paddingForText;
            int width = getNoPaddingWidth();

            // draw model.date connected to vertical line
            String date = model.getDate();
            if (date.length() == 14) {
                setUpBlackTextPaint(mTextPaint);
                date = date.substring(8, 10) + ":" + date.substring(10, 12);
                float dateY = 0 + getPaddingTop() + paddingForText / 2 + offsetY4TextCenter();
                float textWidth = mTextPaint.measureText(date);
                float dateX = touchX - textWidth / 2;

                // when date string touches the borders, add offset
                dateX = dateX < 0 ? 0 + dp2Px(2) : dateX;
                dateX = dateX > getNoPaddingWidth() - textWidth ?
                        getNoPaddingWidth() - textWidth - dp2Px(2) : dateX;

                // draw rectangle yellow background for date
                RectF rectF = getTextRectF(dateX, dateY, textWidth);
                setUpYellowBgPaint(mPaint);
                canvas.drawRoundRect(rectF, 5, 5, mPaint);
                canvas.drawText(date, dateX, dateY, mTextPaint);

                // draw vertical line
                setUpTouchLinePaint(mPaint);
                float topY = 0 + getPaddingTop() + paddingForText / 2  + rectF.height() / 2; // connected to rect
                float bottomY = 0 + height + paddingForText;
                Path verticalPath = new Path();
                verticalPath.moveTo(touchX, topY);
                verticalPath.lineTo(touchX, bottomY);
                canvas.drawPath(verticalPath, mPaint);
            }

            // draw model.lastPrice connected to horizontal line
            if (model.getLastPrice() != null) {
                String price = formatNumber(model.getLastPrice());
                float textWidth = mTextPaint.measureText(price);
                float priceX = 0 + getPaddingLeft() + width - textWidth - dp2Px(2);
                float priceY = touchY + offsetY4TextCenter();

                // when touchX is larger than half of width, move price to left
                if (touchX > width / 2) {
                    priceX = 0 + getPaddingLeft() + dp2Px(2);
                }

                RectF rectF = getTextRectF(priceX, priceY, textWidth);
                setUpYellowBgPaint(mPaint);
                canvas.drawRoundRect(rectF, 5, 5, mPaint);
                canvas.drawText(price, priceX, priceY, mTextPaint);

                // draw horizontal line
                setUpTouchLinePaint(mPaint);
                float leftX = 0 + getPaddingLeft();
                float rightX = leftX + width - rectF.width(); // connected to rect

                // when touchX is larger than half of width, add offset for line.x
                if (touchX > width / 2) {
                    leftX = leftX + rectF.width();
                    rightX = rightX + rectF.width();
                }

                Path horizontalPath = new Path();
                horizontalPath.moveTo(leftX, touchY);
                horizontalPath.lineTo(rightX, touchY);
                canvas.drawPath(horizontalPath, mPaint);
            }
        }
    }

    private void drawTimeLine(Canvas canvas) {
        if (mProduct != null && mModelList != null && mModelList.size() > 0) {
            setUpTimeLineTextPaint(mTextPaint);
            int paddingForText = heightForText();
            int width = getNoPaddingWidth();
            float startY = getMainHeight() - paddingForText + dp2Px(14); // add some margin

            String[] times = mProduct.getTimeLine(mIsAtNight);
            if (times != null && times.length > 0) {
                float textWidth = mTextPaint.measureText(times[0]);
                for (int i = 0; i < times.length; i++) {
                    if (i == 0) {
                        canvas.drawText(times[i], getChartX(0), startY, mTextPaint);
                    } else if (i == times.length - 1) {
                        canvas.drawText(times[i], getChartX(0) + width - textWidth, startY, mTextPaint);
                    } else {
                        int indexOfTime = mProduct.getIndexFromDate(times[i], mIsAtNight);
                        canvas.drawText(times[i], getChartX(indexOfTime) - textWidth/2, startY, mTextPaint);
                    }
                }
            }
        }
    }
}
