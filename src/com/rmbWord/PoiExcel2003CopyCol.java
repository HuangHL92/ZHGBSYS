package com.rmbWord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.record.common.UnicodeString;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class PoiExcel2003CopyCol {

	public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("目标文件地址",    "H:\\统计报表\\第五套\\J1606008 第三表 中央单位领导班子成员年龄情况（四）.xls" );
        map.put("输出文件地址",    "H:\\统计报表\\第五套\\J1606008 第三表 中央单位领导班子成员年龄情况（四）.xls");
        map.put("参照列",        "3");
        map.put("起始位置",        "e5");
        map.put("总行数",        "15");
        String[][] commentStr = {{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=51"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=52"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=53"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=54"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=55"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=56"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=57"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=58"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=59"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=60"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=61"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=62"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=63"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=64"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=65"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）>=66"},
        		{"列条件：TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期）=50","列条件：SUM(TIMESTAMPDIFF(MONTH,人员基本信息.出生日期,报告截止日期))"}
        		};	
        
        
        
        map.put("替换字符串",commentStr );
        replaceCol(map);
	}
	
	public static void replaceCol(Map<String, Object> map) throws Exception{
		String startCell = map.get("起始位置").toString().toUpperCase();
        char[] crs = startCell.toCharArray();
        String clumnstr = "";
        String rowStr = "";
        for(int i=0;i<crs.length;i++){
        	if(crs[i]-'A'>=0){
        		clumnstr = clumnstr+crs[i];
        	}else{
        		rowStr = rowStr+crs[i];
        	}
        }
        System.out.println("起始位置:列"+clumnstr+",行"+rowStr);
        String startColy = excelColStrToNum(clumnstr, clumnstr.length())-1+"";
        String startRowx = (Integer.valueOf(rowStr)-1)+"";
        map.put("起始行", startRowx);
        map.put("起始列", startColy);
        System.out.println("java起始位置:列"+startColy+",行"+startRowx);
		
		
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(map.get("目标文件地址").toString()));  
        //2.得到Excel工作簿对象  
        HSSFWorkbook wb = new HSSFWorkbook(fs);  
        //3.得到Excel工作表对象  
        HSSFSheet sheet = wb.getSheetAt(0);  
     
        HSSFCell cell = null;  
        HSSFPatriarch patr = null;
        HSSFComment comment = null;
        int startCol = Integer.valueOf(map.get("起始列").toString());
        int replaceCol = Integer.valueOf(map.get("参照列").toString());
        int startRow = Integer.valueOf(map.get("起始行").toString());
        int total = Integer.valueOf(map.get("总行数").toString());
        
        String[][] commentStr = (String[][])map.get("替换字符串");
        
        for(int m=0;m<commentStr.length;m++){
        	for(int i=startRow;i<total+startRow;i++){
            	cell = sheet.getRow(i).getCell(replaceCol);
            	
                HSSFComment comment2 = cell.getCellComment();
                if(comment2!=null){
                	patr = sheet.createDrawingPatriarch();
                    comment = patr.createComment(new HSSFClientAnchor(0,0,0,0, (short)4, 7 ,(short) 18, 25));
                    HSSFRichTextString ts = comment2.getString();
                    UnicodeString us = (UnicodeString)ts.get_string().clone();
                    us.setString(us.getString().replace(commentStr[m][0], commentStr[m][1]));
                    HSSFRichTextString ts2 = new HSSFRichTextString();
                    ts2.set_string(us);
                	comment.setString(ts2);
                    cell = sheet.getRow(i).getCell(startCol+m);
                    
                    cell.setCellComment(comment);
                	//System.out.println(comment2.getString());
                }
                
            }
        	System.out.println("第"+(startCol+m+1)+"列完成");
        }
        
        
        
        
        
        FileOutputStream out = new FileOutputStream(map.get("输出文件地址").toString());
        wb.write(out);
        out.close();
	}

	/**
     * Excel column index begin 1
     * @param colStr
     * @param length
     * @return
     */
    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }
}
