package com.insigma.siis.local.pagemodel.sysorg.org;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Datarecord;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class recoverPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		this.setNextEventName("recover.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("recover.dogridquery")
	public int recovershow(int start,int limit)throws RadowException{
		//得到当前用户 
		String userID = SysManagerUtils.getUserId();
		List<Datarecord> list = HBUtil.getHBSession().createSQLQuery("select * from datarecord").addEntity(Datarecord.class).list();
		if(list.size()==0) {
			String sql = "select datarecordid,filename,removedate from datarecord";
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		//取可以显示的datarecordid
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(Datarecord datarecord:list) {
			String organization = datarecord.getORGANIZZTION();
			//得到所有上级用户
			List<String> listUserid = new ArrayList<String>();
			if(DBType.ORACLE==DBUtil.getDBType()){
				listUserid = HBUtil.getHBSession().createSQLQuery("SELECT userid  FROM smt_user WHERE INSTR('"+organization+"',OTHERINFO)>0").list();
			}else{
				listUserid = HBUtil.getHBSession().createSQLQuery("SELECT userid  FROM smt_user WHERE '"+organization+"' LIKE CONCAT('%',OTHERINFO,'%')").list();
			}
			//得到该显示的datarecordid
			if(listUserid.contains(userID)) {
				sb.append("'"+datarecord.getDATARECORDID()+"'"+",");
			}
		}
		if(sb.toString().equals("(")) {
			//没有要显示的数据，只要查询为空就行
			String sql = "select datarecordid,filename,removedate from datarecord where datarecordid='-1'";
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String datarecordids = sb.toString();
		String sql = "select datarecordid,filename,removedate from datarecord where datarecordid in "+datarecordids;
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("singledelete")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int singledelete(String datarecordid) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		try {
			delete(datarecordid,"1");
		} catch (SQLException e) {
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.setMainMessage("删除成功");
		setNextEventName("recover.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	//删除单条数据
	public void delete(String datarecordid,String type) throws SQLException {
		HBSession sess = HBUtil.getHBSession();
		
		List<Datarecord> list = HBUtil.getHBSession().createSQLQuery("select * from datarecord where datarecordid='"+datarecordid+"'").addEntity(Datarecord.class).list();
		Datarecord datarecord = list.get(0);
		String temp_table = datarecord.getTEMP_TABLE();
		String organization = datarecord.getORGANIZZTION();
		HBUtil.getHBSession().createSQLQuery("drop table a01"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a02"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a05"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a06"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a08"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a14"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a15"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a36"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a11"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a41"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a29"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a53"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a37"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a31"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table a30"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table A99Z1"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table b01"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("drop table COMPETENCE_USERDEPT"+temp_table).executeUpdate();
		HBUtil.getHBSession().createSQLQuery("delete from datarecord where datarecordid = '"+datarecordid+"'").executeUpdate();
		if(type.equals("1")) {//表示点删除操作
			//得到当前机构所有用户
			List<String> userids = HBUtil.getHBSession().createSQLQuery("select USERID from smt_user where OTHERINFO like '"+organization+"%'").list();
			if(userids.size()==0) {
				return;
			}
			StringBuffer sbUserids = new StringBuffer();
			for(String userid :userids) {
				sbUserids.append("'"+userid+"',");

			}
			sbUserids.deleteCharAt(sbUserids.length()-1);
			//删除机构下的用户
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			String sDate = sf.format(date);
			HBUtil.getHBSession().createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid in("+sbUserids+")").executeUpdate();
			HBUtil.getHBSession().createSQLQuery("delete from smt_act where (objectid in("+sbUserids+") or userid in("+sbUserids+"))").executeUpdate();
			HBUtil.getHBSession().createSQLQuery("delete from smt_usergroupref where userid in("+sbUserids+")").executeUpdate();

			HBUtil.getHBSession().createSQLQuery("delete from competence_userdept where userid in("+sbUserids+")").executeUpdate();
		}
			
	}
	@PageEvent("verify")
	public int verify(String datarecordid) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException, SQLException {
		HBSession sess = HBUtil.getHBSession();
		List<Datarecord> list = HBUtil.getHBSession().createSQLQuery("select * from datarecord where datarecordid='"+datarecordid+"'").addEntity(Datarecord.class).list();
		//得到这个恢复对象
		Datarecord datarecord = list.get(0);
		//得到临时表名
		String temp_table = datarecord.getTEMP_TABLE();
		//得到当时被删除的机构id
		String groupid = list.get(0).getORGANIZZTION();
		
		//找出机构编码是否有重复的
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		List<String> listtablename = HBUtil.getHBSession().createSQLQuery("select temp_table from datarecord where datarecordid='"+datarecordid+"'").list();
		List<String> listB0114 = HBUtil.getHBSession().createSQLQuery("select b0114 from b01").list();
		listB0114.removeAll(Collections.singleton(null));
		List<String> listB0114record = HBUtil.getHBSession().createSQLQuery("select b0114 from b01"+listtablename.get(0)).list();
		listB0114record.removeAll(Collections.singleton(null));
		List<String> listSame = getSame(listB0114,listB0114record);
		if(listSame.size()!=0) {//存在与机构库中人机构重复的情况
			for(String B0114:listSame) {
				sb.append("'"+B0114+"',");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			String b0114s = sb.toString();
			List<String> listb0101 = HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0114 in"+b0114s).list();
			StringBuffer message = new StringBuffer();
			if(listb0101.size()>5) {
				listb0101= listb0101.subList(0, 4);
				for(String B0101:listb0101) {
					message.append(B0101+",");
				}
				message = message.deleteCharAt(message.length() - 1);
				message.append("……");
			}else {
				for(String B0101:listb0101) {
					message.append(B0101+",");
				}
				message = message.deleteCharAt(message.length() - 1);
			}
			
			
			this.setMainMessage("不能恢复！\n当前机构树下存在重复机构编码：\n"+message);
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断是否有重复机构内码
		List<B01> listb01 = sess.createSQLQuery("select * from b01 where b0111='"+groupid+"'").addEntity(B01.class).list();
		if(listb01.size()!=0) {
			this.setMainMessage("系统中已存在该位置的节点，不能恢复！");
			return EventRtnType.NORMAL_SUCCESS; 
		}
		List<String> listb0121 = sess.createSQLQuery("select b0121 from b01"+temp_table+" where b0111='"+groupid+"'").list();
		List<B01> listB01 = sess.createSQLQuery("select * from b01 where b0111='"+listb0121.get(0)+"'").list();
		if(listB01.size()==0) {//父节点已经没有了，不能恢复
			this.setMainMessage("该机构的上级单位已不存在系统中，不能恢复!");
			return EventRtnType.NORMAL_SUCCESS; 
		}
		//判断人员是否重复！有重复就要判断是否要覆盖
		List<String> listA0184 = HBUtil.getHBSession().createSQLQuery("select a0184 from a01").list();
		List<String> listA0184record = HBUtil.getHBSession().createSQLQuery("select a0184 from a01"+temp_table).list();
		List<String> listSamePerson = getSame(listA0184,listA0184record);
		if(listSamePerson.size()!=0) {//存在与人员库中人员重复的情况
			int numPerson = listSamePerson.size();
			String result= numPerson+","+datarecordid;
			this.getExecuteSG().addExecuteCode("recoverReal('"+result+"')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("recover("+datarecordid+")");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	
	@PageEvent("delRecover")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int delRecover(String datarecordid) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException, SQLException {
		try {
			//删除人员再恢复
			List<String> listtablename = HBUtil.getHBSession().createSQLQuery("select temp_table from datarecord where datarecordid='"+datarecordid+"'").list();
			String temp_table = listtablename.get(0);
			List<String> listA0184 = HBUtil.getHBSession().createSQLQuery("select a0184 from a01").list();
			List<String> listA0184record = HBUtil.getHBSession().createSQLQuery("select a0184 from a01"+temp_table).list();
			List<String> listSamePerson = getSame(listA0184,listA0184record);
			//转换为a0000
			StringBuffer sba0184s = new StringBuffer();
			sba0184s.append("(");
			for(String a0184 : listSamePerson) {
				sba0184s.append("'"+a0184+"',");
			}
			sba0184s = sba0184s.deleteCharAt(sba0184s.length() - 1);
			sba0184s.append(")");
			List<String> listA0000s = HBUtil.getHBSession().createSQLQuery("select a0000 from a01 where a0184 in"+sba0184s).list();
			//拼写要删除的人员
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for(String a0000 : listA0000s) {
				sb.append("'"+a0000+"',");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			String sqla01 = "delete from a01 where a0000 in "+sb.toString();
			String sqla02 = "delete from a02 where a0000 in "+sb.toString();
			String sqla05 = "delete from a05 where a0000 in "+sb.toString();
			String sqla06 = "delete from a06 where a0000 in "+sb.toString();
			String sqla08 = "delete from a08 where a0000 in "+sb.toString();
			String sqla14 = "delete from a14 where a0000 in "+sb.toString();
			String sqla15 = "delete from a15 where a0000 in "+sb.toString();
			String sqla36 = "delete from a36 where a0000 in "+sb.toString();
			String sqla11 = "delete from a11 where a0000 in "+sb.toString();
			String sqla41 = "delete from a41 where a0000 in "+sb.toString();
			String sqla29 = "delete from a29 where a0000 in "+sb.toString();
			String sqla53 = "delete from a53 where a0000 in "+sb.toString();
			String sqla37 = "delete from a37 where a0000 in "+sb.toString();
			String sqla31 = "delete from a31 where a0000 in "+sb.toString();
			String sqla30 = "delete from a30 where a0000 in "+sb.toString();
			String sqla99Z1 = "delete from A99Z1 where a0000 in "+sb.toString();
			HBUtil.getHBSession().createSQLQuery(sqla01).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla02).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla05).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla06).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla08).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla14).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla15).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla36).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla11).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla41).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla29).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla53).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla37).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla31).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla30).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla99Z1).executeUpdate();
			CommonQueryBS.systemOut("恢复开始");
			HBSession sess = HBUtil.getHBSession();
			//恢复数据
			List<Datarecord> list = HBUtil.getHBSession().createSQLQuery("select * from datarecord where datarecordid='"+datarecordid+"'").addEntity(Datarecord.class).list();
			Datarecord datarecord = list.get(0);
			String groupid = list.get(0).getORGANIZZTION();
			HBUtil.getHBSession().createSQLQuery("insert into a01 select * from a01"+temp_table).executeUpdate();
			if(DBType.ORACLE==DBUtil.getDBType()) {
				HBUtil.getHBSession().createSQLQuery("INSERT INTO a02 (A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,A0215B,A0209,A0207,A0204,A0201C,A0201,A0279) (select A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,A0215B,A0209,A0207,A0204,A0201C,A0201,A0279 from a02"+temp_table+")").executeUpdate();
			}else {
				HBUtil.getHBSession().createSQLQuery("insert into a02 select * from a02"+temp_table).executeUpdate();
			}
			HBUtil.getHBSession().createSQLQuery("insert into a05 select * from a05"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a06 select * from a06"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a08 select * from a08"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a14 select * from a14"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a15 select * from a15"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a36 select * from a36"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a11 select * from a11"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a41 select * from a41"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into b01 select * from b01"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a29 select * from a29"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a53 select * from a53"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a37 select * from a37"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a31 select * from a31"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a30 select * from a30"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into A99Z1 select * from A99Z1"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into COMPETENCE_USERDEPT select * from COMPETENCE_USERDEPT"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("UPDATE SMT_USER SET USEFUL = '1' WHERE OTHERINFO like '"+groupid+"%'").executeUpdate();
			delete(datarecordid,"2");
			applog.createLogNewF("机构恢复", "", "1111111", "系统");
			CommonQueryBS.systemOut("恢复结束");
			this.setMainMessage("恢复成功");
			setNextEventName("recover.dogridquery");
			this.getExecuteSG().addExecuteCode("realParent.window.reloadTree()");
			this.getExecuteSG().addExecuteCode("Ext.WindowMgr.getActive().close();");
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("恢复失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("datarecord")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int datarecord(String datarecordid) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException, SQLException {
		try {
			CommonQueryBS.systemOut("恢复开始");
			HBSession sess = HBUtil.getHBSession();
			//恢复数据
			List<Datarecord> list = HBUtil.getHBSession().createSQLQuery("select * from datarecord where datarecordid='"+datarecordid+"'").addEntity(Datarecord.class).list();
			Datarecord datarecord = list.get(0);
			String temp_table = datarecord.getTEMP_TABLE();
			String groupid = list.get(0).getORGANIZZTION();
			HBUtil.getHBSession().createSQLQuery("insert into a01 select * from a01"+temp_table).executeUpdate();
			if(DBType.ORACLE==DBUtil.getDBType()) {
				HBUtil.getHBSession().createSQLQuery("INSERT INTO a02 (A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,A0215B,A0209,A0207,A0204,A0201C,A0201,A0279) (select A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,A0215B,A0209,A0207,A0204,A0201C,A0201,A0279 from a02"+temp_table+")").executeUpdate();
			}else {
				HBUtil.getHBSession().createSQLQuery("insert into a02 select * from a02"+temp_table).executeUpdate();
			}
			HBUtil.getHBSession().createSQLQuery("insert into a05 select * from a05"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a06 select * from a06"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a08 select * from a08"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a14 select * from a14"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a15 select * from a15"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a36 select * from a36"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a11 select * from a11"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a41 select * from a41"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into b01 select * from b01"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a29 select * from a29"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a53 select * from a53"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a37 select * from a37"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a31 select * from a31"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into a30 select * from a30"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into A99Z1 select * from A99Z1"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("insert into COMPETENCE_USERDEPT select * from COMPETENCE_USERDEPT"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("UPDATE SMT_USER SET USEFUL = '1' WHERE OTHERINFO like '"+groupid+"%'").executeUpdate();
			delete(datarecordid,"2");
			applog.createLogNewF("机构恢复", "", "1111111", "系统");
			CommonQueryBS.systemOut("恢复结束");
			this.setMainMessage("恢复成功");
			setNextEventName("recover.dogridquery");
			this.getExecuteSG().addExecuteCode("realParent.window.reloadTree()");
			this.getExecuteSG().addExecuteCode("Ext.WindowMgr.getActive().close();");
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("恢复失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("allDelete")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int allDelete(String datarecordid) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException, SQLException {
		StringBuffer sb = new StringBuffer();
		List<HashMap<String,String>> listData = this.getPageElement("recover").getStringValueList();
		if(listData.size()==0) {
			this.setMainMessage("表格中没有数据！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		sb.append("(");
		for(HashMap<String, String> m :listData) {
			sb.append("'"+m.get("datarecordid")+"',");
		}
		if(sb.toString().equals("(")) {
			this.setMainMessage("删除失败！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String datarecordids = sb.toString();
		HBSession sess = HBUtil.getHBSession();
		List<Datarecord> list = sess.createSQLQuery("select * from datarecord where datarecordid in "+datarecordids).addEntity(Datarecord.class).list();
		for(int i=0;i<list.size();i++) {
			Datarecord datarecord = list.get(i);
			String temp_table = datarecord.getTEMP_TABLE();
			String organization = datarecord.getORGANIZZTION();
			
			
			//得到当前机构所有用户
			List<String> userids = HBUtil.getHBSession().createSQLQuery("select USERID from smt_user where OTHERINFO like '"+organization+"%'").list();
			StringBuffer sbUserids = new StringBuffer();
			if(userids.size()==0) {
				
			}else {
				for(String userid :userids) {
					sbUserids.append("'"+userid+"',");
				}
				sbUserids.deleteCharAt(sbUserids.length()-1);
			}
			//删除机构下的用户
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			String sDate = sf.format(date);
			if(sbUserids.toString()==null||"".equals(sbUserids.toString())){
				sbUserids = new StringBuffer("''");
			}
			
			HBUtil.getHBSession().createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid in("+sbUserids+")").executeUpdate();
			HBUtil.getHBSession().createSQLQuery("delete from smt_act where (objectid in("+sbUserids+") or userid in("+sbUserids+"))").executeUpdate();
			HBUtil.getHBSession().createSQLQuery("delete from smt_usergroupref where userid in("+sbUserids+")").executeUpdate();

			HBUtil.getHBSession().createSQLQuery("delete from competence_userdept where userid in("+sbUserids+")").executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a01"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a02"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a05"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a06"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a08"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a14"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a15"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a36"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a11"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a41"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a29"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a53"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a37"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a31"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table a30"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table A99Z1"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table b01"+temp_table).executeUpdate();
			HBUtil.getHBSession().createSQLQuery("drop table COMPETENCE_USERDEPT"+temp_table).executeUpdate();
			
		}
		HBUtil.getHBSession().createSQLQuery("delete from datarecord").executeUpdate();
		this.setMainMessage("删除成功");
		setNextEventName("recover.dogridquery");
		this.getExecuteSG().addExecuteCode("Ext.WindowMgr.getActive().close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) {
		List<String> list1= new ArrayList<String>();
		list1.add("w");
		list1.add("s");
		list1.add("d");
		list1.add("f");
		list1.add("g");
		List<String> newList = list1.subList(1, 3);
		System.out.println(newList);
	}
	//找出两个list中相同的元素
	public List<String> getSame(List<String> list1,
            List<String> list2) {
        List<String> result = new ArrayList<String>();
        for (String str : list2) {//遍历list1
            if (list1.contains(str)) {//如果存在这个数
                result.add(str);//放进一个list里面，这个list就是交集
            }
        }
        return result;
    }
	
}
