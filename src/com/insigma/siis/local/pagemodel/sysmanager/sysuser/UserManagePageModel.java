package com.insigma.siis.local.pagemodel.sysmanager.sysuser;




import org.hibernate.Query;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUsergroupref;
import com.insigma.odin.framework.privilege.entity.SmtUserselfcolumn;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.util.HashCodeUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class UserManagePageModel extends PageModel {
	
	/**
	 * 系统区域信息  
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static int flag = 0;
	public static int flag2 = 0;
	
	public UserManagePageModel(){
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
			}else{
				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.name,a.AAA005 FROM AA01 a,SMT_GROUP b WHERE a.AAA001='AREA_ID' and a.AAA005=b.groupid").uniqueResult();
				if(area==null){
					String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
					areaInfo.put("areaname", areaname);
					areaInfo.put("areaid", "G001");
				}else{
					areaInfo.put("areaname", area[0]);
					areaInfo.put("areaid", area[1]);
				}
			}
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				areaInfo.put("manager", "true");
			}else{
				areaInfo.put("manager", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}
	/**
	 * 点击用户组的树查询用户信息
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
			if(group == null){
				throw new RadowException("查询错误");
			}
			//this.getPageElement("searchGroupBtn").setValue(group.getName());
			this.getPageElement("optionGroup").setValue(group.getName());
			this.getExecuteSG().addExecuteCode("window.reloadThisGroup()");
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		/*this.getPageElement("searchGroupBtn").setValue(" ");*/
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  查询用户列表信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String groupid = this.getPageElement("checkedgroupid").getValue();//获取用户组编号
		//获取页面查询区域信息
		String loginname = this.getPageElement("searchUserBtn").getValue();//用户名
		String name = this.getPageElement("searchUserNameBtn").getValue(); //用户登录名
		String useful = this.getPageElement("useful").getValue();          //用户状态
		String isleader = this.getPageElement("isleader").getValue();      //是否为管理员
		StringBuffer str = new StringBuffer();
		//登陆用户为system时，可以看到所有用户组的用户
		if("system".equals(user.getLoginname()) || "admin".equals(user.getLoginname())){
			str.append("select smtuser0_.userid," +
					"smtuser0_.loginname," +
					"smtuser0_.dept," +
					"smtuser0_.description," +
					"smtuser0_.iplist," +
					"smtuser0_.checkip," +
					"smtuser0_.useful," +
					"smtuser0_.isleader," +
					"smtuser0_.username," +
					"smtuser0_.owner," +
					"smtuser0_.rate," +
					"smtuser0_.empid," +
					"smtuser0_.macaddr," +
					"smtuser0_.usertype," +
					"smtuser0_.otherinfo," +
					"smtuser0_.createdate," +
					"smtuser0_.work," +
					"smtuser0_.mobile," +
					"smtuser0_.tel," +
					"smtuser0_.email," +
					"smtuser0_.hashcode " +
					" from smt_user smtuser0_ " +
					" where smtuser0_.userid IN(SELECT t.userid FROM Smt_Usergroupref t WHERE t.groupid='"+groupid+"')");
		}else{
			HBSession sess = HBUtil.getHBSession();
			List<SmtUsergroupref> su = sess.createQuery("from SmtUsergroupref where userid = '"+user.getId()+"'").list();
			if(su.size()>0){
				if(groupid.equals(su.get(0).getGroupid())){//当登陆用户不为system时，判断查看的用户组是否为用户所在的用户组
					
					str.append("select smtuser0_.userid," +
							"smtuser0_.loginname," +
							"smtuser0_.dept," +
							"smtuser0_.description," +
							"smtuser0_.iplist," +
							"smtuser0_.checkip," +
							"smtuser0_.useful," +
							"smtuser0_.isleader," +
							"smtuser0_.username," +
							"smtuser0_.owner," +
							"smtuser0_.rate," +
							"smtuser0_.empid," +
							"smtuser0_.macaddr," +
							"smtuser0_.usertype," +
							"smtuser0_.otherinfo," +
							"smtuser0_.createdate," +
							"smtuser0_.work," +
							"smtuser0_.mobile," +
							"smtuser0_.tel," +
							"smtuser0_.email," +
							"smtuser0_.hashcode " +
							" from smt_user smtuser0_ " +
							" where smtuser0_.userid IN(SELECT t.userid FROM Smt_Usergroupref t WHERE t.groupid='"+groupid+"') ");
				}else{//当登陆用户不为system时，用户只能看到自己所在用户的下级用户组的用户信息
					str.append("select smtuser0_.userid," +
							"smtuser0_.loginname," +
							"smtuser0_.dept," +
							"smtuser0_.description," +
							"smtuser0_.iplist," +
							"smtuser0_.checkip," +
							"smtuser0_.useful," +
							"smtuser0_.isleader," +
							"smtuser0_.username," +
							"smtuser0_.owner," +
							"smtuser0_.rate," +
							"smtuser0_.empid," +
							"smtuser0_.macaddr," +
							"smtuser0_.usertype," +
							"smtuser0_.otherinfo," +
							"smtuser0_.createdate," +
							"smtuser0_.work," +
							"smtuser0_.mobile," +
							"smtuser0_.tel," +
							"smtuser0_.email," +
							"smtuser0_.hashcode " +
							" from smt_user smtuser0_ " +
							" where smtuser0_.userid IN(SELECT t.userid FROM Smt_Usergroupref t WHERE t.groupid='"+groupid+"'" +
							" and exists (select 1 from smt_group s1, smt_usergroupref su where s1.parentid = su.groupid"+
							" and s1.groupid = t.groupid and su.userid = '"+user.getId()+"'))");
				}
			}
			
		}
		if(this.flag==1){
			if(!loginname.equals("")){
				str.append(" AND smtuser0_.loginname like '%"+loginname+"%'");
			}
			if(!name.equals("")){
				str.append(" AND smtuser0_.username like '%"+name+"%'");
			}
			if(!useful.equals("")){
				str.append(" AND smtuser0_.useful='"+useful+"'");
			}
			if(!isleader.equals("")){
				str.append(" AND smtuser0_.isleader='"+isleader+"'");
			}
			this.flag=0;
		}
		//过滤删除的用户记录:useful=2是删除的用户 add by lizs 20161018
		str.append(" and smtuser0_.useful != '2' ");
		
		if(DBType.ORACLE==DBUtil.getDBType()){
			str.append(" order by to_number(smtuser0_.sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			str.append(" order by (smtuser0_.sortid+0)");
		}
		String hql = str.toString();
		
		this.pageQuery(hql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 生成机构树
	 * @return
	 * @throws PrivilegeException
	 */
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		List<GroupVO> list  = new ArrayList<GroupVO>();
		String node = this.getParameter("node");
		String pereaid= (String)(new UserManagePageModel().areaInfo.get("pareaid"));
		list = PrivilegeManager.getInstance()
					.getIGroupControl().findByParentId(node);
		
		//只显示所在的组织及下级组织 不在组织中 则显示全部
		List<GroupVO> choose = new ArrayList<GroupVO>();
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
		for(int i=0;i<groups.size();i++){
			for(int j=0;j<groups.size();j++){
				if(groups.get(j).getId().equals(groups.get(i).getParentid())){
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if(!groups.isEmpty()){
			for(int i = 0;i<list.size();i++){
				for(int j = 0;j<groups.size();j++){
					if(groups.get(j).getId().equals(list.get(i).getId())){
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if(equel){
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (GroupVO group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"leaf\":"+hasChildren(group.getId())+",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"leaf\":"+hasChildren(group.getId())+",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"leaf\":"+hasChildren(group.getId())+",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"leaf\":"+hasChildren(group.getId())+",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	//查询是否有下级节点  false没有 true有
		public static String hasChildren(String id){
			String sql="from SmtGroup b where b.parentid='"+id+"'";// -1其它现职人员
			List<SmtGroup> list = HBUtil.getHBSession().createQuery(sql).list();
			if(list!=null && list.size()>0){
				return "false";
			}else{
				return "true";
			}
		}
	/**
	 * 用于弹出用户组新建窗口
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("createGroupBtn.onclick")
	public int createGroupClick() throws RadowException {
		//操作权限判断，仅系统管理员可以作新建、修改、删除等操作
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(isLeader()){
//			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.openWindow("createGroupWin", "pages.sysmanager.sysuser.GroupAddWindow");
			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 用于修改用户组新建窗口
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("modifyGroupBtn.onclick")
	public int modifyGroupClick() throws RadowException {
		//操作权限判断，仅系统管理员可以作新建、修改、删除等操作
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(isLeader()){
//			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.openWindow("modifyGroupWin", "pages.sysmanager.sysuser.GroupModifyWindow");
			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 用于删除用户组
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deleteGroupBtn.onclick")
	public int deleteGroupClick() throws RadowException {
		//操作权限判断，仅系统管理员可以作新建、修改、删除等操作
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(isLeader()){
//			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(groupid.equals("")){
			this.setMainMessage("请选择需要删除的用户组");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doDeleteGroup",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行删除操作吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doDeleteGroup")
	
	public int doDeleteGroup(String groupid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtGroup S where S.id = '"+groupid+"'";
		SmtGroup group =(SmtGroup) sess.createQuery(hql).list().get(0);
		try {
			boolean state = PrivilegeManager.getInstance().getIGroupControl().deleteGroup(groupid);
			if(state){
				try {
					new LogUtil().createLog("63", "SMT_GROUP",group.getId(), group.getName(), "", new ArrayList());
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.setMainMessage("删除用户组成功");
				this.reloadPage();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("删除失败："+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("createUserBtn.onclick")
	
	public int createUser() throws RadowException{
		//操作权限判断，仅系统管理员可以作新建、修改、删除等操作
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(isLeader()){
//			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.openWindow("createUserWin", "pages.sysmanager.sysuser.UserAddWindow");
			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("modifyUserBtn.onclick")
	
	public int modifyUser() throws RadowException{
		
		//操作权限判断，仅系统管理员可以作新建、修改、删除等操作
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(isLeader()){
//			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		int choosed = choosePerson("memberGrid",false);
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else if(choosed>1){
			this.setMainMessage("一次只能修改一个用户信息");
		}else{
			this.openWindow("modifyUserWin", "pages.sysmanager.sysuser.UserModifyWindow");
			this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("removeUserBtn.onclick")
	public int removeUser() throws RadowException{
		//操作权限判断，仅系统管理员可以作新建、修改、删除等操作
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
//		if(isLeader()){
//			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		int count = choosePerson("memberGrid",false);
		if(count == 0){
			this.setMainMessage("请选择需要移除的成员");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(count >1){
//			//this.setMainMessage("确定要移除这"+count+"名成员嘛？");
//			this.setMainMessage("用户信息为重要信息，暂不允许做批量删除操作！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.addNextEvent(NextEventValue.YES, "doRemoveUser");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		if(count != 0){
			this.setMainMessage("确定要移除该成员嘛？");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doRemoveUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doRemoveUser() throws RadowException, PrivilegeException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String getuserids = choosePersonIds("memberGrid");
		String[] userids = getuserids.split(",");
		HBSession sess = HBUtil.getHBSession();
		String returnMessage = "";
		StringBuffer users = new StringBuffer();
		try {
			for(int i=0;i<userids.length;i++){
//				List list_0 = sess.createSQLQuery("select su.* from smt_user su,smt_act sa where su.userid = sa.objectid and sa.dispatchauth = '1' and su.userid ='"+userids[i]+"'").list();
//				if(list_0.size()>0){
//					returnMessage = "该用户有可分授权限的角色，不能删除。如要删除，请先取消用户的角色授权！";
//					continue;
//				}
				//boolean result = PrivilegeManager.getInstance().getIGroupControl().removeUserFromGroup(groupid, userids[i]);
				//修改删除用户模式，由原来的删除数据库记录修改为将用户的有效状态置为无效
//				if(result){
//					this.setMainMessage("从组移除用户成功");
//					List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
//					if(list == null || list.isEmpty()){//解决成员被移除光的时候不能刷新
//						this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
//					}else{
//						this.createPageElement("memberGrid", ElementType.GRID, false).reload();
//					}
//				}
				//doDeleteUser(userids[i]);
				users.append("'");
				users.append(userids[i]);
				users.append("',");
			}
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			String sDate = sf.format(date);
			users.deleteCharAt(users.length()-1);
			sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid in("+users.toString()+")").executeUpdate();
			sess.createSQLQuery("delete from smt_act where (objectid in("+users.toString()+") or userid in("+users.toString()+"))").executeUpdate();
			List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
			if(list == null || list.isEmpty()){//解决成员被移除光的时候不能刷新
				this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
			}else{
				this.createPageElement("memberGrid", ElementType.GRID, false).reload();
			}
			this.setMainMessage("用户删除成功！");
			
		} catch (Exception e) {
			returnMessage = e.getMessage();
			this.setMainMessage(returnMessage);
			System.out.println("***从组移除用户失败："+returnMessage);
		}
		if(!"".equals(returnMessage)){
			this.setMainMessage(returnMessage);
			throw new PrivilegeException(returnMessage);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doDeleteUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doDeleteUser(String userid) throws RadowException, PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user1 =(SmtUser) sess.createQuery(hql).list().get(0);
			UserVO user = new UserVO();
			user.setId(userid);
			PrivilegeManager.getInstance().getIUserControl().deleteUser(user);
			try {
				new LogUtil().createLog("66", "SMT_USER",user1.getId(), user1.getLoginname(), "", new Map2Temp().getLogInfo(user1,new SmtUser()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.createPageElement("memberGrid", ElementType.GRID, false).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("findUserBtn.onclick")
	public int findUserBtn() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.flag=1;
			this.setNextEventName("memberGrid.dogridquery");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private int choosePerson(String grid, boolean isRowNum) throws RadowException{
		int result = 0;
		int number = 0;
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				number = i;
				result++;
			}
		}
		if(isRowNum){
			return number;//选中的第几个
		}
		return result;//选中用户个数
	}
	
	private String choosePersonIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String userIds = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String userid = (String) this.getPageElement(grid).getValue("userid", i);
				if(userIds.equals("")){
					userIds += userid;
				}
				else{
					userIds += ","+userid;
				}
			}
		}
		return userIds;
	}
	public boolean isLeader(){
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		return "0".equals(isleader);	
	}
	
	@Override
	public int doInit() throws RadowException {
		this.flag2=0;
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		this.getPageElement("CurLoginname").setValue(user.getLoginname());
		this.getPageElement("CurUserType").setValue(user.getUsertype());
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("resetUserPsw.onclick")
	public int resetUserPsw() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		int choosed = choosePerson("memberGrid",false);
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String isleader = PrivilegeManager.getInstance().getCueLoginUser().getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else{
			this.addNextEvent(NextEventValue.YES, "reset");
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
			this.setMainMessage("您确定要重置这"+choosed+"个人员的登录密码么？");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("reset")
	public int reset() throws RadowException{
		String users = choosePersonIds("memberGrid");
		String[] userids = users.split(",");
		for(int i=0;i<userids.length;i++){
			UserVO user = new UserVO();
			try {
				user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userids[i]);
			} catch (PrivilegeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String newPwd = user.getLoginname();
			String newPassword = MD5.MD5(newPwd);
			user.setPasswd(newPassword);
			try {
				PrivilegeManager.getInstance().getIUserControl().updateUser(user);
			} catch (PrivilegeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List list = new ArrayList();
		try {
			new LogUtil().createLog("68", "SMT_USER","","", "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("重置密码成功，重置后的密码和用户登录名相同。");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 重置页面查询条件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("resetQuery.onclick")
	public int resetQuery() throws RadowException{
		
		this.getPageElement("searchUserNameBtn").setValue("");
		this.getPageElement("searchUserBtn").setValue("");
		this.getPageElement("useful").setValue("");
		this.getPageElement("isleader").setValue("");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//排序按钮
	@PageEvent("groupSort.onclick")
	public int sortSysOrg() throws RadowException {
	    this.openWindow("groupSortWin", "pages.sysmanager.sysuser.GroupSort");
		this.request.getSession().setAttribute("transferType", "groupSort");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("usersort")
	@NoRequiredValidate
	public int usersort(String pageInfo)throws RadowException{
		String[] pfs = pageInfo.split(",");
		Long pn = Long.parseLong(pfs[1]);
		Long pSize = Long.parseLong(pfs[0]);
		
		List<HashMap<String,String>> list = this.getPageElement("memberGrid").getStringValueList();
		try {
			Long i = 1L;
			if(pn>1){
				i = pSize*pn;
			}
			
			HBSession sess = HBUtil.getHBSession();
			for(HashMap<String,String> m : list){
				String userid = m.get("userid");
				//UserVO user= PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
				SmtUser user = (SmtUser) sess.createQuery("from SmtUser where userid = '"+userid+"'").list().get(0);
				user.setSortid(i);
				HashCodeUtil.getBeanHashCode(user);
				sess.update(user);
				sess.flush();
				//PrivilegeManager.getInstance().getIUserControl().updateUser(user);
				//HBUtil.executeUpdate("update smt_user set sortid='"+String.valueOf(i)+"' where userid='"+userid+"'");
				i++;
			}
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 用户记录双击打开用户详情页面
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("memberGrid.rowdbclick")
	@GridDataRange
	public int getUserDetail() throws RadowException{
		String sUserID = this.getPageElement("memberGrid").getValue("userid",this.getPageElement("memberGrid").getCueRowIndex()).toString();
		this.setRadow_parent_data(sUserID);
		this.openWindow("UserDetailWin", "pages.sysmanager.sysuser.UserDetail");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
