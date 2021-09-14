package com.insigma.siis.local.pagemodel.publicServantManage;

public class YeartoMonths {
	//时间转换
	public static Byte DataChange(String year,String months){
		Byte year_flag =0;
		Byte month_flag =0;
		try{
			year_flag= Byte.valueOf(year);
		}catch (Exception e) {
		}
		try{
			month_flag = Byte.valueOf(months);
		}catch (Exception e) {
		}
		Byte result = 0;
		if(year_flag!=0||month_flag!=0){
			//计算成月份
			result = (byte) (year_flag*12+month_flag);
		}
		return result;
	}
}
