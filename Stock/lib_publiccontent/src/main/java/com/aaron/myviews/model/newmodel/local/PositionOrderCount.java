package com.aaron.myviews.model.newmodel.local;

import java.util.ArrayList;
import java.util.List;

/**
 *  /order/posiOrderCount 用户持仓订单数量
 *
 *  instrumentID 类型名
    cash:现金订单数量
    score:积分订单数量
 */
public class PositionOrderCount {

    private String instrumentCode;
    private int cash;
    private int score;

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public int getCash() {
        return cash;
    }

    public int getScore() {
        return score;
    }

    public boolean hasCashOrders() {
        if (cash > 0) {
            return true;
        }
        return false;
    }

    public boolean hasScoreOrders() {
        if (score > 0) {
            return true;
        }
        return false;
    }

    public static List<ProductListItem> updateProductList(List<PositionOrderCount> data, List<ProductListItem> productList) {
        List<ProductListItem> newList = new ArrayList<>();
        if (productList != null && productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {

            }
        }
        return newList;
    }

    @Override
    public String toString() {
        return "PositionOrderCount{" +
                "instrumentCode='" + instrumentCode + '\'' +
                ", cash=" + cash +
                ", score=" + score +
                '}';
    }
}