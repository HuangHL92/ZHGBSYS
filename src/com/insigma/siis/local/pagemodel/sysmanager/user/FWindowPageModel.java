package com.insigma.siis.local.pagemodel.sysmanager.user;

import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.MD5;

public class FWindowPageModel extends PageModel {

	private static String owner = null;
	private static String userid = null;
	private static String password = null;
	private static String useful = null;

	@Override
	public int doInit() throws RadowException {
		this.getPageElement("wdloginname").setValue(this.getRadow_parent_data());
		String	loginname = this.getPageElement("wdloginname").getValue();
		try {
			List list = PrivilegeManager.getInstance().getIUserControl().queryByName(loginname, true);
			UserVO user = (UserVO) list.get(0);
			userid = user.getId();
			owner = user.getOwnerId();
			password = user.getPasswd();
			useful=user.getStatus();
			//this.getPageElement("wdstatus").setValue(user.getStatus());
			this.getPageElement("wdpassword").setValue(user.getPasswd());
			if(user.getName()==null){
				this.getPageElement("wdusername").setValue("");
			}else{
				this.getPageElement("wdusername").setValue(user.getName());
			}
			if(user.getDesc() == null){
				this.getPageElement("desc").setValue("");
			}else{
				this.getPageElement("desc").setValue(user.getDesc());
			}
			if("".equals(useful)||useful==null){
				((Combo)this.getPageElement("useful")).setValue("0");
			}else{
				((Combo)this.getPageElement("useful")).setValue(useful);
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("wdloginname.onchange")
	@NoRequiredValidate
	public int LoginNameOnChonge() throws RadowException{
		String loginname = this.getPageElement("wdloginname").getValue();
		try {
			List list = PrivilegeManager.getInstance().getIUserControl().queryByName(loginname, true);
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("该登录名已经存在");
		} catch (PrivilegeException e) {
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("savebut.onclick")
	@Transaction
	public int savebutOnclick() throws RadowException{
		//String status = this.getPageElement("wdstatus").getValue();
		String loginname = this.getPageElement("wdloginname").getValue();
		String username = this.getPageElement("wdusername").getValue();
		String desc = this.getPageElement("desc").getValue();
		String npassword = this.getPageElement("wdpassword").getValue();
		String nuseful = this.getPageElement("useful").getValue();
		UserVO user = new UserVO();
		user.setOwnerId(owner);
		user.setLoginname(loginname);
		user.setId(userid);
		user.setStatus("1");
		user.setDesc(desc);
		if(username==null){
			user.setName("");
		}else{
			user.setName(username);
		}
		if(npassword.equals(password)){
			user.setPasswd(password);
		}else{
			user.setPasswd(MD5.MD5(npassword));
		}
		if(nuseful.equals("0")||nuseful==null){
			user.setStatus("0");
		}else{
			user.setStatus(nuseful);
		}
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e) {
			this.isShowMsg=true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		this.closeCueWindow("updateUserWin");
		this.createPageElement("memberGrid",ElementType.GRID, true).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
