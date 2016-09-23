package com.aaron.myviews.model.newmodel.account;

/**
 * Created by bvin on 2015/10/23.
 */
public class AuthIdentityInfo{

    /**未审核*/
    public static final int STATUS_UNAPPROVED  = -1;
    /**待审核*/
    public static final int STATUS_PENDING_APPROVE = 0;
    /**已审核*/
    public static final int STATUS_APPROVED = 1;
    /**审核失败*/
    public static final int STATUS_APPROVE_FAIL = 2;

    private int status;
    private String remark;//审核失败原因
    private String image1;
    private String image2;
    private String image3;

    public int getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }
}
