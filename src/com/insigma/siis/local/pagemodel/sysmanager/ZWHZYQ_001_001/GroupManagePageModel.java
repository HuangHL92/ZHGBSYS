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
	 * ϵͳ������Ϣ
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
	 * ����û��������ѯ�û���Ϣ
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
				throw new RadowException("��ѯ����");
			}
			//this.getPageElement("searchGroupBtn").setValue(group.getName());
			this.getPageElement("optionGroup").setValue(group.getName());
			this.getExecuteSG().addExecuteCode("window.reloadThisGroup()");
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}*/
		this.getPageElement("checkedgroupid").setValue(id);
		if(id.equals("X001")){
			this.getExecuteSG().addExecuteCode("document.getElementById('groupname').innerHTML='�޹���λ�û�'");
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
	 *  ��ѯ�û��б���Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		//��ȡ��½�û���Ϣ
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String groupid = this.getPageElement("checkedgroupid").getValue();//��ȡ����id
		StringBuffer str = new StringBuffer();
		//�жϲ�ѯ����
		String type = this.getPageElement("qType").getValue();
		if(type.equals("2")){
			
			//��鵱ǰ�û��Ƿ�Ϊsystem�����Ϊsystem������Բ�ѯ�����û�
			String sql = "smtuser0_.otherinfo like '"+user.getOtherinfo()+"%' or";
			
			if(user.getId() != null && user.getId().equals("40288103556cc97701556d629135000f")){
				sql = "1=1 or";
			}
			
			
			//��ȡҳ���ѯ������Ϣ
			String loginname = this.getPageElement("searchUserBtn").getValue();//�û���¼��
			String name = this.getPageElement("searchUserNameBtn").getValue(); //�û���
			String useful = this.getPageElement("useful").getValue();          //�û�״̬
			String usertype = this.getPageElement("searchUserTypeBtn").getValue();      //�û�����
			str.append("select smtuser0_.userid,smtuser0_.loginname,smtuser0_.useful,"
					+ "smtuser0_.isleader,smtuser0_.username,smtuser0_.owner,smtuser0_.usertype,smtuser0_.otherinfo,smtuser0_.createdate,"
					+ "(select b0101 from b01 b where b.b0111=smtuser0_.otherinfo) b0101,"
					+ "(select b0114 from b01 b where b.b0111=smtuser0_.otherinfo) b0114 from smt_user smtuser0_ where ("+sql+" smtuser0_.otherinfo='X001')");
			//���Ĳ�ѯ��ʽ�������û�����ͬһ���û����У���ѯ�û������û���
			
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
			//������ѯ
			str.append("select smtuser0_.userid,smtuser0_.loginname,smtuser0_.useful,"
					+ "smtuser0_.isleader,smtuser0_.username,smtuser0_.owner,smtuser0_.usertype,smtuser0_.otherinfo,smtuser0_.createdate,"
					+ "(select b0101 from b01 b where b.b0111=smtuser0_.otherinfo) b0101,"
					+ "(select b0114 from b01 b where b.b0111=smtuser0_.otherinfo) b0114 from smt_user smtuser0_ where smtuser0_.otherinfo = '"+groupid+"'");
		}
		
		this.flag=0;		
		//�����������ƣ�ɾ�����û�����ҳ������ʾ add by lizs 20161019
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
	 * ���ɻ�����
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
		
		//ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
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
	//��ѯ�Ƿ����¼��ڵ�  falseû�� true��
		public static String hasChildren(String id){
			String sql="from SmtGroup b where b.parentid='"+id+"'";// -1������ְ��Ա
			List<SmtGroup> list = HBUtil.getHBSession().createQuery(sql).list();
			if(list!=null && list.size()>0){
				return "false";
			}else{
				return "true";
			}
		}
	/**
	 * ���ڵ����û����½�����
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
					this.setMainMessage("���ã���û�и��û����Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("��ѡ����Ҫ��������֯");
		}else{
			this.openWindow("createGroupWin", "pages.sysmanager.ZWHZYQ_001_001.GroupAddWindow");
			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �����޸��û����½�����
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
					this.setMainMessage("���ã���û�и��û����Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("��ѡ����Ҫ��������֯");
		}else{
			this.openWindow("modifyGroupWin", "pages.sysmanager.ZWHZYQ_001_001.GroupModifyWindow");
			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����ɾ���û���
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
					this.setMainMessage("���ã���û�и��û����Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫɾ�����û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doDeleteGroup",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ��ɾ��������");
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
				this.setMainMessage("ɾ���û���ɹ�");
				this.reloadPage();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("ɾ��ʧ�ܣ�"+e.getMessage());
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
					this.setMainMessage("���ã���û�иû�����Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("��ѡ����Ҫ��������֯");
		}else{
//			this.openWindow("createUserWin", "pages.sysmanager.ZWHZYQ_001_001.UserAddWindow");
			this.getExecuteSG().addExecuteCode("$h.openWin('createUserWin','pages.sysmanager.ZWHZYQ_001_001.UserAddWindow&groupid="+groupid+"','������Ա�û�����',1000,550,'"+groupid+"',ctxPath,null,{maximized:false});");
//			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
//			this.getExecuteSG().addExecuteCode("addTab('�½��û�','',ctxPath+'/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.UserAddWindow',false,false);");			
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
					this.setMainMessage("���ã���û�иû�����Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String usertype = this.getPageElement("CurUserType").getValue();
		if(!usertype.equals("1")){
			this.setMainMessage("���ã�����ϵͳ����Ա�û���û�д����û���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("��ѡ����Ҫ��������֯");
		}else{
			this.getExecuteSG().addExecuteCode("$h.openWin('createAdminWin','pages.sysmanager.ZWHZYQ_001_001.AdminAddWindow','��������Ա�û�����',550,300,'"+groupid+"',ctxPath,null,{maximized:false});");
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
					this.setMainMessage("���ã���û�и��û����Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
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
			this.setMainMessage("��ѡ����Ҫ�������û�");
		}else if(choosed>1){
			this.setMainMessage("һ��ֻ���޸�һ���û���Ϣ");
		}else{
			this.openWindow("modifyUserWin", "pages.sysmanager.ZWHZYQ_001_001.UserModifyWindow");
			this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 *  �Ƴ��û���¼����ԭ����ɾ�����ݿ��¼�޸�Ϊ�����û�����Ч״̬�ֶ���Ϊ��Ч----�������
	 * @return
	 * @throws RadowException
	 * @throws PrivilegeException 
	 */
	@PageEvent("removeUserBtn.onclick")
	public int removeUser(String id) throws RadowException, PrivilegeException{
//		String groupid = this.getPageElement("checkedgroupid").getValue();
		String groupid= id.split(",")[1];
		String opid = id.split(",")[0]; //���������˵�id
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
					this.setMainMessage("���ã���û�и���֯��Ȩ��");
					return EventRtnType.NORMAL_SUCCESS;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж�Ҫɾ�����û��Ƿ����system��system�ǳ�������Ա������ɾ��
//		boolean isSystem = checkLoginname("memberGrid");
		if(opid.equals("40288103556cc97701556d629135000f")){
			this.setMainMessage("systemΪ��������Ա������ɾ����");
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
			this.setMainMessage("��ѡ����Ҫ�Ƴ��ĳ�Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String[] userids = getuserids.split(",");
		for(int i=0;i<userids.length;i++){
			if(userids[i].equals(userid)){
				this.setMainMessage("���ã�������ɾ���Լ�ʹ�õ��û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
//		if(count >1){
//			//this.setMainMessage("ȷ��Ҫ�Ƴ���"+count+"����Ա�");
//			this.setMainMessage("�û���ϢΪ��Ҫ��Ϣ���ݲ�����������ɾ��������");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.addNextEvent(NextEventValue.YES, "doRemoveUser",id);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
/*		if(count != 0){
			this.setMainMessage("ȷ��Ҫ�Ƴ��ó�Ա�");
		}*/
		this.setMainMessage("ȷ���Ƴ����û���");
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
					returnMessage = "���û��пɷ���Ȩ�޵Ľ�ɫ������ɾ������Ҫɾ��������ȡ���û��Ľ�ɫ��Ȩ��";
					continue;
				}
				boolean result = PrivilegeManager.getInstance().getIGroupControl().removeUserFromGroup(groupid, userids[i]);
				if(result){
					this.setMainMessage("�����Ƴ��û��ɹ�");
					List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
					if(list == null || list.isEmpty()){//�����Ա���Ƴ����ʱ����ˢ��
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
			//ɾ����ɫ 
//			sess.createSQLQuery("delete from smt_role where roleid in(select roleid from smt_act where (objectid in("+users.toString()+") or userid in("+users.toString()+")))").executeUpdate();
			sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid in("+users.toString()+")").executeUpdate();
			sess.createSQLQuery("delete from smt_act where (objectid in("+users.toString()+") or userid in("+users.toString()+"))").executeUpdate();
			sess.createSQLQuery("delete from smt_usergroupref where userid in("+users.toString()+")").executeUpdate();

			sess.createSQLQuery("delete from competence_userdept where userid in("+users.toString()+")").executeUpdate();
			List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
			if(list == null || list.isEmpty()){//�����Ա���Ƴ����ʱ����ˢ��
				this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
			}else{
				this.createPageElement("memberGrid", ElementType.GRID, false).reload();
			}
			this.setMainMessage("�û�ɾ���ɹ���");
		} catch (Exception e) {
			returnMessage = e.getMessage();
			this.setMainMessage(returnMessage);
			System.out.println("***�����Ƴ��û�ʧ�ܣ�"+returnMessage);
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
			return number;//ѡ�еĵڼ���
		}
		return result;//ѡ���û�����
	}
	/**
	 * ��ȡѡ�������ж��Ƿ�Ϊ��ǰ�����߽����û�����Ϊ���ܽ��в���
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
				//���ǹ���Ա ���߱��������ǹ���Ա�����޸�
				if(cueUserName.equals(map.get("loginname")) || !"1".equals(isleader) || (map.get("usertype").equals("1") && !"system".equals(cueUserName) && !"admin".equals(cueUserName))){
					throw new AppException("���ã��㲻�߱������û� "+ map.get("username")+" ��Ȩ��");
				}
				number = i;
				result++;
			}
		}
		if(isRowNum){
			return number;//ѡ�еĵڼ���
		}
		return result;//ѡ���û�����
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
	 * ���ڵ�����Ϣ��������ҳ�� 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("setInfoGroupBtn.onclick")
	public int setInfoGroupClick() throws RadowException {
		
		if(isLeader()){
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		String area = (String) sess.createSQLQuery("SELECT b.infogroupname FROM AA01 a,COMPETENCE_INFOGROUP b WHERE a.AAA001='AREA_ID' and a.AAA005=b.infogroupid").uniqueResult();
		if(area==null){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			InfoGroup ig = new InfoGroup();
			ig.setCreateuserid("admin");
			ig.setInfogroupname("��Ϣ����");
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
			this.setMainMessage("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(choosed==0){
//			this.setMainMessage("��ѡ����Ҫ�������û�");
//		}else{
			this.addNextEvent(NextEventValue.YES, "reset",id);
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
			this.setMainMessage("��ȷ��Ҫ���ø��û��ĵ�¼����ô��");
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
		this.setMainMessage("��������ɹ������ú��������û���¼����ͬ��");
		return EventRtnType.NORMAL_SUCCESS;
	}
//	@PageEvent("reset")
//	public int reset(String userid) throws RadowException{
//		this.addNextEvent(NextEventValue.YES, "doReset",userid);
//		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
//		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ������(confirm���ʹ���)
//		this.setMainMessage("ȷ��Ҫ���øó�Ա���룿");
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
//		this.setMainMessage("��������ɹ�,����Ϊ��"+passwd);
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * ����ҳ���ѯ����
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
	
	//����ť
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
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �û���¼������ʾ��Ȩ��Ϣ
	 * 1.������Ȩ��Ϣ�������¼���
	 * 2.����ģ����Ȩ������ɫ
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
	 * ����idɾ��������Ȩһ����¼
	 */
	@PageEvent("deleteOrgById")
	public int deleteOrgById(String id) throws RadowException{
		String sql ="delete from competence_userdept t where t.userdeptid='"+id+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		this.setNextEventName("orgGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����һ��������Ȩ
	 */
	@PageEvent("addOrgById")
	public int addOrgById(String id) throws RadowException{
//		String sql ="insert into competence_userdept t where t.userdeptid='"+id+"'";
		UserDept dept = new UserDept();
		String userId = this.getPageElement("idForOrgQuery").getValue();
		dept.setUserid(userId);
		dept.setB0111(id);
		dept.setType("1");//��Ĭ�Ͽ���ά�����Ժ��ٸ�
//		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		HBSession sess = HBUtil.getHBSession();
		sess.save(dept);
		sess.flush();
		this.setNextEventName("orgGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �û���¼˫�����û�����ҳ��
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
//		this.getExecuteSG().addExecuteCode("	doOpenPupWin('/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&userid='"+sUserID+",'��Ϣ��Ȩ������ƴ���',600,255,null);");
		this.getExecuteSG().addExecuteCode("$h.openWin('UserDetailWin', 'pages.sysmanager.sysuser.UserDetail&groupid="+groupid+"&userid="+sUserID+"', '�û�����', 1000, 550, '"+sUserID+"', '"+request.getContextPath()+"');");
		
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
//		this.setSelfDefResData("{'results' : 1,'rows': [{ 'b0101': 2, 'b0114': '��һɽ', 'b0194': 'Ů' }]}");
		List<Object[]> list = query.list();
		for (Object[] objects : list) {
			String b0194 = objects[1].toString();
			if(null != b0194){
				if("1".equals(b0194)){
					b0194 = "���˵�λ";
				}else if("2".equals(b0194)){
					b0194 = "�������";
				}
				else if("3".equals(b0194)){
					b0194 = "��������";
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
					b0194 = "���˵�λ";
				}else if("2".equals(b0194)){
					b0194 = "�������";
				}
				else if("3".equals(b0194)){
					b0194 = "��������";
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
