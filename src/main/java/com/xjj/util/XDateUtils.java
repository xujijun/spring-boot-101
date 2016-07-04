package com.xjj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期处理相关工具类
 */
public class XDateUtils {
	/** 日期格式枚举类，根据需要添加其他格式 **/
	public enum DatePattern{
		ISO_SECOND("yyyy-MM-dd'T'HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$"),
		ISO_MINUTE("yyyy-MM-dd'T'HH:mm", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$"),
		DATE_TIME("yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$"),
		DATE_TIME_FULL("yyyy-MM-dd HH:mm:ss,SSS", "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}$"),
		DATE_ONLY("yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{2}$"),
		YEAR_MONTH("yyyy-MM", "^\\d{4}-\\d{2}$");
		
		DatePattern(String pattern, String regex){
			this.pattern = pattern;
			this.regex = regex;
		}
		
		public String getPattern() {
			return pattern;
		}
		public String getRegex() {
			return regex;
		}

		private String pattern;
		private String regex;
		
		/**
		 * 根据日期字符串，判断该日期的格式类型。
		 * 
		 * @param dateStr 日期字符串
		 * @return 日期的格式类型，比如getPatternByDateStr("2016-04-27 10:15:08")返回："yyyy-MM-dd HH:mm:ss"
		 */
		public static String getPatternByDateStr(String dateStr){
			for(DatePattern df : DatePattern.values()){
				if(RegexUtils.matches(dateStr, df.getRegex())){
					return df.getPattern();
				}
			}
			return null;
		}
	}
	
	/**
	 * 字符串转换为Date对象，自动匹配日期格式
	 * 
	 * @param strDate 日期字符串
	 * @return Date
	 */
	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, DatePattern.getPatternByDateStr(dateStr));
	}

	/**
	 * 把字符串转换为Date类型
	 * 
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date stringToDate(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把字符串转换为LocalDate类型
	 * @param dateStr 格式必须为：“2016-05-31”
	 * @return
	 */
	public static LocalDate stringToLocalDate(String dateStr){
		return LocalDate.parse(dateStr);
	}

	/**
	 * 把字符串转换为LocalDate类型
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static LocalDate stringToLocalDate(String dateStr, DatePattern pattern){
		final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
		return LocalDate.parse(dateStr, df);
	}

	/**
	 * 将指定的日期转换成long时间戳
	 * 
	 * @param String date 需要转换的日期，自动匹配日期格式
	 * @return long 时间戳，单位：秒
	 */
	public static long stringToTimestamp(String dateStr) {
		String d = dateStr.trim();
		String pattern = DatePattern.getPatternByDateStr(dateStr);
		
		return stringToTimestamp(d, pattern);
	}

	/**
	 * 将指定的日期转换成long时间戳
	 * 
	 * @param String date 需要转换的日期
	 * @param String dateFormat 需要转换的日期格式
	 * @return long 时间戳，单位：秒
	 */
	public static long stringToTimestamp(String dateStr, String pattern) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(pattern).parse(dateStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp/1000;
	}

	/**
	 * 日期转String
	 * 
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/*public static String localDateToString(LocalDate date) {
		return date.toString();
	}*/
	
	/**
	 * 将long时间戳转换成日期
	 * 
	 * @param long timestamp 时间戳，单位：秒
	 * @return String 日期字符串
	 */
	public static Date timestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DatePattern.DATE_TIME.getPattern());
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return new Date(timestamp*1000);
	}

	/**
	 * 将long时间戳转换pattern格式的String
	 * @param timestamp
	 * @param pattern 格式
	 * @return 格式化后的时间
	 */
	public static String timestampToString(long timestamp, String pattern) {
		Date date = timestampToDate(timestamp);
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 转换long类型的timestamp为LocalDateTime类型
	 * @param timestamp
	 * @return LocalDateTime
	 */
	public static LocalDateTime timestampToLocalDateTime(long timestamp){
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
	}
	
	/**
	 * 转换long类型的timestamp为LocalDate类型
	 * @param timestamp
	 * @return LocalDate
	 */
	public static LocalDate timestampToLocalDate(long timestamp){
		return timestampToLocalDateTime(timestamp).toLocalDate();
	}
	
	/**
	 * 获取系统当前时间字符串（"yyyy-MM-dd HH:mm:ss"）
	 * 
	 * @return
	 */
	public static String nowToString() {
		return nowToString(DatePattern.DATE_TIME);
	}

	/**
	 * 获取系统当前时间，指定格式
	 * 
	 * @return
	 */
	public static String nowToString(DatePattern pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern.getPattern());
		return df.format(new Date());
	}

	/**
	 * 将当前日期转换成long时间戳
	 * 
	 * @return long 时间戳，单位：秒
	 */
	public static long nowToTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp/1000;
	}
	
	/**
	 * 和当前时间比较（Date格式）
	 * 
	 * @param date
	 * @return 负数：之前；0：当前；正数：之后
	 */
	public static int compareDateWithNow(Date date1) {
		Date date2 = new Date();
		int rnum = date1.compareTo(date2);
		return rnum;
	}

	/**
	 * 和当前时间比较(时间戳比较)
	 * 
	 * @param date
	 * @return
	 */
	public static int compareDateWithNow(long date1) {
		long date2 = nowToTimestamp();
		if (date1 > date2) {
			return 1;
		} else if (date1 < date2) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取几天前00:00:00的时间戳
	 * @param d 距离现在d天前
	 * @return 时间戳，以秒为单位
	 */
	public static long getMinusDay(int d) {
		LocalDate now = LocalDate.now();
		return now.minusDays(d).atStartOfDay().toEpochSecond(ZoneOffset.ofHours(8));
	}

	/**
	 * 获取距离现在m个月前00:00:00的时间戳
	 * @param m 距离现在m个月前
	 * @return 时间戳，以秒为单位
	 */
	public static long getMinusMonth(int m){
		LocalDate now = LocalDate.now(); 
		return now.minusMonths(m).atStartOfDay().toEpochSecond(ZoneOffset.ofHours(8));
	}
	
	public static String getDayOfWeek(LocalDate d){
		DayOfWeek dayOfWeek = d.getDayOfWeek();
		return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static void main(String[] args) {
		System.out.println(stringToTimestamp("2016-05-05T15:22:09"));
		System.out.println(stringToTimestamp("2016-05-05 15:22:09"));
		System.out.println(stringToTimestamp("2016-05-05"));
		System.out.println(getMinusMonth(1));
		System.out.println(LocalDateTime.ofEpochSecond(getMinusMonth(2), 0, ZoneOffset.ofHours(8)));
		
		System.out.println(LocalDateTime.ofEpochSecond(getMinusDay(0), 0, ZoneOffset.ofHours(8)));
		System.out.println(LocalDateTime.ofEpochSecond(getMinusDay(1), 0, ZoneOffset.ofHours(8)));
		
		LocalDate now = LocalDate.now();
		System.out.println(getDayOfWeek(now));
		
		LocalDate ld = stringToLocalDate("2015-05-29", DatePattern.DATE_ONLY);
		System.out.println(ld);
		
		System.out.println("defult zone Id: " + TimeZone.getDefault().toZoneId());
		System.out.println("timestamp to LocalDate: " + timestampToLocalDate(1462432929));
		System.out.println("timestamp to LocalDateTime: " + timestampToLocalDateTime(1462432929));
	}
}