package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;




import org.hibernate.Query;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

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
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel;

public class CompetenceManagePageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static int flag = 0;//用于分辨是点击的查询按钮，还是点击的用户组树
	public static int flag2 = 0;//用于解决上级节点为空则本节点显示不出的问题
	
	public CompetenceManagePageModel(){
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
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		/*this.getPageElement("searchGroupBtn").setValue(" ");*/
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		List alist = new ArrayList();
		String loginname = this.getPageElement("searchUserBtn").getValue();
		String name = this.getPageElement("searchUserNameBtn").getValue();
		String useful = this.getPageElement("useful").getValue();
		String isleader = this.getPageElement("isleader").getValue();
		StringBuffer str = new StringBuffer();
		str.append("FROM SmtUser S WHERE S.id IN(SELECT t.userid FROM SmtUsergroupref t WHERE groupid='"+groupid+"')");
		if(this.flag==1){
			if(!loginname.equals("")){
				str.append(" AND S.loginname like '"+loginname+"%'");
			}
			if(!name.equals("")){
				str.append(" AND S.name like '"+name+"%'");
			}
			if(!useful.equals("")){
				str.append(" AND S.status='"+useful+"'");
			}
			if(!isleader.equals("")){
				str.append(" AND S.isleader='"+isleader+"'");
			}
			this.flag=0;
		}
		String hql = str.toString();
		CommonQueryBS.systemOut(hql);
		HBSession session = HBUtil.getHBSession();
		Query query = session.createQuery(hql);
		alist = query.list();
		if(alist == null || alist.isEmpty()){
			this.setSelfDefResData(this.getPageQueryData(new ArrayList(), start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		this.setSelfDefResData(this.getPageQueryData(alist, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		List<GroupVO> list  = new ArrayList<GroupVO>();
		String node = this.getParameter("node");
		/*if(this.flag2==0){	
			GroupVO g = PrivilegeManager.getInstance()
					.getIGroupControl().findById(pereaid);
			list.add(g);
			this.flag2=1;
		}else if(node.equals("G001")){
		list = PrivilegeManager.getInstance()
				.getIGroupControl().findByParentId(pereaid);
		}else{*/
		list = PrivilegeManager.getInstance()
					.getIGroupControl().findByParentId(node);
		/*}*/
		//只显示所在的组织及下级组织 不在组织中 则显示全部
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
		//。
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
	/**
	 * 用于弹出用户模块权限分配页面
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ModuleComBtn.onclick")
	public int ModuleComBtnClick() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		int choosed = choosePerson("memberGrid",false);
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else if(choosed>1){
			this.setMainMessage("一次只能修改一个用户信息");
		}else{
			String id = choosePersonIds("memberGrid");
		this.openWindow("ModuleComWin", "pages.sysmanager.ZWHZYQ_001_003.ModuleComWindow&roleid="
				+ id + "");
		this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 用于弹出用户机构权限分配页面
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("mechanismComBtn.onclick")
	public int MechanismComBtnClick() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		int choosed = choosePerson("memberGrid",false);
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else if(choosed>1){
			this.setMainMessage("一次只能修改一个用户信息");
		}else{
			String id = choosePersonIds("memberGrid");
		this.openWindow("mechanismComWin", "pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&userid="
				+ id + "");
		this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 用于弹出人员权限分配页面
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("perFindComBtn.onclick")
	public int personComBtnClick() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		int choosed = choosePerson("memberGrid",false);
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else if(choosed>1){
			this.setMainMessage("一次只能修改一个用户信息");
		}else{
			String id = choosePersonIds("memberGrid");
		this.openWindow("personComWin", "pages.sysmanager.ZWHZYQ_001_003.PersonComWindow&userid="
				+ id + "");
		this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
		return EventRtnType.NORMAL_SUCCESS;
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
	
	/**
	 * 用于弹出用户组权限分配页面
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("InfoComBtn.onclick")
	public int InfoComBtnClick() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		int choosed = choosePerson("memberGrid",false);
		if(isLeader()){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(groupid == null || groupid.trim().equals("")){
			this.setMainMessage("请选择需要操作的组织");
		}else if(choosed==0){
			this.setMainMessage("请选择需要操作的用户");
		}else if(choosed>1){
			this.setMainMessage("一次只能修改一个用户信息");
		}else{
			String id = choosePersonIds("memberGrid");
		this.openWindow("InfoComWin", "pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&userid="
				+ id + "");
		this.setRadow_parent_data(choosePersonIds("memberGrid"));
		}
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
	public boolean isLeader(){
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		return "0".equals(isleader);	
	}
	
	
	
	@Override
	public int doInit() {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
