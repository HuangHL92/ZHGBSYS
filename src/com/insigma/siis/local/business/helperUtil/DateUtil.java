package com.insigma.siis.local.business.helperUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;


/**
 * <p>
 * Title: 日期处理工具类
 * </p>
 * <p>
 * Description: 工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: BJLBS
 * </p>
 * 
 * @author POC
 * @modified by liwb
 * @version 3.0
 */

public class DateUtil {

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param dtBegin
	 *            开始日期
	 * @param dtEnd
	 *            结束日期
	 * @return int dtBegin到dtEnd之间的天数
	 */
	public static int getDaysBetween(final Date dtBegin, final Date dtEnd) {
		return new Long((dtEnd.getTime() - dtBegin.getTime()) / 86400000)
				.intValue();
	}

	/**
	 * 获取指定年份的天数
	 * 
	 * @param year
	 *            指定的年份，四位数字。
	 * @return int 年份具有的天数。
	 */
	public static int getDaysOfYear(final int year) {
		if (isLeapYear(year)) {
			return 366;
		}
		return 365;
	}

	/**
	 * 判断指定的年份是否是闰年
	 * 
	 * @param year
	 *            四位整数
	 * @return boolean true:闰年，false:非闰年
	 */
	public static boolean isLeapYear(final int year) {
		if ((year % 4) != 0) {
			return false;
		}
		if (((year % 100) == 0) && ((year % 400) != 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取指定日期在当年的第几天
	 * 
	 * @param date
	 *            日期类型
	 * @return int 是当年的第几天
	 */
	public static int getDayOfYear(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 根据传入的日期和传入天数，返回新的日期 正数返回往后的日期 负数返回往前的日期
	 * 
	 * @return 新的日期
	 */

	public static java.sql.Date getNewDay(final java.sql.Date date,
			final int days) {
		java.sql.Date tmp = new java.sql.Date(System.currentTimeMillis());
		tmp.setTime(date.getTime() + (long) 86400000 * days);
		return tmp;
	}

	/**
	 * 根据传入的起始日期和传入月数，返回信息的日期 只传入返回往后的日期
	 * 
	 * @return Date
	 */
	public static java.sql.Date getNewDate(final java.sql.Date date,
			final int months) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);

		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + months);
		int yyyy = ca.get(Calendar.YEAR);
		int DD = ca.get(Calendar.DATE);
		int MM = ca.get(Calendar.MONTH);
		ca.clear();
		ca.set(yyyy, MM, DD);
		return new java.sql.Date(ca.getTime().getTime());
	}

	/**
	 * 得到当前日期(java.sql.Date类型)，注意：返回的时间只到秒
	 * 
	 * @return 当前日期
	 */
	public static java.sql.Date getSysDate() {
		Calendar oneCalendar = Calendar.getInstance();
		int year = oneCalendar.get(Calendar.YEAR);
		int month = oneCalendar.get(Calendar.MONTH);
		int day = oneCalendar.get(Calendar.DATE);
		int hours = oneCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = oneCalendar.get(Calendar.MINUTE);
		int second = oneCalendar.get(Calendar.SECOND);
		int weekday = oneCalendar.get(Calendar.DAY_OF_WEEK);
		oneCalendar.clear();
		oneCalendar.set(year, month, day, hours, minute, second);
		oneCalendar.set(Calendar.DAY_OF_WEEK, weekday);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}

	public static String getTime() {
		Calendar oneCalendar = Calendar.getInstance();
		int year = oneCalendar.get(Calendar.YEAR);
		int month = oneCalendar.get(Calendar.MONTH) + 1;
		int day = oneCalendar.get(Calendar.DATE);
		int hours = oneCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = oneCalendar.get(Calendar.MINUTE);
		int second = oneCalendar.get(Calendar.SECOND);
		// int weekday =
		// oneCalendar.get(Calendar.DAY_OF_WEEK);
		return year + "年" + month + "月" + day + "日" + hours + "时" + minute
				+ "分" + second + "秒";
	}

	/**
	 * 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。
	 * 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
	 * 
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return 日期
	 */
	public static java.sql.Date getDate(final int yyyy, final int MM,
			final int dd) {
		if (!verityDate(yyyy, MM, dd)) {
			throw new IllegalArgumentException("This is illegimate date!");
		}

		Calendar oneCalendar = Calendar.getInstance();
		oneCalendar.clear();
		oneCalendar.set(yyyy, MM - 1, dd);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}

	/**
	 * 根据所给年、月、日，检验是否为合法日期。
	 * 
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return
	 */
	public static boolean verityDate(final int yyyy, final int MM, final int dd) {
		boolean flag = false;

		if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
			if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
				if (dd <= 30) {
					flag = true;
				}
			} else if (MM == 2) {
				if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
					if (dd <= 29) {
						flag = true;
					}
				} else if (dd <= 28) {
					flag = true;
				}
			} else {
				flag = true;
			}

		}
		return flag;
	}

	/**
	 * 根据所给的起始,终止时间来计算间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 间隔天数
	 */
	public static int getIntervalDay(final java.sql.Date startDate,
			final java.sql.Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) interval / (1000 * 60 * 60 * 24);
		return intervalday;
	}

	/**
	 * 根据所给的起始,终止时间来计算间隔月数
	 * 
	 * @param startDate
	 *            YYYYMM
	 * @param endDate
	 *            YYYYMM
	 * @return 间隔月数
	 */
	public static int getIntervalMonth(final String startDate,
			final String endDate) {
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int startMonth = 0;
		if (startDate.substring(4, 5).equals("0")) {
			startMonth = Integer.parseInt(startDate.substring(5));
		} else {
			startMonth = Integer.parseInt(startDate.substring(4, 6));
		}
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int endMonth = 0;
		if (endDate.substring(4, 5).equals("0")) {
			endMonth = Integer.parseInt(endDate.substring(5));
		}
		endMonth = Integer.parseInt(endDate.substring(4, 6));
		int intervalMonth = (endYear * 12 + endMonth)
				- (startYear * 12 + startMonth);
		return intervalMonth;
	}

	public static String getYearMonth() {
		Calendar cl = Calendar.getInstance();
		String year = String.valueOf(cl.get(Calendar.YEAR));
		String month = String.valueOf(cl.get(Calendar.MONTH) + 1);

		month = month.length() > 1 ? month : "0" + month;

		month = year + month;
		return month;
	}

	public static String getDayOfMonth() {
		Calendar cl = Calendar.getInstance();
		String day = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
		return day;
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 * 
	 * @param d
	 *            - 日期
	 * @param format
	 *            - 指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String dateToString(final Date d, final String format) {
		if (d == null) {
			return "";
		}
		Hashtable h = new Hashtable(); 
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1) {
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		} else if (s.indexOf("yy") != -1) {
			h.put(new Integer(s.indexOf("yy")), "yy");
		}
		if (s.indexOf("mm") != -1) {
			h.put(new Integer(s.indexOf("mm")), "MM");
		}

		if (s.indexOf("dd") != -1) {
			h.put(new Integer(s.indexOf("dd")), "dd");
		}
		if (s.indexOf("hh24") != -1) {
			h.put(new Integer(s.indexOf("hh24")), "HH");
		}
		if (s.indexOf("mi") != -1) {
			h.put(new Integer(s.indexOf("mi")), "mm");
		}
		if (s.indexOf("ss") != -1) {
			h.put(new Integer(s.indexOf("ss")), "ss");
		}

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1) {
			h.put(new Integer(s.indexOf("年")), "年");
		}
		if (s.indexOf("月") != -1) {
			h.put(new Integer(s.indexOf("月")), "月");
		}
		if (s.indexOf("日") != -1) {
			h.put(new Integer(s.indexOf("日")), "日");
		}
		if (s.indexOf("时") != -1) {
			h.put(new Integer(s.indexOf("时")), "时");
		}
		if (s.indexOf("分") != -1) {
			h.put(new Integer(s.indexOf("分")), "分");
		}
		if (s.indexOf("秒") != -1) {
			h.put(new Integer(s.indexOf("秒")), "秒");
		}

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n) {
					n = i;
				}
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat,
				new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 将指定格式的字符串转换为日期型
	 * 
	 * @param strDate
	 *            - 日期
	 * @param format
	 *            --oracle型日期格式
	 * @return 转换得到的日期
	 */
	public static Date stringToDate(final String strDate,
			final String oracleFormat) {
		if (strDate == null) {
			return null;
		}
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = oracleFormat.toLowerCase();
		if (s.indexOf("yyyy") != -1) {
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		} else if (s.indexOf("yy") != -1) {
			h.put(new Integer(s.indexOf("yy")), "yy");
		}
		if (s.indexOf("mm") != -1) {
			h.put(new Integer(s.indexOf("mm")), "MM");
		}

		if (s.indexOf("dd") != -1) {
			h.put(new Integer(s.indexOf("dd")), "dd");
		}
		if (s.indexOf("hh24") != -1) {
			h.put(new Integer(s.indexOf("hh24")), "HH");
		}
		if (s.indexOf("mi") != -1) {
			h.put(new Integer(s.indexOf("mi")), "mm");
		}
		if (s.indexOf("ss") != -1) {
			h.put(new Integer(s.indexOf("ss")), "ss");
		}

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1) {
			h.put(new Integer(s.indexOf("年")), "年");
		}
		if (s.indexOf("月") != -1) {
			h.put(new Integer(s.indexOf("月")), "月");
		}
		if (s.indexOf("日") != -1) {
			h.put(new Integer(s.indexOf("日")), "日");
		}
		if (s.indexOf("时") != -1) {
			h.put(new Integer(s.indexOf("时")), "时");
		}
		if (s.indexOf("分") != -1) {
			h.put(new Integer(s.indexOf("分")), "分");
		}
		if (s.indexOf("秒") != -1) {
			h.put(new Integer(s.indexOf("秒")), "秒");
		}

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n) {
					n = i;
				}
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}

		SimpleDateFormat df = new SimpleDateFormat(javaFormat);
		Date myDate = new Date();
		try {
			myDate = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}

		return myDate;
	}

	/**
	 * 将指定格式的字符串转换为日期型
	 * 
	 * @param strDate
	 *            - 日期
	 * @param format
	 *            --oracle型日期格式
	 * @return 转换得到的日期
	 */
	public static String GetDateFormate(final String strDate,
			final String oracleFormat) {
		if (strDate == null) {
			return null;
		}

		StringBuffer javaFormat = new StringBuffer();
		javaFormat.append(strDate.substring(0, 4));
		javaFormat.append("-");
		javaFormat.append(strDate.substring(4, 6));
		javaFormat.append("-");
		javaFormat.append(strDate.substring(6, 8));
		return javaFormat.toString();
	}

	/**
	 * UtilDate->SqlDate
	 * 
	 * @author 王长庚
	 * @param java
	 *            .util.Date date
	 * @return java.sql.Date
	 */
	public static java.sql.Date utilDate2SqlDate(final java.util.Date date) {
		java.sql.Date result = null;
		if (null != date) {
			long time = date.getTime();
			result = new java.sql.Date(time);
		}
		return result;

	}

	/**
	 * 判断两个日期是否为同一天(忽略时间)
	 * 
	 * @author 王长庚
	 * @param java
	 *            .sql.Date,java.sql.Date enddate
	 * @return List
	 */
	public static boolean isEqualDate(final Date args1, final Date args2) {
		if (args1 == null || args2 == null) {
			return false;
		}
		java.sql.Date date1 = new java.sql.Date(args1.getTime());
		java.sql.Date date2 = new java.sql.Date(args2.getTime());
		String date1str = date1.toString();
		String date2str = date2.toString();
		if (date1str.equals(date2str)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 生成以begindate为起点到enddate结束（不包括enddate）的有序日期集合
	 * 
	 * @author 王长庚
	 * @param java
	 *            .sql.Date begindate,java.sql.Date enddate
	 * @return List
	 */

	public static List creatWorkdayList(Date begindate, final Date enddate) {
		List list = new ArrayList();
		if (begindate == null || enddate == null) {
			return list;
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(begindate);

		while (!DateUtil.isEqualDate(begindate, enddate)) {
			Date date = ca.getTime();
			list.add(DateUtil.utilDate2SqlDate(date));
			ca.add(Calendar.DAY_OF_MONTH, 1);
			begindate = ca.getTime();
		}
		return list;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getLastDate(final String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getNextDate(final String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 取得给定日期的下一天
	 * 
	 * @author 王长庚
	 * @param java
	 *            .sql.Date
	 * @return Date
	 */

	public static java.sql.Date getNextDate(final java.sql.Date date) {
		if (date == null) {
			return null;
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		Date next = ca.getTime();
		return DateUtil.utilDate2SqlDate(next);
	}

	/**
	 * 取得给定日期的上一天
	 * 
	 * @author xiehb add
	 * @param java
	 *            .sql.Date
	 * @return Date
	 */

	public static java.sql.Date getLastDate(final java.sql.Date date) {
		if (date == null) {
			return null;
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		Date next = ca.getTime();
		return DateUtil.utilDate2SqlDate(next);
	}

	/**
	 * 获取输入"YYYYMM"前n个月的YYYYMM
	 * 
	 * @param inputDate
	 * @param n
	 * @return
	 */
	public static String getPreMonth(final String dateStr, final int n) {
		String endStr = "";
		int endY = 0;
		int endM = 0;
		int startYear = Integer.parseInt(dateStr.substring(0, 4));
		int startMonth = 0;
		if (dateStr.substring(4, 5).equals("0")) {
			startMonth = Integer.parseInt(dateStr.substring(5));
		} else {
			startMonth = Integer.parseInt(dateStr.substring(4, 6));
		}
		endY = startYear - n / 12;
		if (n < startMonth) {
			endM = startMonth - n;
		} else if (n % 12 < startMonth) {
			endM = startMonth - n % 12;
		} else {
			endY = endY - 1;
			endM = startMonth - n % 12 + 12;
		}

		if (endM < 10) {
			endStr = "" + endY + "0" + endM;
		} else {
			endStr = "" + endY + "" + endM;
		}

		return endStr;
	}

	/**
	 * 取得给定日期的下一天
	 * 
	 * @author 王长庚
	 * @param java
	 *            .sql.Date
	 * @return Date
	 */
	public static void main(final String args[]) {
		CommonQueryBS.systemOut(getAgeByBirthday(stringToDate("20160601", "yyyyMMdd"))+"");
		Calendar ca = Calendar.getInstance();
		java.sql.Date d = new java.sql.Date(ca.getTime().getTime());
		CommonQueryBS.systemOut(dateToString(d, "yyyymmdd"));
		CommonQueryBS.systemOut(getPreMonth("200504", 4));
		CommonQueryBS.systemOut(getPreMonth("200504", 18));

	}

	/**
	 * 取得给定日期的上个月
	 * 
	 * @author liwb
	 * @param Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getProvisMonthDay(final Date CrrentDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(CrrentDate);
		ca.add(Calendar.MONTH, -1);
		Date nextMonth = ca.getTime();
		return DateUtil.utilDate2SqlDate(nextMonth);
	}

	/**
	 * 取得给定日期的下个月
	 * 
	 * @author lijie
	 * @param Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getNextMonthDay(final Date CrrentDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(CrrentDate);
		ca.add(Calendar.MONTH, 1);
		Date nextMonth = ca.getTime();
		return DateUtil.utilDate2SqlDate(nextMonth);
	}

	/**
	 * 取得给定日期的上一年
	 * 
	 * @author lijie
	 * @param Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getProvisYearDay(final Date CrrentDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(CrrentDate);
		ca.add(Calendar.YEAR, -1);
		Date nextMonth = ca.getTime();
		return DateUtil.utilDate2SqlDate(nextMonth);
	}

	/**
	 * 取得给定日期的下一年
	 * 
	 * @author lijie
	 * @param Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getNextYearDay(final Date CrrentDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(CrrentDate);
		ca.add(Calendar.YEAR, 1);
		Date nextMonth = ca.getTime();
		return DateUtil.utilDate2SqlDate(nextMonth);
	}

	/**
	 * 把传入的java.util.Date 转换为 java.sql.Date, 为空日期，则将传入的日期设置为null
	 * 
	 * @param date
	 *            日期
	 * @return java.sql.Date
	 */
	public static java.sql.Date date2sqlDate(final Date date) {
		long time = 0;
		if (null == date) {
			return null;
		} else {
			time = date.getTime();
		}
		return new java.sql.Date(time);

	}

	/**
	 * 获取当前时间Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return now;
	}

	/*public static Long getLongDate(final String strdate) {

		strdate.replaceAll(" ", "");
		if (strdate.indexOf("-") > 0) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				sdf.setLenient(false);
				sdf.parse(strdate);
			} catch (Exception e) {
				
			}

			String[] time = strdate.split("-");
			if (time[1].length() == 1) {
				time[1] = "0" + time[1];
			} else if (time[1].length() == 2) {

			} else {
				
			}
			if (time[2].length() == 1) {
				time[2] = "0" + time[2];
			} else if (time[2].length() == 2) {

			} else {
				
			}
			String date = time[0] + time[1] + time[2];
			return Long.parseLong(date);
		} else if (strdate.indexOf("/") > 0) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				sdf.setLenient(false);
				sdf.parse(strdate);
			} catch (Exception e) {
				
			}

			String[] time = strdate.split("/");
			if (time[1].length() == 1) {
				time[1] = "0" + time[1];
			} else if (time[1].length() == 2) {

			} else {
				
			}
			if (time[2].length() == 1) {
				time[2] = "0" + time[2];
			} else if (time[2].length() == 2) {

			} else {
				
			}
			String date = time[0] + time[1] + time[2];
			return Long.parseLong(date);
		} else if (strdate.indexOf(".") > 0) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			try {
				sdf.setLenient(false);
				sdf.parse(strdate);
			} catch (Exception e) {
				
			}

			String[] time = strdate.split("\\.");
			if (time[1].length() == 1) {
				time[1] = "0" + time[1];
			} else if (time[1].length() == 2) {

			} else {
				
			}
			if (time[2].length() == 1) {
				time[2] = "0" + time[2];
			} else if (time[2].length() == 2) {

			} else {
				
			}
			String date = time[0] + time[1] + time[2];
			return Long.parseLong(date);
		} else {

			
		}
	}*/

	/**
	 * 获取与当前日期相差天数
	 * 
	 * @param starttime
	 * @return
	 */
	public static Long getCheckDay(final String starttime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		sdf.format(calendar.getTime());
		Date sysdate = null;
		Date begintime = null;
		Long checkday = Long.parseLong("0");
		try {
			sysdate = sdf.parse(sdf.format(calendar.getTime()));
			begintime = sdf1.parse(starttime);
			checkday = (sysdate.getTime() - begintime.getTime())
					/ (1000 * 24 * 60 * 60);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return checkday;
	}
	public static String timeToString(final Timestamp ts) {
		String tsStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//方法一
			tsStr = sdf.format(ts);
			//tsStr = ts.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
	
	public static Timestamp stringToTime(final String tsStr) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			ts = Timestamp.valueOf(tsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	//保证格式
	public static String timeToString(final Time ts) {
		String tsStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			//方法一
			tsStr = sdf.format(ts);
			//tsStr = ts.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
	
	public static String timeToString(final Timestamp ts, final String format) {
		String tsStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			//方法一
			tsStr = sdf.format(ts);
			//tsStr = ts.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
	/**
	 * yyyyMMdd
	 * @return
	 */
	public static String getcurdate(){
		
		return DateUtil.dateToString(new Date(), "yyyyMMdd");
	}
	
	public static String Long2String(Long s,String format){
		if(s==null){
			return null;
		}
		if(format==null||"".equals(format)){
			format = "yyyyMMdd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		Date d = new Date(s);
		
		return sdf.format(d);
	}
	
	/** * 根据用户生日计算年龄 */
	public static int getAgeByBirthday(Date birthday) {	
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);	
		int monthNow = cal.get(Calendar.MONTH) + 1;	
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);	
		cal.setTime(birthday);	
		int yearBirth = cal.get(Calendar.YEAR);	
		int monthBirth = cal.get(Calendar.MONTH) + 1;	
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);	
		int age = yearNow - yearBirth;	
		if (monthNow <= monthBirth) {		
			if (monthNow == monthBirth) {			
				// monthNow==monthBirth 			
				if (dayOfMonthNow < dayOfMonthBirth) {				
					age--;			
				}		
			} else {			
				// monthNow>monthBirth 			
				age--;		
			}	
		}	
		return age;
	}

	public static Object stringToDate_Size6_8(String strDate) {
		if (strDate == null|| strDate.equals("")) {
			return null;
		}
		if(strDate.length()==6){
			strDate = strDate +"01";
		} 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date myDate = new Date();
		try {
			myDate = df.parse(strDate);
			java.sql.Date date = date2sqlDate(myDate);
			return date;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Timestamp stringToTime2(final String tsStr) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			if(tsStr.length()>19){
				ts = Timestamp.valueOf(tsStr.substring(0, 19));
			} else if(tsStr.length()==19){
				ts = Timestamp.valueOf(tsStr);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
	/**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
 
	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
	    SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
	    Calendar calendar = Calendar.getInstance();
	    return df.format(calendar.getTime());
	}

	/**
	 * 格式化时间字符串
	 * @param dataStr		4位、6位、8位时间字符串
	 * @param gejianzifu1	第一分割符
	 * @param gejianzifu2	第二分割符
	 * @param gejianzifu3	第三分割符
	 * @return
	 */
	public static String dataStrFormart(Object dataStrObj, String gejianzifu1, String gejianzifu2, String gejianzifu3) {
		if(dataStrObj == null){
			return "";
		}
		String dataStr = dataStrObj.toString();
		dataStr=dataStr.trim();
		if("".equals(dataStr) || "*".equals(dataStr) ){
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		char charAt;
		for(int i=0;i<dataStr.length();i++){
			charAt = dataStr.charAt(i);//只保留数字
			buffer.append(charAt>=48 && charAt<=57 ? charAt : "");
		}
		dataStr = buffer.toString();
		int dataStrLength = dataStr.length();
		String dataStr4 = dataStr.substring(0, 4);
		if(dataStrLength >= 8){
			String dataStr6 = dataStr.substring(4, 6);
			String dataStr8 = dataStr.substring(6, 8);
			if("月".equals(gejianzifu2) || "日".equals(gejianzifu2) ){
				dataStr6 = dataStr6.startsWith("0")?dataStr6.substring(1):dataStr6;
				dataStr8 = dataStr8.startsWith("0")?dataStr8.substring(1):dataStr8;
				dataStr = dataStr4 + gejianzifu1 + dataStr6 + gejianzifu2 + dataStr8 + gejianzifu3;
			}else{
				dataStr = dataStr4 + gejianzifu1 + dataStr6 + gejianzifu2 + dataStr8;
			}
		}else if(dataStrLength == 6){
			String dataStr6 = dataStr.substring(4, 6);
			if("月".equals(gejianzifu2)){
				dataStr6 = dataStr6.startsWith("0")?dataStr6.substring(1):dataStr6;
				dataStr = dataStr4 + gejianzifu1 + dataStr6 + gejianzifu2;
			}else{
				dataStr = dataStr4 + gejianzifu1 + dataStr6;
			}
		}else {
			if(dataStrLength == 4) dataStr = dataStr4;
		}
		
		return dataStr;
	}
}
