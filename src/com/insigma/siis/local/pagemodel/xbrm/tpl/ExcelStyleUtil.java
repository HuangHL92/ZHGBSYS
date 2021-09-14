package com.insigma.siis.local.pagemodel.xbrm.tpl;

import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.xbrm.QCJSPageModel;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;

public class ExcelStyleUtil {
	public static String js0100WhereSql;
	/**
	 * 带上下左右边框
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook workbook){
		return getCellStyle(workbook, null);
	}
	/**
	 * 带上下左右边框
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook workbook,Short fontHeightInPoints){
		if(fontHeightInPoints==null){
			fontHeightInPoints = 12;
		}
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN); //下边框   
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderTop(BorderStyle.THIN);//上边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		Font font =workbook.createFont();  
		font.setFontName("仿宋_GB2312");  
		font.setFontHeightInPoints((short) fontHeightInPoints);//字体大小
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
		setCellValue(cell, style, value, null);
	}
	public static void setCellValue(Cell cell,CellStyle style,Object value,ExcelReturnParam setRowHeight){
		cell.setCellStyle(style);
		if(value!=null&&!"".equals(value)){
			cell.setCellValue(value.toString());
			if(setRowHeight!=null){
				if(value.toString().length()>setRowHeight.getRowHeightLength()){
					setRowHeight.setSetRowHeight(false);
				}
			}
		}
	}
	
	//public static String js0100WhereSql;
	/**
	 * 通用sql
	 * @param cur_hj
	 * @param cur_hj_4
	 * @param rbId
	 * @param dc005
	 * @return
	 */
	public static String getSql(String cur_hj,String cur_hj_4,String rbId,String dc005, String js0100WhereSql){
		String ref_dc001 = "js01.dc001";
		String orderbyfield = " JS_SORT";
		//String dc001_alias = "dc001";
		//String f
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			cur_hj = cur_hj_4;
			if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
				orderbyfield = " JS_SORT4";
			}
		}
		if(RMHJ.JI_BEN_QING_KUANG.equals(cur_hj)){
			cur_hj = RMHJ.DONG_YI;
			
		}
		String hj4sql = " and js_type like '"+cur_hj+"%' ";
		if(RMHJ.DONG_YI.equals(cur_hj)){
			hj4sql = "";
			orderbyfield = " js0113";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			orderbyfield = " JS_SORT_dc005_2";
			ref_dc001 = "js_hj.JS_CLASS_DC001_2";
			//dc001_alias = "dc001_2";
		}
		//注释原来只差A02库的sql，
		/*String sql = "select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104"
				+ " from a01,js01,js_hj where "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+hj4sql + js0100WhereSql +"order by dc004,"+orderbyfield;*/
		//注释原来只差A02库的sql，
		String sql = "select * from (select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122 "
				+ " from a01,js01,js_hj where js01.js0122 = '1' and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+hj4sql + js0100WhereSql 
				+" union select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122 "
				+ " from v_js_a01 a01,js01,js_hj where js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+hj4sql + js0100WhereSql +""
				+ ")order by dc004,"+orderbyfield;
		return sql;
		//distinct a01.a0101, js01.js0108,js01.js0111,"+orderbyfield
	}
	/**
	 * 通气材料sql
	 * @param cur_hj 
	 * @return
	 */
	public static String getSqlTQCL(String rbId, String cur_hj){
		String sql = "select distinct js_hj.js0100, js01.js0108, js01.js0111, a01.a0107,"
               +" a01.zgxl, a01.a0196, nvl((select dc003 from deploy_classify t"
               +" where t.dc001 = lddw.dc001), '其他') dc003,"
               +" (select dc006 from deploy_classify t where t.dc001 = lddw.dc001) dc006,"
               +" a0101,"
               +" (select dc004 from deploy_classify t where t.dc001 = lddw.dc001) dc004,"
               +" sd.jsdw002, JS_SORT,lddw.jslddw003 "
               + " ,a01.a0141,a01.a0104"
               +" from a01, js01, js_hj ,js_dw sd,js_ld_dw lddw"
               +" where a01.a0000 = js01.a0000 and sd.b0111=lddw.b0111"
               +" and js_hj.js0100 = js01.js0100 and (sd.b0111=js01.js0115 or sd.b0111=js01.js0116)"
               +" and rb_id = '"+rbId+"'"
               +" and js_type like '"+cur_hj+"%'"
               +" order by dc004,lddw.jslddw003,JS_SORT";
		return sql;
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
	public static void mergeRegionAndSetValue(Sheet sheet, Workbook workbook,int colstart, int colend, int rownum,Object value) {
		sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,colstart,colend));
		Row row = sheet.getRow(rownum);
		if(value.toString().length()<60){
			row.setHeightInPoints((short)40);//行高
		}else{
			row.setHeightInPoints(getExcelCellAutoHeight(value.toString(), 32f)+40);//行高
		}
		Cell cell = row.createCell(colstart);
		
		
		CellStyle style = workbook.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		Font font =workbook.createFont();  
		font.setFontName("仿宋_GB2312");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		setCellValue(cell, style, value);
		//最后的单元格
		cell = row.createCell(colend);
		style = workbook.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);//又边框   
		cell.setCellStyle(style);
		
		//return style;
	}
	
	
	 public static float getExcelCellAutoHeight(String str, float fontCountInline) {
        float defaultRowHeight = 12.00f;//每一行的高度指定
        float defaultCount = 0.00f;
        for (int i = 0; i < str.length(); i++) {
            float ff = getregex(str.substring(i, i + 1));
            defaultCount = defaultCount + ff;
        }
        return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;//计算
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
	public static String getSql(String cur_hj, String cur_hj_4, String rbId, String dc005) {
		String ref_dc001 = "js01.dc001";
		String orderbyfield = " JS_SORT";
		//String dc001_alias = "dc001";
		//String f
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			cur_hj = cur_hj_4;
			if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
				orderbyfield = " JS_SORT4";
			}
		}
		if(RMHJ.JI_BEN_QING_KUANG.equals(cur_hj)){
			cur_hj = RMHJ.DONG_YI;
			
		}
		String hj4sql = " and js_type like '"+cur_hj+"%' ";
		if(RMHJ.DONG_YI.equals(cur_hj)){
			hj4sql = "";
			orderbyfield = " js0113";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			orderbyfield = " JS_SORT_dc005_2";
			ref_dc001 = "js_hj.JS_CLASS_DC001_2";
			//dc001_alias = "dc001_2";
		}
		//注释原来只差A02库的sql，
		/*String sql = "select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104"
				+ " from a01,js01,js_hj where "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+hj4sql + js0100WhereSql +"order by dc004,"+orderbyfield;*/
		//注释原来只差A02库的sql，
		String sql = "select * from (select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122 "
				+ " from a01,js01,js_hj where js01.js0122 = '1' and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+hj4sql + js0100WhereSql 
				+" union select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122 "
				+ " from v_js_a01 a01,js01,js_hj where js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+hj4sql + js0100WhereSql +""
				+ ")order by dc004,"+orderbyfield;
		return sql;
		//distinct a01.a0101, js01.js0108,js01.js0111,"+orderbyfield
	}
}
