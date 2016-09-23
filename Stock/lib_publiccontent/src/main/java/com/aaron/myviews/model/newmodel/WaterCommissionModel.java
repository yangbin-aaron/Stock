package com.aaron.myviews.model.newmodel;

/**
 * Created by Administrator on 2015/10/27.
 */
public class WaterCommissionModel {

    /**
     "inOutCommissions":金额,
     "createDate": 时间,
     "status":
     0：审核中
     1：成功
     2：失败

     */

    private Double inOutCommissions;
    private String createDate;
    private String statusRemark;
    private Integer status;

    public Double getInOutCommissions() {
        return inOutCommissions;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public String getStatus() {
        if (status != null) {
            if (status == 0) {
                return "审核中";
            } else if (status == 1) {
                return "成功";
            } else if (status == 2) {
                return "失败";
            }
        }
        return "";
    }
}
