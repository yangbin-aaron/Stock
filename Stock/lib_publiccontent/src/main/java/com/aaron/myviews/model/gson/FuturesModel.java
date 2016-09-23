/**
 * @Title: FuturesModel.java
 * @Package com.luckin.magnifier.model.gson
 * @Description: TODO
 * @ClassName: FuturesModel
 * @author 于泽坤
 * @date 2015-7-29 下午1:58:31
 */

package com.aaron.myviews.model.gson;

import android.text.TextUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * /order/futures/posiList
 */
public class FuturesModel implements Comparable<FuturesModel>, Serializable {

    public static final int TRADE_TYPE_LONG = 0; // 看多
    public static final int TRADE_TYPE_SHORT = 1; // 看空

    public static final int FUND_TYPE_CASH = 0;        //0是现金
    public static final int FUND_TYPE_SCORE = 1;    //1是积分

    public static final int PENDING_BUY = 0;                //买待处理
    public static final int BUY_PROCESSING = 1;             //买处理中
    public static final int BUY_HAS_BEEN_DECLARED = 2;      //买已申报
    public static final int UNWIND = 3;                     //平仓
    public static final int SELL_PROCESSING = 4;            //卖处理中
    public static final int SELL_BEEN_DECLARED = 5;         //卖已申报
    public static final int UNWIND_SUCCESS = 6;             //平仓成功
    public static final int STOP_LOSS_UNWIND = 7;           //止损平仓
    public static final int STOP_PROFIT_UNWIND = 8;         //止盈平仓

    private int id;// 订单Id
    private int fundType;// 订单类型0现金1积分
    private int tradeType;// 交易类型0看多1看空
    private String displayId;// 订单展示ID
    private Integer futuresType;// 期货类型1黄金2白银3股指
    private String futuresCode;// 合约编号
    private String couponId;// 抵用券ID
    private Double cashFund;// 保证金
    private Double theoryCounterFee;// 应扣手续费
    private Double counterFee;// 手续费
    private Double lossProfit;// 结算收益
    private Double stopLoss;// 触发止损金额
    private Double stopProfit;// 触发止盈金额

    private Double stopLossPrice;// 止损价
    private Double stopProfitPrice;// 止盈价
    private Double buyPrice;// 买入价
    private Integer count;// 买入数量（单位;//手）
    private String buyDate;// 买入日期
    private String createDate;// 订单创建日期
    private Double salePrice;// 卖出价格
    private String saleDate;// 卖出日期
    private String sysSetSaleDate;// 合约期
    private int status;// 订单状态（-1;//支付失败-2;//买入失败0;//买入待处理1;//买入处理中2;//买委托成功3;//持仓中4;//卖出处理中5;//卖委托成功6卖出成功）7，止损平仓 8，止盈平仓

    @SuppressWarnings("unused")
    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }


    /**
     *
     * @return  订单类型0现金1积分
     */
    public int getFundType() {
        return fundType;
    }

    /**
     * 交易类型0看多1看空
     * @return
     */
    public int getTradeType() {
        return tradeType;
    }

    public Double getCashFund() {
        return cashFund;
    }

    public Double getCounterFee() {
        return counterFee;
    }

    public Double getLossProfit() {
        return lossProfit;
    }

    public Double getStopLoss() {
        return stopLoss;
    }

    public Double getStopProfit() {
        return stopProfit;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Integer getCount() {
        return count;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 0看多，1看空
     * @param bidPrice 系统买入价
     * @param askPrice 系统卖出价
     * @param earningsMultiple
     * @return
     */
    public BigDecimal getGoldEarnings(Double bidPrice, Double askPrice, BigDecimal earningsMultiple) {
        BigDecimal bidPriceBigDecimal = new BigDecimal(bidPrice);
        BigDecimal askPriceBigDecimal = new BigDecimal(askPrice);
        BigDecimal buyPriceBigDecimal = new BigDecimal(buyPrice);
        if (tradeType == TRADE_TYPE_LONG) {
            return (bidPriceBigDecimal.subtract(buyPriceBigDecimal)).multiply(earningsMultiple);
        } else {
            return (buyPriceBigDecimal.subtract(askPriceBigDecimal)).multiply(earningsMultiple);
        }
    }

    /**
     * 0看多，1看空
     * @param bidPrice 系统买入价
     * @param askPrice 系统卖出价
     * @return
     */
    public Double getLastPrice(Double bidPrice, Double askPrice) {
        if (tradeType == TRADE_TYPE_LONG) {
            return bidPrice;
        } else {
            return askPrice;
        }
    }

    private Long getDateTime(String dateStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = simpleDateFormat.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int compareTo(FuturesModel another) {
        if (!TextUtils.isEmpty(this.buyDate) || !TextUtils.isEmpty(another.getBuyDate())) {
            Long nowBuyTime = getDateTime(this.getBuyDate());
            Long anotherTime = getDateTime(another.getBuyDate());
            if (nowBuyTime != null && anotherTime != null) {
                if (nowBuyTime.compareTo(anotherTime) != 0) {
                    return nowBuyTime.compareTo(anotherTime);
                } else {
                    String nowId = String.valueOf(this.getId());
                    String anotherId = String.valueOf(another.getId());
                    return nowId.compareTo(anotherId);
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "FuturesModel{" +
                "id='" + id + '\'' +
                ", fundType=" + fundType +
                ", tradeType=" + tradeType +
                ", displayId='" + displayId + '\'' +
                ", futuresType=" + futuresType +
                ", futuresCode='" + futuresCode + '\'' +
                ", couponId='" + couponId + '\'' +
                ", cashFund=" + cashFund +
                ", theoryCounterFee=" + theoryCounterFee +
                ", counterFee=" + counterFee +
                ", lossProfit=" + lossProfit +
                ", stopLoss=" + stopLoss +
                ", stopProfit=" + stopProfit +
                ", stopLossPrice=" + stopLossPrice +
                ", stopProfitPrice=" + stopProfitPrice +
                ", buyPrice=" + buyPrice +
                ", count=" + count +
                ", buyDate='" + buyDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", salePrice=" + salePrice +
                ", saleDate='" + saleDate + '\'' +
                ", sysSetSaleDate='" + sysSetSaleDate + '\'' +
                ", status=" + status +
                '}';
    }

    public static class SaleOrderId {

        private String fundType;
        private String orderId;

        public SaleOrderId(String fundType) {
            this.fundType = fundType;
        }

        public void addOrderId(String orderId) {
            if (TextUtils.isEmpty(this.orderId)) {
                this.orderId = orderId;
            } else {
                this.orderId += "," + orderId;
            }
        }
    }
}
