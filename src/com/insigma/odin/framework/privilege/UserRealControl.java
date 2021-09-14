package com.insigma.odin.framework.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.helper.CommomPermissionHelper;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.helper.UserHelper;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.util.HQuery;
import com.insigma.odin.framework.sys.cap.businesssys.util.HttpConnection;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;


/**
 * @author 陈飞龙
 * @created 18-九月-2009 11:48:15
 */
public class UserRealControl extends AbstractPrivilegeBase implements
		IUserControl {

	public UserRealControl() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 *            传入要删除的用户
	 * @return    true 对此用户的删除操作成功，false 对此用户的删除操作失败
	 * @throws PrivilegeException 可能抛出的异常：用户不存在,用户在对自己进行非法操作
	 */
	public boolean deleteUser(UserVO user) throws PrivilegeException {
		if(user==null){
			throw new PrivilegeException("传入的要删除的用户为null");
		}
		String userId = user.getId();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, userId);
		if(smtUser==null){
			throw new PrivilegeException("删除的用户不存在");
		}
		if (super.getPrivilegeManager().getCueLoginUser().getLoginname()
				.equals(smtUser.getLoginname())) {
			throw new PrivilegeException("用户在对自己进行非法操作");
		}
		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_DELETE,
				CommomPermissionHelper.OWNER_CHECK, smtUser.getOwnerId(), null)) {// 是否有权限操作
			throw new PrivilegeException("无操作权限，请联系管理员");
		}
		if (userParamsValidata(userId,false)) {
			super.delete("from SmtUsergroupref sug where sug.userid='"
					+ userId + "'");
			super.delete("from SmtAct sa where sa.objectid='" + userId
					+ "'");
			super.delete("from SmtUser su where su.id='" + userId + "'");
			PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
			privilegeLog.log("删除用户"+smtUser.getLoginname(), "调用IUserControl接口的deleteUser方法", null);
			return true;
		}
		return false;	
	}

	
	
	/**
	 * 为分页查询提供的查询方法
	 * 
	 * @param cueUser
	 *            用户对象
	 * @param start
	 *            起始位置
	 * @param limit
	 *            分页时每页显示数
	 * @param params
	 *            HashMap 查询RoleVO的限制条件
	 * @param isAll 是否级联查询组下所有成员
	 * @return PageQueryData 返回分页查询数据对象
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryByUserVO(UserVO cueUser, int start,
			int limit, HashMap params,boolean isAll) throws PrivilegeException {
		if (cueUser == null) {
			cueUser = getPrivilegeManager().getCueLoginUser();
		}
		Session sess = getPrivilegeManager().getHbSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		if (hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// 检测是否是超级管理员或可见所有的资源级权限
			hql.append("select count(distinct su) from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") ");
		} else {
			hql
					.append("select count(distinct su) from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") and (su.ownerId='"
							+ cueUser.getId()
							+ "' or "
							+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
							+ " (select sg.id from SmtGroup sg where sg.ownerId='"
							+ cueUser.getId()
							+ "') or sug.groupid in"
							+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
							+ cueUser.getId() + "')))");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				if("querysql".equals(key)){
					continue;
				}
				if(!"gsearch".equals(key)) {
					hql.append(" and su." + key + " like '%" + params.get(key)
						+ "%'");
				}
			}
		}
		Query query = sess.createQuery(hql.toString());
		List list = query.list();
		// 取记录总数
		int totalCount = Integer.parseInt(query.list().get(0).toString());
		List roles = queryByUser(cueUser, start, limit, params,true);
		pd.setTotalCount(totalCount);
		try {
			pd.setData(HQuery.fromList(roles));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * 根据当前登录的用户查找它的可见的用户//
	 * 
	 * @param cueUser
	 *            当前登录的用户
	 * @param start
	 *            起始数
	 * @param limit
	 *            限制数
	 * @param params
	 *            HashMap 查询RoleVO的限制条件
	 * @param isAll 是否级联查询组下所有成员
	 * @return  list   返回当前用户的可见用户
	 */
	public List queryByUser(UserVO cueUser, int start, int limit, HashMap params,boolean isAll)
			throws PrivilegeException {
		Session sess = super.getPrivilegeManager().getHbSession();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, cueUser.getId());
		List list = new ArrayList();
		boolean querysql = false;
		if (super.hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null,null)) {
				StringBuffer hql3 = new StringBuffer("select distinct su from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") ");		
				if (params != null) {
					Set<String> keys = params.keySet();
					Iterator<String> it = keys.iterator();
					while (it.hasNext()) {
						String key = it.next();
						if("querysql".equals(key)){
							querysql = true;
							continue;
						}else{
							if(!"gsearch".equals(key)) {
								hql3.append(" and su." + key + " like '%" + params.get(key)
									+ "%'");
							}
						}
					}
				}
				if(querysql){
					hql3 = addSql(params,hql3);
				}
				Query q3 = sess.createQuery(hql3.toString());
				if (limit != -1) {
					q3.setFirstResult(start);
					q3.setMaxResults(limit);
				}
				return PrivilegeUtil.getVOList(q3.list(), UserVO.class);
		 }
		 
		StringBuffer sql = new StringBuffer("select distinct su from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") and (su.ownerId='"
				+ cueUser.getId()
				+ "' or "
				+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
				+ " (select sg.id from SmtGroup sg where sg.ownerId='"
				+ cueUser.getId()
				+ "') or sug.groupid in"
				+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
				+ cueUser.getId() + "')))");
		 if (params != null) {
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if("querysql".equals(key)){
						querysql = true;
						continue;
					}
					if(!"gsearch".equals(key)) {
						sql.append("and  su." + key + " like '%" + params.get(key)+ "%' ");
					}
				}
		 }		
		 if(querysql){
			sql = addSql(params,sql);
		 }
		Query query = sess.createQuery(sql.toString());
		if (limit != -1) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}	
		return PrivilegeUtil.getVOList(query.list(),UserVO.class);
	}

	/**
	 * 根据用户的ＩＤ注销用户
	 * 
	 * @param userId
	 *            要注销的用户的ＩＤ
	 * @return    true 对此用户的注销操作成功，false 对此用户的注销操作失败
	 * 
	 * @throws  PrivilegeException  可能抛出的异常：用户在对自己进行非法操作，用户状态已经为无效
	 */
	public boolean revokeUser(String userId) throws PrivilegeException {
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if (super.getPrivilegeManager().getCueLoginUser().getId().equals(userId)) {
			throw new PrivilegeException("用户在对自己进行非法操作");
		}
		if(smtUser==null){
			throw new PrivilegeException("该用户不存在");
		}
		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_REVOKE,
			CommomPermissionHelper.OWNER_CHECK, smtUser.getOwnerId(),null)) {
			throw new PrivilegeException("无操作权限,请联系管理员");
		}
		if (userParamsValidata(userId, false)) {
			if (smtUser.getStatus().equals("0")) {
				throw new PrivilegeException("该用户状态已经为无效");
			}
			smtUser.setStatus("0");
			super.update(smtUser);
			return true;
			}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("注销用户"+smtUser.getLoginname(), "调用IUserControl接口的revokeUser方法", null);
		return false;	
	}

	/**
	 * 创建新用户
	 * 
	 * @param user
	 *            需保存的用户
	 * 
	 * @return    true 对此用户的创建操作成功  false 表示没有相应的操作权限
	 * @throws    PrivilegeException  可能抛出的异常：请传入要创建的用户，用户已存在，指定的持有者无效
	 */
	public boolean saveUser(UserVO user) throws PrivilegeException {
		if(user==null){
			throw new PrivilegeException("请传入要创建的用户");
		}
		if(super.hasPermission(null, null, CommomPermissionHelper.NORMAL_CHECK, null, null)){
			List list = (List) queryByName(user.getLoginname(), true);
			if (!list.isEmpty()){
				throw new PrivilegeException("该登录名已存在，请换一个登录名");
			}
			SmtUser smtUser = new SmtUser();
			SmtUser ownerUser = null;
			SmtGroup ownerGroup = null;
			BeanUtil.propertyCopy(user, smtUser);
			String ownerId = smtUser.getOwnerId();
			if(smtUser.getStatus()== null){
				smtUser.setStatus("1");
			}
			if (ownerId == null) {// 是否指定持有者
				smtUser.setOwnerId(super.getPrivilegeManager().getCueLoginUser().getId());// 默认为当前用户
			} else {
				boolean userboolean =  ((ownerUser = (SmtUser) get(SmtUser.class, ownerId)) == null || ownerUser.getStatus().equals("0"));
				boolean groupboolean = ((ownerGroup = (SmtGroup) get(SmtGroup.class, ownerId))==null|| ownerGroup.getStatus().equals("0"));
				if(userboolean&&groupboolean){
					throw new PrivilegeException("指定的持有者无效");
				}
			}
			String password = MD5.MD5(user.getPasswd());
			smtUser.setPasswd(password);
			CommonQueryBS.systemOut("password"+password);
			smtUser.setCreatedate(new java.sql.Date(System.currentTimeMillis()));
			super.save(smtUser);
			user.setId(smtUser.getId());
			
			PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
			privilegeLog.log("创建用户"+smtUser.getLoginname(), "调用IUserControl接口的saveUser方法", null);
			return true;
		}
		return false;
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            需更新的用户
	 * @throws PrivilegeException 可能抛出的异常：用户在对自己进行非法操作，用户不存在，请传入要更新的用户
	 * 
	 * @return    true 对此用户的更新操作成功，false 对此用户的更新操作失败 
	 * 
	 */
	public boolean updateUser(UserVO user) throws PrivilegeException {
		if(user==null){
			throw new PrivilegeException("请传入要更新的用户");
		}
		if(!super.getPrivilegeManager().getIPermission().isSuperManager(super.getPrivilegeManager().getCueLoginUser())){
			if (super.getPrivilegeManager().getCueLoginUser().getId().equals(
				user.getId())) {
			throw new PrivilegeException("用户在对自己进行非法操作");
			}
		}
		SmtUser SoldUser = (SmtUser) super.get(SmtUser.class, user.getId());
		UserVO UoldUser = new UserVO();
		BeanUtil.propertyCopy(SoldUser, UoldUser);
		if (SoldUser == null) {
			throw new PrivilegeException("该用户不存在");
		}
		//更新用户时，暂不作任何权限的判断  modify by lizs  20161013  经李伟宁和马金鑫确认修改
//		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_EDIT,
//				CommomPermissionHelper.OWNER_CHECK, SoldUser.getOwnerId(), null)) {
//			throw new PrivilegeException("无操作权限,请联系管理员");
//		}
		SmtUser smtUser = new SmtUser();
		BeanUtil.propertyCopy(user, smtUser);
/*		if(!validateHashCode(UoldUser)){
			throw new PrivilegeException("哈希值校验不正确");
		}*/
		if (SoldUser.getOwnerId()!=null && !SoldUser.getOwnerId().equals(user.getOwnerId())) {// 判断持有者是否发生改变
			SmtUser newOwner = (SmtUser) super.get(SmtUser.class, user.getOwnerId());
			if (newOwner == null) {
				throw new PrivilegeException("更改的新持有者不存在");
			} else if (newOwner.getStatus().equals("0")) {
				throw new PrivilegeException("更改的新持有者无效");
			}
		}
		if (!SoldUser.getStatus().equals(user.getStatus())) {	//判断状态是否发生改变
			if(SoldUser.getStatus().equals("1")){
				if(someUserParamsValidata(SoldUser)){
					boolean sg = this.revokeUser(user.getId());
				}	
			}
		}
		if(!SoldUser.getLoginname().equals(user.getLoginname())){
			if(!hasSameLoginNameExsit(user.getLoginname())){
				throw new PrivilegeException("该登录名名已经存在");
			}
		}
		getPrivilegeManager().getHbSession().clear();
		super.update(smtUser);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("更新用户", "调用IUserControl接口的updateUser方法", null);
		return true;	
	}

	/**
	 * 根据传入的登录名查找用户，分模糊匹配和精确匹配
	 * 
	 * @param name
	 *            登录名
	 * @param isEqual
	 *            是模糊匹配还是精确匹配
	 * @return  list 返回根据登录名查找到的用户
	 * 
	 */
	public List<Object> queryByName(String name, boolean isEqual)
			throws PrivilegeException {
		HashMap<String, String> params = new HashMap<String, String>();
		StringBuffer hql = new StringBuffer("from SmtUser where loginname");
		if (isEqual) {
			params.put("name", name);
			hql.append("=:name");
		} else {
			hql.append(" like '%"+name+"%'");
		}
		String sql = hql.toString();
		return PrivilegeUtil.getVOList(query(sql, params), UserVO.class);
	}

	/**
	 * 查看用户是不是拥有可分受权限的角色
	 * 
	 * @param userId
	 *            用户Id
	 * @return boolean 如果用户拥有可分受权限的角色返回 true，否则返回false
	 * 
	 */
	public boolean hasPrivilegeRole(String userId) {
		Session session = super.getPrivilegeManager().getHbSession();
		String hql = "from SmtAct sa where sa.objectid=:userId and dispatchauth='1'";
		Query query = session.createQuery(hql).setParameter("userId", userId);
		List list = query.list();
		if (list.size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param obj  传入的Object
	 * @return  true表示通过hashCode的验证，false表示没有通过hashCode的验证
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		UserVO userVO = (UserVO) obj;
		return super.validateHashCode(userVO.getId(), userVO.getHashcode(),
				SmtUser.class);
	}

	/**
	 * 重新启用用户
	 * 
	 * @param userId
	 *            重新启用的用户的Id
	 * @return boolean 如果重新启用成功返回true，否则返回false
	 * @throws PrivilegeException  用户的状态已经为有效
	 */
	public boolean reuseUser(String userId) throws PrivilegeException {
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if(smtUser==null){
			throw new PrivilegeException("该用户不存在");
		}
		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_REUSE,
				CommomPermissionHelper.OWNER_CHECK, smtUser.getOwnerId(),null)) {
			throw new PrivilegeException("无操作权限,请联系管理员");
		}
		if (smtUser.getStatus().equals("1")) {
			throw new PrivilegeException("该用户的状态已经为有效");
		}
		smtUser.setStatus("1");
		super.update(smtUser);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("重新启用用户"+smtUser.getLoginname(), "调用IUserControl接口的reuseUser方法", null);
		return true;
	}

	/*
	 * 公共验证抽出来的类
	 * 
	 * @param userId
	 *            用户的ID
	 * @param onlyExsitOrEffective
	 *            是不是只对存在和有效进行判断，true表示是，false表示还要进行其他的判断
	 * @rturn boolean true表示验证通过，false表示验证失败
	 * @throws PrivilegeException 可能抛出的异常有：用户状态为无效,用户存在可分受权限的角色，用户是组管理员，用户持有其他资源，用户不存在
	 */
	private boolean userParamsValidata(String userId,
			boolean onlyExsitOrEffective) throws PrivilegeException {
		boolean reslut = true;
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, userId);
		if (smtUser != null) {
			if (onlyExsitOrEffective == false) {
				if (hasPrivilegeRole(smtUser.getId())) {
					throw new PrivilegeException("该用户存在可分受权限的角色");
				} else if (GroupHelper.isGroupLeader(super
						.getPrivilegeManager(), smtUser.getId())) {
					throw new PrivilegeException("该用户是组管理员");
				} else if (this.isOwnEntityPermission(smtUser.getId())) {
					throw new PrivilegeException("该用户持有其他资源");
				}
			}
			reslut = true;
		}
		if(smtUser==null){
			reslut= false;
			throw new PrivilegeException("该用户不存在");
		}
		return reslut;
	}

	/*
	 * 对用户的一些信息进行验证主要是（用户是否存在可分受权限的角色，是否是组管理员，是否持有其他资源）。
	 * 
	 * @param smtUser 要进行验证的用户
	 * @return boolean	验证成功返回true，否则返回false
	 * @throws PrivilegeException 用户存在可分受权限的角色，用户是组管理员，用户持有其他资源
	 */
	private boolean someUserParamsValidata(SmtUser smtUser)
			throws PrivilegeException {
		if (hasPrivilegeRole(smtUser.getId())) {
			throw new PrivilegeException("用户存在可分受权限的角色");
		} else if (GroupHelper.isGroupLeader(super.getPrivilegeManager(),
				smtUser.getId())) {
			throw new PrivilegeException("用户是组管理员");
		} else if (this.isOwnEntityPermission(smtUser.getId())) {
			throw new PrivilegeException("用户持有其他资源");
		}
		return true;
	}

	/*
	 * 判断用户名是否已经存在
	 * 
	 * @param userId
	 * @return true 表示用户名不存在
	 * @throws PrivilegeException  表示用户名已经存在
	 *             
	 */
	private boolean hasSameLoginNameExsit(String loginName)
			throws PrivilegeException {
		String hql = "from SmtUser su where su.loginname=:loginName";
		Session session = super.getPrivilegeManager().getHbSession();
		List list = session.createQuery(hql).setString("loginName", loginName)
				.list();
		if (list.size() != 0) {
			throw new PrivilegeException("该登录名已经存在，请换一个登录名");
		}
		return true;
	}

	/**
	 *  用户修改自己密码的方法
	 *  @param  userId			要更改密码的用户的ID
	 *  @param  oldPassword		用户的旧密码
	 *  @param  newPassword		用户的新密码
	 *  @return  boolean  true 表示更改密码成功 false表示更改密码失败
	 *  @throws  PrivilegeException   用户不存在或者用户无效
	 */
	public boolean updatePassword(String userId, String oldPassword,
			String newPassword) throws PrivilegeException {
		UserVO cueUser = super.getPrivilegeManager().getCueLoginUser();
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if (UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),
				userId) == UserHelper.USER_EXSIT_AND_EFFECTIVE) {
			if (cueUser.getId().equals(smtUser.getId())) {
				if (smtUser.getPasswd().equals(oldPassword)) {
					smtUser.setPasswd(MD5.MD5(newPassword));
					getPrivilegeManager().getHbSession().clear();
					super.update(smtUser);
					PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
					privilegeLog.log("用户"+smtUser.getLoginname()+"修改密码", "调用IUserControl接口的updatePassword方法", null);
					return true;
				}
				return false;
			}
		}
		throw new PrivilegeException("用户不存在或存在的用户无效");
	}
	
	
	/**
	 *  用户修改自己密码的方法
	 *  @param  userId			要更改密码的用户的ID
	 *  @param  oldPassword		用户的旧密码
	 *  @param  newPassword		用户的新密码
	 *  @return  boolean  true 表示更改密码成功 false表示更改密码失败
	 *  @throws  PrivilegeException   用户不存在或者用户无效
	 */
	public boolean updatePassword_uc(String userId, String oldPassword,
			String newPassword) throws PrivilegeException {
		UserVO cueUser = super.getPrivilegeManager().getCueLoginUser();
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if (UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),
				userId) == UserHelper.USER_EXSIT_AND_EFFECTIVE) {
			if (cueUser.getId().equals(smtUser.getId())) {
				if (smtUser.getPasswd().equals(oldPassword)) {
					smtUser.setPasswd(newPassword);
					getPrivilegeManager().getHbSession().clear();
					super.update(smtUser);
					PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
					privilegeLog.log("用户"+smtUser.getLoginname()+"修改密码", "调用IUserControl接口的updatePassword方法", null);
					return true;
				}
				return false;
			}
		}
		throw new PrivilegeException("用户不存在或存在的用户无效");
	}
	
	
	
	/**
	 * 根据用户的ID查找用户
	 * @param principalId
	 * @return
	 * @throws PrivilegeException 
	 */
	public UserVO findUserByUserId(String userID) throws PrivilegeException{
		UserVO userVO = new UserVO();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, userID);
		BeanUtil.propertyCopy(smtUser, userVO);
		return userVO;
	}
	
	/**
	 * 查看用户所持有的资源
	 * @param principalId
	 * @return true 表示持有该资源 false表示不持有该资源
	 */
	public boolean isOwnEntityPermission(String principalId){
		Session session = super.getPrivilegeManager().getHbSession();
		String hql = "from SmtUser su where su.ownerId=:principalId";
		Query query = session.createQuery(hql).setString("principalId", principalId);
		List list = query.list();
		if(list.size()!=0){
			return true;
		}
		String hql2 = "from SmtGroup sg where sg.ownerId=:principalId";
		Query query2 = session.createQuery(hql2).setString("principalId", principalId);
		List list2 = query2.list();
		if(list.size()!=0){
			return true;
		}
		String hql3 = "from SmtRole sr where sr.owner=:principalId";
		Query query3 = session.createQuery(hql3).setString("principalId", principalId);//
		List list3 = query3.list();
		if(list.size()!=0){
			return true;
		}
		String hql4 = "from SmtScene ss where ss.owner=:principalId";
		Query query4 = session.createQuery(hql4).setString("principalId", principalId);
		List list4 = query4.list();
		if(list.size()!=0){
			return true;
		}
		String hql5 = "from SmtResource sr where sr.owner=:principalId";
		Query query5 = session.createQuery(hql5).setString("principalId", principalId);
		List list5 = query5.list();
		if(list.size()!=0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 为分页查询提供的查询方法:根据当前登录用户查询可见的所有用户
	 * @param cueUser  用户对象
	 * @param start  起始位置
	 * @param limit  分页时每页显示数
	 * @param params  HashMap,查询RoleVO的限制条件
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
		if (hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// 检测是否是超级管理员或可见所有的资源级权限
			hql.append("select count(*) from SmtUser su where su.status='1' and 1=1 ");
		} else {
			hql
					.append("select count(*) from SmtUser su where su.status='1' and (su.ownerId='"
							+ cueUser.getId()
							+ "' or "
							+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
							+ " (select sg.id from SmtGroup sg where sg.ownerId='"
							+ cueUser.getId()
							+ "') or sug.groupid in"
							+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
							+ cueUser.getId() + "')))");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				if("querysql".equals(key)){
					continue;
				}else{
					hql.append(" and su." + key + " like '%" + params.get(key)
							+ "%'");
				}
			}
		}

		Query query = sess.createQuery(hql.toString());
		List list = query.list();
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
	 * 查询当前登录的用户的可见的用户：根据start和limit的值查询某一页的值;当limit为-1时,表示查询登录者所有可见组.
	 * @param cueUser  当前登录的用户
	 * @param start  起始数
	 * @param limit  限制数
	 * @return  返回当前用户的可见用户列表list
	 */
	public List queryByUser(UserVO cueUser, int start, int limit, HashMap params)
			throws PrivilegeException {
		Session sess = super.getPrivilegeManager().getHbSession();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, cueUser.getId());
		List list = new ArrayList();
		boolean querysql =false;
		if (super.hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null,null)) {
				StringBuffer hql3 = new StringBuffer("from SmtUser su where su.status='1' and 1=1 ");		
				if (params != null) {
					Set<String> keys = params.keySet();
					Iterator<String> it = keys.iterator();
					while (it.hasNext()) {
						String key = it.next();
						if("querysql".equals(key)){
							querysql=true;
							continue;
						}else{
							hql3.append(" and su." + key + " like '%" + params.get(key)
									+ "%'");
							}
						}
					}
				if(querysql){
					hql3 = addSql(params,hql3);
				}
				Query q3 = sess.createQuery(hql3.toString());
				if (limit != -1) {
					q3.setFirstResult(start);
					q3.setMaxResults(limit);
				}
				return PrivilegeUtil.getVOList(q3.list(), UserVO.class);
		 }
		 
		StringBuffer sql = new StringBuffer("select su from SmtUser su where su.status='1' and (su.ownerId='"
				+ cueUser.getId()
				+ "' or "
				+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
				+ " (select sg.id from SmtGroup sg where sg.ownerId='"
				+ cueUser.getId()
				+ "') or sug.groupid in"
				+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
				+ cueUser.getId() + "')))");
		if (params != null) {
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if("querysql".equals(key)){
						querysql=true;
						continue;
					}else{
						sql.append("and  su." + key + " like '%" + params.get(key)+ "%' ");
					}
				}
		 }	
		if(querysql){
			sql = addSql(params,sql);
		}
		Query query = sess.createQuery(sql.toString());
		if (limit != -1) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}	
		return PrivilegeUtil.getVOList(query.list(),UserVO.class);
	}
	
	public StringBuffer addSql(HashMap params,StringBuffer sql){
		List querylist = (List)params.get("querysql");
		for(int i=0;i<querylist.size();i++){
			if(i==0){
				sql.append(" order by su."+querylist.get(i).toString()+"");
			}else{
				sql.append(",su."+querylist.get(i).toString()+"");
			}
		}
		return sql;
	}
	
}