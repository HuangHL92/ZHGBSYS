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
	 *  ��ɫ����ҳ���������޸�
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvent("grid6.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws RadowException, SQLException {

		//��ȡ��¼�û�����Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//��ȡҳ������
		Grid grid6 = (Grid) this.getPageElement("grid6");
		//�жϵ�½�û��Ƿ�Ϊϵͳ����Ա�û���������ǲ��ɽ���������ɫ����
		if(!"1".equals(user.getUsertype())){
			this.setMainMessage("���ã�����ϵͳ����Ա�û���û�в�����ģ���Ȩ�ޣ�");
			grid6.reload();
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.isShowMsg = false;
		//�Ƿ����ֶ�ֵ�ı��жϱ�־
		boolean isChange = false;

		
		if(!"admin".equals(user.getLoginname())){
			
			//�жϣ��û������޸��Լ�����Ȩ
			HBSession sess = HBUtil.getHBSession();
			String user_id = user.getId();//��ȡ�û����
			String hql = "from SmtAct where objectid = '"+user_id+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for(int i=0;i<smtact_list.size();i++){
				String roleid = smtact_list.get(i).getRoleid();//��ȡ�û���
				if(grid6.getValue("roleid").equals(roleid)){
					this.setMainMessage("�û����ܶ��Լ��Ľ�ɫ�����޸Ĳ�����");
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
		
		//�жϵ�¼�û��Ƿ�Ϊ����Ա
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			grid6.reload();
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ȡ״̬��ԭֵ
		String status = role.getStatus();
		
		grid6.setValueToObj(role);
		
		//��ȡҳ���޸ĵĽ�ɫ������ֵ
		String roledesc = (String) grid6.getValue("roledesc");
		//�жϣ���ҳ���еĽ�ɫ������ֵ�����ݿ��е�ֵ��ͬ����ҳ���е�ֵ����ֵ����role
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
			
		//�жϽ�ɫ��״ֵ̬�Ƿ�ı�
		if(!status.equals(role.getStatus())){
			isChange = true;
		}
		if(isChange){
			//�ж��޸ĵĽ�ɫ�Ƿ�Ϊ��¼�û��Լ��Ľ�ɫ���û������޸��Լ��Ľ�ɫ
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
					this.setMainMessage("�ý�ɫ��û��Ȩ���޸ģ�");
					grid6.reload();
					return EventRtnType.NORMAL_SUCCESS;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}finally{
				//�ر���Դ
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
			//���½�ɫ����
			try {
				rc.updateRole(role);
			} catch (PrivilegeException e) {
				this.isShowMsg = true;
				this.setMainMessage(e.getMessage());
				grid6.reload();
				e.printStackTrace();
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("��ɫ��Ϣ�޸ĳɹ���");
		}
		
		grid6.reload();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dogriddelete")
	public int dogriddelete(String id) throws Exception {
		
		//�жϵ�¼�û��Ƿ�Ϊ����Ա
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"admin".equals(user.getLoginname())){
			
			//�жϣ��û������޸��Լ�����Ȩ
			HBSession sess = HBUtil.getHBSession();
			String user_id = user.getId();//��ȡ�û����
			String hql = "from SmtAct where objectid = '"+user_id+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for(int i=0;i<smtact_list.size();i++){
				String roleid = smtact_list.get(i).getRoleid();//��ȡ�û���
				if(id.equals(roleid)){
					this.setMainMessage("�û����ܶ��Լ��Ľ�ɫ����ɾ��������");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName("suretodelete");
		ne.setNextEventParameter(id);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪɾ���ü�¼��"); // ������ʾ��Ϣ
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
			this.setMainMessage("��ɫ�ѹ���"+userName+"�û�,������ɾ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.isShowMsg = true;
		this.setMainMessage("�ɹ�ɾ���ý�ɫ��");
		this.getPageElement("grid6").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid6.dogridquery")
	@EventDataCustomized("roleQName,roleOwner,roleQDesc")
	public int dogrid6Query(int start, int limit) throws RadowException{
		
		//��ȡ��¼�û���Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();//��ȡ��¼�û���id
		StringBuffer sql = new StringBuffer();//�����ѯsql
		//��¼�û�Ϊadminʱ����ѯȫ����ɫ��¼
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
		
		
		//��ȡҳ�����
		//��ɫ����
		String roleQName = "";
		try {
			roleQName = this.getPageElement("roleQName").getValue();
		} catch (Exception e) {
			
		}
		if(!"".equals(roleQName)){
			sql.append(" and smtrole.rolename like '%"+roleQName+"%'");
		}
		//��ɫ������
		String roleOwner="";
		try {
			roleOwner = this.getPageElement("roleOwner").getValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!"".equals(roleOwner)){
			sql.append(" and smtrole.owner='"+roleOwner+"'");
		}
		//��ɫ����
		String roleQDesc="";
		try {
			roleQDesc = this.getPageElement("roleQDesc").getValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!"".equals(roleQDesc)){
			sql.append(" and smtrole.roledesc like '%"+roleQDesc+"%'");
		}
		//��sql��������������򡡡�modify by lizs 2016/10/18
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
		//�жϵ�¼�û��Ƿ�Ϊ����Ա
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//�жϵ�½�û��Ƿ�Ϊϵͳ����Ա�û���������ǲ��ɽ���������ɫ����
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("���ã�����ϵͳ����Ա�û���û�в�����ģ���Ȩ�ޣ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("roleWindow", "pages.sysmanager.role.addRole");
		this.setRadow_parent_data("");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("delRole.onclick")
	public int delRole() throws RadowException {
		//�жϵ�¼�û��Ƿ�Ϊ����Ա
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//�жϵ�½�û��Ƿ�Ϊϵͳ����Ա�û���������ǲ��ɽ���������ɫ����
		if(!"admin".equals(user.getLoginname())){
			if(!"1".equals(user.getUsertype())){
				this.setMainMessage("���ã�����ϵͳ����Ա�û���û�в�����ģ���Ȩ�ޣ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(!"admin".equals(user.getLoginname())){
			
			//�жϣ��û������޸��Լ�����Ȩ
			List<HashMap<String, Object>> valuelist = getPageElement("grid6")
			.getValueList();
			
			HBSession sess = HBUtil.getHBSession();
			String user_id = user.getId();//��ȡ�û����
			String hql = "from SmtAct where objectid = '"+user_id+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for (int i = 0; i < valuelist.size(); i++) {
				if (valuelist.get(i).get("checked").equals(true)) {
					if(valuelist.get(i).get("hostsys").equals("3")){
						this.setMainMessage("ϵͳĬ�Ͻ�ɫ������ɾ����");
						return EventRtnType.NORMAL_SUCCESS;
					}
					for(int j=0;j<smtact_list.size();j++){
						String roleid = smtact_list.get(j).getRoleid();//��ȡ�û���
						String roleid1=(String) valuelist.get(i).get("roleid");//��ȡѡ�еĽ�ɫ���
						if(roleid1.equals(roleid)){
							this.setMainMessage("ѡ�еĽ�ɫ�����û��Լ��Ľ�ɫ���û����ܶ��Լ��Ľ�ɫ����ɾ��������");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}
		
		
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName("suretodeleteall");
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪɾ��ѡ�еļ�¼��"); // ������ʾ��Ϣ
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
					this.setMainMessage(roleName+"��ɫ�ѹ���"+userName+"�û�,������ɾ����");
					return;
				}
			}
		}
		if (!sign) {
			throw new RadowException("��ѡ��Ҫɾ���Ľ�ɫ��");
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
		
		//�жϵ�¼�û��Ƿ�Ϊ����Ա
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"admin".equals(user.getLoginname())){
			
			//�жϣ��û������޸��Լ�����Ȩ
			HBSession sess = HBUtil.getHBSession();
			String userid = user.getId();//��ȡ�û����
			String hql = "from SmtAct where objectid = '"+userid+"'";
			List<SmtAct> smtact_list = sess.createQuery(hql).list();
			for(int i=0;i<smtact_list.size();i++){
				String roleid = smtact_list.get(i).getRoleid();//��ȡ�û���
				if(id.equals(roleid)){
					this.setMainMessage("�û����ܶ��Լ��Ľ�ɫ������Ȩ������");
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
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
