package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.privilege.util.DateUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class formanalysis_ssygwyPageModel extends PageModel{

	public formanalysis_ssygwyPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		year =year-1;
		String date=year+"-12"+"-31";
		this.getPageElement("tjtime").setValue(date);
		this.getPageElement("timetj").setValue(date);
		this.setNextEventName("init");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("init")
	public int init() throws RadowException{
		try {
			String groupid=this.getPageElement("subWinIdBussessId2").getValue();
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
			map.put("sid", sid);
			map.put("cq", cq);
			map.put("num", "");
			map.put("a0160", " and a01.A0160='1' ");
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("year", year);
			String userid=SysUtil.getCacheCurrentUser().getId();
			map.put("userid", userid);
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			map.put("username", username);
			List<HashMap<String, Object>> list_user=cq.getListBySQL(" select rate,empid from smt_user t where t.userid = '"+userid+"' ");
			String rylb="";
			if(list_user!=null&&list_user.size()>0){
				String temp=(String)list_user.get(0).get("rate");//��Ա��� ���������
				if(temp!=null&&temp.length()>0){
					rylb=temp;
				}
				temp=(String)list_user.get(0).get("empid");//��Ա��� ����ά����
				if(temp!=null&&temp.length()>0){
					rylb=rylb+","+temp;
				}
			}
			map.put("rylb", rylb);
			String sql=(String) this.request.getSession().getAttribute("ry_tj_zy");//��Ա��Ϣ��ѯsql
			map.put("sql", sql);
			map.put("a0221",   " and a01.a0221 in ('1A11',"//ʡ������ְ
                    + "'1A12', "//ʡ������ְ
                    + "'1A50',"//��Ա 
                    + "'1A60',"//����Ա 
                    + "'1A98',"//��������Ա
                    + "'1A99')"//����
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			StringBuffer ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str1").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('1')");//
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//���ּ���ְ
                    + "'1A22', "//���ּ���ְ
                    + "'1A31',"//�ش�����ְ
                    + "'1A32',"//�ش�����ְ
                    + "'1A41',"//��Ƽ���ְ
                    + "'1A42')"//����Ƽ���ְ
                    );
			map.put("a0279", " and a02.a0279='1' ");
			map.put("a0219", " and a02.a0219='1' ");
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");//��ְ
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//���ּ���ְ
                    + "'1A22', "//���ּ���ְ
                    + "'1A31',"//�ش�����ְ
                    + "'1A32',"//�ش�����ְ
                    + "'1A41',"//��Ƽ���ְ
                    + "'1A42')"//����Ƽ���ְ
                    );
			map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
			map.put("a0279", " and a02.a0279='1' ");
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str3").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('3')");//��ְ
			
//			map.put("a0221",   " and a01.a0221 like '1A%' "//����Ա
//                    );
//			map.put("a0219", "");
//			map.put("a0279", "");
//			map.put("num", "4");
//			ss=new GbjbqkComm().toJson(returnList(map));
//			this.getPageElement("jsonString_str4").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func('4')");//�ϼ�
			
