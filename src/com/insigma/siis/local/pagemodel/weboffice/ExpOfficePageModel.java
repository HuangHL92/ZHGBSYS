package com.insigma.siis.local.pagemodel.weboffice;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.comm.JsonCovert;

public class ExpOfficePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		String type = (String) this.request.getSession().getAttribute("type");
		String sql = "from WebOffice where type='"+type+"'";
		HBSession sess =HBUtil. getHBSession ();
        List list = sess.createQuery(sql).list();
        this.setSelfDefResData( this .getPageQueryData(list, start, limit));
	    return EventRtnType.SPE_SUCCESS;
	}
	


	private PageModel getRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PageEvent("orgTreeJsonData")
	public int orgTreeJsonData() throws RadowException {
		/** 获取root节点值 */
		HBSession sess = HBUtil.getHBSession();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		//String First = "select distinct table_code,col_lection_name from code_table_col where table_code in ('A01','A36')and ISUSE='1'";//根目录
		//String First = "select distinct table_code,col_lection_name from code_table_col where  ISUSE='1' order by  table_code" ;//根目录
		//String First = "select distinct type,nametype  from weboffice" ;
		String First="";
		//if("40288103556cc97701556d629135000f".equals(cueUserid)){
			 First = "select distinct type,nametype  from weboffice order by type" ;
		/*}else{
			 First = "select distinct w.type,w.nametype  from weboffice w left join competence_userweboffice c on w.id=c.officeid where c.userid='"+cueUserid+"' and w.selty=0 or c.userid='"+cueUserid+"'  and w.selty=1  order by type" ;
		}*/
		
		//String First = "select distinct Case  weboffice.type WHEN '3' THEN '自定义Word' WHEN '2' THEN '自定义基本名册' WHEN '1' THEN '自定义单位名册' ELSE '其他'end from weboffice" ;//根目录
		List<Object[]> First_ = sess.createSQLQuery(First).list();
		JsonCovert js = new JsonCovert();
		List<Map> list =new ArrayList<Map>();
		//System.out.println("1========================="+First_.size());
		/** 第一级树 */
		for (int i = 0; i < First_.size(); i++) {
			Object[] First_object = First_.get(i);
			String TableName = First_object[0].toString();
			Map map=new LinkedMap();
    		map.put("id", First_object[0].toString());
    		map.put("text",First_object[1].toString());
    		list.add(map);
        	map.put("children",getTableItems(TableName));
		}
		//System.out.println("2========================="+list.size());
		String jsonstr=js.writeUtilJSON(list);
		//jsonstr=jsonstr.replace("\"", "'").replace("'children'", "children");
		this.setSelfDefResData(jsonstr);
		//System.out.println("======"+jsonstr);
		return EventRtnType.XML_SUCCESS;
	}
	
	public List<Map> getTableItems(String tableName){
		HBSession sess = HBUtil.getHBSession();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String Second="";
		//String Second = "SELECT table_code,col_code,col_name from code_table_col where table_code='"+tableName+"' and ISUSE='1' order by  col_code";//子目录'1'信息集有效
		//if("40288103556cc97701556d629135000f".equals(cueUserid)){
			 Second = " select id,filename,type from weboffice where type='"+tableName+"'";
		/*}else{
			 //Second = " select w.id,w.filename,w.type from weboffice w left join competence_userweboffice c on w.id=c.officeid where c.userid='"+cueUserid+"'and w.type='"+tableName+"'";//子目录'1'信息集有效 select id,filename,type from weboffice where type='1'
			 Second = " select w.id,w.filename,w.type from weboffice w left join competence_userweboffice c on w.id=c.officeid where c.userid='"+cueUserid+"'and w.type='"+tableName+"' and w.selty=0 or c.userid='"+cueUserid+"'  and w.selty=1  order by type";//子目录'1'信息集有效 select id,filename,type from weboffice where type='1'
		}*/
		//String Second = " select id,filename,type from weboffice where type='"+tableName+"'";//子目录'1'信息集有效 select id,filename,type from weboffice where type='1'
		List<Object[]> Second_ = sess.createSQLQuery(Second).list();
		
		List<Map> childrenlist =new ArrayList();
		/*if("3".equals(tableName)){
			Map map1=new LinkedMap();
    		map1.put("text", "通用花名册");
    		map1.put("id","D99D8CB4B5FC4276BB9A93376CA96A15");
    		map1.put("leaf",true);
    		map1.put("iconCls","leaf");
    		childrenlist.add(map1);
    		Map map2=new LinkedMap();
    		map2.put("text", "推荐干部参考名册");
    		map2.put("id","E3673674657D47B499099FB97F0EF64A");
    		map2.put("leaf",true);
    		map2.put("iconCls","leaf");
    		childrenlist.add(map2);
    		Map map3=new LinkedMap();
    		map3.put("text", "推荐干部用有关参考名册(上报版)");
    		map3.put("id","52C1F7A298A742E3B9744CE4B7CD1A68");
    		map3.put("leaf",true);
    		map3.put("iconCls","leaf");
    		childrenlist.add(map3);
		}*/
		for (int j = 0; j < Second_.size(); j++) {
			Object[] Second_object = Second_.get(j);
			Map map1=new LinkedMap();
    		map1.put("text", Second_object[1].toString());
    		map1.put("id",Second_object[0].toString());
    		//map1.put("pathname",Second_object[0].toString());
    		map1.put("leaf",true);
    		map1.put("iconCls","leaf");
    		childrenlist.add(map1);
		}
		return childrenlist;
	}
	
	
}
