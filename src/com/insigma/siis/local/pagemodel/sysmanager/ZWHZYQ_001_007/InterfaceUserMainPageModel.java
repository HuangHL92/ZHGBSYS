package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;

public class InterfaceUserMainPageModel extends PageModel{

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("list.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 将所有接口用户显示出来
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("list.dogridquery")
	public int queryObj(int start, int limit) throws RadowException{
		String sql = "select "
				   + "user_id,user_name,password,real_name "
				   + "from interface_user";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 新增接口用户
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("btnAdd.onclick")
	public int add() throws RadowException {
		this.setRadow_parent_data("NEW");
		this.openWindow("NewInterfaceUser", "pages.sysmanager.ZWHZYQ_001_007.InterfaceUserCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 修改用户
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("edit")
	public int edit(String id) throws RadowException {
		this.setRadow_parent_data(id);
		this.openWindow("EditInterfaceUser", "pages.sysmanager.ZWHZYQ_001_007.InterfaceUserCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("del")
	public int del(String id) throws RadowException {
		String sql = "delete from Interface_user where user_id='"+id+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		this.setNextEventName("list.dogridquery");
		bs7.refreshUsersLst();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
