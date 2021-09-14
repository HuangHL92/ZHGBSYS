package com.insigma.siis.local.pagemodel.fxyp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Association;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.publicServantManage.AddRmbPageModel;

import net.sf.json.JSONArray;

public class NMGWBDPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("nrGrid.dogridquery");
//		this.setExecuteSG(executeSG);
//		this.setNextEventName("pgrid.dogridquery");
//		this.setNextEventName("pgrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("nrGrid.dogridquery")
	@NoRequiredValidate
	public int nrquery(int start,int limit)  throws RadowException, AppException, PrivilegeException{
		String a0200=this.getPageElement("a0200").getValue();
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b0111 = this.getPageElement("b0111").getValue();
		String zrrx = this.getPageElement("zrrx").getValue();
		String sql="";
		try {
			@SuppressWarnings("unchecked")
			List<String> fxyp00nmList= HBUtil.getHBSession().createSQLQuery("select fxyp00 from fxyp where a0200='"+a0200+"' and  mntp00 = '"+mntp00+"'" + 
					" and b01id=(select b01id from b01 where b0111= '"+b0111+"') and fxyp06='"+zrrx+"'").list();
			String fxyp00nm="";
			if(fxyp00nmList.size()>0) {
				fxyp00nm=fxyp00nmList.get(0);
			}
			if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
				sql="select * from (select fxyp06 zrrx ,fxyp07,fxyp02,fxyp00,FXYP00REF" + 
						"                  from hz_mntp_gw t,               " + 
						"                       (select *" + 
						"                          from GWSORT" + 
						"                         where mntp00 = '"+mntp00+"'" + 
						"                           and sorttype = '2') b" + 
						"                 where  t.fxyp07 = 1" + 
						"                   and t.mntp00 ='"+mntp00+"'" + 
						"                   and t.b01id = (select b01id from b01 where b0111='"+b0111+"')" + 
						"                   and  b.SORTID(+) = t.fxyp00 " + 
						"                   and fxyp06='"+zrrx+"'" + 
						"                  and (fxyp00ref='' or fxyp00ref is null or  fxyp00ref='"+fxyp00nm+"')"+
						"                 order by t.fxyp00)" ;
			}else {
				sql="select * from (select fxyp06 zrrx ,fxyp07,fxyp02,fxyp00,FXYP00REF" + 
						"                  from hz_mntp_gw t,               " + 
						"                       (select *" + 
						"                          from GWSORT" + 
						"                         where mntp00 = '"+mntp00+"'" + 
						"                           and sorttype = '2') b" + 
						"                 where  t.fxyp07 = 1" + 
						"                   and t.mntp00 ='"+mntp00+"'" + 
						"                   and t.b01id = (select b01id from b01 where b0111='"+b0111+"')" + 
						"                   and  b.SORTID(+) = t.fxyp00 " + 
						"                  and (fxyp00ref='' or fxyp00ref is null or  fxyp00ref='"+fxyp00nm+"')"+
						"                 order by t.fxyp00)";
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	
	}
	
	@PageEvent("bindGW")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int bingGW(String fxyp00) throws RadowException, AppException {
		String mntp00=this.getPageElement("mntp00").getValue();
		String a0200=this.getPageElement("a0200").getValue();
		String zrrx=this.getPageElement("zrrx").getValue();
		String b0111=this.getPageElement("b0111").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			@SuppressWarnings("unchecked")
			List<String> fxyp00nm= HBUtil.getHBSession().createSQLQuery("select fxyp00 from fxyp where a0200='"+a0200+"' and  mntp00 = '"+mntp00+"'" + 
					" and b01id=(select b01id from b01 where b0111= '"+b0111+"') and fxyp06='"+zrrx+"'").list();
			if(fxyp00nm.size()>0) {
				HBUtil.executeUpdate("update fxyp set fxyp00ref='' where fxyp00ref='"+fxyp00nm.get(0)+"'");
//				@SuppressWarnings("unchecked")
//				List<String> fxyp00ref1= HBUtil.getHBSession().createSQLQuery("select fxyp00ref from fxyp where fxyp00='"+fxyp00nm.get(0)+"'").list();
//				if(fxyp00ref1.get(0)!=null && !"".equals(fxyp00ref1.get(0))) {
//					this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','所拟免的职务已绑定！请先解除绑定',null,'220')");
//					return EventRtnType.FAILD;
//				}else {
//					HBUtil.executeUpdate("update fxyp set fxyp00ref='"+fxyp00+"' where fxyp00='"+fxyp00nm.get(0)+"'");
//					HBUtil.executeUpdate("update fxyp set fxyp00ref='"+fxyp00nm.get(0)+"' where fxyp00='"+fxyp00+"'");
//				}
				HBUtil.executeUpdate("update fxyp set fxyp00ref='"+fxyp00+"' where fxyp00='"+fxyp00nm.get(0)+"'");
				HBUtil.executeUpdate("update fxyp set fxyp00ref='"+fxyp00nm.get(0)+"' where fxyp00='"+fxyp00+"'");
			}
			
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','绑定失败！',null,'220')");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','绑定成功！',null,'220')");
		this.setNextEventName("nrGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("realParent.PersonQuery()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	
	
	
	
	
	
}