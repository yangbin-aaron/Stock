package com.aaron.myviews.model.newmodel;


/**
 * 用户绑定的银行卡信息</br>
 * {@link }接口输出data数据.
 * @author bvin
 */
public class BankcardInfo {

    private Integer id;//银行卡惟一标识id
    private Integer status;// 1,
    private String bankName;// 银行卡名
    private String bankNum;// 卡号末四位
    private String provName;// 开户省
    private String cityName;// 开户市
    private String branName;// 开户支行
    
    /**
     * 惟一标识id
     * @return
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * 获取银行卡绑定状态
     * @return 1:已绑定，不可改；0:未填写；2:已填写，可修改
     */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 银行名称
     * @return
     */
    public String getBankName() {
        return bankName;
    }
    
    /**
     * 卡号末四位
     * @return
     */
    public String getBankNum() {
        return bankNum;
    }
    
    /**
     * 开户省
     * @return
     */
    public String getProvName() {
        return provName;
    }
    
    /**
     * 开户市
     * @return
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * 开户支行
     * @return
     */
    public String getBranName() {
        return branName;
    }
    
    /**
     * 是否有效
     * @return true意味着已绑定或已记录，false表示未记录
     */
    public boolean isValid() {
        if (status!=null) {
            if (status==1||status==2) {
                return true;
            }
            if (status==0) {
                return false;
            }
        }
        return false;
    }
}
