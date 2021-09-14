package com.insigma.siis.local.pagemodel.templateconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.collections.map.LinkedMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.pagemodel.comm.JsonCovert;

public class SingleElementSurveyPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("peoplelb.dogridquery")
	public int doQuery(int start,int limit) throws RadowException{
		
		String ids = this.getPageElement("ryids").getValue();
		String[] idsArr = ids.split("&");
		String queryids="";
		for (String str : idsArr) {
			queryids+="'"+str+"',";
		}
		queryids=queryids.substring(0, queryids.length()-1);
		
		String sql="select * from a01 where a0000 in ("+queryids+")";
		
		this.pageQueryByAsynchronous(sql, "SQL", start, limit);
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("peoplelb.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("peoplelb").getValue("a0000",this.getPageElement("peoplelb").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode("$h.openPageModeWin('RmbkzWin','pages.cadremgn.infmtionentry.custom.Rmbkz', '任免表', 1360, 665,'peoplelb','"+request.getContextPath()+"');");
			this.request.getSession().setAttribute("a0000", a0000);
			
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	
	@PageEvent("showPeople")
	public int showPeople(String personId) throws RadowException, AppException {
		System.out.println(personId);
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, personId);
		if(ac01 != null){
			//this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode("$h.openPageModeWin('RmbkzWin','pages.cadremgn.infmtionentry.custom.Rmbkz', '任免表', 1360, 665,'peopleInfoGrid','"+request.getContextPath()+"');");
			this.request.getSession().setAttribute("a0000", personId);
			
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}

	}
	
	@PageEvent("dwlb.dogridquery")
	public int doQueryD(int start,int limit) throws RadowException{
		String queryCondition = this.getPageElement("queryCondition").getValue();
		/*if("".equals(queryCondition) || queryCondition==null){
			this.setMainMessage("请选择一个项目!");
			return EventRtnType.FAILD;
		}*/
		HBSession session = HBUtil.getHBSession();
		//System.out.println(queryCondition);
		String zhtj = this.getPageElement("zhtj").getValue();
		String scjg = this.getPageElement("scjg").getValue();
		//System.out.println(zhtj);
		//System.out.println(scjg);
		String sql="select a0000 from a01 where ";
		if("yes".equals(scjg)){
			sql+=" ";
		}else{
			sql+="not ";
		}
		String condition="";
		String[] queryStr = queryCondition.split("@@");
		for(int i=0;i<queryStr.length;i++){
			String query=queryStr[i];
			String[] split = query.split("---");
			if(i==0){
				sql+="("+split[0]+"='"+split[1]+"' ";
			}else if(i==queryStr.length-1){
				sql+=zhtj+" "+split[0]+"='"+split[1]+"')";
			}else{
				sql+=zhtj+" "+split[0]+"='"+split[1]+"' ";
			}
			
			if(queryStr.length==1){
				sql+=")";
			}
		}
		System.out.println(sql);
		//获取人员以及其对应的单位集合
		HashMap<String, List<String>> idMap = new HashMap<String, List<String>>();
		List<String> list = session.createSQLQuery(sql).list();
		for (String str : list) {
			String id=str;
			//根据id查询此人的单位id
			String dwsql="select distinct a0201b from a02 where a0000='"+id+"'";
			List<String> dwList = session.createSQLQuery(dwsql).list();
			idMap.put(id, dwList);
		}
		
		//System.out.println(idMap);
		//获取所有的单位,并去重
		TreeSet<String> dwSet = new TreeSet<String>();
		 for (Map.Entry<String, List<String>> entry : idMap.entrySet()) {
			 List<String> value = entry.getValue();
			 for (String string : value) {
				dwSet.add(string);
			}
		 }
		 
		 //创建map<单位id,人员list>
		 //HashMap<String, List<String>> dwMap = new HashMap<String, List<String>>();
		 HashMap<String, String> dwMap = new HashMap<String, String>();
		 Iterator<String> iterator = dwSet.iterator();
		  
		  while(iterator.hasNext()) {
			  String dw = (String)iterator.next();
			  String dwnamesql="select b0101 from b01 where b0111='"+dw+"'";
			  String dwname = (String) session.createSQLQuery(dwnamesql).uniqueResult();
			  //ArrayList<String> ryList = new ArrayList<String>();
			  String ryStr="";
			  for (Map.Entry<String, List<String>> entry : idMap.entrySet()) {
				  String key = entry.getKey();
				  List<String> value = entry.getValue();
				  if(value.contains(dw)){
					  //ryList.add(key);
					  ryStr+=key+"&";
				  }
			  }
			  ryStr=ryStr.substring(0,ryStr.length()-1);
			  dwMap.put(dwname, ryStr);
		  }
		  
		  ArrayList<Object> dataList = new ArrayList<Object>();
		  for (Map.Entry<String,String> entry : dwMap.entrySet()) {
			  String dw = entry.getKey();
			  String ry = entry.getValue();
			  Object[] objarr= {dw,ry};
			  dataList.add(objarr);
		  }
		  //JsonCovert js = new JsonCovert();
		  //String jsonstr=js.writeUtilJSON(dwMap);
		// System.out.println(jsonstr);
		//this.getExecuteSG().addExecuteCode("showdw('"+jsonstr+"');");
		
		this.setSelfDefResData(this.getPageQueryData(dataList, start, limit));
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int orgTreeJsonData() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		JsonCovert js = new JsonCovert();
		List<Map> list =new ArrayList<Map>();
		//年龄的map
		Map mapNl=new LinkedMap();
		mapNl.put("id", "a0104");
		mapNl.put("text", "性别");
		mapNl.put("children",getTableItems("GB2261"));
		list.add(mapNl);
		//民族map
		Map mapMz=new LinkedMap();
		mapMz.put("id", "a0117");
		mapMz.put("text", "民族");
		mapMz.put("children",getTableItems("GB3304"));
		list.add(mapMz);
		//政治面貌GB4762
		Map mapZzmm=new LinkedMap();
		mapZzmm.put("id", "a0141");
		mapZzmm.put("text", "政治面貌");
		mapZzmm.put("children",getTableItems("GB4762"));
		list.add(mapZzmm);
		String jsonstr=js.writeUtilJSON(list);
		this.setSelfDefResData(jsonstr);
		return EventRtnType.XML_SUCCESS;
	}

	public List<Map> getTableItems(String code){
		HBSession sess = HBUtil.getHBSession();
		List<Map> childrenlist =new ArrayList<Map>();
		
		String sql="select code_value,code_name from code_value where code_type='"+code+"'";
		List<Object[]> codeList = sess.createSQLQuery(sql).list();
		for (Object[] obj : codeList) {
			Map map1=new LinkedMap();
    		map1.put("text", obj[1].toString());//{text=人员姓名}
    		map1.put("id",obj[0].toString());//{text=人员姓名}
    		map1.put("leaf",true);
    		map1.put("iconCls","leaf");
    		map1.put("checked",false);
    		childrenlist.add(map1);
		}
		
		return childrenlist;
	}

}
