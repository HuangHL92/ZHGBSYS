package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class MechanismRemWindowPageModel extends PageModel {
	private List aclList = null;
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("remdeptGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("remdeptGrid.dogridquery")
	@NoRequiredValidate
	public int doDeptQuery(int start,int limit) throws RadowException {
		String userid = this.getRadow_parent_data();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String cueUsername = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
		List alist = new ArrayList();
		String b0111 = this.getPageElement("searchB0111").getValue();
		String b0121 = this.getPageElement("searchB0121").getValue();
		String b0101 = this.getPageElement("searchB0101").getValue();
		StringBuffer str = new StringBuffer();
		if ("admin".equals(cueUsername)){
			str.append("FROM B01 S where 1=1");
		}else{
		str.append("FROM B01 S WHERE S.b0111 IN(SELECT t.b0111 FROM UserDept t WHERE userid='"+cueUserid+"')");
		}
		str.append(" and S.b0111 IN(SELECT t.b0111 FROM UserDept t WHERE userid='"+userid+"')");
		if(!("".equals(b0111)||b0111==null)){
			str.append(" AND S.b0111='"+b0111+"'");
		}
		if(!("".equals(b0121)||b0121==null)){
			str.append(" AND S.b0121='"+b0121+"'");
		}
		if(!("".equals(b0101)||b0101==null)){
			str.append(" AND S.b0101='"+b0101+"'");
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
		//checked("deptGrid",userid);
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("remove.onclick")
	public int doSave() throws RadowException{
		String userid = this.getRadow_parent_data();
		String b0111s = chooseDeptIds("remdeptGrid");
		//String removeb0111s = removeDeptIds("deptGrid");
		String[] deptids = b0111s.split(",");
		//String[] removedeptids = removeb0111s.split(",");
		HBSession sess = HBUtil.getHBSession();
		for(int i=0;i<deptids.length;i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			if(deptids[i].equals("")){
				continue;
			}
			String hql = " from UserDept t where t.b0111='"+deptids[i]+"'";
			UserDept ud2 = (UserDept)sess.createQuery(hql).list().get(0);
			sess.delete(ud2);
			ts.commit();
		}
		
		/*for(int i=0;i<removedeptids.length;i++){
			Transaction ts1 = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			if(findUserId(removedeptids[i])==false){
				continue;
			}
			String hql = " from UserDept t where t.b0111='"+removedeptids[i]+"'";
			UserDept ud2 = (UserDept)sess.createQuery(hql).list().get(0);
			sess.delete(ud2);
			ts1.commit();
		}*/
		this.setMainMessage("解除授权成功！");
		this.closeCueWindowByYes("mechanismRemWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*public List<UserDept> findUserDept(String cueUserid){
		HBSession sess = HBUtil.getHBSession();
		String hql = " from UserDept t where t.userid='"+cueUserid+"'";
		List<UserDept> list = sess.createQuery(hql).list();
		return list;
	}
	public B01 findB0111(String B0111){
		HBSession sess = HBUtil.getHBSession();
		String hql = " from B01 t where t.b0111='"+B0111+"'";
		B01 b = (B01)sess.createQuery(hql).list();
		return b;
	}*/
	@PageEvent("find.onclick")
	public int findDept(){
		this.setNextEventName("remdeptGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public boolean findUserId(String b0111){
		HBSession sess = HBUtil.getHBSession();
		Object userid = sess.createSQLQuery("SELECT a.userid FROM COMPETENCE_USERDEPT a WHERE a.b0111='"+b0111+"'").uniqueResult();
		if(userid==null){
			return false;
		}
		return true;
	}
	
	private String chooseDeptIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String b0111s = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String b0111 = (String) this.getPageElement(grid).getValue("b0111", i);
				if(b0111s.equals("")){
					b0111s += b0111;
				}
				else{
					b0111s += ","+b0111;
				}
			}
		}
		return b0111s;
	}
	/*private String removeDeptIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String b0111s = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(false)){
				String b0111 = (String) this.getPageElement(grid).getValue("b0111", i);
				if(b0111s.equals("")){
					b0111s += b0111;
				}
				else{
					b0111s += ","+b0111;
				}
			}
		}
		return b0111s;
	}
	*/
	
}
