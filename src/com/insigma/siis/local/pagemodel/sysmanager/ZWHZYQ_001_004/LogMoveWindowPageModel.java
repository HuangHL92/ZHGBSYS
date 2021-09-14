package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004;

import java.io.*;  
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;  

import org.dom4j.*;  
import org.dom4j.io.*;
import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.HashMap;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.LogDetail;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class LogMoveWindowPageModel extends PageModel {
	/**
	 * 日志迁移
	 */
	@PageEvent("doSave")
	public int doSave() throws RadowException, ParseException{
		String starttime = this.getPageElement("start").getValue();
		String endtime = this.getPageElement("end").getValue();
		String isdelete = this.getPageElement("isDelete").getValue();
		if("".equals(starttime)){
			this.setMainMessage("请选择开始时间");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("".equals(endtime)){
			this.setMainMessage("请选择结束时间");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("".equals(isdelete)){
			this.setMainMessage("请选择是否执行删除");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "delTTab","yes");							/*confirm类型窗口,点击确定时触发事件*/
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");							/*confirm类型窗口,点击取消时触发事件*/
		this.setMessageType(EventMessageType.CONFIRM); 									/*消息框类型(confirm类型窗口)*/
		this.setMainMessage("迁移大任务日志到备份表的同时，将会迁移相关明细日志，您确定迁移吗？");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 *操作数据库迁移数据
	 * @param confirm confirm返回值
	 * @return
	 * @throws Exception 
	 */
	@PageEvent("delTTab")
	public int delTTB(String confirm) throws Exception{
		String starttime = this.getPageElement("start").getValue();
		String endtime = this.getPageElement("end").getValue();
		String isdelete = this.getPageElement("isDelete").getValue();
		String startsql = "";
		String endsql = "";
		if(DBUtil.getDBType()==DBType.ORACLE)
			startsql = " to_date('"+starttime+"','YYYY-MM-DD') ";
		else
			startsql = " STR_TO_DATE('"+starttime+"','%Y-%m-%d') ";
		if(DBUtil.getDBType()==DBType.ORACLE)
			endsql = " (to_date('"+endtime+"','YYYY-MM-DD')+1) ";
		else
			endsql = " DATE_ADD(STR_TO_DATE('"+endtime+"','%Y-%m-%d'), INTERVAL 1 DAY) ";
		String sql="insert into Log_Main_B ("
				+ "select * from Log_Main t where "
				+ "t.SYSTEM_OPERATE_DATE>= "+startsql
						+ " and t.SYSTEM_OPERATE_DATE< "+endsql+")";
		String sql1="insert into Log_Detail_B ("
				+ "select * from Log_Detail t where "
				+ "t.CHANGEDATETIME>= "+startsql
				+ "and t.CHANGEDATETIME< "+endsql+")";
		String sql2="delete from Log_Main where SYSTEM_OPERATE_DATE>= "+startsql+" and SYSTEM_OPERATE_DATE< "+endsql+"";
		String sql3="delete from Log_Detail where CHANGEDATETIME>= "+startsql+" and CHANGEDATETIME< "+endsql+"";
		String sql4="delete from Log_Main_B where SYSTEM_OPERATE_DATE>= "+startsql+" and SYSTEM_OPERATE_DATE< "+endsql+"";
		String sql5="delete from Log_Detail_B where CHANGEDATETIME>= "+startsql+" and CHANGEDATETIME< "+endsql+"";
		HBSession sess = HBUtil.getHBSession();
		Statement stat = null;
		Connection conn = null;
		conn = sess.connection();
		stat = conn.createStatement();
		if(isdelete.equals("1")){
			stat.execute(sql4);
			stat.execute(sql5);
		}
		stat.execute(sql);
		stat.execute(sql1);
		stat.execute(sql2);
		stat.execute(sql3);
		sess.flush();
		stat.close();
		conn.close();
		//this.setNextEventName("memberGrid.dogridquery");
		//刷新日志列表页面
		this.getExecuteSG().addExecuteCode("parent.flash();");
		
		this.setMainMessage("日志迁移成功。");
		this.closeCueWindowByYes("logMoveWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}