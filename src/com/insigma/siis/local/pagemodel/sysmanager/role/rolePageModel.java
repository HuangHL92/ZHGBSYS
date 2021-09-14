package com.insigma.siis.local.pagemodel.sysmanager.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.IRoleControl;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class rolePageModel extends PageModel {
	private IRoleControl rc = PrivilegeManager.getInstance().getIRoleControl();

	private static String []STATICROLE = {"402881e456498d9601564a2ccde004c0","402881f35e79fa19015e79ff43f1000b",
			"402881f35e79fa19015e79ff85ef000d","402881f35e79fa19015e79ffcc74000f","402881f35e79fa19015e7a000cd70011",
			/*"402881f35e79fa19015e7a004f170013",*/"402881f35e79fa19015e7a00b4190015","402881ff5e7a074c015e7a48748a02f6",
			"402881ff5e7a074c015e7a4cb9cf0482","402881ff5e7a074c015e7a51053304f5","402881ff5e7a074c015e7a53bddd04f7",
			"402881ff5e7a074c015e7a5481300579","402881ff5e7a074c015e7a54d8ae057b","402881ff5e7a074c015e7a5553ab05cc",
			"4028c60c25335f86012533767f3c0002"};
	@Override
	public int doInit() throws RadowException {
/*		HBSession sess = HBUtil.getHBSession();
		List userList=sess.createQuery("from SmtUser where useful='1'").list();
		HashMap<String,String> map = new HashMap<String,String>();
		for(int i=0;i<userList.size();i++){
			SmtUser u = (SmtUser) userList.get(i);
			map.put(u.getId(), u.getLoginname());
		}
		PageElement pe = this.getPageElement("roleOwner");
		if (null != pe){
			((Combo)pe).setValueListForSelect(map);
		}*/
//		this.setNextEventName("grid6.dogridquery");
		this.isShowMsg = false;
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btn_query.onclick")
	public int doQuery() throws RadowException {
		this.setNextEventName("grid6.dogridquery");
		this.isShowMsg = false;
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 *  角色管理页面数据项修改
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvent("grid6.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws RadowException, SQLException {

		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		this.isShowMsg = false;
		//是否有字段值改变判断标志
		boolean isChange = false;
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("grid6");
		String selrole = grid6.getValue("roleid") +"";
		if((selrole.equals("402881e456498d9601564a2ccde004c0") || selrole.equals("4028c60c25335f86012533767f3c0002")
				|| selrole.equals("402881035b6a24af015b6a612d3e004b")) && !"admin".equals(user.getLoginname())){
			this.setMainMessage("该角色为默认角色，不可修改！");
			grid6.reload();
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"admin".equals(user.getLoginname())){
			
			//判断：用户不能修改自己的授权
			HBSession sess = HBUtil.getHBSession();
			String user_id = user.getId();//获取用户编号
			String hql = "from SmtAct where objectid = '"+user_id+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for(int i=0;i<smtact_list.size();i++){
				String roleid = smtact_list.get(i).getRoleid();//获取用户的
				if(grid6.getValue("roleid").equals(roleid)){
					this.setMainMessage("用户不能对自己的角色进行修改操作！");
					grid6.reload();
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		
		RoleVO role = null;
		try {
			role = rc.getById((String) grid6.getValue("roleid"));
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//判断登录用户是否为管理员
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			grid6.reload();
			return EventRtnType.NORMAL_SUCCESS;
		}
		//获取状态的原值
		String status = role.getStatus();
		
		grid6.setValueToObj(role);
		
		//获取页面修改的角色描述的值
		String roledesc = (String) grid6.getValue("roledesc");
		//判断，当页面中的角色描述的值与数据库中的值不同，将页面中的值赋给值对象role
		if(role.getDesc() == null){
			if(!roledesc.equals("")){
				role.setDesc(roledesc);
				isChange = true;
			}
		}else{
			if(roledesc == ""){
				role.setDesc(roledesc);
				isChange = true;
			}else if(!roledesc.equals(role.getDesc())){
				role.setDesc(roledesc);
				isChange = true;
			}
		} 
			
		//判断角色的状态值是否改变
		if(!status.equals(role.getStatus())){
			isChange = true;
		}
		if(isChange){
			//判断修改的角色是否为登录用户自己的角色，用户不能修改自己的角色
			String roleid =role.getId();
			String userid = user.getId();
			String sql="select * from smt_act smtact where smtact.roleid = '"+roleid+"' and smtact.objectid = '"+userid+"'";
			Connection conn1 = HBUtil.getHBSession().connection();
			PreparedStatement ps1 = null;
			ResultSet rs1 = null;
			try {
				ps1 = conn1.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				if(rs1.next()){
					this.setMainMessage("该角色您没有权限修改！");
					grid6.reload();
					return EventRtnType.NORMAL_SUCCESS;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}finally{
				//关闭资源
				if(conn1 != null){
					conn1.close();
				}
				if(ps1 != null){
					ps1.close();
				}
				if(rs1 != null){
					rs1.close();
				}
			}
			//更新角色数据
			try {
				rc.updateRole(role);
			} catch (PrivilegeException e) {
				this.isShowMsg = true;
				this.setMainMessage(e.getMessage());
				grid6.reload();
				e.printStackTrace();
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("角色信息修改成功！");
		}
		
		grid6.reload();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dogriddelete")
	public int dogriddelete(String id) throws Exception {
		
		//判断登录用户是否为管理员
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//内置角色不能删除
		for (int i = 0; i < STATICROLE.length; i++) {
			if(id.equals(STATICROLE[i])){
				this.setMainMessage("内置角色不能删除！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(!"admin".equals(user.getLoginname())){
			
			//判断：用户不能修改自己的授权
			HBSession sess = HBUtil.getHBSession();
			String user_id = user.getId();//获取用户编号
			String hql = "from SmtAct where objectid = '"+user_id+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for(int i=0;i<smtact_list.size();i++){
				String roleid = smtact_list.get(i).getRoleid();//获取用户的
				if(id.equals(roleid)){
					this.setMainMessage("用户不能对自己的角色进行删除操作！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("suretodelete");
		ne.setNextEventParameter(id);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要删除该记录？"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("suretodelete")
	@Transaction
	public int delete(String id) throws RadowException, SQLException {
		String userName ="";
		try {
			userName = getUserNameByRoleId(id);
			rc.deleteRole(id);
		} catch (PrivilegeException e) {
			this.setMainMessage("角色已关联"+userName+"用户,不可以删除。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.isShowMsg = true;
		this.setMainMessage("成功删除该角色！");
		this.getPageElement("grid6").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid6.dogridquery")
//	@EventDataCustomized("roleQName,roleOwner,roleQDesc")
	public int dogrid6Query(int start, int limit) throws RadowException{
		//获取角色类型
		String hostsys = request.getParameter("hostsys");
		//获取是否为角色模块查询
		String limited = request.getParameter("limited");
		//获取登录用户信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();//获取登录用户的id
		StringBuffer sql = new StringBuffer();//定义查询sql
		//登录用户为admin时，查询全部角色记录
		if("admin".equals(user.getLoginname()) || "3".equals(user.getUsertype()) || "system".equals(user.getLoginname())){
			sql.append("select smtrole.roleid,"+
					"smtrole.roledesc,"+
					 "smtrole.parent,"+
					 "smtrole.owner,"+
					 "(select loginname from smt_user where userid = smtrole.owner) ownername,"+
					 "smtrole.rolename,"+
					 "smtrole.hostsys,"+
					 "case when smtrole.hostsys = '2' then smtrole.roleid else '' end as rolecode,"+
					 "smtrole.status status,"+
//					 "smtrole.createdate,"+
					 //有人把这张表createdate字段存入了0000-00-00 00:00:00的垃圾数据，mysql会报错，看到这段的人自己想办法
					 "smtrole.hashcode"+
					 " from smt_role smtrole"+
					 " where 1=1  ");//
		}else{
			sql.append("select smtrole.roleid,"+
					"smtrole.roledesc,"+
					 "smtrole.parent,"+
					 "smtrole.owner,"+
					 "(select loginname from smt_user where userid = smtrole.owner) ownername,"+
					 "smtrole.rolename,"+
					 "smtrole.hostsys,"+
					 "case when smtrole.hostsys = '2' then smtrole.roleid else '' end as rolecode,"+
					 "smtrole.status status,"+
//					 "smtrole.createdate,"+
					 "smtrole.hashcode"+
					 " from smt_role smtrole"+
					 " where 1=1 ");/*and smtrole.roleid in(select act.roleid from smt_act act where act.objectid='"+userid+"')*/
		}
/*		if(hostsys != null){
			sql.append("and smtrole.hostsys = '"+hostsys+"' and smtrole.roleid in(select act.roleid from smt_act act where act.objectid='"+userid+"') ");
		}*/
		
		if(null == limited){
			sql.append("and smtrole.hostsys = '"+hostsys+"' ");
		}

		if(DBType.ORACLE==DBUtil.getDBType()){
			sql.append(" order by to_number(smtrole.sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sql.append(" order by (smtrole.sortid+0)");
		}
		CommonQueryBS.systemOut(sql.toString());
		this.pageQuery(sql.toString(), "SQL", start, limit);
		this.isShowMsg = false;
		return EventRtnType.SPE_SUCCESS;
	}

	/*
	 * @PageEvent("grid6.rowdbclick") public int dbclick() throws RadowException {
	 * this.openWindow("roleWindow", "pages.sysmanager.role.addRole");
	 * this.setRadow_parent_data(((Grid)getPageElement("grid6")).getJsonValues());
	 * this.isShowMsg = false; return EventRtnType.NORMAL_SUCCESS; }
	 */

	@PageEvent("grid7.dogridquery")
	public int dogrid7Query(int start, int limit) throws RadowException{
		String roleid = null == this.getPageElement("roleId") ? "" : this.getPageElement("roleId").getValue();
		String roletype = null == this.getPageElement("roletype") ? "" : this.getPageElement("roletype").getValue();
//		String sql = "select u.*,'true' checked from smt_user u ,smt_act a where    u.userid = a.objectid and u.useful='1' and u.loginname <> 'admin'";
		String sql = "";
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//system 显示所有用户 都可以操作
		if(user.getLoginname().equals("system")){
			 sql = "select u.*,case when (select 1 from smt_act a where  u.userid = a.objectid  and a.roleid='"+roleid+"')='1' then 'true' else 'false' end checked from smt_user u where  u.useful='1' and u.loginname <> 'admin' and u.loginname <> 'checker'";

		}else{
			 sql = "select u.*,case when (select 1 from smt_act a where  u.userid = a.objectid  and a.roleid='"+roleid+"')='1' then 'true' else 'false' end checked from smt_user u where u.useful='1' and u.owner='"+user.getId()+"' and u.loginname <> 'admin' and u.loginname <> 'checker'";

		}
		if(null != roletype && !roletype.equals("")){
			sql += " and u.usertype='"+roletype+"'";
		}
//		this.getPageElement("roletype").setValue("");
		this.pageQuery(sql.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid6.rowclick")
	@GridDataRange(GridData.cuerow)
	public int gridClick() throws RadowException {
		Grid grid6 = (Grid) this.getPageElement("grid6");
		String roleid = grid6.getValue("roleid").toString();
		String roletype = grid6.getValue("hostsys").toString();
		this.getPageElement("roleId").setValue(roleid);
		this.getPageElement("roletype").setValue(roletype);
		this.setNextEventName("grid7.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delUserOfRole")
	public int delUser() throws RadowException {
		String userList = this.getPageElement("removeArray").getValue();
		String userList1 = this.getPageElement("grantArray").getValue();
		String roleid = this.getPageElement("roleId").getValue();
		if(userList.contains("40288103556cc97701556d629135000f")){
			this.setMainMessage("不能移除根管理员账户【system】的授权！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "delete from smt_act where objectid in ('"+userList+"') and roleid = '"+roleid+"'";
		try {
			HBUtil.executeUpdate(sql);
			
			SceneVO scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
			String[] id = userList1.split(",");
			for(int i=0;i<id.length;i++){
				if(!id.equals(""))
				PrivilegeManager.getInstance().getIRoleControl().grant(id[i], scene.getSceneid(), roleid, true);
			}
			
			this.setMainMessage("操作成功");
			this.getPageElement("removeArray").setValue("");
			this.getPageElement("grantArray").setValue("");
			this.setNextEventName("grid7.dogridquery");
		} catch (AppException e) {
			e.printStackTrace();
		} catch (PrivilegeException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addRole.onclick")
	public int addRole() throws RadowException {
		//判断登录用户是否为管理员
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("roleWindow", "pages.sysmanager.role.addRole");
		this.setRadow_parent_data("");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("delRole.onclick")
	public int delRole() throws RadowException {
		//判断登录用户是否为管理员
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"admin".equals(user.getLoginname())){
			
			//判断：用户不能修改自己的授权
			List<HashMap<String, Object>> valuelist = getPageElement("grid6")
			.getValueList();
			
			HBSession sess = HBUtil.getHBSession();
			String user_id = user.getId();//获取用户编号
			String hql = "from SmtAct where objectid = '"+user_id+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for (int i = 0; i < valuelist.size(); i++) {
				if (valuelist.get(i).get("checked").equals(true)) {
					
					for(int j=0;j<smtact_list.size();j++){
						String roleid = smtact_list.get(j).getRoleid();//获取用户的
						String roleid1=(String) valuelist.get(i).get("roleid");//获取选中的角色编号
						if(roleid1.equals(roleid)){
							this.setMainMessage("选中的角色包含用户自己的角色，用户不能对自己的角色进行删除操作！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if((roleid1.equals("402881e456498d9601564a2ccde004c0") || roleid1.equals("4028c60c25335f86012533767f3c0002") 
								|| roleid1.equals("402881035b6a24af015b6a612d3e004b"))&&!"admin".equals(user.getLoginname())){
							this.setMainMessage("该角色为默认角色，不可修改！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}
		
		
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("suretodeleteall");
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要删除选中的记录？"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("suretodeleteall")
	@Transaction
	public void delAll() throws RadowException {
		List<HashMap<String, Object>> valuelist = getPageElement("grid6").getValueList();
		boolean sign = false;
		String userName = "";
		String roleName = "";
		for (int i = 0; i < valuelist.size(); i++) {
			if (valuelist.get(i).get("checked").equals(true)) {
				sign = true;
				try {
					userName = getUserNameByRoleId((String) valuelist.get(i).get("roleid"));
					roleName = (String)valuelist.get(i).get("rolename");
					rc.deleteRole((String) valuelist.get(i).get("roleid"));
				} catch (Exception e) {
					this.setMainMessage(roleName+"角色已关联"+userName+"用户,不可以删除。");
					return;
				}
			}
		}
		if (!sign) {
			throw new RadowException("请选择要删除的角色！");
		}
		List list = new ArrayList();
		try {
			new LogUtil().createLog("619", "SMT_ROLE","","", "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getPageElement("grid6").reload();
	}

	@PageEvent("clean.onclick")
	@NoRequiredValidate
	public int clean() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dogridgrant")
	public int grant(String id) throws RadowException {
		//判断登录用户是否为管理员
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
/*		if((id.equals("402881e456498d9601564a2ccde004c0") || id.equals("4028c60c25335f86012533767f3c0002") 
				|| id.equals("402881035b6a24af015b6a612d3e004b"))&&!"admin".equals(user.getLoginname())){
			this.setMainMessage("该角色为默认角色，不可修改！");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		if(!"system".equals(user.getLoginname())){
			
			//判断：用户不能修改自己的授权
			HBSession sess = HBUtil.getHBSession();
			String userid = user.getId();//获取用户编号
			String hql = "from SmtAct where objectid = '"+userid+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for(int i=0;i<smtact_list.size();i++){
				String roleid = smtact_list.get(i).getRoleid();//获取用户的
				if(id.equals(roleid)){
					this.setMainMessage("用户不能对自己的角色进行授权操作！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		
		this.setRadow_parent_data(id);
		this.openWindow("grantWindow", "pages.sysmanager.role.roletree&roleid="
				+ id + "");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String getUserNameByRoleId(String roleId) {
		String sql = "select * "
				   + "  from smt_user a "
				   + "where "
				   + "a.userid "
				   + "in "
				   + "(select b.objectid from smt_act b where b.roleid='"+roleId+"')";
		Connection conn = HBUtil.getHBSession().connection();
		PreparedStatement ps;
		String userNameList = "";
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			StringBuffer sb = new StringBuffer();
			while(rs.next()) {
				sb.append(rs.getString("USERNAME"));
				sb.append(",");
			}
			userNameList = sb.toString();
			userNameList = userNameList.substring(0,userNameList.length()-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userNameList;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("usersort")
	@NoRequiredValidate
	public int usersort(String pageInfo)throws RadowException{
		String[] pfs = pageInfo.split(",");
		int pn = Integer.valueOf(pfs[1]);
		int pSize = Integer.valueOf(pfs[0]);
		
		List<HashMap<String,String>> list = this.getPageElement("grid6").getStringValueList();
		try {
			int i = 1;
			if(pn>1){
				i = pSize*pn;
			}
			
			for(HashMap<String,String> m : list){
				String roleid = m.get("roleid");
				String sql = "update smt_role set sortid="+String.valueOf(i)+" where roleid = '"+roleid+"'";
				HBUtil.executeUpdate(sql);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
