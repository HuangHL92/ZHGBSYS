package com.insigma.odin.framework.privilege;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.entity.SmtAbsResource;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtPrincipal;
import com.insigma.odin.framework.privilege.entity.SmtResource;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.privilege.entity.SmtScene;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUsergroupref;
import com.insigma.odin.framework.privilege.helper.CommomPermissionHelper;
import com.insigma.odin.framework.privilege.helper.ResourceHelper;
import com.insigma.odin.framework.privilege.helper.RoleHelper;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.AbsResourceVO;
import com.insigma.odin.framework.privilege.vo.PrincipalVO;
import com.insigma.odin.framework.privilege.vo.ResourceVO;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.util.HQuery;

/**
 * ��ɫ����ʵ����
 * 
 * @author yangl
 * @created 18-����-2009 11:48:11
 */
public class RoleRealControl extends AbstractPrivilegeBase implements
		IRoleControl {

	public RoleRealControl() {

	}

	/**
	 * ����ɫ��Ȩ��������ɫ����Դ�Ĺ��� (�����ǵ����ĸ���ɫ����Ȩ�ޣ�����Ҫ�Ƚ���ɫ�Ѿ��е�Ȩ��ɾ��)
	 * @param roleId
	 *            ��Ȩ��ɫ��ID
	 * @param resourceIds
	 *            ��ԴID�� ���functionid�ö��Ÿ���
	 * @return �ɹ�����true
	 * @throws PrivilegeException
	 */
	public boolean addResousesToRole(String roleId, String resourceIds)
			throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		/*
		 * jinwei - 2011.7.22 ȡ��ֻ�г��иý�ɫ���ܸ���������Դ�Ĺ�������
		if(!role.getOwner().equals(getPrivilegeManager().getCueLoginUser().getId())) {
			throw new PrivilegeException("�����޸������û����еĽ�ɫ��");
		}
		*/
		if (RoleHelper.isUsefulRole(role) == RoleHelper.ROLE_NOT_EXIST)
			throw new PrivilegeException("�ý�ɫ�����ڣ�");
		if (RoleHelper.isUsefulRole(role) == RoleHelper.ROLE_UN_USEFUL)
			throw new PrivilegeException("��Ч�Ľ�ɫ");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null))// Ȩ�޼��
			throw new PrivilegeException("û��Ȩ��");

		List<String> canDispatchResourceIds = new ArrayList<String>();// �ɷ��ڵ���Դ��
		List<Object> roles = getRolesByPrincipalId(getPrivilegeManager()// �ɷ���Ȩ�Ľ�ɫ��
				.getCueLoginUser().getId(), true);
		for (int k = 0; k < roles.size(); k++) {
			RoleVO trole = (RoleVO) roles.get(k);
			List<ResourceVO> res = this.getResourcesWithNoCheck(trole.getId());
			for (int j = 0; j < res.size(); j++) {
				if (!canDispatchResourceIds.contains(res.get(j).getId()))
					canDispatchResourceIds.add(res.get(j).getId());
			}
		}

		String[] functionIds = resourceIds.split(",");
		int temp = 0;
		for (int i = 0; i < functionIds.length; i++) {
//			if (get(SmtResource.class, functionIds[i]) == null)
//				throw new PrivilegeException("��Դ������");
			
			String hql = "from SmtAcl where roleid=:roleid and resourceid=:resourceid";
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("roleid", roleId);
			params.put("resourceid", functionIds[i]);
			if (query(hql, params).size() > 0) {
				continue;
			}
			if (!getPrivilegeManager().getIPermission().isSuperManager(
					getPrivilegeManager().getCueLoginUser()) && !getPrivilegeManager().getIPermission().isSysPermission(getPrivilegeManager().getCueLoginUser(), "ROLE_ALL_EDIT")
					&& !canDispatchResourceIds.contains(functionIds[i]))
				throw new PrivilegeException("û�з��ڸ���Դ��Ȩ��");

			SmtAcl acl = new SmtAcl();
			acl.setRoleid(roleId);
			acl.setResourceid(functionIds[i]);
			save(acl);
			temp++;
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("����ɫ"+role.getName()+"����"+temp+"����Դ", "����IRoleControl�ӿڵ�addResousesToRole����", null);
		return true;
	}

	/**
	 * ɾ����ɫ�����ݴ���Ľ�ɫID���н�ɫ��ɾ��
	 * @param roleid
	 *            ��ɾ���Ľ�ɫid
	 * @return �ɹ�ɾ������true
	 * @throws PrivilegeException
	 */
	public boolean deleteRole(String roleid) throws PrivilegeException {
		RoleVO role = new RoleVO();
		role.setId(roleid);
		deleteRole(role);
		return true;
	}

	/**
	 * ɾ����ɫ�����ݴ���Ľ�ɫ������ɾ��
	 * @param role
	 *            ��ɾ���Ľ�ɫ����
	 * @return �ɹ�ɾ������true
	 * @throws PrivilegeException
	 */
	public boolean deleteRole(RoleVO role) throws PrivilegeException {

		if (role == null)
			throw new PrivilegeException("��Ч�Ľ�ɫ����");
		SmtRole rrole = (SmtRole) get(SmtRole.class, role.getId());
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_DELETE,
				CommomPermissionHelper.OWNER_CHECK, rrole.getOwner(), null))// Ȩ�޼��
			throw new PrivilegeException("û��Ȩ��");
		String hql = "from SmtAct where roleid=:roleId";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleId", rrole.getId());
		List<Object> lists = super.query(hql, params);
		if (lists.size() > 0) {
			throw new PrivilegeException("�ý�ɫ����"+lists.size()+"��������Ϣ(�����û����û���)��������ɾ��");
		}
		getPrivilegeManager().getHbSession().clear();
		delete(rrole);

		String hqlacl = "from SmtAcl where roleid='" + role.getId() + "'";
		delete(hqlacl);

		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("ɾ����ɫ"+role.getName(), "����IRoleControl�ӿڵ�deleteRole����", null);
		return true;
	}

	/**
	 * ���ݶ���ID����ѯ��ɷ���Ȩ�Ŀ��ý�ɫ��ȫ�����ý�ɫ��
	 * ��isCanDispatchΪtrueʱ��ѯ���û��ɷ���Ȩ�Ŀ��ý�ɫ������Ϊ��ѯȫ�����ý�ɫ
	 * @param principalId  ����ID
	 * @param isCanDispatch  �Ƿ�ɷ���Ȩ��ǣ�trueΪ��ѯ�ɷ���Ȩ�ģ�falseΪ��ѯ���е�
	 * @return List ���ز��ҵ��ý�ɫ����
	 * @throws PrivilegeException
	 */
	public List<Object> getRolesByPrincipalId(String principalId,
			boolean isCanDispatch) throws PrivilegeException {
		SmtUser user = (SmtUser) super.get(SmtUser.class, principalId);
		SmtGroup group = (SmtGroup) get(SmtGroup.class, principalId);
		UserVO userVO = new UserVO();
		if(user!=null){
			BeanUtil.propertyCopy(user, userVO);
		}
		if (!principalId.equals(this.getPrivilegeManager().getCueLoginUser().getId())) {
			if(isCanDispatch){
				if (user != null
						&& !hasPermission(null, ResourcesPermissionConst.USER_ALL,
								CommomPermissionHelper.MANAGER_CHECK, user
										.getOwnerId(), principalId))// Ȩ�޼��
					throw new PrivilegeException("û��Ȩ��");
			}
			if (group != null
					&& !hasPermission(null, ResourcesPermissionConst.USER_ALL,
							CommomPermissionHelper.MANAGER_CHECK, group
									.getOwnerId(), principalId))// Ȩ�޼��
				throw new PrivilegeException("û��Ȩ��");

			if (user == null && group == null)
				throw new PrivilegeException("��Ȩ���󲻴���");
		}

		//if (getPrivilegeManager().getIPermission().isSuperManager(// �Ƿ��ǳ�������Ա
			//	userVO)) {
		if(principalId.equals(getPrivilegeManager().getCueLoginUser().getId()) && hasPermission(null, ResourcesPermissionConst.ROLE_ALL, CommomPermissionHelper.NORMAL_CHECK, null, null)){
			String hqlForall = "from SmtRole where status='1'";
			return PrivilegeUtil
					.getVOList(query(hqlForall, null), RoleVO.class);
		}
		List<Object> roles = new ArrayList<Object>();
		HashMap<String, String> params = new HashMap<String, String>();
		String hqlGroupRoles = "from SmtUsergroupref where userid=:userid";
		params.put("userid", principalId);
		List<Object> groups = query(hqlGroupRoles, params);
		//System.out.print("from SmtUsergroupref where userid="+principalId+"��ȡ���û�����Ϊ"+groups.size());
		for (int i = 0; i < groups.size(); i++) {
			
			SmtUsergroupref sug = (SmtUsergroupref) groups.get(i);
			roles.addAll(getRoleByPrincipalId(sug.getGroupid(), isCanDispatch));// ����õĽ�ɫ
		}
		//System.out.print("��ȡ����õĽ�ɫ�ɹ���principalId="+principalId);
		roles.addAll(getRoleByPrincipalId(principalId, isCanDispatch));// �Լ����õĽ�ɫ
		RoleHelper.removeDuplicate(roles);// ȥ���ظ��ļ�¼
		return PrivilegeUtil.getVOList(roles, RoleVO.class);
	}

	/*
	 * ˽�з���,�������ڲ����� ������Ȩ����ID��ѯ��ɫ
	 * @param principalId  ��Ȩ����ID
	 * @param isCanDispatch  �Ƿ�ɷ���Ȩ
	 * @return List ���ز��ҵ��ý�ɫ����
	 */
	private List<Object> getRoleByPrincipalId(String principalId,
			boolean isCanDispatch) {
		HashMap<String, String> params = new HashMap<String, String>();
		String hql = "select sr from SmtAct sa,SmtRole sr where sr.status='1' and sa.objectid=:userId and sa.roleid=sr.id ";
		if (isCanDispatch) {
			hql += " and dispatchauth='1'";
		}
		hql += " or sr.owner=:owner";// ���еĽ�ɫ
		params.put("userId", principalId);
		params.put("owner", principalId);
		return query(hql, params);
	}

	/**
	 * ������Ȩ����ID������ID�ͳ�����ԴID����Ȩ������Ȩ����������Ȩ�����û����û��飩����������ɫ��Ȩ�޹�ϵ��
	 * @param principalId ��Ȩ����id
	 * @param sceneId ����id
	 * @param absResourceId ������Դid
	 * @param isCanDispatch ָ����Ȩ�����Ƿ���Է���Ȩ�ý�ɫ��trueΪ�ɷ���Ȩ
	 * @return �ɹ���Ȩ����true
	 * @throws PrivilegeException
	 */
	public boolean grant(String principalId,String sceneId,String absResourceId,boolean isCanDispatch) throws PrivilegeException {
		SmtPrincipal principal = (SmtPrincipal)((SmtUser) get(SmtUser.class,principalId)==null?(SmtGroup)get(SmtGroup.class,principalId):(SmtUser) get(SmtUser.class,principalId));
		SmtScene  scene = (SmtScene) get(SmtScene.class,sceneId);
		SmtAbsResource absResource = (SmtAbsResource)((SmtResource) get(SmtResource.class,absResourceId)==null?(SmtRole) get(SmtRole.class,absResourceId):(SmtResource) get(SmtResource.class,absResourceId));
		PrincipalVO principalVo = new PrincipalVO();
		SceneVO sceneVo = new SceneVO();
		AbsResourceVO absResourceVo = new AbsResourceVO();
		BeanUtil.propertyCopy(principal, principalVo);
		BeanUtil.propertyCopy(scene, sceneVo);
		BeanUtil.propertyCopy(absResource, absResourceVo);
		grant(principalVo,sceneVo,absResourceVo,isCanDispatch);
		return true;
	}
	
	/**
	 * ������Ȩ���󡢳�������ͳ�����Դ�������Ȩ������Ȩ����������Ȩ�����û����û��飩����������ɫ��Ȩ�޹�ϵ��
	 * @param principal  ��Ȩ����
	 * @param scene  ��������
	 * @param absResource  ������Դ����
	 * @param isCanDispatch  ָ����Ȩ�����Ƿ���Է���Ȩ�ý�ɫ��trueΪ�ɷ���Ȩ
	 * @return �ɹ���Ȩ����true
	 * @throws PrivilegeException
	 */
	public boolean grant(PrincipalVO principal, SceneVO scene,
			AbsResourceVO absResource, boolean isCanDispatch)
			throws PrivilegeException {
		boolean canDispatchAbs = true;// �Ƿ�ɷ���Ȩ�ý�ɫ�ı�־
		/*ע�� ע�� ���ﱾ����false����systemͳһ������ɫ���Ϊtrue������������*/
		String pid = principal.getId();
		String rid = absResource.getId();
		String sid = scene.getSceneid();
		if (!hasPermission(null, ResourcesPermissionConst.GRANT_CREATE,
				CommomPermissionHelper.MANAGER_CHECK, principal.getOwnerId(),
				pid))// Ȩ�޼��
			throw new PrivilegeException("û��Ȩ��");
		String usertype = grantParamsValidata(pid, sid, rid);// ������Ч�Ե���֤

		String hql = "from SmtAct where roleid=:roleid and sceneid=:sceneid and objectid=:objectid";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", absResource.getId());
		params.put("sceneid", scene.getSceneid());
		params.put("objectid", principal.getId());
		List<Object> lists = super.query(hql, params);
		if (lists.size() > 0)// ��Ȩ�Ѵ��ڣ�ֱ�ӷ���true
			return true;
		if(principal.getId().equals(getPrivilegeManager().getCueLoginUser().getId())) {
			throw new PrivilegeException("�޷����Լ���Ȩ!");
		}
		
		if (getPrivilegeManager().getIPermission().isSuperManager(// �Ƿ��ǳ�������Ա
				getPrivilegeManager().getCueLoginUser())) {
			canDispatchAbs = true;
		}

		if (!canDispatchAbs) {// �Ƿ�ɷ��ڸý�ɫ
			List<Object> roles = getRolesByPrincipalId(getPrivilegeManager()// �ɷ���Ȩ�Ľ�ɫ��
					.getCueLoginUser().getId(), true);
			for (int i = 0; i < roles.size(); i++) {
				if (absResource.getId().equals(((RoleVO) roles.get(i)).getId())) {
					canDispatchAbs = true;
				}
			}
		}
		if (!canDispatchAbs)
			throw new PrivilegeException("��Ȩ�޷��ڸý�ɫ");
		SmtAct act = new SmtAct();
		act.setObjectid(pid);
		act.setUserid(pid);
		act.setRoleid(rid);
		act.setSceneid(sid);
		act.setObjecttype(usertype);
		if (isCanDispatch) {
			act.setDispatchauth(RoleHelper.USEFUL);
		} else {
			act.setDispatchauth(RoleHelper.UN_USEFUL);
		}
		save(act);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("������Ȩ���󡢳�������ͳ�����Դ�������Ȩ����"+principal.getName()+"��Ȩ", "����IRoleControl�ӿڵ�grant����", null);
		return true;
	}

	/*
	 * ��֤����������������Ч��
	 * @param principalId  ��Ȩ����ID
	 * @param sceneId  ����ID
	 * @param absResourceId  ��ԴID
	 * @return ���ش������Ȩ���������
	 * @throws PrivilegeException
	 */
	private String grantParamsValidata(String principalId, String sceneId,
			String absResourceId) throws PrivilegeException {
		String usertype = null;
		SmtUser user = (SmtUser) get(SmtUser.class, principalId);
		SmtGroup group = (SmtGroup) get(SmtGroup.class, principalId);
		if (user == null && group == null) {
			throw new PrivilegeException("��Ȩ���󲻴���");
		}
		if (user != null) {
			usertype = RoleHelper.UN_USEFUL;
			if (user.getStatus().equals(RoleHelper.UN_USEFUL))
				throw new PrivilegeException("��Ч����Ȩ����");
		} else {
			usertype = RoleHelper.USEFUL;
			if (group.getStatus().equals(RoleHelper.UN_USEFUL))
				throw new PrivilegeException("��Ч����Ȩ����");
		}
		SmtScene checkscene = (SmtScene) get(SmtScene.class, sceneId);
		if (checkscene == null) {
			throw new PrivilegeException("����������");
		}
		if (checkscene.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("��Ч�ĳ���");
		SmtRole role = (SmtRole) get(SmtRole.class, absResourceId);
		SmtResource resource = (SmtResource) get(SmtResource.class,
				absResourceId);
		if (role == null && resource == null) {
			throw new PrivilegeException("��Դ������");
		}
		if (role != null
				&& RoleHelper.isUsefulRole(role) == RoleHelper.ROLE_UN_USEFUL)
			throw new PrivilegeException("��Ч�Ľ�ɫ");
		if (resource != null
				&& ResourceHelper.isUsefulResource(resource) == ResourceHelper.RESOURCE_UN_USEFUL)
			throw new PrivilegeException("��Ч����Դ");
		return usertype;
	}

	/**
	 * ������ɫ�Ĳ�����Ȩ����������Ӧ��Ȩ��
	 * @param roleId  ��ɫID
	 * @param resourceIds  ��ԴID ���resourceid�ö��Ÿ���
	 * @return �ɹ�������Ȩ����true
	 * @throws PrivilegeException
	 */
	public boolean removeResourcesFromRole(String roleId, String resourceIds)
			throws PrivilegeException {
		SmtRole role = (SmtRole) super.get(SmtRole.class, roleId);
		if (role == null || role.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("��ɫ������");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null)) // �ж��Ƿ���Ȩ��
			throw new PrivilegeException("û��Ȩ��");
		recycleResource(roleId, resourceIds);// ��Դ����
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("������ɫ"+role.getName()+"�Ĳ�����Ȩ����������Ӧ��Ȩ��", "����IRoleControl�ӿڵ�removeResourcesFromRole����", null);
		return true;
	}

	/*
	 * ˽�з������Ƴ���ɫ�Ĳ�����Ȩ����������Ӧ��Ȩ��
	 * @param roleId  ��ɫID
	 * @param resourceIds  ��ԴID���� ���resourceid�ö��Ÿ���
	 * @return void
	 * @throws PrivilegeException
	 */
	private void recycleResource(String roleId, String resourceIds)
			throws PrivilegeException {
		if (resourceIds == null || resourceIds.trim().length() == 0)
			return;
		String[] functionIds = resourceIds.split(",");
		for (int i = 0; i < functionIds.length; i++) {
			String hql = "from SmtAcl where roleid=:roleid and resourceid=:resourceid";
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("roleid", roleId);
			params.put("resourceid", functionIds[i]);
			List<Object> list = query(hql, params);
			if (list.size() == 0) {
				continue;
			}

			SmtAcl acl = (SmtAcl) list.get(0);
			delete(acl);
		}
		String hql = "select su from SmtAct sa,SmtUser su where sa.roleid=:roleid and sa.dispatchauth='1' and sa.objectid=su.id";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", roleId);
		List<Object> principals = query(hql, params);
		hql = "select sg from SmtAct sa,SmtGroup sg where sa.roleid=:roleid and sa.dispatchauth='1' and sa.objectid=sg.id";
		principals.addAll(query(hql, params));// ��ÿɷ���Ȩ�ý�ɫ������ ��Ȩ����
		for (int i = 0; i < principals.size(); i++) {
			SmtPrincipal principal = (SmtPrincipal) principals.get(i);
			List<ResourceVO> resourcesCanDispath = getResourcesCanDispath(principal
					.getId());
			String hqlownRole = "from SmtRole where owner=:owner";
			params.clear();
			params.put("owner", principal.getId());
			List<Object> ownerRoles = query(hqlownRole, params);// ��Ȩ������еĽ�ɫ��
			for (int k = 0; k < ownerRoles.size(); k++) {
				SmtRole roleOwner = (SmtRole) ownerRoles.get(k);
				String resourceids = compareResources(resourcesCanDispath,
						getResources(roleOwner.getId()));
				recycleResource(roleOwner.getId(), resourceids);// �ݹ�ɾ����ɫ��Դ
			}
		}
	}

	/*
	 * ˽�з������Ƚ���Դ���Ƿ���ϴ�С��ϵ��������������Զ�У��
	 * @param total  �ܵ���Դ����
	 * @param owner  ���еĽ�ɫ����Դ����
	 * @return ���ش��Ƴ�����Դ������","�ָ�
	 */
	private String compareResources(List<ResourceVO> total,
			List<ResourceVO> owner) {
		String resourceIds = "";
		for (int i = 0; i < owner.size(); i++) {
			if (!total.contains(owner.get(i)))
				resourceIds += owner.get(i).getId() + ",";
		}
		return resourceIds;
	}

	/*
	 * ˽�з�����������Ȩ������Է���Ȩ�����е���Դ
	 * @param principalId  ��Ȩ����ID
	 * @return List  �ɷ�����Դ����
	 * @throws PrivilegeException
	 */
	private List<ResourceVO> getResourcesCanDispath(String principalId)
			throws PrivilegeException {
		List<ResourceVO> resourcesCanDispath = new ArrayList<ResourceVO>();
		HashMap<String, String> params = new HashMap<String, String>();
		String hqlForRole = "from SmtAct where objectid=:objectid and dispatchauth='1'";// �ɷ��ڽ�ɫ����
		params.clear();
		params.put("objectid", principalId);
		List<Object> roles = query(hqlForRole, params);
		for (int j = 0; j < roles.size(); j++) {// ������н�ɫ����Դ��
			SmtAct sa = (SmtAct) roles.get(j);
			resourcesCanDispath.addAll(getResources(sa.getRoleid()));
		}
		SmtUser user = (SmtUser) get(SmtUser.class, principalId);
		if (user != null) {
			String hqlForGroup = "from SmtUsergroupref where userid=:userid";
			params.clear();
			params.put("userid", user.getId());
			List<Object> groups = query(hqlForGroup, params);
			for (int i = 0; i < groups.size(); i++) {
				String groupId = ((SmtUsergroupref) groups.get(i)).getGroupid();
				params.clear();
				params.put("objectid", groupId);
				List<Object> group_roles = query(hqlForRole, params);
				for (int k = 0; k < group_roles.size(); k++) {// ������н�ɫ����Դ��
					SmtAct sa = (SmtAct) roles.get(k);
					resourcesCanDispath.addAll(getResources(sa.getRoleid()));
				}
			}
		}
		RoleHelper.removeDuplicate(resourcesCanDispath);// ȥ���ظ��ļ�¼
		return resourcesCanDispath;
	}

	/**
	 * ����ɫ����Ȩ�ޡ����������ɫ���еĹ���Ȩ�ޣ�Ȼ�����������ñ����䴫������Ȩ��
	 * @param roleId
	 *            ��ɫID
	 * @param functionIds
	 *            ��ԴID���� ���functionid�ö��Ÿ���
	 * @return �ɹ����ý�ɫ����true
	 * @throws PrivilegeException
	 */
	public boolean resetResourcesToRole(String roleId, String functionIds)
			throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		if (role == null || role.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("��Ч��ɫID");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null))// Ȩ�޼��
			throw new PrivilegeException("û��Ȩ��");

		String removeResourcesIds = null;
		String addResourcesIds = null;
		List<String> resourceIds = Arrays.asList(functionIds.split(","));
		List<ResourceVO> resources = getResources(roleId);
		for (int i = 0; i < resources.size(); i++) {
			String compareResourceId = resources.get(i).getId();
			if (resourceIds.contains(compareResourceId)) {
				resourceIds.remove(compareResourceId);// ԭ���Ѿ����ڵ���Դ������Ҫ�����
			} else {
				removeResourcesIds += compareResourceId + ",";// ���ڲ����ڵ���Դ��Ҫɾ��
			}
		}
		for (int j = 0; j < resourceIds.size(); j++) {
			addResourcesIds += resourceIds.get(j) + ",";
		}
		this.removeResourcesFromRole(roleId, removeResourcesIds);// ɾ����ɫ������Դ
		this.addResousesToRole(roleId, addResourcesIds);// ����ɫ�����Դ
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("����ɫ"+role.getName()+"����Ȩ��", "����IRoleControl�ӿڵ�resetResourcesToRole����", null);
		return true;
	}

	/**
	 * ע����ɫ�����ݴ���Ľ�ɫID��ע���ý�ɫ
	 * @param roleId  ��ɫID
	 * @return �ɹ�ע������true
	 * @throws PrivilegeException
	 */
	public boolean revokeRole(String roleId) throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		if (role == null)
			throw new PrivilegeException("��Ч��ɫID");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_REVOKE,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null)) // �ж��Ƿ���Ȩ��
			throw new PrivilegeException("û��Ȩ��");

		String hql = "from SmtAct where  roleid=:roleid";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", roleId);
		List<Object> list = query(hql, params);
		if (list.size() > 0)
			throw new PrivilegeException("�ý�ɫ���ڹ�����Ϣ���޷�ע��");
		if (role.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("�ý�ɫ�ѱ�ע��");
		RoleVO roleVO = new RoleVO();
		BeanUtil.propertyCopy(role, roleVO);
		roleVO.setStatus(RoleHelper.UN_USEFUL);

		updateRole(roleVO);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("ע����ɫ"+role.getName(), "����IRoleControl�ӿڵ�revokeRole����", null);
		return true;
	}

	/**
	 * ���ý�ɫ�����ݽ�ɫID�����������ѱ�ע���Ľ�ɫ
	 * @param roleId  ��ɫID
	 * @return �ɹ����÷���true
	 * @throws PrivilegeException
	 */
	public boolean reuseRole(String roleId) throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		if (role == null)
			throw new PrivilegeException("��Ч��ɫID");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_REVOKE,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null)) // �ж��Ƿ���Ȩ��
			throw new PrivilegeException("û��Ȩ��");
		if (role.getStatus().equals(RoleHelper.USEFUL))
			throw new PrivilegeException("�ý�ɫ�ѱ�����");
		RoleVO roleVO = new RoleVO();
		BeanUtil.propertyCopy(role, roleVO);
		roleVO.setStatus(RoleHelper.USEFUL);
		updateRole(roleVO);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("���ý�ɫ"+role.getName(), "����IRoleControl�ӿڵ�reuseRole����", null);
		return true;
	}

	/**
	 * �����ɫ��δָ����ɫ�ĳ�����ʱ��Ĭ�ϴ�����Ϊ�ý�ɫ�ĳ�����
	 * @param role  ������Ľ�ɫ����
	 * @return �ɹ����淵��true
	 * @throws PrivilegeException
	 */
	public boolean saveRole(RoleVO role) throws PrivilegeException {
		List<Object> list = queryByName(role.getName(), true);
		if (list.size() > 0)
			throw new PrivilegeException("��ɫ�Ѵ���");

		SmtRole rrole = new SmtRole();
		BeanUtil.propertyCopy(role, rrole);
		String ownerId = rrole.getOwner();
		if (ownerId == null || ownerId.trim().length()==0) {// �Ƿ�ָ��������
			rrole.setOwner(getPrivilegeManager().getCueLoginUser().getId());// Ĭ��Ϊ��ǰ�û�
		} else {
			if (get(SmtUser.class, ownerId) == null)
				if (get(SmtGroup.class, ownerId) == null)
					throw new PrivilegeException("ָ���ĳ�������Ч");
		}
		if (rrole.getCreatedate() == null) {
			// rrole.setCreatedate(new
			// java.sql.Date(System.currentTimeMillis()));
		}
		if (rrole.getStatus() == null) {
			rrole.setStatus(RoleHelper.USEFUL);
		}
		super.save(rrole);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("�����ɫ"+role.getName(), "����IRoleControl�ӿڵ�saveRole����", null);
		return true;
	}

	/**
	 * ���½�ɫ��Ϣ
	 * @param role  ���µĽ�ɫ����
	 * @return �ɹ����·���true��ʧ�������ʧ��ԭ���׳���Ӧ�쳣
	 * @throws PrivilegeException
	 */
	public boolean updateRole(RoleVO role) throws PrivilegeException {
		boolean hasChangeOwner = false;
		SmtRole smtrole = (SmtRole) get(SmtRole.class, role.getId());
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, smtrole==null?null:smtrole.getOwner(), null)) // �ж��Ƿ���Ȩ��
			throw new PrivilegeException("û��Ȩ��");
		String roleId = role.getId();
		if (roleId == null)
			throw new PrivilegeException("��Ч�Ľ�ɫ");

		SmtRole oldRole = null;
		if ((oldRole = (SmtRole) get(SmtRole.class, roleId)) == null)
			throw new PrivilegeException("���µĽ�ɫ������");

		RoleVO roleVO = new RoleVO();
		BeanUtil.propertyCopy(oldRole, roleVO);
