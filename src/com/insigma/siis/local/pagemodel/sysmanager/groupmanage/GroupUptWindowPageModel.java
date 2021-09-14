package com.insigma.siis.local.pagemodel.sysmanager.groupmanage;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class GroupUptWindowPageModel extends PageModel {

	@PageEvent("uptBtn.onclick")
	@Transaction
	public int saveBtnOnClick() throws RadowException{
		GroupVO group = new GroupVO();
		String data = this.getRadow_parent_data();
		String[] datas = data.split(",");
		String groupid = datas[0];
		try {
			group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.copyElementsValueToObj(group, this);
		group.setName(this.getPageElement("name1").getValue().trim());
		group.setDesc(this.getPageElement("desc1").getValue().trim());
		group.setAddress(this.getPageElement("address1").getValue().trim());
		group.setChargedept(this.getPageElement("chargedept1").getValue().trim());
		group.setDistrictcode(this.getPageElement("districtcode1").getValue().trim());
		group.setLicense(this.getPageElement("license1").getValue().trim());
		group.setLinkman(this.getPageElement("linkman1").getValue().trim());
		group.setOrg(this.getPageElement("org1").getValue().trim());
		group.setOtherinfo(this.getPageElement("otherinfo1").getValue().trim());
		group.setPrincipal(this.getPageElement("principal1").getValue().trim());
		group.setTel(this.getPageElement("tel1").getValue().trim());
		group.setShortname(this.getPageElement("shortname1").getValue().trim());
		try {
			PrivilegeManager.getInstance().getIGroupControl().updateGroup(group);
		} catch (PrivilegeException e) {
			throw new RadowException("更新失败"+e.getMessage());
		}
//		if(datas[1].equals("groupShowWin")){
//			this.createPageElement("groupgrid", ElementType.GRID, true).reload();
//		}
		this.setMainMessage("更新成功");
		this.closeCueWindowByYes("uptWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("uptSpeBtn.onclick")
	@EventDataCustomized("ownerName,parentName")
	public int saveSpeBtn() throws RadowException{
		GroupVO group = new GroupVO();
		String data = this.getRadow_parent_data();
		String[] datas = data.split(",");
		String groupid = datas[0];
		try {
			group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		PageElement peow = this.getPageElement("ownerName");
		PageElement pepa = this.getPageElement("parentName");
		String ownerName = "";
		String parentName = "";
		if(peow != null){
			ownerName = peow.getValue().trim();
		}
		if(pepa != null){
			parentName = pepa.getValue().trim();
		}
		if(!ownerName.equals("")){
			String ownerId = getOwnerIdorParentidByName(ownerName,true);
			if(ownerId.equals("")){
				this.setMainMessage("该持有者不存在,请重新输入");
				return EventRtnType.NORMAL_SUCCESS;
			}
			group.setOwnerId(ownerId);
		}
		if(!parentName.equals("")){
			String parentid = getOwnerIdorParentidByName(parentName,false);
			if(parentid.equals("")){
				this.setMainMessage("该父类组不存在,请重新输入");
				return EventRtnType.NORMAL_SUCCESS;
			}
			group.setParentid(parentid);
		}
		if(ownerName.equals("") || parentName.equals("")){
			this.setMainMessage("请输入需要更改的持有者或父类组");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			PrivilegeManager.getInstance().getIGroupControl().updateGroup(group);
			if(datas[1].equals("groupShowWin")){
				this.createPageElement("groupgrid", ElementType.GRID, true).reload();
			}
			this.setMainMessage("更新成功");
		} catch (PrivilegeException e) {
			this.setMainMessage("更新失败"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException{
		this.isShowMsg = false;
		String data = this.getRadow_parent_data();
		String[] datas = data.split(",");
		String groupid = datas[0];
		GroupVO group = new GroupVO();
		try {
			group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		if(group.getOwnerId() != null)
			this.getPageElement("ownerName").setValue(getOwnerOrParentName(group.getOwnerId(),true));
		if(group.getParentid() != null)
			this.getPageElement("parentName").setValue(getOwnerOrParentName(group.getParentid(),false));
		if(group.getName() != null)
			this.getPageElement("name1").setValue(group.getName());
		if(group.getDesc() != null)
			this.getPageElement("desc1").setValue(group.getDesc());
		if(group.getAddress() != null)
			this.getPageElement("address1").setValue(group.getAddress());
		if(group.getChargedept() != null)
			this.getPageElement("chargedept1").setValue(group.getChargedept());
		if(group.getDistrictcode() != null)
			this.getPageElement("districtcode1").setValue(group.getDistrictcode());
		if(group.getLicense() != null)
			this.getPageElement("license1").setValue(group.getLicense());
		if(group.getLinkman() != null)
			this.getPageElement("linkman1").setValue(group.getLinkman());
		if(group.getOrg() != null)
			this.getPageElement("org1").setValue(group.getOrg());
		if(group.getOtherinfo() != null)
			this.getPageElement("otherinfo1").setValue(group.getOtherinfo());
		if(group.getPrincipal() != null)
			this.getPageElement("principal1").setValue(group.getPrincipal());
		if(group.getTel() != null)
			this.getPageElement("tel1").setValue(group.getTel());
		if(group.getType() != null)
			this.getPageElement("type1").setValue(group.getType());
		if(group.getShortname() != null)
			this.getPageElement("shortname1").setValue(group.getShortname());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String getOwnerIdorParentidByName(String name, boolean isOwner) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String hql = "";
		if(isOwner){
			hql = "select id from SmtUser where loginname=:name";
		}else{
			hql = "select id from SmtGroup where name=:name";
		}
		List<String> list = session.createQuery(hql).setString("name", name).list();
		if(list == null || list.isEmpty()){
			return "";
		}
		return list.get(0);
	}
	
	private String getOwnerOrParentName(String id, boolean isOwner) throws RadowException{
		String name = "";
		try {
			if(isOwner){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(id);
				name = user.getLoginname();
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