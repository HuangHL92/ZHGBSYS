package com.insigma.siis.local.pagemodel.dataverify;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.hibernate.Transaction;

import com.fr.json.JSONObject;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A06temp;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A11temp;
import com.insigma.siis.local.business.entity.A14temp;
import com.insigma.siis.local.business.entity.A15temp;
import com.insigma.siis.local.business.entity.A29temp;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A30temp;
import com.insigma.siis.local.business.entity.A31temp;
import com.insigma.siis.local.business.entity.A36temp;
import com.insigma.siis.local.business.entity.A37temp;
import com.insigma.siis.local.business.entity.A41temp;
import com.insigma.siis.local.business.entity.A53temp;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A57temp;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01temp;
import com.insigma.siis.local.business.entity.B01tempb01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Folder;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.PackFilter;
import com.insigma.siis.local.pagemodel.cbdHandler.CBDTools;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.repandrec.local.ImpThread;
import com.insigma.siis.local.util.ZipUtil;
  
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}  
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("GBK");
//		String encode = request.getCharacterEncoding();
//		CommonQueryBS.systemOut("encode:"+encode);
//		String tempMethod = request.getParameter("method");
//		String tempRequest = request.getParameter("request");
//		String r = new String(tempRequest.getBytes("ISO-8859-1"),"utf-8");
//		CommonQueryBS.systemOut("method:"+tempMethod);
//		CommonQueryBS.systemOut("request:"+r);
//		PrintWriter out = response.getWriter();
////		out.append("Served at: ����").append(request.getContextPath());
//		out.append("1,�㽭ʡ��������,����������,2016-07-15 19:05:27,δƥ�䵽���������ܵ��롣,01,zb3,2,2,26,��ϵ��,80000000,3c958fbf175944f6b319711c3e5a1c4b,,1,");
//	}   
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String clienttype = request.getParameter("clienttype");
		if (method.equals("zzbFile")) {//�ϴ����
			if(clienttype!=null && clienttype.equals("1")){
				uploadZzbFile2(request, response);
			} else {
				uploadZzbFile(request, response);
			}
		} else if (method.equals("zzbFileTYGS")) {//zzbFileTYGSͨ�ø�ʽ�ϴ����
			if(clienttype!=null && clienttype.equals("1")){
				//uploadTYGSFile2(request, response);
			} else {
				uploadTYGSFile(request, response);
			}
		} else if (method.equals("zzbFileImp")) {//����
			if(clienttype!=null && clienttype.equals("1")){
				uploadZzbFileImp2(request, response); 
			} else {
				uploadZzbFileImp(request, response); 
			}
		} else if (method.equals("zzbFilePsn")) {//���ݰ�����
			uploadzzbFilePsn(request, response); 
		} else if (method.equals("zzbFileImpPsn")) {//
			uploadzzbFileImpPsn(request, response); 
		} else if (method.equals("zzbFileImpRefresh")) {
			zzbFileImpRefresh(request, response); 
		}else if(method.equals("uploadAttachment")){
			uploadAttachment(request, response);
		}else if("uploadZip".equals(method)){
			uploadZip(request, response);
		}else if("uploadFolder".equals(method)){		//���ӵ����ļ��ϴ�
			uploadFolder(request, response);
		}else if("deleteFolderFile".equals(method)){		//���ӵ����ļ�ɾ��
			deleteFolderFile(request, response);
		}else if("downloadFolderFile".equals(method)){		//���ӵ����ļ�����
			downloadFolderFile(request, response);
		} else if (method.equals("uploadHzbOldFile")) {			//�ϴ�����ϰ汾
			uploadHzbOldFile(request, response);
		} else if("hzbOldFileImp".equals(method)){			//�����ϰ汾
			uploadHzbOldFileImp(request, response);
		} else if (method.equals("uploadHzbMakeFile")) {			//�ϴ�����׸İ汾
			uploadHzbMakeFile(request, response);
		} else if("hzbMakeFileImp".equals(method)){			//�����׸İ汾
			uploadHzbMakeFileImp(request, response);
		} else if (method.equals("zzbFileWages")) {//���ʵ���
			uploadzzbFileWages(request, response); 
		} else if (method.equals("zzbFileImpWages")) {//���ʵ���
			uploadzzbFileImpWages(request, response); 
		}
		
		
		
	} 
	
	private void uploadHzbMakeFileImp(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		try {
			String id = request.getParameter("uuid");
			String b0111new = request.getParameter("b0111new");
			String filename = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
			String a0165 = request.getParameter("a0165");
			String fxz = request.getParameter("fxz");
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,a0165) values ('"+ id +"','"+a0165+"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//ˢ�����ݵĴ�����
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpMakeHzbThreadNewCaNull thr = new ImpMakeHzbThreadNewCaNull(filename, id,b0111new,a0165, user,userVo,fxz);
			new Thread(thr,"���ݵ����߳�1").start();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
	}
	
	private void uploadHzbMakeFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// ����ļ��в������򴴽�
					file1.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString(); 
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//String md5Name = MD5.MD5(filename);
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
					} catch (Exception e) {
						e.printStackTrace();
						throw new AppException("�ϴ�ʧ��");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.document.getElementById('uuid').value='"+uuid
					+"';parent.parent.document.getElementById('subWinIdBussessId').value='"+uuid+"@"+URLEncoder.encode(URLEncoder.encode(filename,"UTF-8"),"UTF-8")+"';"
							+ "parent.parent.radow.doEvent('initX');"); 
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('���ʧ�ܣ�');</script>");
		}finally{
			factory = null;
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
		}
	}

	private void uploadHzbOldFileImp(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		try {
			String id = request.getParameter("uuid");
			String b0111new = request.getParameter("b0111new");
			String filename = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
			String a0165 = request.getParameter("a0165");
			String fxz = request.getParameter("fxz");
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,a0165) values ('"+ id +"','"+a0165+"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//ˢ�����ݵĴ�����
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpOldHzbThreadNewCaNull thr = new ImpOldHzbThreadNewCaNull(filename, id,b0111new,a0165, user,userVo,fxz);
			new Thread(thr,"���ݵ����߳�1").start();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
	}
	private void uploadHzbOldFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// ����ļ��в������򴴽�
					file1.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString(); 
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//String md5Name = MD5.MD5(filename);
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
					} catch (Exception e) {
						e.printStackTrace();
						throw new AppException("�ϴ�ʧ��");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.document.getElementById('uuid').value='"+uuid
					+"';parent.parent.document.getElementById('subWinIdBussessId').value='"+uuid+"@"+URLEncoder.encode(URLEncoder.encode(filename,"UTF-8"),"UTF-8")+"';"
							+ "parent.parent.radow.doEvent('initX');"); 
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('���ʧ�ܣ�');</script>");
		}finally{
			factory = null;
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
		}
	}
	private void uploadzzbFilePsn(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
//				if (fi.isFormField()) {
//					String fieldName = fi.getFieldName();
//					String fieldvalue = fi.getString(); 
//				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						System.out.println("���ݰ�����=================="+filename);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
//				}
			}
			HBSession sess =  HBUtil.getHBSession();
			conn = sess.connection();
			stmt = conn.createStatement();
			String sql5 = "select count(A0000) from a01";
			rs = stmt.executeQuery(sql5);
			String size = null;
			while(rs.next()){
				size = rs.getString(1);
			}
			String type2 = DBUtil.getDBType().toString();
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "1");
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else if(houzhui.equalsIgnoreCase("7z")){
				Zip7z.unzip7zOne(filePath, unzip, "20171020");
			} else if(houzhui.equalsIgnoreCase("zip")){
				Zip7z.unzip7zOne(filePath, unzip, null);
			} else {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "2");
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String type = map.get("type");
			CommonQueryBS.systemOut("dataversion------"+map.get("dataversion"));
			if (type!=null && type.equals("31")) {
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "���Ե��롣");
					map.put("packagetype", "����Ա����");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "��⵽���ݰ��汾���ɣ���ʹ��2019��HZB��Ա���ݰ���");
				}
			} else if(type!=null && "21".equals(type)){
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "���Ե��롣");
					map.put("packagetype", "����������");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "��⵽���ݰ��汾���ɣ���ʹ��2019��HZB���ݰ���");
				}
			} else if(type!=null && "23".equals(type)){
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "���Ե��롣");
					map.put("packagetype", "����������");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "��⵽���ݰ��汾���ɣ���ʹ��2019��ͨ�ø�ʽ���ݰ���");
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", "�޷����룬��ʹ����ȷ�����ݰ���");
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "hzb");
				map.put("gjgss", "1");
			} else if(houzhui.equalsIgnoreCase("7z")) {
				map.put("gjgs", "7z");
				map.put("gjgss", "1");
			} else if(houzhui.equalsIgnoreCase("zip")) {
				map.put("gjgs", "zip");
				map.put("gjgss", "1");
			} else {
				map.put("gjgs", "zb3");
				map.put("gjgss", "2");
			}
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();" +
//					"parent.Ext.getCmp('b0101').setValue('"+ org +"');" +
					"parent.Ext.getCmp('packagetype').setValue('"+ map.get("packagetype") +"');" +
					"parent.Ext.getCmp('packagetime').setValue('"+ map.get("time") +"');" +
					"parent.Ext.getCmp('orginfo').setValue('"+ map.get("orginfo") +"');" +
