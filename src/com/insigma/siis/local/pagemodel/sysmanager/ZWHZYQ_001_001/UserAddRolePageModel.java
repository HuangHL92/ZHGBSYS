package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.IRoleControl;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class UserAddRolePageModel extends PageModel {
	private IRoleControl rc = PrivilegeManager.getInstance().getIRoleControl();

	@Override
	public int doInit() throws RadowException {
/*		HBSession sess = HBUtil.getHBSession();
		List userList=sess.createQuery("from SmtUser where useful='1'").list();
		HashMap<String,String> map = new HashMap<String,String>();
		for(int i=0;i<userList.size();i++){
			SmtUser u = (SmtUser) userList.get(i);
			map.put(u.getId(), u.getLoginname());
		}
		((Combo)this.getPageElement("roleOwner")).setValueListForSelect(map);*/
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



	@PageEvent("grid6.dogridquery")
	@EventDataCustomized("roleQName,roleQDesc")
	public int dogrid6Query(int start, int limit) throws RadowException{
		
		//获取登录用户信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();//获取登录用户的id
		StringBuffer sql = new StringBuffer();//定义查询sql
		//登录用户为admin时，查询全部角色记录
		if("admin".equals(user.getLoginname()) ){
			sql.append("select smtrole.roleid,"+
					"smtrole.roledesc,"+
					 "smtrole.parent,"+
					 "smtrole.owner,"+
					 "(select loginname from smt_user where userid = smtrole.owner) ownername,"+
					 "smtrole.rolename,"+
					 "smtrole.hostsys,"+
					 "case when smtrole.hostsys = '2' then smtrole.roleid else '' end as rolecode,"+
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
					 "smtrole.hostsys,"+
					 "case when smtrole.hostsys = '2' then smtrole.roleid else '' end as rolecode,"+
					 "smtrole.status status,"+
					 "smtrole.createdate,"+
					 "smtrole.hashcode"+
					 " from smt_role smtrole"+
					 " where (smtrole.owner = '"+userid+"' or"+
					 " smtrole.roleid in"+
					 " (select smtact.roleid"+
						 " from smt_act smtact"+
						" where smtact.objectid = '"+userid+"') or  smtrole.roleid in ('402881e456498d9601564a2ccde004c0','4028c60c25335f86012533767f3c0002','402881035b6a24af015b6a612d3e004b'))  ");
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
		//sql.append(" order by smtrole.sortid");
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql.append(" order by to_number(smtrole.sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sql.append(" order by (smtrole.sortid+0)");
		}
//		CommonQueryBS.systemOut(sql.toString());
		this.pageQuery(sql.toString(), "SQL", start, limit);
		this.isShowMsg = false;
		return EventRtnType.SPE_SUCCESS;
	}







	@PageEvent("clean.onclick")
	@NoRequiredValidate
	public int clean() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
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
