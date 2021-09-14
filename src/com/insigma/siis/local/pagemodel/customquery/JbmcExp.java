package com.insigma.siis.local.pagemodel.customquery;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Sheet;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.cadremgn.workflow.FlexibleStringExpander;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;

public class JbmcExp {
	

	 
    public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;
 
    public static final int UNIT_OFFSET_LENGTH = 7;
 
    public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 };
	
	//poi导出基本名册
	public static String expJbmc(String templateName,List<String> a0000s,String templatePath,String queryCondition,String isCode,String exportPath) throws IOException{
		String queryCondition_old = queryCondition;
		
		HBSession sess = HBUtil.getHBSession();
		File file=new File(templatePath+"\\"+templateName);
		HSSFWorkbook workbook = null;
		if(file.exists()){
			workbook = new HSSFWorkbook(new FileInputStream(file));
		}else{
			return "没有模板";
		}
		//获取excel的第一个sheet
		HSSFSheet sheet = workbook.getSheetAt(0);	
		 int rowNum = sheet.getPhysicalNumberOfRows();//获取每行
		 int firstRowNum = sheet.getFirstRowNum();
		 int lastRowNum = sheet.getLastRowNum();
		 //int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();//获取
		 //获取真实内容的起始行和结束行
		 Integer startIndex=0;
		 Integer endIndex=0;
		 Integer maxColumn=0;
		 for(int j=0;j<lastRowNum+1;j++){
		     HSSFRow row = sheet.getRow(firstRowNum);
		     if(row != null){
		    	 int cellNum = row.getPhysicalNumberOfCells();
		    	 if(maxColumn<=cellNum){
		    		 maxColumn=cellNum;
		    	 }
			     short firstCellNum = row.getFirstCellNum();
			     for(short k=0;k<cellNum;k++){
			    	 HSSFCell cell = row.getCell(firstCellNum);
			    	 if(cell != null && cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
			    		//System.out.println("==========="+cell.getStringCellValue()+",");
				    	 if(cell.getStringCellValue().equals("contentstart")){
				    		 startIndex=firstRowNum;
				    	 }
				    	 if(cell.getStringCellValue().equals("contentend")){
				    		 endIndex=firstRowNum;
				    	 }
			    	 }
			    	 
			    	 firstCellNum++;
			     }
		     }
		     
		     firstRowNum++; 
		 }
		 
		 //创建map封装每条数据的行列
		 HashMap<String, Object> anotherMap = new HashMap<String, Object>();
		 HashMap<String, Object> contentMap = new HashMap<String, Object>();
		 HashMap<String, HSSFCellStyle> anotherMapStyle = new HashMap<String, HSSFCellStyle>();
		 HashMap<String, HSSFCellStyle> contentMapStyle = new HashMap<String, HSSFCellStyle>();
		 //获取内容中 的数据			 
		 for(int j=0;j<=lastRowNum;j++){
			 HSSFRow row = sheet.getRow(j);
			 if(row != null){
				 //int cellNum = row.getPhysicalNumberOfCells();
				 int cellNum = row.getLastCellNum();
				 
				 short firstCellNum = row.getFirstCellNum();
				 for(short k=0;k<cellNum;k++){
					 HSSFCell cell = row.getCell(firstCellNum);
					if(cell != null){
						 HSSFCellStyle cellStyle = cell.getCellStyle();
						 Object cellvalue="";
						 switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								cellvalue=cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								cellvalue=cell.getCellFormula();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if(HSSFDateUtil.isCellDateFormatted(cell)){
									cellvalue =cell.getDateCellValue();
								}else{
									cellvalue=cell.getNumericCellValue();
								}
								break;
							default:
								break;
							}
						 
						 if(j<startIndex || j>endIndex){
							 anotherMap.put(j+","+firstCellNum, cellvalue);
							 anotherMapStyle.put(j+","+firstCellNum, cellStyle);
						 }
						 if(startIndex<j && j<endIndex){
							 contentMap.put(j+","+firstCellNum, cellvalue);
							 contentMapStyle.put(j+","+firstCellNum, cellStyle);
						 }
						 
					 }
					 firstCellNum++;
				}
			}
			 
			
		 }
		 
		// System.out.println(anotherMap);
		 //System.out.println(contentMap);
		//封装要查询的字段和表
		 ArrayList<String> query = new ArrayList<String>();
		 for (Map.Entry<String, Object> entry : contentMap.entrySet()) {
			 Object value = entry.getValue();
			 if(value instanceof String){
				 query.addAll(FlexibleStringExpander.getKeys((String)value));
				 /*
				 String value1=(String)value;
				 if(value1.matches("\\$\\{[\\w]+\\}")){
					 query.add(value1.replace("${", "").replace("}", ""));
				 }*/
			 }
			
		 }
		 
		 if(query==null || query.size()==0){
			 return templatePath+"\\"+templateName;
		 }
		 
		// System.out.println(query);
		//String queryFiled="";
		ArrayList<String> queryFiled = new ArrayList<String>();
		String queryTable="";
		for (String str : query) {
			String[] split = str.split("_");
			if(str.contains("CAPPOINTC")){
				split = new String[]{"CAPPOINTC",str.substring("CAPPOINTC_".length())};
				System.out.println(str.substring("CAPPOINTC_".length()));
			}
			String sqlcodeType="select code_type from code_table_col where table_code='"+split[0]+"' and col_code='"+split[1]+"'";
			String codeType = (String) sess.createSQLQuery(sqlcodeType).uniqueResult();
			String qq="";
			if(!StringUtils.isEmpty(codeType) && isCode != null && (isCode.equals("yes") || isCode.equals("undefined"))){
				qq="CONCAT((select code_name from code_value where code_type='"+codeType+"' and code_value="+split[0]+"."+split[1]+"),'&&&"+str+"')";
			}else{
				qq="CONCAT("+split[0]+"."+split[1]+",'&&&"+str+"')";
			}
			queryFiled.add(qq);
			queryTable+=split[0]+",";
		}
		//对查询的表去重
		TreeSet<String> treeSet = new TreeSet<String>();
		String[] tables = queryTable.split(",");
		for (String str : tables) {
			treeSet.add(str);
		}
		String[] tableArr = new String[treeSet.size()];
		for(int i=0;i<tableArr.length;i++){
			tableArr[i] =treeSet.pollFirst();
		}
		
		String realqueryTable="";
		for (String string : tableArr) {
			realqueryTable+=string +",";
		}
		
		queryTable=realqueryTable;
		//System.out.println(queryTable);
		//System.out.println(queryFiled);
		queryTable=queryTable.substring(0, queryTable.length()-1);
		//queryFiled=queryFiled.substring(0, queryFiled.length()-1);
		
		String[] queryTableArr = queryTable.split(",");
		Object[] queryFiledArr = queryFiled.toArray();
		System.out.println(queryFiledArr[0]);
		//定义map保存表和对应需要查询的字段
		HashMap<String, String> tableAndFields = new HashMap<String, String>();
		for(int i=0;i<queryTableArr.length;i++){
			String ff="";
			for(int j=0;j<queryFiledArr.length;j++){
				String field=(String) queryFiledArr[j];
				String[] split = field.split("&&&");
				String replace = split[split.length-1];
				if(field.contains("CAPPOINTC") && queryTableArr[i].equals("CAPPOINTC")){
					ff+=field+",";
				} else if(field.contains("CAPPOINTC") && !queryTableArr[i].equals("CAPPOINTC")){
					continue;
				}else if(replace.split("_")[0].equals(queryTableArr[i])){	
					ff+=field+",";
				}
			}
			
			ff=ff.substring(0, ff.length()-1);
			tableAndFields.put(queryTableArr[i], ff);
		}
        
		//定义Map保存所有数据
       HashMap<String, List<Object[]>> mapAll = new HashMap<String, List<Object[]>>();
        for(int i=0;i<a0000s.size();i++){
        	
        	ArrayList<Object> arrayList = new ArrayList<Object>();
        	for(Map.Entry<String, String> entry : tableAndFields.entrySet()){
        		String key = entry.getKey();
        		String value = entry.getValue();
        		queryCondition = queryCondition_old;
        		if(queryCondition == null){
        			queryCondition="";
        		} else {
        			if(queryCondition.contains("CAPPOINTC") && !key.equals("CAPPOINTC")){
        				queryCondition="";
        			} else 
        			if(!queryCondition.contains(key)){
        				queryCondition="";
        			}
        		}
        		String sql="select "+value+" from "+key+" where a0000='"+a0000s.get(i)+"' "+queryCondition;
        		System.out.println(sql);
        		List<Object[]> list = sess.createSQLQuery(sql).list();
        		if(list != null){
        			if(list.size()>1){
        				String allData="";
                		for (int j=0;j<list.size();j++) {
                			
                			Object object = list.get(j);
                			Object[] objects = null;
                			if(!(object instanceof Object[])){
                				objects = new Object[1];
                				objects[0]=object;
                			}else{
                				Object[] objectaa = list.get(j);
                				objects=objectaa;
                			}
                			
                			for(int x=0;x<objects.length;x++){
                				allData+=x+"@"+(String)objects[x]+"!!";
                			}
                			
    					}
                		allData=allData.substring(0, allData.length()-2);
                		String[] split = allData.split("!!");
                		TreeSet<String> treeSet2 = new  TreeSet<String>();
                		for (String string : split) {
    						String[] split2 = string.split("@");
    						treeSet2.add(split2[0]);
    					}
                		
                		String[] s=new String[treeSet2.size()];
                		for (int j=0;j<s.length;j++) {
    						s[j]=treeSet2.pollFirst();
    					}
                		HashMap<String, String> dataMap = new HashMap<String, String>();
                		for (String string : s) {
                			String data="";
                			String bb="";
                			for (String str : split) {
                				String[] split2 = str.split("@");
                				if(str.contains(string+"@")){
                					data+=split2[1].split("&&&")[0]+"\r\n";
                					bb=split2[1].split("&&&")[1];
                				}
                			}
                			//System.out.println(data);
                			//System.out.println("\r\n".length());
                			data=data.substring(0, data.length()-2);
                			
                			data+="&&&"+bb;
                			dataMap.put(string, data);
    					}
                		int max=0;
                		for (String string : s) {
                			if(max<Integer.parseInt(string)){
                				max=Integer.parseInt(string);
                			}
                		}
                		Object[] obja= new Object[max+1];
                		for(Map.Entry<String, String> entry1 : dataMap.entrySet()){
                			obja[Integer.parseInt(entry1.getKey())]=entry1.getValue();
                		}
                		
                		//ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
                		//arrayList.add(obja);
                		
                		for (Object object : obja) {
                			arrayList.add(object);
    					}
        			}else if(list.size()==1){
            			Object object = list.get(0);
            			
            			Object[] objects = null;
            			if(!(object instanceof Object[])){
            				objects = new Object[1];
            				objects[0]=object;
            			}else{
            				Object[] objectaa = list.get(0);
            				objects=objectaa;
            			}
            			for (Object obj : objects) {
            				arrayList.add(obj);
    					}
            		}
        			
        		}

        	}
        	Object[] array = arrayList.toArray();
        	ArrayList<Object[]> list = new ArrayList<Object[]>();
        	list.add(array);
        	//System.out.println(sql);
        	
        	mapAll.put(a0000s.get(i), list);
        }
        
       // System.out.println(listAll);
        
        //定义新的excel用于导出
        HSSFWorkbook newwork = new HSSFWorkbook();
        HSSFSheet sheet1 = newwork.createSheet("sheet1");
        //sheet1.autoSizeColumn((short)0);
        //添加content外的所有内容
        for (Map.Entry<String, Object> entry : anotherMap.entrySet()) {
        	String key = entry.getKey();
        	Object value = entry.getValue();
        	String[] split = key.split(",");
        	HSSFCellStyle hssfCellStyle = anotherMapStyle.get(key);
        	HSSFRow row = sheet1.getRow(Integer.parseInt(split[0]));
        	HSSFRow createRow = null;
        	if(row==null){
        		createRow = sheet1.createRow(Integer.parseInt(split[0]));
        	}else{
        		createRow=row;			
        	}
        	HSSFCell createCell = createRow.createCell(Integer.parseInt(split[1]));
        	if(value instanceof String){
        		String valueStr=(String)value;
        		createCell.setCellValue(valueStr);
        	}else if(value instanceof Double){
        		Double valueStr=(Double)value;
        		createCell.setCellValue(valueStr);
        	}else if(value instanceof Date){
        		Date valueStr=(Date)value;
        		createCell.setCellValue(valueStr);
        	}
        	
        	HSSFCellStyle headstyle = newwork.createCellStyle();
        	headstyle.cloneStyleFrom(hssfCellStyle);
        	createCell.setCellStyle(headstyle);
        	HSSFRow row2 = sheet.getRow(Integer.parseInt(split[0]));
        	createRow.setHeight(row2.getHeight());
        }
        
     /* //表头样式
    HSSFCellStyle headstyle = newwork.createCellStyle();
    HSSFFont font2 = newwork.createFont();    
    font2.setFontName("黑体");    
    font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
    font2.setFontHeightInPoints((short)10);  //字体大小  
        
    headstyle.setFont(font2);//选择需要用到的字体格式
    headstyle.setWrapText(true);//设置自动换行
  */   
     
    //设置数据样式
    HSSFCellStyle datastyle = newwork.createCellStyle();
    HSSFFont font3 = newwork.createFont();    
    font3.setFontName("宋体");    
    //font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
    font3.setFontHeightInPoints((short) 10);  //字体大小  
    datastyle.setFont(font3);
    datastyle.setWrapText(true);//设置自动换行   
       
        Integer rowIndex=0;
       // Integer dataSize=0;
        //添加content中的内容
        for(Map.Entry<String, List<Object[]>> entry1 : mapAll.entrySet()){
        	
        	List<Object[]> value2 = entry1.getValue();
        	//先填充不是数据的单元格
        	
        	for(Map.Entry<String, Object> entry : contentMap.entrySet()){
        		
        		String key = entry.getKey();
        		Object value = entry.getValue();
        		String[] split = key.split(",");
        		HSSFCellStyle hssfCellStyle = contentMapStyle.get(key);
        		HSSFCellStyle headstyle = newwork.createCellStyle();
            	headstyle.cloneStyleFrom(hssfCellStyle);
            	if(value instanceof String){
            		String valueStr=(String)value;
            		if(!valueStr.matches("\\$\\{[\\w]+\\}") && !valueStr.matches("[A-Z]+[\\d]+[\\&\\+\\-\\*\\/][A-Z]+[\\d]+")){
            			HSSFRow row = sheet1.getRow(Integer.parseInt(split[0])-1+rowIndex);
            			HSSFRow createRow=null;
            			if(row == null){
            				createRow = sheet1.createRow(Integer.parseInt(split[0])-1+rowIndex);
            			}else{
            				createRow=row;
            			}
                		HSSFCell createCell = createRow.createCell(Integer.parseInt(split[1]));
                		createCell.setCellValue(valueStr);
                    	createCell.setCellStyle(headstyle);
                    	HSSFRow row2 = sheet.getRow(Integer.parseInt(split[0]));
                    	createRow.setHeight(row2.getHeight());
                		
            		}else if(valueStr.matches("\\$\\{[\\w]+\\}")){
            			for(int i=0;i<value2.size();i++){
            				HSSFRow row = sheet1.getRow(Integer.parseInt(split[0])-1+rowIndex+i);
            				HSSFRow createRow=null;
            				if(row == null){
            					 createRow = sheet1.createRow(Integer.parseInt(split[0])-1+rowIndex+i);
            				}else{
            					createRow=row;
            				}
            				
                    		HSSFCell createCell = createRow.createCell(Integer.parseInt(split[1]));
                    		int maxHeight=0;
                        	Object[] obj = value2.get(i);
                        	for(int j=0;j<obj.length;j++){
                        		String str=(String)obj[j];
                        		String[] split1 = str.split("&&&");
                        		String data=split1[0];
                        		int dataLength=data.split("\r\n").length;
                        		if(maxHeight<dataLength){
                        			maxHeight=dataLength;
                        		}
                        		String[] split2 = data.split("\r\n");
                        	
                        		String table=split1[1];
                        		
                        		if(valueStr.equals("${"+table+"}")){
                        			headstyle.setWrapText(true);
                        			createCell.setCellValue(new HSSFRichTextString(data+" "));
                                	createCell.setCellStyle(headstyle);
                        		}
                        	}
                        	short firstCellNum = createRow.getFirstCellNum();
            				short lastCellNum = createRow.getLastCellNum();
            				for(int d=firstCellNum;d<=lastCellNum;d++){
            					sheet1.autoSizeColumn(d);
            				}
                        	//createRow.setHeight((short)(maxHeight * 300));
            				HSSFRow row2 = sheet.getRow(Integer.parseInt(split[0]));
                        	createRow.setHeight(row2.getHeight());
                        	
                    	}
            		}else if(valueStr.matches("[A-Z]+[\\d]+[\\&\\+\\-\\*\\/][A-Z]+[\\d]+")){
            			for(int i=0;i<value2.size();i++){
            				HSSFRow row = sheet1.getRow(Integer.parseInt(split[0])-1+rowIndex+i);
            				HSSFRow createRow=null;
            				if(row == null){
            					 createRow = sheet1.createRow(Integer.parseInt(split[0])-1+rowIndex+i);
            				}else{
            					createRow=row;
            				}
            				
            				
                    		HSSFCell createCell = createRow.createCell(Integer.parseInt(split[1]));
                    		valueStr=valueStr.replaceAll("\\d+", String.valueOf(Integer.parseInt(split[0])+rowIndex+i));
                    		//String[] formula = valueStr.split("&");
                    		//valueStr=formula[0]+String.valueOf(Integer.parseInt(split[0])+rowIndex+i)+"&"+formula[1]+String.valueOf(Integer.parseInt(split[0])+rowIndex+i);
                    		createCell.setCellFormula(valueStr);
                    		createCell.setCellStyle(headstyle);
                    		HSSFRow row2 = sheet.getRow(Integer.parseInt(split[0]));
                        	createRow.setHeight(row2.getHeight());
            			}
            		}
            	}else{
            		HSSFRow row = sheet1.getRow(Integer.parseInt(split[0])-1+rowIndex);
        			HSSFRow createRow=null;
        			if(row == null){
        				createRow = sheet1.createRow(Integer.parseInt(split[0])-1+rowIndex);
        			}else{
        				createRow=row;
        			}
            		HSSFCell createCell = createRow.createCell(Integer.parseInt(split[1]));
            		if(value instanceof Double){
            			Double valueStr = (Double)value;
            			createCell.setCellValue(valueStr);
            		}else if(value instanceof Date){
            			Date valueStr = (Date)value;
            			createCell.setCellValue(valueStr);
            		}
            		
                	createCell.setCellStyle(headstyle);
                	HSSFRow row2 = sheet.getRow(Integer.parseInt(split[0]));
                	createRow.setHeight(row2.getHeight());
            	}
        		
        	}

        	
        	rowIndex+=(endIndex-startIndex-2+value2.size());
        	
        }
        
        
        MergerRegion(sheet1, sheet);
        for(int i=0;i<maxColumn;i++){
        	sheet1.setColumnWidth(i, sheet.getColumnWidth(i));
        }
        
        String jbmc=exportPath.replace("\\webOffice\\word", "")+"\\ziploud/"+UUID.randomUUID().toString().replace("-", "")+"/"+"expFiles_"+System.currentTimeMillis()+"/";
        File file2 = new File(jbmc);
        if(!file2.exists()){
        	file2.mkdirs();
        }
        
        newwork.write(new File(jbmc+"基本名册.xls"));  
        
        newwork.close();//最后记得关闭工作簿  
		
		return jbmc+"基本名册.xls";
	}
	
	
	public static void MergerRegion(HSSFSheet sheetCreat, HSSFSheet sheet) {
		 int sheetMergerCount = sheet.getNumMergedRegions();
		 for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
			sheetCreat.addMergedRegion(mergedRegion);
		 }
	}
	
	
	public  static String expRmb(String templateName,List<String> a0000s,String templatePath,String queryCondition,String isCode,String exportPath) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		File file=new File(templatePath+"\\"+templateName);
		HSSFWorkbook workbook = null;
		if(file.exists()){
			workbook = new HSSFWorkbook(new FileInputStream(file));
		}else{
			return "没有模板";
		}
		//获取excel的第一个sheet
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		//获取第一个不为空行的行坐标
		int firstRowNum = sheet.getFirstRowNum();
		//获取最后一个不为空行的行坐标
		int lastRowNum = sheet.getLastRowNum();
			
		//保存所有有字段代码的数据
		HashMap<String, List<String>> fieldsMap = new HashMap<String, List<String>>();
		HashMap<String, HSSFCellStyle> fieldsMapStyle = new HashMap<String, HSSFCellStyle>();
		String imageIndex="";
		HSSFCellStyle imageStyle=null;
		//循环遍历excel
		for(int i=firstRowNum;i<=lastRowNum;i++){
			HSSFRow row = sheet.getRow(i);
			//获取当前行第一个不为空的列坐标
			if(row != null){
				short firstCellNum = row.getFirstCellNum();
				short lastCellNum = row.getLastCellNum();
				for(int j=firstCellNum;j<=lastCellNum;j++){
					HSSFCell cell = row.getCell(j);
					if(cell != null){
						String cellValue = cell.getStringCellValue();
						HSSFCellStyle cellStyle = cell.getCellStyle();
						//if(cellValue.matches("\\$\\{[\\w]+[#]?[-]*[\\d]*\\}")){
						//modify zepeng 20189710 修改EL表达式的判断方式，以实现同一单元格内多EL表达式的处理方式
						List<String> original = FlexibleStringExpander.getKeys(cellValue);
						if(original.size()>0) {
								fieldsMap.put(i+","+j, original);
								fieldsMapStyle.put(i+","+j, cellStyle);
							fieldsMapStyle.put(i + "," + j, cellStyle);
						}else if(cellValue.contains("image")){
							imageIndex=i+","+j;
							imageStyle=cellStyle;
						}
						/*
						if(cellValue.matches("\\$\\{.*?\\}")){
							fieldsMap.put(i+","+j, cellValue.replace("${", "").replace("}", ""));
							fieldsMapStyle.put(i+","+j, cellStyle);
						}else if(cellValue.contains("image")){
							imageIndex=i+","+j;
							imageStyle=cellStyle;
						}
						*/
					}
					
				}
			}
			
		}
			
		//System.out.println(fieldsMap);
		//System.out.println(fieldsMap.size());
		
		//分别封装单数据表的数据和其他表的数据
		ArrayList<String> allList = new ArrayList<String>();
		//modify zepeng 20189710 修改EL表达式的判断方式，以实现同一单元格内多EL表达式的处理方式
		 for (Map.Entry<String, List<String>> entry : fieldsMap.entrySet()) {
			 for(String value:(List<String>)entry.getValue()) {
				 allList.add(value);
			 }
		 }
		 
		 
		 if(allList== null || allList.size()==0){
			 return templatePath+"\\"+templateName;
		 }
		 
		 
		 String jbmc = exportPath.replace("\\webOffice\\word", "")+"\\ziploud/"+UUID.randomUUID().toString().replace("-", "")+"/"+"expFiles_"+System.currentTimeMillis()+"/";
		 //遍历人员循环插入数据
		 for(int i=0;i<a0000s.size();i++){
			 String a0000 = a0000s.get(i);
			 
			 Map<String, Object> map = FiledownServlet.getMapw(a0000,templateName ,allList,queryCondition,isCode);
			 System.out.println(map);
			 //插入图片
			 if(!"".equals(imageIndex) && imageStyle != null){ 
					int mergedRow = 0;
					int mergedColumn = 0;
			 A57 a57 = (A57)sess.get(A57.class, a0000);
			 if(a57 != null){
				 String photourl = a57.getPhotopath();
				// System.out.println(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				 File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				 if(fileF.isFile()){
					 FileOutputStream fileOut = null;     
			         BufferedImage bufferImg = null;     
			        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray    
			        try {  
			        	HSSFRow row = sheet.getRow(Integer.parseInt(imageIndex.split(",")[0]));
			        	int width=0;
			        	int height=0;
			        	float widthFloat = 0;
			        	float heightFloat = 0;
						 if(row != null){
							 HSSFCell cell = row.getCell(Integer.parseInt(imageIndex.split(",")[1]));
							 if(cell != null){
								 cell.setCellValue("");
								 cell.setCellStyle(imageStyle);
								 boolean isMerge = isMergedRegion(sheet, i, cell.getColumnIndex());  
								 if(isMerge) {
									 mergedRow = getMergedRow(sheet, Integer.parseInt(imageIndex.split(",")[0]), Integer.parseInt(imageIndex.split(",")[1]));
									 mergedColumn = getMergedColumn(sheet, Integer.parseInt(imageIndex.split(",")[0]), Integer.parseInt(imageIndex.split(",")[1]));
								 }
								 width = (int) Math.floor(sheet.getColumnWidthInPixels(cell.getColumnIndex()));
								 height = (int) Math.floor(row.getHeightInPoints());
								 widthFloat = sheet.getColumnWidthInPixels(cell.getColumnIndex());
								 heightFloat = row.getHeightInPoints();
							 }
						 }
			            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
			            bufferImg = ImageIO.read(fileF);     
			            ImageIO.write(bufferImg, "jpg", byteArrayOut);
			             int  imgWidth  =  bufferImg.getWidth( null );
			             int  imgHeight  =  bufferImg.getHeight( null );
			             int cellWidth = (int) Math.floor(widthFloat*8.53);
			             int cellHeight = (int) Math.floor(heightFloat*3.85);

			            	 width = (int) Math.floor(imgWidth*(widthFloat/imgWidth));
			            	 height = (int) Math.floor(imgHeight*(widthFloat/imgWidth));
			             
						 width = (int) Math.floor(width*8.53);
						 height = (int) Math.floor(height*3.85);
			             int left = 0;
			             int top = 0;
			             if(width<cellWidth) {
			            	 left = (cellWidth-width)/2;
			             }
			             if(height<cellHeight) {
			            	 top = (cellHeight-height)/2;
			             }
			             top+=1;
			          //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
			            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();     
			            //anchor主要用于设置图片的属性  imageIndex
			            //HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1000,255,Short.parseShort(imageIndex.split(",")[1]), Integer.parseInt(imageIndex.split(",")[0]), (short)(Integer.parseInt(imageIndex.split(",")[1])),Integer.parseInt(imageIndex.split(",")[0])+6);     

			            HSSFClientAnchor anchor = new HSSFClientAnchor(left, top,width+left,255,
			            		Short.parseShort(imageIndex.split(",")[1]), 
			            		Integer.parseInt(imageIndex.split(",")[0]), 
			            		(short)(Integer.parseInt(imageIndex.split(",")[1])),
			            		Integer.parseInt(imageIndex.split(",")[0]));     

			            anchor.setAnchorType(3);     
			            //插入图片    
			            patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 

			        }catch(Exception e){
			        	e.printStackTrace();
			        }
				 }
			 }else{
				 HSSFRow row = sheet.getRow(Integer.parseInt(imageIndex.split(",")[0]));
		        	//Integer width=0;
		        	//Integer height=0;
		        
					 if(row != null){
						 HSSFCell cell = row.getCell(Integer.parseInt(imageIndex.split(",")[1]));
						 if(cell != null){
							 cell.setCellValue("");
							 cell.setCellStyle(imageStyle);
							 //width = sheet.getColumnWidth(cell.getColumnIndex());
						 }
					 }
			 	}
			 }

			 for (Map.Entry<String, List<String>> entry1 : fieldsMap.entrySet()) {
				 String key1 = entry1.getKey();
				 List<String> value1s = entry1.getValue();
				 for(String value1:value1s) {
					 for (Map.Entry<String, Object> entry : map.entrySet()) {
						 String key = entry.getKey();
						 String value = (String) entry.getValue();
						 if(key.toLowerCase().startsWith(value1.toLowerCase())){
							 String[] split2 = key1.split(",");
							 String row=split2[0];
							 String col=split2[1];
							 HSSFRow row2 = sheet.getRow(Integer.parseInt(row));
							 HSSFCell cell = row2.getCell(Integer.parseInt(col));
							 String cellvalue = cell.getStringCellValue();
							 Map itemValue = new HashMap<String, String>();
							 itemValue.put(value1, value);
							 cellvalue = FlexibleStringExpander.expandString(cellvalue, itemValue);
							 cell.setCellValue(value);
							 if ("A01_A1701".equalsIgnoreCase(key)) {
						        //方式一
						        formatWords(workbook, sheet, row2, cell, workbook.createCellStyle(), value);//参数：简历单元格,样式对象,插入字符串
						        //方式二
//								setFontSize(workbook, cell, new JbmcExp(), value);
							 }
						 }
					 }
				 }
			 }
			 /*
			 for (Map.Entry<String, Object> entry : map.entrySet()) {
				 String key = entry.getKey();
				 String value = (String) entry.getValue();
				 for (Map.Entry<String, String> entry1 : fieldsMap.entrySet()) {
					 String key1 = entry1.getKey();
					 String value1 =entry1.getValue();
					 if(key.equalsIgnoreCase(value1)){
						 String[] split2 = key1.split(",");
						 String row=split2[0];
						 String col=split2[1];
						 HSSFRow row2 = sheet.getRow(Integer.parseInt(row));
						 HSSFCell cell = row2.getCell(Integer.parseInt(col));
						 cell.setCellValue(value);
					 }
				 }
			 }*/
				//modify zepeng 20189710 修改EL表达式的判断方式，以实现同一单元格内多EL表达式的处理方式
			 
			 
			 sheet.setForceFormulaRecalculation(true);//强制执行公式
			 
			 String namesql="select a0101 from a01 where a0000='"+a0000+"'";
			 String peopleName = (String) sess.createSQLQuery(namesql).uniqueResult();
			 
		       File file2 = new File(jbmc);
		       if(!file2.exists()){
		       		file2.mkdirs();
		       }
		       workbook.write(new File(jbmc+peopleName+".xls"));  
		
		 }
		 
	       	workbook.close();//最后记得关闭工作簿  
	       
	       	String infile=jbmc.substring(0, jbmc.length()-1)+".zip";
	       	//String infile=jbmc+"单位名册.xls";
	       	SevenZipUtil.zip7z(jbmc,infile, null);
	       	
	       	return infile;
	}
    /**   
     * 判断指定的单元格是否是合并单元格   
     * @param sheet    
     * @param row 行下标   
     * @param column 列下标   
     * @return   
     */    
    private static boolean isMergedRegion(HSSFSheet sheet,int row ,int column) {    
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

    /**   
     * 判断合]并了行   
     * @param sheet   
     * @param row   
     * @param column   
     * @return   
     */    
    private boolean isMergedRow(HSSFSheet sheet,int row ,int column) {    
        int sheetMergeCount = sheet.getNumMergedRegions();    
        for (int i = 0; i < sheetMergeCount; i++) {    
            CellRangeAddress range = sheet.getMergedRegion(i);    
            int firstColumn = range.getFirstColumn();    
            int lastColumn = range.getLastColumn();    
            int firstRow = range.getFirstRow();    
            int lastRow = range.getLastRow();    
            if(row == firstRow && row == lastRow){    
                if(column >= firstColumn && column <= lastColumn){    
                    return true;    
                }    
            }    
        }    
        return false;    
    }    
    private static int getMergedRow(HSSFSheet sheet,int row ,int column) {    
        int sheetMergeCount = sheet.getNumMergedRegions();    
        for (int i = 0; i < sheetMergeCount; i++) {    
            CellRangeAddress range = sheet.getMergedRegion(i);    
            int firstColumn = range.getFirstColumn();    
            int lastColumn = range.getLastColumn();    
            int firstRow = range.getFirstRow();    
            int lastRow = range.getLastRow();    
            if(row >= firstRow && row <= lastRow){    
            	if(column >= firstColumn && column <= lastColumn){
            		return lastRow-firstRow;
            	}
            }    
        }    
        return 0;    
    }    
    private static int getMergedColumn(HSSFSheet sheet,int row ,int column) {    
        int sheetMergeCount = sheet.getNumMergedRegions();    
        for (int i = 0; i < sheetMergeCount; i++) {    
            CellRangeAddress range = sheet.getMergedRegion(i);    
            int firstColumn = range.getFirstColumn();    
            int lastColumn = range.getLastColumn();    
            int firstRow = range.getFirstRow();    
            int lastRow = range.getLastRow();    
            if(row == firstRow && row < lastRow){    
            	if(column >= firstColumn && column <= lastColumn){
            		return lastColumn-firstColumn;
            	}
            }    
        }    
        return 0;    
    }    
	 
    /**
     * 宽像素
     * @param pxs
     * @return
     */
    public static short pixelWidth(int pxs) {
        short width = (short) (EXCEL_COLUMN_WIDTH_FACTOR * (pxs / UNIT_OFFSET_LENGTH));
        width += UNIT_OFFSET_MAP[(pxs % UNIT_OFFSET_LENGTH)];
        return width;
    }

	public static void main(String[] args) {
		String test="S4&H4";
		System.out.println(test.matches("[A-Z]+[\\d]+[\\&\\+\\-\\*\\/][A-Z]+[\\d]+"));
	}

	/**
	 * 
	 * 
	 * 处理建立字段相应信息
	 * 
	 * 
	 */

	/**
	 * 获取字符串长度(中文1,中文符合1,英文0.8,英文符合0.8)
	 */
	public static int gbkCount(String text) {
		String Reg = "^[\u4e00-\u9fa5]{1}|[（）]{1}$";// 正则
		int result = 0;
		float ansiCharNumber = 0;
		for (int i = 0; i < text.length(); i++) {
			String b = Character.toString(text.charAt(i));
			if (b.matches(Reg)) {
				result++;
			} else {
				ansiCharNumber += 0.8;
			}
		}
		return result + Math.round(ansiCharNumber) + 1;
	}

	/**
	 * 计算出串中汉字和非汉字按汉字计算的长度
	 */
	public static int getGbkWordLen(String value) {
		int allLength = value.length();
		int gbkLength = gbkCount(value);
		int len = allLength - gbkLength;
		return gbkLength + (len % 2 == 0 ? len / 2 : (len / 2 + 1));
	}

	/**
	 * 根据字号单元格式宽度，计算出占用行数
	 */
	public static int calcRows(int gbWordLength, int rowWords) {
		if (gbWordLength > rowWords) {
			int tempWords = gbWordLength;
			int cellWords = rowWords;
			return tempWords % cellWords == 0 ? tempWords / cellWords : tempWords / cellWords + 1;
		} else {
			return 1;
		}
	}

	/**
	 * 计算出某句话占用行数
	 */
	public static int formatWordsByEnter( String singleFieldValue, double cellWidth, double wordLength) throws Exception {
		int gbWordLength = getGbkWordLen(singleFieldValue);
		int rows = calcRows(gbWordLength, (int) (cellWidth / (wordLength)));
		return rows;
	}

	/**
	 * 循环每句，计算每句话是否需要换行操作，从而得到当前计算后的实际行数
	 */
	public static int getCurrentRows( String args, double cellWidth, double wordLength) throws Exception {
		int currentRows = 0;
		String[] fieldValueArray = args.split("\r\n|\r|\n");
		for (String jl : fieldValueArray) {
			currentRows += formatWordsByEnter(jl, cellWidth, wordLength);
		}

		return currentRows;
	}

	/**
	 * 选择最适合的
	 */
	public static boolean isFitCell(String args, double cellHeight, double cellWidth, String fontSize, double wordLength) throws Exception {
		int totalRows = getCurrentRows(args, cellWidth, wordLength);// 得到当前计算后的实际行数
		int currentRows = (int) (cellHeight / (wordLength + 2));// 所占行数
		if (cellHeight + 18 > wordLength * totalRows) {//单元格高度允许时
			return true;
		}
		if ("2".equals(fontSize)) {// 已选择最小字体
			return true;
		}
		if (totalRows < currentRows) {// 总行数小于改字号可以出现行数
			return true;
		}

		return false;
	}

	/**
	 * 循环定义的字体大小集,查找最合适的字体
	 */
	private static void formatWords(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row2, HSSFCell cell, HSSFCellStyle cellStyle, String args) throws Exception {
        //单元格 - 宽
//    	double cellWidth = getTotalWidth(cell) ;
        double cellWidth = getTotalWidth(cell) * 1.14;
//		float cellWidth = sheet.getColumnWidthInPixels(cell.getColumnIndex());
        //单元格 - 高
        double cellHeight = cell.getRow().getHeightInPoints();
//		float cellHeight = row2.getHeightInPoints();
        String newArgs = "";
        double wordLength = 0;
        if (fontSizeMap == null || (fontSizeMap != null && fontSizeMap.isEmpty())) {
            initMap();
        }
        for (String fontSize : fontSizeMap.keySet()) {
            wordLength = getFontSize(fontSize);
            if (isFitCell(args, cellHeight, cellWidth, fontSize, wordLength)) {//参数：目标字符串,单元格高、宽，字体大小、高度
                HSSFFont fontForJL = workbook.createFont();
                //设置文字大小
                double fontWidth = Short.parseShort(fontSize) / 72 * 96 * 2;
                fontForJL.setFontHeightInPoints(Short.parseShort(fontSize));
                System.out.println("单元格高是："+cellHeight);
                System.out.println("单元格宽是："+cellWidth);
                System.out.println("使用 的字体是："+fontSize);
                //居中方式
                cellStyle.setAlignment( CellStyle.ALIGN_LEFT);
                cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
                //自动换行
                cellStyle.setWrapText(true);
                //将字体样式赋给样式对象
                cellStyle.setFont(fontForJL);
                //为单元格添加样式
                cell.setCellStyle(cellStyle);
                //处理目标字符串  添加在换行位置添加 换行符+空格
                int howLong = (int)(cellWidth/wordLength);
                if(howLong==0){
                    newArgs = args;
                }else{
                    String[] fieldValueArray = args.split("\r\n|\r|\n");
                    for (String jl : fieldValueArray) {
                        newArgs = newArgs + gbkCountAddK(jl, howLong, fontSize) + "\n";
                    }
                }
                //填充内容
                cell.setCellValue(newArgs);
                break;
            }
        }
    }
	/**
     * 获取单元格的列数，如果是合并单元格，就获取总的列数
     */
    private static int getColNum(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        //判断该单元格是否是合并区域的内容
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
	/**
     * 获取单元格的总宽度（单位：像素）
     */
    private static double getTotalWidth(Cell cell) {
        int x = getColNum(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex());
        double totalWidthInPixels = 0;
        for (int i = 0; i < x; i++) {
            totalWidthInPixels += cell.getSheet().getColumnWidthInPixels(i + cell.getColumnIndex());
            System.out.println(i + cell.getColumnIndex());
        }
        return totalWidthInPixels;
    }
	/**
     * 为目标字符串插入：换行+进位
     */
    private static String gbkCountAddK(String text, int bbbb, String fontSize) {// bbbb目标首行长度
        String MB = "",Reg = "^[\u4e00-\u9fa5]{1}|[（）]{1}$",kong = fontSizeKongMap.get(fontSize);
        int result = 0 ;
        float ansiCharNumber = 0;
        Boolean isFrist = true;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg)) {
                result++;
            } else {
                ansiCharNumber += 0.8;
            }
            /**
             * 说明：特别指出，下面的“MB=MB+”部分存在一定长度的空格！
             */
            if((result + Math.round(ansiCharNumber) + 1) % bbbb < 1 && isFrist){
                MB = MB + "\n" + kong;
                isFrist = false;
                result = 0;
                ansiCharNumber = 0;
            }else if((result + Math.round(ansiCharNumber) + 1) % (bbbb-16) < 1 && !isFrist){//减去进位字符
                MB = MB + "\n" + kong;
                result = 0;
                ansiCharNumber = 0;
            }
            MB=MB+b;
        }
        return MB;
    }
    /**
     * 注意：下边定义空格，补齐相应的空格部分
     */
    @SuppressWarnings("serial")
	private static TreeMap<String, String> fontSizeKongMap = new TreeMap<String, String>(){{
//		put("2",  "");
//		put("3",  "                              ");
//		put("4",  "                                            ");
//		put("5",  "                             ");
        put("6",  "                               ");
        put("7",  "                          ");
        put("8",  "			                               ");
        put("9",  "			                                   ");
        put("10", "				                           ");
        put("11", "				                               ");
        put("12", "                                  ");
        put("14", "                                 ");
//		put("15", "                            ");
//		put("16", "                              ");
//		put("18", "                             ");
    }};
	public static TreeMap<String, Double> fontSizeMap = new TreeMap<String, Double>(
			new Comparator<String>() {
				public int compare(String obj1, String obj2) {
					double d1 = Double.valueOf(obj1);
					double d2 = Double.valueOf(obj2);

					if (d2 > d1)
						return 1;
					else if (d2 < d1)
						return -1;
					else
						return 0;
				}
			});

	public static void initMap() {
		fontSizeMap.put("2", 2.03);
		fontSizeMap.put("3", 3.03);
		fontSizeMap.put("4", 4.03);
		fontSizeMap.put("5", 5.56);
//		fontSizeMap.put("5.5", 5.97);
//		fontSizeMap.put("6.5", 7.32);
		fontSizeMap.put("6", 6.72);
		fontSizeMap.put("7", 7.67);
//		fontSizeMap.put("7.5", 8.48);
		fontSizeMap.put("8", 8.95);
//		fontSizeMap.put("8.5", 9.95);
		fontSizeMap.put("9", 10.07);
//		fontSizeMap.put("9.5", 10.75);
		fontSizeMap.put("10", 11.51);
		fontSizeMap.put("11", 12.40);
		fontSizeMap.put("12", 13.43);
		fontSizeMap.put("14", 18.00);
//		fontSizeMap.put("15", 20.0);
//		fontSizeMap.put("16", 21.0);
//		fontSizeMap.put("18", 24.0);
	}

	private static double getFontSize(String key) {
		return fontSizeMap.get(key);
	}
	/**
	 * 为目标字符串插入：换行+进位
	 */
	public static String gbkCountAddK(String text, int bbbb) {// bbbb目标首行长度
        String MB = "";
        String Reg = "^[\u4e00-\u9fa5]{1}|[（）]{1}$";
        int result = 0 ;
        float ansiCharNumber = 0;
        Boolean isFrist = true;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg)) {
                result++;
            } else {
                ansiCharNumber += 0.8;
            }
            /**
             * 说明：特别指出，下面的“MB=MB+”部分存在一定长度的空格！
             */
            if((result + Math.round(ansiCharNumber) + 1) % bbbb < 1 && isFrist){
                MB = MB + "\n				                                            ";
                isFrist = false;
                result = 0;
                ansiCharNumber = 0;
            }else if((result + Math.round(ansiCharNumber) + 1) % (bbbb-16) < 1 && !isFrist){//减去进位字符
                MB = MB + "\n				                                            ";
                result = 0;
                ansiCharNumber = 0;
            }
            MB=MB+b;
        }
        return MB;
    }	
}
