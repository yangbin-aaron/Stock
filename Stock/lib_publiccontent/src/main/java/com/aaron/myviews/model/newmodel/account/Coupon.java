package com.aaron.myviews.model.newmodel.account;

/**
 * Created by bvin on 2015/11/17.
 */
public class Coupon {

    public final static int STATE_AVAILABLE = 4;
    public final static int STATE_USED = 5;
    public final static int STATE_EXPIRED = 7;

    public final static int SIDE_AVAILABLE = STATE_AVAILABLE;
    public final static int SIDE_HISTORY = -1;

    private int id ;//本条记录唯一标识
    private int couponId ;//本条记录所属抵用券ID
    private String couponName ;//抵用券名称
    private String startTime ;//有效期起始时间
    private String endTime ;//有效期结束时间
    private double amount ;//面额
    private String receiveTime ;//领取时间
    private int status ;//状态（0新建、1未激活、2已激活、3已分发、4已绑定或已领取、5已使用、6已作废、7已过期）
    private String number ;//抵用券券号
    private int variety ;//支持品种(1;//沪金(au),2;//沪银(ag),3;//期指(IF),4;//螺纹钢(rb);//,5;//沪铜(cu),6;//沪铝(al),7;//沥青(bu),8;//镍(ni),9;//天然橡胶(ru),10;//白糖(SR),1001;//A50(CN))
    private String description ;//描述
    private String useTypeName ;//用途描述

    public int getId() {
        return id;
    }

    public int getCouponId() {
        return couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public int getStatus() {
        return status;
    }

    public boolean isAvailable(){
        return status == STATE_AVAILABLE;
    }

    public String getNumber() {
        return number;
    }

    public int getVariety() {
        return variety;
    }

    public String getDescription() {
        return description;
    }

    public String getUseTypeName() {
        return useTypeName;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", couponName='" + couponName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", amount=" + amount +
                ", receiveTime='" + receiveTime + '\'' +
                ", status=" + status +
                ", number='" + number + '\'' +
                ", variety=" + variety +
                ", description='" + description + '\'' +
                ", useTypeName='" + useTypeName + '\'' +
                '}';
    }
}
