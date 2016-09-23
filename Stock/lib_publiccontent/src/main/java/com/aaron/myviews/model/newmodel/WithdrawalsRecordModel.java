package com.aaron.myviews.model.newmodel;

/**
 * Created by Administrator on 2015/10/22.
 */
public class WithdrawalsRecordModel {

    private String id;
    private Double inOutAmt;
    private String createDate;
    private String resultStatus;
    private Integer status;

    public Double getInOutAmt() {
        return inOutAmt;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setWithdraw(){
        resultStatus = "已撤回";
        status = 21;
    }
}
