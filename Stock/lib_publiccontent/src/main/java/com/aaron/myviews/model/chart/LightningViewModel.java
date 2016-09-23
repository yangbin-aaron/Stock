package com.aaron.myviews.model.chart;

import com.aaron.myviews.model.newmodel.futures.FuturesQuotaData;
import com.aaron.myviews.utils.FinancialUtil;

public class LightningViewModel {

    private Float lastPrice;

    public LightningViewModel(Float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public LightningViewModel(FuturesQuotaData quotaData) {
        this.lastPrice = FinancialUtil.accurateToFloat(quotaData.getLastPrice());
    }

    public Float getLastPrice() {
        return lastPrice;
    }
}
