package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.utils.CommonQueryBS;

public class PersonGridPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String checkregid = this.getPageElement("checkregid").getValue();
		/*String sql="select * from CHECKREGPERSON t where t.checkregid='"+checkregid+
				"'  order by SORTID1,SORTID2 ";*/
		String sql="select * from CHECKREGPERSON t where checkregid='"+checkregid+
				"' order by SORTID1,SORTID2 ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
}
