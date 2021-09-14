package com.insigma.siis.local.pagemodel.otherdb.nqgb;


import java.io.File;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.SQLQuery;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.YJMX;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.utils.CommonParamUtil;
import com.utils.DBUtils;

import net.sf.json.JSONArray;

public class SelectPersonByNameNQPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		//设置下拉框
		String rbId = this.getPageElement("rbId").getValue();
		String cur_hj = this.getPageElement("cur_hj").getValue();
		String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();
		String dc005 = this.getPageElement("dc005").getValue();
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)){
			dc005 = RMHJ.TAN_HUA_AN_PAI;
		}
		String sql="select  DC001,DC003 from DEPLOY_CLASSIFY where RB_ID  ='"+rbId+"' and dc005='"+dc005+"' order by dc004";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			mapCode.put(listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003"));
		}
		//((Combo)this.getPageElement("tplb")).setValueListForSelect(mapCode);
		
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			cur_hj = cur_hj_4;
		}else if(RMHJ.JI_BEN_QING_KUANG.equals(cur_hj)||RMHJ.DONG_YI.equals(cur_hj)){
			return EventRtnType.NORMAL_SUCCESS;
		}
		//在当前批次中选择人员
		String sql2 = "select  a01.a0000, a0101, a0104,a0192a,a0163,'1' vxt from A01 a01  where a01.a0000  in "
				+ "(select a0000 from js01 j where rb_id='"+rbId+"' and js0122='1' and not exists "
						+ "(select 1 from js01,js_hj where js01.js0100=js_hj.js0100 and js01.js0100 = j.js0100 and js01.rb_id='"+rbId+"' "
								+ "and js_hj.js_type like '"+cur_hj+"%'))  ";
		//非干部系统库查询
		sql2 = sql2 + " union  select  a01.a0000, a0101, a0104,a0192a,a0163,v_xt vxt from V_js_A01 a01  where a01.a0000  in "
				+ "(select a0000 from js01 j where rb_id='"+rbId+"' and js0122<>'1' and not exists "
				+ "(select 1 from js01,js_hj where js01.js0100=js_hj.js0100 and js01.js0100 = j.js0100 and js01.rb_id='"+rbId+"' "
						+ "and js_hj.js_type like '"+cur_hj+"%'))  ";;
		this.getPageElement("sql").setValue(sql2);
		this.getPageElement("mark").setValue("1");
		this.setNextEventName("gridcq.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryFromDatainit")
	public int queryFromDatainit() throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;
		return flg;
	}
	
	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException{
		String uderID = SysManagerUtils.getUserId();
		HBSession sess=HBUtil.getHBSession();
		String sql2="select  a01.a0000, a0101, a0104,a0192a,a0163,'1' vxt from A01 a01  where a01.a0000  in (select a02.a0000 from a02 where a02.a0281='true' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"'))  ";
		
		String unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163,'1' vxt from A01 a01  where "
				+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and a01.status!='4' ";
		
		String querydb = this.getPageElement("querydb").getValue();
		
		//非干部系统库查询
		if(!querydb.equals("1")) {
			sql2="select  a01.a0000, a0101, a0104,a0192a,a0163,v_xt vxt from V_js_A01 a01  where 1=1 and v_xt='"+querydb+"' ";
			
		}
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
				//干部系统库查询 非干部系统不进行更新
				if(querydb.equals("1")) {
					//不包含逗号则进行模糊查询
					name = name.replaceAll("\\s", "");
					boolean ischinese = isChinese(name);
					Object A0102has = sess.createSQLQuery("select JIANPIN('"+name+"') jp from A01 where A0102 is null and A0101='"+name+"'").uniqueResult();
					//如果为汉字 && 简拼为空，更新简拼
					if(ischinese && A0102has!=null){
						sess.createSQLQuery("update A01 set A0102='"+A0102has+"' where A0102 is null and A0101='"+name+"'").executeUpdate();
						sess.flush();
					}
					
				}
				
				/*sql2 = sql2 +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";
				unionsql = unionsql +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";*/
				
				sql2 = sql2 +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
				unionsql = unionsql +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
			}
		}
		if(querydb.equals("1")) {
			this.getPageElement("sql").setValue(sql2+" union all " + unionsql);
		} else {
			this.getPageElement("sql").setValue(sql2);
		}
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
	
	
	//判断选择的list中是否有暂缓人员
	public void selectlistInJs01(List<HashMap<String, Object>> listSelect){
		HBSession sess = HBUtil.getHBSession();
		String names="";
		String nameszz="";
		String namesce="";
		for(HashMap<String, Object> map:listSelect){
			String a0000=(String)map.get("a0000");
			String vxt=(String)map.get("vxt");
			List<Object[]> list = sess.createSQLQuery("select a0101,jsh001 from js_his where a0000='"+a0000+"' and js0122='"+vxt+"' and jsh004=1").list();
			if(list.size()>0){
				String jsa001=list.get(0)[1].toString();
				String js0102=list.get(0)[0].toString();
				if(jsa001.equals("1") || jsa001.equals("3")) {
					if(StringUtil.isEmpty(names)){
						names=js0102;
					}else{
						names=names+","+js0102;
					}
				} else {
					if(StringUtil.isEmpty(nameszz)){
						nameszz=js0102;
					}else{
						nameszz=nameszz+","+js0102;
					}
				}
				
			}
		}
		if(!StringUtil.isEmpty(names)){
			names=names+",是暂缓或超额人员，如需清除,请勾选后点击[清除暂缓信息]!";
		}else{
			names="无暂缓人员。";
		}
		if(!StringUtil.isEmpty(nameszz)){
			nameszz=nameszz+",是终止过的人员!";
		}else{
			//nameszz="无暂缓人员。";
		}
		this.getExecuteSG().addExecuteCode("document.getElementById('coverinfo').value='"+names+nameszz+"';");
		
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
		//判断是否为暂缓人员
		//selectlistInJs01(listSelect);	
		
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
		//判断是否为暂缓人员
		//selectlistInJs01(listSelect);
		
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
		//判断是否为暂缓人员
		selectlistInJs01(listSelect);
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
		//判断是否为暂缓人员,防止为空的时候不更新信息
		selectlistInJs01(listSelect);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//关闭
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException{
		/*List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		//去掉查询列表去除的数据
		pe.setValue(JSONArray.fromObject(addlist).toString());*/
		//this.closeCueWindow("findById");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//确认保存
	@PageEvent("saveSelect")
	public int saveSelect() throws RadowException, AppException{
		
		String RMRY = this.getPageElement("RMRY").getValue();
		
		//获得选择的人员ID
		List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		
		HBSession sess = HBUtil.getHBSession();
		
		String a0000s="";
		for (HashMap<String, Object> hm : listSelect) {
			
			addlist.add(hm.get("a0000")+"@" + hm.get("vxt"));
			
			//true  并且在js01中存在
			String personcheck2=hm.get("personcheck2")+"";
			String a0000=hm.get("a0000")+"";
		}
		//personcheck2=true
		if(addlist.size()<1){
			this.setMainMessage("请选择人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//String tplb = this.getPageElement("tplb").getValue();
		//this.getExecuteSG().addExecuteCode("window.realParent.$('#tplb').val('"+tplb+"');");
		
		this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS2('"+addlist+"');");
		//this.setNextEventName("scyj");
		
		
		//this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
		this.getPageElement("selectName").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//清除输出列表
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) {
		String s = "陈.接.                    空";
		System.out.println(s.replace(" ", ","));
	}
	
	

}
