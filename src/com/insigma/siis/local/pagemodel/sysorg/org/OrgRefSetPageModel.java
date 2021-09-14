package com.insigma.siis.local.pagemodel.sysorg.org;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.fr.report.core.A.f;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class OrgRefSetPageModel extends PageModel{
	
	public OrgRefSetPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 导出
	 * @return
	 * @throws RadowException
	 * @throws IOException 
	 * @throws AppException 
	 */
	@PageEvent("exportExcel")
	public int exportExcel() throws RadowException, IOException, AppException{
		String sql="";
		String idcheckedarr=request.getParameter("idcheckedarr");
		String orgref=request.getParameter("orgref");
		
		HBSession sess = HBUtil.getHBSession();
		Connection conn= null;
		Statement stmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			//获取构类型机，所在政区，隶属关系，机构类别，机构级别，数据集合
			List<Object>  list=null;
			List<Object>  list_u= new ArrayList<Object>();;
			Map<String, String> map = null;

			if(idcheckedarr.length()>0){//有选择
				map = this.operateCheckedData(idcheckedarr);
				String cueUserid=SysUtil.getCacheCurrentUser().getId();
				String [] arr=idcheckedarr.split(",");
				Set<String> set = map.keySet();
				int i = 0;
				Map<String, Integer> maprow=new HashMap<String, Integer>();
				maprow.put("rownum", 0);
				for(String sv : set){
					if(map.containsValue(sv)){
						sql=" select b.b0111"//机构编码
								+" from b01 b,competence_userdept t where b.b0111=t.b0111 and b.b0111='"+sv+"' and t.userid='"+cueUserid+"'  ORDER BY b.SORTID ASC ";
						 list=sess.createSQLQuery(sql).list();//根据条件查询，所有数据
						 list_u.addAll(list);
					}else{
						 sql="select b.b0111" + 
								"   from b01 b" + 
								"   where b.b0111 in  (select t.b0111 from competence_userdept t where t.userid='"+cueUserid+"')" + 
								"   start with b.b0111 = '"+sv+"'" + 
								" connect by  prior b.b0111 =  b.b0121";
						 list=sess.createSQLQuery(sql).list();//根据条件查询，所有数据
						 list_u.addAll(list);
					}
					i++;
				}
			}
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String d_sql = "delete from REFB01GROUP where GROUPTYPE='"+orgref+"'";
			stmt.execute(d_sql);
			pstmt1 = conn.prepareStatement("delete from REFB01GROUP where b0111=?");
			pstmt2 = conn.prepareStatement("insert into REFB01GROUP values (sys_guid(),?,'"+orgref+"')");
			for (int i = 0; i < list_u.size(); i++) {
				Object o = list_u.get(i);
				pstmt1.setString(1, o+"");
				pstmt1.addBatch();
				pstmt2.setString(1, o+"");
				pstmt2.addBatch();
			}
			pstmt1.executeBatch();
			pstmt2.executeBatch();
			conn.commit();
			this.setMainMessage("设置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RadowException("设置失败!");
		} finally {
			try {
				if(stmt != null)
					stmt.close();;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if(pstmt1 != null)
					pstmt1.close();;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if(pstmt2 != null)
					pstmt2.close();;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	private Map<String, String> operateCheckedData(String idcheckedarr){
		Map<String, String> map = new LinkedHashMap<String, String>();
		String [] arr=idcheckedarr.split(",");
		for(String str : arr){
			String[] s = str.split("@");
			map.put(s[0], s[0].contains(".") ? s[0].substring(0,s[0].lastIndexOf(".")) : "1");
		}
		return map;
	}
}
