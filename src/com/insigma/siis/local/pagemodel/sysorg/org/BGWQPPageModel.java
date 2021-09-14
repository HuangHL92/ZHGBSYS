package com.insigma.siis.local.pagemodel.sysorg.org;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;

import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class BGWQPPageModel  extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("init();");
		return 0;
	}
	
	@PageEvent("updateb01id")
	public int updateb01id(String b0111) {
		CommQuery cqbs=new CommQuery();
		try {
			String sql="select b0101,b01id,b0140,b0270,b0271,b0272,b0273,b0274 from b01 where b0111='"+b0111+"'";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			this.getPageElement("b01id").setValue(list.get(0).get("b01id").toString());
			this.getPageElement("b0140").setValue(list.get(0).get("b0140")==null?"":list.get(0).get("b0140").toString());
			this.getPageElement("b0270").setValue(list.get(0).get("b0270")==null?"":list.get(0).get("b0270").toString());
			this.getPageElement("b0271").setValue(list.get(0).get("b0271")==null?"":list.get(0).get("b0271").toString());
			this.getPageElement("b0272").setValue(list.get(0).get("b0272")==null?"":list.get(0).get("b0272").toString());
			this.getPageElement("b0273").setValue(list.get(0).get("b0273")==null?"":list.get(0).get("b0273").toString());
			this.getPageElement("b0274").setValue(list.get(0).get("b0274")==null?"":list.get(0).get("b0274").toString());
		
		
			String sqlsel="select wayid,wayname from ZDGW_WAY order by sortid";
			List<HashMap<String, Object>> listsel = cqbs.getListBySQL(sqlsel);
			Map<String, String> map = new TreeMap<String, String>();
			if (listsel != null && listsel.size() > 0) {
				for (HashMap<String, Object> map1 : listsel) {
					map.put(map1.get("wayid").toString(), map1.get("wayname").toString());
				}
			}
			StringBuffer dataarrayjs = new StringBuffer("[");
			Set<String> tablecodes = map.keySet();
			for(String tablecode : tablecodes){
				dataarrayjs.append("['"+tablecode+"','"+map.get(tablecode)+"'],");
			}
			if(map.size()>0){
				dataarrayjs.deleteCharAt(dataarrayjs.length()-1);
			}
			dataarrayjs.append("]");
			this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'allGrid',colIndex:3,dataArray:"+dataarrayjs+"});");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
			
		this.setNextEventName("GWGrid.dogridquery");
		this.setNextEventName("allGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("GWGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int GWSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String b0111=this.getPageElement("b0111").getValue();
		String sql="select a.gwname,decode(a.gwzf,'1','正职','3','副职') gwzf,a.qpid,a.sortid from BGWQP a,b01 b where a.b01id=b.b01id and b.b0111='"+b0111+"' order by sortid";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("allGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int allSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String b0111=this.getPageElement("b0111").getValue();
		String sql="";
		try {
			sql="select * from gwpxall where b0111='"+b0111+"'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				sql="select  t.gwname,b.sortid"
						+ "		,(select a0101 from a01 where t.a0000=a01.a0000) a0101,a0200 id,t.type,bzgw,gwmc from ("
						+ "	select a0000,a0215a gwname,a0200,'1' type from a02 a where a.a0201b='"+b0111+"' and a0281='true' and a0201e in ('1','3','31')  and exists (select 1 from a01 where a01.a0000=a.a0000 and a0163='1' and status='1')"
						+ "		union all"
						+ "	select '',gwname,qpid,'2' type from BGWQP a where a.b01id=(select b01id from b01 where b0111='"+b0111+"')"
						+ "	) t,(select * from gwpxall where b0111='"+b0111+"') b where  t.a0200=b.id(+)  order by nvl(b.sortid,99999)";
			}else {
				sql="select a0215a gwname,(select a0101 from a01 where t.a0000=a01.a0000) a0101,a0200 id,type,'' bzgw,'' gwmc from ("
						+ " select a0000,a0215a,to_number(a0225) a0225,-1 sortid,a0200,'1' type from a02 a where a.a0201b='"+b0111+"' and a0281='true' and a0201e in ('1','3','31') and exists (select 1 from a01 where a01.a0000=a.a0000 and a0163='1' and status='1')"
						+ "		union all"
						+ " select '',gwname,999999,sortid,qpid,'2' type from BGWQP a where a.b01id=(select b01id from b01 where b0111='"+b0111+"')"
						+ ") t  order by type,a0225,sortid";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("updateZS")
	public int updateZS(){
		String zongshu="";
		CommQuery cqbs=new CommQuery();
		try {
			String b01id=this.getPageElement("b01id").getValue();
			String sql="select '缺配'||wm_concat(name) zongshu from (select gwname||gwnum||'名' name from (select gwname,count(1) gwnum,min(sortid) sortid from BGWQP where b01id='"+b01id+"' group by gwname )  order by sortid)";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(!"缺配".equals(list.get(0).get("zongshu"))) {
				zongshu=list.get(0).get("zongshu").toString();
			}
			String sql_up="update b01 set b0236='"+zongshu+"' where b01id='"+b01id+"' ";
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql_up);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("document.getElementById('zongshu').value='"+zongshu+"';");
		this.getExecuteSG().addExecuteCode("parent.location.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("save")
	@NoRequiredValidate
	public int save(String type)throws RadowException{
		String b01id=this.getPageElement("b01id").getValue();
		String flag=this.getPageElement("flag").getValue();//新增或者修改标识
		String gwname=this.getPageElement("gwname").getValue();
		String gwzf=this.getPageElement("gwzf").getValue();
		String zongshu=this.getPageElement("zongshu").getValue();
		if(zongshu==null) {
			zongshu="";
		}
		String qpid="";
		
		try {
			String sql="";
			if("1".equals(flag)) {
				//新增
				qpid=UUID.randomUUID().toString();
				String pxh=getMax_sort(b01id);
				sql="insert into BGWQP (qpid,gwname,gwnum,b01id,sortid,gwzf) values "
					+ " ('"+qpid+"','"+gwname+"','1','"+b01id+"','"+pxh+"','"+gwzf+"')";
				this.getExecuteSG().addExecuteCode("document.getElementById('flag').value='2';document.getElementById('qpid').value='"+qpid+"';");
			}else {
				//修改
				qpid=this.getPageElement("qpid").getValue();
				sql="update BGWQP set gwname='"+gwname+"',gwzf='"+gwzf+"'  where qpid='"+qpid+"' ";
			}
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
//			stmt.close();
			
			if("1".equals(type)) {
				CommQuery cqbs=new CommQuery();
				sql="select '缺配'||wm_concat(name) zongshu from (select gwname||gwnum||'名' name from (select gwname,count(1) gwnum,min(sortid) sortid from BGWQP where b01id='"+b01id+"' group by gwname )  order by sortid)";
				try {
					List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
					zongshu=list.get(0).get("zongshu").toString();
				} catch (AppException e) {
					e.printStackTrace();
				}
				this.getExecuteSG().addExecuteCode("document.getElementById('zongshu').value='"+zongshu+"';");
			}
			
			sql="update b01 set b0236='"+zongshu+"' where b01id='"+b01id+"' ";
//			Statement stmt2 = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			
			stmt.close();
			this.setMainMessage("保存成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("GWGrid.dogridquery");
		this.setNextEventName("allGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("parent.location.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveBZ")
	@NoRequiredValidate
	public int saveBZ()throws RadowException{
		String b01id=this.getPageElement("b01id").getValue();
		String b0140=this.getPageElement("b0140").getValue();
		String b0270=this.getPageElement("b0270").getValue();
		String b0271=this.getPageElement("b0271").getValue();
		String b0272=this.getPageElement("b0272").getValue();
		String b0273=this.getPageElement("b0273").getValue();
		String b0274=this.getPageElement("b0274").getValue();
		try {
			String sql="update b01 set b0140='"+b0140+"',b0270='"+b0270+"',b0271='"+b0271+"',b0272='"+b0272+"',b0273='"+b0273+"',b0274='"+b0274+"' where b01id='"+b01id+"' ";
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			
			stmt.close();
			this.setMainMessage("保存成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("parent.location.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 职务列表单击事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("GWGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int GridOnRowClick() throws RadowException{ 
		//System.out.println("1121212------------------------------------------");
		//获取选中行index
		int index = this.getPageElement("GWGrid").getCueRowIndex();
		String qpid = this.getPageElement("GWGrid").getValue("qpid",index).toString();
		try {
			String sql="select gwname,gwzf from BGWQP where qpid='"+qpid+"'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("gwname").setValue(map.get("gwname").toString());
				this.getPageElement("gwzf").setValue(map.get("gwzf").toString());
				this.getPageElement("flag").setValue("2");
				this.getPageElement("qpid").setValue(qpid);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 职务删除
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deleteGW")
	@GridDataRange
	@NoRequiredValidate
	public int deleteGW(String qpid) throws RadowException{
		String sql="delete from BGWQP where qpid='"+qpid+"' ";
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			
			String b01id=this.getPageElement("b01id").getValue();
			sql="select '缺配'||wm_concat(name) zongshu from (select gwname||gwnum||'名' name from (select gwname,count(1) gwnum,min(sortid) sortid from BGWQP where b01id='"+b01id+"' group by gwname )  order by sortid)";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			String zongshu="";
			if(!"缺配".equals(list.get(0).get("zongshu"))) {
				zongshu=list.get(0).get("zongshu").toString();
			}
			String sql_up="update b01 set b0236='"+zongshu+"' where b01id='"+b01id+"' ";
			stmt.executeUpdate(sql_up);
			stmt.close();
			this.getExecuteSG().addExecuteCode("document.getElementById('zongshu').value='"+zongshu+"';");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getPageElement("gwname").setValue("");
		this.getPageElement("gwzf").setValue("");
		this.getPageElement("qpid").setValue("");
		this.getPageElement("flag").setValue("1");
		this.setNextEventName("GWGrid.dogridquery");
		this.setNextEventName("allGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("parent.location.reload();");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	@PageEvent("rolesort")
	@Transaction
	public int rolesort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("GWGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update BGWQP set sortid = ? where qpid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String qpid = m.get("qpid");
				ps.setInt(1, i);
				ps.setString(2, qpid);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("GWGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("allgwsort")
	@Transaction
	public int allgwsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("allGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		
		Connection con = null;
		try {
			String b0111=this.getPageElement("b0111").getValue();
			String delsql="delete from gwpxall where b0111='"+b0111+"' ";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(delsql);
			stmt.close();
			
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "insert into  gwpxall(gwname,sortid,b0111,id,type,bzgw,gwmc) values (?,?,?,?,?,?,?) ";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String gwname = m.get("gwname");
				String id = m.get("id");
				String type = m.get("type");
				String bzgw = m.get("bzgw");
				String gwmc = m.get("gwmc");
				ps.setString(1, gwname);
				ps.setInt(2, i);
				ps.setString(3, b0111);
				ps.setString(4, id);
				ps.setString(5, type);
				ps.setString(6, bzgw);
				ps.setString(7, gwmc);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("allGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("parent.location.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("allGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws Exception {
		//获取页面数据
		String b0111=this.getPageElement("b0111").getValue();
		Grid grid6 = (Grid) this.getPageElement("allGrid");
		String id = grid6.getValue("id") +"";
		String bzgw = grid6.getValue("bzgw") +"";
		String gwmc = grid6.getValue("gwmc") +"";
		HBSession sess = HBUtil.getHBSession();
		
		String sql="select * from gwpxall where id='"+id+"' and b0111='"+b0111+"'";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(id!=null&&!"".equals(id)&&bzgw!=null&&!"".equals(bzgw)	) {
			String sql2="select  t.gwname,b.sortid,a0200 id,t.type,bzgw,gwmc from (  select a0000,a0215a gwname,a0200,'1' type from a02 a where a.a0201b='"+b0111+"' and a0281='true' and a0201e in ('1','3','31')  and exists (select 1 from a01 where a01.a0000=a.a0000 and a0163='1' and status='1')"
					+ "		union all"
					+ "      select '',gwname,qpid,'2' type from BGWQP a where a.b01id=(select b01id from b01 where b0111='"+b0111+"')"
					+ "   ) t,(select * from gwpxall where b0111='"+b0111+"') b where  t.a0200=b.id(+) and id<>'"+id+"' and bzgw='"+bzgw+"'";
			List<HashMap<String, Object>> list2=cqbs.getListBySQL(sql2);
			if(list2!=null&&list2.size()>0) {
				this.setMainMessage("本单位其他岗位已经选择过该代码！");
				this.setNextEventName("allGrid.dogridquery");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(list!=null&&list.size()>0) {
			sess.createSQLQuery("update gwpxall set bzgw=?,gwmc=? where id=? and b0111=? ").setString(0, bzgw).setString(1, gwmc).setString(2, id).setString(3, b0111).executeUpdate();
		}else {
			String gwname = grid6.getValue("gwname") +"";
			String type = grid6.getValue("type") +"";
			String sortid = grid6.getValue("sortid") +"";
			sess.createSQLQuery("insert into gwpxall(gwname,sortid,b0111,id,type,bzgw,gwmc) values (?,?,?,?,?,?,?) "
					+ "").setString(0, gwname).setString(1, sortid).setString(2, b0111).setString(3, id).setString(4, type).setString(5, bzgw).setString(6, gwmc).executeUpdate();
		}
		sess.flush();
		this.setNextEventName("allGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String b01id){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(sortid),0)+1 from BGWQP where b01id='"+b01id+"'" ).uniqueResult().toString();
		return sort;
	}
}
