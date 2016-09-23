package com.aaron.myviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aaron.myviews.R;
import com.aaron.myviews.model.Product;
import com.aaron.myviews.model.newmodel.futures.FuturesQuotaData;
import com.aaron.myviews.utils.DisplayUtil;
import com.aaron.myviews.utils.FinancialUtil;

import java.math.BigDecimal;

public class QuotationView extends ScrollView {

    private FuturesQuotaData mData;
    private Product mProduct;
    private Double mPriceChange;
    private Double mPriceChangePercent;

    private TextView mUpsAndDownsNum;
    private TextView mUpsAndDownsRatioNum;
    private TextView mHighestPriceNum;
    private TextView mLowestPriceNum;
    private TextView mOpenPriceNum;
    private TextView mPreClosePriceNum;
    private TextView mPositionNum;
    private TextView mPrePositionNum;
    private TextView mTodaySettlementNum;
    private TextView mPreSettlementNum;
    private TextView mTotalHandsNum;
    private TextView mTotalAmountNum;
    private TextView mRaisingLimitNum;
    private TextView mDownLimitNum;

    private Context mContext;

    public QuotationView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public QuotationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.gray_main));
        setPadding((int) DisplayUtil.convertDp2Px(mContext,16), 0, (int) DisplayUtil.convertDp2Px(mContext,16), 0);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);

        View child = LayoutInflater.from(getContext()).inflate(R.layout.layout_quotation, null);
        addView(child);

        retrieveViews();
    }

    private void retrieveViews() {
        mUpsAndDownsNum = (TextView) findViewById(R.id.ups_and_downs_num);
        mUpsAndDownsRatioNum = (TextView) findViewById(R.id.ups_and_downs_ratio_num);
        mHighestPriceNum = (TextView) findViewById(R.id.highest_price_num);
        mLowestPriceNum = (TextView) findViewById(R.id.lowest_price_num);
        mOpenPriceNum = (TextView) findViewById(R.id.open_price_num);
        mPreClosePriceNum = (TextView) findViewById(R.id.pre_close_price_num);
        mPositionNum = (TextView) findViewById(R.id.position_num);
        mPrePositionNum = (TextView) findViewById(R.id.pre_position_num);
        mTodaySettlementNum = (TextView) findViewById(R.id.today_settlement_num);
        mPreSettlementNum = (TextView) findViewById(R.id.pre_settlement_num);
        mTotalHandsNum = (TextView) findViewById(R.id.total_hands_num);
        mTotalAmountNum = (TextView) findViewById(R.id.total_amount_num);
        mRaisingLimitNum = (TextView) findViewById(R.id.raising_limit_num);
        mDownLimitNum = (TextView) findViewById(R.id.down_limit_num);
    }

    public void addQuotationData(FuturesQuotaData data) {
        mData = data;
        updateView();
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public void setPriceChange(Double priceChange, Double priceChangePercent) {
        if (isShown()) {
            mPriceChange = priceChange;
            mPriceChangePercent = priceChangePercent;

            String priceChangeStr = FinancialUtil.accurateTheSecondDecimalPlace(priceChange);
            String priceChangePercentStr = FinancialUtil.formatToPercentage(priceChangePercent);
            int priceChangeColor = getResources().getColor(R.color.red_main);
            if (priceChange > 0) {
                priceChangeStr = "+" + priceChangeStr;
                priceChangePercentStr = "+" + priceChangePercentStr;
            } else if (priceChange < 0) {
                priceChangeColor = getResources().getColor(R.color.green_main);
            }
            mUpsAndDownsNum.setText(priceChangeStr);
            mUpsAndDownsNum.setTextColor(priceChangeColor);

            mUpsAndDownsRatioNum.setText(priceChangePercentStr);
            mUpsAndDownsRatioNum.setTextColor(priceChangeColor);
        }
    }

    private void updateView() {
        if (isShown() && mData != null && mProduct != null) {
            int pinkColor = getResources().getColor(R.color.red_main);
            int limeColor = getResources().getColor(R.color.green_main);
            double preClosePrice = 0;
            if (mData.getPreClosePrice() != null) {
                preClosePrice = mData.getPreClosePrice();
            }

            if (mPriceChange == null || mPriceChangePercent == null) {
                setPriceChange(mData.getPriceChange(), mData.getPriceChangePercent());
            }

            mHighestPriceNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getHighestPrice(), mProduct));
            mHighestPriceNum.setTextColor(mData.getHighestPrice() > preClosePrice ? pinkColor : limeColor);

            mLowestPriceNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getLowestPrice(), mProduct));
            mOpenPriceNum.setTextColor(mData.getLowestPrice() > preClosePrice ? pinkColor : limeColor);

            mOpenPriceNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getOpenPrice(), mProduct));
            mOpenPriceNum.setTextColor(mData.getOpenPrice() > preClosePrice ? pinkColor : limeColor);

            if (mData.getPreClosePrice() != null) {
                mPreClosePriceNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getPreClosePrice(), mProduct));
            }

            mPositionNum.setText(FinancialUtil.addUnitWhenBeyondTenThousand(new BigDecimal(mData.getOpenInterest())));
            mPrePositionNum.setText(FinancialUtil.addUnitWhenBeyondTenThousand(new BigDecimal(mData.getPreOpenInterest())));

            if (mData.getSettlementPrice() != 0) {
                mTodaySettlementNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getSettlementPrice(), mProduct));
                mTodaySettlementNum.setTextColor(mData.getSettlementPrice() > preClosePrice ? pinkColor : limeColor);
            } else {
                mTodaySettlementNum.setText("-- --");
            }

            mPreSettlementNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getPreSettlementPrice(), mProduct));
            mTotalHandsNum.setText(FinancialUtil.addUnitWhenBeyondTenThousand(new BigDecimal(mData.getVolume())));
            mTotalAmountNum.setText(FinancialUtil.addUnitOfHundredMillion(mData.getTurnover()));
            mRaisingLimitNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getUpperLimitPrice(), mProduct));
            mDownLimitNum.setText(FinancialUtil.formatPriceBasedOnFuturesType(mData.getLowerLimitPrice(), mProduct));
        }
    }
}
