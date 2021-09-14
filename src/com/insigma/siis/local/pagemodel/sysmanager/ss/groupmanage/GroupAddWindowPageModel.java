package com.insigma.siis.local.pagemodel.sysmanager.ss.groupmanage;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class GroupAddWindowPageModel extends PageModel {

	@PageEvent("saveBtn.onclick")
	@Transaction
	public int saveBtnOnClick() throws RadowException{
		try {
			GroupVO group = new GroupVO();
			this.copyElementsValueToObj(group, this);
			group.setName(this.getPageElement("name").getValue().trim());
			group.setDesc(this.getPageElement("desc").getValue().trim());
			group.setStatus(this.getPageElement("status").getValue().trim());
			group.setAddress(this.getPageElement("address").getValue().trim());
			group.setChargedept(this.getPageElement("chargedept").getValue().trim());
			group.setDistrictcode(this.getPageElement("districtcode").getValue().trim());
			group.setLinkman(this.getPageElement("linkman").getValue().trim());
			group.setOrg(this.getPageElement("org").getValue());
			group.setOtherinfo(this.getPageElement("otherinfo").getValue().trim());
			group.setPrincipal(this.getPageElement("principal").getValue().trim());
			group.setTel(this.getPageElement("tel").getValue().trim());
			group.setShortname(this.getPageElement("shortname").getValue().trim());
			String parentid = this.getRadow_parent_data();
			if(!parentid.equals("")) group.setParentid(parentid);
			PrivilegeManager.getInstance().getIGroupControl().saveGroup(group);
		} catch (Exception e) {
			throw new RadowException("增加失败:"+e.getMessage());
		}
		this.createPageElement("groupTreeContent", ElementType.WEBPAGE, true).reload();
		this.setMainMessage("增加用户组成功!");
		this.closeCueWindowByYes("addWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit(){
		try {
			String parentid = this.getRadow_parent_data();
			if(parentid==null || parentid.equals("")){
				this.setMainMessage("请注意,您未选择任何父节点！点击确定可继续操作！");
			}
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	



	
	
	
	@PageEvent("rate.onchange")
	@NoRequiredValidate
	public int rateOnchange() throws RadowException, PrivilegeException{
		String crate=this.getPageElement("rate").getValue();
		String parentid = this.getRadow_parent_data();
		if(parentid!=null && !parentid.equals("")){
			GroupVO pvo =PrivilegeManager.getInstance().getIGroupControl().findById(parentid);
			
			String prate=pvo.getRate();
			if(prate!=null){
				if(Long.parseLong(crate)<=Long.parseLong(prate)){
					this.setMainMessage("新增用户组的级别不能高于他的");
					this.getPageElement("rate").setValue("");
				}
			}
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
