package com.insigma.siis.local.pagemodel.customquery;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

/**
 * @author zoul
 *
 */
public class QueryWithOrgTreePageModel extends PageModel {
	
	private CustomQueryBS ctcBs=new CustomQueryBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		//this.getExecuteSG().addExecuteCode("resizeframe();");
		//this.setNextEventName("gridcq");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 双击固定条件查询
	 * @author 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("doQuery.onclick")
	@NoRequiredValidate
	public int rowdbclick() throws RadowException, AppException{
		System.out.println("执行了按机构查询");
		//this.getPageElement("checkedgroupid").setValue(null);
		this.request.getSession().setAttribute("queryType", "1");
		//this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		
		String querysql = "";//this.getPageElement("gridcq").getValue("querysql").toString();
		
		String sid = this.request.getSession().getId();
		/*querysql = CommSQL.getComSQL(sid)+"  where  not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"') ";*/

		String mllb = this.getPageElement("mllb").getValue();	
		String mllbsql = "".equals(mllb) ? "" : " and a01.a0165 = '"+mllb+"' ";
		String a0163sql = "";
		String a0163 = this.getPageElement("personq").getValue();
		if("1".equals(a0163)) {
			a0163sql=" and a0163 like '1%' ";
		}else if("2".equals(a0163)) {
			a0163sql=" and a0163 like '2%' ";
		}else if("".equals(a0163)|| a0163==null) {
			
		}else if("3".equals(a0163)) {
			
		}else {
			a0163sql=" and a0l63='"+a0163+"' ";
		}
		
		/*
		 * if("2".equals(a0163)){ a0163sql = " and a0163 in('2','23','25','29'); }else
		 * if("".equals(a0163)){
		 * 
		 * }else{ a0163sql = " and a0163='"+a0163+"' "; }
		 */
		
		
		
		querysql = CommSQL.getComSQL(sid)+"  where  1=1 "+mllbsql+a0163sql;
		
		
		
		this.getExecuteSG().addExecuteCode("realParent.conditionRowdbclick('"+querysql.replace("'", "\\'")+"',doQuery());");
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
}