package com.aaron.myviews.model.newmodel;

/**
 * 股票下单接口数据包
 * @author bvin
 */
public class StockPayOrderData extends PayOrderData{
    
    private String stockCode;//股票代码
    private String typeCode;//股票所属市场类型代码
    private String stockName;//股票名称
    private Integer userBuyCount;//用户购买数量
    private Integer tradeDayCount;//T+N的N值，目前恒等于1
    
    public String getStockCode() {
        return stockCode;
    }
    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public Integer getUserBuyCount() {
        return userBuyCount;
    }
    public void setUserBuyCount(Integer userBuyCount) {
        this.userBuyCount = userBuyCount;
    }
    public Integer getTradeDayCount() {
        return tradeDayCount;
    }
    public void setTradeDayCount(Integer tradeDayCount) {
        this.tradeDayCount = tradeDayCount;
    }
}
