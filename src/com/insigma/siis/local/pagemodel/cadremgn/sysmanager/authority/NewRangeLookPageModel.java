package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
    
public class NewRangeLookPageModel extends PageModel{
	
	public static int syDayCount = 0;
	public static int birthDaycount = 0;
	public static String params;
	public static int queryType = 0;//0未检测  1检测结果中查询  2开始检测  3点击树查询
	public static String status = "";
	public static String querysql = "";
	public static String checktime = "";
	
	public NewRangeLookPageModel(){
	}
	
	  
	@Override   
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("findB0111");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException, PrivilegeException{
		/*syDayCount = 0;
		birthDaycount = 0;
		queryType = 0;//0未检测  1检测结果中查询  2开始检测  3点击树查询
		status = "";
		querysql = "";
		checktime = "";*/
		//this.getPageElement("subid").setValue(this.getPageElement("subWinIdBussessId").getValue());
		String param = this.getPageElement("subWinIdBussessId").getValue();
		String userid = "";
		if("&1".equals(param.substring(param.length()-2))){
			userid = param.substring(0, param.length()-2);
			//this.getPageElement("sign").setValue("1");//回显标记
			this.getPageElement("isFirst").setValue("1");
		}else{
			userid = param;
		}
		
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select t.username from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		if(obj!=null){
			this.getExecuteSG().addExecuteCode("document.getElementById('text11').innerText = '"+"当前用户 : "+obj+"';");
		}
		
		
		this.getPageElement("userid").setValue(userid);
		if("&1".equals(param.substring(param.length()-2))){
			//this.setNextEventName("persongrid2.dogridquery");
		}else{
			//开放保存范围按钮
			this.getExecuteSG().addExecuteCode("Ext.getCmp('btn1').setDisabled(false);");
		}
		return 0;
	}
	
