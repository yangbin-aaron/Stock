package com.aaron.myviews.model.gson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  HoldingStock 的子集，考虑以后合并用同一个或者继承
 */
public class RankPersonHoldingStock implements Parcelable {
	
    private Long id;
    private String displayId;
    private String stockId;
    private String fundType;
    private String stockCode;

    private String stockName;
    private String typeCode;
    private String buyPrice;
    private String factBuyCount;
    private String tradeDayCount;

    private String cashFund;
    private String buyDate;
    
    public String getBuyDate() {
        return buyDate;
    }
    
    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getStockId() {
        return stockId;
    }
    
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getCashFund() {
        return cashFund;
    }

    public void setCashFund(String cashFund) {
        this.cashFund = cashFund;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDisplayId() {
        return displayId;
    }
    
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }
    
    public String getFundType() {
        return fundType;
    }
    
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }
    
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    
    public String getTypeCode() {
        return typeCode;
    }

    
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }
    
    public String getFactBuyCount() {
        return factBuyCount;
    }
    
    public void setFactBuyCount(String factBuyCount) {
        this.factBuyCount = factBuyCount;
    }

    public String getTradeDayCount() {
        return tradeDayCount;
    }

    public void setTradeDayCount(String tradeDayCount) {
        this.tradeDayCount = tradeDayCount;
    }

    @Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(id);
		dest.writeString(displayId);
		dest.writeString(fundType);
		
		dest.writeString(stockCode);
		dest.writeString(stockName);
		dest.writeString(typeCode);
		
		dest.writeString(buyPrice);
		dest.writeString(factBuyCount);
		dest.writeString(tradeDayCount);
		
		dest.writeString(cashFund);
		dest.writeString(stockId);
		dest.writeString(buyDate);
		
	}
	 public static final Creator<RankPersonHoldingStock> CREATOR = new Creator<RankPersonHoldingStock>()    {    
			    
		 @Override    
		 public RankPersonHoldingStock createFromParcel(Parcel source) {    
			 RankPersonHoldingStock model = new RankPersonHoldingStock();
			 
			 model.setId(source.readLong());
			 model.setDisplayId(source.readString());
			 model.setFundType(source.readString());
			 
			 model.setStockCode(source.readString());
			 model.setStockName(source.readString());
			 model.setTypeCode(source.readString());
			 
			 model.setBuyPrice(source.readString());
			 model.setFactBuyCount(source.readString());
			 model.setTradeDayCount(source.readString());
			 
			 model.setCashFund(source.readString());
			 model.setStockId(source.readString());
			 model.setBuyDate(source.readString());

			 return model;    
		 }    
			    
		 @Override    
		 public RankPersonHoldingStock[] newArray(int size) {    
			 return new RankPersonHoldingStock[size];    
		 }    
			            
	};   

}
