package com.insigma.siis.local.pagemodel.xbrm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.utils.CommonQueryBS;

import net.sf.json.JSONObject;

/**
 * 预警明细
 * @author a
 *
 */
public class MergeSendDocPageModel extends PageModel{

	/**
	 * 页面初始化
	 */
	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("radow.doEvent('gridcq.dogridquery');radow.doEvent('gridwh.dogridquery');"
				+ "radow.doEvent('NiRenGrid.dogridquery');radow.doEvent('WorkUnitsGrid.dogridquery');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("gridcq.dogridquery")
	public int dogridcqQuery(int start,int limit) throws RadowException{
		String p = this.getPageElement("subWinIdBussessId").getValue();
		String sql="select a0000,js0102,js0108,r.rb_id,r.rb_name from js01 j,RECORD_BATCH r where j.rb_id=r.rb_id and rbm_id ='"+p+"' order by rb_sysno desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("gridwh.dogridquery")
	public int dogridwhQuery(int start,int limit) throws RadowException{
		String p = this.getPageElement("subWinIdBussessId").getValue();
		String sql="select rbd001,rbd003,rbd004,rbd005,rbd006,rbd000 from RECORD_BATCH_DOCNO r where rbm_id ='"+p+"' order by rbd002 desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("saveWh.onclick")
	public int saveWh() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String p = this.getPageElement("subWinIdBussessId").getValue();
		String rbd001 = this.getPageElement("rbd001").getValue();
		String rbd000 = this.getPageElement("rbd000").getValue();
		String rbd003 = this.getPageElement("rbd003").getValue();
		String rbd004 = this.getPageElement("rbd004").getValue();
		String rbd005 = this.getPageElement("rbd005").getValue();
		String rbd006 = this.getPageElement("rbd006").getValue();
		try {
			if(rbd003 == null  || rbd003.equals("")) {
				this.setMainMessage("请选择文号类型！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(rbd004 == null  || rbd004.equals("")) {
				this.setMainMessage("请填写文号年度！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(rbd005 == null  || rbd005.equals("")) {
				this.setMainMessage("请填写文号序号！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(rbd006 == null || rbd006.equals("")){
				this.setMainMessage("请填写发文时间！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			rbd001 = (rbd003.equals("1")?"锡委组":"锡组干")+"〔"+rbd004+"〕"+rbd005+"号";

			if(rbd000!=null &&  !rbd000.trim().equals("")) {
				String sql = "update RECORD_BATCH_DOCNO set rbd001= ?,rbd003= ?,"
						+ " rbd004= ?, rbd005= ?, rbd006= ?  where rbd000=?";
				sess.createSQLQuery(sql).setString(0, rbd001).setString(1, rbd003).setString(2, rbd004)
					.setString(3, rbd005).setString(4, rbd006).setString(5, rbd000).executeUpdate();
			} else {
				String sql = "insert into  RECORD_BATCH_DOCNO values(sys_guid(),?,?,sysdate,?,?,?,?)";
				sess.createSQLQuery(sql).setString(0, p).setString(1, rbd001).setString(2, rbd003)
					.setString(3, rbd004).setString(4, rbd005).setString(5, rbd006).executeUpdate();
			}
			this.getExecuteSG().addExecuteCode("$h.alert('提示','保存成功！');radow.doEvent('gridwh.dogridquery');");
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("保存异常");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("delwh")
	@NoRequiredValidate
	public int delwh(String whid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			String ids = whid.replace(",", "','");
			
			String sql_1 = "delete from js23 where js2300 in (select js2300 from js22 where RBD000 in ('"+ids
					+"')) or js2300 in (select js2300 from js24 where RBD000 in ('"+ids+"'))" ;
			sess.createSQLQuery(sql_1).executeUpdate();
			
			String a22sql_u = "update js22 set js2300='',RBD000='',RBD001='' where RBD000 in ('"+ids+"')";
			sess.createSQLQuery(a22sql_u).executeUpdate();
			
			String sql_0 = "delete from js24 where rbd000 in ('"+ids+"')";
			sess.createSQLQuery(sql_0).executeUpdate();
			
			String sql = "delete from RECORD_BATCH_DOCNO where rbd000 in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			this.getExecuteSG().addExecuteCode("$h.alert('提示','删除成功！');radow.doEvent('gridwh.dogridquery');");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("删除异常");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("NiRenGrid.dogridquery")
    @NoRequiredValidate
    public int  niRenGridQuery(int start,int limit) throws RadowException{
    	String p = this.getPageElement("subWinIdBussessId").getValue();
    	String wh = this.getPageElement("whcheckid").getValue();
    	
    	String sql="select j2.js0100,js2200,js2201,js2202,js2203,js2204,js2205,j1.a0000,j1.js0102,js2300,j1.rb_id,j2.rbd000,j2.rbd001,decode(rbd000,'"+wh+"','true','false') checkid from js22 j2,js01 j1"
    			+ " where j1.js0100=j2.js0100 and j2.js0100 in (select j.js0100 from js01 j,RECORD_BATCH r where"
    			+ " j.rb_id=r.rb_id and rbm_id ='"+p+"')";
    	this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
    }
	
	@PageEvent("WorkUnitsGrid.dogridquery")
    @NoRequiredValidate
    public int workUnitsGridQuery(int start, int limit) throws RadowException, AppException {
    	String p = this.getPageElement("subWinIdBussessId").getValue();
    	String wh = this.getPageElement("whcheckid").getValue();
    	String a0200ssql="select rb_id from RECORD_BATCH where rbm_id ='"+p+"'";
    	CommonQueryBS cq=new CommonQueryBS();
    	List<HashMap<String, Object>> list = cq.getListBySQL(a0200ssql);
    	String rbids = "";
    	if(list.size()>0){
    		for (int i = 0; i < list.size(); i++) {
    			HashMap<String, Object> map = list.get(i);
    			rbids += "'" + map.get("rb_id") +"',";
			}
    	}
    	rbids = rbids.length()>3?rbids.substring(0, rbids.length()-1):"''";
    	String sql = "select a.a0200,a0201b,a0201a,a0215a,a0222,a0255,a0223,rbd001,js0102,js2400,rbd000,j.js0100,j.a0000,j.rb_id,js2300,decode(rbd000,'"+wh+"','true','false') demo from a02 a join js01 j on  a.a0000 = j.a0000 left outer join JS24 k on j.js0100 = k.js0100 and a.a0200 = k.a0200 where "
    			+ " 1=1 and js0122='1' and a0255='1' and "
    			+ " j.rb_id in("+rbids+") and instr(','||js0123||',',','||a.a0200||',')>0 ";
    	String sql2 = "select a.a0200,a0201b,a0201a,a0215a,a0222,a0255,a0223,rbd001,js0102,js2400,rbd000,j.js0100,j.a0000,j.rb_id,js2300,decode(rbd000,'"+wh+"','true','false') demo from v_js_a02 a join js01 j on  a.a0000 = j.a0000 left outer join JS24 k on j.js0100 = k.js0100 and a.a0200 = k.a0200 where "
    			+ " 1=1 and js0122 in ('2','3','4') and "
    			+ " a0255='1' and j.rb_id in("+rbids+") and instr(','||js0123||',',','||a.a0200||',')>0 ";
		
    	String sqlall = "select * from ("+sql +" union "+ sql2 + " ) order by lpad(a0223,5,'" + 0 + "') ";
        System.out.println(sqlall);
        this.pageQuery(sqlall, "SQL", start, limit); //处理分页查询
        return EventRtnType.SPE_SUCCESS;
    }
	/*@PageEvent("setWh")
    @NoRequiredValidate
    public int setWh(String whid) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			
			String p = this.getPageElement("subWinIdBussessId").getValue();
	    	String a0200ssql="select rb_name,rb_org,rb_applicant,rb_cdate,rb_id from record_batch where  rbm_id ='"+p+"'";
	    	CommonQueryBS cq=new CommonQueryBS();
	    	List<HashMap<String, Object>> list = cq.getListBySQL(a0200ssql);
	    	Map<String, HashMap<String, Object>> map_rb  = new HashMap<String, HashMap<String,Object>>();
	    	for (int i = 0; i < list.size(); i++) {
    			HashMap<String, Object> map = list.get(i);
    			map_rb.put(map.get("rb_id")+"", map);
			}
			String now=DateUtil.dateToString(new Date(), "yyyyMMdd");
			String sql = "select rbd001 from RECORD_BATCH_DOCNO where rbd000='"+whid+"' ";
			Object obj = sess.createSQLQuery(sql).uniqueResult();
			List<HashMap<String, Object>> list1 = this.getPageElement("NiRenGrid").getValueList();
			List<HashMap<String, Object>> list2 = this.getPageElement("WorkUnitsGrid").getValueList();
			Map<String , String[]> map = new HashMap<String, String[]>();
			List<String[]> list3 = new ArrayList<String[]>();
			List<String[]> list4 = new ArrayList<String[]>();
			for (HashMap<String, Object> hm : list1) {
				if(hm.get("checkid")!=null&&!"".equals(hm.get("checkid"))&& (hm.get("checkid")+"").equals("true")){
					String js0100 = hm.get("js0100")+"";
					if(map.containsKey(js0100)) {
						String arr[] = map.get(js0100);
						arr[5] = arr[5] + "，" + hm.get("js2201") +hm.get("js2203") ;
						arr[13] = arr[13] + "," + hm.get("js2200")+"";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"", ""+hm.get("js2201")+hm.get("js2203")};
								list3.add(arr2);
							}
						}
					} else {
						String arr[] = new String[15];
						arr[0] = js0100;
						arr[1] = hm.get("a0000")+"";
						arr[2] = hm.get("rb_id")+"";
						arr[3] = UUID.randomUUID().toString();
						arr[4] = obj+"";
						arr[5] = ""+hm.get("js2201") + hm.get("js2203");
						arr[6] = "";
						Map rbmap =map_rb.get(hm.get("rb_id")+"");
						arr[7] = rbmap!=null ? rbmap.get("rb_name") +"" : "";
						arr[8] = rbmap!=null ? rbmap.get("rb_org") +"" : "";
						arr[9] = now;
						arr[10] = rbmap!=null ? rbmap.get("rb_applicant") +"" : "";
						arr[11] = "1";
						arr[12] = now;
						arr[13] = hm.get("js2200")+"";
						arr[14] = "";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"",""+hm.get("js2201")+hm.get("js2203")};
								list3.add(arr2);
							}
						}
					}
				}
			}
			for (HashMap<String, Object> hm : list2) {
				if(hm.get("demo")!=null&&!"".equals(hm.get("demo"))&& (hm.get("demo")+"").equals("true")){
					String js0100 = hm.get("js0100")+"";
					if(map.containsKey(js0100)) {
						String arr[] = map.get(js0100);
						arr[6] = (arr[6]!= null&&!arr[6].equals("")?arr[6]+ "，" :"")  + hm.get("a0201a") +hm.get("a0215a") ;
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"",""+hm.get("a0201a") + hm.get("a0215a"),hm.get("js2400")+""};
								list4.add(arr2);
							}
						}
					} else {
						String arr[] = new String[15];
						arr[0] = js0100;
						arr[1] = hm.get("a0000")+"";
						arr[2] = hm.get("rb_id")+"";
						arr[3] = UUID.randomUUID().toString();
						arr[4] = obj+"";
						arr[5] = "";
						arr[6] = ""+hm.get("a0201a")+hm.get("a0215a");
						Map rbmap =map_rb.get(hm.get("rb_id")+"");
						arr[7] = rbmap!=null ? rbmap.get("rb_name") +"" : "";
						arr[8] = rbmap!=null ? rbmap.get("rb_org") +"" : "";
						arr[9] = now;
						arr[10] = rbmap!=null ? rbmap.get("rb_applicant") +"" : "";
						arr[11] = "1";
						arr[12] = now;
						arr[13] = "";
						arr[14] = "";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"", "" + hm.get("a0201a") +hm.get("a0215a") ,hm.get("js2400")+""};
								list4.add(arr2);
							}
						}
					}
				}
			}
//			String a24sql_u = "delete from js23 where js2300='"+whid+"'";
//			sess.createSQLQuery(a24sql_u).executeUpdate();
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String a23sql_u = "delete from js23 where js2300 in (select j2.js2300 from  js22 j2 "
					+ " where RBD000='"+whid+"') or js2300 in (select j2.js2300 from  js24 j2 "
					+ " where RBD000='"+whid+"')";
			//sess.createSQLQuery(a23sql_u).executeUpdate();
			stmt.execute(a23sql_u);
			String a22sql_u = "update js22 set js2300='',RBD000='',RBD001='' where RBD000='"+whid+"'";
			//sess.createSQLQuery(a22sql_u).executeUpdate();
			stmt.execute(a22sql_u);
			String a24sql_u = "delete from js24 where RBD000='"+whid+"'";
			//sess.createSQLQuery(a24sql_u).executeUpdate();
			stmt.execute(a24sql_u);
			pstmt1 = conn.prepareStatement("insert into js23(js0100,a0000,rb_id,js2300,js2301,js2302,js2303,"
					+ "js2304,js2305,js2306,js2307,js2308,js2309) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)");
			pstmt2 = conn.prepareStatement("update js22 set RBD000=?,RBD001=?,js2300=? where js2200 in (?)");
			pstmt3 = conn.prepareStatement("insert into js24(js2400,a0000,js2300,rbd000,rbd001,js0100"
					+ ") values(sys_guid(),?,?,?,?,?)");
			for(String key :map.keySet()) {
				String arr[] =map.get(key);
				String sql_i="insert into js23(js0100,a0000,rb_id,js2300,js2301,js2302,js2303,js2304,js2305,js2306,js2307,js2308,js2309"
						+ ") values('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"'"
								+ ",'"+arr[8]+"','"+arr[9]+"','"+arr[10]+"','"+arr[11]+"','"+arr[12]+"')";
				sess.createSQLQuery(sql_i).executeUpdate();
				pstmt1.setString(1, arr[0]);pstmt1.setString(2, arr[1]);pstmt1.setString(3, arr[2]);
				pstmt1.setString(4, arr[3]);pstmt1.setString(5, arr[4]);pstmt1.setString(6, arr[5]);
				pstmt1.setString(7, arr[6]);pstmt1.setString(8, arr[7]);pstmt1.setString(9, arr[8]);
				pstmt1.setString(10, arr[9]);pstmt1.setString(11, arr[10]);pstmt1.setString(12, arr[11]);
				pstmt1.setString(13, arr[12]);
				pstmt1.addBatch();
				//String j22ids = arr[13].replace(",", "','");
				//sess.createSQLQuery("update js22 set RBD000='"+whid+"',RBD001='"+obj+"',js2300='"+arr[3]+"' where js2200 in ('"+j22ids+"')").executeUpdate();
				String j22idarr[] = arr[13].split(",");
				for (int i = 0; i < j22idarr.length; i++) {
					String id = j22idarr[i];
					pstmt2.setString(1, whid);pstmt2.setString(2, obj+"");
					pstmt2.setString(3, arr[3]);pstmt2.setString(4, id);
					pstmt2.addBatch();
				}
				String sql24_i="insert into js24(js2400,a0000,js2300,rbd000,rbd001,js0100"
						+ ") values(sys_guid(),'"+arr[1]+"','"+arr[3]+"','"+whid+"','"+obj+"','"+arr[0]+"')";
				sess.createSQLQuery(sql24_i).executeUpdate();
				pstmt3.setString(1, arr[1]);pstmt3.setString(2, arr[3]);pstmt3.setString(3, whid);
				pstmt3.setString(4, obj+"");pstmt3.setString(5, arr[0]);
				pstmt3.addBatch();
			}
			pstmt1.executeBatch();
			pstmt2.executeBatch();
			pstmt3.executeBatch();
			for (int i = 0; i < list3.size(); i++) {
				String arr[] = list3.get(i);
				String sql_u = "update js23 set js2302=replace(replace(js2302,'"+arr[1]+"，"+"'),'"+arr[1]+"') where js2300='"+arr[0]+"'";
				//sess.createSQLQuery(sql_u).executeUpdate();
				stmt.addBatch(sql_u);
			}
			stmt.executeBatch();
			stmt.clearBatch();
			
			for (int i = 0; i < list4.size(); i++) {
				String arr[] = list4.get(i);
				String sql_u = "update js23 set js2303=replace(replace(js2303,'"+arr[1]+"，"+"'),'"+arr[1]+"') where js2300='"+arr[0]+"'";
				//sess.createSQLQuery(sql_u).executeUpdate();
				stmt.addBatch(sql_u);
				String sql_d = "delete js24  where js2400='"+arr[2]+"'";
				stmt.addBatch(sql_d);
				//sess.createSQLQuery(sql_d).executeUpdate();
			}
			stmt.executeBatch();
			stmt.clearBatch();
			conn.commit();
			//sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('提示','设置成功！');radow.doEvent('NiRenGrid.dogridquery');radow.doEvent('WorkUnitsGrid.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RadowException("系统异常！");
				}
			}
			throw new RadowException("系统异常！");
		} finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt1!=null) {
				try {
					pstmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt2!=null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt3!=null) {
				try {
					pstmt3.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	@PageEvent("setWh")
    @NoRequiredValidate
    public int setWh(String rbid) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		try {
			String p = this.getPageElement("subWinIdBussessId").getValue();
			String rbids = "";
			String whid = rbid.split("@@")[0];
			String rbd006 = rbid.split("@@")[1];
			
			// 获取批次里的信息
	    	String a0200ssql="select rb_name,rb_org,rb_applicant,rb_cdate,rb_id from record_batch where  rbm_id ='"+p+"'";
	    	CommonQueryBS cq=new CommonQueryBS();
	    	List<HashMap<String, Object>> list = cq.getListBySQL(a0200ssql);
	    	Map<String, HashMap<String, Object>> map_rb  = new HashMap<String, HashMap<String,Object>>();
	    	for (int i = 0; i < list.size(); i++) {
    			HashMap<String, Object> map = list.get(i);
    			map_rb.put(map.get("rb_id")+"", map);
    			rbids = rbids + map.get("rb_id") +",";
			}
	    	if(rbids.length()>1) {
	    		rbids = rbids.substring(0, rbids.length()-1);
	    	} else {
	    		this.setMainMessage("合并异常，找不到原批次信息！");
	    		return EventRtnType.NORMAL_SUCCESS;
	    	}
			String now=DateUtil.dateToString(new Date(), "yyyyMMdd");
			String sql = "select rbd001 from RECORD_BATCH_DOCNO where rbd000='"+whid+"' ";
			Object obj = sess.createSQLQuery(sql).uniqueResult();
			List<HashMap<String, Object>> list1 = this.getPageElement("NiRenGrid").getValueList();
			List<HashMap<String, Object>> list2 = this.getPageElement("WorkUnitsGrid").getValueList();
			Map<String , String[]> map = new HashMap<String, String[]>();
			List<String[]> list3 = new ArrayList<String[]>();
			List<String[]> list4 = new ArrayList<String[]>();
			for (HashMap<String, Object> hm : list1) {
				if(hm.get("checkid")!=null&&!"".equals(hm.get("checkid"))&& (hm.get("checkid")+"").equals("true")){
					String js0100 = hm.get("js0100")+"";
					if(map.containsKey(js0100)) {
						String arr[] = map.get(js0100);
						arr[5] = arr[5] + "，" + hm.get("js2201") +hm.get("js2203") ;
						arr[13] = arr[13] + "," + hm.get("js2200")+"";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"", ""+hm.get("js2201")+hm.get("js2203"),hm.get("js2200")+""};
								list3.add(arr2);
							}
						}
					} else {
						String arr[] = new String[16];
						arr[0] = js0100;
						arr[1] = hm.get("a0000")+"";
						arr[2] = hm.get("rb_id")+"";
						arr[3] = UUID.randomUUID().toString();
						arr[4] = obj+"";
						arr[5] = ""+hm.get("js2201") + hm.get("js2203");
						arr[6] = "";
						Map rbmap =map_rb.get(hm.get("rb_id")+"");
						arr[7] = rbmap!=null ? rbmap.get("rb_name") +"" : "";
						arr[8] = rbmap!=null ? rbmap.get("rb_org") +"" : "";
						arr[9] = now;
						arr[10] = rbmap!=null ? rbmap.get("rb_applicant") +"" : "";
						arr[11] = "1";
						arr[12] = now;
						arr[13] = hm.get("js2200")+"";
						arr[14] = "";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"",""+hm.get("js2201")+hm.get("js2203"),hm.get("js2200")+""};
								list3.add(arr2);
							}
						}
					}
				}
			}
			for (HashMap<String, Object> hm : list2) {
				if(hm.get("demo")!=null&&!"".equals(hm.get("demo"))&& (hm.get("demo")+"").equals("true")){
					String js0100 = hm.get("js0100")+"";
					if(map.containsKey(js0100)) {
						String arr[] = map.get(js0100);
						arr[6] = (arr[6]!= null&&!arr[6].equals("")?arr[6]+ "，" :"")  + hm.get("a0201a") +hm.get("a0215a") ;
						arr[15] = (arr[15]!= null&&!arr[15].equals("")?arr[15]+ "，" :"") +hm.get("a0200")+"";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"",""+hm.get("a0201a") + hm.get("a0215a"),hm.get("js2400")+""};
								list4.add(arr2);
							}
						}
					} else {
						String arr[] = new String[16];
						arr[0] = js0100;
						arr[1] = hm.get("a0000")+"";
						arr[2] = hm.get("rb_id")+"";
						arr[3] = UUID.randomUUID().toString();
						arr[4] = obj+"";
						arr[5] = "";
						arr[6] = ""+hm.get("a0201a")+hm.get("a0215a");
						Map rbmap =map_rb.get(hm.get("rb_id")+"");
						arr[7] = rbmap!=null ? rbmap.get("rb_name") +"" : "";
						arr[8] = rbmap!=null ? rbmap.get("rb_org") +"" : "";
						arr[9] = now;
						arr[10] = rbmap!=null ? rbmap.get("rb_applicant") +"" : "";
						arr[11] = "1";
						arr[12] = now;
						arr[13] = "";
						arr[14] = "";
						arr[15] = hm.get("a0200")+"";
						map.put(js0100, arr);
						String rbd000 = hm.get("rbd000")+"";
						if(rbd000!=null && !rbd000.equals("") && !rbd000.equals("null")) {
							if(!rbd000.equals(whid)) {
								String arr2 [] = new String[]{hm.get("js2300")+"", "" + hm.get("a0201a") +hm.get("a0215a") ,hm.get("js2400")+""};
								list4.add(arr2);
							}
						}
					}
				}
			}
//			String a24sql_u = "delete from js23 where js2300='"+whid+"'";
//			sess.createSQLQuery(a24sql_u).executeUpdate();
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String a23sql_u = "delete from js23 where js2300 in (select j2.js2300 from  js22 j2 "
					+ " where RBD000='"+whid+"') or js2300 in (select j2.js2300 from  js24 j2 "
					+ " where RBD000='"+whid+"')";
			//sess.createSQLQuery(a23sql_u).executeUpdate();
			stmt.execute(a23sql_u);
			String a22sql_u = "update js22 set js2300='',RBD000='',RBD001='',js2205='0',js2206='0' where RBD000='"+whid+"'";
			//sess.createSQLQuery(a22sql_u).executeUpdate();
			stmt.execute(a22sql_u);
			String a24sql_u = "delete from js24 where RBD000='"+whid+"'";
			//sess.createSQLQuery(a24sql_u).executeUpdate();
			stmt.execute(a24sql_u);
			pstmt1 = conn.prepareStatement("insert into js23(js0100,a0000,rb_id,js2300,js2301,js2302,js2303,"
					+ "js2304,js2305,js2306,js2307,js2308,js2309,js2310) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)");
			pstmt2 = conn.prepareStatement("update js22 set RBD000=?,RBD001=?,js2300=?,js2205='1' where js2200 in (?)");
			pstmt3 = conn.prepareStatement("insert into js24(js2400,a0000,js2300,rbd000,rbd001,js0100"
					+ ",a0200,rb_id) values(sys_guid(),?,?,?,?,?,?,?)");
			for(String key :map.keySet()) {
				
				String arr[] =map.get(key);
				//String sql_i="insert into js23(js0100,a0000,rb_id,js2300,js2301,js2302,js2303,js2304,js2305,js2306,js2307,js2308,js2309"
				//		+ ") values('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"'"
				//				+ ",'"+arr[8]+"','"+arr[9]+"','"+arr[10]+"','"+arr[11]+"','"+arr[12]+"')";
				//sess.createSQLQuery(sql_i).executeUpdate();
				pstmt1.setString(1, arr[0]);pstmt1.setString(2, arr[1]);pstmt1.setString(3, arr[2]);
				pstmt1.setString(4, arr[3]);pstmt1.setString(5, arr[4]);pstmt1.setString(6, arr[5]);
				pstmt1.setString(7, arr[6]);pstmt1.setString(8, arr[7]);pstmt1.setString(9, arr[8]);
				pstmt1.setString(10, arr[9]);pstmt1.setString(11, arr[10]);pstmt1.setString(12, arr[11]);
				pstmt1.setString(13, arr[12]);pstmt1.setString(14, rbd006);
				/*for(String arritem:arr) {
				System.out.println("'"+arritem+"',");
				}*/
				pstmt1.addBatch();
				//String j22ids = arr[13].replace(",", "','");
				//sess.createSQLQuery("update js22 set RBD000='"+whid+"',RBD001='"+obj+"',js2300='"+arr[3]+"' where js2200 in ('"+j22ids+"')").executeUpdate();
				String j22idarr[] = arr[13].split(",");
				for (int i = 0; i < j22idarr.length; i++) {
					String id = j22idarr[i];
					pstmt2.setString(1, whid);pstmt2.setString(2, obj+"");
					pstmt2.setString(3, arr[3]);pstmt2.setString(4, id);
					pstmt2.addBatch();
				}
				//String sql24_i="insert into js24(js2400,a0000,js2300,rbd000,rbd001,js0100"
				//		+ ") values(sys_guid(),'"+arr[1]+"','"+arr[3]+"','"+whid+"','"+obj+"','"+arr[0]+"')";
				//sess.createSQLQuery(sql24_i).executeUpdate();
				String n15 = arr[15];
				if(n15!=null && !n15.equals("")) {
					String dsql = "delete from js24 where js0100='"+arr[0]+"' and a0200 in ('"+n15.replace("，", "','")+"')";
					stmt.addBatch(dsql);
					String a02idarr[] = n15.split("，");
					for (int i = 0; i < a02idarr.length; i++) {
						String id = a02idarr[i];
						pstmt3.setString(1, arr[1]);pstmt3.setString(2, arr[3]);pstmt3.setString(3, whid);
						pstmt3.setString(4, obj+"");pstmt3.setString(5, arr[0]);pstmt3.setString(6, id);
						pstmt3.setString(7, arr[2]);
						pstmt3.addBatch();
					}
				}
				
			}
			stmt.executeBatch();
			stmt.clearBatch();
			
			pstmt1.executeBatch();
			pstmt2.executeBatch();
			pstmt3.executeBatch();
			/*for (int i = 0; i < list3.size(); i++) {
				String arr[] = list3.get(i);
				String sql_u = "update js23 set js2302=replace(replace(js2302,'"+arr[1]+"，"+"'),'"+arr[1]+"') where js2300='"+arr[0]+"'";
				//sess.createSQLQuery(sql_u).executeUpdate();
				stmt.addBatch(sql_u);
			}
			stmt.executeBatch();
			stmt.clearBatch();*/
			
			for (int i = 0; i < list4.size(); i++) {
				String arr[] = list4.get(i);
				//String sql_u = "update js23 set js2303=replace(replace(js2303,'"+arr[1]+"，"+"'),'"+arr[1]+"') where js2300='"+arr[0]+"'";
				//sess.createSQLQuery(sql_u).executeUpdate();
				//stmt.addBatch(sql_u);
				String sql_d = "delete js24  where js2400='"+arr[2]+"'";
				stmt.addBatch(sql_d);
				//sess.createSQLQuery(sql_d).executeUpdate();
			}
			stmt.executeBatch();
			stmt.clearBatch();
			
			String listsql = "select js2300,js0122 from js23,js01 where js01.js0100=js23.js0100 and js01.rb_id in ('"+(rbids.replace(",", "','"))+"') ";
			stmt2 = conn.createStatement();
			rs = stmt2.executeQuery(listsql);
			while (rs.next()) {
				String js2300 = rs.getString(1);
				String v_xt = rs.getString(2);
				Map<String, String> r1 = UpdateTitleBtn(js2300, conn, v_xt);
				Map<String, String> r2 = saveCheck2(js2300, conn, v_xt);
				if((r1.get("zrjc")==null || r1.get("zrjc").trim().equals("")) &&
						(r2.get("showWord")==null || r2.get("showWord").trim().equals(""))) {
					String u_sql= "delete from js23 where js2300 = '"+js2300+"'";
					stmt.addBatch(u_sql);
				} else {
					String u_sql= "update js23 set js2302='"+r1.get("zrjc")+"',js2303='"+r2.get("showWord")+"',"
							+ " js2302a='"+r1.get("zrqm")+"',js2303a='"+r2.get("showWord2")+"',"
							+ " js2302b='"+r1.get("js2200s_old")+"',js2303b='"+r2.get("a0200")+"' "
							+ " where js2300 = '"+js2300+"'";
					stmt.addBatch(u_sql);
				}
				
				
			}
			stmt.executeBatch();
			stmt.clearBatch();
			
			conn.commit();
			//sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('提示','设置成功！');radow.doEvent('NiRenGrid.dogridquery');radow.doEvent('WorkUnitsGrid.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RadowException("系统异常！");
				}
			}
			throw new RadowException("系统异常！");
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt2!=null) {
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt1!=null) {
				try {
					pstmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt2!=null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt3!=null) {
				try {
					pstmt3.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public Map<String, String> saveCheck2(String id, Connection conn, String v_xt) throws RadowException, AppException {
        String a0200s = "";
        Map<String, String> map = new HashMap<String, String>();
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		Statement stmt3 = null;
		ResultSet rs3 = null;
		String wheresql = "";
		String tablesql = "";
		try {
			if(id.equals("e20ed7b0-0a5f-40a2-83eb-b29fd03fe278")) {
				System.out.println(id);
			}
			if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
				wheresql = " and v_xt='"+v_xt+"' ";
				tablesql = " v_js_";
			}
			String sql = "select * from "+tablesql+"A02 where a0200 in (select j.a0200 from js24 j where js2300='" + id + "') "+wheresql+" order by a0223";//-1 其它单位and a0201b!='-1'
	        
			if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
				sql = "select a.* from v_js_a02 a join js01 j on  a.a0000 = j.a0000 left outer join JS24 k on j.js0100 = k.js0100 and a.a0200 = k.a0200 where "
		    			+ " 1=1 and js0122 in ('2','3','4') and a0255='1' and k.js2300 in('"+id+"') "
		    			+ "and instr(','||js0123||',',','||a.a0200||',')>0  order by a0223";
				
			} else {
				sql = "select a.* from a02 a join js01 j on  a.a0000 = j.a0000 left outer join JS24 k on j.js0100 = k.js0100 and a.a0200 = k.a0200 where "
		    			+ " 1=1 and js0122='1' and a0255='1' and "
		    			+ " k.js2300 in('"+id+"') and instr(','||js0123||',',','||a.a0200||',')>0  order by a0223";
			}
			
            Map<String, String> desc_full = new LinkedHashMap<String, String>();//描述 全称
            Map<String, String> desc_short = new LinkedHashMap<String, String>();//描述 简称

            String zrqm = "";//全名 在任
            //String ymqm = "";//以免
            String zrjc = "";//简称
            //String ymjc = "";
            stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	String a0200 = rs.getString("a0200");//任职状态
            	 a0200s =  a0200s + a0200 +",";
                String a0255 = rs.getString("a0255");//任职状态
                String jgbm = rs.getString("a0201b");//机构编码
                List<String> jgmcList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };//机构名称 全名
                jgmcList.add(rs.getString("a0201a") == null ? "" : rs.getString("a0201a"));
               

                List<String> jgmc_shortList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };
                jgmc_shortList.add(rs.getString("a0201c") == null ? "" : rs.getString("a0201c"));//机构名称 简称
                String zwmc = rs.getString("a0215a") == null ? "" : rs.getString("a0215a");//职务名称
                if (jgbm != null && !"".equals(jgbm)) {//导入的数据有些为空。 机构编码不为空。
                	stmt2 = conn.createStatement();
					
					rs2 = stmt2.executeQuery("select * from "+tablesql+"b01 where b0111 ='"+jgbm+"' " +wheresql);
					if(rs2.next()){
						String b0194 = rs2.getString("b0194");//1—法人单位；2—内设机构；3—机构分组。
						String b0121 = rs2.getString("b0121");
                        if ("2".equals(b0194)) {//2—内设机构
                            while (true) {
								stmt3 = conn.createStatement();
								rs3 = stmt3.executeQuery("select * from "+tablesql+"b01 where b0111 ='"+b0121+"'"+wheresql);
								if(rs3.next()){
									b0194 = rs3.getString("b0194");
									b0121 = rs3.getString("b0121");
									if("2".equals(b0194)){//2—内设机构
										//jgmc = b01.getB0101()+jgmc;
										jgmcList.add(rs3.getString("b0101"));
										jgmc_shortList.add(rs3.getString("b0104"));
									}else if("3".equals(b0194)){//3—机构分组
										continue;
									}else if("1".equals(b0194)){//1—法人单位
										//jgmc = b01.getB0101()+jgmc;
										//jgmc_short = b01.getB0104()+jgmc_short;
										//全称
										String key_full = rs3.getString("b0111")+"_$_"+rs3.getString("b0101") + "_$_" + "1";
										String value_full = desc_full.get(key_full);
										if(value_full==null){
											desc_full.put(key_full, jgmcList.toString()+zwmc);
										}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
											List<String> romvelist = new ArrayList<String>();
											for(int i=jgmcList.size()-1;i>=0;i--){
												if(value_full.indexOf(jgmcList.get(i))>=0){
													romvelist.add(jgmcList.get(i));
												}
											}
											jgmcList.removeAll(romvelist);
											
											desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
											
											
										}
										//简称
										String key_short = rs3.getString("b0111")+"_$_"+rs3.getString("b0104") + "_$_" + "1";
										String value_short = desc_short.get(key_short);
										if(value_short==null){
											desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
										}else{
											List<String> romvelist = new ArrayList<String>();
											for(int i=jgmc_shortList.size()-1;i>=0;i--){
												if(value_short.indexOf(jgmc_shortList.get(i))>=0){
													romvelist.add(jgmc_shortList.get(i));
												}
											}
											jgmc_shortList.removeAll(romvelist);
											desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
										}
										break;
									}else{
										break;
									}
								}
								rs3.close();
								stmt3.close();
							}
                        } else if ("1".equals(b0194)) {//1—法人单位； 第一次就是法人单位，不往上递归
                            String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
                            String value_full = desc_full.get(key_full);
                            if (value_full == null) {
                                desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
                            } else {
                                desc_full.put(key_full, value_full + "、" + zwmc);
                            }

                            //简称
                            String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
                            String value_short = desc_short.get(key_short);
                            if (value_short == null) {
                                desc_short.put(key_short, zwmc);
                            } else {
                                desc_short.put(key_short, value_short + "、" + zwmc);
                            }
                        }

                    }
					rs2.close();
					stmt2.close();
                }
                

            }
            rs.close();
			stmt.close();
            for (String key : desc_full.keySet()) {//全名
                String[] parm = key.split("_\\$_");
                String jgzw = parm[1] + desc_full.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrqm += jgzw + "，";
                    }
                }/* else {//以免

                    if (!"".equals(jgzw)) {
                        ymqm += jgzw + "，";
                    }
                }*/
            }


            for (String key : desc_short.keySet()) {//简称
                String[] parm = key.split("_\\$_");
                String jgzw = parm[1] + desc_short.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrjc += jgzw + "，";
                    }
                }/* else {//以免
                    if (!"".equals(jgzw)) {
                        ymjc += jgzw + "，";
                    }
                }*/
            }


            if (!"".equals(zrqm)) {
                zrqm = zrqm.substring(0, zrqm.length() - 1);
            }
           /* if (!"".equals(ymqm)) {
                ymqm = ymqm.substring(0, ymqm.length() - 1);
                ymqm = "(原" + ymqm + ")";
            }*/
            if (!"".equals(zrjc)) {
                zrjc = zrjc.substring(0, zrjc.length() - 1);
            }
            /*if (!"".equals(ymjc)) {
                ymjc = ymjc.substring(0, ymjc.length() - 1);
                ymjc = "(原" + ymjc + ")";
            }*/
           /* A01 a01 = (A01) sess.get(A01.class, a0000);
            a01.setA0192a(zrqm + ymqm);
            a01.setA0192(zrjc + ymjc);
            sess.update(a01);*/
            //人员基本信息界面
            
            map.put("a0200", a0200s.length()>1?a0200s.toString().substring(0, a0200s.toString().length() - 1):"");
            map.put("showWord", zrjc);
            map.put("showWord2", zrqm);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
    }
	
