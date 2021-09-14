package com.insigma.siis.local.pagemodel.templateconf;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;

public class BackCheckPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		String id = this.getPageElement("subWinIdBussessId2").getValue();
		Object obj = sess.createSQLQuery("select t.querysql from NATURAL_STRUCTURE t where t.id = '"+id+"'").uniqueResult();
		if(obj!=null&&!"".equals(obj)){
			sql = "" + obj;
		}
	    this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/*@PageEvent("TrainingInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("TrainingInfoGrid").getValue("a0000",this.getPageElement("TrainingInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','人员信息修改',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");
			this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");
			this.getExecuteSG().addExecuteCode("$h.openPageModeWin('RmbkzWin','pages.cadremgn.infmtionentry.custom.Rmbkz', '任免表', 1360, 665,'TrainingInfoGrid','"+request.getContextPath()+"');");
			this.request.getSession().setAttribute("a0000", a0000);
			//initA0000Map(a0000);
			//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			//this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
		
	}*/
	
}
