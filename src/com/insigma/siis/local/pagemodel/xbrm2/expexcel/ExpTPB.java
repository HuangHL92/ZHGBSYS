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
	 * �����ļ�·����ȡWorkbook����
	 * 
	 * @param filepath �ļ�ȫ·��
	 */
	public Workbook getWorkbook(String filepath)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream is = null;
		Workbook wb = null;
//        if (StringUtils.isBlank(filepath)) {
//            throw new IllegalArgumentException("�ļ�·������Ϊ��");
//        } else {
		String suffiex = getSuffiex(filepath);
//            if (StringUtils.isBlank(suffiex)) {
//                throw new IllegalArgumentException("�ļ���׺����Ϊ��");
//            }
		if (OFFICE_EXCEL_XLS.equals(suffiex) || OFFICE_EXCEL_XLSX.equals(suffiex)) {
			try {
				is = new FileInputStream(filepath);
				wb = WorkbookFactory.create(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("���ļ���Excel�ļ�");
		}
//        }
		return wb;
	}

	/**
	 * ��ȡ��׺
	 * 
	 * @param filepath filepath �ļ�ȫ·��
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
			int rowNos = sheet.getLastRowNum();// �õ�excel���ܼ�¼����
			for (int i = 0; i <= rowNos; i++) {// ������
				Row row = sheet.getRow(i);
				if (row != null) {
					int columNos = row.getLastCellNum();// ��ͷ�ܹ�������
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
	 * ����Excel�ļ�
	 * 
	 * @param filepath  filepath �ļ�ȫ·��
	 * @param sheetName ��Sheetҳ������
	 * @param titles    ��ͷ
	 * @param values    ÿ�еĵ�Ԫ��
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public boolean writeExcel(String filepath, String sheetName, List<String> titles,
			List<HashMap<String, Object>> values)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		boolean success = false;
		// OutputStream outputStream = null;
//        if (StringUtils.isBlank(filepath)) {
//            throw new IllegalArgumentException("�ļ�·������Ϊ��");
//        } else {
		String suffiex = getSuffiex(filepath);
//            if (StringUtils.isBlank(suffiex)) {
//                throw new IllegalArgumentException("�ļ���׺����Ϊ��");
//            }
		Workbook workbook = getWorkbook(filepath);
//            if ("xls".equals(suffiex.toLowerCase())) {
//                workbook = new HSSFWorkbook();
//            } else {
//                workbook = new XSSFWorkbook();
//            }
		// ����һ�����
		Sheet sheet = workbook.getSheetAt(0);
		// ����������
		Row row = null;
		// �洢������Excel�ļ��е����
		Map<String, Integer> titleOrder = new HashMap<String, Integer>();
		for (int i = 0; i < titles.size(); i++) {
//                Cell cell = row.createCell(i); 
			String title = titles.get(i);
//                cell.setCellValue(title);
			titleOrder.put(title, i);
		}
		// д������
		Iterator<HashMap<String, Object>> iterator = values.iterator();
		// �к�
		int beginRowIndex = 4;
		for (int i = 0; i < values.size(); i++) {
			if (!"1".equals(values.get(i).get("type"))) {
				//���Ǳ�����
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
		 
		//�����һ�в��Ǳ���
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
				// ��ȡ����
				String fieldName = map.getKey().toLowerCase();
				if ("a0000".equals(fieldName)) {
					A0000 = (String) map.getValue();
//					insertPicture(workbook,,A0000,index,3);
//					continue;
				}else if (!titleOrder.containsKey(fieldName)) {
					continue; 
				}
				// ����������ȡ���
				int i = titleOrder.get(fieldName);
				// ��ָ����Ŵ�����cell
				Cell cell = row.getCell(i);
				if (cell == null)
					continue;
				// ��ȡ�е�ֵ
				Object object = map.getValue();
				if ("xh".equals(fieldName)) { 
					object = String.valueOf(xhNumber);
					xhNumber++; 
				}else if ("tp0121".equals(fieldName)) { 
					//�����ֶ�Ϊ0ʱ����ʾ
					if ("0".equals(object.toString())) {
						object = "";
					}
				}else if ("tp0117".equals(fieldName)) { 
					object = "";
				}
				// �ж�object������
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
		BufferedImage bufferImg = null;// ͼƬ
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
				// �ȰѶ�������ͼƬ�ŵ�һ��ByteArrayOutputStream�У��Ա����ByteArray
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
				// ��ͼƬ����BufferedImage
				bufferImg = ImageIO.read(new File(picFilePath));
				// ��ͼƬд������
				ImageIO.write(bufferImg, "png", byteArrayOut); 
				
				// ����HSSFPatriarch��ͼƬд��EXCEL
				XSSFDrawing patriarch = sheet.createDrawingPatriarch();
				/**
				 * �ù��캯����8������ ǰ�ĸ������ǿ���ͼƬ�ڵ�Ԫ���λ�ã��ֱ���ͼƬ���뵥Ԫ��left��top��right��bottom�����ؾ���
				 * ���ĸ�������ǰ������ʾͼƬ���Ͻ����ڵ�cellNum�� rowNum�������������Ӧ�ı�ʾͼƬ���½����ڵ�cellNum�� rowNum��
				 * excel�е�cellNum��rowNum��index���Ǵ�0��ʼ��
				 * 
				 */ 
				XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short) photoColums, row, (short) (photoColums+1), row + 1);
				row++;
				anchor.setAnchorType(2); 
				// ����ͼƬ
				patriarch.createPicture(anchor,
						wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
				byteArrayOut.close();
				
			}

			fileOut = new FileOutputStream(filePath);
			// д��excel�ļ�
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
	 * ������
	 * 
	 * @param startRowIndex ��ʼ��
	 * @param endRowIndex   ������
	 * @param pPosition     Ŀ����ʼ��λ��
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
	 * ���ø�ʽ
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		// ������ʽ
		XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER); // ˮƽ����
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // ��ֱ����
		titleStyle.setLocked(true); // ��ʽ����
		titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBold(true);
		titleFont.setFontName("΢���ź�");
		titleStyle.setFont(titleFont);
		styles.put("title", titleStyle);

		// �ļ�ͷ��ʽ
		XSSFCellStyle headerStyle = (XSSFCellStyle) wb.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // ǰ��ɫ
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // ��ɫ��䷽ʽ
		headerStyle.setWrapText(true);
		headerStyle.setBorderRight(BorderStyle.THIN); // ���ñ߽�
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
		titleFont.setFontName("΢���ź�");
		headerStyle.setFont(headerFont);
		styles.put("header", headerStyle);

		Font cellStyleFont = wb.createFont();
		cellStyleFont.setFontHeightInPoints((short) 12);
		cellStyleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
		cellStyleFont.setFontName("΢���ź�");

		// ������ʽA
		XSSFCellStyle cellStyleA = (XSSFCellStyle) wb.createCellStyle();
		cellStyleA.setAlignment(HorizontalAlignment.CENTER); // ��������
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

		// ������ʽB:���ǰ��ɫΪǳ��ɫ
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
	 * ��Դ�ļ������ݸ��Ƶ���Excel�ļ�(�ɹ����Excelʹ��,ʹ�ü�ֵ����)
	 * 
	 * @param srcFilepath Դ�ļ�ȫ·��
	 * @param desFilepath Ŀ���ļ�ȫ·��
	 */
	public void copyFile(String srcFilepath, String desFilepath)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		FileUtils.copyFile(new File(srcFilepath), new File(desFilepath));
	}

}
