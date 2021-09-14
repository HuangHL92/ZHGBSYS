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

public class BQimport {
	static String DIR = "E:\\杭州20201106\\智选标签汇总\\122";
	public static void importExcel(HBSession sess) {
		try {
			Connection conn = sess.connection();
			PreparedStatement pst = null;
			pst = conn.prepareStatement("insert into HZ_BIAOQIAN_HX(a001,a002,a003,a004,a005,a006) values (?,?,?,?,?,?)");
	
			File impdir = new File(DIR);
			File[] impfiles = impdir.listFiles();
			for(File impfile : impfiles){
				try {
					imp(sess,impfile,pst);
				} catch (Exception e) {
					System.out.println(impfile.getName());
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
		InputStream is = new FileInputStream(impfile);  //读取复制好的文件
		
		Workbook workbook = null;
		
		if(filename.toLowerCase().endsWith(".xlsx")){
			workbook = new XSSFWorkbook(is);      //默认读取2007版的Excel
		}else{
			workbook = new HSSFWorkbook(is);      
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		int rows_total = sheet.getLastRowNum();
		
		List<String> a0101s = new ArrayList<String>();
		a0101s.add("0");
		a0101s.add("1");
		a0101s.add("2");
		String yiJiBianQian = "";
		String erJiBianQian = "";
		String sanJiBianQian = "";
		String xuhao = "";
		for(int rowIndex=1;rowIndex<=rows_total;rowIndex++){
			Row row = sheet.getRow(rowIndex);
			int col_total = row.getLastCellNum();
			
			
			if(rowIndex==1){
				for(int colIndex=3;colIndex<=col_total;colIndex++){
					Cell cell = row.getCell(colIndex);
					if(cell!=null){
						cell.setCellType(CellType.STRING);
						String v = cell.getStringCellValue();
						a0101s.add(v);
						//System.out.println(filename+":"+v);
					}
					
				}
			}else{
				Cell cell = row.getCell(0);
				if(cell!=null){
					cell.setCellType(CellType.STRING);
					String xuhaoTemp = cell.getStringCellValue();
					if(!StringUtils.isEmpty(xuhaoTemp)){
						xuhao = xuhaoTemp;
					}
					
				}
				cell = row.getCell(1);
				if(cell!=null){
					cell.setCellType(CellType.STRING);
					String yiJiBianQianTemp = cell.getStringCellValue();
					if(!StringUtils.isEmpty(yiJiBianQianTemp)){
						yiJiBianQian = yiJiBianQianTemp;
					}
					
				}
				
				cell = row.getCell(2);
				if(cell!=null){
					cell.setCellType(CellType.STRING);
					String erJiBianQianTemp = cell.getStringCellValue();
					if(!StringUtils.isEmpty(erJiBianQianTemp)){
						erJiBianQian = erJiBianQianTemp;
					}
					
				}
				
				cell = row.getCell(3);
				if(cell!=null){
					cell.setCellType(CellType.STRING);
					String sanJiBianQianTemp = cell.getStringCellValue();
					if(!StringUtils.isEmpty(sanJiBianQianTemp)){
						sanJiBianQian = sanJiBianQianTemp;
					}
					
				}
				
				for(int colIndex=4;colIndex<=col_total;colIndex++){
					cell = row.getCell(colIndex);
					if(cell!=null){
						cell.setCellType(CellType.STRING);
						String v = cell.getStringCellValue();
						if(!StringUtils.isEmpty(v)){
							//System.out.println(filename+":"+xuhao+":"+yiJiBianQian+":"+erJiBianQian+":"+sanJiBianQian+":"+a0101s.get(colIndex)+":"+v);
							pst.setString(1, filename);
							pst.setString(2, xuhao);
							pst.setString(3, yiJiBianQian);
							pst.setString(4, erJiBianQian);
							pst.setString(5, sanJiBianQian);
							pst.setString(6, a0101s.get(colIndex));
							pst.addBatch();
						}
						
					}
					
				}
			}
			
		}
		//System.out.println(filename+":"+rows_total);
		pst.executeBatch();
		pst.clearParameters();
		workbook.close();
	}

}
