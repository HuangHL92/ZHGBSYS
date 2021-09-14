package com.insigma.siis.local.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 时间工具类
 * 
 * @author tongjl
 *
 */
public class DateUtil {
	/**
	 * yyyy-MM-dd
	 */
	public static final String PARTTEN_DATE = "yyyy-MM-dd";
	/**
	 * HH:mm:ss
	 */
	public static final String PARTTEN_TIME = "HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String PARTTEN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd'T'HH:mm:ss
	 */
	public static final String PARTTEN_DATETIMET = "yyyy-MM-dd'T'HH:mm:ss";
	/**
	 * HH:mm
	 */
	public static final String PARTTEN_TIMES = "HH:mm";
	/**
	 * yyyyMMdd
	 */
	public static final String PARTTEN_DATE_TIME = "yyyyMMdd";
	/**
	 * yyyy年MM月dd日
	 */
	public static final String PARTTEN_DATE_STRING = "yyyy年MM月dd日";
	/**
	 * HH
	 */
	public static final String PARTTEN_TIMES_HOURS = "HH";
	/**
	 * mm
	 */
	public static final String PARTTEN_TIMES_MIN = "mm";
	/**
	 * MM月dd日
	 */
	public static final String PARTTEN_MONTH_DAY = "MM月dd日";
	/**
	 * dd日
	 */
	public static final String PARTTEN_ONLY_DAY = "dd日";
	/**
	 * yyyy
	 */
	public static final String PARTTEN_YEAR = "yyyy";
	/**
	 * MM
	 */
	public static final String PARTTEN_MONTH = "MM";
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String PARTTEN_DATESTRING = "yyyyMMddHHmmss";

	public static String formatDateStr(Date date) {
		if (null == date) {
			return StringUtils.EMPTY;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(PARTTEN_DATETIME);
		return sdf.format(date);
	}

	public static String formatDateStr(Date date, String partten) {
		if (null == date) {
			return StringUtils.EMPTY;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		return sdf.format(date);
	}

	public static String getCurrentDate() {
		return formatDateStr(new Date(), PARTTEN_DATE);
	}

	public static String getCurrentDate(String partten) {
		return formatDateStr(new Date(), PARTTEN_DATE);
	}


	public static String getCurrentDateTime() {
		return formatDateStr(new Date(), PARTTEN_DATETIME);
	}

	public static String getCurrentTime() {
		return formatDateStr(new Date(), PARTTEN_TIME);
	}

	public static Date getFromStr(String dt, String partten) throws ParseException {
		if (StringUtils.isEmpty(dt)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		return sdf.parse(dt);
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PARTTEN_DATE);
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PARTTEN_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int hoursBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PARTTEN_DATETIME);
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_hours = (time2 - time1) / (1000 * 3600);

		return Integer.parseInt(String.valueOf(between_hours));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int hoursBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PARTTEN_DATETIME);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_hours = (time2 - time1) / (1000 * 3600);

		return Integer.parseInt(String.valueOf(between_hours));
	}

	public static String formatDateStr(Calendar date) {
		if (null == date) {
			return StringUtils.EMPTY;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(PARTTEN_DATETIME);
		return sdf.format(date.getTime());
	}

	public static String formatDateStr(Calendar date, String partten) {
		if (null == date) {
			return StringUtils.EMPTY;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		return sdf.format(date.getTime());
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date transForDate(long time) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Long time2=new Long(time);
		// String d = format.format(time2);
		try {
			return format.parse(format.format(new java.util.Date(time * 1000)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间精确时分秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(format.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间精确时分秒
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(String dateStr) {

		SimpleDateFormat oldsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newsdf = new SimpleDateFormat("yyyy年M月d日 H时m分s秒");
		try {
			return newsdf.format(oldsdf.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算GE FW
	 * 
	 * @param currentDate
	 * @return
	 */
	public static String calcFw(Date currentDate) {
		if (currentDate == null) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return formatDateStr(currentDate, PARTTEN_YEAR) + "FW" + cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取季度
	 * 
	 * @param date
	 * @return 1: Q1 2: Q2 3: Q3 4: Q4
	 */
	public static byte getQuarter(Date currentDate) {
		if (currentDate == null) {
			return 0;
		}

		int curMonth = Integer.parseInt(formatDateStr(currentDate, PARTTEN_MONTH));
		if (curMonth >= 1 && curMonth <= 3) {
			return 1;
		} else if (curMonth >= 4 && curMonth <= 6) {
			return 2;
		} else if (curMonth >= 7 && curMonth <= 9) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 获取季度并按 年份+季度（e.g. 2015Q2）返回
	 * 
	 * @param currentDate
	 * @return
	 */
	public static String getQtrWithFormat(Date currentDate) {
		if (currentDate == null) {
			return "";
		}

		return formatDateStr(currentDate, PARTTEN_YEAR) + "Q" + getQuarter(currentDate);
	}

	/**
	 * 是否当季日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public static boolean isCq(Date currentDate) {
		if (currentDate == null) {
			return false;
		}

		boolean ret = false;
		Date sysDate = new Date();
		SimpleDateFormat sdfYear = new SimpleDateFormat(PARTTEN_YEAR);

		if (sdfYear.format(sysDate).equals(sdfYear.format(currentDate))) {
			if (getQuarter(sysDate) == getQuarter(currentDate)) {
				ret = true;
			}
		}

		return ret;
	}

	/**
	 * 是否未来季日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public static boolean isFq(Date currentDate) {
		if (currentDate == null) {
			return false;
		}

		boolean ret = false;
		Date sysDate = new Date();
		SimpleDateFormat sdfYear = new SimpleDateFormat(PARTTEN_YEAR);
		String curYear = sdfYear.format(currentDate);
		if (sdfYear.format(sysDate).equals(curYear)) {
			if (getQuarter(sysDate) < getQuarter(currentDate)) {
				ret = true;
			}
		} else if (sdfYear.format(sysDate).compareTo(curYear) < 0) {
			ret = true;
		}

		return ret;
	}

	/**
	 * 
	 * @return true/false
	 */
	public static boolean isDate(String str, String format) {
		return parseStrToDate(str, format) != null;
	}

	public static Date parseStrToDate(String str, String format) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String parseGreeting() {
		String greeting = "您好";
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH");
		String str = df.format(date);
		int hour = Integer.parseInt(str);
		if (hour >= 0 && hour <= 6) {
			greeting = "凌晨好";
		}
		if (hour > 6 && hour <= 12) {
			greeting = "上午好";
		}
		if (hour > 12 && hour <= 13) {
			greeting = "中午好";
		}
		if (hour > 13 && hour <= 18) {
			greeting = "下午好";
		}
		if (hour > 18 && hour <= 24) {
			greeting = "晚上好";
		}
		return greeting;
	}
	
	/**
	     * 指定日期加上天数后的日期
	     * @param num 为增加的天数
	     * @param newDate 创建时间
	     * @return
	     * @throws ParseException 
	     */
	    public static String plusDay(int num,String newDate, String partten) throws ParseException{
	        Date  currdate =  getFromStr(newDate,partten);
	        Calendar ca = Calendar.getInstance();
	        ca.setTime(currdate);
	        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
	        currdate = ca.getTime();
	        String enddate = formatDateStr(currdate,partten);
	        return enddate;
	    }
}
