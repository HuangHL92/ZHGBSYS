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
	 * 点击导入
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
		
		Map<String,String> data= new HashMap<String, String>();//返回前台的MSG
		
		String filePath = "";
		String str = "";
		
		//创建接收文件对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = response.getWriter();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String fileName = "";
		String upload_file = AppConfig.HZB_PATH + "/temp/upload/"+uuid+"/";// 上传路径
		
		try {
			fileItems = uploader.parseRequest(request);  //文件本身
		} catch (FileUploadException e) {
			str = "导入文件不能为空，请重新导入！";
			data.put("error", str);
			e.printStackTrace();
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}
				
		if(!fileItems.isEmpty()){
			iter = fileItems.iterator();
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
					
			while(iter.hasNext()){
						
				FileItem fi = iter.next();
						
				if(fi.getSize()==0){
					str = "导入文件不能为空，请重新导入！";
					data.put("error", str);
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				if (!(fi.getName().endsWith(EXCEL_XLS) || (fi.getName().endsWith(EXCEL_XLSX))||(fi.getName().endsWith(EXCEL_ZIP)))) {
					data.put("error", "文件不是Zip或Excel格式,请检查！");
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
				//获取文件名字
				fileName = fi.getName().substring(fi.getName().lastIndexOf(System.getProperty("file.separator"))+1);
				fi.getOutputStream().close();
			}
		}
		
		//读取服务器中的文件，并进行处理
		data = Imp(unid,filePath,fileName,upload_file,uuid);
		
		File f = new File(filePath);
		f.delete();
		
		long s2 = System.currentTimeMillis();
		System.out.println("Excel套表导入，共计耗时："+((s2-s)/1000)+"秒");
		
		this.doSuccess(request, data);
		return this.ajaxResponse(request, response);
	}

	private Map<String, String> Imp(String unid, String path, String fileName, String upload_file, String uuid) {
		HBSession session = HBUtil.getHBSession();
		Map<String,String> data= new HashMap<String, String>();//返回前台的MSG
		boolean hasPhoto = false;
		File photoFile = null;
		File excelFile = null;
		
		String unzip = "";
		//如果是ZIP，则解压
		if(fileName.endsWith(EXCEL_ZIP)){
			unzip = AppConfig.HZB_PATH + "/temp/upload/unzip/"+uuid+"/";										// 解压路径
			File file = new File(unzip);															// 如果文件夹不存在则创建
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
				data.put("error", "请使用正确的ZIP文件！");
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
			excelFile = new File(path); // 创建文件对象
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
		UploadHzbFileBSZHGB uploadbs = new UploadHzbFileBSZHGB();		// 业务处理bs
		
		FileInputStream is = null;
		try {
			//先获取一张sheet页的所有信息
			// 同时支持Excel 2003、2007
			is = new FileInputStream(excelFile);
			
			Workbook workbook = getWorkbok(is, excelFile);//读取文件
			
			//判断模板是否一致，可以通过判断版本号和sheet页总数
			int sheetNums = workbook.getNumberOfSheets();
			Sheet sheetZero = workbook.getSheetAt(0);
			Cell version = sheetZero.getRow(1).getCell(7);
			
			if(!((sheetNums == 11)) && ("V2017.01".equals(version))){
				data.put("error", "导入的Excel文件版本与填写模板不一致，请检查！");
				return data;
			}
			
			//第一步：创建临时表
			uploadbs.createTempTableTYGS(tableExt);
			
			
			//第二步：先初始化mapB0111
			Sheet sheetOne = workbook.getSheetAt(1);//B01单位
			String rootB01Name = "";//B01根单位
			//获得总行数
			int rowNum =sheetOne.getLastRowNum();
			
			List<String> upListB0101 = new ArrayList<String>();//存放上级b0101
			List<String> listB0101 = new ArrayList<String>();//存放本机b0101
			
			for(int i = 2;i<=rowNum;i++){//初始化  存放上级b0101的list
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
			
			for(int i = 3;i<=rowNum;i++){//正式 读b0101，确定上下级关系,从第4行开始读
				String name = "";
				String sjName = "";
				//String realName = "";//name - sjName 剩下的名字
				int n = 0;//用于记录上级的字段长度
				
				Cell unitName = sheetOne.getRow(i).getCell(0);
				if(unitName!=null && !"".equals(unitName.toString().trim())){
					//去upListB0101中，找到对应的上级     规则： unitName包含 上级的名称，并且其上级的名称长度最长，即为该unitName的上级
					name = unitName.toString().trim().replaceAll(" ", "");
					for(String v : upListB0101){
						if(name.contains(v)&&!name.equals(v)){//如果包含（去除自己本身），说明v是unitName的上级或上几级单位
							if(v.length()>n){
								n = v.length();
								sjName = v;
								
								//realName = name.substring(sjName.length());
								
								//判断mapSJ中是否已经有记录，没有则添加
								if(mapSJ.get(sjName)==null){
									mapSJ.put(sjName, new ArrayList<String>());
								}
							}
						}
					}
					//将下级放入上级的map中
					mapSJ.get(sjName).add(name);
				}
			}
			
			//获取上级mapSJ,给每个下级单位编码
			//首先取上级单位为   根单位  的本级单位
			List<String> SJSon = mapSJ.get(rootB01Name);   //子集
			String SJBM = mapB0111.get(rootB01Name);    //例如：获取001.001
			String sjbm = "";
			String convert = "";
			for(int j=0;j<SJSon.size();j++){
				//根据j生成b0111
				String son = SJSon.get(j);
				convert = ConvertTo36(j+1);
				//BJBM = SJBM + newB0111
				sjbm = SJBM + "." + convert;
				//存入mapB0111中
				mapB0111.put(son, sjbm);
				
				createB0111(mapSJ,mapB0111,son);
			}
			
			mapB0111.put("其他单位", "-1");
			
			//第三步：初始化人员数据 mapA0000
			Map<String, String> mapA0000 = new HashMap<String, String>();
			//listA0000  用来判断是否有重复的人
			List<String> listA0000 = new ArrayList<String>();
			
			Sheet sheetTwo = workbook.getSheetAt(2);
			int rowTwo =sheetTwo.getLastRowNum();
			Cell c1 = null;
			Cell c2 = null;
			String key = "";
			for(int j=2;j<=rowTwo;j++){
				c1 = sheetTwo.getRow(j).getCell(0);//姓名
				c2 = sheetTwo.getRow(j).getCell(1);//身份证号
				if(c1==null || c2==null || "".equals(c1.toString().trim()) ||
						 "".equals(c2.toString().trim())){
					//data.put("error", "人员基本信息——存在重复人员，请检查"+(j+1)+"行数据！");
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim())) ){
						continue;
					}
					data.put("error", "A01人员——第"+(j+1)+"行存在人员未填写:姓名/身份证号码！");
					return data;
				}
				key = c1.toString()+c2.toString();
				
				//查是否人员重复
				for(String allPerson : listA0000){
					if(key.equals(allPerson)){
						data.put("error", "A01人员——存在重复人员，请检查"+(j+1)+"行数据！");
						return data;
					}
				}
				listA0000.add(key);
				
				String A0000uuid = UUID.randomUUID().toString();
				mapA0000.put(key, A0000uuid);
			}
			session.createSQLQuery("update IMP_RECORD set TOTAL_NUMBER = '"+mapA0000.size()+"',EMP_DEPT_NAME = '"+rootB01Name+"' where IMP_RECORD_ID = '"+IMP_RECORD_ID+"'").executeUpdate();
			
			// 第四步：读取code_value,正式遍历Sheet
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
				System.out.println("--------------------------------------------进入Sheet表"+(i+1));
				data = dispose(i,sheet,unid,session,data,mapB0111,mapA0000,codeValues,tableExt);
				
				long end = System.currentTimeMillis();
				System.out.println("--------------------------------------------Sheet表"+(i+1)+"处理完成,耗时："+((end-start)/1000)+"秒");
				
				//返回error则结束循环
				if(data.get("error")!=null){
					throw new Exception(data.get("error"));
				}
				if(i==10){
					//对照片进行处理
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
					
					//更新A01中的学历学位信息
					TYGSsqlUtil.updateA08(tableExt);
					
					data.put("success", "Excel成功导入临时库！");
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
			
		//获得总行数和总列数
		int rowNum =sheet.getLastRowNum();
		int colNum=sheet.getRow(1).getPhysicalNumberOfCells();
		int n = 0;//计数
			
		//遍历行，写数据 (mapB0111)
		Cell cell = null;
		String c = "";
		
		//B01表___单位基本情况信息
		if(i==1){
			pstmt = conn.prepareStatement("insert into B01"+tableExt+"("+TYGSsqlUtil.B01EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				String bjB0111 = mapB0111.get(sheet.getRow(j).getCell(0).toString().trim().replaceAll(" ", ""));//获取本级编码
				String sjB0111 = "";//获取上级编码
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
					if(k==0){//机构名称处理
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
					if(k==3||k==4||k==5||k==6){  //单位所在政区
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value!=null){
								pstmt.setObject(k+3, value);
							}else{
								data.put("error", "B01单位,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
								return data;
							}
						}
						continue;
					}
					if(k==7){  //法人单位
						if("法人单位".equals(c)){
							pstmt.setObject(k+3, "1");
						}
						if("内设机构".equals(c)){
							pstmt.setObject(k+3, "2");
						}
						if("机构分组".equals(c)){
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
		//A01----人员基本信息
		if(i==2){
			pstmt = conn.prepareStatement("insert into A01"+tableExt+"("+TYGSsqlUtil.A01EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				//前面已经判断非空
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
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value != null){
								pstmt.setObject(k+2, value);
							}else{
								data.put("error", "A01人员,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
								return data;
							}
						}
						continue;
					}
					if(k==26){
						if("是".equals(c)){
							pstmt.setObject(k+2, "1");
						}else if("否".equals(c)){
							pstmt.setObject(k+2, "0");
						}else{
							data.put("error", "A01人员,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
							return data;
						}
						continue;
					}
					if(k==27){  //统计关系所在单位,暂时存中文
						String tjName = c.replaceAll(" ", "");
						String A0195 = mapB0111.get(tjName);
						if(A0195 != null){
							pstmt.setObject(k+2, A0195);
						}else{
							pstmt.setObject(k+2, "-1");//其他单位
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
		//A02---职务信息
		if(i==3){
			pstmt = conn.prepareStatement("insert into A02"+tableExt+"("+TYGSsqlUtil.A02EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A02职务,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A02职务,第"+(j+1)+"行，该人员在“A01人员”不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						if(k==2){//任职机构
							pstmt.setObject(3, null);
							pstmt.setObject(4, null);
						}else{
							pstmt.setObject(k+2, null);
						}
						continue;
					}
					if(k==2){//任职机构
						String name = c.replaceAll(" ", "");
						String bjB0111 = mapB0111.get(name);
						if("-1".equals(bjB0111)){
							pstmt.setString(3, "其他单位");
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
						if("是".equals(c)){
							pstmt.setObject(k+2, "1");
						}else if("否".equals(c)){
							if(k==6){
								pstmt.setObject(k+2, "2");
							}else{
								pstmt.setObject(k+2, "0");
							}
						}else{
							data.put("error", "A02职务,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
							return data;
						}
						continue;
					}
					if(k==4 || k==11 || k==13){
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value != null){
								pstmt.setObject(k+2, value);
							}else{
								data.put("error", "A02职务,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
								return data;
							}
						}
						continue;
					}
					if(k==17){
						if("输出".equals(c)){
							pstmt.setObject(k+2, "true");
						}else if("不输出".equals(c)){
							pstmt.setObject(k+2, "false");
						}else{
							data.put("error", "A02职务,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
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
		//A05---职务职级信息
		if(i==4){
			pstmt = conn.prepareStatement("insert into A05"+tableExt+"("+TYGSsqlUtil.A05EXCEL+") values(?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A05职务职级,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A05职务职级,第"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第3列开始读取
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==2){
						if("职务层次".equals(c)){
							pstmt.setObject(k+1, "0");
						}else if("享受的职级".equals(c)){
							pstmt.setObject(k+1, "1");
						}else{
							data.put("error", "A05职务职级,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
							return data;
						}
						continue;
					}
					if(k==3 || k==7){
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
						String codeType = "";
						if(k==3){
							Cell v = sheet.getRow(j).getCell(2);
							if(v!=null && "职务层次".equals(v.toString())){
								codeType = "ZB09";
							}
							if(v!=null && "享受的职级".equals(v.toString())){
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A05职务职级,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
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
			
		//A06---专业技术任职资格信息
		if(i==5){
			pstmt = conn.prepareStatement("insert into A06"+tableExt+"("+TYGSsqlUtil.A06EXCEL+") values(?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A06专业技术,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A06专业技术,第"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第3列开始读取
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==2 || k==5){
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A06专业技术,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
								return data;
							}
						}
						continue;
					}
					if(k==7){
						if("输出".equals(c)){
							pstmt.setObject(k+1, "true");
						}else if("不输出".equals(c)){
							pstmt.setObject(k+1, "false");
						}else{
							data.put("error", "A06专业技术,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
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
		//A08----学历学位信息
		if(i==6){
			pstmt = conn.prepareStatement("insert into A08"+tableExt+"("+TYGSsqlUtil.A08EXCEL+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A08学历学位,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A08学历学位,"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第4列开始读取
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==3 || k==5 || k==11 || k==12){
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A08学历学位,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
								return data;
							}
						}
						continue;
					}
					if(k==14){
						if("输出".equals(c)){
							pstmt.setObject(k+1, "true");
						}else if("不输出".equals(c)){
							pstmt.setObject(k+1, "false");
						}else{
							data.put("error", "A08学历学位,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
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
		//A14----奖惩信息
		if(i==7){
			pstmt = conn.prepareStatement("insert into A14"+tableExt+"("+TYGSsqlUtil.A14EXCEL+") values(?,?,?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A14奖惩,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A14奖惩,"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第3列开始读取
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==3 || k==6 || k==7 || k==9){
						String newcell = c.replaceAll("\\(", "（").replaceAll("\\)", "）");
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
							value = codeValues.get(codeType+newcell.replaceAll("（","\\(").replaceAll("）","\\)"));
							if(value != null){
								pstmt.setObject(k+1, value);
							}else{
								data.put("error", "A14奖惩,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
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
		//A15----年度考核信息
		if(i==8){
			pstmt = conn.prepareStatement("insert into A15"+tableExt+"("+TYGSsqlUtil.A15EXCEL+") values(?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A15考核,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A15考核,"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第3列开始读取
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
							data.put("error", "A15考核,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
							return data;
						}
						continue;
					}
					if(k==3){
						if(c==null || "".equals(c)){
							data.put("error", "A15考核,"+(j+1)+"行"+(k+1)+"列，数据不能为空值！请检查");
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
		
		//A36---家庭成员及社会关系信息
		if(i==9){
			pstmt = conn.prepareStatement("insert into A36"+tableExt+"("+TYGSsqlUtil.A36EXCEL+") values(?,?,?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A36家庭及社会关系,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A36家庭及社会关系,"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第4列开始读取
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
		
		//A99Z1---补充信息
		if(i==10){
			pstmt = conn.prepareStatement("insert into A99Z1"+tableExt+"("+TYGSsqlUtil.A99Z1EXCEL+") values(?,?,?,?,?,?)");
			for(j=2;j<=rowNum;j++){ //从第三行开始
				Cell c1 = sheet.getRow(j).getCell(0);
				Cell c2 = sheet.getRow(j).getCell(1);
				if(c1==null || c2==null || "".equals(c1.toString().trim()) || "".equals(c2.toString().trim()) ){
					if((c1==null||"".equals(c1.toString().trim())) && (c2==null||"".equals(c2.toString().trim()))){
						continue;
					}
					data.put("error", "A99Z1补充,第"+(j+1)+"行，该人员未填写:姓名/身份证号码！");
					return data;
				}
				String key = c1.toString()+c2.toString();
				String a0000 = mapA0000.get(key);
				if(a0000==null){
					data.put("error", "A99Z1补充,"+(j+1)+"行，该人员在“A01人员”中不存在！请检查");
					return data;
				}
				
				pstmt.setString(1, a0000);
				pstmt.setString(2, UUID.randomUUID().toString());
				for(int k=2;k<colNum;k++){   //直接从第4列开始读取
					cell = sheet.getRow(j).getCell(k);
					c = getCellValueByCell(cell);
					if(c==null || "".equals(c)){
						pstmt.setObject(k+1, null);
						continue;
					}
					if(k==2 || k==4){
						if("是".equals(c)){
							pstmt.setObject(k+1, "1");
						}else if("否".equals(c)){
							pstmt.setObject(k+1, "0");
						}else{
							data.put("error", "A99Z1补充,"+(j+1)+"行"+(k+1)+"列，数据有误！请检查");
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
		
		//最后也到执行一次
		pstmt.executeBatch();
		pstmt.clearBatch();
		
		pstmt.close();
		conn.commit();
		conn.close();
		
		} catch (Exception e) {    //先catch错误，处理错误后再抛出
			e.printStackTrace();
			throw new Exception("导入失败！请检查Excel，Sheet表"+(i+1)+"，第"+(j+1)+"行数据！");
		} 
		
		return data;
		
	}
	

	private void createB0111(Map<String, List<String>> mapSJ,
			Map<String, String> mapB0111, String son) throws Exception {
		List<String> SJSon = mapSJ.get(son);   //子集
		if(SJSon!=null){
			String SJBM = mapB0111.get(son);    //例如：获取001.001
			String sjbm = "";
			String convert = "";
			for(int j=0;j<SJSon.size();j++){
				//根据j生成b0111
				son = SJSon.get(j);
				convert = ConvertTo36(j+1);
				//BJBM = SJBM + newB0111
				sjbm = SJBM + "." + convert;
				//存入mapB0111中
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
        	throw new Exception("机构编码超出范围！");
        }
        while(result.length()<3){
        	result = "0" + result;
        }
        return result;
    }

	/**
	 * 判断Excel的版本,获取Workbook
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
		codeValues.put("ZB09试用期人员（综合管理类）","1A98");
		codeValues.put("ZB09其他（综合管理类）","1A99");
		codeValues.put("ZB09试用期人员（专业技术类）","1B98");
		codeValues.put("ZB09其他（专业技术类）","1B99");
		codeValues.put("ZB09试用期人员（行政执法类）","1C98");
		codeValues.put("ZB09其他（行政执法类）","1C99");
		
		codeValues.put("ZB09试用期人员（人民警察警员职务序列）","27");
		codeValues.put("ZB09其他（人民警察警员职务序列）","28");
		codeValues.put("ZB09试用期人员（事业单位管理等级）","911");
		codeValues.put("ZB09其他（事业单位管理等级）","912");
		codeValues.put("ZB09试用期人员（事业单位专业技术岗位）","C98");
		codeValues.put("ZB09其他（事业单位专业技术岗位）","C99");
		codeValues.put("ZB09其他（机关普通工人岗位）","E09");
		codeValues.put("ZB09其他（事业单位普通工人岗位）","G09");
		
		codeValues.put("ZB09一级警长（人民警察警员职务序列）","20");
		codeValues.put("ZB09二级警长（人民警察警员职务序列）","21");
		codeValues.put("ZB09三级警长（人民警察警员职务序列）","22");
		codeValues.put("ZB09四级警长（人民警察警员职务序列）","23");
		codeValues.put("ZB09一级警员（人民警察警员职务序列）","24");
		codeValues.put("ZB09二级警员（人民警察警员职务序列）","25");
		codeValues.put("ZB09三级警员（人民警察警员职务序列）","26");
		
		codeValues.put("ZB09一级警务专员（执法勤务警员职务等级）","601");
		codeValues.put("ZB09二级警务专员（执法勤务警员职务等级）","602");
		codeValues.put("ZB09一级高级警长（执法勤务警员职务等级）","603");
		codeValues.put("ZB09二级高级警长（执法勤务警员职务等级）","604");
		codeValues.put("ZB09三级高级警长（执法勤务警员职务等级）","605");
		codeValues.put("ZB09四级高级警长（执法勤务警员职务等级）","606");
		codeValues.put("ZB09一级警长（执法勤务警员职务等级）","607");
		codeValues.put("ZB09二级警长（执法勤务警员职务等级）","608");
		codeValues.put("ZB09三级警长（执法勤务警员职务等级）","609");
		codeValues.put("ZB09四级警长（执法勤务警员职务等级）","610");
		codeValues.put("ZB09一级警员（执法勤务警员职务等级）","611");
		codeValues.put("ZB09二级警员（执法勤务警员职务等级）","612");
		
		codeValues.put("ZB09试用期人员（深圳市执法员）","7118");
		codeValues.put("ZB09其他（深圳市执法员）","7119");

		codeValues.put("ZB09一级高级警长（深圳市警员）","7201");
		codeValues.put("ZB09二级高级警长（深圳市警员）","7202");
		codeValues.put("ZB09一级警长（深圳市警员）","7203");
		codeValues.put("ZB09二级警长（深圳市警员）","7204");
		codeValues.put("ZB09三级警长（深圳市警员）","7205");
		codeValues.put("ZB09四级警长（深圳市警员）","7206");
		codeValues.put("ZB09一级警员（深圳市警员）","7207");
		codeValues.put("ZB09二级警员（深圳市警员）","7208");
		codeValues.put("ZB09三级警员（深圳市警员）","7209");
		codeValues.put("ZB09四级警员（深圳市警员）","7210");
		codeValues.put("ZB09初级警员（深圳市警员）","7211");
		codeValues.put("ZB09见习警员（深圳市警员）","7212");
		codeValues.put("ZB09试用期人员（深圳市警员）","7218");
		codeValues.put("ZB09其他（深圳市警员）","7219");

		codeValues.put("ZB09试用期人员（深圳警务技术职务）","7318");
		codeValues.put("ZB09其他（深圳警务技术职务）","7319");

		codeValues.put("ZB09试用期人员（深圳市气象预报员）","7418");
		codeValues.put("ZB09其他（深圳市气象预报员）","7419");

		codeValues.put("ZB09试用期人员（深圳市气象信息员）","7518");
		codeValues.put("ZB09其他（深圳市气象信息员）","7519");
		
		codeValues.put("ZB65奖励","01");
		codeValues.put("ZB65授予荣誉称号","01111");
		codeValues.put("ZB65记一等功","01112");
	    codeValues.put("ZB65记二等功","01113");
	    codeValues.put("ZB65记三等功","01114");
	    codeValues.put("ZB65嘉奖","01115");
	    codeValues.put("ZB65其他","01119");
	    codeValues.put("ZB65惩戒","02");
	    codeValues.put("ZB65公务员纪律处分","021");
	    codeValues.put("ZB65警告（公务员）","02110");
	    codeValues.put("ZB65记过（公务员）","02112");
	    codeValues.put("ZB65记大过（公务员）","02113");
	    codeValues.put("ZB65降级（公务员）","02114");
	   	codeValues.put("ZB65撤职（公务员）","02117");
	    codeValues.put("ZB65开除（公务员）","02119");
	    codeValues.put("ZB65企业职工纪律处分","022");
	    codeValues.put("ZB65警告（企业职工）","02220");
	    codeValues.put("ZB65记过（企业职工）","02222");
	    codeValues.put("ZB65记大过（企业职工）","02223");
	    codeValues.put("ZB65降一级（企业职工）","02224");
	    codeValues.put("ZB65降二级（企业职工）","02225");
	    codeValues.put("ZB65撤职（企业职工）","02226");
		codeValues.put("ZB65留用察看（企业职工）","02227");
		codeValues.put("ZB65开除（企业职工）","02228");
		codeValues.put("ZB65中国人民解放军军人纪律处分","023");
		codeValues.put("ZB65警告（军人）","02230");
		codeValues.put("ZB65严重警告（军人）","02231");
		codeValues.put("ZB65记过（军人）","02232");
		codeValues.put("ZB65记大过（军人）","02233");
		codeValues.put("ZB65降职（级）降衔（级）","02234");
		codeValues.put("ZB65撤职（军人）","02237");
		codeValues.put("ZB65开除军籍（军人）","02239");
		codeValues.put("ZB65中国共产主义青年团团员纪律处分","024");
		codeValues.put("ZB65警告（团员）","02240");
		codeValues.put("ZB65严重警告（团员）","02241");
		codeValues.put("ZB65撤销团内职务（团员）","02247");
		codeValues.put("ZB65留团察看（团员）","02248");
		codeValues.put("ZB65开除团籍（团员）","02249");
		codeValues.put("ZB65中国共产党党员纪律处分","025");
		codeValues.put("ZB65警告（党员）","02250");
		codeValues.put("ZB65严重警告（党员）","02251");
		codeValues.put("ZB65撤销党内职务（党员）","02257");
		codeValues.put("ZB65留党察看（党员）","02258");
		codeValues.put("ZB65开除党籍（党员）","02259");
		codeValues.put("ZB65事业单位工作人员处分","026");
		codeValues.put("ZB65警告（事业单位工作人员）","02610");
		codeValues.put("ZB65记过（事业单位工作人员）","02620");
		codeValues.put("ZB65降低岗位等级或者撤职（事业单位工作人员）","02630");
		codeValues.put("ZB65开除（事业单位工作人员）","02640");
		return codeValues;
	}
	
	//获取单元格各类型值，返回字符串类型

    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
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
