package com.xw.common.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    static public final String FORMAT_DATETIME_UI = "yyyy-MM-dd HH:mm:ss";
    static public final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm";
    static public final String FORMAT_DATE = "yyyy-MM-dd";
    static public final String FORMAT_DATETIME_YEAR_MONTH = "yyyy年MM月";
    static public final String FORMAT_DATE_UI = "yyyyMMdd";
    static public final String FORMAT_TIME_UI = "HH:mm:ss";
    static public final String FORMAT_DATETIME_HOUR_MINUTE = "HH:mm";

    /**
     * @return yyyyMMdd
     */
    public static String getDate(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATE_UI, Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String formatYearMonthDay(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String formatDate(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df.format(time);
    }

    public static String formatDateTime(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME, Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return yyyy
     */
    public static String formatYear(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat("yyyy", Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return HH:mm
     */
    public static String formatHourMinute(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_HOUR_MINUTE, Locale.CHINA);
        return df.format(time);
    }

    public static String getYear(long time) {
        if (time == 0) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy", Locale.CHINA);
        return df.format(time);
    }

    public static String getYearMonth(long time) {
        if (time == 0) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyyMM", Locale.CHINA);
        return df.format(time);
    }

    public static String formatYearMonth(long time) {
        if (time == 0) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return df.format(time);
    }

    public static long getNextMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getTimeInMillis();
    }

    public static long getPreviousMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) - 1));
        return calendar.getTimeInMillis();
    }

    /**
     * HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String getDateStr(long time) {
        if (time == 0) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(FORMAT_TIME_UI, Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return 两个日期的时间差 e.g  1天2小时5分
     */
    public static String minusDateForDaysStr(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays + "天" + diffHours + "小时" + diffMinutes + "分";
    }

    /**
     * @return 两个日期的时间差 e.g  1
     */
    public static long minusDateForDaysCount(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

    public static int countDays(Calendar cal1, Calendar cal2) {
        if (cal1.after(cal2)) {
            return countDays(cal2, cal1);
        }
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 == year2) {
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);
            return day2 - day1 + 1;
        } else {
            int yearNum = year2 - year1;
            int count = 0;
            Calendar countCal = (Calendar) cal1.clone();
            for (int i = 0; i < yearNum; i++) {
                count += countCal.getMaximum(Calendar.DAY_OF_YEAR);
                countCal.add(Calendar.YEAR, 1);
            }
            return count + countDays(countCal, cal2);
        }
    }

    /**
     * @return 两个日期的时间差 e.g  2小时5分
     */
    public static String minusDateForHoursStr(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        return diffHours + "小时" + diffMinutes + "分";
    }

    /**
     * @return 两个日期的时间差 e.g  2小时5分
     */
    public static long minusDateForHours(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return diff / (60 * 60 * 1000);
    }

    /**
     * @return e.g  2小时5分
     */
    public static String getHoursMinute(int minute) {
        long minutesValue = minute % 60;
        long hoursValue = minute / 60;
        if (hoursValue > 0) {
            return hoursValue + "小时" + minutesValue + "分钟";
        } else {
            return minutesValue + "分钟";
        }
    }

    /**
     * @return e.g  2年5月
     */
    public static String formYearMonth(int month) {
        long monthValue = month % 12;
        long yearValue = month / 12;
        return yearValue + "年" + monthValue + "月";
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串转化日期
     *
     * @param date
     * @return
     */
    public static Date parse(String date) {
        if (date == null) {
            return new Date();
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_UI, Locale.CHINA);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm格式的字符串转化日期
     */
    public static Date parseDatetime(String date) {
        if (date == null) {
            return new Date();
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME, Locale.CHINA);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 将Date转化时间戳
     *
     * @return 10位时间戳
     */
    public static long parseTimestamp(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime() / 1000;
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 格式的字符串转化时间戳
     *
     * @return 13 位时间戳
     */
    public static long parseStamp(String date) {
        if (date == null) {
            return 0;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_UI, Locale.CHINA);
        try {
            return df.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 将 yyyy-MM-dd 格式的字符串转化时间戳
     *
     * @return 13 位时间戳
     */
    public static long parseTimeStamp(String date) {
        if (date == null) {
            return 0;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);
        try {
            return df.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * @return yyyy年MM月
     */
    public static String parseDatetimeYearMonth(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_YEAR_MONTH, Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseDatetime(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_UI, Locale.CHINA);
        return df.format(time);
    }

    /**
     * @return 12:12
     */
    public static String parseHourMinute(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_HOUR_MINUTE, Locale.CHINA);
        return df.format(time);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getDatetime(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATE_UI, Locale.CHINA);
        return df.format(time);
    }

    public static String getDatetime(Date dbDate, String format) {
        if (dbDate == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        return df.format(dbDate.getTime());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getDatetime(Date dbDate) {
        if (dbDate == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATE_UI, Locale.CHINA);
        return df.format(dbDate.getTime());
    }

    public static boolean isSameDay(long time1, long time2) {
        time1 = time1 * 1000;
        time2 = time2 * 1000;
        long diffDays = (time1 - time2) / (24 * 60 * 60 * 1000);
        DateFormat df = new SimpleDateFormat("dd", Locale.CHINA);
        return diffDays == 0 && TextUtils.equals(df.format(time1), df.format(time2));
    }

    /**
     * 获取前几天的日期
     *
     * @param customDay 要获取的是前一天
     * @return
     */
    public static String getCustomDay(int customDay) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -customDay);
        String date = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
        System.out.println(date);
        return date;
    }

    /**
     * @return yyyy-MM-dd HH:mm
     */
    public static String parseDatetimeStr(long time) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME, Locale.CHINA);
        return df.format(time);
    }

    public static String parseDatetimeStr(long time, String format) {
        if (time == 0) {
            return "";
        }
        if (String.valueOf(time).length() == 10) { // java 13位时间戳 php 10位时间戳
            time = time * 1000;
        }
        DateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        return df.format(time);
    }

    public static String formatDateOrTime(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
        if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        return sdf.format(cal.getTime());
    }

    /**
     * @param date yyyyMM
     * @return yyyyMMdd
     */
    public static String getEndDayOfMonth(String date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyyMM", Locale.CHINA);
        Date monthDate = new Date();
        try {
            monthDate = df.parse(date);
        } catch (ParseException e) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateFormat dfRes = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return dfRes.format(calendar.getTime());
    }

    /**
     * 返回当天的时分秒，或者年月日
     */
    public static String getHourOrDay(long timeMillis) {
        Calendar nowCal = Calendar.getInstance();
        Calendar testCal = Calendar.getInstance();
        testCal.setTimeInMillis(timeMillis);
        if (isSameDay(nowCal.getTimeInMillis() / 1000, testCal.getTimeInMillis() / 1000)) {
            return new SimpleDateFormat("HH:mm:ss").format(testCal.getTime());
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(testCal.getTime());
        }
    }

    /**
     * 获取当前时间 格式 yyyy-MM  年月
     *
     * @return
     */
    public static String getCurrentDate1() {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        time = sdf.format(System.currentTimeMillis());
        return time;
    }

    public static String getWeekSunday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public static String getWeekMonday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public static String getWeekSunday(int week) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        long l = System.currentTimeMillis() + week * 7 * 24 * 60 * 60 * 1000L;
        calendar.setTimeInMillis(l);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public static String getWeekMonday(int week) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        long l = System.currentTimeMillis() + week * 7 * 24 * 60 * 60 * 1000L;
        calendar.setTimeInMillis(l);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    /**
     *     * 当前年的开始时间，即2012-01-01 00:00:00
     *     * 
     *     * @return
     *    
     */
    public static long getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            DateFormat shortSdf = new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);
            DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_UI, Locale.CHINA);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now.getTime();
    }

    /**
     *     * 当前年的结束时间，即2012-12-31 23:59:59
     *     * 
     *     * @return
     *    
     */
    public static long getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            DateFormat shortSdf = new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);
            DateFormat df = new SimpleDateFormat(FORMAT_DATETIME_UI, Locale.CHINA);
            now = df.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now.getTime();
    }
}
