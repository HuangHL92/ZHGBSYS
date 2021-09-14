package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;


/**
 * 时间格式的转换
 * @author 周兆巍
 */
public final class DateUtil extends com.insigma.odin.framework.util.DateUtil
{
	/**
	 * 日期紧凑格式
	 */
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * 日期普通格式
	 */
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 日期格式 年月日 时分秒
	 */
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 将字符串日期转换成yyyyMMdd的形式，strDate格式必须"yyyy-MM-dd"。
	 * 将字符串日期转换成yyyyMM的形式，strDate格式必须"yyyy-MM"。
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum(String strDate) throws AppException{
		if(strDate==null || strDate.equals("")){
			return null;
		}
		String[] date=null;
		String newDate="";
		if(strDate.indexOf("-")>=0)
		{
		     date=strDate.split("-");
		     for(int i=0;i<date.length;i++){
		    	 newDate=newDate+date[i];
		     }	     
		     return Long.parseLong(newDate);
		}
	
		return Long.parseLong(strDate);
	}
	/**
	 * 将字符串日期转换成yyyyMMdd的形式，strDate格式为"yyyy-MM-dd"或"yyyy-M-d"。
	 * 将字符串日期转换成yyyyMM的形式，strDate格式必须"yyyy-M"。
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum1(String strDate) throws AppException{
		if(strDate==null){
			return null;
		}
		String[] date=null;
		String newDate="";
		if(strDate.indexOf("-")>=0)
		{
		     date=strDate.split("-");
		     for(int i=0;i<date.length;i++){
		    	 if(date[i].length()== 1){
		    		 newDate=newDate+"0"+date[i];
		    	 }else{
		    		 newDate=newDate+date[i];
		    	 }
		     }	     
		     return Long.parseLong(newDate);
		}
	
		return Long.parseLong(strDate);
	}
	
	/**
	 * 将字符串日期转换成yyyyMMdd的形式，strDate格式为"yyyy-MM-dd......"
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum2(String strDate) throws AppException{
		if(strDate==null){
			return null;
		}
		return strDateToNum(strDate.substring(0,10));
	}
	
	/**
	 * 将数字日期转换成yyyy-MM-dd的字符串形式"。
	 * @param numDate
	 * @return
	 */
	public static String numDateToStr(Long numDate)
	{
		if(numDate==null){
			return null;
		}
		String strDate=null;
		strDate=numDate.toString().substring(0, 4)+"-"+numDate.toString().substring(4, 6)+"-"+
		numDate.toString().substring(6, 8);
		return strDate;		
	}
	
	/**
	 * 将字符串日期转换成yyyy-MM-dd的字符串形式"。
	 * @param numDate
	 * @return
	 */
	public static String numDateToStr1(String numDate)
	{
		if(numDate==null || numDate.equals("")){
			return null;
		}
		String strDate=null;
		strDate=numDate.substring(0, 4)+"-"+numDate.substring(4, 6)+"-"+
		numDate.substring(6, 8);
		return strDate;		
	}
	
	/**
	 * 将系统日期转换成yyyyMMdd的形式
	 * @return
	 * @throws Exception
	 */
    public static Long sysDateToNum(String format) throws AppException
    {
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return strDateToNum(sdf.format(HBUtil.getSysdate()));
    }
    
    /**
     * 将传入的字符串，根据给定的格式转换为Date类型
     * @param str 待转换的字符串
     * @param format 指定的格式
     * @return 转换后的日期
     * @throws AppException 如果转换出错将抛出此异常
     * @author 冯宁前
     */
    public static Date stringToDate(String str,String format) throws AppException{
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	try {
			return sdf.parse(str);
		} catch (ParseException e) {
			throw new AppException("解析日期字符串时出错！");
		}
    }
    
    /**
     * 将传入的日期，根据给定的格式，格式化为字符串
     * @param date 需要转换的日期
     * @param format 指定的格式
     * @return 格式化后的字符串
     * @author 冯宁前
     */
    public static String dateToString(Date date,String format){
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return sdf.format(date);
    }
    
