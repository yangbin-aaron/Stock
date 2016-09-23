package com.aaron.myviews.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.aaron.myviews.R;
import com.aaron.myviews.utils.BigDecimalUtil;
import com.aaron.myviews.utils.DateUtil;
import com.hundsun.quote.model.Realtime;

import java.math.BigDecimal;

/**
 * 个人持仓订单数据
 * 相关接口：/order/order/currentOrderList
 *
 * status:
 -2：买入失败
 0：买待处理，创建订单默认状态
 1：买处理中
 2：买委托成功，即申报成功
 3：持仓中
 4：卖处理中
 5：卖委托成功，即申报成功
 6：卖出成功
 *
 */
public class HoldingStock implements Parcelable {

    public static final int FUND_TYPE_CASH = 0;
    public static final int FUND_TYPE_SCORE = 1;

    public static final int STATUS_BUY_FAILURE = -2;
    public static final int STATUS_BUY_PENDING = 0;
    public static final int STATUS_BUY_PROCESSING = 1;
    public static final int STATUS_BUY_SUBMISSION_SUCCESS = 2;
    public static final int STATUS_HOLDING = 3;
    public static final int STATUS_SELL_PROCESSING = 4;
    public static final int STATUS_SELL_SUBMISSION_SUCCESS = 5;
    public static final int STATUS_SELL_OUT_SUCCESS = 6;

    private Long id; // 订单id
    private String displayId; // 订单展示
    private Integer fundType; // 0：现金，1积分
    private String nickName; // 用户昵称
    private String headPic;

    private Long stockId; // 股票id
    private String stockCode; // 股票代码
    private String stockName; // 股票名称
    private String typeCode;
    private Long traderId; // 操盘标识ID

    private Integer multiple; // 融资比例（1:5 的 5）

    private Double financyAllocation; //配资额度
    private Double interest; // 每日利息
    private Double totalInterest; // 总利息，即服务费
    private Double maxLoss; // 最大亏损金额
    private Double warnAmt; // 预警线

    private Double cashFund; // 保证金（不包括手续费，前段显示保证金：cashFund+ counterFee）
    private Double counterFee; // 手续费
    private Double buyPrice; // 买入价
    private Integer factBuyCount; // 实际买入数量
    private Double factBorrowAmt; // 借款金额

    private String buyDate; // 买入时间
    private Integer tradeDayCount; // t+n的n值
    private String sysSetSaleDate; // 合约日期
    private Integer status; // 股票状态

    // 临时变量存储
    private Integer tradeStatus; // 是否停牌, 只在 status=3 时候需会有true
    private Double curPrice; // 股票现价
    private Double preClosePrice; // 昨收价

    private HoldingStock(Parcel source) {
        id = source.readLong();
        displayId = source.readString();
        fundType = source.readInt();
        nickName = source.readString();
        headPic = source.readString();

        stockId = source.readLong();
        stockCode = source.readString();
        stockName = source.readString();
        typeCode = source.readString();
        traderId = source.readLong();

        multiple = source.readInt();

        financyAllocation = source.readDouble();
        interest = source.readDouble();
        totalInterest = source.readDouble();
        maxLoss = source.readDouble();
        warnAmt = source.readDouble();

        cashFund = source.readDouble();
        counterFee = source.readDouble();
        buyPrice = source.readDouble();
        factBuyCount = source.readInt();
        factBorrowAmt = source.readDouble();

        buyDate = source.readString();
        tradeDayCount = source.readInt();
        sysSetSaleDate = source.readString();
        status = source.readInt();

        // 临时变量存储
        tradeStatus = source.readInt();
        curPrice = source.readDouble();
        preClosePrice = source.readDouble();
    }
    
    /** 
     * 创建一个新的实例 HoldingStock.   
     */

    public HoldingStock() {
    }

    public Long getId() {
        return id;
    }

    public String getDisplayId() {
        return displayId;
    }

    public Integer getFundType() {
        return fundType;
    }

