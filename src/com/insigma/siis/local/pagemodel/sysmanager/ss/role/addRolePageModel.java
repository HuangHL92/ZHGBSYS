package com.insigma.siis.local.pagemodel.sysmanager.ss.role;

import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.pagemodel.sample.SimpleWindowPageModel;

public class addRolePageModel extends PageModel {

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
	@Transaction
	//@OpLog
	public int btnSaveOnclick() {
		RoleVO role = new RoleVO();
		try {
			PMPropertyCopyUtil.copyElementsValueToObj(role, this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//System.out.println(role.getName());
		try {
			PrivilegeManager.getInstance().getIRoleControl().saveRole(role);
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setMainMessage("±£´æ³É¹¦");
		this.createPageElement("grid6", "grid", true).reload();
		this.closeCueWindow("roleWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
