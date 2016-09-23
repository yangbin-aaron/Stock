package com.aaron.myviews.model.newmodel;

/**
 * 获取操盘配置信息 实际操盘金额
 */
public class FinancyAllocation {

    private static final String IS_ENABLE = "1";

    private String financyAllocation;
    private String status;

    public String getFinancyAllocation() {
        int intFinancyAllocation = Double.valueOf(financyAllocation).intValue();
        return String.valueOf(intFinancyAllocation);
    }

    public boolean isEnable() {
        return status.equals(IS_ENABLE);
    }
}
