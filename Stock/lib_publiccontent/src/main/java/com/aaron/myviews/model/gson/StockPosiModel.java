package com.aaron.myviews.model.gson;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


public class StockPosiModel implements Parcelable {
	private Double totalCashProfit, totalScoreProfit;
	
	private List<CPosiBo> posiBoList;

	public Double getTotalCashProfit() {
		return totalCashProfit;
	}

	public void setTotalCashProfit(Double totalCashProfit) {
		this.totalCashProfit = totalCashProfit;
	}

	public Double getTotalScoreProfit() {
		return totalScoreProfit;
	}

	public void setTotalScoreProfit(Double totalScoreProfit) {
		this.totalScoreProfit = totalScoreProfit;
	}
	
	

	public List<CPosiBo> getPosiBoList() {
		return posiBoList;
	}

	public void setPosiBoList(List<CPosiBo> posiBoList) {
		this.posiBoList = posiBoList;
	}

	public static class CPosiBo{
		private Integer orderId, orderdetailId , userId, buyType,
		buyCount, quantity;

		private Double operateAmt, openPrice, buyPrice, curPrice,
		lossFund, counterFee, marketValue, rankScore;
		
		private String nickName, stockCode, stockCodeType, stockName,
		proType, incomeRate, curScoreProfit, curCashProfit;

		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public Integer getOrderdetailId() {
			return orderdetailId;
		}

		public void setOrderdetailId(Integer orderdetailId) {
			this.orderdetailId = orderdetailId;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public Integer getBuyType() {
			return buyType;
		}

		public void setBuyType(Integer buyType) {
			this.buyType = buyType;
		}

		public Integer getBuyCount() {
			return buyCount;
		}

		public void setBuyCount(Integer buyCount) {
			this.buyCount = buyCount;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Double getOperateAmt() {
			return operateAmt;
		}

		public void setOperateAmt(Double operateAmt) {
			this.operateAmt = operateAmt;
		}

		public Double getOpenPrice() {
			return openPrice;
		}

		public void setOpenPrice(Double openPrice) {
			this.openPrice = openPrice;
		}

		public Double getBuyPrice() {
			return buyPrice;
		}

		public void setBuyPrice(Double buyPrice) {
			this.buyPrice = buyPrice;
		}

		public Double getCurPrice() {
			return curPrice;
		}

		public void setCurPrice(Double curPrice) {
			this.curPrice = curPrice;
		}

		public Double getLossFund() {
			return lossFund;
		}

		public void setLossFund(Double lossFund) {
			this.lossFund = lossFund;
		}

		public Double getCounterFee() {
			return counterFee;
		}

		public void setCounterFee(Double counterFee) {
			this.counterFee = counterFee;
		}

		public Double getMarketValue() {
			return marketValue;
		}

		public void setMarketValue(Double marketValue) {
			this.marketValue = marketValue;
		}

		public Double getRankScore() {
			return rankScore;
		}

		public void setRankScore(Double rankScore) {
			this.rankScore = rankScore;
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

		public String getStockCodeType() {
			return stockCodeType;
		}

		public void setStockCodeType(String stockCodeType) {
			this.stockCodeType = stockCodeType;
		}

		public String getStockName() {
			return stockName;
		}

		public void setStockName(String stockName) {
			this.stockName = stockName;
		}

		public String getProType() {
			return proType;
		}

		public void setProType(String proType) {
			this.proType = proType;
		}

		public String getIncomeRate() {
			return incomeRate;
		}

		public void setIncomeRate(String incomeRate) {
			this.incomeRate = incomeRate;
		}

		public String getCurScoreProfit() {
			return curScoreProfit;
		}

		public void setCurScoreProfit(String curScoreProfit) {
			this.curScoreProfit = curScoreProfit;
		}

		public String getCurCashProfit() {
			return curCashProfit;
		}

		public void setCurCashProfit(String curCashProfit) {
			this.curCashProfit = curCashProfit;
		}

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
	
	public static final Creator<StockPosiModel> CREATOR = new Creator<StockPosiModel>(){  
		   
		@Override  
		public StockPosiModel createFromParcel(Parcel source) {  
             // TODO Auto-generated method stub  
             // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
			StockPosiModel p = new StockPosiModel();  

			return p;  
		}  
   
         @Override  
         public StockPosiModel[] newArray(int size) {  
             // TODO Auto-generated method stub  
             return new StockPosiModel[size];  
         }  
	}; 
}
