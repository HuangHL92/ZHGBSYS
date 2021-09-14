package com.insigma.siis.local.pagemodel.customquery.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.yntp.YNTPFileExportBS;
import com.insigma.siis.local.pagemodel.yntp.tpl.YNTPExcelUtil;

public class ExpExcelPList {

	public static String insertList(String sql,String excelname) throws Exception {
		String outputpath = YNTPFileExportBS.HZBPATH+"/zhgboutputfiles/rylbdc/"+SysManagerUtils.getUserloginName()+"/";
		File f = new File(outputpath);
		
		if(f.isDirectory()){//先清空输出目录
			JSGLBS.deleteDirectory(outputpath);
		}
		if(!f.isDirectory()){
			f.mkdirs();
		}
		
		
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> plist = cqbs.getListBySQL(sql,true);
		if (plist.size()==0) {
			throw new AppException("查询失效！请重选机构树");
		}
		
		
		File tplfilepath = new File(ExpExcelPList.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/customquery/util/plist.xls").getPath());
		InputStream is = new FileInputStream(tplfilepath);  //读取复制好的文件
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		exportExcel(plist,workbook);
		OutputStream fos = new  FileOutputStream(new File(outputpath+excelname));


        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
        return outputpath+excelname;
	}

	//人员信息excel导出 代码缓存
	public static Map<String,String> codevalueMap = new HashMap<String, String>();
	
	private static void exportExcel(List<HashMap<String, Object>> plist, Workbook workbook) throws Exception {
		
		
		
		
		List<Object[]> cfglist = CommSQL.getA01_config(SysManagerUtils.getUserId());
		Map<String,String[]> cfgMap = new HashMap<String, String[]>();
		for(Object[] o : cfglist){
			String dicid = o[0]==null?"":o[0].toString();
			String name = o[2]==null?"":o[2].toString();
			String excelwidth = o[11]==null?"":o[11].toString();
			String formtype = o[5]==null?"":o[5].toString();
			String aboutcode = o[4]==null?"":o[4].toString();
			String align = o[12]==null?"":o[12].toString();
			cfgMap.put(dicid, new String[]{name,excelwidth,formtype,aboutcode,align});
		}
		
		
		
		
		CellStyle styleBTL = getCellStyleBiaoTi(workbook,HorizontalAlignment.LEFT);
		CellStyle styleBTC = getCellStyleBiaoTi(workbook,HorizontalAlignment.CENTER);
		CellStyle styleZWLF = getCellStyleZhengWen(workbook,HorizontalAlignment.LEFT,"仿宋_GB2312");
		CellStyle styleZWCF = getCellStyleZhengWen(workbook,HorizontalAlignment.CENTER,"仿宋_GB2312");
		CellStyle styleZWCT = getCellStyleZhengWen(workbook,HorizontalAlignment.CENTER,"Times New Roman");
		
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowIndex = 2;
		int i=0;//正文序号
		int cellIndex = 0;
		Row row = YNTPExcelUtil.insertRow(sheet,rowIndex++);
		Cell cell = row.createCell(cellIndex++);//序号
		row.setHeightInPoints(60);
		YNTPExcelUtil.setCellValue(cell, styleBTC, "序号");
		//获取字段信息  表头
		Set<String> fieldSet = plist.get(0).keySet();
		//合并标题单元格
		
		List<Object[]> gloabCFG = new ArrayList<Object[]>();
		//表头
		for(String field : fieldSet){
			String[] cfgArray = cfgMap.get(field);
			if(cfgArray!=null){
				
				cell = row.createCell(cellIndex);
				//{name,excelwidth,formtype,aboutcode,align}
				//宽，一行字数，左中位置（左或中），是否数字列1是0否，是否参与计算行高1是0否
				String excelCfg = cfgArray[1];
				
				Float excelwidth = 10f;
				Float fontCountInline = 5f;
				String align = "中";
				String numberic = "0";
				String countH = "0";
				if(!StringUtils.isEmpty(excelCfg)){
					String[] excelCfgArray = excelCfg.split(",");
					if(excelCfgArray.length==5){
						if(!StringUtils.isEmpty(excelCfgArray[0])){
							excelwidth = Float.valueOf(excelCfgArray[0]);
						}
						if(!StringUtils.isEmpty(excelCfgArray[1])){
							fontCountInline = Float.valueOf(excelCfgArray[1]);
						}
						if(!StringUtils.isEmpty(excelCfgArray[2])){
							align = excelCfgArray[2];
						}
						if(!StringUtils.isEmpty(excelCfgArray[3])){
							numberic = excelCfgArray[3];
						}
						if(!StringUtils.isEmpty(excelCfgArray[4])){
							countH = excelCfgArray[4];
						}
					}
				}
				
				gloabCFG.add(new Object[]{excelwidth,fontCountInline,align,numberic,countH,cfgArray[2],cfgArray[3]});
				sheet.setColumnWidth(cellIndex++, (int)(256*excelwidth+184));//列宽
				
				
				String name = cfgArray[0];
				name = name.replaceAll("<br>", "");
				YNTPExcelUtil.setCellValue(cell, styleBTC, name);
				/*if("左".endsWith(align)){
					YNTPExcelUtil.setCellValue(cell, styleBTL, name);
				}else{
					YNTPExcelUtil.setCellValue(cell, styleBTC, name);
				}*/
				
				
				
				
				
			}
			
		}
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellIndex-1));
		
