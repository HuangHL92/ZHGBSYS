package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;
    
 import java.util.ArrayList;   
import java.util.Comparator;   
import java.util.HashMap;   
import java.util.Iterator;   
import java.util.List;   
import java.util.Map;   
import java.util.Set;   
import java.util.Collections;   
import java.util.Vector;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_003.QXDTO;
import com.insigma.siis.local.epsoft.util.NodeUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.CopyIgnoreProperty;
    
 /**  
  * 多叉树类  
 */   
 public class MultipleTree {   
       
 }   
    
    
 /**  
 * 节点类  
 */   
 class Node {   
  /**  
   * 节点编号  
   */   
  public String id;   
  /**  
   * 节点内容  
   */   
  public String text;   
  /**  
   * 父节点编号  
   */   
  public String parentId;   
  /**  
   * 孩子节点列表  
   */
  public String userId; 
  public Long sortId; 
  private Children children = new Children();   

	public String qxtype;//权限
     
  // 先序遍历，拼接JSON字符串   
  public String toString() {
	  //UserDept ud = new UserDept();
	  //HBSession sess = HBUtil.getHBSession();
	  //String hql = "from UserDept t where t.b0111='"+id+"' and t.userid='"+userId+"'";
	  //List list = sess.createQuery(hql).list();
	  String childsll =parentId+"0";
	  String childswh =parentId+"1";
	  /*List list1 = getChild(id);
	  childsll = (String)list1.get(0);
	  childswh = (String)list1.get(1);*/
	  String read = "";
	  String write = "";
	  //if(list.size()==1){
		 // ud = (UserDept)list.get(0);
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
	  //}
	
    String result = "{"   
    + "task : '" +  text + "'"   
    + ",duration:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+read+" id=\""+id+"0"+"\"  name=\""+childsll+"\"/>'";   
      
   if (children != null && children.getSize() != 0) {
	   if(text.equals("机构树")){
	    	 result=children.toString();
	    	 return result;
	     }
    result += ", children : " + children.toString();   
   } else {   
    result += ", leaf : true";   
   }   
   	result +=",user:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+write+" id=\""+id+"1"+"\"  name=\""+childswh+"\"/>'";
     result +=",uiProvider:'col'";
   return result + "}";   
  } 
  /*public List getChild(String id){
	  List<String> list = new ArrayList<String>();
	  String childsll = "";
	  String childswh = "";
	  HBSession sess = HBUtil.getHBSession();
	  String hql1 = "from B01 t where t.b0121='"+id+"'";
	  List list1 = sess.createQuery(hql1).list();
	  if(list1.size()!=0){
		  for(int i=0;i<list1.size();i++){
			  B01 b = (B01)list1.get(i);
			  childsll+=b.getB0111()+"0,";
			  childswh+=b.getB0111()+"1,";
		  }
	  }
	  list.add(childsll);
	  list.add(childswh);
	  return list;
  }*/
     
  // 兄弟节点横向排序   
  public void sortChildren() {   
   if (children != null && children.getSize() != 0) {   
    children.sortChildren();   
   }   
  }   
     
  // 添加孩子节点   
  public void addChild(Node node) {   
   this.children.addChild(node);   
  }   
 }   
    
 /**  
 * 孩子列表类  
 */   
 class Children {   
  private List list = new ArrayList();   
     
  public int getSize() {   
   return list.size();   
  }   
     
  public void addChild(Node node) {   
   list.add(node);   
  }   
     
  // 拼接孩子节点的JSON字符串   
  public String toString() { 
	  /*List list1 = new ArrayList();
	  int index=0;
	  for(int i=0;i<list.size();i++){
		  int a=i+1;
		  Node node = (Node)list.get(i);
		  if(node.sortId==null){
			  index=1;
		  }else{
			  if(a==node.sortId){
				  list1.add(node);
			  }
		  }
		  
	  }*/
	  /*if(index!=1){
		  list=list1; 
		  index=0;
	  }*/
	  
   String result = "[";     
   for (Iterator it = list.iterator(); it.hasNext();) {   
    result += ((Node) it.next()).toString();   
    result += ",";   
   }   
   result = result.substring(0, result.length() - 1);   
   result += "]";   
   return result;   
  }   
     
  // 孩子节点排序   
  public void sortChildren() {   
   // 对本层节点进行排序   
   // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器   
   Collections.sort(list, new NodeIDComparator());   
   // 对每个节点的下一层节点进行排序   
   for (Iterator it = list.iterator(); it.hasNext();) {   
    ((Node) it.next()).sortChildren();   
   }   
  }   
 }   
    
 /**  
  * 节点比较器  
  */   
 class NodeIDComparator implements Comparator {   
  // 按照节点编号比较   
  public int compare(Object o1, Object o2) {   
   /*String j1 = ((Node)o1).id;   
      String j2 = ((Node)o2).id;
      String j3 = ((Node)o2).parentId; */
	  Long j1=((Node)o1).sortId;
	  Long j2=((Node)o2).sortId;
      //return (j1.equals(j3) ? - 1: (j1.equals(j2) ?  0: 1)); 
	  return (int) (j1-j2);
  }    
 }   
    
 /**  
  * 构造虚拟的层次数据  
  */   
 class VirtualDataGenerator {   
  // 构造无序的结果集列表，实际应用中，该数据应该从数据库中查询获得；   
  public static List getVirtualResult(String userid,String xzuserid,String node) throws AppException {  
	  StopWatch w1 = new StopWatch();
	  w1.start();
   List<HashMap> list_map = null;
   if("admin".equals(userid)){
//	   String str="select (select count(b.b0111) from B01 b where b.B0111 like CONCAT(a.b0111,'.%') ) as count1,a.b0111 b0111,a.b0101 b0101,a.b0121 b0121,a.sortid,case when b.type is null then '2' else b.type end type from b01 a left join (select c.type,c.b0111 from competence_userdept c where c.userid='"+xzuserid+"') b on a.b0111=b.b0111 where a.b0111<>'-1'";
	   String str = "select (select count(b.b0111) from b01 b where b.b0121 = a.b0111 ) as count1,'1' type2," +
	   		"a.b0111 b0111,a.b0101 b0101,a.b0121 b0121,a.sortid,b0194," +
	   		"(select c.type from competence_userdept c where c.b0111 = a.b0111 and c.userid = '"+xzuserid+"') type" +
	   		" from b01 a where a.b0121 = '"+node+"' order by a.sortid";
	   
	   list_map = CommonQueryBS.getQueryInfoByManulSQL(str.toString());
   }else{
		boolean nodeFlag = false;
		String node1 = "";
		if(!"system".equals(userid) && "-1".equals(node)){
			node1 = "001";
		}else if(!"system".equals(userid) && !"-1".equals(node)){
			node1 = node;
		}
		
	   HBSession sess = HBUtil.getHBSession();
	   String hql = "From SmtUser t where t.loginname='"+userid+"'";
	   List list = sess.createQuery(hql).list();
	   SmtUser su = (SmtUser)list.get(0);
	   
	   
	   String cueUserID = PrivilegeManager.getInstance().getCueLoginUser().getId();
	   
	   //计算机构是否存在下级机构
	   String sql6 = "";
	   if("system".equals(userid)){
		   sql6=" (select count(b.b0111) from b01 b where b.b0121 = a.b0111 ) as count1,'1' type2, ";
		}else{
			
//			sql6=" (select count(cu.b0111) from COMPETENCE_USERDEPT cu where " +
//					" cu.userid = '"+cueUserID+"' and cu.B0111 like CONCAT(a.b0111, '.%') ) as count1," +
//					"(select c.type from competence_userdept c where c.b0111 = a.b0111 and c.userid = '"+cueUserID+"') type1, ";//查询登陆用户对机构是否有授权权限
			sql6 = "cc.count1 ,(select c.type from competence_userdept c where c.b0111 = a.b0111 and c.userid = '"+cueUserID+"') type2,";
		}
	   //查询语句主体
	   StringBuffer  str=new StringBuffer("select "+sql6+"a.b0111 b0111,a.b0101 b0101,a.b0121 b0121,a.sortid,a.b0194 b0194," +
	   		"(select c.type from competence_userdept c where c.b0111 = a.b0111 and c.userid = '"+xzuserid+"') type ");
	   //根据登陆用户不同，组装不同的where条件
	   String sql_System = "";
	   if("system".equals(userid)){
		   sql_System = " from b01 a where a.b0121 = '"+node+"'";
			   
	   }else{
//		   sql_System = " from b01 a where a.b0121 = '"+node+"' and a.b0111 in(select substr(c.b0111,1,length('"+node1+"')+4) " +
//		   		"from competence_userdept c where c.userid = '"+cueUserID+"' group by substr(c.b0111,1,length('"+node1+"')+4))";
		   sql_System = " from b01 a join (select substr(b0111, 1, length('"+node1+"')+4) b01111," +
		   		"max(length(trim(substr(b0111, length('"+node1+"')+6, 3)))) count1" +
		   		" from competence_userdept t where t.b0111 like '"+node1+".%'" +
		   		" and t.USERID = '"+cueUserID+"' " +
		   		" group by substr(b0111, 1, length('"+node1+"')+4)) cc on a.b0111 = cc.b01111";
	   }
	   str.append(sql_System);
	   str.append(" order by a.sortid");
	   
	   
	   list_map = CommonQueryBS.getQueryInfoByManulSQL(str.toString());
	   
   }
   
	w1.stop();
	CommonQueryBS.systemOut("机构授权树执行时间："+w1.elapsedTime());
   return list_map;   
  }
  
  private String hasChildren(String id){
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql="select b.b0111 from B01 b where b.B0111 like '"+id+"%' order by sortid";// -1其它现职人员
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
 }   

