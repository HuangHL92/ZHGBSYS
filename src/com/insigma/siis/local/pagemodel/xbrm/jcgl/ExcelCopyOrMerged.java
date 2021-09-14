package com.insigma.siis.local.pagemodel.xbrm.jcgl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelCopyOrMerged {


    public String importExcel(InputStream inputStream, String fileName) throws Exception{

        String message = "Import success";

        boolean isE2007 = false;
        //判断是否是excel2007格式
        if(fileName.endsWith("xlsx")){
            isE2007 = true;
        }

        int rowIndex = 0;
        try {
            InputStream input = inputStream;  //建立输入流
            Workbook wb;
            //根据文件格式(2003或者2007)来初始化
            if(isE2007){
                wb = new XSSFWorkbook(input);
            }else{
                wb = new HSSFWorkbook(input);
            }
            Sheet sheet = wb.getSheetAt(0);    //获得第一个表单
            int rowCount = sheet.getLastRowNum()+1;

            for(int i = 1; i < rowCount;i++){
                rowIndex = i;
                Row row ;

                for(int j = 0;j<26;j++){
                    if(isMergedRegion(sheet,i,j)){
                        System.out.print(getMergedRegionValue(sheet,i,j)+"\t");
                    }else{
                        row = sheet.getRow(i);
                        System.out.print(row.getCell(j)+"\t");
                    }
                }
                System.out.print("\n");
            }
        } catch (Exception ex) {
            message =  "Import failed, please check the data in "+rowIndex+" rows ";
        }
        return message;
    }


    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell){
        if(cell == null) return "";
        return cell.getStringCellValue();
    }


    /**
     * 合并单元格处理,获取合并行
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public  List<CellRangeAddress> getCombineCell(Sheet sheet)
    {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for(int i = 0; i<sheetmergerCount;i++)
        {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    private  int getRowNum(List<CellRangeAddress> listCombineCell,Cell cell,Sheet sheet){
        int xr = 0;
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        for(CellRangeAddress ca:listCombineCell)
        {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if(cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR)
            {
                if(cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC)
                {
                    xr = lastR;
                }
            }

        }
        return xr;

    }
    /**
     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
     * @param listCombineCell 存放合并单元格的list
     * @param cell 需要判断的单元格
     * @param sheet sheet
     * @return
     */
    public  String isCombineCell(List<CellRangeAddress> listCombineCell,Cell cell,Sheet sheet)
            throws Exception{
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        String cellValue = null;
        for(CellRangeAddress ca:listCombineCell)
        {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if(cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR)
            {
                if(cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC)
                {
                    Row fRow = sheet.getRow(firstR);
                    Cell fCell = fRow.getCell(firstC);
                    cellValue = getCellValue(fCell);
                    break;
                }
            }
            else
            {
                cellValue = "";
            }
        }
        return cellValue;
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public  String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }


    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private  boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
    
    

//	/**
//	 * 把一个excel中的cellstyletable复制到另一个excel，这里会报错，不能用这种方法，不明白呀？？？？？
//	 * @param fromBook
//	 * @param toBook
//	 */
//	public static void copyBookCellStyle(HSSFWorkbook fromBook,HSSFWorkbook toBook){
//		for(short i=0;i<fromBook.getNumCellStyles();i++){
//			HSSFCellStyle fromStyle=fromBook.getCellStyleAt(i);
//			HSSFCellStyle toStyle=toBook.getCellStyleAt(i);
//			if(toStyle==null){
//				toStyle=toBook.createCellStyle();
//			}
//			copyCellStyle(fromStyle,toStyle);
//		}
//	}
	/**
	 * 复制一个单元格样式到目的单元格样式
	 * @param fromStyle
	 * @param toStyle
	 */
	public static void copyCellStyle(HSSFCellStyle fromStyle,
			HSSFCellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		//边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());
		
		//背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());
		
		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
//		toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());//首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());//旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());
		
	}
	/**
	 * Sheet复制
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag
	 */
	public static void copySheet(HSSFWorkbook wb,HSSFSheet fromSheet, HSSFSheet toSheet,
			boolean copyValueFlag) {
		//合并区域处理
		mergerRegion(fromSheet, toSheet);
		for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			HSSFRow tmpRow = (HSSFRow) rowIt.next();
			HSSFRow newRow = toSheet.createRow(tmpRow.getRowNum());
			newRow.setHeight(tmpRow.getHeight());
			//行复制
			copyRow(wb,tmpRow,newRow,copyValueFlag);
		}
		int cols = fromSheet.getRow(0).getLastCellNum();
		for (int i=0 ; i <= cols; i++) {
			toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
		}
	}
	/**
	 * 行复制功能
	 * @param fromRow
	 * @param toRow
	 */
	public static void copyRow(HSSFWorkbook wb,HSSFRow fromRow,HSSFRow toRow,boolean copyValueFlag){
		for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
			HSSFCell tmpCell = (HSSFCell) cellIt.next();
			HSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
			
			copyCell(wb,tmpCell, newCell, copyValueFlag);
		}
	}
	/**
	* 复制原有sheet的合并单元格到新创建的sheet
	* 
	* @param sheetCreat 新创建sheet
	* @param sheet      原有的sheet
	*/
	public static void mergerRegion(HSSFSheet fromSheet, HSSFSheet toSheet) {
	   int sheetMergerCount = fromSheet.getNumMergedRegions();
	   for (int i = 0; i < sheetMergerCount; i++) {
	    CellRangeAddress mergedRegionAt = fromSheet.getMergedRegion(i);
	    toSheet.addMergedRegion(mergedRegionAt);
	   }
	}
	/**
	 * 复制单元格
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(HSSFWorkbook wb,HSSFCell srcCell, HSSFCell distCell,
			boolean copyValueFlag) {
		//HSSFCellStyle newstyle=wb.createCellStyle();
		//copyCellStyle(srcCell.getCellStyle(), newstyle);
		
		//distCell.setEncoding(s);
		//样式
		distCell.setCellStyle(srcCell.getCellStyle());
		//评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		distCell.setCellType(srcCell.getCellTypeEnum());
		if (copyValueFlag) {
			if (srcCell.getCellTypeEnum() == CellType.NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if (srcCell.getCellTypeEnum() == CellType.STRING) {
				distCell.setCellValue(srcCell.getStringCellValue());
			} else if (srcCell.getCellTypeEnum() == CellType.BLANK) {
				// nothing21
			} else if (srcCell.getCellTypeEnum() == CellType.BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if (srcCell.getCellTypeEnum() == CellType.ERROR) {
				//distCell.setCellErrorValue(srcCell.getErrorCellValue());
			} else if (srcCell.getCellTypeEnum() == CellType.FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else { // nothing29
			}
		}
	}
}