	public Map<String, String> UpdateTitleBtn(String id, Connection conn,String v_xt) throws RadowException, AppException {
    	String js2201s="";
    	String js2202s="";
    	String js2200s_old="";
    	Map<String, String> map = new HashMap<String, String>();
		//HBSession sess = HBUtil.getHBSession();
		//String upsql1="update js22 set js2205='0' where js0100 ='"+JS0100+"'";
		//sess.createSQLQuery(upsql1);
		String sql = "select * from js22 where js2300 in ('"+id+"') order by sortid";//-1 其它单位and a0201b!='-1'
		//List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		Statement stmt3 = null;
		ResultSet rs3 = null;
		String wheresql = "";
		String tablesql = "";
		try {
			if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
				wheresql = " and v_xt='"+v_xt+"' ";
				tablesql = " v_js_";
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			String zrqm = "";//全名 在任
			//String ymqm = "";//以免
			String zrjc = "";//简称
			//String ymjc = "";
			while(rs.next()){
				js2200s_old = js2200s_old + rs.getString("js2200") +",";
				//String a0255 = a02.getA0255();//任职状态
				String jgbm = rs.getString("js2202");//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				String js2201 = rs.getString("js2201");//机构编码;//单位
				String js2202 = rs.getString("js2202");//单位ID
				
				jgmcList.add(js2201==null?"":js2201);
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(rs.getString("js2201a")==null?"":rs.getString("js2201a")+"");//机构名称 简称
				String zwmc = rs.getString("js2203")==null?"":rs.getString("js2203")+"";//职务名称
				
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					stmt2 = conn.createStatement();
					
					rs2 = stmt2.executeQuery("select * from b01 where b0111 ='"+jgbm+"'");
					if(rs2.next()){
						String b0194 = rs2.getString("b0194");//1—法人单位；2—内设机构；3—机构分组。
						String b0121 = rs2.getString("b0121");
						if("2".equals(b0194)){//2—内设机构
							while(true){
								stmt3 = conn.createStatement();
								rs3 = stmt3.executeQuery("select * from b01 where b0111 ='"+b0121+"'");
								if(rs3.next()){
									b0194 = rs3.getString("b0194");
									b0121 = rs3.getString("b0121");
									if("2".equals(b0194)){//2—内设机构
										//jgmc = b01.getB0101()+jgmc;
										jgmcList.add(rs3.getString("b0101"));
										jgmc_shortList.add(rs3.getString("b0104"));
									}else if("3".equals(b0194)){//3—机构分组
										continue;
									}else if("1".equals(b0194)){//1—法人单位
										//jgmc = b01.getB0101()+jgmc;
										//jgmc_short = b01.getB0104()+jgmc_short;
										//全称
										String key_full = rs3.getString("b0111")+"_$_"+rs3.getString("b0101") + "_$_" + "1";
										String value_full = desc_full.get(key_full);
										if(value_full==null){
											desc_full.put(key_full, jgmcList.toString()+zwmc);
										}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
											List<String> romvelist = new ArrayList<String>();
											for(int i=jgmcList.size()-1;i>=0;i--){
												if(value_full.indexOf(jgmcList.get(i))>=0){
													romvelist.add(jgmcList.get(i));
												}
											}
											jgmcList.removeAll(romvelist);
											
											desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
											
											
										}
										//简称
										String key_short = rs3.getString("b0111")+"_$_"+rs3.getString("b0104") + "_$_" + "1";
										String value_short = desc_short.get(key_short);
										if(value_short==null){
											desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
										}else{
											List<String> romvelist = new ArrayList<String>();
											for(int i=jgmc_shortList.size()-1;i>=0;i--){
												if(value_short.indexOf(jgmc_shortList.get(i))>=0){
													romvelist.add(jgmc_shortList.get(i));
												}
											}
											jgmc_shortList.removeAll(romvelist);
											desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
										}
										break;
									}else{
										break;
									}
								}
								rs3.close();
								stmt3.close();
							}
						}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
							String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + "1";
							String value_full = desc_full.get(key_full);
							if(value_full == null){
								desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
							}else{
								desc_full.put(key_full, value_full + "、" + zwmc);
							}
							
							//简称
							String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + "1";
							String value_short = desc_short.get(key_short);
							if(value_short==null){
								desc_short.put(key_short, zwmc);
							}else{
								desc_short.put(key_short, value_short  + "、" +  zwmc);
							}
						}
						
					}
					
					rs2.close();
					stmt2.close();
				}
				
				
				
