package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fr.report.core.A.r;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class ModuleComWindowPageModel extends PageModel {

	/**
	 * 私有属性 ids 父页传过来的数据
	 */
	private static String ids = null;
	/**
	 * 私有属性：当前登录的场景
	 */
	private SceneVO scene = null;
	/**
	 * 用于判断是那种查询
	 */
	private static int index = 0;
	/**
	 * 
	 */
	@Override
	public int doInit() throws RadowException {
		ids = this.getRadow_parent_data();
		this.getExecuteSG().addExecuteCode("checkType3()");
		this.setNextEventName("find.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws PrivilegeException 
	 */
	
	@PageEvent("rolegrid.dogridquery")
	public int usergridDoQuery(int start, int limit) throws RadowException, PrivilegeException {
/*		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
			try {
				List rlist = PrivilegeManager.getInstance().getIRoleControl().getRolesByPrincipalId(cueUser.getId(),true);
				String[] id = ids.split(",");
				if(id.length==1){

					UserVO u = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(id.toString());
					List nlist =  PrivilegeManager.getInstance().getIRoleControl().getRolesByPrincipalId(id[0],false);
					for(int j=0;j<nlist.size();j++){
						RoleVO role2 = (RoleVO) nlist.get(j);
						for(int i=0;i<rlist.size();i++){
							RoleVO role = (RoleVO) rlist.get(i);
							if(role2.getId().equals(role.getId()) || role2.getHostsys().equals(u.getUsertype())){
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
				index=0;
				return EventRtnType.SPE_SUCCESS;
			}*/
		String id =  this.getRadow_parent_data();
		UserVO u = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(id.toString());
		String type = u.getUsertype();
		//String sql = "select s.* from smt_role s where s.roleid not in (select sr.roleid from Smt_Act sa, Smt_Role sr where sa.roleid = sr.roleid and objectid = '"+id+"') and s.hostsys = '"+type+"'";
		String sql = "select * from smt_role";
		this.pageQuery(sql,"SQL", start, limit);
		
		return EventRtnType.SPE_SUCCESS;
	}
	@SuppressWarnings("unchecked")
	@PageEvent("rolegrid1.dogridquery")
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
	
	@PageEvent("save.onclick")
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
			int k=0;
			for(int j=0;j<list.size();j++){
				
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("rolecheck");
				if(usercheck.equals(true)){
					k++;
					String roleid=	(String) this.getPageElement("rolegrid").getValue("roleid", j);
					String isCan =   (String) this.getPageElement("rolegrid").getValue("isCan", j);
					boolean isCanDespacth = false;
					if(isCan.equals("1")){
						isCanDespacth=true;
					}
					String[] id = ids.split(",");
					for(int i=0;i<id.length;i++){
						/*try {
							PrivilegeManager.getInstance().getIRoleControl().grant(id[i], scene.getSceneid(), roleid, true);
						} catch (PrivilegeException e) {
							this.setMainMessage(e.getMessage());
							return EventRtnType.FAILD;
						}*/
						try {
							scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
							SmtAct smtact = new SmtAct();
							smtact.setObjectid(id[i]);
							smtact.setRoleid(roleid);
							smtact.setUserid(id[i]);
							smtact.setSceneid(scene.getSceneid());
							smtact.setDispatchauth("0");
							smtact.setObjecttype("0");
							HBSession session = HBUtil.getHBSession();
							session.save(smtact);
							session.flush();
						} catch (PrivilegeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			if(k==0) {
				this.setMainMessage("您尚未选择角色。");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//this.createPageElement("usergrid", "grid", true).reload();
		String userid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("611", "SMT_ACT",user.getId(),user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("授权成功");
		this.closeCueWindowByYes("win_pup");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("find.onclick")
	public int findOnClick() throws  RadowException{
		this.getExecuteSG().addExecuteCode("checkType()");
		this.setNextEventName("rolegrid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("find2.onclick")
	public int find2OnClick() throws  RadowException{
		this.getExecuteSG().addExecuteCode("checkType()");
		this.setNextEventName("rolegrid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("find3.onclick")
	public int find3OnClick() throws  RadowException{
		this.getExecuteSG().addExecuteCode("checkType2()");
		
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("find1.onclick")
	public int find1OnClick() throws  RadowException{
		this.getExecuteSG().addExecuteCode("checkType2()");
		
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("remove1.onclick")
	public int remove1OnClick() throws RadowException {
		try {
			scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl()
											  .queryByName("sce", true).get(0);
		} catch (PrivilegeException e1) {
			this.setMainMessage(e1.getMessage());
			return EventRtnType.FAILD;
		}
		PageElement pe = this.getPageElement("rolegrid1");
		int count=0;
		if (pe != null) {
			List<HashMap<String, Object>> list = pe.getValueList();
			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("rolecheck");
				if (usercheck.equals(true)) {
					count++;
					String roleid = (String) this.getPageElement("rolegrid1").getValue("roleid", j);
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
		if(count==0){
			throw new RadowException("请选择需要取消授权的角色！");
		}
		String userid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("611", "SMT_ACT",user.getId(),user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("解除授权成功");
		this.closeCueWindowByYes("win_pup");
		//this.createPageElement("usergrid", "grid", true).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
}
