package com.aaron.myviews.model.newmodel.futures;

import java.io.Serializable;

/**
 * /order/futures/balancedList接口返回数据
 * @author bvin
 */
public class FuturesTradeRecord implements Serializable {
    
    public static final int TRADE_TYPE_LONG = 0; // 看多
    public static final int TRADE_TYPE_SHORT = 1; // 看空

    public static final int FUND_TYPE_CASH = 0;
    public static final int FUND_TYPE_SCORE = 1;
    
    private Integer fundType;//货币类型0现金1积分
    private Integer id;//订单id
    private Integer tradeType;//交易类型0看多1看空
    private String displayId;//订单展示id
    private Integer futuresType;//期货类型1黄金2白银3股指
    private Integer futuresId;//期货标识
    private String futuresCode;//合约代码
    private Integer couponId;//抵用券ID
    private Double financyAllocation;//配资额度
    private Double cashFund;//保证金
    private Double theoryCounterFee;//应扣手续费
    private Double counterFee;//手续费
    private Double lossProfit;//结算收益
    private Double stopLoss;//触发止损额度
    private Double stopProfit;//触发止盈额度
    private Double stopLossPrice;//触发止损价格
    private Double stopProfitPrice;//触发止盈价格
    private Double buyPrice;//买入价
    private Integer count;//数量（单位:手）
    private String buyDate;//买入时间
    private String createDate;//订单创建日期
    private Double salePrice;//卖出价
    private String saleDate;//卖出时间
    private String sysSetSaleDate;//合约时间
    private Integer status;//订单状态（-1：支付失败-2：买入失败0：买入待处理1：买入处理中2：买委托成功3：持仓中4：卖出处理中5：卖委托成功6卖出成功）
    private Integer saleOpSource; //卖出类型 1后台人工卖出2用户自己主动卖出3系统清仓卖出4风控卖出100未知的卖出方式
    private Double rate; //汇率
    
    public Integer getFundType() {
        return fundType;
    }
    public Integer getId() {
        return id;
    }
    public Integer getTradeType() {
        return tradeType;
    }
    public String getDisplayId() {
        return displayId;
    }
    public Integer getFuturesType() {
        return futuresType;
    }
    public Integer getFuturesId() {
        return futuresId;
    }
    public String getFuturesCode() {
        return futuresCode;
    }
    public Integer getCouponId() {
        return couponId;
    }
    public Double getFinancyAllocation() {
        return financyAllocation;
    }
    public Double getCashFund() {
        return cashFund;
    }
    public Double getTheoryCounterFee() {
        return theoryCounterFee;
    }
    public double getCounterFee() {
        return counterFee;
    }
    
    /**
     * 获取结算盈亏
     * @return 缺省返回0
     */
    public double getLossProfit() {
        if (lossProfit!=null) {
            return lossProfit;
        }else {
            return 0;
        }
    }
    public Double getStopLoss() {
        return stopLoss;
    }
    public Double getStopProfit() {
        return stopProfit;
    }
    public Double getStopLossPrice() {
        return stopLossPrice;
    }
    public Double getStopProfitPrice() {
        return stopProfitPrice;
    }
    public Double getBuyPrice() {
        if (buyPrice != null) {
            return buyPrice;
        }
        return 0d;
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
    public Double getSalePrice() {
        if (salePrice != null) {
            return salePrice;
        }
        return 0d;
    }
    public String getSaleDate() {
        return saleDate;
    }
    public String getSysSetSaleDate() {
        return sysSetSaleDate;
    }
    public Integer getStatus() {
        return status;
    }

//    public Integer getSaleOperationResId() {
//        if (saleOpSource == null) return null;
//
//        if (saleOpSource == 1 || saleOpSource == 3) {
//            return R.string.time_up_sale;
//        } else if (saleOpSource == 2) {
//            return R.string.market_price_sale;
//        } else if (saleOpSource == 4) {
//            if (getLossProfit() > 0) {
//                return R.string.stop_gain_sale;
//            } else {
//                return R.string.stop_loss_sale;
//            }
//        }
//        return null;
//    }

    public double getRate() {
        if (rate != null) {
            return rate.doubleValue();
        }
        return 1;
    }
}
