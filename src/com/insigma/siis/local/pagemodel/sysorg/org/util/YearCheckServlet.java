package com.insigma.siis.local.pagemodel.sysorg.org.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
import org.hibernate.Transaction;
import org.hsqldb.lib.StringUtil;

import com.fr.json.JSONObject;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.GbkcFile;
import com.insigma.siis.local.business.entity.HJXJAdd;
import com.insigma.siis.local.business.entity.PersionFile;
import com.insigma.siis.local.business.entity.TJLXAdd;
import com.insigma.siis.local.business.entity.WJGLAdd;
import com.insigma.siis.local.business.entity.YearCheck;
import com.insigma.siis.local.business.entity.YearCheckFile;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class YearCheckServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String separator = System.getProperty("file.separator");
	public static final List<String> FILE1 = Arrays.asList("doc", "wps","docx"); // �ļ�����һ
	
	public YearCheckServlet(){
		super();
	}
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("*************���뷽��");
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String method = request.getParameter("method");
		//����ģ��
		if (method != null && method.equals("download")) {
			String fileName = URLDecoder.decode(request.getParameter("fileName")+"", "UTF-8");
			String downloadfileName = request.getParameter("downloadfileName");
			download(request, response, fileName,downloadfileName);
			return;
		//����������ʾ
		}else if(method != null && method.equals("search")){
			String keyword = URLDecoder.decode(request.getParameter("keyword")+"", "UTF-8");
			//String dept = request.getParameter("dept");
			//String usertype = request.getParameter("usertype");
			List<String> listData=getListDate(keyword);
			//����json��ʽ
			response.getWriter().write(net.sf.json.JSONArray.fromObject(listData).toString());
		//����ԭ���ϴ�
		}else if(method != null && method.equals("addadmonishing")){
			//addadmonishing(request, response);
		//�����ļ�����
		}else if("YearCheckFile".equals(method)){		
			downloadYearCheckFileFile(request, response);
		}else if("PersionFile".equals(method)){		
			downloadYearCheckFileFile(request, response);
		}else if("PersionFileNew".equals(method)){
			String filename = request.getParameter("filename");
			downloadYearCheckFileFileNew(request, response,filename);
		}else if("Impfile".equals(method)){		
			String sort=request.getParameter("sort");
			String a0000=request.getParameter("a0000");
			String time=request.getParameter("time");
			String filepath=impfile(request, response);
			String message=saveimp(filepath,sort,a0000,time);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("adsc".equals(method)){
			
			String xfoid=request.getParameter("oid");
			String filepath=adsc(request,response);
			String message=saveadfj(filepath,xfoid);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("adsc1".equals(method)){
			
			String xfoid=request.getParameter("oid");
			String filepath=adsc1(request,response);
			String message=saveadfj1(filepath,xfoid);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("adsc2".equals(method)){
			
			String xfoid=request.getParameter("oid");
			String filepath=adsc2(request,response);
			String message=saveadfj2(filepath,xfoid);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("adsc3".equals(method)){
			
			String xfoid=request.getParameter("oid");
			String filepath=adsc3(request,response);
			String message=saveadfj3(filepath,xfoid);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("adsc4".equals(method)){
			
			String xfoid=request.getParameter("oid");
			String filepath=adsc4(request,response);
			String message;
			try {
				message = saveadfj4(filepath,xfoid);
			
			PrintWriter out = response.getWriter();
			out.write(message);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("adsc5".equals(method)){
			
			String xfoid=request.getParameter("oid");
			String filepath=adsc5(request,response);
			String message=saveadfj5(filepath,xfoid);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("historyfile".equals(method)){
			
			String uuid1=request.getParameter("oid1");
			String uuid2=request.getParameter("oid2");
			String uuid3=request.getParameter("oid3");
			
			String filepaths=htsc(request,response);
			String filepath1 = "";
			String filepath2 = "";
			String filepath3 = "";
			String message = "";
			String message2 = "";
			String message3 = "";
			String[] path = filepaths.split("@@@");
			if(path.length==3){
				filepath1 = path[2];
				filepath2 = path[1];
				filepath3 = path[0];				
				message=saveht(filepath1,uuid1);
				message2=saveht(filepath2,uuid2);
				message3=saveht(filepath3,uuid3);
			}else if(uuid1==""&&uuid2!=""&&uuid3!=""){
				filepath2 = path[1];
				filepath3 = path[0];				
				message="success";
				message2=saveht(filepath2,uuid2);
				message3=saveht(filepath3,uuid3);
			}else if(uuid2==""&&uuid1!=""&&uuid3!=""){
				filepath1 = path[1];
				filepath3 = path[0];				
				message=saveht(filepath1,uuid1);
				message3=saveht(filepath3,uuid3);
			}else if(uuid3==""&&uuid1!=""&&uuid2!=""){
				filepath1 = path[1];
				filepath2 = path[0];				
				message=saveht(filepath1,uuid1);
				message2=saveht(filepath2,uuid2);
			}else if(uuid1==""&&uuid2==""){
				filepath3 = path[0];							
				message="success";
				message3=saveht(filepath3,uuid3);
			}else if(uuid1==""&&uuid3==""){
				filepath2 = path[0];							
				message="success";
				message2=saveht(filepath2,uuid2);
			}else if(uuid2==""&&uuid3==""){
				filepath1 = path[0];							
				message=saveht(filepath1,uuid1);
			}
									
			PrintWriter out = response.getWriter();
			out.write(message);
		/*}else if("schemefile".equals(method)){
			
			String uuid=request.getParameter("oid");
			String filepath=sfsc(request,response);
			String message=saveht(filepath,uuid);
			PrintWriter out = response.getWriter();
			out.write(message);
		}else if("leaderfile".equals(method)){
			
			String uuid=request.getParameter("oid");
			String filepath=lfsc(request,response);
			String message=saveht(filepath,uuid);
			PrintWriter out = response.getWriter();
			out.write(message);*/
		}
	}
	
	/**
	 * ����������ʾ--��ùؼ���
	 * @param keyword
	 * @param dept
	 * @return
	 */
	public List<String> getListDate(String keyword){
		List<String> list = new ArrayList<String>();
		HBSession sess = HBUtil.getHBSession();
		String sql =" from A01 where (a0101 like '"+keyword+"%' or a0102 like '"+keyword+"%')";
		/*//�ǳ�������Ա
		if(usertype==null||!"0".equals(usertype)){
			sql+=" and a0195 like '"+dept+"%'";
		}*/
		//System.out.println(sql);
		List<A01> a01list = sess.createQuery(sql).list();
		for(A01 a01:a01list){
			list.add(a01.getA0101()+"("+a01.getA0192a()+")");
		}
		return list;
	}
	/**
	 * �����ļ�
	 * @param request
	 * @param response
	 * @param fileName
	 * @param downloadfileName
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, String fileName,
			String downloadfileName) {
		File outFile2;
		ServletContext event = request.getSession().getServletContext();
		if("ygcgsld".equals(downloadfileName)){
			outFile2=new File(event.getRealPath("pages/goAbroad/onDuty/ygcgsld.xls"));
		}else if("yscgsld".equals(downloadfileName)){
			outFile2=new File(event.getRealPath("pages/goAbroad/intimate/yscgsld.xls"));
		}else if("jwzqyjqtmb".equals(downloadfileName)){
			outFile2=new File(event.getRealPath("pages/jwview/otherVerify/jwzqyjqtmb.xls"));
		}else if("yjshslsj".equals(downloadfileName)){
			outFile2=new File(event.getRealPath("pages/jwview/viewVerify/yjshslsj.xls"));
		}else{
			// ��������ʽ�����ļ���
			outFile2 = new File(downloadfileName);
		}
		InputStream fis;
		try {
			if("ygcgsld".equals(downloadfileName)){
				fis = new BufferedInputStream(new FileInputStream(event.getRealPath("pages/goAbroad/onDuty/ygcgsld.xls")));
			}else if("yscgsld".equals(downloadfileName)){
				fis = new BufferedInputStream(new FileInputStream(event.getRealPath("pages/goAbroad/intimate/yscgsld.xls")));
			}else if("jwzqyjqtmb".equals(downloadfileName)){
				fis = new BufferedInputStream(new FileInputStream(event.getRealPath("pages/jwview/otherVerify/jwzqyjqtmb.xls")));
			}else if("yjshslsj".equals(downloadfileName)){
				fis = new BufferedInputStream(new FileInputStream(event.getRealPath("pages/jwview/viewVerify/yjshslsj.xls")));
			}else{
				fis = new BufferedInputStream(new FileInputStream(downloadfileName));
			}			
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// ���response
			response.reset();
			// ����response��Header
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			response.addHeader("Content-Length", "" + outFile2.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ԭ���ϴ�
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	/*public void addadmonishing(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		System.out.println("***********�����ļ��ϴ�����");
		
		//�ļ�·��
		String oldfilepath =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("file"),"UTF-8"),"UTF-8");
		//UUID
		String uuid =  request.getParameter("uuid");
		String realSavePath =AppConfig.HZB_PATH + "/admonishingfile/"+uuid+"/";//������ļ�·��
		String filename = oldfilepath.substring(oldfilepath.lastIndexOf("\\") + 1);
		String newfilepath=realSavePath+filename;
		int bytesum = 0; 
		InputStream input = null;    
		OutputStream output = null;  
		try {
			File file = new File(realSavePath);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}
			input = new FileInputStream(oldfilepath);
	        output = new FileOutputStream(newfilepath);
	        byte[] buf = new byte[1024]; 
	        int bytesRead;        
	        while ((bytesRead = input.read(buf)) > 0) {
	            output.write(buf, 0, bytesRead);
	            bytesum+=bytesRead;
	        }
		} finally {
			input.close();
		    output.close();
		}
		//��������Ϣ��������������
		saveadmonishingFile(uuid,newfilepath,filename,bytesum+"");
		System.out.println(oldfilepath);
		System.out.println(newfilepath);
		System.out.println(bytesum);
	}*/
	
	/**
	 *  ���ϴ���ԭ����Ϣ���������ݿ���
	 * @param title		����
	 * @param filepath	�ļ���ַ
	 * @param filename	�ļ�����
	 * @return
	 */
	/*public int saveadmonishingFile(String uuid,String filepath,String filename,String fileSize){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//�������ݿ����
		HBSession sess = HBUtil.getHBSession();
		//��������
		YearCheck atta = new YearCheck();
		atta.setId(uuid);
		atta.setFileName(filename);		//�ļ�����
		atta.setFileSize(fileSize);		//�ļ���С
		atta.setFileUrl(filepath);		//�ļ��洢���·��
		//ִ�����ݿ����
		sess.saveOrUpdate(atta);	//ԭ���ļ���Ϣ
		sess.flush();
		ts.commit();
		return 0;
	}*/
	
	//�����ļ�
	public void downloadYearCheckFileFile(HttpServletRequest request,
				HttpServletResponse response) throws IOException{
		//��ô������ļ����·��
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		//�ļ�����
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
		/*��ȡ�ļ�*/
		File file = new File(filePath);
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
	
	
	//�����ļ�
	public void downloadYearCheckFileFileNew(HttpServletRequest request,
				HttpServletResponse response,String filename) throws IOException{
		//��ô������ļ����·��
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		filename=java.net.URLDecoder.decode(java.net.URLDecoder.decode(filename,"UTF-8"),"UTF-8");
		//�ļ�����
		//String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
		/*��ȡ�ļ�*/
		File file = new File(filePath);
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
	
	private String impfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/PersionFile/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				//String namecode= new String(fi.getName().getBytes("ISO8859_1"),"utf-8");
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				filePath=upload_file+name;
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
	
	private String adsc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/YearCheckFile/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				filePath=upload_file+name;
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
	
	
	private String adsc1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/khpj/gbkc/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				filePath=upload_file+name;
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
	
	private String adsc2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";
		String filePath1="";
		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/�ļ�����/�����ļ�/"+uuid+"/";// �ϴ�·��
			String upload_file1 = "�ļ�����/�����ļ�/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				String fileExtName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				
				filePath=upload_file+name;
				filePath1=upload_file1+name;
			
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				fi.getOutputStream().close();
			
				
			}

		}
		return filePath1;
	}
	private String adsc3(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";
		String filePath1="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/�ļ�����/�����ļ�/"+uuid+"/";// �ϴ�·��
			String upload_file1 = "�ļ�����/�����ļ�/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				String fileExtName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				
			if(FILE1.contains(fileExtName)) {
				
				//filePath=upload_file+name;
				filePath=upload_file+name;
				filePath1=upload_file1+name;
			}else {
				filePath = "0";
			}
				
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				fi.getOutputStream().close();
			
				
			}

		}
		return filePath1;
	}
	private String adsc4(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";
		String filePath1="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/���쿼��/�����ļ�/"+uuid+"/";// �ϴ�·��
			String upload_file1 = "/���쿼��/�����ļ�/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				String fileExtName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				
				
				//filePath=upload_file+name;
				filePath=upload_file+name;
				filePath1=upload_file1+name;
				
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				fi.getOutputStream().close();
				
			}

		}
		return filePath1;
	}
	private String adsc5(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";
		String filePath1="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/�����ļ�/�����ļ�/"+uuid+"/";// �ϴ�·��
			String upload_file1 = "/�����ļ�/�����ļ�/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				String fileExtName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				
				
				//filePath=upload_file+name;
				filePath=upload_file+name;
				filePath1=upload_file1+name;
				
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				fi.getOutputStream().close();
				
			}

		}
		return filePath1;
	}
	private String saveimp(String filepath,String sort,String a0000,String time) {
		HBSession sess = HBUtil.getHBSession();
		String xfoid = UUID.randomUUID().toString();
		Date date = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//String createtime = sdf.format(date);
		// ��������
		PersionFile persionfile = (PersionFile) sess.get(PersionFile.class, xfoid);
		if(persionfile==null){
			persionfile=new PersionFile();
			persionfile.setId(xfoid);
		}
		File file=new File(filepath);
		persionfile.setFileName(file.getName()); // �ļ�����
		persionfile.setFileSize(String.valueOf(file.length())); // �ļ���С
		persionfile.setFileUrl(filepath); // �ļ��洢���·��
		persionfile.setA0000(a0000);
		persionfile.setFilesort(sort);
		persionfile.setTime(time);
		// ִ�����ݿ����
		sess.saveOrUpdate(persionfile); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}
	
	private String saveadfj(String filepath,String xfoid) {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);
		if(adAppendFile==null){
			adAppendFile=new YearCheckFile();
			adAppendFile.setId(xfoid);
		}
		File file=new File(filepath);
		adAppendFile.setFileName(file.getName()); // �ļ�����
		adAppendFile.setFileSize(String.valueOf(file.length())); // �ļ���С
		adAppendFile.setFileUrl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.saveOrUpdate(adAppendFile); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}
	private String saveadfj1(String filepath,String xfoid) {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);
		if(adAppendFile==null){
			adAppendFile=new GbkcFile();
			adAppendFile.setId(xfoid);
		}
		File file=new File(filepath);
		adAppendFile.setFilename(file.getName()); // �ļ�����
		adAppendFile.setFilesize(String.valueOf(file.length())); // �ļ���С
		adAppendFile.setFileurl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.saveOrUpdate(adAppendFile); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}
	private String saveadfj2(String filepath,String xfoid) {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		WJGLAdd wjgladd=new WJGLAdd();
//		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);

		wjgladd.setAdd00(xfoid);
		File file=new File(AppConfig.HZB_PATH +"/"+filepath);
		wjgladd.setFilename(file.getName()); // �ļ�����
		wjgladd.setFilesize(String.valueOf(file.length()/1024) +" KB"); // �ļ���С
		wjgladd.setFileurl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.save(wjgladd); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}
	private String saveadfj3(String filepath,String xfoid) {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		WJGLAdd wjgladd=new WJGLAdd();
//		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);

		wjgladd.setAdd00(xfoid);
		
	if(filepath.equals("0")) {
		return "�������ϴ������ϸ�ʽ���ļ�";
	}else {
		File file=new File(AppConfig.HZB_PATH +"/"+filepath);
		wjgladd.setFilename(file.getName()); // �ļ�����
		wjgladd.setFilesize(String.valueOf(file.length()/1024) +" KB"); // �ļ���С
		wjgladd.setFileurl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.save(wjgladd); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}
	}
