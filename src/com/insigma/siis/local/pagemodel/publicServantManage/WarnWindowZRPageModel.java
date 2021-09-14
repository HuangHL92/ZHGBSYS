package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.hsqldb.lib.StringUtil;

import com.fr.report.core.A.t;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;

public class WarnWindowZRPageModel extends PageModel{
	
	public static int syDayCount = 0;
	public static int birthDaycount = 0;
	private CustomQueryBS cbBs=new CustomQueryBS();
	
	public WarnWindowZRPageModel(){
		/*HBSession sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		Object syday =  sess.getSession().createSQLQuery("select t.syday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		WarnWindowZRPageModel.setSyDayCount(syday!=null && !StringUtil.isEmpty(syday.toString())?Integer.parseInt(syday.toString()):30);
		Object birthday = sess.getSession().createSQLQuery("select t.birthday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		WarnWindowZRPageModel.setBirthDaycount(birthday!=null&&!StringUtil.isEmpty(birthday.toString())?Integer.parseInt(birthday.toString()):30);*/
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		this.setNextEventName("persongrid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*@PageEvent("persongrid1.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid1").getValue("a0000",this.getPageElement("persongrid1").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}*/
	
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("persongrid1.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery1(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();//获取用户id
		
		//String sqlIn = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE LOGINID = '"+userid+"' AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		String sqlIn = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlIn = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlIn = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (select date_format(now(),'%Y%m%d'))";
		}
		String sql = "SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM("+sqlIn+") b,A01 a01 WHERE A01.A0000 = b.A0000";
		this.request.getSession().setAttribute("listName_swtx", "待转入人员");
		this.request.getSession().setAttribute("sql_swtx", sql);
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	public static int getSyDayCount() {
		return syDayCount;
	}

	public static void setSyDayCount(int syDayCount) {
		WarnWindowZRPageModel.syDayCount = syDayCount;
	}
	
	public static void setBirthDaycount(int birthDaycount) {
		WarnWindowZRPageModel.birthDaycount = birthDaycount;
	}
	
	public static int getBirthDaycount() {
		return birthDaycount;
	}
	
	/**
	 * 保存列表
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("dataSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int dataSave() throws UnsupportedEncodingException, SerialException, AppException, SQLException, RadowException{
		
		//判断列表是否有数据
		List<HashMap<String,Object>> list22 = this.getPageElement("persongrid1").getValueList();
		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要保存的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String saveName = this.request.getSession().getAttribute("listName_swtx").toString();
		String sql = this.request.getSession().getAttribute("sql_swtx").toString();
		
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		sql = "select a0000 from ("+sql+") a";
		cbBs.saveSWTXList(saveName, "", "", loginName,sql);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
