package com.insigma.siis.local.pagemodel.gzgl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class Exec {
	public static final int FLAG = 7;
	
	public static void main(String[] args) throws IOException {
		train_execl(null);
	}
	
	
	public static String train_execl(List<Map<String, Object>> list_data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style_title = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 24);
		// ����
		font.setFontName("����");
		style_title.setFont(font);
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_title.setBorderBottom(BorderStyle.THIN);
		style_title.setBorderLeft(BorderStyle.THIN);
		style_title.setBorderRight(BorderStyle.THIN);
		style_title.setBorderTop(BorderStyle.THIN);
		HSSFSheet sheet = workbook.createSheet("��ѵ��¼��");
		/*HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style_title);
		cell_00.setCellValue("��ѵ��¼��");*/
		
		HSSFCellStyle style_line_fix = workbook.createCellStyle();//�̶����� ����...
		HSSFFont font_line_fix = workbook.createFont();
		// ���������С
		font_line_fix.setFontHeightInPoints((short) 14);
		// ����
		font_line_fix.setFontName("����");
		font_line_fix.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ
		style_line_fix.setFont(font_line_fix);
		style_line_fix.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_line_fix.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_line_fix.setFont(font_line_fix);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0); 
		cell1.setCellStyle(style_line_fix);
		cell1.setCellValue("����");
		HSSFCell cell2 = row1.createCell(1); 
		cell2.setCellStyle(style_line_fix);
		cell2.setCellValue("�Ա�");
		HSSFCell cell3 = row1.createCell(2); 
		cell3.setCellStyle(style_line_fix);
		cell3.setCellValue("��������");
		HSSFCell cell4 = row1.createCell(3); 
		cell4.setCellStyle(style_line_fix);
		cell4.setCellValue("���ڴ���");
		HSSFCell cell5 = row1.createCell(4); 
		cell5.setCellStyle(style_line_fix);
		cell5.setCellValue("ְ��");
		HSSFCell cell6 = row1.createCell(5); 
		cell6.setCellStyle(style_line_fix);
		cell6.setCellValue("ְ��");
		HSSFCell cell7 = row1.createCell(6); 
		cell7.setCellStyle(style_line_fix);
		cell7.setCellValue("��ѵ���");
		HSSFCell cell8 = row1.createCell(7); 
		cell8.setCellStyle(style_line_fix);
		cell8.setCellValue("��ѵ��λ");
		HSSFCell cell9 = row1.createCell(8); 
		cell9.setCellStyle(style_line_fix);
		cell9.setCellValue("��ѵ�ص�");
		HSSFCell cell10 = row1.createCell(9); 
		cell10.setCellStyle(style_line_fix);
		cell10.setCellValue("��ѵʱ��");
		HSSFCell cell11 = row1.createCell(10); 
		cell11.setCellStyle(style_line_fix);
		cell11.setCellValue("��ѵ����");
		int i=1;
		//�������
		for(Map<String, Object> map:list_data){
			
			try {

				HSSFRow row = sheet.createRow(i);
				HSSFCell cell_1 = row.createCell(0); 
				cell_1.setCellValue(map.get("XM")!=null?map.get("XM").toString():"");
				HSSFCell cell_2 = row.createCell(1); 
				cell_2.setCellValue(map.get("XB")!=null?HBUtil.getCodeName("GB2261",  map.get("XB").toString()):"");
				HSSFCell cell_3 = row.createCell(2); 
				cell_3.setCellValue(map.get("CSNY")!=null?map.get("CSNY").toString():"");
				HSSFCell cell_4 = row.createCell(3); 
				cell_4.setCellValue(map.get("SZCS")!=null?map.get("SZCS").toString():"");
				HSSFCell cell_5 = row.createCell(4); 
				cell_5.setCellValue(map.get("ZW")!=null?map.get("ZW").toString():"");
				HSSFCell cell_6 = row.createCell(5);

				cell_6.setCellValue(map.get("zj")!=null?HBUtil.getCodeName("ZB148",map.get("ZJ").toString()):"");
				HSSFCell cell_7 = row.createCell(6); 
				cell_7.setCellValue(map.get("CXBC")!=null?map.get("CXBC").toString():"");
				HSSFCell cell_8 = row.createCell(7); 
				cell_8.setCellValue(map.get("TXDW")!=null?map.get("TXDW").toString():"");
				HSSFCell cell_9 = row.createCell(8); 
				cell_9.setCellValue(map.get("PXDD")!=null?map.get("PXDD").toString():"");
				HSSFCell cell_10 = row.createCell(9); 
				cell_10.setCellValue(map.get("PXSC")!=null?map.get("PXSC").toString():"");
				HSSFCell cell_11 = row.createCell(10); 
				cell_11.setCellValue(map.get("PXBX")!=null?map.get("PXBX").toString():"");
				i++;
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		String path = AppConfig.HZB_PATH + "/"+"train/outExec/";
		try {
			File file =new File(path);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		path = path+UUID.randomUUID().toString()+".xls";
		File file = new File(path);
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			workbook.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�ļ��������");
		return path;
	}
	
	
	
	public static String jbgz_execl(List<Map<String, Object>> list_data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style_title = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 20);
		// ����
		font.setFontName("����");
		style_title.setFont(font);
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_title.setBorderBottom(BorderStyle.THIN);
		style_title.setBorderLeft(BorderStyle.THIN);
		style_title.setBorderRight(BorderStyle.THIN);
		style_title.setBorderTop(BorderStyle.THIN);
		HSSFSheet sheet = workbook.createSheet("������ְ��");
		/*HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style_title);
		cell_00.setCellValue("��ѵ��¼��");*/
		
		HSSFCellStyle style_line_fix = workbook.createCellStyle();//�̶����� ����...
		HSSFFont font_line_fix = workbook.createFont();
		// ���������С
		font_line_fix.setFontHeightInPoints((short) 14);
		// ����
		font_line_fix.setFontName("����");
		font_line_fix.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ
		style_line_fix.setFont(font_line_fix);
		style_line_fix.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_line_fix.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_line_fix.setFont(font_line_fix);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0); 
		cell1.setCellStyle(style_line_fix);
		cell1.setCellValue("����");
		HSSFCell cell2 = row1.createCell(1); 
		cell2.setCellStyle(style_line_fix);
		cell2.setCellValue("�Ա�");
		HSSFCell cell3 = row1.createCell(2); 
		cell3.setCellStyle(style_line_fix);
		cell3.setCellValue("��������");
		HSSFCell cell5 = row1.createCell(4); 
		cell5.setCellStyle(style_line_fix);
		cell5.setCellValue("ְ��");
		HSSFCell cell6 = row1.createCell(5); 
		cell6.setCellStyle(style_line_fix);
		cell6.setCellValue("ְ��");
		HSSFCell cell4 = row1.createCell(3); 
		cell4.setCellStyle(style_line_fix);
		cell4.setCellValue("���ڴ���");
		
		HSSFCell cell7 = row1.createCell(6); 
		cell7.setCellStyle(style_line_fix);
		cell7.setCellValue("ѡ�ɵ�λ");
		HSSFCell cell8 = row1.createCell(7); 
		cell8.setCellStyle(style_line_fix);
		cell8.setCellValue("��ְ����");
		HSSFCell cell9 = row1.createCell(8); 
		cell9.setCellStyle(style_line_fix);
		cell9.setCellValue("��ְ����");
		HSSFCell cell10 = row1.createCell(9); 
		cell10.setCellStyle(style_line_fix);
		cell10.setCellValue("��ֹʱ��");
		HSSFCell cell11 = row1.createCell(10); 
		cell11.setCellStyle(style_line_fix);
		cell11.setCellValue("�������");
		HSSFCell cell12 = row1.createCell(11); 
		cell12.setCellStyle(style_line_fix);
		cell12.setCellValue("��������");
		HSSFCell cell13 = row1.createCell(12); 
		cell13.setCellStyle(style_line_fix);
		cell13.setCellValue("����ְ��");
		int i=1;
		//�������
		for(Map<String, Object> map:list_data){
			
			try {
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell_1 = row.createCell(0); 
				cell_1.setCellValue(map.get("XM")!=null?map.get("XM").toString():"");
				HSSFCell cell_2 = row.createCell(1); 
				cell_2.setCellValue(map.get("XB")!=null?HBUtil.getCodeName("GB2261",  map.get("XB").toString()):"");
				HSSFCell cell_3 = row.createCell(2); 
				cell_3.setCellValue(map.get("CSNY")!=null?map.get("CSNY").toString():"");
				HSSFCell cell_5 = row.createCell(4); 
				cell_5.setCellValue(map.get("ZW")!=null?map.get("ZW").toString():"");
				HSSFCell cell_6 = row.createCell(5); 
				cell_6.setCellValue(map.get("ZJ")!=null?HBUtil.getCodeName("ZB148",  map.get("ZJ").toString()):"");
				HSSFCell cell_4 = row.createCell(3); 
				cell_4.setCellValue(map.get("RZJG")!=null?map.get("RZJG").toString():"");
				
				HSSFCell cell_7 = row.createCell(6); 
				cell_7.setCellValue(map.get("XPDW")!=null?map.get("XPDW").toString():"");
				HSSFCell cell_8 = row.createCell(7); 
				cell_8.setCellValue(map.get("GZLX")!=null?map.get("GZLX").toString():"");
				HSSFCell cell_9 = row.createCell(8); 
				cell_9.setCellValue(map.get("GZRW")!=null?map.get("GZRW").toString():"");
				HSSFCell cell_10 = row.createCell(9); 
				cell_10.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_11 = row.createCell(10); 
				cell_11.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				HSSFCell cell_12 = row.createCell(11); 
				cell_12.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_13 = row.createCell(12); 
				cell_13.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				i++;
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		String path = AppConfig.HZB_PATH + "/"+"jbgz/outExec/";
		try {
			File file =new File(path);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		path = path+UUID.randomUUID().toString()+".xls";
		File file = new File(path);
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			workbook.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�ļ��������");
		return path;
	}
	public static String jbjd_execl(List<Map<String, Object>> list_data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style_title = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 20);
		// ����
		font.setFontName("����");
		style_title.setFont(font);
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_title.setBorderBottom(BorderStyle.THIN);
		style_title.setBorderLeft(BorderStyle.THIN);
		style_title.setBorderRight(BorderStyle.THIN);
		style_title.setBorderTop(BorderStyle.THIN);
		HSSFSheet sheet = workbook.createSheet("���������");
		/*HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style_title);
		cell_00.setCellValue("��ѵ��¼��");*/
		
		HSSFCellStyle style_line_fix = workbook.createCellStyle();//�̶����� ����...
		HSSFFont font_line_fix = workbook.createFont();
		// ���������С
		font_line_fix.setFontHeightInPoints((short) 14);
		// ����
		font_line_fix.setFontName("����");
		font_line_fix.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ
		style_line_fix.setFont(font_line_fix);
		style_line_fix.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_line_fix.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_line_fix.setFont(font_line_fix);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0); 
		cell1.setCellStyle(style_line_fix);
		cell1.setCellValue("����");
		HSSFCell cell2 = row1.createCell(1); 
		cell2.setCellStyle(style_line_fix);
		cell2.setCellValue("�Ա�");
		HSSFCell cell3 = row1.createCell(2); 
		cell3.setCellStyle(style_line_fix);
		cell3.setCellValue("��������");
		HSSFCell cell5 = row1.createCell(4); 
		cell5.setCellStyle(style_line_fix);
		cell5.setCellValue("ְ��");
		HSSFCell cell6 = row1.createCell(5); 
		cell6.setCellStyle(style_line_fix);
		cell6.setCellValue("ְ��");
		HSSFCell cell4 = row1.createCell(3); 
		cell4.setCellStyle(style_line_fix);
		cell4.setCellValue("���ڴ���");
		
		HSSFCell cell7 = row1.createCell(6); 
		cell7.setCellStyle(style_line_fix);
		cell7.setCellValue("ѡ�ɵ�λ");
		HSSFCell cell8 = row1.createCell(7); 
		cell8.setCellStyle(style_line_fix);
		cell8.setCellValue("�������");
		HSSFCell cell9 = row1.createCell(8); 
		cell9.setCellStyle(style_line_fix);
		cell9.setCellValue("�������");
		HSSFCell cell10 = row1.createCell(9); 
		cell10.setCellStyle(style_line_fix);
		cell10.setCellValue("��ֹʱ��");
		HSSFCell cell11 = row1.createCell(10); 
		cell11.setCellStyle(style_line_fix);
		cell11.setCellValue("�������");
		HSSFCell cell12 = row1.createCell(11); 
		cell12.setCellStyle(style_line_fix);
		cell12.setCellValue("��������");
		HSSFCell cell13 = row1.createCell(12); 
		cell13.setCellStyle(style_line_fix);
		cell13.setCellValue("���ְ��");
		int i=1;
		//�������
		for(Map<String, Object> map:list_data){
			
			try {
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell_1 = row.createCell(0); 
				cell_1.setCellValue(map.get("XM")!=null?map.get("XM").toString():"");
				HSSFCell cell_2 = row.createCell(1); 
				cell_2.setCellValue(map.get("XB")!=null?HBUtil.getCodeName("GB2261",  map.get("XB").toString()):"");
				HSSFCell cell_3 = row.createCell(2); 
				cell_3.setCellValue(map.get("CSNY")!=null?map.get("CSNY").toString():"");
				HSSFCell cell_5 = row.createCell(4); 
				cell_5.setCellValue(map.get("ZW")!=null?map.get("ZW").toString():"");
				HSSFCell cell_6 = row.createCell(5); 
				cell_6.setCellValue(map.get("ZJ")!=null?HBUtil.getCodeName("ZB148",  map.get("ZJ").toString()):"");
				HSSFCell cell_4 = row.createCell(3); 
				cell_4.setCellValue(map.get("RZJG")!=null?map.get("RZJG").toString():"");
				
				HSSFCell cell_7 = row.createCell(6); 
				cell_7.setCellValue(map.get("XPDW")!=null?map.get("XPDW").toString():"");
				HSSFCell cell_8 = row.createCell(7); 
				cell_8.setCellValue(map.get("GZLX")!=null?map.get("GZLX").toString():"");
				HSSFCell cell_9 = row.createCell(8); 
				cell_9.setCellValue(map.get("GZRW")!=null?map.get("GZRW").toString():"");
				HSSFCell cell_10 = row.createCell(9); 
				cell_10.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_11 = row.createCell(10); 
				cell_11.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				HSSFCell cell_12 = row.createCell(11); 
				cell_12.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_13 = row.createCell(12); 
				cell_13.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				i++;
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		String path = AppConfig.HZB_PATH + "/"+"jbgz/outExec/";
		try {
			File file =new File(path);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
				file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		path = path+UUID.randomUUID().toString()+".xls";
		File file = new File(path);
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			workbook.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�ļ��������");
		return path;
	}
	public static String wpgz_execl(List<Map<String, Object>> list_data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style_title = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 20);
		// ����
		font.setFontName("����");
		style_title.setFont(font);
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_title.setBorderBottom(BorderStyle.THIN);
		style_title.setBorderLeft(BorderStyle.THIN);
		style_title.setBorderRight(BorderStyle.THIN);
		style_title.setBorderTop(BorderStyle.THIN);
		HSSFSheet sheet = workbook.createSheet("���ɹ�ְ��");
		/*HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style_title);
		cell_00.setCellValue("��ѵ��¼��");*/

		HSSFCellStyle style_line_fix = workbook.createCellStyle();//�̶����� ����...
		HSSFFont font_line_fix = workbook.createFont();
		// ���������С
		font_line_fix.setFontHeightInPoints((short) 14);
		// ����
		font_line_fix.setFontName("����");
		font_line_fix.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ
		style_line_fix.setFont(font_line_fix);
		style_line_fix.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_line_fix.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_line_fix.setFont(font_line_fix);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0);
		cell1.setCellStyle(style_line_fix);
		cell1.setCellValue("����");
		HSSFCell cell2 = row1.createCell(1);
		cell2.setCellStyle(style_line_fix);
		cell2.setCellValue("�Ա�");
		HSSFCell cell3 = row1.createCell(2);
		cell3.setCellStyle(style_line_fix);
		cell3.setCellValue("��������");
		HSSFCell cell5 = row1.createCell(4);
		cell5.setCellStyle(style_line_fix);
		cell5.setCellValue("ְ��");
		HSSFCell cell6 = row1.createCell(5);
		cell6.setCellStyle(style_line_fix);
		cell6.setCellValue("ְ��");
		HSSFCell cell4 = row1.createCell(3);
		cell4.setCellStyle(style_line_fix);
		cell4.setCellValue("���ڴ���");

		HSSFCell cell7 = row1.createCell(6);
		cell7.setCellStyle(style_line_fix);
		cell7.setCellValue("ѡ�ɵ�λ");
		HSSFCell cell8 = row1.createCell(7);
		cell8.setCellStyle(style_line_fix);
		cell8.setCellValue("��ְ����");
		HSSFCell cell9 = row1.createCell(8);
		cell9.setCellStyle(style_line_fix);
		cell9.setCellValue("��ְ����");
		HSSFCell cell10 = row1.createCell(9);
		cell10.setCellStyle(style_line_fix);
		cell10.setCellValue("��ֹʱ��");
		HSSFCell cell11 = row1.createCell(10);
		cell11.setCellStyle(style_line_fix);
		cell11.setCellValue("�������");
		HSSFCell cell12 = row1.createCell(11);
		cell12.setCellStyle(style_line_fix);
		cell12.setCellValue("��������");
		HSSFCell cell13 = row1.createCell(12);
		cell13.setCellStyle(style_line_fix);
		cell13.setCellValue("����ְ��");
		int i=1;
		//�������
		for(Map<String, Object> map:list_data){

			try {
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell_1 = row.createCell(0);
				cell_1.setCellValue(map.get("XM")!=null?map.get("XM").toString():"");
				HSSFCell cell_2 = row.createCell(1);
				cell_2.setCellValue(map.get("XB")!=null?HBUtil.getCodeName("GB2261",  map.get("XB").toString()):"");
				HSSFCell cell_3 = row.createCell(2);
				cell_3.setCellValue(map.get("CSNY")!=null?map.get("CSNY").toString():"");
				HSSFCell cell_5 = row.createCell(4);
				cell_5.setCellValue(map.get("ZW")!=null?map.get("ZW").toString():"");
				HSSFCell cell_6 = row.createCell(5);
				cell_6.setCellValue(map.get("ZJ")!=null?HBUtil.getCodeName("ZB148",  map.get("ZJ").toString()):"");
				HSSFCell cell_4 = row.createCell(3);
				cell_4.setCellValue(map.get("RZJG")!=null?map.get("RZJG").toString():"");

				HSSFCell cell_7 = row.createCell(6);
				cell_7.setCellValue(map.get("XPDW")!=null?map.get("XPDW").toString():"");
				HSSFCell cell_8 = row.createCell(7);
				cell_8.setCellValue(map.get("GZLX")!=null?map.get("GZLX").toString():"");
				HSSFCell cell_9 = row.createCell(8);
				cell_9.setCellValue(map.get("GZRW")!=null?map.get("GZRW").toString():"");
				HSSFCell cell_10 = row.createCell(9);
				cell_10.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_11 = row.createCell(10);
				cell_11.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				HSSFCell cell_12 = row.createCell(11);
				cell_12.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_13 = row.createCell(12);
				cell_13.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				i++;
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}


		String path = AppConfig.HZB_PATH + "/"+"jbgz/outExec/";
		try {
			File file =new File(path);
			//����ļ��в������򴴽�
			if  (!file .exists()  && !file .isDirectory())
			{
				file .mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		path = path+UUID.randomUUID().toString()+".xls";
		File file = new File(path);
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			workbook.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�ļ��������");
		return path;
	}
	public static String wpjd_execl(List<Map<String, Object>> list_data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style_title = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 20);
		// ����
		font.setFontName("����");
		style_title.setFont(font);
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_title.setBorderBottom(BorderStyle.THIN);
		style_title.setBorderLeft(BorderStyle.THIN);
		style_title.setBorderRight(BorderStyle.THIN);
		style_title.setBorderTop(BorderStyle.THIN);
		HSSFSheet sheet = workbook.createSheet("���ɽ����");
		/*HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style_title);
		cell_00.setCellValue("��ѵ��¼��");*/
		
		HSSFCellStyle style_line_fix = workbook.createCellStyle();//�̶����� ����...
		HSSFFont font_line_fix = workbook.createFont();
		// ���������С
		font_line_fix.setFontHeightInPoints((short) 14);
		// ����
		font_line_fix.setFontName("����");
		font_line_fix.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ
		style_line_fix.setFont(font_line_fix);
		style_line_fix.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_line_fix.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_line_fix.setFont(font_line_fix);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0);
		cell1.setCellStyle(style_line_fix);
		cell1.setCellValue("����");
		HSSFCell cell2 = row1.createCell(1);
		cell2.setCellStyle(style_line_fix);
		cell2.setCellValue("�Ա�");
		HSSFCell cell3 = row1.createCell(2);
		cell3.setCellStyle(style_line_fix);
		cell3.setCellValue("��������");
		HSSFCell cell5 = row1.createCell(4);
		cell5.setCellStyle(style_line_fix);
		cell5.setCellValue("ְ��");
		HSSFCell cell6 = row1.createCell(5);
		cell6.setCellStyle(style_line_fix);
		cell6.setCellValue("ְ��");
		HSSFCell cell4 = row1.createCell(3);
		cell4.setCellStyle(style_line_fix);
		cell4.setCellValue("���ڴ���");
		
		HSSFCell cell7 = row1.createCell(6);
		cell7.setCellStyle(style_line_fix);
		cell7.setCellValue("ѡ�ɵ�λ");
		HSSFCell cell8 = row1.createCell(7);
		cell8.setCellStyle(style_line_fix);
		cell8.setCellValue("�������");
		HSSFCell cell9 = row1.createCell(8);
		cell9.setCellStyle(style_line_fix);
		cell9.setCellValue("�������");
		HSSFCell cell10 = row1.createCell(9);
		cell10.setCellStyle(style_line_fix);
		cell10.setCellValue("��ֹʱ��");
		HSSFCell cell11 = row1.createCell(10);
		cell11.setCellStyle(style_line_fix);
		cell11.setCellValue("�������");
		HSSFCell cell12 = row1.createCell(11);
		cell12.setCellStyle(style_line_fix);
		cell12.setCellValue("��������");
		HSSFCell cell13 = row1.createCell(12);
		cell13.setCellStyle(style_line_fix);
		cell13.setCellValue("���ְ��");
		int i=1;
		//�������
		for(Map<String, Object> map:list_data){
			
			try {
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell_1 = row.createCell(0);
				cell_1.setCellValue(map.get("XM")!=null?map.get("XM").toString():"");
				HSSFCell cell_2 = row.createCell(1);
				cell_2.setCellValue(map.get("XB")!=null?HBUtil.getCodeName("GB2261",  map.get("XB").toString()):"");
				HSSFCell cell_3 = row.createCell(2);
				cell_3.setCellValue(map.get("CSNY")!=null?map.get("CSNY").toString():"");
				HSSFCell cell_5 = row.createCell(4);
				cell_5.setCellValue(map.get("ZW")!=null?map.get("ZW").toString():"");
				HSSFCell cell_6 = row.createCell(5);
				cell_6.setCellValue(map.get("ZJ")!=null?HBUtil.getCodeName("ZB148",  map.get("ZJ").toString()):"");
				HSSFCell cell_4 = row.createCell(3);
				cell_4.setCellValue(map.get("RZJG")!=null?map.get("RZJG").toString():"");
				
				HSSFCell cell_7 = row.createCell(6);
				cell_7.setCellValue(map.get("XPDW")!=null?map.get("XPDW").toString():"");
				HSSFCell cell_8 = row.createCell(7);
				cell_8.setCellValue(map.get("GZLX")!=null?map.get("GZLX").toString():"");
				HSSFCell cell_9 = row.createCell(8);
				cell_9.setCellValue(map.get("GZRW")!=null?map.get("GZRW").toString():"");
				HSSFCell cell_10 = row.createCell(9);
				cell_10.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_11 = row.createCell(10);
				cell_11.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				HSSFCell cell_12 = row.createCell(11);
				cell_12.setCellValue(map.get("QZSJ")!=null?map.get("QZSJ").toString():"");
				HSSFCell cell_13 = row.createCell(12);
				cell_13.setCellValue(map.get("KHQK")!=null?map.get("KHQK").toString():"");
				i++;
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		String path = AppConfig.HZB_PATH + "/"+"jbgz/outExec/";
		try {
			File file =new File(path);
			//����ļ��в������򴴽�
			if  (!file .exists()  && !file .isDirectory())
			{
				file .mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		path = path+UUID.randomUUID().toString()+".xls";
		File file = new File(path);
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			workbook.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�ļ��������");
		return path;
	}
}