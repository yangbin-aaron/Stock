package com.aaron.myviews.model.gson;


import android.os.Parcel;
import android.os.Parcelable;

public class BuyOrderDetailModel implements Parcelable {
	private Integer id 				= null;
	private Integer orderId 		= null;
	private Integer userId 			= null;
	private Integer quantity 		= null;
	private Integer factCount 		= null;
	private Integer status 			= null;
	private Integer isCountProfit	= null;
	private Integer buyType 		= null;
	private Integer saleType 		= null;
	
	private String	nickName		= null;
	private String	stockCode		= null;
	private String	codeType		= null;
	private String	stockCom		= null;
	private String	exchangeType	= null;
	private String	createDate		= null;
	
	private Double 	amt 			= null;
	private Double 	lossAmt 		= null;
	private Double 	counterFee 		= null;
	private Double	buyPrice		= null;
	private Double	salePrice		= null;
	private Double 	openPrice		= null;
	private Double	lossProfit		= null;
	private Double 	cashFund 		= null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
		
	public static final Creator<BuyOrderDetailModel> CREATOR = new Creator<BuyOrderDetailModel>(){  
		   
		@Override  
		public BuyOrderDetailModel createFromParcel(Parcel source) {  
             // TODO Auto-generated method stub  
             // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
			BuyOrderDetailModel p = new BuyOrderDetailModel();  

			return p;  
		}  
   
         @Override  
         public BuyOrderDetailModel[] newArray(int size) {  
             // TODO Auto-generated method stub  
             return new BuyOrderDetailModel[size];  
         }  
	};  
}
