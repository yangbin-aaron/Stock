package com.aaron.myviews.model.newmodel.futures;

/**
 * /user/account/getApply
 *
 * { "code": 200, "msg": "", "msgType": 0, "errparam": "", "data": { "status": 0, "msg": "" } }
 { "code": 200, "msg": "", "msgType": 0, "errparam": "", "data": { "status": 1, "msg": "您的信息已提交，请耐心等待" } }
 */
public class FuturesExchangeStatus {

    public static final int STATUS_APPLIED = 1; // 已经申请开户
    public static final int STATUS_UNAPPLIED = 0;

    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