		//填充正文数据
		for(HashMap<String, Object> dataMap : plist){
			List<Float> rowHeightList = new ArrayList<Float>();
			row = YNTPExcelUtil.insertRow(sheet,rowIndex++);
			cellIndex=0;
			cell = row.createCell(cellIndex++);//序号
			YNTPExcelUtil.setCellValue(cell, styleZWCT, ++i);
			for(String field : dataMap.keySet()){
				String[] cfgArray = cfgMap.get(field);
				if(cfgArray!=null){
					//{excelwidth,fontCountInline,align,numberic,countH,cfgArray[2],cfgArray[3]}
					Object[] gloabCfgArray = gloabCFG.get(cellIndex-1);
					cell = row.createCell(cellIndex++);
					
					//{name,excelwidth,formtype,aboutcode,align}
					//宽，一行字数，左中位置（左或中），是否数字列1是0否，是否参与计算行高1是0否
					
					Float excelwidth = (Float)gloabCfgArray[0];
					Float fontCountInline = (Float)gloabCfgArray[1];
					String align = gloabCfgArray[2].toString();
					String numberic = gloabCfgArray[3].toString();
					String countH = gloabCfgArray[4].toString();
					
					
					
					
					String v = dataMap.get(field)==null?"":dataMap.get(field).toString();
					Object formtype = gloabCfgArray[5];
					if(formtype!=null&&"SELECT".equals(formtype)){
						Object codetype = gloabCfgArray[6];
						if(!"".equals(v)&&codetype!=null){
							
							//管理类别
							if("a0165".equals(field)){
								v = HBUtil.getValueFromTab(" listagg (code_name, ',') WITHIN GROUP (ORDER BY inino,code_value) code_name", "code_value", "code_type='"+codetype+"' and code_value in('"+(v.replaceAll(",", "','"))+"')");
							}else{
								String rv = codevalueMap.get(codetype+"_"+v);
								if(StringUtils.isEmpty(rv)){
									rv = HBUtil.getCodeName(codetype.toString(), v);
									if(rv!=null){
										codevalueMap.put(codetype+"_"+v,rv);
										v = rv;
									}
								}else{
									v = rv;
								}
							}
							
							
							
						}
					}
					
					if("左".endsWith(align)){
						if("1".equals(numberic)){
							YNTPExcelUtil.setCellValue(cell, styleZWCT, v);
						}else{
							YNTPExcelUtil.setCellValue(cell, styleZWLF, v);
						}
						
					}else{
						if("1".equals(numberic)){
							YNTPExcelUtil.setCellValue(cell, styleZWCT, v);
						}else{
							YNTPExcelUtil.setCellValue(cell, styleZWCF, v);
						}
						
					}
					if("1".equals(countH)){
						rowHeightList.add(getHeight(v, fontCountInline));
					}
				}
			}
			if(rowHeightList.size()>0){
				row.setHeightInPoints(getMax(rowHeightList));
			}
		}
		workbook.setPrintArea(
	            0, //工作薄 下标0开始
	            0, //起始列 下标0开始
	            --cellIndex, //终止列 下标0开始
	            0, //起始行 下标0开始
	            --rowIndex  //终止行 下标0开始
	    );
	}

	
	
	
	
	/**
	 * 带上下左右边框 正文正文居左
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleBiaoTi(Workbook workbook,HorizontalAlignment ha){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  	
		style.setBorderTop(BorderStyle.THIN);//上边框  	
		style.setBorderBottom(BorderStyle.THIN);//下边框  	
		
		style.setAlignment(ha);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		Font font =workbook.createFont();  
		font.setBold(true);
		font.setFontName("宋体");  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * 带上下左右边框 正文正文居左
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleZhengWen(Workbook workbook,HorizontalAlignment ha,String fname){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  	
		style.setBorderTop(BorderStyle.THIN);//上边框  	
		style.setBorderBottom(BorderStyle.THIN);//下边框  	
		
		style.setAlignment(ha);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		Font font =workbook.createFont();  
		font.setFontName(fname);  
		font.setFontHeightInPoints((short) 12);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	
	
	
	
	
	public static final float PADDING_BOTTOM = 10f;//下边距3
	public static float getMax(List<Float>  f){
		return Collections.max(f);
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
        //float defaultRowHeight = 18.75f;//每一行的高度指定  14号字体
        float defaultRowHeight = 14.25f;//每一行的高度指定  12号字体
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
	
	
}
