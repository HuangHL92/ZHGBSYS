package com.insigma.siis.local.pagemodel.customquery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class AlertWinPageModel extends PageModel {
	public AlertWinPageModel() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("templateInfoGrid1.dogridquery");
		this.setNextEventName("templateInfoGrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/*	@PageEvent("templateInfoGrid1.dogridquery")
	public int doMemberQuery1(int start,int limit) throws RadowException{
		//自定义名册
		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype='2' and t.tpkind in('2','3') group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}*/
/*	@PageEvent("templateInfoGrid2.dogridquery")
	public int doMemberQuery12(int start,int limit) throws RadowException{
		//自定义名册
		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype='2' and t.tpkind='1' group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}*/
	
	@PageEvent("templateInfoGrid1.dogridquery")
	public int doMemberQuery13(int start,int limit) throws RadowException, SQLException{
		String type = this.getPageElement("type").getValue();
		if("1".equals(type)){
			String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype='2' and t.tpkind in('2') group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
			System.out.println(sql);
			this.pageQuery(sql, "SQL", start, limit);
		}else{
			
			String  modelid = "";
			String  modelid1 = "";
			String sql2 = "select modelid from powergx where userid = '"+SysManagerUtils.getUserId()+"' group by modelid ";
			ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql2);
			while(re.next()){
				modelid += "'"+ re.getString(1) +"'"+",";
				System.out.println(modelid);
			}
///		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where t.tptype = '5' and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
			if(modelid.length()>0){
				modelid = modelid.substring(0, modelid.length()-1);
				modelid1 = "t.tpid in ("+modelid+")";
			}else{
				modelid1 = " 1=2 ";
			}
			String  sql3 = "select t.tpid from listoutput t where "+modelid1+" and t.tptype = '2' and t.tpkind = '2' group by t.tpid"; 
			modelid = "";
			modelid1 = "";
			ResultSet re1 = HBUtil.getHBSession().connection().createStatement().executeQuery(sql3);
			while(re1.next()){
				modelid += "'"+ re1.getString(1) +"'"+",";
				System.out.println(modelid);
			}
			if(modelid.length()>0){
				modelid = modelid.substring(0, modelid.length()-1);
				modelid1 = "t.tpid in ("+modelid+")";
			}else{
				modelid1 = " 1=2 ";
			}
			
			String sql = "select t.tpid,t.tptype,x.tpname tpname from listoutput t,powergx x where  "+modelid1+" and x.tpname <> 'null' and t.tpkind = '2' and t.tptype='2' and x.modelid = t.tpid group by t.tpid,x.tpname,t.tptype,t.tpkind order by t.tpkind";
			System.out.println(sql);
			this.pageQuery(sql, "SQL", start, limit);
		}
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("templateInfoGrid2.dogridquery")
	public int doMemberQuery14(int start,int limit) throws RadowException, SQLException{
		String type = this.getPageElement("type").getValue();
		if("1".equals(type)){
			String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype='2' and t.tpkind='1' group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
			System.out.println(sql);
			this.pageQuery(sql, "SQL", start, limit);
		}else{
			
			String  modelid = "";
			String  modelid1 = "";
			String sql2 = "select modelid from powergx where userid = '"+SysManagerUtils.getUserId()+"' group by modelid ";
			ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql2);
			while(re.next()){
				modelid += "'"+ re.getString(1) +"'"+",";
				System.out.println(modelid);
			}
///		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where t.tptype = '5' and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
			if(modelid.length()>0){
				modelid = modelid.substring(0, modelid.length()-1);
				modelid1 = "t.tpid in ("+modelid+")";
			}else{
				modelid1 = " 1=2 ";
			}
			String  sql3 = "select t.tpid from listoutput t where "+modelid1+" and t.tptype = '2' and t.tpkind = '1' group by t.tpid"; 
			modelid = "";
			modelid1 = "";
			ResultSet re1 = HBUtil.getHBSession().connection().createStatement().executeQuery(sql3);
			while(re1.next()){
				modelid += "'"+ re1.getString(1) +"'"+",";
				System.out.println(modelid);
			}
			if(modelid.length()>0){
				modelid = modelid.substring(0, modelid.length()-1);
				modelid1 = "t.tpid in ("+modelid+")";
			}else{
				modelid1 = " 1=2 ";
			}
			
			String sql = "select t.tpid,t.tptype,x.tpname tpname from listoutput t,powergx x where  "+modelid1+" and x.tpname <> 'null' and t.tpkind = '1' and t.tptype='2' and x.modelid = t.tpid group by t.tpid,x.tpname,t.tptype,t.tpkind order by t.tpkind";
			System.out.println(sql);
			this.pageQuery(sql, "SQL", start, limit);
		}
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("templateInfoGrid1.rowdbclick")
	@GridDataRange
	public int dbClick1(String id) throws RadowException{
		//int i = this.getPageElement("templateInfoGrid1").getCueRowIndex();
//		String tpid=this.getPageElement("templateInfoGrid1").getValue("tpid",this.getPageElement("templateInfoGrid1").getCueRowIndex()).toString();
		int i = this.getPageElement("templateInfoGrid1").getCueRowIndex();
		String tpid=this.getPageElement("templateInfoGrid1").getValue("tpid",i).toString();
		String a0000 = request.getParameter("initParams");
		if("allperson".equals(a0000)){
			StringBuffer ids = new StringBuffer();
			String allSelect = (String)this.request.getSession().getAttribute("allSelect");
			String newsql = allSelect.replace("*", "a0000");
			List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
			if (allPeople != null && allPeople.size() > 0) {
				for(Object sa0000 : allPeople){
					ids.append("|").append(sa0000).append("|@");
				}
			}
			a0000 = ids.substring(0, ids.length() - 1);
		}
		this.request.getSession().setAttribute("tpid", tpid);
		this.request.getSession().setAttribute("personids",a0000);
		request.getSession().setAttribute("viewType", "11");
//		this.getExecuteSG().addExecuteCode("addTab('自定义名册','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.PreSubmit',false,false)");
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin6','pages.search.PreSubmit','自定义名册',1200,900,'R','"+ctxPath+"');");

		//closeCueWindow("alertWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//@PageEvent("dbClick2")
	@PageEvent("templateInfoGrid2.rowdbclick")
	@GridDataRange
	public int dbClick2(String id) throws RadowException{
		//this.getPageElement("templateInfoGrid2").get
		int i = this.getPageElement("templateInfoGrid2").getCueRowIndex();
		String tpid=this.getPageElement("templateInfoGrid2").getValue("tpid",i).toString();
		//System.out.println(tpid);
		String a0000 = request.getParameter("initParams");
		if("allperson".equals(a0000)){
			StringBuffer ids = new StringBuffer();
			String allSelect = (String)this.request.getSession().getAttribute("allSelect");
			String newsql = allSelect.replace("*", "a0000");
			List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
			if (allPeople != null && allPeople.size() > 0) {
				for(Object sa0000 : allPeople){
					ids.append("|").append(sa0000).append("|@");
				}
			}
			a0000 = ids.substring(0, ids.length() - 1);
		}
		this.request.getSession().setAttribute("tpid", tpid);
		this.request.getSession().setAttribute("personids",a0000);
//		this.getExecuteSG().addExecuteCode("addTab('自定义表格','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin7','pages.publicServantManage.OtherTemShow','自定义表格',1200,900,'R','"+ctxPath+"');");
		///closeCueWindow("alertWin");
		//return EventRtnType.SPE_SUCCESS;
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public void closeCueWindow(String arg0) {
	this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}
	
}
