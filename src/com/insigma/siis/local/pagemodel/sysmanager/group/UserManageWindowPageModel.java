package com.insigma.siis.local.pagemodel.sysmanager.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PmHListUtil;

public class UserManageWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.isShowMsg=false;
		this.setNextEventName("userGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryBtn.onclick")
	public int queryOnClick() throws RadowException{
		try {
			List list = getUserList();
			if(list == null){
				this.createPageElement("userGrid", ElementType.GRID, false).setValueList(null);
			}else{
				this.setNextEventName("userGrid.dogridquery");
			}
		} catch (Exception e) {
			this.setMainMessage("根据当用户查找可见用户失败："+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("userGrid.dogridquery")
	@EventDataCustomized("userloginname")
	public int doSeeUserQuery(int start,int limit) throws Exception{
		String groupid = this.getRadow_parent_data();
		List sList = getUserList();
		List uList = new ArrayList();
		uList = unGroupM(sList,groupid);
		this.setSelfDefResData(this.getPageQueryData(uList, start, limit));
		//this.setSelfDefResData(PmHListUtil.getQueryData(uList));
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("addUserBtn.onclick")
	public int AddUser(String userid) throws RadowException{
		int count = choosePerson("userGrid");
		if(count == 0){
			this.setMainMessage("请选择需要增加到组的用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doAddUser",userid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		if(count == 1)
			this.setMainMessage("确定将该用户增加到用户组嘛？");
		if(count >1)
			this.setMainMessage("确定要将这"+count+"名用户增加到用户组嘛？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doAddUser")
	public int doAddUser() throws RadowException{
		String groupid = this.getRadow_parent_data();
		this.createPageElement("checkedgroupid", ElementType.HIDDEN, true).setValue(groupid);
		try {
			boolean result = PrivilegeManager.getInstance().getIGroupControl().addUserToGroup(groupid, choosePersonIds("userGrid"));
			if(result){
				this.setMainMessage("增加组成员成功");
				this.createPageElement("memberGrid", ElementType.GRID, true).reload();
				this.createPageElement("userGrid", ElementType.GRID, false).reload();
			}
		} catch (Exception e) {
			this.setMainMessage("增加用户到组是异常："+e.getMessage());
			System.out.println("***增加到组失败："+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private List unGroupM(List list, String groupid){
		List mlist = new ArrayList();
		try {
			mlist = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
		} catch (PrivilegeException e) {
			this.setMainMessage("获取用户成员失败："+e.getMessage());
		}
		if(mlist == null || mlist.isEmpty()){
			return list;
		}
		for(int i=0;i<list.size();i++){
			UserVO user = new UserVO();
			user = (UserVO)list.get(i);
			if(user.getStatus().equals("0")){
				list.remove(i);
				i--;
			}else{
				for(int j=0;j<mlist.size();j++){
					UserVO mUser = new UserVO();
					mUser = (UserVO)mlist.get(j);
					if(user.getId().equals(mUser.getId())){
						list.remove(i);
						i--;
					}
				}
			}
		}
		return list;
	}
	
	private int choosePerson(String grid) throws RadowException{
		int result = 0;
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				result++;
			}
		}
		return result;//选中用户个数
	}
	
	private String choosePersonIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String userIds = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String userid = (String) this.getPageElement(grid).getValue("id", i);
				if(userIds.equals("")){
					userIds += userid;
				}
				else{
					userIds += ","+userid;
				}
			}
		}
		return userIds;
	}
	
	private List getUserList() throws Exception{
		HashMap<String,String> params = new HashMap<String,String>();
		PageElement pe = this.getPageElement("userloginname");
		if(pe !=null && !pe.getValue().equals("")){
			params.put("loginname", pe.getValue());
		}else{
			params = null;
		}
		List sList = new ArrayList();
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		sList = PrivilegeManager.getInstance().getIUserControl().queryByUser(cueUser, 0, -1, params);
		if(sList == null || sList.isEmpty()){
			return null;
		}
		return sList;
	}

}
