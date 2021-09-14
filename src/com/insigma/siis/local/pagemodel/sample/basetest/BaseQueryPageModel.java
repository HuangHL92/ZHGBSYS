package com.insigma.siis.local.pagemodel.sample.basetest;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class BaseQueryPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("showPageTime();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid1.dogridquery")
	@GridDataRange(GridData.cuerow)
	public int grid1quey(int start,int limit) throws RadowException {
		StopWatch sw=new StopWatch();
		sw.start();
		StringBuffer sql=new StringBuffer("select * from KA02 where aae016='1'");
		if(getPageElement("ake001").getValue().trim().length()>0) {
			sql.append(" and ake001 like '%"+getPageElement("ake001").getValue()+"%'");
		}
		if(getPageElement("ake002").getValue().trim().length()>0) {
			sql.append(" and ake002 like '%"+getPageElement("ake002").getValue()+"%'");
		}
		if(getPageElement("saka020").getValue().trim().length()>0) {
			sql.append(" and Upper(aka020) like '%"+getPageElement("saka020").getValue().toUpperCase()+"%'");
		}
		if(getPageElement("saka021").getValue().trim().length()>0) {
			sql.append(" and Upper(aka021) like '%"+getPageElement("saka021").getValue().toUpperCase()+"%'");
		}
		if(getPageElement("saka065").getValue().trim().length()>0) {
			sql.append(" and aka065='"+getPageElement("saka065").getValue()+"'");
		}
		if(getPageElement("saae100").getValue().trim().length()>0) {
			sql.append(" and aae100='"+getPageElement("saae100").getValue()+"'");
		}
		
		if (getPageElement("caka094").getValue().trim().length() > 0) {
			sql.append(" and aka094 like '%" + getPageElement("caka094").getValue()
					+ "%'");
		}
		
		if (getPageElement("cbka009").getValue().trim().length() > 0) {
			sql.append(" and bka009 like '%" + getPageElement("cbka009").getValue()
					+ "%'");
		}
		
		sql.append(" order by ake001");
		this.pageQuery(sql.toString(), "SQL", start, limit);
		sw.stop();
		CommonQueryBS.systemOut(sw.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}
}
