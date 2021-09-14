package com.insigma.siis.local.util;


import java.util.Iterator;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFFont;

import org.apache.poi.ss.usermodel.Font;

public class ExeclUtil {
    private static Logger log = Logger.getLogger(ExeclUtil.class);

    /**
     * �и��ƹ���
     * @param fromRow
     * @param toRow
     */
    public static void copyRow(HSSFCellStyle newstyle,HSSFWorkbook wb,HSSFRow fromRow,HSSFRow toRow,boolean copyValueFlag){
        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
            HSSFCell tmpCell = (HSSFCell) cellIt.next();
            HSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
            //HSSFCellStyle newstyle=wb.createCellStyle();
            copyCell(newstyle,wb,tmpCell, newCell, copyValueFlag);
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
    public static void copyCell(HSSFCellStyle newstyle,HSSFWorkbook wb,HSSFCell srcCell, HSSFCell distCell,
                                boolean copyValueFlag) {
        newstyle = srcCell.getCellStyle();
        // copyCellStyle(srcCell.getCellStyle(), newstyle);
            /*newstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            newstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            newstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            newstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            newstyle.setWrapText(true);*/
//            distCell.setEncoding(srcCell.getEncoding());
        //��ʽ
        distCell.setCellStyle(newstyle);
        //����
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // ��ͬ�������ʹ���
        int srcCellType = srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if (copyValueFlag) {
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
                //distCell.setCellValue(srcCell.getRichStringCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());
            } else { // nothing29
                //distCell.setCellValue(srcCell.getRichStringCellValue());
            }
        }
    }

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
//          toStyle.setFont(fromStyle.getFont(null));
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());//��������
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());//��ת
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());

    }


    /**
     * ���õ�Ԫ�������С
     */
    public static void setFontSize(Cell cell) {
        Workbook book = cell.getSheet().getWorkbook();
        CellStyle cStyle = book.createCellStyle();
        cStyle.cloneStyleFrom(cell.getCellStyle());
        Font font = book.createFont();
        font.setFontName("����");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ������ʾ
        //font.setFontName("����");
        for (short k = 11; k >= 3; k--) {
            font.setFontHeightInPoints(k);
            if (checkCellReasonable(cell, k)) {
                break;
            }
        }
        //�����Ԫ����ʽ���ǵ�����
        cStyle.setWrapText(true);
        cStyle.setFont(font);
        cStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
        cStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
        cStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
        cStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
        cell.setCellStyle(cStyle);
    }
    /**
                   * ���õ�Ԫ�������С
     * @param cell  ��Ԫ�����
     * @param cStyles ���������ɴ�С��cStyle����
     */
    public static void setFontSize(Cell cell,CellStyle[] cStyles ) {
        for (int i = cStyles.length-1; i >= 0; i--) {
        	Font font = cell.getSheet().getWorkbook().getFontAt(cStyles[i].getFontIndex());
            if (checkCellReasonable(cell, font.getFontHeightInPoints())){
                //�����Ԫ����ʽ���ǵ�����
            	CellStyle cStyle = cStyles[i] ;
                cStyle.setWrapText(true);
                cStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                cStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
                cStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
                cStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
                cStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
                cell.setCellStyle(cStyle);
                break;
            }
         }

        

    }

    /**
                   *  ����cell��CellStyle ,�õ�ֻ�������С��ͬ��CellStyle����
     * @param book  ������Ԫ��
     * @param j ��С����
     * @param k  �������
     * @param fontName �������������
     * @param Boldweight �����ϸ ��  HSSFFont.BOLDWEIGHT_BOLD 
     * @return 
     */
    public static CellStyle[] getFontSizeStyle(HSSFWorkbook book,short j,short k,String fontName,short Boldweight,short Alignment) {
    	CellStyle[] cStyles = new CellStyle[k-j+1];
    	
    	for (int i = 0; i < cStyles.length; i++,j++) {
            CellStyle cStyle = book.createCellStyle();
        
            Font font = book.createFont();
            font.setFontHeightInPoints(j);
            font.setFontName(fontName);
            font.setBoldweight(Boldweight);
            cStyle.setFont(font);
            cStyle.setAlignment(Alignment);
            cStyles[i] = cStyle;
     
		}
    	return cStyles;
    }
    /**
     *  ����cell��CellStyle ,�õ�ֻ�������С��ͬ��CellStyle����
* @param book  ������Ԫ��
* @param j ��С����
* @param k  �������
* @param fontName �������������
* @param Boldweight �����ϸ ��  HSSFFont.BOLDWEIGHT_BOLD 
* @param moban ģ��CellStyle
* @return 
*/
public static CellStyle[] getFontSizeStyle(HSSFWorkbook book,short j,short k,String fontName,short Boldweight,CellStyle moban) {
CellStyle[] cStyles = new CellStyle[k-j+1];

for (int i = 0; i < cStyles.length; i++,j++) {
CellStyle cStyle = book.createCellStyle();
cStyle.cloneStyleFrom(moban);
Font font = book.createFont();
font.setFontHeightInPoints(j);
font.setFontName(fontName);
font.setBoldweight(Boldweight);
cStyle.setFont(font);

cStyles[i] = cStyle;

}
return cStyles;
}

    /**
     * У�鵥Ԫ���е������С�Ƿ����
     */
    private static boolean checkCellReasonable(Cell cell, short fontSize) {
        int sum = cell.getStringCellValue().length();
        double cellWidth = getTotalWidth(cell);
        double fontWidth = (double) fontSize / 72 * 96 * 2;
        double cellHeight = cell.getRow().getHeightInPoints();
        double rows1 = fontWidth * sum / cellWidth;
        double rows2 = cellHeight / fontSize;
        return rows2 >= rows1;
    }

    /**
     * ��ȡ��Ԫ����ܿ�ȣ���λ�����أ�
     */
    private static double getTotalWidth(Cell cell) {
        int x = getColNum(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex());
        double totalWidthInPixels = 0;
        for (int i = 0; i < x; i++) {
            totalWidthInPixels += cell.getSheet().getColumnWidthInPixels(i + cell.getColumnIndex());
        }
        return totalWidthInPixels;
    }

    /**
     * ��ȡ��Ԫ�������������Ǻϲ���Ԫ�񣬾ͻ�ȡ�ܵ�����
     */
    private static int getColNum(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        //�жϸõ�Ԫ���Ƿ��Ǻϲ����������
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return lastColumn - firstColumn + 1;
            }
        }
        return 1;
    }







}
