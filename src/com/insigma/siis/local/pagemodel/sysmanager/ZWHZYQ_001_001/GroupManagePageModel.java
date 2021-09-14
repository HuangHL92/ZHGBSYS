package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001;




import org.hibernate.Query;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.util.HashCodeUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GroupManagePageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static int flag = 0;
	public static int flag2 = 0;
	
	public GroupManagePageModel(){

		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			
			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "true");
			}else{
				area =  SysOrgBS.queryInit();
				areaInfo.put("manager", "false");
			}
			if(area!=null ) { 
				if(area[2].equals("1")){
					area[2]="picOrg";
				}else if(area[2].equals("2")){
					area[2]="picInnerOrg";
				}else{
					area[2]="picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
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
/*		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
			if(group == null){
				throw new RadowException("查询错误");
			}
			//this.getPageElement("searchGroupBtn").setValue(group.getName());
			this.getPageElement("optionGroup").setValue(group.getName());
			this.getExecuteSG().addExecuteCode("window.reloadThisGroup()");
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}*/
		this.getPageElement("checkedgroupid").setValue(id);
		if(id.equals("X001")){
			this.getExecuteSG().addExecuteCode("document.getElementById('groupname').innerHTML='无管理单位用户'");
			this.getPageElement("b0121").setValue("");
			this.getPageElement("qType").setValue("1");
			this.setNextEventName("memberGrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<Object[]> name = HBUtil.getHBSession().createSQLQuery("select b0101,b0121 from b01 where b0111='"+id+"'").list();
		for (Object[] objects : name) {
			this.getExecuteSG().addExecuteCode("document.getElementById('groupname').innerHTML='"+objects[0].toString()+"'");
			this.getPageElement("optionGroup").setValue(objects[0].toString());
			this.getPageElement("b0121").setValue(objects[1].toString());
		}
		
		/*this.getPageElement("searchGroupBtn").setValue(" ");*/
		this.getPageElement("qType").setValue("1");
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
		String groupid = this.getPageElement("checkedgroupid").getValue();//获取机构id
		StringBuffer str = new StringBuffer();
		//判断查询类型
		String type = this.getPageElement("qType").getValue();
		if(type.equals("2")){
			
			//检查当前用户是否为system，如果为system，则可以查询所有用户
			String sql = "smtuser0_.otherinfo like '"+user.getOtherinfo()+"%' or";
			
			if(user.getId() != null && user.getId().equals("40288103556cc97701556d629135000f")){
				sql = "1=1 or";
			}
			
			
			//获取页面查询区域信息
			String loginname = this.getPageElement("searchUserBtn").getValue();//用户登录名
			String name = this.getPageElement("searchUserNameBtn").getValue(); //用户名
			String useful = this.getPageElement("useful").getValue();          //用户状态
			String usertype = this.getPageElement("searchUserTypeBtn").getValue();      //用户类型
			str.append("select smtuser0_.userid,smtuser0_.loginname,smtuser0_.useful,"
					+ "smtuser0_.isleader,smtuser0_.username,smtuser0_.owner,smtuser0_.usertype,smtuser0_.otherinfo,smtuser0_.createdate,"
					+ "(select b0101 from b01 b where b.b0111=smtuser0_.otherinfo) b0101,"
					+ "(select b0114 from b01 b where b.b0111=smtuser0_.otherinfo) b0114 from smt_user smtuser0_ where ("+sql+" smtuser0_.otherinfo='X001')");
			//更改查询方式：所有用户都在同一个用户组中，查询该机构下用户。
			
				if(!loginname.equals("")){
					str.append(" AND smtuser0_.loginname like '%"+loginname+"%'");
				}
				if(!name.equals("")){
					str.append(" AND smtuser0_.username like '%"+name+"%'");
				}
				if(!useful.equals("")){
					str.append(" AND smtuser0_.useful='"+useful+"'");
				}
				if(!usertype.equals("")){
					str.append(" AND smtuser0_.usertype='"+usertype+"'");
				}
				
			
		}else{
			//机构查询
			str.append("select smtuser0_.userid,smtuser0_.loginname,smtuser0_.useful,"
					+ "smtuser0_.isleader,smtuser0_.username,smtuser0_.owner,smtuser0_.usertype,smtuser0_.otherinfo,smtuser0_.createdate,"
					+ "(select b0101 from b01 b where b.b0111=smtuser0_.otherinfo) b0101,"
					+ "(select b0114 from b01 b where b.b0111=smtuser0_.otherinfo) b0114 from smt_user smtuser0_ where smtuser0_.otherinfo = '"+groupid+"'");
		}
		
		this.flag=0;		
		//增加条件限制：删除的用户不字页面中显示 add by lizs 20161019
		str.append(" and  smtuser0_.loginname !='admin' and  smtuser0_.loginname !='checker'");
		
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
		String pereaid= (String)(new GroupManagePageModel().areaInfo.get("pareaid"));
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
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		ResultSet res;
		try {
			res = HBUtil.getHBSession().connection().prepareStatement("select groupid from smt_usergroupref where userid='"+userid+"'").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.equals(usergroupid)&&isLeader()){
					this.setMainMessage("您好，您没有该用户组的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.openWindow("createGroupWin", "pages.sysmanager.ZWHZYQ_001_001.GroupAddWindow");
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
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		ResultSet res;
		try {
			res = HBUtil.getHBSession().connection().prepareStatement("select groupid from smt_usergroupref where userid='"+userid+"'").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.equals(usergroupid)&&isLeader()){
					this.setMainMessage("您好，您没有该用户组的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.openWindow("modifyGroupWin", "pages.sysmanager.ZWHZYQ_001_001.GroupModifyWindow");
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
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		ResultSet res;
		try {
			res = HBUtil.getHBSession().connection().prepareStatement("select groupid from smt_usergroupref where userid='"+userid+"'").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.equals(usergroupid)&&isLeader()){
					this.setMainMessage("您好，您没有该用户组的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
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
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		ResultSet res;
		try {
//			res = HBUtil.getHBSession().connection().prepareStatement("select groupid from smt_usergroupref where userid='"+userid+"'").executeQuery();
			res = HBUtil.getHBSession().connection().prepareStatement("select * from competence_userdept t where t.userid = '"+userid+"' and t.b0111='"+groupid+"' and t.type='1' ").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.equals(usergroupid)&&isLeader()){
					this.setMainMessage("您好，您没有该机构的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
//			this.openWindow("createUserWin", "pages.sysmanager.ZWHZYQ_001_001.UserAddWindow");
			this.getExecuteSG().addExecuteCode("$h.openWin('createUserWin','pages.sysmanager.ZWHZYQ_001_001.UserAddWindow&groupid="+groupid+"','创建成员用户窗口',1000,550,'"+groupid+"',ctxPath,null,{maximized:false});");
//			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
//			this.getExecuteSG().addExecuteCode("addTab('新建用户','',ctxPath+'/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.UserAddWindow',false,false);");			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("createAdminBtn.onclick")
	public int createAdmin() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		ResultSet res;
		try {
			res = HBUtil.getHBSession().connection().prepareStatement("select * from competence_userdept t where t.userid = '"+userid+"' and t.b0111='"+groupid+"' and t.type='1' ").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.equals(usergroupid)&&isLeader()){
					this.setMainMessage("您好，您没有该机构的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String usertype = this.getPageElement("CurUserType").getValue();
		if(!usertype.equals("1")){
			this.setMainMessage("您好，您非系统管理员用户，没有创建用户的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else{
			this.getExecuteSG().addExecuteCode("$h.openWin('createAdminWin','pages.sysmanager.ZWHZYQ_001_001.AdminAddWindow','创建管理员用户窗口',550,300,'"+groupid+"',ctxPath,null,{maximized:false});");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("modifyUserBtn.onclick")
	
	public int modifyUser() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		ResultSet res;
		try {
			res = HBUtil.getHBSession().connection().prepareStatement("select groupid from smt_usergroupref where userid='"+userid+"'").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.equals(usergroupid)&&isLeader()){
					this.setMainMessage("您好，您没有该用户组的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int choosed = 0;
		try {
			choosed = choosePersonR("memberGrid",false);
		} catch (AppException e) {
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else if(choosed>1){
			this.setMainMessage("一次只能修改一个用户信息");
		}else{
			this.openWindow("modifyUserWin", "pages.sysmanager.ZWHZYQ_001_001.UserModifyWindow");
			this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 *  移除用户记录，由原来的删除数据库记录修改为仅将用户的有效状态字段置为无效----马金鑫提
	 * @return
	 * @throws RadowException
	 * @throws PrivilegeException 
	 */
	@PageEvent("removeUserBtn.onclick")
	public int removeUser(String id) throws RadowException, PrivilegeException{
//		String groupid = this.getPageElement("checkedgroupid").getValue();
		String groupid= id.split(",")[1];
		String opid = id.split(",")[0]; //被操作的人的id
		UserVO opuser = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(opid);
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String userid = user.getId();
		String getuserids = choosePersonIds("memberGrid");
		ResultSet res;
		try {
			
//			res = HBUtil.getHBSession().connection().prepareStatement("select groupid from smt_usergroupref where userid='"+userid+"'").executeQuery();
			res = HBUtil.getHBSession().connection().prepareStatement("select otherinfo from smt_user where userid='"+userid+"'").executeQuery();
			while(res.next()){
				String usergroupid = res.getString(1);
				if(!groupid.contains(usergroupid+".") && !"".equals(groupid) && !opuser.getOwnerId().equals(userid) && !userid.equals("40288103556cc97701556d629135000f")){
					this.setMainMessage("您好，您没有该组织的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断要删除的用户是否存在system，system是超级管理员，不能删除
//		boolean isSystem = checkLoginname("memberGrid");
		if(opid.equals("40288103556cc97701556d629135000f")){
			this.setMainMessage("system为超级管理员，不能删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		int count = choosePerson("memberGrid",false);
/*		int count = 0;
		try {
			count = choosePersonR("memberGrid",false);
		} catch (AppException e) {
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(count == 0){
			this.setMainMessage("请选择需要移除的成员");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String[] userids = getuserids.split(",");
		for(int i=0;i<userids.length;i++){
			if(userids[i].equals(userid)){
				this.setMainMessage("您好，您不能删除自己使用的用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
//		if(count >1){
//			//this.setMainMessage("确定要移除这"+count+"名成员嘛？");
//			this.setMainMessage("用户信息为重要信息，暂不允许做批量删除操作！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.addNextEvent(NextEventValue.YES, "doRemoveUser",id);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
/*		if(count != 0){
			this.setMainMessage("确定要移除该成员嘛？");
		}*/
		this.setMainMessage("确定移除该用户？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doRemoveUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doRemoveUser(String getuserids) throws RadowException, PrivilegeException{
//		String groupid = this.getPageElement("checkedgroupid").getValue();
		String groupid="330700";
//		String getuserids = choosePersonIds("memberGrid");
		String[] userids = getuserids.split(",");
		HBSession sess = HBUtil.getHBSession();
		String returnMessage = "";
		StringBuffer users = new StringBuffer();
		try {
			for(int i=0;i<userids.length;i++){
				/*
				List list_0 = sess.createSQLQuery("select su.* from smt_user su,smt_act sa where su.userid = sa.objectid and sa.dispatchauth = '1' and su.userid ='"+userids[i]+"'").list();
				if(list_0.size()>0){
					returnMessage = "该用户有可分授权限的角色，不能删除。如要删除，请先取消用户的角色授权！";
					continue;
				}
				boolean result = PrivilegeManager.getInstance().getIGroupControl().removeUserFromGroup(groupid, userids[i]);
				if(result){
					this.setMainMessage("从组移除用户成功");
					List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
					if(list == null || list.isEmpty()){//解决成员被移除光的时候不能刷新
						this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
					}else{
						this.createPageElement("memberGrid", ElementType.GRID, false).reload();
					}
				}
				doDeleteUser(userids[i]);*/
				users.append("'");
				users.append(userids[i]);
				users.append("',");
			}
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			String sDate = sf.format(date);
			users.deleteCharAt(users.length()-1);
			//删除角色 
//			sess.createSQLQuery("delete from smt_role where roleid in(select roleid from smt_act where (objectid in("+users.toString()+") or userid in("+users.toString()+")))").executeUpdate();
			sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid in("+users.toString()+")").executeUpdate();
			sess.createSQLQuery("delete from smt_act where (objectid in("+users.toString()+") or userid in("+users.toString()+"))").executeUpdate();
			sess.createSQLQuery("delete from smt_usergroupref where userid in("+users.toString()+")").executeUpdate();

			sess.createSQLQuery("delete from competence_userdept where userid in("+users.toString()+")").executeUpdate();
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
			this.getPageElement("qType").setValue("2");
			this.flag=1;
			this.setNextEventName("memberGrid.dogridquery");
			this.getPageElement("checkedgroupid").setValue("");
			this.getExecuteSG().addExecuteCode("document.getElementById('groupname').innerHTML='';");
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
	/**
	 * 获取选择数据判断是否为当前所有者建立用户，不为则不能进行操作
	 * @param grid
	 * @param isRowNum
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	private int choosePersonR(String grid, boolean isRowNum) throws RadowException, AppException{
		int result = 0;
		int number = 0;
		PageElement pe = this.getPageElement(grid);
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String cueUserName = user.getLoginname();
		String isleader = user.getIsleader();
		//Object obj = PrivilegeManager.getInstance().getCueLoginUser();
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			Object owner =  map.get("owner");
			if(logchecked.equals(true)){
				//不是管理员 或者被操作的是管理员不能修改
				if(cueUserName.equals(map.get("loginname")) || !"1".equals(isleader) || (map.get("usertype").equals("1") && !"system".equals(cueUserName) && !"admin".equals(cueUserName))){
					throw new AppException("您好，你不具备操作用户 "+ map.get("username")+" 的权限");
				}
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
	
	/**
	 * 用于弹出信息项组设置页面 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("setInfoGroupBtn.onclick")
	public int setInfoGroupClick() throws RadowException {
		
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		String area = (String) sess.createSQLQuery("SELECT b.infogroupname FROM AA01 a,COMPETENCE_INFOGROUP b WHERE a.AAA001='AREA_ID' and a.AAA005=b.infogroupid").uniqueResult();
		if(area==null){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			InfoGroup ig = new InfoGroup();
			ig.setCreateuserid("admin");
			ig.setInfogroupname("信息项组");
			String area1 = (String) sess.createSQLQuery("SELECT a.AAA005 FROM AA01 a WHERE a.AAA001='AREA_ID'").uniqueResult();
			ig.setInfogroupid(area1);
			sess.save(ig);
			ts.commit();
		}
		
		this.openWindow("setInfoGroupWin", "pages.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindow");
		this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@Override
	public int doInit() throws RadowException {
		this.flag2=0;
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		this.getPageElement("CurUserid").setValue(user.getId());
		this.getPageElement("CurLoginname").setValue(user.getLoginname());
		this.getPageElement("CurUserType").setValue(user.getUsertype());
		this.getPageElement("Leader").setValue(isleader);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("resetUserPsw.onclick")
	public int resetUserPsw(String id) throws RadowException{
/*		String groupid = this.getPageElement("checkedgroupid").getValue();
		int choosed = 0;
		try {
			choosed = choosePersonR("memberGrid",false);
		} catch (AppException e) {
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();*/
		String isleader = PrivilegeManager.getInstance().getCueLoginUser().getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(choosed==0){
//			this.setMainMessage("请选择需要操作的用户");
//		}else{
			this.addNextEvent(NextEventValue.YES, "reset",id);
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
			this.setMainMessage("您确定要重置改用户的登录密码么？");
//		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("reset")
	public int reset(String users) throws RadowException{
//		String users = choosePersonIds("memberGrid");
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
//	@PageEvent("reset")
//	public int reset(String userid) throws RadowException{
//		this.addNextEvent(NextEventValue.YES, "doReset",userid);
//		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
//		this.setMessageType(EventMessageType.CONFIRM); //消息框类型(confirm类型窗口)
//		this.setMainMessage("确定要重置该成员密码？");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
//	
//
//	@PageEvent("doReset")
//	@com.insigma.odin.framework.radow.annotation.Transaction
//	public int realreset(String id) throws RadowException{
//		HBSession sess = HBUtil.getHBSession();
//		SmtUser smtUser  = (SmtUser) sess.load(SmtUser.class, id);
//		String passwd=GlobalNames.sysConfig.get("RESET_PASSWD");
//		//smtUser.setPasswd(MD5.MD5(passwd));
//
//		
//		
//		
//		
//
//		UserVO user = new UserVO();
//		user.setOwnerId(smtUser.getOwnerId());
//		user.setLoginname(smtUser.getLoginname());
//		user.setId(smtUser.getId());
//		user.setStatus(smtUser.getStatus());
//		user.setDesc(smtUser.getDesc());
//		user.setName(smtUser.getName());
//		user.setPasswd(MD5.MD5(passwd));
//
//		try {
//			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
//		} catch (PrivilegeException e) {
//			this.isShowMsg=true;
//			this.setMainMessage(e.getMessage());
//			return EventRtnType.FAILD;
//		}
//		this.setMainMessage("重置密码成功,密码为："+passwd);
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
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
	    this.openWindow("groupSortWin", "pages.sysmanager.ZWHZYQ_001_001.GroupSort");
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
	 * 用户记录单击显示授权信息
	 * 1.机构授权信息（包含下级）
	 * 2.功能模块授权——角色
	 */	
	@PageEvent("")
	@GridDataRange
	public int getUserAuth() throws RadowException{
		String sUserID = this.getPageElement("memberGrid").getValue("userid",this.getPageElement("memberGrid").getCueRowIndex()).toString();
		this.getPageElement("idForOrgQuery").setValue(sUserID);
		this.setNextEventName("orgGrid.dogridquery");
//		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgGrid.dogridquery")
	public int orgQuery(int start,int limit) throws RadowException{
		String userId = this.getPageElement("idForOrgQuery").getValue();
		String sql = "select t.userdeptid,t.type, (select b0101 from b01 b where t.b0111 = b.b0111) b0111 from competence_userdept t where t.userid = '"+userId+"'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 根据id删除机构授权一条记录
	 */
	@PageEvent("deleteOrgById")
	public int deleteOrgById(String id) throws RadowException{
		String sql ="delete from competence_userdept t where t.userdeptid='"+id+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		this.setNextEventName("orgGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 增加一条机构授权
	 */
	@PageEvent("addOrgById")
	public int addOrgById(String id) throws RadowException{
//		String sql ="insert into competence_userdept t where t.userdeptid='"+id+"'";
		UserDept dept = new UserDept();
		String userId = this.getPageElement("idForOrgQuery").getValue();
		dept.setUserid(userId);
		dept.setB0111(id);
		dept.setType("1");//先默认可以维护，以后再改
//		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		HBSession sess = HBUtil.getHBSession();
		sess.save(dept);
		sess.flush();
		this.setNextEventName("orgGrid.dogridquery");
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
		String groupid = this.getPageElement("memberGrid").getValue("otherinfo",this.getPageElement("memberGrid").getCueRowIndex()).toString();
		this.setRadow_parent_data(sUserID);
//		this.openWindow("UserDetailWin", "pages.sysmanager.sysuser.UserDetail");
//		this.getExecuteSG().addExecuteCode("	doOpenPupWin('/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&userid='"+sUserID+",'信息项权限组控制窗口',600,255,null);");
		this.getExecuteSG().addExecuteCode("$h.openWin('UserDetailWin', 'pages.sysmanager.sysuser.UserDetail&groupid="+groupid+"&userid="+sUserID+"', '用户详情', 1000, 550, '"+sUserID+"', '"+request.getContextPath()+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public boolean checkLoginname(String gridName) throws RadowException{
		
		boolean bReturn = false;
		PageElement pe = this.getPageElement(gridName);
		List<HashMap<String, Object>> list = pe.getValueList();
		String userIds = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String loginname = (String) this.getPageElement(gridName).getValue("loginname", i);
				if("system".equals(loginname)){
					bReturn = true;
					break;
				}
				else{
					bReturn=false;
				}
			}
		}
		return bReturn;
	}
	
	@PageEvent("fuzzy")
	public int fuzzySearch() throws RadowException{
		JSONObject json = new JSONObject();	
		JSONObject rows = new JSONObject();	
		JSONArray array = new JSONArray();
		JSONObject result = new JSONObject();	
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String b0101 = this.getParameter("b0101");
		int start = Integer.parseInt(this.getParameter("start"));
		int limit = Integer.parseInt(this.getParameter("limit"));
		String sql = "select b.b0101,b.b0194,b.b0111,b.b0121,b.b0114 from B01 b,UserDept c where  c.userid='"+user.getId()+"'  and b.b0111 = c.b0111 and b.b0101 like '%"+b0101+"%'";
		Query query = HBUtil.getHBSession().createQuery(sql);
		query.setFirstResult(start);
		query.setMaxResults(limit);
//		Iterator<B01> it = query.iterate();
//		this.setSelfDefResData("{'results' : 1,'rows': [{ 'b0101': 2, 'b0114': '于一山', 'b0194': '女' }]}");
		List<Object[]> list = query.list();
		for (Object[] objects : list) {
			String b0194 = objects[1].toString();
			if(null != b0194){
				if("1".equals(b0194)){
					b0194 = "法人单位";
				}else if("2".equals(b0194)){
					b0194 = "内设机构";
				}
				else if("3".equals(b0194)){
					b0194 = "机构分组";
				}
			}
				json.put("b0101", null == objects[0] ? "" : objects[0].toString());
				json.put("b0194", b0194);
				json.put("b0111", null == objects[2] ? "" : objects[2].toString());
				json.put("b0121", null == objects[3] ? "" : objects[3].toString());
				json.put("b0114", null == objects[4] ? "" : objects[4].toString());
				rows.put("rows", json);			
				array.add(json);
				rows.element("rows", array);
		}
/*		while(it.hasNext()){
			B01 b01 = it.next();
			String b0194 = b01.getB0194();
			if(null != b0194){
				if("1".equals(b0194)){
					b0194 = "法人单位";
				}else if("2".equals(b0194)){
					b0194 = "内设机构";
				}
				else if("3".equals(b0194)){
					b0194 = "机构分组";
				}
			}
			json.put("b0101", b01.getB0101());
			json.put("b0114", b01.getB0114());
			json.put("b0194", b0194);
			json.put("b0111", b01.getB0111());
			json.put("b0121", b01.getB0121());
			rows.put("rows", json);			
			array.add(json);
			rows.element("rows", array);
			
		}*/
		Object count = HBUtil.getHBSession().createSQLQuery("select count(1) from b01 b,competence_userdept c where  c.userid='"+user.getId()+"'  and b.b0111 = c.b0111 and b.b0101 like '%"+b0101+"%'").uniqueResult();
		result.put("results", count.toString()+"");
		result.putAll(rows);
		this.setSelfDefResData(result);
		return EventRtnType.XML_SUCCESS;
	}
	
}
