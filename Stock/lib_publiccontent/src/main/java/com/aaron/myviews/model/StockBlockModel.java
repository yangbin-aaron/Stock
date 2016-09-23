package com.aaron.myviews.model;

public class StockBlockModel {

    private String strBlockName;
    private String strChangePercent;
    private String strStockName;
    private String strStockCode;
    private String strBlockCode;
    private String strChange;
    private String strPrice;

    private int nFallCount;
    private int nRiseCount;
    private int nTotalStocks;

    public String getBlockName() {
        return strBlockName;
    }

    public void setBlockName(String BlockName) {
        this.strBlockName = BlockName;
    }

    public String getChangePercent() {
        return strChangePercent;
    }

    public void setChangePercent(String ChangePercent) {
        this.strChangePercent = ChangePercent;
    }

    public String getStockName() {
        return strStockName;
    }

    public void setStockName(String StockName) {
        this.strStockName = StockName;
    }

    public String getStockCode() {
        return strStockCode;
    }

    public void setStockCode(String StockCode) {
        this.strStockCode = StockCode;
    }

    public String getBlockCode() {
        return strBlockCode;
    }

    public void setBlockCode(String BlockCode) {
        this.strBlockCode = BlockCode;
    }

    public String getChange() {
        return strChange;
    }

    public void setChange(String Change) {
        this.strChange = Change;
    }

    public String getPrice() {
        return strPrice;
    }

    public void setPrice(String Price) {
        this.strPrice = Price;
    }

    public int getFallCount() {
        return nFallCount;
    }

    public void setFallCount(int FallCount) {
        this.nFallCount = FallCount;
    }

    public int getRiseCount() {
        return nRiseCount;
    }

    public void setRiseCount(int RiseCount) {
        this.nRiseCount = RiseCount;
    }

    public int getTotalStocks() {
        return nTotalStocks;
    }

    public void setTotalStocks(int TotalStocks) {
        this.nTotalStocks = TotalStocks;
    }


}