    public String getNickName() {
        return nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public Long getStockId() {
        return stockId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public Long getTraderId() {
        return traderId;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public Double getFinancyAllocation() {
        return financyAllocation;
    }

    public Double getInterest() {
        return interest;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public Double getMaxLoss() {
        if (maxLoss != null) {
            return maxLoss;
        }
        return 0d;
    }

    public Double getWarnAmt() {
        if (warnAmt != null) {
            return warnAmt;
        }
        return 0d;
    }

    public Double getCashFund() {
        if (cashFund != null) {
            return cashFund;
        }
        return 0d;
    }

    public Double getCounterFee() {
        if (counterFee != null) {
            return counterFee;
        }
        return 0d;
    }

    public Double getBuyPrice() {
        if (buyPrice != null) {
            return buyPrice;
        }
        return 0d;
    }

    public Integer getFactBuyCount() {
        if (factBuyCount != null) {
            return factBuyCount;
        }
        return 0;
    }

    public Double getFactBorrowAmt() {
        return factBorrowAmt;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public Integer getTradeDayCount() {
        return tradeDayCount;
    }

    public String getSysSetSaleDate() {
        return sysSetSaleDate;
    }

    public Integer getStatus() {
        if (status != null) {
            return status;
        }
        return STATUS_HOLDING;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getCurPrice() {
        if (curPrice != null) {
            return curPrice;
        }
        return 0D;
    }

    public void setTradeStatus(Integer realtimeTradeStatus) {
        tradeStatus = realtimeTradeStatus;
    }

    public void setPreClosePrice(Double preClosePrice) {
        this.preClosePrice = preClosePrice;
    }

    public Double getPreClosePrice() {
        if (preClosePrice != null) {
            return preClosePrice;
        }
        return 0d;
    }

    public Double getCashOrScoreProfit() {
        if (isHalt()) {
            return 0d;
        }
        // 持仓|卖处理中|卖已申报
        if (getStatus() == STATUS_HOLDING
                || getStatus() == STATUS_SELL_PROCESSING
                || getStatus() == STATUS_SELL_SUBMISSION_SUCCESS) {
            BigDecimal bigDecimalSpread = BigDecimalUtil.subtraction(getCurPrice(), getBuyPrice());
            return bigDecimalSpread.multiply(new BigDecimal(getFactBuyCount())).doubleValue();
        }
        return 0d;
    }

    public Double getCashOrScoreProfitBaseOnPreClosePrice() {
        if (isHalt()) {
            BigDecimal bigDecimalSpread = BigDecimalUtil.subtraction(getPreClosePrice(), getBuyPrice());
            return bigDecimalSpread.multiply(new BigDecimal(getFactBuyCount())).doubleValue();
        }
        return 0d;
    }

    public static int getStatusMessageResId(int status) {
        switch (status) {
            case STATUS_BUY_FAILURE: return R.string.status_buy_failure;
            case STATUS_BUY_PENDING: return R.string.status_buy_pending;
            case STATUS_BUY_PROCESSING: return R.string.status_buy_processing;
            case STATUS_BUY_SUBMISSION_SUCCESS: return R.string.status_buy_submission_success;
            case STATUS_HOLDING: return R.string.status_holding;
            case STATUS_SELL_PROCESSING: return R.string.status_sell_submission_success; // 用‘卖已申报’替换‘卖处理中’
            case STATUS_SELL_SUBMISSION_SUCCESS: return R.string.status_sell_submission_success;
            case STATUS_SELL_OUT_SUCCESS: return R.string.status_sell_out_success;
        }
        return R.string.status_unknown;
    }

    public Boolean isHalt() { // 是否停牌
        if (tradeStatus != null) {
            return tradeStatus == Realtime.TRADE_STATUS_HALT;
        }
        return false;
    }
    
    public boolean isStart() {
        if (tradeStatus != null) {
            return tradeStatus == Realtime.TRADE_STATUS_START;
        }
        return false;
    }

    public Boolean isBuyInToday() {
        if (!TextUtils.isEmpty(buyDate)) {
            return DateUtil.isToday(buyDate);
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        write(dest, id);
        write(dest, displayId);
        write(dest, fundType);
        write(dest, nickName);
        write(dest, headPic);

        write(dest, stockId);
        write(dest, stockCode);
        write(dest, stockName);
        write(dest, typeCode);
        write(dest, traderId);

        write(dest, multiple);

        write(dest, financyAllocation);
        write(dest, interest);
        write(dest, totalInterest);
        write(dest, maxLoss);
        write(dest, warnAmt);

        write(dest, cashFund);
        write(dest, counterFee);
        write(dest, buyPrice);
        write(dest, factBuyCount);
        write(dest, factBorrowAmt);

        write(dest, buyDate);
        write(dest, tradeDayCount);
        write(dest, sysSetSaleDate);
        write(dest, getStatus());

        // 临时变量存储
        write(dest, tradeStatus);
        write(dest, curPrice);
        write(dest, preClosePrice);
    }

    public static final Creator<HoldingStock> CREATOR = new Creator<HoldingStock>()    {

        @Override
        public HoldingStock createFromParcel(Parcel source) {
            return new HoldingStock(source);
        }

        @Override
        public HoldingStock[] newArray(int size) {
            return new HoldingStock[size];
        }

    };

    private void write(Parcel dest, Long value) {
        if (value != null) {
            dest.writeLong(value);
        } else {
            dest.writeLong(0);
        }
    }

    private void write(Parcel dest, Double value) {
        if (value != null) {
            dest.writeDouble(value);
        } else {
            dest.writeLong(0);
        }
    }

    private void write(Parcel dest, Integer value) {
        if (value != null) {
            dest.writeInt(value);
        } else {
            dest.writeInt(0);
        }
    }

    private void write(Parcel dest, String value) {
        if (value != null) {
            dest.writeString(value);
        } else {
            dest.writeString("");
        }
    }
}
