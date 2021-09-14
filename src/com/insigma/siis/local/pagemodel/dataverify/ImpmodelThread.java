package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.Datarecrejlog;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysmanager.photoconfig.MakeTables;
import com.insigma.siis.local.util.Sortutil;

public class ImpmodelThread implements Runnable {
	
	private Imprecord  imp;
	private CurrentUser user;
	private UserVO userVo;
	private String impno;
    public ImpmodelThread(Imprecord  imp,String impno, CurrentUser user,UserVO userVo) {
        this.imp = imp;
        this.user = user;
        this.userVo = userVo;
        this.impno = impno;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		String impdeptid = imp.getImpdeptid();
		String imptype = imp.getImptype();
		String ftype = imp.getFiletype();
		String imprecordid = imp.getImprecordid();
		String imptemptable = imp.getImptemptable().toUpperCase();
		String A0165 = imp.getA0165();
		Long sortid = 1L;
		String optype = "1";						//日志类型 1、接收
		String a0165sql = "1=1";
		List<String> a0000List=null;
		try {
			sess.beginTransaction();
			imp.setImpstutas("4");
			sess.update(imp);
			sess.flush();
			CommonQueryBS.systemOut("index-start---------------"+ DateUtil.getTime());
			new Thread(new ImpModLogThread("index", "创建索引", sortid++, optype, imprecordid, false)).start();
			if(imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip")){
				checkIndexZip(imptemptable);
			}else{
				String [] tables = {"A01","A02","A05","A06","A08","A14","A15","A30","A36","A57","A99Z1","A33"};//B01不需要添加索引
				checkIndex(imptemptable,tables);
			}
			CommonQueryBS.systemOut("index-end---------------"+ DateUtil.getTime());
			String A0000Sql = "";
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				String borgs = "select mt.b0111 from (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%') mt ";
				
				//处理 不可覆盖的管理人员类别人员。
				if(A0165!=null && !A0165.equals("")){
					a0165sql = "a0165 >'"+A0165+"'";
					
					new Thread(new ImpModLogThread(imptemptable+"A01", "数据比较", sortid++, optype, imprecordid, false)).start();
					//String a01sql = "create table a"+imptemptable+"a01  as  select t.a0000 a0000t,k.a0000 from a01"+imptemptable+" t, a01 k where t.a0184=k.a0184 and k.a0165 <='"+A0165+"' ";
					//直接在临时库中，删除不满足管理类别的人员
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table a"+imptemptable+"a01  as  select t.a0000 a0000t from a01"+imptemptable+" t where t.a0165  < '"+A0165+"' ")).executeUpdate();
					
					if(!(imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a11"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a29"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a31"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a37"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a41"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a53"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a61"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a60"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a62"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a63"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a64"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a68"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a69"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a71"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					}
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a01"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a02"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a06"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a08"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a05"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a14"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a15"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a36"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a57"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a99Z1"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
				}

				// - - 无效 - - 经过文赫同意，原因是，下级接收上级的数据后，会导致数据重复
				if(!imptype.equals("4")){//按机构导入
					//A0000Sql = "SELECT a0000 FROM a01 WHERE "+a0165sql+" AND CONCAT (a0000, '') IN (SELECT t.a0000 from(SELECT a02.a0000,SUBSTR(a02.a0201b,1,"+impdeptid.length()+") as str FROM a02) t WHERE t.str = '"+impdeptid+"') ";
					//SELECT a0000 FROM a01 WHERE CONCAT (a0000, '') IN (SELECT t.a0000 from(SELECT a02.a0000,SUBSTR(a02.a0201b,1,"+impdeptid.length()+") as str FROM a02) t WHERE t.str = '"+impdeptid+"')
					//遇到用户强行导入数据，导致数据重复问题出现时，由用户自己负责
					//用户信息存在两边录入时，应该存在信息覆盖问题，由后入信息覆盖先前录入信息
					//把      当前机构下(包含权限内)的A0000     、    临时表现职与正式库身份证一致的   这两种主键合并
					//存储是接收节点的人员信息 + 新增人员信息
					String a0165SqlTemp="";
					if(A0165!=null && !A0165.equals("")){
						a0165SqlTemp = " and m.a0165 >'"+A0165+"' ";
					}
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table aa"+imptemptable+"a01  as  (select a0000 from a01 WHERE "+a0165sql+" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 "
							+ " WHERE a02.A0201B IN (SELECT cu.b0111 FROM competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"') and a02.a0281 ='true' AND "
							+ " a02.a0201b LIKE '"+impdeptid+"%')) union (select m.a0000 from a01"+imptemptable+" n,a01 m where m.a0184=n.a0184 and n.a0163 = '1' "+a0165SqlTemp+")")).executeUpdate();
					
				}else if("4".equals(imptype)){//按人员导入
					if(A0165!=null && !A0165.equals("")){
						a0165sql = " a." + a0165sql;
					}
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table aa"+imptemptable+"a01  as  select a.a0000 from A01 a,a01"+imptemptable+" t WHERE "+a0165sql+" AND "
							+ " t.a0184 = a.A0184 and t.a0163 ='1'")).executeUpdate();
				}
				
				if(A0165!=null && !A0165.equals("")){
					a0165sql = " a." + a0165sql;
				}
				// 说明：AA" + imptemptable + "A01存储人员重复信息 - 存储是接收节点的人员信息
				A0000Sql = "SELECT X.a0000 FROM AA" + imptemptable + "A01 X group by X.a0000";
				new Thread(new ImpModLogThread("A30", "现职与非现职数据过滤", sortid++, optype, imprecordid, false)).start();
				if(!imptype.equals("4")){//按机构导入
					
					/**
					 * 忽略临时库非现职人员
					 * 优化---2019/04/16
					 * 如果临时库有退出人员，就判断，否则不用判断
					 */
					String tempExitA01Count = sess.createSQLQuery("select count(*) from a01"+imptemptable+" where a0163 <> '1'").uniqueResult().toString();
					if(!"0".equals(tempExitA01Count)) {
						//临时库有退出人员，查出这些人的主键，拼接成sql片段		临时库时退出人员，正式库为现职人员的A0000
						//临时库非现职，正式库现职人员
						long currentTimeMillis = System.currentTimeMillis();
						sess.createSQLQuery("create table A30Temp"+currentTimeMillis+" as select a.a0000 from a01"+imptemptable+" a where exists (select * from a01 b where a.a0184=b.a0184  and a.a0163 <> '1' and  b.a0163 ='1' ) ").executeUpdate();
						String querySql="select a0000 from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")";
						String a0000Num= sess.createSQLQuery(CommonQueryBS.sysOutRtn("select count(a0000) from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")")).uniqueResult().toString();
						if(!"0".equals(a0000Num)) {
							//开始删除
							CommonQueryBS.systemOut("删除临时库的非现职人员...start");
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a02"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a06"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a08"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a05"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a14"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a15"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a36"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a57"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a99Z1"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a01"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
							
							CommonQueryBS.systemOut("删除临时库的非现职人员...end");
						}
						sess.createSQLQuery("drop table A30Temp"+currentTimeMillis+"").executeUpdate();
					}
				}else if("4".equals(imptype)){//按人员导入
					/**
					 * 忽略临时库非现职人员
					 * 优化---2019/04/16
					 * 如果临时库有退出人员，就判断，否则不用判断
					 */
					String queryTempExitA01Count="select count(*) from a01"+imptemptable+" where a0163 <> '1'";
					String tempExitA01Count = sess.createSQLQuery(queryTempExitA01Count).uniqueResult().toString();
					if(!"0".equals(tempExitA01Count)) {
						long currentTimeMillis = System.currentTimeMillis();
						sess.createSQLQuery("create table A30Temp"+currentTimeMillis+" as select a.a0000 from a01"+imptemptable+" a where exists (select * from a01 b where a.a0184=b.a0184  and a.a0163 <> '1' and  b.a0163 ='1' ) ").executeUpdate();
						String querySql="select a0000 from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")";
						//临时库时退出人员，正式库为现职人员的A0000
						String a0000Num= sess.createSQLQuery(CommonQueryBS.sysOutRtn("select count(a0000) from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")")).uniqueResult().toString();
						if(!"0".equals(a0000Num)) {
							//开始删除
							CommonQueryBS.systemOut("删除临时库的非现职人员...start");
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a02"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a06"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a08"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a05"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a14"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a15"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a36"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a57"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a99Z1"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a01"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
							CommonQueryBS.systemOut("删除临时库的非现职人员...end");
						}
						sess.createSQLQuery("drop table A30Temp"+currentTimeMillis+"").executeUpdate();
					}
					
				}
				
