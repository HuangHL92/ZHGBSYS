package com.insigma.siis.local.pagemodel.customquery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.zhgbDatav.UploadHzbFileBSZHGB;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class HZBImportAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	private static final String EXCEL_ZIP = "zip";
	
	/**
	 * �������
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importByUnid(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		long s = System.currentTimeMillis();
		
		String unid = request.getParameter("unid");
		
		Map<String,String> data= new HashMap<String, String>();//����ǰ̨��MSG
		
		String filePath = "";
		String str = "";
		
		//���������ļ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = response.getWriter();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String fileName = "";
		String upload_file = AppConfig.HZB_PATH + "/temp/upload/"+uuid+"/";// �ϴ�·��
		
		try {
			fileItems = uploader.parseRequest(request);  //�ļ�����
		} catch (FileUploadException e) {
			str = "�����ļ�����Ϊ�գ������µ��룡";
			data.put("error", str);
			e.printStackTrace();
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}
				
		if(!fileItems.isEmpty()){
			iter = fileItems.iterator();
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}
					
			while(iter.hasNext()){
						
				FileItem fi = iter.next();
						
				if(fi.getSize()==0){
					str = "�����ļ�����Ϊ�գ������µ��룡";
					data.put("error", str);
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				if (!(fi.getName().endsWith(EXCEL_XLS) || (fi.getName().endsWith(EXCEL_XLSX))||(fi.getName().endsWith(EXCEL_ZIP)))) {
					data.put("error", "�ļ�����Zip��Excel��ʽ,���飡");
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				if(fi.getName().endsWith(EXCEL_ZIP)){
					filePath = upload_file+uuid+".zip";
				}
				if(fi.getName().endsWith(EXCEL_XLS)){
					filePath = upload_file+uuid+".xls";
				}
				if(fi.getName().endsWith(EXCEL_XLSX)){
					filePath = upload_file+uuid+".xlsx";
				}
				
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				//��ȡ�ļ�����
				fileName = fi.getName().substring(fi.getName().lastIndexOf(System.getProperty("file.separator"))+1);
				fi.getOutputStream().close();
			}
		}
		
		//��ȡ�������е��ļ��������д���
		data = Imp(unid,filePath,fileName,upload_file,uuid);
		
		File f = new File(filePath);
		f.delete();
		
		long s2 = System.currentTimeMillis();
		System.out.println("Excel�ױ��룬���ƺ�ʱ��"+((s2-s)/1000)+"��");
		
		this.doSuccess(request, data);
		return this.ajaxResponse(request, response);
	}

	private Map<String, String> Imp(String unid, String path, String fileName, String upload_file, String uuid) {
		HBSession session = HBUtil.getHBSession();
		Map<String,String> data= new HashMap<String, String>();//����ǰ̨��MSG
		boolean hasPhoto = false;
		File photoFile = null;
		File excelFile = null;
		
		String unzip = "";
		//�����ZIP�����ѹ
		if(fileName.endsWith(EXCEL_ZIP)){
			unzip = AppConfig.HZB_PATH + "/temp/upload/unzip/"+uuid+"/";										// ��ѹ·��
			File file = new File(unzip);															// ����ļ��в������򴴽�
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			try {
				Zip7z.unzip7zAll(path, unzip, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			File[] f = file.listFiles();
			if(f.length>2){
				data.put("error", "��ʹ����ȷ��ZIP�ļ���");
				return data;
			}
			for(File f2 : f){
				if(f2.getName().endsWith("xls")||f2.getName().endsWith("xlsx")){
					excelFile = f2;
					continue;
				}
				if(f2.isDirectory()&&f2.getName().equals("Photos")){
					hasPhoto = true;
					photoFile = f2;
				}
			}
		}else{
			path = path.replaceAll("\\\\", "\\/");
			excelFile = new File(path); // �����ļ�����
		}
		
		String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
		String IMP_RECORD_ID = uuid;
		String dbtyle = "";
		if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			dbtyle = "now()";
		}else{
			dbtyle = "SYSDATE";
		}
		session.createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,IMP_USER_ID,IMP_TIME,IMP_DEPTID,IMP_STUTAS,IS_VIRETY,FILE_NAME,FILE_TYPE,IMP_TEMP_TABLE,IMP_TYPE,PROCESS_STATUS) "
				+ "values ('"+IMP_RECORD_ID+"','"+SysUtil.getCacheCurrentUser().getId()+"',"+dbtyle+","
				+ "'"+unid+"','1','0','"+fileName+"','"+fileName.substring(fileName.lastIndexOf(".")+1)+"','"+tableExt+"','1','2')").executeUpdate();
		UploadHzbFileBSZHGB uploadbs = new UploadHzbFileBSZHGB();		// ҵ����bs
		
		FileInputStream is = null;
		try {
			//�Ȼ�ȡһ��sheetҳ��������Ϣ
			// ͬʱ֧��Excel 2003��2007
			is = new FileInputStream(excelFile);
			
			Workbook workbook = getWorkbok(is, excelFile);//��ȡ�ļ�
			
			//�ж�ģ���Ƿ�һ�£�����ͨ���жϰ汾�ź�sheetҳ����
			int sheetNums = workbook.getNumberOfSheets();
			Sheet sheetZero = workbook.getSheetAt(0);
			Cell version = sheetZero.getRow(1).getCell(7);
			
			if(!((sheetNums == 11)) && ("V2017.01".equals(version))){
				data.put("error", "�����Excel�ļ��汾����дģ�岻һ�£����飡");
				return data;
			}
			
			//��һ����������ʱ��
			uploadbs.createTempTableTYGS(tableExt);
			
			
			//�ڶ������ȳ�ʼ��mapB0111
			Sheet sheetOne = workbook.getSheetAt(1);//B01��λ
			String rootB01Name = "";//B01����λ
			//���������
			int rowNum =sheetOne.getLastRowNum();
			
			List<String> upListB0101 = new ArrayList<String>();//����ϼ�b0101
			List<String> listB0101 = new ArrayList<String>();//��ű���b0101
			
			for(int i = 2;i<=rowNum;i++){//��ʼ��  ����ϼ�b0101��list
				Cell unitName = sheetOne.getRow(i).getCell(0);
				if(unitName!=null && !"".equals(unitName.toString().trim())){
					if(i==2){
						rootB01Name = unitName.toString().trim().replaceAll(" ", "");
					}
					upListB0101.add(unitName.toString().trim().replaceAll(" ", ""));
				}
			}
			
			Map<String, List<String>> mapSJ = new HashMap<String, List<String>>();
			mapSJ.put(rootB01Name, new ArrayList<String>());
			
			Map<String, String> mapB0111 = new HashMap<String, String>();
			mapB0111.put(rootB01Name, unid);
			
			for(int i = 3;i<=rowNum;i++){//��ʽ ��b0101��ȷ�����¼���ϵ,�ӵ�4�п�ʼ��
				String name = "";
				String sjName = "";
				//String realName = "";//name - sjName ʣ�µ�����
				int n = 0;//���ڼ�¼�ϼ����ֶγ���
				
				Cell unitName = sheetOne.getRow(i).getCell(0);
				if(unitName!=null && !"".equals(unitName.toString().trim())){
					//ȥupListB0101�У��ҵ���Ӧ���ϼ�     ���� unitName���� �ϼ������ƣ��������ϼ������Ƴ��������Ϊ��unitName���ϼ�
					name = unitName.toString().trim().replaceAll(" ", "");
					for(String v : upListB0101){
						if(name.contains(v)&&!name.equals(v)){//���������ȥ���Լ�������˵��v��unitName���ϼ����ϼ�����λ
							if(v.length()>n){
								n = v.length();
								sjName = v;
								
								//realName = name.substring(sjName.length());
								
								//�ж�mapSJ���Ƿ��Ѿ��м�¼��û�������
								if(mapSJ.get(sjName)==null){
									mapSJ.put(sjName, new ArrayList<String>());
								}
							}
						}
					}
					//���¼������ϼ���map��
					mapSJ.get(sjName).add(name);
				}
			}
			
			//��ȡ�ϼ�mapSJ,��ÿ���¼���λ����
			//����ȡ�ϼ���λΪ   ����λ  �ı�����λ
			List<String> SJSon = mapSJ.get(rootB01Name);   //�Ӽ�
			String SJBM = mapB0111.get(rootB01Name);    //���磺��ȡ001.001
			String sjbm = "";
			String convert = "";
			for(int j=0;j<SJSon.size();j++){
				//����j����b0111
				String son = SJSon.get(j);
				convert = ConvertTo36(j+1);
				//BJBM = SJBM + newB0111
				sjbm = SJBM + "." + convert;
				//����mapB0111��
				mapB0111.put(son, sjbm);
				
				createB0111(mapSJ,mapB0111,son);
			}
			
			mapB0111.put("������λ", "-1");
			
			//����������ʼ����Ա���� mapA0000
			Map<String, String> mapA0000 = new HashMap<String, String>();
			//listA0000  �����ж��Ƿ����ظ�����
			List<String> listA0000 = new ArrayList<String>();
			
			Sheet sheetTwo = workbook.getSheetAt(2);
			int rowTwo =sheetTwo.getLastRowNum();
			Cell c1 = null;
			Cell c2 = null;
			String key = "";
			for(int j=2;j<=rowTwo;j++){
				c1 = sheetTwo.getRow(j).getCell(0);//����
				c2 = sheetTwo.getRow(j).getCell(1);//���֤��
				if(c1==null || c2==null || "".equals(c1.toString().trim()) ||
						 "".equals(c2.toString().trim())){
					//data.put("error", "��Ա������Ϣ���������ظ���Ա������"+(j+1)+"�����ݣ�");
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim())) ){
						continue;
					}
					data.put("error", "A01��Ա������"+(j+1)+"�д�����Աδ��д:����/���֤���룡");
					return data;
				}
				key = c1.toString()+c2.toString();
				
				//���Ƿ���Ա�ظ�
				for(String allPerson : listA0000){
					if(key.equals(allPerson)){
						data.put("error", "A01��Ա���������ظ���Ա������"+(j+1)+"�����ݣ�");
						return data;
					}
				}
				listA0000.add(key);
				
				String A0000uuid = UUID.randomUUID().toString();
				mapA0000.put(key, A0000uuid);
			}
			session.createSQLQuery("update IMP_RECORD set TOTAL_NUMBER = '"+mapA0000.size()+"',EMP_DEPT_NAME = '"+rootB01Name+"' where IMP_RECORD_ID = '"+IMP_RECORD_ID+"'").executeUpdate();
			
			// ���Ĳ�����ȡcode_value,��ʽ����Sheet
			Map<String, String> codeValues = new HashMap<String, String>();
			String codeValueSql = "SELECT c.CODE_TYPE,c.CODE_NAME3,c.CODE_VALUE FROM CODE_VALUE c WHERE c.CODE_TYPE IN('ZB01','ZB87',"
					+ "'ZB03','ZB04','GB2261','GB3304','GB4762','ZB125','ZB126','ZB130','ZB09','ZB148','ZB134','ZB135','ZB139',"
					+ "'ZB129','ZB122','ZB14','GB8561','ZB24','ZB64','GB6864','GB16835','ZB123','ZB65','ZB03','ZB128','ZB18','XZ91')";
			List<Object[]> values = session.createSQLQuery(codeValueSql).list();
			for(Object[] obj : values){
				if(obj!=null && obj[0]!=null && obj[1]!=null && obj[2]!=null){
					codeValues.put(obj[0].toString()+obj[1].toString(), obj[2].toString());
				}
			}
			codeValues = getNewCodeValues(codeValues);
			
			for(int i=1;i<11;i++){
				Sheet sheet = workbook.getSheetAt(i);
				
				long start = System.currentTimeMillis();
				System.out.println("--------------------------------------------����Sheet��"+(i+1));
				data = dispose(i,sheet,unid,session,data,mapB0111,mapA0000,codeValues,tableExt);
				
				long end = System.currentTimeMillis();
				System.out.println("--------------------------------------------Sheet��"+(i+1)+"�������,��ʱ��"+((end-start)/1000)+"��");
				
				//����error�����ѭ��
				if(data.get("error")!=null){
					throw new Exception(data.get("error"));
				}
				if(i==10){
					//����Ƭ���д���
					if(hasPhoto){
						Connection conn = session.connection();
						conn.setAutoCommit(false);
						PreparedStatement pstmt = conn.prepareStatement("insert into A57"+tableExt+"(A0000,A5714,UPDATED,PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH,PICSTATUS) values(?,?,?,?,?,?,?,?)");
						int m = 0;
						
						File[] photos = photoFile.listFiles();
						if(photos.length>0){
							for(File photo : photos){
								String photoName = photo.getName().substring(0,photo.getName().indexOf("."));
								String a0000 = mapA0000.get(photoName);
								if(a0000!=null&&!"".equals(a0000)){
									pstmt.setObject(1, a0000);
									pstmt.setObject(2, a0000+".jpg");
									pstmt.setObject(3, "1");
									pstmt.setObject(4, null);
									pstmt.setObject(5, a0000+".jpg");
									pstmt.setObject(6, ".jpg");
									String photopath = "";
									String str = a0000.substring(0, 2);
									if (PhotosUtil.isLetterDigit(str)) {
										photopath = a0000.charAt(0) + "/" + a0000.charAt(1) + "/";
									} else {
										photopath = a0000.charAt(0) + "/";
									}
									pstmt.setObject(7, photopath);
									pstmt.setObject(8, "1");
									
									pstmt.addBatch();
									m++;
									
									photo.renameTo(new File(photo.getParent()+File.separator+a0000+".jpg"));
									
									if(m%500==0){
										pstmt.executeBatch();
										pstmt.clearBatch();
									}
								}else{
									System.out.println("a0000: "+photoName);
								}
							}
							pstmt.executeBatch();
							pstmt.clearBatch();
							
							pstmt.close();
							conn.commit();
							conn.close();
						}
					}
					
					//����A01�е�ѧ��ѧλ��Ϣ
					TYGSsqlUtil.updateA08(tableExt);
					
					data.put("success", "Excel�ɹ�������ʱ�⣡");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			data.put("error", e.getMessage());
			uploadbs.rollbackImpTable(IMP_RECORD_ID, tableExt);
			return data;
		}finally{
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return data;
	}

	private Map<String,String> dispose(int i, Sheet sheet, String unid, HBSession session, 
			Map<String,String> data, Map<String, String> mapB0111, Map<String, String> mapA0000, Map<String, String> codeValues, String tableExt) throws Exception {
		
		Connection conn = session.connection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		int j = 0;
		
		try {
			
		//�����������������
		int rowNum =sheet.getLastRowNum();
		int colNum=sheet.getRow(1).getPhysicalNumberOfCells();
		int n = 0;//����
			
		//�����У�д���� (mapB0111)
		Cell cell = null;
		String c = "";
		
		//B01��___��λ���������Ϣ
		if(i==1){
			pstmt = conn.prepareStatement("insert into B01"+tableExt+"("+TYGSsqlUtil.B01EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				String bjB0111 = mapB0111.get(sheet.getRow(j).getCell(0).toString().trim().replaceAll(" ", ""));//��ȡ��������
				String sjB0111 = "";//��ȡ�ϼ�����
				if("001.001".equals(bjB0111)){
					sjB0111 = "-1";
				}else{
					sjB0111 = bjB0111.substring(0, bjB0111.length()-4);
				}
				pstmt.setString(1, bjB0111);
				pstmt.setString(2, sjB0111);
				for(int k=0;k<colNum;k++){
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(k==0){//�������ƴ���
						String name = c.replaceAll(" ", "");
						if(unid.equals(bjB0111)){
							pstmt.setString(3, name);
						}else{
							String realname = "";
							Set<String> set = mapB0111.keySet();
							for(String ks: set){
							    if(sjB0111.equals(mapB0111.get(ks))){
							    	realname = name.substring(ks.length());
							    	pstmt.setString(3, realname);
							    	break;
							    }
							}
						}
						continue;
					}
					if(c==null || "".equals(c)){
						pstmt.setObject(k+3, null);
						continue;
					}
					if(k==3||k==4||k==5||k==6){  //��λ��������
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==3){
							codeType = "ZB01";
						}
						if(k==4){
							codeType = "ZB87";
						}
						if(k==5){
							codeType = "ZB03";
						}
						if(k==6){
							codeType = "ZB04";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+3, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value!=null){
								pstmt.setObject(k+3, value);
							}else{
								data.put("error", "B01��λ,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					if(k==7){  //���˵�λ
						if("���˵�λ".equals(c)){
							pstmt.setObject(k+3, "1");
						}
						if("�������".equals(c)){
							pstmt.setObject(k+3, "2");
						}
						if("��������".equals(c)){
							pstmt.setObject(k+3, "3");
						}
						continue;
					}
					if((""+c).endsWith(".0")){
						pstmt.setObject(k+3, (""+c).substring(0, (""+c).indexOf(".")));
					}else{
						pstmt.setObject(k+3, ""+c);
					}
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
		}
		//A01----��Ա������Ϣ
		if(i==2){
			pstmt = conn.prepareStatement("insert into A01"+tableExt+"("+TYGSsqlUtil.A01EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				//ǰ���Ѿ��жϷǿ�
				String c1 = sheet.getRow(j).getCell(0).toString();
				String c2 = sheet.getRow(j).getCell(1).toString();
				if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim())) ){
					continue;
				}
				String key = c1+c2;
				String a0000 = mapA0000.get(key);
				
				pstmt.setString(1, a0000);
				for(int k=0;k<colNum;k++){
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+2, null);
						if(k==35){
							pstmt.setObject(k+3, "1");
						}
						continue;
					}
					if(k==2||k==6||k==7||k==11||k==13||k==14||k==15||k==16||k==17||k==21||k==23||k==31||k==32||k==33){
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==2){
							codeType = "GB2261";
						}
						if(k==6){
							codeType = "ZB01";
						}
						if(k==7){
							codeType = "GB3304";
						}
						if(k==11||k==13||k==14){
							codeType = "GB4762";
						}
						if(k==15){
							codeType = "ZB125";
						}
						if(k==16){
							codeType = "ZB126";
						}
						if(k==17){
							codeType = "ZB130";
						}
						if(k==21){
							codeType = "ZB09";
						}
						if(k==23){
							codeType = "ZB148";
						}
						if(k==31){
							codeType = "ZB134";
						}
						if(k==32){
							codeType = "ZB135";
						}
						if(k==33){
							codeType = "ZB139";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+2, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value != null){
								pstmt.setObject(k+2, value);
							}else{
								data.put("error", "A01��Ա,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					if(k==26){
						if("��".equals(c)){
							pstmt.setObject(k+2, "1");
						}else if("��".equals(c)){
							pstmt.setObject(k+2, "0");
						}else{
							data.put("error", "A01��Ա,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if(k==27){  //ͳ�ƹ�ϵ���ڵ�λ,��ʱ������
						String tjName = c.replaceAll(" ", "");
						String A0195 = mapB0111.get(tjName);
						if(A0195 != null){
							pstmt.setObject(k+2, A0195);
						}else{
							pstmt.setObject(k+2, "-1");//������λ
						}
						continue;
					}
					if(k!=28&&(""+c).endsWith(".0")){
						pstmt.setObject(k+2, (""+c).substring(0, (""+c).indexOf(".")));
					}else{
						pstmt.setObject(k+2, ""+c);
					}
					if(k==35){//STATUS
						pstmt.setObject(k+3, "1");
					}
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
		}
		//A02---ְ����Ϣ
		if(i==3){
			pstmt = conn.prepareStatement("insert into A02"+tableExt+"("+TYGSsqlUtil.A02EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A02ְ��,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A02ְ��,��"+(j+1)+"�У�����Ա�ڡ�A01��Ա�������ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						if(k==2){//��ְ����
							pstmt.setObject(3, null);
							pstmt.setObject(4, null);
						}else{
							pstmt.setObject(k+2, null);
						}
						continue;
					}
					if(k==2){//��ְ����
						String name = c.replaceAll(" ", "");
						String bjB0111 = mapB0111.get(name);
						if("-1".equals(bjB0111)){
							pstmt.setString(3, "������λ");
						}else if(unid.equals(bjB0111)){
							pstmt.setString(3, name);
						}else{
							String sjB0111 = bjB0111.substring(0, bjB0111.length()-4);
							String realname = "";
							Set<String> set = mapB0111.keySet();
							for(String ks: set){
							    if(sjB0111.equals(mapB0111.get(ks))){
							    	realname = name.substring(ks.length());
							    	pstmt.setString(3, realname);
							    }
							}
						}
						
						pstmt.setObject(4, bjB0111);
						
						continue;
					}
					if(k==3 || k==6 || k==12 || k==18){
						if("��".equals(c)){
							pstmt.setObject(k+2, "1");
						}else if("��".equals(c)){
							if(k==6){
								pstmt.setObject(k+2, "2");
							}else{
								pstmt.setObject(k+2, "0");
							}
						}else{
							data.put("error", "A02ְ��,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if(k==4 || k==11 || k==13){
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==4){
							codeType = "ZB129";
						}
						if(k==11){
							codeType = "ZB122";
						}
						if(k==13){
							codeType = "ZB14";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+2, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value != null){
								pstmt.setObject(k+2, value);
							}else{
								data.put("error", "A02ְ��,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					if(k==17){
						if("���".equals(c)){
							pstmt.setObject(k+2, "true");
						}else if("�����".equals(c)){
							pstmt.setObject(k+2, "false");
						}else{
							data.put("error", "A02ְ��,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if((""+c).endsWith(".0")){
						pstmt.setObject(k+2, (""+c).substring(0, (""+c).indexOf(".")));
					}else{
						pstmt.setObject(k+2, ""+c);
					}
					
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
		}
		//A05---ְ��ְ����Ϣ
		if(i==4){
			pstmt = conn.prepareStatement("insert into A05"+tableExt+"("+TYGSsqlUtil.A05EXCEL+") values(?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A05ְ��ְ��,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A05ְ��ְ��,��"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�3�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==2){
						if("ְ����".equals(c)){
							pstmt.setObject(k+1, "0");
						}else if("���ܵ�ְ��".equals(c)){
							pstmt.setObject(k+1, "1");
						}else{
							data.put("error", "A05ְ��ְ��,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if(k==3 || k==7){
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==3){
							Cell v = sheet.getRow(j).getCell(2);
							if(v!=null && "ְ����".equals(v.toString())){
								codeType = "ZB09";
							}
							if(v!=null && "���ܵ�ְ��".equals(v.toString())){
								codeType = "ZB148";
							}
						}
						if(k==7){
							codeType = "ZB14";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+1, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A05ְ��ְ��,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					pstmt.setObject(k+1, ""+c);
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
		}
			
		//A06---רҵ������ְ�ʸ���Ϣ
		if(i==5){
			pstmt = conn.prepareStatement("insert into A06"+tableExt+"("+TYGSsqlUtil.A06EXCEL+") values(?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A06רҵ����,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A06רҵ����,��"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�3�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==2 || k==5){
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==2){
							codeType = "GB8561";
						}
						if(k==5){
							codeType = "ZB24";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+1, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A06רҵ����,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					if(k==7){
						if("���".equals(c)){
							pstmt.setObject(k+1, "true");
						}else if("�����".equals(c)){
							pstmt.setObject(k+1, "false");
						}else{
							data.put("error", "A06רҵ����,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if((""+c).endsWith(".0")){
						pstmt.setObject(k+1, (""+c).substring(0, (""+c).indexOf(".")));
					}else{
						pstmt.setObject(k+1, ""+c);
					}
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
				
			}	
		}
		//A08----ѧ��ѧλ��Ϣ
		if(i==6){
			pstmt = conn.prepareStatement("insert into A08"+tableExt+"("+TYGSsqlUtil.A08EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A08ѧ��ѧλ,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A08ѧ��ѧλ,"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�4�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==3 || k==5 || k==11 || k==12){
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==3){
							codeType = "ZB64";
						}
						if(k==5){
							codeType = "GB6864";
						}
						if(k==11){
							codeType = "GB16835";
						}
						if(k==12){
							codeType = "ZB123";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+1, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A08ѧ��ѧλ,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					if(k==14){
						if("���".equals(c)){
							pstmt.setObject(k+1, "true");
						}else if("�����".equals(c)){
							pstmt.setObject(k+1, "false");
						}else{
							data.put("error", "A08ѧ��ѧλ,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if((""+c).endsWith(".0")){
						pstmt.setObject(k+1, (""+c).substring(0, (""+c).indexOf(".")));
					}else{
						pstmt.setObject(k+1, ""+c);
					}
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}	
		}
		//A14----������Ϣ
		if(i==7){
			pstmt = conn.prepareStatement("insert into A14"+tableExt+"("+TYGSsqlUtil.A14EXCEL+") values(?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A14����,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A14����,"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�3�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==3 || k==6 || k==7 || k==9){
						String newcell = c.replaceAll("\\(", "��").replaceAll("\\)", "��");
						String codeType = "";
						if(k==3){
							codeType = "ZB65";
						}
						if(k==6){
							codeType = "ZB03";
						}
						if(k==7){
							codeType = "ZB09";
						}
						if(k==9){
							codeType = "ZB128";
						}
						String value = codeValues.get(codeType+newcell);
						if(value != null){
							pstmt.setObject(k+1, value);
						}else{
							value = codeValues.get(codeType+newcell.replaceAll("��","\\(").replaceAll("��","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A14����,"+(j+1)+"��"+(k+1)+"�У�������������");
								return data;
							}
						}
						continue;
					}
					if((""+c).endsWith(".0")){
						pstmt.setObject(k+1, (""+c).substring(0, (""+c).indexOf(".")));
					}else{
						pstmt.setObject(k+1, ""+c);
					}
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}	
		}
		//A15----��ȿ�����Ϣ
		if(i==8){
			pstmt = conn.prepareStatement("insert into A15"+tableExt+"("+TYGSsqlUtil.A15EXCEL+") values(?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A15����,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A15����,"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�3�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(k==2){
						if(c==null || "".equals(c)){
							pstmt.setObject(k+1, null);
							continue;
						}
						String codeType = "ZB18";
						String value = codeValues.get(codeType+c);
						if(value != null){
							pstmt.setString(k+1, value);
						}else{
							data.put("error", "A15����,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					if(k==3){
						if(c==null || "".equals(c)){
							data.put("error", "A15����,"+(j+1)+"��"+(k+1)+"�У����ݲ���Ϊ��ֵ������");
							return data;
						}
						pstmt.setObject(k+1, ""+c);
						continue;
					}
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}	
		}
		
		//A36---��ͥ��Ա������ϵ��Ϣ
		if(i==9){
			pstmt = conn.prepareStatement("insert into A36"+tableExt+"("+TYGSsqlUtil.A36EXCEL+") values(?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A36��ͥ������ϵ,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A36��ͥ������ϵ,"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�4�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					pstmt.setObject(k+1, ""+c);
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
		}
		
		//A99Z1---������Ϣ
		if(i==10){
			pstmt = conn.prepareStatement("insert into A99Z1"+tableExt+"("+TYGSsqlUtil.A99Z1EXCEL+") values(?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //�ӵ����п�ʼ
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A99Z1����,��"+(j+1)+"�У�����Աδ��д:����/���֤���룡");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A99Z1����,"+(j+1)+"�У�����Ա�ڡ�A01��Ա���в����ڣ�����");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //ֱ�Ӵӵ�4�п�ʼ��ȡ
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==2 || k==4){
						if("��".equals(c)){
							pstmt.setObject(k+1, "1");
						}else if("��".equals(c)){
							pstmt.setObject(k+1, "0");
						}else{
							data.put("error", "A99Z1����,"+(j+1)+"��"+(k+1)+"�У�������������");
							return data;
						}
						continue;
					}
					pstmt.setObject(k+1, ""+c);
				}
				pstmt.addBatch();
				n++;
				
				if(n%500==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
		}
		
		//���Ҳ��ִ��һ��
		pstmt.executeBatch();
		pstmt.clearBatch();
		
		pstmt.close();
		conn.commit();
		conn.close();
		
		} catch (Exception e) {    //��catch���󣬴����������׳�
			e.printStackTrace();
			throw new Exception("����ʧ�ܣ�����Excel��Sheet��"+(i+1)+"����"+(j+1)+"�����ݣ�");
		} 
		
		return data;
		
	}
	

	private void createB0111(Map<String, List<String>> mapSJ,
			Map<String, String> mapB0111, String son) throws Exception {
		List<String> SJSon = mapSJ.get(son);   //�Ӽ�
		if(SJSon!=null){
			String SJBM = mapB0111.get(son);    //���磺��ȡ001.001
			String sjbm = "";
			String convert = "";
			for(int j=0;j<SJSon.size();j++){
				//����j����b0111
				son = SJSon.get(j);
				convert = ConvertTo36(j+1);
				//BJBM = SJBM + newB0111
				sjbm = SJBM + "." + convert;
				//����mapB0111��
				mapB0111.put(son, sjbm);
				
				createB0111(mapSJ,mapB0111,son);
			}
		}
	}

	public String ConvertTo36(int val) throws Exception{
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		
        String result = "";
        while (val >= 36)
        {
        	result = key[val % 36] + result;
        	val /= 36;
        }
        if (val >= 0) result = key[val] + result;
        if(result.length()>3){
        	throw new Exception("�������볬����Χ��");
        }
        while(result.length()<3){
        	result = "0" + result;
        }
        return result;
    }

	/**
	 * �ж�Excel�İ汾,��ȡWorkbook
	 * 
	 * @param in
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(InputStream in, File file) throws IOException {
		Workbook wb = null;
		if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		in.close();
		return wb;
	}
	
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		//CommonQueryBS.systemOut(no);
		return no;
	}
	
	private Map<String, String> getNewCodeValues(Map<String, String> codeValues) {
		codeValues.put("ZB09��������Ա���ۺϹ����ࣩ","1A98");
		codeValues.put("ZB09�������ۺϹ����ࣩ","1A99");
		codeValues.put("ZB09��������Ա��רҵ�����ࣩ","1B98");
		codeValues.put("ZB09������רҵ�����ࣩ","1B99");
		codeValues.put("ZB09��������Ա������ִ���ࣩ","1C98");
		codeValues.put("ZB09����������ִ���ࣩ","1C99");
		
		codeValues.put("ZB09��������Ա�����񾯲쾯Աְ�����У�","27");
		codeValues.put("ZB09���������񾯲쾯Աְ�����У�","28");
		codeValues.put("ZB09��������Ա����ҵ��λ����ȼ���","911");
		codeValues.put("ZB09��������ҵ��λ����ȼ���","912");
		codeValues.put("ZB09��������Ա����ҵ��λרҵ������λ��","C98");
		codeValues.put("ZB09��������ҵ��λרҵ������λ��","C99");
		codeValues.put("ZB09������������ͨ���˸�λ��","E09");
		codeValues.put("ZB09��������ҵ��λ��ͨ���˸�λ��","G09");
		
		codeValues.put("ZB09һ�����������񾯲쾯Աְ�����У�","20");
		codeValues.put("ZB09�������������񾯲쾯Աְ�����У�","21");
		codeValues.put("ZB09�������������񾯲쾯Աְ�����У�","22");
		codeValues.put("ZB09�ļ����������񾯲쾯Աְ�����У�","23");
		codeValues.put("ZB09һ����Ա�����񾯲쾯Աְ�����У�","24");
		codeValues.put("ZB09������Ա�����񾯲쾯Աְ�����У�","25");
		codeValues.put("ZB09������Ա�����񾯲쾯Աְ�����У�","26");
		
		codeValues.put("ZB09һ������רԱ��ִ������Աְ��ȼ���","601");
		codeValues.put("ZB09��������רԱ��ִ������Աְ��ȼ���","602");
		codeValues.put("ZB09һ���߼�������ִ������Աְ��ȼ���","603");
		codeValues.put("ZB09�����߼�������ִ������Աְ��ȼ���","604");
		codeValues.put("ZB09�����߼�������ִ������Աְ��ȼ���","605");
		codeValues.put("ZB09�ļ��߼�������ִ������Աְ��ȼ���","606");
		codeValues.put("ZB09һ��������ִ������Աְ��ȼ���","607");
		codeValues.put("ZB09����������ִ������Աְ��ȼ���","608");
		codeValues.put("ZB09����������ִ������Աְ��ȼ���","609");
		codeValues.put("ZB09�ļ�������ִ������Աְ��ȼ���","610");
		codeValues.put("ZB09һ����Ա��ִ������Աְ��ȼ���","611");
		codeValues.put("ZB09������Ա��ִ������Աְ��ȼ���","612");
		
		codeValues.put("ZB09��������Ա��������ִ��Ա��","7118");
		codeValues.put("ZB09������������ִ��Ա��","7119");

		codeValues.put("ZB09һ���߼������������о�Ա��","7201");
		codeValues.put("ZB09�����߼������������о�Ա��","7202");
		codeValues.put("ZB09һ�������������о�Ա��","7203");
		codeValues.put("ZB09���������������о�Ա��","7204");
		codeValues.put("ZB09���������������о�Ա��","7205");
		codeValues.put("ZB09�ļ������������о�Ա��","7206");
		codeValues.put("ZB09һ����Ա�������о�Ա��","7207");
		codeValues.put("ZB09������Ա�������о�Ա��","7208");
		codeValues.put("ZB09������Ա�������о�Ա��","7209");
		codeValues.put("ZB09�ļ���Ա�������о�Ա��","7210");
		codeValues.put("ZB09������Ա�������о�Ա��","7211");
		codeValues.put("ZB09��ϰ��Ա�������о�Ա��","7212");
		codeValues.put("ZB09��������Ա�������о�Ա��","7218");
		codeValues.put("ZB09�����������о�Ա��","7219");

		codeValues.put("ZB09��������Ա�����ھ�����ְ��","7318");
		codeValues.put("ZB09���������ھ�����ְ��","7319");

		codeValues.put("ZB09��������Ա������������Ԥ��Ա��","7418");
		codeValues.put("ZB09����������������Ԥ��Ա��","7419");

		codeValues.put("ZB09��������Ա��������������ϢԱ��","7518");
		codeValues.put("ZB09������������������ϢԱ��","7519");
		
		codeValues.put("ZB65����","01");
		codeValues.put("ZB65���������ƺ�","01111");
		codeValues.put("ZB65��һ�ȹ�","01112");
	    codeValues.put("ZB65�Ƕ��ȹ�","01113");
	    codeValues.put("ZB65�����ȹ�","01114");
	    codeValues.put("ZB65�ν�","01115");
	    codeValues.put("ZB65����","01119");
	    codeValues.put("ZB65�ͽ�","02");
	    codeValues.put("ZB65����Ա���ɴ���","021");
	    codeValues.put("ZB65���棨����Ա��","02110");
	    codeValues.put("ZB65�ǹ�������Ա��","02112");
	    codeValues.put("ZB65�Ǵ��������Ա��","02113");
	    codeValues.put("ZB65����������Ա��","02114");
	   	codeValues.put("ZB65��ְ������Ա��","02117");
	    codeValues.put("ZB65����������Ա��","02119");
	    codeValues.put("ZB65��ҵְ�����ɴ���","022");
	    codeValues.put("ZB65���棨��ҵְ����","02220");
	    codeValues.put("ZB65�ǹ�����ҵְ����","02222");
	    codeValues.put("ZB65�Ǵ������ҵְ����","02223");
	    codeValues.put("ZB65��һ������ҵְ����","02224");
	    codeValues.put("ZB65����������ҵְ����","02225");
	    codeValues.put("ZB65��ְ����ҵְ����","02226");
		codeValues.put("ZB65���ò쿴����ҵְ����","02227");
		codeValues.put("ZB65��������ҵְ����","02228");
		codeValues.put("ZB65�й������ž����˼��ɴ���","023");
		codeValues.put("ZB65���棨���ˣ�","02230");
		codeValues.put("ZB65���ؾ��棨���ˣ�","02231");
		codeValues.put("ZB65�ǹ������ˣ�","02232");
		codeValues.put("ZB65�Ǵ�������ˣ�","02233");
		codeValues.put("ZB65��ְ���������Σ�����","02234");
		codeValues.put("ZB65��ְ�����ˣ�","02237");
		codeValues.put("ZB65�������������ˣ�","02239");
		codeValues.put("ZB65�й�����������������Ա���ɴ���","024");
		codeValues.put("ZB65���棨��Ա��","02240");
		codeValues.put("ZB65���ؾ��棨��Ա��","02241");
		codeValues.put("ZB65��������ְ����Ա��","02247");
		codeValues.put("ZB65���Ų쿴����Ա��","02248");
		codeValues.put("ZB65�����ż�����Ա��","02249");
		codeValues.put("ZB65�й���������Ա���ɴ���","025");
		codeValues.put("ZB65���棨��Ա��","02250");
		codeValues.put("ZB65���ؾ��棨��Ա��","02251");
		codeValues.put("ZB65��������ְ�񣨵�Ա��","02257");
		codeValues.put("ZB65�����쿴����Ա��","02258");
		codeValues.put("ZB65������������Ա��","02259");
		codeValues.put("ZB65��ҵ��λ������Ա����","026");
		codeValues.put("ZB65���棨��ҵ��λ������Ա��","02610");
		codeValues.put("ZB65�ǹ�����ҵ��λ������Ա��","02620");
		codeValues.put("ZB65���͸�λ�ȼ����߳�ְ����ҵ��λ������Ա��","02630");
		codeValues.put("ZB65��������ҵ��λ������Ա��","02640");
		return codeValues;
	}
	
	//��ȡ��Ԫ�������ֵ�������ַ�������

    private static String getCellValueByCell(Cell cell) {
        //�ж��Ƿ�Ϊnull��մ�
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {   //����cell�е��������������  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            DecimalFormat df = new DecimalFormat("0");  
            cellValue =  df.format(cell.getNumericCellValue());
            break;  
        case HSSFCell.CELL_TYPE_STRING:  
        	cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
        	cellValue = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA: 
            cellValue = cell.getCellFormula();
            break;  
        default:  
            System.out.println("unsuported sell type");  
        break;  
        }  
        
        return cellValue;
    }
}
