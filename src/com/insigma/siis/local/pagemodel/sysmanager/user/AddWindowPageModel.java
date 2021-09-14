package com.insigma.siis.local.pagemodel.sysmanager.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PmHListUtil;

public class AddWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("loginname.onchange")
	@NoRequiredValidate
	public int LoginNameOnChonge() throws RadowException{
		String loginname = this.getPageElement("loginname").getValue();
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
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException{
		String loginname = this.getPageElement("loginname").getValue();
		String username = this.getPageElement("username").getValue();
		if(loginname.equals("")) {
			this.setMainMessage("请输入用户登录名");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(username.equals("")){
			this.setMainMessage("请输入姓名");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int temp = chooseCount();
		if(temp == 0){
			this.addNextEvent(NextEventValue.YES, "saveUser","no");
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //消息框类型(confirm类型窗口)
			this.setMainMessage("确定不进行授权就保存该用户么？");
			return EventRtnType.NORMAL_SUCCESS;
		}
		return saveUser("yes");
	}
	
	@PageEvent("cannelEvent")
	public int cannelEvent(String acredit) throws RadowException{
		String aaa="";
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveUser")
	public int saveUser(String acredit) throws RadowException{
		Date createdate = new Date();
		UserVO user = new UserVO();
		user.setCreatedate(createdate);
		user.setLoginname(this.getPageElement("loginname").getValue());
		user.setStatus("1");
		user.setName(this.getPageElement("username").getValue());
		user.setPasswd(this.getPageElement("loginname").getValue());
		user.setDesc(this.getPageElement("description").getValue());
		String groupid = this.getRadow_parent_data();
		String userid = "";
		try {
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			PrivilegeManager.getInstance().getIUserControl().saveUser(user);
			List list = PrivilegeManager.getInstance().getIUserControl().queryByName(user.getLoginname(), true);
			userid = ((UserVO)list.get(0)).getId();
			if(groupid!=null && !groupid.equals("")){
				PrivilegeManager.getInstance().getIGroupControl().addUserToGroup(groupid, userid);
				this.createPageElement("memberGrid", ElementType.GRID, true).reload();
			}else{
				this.createPageElement("usergrid", ElementType.GRID, true).reload();
			}
			ts.commit();
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		if(acredit.equals("yes")) doAcredit(userid);
		this.setMainMessage("保存成功");
		this.closeCueWindowByYes("createUserWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("rolegrid.dogridquery")
	public int usergridDoQuery(int start, int limit) throws RadowException {
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		try {
			List rlist = PrivilegeManager.getInstance().getIRoleControl().getRolesByPrincipalId(cueUser.getId(),true);
			this.setSelfDefResData(PmHListUtil.getQueryData(rlist));
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	private int doAcredit(String userid) throws  RadowException{
		SceneVO scene = null;
		try {
			scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
		} catch (PrivilegeException e1) {
			throw new RadowException(e1.getMessage());
		}
		List<HashMap<String, Object>> list = this.getPageElement("rolegrid").getValueList();
		if(list == null || list.isEmpty()){
			return -1;
		}
		int temp = 0;
		for(int j=0;j<list.size();j++){
			HashMap<String, Object> map = list.get(j);
			Object usercheck = map.get("rolecheck");
			if(usercheck.equals(true)){
				String roleid=	(String) this.getPageElement("rolegrid").getValue("id", j);
				String isCan =   (String) this.getPageElement("rolegrid").getValue("isCan", j);
				boolean isCanDespacth = false;
				if(isCan.equals("1")){
					isCanDespacth=true;
				}
				try {
					PrivilegeManager.getInstance().getIRoleControl().grant(userid, scene.getSceneid(), roleid, isCanDespacth);
					temp++;
				} catch (PrivilegeException e) {
					throw new RadowException(e.getMessage());
				}
			}
		}
		return temp;
	}
	
	private int chooseCount() throws RadowException{
		int result = 0;
		PageElement pe = this.getPageElement("rolegrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("rolecheck");
			if(logchecked.equals(true)){
				result++;
			}
		}
		return result;//选中用户个数
	}

}
