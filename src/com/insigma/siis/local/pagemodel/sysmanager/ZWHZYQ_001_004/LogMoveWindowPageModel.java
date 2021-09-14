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
	 * ��־Ǩ��
	 */
	@PageEvent("doSave")
	public int doSave() throws RadowException, ParseException{
		String starttime = this.getPageElement("start").getValue();
		String endtime = this.getPageElement("end").getValue();
		String isdelete = this.getPageElement("isDelete").getValue();
		if("".equals(starttime)){
			this.setMainMessage("��ѡ��ʼʱ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("".equals(endtime)){
			this.setMainMessage("��ѡ�����ʱ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("".equals(isdelete)){
			this.setMainMessage("��ѡ���Ƿ�ִ��ɾ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "delTTab","yes");							/*confirm���ʹ���,���ȷ��ʱ�����¼�*/
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");							/*confirm���ʹ���,���ȡ��ʱ�����¼�*/
		this.setMessageType(EventMessageType.CONFIRM); 									/*��Ϣ������(confirm���ʹ���)*/
		this.setMainMessage("Ǩ�ƴ�������־�����ݱ��ͬʱ������Ǩ�������ϸ��־����ȷ��Ǩ����");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 *�������ݿ�Ǩ������
	 * @param confirm confirm����ֵ
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
		//ˢ����־�б�ҳ��
		this.getExecuteSG().addExecuteCode("parent.flash();");
		
		this.setMainMessage("��־Ǩ�Ƴɹ���");
		this.closeCueWindowByYes("logMoveWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}