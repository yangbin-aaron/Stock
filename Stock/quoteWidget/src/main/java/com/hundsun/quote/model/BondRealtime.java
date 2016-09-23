package com.hundsun.quote.model;

/**
 * 债券快照
 * @author huangcheng、LiangHao
 */
public class BondRealtime extends Realtime{
	/*国债利率*/
	protected int mTreasuryBondsRate;
	/*债券加权平均委买价格*/
	protected double mBondsWeightAvgBuyPrice;
	/*债券加权平均委卖价格*/
	protected double mBondsWeightAvgSellPrice;
	/*行业分级*/
	protected int mIndustryLevel;
	
	public int getTreasuryBondsRate() {
		return mTreasuryBondsRate;
	}
	public void setTreasuryBondsRate(int mTreasuryBondsRate) {
		this.mTreasuryBondsRate = mTreasuryBondsRate;
	}
	public double getBondsWeightAvgBuyPrice() {
		return mBondsWeightAvgBuyPrice;
	}
	public void setBondsWeightAvgBuyPrice(double mBondsWeightAvgBuyPrice) {
		this.mBondsWeightAvgBuyPrice = mBondsWeightAvgBuyPrice;
	}
	public double getBondsWeightAvgSellPrice() {
		return mBondsWeightAvgSellPrice;
	}
	public void setBondsWeightAvgSellPrice(double mBondsWeightAvgSellPrice) {
		this.mBondsWeightAvgSellPrice = mBondsWeightAvgSellPrice;
	}
	public int getIndustryLevel() {
		return mIndustryLevel;
	}
	public void setIndustryLevel(int mIndustryLevel) {
		this.mIndustryLevel = mIndustryLevel;
	}
	
}
