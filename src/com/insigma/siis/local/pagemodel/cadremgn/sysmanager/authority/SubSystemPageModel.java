package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;


import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class SubSystemPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String param = this.getPageElement("subWinIdBussessId").getValue();
		if(param==null||"".equals(param)){
			throw new RadowException("查询部门失败");
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select s.usergroupname from smt_usergroup s where s.id = '"+param+"'").uniqueResult();
		if(obj!=null){
			this.getPageElement("text1").setValue(""+obj);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	public int save() throws RadowException{
		String param = this.getPageElement("subWinIdBussessId").getValue();
		String text = this.getPageElement("text1").getValue();
		if(text==null||"".equals(text)){
			this.setMainMessage("部门名称不能为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(UserGroupPageModel.isSpecialChar(text)){
			this.setMainMessage("部门名称只支持中文或者英文");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj2 = sess.createSQLQuery("select count(*) from SMT_USERGROUP s where s.sid = '"+param+"' and s.usergroupname = '"+text+"'").uniqueResult();
		if(Integer.parseInt(""+obj2)>0){
			this.setMainMessage("同一个部门下，不能建立同名部门");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		Object obj = sess.createSQLQuery("select s.usergroupname from smt_usergroup s where s.id = '"+param+"'").uniqueResult();
		if(obj!=null){
			LogUtil applog = new LogUtil();
			List<String[]> list = new ArrayList<String[]>();
			String[] arr = {"usergroupname", obj.toString(), text,"usergroupname"};
			list.add(arr);
			applog.createLogNew(param,"用户所在部门修改","smt_usergroup",param,text,list);
		}

		sess.createSQLQuery("update smt_usergroup s set s.usergroupname = '"+text+"' where s.id = '"+param+"'").executeUpdate();
		this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '保存成功', function(e){ if ('ok' == e){parent.Ext.getCmp('resetGroup').close();realParent.reloadTree();}});");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
