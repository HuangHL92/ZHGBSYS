package com.insigma.siis.local.pagemodel.sysmanager.sysuser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class GroupAddWindowPageModel extends PageModel {

	@PageEvent("saveBtn.onclick")
	public int saveBtnOnClick() throws RadowException{
		GroupVO group = new GroupVO();
		try {
			String name = this.getPageElement("name").getValue();
			if(name.indexOf(" ")!=-1){
				this.setMainMessage("用户组名中不能包含空格");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.copyElementsValueToObj(group, this);
			group.setName(this.getPageElement("name").getValue().trim());
			String parentid = this.getRadow_parent_data();
			if(!parentid.equals("")) group.setParentid(parentid);
			Long sortID = getSortid(parentid);
			group.setSortid(sortID);
			
			PrivilegeManager.getInstance().getIGroupControl().saveGroup(group);
		} catch (Exception e) {
			throw new RadowException("增加失败:"+e.getMessage());
		}
		this.createPageElement("groupTreeContent", ElementType.WEBPAGE, true).reload();
		try {
			new LogUtil().createLog("61", "SMT_GROUP",group.getId(), group.getName(), "", new Map2Temp().getLogInfo(group,new GroupVO()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("增加用户组成功");
		this.closeCueWindowByYes("createGroupWin");
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 *  获取用户组的排序编号
	 * @param parentid
	 * @return
	 */
	public Long getSortid(String parentid){
		
		String sql = "select max(t.sortid)+1 sortid from smt_group t where t.parentid='"+ parentid.trim() + "'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Long sortid = 1L;
		if (list.get(0) == null) {
			sortid = 1L;
		} else {
			sortid = Long.parseLong(list.get(0).toString());
		}
		
		return sortid;
	}
	
	@Override
	public int doInit(){
		this.isShowMsg = false;
		return EventRtnType.NORMAL_SUCCESS;
	}
}
