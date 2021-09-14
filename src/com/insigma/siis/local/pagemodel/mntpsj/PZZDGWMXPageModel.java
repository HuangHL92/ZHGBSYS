package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class PZZDGWMXPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String type1=this.getPageElement("type1").getValue();
		String id=this.getPageElement("id").getValue();
		String query=this.getPageElement("query").getValue();
		String sql="";
		if("zg".equals(type1)) {
			sql="select mxname condname,mxdsec conddesc,mxcs1 cs,mxcs2 cs2,mxsql_p,'' grade,cstype,mxcs_p,csdesc2,tjtype from ZDGW_ZGMX"+query+" where zgmxid='"+id+"' ";
		}else if("qz".equals(type1)) {
			sql="select mxname condname,mxdsec conddesc,mxcs1 cs,mxcs2 cs2,mxsql_p,grade,cstype,mxcs_p,csdesc2,tjtype from ZDGW_QZMX"+query+" where qzmxid='"+id+"' ";
		}
		CommQuery cqbs=new CommQuery();
		try {
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			HashMap<String, Object> map=list.get(0);
			this.getPageElement("condname").setValue(map.get("condname")==null?"":map.get("condname").toString());
			this.getPageElement("conddesc").setValue(map.get("conddesc")==null?"":map.get("conddesc").toString());
			if(map.get("cs")!=null&&!"".equals(map.get("cs"))) {
				String cs=map.get("cs").toString();
				this.getPageElement("cs").setValue(cs);
				String cs_combo="";
				if("=".equals(cs)) {
					cs_combo="等于";
				}else if(">".equals(cs)) {
					cs_combo="大于";
				}else if(">=".equals(cs)) {
					cs_combo="大于等于";
				}else if("<".equals(cs)) {
					cs_combo="小于";
				}else if("<=".equals(cs)) {
					cs_combo="小于等于";
				}else if("in".equals(cs)) {
					cs_combo="包含";
				}else if("not in".equals(cs)) {
					cs_combo="不包含";
				}
				this.getPageElement("cs_combo").setValue(cs_combo);
			}
			this.getPageElement("mxcs_p").setValue(map.get("mxcs_p")==null?"":map.get("mxcs_p").toString());
			this.getPageElement("mxsql_p").setValue(map.get("mxsql_p")==null?"":map.get("mxsql_p").toString());
			this.getPageElement("grade").setValue(map.get("grade")==null?"":map.get("grade").toString());
			HashMap<String, Object> map_fh1=new LinkedHashMap<String, Object>();
			if(map.get("mxcs_p")!=null&&("in".equals(map.get("mxcs_p"))||"not in".equals(map.get("mxcs_p")))) {
				String code_type=map.get("cstype")==null?"":map.get("cstype").toString();
				if("SXLY2".equals(code_type)) {
					this.getPageElement("code2").setValue(map.get("csdesc2")==null?"":map.get("csdesc2").toString());
					this.getPageElement("sxly").setValue(map.get("cs2")==null?"":map.get("cs2").toString());
					this.getExecuteSG().addExecuteCode("document.getElementById('h2').style.display='none';");
				}else {
					this.getExecuteSG().addExecuteCode("var code = Ext.getCmp('code_combotree');code.rebuildTree('"+code_type+"','code_name');document.getElementById('h4').style.display='none';");
					this.getPageElement("code").setValue(map.get("cs2")==null?"":map.get("cs2").toString());
					this.getPageElement("code_combotree").setValue(map.get("csdesc2")==null?"":map.get("csdesc2").toString());
				}
				this.getPageElement("code_type").setValue(code_type);
			}else {
				this.getPageElement("cs2").setValue(map.get("cs2")==null?"":map.get("cs2").toString());
			}
			String tjtype=map.get("tjtype")==null?"":map.get("tjtype").toString();
			this.getPageElement("tjtype").setValue(tjtype);
			if("3".equals(tjtype)) {
				this.createPageElement("cs", ElementType.SELECT, false).setDisabled(true);
				this.createPageElement("cs2", ElementType.TEXT, false).setDisabled(true);
//				this.createPageElement("code", ElementType.SELECT, false).setDisabled(true);
				this.createPageElement("code2", ElementType.TEXTAREA, false).setDisabled(true);
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 保存
	 */         
	@PageEvent("save")
	@Transaction
	public int save() throws RadowException {
		String tjtype=this.getPageElement("tjtype").getValue();
		String query=this.getPageElement("query").getValue();
		String type1=this.getPageElement("type1").getValue();
		String cs=this.getPageElement("cs").getValue();
		String mxcs_p=this.getPageElement("mxcs_p").getValue();
		String id=this.getPageElement("id").getValue();
		String cs2="";
		String csdesc="";
		String sql_p="";
		String nextway="";
		if("in".equals(mxcs_p)||"not in".equals(mxcs_p)) {
			String code_type=this.getPageElement("code_type").getValue();
			if("SXLY2".equals(code_type)) {
				cs2=this.getPageElement("sxly").getValue();
				csdesc=this.getPageElement("code2").getValue();
			}else {
				cs2=this.getPageElement("code").getValue();
				csdesc=this.getPageElement("code_combotree").getValue();
			}
			sql_p="replace(mxsql_p,'"+mxcs_p+" ('''')','"+cs+" (''"+cs2.replaceAll(",","'',''")+"'')'),csdesc2='"+csdesc+"'";
		}else {
			cs2=this.getPageElement("cs2").getValue();
			sql_p="mxsql_p||'"+cs+cs2+"'";
		}
		String condname=this.getPageElement("condname").getValue();
		String conddesc=this.getPageElement("conddesc").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql="";
			if("zg".equals(type1)) {
				sql="update ZDGW_ZGMX"+query+" set mxname='"+condname+"',mxdsec='"+conddesc+"',mxcs1='"+cs+"',mxcs2='"+cs2+"',mxsql="+sql_p+" where zgmxid='"+id+"'";
				nextway="parent.updateZDGWZG();";
			}else {
				String grade=this.getPageElement("grade").getValue();
				if("3".equals(tjtype)) {
					sql="update ZDGW_QZMX"+query+" set mxname='"+condname+"',mxdsec='"+conddesc+"',grade='"+grade+"' where qzmxid='"+id+"'";
				}else {
					sql="update ZDGW_QZMX"+query+" set mxname='"+condname+"',mxdsec='"+conddesc+"',mxcs1='"+cs+"',mxcs2='"+cs2+"',mxsql="+sql_p+",grade='"+grade+"' where qzmxid='"+id+"'";
				}
				nextway="parent.updateZDGWQZ();";
			}
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.getExecuteSG().addExecuteCode(nextway+"window.close();");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
			
	
}
