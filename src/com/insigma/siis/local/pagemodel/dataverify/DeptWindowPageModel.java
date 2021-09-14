package com.insigma.siis.local.pagemodel.dataverify;

import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;

public class DeptWindowPageModel extends PageModel {
	
	//点击树事件
	@PageEvent("submitvalue.onclick")
	@NoRequiredValidate
	public int submitValue(String id) throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String groupname = this.getPageElement("optionGroup").getValue();
		this.createPageElement("searchDeptBtn", ElementType.HIDDEN, true).setValue(groupname);
		this.createPageElement("searchDeptid", ElementType.HIDDEN, true).setValue(groupid);
		this.closeCueWindow("deptWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击树查询事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		try {
			String sql="from B01 where B0111='"+id+"'";
			List list = HBUtil.getHBSession().createQuery(sql).list();
			B01 b01 =(B01) list.get(0);
			if(b01 == null){
				throw new RadowException("查询错误");
			}
			this.getPageElement("optionGroup").setValue(b01.getB0101());
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
