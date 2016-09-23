package com.aaron.myviews.model.newmodel;

import com.hundsun.quote.model.Stock;

/**
 * 自选股对应model
 * @author bvin
 *
 */
public class FavStock extends Stock{
    
    private static final long serialVersionUID = 1L;

    private String stockName;//股票名称
    private String stockCode;//股票代码
    private String stockCodeType;//股票代码类型
    private String createDate;//创建时间
    
    @Override
    public String getStockCode() {
        if (mStockCode!=null) {
            return mStockCode;
        }else {
            return stockCode;
        }
    }
    
    @Override
    public String getCodeType() {
        if (mCodeType!=null) {
            return mCodeType;
        }else {
            return stockCodeType;
        }
    }
    
    @Override
    public String getStockName() {
        if (mStockName!=null) {
            return mStockName;
        }else {
            return stockName;
        }
    }

    public String getCreateDate() {
        return createDate;
    }

    
    @Override
    public boolean isFav() {//主键id不等于空就true
        return id!=null;
    }

    @Override
    public String toString() {
        return "FavStock [id=" + id + ", stockName=" + stockName + ", stockCode=" + stockCode + ", stockCodeType="
                + stockCodeType + ", createDate=" + createDate + "]";
    }
    
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof FavRealtimeStock) {
//            FavRealtimeStock stock = (FavRealtimeStock) o;
//            return stock.getCode().equalsIgnoreCase(getStockCode());
//        }
//        return super.equals(o);
//    }   
    
}
