package com.insigma.siis.local.pagemodel.dataverify;

import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sample.ProductBS;

public class DataVerifyPageModel extends PageModel{
	
	@PageEvent("doFile")
	@Transaction
	@OpLog
	@NoRequiredValidate
	public int doFile(String path)throws RadowException{
		String filename = path.substring((path.lastIndexOf("\\") + 1) , path.lastIndexOf("."));
		String houzhui = path.substring(path.lastIndexOf(".")+1, path.length());
		if(houzhui.equalsIgnoreCase("zb3")){
			String srr[] = filename.split("_");
			String org = srr[2].substring(0, srr[2].length()-14);
			List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "'").list();
			if (b01s!=null && b01s.size()>0) {
				for (B01 b01 : b01s) {
					this.getPageElement("b0101").setValue(org);
					this.getPageElement("b0111").setValue(b01.getB0111());
					this.getPageElement("b0131").setValue(b01.getB0131());
					this.getPageElement("valueimp").setValue("1");
					this.getPageElement("orginfo").setValue("已匹配到机构，可以导入。");
				}
			} else {
				this.getPageElement("valueimp").setValue("2");
				this.getPageElement("orginfo").setValue("为匹配到机构，不能导入。");
			}
			String time = srr[2].substring(srr[2].length()-14);
			
			this.getPageElement("gjgs").setValue("2");
			this.getPageElement("b0101").setValue(org);
			this.getPageElement("packagetype").setValue(srr[0]);
			this.getPageElement("packagetime").setValue(DateUtil.dateToString(DateUtil.stringToDate(time, "yyyyMMddhh24miss"), "yyyyMMdd hh24:mi:ss"));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {

		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
