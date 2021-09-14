package com.insigma.siis.local.pagemodel.gbmc.tpl;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class YNTPExcelUtil {
	public static final float PADDING_BOTTOM = 3f;//下边距
	public static final float PAGE_HEIGHT = 618f;//一页的高度
	
	/**
	 * 下边框
	 * @param workbook
	 * @return
	 */
	public static CellStyle getXiaBianKuangStyle(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);//左边框   
		return style;
	}
	/**
	 * 一级标题
	 * @param workbook
	 * @return
	 */
	public static CellStyle getYiJiBiaoTiStyle(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框   
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP); 
		Font font =workbook.createFont();  
		font.setFontName("黑体");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		
		return style;
	}
	
	/**
	 * 二级标题
	 * @param workbook
	 * @return
	 */
	public static CellStyle getErJiBiaoTiStyle(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框   
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP); 
		Font font =workbook.createFont();  
		font.setFontName("楷体_GB2312");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * 带左右边框 正文居中
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleC(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.TOP); 
		Font font =workbook.createFont();  
		font.setFontName("仿宋");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	/**
	 * 带左右边框 正文时间
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleDate(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.TOP); 
		Font font =workbook.createFont();  
		font.setFontName("Times New Roman");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	/**
	 * 带左右边框 正文正文居左
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleL(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP); 
		Font font =workbook.createFont();  
		font.setFontName("仿宋");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * 插入行
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	public static Row insertRow(Sheet sheet, Integer rowIndex) {  
        Row row = null;  
        if (sheet.getRow(rowIndex) != null) {  
            int lastRowNo = sheet.getLastRowNum();  
            sheet.shiftRows(rowIndex, lastRowNo, 1);  
        }  
        row = sheet.createRow(rowIndex);  
        return row;  
    }  
	
	public static void setCellValue(Cell cell,CellStyle style,Object value){
		
		cell.setCellStyle(style);
		if(value!=null&&!"".equals(value)){
			cell.setCellValue(value.toString());
		}
	}
	

	/**
	 * 合并行单元格
	 * 左右两边加边框
	 * 居左
	 * @param sheet 
	 * @param workbook
	 * @param string 
	 * @return
	 */
	public static Cell mergeRegionAndSetValue(Sheet sheet, int colstart, int colend, int rownum) {
		sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,colstart,colend));
		Row row = sheet.getRow(rownum);
		Cell cell = row.createCell(colstart);
		
		return cell;
	}
	
	/**
	 * 获取标题的行高
	 * @param v
	 * @param yntype 
	 * @return
	 */
	public static float getBiaoTiHeight(String v, String yntype){
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			return getHeight(v, 37f);
		}else{
			return getHeight(v, 34f);
		}
	}
	
	
	
	/**
	 * 获取姓名的行高
	 * @param v
	 * @param yntype 
	 * @return
	 */
	public static float getXingMingHeight(String v, String yntype){
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			return getHeight(v, 3f);
		}else{
			return getHeight(v, 3f);
		}
	}
	/**
	 * 获取学历学位的行高
	 * @param v
	 * @param yntype 
	 * @return
	 */
	public static float getXueLiXueWeiHeight(String v, String yntype){
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			return getHeight(v, 5f);
		}else{
			return getHeight(v, 5f);
		}
		
	}
	/**
	 * 获取现任拟任拟免的行高
	 * @param v
	 * @param yntype 
	 * @return
	 */
	public static float getNiRenMianHeight(String v, String yntype){
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			return getHeight(v, 9f);
		}else{
			return getHeight(v, 11f);
		}
	}
	
	public static float getMax(Float ...f){
		return Collections.max(Arrays.asList(f));
	}
	
	/**
	 * 
	 * @param v
	 * @param fontCountInline 一行的字
	 * @return
	 */
	private static float getHeight(String v, float fontCountInline){
		float height = 0.00f;
		String[] vs = v.split("\n");
		for(int i=0; i<vs.length; i++){
			height = height + getExcelCellAutoHeight(vs[i], fontCountInline);
		}
		return height+PADDING_BOTTOM;
	}
	 public static float getExcelCellAutoHeight(String str, float fontCountInline) {
        float defaultRowHeight = 18.75f;//每一行的高度指定
        float defaultCount = 0.00f;
        for (int i = 0; i < str.length(); i++) {
            float ff = getregex(str.substring(i, i + 1));
            defaultCount = defaultCount + ff;
        }
        if(defaultCount % fontCountInline==0&&defaultCount!=0){
        	return ((int) (defaultCount / fontCountInline)) * defaultRowHeight;//计算
        }else{
        	return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;//计算
        }
        
    }

    public static float getregex(String charStr) {
        
        if(charStr==" ")
        {
            return 0.5f;
        }
        // 判断是否为字母或字符
        if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
            return 0.5f;
        }
        // 判断是否为全角

        if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
            return 1.00f;
        }
        //全角符号 及中文
        if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
            return 1.00f;
        }
        return 0.5f;

    }
    /**
	 * 时间6位点2位
	 * @param str
	 * @param fontCountInline
	 * @return
	 */    
    public static String formatDate(String date){
    	if(date==null||"".equals(date)){
    		return "";
    	}else if(date.length()<6){
    		return date;
    	}else{
    		return date.substring(0, 4)+"."+date.substring(4,6);
    	}
    }
    /**
     * 党外人士
     * @param name
     * @return
     * @throws AppException 
     */
    public static String formatName(String a0141,String a0104) throws AppException{
    	String desc = "";
		if(a0141!=null&&!"".equals(a0141)&&!"01".equals(a0141)&&!"02".equals(a0141)){//不是中共党员或预备党员的
			a0141 = HBUtil.getCodeName("GB4762", a0141);
			if(a0141!=null&&!"".equals(a0141)){
				desc = "（"+a0141;
			}
		}
		if("2".equals(a0104)){
			if(!"".equals(desc)){
				desc = desc + "，女）";
			}else{
				desc = "（女）";
			}
		}else if(!"".equals(desc)){
			desc = desc + "）";
		}
		return desc;
    }
}
