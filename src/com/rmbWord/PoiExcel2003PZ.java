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
		replaceExeclAll("��", "hang",
				"H:\\ͳ�Ʊ���\\J1603014�ڰ˱� ����Ա�����������ʡ�������ڵط�����  ����һ��--����.xls",
				"H:\\ͳ�Ʊ���\\ddddddd.xls");
	}
	/**
	 * filePath��fileOutPath��дһ��������ԭ�ļ�
	 * @param replace ���滻����ע����
	 * @param replaceTo  �滻�ɵ���ע����
	 * @param filePath	 �ļ�����λ��
	 * @param fileOutPath  �滻��������ļ�λ��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void replaceExeclAll(String replace,String replaceTo,String filePath,String fileOutPath) throws FileNotFoundException, IOException{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));  
        //2.�õ�Excel����������  
        HSSFWorkbook wb = new HSSFWorkbook(fs);  
        //3.�õ�Excel���������  
        HSSFSheet sheet = wb.getSheetAt(0);  
     
        HSSFCell cell = null;  
        HSSFComment comment = null;
        HSSFRow row = null;
        int rowsize = sheet.getPhysicalNumberOfRows();
      //����ÿһ��  
        for (int r = 0; r < rowsize; r++) {  
            row = sheet.getRow(r);  
            if(row!=null){
            	int cellCount = row.getPhysicalNumberOfCells(); //��ȡ������  
                //����ÿһ��  
                for (int c = 0; c < cellCount; c++) {  
                    cell = row.getCell(c);  
                    if(cell!=null){
                    	comment = cell.getCellComment();
                        if(comment!=null){
                        	//System.out.println("��Ԫ��"+r+","+c+"��:");
                        	//System.out.println("���ǰ��"+comment.getString());
                        	HSSFRichTextString ts = comment.getString();
                        	UnicodeString us = ts.get_string();
                        	us.setString(us.getString().replace(replace, replaceTo));
                        	//ts.set_string(comment.getString().toString().replace(replace, replaceTo));
                        	//comment.setString(new HSSFRichTextString(comment.getString().toString().replace(replace, replaceTo)));
                        	//System.out.println("�����"+comment.getString());
                        }
                    }
                    
                }
            }
            
        }
        System.out.println("������!");
        FileOutputStream out = new FileOutputStream(fileOutPath);
        wb.write(out);
        
        wb.close();
	}
}
