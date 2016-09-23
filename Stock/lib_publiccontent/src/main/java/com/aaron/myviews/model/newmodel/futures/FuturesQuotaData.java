package com.aaron.myviews.model.newmodel.futures;

import com.aaron.myviews.utils.BigDecimalUtil;
import com.aaron.myviews.utils.TextUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FuturesQuotaData implements Serializable{

    private static final long serialVersionUID = 2230317175662700198L;

    /**
     * 交易日
     */
    //private String tradingDay;
    /**
     * 合约代码
     */
    private String instrumentID;
    /**
     * 最新价
     */
    private Double lastPrice;
    /**
     * 上次结算价
     */
    private double preSettlementPrice;
    /**
     * 昨收盘
     */
    private Double preClosePrice;
    /**
     * 昨持仓量
     */
    private double preOpenInterest;
    /**
     * 今开盘
     */
    private double openPrice;
    /**
     * 最高价
     */
    private double highestPrice;
    /**
     * 最低价
     */
    private double lowestPrice;
    /**
     * 数量
     */
    private long volume;
    /**
     * 成交金额
     */
    private long turnover;
    /**
     * 持仓量
     */
    private String openInterest;
    /**
     * 今收盘
     */
    //private BigDecimal closePrice;
    /**
     * 本次结算价
     */
    private String settlementPrice;
    /**
     * 涨停板价
     */
    private double upperLimitPrice;
    /**
     * 跌停板价
     */
    private double lowerLimitPrice;
    /**
     * 最后修改时间
     */
    //private String updateTime;
    /**
     * 最后修改毫秒
     */
    //private String updateMillisec;
    /**
     * 系统买入价
     */
    private Double bidPrice1;
    /**
     * 系统买入数量
     */
    private Integer bidVolume1;
    /**
     * 系统卖出价
     */
    private Double askPrice1;
    /**
     * 系统卖出数量
     */
    private Integer askVolume1;
    /**
     * 当日均价
     */
    //private Double averagePrice;
    /**
     * 业务日期
     */
    //private String actionDay;

    public String getInstrumentID() {
        return instrumentID;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public Double getPriceChange() {
        if (lastPrice != null && preClosePrice != null) {
            return BigDecimalUtil.subtraction(lastPrice, preClosePrice).doubleValue();
        }
        return 0d;
    }

    public Double getPriceChangePercent() {
        if (lastPrice != null && preClosePrice != null) {
            return BigDecimalUtil.subtraction(lastPrice, preClosePrice)
                    .divide(new BigDecimal(preClosePrice), 5, RoundingMode.HALF_EVEN)
                    .doubleValue();
        }
        return 0d;
    }

    public Double getPreClosePrice() {
        return preClosePrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public String getOpenInterest() {
        return openInterest;
    }

    public double getUpperLimitPrice() {
        return upperLimitPrice;
    }

    public double getLowerLimitPrice() {
        return lowerLimitPrice;
    }

    /**
     * 系统买入价
     */
    public Double getBidPrice1() {
        return bidPrice1;
    }

    /**
     * 系统买入数量
     */
    public Integer getBidVolume1() {
        return bidVolume1;
    }

    /**
     * 系统卖出价
     */
    public Double getAskPrice1() {
        return askPrice1;
    }

    /**
     * 系统卖出数量
     */
    public Integer getAskVolume1() {
        return askVolume1;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getPreOpenInterest() {
        return preOpenInterest;
    }

    public double getSettlementPrice() {
        if (TextUtil.isDecimal(settlementPrice)) {
            return Double.valueOf(settlementPrice);
        }
        return 0;
    }

    public double getPreSettlementPrice() {
        return preSettlementPrice;
    }

    public long getVolume() {
        return volume;
    }

    public long getTurnover() {
        return turnover;
    }

    @Override
    public String toString() {
        return "FuturesQuotaData{" +
                "instrumentID='" + instrumentID + '\'' +
                ", lastPrice=" + lastPrice +
                ", preClosePrice=" + preClosePrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", openInterest='" + openInterest + '\'' +
                ", upperLimitPrice=" + upperLimitPrice +
                ", lowerLimitPrice=" + lowerLimitPrice +
                ", bidPrice1=" + bidPrice1 +
                ", bidVolume1=" + bidVolume1 +
                ", askPrice1=" + askPrice1 +
                ", askVolume1=" + askVolume1 +
                '}';
    }
}
