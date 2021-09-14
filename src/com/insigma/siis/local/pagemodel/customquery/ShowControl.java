package com.insigma.siis.local.pagemodel.customquery;

import com.insigma.odin.framework.persistence.HBUtil;

public class ShowControl {
	//
	public String getPpsResult() {
		String pps_isuseful = HBUtil.getHBSession().createSQLQuery("select aaa005 from aa01 where aaa001 = 'PPS_ISUSEFUL'").uniqueResult().toString();
		
		return pps_isuseful;
		
	}
}
