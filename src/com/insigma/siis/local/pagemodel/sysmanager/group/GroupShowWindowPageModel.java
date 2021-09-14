package com.insigma.siis.local.pagemodel.sysmanager.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class GroupShowWindowPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException{
		this.getPageElement("selectUsername").setValue(this.getRadow_parent_data());
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("searchUserBtn.onclick")
	public int searchUser() throws RadowException{
		String username = this.getPageElement("username").getValue();
		if(username == null ||username.trim().equals("")){
			this.getPageElement("selectUsername").setValue("searchAll");
		}else{
			this.getPageElement("selectUsername").setValue(username);
		}
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("usergrid.dogridquery")
	public int userQuery(int start,int limit) throws RadowException{
		String name = this.getPageElement("selectUsername").getValue();
		PageQueryData pq = new PageQueryData();
		try {
			if(name.equals("searchAll")){
				pq = PrivilegeManager.getInstance().getIUserControl().pageQueryByUserVO(PrivilegeManager.getInstance().getCueLoginUser(), start, limit, null);
			}else{
				HashMap<String,String> params = new HashMap<String,String>();
				params.put("loginname", name);
				pq = PrivilegeManager.getInstance().getIUserControl().pageQueryByUserVO(PrivilegeManager.getInstance().getCueLoginUser(), start, limit, params);
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.setSelfDefResData(pq);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("usergrid.rowclick")
	@GridDataRange
	public int usergridClick() throws RadowException{
		int rowIndex = this.getPageElement("usergrid").getCueRowIndex();
		String userid = this.getPageElement("usergrid").getValue("id",rowIndex).toString();
		this.getPageElement("selectUserid").setValue(userid);
		this.setNextEventName("groupgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("usergrid.rowdbclick")
	@GridDataRange
	public int usergriddbClick() throws RadowException{
		this.openWindow("updateUserWin", "pages.sysmanager.user.FWindow");
		int rowIndex = this.getPageElement("usergrid").getCueRowIndex();
		String loginname = this.getPageElement("usergrid").getValue("loginname",rowIndex).toString();
		this.setRadow_parent_data(loginname);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("groupgrid.dogridquery")
	public int groupQuery(int start,int limit) throws RadowException{
		String userid = this.getPageElement("selectUserid").getValue();
		List groups = new ArrayList(); 
		try {
			groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(userid);
		} catch (PrivilegeException e1) {
			throw new RadowException(e1.getMessage());
		}
		this.setSelfDefResData(this.getPageQueryData(groups, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("groupgrid.rowdbclick")
	public int groupgridOnRowDbClick() throws RadowException{
		String groupname = this.getPageElement("groupgrid").getValue("name").toString();
		String groupid = this.getPageElement("groupgrid").getValue("id").toString();
		this.createPageElement("searchGroupBtn", ElementType.TEXTWITHICON, true).setValue(groupname);
		this.createPageElement("optionGroup", ElementType.TEXT, true).setValue("请点击组织名查询");
		this.createPageElement("checkedgroupid", ElementType.TEXT, true).setValue("");
		this.createPageElement("forsearchgroupid", ElementType.HIDDEN, true).setValue(groupid);
		this.closeCueWindow("userShowWin");
		this.createPageElement("memberGrid", ElementType.GRID, true).setValueList(null);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogridgrant")
	public int groupgridChange(String groupid) throws RadowException{
		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
			if(group == null){
				throw new RadowException("查询错误");
			}
			this.createPageElement("searchGroupBtn", ElementType.TEXTWITHICON, true).setValue(group.getName());
			this.createPageElement("optionGroup", ElementType.TEXT, true).setValue("请点击组织名查询");
			this.createPageElement("checkedgroupid", ElementType.HIDDEN, true).setValue("");
			this.createPageElement("forsearchgroupid", ElementType.HIDDEN, true).setValue(groupid);
			this.closeCueWindow("userShowWin");
			this.createPageElement("memberGrid", ElementType.GRID, true).setValueList(new ArrayList());
			//this.getExecuteSG().addExecuteCode("parent.execute('searchGroupBtn.ontriggerclick');");
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	} 	
	
	private List getGroupMember(String groupid, int model) throws PrivilegeException{

		List alist = new ArrayList();
		List<UserVO> mlist = new ArrayList();
		try {
			alist = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
			mlist = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_LEADER);
		} catch (PrivilegeException e) {
			this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
		}
		for(int i=0;i<alist.size();i++){
			UserVO member = (UserVO)alist.get(i);
			member.setIsleader("0");
			if(mlist != null){
				for(int j=0;j<mlist.size();j++){
					UserVO manager = mlist.get(j);
					if(member.getId().equals(manager.getId())){
						member.setIsleader("1");
					}
				}
			}
		}
		if(alist.isEmpty()){
			return null;
		}
		
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<alist.size();i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			UserVO user = (UserVO)alist.get(i);
			map.put("logchecked", false);
			map.put("id", user.getId());
			map.put("loginname", user.getLoginname());
			map.put("name", user.getName());
			map.put("desc", user.getDesc());
			map.put("isleader", user.getIsleader());
			list.add(map);
		}
		
		return list;
	}
}
