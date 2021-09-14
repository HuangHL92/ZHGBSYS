package com.insigma.siis.local.pagemodel.gbmc;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.gbmc.expword.ExpTPBRMB;
import com.insigma.siis.local.pagemodel.gbmc.shcl.ExpSHCLBS;
import com.insigma.siis.local.pagemodel.gbmc.tpl.YNTPExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YNTPFileExportBS {
	final static Map<String,String> TPHJ = new HashMap<String, String>(){
		{
			put("TPHJ1","酝酿");
			put("TPHJ2","市委书记专题会议成员酝酿");
			put("TPHJ3","部务会议");
			put("TPHJ4","市委书记专题会议");
			put("TPHJ5","市委常委会");
		}
	};
	final static Map<String,String> SHEET_NAME = new HashMap<String, String>(){
		{
			put("TPHJ1","酝酿");
			put("TPHJ2","市委书记专题会议成员酝酿");
			put("TPHJ3","部会");
			put("TPHJ4","书记会");
			put("TPHJ5","常委会");
		}
	};
	public String tpbSql;
	private static String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		return upload_file;
	}
	public final static String HZBPATH = getPath();
	/**
	 * 文件名
	 */
	final private List<String> outputFileNameList = new ArrayList<String>();
	
	public List<String> getOutputFileNameList() {
		return outputFileNameList;
	}
	/**
	 * 文件输出路径
	 */
	private String outputpath = HZBPATH+"zhgboutputfiles/";
	public ExpSHCLBS expSHCLBS;
	public YNTPFileExportBS(ExpSHCLBS expSHCLBS2) {
		this.expSHCLBS = expSHCLBS2;
	}
	public String getOutputpath() {
		return outputpath;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
	}
	/**
	 * 干部调配建议方案  环节1和环节2
	 * @throws Exception 
	 * @throws RadowException 
	 * @throws Exception 
	 */
	public void exportGBTP(String ynId,String yntype) throws Exception {
		File tplfilepath = null;
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/干部调配建议方案环节12.xlsx").getPath());;
		}else{
			tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/干部调配建议方案环节345.xlsx").getPath());;
		}
		//tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/干部调配建议方案.xlsx").getPath());;
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件

		Workbook workbook = new XSSFWorkbook(is);      //默认读取2003版的Excel
		//5个环节
		this.writeSheetData(workbook,0,yntype,ynId,false);
		/*this.writeSheetData(workbook,1,"TPHJ2",ynId,false);
		this.writeSheetData(workbook,2,"TPHJ3",ynId,false);
		this.writeSheetData(workbook,3,"TPHJ4",ynId,false);
		this.writeSheetData(workbook,4,"TPHJ5",ynId,false);*/


		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String hjstr = TPHJ.get(yntype);
		String fn = "干部调配建议方案"+hjstr+".xlsx";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}

        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));


        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);

        bos.flush();
        fos.close();
        bos.close();
	}


	/**
	 * 干部调配建议方案  导出上会调配表
	 * @param expSHCLBS
	 * @throws Exception
	 * @throws RadowException
	 * @throws Exception
	 */
	public void exportBuHui(String ynId) throws Exception {
		File tplfilepath = null;
		tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/干部调配建议方案2.xlsx").getPath());;
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new XSSFWorkbook(is);      //默认读取2003版的Excel
		//5个环节
		this.writeSheetData(workbook,0,"TPHJ3",ynId,true);
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "干部调配建议方案（部会）.xlsx";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));


        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	/**
	 * 干部调配建议方案  导出上会调配表
	 * @throws Exception 
	 * @throws RadowException 
	 * @throws Exception 
	 */
	public void exportShuJiHui(String ynId) throws Exception {
		File tplfilepath = null;
		tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/干部调配建议方案3.xlsx").getPath());;
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new XSSFWorkbook(is);      //默认读取2003版的Excel
		//5个环节
		this.writeSheetData(workbook,0,"TPHJ4",ynId,true);
		this.writeSheetData(workbook,1,"TPHJ5",ynId,true);
		
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "干部调配建议方案（书记会常委会）.xlsx";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));


        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	
	private void writeSheetData(Workbook workbook, int sheetIndex, String yntype,final String ynId,boolean expLrmx) throws Exception {
		
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		workbook.setSheetName(sheetIndex, SHEET_NAME.get(yntype));
		//设置表头
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		sheet.getRow(3).getCell(0).setCellValue("（"+TPHJ.get(yntype)+"）");
		sheet.getRow(4).getCell(0).setCellValue(HBUtil.getValueFromTab("info0y||'年'||info0m||'月'||info0d||'日'", "js2_yntp_info", "yn_id='"+ynId+"' and info01='"+yntype+"'"));
		
		String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
				+ " tp0105, tp0106, tp0107 tp0107 from TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yntype+"' order by sortnum asc ";
		if(this.tpbSql!=null&&!"".equals(this.tpbSql)){
			sql = this.tpbSql;
		}
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
		rs = stmt.executeQuery(sql);
		//String tmp = null;
		CellStyle styleBT1 = YNTPExcelUtil.getYiJiBiaoTiStyle(workbook);
		CellStyle styleBT2 = YNTPExcelUtil.getErJiBiaoTiStyle(workbook);
		CellStyle styleZWC = YNTPExcelUtil.getCellStyleC(workbook);
		CellStyle styleZWL = YNTPExcelUtil.getCellStyleL(workbook);
		CellStyle styleDate = YNTPExcelUtil.getCellStyleDate(workbook);
		CellStyle bstyleBT1 = YNTPExcelUtil.getYiJiBiaoTiStyle(workbook);
		CellStyle bstyleBT2 = YNTPExcelUtil.getErJiBiaoTiStyle(workbook);
		CellStyle bstyleZWC = YNTPExcelUtil.getCellStyleC(workbook);
		CellStyle bstyleZWL = YNTPExcelUtil.getCellStyleL(workbook);
		CellStyle bstyleDate = YNTPExcelUtil.getCellStyleDate(workbook);
		bstyleBT1.setBorderBottom(BorderStyle.THIN);
		bstyleBT2.setBorderBottom(BorderStyle.THIN);
		bstyleZWC.setBorderBottom(BorderStyle.THIN);
		bstyleZWL.setBorderBottom(BorderStyle.THIN);
		bstyleDate.setBorderBottom(BorderStyle.THIN);
		int rowIndex = 6;
		int i=0;//正文序号
		float page_height = 0f;//页高
		float current_row_height=0;//当前行高
		List<Integer> content_mark = new ArrayList<Integer>();//true标识正文 false表示行高
		//标题合并列 
		int biaoticol = 5;
		int biaoticolLeft = biaoticol+1;
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			biaoticol = 7;
			biaoticolLeft = biaoticol;
		}
		//人员信息 用于导出上会材料
		final ArrayList<String> a0000Info = new ArrayList<String>();
		
		while (rs.next()) {
			
			if(rs.isLast()){
				styleBT1 = YNTPExcelUtil.getYiJiBiaoTiStyle(workbook);
				styleBT2 = YNTPExcelUtil.getErJiBiaoTiStyle(workbook);
				styleZWC = YNTPExcelUtil.getCellStyleC(workbook);
				styleZWL = YNTPExcelUtil.getCellStyleL(workbook);
				styleDate = YNTPExcelUtil.getCellStyleDate(workbook);
				styleBT1.setBorderBottom(BorderStyle.THIN);
				styleBT2.setBorderBottom(BorderStyle.THIN);
				styleZWC.setBorderBottom(BorderStyle.THIN);
				styleZWL.setBorderBottom(BorderStyle.THIN);
				styleDate.setBorderBottom(BorderStyle.THIN);
			}
			
			
			
			Object value = rs.getObject("type")==null?"":rs.getObject("type").toString();
			
			Row row = YNTPExcelUtil.insertRow(sheet,rowIndex);
			
			
			
			
			
			if("1".equals(value)){//一级标题
				Cell cell = row.createCell(0);//序号
				cell.setCellStyle(styleZWC);//主要是打印的左边宽
				value = rs.getObject("tp0101");
				cell = YNTPExcelUtil.mergeRegionAndSetValue(sheet, 1, biaoticol, rowIndex);
				current_row_height = YNTPExcelUtil.getBiaoTiHeight(value==null?"":value.toString(),yntype);//行高
				page_height = page_height + current_row_height;//计算页高
				row.setHeightInPoints(current_row_height);
				YNTPExcelUtil.setCellValue(cell, styleBT1, value);
				
				cell = row.createCell(biaoticolLeft);//右边框
				cell.setCellStyle(styleBT1);
				
				content_mark.add(1);
			}else if("2".equals(value)){//二级标题
				Cell cell = row.createCell(0);//序号
				cell.setCellStyle(styleZWC);//主要是打印的左边宽
				value = rs.getObject("tp0101");
				cell = YNTPExcelUtil.mergeRegionAndSetValue(sheet, 1, biaoticol, rowIndex);//合并单元格
				current_row_height = YNTPExcelUtil.getBiaoTiHeight(value==null?"":value.toString(),yntype);//行高
				page_height = page_height + current_row_height;//计算页高
				row.setHeightInPoints(current_row_height);
				YNTPExcelUtil.setCellValue(cell, styleBT2, value);
				
				
				cell = row.createCell(biaoticolLeft);//右边框
				cell.setCellStyle(styleBT2);
				
				content_mark.add(2);
			}else{//正文
				i++;
				String a0000 = rs.getObject("a0000")==null?"":rs.getObject("a0000").toString();
				a0000Info.add(a0000);
				
				int cellnum = 0;
				Cell cell = row.createCell(cellnum++);//序号
				YNTPExcelUtil.setCellValue(cell, styleZWC, i+"");
				
				cell = row.createCell(cellnum++);//姓名
				Object tp0101 = rs.getObject("tp0101");
				YNTPExcelUtil.setCellValue(cell, styleZWC, tp0101);
			
				cell = row.createCell(cellnum++);//出生年月
				value = rs.getObject("tp0102");
				YNTPExcelUtil.setCellValue(cell, styleDate, value);
				if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
					cell = row.createCell(cellnum++);//tp0103 任现职时间
					value = rs.getObject("tp0103");
					YNTPExcelUtil.setCellValue(cell, styleDate, value);
					
					cell = row.createCell(cellnum++);//tp0104 同职级时间
					value = rs.getObject("tp0104");
					YNTPExcelUtil.setCellValue(cell, styleDate, value);
				}
				cell = row.createCell(cellnum++);//tp0105 学历职称
				Object tp0105 = rs.getObject("tp0105");
				YNTPExcelUtil.setCellValue(cell, styleZWC, tp0105);
				
				cell = row.createCell(cellnum++);//tp0106 现任职务 
				Object tp0106 = rs.getObject("tp0106");
				YNTPExcelUtil.setCellValue(cell, styleZWL, tp0106);
				
				cell = row.createCell(cellnum++);//tp0107 拟任免职务
				Object tp0107 = rs.getObject("tp0107");
				YNTPExcelUtil.setCellValue(cell, styleZWL, tp0107);
				if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
					
				}else{
					cell = row.createCell(cellnum++);//讨论意见
					YNTPExcelUtil.setCellValue(cell, styleZWC, "");
				}
				
				current_row_height = YNTPExcelUtil.getMax(
						YNTPExcelUtil.getXingMingHeight(tp0101==null?"":tp0101.toString(),yntype),
						YNTPExcelUtil.getXueLiXueWeiHeight(tp0105==null?"":tp0105.toString(),yntype),
						YNTPExcelUtil.getNiRenMianHeight(tp0106==null?"":tp0106.toString(),yntype),
						YNTPExcelUtil.getNiRenMianHeight(tp0107==null?"":tp0107.toString(),yntype));
				page_height = page_height + current_row_height;//计算页高
				row.setHeightInPoints(current_row_height);
				
				content_mark.add(3);
			}
			
			if(page_height>= YNTPExcelUtil.PAGE_HEIGHT){
				//判断是否超过打印页
				Row row1 = sheet.getRow(rowIndex-1);
				page_height=current_row_height;
				if(page_height== YNTPExcelUtil.PAGE_HEIGHT){
					row1 = sheet.getRow(rowIndex);
					page_height = 0;
				}
				int ci = 0;
				row1.getCell(ci++).setCellStyle(bstyleZWC);//主要是打印的下边框
				if(content_mark.get(rowIndex-1-6)==1){
					row1.getCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					row1.createCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.createCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
						row1.createCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					}
					row1.createCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					row1.createCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					}else{
						row1.createCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
						row1.getCell(ci++).setCellStyle(bstyleBT1);//主要是打印的下边框
					}
				}else if(content_mark.get(rowIndex-1-6)==2){
					row1.getCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					row1.createCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.createCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
						row1.createCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					}
					row1.createCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					row1.createCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					}else{
						row1.createCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
						row1.getCell(ci++).setCellStyle(bstyleBT2);//主要是打印的下边框
					}
				}else{
					row1.getCell(ci++).setCellStyle(bstyleZWC);//主要是打印的下边框
					row1.getCell(ci++).setCellStyle(bstyleDate);//主要是打印的下边框
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleDate);//主要是打印的下边框
						row1.getCell(ci++).setCellStyle(bstyleDate);//主要是打印的下边框
					}
					row1.getCell(ci++).setCellStyle(bstyleZWC);//主要是打印的下边框
					row1.getCell(ci++).setCellStyle(bstyleZWL);//主要是打印的下边框
					
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleZWL);//主要是打印的下边框
					}else{
						row1.getCell(ci++).setCellStyle(bstyleZWL);//主要是打印的下边框
						row1.getCell(ci++).setCellStyle(bstyleZWC);//主要是打印的下边框
					}
				}
				
				
			}
			//判断是否超过打印页
			//if(page_height>=YNTPExcelUtil.PAGE_HEIGHT){
				
				
				
				//客户说标题在一页的最下方是不需要调到第二页，先注释掉
				//判断上一个是否为标题   即一页的最后是否为标题
				/*int titleCount = 0;//连续的标题次数
				int contentCount = 0;//连续的正文次数
				int bottom_rowindex = 0;//加下边框的行
				
				for(int index=content_mark.size()-1;index>0;index--){
					boolean isTitle = content_mark.get(index);
					if(isTitle&&contentCount==0){//标题计数
						titleCount++;
					}else if(!isTitle&&titleCount!=0){//正文计数
						contentCount++;
					}else if(isTitle&&titleCount!=0&&contentCount!=0){//计数结束
						计算下移的行高
						
						行高分配
						
						page_height = 下移的行高
						
						break;
					}else {
						page_height = 0;
						bottom_rowindex = index-1+6; //的行加下边框
						break;
					}
				}*/
			//}
			
			rowIndex++;
		}
		workbook.setPrintArea(
	            sheetIndex, //工作薄 下标0开始
	            0, //起始列 下标0开始
	            biaoticolLeft, //终止列 下标0开始
	            0, //起始行 下标0开始
	            --rowIndex  //终止行 下标0开始
	    );
		
		
		
		
		//导出lrmx
		if(expLrmx){
			final String exptype = this.expSHCLBS.request.getParameter("exptype");
			final String userid = SysUtil.getCacheCurrentUser().getId();
			Thread t = new Thread(){
				@Override
				public void run() {
					try {
						HashMap<String, String> a0000XingMingInfo = new HashMap<String, String>();
						HashMap<String, PersonXml> personXmlInfo = new HashMap<String, PersonXml>();
						String a0000Str = "@@";
						int i=0;
						//人员所有信息
						for(String a0000:a0000Info){
							PersonXml px = expSHCLBS.createLrmxStr(a0000, "",userid);
							personXmlInfo.put(a0000, px);
							a0000Str += px.getXingMing()+"@@";
						}
						//判断是否重名
						for(String a0000:a0000Info){
							i++;
							if(a0000Str.indexOf("@@"+personXmlInfo.get(a0000).getXingMing()+"@@")!=a0000Str.lastIndexOf("@@"+personXmlInfo.get(a0000).getXingMing()+"@@")){
								a0000XingMingInfo.put(a0000, i+personXmlInfo.get(a0000).getXingMing());
							}else{
								a0000XingMingInfo.put(a0000, personXmlInfo.get(a0000).getXingMing());
							}
						}
						//导出文件数据
						for(String a0000:a0000Info){
							if("full".equals(exptype)){
								expSHCLBS.createFile(outputpath+a0000XingMingInfo.get(a0000)+"/",a0000XingMingInfo.get(a0000)+"干部任免审批表.lrmx", JXUtil.Object2Xml(personXmlInfo.get(a0000),true), false, "UTF-8");
								@SuppressWarnings("unchecked")
								List<TpbAtt> tpbattList = HBUtil.getHBSession().
										createSQLQuery("select * from tpb_att where rb_id='"+ynId+"' and js0100='"+a0000+"'").addEntity(TpbAtt.class).list();
								if(tpbattList.size()>0){
									TpbAtt tat = tpbattList.get(0);
									String targetPath = outputpath+a0000XingMingInfo.get(a0000)+"/"  + tat.getJsa04();
									String sourcePath = HZBPATH+tat.getJsa07() + tat.getJsa00();
									expSHCLBS.copyFileByPath(sourcePath, targetPath,tat.getJsa04());
								}
							}else{
								expSHCLBS.createFile(outputpath+"干部任免审批表/",a0000XingMingInfo.get(a0000)+".lrmx", JXUtil.Object2Xml(personXmlInfo.get(a0000),true), false, "UTF-8");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					super.run();
				}
			};
			t.start();
			t.join();
		}
	}
	
	public String NORM;
	public String RMsql;
	public void expWord(String ynId, String yntype) throws AppException {
		String sql = "select a0000,tp0106 from TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yntype+"' and a0000 is not null order by sortnum asc ";
		if(this.RMsql!=null&&!"".equals(this.RMsql)){
			sql = this.RMsql;
		}
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> list = sess.createSQLQuery(sql).list();
		ExpTPBRMB.getPdfsByA000s_aspose(list, "eebdefc2-4d67-4452-a973-5f7939530a11","word",this.getOutputpath(),this.NORM);
		
	}
	
	
	 
}
