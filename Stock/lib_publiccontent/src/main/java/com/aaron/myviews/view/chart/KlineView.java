package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aaron.myviews.greendao.KlineModel;
import com.aaron.myviews.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KlineView extends ChartView {

    private static final int CANDLES = 40;
    private static final int CANDLES_WIDTH = 6; //dp

    public enum Measure {

        ONE(1),
        THREE(3),
        FIVE(5),
        FIFTEEN(15),
        DAY(1);

        private int value;

        Measure(int value) {
            this.value = value;
        }

        public int get() {
            return value;
        }
    }

    public enum ReferenceIndex {
        NONE,
        VOL;
    }

    private List<KlineModel> mModelList;
    private List<ProcessedKlineModel> mProcessedModelList;
    private Map<Integer, ProcessedKlineModel> mVisibleModelList;
    private OnTouchLineDrawListener mOnTouchLineDrawListener;
    private int mTouchIndex;

    private Product mProduct;
    private ReferenceIndex mReferenceIndex;
    private Measure mMeasure;
    private int[] mMaArg;
    private int mMaxVolume;

    public interface OnTouchLineDrawListener {
        void onDraw(ProcessedKlineModel klineModel, boolean visible);
    }

    public static class ProcessedKlineModel {
        public String startTime;
        public KlineModel klineModel;
        public float[] ma;
        public float[] volMa;

        public ProcessedKlineModel(String startTime) {
            this.startTime = startTime;
            this.ma = new float[3];
            this.volMa = new float[3];
        }

        public ProcessedKlineModel(KlineModel klineModel) {
            this.klineModel = klineModel;
            this.ma = new float[3];
            this.volMa = new float[3];
        }

        public void setKlineModel(KlineModel klineModel) {
            this.klineModel = klineModel;
        }
    }

    public KlineView(Context context) {
        super(context);
        init();
    }

    public KlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mProcessedModelList = new ArrayList<>();
        mVisibleModelList = new HashMap<>();
        mTouchIndex = -1;
        mMeasure = Measure.ONE;
        mReferenceIndex = ReferenceIndex.VOL;
        mMaArg = new int[] {5, 10, 20};
        setTotalPoints(CANDLES);
        setBaselines(4);
        setReferenceBaseLines(3);
        setReferenceIndexEnable(true);
    }

    public void setOnTouchLineDrawListener(OnTouchLineDrawListener listener) {
        mOnTouchLineDrawListener = listener;
    }

    public void setKlineModels(List<KlineModel> modelList) {
        mModelList = modelList;

        new KlineDataProcessTask().execute();
    }

    private class KlineDataProcessTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            processModelList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mLoadDataFailed = false;
            mIsLoadingData = false;
            redraw();
        }

        private void processModelList() {
            if (mModelList != null) {

                mProcessedModelList.clear();
                mVisibleModelList.clear();

                for (int i = 0; i < mModelList.size(); i += mMeasure.get()) {
                    int first = i;
                    int last = i + mMeasure.get() - 1;
                    if (last >= mModelList.size()) {
                        last = mModelList.size() - 1;
                    }

                    if (first == last) {
                        mProcessedModelList.add(new ProcessedKlineModel(mModelList.get(first)));
                        continue;
                    }

                    KlineModel firstData = mModelList.get(first);
                    KlineModel lastData = mModelList.get(last);

                    ProcessedKlineModel processedKlineModel =
                            new ProcessedKlineModel(firstData.getTime());

                    KlineModel model = new KlineModel();
                    model.setOpenPrice(firstData.getOpenPrice());
                    model.setClosePrice(lastData.getClosePrice());
                    model.setTime(lastData.getTime());

                    model.setMaxPrice(Float.MIN_VALUE);
                    model.setMinPrice(Float.MAX_VALUE);
                    int volume = 0;
                    for (int j = first; j <= last; j++) {
                        KlineModel middleModel = mModelList.get(j);
                        if (model.getMaxPrice() < middleModel.getMaxPrice()) {
                            model.setMaxPrice(middleModel.getMaxPrice());
                        }

                        if (model.getMinPrice() > middleModel.getMinPrice()) {
                            model.setMinPrice(middleModel.getMinPrice());
                        }

                        volume += middleModel.getVolume();
                    }
                    model.setVolume(volume);

                    processedKlineModel.setKlineModel(model);
                    mProcessedModelList.add(processedKlineModel);
                }
                calculateMovingAverages();
            }
        }

        private void calculateMovingAverages() {
            if (mProcessedModelList != null && mProcessedModelList.size() > 0) {
                int size = mProcessedModelList.size();
                for (int i = 0; i < size; i++) {
                    ProcessedKlineModel processedKlineModel = mProcessedModelList.get(i);
                    for (int j = 0; j < mMaArg.length; j++) {
                        processedKlineModel.ma[j] = calculateMovingAverage(i, mMaArg[j]);
                        processedKlineModel.volMa[j] = calculateMovingAverage(i, mMaArg[j], ReferenceIndex.VOL);
                    }
                }
            }
        } //calculateMovingAverages()

    }

    public KlineModel getTheLastModel() {
        if (mModelList != null && mModelList.size() > 0) {
            return mModelList.get(mModelList.size() - 1);
        }
        return null;
    }

    public Measure getChartType() {
        return mMeasure;
    }

    public void setChartType(Measure measure) {
        mMeasure = measure;
    }

    public void notifyChartTypeChanged() {
        new KlineDataProcessTask().execute();
    }

    public void setProduct(Product product) {
        mProduct = product;
        setNumberScale(mProduct.getPriceScale());
    }

    private void setUpDashPaint(Paint paint) {
        paint.setColor(Color.parseColor(ChartColor.GRAY.get()));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(0.5f));
        paint.setPathEffect(new DashPathEffect(new float[]{3, 8}, 1));
    }

    private void setUpTextPaint(Paint paint) {
        paint.setColor(Color.parseColor(ChartColor.GRAY.get()));
        paint.setTextSize(sp2Px(9));
    }

    private void setUpMovingAverageTextPaint(Paint paint, int which) {
        String color = ChartColor.BLUE.get();
        if (which == 1) {
            color = ChartColor.ORANGE.get();
        } else if (which == 2) {
            color = ChartColor.PURPLE.get();
        }
        paint.setColor(Color.parseColor(color));
        paint.setTextSize(sp2Px(9));
    }

    private void setUpTimeLineTextPaint(Paint paint) {
        paint.setColor(Color.parseColor(ChartColor.GRAY.get()));
        paint.setTextSize(sp2Px(8));
    }

    private void setUpYearTimeLineTextPaint(Paint paint) {
        paint.setColor(Color.parseColor(ChartColor.YELLOW.get()));
        paint.setTextSize(sp2Px(8));
    }

    private void setUpMovingAveragePaint(Paint paint, int which) {
        String color = ChartColor.BLUE.get();
        if (which == 1) {
            color = ChartColor.ORANGE.get();
        } else if (which == 2) {
            color = ChartColor.PURPLE.get();
        }
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(0.5f));
        paint.setPathEffect(null);
    }

    private void setUpCandleLinePaint(Paint paint, String color) {
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(1));
        paint.setPathEffect(null);
    }

    private void setUpCandleBodyPaint(Paint paint, String color) {
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
    }

    private void setUpTouchLinePaint(Paint paint) {
        paint.setColor(Color.parseColor("#7F7F7F"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(1));
        paint.setPathEffect(null);
    }

    protected void setUpYellowBgPaint(Paint paint) {
        paint.setColor(Color.parseColor("#80EAC281"));
        paint.setStyle(Paint.Style.FILL);
    }

    protected void setUpBlackTextPaint(Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2Px(8));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculateBaselines();
        calculateReferenceBaseLines();

        super.onDraw(canvas);

        if (mIsLoadingData || mLoadDataFailed) return;
        
        drawTimeLine(canvas);
        drawMovingAverageTexts(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    private int getOffsetCount() {
        return (int) (getTransX() / super.getChartX(1));
    }

    private int getIndexOffset() {
        if (mProcessedModelList != null) {
            int size = mProcessedModelList.size();
            return size - CANDLES;
        }
        return 0;
    }


    private float calculateMovingAverage(int start, int maArg) {
        return calculateMovingAverage(start, maArg, ReferenceIndex.NONE);
    }

    private float calculateMovingAverage(int start, int maArg, ReferenceIndex referenceIndex) {
        if (start < maArg - 1) return -1;

        float result = 0;
        for (int i = 0; i < maArg; i++) {
            int position = start - i;
            if (referenceIndex == ReferenceIndex.NONE) {
                result += mProcessedModelList.get(position).klineModel.getClosePrice();
            } else if (referenceIndex == ReferenceIndex.VOL) {
                result += mProcessedModelList.get(position).klineModel.getVolume();
            }
        }
        return result / maArg;
    }

    private void calculateBaselines() {
        if (mProcessedModelList != null && mProcessedModelList.size() > 0) {
            int size = mProcessedModelList.size();
            float max = Float.MIN_VALUE;
            float min = Float.MAX_VALUE;
            mMaxVolume = Integer.MIN_VALUE;
            int offset = Math.max(getIndexOffset(), 0);
            for (int i = 0; i < size && i < CANDLES; i++) {
                KlineModel model = mProcessedModelList.get(i + offset).klineModel;
                if (max < model.getMaxPrice()) max = model.getMaxPrice();
                if (min > model.getMinPrice()) min = model.getMinPrice();
                if (mMaxVolume < model.getVolume()) mMaxVolume = model.getVolume();
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

    private void calculateReferenceBaseLines() {
        if (mReferenceIndex == ReferenceIndex.VOL) {
            float[] rBaselines = getReferenceBaseLines();
            int volumeRange = mMaxVolume / (rBaselines.length - 1);
            rBaselines[0] = mMaxVolume;
            rBaselines[rBaselines.length - 1] = 0;
            for (int i = rBaselines.length - 2; i > 0; i--) {
                rBaselines[i] = rBaselines[i + 1] + volumeRange;
            }
        }
    }

    @Override
    protected void drawBaseLines(Canvas canvas) {
        int paddingForText = heightForText();

        int height = getMainHeight() - 2 * paddingForText;
        int width = getNoPaddingWidth();

        float startX = getNoPaddingLeft();
        float startY = getNoPaddingTop() + paddingForText;

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

        drawReferenceIndexBaseLines(canvas);
    }

    private void drawReferenceIndexBaseLines(Canvas canvas) {
        int heightForText = heightForText();

        int height = getReferenceHeight() - 3 * heightForText;
        int width = getNoPaddingWidth();

        float startX = getNoPaddingLeft();
        float startY = getNoPaddingTop() + getMainHeight() + 2 * heightForText;

        if (mReferenceIndex == ReferenceIndex.VOL) {
            setUpDashPaint(mPaint);
            float[] rBaselines = getReferenceBaseLines();
            int verticalInterval = height / (rBaselines.length - 1);
            for (int i = 0; i < rBaselines.length; i++) {
                Path path = new Path();
                path.moveTo(startX, startY);
                path.lineTo(startX + width, startY);
                canvas.drawPath(path, mPaint);

                setUpTextPaint(mTextPaint);
                String volume = String.valueOf((int) rBaselines[i]);
                float textWidth = mTextPaint.measureText(volume);
                canvas.drawText(formatNumber(rBaselines[i]), startX + width - textWidth,
                        startY - offsetY4TextCenter(), mTextPaint);
                startY += verticalInterval;
            }
        }
    }

    private void drawTimeLine(Canvas canvas) {
        if (mProcessedModelList != null && mProcessedModelList.size() > 0) {
            int size = mProcessedModelList.size();
            int offset = Math.max(getIndexOffset(), 0);
            int paddingForText = heightForText();
            float startY = getMainHeight() - paddingForText + dp2Px(8); // add some margin
            float startY2 = startY + dp2Px(9);

            for (int i = 0; i < size && i < CANDLES; i++) {
                ProcessedKlineModel processedKlineModel = mProcessedModelList.get(i + offset);
                if (i == 0) {
                    setUpTimeLineTextPaint(mTextPaint);
                    String intervalTime = formatIntervalTime(processedKlineModel.klineModel.getTime());
                    canvas.drawText(intervalTime, super.getChartX(0), startY, mTextPaint);
                    setUpYearTimeLineTextPaint(mTextPaint);
                    String yearTime = formatYearTime(processedKlineModel.klineModel.getTime());
                    canvas.drawText(yearTime, super.getChartX(0), startY2, mTextPaint);
                } else if (i == CANDLES - 1) {
                    setUpTimeLineTextPaint(mTextPaint);
                    String intervalTime = formatIntervalTime(processedKlineModel.klineModel.getTime());
                    float textWidth = mTextPaint.measureText(intervalTime);
                    canvas.drawText(intervalTime, super.getChartX(CANDLES) - textWidth, startY, mTextPaint);
                    setUpYearTimeLineTextPaint(mTextPaint);
                    String yearTime = formatYearTime(processedKlineModel.klineModel.getTime());
                    textWidth = mTextPaint.measureText(yearTime);
                    canvas.drawText(yearTime, super.getChartX(CANDLES) - textWidth, startY2, mTextPaint);
                } else if (i != 0 && i % 10 == 0) {
                    setUpTimeLineTextPaint(mTextPaint);
                    String intervalTime = formatIntervalTime(processedKlineModel.klineModel.getTime());
                    float textWidth = mTextPaint.measureText(intervalTime);
                    canvas.drawText(intervalTime, getChartX(i) - textWidth / 2, startY, mTextPaint);
                }
            }
        }
    }

    private void drawMovingAverageTexts(Canvas canvas) {
        if (mProcessedModelList != null && mProcessedModelList.size() > 0) {
            int lastIndex = CANDLES - 1;
            if (mProcessedModelList.size() < CANDLES) {
                lastIndex = mProcessedModelList.size() - 1;
            }
            ProcessedKlineModel model = mProcessedModelList.get(lastIndex);

            if (getTouchPoint() != null && mVisibleModelList.containsKey(mTouchIndex)) {
                model = mVisibleModelList.get(mTouchIndex);
            }

            float startX = getNoPaddingLeft();
            float startY = getNoPaddingTop() + heightForText() - offsetY4TextCenter();
            drawMovingAverageText(canvas, startX, startY, model.ma);

            if (mReferenceIndex == ReferenceIndex.VOL) {
                startX = getNoPaddingLeft();
                startY = getNoPaddingTop() + getMainHeight() + 2 * heightForText() - offsetY4TextCenter();
                drawMovingAverageText(canvas, startX, startY, model.volMa);
            }
        }
    }

    private void drawMovingAverageText(Canvas canvas, float startX, float startY, float[] ma) {
        setUpTextPaint(mTextPaint);
        String maArgs = getFormattedMaArgs(mMaArg);
        canvas.drawText(maArgs, startX, startY, mTextPaint);
        startX += mTextPaint.measureText(maArgs);
        for (int i = 0; i < ma.length; i++) {
            StringBuilder builder = new StringBuilder(" MA").append(i + 1).append(":");
            if (ma[i] < 0) {
                builder.append("- -");
            } else {
                builder.append(formatNumber(ma[i], 2));
            }
            String singleMa = builder.toString();
            setUpMovingAverageTextPaint(mTextPaint, i);
            canvas.drawText(singleMa, startX, startY, mTextPaint);
            startX += mTextPaint.measureText(singleMa);
        }
    }

    private String getFormattedMaArgs(int[] maArgs) {
        String result = "";
        for (int i = 0; i < maArgs.length; i++) {
            if (i == 0) {
                result += maArgs[i];
            } else {
                result += "," + maArgs[i];
            }
        }
        return result;
    }

    private String formatIntervalTime(String time) {
        String result = "";
        try {
            if (time.length() == 14) {
                if(getChartType()== Measure.DAY){
                    SimpleDateFormat parse = new SimpleDateFormat("yyyyMMddHHmmss");
                    SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                    Date date = parse.parse(time);
                    result = format.format(date);
                }else {
                    SimpleDateFormat parse = new SimpleDateFormat("yyyyMMddHHmmss");
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date date = parse.parse(time);
                    result = format.format(date);
                }
                }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private String formatYearTime(String time) {
        String result = "";
        try {
            if (time.length() == 14) {
                SimpleDateFormat parse = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                Date date = parse.parse(time);
                result = format.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    protected void drawRealTimeLines(Canvas canvas) {
        if (mProcessedModelList != null && mProcessedModelList.size() > 0) {
            int size = mProcessedModelList.size();
            int offset = Math.max(getIndexOffset(), 0);
            for (int i = 0; i < size && i < CANDLES; i++) {
                ProcessedKlineModel processedKlineModel = mProcessedModelList.get(i + offset);
                float chartX = getChartX(i);
                drawCandle(canvas, chartX, processedKlineModel.klineModel);
                drawReferenceIndex(canvas, chartX, processedKlineModel.klineModel);

                if (mVisibleModelList != null) {
                    mVisibleModelList.put(i, processedKlineModel);
                }
            }

            drawMovingAverageLines(canvas);
        }
    }

    /**
     * 画移动平均线MA，包括蜡烛图部分和下方指标部分的MA
     *
     * @param canvas
     */
    private void drawMovingAverageLines(Canvas canvas) {
        for (int i = 0; i < mMaArg.length; i++) {
            setUpMovingAveragePaint(mPaint, i);
            int size = mProcessedModelList.size();
            int offset = Math.max(getIndexOffset(), 0);
            Path mvPath = new Path();
            Path referencePath = new Path();
            for (int j = 0; j < size && j < CANDLES; j++) {
                ProcessedKlineModel processedKlineModel = mProcessedModelList.get(j + offset);
                float chartX = getChartX(j);

                if (processedKlineModel.ma[i] < 0) continue;

                addPath(mvPath, chartX, getChartY(processedKlineModel.ma[i]));
                if (mReferenceIndex == ReferenceIndex.VOL) {
                    addPath(referencePath, chartX, getReferenceChartY(processedKlineModel.volMa[i]));
                }
            }
            canvas.drawPath(mvPath, mPaint);
            canvas.drawPath(referencePath, mPaint);
        }
    }

    private void addPath(Path path, float chartX, float chartY) {
        if (path.isEmpty()) {
            path.moveTo(chartX, chartY);
        } else {
            path.lineTo(chartX, chartY);
        }
    }

    private void drawReferenceIndex(Canvas canvas, float chartX, KlineModel klineModel) {
        if (mReferenceIndex == ReferenceIndex.VOL) {
            drawVolumes(canvas, chartX, klineModel);
        }
    }

    private void drawVolumes(Canvas canvas, float chartX, KlineModel klineModel) {
        ChartColor color = ChartColor.RED;
        if (klineModel.getClosePrice() < klineModel.getOpenPrice()) {
            color = ChartColor.GREEN;
        }
        setUpCandleBodyPaint(mPaint, color.get());
        RectF rectf = new RectF();
        rectf.left = chartX - dp2Px(CANDLES_WIDTH / 2);
        rectf.top = getReferenceChartY(klineModel.getVolume());
        rectf.right = chartX + dp2Px(CANDLES_WIDTH / 2);
        rectf.bottom = getReferenceChartY(0);
        canvas.drawRect(rectf, mPaint);
    }

    private void drawCandle(Canvas canvas, float chartX, KlineModel klineModel) {
        ChartColor color = ChartColor.RED;
        float topPrice = klineModel.getClosePrice();
        float bottomPrice = klineModel.getOpenPrice();
        if (klineModel.getClosePrice() < klineModel.getOpenPrice()) { // negative line
            color = ChartColor.GREEN;
            topPrice = klineModel.getOpenPrice();
            bottomPrice = klineModel.getClosePrice();
        }

        drawTopCandleLine(canvas, klineModel.getMaxPrice(), topPrice, color.get(), chartX);
        drawCandleBody(canvas, topPrice, bottomPrice, color.get(), chartX);
        drawBottomCandleLine(canvas, klineModel.getMinPrice(), bottomPrice, color.get(), chartX);
    }

    private void drawCandleBody(Canvas canvas, float topPrice, float bottomPrice, String color, float chartX) {
        if (topPrice == bottomPrice) {
            setUpCandleLinePaint(mPaint, color);
            Path path = new Path();
            path.moveTo(chartX - dp2Px(CANDLES_WIDTH / 2), getChartY(topPrice));
            path.lineTo(chartX + dp2Px(CANDLES_WIDTH / 2), getChartY(bottomPrice));
            canvas.drawPath(path, mPaint);
        } else {
            setUpCandleBodyPaint(mPaint, color);
            RectF rectf = new RectF();
            rectf.left = chartX - dp2Px(CANDLES_WIDTH / 2);
            rectf.top = getChartY(topPrice);
            rectf.right = chartX + dp2Px(CANDLES_WIDTH / 2);
            rectf.bottom = getChartY(bottomPrice);
            canvas.drawRect(rectf, mPaint);
        }
    }

    private void drawTopCandleLine(Canvas canvas, Float maxPrice, float topPrice, String color, float chartX) {
        setUpCandleLinePaint(mPaint, color);
        Path path = new Path();
        path.moveTo(chartX, getChartY(maxPrice));
        path.lineTo(chartX, getChartY(topPrice));
        canvas.drawPath(path, mPaint);
    }

    private void drawBottomCandleLine(Canvas canvas, Float minPrice, float bottomPrice, String color, float chartX) {
        setUpCandleLinePaint(mPaint, color);
        Path path = new Path();
        path.moveTo(chartX, getChartY(bottomPrice));
        path.lineTo(chartX, getChartY(minPrice));
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected float getChartX(int index) {
        // get the x coordinate of middle between two points
        return super.getChartX(index) + super.getChartX(1) / 2;
    }

    @Override
    protected int getIndexOfTotalPoints(float x) {
        // get the index of the point that at this x coordinate
        x = x - super.getChartX(1) / 2;
        return super.getIndexOfTotalPoints(x);
    }

    @Override
    protected boolean shouldUpdate(MotionEvent event) {
        if (mVisibleModelList != null) {
            int index = getIndexOfTotalPoints(event.getX());
            if (mVisibleModelList.containsKey(index)) {
                if (mTouchIndex == index) {
                    return false;
                } else {
                    mTouchIndex = index;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void drawTouchLines(Canvas canvas) {
        PointF touchPoint = getTouchPoint();
        if (touchPoint != null) {
            float touchX = getChartX(mTouchIndex);
            if (mVisibleModelList == null
                    || !mVisibleModelList.containsKey(mTouchIndex)) return;

            ProcessedKlineModel model = mVisibleModelList.get(mTouchIndex);

            int paddingForText = heightForText();
            int height = getMainHeight() - 2 * paddingForText;
            int width = getNoPaddingWidth();

            // draw vertical line
            setUpTouchLinePaint(mPaint);
            float topY = getNoPaddingTop() + paddingForText;
            float bottomY = topY + height;
            Path verticalPath = new Path();
            verticalPath.moveTo(touchX, topY);
            verticalPath.lineTo(touchX, bottomY);
            canvas.drawPath(verticalPath, mPaint);

            // draw model.closePrice connected to horizontal line
            if (model.klineModel.getClosePrice() != null) {
                setUpBlackTextPaint(mTextPaint);
                float touchY = getChartY(model.klineModel.getClosePrice());
                String price = formatNumber(model.klineModel.getClosePrice());
                float textWidth = mTextPaint.measureText(price);
                float priceX = getNoPaddingLeft() + width - textWidth - dp2Px(2);
                float priceY = touchY + offsetY4TextCenter();

                // when touchX is larger than half of width, move price to left
                if (touchX > width / 2) {
                    priceX = getNoPaddingLeft() + dp2Px(2);
                }

                RectF rectF = getTextRectF(priceX, priceY, textWidth);
                setUpYellowBgPaint(mPaint);
                canvas.drawRoundRect(rectF, 5, 5, mPaint);
                canvas.drawText(price, priceX, priceY, mTextPaint);

                // draw horizontal line
                setUpTouchLinePaint(mPaint);
                float leftX = getNoPaddingLeft();
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

            drawReferenceTouchLines(canvas, touchX, model);

            onTouchLinesVisible(model, true);
        } else {
            onTouchLinesVisible(null, false);
        }
    }

    private void drawReferenceTouchLines(Canvas canvas, float touchX, ProcessedKlineModel model) {
        if (mReferenceIndex == ReferenceIndex.VOL) {

            // draw vertical line
            int heightForText = heightForText();
            setUpTouchLinePaint(mPaint);
            float topY = getNoPaddingTop() + getMainHeight() + 2 * heightForText;
            float bottomY = getNoPaddingBottom() - heightForText;
            Path verticalPath = new Path();
            verticalPath.moveTo(touchX, topY);
            verticalPath.lineTo(touchX, bottomY);
            canvas.drawPath(verticalPath, mPaint);

            // draw model.volume connected to horizontal line
            if (model.klineModel.getVolume() != null) {
                setUpBlackTextPaint(mTextPaint);
                float touchY = getReferenceChartY(model.klineModel.getVolume());
                String volume = model.klineModel.getVolume().toString();
                float textWidth = mTextPaint.measureText(volume);
                float volumeX = getNoPaddingRight() - textWidth - dp2Px(2);
                float volumeY = touchY + offsetY4TextCenter();

                // when touchX is larger than half of width, move volume to left
                if (touchX > getNoPaddingWidth() / 2) {
                    volumeX = getNoPaddingLeft() + dp2Px(2);
                }

                RectF rectF = getTextRectF(volumeX, volumeY, textWidth);
                setUpYellowBgPaint(mPaint);
                canvas.drawRoundRect(rectF, 5, 5, mPaint);
                canvas.drawText(volume, volumeX, volumeY, mTextPaint);

                // draw horizontal line
                setUpTouchLinePaint(mPaint);
                float leftX = getNoPaddingLeft();
                float rightX = getNoPaddingRight() - rectF.width(); // connected to rect

                // when touchX is larger than half of width, add offset for line.x
                if (touchX > getNoPaddingWidth() / 2) {
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

    private void onTouchLinesVisible(ProcessedKlineModel klineModel, boolean visible) {
        if (mOnTouchLineDrawListener != null) {
            mOnTouchLineDrawListener.onDraw(klineModel, visible);
        }
    }
}