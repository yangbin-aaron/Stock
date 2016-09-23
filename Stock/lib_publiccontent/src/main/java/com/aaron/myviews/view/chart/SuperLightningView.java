package com.aaron.myviews.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.aaron.myviews.R;
import com.aaron.myviews.model.chart.LightningViewModel;
import com.aaron.myviews.model.chart.SuperLightningViewModel;
import com.aaron.myviews.utils.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SuperLightningView extends LightningView {

    public SuperLightningView(Context context) {
        super(context);
    }

    public SuperLightningView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void calculateBaseLines() {
        SuperLightningViewModel model = (SuperLightningViewModel) getLastPoint();
        if (mProduct != null && model != null) {

            int areaCount = getBaselines().length - 1;
            float priceRange = mProduct.getPriceInterval() / areaCount;

            if (getFirstBaseLine() == 0 && getLastBaseLine() == 0) { // first

                BigDecimal bigMid = BigDecimalUtil.add(model.getAskPrice1(), model.getBidPrice1())
                        .divide(BigDecimal.valueOf(2), mProduct.getPriceScale(), RoundingMode.HALF_EVEN);

                BigDecimal firstBaseLine = bigMid.add(new BigDecimal((areaCount / 2) * priceRange));

                if (getBaselines().length % 2 == 0) {
                    firstBaseLine = firstBaseLine.add(new BigDecimal(priceRange / 2));
                    setBaseLinesFromFirst(firstBaseLine.floatValue(), priceRange);
                } else {
                    setBaseLinesFromFirst(firstBaseLine.floatValue(), priceRange);
                }
                return;
            }

            if (model.getAskPrice1() > getFirstBaseLine()) {
                float firstBaseLine = getFirstBaseLine();
                while (model.getAskPrice1() > firstBaseLine) {
                    firstBaseLine += priceRange;
                }
                setBaseLinesFromFirst(firstBaseLine, priceRange);
                return;
            }

            if (model.getBidPrice1() < getLastBaseLine()) {
                float lastBaseLine = getLastBaseLine();
                while (model.getBidPrice1() < lastBaseLine) {
                    lastBaseLine -= priceRange;
                }
                setBaselinesFromLast(lastBaseLine, priceRange);
                return;
            }
        }
    }

    @Override
    protected float getCurrentPrice(LightningViewModel point) {
        SuperLightningViewModel model = (SuperLightningViewModel) point;

        if (getDrawingLine() == 0) {
            return model.getAskPrice1().floatValue();
        } else {
            return model.getBidPrice1().floatValue();
        }
    }

    @Override
    protected void setUpYellowLinePaint(Paint paint) {
        if (getDrawingLine() == 0) {
            paint.setColor(getResources().getColor(R.color.gold_long_pink));
        } else {
            paint.setColor(getResources().getColor(R.color.gold_short_lime));
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2Px(1));
        paint.setPathEffect(null);
    }

    @Override
    protected void setUpYellowBgPaint(Paint paint) {
        if (getDrawingLine() == 0) {
            paint.setColor(getResources().getColor(R.color.gold_long_pink));
        } else {
            paint.setColor(getResources().getColor(R.color.gold_short_lime));
        }
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void setUpPointPaint(Paint paint) {
        paint.setColor(Color.parseColor("#EAC281"));
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
    }

    @Override
    protected void setUpBlackTextPaint(Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(sp2Px(9));
    }
}
