package com.insigma.siis.local.pagemodel.sysmanager.ss.role;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class RemoveGrantPageModel extends PageModel {

	private static String ids = null;

	private SceneVO scene = null;

	@Override
	public int doInit() throws RadowException {
		ids = this.getRadow_parent_data();
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("rolegrid.dogridquery")
	public int doGroupGridQuery(int start, int limit) throws RadowException {
//		try {
			//PrivilegeManager.getInstance().getHbSession().createSQLQuery("select sa.dispatchauth,sa.id,sa.name,sa.description from Smt_Act sa,Smt_Role where objectid='"+ids+"'");
			String sql = "select sa.dispatchauth,sr.roleid,sr.rolename,sr.status,sr.roledesc from Smt_Act sa,Smt_Role sr where sa.roleid=sr.roleid and objectid='"+ids+"'";
			this.pageQuery(sql,"SQL", start, limit);
//			List rlist = PrivilegeManager.getInstance().getIRoleControl().getRolesByPrincipalId(ids, false);
//			this.setSelfDefResData(this.getPageQueryData(rlist, start, limit));
//		} catch (PrivilegeException e) {
//			this.isShowMsg = true;
//			this.setMainMessage(e.getMessage());
//			return EventRtnType.FAILD;
//		}
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("remove.onclick")
	@Transaction
	public int saveOnClick() throws RadowException {
		try {
			scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl()
											  .queryByName("sce", true).get(0);
		} catch (PrivilegeException e1) {
			this.setMainMessage(e1.getMessage());
			return EventRtnType.FAILD;
		}
		PageElement pe = this.getPageElement("rolegrid");
		if (pe != null) {
			List<HashMap<String, Object>> list = pe.getValueList();
			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("rolecheck");
				if (usercheck.equals(true)) {
					String roleid = (String) this.getPageElement("rolegrid").getValue("roleid", j);
					String[] id = ids.split(",");
					for (int i = 0; i < id.length; i++) {
						try {
							PrivilegeManager.getInstance().getIRoleControl()
									        .grantRemove(id[i], scene.getSceneid(), roleid);
						} catch (PrivilegeException e) {
							this.setMainMessage(e.getMessage());
							return EventRtnType.FAILD;
						}
					}
				}
			}
		}
		this.setMainMessage("解除授权成功");
		this.closeCueWindowByYes("win2");
		this.createPageElement("usergrid", "grid", true).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
}
