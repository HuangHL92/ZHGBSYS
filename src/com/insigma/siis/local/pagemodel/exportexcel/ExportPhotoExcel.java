package com.insigma.siis.local.pagemodel.exportexcel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hsqldb.lib.StringUtil;

import sun.misc.BASE64Decoder;

public class ExportPhotoExcel {
	
	public Workbook export(Map<String, Object> dataMap,String tempType){
		Workbook wb = null;
		if(tempType.equals("6")){
			wb = exportExcel(dataMap);
		}else{
			wb = exportExcel2(dataMap);
		}
		return wb;
	}
	
	private Workbook exportExcel(Map<String, Object> dataMap) {
		//��Ƭ���ᣨÿ��4�ˣ�
		 Workbook wb = new HSSFWorkbook();
		 
			try {
				BASE64Decoder decode = new BASE64Decoder();
				List list = (List) dataMap.get("mc");
				// ����HSSFWorkbook����(excel���ĵ�����)
				// �����µ�sheet����excel�ı���
				Sheet sheet = wb.createSheet("��Ƭ����");
				// ����һ����ͼ�Ķ���������
				HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
				// ����������
				sheet.setDisplayGridlines(false);
				// ���ñ��Ĭ���п��Ϊ15���ֽ�
				sheet.setDefaultColumnWidth((short) 15);
				// ����ָ���п��
				sheet.setColumnWidth(0, 1 * 256);
				sheet.setColumnWidth(2, 5 * 256);
				sheet.setColumnWidth(4, 5 * 256);
				sheet.setColumnWidth(6, 5 * 256);
				// ����һ����ʽ
				CellStyle style = wb.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_CENTER);
				// ����һ������
				Font font = wb.createFont();
				font.setFontHeightInPoints((short) 17);
				font.setFontName("����");
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				// ������Ӧ�õ���ǰ����ʽ
				style.setFont(font);
				// ������һ����ʽ
				CellStyle Style2 = wb.createCellStyle();
				Style2.setWrapText(true);// �Զ�����
				// ������һ������
				Font font2 = wb.createFont();
				font2.setFontName("����");
				// ������Ӧ�õ���ǰ��ʽ
				Style2.setFont(font2);
				// ������һ����ʽ
				CellStyle Style3 = wb.createCellStyle();
				Style3.setWrapText(true);// �Զ�����
				Style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//��ֱ����
				Style3.setAlignment(CellStyle.ALIGN_CENTER);//ˮƽ����
				Style3.setBorderBottom(CellStyle.BORDER_THIN);//�±߿�       
				Style3.setBorderLeft(CellStyle.BORDER_THIN);//��߿�       
				Style3.setBorderRight(CellStyle.BORDER_THIN);//�ұ߿�       
				Style3.setBorderTop(CellStyle.BORDER_THIN);//�ϱ߿� 
				// ������һ������
				Font font3 = wb.createFont();
				font3.setFontName("����");
				// ������Ӧ�õ���ǰ��ʽ
				Style3.setFont(font3);
				// ������һ����ʽ
				CellStyle Style4 = wb.createCellStyle();
				Style4.setWrapText(true);// �Զ�����
				Style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//��ֱ����
				Style4.setAlignment(CellStyle.ALIGN_LEFT);
				// ������һ������
				Font font4 = wb.createFont();
				font4.setFontName("����");
				// ������Ӧ�õ���ǰ��ʽ
				Style4.setFont(font4);
				int rowNum = 0;
				double allUse = 4;
				Row row1 = null;
				for (int i = 0; i < list.size(); i++) {
					HashMap<String, Object> map = (HashMap<String, Object>) list
							.get(i);
					String tags = map.get("tag").toString();
					// ��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
					row1 = sheet.createRow(rowNum);
					// ���ô�����Ԫ��߶�
					row1.setHeightInPoints((short) 22);
					// ������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
					Cell cell = row1.createCell(0);
					// ���õ�Ԫ������
					cell.setCellValue(tags);
					// �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
					cell.setCellStyle(style);
					List<Map<String, String>> groupList = (List<Map<String, String>>) map
							.get("datas");
					double ListSize = groupList.size();// ����������
					int row = (int) Math.ceil(ListSize / allUse) * 8;// ��Ա������ռ����
					int forNum = (int) Math.ceil(ListSize / allUse);// ����ѭ������

					for (int j = 0; j < forNum; j++) {
						//����Ա��Ϣ����4��map�У��������ݵ�д��
						HashMap<String, String> map1 = null;
						HashMap<String, String> map2 = null;
						HashMap<String, String> map3 = null;
						HashMap<String, String> map4 = null;
						map1 = (HashMap<String, String>) groupList.get(j * 4);
						if (j * 4 + 1 < ListSize) {
							map2 = (HashMap<String, String>) groupList
									.get(j * 4 + 1);
						}
						if (j * 4 + 2 < ListSize) {
							map3 = (HashMap<String, String>) groupList
									.get(j * 4 + 2);
						}
						if (j * 4 + 3 < ListSize) {
							map4 = (HashMap<String, String>) groupList
									.get(j * 4 + 3);
						}
						// ��һ��
						Row row3 = null;
						row3 = sheet.createRow(rowNum + 1);
						row3.setHeightInPoints((short) 3);
						for (int z = 1; z <= 8; z++) {
							int creatRow = j * 8 + z + rowNum + 1;// ��Ա��ϸ��Ϣ��ʼд������
							Row row2 = null;

							String ObjectName = "";
							if (z == 1) {
								ObjectName = "image";
							}
							if (z == 2) {
								ObjectName = "a0101";
							}
							if (z == 3) {
								ObjectName = "a0192";
							}
							if (z == 4) {
								ObjectName = "a0243";
							}
							if (z == 5) {
								ObjectName = "a0107";
							}
							if (z == 6) {
								ObjectName = "a0111a";
							}
							if (z == 7) {
								ObjectName = "qrzxl";
							}
							if (z == 8) {
								ObjectName = "a0134";
							}

							row2 = sheet.createRow(creatRow);
							if (z == 1) {
								// �����и�
								row2.setHeightInPoints((short) 100);
							}
							if (z == 2) {
								row2.setHeightInPoints((short) 20);
							}
							if (z == 3) {
								row2.setHeightInPoints((short) 40);
							}
							if (z == 7) {
								row2.setHeightInPoints((short) 20);
							}
							if (z == 1) {
								byte[] bsValue1 = decode.decodeBuffer(map1.get(
										ObjectName).toString());
								if(bsValue1.length<2){
									Cell cell2 = row2.createCell(1);
									String value1 = "��   Ƭ";
									cell2.setCellValue(value1);
									cell2.setCellStyle(Style3);// ���õ�Ԫ��Ϊ�Զ�����
								}else{
									HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0,
											1023, 255, (short) 1, creatRow, (short) 1, creatRow);
									anchor1.setAnchorType(2);
									patriarch.createPicture(anchor1, wb.addPicture(
											bsValue1, Workbook.PICTURE_TYPE_JPEG));
								}
								
								
								if(map2!=null){
									byte[] bsValue2 = decode.decodeBuffer(map2.get(
											ObjectName).toString());
									if(bsValue2.length<2){
										Cell cell3 = row2.createCell(3);
										String value2 = "��   Ƭ";
										cell3.setCellValue(value2);
										cell3.setCellStyle(Style3);
									}else{
										HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0,
												1023, 255, (short) 3, creatRow, (short) 3, creatRow);
										anchor2.setAnchorType(2);
										patriarch.createPicture(anchor2, wb.addPicture(
												bsValue2, Workbook.PICTURE_TYPE_JPEG));
									}

								}
								if(map3!=null){
									byte[] bsValue3 = decode.decodeBuffer(map3.get(
											ObjectName).toString());
									if(bsValue3.length<2){
										Cell cell4 = row2.createCell(5);
										String value3 = "��   Ƭ";
										cell4.setCellValue(value3);
										cell4.setCellStyle(Style3);
									}else{
										HSSFClientAnchor anchor3 = new HSSFClientAnchor(0, 0,
												1023, 255, (short) 5, creatRow, (short) 5, creatRow);
										anchor3.setAnchorType(2);
										patriarch.createPicture(anchor3, wb.addPicture(
												bsValue3, Workbook.PICTURE_TYPE_JPEG));
									}

								}
								if(map4!=null){
									byte[] bsValue4 = decode.decodeBuffer(map4.get(
											ObjectName).toString());
									if(bsValue4.length<2){
										Cell cell5 = row2.createCell(7);
										String value4 = "��   Ƭ";
										cell5.setCellValue(value4);
										cell5.setCellStyle(Style3);
									}else{
										HSSFClientAnchor anchor4 = new HSSFClientAnchor(0, 0,
												1023, 255, (short) 7, creatRow, (short) 7, creatRow);
										anchor4.setAnchorType(2);
										patriarch.createPicture(anchor4, wb.addPicture(
												bsValue4, Workbook.PICTURE_TYPE_JPEG));
									}

								}
							}else if(z == 4){//ƴ����ְ��Ϣ
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1.substring(0, 4)+"."+value1.substring(4, 6)+"��ְ";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// ���õ�Ԫ��Ϊ�Զ�����
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2.substring(0, 4)+"."+value2.substring(4, 6)+"��ְ";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3.substring(0, 4)+"."+value3.substring(4, 6)+"��ְ";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4.substring(0, 4)+"."+value4.substring(4, 6)+"��ְ";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 5){//ƴ��������Ϣ
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1.substring(0, 4)+"."+value1.substring(4, 6)+"��";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// ���õ�Ԫ��Ϊ�Զ�����
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2.substring(0, 4)+"."+value2.substring(4, 6)+"��";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3.substring(0, 4)+"."+value3.substring(4, 6)+"��";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4.substring(0, 4)+"."+value4.substring(4, 6)+"��";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 6){//ƴ�Ӽ�����Ϣ
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1+"��";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// ���õ�Ԫ��Ϊ�Զ�����
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2+"��";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3+"��";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4+"��";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 8){//ƴ�Ӳμӹ���ʱ����Ϣ
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1.substring(0, 4)+"."+value1.substring(4, 6)+"�μӹ���";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// ���õ�Ԫ��Ϊ�Զ�����
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2.substring(0, 4)+"."+value2.substring(4, 6)+"�μӹ���";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3.substring(0, 4)+"."+value3.substring(4, 6)+"�μӹ���";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4.substring(0, 4)+"."+value4.substring(4, 6)+"�μӹ���";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 3){
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style4);// ���õ�Ԫ��Ϊ�Զ�����
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style4);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style4);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style4);
								}
							}else{
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// ���õ�Ԫ��Ϊ�Զ�����
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}
						}
					}
					rowNum = rowNum + row + 2;// ��һ��λ������ʼ����
				}
               
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return wb;
		}
	
	private Workbook exportExcel2(Map<String, Object> dataMap) {
		//��Ƭ����ÿ��һ��
		Workbook wb = new HSSFWorkbook();
		try {
			BASE64Decoder decode = new BASE64Decoder();
			List list = (List) dataMap.get("mc");
			// ����HSSFWorkbook����(excel���ĵ�����)
			// �����µ�sheet����excel�ı���
			Sheet sheet = wb.createSheet("��Ƭ����");
			// ����һ����ͼ�Ķ���������
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			// ����������
			sheet.setDisplayGridlines(false);
			// ���ñ��Ĭ���п��Ϊ17���ֽ�
			sheet.setDefaultColumnWidth((short) 17);
			// ����ָ���п��
			sheet.setColumnWidth(0, 0 * 256);
			sheet.setColumnWidth(3, 27 * 256);
			sheet.setColumnWidth(4, 21 * 256);
			// ����һ����ʽ
			CellStyle style = wb.createCellStyle();//������ʽ
			style.setAlignment(CellStyle.ALIGN_CENTER);//ˮƽ����
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//��ֱ����
			// ����һ������
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 17);
			font.setFontName("����");
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);//����Ӵ�
			// ������Ӧ�õ���ǰ����ʽ
			style.setFont(font);
			// ������һ����ʽ
			CellStyle Style2 = wb.createCellStyle();
			Style2.setWrapText(true);// �Զ�����
			Style2.setVerticalAlignment(CellStyle.VERTICAL_TOP);//���˶���
			// ������һ������
			Font font2 = wb.createFont();
			font2.setFontHeightInPoints((short) 12);//�ֺ�
			font2.setFontName("����");
			font2.setBoldweight(Font.BOLDWEIGHT_BOLD);//����Ӵ�
			// ������Ӧ�õ���ǰ��ʽ
			Style2.setFont(font2);
			// ������һ����ʽ
			CellStyle Style3 = wb.createCellStyle();
			Style3.setWrapText(true);// �Զ�����
			Style3.setVerticalAlignment(CellStyle.VERTICAL_TOP);//���˶���
			Style3.setAlignment(CellStyle.ALIGN_LEFT);//�����
			// ������һ������
			Font font3 = wb.createFont();
			font3.setFontHeightInPoints((short) 12);//�ֺ�
			font3.setFontName("����");
			// ������Ӧ�õ���ǰ��ʽ
			Style3.setFont(font3);
			// ������һ����ʽ
			CellStyle Style4 = wb.createCellStyle();
			Style4.setWrapText(true);// �Զ�����
			Style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//��ֱ����
			Style4.setAlignment(CellStyle.ALIGN_CENTER);//ˮƽ����
			Style4.setBorderBottom(CellStyle.BORDER_THIN);//�±߿�       
			Style4.setBorderLeft(CellStyle.BORDER_THIN);//��߿�       
			Style4.setBorderRight(CellStyle.BORDER_THIN);//�ұ߿�       
			Style4.setBorderTop(CellStyle.BORDER_THIN);//�ϱ߿� 
			// ������һ������
			Font font4 = wb.createFont();
			font4.setFontName("����");
			// ������Ӧ�õ���ǰ��ʽ
			Style4.setFont(font4);   
			int rowNum = 0;//����д������
			int allPersonNum = 0;//��¼�ܹ�����
			//double allUse = 4;
			Row row1 = null;
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) list
						.get(i);
				String tags = map.get("tag").toString();
				// ��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
				row1 = sheet.createRow(rowNum);
				// ���ô�����Ԫ��߶�
				row1.setHeightInPoints((short) 22);
				// ������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
				Cell cell = row1.createCell(0);
				// ���õ�Ԫ������
				cell.setCellValue(tags);
				// �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
				cell.setCellStyle(style);
				List<Map<String, String>> groupList = (List<Map<String, String>>) map
						.get("datas");
				int row = groupList.size() * 4;// ��Ա������ռ����
				for (int j = 0; j < groupList.size(); j++) {
					allPersonNum++;
					HashMap<String, String> map1 = (HashMap<String, String>) groupList.get(j);
						// ��һ��
						Row row2 = null;
						row2 = sheet.createRow(rowNum + j*4 + 1);
						row2.setHeightInPoints((short) 10);
						//��ϸ��Ϣд������
						Row row3 = null;
						row3 = sheet.createRow(rowNum + j*4 + 2);
						// �����и�
						row3.setHeightInPoints((short) 50);
						byte[] bsValue1 = decode.decodeBuffer(map1.get(
								"image").toString());
						if(bsValue1.length<2){
							Cell cell2 = row3.createCell(1);
							cell2.setCellValue("��    Ƭ");
							cell2.setCellStyle(Style4);
						}else{
							HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0,
									1023, 255, (short) 1, rowNum + j*4 + 2, (short) 1, rowNum + j*4 + 3);
							anchor1.setAnchorType(2);
							patriarch.createPicture(anchor1, wb.addPicture(
									bsValue1, HSSFWorkbook.PICTURE_TYPE_JPEG));
						}
						Cell cell3 = row3.createCell(2);
						String a0192 = map1.get("a0192");
						cell3.setCellValue(a0192);
						cell3.setCellStyle(Style2);
						Cell cell4 = row3.createCell(4);
						String a0101 = map1.get("a0101");
						cell4.setCellValue(a0101);
						cell4.setCellStyle(Style2);
						// �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
						CellRangeAddress cra1=new CellRangeAddress(rowNum + j*4 + 2, rowNum + j*4 + 3, 1, 1); 
						sheet.addMergedRegion(cra1);
						CellRangeAddress cra3=new CellRangeAddress(rowNum + j*4 + 2, rowNum + j*4 + 2, 2, 3); 
						sheet.addMergedRegion(cra3);
						//��ϸ��Ϣд������
						Row row4 = null;
						row4 = sheet.createRow(rowNum + j*4 + 3);
						// �����и�
						row4.setHeightInPoints((short) 70);
						Cell cell5 = row4.createCell(2);
						Cell cell6 = row4.createCell(1);
						cell6.setCellStyle(Style4);
						String personMessage = map1.get("a0111a")+map1.get("a0107")+map1.get("qrzxl")+map1.get("a0144")+map1.get("a0134")+map1.get("a0243");
						cell5.setCellValue(personMessage);
						cell5.setCellStyle(Style3);
						// �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
						CellRangeAddress cra2=new CellRangeAddress(rowNum + j*4 + 3, rowNum + j*4 + 3, 2, 4); 
						sheet.addMergedRegion(cra2);
						// ��һ��
						Row row5 = null;
						row5 = sheet.createRow(rowNum + j*4 + 4);
						row5.setHeightInPoints((short) 10);
						if(allPersonNum%4 == 0){//��ҳ�����룬��ӡ�ã�ÿҳ��4��
							sheet.setRowBreak(rowNum + j*4 + 4);
						}
					}
				
				rowNum = rowNum + row + 1;// ��һ��λ������ʼ����
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public Workbook exportTjExcel(List<HashMap<String, Object>> list ) {
		Workbook wb = new HSSFWorkbook();
		//�����µ�sheet����excel�ı���  
		Sheet sheet=wb.createSheet("ͳ������");  
		//��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��  
		Row row1=sheet.createRow(0);  
		//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��  
		for(int i=0;i<list.size();i++){
			//���õ�Ԫ������  
			row1.createCell(i).setCellValue(list.get(i).get("name").toString());
		}
		//��sheet�ﴴ���ڶ���  
		Row row2=sheet.createRow(1); 
		for(int i=0;i<list.size();i++){
			//���õ�Ԫ������  
			row2.createCell(i).setCellValue(list.get(i).get("number").toString());
		}
		return wb;
		
	}
	
	public Workbook exportEwTjExcel(HashMap<String, String> map) {
		Workbook wb = new HSSFWorkbook();
		//�����µ�sheet����excel�ı���  
		Sheet sheet=wb.createSheet("ͳ������");  
		//��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��  		
		Row row0=sheet.createRow(0);  
		//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��  
		for(int i=0;i<Integer.parseInt(map.get("tran_num"));i++){
			//���õ�Ԫ������  
			row0.createCell(i+1).setCellValue(map.get("tran_name"+i).toString());
		}
		for(int i=0;i<Integer.parseInt(map.get("vert_num"));i++){
			Row row=sheet.createRow(i+1);
			row.createCell(0).setCellValue(map.get("vert_name"+i).toString());
			for(int j=0;j<Integer.parseInt(map.get("tran_num"));j++){
				row.createCell(j+1).setCellValue(map.get("vert"+i+"_tran"+j).toString());
			}
		}
		return wb;
		
	}

}
