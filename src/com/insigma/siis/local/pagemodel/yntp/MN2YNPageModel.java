package com.insigma.siis.local.pagemodel.yntp;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class MN2YNPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("noticeSetgrid.dogridquery");
		return 0;
	}

	
	
	/**
	 *  查询未匹配人员信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		String sql="select y.*,u.mnur04 from hz_mntp y,hz_mntp_userref u where y.mntp00=u.mntp00 and"
				+ " u.mnur01='"+SysManagerUtils.getUserId()+"' order by mntp02 desc";
		
	  this.pageQuery(sql, "SQL", start, limit);
	  
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
}
