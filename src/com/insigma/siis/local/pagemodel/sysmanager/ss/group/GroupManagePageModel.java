package com.insigma.siis.local.pagemodel.sysmanager.ss.group;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUserselfcolumn;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
/**
 * �û�����֯����ģ��PageModel
 * @author ljd
 * <p>Company: �㽭���¶���������޹�˾</p>
 *
 */
public class GroupManagePageModel extends PageModel {
	
	/**
	 * ϵͳ������Ϣ   
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	
	public GroupManagePageModel(){
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
				////2017.05.09
				//String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
				//List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
				//UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
				//boolean issupermanager=new DefaultPermission().isSuperManager(vo);
				areaInfo.put("manager", "true");
			}
			/**else{
				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.AAA146,a.AAA005 FROM AA01 a,AA26 b WHERE a.AAA001='AREA_ID' and a.AAA005=b.AAB301 ").uniqueResult();
				if(area==null){
					String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
					areaInfo.put("areaname", areaname);
					areaInfo.put("areaid", "G001");
				}else{
					areaInfo.put("areaname", area[0]);
					areaInfo.put("areaid", area[1]);
				}
			}**/
		/**	String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);**/
//			if(groups.isEmpty() || issupermanager){
//				areaInfo.put("manager", "true");
//			}else{
//				areaInfo.put("manager", "false");
//			}
			/**areaInfo.put("manager", "true");**/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}

	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
			if(group == null){
				throw new RadowException("��ѯ����");
			}
			//this.getPageElement("searchGroupBtn").setValue(group.getName());
			this.getPageElement("optionGroup").setValue(group.getName());
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		this.getPageElement("searchGroupBtn").setValue(" ");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		List alist = new ArrayList();
		List<UserVO> mlist = new ArrayList();
		try {
			alist = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
			mlist = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_LEADER);
		} catch (PrivilegeException e) {
			this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
		}
		if(alist == null || alist.isEmpty()){
			this.setSelfDefResData(this.getPageQueryData(new ArrayList(), start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		for(int i=0;i<alist.size();i++){
			UserVO member = (UserVO)alist.get(i);
			member.setIsleader("0");
			if(mlist != null){
				for(int j=0;j<mlist.size();j++){
					UserVO manager = mlist.get(j);
					if(member.getId().equals(manager.getId())){
						member.setIsleader("1");
					}
				}
			}
		}
		this.setSelfDefResData(this.getPageQueryData(alist, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		List<GroupVO> list = PrivilegeManager.getInstance()
				.getIGroupControl().findByParentId(node);
		//ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ�� 
		List<GroupVO> choose = new ArrayList<GroupVO>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
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
		//��
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (GroupVO group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
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
	/*
	@PageEvent("setArea.onclick")
	public int setArea() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String groupname = this.getPageElement("searchGroupBtn").getValue();
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫ���õ���֯");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doSetArea",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); 
		this.setMainMessage("ȷ����"+groupname+"����Ϊͳ������");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doSetArea")
	public int doSetArea(String groupid) throws RadowException{
		OverallAreaBean area = new OverallAreaBean();
		area.setAreaCode(groupid);
		boolean result = false;
		try {
			result = OverallAreaManager.getInstance().setAreaAsOverall(area);
		} catch (AppException e) {
			throw new RadowException(e.getMessage());
		}
		if(result){
			this.getPageElement("isArea").setValue("��");
			this.reloadPage();
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.setMainMessage("����ʧ��");
			return EventRtnType.FAILD;
		}
	}
	
	@PageEvent("cancelArea.onclick")
	public int cancelArea() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String groupname = this.getPageElement("searchGroupBtn").getValue();
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫ���õ���֯");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doCancelArea",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); 
		this.setMainMessage("ȷ��ȡ��"+groupname+"ͳ������,ȡ���Ļ���������ͳ�������в������������ݲ��ɻָ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doCancelArea")
	public int doCancelArea(String groupid) throws RadowException{
		OverallAreaBean area = new OverallAreaBean();
		area.setAreaCode(groupid);
		boolean result = false;
		try {
			result = OverallAreaManager.getInstance().cancelAreaAsOverall(area);
		} catch (AppException e) {
			throw new RadowException(e.getMessage());
		}
		if(result){
			this.getPageElement("isArea").setValue("��");
			this.reloadPage();
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.setMainMessage("ȡ��ʧ��");
			return EventRtnType.FAILD;
		}
	}
	*/
	@PageEvent("searchGroupBtn.ontriggerclick")
	public int searchGroup(String name) throws RadowException{
		if(name == null ||name.trim().equals("")){
			this.openWindow("groupWin", "pages.sysmanager.group.GroupWindow");
			this.setRadow_parent_data("searchAll");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String groupid = this.getPageElement("forsearchgroupid").getValue();
		if(!groupid.equals("")){
			query(groupid);
			this.getPageElement("forsearchgroupid").setValue("");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("groupname", name.trim());
		List list = new ArrayList();
		try {
			list = PrivilegeManager.getInstance().getIGroupControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(), -1, 0, params);
			if(list.size() != 1){
				params.clear();
				params.put("name", name.trim());
				list = PrivilegeManager.getInstance().getIGroupControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(), -1, 0, params);
				if(list == null || list.isEmpty()){
					this.setMainMessage("�����֯������");
				}else{
					this.openWindow("groupWin", "pages.sysmanager.group.GroupWindow");
					this.setRadow_parent_data(name);
				}
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		GroupVO group = (GroupVO)list.get(0);
		query(group.getId());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("searchUserBtn.ontriggerclick")
	public int searchUser(String name) throws RadowException{
		if(name == null ||name.trim().equals("")){
			this.openWindow("userShowWin", "pages.sysmanager.group.GroupShowWindow");
			this.setRadow_parent_data("searchAll");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HashMap<String,String> params = new HashMap<String,String>();
		List list = new ArrayList();
		try {
			params.put("loginname", name.trim());
			list = PrivilegeManager.getInstance().getIUserControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(), -1, -1, params);
			if(list == null && list.isEmpty()){
				this.setMainMessage("����û���¼�����û�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.openWindow("userShowWin", "pages.sysmanager.group.GroupShowWindow");
		this.setRadow_parent_data(name);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("addMemberBtn.onclick")
	public int userManageOnClick() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫ������û���");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.openWindow("userWin", "pages.sysmanager.ss.group.UserManageWindow");
			this.setRadow_parent_data(groupid);
		}
		//������޳�Ա�������ӳ�Աʱ����֮ǰmemberGrid��ʾ�����г�Ա����ʱ��������Ϻ�grid��ʾ����Ȼ��֮ǰ�����Ա��Ϣ�Ĵ���
		try {
			List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
			if(list == null ||list.isEmpty()){
				this.setNextEventName("memberGrid.dogridquery");
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setLeaderBtn.onclick")
	public int setLeader() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫ������û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int count = choosePerson("memberGrid",false);
		if(count == 0){
			this.setMainMessage("��ѡ����Ҫ����Ϊ����Ա�ĳ�Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doSetLeader");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ������(confirm���ʹ���)
		if(count == 1){
			this.setMainMessage("ȷ��Ҫ���ó�Ա����Ϊ����Ա�");
		}
		if(count >1){
			this.setMainMessage("ȷ��Ҫ����"+count+"����Ա����Ϊ����Ա�");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doSetLeader")
	@Transaction
	public void doSetLeader() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		try {
			boolean result = PrivilegeManager.getInstance().getIGroupControl().setGroupManager(groupid, choosePersonIds("memberGrid"));
			if(result){
				this.setMainMessage("����Ա���óɹ�");
				this.createPageElement("memberGrid", ElementType.GRID, false).reload();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage(e.getMessage());
			System.out.println("***���ù���Աʧ�ܣ�"+e.getMessage());
		}
		
	}
	
	
	@PageEvent("cancelLeaderBtn.onclick")
	public int cancelLeader() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫ������û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int count = choosePerson("memberGrid",false);
		if(count == 0){
			this.setMainMessage("��ѡ����Ҫȡ������Ա�Ĺ���Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doCancelLeader");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		if(count == 1)
			this.setMainMessage("ȷ��Ҫȡ���ù���Ա�ʸ��");
		if(count >1)
			this.setMainMessage("ȷ��Ҫȡ����"+count+"������Ա�ʸ��");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doCancelLeader")
	@Transaction
	public int doCancelLeader() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		try {
			boolean result = PrivilegeManager.getInstance().getIGroupControl().cancelGroupManager(groupid, choosePersonIds("memberGrid"));
			if(result){
				this.setMainMessage("����Աȡ���ɹ�");
				this.createPageElement("memberGrid", ElementType.GRID, false).reload();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage(e.getMessage());
			System.out.println("***����Աȡ��ʧ�ܣ�"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("removeUserBtn.onclick")
	public int removeUser() throws RadowException{
		int count = choosePerson("memberGrid",false);
		if(count == 0){
			this.setMainMessage("��ѡ����Ҫ�Ƴ��ĳ�Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doRemoveUser");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		if(count == 1){
			this.setMainMessage("ȷ��Ҫ�Ƴ��ó�Ա�");
		}
		if(count >1){
			this.setMainMessage("ȷ��Ҫ�Ƴ���"+count+"����Ա�");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doRemoveUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doRemoveUser() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String getuserids = choosePersonIds("memberGrid");
		String[] userids = getuserids.split(",");
		try {
//			int type = checkInOneGroup(userids);
//			if(type == EventRtnType.FAILD){
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			boolean result = PrivilegeManager.getInstance().getIGroupControl().removeUserFromGroup(groupid, choosePersonIds("memberGrid"));
			if(result){
				this.setMainMessage("�����Ƴ��û��ɹ�");
				List list = PrivilegeManager.getInstance().getIGroupControl().getGroupMember(groupid, GroupHelper.GROUP_ALL_MEMBER);
				if(list == null || list.isEmpty()){//�����Ա���Ƴ����ʱ����ˢ��
					this.createPageElement("memberGrid", ElementType.GRID, false).reload();
				}else{
					this.createPageElement("memberGrid", ElementType.GRID, false).reload();
				}
			}
		} catch (PrivilegeException e) {
			this.setMainMessage(e.getMessage());
			System.out.println("***�����Ƴ��û�ʧ�ܣ�"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
//	@PageEvent("createUserBtn.onclick")
//	public int btn2OnClick() throws RadowException {
//		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(groupid == null || groupid.trim().equals("")){
//			this.setMainMessage("��ѡ����Ҫ��������֯");
//		}else{
//			this.openWindow("createUserWin", "pages.sysmanager.user.AddWindow");
//			this.setRadow_parent_data(this.getPageElement("checkedgroupid").getValue());
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	@PageEvent("dogridgrant")
	public int updateOnClick(String username) throws RadowException {
		this.openWindow("updateUserWin", "pages.sysmanager.user.FWindow");
		this.setRadow_parent_data(username);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("dogridunlock")
	public int unlockOnClick(String loginname) throws RadowException {
		this.addNextEvent(NextEventValue.YES, "doUnlockUser",loginname);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ������(confirm���ʹ���)
		this.setMainMessage("ȷ��Ҫ�����ó�Ա��");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doUnlockUser")
	@Transaction
	public int doUnlockUser(String loginname) throws RadowException, AppException{
		try {
			List list = PrivilegeManager.getInstance().getIUserControl()
					.queryByName(loginname, true);
			Session sess = PrivilegeManager.getInstance().getHbSession();
			UserVO user = (UserVO) list.get(0);
			user.setStatus("1");
			SmtUserselfcolumn col = (SmtUserselfcolumn) sess
					.createQuery(
							"from SmtUserselfcolumn where id.userid=:userid and id.key=:key")
					.setString("userid", user.getId())
					.setString("key", "PD_ERROR_COUNT").list().get(0);
			col.setValue("0");
			sess.update(col);
			sess.flush();
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e) {
			this.setMainMessage("�����û�ʧ�ܣ�"+e.getMessage());
		}
		this.createPageElement("memberGrid", ElementType.GRID, false).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogriddelete")
	public int RevokeUserBtnOnClick(String userid) throws RadowException{
		if(userid==null){
			throw new RadowException("���û������ڣ��޷�ɾ����");
		}
		
		this.addNextEvent(NextEventValue.YES, "doDeleteUser",userid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ������(confirm���ʹ���)
		this.setMainMessage("ȷ��Ҫɾ���ó�Ա��");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doDeleteUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doDeleteUser(String userid) throws RadowException{
		try {
			UserVO user = new UserVO();
			user.setId(userid);
			PrivilegeManager.getInstance().getIUserControl().deleteUser(user);
//			String groupid = this.getPageElement("checkedgroupid").getValue();
//			PrivilegeManager.getInstance().getIGroupControl().removeUserFromGroup(groupid, userid);
		} catch (PrivilegeException e) {
			this.setMainMessage("ɾ���û�ʧ�ܣ�"+e.getMessage());
		}
		this.createPageElement("memberGrid", ElementType.GRID, false).reload();
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
	
	private String choosePersonIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String userIds = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String userid = (String) this.getPageElement(grid).getValue("id", i);
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
	
	private int checkInOneGroup(String[] userids) throws PrivilegeException{
		String inOneGroupUserIds = "";
		String message = "";
		for(int i=0;i<userids.length;i++){
			List groups = PrivilegeManager.getInstance().getIGroupControl().getGroupsByUserId(userids[i]);
			if(groups.size() == 1){
				if(inOneGroupUserIds.equals("")){
					inOneGroupUserIds += userids[i];
				}else{
					inOneGroupUserIds += ","+userids[i];
				}
			}
		}
		if(!inOneGroupUserIds.equals("")){
			String[] oguserIds = inOneGroupUserIds.split(",");
			for(int i=0;i<oguserIds.length;i++){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(oguserIds[i]);
				if(message.equals("")){
					message += user.getLoginname();
				}else{
					message += ","+user.getLoginname();
				}
			}
		}
		if(!message.equals("")){
			this.setMainMessage("�뽫"+message+"ת�Ƶ�������֯��,����ִ���Ƴ�����");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() {

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("reset")
	public int reset(String userid) throws RadowException{
		this.addNextEvent(NextEventValue.YES, "doReset",userid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ������(confirm���ʹ���)
		this.setMainMessage("ȷ��Ҫ���øó�Ա���룿");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("doReset")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int realreset(String id) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		SmtUser smtUser  = (SmtUser) sess.load(SmtUser.class, id);
		String passwd=GlobalNames.sysConfig.get("RESET_PASSWD");
		//smtUser.setPasswd(MD5.MD5(passwd));

		
		
		
		

		UserVO user = new UserVO();
		user.setOwnerId(smtUser.getOwnerId());
		user.setLoginname(smtUser.getLoginname());
		user.setId(smtUser.getId());
		user.setStatus(smtUser.getStatus());
		user.setDesc(smtUser.getDesc());
		user.setName(smtUser.getName());
		user.setPasswd(MD5.MD5(passwd));

		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e) {
			this.isShowMsg=true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setMainMessage("��������ɹ�,����Ϊ��"+passwd);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("addUser.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int addUser() throws RadowException, AppException {
		
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.equals("")){
			this.setMainMessage("��ѡ����Ҫ������û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			Grid grid = (Grid) getPageElement("memberGrid");
			grid.addRow();
		} catch (RadowException e) {
			e.printStackTrace();
			this.isShowMsg = true;
			this.setMainMessage(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	@PageEvent("save.onclick")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int save() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		
		List<HashMap<String,String>> list=this.getPageElement("memberGrid").getStringValueList();
		int count=0;
		for (int i=0; i<list.size(); i++){
			HashMap<String,String> map =list.get(i);
			String checked = map.get("logchecked");
			if(checked.equalsIgnoreCase("1")||checked.equalsIgnoreCase("true")){
				count++;
				String id=map.get("id");
				String loginname=map.get("loginname");
				String name=map.get("name");
				String desc=map.get("desc");
				String isleader=map.get("isleader");
				String status=map.get("status");
				String dept=map.get("dept");
				if(id!=null&&!id.equals("")){
					//�޸�
					SmtUser smtUser  = (SmtUser) sess.load(SmtUser.class, id);
					UserVO user = new UserVO();
					user.setOwnerId(smtUser.getOwnerId());
					user.setLoginname(loginname);
					user.setId(id);
					if(status!=null&&!status.equals("")){
						user.setStatus(status);
					}else{
						user.setStatus("1");
					}
					
					user.setDesc(desc);
					user.setName(name);
					user.setPasswd(smtUser.getPasswd());
					user.setDept(dept);

					try {
						PrivilegeManager.getInstance().getIUserControl().updateUser(user);
					} catch (PrivilegeException e) {
						this.isShowMsg=true;
						this.setMainMessage(e.getMessage());
						return EventRtnType.FAILD;
					}
				}else{
					//����
					try {
						List list_user = PrivilegeManager.getInstance().getIUserControl().queryByName(loginname, true);
						if(list_user.size()>0){
							throw new RadowException("�õ�¼���Ѿ�����:"+loginname);
						}						
					} catch (Exception e) {
						throw new RadowException(e.getMessage());
					}
					
					
					Date createdate = new Date();
					UserVO user = new UserVO();
					user.setCreatedate(createdate);
					user.setLoginname(loginname);
					if(status!=null&&!status.equals("")){
						user.setStatus(status);
					}else{
						user.setStatus("1");
					}
					user.setName(name);
					user.setPasswd(loginname);
					user.setDesc(desc);
					user.setDept(dept);
					String groupid =this.getPageElement("checkedgroupid").getValue();
					String userid = "";
					try {
						PrivilegeManager.getInstance().getIUserControl().saveUser(user);
						List list_user = PrivilegeManager.getInstance().getIUserControl().queryByName(user.getLoginname(), true);
						userid = ((UserVO)list_user.get(0)).getId();
						PrivilegeManager.getInstance().getIGroupControl().addUserToGroup(groupid, userid);
						

					} catch (PrivilegeException e) {
						throw new RadowException(e.getMessage());
					}
				}
			}
		}
		
		if(count==0){
			throw new RadowException("��ѡ����Ҫ�������Ա��Ϣ");
		}
		
		this.setMainMessage("����ɹ�");

		this.setNextEventName("reload");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("reload")
	public int reload() throws RadowException{

		this.createPageElement("memberGrid", ElementType.GRID, false).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@GridDataRange(GridData.allrow)
	@PageEvent("memberGrid.afteredit")
	@NoRequiredValidate
	@AutoNoMask
	public int ab02onchange() throws RadowException{ 
//		try {
//			HBUtil.getHBSession().getSession().beginTransaction();
//			PrivilegeManager pm = PrivilegeManager.getInstance(HBUtil.getHBSession().getSession());
//			pm.reSetHashCodeByTabName("SmtUser");
//			HBUtil.getHBSession().getSession().getTransaction().commit();
//		} catch (HibernateException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (PrivilegeException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		List<HashMap<String, Object>> list_m = this.getPageElement("memberGrid").getValueList();
		HashMap<String, Object> map = list_m.get(this.getPageElement("memberGrid").getCueRowIndex());
		map.put("logchecked", "1");
		String loginname = map.get("loginname").toString();
		String id = map.get("id").toString();
		if(id==null||id.equals("")){
			try {
				List list = PrivilegeManager.getInstance().getIUserControl().queryByName(loginname, true);
				if(list.size()==0){
					return EventRtnType.NORMAL_SUCCESS;
				}
				this.setMainMessage("�õ�¼���Ѿ�����:"+loginname);
			} catch (Exception e) {
				throw new RadowException(e.getMessage());
			}
		}else{
			try {
				List<UserVO> list = PrivilegeManager.getInstance().getIUserControl().queryByName(loginname, true);
				if(list.size()==1){
					UserVO vo=(UserVO)list.get(0);
					if(!vo.getId().equals(id)){
						this.setMainMessage("�õ�¼���Ѿ�����:"+loginname);
					}
				}
				
			} catch (Exception e) {
				throw new RadowException(e.getMessage());
			}
		}
		this.getPageElement("memberGrid").setValue(JSONArray.fromObject(list_m).toString());

		return EventRtnType.NORMAL_SUCCESS;
	}
}
