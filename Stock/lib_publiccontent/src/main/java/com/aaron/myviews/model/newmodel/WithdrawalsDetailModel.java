package com.aaron.myviews.model.newmodel;

/**
 * Created by Administrator on 2015/10/23.
 */
public class WithdrawalsDetailModel {

    /**
     * id: 提现记录id
     factInAmt;//实际到账金额
     inOutAmt;//提现金额
     factTax;//交易手续费
     payId;//流水号
     bankInfo;//提现银行
     createDate;//提现时间
     doneDate;//到账时间
     remark;//备注
     resultStatus://状态描述
     stats: 	0,"待审核"
     1,"待转帐"
     2,"拒绝"
     8,"转账中"
     9,"转账失败"
     10,"转账成功"
     21,"已撤回"
     */

    private String id;
    private Double factInAmt;
    private Double inOutAmt;
    private Double factTax;
    private String payId;
    private String bankInfo;
    private String createDate;
    private String doneDate;
    private String remark;
    private String resultStatus;
    private Integer status;



    public String getId() {
        return id;
    }

    public Double getFactInAmt() {
        return factInAmt;
    }

    public Double getInOutAmt() {
        return inOutAmt;
    }

    public Double getFactTax() {
        return factTax;
    }

    public String getPayId() {
        return payId;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public String getRemark() {
        return remark;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public Integer getStatus() {
        return status;
    }
}
