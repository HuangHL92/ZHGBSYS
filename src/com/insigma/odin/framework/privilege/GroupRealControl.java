package com.insigma.odin.framework.privilege;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUsergroupref;
import com.insigma.odin.framework.privilege.helper.CommomPermissionHelper;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.helper.UserHelper;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.IPermission;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.util.HQuery;


/**
 * 用户组处理类
 * @author 黄定吉
 * @created 18-九月-2009 11:48:07
 */
public class GroupRealControl extends AbstractPrivilegeBase  implements IGroupControl{
	
	public GroupRealControl(){
		
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	
	
	/**
	 * 校验该用户所在组与传入的组是否在同一个辖区内 辖区级以上的不参与校验
	 * @param groupId	需要增加用户的组的ID
	 * @param userIds	多个用户ID中间用逗号隔开
	 * @throws PrivilegeException 
	 */
	public void checkGroup(String groupId, String userIds) throws PrivilegeException{
		GroupVO newvo=findXQById(groupId);//新增用户组的辖区
		String newrate=newvo.getRate();
		
		if(newrate!=null&&!newrate.equals("")){//级别不为空的校验  空的不校验

			if(Long.parseLong(newrate)>=2){//新增的用户组是区级或者区级以下 则校验  否则不校验
				String[] ids = userIds.split(",");
				for(int i = 0;i<ids.length;i++){
					String userid=ids[i];
					List<GroupVO> list=findGroupByUserId(userid);
					Boolean boo=false;//false 表示人员新加入的组和以前的组不在同一个辖区   true  表示人员新加入的组和以前的组在同一个辖区
					if(list.size()==0){
						boo=true;
					}
					for(int j = 0;j<list.size();j++){
						GroupVO vo=list.get(j);
						GroupVO oldvo =findXQById(vo.getId());//查到该用户组属于哪个辖区
						if(newvo.getId().equals(oldvo.getId())){//所在辖区相同 
							boo=true;
							break;
							//throw new PrivilegeException("请不要跨辖区增加用户组");
						}
					}
					
					if(boo==false){
						throw new PrivilegeException("请不要跨辖区增加用户");
					}
					
				}
			}
			
		}
		
		
	}
	
	/**
	 * 查到该用户组属于哪个辖区
	 * @param groupId	传入的用户组id
	 * @throws PrivilegeException 
	 */
	public GroupVO findXQById(String groupId) throws PrivilegeException{
		GroupVO vo =findById(groupId);
		String rate =vo.getRate();
		if(rate!=null&&!rate.equals("")){
			if(Long.parseLong(rate)>3){//区级以下
				return findXQById_extends(vo.getParentid());
			}else if(Long.parseLong(rate)==3){//区级
				return vo;
			}else{//区级以上
				//throw new PrivilegeException("该人员所在群组为辖区以上级别,名称为"+vo.getName()+",id："+vo.getId());
				return vo;
			}
		}
		return vo;
	}
	
	
	/**
	 * 根据用户组ID查询用户组信息
	 * @param id 用户组ID
	 * @return GroupVO
	 * @throws PrivilegeException
	 */
	private GroupVO findXQById_extends(String id) throws PrivilegeException{
//		List list = getPrivilegeManager().getHbSession().createQuery("from SmtGroup where id=:id").setString("id", id).list();
//		if(list == null || list.isEmpty()){
//			throw new PrivilegeException("未找到群组，id为："+id);
//		}
//		
//		SmtGroup smtGroup = (SmtGroup)list.get(0);
//		return findXQById(smtGroup.getId());
		GroupVO vo =findById(id);
		if(vo.getRate().equals("3")){
			return vo;
		}else{
			return findXQById(id);
		}
		
	}
	
	

	/**
	 * 增加用户到组。增加多个用户时,按照所传入的顺序进行增加,当某个用户出现增加异常时便停止增加.需要通过事务来控制其共同成功或失败.
	 * @param groupId	需要增加用户的组的ID
	 * @param userIds	多个用户ID中间用逗号隔开
	 * @return boolean	true表示添加成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean addUserToGroup(String groupId, String userIds) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_USEFUL,null);//判断用户组存在性和有效性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.MANAGER_CHECK, getGroupOwnerId(groupId), groupId);
		if(permission == false)//权限判断
			throw new PrivilegeException("无操作权限");
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			getUserUseful(ids[i],"用户");//判断用户存在有效性
			if(!(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i])==GroupHelper.UN_GROUP_MEMGBER))
				throw new PrivilegeException("该用户已为该组成员");
			try {
				SmtUsergroupref ug = new SmtUsergroupref();
				ug.setGroupid(groupId);
				ug.setUserid(ids[i]);
				ug.setIsleader("0");
				super.save(ug);  
				temp++;
			} catch (PrivilegeException e) {
				throw new PrivilegeException("用户组添加成员失败",e);
			}
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("在用户与用户组关系表中插入"+temp+"条数据", "调用IGroupControl接口的addUserToGroup方法向指定组增加成员", null);
		return true;
	}

	/**
	 * 取消组管理员资格。在取消多个人员时，按照传入的顺序进行取消.当某个用户出现取消异常时则停止取消,需要通过事务来控制其共同成功或失败.
	 * @param groupId	需取消管理员的组的ID
	 * @param userIds	被取消管理员的人员ID，多个用户ID用逗号隔开
	 * @return boolean true表示取消成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean cancelGroupManager(String groupId, String userIds) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_USEFUL,null);//判断用户组存在性和有效性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, getGroupOwnerId(groupId), null);
		if(permission == false)
			throw new PrivilegeException("无操作权限");//权限判断
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			getUserUseful(ids[i],"管理员");//判断需取消管理员的人是否存在有效
			//判断需取消管理员的人员是否已经取消管理员身份
			if(!(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.GROUP_LEADER))
				throw new PrivilegeException("该人员非该用户组管理员,不能取消其管理员身份...");
			try {
//				session.createQuery("update SmtUsergroupref set ISLEADER = 0 where GROUPID=:groupId and USERID=:userId")
//					   .setString("groupId", groupId)
//					   .setString("userId", ids[i])
//					   .executeUpdate();
				String	hql = "from SmtUsergroupref sug where sug.groupid=:groupId and sug.userid=:userId";
				Session session = getPrivilegeManager().getHbSession();
				List<?> list = session.createQuery(hql).setString("groupId", groupId)
										.setString("userId", ids[i]).list();
				if(list.size()!=0){
					SmtUsergroupref sug = (SmtUsergroupref) list.get(0);
					sug.setIsleader("0");
					session.saveOrUpdate(sug);
					session.flush();
				}	
				temp++;
			} catch (HibernateException e) {
				throw new PrivilegeException("取消管理员失败",e);
			}
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("取消"+temp+"名成员的管理员身份", "调用IGroupControl接口的cancelGroupManager方法", null);
		return true;
	}

	/**
	 * 删除组。不支持同时删除多个用户组操作
	 * @param groupId 需被删除的组的ID
	 * @return boolean true表示删除成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean deleteGroup(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		//GroupUseful(smtGroup,GroupHelper.GROUP_EXIST,null);//判断组存在性
		if(smtGroup == null) throw new PrivilegeException("所需删除的用户组不存在");//判断组存在性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_DELETE, CommomPermissionHelper.OWNER_CHECK, getGroupOwnerId(groupId), null);
		if(permission == false)//权限判断
			throw new PrivilegeException("无操作权限");
		isContainResources(groupId,"删除");//判断该用户组是否有成员或资源
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("groupId", groupId);
		List<?> list = super.query("from SmtAct where OBJECTID=:groupId",params);
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				SmtAct act = (SmtAct)list.get(i);
				super.delete(act);
			}
		}
		super.delete(smtGroup);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("删除用户组"+smtGroup.getName(), "调用IGroupControl接口的deleteGroup方法", null);
		return true;
	}

	/**
	 * 查询当前登录用户可见的所有用户组:根据start和limit的值查询某一页的值;当start或limit为-1时,表示查询登录者所有可见组.
	 * @param cueUser  UserVO类:当前用户信息
	 * @param start  查询从哪条开始
	 * @param limit  查询多少条 
	 * @return 存放GroupVO的List用户组列表
	 */
	public List<Object> queryByUser(UserVO cueUser, int start, int limit,HashMap params) throws PrivilegeException{
		String hql = "from SmtGroup sg";
		String hql_n = " from SmtGroup sg where id in" +
					" (select groupid from SmtUsergroupref where userid ='"+cueUser.getId()+"')" +
					" or sg.ownerId = '"+cueUser.getId()+"'";
		if(params != null){
			if(params.get("groupname") != null){
				hql += " where name = '"+params.get("groupname")+"'";
				hql_n += " and name = '"+params.get("groupname")+"'";
			}else if(params.get("groupnames") != null){
				hql += " where name like '%"+params.get("groupnames")+"%' ";
				hql_n += " and name like '%"+params.get("groupnames")+"%' ";
			}else{
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					hql += " where sg." + key + " like '%" + params.get(key) + "%'";
					hql_n += " and sg." + key + " like '%" + params.get(key) + "%'";
				}
			}
		}
		Session session = getPrivilegeManager().getHbSession();
		List list = new ArrayList();
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL,CommomPermissionHelper.NORMAL_CHECK,null, null);
		if(permission == true){//如果是超级管理员和持有数据级的用户，则查询所有用户组
			if(limit == -1 || start == -1){
				list = session.createQuery(hql).list();
			}else{
				Query q = session.createQuery(hql);
				q.setFirstResult(start); 
				q.setMaxResults(limit); 
				list = q.list();
			}
		}else{//其他用户
			if(limit == -1 || start == -1){
				list = session.createQuery(hql_n).list();
			}else{
				Query q = session.createQuery(hql_n);
				q.setFirstResult(start); 
				q.setMaxResults(limit);
				list = q.list();
			}
		}
		return PrivilegeUtil.getVOList(list,GroupVO.class);
	}

	/**
	 * 分页查询。根据当前登录用户查询可见的所有用户组
	 * @param cueUser  用户对象
	 * @param start  起始位置
	 * @param limit  分页时每页显示数
	 * @param params  HashMap集合:查询RoleVO的限制条件
	 * @return PageQueryData 返回分页查询数据对象
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		PageQueryData pd = getPageQueryDataByUser(cueUser, start, limit, params);
		List groups = queryByUser(cueUser, start, limit, params);
		try {
			pd.setData(HQuery.fromList(groups));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * 分页查询包含父类组名称。根据当前登录用户查询可见的所有用户组,其中GroupVO中的parentid字段为父类组的名称
	 * @param cueUser  用户对象
	 * @param start  起始位置
	 * @param limit  分页时每页显示数
	 * @param params  HashMap集合:查询RoleVO的限制条件
	 * @return PageQueryData 返回分页查询数据对象
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryContantParentNameByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		PageQueryData pd = getPageQueryDataByUser(cueUser, start, limit, params);
		List groups = queryByUser(cueUser, start, limit, params);
		for(int i=0;i<groups.size();i++){
			GroupVO group = (GroupVO)groups.get(i);
			GroupVO parent = this.findById(group.getParentid());
			if(parent != null){
				group.setParentid(parent.getName());
			}
		}
		try {
			pd.setData(HQuery.fromList(groups));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * 分页查询包含持有者登录名。根据当前登录用户查询可见的所有用户组,其中GroupVO的ownerid字段为持有者的登录名
	 * @param cueUser  用户对象
	 * @param start  起始位置
	 * @param limit  分页时每页显示数
	 * @param params  HashMap集合:查询RoleVO的限制条件
	 * @return PageQueryData 返回分页查询数据对象
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryContantOwnerNameByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		PageQueryData pd = getPageQueryDataByUser(cueUser, start, limit, params);
		List groups = queryByUser(cueUser, start, limit, params);
		for(int i=0;i<groups.size();i++){
			GroupVO group = (GroupVO)groups.get(i);
			UserVO owner = this.getPrivilegeManager().getIUserControl().findUserByUserId(group.getOwnerId());
			if(owner == null){
				GroupVO groupOwner = findById(group.getOwnerId());
				group.setOwnerId(groupOwner.getName());
			}else{
				group.setOwnerId(owner.getLoginname());
			}
		}
		try {
			pd.setData(HQuery.fromList(groups));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * 从用户组移除用户。移除多个用户时根据传入的顺序进行移除,当某个用户移除异常时停止移除.需要通过事务来控制其共同成功或失败.
	 * @param groupId	需移除用户的组的ID
	 * @param userIds	被移除的用户ID,多个用户ID中间用逗号隔开
	 * @return boolean  true表示移除成功;失败则抛相应异常信息
	 * @throws PrivilegeException
	 */
	public boolean removeUserFromGroup(String groupId, String userIds) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		GroupUseful(smtGroup,GroupHelper.GROUP_USEFUL,null);//判断组存在性和有效性
		UserVO user = super.getPrivilegeManager().getCueLoginUser();
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, smtGroup.getOwnerId(), null);
		if(permission == false){//判断是否是超级管理员，数据级管理权限用户或持有者
			//如果是管理员
			if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,user.getId()) == GroupHelper.GROUP_LEADER){
				String[] ids = userIds.split(",");
				for(int i = 0;i<ids.length;i++){
					if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.GROUP_LEADER)
						throw new PrivilegeException("该人员为组管理员,您无权删除");
					doRemoveUserFromGroup(groupId,ids[i]);
				}
				return true;
			}
			else{
				throw new PrivilegeException("无操作权限");
			}
		}
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			doRemoveUserFromGroup(groupId,ids[i]);
			temp++;
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("在用户与用户组关系表中删除"+temp+"条数据", "调用IGroupControl接口的removeUserFromGroup方法", null);
		return true;
	}
	
	/**
	 * 注销组。不支持同时注销多个用户组
	 * @param groupId	需被注销的组的ID
	 * @return boolean true表示注销成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean revokeGroup(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		GroupUseful(smtGroup,GroupHelper.GROUP_USEFUL,null);//判断组存在性和有效性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_REVOKE, CommomPermissionHelper.OWNER_CHECK, smtGroup.getOwnerId(), null);
		if(permission == false)//权限判断
			throw new PrivilegeException("无操作权限");
		isContainResources(groupId,"注销");//判断该用户组是否有成员或资源
		try {
			smtGroup.setStatus(Integer.toString(GroupHelper.GROUP_UN_USEFUL));
			super.update(smtGroup);
		} catch (PrivilegeException e) {
			throw new PrivilegeException("注销用户组失败",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("注销组"+smtGroup.getName(), "调用IGroupControl接口的revokeGroup方法", null);
		return true;
	}

	/**
	 * 设置组有效。不支持同时设置多个用户组有效
	 * @param groupId	需设为有效的组的ID
	 * @return boolean true表示设置成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean reuseGroup(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		GroupUseful(smtGroup,GroupHelper.GROUP_UN_USEFUL,null);//判断组存在性和无效性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, smtGroup.getOwnerId(), null);
		if(permission == false)//权限判断
			throw new PrivilegeException("无操作权限");
		try {
			smtGroup.setStatus(Integer.toString(GroupHelper.GROUP_USEFUL));
			super.update(smtGroup);
		} catch (PrivilegeException e) {
			throw new PrivilegeException("设置用户组有效失败",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("启用组"+smtGroup.getName(), "调用IGroupControl接口的reuseGroup方法", null);
		return true;
	}
	
	/**
	 * 增加组。未指定父类组时,默认为系统用户组;未指定持有者时,默认为创建者
	 * @param group GroupVO类:所要增加的组的信息
	 * @return boolean true表示设置成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean saveGroup(GroupVO group) throws PrivilegeException{
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT,CommomPermissionHelper.NORMAL_CHECK, null, null);
		if(permission == false)//权限判断
			throw new PrivilegeException("无操作权限");
		List<?> groupList = queryByName(group.getName(),true);
		if(!groupList.isEmpty())
			throw new PrivilegeException("该组名已经存在");
		
		if(group.getParentid() == null || group.getParentid().equals("")){
			group.setParentid("G001");
		}else{//判断指定的父类组是否存在有效
			GroupUseful((SmtGroup)super.get(SmtGroup.class, group.getParentid()),GroupHelper.GROUP_USEFUL,"指定的父类组");
		}
		UserVO cueuser = getPrivilegeManager().getCueLoginUser();
		if(group.getOwnerId() == null){//判断是否指定持有者
			group.setOwnerId(cueuser.getId());
		}else{
			getUserUseful(group.getOwnerId(),"用户组指定的持有者");//判断用户组持有者存在和有效性
		}
		SmtGroup smtGroup = new SmtGroup();
		try {
			BeanUtil.propertyCopy(group, smtGroup);
			if(smtGroup.getStatus() == null || smtGroup.getStatus().equals(""))//如果没有设置组的有效性，则默设为有效
				smtGroup.setStatus("1");
			super.save(smtGroup);
		} catch (PrivilegeException e) {
			throw new PrivilegeException("增加用户组失败",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("保存用户组"+smtGroup.getName(), "调用IGroupControl接口的saveGroup方法", null);
		return true;
	}

	/**
	 * 设置组管理员。当设置多个用户为指定组管理员时,根据传入顺序设置,当某个设置管理员出现异常时,则停止设置.需要通过事务来控制其共同成功或失败.
	 * @param groupId	需设置管理员的组的ID
	 * @param userIds   被设置为管理员的人员ID，多个用户ID用逗号隔开
	 * @return boolean true表示设置成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean setGroupManager(String groupId, String userIds) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_USEFUL,null);//判断用户组存在性和有效性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, getGroupOwnerId(groupId), null);
		if(permission == false)//权限判断
			throw new PrivilegeException("无操作权限");
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			getUserUseful(ids[i],"用户");//判断用户存在有效性
			if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.UN_GROUP_MEMGBER){
				throw new PrivilegeException("该人员非该组成员，不能设置为管理员");
			}
			if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.GROUP_LEADER){
				throw new PrivilegeException("需被设置为管理员的人员已经为该组管理员");
			}
			try {
//				session.createQuery("update SmtUsergroupref set ISLEADER = 1 where GROUPID=:groupId and USERID=:userId")
//						.setString("groupId", groupId)
//						.setString("userId", ids[i])
//						.executeUpdate();
				String	hql = "from SmtUsergroupref sug where sug.groupid=:groupId and sug.userid=:userId";
				Session session = getPrivilegeManager().getHbSession();
				List<?> list = session.createQuery(hql).setString("groupId", groupId)
										.setString("userId", ids[i]).list();
				if(list.size()!=0){
					SmtUsergroupref sug = (SmtUsergroupref) list.get(0);
					sug.setIsleader("1");
					session.saveOrUpdate(sug);
					session.flush();
				}	
				temp++;
			} catch (HibernateException e) {
				throw new PrivilegeException("设置用户组管理员失败",e);
			}
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("在用户与用户组关系表中设置"+temp+"名成员为管理员", "调用IGroupControl接口的setGroupManager方法", null);
		return true;
	}

	/**
	 * 更新组
	 * @param group	GroupVO类
	 * @return boolean true表示更新成功;失败则抛相应异常信息
	 * @throws PrivilegeException 
	 */
	public boolean updateGroup(GroupVO group) throws PrivilegeException{
		SmtGroup oldGroup = (SmtGroup)super.get(SmtGroup.class, group.getId());
		GroupUseful(oldGroup,GroupHelper.GROUP_EXIST,null);//判断用户组存在性
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, 
				CommomPermissionHelper.OWNER_CHECK, oldGroup.getOwnerId(), null);
		if(permission == false)
			throw new PrivilegeException("无操作权限");
		if(group.getParentid() != null && !group.getParentid().equals(""))//判断指定的父类组是否存在有效
			GroupUseful((SmtGroup)super.get(SmtGroup.class, group.getParentid()),GroupHelper.GROUP_USEFUL,"指定的父类组");
		if(group.getOwnerId() != null && !(group.getOwnerId().equals(oldGroup.getOwnerId())))//判断用户组持有者存在和有效性
			getUserUseful(group.getOwnerId(),"用户组持有者");
		if(group.getStatus().equals(Integer.toString(GroupHelper.GROUP_UN_USEFUL)))//如果执行注销组的更新，判断是否有成员或持有资源
			isContainResources(group.getId(), "注销组的更新");
		if(group.getStatus().equals(""))
			throw new PrivilegeException("用户组状态不能设置为空");
		SmtGroup smtGroup_new = new SmtGroup();
		try {
			BeanUtil.propertyCopy(group, smtGroup_new);
//			boolean state = validateHashCode(oldGroup.getId(), oldGroup.getHashcode(),SmtGroup.class);
//			if(state) {
			getPrivilegeManager().getHbSession().clear();//清空session
			super.update(smtGroup_new);
//			}
		} catch (PrivilegeException e) {
			throw new PrivilegeException("更新失败",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("更新用户组", "调用IGroupControl接口的updateGroup方法", null);
		return true;
	}

	/**
	 * 根据组名查询组信息:无权限控制,仅为一个查询方法
	 * @param name	需要查询的组的名称
	 * @param isEqual true:采用等式查找；false：采用模糊查找
	 * @return 存放GroupVO的List用户组列表
	 * @throws PrivilegeException
	 */
	public List<Object> queryByName(String name, boolean isEqual)
			throws PrivilegeException {
		List<Object> list = null;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		String hql = "";
		if (isEqual) {
			hql = "from SmtGroup where name=:name";
			list = query(hql, params);
		}else {
			hql = "from SmtGroup where name like '%"+name+"%'";
			list = query(hql, null);
		}
		return PrivilegeUtil.getVOList(list,GroupVO.class);
	}

	/**
	 * 根据用户组ID来查询该用户组所包含的成员。可以传入的model值查询用户组的所有成员、有效成员、无效成员和管理员四类.
	 * @param groupId 需要查询的用户组的ID
	 * @param model 值为GroupHelper中的四个int型常量属性.
	 * 		        当modol值为GROUP_ALL_MENBER时,查询所查组中的所有成员;
	 * 		  				为GROUP_USEFUL_MENBER时,查询所查组中的有效成员;
	 *        				为GROUP_UN_USEFUL_MENBER时,查询所查组中的无效成员;
	 *        				为GROUP_LEADER时,查询所查用户组的管理员.
	 * @return 存放UserVO的List用户列表
	 * @throws PrivilegeException 
	 */
	public List<UserVO> getGroupMember(String groupId,int model) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_EXIST,null);//判断用户组存在性
		String ownerid = getGroupOwnerId(groupId);
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.MANAGER_CHECK, ownerid, groupId);
		if(permission == false){//权限判断
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("groupId", groupId);
			params.put("userId", getPrivilegeManager().getCueLoginUser().getId());
			String hql = "from SmtUsergroupref where groupid=:groupId and userid=:userId";
			List<?> list = super.query(hql, params);
			if(list.isEmpty())//无管理级别以上权限时，是否为该组成员，是的话可查询该用户组，不是则抛出无操作权限异常
				throw new PrivilegeException("无操作权限");
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("groupId", groupId);
		String hql = "select userid from SmtUsergroupref where groupid=:groupId";
		if(model == GroupHelper.GROUP_LEADER){
			hql = "select userid from SmtUsergroupref where groupid=:groupId and isleader='1'";
		}
		List<?> idList = super.query(hql,params);
		if(idList.isEmpty()) return null;
		List<UserVO> list = new ArrayList<UserVO>();
		for(int i=0;i<idList.size();i++){
			String userId = (String)idList.get(i);
			SmtUser smtUser = UserHelper.findUserByUserId(super.getPrivilegeManager(), userId);
			if(smtUser==null) continue;
			UserVO user = new UserVO();
			BeanUtil.propertyCopy(smtUser, user);
			if(model == GroupHelper.GROUP_ALL_MEMBER){
				list.add(user);
			}
			if(model == GroupHelper.GROUP_USEFUL_MEMBER){
				if(user.getStatus().equals(Integer.toString(GroupHelper.GROUP_USEFUL_MEMBER))){
					list.add(user);
				}
			}
			if(model == GroupHelper.GROUP_UN_USEFUL_MEMBER){
				if(user.getStatus().equals(Integer.toString(GroupHelper.GROUP_UN_USEFUL_MEMBER))){
					list.add(user);
				}
			}
			if(model == GroupHelper.GROUP_LEADER){
				list.add(user);
			}
		}
		return list;
	}
	
	/**
	 * 根据用户ID查找其所在的用户组,当用户组有多个时,则返回所有的用户组
	 * @param id  用户ID
	 * @return  存放GroupVO的List用户组集合
	 * @throws PrivilegeException
	 */
	public List findGroupByUserId(String userid) throws PrivilegeException{
		StringBuffer hql =new StringBuffer( "from SmtGroup where id in (select groupid from SmtUsergroupref where userid=:userid)");
		if(DBType.ORACLE==DBUtil.getDBType()){
			hql.append(" order by to_number(sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			hql.append(" order by (sortid+0)");
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("userid", userid);
		return PrivilegeUtil.getVOList(query(hql.toString(), params),GroupVO.class);
	}
	
	/**
	 * 根据用户组的ID查询其子节点的用户组
	 * @param id 用户组ID
	 * @return 子节点用户组集合List
	 * @throws PrivilegeException
	 */
	public List findByParentId(String id) throws PrivilegeException {
		StringBuffer hql =new StringBuffer( "from SmtGroup where parentid=:parentId ");
		if(DBType.ORACLE==DBUtil.getDBType()){
			hql.append(" order by to_number(sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			hql.append(" order by (sortid+0)");
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("parentId", id);
		return PrivilegeUtil.getVOList(query(hql.toString(), params),GroupVO.class);
	}
	
	/**
	 * 根据用户组ID查询用户组信息
	 * @param id 用户组ID
	 * @return GroupVO
	 * @throws PrivilegeException
	 */
	public GroupVO findById(String id) throws PrivilegeException{
		List list = getPrivilegeManager().getHbSession().createQuery("from SmtGroup where id=:id").setString("id", id).list();
		if(list == null || list.isEmpty()){
			return null;
		}
		SmtGroup smtGroup = (SmtGroup)list.get(0);
		GroupVO group = new GroupVO();
		BeanUtil.propertyCopy(smtGroup, group);
		return group;
	}
	
	/**
	 * 查询该用户所属的用户列表
	 * @param userId 用户ID
	 * @return 存放GroupVO的List用户所属的用户列表
	 * @throws PrivilegeException
	 */
	public List<GroupVO> getGroupsByUserId(String userId)
			throws PrivilegeException {
		List list = new ArrayList();
		String hql = "from SmtGroup where id in (select groupid from SmtUsergroupref where userid=:userid)";
		list = getPrivilegeManager().getHbSession().createQuery(hql).setString("userid", userId).list();
		List listvo = PrivilegeUtil.getVOList(list,GroupVO.class);
		return listvo;
	}
	
	/**
	 * hashCode验证
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		GroupVO group = (GroupVO)obj;
		return super.validateHashCode(group.getId(), group.getHashcode(), SmtGroup.class);
	}

	/*
	 * 获取组持有者
	 */
	private String getGroupOwnerId(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		if(smtGroup == null) throw new PrivilegeException("该用户组不存在");
		return smtGroup.getOwnerId();
	}
	
	/*
	 * 根据登陆人员获取包含可见数目的PageQueryData
	 */
	private PageQueryData getPageQueryDataByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException{
		if (cueUser == null) {
			cueUser = getPrivilegeManager().getCueLoginUser();
		}
		Session sess = getPrivilegeManager().getHbSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		if (hasPermission(null, ResourcesPermissionConst.GROUP_ALL, CommomPermissionHelper.NORMAL_CHECK, null, null)) {// 检测是否是超级管理员或可见所有的资源级权限
			hql.append("select count(*) from SmtGroup sg where 1=1 ");
		} else {
			hql.append(" select count(*) from SmtGroup sg where id in" +
					" (select groupid from SmtUsergroupref where userid ='"+cueUser.getId()+"')" +
					" or sg.ownerId = '"+cueUser.getId()+"'");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				hql.append(" and sg." + key + " like '%" + params.get(key) + "%'");
			}
		}
		Query query = sess.createQuery(hql.toString());
		// 取记录总数
		int totalcount = Integer.parseInt(query.list().get(0).toString());
		pd.setTotalCount(totalcount);
		return pd;
	}
	
	/*
	 * 获取用户的存在和有效性
	 */
	private void getUserUseful(String userId,String message) throws PrivilegeException{
		if(UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),userId) == UserHelper.USER_UN_EXSIT){
			throw new PrivilegeException("该"+message+"不存在");
		}
		if(UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),userId) == UserHelper.USER_EXSIT_UN_EFFECTIVE){
			throw new PrivilegeException("该"+message+"无效");
		}
	}
	
	/*
	 * 获取组的存在有效性情况
	 * model值为GROUP_EXIST时，只判断组是否存在
	 * 		  为GROUP_USEFUL时，判断存在性和有效性
	 *        为GROUP_UN_USEFUL时，判断存在性和无效性
	 */
	private void GroupUseful(SmtGroup group,int model,String message) throws PrivilegeException{
		if(message == null) message = "用户组";
		if(model == GroupHelper.GROUP_EXIST){
			if(GroupHelper.isUsefulGroup(group) == GroupHelper.GROUP_UN_EXIST)
				throw new PrivilegeException("该"+message+"不存在");
		}
		if(model == GroupHelper.GROUP_USEFUL){
			if(GroupHelper.isUsefulGroup(group) == GroupHelper.GROUP_UN_USEFUL){
				throw new PrivilegeException("该"+message+"无效");
			}
		}
		if(model == GroupHelper.GROUP_UN_USEFUL){
			if(GroupHelper.isUsefulGroup(group) == GroupHelper.GROUP_USEFUL){
				throw new PrivilegeException("该"+message+"有效");
			}
		}
	}
	
	/*
	 * 判断用户组是否有成员或含有其他资源
	 */
	private void isContainResources(String groupId,String message) throws PrivilegeException{
		List list_users=getGroupMember(groupId,GroupHelper.GROUP_ALL_MEMBER);
		if(list_users != null){
			throw new PrivilegeException("该用户组有"+list_users.size()+"个有效成员存在，不能执行"+message+"操作");
		}//判断该用户组是否有成员
		//判断该用户组是否含有子用户组
		List<?> list = getPrivilegeManager().getHbSession().createQuery("from SmtGroup where parentid=:id").setString("id", groupId).list();
		if(!list.isEmpty()){
			throw new PrivilegeException("该用户组含有子用户组,不能执行"+message+"操作");
		}
		IPermission ipermission = super.getPrivilegeManager().getIPermission();
		boolean temp = ipermission.isOwnEntityPermission(groupId);
		if(temp){
			throw new PrivilegeException("该用户组持有其他资源,不能执行"+message+"操作");
		}//判断该用户组是否含有其他资源
	}
	
	/*
	 * 执行从用户组移除用户操作
	 */
	private void doRemoveUserFromGroup(String groupId,String userId) throws PrivilegeException{
		if(UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),userId) == UserHelper.USER_UN_EXSIT){
			throw new PrivilegeException("该用户不存在");
		}
		if(GroupHelper.isGroupMember(super.getPrivilegeManager(), groupId, userId) == GroupHelper.UN_GROUP_MEMGBER){
			throw new PrivilegeException("该人员已经非该组成员,无需移除");
		}
		getPrivilegeManager().getHbSession().createQuery("delete from SmtUsergroupref where USERID=:userId and GROUPID=:groupId")
				.setString("groupId", groupId)
				.setString("userId", userId)
				.executeUpdate();
	}
	
}