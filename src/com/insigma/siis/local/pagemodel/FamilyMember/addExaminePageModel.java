package com.insigma.siis.local.pagemodel.FamilyMember;

import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

public class addExaminePageModel extends PageModel {

private static final String cols = "a01.a0000,a01.a0101,a01.a0192,a01.a0107,"+ 
" decode((select count(1) from A36NEW b , a36z1new c where( b.a0000 = a01.a0000 and b.a3645 = '0' ) or (c.a0000 = a01.a0000 and c.a3645 = '0')),0,1,0)  shzt";

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	
	@PageEvent("initX")
	public int initX() throws RadowException {
		String userid=SysUtil.getCacheCurrentUser().getId();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		System.out.println(user.getName()+user.getLoginname());
		String sql="select b0111 from ( select b0111 from competence_userdept where userid = '"+userid+"' order by b0111) where rownum=1";
		List<String> list=HBUtil.getHBSession().createSQLQuery(sql).list();
		String b0111=list.get(0);
		this.getPageElement("dept").setValue(b0111);
		this.getPageElement("dept_combo").setValue("");
		this.getPageElement("usertype").setValue(user.getLoginname());
		this.getPageElement("userid").setValue(userid);
		this.setNextEventName("queryByName");
		return EventRtnType.NORMAL_SUCCESS;
		
		
	}
	
