package com.aaron.myviews.model.newmodel;

/**
 * 持仓页面播报数据 /financy/fs/profitStatisticsList
 */
public class ProfitStatistics {

    public static final String CACHE_KEY = "profit_statistics_cache";

    private String nickName;
    private String photoUrl;
    private Double profit;

    public String getNickName() {
        return nickName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Double getProfit() {
        return profit;
    }
}
