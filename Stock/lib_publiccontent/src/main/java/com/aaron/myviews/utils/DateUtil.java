/**
 * @Title: DateUtil.java
 * @Package com.luckin.magnifier.utils
 * @Description: TODO
 * @ClassName: DateUtil
 * @author 于泽坤
 * @date 2015-7-6 上午10:52:37
 */

package com.aaron.myviews.utils;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * 格式化时间戳
     * @param milliseconds
     * @return 返回HH:mm:ss的时间字符串
     */
    public static String getFormattedTimestamp(long milliseconds) {
        return getFormattedTimestamp(milliseconds, "HH:mm:ss");
    }

    /**
     * 格式化时间戳
     * @param milliseconds
     * @return 返回yyyy-MM-dd的时间字符串
     */
    public static String getFormattedTimesDate(long milliseconds) {
        return getFormattedTimestamp(milliseconds, "yyyy-MM-dd");
    }


    /**
     * 格式化时间戳
     * @param milliseconds
     * @param timePattern
     * @return 返回格式化的时间字符串
     */
    public static String getFormattedTimestamp(long milliseconds, String timePattern) {
        Date date = new Date(milliseconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
        return dateFormat.format(date);
    }

    /**
     * 获取当天早上的格式化时间yyyyMMdd090000
     * @return
     */
    public static String getFormattedMorningTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String halfOfTime = dateFormat.format(new Date());
        return halfOfTime + "090000";
    }

    public static String getFormattedCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static boolean isToday(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = simpleDateFormat.parse(time);
                return isToday(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isSameDay(Long time1, Long time2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(time1);
        c2.setTimeInMillis(time2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    public static String getTodayDateOf(Context context) {
        return DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * 判断一个时间（字符串）是否早于现在时间，time < curTime, 早于的时间差 deltaTime < seconds，
     *
     * @param time 比较时间
     * @param seconds
     * @param minutes 返回的数据 minutes[0,1,2,3] = [day, hour, min, sec]
     * @return
     */
    public static boolean isEarlierThanCurrentTime(String time, int seconds, int[] minutes) {
        int DAY = 0;
        int HOUR = 1;
        int MIN = 2;
        int SEC = 3;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = simpleDateFormat.parse(time);

            long diff = new Date().getTime() - dateTime.getTime();
            minutes[DAY] = (int) (diff / (24 * 60 * 60 * 1000));
            minutes[HOUR] = (int) (diff / (60 * 60 * 1000));
            minutes[MIN] = (int) (diff / (60 * 1000));
            minutes[SEC] = (int) (diff / (1000));

            return minutes[SEC] < seconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * time1 早于 time2 至少 seconds 秒
     * 即 time2 - time1 >= seconds
     *
     * @param time1 比较早的时间值
     * @param time2 比较晚的时间值
     * @param seconds 时间差距
     * @return
     */
    public static boolean isEarlier(long time1, long time2, int seconds) {
        long delta = time2 - time1;
        if (delta / 1000 >= seconds) {
            return true;
        }
        return false;
    }

    /**
     * 绝对时间差
     * @param c1 Calendar1
     * @param c2 Calendar2
     * @param calendarField CalendarField(年/月/日/时/分/秒)
     * @return 返回0表示相同时间，负数代表c1比c2早，否则反之
     */
    public static long offset(Calendar c1, Calendar c2, int calendarField) {
        long offsetTimeInMillis = c1.getTimeInMillis() - c2.getTimeInMillis();

        switch (calendarField) {
            /*case Calendar.YEAR:
                return -1;//年没法算,闰年等一年不是固定365

            case Calendar.MONTH:
                return -1;//月也不是固定30天*/

            case Calendar.WEEK_OF_YEAR:
                return offsetTimeInMillis / (1000 * 60 * 60 * 24 * 7);

            case Calendar.DAY_OF_YEAR:
                return offsetTimeInMillis / (1000 * 60 * 60 * 24);

            case Calendar.HOUR:
                return offsetTimeInMillis / (1000 * 60 * 60);

            case Calendar.MINUTE:
                return offsetTimeInMillis / (1000 * 60);

            case Calendar.SECOND:
                return offsetTimeInMillis / 1000;

            case Calendar.MILLISECOND:
                return offsetTimeInMillis;

            default:
                return Long.MIN_VALUE;
        }
    }

    /**
     * c2是否是c1的后一天
     * @param c1
     * @param c2
     * @return
     */
    public static boolean nextDay(Calendar c1, Calendar c2) {
        cutTimeOnlyRetainDate(c1);
        cutTimeOnlyRetainDate(c2);
        long offset = offset(c1, c2, Calendar.DAY_OF_YEAR);
        return Math.abs(offset) == 1;
    }

    public static boolean nextDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return nextDay(c1, c2);
    }

    /**
     * 砍掉时间只保留日期
     */
    private static void cutTimeOnlyRetainDate(Calendar c) {
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    }

    /**
     * 是否是次日(本地时间)
     * @param calendar
     * @return
     */
    public static boolean isTomorrow(Calendar calendar) {
        return nextDay(Calendar.getInstance(), calendar);
    }


    /**
     * 获取最近天数
     * @param calendar 目标日期
     * @param defaultDateFormat 默认格式化时间
     * @return
     */
    public static String getRecentlyDayDisplay(Calendar calendar, DateFormat defaultDateFormat) {
        long offset = offset(calendar, Calendar.getInstance(), Calendar.DAY_OF_YEAR);
        /*if (offset==-2){//前天
        }else */
        if (offset == -1) {//昨天
            return "昨天";
        } else if (offset == 0) {//今日
            return "今天";
        } else {
            //超出范围
            return defaultDateFormat.format(calendar.getTime());
        }
    }

    /**
     *
     * @param dateStr 需要格式化显示的时间字符串
     * @return 今天 HH:mm  昨天 HH:mm  yyyy-MM-dd HH:mm
     */
    public static String getWithdrawalsDayDisplay(String dateStr, SimpleDateFormat dateFormat, String FormatTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Date formatDate = simpleDateFormat.parse(dateStr);
            Calendar formatCalendar = Calendar.getInstance();
            formatCalendar.setTime(formatDate);

            long offset = offset(calendar, Calendar.getInstance(), Calendar.DAY_OF_YEAR);
            if (offset == -1) {//昨天
                return "昨" + FormatTimeStr + dateFormat.format(formatCalendar.getTime());
            } else if (offset == 0) {//今日
                return "今" + FormatTimeStr + dateFormat.format(formatCalendar.getTime());
            } else {
                //超出范围
                return simpleDateFormat.format(formatCalendar.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 判断是否 timestamp1 <= timestamp <= timestamp2
     *
     * @param timestamp1
     * @param timestamp2
     * @return
     */
    public static boolean isBetweenTimestamps(Long timestamp, Long timestamp1, Long timestamp2) {
        return timestamp >= timestamp1 && timestamp <= timestamp2;
    }

    private static boolean isToday(Date date) {
        Date today = new Date();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(today);
        c2.setTime(date);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    public static String getHHmmssTime(String time) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = simpleDateFormat.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            return dateFormat.format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeYYmm(String time) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = simpleDateFormat.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm");
            return dateFormat.format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormattedTime(String time, String formatStr) {
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(formatStr)) return "";

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = simpleDateFormat.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
            return dateFormat.format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormattedTime(String time, String originalFormatStr, String formatStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(originalFormatStr);
            Date dateTime = simpleDateFormat.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
            return dateFormat.format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long convertToTimestamp(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = simpleDateFormat.parse(date);
            return dateTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long convertToTimestamp(String date, String formatStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            Date dateTime = simpleDateFormat.parse(date);
            return dateTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
