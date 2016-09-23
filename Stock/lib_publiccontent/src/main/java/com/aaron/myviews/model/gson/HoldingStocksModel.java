package com.aaron.myviews.model.gson;

import android.os.Parcel;
import android.os.Parcelable;

import com.aaron.myviews.model.entity.HoldingStock;

import java.util.List;


/**
 * 个人持仓数据
 * 相关接口：/order/order/currentOrderList
 */
public class HoldingStocksModel implements Parcelable {

    private Integer type;

    private Double usedAmt;
    private Double score;

    private Double totalCashProfit;
    private Double totalScoreProfit;

    private List<HoldingStock> orderList;

    public Double getTotalCashProfit() {
        if (totalCashProfit != null) {
            return totalCashProfit;
        }
        return 0d;
    }


    public Double getTotalScoreProfit() {
        if (totalScoreProfit != null) {
            return totalScoreProfit;
        }
        return 0d;
    }

    public List<HoldingStock> getOrderList() {
        return orderList;
    }

    public Integer getType() {
        return type;
    }

    public Double getUsedAmt() {
        return usedAmt;
    }

    public Double getScore() {
        return score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {

    }

    public static final Creator<BuyOrderDetailModel> CREATOR = new Creator<BuyOrderDetailModel>() {

        @Override
        public BuyOrderDetailModel createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            BuyOrderDetailModel p = new BuyOrderDetailModel();

            return p;
        }

        @Override
        public BuyOrderDetailModel[] newArray(int size) {
            return new BuyOrderDetailModel[size];
        }
    };

}
