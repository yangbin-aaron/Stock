package com.aaron.myviews.model.newmodel;

/**
 * /order/futures/buy期货下单接口返回数据
 * @author bvin
 */
public class FuturesPayResponse {
    
    private Integer fundType ;//货币类型（0：现金，1：积分）
    private String futuredOrderIdsStr;// 订单id数组
    
    public Integer getFundType() {
        return fundType;
    }

    public void setFundType(Integer fundType) {
        this.fundType = fundType;
    }

    public String getFuturedOrderIdsStr() {
        return futuredOrderIdsStr;
    }

    public void setFuturedOrderIdsStr(String futuredOrderIdsStr) {
        this.futuredOrderIdsStr = futuredOrderIdsStr;
    }
    
}
