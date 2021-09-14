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
    
public class PZZDGWWAYPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String wayid = this.getPageElement("wayid").getValue();
		try {
			if(wayid==null||"".equals(wayid)) {
				
			}else {
				CommQuery cqbs=new CommQuery();
				String sql="select * from zdgw_way where wayid='"+wayid+"'";
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("wayname").setValue(map.get("wayname").toString());
				//this.getPageElement("dwtype").setValue(map.get("dwtype").toString());
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
		String wayid = this.getPageElement("wayid").getValue();
		String zdgwid = this.getPageElement("zdgwid").getValue();
		String wayname = this.getPageElement("wayname").getValue().trim();
		//String dwtype = this.getPageElement("dwtype").getValue();
		if(null==wayname||"".equals(wayname)){
			this.setMainMessage("请填写方案名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(null==dwtype||"".equals(dwtype)){
//			this.setMainMessage("请填写单位类型");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		String sql="";
		try {
			HBSession sess = HBUtil.getHBSession();
			if(wayid==null||"".equals(wayid)) {
				String uuid=UUID.randomUUID().toString().replaceAll("-", "");
				String userid = SysManagerUtils.getUserId();
				sql="insert into zdgw_way (wayid,wayname,zdgwid,userid,sortid) values ('"+uuid+"','"+wayname+"','"+zdgwid+"','"+userid+"',(select max(sortid) from zdgw_way where zdgwid='"+zdgwid+"')+1)";
			}else {
				sql="update zdgw_way set wayname='"+wayname+"' where wayid='"+wayid+"'";
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
