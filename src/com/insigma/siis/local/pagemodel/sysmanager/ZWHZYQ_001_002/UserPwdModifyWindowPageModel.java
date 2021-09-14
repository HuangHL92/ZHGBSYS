package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_002;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class UserPwdModifyWindowPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("savebut.onclick")
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException{
		String oldPwd = MD5.MD5(this.getPageElement("oldPwd").getValue());
		String newPwd = this.getPageElement("newPwd").getValue();
		String newPwd1 = this.getPageElement("newPwd1").getValue();
		String data= this.getRadow_parent_data();
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(data);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pwd = user.getPasswd();
		if(oldPwd.equals("")){
			this.setMainMessage("请输入原密码");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(newPwd.equals("")){
			this.setMainMessage("请输入新密码");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(newPwd1.equals("")){
			this.setMainMessage("请输入确认密码");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!newPwd1.equals(newPwd)){
			this.setMainMessage("新密码与确认密码不一致");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!pwd.equals(oldPwd)){
			this.setMainMessage("原密码错误");
			return EventRtnType.NORMAL_SUCCESS;
		}
			this.saveUserPwd("yes");
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveUserPwd")
	public int saveUserPwd(String acredit) throws RadowException{
		String data= this.getRadow_parent_data();
		String userid=data;
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(data);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newPwd = this.getPageElement("newPwd").getValue();
		String newPassword = MD5.MD5(newPwd);
		user.setPasswd(newPassword);
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if(acredit.equals("yes")) doAcredit(userid);*/
		List list = new ArrayList();
		try {
			new LogUtil().createLog("67", "SMT_USER",user.getId(), user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("保存成功");
		this.closeCueWindowByYes("modifyUserPwdWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