				if(!js2201s.contains(js2201)){
					js2201s=js2201s+js2201+",";
				}
				if(!js2202s.contains(js2202)){
					js2202s=js2202s+js2202+",";
				}
			}
			rs.close();
			stmt.close();
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}/*else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}*/
			}
			
			
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}/*else{//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "，";
					}
				}*/
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			/*if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(原"+ymqm+")";
			}*/
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			/*if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(原"+ymjc+")";
			}*/
			
			if(!StringUtil.isEmpty(js2201s)){
				js2201s=js2201s.substring(0, js2201s.length()-1);
			}
			if(!StringUtil.isEmpty(js2202s)){
				js2202s=js2202s.substring(0, js2202s.length()-1);
			}
			
			//更新已选择状态
			/*String upsql="update js22 set js2205='1' where js2200 in('"+js2200s+"')";
			HBUtil.executeUpdate(upsql);*/
			/*a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);*/
			//this.getExecuteSG().addExecuteCode("saveafter('"+js2201s+"','"+zrjc+"','"+js2200s_old+"','"+zrqm+"')");
			
			map.put("js2201s", js2201s);
            map.put("zrjc", zrjc);
            map.put("js2200s_old", js2200s_old.length()>1 ?js2200s_old.substring(0, js2200s_old.length()-1):"");
            map.put("zrqm", zrqm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
