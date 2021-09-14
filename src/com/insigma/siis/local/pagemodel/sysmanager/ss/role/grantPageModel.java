package com.insigma.siis.local.pagemodel.sysmanager.ss.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class grantPageModel extends PageModel {

	private final static int ON_ONE_CHOOSE=-1;
	
	private final static int CHOOSE_OVER_TOW=-2;
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("usergrid.dogridquery");
		this.setNextEventName("groupgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("usergrid.dogridquery")
	public int usergridDoQuery(int start, int limit) throws RadowException {
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		HashMap params = new HashMap();
		PageElement pe = this.getPageElement("loginname");
		PageElement pe2 = this.getPageElement("name");
		PageElement pe3 = this.getPageElement("gsearch");
		boolean usernameBoolean = (pe2 != null && !pe2.getValue().equals(""));
		boolean loginnameBoolean = (pe != null && !pe.getValue().equals(""));
		boolean gsearchBoolean = (pe3 != null && !pe3.getValue().equals(""));
		List list = new ArrayList();
		list.add("loginname desc");
//		if(loginnameBoolean) {
//			params.put("loginname", pe.getValue());
//		}
//		if(usernameBoolean) {
//			params.put("name", pe2.getValue());
//		}
//		if(gsearchBoolean) {
//			String aaa148 = DataPermissionUtil.getCueUserAAA148();
//			HBSession sess = HBUtil.getHBSession();
//			String comparehql = "from Aaa1 aaa1 where aaa1.id.aaa148 in("+aaa148+") and aaa1.id.aab301='"+pe3.getValue()+"'";
//			if(sess.createQuery(comparehql).list().size()>0) {
//				aaa148 =pe3.getValue();
//			}
//			params.put("gsearch", aaa148);
//		}else {
//			params.put("gsearch",DataPermissionUtil.getCueUserAAA148());
//		}
		if (loginnameBoolean) {
			params.put("loginname", pe.getValue());
			if (usernameBoolean) {
				params.put("name", pe2.getValue());
			}
		} else {
			if (usernameBoolean) {
				params.put("name", pe2.getValue());
			}
		}
		params.put("querysql", list);
		
		try {
			this.setSelfDefResData(PrivilegeManager.getInstance().getIUserControl().pageQueryByUserVO(cueUser, start, limit, params));
		} catch (PrivilegeException e) {
			this.isShowMsg = true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.SPE_SUCCESS;
		}
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("query.onclick")
	public int Query() throws RadowException{
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("usergrid.rowdbclick")
	public int userdbclick() throws RadowException{
		String userid = this.getPageElement("usergrid").getValue("id").toString();
		try {
			List list = PrivilegeManager.getInstance().getIRoleControl().findRoleByObject(userid);
			if(list.isEmpty()){
				this.setMainMessage("该用户没有持有角色");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.openWindow("win3", "pages.sysmanager.ss.role.ShowGrantWindow");
		this.setRadow_parent_data(userid+",userid");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("groupgrid.dogridquery")
	public int groupgridDoQuery(int start, int limit) throws RadowException {
		HashMap params = new HashMap();
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		String parentid = this.getPageElement("gsearch").getValue();
		if(parentid!=null&&!parentid.trim().equals("")){
			params.put("parentid", parentid);
		}
		try {
				this.setSelfDefResData(PrivilegeManager.getInstance().getIGroupControl().pageQueryByUser(cueUser, start, limit, params));
			}
		catch (PrivilegeException e) {
				this.isShowMsg = true;
				this.setMainMessage(e.getMessage());
				return EventRtnType.SPE_SUCCESS;
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("groupgrid.rowdbclick")
	public int groupdbclick() throws RadowException{
		String groupid = this.getPageElement("groupgrid").getValue("id").toString();
		try {
			List list = PrivilegeManager.getInstance().getIRoleControl().findRoleByObject(groupid);
			if(list.isEmpty()){
				this.setMainMessage("该组织没有持有角色");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.openWindow("win3", "pages.sysmanager.ss.role.ShowGrantWindow");
		this.setRadow_parent_data(groupid+",groupid");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("groupEvent")
	@AutoNoMask
	public int dogroupQuery() throws RadowException{
		this.setNextEventName("groupgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("save.onclick")
	public int douserGrigGrant() throws RadowException{
		int i = choose("usergrid","usercheck");
		int k = choose("groupgrid","groupcheck");
		if (i == ON_ONE_CHOOSE && k==ON_ONE_CHOOSE) {
			this.setMainMessage("请先选中要授权的对象");
			return EventRtnType.FAILD;
		}
		if(i!=ON_ONE_CHOOSE && k!=ON_ONE_CHOOSE){
			this.setMainMessage("只能选择一个用户或者组进行操作，请您请选择一个用户或者组进行操作");
			return EventRtnType.FAILD;
		}
		if(i==CHOOSE_OVER_TOW||k==CHOOSE_OVER_TOW){
			StringBuffer ids = new StringBuffer();
			PageElement pe = this.getPageElement("usergrid");
			if(pe!=null){
				List<HashMap<String, Object>> list = pe.getValueList();
				for(int j=0;j<list.size();j++){
					HashMap<String, Object> map = list.get(j);
					Object usercheck = map.get("usercheck");
					if(usercheck.equals(true)){
						ids.append(this.getPageElement("usergrid").getValue("id",j).toString());
						ids.append(",");
					}
				}
			}
			PageElement grouppe = this.getPageElement("groupgrid");
			if(grouppe!=null){
				List<HashMap<String, Object>> grouplist = grouppe.getValueList();
				for(int j=0;j<grouplist.size();j++){
					HashMap<String, Object> map = grouplist.get(j);
					Object usercheck = map.get("groupcheck");
					if(usercheck.equals(true)){
						ids.append(this.getPageElement("groupgrid").getValue("id",j).toString());
						ids.append(",");
					}
				}
			}
			this.setRadow_parent_data(ids.toString());
			CommonQueryBS.systemOut(ids.toString());
			this.openWindow("win1",	"pages.sysmanager.ss.role.AddGrant");//事件处理完后的打开窗口事件
			return EventRtnType.NORMAL_SUCCESS;
		}
		String id = null;
		StringBuffer ids = new StringBuffer();
		PageElement userpe = this.getPageElement("usergrid");
		PageElement grouppe = this.getPageElement("groupgrid");
		if(userpe!=null){
			List<HashMap<String, Object>> userlist = userpe.getValueList();
			for(int user=0;user<userlist.size();user++){
				HashMap<String, Object> map = userlist.get(user);
				Object usercheck = map.get("usercheck");
				if(usercheck.equals(true)){
					id = this.getPageElement("usergrid").getValue("id",user).toString();
					this.openWindow("win1",	"pages.sysmanager.ss.role.AddGrant");//事件处理完后的打开窗口事件
					this.setRadow_parent_data(id);
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		List<HashMap<String, Object>> grouplist = grouppe.getValueList();
		this.openWindow("win1",	"pages.sysmanager.ss.role.AddGrant");//事件处理完后的打开窗口事件
		this.setRadow_parent_data(this.getPageElement("groupgrid").getValue("id", k).toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("remove.onclick")
	public int douserRemoveGrigGrant() throws RadowException{
		int i = choose("usergrid","usercheck");
		int k = choose("groupgrid","groupcheck");
		if (i == ON_ONE_CHOOSE && k==ON_ONE_CHOOSE) {
			this.setMainMessage("请先选中要授权的对象");
			return EventRtnType.FAILD;
		}
		if(i==CHOOSE_OVER_TOW||k==CHOOSE_OVER_TOW){
			this.setMainMessage("只能选择一个用户或者组进行操作，请您请选择一个用户或者组进行操作");
			return EventRtnType.FAILD;
//			StringBuffer ids = new StringBuffer();
//			PageElement pe = this.getPageElement("usergrid");
//			if(pe!=null){
//				List<HashMap<String, Object>> list = pe.getValueList();
//				for(int j=0;j<list.size();j++){
//					HashMap<String, Object> map = list.get(j);
//					Object usercheck = map.get("usercheck");
//					if(usercheck.equals(true)){
//						ids.append(this.getPageElement("usergrid").getValue("id",j).toString());
//						ids.append(",");
//					}
//				}
//			}
//			PageElement grouppe = this.getPageElement("groupgrid");
//			if(grouppe!=null){
//				List<HashMap<String, Object>> grouplist = grouppe.getValueList();
//				for(int j=0;j<grouplist.size();j++){
//					HashMap<String, Object> map = grouplist.get(j);
//					Object usercheck = map.get("groupcheck");
//					if(usercheck.equals(true)){
//						ids.append(this.getPageElement("groupgrid").getValue("id",j).toString());
//						ids.append(",");
//					}
//				}
//			}
//			this.setRadow_parent_data(ids.toString());
//			System.out.println(ids.toString());
//			this.openWindow("win2",	"pages.sysmanager.role.RemoveGrant");//事件处理完后的打开窗口事件
//			return EventRtnType.NORMAL_SUCCESS;
		}
		if(i != ON_ONE_CHOOSE && k!=ON_ONE_CHOOSE){
			this.setMainMessage("只能选择一个用户或者组进行操作，请您请选择一个用户或者组进行操作");
			return EventRtnType.FAILD;
		}
		String id = null;
		StringBuffer ids = new StringBuffer();
		PageElement userpe = this.getPageElement("usergrid");
		PageElement grouppe = this.getPageElement("groupgrid");
		if(userpe!=null){
			List<HashMap<String, Object>> userlist = userpe.getValueList();
			for(int user=0;user<userlist.size();user++){
				HashMap<String, Object> map = userlist.get(user);
				Object usercheck = map.get("usercheck");
				if(usercheck.equals(true)){
					id = this.getPageElement("usergrid").getValue("id",user).toString();
					this.openWindow("win2",	"pages.sysmanager.ss.role.RemoveGrant");//事件处理完后的打开窗口事件
					this.setRadow_parent_data(id);
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		List<HashMap<String, Object>> grouplist = grouppe.getValueList();
		this.openWindow("win2",	"pages.sysmanager.ss.role.RemoveGrant");//事件处理完后的打开窗口事件
		this.setRadow_parent_data(this.getPageElement("groupgrid").getValue("id", k).toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 私有方法，是否选中用户
	 * 
	 * @throws RadowException
	 */
	private int choose(String gridid,String checkId) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get(checkId);
			if(check1==null){
				continue;
			}
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// 没有选中任何用户
		}
		if (result > 2) {
			return CHOOSE_OVER_TOW;// 选中多于一个用户
		}
		return number;// 选中第几个用户
	}
}
