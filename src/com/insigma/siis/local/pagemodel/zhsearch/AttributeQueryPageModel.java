package com.insigma.siis.local.pagemodel.zhsearch;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.Attribute;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AttributeQueryPageModel extends PageModel {

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryData")
	@Synchronous(true)
	@NoRequiredValidate
	public int queryData(String param)throws RadowException, AppException, InterruptedException{
		HBSession session = HBUtil.getHBSession();
		StringBuilder sbsql = new StringBuilder();
		//过滤权限
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		if(cueUserid.equals("40288103556cc97701556d629135000f")){//对system用户放行
			sbsql.append("select a0000 from ATTRIBUTE where 1=1 ");
		}else{
			sbsql.append("select a0000 from ATTRIBUTE where a0000 in (select a0000 from a02 where a0201b in (select b0111 from competence_userdept where userid='"+cueUserid+"')) ");
		}
		String[] split = param.split(",");
		for(String str:split){
			sbsql.append("and "+str+"='1' ");
		}
		List<String> list_a0000 = session.createSQLQuery(sbsql.toString()).list();
		if(list_a0000.size()==0){
			this.setMainMessage("本次查询没有查询到人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+list_a0000+"');");
		this.getExecuteSG().addExecuteCode("window.realParent.setAttributeFields('"+param+"')");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('AttributeWin').close();");
		//this.getExecuteSG().addExecuteCode("Ext.WindowMgr.getActive().close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
