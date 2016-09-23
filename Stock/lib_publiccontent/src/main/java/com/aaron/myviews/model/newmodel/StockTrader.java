package com.aaron.myviews.model.newmodel;

import java.io.Serializable;

public class StockTrader implements Serializable {
    
	private static final long serialVersionUID = 917274074499917434L;

	public static final int FUND_TYPE_MONEY =  0;
	public static final int FUND_TYPE_SCORE =  1;
	
	private Integer id;

	private Integer fundType; // 0 现金  1 积分

	//	FUND_TYPE_MONEY(0,"现金"),
	//	FUND_TYPE_SCORE(1,"积分"),

	private Double financyAllocation;//配资额度

	private String multiple;//配资倍数

	private Double cashFund;//保证金

	private Double counterFee;//手续费

	private Double interest;//实扣服务费
	
	private Double theoryInterest;//应扣服务费

	private Double maxLoss;//允许最大亏损额度

	private Double warnAmt;//预警通知金额

	private int status;//0 不可用  1 可用

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public Double getFinancyAllocation() {
		return financyAllocation;
	}

	public void setFinancyAllocation(Double financyAllocation) {
		this.financyAllocation = financyAllocation;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	/**
	 * 获取保证金
	 * @return
	 */
	public Double getCashFund() {
		return cashFund;
	}

	public void setCashFund(Double cashFund) {
		this.cashFund = cashFund;
	}

	public Double getCounterFee() {
		return counterFee;
	}

	public void setCounterFee(Double counterFee) {
		this.counterFee = counterFee;
	}

	public Double getInterest() {
		return interest;
	}

	//实扣服务费
	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getMaxLoss() {
		return maxLoss;
	}

	public void setMaxLoss(Double maxLoss) {
		this.maxLoss = maxLoss;
	}

	public Double getWarnAmt() {
		return warnAmt;
	}

	public void setWarnAmt(Double warnAmt) {
		this.warnAmt = warnAmt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 应扣服务费
	 * @return
	 */
	public Double getTheoryInterest() {
        return theoryInterest;
    }

    @Override
    public String toString() {
        return "StockTrader [id=" + id + ", fundType=" + fundType + ", financyAllocation=" + financyAllocation
                + ", multiple=" + multiple + ", cashFund=" + cashFund + ", counterFee=" + counterFee + ", interest="
                + interest + ", theoryInterest=" + theoryInterest + ", maxLoss=" + maxLoss + ", warnAmt=" + warnAmt
                + ", status=" + status + "]";
    }


	
}
