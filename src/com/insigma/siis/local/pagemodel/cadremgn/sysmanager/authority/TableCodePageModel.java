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

public class TableCodePageModel extends PageModel {
	
	//信息集的col树
		@PageEvent("orgTreeTableJsonData")
		public int orgTreeTableJsonData() throws PrivilegeException, RadowException {
			String userid = (String)this.getParameter("userid");
			if(userid==null){
				userid = "";
			}
			HBSession sess = HBUtil.getHBSession();
			Object ob = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int x = Integer.parseInt(""+ob);
			if(x==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行保存操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			Map<String, Map<String, String>> mapTables = getMapTables(sess, userid);
			
			StringBuffer jsonStr = new StringBuffer();
			List<Object[]> list = sess.createSQLQuery("select t.table_code,t.table_name from CODE_TABLE t where t.use_type='1' order by t.table_code").list();
			if(list!=null&&list.size()>0){
				jsonStr.append("[");
				
				jsonStr.append("{id:'',task:'所有信息集',duration:'',leaf:false,"
						+ "look:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\"LOOK\" name=\"LOOK\" checked />',"
						+ "add:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\"ADD\"  name=\"ADD\" checked />',"
						+ "change:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\"CHANGE\"  name=\"CHANGE\" checked />',"
						+ "del:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\"DEL\" name=\"DEL\" checked />',"
						+ "uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
				
				for(Object[] objs : list){
					String tableid = ""+objs[0];
					String tableName = ""+objs[1];
					jsonStr.append("{id:'"+tableid+"',task:'"+tableName+"',duration:'',leaf:true,"
							+ "look:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+tableid+"|LOOK\" name=\""+tableid+"|LOOK\" "+(mapTables.size()==0||(mapTables.get(tableid)==null)||("1".equals(mapTables.get(tableid).get("ISLOOK")))?"checked":"")+"/>',"
							+ "add:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+tableid+"|ADD\"  name=\""+tableid+"|ADD\" "+(mapTables.size()==0||(mapTables.get(tableid)==null)||("1".equals(mapTables.get(tableid).get("ISADD")))?"checked":"")+"/>',"
							+ "change:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+tableid+"|CHANGE\"  name=\""+tableid+"|CHANGE\" "+(mapTables.size()==0||(mapTables.get(tableid)==null)||("1".equals(mapTables.get(tableid).get("ISCHANGE")))?"checked":"")+"/>',"
							+ "del:'<input type=\"checkbox\" onclick=\"clickCheck(this,2)\" id=\""+tableid+"|DEL\" name=\""+tableid+"|DEL\" "+(mapTables.size()==0||(mapTables.get(tableid)==null)||("1".equals(mapTables.get(tableid).get("ISDEL")))?"checked":"")+"/>',"
							+ "uiProvider:'col',cls:'master-task',iconCls:'task-folder'},");
				}
				jsonStr.deleteCharAt(jsonStr.length()-1);
				jsonStr.append("]}]");
			}else{
				jsonStr.append("{}");
			}
			//System.out.println(jsonStr.toString());
			this.setSelfDefResData(jsonStr.toString());
			return EventRtnType.XML_SUCCESS;
		}
		
		public Map<String, Map<String, String>> getMapTables(HBSession sess,String userid){
			Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
			if(userid!= null&&!"".equals(userid)){
				String sql = "select t.table_code,t.islook,t.isadd,t.ischange,t.isdel,t.ischeckout from COMPETENCE_USERTABLE t where t.userid = '"+userid+"' order by t.table_code";
				List<Object[]> list = sess.createSQLQuery(sql).list();
				if(list!=null&&list.size()>0){
					for(Object[] objs : list){
						Map<String, String> col = new HashMap<String, String>();
						String tablecode = ""+objs[0];
						
						col.put("ISLOOK", ""+objs[1]);
						col.put("ISADD", ""+objs[2]);
						col.put("ISCHANGE", ""+objs[3]);
						col.put("ISDEL", ""+objs[4]);
						col.put("ISCHECKOUT", ""+objs[5]);
						map.put(tablecode, col);
					}
				}
			}
			return map;
		}
	
		@PageEvent("dogrant")
		@com.insigma.odin.framework.radow.annotation.Transaction
		public void save(String value) throws RadowException{
			//查看本来该用户能看到哪些信息项
			String userid = this.getPageElement("subWinIdBussessId").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int x = Integer.parseInt(""+obj);
			if(x==0){
				this.setMainMessage("请先选择用户");
				return;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行保存操作");
				return;
			}
			
			Map<String, Map<String, String>> maptables = new HashMap<String, Map<String,String>>();//usertable
					
			String[] v = value.split(",");
			for (String val : v) {
				String[] vv = val.split(":");
				String codeid = vv[0];// 信息项 A01|LOOK
				//String codename = vv[1];
				String c = vv[2];// "true" "false"

				String[] vvv = codeid.split("\\|");
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
			}
			
			sess.createSQLQuery("delete from competence_usertable where userid = '"+userid+"'").executeUpdate();
			
			Set<String> set = maptables.keySet();
			Connection conn = sess.connection();
			PreparedStatement pstmt = null;
			int i = 0;
			try {
				pstmt = conn.prepareStatement("insert into competence_usertable values(sys_guid(),?,?,?,?,?,?,null)");
			
				for(String key : set){
					Map<String, String> m = maptables.get(key);
					String tablecode = key;
					String islook = m.get("ISLOOK");
					String isadd = m.get("ISADD");
					String ischange = m.get("ISCHANGE");
					String isdel = m.get("ISDEL");
					
					pstmt.setString(1, userid);
					pstmt.setString(2, tablecode);
					pstmt.setString(3, islook);
					pstmt.setString(4, isadd);
					pstmt.setString(5, ischange);
					pstmt.setString(6, isdel);
					pstmt.addBatch();
					i++;
					if (i % 5000 == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("信息集保存失败");
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//先查询competence_usertablecol,判断是否已经保存过信息项：如果有跳过，如果没有则进行信息项授权
			Object num = sess.createSQLQuery("select count(1) from competence_usertablecol where userid = '" + userid + "'").uniqueResult();
			if(Integer.parseInt(""+num)>0){
				
			}else{
				//点击信息集授权时，将信息项的重新进行授权
				// 先再删除之前的信息项授权
				//sess.createSQLQuery("delete from competence_usertablecol where userid = '" + userid + "'").executeUpdate();

				// 再根据最新的mapCols,进行授权
				List<Object[]> list = sess.createSQLQuery("select t.table_code,t.col_code from CODE_TABLE_COL t where t.use_type='1' order by t.col_code").list();
				Connection conn2 = sess.connection();
				PreparedStatement pstmt2 = null;
				int j = 0;
				try {
					pstmt2 = conn2.prepareStatement("insert into competence_usertablecol values(sys_guid(),?,?,?,'1',null,'1',null,'1')");

					for (Object[] objs : list) {
						String tablecode = ""+objs[0];
						String colcode = ""+objs[1];
						
						pstmt2.setString(1, userid);
						pstmt2.setString(2, colcode);
						pstmt2.setString(3, tablecode);
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
					throw new RadowException("信息项保存失败");
				} finally {
					try {
						pstmt2.close();
						conn2.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			this.setMainMessage("授权成功！");
		}
		
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException{
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		if(userid==null){
			userid = "";
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select t.username from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		if(obj!=null){
			this.getExecuteSG().addExecuteCode("document.getElementById('text11').innerText = '"+"当前用户 : "+obj+"';");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
