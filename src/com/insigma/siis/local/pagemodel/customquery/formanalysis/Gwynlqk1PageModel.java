package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class Gwynlqk1PageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		year =year-1;
		String date=year+"-12"+"-31";
		this.getPageElement("tjtime").setValue(date);
		this.getPageElement("timetj").setValue(date);
		this.setNextEventName("initx");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initx")
	public int initx() throws RadowException{

		try {
			String groupid=this.getPageElement("subWinIdBussessId").getValue();
			if(StringUtil.isEmpty(groupid)){
				groupid="001.001";
			}
			String year=this.getPageElement("tjtime").getValue();
			this.getPageElement("timetj").setValue(year);
			this.getPageElement("groupid").setValue(groupid);
			CommQuery cq=new CommQuery();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("groupid", groupid);
			String sid = this.request.getSession().getId();
			map.put("cq", cq);
			map.put("num", "");
			map.put("a0279", "");
			map.put("a0219", "");
			map.put("year", year);
			map.put("a0160", " and a01.A0160='1' ");
			StringBuffer ss=new StringBuffer();
			map.put("a0221",   " and a01.a0221 in ('1A11',"//省部级正职
                    + "'1A12', "//省部级副职
                    + "'1A50',"//科员 
                    + "'1A60',"//办事员 
                    + "'1A98',"//试用期人员
                    + "'1A99')"//其他
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "");
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str1").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('1')");//分组
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//其乡科级副职
                    );
			map.put("a0219", " and a02.a0219='1' ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num 合计标志
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");//领导
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//乡科级副职
                    );
			map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num 合计标志
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str3").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('3')");//非领导
			
			
			map.put("a0221",   " and (a01.a0221 like '7%' or "//
					+ "  a01.a0221 like '3%' or "//法官
					+ "  a01.a0221 like '4%' or "//检察官等级
					+ " a01.a0221 like '2%' )"//深圳市行政执法类公务员
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "28");//num 合计标志
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str4").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('4')");//非领导
		
			
			map.put("a0221", " and a01.a0221 like '1B%' ");//专业技术类
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "29");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str5").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(5)");
			
			map.put("a0221", " and a01.a0221 like '1C%' ");//专业技术类
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "30");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str6").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(6)");
			
			
			map.put("a0221", " and (a01.a0221 like '5%' or a01.a0221 like '6%')");//专业技术类
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "31");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str7").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(7)");
		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	
		
	}
	
	
	public static String selectNl(Map<String,Object> map){
		StringBuffer sb=new StringBuffer();
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb.append(" "
					+ " SELECT  "
			       + " count(a.a0000) heji,"//合计
		            +(map.get("num").equals("")?" a.A0221, ":"")//当前职位层次
		            + " sum(CASE "
		            	+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char(add_months("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",-264),'yyyy-mm')),'yyyy-mm'))/12)<=20  THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			              + " END) xydy20, "
			        + " sum(CASE "
	            		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=21  THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			              + " END) dy21, "
			        + " sum(CASE "
		            		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=22  THEN "
				              + " 1 "
				             + " ELSE "
				              + " 0 "
				              + " END) dy22, "
		            + " sum(CASE "
		        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=23  THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			              + " END) dy23, "
			        + " sum(CASE "
		        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=24  THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			              + " END) dy24, "
			        + " sum(CASE "
			        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=25  THEN "
				              + " 1 "
				             + " ELSE "
				              + " 0 "
				              + " END) dy25, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=26  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy26, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=27  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy27, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=28  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy28, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=29  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy29, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=30  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy30, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=31  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy31, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=32  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy32, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=33  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy33, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=34  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy34, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=35  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy35, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=36  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy36, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=37  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy37, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=38  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy38, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=39  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy39, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=40  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy40, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=41  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy41, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=42  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy42, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=43  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy43 ");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append(" "
					+ " SELECT  "
			       + " count(a.a0000) heji,"//合计
		            +" a.A0221, "//当前职位层次
		            + " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT(DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 264 month),'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) <= 20 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) xydy20, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month,		 "	
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 21 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy21, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 			if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 22 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy22, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 		if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 23 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy23, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 24 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy24, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 25 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy25, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 26 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy26, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 27 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy27, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 28 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy28, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 29 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy29, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 30 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy30, "
					+ " sum( "
					+ " 	CASE "
					+ " 	WHEN floor( "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 31 THEN "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy31,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(   "
					+ " 		timestampdiff (     "
					+ " 			month,    "
					+ " 				if (    "
					+ " 					a.a0107='',   "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),    "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),      "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12   "
					+ " 	) = 32 THEN   "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy32,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(    "
					+ " 		timestampdiff (  "
					+ " 			month, "
					+ " 				if (    "
					+ " 					a.a0107='',  "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),  "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
					+ " 				),      "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12  "
					+ " 	) = 33 THEN "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy33,    "
					+ " sum(       "
					+ " 	CASE   "
					+ " 	WHEN floor( "
					+ " 		timestampdiff (   "
					+ " 			month,   "
					+ " 				if (  "
					+ " 					a.a0107='',    "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
					+ " 				),    "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12  "
					+ " 	) = 34 THEN   "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy34,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(  "
					+ " 		timestampdiff (    "
					+ " 			month, "
					+ " 				if (   "
					+ " 					a.a0107='',    "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),  "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)    "
					+ " 				),       "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12  "
					+ " 	) = 35 THEN  "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy35,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(    "
					+ " 		timestampdiff (   "
					+ " 			month,  "
					+ " 				if (  "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),  "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12   "
					+ " 	) = 36 THEN  "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy36,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(  "
					+ " 		timestampdiff (  "
					+ " 			month, "
					+ " 				if (   "
					+ " 					a.a0107='',   "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),   "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')  "
					+ " 		) / 12 "
					+ " 	) = 37 THEN   "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy37,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(  "
					+ " 		timestampdiff (  "
					+ " 			month, "
					+ " 				if (  "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')  "
					+ " 		) / 12   "
					+ " 	) = 38 THEN  "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy38,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(   "
					+ " 		timestampdiff (   "
					+ " 			month,  "
					+ " 				if (   "
					+ " 					a.a0107='',  "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12  "
					+ " 	) = 39 THEN   "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy39,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(  "
					+ " 		timestampdiff ( "
					+ " 			month, "
					+ " 				if (  "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),   "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12  "
					+ " 	) = 40 THEN  "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy40,    "
					+ " sum(       "
					+ " 	CASE   "
					+ " 	WHEN floor(   "
					+ " 		timestampdiff (  "
					+ " 			month,  "
					+ " 				if (  "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),  "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),  "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12 "
					+ " 	) = 41 THEN   "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy41,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(   "
					+ " 		timestampdiff (   "
					+ " 			month, "
					+ " 				if (  "
					+ " 					a.a0107='',  "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),  "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12 "
					+ " 	) = 42 THEN   "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy42,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(   "
					+ " 		timestampdiff (   "
					+ " 			month, "
					+ " 				if (   "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),  "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12  "
					+ " 	) = 43 THEN  "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy43   ");
		}
	
		return sb.toString();
	}
	
	
	public static List<HashMap<String, Object>> returnList(Map<String,Object> map) throws RadowException, AppException{
		CommQuery cq=(CommQuery)map.get("cq");
		StringBuffer sb=new StringBuffer(selectNl(map));
		sb.append(" from (SELECT a01.a0000,a01.a0107,a01.a0221 FROM A01 a01");
			if(map.get("a0219")!=null&&!"".equals(map.get("a0219"))){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0281 = 'true' "
						+ map.get("a0219")
						+ map.get("a0279")
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
                     + "  and a02.a0000=a01.a0000  "
                     + "  and a01.a0195 like '"+map.get("groupid")+"%' "
                     + map.get("a0221")
						);
			}else{
				sb.append(" where "
	                     + "  a01.a0163='1' "//人员管理状态   等
	                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
	                     + "  and a01.a0195 like '"+map.get("groupid")+"%' "
	                     + map.get("a0221")
							);
			}
		
		sb.append(map.get("a0160"));//
		sb.append(" ) a "+(map.get("num").equals("")?" group by A0221 ":"") //当前职务层次 分组
		          );
		//分组查询(包含学历)()
		System.out.println(sb.toString());
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		return list;
	}
}
