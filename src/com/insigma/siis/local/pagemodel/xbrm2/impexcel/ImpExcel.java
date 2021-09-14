package com.insigma.siis.local.pagemodel.xbrm2.impexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig; 

public class ImpExcel {
	private final static String xls = "xls";
	private final static String xlsx = "xlsx";

	private int firstRowNum = 2;
	private String ynId = null;
	private String yn_type = null;

	public String getYnId() {
		return ynId;
	}

	public void setYnId(String ynId) {
		this.ynId = ynId;
	}

	public String getYn_type() {
		return yn_type;
	}

	public void setYn_type(String yn_type) {
		this.yn_type = yn_type;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path = "C:\\HZB\\zhgboutputfiles\\703dd775-a82f-4456-8e2e-54b35e3018b2\\gbynrmb_707ead1942f045c390ce3f36a669b441.xlsx";

		ImpExcel obj = new ImpExcel();
		List<Map<Integer, String>> list = obj.readExcel(new File(path));
//		for (Map<Integer, String> map : list) {
//			for (Integer key : map.keySet()) {
//				if (map.get(key) != null && !"".equals(map.get(key))) {
//					System.out.print(map.get(key) + "[" + key + "]    ");
//				}
//			}
//			System.out.println("");
//		}

		Map<String, PictureData> pictureMap = obj.getPictures(path);
		System.out.println(pictureMap.toString());

	}

	/***
	 * 导入数据
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public int doImpExcelData(String path) throws Exception {
		List<Map<Integer, String>> excelRowList = this.readExcel(new File(path));
		List<Map<String, String>> rowList = this.changeList(excelRowList);
		Map<String, PictureData> pictureMap = getPictures(path);
		this.save(rowList, pictureMap);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/***
	 * 处理数据
	 * 
	 * @param rowList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, String>> changeList(List<Map<Integer, String>> rowList) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<Integer, String> titleMap = rowList.get(0);
		Integer xhColumnKey = 0;
		Integer photoColumnKey = 0;
		for (Integer key : titleMap.keySet()) {
			// 序号列
			if ("xh".equals(titleMap.get(key).toLowerCase())) {
				//序号
				xhColumnKey = key;
			} else if ("tp0117".equals(titleMap.get(key).toLowerCase())) {
				//照片
				photoColumnKey = key;
			}
			titleMap.put(key, titleMap.get(key).toLowerCase());
		}

		for (int i = 1; i < rowList.size(); i++) {
			Map<Integer, String> map = rowList.get(i);
			Map<String, String> dataRow = new HashMap<String, String>();
			for (Integer key : titleMap.keySet()) {
				if ("a0000".equals(titleMap.get(key)) && (map.get(key) == null || "".equals(map.get(key).trim()))) {
					if ((map.get(xhColumnKey) == null || "".equals(map.get(xhColumnKey).trim()))) {
						// 序号为空的情况下，是标题
						dataRow.put("type", "1");
					} else {
						// 序号不为空的情况下，是新增人员
						dataRow.put("a0000", "ZZZZ" + UUID.randomUUID().toString());
						dataRow.put("type", "3");
					}
//				} else if ("a0000".equals(titleMap.get(key))) {
//					dataRow.put("tp0100", UUID.randomUUID().toString());
				} else if ("tp0100".equals(titleMap.get(key))) {
					dataRow.put("tp0100", UUID.randomUUID().toString());
				} else {
					dataRow.put(titleMap.get(key), map.get(key));
				}
			}
			if ("1".equals(dataRow.get("type"))) {
				if (dataRow.get("tp0101") == null || "".equals(dataRow.get("tp0101"))) {
					dataRow.put("tp0101", dataRow.get("xh"));
				}

			} else {
				// photoColumnKey
				dataRow.put("tp0117", (i + firstRowNum+1) + "-" + photoColumnKey);
			}
			if (dataRow.get("type")==null) {
				if (isInteger(dataRow.get("xh"))) {
					dataRow.put("type", "3");
				}else {
					dataRow.put("type", "1");
				}
			}
			result.add(dataRow);
		}
		return result;
	}
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
	/**
	 * 保存数据
	 * 
	 * @param rowList
	 * @return
	 * @throws Exception
	 */
	private int save(List<Map<String, String>> rowList, Map<String, PictureData> pictureData) throws Exception {
		HBSession sess = null;
		PreparedStatement ps = null;
		Statement stat = null;
		Connection conn = null;
		String sql = "insert into TPHJ1(tp0100, a0000, type, tp0101,tp0117,tp0106,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128, sortnum, yn_id,tp0116 ,tp0107)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);

			// 删除
			ps = conn.prepareStatement("delete from TPHJ1 where yn_id=? and tp0116=?");
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.executeUpdate();

			// 插入
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < rowList.size(); i++) {
				Map<String, String> rowData = rowList.get(i);
				if (rowData.get("tp0100")== null || "".equals(rowData.get("tp0100"))) {
					continue;
				}
				ps.setString(1, rowData.get("tp0100"));
				String a0000 = "";
				if (textFormat(rowData.get("a0000")) != null && !"".equals(rowData.get("a0000"))) {
					a0000 = textFormat(rowData.get("a0000"));
				} else {
					a0000 = "ZZZZ" + UUID.randomUUID().toString();
				}
				ps.setString(2, a0000);

				ps.setString(3, rowData.get("type"));
				ps.setString(4, textFormat(rowData.get("tp0101")));

				ps.setString(5, "");

				// 处理照片
				if (!"".equals(a0000)) {
					String rowCol = rowData.get("tp0117");
					if (rowCol != null && !"".equals(rowCol)) {
						if (pictureData.get(rowCol) != null) {
							String filePath = AppConfig.PHOTO_PATH +"/"+ a0000.substring(0, 1) + "/" + a0000.substring(1, 2) + "/"
									+ a0000 + ".jpg"; 
							System.out.println(filePath);
							writePhoto(pictureData.get(rowCol), filePath);  //输出到文件
							
							//同时存入到A57中去
							A57 objA57 = new A57();
							objA57.setA0000(a0000);
							objA57.setA5714(a0000);
							objA57.setUpdated("1"); 
							objA57.setPhotstype("jpg"); 
							String fileName = objA57.getA0000()+".jpg";
							String photourl = PhotosUtil.getSavePath(fileName);
							objA57.setPhotopath(photourl);
							objA57.setPhotoname(fileName); 
							sess.saveOrUpdate(objA57); 
							sess.flush();
							conn.commit();
						}else {
							if (a0000.startsWith("ZZZZ")) {
								String filePath = AppConfig.PHOTO_PATH +"/"+ a0000.substring(0, 1) + "/" + a0000.substring(1, 2) + "/"
										+ a0000 + ".jpg"; 
								File file = new File(filePath);
								if (file.exists()) {
									file.delete();
								}
							}
						}
					}
				}

				ps.setString(6, textFormat(rowData.get("tp0106")));
				ps.setString(7, textFormat(rowData.get("tp0118")));
				ps.setString(8, textFormat(rowData.get("tp0119")));
				ps.setString(9, textFormat(rowData.get("tp0120")));
				ps.setString(10, textFormat(rowData.get("tp0102")));
				if (rowData.get("tp0121") != null) {
					ps.setInt(11, Integer.parseInt("0" + rowData.get("tp0121")));
				} else {
					ps.setInt(11, 0);
				}
				ps.setString(12, textFormat(rowData.get("tp0122")));
				ps.setString(13, textFormat(rowData.get("tp0123")));
				ps.setString(14, textFormat(rowData.get("tp0103")));
				ps.setString(15, textFormat(rowData.get("tp0104")));
				ps.setString(16, textFormat(rowData.get("tp0124")));
				ps.setString(17, textFormat(rowData.get("tp0125")));
				ps.setString(18, textFormat(rowData.get("tp0126")));
				ps.setString(19, textFormat(rowData.get("tp0127")));
				ps.setString(20, textFormat(rowData.get("tp0128")));
				ps.setInt(21, i + 1);
				ps.setString(22, ynId);
				ps.setString(23, yn_type);
				ps.setString(24, textFormat(rowData.get("tp0107")));
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			
			//保存 TPHJ1Html
			String TPHJ1HtmlSql = "delete from TPHJ1Html where yn_id=?";
			ps = sess.connection().prepareStatement(TPHJ1HtmlSql);
			ps.setString(1, ynId);
			ps.executeUpdate();
			sess.connection().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}

			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 图片写出
	public void writePhoto(PictureData pic,String filePath) throws IOException {
		// 获取图片流 
		// 获取图片索引 
		// 获取图片格式
		String ext = pic.suggestFileExtension();

		byte[] data = pic.getData();
		// 图片保存路径
		FileOutputStream out = new FileOutputStream(filePath);
		out.write(data);
		out.close(); 

	}

