package com.aaron.myviews.model.gson;

/**
 * Created by yangbin on 16/9/19.
 * 汇付宝支付信息
 */
public class PrepareInfoHFBModel {
    private String billNo;
    private String tokenId;
    private String agentId;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "PrepareInfoData{" +
                "billNo='" + billNo + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}
