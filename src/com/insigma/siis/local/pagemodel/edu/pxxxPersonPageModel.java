package com.insigma.siis.local.pagemodel.edu;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.TrainPersonnel;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class pxxxPersonPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String a0000 = this.getPageElement("subWinIdBussessId2").getValue();
		this.getPageElement("a0000").setValue(a0000);

		String sql = "select a.a0101,a.a0104,a.a0184,a.a0192a from a01 a where a.a0000='"+a0000+"'  ";
		CommQuery commQuery =new CommQuery();
		try {
			List<HashMap<String, Object>> list = commQuery.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("a0101").setValue(isnull(map.get("a0101")));
				this.getPageElement("a0104").setValue(isnull(map.get("a0104")));
				this.getPageElement("a0184").setValue(isnull(map.get("a0184")));
				//this.getPageElement("a1108").setValue(isnull(map.get("a1108")));
				this.getPageElement("a0192a").setValue(isnull(map.get("a0192a")));
			}
		} catch (AppException e) {
			
			e.printStackTrace();
		}
		this.setNextEventName("traingrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String isnull(Object obj) {
		String str="";
		if(obj!=null&&!"".equals(obj)) {
			str=obj.toString();
		}
		return str;
	}
	
	@PageEvent("traingrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		
		String sql = " select t.xrdx00,t.xrdx01,t.year,(select code_name from code_value where code_type='PXLX' and code_value=t.xrdx02) xrdx02," + 
				" (select code_name from code_value where code_type='TRANORG' and code_value=t.xrdx03) xrdx03," + 
				" (select code_name from code_value where code_type='TRANCRJ' and code_value=t.xrdx04) xrdx04," + 
				" substr(t.xrdx05,0,4) || '.'||  substr(t.xrdx05,5,2) || '.' || substr(t.xrdx05,7,2) xrdx05," + 
				" substr(t.xrdx06,0,4) || '.'||  substr(t.xrdx06,5,2) || '.' || substr(t.xrdx06,7,2) xrdx06," + 
				" t.xrdx08,t.xrdx09,t.xrdx10," + 
				" (select code_name from code_value where code_type='TRANINS' and code_value=t.xrdx11) xrdx11," + 
				" (select code_name from code_value where code_type='ZB27' and code_value=t.xrdx12) xrdx12" + 
				" from edu_xrdx t,edu_xrdx_ry t1 where t.xrdx00=t1.xrdx00 and t1.a0000='"+this.getPageElement("a0000").getValue()+
				"'  order by t.xrdx05 desc ";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
}