//			map.put("a0221",   " and (a01.a0221 like '1A%' "//����Ա
//					+ " or a01.a0221 like '2%' "//���񾯲쾯Աְ������ 
//					+ " or a01.a0221 like '3%' "//����
//					+ " or a01.a0221 like '7%' ) "//����������ִ���๫��Ա
//                    );
//			map.put("a0219", "");
//			map.put("a0279", "");
//			map.put("num", "5");
//			ss=new GbjbqkComm().toJson(returnList(map));
//			this.getPageElement("jsonString_str5").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func(5)");//�ϼ�
			
			
			map.put("a0221", " and a01.a0221 like '1B%' ");//רҵ������
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "4");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str4").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(4)");
			
			
			map.put("a0221", " and a01.a0221 like '1C%' ");//רҵ������
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "5");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str5").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(5)");
			
			map.put("a0221", " and (a01.a0221 like '5%' or a01.a0221 like '6%')");//רҵ������
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "7");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str7").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(7)");
			
			map.put("a0221",   " and (a01.a0221 like '7%' or "//
					+ "  a01.a0221 like '3%' or "//����
					+ "  a01.a0221 like '4%' or "//���ٵȼ�
					+ " a01.a0221 like '2%' )"//����������ִ���๫��Ա
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "6");//�ϼ�
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str6").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(6)");//2��ϼ�
		
		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public List<HashMap<String, Object>> returnList(Map<String,Object> map) throws RadowException, AppException{
		CommQuery cq=(CommQuery)map.get("cq");
		StringBuffer sb=new StringBuffer();
		returnGbjbqkSql(sb,map);//ƴ��select 
		returnZb(sb);
		/*if(map.get("sql")==null||"".equals(map.get("sql"))||((String)map.get("sql")).length()==0){*///�б�δ��ѯ����
			/*if("system".equals(map.get("username"))){*/
				/*sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "
						+ map.get("a0219")
						+ map.get("a0279")
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//��Ա����״̬   ��
                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                     + "  and a02.a0000=a01.a0000  "
                     + map.get("a0221")
						);*/
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
				
			/*}else{
				sb.append(" ,(select  a02.a0000 "
						+ " from a02, competence_userdept cu "
						+ " where "
						+ " a02.a0255 = '1' "
						+ map.get("a0219")
						+ map.get("a0279")
                     + " and a02.A0201B = cu.b0111 "
                     + " and cu.userid = '"+map.get("userid")+"' "
                     + " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//��Ա����״̬   ��
                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
                     + "  and a02.a0000=a01.a0000  "
                     + map.get("a0221")
						);*/
			/*}*/
		/*}else{//�б��ѯ����
			String [] arrPersonnelList=((String)map.get("sql")).split("@@");
			if(arrPersonnelList.length==2){
			}else{
				throw new RadowException("�����쳣!");
			}
			String where="";
			if("tempForCount".equals(arrPersonnelList[0])){//��ʱ��
				String sid = (String)map.get("sid");
				if("".equals(map.get("a0219"))){
					where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
					sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
							+ map.get("a0221")
							+ " ");
				}else{
					where ="(select aa.a0000,bb.a0219,bb.a0279 from ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) aa,a02 bb where aa.a0000=bb.a0000)";
					sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
							+ map.get("a0221")
							+ map.get("a0219")
							+ map.get("a0279")
							+ " ");
				}
			}else if("conditionForCount".equals(arrPersonnelList[0])){//��ѯ����
				where=((String)map.get("sql")).substring(((String)map.get("sql")).indexOf("from A01 a01"), ((String)map.get("sql")).length());
				if("".equals(map.get("a0219"))){
					
				}else{
					where=where.substring(0, where.indexOf("from a02")+14)+ " "+ ((String)map.get("a0219")).substring(4,((String)map.get("a0219")).length())+ map.get("a0279")+ " and "+where.substring(where.indexOf("from a02")+14, where.length());
				}
				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000"
						+ map.get("a0221")
						+ " ";
				sb.append(where);
			}else{
				throw new RadowException("��Ա��Ϣ���������쳣!");
			}
			
		}*/
		/*if(((String)map.get("rylb"))!=null&&((String)map.get("rylb")).indexOf("\'")!=-1){
	    	sb.append(" and a01.A0165 not in ( "+((String)map.get("rylb"))+") ");
	    }*/
		sb.append(map.get("a0160"));//
		sb.append(" ) a "+(map.get("num").equals("")?" group by A0221 ":"") //��ǰְ���� ����
		          );
		//�����ѯ(����ѧ��)()
		System.out.println(sb.toString());
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
//		StringBuffer sb1=new StringBuffer();
//		returnGbjbqkSql2(sb1,map);//ƴ��select 
//		returnGbjbqkSql2sub(sb1);
//		if(((String)map.get("sql"))==null||"".equals(((String)map.get("sql")))||((String)map.get("sql")).length()==0){//�б�δ��ѯ����
//			if("system".equals(((String)map.get("username")))){
//				sb1.append(" ,(select  a02.a0000 "
//						+ " from a02 "
//						+ " where "
//						+ " a02.a0255 = '1' "
//						+ map.get("a0219")
//						+ map.get("a0279")
//						+ " group by a02.a0000"
//                     + " ) a02 where "
//                     + "  a01.a0163='1' "//��Ա����״̬   ��
//                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
//                     + "  and a02.a0000=a01.a0000  "
//                     + map.get("a0221")
//						);
//			}else{
//				sb1.append(" ,(select  a02.a0000 "
//						+ " from a02, competence_userdept cu "
//						+ " where "
//						+ " a02.a0255 = '1' "
//						+ map.get("a0219")
//						+ map.get("a0279")
//                     + " and a02.A0201B = cu.b0111 "
//                     + " and cu.userid = '"+((String)map.get("userid"))+"' "
//                     + " group by a02.a0000"
//                     + " ) a02 where "
//                     + "  a01.a0163='1' "//��Ա����״̬   ��
//                     + " and a01.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
//                     + "  and a02.a0000=a01.a0000  "
//                     + map.get("a0221")
//						);
//			}
//		}else{//�б��ѯ����
//			String [] arrPersonnelList=((String)map.get("sql")).split("@@");
//			if(arrPersonnelList.length==2){
//			}else{
//				throw new RadowException("�����쳣!");
//			}
//			String where="";
//			if("tempForCount".equals(arrPersonnelList[0])){//��ʱ��
//				String sid = (String)map.get("sid");
//				if("".equals(map.get("a0219"))){
//					where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
//					sb1.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
//							+ map.get("a0221")
//							+ " ");
//				}else{
//					where ="(select aa.a0000,bb.a0219,bb.a0279 from ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) aa,a02 bb where aa.a0000=bb.a0000)";
//					sb1.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
//							+ map.get("a0221")
//							+ map.get("a0219")
//							+ map.get("a0279")
//							+ " ");
//				}
//				
//			}else if("conditionForCount".equals(arrPersonnelList[0])){//��ѯ����
//				where=((String)map.get("sql")).substring(((String)map.get("sql")).indexOf("from A01 a01"), ((String)map.get("sql")).length());
//				if("".equals(map.get("a0219"))){
//					
//				}else{
//					where=where.substring(0, where.indexOf("from a02")+14)+ " "+ ((String)map.get("a0219")).substring(4,((String)map.get("a0219")).length())+ map.get("a0279")+ " and "+where.substring(where.indexOf("from a02")+14, where.length());
//				}
//				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000"
//						+ map.get("a0221")
//						+ " ";
//				sb1.append(where);
//			}else{
//				throw new RadowException("��Ա��Ϣ���������쳣!");
//			}
//			
//		}
//		if(((String)map.get("rylb"))!=null&&((String)map.get("rylb")).indexOf("\'")!=-1){
//	    	sb1.append(" and a01.A0165 not in ( "+((String)map.get("rylb"))+") ");
//	    }
//		sb1.append(map.get("a0160"));//
//		sb1.append(" ) a "+(map.get("num").equals("")?" group by A0221 ":"")//��ǰְ���� ����
//		          );
//		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		//list=combine(list,list1);
		return list;
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> list1�е����ݺϲ���list��
	 * @param list
	 * @param list1
	 * @return
	 */
//	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
//		if(list==null||list1==null||list1.size()==0||list.size()==0){
//			return list;
//		}
//		String temp="";
//		if(list.size()<=list1.size()){
//			for(int i=0;i<list.size();i++){
//				if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
//					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
//					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
//					continue;
//				}
//				temp=(String)list.get(i).get("a0221");
//				if(temp.equals((String)list1.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
//					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
//					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
//				}else{
//					combine_jy(list,list1,temp,i);
//				}
//			}
//		}else{
//			for(int i=0;i<list1.size();i++){
//				if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
//					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
//					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
//					continue;
//				}
//				
//				temp=(String)list1.get(i).get("a0221");
//				if(temp.equals((String)list.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
//					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
//					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
//				}else{
//					combine_jy_f(list,list1,temp,i);
//				}
//			}
//		}
//		
//		return list;
//	}
	
//	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
//		for(int j=0;j<list1.size();j++){
//			if(temp.equals((String)list1.get(j).get("a0221"))){
//				list.get(i).put("bs", list1.get(j).get("bs"));//��ʿ
//				list.get(i).put("ss", list1.get(j).get("ss"));//˶ʿ
//				list.get(i).put("xs", list1.get(j).get("xs"));//ѧʿ
//			}
//		}
//	}
	
//	public void combine_jy_f(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
//		for(int j=0;j<list.size();j++){
//			if(temp.equals((String)list.get(j).get("a0221"))){
//				list.get(j).put("bs", list1.get(i).get("bs"));//��ʿ
//				list.get(j).put("ss", list1.get(i).get("ss"));//˶ʿ
//				list.get(j).put("xs", list1.get(i).get("xs"));//ѧʿ
//			}
//		}
//	}
	
//	public int returnGbjbqkSql2(StringBuffer sb1,Map<String,Object> map) throws RadowException{
//		if(DBType.ORACLE==DBUtil.getDBType()){
//			sb1.append(  " select  "
//	                + (map.get("num").equals("")?" a.A0221, ":"")
//	                +" SUM(CASE "
//			                +" WHEN nvl(a.A0901B,0) like '2%' or nvl(a.A0901B,0)=1  THEN "
//			                 +" 1 "
//			                +" ELSE "
//			                 +" 0 "
//			              +" END) bs, "//--  ��ʿ
//			       +" SUM(CASE "
//			             +" WHEN nvl(a.A0901B,0) like '3%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) ss, "//--˶ʿ
//			       +" SUM(CASE "
//			             +" WHEN nvl(a.A0901B,0) like '4%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) xs "//--ѧʿ
//	                +" FROM"
//	                + " (SELECT "
//	                + " a01.a0221,"
//					  + " a08.A0901B ");//ѧλ����   ��
//		}else if(DBType.MYSQL==DBUtil.getDBType()){
//			sb1.append(  " select  "
//	                + (map.get("num").equals("")?" a.A0221, ":"")
//	                +" SUM(CASE "
//			                +" WHEN if(a.A0901B='',0,a.A0901B) like '2%' or if(a.A0901B='',0,a.A0901B)=1  THEN "
//			                 +" 1 "
//			                +" ELSE "
//			                 +" 0 "
//			              +" END) bs, "//--  ��ʿ
//			       +" SUM(CASE "
//			             +" WHEN if(a.A0901B='',0,a.A0901B) like '3%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) ss, "//--˶ʿ
//			       +" SUM(CASE "
//			             +" WHEN if(a.A0901B='',0,a.A0901B) like '4%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) xs "//--ѧʿ
//	                +" FROM"
//	                + " (SELECT "
//	                + " a01.a0221,"
//					  + " a08.A0901B ");//ѧλ����   ��
//		}else{
//			throw new RadowException("����Դ��������ϵϵͳ����Ա���ԭ��!");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
//	public int returnGbjbqkSql2sub(StringBuffer sb1) throws RadowException{
//		 sb1.append( " from A01 a01 left join ");
//		 
//		 if(DBType.ORACLE==DBUtil.getDBType()){
//		    	 sb1.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
//		 }else if(DBType.MYSQL==DBUtil.getDBType()){
//		    	 sb1.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' group by a0000 ) a08 ");
//		 }
//        sb1.append(" on a01.a0000=a08.a0000 "//
//       + "  "//��Աͳһ��ʶ�� ����
//	         );
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	public int returnZb(StringBuffer sb){
		sb.append(" FROM (SELECT a01.a0000, "//��Աͳһ��ʶ��
				  + " a01.a0104, "//�Ա�
				  + " a01.a0117, "//����
				  + " a01.a0141, "//������ò
				  + " a01.a0221, "//��ǰְ����
				  + " a08.a0801b, "//ѧ������   ��
				  + " a08_2.a0901b, "//ѧλ����   ��
				  + " a01.a0107, "//��������
				  + " a01.a0288, "//����ְ����ʱ��   ��
				  + " a01.a0197 "//�Ƿ�����������ϻ��㹤������
	          +" FROM A01 a01 "
	          + " left join  ");//��Ա������Ϣ��
		if(DBType.ORACLE==DBUtil.getDBType()){
		    sb.append( " (select * from  ( select A0801B,a0000,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
		    sb.append( " ( select A0801B,a0000 from a08 where A0834='1' group by a0000 ) a08 ");
		}
	      sb.append(" on a01.a0000=a08.a0000 "
	      + "  ");//��Աͳһ��ʶ�� ���� a08 ��¼����Ϊ��
	      

			 if(DBType.ORACLE==DBUtil.getDBType()){
			    	 sb.append( "  left join   (select * from  ( select a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08_2 ");
			 }else if(DBType.MYSQL==DBUtil.getDBType()){
			    	 sb.append( "  left join   ( select a0000,A0901B from a08 where A0835='1' group by a0000 ) a08_2 ");
			 }
			 sb.append(" on a01.a0000=a08_2.a0000 "//
				       + "  "//��Աͳһ��ʶ�� ����
					         );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int returnGbjbqkSql(StringBuffer sb,Map<String,Object> map){
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb.append(" SELECT  "
					+ "count(a.a0000) heji,"
					+ " SUM(CASE "
		             +" WHEN nvl(a.a0104,0) = 2 THEN "
		             +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) nv, "//�Ա� 2 Ů
				       +" SUM(CASE "
			             +" WHEN nvl(a.a0117,0) = 01 THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) sm, "//��������  !=01
			       +" SUM(CASE "
			             +" WHEN nvl(a.a0141,0) in ('01','02') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zd, "//�й���Ա
	            +(map.get("num").equals("")?" a.A0221, ":"")//��ǰְλ���
	             +" SUM(CASE "
			                +" WHEN nvl(a.A0901B,0) like '2%' or nvl(a.A0901B,0)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  ��ʿ
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--˶ʿ
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs, "//--ѧʿ
	            + " SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '1%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) yjs,"//�о���
		        + "  SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '2%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) dxbk,"//��ѧ����
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) like '3%' THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) dxzz,"//��ѧר��
		        +" SUM(CASE "
		             +" WHEN (nvl(a.A0801B,0) like '4%' or nvl(a.A0801B,0) in('61','71','81','91') )THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zz,"//��ר������
           +" sum( case when add_months(to_date((nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-25),'yyyy-mm-dd') "
           		+" ) ),'yyyy-mm-dd'),24)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end ) zhccxy2, "//����2��
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-37),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-23),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),24)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy3, "//2������3��
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-49),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),48)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-35),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy4, "//3������4��
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-61),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-47),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),48)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy5, "//4������5��
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-73),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),72)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy6, "//5������6��  
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-85),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),84)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),72)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy7, "//6������7��
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-97),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),96)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),84)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy8, "//7������8��
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-145),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),144)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),96)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy12, "//8������12��
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-181),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),180)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),144)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy15, "//12������15��
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-170),'yyyy-mm-dd') "
          	   +" ),'yyyy-mm-dd'),180)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end ) zhccxy16 "//15�꼰����
          	   );
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append("SELECT  "
					+ " count(a.a0000) heji,"
					+ "	SUM(     "
					+ "		CASE "
					+ "		WHEN a.a0104 = 2 THEN  "
					+ "			1 " 
					+ "		ELSE  " 
					+ "			0 " 
					+ "		END   " 
					+ "	) nv,     " //Ů
					+ "	SUM(      " 
					+ "		CASE  " 
					+ "		WHEN a.a0117 = 01 THEN "
					+ "			0 "
					+ "		ELSE  "
					+ "			1 "
					+ "		END "
					+ "	) sm,   "//��
					+ "	SUM(    "
					+ "		CASE  "
					+ "		WHEN a.a0141 in ('01','02') THEN "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END   "
					+ "	) zd,     "//��Ա
					+ (map.get("num").equals("")?" a.A0221, ":"")
					+" SUM(CASE "
			                +" WHEN if(a.A0901B='',0,a.A0901B) like '2%' or if(a.A0901B='',0,a.A0901B)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  ��ʿ
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--˶ʿ
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs, "//--ѧʿ
					+ "	SUM(     "
					+ "		CASE "
					+ "		WHEN a.A0801B LIKE '1%' THEN   "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END  "
					+ "	) yjs,   "//�о���
					+ "	SUM(     "
					+ "		CASE  "
					+ "		WHEN a.A0801B LIKE '2%' THEN    "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END  "
					+ "	) dxbk,  "//��ѧ����
					+ "	SUM(     "
					+ "		CASE  "
					+ "		WHEN a.A0801B LIKE '3%' THEN    "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END "
					+ "	) dxzz, "//��ѧר��
					+ "	SUM(    "
					+ "		CASE  "
					+ "		WHEN (a.A0801B LIKE '4%' or a.A0801B IN ('61', '71', '81', '91') ) THEN    "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END "
					+ "	) zz,   "//��ר������
					+ "sum(           "
					+ "		CASE      "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 25 month) ,  "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 24 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN   "
					+ "			1  "
					+ "		ELSE   "
					+ "			0  "
					+ "		END    "
					+ "	) zhccxy2, "//����2��
					+ "	sum(       "
					+ "		CASE   "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,       "
					+ "						DATE_FORMAT(    "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 37 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 36 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 23 month) ,  "
					+ "							'%Y%m%d'  "
					+ "						),            "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
					+ "					),'%Y%m%d'),INTERVAL 24 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN  "
					+ "			1  "
					+ "		ELSE   "
					+ "			0  "
					+ "		END    "
					+ "	) zhccxy3, "//2��������3��
					+ "	sum(       "
					+ "		CASE   "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,       "
					+ "						DATE_FORMAT(    "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 61 month) ,  "
					+ "							'%Y%m%d'  "
					+ "						),            "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
					+ "					),'%Y%m%d'),INTERVAL 48 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 35 month) ,  "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 36 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN  "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END   "
					+ "	) zhccxy4,"//3��������4��
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 61 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 60 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 47 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 48 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy5, "//4��������5��
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 73 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 72 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 60 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy6, "//5��������6��
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 85 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 84 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 72 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy7, "//6��������7��
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 97 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 96 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 84 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy8, "//7��������8��
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 145 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 144 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 96 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy12, "//8��������12��
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 181 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 180 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 144 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy15, "//12��������15��
					+ "	sum(        "
					+ "		CASE    "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,       "
					+ "						DATE_FORMAT(    "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 179 month) ,"
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 180 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1  "
					+ "		ELSE   "
					+ "			0  "
					+ "		END    "
					+ "	) zhccxy16 "//15�꼰����
					);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