private String saveadfj4(String filepath,String xfoid) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		HJXJAdd hjxjadd=new HJXJAdd();
		CommQuery cqbs=new CommQuery();
//		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);
		
		String count="select count(add00) count from HJXJADD a where a.add00='"+xfoid+"'  ";
		List<HashMap<String, Object>> list;
			list = cqbs.getListBySQL(count);
			HashMap<String, Object> map=list.get(0);
			String count1 = map.get("count")==null?"":map.get("count").toString();
	if(Integer.parseInt(count1)<1){	

		hjxjadd.setAdd00(xfoid);
		
	
		File file=new File(AppConfig.HZB_PATH +"/"+filepath);
		hjxjadd.setFilename(file.getName()); // �ļ�����
		hjxjadd.setFilesize(String.valueOf(file.length()/1024) +" KB"); // �ļ���С
		hjxjadd.setFileurl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.save(hjxjadd); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}else {
		return "";
	}
}
	private String saveadfj5(String filepath,String xfoid) {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		TJLXAdd tjlxadd=new TJLXAdd();
//		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);
		String jk=UUID.randomUUID().toString().replace("-", "");
		tjlxadd.setAdd00(jk);
		
	
		File file=new File(AppConfig.HZB_PATH +"/"+filepath);
		tjlxadd.setFilename(file.getName()); // �ļ�����
		tjlxadd.setFilesize(String.valueOf(file.length()/1024) +" KB"); // �ļ���С
		tjlxadd.setFileurl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.save(tjlxadd); // ԭ���ļ���Ϣ
		sess.flush();
		return jk;
	}
	private String htsc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";
		String filePaths="";
		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String uuid = UUID.randomUUID().toString();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/CreateSysOrg/"+uuid+"/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				filePath=upload_file+name;
				try {
					fi.write(new File(filePath));
					
					filePaths = filePath+"@@@"+filePaths;
				} catch (Exception e) {
					e.printStackTrace();
				}
				fi.getOutputStream().close();
			}

		}
		return filePaths;
	}
	
	private String saveht(String filepath,String xfoid) {
		HBSession sess = HBUtil.getHBSession();
		// ��������
		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);
		if(adAppendFile==null){
			adAppendFile=new YearCheckFile();
			adAppendFile.setId(xfoid);
		}
		File file=new File(filepath);
		adAppendFile.setFileName(file.getName()); // �ļ�����
		adAppendFile.setFileSize(String.valueOf(file.length())); // �ļ���С
		adAppendFile.setFileUrl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.saveOrUpdate(adAppendFile); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
	}
	
	private String sfsc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/CreateSysOrg/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				filePath=upload_file+name;
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
	
	private String lfsc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("ISO8859_1");
		//uploader.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;
		String filePath="";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/CreateSysOrg/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}

			while (iter.hasNext()) {

				FileItem fi = iter.next();
				if(fi.getSize()==0){
					continue;
				}
				String namecode= new String(fi.getName().getBytes("ISO8859_1"),"gb2312");
				
				//String name= new String(fi.getName().substring(fi.getName().lastIndexOf(separator) + 1).getBytes("ISO8859_1"), "utf-8"); 
				//String name=fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
                 
				String name=namecode.substring(namecode.lastIndexOf(separator)+1);
				
				filePath=upload_file+name;
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
}
