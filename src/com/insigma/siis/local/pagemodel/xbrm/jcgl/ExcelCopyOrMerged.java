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
        //�ж��Ƿ���excel2007��ʽ
        if(fileName.endsWith("xlsx")){
            isE2007 = true;
        }

        int rowIndex = 0;
        try {
            InputStream input = inputStream;  //����������
            Workbook wb;
            //�����ļ���ʽ(2003����2007)����ʼ��
            if(isE2007){
                wb = new XSSFWorkbook(input);
            }else{
                wb = new HSSFWorkbook(input);
            }
            Sheet sheet = wb.getSheetAt(0);    //��õ�һ����
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
     * ��ȡ��Ԫ���ֵ
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell){
        if(cell == null) return "";
        return cell.getStringCellValue();
    }


    /**
     * �ϲ���Ԫ����,��ȡ�ϲ���
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public  List<CellRangeAddress> getCombineCell(Sheet sheet)
    {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //���һ�� sheet �кϲ���Ԫ�������
        int sheetmergerCount = sheet.getNumMergedRegions();
        //�������еĺϲ���Ԫ��
        for(int i = 0; i<sheetmergerCount;i++)
        {
            //��úϲ���Ԫ�񱣴��list��
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
            //��úϲ���Ԫ�����ʼ��, ������, ��ʼ��, ������
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
     * �жϵ�Ԫ���Ƿ�Ϊ�ϲ���Ԫ���ǵĻ��򽫵�Ԫ���ֵ����
     * @param listCombineCell ��źϲ���Ԫ���list
     * @param cell ��Ҫ�жϵĵ�Ԫ��
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
            //��úϲ���Ԫ�����ʼ��, ������, ��ʼ��, ������
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
     * ��ȡ�ϲ���Ԫ���ֵ
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
     * �ж�ָ���ĵ�Ԫ���Ƿ��Ǻϲ���Ԫ��
     * @param sheet
     * @param row ���±�
     * @param column ���±�
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
//	 * ��һ��excel�е�cellstyletable���Ƶ���һ��excel������ᱨ�����������ַ�����������ѽ����������
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
	 * ����һ����Ԫ����ʽ��Ŀ�ĵ�Ԫ����ʽ
	 * @param fromStyle
	 * @param toStyle
	 */
	public static void copyCellStyle(HSSFCellStyle fromStyle,
			HSSFCellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		//�߿�ͱ߿���ɫ
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());
		
		//������ǰ��
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());
		
		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
//		toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());//��������
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());//��ת
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());
		
	}
	/**
	 * Sheet����
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag
	 */
	public static void copySheet(HSSFWorkbook wb,HSSFSheet fromSheet, HSSFSheet toSheet,
			boolean copyValueFlag) {
		//�ϲ�������
		mergerRegion(fromSheet, toSheet);
		for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			HSSFRow tmpRow = (HSSFRow) rowIt.next();
			HSSFRow newRow = toSheet.createRow(tmpRow.getRowNum());
			newRow.setHeight(tmpRow.getHeight());
			//�и���
			copyRow(wb,tmpRow,newRow,copyValueFlag);
		}
		int cols = fromSheet.getRow(0).getLastCellNum();
		for (int i=0 ; i <= cols; i++) {
			toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
		}
	}
	/**
	 * �и��ƹ���
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
	* ����ԭ��sheet�ĺϲ���Ԫ���´�����sheet
	* 
	* @param sheetCreat �´���sheet
	* @param sheet      ԭ�е�sheet
	*/
	public static void mergerRegion(HSSFSheet fromSheet, HSSFSheet toSheet) {
	   int sheetMergerCount = fromSheet.getNumMergedRegions();
	   for (int i = 0; i < sheetMergerCount; i++) {
	    CellRangeAddress mergedRegionAt = fromSheet.getMergedRegion(i);
	    toSheet.addMergedRegion(mergedRegionAt);
	   }
	}
	/**
	 * ���Ƶ�Ԫ��
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true����ͬcell������һ����
	 */
	public static void copyCell(HSSFWorkbook wb,HSSFCell srcCell, HSSFCell distCell,
			boolean copyValueFlag) {
		//HSSFCellStyle newstyle=wb.createCellStyle();
		//copyCellStyle(srcCell.getCellStyle(), newstyle);
		
		//distCell.setEncoding(s);
		//��ʽ
		distCell.setCellStyle(srcCell.getCellStyle());
		//����
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// ��ͬ�������ʹ���
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