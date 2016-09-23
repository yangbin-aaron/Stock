package com.aaron.myviews.model.newmodel.account;

/**
 /user/user/checkUserName

 * {
 "code": 200,
 "msg": "查询成功",
 "errparam": "",
 "data": {
     "status": 1,
     "userName": "张三",
     "idCardNum": "330****3245"
     }
 }

 status  1:已认证，0:未认证
 userName 认证姓名
 idCardNum 身份证号

 */
public class CheckUsername extends CheckStatus {

    public static final int STATUS_BINDING  = 1;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_FILL_IN = 2;

    private String userName;
    private String idCardNum;

    public String getUserName() {
        return userName;
    }

    public String getIdCardNum() {
        return idCardNum;
    }
}

