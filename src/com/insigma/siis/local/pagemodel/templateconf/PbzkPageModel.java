package com.insigma.siis.local.pagemodel.templateconf;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class PbzkPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("pbzk.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("pbzk.dogridquery")
	@NoRequiredValidate
	public int pbzkGridQuery(int start,int limit) throws RadowException{
		String unid = this.getPageElement("unid").getValue();
		System.out.println(unid);
		HBSession sess =HBUtil. getHBSession ();
		String sql ="select distinct a01.a0000 a0000,a01.a0101 a0101,a01.a0192 a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+unid+"'";
		
	     this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
	
	@PageEvent("confirm")
	public int confirm(String names)throws  RadowException{
		closeCueWindowEX();
		this.getExecuteSG().addExecuteCode("realParent.addNames('"+names+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("close")
	public int close()throws  RadowException{
		closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}

}
