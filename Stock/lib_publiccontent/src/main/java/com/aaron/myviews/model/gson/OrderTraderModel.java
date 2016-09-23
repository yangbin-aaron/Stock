package com.aaron.myviews.model.gson;

public class OrderTraderModel {
	private String id, cashFundOption, scoreFundOption, lowestFee, lowestScore, highestFee,
    		highestScore, counterFeeOption, counterScoreOption, dayFee, dayScore, opAmt, opScore,
    		type,maxLoss;
	
	public String getCounterFee(int index){
		String[] strArray = counterFeeOption.split("\\,");
		if(strArray.length > index){
			return strArray[index];
		}

		return "";
	}

	public String getCounterScore(int index){
		String[] strArray = counterScoreOption.split("\\,");
		if(strArray.length > index){
			return strArray[index];
		}

		return "";
	}

	public String getCashFund(int index){
		String[] strArray = cashFundOption.split("\\,");
		if(strArray.length > index){
			return strArray[index];
		}

		return "";
	}
	
	public String getScoreFund(int index){
		
		String[] strArray = scoreFundOption.split("\\,");
		if(strArray.length > index){
			return strArray[index];
		}

		return "";
	}
	
	public String getMaxLossOption(int index){
		if(index > 1){
			return "";
		}
		
		String[] strArray = maxLoss.split("\\,");
		if(strArray.length > index){
			return strArray[index];
		}

		return "";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCashFundOption() {
		return cashFundOption;
	}

	public void setCashFundOption(String cashFundOption) {
		this.cashFundOption = cashFundOption;
	}

	public String getScoreFundOption() {
		return scoreFundOption;
	}

	public void setScoreFundOption(String scoreFundOption) {
		this.scoreFundOption = scoreFundOption;
	}

	public String getLowestFee() {
		return lowestFee;
	}

	public void setLowestFee(String lowestFee) {
		this.lowestFee = lowestFee;
	}

	public String getLowestScore() {
		return lowestScore;
	}

	public void setLowestScore(String lowestScore) {
		this.lowestScore = lowestScore;
	}

	public String getHighestFee() {
		return highestFee;
	}

	public void setHighestFee(String highestFee) {
		this.highestFee = highestFee;
	}

	public String getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}

	public String getCounterFeeOption() {
		return counterFeeOption;
	}

	public void setCounterFeeOption(String counterFeeOption) {
		this.counterFeeOption = counterFeeOption;
	}

	public String getCounterScoreOption() {
		return counterScoreOption;
	}

	public void setCounterScoreOption(String counterScoreOption) {
		this.counterScoreOption = counterScoreOption;
	}

	public String getDayFee() {
		return dayFee;
	}

	public void setDayFee(String dayFee) {
		this.dayFee = dayFee;
	}

	public String getDayScore() {
		return dayScore;
	}

	public void setDayScore(String dayScore) {
		this.dayScore = dayScore;
	}

	public String getOpAmt() {
		return opAmt;
	}

	public void setOpAmt(String opAmt) {
		this.opAmt = opAmt;
	}

	public String getOpScore() {
		return opScore;
	}

	public void setOpScore(String opScore) {
		this.opScore = opScore;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaxLoss() {
		return maxLoss;
	}

	public void setMaxLoss(String maxLoss) {
		this.maxLoss = maxLoss;
	}
	
	
}
