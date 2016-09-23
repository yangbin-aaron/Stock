package com.hundsun.quote.model;

/**
 * 财务数据
 * 财务指标的字段改为存成字符串 ， 格式化好
 * @author huangcheng 、LiangHao
 *
 */
public class FinancialItem {
	/*总股数*/
	protected String mTotalShares;
	/*流通股*/
	protected String mCirculationShares;
	/*每股净资产*/
	protected String mNetAsset;
	/*净资产收益率*/
	protected String mROE;
	/*市盈率*/
	protected String mPE;
	/*每股收益*/
	protected String mEPS;
	/*股东人数*/
	protected String mStockHolders;
	/*市值  LiangHao添加 2014-10-23*/
	protected String mMarketValue;
	/*流通市值  黄成添加 2014-11-19*/
	protected String mCirculationValue;
	/*流通股*/
	protected float mfCirculationShares;
	
	public String getTotalShares() {
		return mTotalShares;
	}

	public void setTotalShares(String totalShares) {
		this.mTotalShares = totalShares;
	}

	public String getPE() {
		return mPE;
	}

	public void setPE(String pe) {
		this.mPE = pe;
	}

	public String getCirculationShares() {
		return mCirculationShares;
	}

	public void setCirculationShares(String circulationShares) {
		this.mCirculationShares = circulationShares;
	}

	public String getNetAsset() {
		return mNetAsset;
	}

	public void setNetAsset(String netAsset) {
		this.mNetAsset = netAsset;
	}

	public String getROE() {
		return mROE;
	}

	public void setROE(String roe) {
		this.mROE = roe;
	}

	public String getEPS() {
		return mEPS;
	}

	public void setEPS(String eps) {
		this.mEPS = eps;
	}

	public String getStockHolders() {
		return mStockHolders;
	}

	public void setStockHolders(String stockHolders) {
		this.mStockHolders = stockHolders;
	}

	public String getMarketValue() {
		return mMarketValue;
	}

	public void setMarketValue(String marketValue) {
		this.mMarketValue = marketValue;
	}
	
	public String getCirculationValue() {
		return mCirculationValue;
	}

	public void setCirculationValue(String marketValue) {
		this.mCirculationValue = marketValue;
	}
}
