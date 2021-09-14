package com.insigma.siis.local.pagemodel.sysmanager.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class AddGrantPageModel extends PageModel {

	/**
	 * 私有属性 ids 父页传过来的数据
	 */
	private static String ids = null;
	/**
	 * 私有属性：当前登录的场景
	 */
	private SceneVO scene = null;
	
	/**
	 * 
	 */
	@Override
	public int doInit() throws RadowException {
		ids = this.getRadow_parent_data();
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("rolegrid.dogridquery")
	public int usergridDoQuery(int start, int limit) throws RadowException {
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		try {
			List rlist = PrivilegeManager.getInstance().getIRoleControl().getRolesByPrincipalId(cueUser.getId(),true);
			String[] id = ids.split(",");
			if(id.length==1){
				List nlist =  PrivilegeManager.getInstance().getIRoleControl().getRolesByPrincipalId(id[0],false);
				for(int j=0;j<nlist.size();j++){
					RoleVO role2 = (RoleVO) nlist.get(j);
					for(int i=0;i<rlist.size();i++){
						RoleVO role = (RoleVO) rlist.get(i);
						if(role2.getId().equals(role.getId())){
							rlist.remove(i);
						}	
					}
				}
			}
			this.setSelfDefResData(this.getPageQueryData(rlist, start, limit));
		} catch (PrivilegeException e) {
			this.isShowMsg = true;
			this.setMainMessage(e.getMessage());
			List list = new ArrayList();
			this.setSelfDefResData(this.getPageQueryData(list, start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("save.onclick")
	@Transaction
	public int saveOnClick() throws  RadowException{
		try {
			scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
		} catch (PrivilegeException e1) {
			this.setMainMessage(e1.getMessage());
			return EventRtnType.FAILD;
		}
		PageElement pe = this.getPageElement("rolegrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("rolecheck");
				if(usercheck.equals(true)){
					String roleid=	(String) this.getPageElement("rolegrid").getValue("id", j);
					String isCan =   (String) this.getPageElement("rolegrid").getValue("isCan", j);
					boolean isCanDespacth = false;
					if(isCan.equals("1")){
						isCanDespacth=true;
					}
					String[] id = ids.split(",");
					for(int i=0;i<id.length;i++){
						try {
							PrivilegeManager.getInstance().getIRoleControl().grant(id[i], scene.getSceneid(), roleid, isCanDespacth);
						} catch (PrivilegeException e) {
							this.setMainMessage(e.getMessage());
							return EventRtnType.FAILD;
						}
					}
				}
			}
		}
		this.setMainMessage("授权成功");
		this.createPageElement("usergrid", "grid", true).reload();
		this.closeCueWindowByYes("win1");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
