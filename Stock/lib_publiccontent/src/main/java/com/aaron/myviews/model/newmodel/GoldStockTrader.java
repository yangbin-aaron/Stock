package com.aaron.myviews.model.newmodel;

import android.text.TextUtils;

import com.aaron.myviews.utils.FinancialUtil;

import java.util.regex.PatternSyntaxException;

/**
 * /financy/financy/getStockTraderList沪金接口返回数据
 * @author bvin
 *
 */
public class GoldStockTrader extends StockTrader{


    private static final long serialVersionUID = 8327098435242310389L;
    
    private String maxProfit;//最大止盈 
    private Double defaultProfit;//默认止盈
    private int defaultProfitsIndex;
    private Integer isEnabled;//0 不可买  1 可买 
    private Integer isDefault;//1就是默认的 
    
    private Double theoryCounterFee;//应扣手续费

    private String currency;//币种
    private Double rate;//汇率

    /**
     * 获取默认止盈数组的的索引，必须在调用{@link #getMaxProfits()}之后才能取到正确的值
     * @return 止盈数组的的索引
     */
    public int defaultProfitsIndex() {
        return defaultProfitsIndex;
    }
    
    /**
     * 获取止盈选项
     * @return 止盈数组
     */
    public double[] getMaxProfits() {
        String separator  = ",";
        if (!TextUtils.isEmpty(maxProfit)) {
            String[] maxProfitArray = maxProfit.split(separator);
            double[] maxProfits = new double[maxProfitArray.length];
            for (int i = 0; i < maxProfits.length; i++) {
                try {
                    maxProfits[i] = Double.valueOf(maxProfitArray[i]);
                    if(maxProfits[i] == defaultProfit) defaultProfitsIndex = i;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    //TODO 数字格式化异常了就不赋值，默认0
                }
            }
            return maxProfits;
        }
        return null;
    }
    
    /**应扣手续费*/
    public Double getTheoryCounterFee() {
        return theoryCounterFee;
    }


    /**
     * 获取是否可买
     * @return 0 不可买  1 可买
     */
    public boolean isEnabled() {
        if (isEnabled == 0) {
            return false;
        }else if (isEnabled == 1) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否默认档
     * @return 1就是默认 0不是
     */
    public boolean isDefault() {
        if (0 == isDefault) {
            return false;
        }else if (1 == isDefault) {
            return true;
        }
        return false;
    }
    
    

    public Double getDefaultProfit() {
        return defaultProfit;
    }

    /**
     * 获取交易数量配置数组
     * @return 如果返回的multiple字段是正确的数字就返回交易数量配置数组
     */
    public int[] getTraderCount() {
        String traderCountStr = getMultiple();
        String separator  = ",";
        try {
            String[] traderCountArray = traderCountStr.split(separator);
            int[] traderCounts = new int[traderCountArray.length];
            for (int i = 0; i < traderCounts.length; i++) {
                try {
                    traderCounts[i] = Integer.valueOf(traderCountArray[i]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    //TODO 数字格式化异常了就不赋值，默认0
                }
            }
            return traderCounts;
        } catch (NullPointerException  e) {
            e.printStackTrace();
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getRate() {
        if (rate != null) {
            return rate;
        }
        return 1D;
    }

    /**
     * 保留4位小数的汇率
     * @return if rate不等于null，返回格式化的汇率
     */
    public String getScaledRate(){
        if (rate!=null){
            return FinancialUtil.formatWithScale(rate,4);
        }else{
            return null;
        }
    }
}
