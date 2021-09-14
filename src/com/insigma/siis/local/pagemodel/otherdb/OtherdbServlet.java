package com.insigma.siis.local.pagemodel.otherdb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class OtherdbServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("impDxscgFile".equals(method)){
			impDxscgFile(request, response);
		}
		
	}

	private void impDxscgFile(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		//Map<String ,String> formDataMap = new HashMap<String ,String>();
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		InputStream in = null;
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String sqlq="select smt_usergroup.usergroupname,smt_usergroup.id,smt_user.loginname,smt_user.userid from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
			List<HashMap> mapBySQL = CommonQueryBS.getQueryInfoByManulSQL(sqlq);
			String usergroupname=(String)mapBySQL.get(0).get("usergroupname")+"";
			String loginname=(String)mapBySQL.get(0).get("loginname");
			String groupid=(String)mapBySQL.get(0).get("id");
			String userid=(String)mapBySQL.get(0).get("userid");
			
			
			List<FileItem> fileItems = uploader.parseRequest(request); 
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String path = fi.getName();// 文件名称
					String filename = path.substring(path.lastIndexOf("\\")+1);

					in = fi.getInputStream();
					Workbook wb = getWorkbok(in, null, filename);
					Sheet sheet = wb.getSheetAt(0);
					sess = HBUtil.getHBSession();
					BigDecimal o = (BigDecimal) sess.createSQLQuery("select max(CVO011) from Collegevofficail ").uniqueResult();
					int k = o!=null ? o.intValue() : 0;
					conn = sess.connection();
					conn.setAutoCommit(false);
					String sql = "insert into COLLEGEVOFFICAIL(cvo000,cvo001,cvo002c,cvo002,cvo003,cvo004,cvo005,cvo006c,"
							+ "cvo006,cvo007,cvo008,cvo009,cvo010,cvo011) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
					ps = conn.prepareStatement(sql.toString());
					int rowNum =sheet.getLastRowNum();	//总行数
					for (int i = 2; i <= rowNum; i++) {
						Row row = sheet.getRow(i);
						String id = UUID.randomUUID().toString();
						
						String cvo001 = getCellValue(row.getCell(1),"#");
						if(cvo001.trim().equals("")) {
							continue;
						}
						String cvo002 = getCellValue(row.getCell(2),"#");
						String cvo003 = getCellValue(row.getCell(3),"#.##");
						String cvo004 = getCellValue(row.getCell(4),"#.##");
						
						ps.setString(1, id);
						ps.setString(2, cvo001);
						ps.setString(3, cvo002.equals("男")?"1":cvo002.equals("女")?"2":"");
						ps.setString(4, cvo002);
						ps.setString(5, cvo003.replace(".",""));
						ps.setString(6, cvo004.replace(".",""));
						ps.setString(7, getCellValue(row.getCell(5),"#"));
						ps.setString(8, "");
						ps.setString(9, getCellValue(row.getCell(6),"#"));
						ps.setString(10, getCellValue(row.getCell(7),"#"));
						ps.setString(11, getCellValue(row.getCell(8),"#"));
						ps.setString(12, getCellValue(row.getCell(9),"#"));
						ps.setString(13, getCellValue(row.getCell(10),"#"));
						ps.setInt(14, i+k);
						ps.addBatch();
					}
					
					ps.executeBatch();
					conn.commit();
				}
			}
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.Ext.Msg.hide();"
					+ "parent.realParent.infoSearch();parent.parent.odin.ext.getCmp('simpleExpWin3').close();");
			out.println("</script>");
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			try {
				if(conn!=null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('导入失败！');</script>");
			e.printStackTrace();
		}finally{
			try{
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			if(out != null){
				out.close();
			}
		}
		
	}
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	public static Workbook getWorkbok(InputStream in, File file, String filename) throws IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb = null;
		if (filename.toLowerCase().endsWith(EXCEL_XLS)) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (filename.toLowerCase().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		in.close();
		return wb;
	}
	
	/**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell, String numFormat){
    	//判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            DecimalFormat df = new DecimalFormat(numFormat);  
            cellValue =  df.format(cell.getNumericCellValue());
			/* cellValue = cell.getNumericCellValue() +""; */
            break;  
        case HSSFCell.CELL_TYPE_STRING:  
        	cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
        	cellValue = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA: 
            cellValue = cell.getCellFormula();
            break;  
        default:  
            System.out.println("unsuported sell type");  
        break;  
        }
        return cellValue;
       
    }
}
