package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class PZZDGWTJPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		this.getPageElement("query_type").setValue("9");
		//this.setNextEventName("waysGrid.dogridquery");
		this.setNextEventName("DXcondGrid.dogridquery");
		//this.setNextEventName("ZGcondGrid.dogridquery");
		//this.setNextEventName("QZcondGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("zdgwGrid.dogridquery")
	@NoRequiredValidate         
	public int zdgwDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String type=this.getPageElement("zdgw_type").getValue();
		String sql ="";
		if(type==null||"".equals(type)||"0".equals(type)) {
			sql = "select zdgwname,decode(dwtype,'1','市直','2','国企高校','3','区县市','') dwtype,decode(zdgwtype,'1','正职','3','副职','') zdgwtype,userid,zdgwid from ZDGW where 1=2 order by sortid";
		}else {
			sql = "select zdgwname,decode(dwtype,'1','市直','2','国企高校','3','区县市','') dwtype,decode(zdgwtype,'1','正职','3','副职','') zdgwtype,userid,zdgwid from ZDGW where dwtype='"+type+"' order by sortid";
		}
		this.pageQuery(sql, "SQL", start, 500); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("waysGrid.dogridquery")
	@NoRequiredValidate         
	public int waysDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String zdgwid=this.getPageElement("zdgwid").getValue();
		String sql = "select wayname,decode(dwtype,'1','市直','2','国企高校','3','区县市','') dwtype,userid,wayid from ZDGW_WAY where zdgwid='"+zdgwid+"' order by sortid";
		this.pageQuery(sql, "SQL", start, 500); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("DXcondGrid.dogridquery")
	@NoRequiredValidate         
	public int DXDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String type=this.getPageElement("query_type").getValue();
		String sql ="";
		if(type==null||"".equals(type)||"9".equals(type)) {
			sql = "select mxname dcondname,mxdsec dconddesc,mxtype,dxmxid from ZDGW_DXMX where substr(mxtype,1,1)='9' order by mxtype";
		}else {
			sql = "select mxname dcondname,mxdsec dconddesc,mxtype,dxmxid from ZDGW_DXMX where substr(mxtype,1,1)='"+type+"' order by mxtype";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("ZGcondGrid.dogridquery")
	@NoRequiredValidate         
	public int ZGDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String wayid=this.getPageElement("wayid").getValue();
		String sql = "select mxname zgcondname,mxdsec zgconddesc,mxcs1 zgcondcs1,decode(tjtype,'2',csdesc2,mxcs2) zgcondcs2,tjtype zgtjtype,zgmxid from ZDGW_ZGMX where wayid='"+wayid+"' order by mxtype";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("QZcondGrid.dogridquery")
	@NoRequiredValidate         
	public int QZDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String wayid=this.getPageElement("wayid").getValue();
		String sql = "select mxname qzcondname,mxdsec qzconddesc,mxcs1 qzcondcs1,decode(tjtype,'2',csdesc2,mxcs2) qzcondcs2,tjtype qztjtype,grade qzgrade,qzmxid from ZDGW_QZMX where wayid='"+wayid+"' order by mxtype";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 保存
	 */         
	@PageEvent("save")
	@Transaction
	public int save() throws RadowException {
		String wayid=this.getPageElement("wayid").getValue();
		CommQuery commQuery =new CommQuery();
		String sql_all="select a01.a0000  from ZHGBSYS.A01 a01 where a0163='1' and a0288 is not null and a0165 not like '%01%' and a0165 not like '%02%' ";
		try {
			String sql_zg="select mxsql,mxtype from ZDGW_ZGMX where wayid='"+wayid+"' ";
			List<HashMap<String, Object>> list_zg = commQuery.getListBySQL(sql_zg);
			if(list_zg!=null&&list_zg.size()>0) {
				String zg1="and (";
				String zg2="and";
				for(HashMap<String, Object> map:list_zg) {
					if("10".equals(map.get("mxtype"))||"910".equals(map.get("mxtype"))) {
						zg1=zg1+" ("+map.get("mxsql")+") or";
					}else {
						zg2=zg2+" "+map.get("mxsql")+" and";
					}
				}
				if("and (".equals(zg1)){
					zg1="";
				}else {
					zg1=zg1.substring(0,zg1.length()-2)+")";
				}
				zg2=zg2.substring(0,zg2.length()-3);
				sql_all=sql_all+zg1+zg2;
			}
			String sql_gz="select '(case when '||mxsql||' then '||grade||' else 0 end)' as mxsql  from ZDGW_QZMX where wayid='"+wayid+"' ";
			List<HashMap<String, Object>> list_gz = commQuery.getListBySQL(sql_gz);
			if(list_gz!=null&&list_gz.size()>0) {
				String qz="order by ";
				for(HashMap<String, Object> map:list_gz) {
					qz=qz+map.get("mxsql")+"+";
				}
				qz=qz.substring(0,qz.length()-1);
				sql_all=sql_all+qz+" desc";
				sql_all=sql_all+", (select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')  from (select a02.a0000,b0269, a0225,"
						+ " row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn from a02, b01"
						+ " where a02.a0201b = b01.b0111  and a0281 = 'true' and a0201b like '001.001%') t where rn = 1  and t.a0000 = a01.a0000)";
			}else {
				sql_all=sql_all+"order by (select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')  from (select a02.a0000,b0269, a0225,"
						+ " row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn from a02, b01"
						+ " where a02.a0201b = b01.b0111  and a0281 = 'true' and a0201b like '001.001.002.01N%') t where rn = 1  and t.a0000 = a01.a0000)";
			}
			sql_all=sql_all.replaceAll("'", "''");
			HBSession sess = HBUtil.getHBSession();
			String sql="update zdgw_way set sql='"+sql_all+"' where wayid='"+wayid+"'";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setMainMessage("保存成功");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 新增资格条件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addZGMX")
	@Transaction
	public int addZGMX(String dxmxid) throws RadowException {
		String wayid=this.getPageElement("wayid").getValue();
		if(wayid==null||"".equals(wayid)) {
			this.getExecuteSG().addExecuteCode("alert('请先选择方案');");
		}else {
			HBSession sess = HBUtil.getHBSession();
			try {
				boolean flag=dxmxid.contains("##");
				if(flag) {
					dxmxid=dxmxid.replace("##1", "");
				}
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String sql="insert into ZDGW_ZGMX(zgmxid,mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,wayid,mxsql_p,cstype,tjtype,mxcs_p,csdesc2) "
						+ " select '"+uuid+"',mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,'"+wayid+"',mxsql_p,cstype,tjtype,mxcs_p,csdesc2 from ZDGW_DXMX where dxmxid='"+dxmxid+"'";
				Statement stmt = sess.connection().createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				this.setNextEventName("ZGcondGrid.dogridquery");
				if(flag) {
					CommQuery cqbs=new CommQuery();
					sql="select mxname,mxcs1 from ZDGW_ZGMX where zgmxid='"+uuid+"'";
					List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
					this.getExecuteSG().addExecuteCode("$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'zg',id:'"+uuid+"',cs:'"+list.get(0).get("mxcs1")+"',query:''},g_contextpath);");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 新增权重条件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addQZMX")
	@Transaction
	public int addQZMX(String dxmxid) throws RadowException {
		String wayid=this.getPageElement("wayid").getValue();
		if(wayid==null||"".equals(wayid)) {
			this.getExecuteSG().addExecuteCode("alert('请先选择方案');");
		}else {
			HBSession sess = HBUtil.getHBSession();
			try {
				boolean flag=dxmxid.contains("##");
				if(flag) {
					dxmxid=dxmxid.replace("##1", "");
				}
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String sql="insert into ZDGW_QZMX(qzmxid,mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,grade,wayid,mxsql_p,cstype,tjtype,mxcs_p,csdesc2) "
						+ " select '"+uuid+"',mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,100,'"+wayid+"',mxsql_p,cstype,tjtype,mxcs_p,csdesc2 from ZDGW_DXMX where dxmxid='"+dxmxid+"'";
				Statement stmt = sess.connection().createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				this.setNextEventName("QZcondGrid.dogridquery");
				if(flag) {
					CommQuery cqbs=new CommQuery();
					sql="select mxname,mxcs1 from ZDGW_QZMX where qzmxid='"+uuid+"'";
					List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
					this.getExecuteSG().addExecuteCode("$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'qz',id:'"+uuid+"',cs:'"+list.get(0).get("mxcs1")+"',query:''},g_contextpath);");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteZGMX")
	@Transaction
	public int deleteZGMX(String zgmxid) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			String sql="delete from ZDGW_ZGMX where zgmxid='"+zgmxid+"' ";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("ZGcondGrid.dogridquery");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteQZMX")
	@Transaction
	public int deleteQZMX(String qzmxid) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			String sql="delete from ZDGW_QZMX where qzmxid='"+qzmxid+"' ";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("QZcondGrid.dogridquery");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delzdgw")
	@Transaction
	public int delzdgw(String zdgwid) throws RadowException {
		String sql="";
		try {
			CommQuery cqbs=new CommQuery();
			sql="select wayname,wayid from zdgw_way where zdgwid='"+zdgwid+"'";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			String wayid="";
			for(HashMap<String, Object> map:list) {
				wayid=map.get("wayid")==null?"":map.get("wayid").toString();
				sql="delete from zdgw_way where wayid='"+wayid+"'";
				stmt.executeUpdate(sql);
				
				sql="delete from zdgw_zgmx where wayid='"+wayid+"'";
				stmt.executeUpdate(sql);
				
				sql="delete from zdgw_qzmx where wayid='"+wayid+"'";
				stmt.executeUpdate(sql);
			}
			sql="delete from zdgw where zdgwid='"+zdgwid+"'";
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("zdgwGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("document.getElementById('zdgwid').value = '';updateWay();document.getElementById('wayid').value = '';updateZDGWZG();updateZDGWQZ();");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delway")
	@Transaction
	public int delway(String wayid) throws RadowException {
		CommQuery commQuery =new CommQuery();
		try {
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			String sql="delete from zdgw_way where wayid='"+wayid+"'";
			stmt.executeUpdate(sql);
			
			sql="delete from zdgw_zgmx where wayid='"+wayid+"'";
			stmt.executeUpdate(sql);
			
			sql="delete from zdgw_qzmx where wayid='"+wayid+"'";
			stmt.executeUpdate(sql);
			
			stmt.close();
			this.setNextEventName("waysGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("document.getElementById('wayid').value = '';updateZDGWZG();updateZDGWQZ();");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("rolesort")
	  @Transaction
	  public int rolesort() throws RadowException {
		String zdgwid=this.getPageElement("zdgwid").getValue();
	    List<HashMap<String, String>> list = this.getPageElement("waysGrid").getStringValueList();
	    HBSession sess = HBUtil.getHBSession();
	    Connection con = null;
	    try {
	      con = sess.connection();
	      con.setAutoCommit(false);
	      String sql = "update zdgw_way set sortid = ? where wayid=? and zdgwid='"+zdgwid+"' ";
	      PreparedStatement ps = con.prepareStatement(sql);
	      int i = 1;
	      String sql_cnt="";
	      CommQuery commQuery =new CommQuery();
	      for (HashMap<String, String> m : list) {
	        String wayid = m.get("wayid");
	        ps.setInt(1, i);
	        ps.setString(2, wayid);
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
	    this.setNextEventName("waysGrid.dogridquery");
	    return EventRtnType.NORMAL_SUCCESS;
	  }
	
	@PageEvent("zdgwsort")
	  @Transaction
	  public int zdgwsort() throws RadowException {
	    List<HashMap<String, String>> list = this.getPageElement("zdgwGrid").getStringValueList();
	    HBSession sess = HBUtil.getHBSession();
	    Connection con = null;
	    try {
	      con = sess.connection();
	      con.setAutoCommit(false);
	      String sql = "update zdgw set sortid = ? where zdgwid=?  ";
	      PreparedStatement ps = con.prepareStatement(sql);
	      int i = 1;
	      String sql_cnt="";
	      CommQuery commQuery =new CommQuery();
	      for (HashMap<String, String> m : list) {
	        String zdgwid = m.get("zdgwid");
	        ps.setInt(1, i);
	        ps.setString(2, zdgwid);
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
	    this.setNextEventName("zdgwGrid.dogridquery");
	    return EventRtnType.NORMAL_SUCCESS;
	  }
	
}
