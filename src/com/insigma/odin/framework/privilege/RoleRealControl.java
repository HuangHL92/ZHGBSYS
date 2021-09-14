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
 * 角色管理实现类
 * 
 * @author yangl
 * @created 18-九月-2009 11:48:11
 */
public class RoleRealControl extends AbstractPrivilegeBase implements
		IRoleControl {

	public RoleRealControl() {

	}

	/**
	 * 给角色授权。建立角色与资源的关联 (这里是单纯的给角色增加权限，不需要先将角色已经有的权限删掉)
	 * @param roleId
	 *            授权角色的ID
	 * @param resourceIds
	 *            资源ID串 多个functionid用逗号隔开
	 * @return 成功返回true
	 * @throws PrivilegeException
	 */
	public boolean addResousesToRole(String roleId, String resourceIds)
			throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		/*
		 * jinwei - 2011.7.22 取消只有持有该角色才能给其增加资源的规则限制
		if(!role.getOwner().equals(getPrivilegeManager().getCueLoginUser().getId())) {
			throw new PrivilegeException("不能修改其他用户持有的角色！");
		}
		*/
		if (RoleHelper.isUsefulRole(role) == RoleHelper.ROLE_NOT_EXIST)
			throw new PrivilegeException("该角色不存在！");
		if (RoleHelper.isUsefulRole(role) == RoleHelper.ROLE_UN_USEFUL)
			throw new PrivilegeException("无效的角色");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null))// 权限检测
			throw new PrivilegeException("没有权限");

		List<String> canDispatchResourceIds = new ArrayList<String>();// 可分授的资源和
		List<Object> roles = getRolesByPrincipalId(getPrivilegeManager()// 可分授权的角色集
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
//				throw new PrivilegeException("资源不存在");
			
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
				throw new PrivilegeException("没有分授该资源的权限");

			SmtAcl acl = new SmtAcl();
			acl.setRoleid(roleId);
			acl.setResourceid(functionIds[i]);
			save(acl);
			temp++;
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("给角色"+role.getName()+"授予"+temp+"条资源", "调用IRoleControl接口的addResousesToRole方法", null);
		return true;
	}

	/**
	 * 删除角色，根据传入的角色ID进行角色的删除
	 * @param roleid
	 *            待删除的角色id
	 * @return 成功删除返回true
	 * @throws PrivilegeException
	 */
	public boolean deleteRole(String roleid) throws PrivilegeException {
		RoleVO role = new RoleVO();
		role.setId(roleid);
		deleteRole(role);
		return true;
	}

	/**
	 * 删除角色，根据传入的角色来进行删除
	 * @param role
	 *            待删除的角色对象
	 * @return 成功删除返回true
	 * @throws PrivilegeException
	 */
	public boolean deleteRole(RoleVO role) throws PrivilegeException {

		if (role == null)
			throw new PrivilegeException("无效的角色对象");
		SmtRole rrole = (SmtRole) get(SmtRole.class, role.getId());
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_DELETE,
				CommomPermissionHelper.OWNER_CHECK, rrole.getOwner(), null))// 权限检测
			throw new PrivilegeException("没有权限");
		String hql = "from SmtAct where roleid=:roleId";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleId", rrole.getId());
		List<Object> lists = super.query(hql, params);
		if (lists.size() > 0) {
			throw new PrivilegeException("该角色存在"+lists.size()+"条关联信息(包括用户和用户组)，不允许删除");
		}
		getPrivilegeManager().getHbSession().clear();
		delete(rrole);

		String hqlacl = "from SmtAcl where roleid='" + role.getId() + "'";
		delete(hqlacl);

		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("删除角色"+role.getName(), "调用IRoleControl接口的deleteRole方法", null);
		return true;
	}

	/**
	 * 根据对象ID来查询其可分授权的可用角色或全部可用角色。
	 * 当isCanDispatch为true时查询该用户可分授权的可用角色，否则为查询全部可用角色
	 * @param principalId  对象ID
	 * @param isCanDispatch  是否可分授权标记，true为查询可分授权的，false为查询所有的
	 * @return List 返回查找到得角色集合
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
										.getOwnerId(), principalId))// 权限检测
					throw new PrivilegeException("没有权限");
			}
			if (group != null
					&& !hasPermission(null, ResourcesPermissionConst.USER_ALL,
							CommomPermissionHelper.MANAGER_CHECK, group
									.getOwnerId(), principalId))// 权限检测
				throw new PrivilegeException("没有权限");

			if (user == null && group == null)
				throw new PrivilegeException("授权对象不存在");
		}

		//if (getPrivilegeManager().getIPermission().isSuperManager(// 是否是超级管理员
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
		//System.out.print("from SmtUsergroupref where userid="+principalId+"获取到用户组数为"+groups.size());
		for (int i = 0; i < groups.size(); i++) {
			
			SmtUsergroupref sug = (SmtUsergroupref) groups.get(i);
			roles.addAll(getRoleByPrincipalId(sug.getGroupid(), isCanDispatch));// 组可用的角色
		}
		//System.out.print("获取组可用的角色成功！principalId="+principalId);
		roles.addAll(getRoleByPrincipalId(principalId, isCanDispatch));// 自己可用的角色
		RoleHelper.removeDuplicate(roles);// 去除重复的记录
		return PrivilegeUtil.getVOList(roles, RoleVO.class);
	}

	/*
	 * 私有方法,供程序内部调用 根据授权对象ID查询角色
	 * @param principalId  授权对象ID
	 * @param isCanDispatch  是否可分授权
	 * @return List 返回查找到得角色集合
	 */
	private List<Object> getRoleByPrincipalId(String principalId,
			boolean isCanDispatch) {
		HashMap<String, String> params = new HashMap<String, String>();
		String hql = "select sr from SmtAct sa,SmtRole sr where sr.status='1' and sa.objectid=:userId and sa.roleid=sr.id ";
		if (isCanDispatch) {
			hql += " and dispatchauth='1'";
		}
		hql += " or sr.owner=:owner";// 持有的角色
		params.put("userId", principalId);
		params.put("owner", principalId);
		return query(hql, params);
	}

	/**
	 * 根据授权对象ID、场景ID和抽象资源ID给授权对象授权。即建立授权对象（用户或用户组）、场景、角色的权限关系。
	 * @param principalId 授权对象id
	 * @param sceneId 场景id
	 * @param absResourceId 抽象资源id
	 * @param isCanDispatch 指定授权对象是否可以分授权该角色，true为可分授权
	 * @return 成功授权返回true
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
	 * 根据授权对象、场景对象和抽象资源对象给授权对象授权。即建立授权对象（用户或用户组）、场景、角色的权限关系。
	 * @param principal  授权对象
	 * @param scene  场景对象
	 * @param absResource  抽象资源对象
	 * @param isCanDispatch  指定授权对象是否可以分授权该角色，true为可分授权
	 * @return 成功授权返回true
	 * @throws PrivilegeException
	 */
	public boolean grant(PrincipalVO principal, SceneVO scene,
			AbsResourceVO absResource, boolean isCanDispatch)
			throws PrivilegeException {
		boolean canDispatchAbs = true;// 是否可分授权该角色的标志
		/*注意 注意 这里本来是false，又system统一创建角色后改为true！！！！！！*/
		String pid = principal.getId();
		String rid = absResource.getId();
		String sid = scene.getSceneid();
		if (!hasPermission(null, ResourcesPermissionConst.GRANT_CREATE,
				CommomPermissionHelper.MANAGER_CHECK, principal.getOwnerId(),
				pid))// 权限检测
			throw new PrivilegeException("没有权限");
		String usertype = grantParamsValidata(pid, sid, rid);// 参数有效性的验证

		String hql = "from SmtAct where roleid=:roleid and sceneid=:sceneid and objectid=:objectid";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", absResource.getId());
		params.put("sceneid", scene.getSceneid());
		params.put("objectid", principal.getId());
		List<Object> lists = super.query(hql, params);
		if (lists.size() > 0)// 授权已存在，直接返回true
			return true;
		if(principal.getId().equals(getPrivilegeManager().getCueLoginUser().getId())) {
			throw new PrivilegeException("无法对自己授权!");
		}
		
		if (getPrivilegeManager().getIPermission().isSuperManager(// 是否是超级管理员
				getPrivilegeManager().getCueLoginUser())) {
			canDispatchAbs = true;
		}

		if (!canDispatchAbs) {// 是否可分授该角色
			List<Object> roles = getRolesByPrincipalId(getPrivilegeManager()// 可分授权的角色集
					.getCueLoginUser().getId(), true);
			for (int i = 0; i < roles.size(); i++) {
				if (absResource.getId().equals(((RoleVO) roles.get(i)).getId())) {
					canDispatchAbs = true;
				}
			}
		}
		if (!canDispatchAbs)
			throw new PrivilegeException("无权限分授该角色");
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
		privilegeLog.log("根据授权对象、场景对象和抽象资源对象给授权对象"+principal.getName()+"授权", "调用IRoleControl接口的grant方法", null);
		return true;
	}

	/*
	 * 验证以下三个参数的有效性
	 * @param principalId  授权对象ID
	 * @param sceneId  场景ID
	 * @param absResourceId  资源ID
	 * @return 返回传入的授权对象的类型
	 * @throws PrivilegeException
	 */
	private String grantParamsValidata(String principalId, String sceneId,
			String absResourceId) throws PrivilegeException {
		String usertype = null;
		SmtUser user = (SmtUser) get(SmtUser.class, principalId);
		SmtGroup group = (SmtGroup) get(SmtGroup.class, principalId);
		if (user == null && group == null) {
			throw new PrivilegeException("授权对象不存在");
		}
		if (user != null) {
			usertype = RoleHelper.UN_USEFUL;
			if (user.getStatus().equals(RoleHelper.UN_USEFUL))
				throw new PrivilegeException("无效的授权对象");
		} else {
			usertype = RoleHelper.USEFUL;
			if (group.getStatus().equals(RoleHelper.UN_USEFUL))
				throw new PrivilegeException("无效的授权对象");
		}
		SmtScene checkscene = (SmtScene) get(SmtScene.class, sceneId);
		if (checkscene == null) {
			throw new PrivilegeException("场景不存在");
		}
		if (checkscene.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("无效的场景");
		SmtRole role = (SmtRole) get(SmtRole.class, absResourceId);
		SmtResource resource = (SmtResource) get(SmtResource.class,
				absResourceId);
		if (role == null && resource == null) {
			throw new PrivilegeException("资源不存在");
		}
		if (role != null
				&& RoleHelper.isUsefulRole(role) == RoleHelper.ROLE_UN_USEFUL)
			throw new PrivilegeException("无效的角色");
		if (resource != null
				&& ResourceHelper.isUsefulResource(resource) == ResourceHelper.RESOURCE_UN_USEFUL)
			throw new PrivilegeException("无效的资源");
		return usertype;
	}

	/**
	 * 撤销角色的部分授权，并回收相应的权限
	 * @param roleId  角色ID
	 * @param resourceIds  资源ID 多个resourceid用逗号隔开
	 * @return 成功撤销授权返回true
	 * @throws PrivilegeException
	 */
	public boolean removeResourcesFromRole(String roleId, String resourceIds)
			throws PrivilegeException {
		SmtRole role = (SmtRole) super.get(SmtRole.class, roleId);
		if (role == null || role.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("角色不存在");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null)) // 判断是否有权限
			throw new PrivilegeException("没有权限");
		recycleResource(roleId, resourceIds);// 资源回收
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("撤销角色"+role.getName()+"的部分授权，并回收相应的权限", "调用IRoleControl接口的removeResourcesFromRole方法", null);
		return true;
	}

	/*
	 * 私有方法，移除角色的部分授权，并回收相应的权限
	 * @param roleId  角色ID
	 * @param resourceIds  资源ID串， 多个resourceid用逗号隔开
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
		principals.addAll(query(hql, params));// 获得可分授权该角色的所有 授权对象
		for (int i = 0; i < principals.size(); i++) {
			SmtPrincipal principal = (SmtPrincipal) principals.get(i);
			List<ResourceVO> resourcesCanDispath = getResourcesCanDispath(principal
					.getId());
			String hqlownRole = "from SmtRole where owner=:owner";
			params.clear();
			params.put("owner", principal.getId());
			List<Object> ownerRoles = query(hqlownRole, params);// 授权对象持有的角色集
			for (int k = 0; k < ownerRoles.size(); k++) {
				SmtRole roleOwner = (SmtRole) ownerRoles.get(k);
				String resourceids = compareResources(resourcesCanDispath,
						getResources(roleOwner.getId()));
				recycleResource(roleOwner.getId(), resourceids);// 递归删除角色资源
			}
		}
	}

	/*
	 * 私有方法，比较资源和是否符合大小关系，不符合则进行自动校正
	 * @param total  总的资源集合
	 * @param owner  持有的角色的资源集合
	 * @return 返回待移除的资源串，以","分隔
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
	 * 私有方法，返回授权对象可以分授权的所有的资源
	 * @param principalId  授权对象ID
	 * @return List  可分授资源集合
	 * @throws PrivilegeException
	 */
	private List<ResourceVO> getResourcesCanDispath(String principalId)
			throws PrivilegeException {
		List<ResourceVO> resourcesCanDispath = new ArrayList<ResourceVO>();
		HashMap<String, String> params = new HashMap<String, String>();
		String hqlForRole = "from SmtAct where objectid=:objectid and dispatchauth='1'";// 可分授角色集合
		params.clear();
		params.put("objectid", principalId);
		List<Object> roles = query(hqlForRole, params);
		for (int j = 0; j < roles.size(); j++) {// 获得所有角色的资源和
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
				for (int k = 0; k < group_roles.size(); k++) {// 获得所有角色的资源和
					SmtAct sa = (SmtAct) roles.get(k);
					resourcesCanDispath.addAll(getResources(sa.getRoleid()));
				}
			}
		}
		RoleHelper.removeDuplicate(resourcesCanDispath);// 去除重复的记录
		return resourcesCanDispath;
	}

	/**
	 * 给角色重置权限。即先清除角色所有的功能权限，然后再重新设置保存其传进来的权限
	 * @param roleId
	 *            角色ID
	 * @param functionIds
	 *            资源ID串， 多个functionid用逗号隔开
	 * @return 成功重置角色返回true
	 * @throws PrivilegeException
	 */
	public boolean resetResourcesToRole(String roleId, String functionIds)
			throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		if (role == null || role.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("无效角色ID");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null))// 权限检测
			throw new PrivilegeException("没有权限");

		String removeResourcesIds = null;
		String addResourcesIds = null;
		List<String> resourceIds = Arrays.asList(functionIds.split(","));
		List<ResourceVO> resources = getResources(roleId);
		for (int i = 0; i < resources.size(); i++) {
			String compareResourceId = resources.get(i).getId();
			if (resourceIds.contains(compareResourceId)) {
				resourceIds.remove(compareResourceId);// 原本已经存在的资源，不必要再添加
			} else {
				removeResourcesIds += compareResourceId + ",";// 现在不存在的资源，要删除
			}
		}
		for (int j = 0; j < resourceIds.size(); j++) {
			addResourcesIds += resourceIds.get(j) + ",";
		}
		this.removeResourcesFromRole(roleId, removeResourcesIds);// 删除角色部分资源
		this.addResousesToRole(roleId, addResourcesIds);// 给角色添加资源
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("给角色"+role.getName()+"重置权限", "调用IRoleControl接口的resetResourcesToRole方法", null);
		return true;
	}

	/**
	 * 注销角色。根据传入的角色ID来注销该角色
	 * @param roleId  角色ID
	 * @return 成功注销返回true
	 * @throws PrivilegeException
	 */
	public boolean revokeRole(String roleId) throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		if (role == null)
			throw new PrivilegeException("无效角色ID");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_REVOKE,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null)) // 判断是否有权限
			throw new PrivilegeException("没有权限");

		String hql = "from SmtAct where  roleid=:roleid";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roleid", roleId);
		List<Object> list = query(hql, params);
		if (list.size() > 0)
			throw new PrivilegeException("该角色存在关联信息，无法注销");
		if (role.getStatus().equals(RoleHelper.UN_USEFUL))
			throw new PrivilegeException("该角色已被注销");
		RoleVO roleVO = new RoleVO();
		BeanUtil.propertyCopy(role, roleVO);
		roleVO.setStatus(RoleHelper.UN_USEFUL);

		updateRole(roleVO);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("注销角色"+role.getName(), "调用IRoleControl接口的revokeRole方法", null);
		return true;
	}

	/**
	 * 启用角色。根据角色ID来重新启用已被注销的角色
	 * @param roleId  角色ID
	 * @return 成功启用返回true
	 * @throws PrivilegeException
	 */
	public boolean reuseRole(String roleId) throws PrivilegeException {
		SmtRole role = (SmtRole) get(SmtRole.class, roleId);
		if (role == null)
			throw new PrivilegeException("无效角色ID");
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_REVOKE,
				CommomPermissionHelper.OWNER_CHECK, role.getOwner(), null)) // 判断是否有权限
			throw new PrivilegeException("没有权限");
		if (role.getStatus().equals(RoleHelper.USEFUL))
			throw new PrivilegeException("该角色已被启用");
		RoleVO roleVO = new RoleVO();
		BeanUtil.propertyCopy(role, roleVO);
		roleVO.setStatus(RoleHelper.USEFUL);
		updateRole(roleVO);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("启用角色"+role.getName(), "调用IRoleControl接口的reuseRole方法", null);
		return true;
	}

	/**
	 * 保存角色。未指定角色的持有者时，默认创建者为该角色的持有者
	 * @param role  待保存的角色对象
	 * @return 成功保存返回true
	 * @throws PrivilegeException
	 */
	public boolean saveRole(RoleVO role) throws PrivilegeException {
		List<Object> list = queryByName(role.getName(), true);
		if (list.size() > 0)
			throw new PrivilegeException("角色已存在");

		SmtRole rrole = new SmtRole();
		BeanUtil.propertyCopy(role, rrole);
		String ownerId = rrole.getOwner();
		if (ownerId == null || ownerId.trim().length()==0) {// 是否指定持有者
			rrole.setOwner(getPrivilegeManager().getCueLoginUser().getId());// 默认为当前用户
		} else {
			if (get(SmtUser.class, ownerId) == null)
				if (get(SmtGroup.class, ownerId) == null)
					throw new PrivilegeException("指定的持有者无效");
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
		privilegeLog.log("保存角色"+role.getName(), "调用IRoleControl接口的saveRole方法", null);
		return true;
	}

	/**
	 * 更新角色信息
	 * @param role  更新的角色对象
	 * @return 成功更新返回true，失败则根据失败原因抛出相应异常
	 * @throws PrivilegeException
	 */
	public boolean updateRole(RoleVO role) throws PrivilegeException {
		boolean hasChangeOwner = false;
		SmtRole smtrole = (SmtRole) get(SmtRole.class, role.getId());
		if (!hasPermission(null, ResourcesPermissionConst.ROLE_ALL_EDIT,
				CommomPermissionHelper.OWNER_CHECK, smtrole==null?null:smtrole.getOwner(), null)) // 判断是否有权限
			throw new PrivilegeException("没有权限");
		String roleId = role.getId();
		if (roleId == null)
			throw new PrivilegeException("无效的角色");

		SmtRole oldRole = null;
		if ((oldRole = (SmtRole) get(SmtRole.class, roleId)) == null)
			throw new PrivilegeException("更新的角色不存在");

		RoleVO roleVO = new RoleVO();
		BeanUtil.propertyCopy(oldRole, roleVO);
//		if (!validateHashCode(roleVO)) // 判断哈希值是否有效
//			throw new PrivilegeException("哈希值校验不正确");

		if (role.getName() == null || role.getName().trim().length() == 0)
			throw new PrivilegeException("指定的角色名不能为空");

		if (!oldRole.getName().equals(role.getName())) {
			List<Object> rolelist = queryByName(role.getName(), true);
			if (!rolelist.isEmpty())
				throw new PrivilegeException("该角色名已存在");
		}

		SmtRole newRole = new SmtRole();
		BeanUtil.propertyCopy(role, newRole);

		if (!oldRole.getOwner().equals(role.getOwner())) {// 判断持有者是否更改，更改后是否有效
			if (get(SmtUser.class, role.getOwner()) == null) {
				if (get(SmtGroup.class, role.getOwner()) == null) {
					throw new PrivilegeException("指定的持有者无效");
				}
			}
			hasChangeOwner = true;
		}

		getPrivilegeManager().getHbSession().clear();
		update(newRole);

		if (hasChangeOwner) {// 更改持有后的权限大小验证
			List<ResourceVO> dispath_ress = getResourcesCanDispath(role
					.getOwner());
			List<ResourceVO> resByRole = getResources(role.getId());
			String needToRemoveRes = compareResources(dispath_ress, resByRole);
			if (needToRemoveRes.trim().length() > 0)
				removeResourcesFromRole(role.getId(), needToRemoveRes);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("更新角色名为"+role.getName()+"的角色", "调用IRoleControl接口的updateRole方法", null);
		return true;

	}

	/**
	 * 根据角色名来查找角色。isEqual为true是进行精确查询，为false时则进行模糊查询
	 * @param name  角色名
	 * @param isEqual 是否精确查询
	 */
	public List<Object> queryByName(String name, boolean isEqual)
			throws PrivilegeException {// 分模糊匹配和完全匹配
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
	 * 根绝角色ID查询该角色拥有的资源
	 * @param roleid  角色ID
	 * @return List 资源集合
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
	 * 根据角色ID来获得其所有资源（包括无效资源）
	 * @param roleid  角色ID
	 * @return 资源列表
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
	 * 根据传入的对象来验证HashCode值
	 * @param obj  需要验证的对象
	 * @return 验证通过返回true，否则返回false
	 * @throws PrivilegeException
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		RoleVO roleVO = (RoleVO) obj;
		return validateHashCode(roleVO.getId(), roleVO.getHashcode(),
				SmtRole.class);
	}

	/**
	 * 根据当前登录用户查找可见角色信息。根据start和limit的值查询某一页的值;当limit为-1时,表示查询登录者所有可见角色.
	 * @param cueUser  用户对象
	 * @param start  起始位置
	 * @param limit  分页时每页显示数
	 * @param params  HashMap，查询RoleVO的限制条件
	 * @return List 返回查找到得角色集合
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public List<Object> queryByUser(UserVO cueUser, int start, int limit,
			HashMap params) throws PrivilegeException {
		Session sess = getPrivilegeManager().getHbSession();
		if (hasPermission(null, ResourcesPermissionConst.ROLE_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// 检测是否是超级管理员或可见所有的资源级权限
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
								.getOwnerId(), cueUser.getId()))// 权限检测
			throw new PrivilegeException("没有权限查看该用户的角色!");
		if (get(SmtUser.class, cueUser.getId()) == null)
			throw new PrivilegeException("用户不存在");
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
	 * 分页查询:根据当前登录用户查询可见的所有角色
	 * @param cueUser  用户对象
	 * @param start  起始位置
	 * @param limit  分页时每页显示数
	 * @param params  HashMap 查询RoleVO的限制条件
	 * @return PageQueryData 返回分页查询数据对象
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
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// 检测是否是超级管理员或可见所有的资源级权限
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
		// 取记录总数
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
	 * 根据授权对象ID、场景ID和抽象资源ID来撤销授权。即撤销授权对象（用户或用户组）、场景、角色的权限关系
	 * @param principalId 授权对象Id
	 * @param sceneId 场景Id
	 * @param absResourceId 抽象资源Id
	 * @return 成功撤销授权返回true
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
	 * 根据授权对象、场景对象和抽象资源对象来撤销授权。即撤销授权对象（用户或用户组）、场景、角色的权限关系
	 * @param principal 授权对象
	 * @param scene 场景对象
	 * @param absResource 抽象资源对象
	 * @return 成功撤销授权返回true
	 * @throws PrivilegeException
	 */
	public boolean grantRemove(PrincipalVO principal, SceneVO scene,
			AbsResourceVO absResourceId) throws PrivilegeException {
		String pid = principal.getId();
		String rid = absResourceId.getId();
		String sid = scene.getSceneid();
		if (!hasPermission(null, ResourcesPermissionConst.GRANT_REMOVE,
				CommomPermissionHelper.MANAGER_CHECK, principal.getOwnerId(),
				pid))// 权限检测
			throw new PrivilegeException("没有权限");
		grantParamsValidata(pid, sid, rid);// 参数有效性的验证
		HashMap<String, String> params = new HashMap<String, String>();
		if (!getPrivilegeManager().getIPermission().isSuperManager(// 是否是超级管理员
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
				throw new PrivilegeException("没有取消该角色的权限");
		}
		String hql = "from SmtAct where roleid=:roleid and sceneid=:sceneid and objectid=:objectid";
		params.clear();
		params.put("roleid", absResourceId.getId());
		params.put("sceneid", scene.getSceneid());
		params.put("objectid", principal.getId());
		List<Object> lists = super.query(hql, params);
		if (lists.isEmpty())
			throw new PrivilegeException("取消授权失败，该授权不存在");
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
			recycleResource(roleOwner.getId(), resourceids);// 递归删除角色资源
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("根据授权对象、场景对象和抽象资源来撤销对象"+principal.getName()+"的授权", "调用IRoleControl接口的grantRemove方法", null);
		return true;
	}

	/**
	 * 根据RoleVO的主键(id)查找对象
	 * @param id  RoleVO的 id
	 * @return 存在则返回该对象，否则返回null
	 * @throws PrivilegeException
	 */
	public RoleVO getById(String id) throws PrivilegeException {
		RoleVO role = new RoleVO();
		BeanUtil.propertyCopy(get(SmtRole.class, id), role);
		return role;

	}

	/**
	 * 根据对象ID来查找其所持有的角色
	 * @param objectid 对象ID
	 * @return 返回查找到的角色列表
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