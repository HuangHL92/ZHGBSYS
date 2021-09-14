package com.insigma.siis.local.pagemodel.zwzc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	public ReadExcel() {

	}


	private static final long serialVersionUID = -8344971443770122206L;

	/**
	 * 读取 Excel 显示页面.
	 * 
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public String read(InputStream in,String xlsx) throws Exception {
		Sheet sheet = null;
		String οnmοuseοver = "";
		StringBuilder lsb = new StringBuilder();
		Workbook workbook = null;
		try {
			if("xlsx".equals(xlsx)) {
				workbook = new XSSFWorkbook(in); // 获整个Excel
			}else {
				workbook = new HSSFWorkbook(in); // 获整个Excel
			}
				
			int sheetIndex = 0;
			//for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				sheet = workbook.getSheetAt(sheetIndex);// 获所有的sheet
				String sheetName = workbook.getSheetName(sheetIndex); // sheetName
				if (workbook.getSheetAt(sheetIndex) != null) {
					sheet = workbook.getSheetAt(sheetIndex);// 获得不为空的这个sheet
					if (sheet != null) {
						int firstRowNum = sheet.getFirstRowNum(); // 第一行
						int lastRowNum = sheet.getLastRowNum(); // 最后一行
						// 构造Table
						lsb.append(
								"<table  width=\"100%\" style=\"border:1px solid #000;border-width:1px 0 0 1px;margin:2px 0 2px 0;border-collapse:collapse;\">");
						for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
							if (sheet.getRow(rowNum) != null) {// 如果行不为空，
								Row row = sheet.getRow(rowNum);
								short firstCellNum = row.getFirstCellNum(); // 该行的第一个单元格
								if(firstCellNum<0){
									continue;
								}
								short lastCellNum = row.getLastCellNum(); // 该行的最后一个单元格
								int height = (int) (row.getHeight() / 12.625); // 行的高度
								lsb.append("<tr height=\"" + height
										+ "\" style=\"border:1px solid #000;border-width:0 1px 1px 0;margin:2px 0 2px 0;\">");
								
								int colspanCount = 0;
								int rowspanCount = 0;
								for (short cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) { // 循环该行的每一个单元格
									Cell cell = row.getCell(cellNum);
									int cellReginCol = getMergerCellRegionCol(sheet, rowNum, cellNum); // 合并的列（solspan）
									colspanCount = cellReginCol;
									int cellReginRow = getMergerCellRegionRow(sheet, rowNum, cellNum);// 合并的行（rowspan）
									if (cell != null) {
										if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
											if(colspanCount>0){
												colspanCount--;
											 }else{
												lsb.append("<td style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0; \">-</td>");

											 }
											//continue;
										} else {
											Object value = getCellValue(cell);
											if (value.toString().length() > 6) {
												οnmοuseοver = "οnmοuseοver='this.title=this.innerText' ";
											} else {
												οnmοuseοver = "";
											}
											StringBuffer tdStyle = new StringBuffer("<td " + οnmοuseοver
													+ "style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0; ");
											CellStyle cellStyle = cell.getCellStyle();
											

										
											
											lsb.append(tdStyle + "\"");

											int width = 50; //
											
											String align = convertAlignToHtml(cellStyle.getAlignment()); //
											String vAlign = convertVerticalAlignToHtml(
													cellStyle.getVerticalAlignment());

											lsb.append(" align=\"" + align + "\" valign=\"" + vAlign + "\" width=\""
													+ (cellReginCol == 0 ? width : width * cellReginCol) + "\" ");
											lsb.append(" colspan=\"" + (cellReginCol==0?"1":cellReginCol) + "\" rowspan=\"" + (cellReginRow==0?"1":cellReginRow)
													+ "\"");
											lsb.append(">" + value + "</td>");
											// System.out.println("1");
										}
									}else{
										 if(colspanCount>0){
											colspanCount--;
										 }else{
											lsb.append("<td style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0; \">-</td>");

										 }
									}
								}
								lsb.append("</tr>");
							}
						}
					}
				}
			//}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lsb.append("</table>");
		// System.out.println(lsb);
		return lsb.toString();
	}

	/**
	 * 取得单元格的值
	 * 
	 * @param cell
	 * @return
	 * @throws IOException
	 */
	private static Object getCellValue(Cell cell) throws IOException {
		// System.out.println("1");
		Object value = "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getRichStringCellValue().toString();
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			
			cell.setCellType(CellType.STRING);
			value = String.valueOf(cell.getStringCellValue());
			if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串

				value = cell.getRichStringCellValue().toString();
			}
			// value= cell.getCellFormula().toString() + " ";
			// System.out.println("2222"+cell.getNumericCellValue());
			// System.out.println("111q"+value);
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				value = sdf.format(date);
			} else {
				if (cell.getCellStyle().getDataFormatString().indexOf("%") != -1) {
					DecimalFormat df = new DecimalFormat("0.00");
					value = df.format(cell.getNumericCellValue() * 100) + "%";
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					value = cell.getStringCellValue();
				}
				/*
				 * DecimalFormat format = new DecimalFormat("#0.###"); value =
				 * format.format(cell.getNumericCellValue());
				 */
			}
		}
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			value = "";
		}
		return value;
	}

	/**
	 * 判断单元格在不在合并单元格范围内，如果是，获取其合并的列数。
	 * 
	 * @param sheet
	 *            工作表
	 * @param cellRow
	 *            被判断的单元格的行号
	 * @param cellCol
	 *            被判断的单元格的列号
	 * @return
	 * @throws IOException
	 */
	private static int getMergerCellRegionCol(Sheet sheet, int cellRow, int cellCol) throws IOException {
		int retVal = 0;
		int sheetMergerCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
			int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
			int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
			int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
			int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
			if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
				if (cellCol >= firstCol && cellCol <= lastCol) {
					retVal = lastCol - firstCol + 1; // 得到合并的列数
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * 判断单元格是否是合并的单格，如果是，获取其合并的行数。
	 * 
	 * @param sheet
	 *            表单
	 * @param cellRow
	 *            被判断的单元格的行号
	 * @param cellCol
	 *            被判断的单元格的列号
	 * @return
	 * @throws IOException
	 */
	private static int getMergerCellRegionRow(Sheet sheet, int cellRow, int cellCol) throws IOException {
		int retVal = 0;
		int sheetMergerCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
			int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
			int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
			int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
			int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
			if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
				if (cellCol >= firstCol && cellCol <= lastCol) {
					retVal = lastRow - firstRow + 1; // 得到合并的行数
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * 单元格背景色转换
	 * 
	 * @param hc
	 * @return
	 */
	private String convertToStardColor(HSSFColor hc) {
		StringBuffer sb = new StringBuffer("");
		if (hc != null) {
			int a = HSSFColor.AUTOMATIC.index;
			int b = hc.getIndex();
			if (a == b) {
				return null;
			}
			sb.append("#");
			for (int i = 0; i < hc.getTriplet().length; i++) {
				String str;
				String str_tmp = Integer.toHexString(hc.getTriplet()[i]);
				if (str_tmp != null && str_tmp.length() < 2) {
					str = "0" + str_tmp;
				} else {
					str = str_tmp;
				}
				sb.append(str);
			}
		}
		return sb.toString();
	}

	/**
	 * 单元格小平对齐
	 * 
	 * @param alignment
	 * @return
	 */
	private String convertAlignToHtml(short alignment) {
		String align = "left";
		switch (alignment) {
		case CellStyle.ALIGN_LEFT:
			align = "left";
			break;
		case CellStyle.ALIGN_CENTER:
			align = "center";
			break;
		case CellStyle.ALIGN_RIGHT:
			align = "right";
			break;
		default:
			break;
		}
		return align;
	}

	/**
	 * 单元格垂直对齐
	 * 
	 * @param verticalAlignment
	 * @return
	 */
	private String convertVerticalAlignToHtml(short verticalAlignment) {
		String valign = "middle";
		switch (verticalAlignment) {
		case CellStyle.VERTICAL_BOTTOM:
			valign = "bottom";
			break;
		case CellStyle.VERTICAL_CENTER:
			valign = "center";
			break;
		case CellStyle.VERTICAL_TOP:
			valign = "top";
			break;
		default:
			break;
		}
		return valign;
	}
}