package com.aaron.myviews.model.chart;

import com.aaron.myviews.model.newmodel.futures.FuturesQuotaData;

public class SuperLightningViewModel extends LightningViewModel {

    /**
     * 系统买入价
     */
    private Double bidPrice1;
    /**
     * 系统卖出价
     */
    private Double askPrice1;

    public SuperLightningViewModel(FuturesQuotaData quotaData) {
        super(quotaData);
        this.bidPrice1 = quotaData.getBidPrice1();
        this.askPrice1 = quotaData.getAskPrice1();
    }

    public Double getAskPrice1() {
        return askPrice1;
    }

    public Double getBidPrice1() {
        return bidPrice1;
    }

    @Override
    public String toString() {
        return "SuperLightningChartModel{" +
                "bidPrice1=" + bidPrice1 +
                ", askPrice1=" + askPrice1 +
                '}';
    }
}
