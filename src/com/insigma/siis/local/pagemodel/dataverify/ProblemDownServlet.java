package com.insigma.siis.local.pagemodel.dataverify;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hsqldb.lib.StringUtil;


import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.JwBatch;
import com.insigma.siis.local.business.entity.JwOpinion;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.ZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.exportexcel.ExportPhotoExcel;
import com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis.GeneralStatisticsPageModel;
import com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis.TwoDStatisticsShowPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.SimpleStatisticsPageModel;
import com.lbs.cp.util.SysManagerUtil;
import com.picCut.jspsmart.upload.Request;
import com.utils.DateUtil;

import net.sf.json.JSONArray;

public class ProblemDownServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ProblemDownServlet.class);
	private static final String separator = System.getProperty("file.separator");
	private LogUtil applog = new LogUtil();
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		doPost(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void doPost(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		String method = paramHttpServletRequest.getParameter("method");
		if("impA15Check".equals(method)) {
			//���˵���ǰУ��
			PrintWriter out = paramHttpServletResponse.getWriter();
			String filepath=uploadfile(paramHttpServletRequest,paramHttpServletResponse,method);
			String drnf=paramHttpServletRequest.getParameter("drnf");
			String result=impA15Check(filepath,drnf);
			out.write(result);
		}
		else if("impA15".equals(method)) {
			//���˵���
			PrintWriter out = paramHttpServletResponse.getWriter();
			String filepath=uploadfile(paramHttpServletRequest,paramHttpServletResponse,method);
			String batchid=paramHttpServletRequest.getParameter("batchid");
			String drnf=paramHttpServletRequest.getParameter("drnf");
			String result=impA15(filepath,batchid,drnf,paramHttpServletRequest);
			out.write(result);
		}else if("impA15By7zOrZip".equals(method)) {
			//����ѹ��������
			PrintWriter out = paramHttpServletResponse.getWriter();
			String filepath=uploadfile(paramHttpServletRequest,paramHttpServletResponse,method);
			String batchid=paramHttpServletRequest.getParameter("batchid");
			String drnf=paramHttpServletRequest.getParameter("drnf");
			String suffix=paramHttpServletRequest.getParameter("suffix");
			String result=impA15By7zOrZip(filepath,batchid,drnf,suffix,paramHttpServletRequest);
			out.write(result);
		}
		else if("gbjwopinion".equals(method)) {
			//�ɼ�ϵͳ���͹����������ί���
			PrintWriter out = paramHttpServletResponse.getWriter();
			String batchid=paramHttpServletRequest.getParameter("batchid");
			String person=paramHttpServletRequest.getParameter("person");
			String result=updateJWOpinion(batchid,person);
			out.write(URLEncoder.encode(result,"GBK"));
		}else if (method.equals("downFile")) {// �����ļ�����
			downFile(paramHttpServletRequest, paramHttpServletResponse);
//			String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(paramHttpServletRequest.getParameter("prid"),"UTF-8"),"UTF-8");
//			String foldername = path.substring(0,path.lastIndexOf("/")+1);
//			UploadHelpFileServlet.delFolder(foldername);
		}else if (method.equals("printPdfFile")) {// �����ļ�����
			printPdfFile(paramHttpServletRequest, paramHttpServletResponse);
		}else if(method.equals("downFiletj2")){//��άͳ�Ʊ������
			downFile3(paramHttpServletRequest, paramHttpServletResponse);
		}else if(method.equals("downFileSys")) {// ������Ϣ����
			downFileSys(paramHttpServletRequest, paramHttpServletResponse);
		}else if(method.equals("downFileExcelry")){//excel�����ϱ�����
			downFileExcelry(paramHttpServletRequest, paramHttpServletResponse);
		}else if("downloadExcel".equals(method)) {
			//������Ŀ��exportexcel�ļ�������ļ�
			String fileName = URLDecoder.decode(paramHttpServletRequest.getParameter("fileName") + "", "UTF-8");
			
			downloadExportExcel(paramHttpServletRequest, paramHttpServletResponse,fileName);
		}else{
			downFile2(paramHttpServletRequest, paramHttpServletResponse);
		}
	}

	private String impA15By7zOrZip(String filepath, String batchid, String drnf,String suffix,
			HttpServletRequest paramHttpServletRequest) {
		String result="";
		try {
			FileInputStream is = null;
			String unzip = "";										// ��ѹ·��
			String uuid = UUID.randomUUID().toString(); 
			unzip = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid + "/";										// ��ѹ·��
//			File file = new File(unzip);															// ����ļ��в������򴴽�
//			if (!file.exists() && !file.isDirectory()) {
//				file.mkdirs();
//			}
//			if(suffix.equalsIgnoreCase("7z")) {
//					Zip7z.unzip7zAll(filepath, unzip, "20171020");
//			}
			if(suffix.equalsIgnoreCase("zip")) {
				ZipUtil.decompress(filepath, unzip, "");
				File filetemp = new File(filepath);
				String filename=filetemp.getName().substring(0, filetemp.getName().lastIndexOf(".zip"));
				File file = new File(unzip+"/"+filename+"/");
				paramHttpServletRequest.getSession().setAttribute("fileName", filetemp.getName());
				File[] files = file.listFiles();
				long startTime = System.currentTimeMillis();    //��ȡ��ʼʱ��
				result=manageFile(batchid,drnf,files,paramHttpServletRequest,startTime);
		}
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		 return result;
	}
	
	
	private String impA15Check(String filepath, String drnf) {
		HBSession sess = HBUtil.getHBSession();
		try {
		//���ж��Ƿ�������ļ���ͨ���ļ����ķ�ʽ�Լ��������
		File excelFile = new File(filepath); // �����ļ�����
		String isUpload= sess.createSQLQuery("select count(1) from A15FileRecords where fileName='"+excelFile.getName()+"' and impYear='"+drnf+"'").uniqueResult().toString();
		if(Integer.parseInt(isUpload)!=0) {
			return "error";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	

	private String impA15(String filepath,String batchid,String drnf,HttpServletRequest paramHttpServletRequest) {
		Date date =new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		File excelFile = new File(filepath); // �����ļ�����
		//���ļ�������session
		paramHttpServletRequest.getSession().setAttribute("fileName", excelFile.getName());
		File[] files= {excelFile};
		long startTime = System.currentTimeMillis();    //��ȡ��ʼʱ��
		String result=manageFile(batchid,drnf,files,paramHttpServletRequest,startTime);
		return result;		
	
	}

	
	
	
	private String manageFile(String batchid,String drnf,File[] files, HttpServletRequest paramHttpServletRequest,long startTime) {
		HBSession sess = HBUtil.getHBSession();
		String year=drnf;
		String a1517="";
		FileInputStream is = null;
		//��¼����ɹ���ʧ����
		int success=0;
		int failure=0;
		List<String> sqlList = new ArrayList<String>();
		HashMap<String,A15> sessMap = new HashMap<String,A15>();
			try {
				
				sess.beginTransaction();
				sqlList.add("delete a15report where batchid='"+batchid+"'");
				HashSet<String> hashSet = new HashSet<String>();
				HashMap<String,String> renamesMap = new HashMap<String,String>();
			for(File excelFile:files) {
				is = new FileInputStream(excelFile);
				Workbook workbook = getWorkbok(is, excelFile);
				Sheet sheet = workbook.getSheetAt(0); 
				int rowNum = sheet.getLastRowNum();
				if(rowNum<1) {
					return "error";
				}
				for(int j=1;j<=rowNum;j++){
					Row row=sheet.getRow(j);
					//��������
					if(row==null||row.equals(null)) {
						continue;
					}
						String name=CelltoString(row.getCell(1));
						name=name.replaceAll(" ", "").replaceAll("��", "");
						if(hashSet.contains(name)) {
							renamesMap.remove(name);
							renamesMap.put(name, name);
						}
						hashSet.add(name);
				}
				
			}
				
			for(File excelFile:files) {
				is = new FileInputStream(excelFile);
				Workbook workbook = getWorkbok(is, excelFile);
				Sheet sheet = workbook.getSheetAt(0); 
				int rowNum = sheet.getLastRowNum();
				if(rowNum<1) {
					return "error";
				}
				
				for(int i=1;i<=rowNum;i++) {
					Row row=sheet.getRow(i);
					//��������
					if(row==null||row.equals(null)) {
						continue;
					}
					String num=CelltoString(row.getCell(0));
					String name=CelltoString(row.getCell(1));
					name=name.replaceAll(" ", "").replaceAll("��", "");
					
					
					String duty=CelltoString(row.getCell(2));
					String kaohe=CelltoString(row.getCell(3));
					
					//�жϱ���excel��������Ա
					
					if(renamesMap.containsKey(name)&&!StringUtil.isEmpty(name)){
						String sql="insert into a15report values(sys_guid(),'','"+kaohe+"','"+year+"','"+batchid+"','"+duty+"','excel����("+name+")','"+num+"','3','0')";
						sqlList.add(sql);
//						String sql="insert into a15report select  sys_guid(),a0000,'"+a1517+"','"+year+"','"+batchid+"','"+duty+"','excel����("+name+")','"+num+"'  from a01 where a0101='"+name+"'";
						failure++;
						continue;
					}
					
					if(StringUtil.isEmpty(name)) {
						String sql="insert into a15report values(sys_guid(),'','"+kaohe+"','"+year+"','"+batchid+"','"+duty+"','�Ҳ���ƥ����Ա("+name+")','"+num+"','3','0')";
						sqlList.add(sql);
						failure++;
						continue;
					}
					if(StringUtil.isEmpty(kaohe)) {
						String sql="insert into a15report values(sys_guid(),'','"+kaohe+"','"+year+"','"+batchid+"','"+duty+"','���˵ȴβ��淶("+name+")','"+num+"','3','0')";
						sqlList.add(sql);
						failure++;
						continue;
					}
					//���˵ȴ��ж�
					if("����".equals(kaohe)) {
						a1517="1";
					}
					else if("��ְ".equals(kaohe)) {
						a1517="2";
					}
					else if("������ְ".equals(kaohe)) {
						a1517="3";
					}
					else if("����ְ".equals(kaohe)) {
						a1517="4";
					}
					else if("�����ȴ�".equals(kaohe)) {
						a1517="5";
					}
					else {
						//���˵ȴβ��淶
						String sql="insert into a15report values(sys_guid(),'','"+kaohe+"','"+year+"','"+batchid+"','"+duty+"','���˵ȴβ��淶("+name+")','"+num+"','3','0')";
						sqlList.add(sql);
//						String sql="insert into a15report select  sys_guid(),a0000,'"+kaohe+"','"+year+"','"+batchid+"','"+duty+"','���˵ȴβ��淶("+name+")','"+num+"'  from a01 where a0101='"+name+"'";
						failure++;
						continue;
					}
					
					
					List<String> list=sess.createSQLQuery("select a0000 from a01 where a0101='"+name+"' and a0163='1'").list();
					if(list.size()==1) {
						//������˲�����������
						//ֻ��һ��  ֱ�Ӹ���
						String a0000=list.get(0);
						List<String>  list2= sess.createSQLQuery("select a1500 from a15 where a0000='"+a0000+"' and a1521='"+year+"'").list();
						if(list2.size()<1) {
							//û�м�¼  ֱ�� ����
							A15 a15=new A15();
							a15.setA0000(a0000);
							a15.setA1500(UUID.randomUUID().toString());
							a15.setA1521(year);
							a15.setA1517(a1517);
							a15.setA1527("3");
							//�ɹ���������������Ѷ������session�У���ȷ�ϵ���ʱ�ڲ���
//							sess.save(a15);
							sessMap.put(name, a15);
							success++;
							//������־��¼
//							applog.createLog("3151", "A15", a0000, name, "������¼", new Map2Temp().getLogInfo(new A15(), a15));
						}else {
							//��ȿ����ظ�
							String sql="insert into a15report select  sys_guid(),a0000,'"+a1517+"','"+year+"','"+batchid+"','"+duty+"','"+year+"��ȿ����ظ�("+name+")','"+num+"','1','0'  from a01 where a0101='"+name+"' and a0163='1'";
							sqlList.add(sql);
							failure++;
						}
						
					}else if(list.size()<1) {
						//���޴���,����  //����ԭ���Ҳ���ƥ����Ա
						String sql="insert into a15report values(sys_guid(),'','"+a1517+"','"+year+"','"+batchid+"','"+duty+"','�Ҳ���ƥ����Ա("+name+")','"+num+"','3','0')";
						sqlList.add(sql);
						failure++;
						continue;
					}else {
						//�ж�� ��Ա  ��¼һ����Ϣ
//						String sql="insert into a15report  select  sys_guid(),a0000,'"+a1517+"','"+year+"','"+batchid+"',a0192,'ϵͳ������("+name+")','"+num+"'  from a01 where a0101='"+name+"'";
						String sql="insert into a15report values(sys_guid(),'','"+a1517+"','"+year+"','"+batchid+"','"+duty+"','ϵͳ������("+name+")','"+num+"','2','0')";
						sqlList.add(sql);
						failure++;
					}
				
				}
				}
				
				 HBUtil.batchSQLexqute(sqlList);
				 sess.getTransaction().commit();
				 
				 
			} catch (Exception e) {
				sess.getTransaction().rollback();
				e.printStackTrace();
				return "error";
			}
			
			
			
			sess.flush();
			//��Map����session
			paramHttpServletRequest.getSession().setAttribute("sessMap", sessMap);
			paramHttpServletRequest.getSession().setAttribute("drnf", drnf);
			long endTime = System.currentTimeMillis();    //��ȡ����ʱ��
			System.out.println("�ļ�������ʱ��" + (endTime - startTime) + "ms");    
			return "success--"+success+"--"+failure;
	}

	private String updateJWOpinion(String batchid, String person) {
		try {
			JSONArray jsonArray = JSONArray.fromObject(person);
			List<JwOpinion> list=JSONArray.toList(jsonArray, JwOpinion.class);
			HBSession sess = HBUtil.getHBSession();
			JwBatch jb=(JwBatch) sess.createSQLQuery("select * from jw_batch where id='"+batchid+"'").addEntity(JwBatch.class).uniqueResult();
			jb.setStatus("3");
			sess.update(jb);
			for(JwOpinion jo:list) {
				sess.update(jo);
			}
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return "����ʧ��";
		}
		
		return "���ճɹ�";
	}

	public void init() throws ServletException {
	}
	
	public void downFile2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String Str = request.getParameter("prid");
		List<HashMap<String, Object>> exl = new ArrayList<HashMap<String,Object>>();
		if(Str.contains("@")){
			String[] data = Str.split("@");
			String[] number = data[0].split(",");//����
			
			int sum = 0;
			for(int i=0;i<GeneralStatisticsPageModel.query_condition.size();i++){
	    		String allname = (String) GeneralStatisticsPageModel.query_condition.get(i).get("Queryname");
	    		String allnumber = number[i];
	    		sum += Integer.parseInt(number[i]);//��������
	    		HashMap<String, Object> map = new HashMap<String, Object>();
	    		map.put("name", allname);
	    		map.put("number", allnumber);
	    		exl.add(map);
	    	}
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name", "�ϼ�");
    		map2.put("number", sum);
    		exl.add(map2);
    		
		}else if(Str.contains("gdcy")){
			HBSession sess = HBUtil.getHBSession();
			Str = SimpleStatisticsPageModel.strarr;
			String groupid = Str.split("\\$")[0].trim().replace("|", "'").split("group")[0];
			String SQ001 = Str.split("\\$")[1];
			String[] n_arr = null;
			if("1".equals(SQ001)){
				String[] arr = {"Ů��","��������","���й���Ա"};
				n_arr = arr;
			}else if("2".equals(SQ001)){
				String[] arr = {"��ʿ","˶ʿ","ѧʿ"};
				n_arr = arr;
			}else if("3".equals(SQ001)){
				String[] arr = {"�о���","��ѧ����","��ѧ��ר","��ר","���м�����"};
				n_arr = arr;
			}else if("4".equals(SQ001)){
				String[] arr = {"30�꼰����","31����35��","36����40��","41����45��","46����50��","51����54��","55����59��","60�꼰����"};
				n_arr = arr;
			}else if("5".equals(SQ001)){
				String[] arr = {"����","�ɹ���","����","����","ά�����","׳��","����"};
				n_arr = arr;
			}else if("9".equals(SQ001)){
				String[] arr = {"���Ҽ���ְ","���Ҽ���ְ","ʡ������ְ","ʡ������ְ","���ּ���ְ","���ּ���ְ","�ش�����ְ","�ش�����ְ","��Ƽ���ְ","��Ƽ���ְ","��Ա","����Ա","��������Ա","����"};
				n_arr = arr;
			}else if("10".equals(SQ001)){
				String[] arr = {"����һ��","�������ڸ���","ʡ����һ��","ʡ�������ڸ���","��ʡ����һ��","��ʡ�������ڸ���","��(�ء��ݡ���)","��(�С�������)","��(��)"};
				n_arr = arr;
			}
			int sum = 0;
			String sql = "select QC002,QC003 from query_condition where SQ001 = '"+SQ001+"'";
			List<Object[]> list = sess.createSQLQuery(sql).list();
			if(list != null && list.size() > 0){
				String QC003 = list.get(0)[1].toString();
				String tj = QC003.replace("groupid", groupid);
				List<Object[]> list11 = sess.createSQLQuery(tj).list();
				if(list11 != null){
					Object[] oo = list11.get(0);
					for(int i = 0; i < oo.length; i++){
						HashMap<String, Object> um = new HashMap<String, Object>();
						String number =	oo[i].toString();
						sum += Integer.parseInt(number);
						um.put("name", n_arr[i]);
						um.put("number", number);
						exl.add(um);
					}
					
				}
			}
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name", "�ϼ�");
    		map2.put("number", sum);
    		exl.add(map2);
		}else{
			HBSession sess = HBUtil.getHBSession();
			Str = SimpleStatisticsPageModel.strarr;
			String groupid = Str.split("\\$")[0].replace("|", "'");
			String SQ001 = Str.split("\\$")[1];
			String sql = "select QC002,QC003 from query_condition where SQ001 = '"+SQ001+"'";
			int sum = 0;
			List<Object[]> list = sess.createSQLQuery(sql).list();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> um = new HashMap<String, Object>();
				Object[] a=list.get(i);
				String QC002 = a[0].toString();
				String QC003 = a[1].toString().replace("|", "'");
				String tj = QC003+groupid;
				String number = sess.createSQLQuery("select count(1) from ( "+tj+" )").uniqueResult().toString();
				sum += Integer.parseInt(number);
				um.put("name", QC002);
				um.put("number", number);
				exl.add(um);
			}
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name", "�ϼ�");
    		map2.put("number", sum);
    		exl.add(map2);
		}
		ExportPhotoExcel epe = new ExportPhotoExcel();
		Workbook workbook = epe.exportTjExcel(exl);
		String filename = "ͳ�ƽ��_"+System.currentTimeMillis()+".xls";
		downloadExcel(workbook, response, filename);
	}
	
	public void downFile3(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String str = TwoDStatisticsShowPageModel.table_data_str;
		HashMap<String, String> exl = new HashMap<String,String>();
		String[] vert_name_arr = null;
		String[] tran_name_arr = null;
		String[] data_arr = null;
		Integer tran_num = null;
		Integer vert_num = null;
		String data_str = "";
		String[] data = null;
   
    	//�ָ�����ݴ�
    	data = str.split("\\$");
    	String tran_name_str = data[0];
    	tran_num = Integer.parseInt(data[1]);
		String vert_name_str = data[2];
		vert_num =Integer.parseInt(data[3]);
		data_str = data[4];
		vert_name_arr = vert_name_str.split("@");
		tran_name_arr = tran_name_str.split("@");
		data_arr = data_str.split("#");
		for(int i=0;i<vert_num;i++){
			exl.put("vert_name"+i,vert_name_arr[i]);
			for(int j=0;j<tran_num;j++){
				String[] data_add = data_arr[i].split("@");
				exl.put("vert"+i+"_tran"+j,data_add[j]);
			}
		}  
		for(int i=0;i<tran_num;i++){
			exl.put("tran_name"+i,tran_name_arr[i]);
		}		
		exl.put("vert_num",vert_num.toString());
		exl.put("tran_num",tran_num.toString());
		
		ExportPhotoExcel epe = new ExportPhotoExcel();
		Workbook workbook = epe.exportEwTjExcel(exl);
		String filename = "ͳ�ƽ��.xls";
		downloadExcel(workbook, response, filename);
	}
	
	/**
	 * // excel �����ϱ�����
	 */
	public void downFileExcelry(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
			String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"),"UTF-8"),"UTF-8");
			String filename = path.substring(path.lastIndexOf("/")+1);
			
	        /*��ȡ�ļ�*/
	        File file = new File(path);
	        /*����ļ�����*/
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*����ļ����ȴ���0*/
            if (fileLength != 0) {
                /*����������*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*���������*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
        
	}
	
	/**
	 * // ������Ϣ����
	 */
	public void downFileSys(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"),"UTF-8"),"UTF-8");
		String ss=File.separatorChar+"";
		path=path.replace("@@", ss);
		String filename = path.substring(path.lastIndexOf("/")+1);
		
        /*��ȡ�ļ�*/
        File file = new File(path);
        /*����ļ�����*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*����ļ����ȴ���0*/
            if (fileLength != 0) {
                /*����������*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*���������*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
        }
        file = null;
        
	}
	
	/**
	 * �����ļ�(�����ļ�����)����
	 */
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"),"UTF-8"),"UTF-8");
		String filename = path.substring(path.lastIndexOf("/")+1);
		String foldername = path.substring(0,path.lastIndexOf("."));
        /*��ȡ�ļ�*/
        File file = new File(path);
        /*����ļ�����*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*����ļ����ȴ���0*/
            if (fileLength != 0) {
                /*����������*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*���������*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
//              UploadHelpFileServlet.delFolder(foldername);
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
//            UploadHelpFileServlet.delFolder(foldername);
        }
        file = null;
        
	}
	
	/**
	 * �����ļ�(�����ļ�����)����
	 */
	public void printPdfFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"),"UTF-8"),"UTF-8");
		String filename = path.substring(path.lastIndexOf("/")+1);
		String foldername = path.substring(0,path.lastIndexOf("."));
        /*��ȡ�ļ�*/
        File file = new File(path);
        /*����ļ�����*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpg;charset=GBK");
			//response.addHeader("Content-Disposition", "attachment;filename="
			//		+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*����ļ����ȴ���0*/
            if (fileLength != 0) {
                /*����������*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*���������*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
//              UploadHelpFileServlet.delFolder(foldername);
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
//            UploadHelpFileServlet.delFolder(foldername);
        }
        file = null;
        
	}
	
	protected void downloadExcel(Workbook workbook, HttpServletResponse response, String filename) throws IOException {
        OutputStream out = response.getOutputStream();
    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
    response.setContentType("application/msexcel;charset=UTF-8");
    workbook.write(out);
    out.close();        
} 

	
	private void downloadExportExcel(HttpServletRequest request, HttpServletResponse response,String fileName) {
		ServletContext event = request.getSession().getServletContext();
		File file;
		try {

			
				String path = "pages/exportexcel/" + fileName;
				file = new File(event.getRealPath(path));
			
			// ���response
			response.reset();

			// ����response��Header
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel");

			DataInputStream in = new DataInputStream(new FileInputStream(file));

			OutputStream out = response.getOutputStream();

			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.close();
			in.close();

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

			toClient.write(bufferOut);
			toClient.flush();
			toClient.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	@SuppressWarnings("unchecked")
	private String uploadfile(HttpServletRequest request, HttpServletResponse response,String method) throws IOException {

		String uuid=UUID.randomUUID().toString();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath = "";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/"+method+"/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}
			
			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
			
					String name = fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
					filePath = upload_file + name;
				
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				fi.getOutputStream().close();
			}
		}
		return filePath;
	}
	
	public static Workbook getWorkbok(InputStream in, File file) throws IOException {
		Workbook wb = null;
		if (file.getName().endsWith("xls")) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith("xlsx")) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		in.close();
		return wb;
	}
	
	public static String CelltoString(Cell c) {
		if(c!=null) {
			c.setCellType(c.CELL_TYPE_STRING);
		}
		return c==null||"null".equals(c)?"":c.toString();
	}
}
