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
        
        map.put("Ŀ���ļ���ַ",    "H:\\ͳ�Ʊ���\\������\\J1606008 ������ ���뵥λ�쵼���ӳ�Ա����������ģ�.xls" );
        map.put("����ļ���ַ",    "H:\\ͳ�Ʊ���\\������\\J1606008 ������ ���뵥λ�쵼���ӳ�Ա����������ģ�.xls");
        map.put("������",        "3");
        map.put("��ʼλ��",        "e5");
        map.put("������",        "15");
        String[][] commentStr = {{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=51"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=52"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=53"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=54"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=55"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=56"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=57"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=58"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=59"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=60"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=61"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=62"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=63"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=64"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=65"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�>=66"},
        		{"��������TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ���ڣ�=50","��������SUM(TIMESTAMPDIFF(MONTH,��Ա������Ϣ.��������,�����ֹ����))"}
        		};	
        
        
        
        map.put("�滻�ַ���",commentStr );
        replaceCol(map);
	}
	
	public static void replaceCol(Map<String, Object> map) throws Exception{
		String startCell = map.get("��ʼλ��").toString().toUpperCase();
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
        System.out.println("��ʼλ��:��"+clumnstr+",��"+rowStr);
        String startColy = excelColStrToNum(clumnstr, clumnstr.length())-1+"";
        String startRowx = (Integer.valueOf(rowStr)-1)+"";
        map.put("��ʼ��", startRowx);
        map.put("��ʼ��", startColy);
        System.out.println("java��ʼλ��:��"+startColy+",��"+startRowx);
		
		
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(map.get("Ŀ���ļ���ַ").toString()));  
        //2.�õ�Excel����������  
        HSSFWorkbook wb = new HSSFWorkbook(fs);  
        //3.�õ�Excel���������  
        HSSFSheet sheet = wb.getSheetAt(0);  
     
        HSSFCell cell = null;  
        HSSFPatriarch patr = null;
        HSSFComment comment = null;
        int startCol = Integer.valueOf(map.get("��ʼ��").toString());
        int replaceCol = Integer.valueOf(map.get("������").toString());
        int startRow = Integer.valueOf(map.get("��ʼ��").toString());
        int total = Integer.valueOf(map.get("������").toString());
        
        String[][] commentStr = (String[][])map.get("�滻�ַ���");
        
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
        	System.out.println("��"+(startCol+m+1)+"�����");
        }
        
        
        
        
        
        FileOutputStream out = new FileOutputStream(map.get("����ļ���ַ").toString());
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
