package com.aaron.myviews.model.gson;

import java.io.Serializable;

/**  
 * @类描述： 查询售出股票
 * @创建人：龙章煌  
 * @创建时间：2015-05-21  
 * @修改人： 
 * @修改时间：  
 * @修改备注：   
 * @version
 */
public class StockResultsaleModel  implements Serializable{
    private Integer id;   //订单详情ID
    private String orderId;//订单ID
    private Integer userId;//用户id
    private String nickName;//用户昵称
    private String stockCode;//股票代码
    private String codeType;//股票市场类型
    private String stockCom;//股票名称
    private Double amt; //实盘额度
    private Integer quantity;//委托购买数量
    private Integer factCount;//实际成交数量
    private Double lossAmt;//保证金
    private Double counterFee;//手续费
    private Double cashFund;//
    private Integer exchangeType;//
    private Integer status;//订单状态4标识已经卖出并处理完成 3标识卖出处理中
    private String createDate;//下单时间
    private Integer isCountProfit;//
    private Integer buyType;//0现金1积分
    private Integer saleType;//出售类型0现金1积分
    private Double buyPrice;//买入价
    private Double salePrice;//出售价格
    private Double openPrice;//开盘价
    private Double lossProfit;//本次盈亏
    private String marketValue;//市值
    private String finishDate;//下单时间
    
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getStockCom() {
		return stockCom;
	}
	public void setStockCom(String stockCom) {
		this.stockCom = stockCom;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getFactCount() {
		return factCount;
	}
	public void setFactCount(Integer factCount) {
		this.factCount = factCount;
	}
	public Double getLossAmt() {
		return lossAmt;
	}
	public void setLossAmt(Double lossAmt) {
		this.lossAmt = lossAmt;
	}
	public Double getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(Double counterFee) {
		this.counterFee = counterFee;
	}
	public Double getCashFund() {
		return cashFund;
	}
	public void setCashFund(Double cashFund) {
		this.cashFund = cashFund;
	}
	public Integer getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getIsCountProfit() {
		return isCountProfit;
	}
	public void setIsCountProfit(Integer isCountProfit) {
		this.isCountProfit = isCountProfit;
	}
	public Integer getBuyType() {
		return buyType;
	}
	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}
	public Integer getSaleType() {
		return saleType;
	}
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public Double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}
	public Double getLossProfit() {
		return lossProfit;
	}
	public void setLossProfit(Double lossProfit) {
		this.lossProfit = lossProfit;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
    
    
}
