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

public class NoticeRecipentWindowPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("noticeRecipentgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 通知公告查看情况列表数据加载
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeRecipentgrid.dogridquery")
	@NoRequiredValidate
	public int noticeSetgrid(int start,int limit) throws RadowException{
		String noticeId = this.getPageElement("subWinIdBussessId").getValue();
		
		String sql = "select * from NOTICERECIPIENT where noticeId='"+noticeId+"' order by see";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
}
