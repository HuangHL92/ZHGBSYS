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

public class WarnWindowZCPageModel extends PageModel{
	
	public static int syDayCount = 0;
	public static int birthDaycount = 0;
	private CustomQueryBS cbBs=new CustomQueryBS();
	
	public WarnWindowZCPageModel(){
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
	
	@PageEvent("persongrid1.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid1").getValue("a0000",this.getPageElement("persongrid1").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'persongrid1',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
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
		
		//String sqlOut = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+userid+"' AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		String sqlOut = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlOut = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlOut = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (select date_format(now(),'%Y%m%d'))";
		}
		String sql = "SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM("+sqlOut+") b,A01 a01 WHERE A01.A0000 = b.A0000";
		this.request.getSession().setAttribute("listName_swtx", "待转出人员");
		this.request.getSession().setAttribute("sql_swtx", sql);
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	private void trueRevoke(String fnDelte, String strHint,String str){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(str);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	@PageEvent("revoke")
	@Transaction
	public int revoke(String a0000)throws AppException, RadowException{
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null ){
			throw new AppException("人员信息有误！");
		}
		
		//从people_transfer中删除记录，将其改为现职人员（在其他人员中可以查询到）
		String sql = "DELETE FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' AND A0000 = '"+a0000+"'";
		sess.createSQLQuery(sql).executeUpdate();
		
		//a01.setA0163("1");
		//a01.setStatus("1");
		//a01.setOrgid("");
		//sess.update(a01);
		
		this.getExecuteSG().addExecuteCode("revokeSuccess()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("revokeWarn")
	public int revokeWarn(String a0000){
		trueRevoke("revoke","调转撤销后，您可在'人员信息'——'非现职人员'——原任职机构下查询到该人员。是否确认撤销？",a0000);
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	public static int getSyDayCount() {
		return syDayCount;
	}

	public static void setSyDayCount(int syDayCount) {
		WarnWindowZCPageModel.syDayCount = syDayCount;
	}
	
	public static void setBirthDaycount(int birthDaycount) {
		WarnWindowZCPageModel.birthDaycount = birthDaycount;
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
