package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.pagemodel.mntpsj.base.ExpExcelUtils;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm2.YNTPFileExportBS;
import com.insigma.siis.local.pagemodel.yntp.YNTPGLPageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExpExcelPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("expExcel")
    public int expExcel() {
    	try {
    		HttpServletRequest request = this.request;
    		String dataArray = request.getParameter("dataArray");
    		String excelname = request.getParameter("excelname");
    		JSONArray  arrayObject = JSONArray.fromObject(dataArray);
    		List<List<String>> dataList = (List<List<String>>)arrayObject;
			String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			File f = new File(outputpath);
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(outputpath);
			}
			if(!f.isDirectory()){
				f.mkdirs();
			}
			File tplfilepath = new File(ExpExcelPageModel.class.getResource("./"+excelname+".xlsx").getPath());
			File newFile = tplfilepath;
			InputStream is = new FileInputStream(newFile);
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			int headRow=getExcelRealRow(sheet);		
			CellStyle cellstyle = getCellStyleCL(workbook, "楷体_GB2312");
			for(int rowi=headRow;rowi<dataList.size()+headRow;rowi++){
				List<String> cellList = dataList.get(rowi-headRow);
				Row row = sheet.createRow(rowi);
				for(int celli=0;celli<cellList.size();celli++){
					String cellV = cellList.get(celli);
					Cell cell = row.createCell(celli);
					cell.setCellStyle(cellstyle);	
					sheet.setColumnWidth(celli, 5200);
					if(cellV!=null){
						cell.setCellValue(cellV);
					}
				}
			}
			String downloadName = excelname+".xlsx";
			String downloadPath = outputpath+downloadName;
			OutputStream fos = new  FileOutputStream(new File(downloadPath));
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        bos.flush();
	        fos.close();
	        bos.close();
	        workbook.close();
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	
	
	//获取excel已存在行数
		public int getExcelRealRow(Sheet sheet) {
	        int i;
	        for (i = 0; i <= sheet.getLastRowNum(); i++) {
	            Row r = sheet.getRow(i);
	            if (r == null) {
	               break;
	            }
	        }
	        return i;
	    }
    /**
	 * 带上下左右边框 正文正文居中 上下居中
	 * 仿宋_GB2312 楷体_GB2312
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleCL(Workbook workbook,String fontName){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setBorderTop(BorderStyle.THIN);//上边框  		
		style.setBorderBottom(BorderStyle.THIN);//下边框  		
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		Font font =workbook.createFont();  
		font.setFontName(fontName);  
		font.setFontHeightInPoints((short) 11);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	public static CellStyle getCellStyleCL2(XSSFWorkbook workbook,String fontName,String ground){
		XSSFCellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setBorderTop(BorderStyle.THIN);//上边框  		
		style.setBorderBottom(BorderStyle.THIN);//下边框  		
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //设置填充方案
		if(ground.equals("B")) {
			style.setFillForegroundColor(new XSSFColor(new Color(202,255,255)));  //设置填充颜色
		}else if(ground.equals("R")) {
			style.setFillForegroundColor(new XSSFColor(new Color(255,193,224)));  //设置填充颜色
		}else if(ground.equals("T")) {
			style.setFillForegroundColor(new XSSFColor(new Color(202,232,234)));  //设置填充颜色
		}else{
			style.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));  //设置填充颜色
		}
		Font font =workbook.createFont();  
		font.setFontName(fontName);  
		font.setFontHeightInPoints((short) 11);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	
	private List<List<String>> backcolorlist(List<Object> colorList) {
		List<List<String>> color=new ArrayList<List<String>>();
		if(colorList.get(0).toString().equals("BZRYBDGW")) {
			List<String> allcolorBR=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorBR.add("w");	
			}
			for(int j=0;j<1;j++) {
				allcolorBR.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorBR.add("B");	
			}
			for(int j=0;j<5;j++) {
				allcolorBR.add("R");	
			}
			List<String> allcolorBW=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorBW.add("W");	
			}
			for(int j=0;j<1;j++) {
				allcolorBW.add("B");	
			}
			for(int j=0;j<6;j++) {
				allcolorBW.add("B");	
			}
			for(int j=0;j<5;j++) {
				allcolorBW.add("B");	
			}
			List<String> allcolorWR=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorWR.add("W");	
			}
			for(int j=0;j<1;j++) {
				allcolorWR.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorWR.add("W");	
			}
			for(int j=0;j<5;j++) {
				allcolorWR.add("R");	
			}
			List<String> allcolorWW=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorWW.add("W");	
			}
			for(int j=0;j<1;j++) {
				allcolorWW.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorWW.add("W");	
			}
			for(int j=0;j<5;j++) {
				allcolorWW.add("W");	
			}
		for(int i=1;i<colorList.size();) {
			String left= colorList.get(i).toString();
			String right=colorList.get(i+1).toString();
			if(left.equals("B")) {
				if(right.equals("R")) {
					color.add(allcolorBR);
				}else {
					color.add(allcolorBW);
				}
			}else {
				if(right.equals("R")) {
					color.add(allcolorWR);
				}else{
					color.add(allcolorWW);
				}
			}
			i=i+2;
		}
		}else if(colorList.get(0).toString().equals("BZRYBD")){
			List<String> allcolorBR=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorBR.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorBR.add("B");	
			}
			for(int j=0;j<7;j++) {
				allcolorBR.add("R");	
			}
			List<String> allcolorBW=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorBW.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorBW.add("B");	
			}
			for(int j=0;j<7;j++) {
				allcolorBW.add("W");	
			}
			List<String> allcolorWR=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorWR.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorWR.add("W");	
			}
			for(int j=0;j<7;j++) {
				allcolorWR.add("R");	
			}
			List<String> allcolorWW=new ArrayList<String>();
			for(int j=0;j<2;j++) {
				allcolorWW.add("W");	
			}
			for(int j=0;j<6;j++) {
				allcolorWW.add("W");	
			}
			for(int j=0;j<7;j++) {
				allcolorWW.add("W");	
			}
			for(int i=1;i<colorList.size();) {
				String left= colorList.get(i).toString();
				String right=colorList.get(i+1).toString();	
				if(left.equals("B")) {
					if(right.equals("R")) {
						
						color.add(allcolorBR);
					}else{
						
						color.add(allcolorBW);
					}
				}else {
					if(right.equals("R")) {
						
						color.add(allcolorWR);
					}else{
						
						color.add(allcolorWW);
					}
				}
				i=i+2;
			}
		}
		return color;
	}
	
	@PageEvent("expExcel1")
    public int expExcel1() {
		try {
    		HttpServletRequest request = this.request;
    		String dataArray = request.getParameter("dataArray");
    		String excelname = request.getParameter("excelname");
    		JSONArray  arrayObject = JSONArray.fromObject(dataArray);
    		List<List<String>> dataList = (List<List<String>>)arrayObject;
			String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			File f = new File(outputpath);
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(outputpath);
			}
			if(!f.isDirectory()){
				f.mkdirs();
			}
			File tplfilepath = new File(ExpExcelPageModel.class.getResource("./"+excelname+".xlsx").getPath());
			File newFile = tplfilepath;
			InputStream is = new FileInputStream(newFile);
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			int headRow=getExcelRealRow(sheet);		
			int maxlong=dataList.get(0).size();
			List<Integer> listy=new ArrayList<Integer>();
			List<Integer> listx=new ArrayList<Integer>();
			CellStyle cellstyle = getCellStyleCL(workbook, "楷体_GB2312");
			for(int rowi=headRow;rowi<dataList.size()+headRow;rowi++){
				List<String> cellListx = dataList.get(rowi-headRow);
				List<String> cellList =new ArrayList<String>();
				if(cellListx.size()<maxlong) {
					for(int i=0;i<maxlong-cellListx.size();i++) 
					{
					cellList.add("x");//标记为需要合并
					listy.add(rowi);
					listx.add(i);
					}
					cellList.addAll(cellListx);
				}else {
					cellList.addAll(cellListx);
				}
				Row row = sheet.createRow(rowi);
				for(int celli=0;celli<cellList.size();celli++){
					String cellV = cellList.get(celli);
					Cell cell = row.createCell(celli);
					cell.setCellStyle(cellstyle);	
					sheet.setColumnWidth(celli, 5200);
					if(cellV!=null){
						cell.setCellValue(cellV);
					}

				}	
			}
			//System.out.print(listx);
			//System.out.print(listy);
			List<Integer> re=new ArrayList<Integer>();
			re.add(-1);
			for(int j=0;j<re.size();j++) {
				for(int i =0;i<listx.size();i++) {
					if(listx.get(i)!=re.get(j)) {
						re.add(listx.get(i));
						break;
					}
				}
			}
			//System.out.print(re);
			for(int i =1;i<re.size();i++) {
				List<Integer> sub=new ArrayList<Integer>();
				for(int j=0;j<listx.size();j++) {
					if(listx.get(j)==re.get(i)) {
						sub.add(j);
					}
				}
				//System.out.print(sub);
				int a=-1;
				int b=-1;
				for(int k=0;k<sub.size()-1;k++) {
					if(k==0) {
						a=listy.get(sub.get(k));
					}
					if(listy.get(sub.get(k)+1)-listy.get(sub.get(k))!=1) {
						b=listy.get(sub.get(k));
						sheet.addMergedRegion(new CellRangeAddress(a-1, b, i-1, i-1));
						a=listy.get(sub.get(k)+1);
					}
					if(k+1==sub.size()-1) {
						sheet.addMergedRegion(new CellRangeAddress(a-1, listy.get(sub.get(k+1)), i-1, i-1));
					}
			
				}
			}	
			String downloadName = excelname+".xlsx";
			String downloadPath = outputpath+downloadName;
			OutputStream fos = new  FileOutputStream(new File(downloadPath));
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        bos.flush();
	        fos.close();
	        bos.close();
	        workbook.close();
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	
	
	
	@PageEvent("expExcel2")
    public int expExcel2() {
		try {
    		HttpServletRequest request = this.request;
    		String dataArray = request.getParameter("dataArray");
    		String excelname = request.getParameter("excelname");
    		String colorArray = request.getParameter("colorArray");
    		JSONArray  arrayObject = JSONArray.fromObject(dataArray);
    		JSONArray  colorObject = JSONArray.fromObject(colorArray);
    		List<List<String>> dataList = (List<List<String>>)arrayObject;
    		List<Object> colorList = (List<Object>)colorObject;
    		List<List<String>> color=backcolorlist(colorList);
			String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			File f = new File(outputpath);
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(outputpath);
			}
			if(!f.isDirectory()){
				f.mkdirs();
			}
			File tplfilepath = new File(ExpExcelPageModel.class.getResource("./"+excelname+".xlsx").getPath());
			File newFile = tplfilepath;
			InputStream is = new FileInputStream(newFile);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			int headRow=getExcelRealRow(sheet);	
			int maxlong;
			maxlong=dataList.get(0).size();	
			List<Integer> listy=new ArrayList<Integer>();
			List<Integer> listy2=new ArrayList<Integer>();
			List<Integer> listy3=new ArrayList<Integer>();
			CellStyle cellstyleR = getCellStyleCL2(workbook, "楷体_GB2312","R");
			CellStyle cellstyleB = getCellStyleCL2(workbook, "楷体_GB2312","B");
			CellStyle cellstyleW = getCellStyleCL2(workbook, "楷体_GB2312","");
			for(int rowi=headRow;rowi<dataList.size()+headRow;rowi++){
				List<String> cellListx = dataList.get(rowi-headRow);
				List<String> cellList =new ArrayList<String>();
				if(cellListx.size()<maxlong) {
					for(int i=0;i<maxlong-cellListx.size();i++) 
					{
					cellList.add("x");//标记为需要合并
					if(i==0) {
					listy.add(rowi);
						}
					if(i==1) {
						listy2.add(rowi);	
						}
					if(i==2) {
						listy3.add(rowi);	
						}
					}
					cellList.addAll(cellListx);
				}else {
					cellList.addAll(cellListx);
				}
				
				
				Row row = sheet.createRow(rowi);
				List<String> back=color.get(rowi-headRow);
				for(int celli=0;celli<cellList.size();celli++){
					String ground=back.get(celli);
					String cellV = cellList.get(celli);
					Cell cell = row.createCell(celli);
					if(ground.equals("B")) {
						cell.setCellStyle(cellstyleB);	  //设置填充颜色
					}else if(ground.equals("R")) {
						cell.setCellStyle(cellstyleR);  //设置填充颜色
					}else {
						cell.setCellStyle(cellstyleW);  //设置填充颜色
					}
						sheet.setColumnWidth(celli, 5200);
						
					if(cellV!=null){
						cell.setCellValue(cellV);
					}

				}
				
			}
			int a=-1;
			int b=-1;
			for(int i=0;i<listy.size()-1;i++) {
				if(i==0) {
					a=listy.get(i);
				}
				if(listy.get(i+1)-listy.get(i)!=1) {
					b=listy.get(i);
					sheet.addMergedRegion(new CellRangeAddress(a-1, b, 0, 0));
					a=listy.get(i+1);
				}
				if(i+1==listy.size()-1) {
					sheet.addMergedRegion(new CellRangeAddress(a-1, listy.get(i+1), 0, 0));
				}
		
			}
			if(listy.size()==1) {
				sheet.addMergedRegion(new CellRangeAddress(listy.get(0)-1, listy.get(0), 2, 2));
			}
			int a2=-1;
			int b2=-1;
			for(int i=0;i<listy2.size()-1;i++) {
				if(i==0) {
					a2=listy2.get(i);
				}
				if(listy2.get(i+1)-listy2.get(i)!=1) {
					b2=listy2.get(i);
					sheet.addMergedRegion(new CellRangeAddress(a2-1, b2, 1, 1));
					a2=listy2.get(i+1);
				}
				if(i+1==listy2.size()-1) {
					sheet.addMergedRegion(new CellRangeAddress(a2-1, listy2.get(i+1), 1, 1));
				}
			}
			if(listy2.size()==1) {
				sheet.addMergedRegion(new CellRangeAddress(listy2.get(0)-1, listy2.get(0), 2, 2));
			}
			int a3=-1;
			int b3=-1;
			for(int i=0;i<listy3.size()-1;i++) {
				if(i==0) {
					a3=listy3.get(i);
				}
				if(listy3.get(i+1)-listy3.get(i)!=1) {
					b3=listy3.get(i);
					sheet.addMergedRegion(new CellRangeAddress(a3-1, b3, 2, 2));
					a3=listy3.get(i+1);
				}
				if(i+1==listy3.size()-1) {
					sheet.addMergedRegion(new CellRangeAddress(a3-1, listy3.get(i+1), 2, 2));
				}
			}
			if(listy3.size()==1) {
				sheet.addMergedRegion(new CellRangeAddress(listy3.get(0)-1, listy3.get(0), 2, 2));
			}
			String downloadName = excelname+".xlsx";
			String downloadPath = outputpath+downloadName;
			OutputStream fos = new  FileOutputStream(new File(downloadPath));
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        bos.flush();
	        fos.close();
	        bos.close();
	        workbook.close();
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	
	
	@PageEvent("expExcel3")
    public int expExcel3() {
		try {
    		HttpServletRequest request = this.request;
    		String dataArray = request.getParameter("dataArray");
    		String excelname = request.getParameter("excelname");
    		String colorArray = request.getParameter("colorArray");
    		JSONArray  arrayObject = JSONArray.fromObject(dataArray);
    		JSONArray  colorObject = JSONArray.fromObject(colorArray);
    		List<List<String>> dataList = (List<List<String>>)arrayObject;
    		List<Object> colorList = (List<Object>)colorObject;
    		List<List<String>> color=backcolorlist(colorList);
			String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			File f = new File(outputpath);
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(outputpath);
			}
			if(!f.isDirectory()){
				f.mkdirs();
			}
			File tplfilepath = new File(ExpExcelPageModel.class.getResource("./"+excelname+".xlsx").getPath());
			File newFile = tplfilepath;
			InputStream is = new FileInputStream(newFile);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			int headRow=getExcelRealRow(sheet);	
			int maxlong=14;
			int blog=-1;
			int blog2=-1;
			List<Integer> listy=new ArrayList<Integer>();
			List<Integer> listy2=new ArrayList<Integer>();
			List<Integer> listy3=new ArrayList<Integer>();
			CellStyle cellstyleR = getCellStyleCL2(workbook, "楷体_GB2312","R");
			CellStyle cellstyleB = getCellStyleCL2(workbook, "楷体_GB2312","B");
			CellStyle cellstyleW = getCellStyleCL2(workbook, "楷体_GB2312","");
			for(int i=0;i<dataList.size();i++) {
				List<String> cellLista=dataList.get(i);
				if(cellLista.size()==maxlong) {
					blog=i;
					break;
				}
			}
			for(int rowi=headRow;rowi<dataList.size()+headRow;rowi++){
				List<String> cellLista=dataList.get(rowi-headRow);
				List<String> cellListx = new ArrayList<String>();
				if(maxlong-cellLista.size()==1) {
					if(cellLista.get(0).equals("党委")||cellLista.get(0).equals("人大")||cellLista.get(0).equals("政府")||cellLista.get(0).equals("政协")||cellLista.get(0).equals("法院")||cellLista.get(0).equals("检察院")) {
						cellListx.addAll(cellLista);
					}else {
						for(int p=0;p<cellLista.size();p++) {
							cellListx.add(cellLista.get(p));
							if(p==0) {
								cellListx.add(" ");
							}
						}
					}
				}else {
					cellListx.addAll(cellLista);
				}
				List<String> cellList =new ArrayList<String>();
				if(cellListx.size()<maxlong) {
					for(int i=0;i<maxlong-cellListx.size();i++) 
					{
					cellList.add("x");//标记为需要合并
					if(i==0) {
						listy.add(rowi);
						}
					if(i==1) {
						listy2.add(rowi);	
						}
					if(i==2) {
						listy3.add(rowi);	
						}
					}
					cellList.addAll(cellListx);
				}else {
					cellList.addAll(cellListx);
				}
				if((rowi-headRow)==blog) {
					blog2=listy.size();
				}
				Row row = sheet.createRow(rowi);
				List<String> back=color.get(rowi-headRow);
				for(int celli=0;celli<cellList.size();celli++){
					String ground=back.get(celli);
					String cellV = cellList.get(celli);
					Cell cell = row.createCell(celli);
					if(ground.equals("B")) {
						cell.setCellStyle(cellstyleB);	  //设置填充颜色
					}else if(ground.equals("R")) {
						cell.setCellStyle(cellstyleR);  //设置填充颜色
					}else {
						cell.setCellStyle(cellstyleW);  //设置填充颜色
					}
					if(celli==5||celli==6){
						sheet.setColumnWidth(celli, 3500);
					}else if(celli==3||celli==4) {
						sheet.setColumnWidth(celli, 2500);
					}else {
						sheet.setColumnWidth(celli, 5200);
					}
					if(cellV!=null){
						cell.setCellValue(cellV);
					}

				}
				if(blog==-1) {
					if(rowi-headRow<dataList.size()-1) {
						if(cellList.get(0)!="x"&&cellList.get(1)==" "&&dataList.get(rowi-headRow+1).size()==13) 
						{
							sheet.addMergedRegion(new CellRangeAddress(rowi, rowi, 0, 1));
						}
					}else if(cellList.get(0)!="x"&&cellList.get(1)==" ") {
						sheet.addMergedRegion(new CellRangeAddress(rowi, rowi, 0, 1));
					}
				}else {
					if(cellList.get(0)!="x"&&cellList.get(1)==" "&&(dataList.get(rowi-headRow+1).size()==13||dataList.get(rowi-headRow+1).size()==14)&&(rowi-headRow)<blog) 
					{
						sheet.addMergedRegion(new CellRangeAddress(rowi, rowi, 0, 1));
					}
				}	
				
			}
			if(blog==-1) {
				int a=-1;
				int b=-1;
				for(int i=0;i<listy.size()-1;i++) {
					if(i==0) {
						a=listy.get(i);
					}
					if(listy.get(i+1)-listy.get(i)!=1) {
						b=listy.get(i);
						sheet.addMergedRegion(new CellRangeAddress(a-1, b, 0, 1));
						a=listy.get(i+1);
					}
					if(i+1==listy.size()-1) {
						sheet.addMergedRegion(new CellRangeAddress(a-1, listy.get(i+1), 0, 1));
					}
				}
			}else {
				int ax=-1;
				int bx=-1;
				for(int i=0;i<blog2;i++) {
					if(i==0) {
						ax=listy.get(i);
					}
					if(listy.get(i+1)-listy.get(i)!=1) {
						bx=listy.get(i);
						sheet.addMergedRegion(new CellRangeAddress(ax-1, bx, 0, 1));
						ax=listy.get(i+1);
					}
					if(i+1==listy.size()-1) {
						sheet.addMergedRegion(new CellRangeAddress(ax-1, listy.get(i+1), 0, 1));
					}
				}
				int a=-1;
				int b=-1;
				for(int i=blog2;i<listy.size()-1;i++) {
					if(i==blog2) {
						a=listy.get(i);
					}
					if(listy.get(i+1)-listy.get(i)!=1) {
						b=listy.get(i);
						sheet.addMergedRegion(new CellRangeAddress(a-1, b, 0, 0));
						a=listy.get(i+1);
					}
					if(i+1==listy.size()-1) {
						sheet.addMergedRegion(new CellRangeAddress(a-1, listy.get(i+1), 0, 0));
					}
				}
				if(listy.size()==1) {
					sheet.addMergedRegion(new CellRangeAddress(listy.get(0)-1, listy.get(0), 0, 0));
				}
				int a2=-1;
				int b2=-1;
				for(int i=blog2;i<listy2.size()-1;i++) {
					if(i==blog2) {
						a2=listy2.get(i);
					}
					if(listy2.get(i+1)-listy2.get(i)!=1) {
						b2=listy2.get(i);
						sheet.addMergedRegion(new CellRangeAddress(a2-1, b2, 1, 1));
						a2=listy2.get(i+1);
					}
					if(i+1==listy2.size()-1) {
						sheet.addMergedRegion(new CellRangeAddress(a2-1, listy2.get(i+1), 1, 1));
					}
				}
			}
			int a3=-1;
			int b3=-1;
			for(int i=0;i<listy3.size()-1;i++) {
				if(i==0) {
					a3=listy3.get(i);
				}
				if(listy3.get(i+1)-listy3.get(i)!=1) {
					b3=listy3.get(i);
					sheet.addMergedRegion(new CellRangeAddress(a3-1, b3, 2, 2));
					a3=listy3.get(i+1);
				}
				if(i+1==listy3.size()-1) {
					sheet.addMergedRegion(new CellRangeAddress(a3-1, listy3.get(i+1), 2, 2));
				}
			}
			if(listy3.size()==1) {
				sheet.addMergedRegion(new CellRangeAddress(listy3.get(0)-1, listy3.get(0), 2, 2));
			}
			String downloadName = excelname+".xlsx";
			String downloadPath = outputpath+downloadName;
			OutputStream fos = new  FileOutputStream(new File(downloadPath));
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        bos.flush();
	        fos.close();
	        bos.close();
	        workbook.close();
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	
	@PageEvent("expExcelnew")
    public int expExcelnew() {
		try {
    		HttpServletRequest request = this.request;
    		String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			File f = new File(outputpath);
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(outputpath);
			}
			if(!f.isDirectory()){
				f.mkdirs();
			}
    		String dataArray = request.getParameter("dataArray");
    		String colorArray = request.getParameter("colorArray");
    		String spansArray = request.getParameter("spansArray");
    		String excelname = request.getParameter("excelname");
    		JSONArray  arrayObject = JSONArray.fromObject(dataArray);
    		JSONArray  colorObject = JSONArray.fromObject(colorArray);
    		JSONArray  spansObject = JSONArray.fromObject(spansArray);
    		List<List<String>> dataList = (List<List<String>>)arrayObject;
    		List<List<String>> colorList = (List<List<String>>)colorObject;
    		List<List<List<String>>> spansList = (List<List<List<String>>> )spansObject;
    		List<CellRangeAddress> celllist=new ArrayList<CellRangeAddress>();
    	     //创建Excel文件薄
    		XSSFWorkbook workbook=new XSSFWorkbook();
            //创建工作表sheeet
            XSSFSheet sheet=workbook.createSheet();
			CellStyle cellstyleR = getCellStyleCL2(workbook, "楷体_GB2312","R");
			CellStyle cellstyleB = getCellStyleCL2(workbook, "楷体_GB2312","B");
			CellStyle cellstyleT = getCellStyleCL2(workbook, "楷体_GB2312","T");
			CellStyle cellstyleW = getCellStyleCL2(workbook, "楷体_GB2312","");
    		int hangi=0;
    		List<Integer> hang = new ArrayList<Integer>();
    		List<Integer> hangx=new ArrayList<Integer>();
    		for(int i=0;i<spansList.size();i++) {
    			int lie =0;
    			Row row = sheet.createRow(i);
    			List<String> dataListx=dataList.get(i);//文本内容
    			List<String> back=colorList.get(i);//背景色
    			List<List<String>> spansList2=spansList.get(i);//合并单元格
    			if(hangx!=null&&hangx.size()>0) {
    				for(int k=hangx.size();k>0;k--) {
    					if(i<=hangx.get(k-1)) {
    						hangi=hang.get(k-1);
    						break;
    					}
    					if(k-1==0) {
    						hangx.removeAll(hangx);
    						hang.removeAll(hang);	
    						hangi=0;
    						break;
    					}
    				}
    			}
    			for(int j=0;j<spansList2.size();j++) {
    				String aa= String.valueOf(spansList2.get(j).get(0));//多少行
    				String bb= String.valueOf(spansList2.get(j).get(1));//多少列
    				int aai=Integer.valueOf(aa)-1;
    				int bbi=Integer.valueOf(bb)-1;
    				Cell cell = row.createCell(j+lie+hangi);
    				String ground=back.get(j);
    				String cellV = dataListx.get(j);
					sheet.setColumnWidth(j+lie+hangi, 5200);	
					//System.out.println(i+","+(i+aai)+","+(j+lie+hangi)+","+(j+bbi+lie+hangi));
					if(cellV!=null){
  						cell.setCellValue(cellV);
  					}
				    if(aai!=0||bbi!=0) {
				    	CellRangeAddress CellRangeAddress=new CellRangeAddress(i, (i+aai), (j+lie+hangi), (j+bbi+lie+hangi));
  				    	sheet.addMergedRegion(CellRangeAddress);
  				    	celllist.add(CellRangeAddress);
    				}
					if(ground.equals("B")) {
						cell.setCellStyle(cellstyleB);	  //设置填充颜色
					}else if(ground.equals("R")) {
						cell.setCellStyle(cellstyleR);  //设置填充颜色
					}else if(ground.equals("T")){
						cell.setCellStyle(cellstyleT); 
					}else{
						cell.setCellStyle(cellstyleW);  //设置填充颜色
					}
					if(aai!=0&&j!=spansList2.size()-1) {
						if(hangx!=null&&hangx.size()>0) {
						if(i+aai>hangx.get(hangx.size()-1)) {
							for(int x=hangx.size();x>0;x--) {
								if(i+aai>hangx.get(x-1)) {
									hangx.add((i+aai));
									hang.add(hang.get(x-1-1)+(bbi+1));
								}
							}
						}else {
						hangx.add((i+aai));
						hang.add(hang.get(hang.size()-1)+(bbi+1));
						}
						}else {
						hangx.add(i+aai);
						hang.add(bbi+1);
						}
					}

    				if(bbi!=0) {
    					lie=lie+bbi;
    				}
    			}
    		  }	
    		for(int i=0;i<celllist.size();i++) {
    			setBorder(celllist.get(i),sheet,workbook);
    		}
			  String downloadName = excelname+".xlsx"; 
			  String downloadPath =outputpath+downloadName; 
			  OutputStream fos = new FileOutputStream(new File(downloadPath)); BufferedOutputStream bos = new
		      BufferedOutputStream(fos); 
			  workbook.write(bos); 
			  bos.flush(); 
		      fos.close();
		      bos.close();
		  	  workbook.close(); 
			  String downloadUUID =UUID.randomUUID().toString(); request.getSession().setAttribute(downloadUUID,new String[]{downloadPath,downloadName}); 
		      this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			  this.getExecuteSG().addExecuteCode("downloadByUUID();");
			 
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	@PageEvent("expExceldata")
    public int expExceldata() {
		try {
			HttpServletRequest request = this.request;
			String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			String downloadUUID = ExpExcelUtils.expExceldata(request, outputpath);
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	public void setBorder(CellRangeAddress cellRangeAddress, Sheet sheet,  
            Workbook wb) throws Exception {  
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, wb);  
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, wb);  
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, wb);  
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, wb);  
          
}
	private int indexOf(String string) {
		// TODO Auto-generated method stub
		return 0;
	}
}
