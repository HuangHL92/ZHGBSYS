package com.insigma.siis.local.pagemodel.sysorg.org;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;

import net.sf.json.JSONArray;

public class OrgConnectPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// this.controlButton();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException {
		String sql2 = " ";
		HBSession sess = HBUtil.getHBSession();
		String uderID = SysManagerUtils.getUserId();
		String selectByInputYnIdHidden = this.getPageElement("selectByInputYnIdHidden").getValue();
		// 获得信用代码或者机构名称
		String name = this.getPageElement("queryName").getValue();
		// 获得查询类型，信用代码或者机构名称
		String tpye = this.getPageElement("tpye").getValue();
		String col1 = "unify_code";
		boolean multiUnit = false;// 是否包含下级单位
		String check = this.getPageElement("existsCheckbox").getValue();
		// 邹志林 2018-08-22
		String selectUnitId = this.getPageElement("selectUnitId").getValue();
		JSONObject objUnitId = new JSONObject(selectUnitId);
		StringBuffer sqlBuffer = new StringBuffer();
		multiUnit = objUnitId.keySet().size() > 1;
		for (Object oKey : objUnitId.keySet()) {
			String unitId = (String) oKey;
			String array[] = objUnitId.get(unitId).toString().split(":");
			if (check.equals("1")) {
				sqlBuffer.append(" OR unitid like '" + unitId + "%' ");
				multiUnit = true;
			} else {
				sqlBuffer.append(" OR unitid = '" + unitId + "' ");
			}
		}
		sqlBuffer.delete(0, 3);
		@SuppressWarnings("unused")
		String appointment = this.getPageElement("appointment").getValue();
		sql2 = "select distinct unitid,unify_code,jgsy_name,dwlb from gzdba.jgsy_common j";

		// 当查询条件为机构名称时
		if (tpye != null && tpye.equals("2")) {
			col1 = "jgsy_name";
		}

		if (name != null && !name.trim().equals("")) {
			name = StringEscapeUtils.escapeSql(name.trim());
			name = name.replaceAll("\\s+", " ");
			name = name.replaceAll(" ", ",");
			name = name.replace(".", ",");
			name = name.replace("&", ",");
			name = name.replace("#", ",");
			name = name.replaceAll("[\\t\\n\\r]", ",");
			String[] names = name.trim().split(",|，");
			// 包含逗号则进行精确查询
			if (names.length > 1) {
				StringBuffer sb = new StringBuffer("j." + col1 + " in(");
				for (int i = 0; i < names.length; i++) {
					name = names[i].replaceAll("\\s", "");
					sb.append("'" + name + "',");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(")");
				sql2 = sql2 + " where (" + sb.toString() + ")";
			}else{ 
				//不包含逗号则进行模糊查询
				name = name.replaceAll("\\s", "");
				sql2 = sql2 +" where "+col1+" like '%"+name+"%'";
			}
		} else {
			if (sqlBuffer.length() > 0) {
				sql2 += " where " + sqlBuffer.toString();
			}
		}
		System.out.println(sql2);
		this.getPageElement("sql").setValue(sql2);
		this.setNextEventName("gridcq.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start, int limit) throws RadowException {
		try {
			String sql = this.getPageElement("sql").getValue();
			if (limit < 1000) {
				limit = 1000;
			}
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");

			return EventRtnType.SPE_SUCCESS;
		}
	}

	
	// 关闭
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException {
	
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 确认保存
	@PageEvent("saveSelect")
	public int saveSelect(String a) throws RadowException, AppException {

		// 获得选择的人员ID
		List<String> addlist = new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));// 选择机构列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm.get("unitid") + "");
			addlist.add(hm.get("unify_code") + "");
		}

		if (addlist.size() < 1) {
			this.setMainMessage("请选择人员或选择会议类型！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String b0111 = this.getPageElement("B0111").getValue();
		String sql = "update b01 set unitid='"+addlist.get(0)+"',unify_code='"+addlist.get(1)+ "' where b0111='"+b0111+"'";
		System.out.println("sql"+sql);
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = sess.beginTransaction();
		Connection conn = sess.connection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		trans.commit();
		this.setMainMessage("绑定成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private String change(String name) throws RadowException {
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			name = name + "2";
		}
		return name;
	}

	// 清除输出列表
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException {
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String getJson(){
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node=this.getParameter("node");;
//		
//		if(node.equals("-1")&&!("5".equals(type))){//首次访问
//				//获取最高级别单位的id
//				List<HashMap> list =null;
//				try {
//						String sql="select UNITID from ( select UNITID from GZDBA.JGSY_COMMON t  order by UNITID asc) where ROWNUM =1;";
//						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
//				}catch(AppException e){
//					e.printStackTrace();
//				}
//				if(list!=null&&list.size()>0){
//					node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
//				}
//				
//		}
		if(node.equals("-1"))//干部信息主界面，如果第一次访问，返回机构树最上级的机构
		{
			node="-1";//默认的最上级机构
		}
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		String sql1="";
		sql1=node.equals("-1")?" 1 ":"select count(t1.unitid)  from GZDBA.JGSY_COMMON  t1 where t1.parent= t.unitid and t1.unitid != t.unitid ";		
		String sql="";
		sql="select ("+sql1+") count1, t.unitid,t.parent,t.jgsy_name from GZDBA.JGSY_COMMON t where   t.unitid in ( select distinct unitid from GZDBA.JGSY_COMMON s where s.parent = '"+node+"'  and s.unitid != '"+node+"' )";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		String unsort="";
		if(this.request!=null){
			unsort = this.request.getParameter("unsort");
		}
		
		
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			
			if("1".equals(unsort)) {
				for (HashMap group : list) {
					Object o = group.get("jgsy_name");
					if(o!=null){
						group.put("jgsy_name", o.toString().replaceAll("\r|\n|\r\n", ""));
					}
					if(i==0 && last==1) {
						jsonStr.append("[");
						appendjson( group, jsonStr);
						jsonStr.append("]");
					}else if (i == 0) {
						jsonStr.append("[");
						appendjson( group, jsonStr);
					}else if (i == (last - 1)) {
						jsonStr.append(",");
						appendjson( group, jsonStr);
						jsonStr.append("]");
					} else {
						jsonStr.append(",");
						appendjson( group, jsonStr);
					}
					i++;
				}
			}else {
			
				for (HashMap group : list) {
					Object o = group.get("jgsy_name");
					if(o!=null){
						group.put("jgsy_name", o.toString().replaceAll("\r|\n|\r\n", ""));
					}
					if(i==0 && last==1) {
						jsonStr.append("[");
						appendjson( group, jsonStr);
						jsonStr.append("]");
					}else if (i == 0) {
						jsonStr.append("[");
						appendjson( group, jsonStr);
					}else if (i == (last - 1)) {
						jsonStr.append(",");
						appendjson( group, jsonStr);
						jsonStr.append("]");
					} else {
						jsonStr.append(",");
						appendjson( group, jsonStr);
					}
					i++;
				}
			}
		} else {
			jsonStr.append("{}");
		}
		return jsonStr.toString();
		
	}
	private String appendjson(HashMap map,StringBuffer sb_tree){
		String icon ="";
//		if(map.get("b0194").equals("1")){
//			icon="./main/images/icons/companyOrgImg2.png";
//		}else if(map.get("b0194").equals("2")){
//			icon="./main/images/tree/leaf.gif";
//		}else if(map.get("b0194").equals("3")){
//			icon="./main/images/tree/folder.gif";
//		}
		icon="./main/images/tree/folder.gif";
		
			sb_tree.append(" {text: '"+map.get("jgsy_name")+"',id:'"+map.get("unitid")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("unitid")+"')\"}");
		
		return sb_tree.toString();
	}
	private String hasChildren(String id){
		/*String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql="select b.b0111 from B01 b,competence_userdept t where t.b0111=b.b0111 and t.USERID = '"+cueUserid+"' and b.B0111 like '"+id+"%' and b.b0111!='"+id+"' order by sortid";// -1其它现职人员
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();*/
//		if("3".equals(id)){
//			return "false";
//		}
//			return "true";
			
			if("0".equals(id)){
				return "true";
			}
				return "false";
		
	}
	/**
	 * 编办构信息树
	 */
	@PageEvent("orgTreeOrgConnect")
	public int orgTreeOrgConnect() throws PrivilegeException {
		String jsonStr = getJson();
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
}
