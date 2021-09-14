package com.insigma.siis.local.pagemodel.notice;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class NoticeInfoPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("NoticeInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 标记为已查看
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("NoticeInfo")
	@NoRequiredValidate
	public int NoticeInfo() throws RadowException{
		//this.getExecuteSG().addExecuteCode("parent.location.reload(); ");
		//this.getExecuteSG().addExecuteCode("window.parent.parent.Ext.getCmp('noticeSetgrid').store.reload();");//刷新人员列表
		//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('noticeSetgrid').getStore().reload()");//刷新人员列表
		
		this.getExecuteSG().addExecuteCode("try{" +
				"if(parent.document.getElementById('I11111')){" +
				"	var personListWindow = parent.document.getElementById('I11111').contentWindow;" +
				"	personListWindow.Ext.getCmp('noticeSetgrid').store.reload();}" +
				"}catch(e){} "
			);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}
