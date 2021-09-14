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
 * ʱ���ʽ��ת��
 * @author ����Ρ
 */
public final class DateUtil extends com.insigma.odin.framework.util.DateUtil
{
	/**
	 * ���ڽ��ո�ʽ
	 */
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * ������ͨ��ʽ
	 */
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * ���ڸ�ʽ ������ ʱ����
	 */
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * ���ַ�������ת����yyyyMMdd����ʽ��strDate��ʽ����"yyyy-MM-dd"��
	 * ���ַ�������ת����yyyyMM����ʽ��strDate��ʽ����"yyyy-MM"��
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
	 * ���ַ�������ת����yyyyMMdd����ʽ��strDate��ʽΪ"yyyy-MM-dd"��"yyyy-M-d"��
	 * ���ַ�������ת����yyyyMM����ʽ��strDate��ʽ����"yyyy-M"��
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
	 * ���ַ�������ת����yyyyMMdd����ʽ��strDate��ʽΪ"yyyy-MM-dd......"
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
	 * ����������ת����yyyy-MM-dd���ַ�����ʽ"��
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
	 * ���ַ�������ת����yyyy-MM-dd���ַ�����ʽ"��
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
	 * ��ϵͳ����ת����yyyyMMdd����ʽ
	 * @return
	 * @throws Exception
	 */
    public static Long sysDateToNum(String format) throws AppException
    {
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return strDateToNum(sdf.format(HBUtil.getSysdate()));
    }
    
    /**
     * ��������ַ��������ݸ����ĸ�ʽת��ΪDate����
     * @param str ��ת�����ַ���
     * @param format ָ���ĸ�ʽ
     * @return ת���������
     * @throws AppException ���ת�������׳����쳣
     * @author ����ǰ
     */
    public static Date stringToDate(String str,String format) throws AppException{
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	try {
			return sdf.parse(str);
		} catch (ParseException e) {
			throw new AppException("���������ַ���ʱ����");
		}
    }
    
    /**
     * ����������ڣ����ݸ����ĸ�ʽ����ʽ��Ϊ�ַ���
     * @param date ��Ҫת��������
     * @param format ָ���ĸ�ʽ
     * @return ��ʽ������ַ���
     * @author ����ǰ
     */
    public static String dateToString(Date date,String format){
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return sdf.format(date);
    }
    
    /**
     * ����������ڣ����ݸ����ĸ�ʽ����ʽ��Ϊ�ַ���
     * @param date ��Ҫת��������
     * @param format ָ���ĸ�ʽ
     * @return ��ʽ������ַ���
     * @author ����ǰ
     */
    public static Long dateToLong(Date date,String format){
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return Long.valueOf(sdf.format(date));
    }
    
    /**
     * ���ַ���ת��Ϊ�������ͣ��ַ����ĸ�ʽΪ���ո�ʽ����ʽΪ COMPACT_DATE_FORMAT
     * @param str ��Ҫת�����ַ���
     * @return ת����õ�������
     * @throws AppException ת��ʧ�ܽ��׳����쳣
     * @author ����ǰ
     */
    public static Date compactStringToDate(String str) throws AppException{
    	return stringToDate(str, COMPACT_DATE_FORMAT);
    }
    
    /**
     * ���������͸�ʽ��Ϊ�ַ������ַ����ĸ�ʽΪ���ո�ʽ����ʽΪ COMPACT_DATE_FORMAT
     * @param date ��Ҫ��ʽ��������
     * @return ��ʽ���õ����ַ�
     * @author ����ǰ
     */
    public static String dateToCompactString(Date date){
    	return dateToString(date, COMPACT_DATE_FORMAT);
    }
    
    /**
     * ������ת��Ϊ��ͨ���ڸ�ʽ�ַ������ַ����ĸ�ʽΪ NORMAL_DATE_FORMAT
     * @param date ��Ҫ��ʽ��������
     * @return ��ʽ���õ����ַ�
     * @author ����ǰ
     */
    public static String dateToNormalString(Date date){
    	return dateToString(date, NORMAL_DATE_FORMAT);
    }
    
