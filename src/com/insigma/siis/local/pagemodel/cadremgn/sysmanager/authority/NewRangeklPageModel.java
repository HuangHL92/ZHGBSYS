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
	 * ϵͳ������Ϣ
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static int flag = 0;//���ڷֱ��ǵ���Ĳ�ѯ��ť�����ǵ�����û�����
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
			String cueUserid = user.getId(); //��¼�û�userid
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
		String node = this.getParameter("node");//����ID
		String isFirst = node;
		StringBuffer jsonStr = new StringBuffer();
		HBSession sess = HBUtil.getHBSession();
		jsonStr.append("[");
		//�����ܿ�����Ȩ�޹���ģ���һ���ǹ���Ա�û�
		if("-1".equals(node)){//��һ�μ���
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
			//�����root�û�����system����
			if("U000".equals(cueUserid)){
				cueUserid = "40288103556cc97701556d629135000f";
			}
			String groupSql ="select sm.id,sm.usergroupname from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"' order by sm.sortid asc " ;//��ȡ����Ա���ڵĲ���
			Object[] group = (Object[]) sess.createSQLQuery(groupSql).uniqueResult();
			if(group==null){
				this.setMainMessage("�û�δ�������ţ�����ϵϵͳ����Ա");
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
		//��smt_user�в�ѯ���ڲ���group������û�(����Ա����ͨ�û�)
//		List<Object[]> users = sess.createSQLQuery("select s.userid, s.username, s.isleader from smt_user s where s.dept = '"+node+"' and s.useful = '1' order by s.sortid  asc,s.isleader desc").list();
//		if(users!=null&&users.size()>0){
//			for(Object[] user : users){
//				String userid = ""+user[0];
//				String username = ""+user[1];
//				String isleader = ""+user[2];//���ڷŶ�Ӧ��ͼ����
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
//		//�ٴ�smt_usergroup�в�ѯ���Ƿ����¼�����
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
				jsonStr.append("{\"text\" :\"" + "δ�����û�"
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
		String node = this.getParameter("node");//����ID
		String isFirst = node;
		StringBuffer jsonStr = new StringBuffer();
		HBSession sess = HBUtil.getHBSession();
		jsonStr.append("[");
		//�����ܿ�����Ȩ�޹���ģ���һ���ǹ���Ա�û�
		if("-1".equals(node)){//��һ�μ���
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
			
			String groupSql ="select sm.id,sm.usergroupname from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"'";//��ȡ����Ա���ڵĲ���
			Object[] group = (Object[]) sess.createSQLQuery(groupSql).uniqueResult();
			if(group==null){
				this.setMainMessage("�û�δ�������ţ�����ϵϵͳ����Ա");
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
		
		//��smt_user�в�ѯ���ڲ���group������û�(����Ա����ͨ�û�)
		List<Object[]> users = sess.createSQLQuery("select s.userid, s.username, s.isleader from smt_user s where s.dept = '"+node+"' and s.useful = '1' order by s.isleader desc").list();
		if(users!=null&&users.size()>0){
			for(Object[] user : users){
				String userid = ""+user[0];
				String username = ""+user[1];
				String isleader = ""+user[2];//���ڷŶ�Ӧ��ͼ����
				
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
			
//			" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:false,icon:'"+icon+"',\"href\":\"javascript:if('true'=='"+flag+"'){alert('��û�иû�����Ȩ��')}else{radow.doEvent('specoper')}\"}"
		}
		
		//�ٴ�smt_usergroup�в�ѯ���Ƿ����¼�����
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
	
	//��Ϣ����col��
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
			
			jsonStr.append("{id:'',task:'������Ϣ��',duration:'',leaf:false,"
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
         System.out.println("��¡����Ȩ��---��ȡȨ��(userid)"+userid+"**��дȨ���û�(user1)"+user1);
         String sql = "select sys_guid() as userdeptid  , t.userid as userid , t.b0111 as b0111 , t.type as type from competence_userdept t  where t.userid = '"+userid+"'";
     
 		HBSession sess = HBUtil.getHBSession();
 		//delete
 		sess.createSQLQuery(" delete from competence_userdept where userid = '"+user1+"'").executeUpdate();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("��ѡ���ȡ����¡(����)�û�");
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
			throw new RadowException("��¡(����)��Ȩʧ��");
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
			this.setMainMessage("��ѡ���ȡ����¡(��ɫ)�û�");
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
			System.out.println("NewRangeklPageModel.saveRole:��ɫ��¡SQL="+sql);
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
			throw new RadowException("��¡(��ɫ)��Ȩʧ��");
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
			this.setMainMessage("��ѡ����Ҫɾ�����û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û����Ƿ�ΪĬ�ϵ��û��飬Ĭ���û��鲻��ɾ��
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"' and S.infogroupname in ('����һ��','������Ϣ','������Ϣ','������')";
		List<InfoGroup> ig1 =sess.createQuery(hql).list();
		if(ig1.size()>0){
			this.setMainMessage("����Ϣ����ϵͳĬ�ϣ�����ɾ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.addNextEvent(NextEventValue.YES, "doDeleteGroup",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ��ɾ��������");
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
		this.setMainMessage("ɾ����Ϣ��Ȩ������ɹ�");
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("ModifyInfoGroupBtn.onclick")
	public int ModifyInfoGroupBtn() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid==null || "".equals(groupid)) {
			this.setMainMessage("��ѡ��һ����Ϣ���顣");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û����Ƿ�ΪĬ�ϵ��û��飬Ĭ���û��鲻���޸�
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"' and S.infogroupname in ('����һ��','������Ϣ','������Ϣ','������')";
		List<InfoGroup> ig1 =sess.createQuery(hql).list();
		if(ig1.size()>0){
			this.setMainMessage("����Ϣ����ϵͳĬ�ϣ������޸ģ�");
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
		//�鿴�������û��ܿ�����Щ��Ϣ��
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("����ѡ���û�");
			return;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int x = Integer.parseInt(""+obj);
		if(x==0){
			this.setMainMessage("����ѡ���û�");
			return;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա���б������");
			return;
		}
		
		Map<String, Map<String, String>> maptables = new HashMap<String, Map<String,String>>();//usertable
				
		String[] v = value.split(",");
		for (String val : v) {
			String[] vv = val.split(":");
			String codeid = vv[0];// ��Ϣ�� A01|LOOK
			//String codename = vv[1];
			String c = vv[2];// "true" "false"

			String[] vvv = codeid.split("\\|");
			String table = vvv[0];// A01
			String type = "IS" + vvv[1];// LOOK
				
			//������usertable��
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
			throw new RadowException("��Ϣ������ʧ��");
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//�Ȳ�ѯcompetence_usertablecol,�ж��Ƿ��Ѿ��������Ϣ���������������û���������Ϣ����Ȩ
		Object num = sess.createSQLQuery("select count(1) from competence_usertablecol where userid = '" + userid + "'").uniqueResult();
		if(Integer.parseInt(""+num)>0){
			
		}else{
			//�����Ϣ����Ȩʱ������Ϣ������½�����Ȩ
			// ����ɾ��֮ǰ����Ϣ����Ȩ
			//sess.createSQLQuery("delete from competence_usertablecol where userid = '" + userid + "'").executeUpdate();

			// �ٸ������µ�mapCols,������Ȩ
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
				throw new RadowException("��Ϣ���ʧ��");
			} finally {
				try {
					pstmt2.close();
					conn2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		this.setMainMessage("��Ȩ�ɹ���");
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
			return number;//ѡ�еĵڼ���
		}
		return result;//ѡ���û�����
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
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(SysUtil.getCacheCurrentUser().getId())){
			if(userid==null){
				throw new RadowException("����ѡ��Ҫɾ���Ĳ���");
			}
			if("G0000".equals(userid)){
				throw new RadowException("��ϵͳ   �޷���ɾ��");
			}
			HBSession sess = HBUtil.getHBSession();
			//����Ա�Լ����ڵĲ��ţ��޷���ɾ��
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
			Object o = sess.createSQLQuery("select sm.id from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"'").uniqueResult();
			if(o!=null&&!"".equals(o)&&userid.equals(o)){
				throw new RadowException("�޷�ɾ���Լ����ڵĲ���");
			}
			
			Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				throw new RadowException("����ѡ��Ҫɾ���Ĳ���");
			}
			this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','ȷ��ɾ�����û����ż��¼������û���',200,function(id){" +
	                "if(id=='ok'){" +
	                "           radow.doEvent('deleteTrue','"+userid+"');" +
	                   "}else if(id=='cancel'){}"
	                    + "});");
			/*//�жϲ�����Ȩ���������
			if(!StrideGroup()){
				this.setMainMessage("����������������");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ���ɾ��");
		}
		if(userid==null){
			throw new RadowException("����ѡ��Ҫɾ���Ĳ���");
		}
		if("G0000".equals(userid)){
			throw new RadowException("��ϵͳ   �޷���ɾ��");
		}
		HBSession sess = HBUtil.getHBSession();
		//����Ա�Լ����ڵĲ��ţ��޷���ɾ��
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
		Object o = sess.createSQLQuery("select sm.id from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '"+cueUserid+"'").uniqueResult();
		if(o!=null&&!"".equals(o)&&userid.equals(o)){
			throw new RadowException("�޷�ɾ���Լ����ڵĲ���");
		}
		
		Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			throw new RadowException("����ѡ��Ҫɾ���Ĳ���");
		}
		//�жϲ�����Ȩ���������
		if(!StrideGroup()){
			this.setMainMessage("����������������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','ȷ��ɾ�����û����ż��¼������û���',200,function(id){" +
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
		//ɾ�����������������Ա������Ϣ
		deleteForGroup(sess,groupid);
		
		this.getExecuteSG().addExecuteCode("deleteSuccess();");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("userSort.onclick")
	public int userSort() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			this.getExecuteSG().addExecuteCode("userSort();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ�������");
		}
		this.getExecuteSG().addExecuteCode("userSort();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	private void deleteForGroup(HBSession sess,String groupid){
		//ɾ���û��������������Ա
		List<Object> list = sess.createSQLQuery("select t.userid from SMT_USER t where t.dept = '"+groupid+"'").list();
		if(list!=null&&list.size()>0){
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			String sDate = sf.format(date);
			for(Object obj : list){
				if(obj!=null){
					//ɾ�����Ȩ����Ϣ
					deleteAuthority(sess,""+obj);
					
					sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid = '"+obj+"'").executeUpdate();
				}
			}
		}
		//ɾ���û���
		sess.createSQLQuery("delete from smt_usergroup where id = '"+groupid+"'").executeUpdate();
		
		//ѭ��ɾ�������е��¼����Ա�Լ�����Ϣ
		List<Object> childlist = sess.createSQLQuery("select s.id from smt_usergroup s where s.sid = '"+groupid+"'").list();
		if(childlist!=null&&childlist.size()>0){
			for(Object childgroup : childlist){
				deleteForGroup(sess,""+childgroup);
			}
		}
	}
	
	//�ж��û����������
	public boolean StrideUser() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���������û�id
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
	//�жϲ��ſ��������
	public boolean StrideGroup() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���������û���id
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
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			//userid �����ǲ���ID��Ҳ���ܴ�������û�ID
			String userid = this.getPageElement("userid").getValue();
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_usergroup s where s.id = '"+userid+"'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				throw new RadowException("����ѡ�������ϼ�����");
			}
			/*//�жϲ�����Ȩ���������
			if(!StrideGroup()){
				this.setMainMessage("����������������");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("createGroup();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ����½�����");
		}
		//userid �����ǲ���ID��Ҳ���ܴ�������û�ID
		String userid = this.getPageElement("userid").getValue();
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_usergroup s where s.id = '"+userid+"'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			throw new RadowException("����ѡ�������ϼ�����");
		}
		//�жϲ�����Ȩ���������
		if(!StrideGroup()){
			this.setMainMessage("����������������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("createGroup();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("create.onclick")
	public int createUser() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				throw new RadowException("����ѡ���û���������");
			}
			/*//�ж��û���Ȩ���������
			if(!StrideGroup()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("createUser();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ����½��û�");
		}
		String userid = this.getPageElement("userid").getValue();
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_usergroup where id = '"+userid+"'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			throw new RadowException("����ѡ���û���������");
		}
		//�ж��û���Ȩ���������
//		if(!StrideGroup()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("createUser();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ɾ���û�
	@PageEvent("deleteUser.onclick")
	public int deleteUser() throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("���ã�����ѡ��Ҫɾ�����û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("���ã��޷�ɾ����������Ա");
				return EventRtnType.NORMAL_SUCCESS;
			}
			CurrentUser user = SysUtil.getCacheCurrentUser();
			String id = user.getId();
			if(userid.equals(id)){
				this.setMainMessage("���ã�������ɾ���Լ�ʹ�õ��û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("���ã�����ѡ��Ҫɾ�����û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(isLeader(userid)){
				this.setMainMessage("���ã��޷�����ɾ������Ա�û�������ɾ������ɾ������Ա���ڲ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.addNextEvent(NextEventValue.YES, "doRemoveUser",userid);
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���

			this.setMainMessage("ȷ���Ƴ����û���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO userVo=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(userVo.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ���ɾ��");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("���ã�����ѡ��Ҫɾ�����û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("���ã��޷�ɾ����������Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String id = user.getId();
		if(userid.equals(id)){
			this.setMainMessage("���ã�������ɾ���Լ�ʹ�õ��û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("���ã�����ѡ��Ҫɾ�����û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(isLeader(userid)){
			this.setMainMessage("���ã��޷�����ɾ������Ա�û�������ɾ������ɾ������Ա���ڲ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.addNextEvent(NextEventValue.YES, "doRemoveUser",userid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���

		this.setMainMessage("ȷ���Ƴ����û���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doRemoveUser")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doRemoveUser(String userid) throws RadowException, PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
		
		//ɾ������Ȩ�ޱ������Ϣ
		deleteAuthority(sess,userid);
		
		//���û�����Ϊ��Ч
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String sDate = sf.format(date);
			
		sess.createSQLQuery("update smt_user set useful = '2',loginname=concat(loginname,'"+sDate+"') where userid = '"+userid+"'").executeUpdate();
		
		this.getExecuteSG().addExecuteCode("deleteUserSuccess();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void deleteAuthority(HBSession sess,String userid){
		/*//ɾ���û�����Ա��competence_subperson�Ĺ�ϵ
		sess.createSQLQuery("delete from competence_subperson s where s.userid = '"+userid+"'").executeUpdate();*/
		//ɾ���û�����Ա��������competence_usermanager�Ĺ�ϵ
		sess.createSQLQuery("delete from competence_usermanager s where s.userid = '"+userid+"'").executeUpdate();
		//ɾ���û�����Ϣ������Ϣ��Ȩ�޵Ĺ�ϵ
		sess.createSQLQuery("delete from competence_usertable c where c.userid = '"+userid+"'").executeUpdate();
		sess.createSQLQuery("delete from competence_usertablecol c where c.userid = '"+userid+"'").executeUpdate();
		/*//ɾ���û��빦��Ȩ���б�Ĺ�ϵ
		sess.createSQLQuery("delete from smt_userfunction s where s.userid = '"+userid+"'").executeUpdate();*/
		//ɾ���û��빦��Ȩ���б�Ĺ�ϵ(����)
		sess.createSQLQuery("delete from smt_act where userid = '"+userid+"'").executeUpdate();
		//ɾ���û��������competence_userdept�Ĺ�ϵ
		sess.createSQLQuery("delete from competence_userdept s where s.userid = '"+userid+"'").executeUpdate();
		/*//ɾ���û����Զ���¼�뷽���Ĺ�ϵ
		sess.createSQLQuery("delete from COMPETENCE_USERSMTBUSINESS s where s.userid = '"+userid+"'").executeUpdate();
		//ɾ���û����Զ�����Ĺ�ϵ
		sess.createSQLQuery("delete from COMPETENCE_USERWEBOFFICE s where s.userid = '"+userid+"'").executeUpdate();
		//ɾ���û����Զ�����ͼ�Ĺ�ϵ
		sess.createSQLQuery("delete from COMPETENCE_USERQRYVIEW s where s.userid = '"+userid+"'").executeUpdate();
		//ɾ���û����Զ���ͳ�ƵĹ�ϵ
		sess.createSQLQuery("delete from COMPETENCE_USERTABLEINFO s where s.userid = '"+userid+"'").executeUpdate();*/
	}
	
	//�����û�
	@PageEvent("reset.onclick")
	public int reset() throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("���ã�����ѡ��Ҫ���ĵ��û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("���ã��޷����ĳ�������Ա");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				//˵���ǲ���
				//�жϲ�����Ȩ���������
				/*if(!StrideGroup()){
					this.setMainMessage("����������������");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				this.getExecuteSG().addExecuteCode("resetGroup();");
			}else{
				//�ж��û���Ȩ���������
				/*if(!StrideUser()){
					this.setMainMessage("�������������û�");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				this.getExecuteSG().addExecuteCode("resetUser();");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ��ܸ����û�(����)");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("���ã�����ѡ��Ҫ���ĵ��û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("���ã��޷����ĳ�������Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user where userid = '"+userid+"' and userid not in('U001','40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002')").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			//˵���ǲ���
			//�жϲ�����Ȩ���������
			if(!StrideGroup()){
				this.setMainMessage("����������������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("resetGroup();");
		}else{
			//�ж��û���Ȩ���������
//			if(!StrideUser()){
//				this.setMainMessage("�������������û�");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			this.getExecuteSG().addExecuteCode("resetUser();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//����˵�����Ȩ��
	@PageEvent("personFunc.onclick")
	public int personFunc() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���й���Ȩ�޲���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("personFunction();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
//		if(!("3".equals(user.getUsertype())||"1".equals(user.getUsertype()))){
//			throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܽ���");
//		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա���й���Ȩ�޲���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("personFunction();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�����ѯ��ͼ����Ȩ��
		@PageEvent("personFuncQuery.onclick")
		public int personFuncQuery() throws RadowException{
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���й���Ȩ�޲���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("personFuncQuery();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//�����Ṧ��Ȩ��
		@PageEvent("personFuncBook.onclick")
		public int personFuncBook() throws RadowException{
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���й���Ȩ�޲���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("personFuncBook();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//����Զ��������Ȩ��
		@PageEvent("personFuncWin.onclick")
		public int personFuncWin() throws RadowException{
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���й���Ȩ�޲���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("personFuncWin();");
			return EventRtnType.NORMAL_SUCCESS;
		}
	
		// �����¡����   add LINJUN
		@PageEvent("personRange3.onclick")
		public int personRange3() throws RadowException, PrivilegeException {
			String cueUserid = SysUtil.getCacheCurrentUser().getId();
			System.out.println("personRange3(��¡)*******cueUserid="+cueUserid);
			//���г����û�system--------40288103556cc97701556d629135000f
			if("40288103556cc97701556d629135000f".equals(cueUserid)){
				String userid = this.getPageElement("userid").getValue();
				System.out.println("personRange3(��¡)*******userid="+userid);
				if (userid == null || "".equals(userid)) {
					this.setMainMessage("����ѡ���û�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				HBSession sess = HBUtil.getHBSession();
				Object obj = sess.createSQLQuery(
						"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
				int i = Integer.parseInt("" + obj);
				if (i == 0) {
					this.setMainMessage("����ѡ���û�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
					this.setMainMessage("�޷��Գ�������Ա���з�Χ�������");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/*//��ȡ�ϼ�����ԱID  ���ڶԸ��û�����Ȩ�޷�Χ����
				String overUserid = NewRangePageModel.getOverUserID(userid);
				if("".equals(overUserid)){
					this.setMainMessage("�޷�ȷ���ϼ���Χ������ϵϵͳ����Ա");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				//ͨ����ѯcompetence_userdept,���жϸ��û��Ƿ��Ѿ����й���Ȩ���Ѿ���Ȩ�������л���
				Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
				int j = Integer.parseInt("" + obj2);
				/*//�ж��û���Ȩ���������
				if(!StrideUser()){
					this.setMainMessage("�������������û�");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				this.getExecuteSG().addExecuteCode("personRange('"+userid+(j==0?"":"&1")+"');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			System.out.println("personRange3(��¡)*******user="+user);
			if(!("3".equals(user.getUsertype()) || "1".equals(user.getUsertype()))){
				throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܽ���");
			}
			String userid = this.getPageElement("userid").getValue();
			if (userid == null || "".equals(userid)) {
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery(
					"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt("" + obj);
			if (i == 0) {
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���з�Χ�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//��ȡ�ϼ�����ԱID  ���ڶԸ��û�����Ȩ�޷�Χ����
			String overUserid = NewRangePageModel.getOverUserID(userid);
			if("".equals(overUserid)){
				this.setMainMessage("�޷�ȷ���ϼ���Χ������ϵϵͳ����Ա");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//ͨ����ѯcompetence_userdept,���жϸ��û��Ƿ��Ѿ����й���Ȩ���Ѿ���Ȩ�������л���
			Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
			
			int j = Integer.parseInt("" + obj2);
			//�ж��û���Ȩ���������
//			if(!StrideUser()){
//				this.setMainMessage("�������������û�");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			this.getExecuteSG().addExecuteCode("personRange3('"+userid+(j==0?"":"&1")+"');");
			return EventRtnType.NORMAL_SUCCESS;
			
		}	
		
	// ���������Ա����Ȩ��
	@PageEvent("personRange.onclick")
	public int personRange() throws RadowException, PrivilegeException {
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if (userid == null || "".equals(userid)) {
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery(
					"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt("" + obj);
			if (i == 0) {
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���з�Χ�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//��ȡ�ϼ�����ԱID  ���ڶԸ��û�����Ȩ�޷�Χ����
			String overUserid = NewRangePageModel.getOverUserID(userid);
			if("".equals(overUserid)){
				this.setMainMessage("�޷�ȷ���ϼ���Χ������ϵϵͳ����Ա");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//ͨ����ѯcompetence_userdept,���жϸ��û��Ƿ��Ѿ����й���Ȩ���Ѿ���Ȩ�������л���
			Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
			int j = Integer.parseInt("" + obj2);
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("personRange('"+userid+(j==0?"":"&1")+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!("3".equals(user.getUsertype()) || "1".equals(user.getUsertype()))){
			throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܽ���");
		}
		String userid = this.getPageElement("userid").getValue();
		if (userid == null || "".equals(userid)) {
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery(
				"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt("" + obj);
		if (i == 0) {
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա���з�Χ�������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*//��ȡ�ϼ�����ԱID  ���ڶԸ��û�����Ȩ�޷�Χ����
		String overUserid = NewRangePageModel.getOverUserID(userid);
		if("".equals(overUserid)){
			this.setMainMessage("�޷�ȷ���ϼ���Χ������ϵϵͳ����Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		//ͨ����ѯcompetence_userdept,���жϸ��û��Ƿ��Ѿ����й���Ȩ���Ѿ���Ȩ�������л���
		Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
		
		int j = Integer.parseInt("" + obj2);
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("personRange('"+userid+(j==0?"":"&1")+"');");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	// ���������Ա����Ȩ��(���Ȩ��)
	@PageEvent("personRangeLL.onclick")
	public int personRangeLL() throws RadowException, PrivilegeException {
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if (userid == null || "".equals(userid)) {
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery(
					"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt("" + obj);
			if (i == 0) {
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���з�Χ�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//��ȡ�ϼ�����ԱID  ���ڶԸ��û�����Ȩ�޷�Χ����
			String overUserid = NewRangePageModel.getOverUserID(userid);
			if("".equals(overUserid)){
				this.setMainMessage("�޷�ȷ���ϼ���Χ������ϵϵͳ����Ա");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//ͨ����ѯcompetence_userdept,���жϸ��û��Ƿ��Ѿ����й���Ȩ���Ѿ���Ȩ�������л���
			Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept_look t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
			int j = Integer.parseInt("" + obj2);
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("personRangeLL2('"+userid+(j==0?"":"&1")+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!("3".equals(user.getUsertype()) || "1".equals(user.getUsertype()))){
			throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܽ���");
		}
		String userid = this.getPageElement("userid").getValue();
		if (userid == null || "".equals(userid)) {
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery(
				"select count(*) from smt_user t where t.userid = '" + userid + "' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt("" + obj);
		if (i == 0) {
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա���з�Χ�������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*//��ȡ�ϼ�����ԱID  ���ڶԸ��û�����Ȩ�޷�Χ����
		String overUserid = NewRangePageModel.getOverUserID(userid);
		if("".equals(overUserid)){
			this.setMainMessage("�޷�ȷ���ϼ���Χ������ϵϵͳ����Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		//ͨ����ѯcompetence_userdept,���жϸ��û��Ƿ��Ѿ����й���Ȩ���Ѿ���Ȩ�������л���
		Object obj2 = sess.createSQLQuery("select count(*) from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").uniqueResult();
		int j = Integer.parseInt("" + obj2);
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("personRangeLL2('"+userid+(j==0?"":"&1")+"');");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	//�����Ϣ��Ȩ��tableCol
	@PageEvent("tableCol.onclick")
	public int tableCol() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա������Ϣ��Ȩ�޲���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("tableCol('"+userid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!("3".equals(user.getUsertype())||"1".equals(user.getUsertype()))){
			throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܽ���");
		}
		
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա������Ϣ��Ȩ�޲���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("tableCol('"+userid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�����Ϣ��Ȩ��table
	@PageEvent("table.onclick")
	public int table() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա������Ϣ��Ȩ�޲���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("table('"+userid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"3".equals(user.getUsertype())){
			throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܽ���");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա������Ϣ��Ȩ�޲���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("table('"+userid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	// �û����͸���
	@PageEvent("changeType.onclick")
	public int changeType() throws RadowException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("����ѡ���û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("�޷��Գ�������Ա���в���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*//�ж��û���Ȩ���������
			if(!StrideUser()){
				this.setMainMessage("�������������û�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			this.getExecuteSG().addExecuteCode("changeUserType('"+userid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"3".equals(user.getUsertype())){
			throw new RadowException("ֻ�а�ȫ����Ա��ݲ��ܲ���");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա���в���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�ж��û���Ȩ���������
//		if(!StrideUser()){
//			this.setMainMessage("�������������û�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		this.getExecuteSG().addExecuteCode("changeUserType('"+userid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*//����̳й���ԱȨ��
	@PageEvent("inherit.onclick")
	public int inherit() throws RadowException, PrivilegeException{
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("�޷��Գ�������Ա���м̳в���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("����ѡ���û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�Լ��޷�����ȥ�̳��ϼ�����Ա��Ȩ��
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
		if(cueUserid.equals(userid)){
			this.setMainMessage("�޷����Լ����м̳в���������ϵ�ϼ�����Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//����userid��ȡ�����ŵĹ���ԱID
		String overUserid = NewRangePageModel.getOverUserID(userid);
		UserVO overVo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(overUserid);
		UserVO vo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		String overName = overVo.getName();
		String name = vo.getName();
		
		this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','ȷ��������Ա   "+overName+" ������Ȩ�޼̳и��û�    "+name+" ��',300,function(id){" +
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
		//��ȡ�ϼ�����Ա�Ļ�������Ա�����������Ϣ������Ϣ��
		//���ڲ����Զ���Ĳ���
		HBSession sess = HBUtil.getHBSession();
		
		//������Ȩ
		// ��ɾ��
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
			// ��ѯ�����е���ʱ������Ȩ�Ļ���
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
			throw new RadowException("�̳�ʧ��");
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

		// �û����������������
		// �Ƚ���ɾ��
		sess.createSQLQuery(" delete from competence_usermanager where userid = '" + userid + "'").executeUpdate();
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
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

		// �û�����������Ա
		// �Ƚ���ɾ��
		sess.createSQLQuery(" delete from COMPETENCE_SUBPERSON where userid = '" + userid + "'").executeUpdate();
		//------����·���
		try {
			sess.createSQLQuery("ALTER TABLE COMPETENCE_SUBPERSON ADD PARTITION p"+userid.substring(22)+" VALUES ('"+userid+"')").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			//�������˵���Ѿ��и÷���
		}
		String rysql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
			rysql = "select a01.a0000 from a01 where a01.status != '4'";//��ȡ������
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
			throw new RadowException("�̳�ʧ��");
		} finally {
			try {
				pstmt2.close();
				conn2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//�û�����������Ϣ��
		sess.createSQLQuery(" delete from competence_usertable where userid = '" + userid + "'").executeUpdate();
		
		Connection conn3 = sess.connection();
		PreparedStatement pstmt3 = null;
		int k = 0;
		
		String xxjsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
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
				throw new RadowException("�̳�ʧ��");
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
				throw new RadowException("�̳�ʧ��");
			} finally {
				try {
					pstmt3.close();
					conn3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//�û�����������Ϣ��
		sess.createSQLQuery(" delete from competence_usertablecol where userid = '" + userid + "'").executeUpdate();
		
		Connection conn4 = sess.connection();
		PreparedStatement pstmt4 = null;
		int m = 0;
		
		String xxxsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
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
				throw new RadowException("�̳�ʧ��");
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
				throw new RadowException("�̳�ʧ��");
			} finally {
				try {
					pstmt4.close();
					conn4.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//������   ���й���Ȩ�޵ļ̳�
		inheritOtherPower(sess,userid,overUserid);
		
		//this.setMainMessage("�̳й���ԱȨ�޳ɹ�");
		this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('��Ϣ��ʾ', '�̳й���ԱȨ�޳ɹ�', function(e){ if ('ok' == e){var tree = Ext.getCmp('treegrid');tree.root.reload();}});");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
/*	private void inheritOtherPower(HBSession sess, String userid,
			String overUserid) throws RadowException, PrivilegeException {
		// �û����������Զ����
		sess.createSQLQuery(" delete from COMPETENCE_USERSMTBUSINESS where userid = '"+ userid + "'").executeUpdate();

		Connection conn5 = sess.connection();
		PreparedStatement pstmt5 = null;
		int n = 0;

		String zdybdsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
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
				throw new RadowException("�̳�ʧ��");
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
				throw new RadowException("�̳�ʧ��");
			} finally {
				try {
					pstmt5.close();
					conn5.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// �û����������Զ�����
		sess.createSQLQuery(" delete from COMPETENCE_USERWEBOFFICE where userid = '"+ userid + "'").executeUpdate();

		Connection conn = sess.connection();
		PreparedStatement pstmt = null;
		int m = 0;

		String zdybcsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
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
				throw new RadowException("�̳�ʧ��");
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
				throw new RadowException("�̳�ʧ��");
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// �û����������Զ�����ͼ
		sess.createSQLQuery(" delete from COMPETENCE_USERQRYVIEW where userid = '"+ userid + "'").executeUpdate();

		Connection conn2 = sess.connection();
		PreparedStatement pstmt2 = null;
		int x = 0;

		String zdystsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
			zdystsql = "select q.qvid from qryview q where q.type = '1'";

			List<Object> list2 = sess.createSQLQuery(zdystsql).list();
			//�����system��ֱ�ӰѲ�ѯ������Ȩ�޸���ǰ�û�
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
				throw new RadowException("�̳�ʧ��");
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
				throw new RadowException("�̳�ʧ��");
			} finally {
				try {
					pstmt2.close();
					conn2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		
		// �û����������Զ���ͳ��
		sess.createSQLQuery(" delete from COMPETENCE_USERTABLEINFO where userid = '"+ userid + "'").executeUpdate();

		Connection conn3 = sess.connection();
		PreparedStatement pstmt3 = null;
		int y = 0;

		String zdytjsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
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
				throw new RadowException("�̳�ʧ��");
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
				throw new RadowException("�̳�ʧ��");
			} finally {
				try {
					pstmt3.close();
					conn3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// �û����������˵�Ȩ��
		UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		
		sess.createSQLQuery(" delete from smt_userfunction where userid = '"+ userid + "'").executeUpdate();

		Connection conn4 = sess.connection();
		PreparedStatement pstmt4 = null;
		int z = 0;

		String csqxsql = "";
		if ("40288103556cc97701556d629135000f".equals(overUserid)) {// �ϼ�����ԱΪsystem
			csqxsql = "select f.functionid from smt_function f,smt_resource r where f.functionid = r.resourceid and r.status = '1' order by f.parent,f.orderno";

			List<Object> list2 = sess.createSQLQuery(csqxsql).list();
			
			if("2".equals(user.getUsertype())){//��ͨ�û�,ȥ��ϵͳ����Ȩ��
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
				throw new RadowException("�̳�ʧ��");
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
			
			if("2".equals(user.getUsertype())){//��ͨ�û�,ȥ��ϵͳ����Ȩ��
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
				throw new RadowException("�̳�ʧ��");
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
	 * ��������
	 * userid ���û�ID
	 * b0111 ����������ID
	 * 
	 * ע���÷����Ƕ�user�ģ���������Ա���ϼ�����Ա�����ϼ�����Ա...���л�����Ȩ��������user������Ȩ��Ҳ����������...������ͨ�û�
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void increaseB0111(HBSession sess,String userid,String b0111){
		Object[] obj = (Object[]) sess.createSQLQuery("select s.isleader,s.dept from smt_user s where s.userid = '"+userid+"' and s.useful = '1'").uniqueResult();
		if(obj!=null){
			String isleader = ""+obj[0];
			String dept = ""+obj[1];
			if("0".equals(isleader)){//����Ǳ����ŵ���ͨ�û������ҵ������ŵĹ���Ա���л�����Ȩ
				Object ob = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				if(ob!=null){
					sess.createSQLQuery("insert into competence_userdept values (sys_guid(),'"+ob+"','"+b0111+"','1')").executeUpdate();
				}
			}
			//���������ϼ������ϼ�...����Ա���л�����Ȩ
			while(!"G0000".equals(dept)){//ѭ������ϵͳֹͣ
				//��ȡ�ϼ�����ID
				Object o = sess.createSQLQuery("select g.sid from smt_usergroup g where g.id = '"+dept+"'").uniqueResult();
				dept = ""+o;
				//��ȡ�ϼ����ŵĹ���Ա�û�ID�����л�����Ȩ
				Object overUserid = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				sess.createSQLQuery("insert into competence_userdept values (sys_guid(),'"+overUserid+"','"+b0111+"','1')").executeUpdate();
			}
		}
		
	}
	
	
	/**
	 * ����ɾ��
	 * b0111����������ID
	 * ע��ɾ������ʱ���Ƕ�competence_userdept�������ж�Ӧ��b0111����ɾ���������û�
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void deleteB0111(HBSession sess , String b0111){
		sess.createSQLQuery("delete from competence_userdept where b0111 = '"+b0111+"'").executeUpdate();
		sess.createSQLQuery("delete from smt_fileupload where fileuser = '"+b0111+"'").executeUpdate();	
	}
	
	
	/**
	 * ��Ա����
	 * userid ���û�ID
	 * a0000 ����Ա����ID
	 *
	 * ע���÷����Ƕ�user�ģ���������Ա���ϼ�����Ա�����ϼ�����Ա...������Ա��Ȩ��������user������Ȩ��Ҳ����������...������ͨ�û�
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void increaseA0000(HBSession sess,String userid,String a0000){
		Object[] obj = (Object[]) sess.createSQLQuery("select s.isleader,s.dept from smt_user s where s.userid = '"+userid+"' and s.useful = '1'").uniqueResult();
		if(obj!=null){
			String isleader = ""+obj[0];
			String dept = ""+obj[1];
			if("0".equals(isleader)){//����Ǳ����ŵ���ͨ�û������ҵ������ŵĹ���Ա������Ա��Ȩ
				Object ob = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				if(ob!=null){
					sess.createSQLQuery("insert into competence_subperson values (sys_guid(),'"+ob+"','"+a0000+"','1')").executeUpdate();
				}
			}
			//���������ϼ������ϼ�...����Ա������Ա��Ȩ
			while(!"G0000".equals(dept)){//ѭ������ϵͳֹͣ
				//��ȡ�ϼ�����ID
				Object o = sess.createSQLQuery("select g.sid from smt_usergroup g where g.id = '"+dept+"'").uniqueResult();
				dept = ""+o;
				//��ȡ�ϼ����ŵĹ���Ա�û�ID��������Ա��Ȩ
				Object overUserid = sess.createSQLQuery("select s.userid from smt_user s where s.dept = '"+dept+"' and s.useful = '1' and s.isleader = '1'").uniqueResult();
				if(!"40288103556cc97701556d629135000f".equals(overUserid)){//system�û�����Ҫ��Ȩcompetence_subperson
					sess.createSQLQuery("insert into competence_subperson values (sys_guid(),'"+overUserid+"','"+a0000+"','1')").executeUpdate();
				}
			}
		}
		
	}
	
	
	/**
	 * ��Աɾ�� 
	 * a0000����Ա����ID
	 * ע��ɾ����Աʱ���Ƕ�competence_subperson�������ж�Ӧ��a0000����ɾ���������û�
	 */
	@com.insigma.odin.framework.radow.annotation.Transaction
	public static void deleteA0000(HBSession sess , String a0000){
		sess.createSQLQuery("delete from competence_subperson where b0111 = '"+a0000+"'").executeUpdate();	
	}
	
	//�����û�
	@PageEvent("setUserDept.onclick")
	public int setUserDept() throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//���г����û�system--------40288103556cc97701556d629135000f
		if("40288103556cc97701556d629135000f".equals(cueUserid)){
			String userid = this.getPageElement("userid").getValue();
			if(userid==null||"".equals(userid)){
				this.setMainMessage("���ã�����ѡ��Ҫ���ĵ��û�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
				this.setMainMessage("���ã��޷����ĳ�������Ա");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select count(*) from smt_user where dept is null and userid = '"+userid+"' ").uniqueResult();
			int i = Integer.parseInt(""+obj);
			if(i==0){
				this.setMainMessage("��ѡ��δ�����û���");
			}else{
				//this.getExecuteSG().addExecuteCode("setUserDept();");
				this.getExecuteSG().addExecuteCode("setUserDeptWin('"+userid+"');");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(!"1".equals(user.getUsertype())){
			throw new RadowException("ֻ��ϵͳ����Ա��ݲ��ܸ����û�(����)");
		}
		String userid = this.getPageElement("userid").getValue();
		if(userid==null||"".equals(userid)){
			this.setMainMessage("���ã�����ѡ��Ҫ���ĵ��û�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("U001".equals(userid)||"40288103556cc97701556d629135000f".equals(userid)||"4028810f5f2aed67015f2aefeeee0002".equals(userid)){
			this.setMainMessage("���ã��޷����ĳ�������Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select count(*) from smt_user where dept is null and userid = '"+userid+"' ").uniqueResult();
		int i = Integer.parseInt(""+obj);
		if(i==0){
			this.setMainMessage("��ѡ��δ�����û���");
		}else{
			//�ж��û���Ȩ���������
			this.getExecuteSG().addExecuteCode("setUserDeptWin('"+userid+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
