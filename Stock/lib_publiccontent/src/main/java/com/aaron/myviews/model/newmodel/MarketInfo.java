
/** 
 * 市场状态
 * @Title: MarketInfo.java 
 * @Package com.luckin.magnifier.model.newmodel 
 * @Description: TODO 
 * @ClassName: MarketInfo 
 *
 * @author 于泽坤 
 * @date 2015-7-3 下午2:33:12 
*/
 
package com.aaron.myviews.model.newmodel;

import com.aaron.myviews.utils.DateUtil;

import java.io.Serializable;


public class MarketInfo implements Serializable {

    /**序列化参数传递时的key*/
    public static final String KEY = "MarketInfo";

    public static final int STATUS_INVALID = 0;//不可交易
    public static final int STATUS_BUYABLE = 1;//可买
    public static final int STATUS_SALEABLE = 2;//可卖
    public static final int STATUS_BUYABLE_AND_SALEABLE = 3;//可买也可卖
    
    public static final int MARKET_ID_STOCK_SHANGHAI = 1;//上证股票
    public static final int MARKET_ID_STOCK_SHENZHENG = 2;//深证股票
    public static final int MARKET_ID_FUTURES_SHANGHAI = 3;//上海期货-金银
    public static final int MARKET_ID_STOCK_INDEX = 4;//中金所-期指
    
    private Integer status;
    private String desc;
    private Long end_time;
    private Long start_time;
    
    public MarketInfo() {
        this.status = 3;
        this.desc = "开市";
    }

    public static int getMarketId(Product product) {
        if (product.getId() == Product.ID_AU || product.getId() == Product.ID_AG) {
            return MARKET_ID_FUTURES_SHANGHAI;
        } else if (product.getId() == Product.ID_IF) {
            return MARKET_ID_STOCK_INDEX;
        }
        return -1;
    }

    /**
     * 获取结束时间
     * @return end_time如果为空返回0
     */
    public Long getEndTime() {
        if (end_time!=null) {
            return end_time;
        }else {
            return 0L;
        }
    }
    
    /**
     * 获取格式化后的结束时间
     * @return end_time如果为空返回null
     */
    public String getFormattedEndTime() {
        if (end_time!=null) {
            return DateUtil.getFormattedTimestamp(end_time);
        }else {
            return null;
        }
    }
    
    /**
     * 获取开始时间
     * @return start_time如果为空返回0
     */
    public Long getStartTime() {
        if (start_time!=null) {
            return start_time;
        }else {
            return 0L;
        }
    }
    
    /**
     * 获取格式化后的开始时间
     * @return start_time如果为空返回null
     */
    public String getFormattedStartTime() {
        if (start_time!=null) {
            return DateUtil.getFormattedTimestamp(start_time);
        }else {
            return null;
        }
    }

    public Integer getMarketStatus() {
        return status;
    }
    
    public void setMarketStatus(Integer marketStatus) {
        this.status = marketStatus;
    }
    
    public String getMarketDesc() {
        return desc;
    }
    
    public void setMarketDesc(String marketDesc) {
        this.desc = marketDesc;
    }

    /**
     * 允许交易，市场状态码为3
     * @return
     */
    public boolean allowTrade() {
        return status == STATUS_BUYABLE_AND_SALEABLE;
    }
    
    /**
     * 允许买，市场状态为1或3
     * @return
     */
    public boolean allowSell() {
        return status == STATUS_BUYABLE ||allowTrade();
    }

    /**
     * 不允许交易，市场状态为0
     * @return
     */
    public boolean isInvalid() {
        return status == STATUS_INVALID;
    }
    
    @Override
    public String toString() {
        return "MarketInfo [status=" + status + ", desc=" + desc + ", end_time=" + getFormattedEndTime() + ", start_time="
                + getFormattedStartTime() + "]";
    }
}

