package com.insigma.siis.local.pagemodel.customquery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class GroupPageBS {
	public static String getDateformY(String y, String endDay){
		//年龄范围转换成日期范围
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		Calendar calendar=Calendar.getInstance();   
		
		if(endDay==null||"".equals(endDay)){
			endDay = sdf.format(new Date());
		}
		
		if(!StringUtils.isNumeric(y)){//是否数字
			return "";
		}
		
		String retDate = "";
		try {
			if(endDay.length()==6){
				endDay = endDay + "01";
			}
			Date djiezsj = sdf.parse(endDay);
			calendar.setTime(djiezsj); 
			int year=0;
			if(y!=null&&!"".equals(y)){
				year = Integer.parseInt(y);
				calendar.add(Calendar.YEAR, -year);
				retDate = sdf.format(calendar.getTime());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retDate;
	}
}
