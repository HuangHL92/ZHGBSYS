package com.insigma.siis.local.pagemodel.fxyp;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;
import java.util.UUID;
import net.sf.json.JSONArray;

public class QxypCandidatePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("queryFromData");
		return 0;
	}
	
	
	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException{
		String tp00=this.getPageElement("tp00").getValue();
		System.out.print(tp00);
		List<String> sql2= HBUtil.getHBSession().createSQLQuery("select to_char(FINSQL) from QXYPCONTITION t where tp00='"+tp00+"'").list();
		StringBuffer sql=new StringBuffer("");
		if(sql2.size()>0) {
			if(sql2.get(0).length()>0) {
				sql.append(sql2.get(0)+" and  a01.a0000 not in (select a0000 from QXYPRY where tp00='"+tp00+"')");
			}
		}
//		String sql2="select distinct  a01.a0000,a01.a0101,a01.a0104,substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2) a0107,a01.a0221,substr(a01.a0288,1,4)||'.'||substr(a01.a0288,5,2) a0288,a01.a0192a from A01 a01,A02 a02 where a01.A0000=a02.A0000  and a01.a0101 like '%姜%'";
		this.getPageElement("sql").setValue(sql.toString());
		if(sql.length()>0 && !"".equals(sql.toString())) {
			this.setNextEventName("gridcq.dogridquery");
		}
		this.setNextEventName("selectName.dogridquery");
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
	
	
	@PageEvent("selectName.dogridquery")
	public int selectNameQuery(int start,int limit) throws RadowException{
		String tp00=this.getPageElement("tp00").getValue();
		try {
			String sql="select distinct  a01.a0000,a01.a0101,a01.a0104,substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2) a0107,a01.a0221,substr(a01.a0288,1,4)||'.'||substr(a01.a0288,5,2) a0288,a01.a0192a from QXYPRY left join a01 on a01.a0000=QXYPRY.a0000 left join a02 on a02.a0000=a01.a0000 where  QXYPRY.tp00='"+tp00+"'";
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
	//确认保存
	@PageEvent("saveSelect")
	public int saveSelect(String a) throws RadowException, AppException{
		String tp00=this.getPageElement("tp00").getValue();
		//获得选择的人员ID
		List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		
		if(listSelect.size()<1){
			this.setMainMessage("请选择人员");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="";
		HBSession sess = HBUtil.getHBSession();
		StringBuffer nameList=new StringBuffer("");
		try {
			Statement stmt = sess.connection().createStatement();
			sql="delete from QXYPRY where tp00='"+tp00+"'";
			stmt.executeUpdate(sql);
			for (HashMap<String, Object> hm : listSelect) {
//				System.out.println(hm.get("a0000"));
				sql="insert into QXYPRY (RY00,A0000,TP00) values('"+UUID.randomUUID().toString().replaceAll("-", "")+"','"+hm.get("a0000")+"','"+tp00+"')";
//				addlist.add(hm.get("a0000")+"");
//				sql="delete from hz_sh_a01 where publishid='"+publishid+"' and titleid='"+titleid+"' and a0000='"+hm.get("a0000")+"' ";
				stmt.executeUpdate(sql);
//				List<String> name= HBUtil.getHBSession().createSQLQuery("select a0101 from a01 where a0000='"+hm.get("a0000")+"'").list();
				nameList.append(hm.get("a0101")+"，");
//				sql="insert into hz_sh_a01 (sh000,a0000,publishid,titleid,tp0116,tp0117) select sys_guid(),'"+hm.get("a0000")+"','"+publishid+"','"+titleid+"','1','1' from dual ";
//				stmt.executeUpdate(sql);
			}
			if(nameList.length()>0){
				nameList.deleteCharAt(nameList.length()-1);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tpry').value='"+nameList.toString()+"'");
		this.getExecuteSG().addExecuteCode("window.close();");
		//this.getExecuteSG().addExecuteCode("parent.queryPerson();");
		this.getPageElement(change("selectName")).setValue("[]");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String change(String name) throws RadowException {
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			name=name+"2";
		}
		return name;
	}
	//清除输出列表
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
