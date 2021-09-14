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

public class HistoryTeamPageModel extends PageModel {
	
	
	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 职位信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("grid1.dogridquery")
	public int grid1GriddoMemberQuery(int start,int limit) throws RadowException{
		String warndate = this.getPageElement("warndate").getValue();		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(warndate==null||"".equals(warndate)) {
			warndate = formatter.format(date);
		}		
		String ereaid = this.getPageElement("ereaid").getValue();    //选择的机构id
		if (ereaid.equals("-1")) {
			ereaid = "001.001%";
		}
		String sql = "select *" + 
				"  from a02" + 
				"  left join a01" + 
				"  on a01.a0000=a02.a0000" + 
				" where a02.a0201d = '1'" + 
				" and to_date('"+warndate+"','yyyy-mm-dd')>= to_date(substr(a02.a0243,0,6)||'01' ,'yyyymmdd')" + 
				" and to_date('"+warndate+"','yyyy-mm-dd')<= to_date(substr(nvl(a02.a0265,'999912'),0,6)||'01' ,'yyyymmdd')" + 
				" and substr(a01.a0163, 0, 1) = '1'" + 
				" and a01.status != '4'" + 
				" and a02.a0201b like '"+ereaid+"%'" + 
				" order by a02.a0225 asc";
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
		return EventRtnType.NORMAL_SUCCESS;
	}
}
