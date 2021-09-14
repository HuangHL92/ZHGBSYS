package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class TableColPageModel extends PageModel {
	
	//信息集的col树
	@PageEvent("orgTreeTableJsonData")
	public int orgTreeTableJsonData() throws PrivilegeException, RadowException {
		
		//根据贵州客户要求，信息集与角色的授权，角色绑定信息集，故userid实际上转入的是roleid !!!!!!!
		
		String userid = (String)this.getParameter("userid");
		if(userid==null){
			userid = "";
		}
		HBSession sess = HBUtil.getHBSession();
		/*Object ob = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();*/
		Object ob = sess.createSQLQuery("select count(*) from smt_role s where s.roleid = '"+userid+"'").uniqueResult();
		int x = Integer.parseInt(""+ob);
		if(x==0){
			/*this.setMainMessage("请先选择用户");*/
			this.setMainMessage("请先选择角色");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行保存操作");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		
		Map<String, Map<String, String>> mapCols = getMapCols(sess, userid);
		
		StringBuffer jsonStr = new StringBuffer();
		List<Object[]> list = sess.createSQLQuery("select t.table_code,t.table_name from CODE_TABLE t where t.use_type='1' order by t.table_code").list();
		if(list!=null&&list.size()>0){
			jsonStr.append("[");
			for(Object[] objs : list){
				String tableid = ""+objs[0];
				String tableName = ""+objs[1];
				jsonStr.append("{id:'"+tableid+"',task:'"+tableName+"',duration:'',"
						+ "look:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+tableid+"|LOOK\" name=\""+tableid+"|LOOK\" "+((mapCols.size()==0)?"":"checked")+"/>',"
						+ "change:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+tableid+"|CHANGE\"  name=\""+tableid+"|CHANGE\" "+((mapCols.size()==0)?"":"checked")+"/>',"
						+ "checkout:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+tableid+"|CHECKOUT\" name=\""+tableid+"|CHECKOUT\" "+((mapCols.size()==0)?"":"checked")+"/>',"
						+ "uiProvider:'col',cls:'master-task',iconCls:'task-folder'");
				
				List<Object[]> codes = sess.createSQLQuery("select t.col_code,t.col_name from CODE_TABLE_COL t where t.table_code = '"+tableid+"' and t.use_type = '1' order by t.col_code").list();
				if(codes!=null&&codes.size()>0){
					jsonStr.append(",children:[");
					for(Object[] obj : codes){
						String code = ""+obj[0];
						String code_name = ""+obj[1];
						jsonStr.append("{id:\""+code+"\",task:'"+tableName+"',duration:'"+code_name+"',"
								+ "look:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+code+"|LOOK\" name=\""+tableid+"|LOOK\" "+((mapCols.get(tableid+"|"+code)!=null&&("1".equals(mapCols.get(tableid+"|"+code).get("ISLOOK"))))?"checked":"")+"/>',"
								+ "change:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+code+"|CHANGE\"  name=\""+tableid+"|CHANGE\" "+((mapCols.get(tableid+"|"+code)!=null&&("1".equals(mapCols.get(tableid+"|"+code).get("ISCHANGE"))))?"checked":"")+"/>',"
								+ "checkout:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+code+"|CHECKOUT\" name=\""+tableid+"|CHECKOUT\" "+((mapCols.get(tableid+"|"+code)!=null&&("1".equals(mapCols.get(tableid+"|"+code).get("ISCHECKOUT"))))?"checked":"")+"/>',"
								+ "uiProvider:'col',leaf:true,iconCls:'task'},");
					}
					jsonStr.deleteCharAt(jsonStr.length()-1);
					jsonStr.append("]},");
				}else{
					jsonStr.append(",leaf:true},");
				}
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
		}else{
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	//当前用户信息集的col树
	@PageEvent("orgTreeTableJsonDataNow")
	public int orgTreeTableJsonDataNow() throws PrivilegeException, RadowException {
		HBSession sess = HBUtil.getHBSession();
		
		StringBuffer jsonStr = new StringBuffer();
		List<Object[]> list = sess.createSQLQuery("select t.table_code,t.table_name from CODE_TABLE t where t.use_type='1' order by t.table_code").list();
		if(list!=null&&list.size()>0){
			jsonStr.append("[");
			for(Object[] objs : list){
				String tableid = ""+objs[0];
				String tableName = ""+objs[1];
				jsonStr.append("{id:'"+tableid+"',task:'"+tableName+"',duration:'',"
						+ "look:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+tableid+"|LOOK\" name=\""+tableid+"|LOOK\" checked/>',"
						+ "change:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+tableid+"|CHANGE\"  name=\""+tableid+"|CHANGE\" checked/>',"
						+ "checkout:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+tableid+"|CHECKOUT\" name=\""+tableid+"|CHECKOUT\" checked/>',"
						+ "uiProvider:'col',cls:'master-task',iconCls:'task-folder'");
				
				List<Object[]> codes = sess.createSQLQuery("select t.col_code,t.col_name from CODE_TABLE_COL t where t.table_code = '"+tableid+"' and t.use_type='1' order by t.col_code").list();
				if(codes!=null&&codes.size()>0){
					jsonStr.append(",children:[");
					for(Object[] obj : codes){
						String code = ""+obj[0];
						String code_name = ""+obj[1];
						jsonStr.append("{id:\""+code+"\",task:'"+tableName+"',duration:'"+code_name+"',"
								+ "look:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+code+"|LOOK\" name=\""+tableid+"|LOOK\" checked/>',"
								+ "change:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+code+"|CHANGE\"  name=\""+tableid+"|CHANGE\" checked/>',"
								+ "checkout:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+code+"|CHECKOUT\" name=\""+tableid+"|CHECKOUT\" checked/>',"
								+ "uiProvider:'col',leaf:true,iconCls:'task'},");
					}
					jsonStr.deleteCharAt(jsonStr.length()-1);
					jsonStr.append("]},");
				}else{
					jsonStr.append(",leaf:true},");
				}
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
		}else{
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	public Map<String, Map<String, String>> getMapCols(HBSession sess,String userid){
		Map<String, Map<String, String>> mapCols = new HashMap<String, Map<String,String>>();//usertablecol
		if(userid!= null&&!"".equals(userid)){
			String cols = "select t.table_code,t.col_code,t.islook,t.ischange,t.ischeckout from competence_usertablecol t "
					+ "where t.userid = '"+userid+"'";
			List<Object[]> list = sess.createSQLQuery(cols).list();
			
			if(list!=null&&list.size()>0){
				for(Object[] objs : list){
					Map<String, String> col = new HashMap<String, String>();
					String tablecode = ""+objs[0];
					String colcode = ""+objs[1];
					col.put("ISLOOK", ""+objs[2]);
					col.put("ISCHANGE", ""+objs[3]);
					col.put("ISCHECKOUT", ""+objs[4]);
					mapCols.put(tablecode+"|"+colcode, col);
				}
			}
		}
		return mapCols;
	}
	
	@PageEvent("dogrant")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public void save(String value) throws RadowException{
		
		//根据贵州客户要求，信息集与角色的授权，角色绑定信息集，故userid实际上转入的是roleid !!!!!!!
		
		//查看本来该用户能看到哪些信息项
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		/*if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return;
		}*/
		HBSession sess = HBUtil.getHBSession();
		/*Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int x = Integer.parseInt(""+obj);
		if(x==0){
			this.setMainMessage("请先选择用户");
			return;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行保存操作");
			return;
		}*/
		
		//判断是都已经进行过信息集的授权，如果没有，提示
		/*Object num = sess.createSQLQuery("select count(1) from competence_usertable where userid = '"+userid+"'").uniqueResult();
		if(Integer.parseInt(""+num)==0){
			this.setMainMessage("请先进行信息集的授权");
			return;
		}*/
		
		Map<String, Map<String, String>> mapCols = getMapCols(sess, userid);
				
		String[] v = value.split(",");
		for (String val : v) {
			String[] vv = val.split(":");
			String codeid = vv[0];// 信息项 A01|LOOK A0101|LOOK
			String codename = vv[1];
			String c = vv[2];// "true" "false"
			if (codeid.equals(codename)) {// 节点 A01|LOOK|TRUE
				/*String[] vvv = codeid.split("\\|");
				String table = vvv[0];// A01
				String type = "IS" + vvv[1];// LOOK
				
				//保存入usertable表
				if(maptables.get(table)!=null){
					Map<String, String> M = maptables.get(table);
					M.put(type, ("true".equals(c)) ? "1" : "0");
				}else{
					Map<String, String> M = new HashMap<String, String>();
					M.put(type, ("true".equals(c)) ? "1" : "0");
					maptables.put(table, M);
				}
				
				if("true".equals(c)){
					//保存入usertablecol表
					List<Object> l = sess.createSQLQuery(
							"select t.col_code from CODE_TABLE_COL t where t.table_code = '"
									+ table
									+ "' and t.isuse = '1' order by t.col_code")
							.list();
					
					for (Object colcodes : l) {
						if (mapCols.get(table+"|"+colcodes) != null) {
							Map<String, String> m = mapCols.get(table+"|"+colcodes);
							m.put(type, ("true".equals(c)) ? "1" : "0");
						} else {// 新增
							Map<String, String> m = new HashMap<String, String>();
							m.put(type, ("true".equals(c)) ? "1" : "0");
							mapCols.put(table+"|"+colcodes, m);
						}
					}
				}else{
					continue;
				}*/
			} else {
				String tab = codename.split("\\|")[0];//A01
				// A0101|LOOK|TRUE
				String[] vvv = codeid.split("\\|");
				String table = vvv[0];// A0101
				String type = "IS" + vvv[1];// LOOK
				if (mapCols.get(tab+"|"+table) != null) {
					Map<String, String> m = mapCols.get(tab+"|"+table);
					m.put(type, ("true".equals(c)) ? "1" : "0");
				} else {// 新增
					Map<String, String> m = new HashMap<String, String>();
					m.put(type, ("true".equals(c)) ? "1" : "0");
					mapCols.put(tab+"|"+table, m);
				}
			}
		}
		
		//先再删除之前的信息项授权
		sess.createSQLQuery("delete from competence_usertablecol where userid = '"+userid+"'").executeUpdate();
		//再根据最新的mapCols,进行授权
		Set<String> set2 = mapCols.keySet();
		Connection conn2 = sess.connection();
		PreparedStatement pstmt2 = null;
		int j = 0;
		try {
			pstmt2 = conn2.prepareStatement("insert into competence_usertablecol values(sys_guid(),?,?,?,?,null,?,null,?)");
		
			for(String key : set2){
				Map<String, String> m = mapCols.get(key);
				String tablecode = key.split("\\|")[0];
				String colcode = key.split("\\|")[1];
				String islook = m.get("ISLOOK");
				String ischange = m.get("ISCHANGE");
				//String ischeckout = m.get("ISCHECKOUT");
				
				pstmt2.setString(1, userid);
				pstmt2.setString(2, colcode);
				pstmt2.setString(3, tablecode);
				pstmt2.setString(4, islook);
				pstmt2.setString(5, ischange);
				//检验不需要
				pstmt2.setString(6, "0");
				pstmt2.addBatch();
				j++;
				if (j % 5000 == 0) {
					pstmt2.executeBatch();
					pstmt2.clearBatch();
				}
			}
			pstmt2.executeBatch();
			pstmt2.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt2.close();
				conn2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.setMainMessage("授权成功！");
	}
	
	public boolean isLeader(){
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		return "0".equals(isleader);	
	}
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException{
		//根据贵州客户要求，信息集与角色的授权，角色绑定信息集，故userid实际上转入的是roleid !!!!!!!
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		if(userid==null){
			userid = "";
		}
		HBSession sess = HBUtil.getHBSession();
		/*Object obj = sess.createSQLQuery("select t.username from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();*/
		Object obj = sess.createSQLQuery("select s.rolename from smt_role s where s.roleid = '"+userid+"'").uniqueResult();
		if(obj!=null){
			/*this.getExecuteSG().addExecuteCode("document.getElementById('text11').innerText = '"+"当前用户 : "+obj+"';");*/
			this.getExecuteSG().addExecuteCode("document.getElementById('text11').innerText = '"+"当前角色 : "+obj+"';");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
