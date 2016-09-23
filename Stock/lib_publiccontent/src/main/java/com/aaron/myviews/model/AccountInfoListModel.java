package com.aaron.myviews.model;

/**
 * Created by Yuan on 16/2/25.
 */
public class AccountInfoListModel {

    /**
     * 模拟交易
     */
    public static final String CODE_SCORE = "score";

    /**
     * 财牛账户
     */
    public static final String CODE_CAINIU = "cainiu";

    /**
     * 南方贵金属交易所
     */
    public static final String CODE_NANJS = "nanjs";

    /**
     * 方正证券
     */
    public static final String CODE_FANGZ = "fangz";

    /** 0未开户/未绑定 */
    public static final int STATUS_NO_OPEN = 0;

    /** 1已开户 */
    public static final int STATUS_OPENED = 1;

    /** 0 模拟账户 */
    public static final int TYPE_MOCK = 0;

    /** 1 现金账户 */
    public static final int TYPE_CASH = 1;

    /**
     * code： 账户编码 cainiu(财牛) score(模拟) nanjs(南交所现货)，当前只会有这三个
     */
    private String code;

    private String url;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 账户名
     */
    private String name;

    /**
     * status：账户状态  0未开户/未绑定    1已开户
     */
    private int status;

    /**
     * type：账户类型    0 模拟账户    1现金账户
     */
    private int type;

    /**
     * amt：账户余额，未绑定时可能为null
     */
    private double amt;

    /**
     * floatAmt: 浮动盈亏，当前没有值，后续根据需求决定是否添加
     */
    private Double floatAmt;

    public AccountInfoListModel() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public void setFloatAmt(Double floatAmt) {
        this.floatAmt = floatAmt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public Double getFloatAmt() {
        return floatAmt;
    }

    public double getAmt() {
        return amt;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AccountInfoListModel{" +
                "code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", amt=" + amt +
                ", floatAmt=" + floatAmt +
                '}';
    }
}