//		if (!validateHashCode(roleVO)) // �жϹ�ϣֵ�Ƿ���Ч
//			throw new PrivilegeException("��ϣֵУ�鲻��ȷ");

		if (role.getName() == null || role.getName().trim().length() == 0)
			throw new PrivilegeException("ָ���Ľ�ɫ������Ϊ��");

		if (!oldRole.getName().equals(role.getName())) {
			List<Object> rolelist = queryByName(role.getName(), true);
			if (!rolelist.isEmpty())
				throw new PrivilegeException("�ý�ɫ���Ѵ���");
		}

		SmtRole newRole = new SmtRole();
		BeanUtil.propertyCopy(role, newRole);

		if (!oldRole.getOwner().equals(role.getOwner())) {// �жϳ������Ƿ���ģ����ĺ��Ƿ���Ч
			if (get(SmtUser.class, role.getOwner()) == null) {
				if (get(SmtGroup.class, role.getOwner()) == null) {
					throw new PrivilegeException("ָ���ĳ�������Ч");
				}
			}
			hasChangeOwner = true;
		}

		getPrivilegeManager().getHbSession().clear();
		update(newRole);

		if (hasChangeOwner) {// ���ĳ��к��Ȩ�޴�С��֤
			List<ResourceVO> dispath_ress = getResourcesCanDispath(role
					.getOwner());
			List<ResourceVO> resByRole = getResources(role.getId());
			String needToRemoveRes = compareResources(dispath_ress, resByRole);
			if (needToRemoveRes.trim().length() > 0)
				removeResourcesFromRole(role.getId(), needToRemoveRes);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("���½�ɫ��Ϊ"+role.getName()+"�Ľ�ɫ", "����IRoleControl�ӿڵ�updateRole����", null);
		return true;

	}

	/**
	 * ���ݽ�ɫ�������ҽ�ɫ��isEqualΪtrue�ǽ��о�ȷ��ѯ��Ϊfalseʱ�����ģ����ѯ
	 * @param name  ��ɫ��
	 * @param isEqual �Ƿ�ȷ��ѯ
	 */
	public List<Object> queryByName(String name, boolean isEqual)
			throws PrivilegeException {// ��ģ��ƥ�����ȫƥ��
		List<Object> list = null;
		if (isEqual) {
			String hql = "from SmtRole where name='" + name + "'";
			list = query(hql, null);
		} else {
			String hql = "from SmtRole where name like '%" + name + "%'";
			list = query(hql, null);
		}
		return PrivilegeUtil.getVOList(list, RoleVO.class);
	}

	/**
	 * ������ɫID��ѯ�ý�ɫӵ�е���Դ
	 * @param roleid  ��ɫID
	 * @return List ��Դ����
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public List<ResourceVO> getResources(String roleid)
			throws PrivilegeException {
		String hql = "select sr from SmtAcl sa,SmtResource sr where sa.roleid=:roleid and sr.id=sa.resourceid";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", roleid);
		List resources = PrivilegeUtil.getVOList(query(hql, params),
				ResourceVO.class);
		return resources;
	}
	
	/**
	 * ���ݽ�ɫID�������������Դ��������Ч��Դ��
	 * @param roleid  ��ɫID
	 * @return ��Դ�б�
	 * @throws PrivilegeException
	 */
	private List<ResourceVO> getResourcesWithNoCheck(String roleid) throws PrivilegeException {
		String hql = "select sa from SmtAcl sa where sa.roleid=:roleid ";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", roleid);
		List<ResourceVO> resources = new ArrayList<ResourceVO>();
		List acls = query(hql, params);
		for(int i=0;i<acls.size();i++) {
			ResourceVO res = new ResourceVO();
			res.setId(((SmtAcl)acls.get(i)).getResourceid());
			resources.add(res);
		}
		return resources;
	}

	/**
	 * ���ݴ���Ķ�������֤HashCodeֵ
	 * @param obj  ��Ҫ��֤�Ķ���
	 * @return ��֤ͨ������true�����򷵻�false
	 * @throws PrivilegeException
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		RoleVO roleVO = (RoleVO) obj;
		return validateHashCode(roleVO.getId(), roleVO.getHashcode(),
				SmtRole.class);
	}

	/**
	 * ���ݵ�ǰ��¼�û����ҿɼ���ɫ��Ϣ������start��limit��ֵ��ѯĳһҳ��ֵ;��limitΪ-1ʱ,��ʾ��ѯ��¼�����пɼ���ɫ.
	 * @param cueUser  �û�����
	 * @param start  ��ʼλ��
	 * @param limit  ��ҳʱÿҳ��ʾ��
	 * @param params  HashMap����ѯRoleVO����������
	 * @return List ���ز��ҵ��ý�ɫ����
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public List<Object> queryByUser(UserVO cueUser, int start, int limit,
			HashMap params) throws PrivilegeException {
		Session sess = getPrivilegeManager().getHbSession();
		if (hasPermission(null, ResourcesPermissionConst.ROLE_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// ����Ƿ��ǳ�������Ա��ɼ����е���Դ��Ȩ��
			StringBuffer hqlForAll = new StringBuffer(
					"from SmtRole sr where 1=1 ");
			if (params != null) {
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					hqlForAll.append(" and sr." + key + " like '%"
							+ params.get(key) + "%'");
				}
			}
			List list = null;
			if (limit != -1) {
				list = sess.createQuery(hqlForAll.toString()).setFirstResult(
						start).setMaxResults(limit).list();
			} else {
				list = sess.createQuery(hqlForAll.toString()).list();
			}
			return PrivilegeUtil.getVOList(list, RoleVO.class);
		}

		if (!cueUser.getId().equals(
				getPrivilegeManager().getCueLoginUser().getId())
				&& !hasPermission(null, ResourcesPermissionConst.ROLE_ALL,
						CommomPermissionHelper.MANAGER_CHECK, cueUser
								.getOwnerId(), cueUser.getId()))// Ȩ�޼��
			throw new PrivilegeException("û��Ȩ�޲鿴���û��Ľ�ɫ!");
		if (get(SmtUser.class, cueUser.getId()) == null)
			throw new PrivilegeException("�û�������");
		StringBuilder hql = new StringBuilder(
				"select distinct sr from SmtAct sa,SmtRole sr,SmtUsergroupref ug where ((sr.owner=:owner or ( sa.objectid=:owner and sa.roleid=sr.id) or (ug.userid=:owner and sa.roleid=sr.id and ug.groupid=sa.objectid)) and (sa.objectid=ug.userid or sa.objectid=ug.groupid) and sr.status='1')");
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				hql.append(" and sr." + key + " like '%" + params.get(key)
						+ "%'");
			}
		}

		Query q = sess.createQuery(hql.toString());
		if(!hasPermission(null, ResourcesPermissionConst.ROLE_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {
			q.setString("owner", cueUser.getId());
		}
		if (limit != -1) {
			q.setFirstResult(start);
			q.setMaxResults(limit);
		}
		//String hqls = "select sr from SmtAct sa,SmtRole sr,SmtUsergroupref ug where (sr.owner=:owner or ( sa.objectid=:owner and sa.roleid=sr.roleid) or (ug.userid=:owner and sa.roleid=sr.roleid and ug.groupid=sa.objectid)) and sr.roleid=sa.roleid and (sa.objectid=ug.userid or sa.objectid=ug.groupid)";
		return PrivilegeUtil.getVOList(q.list(), RoleVO.class);

	}

	/**
	 * ��ҳ��ѯ:���ݵ�ǰ��¼�û���ѯ�ɼ������н�ɫ
	 * @param cueUser  �û�����
	 * @param start  ��ʼλ��
	 * @param limit  ��ҳʱÿҳ��ʾ��
	 * @param params  HashMap ��ѯRoleVO����������
	 * @return PageQueryData ���ط�ҳ��ѯ���ݶ���
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryByUserVO(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		if (cueUser == null) {
			cueUser = getPrivilegeManager().getCueLoginUser();
		}
		Session sess = getPrivilegeManager().getHbSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		if (hasPermission(null, ResourcesPermissionConst.ROLE_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// ����Ƿ��ǳ�������Ա��ɼ����е���Դ��Ȩ��
			hql.append("select count(*) from SmtRole sr where 1=1 and status='1' ");
		} else {
			hql.append("select count(distinct sr) from SmtAct sa,SmtRole sr,SmtUsergroupref ug where ((sr.owner=:owner or ( sa.objectid=:owner and sa.roleid=sr.id) or (ug.userid=:owner and sa.roleid=sr.id and ug.groupid=sa.objectid))  and (sa.objectid=ug.userid or sa.objectid=ug.groupid) and sr.status='1')");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				hql.append(" and sr." + key + " like '%" + params.get(key)
						+ "%'");
			}
		}

		Query query = sess.createQuery(hql.toString());
		if(!hasPermission(null, ResourcesPermissionConst.ROLE_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {
			query.setString("owner", cueUser.getId());
		}
		// ȡ��¼����
		int totalCount = Integer.parseInt(query.list().get(0).toString());
		List roles = queryByUser(cueUser, start, limit, params);
		pd.setTotalCount(totalCount);
		try {
			pd.setData(HQuery.fromList(roles));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * ������Ȩ����ID������ID�ͳ�����ԴID��������Ȩ����������Ȩ�����û����û��飩����������ɫ��Ȩ�޹�ϵ
	 * @param principalId ��Ȩ����Id
	 * @param sceneId ����Id
	 * @param absResourceId ������ԴId
	 * @return �ɹ�������Ȩ����true
	 * @throws PrivilegeException
	 */
	public boolean grantRemove(String principalId, String sceneId,
			String absResourceId) throws PrivilegeException {
		SmtPrincipal principal = (SmtPrincipal)((SmtUser) get(SmtUser.class, principalId) == null ? (SmtGroup) get(
				SmtGroup.class, principalId)
				: (SmtUser) get(SmtUser.class, principalId));
		SmtScene scene = (SmtScene) get(SmtScene.class, sceneId);
		SmtAbsResource absResource = (SmtAbsResource)((SmtResource) get(SmtResource.class,
				absResourceId) == null ? (SmtRole) get(SmtRole.class,
				absResourceId) : (SmtResource) get(SmtResource.class,
				absResourceId));
		PrincipalVO principalVo = new PrincipalVO();
		SceneVO sceneVo = new SceneVO();
		AbsResourceVO absResourceVo = new AbsResourceVO();
		BeanUtil.propertyCopy(principal, principalVo);
		BeanUtil.propertyCopy(scene, sceneVo);
		BeanUtil.propertyCopy(absResource, absResourceVo);
		grantRemove(principalVo, sceneVo, absResourceVo);
		return true;
	}

	/**
	 * ������Ȩ���󡢳�������ͳ�����Դ������������Ȩ����������Ȩ�����û����û��飩����������ɫ��Ȩ�޹�ϵ
	 * @param principal ��Ȩ����
	 * @param scene ��������
	 * @param absResource ������Դ����
	 * @return �ɹ�������Ȩ����true
	 * @throws PrivilegeException
	 */
	public boolean grantRemove(PrincipalVO principal, SceneVO scene,
			AbsResourceVO absResourceId) throws PrivilegeException {
		String pid = principal.getId();
		String rid = absResourceId.getId();
		String sid = scene.getSceneid();
		if (!hasPermission(null, ResourcesPermissionConst.GRANT_REMOVE,
				CommomPermissionHelper.MANAGER_CHECK, principal.getOwnerId(),
				pid))// Ȩ�޼��
			throw new PrivilegeException("û��Ȩ��");
		grantParamsValidata(pid, sid, rid);// ������Ч�Ե���֤
		HashMap<String, String> params = new HashMap<String, String>();
		if (!getPrivilegeManager().getIPermission().isSuperManager(// �Ƿ��ǳ�������Ա
				getPrivilegeManager().getCueLoginUser())) {
			String hqlforRole = "from SmtAct where roleid=:roleid  and objectid=:objectid";
			params.put("roleid", absResourceId.getId());
			params.put("objectid", getPrivilegeManager().getCueLoginUser()
					.getId());
			List<Object> listRes = super.query(hqlforRole, params);
			params.clear();
			params
					.put("owner", getPrivilegeManager().getCueLoginUser()
							.getId());
			listRes.addAll(query("from SmtRole where owner=:owner", params));
			if (listRes.isEmpty())
				throw new PrivilegeException("û��ȡ���ý�ɫ��Ȩ��");
		}
		String hql = "from SmtAct where roleid=:roleid and sceneid=:sceneid and objectid=:objectid";
		params.clear();
		params.put("roleid", absResourceId.getId());
		params.put("sceneid", scene.getSceneid());
		params.put("objectid", principal.getId());
		List<Object> lists = super.query(hql, params);
		if (lists.isEmpty())
			throw new PrivilegeException("ȡ����Ȩʧ�ܣ�����Ȩ������");
		delete(lists.get(0));

		List<ResourceVO> total = getResourcesCanDispath(principal.getId());
		String hqlownRole = "from SmtRole where owner=:owner";
		params.clear();
		params.put("owner", principal.getId());
		List<Object> ownerRole = query(hqlownRole, params);
		for (int k = 0; k < ownerRole.size(); k++) {
			SmtRole roleOwner = (SmtRole) ownerRole.get(k);
			String resourceids = compareResources(total, getResources(roleOwner
					.getId()));
			recycleResource(roleOwner.getId(), resourceids);// �ݹ�ɾ����ɫ��Դ
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("������Ȩ���󡢳�������ͳ�����Դ����������"+principal.getName()+"����Ȩ", "����IRoleControl�ӿڵ�grantRemove����", null);
		return true;
	}

	/**
	 * ����RoleVO������(id)���Ҷ���
	 * @param id  RoleVO�� id
	 * @return �����򷵻ظö��󣬷��򷵻�null
	 * @throws PrivilegeException
	 */
	public RoleVO getById(String id) throws PrivilegeException {
		RoleVO role = new RoleVO();
		BeanUtil.propertyCopy(get(SmtRole.class, id), role);
		return role;

	}

	/**
	 * ���ݶ���ID�������������еĽ�ɫ
	 * @param objectid ����ID
	 * @return ���ز��ҵ��Ľ�ɫ�б�
	 * @throws PrivilegeException
	 */
	public List findRoleByObject(String objectid) throws PrivilegeException{
		Session sess = getPrivilegeManager().getHbSession();
		List ids = sess.createQuery("select roleid from SmtAct where objectid=:objectid").setString("objectid", objectid).list();
		List list = new ArrayList();
		for(int i=0;i<ids.size();i++){
			String roleid = (String)ids.get(i);
			String hql = "from SmtRole where id=:roleid";
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("roleid", roleid);
			List<Object> roles = super.query(hql, params);
			if(!roles.isEmpty()){
				SmtRole smtRole = (SmtRole)roles.get(0);
				RoleVO role = new RoleVO();
				try {
					BeanUtil.propertyCopy(smtRole, role);
				} catch (PrivilegeException e) {
					throw new PrivilegeException(e.getMessage());
				}
				list.add(role);
			}
		}
		return list;
	}

}