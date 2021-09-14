package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.InfoGroupInfo;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.entity.UserInfoGroup;
import com.insigma.siis.local.business.entity.UserPerson;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PersonComWindowPageModel extends PageModel {
	private List aclList = null;
	private static String dept = "";
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	private static String operateType = "";
	private static String operateDept = "";
	public PersonComWindowPageModel(){
		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			
			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				area = (Object[]) sess.createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
				areaInfo.put("manager", "true");
			}else{
				area = (Object[]) sess.createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
				areaInfo.put("manager", "false");
			}
			if(area[2].equals("1")){
				area[2]="picOrg";
			}else if(area[2].equals("2")){
				area[2]="picInnerOrg";
			}else{
				area[2]="picGroupOrg";
			}
			areaInfo.put("areaname", area[0]);
			areaInfo.put("areaid", area[1]);
			areaInfo.put("picType", area[2]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public int doInit() throws RadowException {
		this.getPageElement("operateType").setValue("0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		HBSession sess = HBUtil.getHBSession();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		int nodelength = 0;
		String node1 = "";
		if(!"system".equals(user.getLoginname()) && "-1".equals(node)){
			node1 = "001";
		}else if(!"system".equals(user.getLoginname()) && !"-1".equals(node)){
			node1 = node;
		}
		
		List<HashMap> list_map = null;
	   
	   
	 //计算机构是否存在下级机构
	   String sql6 = "";
	   if("system".equals(user.getLoginname())){
		   sql6=" (select count(b.b0111) from b01 b where b.b0121 = a.b0111 ) as count1, ";
		}else{
			
			sql6 = "cc.count1 ,";
		}
	   
	   //定义查询sql主体
	   StringBuffer  str= new StringBuffer("select "+sql6+"a.b0111,a.b0101,a.b0121,a.b0194," +
	   		"(select c.type from competence_userdept c where c.b0111 = a.b0111 and c.userid = '"+user.getId()+"') type2 from B01 a  ") ;
	   //根据登陆用的不同，组装不同的条件语句
	   if("system".equals(user.getLoginname()) || "admin".equals(user.getLoginname())){
		   str.append(" where a.b0121 = '"+node+"'");
	   }else{
		   str.append(" join (select substr(t.b0111, 1, length('"+node1+"')+4) b01111," +
		   		"max(length(trim(substr(b0111, length('"+node1+"')+6, 3)))) count1" +
		   		" from competence_userdept t where t.b0111 like '"+node1+".%'" +
		   		" and t.userid = '"+user.getId()+"' " +
		   		" group by substr(t.b0111, 1, length('"+node1+"')+4)) cc on a.b0111 = cc.b01111");
	   }
	   str.append(" order by a.sortid");
	   
	   //查询符合要求的机构记录
		try {
			list_map = CommonQueryBS.getQueryInfoByManulSQL(str.toString());
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		//组装机构字符串
		StringBuffer jsonStr = new StringBuffer();
		String companyOrgImg = request.getContextPath()+"/pages/sysorg/org/images/companyOrgImg2.png";
		String insideOrgImg = request.getContextPath()+"/pages/sysorg/org/images/insideOrgImg1.png";
		String groupOrgImg = request.getContextPath()+"/pages/sysorg/org/images/groupOrgImg1.png";
		String path = companyOrgImg;
		
		if (list_map != null && !list_map.isEmpty()) {
			int i = 0;
			int last = list_map.size();
			String b0111 = "";
			String b0101 = "";
			String b0194 = "";
			String count1 = "";
			String type2 = "";
			HashMap nodemap = new HashMap();
			
			Iterator iter = list_map.iterator();
			while(iter.hasNext()){
				nodemap = (HashMap) iter.next();
				b0111 = (String) nodemap.get("b0111");
				b0101 = (String) nodemap.get("b0101");
				b0194 = (String) nodemap.get("b0194");
				count1 = (String) nodemap.get("count1");
				type2 = (String) nodemap.get("type2");
				if(i==0 && last==1) {
					if("2".equals(b0194)){
						path=insideOrgImg;
					}else if ("3".equals(b0194)){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append("[{\"text\" :\"" + b0101
							+ "\" ,\"id\" :\"" + b0111
							+ "\" ,\"leaf\":"+hasChildren(count1)+",\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
//					List<UserDept> UserDept = sess.createQuery("from UserDept where b0111 = '"+b0111+"' and userid = '"+user.getId()+"'").list();
					if("1".equals(type2) || "0".equals(type2)){
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ b0111 + "')\"");
					}else{
						jsonStr.append("\"#\"");
					}
					jsonStr.append("}]");
				}else if (i == 0) {
					if("2".equals(b0194)){
						path=insideOrgImg;
					}else if ("3".equals(b0194)){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append("[{\"text\" :\"" + b0101
							+ "\" ,\"id\" :\"" + b0111
							+ "\" ,\"leaf\":"+hasChildren(count1)+",\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					if("1".equals(type2) || "0".equals(type2)){
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ b0111 + "')\"");
					}else{
						jsonStr.append("\"#\"");
					}
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					if("2".equals(b0194)){
						path=insideOrgImg;
					}else if ("3".equals(b0194)){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append(",{\"text\" :\"" + b0101
							+ "\" ,\"id\" :\"" + b0111
							+ "\" ,\"leaf\":"+hasChildren(count1)+",\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					if("1".equals(type2) || "0".equals(type2)){
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ b0111 + "')\"");
					}else{
						jsonStr.append("\"#\"");
					}
					jsonStr.append("}]");
				} else {
					if("2".equals(b0194)){
						path=insideOrgImg;
					}else if ("3".equals(b0194)){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append(",{\"text\" :\"" + b0101
							+ "\" ,\"id\" :\"" + b0111
							+ "\" ,\"leaf\":"+hasChildren(count1)+",\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					if("1".equals(type2) || "0".equals(type2)){
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ b0111 + "')\"");
					}else{
						jsonStr.append("\"#\"");
					}
					jsonStr.append("}");
				}
				i++;
			}
//			for (B01 group : list) {
//			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	//根据机构树中选择的机构，查询机构下的人员信息
	@PageEvent("orgTreeGridJsonData")
	public int getOrgTreeGridJsonData() throws PrivilegeException, AppException, RadowException {
		//登录用户的用户编码
		String userid =this.getParameter("userid");
		//选中的机构编号
		String b0111 = dept;
		HBSession sess = HBUtil.getHBSession();
		//定义返回信息的json串
		StringBuffer jsonStr = new StringBuffer();
		//sql语句
		String sql = "";
		//判断类型，0为职务层次，1为管理类别，2为人员
		if("0".equals(operateType)){
			if(b0111!=null){
				if(DBType.ORACLE==DBUtil.getDBType()){
					
					sql="select cv.code_value,cv.code_name,cu.userpersonid from code_value cv,COMPETENCE_USERPERSON cu "+
					" where cv.code_value = cu.a0000(+) and cv.code_type = 'ZB09' and cu.userid(+)='"+userid+"' and cu.B0111(+)='"+b0111+"'" +
					" and cv.code_value not like '01_' and cv.code_value not in('01','02') order by cv.code_value";
				}else if(DBType.MYSQL==DBUtil.getDBType()) {
					sql="select cv.code_value,cv.code_name,cu.userpersonid from code_value cv LEFT JOIN COMPETENCE_USERPERSON cu"+
					" on cv.CODE_VALUE = cu.A0000 and cu.USERID='"+userid+"' and cu.B0111='"+b0111+"' where cv.code_type = 'ZB09'" +
							" and cv.code_value not like '01_' and cv.code_value not in('01','02') order by cv.code_value";
				}
				CommonQueryBS query_person = new CommonQueryBS();
				query_person.setConnection(HBUtil.getHBSession().connection());
				query_person.setQuerySQL(sql);
				Vector<?> vector_person = query_person.query();
				Iterator<?> iterator_person = vector_person.iterator();
				int i = 0;
				//开始组装json字符串
				jsonStr.append("[");
				
				while(iterator_person.hasNext()){
					HashMap person_hashmap = (HashMap) iterator_person.next();
					//定义变量，用以判断该人员是否已被选中
					String check="";
					if(person_hashmap.get("userpersonid") != null && !"".equals(person_hashmap.get("userpersonid"))){
						check="checked";
					}else{
						check="indeterminate";
					}
					
					if(i==0){
						jsonStr.append("{task:'"+person_hashmap.get("code_name")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+person_hashmap.get("code_value")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}else{
						jsonStr.append(",{task:'"+person_hashmap.get("code_name")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+person_hashmap.get("code_value")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}
					i++;
					
				}
				//组装结尾
				jsonStr.append("]");
			}
		}else if("1".equals(operateType)){
			if(b0111!=null){
				
				if(DBType.ORACLE==DBUtil.getDBType()){
					
					sql="select cv.code_value,cv.code_name,cu.userpersonid from code_value cv,COMPETENCE_USERPERSON cu "+
					" where cv.code_value = cu.a0000(+) and cv.code_type = 'ZB130' and cu.userid(+)='"+userid+"' and cu.B0111(+)='"+b0111+"' order by cv.code_value";
				}else if(DBType.MYSQL==DBUtil.getDBType()) {
					sql="select cv.code_value,cv.code_name,cu.userpersonid from code_value cv LEFT JOIN COMPETENCE_USERPERSON cu"+
					" on cv.CODE_VALUE = cu.A0000 and cu.USERID='"+userid+"' and cu.B0111='"+b0111+"' where cv.code_type = 'ZB130' order by cv.code_value";
				}
				CommonQueryBS query_person = new CommonQueryBS();
				query_person.setConnection(HBUtil.getHBSession().connection());
				query_person.setQuerySQL(sql);
				Vector<?> vector_person = query_person.query();
				Iterator<?> iterator_person = vector_person.iterator();
				int i = 0;
				//开始组装json字符串
				jsonStr.append("[");
				
				while(iterator_person.hasNext()){
					HashMap person_hashmap = (HashMap) iterator_person.next();
					//定义变量，用以判断该人员是否已被选中
					String check="";
					if(person_hashmap.get("userpersonid") != null && !"".equals(person_hashmap.get("userpersonid"))){
						check="checked";
					}else{
						check="indeterminate";
					}
					
					if(i==0){
						jsonStr.append("{task:'"+person_hashmap.get("code_name")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+person_hashmap.get("code_value")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}else{
						jsonStr.append(",{task:'"+person_hashmap.get("code_name")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+person_hashmap.get("code_value")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}
					i++;
					
				}
				//组装结尾
				jsonStr.append("]");
			}
		}else if("2".equals(operateType)){
			
			//如果选中机构树中的机构时，查询相关的人员信息
			if(b0111!=null){
				//String hql = "select a.a0000,a.from A02 a2,A01 a1,COMPETENCE_USERPERSON cu where a.a0201b='"+b0111+"'";
				//不同的数据库设置不同的sql
				if(DBType.ORACLE==DBUtil.getDBType()){
					
					sql = "select a2.a0000, c.b0111,a1.a0101"+
					" from A02 a2,A01 a1, COMPETENCE_USERPERSON c"+
					" where a2.a0000 = c.a0000(+)"+
					" and a1.a0000 = a2.a0000"+
					" and a2.a0201b = '"+b0111+"'"+
					" and c.userid(+) = '"+userid+"'" +
					" and c.b0111(+) =a2.a0201b "+
					" and a2.a0255 = '1'"+
					" and a1.a0163 = '1'" +
					" group by a2.a0000, c.b0111,a1.a0101";
				}else if(DBType.MYSQL==DBUtil.getDBType()) {
					sql = "select a2.a0000, c.b0111,a1.a0101 from (a02 a2 " +
					" LEFT JOIN COMPETENCE_USERPERSON c on a2.A0000=c.A0000 and c.USERID = '"+userid+"' and c.b0111 = a2.a0201b)" +
					" LEFT JOIN a01 a1 on a2.A0000 = a1.A0000 "+
					" where a2.A0201B = '"+b0111+"' " +
					" and a2.a0255 = '1'"+
					" and a1.a0163 = '1'" +
					" group by a2.a0000,a1.a0101";
				}
				CommonQueryBS query_person = new CommonQueryBS();
				query_person.setConnection(HBUtil.getHBSession().connection());
				query_person.setQuerySQL(sql);
				Vector<?> vector_person = query_person.query();
				Iterator<?> iterator_person = vector_person.iterator();
				int i = 0;
				//开始组装json字符串
				jsonStr.append("[");
				
				while(iterator_person.hasNext()){
					HashMap person_hashmap = (HashMap) iterator_person.next();
					//定义变量，用以判断该人员是否已被选中
					String check="";
					if(person_hashmap.get("b0111") != null && !"".equals(person_hashmap.get("b0111"))){
						check="checked";
					}else{
						check="indeterminate";
					}
					
					if(i==0){
						jsonStr.append("{task:'"+person_hashmap.get("a0101")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+person_hashmap.get("a0000")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}else{
						jsonStr.append(",{task:'"+person_hashmap.get("a0101")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+person_hashmap.get("a0000")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}
					i++;
					
				}
				//组装结尾
				jsonStr.append("]");
			}
		}
		dept=null;
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	@PageEvent("querybyid")
	public int queryById(String id) throws RadowException{
		dept = id;
		operateDept = id;
		String sOperateType = this.getPageElement("operateType").getValue();
		operateType = sOperateType;
		this.getPageElement("checkedgroupid").setValue(id);
		this.getExecuteSG().addExecuteCode("window.reloadGridTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String getName(HBSession sess,String a0000){
		String	name = (String) sess.createSQLQuery("select a0101 from a01 a where a.a0000='"+a0000+"'").uniqueResult();
		return name;
	}
	@PageEvent("dogrant")
	public void doGant(String value) throws RadowException, AppException{
		
		String userid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		String id = this.getPageElement("checkedgroupid").getValue();
		if("".equals(id)||id==null){
			this.setMainMessage("您好，请选择需要操作的信息项权限组");
			return;
		}
		if(value==null){
			deleteInfoGroupInfo("null",userid);
			List list = new ArrayList();
			try {
				new LogUtil().createLog("612", "COMPETENCE_USERPERSON",user.getId(),user.getLoginname(), "", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setMainMessage("授权成功！");
			this.closeCueWindowByYes("win_pup");
			return;
		}
		//先删除用户对应的信息记录
		String[] a0000s = value.split(",");
		//当操作类型为职务层次时，执行以下操作
		if("0".equals(operateType)){
			deleteInfoGroupInfo("null",userid);
			for(int i=0;i<a0000s.length;i++){
				String hql1="from A02 where a0221 = '"+a0000s[i]+"' and a0201b = '"+operateDept+"'";
				List<A02> a02_list = sess.createQuery(hql1).list();
				for(int j=0;j<a02_list.size();j++){
					saveUpdate(a02_list.get(j).getA0000(),a02_list.get(j).getA0201b(),userid);
				}
				saveUpdate(a0000s[i],operateDept,userid);
			}
		}else if("1".equals(operateType)){//当操作类型为管理类别时，执行以下操作
			deleteInfoGroupInfo("null",userid);
			for(int i=0;i<a0000s.length;i++){
				String sql="select a1.a0000 from A01 a1,A02 a2 where a1.a0000=a2.a0000 and a1.a0165 = '"+a0000s[i]+"' and a2.a0201b='"+operateDept+"'";
				CommonQueryBS query_person = new CommonQueryBS();
				query_person.setConnection(HBUtil.getHBSession().connection());
				query_person.setQuerySQL(sql);
				Vector<?> vector_person = query_person.query();
				Iterator<?> iterator_person = vector_person.iterator();
				while(iterator_person.hasNext()){
					HashMap hashmap = (HashMap) iterator_person.next();
					saveUpdate(hashmap.get("a0000").toString(),operateDept,userid);
				}
				saveUpdate(a0000s[i],operateDept,userid);
			}
		}else if("2".equals(operateType)){
			StringBuffer a0000s1 = new StringBuffer();
			for(int i=0;i<a0000s.length;i++){
				saveUpdate(a0000s[i],operateDept,userid);
				a0000s1.append("'"+a0000s[i]+"'"+",");
			}
			String a0000 = a0000s1.substring(0,a0000s1.length()-1);
			deleteInfoGroupInfo(a0000,userid);
		}
		List list = new ArrayList();
		try {
			new LogUtil().createLog("612", "COMPETENCE_USERPERSON",user.getId(),user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		this.closeCueWindowByYes("win_pup");
	}
	public void saveUpdate(String a0000,String b0101,String userid){
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		String hql = "from UserPerson t where t.a0000='"+a0000+"' and t.userid='"+userid+"' and b0111 = '"+operateDept+"' and operateType = '"+operateType+"'";
		List list = sess.createQuery(hql).list();
		if(list.size()==0){
			UserPerson ud = new UserPerson();
			ud.setA0000(a0000);
			ud.setUserid(userid);
			ud.setB0111(b0101);
			ud.setOPERATETYPE(operateType);
			sess.save(ud);
			ts.commit();
		}
	}
	
	public void deleteInfoGroupInfo(String a0000s,String userid){
		HBSession sess = HBUtil.getHBSession();
		String hql = "from UserPerson t where t.userid='"+userid+"' and t.a0000 not in("+a0000s+") and b0111 = '"+operateDept+"'" +
				" and exists (select 1 from A01 a where a.a0000 = t.a0000)";
		if(a0000s.equals("null")){
			hql = "from UserPerson t where t.userid='"+userid+"' and b0111 = '"+operateDept+"' and operateType = '"+operateType+"'";
		}
		List list = sess.createQuery(hql).list();
		for(int i=0;i<list.size();i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			UserPerson igi = (UserPerson)list.get(i);
			sess.delete(igi);
			ts.commit();
		}
	}
	
	//查询是否有下级节点  false没有 true有
	public static String hasChildren(String id){
		if(id!=null && !"0".equals(id)){
			return "false";
		}else{
			return "true";
		}
	}
}
