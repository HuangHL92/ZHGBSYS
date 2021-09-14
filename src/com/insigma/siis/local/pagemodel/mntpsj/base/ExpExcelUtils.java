package com.insigma.siis.local.pagemodel.mntpsj.base;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.insigma.odin.framework.radow.annotation.PageEvent;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExpExcelUtils {
	@PageEvent("expExceldata")
    public static String expExceldata(HttpServletRequest request,String outputpath) throws Exception {
		
		File f = new File(outputpath);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		
		String downloadUUID = UUID.randomUUID().toString();
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*(\\.[\\d]*)?$");  
		try {
			String data = request.getParameter("data");
			String excelname = request.getParameter("excelname");
			JSONArray  dataObject = JSONArray.fromObject(data);
			List<List<HashMap<String, String>>> dataList = (List<List<HashMap<String, String>>>)dataObject;
	        //创建Excel文件薄
			XSSFWorkbook workbook=new XSSFWorkbook();
	        //创建工作表sheeet
	        XSSFSheet sheet=workbook.createSheet();
			
	        //列宽
	        Map<Integer, String> cellWidthMap = new HashMap<Integer, String>();
	        //样式库
	        Map<String, CellStyle> styleMap = new HashMap<String, CellStyle>();
			CellStyle cellstyle = null;
			CellStyle cellstyleN = null;
	        for(int i=0;i<dataList.size();i++) {
	        	List<HashMap<String, String>> arraylist=dataList.get(i);
	        	Row row = sheet.createRow(i);
	        	for(int j=0;j<arraylist.size();j++) {
	        		sheet.setColumnWidth(j, 5200);
	        		HashMap<String, String> map=new HashMap<String, String>();
	        	    JSONObject jsonObject = JSONObject.fromObject(arraylist.get(j)); 
	        	    @SuppressWarnings("rawtypes")
	        		Iterator it = jsonObject.keys();
	        	    // 遍历jsonObject数据，添加到Map对象  
	        	    while (it.hasNext())  
	        	    {
	        	    	String key = String.valueOf(it.next()).toString();  
	        	        String value = (String) jsonObject.get(key).toString();    
	        	        map.put(key, value);  
	        	    } 
	        		Cell cell = row.createCell(j);
	        		String cellV = map.get("text");
	        		
	        		String rowspan = map.get("rowspan");
	        		//背景颜色
	        		String color = map.get("color");
	        		String fontfamily = map.get("fontfamily");
	        		String fontsize = map.get("fontsize");
	        		String textalign = map.get("textalign");
	        		cellstyle = styleMap.get(color+fontfamily+fontsize+textalign);
	        		cellstyleN = styleMap.get(color+fontfamily+fontsize+"N"+textalign);
	        		if(cellstyle == null){
						cellstyle = getCellStyleCL2(workbook, fontfamily, fontsize,color,textalign);
						cellstyleN = getCellStyleCL2(workbook, "Times New Roman", fontsize,color,textalign);
						styleMap.put(color+fontfamily+fontsize, cellstyle);
						styleMap.put(color+fontfamily+fontsize+"N", cellstyleN);
	        		}
	        		
	        		//列宽
	        		String colWidth=map.get("colWidth");
	        		if(!StringUtils.isEmpty(colWidth)){
	        			cellWidthMap.put(j, colWidth);
	        		}
	        		//行高
	        		String rowHeight=map.get("rowHeight");
	        		if(!StringUtils.isEmpty(rowHeight)){
	        			row.setHeightInPoints(Float.valueOf(rowHeight)/96*72);
	        		}
	        		int rowspani=0;
	        		if(rowspan!=null) {
	        			rowspani=Integer.valueOf(rowspan)-1;
	        		}
	        		String colspan=map.get("colspan");
	        		int colspani=0;
	        		if(colspan!=null) {
	        			colspani=Integer.valueOf(colspan)-1;
	        		}
					if(cellV!=null){
						cell.setCellValue(cellV);
					}
					if(pattern.matcher(cellV).matches()){
						cell.setCellStyle(cellstyleN);
					}else{
						cell.setCellStyle(cellstyle);
					}
					
					
					if(rowspani!=0||colspani!=0) {
				    	CellRangeAddress CellRangeAddress;
						try {
							CellRangeAddress = new CellRangeAddress(i, i + rowspani, j, j + colspani);
							sheet.addMergedRegion(CellRangeAddress);	
						} catch (Exception e) {
						}
				    	
					}
					
	        	}
	        }
	        
	        //设置列宽
	        for(Integer colindex : cellWidthMap.keySet()){
	        	sheet.setColumnWidth(colindex, Integer.valueOf(cellWidthMap.get(colindex))* 33);
	        }
	        
			String downloadName = excelname + ".xlsx";
			String downloadPath = outputpath + downloadName;
			OutputStream fos = new FileOutputStream(new File(downloadPath));
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			workbook.write(bos);
			bos.flush();
			fos.close();
			bos.close();
			workbook.close();
			
			request.getSession().setAttribute(downloadUUID, new String[] { downloadPath, downloadName });
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return downloadUUID;
    }
	
	
	public static CellStyle getCellStyleCL2(XSSFWorkbook workbook,String fontName,String fontsize, String color, String textalign){
		XSSFCellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setBorderTop(BorderStyle.THIN);//上边框  		
		style.setBorderBottom(BorderStyle.THIN);//下边框  	
		HorizontalAlignment ta = HorizontalAlignment.CENTER;
		if("left".equals(textalign)){
			ta = HorizontalAlignment.LEFT;
		}else if("right".equals(textalign)){
			ta = HorizontalAlignment.RIGHT;
		}
		style.setAlignment(ta);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //设置填充方案
		style.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));  //设置填充颜色
		Font font =workbook.createFont();  
		if(StringUtils.isEmpty(fontName)){
			fontName = "楷体_GB2312";
		}else{
			fontName = fontName.replace(", \"Times New Roman\"", "");
		}
		font.setFontName(fontName); 
		short fs = 11;
		if(!StringUtils.isEmpty(fontsize)){
			fs = (short)(Double.valueOf(fontsize.replace("px", ""))/96*72);
		}
		font.setFontHeightInPoints(fs);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		
		if(!StringUtils.isEmpty(color)){
			color = color.toLowerCase();
			if(color.indexOf("rgba")>=0){
				style.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
			}else if(color.indexOf("rgb")>=0){
				color = color.replaceAll("(rgb\\()|(rgba\\()", "").replace(")", "");
				String[] colors = color.split(",");
				int r = Integer.valueOf(colors[0].trim());
				int g = Integer.valueOf(colors[1].trim());
				int b = Integer.valueOf(colors[2].trim());
				style.setFillForegroundColor(new XSSFColor(new Color(r,g,b)));
			}
			
		}else{
			style.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
		}
		
		return style;
	}
}
