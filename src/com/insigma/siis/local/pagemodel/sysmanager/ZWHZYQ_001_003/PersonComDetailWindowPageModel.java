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
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.entity.UserPerson;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PersonComDetailWindowPageModel extends PageModel {
	private List aclList = null;
	private static int index = 0;
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("personGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("personGrid.dogridquery")
	@NoRequiredValidate
	public int doPersonQuery(int start,int limit) throws RadowException {
		String ids = this.getRadow_parent_data();
		String[] id = ids.split(","); 
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String cueUsername = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
		List alist = new ArrayList();
		HBSession session = HBUtil.getHBSession();
		StringBuffer str1 = new StringBuffer();
		String sql = "select a.a0000 from A02 a where a.a0201b='"+id[0]+"'";
		List list = session.createSQLQuery(sql).list();
		if(list!=null){
		for(int i=0;i<list.size();i++){
			if(i==0){
				str1.append("'"+list.get(i)+"'");
			}else{
				str1.append(",'"+list.get(i)+"'");
			}
		}
		}else{
			str1.append("''");
		}
		String a0184 = this.getPageElement("searchA0184").getValue();
		String a0101 = this.getPageElement("searchA0101").getValue();
		StringBuffer str = new StringBuffer();
		str.append("FROM A01 b where 1=1");
		if(!str1.toString().equals("")){
			str.append(" and b.a0000 in("+str1.toString()+")");//
		}
		if(index==1){
		str.append(" and b.a0000 not IN(SELECT t.a0000 FROM UserPerson t WHERE t.userid = '"+id[1]+"')");
		}
		if(index==2){
		str.append(" and b.a0000 IN(SELECT t.a0000 FROM UserPerson t WHERE t.userid = '"+id[1]+"')");
		}
		if(!("".equals(a0184)||a0184==null)){
			str.append(" AND S.a0184 like '"+a0184+"%'");
		}
		if(!("".equals(a0101)||a0101==null)){
			str.append(" AND S.a0101 like '"+a0101+"%'");
		}
		String hql = str.toString();
		CommonQueryBS.systemOut(hql);
		Query query = session.createQuery(hql);
		alist = query.list();
		if(alist == null || alist.isEmpty()){
			this.setSelfDefResData(this.getPageQueryData(new ArrayList(), start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		this.setSelfDefResData(this.getPageQueryData(alist, start, limit));
		//checked("deptGrid",userid);
		index=0;
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("visible.onclick")
	public int Invisible() throws RadowException{
		String ids = this.getRadow_parent_data();
		String[] id = ids.split(",");
		String userid = id[1];
		String a0000 = choosePersonIds("personGrid");
		String[] a0000s = a0000.split(",");
		HBSession sess = HBUtil.getHBSession();
		for(int i=0;i<a0000s.length;i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			if(a0000s[i].equals("")){
				continue;
			}
			if(!findUserId(a0000s[i],userid)){
				continue;
			}
			String hql = " from UserPerson t where t.a0000='"+a0000s[i]+"' and t.userid='"+userid+"'";
			UserPerson up2 = (UserPerson)sess.createQuery(hql).list().get(0);
			sess.delete(up2);
			ts.commit();
		}
		this.setMainMessage("解除授权成功！");
		this.closeCueWindowByYes("personComDetailWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("Invisible.onclick")
	public int Visible() throws RadowException{
		String ids = this.getRadow_parent_data();
		String[] id = ids.split(",");
		String userid = id[1];
		String a0000 = choosePersonIds("personGrid");
		String[] a0000s = a0000.split(",");
		HBSession sess = HBUtil.getHBSession();
		for(int i=0;i<a0000s.length;i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			if(a0000s[i].equals("")){
				continue;
			}
			if(findUserId(a0000s[i],userid)){
				continue;
			}
			UserPerson up = new UserPerson();
			up.setA0000(a0000s[i]);
			up.setUserid(userid);
			up.setUserpersonid(java.util.UUID.randomUUID().toString());
			sess.save(up);
			ts.commit();
		}
		this.setMainMessage("授权成功！");
		this.closeCueWindowByYes("personComDetailWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("find.onclick")
	public int findDept(){
		index=1;
		this.setNextEventName("personGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("find2.onclick")
	public int findDept2(){
		index=2;
		this.setNextEventName("personGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public boolean findUserId(String a0000,String userid){
		HBSession sess = HBUtil.getHBSession();
		Object userids = sess.createSQLQuery("SELECT a.userid FROM COMPETENCE_USERPERSON a WHERE a.a0000='"+a0000+"' and a.userid='"+userid+"'").uniqueResult();
		if(userids==null){
			return false;
		}
		return true;
	}
	
	private String choosePersonIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String a0000s = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String a0184 = (String) this.getPageElement(grid).getValue("a0184", i);
				String hql = "FROM A01 a where a.a0184='"+a0184+"'";
				List<A01> list1= new ArrayList<A01>();
				HBSession sess = HBUtil.getHBSession();
				list1 = sess.createQuery(hql).list();
				String a0000 = list1.get(0).getA0000();
				if(a0000s.equals("")){
					a0000s += a0000;
				}
				else{
					a0000s += ","+a0000;
				}
			}
		}
		return a0000s;
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
}