	@PageEvent("MGrid.dogridquery")//家庭成员列表
	public int mgridquery(int start, int limit) throws RadowException, SQLException {
		String a0000 =this.getPageElement("a0000").getValue();
		
		StringBuffer sql=new StringBuffer();
		
		
	
		sql.append(" select * from ");
		sql.append(" (select a3600 ,a3604a,a3601,a3607,a3627,a3645,");
		sql.append("a3611,a0184gz,"+sql2("ZB01","a0111gz")+sql2("ZB01","a0115gz"));
		sql.append(sql2("GB2659","a0111gzb")+sql2("GB3304","a3621"));
		sql.append(sql2("ZB06","a3631")+sql2("ZB56","a3641"));
		sql.append(" updated,sortid from a36new where a0000 ='"+a0000+"'");
		sql.append(" union ");
		sql.append("select a3600 ,a3604a,a3601,a3607,a3627,a3645,");
		sql.append("a3611,a0184gz,"+sql2("ZB01","a0111gz")+sql2("ZB01","a0115gz"));
		sql.append(sql2("GB2659","a0111gzb")+sql2("GB3304","a3621"));
		sql.append(sql2("ZB06","a3631")+sql2("ZB56","a3641"));
		sql.append(" updated , sortid from a36z1new where a0000 ='"+a0000+"' ) ");
		sql.append(" order by sortid asc ");
		
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("MGrid2.dogridquery")//家庭成员列表
	public int mgridquery1(int start, int limit) throws RadowException, SQLException {
		String a0000 =this.getPageElement("a0000").getValue();
		
		StringBuffer sql=new StringBuffer();
	
		
		
		
		sql.append(" select * from ");
		sql.append(" (select a3600 ,a3604a,a3601,a3607,a3627,");
		sql.append("a3611,a0184gz,"+sql2("ZB01","a0111gz")+sql2("ZB01","a0115gz"));
		sql.append(sql2("GB2659","a0111gzb")+sql2("GB3304","a3621"));
		sql.append(sql2("ZB06","a3631")+sql2("ZB56","a3641"));
		sql.append(" updated,sortid from a36 where a0000 ='"+a0000+"'");
		sql.append(" union ");
		sql.append("select a3600 ,a3604a,a3601,a3607,a3627,");
		sql.append("a3611,a0184gz,"+sql2("ZB01","a0111gz")+sql2("ZB01","a0115gz"));
		sql.append(sql2("GB2659","a0111gzb")+sql2("GB3304","a3621"));
		sql.append(sql2("ZB06","a3631")+sql2("ZB56","a3641"));
		sql.append(" updated , sortid from a36z1 where a0000 ='"+a0000+"' ) ");
		sql.append(" order by sortid asc ");
		
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	private String sql2(String code_type,String field) {
		
		return " (select code_name from code_value b where b.code_type='"+code_type+"' and b.code_value="+field+" ) "+field+" ,"; 
	}
	
	
	@PageEvent("perGrid.dogridquery")
	
		public int perdquery(int start, int limit) throws RadowException {
        String sql = this.getPageElement("sql").getValue();
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
		}
	 
	@PageEvent("perGrid.rowdbclick")//人员信息双击事件
	public int perdbclick() throws RadowException, AppException, FileNotFoundException {
		Grid grid1=(Grid) this.getPageElement("perGrid");
		String a0000=(String) grid1.getValue("a0000");
		String a0101=(String) grid1.getValue("a0101");
		
		if(a0000!=null) {
			this.getPageElement("a0000").setValue(a0000);
			this.getPageElement("a0000s").setValue(a0000);
			this.getPageElement("a0101").setValue(a0101);
			this.getExecuteSG().addExecuteCode("radow.doEvent('MGrid.dogridquery');");
			this.getExecuteSG().addExecuteCode("radow.doEvent('MGrid2.dogridquery');");
		}else {
			throw new AppException("人员信息不再系统中");
		}
		
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("queryByName")
	public int queryByName() throws RadowException, AppException {
		String b0111 = this.getPageElement("dept").getValue();
		String userid = this.getPageElement("userid").getValue();
		StringBuffer sql = new StringBuffer();
	
		if (b0111 == null || "".equals(b0111)) {
			b0111 = this.getPageElement("b0111").getValue();

		}
		String name = this.getPageElement("seachName").getValue();
		if (name == null || "".equals(name)||"输入姓名".equals(name)) {
			name = " and 1=1 ";
		}else {
			name = StringEscapeUtils.escapeSql(name.trim());
			name = name.replaceAll("\\s+"," ");
			name = name.replaceAll(" ", ",");
			name = name.replace(".", ",");
			name = name.replace("&", ",");
			name = name.replace("#", ",");
			name = name.replaceAll("[\\t\\n\\r]", ",");
			// 判断name是否包含","
			if (name.indexOf(",") > 0) {
				String[] names = name.split(",");
				String newName = "";
				for (String n : names) {
					if (n == null || "".equals(n)) {
						continue;
					}
					newName = newName + "'" + n + "',";
				}
				if (newName != null) {
					newName = newName.substring(0, newName.length() - 1);
					
					name = " and (a01.a0184 in (" + newName + ") or a01.a0101 in (" + newName
							+ ") ) ";
				}
		}else {
			name = " and (a01.a0184 like '%" + name + "%' or a01.a0101 like '%" + name+ "%' or a01.a0102 ='"+name.toUpperCase()+"') ";
		}
		}
		
			sql.append("select "+cols+" from A01 a01 where 1=1 "
					+ " and (a01.a0221 not in ('1A01','1A02','1A11','1A12') or a01.a0221 is null) ");
			sql.append(" and a01.status!='4' and a0163='1' and a01.a0000 is not null  "+name+" ");
			sql.append(" and exists (select 1 from a02 a02 where a02.a0201b in (select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"') "
					+ " and a0281='true' ");
			sql.append(" and a01.a0000=a02.a0000 and a02.a0201b like '" + b0111
					+ "%') order by a01.torgid,a01.torder asc ");
	    this.getPageElement("sql").setValue(sql.toString());
		this.setNextEventName("perGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("addexamine")
	public int addexamine() throws RadowException, SQLException, AppException {
		
		String a0000 =this.getPageElement("a0000").getValue();
		doexamine(a0000);
		this.getExecuteSG().addExecuteCode("freshGrid('perGrid,MGrid,MGrid2')");//刷新列表
		this.setMainMessage("审批成功");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("beatchexamine")
	@Transaction
	public int beatchexamine() throws RadowException, AppException, SQLException {
		List<String> oids=outputlist("pergrid");
		HBSession sess=HBUtil.getHBSession();
		String sql = this.getPageElement("sql").getValue();
		sql =sql.replace(cols, "a01.a0000");
		
		if(oids.size()<1) {
			List list1=sess.createSQLQuery(sql.toString()).list();
			for(int i=0;i<list1.size();i++) {
				oids.add((String) list1.get(i));
			}
		}
		for(String oid:oids) {
			doexamine(oid);
			this.setMainMessage("批量审批成功");
		}
		this.getExecuteSG().addExecuteCode("freshGrid('perGrid,MGrid,MGrid2')");//刷新列表
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	
	private void doexamine(String oid) throws RadowException, AppException, SQLException {
		HBSession sess=HBUtil.getHBSession();
		
		A01 a01=(A01) sess.get(A01.class, oid);
		CallableStatement call = null;
		List list=sess.createSQLQuery("select a3600 from a36new where a0000='"+oid+"' union all"
				+ " select a3600 from a36new where a0000='"+oid+"' ").list();	
		
		if(list.size()<1) {
			this.setMainMessage(a01.getA0101()+"审核表没有数据 ,审批出错!!!");
		}else {
			//参考位置
			call=sess.connection().prepareCall("{call PRO_A36LOG(?)}");
			call.setString(1, oid);
			call.execute();
			call=sess.connection().prepareCall("{call PRO_A36_EXAMINE(?)}");
			call.setString(1, oid);
			call.execute();
		}
		
	}
	
	@PageEvent("addfile")
	@Transaction
	public int addfile() throws RadowException, SQLException {
		HBSession sess=HBUtil.getHBSession();
		String a0000 =this.getPageElement("a0000").getValue();
		A01 a01=(A01) sess.get(A01.class, a0000);
		CallableStatement call = null;
		List list=sess.createSQLQuery("select a3600 from a36 where a0000='"+a0000+"' union all"
				+ " select a3600 from a36new where a0000='"+a0000+"' ").list();
		if(list.size()<1) {
			this.setMainMessage(a01.getA0101()+"正式表没有数据 ,归档出错!!!");
		}else {
			call=sess.connection().prepareCall("{call PRO_A36LOG(?)}");
			call.setString(1, a0000);
			call.execute();
			this.setMainMessage("归档成功");
		}
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("addbatchfile")
	@Transaction	
	public int addbeatchfile() throws RadowException, AppException, SQLException {
		List<String> oids=outputlist("pergrid");
		HBSession sess=HBUtil.getHBSession();
		String sql = this.getPageElement("sql").getValue();
		sql =sql.replace(cols, "a01.a0000");
		if(oids.size()<1) {
			List list1=sess.createSQLQuery(sql.toString()).list();
			for(int i=0;i<list1.size();i++) {
				oids.add((String) list1.get(i));
			}
		}
		
		for(String oid:oids) {
			
			dofile(oid);
			this.setMainMessage("归档成功");
		}
		
		
		
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	
	
	private void dofile(String oid) throws RadowException, AppException, SQLException {
		HBSession sess=HBUtil.getHBSession();
		
		A01 a01=(A01) sess.get(A01.class, oid);
		CallableStatement call = null;
		List list=sess.createSQLQuery("select a3600 from a36 where a0000='"+oid+"' union all"
				+ " select a3600 from a36new where a0000='"+oid+"' ").list();
		if(list.size()<1) {
			this.setMainMessage(a01.getA0101()+"正式表没有数据 ,归档出错!!!");
		}else {
			call=sess.connection().prepareCall("{call PRO_A36LOG(?)}");
			call.setString(1, oid);
			call.execute();
		}
	}
	
	
	
	private List<String> outputlist(String gridname) throws RadowException, AppException {
		
		PageElement pe = this.getPageElement(gridname);
		List<HashMap<String, Object>> list = pe.getValueList();
		List<String> oids = new ArrayList<String>();
		
		for (int j = 0; j < list.size(); j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 =  map.get("percheck");
			if (check1 != null && check1.equals(true)) {
				oids.add(map.get("a0000").toString());
			}
		}
		
		
		return oids;
	}
	
}
