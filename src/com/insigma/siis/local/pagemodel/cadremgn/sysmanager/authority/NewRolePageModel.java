package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.sample.SimpleWindowPageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NewRolePageModel extends PageModel {

	public static Logger log = Logger.getLogger(SimpleWindowPageModel.class);

	@Override
	public int doInit() throws RadowException { 
		this.isShowMsg = false;
		String parentJsonValue = this.getRadow_parent_data();
		if (parentJsonValue != null && parentJsonValue.trim().length() > 0) {
			JSONArray jObjArray = JSONArray.fromObject(parentJsonValue);
			for (int i = 0; i < jObjArray.size(); i++) {
				JSONObject jObj = jObjArray.getJSONObject(i);
				Set<String> keys = jObj.keySet();
				for (String key : keys) {
					if (getPageElement(key) != null) {
						getPageElement(key).setValue(jObj.getString(key));
					}
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnClose.onclick")
	@NoRequiredValidate
	public int btn1Onclick() {
		this.closeCueWindow("roleWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnSave.onclick")
	//@OpLog
	public int btnSaveOnclick() throws RadowException {
		SmtRole role = new SmtRole();
		String name = this.getPageElement("name").getValue().trim();
		HBSession session = HBUtil.getHBSession();
		List<SmtRole> list_Smtrole= session.createSQLQuery("select * from smt_role where rolename = '"+name+"'").addEntity(SmtRole.class).list();
		if(list_Smtrole.size()!=0){
			this.setMainMessage("角色名称已存在！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("".equals(name) || name==null) {
			this.setMainMessage("角色名称不能为空或空字符串。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			PMPropertyCopyUtil.copyElementsValueToObj(role, this);
			role.setOwner(SysUtil.getCacheCurrentUser().getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		Long sortID = getSortid();
		role.setSortid(sortID);
		role.setStatus("1");
		String role1 =  (String) sess.save(role);
		ts.commit();
		try {
			new LogUtil().createLogNew(role.getId(),"角色新增","SMT_ROLE",role.getId(),role.getName(), new ArrayList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//this.createPageElement("grid6", "grid", true).reload();
		//this.closeCueWindowByYes("roleWindow");
		this.getExecuteSG().addExecuteCode("window.realParent.selectGrid();window.close()");
		this.setMainMessage("创建角色成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  获取用户的排序编号
	 * @param parentid
	 * @return
	 */
	public Long getSortid(){
		
		String sql = "select max(case when t.sortid is null then 0 else t.sortid end)+1 sortid from smt_role t";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Long sortid = 1L;
		if (list.get(0) == null) {
			sortid = 1L;
		} else {
			sortid = Long.parseLong(list.get(0).toString());
		}
		
		return sortid;
	}

}
