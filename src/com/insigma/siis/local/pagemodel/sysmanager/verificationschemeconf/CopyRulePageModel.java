package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifySqlList;


public class CopyRulePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		String vru001Para=this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String vru001Str = vru001Para.substring(0, vru001Para.indexOf(","));
		String sql="select vsc001,vsc002 from verify_scheme where vsc001 not in (select vsc001 from verify_rule where vru001 ='"+vru001Str+"' ) and  vsc005='"+userid+"' and vsc007 ='0'";
		List<Object[]>  list=sess.createSQLQuery(sql).list();
		HashMap<String,String> map=new HashMap<String,String>();
		if(list!=null&&list.size()>0){
			for(Object[] obj:list){
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		
		( (Combo) this.getPageElement("vsc001")).setValueListForSelect(map);
		return 0;
	}
	
	@PageEvent("close.onclick")
	public int close(){
		this.closeCueWindow("copyWin");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
    
	@PageEvent("copy.onclick")
	@Transaction
	public int copy() throws RadowException{
		String vsc001=this.getPageElement("vsc001").getValue();
		String vru001Para=this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String vru001Str = vru001Para.substring(0, vru001Para.lastIndexOf(","));
		//StringBuffer vru001Where = new StringBuffer();
		String[] vru001s = vru001Str.split(",");
		/*for(String vru001 : vru001s){
			vru001Where.append("'").append(vru001).append("',");
			vru001List.add(vru001);
		}
		vru001Str = vru001Where.toString().substring(0, vru001Where.lastIndexOf(","));
		 vru001s=vru001Str.split(",");*/
		try {
			for(String vru001:vru001s){
				//保存每一条规则到目标校验方案
				VerifyRule vr=(VerifyRule) sess.get(VerifyRule.class, vru001);
				VerifyRule vr1 = new VerifyRule ();
					BeanUtil.copy(vr, vr1);		
				vr1.setVsc001(vsc001);
				sess.save(vr1);
				//查出每条规则对应的verifysqllist对应到规则下
				String  sql="select vsl001 from verify_sql_list where vru001='"+vru001+"'";
				List<Object> list=sess.createSQLQuery(sql).addEntity(VerifySqlList.class).list();
				if(list!=null&&list.size()>0){
					for(Object vsl001:list){
						VerifySqlList vsl=(VerifySqlList) sess.get(VerifySqlList.class, vsl001.toString());
						VerifySqlList vls1=new VerifySqlList();
						BeanUtil.copy(vsl, vls1);
						vls1.setVru001(vr1.getVru001());
						sess.save(vls1);
					}
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("msg()");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
