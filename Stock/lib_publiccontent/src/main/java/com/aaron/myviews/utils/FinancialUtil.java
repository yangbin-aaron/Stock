package com.aaron.myviews.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.aaron.myviews.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FinancialUtil {

    public static final String UNIT_YUAN = "元";
    public static final String UNIT_WANG = "万";
    public static final String UNIT_YI = "亿";
    public static final String UNIT_SCORE = "积分";
    public static final String UNIT_DOLLAR = "美元";
    public static final String UNIT_HK_DOLLAR = "港币";

    public static final String UNIT_SIGN_CNY = "￥";
    public static final String UNIT_SIGN_USD = "$";
    public static final String UNIT_SIGN_HKD = "HK$";

    /**
     * 按比例缩放金额单位字体的大小，同时拼接数字与单位字符串
     * @param numberStr 金额值
     * @param proportion 比例值，单位字体大小 = 原字体大小 * proportion
     * @param unit 单位
     * @return 处理后的字符串
     */
    public static SpannableString scaleUnitFontSize(String numberStr, float proportion, String unit) {
        return scaleUnitFontSize(numberStr, proportion, unit, Color.TRANSPARENT);
    }

    /**
     * 按比例缩放金额单位字体的大小，同时拼接数字与单位字符串，同时给单位设置颜色
     * @param numberStr 金额值
     * @param proportion 比例值，单位字体大小 = 原字体大小 * proportion
     * @param unit 单位
     * @return 处理后的字符串
     */
    public static SpannableString scaleUnitFontSize(String numberStr, float proportion, String unit, int unitColor) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(numberStr)) {
            int start = numberStr.length();
            numberStr = numberStr + unit;
            int end = numberStr.length();
            res = new SpannableString(numberStr);
            res.setSpan(new RelativeSizeSpan(proportion), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (unitColor != Color.TRANSPARENT) {
                res.setSpan(new ForegroundColorSpan(unitColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return res;
    }

    /**
     * 按比例缩放数字小数部分的大小
     * @param numberStr 数字
     * @param proportion 比例值，单位字体大小 = 原字体大小 * proportion
     * @return 处理后的字符串
     */
    public static SpannableString scaleDecimalFontSize(String numberStr, float proportion) {
        if (!TextUtils.isEmpty(numberStr)) {
            int decimalPointIndex = numberStr.indexOf(".");
            if (decimalPointIndex != -1) {
                String integerAndPointPart = numberStr.substring(0, decimalPointIndex + 1);
                String decimalPart = numberStr.substring(decimalPointIndex + 1, numberStr.length());
                return scaleUnitFontSize(integerAndPointPart, proportion, decimalPart);
            }
            return scaleUnitFontSize(numberStr, proportion, "");
        }
        return null;
    }

    /**
     * 格式化 double 数据到 2位 小数的float
     * @param value
     * @return
     */
    public static float accurateToFloat(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).floatValue();
    }

    /**
     * 格式化 Double 数据成百分数格式，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return
     */
    public static String formatToPercentage(Double value) {
        BigDecimal bigDecimal = BigDecimalUtil.multiply(value, 100d);
        return accurateTheSecondDecimalPlace(bigDecimal.doubleValue()) + "%";
    }

    /**
     * 使用‘银行家算法’精确（保留）到小数点后两位，请传入合法字符串，or NumberFormatException
     * @param value
     * @return 处理后的字符串
     */
    public static String accurateTheSecondDecimalPlace(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).toString();
    }

    /**
     * 使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String accurateTheSecondDecimalPlace(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).toString();
    }
    
    /**
     * 使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String accurateTheSecondDecimalPlace(Long value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).toString();
    }

    public static String accurateTheNearestInteger(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(0, RoundingMode.HALF_EVEN).toString();
    }

    public static String accurateTheFirstDecimalPlace(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(1, RoundingMode.HALF_EVEN).toString();
    }

    /**
     * 当数字字符串大于 10,000 或小于 -10,000 时候，添加‘万’单位，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String addUnitWhenBeyondTenThousand(BigDecimal value) {
        if (value.abs().doubleValue() > 10000) {
            return formatWithThousandsSeparatorAndUnit(value, UNIT_WANG);
        }
        return formatWithThousandsSeparator(value);
    }

    /**
     * 当数字字符串大于 100,000 或小于 -100,000 时候，添加‘万’单位，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String addUnitWhenBeyondHundredThousand(BigDecimal value) {
        if (value.abs().doubleValue() > 100000) {
            return formatWithThousandsSeparatorAndUnit(value, UNIT_WANG);
        }
        return formatWithThousandsSeparator(value);
    }
    
    /**
     * 当数字字符串大于 100,000 或小于 -100,000 时候，添加‘万’单位，并使用‘银行家算法’精确（保留）整数
     * @param value
     * @return 处理后的字符串
     */
    public static String addUnitWhenBeyondHundredThousandAndAccurate(BigDecimal value) {
        if (value.abs().doubleValue() > 100000) {
            return formatWithThousandsSeparatorAndUnitAndAccurate(value, UNIT_WANG);
        }
        return formatWithThousandsSeparatorAndAccurate(value);
    }

    /**
     * 当数字字符串大于 1,000,000 或小于 -1,000,000 时候，添加‘万’单位，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String addUnitWhenBeyondMillion(BigDecimal value) {
        if (value.abs().doubleValue() > 1000000) {
            return formatWithThousandsSeparatorAndUnit(value, UNIT_WANG);
        }
        return formatWithThousandsSeparator(value);
    }

    /**
     * 当数字字符串大于 1,000,000 或小于 -1,000,000 时候，添加‘万’单位，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String addUnitWhenBeyondMillion(Double value) {
        return addUnitWhenBeyondMillion(new BigDecimal(value));
    }

    /**
     * 添加‘亿’单位，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return
     */
    public static String addUnitOfHundredMillion(Long value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        if (value > 0) {
            return formatWithThousandsSeparatorAndUnit(bigDecimal, UNIT_YI);
        }
        return formatWithThousandsSeparator(value);
    }

    public static String addUnitWhenLargerThanTenThousand(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return addUnitWhenBeyondTenThousand(bigDecimal);
    }

    public static String addUnitWhenLargerThanTenThousand(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return addUnitWhenBeyondTenThousand(bigDecimal);
    }

    public static String addUnitWhenLargerThanHundredThousand(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return addUnitWhenBeyondHundredThousand(bigDecimal);
    }

    public static String addUnitWhenLargerThanHundredThousand(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return addUnitWhenBeyondHundredThousand(bigDecimal);
    }

    /**
     * 使用千位分隔符分割数字字符串，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparator(bigDecimal);
    }

    /**
     * 使用千位分隔符分割 Double 数据  ，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparator(bigDecimal);
    }

    /**
     * 使用千位分隔符分割 Integer 数据，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(Integer value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparator(bigDecimal);
    }

    /**
     * 使用千位分隔符分割 Long 数据 ，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(Long value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparator(bigDecimal);
    }

    /**
     * 使用千位分隔符分割 Double 数据，并使用‘银行家算法’精确（保留）到整数
     * @return
     */
    public static String formatWithThousandsSeparatorAndAccurate(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparatorAndAccurate(bigDecimal);
    }


    /**
     * 使用千位分隔符分割 Double 数据，并使用‘银行家算法’精确（保留）到整数
     * @return
     */
    public static String formatWithThousandsSeparatorAndAccurate(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparatorAndAccurate(bigDecimal);
    }    
    
    /**
     * 使用千位分隔符分割 bigDecimal 数据，并使用‘银行家算法’精确（保留）到整数
     * @return
     */
    public static String formatWithThousandsSeparatorAndAccurate(BigDecimal value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        decimalFormat.applyPattern("#,##0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        return decimalFormat.format(value.doubleValue());
    }

    /**
     * 使用千位分隔符分割 bigDecimal，并使用‘银行家算法’精确（保留）到小数点后两位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(BigDecimal value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        decimalFormat.applyPattern("#,##0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        return decimalFormat.format(value.doubleValue());
    }

    /**
     * 使用千位分隔符分割 bigDecimal，并使用‘银行家算法’精确（保留）到小数点后一位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparatorAndAccurateTheFirst(Double value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        decimalFormat.applyPattern("#,##0.0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        return decimalFormat.format(value.doubleValue());
    }

    /**
     * 使用千位分隔符分割 bigDecimal，并使用‘银行家算法’精确（保留）到小数点后一位
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparatorAndAccurateTheFirst(BigDecimal value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        decimalFormat.applyPattern("#,##0.0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        return decimalFormat.format(value.doubleValue());
    }

    /**
     * 使用千位分隔符分割 Double，并使用‘银行家算法’精确（保留）到小数点后 scale 位
     * @param value
     * @param scale
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparatorAndAccurate(Double value, int scale) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        String pattern = "#,##0";
        for (int i = 1; i <= scale; i++) {
            if (i == 1) pattern += ".0";
            else pattern += "0";
        }

        decimalFormat.applyPattern(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        return decimalFormat.format(value.doubleValue());
    }

    /**
     * 使用大额单位和千位分隔符格式化数字
     *
     * 1 基于使用的大额单位做除法（现在暂时只支持‘万’，以后或许会有百万，千万，亿）
     * 2 调用 formatWithThousandsSeparator 进行千位分隔以及两位小数保留
     * 3 添加单位 unit
     *
     * @param value
     * @param unit
     * @return 处理后的字符串
     */
    private static String formatWithThousandsSeparatorAndUnit(BigDecimal value, String unit) {
        if (unit == UNIT_WANG) {
            value = BigDecimalUtil.divide(value.doubleValue(), 10000.000);
            return formatWithThousandsSeparator(value) + unit;
        } else  if (unit == UNIT_YI) {
            value = BigDecimalUtil.divide(value.doubleValue(), 100000000.000);
            return formatWithThousandsSeparator(value) + unit;
        }
        return value.toString();
    }
    
    /**
     * 使用大额单位和千位分隔符格式化数字
     *
     * 1 基于使用的大额单位做除法（现在暂时只支持‘万’，以后或许会有百万，千万，亿）
     * 2 调用 formatWithThousandsSeparator 进行千位分隔以及两位小数保留
     * 3 添加单位 unit
     *
     * @param value
     * @param unit
     * @return 处理后的字符串
     */
    private static String formatWithThousandsSeparatorAndUnitAndAccurate(BigDecimal value, String unit) {
        if (unit == UNIT_WANG) {
            value = BigDecimalUtil.divide(value.doubleValue(), 10000.000);
            return formatWithThousandsSeparatorAndAccurate(value) + unit;
        } else  if (unit == UNIT_YI) {
            value = BigDecimalUtil.divide(value.doubleValue(), 100000000.000);
            return formatWithThousandsSeparatorAndAccurate(value) + unit;
        }
        return value.toString();
    }

    /**
     * 根据期货类型对传入的数据进行进度处理，黄金AU X.00，白银AG取整，期指IF X.0
     *
     * @param value
     * @param product
     * @return
     */
    public static String formatPriceBasedOnFuturesType(Double value, Product product) {
        return formatWithScale(value, product.getPriceScale());
    }

    /**
     * 根据期货类型对传入的数据进行进度处理，黄金AU X.00，白银AG取整，期指IF X.0
     *
     * @param value
     * @param product
     * @return
     */
    public static String formatPriceBasedOnFuturesType(Float value, Product product) {
        return formatWithScale(value.doubleValue(), product.getPriceScale());
    }

    /**
     * 使用‘银行家算法’精确（保留）到小数点后 scale 位
     * @param value
     * @param scale 小数位数
     * @return
     */
    public static String formatWithScale(Double value, int scale) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        String pattern = "##0";
        for (int i = 1; i <= scale; i++) {
            if (i == 1) pattern += ".0";
            else pattern += "0";
        }
        decimalFormat.applyPattern(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        String v = decimalFormat.format(value);
        return v;
    }

    /**
     * 现货手续费
     * @param price
     * @param holdingNum
     * @return
     */
    public static BigDecimal formatCommodityFee(Double price,int holdingNum){
        BigDecimal priceDecimal = new BigDecimal(price);
        BigDecimal holdingNumDecimal = new BigDecimal(holdingNum);
        BigDecimal turnoverDecimal = priceDecimal.multiply(holdingNumDecimal);
        turnoverDecimal = turnoverDecimal.multiply(new BigDecimal(8D / 10000D));
        return turnoverDecimal;
    }

}
