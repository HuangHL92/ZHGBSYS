package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.RadowException;

public class GbjbqkxzccSql {

	public GbjbqkxzccSql() {
		
	}
	
	public void returnSqlSelect(StringBuffer sb) throws RadowException{
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb.append(" "
					+ " SELECT  "
					
		            +" a.A0221, "//当前职位层次
	           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
	           		+" ),'yyyy-mm-dd'),12)>trunc(sysdate) and to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,1),'yyyy-mm-dd')), 'yyyy-mm-dd')"
	           		+ "<trunc(sysdate) then 1 else 0 end ) xzccxy1, "//不满1年
	           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-25),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),24)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),12)<=trunc(sysdate) then 1 else 0 end) xzccdy1, "//1年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-37),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),36)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-23),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),24)<=trunc(sysdate) then 1 else 0 end) xzccdy2, "//2年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-49),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),48)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-35),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),36)<=trunc(sysdate) then 1 else 0 end) xzccdy3, "//3年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-61),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),60)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-47),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),48)<=trunc(sysdate) then 1 else 0 end) xzccdy4, "//4年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-73),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),72)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-59),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),60)<=trunc(sysdate) then 1 else 0 end) xzccdy5, "//5年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-85),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),84)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-71),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),72)<=trunc(sysdate) then 1 else 0 end) xzccdy6, "//6年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-97),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),96)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-23),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),84)<=trunc(sysdate) then 1 else 0 end) xzccdy7, "//7年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-109),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),108)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-95),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),96)<=trunc(sysdate) then 1 else 0 end) xzccdy8, "//8年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-121),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),120)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-107),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),108)<=trunc(sysdate) then 1 else 0 end) xzccdy9, "//9年
			   +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-133),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),132)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-119),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),120)<=trunc(sysdate) then 1 else 0 end) xzccdy10, "//10年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-145),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),144)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-131),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),132)<=trunc(sysdate) then 1 else 0 end) xzccdy11, "//11年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-157),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),156)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-143),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),144)<=trunc(sysdate) then 1 else 0 end) xzccdy12, "//12年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-169),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),168)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-155),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),156)<=trunc(sysdate) then 1 else 0 end) xzccdy13, "//13年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-181),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),180)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-167),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),168)<=trunc(sysdate) then 1 else 0 end) xzccdy14, "//14年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-193),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),192)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-179),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),180)<=trunc(sysdate) then 1 else 0 end) xzccd15, "//15年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-205),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),204)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-191),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),192)<=trunc(sysdate) then 1 else 0 end) xzccdy16, "//16年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-217),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),216)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-203),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),204)<=trunc(sysdate) then 1 else 0 end) xzccdy17, "//17年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-229),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),228)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-215),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),216)<=trunc(sysdate) then 1 else 0 end) xzccdy18, "//18年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-241),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),240)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-227),'yyyy-mm-dd') "
		           +" ),'yyyy-mm-dd'),228)<=trunc(sysdate) then 1 else 0 end) xzccdy19, "//19年
		       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-239),'yyyy-mm-dd') "
	          	   +" ),'yyyy-mm-dd'),240)<=trunc(sysdate) then 1 else 0 end ) xzccdy20, "//20年及以上
	          + " count(a0000) heji");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append(" "
					+ " SELECT  "
					
		            +" a.A0221, "//当前职位层次
		            +" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 11 month),'%y%m%d'),a0288  ),"
		            +" '%y%m%d'),interval 12 month)>curdate()"  
		            +"  and str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 1 month),'%y%m%d'),a0288), "
		            		    
					+"  '%y%m%d')<curdate() then 1 else 0 end ) xzccxy1,   "
		            +" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null, "
		            		    
					+" date_format(date_sub(curdate(),interval 25 month),'%Y%M%D'),a0288  ),'%Y%M%D'),interval 24 month) >curdate()   and   "
							            		    
					+" date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 11 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 12 month)<=curdate() then 1 else 0 end) xzccdy1,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 37 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 36 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 23 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 24 month)<=curdate() then 1 else 0 end) xzccdy2, "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 49 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 48 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 35 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 36 month)<=curdate() then 1 else 0 end) xzccdy3,   "
							            		    
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 61 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 60 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 47 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 48 month)<=curdate() then 1 else 0 end) xzccdy4,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 73 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 72 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 59 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 60 month)<=curdate() then 1 else 0 end) xzccdy5,  "
							            		    
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 85 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 84 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 71 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 72 month )<=curdate() then 1 else 0 end) xzccdy6,   "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 97 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 96 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 23 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 84 month)<=curdate() then 1 else 0 end) xzccdy7,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 109 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 108 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 95 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 96 month)<=curdate() then 1 else 0 end) xzccdy8,   "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 121 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 120 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 107 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 108 month)<=curdate() then 1 else 0 end) xzccdy9,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 133 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 132 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 119 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 120 month)<=curdate() then 1 else 0 end) xzccdy10,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 145 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 144 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 131 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 132 month)<=curdate() then 1 else 0 end) xzccdy11,   "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 157 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 156 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 143 month),'%Y%M%D'),a0288  ), "
							            		   
					+" '%Y%M%D'),interval 144 month)<=curdate() then 1 else 0 end) xzccdy12,  "
							            		  
					+"  sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 169 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 168 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 155 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 156 month)<=curdate()then 1 else 0 end) xzccdy13,   "
							            		  
					+"  sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 181 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 180 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 167 month),'%Y%M%D'),a0288  ), "
							            		   
					+" '%Y%M%D'),interval 168 month)<=curdate() then 1 else 0 end) xzccdy14,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 193 month),'%Y%M%D') ,a0288 ), "
							            		  
					+" '%Y%M%D'),interval 192 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 179 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 180 month)<=curdate() then 1 else 0 end) xzccd15,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 205 month),'%Y%M%D'),a0288  ), "
							            		  
					+" '%Y%M%D'),interval 204 month)>curdate() and  date_add(str_to_date(if(0288='' or a0288 is null,date_format(date_sub(curdate(),interval 191 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 192 month)<=curdate() then 1 else 0 end) xzccdy16,  "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 217 month),'%Y%M%D') ,a0288 ), "
							            		  
					+" '%Y%M%D'),interval 216 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 203 month),'%Y%M%D'),a0288  ), "
							            		    
					+" '%Y%M%D'),interval 204 month)<=curdate() then 1 else 0 end) xzccdy17,   "
							            		  
					+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 229 month),'%Y%M%D'),a0288  ), "
		            		  
		            		    +" '%Y%M%D'),interval 228 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 215 month),'%Y%M%D'),a0288  ), "
		            		    
		            		    +" '%Y%M%D'),interval 216 month)<=curdate() then 1 else 0 end) xzccdy18, "
		            		  
		            		  +" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 241 month),'%Y%M%D'),a0288  ), "
		            		  
		            		    +" '%Y%M%D'),interval 240 month)>curdate() and  date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 227 month),'%Y%M%D'),a0288  ), "
		            		   
		            		    +" '%Y%M%D'),interval 228 month)<=curdate() then 1 else 0 end) xzccdy19,   "
		            		   
		            		+" sum( case when date_add(str_to_date(if(a0288='' or a0288 is null,date_format(date_sub(curdate(),interval 239 month),'%Y%M%D'),a0288  ), "

		            		  +" '%Y%M%D'),interval 240 month)<=curdate() then 1 else 0 end ) xzccdy20, "
	          + " count(a0000) heji ");
		}else{
			throw new RadowException("发现未知数据源，请联系系统管理员!");
		}
		
	}

}
