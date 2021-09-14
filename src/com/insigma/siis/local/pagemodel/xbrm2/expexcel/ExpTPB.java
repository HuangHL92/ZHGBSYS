package com.insigma.siis.local.pagemodel.xbrm2.expexcel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFRow;
import com.insigma.siis.local.epsoft.config.AppConfig;

import sun.awt.SunHints.Value;

public class ExpTPB {

	private final static String OFFICE_EXCEL_XLS = "XLS";
	private final static String OFFICE_EXCEL_XLSX = "XLSX";

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		File directory = new File(".");
		String filePath = "d:\\temp\\gbynrmb.xlsx";
		String desFilepath = "d:\\temp\\gbynrmb11.xlsx";
		ExpTPB objExpTPB = new ExpTPB();
		Workbook workbook = objExpTPB.getWorkbook(filePath);
		Sheet sheet = workbook.getSheetAt(0);
		Row row= sheet.getRow(3);
		Cell cell = row.getCell(0);
		List<CellRangeAddress> listMerged= sheet.getMergedRegions();
		int k=0;
		for (CellRangeAddress mergeCell:listMerged) {
			int rowNum = mergeCell.getFirstRow();
			if (rowNum>=4 && rowNum<=5) {
				sheet.removeMergedRegion(k); 
			}
			k++;
		}
		objExpTPB.copyRows(sheet, 6, 6, 4); 
		
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(desFilepath);
			workbook.write(outputStream); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	private List<HashMap<String, Object>> getValues() {
		List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 100; i++) {
			HashMap<String, Object> titles = new HashMap<String, Object>();
			titles.put("xh", i);
			titles.put("TP0101", "TP0101" + "_" + i);
			titles.put("TP0117", "TP0117" + "_" + i);
			titles.put("TP0106", "TP0106" + "_" + i);
			titles.put("rmzw", "rmzw" + "_" + i);
			titles.put("TP0118", "TP0118" + "_" + i);
			titles.put("TP0119", "TP0119" + "_" + i);
			titles.put("TP0120", "TP0120" + "_" + i);
			titles.put("TP0102", "TP0102" + "_" + i);
			titles.put("TP0121", "TP0121" + "_" + i);
			titles.put("TP0122", "TP0122" + "_" + i);
			titles.put("TP0124", "TP0124" + "_" + i);
			titles.put("TP0123", "TP0123" + "_" + i);
			titles.put("TP0103", "TP0103" + "_" + i);
			titles.put("TP0104", "TP0104" + "_" + i);
			titles.put("TP0125", "TP0125" + "_" + i);
			titles.put("TP0126", "TP0126" + "_" + i);
			titles.put("TP0127", "TP0127" + "_" + i);
			titles.put("TP0128", "TP0128" + "_" + i);

			lst.add(titles);
		}
		return lst;
	}

	/***
	 * @param flag
	 * @return
	 */
	public List<String> getTitles() {
		List<String> titles = new ArrayList<String>();
		 
		titles.add("tp0100");
		titles.add("a0000");
		titles.add("type"); 
		titles.add("yn_id");
		titles.add("tp0116");
		 
		titles.add("xh");
		titles.add("tp0101");
		titles.add("tp0117");
		titles.add("tp0106");
		titles.add("rmzw");
		titles.add("tp0118");
		titles.add("tp0119");
		titles.add("tp0120");
		titles.add("tp0102");
		titles.add("tp0121");
		titles.add("tp0122");
		titles.add("tp0124");
		titles.add("tp0123");
		titles.add("tp0103");
		titles.add("tp0104");
		titles.add("tp0125");
		titles.add("tp0126");
		titles.add("tp0127");
		titles.add("tp0128");
		return titles;
	}

	/**
	 * 根据文件路径获取Workbook对象
	 * 
	 * @param filepath 文件全路径
	 */
	public Workbook getWorkbook(String filepath)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream is = null;
		Workbook wb = null;