//					"parent.Ext.getCmp('b0111').setValue('"+ B0114 +"');" +
					"parent.document.getElementById('gjgs').value='"+ map.get("gjgs") +"';" +
					"parent.document.getElementById('gjgss').value='"+ map.get("gjgss") +"';" +
					"parent.document.getElementById('valueimp').value='"+ map.get("valueimp") +"';" +
					"parent.document.getElementById('psncount').value='"+ map.get("psncount") +"';" +
					"parent.document.getElementById('count1').value='"+ size +"';" +
					"parent.document.getElementById('type1').value='"+ type2 +"';" +
//					"parent.document.getElementById('linkpsn').value='"+ map.get("linkpsn") +"';" +
//					"parent.document.getElementById('linktel').value='"+ map.get("linktel") +"';" +
					"parent.document.getElementById('uuid').value='"+ uuid +"';" +
					"parent.document.getElementById('filename').value='"+ URLEncoder.encode(filename,"UTF-8") +"';" +
//					"parent.document.getElementById('b0131').value='"+ map.get("B0194") +"';" +
//					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
					"parent.Ext.getCmp('impBtn').setDisabled(false);parent.parent.clickquery();");
			out.println("</script>");
			//out.println("<script>parent.odin.alert('�����ɣ�');");
			//out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('���ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
		}
		
	}
	private void uploadzzbFileWages(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						System.out.println("���ݰ�����=================="+filename);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
//				}
			}
			HBSession sess =  HBUtil.getHBSession();
			conn = sess.connection();
			stmt = conn.createStatement();
			String sql5 = "select count(A0000) from a01";
			rs = stmt.executeQuery(sql5);
			String size = null;
			while(rs.next()){
				size = rs.getString(1);
			}
			String type2 = DBUtil.getDBType().toString();
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			Zip7z.unzip7zOne(filePath, unzip, null);
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String type = map.get("type");
			CommonQueryBS.systemOut("dataversion------"+map.get("dataversion"));
			if (type!=null && type.equals("A33")) {
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "���Ե��롣");
					map.put("packagetype", "��������������");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "��⵽���ݰ��汾���ɣ���ʹ��2019��ZIP�������ݰ���");
				}
			}else {
				map.put("valueimp", "2");
				map.put("orginfo", "�޷����룬��ʹ����ȷ�����ݰ���");
			}
			if(houzhui.equalsIgnoreCase("zip")) {
				map.put("gjgs", "zip");
				map.put("gjgss", "1");
			} else {
				map.put("gjgs", "zb3");
				map.put("gjgss", "2");
			}
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();" +
					"parent.Ext.getCmp('packagetype').setValue('"+ map.get("packagetype") +"');" +
					"parent.Ext.getCmp('packagetime').setValue('"+ map.get("time") +"');" +
					"parent.Ext.getCmp('orginfo').setValue('"+ map.get("orginfo") +"');" +
					"parent.document.getElementById('gjgs').value='"+ map.get("gjgs") +"';" +
					"parent.document.getElementById('gjgss').value='"+ map.get("gjgss") +"';" +
					"parent.document.getElementById('valueimp').value='"+ map.get("valueimp") +"';" +
					"parent.document.getElementById('type1').value='"+ type2 +"';" +
					"parent.document.getElementById('count1').value='"+ size +"';" +
					"parent.document.getElementById('psncount').value='"+ map.get("psncount") +"';" +
					"parent.document.getElementById('uuid').value='"+ uuid +"';" +
					"parent.document.getElementById('filename').value='"+ URLEncoder.encode(filename,"UTF-8") +"';");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('���ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
		}
		
	}
	private void uploadzzbFileImpPsn(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		try {
			String id = request.getParameter("uuid");
			String filename = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
			String a0165 = request.getParameter("a0165");
			String fxz = request.getParameter("fxz");
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //�ļ���׺
			ImpHzbThread thr = new ImpHzbThread(filename, id, user,userVo,a0165,fxz);
			new Thread(thr,"���ݵ����߳�1").start();
//			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //�ļ���׺
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				new LogUtil().createLog("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
//			} else {
//				new LogUtil().createLog("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void uploadzzbFileImpWages(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		try {
			String id = request.getParameter("uuid");
			String filename = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpWagesThread thr = new ImpWagesThread(filename, id, user,userVo);
			new Thread(thr,"���ݵ����߳�1").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

/*	private void uploadzzbFileImpPsn1(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		String uuid = request.getParameter("uuid");
		String filename = request.getParameter("filename");
		String rootPath = "";									// ��Ŀ·��
		String imprecordid = "";								// �����¼id
		String process_run = "1";								// ����������
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// ҵ����bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// �ϴ��ļ�����·��
		String unzip = "";										// ��ѹ·��
		try {
			
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			// 001==============�����ļ� ��׺  ��ʽ  �ϴ�·��  ��ѹ·��==============================================================
			String classPath = getClass().getClassLoader().getResource("/").getPath();				// class ·��
			if ("\\".equals(File.separator)) {														// windows��
				rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {														// linux��
				rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = rootPath + "upload/" + uuid + "/";									// �ϴ�·��
			unzip = rootPath + "upload/unzip/" + uuid + "/";										// ��ѹ·��
			File file = new File(unzip);															// ����ļ��в������򴴽�
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //�ļ���׺
			filePath = upload_file + "/" + uuid + "." +houzhui;									    //�ϴ��ļ�����·��
			String from_file = unzip + "Photos/";													//��ѹ��ͼƬ���·��
			
			// 003================  �ļ���ѹ   =========================================================================
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.extractilenew(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.extractilenew(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//004================  ����ͷ�ļ�   =========================================================================
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "" + "gwyinfo.xml");
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			String B0111 = headlist.get(0).get("B0111");							// ���ڵ��ϼ�����id
			String deptid = "";														// ���ڵ��ϼ�����id
			String impdeptid = "";													//���ڵ����id
			List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				if (gr != null) {
					imprecord.setImpgroupid(gr.getB0111());
					imprecord.setImpgroupname(gr.getB0101());
				}
				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype(houzhui);
				imprecord.setImptype("4");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				imprecord.setPsncount((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?Long.parseLong(headlist.get(0).get("psncount")):0L);
				imprecord.setLinkpsn(headlist.get(0).get("linkpsn"));
				imprecord.setLinktel(headlist.get(0).get("linktel"));
				imprecord.setImprecordid(uuid);
				sess.save(imprecord);
				imprecordid = imprecord.getImprecordid();
				process_run = "3";																//�������
				int t_n = 0;
				//004================ ���������ļ����������ݿ�   =================================================================================
				
				
				//NO.006.002 ����A02��
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A02���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("NO.006.002 A02���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A02", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.002 A02���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				
				
				//NO.006.003 ����A06��
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A06���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a06����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A06", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a06����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A08���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a08����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A08", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a08����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A11���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a11����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A11", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a11����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A14���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a14����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A14", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a14����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A15���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a15����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A15", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a15����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A29���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a29����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A29", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a29����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A30���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a30����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A30", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a30����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A31���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a31����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A31", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a31����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A36���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a36����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A36", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a36����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A37���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a37����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A37", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a37����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A41���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a41����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A41", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a41����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A53���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a53����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A53", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("aa53����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			 
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A57���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a57����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A57", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a57����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				//NO.006.001 ����B01��
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�B01���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
//				CommonQueryBS.systemOut("NO.006.001 B01���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
//				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
//				CommonQueryBS.systemOut("NO.006.001 B01���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");

				
//				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A01���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a01����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a01����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				
				imprecord.setTotalnumber(t_n + "");
				sess.update(imprecord);
			}
			CommonQueryBS.systemOut("END INSERT---------" +DateUtil.getTime());
			sess.flush();
			
//			KingbsconfigBS.saveImpDetail(process_run, "2", "��ȡ���", imprecordid);
			try {
				if (houzhui.equalsIgnoreCase("hzb")) {
					new LogUtil("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList(),userVo).start();
				} else {
					new LogUtil("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil().createLog("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
					} else {
						new LogUtil().createLog("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			this.delFolder(unzip);
			this.delFolder(filePath);
			CommonQueryBS.systemOut("delete file END---------" +DateUtil.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			try {
//				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//������ʾ��Ϣ�� 
			uploadbs.rollbackImp(imprecordid);
			this.delFolder(unzip);
			this.delFolder(filePath);
			if(sess != null)
				sess.getTransaction().rollback();
		}
		
	}*/

	private void uploadZzbFileImp(HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		try {
			String id = request.getParameter("uuid");
			String b0111new = request.getParameter("b0111new");
			String filename = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
			String a0165 = request.getParameter("a0165");
			String fxz = request.getParameter("fxz");
			String addChild = request.getParameter("addChild");
			String isContain="true".equals(addChild)?"1":"0";
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,a0165,isContain) values ('"+ id +"','"+a0165+"','"+isContain+"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//ˢ�����ݵĴ�����
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpZzbHzbThreadNewCaNull thr = new ImpZzbHzbThreadNewCaNull(filename, id,b0111new,a0165, user,userVo,fxz,isContain);
			new Thread(thr,"���ݵ����߳�1").start();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
	}
   

	private void uploadZzbFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
//		factory.setSizeThreshold(1024*1024*256);
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// ����ļ��в������򴴽�
					file1.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString(); 
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//String md5Name = MD5.MD5(filename);
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
					} catch (Exception e) {
						e.printStackTrace();
						throw new AppException("�ϴ�ʧ��");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "1");// 1 ʹ��hzb�����ѹgwyinfo.xml
//			} else {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "2");// 1 ʹ�ñ�׼�������ѹ
//			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
//			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
//			Map<String, String> map = headlist.get(0);
//			String B0111 = map.get("B0111");// ���ڵ��ϼ�����id
//			String org = map.get("B0101");// ���ڵ��ϼ�����id
//			String B0114 = map.get("B0114");// ���ڵ��ϼ�����id
//			String B0131 = map.get("B0194");// ���ڵ��ϼ�����id
//			map.put("b0131_combo", "");
//			List<B01> b01s = null;
//			HBSession sess =  HBUtil.getHBSession();
//			conn = sess.connection();
//			stmt = conn.createStatement();
//			String sql5 = "select count(A0000) from a01";
//			rs = stmt.executeQuery(sql5);
//			String size = null;
//			while(rs.next()){
//				size = rs.getString(1);
//			}
//			String type = DBUtil.getDBType().toString();
//			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
//			if (b01s!=null && b01s.size()>0) {
//				B01 b01 = b01s.get(0);
//				if(b01.getB0194().equals(B0131)){
//					Object obj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value t where t.code_type='B0194' AND t.code_value='" + B0131 + "'").uniqueResult();
//					map.put("b0131_combo", obj ==null ? "" : obj.toString());
//					map.put("valueimp", "1");
//					map.put("orginfo", "��ƥ�䵽���������Ե��롣");
//				} else {
//					map.put("valueimp", "2");
//					map.put("orginfo", "�������Ͳ�ƥ�䣬���ܵ��롣");
//				}
//			} else {
//				map.put("valueimp", "2");
//				map.put("orginfo", "δƥ�䵽���������ܵ��롣");
//			}
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				map.put("gjgs", "1");
//				map.put("gjgs_combo", "hzb");
//			} else {
//				map.put("gjgs", "2");
//				map.put("gjgs_combo", "zb3");
//			}
//			map.put("b0101", org);
//			map.put("packagetype", "����������");
			
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			/*
			 * pywu  20170608
			 * ��Ϊ��jsp�ж����Ľ����˱����ʱ���õ���UTF-8�ı��뷽ʽ������servlet�е���request.getParameter();������ʱ��ʹ�÷�����ָ���ı����ʽ�Զ�����һ�Σ�
			 * ����ǰ̨����һ�κ�̨����һ�ζ�����ͱ���ķ�ʽ�����������������ĳ��֣�
			 * 
			 * */
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.document.getElementById('uuid').value='"+uuid
					+"';parent.parent.document.getElementById('subWinIdBussessId').value='"+uuid+"@"+URLEncoder.encode(URLEncoder.encode(filename,"UTF-8"),"UTF-8")+"';"
							+ "parent.parent.radow.doEvent('initX');"); 
			out.println("</script>");
//			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.realParent.$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew','���봰��',850,550,'"+uuid +"@"+URLEncoder.encode(filename,"UTF-8")+"','"+request.getContextPath()+"');"); 
//			out.println("</script>");
			
//			out.println("<script>parent.Ext.getCmp('impBtn').setDisabled(false);");
//			out.println("</script>");
//			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();" +
//					"parent.Ext.getCmp('b0101').setValue('"+ org +"');" +
//					"parent.Ext.getCmp('packagetype').setValue('"+ map.get("packagetype") +"');" +
//					"parent.Ext.getCmp('packagetime').setValue('"+ map.get("time") +"');" +
//					"parent.Ext.getCmp('orginfo').setValue('"+ map.get("orginfo") +"');" +
//					"parent.Ext.getCmp('b0111').setValue('"+ B0114 +"');" +
//					"parent.document.getElementById('gjgs_combo').value='"+ map.get("gjgs_combo") +"';" +
//					"parent.document.getElementById('gjgs').value='"+ map.get("gjgs") +"';" +
//					"parent.document.getElementById('valueimp').value='"+ map.get("valueimp") +"';" +
//					"parent.document.getElementById('psncount').value='"+ map.get("psncount") +"';" +
//					"parent.document.getElementById('linkpsn').value='"+ map.get("linkpsn") +"';" +
//					"parent.document.getElementById('linktel').value='"+ map.get("linktel") +"';" +
//					"parent.document.getElementById('uuid').value='"+ uuid +"';" +
//					"parent.document.getElementById('filename').value='"+ URLEncoder.encode(filename,"UTF-8") +"';" +
//					"parent.document.getElementById('b0131').value='"+ map.get("B0194") +"';" +
//					"parent.document.getElementById('count').value= "+ size +";" +  //ͳ������
//					"parent.document.getElementById('type11').value= '"+ type +"';" +  //���ݿ�����
//					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
//					"parent.Ext.getCmp('impBtn').setDisabled(false);parent.parent.clickquery();");
//			out.println("</script>");
//			out.println("<script>parent.odin.alert('"+ map.get("orginfo") +"');");
//			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('���ʧ�ܣ�');</script>");
		}finally{
			factory = null;
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
		}
	}
	
	private void uploadTYGSFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
