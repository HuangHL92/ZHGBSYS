package com.insigma.siis.local.pagemodel.sysmanager.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.IRoleControl;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class OutSideRolePageModel extends PageModel{
	private IRoleControl rc = PrivilegeManager.getInstance().getIRoleControl();

	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		List userList=sess.createQuery("from SmtUser where useful='1'").list();
		HashMap<String,String> map = new HashMap<String,String>();
		for(int i=0;i<userList.size();i++){
			SmtUser u = (SmtUser) userList.get(i);
			map.put(u.getId(), u.getLoginname());
		}
		((Combo)this.getPageElement("roleOwner")).setValueListForSelect(map);
		this.setNextEventName("grid6.dogridquery");
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
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("grid6");
		//判断登陆用户是否为系统管理员用户，如果不是不可进行新增角色操作
		if(!"1".equals(user.getUsertype())){
			this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
			grid6.reload();
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.isShowMsg = false;
		//是否有字段值改变判断标志
		boolean isChange = false;

		
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
	@EventDataCustomized("roleQName,roleOwner,roleQDesc")
	public int dogrid6Query(int start, int limit) throws RadowException{
		
		//获取登录用户信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();//获取登录用户的id
		StringBuffer sql = new StringBuffer();//定义查询sql
		//登录用户为admin时，查询全部角色记录
		if("admin".equals(user.getLoginname()) || "3".equals(user.getUsertype())){
			sql.append("select smtrole.roleid,"+
					"smtrole.roledesc,"+
					 "smtrole.parent,"+
					 "smtrole.owner,"+
					 "(select loginname from smt_user where userid = smtrole.owner) ownername,"+
					 "smtrole.rolename,"+
					 "case when smtrole.hostsys = '2' then smtrole.roleid else '' end as rolecode,"+
					 "smtrole.hostsys,"+
					 "smtrole.status status,"+
					 "smtrole.createdate,"+
					 "smtrole.hashcode"+
					 " from smt_role smtrole"+
					 " where 1=1 ");
		}else{
			sql.append("select smtrole.roleid,"+
					"smtrole.roledesc,"+
					 "smtrole.parent,"+
					 "smtrole.owner,"+
					 "(select loginname from smt_user where userid = smtrole.owner) ownername,"+
					 "smtrole.rolename,"+
					 "case when smtrole.hostsys = '2' then smtrole.roleid else '' end as rolecode,"+
					 "smtrole.hostsys,"+
					 "smtrole.status status,"+
					 "smtrole.createdate,"+
					 "smtrole.hashcode"+
					 " from smt_role smtrole"+
					 " where (smtrole.owner = '"+userid+"' or"+
					 " smtrole.roleid in"+
					 " (select smtact.roleid"+
						 " from smt_act smtact"+
						" where smtact.objectid = '"+userid+"')) and smtrole.hostsys <> '4' ");
		}
		
		
		//获取页面参数
		//角色名称
		String roleQName = "";
		try {
			roleQName = this.getPageElement("roleQName").getValue();
		} catch (Exception e) {
			
		}
		if(!"".equals(roleQName)){
			sql.append(" and smtrole.rolename like '%"+roleQName+"%'");
		}
		//角色所有者
		String roleOwner="";
		try {
			roleOwner = this.getPageElement("roleOwner").getValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!"".equals(roleOwner)){
			sql.append(" and smtrole.owner='"+roleOwner+"'");
		}
		//角色描述
		String roleQDesc="";
		try {
			roleQDesc = this.getPageElement("roleQDesc").getValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!"".equals(roleQDesc)){
			sql.append(" and smtrole.roledesc like '%"+roleQDesc+"%'");
		}
		//在sql的最后面增加排序　　modify by lizs 2016/10/18
		//sql.append(" order by smtrole.sortid");
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql.append(" order by to_number(smtrole.sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sql.append(" order by (smtrole.sortid+0)");
		}
		CommonQueryBS.systemOut("1111"+sql.toString());
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

	@PageEvent("addRole.onclick")
	public int addRole() throws RadowException {
		//判断登录用户是否为管理员
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//判断登陆用户是否为系统管理员用户，如果不是不可进行新增角色操作
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
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
		//判断登陆用户是否为系统管理员用户，如果不是不可进行新增角色操作
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("您好，您非系统管理员用户，没有操作此模块的权限！");
				return EventRtnType.NORMAL_SUCCESS;
			}
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
					if(valuelist.get(i).get("hostsys").equals("3")){
						this.setMainMessage("系统默认角色，不可删除！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					for(int j=0;j<smtact_list.size();j++){
						String roleid = smtact_list.get(j).getRoleid();//获取用户的
						String roleid1=(String) valuelist.get(i).get("roleid");//获取选中的角色编号
						if(roleid1.equals(roleid)){
							this.setMainMessage("选中的角色包含用户自己的角色，用户不能对自己的角色进行删除操作！");
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
		if(!"admin".equals(user.getLoginname())){
			
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
