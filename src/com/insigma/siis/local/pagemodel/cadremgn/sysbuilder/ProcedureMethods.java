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
	 * 1生成人员的实际年龄。生成规则：当前日期与人员的出生日期的差值，若出生月和日大于当前月和日，则当前年的差值减1年
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
	 * 2生成人员的奖惩信息。生成规则：取A14.A1407(奖惩信息集.奖惩批准日期)  A14.A1404A(奖惩信息集.奖惩名称)，A14.A1421(奖惩信息集.奖惩说明)，按照ID字段排序。
	 * @param tablename
	 * @param codename
	 * @throws AppException
	 * @throws SQLException
	 */
	public void GenAwardFine(String tablename,String codename) throws AppException, SQLException{
		 //A1407+"  "+A1411A+"授予"+A1401Q(转ZB19)
		CommInertA01(tablename);
		String sql="select * from A01";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
		if(listctn!=null&&listctn.size()>0){
			for(HashMap<String,Object> a:listctn){
				sql="select (substr(a.A1407,0,4)||'.'||substr(a.A1407,5,2)||'  '||a.a1411a||'授予'||b.code_name) a14a from "
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
						message="无";
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
	 * 3若仅为党员，出现入党日期(yyyy-mm)，若同时为民主党派，同时出现民主党派的名称；     若仅为民主党派，仅出现民主党派的名称；若是多个民主党派，出现多个，民主党派的名称。
	 * @throws SQLException 
	 * @throws AppException 
	 */
	public void GenInPartyDate(String tablename,String codename) throws AppException, SQLException{
		//A01:a0141政治面貌,a0144入党时间,a3921第二党派,a3927第三党派
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
	 * 4若仅为党员，出现入党日期(yyyy-mm)，若同时为民主党派，同时出现民主党派的名称；     若仅为民主党派，仅出现民主党派的名称；若是多个民主党派，出现多个，民主党派的名称。
	 * @throws SQLException 
	 * @throws AppException 
	 */
	public void GenInPartyDate_short(String tablename,String codename) throws AppException, SQLException{
		//A01:a0141政治面貌,a0144入党时间,a3921第二党派,a3927第三党派
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
	 * 5建立人员多职务的多记录信息集。生成规则：生成的名册的人员首先按照单位排序，                           单个人的多职务按照多职务主次序号出现,人员在多个单位任职,以本单位的职务优先出现,其他单位职务按照多职务主次顺序出现.
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
					//当前单位
					sql="select b.code_name3 dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255='1' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message="";
					for(int i=0;i<list.size();i++){
						String flag="、";
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
					
					//兼职单位
					sql="insert into A49Q(ID,A0000,A4999,A4901,A4902,A4903,A4904,A4905)"
						+ "  select 1,'"+a.get("a0000")+"',1,a.a0201b,0,b.code_name3||'(兼)',a.a0225,a.a0243 from "
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
	 * 6显示各个职称分类中的最高职称、并用、连接
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
						String flag="、";
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
	 * 7建立人员多职务的单信息集。生成规则：生成的名册的人员首先按照单位排序，单个人的多职务按照多职务主次序号出现
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
					//当前单位
					sql="select b.code_name3 dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="、";
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
	 * 8生成人员的奖惩信息。  生成规则：以系统日期为准，取前三年的年度考核结果，按升序排列，之间用“，”号隔开。  如相邻的年度考核结果相同，予以合并，如“1997、1998年度考核为称职，1999年度考核为优秀”。
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
							message=message.replace(datebefor, datebefor+"、"+khdate);
						}else{
							String flag=",";
							if(i==0){
								flag="";
							}
							message=message+flag+entryStr(list.get(i).get("a1521"))+"年度考核为"+entryStr(list.get(i).get("code_name"));
						}
					}
					if("".equals(message)){
						message="(无)";
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
	 * 9根据单位名称取人员所有现任、原任职务
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
					sql="select decode(a.a0255,'3','原'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="、";
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
	 * 10根据单位代码简称一取人员职务
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
					sql="select decode(a.a0255,'3','原'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="、";
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
	 * 11根据单位代码最简称取人员职务
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
					sql="select decode(a.a0255,'3','原'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="、";
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
	 * 12根据单位名称取人员职务
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
					sql="select decode(a.a0255,'3','原'||b.code_name3,b.code_name3) dutyname from "
						+ " A02 a,(select * from code_value where code_type = 'ZB08') b"
						+ " where A0000='"+a.get("a0000")+"' and a.a0255>'0' and a.a0213Q<>1 "
						+ "  and a.a0215a = b.code_value(+) and a.A0201B='"+listb01.get(0).get("a0201b")+"'"
						+ "   order by to_number(a.a0223) ";
					List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
					String message=bname;
					for(int i=0;i<list.size();i++){
						String flag="、";
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
	 * 公共方法：比A01数据少时，补全少于A01的数据,并清空数据
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
	 * 为null转换为空字符串
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