	@PageEvent("findB0111")
	@Transaction
	public int findB0111() throws RadowException, AppException {
		//获取当前用户下的已授权的机构编码
		List<HashMap> list =null;
		String param = this.getPageElement("subWinIdBussessId").getValue();
		String userid = "";
		userid = param.substring(0, param.length()-2);
		String sql="select b0111 from competence_userdept_look t where userid='"+userid+"' order by length(b0111) asc";
		HBSession sess = HBUtil.getHBSession();
		List s= sess.createSQLQuery(sql).list();
		String s1 = s.toString();
		String s2 =" "+s1.substring(s1.indexOf("[")+1, s1.indexOf("]"));
		s2 = s2.replaceAll("\\s","");
		System.out.println("s2-----------------------"+s2);
		this.getPageElement("b0111s").setValue(s2);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("saveTree")
	@Transaction
	public int saveTree(String result) throws RadowException, PrivilegeException{
		System.out.println("result: "+result);
		
		Set<String> b0111Like = new HashSet<String>();//选中的重要节点
		if(result!=null ) {
			String[] results = result.split(",");
			for(String ss : results){
				b0111Like.add(ss);
			}
		}
		

		String b0111LikeJson = StringUtils.join(b0111Like.toArray(), ",");
		this.getPageElement("b0111Like").setValue(b0111LikeJson);
		System.out.println("b0111LikeJson"+b0111LikeJson);
		

		String userid = this.getPageElement("userid").getValue();
		System.out.println("userid："+userid);
		saveGrant(userid);
		return EventRtnType.NORMAL_SUCCESS;
	}


	public int saveGrant(String userid) throws RadowException, PrivilegeException{
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//add zepeng 20190926 sign代表包含下级授权还是，单独连续授权
		
		String b0111Like = this.getPageElement("b0111Like").getValue();
		String sql = "select b01.b0111 from b01 where ( 1 = 2 ";
		
		//去除这些机构ID下的人员
		if(b0111Like!=null&&!"".equals(b0111Like)){
			String[] notB0111s = b0111Like.split(",");
			for(String v : notB0111s){
				// sql = sql + " or b01.b0111 like '"+ v + sign + "' ";
				sql = sql + " or  '"+ v.trim() +"'= b01.b0111";
			}
		}
		System.out.println("b0111Like*******sql="+sql);
		sql = sql + ") ";
		System.out.println("sql:  "+sql);
		if(!"40288103556cc97701556d629135000f".equals(cueUserid)){//上级不是system
			sql = sql + " and b01.b0111 in (select t.b0111 from competence_userdept_look t where t.userid = '"+cueUserid+"' and t.type = '1') ";
			System.out.println("上级不是system*******sql="+sql);
		}
		System.out.println("NewRangePageModel.saveGrant:sql="+sql+"***b0111Like="+b0111Like+"***cueUserid="+cueUserid);
		HBSession sess = HBUtil.getHBSession();
		//当前用户机构授权
		Object obj = sess.createSQLQuery("select s.isleader from smt_user s where s.userid = '"+userid+"'").uniqueResult();
		System.out.println("obj"+obj);
		if(obj!=null&&"1".equals(obj)){
			minusQX(sess,userid,sql,"competence_userdept_look","B0111", null);
		}
		//先删除
		sess.createSQLQuery(" delete from competence_userdept_look where userid = '"+userid+"'").executeUpdate();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement("insert into competence_userdept_look values(?,?,?,?)");
			//查询出所有导入时，待授权的机构
			rs = stmt.executeQuery(sql);
			int i = 0;
			if (rs != null){
				while (rs.next()) {
					pstmt.setString(1, UUID.randomUUID().toString().replace("-", ""));
					pstmt.setString(2, userid);
					pstmt.setString(3, rs.getString(1));
					pstmt.setString(4, "1");
					pstmt.addBatch();
					i++;
					if(i%5000 == 0){
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
					System.out.println("**i="+i+"**1：="+UUID.randomUUID().toString().replace("-", "")+"****2：userid="+userid+"***3：rs="+rs.getString(1));
				}
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("机构授权失败");
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
		this.setMainMessage("机构范围保存成功");

		return EventRtnType.NORMAL_SUCCESS;

	}
	
	public void minusQX(HBSession sess,String userid,String sql, String table, String tableCode, List<Object> oldList) {
		//如果当前被操作的用户是管理员，并且是对管理员重新授权，则先取出competence_userdept_look中的所有机构ID，减去新授权的机构ID
		//如果有差值，则将管理员的下级、下下级用户的机构权限也减去
		String minusSQL = "select "+tableCode+" from "+table+" where userid = '"+userid+"'"
						+ " minus "
						+ sql;
		
		List<Object> list = new ArrayList<Object>();
		if("权限".equals(sql)){
			list.addAll(oldList);
		}else{
			list = sess.createSQLQuery(minusSQL).list();
		}
		if(list!=null&&list.size()>0){
			Set<Object> objs = new HashSet<Object>();
			//取出userid所在的部门
			Object obj = sess.createSQLQuery("select s.dept from smt_user s where s.userid = '"+userid+"'").uniqueResult();
			if(obj!=null&&!"".equals(obj)){
				minusDept(sess,obj,objs);
			}
			
			if(objs!=null&&objs.size()>0){
				String b0111part = "(c."+tableCode+" in (";
				for(int i=0;i<list.size();i++){
					Object o = list.get(i);
					if(i%900==0){
						b0111part = b0111part + "'" + o + "') or c."+tableCode+" in (";
					}else{
						b0111part = b0111part + "'" + o + "',";
					}
				}
				if(",".equals(b0111part.substring(b0111part.length()-1))){
					b0111part = b0111part.substring(0, b0111part.length()-1);
				}else if("(".equals(b0111part.substring(b0111part.length()-1))){
					int num = b0111part.lastIndexOf("or");
					b0111part = b0111part.substring(0,num-2);
				}
				b0111part = b0111part + "))";
				
				String userpart = "";
				for(Object user : objs){
					userpart  = userpart + "'" + user + "',";
				}
				userpart = userpart.substring(0, userpart.length()-1);
				//System.out.println("delete from "+table+" c where c.userid in ("+userpart+") and "+b0111part+"");
				sess.createSQLQuery("delete from "+table+" c where c.userid in ("+userpart+") and "+b0111part+"").executeUpdate();
			}
		}
	}


	public void minusDept(HBSession sess, Object groupid, Set<Object> objs) {
		// TODO Auto-generated method stub
		//先取出部门下的普通用户
		List<Object> list = sess.createSQLQuery("select s.userid from  smt_user s where s.dept = '"+groupid+"'").list();
		if(list!=null&&list.size()>0){
			for(Object userid : list){
				objs.add(userid);
			}
		}
		
		//再取出下级部门下的所有部门
		List<Object> groups = sess.createSQLQuery("select s.id from smt_usergroup s where s.sid = '"+groupid+"'").list();
		if(groups!=null&&groups.size()>0){
			for(Object group : groups){
				minusDept(sess,group,objs);
			}
		}
	}


	@PageEvent("deletePerson")
	@Transaction
	public int deletePerson() throws RadowException{
		PageElement pe = this.getPageElement("persongrid2");
		List<HashMap<String, Object>> list = pe.getValueList();
		
		boolean isnoone = true;
		//获取session中的
		List<String> list3 = (List<String>)this.request.getSession().getAttribute("deletePerson");
		if(list3==null){
			list3 = new ArrayList<String>();
		}
		
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get("personcheck");
			if (check1 == null || "".equals(check1) || check1.equals(false)) {
				continue;
			}
			if (check1.equals(true)) {
				isnoone = false;
				Object v = map.get("a0000");
				if(v!=null&&!"".equals(v)){
					list3.add(""+v);
				}
			}
		}
		if(isnoone){
			this.setMainMessage("请在列表中勾选不需要授权的人员，再右键删除");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.request.getSession().setAttribute("deletePerson",list3);
		this.setNextEventName("persongrid2.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("goToByName")
	public int goToByName(String value) throws RadowException{
		List<HashMap<String, Object>> listSelect = this.getPageElement("persongrid2").getValueList();// 选择人员列表
		String[] v = value.split("\\|");
		int n = 0;
		String name = "";
		//默认从选中的当前行往下找
		if(v!=null&&v.length==1){
			name = value;
		}else if(v!=null&&v.length==2){
			String rowid = v[0];
			name = v[1];
			n = Integer.parseInt(rowid) + 1;
		}
		if (listSelect != null && listSelect.size() > 0) {
			for(int i=n;i<listSelect.size();i++){
				HashMap<String, Object> sel = listSelect.get(i);
				if ((sel.get("a0101")!=null&&sel.get("a0101").toString().contains(name))||((sel.get("a0184")!=null&&sel.get("a0184").toString().contains(name)))) {
					n = i;
					break;
				}
				//说明已经查到了最后一个,则在对listSelect.size()-n进行查询
				if(i==listSelect.size()-1){
					for(int j=0;j<n;j++){
						HashMap<String, Object> hl = listSelect.get(j);
						if ((hl.get("a0101")!=null&&hl.get("a0101").toString().contains(name))||((hl.get("a0184")!=null&&hl.get("a0184").toString().contains(name)))) {
							n = j;
							break;
						}
						//再次查到最后一个，说明都没有查到，就进行提醒
						if(j==n-1){
							this.getExecuteSG().addExecuteCode("alert('未查询到输入内容，无法进行定位');");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}	
		//n为最新的行号
		this.getExecuteSG().addExecuteCode("Ext.getCmp('persongrid2').getSelectionModel().selectRow("+n+");"
				+ "Ext.getCmp('persongrid2').getView().focusRow("+n+");");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
