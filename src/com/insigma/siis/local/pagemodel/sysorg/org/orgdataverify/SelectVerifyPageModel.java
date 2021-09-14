package com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class SelectVerifyPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		String bstype=this.getRadow_parent_data();
		if(!StringUtil.isEmpty(bstype)&&!"4".equals(bstype)){
			this.getExecuteSG().addExecuteCode("hide()");
		}
		String userid=SysUtil.getCacheCurrentUser().getId();
		HBSession sess=HBUtil.getHBSession();
		String sql="select vsc001,vsc002 from verify_scheme where vsc003='1' and (vsc007='1' or vsc005='"+userid+"')";
		List<Object[]> list=sess.createSQLQuery(sql).list();
		HashMap<String,String> map= new HashMap<String,String>();
		if(list!=null&&list.size()>0){
			for(Object[] obj:list){
				String vsc001=obj[0].toString();
				String vsc002=obj[1].toString();
				map.put(vsc001, vsc002);
			}
		}
		((Combo)this.getPageElement("vsc001")).setValueListForSelect(map);
		return 0;
	}

	
	@PageEvent("vsc001.onchange")
	public int vcc001change(){
		this.setNextEventName("ruleGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("ruleGrid.dogridquery")
	public int doruleGridQuery(int start,int limit) throws RadowException{
		String bstype=this.getRadow_parent_data();
		String filtersql="";
		if("1".equals(bstype)){
			filtersql=" and  vru004='B01'";
		}
		if("3".equals(bstype)){
			filtersql=" and  vru004<>'B01'";
		}
		String vsc001=this.getPageElement("vsc001").getValue();
		String sql="select vru001,vru002 from verify_rule where vsc001='"+vsc001+"' and vru007='1' "+filtersql+" order by  vru004";
		this.pageQuery(sql,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("btnSave.onclick")
	public int readyverify() throws RadowException, AppException{
		String bstype=this.getRadow_parent_data();
		PageElement pe = this.getPageElement("ruleGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String ruleids="";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object checked=map.get("checked");
			if(checked!=null&&checked.toString().equals("true")){
				Object vru001=map.get("vru001");
				ruleids+=vru001+",";
			}
		}
		if(StringUtil.isEmpty(ruleids)){
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				Object vru001=map.get("vru001");
				ruleids+=vru001+",";
			}
		}
		ruleids=ruleids.substring(0, ruleids.length()-1);
		
		if("4".equals(bstype)){
			this.closeCueWindow("selectVerifyWin2");
			String a0163=this.getPageElement("a0163").getValue();
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('btnSave','"+ruleids+"@"+a0163+"')");
		}else{
			this.closeCueWindow("selectVerifyWin");
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('btnSave','"+ruleids+"')");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
