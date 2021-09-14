package com.insigma.siis.local.pagemodel.zwzc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.JUpload.JUpload;
import com.aspose.cells.Color;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.yntp.YNTPFileExportBS;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.util.CellRangeAddress;

public class RyzsbdPageModel extends PageModel implements JUpload{

	public StringBuffer excel;
	public static InputStream in1;
	public static InputStream in2;
	public static String type1;
	public static String type2;
	@Override
	public int doInit() throws RadowException {
		return 0;
	}
	
	@PageEvent("impBtn1.onclick")
	public int impBtn1() throws RadowException, AppException{
		this.upLoadFile("file1");
		this.getPageElement("upflag1").setValue("1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("impBtn2.onclick")
	public int impBtn2() throws RadowException, AppException{
		this.upLoadFile("file2");
		this.getPageElement("upflag2").setValue("1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static String  disk = YNTPFileExportBS.HZBPATH;
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
		Map<String, String> map = new HashMap<String, String>();
//		for(Map.Entry<String, String> entry : formDataMap.entrySet()) {
//			System.out.print("[Key = " + entry.getKey() + ",value=" + entry.getValue()+"]");
//		}
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				map.put("file_name", isfile);
				if(formDataMap.get("fileid").equals("file1")) { 
					map.put("file_pk", "1");
					in1=fi.getInputStream();
					if(isfile.endsWith(".xlsx")){
						type1 = "xlsx";
					}else{
						type1 = "xls";
					}
				}
				if(formDataMap.get("fileid").equals("file2")) { 
					map.put("file_pk", "2");
					in2=fi.getInputStream();
					if(isfile.endsWith(".xlsx")){
						type2 = "xlsx";
					}else{
						type2 = "xls";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	@Override
	public String deleteFile(String id) {
		try {
			return id;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@PageEvent("Excelshow01")
	public int excelshow01() throws Exception{
		if(in1!=null) {
			String htmlv=new ReadExcel().read(in1,type1);
			this.getPageElement("tabledata1").setValue(htmlv);
			this.getExecuteSG().addExecuteCode("fillExcel1()");
		}
		in1=null;
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("Excelshow02")
	public int excelshow02() throws Exception{
		if(in2!=null) {
			String htmlv=  new ReadExcel().read(in2,type2);
			this.getPageElement("tabledata2").setValue(htmlv);
			this.getExecuteSG().addExecuteCode("fillExcel2()");
		}
		in1=null;
		return EventRtnType.NORMAL_SUCCESS;
	}
	


	

	
	
}