    /**
     * 将传入的日期，根据给定的格式，格式化为字符串
     * @param date 需要转换的日期
     * @param format 指定的格式
     * @return 格式化后的字符串
     * @author 冯宁前
     */
    public static Long dateToLong(Date date,String format){
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return Long.valueOf(sdf.format(date));
    }
    
    /**
     * 将字符串转换为日期类型，字符串的格式为紧凑格式，格式为 COMPACT_DATE_FORMAT
     * @param str 需要转换的字符串
     * @return 转换后得到的日期
     * @throws AppException 转换失败将抛出此异常
     * @author 冯宁前
     */
    public static Date compactStringToDate(String str) throws AppException{
    	return stringToDate(str, COMPACT_DATE_FORMAT);
    }
    
    /**
     * 将日期类型格式化为字符串，字符串的格式为紧凑格式，格式为 COMPACT_DATE_FORMAT
     * @param date 需要格式化的日期
     * @return 格式化得到的字符
     * @author 冯宁前
     */
    public static String dateToCompactString(Date date){
    	return dateToString(date, COMPACT_DATE_FORMAT);
    }
    
    /**
     * 将日期转换为普通日期格式字符串，字符串的格式为 NORMAL_DATE_FORMAT
     * @param date 需要格式化的日期
     * @return 格式化得到的字符
     * @author 冯宁前
     */
    public static String dateToNormalString(Date date){
    	return dateToString(date, NORMAL_DATE_FORMAT);
    }
    
    /**
     * 将紧凑格式日期字符串转换为普通日期格式字符串
     * @param str 紧凑格式日期字符串
     * @return 普通日期格式字符串
     * @throws AppException 如果转换不成功将抛出此异常
     * @author 冯宁前
     */
    public static String compactStringDateToNormal(String str) throws AppException{
    	return dateToNormalString(compactStringToDate(str));	
    }
    
    /**
     * 取二个日期之间的天数
     * @param date_str 起始日期
     * @param date_end 终止日期
     * @return 日期间天数
     * @author 李桂
     */
    public static int getDaysBetween(Date date_str, Date date_end) throws AppException{
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(date_str);
		d2.setTime(date_end);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			throw new AppException("起始日期小于终止日期!");
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}
    
