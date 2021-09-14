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
			put("TPHJ1","����");
			put("TPHJ2","��ί���ר������Ա����");
			put("TPHJ3","�������");
			put("TPHJ4","��ί���ר�����");
			put("TPHJ5","��ί��ί��");
		}
	};
	final static Map<String,String> SHEET_NAME = new HashMap<String, String>(){
		{
			put("TPHJ1","����");
			put("TPHJ2","��ί���ר������Ա����");
			put("TPHJ3","����");
			put("TPHJ4","��ǻ�");
			put("TPHJ5","��ί��");
		}
	};
	public String tpbSql;
	private static String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/";
		try {
			File file =new File(upload_file);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//��ѹ·��
		return upload_file;
	}
	public final static String HZBPATH = getPath();
	/**
	 * �ļ���
	 */
	final private List<String> outputFileNameList = new ArrayList<String>();
	
	public List<String> getOutputFileNameList() {
		return outputFileNameList;
	}
	/**
	 * �ļ����·��
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
	 * �ɲ����佨�鷽��  ����1�ͻ���2
	 * @throws Exception 
	 * @throws RadowException 
	 * @throws Exception 
	 */
	public void exportGBTP(String ynId,String yntype) throws Exception {
		File tplfilepath = null;
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/�ɲ����佨�鷽������12.xlsx").getPath());;
		}else{
			tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/�ɲ����佨�鷽������345.xlsx").getPath());;
		}
		//tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/�ɲ����佨�鷽��.xlsx").getPath());;
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //��ȡ���ƺõ��ļ�

		Workbook workbook = new XSSFWorkbook(is);      //Ĭ�϶�ȡ2003���Excel
		//5������
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
		String fn = "�ɲ����佨�鷽��"+hjstr+".xlsx";
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
	 * �ɲ����佨�鷽��  �����ϻ�����
	 * @param expSHCLBS
	 * @throws Exception
	 * @throws RadowException
	 * @throws Exception
	 */
	public void exportBuHui(String ynId) throws Exception {
		File tplfilepath = null;
		tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/�ɲ����佨�鷽��2.xlsx").getPath());;
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //��ȡ���ƺõ��ļ�
		
		Workbook workbook = new XSSFWorkbook(is);      //Ĭ�϶�ȡ2003���Excel
		//5������
		this.writeSheetData(workbook,0,"TPHJ3",ynId,true);
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "�ɲ����佨�鷽�������ᣩ.xlsx";
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
	 * �ɲ����佨�鷽��  �����ϻ�����
	 * @throws Exception 
	 * @throws RadowException 
	 * @throws Exception 
	 */
	public void exportShuJiHui(String ynId) throws Exception {
		File tplfilepath = null;
		tplfilepath = new File(YNTPGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm2/tpl/�ɲ����佨�鷽��3.xlsx").getPath());;
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //��ȡ���ƺõ��ļ�
		
		Workbook workbook = new XSSFWorkbook(is);      //Ĭ�϶�ȡ2003���Excel
		//5������
		this.writeSheetData(workbook,0,"TPHJ4",ynId,true);
		this.writeSheetData(workbook,1,"TPHJ5",ynId,true);
		
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "�ɲ����佨�鷽������ǻ᳣ί�ᣩ.xlsx";
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
		
		//��ȡsheetҳ����
		//int sheetNums = workbook.getNumberOfSheets();
		workbook.setSheetName(sheetIndex, SHEET_NAME.get(yntype));
		//���ñ�ͷ
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		sheet.getRow(3).getCell(0).setCellValue("��"+TPHJ.get(yntype)+"��");
		sheet.getRow(4).getCell(0).setCellValue(HBUtil.getValueFromTab("info0y||'��'||info0m||'��'||info0d||'��'", "js2_yntp_info", "yn_id='"+ynId+"' and info01='"+yntype+"'"));
		
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
		int i=0;//�������
		float page_height = 0f;//ҳ��
		float current_row_height=0;//��ǰ�и�
		List<Integer> content_mark = new ArrayList<Integer>();//true��ʶ���� false��ʾ�и�
		//����ϲ��� 
		int biaoticol = 5;
		int biaoticolLeft = biaoticol+1;
		if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
			biaoticol = 7;
			biaoticolLeft = biaoticol;
		}
		//��Ա��Ϣ ���ڵ����ϻ����
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
			
			
			
			
			
			if("1".equals(value)){//һ������
				Cell cell = row.createCell(0);//���
				cell.setCellStyle(styleZWC);//��Ҫ�Ǵ�ӡ����߿�
				value = rs.getObject("tp0101");
				cell = YNTPExcelUtil.mergeRegionAndSetValue(sheet, 1, biaoticol, rowIndex);
				current_row_height = YNTPExcelUtil.getBiaoTiHeight(value==null?"":value.toString(),yntype);//�и�
				page_height = page_height + current_row_height;//����ҳ��
				row.setHeightInPoints(current_row_height);
				YNTPExcelUtil.setCellValue(cell, styleBT1, value);
				
				cell = row.createCell(biaoticolLeft);//�ұ߿�
				cell.setCellStyle(styleBT1);
				
				content_mark.add(1);
			}else if("2".equals(value)){//��������
				Cell cell = row.createCell(0);//���
				cell.setCellStyle(styleZWC);//��Ҫ�Ǵ�ӡ����߿�
				value = rs.getObject("tp0101");
				cell = YNTPExcelUtil.mergeRegionAndSetValue(sheet, 1, biaoticol, rowIndex);//�ϲ���Ԫ��
				current_row_height = YNTPExcelUtil.getBiaoTiHeight(value==null?"":value.toString(),yntype);//�и�
				page_height = page_height + current_row_height;//����ҳ��
				row.setHeightInPoints(current_row_height);
				YNTPExcelUtil.setCellValue(cell, styleBT2, value);
				
				
				cell = row.createCell(biaoticolLeft);//�ұ߿�
				cell.setCellStyle(styleBT2);
				
				content_mark.add(2);
			}else{//����
				i++;
				String a0000 = rs.getObject("a0000")==null?"":rs.getObject("a0000").toString();
				a0000Info.add(a0000);
				
				int cellnum = 0;
				Cell cell = row.createCell(cellnum++);//���
				YNTPExcelUtil.setCellValue(cell, styleZWC, i+"");
				
				cell = row.createCell(cellnum++);//����
				Object tp0101 = rs.getObject("tp0101");
				YNTPExcelUtil.setCellValue(cell, styleZWC, tp0101);
			
				cell = row.createCell(cellnum++);//��������
				value = rs.getObject("tp0102");
				YNTPExcelUtil.setCellValue(cell, styleDate, value);
				if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
					cell = row.createCell(cellnum++);//tp0103 ����ְʱ��
					value = rs.getObject("tp0103");
					YNTPExcelUtil.setCellValue(cell, styleDate, value);
					
					cell = row.createCell(cellnum++);//tp0104 ְͬ��ʱ��
					value = rs.getObject("tp0104");
					YNTPExcelUtil.setCellValue(cell, styleDate, value);
				}
				cell = row.createCell(cellnum++);//tp0105 ѧ��ְ��
				Object tp0105 = rs.getObject("tp0105");
				YNTPExcelUtil.setCellValue(cell, styleZWC, tp0105);
				
				cell = row.createCell(cellnum++);//tp0106 ����ְ�� 
				Object tp0106 = rs.getObject("tp0106");
				YNTPExcelUtil.setCellValue(cell, styleZWL, tp0106);
				
				cell = row.createCell(cellnum++);//tp0107 ������ְ��
				Object tp0107 = rs.getObject("tp0107");
				YNTPExcelUtil.setCellValue(cell, styleZWL, tp0107);
				if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
					
				}else{
					cell = row.createCell(cellnum++);//�������
					YNTPExcelUtil.setCellValue(cell, styleZWC, "");
				}
				
				current_row_height = YNTPExcelUtil.getMax(
						YNTPExcelUtil.getXingMingHeight(tp0101==null?"":tp0101.toString(),yntype),
						YNTPExcelUtil.getXueLiXueWeiHeight(tp0105==null?"":tp0105.toString(),yntype),
						YNTPExcelUtil.getNiRenMianHeight(tp0106==null?"":tp0106.toString(),yntype),
						YNTPExcelUtil.getNiRenMianHeight(tp0107==null?"":tp0107.toString(),yntype));
				page_height = page_height + current_row_height;//����ҳ��
				row.setHeightInPoints(current_row_height);
				
				content_mark.add(3);
			}
			
			if(page_height>= YNTPExcelUtil.PAGE_HEIGHT){
				//�ж��Ƿ񳬹���ӡҳ
				Row row1 = sheet.getRow(rowIndex-1);
				page_height=current_row_height;
				if(page_height== YNTPExcelUtil.PAGE_HEIGHT){
					row1 = sheet.getRow(rowIndex);
					page_height = 0;
				}
				int ci = 0;
				row1.getCell(ci++).setCellStyle(bstyleZWC);//��Ҫ�Ǵ�ӡ���±߿�
				if(content_mark.get(rowIndex-1-6)==1){
					row1.getCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					row1.createCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.createCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
						row1.createCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					}
					row1.createCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					row1.createCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					}else{
						row1.createCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
						row1.getCell(ci++).setCellStyle(bstyleBT1);//��Ҫ�Ǵ�ӡ���±߿�
					}
				}else if(content_mark.get(rowIndex-1-6)==2){
					row1.getCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					row1.createCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.createCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
						row1.createCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					}
					row1.createCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					row1.createCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					}else{
						row1.createCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
						row1.getCell(ci++).setCellStyle(bstyleBT2);//��Ҫ�Ǵ�ӡ���±߿�
					}
				}else{
					row1.getCell(ci++).setCellStyle(bstyleZWC);//��Ҫ�Ǵ�ӡ���±߿�
					row1.getCell(ci++).setCellStyle(bstyleDate);//��Ҫ�Ǵ�ӡ���±߿�
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleDate);//��Ҫ�Ǵ�ӡ���±߿�
						row1.getCell(ci++).setCellStyle(bstyleDate);//��Ҫ�Ǵ�ӡ���±߿�
					}
					row1.getCell(ci++).setCellStyle(bstyleZWC);//��Ҫ�Ǵ�ӡ���±߿�
					row1.getCell(ci++).setCellStyle(bstyleZWL);//��Ҫ�Ǵ�ӡ���±߿�
					
					if("TPHJ1".equals(yntype)||"TPHJ2".equals(yntype)){
						row1.getCell(ci++).setCellStyle(bstyleZWL);//��Ҫ�Ǵ�ӡ���±߿�
					}else{
						row1.getCell(ci++).setCellStyle(bstyleZWL);//��Ҫ�Ǵ�ӡ���±߿�
						row1.getCell(ci++).setCellStyle(bstyleZWC);//��Ҫ�Ǵ�ӡ���±߿�
					}
				}
				
				
			}
			//�ж��Ƿ񳬹���ӡҳ
			//if(page_height>=YNTPExcelUtil.PAGE_HEIGHT){
				
				
				
				//�ͻ�˵������һҳ�����·��ǲ���Ҫ�����ڶ�ҳ����ע�͵�
				//�ж���һ���Ƿ�Ϊ����   ��һҳ������Ƿ�Ϊ����
				/*int titleCount = 0;//�����ı������
				int contentCount = 0;//���������Ĵ���
				int bottom_rowindex = 0;//���±߿����
				
				for(int index=content_mark.size()-1;index>0;index--){
					boolean isTitle = content_mark.get(index);
					if(isTitle&&contentCount==0){//�������
						titleCount++;
					}else if(!isTitle&&titleCount!=0){//���ļ���
						contentCount++;
					}else if(isTitle&&titleCount!=0&&contentCount!=0){//��������
						�������Ƶ��и�
						
						�и߷���
						
						page_height = ���Ƶ��и�
						
						break;
					}else {
						page_height = 0;
						bottom_rowindex = index-1+6; //���м��±߿�
						break;
					}
				}*/
			//}
			
			rowIndex++;
		}
		workbook.setPrintArea(
	            sheetIndex, //������ �±�0��ʼ
	            0, //��ʼ�� �±�0��ʼ
	            biaoticolLeft, //��ֹ�� �±�0��ʼ
	            0, //��ʼ�� �±�0��ʼ
	            --rowIndex  //��ֹ�� �±�0��ʼ
	    );
		
		
		
		
		//����lrmx
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
						//��Ա������Ϣ
						for(String a0000:a0000Info){
							PersonXml px = expSHCLBS.createLrmxStr(a0000, "",userid);
							personXmlInfo.put(a0000, px);
							a0000Str += px.getXingMing()+"@@";
						}
						//�ж��Ƿ�����
						for(String a0000:a0000Info){
							i++;
							if(a0000Str.indexOf("@@"+personXmlInfo.get(a0000).getXingMing()+"@@")!=a0000Str.lastIndexOf("@@"+personXmlInfo.get(a0000).getXingMing()+"@@")){
								a0000XingMingInfo.put(a0000, i+personXmlInfo.get(a0000).getXingMing());
							}else{
								a0000XingMingInfo.put(a0000, personXmlInfo.get(a0000).getXingMing());
							}
						}
						//�����ļ�����
						for(String a0000:a0000Info){
							if("full".equals(exptype)){
								expSHCLBS.createFile(outputpath+a0000XingMingInfo.get(a0000)+"/",a0000XingMingInfo.get(a0000)+"�ɲ�����������.lrmx", JXUtil.Object2Xml(personXmlInfo.get(a0000),true), false, "UTF-8");
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
								expSHCLBS.createFile(outputpath+"�ɲ�����������/",a0000XingMingInfo.get(a0000)+".lrmx", JXUtil.Object2Xml(personXmlInfo.get(a0000),true), false, "UTF-8");
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
