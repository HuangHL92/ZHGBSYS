package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class CheckRegZQPageModel extends PageModel implements JUpload {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		String checkregid = this.getPageElement("checkregid").getValue();
		String sql="select * from CHECKREGZQ t where CHECKREGID='"+checkregid
				+"' order by SORTID ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("doInitX")
	public int doInitX() throws RadowException, AppException{
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			CommQuery cqbs=new CommQuery();
			String spasql = "select ckfileid,filesize,filename from CHECKREGFILE where "
					+ " checkregid='" +checkregid+"' and filetype='crzq'";
			List<HashMap<String, Object>> spalist=cqbs.getListBySQL(spasql.toString());
			if (spalist != null) {
				List<Map<String, String>> list_temp=null;
				for (HashMap<String, Object> ta : spalist) {
					
					Map<String, String> map_temp = new HashMap<String, String>();
					String type =ta.get("jsf06")!=null ?ta.get("jsf06").toString():"";
					list_temp = new ArrayList<Map<String, String>>();
					map_temp.put("id", ta.get("ckfileid")+"");
					map_temp.put("name", ta.get("filename")+"");
					map_temp.put("fileSize", ta.get("filesize")+"");
					list_temp.add(map_temp);
				}
				//遍历map
				this.setFilesInfo("crzq", list_temp, false);
			}
			
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("doInitX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveBtn.onclick")
	public int saveBtn() throws RadowException{
		this.upLoadFile("crzq");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// 如果文件大于1M则显示M，小于则显示kb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static String  disk = AppConfig.HZB_PATH + "/";
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String filename = formDataMap.get("Filename");
		String classAtt = formDataMap.get("fileid");
		
		String checkregid = formDataMap.get("checkregid");
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		Connection conn = null;
		InputStream in = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String sql = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
			System.out.println(sql);
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, checkregid);
			
			ps.setString(fidex++, filename);
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			
			fi.write(new File(disk + filePath));
			
			
			String sql2 = "insert into CHECKREGZQ values (?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
			//System.out.println(sql2);
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();//总行数
			a:for (int i = 1; i <= rowNum; i++) {
				int cidex = 3;
				//从Excel第三行开始取值 k行
				for(int x=0; x<11; x++){  //顺序同	前端的华表 x列
					Row row = sheet.getRow(i);
					if(row==null) {
						break a;
					}
					Cell cell = row.getCell(x); 
					//System.out.println(cidex +"---"+cell);
					String value = getCellValue(cell);
					if(x==0) {
						String reg = "^\\d+$";
						if(value==null || "".equals(value.trim())
								|| !value.matches(reg)) {
							break a;
						}
					}
					if(value!=null) {
						ps2.setString(cidex ++, value);
					} else {
						ps2.setString(cidex ++, null);
					}
					
				}
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, checkregid);
				
				ps2.setInt(13, i);
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
			ps.executeUpdate();
			
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("上传附件失败！");
		} finally {
			try{
				if(ps2!=null)
					ps.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}

	@Override
	public String deleteFile(String id) {
		
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME,checkregid from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "'").list();
			if(list==null || list.size()==0){
				return null;//删除失败
			}
			Object[] arr = list.get(0);
			String checkregid = arr[3]+"";
			String directory = disk+arr[1];
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("delete from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "'").executeUpdate();
			sess.createSQLQuery("delete from CHECKREGZQ "
					+ " where checkregid = '" +checkregid + "'").executeUpdate();
			sess.flush();
			
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	/**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell){
    	//判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            DecimalFormat df = new DecimalFormat("0");  
            cellValue =  df.format(cell.getNumericCellValue());
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