    /**
     * �����ո�ʽ�����ַ���ת��Ϊ��ͨ���ڸ�ʽ�ַ���
     * @param str ���ո�ʽ�����ַ���
     * @return ��ͨ���ڸ�ʽ�ַ���
     * @throws AppException ���ת�����ɹ����׳����쳣
     * @author ����ǰ
     */
    public static String compactStringDateToNormal(String str) throws AppException{
    	return dateToNormalString(compactStringToDate(str));	
    }
    
    /**
     * ȡ��������֮�������
     * @param date_str ��ʼ����
     * @param date_end ��ֹ����
     * @return ���ڼ�����
     * @author ���
     */
    public static int getDaysBetween(Date date_str, Date date_end) throws AppException{
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(date_str);
		d2.setTime(date_end);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			throw new AppException("��ʼ����С����ֹ����!");
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
     * ���ڼ�N��(��������)
     * @param date ����
     * @param days ����
     * @return ���ڼ�����
     * @author ���
     */
    public static Date addDays(Date date, int days) throws AppException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();
		
	}

    /**
     * �ַ������ڼ�N��(��������)
     * @param str �ַ�������
     * @param format �ַ��͸�ʽ(ʵ�ʵ��ַ������ڸ�ʽ��yyyyMMdd yyyy-MM-dd)
     * @param days ����
     * @return ���ڼ�����
     * @author ���
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
     * @$comment ��java.util.Date ת�� java.sql.Date 
     * @param date java.util.Date
     * @return java.sql.Date
     * @throws AppException
     */
    public static java.sql.Date getSqlDate(Date date) throws AppException{
        java.sql.Date sqlDate= new java.sql.Date(date.getTime()); 
		return sqlDate;  	
    }


    
    /**
     * @$comment ��ȡ��������֮������� 
     * @param max_hd �����
     * @param min_hd С����
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
     * @$comment ��ȡ��������֮������� 
     * @param max_hd �����
     * @param min_hd С����
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
     * @$comment ��ȡ��������֮������� 
     * @param max_hd �������
     * @param min_hd С������
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
     * @$comment �������»�ȡ������
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
	 * @$comment ͨ���ض�SQL��ȡָ�����¼���Ϣ
	 * @param sql ƴװ��sql���
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
	 * @$comment YYYYMM���ڼ�i�£�i���Դ���12��
	 * @param toPjym
	 * @return YYYYMM
	 * @author ��Ԫ��
	 * @throws AppException��RadowException
	 */
	public static String addMonthYM(Long toPjym, int i) throws AppException{
		
		return String.valueOf(DateUtil.addMonths(toPjym.intValue(), i));
		/*
		
		if(toPjym%100>12){
			throw new AppException("�����������������ܴ���12����������");
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
	 * ���ڼ�һ����
	 * @param strdate ��ʽyyyymmdd
	 * @return ��ʽ yyyymmdd
	 * @author ��Ԫ�����̳ɹ�
	 * @throws AppException��RadowException
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
	 * ��������һ����
	 * @param strdate yyyymmdd
	 * @return ��ʽ yyyymmdd
	 * @author �̳ɹ�
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
	 * ��ȡ�¸��µĵ�һ��
	 * @param numberDate yyyyMMdd
	 * @return ��ʽyyyyMMdd
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
	 * ��ȡ�¸��µĵ�һ��
	 * @param strDate yyyy-MM-dd
	 * @return ��ʽyyyy-MM-dd
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
	 * ��ȡ��ǰ��
	 * @return yyyy
	 */
	public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
	}
	
	/**
	 * ��ȡ��ǰ����
	 * @return  yyyy-MM-dd
	 */
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
	}
	
	/**
	 * ��ȡ��ǰ���� ʱ����
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateHMS(){
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_NEW);
        Date date = new Date();
        return sdf.format(date);
	}
	
	/** * �����û����ռ������� */
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