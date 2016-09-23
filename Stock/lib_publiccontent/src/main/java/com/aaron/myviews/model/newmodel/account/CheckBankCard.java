package com.aaron.myviews.model.newmodel.account;

/**
 /user/user/checkBankCard

 * {
 "code": 200,
 "msg": "查询成功",
 "errparam": "",
 "data": {
     "id": 1,
     "bankNum": "1111",
     "provName": "江苏",
     "status": 1,
     "cityName": "南京",
     "bankName": "招商银行",
     "branName": "招商南京支行"
     }
 }

 {
 "code": 200,
 "msg": "查询成功",
 "errparam": "",
 "data": {
     "status": 0,
     "bankNum": null,
     "bankName": null,
     }
 }

 status   1:已绑定，不可改；0:未填写；2:已填写，可修改
 id 唯一标示id
 bankName 银行卡名
 bankNum 卡号末四位
 provName开户省
 cityName开户市
 branName开户支行

 */
public class CheckBankCard extends CheckStatus {

    private long id;
    private String bankName;
    private String bankNum;
    private String provName;
    private String cityName;
    private String branName;

    public long getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNum() {
        return bankNum;
    }

    public String getProvName() {
        return provName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getBranName() {
        return branName;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
