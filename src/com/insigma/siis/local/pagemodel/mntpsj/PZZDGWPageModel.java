package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class PZZDGWPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String zdgwid = this.getPageElement("zdgwid").getValue();
		try {
			if(zdgwid==null||"".equals(zdgwid)) {
				
			}else {
				CommQuery cqbs=new CommQuery();
				String sql="select * from zdgw where zdgwid='"+zdgwid+"'";
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("zdgwname").setValue(map.get("zdgwname").toString());
				this.getPageElement("dwtype").setValue(map.get("dwtype").toString());
				this.getPageElement("zdgwtype").setValue(map.get("zdgwtype").toString());
				this.getPageElement("zdgwdesc").setValue(map.get("zdgwdesc")==null?"":map.get("zdgwdesc").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 保存
	 */         
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		String zdgwid = this.getPageElement("zdgwid").getValue();
		String zdgwname = this.getPageElement("zdgwname").getValue().trim();
		String dwtype = this.getPageElement("dwtype").getValue();
		String zdgwtype = this.getPageElement("zdgwtype").getValue();
		String zdgwdesc = this.getPageElement("zdgwdesc").getValue()==null?"":this.getPageElement("zdgwdesc").getValue();
		if(null==zdgwname||"".equals(zdgwname)){
			this.setMainMessage("请填写重点岗位名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(null==dwtype||"".equals(dwtype)){
			this.setMainMessage("请填写单位类型");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(null==zdgwtype||"".equals(zdgwtype)){
			this.setMainMessage("请填写成员类别");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="";
		try {
			HBSession sess = HBUtil.getHBSession();
			if(zdgwid==null||"".equals(zdgwid)) {
				String uuid=UUID.randomUUID().toString().replaceAll("-", "");
				String userid = SysManagerUtils.getUserId();
				sql="insert into zdgw (zdgwid,zdgwname,zdgwdesc,dwtype,zdgwtype,userid,sortid) values ('"+uuid+"','"+zdgwname+"','"+zdgwdesc+"','"+dwtype+"','"+zdgwtype+"','"+userid+"',(select max(sortid) from zdgw_way)+1)";
			}else {
				sql="update zdgw set zdgwname='"+zdgwname+"',zdgwdesc='"+zdgwdesc+"',dwtype='"+dwtype+"',zdgwtype='"+zdgwtype+"' where zdgwid='"+zdgwid+"'";
			}
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
