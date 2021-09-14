package com.insigma.siis.local.pagemodel.comm;

import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.comm.VerWindowBS;
import com.lbs.leaf.exception.BusinessException;

public class VerWindowPageModel extends PageModel{
	/**
	 * 初始化
	 */
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		try {
			String v_name = VerWindowBS.getVersionName();
			//this.getPageElement("v_name").setValue(v_name);
			this.getExecuteSG().addExecuteCode("document.getElementById('name').innerHTML='版本号："+v_name+"';" );//html元素接收
			String v_time = VerWindowBS.getVersionTime();
			//this.getPageElement("v_time").setValue(v_time);
			this.getExecuteSG().addExecuteCode("document.getElementById('time').innerHTML='更新时间："+v_time+"';" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//throw new BusinessException("获取版本信息失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

}
