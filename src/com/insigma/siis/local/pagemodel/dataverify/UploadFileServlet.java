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
////		out.append("Served at: 哈哈").append(request.getContextPath());
//		out.append("1,浙江省人民政府,按机构导出,2016-07-15 19:05:27,未匹配到机构，不能导入。,01,zb3,2,2,26,联系人,80000000,3c958fbf175944f6b319711c3e5a1c4b,,1,");
//	}   
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String clienttype = request.getParameter("clienttype");
		if (method.equals("zzbFile")) {//上传检查
			if(clienttype!=null && clienttype.equals("1")){
				uploadZzbFile2(request, response);
			} else {
				uploadZzbFile(request, response);
			}
		} else if (method.equals("zzbFileTYGS")) {//zzbFileTYGS通用格式上传检查
			if(clienttype!=null && clienttype.equals("1")){
				//uploadTYGSFile2(request, response);
			} else {
				uploadTYGSFile(request, response);
			}
		} else if (method.equals("zzbFileImp")) {//导入
			if(clienttype!=null && clienttype.equals("1")){
				uploadZzbFileImp2(request, response); 
			} else {
				uploadZzbFileImp(request, response); 
			}
		} else if (method.equals("zzbFilePsn")) {//数据包导入
			uploadzzbFilePsn(request, response); 
		} else if (method.equals("zzbFileImpPsn")) {//
			uploadzzbFileImpPsn(request, response); 
		} else if (method.equals("zzbFileImpRefresh")) {
			zzbFileImpRefresh(request, response); 
		}else if(method.equals("uploadAttachment")){
			uploadAttachment(request, response);
		}else if("uploadZip".equals(method)){
			uploadZip(request, response);
		}else if("uploadFolder".equals(method)){		//电子档案文件上传
			uploadFolder(request, response);
		}else if("deleteFolderFile".equals(method)){		//电子档案文件删除
			deleteFolderFile(request, response);
		}else if("downloadFolderFile".equals(method)){		//电子档案文件下载
			downloadFolderFile(request, response);
		} else if (method.equals("uploadHzbOldFile")) {			//上传检查老版本
			uploadHzbOldFile(request, response);
		} else if("hzbOldFileImp".equals(method)){			//导入老版本
			uploadHzbOldFileImp(request, response);
		} else if (method.equals("uploadHzbMakeFile")) {			//上传检查套改版本
			uploadHzbMakeFile(request, response);
		} else if("hzbMakeFileImp".equals(method)){			//导入套改版本
			uploadHzbMakeFileImp(request, response);
		} else if (method.equals("zzbFileWages")) {//工资导入
			uploadzzbFileWages(request, response); 
		} else if (method.equals("zzbFileImpWages")) {//工资导入
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
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,a0165) values ('"+ id +"','"+a0165+"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//刷新数据的创建。
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpMakeHzbThreadNewCaNull thr = new ImpMakeHzbThreadNewCaNull(filename, id,b0111new,a0165, user,userVo,fxz);
			new Thread(thr,"数据导入线程1").start();
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
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
						throw new AppException("上传失败");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('检查失败！');</script>");
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
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,a0165) values ('"+ id +"','"+a0165+"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//刷新数据的创建。
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpOldHzbThreadNewCaNull thr = new ImpOldHzbThreadNewCaNull(filename, id,b0111new,a0165, user,userVo,fxz);
			new Thread(thr,"数据导入线程1").start();
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
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
						throw new AppException("上传失败");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('检查失败！');</script>");
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						System.out.println("数据包名：=================="+filename);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
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
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String type = map.get("type");
			CommonQueryBS.systemOut("dataversion------"+map.get("dataversion"));
			if (type!=null && type.equals("31")) {
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "可以导入。");
					map.put("packagetype", "按人员导出");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "检测到数据包版本过旧，请使用2019版HZB人员数据包！");
				}
			} else if(type!=null && "21".equals(type)){
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "可以导入。");
					map.put("packagetype", "按机构导出");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "检测到数据包版本过旧，请使用2019版HZB数据包！");
				}
			} else if(type!=null && "23".equals(type)){
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "可以导入。");
					map.put("packagetype", "按机构导出");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "检测到数据包版本过旧，请使用2019版通用格式数据包！");
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", "无法导入，请使用正确的数据包。");
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
			//out.println("<script>parent.odin.alert('检查完成！');");
			//out.println("</script>");
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('检查失败！');</script>");
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
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
					String path = fi.getName();// 文件名称
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						System.out.println("数据包名：=================="+filename);
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						filePath = upload_file + uuid + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
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
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			Zip7z.unzip7zOne(filePath, unzip, null);
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String type = map.get("type");
			CommonQueryBS.systemOut("dataversion------"+map.get("dataversion"));
			if (type!=null && type.equals("A33")) {
				if(!"".equals(map.get("dataversion"))){
					map.put("valueimp", "1");
					map.put("orginfo", "可以导入。");
					map.put("packagetype", "按机构导出工资");
				}else{
					map.put("valueimp", "2");
					map.put("orginfo", "检测到数据包版本过旧，请使用2019版ZIP工资数据包！");
				}
			}else {
				map.put("valueimp", "2");
				map.put("orginfo", "无法导入，请使用正确的数据包。");
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
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('检查失败！');</script>");
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
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //文件后缀
			ImpHzbThread thr = new ImpHzbThread(filename, id, user,userVo,a0165,fxz);
			new Thread(thr,"数据导入线程1").start();
//			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //文件后缀
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				new LogUtil().createLog("421", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
//			} else {
//				new LogUtil().createLog("422", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
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
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpWagesThread thr = new ImpWagesThread(filename, id, user,userVo);
			new Thread(thr,"数据导入线程1").start();
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
		String rootPath = "";									// 项目路径
		String imprecordid = "";								// 导入记录id
		String process_run = "1";								// 导入过程序号
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// 业务处理bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// 上传文件整体路径
		String unzip = "";										// 解压路径
		try {
			
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			// 001==============处理文件 后缀  格式  上传路径  解压路径==============================================================
			String classPath = getClass().getClassLoader().getResource("/").getPath();				// class 路径
			if ("\\".equals(File.separator)) {														// windows下
				rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {														// linux下
				rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = rootPath + "upload/" + uuid + "/";									// 上传路径
			unzip = rootPath + "upload/unzip/" + uuid + "/";										// 解压路径
			File file = new File(unzip);															// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //文件后缀
			filePath = upload_file + "/" + uuid + "." +houzhui;									    //上传文件整体路径
			String from_file = unzip + "Photos/";													//解压后图片存放路径
			
			// 003================  文件解压   =========================================================================
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.extractilenew(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.extractilenew(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//004================  解析头文件   =========================================================================
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "" + "gwyinfo.xml");
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			String B0111 = headlist.get(0).get("B0111");							// 根节点上级机构id
			String deptid = "";														// 根节点上级机构id
			String impdeptid = "";													//根节点机构id
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
				process_run = "3";																//导入过程
				int t_n = 0;
				//004================ 解析单个文件，倒入数据库   =================================================================================
				
				
				//NO.006.002 导入A02表
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A02数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A02", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				
				
				//NO.006.003 导入A06表
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A06数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A06", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A08数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A08", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A11数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A11", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A14数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A14", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A15数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A15", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A29数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A29", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A30数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A30", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A31数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A31", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A36数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A36", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A37数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A37", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A41数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A41", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A53数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A53", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("aa53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			 
				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A57数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A57", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				//NO.006.001 导入B01表
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表B01数据， 剩余"+(number2--)+"张。",imprecordid);
//				CommonQueryBS.systemOut("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
//				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
//				CommonQueryBS.systemOut("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");

				
//				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A01数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				imprecord.setTotalnumber(t_n + "");
				sess.update(imprecord);
			}
			CommonQueryBS.systemOut("END INSERT---------" +DateUtil.getTime());
			sess.flush();
			
//			KingbsconfigBS.saveImpDetail(process_run, "2", "提取完成", imprecordid);
			try {
				if (houzhui.equalsIgnoreCase("hzb")) {
					new LogUtil("421", "IMP_RECORD", "", "", "导入临时库", new ArrayList(),userVo).start();
				} else {
					new LogUtil("422", "IMP_RECORD", "", "", "导入临时库", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil().createLog("421", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
					} else {
						new LogUtil().createLog("422", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
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
//				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
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
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID,a0165,isContain) values ('"+ id +"','"+a0165+"','"+isContain+"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//刷新数据的创建。
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpZzbHzbThreadNewCaNull thr = new ImpZzbHzbThreadNewCaNull(filename, id,b0111new,a0165, user,userVo,fxz,isContain);
			new Thread(thr,"数据导入线程1").start();
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
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
						throw new AppException("上传失败");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "1");// 1 使用hzb密码解压gwyinfo.xml
//			} else {
//				NewSevenZipUtil.extractileOnenew(filePath, unzip, "2");// 1 使用标准版密码解压
//			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
//			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
//			Map<String, String> map = headlist.get(0);
//			String B0111 = map.get("B0111");// 根节点上级机构id
//			String org = map.get("B0101");// 根节点上级机构id
//			String B0114 = map.get("B0114");// 根节点上级机构id
//			String B0131 = map.get("B0194");// 根节点上级机构id
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
//					map.put("orginfo", "已匹配到机构，可以导入。");
//				} else {
//					map.put("valueimp", "2");
//					map.put("orginfo", "机构类型不匹配，不能导入。");
//				}
//			} else {
//				map.put("valueimp", "2");
//				map.put("orginfo", "未匹配到机构，不能导入。");
//			}
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				map.put("gjgs", "1");
//				map.put("gjgs_combo", "hzb");
//			} else {
//				map.put("gjgs", "2");
//				map.put("gjgs_combo", "zb3");
//			}
//			map.put("b0101", org);
//			map.put("packagetype", "按机构导出");
			
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			/*
			 * pywu  20170608
			 * 因为在jsp中对中文进行了编码的时候用的是UTF-8的编码方式，而在servlet中调用request.getParameter();方法的时候使用服务器指定的编码格式自动解码一次，
			 * 所以前台编码一次后台解码一次而解码和编码的方式不用所以造成了乱码的出现，
			 * 
			 * */
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.document.getElementById('uuid').value='"+uuid
					+"';parent.parent.document.getElementById('subWinIdBussessId').value='"+uuid+"@"+URLEncoder.encode(URLEncoder.encode(filename,"UTF-8"),"UTF-8")+"';"
							+ "parent.parent.radow.doEvent('initX');"); 
			out.println("</script>");
//			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.realParent.$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew','导入窗口',850,550,'"+uuid +"@"+URLEncoder.encode(filename,"UTF-8")+"','"+request.getContextPath()+"');"); 
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
//					"parent.document.getElementById('count').value= "+ size +";" +  //统计人数
//					"parent.document.getElementById('type11').value= '"+ type +"';" +  //数据库类型
//					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
//					"parent.Ext.getCmp('impBtn').setDisabled(false);parent.parent.clickquery();");
//			out.println("</script>");
//			out.println("<script>parent.odin.alert('"+ map.get("orginfo") +"');");
//			out.println("</script>");
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('检查失败！');</script>");
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String filename = "";
			String houzhui = "";
			String filePath = "";
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file1 = new File(unzip);
				if (!file1.exists() && !file1.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
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
						throw new AppException("上传失败");
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("7z")) {
				Zip7z.unzip7zOne(filePath, unzip, "20171020");
			} else if (houzhui.equalsIgnoreCase("zip")){
				Zip7z.unzip7zOne(filePath, unzip, null);
			}
			//ZipUtil.unZip(filePath);
			
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			/*
			 * pywu  20170608
			 * 因为在jsp中对中文进行了编码的时候用的是UTF-8的编码方式，而在servlet中调用request.getParameter();方法的时候使用服务器指定的编码格式自动解码一次，
			 * 所以前台编码一次后台解码一次而解码和编码的方式不用所以造成了乱码的出现，
			 * 
			 * */
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.document.getElementById('uuid').value='"+uuid
					+"';parent.parent.document.getElementById('subWinIdBussessId').value='"+uuid+"@"+URLEncoder.encode(URLEncoder.encode(filename,"UTF-8"),"UTF-8")+"';"
							+ "parent.parent.radow.doEvent('initX');"); 
			out.println("</script>");
//			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.realParent.$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew','导入窗口',850,550,'"+uuid +"@"+URLEncoder.encode(filename,"UTF-8")+"','"+request.getContextPath()+"');"); 
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
//					"parent.document.getElementById('count').value= "+ size +";" +  //统计人数
//					"parent.document.getElementById('type11').value= '"+ type +"';" +  //数据库类型
//					"parent.Ext.getCmp('b0131_combo').setValue('"+ map.get("b0131_combo") +"');" +
//					"parent.Ext.getCmp('impBtn').setDisabled(false);parent.parent.clickquery();");
//			out.println("</script>");
//			out.println("<script>parent.odin.alert('"+ map.get("orginfo") +"');");
//			out.println("</script>");
			CommonQueryBS.systemOut("上传====================");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('请选择正确的Zip数据包！');</script>");
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
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit2(id);//刷新数据的创建。
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpZzbHzbThreadNew thr = new ImpZzbHzbThreadNew(filename, id, user,userVo);
			new Thread(thr,"数据导入线程1").start();
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
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
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.unzip7zOne(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String B0111 = map.get("B0111");// 根节点上级机构id
			String org = map.get("B0101");// 根节点上级机构id
			String B0114 = map.get("B0114");// 根节点上级机构id
			String B0131 = map.get("B0194");// 根节点上级机构id
			map.put("b0131_combo", "");
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			if(!map.get("type").equals("30")){
				map.put("valueimp", "2");
				map.put("orginfo", "不是装载数据包，不能导入。");
			} else if (b01s!=null && b01s.size()>0) {
				B01 b01 = b01s.get(0);
				if(b01.getB0194().equals(B0131)){
					Object obj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value t where t.code_type='B0194' AND t.code_value='" + B0131 + "'").uniqueResult();
					map.put("b0131_combo", obj ==null ? "" : obj.toString());
					map.put("valueimp", "1");
					map.put("orginfo", "已匹配到机构，可以导入。");
				} else {
					map.put("valueimp", "2");
					map.put("orginfo", "机构类型不匹配，不能导入。");
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", "未匹配到机构，不能导入。");
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "1");
				map.put("gjgs_combo", "hzb");
			} else {
				map.put("gjgs", "2");
				map.put("gjgs_combo", "zb3");
			}
			map.put("b0101", org);
			map.put("packagetype", "按机构导出");
			
			
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
			CommonQueryBS.systemOut("上传====================");
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('检查失败！');");
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = rootPath + "upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
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
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.extractileOnenew(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.extractileOnenew(filePath, unzip, "2");
			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String B0111 = map.get("B0111");// 根节点上级机构id
			String org = map.get("B0101");// 根节点上级机构id
			String B0114 = map.get("B0114");// 根节点上级机构id
			String B0131 = map.get("B0194");// 根节点上级机构id
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
					map.put("orginfo", URLEncoder.encode("已匹配到机构，可以导入。","UTF-8"));
				} else {
					map.put("valueimp", "2");
					map.put("orginfo", URLEncoder.encode("机构类型不匹配，不能导入。","UTF-8"));
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", URLEncoder.encode("未匹配到机构，不能导入。","UTF-8"));
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "1");
				map.put("gjgs_combo", "hzb");
			} else {
				map.put("gjgs", "2");
				map.put("gjgs_combo", "zb3");
			}
			map.put("b0101", URLEncoder.encode(org,"UTF-8"));
			map.put("packagetype", URLEncoder.encode("按机构导出","UTF-8"));
			
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
			CommonQueryBS.systemOut("上传===================="+net.sf.json.JSONObject.fromObject(map).toString());
			
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('检查失败！');");
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
			new Thread(thr,"数据导入线程1").start();
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
			
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String uuid = UUID.randomUUID().toString().replace("-", "");
//			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
//			if ("\\".equals(File.separator)) {// windows下
//				rootPath = classPath.substring(1, classPath
//						.indexOf("WEB-INF/classes"));
//				rootPath = rootPath.replace("/", "\\");
//			}
//			if ("/".equals(File.separator)) {// linux下
//				rootPath = classPath.substring(0, classPath
//						.indexOf("WEB-INF/classes"));
//				rootPath = rootPath.replace("\\", "/");
//			}
//			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";// 上传路径
			try {
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String unzip = upload_file + "unzip/" + uuid + "/";// 解压路径
			try {
				File file = new File(unzip);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
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
				if (!filePathF.exists()) {// 如果文件夹不存在则创建
					filePathF.createNewFile();
				}
				copyFile(fromfilef, filePathF);
			} else {
				out.println("2");
				return;
			}
			
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			String B0111 = map.get("B0111");// 根节点上级机构id
			String org = map.get("B0101");// 根节点上级机构id
			String B0114 = map.get("B0114");// 根节点上级机构id
			String B0131 = map.get("B0194");// 根节点上级机构id
			map.put("b0131_combo", "");
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			if (b01s!=null && b01s.size()>0) {
				B01 b01 = b01s.get(0);
				if(b01.getB0194().equals(B0131)){
					Object obj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value t where t.code_type='B0194' AND t.code_value='" + B0131 + "'").uniqueResult();
					map.put("b0131_combo", obj ==null ? "" : obj.toString());
					map.put("valueimp", "1");
					map.put("orginfo", "已匹配到机构，可以导入。");
				} else {
					map.put("valueimp", "2");
					map.put("orginfo", "机构类型不匹配，不能导入。");
				}
			} else {
				map.put("valueimp", "2");
				map.put("orginfo", "未匹配到机构，不能导入。");
			}
			if (houzhui.equalsIgnoreCase("hzb")) {
				map.put("gjgs", "1");
				map.put("gjgs_combo", "hzb");
			} else {
				map.put("gjgs", "2");
				map.put("gjgs_combo", "zb3");
			}
			map.put("b0101", org);
			map.put("packagetype", "按机构导出");
			
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
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// 业务处理bs
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
	 * 已弃用
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
				pstmt.setString(1, "创建机构_"+cj +"_"+i2+"_"+i);
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
		int num1 = 0;//百
		int num2 = 0;//十
		int num3 = 0;//个
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

	/* 判断是否是中文 */
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

	// 检查文件格式
	public String checkFile(FileItem item) {
		String filename = item.getName();
		filename = filename.toLowerCase();
		if (filename.endsWith(".zb3")||filename.endsWith(".hzb")) {
			return "0";
		} else {
			return "-1";
		}
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
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
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	private static void copyPhotos(String to_file, String from_file) {
		// TODO Auto-generated method stub
		/**
		 * 缺少判断文件为空代码。。。。。。。。。。。。。。
		 */
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(from_file)).listFiles();
		try {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 复制文件
					copyFile(file[i], new File(to_file + file[i].getName()));
				}
				if (file[i].isDirectory()) {
					// 复制目录
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
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outbuff.flush();
		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());

				copyFile(sourceFile, targetFile);

			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}

	}
	
	/**
	 *  附件上传
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void uploadAttachment(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		//获取参数:     flag:附件类型标志   uuid:关联主键   uname:名称
		String flag=new String(request.getParameter("flag").getBytes("iso-8859-1"),"gbk");
		String uuid= new String(request.getParameter("uuid").getBytes("iso-8859-1"),"gbk");
		String uname=new String(request.getParameter("uname").getBytes("iso-8859-1"),"gbk");
		//子文件夹，用来区分呈报单、登记表及其他附件的文件夹名称
		//flag=0时为呈报单，flag=1时为登记表及其他附件,flag=2时为本级呈报单附件(接收页面的附件上传),flag=3时为上传上报呈报单附件
		String attachPath = "";
		if("1".equals(flag) || "2".equals(flag) || "3".equals(flag)){
			attachPath = "CBDAttachment";
		}else if("0".equals(flag)){
			//创建数据库对象
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = "CBDUpload/"+attachPath + "/";// 上传路径
			try {
				File file = new File(rootPath+upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取表单信息
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//定义数组，存放附件路径和备注信息
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					//判断上传文件的尺寸，大于10M的文件不能上传
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>parent.parent.odin.alert('文件："+path+"的尺寸超过10M，不能上传！');</script>");
						return ;
					}
					try {
						//获取文件名（带后缀）
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//截取文件名（不带后缀）
						filename = filename.substring(0,filename.lastIndexOf("."));
//						if("0".equals(flag) || "2".equals(flag)){
//							filename = uname;
//						}
						filePath = upload_file + filename + "." + houzhui;
						File file = new File(rootPath+filePath);
						//判断文件是否已经上传，如果已经上传则跳过不做操作，防止文件重复上报。
//						if(file.exists()){
//							continue;
//						}
						fi.write(new File(rootPath+filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName(), filePath);
						filename_map.put(fi.getFieldName()+"_filename", filename + "." + houzhui);
						//将附件信息保存至数据表中
						//saveAttachmentInfo(uname,uuid,filePath,flag,filename);
						
					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//将附件信息保存至附件表中
			for(int i=1;i<=rowlength;i++){
				String text = text_map.get("textid_"+i);
				String filepath = filePath_map.get("fileid_"+i);
				String fileName = filename_map.get("fileid_"+i+"_filename");
				saveAttachmentInfo(uname,uuid,filepath,flag,fileName,text);
			}
			out.println("<script>parent.parent.odin.alert('上传成功');</script>");
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('上传失败！');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	
	/**
	 *  将上传的附件信息保存在数据库中
	 * @param name
	 * @param uuid
	 * @param filepath
	 * @param flag
	 * @param filename
	 * @return
	 */
	public int saveAttachmentInfo(String name,String uuid,String filepath,String flag,String filename,String text){
		
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//创建附件对象
		AttachmentInfo atta = null;
		//获取人员信息
		A01 a01 = null;
		if("0".equals(flag)){
			a01 = (A01) sess.createQuery("from A01 where a0000 = '"+uuid+"'").list().get(0);
		}
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		//当上传的附件为人员相关附件是（flag=1）
		//当上传的附件为登记表及其他附件时，保存人员相关信息，上传的附件为呈报单时，仅保存关联对象字段
		if("0".equals(flag)){
			//判断是否上传相同附件信息
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
				//获取主键
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
			
			//当上传上报呈报单时，根据本级呈报单编号查询上报呈报单编号
			if("2".equals(flag)){
				List<CBDInfo> list3 = sess.createQuery("from CBDInfo where objectno = '"+uuid+"'").list();
				if(list3.size()>0){
					uuid = list3.get(0).getCbd_id();
				}
			}
			//查询呈报单是否已经上传附件，如果已经上传了相同附件，将呈报单的附件主键设置到附件对象中
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
				//获取主键
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
				//查询呈报单是否已经上传相同附件，如果已经上传了相同附件，将呈报单的附件主键设置到附件对象中
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
					//获取主键
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
		
		//执行数据库操作
		
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		ts.commit();
		
		return 0;
	}
	
	
	/**
	 *  附件呈报单数据包
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void uploadZip(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		//获取参数:     flag:附件类型标志
		String flag=new String(request.getParameter("flag").getBytes("iso-8859-1"),"gbk");

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		CBDTools ct = new CBDTools();
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String rootPath = ct.getPath();
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			
			String upload_file = "GetZip/";// 上传路径
			try {
				File file = new File(rootPath+upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取表单信息
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString();
				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					try {
						//获取文件名（带后缀）
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//截取文件名（不带后缀）
						filename = filename.substring(0,filename.lastIndexOf("."));
						filePath = upload_file + filename + "." + houzhui;
						fi.write(new File(rootPath+filePath));
						fi.getOutputStream().close();
						
						//解压呈报单数据包
						ct.unzip(rootPath+filePath, rootPath+"GetZip/");
						//删除原数据包
						File oldZipFile = new File(rootPath+filePath);
						oldZipFile.delete(); 
						
						//呈报单数据包导入成功后，修改呈报单状态
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
							throw new AppException("导入失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			out.println("<script>parent.parent.odin.alert('导入成功');</script>");
			out.println("<script language='javascript'>parent.parent.radow.doEvent('btnsx.onclick');</script>");
		} catch (Exception e) {
			out.println("<script>parent.parent.odin.alert('导入失败！');");
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
		
		
	}
	
	
	/**
	 *  电子文档文件上传
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void uploadFolder(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		
		//获取参数:     a0000:人员id   treeId:文件夹id   uname:名称
		String a0000=new String(request.getParameter("a0000").getBytes("iso-8859-1"),"gbk");
		String treeId= new String(request.getParameter("treeId").getBytes("iso-8859-1"),"gbk");
		//String uname=new String(request.getParameter("uname").getBytes("iso-8859-1"),"gbk");
		
		//flag=0时为呈报单，flag=1时为登记表及其他附件,flag=2时为本级呈报单附件(接收页面的附件上传),flag=3时为上传上报呈报单附件
		String attachPath = a0000 + "/" + treeId;
		
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			String upload_file = "FolderUpload/"+attachPath + "/";// 上传路径
			try {
				File file = new File(rootPath+upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取表单信息
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//定义数组，存放附件路径和备注信息
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
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					//判断上传文件的尺寸，大于10M的文件不能上传
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					
					
					DecimalFormat df = new DecimalFormat("#.00"); 
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					
					if(fi.getSize() < 1048576){
						fileSize = "0" + fileSize;
					}
					
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>odin.alert('文件："+path+"的尺寸超过10M，不能上传！');</script>");
						return ;
					}
					try {
						//获取文件名（带后缀）
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//截取文件名（不带后缀）
						filename = filename.substring(0,filename.lastIndexOf("."));
//						if("0".equals(flag) || "2".equals(flag)){
//							filename = uname;
//						}
						filePath = upload_file + filename + "." + houzhui;
						File file = new File(rootPath+filePath);
						//判断文件是否已经上传，如果已经上传则跳过不做操作，防止文件重复上报。
//						if(file.exists()){
//							continue;
//						}
						fi.write(new File(rootPath+filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						//将附件信息保存至数据表中
						//saveAttachmentInfo(uname,uuid,filePath,flag,filename);
						
					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//将附件信息保存至附件表中
			for(int i=1;i<=rowlength;i++){
				String text = text_map.get("textid_"+i);
				String filepath = filePath_map.get("excelFile"+i);
				String fileName = filename_map.get("excelFile"+i+"_filename");
				String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
				saveFolder(a0000,filepath,treeId,fileName,fileSize);
			}
			out.println("<script>odin.alert('上传成功');</script>");
		} catch (Exception e) {
			out.println("<script>odin.alert('上传失败！');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	
	/**
	 *  将上传的电子档案文件信息保存在数据库中
	 * @param a0000		人员id
	 * @param filepath	文件地址
	 * @param treeId	文件夹treeid
	 * @param filename	文件名称
	 * @return
	 */
	public int saveFolder(String a0000,String filepath,String treeId,String filename,String fileSize){
		
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//创建附件对象
		//AttachmentInfo atta = null;
		Folder atta = null;
		
		//获取人员信息
		A01 a01 = null;
		
		a01 = (A01) sess.createQuery("from A01 where a0000 = '"+a0000+"'").list().get(0);
		
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		//当上传的附件为人员相关附件是（flag=1）
		
		
		//判断是否上传相同附件信息
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
			atta.setCatalog(filepath);			//上传附件的相对路径
			atta.setName(filename);				//文件名称
			atta.setFileSize(fileSize); 		//文件大小
			atta.setUploads(user.getName());  	//上传人名称
			atta.setA0000(a0000);  				//所属人员id
			atta.setTime(createdate); 			//上传时间
			atta.setTreeId(treeId);  			//所属文件夹id
			
			
		}else{
			atta = new Folder();
			//获取主键
			String uid = UUID.randomUUID().toString();
			atta.setId(uid);
			atta.setCatalog(filepath);			//上传附件的相对路径
			atta.setName(filename);				//文件名称
			atta.setFileSize(fileSize); 		//文件大小
			atta.setUploads(user.getName());  	//上传人名称
			atta.setA0000(a0000);  				//所属人员id
			atta.setTime(createdate); 			//上传时间
			atta.setTreeId(treeId);  			//所属文件夹id
		}
		
		//执行数据库操作
		
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	 
	
	//删除电子档案文件
	public void deleteFolderFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//获得要删除的文件id
		//String filePath = request.getParameter("filePath");		//要删除的文件相对路径
		
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		if(id!=null && !"".equals(id)){
			Folder folder = (Folder) session.get(Folder.class, id);
			if(folder!=null && !"".equals(folder)){
				session.delete(folder);
				session.flush();
			}
		}
		
		//删除文件
		try {
			String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			
			if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			
			filePath = rootPath + filePath;			//拼接出绝对路径
			
			this.deleteFile(filePath);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//下载文件
	public void downloadFolderFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		//String filePath= new String(request.getParameter("filePath").getBytes("iso-8859-1"),"gbk");
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);		//文件名称
		
		String rootPath = "";
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		if ("\\".equals(File.separator)) {// windows下
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		if ("/".equals(File.separator)) {// linux下
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		rootPath = URLDecoder.decode(rootPath, "GBK");
		filePath = rootPath + filePath;			//拼接出绝对路径
		
		
        /*读取文件*/
        File file = new File(filePath);
        /*如果文件存在*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*如果文件长度大于0*/
            if (fileLength != 0) {
                /*创建输入流*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*创建输出流*/
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