	private String textFormat(Object v) {
		String value = null;
		if (v != null) {
			if ("null".equals(v.toString())) {
				return null;
			}
			value = v.toString().replace("{/n}", "\n");
		}
		return value;
	}

	/***
	 * 读取数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private List<Map<Integer, String>> readExcel(File file) throws IOException, InvalidFormatException {
		// 检查文件
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<Map<Integer, String>> rowList = new ArrayList<Map<Integer, String>>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				// 获得当前sheet的开始行
				int firstRowNum = 2;
				// 获得当前sheet的结束行
				int lastRowNum = sheet.getLastRowNum();
				// 循环除了第一行的所有行
				
				
				for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
					// 获得当前行
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					// 获得当前行的开始列
					int firstCellNum = row.getFirstCellNum();
					// 获得当前行的列数
					int lastCellNum = row.getPhysicalNumberOfCells();
					// String[] cells = new String[row.getPhysicalNumberOfCells()];
					Map<Integer, String> map = new HashMap<Integer, String>();
					// 循环当前行
					for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
						Cell cell = row.getCell(cellNum);
						map.put(cellNum, getCellValue(cell));
					}
					rowList.add(map);
				}

			}
			// ==================处理照片======================
//			Map<Integer, String> titleMap = rowList.get(0);
//			Integer photoColumnKey = 0;
//			for (Integer key : titleMap.keySet()) {
//				// 序号列
//				if ("tp0117".equals(titleMap.get(key).toLowerCase())) {
//					photoColumnKey = key;
//				}
//				titleMap.put(key, titleMap.get(key).toLowerCase());
//			} 

		}
		return rowList;
	}

	public Map<String, PictureData> getPictures(String file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Map<String, PictureData> map = new HashMap<String, PictureData>();
		List<POIXMLDocumentPart> list = sheet.getRelations();
		for (int i=5;i<sheet.getLastRowNum();i++) {
			map.put(i+"-7", null);
		}
		for (POIXMLDocumentPart part : list) {
			if (part instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) part;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture picture = (XSSFPicture) shape;
					XSSFClientAnchor anchor = picture.getPreferredSize();
					CTMarker marker = anchor.getFrom();
					String key = marker.getRow() + "-" + marker.getCol();
					map.put(key, picture.getPictureData());
				}
			}
		}
//		workbook.close();
		return map;
	}

	private Workbook getWorkBook(File file) {
		// 获得文件名
		String fileName = file.getName();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 获取excel文件的io流
			InputStream is = new FileInputStream(file);
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return workbook;
	}

	private String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue.trim();
	}
}
