package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.InfoGroupInfo;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class NewRangeklPageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static int flag = 0;//用于分辨是点击的查询按钮，还是点击的用户组树
	private static int index = 0;
	public NewRangeklPageModel(){
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
			}else{
				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.infogroupname,a.AAA005 FROM AA01 a,COMPETENCE_INFOGROUP b WHERE a.AAA001='AREA_ID' and a.AAA005=b.infogroupid").uniqueResult();
				if(area==null){
					String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
					areaInfo.put("areaname", areaname);
					areaInfo.put("areaid", "G001");
				}else{
					areaInfo.put("areaname", area[0]);
					areaInfo.put("areaid", area[1]);
				}
			}
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId(); //登录用户userid
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				areaInfo.put("manager", "true");
			}else{
				areaInfo.put("manager", "false");
			}
			areaInfo.put("user1", cueUserid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}

	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		this.getPageElement("groupid").setValue(id);
		/*HBSession session = HBUtil.getHBSession();
		String hql = "from InfoGroupInfo t where t.infogroupid='"+id+"'";
		List list = session.createQuery(hql).list();
		String infoids = "";
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				InfoGroupInfo igi = (InfoGroupInfo)list.get(i);
				infoids+=igi.getInfoid()+",";
			}
		}
		if(infoids.length()>0){
			infoids = infoids.substring(0,infoids.length()-1);
		}
		this.getExecuteSG().addExecuteCode("checked('"+infoids+"')");
		this.getPageElement("checkedgroupid").setValue(id);*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		
		//List<InfoGroup> list  = new ArrayList<InfoGroup>();
		String node = this.getParameter("node");//部门ID
		String isFirst = node;
		StringBuffer jsonStr = new StringBuffer();
		HBSession sess = HBUtil.getHBSession();
		jsonStr.append("[");
		//首先能看到“权限管理”模块的一定是管理员用户
		if("-1".equals(node)){//第一次加载
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//当前用户ID
			//如果是root用户，则按system处理
			if("U000".equals(cueUserid)){
				cueUserid = "40288103556cc97701556d629135000f";
			}
			String groupSql ="select sm.id,sm.usergroupname from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"' order by sm.sortid asc " ;//获取管理员所在的部门
			Object[] group = (Object[]) sess.createSQLQuery(groupSql).uniqueResult();
			if(group==null){
				this.setMainMessage("用户未关联部门，请联系系统管理员");
			}
			String groupid = ""+group[0];
			String groupname = ""+group[1];
			
			jsonStr.append("{\"text\" :\"" + groupname
					+ "\" ,\"id\" :\"" + groupid
					+ "\" ,\"cls\" :\"folder\",");
			jsonStr.append("\"href\":");
			jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ groupid + "')\"");
			jsonStr.append(",\"children\" :[");
			
			node = groupid;
		}
		
		String sql=" select * from (select s.userid id, s.username name , s.usertype type,s.sortid from smt_user s where s.dept = '"+node+"' and s.useful = '1' "
				+ " union all "
				+ " select smt.id, smt.usergroupname name ,'group' type ,to_number(smt.sortid) sortid from smt_usergroup smt where smt.sid = '"+node+"' "
						+ " ) order by sortid asc "
				+ " ";
		List<Object[]> users_groups = sess.createSQLQuery(sql).list();
		if(users_groups!=null&&users_groups.size()>0){
			for(Object[] ug : users_groups){
				String id = ""+ug[0];
				String name = ""+ug[1];
				String type = ""+ug[2];
				String icon="";
				String leaf="";
				if("group".equals(type)){
					icon="";
				}else if("-1".equals(type)){
					icon="image/u49.png";
					leaf="true";
				}else if("2".equals(type)){
					icon="images/insideOrgImg1.png";
					leaf="true";
				}else{
					icon="image/icon021a6.gif";
					leaf="true";
				}
				jsonStr.append("{\"text\" :\"" + name  + "\" ,"
						+ "\"id\" :\"" + id + "\" ,"
						+ "\"icon\" :\"" + icon + "\" ,"
						+ "\"leaf\" :\""+leaf+"\","
						+ "\"cls\" :\"folder\","
						);
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ id + "')\"");
				jsonStr.append("},");
			}
		}
		//从smt_user中查询出在部门group下面的用户(管理员、普通用户)
//		List<Object[]> users = sess.createSQLQuery("select s.userid, s.username, s.isleader from smt_user s where s.dept = '"+node+"' and s.useful = '1' order by s.sortid  asc,s.isleader desc").list();
//		if(users!=null&&users.size()>0){
//			for(Object[] user : users){
//				String userid = ""+user[0];
//				String username = ""+user[1];
//				String isleader = ""+user[2];//后期放对应的图标用
//				
//				jsonStr.append("{\"text\" :\"" + username
//						+ "\" ,\"id\" :\"" + userid
//						+ "\" ,\"icon\" :\"" + ("1".equals(isleader)?"image/icon021a6.gif":"images/insideOrgImg1.png")
//						+ "\" ,\"leaf\" :\"true\",\"cls\" :\"folder\",");
//				jsonStr.append("\"href\":");
//				jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ userid + "')\"");
//				jsonStr.append("},");
//			}
//		}
//		
//		//再从smt_usergroup中查询出是否有下级部门
//		List<Object[]> groups = sess.createSQLQuery("select smt.id, smt.usergroupname from smt_usergroup smt where smt.sid = '"+node+"' order by smt.sortid").list();
//		if(groups!=null&&groups.size()>0){
//			for(Object[] g : groups){
//				String gid = ""+g[0];
//				String gname = ""+g[1];
//				
//				jsonStr.append("{\"text\" :\"" + gname
//						+ "\" ,\"id\" :\"" + gid
//						+ "\" ,\"cls\" :\"folder\",");
//				jsonStr.append("\"href\":");
//				jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ gid + "')\"");
//				jsonStr.append("},");
//			}
//		}
		UserVO cueLoginUser = PrivilegeManager.getInstance().getCueLoginUser();
		String dept = cueLoginUser.getDept();
		String sql2=" select s.userid id, s.username name , s.usertype type,s.sortid from smt_user s where s.dept is null and userid not in ('U001','4028810f5f2aed67015f2aefeeee0002','40289c816521ba35016521cab94c0019','40289c816521ba35016521cb2b570032','40289c816521ba35016521cc3f6a004b') and s.useful = '1' ";
		List<Object[]> users = sess.createSQLQuery(sql2).list();
		if("-1".equals(isFirst) || (!StringUtil.isEmpty(dept) && dept.equals(node))){
			if(users!=null&&users.size()>0) {
				jsonStr.deleteCharAt(jsonStr.length()-1);
				jsonStr.append("]},");
				jsonStr.append("{\"text\" :\"" + "未分配用户"
						+ "\" ,\"id\" :\"" + "-1"
						+ "\" ,\"cls\" :\"folder\",");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript:void();\"");
				
				
				if(users!=null&&users.size()>0){
					jsonStr.append(",\"children\" :[");
					for(Object[] ug : users){
						String id = ""+ug[0];
						String name = ""+ug[1];
						String type = ""+ug[2];
						String icon="";
						String leaf="";
						if("group".equals(type)){
							icon="";
						}else if("-1".equals(type)){
							icon="image/u49.png";
							leaf="true";
						}else if("2".equals(type)){
							icon="images/insideOrgImg1.png";
							leaf="true";
						}else{
							icon="image/icon021a6.gif";
							leaf="true";
						}
						jsonStr.append("{\"text\" :\"" + name  + "\" ,"
								+ "\"id\" :\"" + id + "\" ,"
								+ "\"icon\" :\"" + icon + "\" ,"
								+ "\"leaf\" :\""+leaf+"\","
								+ "\"cls\" :\"folder\","
								);
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ id + "')\"");
						jsonStr.append("},");
					}
					jsonStr.deleteCharAt(jsonStr.length()-1);
					jsonStr.append("]},");
				} else {
					jsonStr.append("},");
				}
			} else {
				jsonStr.deleteCharAt(jsonStr.length()-1);
				jsonStr.append("]},");
			}
		}
		jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		
		System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	
	@PageEvent("orgTreeJsonDataChecked")
	public int orgTreeJsonDataChecked() throws PrivilegeException {
		//List<InfoGroup> list  = new ArrayList<InfoGroup>();
		String node = this.getParameter("node");//部门ID
		String isFirst = node;
		StringBuffer jsonStr = new StringBuffer();
		HBSession sess = HBUtil.getHBSession();
		jsonStr.append("[");
		//首先能看到“权限管理”模块的一定是管理员用户
		if("-1".equals(node)){//第一次加载
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//当前用户ID
			
			String groupSql ="select sm.id,sm.usergroupname from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"'";//获取管理员所在的部门
			Object[] group = (Object[]) sess.createSQLQuery(groupSql).uniqueResult();
			if(group==null){
				this.setMainMessage("用户未关联部门，请联系系统管理员");
			}
			String groupid = ""+group[0];
			String groupname = ""+group[1];
			
			jsonStr.append("{\"text\" :\"" + groupname
					+ "\" ,\"id\" :\"" + groupid
					+ "\" ,\"cls\" :\"folder\",");
			jsonStr.append("\"href\":");
			jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ groupid + "')\"");
			jsonStr.append(",\"children\" :[");
			
			node = groupid;
		}
		
		//从smt_user中查询出在部门group下面的用户(管理员、普通用户)
		List<Object[]> users = sess.createSQLQuery("select s.userid, s.username, s.isleader from smt_user s where s.dept = '"+node+"' and s.useful = '1' order by s.isleader desc").list();
		if(users!=null&&users.size()>0){
			for(Object[] user : users){
				String userid = ""+user[0];
				String username = ""+user[1];
				String isleader = ""+user[2];//后期放对应的图标用
				
				jsonStr.append("{\"text\" :\"" + username
						+ "\" ,\"id\" :\"" + userid
						+ "\",checked :false" 
					//	+ ", \"checked\":false"
						+ " ,\"icon\" :\"" + ("1".equals(isleader)?"image/icon021a6.gif":"images/insideOrgImg1.png")
						+ "\" ,\"leaf\" :\"true\",\"cls\" :\"folder\",");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ userid + "')\"");
				jsonStr.append("},");
			}
			
//			" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:false,icon:'"+icon+"',\"href\":\"javascript:if('true'=='"+flag+"'){alert('您没有该机构的权限')}else{radow.doEvent('specoper')}\"}"
		}
		
		//再从smt_usergroup中查询出是否有下级部门
		List<Object[]> groups = sess.createSQLQuery("select smt.id, smt.usergroupname from smt_usergroup smt where smt.sid = '"+node+"' order by smt.sortid").list();
		if(groups!=null&&groups.size()>0){
			for(Object[] g : groups){
				String gid = ""+g[0];
				String gname = ""+g[1];
				
				jsonStr.append("{\"text\" :\"" + gname
						+ "\" ,\"id\" :\"" + gid
						+ "\" ,\"cls\" :\"folder\",");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ gid + "')\"");
				jsonStr.append("},");
			}
		}
		if("-1".equals(isFirst)){
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]},");
		}
		jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		
		System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	//信息集的col树
	@PageEvent("orgTreeTableJsonData")
	public int orgTreeTableJsonData() throws PrivilegeException, RadowException {
		String userid = (String)this.getParameter("userid");
		if(userid==null){
			userid = "";
		}
		HBSession sess = HBUtil.getHBSession();
		Map<String, Map<String, String>> mapTables = getMapTables(sess, userid);
		
		StringBuffer jsonStr = new StringBuffer();
		List<Object[]> list = sess.createSQLQuery("select t.table_code,t.table_name from CODE_TABLE t where t.table_code like 'A%' and t.table_code not in('A32','CAPPOINTC') order by t.table_code").list();
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
	
	
	//add gy-linjun 2019-11-4
	@PageEvent("chooseUser")
	@NoRequiredValidate
	public int chooseUser(String param )throws RadowException, AppException{
		//String userid = this.getPageElement("subWinIdBussessId").getValue();
		 String[] s = param.split(",");
         String userid = s[0];
         String user1 = s[1];
         System.out.println("克隆机构权限---读取权限(userid)"+userid+"**被写权限用户(user1)"+user1);
         String sql = "select sys_guid() as userdeptid  , t.userid as userid , t.b0111 as b0111 , t.type as type from competence_userdept t  where t.userid = '"+userid+"'";
     
 		HBSession sess = HBUtil.getHBSession();
 		//delete
 		sess.createSQLQuery(" delete from competence_userdept where userid = '"+user1+"'").executeUpdate();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请选择获取被克隆(机构)用户");
		}
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement("insert into competence_userdept values(?,?,?,?)");
			rs = stmt.executeQuery(sql);
			int i = 0;
			if (rs != null){
				while (rs.next()) {
					pstmt.setString(1, rs.getString("userdeptid"));
					pstmt.setString(2, user1);
					pstmt.setString(3, rs.getString("b0111"));
					pstmt.setString(4, "1");
					pstmt.addBatch();
					i++;
					if(i%5000 == 0){
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("克隆(机构)授权失败");
		} finally {
			try {
				rs.close();
				pstmt.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		saveRole(userid , user1);
		
		return 0;
	}
	//add gy-linjun 2019-11-4
	public void saveRole(String userid , String user1) throws RadowException, AppException{
         String sql = "select sys_guid() as actid, t.objectid as objectid, t.roleid as roleid,t.sceneid as sceneid,t.objecttype as objecttype, t.dispatchauth as dispatchauth, t.hashcode "
         		+ " as hashcode, t.userid as userid from SMT_ACT t    where t.userid = '"+userid+"'";
 		HBSession sess = HBUtil.getHBSession();
 		sess.createSQLQuery(" delete from SMT_ACT where userid = '"+user1+"'").executeUpdate();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请选择获取被克隆(角色)用户");
		}
		ResultSet  rs = null;
		Connection conn = null;
		Statement  stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement("insert into SMT_ACT values(?,?,?,?,?,?,?,?)");
			rs = stmt.executeQuery(sql);
			System.out.println("NewRangeklPageModel.saveRole:角色克隆SQL="+sql);
			int i = 0;
			if (rs != null){
				while (rs.next()) {
					pstmt.setString(1, rs.getString("actid"));
					pstmt.setString(2, rs.getString("objectid"));
					pstmt.setString(3, rs.getString("roleid"));
					pstmt.setString(4, rs.getString("sceneid"));
					pstmt.setString(5, rs.getString("objecttype"));
					pstmt.setString(6, rs.getString("dispatchauth"));
					pstmt.setString(7, rs.getString("hashcode"));
					pstmt.setString(8, user1);
					pstmt.addBatch();
					i++;
					if(i%5000 == 0){
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("克隆(角色)授权失败");
		} finally {
			try {
				rs.close();
				pstmt.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@PageEvent("CreateInfoGroupBtn.onclick")
	public int CreateInfoGroupBtn() throws RadowException{
		this.openWindow("CreateIGWin", "pages.sysmanager.ZWHZYQ_001_003.CreateInfoGroupWindow");
		this.setRadow_parent_data("1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("DeleteInfoGroupBtn.onclick")
	public int DeleteInfoGroupBtn() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.equals("")){
			this.setMainMessage("请选择需要删除的用户组");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户组是否为默认的用户组，默认用户组不能删除
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"' and S.infogroupname in ('三龄一历','其他信息','基本信息','拟任免')";
		List<InfoGroup> ig1 =sess.createQuery(hql).list();
		if(ig1.size()>0){
			this.setMainMessage("该信息组是系统默认，不能删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.addNextEvent(NextEventValue.YES, "doDeleteGroup",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行删除操作吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doDeleteGroup")
	public int doDeleteGroup(String groupid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"'";
		InfoGroup ig1 =(InfoGroup) sess.createQuery(hql).list().get(0);
		/*Connection conn = sess.connection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from InfoGroupInfo where InfoGroupID=?");
			pstmt.setString(1,groupid); 
			pstmt.executeUpdate();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		InfoGroup ig = (InfoGroup)sess.createQuery("From InfoGroup t where t.infogroupid='"+groupid+"'").list().get(0);
		sess.delete(ig);
		ts.commit();
		List list = new ArrayList();
		try {
			new LogUtil().createLog("616", "COMPETENCE_INFOGROUP",ig1.getInfogroupid(),ig1.getInfogroupname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("删除信息项权限组组成功");
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("ModifyInfoGroupBtn.onclick")
	public int ModifyInfoGroupBtn() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid==null || "".equals(groupid)) {
			this.setMainMessage("请选择一个信息项组。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户组是否为默认的用户组，默认用户组不能修改
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"' and S.infogroupname in ('三龄一历','其他信息','基本信息','拟任免')";
		List<InfoGroup> ig1 =sess.createQuery(hql).list();
		if(ig1.size()>0){
			this.setMainMessage("该信息组是系统默认，不能修改！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("CreateIGWin", "pages.sysmanager.ZWHZYQ_001_003.CreateInfoGroupWindow");
		this.setRadow_parent_data(groupid);
		return EventRtnType.NORMAL_SUCCESS;
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
	
	public Map<String, Map<String, String>> getMapCols(HBSession sess,String userid){
		Map<String, Map<String, String>> mapCols = new HashMap<String, Map<String,String>>();//usertablecol
		if(userid!= null&&!"".equals(userid)){
			String cols = "select t.table_code,t.col_code,t.islook,t.isadd,t.ischange,t.isdel,t.ischeckout from competence_usertablecol t "
					+ "where t.userid = '"+userid+"'";
			List<Object[]> list = sess.createSQLQuery(cols).list();
			
			if(list!=null&&list.size()>0){
				for(Object[] objs : list){
					Map<String, String> col = new HashMap<String, String>();
					String tablecode = ""+objs[0];
					String colcode = ""+objs[1];
					col.put("ISLOOK", ""+objs[2]);
					col.put("ISADD", ""+objs[3]);
					col.put("ISCHANGE", ""+objs[4]);
					col.put("ISDEL", ""+objs[5]);
					col.put("ISCHECKOUT", ""+objs[6]);
					mapCols.put(tablecode+"|"+colcode, col);
				}
			}
		}
		return mapCols;
	}
	
	@PageEvent("dogrant")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public void save(String value) throws RadowException{
		//查看本来该用户能看到哪些信息项
		String userid = this.getPageElement("userid").getValue();
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
			List<Object[]> list = sess.createSQLQuery("select t.table_code,t.col_code from CODE_TABLE_COL t where t.table_code not like 'B%' and t.table_code not in('A32','CAPPOINTC') and t.isuse = '1' order by t.col_code").list();
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
	
	public void saveUpdate(String infoid,String infogroupid){
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		String hql = "from InfoGroupInfo t where t.infoid='"+infoid+"' and t.infogroupid='"+infogroupid+"'";
		List list = sess.createQuery(hql).list();
		if(list.size()==0){
			InfoGroupInfo ud = new InfoGroupInfo();
			ud.setInfoid(infoid);
			ud.setInfogroupid(infogroupid);
			sess.save(ud);
			ts.commit();
		}
	}
	
	public void deleteInfoGroupInfo(String infoid,String infogroupid){
		infoid=infoid.substring(0,infoid.length()-1);
		HBSession sess = HBUtil.getHBSession();
		String hql = "from InfoGroupInfo t where t.infogroupid='"+infogroupid+"' and t.infoid not in("+infoid+")";
		if(infoid.equals("nul")){
			hql = "from InfoGroupInfo t where t.infogroupid='"+infogroupid+"'";
		}
		List list = sess.createQuery(hql).list();
		for(int i=0;i<list.size();i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			InfoGroupInfo igi = (InfoGroupInfo)list.get(i);
			sess.delete(igi);
			ts.commit();
		}
	}
	
	
	public boolean findInfoGroupId(String infoid,String id){
		HBSession sess = HBUtil.getHBSession();
		Object infogroupid = sess.createSQLQuery("SELECT a.infogroupid FROM COMPETENCE_INFOGROUPINFO a WHERE a.infoid='"+infoid+"' and a.infogroupid='"+id+"'").uniqueResult();
		if(infogroupid==null){
			return false;
		}
		return true;
	}
	
	private int choosePerson(String grid, boolean isRowNum) throws RadowException{
		int result = 0;
		int number = 0;
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				number = i;
				result++;
			}
		}
		if(isRowNum){
			return number;//选中的第几个
		}
		return result;//选中用户个数
	}
	
	/*private String chooseInfoIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String infoids = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String infoid = (String) this.getPageElement(grid).getValue("infoid", i);
				if(infoids.equals("")){
					infoids += infoid;
				}
				else{
					infoids += ","+infoid;
				}
			}
		}
		return infoids;
	}*/
	public boolean isLeader(String userid) throws PrivilegeException{
		UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		String isleader = user.getIsleader();
		return "1".equals(isleader);	
	}
	
	
	
	@Override
	public int doInit() {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteGroup")
	public int deleteGroup(String userid) throws RadowException{
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(SysUtil.getCacheCurrentUser().getId())){
			if(userid==null){
				throw new RadowException("请先选择要删除的部门");
			}
			if("G0000".equals(userid)){
				throw new RadowException("主系统   无法被删除");
			}
			HBSession sess = HBUtil.getHBSession();
			//管理员自己所在的部门，无法被删除
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//当前用户ID
			Object o = sess.createSQLQuery("select sm.id from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"'").uniqueResult();
			if(o!=null&&!"".equals(o)&&userid.equals(o)){
				throw new RadowException("无法删除自己所在的部门");
			}
			
			Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				throw new RadowException("请先选择要删除的部门");
			}
			this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','确认删除该用户部门及下级所有用户？',200,function(id){" +
	                "if(id=='ok'){" +
	                "           radow.doEvent('deleteTrue','"+userid+"');" +
	                   "}else if(id=='cancel'){}"
	                    + "});");
			/*//判断部门授权跨组的问题
			if(!StrideGroup()){
				this.setMainMessage("不允许跨组操作部门");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("只有系统管理员身份才能删除");
		}
		if(userid==null){
			throw new RadowException("请先选择要删除的部门");
		}
		if("G0000".equals(userid)){
			throw new RadowException("主系统   无法被删除");
		}
		HBSession sess = HBUtil.getHBSession();
		//管理员自己所在的部门，无法被删除
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//当前用户ID
		Object o = sess.createSQLQuery("select sm.id from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"'").uniqueResult();
		if(o!=null&&!"".equals(o)&&userid.equals(o)){
			throw new RadowException("无法删除自己所在的部门");
		}
		
		Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			throw new RadowException("请先选择要删除的部门");
		}
		//判断部门授权跨组的问题
		if(!StrideGroup()){
			this.setMainMessage("不允许跨组操作部门");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','确认删除该用户部门及下级所有用户？',200,function(id){" +
                "if(id=='ok'){" +
                "           radow.doEvent('deleteTrue','"+userid+"');" +
                   "}else if(id=='cancel'){}"
                    + "});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteTrue")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int deleteTrue(String groupid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		//删除本组下面的所有人员与组信息
		deleteForGroup(sess,groupid);
		
		this.getExecuteSG().addExecuteCode("deleteSuccess();");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("userSort.onclick")
	public int userSort() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			this.getExecuteSG().addExecuteCode("userSort();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("只有系统管理员身份才能排序");
		}
		this.getExecuteSG().addExecuteCode("userSort();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	private void deleteForGroup(HBSession sess,String groupid){
		//删除用户组下面的所有人员
		List<Object> list = sess.createSQLQuery("select t.userid from SMT_USER t where t.dept = '"+groupid+"'").list();
		if(list!=null&&list.size()>0){
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			String sDate = sf.format(date);
			for(Object obj : list){
				if(obj!=null){
					//删除相关权限信息
					deleteAuthority(sess,""+obj);
					
					sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid = '"+obj+"'").executeUpdate();
				}
			}
		}
		//删除用户组
		sess.createSQLQuery("delete from smt_usergroup where id = '"+groupid+"'").executeUpdate();
		
		//循环删除本组中的下级组成员以及组信息
		List<Object> childlist = sess.createSQLQuery("select s.id from smt_usergroup s where s.sid = '"+groupid+"'").list();
		if(childlist!=null&&childlist.size()>0){
			for(Object childgroup : childlist){
				deleteForGroup(sess,""+childgroup);
			}
		}
	}
	
	//判断用户跨组的问题
	public boolean StrideUser() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//被操作的用户id
		String userid = this.getPageElement("userid").getValue();
		String sql = "select userid from SMT_USER where dept = (select sm.sid from smt_user s, smt_usergroup sm where s.useful = '1'  and s.dept = sm.id  and s.userid = '"+userid+"')";
		List<String> userids = HBUtil.getHBSession().createSQLQuery(sql).list();
		boolean flag = false;
		for(String id:userids){
			if(id.equals(cueUserid)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	//判断部门跨组的问题
	public boolean StrideGroup() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//被操作的用户组id
		String userid = this.getPageElement("userid").getValue();
		String sql = "select userid from SMT_USER where dept =(select sid from smt_usergroup where id ='"+userid+"')";
		List<String> userids = HBUtil.getHBSession().createSQLQuery(sql).list();
		boolean flag = false;
		for(String id:userids){
			if(cueUserid.equals(id)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	@PageEvent("createGroup.onclick")
	public int createGroup() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			//userid 可能是部门ID，也可能传入的是用户ID
			String userid = this.getPageElement("userid").getValue();
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_usergroup s where s.id = '"+userid+"'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				throw new RadowException("请先选择所属上级部门");
			}
			/*//判断部门授权跨组的问题
			if(!StrideGroup()){
				this.setMainMessage("不允许跨组操作部门");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("createGroup();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("只有系统管理员身份才能新建部门");
		}
		//userid 可能是部门ID，也可能传入的是用户ID
		String userid = this.getPageElement("userid").getValue();
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_usergroup s where s.id = '"+userid+"'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			throw new RadowException("请先选择所属上级部门");
		}
		//判断部门授权跨组的问题
		if(!StrideGroup()){
			this.setMainMessage("不允许跨组操作部门");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("createGroup();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("create.onclick")
	public int createUser() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				throw new RadowException("请先选择用户所属部门");
			}
			/*//判断用户授权跨组的问题
			if(!StrideGroup()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("createUser();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("只有系统管理员身份才能新建用户");
		}
		String userid = this.getPageElement("userid").getValue();
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			throw new RadowException("请先选择用户所属部门");
		}
		//判断用户授权跨组的问题
//		if(!StrideGroup()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("createUser();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除用户
	@PageEvent("deleteUser.onclick")
	public int deleteUser() throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("您好，请先选择要删除的用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("您好，无法删除超级管理员");
				return EventRtnType.NORMAL_SUCCESS;
			}
			CurrentUser user = SysUtil.getCacheCurrentUser();
			String id = user.getId();
			if(userid.equals(id)){
				this.setMainMessage("您好，您不能删除自己使用的用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("您好，请先选择要删除的用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(isLeader(userid)){
				this.setMainMessage("您好，无法单独删除管理员用户，若想删除，请删除管理员所在部门");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.addNextEvent(NextEventValue.YES, "doRemoveUser",userid);
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口

			this.setMainMessage("确定移除该用户？");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO userVo=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(userVo.getUsertype())){
			throw new RadowException("只有系统管理员身份才能删除");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("您好，请先选择要删除的用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("您好，无法删除超级管理员");
			return EventRtnType.NORMAL_SUCCESS;
		}
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String id = user.getId();
		if(userid.equals(id)){
			this.setMainMessage("您好，您不能删除自己使用的用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("您好，请先选择要删除的用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(isLeader(userid)){
			this.setMainMessage("您好，无法单独删除管理员用户，若想删除，请删除管理员所在部门");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.addNextEvent(NextEventValue.YES, "doRemoveUser",userid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口

		this.setMainMessage("确定移除该用户？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doRemoveUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doRemoveUser(String userid) throws RadowException, PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
		
		//删除各种权限表相关信息
		deleteAuthority(sess,userid);
		
		//将用户设置为无效
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String sDate = sf.format(date);
			
		sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid = '"+userid+"'").executeUpdate();
		
		this.getExecuteSG().addExecuteCode("deleteUserSuccess();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void deleteAuthority(HBSession sess,String userid){
		/*//删除用户与人员表competence_subperson的关系
		sess.createSQLQuery("delete from competence_subperson s where s.userid = '"+userid+"'").executeUpdate();*/
		//删除用户与人员管理类别表competence_usermanager的关系
		sess.createSQLQuery("delete from competence_usermanager s where s.userid = '"+userid+"'").executeUpdate();
		//删除用户与信息集、信息项权限的关系
		sess.createSQLQuery("delete from competence_usertable c where c.userid = '"+userid+"'").executeUpdate();
		sess.createSQLQuery("delete from competence_usertablecol c where c.userid = '"+userid+"'").executeUpdate();
		/*//删除用户与功能权限列表的关系
		sess.createSQLQuery("delete from smt_userfunction s where s.userid = '"+userid+"'").executeUpdate();*/
		//删除用户与功能权限列表的关系(宁波)
		sess.createSQLQuery("delete from smt_act where userid = '"+userid+"'").executeUpdate();
		//删除用户与机构表competence_userdept的关系
		sess.createSQLQuery("delete from competence_userdept s where s.userid = '"+userid+"'").executeUpdate();
		/*//删除用户与自定义录入方案的关系
		sess.createSQLQuery("delete from COMPETENCE_USERSMTBUSINESS s where s.userid = '"+userid+"'").executeUpdate();
		//删除用户与自定义表册的关系
		sess.createSQLQuery("delete from COMPETENCE_USERWEBOFFICE s where s.userid = '"+userid+"'").executeUpdate();
		//删除用户与自定义视图的关系
		sess.createSQLQuery("delete from COMPETENCE_USERQRYVIEW s where s.userid = '"+userid+"'").executeUpdate();
		//删除用户与自定义统计的关系
		sess.createSQLQuery("delete from COMPETENCE_USERTABLEINFO s where s.userid = '"+userid+"'").executeUpdate();*/
	}
	
	//更改用户
	@PageEvent("reset.onclick")
	public int reset() throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("您好，请先选择要更改的用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("您好，无法更改超级管理员");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				//说明是部门
				//判断部门授权跨组的问题
				/*if(!StrideGroup()){
					this.setMainMessage("不允许跨组操作部门");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				this.getExecuteSG().addExecuteCode("resetGroup();");
			}else{
				//判断用户授权跨组的问题
				/*if(!StrideUser()){
					this.setMainMessage("不允许跨组操作用户");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				this.getExecuteSG().addExecuteCode("resetUser();");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("只有系统管理员身份才能更改用户(部门)");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("您好，请先选择要更改的用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("您好，无法更改超级管理员");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			//说明是部门
			//判断部门授权跨组的问题
			if(!StrideGroup()){
				this.setMainMessage("不允许跨组操作部门");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("resetGroup();");
		}else{
			//判断用户授权跨组的问题
//			if(!StrideUser()){
//				this.setMainMessage("不允许跨组操作用户");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			this.getExecuteSG().addExecuteCode("resetUser();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击菜单功能权限
	@PageEvent("personFunc.onclick")
	public int personFunc() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行功能权限操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("personFunction();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
//		if(!("3".equals(user.getUsertype())||"1".equals(user.getUsertype()))){
//			throw new RadowException("只有安全管理员身份才能进入");
//		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行功能权限操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("personFunction();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击查询视图功能权限
		@PageEvent("personFuncQuery.onclick")
		public int personFuncQuery() throws RadowException{
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行功能权限操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("personFuncQuery();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//点击表册功能权限
		@PageEvent("personFuncBook.onclick")
		public int personFuncBook() throws RadowException{
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行功能权限操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("personFuncBook();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//点击自定义表单功能权限
		@PageEvent("personFuncWin.onclick")
		public int personFuncWin() throws RadowException{
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行功能权限操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("personFuncWin();");
			return EventRtnType.NORMAL_SUCCESS;
		}
	
		// 点击克隆功能   add LINJUN
		@PageEvent("personRange3.onclick")
		public int personRange3() throws RadowException, PrivilegeException {
			String cueUserid = SysUtil.getCacheCurrentUser().getId();
			System.out.println("personRange3(克隆)*******cueUserid="+cueUserid);
			//放行超级用户system--------40288103556cc97701556d629135000f
			if("40288103556cc97701556d629135000f".equals(cueUserid)){
				String userid = this.getPageElement("userid").getValue();
				System.out.println("personRange3(克隆)*******userid="+userid);
				if (userid == null || "".equals(userid)) {
					this.setMainMessage("请先选择用户");
					return EventRtnType.NORMAL_SUCCESS;
				}
				HBSession sess = HBUtil.getHBSession();
				Object obj = sess.createSQLQuery(
						"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
				int i = Integer.parseInt("" + obj);
				if (i == 0) {
					this.setMainMessage("请先选择用户");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
					this.setMainMessage("无法对超级管理员进行范围管理操作");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/*//获取上级管理员ID  用于对该用户进行权限范围限制
				String overUserid = NewRangePageModel.getOverUserID(userid);
				if("".equals(overUserid)){
					this.setMainMessage("无法确定上级范围，请联系系统管理员");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				//通过查询competence_userdept,来判断该用户是否已经进行过授权，已经授权，即进行回显
				Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
				int j = Integer.parseInt("" + obj2);
				/*//判断用户授权跨组的问题
				if(!StrideUser()){
					this.setMainMessage("不允许跨组操作用户");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				this.getExecuteSG().addExecuteCode("personRange('"+userid+(j==0?"":"&1")+"');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			System.out.println("personRange3(克隆)*******user="+user);
			if(!("3".equals(user.getUsertype()) || "1".equals(user.getUsertype()))){
				throw new RadowException("只有安全管理员身份才能进入");
			}
			String userid = this.getPageElement("userid").getValue();
			if (userid == null || "".equals(userid)) {
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery(
					"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt("" + obj);
			if (i == 0) {
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行范围管理操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//获取上级管理员ID  用于对该用户进行权限范围限制
			String overUserid = NewRangePageModel.getOverUserID(userid);
			if("".equals(overUserid)){
				this.setMainMessage("无法确定上级范围，请联系系统管理员");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//通过查询competence_userdept,来判断该用户是否已经进行过授权，已经授权，即进行回显
			Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
			
			int j = Integer.parseInt("" + obj2);
			//判断用户授权跨组的问题
//			if(!StrideUser()){
//				this.setMainMessage("不允许跨组操作用户");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			this.getExecuteSG().addExecuteCode("personRange3('"+userid+(j==0?"":"&1")+"');");
			return EventRtnType.NORMAL_SUCCESS;
			
		}	
		
	// 点击机构人员管理权限
	@PageEvent("personRange.onclick")
	public int personRange() throws RadowException, PrivilegeException {
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if (userid == null || "".equals(userid)) {
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery(
					"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt("" + obj);
			if (i == 0) {
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行范围管理操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//获取上级管理员ID  用于对该用户进行权限范围限制
			String overUserid = NewRangePageModel.getOverUserID(userid);
			if("".equals(overUserid)){
				this.setMainMessage("无法确定上级范围，请联系系统管理员");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//通过查询competence_userdept,来判断该用户是否已经进行过授权，已经授权，即进行回显
			Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
			int j = Integer.parseInt("" + obj2);
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("personRange('"+userid+(j==0?"":"&1")+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!("3".equals(user.getUsertype()) || "1".equals(user.getUsertype()))){
			throw new RadowException("只有安全管理员身份才能进入");
		}
		String userid = this.getPageElement("userid").getValue();
		if (userid == null || "".equals(userid)) {
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery(
				"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt("" + obj);
		if (i == 0) {
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行范围管理操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*//获取上级管理员ID  用于对该用户进行权限范围限制
		String overUserid = NewRangePageModel.getOverUserID(userid);
		if("".equals(overUserid)){
			this.setMainMessage("无法确定上级范围，请联系系统管理员");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		//通过查询competence_userdept,来判断该用户是否已经进行过授权，已经授权，即进行回显
		Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
		
		int j = Integer.parseInt("" + obj2);
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("personRange('"+userid+(j==0?"":"&1")+"');");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	// 点击机构人员管理权限(浏览权限)
	@PageEvent("personRangeLL.onclick")
	public int personRangeLL() throws RadowException, PrivilegeException {
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if (userid == null || "".equals(userid)) {
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery(
					"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt("" + obj);
			if (i == 0) {
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行范围管理操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//获取上级管理员ID  用于对该用户进行权限范围限制
			String overUserid = NewRangePageModel.getOverUserID(userid);
			if("".equals(overUserid)){
				this.setMainMessage("无法确定上级范围，请联系系统管理员");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//通过查询competence_userdept,来判断该用户是否已经进行过授权，已经授权，即进行回显
			Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept_look t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
			int j = Integer.parseInt("" + obj2);
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("personRangeLL2('"+userid+(j==0?"":"&1")+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!("3".equals(user.getUsertype()) || "1".equals(user.getUsertype()))){
			throw new RadowException("只有安全管理员身份才能进入");
		}
		String userid = this.getPageElement("userid").getValue();
		if (userid == null || "".equals(userid)) {
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery(
				"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt("" + obj);
		if (i == 0) {
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行范围管理操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*//获取上级管理员ID  用于对该用户进行权限范围限制
		String overUserid = NewRangePageModel.getOverUserID(userid);
		if("".equals(overUserid)){
			this.setMainMessage("无法确定上级范围，请联系系统管理员");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		//通过查询competence_userdept,来判断该用户是否已经进行过授权，已经授权，即进行回显
		Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
		int j = Integer.parseInt("" + obj2);
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("personRangeLL2('"+userid+(j==0?"":"&1")+"');");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	//点击信息项权限tableCol
	@PageEvent("tableCol.onclick")
	public int tableCol() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行信息项权限操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("tableCol('"+userid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!("3".equals(user.getUsertype())||"1".equals(user.getUsertype()))){
			throw new RadowException("只有安全管理员身份才能进入");
		}
		
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行信息项权限操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("tableCol('"+userid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击信息集权限table
	@PageEvent("table.onclick")
	public int table() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行信息集权限操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("table('"+userid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"3".equals(user.getUsertype())){
			throw new RadowException("只有安全管理员身份才能进入");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行信息集权限操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("table('"+userid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	// 用户类型更改
	@PageEvent("changeType.onclick")
	public int changeType() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请先选择用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("无法对超级管理员进行操作");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//判断用户授权跨组的问题
			if(!StrideUser()){
				this.setMainMessage("不允许跨组操作用户");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("changeUserType('"+userid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"3".equals(user.getUsertype())){
			throw new RadowException("只有安全管理员身份才能操作");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户授权跨组的问题
//		if(!StrideUser()){
//			this.setMainMessage("不允许跨组操作用户");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("changeUserType('"+userid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*//点击继承管理员权限
	@PageEvent("inherit.onclick")
	public int inherit() throws RadowException, PrivilegeException{
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("无法对超级管理员进行继承操作");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//自己无法主动去继承上级管理员的权限
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//当前用户ID
		if(cueUserid.equals(userid)){
			this.setMainMessage("无法对自己进行继承操作，请联系上级管理员");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//根据userid获取本部门的管理员ID
		String overUserid = NewRangePageModel.getOverUserID(userid);
		UserVO overVo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(overUserid);
		UserVO vo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		String overName = overVo.getName();
		String name = vo.getName();
		
		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','确定将管理员   "+overName+" 的所有权限继承给用户    "+name+" ？',300,function(id){" +
                "if(id=='ok'){" +
                "           radow.doEvent('inheritUser','"+userid+"');" +
                   "}else if(id=='cancel'){}"
                    + "});");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	/*@PageEvent("inheritUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int inheritUser(String userid) throws PrivilegeException, RadowException{
		String overUserid = NewRangePageModel.getOverUserID(userid);
		//获取上级管理员的机构、人员、管理类别、信息集、信息项
		//后期补充自定义的部分
		HBSession sess = HBUtil.getHBSession();
		
		//机构授权
		// 先删除
		sess.createSQLQuery(" delete from competence_userdept where userid = '" + userid + "'").executeUpdate();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			pstmt = conn
					.prepareStatement("insert into competence_userdept values(?,?,?,?)");
			// 查询出所有导入时，待授权的机构
			rs = stmt.executeQuery("select b0111 from competence_userdept t where userid='"+overUserid+"'");

			int i = 0;
			if (rs != null) {
				while (rs.next()) {
					pstmt.setString(1,
							UUID.randomUUID().toString().replace("-", ""));
					pstmt.setString(2, userid);
					pstmt.setString(3, rs.getString(1));
					pstmt.setString(4, "1");
					pstmt.addBatch();
					i++;
					if (i % 5000 == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RadowException("继承失败");
		} finally {
			try {
				rs.close();
				pstmt.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 用户————管理类别
		// 先进行删除
		sess.createSQLQuery(" delete from competence_usermanager where userid = '" + userid + "'").executeUpdate();
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			String sqlZB130 = "select code_value from code_value where code_type = 'ZB130' and code_leaf = '1' and code_status = '1' order by code_value";
			List<Object> list = sess.createSQLQuery(sqlZB130).list();
			String zb130s = "";
			for(Object obj : list){
				zb130s = zb130s + obj + ",";
			}
			zb130s = zb130s.substring(0, zb130s.length()-1);
			sess.createSQLQuery("insert into competence_usermanager values('"
					+ UUID.randomUUID().toString().replace("-", "") + "',"
					+ "'" + userid + "','"+zb130s+"','1','1')").executeUpdate();
		} else {
			String sqlZB130 = "select t.managerid,t.laidoff from COMPETENCE_USERMANAGER t where t.userid = '"+overUserid+"' and t.type = '1'";
			Object[] zb130s = (Object[]) sess.createSQLQuery(sqlZB130).uniqueResult();
			if(zb130s!=null){
				sess.createSQLQuery("insert into competence_usermanager values('"
						+ UUID.randomUUID().toString().replace("-", "") + "',"
						+ "'" + userid + "','"+zb130s[0]+"','"+zb130s[1]+"','1')").executeUpdate();
			}
		}

		// 用户————人员
		// 先进行删除
		sess.createSQLQuery(" delete from COMPETENCE_SUBPERSON where userid = '" + userid + "'").executeUpdate();
		//------添加新分区
		try {
			sess.createSQLQuery("ALTER TABLE COMPETENCE_SUBPERSON ADD PARTITION p"+userid.substring(22)+" VALUES ('"+userid+"')").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			//如果报错说明已经有该分区
		}
		String rysql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			rysql = "select a01.a0000 from a01 where a01.status != '4'";//获取所有人
		}else{
			rysql = "select c.a0000 from competence_subperson c where c.userid = '"+overUserid+"' and c.type = '1'";
		}
		List<Object> list = sess.createSQLQuery(rysql).list();
		
		Connection conn2 = sess.connection();
		PreparedStatement pstmt2 = null;
		int j = 0;
		try {
			pstmt2 = conn2.prepareStatement("insert into COMPETENCE_SUBPERSON values(?,?,?,?)");
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				pstmt2.setString(1,
						UUID.randomUUID().toString().replace("-", ""));
				pstmt2.setString(2, userid);
				pstmt2.setString(3, "" + o);
				pstmt2.setString(4, "1");
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
			throw new RadowException("继承失败");
		} finally {
			try {
				pstmt2.close();
				conn2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//用户————信息集
		sess.createSQLQuery(" delete from competence_usertable where userid = '" + userid + "'").executeUpdate();
		
		Connection conn3 = sess.connection();
		PreparedStatement pstmt3 = null;
		int k = 0;
		
		String xxjsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			xxjsql = "select t.table_code from CODE_TABLE t where t.table_code not like 'B%' and t.table_code not in('A32','CAPPOINTC') order by t.table_code";
		
			List<Object> list2 = sess.createSQLQuery(xxjsql).list();
			try {
				pstmt3 = conn3.prepareStatement("insert into competence_usertable values(sys_guid(),?,?,'1','1','1','1',null)");
				for (int i = 0; i < list2.size(); i++) {
					Object o = list2.get(i);
					pstmt3.setString(1, userid);
					pstmt3.setString(2, "" + o);
					pstmt3.addBatch();
					k++;
					if (k % 5000 == 0) {
						pstmt3.executeBatch();
						pstmt3.clearBatch();
					}
				}
				pstmt3.executeBatch();
				pstmt3.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt3.close();
					conn3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			xxjsql = "select c.table_code,c.islook,c.isadd,c.ischange,c.isdel from competence_usertable c where c.userid = '"+overUserid+"' ";
		
			List<Object[]> list2 = sess.createSQLQuery(xxjsql).list();
			try {
				pstmt3 = conn3.prepareStatement("insert into competence_usertable values(sys_guid(),?,?,?,?,?,?,null)");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					String tablecode = ""+o[0];
					String islook = ""+o[1];
					String isadd = ""+o[2];
					String ischange = ""+o[3];
					String isdel = ""+o[4];
					
					pstmt3.setString(1, userid);
					pstmt3.setString(2, tablecode);
					pstmt3.setString(3, islook);
					pstmt3.setString(4, isadd);
					pstmt3.setString(5, ischange);
					pstmt3.setString(6, isdel);
					pstmt3.addBatch();
					k++;
					if (k % 5000 == 0) {
						pstmt3.executeBatch();
						pstmt3.clearBatch();
					}
				}
				pstmt3.executeBatch();
				pstmt3.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt3.close();
					conn3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//用户————信息项
		sess.createSQLQuery(" delete from competence_usertablecol where userid = '" + userid + "'").executeUpdate();
		
		Connection conn4 = sess.connection();
		PreparedStatement pstmt4 = null;
		int m = 0;
		
		String xxxsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			xxxsql = "select t.table_code,t.col_code from CODE_TABLE_COL t where t.table_code not like 'B%' and t.table_code not in('A32','CAPPOINTC') and t.islook = '1' order by t.table_code,t.col_code";
		
			List<Object[]> list2 = sess.createSQLQuery(xxxsql).list();
			try {
				pstmt4 = conn4.prepareStatement("insert into competence_usertablecol values(sys_guid(),?,?,?,'1',null,'1',null,'1')");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					
					pstmt4.setString(1, userid);
					pstmt4.setString(2, "" + o[1]);
					pstmt4.setString(3, "" + o[0]);
					pstmt4.addBatch();
					m++;
					if (m % 5000 == 0) {
						pstmt4.executeBatch();
						pstmt4.clearBatch();
					}
				}
				pstmt4.executeBatch();
				pstmt4.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt4.close();
					conn4.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			xxxsql = "select c.table_code,c.col_code,c.islook,c.ischange,c.ischeckout from competence_usertablecol c where c.userid = '"+overUserid+"' ";
		
			List<Object[]> list2 = sess.createSQLQuery(xxxsql).list();
			try {
				pstmt4 = conn4.prepareStatement("insert into competence_usertablecol values(sys_guid(),?,?,?,?,null,?,null,?)");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					String tablecode = "" + o[0];
					String colcode = "" + o[1];
					String islook = ""+o[2];
					String ischange = ""+o[3];
					String ischeckout = ""+o[4];
					
					pstmt4.setString(1, userid);
					pstmt4.setString(2, colcode);
					pstmt4.setString(3, tablecode);
					pstmt4.setString(4, islook);
					pstmt4.setString(5, ischange);
					pstmt4.setString(6, ischeckout);
					pstmt4.addBatch();
					m++;
					if (m % 5000 == 0) {
						pstmt4.executeBatch();
						pstmt4.clearBatch();
					}
				}
				pstmt4.executeBatch();
				pstmt4.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt4.close();
					conn4.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//新增加   进行功能权限的继承
		inheritOtherPower(sess,userid,overUserid);
		
		//this.setMainMessage("继承管理员权限成功");
		this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '继承管理员权限成功', function(e){ if ('ok' == e){var tree = Ext.getCmp('treegrid');tree.root.reload();}});");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
/*	private void inheritOtherPower(HBSession sess, String userid,
			String overUserid) throws RadowException, PrivilegeException {
		// 用户————自定义表单
		sess.createSQLQuery(" delete from COMPETENCE_USERSMTBUSINESS where userid = '"+ userid + "'").executeUpdate();

		Connection conn5 = sess.connection();
		PreparedStatement pstmt5 = null;
		int n = 0;

		String zdybdsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			zdybdsql = "select f.functionid fid from Smt_Function f,Smt_Resource r where f.resourceid = r.resourceid and r.status='1' "
					+ "and r.parent = '40287681624b5ada01624b6cabb7000b' and r.type = '0' order by f.parent,f.orderno";

			List<Object> list2 = sess.createSQLQuery(zdybdsql).list();
			try {
				pstmt5 = conn5
						.prepareStatement("insert into COMPETENCE_USERSMTBUSINESS values(sys_guid(),?,?,'1')");
				for (int i = 0; i < list2.size(); i++) {
					Object o = list2.get(i);

					pstmt5.setString(1, userid);
					pstmt5.setString(2, "" + o);
					pstmt5.addBatch();
					n++;
					if (n % 5000 == 0) {
						pstmt5.executeBatch();
						pstmt5.clearBatch();
					}
				}
				pstmt5.executeBatch();
				pstmt5.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt5.close();
					conn5.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			zdybdsql = "select c.businessid,c.type from COMPETENCE_USERSMTBUSINESS c where c.userid = '"
					+ overUserid + "' ";

			List<Object[]> list2 = sess.createSQLQuery(zdybdsql).list();
			try {
				pstmt5 = conn5.prepareStatement("insert into COMPETENCE_USERSMTBUSINESS values(sys_guid(),?,?,?)");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					String businessid = "" + o[0];
					String type = "" + o[1];

					pstmt5.setString(1, userid);
					pstmt5.setString(2, businessid);
					pstmt5.setString(3, type);
					pstmt5.addBatch();
					n++;
					if (n % 5000 == 0) {
						pstmt5.executeBatch();
						pstmt5.clearBatch();
					}
				}
				pstmt5.executeBatch();
				pstmt5.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt5.close();
					conn5.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// 用户————自定义表册
		sess.createSQLQuery(" delete from COMPETENCE_USERWEBOFFICE where userid = '"+ userid + "'").executeUpdate();

		Connection conn = sess.connection();
		PreparedStatement pstmt = null;
		int m = 0;

		String zdybcsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			zdybcsql = "select t.id,t.type from WEBOFFICE t where t.type in ('3','4')";

			List<Object[]> list2 = sess.createSQLQuery(zdybcsql).list();
			try {
				pstmt = conn.prepareStatement("insert into COMPETENCE_USERWEBOFFICE values(sys_guid(),?,?,?,'1')");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);

					pstmt.setString(1, userid);
					pstmt.setString(2, "" + o[0]);
					pstmt.setString(3, "" + o[1]);
					pstmt.addBatch();
					m++;
					if (m % 5000 == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			zdybcsql = "select c.officeid,c.officetype,c.type from COMPETENCE_USERWEBOFFICE c where c.userid = '"+ overUserid + "' ";

			List<Object[]> list2 = sess.createSQLQuery(zdybcsql).list();
			try {
				pstmt = conn.prepareStatement("insert into COMPETENCE_USERWEBOFFICE values(sys_guid(),?,?,?,?)");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					String officeid = "" + o[0];
					String officetype = "" + o[1];
					String type = "" + o[2];

					pstmt.setString(1, userid);
					pstmt.setString(2, officeid);
					pstmt.setString(3, officetype);
					pstmt.setString(4, type);
					pstmt.addBatch();
					m++;
					if (m % 5000 == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// 用户————自定义视图
		sess.createSQLQuery(" delete from COMPETENCE_USERQRYVIEW where userid = '"+ userid + "'").executeUpdate();

		Connection conn2 = sess.connection();
		PreparedStatement pstmt2 = null;
		int x = 0;

		String zdystsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			zdystsql = "select q.qvid from qryview q where q.type = '1'";

			List<Object> list2 = sess.createSQLQuery(zdystsql).list();
			//如果是system，直接把查询的所有权限给当前用户
			list2.add("CX01");list2.add("CX02");list2.add("CX03");list2.add("CX04");
			list2.add("CX05");list2.add("CX06");list2.add("CX07");
			
			try {
				pstmt2 = conn2.prepareStatement("insert into COMPETENCE_USERQRYVIEW values(sys_guid(),?,?,'1')");
				for (int i = 0; i < list2.size(); i++) {
					Object o = list2.get(i);

					pstmt2.setString(1, userid);
					pstmt2.setString(2, "" + o);
					pstmt2.addBatch();
					x++;
					if (x % 5000 == 0) {
						pstmt2.executeBatch();
						pstmt2.clearBatch();
					}
				}
				pstmt2.executeBatch();
				pstmt2.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt2.close();
					conn2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			zdystsql = "select c.viewid,c.type from COMPETENCE_USERQRYVIEW c where c.userid = '"+ overUserid + "' ";

			List<Object[]> list2 = sess.createSQLQuery(zdystsql).list();
			try {
				pstmt2 = conn2.prepareStatement("insert into COMPETENCE_USERQRYVIEW values(sys_guid(),?,?,?)");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					String viewid = "" + o[0];
					String type = "" + o[1];

					pstmt2.setString(1, userid);
					pstmt2.setString(2, viewid);
					pstmt2.setString(3, type);
					pstmt2.addBatch();
					x++;
					if (x % 5000 == 0) {
						pstmt2.executeBatch();
						pstmt2.clearBatch();
					}
				}
				pstmt2.executeBatch();
				pstmt2.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt2.close();
					conn2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		
		// 用户————自定义统计
		sess.createSQLQuery(" delete from COMPETENCE_USERTABLEINFO where userid = '"+ userid + "'").executeUpdate();

		Connection conn3 = sess.connection();
		PreparedStatement pstmt3 = null;
		int y = 0;

		String zdytjsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			zdytjsql = "select t.tableid from tableinfo t where t.commflag = '1' and t.tstatus = '1'";

			List<Object> list2 = sess.createSQLQuery(zdytjsql).list();
			try {
				pstmt3 = conn3.prepareStatement("insert into COMPETENCE_USERTABLEINFO values(sys_guid(),?,?,'1')");
				for (int i = 0; i < list2.size(); i++) {
					Object o = list2.get(i);

					pstmt3.setString(1, userid);
					pstmt3.setString(2, "" + o);
					pstmt3.addBatch();
					y++;
					if (y % 5000 == 0) {
						pstmt3.executeBatch();
						pstmt3.clearBatch();
					}
				}
				pstmt3.executeBatch();
				pstmt3.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt3.close();
					conn3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			zdytjsql = "select c.tableid,c.type from COMPETENCE_USERTABLEINFO c where c.userid = '"
					+ overUserid + "' ";

			List<Object[]> list2 = sess.createSQLQuery(zdytjsql).list();
			try {
				pstmt3 = conn3
						.prepareStatement("insert into COMPETENCE_USERTABLEINFO values(sys_guid(),?,?,?)");
				for (int i = 0; i < list2.size(); i++) {
					Object[] o = list2.get(i);
					String tableid = "" + o[0];
					String type = "" + o[1];

					pstmt3.setString(1, userid);
					pstmt3.setString(2, tableid);
					pstmt3.setString(3, type);
					pstmt3.addBatch();
					y++;
					if (y % 5000 == 0) {
						pstmt3.executeBatch();
						pstmt3.clearBatch();
					}
				}
				pstmt3.executeBatch();
				pstmt3.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt3.close();
					conn3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// 用户————菜单权限
		UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		
		sess.createSQLQuery(" delete from smt_userfunction where userid = '"+ userid + "'").executeUpdate();

		Connection conn4 = sess.connection();
		PreparedStatement pstmt4 = null;
		int z = 0;

		String csqxsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// 上级管理员为system
			csqxsql = "select f.functionid from smt_function f,smt_resource r where f.functionid = r.resourceid and r.status = '1' order by f.parent,f.orderno";

			List<Object> list2 = sess.createSQLQuery(csqxsql).list();
			
			if("2".equals(user.getUsertype())){//普通用户,去除系统管理权限
				PersonFuncPageModel.saveFunction(sess,list2,"S010000","del");
			}
			
			try {
				pstmt4 = conn4.prepareStatement("insert into smt_userfunction values(sys_guid(),?,?,null)");
				for (int i = 0; i < list2.size(); i++) {
					Object o = list2.get(i);

					pstmt4.setString(1, userid);
					pstmt4.setString(2, "" + o);
					pstmt4.addBatch();
					z++;
					if (z % 5000 == 0) {
						pstmt4.executeBatch();
						pstmt4.clearBatch();
					}
				}
				pstmt4.executeBatch();
				pstmt4.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt4.close();
					conn4.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			csqxsql = "select c.resourceid from smt_userfunction c where c.userid = '"
					+ overUserid + "' ";

			List<Object> list2 = sess.createSQLQuery(csqxsql).list();
			
			if("2".equals(user.getUsertype())){//普通用户,去除系统管理权限
				PersonFuncPageModel.saveFunction(sess,list2,"S010000","del");
			}
			
			try {
				pstmt4 = conn4.prepareStatement("insert into smt_userfunction values(sys_guid(),?,?,null)");
				for (int i = 0; i < list2.size(); i++) {
					Object o = list2.get(i);
					String resourceid = "" + o;

					pstmt4.setString(1, userid);
					pstmt4.setString(2, resourceid);
					pstmt4.addBatch();
					z++;
					if (z % 5000 == 0) {
						pstmt4.executeBatch();
						pstmt4.clearBatch();
					}
				}
				pstmt4.executeBatch();
				pstmt4.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RadowException("继承失败");
			} finally {
				try {
					pstmt4.close();
					conn4.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}*/

	/**
	 * 机构新增
	 * userid ：用户ID
	 * b0111 ：机构主键ID
	 * 
	 * 注：该方法是对user的（本机管理员）上级管理员、上上级管理员...进行机构授权，不包括user本身授权，也不包括对上...级的普通用户
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void increaseB0111(HBSession sess,String userid,String b0111){
		Object[] obj = (Object[]) sess.createSQLQuery("select s.isleader,s.dept from smt_user s where s.userid = '"+userid+"' and s.useful = '1'").uniqueResult();
		if(obj!=null){
			String isleader = ""+obj[0];
			String dept = ""+obj[1];
			if("0".equals(isleader)){//如果是本部门的普通用户，先找到本部门的管理员进行机构授权
				Object ob = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				if(ob!=null){
					sess.createSQLQuery("insert into competence_userdept values (sys_guid(),'"+ob+"','"+b0111+"','1')").executeUpdate();
				}
			}
			//接下来对上级、上上级...管理员进行机构授权
			while(!"G0000".equals(dept)){//循环到主系统停止
				//获取上级部门ID
				Object o = sess.createSQLQuery("select g.sid from smt_usergroup g where g.id = '"+dept+"'").uniqueResult();
				dept = ""+o;
				//获取上级部门的管理员用户ID，进行机构授权
				Object overUserid = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				sess.createSQLQuery("insert into competence_userdept values (sys_guid(),'"+overUserid+"','"+b0111+"','1')").executeUpdate();
			}
		}
		
	}
	
	
	/**
	 * 机构删除
	 * b0111：机构主键ID
	 * 注：删除机构时，是对competence_userdept里面所有对应的b0111进行删除，不分用户
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void deleteB0111(HBSession sess , String b0111){
		sess.createSQLQuery("delete from competence_userdept where b0111 = '"+b0111+"'").executeUpdate();
		sess.createSQLQuery("delete from smt_fileupload where fileuser = '"+b0111+"'").executeUpdate();	
	}
	
	
	/**
	 * 人员新增
	 * userid ：用户ID
	 * a0000 ：人员主键ID
	 *
	 * 注：该方法是对user的（本机管理员）上级管理员、上上级管理员...进行人员授权，不包括user本身授权，也不包括对上...级的普通用户
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void increaseA0000(HBSession sess,String userid,String a0000){
		Object[] obj = (Object[]) sess.createSQLQuery("select s.isleader,s.dept from smt_user s where s.userid = '"+userid+"' and s.useful = '1'").uniqueResult();
		if(obj!=null){
			String isleader = ""+obj[0];
			String dept = ""+obj[1];
			if("0".equals(isleader)){//如果是本部门的普通用户，先找到本部门的管理员进行人员授权
				Object ob = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				if(ob!=null){
					sess.createSQLQuery("insert into competence_subperson values (sys_guid(),'"+ob+"','"+a0000+"','1')").executeUpdate();
				}
			}
			//接下来对上级、上上级...管理员进行人员授权
			while(!"G0000".equals(dept)){//循环到主系统停止
				//获取上级部门ID
				Object o = sess.createSQLQuery("select g.sid from smt_usergroup g where g.id = '"+dept+"'").uniqueResult();
				dept = ""+o;
				//获取上级部门的管理员用户ID，进行人员授权
				Object overUserid = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				if(!"40288103556cc97701556d629135000f".equals(overUserid)){//system用户不需要授权competence_subperson
					sess.createSQLQuery("insert into competence_subperson values (sys_guid(),'"+overUserid+"','"+a0000+"','1')").executeUpdate();
				}
			}
		}
		
	}
	
	
	/**
	 * 人员删除 
	 * a0000：人员主键ID
	 * 注：删除人员时，是对competence_subperson里面所有对应的a0000进行删除，不分用户
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void deleteA0000(HBSession sess , String a0000){
		sess.createSQLQuery("delete from competence_subperson where b0111 = '"+a0000+"'").executeUpdate();	
	}
	
	//更改用户
	@PageEvent("setUserDept.onclick")
	public int setUserDept() throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//放行超级用户system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("您好，请先选择要更改的用户");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("您好，无法更改超级管理员");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user where dept is null and userid = '"+userid+"' ").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("请选择未分配用户！");
			}else{
				//this.getExecuteSG().addExecuteCode("setUserDept();");
				this.getExecuteSG().addExecuteCode("setUserDeptWin('"+userid+"');");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("只有系统管理员身份才能更改用户(部门)");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("您好，请先选择要更改的用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("您好，无法更改超级管理员");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user where dept is null and userid = '"+userid+"' ").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("请选择未分配用户！");
		}else{
			//判断用户授权跨组的问题
			this.getExecuteSG().addExecuteCode("setUserDeptWin('"+userid+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
