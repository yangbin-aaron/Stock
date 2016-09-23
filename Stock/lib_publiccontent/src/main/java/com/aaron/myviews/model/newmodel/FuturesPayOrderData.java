package com.aaron.myviews.model.newmodel;

import com.aaron.myviews.model.newmodel.account.Coupon;
import com.google.gson.Gson;

import java.util.List;

/**
 * 期货下单数据包.
 *
 * @author bvin
 */
public class FuturesPayOrderData extends PayOrderData {

    public static final int TRADE_ORIENTATION_LONG = 0;
    public static final int TRADE_ORIENTATION_SHORT = 1;

    protected String futuresCode;//期货合约
    protected int count;//数量（手数）
    protected int tradeType;//交易方向（0;//看多，1;//看空）
    protected int stopProfit;//止盈金额
    protected double rate;//汇率
    private boolean useCoupon;
    protected String couponIds;

    public String getFuturesCode() {
        return futuresCode;
    }

    public void setFuturesCode(String futuresCode) {
        this.futuresCode = futuresCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public double getStopProfit() {
        return stopProfit;
    }

    public void setStopProfit(int stopProfit) {
        this.stopProfit = stopProfit;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
    }

    /**
     * 设置需要使用的优惠券.
     * <p><i>需要先设置购买手数，实际购买手数决定使用几张优惠券<i/>
     *
     * @param coupons 优惠券
     */
    public void setCouponIds(List<Coupon> coupons) {
        if (coupons == null || coupons.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < count; i++) {
            if (i >= coupons.size()) break;//交易手数>优惠券数=跳出循环
            sb.append(coupons.get(i).getId()).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        couponIds = sb.toString();
    }

    public boolean isUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(boolean useCoupon) {
        this.useCoupon = useCoupon;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
        /*return "FuturesPayOrderData{" +
                "futuresCode='" + futuresCode + '\'' +
                ", count=" + count +
                ", tradeType=" + tradeType +
                ", stopProfit=" + stopProfit +
                ", rate=" + rate +
                ", useCoupon=" + useCoupon +
                ", couponIds='" + couponIds + '\'' +
                '}';*/
    }
}
