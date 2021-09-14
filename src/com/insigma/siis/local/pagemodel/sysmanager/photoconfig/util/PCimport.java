package com.insigma.siis.local.pagemodel.sysmanager.photoconfig.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.insigma.odin.framework.persistence.HBSession;

public class PCimport {
	
	public static void importExcel(HBSession sess,String DIR) {
		try {
			Connection conn = sess.connection();
			PreparedStatement pst = null;
			
			String cols = "";
			for(int colIndex=0;colIndex<41;colIndex++){
				cols += "?,";
			}
			cols = cols.substring(0,cols.length()-1);
			pst = conn.prepareStatement("insert into HZ_BZKHPER_MX values ("+cols+")");
	
			File impdir = new File(DIR);
			File[] impfiles = impdir.listFiles();
			for(File impfile : impfiles){
				try {
					System.out.println(impfile.getName());
					imp(sess,impfile,pst);
					
				} catch (Exception e) {
					//System.out.println(impfile.getName());
					e.printStackTrace();
				}
				
				
			}
			pst.close();
			//conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private static void imp(HBSession sess, File impfile, PreparedStatement pst) throws Exception {
		
		
		String filename = impfile.getName();
		String pfilename = impfile.getParentFile().getName();
		InputStream is = new FileInputStream(impfile);  //读取复制好的文件
		
		Workbook workbook = null;
		
		if(filename.toLowerCase().endsWith(".xlsx")){
			workbook = new XSSFWorkbook(is);      //默认读取2007版的Excel
		}else{
			workbook = new HSSFWorkbook(is);      
		}
		
		Sheet sheet = workbook.getSheetAt(1);
		int rows_total = sheet.getLastRowNum();
		Cell cell = null;
		int xuhao = 0;
		for(int rowIndex=4;rowIndex<=rows_total;rowIndex++){
			Row row = sheet.getRow(rowIndex);
			int col_total = row.getLastCellNum();
			//System.out.print(rowIndex+":"+col_total);
			
			cell = row.getCell(1);//优秀分数
			if(cell!=null){
				cell.setCellType(CellType.STRING);
				String v = cell.getStringCellValue();
				if(StringUtils.isEmpty(v)){
					continue;
				}
				//System.out.print(":"+v);
			}else{
				continue;
			}
			
			
			pst.setString(1, pfilename);	
			pst.setString(2, filename);	
			pst.setString(3, ++xuhao + "");	
			for(int colIndex=0;colIndex<38;colIndex++){
				cell = row.getCell(colIndex);
				if(cell!=null){
					cell.setCellType(CellType.STRING);
					String v = cell.getStringCellValue();
					pst.setString(colIndex+4, v);
					
				}else{
					pst.setString(colIndex+4, "");
				}
					
				
			}
			pst.addBatch();
		}
		//System.out.println(filename+":"+rows_total);
		pst.executeBatch();
		pst.clearParameters();
		workbook.close();
	}

}
