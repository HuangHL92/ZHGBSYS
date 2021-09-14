package com.rmbWord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.record.common.UnicodeString;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class PoiExcel2003PZ {

	public static void main(String[] args) throws Exception {
		replaceExeclAll("行", "hang",
				"H:\\统计报表\\J1603014第八表 公务员基本情况（副省级市设在地方机关  ）（一）--邹磊.xls",
				"H:\\统计报表\\ddddddd.xls");
	}
	/**
	 * filePath，fileOutPath填写一样将覆盖原文件
	 * @param replace 配替换的批注文字
	 * @param replaceTo  替换成的批注文字
	 * @param filePath	 文件绝对位置
	 * @param fileOutPath  替换后输出的文件位置
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void replaceExeclAll(String replace,String replaceTo,String filePath,String fileOutPath) throws FileNotFoundException, IOException{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));  
        //2.得到Excel工作簿对象  
        HSSFWorkbook wb = new HSSFWorkbook(fs);  
        //3.得到Excel工作表对象  
        HSSFSheet sheet = wb.getSheetAt(0);  
     
        HSSFCell cell = null;  
        HSSFComment comment = null;
        HSSFRow row = null;
        int rowsize = sheet.getPhysicalNumberOfRows();
      //遍历每一行  
        for (int r = 0; r < rowsize; r++) {  
            row = sheet.getRow(r);  
            if(row!=null){
            	int cellCount = row.getPhysicalNumberOfCells(); //获取总列数  
                //遍历每一列  
                for (int c = 0; c < cellCount; c++) {  
                    cell = row.getCell(c);  
                    if(cell!=null){
                    	comment = cell.getCellComment();
                        if(comment!=null){
                        	//System.out.println("单元格【"+r+","+c+"】:");
                        	//System.out.println("替代前："+comment.getString());
                        	HSSFRichTextString ts = comment.getString();
                        	UnicodeString us = ts.get_string();
                        	us.setString(us.getString().replace(replace, replaceTo));
                        	//ts.set_string(comment.getString().toString().replace(replace, replaceTo));
                        	//comment.setString(new HSSFRichTextString(comment.getString().toString().replace(replace, replaceTo)));
                        	//System.out.println("替代后："+comment.getString());
                        }
                    }
                    
                }
            }
            
        }
        System.out.println("替代完成!");
        FileOutputStream out = new FileOutputStream(fileOutPath);
        wb.write(out);
        
        wb.close();
	}
}
