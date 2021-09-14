package com.insigma.siis.local.pagemodel.xbrm;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
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
import org.springframework.util.StringUtils;

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
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.utils.CommonParamUtil;
import com.utils.DBUtils;

import net.sf.json.JSONArray;

public class SelectPersonByNamePageModel extends PageModel {

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
		selectlistInJs01(listSelect);	
		
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
		selectlistInJs01(listSelect);
		
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
	
	
	//清除人员暂缓信息
	@PageEvent("clearZanhuan")
	public int clearZanhuan() throws RadowException, AppException{
		String RMRY = this.getPageElement("RMRY").getValue();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs2 = null;
		String a0000s="";
		String names="";
		int count = 0;
		try {
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt2 = conn.createStatement();
			for (HashMap<String, Object> hm : listSelect) {
				//true  并且在js01中存在
				String personcheck2=hm.get("personcheck2")+"";
				String a0000=hm.get("a0000")+"";
				String a0101=hm.get("a0101")+"";
				String vxt=hm.get("vxt")+"";
				//List<Object[]> list = sess.createSQLQuery("select a0101,jsh001,js0100,js_his_id from js_his where a0000='"+a0000+"' and js0122='"+vxt+"' and jsh004=1").list();
				String sql = "select a0101,jsh001,js0100,js_his_id from js_his where a0000='"+a0000+"' and js0122='"+vxt+"' and jsh004=1";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()){
					if( "true".equals(personcheck2)) {
						count ++;
						String js0100= rs.getString(3);
						String js_his_id = rs.getString(4);
						String u1 = "update JS_HIS set JSH004='0' where JS_HIS_ID='"+js_his_id+"'";
						stmt2.addBatch(u1);
						String d1 = "delete from js01_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//动议表
						d1 = "delete from js02_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//民主推荐表
						d1 = "delete from js03_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//考察人选确定表 ？
						d1 = "delete from js04_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//酝酿表？
						d1 = "delete from js06_his where js0100 ='"+js0100+"'";
						//任前公示表
						d1 = "delete from js08_his where js0100 ='"+js0100+"'";
						//依法办理任免表
						d1 = "delete from js09_his where js0100 ='"+js0100+"'";
						//试用期表
						d1 = "delete from js10_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//其他情况表
						d1 = "delete from js11_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//组织考察表（无锡）
						d1 = "delete from js14_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//讨论决定表（无锡）
						d1 = "delete from js15_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//推荐
						d1 = "delete from js18_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js19_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js20_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//新的推荐表
						d1 = "delete from js33_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						
						//保存考核与听取意见
						d1 = "delete from js21_his where js0100 ='"+js0100+"'";

						stmt2.addBatch(d1);
						//发文
						d1 = "delete from js22_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js23_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js24_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//删除附件
						stmt3 = conn.createStatement();
						rs2 = stmt3.executeQuery("select * from js_att_his where js0100 ='"+js0100+"'");
						while(rs2.next()) {
							String p = AppConfig.HZB_PATH + "/jshis/";
							String jsa00 = rs2.getString("jsa00");
							String jsa07 = rs2.getString("jsa07");
							File f = new File(p + jsa07 + jsa00);
		                    if (f.isFile()) {
		                        f.delete();
		                    }
						}
						rs2.close();
						stmt3.close();
						d1 = "delete from js_att_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js_hj_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
					} else {
						if(StringUtil.isEmpty(names)){
							names=a0101;
						}else{
							names=names+","+a0101;
						}
					}
				}
				rs.close();
				stmt.close();
			}
			if(count==0) {
				this.setMainMessage("未勾选任何暂缓人员。");
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				stmt2.executeBatch();
				conn.commit();
			}
			if(!StringUtil.isEmpty(names)){
				names=names+",是暂缓人员，如需清除,请勾选后点击[清除暂缓信息]!";
			}else{
				names="无暂缓人员。";
			}
			this.getExecuteSG().addExecuteCode("document.getElementById('coverinfo').value='"+names+"'");
			
			String data= JSONArray.fromObject(listSelect).toString();
	 		this.getPageElement("selectName").setValue(data);
	 		this.setMainMessage("清除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if(stmt2!=null) {
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	JSGLBS bs = new JSGLBS();
	public static String disk = JSGLBS.HZBPATH;
	
	public int allDelete(String js0100) throws RadowException, AppException {
    	
        try {
            HBSession sess = HBUtil.getHBSession();
            bs.setPm(this);
            bs.deletePersonInfo(js0100, sess);

            List<JsAtt> jsattList = sess.createQuery("from JsAtt where js0100='" + js0100 + "'").list();
            for (JsAtt ja : jsattList) {
                String directory = disk + ja.getJsa07();
                File f = new File(directory + ja.getJsa00());
                if (f.isFile()) {
                    f.delete();
                }
                sess.delete(ja);

            }

            //删除明细
            String sql = "delete from YJMX where JS0100 = :js0100";
            SQLQuery query = sess.createSQLQuery(sql);
            query.setString("js0100", js0100);
            query.executeUpdate();
            sess.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return EventRtnType.NORMAL_SUCCESS;
        }
        this.toastmessage("人员已删除。");
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
			List list = sess.createSQLQuery("select js0100 from js01 where js0120='2' and a0000='"+a0000+"'").list();
			if(list.size()>0){
				String js0100=list.get(0).toString();
				sess.createSQLQuery("update js01 set JS0120='1' where a0000='"+a0000+"'").executeUpdate();
				sess.flush();
				this.getExecuteSG().addExecuteCode("document.getElementById('coverinfo').value='恢复成功!'");
//			}else if(list.size()>0 && "".equals(personcheck2)){
//				String js0100=list.get(0).toString();
//				//删除
//				this.getExecuteSG().addExecuteCode("window.realParent.allDelete('"+js0100+"');");
//				this.getExecuteSG().addExecuteCode("document.getElementById('coverinfo').value=''");
			}
		}
		//personcheck2=true
		if(addlist.size()<1){
			this.setMainMessage("请选择人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//String tplb = this.getPageElement("tplb").getValue();
		//this.getExecuteSG().addExecuteCode("window.realParent.$('#tplb').val('"+tplb+"');");
		
		this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+addlist+"');");
		this.setNextEventName("scyj");
		
		
		//this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
		this.getPageElement("selectName").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 生成预警
	 * @throws RadowException 
	 */
	@PageEvent("scyj")
	@SuppressWarnings("unchecked")
	public void scyj() throws RadowException{
		String rbId = this.getPageElement("rbId").getValue();//批次
		String dc005 = "1";//类别标识
		
		String cur_hj = this.getPageElement("cur_hj").getValue();//环节
		String cur_hj_4 = "1";//讨论决定分环节
		cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
		RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
		
		String hjsql = "select distinct js01.js0100,js01.a0000,js01.js0122  from a01,js01,js_hj where "
				+ " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+sm.hj4sql;
		Connection conn = null;
		PreparedStatement pst = null;
		
		String xzfn = CommonParamUtil.PARAM_MAP.get("default_warning_plan_id"); //使用默认方案
		
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> listhja0000s = sess.createSQLQuery(hjsql).list(); //拟任人员名单
			
			/*String sqlstr = "select t.chinesename,t.qrysql,t.yjtype,t.qrysql2 from JS_YJTJ t,JS_YJTJ_ref_fn r "
					+ " where t.qvid=r.qvid and r.yf000=? "; 
			List<Object[]> listsqls = sess.createSQLQuery(sqlstr).setString(0, xzfn).list(); //方案里的条件
			*/
			String sqlstr = "select t.chinesename,t.qrysql,t.yjtype,t.qrysql2 from JS_YJTJ t,JS_YJTJ_ref_fn r "
					+ " where t.qvid=r.qvid "; 
			List<Object[]> listsqls = sess.createSQLQuery(sqlstr).list(); //方案里的条件
			
			//Map<String,List<String>> a0000map = new HashMap<String,List<String>>(); //预警人员和预警信息
			//Map<String, HashSet<String>> a0000map = new HashMap<String,HashSet<String>>(); //预警人员和预警信息
			//其他来源库人员信息 a0000+v_xt 
			Map<String, HashSet<String>> a0000map_ = new HashMap<String,HashSet<String>>(); //预警人员和预警信息
			
			//单个条件中的人员 和 对应中的条件名称。
			HashSet<String> a0000Set; 
			for(Object o[] : listsqls){
				String k = o[0]==null?"":o[0].toString();
				String yjtype = o[2]==null?"":o[2].toString();
				if(!"".equals(k)){
					String vsql = "select a01a0000||'@1' from ("+DBUtils.ClobToString((Clob)o[1])+") ";
					List<String> a0000s = sess.createSQLQuery(vsql).list();
					
					String vsql2 = "select a01a0000||'@'||v_xt from ("+DBUtils.ClobToString((Clob)o[3])+") ";
					List<String> a0000s2 = sess.createSQLQuery(vsql2).list();
					a0000s.addAll(a0000s2);
					a0000Set = new HashSet<String>(a0000s);
					//a0000map.put(k+"@_@"+yjtype, a0000Set);
					a0000map_.put(k+"@_@"+yjtype, a0000Set);
				}
				
			}
			
			//人员预警信息详细列表
			List<YJMX> yjmxList = new ArrayList<YJMX>();
			YJMX yjmx = null;
			
			//人员预警信息
			Map<String, String> ps = new HashMap<String, String>();
			Map<String, Integer> index = new HashMap<String, Integer>();
			String a0000,js0100,desc,vxt;
			for(String key : a0000map_.keySet()){
				Set<String> a0000s = a0000map_.get(key); //预警人员Set
				
				String name_type[] = key.split("@_@"); //预警信息
				String stylecolor = "";
				if("1".equals(name_type[1])){
					stylecolor = " style='color:rgb(255,7,7)' ";
				}else if("2".equals(name_type[1])){
					stylecolor = " style='color:rgb(255,198,0)' ";
				}else{
					stylecolor = " style='color:rgb(0,169,0)' ";
				}
				for(Object o[]:listhja0000s){ //拟任名单
					//环节中的a0000
					a0000 = o[1].toString();
					js0100 = o[0].toString();
					
					vxt = o[2].toString();
					if(a0000s.contains(a0000+"@"+vxt)){//如果预警条件中的人员包含环节中的人员， 做记录
						
						//记录明细
						yjmx = new YJMX(UUID.randomUUID().toString(),a0000, js0100, name_type[0], name_type[1]);
						yjmxList.add(yjmx);
						
						desc = ps.get(js0100);
						if(desc==null){
							index.put(js0100, 1);
							ps.put(js0100, ""+index.get(js0100) +"、<span "+stylecolor+">"+ name_type[0]+"</span>;");
						}else{
							index.put(js0100, index.get(js0100)+1);
							ps.put(js0100, desc+"</br>"+index.get(js0100)+"、<span"+stylecolor+">"+name_type[0]+"</span>;");
						}
						String yjtype = ps.get(js0100+"_type");
						if(yjtype==null||"".equals(yjtype)){
							ps.put(js0100+"_type", name_type[1]);//预警类型
						}else{
							//跟当前预警级别比较  选出预警级别高的
							if(yjtype.compareTo(name_type[1])>0){
								ps.put(js0100+"_type", name_type[1]);//预警类型
							}
						}
					}
				}
			}
			
			if(ps.size()>0){
				conn = HBUtil.getHBSession().connection();
				conn.setAutoCommit(false);
				String sql = "update js01 set js0118=?,js0119=? where js0100=?";
				pst = conn.prepareStatement(sql);
				String js01002;
				for(Object o[]:listhja0000s){//更新批次所有人员，没有预警则置为空
					//环节中的a0000
					js01002 = o[0].toString();
					pst.setString(1, ps.get(js01002));
					pst.setString(2, ps.get(js01002+"_type"));
					pst.setString(3, js01002);
					pst.addBatch();
				}
				
				//明细是否重复(为啥要做这步操作，前面有坑...)
				String delete = "delete from YJMX where A0000 = ? and JS0100 = ? and CHINESENAME = ? and YJTYPE = ?";
				PreparedStatement pst2 = conn.prepareStatement(delete);
				for (YJMX entity : yjmxList) {
					pst2.setString(1, entity.getA0000());
					pst2.setString(2, entity.getJs0100());
					pst2.setString(3, entity.getChinesename());
					pst2.setString(4, entity.getYjtype());
					pst2.addBatch();
				}
				pst2.executeBatch();

				//保存明细
				String insert = "insert into YJMX (MX001, A0000, JS0100, CHINESENAME, YJTYPE) values (?, ?, ?, ?, ?)";
				PreparedStatement pst3 = conn.prepareStatement(insert);
				for (YJMX entity : yjmxList) {
					pst3.setString(1, entity.getMx001());
					pst3.setString(2, entity.getA0000());
					pst3.setString(3, entity.getJs0100());
					pst3.setString(4, entity.getChinesename());
					pst3.setString(5, entity.getYjtype());
					pst3.addBatch();
				}
				pst3.executeBatch();
				
				pst.executeBatch();
				conn.commit();
			}
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
			}
			e.printStackTrace();
			this.setMainMessage("保存失败！");
		}finally {
			try {
				if(conn!=null)
					conn.close();
				if(pst!=null)
					pst.close();
			} catch (SQLException e1) {
				
			}
		}
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
