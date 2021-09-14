package com.insigma.siis.local.pagemodel.bzpj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Competenceuserdept;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.insigma.siis.local.util.SqlUtil;

import net.sf.json.JSONObject;


    
public class PJZBSchemePageModel extends PageModel{
	
	public PJZBSchemePageModel(){
	}
	
	  
	@Override   
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException, PrivilegeException, AppException{
		
		return 0;
	}
	/**
	  *授权 
	 *@param result 用户操作记录字符串
	 * @throws Exception 
	 */
	@PageEvent("saveTree")
	@Transaction
	public int saveTree(String result) throws Exception{
		String userID = SysManagerUtils.getUserId();
		
		String b01String = this.getPageElement("SysOrgTreeIds").getValue();
		String ets00 = this.getPageElement("ets00").getValue();
		String ets01 = this.getPageElement("ets01").getValue();
		 
		StringBuffer b0111_sb = new StringBuffer("");
       
        if(b01String!=null && !"".equals(b01String)&& !"{}".equals(b01String)){//tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
				if("true".equals(types[1])&&"true".equals(types[2])){
					b0111_sb.append(" or "+CommSQL.subString("b01.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					b0111_sb.append(" or "+CommSQL.subString2("b01.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					b0111_sb.append(" or b01.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				b0111_sb.append(" ) ");
			}
			
			if(StringUtils.isEmpty(ets00)){//新增
				ets00 = UUID.randomUUID().toString();
				HBUtil.executeUpdate("insert into EVALUATION_TARGET_scheme(ets00,ets01,ets02,ets04) values(?,?,seq_sort.nextval,?)",
						new Object[]{ets00,ets01,userID});
			}else{
				HBUtil.executeUpdate("update EVALUATION_TARGET_scheme set ets01=? where ets00=?",
						new Object[]{ets01,ets00});
			}
			String selsql = "select '"+ets00+"',b01id,b0269 from b01 where 1=1 "+ b0111_sb;
			HBUtil.executeUpdate("delete from EVALUATION_TARGET_org  where ets00=?",
					new Object[]{ets00});
			
			String insertSQL = "insert into EVALUATION_TARGET_org(ets00,b01id,b0269) ("+selsql+")";
			HBUtil.executeUpdate(insertSQL);
			HBUtil.getHBSession().flush();
        }else{
        	if(StringUtils.isEmpty(ets00)){//新增
				ets00 = UUID.randomUUID().toString();
				HBUtil.executeUpdate("insert into EVALUATION_TARGET_scheme(ets00,ets01,ets02,ets04) values(?,?,seq_sort.nextval,?)",
						new Object[]{ets00,ets01,userID});
			}else{
				HBUtil.executeUpdate("update EVALUATION_TARGET_scheme set ets01=? where ets00=?",
						new Object[]{ets01,ets00});
			}
        }
        this.getPageElement("ets00").setValue(ets00);
        this.getExecuteSG().addExecuteCode("reflashParent();");
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String getSQL(Entry<String, String> entry, int type, String userid) throws RadowException {
		String tablename=this.getPageElement("tablename").getValue();
		String sql = "";
		if(type==1&&entry.getValue().equals("true"))
			sql ="insert into "+tablename+" c ( c.userdeptid,c.userid,c.b0111,c.type) select sys_guid(),'"+userid+"',b.b0111,'1' from b01 b where  b0111 like '"+entry.getKey()+"%'";
		else if(type==1&&entry.getValue().equals("false"))
			sql ="delete "+tablename+" c where c.b0111 like '"+entry.getKey()+"%' and userid = '"+userid+"'";
		else if(type==2&&entry.getValue().equals("true"))
			sql="insert into "+tablename+" c ( c.userdeptid,c.userid,c.b0111,c.type) select sys_guid(),'"+userid+"',b.b0111,'1' from b01 b where b.b0111 ='"+entry.getKey()+"'";
		else if(type==2&&entry.getValue().equals("false"))
			sql ="delete "+tablename+" c where c.b0111 = '"+entry.getKey()+"' and userid = '"+userid+"'";
		return sql;
	}


	



	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sign = this.getParameter("sign");//浏览  编辑  机构树编辑
		
		String jsonStr = getJson("2",node,"write");
		
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}

	
	public String getJson(String type,String nodeother,String sign){
		//String sign = this.getParameter("sign");//浏览  编辑  机构树编辑
		String deptTable = "";
		/*
		 * if("look".equals(sign)){ deptTable = "competence_userdept"; }else{
		 */
			deptTable = "competence_userdept";
		/* } */
		
		String b01Table = "";
		/*if ("UnPeopleLook".equals(sign)) {
			b01Table = "competence_userdeptPeo_look";
			deptTable = "competence_userdept";
		}else {*/
			b01Table = "B01";
		/* } */
		int treeType=0;
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		String ets00 = this.getParameter("ets00");
		if(node.equals("-1")){//首次访问
				//获取用户权限范围内的最高级别单位的id
				List<HashMap> list =null;
				try {
					if(DBType.ORACLE==DBUtil.getDBType()){
						String sql="select min(b0111) b0111 from "+deptTable+" t where userid='"+cueUserid+"'";
						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}else if(DBType.MYSQL==DBUtil.getDBType()){
						String sql="select b0111 from "+deptTable+" t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}
				}catch(AppException e){
					e.printStackTrace();
				}
				if(list!=null&&list.size()>0){
					node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
				}
				if(node==null||node.length()<=7){
					node="-1";
				}else{
					node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
				}
		}
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		String sql1="";
		String sql="";
		//if(("system".equals(loginName)&&"UnPeopleLook".equals(sign)) || "UnPeopleLook".equals(sign)){//system用户不加权限
		if("system".equals(loginName)){//system用户不加权限
			sql1=" select count(t1.b0111) from "+b01Table+" t1 where t1.b0121=t.b0111 ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194,(select 'true' from EVALUATION_TARGET_org t1 where t1.b01id=t.b01id and t1.ets00='"+ets00+"') checked from "+b01Table+" t where t.b0121='"+node+"'  order by t.sortid asc ";			
		}else{
			sql1="select count(t1.b0111)  from "+b01Table+" t1 ,"+deptTable+" s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194,(select 'true' from EVALUATION_TARGET_org t1 where t1.b01id=t.b01id and t1.ets00='"+ets00+"') checked from "+b01Table+" t,"+deptTable+" s where t.b0111=s.b0111 and t.b0121='"+node+"' and s.userid='"+cueUserid+"' order by t.sortid asc ";
		}
//		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
//		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		
		
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			
			for (HashMap group : list) {
				Object o = group.get("b0101");
				if(o!=null){
					group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
				}
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
			icon="./main/images/tree/leaf.gif";
		}else if(map.get("b0194").equals("3")){
			icon="./main/images/tree/folder.gif";
		}
		sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',checked:"+((map.get("checked")==null||"".equals(map.get("checked")))?"false":map.get("checked").toString())+",leaf:"+hasChildren((String)map.get("count1"))+",tag:'"+""+"',icon:'"+icon+"'}");

		return sb_tree.toString();
	}
	private String hasChildren(String id){
			
			if("0".equals(id)){
				return "true";
			}
				return "false";
		
	}
}
