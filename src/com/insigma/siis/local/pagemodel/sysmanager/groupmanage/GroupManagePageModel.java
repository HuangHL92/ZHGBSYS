package com.insigma.siis.local.pagemodel.sysmanager.groupmanage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.GlobalNames;

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
			}else{
				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.name,a.AAA005 FROM AA01 a,smt_group b WHERE a.AAA001='AREA_ID' and a.AAA005=b.DISTRICTCODE ").uniqueResult();
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
	
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		GroupVO group = new GroupVO();
		try {
			group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
			if(group == null) throw new RadowException("�鲻�����û���");
			if(group.getOwnerId() != null){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(group.getOwnerId());
				group.setOwnerId(user.getLoginname());
			}
			if(group.getParentid() != null){
				GroupVO pgroup = PrivilegeManager.getInstance().getIGroupControl().findById(group.getParentid());
				group.setParentid(pgroup.getName());
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		if(group.getStatus().equals("0")){
			group.setStatus("��Ч");
		}
		if(group.getStatus().equals("1")){
			group.setStatus("��Ч");
		}
		PMPropertyCopyUtil.copyObjValueToElement(group, this);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException, RadowException {
		String node = this.getParameter("node");
		List<GroupVO> list = PrivilegeManager.getInstance().getIGroupControl()
				.findByParentId(node);
		// ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<GroupVO> choose = new ArrayList<GroupVO>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser()
				.getId();
		List<GroupVO> groups = PrivilegeManager.getInstance()
				.getIGroupControl().findGroupByUserId(cueUserid);
		boolean topuser=false; 
		String areaid=areaInfo.get("areaid").toString();
		for (int i = 0; i < groups.size(); i++) {
			GroupVO vo=(GroupVO)groups.get(i);
			String groupid=vo.getId();
			if(groupid.equals(areaid)){
				topuser=true;
			}
			for (int j = 0; j < groups.size(); j++) {
				if (groups.get(j).getId().equals(groups.get(i).getParentid())) {
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		
		
		if (!groups.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				
				for (int j = 0; j < groups.size(); j++) {
					if (groups.get(j).getId().equals(list.get(i).getId())) {
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if (equel) {
			list = choose;
		}
		// ��
		if(topuser==false && node.equals(areaInfo.get("areaid"))){
			list=PrivilegeManager.getInstance()
			.getIGroupControl().findGroupByUserId(cueUserid);
		}
		
		
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (GroupVO group : list) {
				if (i == 0 && last == 1) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				} else if (i == (last - 1)) {
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
	
	@PageEvent("searchBtn.ontriggerclick")
	public int searchOnClick(String name) throws RadowException{
		if(name == null ||name.trim().equals("")){
			this.setMainMessage("�������û�������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("groupname", name);
		List list = new ArrayList();
		try {
			list = PrivilegeManager.getInstance().getIGroupControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(), 0, -1, params);
			if(list.isEmpty()){
				params.clear();
				params.put("name", name);
				List lista = PrivilegeManager.getInstance().getIGroupControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(), 0, 5, params);
				if(lista.isEmpty()){
					this.setMainMessage("������Ƶ��û��鲻����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				this.openWindow("groupWin", "pages.sysmanager.groupmanage.GroupShowWindow");
				this.setRadow_parent_data(name);
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("��ѯ�ɼ���ʧ�ܣ�"+e.getMessage());
		}
		GroupVO group = (GroupVO)list.get(0);
		query(group.getId());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("searchAllBtn.onclick")
	public int searchAllBtn() throws RadowException{
		List lista = new ArrayList();
		try {
			lista = PrivilegeManager.getInstance().getIGroupControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(), 0, 5, null);
		} catch (PrivilegeException e) {
			this.setMainMessage("��ѯ�ɼ���ʧ�ܣ�"+e.getMessage());
		}
		if(lista.isEmpty()){
			this.setMainMessage("���û�����ڻ������ɼ��ķ�Χ��ѯ�����û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("groupWin", "pages.sysmanager.groupmanage.GroupShowWindow");
		this.setRadow_parent_data("searchAll");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveBtn.onclick")
	public int saveBtnOnClick() throws RadowException{
		if(this.getPageElement("id").getValue().equals("")){
			this.setMainMessage("��ѡ����Ҫ�޸ĵ��û���");
			return EventRtnType.NORMAL_SUCCESS;
		}	
		this.openWindow("addWin", "pages.sysmanager.groupmanage.GroupAddWindow"); //�¼��������Ĵ򿪴����¼�
			
		this.setRadow_parent_data(this.getPageElement("id").getValue());
		PageElement pe= this.getPageElement("addsuccess");
		if(pe != null){
			this.reloadPage();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editBtn.onclick")
	public int doGroupEdit() throws RadowException{
		String id = this.getPageElement("id").getValue();
		if(id.equals("")){
			this.setMainMessage("��ѡ����Ҫ�޸ĵ��û���");
		}else{
			this.openWindow("uptWin", "pages.sysmanager.groupmanage.GroupUptWindow"); //�¼��������Ĵ򿪴����¼�
			this.setRadow_parent_data(id+",groupManage");//���ø�ҳ������ݣ����Ӵ���window����ʹ��(�����Ӵ����п���get����ֵ)
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("revokeBtn.onclick")
	public int revokeGroup() throws RadowException{
		String groupId = this.getPageElement("id").getValue();
		if(groupId.equals("")){
			this.setMainMessage("��ѡ����Ҫע�����û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doRevokeGroup",groupId);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ��ע��������");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doRevokeGroup")
	@Transaction
	public int doRevokeGroup(String groupId) throws RadowException{
		try {
			boolean state = PrivilegeManager.getInstance().getIGroupControl().revokeGroup(groupId);
			if(state){
				this.setMainMessage("ע���û���ɹ�");
				this.query(groupId);
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("ע���û���ʧ�ܣ�"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("reuseBtn.onclick")
	public int reuseGroup() throws RadowException{
		String groupId = this.getPageElement("id").getValue();
		if(groupId.equals("")){
			this.setMainMessage("��ѡ����Ҫ����Ϊ��Ч���û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doReuseGroup",groupId);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("ȷ��Ҫ����������Ϊ��Ч�");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doReuseGroup")
	@Transaction
	public int doReuseGroup(String groupId) throws RadowException{
		try {
			boolean state = PrivilegeManager.getInstance().getIGroupControl().reuseGroup(groupId);
			if(state){
				this.setMainMessage("�����û�����Ч�ɹ�");
				this.query(groupId);
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("�����û�����Чʧ�ܣ�"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteBtn.onclick")
	public int deleteGroup() throws RadowException{
		String groupId = this.getPageElement("id").getValue();
		if(groupId.equals("")){
			this.setMainMessage("��ѡ����Ҫɾ�����û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "doDeleteGroup",groupId);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ��ɾ��������");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doDeleteGroup")
	@Transaction
	public int doDeleteGroup(String groupid) throws RadowException{
		try {
			boolean state = PrivilegeManager.getInstance().getIGroupControl().deleteGroup(groupid);
			if(state){
				this.setMainMessage("ɾ���û���ɹ�");
				this.reloadPage();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("ɾ��ʧ�ܣ�"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() {
		//this.isShowMsg = true;
		return EventRtnType.NORMAL_SUCCESS;
	}

}
