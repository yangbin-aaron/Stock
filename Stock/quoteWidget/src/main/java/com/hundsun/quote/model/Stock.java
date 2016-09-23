package com.hundsun.quote.model;

import java.io.Serializable;

import com.hundsun.quote.tools.SpellDateUtil;

public class Stock implements Serializable{

	/**
	 * UID
	 */
	private static final long serialVersionUID = -2128509187340809098L;

	protected String mStockCode;
	protected String mCodeType;//考虑精简类型MIC  , 如何设计获取类型的接口问服务器
	protected String mStockName;
	protected String mStockLetter;//股票字母
	protected double mPreClosePrice;
	protected boolean isFav;
	
	protected Integer id;//主键id
	
	public  Stock(){}
	public  Stock(String code , String type){
		mStockCode = code;
		mCodeType = type;
	}

	public String getStockCode() {
		return mStockCode;
	}

	public void setStockCode(String mStockcode) {
		this.mStockCode = mStockcode;
	}

	public String getCodeType() {
		return mCodeType;
	}
	
	/**
	 * 获取市场类型，一定要在{@link #setStockCode(String)}之后调用
	 * @return mCodeType以\\.分割成数组的第一个元素
	 */
	public String getMarketType(){
		if (mCodeType == null || mCodeType.isEmpty()) {
			return null;
		}
		return mCodeType.split("\\.")[0];
	}

	public void setCodeType(String mCodeType) {
		this.mCodeType = mCodeType;
	}

	public String getStockName() {
		return mStockName;
	}

	public void setStockName(String mStockName) {
		//同时设置股票字母
		if(mStockName!=null &&mStockName.length()>0)
		setStockLetter(SpellDateUtil.getSpell(mStockName).toUpperCase());
		this.mStockName = mStockName;
	}

	public double getPreClosePrice() {
		return mPreClosePrice;
	}

	public void setPreClosePrice(double mPrevPrice) {
		this.mPreClosePrice = mPrevPrice;
	}
	
	public String getStockLetter() {
		return mStockLetter;
	}
	
	public void setStockLetter(String mStockLetter) {
		this.mStockLetter = mStockLetter;
	}
	
    public boolean isFav() {
        return isFav;
    }
    
    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }
    
    /**获取自选股Id*/
    public Integer getFavId() {
        return id;
    }
    
    /**设置自选股Id*/
    public void setFavId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Stock [mStockCode=" + mStockCode + ", mCodeType=" + mCodeType + ", mStockName=" + mStockName
                + ", mStockLetter=" + mStockLetter + ", mPreClosePrice=" + mPreClosePrice + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Stock) {
            Stock stock = (Stock) o;
            //被比较对象和本身StockCode不能为空
            if (stock!=null&&getStockCode()!=null) {
                String thisCode = getStockCode().trim();
                String thatCode = stock.getStockCode().trim();
                //忽略大小写和空格
                return thisCode.equalsIgnoreCase(thatCode);
            }else {
                return false;
            }
        }
        return super.equals(o);
    }	
	
	
}
