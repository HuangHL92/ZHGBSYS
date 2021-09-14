package com.insigma.siis.local.pagemodel.audit;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

import net.sf.json.JSONArray;

public class PersonQueryByNamePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException{
		String uderID = SysManagerUtils.getUserId();
		HBSession sess=HBUtil.getHBSession();
		String sql2="select  a01.a0000, a0101, a0104,a0192a,a0163 from A01 a01  where a01.a0000  in (select a02.a0000 from a02 where a02.a0281='true' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid =(select userid from smt_user where userid = '"+uderID+"')))  ";
		
		String unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163 from A01 a01  where "
				+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and a01.status!='4' ";
		
		//获得姓名或者身份证
		String name = this.getPageElement("queryName").getValue();
		//获得查询类型，姓名或身c份证
		String tpye = this.getPageElement("tpye").getValue();
		
		String col1 = "a0101";
		String col2 = "a0102";
		
		if(tpye != null && tpye.equals("2")){
			col1 = "a0184";
			col2 = "a0184";
		}
		
		if(name!=null && !name.trim().equals("")){
			name = StringEscapeUtils.escapeSql(name.trim());
			name = name.replaceAll("\\s+"," ");
			name = name.replaceAll(" ", ",");
			name = name.replace(".", ",");
			name = name.replace("&", ",");
			name = name.replace("#", ",");
			name = name.replaceAll("[\\t\\n\\r]", ",");
			String[] names = name.trim().split(",|，");
			//包含逗号则进行精确查询
			if(names.length>1){
				
				/*	StringBuffer sb = new StringBuffer(" a01.a0101 in(");
					StringBuffer pingyin = new StringBuffer(" a01.a0102 in(");*/
					
					StringBuffer sb = new StringBuffer(" a01."+col1+" in(");
					StringBuffer pingyin = new StringBuffer(" a01."+col2+" in(");
					for(int i=0;i<names.length;i++){
						name = names[i].replaceAll("\\s", "");
						sb.append("'"+name+"',");
						pingyin.append("'"+name.toUpperCase()+"',");
					}
					sb.deleteCharAt(sb.length()-1);
					sb.append(")");
					pingyin.deleteCharAt(pingyin.length()-1);
					pingyin.append(")");
					sql2 = sql2 + " and (" + sb.toString() + " or " + pingyin.toString()+")";
					unionsql = unionsql + " and (" + sb.toString() + " or " + pingyin.toString()+")";
				}else{ 
				//不包含逗号则进行模糊查询
					name = name.replaceAll("\\s", "");
					/*sql2 = sql2 +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";
					unionsql = unionsql +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";*/
					
					
					sql2 = sql2 +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
					unionsql = unionsql +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
				}
		}
		this.getPageElement("sql").setValue(sql2+" union all " + unionsql);
		String count_sql = "select count(*) from ("+sql2+") a ";
		Object number = sess.createSQLQuery(count_sql).uniqueResult();
		int count = 0;
		if(number != null && !"".equals(number.toString())){
			count = Integer.parseInt(number.toString());
		}
		//如果查询结果少于1000条则在待选列表中展示(若只有一条则自动右移到输出列表  jsp:190)
		if(count < 1000){ 
			this.getPageElement("mark").setValue("1");
			this.setNextEventName("gridcq.dogridquery");
		
		//如果多于1000条则不予查询展示并提示	
		}else{
			this.setMainMessage("查询结果过多，请缩小查询范围并重新查询");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String sql = this.getPageElement("sql").getValue();
			if(limit<1000) {
				limit=1000;
			}
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	//添加
	@PageEvent("rigthBtn.onclick")
	public int rigthBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//查询人员列表
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//选择人员列表
		for (HashMap<String, Object> hm : list) {
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				addlist.add(hm);
			}
		}
		//去掉查询列表中被添加的人员
		list.removeAll(addlist);
		String gridcqdata = JSONArray.fromObject(list).toString();
		
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//将数据添加到登记人员列表
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
 		pe.setValue(gridcqdata);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//全部添加
	@PageEvent("rigthAllBtn.onclick")
	public int rigthAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//查询人员列表
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//选择人员列表
		for (HashMap<String, Object> hm : list) {
			addlist.add(hm);
		}
		//去掉查询列表中被添加的人员
		list.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(list).toString());
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//将数据添加到登记人员列表
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//移除
	@PageEvent("liftBtn.onclick")
	public int liftBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//查询人员列表
		for (HashMap<String, Object> hm : listSelect) {
			if(hm.get("personcheck2")!=null&&!"".equals(hm.get("personcheck2"))&&(Boolean) hm.get("personcheck2")){
				addlist.add(hm);
			}
		}
		//去掉查询列表去除的数据
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//将数据添加到登记人员列表
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//全部移除
	@PageEvent("liftAllBtn.onclick")
	public int liftAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//查询人员列表
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm);
		}
		//去掉查询列表去除的数据
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//将数据添加到登记人员列表
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//关闭
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//确认保存
	@PageEvent("saveSelect")
	public int saveSelect() throws RadowException, AppException{
		
		
		//获得选择的人员ID
		List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm.get("a0000")+"");
		}
		if(addlist.size()<1){
			this.setMainMessage("请选择人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+addlist+"');");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//清除输出列表
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}


}
