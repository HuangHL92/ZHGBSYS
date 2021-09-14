package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class MechanismComWindowPageModel extends PageModel {
	private List aclList = null;
	private static int index = 0;
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	
	public MechanismComWindowPageModel(){
		areaInfo.put("areaid", "-1");
	}
	
	@Override
	public int doInit() throws RadowException {
		
/*		String userid = this.getRadow_parent_data();
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = user.getOtherinfo()+"1";*/
//		this.getExecuteSG().addExecuteCode("checkhorizon(document.getElementById('"+id+"'));");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException, AppException {
		
		String userid =this.getParameter("userid");
		StringBuffer jsonStr = new StringBuffer();
		String cueUsername = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
		if("admin".equals(cueUsername)){
			jsonStr.append("[");
			try {
				jsonStr.append(getJeson(userid,cueUsername));
			} catch (RadowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//jsonStr.append("[{task:'ColumnTree Example',duration:'3 hours',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\"  name=\"KD\"/>',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[{task:'Abstract rendering in TreeNodeUI',duration:'15 min',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'},{task:'Test and make sure it works',duration:'1 hour',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}] }]");
			jsonStr.append("]");
		}else{
			jsonStr.append("[");
			try {
				jsonStr.append(getJeson(userid,cueUsername));
			} catch (RadowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonStr.append("]");
		}
	
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
			}
		
	
	
	private String getJeson(String userid,String cueUsername) throws RadowException, AppException{
		
		StopWatch w = new StopWatch();
		w.start();
		String node1 = this.getParameter("node");
		String returnValue =  "";
		// 读取层次数据结果集列表    
		List dataList = VirtualDataGenerator.getVirtualResult(cueUsername,userid,node1);
		if(dataList.size()>0){
			returnValue = makeJSON(dataList);
		}
		w.stop();
		CommonQueryBS.systemOut("机构数组装结束:"+w.elapsedTime());
	   return returnValue;
	}
	
	public String makeJSON(List dataList){
		
		HashMap nodemap = new HashMap();
		String id = "";
		String text = "";
		String parentId = "";
		String sortId = "";
		String qxtype = "";
		String count1 = "";
		String type1 = "";
		StringBuffer result = new StringBuffer();
		
		Iterator iter = dataList.iterator();
		while(iter.hasNext()){
			nodemap = (HashMap) iter.next();
			count1 = (String) nodemap.get("count1");
			id = (String) nodemap.get("b0111");
			text = (String) nodemap.get("b0101");
			parentId = (String) nodemap.get("b0121");
			sortId = (String) nodemap.get("sortid");
			qxtype = (String) nodemap.get("type");
			type1 = (String) nodemap.get("type2");
			
			if("机构树".equals(text)){
				continue;
			}
			String childsll =parentId+"0";
			String childswh =parentId+"1";
			String read = "";
			String write = "";
			if("1".equals(qxtype)){
				read="checked";
				write="checked";
			}else if("0".equals(qxtype)){
				read="checked";
				write="indeterminate";
			}else{
				read="indeterminate";
				write="indeterminate";
			}
			
			String icon ="";
			if(nodemap.get("b0194").equals("1")){
				icon="./main/images/icons/companyOrgImg2.png";
			}else if(nodemap.get("b0194").equals("2")){
				icon="./main/images/tree/leaf.gif";
			}else if(nodemap.get("b0194").equals("3")){
				icon="./main/images/tree/folder.gif";
			}
			HBSession sess = HBUtil.getHBSession();
			String cueUserName = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
			if("admin".equals(cueUserName) || "system".equals(cueUserName)){
				result.append( "{"   
						+ "task : '" +  text + "',id:'"+id+"',icon:'"+icon+"'"   
						+ ",duration:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+read+" id=\""+id+"0"+"\"  name=\""+childsll+"\"/>'");   
				
				result.append( ", leaf : "+hasChildren(count1));   
				result.append(",user:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+write+" id=\""+id+"1"+"\"  name=\""+childswh+"\"/>'");
				
			}else{
				//查询授权用户是否对机构节点有权限，如果没有权限则将机构后面的复选框置灰（不可点击）
				if("1".equals(type1) || "0".equals(type1)){
					if("0".equals(type1)){
					result.append( "{"   
							+ "task : '" +  text + "',id:'"+id+"',icon:'"+icon+"'"   
							+ ",duration:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+read+" id=\""+id+"0"+"\"  name=\""+childsll+"\"/>'");   
					
					result.append( ", leaf : "+hasChildren(count1));   
					result.append(",user:'<input type=\"checkbox\" disabled=\"true\" onclick=\"checkhorizon(this)\" "+write+" id=\""+id+"1"+"\"  name=\""+childswh+"\"/>'");
					}else if("1".equals(type1)){
						result.append( "{"   
								+ "task : '" +  text + "',id:'"+id+"',icon:'"+icon+"'"   
								+ ",duration:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+read+" id=\""+id+"0"+"\"  name=\""+childsll+"\"/>'");   
						
						result.append( ", leaf : "+hasChildren(count1));   
						result.append(",user:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+write+" id=\""+id+"1"+"\"  name=\""+childswh+"\"/>'");
					}
					
				}else{
					result.append( "{"   
							+ "task : '" +  text + "',id:'"+id+"',icon:'"+icon+"'"   
							+ ",duration:'<input type=\"checkbox\" disabled=\"true\" onclick=\"checkhorizon(this)\" "+read+" id=\""+id+"0"+"\"  name=\""+childsll+"\"/>'");   
					
					result.append( ", leaf : "+hasChildren(count1));   
					result.append(",user:'<input type=\"checkbox\" disabled=\"true\" onclick=\"checkhorizon(this)\" "+write+" id=\""+id+"1"+"\"  name=\""+childswh+"\"/>'");
				}
			}
			result.append(",uiProvider:'col'}");
			result.append(",");
		}
		result.substring(0, result.length()-1);
		return result.toString();
	}
	
	private String hasChildren(String id){
		if(id!=null && !"0".equals(id) && !"".equals(id)){
			return "false";
		}else{
			return "true";
		}
	}
	@PageEvent("dogrant")
	public void save(String value) throws RadowException {
		
		StopWatch w = new StopWatch();
		w.start();
		StopWatch w1 = new StopWatch();
		w1.start();
		//获取当前登录用户的编号
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String cueLoginname = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();

		//获取被授权用户编号（对其授权的用户编号，不是操作用户编号）
		String userid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		//查询用户信息
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		String id = "";//定义变量：机构编号
		String type = "";//定义变量：权限类型
		String sChecked = "";//定义变量：是否被勾选
		String sValue = "";//定义变量：checkbox的值
		String delSql = "";//删除语句的变量
		List alreadyExeB0111In = new ArrayList();//定义list变量，用以存储已经执行过批量插入的机构编号
		List alreadyExeB0111Out = new ArrayList();//定义list变量，用以存储已经执行过批量删除的机构编号
		List alreadyExeB0111Up = new ArrayList();//定义list变量，用以存储已经执行过批量更新的机构编号
		//先将对应的数据全部删除，后面再进行插入
		
		//定义变量，记录操作的用户是否已经进行授权
		boolean isAuthed = true;
		//查询操作的用户是否已经机构授权
		List userdep_list = sess.createQuery("from UserDept where userid = '"+userid+"'").list();
		if(userdep_list.size() == 0){
			isAuthed = false;
		}
		//截取机构编号
		String[] ids = value.split(",");
		w1.stop();
		CommonQueryBS.systemOut("机构授权前准备："+w1.elapsedTime());
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//循环处理机构授权
		for(int i=0;i<ids.length;i++){
			String[] values = ids[i].split(":");
			id = values[0].substring(0,values[0].length()-1);
			type = values[0].substring(values[0].length()-1);
			sChecked = values[1];
			sValue = values[2];
			
			String[] values1 = ids[i+1].split(":");
			String id_1 = values1[0].substring(0,values[0].length()-1);
			String type_1 = values1[0].substring(values[0].length()-1);
			String sChecked_1 = values1[1];
			String sValue_1 = values1[2];
			//当该节点为在“勾选下级”被选中后才被勾选，将节点机构及其下级机构查询并放入到hashSet中
			if("1".equals(sValue)){
				if(!alreadyExeB0111In.contains(id.substring(0, id.length()-4))){
					//当登陆用户是system和admin时，包含下级时对机构的所有下级机构进行授权，不涉及用户的机构权限问题。
					if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
						delSql = " AND B0111 LIKE '"+id+"%' ";
						hql = " AND B1.B0111 LIKE '"+id+"%' ";
						
					}else{//当登陆用户非system和admin时，需要关联登陆用户的机构权限表，仅授权自己有权限的机构
						if(DBType.ORACLE==DBUtil.getDBType()){
							delSql = " AND B0111 IN(SELECT B0111 FROM COMPETENCE_USERDEPT B1 WHERE B1.B0111 LIKE '"+id+"%' AND B1.USERID = '"+cueUserid+"') ";
						}else{
							delSql = "AND B0111 in(SELECT a.B0111 FROM " +
									"(SELECT B0111 FROM COMPETENCE_USERDEPT B1 WHERE B1.B0111 LIKE '"+id+"%' AND B1.USERID = '"+cueUserid+"'  ) a)";
						}
						
						hql = "  FROM COMPETENCE_USERDEPT B1 WHERE B1.b0111 LIKE '"+id+"%' AND B1.USERID = '"+cueUserid+"'   ";
					}
					if("true".equals(sChecked_1)){//当维权权限被选中时，机构授权记录的type值为1
						if(isAuthed){//当操作的用户还没有授权时，不做删除操作
							deleteUserDept(delSql,userid);
						}
						if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
							saveUpdate(hql,userid,"1","SQL");
						}else{
							saveUpdate(hql,userid,"1","SQLNS");
						}
					}else{//当浏览权限被选中时，机构授权记录的type值为0
						if(isAuthed){//当操作的用户还没有授权时，不做删除操作
							deleteUserDept(delSql,userid);
						}
						if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
							saveUpdate(hql,userid,"0","SQL");
						}else{
							saveUpdate(hql,userid,"0","SQLNS");
						}
					}
				}
				alreadyExeB0111In.add(id);
			}
			//当该节点为在“勾选下级”被选中前已被勾选，则仅保存节点机构
			if("0".equals(sValue)){
				if("true".equals(sChecked_1)){
					if(isAuthed){//当操作的用户还没有授权时，不做删除操作
						deleteUserDept(" AND B0111 = '"+id+"'",userid);
					}
					saveUpdate(id,userid,"1","value");
				}else{
					if(isAuthed){//当操作的用户还没有授权时，不做删除操作
						deleteUserDept(" AND B0111 = '"+id+"'",userid);
					}
					saveUpdate(id,userid,"0","value");
				}
			}
			//当该节点为在“勾选下级”被选中后才被取消，将该节点及其下级节点移出hashSet
			//包含下级情况下，当机构节点的浏览权限取消时，取消选中机构下的所有机构授权
			if("2".equals(sValue)){
				if(!alreadyExeB0111Out.contains(id.substring(0, id.length()-4))){
					
					if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
						delSql = " AND B0111 LIKE '"+id+"%'";
					}else{
						delSql = " AND B0111 IN(SELECT a.B0111 FROM (SELECT B0111 FROM COMPETENCE_USERDEPT " +
								" WHERE B0111 LIKE '"+id+"%' AND USERID = '"+cueUserid+"')a )";
					}
					deleteUserDept(delSql,userid);
				}
				alreadyExeB0111Out.add(id);
			}else if("2".equals(sValue_1)){//包含下级情况下，当取消机构的维护权限，将机构权限更新为浏览权限
				if(!alreadyExeB0111Up.contains(id.substring(0, id.length()-4))){
					
					if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
						hql = " AND B0111 LIKE '"+id+"%'";
					}else{
						hql = " AND B0111 IN(SELECT a.B0111 FROM (SELECT B0111 FROM COMPETENCE_USERDEPT " +
								"WHERE B0111 LIKE '"+id+"%' AND USERID = '"+cueUserid+"') a )";
					}
					saveUpdate(hql,userid,"0","update");
				}
				alreadyExeB0111Up.add(id);
			}
			//当该节点为在“勾选下级”被选中前已被取消，仅将该节点移出hashSet
			if("3".equals(sValue)){
				deleteUserDept(" AND B0111 = '"+id+"'",userid);
			}else if("3".equals(sValue_1)){
				saveUpdate(" AND B0111 = '"+id+"'",userid,"0","update");
			}
			i++;
		}
		if(DBType.ORACLE==DBUtil.getDBType()){
			//机构授权表索引重建，索引名需要与数据库的索引名一致
			try {
				sess.createSQLQuery("alter index IDX_B0111 rebuild ").executeUpdate();
				sess.createSQLQuery("alter index IDX_USERID rebuild ").executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else{
			
		}
		
		ts.commit();
		List list = new ArrayList();
		try {
			new LogUtil().createLog("69", "COMPETENCE_USERDEPT",user.getId(), user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//机构授权结束
		w.stop();
		CommonQueryBS.systemOut("整体授权时间："+w.elapsedTime());
		
		
		/*---------------------------------绿色的分割线------------------------------------------------*/
		//人员查看控制保存
		String userid1 = this.getRadow_parent_data();
		UserVO user1 = new UserVO();
		try {
			user1 = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid1);
		} catch (PrivilegeException e) {
			e.printStackTrace();
		}
		String rate = "";
		String empid = "";
//		Set<String> result = new HashSet<String>();
		Set<String> llset = new HashSet<String>();
		Set<String> whset = new HashSet<String>();
		String ids2 = this.getPageElement("ryIds").getValue();
		String[] a0165s = ids2.substring(0,ids2.length()-1).split(",");
		for (int i = 0; i < a0165s.length; i++) {
			String value1 = a0165s[i].split(":")[0];//code type
			String hz = value1.substring(value1.length()-1); //end of value1 0:liulan 1:weihu			
			String value2 = a0165s[i].split(":")[1];//checked
			if("false".equals(value2)){
				if("0".equals(hz)){
					llset.add(value1.substring(0, value1.length()-1));
				}else{

					whset.add(value1.substring(0, value1.length()-1));
				}
			}
		}

		rate = StringUtils.join(llset.toArray(),",");
		empid = StringUtils.join(whset.toArray(),",");
		user1.setRate(null == rate || "".equals(rate) ? "" : "'"+rate.replace(",", "','")+"'");
		user1.setEmpid(null == empid || "".equals(empid) ? "" : "'"+empid.replace(",", "','")+"'");
		
		
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user1);
		} catch (PrivilegeException e) {
			
			e.printStackTrace();
		}
		//创建存放旧数据的对象
		UserVO user11 = new UserVO();
		
		try {
			//拷备数据
			PropertyUtils.copyProperties(user11, user11);
			new LogUtil().createLog("65", "SMT_USER",user11.getId(), user11.getLoginname(), "", new Map2Temp().getLogInfo(user11,user11));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		this.closeCueWindowByYes("win_pup");
	}
	//保存授权信息
	public void saveUpdate(String id,String userid,String type,String execMode){
		StopWatch w = new StopWatch();
		w.start();
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		if("SQL".equals(execMode)){
			if(DBType.ORACLE==DBUtil.getDBType()){
				sql = "INSERT INTO COMPETENCE_USERDEPT SELECT sys_guid(),'"+userid+"',B1.B0111,'"+type+"' FROM b01 B1 WHERE 1=1 "+id;
			}else{
				sql = "INSERT INTO COMPETENCE_USERDEPT SELECT uuid(),'"+userid+"',B1.B0111,'"+type+"' FROM b01 B1 WHERE 1=1 "+id;
			}
		}else if("value".equals(execMode)){
			if(DBType.ORACLE==DBUtil.getDBType()){
				sql = "INSERT INTO COMPETENCE_USERDEPT VALUES( sys_guid(),'"+userid+"','"+id+"','"+type+"') ";
			}else{
				sql = "INSERT INTO COMPETENCE_USERDEPT VALUES( uuid(),'"+userid+"','"+id+"','"+type+"') ";
			}
		}else if("update".equals(execMode)){
			sql = "UPDATE COMPETENCE_USERDEPT SET TYPE = '"+type+"' WHERE USERID = '"+userid+"' "+id;
		}else if("SQLNS".equals(execMode)){
			if("1".equals(type)){
				if(DBType.ORACLE==DBUtil.getDBType()){
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT sys_guid(),'"+userid+"',B1.B0111,TYPE "+id;
				}else{
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT uuid(),'"+userid+"',B1.B0111,TYPE "+id;
				}
			}else{
				if(DBType.ORACLE==DBUtil.getDBType()){
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT sys_guid(),'"+userid+"',B1.B0111,'"+type+"' "+id;
				}else{
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT uuid(),'"+userid+"',B1.B0111,'"+type+"' "+id;
				}
			}
		}
		sess.createSQLQuery(sql).executeUpdate();
		w.stop();
		CommonQueryBS.systemOut("插入或更新语句执行时间："+w.elapsedTime());
	}
	//删除授权信息
	public void deleteUserDept(String b0111s,String userid){
		StopWatch w3 = new StopWatch();
		w3.start();
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		if("null".equals(b0111s)){
			sql="DELETE FROM COMPETENCE_USERDEPT WHERE USERID='"+userid+"'";
		}else{
			sql = "DELETE FROM COMPETENCE_USERDEPT WHERE USERID='"+userid+"' "+b0111s;
		}
		sess.createSQLQuery(sql).executeUpdate();
		w3.stop();
		CommonQueryBS.systemOut("删除结束："+w3.elapsedTime());
	}
	
	public static String getQxtype(String userid,String groupid){
	    String sql = "select c.type from competence_userdept c,b01 a where c.b0111 = a.b0111 and c.userid = '"+userid+"' and a.b0111='"+groupid+"'";
	    String qxtype = (String)HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
	    if(null == qxtype){
	    	qxtype="0";
	    }
	    return qxtype;
	}
}
