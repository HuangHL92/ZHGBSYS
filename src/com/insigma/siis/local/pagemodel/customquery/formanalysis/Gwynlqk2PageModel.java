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
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class Gwynlqk2PageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		year =year-1;
		String date=year+"-12"+"-31";
		this.getPageElement("tjtime").setValue(date);
		this.getPageElement("timetj").setValue(date);
		this.setNextEventName("initx");
		return 0;
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
			map.put("a0221",   " and a01.a0221 in ('1A11',"//ʡ������ְ
                    + "'1A12', "//ʡ������ְ
                    + "'1A50',"//��Ա 
                    + "'1A60',"//����Ա 
                    + "'1A98',"//��������Ա
                    + "'1A99')"//����
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "");
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str1").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('1')");//����
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//���ּ���ְ
                    + "'1A22', "//���ּ���ְ
                    + "'1A31',"//�ش�����ְ
                    + "'1A32',"//�ش�����ְ
                    + "'1A41',"//��Ƽ���ְ
                    + "'1A42')"//����Ƽ���ְ
                    );
			map.put("a0219", " and a02.a0219='1' ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num �ϼƱ�־
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");//�쵼
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//���ּ���ְ
                    + "'1A22', "//���ּ���ְ
                    + "'1A31',"//�ش�����ְ
                    + "'1A32',"//�ش�����ְ
                    + "'1A41',"//��Ƽ���ְ
                    + "'1A42')"//��Ƽ���ְ
                    );
			map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num �ϼƱ�־
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str3").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('3')");//���쵼
			
			
			map.put("a0221",   " and (a01.a0221 like '7%' or "//
					+ "  a01.a0221 like '3%' or "//����
					+ "  a01.a0221 like '4%' or "//���ٵȼ�
					+ " a01.a0221 like '2%' )"//����������ִ���๫��Ա
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "28");//num �ϼƱ�־
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str4").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('4')");//���쵼
		
			map.put("a0221", " and a01.a0221 like '1B%' ");//רҵ������
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "29");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str5").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(5)");
			
			map.put("a0221", " and a01.a0221 like '1C%' ");//רҵ������
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "30");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str6").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(6)");
			
			
			map.put("a0221", " and (a01.a0221 like '5%' or a01.a0221 like '6%')");//רҵ������
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "31");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str7").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(7)");
		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	
		
	}
	
	
	
	public static List<HashMap<String, Object>> returnList(Map<String,Object> map) throws RadowException, AppException{
		CommQuery cq=(CommQuery)map.get("cq");
		StringBuffer sb=new StringBuffer(selectN2(map));
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
                     + "  a01.a0163='1' "//��Ա����״̬   ��
                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                     + "  and a02.a0000=a01.a0000  "
                     + "  and a01.a0195 like '"+map.get("groupid")+"%' "
                     + map.get("a0221")
						);
			}else{
				sb.append(" where "
	                     + "  a01.a0163='1' "//��Ա����״̬   ��
	                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
	                     + "  and a01.a0195 like '"+map.get("groupid")+"%' "
	                     + map.get("a0221")
							);
			}
		
		sb.append(map.get("a0160"));//
		sb.append(" ) a "+(map.get("num").equals("")?" group by A0221 ":"") //��ǰְ���� ����
		          );
		//�����ѯ(����ѧ��)()
		System.out.println(sb.toString());
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		return list;
	}
	
	public static String selectN2(Map<String,Object> map){
		StringBuffer sb=new StringBuffer();
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb.append("  "
					+ " SELECT  "
					+ (map.get("num").equals("")?" a.A0221, ":"")
				       + " count(a.a0000) heji,"//�ϼ�
					+ " sum(floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)) nlzs,"
					+ " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=44  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy44, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=45  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy45, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=46  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy46, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=47  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy47, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=48  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy48, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=49  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy49, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=50  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy50, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=51  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy51, "
		              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=52  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy52, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=53  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy53, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=54  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy54, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=55  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy55, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=56  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy56, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=57  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy57, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=58  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy58, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=59  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy59, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=60  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy60, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=61  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy61, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=62  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy62, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=63  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy63, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=64  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy64, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)=65  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dy65, "
	              + " sum(CASE "
	        		+ " WHEN floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)>=66  THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		              + " END) dydy66, "
	              + " round( "
	              	+ " sum( " 
	                      + " floor( "
	                            + "  months_between( "+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",  to_date( nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm') ) , 'yyyy-mm' )  ) /12 "
	                            + " ) "
	                    + " )  /  sum("
	                         + " case when a.a0107 is not null then 1 else 0 end "
	                         + ") "
	                    + " ,2) pjnl, "//ƽ������
	               +" sum("
	                        + " case when a.a0107 is not null then 1 else 0 end "
	                        + ")  heji_zs ");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append(" "
					+ " SELECT "
					+ " a.a0221, "
				       + " count(a.a0000) heji,"//�ϼ�
				     +"  sum(floor( "
								+ " 		timestampdiff (   "
								+ " 			month,  "
								+ " 				if (  "
								+ " 					a.a0107='',  "
								+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),   "
								+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
								+ " 				),  "
								+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
								+ " 		) / 12  "
								+ " 	) ) nlzs,"
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor( "
					+ " 		timestampdiff (   "
					+ " 			month,  "
					+ " 				if (  "
					+ " 					a.a0107='',  "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),   "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),  "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 		) / 12  "
					+ " 	) = 44 THEN "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy44,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor( "
					+ " 		timestampdiff (   "
					+ " 			month,   "
					+ " 				if ( "
					+ " 					a.a0107='',  "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),  "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),  "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12  "
					+ " 	) = 45 THEN  "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy45,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(   "
					+ " 		timestampdiff (  "
					+ " 			month,   "
					+ " 				if (  "
					+ " 					a.a0107='',  "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),   "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),    "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')  "
					+ " 		) / 12  "
					+ " 	) = 46 THEN "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy46,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(           "
					+ " 		timestampdiff (   "
					+ " 			month,        "
					+ " 				if (     "
					+ " 					a.a0107='',     "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12   "
					+ " 	) = 47 THEN  "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy47,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(          "
					+ " 		timestampdiff (  "
					+ " 			month,       "
					+ " 				if (     "
					+ " 					a.a0107='',    "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12    "
					+ " 	) = 48 THEN   "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy48,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(           "
					+ " 		timestampdiff (   "
					+ " 			month,        "
					+ " 				if (      "
					+ " 					a.a0107='',     "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12  "
					+ " 	) = 49 THEN "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy49,    "
					+ " sum(       "
					+ " 	CASE   "
					+ " 	WHEN floor(          "
					+ " 		timestampdiff (  "
					+ " 			month,       "
					+ " 				if (     "
					+ " 					a.a0107='',      "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12    "
					+ " 	) = 50 THEN   "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy50,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(          "
					+ " 		timestampdiff (  "
					+ " 			month,       "
					+ " 				if (     "
					+ " 					a.a0107='',    "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12   "
					+ " 	) = 51 THEN  "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy51,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(          "
					+ " 		timestampdiff (  "
					+ " 			month,       "
					+ " 				if (     "
					+ " 					a.a0107='',      "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12  "
					+ " 	) = 52 THEN "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy52,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(                                          "
					+ " 		timestampdiff (                                  "
					+ " 			month,                                       "
					+ " 				if (                                     "
					+ " 					a.a0107='',                          "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12   "
					+ " 	) = 53 THEN  "
					+ " 		1                                                "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy53,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(         "
					+ " 		timestampdiff ( "
					+ " 			month,      "
					+ " 				if (    "
					+ " 					a.a0107='',    "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12    "
					+ " 	) = 54 THEN   "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy54,     "
					+ " sum(        "
					+ " 	CASE    "
					+ " 	WHEN floor(          "
					+ " 		timestampdiff (  "
					+ " 			month,       "
					+ " 				if (     "
					+ " 					a.a0107='',    "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12    "
					+ " 	) = 55 THEN   "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy55,    "
					+ " sum(       "
					+ " 	CASE        "
					+ " 	WHEN floor(     "
					+ " 		timestampdiff (     "
					+ " 			month,          "
					+ " 				if (        "
					+ " 					a.a0107='',                          "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12    "
					+ " 	) = 56 THEN   "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy56,    "
					+ " sum(       "
					+ " 	CASE   "
					+ " 	WHEN floor(           "
					+ " 		timestampdiff (   "
					+ " 			month,        "
					+ " 				if (      "
					+ " 					a.a0107='',                          "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12               "
					+ " 	) = 57 THEN              "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy57,    "
					+ " sum(       "
					+ " 	CASE   "
					+ " 	WHEN floor(           "
					+ " 		timestampdiff (   "
					+ " 			month,        "
					+ " 				if (      "
					+ " 					a.a0107='',                          "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')  "
					+ " 		) / 12   "
					+ " 	) = 58 THEN  "
					+ " 		1 "
					+ " 	ELSE  "
					+ " 		0 "
					+ " 	END   "
					+ " ) dy58,   "
					+ " sum(      "
					+ " 	CASE  "
					+ " 	WHEN floor(         "
					+ " 		timestampdiff ( "
					+ " 			month,      "
					+ " 				if (    "
					+ " 					a.a0107='',                          "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				),          "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')   "
					+ " 		) / 12              "
					+ " 	) = 59 THEN             "
					+ " 		1  "
					+ " 	ELSE   "
					+ " 		0  "
					+ " 	END    "
					+ " ) dy59,    "
					+ " sum(       "
					+ " 	CASE   "
					+ " 	WHEN floor(           "
					+ " 		timestampdiff (   "
					+ " 			month,        "
					+ " 				if (      "
					+ " 					a.a0107='',                          "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),     "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
					+ " 				),           "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d')    "
					+ " 		) / 12               "
					+ " 	) = 60 THEN              "
					+ " 		1   "
					+ " 	ELSE    "
					+ " 		0   "
					+ " 	END     "
					+ " ) dy60,     "
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
					+ " 	) = 61 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy61, "
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
					+ " 	) = 62 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy62, "
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
					+ " 	) = 63 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy63, "
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
					+ " 	) = 64 THEN "
					+ " 		1 "
					+ " 	ELSE " 
					+ " 		0 "
					+ " 	END "
					+ " ) dy64, "
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
					+ " 	) = 65 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dy65, "
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
					+ " 	) >= 66 THEN "
					+ " 		1 "
					+ " 	ELSE "
					+ " 		0 "
					+ " 	END "
					+ " ) dydy66, "
					+ " round( "
					+ " 	sum( "
					+ " 		floor( "
					+ " 			timestampdiff ( "
					+ " 				month, "
					+ " 					if ( "
					+ " 					a.a0107='', "
					+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
					+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
					+ " 				), "
					+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
					+ " 			) / 12 "
					+ " 		) "
					+ " 	) / sum( "
					+ " 		CASE "
					+ " 		WHEN a. a0107 IS NOT NULL THEN "
					+ " 			1 "
					+ " 		ELSE "
					+ " 			0 "
					+ " 		END "
					+ " 	) "
					+ " ,2) pjnl, "//ƽ������
	               +" sum("
	                        + " case when a.a0107 is not null then 1 else 0 end "
	                        + ")  heji_zs ");
		}
	
		return sb.toString();
	}
}
