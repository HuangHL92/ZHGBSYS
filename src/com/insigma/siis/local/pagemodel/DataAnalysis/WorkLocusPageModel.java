package com.insigma.siis.local.pagemodel.DataAnalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.expword.ExpJSGLRMB;
import com.utils.CommonQueryBS;

import sun.misc.BASE64Encoder;

public class WorkLocusPageModel extends PageModel {
	
	
	@Override
	public int doInit() throws RadowException {
		System.out.println("++++++++++++++++++启动了");
		this.setNextEventName("grid1.dogridquery");
		this.getExecuteSG().addExecuteCode("baidumap();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 人员列表按机构查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("grid1.dogridquery")
	public int grid1GriddoMemberQuery(int start,int limit) throws RadowException{
		
		String ereaid = this.getPageElement("ereaid").getValue();    //选择的机构id
		String personname = this.getPageElement("personname").getValue();    //选择的机构id		
		if (ereaid.equals("-1")) {
			ereaid = "001.001";
		}
		String sql = "";
		String sql1 = "select *" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and b.a0201b like '"+ereaid+"%'" + 
				"   and a.A0160 in ('1', '5', '6')";
		
		String sql2 = "select *" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and b.a0201b like '001.001%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and a.a0101 like '%"+personname+"%'";
		if("".equals(personname)||personname=="") {
			sql = sql1;
		}else {
			sql = sql2;
		}
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}	
	
	@PageEvent("xzgw")
	public int xzgw() throws RadowException{
		String userid = SysManagerUtils.getUserId();
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String ids = "";
			
			BigDecimal c = (BigDecimal) sess.createSQLQuery("select max(nvl(sortid,0)) from fy22 where "
    				+ " fy22.userid in ('"+userid+"')").uniqueResult();
			int sortid = (c==null ? 0:c.intValue()) + 1;
			String sql = "insert into fy22(fy2200,fy2201,fy2202,fy2203,fy2204,"
					+ "fy2201a,fy2201b,fy2201c,sortid,userid) values(?,?,?,?,?, ?,?,?,?,?)";
			conn = sess.connection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			List<HashMap<String, Object>> list = this.getPageElement("jggwInfoGrid").getValueList();
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = list.get(i);
				if(map.get("personcheck")!=null && !map.get("personcheck").toString().equals("") 
						&& (Boolean)(map.get("personcheck"))==true) {
					count ++ ;
					String fy2201 = map.get("b0101").toString();
					String fy2202 = map.get("b0111").toString();
					String fy2203 = map.get("gwname").toString();
					String fy2204 = map.get("gwcode").toString();
					String b0194 = map.get("b0194").toString();
					String b0104 = map.get("b0104")!=null ? map.get("b0104").toString():"";//简称
					//B01 b01 = (B01) sess.get(B01.class, fy2202);
					//String b0104 = b01.getB0104();//简称
		    		//String b0194 = b01.getB0194();//单位类型
		    		
		    		if(b0194.equals("3")) {
		    			throw new RadowException(fy2201+"为机构纷组，请去除！");
		    		} else {
		    			String id = UUID.randomUUID().toString();
		    			
		    			pstmt.setString(1, id);
		    			pstmt.setString(2, fy2201);
		    			pstmt.setString(3, fy2202);
		    			pstmt.setString(4, fy2203);
		    			pstmt.setString(5, fy2204);
		    			pstmt.setString(6, b0104);
		    			if(b0194.equals("2")) {
		    				String name[] = {"",""};
		    				getFrdwName(fy2202, name, conn);
		    				pstmt.setString(7, name[1]);
			    			pstmt.setString(8, name[0]);
		    			} else {
		    				pstmt.setString(7, b0104);
			    			pstmt.setString(8, fy2201);
		    			}
		    			pstmt.setInt(9, sortid);
		    			pstmt.setString(10, userid);
		    			pstmt.addBatch();
		    		}
				}
			}
			if(count == 0) {
				this.setMainMessage("请至少选择一个职位。");
			} else {
				pstmt.executeBatch();
				conn.commit();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','选择成功');radow.doEvent('jggwSelGrid.dogridquery');");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RadowException("系统异常:"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String[] getFrdwName(String codevalueparameter2, String name[], Connection conn) throws RadowException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from b01 where b0111='"+codevalueparameter2+"'");
			if(rs.next()) {
				String b0194 = rs.getString("b0194");
				String b0101 = rs.getString("b0101");
				String b0104 = rs.getString("b0104");
				String b0121 = rs.getString("b0121");
				
				if(b0194.equals("1") || codevalueparameter2.equals("001.001")) {
					name[0] = b0101+name[0];
					name[1] = b0104+name[1];
					return name;
				} else {
					name[0] = b0101+name[0];
					name[1] = b0104+name[1];
					return getFrdwName(b0121, name, conn);
				}
			} else {
				return name;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据异常!");
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	//点击树查询事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException, AppException {		
		if(id==null||"".equals(id)){
			id = "001.001";
		}
		this.getPageElement("ereaid").setValue(id);
		this.setNextEventName("grid1.dogridquery");		
		this.getExecuteSG().addExecuteCode("baidumap();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//双击人员查询事件
	@PageEvent("getmap")
	public int getmap() throws RadowException, AppException {		
		this.getPageElement("personname").setValue("");
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select *" + 
				"  from a17 " + 
				"  left join coordinate " + 
				"  on a17.regioncode = coordinate.regioncode" + 
				" where a17.a0000 = '"+a0000+"'" + 
				"   and a17.regioncode is not null" + 
				" order by a17.start_date asc";
		CommonQueryBS cq=new CommonQueryBS();
		List<HashMap<String, Object>> mapBySQL = cq.getListBySQL(sql);
		StringBuffer jwd = new StringBuffer();
		List list = new ArrayList<String>();
		for(int i=0;i<mapBySQL.size();i++) {
			HashMap<String, Object> map = mapBySQL.get(i);
			String regioncode = map.get("regioncode").toString();
			if(i==0) {
				list.add(regioncode);
				jwd.append("new BMap.Point("+map.get("longitude").toString()+","+map.get("latitude").toString()+"),"); 
			}else if(!mapBySQL.get(i).get("regioncode").toString().equals(mapBySQL.get(i-1).get("regioncode").toString())) {
				list.add(regioncode);
				jwd.append("new BMap.Point("+map.get("longitude").toString()+","+map.get("latitude").toString()+"),"); 
			}
		}
		String jwdstr = "";
		if(jwd.length()==0) {
			jwdstr = "[]";
		}else {
			jwdstr = "["+jwd.toString().substring(0, jwd.toString().length()-1)+"]";
		}
		this.getExecuteSG().addExecuteCode("baidumap("+jwdstr+");");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
