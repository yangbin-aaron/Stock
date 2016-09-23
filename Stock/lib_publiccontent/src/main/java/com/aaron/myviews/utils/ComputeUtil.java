package com.aaron.myviews.utils;

import java.math.RoundingMode;

/**
 * 业务计算工具.
 */
public class ComputeUtil {

    private static final int RADIX_TEN = 10;

    /**
     * 计算涨跌比
     *
     * @param oldPrice 旧价格
     * @param newPrice 新价格
     * @param scale    保留小数位数
     * @return
     */
    public static double computeQuotaChangeRatio(double oldPrice, double newPrice, int scale) {
        double diff = BigDecimalUtil.subtraction(newPrice, oldPrice).doubleValue();
        if (oldPrice == 0) return 0;
        double ratio = BigDecimalUtil.divide(diff, oldPrice, scale + scale * 2, RoundingMode.HALF_EVEN).doubleValue();
        return ratio;
    }

    /**
     * 通过当前价计算默认止盈价(默认止盈价 = 当前价 + 当前价*1%).
     * @param currentPrice 当前价
     * @return
     */
    public static double computeDefaultStopProfitPrice(double currentPrice) {
        double partOfCurrentPrice = BigDecimalUtil.multiply(currentPrice, getDefaultStopProfitRate()).doubleValue();
        return BigDecimalUtil.add(currentPrice, partOfCurrentPrice).doubleValue();
    }

    //止盈系数1%
    public static double getDefaultStopProfitRate() {
        return BigDecimalUtil.divide(1d, 100d).doubleValue();
    }

    /**
     * 通过当前价计算默认止损价(默认止损价 = 当前价 - 当前价*0.5%).
     * @param currentPrice 当前价
     * @return
     */
    public static double computeDefaultStopLossPrice(double currentPrice, int scale) {
        double partOfCurrentPrice = BigDecimalUtil.multiply(currentPrice, getDefaultStopLossRate(),
                scale, RoundingMode.HALF_EVEN).doubleValue();
        return BigDecimalUtil.subtraction(currentPrice, partOfCurrentPrice).doubleValue();
    }

    //止损系数0.5%
    public static double getDefaultStopLossRate() {
        return BigDecimalUtil.divide(0.5d, 100d).doubleValue();
    }

    /**
     * 根据小数位数算出最小波动点位(1/10^n)
     * @param scale 小数位数
     * @return
     */
    public static double computeWavePoint(int scale) {
        double pow = Math.pow(RADIX_TEN, scale);
        return BigDecimalUtil.divide((double) 1, pow, scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * 获取止盈范围[当前价+最小波动3点位,涨停价]
     *
     * @param currentPrice 当前价
     * @param stopPrice    涨停价
     * @param scale        保留小数位数
     * @return
     */
    public static double[] getStopProfitRange(double currentPrice, double stopPrice, int scale) {
        double threePoint = BigDecimalUtil.multiply(computeWavePoint(scale), 3d).doubleValue();
        double start = BigDecimalUtil.add(currentPrice, threePoint).doubleValue();
        double end = stopPrice;
        return new double[]{start, end};
    }

    /**
     * 获取止损范围[跌停价-跌停价*0.5%，当前价-波动3点位]
     *
     * @param currentPrice 当前价
     * @param stopPrice    跌停价
     * @param scale        保留小数位数
     * @return
     */
    public static double[] getStopLossRange(double currentPrice, double stopPrice, int scale) {
        double partOfStopPrice = BigDecimalUtil.multiply(stopPrice, getDefaultStopLossRate()).doubleValue();
        double start = BigDecimalUtil.subtraction(stopPrice, partOfStopPrice).doubleValue();
        double threePoint = BigDecimalUtil.multiply(computeWavePoint(scale), 3d).doubleValue();
        double end = BigDecimalUtil.subtraction(currentPrice, threePoint).doubleValue();
        return new double[]{start, end};
    }
}