//        if (StringUtils.isBlank(filepath)) {
//            throw new IllegalArgumentException("文件路径不能为空");
//        } else {
		String suffiex = getSuffiex(filepath);
//            if (StringUtils.isBlank(suffiex)) {
//                throw new IllegalArgumentException("文件后缀不能为空");
//            }
		if (OFFICE_EXCEL_XLS.equals(suffiex) || OFFICE_EXCEL_XLSX.equals(suffiex)) {
			try {
				is = new FileInputStream(filepath);
				wb = WorkbookFactory.create(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("该文件非Excel文件");
		}
//        }
		return wb;
	}

	/**
	 * 获取后缀
	 * 
	 * @param filepath filepath 文件全路径
	 */
	private String getSuffiex(String filepath) {
//        if (StringUtils.isBlank(filepath)) {
//            return "";
//        }
		int index = filepath.lastIndexOf(".");
		if (index == -1) {
			return "";
		}
		return filepath.substring(index + 1, filepath.length()).toUpperCase();
	}

	private String readExcelSheet(Sheet sheet) {
		StringBuilder sb = new StringBuilder();

		if (sheet != null) {
			int rowNos = sheet.getLastRowNum();// 得到excel的总记录条数
			for (int i = 0; i <= rowNos; i++) {// 遍历行
				Row row = sheet.getRow(i);
				if (row != null) {
					int columNos = row.getLastCellNum();// 表头总共的列数
					for (int j = 0; j < columNos; j++) {
						Cell cell = row.getCell(j);
						if (cell != null) {
							cell.setCellType(CellType.STRING);
							sb.append(cell.getStringCellValue() + " ");
							// System.out.print(cell.getStringCellValue() + " ");
						}
					}
					// System.out.println();
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 创建Excel文件
	 * 
	 * @param filepath  filepath 文件全路径
	 * @param sheetName 新Sheet页的名字
	 * @param titles    表头
	 * @param values    每行的单元格
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public boolean writeExcel(String filepath, String sheetName, List<String> titles,
			List<HashMap<String, Object>> values)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		boolean success = false;
		// OutputStream outputStream = null;
//        if (StringUtils.isBlank(filepath)) {
//            throw new IllegalArgumentException("文件路径不能为空");
//        } else {
		String suffiex = getSuffiex(filepath);
//            if (StringUtils.isBlank(suffiex)) {
//                throw new IllegalArgumentException("文件后缀不能为空");
//            }
		Workbook workbook = getWorkbook(filepath);
//            if ("xls".equals(suffiex.toLowerCase())) {
//                workbook = new HSSFWorkbook();
//            } else {
//                workbook = new XSSFWorkbook();
//            }
		// 生成一个表格
		Sheet sheet = workbook.getSheetAt(0);
		// 创建标题行
		Row row = null;
		// 存储标题在Excel文件中的序号
		Map<String, Integer> titleOrder = new HashMap<String, Integer>();
		for (int i = 0; i < titles.size(); i++) {
//                Cell cell = row.createCell(i); 
			String title = titles.get(i);
//                cell.setCellValue(title);
			titleOrder.put(title, i);
		}
		// 写入正文
		Iterator<HashMap<String, Object>> iterator = values.iterator();
		// 行号
		int beginRowIndex = 4;
		for (int i = 0; i < values.size(); i++) {
			if (!"1".equals(values.get(i).get("type"))) {
				//不是标题行
				if (i>1) {
					copyRows(sheet, 6, 6, beginRowIndex  + i);
				}
			} else {
				if (i>0) {
					copyRows(sheet, 5, 5, beginRowIndex  + i); 
				}
				row = sheet.getRow(beginRowIndex  + i);
				Cell cell = row.getCell(titleOrder.get("xh"));
				cell.setCellValue((String)(values.get(i).get("tp0101")));
				
				cell = row.getCell(titleOrder.get("type"));
				cell.setCellValue((String)(values.get(i).get("type")));
				
//				cell = row.getCell(1);
//				cell.setCellValue((String)(values.get(i).get("tp0101")));
			}
		}
		 
		//如果第一行不是标题
		if (!"1".equals(values.get(0).get("type"))) {
//			sheet.shiftRows(4,4,-1);
//			sheet.createRow(4);
//			copyRows(sheet, 6, 6, 4);  
			Cell cell = sheet.getRow(3).getCell(0);
			List<CellRangeAddress> listMerged= sheet.getMergedRegions();
			int k=0;
			for (CellRangeAddress mergeCell:listMerged) {
				int rowNum = mergeCell.getFirstRow();
				if (rowNum>=4 && rowNum<=5) {
					sheet.removeMergedRegion(k); 
				}
				k++;
			}
			copyRows(sheet, 6, 6, 4); 
		}
		row = sheet.getRow(3);
		row.setZeroHeight(true);
		/****
		 * 
		int index = 5;
		for (int i = 0; i < values.size(); i++) {
			if (!"1".equals(values.get(i).get("type"))) {
				if (i>1) {
					copyRows(sheet, 6, 6, index -1 + i);
				}
			} 
		}
		 * 
		 */
		
		//int rowIndex = 4;
		
		int xhNumber = 1;
		for (int jj=0;jj<values.size();jj++) {
			int rowIndex = jj+4;
			row = sheet.getRow(rowIndex); 
			if (row == null) { 
				continue;
			}
			
			
			Map<String, Object> value = values.get(jj);
			
			if ("1".equals(value.get("type"))) { 
				xhNumber = 1;
				continue;
			}
			
			String A0000 = null;
			for (Map.Entry<String, Object> map : value.entrySet()) {
				// 获取列名
				String fieldName = map.getKey().toLowerCase();
				if ("a0000".equals(fieldName)) {
					A0000 = (String) map.getValue();
//					insertPicture(workbook,,A0000,index,3);
//					continue;
				}else if (!titleOrder.containsKey(fieldName)) {
					continue; 
				}
				// 根据列名获取序号
				int i = titleOrder.get(fieldName);
				// 在指定序号处创建cell
				Cell cell = row.getCell(i);
				if (cell == null)
					continue;
				// 获取列的值
				Object object = map.getValue();
				if ("xh".equals(fieldName)) { 
					object = String.valueOf(xhNumber);
					xhNumber++; 
				}else if ("tp0121".equals(fieldName)) { 
					//年龄字段为0时候不显示
					if ("0".equals(object.toString())) {
						object = "";
					}
				}else if ("tp0117".equals(fieldName)) { 
					object = "";
				}
				// 判断object的类型
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (object instanceof Double) {
					cell.setCellValue((Double) object);
				} else if (object instanceof Date) {
					String time = simpleDateFormat.format((Date) object);
					cell.setCellValue(time);
				} else if (object instanceof Calendar) {
					Calendar calendar = (Calendar) object;
					String time = simpleDateFormat.format(calendar.getTime());
					cell.setCellValue(time);
				} else if (object instanceof Boolean) {
					cell.setCellValue((Boolean) object);
				} else {
					if (object != null) {
						cell.setCellValue(object.toString());
					}
				}
			} 
		}

		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filepath);
			workbook.write(outputStream);
			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (workbook != null) {
				workbook.close();
			}
		}

		insertPicture(values, filepath);
		return success;
//        }
	}

	private void insertPicture(List<HashMap<String, Object>> values, String filePath) {
		XSSFWorkbook wb = null;
		FileOutputStream fileOut = null;
		BufferedImage bufferImg = null;// 图片
		try {
			int photoColums =1;
			for (int i=0;i<getTitles().size();i++) {
				if ("tp0117".equals(getTitles().get(i))) {
					photoColums=i;
					break;
				}
			}
			wb = new XSSFWorkbook(new FileInputStream(new File(filePath)));
			XSSFSheet sheet = wb.getSheetAt(0);
			
			
			for (int jj =0;jj<values.size();jj++) {
				HashMap<String, Object> value = values.get(jj);
			//for (HashMap<String, Object> value : values) {
				String a0000 = (String) value.get("a0000");
				if ("1".equals(value.get("type"))) {
					continue;
				}
				int row = jj+4;
				// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
//				String picFilePath = "C:/HZB/HZBPHOTOS/" + a0000.substring(0, 1) + "/" + a0000.substring(1, 2) + "/"
//						+ a0000 + ".jpg"; 
				String picFilePath = AppConfig.PHOTO_PATH +"/"+ a0000.substring(0, 1) + "/" + a0000.substring(1, 2) + "/"
						+ a0000 + ".jpg"; 
				
				File f = new File(picFilePath);
				if (!f.exists()) { 
					String path = (new ExpTPB()).getClass().getClassLoader().getResource("/").getPath();
					path = path.replaceAll("/WEB-INF/classes/", "");
					picFilePath = path +"/rmb/images/head_pic.png";
					//picFilePath = "D:/EPWork/eclipse/workspace/odin6/hzb_wx/WebContent/rmb/images/head_pic.png";
				}
				// 将图片读到BufferedImage
				bufferImg = ImageIO.read(new File(picFilePath));
				// 将图片写入流中
				ImageIO.write(bufferImg, "png", byteArrayOut); 
				
				// 利用HSSFPatriarch将图片写入EXCEL
				XSSFDrawing patriarch = sheet.createDrawingPatriarch();
				/**
				 * 该构造函数有8个参数 前四个参数是控制图片在单元格的位置，分别是图片距离单元格left，top，right，bottom的像素距离
				 * 后四个参数，前连个表示图片左上角所在的cellNum和 rowNum，后天个参数对应的表示图片右下角所在的cellNum和 rowNum，
				 * excel中的cellNum和rowNum的index都是从0开始的
				 * 
				 */ 
				XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short) photoColums, row, (short) (photoColums+1), row + 1);
				row++;
				anchor.setAnchorType(2); 
				// 插入图片
				patriarch.createPicture(anchor,
						wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
				byteArrayOut.close();
				
			}

			fileOut = new FileOutputStream(filePath);
			// 写入excel文件
			wb.write(fileOut);

		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("io erorr : " + io.getMessage());
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制行
	 * 
	 * @param startRowIndex 起始行
	 * @param endRowIndex   结束行
	 * @param pPosition     目标起始行位置
	 */
	public void copyRows(Sheet currentSheet, int startRow, int endRow, int pPosition) {
		int pStartRow = startRow - 1;
		int pEndRow = endRow - 1;
		int targetRowFrom;
		int targetRowTo;
		int columnCount;
		CellRangeAddress region = null;
		int i;
		int j;
		if (pStartRow == -1 || pEndRow == -1) {
			return;
		}
		for (i = 0; i < currentSheet.getNumMergedRegions(); i++) {
			region = currentSheet.getMergedRegion(i);
			if ((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
				targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
				targetRowTo = region.getLastRow() - pStartRow + pPosition;
				CellRangeAddress newRegion = region.copy();
				newRegion.setFirstRow(targetRowFrom);
				newRegion.setFirstColumn(region.getFirstColumn());
				newRegion.setLastRow(targetRowTo);
				newRegion.setLastColumn(region.getLastColumn());
				currentSheet.addMergedRegion(newRegion);
			}
		}
		for (i = pStartRow; i <= pEndRow; i++) {
			org.apache.poi.ss.usermodel.Row sourceRow = currentSheet.getRow(i);
			columnCount = sourceRow.getLastCellNum();
			if (sourceRow != null) {
				org.apache.poi.ss.usermodel.Row newRow = currentSheet.createRow(pPosition - pStartRow + i);
				newRow.setHeight(sourceRow.getHeight());
				for (j = 0; j < columnCount; j++) {
					org.apache.poi.ss.usermodel.Cell templateCell = sourceRow.getCell(j);
					if (templateCell != null) {
						org.apache.poi.ss.usermodel.Cell newCell = newRow.createCell(j);
						copyCell(templateCell, newCell);
					}
				}
			}
		}
	}

	private void copyCell(org.apache.poi.ss.usermodel.Cell srcCell, org.apache.poi.ss.usermodel.Cell distCell) {
		distCell.setCellStyle(srcCell.getCellStyle());
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
				distCell.setCellValue(srcCell.getDateCellValue());
			} else {
				distCell.setCellValue(srcCell.getNumericCellValue());
			}
		} else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
			distCell.setCellValue(srcCell.getRichStringCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
			// nothing21
		} else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
			distCell.setCellValue(srcCell.getBooleanCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
			distCell.setCellErrorValue(srcCell.getErrorCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
			distCell.setCellFormula(srcCell.getCellFormula());
		} else { // nothing29

		}
	}

	/**
	 * 设置格式
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		// 标题样式
		XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER); // 水平对齐
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直对齐
		titleStyle.setLocked(true); // 样式锁定
		titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBold(true);
		titleFont.setFontName("微软雅黑");
		titleStyle.setFont(titleFont);
		styles.put("title", titleStyle);

		// 文件头样式
		XSSFCellStyle headerStyle = (XSSFCellStyle) wb.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // 前景色
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 颜色填充方式
		headerStyle.setWrapText(true);
		headerStyle.setBorderRight(BorderStyle.THIN); // 设置边界
		headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		titleFont.setFontName("微软雅黑");
		headerStyle.setFont(headerFont);
		styles.put("header", headerStyle);

		Font cellStyleFont = wb.createFont();
		cellStyleFont.setFontHeightInPoints((short) 12);
		cellStyleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
		cellStyleFont.setFontName("微软雅黑");

		// 正文样式A
		XSSFCellStyle cellStyleA = (XSSFCellStyle) wb.createCellStyle();
		cellStyleA.setAlignment(HorizontalAlignment.CENTER); // 居中设置
		cellStyleA.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleA.setWrapText(true);
		cellStyleA.setBorderRight(BorderStyle.THIN);
		cellStyleA.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleA.setBorderLeft(BorderStyle.THIN);
		cellStyleA.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleA.setBorderTop(BorderStyle.THIN);
		cellStyleA.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleA.setBorderBottom(BorderStyle.THIN);
		cellStyleA.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleA.setFont(cellStyleFont);
		styles.put("cellA", cellStyleA);

		// 正文样式B:添加前景色为浅黄色
		XSSFCellStyle cellStyleB = (XSSFCellStyle) wb.createCellStyle();
		cellStyleB.setAlignment(HorizontalAlignment.CENTER);
		cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleB.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		cellStyleB.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyleB.setWrapText(true);
		cellStyleB.setBorderRight(BorderStyle.THIN);
		cellStyleB.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleB.setBorderLeft(BorderStyle.THIN);
		cellStyleB.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleB.setBorderTop(BorderStyle.THIN);
		cellStyleB.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleB.setBorderBottom(BorderStyle.THIN);
		cellStyleB.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellStyleB.setFont(cellStyleFont);
		styles.put("cellB", cellStyleB);

		return styles;
	}

	/**
	 * 将源文件的内容复制到新Excel文件(可供理解Excel使用,使用价值不大)
	 * 
	 * @param srcFilepath 源文件全路径
	 * @param desFilepath 目标文件全路径
	 */
	public void copyFile(String srcFilepath, String desFilepath)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		FileUtils.copyFile(new File(srcFilepath), new File(desFilepath));
	}

}
