package com.insigma.siis.local.pagemodel.audit;

import java.util.Map;

import com.fr.stable.core.UUID;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.audit.AuditPersonFamily;

public class AuditPersonFamilyPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("familyGrid.dogridquery");
		return 0;
	}

	@NoRequiredValidate
	@PageEvent("familyGrid.dogridquery")
	public int BatchGridQuery(int start, int limit) throws RadowException {
		String id=this.getPageElement("subWinIdBussessId").getValue();

		StringBuffer sb = new StringBuffer("select * from AUDIT_PERSON_family  where p_oid ='"+id+"'  " );
		
		System.out.println("personsql:"+sb.toString());
		this.pageQuery(sb.toString(),"SQL",  start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("familyGrid.rowdbclick")
	@GridDataRange
	public int familyGridRowDbClick() throws RadowException{  //打开窗口的实例
		Map<String,Object>	map = (Map<String,Object>)this.getPageElement("familyGrid").getValueList().get(this.getPageElement("familyGrid").getCueRowIndex());
		String oid=(String)map.get("oid");
		HBSession sess=HBUtil.getHBSession();
		AuditPersonFamily apf=(AuditPersonFamily) sess.get(AuditPersonFamily.class, oid);
		this.copyObjValueToElement(apf, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("save.onclick")
	public int save() throws RadowException {
		String oid=this.getPageElement("oid").getValue();
		String poid=this.getPageElement("subWinIdBussessId").getValue();
		AuditPersonFamily apf=new AuditPersonFamily();
		HBSession sess=HBUtil.getHBSession();
		if(StringUtil.isEmpty(oid)) {
			this.copyElementsValueToObj(apf, this);
			apf.setPOid(poid);
			apf.setOid(UUID.randomUUID().toString());
		}else {
			apf=(AuditPersonFamily) sess.get(AuditPersonFamily.class, oid);
			this.copyElementsValueToObj(apf, this);
		}
		
		sess.saveOrUpdate(apf);
		sess.flush();
		this.getExecuteSG().addExecuteCode("clearValue()");
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gzGrid.dogridquery')");
		this.setNextEventName("familyGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@NoRequiredValidate
	@PageEvent("deleteFamily")
	public int deleteFamily(String oids) {
		HBSession sess=HBUtil.getHBSession();
		sess.createSQLQuery("delete from AUDIT_PERSON_family where oid in ("+oids+")").executeUpdate();
		this.getExecuteSG().addExecuteCode("clearValue()");
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gzGrid.dogridquery')");
		this.setNextEventName("familyGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
