package com.insigma.siis.local.pagemodel.xbrm;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;

import net.sf.json.JSONObject;

public class YjscPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		return 0;
	}
	
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException{
		String yf004 = this.getPageElement("xzlb").getValue();
		String sql="select yf000,yf002 from JS_YJTJ_fn q where yf004='"+yf004+"' order by yf003 asc ";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode = new ArrayList<HashMap<String,Object>>();
		try {
			listCode = cqbs.getListBySQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}
		HashMap<String, Object> mapCode_1=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
				mapCode_1.put(listCode.get(i).get("yf000").toString(), listCode.get(i).get("yf002").toString());
		}
		((Combo)this.getPageElement("xzfn")).setValueListForSelect(mapCode_1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	//生成人员
	@PageEvent("scry.onclick")
	public int scry() throws RadowException, AppException{
		//机构条件
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		 
		StringBuffer a02_a0201b_sb = new StringBuffer("");
       
        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
				if("true".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
			}
        }   
        String a02str = " and a0000 in "+
	               "(select a02.a0000 "+
	                  "from a02 "+
	                "where a02.a0281='true' " +
	                   a02_a0201b_sb+")";
		
		
		
		
		
		
		
		String xzfn = this.getPageElement("xzfn").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			String sqlstr = "select t.qrysql from JS_YJTJ t,JS_YJTJ_ref_fn r "
					+ " where t.qvid=r.qvid and r.yf000=?";
			List<Clob> listsqls = sess.createSQLQuery(sqlstr).setString(0, xzfn).list();
			StringBuilder a0000sqls = new StringBuilder();
			for(Clob a0000sqlClob:listsqls){
				
				a0000sqls.append("select a01a0000 from ("+ClobToString(a0000sqlClob)+") ");
				a0000sqls.append(" INTERSECT ");
			}
			if(!"".equals(a0000sqls.toString())){
				a0000sqls.delete(a0000sqls.length()-11, a0000sqls.length());
				List<String> a0000s = sess.createSQLQuery("select a0000 from a01 where a0000 in("+a0000sqls.toString()+") "+a02str).list();
				if(a0000s.size()>0){
					
					
					
					this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+a0000s+"');");
					//this.setMainMessage("加入人员共 "+a0000s.size()+" 人！");
					//this.setNextBackFunc("alert()");
					this.getExecuteSG().addExecuteCode("closewin("+a0000s.size()+")");
				}else{
					this.setMainMessage("未查询到干部数据！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//生成预警
	@PageEvent("scyj.onclick")
	public int scyj() throws RadowException, AppException{
		
		String rbId = this.getPageElement("rbId").getValue();//批次
		String dc005 = "1";//类别标识
		
		String cur_hj = this.getPageElement("cur_hj").getValue();//环节
		String cur_hj_4 = "1";//讨论决定分环节
		cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
		RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
		
		
		String hjsql = "select distinct js01.js0100,js01.a0000  from a01,js01,js_hj where "
				+ " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+sm.hj4sql;
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		String xzfn = this.getPageElement("xzfn").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> listhja0000s = sess.createSQLQuery(hjsql).list();
			String sqlstr = "select t.chinesename,t.qrysql,t.yjtype from JS_YJTJ t,JS_YJTJ_ref_fn r "
					+ " where t.qvid=r.qvid and r.yf000=? ";
			List<Object[]> listsqls = sess.createSQLQuery(sqlstr).setString(0, xzfn).list();
			//StringBuilder a0000sqls = new StringBuilder();
			Map<String,List<String>> a0000map = new HashMap<String,List<String>>();
			//单个条件中的人员 和 对应中的条件名称。
			for(Object o[] : listsqls){
				String k = o[0]==null?"":o[0].toString();
				String yjtype = o[2]==null?"":o[2].toString();
				if(!"".equals(k)){
					String vsql = "select a01a0000 from ("+ClobToString((Clob)o[1])+") ";
					List<String> a0000s = sess.createSQLQuery(vsql).list();
					a0000map.put(k+"@_@"+yjtype, a0000s);
				}
				
			}
			//人员预警信息
			Map<String, String> ps = new HashMap<String, String>();
			Map<String, Integer> index = new HashMap<String, Integer>();
			for(String key : a0000map.keySet()){
				List<String> a0000s = a0000map.get(key);
				String a0000,js0100,desc;
				String name_type[] = key.split("@_@");
				String stylecolor = "";
				if("1".equals(name_type[1])){
					stylecolor = " style='color:rgb(255,7,7)' ";
				}else if("2".equals(name_type[1])){
					stylecolor = " style='color:rgb(255,198,0)' ";
				}else{
					stylecolor = " style='color:rgb(0,169,0)' ";
				}
				for(Object o[]:listhja0000s){
					//环节中的a0000
					a0000 = o[1].toString();
					js0100 = o[0].toString();
					if(a0000s.contains(a0000)){//如果预警条件中的人员包含环节中的人员， 做记录
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
				String js0100;
				for(Object o[]:listhja0000s){//更新批次所有人员，没有预警则置为空
					//环节中的a0000
					js0100 = o[0].toString();
					pst.setString(1, ps.get(js0100));
					pst.setString(2, ps.get(js0100+"_type"));
					pst.setString(3, js0100);
					pst.addBatch();
				}
				pst.executeBatch();
				conn.commit();
			}
			this.getExecuteSG().addExecuteCode("closewin2()");
				
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
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String ClobToString(Clob clob) throws SQLException, IOException {
		String reString = "";
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		if (br != null) {
			br.close();
		}
		if (is != null) {
			is.close();
		}
		return reString;
	}
}
