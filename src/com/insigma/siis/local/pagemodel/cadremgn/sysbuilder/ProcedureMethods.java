package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class ProcedureMethods {
	/**
	 * 1������Ա��ʵ�����䡣���ɹ��򣺵�ǰ��������Ա�ĳ������ڵĲ�ֵ���������º��մ��ڵ�ǰ�º��գ���ǰ��Ĳ�ֵ��1��
	 */
	public void GenAggrAge(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		String year=date.substring(0, 4);
		String month=date.substring(5, 7);
		String day=date.substring(8);
		String sql=" update "+tablename+" a set a."+codename+"="
			+ "(select to_number("+year+") - to_number(substr(b.A0107, 0, 4)) -"
			+"       (case"
			+"    	          when to_number("+month+") - to_number(substr(b.A0107, 5, 2)) > 0 then"
			+"    	           0"
			+"    	          when to_number("+month+") - to_number(substr(b.A0107, 5, 2)) = 0 then"
			+"    	           (case"
			+"    	             when to_number("+day+") - to_number(substr(b.A0107, 7)) >= 0 then"
			+"    	              0"
			+"    	             else"
			+"    	              1"
			+"    	           END)"
			+"    	          else"
			+"    	           1"
			+"    	        END) as personAge"
			+"    	  from A01 b where a.A0000=b.A0000) "
			+ " where a.A0000 in (select A0000 from A01)";
		stmt.executeQuery(sql);
		stmt.close();
	}
	
	/**
	 * 2������Ա�Ľ�����Ϣ�����ɹ���ȡA14.A1407(������Ϣ��.������׼����)  A14.A1404A(������Ϣ��.��������)��A14.A1421(������Ϣ��.����˵��)������ID�ֶ�����
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GenAwardFine(String tablename,String codename) throws AppException, SQLException{
		 //A1407+"  "+A1411A+"����"+A1401Q(תZB19)
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select (substr(a.A1407,0,4)||'.'||substr(a.A1407,5,2)||'  '||a.a1411a||'����'||b.code_name) a14a from "
					+ " A14 a,(select * from code_value where code_type = 'ZB19') b"
					+ " where A0000='"+a.get("a0000")+"' and a.a1401q = b.code_value(+)"
					+ " order by to_number(a.A1407) desc";
				List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
				if(list!=null&&list.size()>0){
					String message="";
					for(int i=0;i<list.size();i++){
						String flag="hhf";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("a14a"));
					}
					if("".equals(message)){
						message="��";
					}
					sql=" update "+tablename+" set "+codename+"=replace('"+message+"','hhf',CHR(13)) where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
		
	}
	
	/**
	 * 3����Ϊ��Ա�������뵳����(yyyy-mm)����ͬʱΪ�������ɣ�ͬʱ�����������ɵ����ƣ�     ����Ϊ�������ɣ��������������ɵ����ƣ����Ƕ���������ɣ����ֶ�����������ɵ����ơ�
	 * @throws SQLException 
	 * @throws AppException 
	 */
	public void GenInPartyDate(String tablename,String codename) throws AppException, SQLException{
		//A01:a0141������ò,a0144�뵳ʱ��,a3921�ڶ�����,a3927��������
		CommInertA01(tablename);
		String sql=" update "+tablename+" a set a."+codename+"="
				+ "(select codename from "
				+ "(select a.A0000,"
				+ "   (case"
			    + "     when a.a0141 is null then"
			    + "      ''"
			    + "     when to_number(a.a0141) < 03 then"
			    + "      (substr(a.a0144, 0, 4) || '.' || substr(a.a0144, 5, 2) ||"
			    + "      decode(a.a3921, null, '', CHR(13) || c.code_name3))"
			    + "     else"
			    + "      b.code_name3 || decode(a.a3921, null, '', CHR(13) || c.code_name3)"
			    + "   end) codename"
				+ "	  from A01 a,"
				+ "	       (select * from code_value where code_type = 'GB4762') b,"
				+ "	       (select * from code_value where code_type = 'GB4762') c"
				+ "	 where a.a0141 = b.code_value(+)"
				+ "	   and a.a3921 = c.code_value(+)) b  where a.A0000=b.A0000)"
				+ " where a.A0000 in (select A0000 from A01)";
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		stmt.executeQuery(sql);
		stmt.close();				
	}
	
	
	/**
	 * 4����Ϊ��Ա�������뵳����(yyyy-mm)����ͬʱΪ�������ɣ�ͬʱ�����������ɵ����ƣ�     ����Ϊ�������ɣ��������������ɵ����ƣ����Ƕ���������ɣ����ֶ�����������ɵ����ơ�
	 * @throws SQLException 
	 * @throws AppException 
	 */
	public void GenInPartyDate_short(String tablename,String codename) throws AppException, SQLException{
		//A01:a0141������ò,a0144�뵳ʱ��,a3921�ڶ�����,a3927��������
		CommInertA01(tablename);
		String sql=" update "+tablename+" a set a."+codename+"="
				+ "(select codename from "
				+ "(select a.A0000,"
				+ "   (case"
			    + "     when a.a0141 is null then"
			    + "      ''"
			    + "     when to_number(a.a0141) < 03 then"
			    + "      (substr(a.a0144, 3, 2) || '.' ||substr(a.a0144, 5, 2) ||"
			    + "      decode(a.a3921, null, '', CHR(13) || c.code_name3))"
			    + "     else"
			    + "      b.code_name3 || decode(a.a3921, null, '', CHR(13) || c.code_name3)"
			    + "   end) codename"
				+ "	  from A01 a,"
				+ "	       (select * from code_value where code_type = 'GB4762') b,"
				+ "	       (select * from code_value where code_type = 'GB4762') c"
				+ "	 where a.a0141 = b.code_value(+)"
				+ "	   and a.a3921 = c.code_value(+)) b  where a.A0000=b.A0000)"
				+ " where a.A0000 in (select A0000 from A01)";
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		stmt.executeQuery(sql);
		stmt.close();				
	}
	
	/**
	 * 5������Ա��ְ��Ķ��¼��Ϣ�������ɹ������ɵ��������Ա���Ȱ��յ�λ����                           �����˵Ķ�ְ���ն�ְ��������ų���,��Ա�ڶ����λ��ְ,�Ա���λ��ְ�����ȳ���,������λְ���ն�ְ������˳�����.
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GenMultiDuty() throws AppException, SQLException{
		String sql="delete from A49Q where 1=1";
		HBSession hbsessdel = HBUtil.getHBSession();	
		Statement  stmtdel = hbsessdel.connection().createStatement();
		stmtdel.executeQuery(sql);
		stmtdel.close();
		sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.* from (select rownum num,a.* from A02 a where a.A0000='"+a.get("a0000")+"' and a.a0255='1' order by to_number(a.a0223)) a where a.num=1";
				List<HashMap<String,Object>> listb01=cqbs.getListBySQL(sql);
				if(listb01!=null&&listb01.size()>0){
					//��ǰ��λ
					sql="select b.code_name3 dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255='1' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message="";
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("dutyname"));
					}
					sql="insert into A49Q(ID,A0000,A4999,A4901,A4902,A4903,A4904,A4905) values(1,'"+a.get("a0000")+"',1,"
						+ "'"+listb01.get(0).get("a0201b")+"',0,'"+message+"','"+listb01.get(0).get("a0225")+"','"+listb01.get(0).get("a0243")+"')";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
					
					//��ְ��λ
					sql="insert into A49Q(ID,A0000,A4999,A4901,A4902,A4903,A4904,A4905)"
						+ "  select 1,'"+a.get("a0000")+"',1,a.a0201b,0,b.code_name3||'(��)',a.a0225,a.a0243 from "
						+ "   A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ "    where A0000='"+a.get("a0000")+"' and a.a0255='1' "
						+ "    and a.a0215a = b.code_value(+) and a.A0201B<>'"+listb01.get(0).get("a0201b")+"'"
						+ "    order by to_number(a.a0223) ";	
					Statement  stmtJz = hbsess.connection().createStatement();
					stmtJz.executeQuery(sql);
					stmtJz.close();
				}else{
					sql="insert into A49Q(ID,A0000,A4999,A4901,A4902,A4903,A4904,A4905) values(0,'"+a.get("a0000")+"',1,'',0,'','',null)";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
		
	}
	
	/**
	 * 6��ʾ����ְ�Ʒ����е����ְ�ơ����á�����
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GenMultiTopZC(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select code_dual from "
					+ " (select code_dual,"
					+ "  row_number() over(partition by substr(a.a0601,0,2) order by"
					+ " a.a0601) row_number"
					+ "  from "
					+ " (select a.*, b.code_name code_dual"
					+ "  from A06 a, (select * from code_value where code_type = 'GB8561') b"
					+ "  where a.a0601 = b.code_value(+) and a.A0000='"+a.get("a0000")+"') a) a"
					+ " where a.row_number=1";
				List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
				if(list!=null&&list.size()>0){
					String message="";
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("code_dual"));
					}
					sql=" update "+tablename+" set "+codename+"='"+message+"'  where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	/**
	 * 7������Ա��ְ��ĵ���Ϣ�������ɹ������ɵ��������Ա���Ȱ��յ�λ���򣬵����˵Ķ�ְ���ն�ְ��������ų���
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GenSingleDuty() throws AppException, SQLException{
		String sql="delete from A48Q where 1=1";
		HBSession hbsessdel = HBUtil.getHBSession();	
		Statement  stmtdel = hbsessdel.connection().createStatement();
		stmtdel.executeQuery(sql);
		stmtdel.close();
		sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.* from (select rownum num,a.* from A02 a where a.A0000='"+a.get("a0000")+"' and a.a0255>'0' order by to_number(a.a0223)) a where a.num=1";
				List<HashMap<String,Object>> listb01=cqbs.getListBySQL(sql);
				if(listb01!=null&&listb01.size()>0){
					sql="select b0101,b0111 from b01 where  b0111='"+listb01.get(0).get("a0201b")+"'";
					List<HashMap<String,Object>> listbname=cqbs.getListBySQL(sql);
					String bname="";
					if(listbname!=null&&listbname.size()>0){
						bname=entryStr(listbname.get(0).get("b0101"));
					}
					//��ǰ��λ
					sql="select b.code_name3 dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("dutyname"));
					}
					sql="insert into A48Q(A0000,A4801,A4803,A4805,A4807) values('"+a.get("a0000")+"',"
						+ "'"+listb01.get(0).get("a0201b")+"','"+message+"',0,'"+listb01.get(0).get("a0225")+"')";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
					
				}else{
					sql="insert into A48Q(A0000,A4801,A4803,A4805,A4807) values('"+a.get("a0000")+"','','',0,'')";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	/**
	 * 8������Ա�Ľ�����Ϣ��  ���ɹ�����ϵͳ����Ϊ׼��ȡǰ�������ȿ��˽�������������У�֮���á������Ÿ�����  �����ڵ���ȿ��˽����ͬ�����Ժϲ����硰1997��1998��ȿ���Ϊ��ְ��1999��ȿ���Ϊ���㡱��
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GenWZAnnualEvaluation(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
		String date=df.format(new Date());
		date=String.valueOf((Integer.valueOf(date)-3));
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.a1521,b.code_name from "
					+ " A15 a,(select * from code_value where code_type = 'ZB18') b"
					+ " where A0000='"+a.get("a0000")+"' and a.a1517 = b.code_value(+) and to_number(a.a1521)>=to_number("+date+")"
					+ " order by to_number(a.a1521) ";
				List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
				if(list!=null&&list.size()>0){
					String message="";
					String result="";
					for(int i=0;i<list.size();i++){
						if(result.equals(list.get(i).get("code_name"))){
							String khdate=list.get(i).get("a1521").toString();
							String datebefor=String.valueOf((Integer.valueOf(khdate)-1));
							message=message.replace(datebefor, datebefor+"��"+khdate);
						}else{
							String flag=",";
							if(i==0){
								flag="";
							}
							message=message+flag+entryStr(list.get(i).get("a1521"))+"��ȿ���Ϊ"+entryStr(list.get(i).get("code_name"));
						}
					}
					if("".equals(message)){
						message="(��)";
					}
					sql=" update "+tablename+" set "+codename+"='"+message+"' where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	/**
	 * 9���ݵ�λ����ȡ��Ա�������Ρ�ԭ��ְ��
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GetAllDuty(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.* from (select rownum num,a.* from A02 a where a.A0000='"+a.get("a0000")+"' and a.a0255>'0' order by to_number(a.a0223)) a where a.num=1";
				List<HashMap<String,Object>> listb01=cqbs.getListBySQL(sql);
				if(listb01!=null&&listb01.size()>0){
					sql="select b0101,b0111 from b01 where  b0111='"+listb01.get(0).get("a0201b")+"'";
					List<HashMap<String,Object>> listbname=cqbs.getListBySQL(sql);
					String bname="";
					if(listbname!=null&&listbname.size()>0){
						bname=entryStr(listbname.get(0).get("b0101"));
					}
					sql="select decode(a.a0255,'3','ԭ'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("dutyname"));
					}
					sql=" update "+tablename+" set "+codename+"='"+message+"' where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	/**
	 * 10���ݵ�λ������һȡ��Աְ��
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GetAllDutyDmabr1(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.* from (select rownum num,a.* from A02 a where a.A0000='"+a.get("a0000")+"' and a.a0255>'0' order by to_number(a.a0223)) a where a.num=1";
				List<HashMap<String,Object>> listb01=cqbs.getListBySQL(sql);
				if(listb01!=null&&listb01.size()>0){
					sql="select b0101,b0111 from b01 where  b0111='"+listb01.get(0).get("a0201b")+"'";
					List<HashMap<String,Object>> listbname=cqbs.getListBySQL(sql);
					String bname="";
					if(listbname!=null&&listbname.size()>0){
						bname=entryStr(listbname.get(0).get("b0101"));
					}
					sql="select decode(a.a0255,'3','ԭ'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("dutyname"));
					}
					sql=" update "+tablename+" set "+codename+"='"+message+"' where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	/**
	 * 11���ݵ�λ��������ȡ��Աְ��
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GetAllDutyDmabr2(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.* from (select rownum num,a.* from A02 a where a.A0000='"+a.get("a0000")+"' and a.a0255>'0' order by to_number(a.a0223)) a where a.num=1";
				List<HashMap<String,Object>> listb01=cqbs.getListBySQL(sql);
				if(listb01!=null&&listb01.size()>0){
					sql="select b0104,b0111 from b01 where  b0111='"+listb01.get(0).get("a0201b")+"'";
					List<HashMap<String,Object>> listbname=cqbs.getListBySQL(sql);
					String bname="";
					if(listbname!=null&&listbname.size()>0){
						bname=entryStr(listbname.get(0).get("b0104"));
					}
					sql="select decode(a.a0255,'3','ԭ'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("dutyname"));
					}
					sql=" update "+tablename+" set "+codename+"='"+message+"' where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	/**
	 * 12���ݵ�λ����ȡ��Աְ��
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GetAllDutyDwmc(String tablename,String codename) throws AppException, SQLException{
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select a.* from (select rownum num,a.* from A02 a where a.A0000='"+a.get("a0000")+"' and a.a0255>'0' and a.a0213Q<>1 order by to_number(a.a0223)) a where a.num=1";
				List<HashMap<String,Object>> listb01=cqbs.getListBySQL(sql);
				if(listb01!=null&&listb01.size()>0){
					sql="select b0104,b0111 from b01 where  b0111='"+listb01.get(0).get("a0201b")+"'";
					List<HashMap<String,Object>> listbname=cqbs.getListBySQL(sql);
					String bname="";
					if(listbname!=null&&listbname.size()>0){
						bname=entryStr(listbname.get(0).get("b0104"));
					}
					sql="select decode(a.a0255,'3','ԭ'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' and a.a0213Q<>1 "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="��";
						if(i==0){
							flag="";
						}
						message=message+flag+entryStr(list.get(i).get("dutyname"));
					}
					sql=" update "+tablename+" set "+codename+"='"+message+"' where A0000='"+a.get("a0000")+"'";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeQuery(sql);
					stmt.close();
				}
			}
		}
	}
	
	
	
	/**
	 * ������������A01������ʱ����ȫ����A01������,���������
	 * @throws AppException 
	 * @throws SQLException 
	 */
	public void CommInertA01(String tablename) throws AppException, SQLException{
		String sql="select * from A01 where A0000 not in (select A0000 from "+tablename+") ";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		if(listctn!=null&&listctn.size()>0){
			sql="insert into "+tablename+" (A0000) select A0000 from A01 where A0000 not in (select A0000 from "+tablename+")";
			stmt.executeQuery(sql);
		}
//		sql="update "+tablename+" set "+codename+"=''";
//		stmt.executeQuery(sql);
		stmt.close();
	}
	
	/**
	 * Ϊnullת��Ϊ���ַ���
	 * @param obj
	 * @return
	 */
	public String entryStr(Object obj){
		String result="";
		if(obj!=null){
			result=obj.toString();
		}
		return result;
	}
	
}