				//位置放置删除非现职人员之前
				
				
				new Thread(new ImpModLogThread("A06", "接收A06数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A06数据");
				//a06 //delete 
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a06 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a06 select t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.A0699, t.UPDATED, t.SORTID"+
	       				" from a06"+imptemptable+" t ")).executeUpdate();
				new Thread(new ImpModLogThread("A08", "接收A08数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A08数据");
				//a08 //delete 
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a08 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a08 select t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0901A,t.A0901B,t.A0804,"
						+ "t.A0807,t.A0904,t.A0814,t.A0824,t.A0827,t.A0837,t.A0811,t.A0898,t.A0831,t.A0832,t.A0834,t.A0835,"
						+ "t.A0838,t.A0839,t.A0899,t.updated,t.SORTID,t.wage_used"+
	       				" from a08"+imptemptable+" t")).executeUpdate();
				
				
				new Thread(new ImpModLogThread("A14", "接收A14数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A14数据");
				//a14 //delete 
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a14 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a14 select t.a0000,t.a1400,t.a1404a,t.a1404b,t.a1407,t.a1411a,t.a1414,t.a1415,t.a1424,t.a1428,"+ 
						"t.updated,t.sortid "+
	       				" from a14"+imptemptable+" t")).executeUpdate();
				//a15 //delete 
				new Thread(new ImpModLogThread("A15", "接收A15数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A15数据");
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a15 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a15 select t.a0000, t.a1500, t.a1517, t.a1521, t.a1527, t.updated   "+
	       				" from a15"+imptemptable+" t")).executeUpdate();
				
					//a30 //delete  
					new Thread(new ImpModLogThread("A30", "接收A30数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("接收A30数据");
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a30 where A0000 in ("+A0000Sql+")")).executeUpdate();
					//insert
					/*sess.createSQLQuery("replace into a30 select t.a0000,t.a3001,t.a3004,t.a3007a ,t.a3034 ,t.updated  "+
		       				" from a30"+imptemptable+" t").executeUpdate();*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a30 select t.a0000,t.a3001,t.a3004,t.A3117A,t.A3101,t.A3137,t.a3034,t.updated,t.A3007A,t.A3038"+
		       				" from a30"+imptemptable+" t   group by t.a0000")).executeUpdate();//
				
				//a36 //delete 
				new Thread(new ImpModLogThread("A36", "接收A36数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A36数据");
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a36 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				/*sess.createSQLQuery("replace into a36 select t.a0000,t.a3600,t.a3601,t.a3604a,t.a3607,t.a3611,t.a3627 ,t.sortid ,t.updated "+
	       				" from a36"+imptemptable+" t").executeUpdate();*/
				//更新临时表中家庭成员排序
				Sortutil.updateA36Sort("a36"+imptemptable);
				//更新临时表中家庭成员类别
				Sortutil.updateA36Updated("a36"+imptemptable);
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a36 select t.a0000,t.a3600,t.a3601,t.a3604a,t.a3607,t.a3611,t.a3627 ,t.sortid ,t.updated,t.a3684 "+
	       				" from a36"+imptemptable+" t")).executeUpdate();
				
				
				//a57 //delete 
				new Thread(new ImpModLogThread("A57", "接收A57数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A57数据");
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a57 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				//-------------------------------------------------------------------------------------------------------
				
				
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a57 select t.a0000,t.a5714 ,t.updated,t.PHOTODATA,t.PHOTONAME,t.PHOTSTYPE,t.PHOTOPATH,t.PICSTATUS,t.PICHASH" +
	       				" from a57"+imptemptable+" t")).executeUpdate();
				
				new Thread(new ImpModLogThread("A65", "接收A65数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A65数据");
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a65 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a65 select t.a6500 ,t.a0000 ,t.a6501 ,t.a6502 ,t.a6503 ,t.a6504 ,t.a6505,t.a6506 ,t.a6507 ,t.a6508 ,t.tgpc ,t.a0200 ,t.a6509 ,t.a6510 ,t.a6511 ,t.a6512 ,t.a6513 ,t.a6514 ,t.a6511a ,t.a6515 ,t.a6516 ,t.A6526,t.A6511B,A6511C,t.A6517A,t.A6517B,t.A6517C,t.A0200_OLD,t.A0500_1,t.A0500_2,t.A0500,t.SORTID"
	       				+" from a65"+imptemptable+" t")).executeUpdate();
				//a05
				new Thread(new ImpModLogThread("A05", "接收A05数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A05数据");
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a05 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a05 select A0000,A0500,A0531,A0501B,A0504,A0511,A0517,A0524,A0525,A0526,A0528B,A0530,A0532 "+
						" from a05"+imptemptable+" t")).executeUpdate();
				
				//a33
				new Thread(new ImpModLogThread("A33", "接收A33数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A33数据");
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a33 where A0000 in ("+A0000Sql+")")).executeUpdate();
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a33 select A0000,A3300,A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3361,A3362,A3371,A3372,A3381,A3382,A3385,A3360,GZNX "+
						" from a33"+imptemptable+" t")).executeUpdate();
				//A99Z1
				new Thread(new ImpModLogThread("A99Z1", "接收A99Z1数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A99Z1数据");
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from A99Z1 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into A99Z1 select a0000,a99Z100,a99Z101,a99Z102,a99Z103,a99Z104,a99Z191,a99Z195 "+
						" from A99Z1"+imptemptable+" t")).executeUpdate();
				
				
				
				new Thread(new ImpModLogThread("A01", "接收A01数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A01数据");
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from A01 where A0000 in ("+A0000Sql+")")).executeUpdate();
					if("4".equals(imptype)){//按人员导入
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("update B01"+imptemptable+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A01"+imptemptable+" t set t.a0195 =(select b.psnb0111 from b01"+imptemptable+" b where b.b0111=t.a0195) where  exists (select 1 from b01"+imptemptable+" b where b.b0111=t.a0195)")).executeUpdate();
					}
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a01 select A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,"
						+ "A0134,A0140,A0141,A0144,A3921,A3927,A0160,A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192D,"
						+ "A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A2949,A0122,A0104A,A0111,A0114,A0117A,"
						+ "A0128B,A0141D,A0144B,A0144C,A0148,A0148C,A0149,A0151,A0153,A0157,A0158,A0159,A015A,A0161,A0162,A0180,"
						+ "A0191,A0192B,A0193,A0194U,A0198,A0199,A01K01,A01K02,CBDRESULT,CBDW,ISVALID,NL,NMZW,NRZW,ORGID,QRZXL,"
						+ "QRZXLXX,QRZXW,QRZXWXX,RMLY,STATUS,TBR,TBRJG,USERLOG,XGR,ZZXL,ZZXLXX,ZZXW,ZZXWXX,A0155,AGE,JSNLSJ,"
						+ "RESULTSORTID,TBSJ,XGSJ,SORTID,A0194,A0192E,A0192F,TORGID,TORDER,ZGXL,ZGXLXX,ZGXW,ZGXWXX,TCSJSHOW,TCFSSHOW,fsj,a0123,A15Z191,A0192X "+
						" from a01"+imptemptable+" t")).executeUpdate();
				
				
				
				if(!imptype.equals("4")){//按机构导入
					new Thread(new ImpModLogThread("B01", "接收B01数据", sortid++, optype, imprecordid, false)).start();
					//b01
					//delete 
//					sess.createSQLQuery(" delete from b01 where b0111 in ("+borgs+")").executeUpdate();
					//insert
					if(impno.equals("2")){//人员排序
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("update b01 b,b01"+imptemptable+" t set t.ERROR_INFO = '1' where b.b0111=t.b0111 ")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("insert into b01 select B0101,B0104,B0107,B0111,B0114,B0117,B0121,B0124,B0127,B0131,B0140,"
								+ "B0141,B0142,B0143,B0150,B0180,B0183,B0185,B0188,B0189,B0190,B0191,B0191A,B0192,B0193,B0194,B01TRANS,"
								+ "B01IP,B0227,B0232,B0233,SORTID,USED,UPDATED,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE,STATUS,B0238,"
								+ "B0239,B0234,B0235,B0236,B0132,B0164,B0167,B0268,B0269"+
			       				" from b01"+imptemptable+" t where  t.ERROR_INFO = '2'")).executeUpdate(); 
					}else{
						//处理首选机构的机构排序
						Object obj = sess.createSQLQuery(CommonQueryBS.sysOutRtn("select sortid from b01 where b0111 = '"+ imp.getImpdeptid() +"'")).uniqueResult();
						
						sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from b01 where b0111 in ("+borgs+") and b0111 !='-1'")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into b01 select B0101,B0104,B0107,B0111,B0114,B0117,B0121,B0124,B0127,B0131,B0140,"
								+ "B0141,B0142,B0143,B0150,B0180,B0183,B0185,B0188,B0189,B0190,B0191,B0191A,B0192,B0193,B0194,B01TRANS,"
								+ "B01IP,B0227,B0232,B0233,SORTID,USED,UPDATED,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE,STATUS,B0238,"
								+ "B0239,B0234,B0235,B0236,B0132,B0164,B0167,B0268,B0269"+
			       				" from b01"+imptemptable+" t")).executeUpdate();
						
						//更新首选机构的机构排序
						if(obj!=null && !"".equals(obj.toString().trim())){
							sess.createSQLQuery(CommonQueryBS.sysOutRtn("update b01 b set b.SORTID = "+obj.toString()+" where b.b0111 = '"+imp.getImpdeptid()+"'")).executeUpdate(); 
						}
					}
					
					
					/**
					 * zxw - b0111 like '"+impdeptid+"%' 性能优化 改为：substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"'
					 * 
					 * 首先给系统管理员用户赋权，其次给当前用户授权。
					 * 其中不为普通用户授权，是要管理元用户重新授权
					 * 
					 * impdeptid：当前导入最高节点 - 新的 -机构编码
					 */
					CommonQueryBS.systemOut("当前插入数据节点："+impdeptid);
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from COMPETENCE_USERDEPT where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and userid='"+user.getId()+"'")).executeUpdate();
					
					Connection conn = sess.connection();
					Statement stmt = conn.createStatement();
					
					
					//查询出所有导入时，待授权的机构
					ResultSet rs = stmt.executeQuery(CommonQueryBS.sysOutRtn("select b0111 from b01 where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"'"));
					//查询出所有待授权的管理员用户
					CommQuery commQuery = new CommQuery();
					List<HashMap<String,Object>> list = commQuery.getListBySQL(CommonQueryBS.sysOutRtn("select userid,otherinfo from smt_user where substr(otherinfo, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and USERTYPE = '1'"));
					
					if(list!=null && list.size() > 0){
						
						for (int k = 0; k < list.size(); k++) {
							sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from COMPETENCE_USERDEPT where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and userid='"+list.get(k)+"'")).executeUpdate();
						}
						
						PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
						String otherinfo = "", b0111 = "";
						int i = 0;
						if (rs != null){
							while (rs.next()) {//循环机构
								
								for (int k = 0; k < list.size(); k++) {//循环用户
									otherinfo = list.get(k).get("otherinfo") + "";
									b0111 = rs.getString(1) + "";
									if(b0111.length() < otherinfo.length() || !otherinfo.equals(b0111.substring(0, otherinfo.length()))){
										continue;//判断当前用户是否存在该机构权限
									}
									pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
									//pstmt1.setString(2, user.getId());
									pstmt1.setString(2, otherinfo);
									pstmt1.setString(3, b0111);
									pstmt1.setString(4, "1");
									pstmt1.addBatch();
									i++;
									if(i%10000 == 0){
										pstmt1.executeBatch();
										pstmt1.clearBatch();
									}
								}
								
							}
							pstmt1.executeBatch();
							pstmt1.clearBatch();
						}
						
						rs.close();
						pstmt1.close();
						stmt.close();
						conn.close();
					}/*else{		//空库时需要给当前用户机构授权
						
					}*/
					
					//当前用户机构授权
					//sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
					
					Connection conn2 = sess.connection();
					Statement stmt2 = conn2.createStatement();
					PreparedStatement pstmt2 = conn2.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
					//查询出所有导入时，待授权的机构
					ResultSet rs2 = stmt2.executeQuery("select b0111 from b01 where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and "
							// zxw 2019-01-25 去除重复插入的数据
							+ "b0111 not in (select b0111 from COMPETENCE_USERDEPT where userid='"+user.getId()+"')");
					
					int i = 0;
					if (rs2 != null){
						while (rs2.next()) {
							pstmt2.setString(1, UUID.randomUUID().toString().replace("-", ""));
							pstmt2.setString(2, user.getId());
							pstmt2.setString(3, rs2.getString(1));
							pstmt2.setString(4, "1");
							pstmt2.addBatch();
							i++;
							if(i%10000 == 0){
								pstmt2.executeBatch();
								pstmt2.clearBatch();
							}
						}
						pstmt2.executeBatch();
						pstmt2.clearBatch();
					}
					rs2.close();
					pstmt2.close();
					stmt2.close();
					conn2.close();
					
				}
				
				//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
				//update 更新temp任职机构id
				new Thread(new ImpModLogThread("A02", "接收A02数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A02数据");
				if(!imptype.equals("4")){//按机构导入
					//delete 
//					sess.createSQLQuery(" delete from a02 where A0000 in (select a0000 from a01 where "+a0165sql+" and ((status='3' OR status='2' ) and ORGID like '"+ impdeptid +"%') or (status='1' and a0000 in (select t.a0000 from (select a0000 from a02 where A0255='1' and  a0201b like '"+ impdeptid +"%' ) t)))").executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a02 where a02.A0000 in ("+A0000Sql+")")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a02 where a02.A0000 not in (select a0000 from a01)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A02"+imptemptable+" t set t.a0201b  = t.a0201a  where t.a0201b not in (select b0111 from B01"+imptemptable+" )")).executeUpdate();
				} else if("4".equals(imptype)){//按机构导入
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a02 where a02.A0000 in ("+A0000Sql+")")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a02 where a02.A0000 not in (select a0000 from a01)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update b01 k,b01"+imptemptable+" q set q.psnB0111 =k.b0111 where k.b0114=q.b0114 and k.b0101=q.b0101")).executeUpdate();
					//sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A02"+imptemptable+" t,b01"+imptemptable+" k set t.a0201b=k.psnB0111,t.ERROR_INFO ='1' where  t.a0201b=k.b0111  ")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A02"+imptemptable+" t set t.a0201b ='-1' where (ERROR_INFO='2' OR a0201b is null)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A02"+imptemptable+" t set t.ERROR_INFO ='2' ")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A02"+imptemptable+" t set t.a0201b  = t.a0201a  where t.a0201b not in (select b0111 from B01 )")).executeUpdate();
				}

				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a02 select A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,"
						+ "A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,"
						+ "A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,"
						+ "A0215B,A0209,A0207,A0204,A0201C,A0201,A0279"+
	       				" from a02"+imptemptable+" t")).executeUpdate();
				
				
			} else {//oracle    pywu 20170609核对字段是否对应数据库
				String borgs = "select d.b0111 from b01 d where d.b0111 like '"+impdeptid+"%' ";
				
				
				//处理 不可覆盖的管理人员类别人员。
				if(A0165!=null && !A0165.equals("")){
					a0165sql = "a0165 >'"+A0165+"'";
					
					new Thread(new ImpModLogThread(imptemptable+"A01", "数据比较", sortid++, optype, imprecordid, false)).start();
					//String a01sql = "create table a"+imptemptable+"a01  as  select t.a0000 a0000t,k.a0000 from a01"+imptemptable+" t, a01 k where t.a0184=k.a0184 and k.a0165 <='"+A0165+"' ";
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table a"+imptemptable+"a01  as  select t.a0000 a0000t from a01"+imptemptable+" t where t.a0165 < '"+A0165+"' ")).executeUpdate();
					
					if(!(imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a11"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a29"+imptemptable+" where exists (select a0000t from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a31"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a37"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a41"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a53"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a61"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a60"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a62"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a63"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a64"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a68"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a69"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a71"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					}
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a01"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a02"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a06"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a08"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a05"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a14"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a15"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a36"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a57"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a99Z1"+imptemptable+" where exists (select 1 from a"+imptemptable+"a01 where a0000=a0000t)")).executeUpdate();
				}
				
				if(!imptype.equals("4")){//按机构导入
					//存储是接收节点的人员信息 + 新增人员信息
					String a0165SqlTemp="";
					if(A0165!=null && !A0165.equals("")){
						a0165SqlTemp = " and m.a0165 >'"+A0165+"' ";
					}
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table aa"+imptemptable+"a01  as  (select a0000 from a01 WHERE "+a0165sql+" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 "
							+ " WHERE a02.A0201B IN (SELECT cu.b0111 FROM competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"') and a02.a0281 ='true' AND "
							+ " a02.a0201b LIKE '"+impdeptid+"%')) union (select m.a0000 from a01"+imptemptable+" n,a01 m where m.a0184=n.a0184 and n.a0163 = '1' "+a0165SqlTemp+")")).executeUpdate();
				}else if("4".equals(imptype)){//按人员导入
					if(A0165!=null && !A0165.equals("")){
						a0165sql = " a." + a0165sql;
					}
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table aa"+imptemptable+"a01  as  select a.a0000 from A01 a,a01"+imptemptable+" t WHERE "+a0165sql+" AND "
							+ "t.a0184 = a.A0184 and t.a0163 = '1'")).executeUpdate();
				}
				if(A0165!=null && !A0165.equals("")){
					a0165sql = " a." + a0165sql;
				}
				A0000Sql = "SELECT X.a0000 FROM AA" + imptemptable + "A01 X group by X.a0000";
				new Thread(new ImpModLogThread("A30", "现职与非现职数据过滤", sortid++, optype, imprecordid, false)).start();
				if(!imptype.equals("4")){//按机构导入
					/**
					 * 忽略临时库非现职人员
					 * 优化---2019/04/16
					 * 如果临时库有退出人员，就判断，否则不用判断
					 */
					String tempExitA01Count = sess.createSQLQuery(CommonQueryBS.sysOutRtn("select count(*) from a01"+imptemptable+" where a0163 <> '1'")).uniqueResult().toString();
					if(!"0".equals(tempExitA01Count)) {
						long currentTimeMillis = System.currentTimeMillis();
						sess.createSQLQuery("create table A30Temp"+currentTimeMillis+" as select a.a0000 from a01"+imptemptable+" a where exists (select * from a01 b where a.a0184=b.a0184  and a.a0163 <> '1' and  b.a0163 ='1' ) ").executeUpdate();
						String querySql="select a0000 from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")";
						//	临时库时退出人员，正式库为现职人员的A0000
						String a0000Num= sess.createSQLQuery(CommonQueryBS.sysOutRtn("select count(a0000) from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")")).uniqueResult().toString();
						if(!"0".equals(a0000Num)) {
							//开始删除
							CommonQueryBS.systemOut("删除临时库的非现职人员...start");
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a02"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a06"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a08"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a05"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a14"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a15"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a36"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a57"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a99Z1"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a01"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
							
							CommonQueryBS.systemOut("删除临时库的非现职人员...end");
						}
						sess.createSQLQuery("drop table A30Temp"+currentTimeMillis+"").executeUpdate();
					}
				}else if("4".equals(imptype)){//按人员导入
					/**
					 * 忽略临时库非现职人员
					 * 优化---2019/04/16
					 * 如果临时库有退出人员，就判断，否则不用判断
					 */
					String tempExitA01Count = sess.createSQLQuery(CommonQueryBS.sysOutRtn("select count(*) from a01"+imptemptable+" where a0163 <> '1'")).uniqueResult().toString();
					if(!"0".equals(tempExitA01Count)) {
						long currentTimeMillis = System.currentTimeMillis();
						sess.createSQLQuery("create table A30Temp"+currentTimeMillis+" as select a.a0000 from a01"+imptemptable+" a where exists (select * from a01 b where a.a0184=b.a0184  and a.a0163 <> '1' and  b.a0163 ='1' ) ").executeUpdate();
						String querySql="select a0000 from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")";
						//临时库时退出人员，正式库为现职人员的A0000
						String a0000Num= sess.createSQLQuery(CommonQueryBS.sysOutRtn("select count(a0000) from A30Temp"+currentTimeMillis+" a where a.a0000 not in ("+A0000Sql+")")).uniqueResult().toString();
						if(!"0".equals(a0000Num)) {
							
							//开始删除
							CommonQueryBS.systemOut("删除临时库的非现职人员...start");
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a02"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a06"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a08"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a05"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a14"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a15"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a30"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a33"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a36"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a57"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a65"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a99Z1"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
								sess.createSQLQuery(CommonQueryBS.sysOutRtn("delete from a01"+imptemptable+" where a0000 in ("+querySql+")")).executeUpdate();
							
							CommonQueryBS.systemOut("删除临时库的非现职人员...end");
						}
						sess.createSQLQuery("drop table A30Temp"+currentTimeMillis+"").executeUpdate();
					}
					
					
				}
				
				
				//a06 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				new Thread(new ImpModLogThread("A06", "接收A06数据", sortid++, optype, imprecordid, false)).start();
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a06 where A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("接收A06数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a06 a USING a06"+imptemptable+" t ON (a.a0600 = t.a0600)  WHEN MATCHED THEN UPDATE " +
						"SET a.A0000=t.A0000,a.A0601=t.A0601,a.A0602=t.A0602,a.A0604=t.A0604,a.A0607=t.A0607," +
						"a.A0611=t.A0611,a.A0614=t.A0614,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A0699=t.A0699 " +
						" WHEN NOT MATCHED THEN INSERT " +
						"(A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, SORTID, UPDATED, A0699)"+
						"VALUES (t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED, t.A0699) " +
						"")).executeUpdate();
				//a08 //delete 
				new Thread(new ImpModLogThread("A08", "接收A08数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a08 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("接收A08数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a08 a USING a08"+imptemptable+" t ON (t.a0800 = a.a0800)  WHEN MATCHED THEN UPDATE " +
						" SET a.A0000=t.A0000,a.A0801A=t.A0801A,a.A0801B=t.A0801B,a.A0804=t.A0804,a.A0807=t.A0807,a.A0811=t.A0811,a.A0814=t.A0814,a.A0824=t.A0824," +
						" a.A0827=t.A0827,a.A0831=t.A0831,a.A0832=t.A0832,a.A0834=t.A0834,a.A0835=t.A0835,a.A0837=t.A0837,a.A0838=t.A0838,a.A0839=t.A0839," +
						" a.A0898=t.A0898,a.A0899=t.A0899,a.A0901A=t.A0901A,a.A0901B=t.A0901B,a.A0904=t.A0904,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.WAGE_USED=t.WAGE_USED" + 
						"  WHEN NOT MATCHED THEN INSERT" +
						"(A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827, A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898," +
						" A0899,A0901A, A0901B,A0904,SORTID,updated,wage_used)"+
						" VALUES (t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0804,t.A0807,t.A0811,t.A0814,t.A0824,t.A0827, t.A0831,t.A0832,t.A0834,t.A0835,t.A0837,t.A0838,t.A0839,t.A0898," +
						" t.A0899,t.A0901A, t.A0901B,t.A0904,t.SORTID,t.updated,t.wage_used ) ")).executeUpdate();
				//a11 //delete 
				
				//a14 //delete 
				new Thread(new ImpModLogThread("A14", "接收A14数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a14 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("接收A14数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a14 a USING a14"+imptemptable+" t ON (a.a1400 = t.a1400)  WHEN MATCHED THEN UPDATE " +
						" SET a.A0000=t.A0000,a.A1404A=t.A1404A,a.A1404B=t.A1404B,a.A1407=t.A1407,a.A1411A=t.A1411A,a.A1414=t.A1414," +
						" a.A1415=t.A1415,a.A1424=t.A1424,a.A1428=t.A1428,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED " +
						"  WHEN NOT MATCHED THEN INSERT" +
						"(a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,sortid ,updated )"+
						" VALUES (t.a0000, t.a1400, t.a1404a, t.a1404b, t.a1407 ,t.a1411a ,t.a1414 ,t.a1415, t.a1424, t.a1428,t.sortid ,t.updated )" +
						" ")).executeUpdate();
				//a15 //delete 
				new Thread(new ImpModLogThread("A15", "接收A15数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a15 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("接收A15数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a15 a USING a15"+imptemptable+" t ON (a.a1500 = t.a1500)  WHEN MATCHED THEN UPDATE" +
						" SET a.A0000=t.A0000,a.A1517=t.A1517,a.A1521=t.A1521,a.UPDATED=t.UPDATED,a.A1527=t.A1527" +
						"  WHEN NOT MATCHED THEN INSERT" +
						"(a0000, a1500, a1517,a1521, updated, a1527)"+
						" VALUES (t.a0000, t.a1500, t.a1517,t.a1521, t.updated, t.a1527) ")).executeUpdate();
					//a30 //delete 
					new Thread(new ImpModLogThread("A30", "接收A30数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a30 where A0000 in ("+A0000Sql+")")).executeUpdate();
					//insert
					CommonQueryBS.systemOut("接收A30数据time---------" + DateUtil.getTime());
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a30 a USING a30"+imptemptable+" t ON (a.a0000 = t.a0000 and t.a0000 in ( select a0000 from A30"+imptemptable+" group by a0000))  WHEN MATCHED THEN UPDATE" +
							" SET a.A3001=t.A3001,a.A3004=t.A3004,a.A3117A=t.A3117A,a.A3101=t.A3101,a.A3137=t.A3137,a.A3007A=t.A3007A,a.A3034=t.A3034,a.UPDATED=t.UPDATED,a.A3038=t.A3038" +
							" " +
							" WHEN NOT MATCHED THEN INSERT(a0000,a3001, a3004, a3117a, a3101, a3137, a3034, updated, a3007a,a3038) VALUES (t.a0000,t.a3001, t.a3004, t.a3117a, t.a3101, t.a3137, t.a3034, t.updated, t.a3007a,t.a3038)" +
							" ")).executeUpdate();
				
				//a36 //delete 
				new Thread(new ImpModLogThread("A36", "接收A36数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a36 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				//insert
				CommonQueryBS.systemOut("接收A36数据time---------" + DateUtil.getTime());
				//更新临时表中家庭成员排序
				Sortutil.updateA36Sort("a36"+imptemptable);
				//更新临时表中家庭成员类别
				Sortutil.updateA36Updated("a36"+imptemptable);
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("MERGE INTO a36 a USING a36"+imptemptable+" t ON (a.a3600 = t.a3600)  WHEN MATCHED THEN UPDATE" +
						" SET a.A0000=t.A0000,a.A3601=t.A3601,a.A3604A=t.A3604A,a.A3607=t.A3607,a.A3611=t.A3611,a.A3627=t.A3627,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A3684=t.A3684 " +
						"  WHEN NOT MATCHED THEN INSERT " +
						"(a0000,a3600,a3601,a3604a,a3607,a3611,a3627 ,sortid ,updated,A3684)"+
						"VALUES (t.a0000,t.a3600, t.a3601, t.a3604a, t.a3607, t.a3611, t.a3627 ,t.sortid ,t.updated,t.A3684) " +
						"")).executeUpdate();
				
				//a57 //delete 
				new Thread(new ImpModLogThread("A57", "接收A57数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a57 where  A0000 in ("+A0000Sql+")")).executeUpdate();
					/*}*/
				//insert
				CommonQueryBS.systemOut("接收A57数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a57 a USING a57"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE" +
						" SET a.A5714=t.A5714,a.UPDATED=t.UPDATED,a.PHOTODATA=t.PHOTODATA,a.PHOTONAME=t.PHOTONAME,a.PHOTSTYPE=t.PHOTSTYPE,a.PHOTOPATH=t.PHOTOPATH,a.PICSTATUS=t.PICSTATUS,a.PICHASH=t.PICHASH " +
						"  WHEN NOT MATCHED THEN INSERT" +
						" (a0000,a5714 ,updated,PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH,PICSTATUS,PICHASH) VALUES (t.a0000,t.a5714 ,t.updated,t.PHOTODATA,t.PHOTONAME,t.PHOTSTYPE,t.PHOTOPATH,'1',t.PICHASH)" +
						" ")).executeUpdate();
				
				new Thread(new ImpModLogThread("A65", "接收A65数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("接收A65数据time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a65 where A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("MERGE INTO a65 a USING a65"+imptemptable+" t ON (a.A6500 = t.A6500) WHEN MATCHED THEN UPDATE"+
	       				" SET a.a0000=t.a0000  ,a.a6501=t.a6501 ,a.a6502=t.a6502 ,a.a6503=t.a6503 ,a.a6504=t.a6504 ,a.a6505=t.a6505,a.a6506=t.a6506 ,a.a6507=t.a6507 ,a.a6508=t.a6508 ,a.tgpc=t.tgpc ,a.a0200=t.a0200 ,a.a6509=t.a6509 ,a.a6510=t.a6510 ,a.a6511=t.a6511 ,a.a6512=t.a6512 ,a.a6513=t.a6513 ,a.a6514=t.a6514 ,a.a6511a=t.a6511a ,a.a6515=t.a6515 ,a.a6516=t.a6516 ,a.A6526=t.A6526,a.A6511B=t.A6511B,a.A6511C=t.A6511C,a.A6517A=t.A6517A,a.A6517B=t.A6517B,a.A6517C=t.A6517C,a.A0200_OLD=t.A0200_OLD,a.A0500_1=t.A0500_1,a.A0500_2=t.A0500_2,a.SORTID=t.SORTID"+
						" WHEN NOT MATCHED THEN INSERT (a.a0000 ,a.a6500 ,a.a6501 ,a.a6502 ,a.a6503 ,a.a6504 ,a.a6505,a.a6506 ,a.a6507 ,a.a6508 ,a.tgpc ,a.a0200 ,a.a6509 ,a.a6510 ,a.a6511 ,a.a6512 ,a.a6513 ,a.a6514 ,a.a6511a ,a.a6515 ,a.a6516 ,a.A6526,a.A6511B,a.A6511C,a.A6517A,a.A6517B,a.A6517C,a.A0200_OLD,a.A0500_1,a.A0500_2,a.A0500,a.SORTID)"+
	       				" VALUES(t.a0000 ,t.a6500 ,t.a6501 ,t.a6502 ,t.a6503 ,t.a6504 ,t.a6505,t.a6506 ,t.a6507 ,t.a6508 ,t.tgpc ,t.a0200 ,t.a6509 ,t.a6510 ,t.a6511 ,t.a6512 ,t.a6513 ,t.a6514 ,t.a6511a ,t.a6515 ,t.a6516 ,t.A6526,t.A6511B,t.A6511C,t.A6517A,t.A6517B,t.A6517C,t.A0200_OLD,t.A0500_1,t.A0500_2,t.A0500,t.SORTID)")).executeUpdate();
				//A05---
				new Thread(new ImpModLogThread("A05", "接收A05数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from A05 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("接收A05数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("MERGE INTO a05 a USING a05"+imptemptable+" t ON (a.A0500 = t.A0500)  WHEN MATCHED THEN UPDATE"+
						" SET a.A0000=t.A0000,a.A0531=t.A0531,a.A0501B=t.A0501B,a.A0504=t.A0504,a.A0511=t.A0511,a.A0517=t.A0517,a.A0524=t.A0524,a.A0525=t.A0525,a.A0528B=t.A0528B,a.A0530=t.A0530,a.A0532=t.A0532 "+
						"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A0500,a.A0531,a.A0501B,a.A0504,a.A0511,a.A0517,a.A0524,a.A0525,a.A0528B,a.A0530,a.A0532)" +
						" VALUES (t.A0000,t.A0500,t.A0531,t.A0501B,t.A0504,t.A0511,t.A0517,t.A0524,t.A0525,t.A0528B,t.A0530,t.A0532)"+
						" ")).executeUpdate();
				
				//A33---
				new Thread(new ImpModLogThread("A33", "接收A33数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from A33 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				//insert
				CommonQueryBS.systemOut("接收A33数据time---------" + DateUtil.getTime());
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("MERGE INTO a33 a USING a33"+imptemptable+" t ON (a.A3300 = t.A3300)  WHEN MATCHED THEN UPDATE"+
						" SET a.A0000=t.A0000,a.A3310=t.A3310,a.A3321=t.A3321,a.A3321C=t.A3321C,a.A3321D=t.A3321D,a.A3322=t.A3322,a.A3323=t.A3323,a.A3331=t.A3331,a.A3332=t.A3332,a.A3333=t.A3333,a.A3350=t.A3350,a.A3361=t.A3361,a.A3362=t.A3362,a.A3371=t.A3371,a.A3372=t.A3372,a.A3381=t.A3381,a.A3382=t.A3382,a.A3385=t.A3385,a.A3360=t.A3360,a.GZNX=t.GZNX "+
						"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A3300,a.A3310,a.A3321,a.A3321C,a.A3321D,a.A3322,a.A3323,a.A3331,a.A3332,a.A3333,a.A3350,a.A3361,a.A3362,a.A3371,a.A3372,a.A3381,a.A3382,a.A3385,a.A3360,a.GZNX)" +
						" VALUES (t.A0000,t.A3300,t.A3310,t.A3321,t.A3321C,t.A3321D,t.A3322,t.A3323,t.A3331,t.A3332,t.A3333,t.A3350,t.A3361,t.A3362,t.A3371,t.A3372,t.A3381,t.A3382,t.A3385,t.A3360,t.GZNX)"+
						" ")).executeUpdate();
				
				//A99Z1---
				new Thread(new ImpModLogThread("A99Z1", "接收A99Z1数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from A99Z1 where  A0000 in ("+A0000Sql+")")).executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("接收A99Z1数据time---------" + DateUtil.getTime());
				sess.createSQLQuery("MERGE INTO a99Z1 a USING a99Z1"+imptemptable+" t ON (a.A99Z100 = t.A99Z100)  WHEN MATCHED THEN UPDATE"+
						" SET a.A0000=t.A0000,a.A99Z101=t.A99Z101,a.A99Z102=t.A99Z102,a.A99Z103=t.A99Z103,a.A99Z104=t.A99Z104,a.A99Z191=t.A99Z191,a.A99Z195=t.A99Z195"+
						"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A99Z100,a.A99Z101,a.A99Z102,a.A99Z103,a.A99Z104,a.A99Z191,a.A99Z195)" +
						" VALUES (t.A0000,t.A99Z100,t.A99Z101,t.A99Z102,t.A99Z103,t.A99Z104,t.A99Z191,t.A99Z195)"+
						" ").executeUpdate();
				
				
				//a01
				new Thread(new ImpModLogThread("A01", "接收A01数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//update 统计关系所在单位
				/*if(!imptype.equals("4")){
					//delete 
					sess.createSQLQuery(" delete from a01 where "+a0165sql+" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN (SELECT cu.b0111 FROM "
							+ "competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"') AND a0281 = 'true' "
							+ "AND a02.a0201b LIKE '"+impdeptid+"%')").executeUpdate();
				}else if(imptype.equals("4")){*/
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from A01 where A0000 in ("+A0000Sql+")")).executeUpdate();
				if("4".equals(imptype)){
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update B01"+imptemptable+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)")).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("update A01"+imptemptable+" t set t.a0195 =(select b.psnb0111 from b01"+imptemptable+" b where b.b0111=t.a0195) where  exists (select 1 from b01"+imptemptable+" b where b.b0111=t.a0195)")).executeUpdate();
				}
				/*}*/
				CommonQueryBS.systemOut("接收A01数据time---------" + DateUtil.getTime());
				String a0000Sql="select a0000 from (a01"+imptemptable+")";
				a0000List=sess.createSQLQuery(a0000Sql).list();
				//insert
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a01 a USING a01"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE " +
						" SET a.A0192E=t.A0192E,a.A0199=t.A0199,a.A01K01=t.A01K01,a.A01K02=t.A01K02,a.CBDRESULT=t.CBDRESULT,a.CBDW=t.CBDW,"
						+ "a.ISVALID=t.ISVALID,a.NL=t.NL,a.NMZW=t.NMZW,a.NRZW=t.NRZW,a.ORGID=t.ORGID,a.QRZXL=t.QRZXL,a.QRZXLXX=t.QRZXLXX,"
						+ "a.QRZXW=t.QRZXW,a.QRZXWXX=t.QRZXWXX,a.RMLY=t.RMLY,a.STATUS=t.STATUS,a.TBR=t.TBR,a.TBRJG=t.TBRJG,"
						+ "a.USERLOG=t.USERLOG,a.XGR=t.XGR,a.ZZXL=t.ZZXL,a.ZZXLXX=t.ZZXLXX,a.ZZXW=t.ZZXW,a.ZZXWXX=t.ZZXWXX,a.A0155=t.A0155,"
						+ "a.AGE=t.AGE,a.JSNLSJ=t.JSNLSJ,a.RESULTSORTID=t.RESULTSORTID,a.TBSJ=t.TBSJ,a.XGSJ=t.XGSJ,a.SORTID=t.SORTID,"
						+ "a.A0194=t.A0194,a.A0104A=t.A0104A,a.A0111=t.A0111,a.A0114=t.A0114,a.A0117A=t.A0117A,a.A0128B=t.A0128B,"
						+ "a.A0141D=t.A0141D,a.A0144B=t.A0144B,a.A0144C=t.A0144C,a.A0148=t.A0148,a.A0148C=t.A0148C,a.A0149=t.A0149,"
						+ "a.A0151=t.A0151,a.A0153=t.A0153,a.A0157=t.A0157,a.A0158=t.A0158,a.A0159=t.A0159,a.A015A=t.A015A,a.A0161=t.A0161,"
						+ "a.A0162=t.A0162,a.A0180=t.A0180,a.A0191=t.A0191,a.A0192B=t.A0192B,a.A0193=t.A0193,a.A0194U=t.A0194U,"
						+ "a.A0198=t.A0198,a.A0101=t.A0101,a.A0102=t.A0102,a.A0104=t.A0104,a.A0107=t.A0107,"
						+ "a.A0111A=t.A0111A,a.A0114A=t.A0114A,a.A0115A=t.A0115A,a.A0117=t.A0117,a.A0128=t.A0128,a.A0134=t.A0134,"
						+ "a.A0140=t.A0140,a.A0141=t.A0141,a.A0144=t.A0144,a.A3921=t.A3921,a.A3927=t.A3927,a.A0160=t.A0160,a.A0163=t.A0163,"
						+ "a.A0165=t.A0165,a.A0184=t.A0184,a.A0187A=t.A0187A,a.A0192=t.A0192,a.A0192A=t.A0192A,a.A0221=t.A0221,"
						+ "a.A0288=t.A0288,a.A0192D=t.A0192D,a.A0192C=t.A0192C,a.A0196=t.A0196,a.A0197=t.A0197,a.A0195=t.A0195,"
						+ "a.A1701=t.A1701,a.A14Z101=t.A14Z101,a.A15Z101=t.A15Z101,a.A0120=t.A0120,a.A0121=t.A0121,a.A2949=t.A2949,"
						+ "a.A0122=t.A0122,a.a0192f=t.a0192f,a.ZGXL=t.ZGXL,a.ZGXW=t.ZGXW,a.ZGXLXX=t.ZGXLXX,a.ZGXWXX=t.ZGXWXX ,a.TCSJSHOW=t.TCSJSHOW,a.TCFSSHOW=t.TCFSSHOW,a.fsj=t.fsj,a.a0123=t.a0123,a.A15Z191=t.A15Z191" +
						"  WHEN NOT MATCHED THEN INSERT" +
						" (a.A0192E, a.A0199, a.A01K01, a.A01K02, a.CBDRESULT, a.CBDW, a.ISVALID, a.NL, a.NMZW, a.NRZW, a.ORGID, a.QRZXL, "
						+ "a.QRZXLXX, a.QRZXW, a.QRZXWXX, a.RMLY, a.STATUS, a.TBR, a.TBRJG, a.USERLOG, a.XGR, a.ZZXL, a.ZZXLXX, a.ZZXW, "
						+ "a.ZZXWXX, a.A0155, a.AGE, a.JSNLSJ, a.RESULTSORTID, a.TBSJ, a.XGSJ, a.SORTID, a.A0194, a.A0104A, a.A0111, "
						+ "a.A0114, a.A0117A, a.A0128B, a.A0141D, a.A0144B, a.A0144C, a.A0148, a.A0148C, a.A0149, a.A0151, a.A0153, "
						+ "a.A0157, a.A0158, a.A0159, a.A015A, a.A0161, a.A0162, a.A0180, a.A0191, a.A0192B, a.A0193, a.A0194U, "
						+ "a.A0198, a.A0000, a.A0101, a.A0102, a.A0104, a.A0107, a.A0111A, a.A0114A, a.A0115A, a.A0117, a.A0128, "
						+ "a.A0134, a.A0140, a.A0141, a.A0144, a.A3921, a.A3927, a.A0160, a.A0163, a.A0165, a.A0184, a.A0187A, a.A0192, "
						+ "a.A0192A, a.A0221, a.A0288, a.A0192D, a.A0192C, a.A0196, a.A0197, a.A0195, a.A1701, a.A14Z101, a.A15Z101,"
						+ " a.A0120, a.A0121, a.A2949, a.A0122, a.a0192f, a.ZGXL, a.ZGXW, a.ZGXLXX, a.ZGXWXX,a.TCSJSHOW,a.TCFSSHOW,a.fsj,a.a0123,a.A15Z191,a.A0192X)" +
						" VALUES (t.A0192E, t.A0199, t.A01K01, t.A01K02, t.CBDRESULT, t.CBDW, t.ISVALID, t.NL, t.NMZW, t.NRZW, t.ORGID,"
						+ " t.QRZXL, t.QRZXLXX, t.QRZXW, t.QRZXWXX, t.RMLY, t.STATUS, t.TBR, t.TBRJG, t.USERLOG, t.XGR, t.ZZXL, t.ZZXLXX, "
						+ "t.ZZXW, t.ZZXWXX, t.A0155, t.AGE, t.JSNLSJ, t.RESULTSORTID, t.TBSJ, t.XGSJ, t.SORTID, t.A0194, t.A0104A, t.A0111, "
						+ "t.A0114, t.A0117A, t.A0128B, t.A0141D, t.A0144B, t.A0144C, t.A0148, t.A0148C, t.A0149, t.A0151, t.A0153, t.A0157,"
						+ " t.A0158, t.A0159, t.A015A, t.A0161, t.A0162, t.A0180, t.A0191, t.A0192B, t.A0193, t.A0194U, t.A0198, t.A0000,"
						+ " t.A0101, t.A0102, t.A0104, t.A0107, t.A0111A, t.A0114A, t.A0115A, t.A0117, t.A0128, t.A0134, t.A0140, t.A0141, "
						+ "t.A0144, t.A3921, t.A3927, t.A0160, t.A0163, t.A0165, t.A0184, t.A0187A, t.A0192, t.A0192A, t.A0221, t.A0288,"
						+ " t.A0192D, t.A0192C, t.A0196, t.A0197, t.A0195, t.A1701, t.A14Z101, t.A15Z101, t.A0120, t.A0121, t.A2949, "
						+ "t.A0122,t.a0192f,t.ZGXL,t.ZGXW,t.ZGXLXX,t.ZGXWXX,t.TCSJSHOW,t.TCFSSHOW,t.fsj,t.a0123,t.A15Z191,t.A0192X ) ")).executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				
				//b01    //update 更新temp任职机构id 上级id 本级id
				if(!imptype.equals("4")){
					new Thread(new ImpModLogThread("B01", "接收B01数据", sortid++, optype, imprecordid, false)).start();
					//delete 
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					//insert
					CommonQueryBS.systemOut("接收B01数据time---------" + DateUtil.getTime());
					if(impno.equals("2")){
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("MERGE INTO b01 a USING b01"+imptemptable+" t ON (a.b0111 = t.b0111) WHEN NOT MATCHED THEN INSERT" +
								"(b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,b0140,b0141,b0142,b0143,b0150,b0180," +
								" b0191,b0194,b01trans,b01ip,b0227,b0232,b0233,sortid,used,updated,create_user," +
								"create_date,update_user,update_date,status,b0238,b0239,b0234,b0236,b0183,b0185,b0164,b0167,b0268,b0269,b01id)"+
								" VALUES (t.b0101,t.b0104,t.b0107,t.b0111,t.b0114,t.b0117,t.b0121,t.b0124,t.b0127,t.b0131,t.b0140,t.b0141,t.b0142,t.b0143,t.b0150,t.b0180," +
								" t.b0191,t.b0194,t.b01trans,t.b01ip,t.b0227,t.b0232,t.b0233,t.sortid,t.used,t.updated,t.create_user," +
								"t.create_date,t.update_user,t.update_date,t.status,t.b0238,t.b0239,t.b0234,b0236,b0183,b0185,b0164,b0167,b0268,b0269,b01id) ")).executeUpdate();
						
					} else {
						//处理首选机构的机构排序
						Object obj = sess.createSQLQuery(CommonQueryBS.sysOutRtn("select sortid from b01 where b0111 = '"+ imp.getImpdeptid() +"'")).uniqueResult();
						
						sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from b01 where b0111 in ("+borgs+") and b0111 !='-1'")).executeUpdate();
						sess.createSQLQuery(CommonQueryBS.sysOutRtn("  MERGE INTO b01 a USING b01"+imptemptable+" t ON (a.b0111 = t.b0111)  WHEN MATCHED THEN UPDATE " +
								"SET a.B0235=t.B0235,a.B0236=t.B0236,a.B0101=t.B0101,a.B0104=t.B0104,a.B0107=t.B0107,a.B0114=t.B0114,a.B0117=t.B0117,"
								+ "a.B0121=t.B0121,a.B0124=t.B0124,a.B0127=t.B0127,a.B0131=t.B0131,a.B0140=t.B0140,a.B0141=t.B0141,a.B0142=t.B0142,"
								+ "a.B0143=t.B0143,a.B0150=t.B0150,a.B0180=t.B0180,a.B0183=t.B0183,a.B0185=t.B0185,a.B0188=t.B0188,a.B0189=t.B0189,"
								+ "a.B0190=t.B0190,a.B0191=t.B0191,a.B0191A=t.B0191A,a.B0192=t.B0192,a.B0193=t.B0193,a.B0194=t.B0194,a.B01TRANS=t.B01TRANS,"
								+ "a.B01IP=t.B01IP,a.B0227=t.B0227,a.B0232=t.B0232,a.B0233=t.B0233,a.SORTID=t.SORTID,a.USED=t.USED,a.UPDATED=t.UPDATED,"
								+ "a.CREATE_USER=t.CREATE_USER,a.CREATE_DATE=t.CREATE_DATE,a.UPDATE_USER=t.UPDATE_USER,a.UPDATE_DATE=t.UPDATE_DATE,"
								+ "a.STATUS=t.STATUS,a.B0238=t.B0238,a.B0239=t.B0239,a.B0234=t.B0234,a.B0132=t.B0132,a.B0164=t.B0164,a.B0167=t.B0167,a.B0268=t.B0268,a.B0269=t.B0269,a.B01ID=t.B01ID " +
								"  WHEN NOT MATCHED THEN INSERT" +
								"(a.B0235,a.B0236,a.B0101,a.B0104,a.B0107,a.B0111,a.B0114,a.B0117,a.B0121,a.B0124,a.B0127,a.B0131,a.B0140,a.B0141,a.B0142,a.B0143,"
								+ "a.B0150,a.B0180,a.B0183,a.B0185,a.B0188,a.B0189,a.B0190,a.B0191,a.B0191A,a.B0192,a.B0193,a.B0194,a.B01TRANS,a.B01IP,a.B0227,"
								+ "a.B0232,a.B0233,a.SORTID,a.USED,a.UPDATED,a.CREATE_USER,a.CREATE_DATE,a.UPDATE_USER,a.UPDATE_DATE,a.STATUS,a.B0238,a.B0239,a.B0234,a.B0132,a.B0164,a.B0167,a.B0268,a.B0269,a.B01ID)"+
								" VALUES (t.B0235,t.B0236,t.B0101,t.B0104,t.B0107,t.B0111,t.B0114,t.B0117,t.B0121,t.B0124,t.B0127,t.B0131,t.B0140,t.B0141,"
								+ "t.B0142,t.B0143,t.B0150,t.B0180,t.B0183,t.B0185,t.B0188,t.B0189,t.B0190,t.B0191,t.B0191A,t.B0192,t.B0193,t.B0194,"
								+ "t.B01TRANS,t.B01IP,t.B0227,t.B0232,t.B0233,t.SORTID,t.USED,t.UPDATED,t.CREATE_USER,t.CREATE_DATE,t.UPDATE_USER,"
								+ "t.UPDATE_DATE,t.STATUS,t.B0238,t.B0239,t.B0234,t.B0132,t.B0164,t.B0167,t.B0268,t.B0269,t.B01ID) ")).executeUpdate();
						
						//更新首选机构的机构排序
						if(obj!=null && !"".equals(obj.toString().trim())){
							sess.createSQLQuery(CommonQueryBS.sysOutRtn("update b01 b set b.SORTID = "+obj.toString()+" where b.b0111 = '"+imp.getImpdeptid()+"'")).executeUpdate();
						}
					}
					
					
					/**
					 * zxw - b0111 like '"+impdeptid+"%' 性能优化 改为：substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"'
					 * 
					 * 首先给系统管理员用户赋权，其次给当前用户授权。
					 * 其中不为普通用户授权，是要管理元用户重新授权
					 * 
					 * impdeptid：当前导入最高节点 - 新的 -机构编码
					 * 
					 * 新的权限控制方式：
					 * 		设置开关，通过开关控制，是否进行数据导入时，权限重置操作；按钮放置系统参数配置
					 */
					
					
					sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from COMPETENCE_USERDEPT where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and userid='"+user.getId()+"'")).executeUpdate();
					
					Connection conn = sess.connection();
					Statement stmt = conn.createStatement();
					
					//查询出所有导入时，待授权的机构
					ResultSet rs = stmt.executeQuery(CommonQueryBS.sysOutRtn("select b0111 from b01 where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"'"));
					//查询出所有待授权的管理员用户
//					String sql="select distinct c.USERID from COMPETENCE_USERDEPT c left join smt_user u on c.USERID = u.userid "
//							+ "where substr(c.b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and u.USERTYPE = '1'";
//					List<String> list = sess.createSQLQuery(sql).list();
					CommQuery commQuery = new CommQuery();
					List<HashMap<String,Object>> list = commQuery.getListBySQL(CommonQueryBS.sysOutRtn("select userid,otherinfo from smt_user where substr(otherinfo, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and USERTYPE = '1'"));
					if(list!=null && list.size() > 0){
						int len = list.size();
						for (int k = 0; k < len; k++) {
							sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from COMPETENCE_USERDEPT where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and userid='"+list.get(k).get("userid")+"'")).executeUpdate();
						}
						
						PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
						String otherinfo = "", b0111 = "";
						int i = 0;
						if (rs != null){
							while (rs.next()) {//循环机构
								
								b0111 = rs.getString(1) + "";
								for (int k = 0; k < len; k++) {//循环用户
									otherinfo = list.get(k).get("otherinfo") + "";
									if(b0111.length() < otherinfo.length() //首先比较长度比较，导入机构码小于用户机构码，跳过
											|| !otherinfo.equals(b0111.substring(0, otherinfo.length()))){//其次截取比较，导入机构码截取用户机构码
										continue;//判断当前用户是否存在该机构权限
									}
									pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
									//pstmt1.setString(2, user.getId());
									pstmt1.setString(2, list.get(k).get("userid")+"");
									pstmt1.setString(3, b0111);
									pstmt1.setString(4, "1");
									pstmt1.addBatch();
									i++;
									if(i%10000 == 0){
										pstmt1.executeBatch();
										pstmt1.clearBatch();
									}
								}
								
							}
							pstmt1.executeBatch();
							pstmt1.clearBatch();
						}
						
						rs.close();
						pstmt1.close();
						stmt.close();
						conn.close();
					}/*else{		//空库时需要给当前用户机构授权
						
					}*/
					
					//当前用户机构授权
					//sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
					
					Connection conn2 = sess.connection();
					Statement stmt2 = conn2.createStatement();
					PreparedStatement pstmt2 = conn2.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
					//查询出所有导入时，待授权的机构
					ResultSet rs2 = stmt2.executeQuery(CommonQueryBS.sysOutRtn("select b0111 from b01 where substr(b0111, 1, length('"+impdeptid+"')) = '"+impdeptid+"' and "
							// zxw 2019-01-25 去除重复插入的数据
							+ "b0111 not in (select b0111 from COMPETENCE_USERDEPT where userid='"+user.getId()+"')"));
					
					int i = 0;
					if (rs2 != null){
						while (rs2.next()) {
							pstmt2.setString(1, UUID.randomUUID().toString().replace("-", ""));
							pstmt2.setString(2, user.getId());
							pstmt2.setString(3, rs2.getString(1));
							pstmt2.setString(4, "1");
							pstmt2.addBatch();
							i++;
							if(i%10000 == 0){
								pstmt2.executeBatch();
								pstmt2.clearBatch();
							}
						}
						pstmt2.executeBatch();
						pstmt2.clearBatch();
					}
					rs2.close();
					pstmt2.close();
					stmt2.close();
					conn2.close();
					/*
					 * //浏览树授权------> Connection conn3 = sess.connection(); Statement stmt3 =
					 * conn3.createStatement(); PreparedStatement pstmt3 =
					 * conn3.prepareStatement("insert into COMPETENCE_USERDEPT_LOOK values(?,?,?,?)"
					 * ); //查询出所有导入时，待授权的机构 ResultSet rs3 = stmt3.executeQuery(CommonQueryBS.
					 * sysOutRtn("select b0111 from b01 where substr(b0111, 1, length('"
					 * +impdeptid+"')) = '"+impdeptid+"' and " // zxw 2019-01-25 去除重复插入的数据 +
					 * "b0111 not in (select b0111 from COMPETENCE_USERDEPT_LOOK where userid='"
					 * +user.getId()+"')"));
					 * 
					 * int j = 0; if (rs3 != null){ while (rs3.next()) { pstmt3.setString(1,
					 * UUID.randomUUID().toString().replace("-", "")); pstmt3.setString(2,
					 * user.getId()); pstmt3.setString(3, rs3.getString(1)); pstmt3.setString(4,
					 * "1"); pstmt3.addBatch(); j++; if(j%10000 == 0){ pstmt3.executeBatch();
					 * pstmt3.clearBatch(); } } pstmt3.executeBatch(); pstmt3.clearBatch(); }
					 * rs3.close(); pstmt3.close(); stmt3.close(); conn3.close();
					 */
					
				}
				
				//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				new Thread(new ImpModLogThread("A02", "接收A02数据", sortid++, optype, imprecordid, false)).start();
				if(!imptype.equals("4")){
					//update 更新temp任职机构id
					//delete 
//					sess.createSQLQuery(" delete from a02 where  A0000 in (select a0000 from a01 where "+a0165sql+" and ((status='3' OR status='2' ) and ORGID like '"+ impdeptid +"%') union select a0000 from a01 where "+a0165sql+" and (status='1' and a0000 in (select a0000 from a02 where A0255='1' and  a0201b like '"+ impdeptid +"%' )))").executeUpdate();
					sess.createSQLQuery(" delete from a02 a2 where a2.A0000 in ("+A0000Sql+")").executeUpdate();
					sess.createSQLQuery(" delete from a02 where a02.A0000 not in (select a0000 from a01)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b  = t.a0201a  where t.a0201b not in (select b0111 from B01"+imptemptable+" )").executeUpdate();
				} else if("4".equals(imptype)){
					sess.createSQLQuery(" delete from a02 a2 where  a2.A0000 in ("+A0000Sql+")").executeUpdate();
					sess.createSQLQuery(" delete from a02 where a02.A0000 not in (select a0000 from a01)").executeUpdate();
					sess.createSQLQuery("update B01"+imptemptable+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)").executeUpdate();
					//sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b =(select b.psnb0111 from b01"+imptemptable+" b where b.b0111=t.a0201b),t.ERROR_INFO ='1' where  exists (select 1 from b01"+imptemptable+" b where b.b0111=t.a0201b)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b =t.a0201a where (ERROR_INFO='2' OR a0201b is null)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b  = t.a0201a  where t.a0201b not in (select b0111 from B01 )").executeUpdate();
				}
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//insert
				
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" MERGE INTO a02 a USING a02"+imptemptable+" t ON (a.a0200 = t.a0200)  WHEN MATCHED THEN UPDATE" +
						" SET a.A0000=t.A0000,a.A0201A=t.A0201A,a.A0201B=t.A0201B,a.A0201D=t.A0201D,a.A0201E=t.A0201E,a.A0215A=t.A0215A,"
						+ "a.A0219=t.A0219,a.A0223=t.A0223,a.A0225=t.A0225,a.A0243=t.A0243,a.A0245=t.A0245,a.A0247=t.A0247,a.A0251B=t.A0251B,"
						+ "a.A0255=t.A0255,a.A0265=t.A0265,a.A0267=t.A0267,a.A0272=t.A0272,a.A0281=t.A0281,a.A0221T=t.A0221T,a.B0238=t.B0238,"
						+ "a.B0239=t.B0239,a.A0221A=t.A0221A,a.WAGE_USED=t.WAGE_USED,a.UPDATED=t.UPDATED,a.A4907=t.A4907,a.A4904=t.A4904,"
						+ "a.A4901=t.A4901,a.A0299=t.A0299,a.A0295=t.A0295,a.A0289=t.A0289,a.A0288=t.A0288,a.A0284=t.A0284,a.A0277=t.A0277,"
						+ "a.A0271=t.A0271,a.A0259=t.A0259,a.A0256C=t.A0256C,a.A0256B=t.A0256B,a.A0256A=t.A0256A,a.A0256=t.A0256,a.A0251=t.A0251,"
						+ "a.A0229=t.A0229,a.A0222=t.A0222,a.A0221W=t.A0221W,a.A0221=t.A0221,a.A0219W=t.A0219W,a.A0216A=t.A0216A,a.A0215B=t.A0215B,"
						+ "a.A0209=t.A0209,a.A0207=t.A0207,a.A0204=t.A0204,a.A0201C=t.A0201C,a.A0201=t.A0201,a.A0279=t.A0279 " +
						"  WHEN NOT MATCHED THEN INSERT" +
						"(a.A0000,a.A0200,a.A0201A,a.A0201B,a.A0201D,a.A0201E,a.A0215A,a.A0219,a.A0223,a.A0225,a.A0243,a.A0245,a.A0247,a.A0251B,"
						+ "a.A0255,a.A0265,a.A0267,a.A0272,a.A0281,a.A0221T,a.B0238,a.B0239,a.A0221A,a.WAGE_USED,a.UPDATED,a.A4907,a.A4904,a.A4901,"
						+ "a.A0299,a.A0295,a.A0289,a.A0288,a.A0284,a.A0277,a.A0271,a.A0259,a.A0256C,a.A0256B,a.A0256A,a.A0256,a.A0251,a.A0229,"
						+ "a.A0222,a.A0221W,a.A0221,a.A0219W,a.A0216A,a.A0215B,a.A0209,a.A0207,a.A0204,a.A0201C,a.A0201,a.A0279)"+
						" VALUES (t.A0000,t.A0200,t.A0201A,t.A0201B,t.A0201D,t.A0201E,t.A0215A,t.A0219,t.A0223,t.A0225,t.A0243,t.A0245,t.A0247,"
						+ "t.A0251B,t.A0255,t.A0265,t.A0267,t.A0272,t.A0281,t.A0221T,t.B0238,t.B0239,t.A0221A,t.WAGE_USED,t.UPDATED,t.A4907,"
						+ "t.A4904,t.A4901,t.A0299,t.A0295,t.A0289,t.A0288,t.A0284,t.A0277,t.A0271,t.A0259,t.A0256C,t.A0256B,t.A0256A,t.A0256,"
						+ "t.A0251,t.A0229,t.A0222,t.A0221W,t.A0221,t.A0219W,t.A0216A,t.A0215B,t.A0209,t.A0207,t.A0204,t.A0201C,t.A0201,t.A0279)" +
						" ")).executeUpdate();
			}
			
			CommonQueryBS.systemOut("time---------" + DateUtil.getTime()); 
			new Thread(new ImpModLogThread("A01", "更新A01排序字段", sortid++, optype, imprecordid, false)).start();
			//更新A01的排序字段
			if(!imptype.equals("4")){
				if(DBUtil.getDBType().equals(DBType.ORACLE)){
					String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
					String torgidSql = "create table torgid"+tableExt+"(A0000 VARCHAR2(120),TORGID VARCHAR2(200))";
					sess.createSQLQuery(torgidSql.toUpperCase()).executeUpdate();
					sess.createSQLQuery(CommonQueryBS.sysOutRtn("insert into torgid"+tableExt+" SELECT W.A0000,W.A0201B FROM (SELECT ROW_NUMBER () OVER (PARTITION BY V.a0000 ORDER BY B01.SORTID DESC) rn,A02.A0000,"
							+ "A02.A0201B FROM (SELECT A01.A0000,MIN (LENGTH(A0201B)) minlength FROM a02,a01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0201B LIKE '"+impdeptid+"%'"
							+ "GROUP BY A01.A0000) V,A02,B01 WHERE V.A0000 = A02.A0000 AND B01.B0111 = A02.A0201B AND A02.A0281 = 'true' AND LENGTH (A02.A0201B) "
							+ "= V.minlength) W WHERE W.RN = 1")).executeUpdate();
					sess.createSQLQuery("Create index tid" + tableExt + " on torgid" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE a01 SET A01.TORGID = (SELECT t.TORGID FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
					sess.createSQLQuery("drop table torgid"+tableExt).executeUpdate();
					
					sess.createSQLQuery("create table torder"+tableExt+"(A0000 VARCHAR2(120),TORDER VARCHAR2(8))").executeUpdate();
					sess.createSQLQuery("insert into torder"+tableExt+" SELECT a01.a0000,LPAD (MAX(a02.a0225), 5, 0) FROM a02,a01 WHERE a01.a0000 = a02.a0000 "
							+ "AND a02.a0281 = 'true' AND a01.torgid = a02.a0201b AND a01.torgid LIKE '"+impdeptid+"%' GROUP BY a01.a0000").executeUpdate();
					sess.createSQLQuery("Create index tder" + tableExt + " on torder" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE a01 SET A01.torder = (SELECT t.torder FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
					sess.createSQLQuery("drop table torder"+tableExt).executeUpdate();
				}else{
					HBUtil.executeUpdate("UPDATE a01 SET A01.TORGID = GET_TORGID (A01.A0000) WHERE EXISTS (SELECT 1 FROM a02 WHERE A02.a0000 = A01.A0000 AND A02.A0201B LIKE '"+impdeptid+"%')");
					HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE A01.TORGID LIKE '"+impdeptid+"%'");
				}
				//HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE A01.TORGID LIKE '"+impdeptid+"%'");  
				//HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE SUBSTR(A01.TORGID, 1, length('"+impdeptid+"')) = '"+impdeptid+"'");  
				
			}else if("4".equals(imptype)){
				HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 in (SELECT t.a0000 FROM a01"+imptemptable+" t)");
				HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 in (SELECT t.a0000 FROM a01"+imptemptable+" t)");
			}
//			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
//					"A31","A36","A37","A41", "A53","A57", "B01", "B01_EXT", "INFO_EXTEND"};
			new Thread(new ImpModLogThread("A15", "更新A15选择年度个数字段", sortid++, optype, imprecordid, false)).start();
			HBUtil.executeUpdate("update a15 set a1527='3' WHERE (a1527 IS NULL OR a1527 = '' )");
			HBUtil.executeUpdate("update a01 set A15Z191='3' where (A15Z191 is null or A15Z191='')");
			HBUtil.executeUpdate("update a01 set A0191='1'  WHERE a0191 IS NULL OR a0191 = ''");
			
			if("zip".equals(ftype)) {
				new Thread(new ImpModLogThread("A01", "更新A01入党时间", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("更新入党时间文字开始...");
				try {
					HBUtil.executeUpdate("update a01 set a0140=concat(concat(substr(a0144,1,4),'.'),substr(a0144,5,2)) "
							+ "where a0141='01' and a0140 is null and (a0144 is not null or a0144 <> '')");
					CommonQueryBS.systemOut("党员部分入党时间文字更新完成！");
					HBUtil.executeUpdate("update a01 set a0140=concat(concat(substr(a0144,1,4),'.'),substr(a0144,5,2)) "
							+ "where a0141='02' and a0140 is null and (a0144 is not null or a0144 <> '')");
					CommonQueryBS.systemOut("预备党员部分入党时间文字更新完成！");
					HBUtil.executeUpdate("update a01 set a0140=concat(concat('(',(select code_name from code_value where code_type ='GB4762' and code_value =a0141)),')') "
							+ "where a0140 is null and a0141 in (select code_value from code_value where code_type ='GB4762' and code_value not in ('01','02'))");
					CommonQueryBS.systemOut("其他成员入党时间文字更新完成！");
				} catch (Exception e) {
					e.printStackTrace();
					CommonQueryBS.systemOut("更新失败！");
				}
				CommonQueryBS.systemOut("更新入党时间文字更新完成！");
				new Thread(new ImpModLogThread("A01", "更新A01退出人员", sortid++, optype, imprecordid, false)).start();
				HBUtil.executeUpdate("update a01 set a01.TCSJSHOW = (select a3004 from a30 where a30.a0000 = a01.a0000),"+
				" a01.TCFSSHOW = (select code_name from code_value where code_type = 'ZB78' and code_value =(select a3001 from a30 where a30.a0000 = a01.a0000))"+
				" where exists (select 1 from a30 where a30.a0000 = a01.a0000)");
			}
			new Thread(new ImpModLogThread("A01", "更新A01学历学位信息", sortid++, optype, imprecordid, false)).start();
			String QrzxlxxNum = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where  QRZXLXX not like '%专业' and (QRZXLXX is not null or QRZXLXX <> '')").uniqueResult().toString();
			if(!"0".equals(QrzxlxxNum)) {
				HBUtil.executeUpdate("update A01 set QRZXLXX=CONCAT(QRZXLXX,'专业') where  QRZXLXX not like '%专业' and (QRZXLXX is not null or QRZXLXX <> '') ");
			}
			String QrzxwxxNum = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where  QRZXWXX not like '%专业' and (QRZXWXX is not null or QRZXWXX <> '') ").uniqueResult().toString();
			if(!"0".equals(QrzxwxxNum)) {
				HBUtil.executeUpdate("update A01 set QRZXWXX=CONCAT(QRZXWXX,'专业') where  QRZXWXX not like '%专业' and (QRZXWXX is not null or QRZXWXX <> '') ");
			}
			String ZzxlxxNum = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where  ZZXLXX not like '%专业' and (ZZXLXX is not null or ZZXLXX <> '')").uniqueResult().toString();
			if(!"0".equals(ZzxlxxNum)) {
				HBUtil.executeUpdate("update A01 set ZZXLXX=CONCAT(ZZXLXX,'专业') where  ZZXLXX not like '%专业' and (ZZXLXX is not null or ZZXLXX <> '') ");
			}
			String ZzxwxxNum = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where  ZZXWXX not like '%专业' and (ZZXWXX is not null or ZZXWXX <> '')").uniqueResult().toString();
			if(!"0".equals(ZzxwxxNum)) {
				HBUtil.executeUpdate("update A01 set ZZXWXX=CONCAT(ZZXWXX,'专业') where  ZZXWXX not like '%专业' and (ZZXWXX is not null or ZZXWXX <> '') ");
			}
			
			
			imp.setImpstutas("2");
			sess.update(imp);
			sess.getTransaction().commit();
			new Thread(new ImpModLogThread("PHOTO", "迁移图片", sortid++, optype, imprecordid, false)).start();
			String photo_path = "";
			String fName = "";
			/*if(imp.getFiletype().equalsIgnoreCase("zip")){
				String p = AppConfig.HZB_PATH + "/temp/upload/" +imprecordid +"/" + imprecordid;
				File file = new File(p);
				File[] subs = file.listFiles();
				File f = subs[0];
				fName = f.getName();
				photo_path = p + "/" + fName + "/Photos/";
			}else{*/
				photo_path = AppConfig.HZB_PATH + "/temp/upload/unzip/"+imprecordid +"/Photos/";
			/*}*/
			File photos = new File(photo_path);
			
			if(photos.exists() && photos.isDirectory()){
				PhotosUtil.moveIMPCmd(imprecordid,photo_path);
				PhotosUtil.moveIMPOtherCmd(imprecordid,photo_path);
				PhotosUtil.removDirImpCmd(imprecordid,fName);
				new Thread(new ImpModLogThread("DONE", "迁移完成", sortid++, optype, imprecordid, true)).start();
				sess.flush();
			} else {
				new Thread(new ImpModLogThread("DONE", "完成:导入包未发现图片或临时图片被删除", sortid++, optype, imprecordid, true)).start();
				sess.flush();
			}

			if((imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip"))&&!"4".equals(imptype)){
				String tables1[] = {"A01", "A02", "A05", "A06", "A08", "A14", "A15", "A36", "A57", "B01", "A99Z1","A30","A33","A65"};
//				new Thread(new ImpModLogThread("ALL", "删除临时数据", sortid++, optype, imprecordid, false)).start();
				for (int i = 0; i < tables1.length; i++) {
					sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
					
				}
			}else{
				String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
							"A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64", "B01", "B_E", "I_E"
							,"A05", "A68", "A69", "A71", "A99Z1", "A65","A33"};
//				new Thread(new ImpModLogThread("ALL", "删除临时数据", sortid++, optype, imprecordid, false)).start();
				for (int i = 0; i < tables1.length; i++) {
					try {
						sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
					} catch (Exception e) {
						
					}
				}
				
			}
			
			if(A0165!=null && !A0165.equals("")){
				sess.createSQLQuery(" drop table a"+imptemptable+"a01").executeUpdate();
			}
			sess.createSQLQuery(" drop table aa"+imptemptable+"a01").executeUpdate();
			sess.createSQLQuery(" delete from verify_process where batch_num like '"+ impdeptid +"%' and result_flag in ('0','1')").executeUpdate();
			try {
				if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zb3")) {
					new LogUtil("463", "IMP_RECORD", "", "", "导入应用库", new ArrayList(),userVo).start();
				} else if (ftype.equalsIgnoreCase("zzb3")){
					new LogUtil("473", "IMP_RECORD", "", "", "导入应用库", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//			new Thread(new DeleteTempThread(imprecordid)).start();
//			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				new Thread(new ImpModLogThread("DONE", "迁移异常", sortid++, optype, imprecordid, true)).start();
//				new ImpModLogThread("DONE", "迁移异常", sortid++, optype, imprecordid, false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(sess!=null){
				try {
					sess.getTransaction().rollback();
					imp.setImpstutas("1");
					sess.update(imp);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			//最后待写
			SysRuleBS.bcUserdept();//zxw 补充未在SYSTEM用户下的编辑机构
			SysRuleBS.bcUserdeptTwo();//zxw 补充未在SYSTEM用户下的浏览机构
			DBUtil.MaintenanceAuthorityFunc();//zxw 执行权限控制操作 - 防止权限越界
			//执行重新生成学位学历......
			SysRuleBS.calls(a0000List);
			if(sess != null){
				sess.close();
			}
		}
		
	}
	@SuppressWarnings({ "unused", "unchecked" })
	private void listAssociation(String a0000Str) {
		String a0000 = a0000Str;
		String a1527 = "3";//选择年度个数
		try {
			HBUtil.executeUpdate("update a15 set a1527='"+a1527+"' where a0000='"+a0000+"'");
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "from A15 where a0000='"+a0000+"' order by a1521 asc";
		List<A15> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		//List<HashMap<String, Object>> list = this.getPageElement("AssessmentInfoGrid").getValueList();
		if(list!=null&&list.size()>0){
			int years = "".equals(a1527)?list.size():Integer.valueOf(a1527);
			if(years>list.size()){
				years = list.size();
			}
			StringBuffer desc = new StringBuffer("");
			for(int i=list.size()-years;i<list.size();i++){
				A15 a15 = list.get(i);
				//考核年度
				String a1521 = a15.getA1521();
				//考核结果
				String a1517 = a15.getA1517();
				String a1517Name ="";
				try {
					a1517Name = HBUtil.getCodeName("ZB18",a1517);
				} catch (AppException e) {
					e.printStackTrace();
				}
				desc.append(a1521+"年年度考核"+a1517Name+"；");
			}
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "。");
			}
			
			a01.setA15z101(desc.toString());
			a01.setA0191("1");
			sess.update(a01);
		}
	}

	/**检查索引创建索引，索引对应如下; 若 imptemptable 为af20150111， 则对应A01表为A01af20150111表
	 * A01(A01af20150111).a0000------a0000A01af20150111
	 * A02(A02af20150111).a0200------a0200A02af20150111
	 * 
	 * @param imptemptable
	 */
	public static void checkIndex(String imptemptable,String[] tables) {
		HBSession sess = HBUtil.getHBSession();
		MakeTables hasTables = new MakeTables();
		try {
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				CommonQueryBS.systemOut("index...");
				try {
					
					if (hasTables.hasTable(tables,"A01")){
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0000A01" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0101A01" + imptemptable + "(A0101)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0104A01" + imptemptable + "(A0104)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0107A01" + imptemptable + "(A0107)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0134A01" + imptemptable + "(A0134)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0144A01" + imptemptable + "(A0144)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0148A01" + imptemptable + "(A0148)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0163A01" + imptemptable + "(A0163)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0184A01" + imptemptable + "(A0184)").executeUpdate();
//						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A1701A01" + imptemptable + "(A1701)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0221A01" + imptemptable + "(A0221)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A02")){
						
						sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0000A02" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0200A02" + imptemptable + "(A0200)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0201BA02" + imptemptable + "(A0201B)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0255A02" + imptemptable + "(A0255)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A06")){
						
						sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0000A06" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0600A06" + imptemptable + "(A0600)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A08")){
						
						sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0000A08" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0800A08" + imptemptable + "(A0800)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0801BA08" + imptemptable + "(A0801B)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A11")){
						sess.createSQLQuery("ALTER TABLE A11" + imptemptable + " add index A0000A11" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A11" + imptemptable + " add index A1100A11" + imptemptable + "(A1100)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A11 的临时表索引创建成功！");
						
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A11 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A11 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A14")){
						
						sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A0000A14" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A1400A14" + imptemptable + "(A1400)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A1407A14" + imptemptable + "(A1407)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A15")){
						
						sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A0000A15" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A1500A15" + imptemptable + "(A1500)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A1517A15" + imptemptable + "(A1517)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A29")){
						sess.createSQLQuery("ALTER TABLE A29" + imptemptable + " add index A0000A29" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A29 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A29 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A29 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A30")){
						sess.createSQLQuery("ALTER TABLE A30" + imptemptable + " add index A0000A30" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A30 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A30 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A30 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A31")){
						sess.createSQLQuery("ALTER TABLE A31" + imptemptable + " add index A0000A31" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A31 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A31 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A31 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A36")){
						
						sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A0000A36" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A3600A36" + imptemptable + "(A3600)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A3604AA36" + imptemptable + "(A3604A)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A360736" + imptemptable + "(A3607)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A360136" + imptemptable + "(A3601)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A37")){
						sess.createSQLQuery("ALTER TABLE A37" + imptemptable + " add index A0000A37" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A37 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A37 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A37 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A41")){
						sess.createSQLQuery("ALTER TABLE A41" + imptemptable + " add index A0000A41" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A41" + imptemptable + " add index A4100A41" + imptemptable + "(A4100)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A41 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A41 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A41 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A53")){
						sess.createSQLQuery("ALTER TABLE A53" + imptemptable + " add index A0000A53" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("ALTER TABLE A53" + imptemptable + " add index A5300A53" + imptemptable + "(A5300)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A53 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A53 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A53 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A57")){
						sess.createSQLQuery("ALTER TABLE A57" + imptemptable + " add index A0000A57" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A60")){
						sess.createSQLQuery("ALTER TABLE A60" + imptemptable + " add index A0000A60" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A60 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A60 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A60 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A61")){
						sess.createSQLQuery("ALTER TABLE A61" + imptemptable + " add index A0000A61" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A61 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A61 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A61 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A62")){
						sess.createSQLQuery("ALTER TABLE A62" + imptemptable + " add index A0000A62" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A62 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A62的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A62 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A63")){
						sess.createSQLQuery("ALTER TABLE A63" + imptemptable + " add index A0000A63" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A63 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A63 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A63 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A64")){
						sess.createSQLQuery("ALTER TABLE A64" + imptemptable + " add index A6000A64" + imptemptable + "(A6400)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A64 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A64 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A64 的临时表不存在");
				}
				try {
					
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0111B01" + imptemptable + "(B0111)").executeUpdate();
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0117B01" + imptemptable + "(B0117)").executeUpdate();
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0121B01" + imptemptable + "(B0121)").executeUpdate();
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0131B01" + imptemptable + "(B0131)").executeUpdate();
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表索引创建成功！");
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
				/*try {
					if (tables!= null && tables.toString().contains("B_E"))
					sess.createSQLQuery("ALTER TABLE B_E" + imptemptable + " add index B0111B_E" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B_E 的临时表不存在");
				}*/
				/*
				try {
					if (tables!= null && tables.toString().contains("I_E"))
					sess.createSQLQuery("ALTER TABLE I_E" + imptemptable + " add index A0000I_E" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": I_E 的临时表不存在");
				} */
			
			} else {
				CommonQueryBS.systemOut("index...");
				try {
					if (hasTables.hasTable(tables,"A01")){

						sess.createSQLQuery("Create index A0000A01" + imptemptable + " on A01" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A0101A01" + imptemptable + " on A01" + imptemptable + "(A0101)").executeUpdate();
						sess.createSQLQuery("Create index A0104A01" + imptemptable + " on A01" + imptemptable + "(A0104)").executeUpdate();
						sess.createSQLQuery("Create index A0107A01" + imptemptable + " on A01" + imptemptable + "(A0107)").executeUpdate();
						sess.createSQLQuery("Create index A0134A01" + imptemptable + " on A01" + imptemptable + "(A0134)").executeUpdate();
						sess.createSQLQuery("Create index A0144A01" + imptemptable + " on A01" + imptemptable + "(A0144)").executeUpdate();
						sess.createSQLQuery("Create index A0148A01" + imptemptable + " on A01" + imptemptable + "(A0148)").executeUpdate();
						sess.createSQLQuery("Create index A0163A01" + imptemptable + " on A01" + imptemptable + "(A0163)").executeUpdate();
						sess.createSQLQuery("Create index A0184A01" + imptemptable + " on A01" + imptemptable + "(A0184)").executeUpdate();
//						sess.createSQLQuery("Create index A1701A01" + imptemptable + " on A01" + imptemptable + "(A1701)").executeUpdate();
						sess.createSQLQuery("Create index A0221A01" + imptemptable + " on A01" + imptemptable + "(A0221)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A02")){
						
						sess.createSQLQuery("Create index A0000A02" + imptemptable + " on A02" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A0200A02" + imptemptable + " on A02" + imptemptable + "(A0200)").executeUpdate();
						sess.createSQLQuery("Create index A0201BA02" + imptemptable + " on A02" + imptemptable + "(A0201B)").executeUpdate();
						sess.createSQLQuery("Create index A0255A02" + imptemptable + " on A02" + imptemptable + "(A0255)").executeUpdate();
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					
					
					if (hasTables.hasTable(tables,"A06")){
						
						sess.createSQLQuery("Create index A0000A06" + imptemptable + " on A06" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A0600A06" + imptemptable + " on A06" + imptemptable + "(A0600)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A08")){
						
						sess.createSQLQuery("Create index A0000A08" + imptemptable + " on A08" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A0800A08" + imptemptable + " on A08" + imptemptable + "(A0800)").executeUpdate();
						sess.createSQLQuery("Create index A0801BA08" + imptemptable + " on A08" + imptemptable + "(A0801B)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A11")){
						sess.createSQLQuery("Create index A0000A11" + imptemptable + " on A11" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A1100A11" + imptemptable + " on A11" + imptemptable + "(A1100)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A11 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A11 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A11 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A14")){
						
						sess.createSQLQuery("Create index A0000A14" + imptemptable + " on A14" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A1400A14" + imptemptable + " on A14" + imptemptable + "(A1400)").executeUpdate();
						sess.createSQLQuery("Create index A1407A14" + imptemptable + " on A14" + imptemptable + "(A1407)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A15")){
						
						sess.createSQLQuery("Create index A0000A15" + imptemptable + " on A15" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A1500A15" + imptemptable + " on A15" + imptemptable + "(A1500)").executeUpdate();
						sess.createSQLQuery("Create index A1517A15" + imptemptable + " on A15" + imptemptable + "(A1517)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A29")){
						sess.createSQLQuery("Create index A0000A29" + imptemptable + " on A29" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A29 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A29 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A29 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A30")){
						
						sess.createSQLQuery("Create index A0000A30" + imptemptable + " on A30" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A30 的临时表索引创建成功！");
				
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A30 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A30 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A31")){
						sess.createSQLQuery("Create index A0000A31" + imptemptable + " on A31" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A31 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A31 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					//CommonQueryBS.systemOut(new Date().toString()+": A31 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A36")){
						
						sess.createSQLQuery("Create index A0000A36" + imptemptable + " on A36" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A3600A36" + imptemptable + " on A36" + imptemptable + "(A3600)").executeUpdate();
						sess.createSQLQuery("Create index A3604AA36" + imptemptable + " on A36" + imptemptable + "(A3604A)").executeUpdate();
						sess.createSQLQuery("Create index A3607A36" + imptemptable + " on A36" + imptemptable + "(A3607)").executeUpdate();
						sess.createSQLQuery("Create index A3601A36" + imptemptable + " on A36" + imptemptable + "(A3601)").executeUpdate();
						
						CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表索引创建成功！");
					}else{
						CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A37")){
						sess.createSQLQuery("Create index A0000A37" + imptemptable + " on A37" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A37 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A37 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A37 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A41")){
						sess.createSQLQuery("Create index A0000A41" + imptemptable + " on A41" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A4100A41" + imptemptable + " on A41" + imptemptable + "(A4100)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A41 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A41 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A41 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A53")){
						sess.createSQLQuery("Create index A0000A53" + imptemptable + " on A53" + imptemptable + "(A0000)").executeUpdate();
						sess.createSQLQuery("Create index A5300A53" + imptemptable + " on A53" + imptemptable + "(A5300)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A53 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A53 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A53 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A57")){
						sess.createSQLQuery("Create index A0000A57" + imptemptable + " on A57" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A60")){
						sess.createSQLQuery("Create index A6000A60" + imptemptable + " on A60" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A60 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A60 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A60 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A61")){
						sess.createSQLQuery("Create index A0000A61" + imptemptable + " on A61" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A61 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A61 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A61 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A62")){
						sess.createSQLQuery("Create index A0000A62" + imptemptable + " on A62" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A62 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A62 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A62 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A63")){
						sess.createSQLQuery("Create index A0000A63" + imptemptable + " on A63" + imptemptable + "(A0000)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A63 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A63 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A63 的临时表不存在");
				}
				try {
					if (hasTables.hasTable(tables,"A64")){
						sess.createSQLQuery("Create index A6400A64" + imptemptable + " on A64" + imptemptable + "(A6400)").executeUpdate();
						CommonQueryBS.systemOut(new Date().toString()+": A64 的临时表索引创建成功！");
					}else{
						//CommonQueryBS.systemOut(new Date().toString()+": A64 的临时表索引创建失败...");
					}
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A64 的临时表不存在");
				}
				try {
					
					sess.createSQLQuery("Create index B0111B01" + imptemptable + " on B01" + imptemptable + "(B0111)").executeUpdate();
					sess.createSQLQuery("Create index B0117B01" + imptemptable + " on B01" + imptemptable + "(B0117)").executeUpdate();
					sess.createSQLQuery("Create index B0121B01" + imptemptable + " on B01" + imptemptable + "(B0121)").executeUpdate();
					sess.createSQLQuery("Create index B0131B01" + imptemptable + " on B01" + imptemptable + "(B0131)").executeUpdate();
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表索引创建成功！");
				
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
				/*try {
					sess.createSQLQuery("Create index B0111B_E" + imptemptable + " on B_E" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B_E 的临时表不存在");
				}*/
				/*try {
					sess.createSQLQuery("Create index A0000I_E" + imptemptable + " on I_E" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": I_E 的临时表不存在");
				}*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void checkIndex(String imptemptable) {
		HBSession sess = HBUtil.getHBSession();
		try {
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0000A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index TBRA01" + imptemptable + "(TBR)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index STATUSA01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0184A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0200A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0000A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0201BA02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0600A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0000A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0800A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0000A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0801BA08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A11" + imptemptable + " add index A1100A11" + imptemptable + "(A1100)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A11" + imptemptable + " add index A0000A11" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A1400A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A0000A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A1500A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A0000A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*try {
					sess.createSQLQuery("ALTER TABLE A29" + imptemptable + " add index A0000A29" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				try {
					sess.createSQLQuery("ALTER TABLE A30" + imptemptable + " add index A0000A30" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A31" + imptemptable + " add index A0000A31" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A3600A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A0000A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A37" + imptemptable + " add index A0000A37" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A41" + imptemptable + " add index A4100A41" + imptemptable + "(A4100)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A41" + imptemptable + " add index A0000A41" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A53" + imptemptable + " add index A5300A53" + imptemptable + "(A5300)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A53" + imptemptable + " add index A0000A53" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A57" + imptemptable + " add index A0000A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A60" + imptemptable + " add index A6000A60" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A61" + imptemptable + " add index A0000A61" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A62" + imptemptable + " add index A0000A62" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A63" + imptemptable + " add index A0000A63" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A64" + imptemptable + " add index A6400A64" + imptemptable + "(A6400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0111B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0131B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0121B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B_E" + imptemptable + " add index B0111B_E" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE I_E" + imptemptable + " add index A0000I_E" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} else {
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("Create index A0000A01" + imptemptable + " on A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0101A01" + imptemptable + " on A01" + imptemptable + "(A0101)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0104A01" + imptemptable + " on A01" + imptemptable + "(A0104)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0148A01" + imptemptable + " on A01" + imptemptable + "(A0148)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0163A01" + imptemptable + " on A01" + imptemptable + "(A0163)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0184A01" + imptemptable + " on A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index ORGIDA01" + imptemptable + " on A01" + imptemptable + "(ORGID)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index STATUSA01" + imptemptable + " on A01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
				try {
					sess.createSQLQuery("Create index A0200A02" + imptemptable + " on A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A02" + imptemptable + " on A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0201BA02" + imptemptable + " on A02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0255A02" + imptemptable + " on A02" + imptemptable + "(A0255)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0600A06" + imptemptable + " on A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A06" + imptemptable + " on A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0800A08" + imptemptable + " on A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A08" + imptemptable + " on A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0801BA08" + imptemptable + " on A08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1100A11" + imptemptable + " on A11" + imptemptable + "(A1100)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A11" + imptemptable + " on A11" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1400A14" + imptemptable + " on A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A14" + imptemptable + " on A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A15" + imptemptable + " on A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1500A15" + imptemptable + " on A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*try {
					sess.createSQLQuery("Create index A0000A29" + imptemptable + " on A29" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				try {
					sess.createSQLQuery("Create index A0000A30" + imptemptable + " on A30" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A31" + imptemptable + " on A31" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A3600A36" + imptemptable + " on A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A36" + imptemptable + " on A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A3600A36New" + imptemptable + " on A36New" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A36New" + imptemptable + " on A36New" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
/*				try {
					sess.createSQLQuery("Create index A0000A37" + imptemptable + " on A37" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
/*				try {
					sess.createSQLQuery("Create index A4100A41" + imptemptable + " on A41" + imptemptable + "(A4100)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A41" + imptemptable + " on A41" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				try {
					sess.createSQLQuery("Create index A5300A53" + imptemptable + " on A53" + imptemptable + "(A5300)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A53" + imptemptable + " on A53" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A57" + imptemptable + " on A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A6000A60" + imptemptable + " on A60" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A61" + imptemptable + " on A61" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A62" + imptemptable + " on A62" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A63" + imptemptable + " on A63" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A6400A64" + imptemptable + " on A64" + imptemptable + "(A6400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0111B01" + imptemptable + " on B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0121B01" + imptemptable + " on B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0131B01" + imptemptable + " on B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
/*				try {
					sess.createSQLQuery("Create index B0111B_E" + imptemptable + " on B_E" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000I_E" + imptemptable + " on I_E" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 */
	public static void bakIndex(String imptemptable) {
		HBSession sess = HBUtil.getHBSession();
		try {
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0000A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0195A01" + imptemptable + "(A0195)").executeUpdate();
				} catch (Exception e) {}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0201BA02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {}
			} else {
				try {
					sess.createSQLQuery("Create index A0000A01" + imptemptable + " on A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {}
				try {
					sess.createSQLQuery("Create index A0195A01" + imptemptable + " on A01" + imptemptable + "(A0195)").executeUpdate();
				} catch (Exception e) {}
				try {
					sess.createSQLQuery("Create index A0201BA01" + imptemptable + " on A02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	public static void savelog(String table, String detail,Long sortid, String optype,String imprecordid, boolean islast) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			List<Datarecrejlog> pros = sess.createQuery(" from Datarecrejlog where sortid="+(sortid-1)+" and imprecordid='" + imprecordid+ "'").list();
			if(pros!=null && pros.size()>0){
				Datarecrejlog pro = pros.get(0);
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setOpstatus("2");
				sess.update(pro);
			}
			if(!islast){
				Datarecrejlog pro = new Datarecrejlog();
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setDatatable(table);
				pro.setDetail(detail);
				pro.setImprecordid(imprecordid);
				pro.setSortid(sortid);
				pro.setOptype(optype);
				pro.setOpstatus("1");
				sess.save(pro);
			}
			
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
	public static void appendFileContent(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void checkIndexZip(String imptemptable) {
		HBSession sess = HBUtil.getHBSession();
		try {
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0000A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index TBRA01" + imptemptable + "(TBR)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index STATUSA01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0184A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0200A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0000A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0201BA02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				
				try {
					sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0600A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0000A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0800A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0000A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0801BA08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A1400A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A0000A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A1500A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A0000A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A3600A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A0000A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE A57" + imptemptable + " add index A0000A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0111B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0131B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0121B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
			} else {
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("Create index A0000A01" + imptemptable + " on A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0101A01" + imptemptable + " on A01" + imptemptable + "(A0101)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0104A01" + imptemptable + " on A01" + imptemptable + "(A0104)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0148A01" + imptemptable + " on A01" + imptemptable + "(A0148)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0163A01" + imptemptable + " on A01" + imptemptable + "(A0163)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0184A01" + imptemptable + " on A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index ORGIDA01" + imptemptable + " on A01" + imptemptable + "(ORGID)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index STATUSA01" + imptemptable + " on A01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A01 的临时表不存在");
				}
				
				
				
				try {
					sess.createSQLQuery("Create index A0200A02" + imptemptable + " on A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A02" + imptemptable + " on A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0201BA02" + imptemptable + " on A02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0255A02" + imptemptable + " on A02" + imptemptable + "(A0255)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A02 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0600A06" + imptemptable + " on A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A06" + imptemptable + " on A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A06 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0800A08" + imptemptable + " on A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A08" + imptemptable + " on A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0801BA08" + imptemptable + " on A08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A08 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A1400A14" + imptemptable + " on A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A14" + imptemptable + " on A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A14 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A15" + imptemptable + " on A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A1500A15" + imptemptable + " on A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A15 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A3600A36" + imptemptable + " on A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A36" + imptemptable + " on A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A36 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index A0000A57" + imptemptable + " on A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": A57 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index B0111B01" + imptemptable + " on B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index B0121B01" + imptemptable + " on B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
				try {
					sess.createSQLQuery("Create index B0131B01" + imptemptable + " on B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					CommonQueryBS.systemOut(new Date().toString()+": B01 的临时表不存在");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
	}
}