//		factory.setSizeThreshold(1024*1024*256);
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// ����ļ��в������򴴽�
					file1.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString(); 
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//String md5Name = MD5.MD5(filename);
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
					} catch (Exception e) {
						e.printStackTrace();
						throw new AppException("�ϴ�ʧ��");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("7z")) {
				Zip7z.unzip7zOne(filePath, unzip, "20171020");
			} else if (houzhui.equalsIgnoreCase("zip")){
				Zip7z.unzip7zOne(filePath, unzip, null);
			}
			//ZipUtil.unZip(filePath);
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			/*
			 * pywu  20170608
			 * ��Ϊ��jsp�ж����Ľ����˱����ʱ���õ���UTF-8�ı��뷽ʽ������servlet�е���request.getParameter();������ʱ��ʹ�÷�����ָ���ı����ʽ�Զ�����һ�Σ�
			 * ����ǰ̨����һ�κ�̨����һ�ζ�����ͱ���ķ�ʽ�����������������ĳ��֣�
			 * 
			 * */
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.document.getElementById('uuid').value='"+uuid
					+"';parent.parent.document.getElementById('subWinIdBussessId').value='"+uuid+"@"+URLEncoder.encode(URLEncoder.encode(filename,"UTF-8"),"UTF-8")+"';"
							+ "parent.parent.radow.doEvent('initX');"); 
			out.println("</script>");
