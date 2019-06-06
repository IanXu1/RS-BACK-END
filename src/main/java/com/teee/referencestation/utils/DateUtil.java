package com.teee.referencestation.utils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 时间工具类
 * @author zhanglei
 */
public class DateUtil {

    /**
     * DateFormat:yyyy-MM-dd
     */
    public static final String FORMAT_YYYY_MM_DD_MINUS = "yyyy-MM-dd";

    /**
     * DateFormat:yyyy-MM-dd
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_MINUS = "yyyy-MM-dd HH:mm:ss";

    /**
     * DateFormat:yyMMddHHmmss
     */
    public static final String FORMAT_YY_MM_DD_HH_MM_SS = "yyMMddHHmmss";

    /**
     * 年月日格式
     */
    public static final String CHN_FORMAT_YYYY_MM_DD = "yyyy年MM月dd日";

    /**
     * 将字符串装换成日期类型
     *
     * @param str 需要转换的字符串
     * @return Date 转换后的日期
     */
    public static LocalDateTime convertToDate(String format, String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.parse(str, LocalDateTime::from);
    }

    public static String convertDateToStr(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDateTime);
    }

    /**
     * 得到系统当前时间戳。(精确到秒)
     *
     * @return String 系统当前时间戳X
     */
    public static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YY_MM_DD_HH_MM_SS);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 得到系统当前时间戳。(精确到秒)
     *
     * @return String 系统当前时间戳X
     */
    public static String getCurrentTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 获取当前日期。
     *
     * @return String 系统当前日期
     */
    public static String getCurrentTimeMinus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_MINUS);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 获取系统当前日期（YYYY年MM月DD日）
     *
     * @return
     */
    public static String getCurrentDateYMDChn() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHN_FORMAT_YYYY_MM_DD);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间(yyyy-MM-dd HH:mm:ss 格式)
     *
     * @return String
     */
    public static String getCurrentDateTimeStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS_MINUS);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 将字符串装换成日期类型
     *
     * @param format  日期格式
     * @param dateStr 需要转换的字符串
     * @return long 时间戳
     */
    public static long convertToTimestamp(String format, String dateStr) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(format);
        LocalDateTime parse = LocalDateTime.parse(dateStr, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间戳转换 LocalDateTime
     *
     * @param timestamp
     * @return LocalDateTime
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算当前日期与{@code lastDate}的间隔天数
     *
     * @param lastDate
     * @param unit
     * @return 间隔天数
     */
    public static long until(LocalDateTime lastDate, ChronoUnit unit) {
        return Math.abs(lastDate.until(LocalDateTime.now(), unit));
    }

    /**
     * @param startDate
     * @param endDate
     * @param unit
     * @return 返回绝对值
     * @desc 计算两个日期的间隔
     */
    public static long until(LocalDateTime startDate, LocalDateTime endDate, ChronoUnit unit) {
        return Math.abs(startDate.until(endDate, unit));
    }

    /**
     * 获取当前时间(yyyy-MM-dd HH:mm:ss 格式)
     *
     * @return String
     */
    public static String formatDate(LocalDateTime localDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS_MINUS);
            return formatter.format(localDateTime);
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(LocalDateTime.now().plusMonths(1).until(LocalDateTime.now().plusDays(1), ChronoUnit.DAYS));
    }
}
