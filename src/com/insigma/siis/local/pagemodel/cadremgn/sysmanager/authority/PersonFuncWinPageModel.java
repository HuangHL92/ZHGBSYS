package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.CheckTreeNode;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.tree.TreeUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.epsoft.util.Node;
import com.insigma.siis.local.epsoft.util.NodeUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PersonFuncWinPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		if(userid == null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("userid").setValue(userid);
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select t.username from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		if(obj!=null){
			this.getExecuteSG().addExecuteCode("document.getElementById('text11').innerText = '"+"当前用户 : "+obj+"';");
		}
		this.setNextEventName("contentList.dogridquery");
		this.setNextEventName("contentList2.dogridquery");
		return 0;
	}
	
	@PageEvent("contentList.dogridquery")
	@NoRequiredValidate         
	public int contentList10(int start,int limit) throws RadowException, AppException, PrivilegeException{
		/*String userid = this.getPageElement("userid").getValue();
		//获取上级管理员id
		String overUserid = NewRangePageModel.getOverUserID(userid);
		String sql = "";
		if("40288103556cc97701556d629135000f".equals(overUserid)){
			sql ="select f.functionid fid,f.title fname from Smt_Function f,Smt_Resource r, Smt_Business sm where f.resourceid = r.resourceid and r.status='1' "
					+ "and r.parent = '40287681624b5ada01624b6cabb7000b' and r.type = '0' and r.resourceid = sm.business_id order by to_number(sm.business_sortid)";
		}else{
			sql ="select f.functionid fid,f.title fname from Smt_Function f,Smt_Resource r, Competence_Usersmtbusiness c, Smt_Business sm where f.resourceid = r.resourceid and r.status='1' "
					+ "and r.parent = '40287681624b5ada01624b6cabb7000b' and r.type = '0' and f.functionid = c.businessid and c.userid = '"+overUserid+"' and r.resourceid = sm.business_id "
					+ "order by to_number(sm.business_sortid)";
		}*/
		String sql = "select '01' as fid,'任免表————家庭成员' as fname from dual";
		CommonQueryBS.systemOut("contentList------->"+sql);
		this.pageQuery(sql, "SQL", start, limit); //基本不做翻页
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("contentList2.dogridquery")
	@NoRequiredValidate         
	public int contentList11(int start,int limit) throws RadowException, AppException{
		String userid = this.getPageElement("userid").getValue();
		String sql ="select sm.business_id fid,sm.business_name fname from COMPETENCE_USERSMTBUSINESS c, Smt_Business sm where c.userid = '"+userid+"' and c.businessid = sm.business_id order by to_number(sm.business_sortid)";
		CommonQueryBS.systemOut("contentList2------->"+sql);
		this.pageQuery(sql, "SQL", start, limit); //基本不做翻页
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("contentList.rowclick")
	@GridDataRange(GridData.cuerow)//范围一行
	public int selectContent() throws RadowException{
		String subid = (String)this.getPageElement("contentList").getValue("fid");	
		String subname = (String)this.getPageElement("contentList").getValue("fname");
		this.getPageElement("fid").setValue(subid);
		this.getPageElement("fname").setValue(subname);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//添加
	@PageEvent("rigthB")
	public int rigthB() throws RadowException, AppException {
		String fid = this.getPageElement("fid").getValue();
		if (fid == null || "".equals(fid)) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("contentList");
		List<HashMap<String, Object>> list = pe.getValueList();// 查询人员列表
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();// 选择人员列表
		for (HashMap<String, Object> hm : list) {
			if(fid.equals(hm.get("fid"))){
				addlist.add(hm);
			}
		}
		if(listSelect!=null && listSelect.size()>0){
			for (HashMap<String, Object> sel : listSelect) {
				if (fid.equals(sel.get("fid"))) {
					addlist.remove(sel);
				}
			}
		}
		//将数据添加到登记人员列表
		listSelect.addAll(addlist);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//全部增加
	@PageEvent("rigthBAll")
	public int rigthBAll() throws RadowException, AppException {
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList").getValueList();// 选择人员列表
		String data = JSONArray.fromObject(listSelect).toString();
		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("contentList2.rowclick")
	@GridDataRange(GridData.cuerow)//范围一行
	public int contentList11() throws RadowException{
		String subid = (String)this.getPageElement("contentList2").getValue("fid");	
		String subname = (String)this.getPageElement("contentList2").getValue("fname");
		this.getPageElement("fid2").setValue(subid);
		this.getPageElement("fname2").setValue(subname);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除
	@PageEvent("liftB")
	public int liftB() throws RadowException, AppException {
		String subid = this.getPageElement("fid2").getValue();
		if (subid == null || "".equals(subid)) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		List<HashMap<String, Object>> removelist=new LinkedList<HashMap<String,Object>>();
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();// 选择人员列表
		if (listSelect != null && listSelect.size() > 0) {
			for (HashMap<String, Object> sel : listSelect) {
				if (subid.equals(sel.get("fid"))) {
					removelist.add(sel);
				}
			}
		}
		//将数据添加到登记人员列表
		listSelect.removeAll(removelist);
		String data = JSONArray.fromObject(listSelect).toString();
		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//全部删除
	@PageEvent("liftBAll")
	public int liftBAll() throws RadowException, AppException {
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();// 选择人员列表
		listSelect.clear();
		String data = JSONArray.fromObject(listSelect).toString();
		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}


	
	@PageEvent("dogrant")
	@Transaction
	public void save() throws RadowException {
		String userid = this.getPageElement("userid").getValue();
		HBSession sess = HBUtil.getHBSession();
		
		//取出之前的表单范围
		List<Object> oldList = sess.createSQLQuery("select c.businessid from COMPETENCE_USERSMTBUSINESS c where c.userid = '"+userid+"'").list();
				
		//1.用户————自定义录入方案关联
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();
		/*if(listSelect==null||listSelect.size()==0){
			this.setMainMessage("至少选择一个自定义录入方案");
			return;
		}*/
		sess.createSQLQuery("delete from COMPETENCE_USERSMTBUSINESS where userid = '"+userid+"'").executeUpdate();
		if (listSelect != null && listSelect.size() > 0) {
			//先删后插入
			for (HashMap<String, Object> sel : listSelect) {
				String fid = (String)sel.get("fid");
				sess.createSQLQuery("insert into COMPETENCE_USERSMTBUSINESS values (sys_guid() ,'"+userid+"' ,'"+fid+"' ,'1')").executeUpdate();
			}
		}
		
		/*List<Object> newList = sess.createSQLQuery("select c.businessid from COMPETENCE_USERSMTBUSINESS c where c.userid = '"+userid+"'").list();
		
		oldList.removeAll(newList);
		if(oldList!=null&&oldList.size()>0){
			Object obj = sess.createSQLQuery("select s.isleader from smt_user s where s.userid = '"+userid+"'").uniqueResult();
			if(obj!=null&&"1".equals(obj)){
				NewRangePageModel np = new NewRangePageModel();
				//去除下级的菜单权限
				np.minusQX(sess, userid, "权限", "COMPETENCE_USERSMTBUSINESS", "businessid",oldList);
			}
		}*/
		
		this.setMainMessage("授权成功！");
	}

}