//			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.realParent.$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew','���봰��',850,550,'"+uuid +"@"+URLEncoder.encode(filename,"UTF-8")+"','"+request.getContextPath()+"');"); 
//			out.println("</script>");
			
//			out.println("<script>parent.Ext.getCmp('impBtn').setDisabled(false);");
//			out.println("</script>");
//			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();" +
//					"parent.Ext.getCmp('b0101').setValue('"+ org +"');" +
//					"parent.Ext.getCmp('packagetype').setValue('"+ map.get("packagetype") +"');" +
//					"parent.Ext.getCmp('packagetime').setValue('"+ map.get("time") +"');" +
//					"parent.Ext.getCmp('orginfo').setValue('"+ map.get("orginfo") +"');" +
//					"parent.Ext.getCmp('b0111').setValue('"+ B0114 +"');" +
//					"parent.document.getElementById('gjgs_combo').value='"+ map.get("gjgs_combo") +"';" +
//					"parent.document.getElementById('gjgs').value='"+ map.get("gjgs") +"';" +
//					"parent.document.getElementById('valueimp').value='"+ map.get("valueimp") +"';" +
//					"parent.document.getElementById('psncount').value='"+ map.get("psncount") +"';" +
//					"parent.document.getElementById('linkpsn').value='"+ map.get("linkpsn") +"';" +
//					"parent.document.getElementById('linktel').value='"+ map.get("linktel") +"';" +
//					"parent.document.getElementById('uuid').value='"+ uuid +"';" +
//					"parent.document.getElementById('filename').value='"+ URLEncoder.encode(filename,"UTF-8") +"';" +
//					"parent.document.getElementById('b0131').value='"+ map.get("B0194") +"';" +
//					"parent.document.getElementById('count').value= "+ size +";" +  //ͳ������
//					"parent.document.getElementById('type11').value= '"+ type +"';" +  //���ݿ�����
//					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
//					"parent.Ext.getCmp('impBtn').setDisabled(false);parent.parent.clickquery();");
//			out.println("</script>");
//			out.println("<script>parent.odin.alert('"+ map.get("orginfo") +"');");
//			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('��ѡ����ȷ��Zip���ݰ���');</script>");
		}finally{
			factory = null;
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
		}
	}
	
	private void zzbFileSqlloadImp(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		try {
			String id = request.getParameter("uuid");
			String filename = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//ˢ�����ݵĴ�����
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpZzbHzbThreadNew thr = new ImpZzbHzbThreadNew(filename, id, user,userVo);
			new Thread(thr,"���ݵ����߳�1").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void zzbFileSqlload(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString(); 
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						String md5Name = MD5.MD5(filename);
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String B0111 = map.get("B0111");// ���ڵ��ϼ�����id
			String org = map.get("B0101");// ���ڵ��ϼ�����id
			String B0114 = map.get("B0114");// ���ڵ��ϼ�����id
			String B0131 = map.get("B0194");// ���ڵ��ϼ�����id
			map.put("b0131_combo", "");
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			if(!map.get("type").equals("30")){
				map.put("valueimp", "2");
				map.put("orginfo", "����װ�����ݰ������ܵ��롣");
			} else if (b01s!=null && b01s.size()>0) {
				B01 b01 = b01s.get(0);
				if(b01.getB0194().equals(B0131)){
					Object obj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value t where t.code_type='B0194' AND t.code_value='" + B0131 + "'").uniqueResult();
					map.put("b0131_combo", obj ==null ? "" : obj.toString());
					map.put("valueimp", "1");
					map.put("orginfo", "��ƥ�䵽���������Ե��롣");
				} else {
					map.put("valueimp", "2");
					map.put("orginfo", "�������Ͳ�ƥ�䣬���ܵ��롣");
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", "δƥ�䵽���������ܵ��롣");
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "1");
				map.put("gjgs_combo", "hzb");
			} else {
				map.put("gjgs", "2");
				map.put("gjgs_combo", "zb3");
			}
			map.put("b0101", org);
			map.put("packagetype", "����������");
			
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();" +
					"parent.Ext.getCmp('b0101').setValue('"+ org +"');" +
					"parent.Ext.getCmp('packagetype').setValue('"+ map.get("packagetype") +"');" +
					"parent.Ext.getCmp('packagetime').setValue('"+ map.get("time") +"');" +
					"parent.Ext.getCmp('orginfo').setValue('"+ map.get("orginfo") +"');" +
					"parent.Ext.getCmp('b0111').setValue('"+ B0114 +"');" +
					"parent.document.getElementById('gjgs_combo').value='"+ map.get("gjgs_combo") +"';" +
					"parent.document.getElementById('gjgs').value='"+ map.get("gjgs") +"';" +
					"parent.document.getElementById('valueimp').value='"+ map.get("valueimp") +"';" +
					"parent.document.getElementById('psncount').value='"+ map.get("psncount") +"';" +
					"parent.document.getElementById('linkpsn').value='"+ map.get("linkpsn") +"';" +
					"parent.document.getElementById('linktel').value='"+ map.get("linktel") +"';" +
					"parent.document.getElementById('uuid').value='"+ uuid +"';" +
					"parent.document.getElementById('filename').value='"+ URLEncoder.encode(filename,"UTF-8") +"';" +
					"parent.document.getElementById('b0131').value='"+ map.get("B0194") +"';" +
					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
					"parent.parent.clickquery();");
			out.println("</script>");
			out.println("<script>parent.parent.odin.alert('"+ map.get("orginfo") +"');");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('���ʧ�ܣ�');");
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	
	
	
/*
	private void uploadZzbFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = rootPath + "upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size());
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString(); 
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						String md5Name = MD5.MD5(filename);
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.extractileOnenew(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.extractileOnenew(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String B0111 = map.get("B0111");// ���ڵ��ϼ�����id
			String org = map.get("B0101");// ���ڵ��ϼ�����id
			String B0114 = map.get("B0114");// ���ڵ��ϼ�����id
			String B0131 = map.get("B0194");// ���ڵ��ϼ�����id
			map.put("linkpsn", URLEncoder.encode(map.get("linkpsn"),"UTF-8"));
			map.put("linktel", URLEncoder.encode(map.get("linktel"),"UTF-8"));
			map.put("b0131_combo", "");
			map.put("uuid", uuid);
			map.put("filename", filename);
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			if (b01s!=null && b01s.size()>0) {
				B01 b01 = b01s.get(0);
				if(b01.getB0194().equals(B0131)){
					Object obj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value t where t.code_type='B0194' AND t.code_value='" + B0131 + "'").uniqueResult();
					map.put("b0131_combo", obj ==null ? "" :  URLEncoder.encode(obj.toString(),"UTF-8"));
					map.put("valueimp", "1");
					map.put("orginfo", URLEncoder.encode("��ƥ�䵽���������Ե��롣","UTF-8"));
				} else {
					map.put("valueimp", "2");
					map.put("orginfo", URLEncoder.encode("�������Ͳ�ƥ�䣬���ܵ��롣","UTF-8"));
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", URLEncoder.encode("δƥ�䵽���������ܵ��롣","UTF-8"));
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "1");
				map.put("gjgs_combo", "hzb");
			} else {
				map.put("gjgs", "2");
				map.put("gjgs_combo", "zb3");
			}
			map.put("b0101", URLEncoder.encode(org,"UTF-8"));
			map.put("packagetype", URLEncoder.encode("����������","UTF-8"));
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
//			out.println("<script>window.parent.parent.parent.alert(1);" +
//					"parent.Ext.getCmp('b0101').setValue('"+ org +"');" +
//					"parent.Ext.getCmp('packagetype').setValue('"+ map.get("packagetype") +"');" +
//					"parent.Ext.getCmp('packagetime').setValue('"+ map.get("time") +"');" +
//					"parent.Ext.getCmp('orginfo').setValue('"+ map.get("orginfo") +"');" +
//					"parent.Ext.getCmp('b0111').setValue('"+ B0114 +"');" +
//					"parent.document.getElementById('gjgs_combo').value='"+ map.get("gjgs_combo") +"';" +
//					"parent.document.getElementById('gjgs').value='"+ map.get("gjgs") +"';" +
//					"parent.document.getElementById('valueimp').value='"+ map.get("valueimp") +"';" +
//					"parent.document.getElementById('psncount').value='"+ map.get("psncount") +"';" +
//					"parent.document.getElementById('linkpsn').value='"+ map.get("linkpsn") +"';" +
//					"parent.document.getElementById('linktel').value='"+ map.get("linktel") +"';" +
//					"parent.document.getElementById('uuid').value='"+ uuid +"';" +
//					"parent.document.getElementById('filename').value='"+ filename +"';" +
//					"parent.document.getElementById('b0131').value='"+ map.get("B0194") +"';" +
//					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
//					"");
//			out.println("</script>");
			out.println(net.sf.json.JSONObject.fromObject(map).toString());
			CommonQueryBS.systemOut("�ϴ�===================="+net.sf.json.JSONObject.fromObject(map).toString());
			
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('���ʧ�ܣ�');");
			e.printStackTrace();
		}
	}
*/		
	private void uploadZzbFileImp2(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String id = request.getParameter("uuid");
			String filename = new String (request.getParameter("filename").getBytes("ISO-8859-1"),"UTF-8");
//			String filename = request.getParameter("filename");
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);
			CurrentUser user = new CurrentUser();
			user.setId("40288103556cc97701556d629135000f");
			UserVO userVo = new UserVO();
			userVo.setLoginname("system");
			ImpZzbHzbThreadNew thr = new ImpZzbHzbThreadNew(filename, id, user,userVo);
			new Thread(thr,"���ݵ����߳�1").start();
			out.println("3");
		} catch (Exception e) {
			out.println("4");
			e.printStackTrace();
		}
		
	}


	private void uploadZzbFile2(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload uploader = new ServletFileUpload(factory);
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			
			String fromfile = new String (request.getParameter("request").getBytes("ISO-8859-1"),"UTF-8");
			
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
//			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
//			if ("\\".equals(File.separator)) {// windows��
//				rootPath = classPath.substring(1, classPath
//						.indexOf("WEB-INF/classes"));
//				rootPath = rootPath.replace("/", "\\");
//			}
//			if ("/".equals(File.separator)) {// linux��
//				rootPath = classPath.substring(0, classPath
//						.indexOf("WEB-INF/classes"));
//				rootPath = rootPath.replace("\\", "/");
//			}
//			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// �ϴ�·��
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// ��ѹ·��
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			houzhui = fromfile.substring(fromfile.lastIndexOf(".") + 1,
					fromfile.length());
			filename = fromfile.substring(fromfile.lastIndexOf("/") + 1);
			File fromfilef = new File(fromfile);
			if(fromfilef.exists()){
				filePath = upload_file + uuid + "." + houzhui;
				File filePathF = new File(filePath);
				if (!filePathF.exists()) {// ����ļ��в������򴴽�
					filePathF.createNewFile();
				}
				copyFile(fromfilef, filePathF);
			} else {
				out.println("2");
				return;
			}
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "1");
//			} else {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "2");
//			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String B0111 = map.get("B0111");// ���ڵ��ϼ�����id
			String org = map.get("B0101");// ���ڵ��ϼ�����id
			String B0114 = map.get("B0114");// ���ڵ��ϼ�����id
			String B0131 = map.get("B0194");// ���ڵ��ϼ�����id
			map.put("b0131_combo", "");
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			if (b01s!=null && b01s.size()>0) {
				B01 b01 = b01s.get(0);
				if(b01.getB0194().equals(B0131)){
					Object obj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value t where t.code_type='B0194' AND t.code_value='" + B0131 + "'").uniqueResult();
					map.put("b0131_combo", obj ==null ? "" : obj.toString());
					map.put("valueimp", "1");
					map.put("orginfo", "��ƥ�䵽���������Ե��롣");
				} else {
					map.put("valueimp", "2");
					map.put("orginfo", "�������Ͳ�ƥ�䣬���ܵ��롣");
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", "δƥ�䵽���������ܵ��롣");
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "1");
				map.put("gjgs_combo", "hzb");
			} else {
				map.put("gjgs", "2");
				map.put("gjgs_combo", "zb3");
			}
			map.put("b0101", org);
			map.put("packagetype", "����������");
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			String str = "1,"+org +","+ map.get("packagetype") +","+map.get("time") +","+map.get("orginfo") 
					+","+B0114 +","+map.get("gjgs_combo") +","+map.get("gjgs") +","+map.get("valueimp") 
					+","+map.get("psncount") +"," +map.get("linkpsn") +","+map.get("linktel") +","+uuid 
					+","+filename +","+map.get("B0194") +","+map.get("b0131_combo");
			CommonQueryBS.systemOut(str);
			out.println(str);
			
		} catch (Exception e) {
			out.println("2");
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	private void zzbFileImpRefresh(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// ҵ����bs
		try {
			out = response.getWriter();
			String id = request.getParameter("uuid");
			String info = uploadbs.getRefreshInfo(id);
			info = info.substring(0,info.length()-1);
			out.println("11,"+info+" ");
		} catch (Exception e) {
			out.println("12");
			e.printStackTrace();
		}
		
	}
	/**
	 * ������
	 */
	private void create(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		HBSession sess= HBUtil.getHBSession();
		Connection conn= sess.connection();
		try { 
			PreparedStatement pstmt = conn.prepareStatement("insert into b01(b0101,b0121,b0111,b0194,sortid) values(?,?,?,?,?)");
			PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
			insertb01(1,"001.001",pstmt,pstmt1,1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void insertb01(int cj, String string, PreparedStatement pstmt, PreparedStatement pstmt1, int i2) {
		int num = 28;
		int bjs = 28;
		int sjs = 28;
		if(cj==1){
			bjs=28;
			sjs=1;
			num = bjs/sjs + (bjs%sjs==0?0:1);
		} else if(cj==2){
			bjs=590;
			sjs=28;
			num = bjs/sjs + (bjs%sjs==0?0:1);
		} else if(cj==3){
			bjs=7383;
			sjs=590;
			num = bjs/sjs + (bjs%sjs==0?0:1);
		} else if(cj==4){
			bjs=18648;
			sjs=7383;
			num = bjs/sjs + (bjs%sjs==0?0:1);
		} else if(cj==5){
			bjs=20380;
			sjs=18648;
			num = bjs/sjs + (bjs%sjs==0?0:1);
//		} else if(cj==6){
//			bjs=28;
//			sjs=1;
//		} else if(cj==7){
//			bjs=28;
//			sjs=1;
//			num = bjs/sjs + (bjs%sjs==0?0:1);
//		} else if(cj==8){
//			bjs=28;
//			sjs=1;
//			num = bjs/sjs + (bjs%sjs==0?0:1);
		} else {
			return;
		}
		try {
			for (int i = 0; i < num; i++) { 
				String b0111 = getNewB0111(string, i);
				pstmt.setString(1, "��������_"+cj +"_"+i2+"_"+i);
				pstmt.setString(2, string);
				pstmt.setString(3, b0111);
				pstmt.setString(4, "1");
				pstmt.setString(5, "1");
				pstmt.execute();
				
				pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
				pstmt1.setString(2, "40288103556cc97701556d629135000f");
				pstmt1.setString(3, b0111);
				pstmt1.setString(4, "1");
				pstmt1.execute();
				insertb01(cj+1, b0111,  pstmt, pstmt1 ,i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		
		
	}
	public static String getNewB0111(String impdeptid, int i) {
		i = i + 1;
		int num1 = 0;//��
		int num2 = 0;//ʮ
		int num3 = 0;//��
		String[] key={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"
				,"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
				,"Y","Z"};	
		num3 = i%36;
		int num2_1 = i/36;
		if(num2_1>0){
			num2 = num2_1%36;
		}
		int num1_1 = num2_1/36;
		if(num1_1>0){
			num1 = num1_1%36;
		}
		String str = impdeptid + "." + key[num1] + key[num2] + key[num3];
		return str;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/* �ж��Ƿ������� */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	
	public boolean deleteFile(String filename) throws AppException {
		java.io.File dir = new java.io.File(filename);
		boolean b = true;
		if (dir.exists()) {
			b = dir.delete();
		}
		return b;
	}

	// ����ļ���ʽ
	public String checkFile(FileItem item) {
		String filename = item.getName();
		filename = filename.toLowerCase();
		if (filename.endsWith(".zb3")||filename.endsWith(".hzb")) {
			return "0";
		} else {
			return "-1";
		}
	}

	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}

	private static void copyPhotos(String to_file, String from_file) {
		// TODO Auto-generated method stub
		/**
		 * ȱ���ж��ļ�Ϊ�մ��롣��������������������������
		 */
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(from_file)).listFiles();
		try {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// �����ļ�
					copyFile(file[i], new File(to_file + file[i].getName()));
				}
				if (file[i].isDirectory()) {
					// ����Ŀ¼
					String sorceDir = from_file + File.separator
							+ file[i].getName();
					String targetDir = to_file + File.separator
							+ file[i].getName();
					copyDirectiory(sorceDir, targetDir);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {
		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// �½��ļ���������������л���
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// ��������
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// ˢ�´˻���������
		outbuff.flush();
		// �ر���
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е��µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());

				copyFile(sourceFile, targetFile);

			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}

	}
	
	/**
	 *  �����ϴ�
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void uploadAttachment(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		//��ȡ����:     flag:�������ͱ�־   uuid:��������   uname:����
		String flag=new String(request.getParameter("flag").getBytes("iso-8859-1"),"gbk");
		String uuid= new String(request.getParameter("uuid").getBytes("iso-8859-1"),"gbk");
		String uname=new String(request.getParameter("uname").getBytes("iso-8859-1"),"gbk");
		//���ļ��У��������ֳʱ������ǼǱ������������ļ�������
		//flag=0ʱΪ�ʱ�����flag=1ʱΪ�ǼǱ���������,flag=2ʱΪ�����ʱ�������(����ҳ��ĸ����ϴ�),flag=3ʱΪ�ϴ��ϱ��ʱ�������
		String attachPath = "";
		if("1".equals(flag) || "2".equals(flag) || "3".equals(flag)){
			attachPath = "CBDAttachment";
		}else if("0".equals(flag)){
			//�������ݿ����
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.createQuery("from A01 where a0000 = '"+uuid+"'").list().get(0);
			//attachPath = "DJBOrOtherAttachment/"+uname.split("@")[0]+uname.split("@")[1];
			attachPath = "DJBOrOtherAttachment/"+a01.getA0101()+a01.getA0184();
		}
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = "CBDUpload/"+attachPath + "/";// �ϴ�·��
			try {
				File file = new File(rootPath+upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//��ȡ����Ϣ
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//�������飬��Ÿ���·���ͱ�ע��Ϣ
			Map<String,String> text_map = new HashMap<String,String>();
			Map<String,String> filePath_map = new HashMap<String,String>();
			Map<String,String> filename_map = new HashMap<String,String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"),"gbk");
					if(fieldName.startsWith("textid_") && !"".equals(fieldvalue)){
						text_map.put(fieldName, fieldvalue);
					}
//					if("rowLength".equals(fieldName)){
//						rowlength = Integer.parseInt(fieldvalue);
//					}
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					//�ж��ϴ��ļ��ĳߴ磬����10M���ļ������ϴ�
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>parent.parent.odin.alert('�ļ���"+path+"�ĳߴ糬��10M�������ϴ���');</script>");
						return ;
					}
					try {
						//��ȡ�ļ���������׺��
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//��ȡ�ļ�����������׺��
						filename = filename.substring(0,filename.lastIndexOf("."));
//						if("0".equals(flag) || "2".equals(flag)){
//							filename = uname;
//						}
						filePath = upload_file + filename + "." + houzhui;
						File file = new File(rootPath+filePath);
						//�ж��ļ��Ƿ��Ѿ��ϴ�������Ѿ��ϴ�������������������ֹ�ļ��ظ��ϱ���
//						if(file.exists()){
//							continue;
//						}
						fi.write(new File(rootPath+filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName(), filePath);
						filename_map.put(fi.getFieldName()+"_filename", filename + "." + houzhui);
						//��������Ϣ���������ݱ���
						//saveAttachmentInfo(uname,uuid,filePath,flag,filename);
						
					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//��������Ϣ��������������
			for(int i=1;i<=rowlength;i++){
				String text = text_map.get("textid_"+i);
				String filepath = filePath_map.get("fileid_"+i);
				String fileName = filename_map.get("fileid_"+i+"_filename");
				saveAttachmentInfo(uname,uuid,filepath,flag,fileName,text);
			}
			out.println("<script>parent.parent.odin.alert('�ϴ��ɹ�');</script>");
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('�ϴ�ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	
	/**
	 *  ���ϴ��ĸ�����Ϣ���������ݿ���
	 * @param name
	 * @param uuid
	 * @param filepath
	 * @param flag
	 * @param filename
	 * @return
	 */
	public int saveAttachmentInfo(String name,String uuid,String filepath,String flag,String filename,String text){
		
		//�������ݿ����
		HBSession sess = HBUtil.getHBSession();
		//��ȡ��¼�û�����Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//������������
		AttachmentInfo atta = null;
		//��ȡ��Ա��Ϣ
		A01 a01 = null;
		if("0".equals(flag)){
			a01 = (A01) sess.createQuery("from A01 where a0000 = '"+uuid+"'").list().get(0);
		}
		//��ȡ��ǰʱ��
		//��ȡʱ��
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		//���ϴ��ĸ���Ϊ��Ա��ظ����ǣ�flag=1��
		//���ϴ��ĸ���Ϊ�ǼǱ���������ʱ��������Ա�����Ϣ���ϴ��ĸ���Ϊ�ʱ���ʱ����������������ֶ�
		if("0".equals(flag)){
			//�ж��Ƿ��ϴ���ͬ������Ϣ
			int count = 0;
			List list1 = sess.createSQLQuery("select FILENAME from Attachment_Info where PERSONID = '"+uuid+"'").list();
			for(int i=0;i<list1.size();i++){
				if(list1.get(i).equals(filename)){
					count++;
				}
			}
			if(count>0){
				List<AttachmentInfo> list4 = sess.createQuery("from AttachmentInfo where PERSONID = '"+uuid+"' and FILENAME = '"+filename+"'").list();
				atta = list4.get(0);
				atta.setUsername(user.getName());
				atta.setUserid(user.getId());
				atta.setUploaddate(createdate);
				atta.setPersonname(a01.getA0101());
				atta.setPersonid(uuid);
				atta.setIdcard(a01.getA0184());
				atta.setFiletype(flag);
				atta.setFilepath(filepath);
				atta.setFilename(filename);
				atta.setBeizhu(text);
			}else{
				atta = new AttachmentInfo();
				//��ȡ����
				String uid = UUID.randomUUID().toString();
				atta.setId(uid);
				atta.setUsername(user.getName());
				atta.setUserid(user.getId());
				atta.setUploaddate(createdate);
				atta.setPersonname(a01.getA0101());
				atta.setPersonid(uuid);
				atta.setIdcard(a01.getA0184());
				atta.setFiletype(flag);
				atta.setFilepath(filepath);
				atta.setFilename(filename);
				atta.setBeizhu(text);
			}
			
		}else if("1".equals(flag) || "2".equals(flag)){
			
			//���ϴ��ϱ��ʱ���ʱ�����ݱ����ʱ�����Ų�ѯ�ϱ��ʱ������
			if("2".equals(flag)){
				List<CBDInfo> list3 = sess.createQuery("from CBDInfo where objectno = '"+uuid+"'").list();
				if(list3.size()>0){
					uuid = list3.get(0).getCbd_id();
				}
			}
			//��ѯ�ʱ����Ƿ��Ѿ��ϴ�����������Ѿ��ϴ�����ͬ���������ʱ����ĸ����������õ�����������
			int count = 0;
			List list = sess.createSQLQuery("select FILENAME from Attachment_Info where objectid = '"+uuid+"'").list();
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(filename)){
					count++;
				}
			}
			if(count>0){
				List<AttachmentInfo> list2 = sess.createQuery("from AttachmentInfo where objectid = '"+uuid+"' and FILENAME = '"+filename+"'").list();
				atta = list2.get(0);
				atta.setBeizhu(text);
				atta.setUploaddate(createdate);
				atta.setObjectid(uuid);
				atta.setUsername(user.getName());
				atta.setUserid(user.getId());
				atta.setFiletype(flag);
				atta.setFilepath(filepath);
				atta.setFilename(filename);
			}else{
				atta = new AttachmentInfo();
				//��ȡ����
				String uid = UUID.randomUUID().toString();
				atta.setId(uid);
				atta.setUsername(user.getName());
				atta.setUserid(user.getId());
				atta.setUploaddate(createdate);
				atta.setFiletype(flag);
				atta.setFilepath(filepath);
				atta.setFilename(filename);
				atta.setBeizhu(text);
				atta.setObjectid(uuid);
			}
			
		}else if("3".equals(flag)){
			int count = 0;
			List<CBDInfo> list = sess.createQuery("from CBDInfo where objectno = '"+uuid+"'").list();
			if(list.size() > 0 ){
				String cid = list.get(0).getCbd_id();
				//��ѯ�ʱ����Ƿ��Ѿ��ϴ���ͬ����������Ѿ��ϴ�����ͬ���������ʱ����ĸ����������õ�����������
				List list3 = sess.createSQLQuery("select FILENAME from Attachment_Info where objectid = '"+cid+"'").list();
				for(int i=0;i<list3.size();i++){
					if(list3.get(i).equals(filename)){
						count++;
					}
				}
				if(count>0){
					List<AttachmentInfo> list1 = sess.createQuery("from AttachmentInfo where objectid = '"+cid+"' and FILENAME = '"+filename+"'").list();
					atta = list1.get(0);
					atta.setObjectid(list.get(0).getCbd_id());
					atta.setBeizhu(text);
					atta.setUploaddate(createdate);
					atta.setUsername(user.getName());
					atta.setUserid(user.getId());
					atta.setFiletype(flag);
					atta.setFilepath(filepath);
					atta.setFilename(filename);
				}else{
					atta = new AttachmentInfo();
					//��ȡ����
					String uid = UUID.randomUUID().toString();
					atta.setId(uid);
					atta.setUsername(user.getName());
					atta.setUserid(user.getId());
					atta.setUploaddate(createdate);
					atta.setFiletype(flag);
					atta.setFilepath(filepath);
					atta.setFilename(filename);
					atta.setBeizhu(text);
					atta.setObjectid(list.get(0).getCbd_id());
				}
			}
		}
		
		//ִ�����ݿ����
		
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		ts.commit();
		
		return 0;
	}
	
	
	/**
	 *  �����ʱ������ݰ�
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void uploadZip(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		//��ȡ����:     flag:�������ͱ�־
		String flag=new String(request.getParameter("flag").getBytes("iso-8859-1"),"gbk");

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		CBDTools ct = new CBDTools();
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String rootPath = ct.getPath();
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			
			String upload_file = "GetZip/";// �ϴ�·��
			try {
				File file = new File(rootPath+upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//��ȡ����Ϣ
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString();
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					try {
						//��ȡ�ļ���������׺��
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//��ȡ�ļ�����������׺��
						filename = filename.substring(0,filename.lastIndexOf("."));
						filePath = upload_file + filename + "." + houzhui;
						fi.write(new File(rootPath+filePath));
						fi.getOutputStream().close();
						
						//��ѹ�ʱ������ݰ�
						ct.unzip(rootPath+filePath, rootPath+"GetZip/");
						//ɾ��ԭ���ݰ�
						File oldZipFile = new File(rootPath+filePath);
						oldZipFile.delete(); 
						
						//�ʱ������ݰ�����ɹ����޸ĳʱ���״̬
						String xmlPath = rootPath+filePath;
						xmlPath = xmlPath.substring(xmlPath.lastIndexOf("/")+1, xmlPath.lastIndexOf("_"));
						File subDir = new File(rootPath+"GetZip/");
						File[] subFiles = subDir.listFiles(new PackFilter("pack_", ".xml"));
						for (File file : subFiles) {
							if(file.getAbsolutePath().indexOf(xmlPath)>=0){
								xmlPath = file.getName();
							}
						}
						xmlPath = rootPath+"GetZip/"+xmlPath;
						ct.changeStatus("@"+xmlPath+"@1");
						
					} catch (Exception e) {
						try {
							throw new AppException("����ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			out.println("<script>parent.parent.odin.alert('����ɹ�');</script>");
			out.println("<script language='javascript'>parent.parent.radow.doEvent('btnsx.onclick');</script>");
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('����ʧ�ܣ�');");
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
		
		
	}
	
	
	/**
	 *  �����ĵ��ļ��ϴ�
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void uploadFolder(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		
		//��ȡ����:     a0000:��Աid   treeId:�ļ���id   uname:����
		String a0000=new String(request.getParameter("a0000").getBytes("iso-8859-1"),"gbk");
		String treeId= new String(request.getParameter("treeId").getBytes("iso-8859-1"),"gbk");
		//String uname=new String(request.getParameter("uname").getBytes("iso-8859-1"),"gbk");
		
		//flag=0ʱΪ�ʱ�����flag=1ʱΪ�ǼǱ���������,flag=2ʱΪ�����ʱ�������(����ҳ��ĸ����ϴ�),flag=3ʱΪ�ϴ��ϱ��ʱ�������
		String attachPath = a0000 + "/" + treeId;
		
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = "FolderUpload/"+attachPath + "/";// �ϴ�·��
			try {
				File file = new File(rootPath+upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//��ȡ����Ϣ
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//�������飬��Ÿ���·���ͱ�ע��Ϣ
			Map<String,String> text_map = new HashMap<String,String>();
			Map<String,String> filePath_map = new HashMap<String,String>();
			Map<String,String> filename_map = new HashMap<String,String>();
			Map<String,String> fileSize_map = new HashMap<String,String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"),"gbk");
					if(fieldName.startsWith("textid_") && !"".equals(fieldvalue)){
						text_map.put(fieldName, fieldvalue);
					}
					
				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					//�ж��ϴ��ļ��ĳߴ磬����10M���ļ������ϴ�
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					
					
					DecimalFormat df = new DecimalFormat("#.00"); 
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					
					if(fi.getSize() < 1048576){
						fileSize = "0" + fileSize;
					}
					
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>odin.alert('�ļ���"+path+"�ĳߴ糬��10M�������ϴ���');</script>");
						return ;
					}
					try {
						//��ȡ�ļ���������׺��
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//��ȡ�ļ�����������׺��
						filename = filename.substring(0,filename.lastIndexOf("."));
//						if("0".equals(flag) || "2".equals(flag)){
//							filename = uname;
//						}
						filePath = upload_file + filename + "." + houzhui;
						File file = new File(rootPath+filePath);
						//�ж��ļ��Ƿ��Ѿ��ϴ�������Ѿ��ϴ�������������������ֹ�ļ��ظ��ϱ���
//						if(file.exists()){
//							continue;
//						}
						fi.write(new File(rootPath+filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						//��������Ϣ���������ݱ���
						//saveAttachmentInfo(uname,uuid,filePath,flag,filename);
						
					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//��������Ϣ��������������
			for(int i=1;i<=rowlength;i++){
				String text = text_map.get("textid_"+i);
				String filepath = filePath_map.get("excelFile"+i);
				String fileName = filename_map.get("excelFile"+i+"_filename");
				String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
				saveFolder(a0000,filepath,treeId,fileName,fileSize);
			}
			out.println("<script>odin.alert('�ϴ��ɹ�');</script>");
		} catch (Exception e) {
			out.println("<script>odin.alert('�ϴ�ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	
	/**
	 *  ���ϴ��ĵ��ӵ����ļ���Ϣ���������ݿ���
	 * @param a0000		��Աid
	 * @param filepath	�ļ���ַ
	 * @param treeId	�ļ���treeid
	 * @param filename	�ļ�����
	 * @return
	 */
	public int saveFolder(String a0000,String filepath,String treeId,String filename,String fileSize){
		
		//�������ݿ����
		HBSession sess = HBUtil.getHBSession();
		//��ȡ��¼�û�����Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//������������
		//AttachmentInfo atta = null;
		Folder atta = null;
		
		//��ȡ��Ա��Ϣ
		A01 a01 = null;
		
		a01 = (A01) sess.createQuery("from A01 where a0000 = '"+a0000+"'").list().get(0);
		
		//��ȡ��ǰʱ��
		//��ȡʱ��
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		//���ϴ��ĸ���Ϊ��Ա��ظ����ǣ�flag=1��
		
		
		//�ж��Ƿ��ϴ���ͬ������Ϣ
		int count = 0;
		List list1 = sess.createSQLQuery("select NAME from Folder where a0000 = '"+a0000+"'").list();
		for(int i=0;i<list1.size();i++){
			if(list1.get(i).equals(filename)){
				count++;
			}
		}
		if(count>0){
			List<Folder> list4 = sess.createQuery("from Folder where a0000 = '"+a0000+"' and NAME = '"+filename+"'").list();
			atta = list4.get(0);
			atta.setCatalog(filepath);			//�ϴ����������·��
			atta.setName(filename);				//�ļ�����
			atta.setFileSize(fileSize); 		//�ļ���С
			atta.setUploads(user.getName());  	//�ϴ�������
			atta.setA0000(a0000);  				//������Աid
			atta.setTime(createdate); 			//�ϴ�ʱ��
			atta.setTreeId(treeId);  			//�����ļ���id
			
			
		}else{
			atta = new Folder();
			//��ȡ����
			String uid = UUID.randomUUID().toString();
			atta.setId(uid);
			atta.setCatalog(filepath);			//�ϴ����������·��
			atta.setName(filename);				//�ļ�����
			atta.setFileSize(fileSize); 		//�ļ���С
			atta.setUploads(user.getName());  	//�ϴ�������
			atta.setA0000(a0000);  				//������Աid
			atta.setTime(createdate); 			//�ϴ�ʱ��
			atta.setTreeId(treeId);  			//�����ļ���id
		}
		
		//ִ�����ݿ����
		
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	 
	
	//ɾ�����ӵ����ļ�
	public void deleteFolderFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//���Ҫɾ�����ļ�id
		//String filePath = request.getParameter("filePath");		//Ҫɾ�����ļ����·��
		
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		if(id!=null && !"".equals(id)){
			Folder folder = (Folder) session.get(Folder.class, id);
			if(folder!=null && !"".equals(folder)){
				session.delete(folder);
				session.flush();
			}
		}
		
		//ɾ���ļ�
		try {
			String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			
			if ("\\".equals(File.separator)) {// windows��
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux��
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			
			filePath = rootPath + filePath;			//ƴ�ӳ�����·��
			
			this.deleteFile(filePath);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//�����ļ�
	public void downloadFolderFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		//String filePath= new String(request.getParameter("filePath").getBytes("iso-8859-1"),"gbk");
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);		//�ļ�����
		
		String rootPath = "";
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		if ("\\".equals(File.separator)) {// windows��
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		if ("/".equals(File.separator)) {// linux��
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		rootPath = URLDecoder.decode(rootPath, "GBK");
		filePath = rootPath + filePath;			//ƴ�ӳ�����·��
		
		
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
	
	class MyFilter implements FileFilter{   
		public boolean accept(File file){
			String name = file.getName();
			return name.endsWith("xml");  
		}  
	}
}
