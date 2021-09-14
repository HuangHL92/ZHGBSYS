package com.insigma.siis.local.pagemodel.cbdHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.InfoGroupInfo;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.entity.UserInfoGroup;
import com.insigma.siis.local.business.entity.UserPerson;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_003.QXDTO;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.lowagie.text.Element;

public class SelectPersonPageModel extends PageModel {
	protected static final String LEFTTREE = "1";//机构信息维护树
	protected static final String OPENTREE = "2";//弹出树
	protected static final String THREETREE = "3";//邹磊用树
	protected static final String FOURTREE = "4";//名册导出用树
	protected static final String FIVETREE = "5";//人员信息维护树
	protected static final String SIXTREE = "6";//人员信息维护树
	private String checkedid = "";
	private static String dept = "";
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	public SelectPersonPageModel(){
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
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		if(node.equals("-1")){
			node="001";
		}
		String jsonStr = getJson("1",node);
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	public String getJson(String type,String nodeother){
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		int nodelength = 0;
		if(node.equals("-1")){
			nodelength=3;
			node="001";
		}else{
			nodelength=node.length();
		}
		checkedid = node;
		int nodelength1 =nodelength+4;
		int nodelength2 = nodelength1+2;
		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjson(type, group, jsonStr);
					jsonStr.append("]");
				}else if (i == 0) {
					jsonStr.append("[");
					appendjson(type, group, jsonStr);
				}else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjson(type, group, jsonStr);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjson(type, group, jsonStr);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	
	private String appendjson(String type,HashMap map,StringBuffer sb_tree){
		String icon ="";
		if(map.get("b0194").equals("1")){
			icon="./main/images/icons/companyOrgImg2.png";
		}else if(map.get("b0194").equals("2")){
			icon="./main/images/icons/insideOrgImg1.png";
		}else if(map.get("b0194").equals("3")){
			icon="./main/images/icons/groupOrgImg1.png";
		}
		if(type.equals(LEFTTREE)){
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(OPENTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",tag:'"+""+"',icon:'"+icon+"'}");
		}else if(type.equals(FOURTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(FIVETREE)){
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"dblclick\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(SIXTREE)){
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(THREETREE)){
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
		}else{
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
			
		}
		return sb_tree.toString();
	}
	
	private String hasChildren(String id){
		/*String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql="select b.b0111 from B01 b,competence_userdept t where t.b0111=b.b0111 and t.USERID = '"+cueUserid+"' and b.B0111 like '"+id+"%' and b.b0111!='"+id+"' order by sortid";// -1其它现职人员
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();*/
		if("3".equals(id)){
			return "false";
		}
			return "true";
		
	}
	
	//根据机构树中选择的机构，查询机构下的人员信息
	@PageEvent("orgTreeGridJsonData")
	public int getOrgTreeGridJsonData() throws PrivilegeException, AppException, RadowException {
		//登录用户的用户编码
		//String userid =this.getParameter("userid");
		String userID = SysManagerUtils.getUserId();
		//选中的机构编号
		String b0111 = dept;
		HBSession sess = HBUtil.getHBSession();
		//定义返回信息的json串
		StringBuffer jsonStr = new StringBuffer();
		//sql语句
		String sql = "";
		//如果选中机构树中的机构时，查询相关的人员信息
		if(b0111!=null){
            sql = "select *                                                       "+
            		"  from (select a01.a0000,                                      "+
            		"               a0101,                                          "+
            		"               cbdresult,                                      "+
            		"               a0104,                                          "+
            		"               GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"') age,        "+
            		"               a0117,                                          "+
            		"               a0141,                                          "+
            		"               a0192a,                                         "+
            		"               a0148,                                          "+
            		"               A0160,                                          "+
            		"               A0192D,                                         "+
            		"               A0120,                                          "+
            		"               QRZXL,                                          "+
            		"               ZZXL,                                           "+
            		"               a0107,                                          "+
            		"               a0140,                                          "+
            		"               a0134,                                          "+
            		"               a0165,                                          "+
            		"               a0121,                                          "+
            		"               a0184,                                          "+
            		"               orgid,                                          "+
            		"               status                                          "+
            		"          from A01 a01, a02 a02, competence_userdept cu        "+
            		"         where a01.a0000 = a02.a0000                           "+
            		"           AND a02.A0201B = cu.b0111                           "+
            		"           and (a01.cbdresult is null or a01.cbdresult = '0')  "+
            		"           AND cu.userid = '"+userID+"'  "+
            		"           and a02.a0201b = '"+b0111+"'                      "+
            		"           and a02.a0255 = '1'                                 "+
            		"           and a01.status = '1'                                "+
            		"           and a02.A0201B=cu.b0111                             "+
            		"           and cu.type='1') a01                                "+
            		" where 1 = 1                                                   "+
            		"   and not exists                                              "+
            		" (select 1                                                     "+
            		"          from COMPETENCE_USERPERSON cu                        "+
            		"         where cu.a0000 = a01.a0000                            "+
            		"           and cu.userid = '"+userID+"') "+
            		" order by a01.a0148,                                           "+
            		"          (select max(a0225)                                   "+
            		"             from b01, a02                                     "+
            		"            where a01.a0000 = a02.a0000                        "+
            		"              and a02.a0201b = b01.b0111                       "+
            		"              and a02.a0255 = '1')                             ";		
			
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
				String check="indeterminate";
//				if(person_hashmap.get("b0111") != null){
//					check="checked";
//				}else{
//					check="indeterminate";
//				}
				
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
		dept=null;
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	@PageEvent("querybyid")
	public int queryById(String id) throws RadowException{
		dept = id;
		this.getPageElement("checkedgroupid").setValue(id);
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty()||map==null) {
			if ("2".equals(map.get("type"))||"0".equals(map.get("type"))){
				this.setMainMessage("您没有该机构的权限");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.getExecuteSG().addExecuteCode("window.reloadGridTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String getName(HBSession sess,String a0000){
		String	name = (String) sess.createSQLQuery("select a0101 from a01 a where a.a0000='"+a0000+"'").uniqueResult();
		return name;
	}
	@PageEvent("dogrant")
	public int doGant(String value) throws RadowException, AppException{
		
		if("".equals(value) || value == null){
			this.setMainMessage("请选择人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//定义变量用来存放人员的编号和姓名
		StringBuffer sql = new StringBuffer();
		StringBuffer names = new StringBuffer();
		StringBuffer ids = new StringBuffer();
		
		String[] values = value.split(",");
		if(values.length > 0){
			sql.append("'");
		}
		HBSession sess = HBUtil.getHBSession();
		
		for(int i=0;i<values.length;i++){
			sql.append(values[i]).append("','");
			if(i == values.length-1){
				sql.append("'");
			}else{
				sql.append("','");
			}
			
		}
		String hql = "From A01 S where S.a0000 in ("+sql.toString()+")";
		List<A01> a01_list =(List<A01>) sess.createQuery(hql).list();
		for(int j=0;j<a01_list.size();j++){
			
			names.append(a01_list.get(j).getA0101()).append(",");
			ids.append(a01_list.get(j).getA0000()).append(",");
		}
		//将最后一位的逗号截取
		names.deleteCharAt(names.length()-1);
		ids.deleteCharAt(ids.length()-1);
		
		this.closeCueWindowByYes("win_pup");
		//将选中的人员的值设置 到父页面中
		this.createPageElement("cbd_personname", ElementType.TEXT, true).setValue(names.toString());
		this.createPageElement("cbd_personid", ElementType.TEXT, true).setValue(ids.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//查询是否有下级节点  false没有 true有
	public static String hasChildren(String id,String user){
		String cueUsername = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
		String sql= "";
		if("system".equals(cueUsername)){
			sql="select b.b0111 from B01 b where b.B0111 like '"+id+".%' ";
		}else{
			
			sql="select b.b0111 from B01 b,COMPETENCE_USERDEPT cu where b.b0111 = cu.b0111 and cu.userid = '"+user+"' and b.B0111 like '"+id+".%' ";
		}
		//String sql="select b.b0111 from B01 b,COMPETENCE_USERDEPT cu where b.b0111 = cu.b0111 and cu.userid = '"+user+"' and b.B0111 like '"+id+".%' ";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
}
