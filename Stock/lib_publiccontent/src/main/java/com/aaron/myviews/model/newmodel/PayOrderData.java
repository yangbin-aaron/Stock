package com.aaron.myviews.model.newmodel;

/**
 * 支付订单数据包
 * @author bvin
 *
 */
public class PayOrderData {

    protected Integer traderId;//操盘标识ID，即获取操盘配置信息接口中的ID
    protected Double userBuyPrice;//用户提交订单时的股票价格
    protected String userBuyDate;//用户提交时的时间
    
    public Integer getTraderId() {
        return traderId;
    }
    public Double getUserBuyPrice() {
        return userBuyPrice;
    }
    public String getUserBuyDate() {
        return userBuyDate;
    }
    
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }
    public void setUserBuyPrice(double userBuyPrice) {
        this.userBuyPrice = userBuyPrice;
    }
    public void setUserBuyDate(String userBuyDate) {
        this.userBuyDate = userBuyDate;
    }
    
    
}
