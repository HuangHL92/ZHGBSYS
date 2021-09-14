package com.insigma.siis.local.pagemodel.sysmanager.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class GroupWindowPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException{
		String name = this.getRadow_parent_data();
		this.getPageElement("selectGroupname").setValue(name);
		this.setNextEventName("groupgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("searchBtn.onclick")
	public int searchUser() throws RadowException{
		String name = this.getPageElement("groupname").getValue();
		if(name == null ||name.trim().equals("")){
			this.getPageElement("selectGroupname").setValue("searchAll");
		}else{
			this.getPageElement("selectGroupname").setValue(name);
		}
		this.setNextEventName("groupgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("groupgrid.dogridquery")
	public int doQuery(int start,int limit) throws RadowException{
		PageQueryData pq = new PageQueryData();
		String name = this.getPageElement("selectGroupname").getValue();
		HashMap<String,String> params = null;
		if(!name.equals("searchAll")){
			params = new HashMap<String,String>();
			params.put("name", name);
		}
		try { 
			pq = PrivilegeManager.getInstance().getIGroupControl().pageQueryContantParentNameByUser(PrivilegeManager.getInstance().getCueLoginUser(), start, limit, params);
		} catch (PrivilegeException e) {
			this.setMainMessage("queryByUser失败"+e.getMessage());
		}
		this.setSelfDefResData(pq);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("groupgrid.rowdbclick")
	public int groupgridOnRowDbClick() throws RadowException{
		String groupname = this.getPageElement("groupgrid").getValue("name").toString();
		String groupid = this.getPageElement("groupgrid").getValue("id").toString();
		this.createPageElement("optionGroup", ElementType.TEXT, true).setValue("请点击组织名查询");
		this.createPageElement("searchGroupBtn", ElementType.TEXTWITHICON, true).setValue(groupname);
		this.createPageElement("checkedgroupid", ElementType.HIDDEN, true).setValue("");
		this.createPageElement("forsearchgroupid", ElementType.HIDDEN, true).setValue(groupid);
		this.closeCueWindow("groupWin");
		this.createPageElement("memberGrid", ElementType.GRID, true).setValueList(null);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogridgrant")
	public int groupgridChange(String groupid) throws RadowException{
		this.closeCueWindow("groupWin");
		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
			if(group == null){
				throw new RadowException("查询错误");
			}
			this.createPageElement("optionGroup", ElementType.TEXT, true).setValue("请点击组织名查询");
			this.createPageElement("searchGroupBtn", ElementType.TEXTWITHICON, true).setValue(group.getName());
			this.createPageElement("checkedgroupid", ElementType.HIDDEN, true).setValue("");
			this.createPageElement("forsearchgroupid", ElementType.HIDDEN, true).setValue(groupid);
			this.createPageElement("memberGrid", ElementType.GRID, true).setValueList(null);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	private List getGroupMemberToHashMapList(String groupid, int model) throws PrivilegeException{

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
	
	private String getOwnerOrParentName(String id, boolean isOwner) throws RadowException{
		String name = "";
		try {
			if(isOwner){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(id);
				name = user.getName();
			}else{
				GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
				name = group.getName();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("查询持有者或父类组异常");
		}
		return name;
	}
}
