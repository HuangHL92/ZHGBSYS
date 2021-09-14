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
		//照片名册（每行4人）
		 Workbook wb = new HSSFWorkbook();
		 
			try {
				BASE64Decoder decode = new BASE64Decoder();
				List list = (List) dataMap.get("mc");
				// 创建HSSFWorkbook对象(excel的文档对象)
				// 建立新的sheet对象（excel的表单）
				Sheet sheet = wb.createSheet("照片名册");
				// 声明一个画图的顶级管理器
				HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
				// 隐藏网格线
				sheet.setDisplayGridlines(false);
				// 设置表格默认列宽度为15个字节
				sheet.setDefaultColumnWidth((short) 15);
				// 设置指定列宽度
				sheet.setColumnWidth(0, 1 * 256);
				sheet.setColumnWidth(2, 5 * 256);
				sheet.setColumnWidth(4, 5 * 256);
				sheet.setColumnWidth(6, 5 * 256);
				// 生成一个样式
				CellStyle style = wb.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_CENTER);
				// 生成一个字体
				Font font = wb.createFont();
				font.setFontHeightInPoints((short) 17);
				font.setFontName("宋体");
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				// 把字体应用到当前的样式
				style.setFont(font);
				// 生成另一个样式
				CellStyle Style2 = wb.createCellStyle();
				Style2.setWrapText(true);// 自动换行
				// 生成另一个字体
				Font font2 = wb.createFont();
				font2.setFontName("宋体");
				// 把字体应用到当前样式
				Style2.setFont(font2);
				// 生成另一个样式
				CellStyle Style3 = wb.createCellStyle();
				Style3.setWrapText(true);// 自动换行
				Style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
				Style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
				Style3.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
				Style3.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
				Style3.setBorderRight(CellStyle.BORDER_THIN);//右边框       
				Style3.setBorderTop(CellStyle.BORDER_THIN);//上边框 
				// 生成另一个字体
				Font font3 = wb.createFont();
				font3.setFontName("宋体");
				// 把字体应用到当前样式
				Style3.setFont(font3);
				// 生成另一个样式
				CellStyle Style4 = wb.createCellStyle();
				Style4.setWrapText(true);// 自动换行
				Style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
				Style4.setAlignment(CellStyle.ALIGN_LEFT);
				// 生成另一个字体
				Font font4 = wb.createFont();
				font4.setFontName("宋体");
				// 把字体应用到当前样式
				Style4.setFont(font4);
				int rowNum = 0;
				double allUse = 4;
				Row row1 = null;
				for (int i = 0; i < list.size(); i++) {
					HashMap<String, Object> map = (HashMap<String, Object>) list
							.get(i);
					String tags = map.get("tag").toString();
					// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
					row1 = sheet.createRow(rowNum);
					// 设置创建单元格高度
					row1.setHeightInPoints((short) 22);
					// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
					Cell cell = row1.createCell(0);
					// 设置单元格内容
					cell.setCellValue(tags);
					// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
					cell.setCellStyle(style);
					List<Map<String, String>> groupList = (List<Map<String, String>>) map
							.get("datas");
					double ListSize = groupList.size();// 总体数据量
					int row = (int) Math.ceil(ListSize / allUse) * 8;// 人员数据所占行数
					int forNum = (int) Math.ceil(ListSize / allUse);// 所需循环次数

					for (int j = 0; j < forNum; j++) {
						//将人员信息放入4个map中，方便数据的写入
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
						// 空一行
						Row row3 = null;
						row3 = sheet.createRow(rowNum + 1);
						row3.setHeightInPoints((short) 3);
						for (int z = 1; z <= 8; z++) {
							int creatRow = j * 8 + z + rowNum + 1;// 人员详细信息开始写入行数
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
								// 设置行高
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
									String value1 = "照   片";
									cell2.setCellValue(value1);
									cell2.setCellStyle(Style3);// 设置单元格为自动换行
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
										String value2 = "照   片";
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
										String value3 = "照   片";
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
										String value4 = "照   片";
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
							}else if(z == 4){//拼接任职信息
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1.substring(0, 4)+"."+value1.substring(4, 6)+"任职";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// 设置单元格为自动换行
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2.substring(0, 4)+"."+value2.substring(4, 6)+"任职";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3.substring(0, 4)+"."+value3.substring(4, 6)+"任职";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4.substring(0, 4)+"."+value4.substring(4, 6)+"任职";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 5){//拼接生日信息
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1.substring(0, 4)+"."+value1.substring(4, 6)+"生";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// 设置单元格为自动换行
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2.substring(0, 4)+"."+value2.substring(4, 6)+"生";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3.substring(0, 4)+"."+value3.substring(4, 6)+"生";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4.substring(0, 4)+"."+value4.substring(4, 6)+"生";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 6){//拼接籍贯信息
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1+"人";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// 设置单元格为自动换行
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2+"人";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3+"人";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4+"人";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 8){//拼接参加工作时间信息
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								if(!StringUtil.isEmpty(value1)){
									value1 = value1.substring(0, 4)+"."+value1.substring(4, 6)+"参加工作";
								}
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style2);// 设置单元格为自动换行
								if (map2 != null) {
									Cell cell3 = row2.createCell(3);
									String value2 = map2.get(ObjectName);
									if(!StringUtil.isEmpty(value2)){
										value2 = value2.substring(0, 4)+"."+value2.substring(4, 6)+"参加工作";
									}
									cell3.setCellValue(value2);
									cell3.setCellStyle(Style2);
								}
								if (map3 != null) {
									Cell cell4 = row2.createCell(5);
									String value3 = map3.get(ObjectName);
									if(!StringUtil.isEmpty(value3)){
										value3 = value3.substring(0, 4)+"."+value3.substring(4, 6)+"参加工作";
									}
									cell4.setCellValue(value3);
									cell4.setCellStyle(Style2);
								}
								if (map4 != null) {
									Cell cell5 = row2.createCell(7);
									String value4 = map4.get(ObjectName);
									if(!StringUtil.isEmpty(value4)){
										value4 = value4.substring(0, 4)+"."+value4.substring(4, 6)+"参加工作";
									}
									cell5.setCellValue(value4);
									cell5.setCellStyle(Style2);
								}
							}else if(z == 3){
								Cell cell2 = row2.createCell(1);
								String value1 = map1.get(ObjectName);
								cell2.setCellValue(value1);
								cell2.setCellStyle(Style4);// 设置单元格为自动换行
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
								cell2.setCellStyle(Style2);// 设置单元格为自动换行
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
					rowNum = rowNum + row + 2;// 下一单位名字起始行数
				}
               
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return wb;
		}
	
	private Workbook exportExcel2(Map<String, Object> dataMap) {
		//照片名册每行一人
		Workbook wb = new HSSFWorkbook();
		try {
			BASE64Decoder decode = new BASE64Decoder();
			List list = (List) dataMap.get("mc");
			// 创建HSSFWorkbook对象(excel的文档对象)
			// 建立新的sheet对象（excel的表单）
			Sheet sheet = wb.createSheet("照片名册");
			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			// 隐藏网格线
			sheet.setDisplayGridlines(false);
			// 设置表格默认列宽度为17个字节
			sheet.setDefaultColumnWidth((short) 17);
			// 设置指定列宽度
			sheet.setColumnWidth(0, 0 * 256);
			sheet.setColumnWidth(3, 27 * 256);
			sheet.setColumnWidth(4, 21 * 256);
			// 生成一个样式
			CellStyle style = wb.createCellStyle();//标题样式
			style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			// 生成一个字体
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 17);
			font.setFontName("宋体");
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);//字体加粗
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成另一个样式
			CellStyle Style2 = wb.createCellStyle();
			Style2.setWrapText(true);// 自动换行
			Style2.setVerticalAlignment(CellStyle.VERTICAL_TOP);//顶端对齐
			// 生成另一个字体
			Font font2 = wb.createFont();
			font2.setFontHeightInPoints((short) 12);//字号
			font2.setFontName("宋体");
			font2.setBoldweight(Font.BOLDWEIGHT_BOLD);//字体加粗
			// 把字体应用到当前样式
			Style2.setFont(font2);
			// 生成另一个样式
			CellStyle Style3 = wb.createCellStyle();
			Style3.setWrapText(true);// 自动换行
			Style3.setVerticalAlignment(CellStyle.VERTICAL_TOP);//顶端对齐
			Style3.setAlignment(CellStyle.ALIGN_LEFT);//左对齐
			// 生成另一个字体
			Font font3 = wb.createFont();
			font3.setFontHeightInPoints((short) 12);//字号
			font3.setFontName("宋体");
			// 把字体应用到当前样式
			Style3.setFont(font3);
			// 生成另一个样式
			CellStyle Style4 = wb.createCellStyle();
			Style4.setWrapText(true);// 自动换行
			Style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			Style4.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
			Style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
			Style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
			Style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
			Style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
			// 生成另一个字体
			Font font4 = wb.createFont();
			font4.setFontName("宋体");
			// 把字体应用到当前样式
			Style4.setFont(font4);   
			int rowNum = 0;//标题写入行数
			int allPersonNum = 0;//记录总共人数
			//double allUse = 4;
			Row row1 = null;
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) list
						.get(i);
				String tags = map.get("tag").toString();
				// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
				row1 = sheet.createRow(rowNum);
				// 设置创建单元格高度
				row1.setHeightInPoints((short) 22);
				// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
				Cell cell = row1.createCell(0);
				// 设置单元格内容
				cell.setCellValue(tags);
				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
				cell.setCellStyle(style);
				List<Map<String, String>> groupList = (List<Map<String, String>>) map
						.get("datas");
				int row = groupList.size() * 4;// 人员数据所占行数
				for (int j = 0; j < groupList.size(); j++) {
					allPersonNum++;
					HashMap<String, String> map1 = (HashMap<String, String>) groupList.get(j);
						// 空一行
						Row row2 = null;
						row2 = sheet.createRow(rowNum + j*4 + 1);
						row2.setHeightInPoints((short) 10);
						//详细信息写入行数
						Row row3 = null;
						row3 = sheet.createRow(rowNum + j*4 + 2);
						// 设置行高
						row3.setHeightInPoints((short) 50);
						byte[] bsValue1 = decode.decodeBuffer(map1.get(
								"image").toString());
						if(bsValue1.length<2){
							Cell cell2 = row3.createCell(1);
							cell2.setCellValue("照    片");
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
						// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
						CellRangeAddress cra1=new CellRangeAddress(rowNum + j*4 + 2, rowNum + j*4 + 3, 1, 1); 
						sheet.addMergedRegion(cra1);
						CellRangeAddress cra3=new CellRangeAddress(rowNum + j*4 + 2, rowNum + j*4 + 2, 2, 3); 
						sheet.addMergedRegion(cra3);
						//详细信息写入行数
						Row row4 = null;
						row4 = sheet.createRow(rowNum + j*4 + 3);
						// 设置行高
						row4.setHeightInPoints((short) 70);
						Cell cell5 = row4.createCell(2);
						Cell cell6 = row4.createCell(1);
						cell6.setCellStyle(Style4);
						String personMessage = map1.get("a0111a")+map1.get("a0107")+map1.get("qrzxl")+map1.get("a0144")+map1.get("a0134")+map1.get("a0243");
						cell5.setCellValue(personMessage);
						cell5.setCellStyle(Style3);
						// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
						CellRangeAddress cra2=new CellRangeAddress(rowNum + j*4 + 3, rowNum + j*4 + 3, 2, 4); 
						sheet.addMergedRegion(cra2);
						// 空一行
						Row row5 = null;
						row5 = sheet.createRow(rowNum + j*4 + 4);
						row5.setHeightInPoints((short) 10);
						if(allPersonNum%4 == 0){//分页符插入，打印用，每页放4人
							sheet.setRowBreak(rowNum + j*4 + 4);
						}
					}
				
				rowNum = rowNum + row + 1;// 下一单位名字起始行数
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public Workbook exportTjExcel(List<HashMap<String, Object>> list ) {
		Workbook wb = new HSSFWorkbook();
		//建立新的sheet对象（excel的表单）  
		Sheet sheet=wb.createSheet("统计数据");  
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
		Row row1=sheet.createRow(0);  
		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个  
		for(int i=0;i<list.size();i++){
			//设置单元格内容  
			row1.createCell(i).setCellValue(list.get(i).get("name").toString());
		}
		//在sheet里创建第二行  
		Row row2=sheet.createRow(1); 
		for(int i=0;i<list.size();i++){
			//设置单元格内容  
			row2.createCell(i).setCellValue(list.get(i).get("number").toString());
		}
		return wb;
		
	}
	
	public Workbook exportEwTjExcel(HashMap<String, String> map) {
		Workbook wb = new HSSFWorkbook();
		//建立新的sheet对象（excel的表单）  
		Sheet sheet=wb.createSheet("统计数据");  
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  		
		Row row0=sheet.createRow(0);  
		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个  
		for(int i=0;i<Integer.parseInt(map.get("tran_num"));i++){
			//设置单元格内容  
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