    /**
     * 日期加N天(正负天数)
     * @param date 日期
     * @param days 天数
     * @return 日期间天数
     * @author 李桂
     */
    public static Date addDays(Date date, int days) throws AppException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();
		
	}

    /**
     * 字符型日期加N天(正负天数)
     * @param str 字符型日期
     * @param format 字符型格式(实际的字符型日期格式：yyyyMMdd yyyy-MM-dd)
     * @param days 天数
     * @return 日期间天数
     * @author 李桂
     */
    public static Date addDays(String str,String format, int days) throws AppException{
		Calendar calendar = Calendar.getInstance();
		Date date=stringToDate(str, format);
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();
		
	}
    
    /**
     * @$comment 将java.util.Date 转成 java.sql.Date 
     * @param date java.util.Date
     * @return java.sql.Date
     * @throws AppException
     */
    public static java.sql.Date getSqlDate(Date date) throws AppException{
        java.sql.Date sqlDate= new java.sql.Date(date.getTime()); 
		return sqlDate;  	
    }


    
    /**
     * @$comment 获取两个日期之间的月数 
     * @param max_hd 大的月
     * @param min_hd 小的月
     * @return
     * @throws AppException
     */
    @SuppressWarnings("unchecked")
	public static int monthsBetween_L(Long max_hd,Long min_hd)throws AppException{
    	String sql="select months_between(to_date('"+max_hd+"','YYYYMM'),to_date('"+min_hd+"','YYYYMM')) as months from dual";
		List<HashMap> hmLst=getQueryInfoByManulSQL(sql);
		int months=0;
		if(hmLst.size()>0){
        	HashMap hp=(HashMap)hmLst.get(0);
            months=Integer.parseInt((String)hp.get("months"));
		}
    	return months;
    }
    
    
    /**
     * @$comment 获取两个日期之间的月数 
     * @param max_hd 大的月
     * @param min_hd 小的月
     * @return
     * @throws AppException
     */
    @SuppressWarnings("unchecked")
	public static int monthsBetween_LS(Long max_hd,Long min_hd)throws AppException{
    	String sql="select months_between(to_date(substr('"+max_hd+"',0,6),'YYYYMM'),to_date(substr('"+min_hd+"',0,6),'YYYYMM')) as months from dual";
		List<HashMap> hmLst=getQueryInfoByManulSQL(sql);
		int months=0;
		if(hmLst.size()>0){
        	HashMap hp=(HashMap)hmLst.get(0);
            months=Integer.parseInt((String)hp.get("months"));
		}
    	return months;
    }
    
    /**
     * @$comment 获取两个日期之间的月数 
     * @param max_hd 大的日期
     * @param min_hd 小的日期
     * @return
     * @throws AppException
     */
    @SuppressWarnings("unchecked")
	public static int monthsBetween_D(Long max_d,Long min_d)throws AppException{
    	String sql="select months_between(to_date('"+max_d+"','YYYYMMdd'),to_date('"+min_d+"','YYYYMMdd')) as months from dual";
		List<HashMap> hmLst=getQueryInfoByManulSQL(sql);
		int months=0;
		if(hmLst.size()>0){
        	HashMap hp=(HashMap)hmLst.get(0);
            months=Integer.parseInt((String)hp.get("months"));
		}
    	return months;
    }
    
    /**
     * @$comment 根据年月获取年月日
     * @param ym
     * @return ymd
     * @throws AppException
     */
    @SuppressWarnings("unchecked")
	public static Long getYmdByYm(Long ym)throws AppException{
    	String sql="select to_char(last_day(to_date("+ym.toString()+",'yyyymm')),'yyyymmdd') as ymd from dual";
		List<HashMap> hmLst=getQueryInfoByManulSQL(sql);
		Long ymd=null;
		if(hmLst.size()>0){
        	HashMap hp=(HashMap)hmLst.get(0);
        	ymd=Long.valueOf((String)hp.get("ymd"));
		}
    	return ymd;
    }
    
	/**
	 * @$comment 通过特定SQL获取指定的事件信息
	 * @param sql 拼装的sql语句
	 * @return List<HashMap>
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap> getQueryInfoByManulSQL(String sql) throws AppException{
		HBSession sess = HBUtil.getHBSession();
 		CommonQueryBS query=new CommonQueryBS();
		query.setConnection(sess.connection());	
 		query.setQuerySQL(sql);
		Vector<?> vector=query.query();
		Iterator<?> iterator = vector.iterator();
		List<HashMap> hmLst=new java.util.ArrayList<HashMap>();
		while (iterator.hasNext())
        {
			HashMap tmp= (HashMap)iterator.next();		
			hmLst.add(tmp);
		}
		return hmLst;		
	}   
	
	/**
	 * @$comment YYYYMM日期加i月（i可以大于12）
	 * @param toPjym
	 * @return YYYYMM
	 * @author 傅元晶
	 * @throws AppException，RadowException
	 */
	public static String addMonthYM(Long toPjym, int i) throws AppException{
		
		return String.valueOf(DateUtil.addMonths(toPjym.intValue(), i));
		/*
		
		if(toPjym%100>12){
			throw new AppException("给定的日期月数不能大于12！，请重试");
		}

		String pjym = toPjym + "";
		int year = Integer.valueOf(pjym.substring(0, 4));
		if (i >= 12) {
			year += (i / 12);
			i = i % 12;
		}

		String m = pjym.substring(4, 6);
		int month = 0;
		if (m.startsWith("0")) {
			m = pjym.substring(5, 6);
			month = Integer.valueOf(m);
			month = month + i;
			if (!(month < 10)) {
				return year + "" + month;
			}
			return year + "0" + month;

		}
		month = Integer.valueOf(m) + i;
		if (month > 12) {
			year += month / 12;
			month = month % 12;
			
			return year + "0" + month;
		}
		return year + "" + month;
	*/}
	/**
	 * 日期减一个月
	 * @param strdate 格式yyyymmdd
	 * @return 格式 yyyymmdd
	 * @author 傅元晶，程成圭
	 * @throws AppException，RadowException
	 */
	public static String deleteMonthYM(String strdate) throws AppException{
		String date = String.valueOf(strDateToNum(strdate));
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4, 6));
		int time = Integer.valueOf(date.substring(6, 8));
		if(month==1){
			year = year-1;
			month = 12;
		}if(date.substring(4, 6).equals("08") && date.substring(6, 8).equals("31")){
			month = month -1;
		}else if(!date.substring(4, 6).equals("08") && date.substring(6, 8).equals("31")){
			month = month -1;
			time = time-1;
		}else{
			month = month -1;
		}
		String ymt="";
		String month1="";
		String time1="";
		if(month<10){
			month1 ="0"+month;
		}else{
			month1=String.valueOf(month);
		}
		if(time<10){
			time1 ="0"+time;
		}else{
			time1 =String.valueOf(time);
		}
		return ymt=""+year+""+month1+""+time1;
	}
	/**
	 * 日期增加一个月
	 * @param strdate yyyymmdd
	 * @return 格式 yyyymmdd
	 * @author 程成圭
	 * @throws AppException
	 */
	public static String addMonthYMD(String strdate)throws AppException{
		String date = String.valueOf(strDateToNum(strdate));
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4, 6));
		int time = Integer.valueOf(date.substring(6,8));
		if(month==12){
			year = year+1;
			month = 1;
		}if(date.substring(4, 6).equals("07") && date.substring(6, 8).equals("31")){
			month = month +1;
		}else if(!date.substring(4, 6).equals("07") && date.substring(6, 8).equals("31")){
			month = month +1;
			time = time-1;
		}else{
			month = month +1;
		}
		String ymt="";
		String month1="";
		String time1="";
		if(month<10){
			month1 ="0"+month;
		}else{
			month1=String.valueOf(month);
		}
		if(time<10){
			time1 ="0"+time;
		}else{
			time1 =String.valueOf(time);
		}
		return ymt=""+year+""+month1+""+time1;
	}
	
	/**
	 * 获取下个月的第一天
	 * @param numberDate yyyyMMdd
	 * @return 格式yyyyMMdd
	 * @author zhaoq
	 * @throws AppException
	 */
	public static Long getNextMonthFirstDay(Long numberDate) throws AppException{
		String date = String.valueOf(numberDate);
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4, 6));
		
		if(month==12){
			month = 1;
			year = year + 1;
		}else{
			month = month + 1;
		}
		
		String strMonth = String.valueOf(month);
		if(month<10){
			strMonth ="0"+month;
		}
		
		String strTime = "01";
		
		String strDate = year + "-" + strMonth + "-" + strTime;
		return strDateToNum(strDate);
		
	}
	/**
	 * 获取下个月的第一天
	 * @param strDate yyyy-MM-dd
	 * @return 格式yyyy-MM-dd
	 * @author zhaoq
	 * @throws AppException
	 */
	public static String getNextMonthFirstDay(String strDate) throws AppException{
		int year = Integer.valueOf(strDate.substring(0, 4));
		int month = Integer.valueOf(strDate.substring(5, 7));
		
		if(month==12){
			month = 1;
			year = year + 1;
		}else{
			month = month + 1;
		}
		
		String strMonth = String.valueOf(month);
		if(month<10){
			strMonth ="0"+month;
		}
		
		String strTime = "01";
		
		String returnStrDate = year + "-" + strMonth + "-" + strTime;
		return returnStrDate;
		
	}
	
	/**
	 * 获取当前年
	 * @return yyyy
	 */
	public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
	}
	
	/**
	 * 获取当前日期
	 * @return  yyyy-MM-dd
	 */
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
	}
	
	/**
	 * 获取当前日期 时分秒
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateHMS(){
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_NEW);
        Date date = new Date();
        return sdf.format(date);
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
}