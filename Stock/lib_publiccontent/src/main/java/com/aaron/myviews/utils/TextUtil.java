
/**
 * @Title: TextUtil.java
 * @Package com.luckin.magnifier.utils
 * @Description: TODO
 * @ClassName: TextUtil
 * @author 于泽坤
 * @date 2015-7-27 下午1:42:03
 */

package com.aaron.myviews.utils;


import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;

/**
 * @Description: TODO
 */

public class TextUtil {

    /**
     * 按比例缩放str2的大小，同时拼str1和str2
     * @param str1
     * @param proportion 比例值，单位字体大小 = 原字体大小 * proportion
     * @param str2
     * @return 处理后的字符串
     */
    public static SpannableString mergeTextWithProportion(String str1, float proportion, String str2) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(str1)) {
            int start = str1.length();
            str1 = str1 + str2;
            int end = str1.length();
            res = new SpannableString(str1);
            res.setSpan(new RelativeSizeSpan(proportion), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return res;
    }

    /**
     * 按比例缩放str2的大小，设置str2颜色，同时拼str1和str2
     * @param str1
     * @param proportion
     * @param str2Color
     * @param str2
     * @return
     */
    public static SpannableString mergeTextWithProportionColor(String str1, float proportion,
                                                               int str2Color, String str2) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(str1)) {
            int start = str1.length();
            str1 = str1 + str2;
            int end = str1.length();
            res = new SpannableString(str1);
            res.setSpan(new RelativeSizeSpan(proportion), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (str2Color != Color.TRANSPARENT) {
                res.setSpan(new ForegroundColorSpan(str2Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return res;
    }

    /**
     * 合并 str1 和 str2，同时设置 str2 颜色
     * @param str1
     * @param str2
     * @param str2Color
     * @return 处理后的字符串
     */
    public static SpannableString mergeTextWithColor(String str1, String str2, int str2Color) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(str1)) {
            int start = str1.length();
            str1 = str1 + str2;
            int end = str1.length();
            res = new SpannableString(str1);
            if (str2Color != Color.TRANSPARENT) {
                res.setSpan(new ForegroundColorSpan(str2Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return res;
    }

    /**
     * 合并 str1 和 str2，同时设置 str1 颜色
     * @param str1
     * @param str1Color
     * @param str2
     * @return 处理后的字符串
     */
    public static SpannableString mergeTextWithColor(String str1, int str1Color, String str2) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(str1)) {
            int start = 0;
            int end = str1.length();
            res = new SpannableString(str1 + str2);
            if (str1Color != Color.TRANSPARENT) {
                res.setSpan(new ForegroundColorSpan(str1Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return res;
    }

    /**
     * 设置带参数的文字资源id
     * @param context
     * @param resId 文字资源id
     * @param args 多个参数
     * @return 组好的字符串
     */
    public static String getFormatString(Context context, int resId, Object... args) {
        if (context != null) {
            String formatStr = context.getResources().getString(resId);
            formatStr = String.format(formatStr, args);
            return formatStr;
        }
        return "";
    }

    public static SpannableStringBuilder formatPromoteText(Context context, String textStr, int firstRowColor, int secondRowColorColor, int firstSize, int secondSize) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
        int end = textStr.indexOf("\n");
        if (end != -1) {
            spannable.setSpan(new ForegroundColorSpan(firstRowColor), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new AbsoluteSizeSpan((int) DisplayUtil.convertSp2Px(context, firstSize)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(secondRowColorColor), end, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new AbsoluteSizeSpan((int) DisplayUtil.convertSp2Px(context, secondSize)), end, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }

    public static SpannableStringBuilder formatWithdrawalText(String textStr) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
        int end = textStr.indexOf(" ");
        if (end != -1) {
            spannable.setSpan(new StrikethroughSpan(), end + 1, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static SpannableStringBuilder formatReplyRecordText(String textStr, int grayColor, int goldColor) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
        int end = textStr.indexOf(":");
        if (end != -1) {
            spannable.setSpan(new ForegroundColorSpan(goldColor), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(grayColor), end, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }

    public static SpannableStringBuilder formatScoreOrAmountText(Context context, String textNumber, String unit, int textSize, int color) {
        int length = textNumber.length();
        String textStr = textNumber + unit;
        SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
        spannable.setSpan(new ForegroundColorSpan(color), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan((int) DisplayUtil.convertSp2Px(context, textSize)), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), length, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan((int) DisplayUtil.convertSp2Px(context, 12)), length, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static SpannableStringBuilder formatScoreOrAmountText(Context context, String textNumber, String unit, int textSize, int color, int unitcolor) {
        int length = textNumber.length();
        String textStr = textNumber + unit;
        SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
        spannable.setSpan(new ForegroundColorSpan(color), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan((int) DisplayUtil.convertSp2Px(context, textSize)), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(unitcolor), length, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan((int) DisplayUtil.convertSp2Px(context, 10)), length, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 将中文全部转为全角
     * @param input
     * @return
     */
    public static String angleOfCharacter(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 用空格按照3-4-4分割格式化手机
     * @param phoneNumber 11位手机号码
     * @return
     */
    public static String formatPhoneNumberWithSpace(String phoneNumber) {
        int len = phoneNumber.length();
        if (len <= 3) {
            return phoneNumber.substring(0);
        } else if (len <= 7) {
            return phoneNumber.substring(0, 3) + " " + phoneNumber.substring(3);
        }

        return phoneNumber.substring(0, 3) + " " + phoneNumber.substring(3, 7) + " " + phoneNumber.substring(7);
    }

    /**
     * 隐秘手机号码(隐藏中间4位，以星号代替)
     * @param number 11位手机号码
     * @return
     */
    public static String formatSecretPhoneNumber(String number) {
        return replaceWithStar(number, 3, 7);
    }

    /**
     * 隐秘身份证号(首尾一位数显示，其他以星号代替)
     * @param idNumber
     * @return
     */
    public static String formatSecretIdNumber(String idNumber) {
        if (TextUtils.isEmpty(idNumber)) return idNumber;
        if (idNumber.contains("*")) return idNumber;
        return replaceWithStar(idNumber, 1, idNumber.length() - 1);
    }

    /**
     * 隐秘银行卡号(首尾4位数显示，其他以星号代替)
     * @param bankCardNumber
     * @return
     */
    public static String formatSecretBankCardNumber(String bankCardNumber) {
        if (!TextUtils.isEmpty(bankCardNumber) && bankCardNumber.length() > 8)
            return replaceWithStar(bankCardNumber, 4, bankCardNumber.length() - 4);
        else
            return bankCardNumber;
    }

    /**
     * 以星号替代start-end部分的字符串
     * @param string 原字符串
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static String replaceWithStar(String string, int startIndex, int endIndex) {
        if (!TextUtils.isEmpty(string)) {
            if (startIndex < 0 || startIndex >= string.length()) {
                return string;
            }
            if (endIndex >= string.length() || endIndex < 0) {
                return string;
            }
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < endIndex - startIndex; i++) {
                stars.append("*");
            }
            return string.replace(string.substring(startIndex, endIndex), stars);
        }
        return string;
    }

    /**
     * 添加分隔符.
     * @param source 原始字符串
     * @param interval 间隔几个字添加一个分隔符
     * @param separator 分隔字符
     * @return
     */
    public static String addSeparator(String source, int interval, String separator) {
        if (TextUtils.isEmpty(source)) return source;
        StringBuilder sb = new StringBuilder();
        source = source.replaceAll(separator, "");//先把字符串中包含分隔字符的移除掉
        for (int i = 0; i < source.length(); i++) {
            sb.append(source.charAt(i));
            if ((i + 1) % interval == 0 && !separator.equals(source.charAt(i))) {
                //非分隔字符并且是间隔数的整数倍追加分隔字符
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static boolean isDecimal(String number) {
        String regularExpression = "[0-9.]*";
        if (number.matches(regularExpression)) {
            return true;
        }
        return false;
    }
}

