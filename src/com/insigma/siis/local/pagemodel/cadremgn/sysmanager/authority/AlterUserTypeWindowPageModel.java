package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class AlterUserTypeWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//页面数据初始化
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, PrivilegeException, AppException {
		String param = this.getPageElement("subWinIdBussessId").getValue();
		UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(param);
		String sectype = HBUtil.getValueFromTab("sectype", "smt_user", "userid='"+param+"'");
		String usertype = null;
		if(user.getUsertype()==null||"".equals(user.getUsertype())){
			usertype = "未定义";
		}else{
			usertype = user.getUsertype();
		}
		this.getPageElement("userid").setValue(param);
		this.getExecuteSG().addExecuteCode("document.getElementById('username').innerHTML="+"'"+user.getName()+"'");
		this.getPageElement("usertype").setValue(usertype);
		this.getPageElement("sectype").setValue(sectype);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("savebut")
	@Transaction
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException, AppException{
		String userid = this.getPageElement("userid").getValue();
		String usertype = this.getPageElement("usertype").getValue();
		String sectype = this.getPageElement("sectype").getValue();
		String loginname = SysManagerUtils.getUserName(userid);
		String usertype_o = HBUtil.getValueFromTab("usertype", "smt_user", "userid = '"+userid+"'");
		String sectype_o = HBUtil.getValueFromTab("sectype", "smt_user", "userid = '"+userid+"'");
		//保存用户类型
		HBUtil.getHBSession().createSQLQuery("update smt_user set usertype = '"+usertype+"', sectype = '"+sectype+"' where userid = '"+userid+"'").executeUpdate();
		/*this.setMainMessage("操作成功");
		this.closeCueWindowEX();*/
		LogUtil applog = new LogUtil();
		List<String[]> list = new ArrayList<String[]>();
		String[] arr1 = {"usertype", usertype_o, usertype, "usertype"};
		String[] arr2 = {"sectype", sectype_o, sectype, "sectype"};
		list.add(arr1);
		list.add(arr2);
		applog.createLogNew(userid,"用户类型修改","smt_user",userid,loginname, list);
		this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '操作成功', function(e){ if ('ok' == e){parent.Ext.getCmp('usertype').close();realParent.reloadTree();}});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
}