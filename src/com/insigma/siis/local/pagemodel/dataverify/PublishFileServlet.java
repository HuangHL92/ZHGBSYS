package com.insigma.siis.local.pagemodel.dataverify;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.DHXJ;
import com.insigma.siis.local.business.entity.GI;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.Notice;
import com.insigma.siis.local.business.entity.NoticeFile;
import com.insigma.siis.local.business.entity.NoticeRecipent;
import com.insigma.siis.local.business.entity.Policy;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.YearCheckFile;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.gbwh.KHPJPageModel;
import com.insigma.siis.local.pagemodel.meeting.MeetingThemePageModel;
import com.insigma.siis.local.pagemodel.meeting.PublishPageModel;
import com.insigma.siis.local.pagemodel.sysmanager.oraclebak.ExpData;
import com.insigma.siis.local.pagemodel.train.HandleTrainPageModel;
import com.insigma.siis.local.pagemodel.xbrm.JSGLPageModel;
import com.insigma.siis.local.pagemodel.xbrm.QCJSPageModel;
  

/**
 * ????????Servlet
 * @author fujun
 *
 */
public class PublishFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	private LogUtil applog = new LogUtil();
	
	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}  
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if("addPolicy".equals(method)){						//????????????
			addPolicy(request, response);
		}else if("addGeneralInspection".equals(method)){						//????????????
			addGeneralInspection(request, response);
		}else if("addMeetingElection".equals(method)){						//????????????
			addMeetingElection(request, response);
		}else if("deleteMeetingFile".equals(method)){						//????????????
			deleteMeetingFile(request, response);
		}else if("deletePolicyFile".equals(method)){		//????????????
			deletePolicyFile(request, response);
		}else if("downloadPolicyFile".equals(method)){		//????????????????
			downloadPolicyFile(request, response);
		}else if("addNotice".equals(method)){		//????????????
			addNotice(request, response);
		}else if("deleteNotice".equals(method)){		//????????????
			deleteNotice(request, response);
		}else if("updateNotice".equals(method)){		//????????????
			updateNotice(request, response);
		}else if("deleteNoticeFile".equals(method)){		//????????????????
			deleteNoticeFile(request, response);
		}else if("customPageSize".equals(method)){		//??????????????????????????????????session
			
			String pageSize = request.getParameter("pageSize");		//????????????id		
			HttpSession session=request.getSession(); 
			session.setAttribute("pageSize",pageSize); 
			
		}else if("expPBCX".equals(method)){//  ????????????????
			try {
				ExpData ebs= new ExpData(request, response);
				ebs.expData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("isOut".equals(method)){
			
			HttpSession session=request.getSession(); 
			Object id =  session.getAttribute("1111");
			isOut(request, response);
		}else if("SysOrgOtherPageSize".equals(method)){
			String pageSize = request.getParameter("pageSize");		
			HttpSession session=request.getSession(); 
			session.setAttribute("SOOPageSize",pageSize); 
		}else if("downloadFile".equals(method)){		//????????????????
			try {
				downloadFile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("playFile".equals(method)){		//????????????????
			try {
				playFile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("downloadshanghuicailiao".equals(method)){//  ????????????
			try {
				JSGLPageModel.downloadshanghuicailiao(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("downloadorgFile".equals(method)){
			
			try {
				downloadorgFile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if("publish_att".equals(method)){
			try {
				PublishPageModel.downFile(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if("gbkh_att".equals(method)){
			try {
				KHPJPageModel.downFile(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if("meetingtheme".equals(method)){
			try {
				MeetingThemePageModel.downFile(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if("Train".equals(method)){
			try {
				HandleTrainPageModel.downFile(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	} 
	
	private String[] getpathCheckRegFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//??????????????????????
		String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("checkregfileid"),"UTF-8"),"UTF-8");
		
		//????????????????????????????????
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME from CHECKREGFILE "
				+ " where CKFILEID = '" +id + "'").list();
		Object[] fileVO = list.get(0);
		return new String[]{QCJSPageModel.disk+fileVO[1]+fileVO[0],fileVO[2]+""};
	}

	public void playFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException{

		String uuid = request.getParameter("uuid");
		String SPid = request.getParameter("SPid");
		String checkregfileid = request.getParameter("checkregfileid");
		String path = "";
		String[] pathInfo ;
		if(uuid!=null&&!"".equals(uuid)){//??????????????
			pathInfo = (String[])request.getSession().getAttribute(uuid);
			//request.getSession().removeAttribute(uuid);
		}else if(SPid!=null&&!"".equals(SPid)){//??????????????
			pathInfo = getpathSP(request,response);
			//request.getSession().removeAttribute(uuid);
		}else if(checkregfileid!=null&&!"".equals(checkregfileid)){//????????
			pathInfo = getpathCheckRegFile(request,response);
			//request.getSession().removeAttribute(uuid);
		}else{//????????
			pathInfo = getpath(request,response);
		}
		path = pathInfo[0];
		String filename = pathInfo[1];
		
        /*????????*/
       //File file = new File("C:\\Users\\LENOVO\\Desktop\\436475f1e75cc5266857e85dc1de0063.jpg");
        /*????????????*/
        if (filename != null && !filename.equals("")) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			
           
            
			System.out.println("========================================");
			System.out.println(filename);
			System.out.println("========================================");
			
            File file = new File(path);
            
            response.setContentLength((int)file.length());
            /*????????????????0*/
            if (file.isFile()) {
            	// ????????????????????
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
            	
                /*??????????*/
                ServletOutputStream servletOS = response.getOutputStream();

                servletOS.write(buffer);
                servletOS.flush();
                servletOS.close();
            }
        }
	}
	//????????
	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException{
		
		String uuid = request.getParameter("uuid");
		String SPid = request.getParameter("SPid");
		String checkregfileid = request.getParameter("checkregfileid");
		String path = "";
		String[] pathInfo ;
		if(uuid!=null&&!"".equals(uuid)){//??????????????
			pathInfo = (String[])request.getSession().getAttribute(uuid);
			//request.getSession().removeAttribute(uuid);
		}else if(SPid!=null&&!"".equals(SPid)){//??????????????
			pathInfo = getpathSP(request,response);
			//request.getSession().removeAttribute(uuid);
		}else if(checkregfileid!=null&&!"".equals(checkregfileid)){//????????
			pathInfo = getpathCheckRegFile(request,response);
			//request.getSession().removeAttribute(uuid);
		}else{//????????
			pathInfo = getpath(request,response);
		}
		path = pathInfo[0];
		String filename = pathInfo[1];
		
        /*????????*/
       //File file = new File("C:\\Users\\LENOVO\\Desktop\\436475f1e75cc5266857e85dc1de0063.jpg");
        /*????????????*/
        if (filename != null && !filename.equals("")) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("charset=GBK");
			
           
			if (filename.startsWith("gbynrmb")) {
				filename = "??????????????????????????????"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbswsjrmb")) {
				filename = "????????????????????????????????????????????"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbcwhrmb")) {
				filename = "??????????????????????????????????????????"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbynrmbgd")) {
				filename = "??????????????????????????????????"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbswsjrmbgd")) {
				filename = "????????????????????????????????????????????????"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbcwhrmbgd")) {
				filename = "??????????????????????????????????????????????"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));	
			}else {
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}
            
			System.out.println("========================================");
			System.out.println(filename);
			System.out.println("========================================");
			
            File file = new File(path);
            
            response.setContentLength((int)file.length());
            /*????????????????0*/
            if (file.isFile()) {
            	// ????????????????????
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
            	
                /*??????????*/
                ServletOutputStream servletOS = response.getOutputStream();

                servletOS.write(buffer);
                servletOS.flush();
                servletOS.close();
            }
        }
       
	}

	private String[] getpath(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//??????????????????????
		String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"),"UTF-8");
		
		//????????????????????????????????
		HBSession sess = HBUtil.getHBSession();
		JsAtt fileVO = (JsAtt) sess.get(JsAtt.class, id);
		
		String filename = fileVO.getJsa04();
		
		return new String[]{QCJSPageModel.disk+fileVO.getJsa07()+fileVO.getJsa00(),filename};
	}
	
	private String[] getpathSP(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//??????????????????????
		String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("SPid"),"UTF-8"),"UTF-8");
		
		//????????????????????????????????
		HBSession sess = HBUtil.getHBSession();
		Sp_Att fileVO = (Sp_Att) sess.get(Sp_Att.class, id);
		
		String filename = fileVO.getSpa02();
		
		return new String[]{QCJSPageModel.disk+fileVO.getSpa05()+fileVO.getSpa00(),filename};
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	public boolean deleteFile(String filename) throws AppException {
		java.io.File dir = new java.io.File(filename);
		boolean b = true;
		if (dir.exists()) {
			b = dir.delete();
		}
		return b;
	}

	

	// ??????????
	// param folderPath ??????????????????
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ??????????????????
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ????????????
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ????????????????????????
	// param path ??????????????????
	public boolean delAllFile(String path) {
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
				delAllFile(path + "/" + tempList[i]);// ??????????????????????
				delFolder(path + "/" + tempList[i]);// ??????????????
				flag = true;
			}
		}
		return flag;
	}
	
	
	/**
	 *  ????????????
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addPolicy(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String mainPath = AppConfig.HZB_PATH;
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//????????:    a0000:????id   treeId:??????id   uname:????
		String title =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8"),"UTF-8");
		String secret =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("secret"),"UTF-8"),"UTF-8");
		
		String attachPath =  "policy/" + user.getId()+"/"+uid;
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("????????");
			out = response.getWriter();
			//String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			/*if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");*/
			String upload_file = mainPath+"/PublishUpload/"+attachPath + "/";// ????????
			try {
				//File file = new File(rootPath+upload_file);
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ??????????????????????
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//????????????
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//????????????????????????????????
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
					// ????????????????????
					String path = fi.getName();// ????????
					//????????????????????????10M??????????????
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					
					
					DecimalFormat df = new DecimalFormat("#.00"); 
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					
					if(fi.getSize() < 1048576){
						fileSize = "0" + fileSize;
					}
					
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>odin.alert('??????"+path+"??????????10M????????????');</script>");
						return ;
					}
					try {
						//????????????????????
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//??????????????????????
						filename = filename.substring(0,filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;
						//File file = new File(rootPath+filePath);
						
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						
						
					} catch (Exception e) {
						try {
							throw new AppException("????????");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//????????????????????????
			for(int i=1;i<=rowlength;i++){
				String filepath = filePath_map.get("excelFile"+i);
				String fileName = filename_map.get("excelFile"+i+"_filename");
				String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
				saveFolder(uid,title,filepath,fileName,fileSize,secret);
			}
			out.println("<script>odin.alert('????????');</script>");
		} catch (Exception e) {
			out.println("<script>odin.alert('??????????');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	/**
	 *  ????????????
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addMeetingElection(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String mainPath = AppConfig.HZB_PATH;
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//????????:    a0000:????id   treeId:??????id   uname:????
		//String hymc =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("hymc"),"UTF-8"),"UTF-8");
		//String hylx =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("hylx"),"UTF-8"),"UTF-8");
		//String sj =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("sj"),"UTF-8"),"UTF-8");
		//String xjqy =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("xjqy"),"UTF-8"),"UTF-8");
		//String wcbz =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("wcbz"),"UTF-8"),"UTF-8");
		String dhxjid =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("dhxjid"),"UTF-8"),"UTF-8");
		
		String attachPath =  "meetingElection/" + user.getId()+"/"+uid;
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploaderl = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("????????");
			out = response.getWriter();
			//String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			String filePath1 = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			/*if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");*/
			String upload_file = mainPath+"/PublishUpload/"+attachPath + "/";// ????????
			try {
				//File file = new File(rootPath+upload_file);
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ??????????????????????
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//????????????
			List<FileItem> fileItems = uploaderl.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//????????????????????????????????
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
					// ????????????????????
					String path = fi.getName();// ????????
					//????????????????????????10M??????????????
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					
					
					DecimalFormat df = new DecimalFormat("#.00"); 
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					
					if(fi.getSize() < 1048576){
						fileSize = "0" + fileSize;
					}
					
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>odin.alert('??????"+path+"??????????10M????????????');</script>");
						return ;
					}
					try {
						//????????????????????
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//??????????????????????
						filename = filename.substring(0,filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;
						//File file = new File(rootPath+filePath);
						String upload_file1 = "/PublishUpload/"+attachPath + "/";// ????????
						filePath1 = upload_file1 + filename + "." + houzhui;
						
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath1);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						
						
					} catch (Exception e) {
						try {
							throw new AppException("????????");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//????????????????????????
			if(String.valueOf(rowlength).equals("3")) {
				String filepath = filePath_map.get("excelFile"+1);
				String filepath1= filePath_map.get("excelFilej"+2);
				String filepath2= filePath_map.get("excelFileg"+3);
				String fileName = filename_map.get("excelFile"+1+"_filename");
				String fileName1 = filename_map.get("excelFilej"+2+"_filename");
				String fileName2 = filename_map.get("excelFileg"+3+"_filename");
				String fileSize = fileSize_map.get("excelFile"+1+"_fileSize");
				String fileSize1 = fileSize_map.get("excelFilej"+2+"_fileSize");
				String fileSize2 = fileSize_map.get("excelFileg"+3+"_fileSize");
				saveMeetingElection(dhxjid,filepath,fileName,fileSize,filepath1,fileName1,fileSize1,filepath2,fileName2,fileSize2);
			}else if(String.valueOf(rowlength).equals("2")) {
				String filepath = filePath_map.get("excelFile"+1) == null ? "" :filePath_map.get("excelFile"+1) ;
				String filepath1= filePath_map.get("excelFilej"+1) == null ? "" :filePath_map.get("excelFilej"+1);
				String filepath2= filePath_map.get("excelFileg"+2) == null ? "" :filePath_map.get("excelFileg"+2);
				String fileName = filename_map.get("excelFile"+1+"_filename") == null ? "" :filename_map.get("excelFile"+1+"_filename");
				String fileName1 = filename_map.get("excelFilej"+1+"_filename") == null ? "" :filename_map.get("excelFilej"+1+"_filename");
				String fileName2 = filename_map.get("excelFileg"+2+"_filename") == null ? "" :filename_map.get("excelFileg"+2+"_filename");
				String fileSize = fileSize_map.get("excelFile"+1+"_fileSize") == null ? "" :fileSize_map.get("excelFile"+1+"_fileSize");
				String fileSize1 = fileSize_map.get("excelFilej"+1+"_fileSize") == null ? "" :fileSize_map.get("excelFilej"+1+"_fileSize");
				String fileSize2 = fileSize_map.get("excelFileg"+2+"_fileSize") == null ? "" :fileSize_map.get("excelFileg"+2+"_fileSize");
				saveMeetingElection(dhxjid,filepath,fileName,fileSize,filepath1,fileName1,fileSize1,filepath2,fileName2,fileSize2);
			}else if(String.valueOf(rowlength).equals("1")) {
				String filepath = filePath_map.get("excelFile"+1) == null ? "" :filePath_map.get("excelFile"+1) ;
				String filepath1= filePath_map.get("excelFilej"+1) == null ? "" :filePath_map.get("excelFilej"+1);
				String filepath2= filePath_map.get("excelFileg"+1) == null ? "" :filePath_map.get("excelFileg"+1);
				String fileName = filename_map.get("excelFile"+1+"_filename") == null ? "" :filename_map.get("excelFile"+1+"_filename");
				String fileName1 = filename_map.get("excelFilej"+1+"_filename") == null ? "" :filename_map.get("excelFilej"+1+"_filename");
				String fileName2 = filename_map.get("excelFileg"+1+"_filename") == null ? "" :filename_map.get("excelFileg"+1+"_filename");
				String fileSize = fileSize_map.get("excelFile"+1+"_fileSize") == null ? "" :fileSize_map.get("excelFile"+1+"_fileSize");
				String fileSize1 = fileSize_map.get("excelFilej"+1+"_fileSize") == null ? "" :fileSize_map.get("excelFilej"+1+"_fileSize");
				String fileSize2 = fileSize_map.get("excelFileg"+1+"_fileSize") == null ? "" :fileSize_map.get("excelFileg"+1+"_fileSize");
				saveMeetingElection(dhxjid,filepath,fileName,fileSize,filepath1,fileName1,fileSize1,filepath2,fileName2,fileSize2);
			}
				
			//out.println("<script>odin.alert('????????');</script>");
		} catch (Exception e) {
			out.println("<script>odin.alert('??????????');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	/**
	 *  ??????????????????????????????????
	 * @param title		????
	 * @param filepath	????????
	 * @param filename	????????
	 * @return
	 */
	public int saveMeetingElection(String dhxjid,String filepath,String filename,String fileSize,String filepath1,String filename1,String fileSize1,String filepath2,String filename2,String fileSize2){
		
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//????????????
		DHXJ atta = null;
		
		
		//????????????
		//????????
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new DHXJ();
		//????????
		//String uid = UUID.randomUUID().toString();
		atta = (DHXJ) HBUtil.getHBSession().get(DHXJ.class, dhxjid);
		if("".equals(filename)&&!"".equals(filename1)&&!"".equals(filename2)) {
			atta.setHfilename(filename2);//????????????
			atta.setHfileurl(filepath2);;	//????????????????????
			atta.setHfilesize(fileSize2);//????????????
			atta.setJfilename(filename1);//????????????????
			atta.setJfileurl(filepath1);;	//????????????????????????
			atta.setJfilesize(fileSize1);//????????????????
		}else if("".equals(filename1)&&!"".equals(filename)&&!"".equals(filename2)) {
			atta.setFilename(filename);	//????????????
			atta.setFilesize(fileSize);		//????????????
			atta.setFileurl(filepath);		//????????????????????
			atta.setHfilename(filename2);//????????????
			atta.setHfileurl(filepath2);;	//????????????????????
			atta.setHfilesize(fileSize2);//????????????
		}else if("".equals(filename2)&&!"".equals(filename1)&&!"".equals(filename)) {
			atta.setFilename(filename);	//????????????
			atta.setFilesize(fileSize);		//????????????
			atta.setFileurl(filepath);		//????????????????????
			atta.setJfilename(filename1);//????????????????
			atta.setJfileurl(filepath1);;	//????????????????????????
			atta.setJfilesize(fileSize1);//????????????????
		}else if("".equals(filename)&&"".equals(filename1)) {
			atta.setHfilename(filename2);//????????????
			atta.setHfileurl(filepath2);;	//????????????????????
			atta.setHfilesize(fileSize2);//????????????
		}else if("".equals(filename)&&"".equals(filename2)) {
			atta.setJfilename(filename1);//????????????????
			atta.setJfileurl(filepath1);;	//????????????????????????
			atta.setJfilesize(fileSize1);//????????????????
		}else if("".equals(filename1)&&"".equals(filename2)) {
			atta.setFilename(filename);	//????????????
			atta.setFilesize(fileSize);		//????????????
			atta.setFileurl(filepath);		//????????????????????
		}else {
			atta.setFilename(filename);	//????????????
			atta.setFilesize(fileSize);		//????????????
			atta.setFileurl(filepath);		//????????????????????
			atta.setHfilename(filename2);//????????????
			atta.setHfileurl(filepath2);;	//????????????????????
			atta.setHfilesize(fileSize2);//????????????
			atta.setJfilename(filename1);//????????????????
			atta.setJfileurl(filepath1);;	//????????????????????????
			atta.setJfilesize(fileSize1);//????????????????
			//atta.setWcbz(wcbz);//????
		}
		
		//??????????????
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	
	/**
	 *  ????????????
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addGeneralInspection(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String mainPath = AppConfig.HZB_PATH;
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//????????:    a0000:????id   treeId:??????id   uname:????
		String tjlx =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("tjlx"),"UTF-8"),"UTF-8");
		String xjqy =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("xjqy"),"UTF-8"),"UTF-8");
		
		String attachPath =  "GeneralInspection/" + user.getId()+"/"+uid;
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("????????");
			out = response.getWriter();
			//String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			String filePath1 = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			/*if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");*/
			String upload_file = mainPath+"/PublishUpload/"+attachPath + "/";// ????????
			try {
				//File file = new File(rootPath+upload_file);
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// ??????????????????????
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//????????????
			List<FileItem> fileItems = uploader.parseRequest(request); 
			CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			//????????????????????????????????
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
					// ????????????????????
					String path = fi.getName();// ????????
					//????????????????????????10M??????????????
					//CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;
					
					
					DecimalFormat df = new DecimalFormat("#.00"); 
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					
					if(fi.getSize() < 1048576){
						fileSize = "0" + fileSize;
					}
					
					//CommonQueryBS.systemOut(MAX_SIZE);
					if(fi.getSize() > MAX_SIZE){
						out.println("<script>odin.alert('??????"+path+"??????????10M????????????');</script>");
						return ;
					}
					try {
						//????????????????????
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if("".equals(filename) ||  filename == null){
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//??????????????????????
						filename = filename.substring(0,filename.lastIndexOf("."));
						
						filePath = upload_file + filename + "." + houzhui;
						//File file = new File(rootPath+filePath);
						String upload_file1 = "/PublishUpload/"+attachPath + "/";// ????????
						filePath1 = upload_file1 + filename + "." + houzhui;
						
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath1);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						
						
					} catch (Exception e) {
						try {
							throw new AppException("????????");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//????????????????????????
			for(int i=1;i<=rowlength;i++){
				String filepath = filePath_map.get("excelFile"+i);
				String fileName = filename_map.get("excelFile"+i+"_filename");
				String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
				saveGeneralInspection(uid,filepath,fileName,fileSize,tjlx,xjqy);
			}
			//out.println("<script>odin.alert('????????');</script>");
		} catch (Exception e) {
			out.println("<script>odin.alert('??????????');</script>");
			e.printStackTrace();
		}finally{
			if(out !=  null){
				out.close();
			}
		}
		
		
	}
	/**
	 *  ??????????????????????????????????
	 * @param title		????
	 * @param filepath	????????
	 * @param filename	????????
	 * @return
	 */
	public int saveGeneralInspection(String uid,String filepath,String filename,String fileSize,String tjlx,String xjqy){
		
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		//??????????????????
		String userid=SysManagerUtils.getUserId();
		//????????????
		GI atta = null;
		
		
		//????????????
		//????????
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new GI();
		//????????
		//String uid = UUID.randomUUID().toString();
		atta.setGiid(uid);
		atta.setFilename(filename);	//????????
		atta.setFilesize(fileSize);		//????????
		atta.setFileurl(filepath);		//????????????????
		atta.setXjqy(xjqy);		//????
		atta.setTjlx(tjlx);	//????????
		atta.setUserid(userid); 
		atta.setCreatedon(new Date());//????????
		
		
		//??????????????
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	/**
	 *  ??????????????????????????????????
	 * @param title		????
	 * @param filepath	????????
	 * @param filename	????????
	 * @return
	 */
	public int saveFolder(String uid,String title,String filepath,String filename,String fileSize,String secret){
		
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//????????????
		Policy atta = null;
		
		
		//????????????
		//????????
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new Policy();
		//????????
		//String uid = UUID.randomUUID().toString();
		atta.setId(uid);
		atta.setFileName(filename);		//????????
		atta.setFileSize(fileSize);		//????????
		atta.setFileUrl(filepath);		//????????????????
		atta.setTitle(title);			//????
		atta.setUpdateTime(createdate);		//????????
		atta.setA0000(user.getId()); 		//??????id
		atta.setUserName(user.getName()); 	//??????
		atta.setSecret(secret);
		
		
		//??????????????
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	 
	
	//????????????
	public void deletePolicyFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//????????????????id
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		//????????????????????????
		if(id!=null && !"".equals(id)){
			Policy policy = (Policy) session.get(Policy.class, id);
			if(policy!=null && !"".equals(policy)){
				session.delete(policy);
				session.flush();
			}
		}
		
		//????????
		try {
			String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			
			if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			
			filePath = rootPath + filePath;			//??????????????
			
			this.deleteFile(filePath);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	
	
	//????????
	public void downloadMeetingFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		//??????????????????????
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		//????????
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
		
		//String rootPath = "";
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		/*if ("\\".equals(File.separator)) {// windows??
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		if ("/".equals(File.separator)) {// linux??
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		rootPath = URLDecoder.decode(rootPath, "GBK");
		filePath = rootPath + filePath;			//??????????????
*/		
		
        /*????????*/
        File file = new File(filePath);
        /*????????????*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*????????????????0*/
            if (fileLength != 0) {
                /*??????????*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*??????????*/
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
	//????????????
		public void deleteMeetingFile(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException{
			
			HBSession session = HBUtil.getHBSession();
			String id = request.getParameter("id");				//????????????????id
			String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
			String hfilePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("hfilePath"),"UTF-8"),"UTF-8");
			String jfilePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("jfilePath"),"UTF-8"),"UTF-8");
			
			//????????????????????????
			if(id!=null && !"".equals(id)){
				DHXJ dhxj = (DHXJ) session.get(DHXJ.class, id);
				if(dhxj!=null && !"".equals(dhxj)){
					session.delete(dhxj);
					session.flush();
				}
			}
			
			//????????
			try {
				
				this.deleteFile(filePath);
				this.deleteFile(hfilePath);
				this.deleteFile(jfilePath);
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		
		
		//????????
		public void downloadPolicyFile(HttpServletRequest request,
				HttpServletResponse response) throws IOException{
			
			//??????????????????????
			String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
			//????????
			String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
			
			//String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			/*if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			filePath = rootPath + filePath;			//??????????????
	*/		
			
	        /*????????*/
	        File file = new File(filePath);
	        /*????????????*/
	        if (file.exists()) {
	            response.reset();
	            response.setHeader("pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("application/octet-stream;charset=GBK");
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
	           
	            int fileLength = (int) file.length();
	            response.setContentLength(fileLength);
	            /*????????????????0*/
	            if (fileLength != 0) {
	                /*??????????*/
	                InputStream inStream = new FileInputStream(file);
	                byte[] buf = new byte[4096];
	                /*??????????*/
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
	 * ????????????
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addNotice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		String uid = UUID.randomUUID().toString();
		
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//????
		String title =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8"),"UTF-8");
		//????
		String isfile =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("file"),"UTF-8"),"UTF-8");
		
		String text =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("text"),"UTF-8"),"UTF-8");
		
		String secret =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("secret"),"UTF-8"),"UTF-8");
		
		text = text.replace("uuiiooopphh", "&nbsp;");
		text = text.replace("hhhjjjkkklll", "&");
		
		
		//????????????????????????????
		saveNotice(uid,title,text,"","","",secret);
		
		//????????????????????????????????????????????
		if(isfile != null && !isfile.equals("")){
			
			String attachPath =  "notice/" + user.getId()+"/"+uid;
			
			response.setContentType("text/html;charset=GBK");
			request.setCharacterEncoding("GBK");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload uploader = new ServletFileUpload(factory);
			PrintWriter out = null;
			try {
				CommonQueryBS.systemOut("????????");
				out = response.getWriter();
				String rootPath = "";
				String filename = "";
				String houzhui = "";
				String classPath = getClass().getClassLoader().getResource("/").getPath();
				String filePath = "";
				CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
				if ("\\".equals(File.separator)) {// windows??
					rootPath = classPath.substring(1, classPath
							.indexOf("WEB-INF/classes"));
					rootPath = rootPath.replace("/", "\\");
				}
				if ("/".equals(File.separator)) {// linux??
					rootPath = classPath.substring(0, classPath
							.indexOf("WEB-INF/classes"));
					rootPath = rootPath.replace("\\", "/");
				}
				rootPath = URLDecoder.decode(rootPath, "GBK");
				String upload_file = "PublishUpload/"+attachPath + "/";// ????????
				try {
					File file = new File(rootPath+upload_file);
					if (!file.exists() && !file.isDirectory()) {// ??????????????????????
						file.mkdirs();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//????????????
				List<FileItem> fileItems = uploader.parseRequest(request); 
				CommonQueryBS.systemOut(fileItems.size()+"");
				java.util.Iterator<FileItem> iter = fileItems.iterator();
				//????????????????????????????????
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
						// ????????????????????
						String path = fi.getName();// ????????
						//????????????????????????10M??????????????
						//CommonQueryBS.systemOut(fi.getSize());
						long MAX_SIZE = 10 * 1024 * 1024;
						
						
						DecimalFormat df = new DecimalFormat("#.00"); 
						String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
						
						if(fi.getSize() < 1048576){
							fileSize = "0" + fileSize;
						}
						
						//CommonQueryBS.systemOut(MAX_SIZE);
						if(fi.getSize() > MAX_SIZE){
							out.println("<script>odin.alert('??????"+path+"??????????10M????????????');</script>");
							return ;
						}
						try {
							//????????????????????
							filename = path.substring(path.lastIndexOf("\\") + 1);
							if("".equals(filename) ||  filename == null){
								continue;
							}
							++rowlength;
							houzhui = filename.substring(filename.lastIndexOf(".") + 1,
									filename.length());
							//??????????????????????
							filename = filename.substring(0,filename.lastIndexOf("."));

							filePath = upload_file + filename + "." + houzhui;
							File file = new File(rootPath+filePath);
							
							fi.write(new File(rootPath+filePath));
							fi.getOutputStream().close();
							filePath_map.put(fi.getFieldName()+rowlength, filePath);
							filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
							fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
							
						} catch (Exception e) {
							try {
								throw new AppException("????????");
							} catch (AppException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
				//????????????????????????
				for(int i=1;i<=rowlength;i++){
					String filepath = filePath_map.get("excelFile"+i);
					String fileName = filename_map.get("excelFile"+i+"_filename");
					String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
					saveNoticeFile(uid,filepath,fileName,fileSize);
				}
				out.println("<script>odin.alert('????????');</script>");
			} catch (Exception e) {
				out.println("<script>odin.alert('??????????');</script>");
				e.printStackTrace();
			}finally{
				if(out !=  null){
					out.close();
				}
			}
		}
	}
	
	
	/**
	 *  ??????????????????????????????????????
	 * @param title		????
	 * @param filepath	????????
	 * @param filename	????????
	 * @return
	 */
	public int saveNoticeFile(String uid,String filepath,String filename,String fileSize){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		
		//????????
		NoticeFile atta = new NoticeFile();
		
		//????????
		String id = UUID.randomUUID().toString();
		atta.setId(id);
		atta.setNoticeId(uid);  		//????????id
		atta.setFileName(filename);		//????????
		atta.setFileSize(fileSize);		//????????
		atta.setFileUrl(filepath);		//????????????????
		
		//??????????????
		sess.saveOrUpdate(atta);	//????????????????
		sess.flush();
		ts.commit();
		return 0;
	}
	
	
	/**
	 *  ??????????????????????????????????
	 * @param title		????
	 * @param filepath	????????
	 * @param filename	????????
	 * @return
	 */
	public int saveNotice(String uid,String title,String text,String filepath,String filename,String fileSize,String secret){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//????????
		Notice atta = null;
		//????????????
		//????????
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new Notice();
		//????????
		//String uid = UUID.randomUUID().toString();
		atta.setId(uid);
	/*	atta.setFileName(filename);		//????????
		atta.setFileSize(fileSize);		//????????
		atta.setFileUrl(filepath);		//????????????????
*/		atta.setTitle(title);			//????
		atta.setText(text); 			//????
		atta.setUpdateTime(createdate);		//????????
		atta.setA0000(user.getId()); 		//??????id
		atta.setA0000Name(user.getName());  //??????????
		atta.setB0111(user.getOtherinfo()); //??????????????ID
		atta.setSecret(secret);
		
		//??????????????????????????????????????????????
		String sql = "select a.userid,a.username from smt_user a where a.USEFUL = '1' and a.OTHERINFO like '"+user.getOtherinfo()+"%' and exists (select 1 from COMPETENCE_USERDEPT b where a.USERID = b.USERID and b.b0111 like '"+user.getOtherinfo()+"%')";
		List userList = sess.createSQLQuery(sql).list();
		
		for (Object object : userList) {
			
			Object[] objlist= (Object[]) object;
			
			NoticeRecipent bean = new NoticeRecipent();
			
			String id = UUID.randomUUID().toString();
			bean.setId(id);				//????id
			bean.setNoticeId(uid);   	//????????id
			bean.setRecipientId(objlist[0].toString()); 			//??????id
			bean.setRecipientName(objlist[1].toString());			//??????????
			bean.setSee("0");  					//??????????
			
			sess.saveOrUpdate(bean);	
		}
		
		//??????????????
		sess.saveOrUpdate(atta);	//????????????
		
		sess.flush();
		ts.commit();
		return 0;
	}
	
	//????????????
	public void deleteNotice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//????????????????????id
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		//????????????????????????????????????????????????
		if(id!=null && !"".equals(id)){
			Notice notice = (Notice) session.get(Notice.class, id);
			if(notice!=null && !"".equals(notice)){
				session.delete(notice);
				//session.flush();
			}
			
			//??????????????????????
			String sql = "delete from NOTICERECIPIENT where NOTICEID = '"+id+"'";
			session.createSQLQuery(sql).executeUpdate();
			
			//????????????????
			String sql2 = "delete from NoticeFile where NOTICEID = '"+id+"'";
			session.createSQLQuery(sql2).executeUpdate();
			
			session.flush();
		}
		
		/*if(filePath != null && !filePath.equals("")){*/
			String attachPath =  "notice/" + user.getId()+"/"+id;
			String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			
			if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			
			filePath = rootPath + "PublishUpload/"+attachPath;			//????????????????????
			
			this.delFolder(filePath);
		/*}*/
	}
	
	
	//????????????????????????
	public void deleteNoticeFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//????????????????????????id
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		//????????????????????????????????????????????????
		if(id!=null && !"".equals(id)){
			NoticeFile noticeFile = (NoticeFile) session.get(NoticeFile.class, id);
			if(noticeFile!=null && !"".equals(noticeFile)){
				session.delete(noticeFile);
				//session.flush();
			}
			
			session.flush();
		}
		
		//????????
		try {
			String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			
			if ("\\".equals(File.separator)) {// windows??
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux??
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			
			filePath = rootPath + filePath;			//??????????????
			
			this.deleteFile(filePath);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void updateNotice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		String id = request.getParameter("id");		//????????????id
		
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//????
		String title =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8"),"UTF-8");
		//????
		String isfile =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("file"),"UTF-8"),"UTF-8");
		//????
		String text =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("text"),"UTF-8"),"UTF-8");
		//
		String secret =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("secret"),"UTF-8"),"UTF-8");
		//??????????????????				
		String fileUrl =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("fileUrl"),"UTF-8"),"UTF-8");
				
		//String text =  request.getParameter("myEditor");
		text = text.replace("uuiiooopphh", "&nbsp;");
		text = text.replace("hhhjjjkkklll", "&");
		
		//????????????????????????????????????????????
		
		//????????????????????????????
		updateNotice(id,title,text,"","","",1,secret);
				
		//????????????????????????????????????????????
		if(isfile != null && !isfile.equals("")){
			
			if(fileUrl != null && !fileUrl.equals("")){		//????????????????????????????????????????????
				//????????
				try {
					String rootPath = "";
					String classPath = getClass().getClassLoader().getResource("/").getPath();
					
					if ("\\".equals(File.separator)) {// windows??
						rootPath = classPath.substring(1, classPath
								.indexOf("WEB-INF/classes"));
						rootPath = rootPath.replace("/", "\\");
					}
					if ("/".equals(File.separator)) {// linux??
						rootPath = classPath.substring(0, classPath
								.indexOf("WEB-INF/classes"));
						rootPath = rootPath.replace("\\", "/");
					}
					rootPath = URLDecoder.decode(rootPath, "GBK");
					
					fileUrl = rootPath + fileUrl;			//??????????????
					
					this.deleteFile(fileUrl);
				} catch (AppException e) {
					e.printStackTrace();
				}
			}
			
			String attachPath =  "notice/" + user.getId()+"/"+id;
			
			response.setContentType("text/html;charset=GBK");
			request.setCharacterEncoding("GBK");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload uploader = new ServletFileUpload(factory);
			PrintWriter out = null;
			try {
				CommonQueryBS.systemOut("????????");
				out = response.getWriter();
				String rootPath = "";
				String filename = "";
				String houzhui = "";
				String classPath = getClass().getClassLoader().getResource("/").getPath();
				String filePath = "";
				CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
				if ("\\".equals(File.separator)) {// windows??
					rootPath = classPath.substring(1, classPath
							.indexOf("WEB-INF/classes"));
					rootPath = rootPath.replace("/", "\\");
				}
				if ("/".equals(File.separator)) {// linux??
					rootPath = classPath.substring(0, classPath
							.indexOf("WEB-INF/classes"));
					rootPath = rootPath.replace("\\", "/");
				}
				rootPath = URLDecoder.decode(rootPath, "GBK");
				String upload_file = "PublishUpload/"+attachPath + "/";// ????????
				try {
					File file = new File(rootPath+upload_file);
					if (!file.exists() && !file.isDirectory()) {// ??????????????????????
						file.mkdirs();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//????????????
				List<FileItem> fileItems = uploader.parseRequest(request); 
				CommonQueryBS.systemOut(fileItems.size()+"");
				java.util.Iterator<FileItem> iter = fileItems.iterator();
				//????????????????????????????????
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
						// ????????????????????
						String path = fi.getName();// ????????
						//????????????????????????10M??????????????
						//CommonQueryBS.systemOut(fi.getSize());
						long MAX_SIZE = 10 * 1024 * 1024;
						
						
						DecimalFormat df = new DecimalFormat("#.00"); 
						String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
						
						if(fi.getSize() < 1048576){
							fileSize = "0" + fileSize;
						}
						
						//CommonQueryBS.systemOut(MAX_SIZE);
						if(fi.getSize() > MAX_SIZE){
							out.println("<script>odin.alert('??????"+path+"??????????10M????????????');</script>");
							return ;
						}
						try {
							//????????????????????
							filename = path.substring(path.lastIndexOf("\\") + 1);
							if("".equals(filename) ||  filename == null){
								continue;
							}
							++rowlength;
							houzhui = filename.substring(filename.lastIndexOf(".") + 1,
									filename.length());
							//??????????????????????
							filename = filename.substring(0,filename.lastIndexOf("."));

							filePath = upload_file + filename + "." + houzhui;
							File file = new File(rootPath+filePath);
							
							fi.write(new File(rootPath+filePath));
							fi.getOutputStream().close();
							filePath_map.put(fi.getFieldName()+rowlength, filePath);
							filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
							fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
							
						} catch (Exception e) {
							try {
								throw new AppException("????????");
							} catch (AppException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
				//????????????????????????
				for(int i=1;i<=rowlength;i++){
					String filepath = filePath_map.get("excelFile"+i);
					String fileName = filename_map.get("excelFile"+i+"_filename");
					String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
					saveNoticeFile(id,filepath,fileName,fileSize);
				}
				out.println("<script>odin.alert('????????');</script>");
			} catch (Exception e) {
				out.println("<script>odin.alert('??????????');</script>");
				e.printStackTrace();
			}finally{
				if(out !=  null){
					out.close();
				}
			}
		}
		
	}
	
	
	/**
	 *  ??????????????????????????????????
	 * @param id		????????id
	 * @param title		????
	 * @param filepath	????????
	 * @param filename	????????
	 * @return
	 */
	public int updateNotice(String id,String title,String text,String filepath,String filename,String fileSize,int isfile,String secret){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//????????????
		//????????
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		Notice atta = (Notice)sess.get(Notice.class, id);
		
		if(isfile == 2){		//??????????????????????????
			atta.setFileName(filename);		//????????
			atta.setFileSize(fileSize);		//????????
			atta.setFileUrl(filepath);		//????????????????
		}
		
		atta.setTitle(title);			//????
		atta.setText(text); 			//????
		atta.setSecret(secret); 			//????
		atta.setUpdateTime(createdate);		//????????
		atta.setA0000(user.getId()); 		//??????id
		atta.setA0000Name(user.getName());  //??????????
		
		//??????????????
		sess.saveOrUpdate(atta);	//????????????
		sess.flush();
		ts.commit();
		return 0;
	}
	
	
	//????????????????????????
	public void isOut(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException{
			//??????????????????
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			
			HBSession session = HBUtil.getHBSession();
			
			applog.createLogNewF("????????","","1111111","????");
			
		}
		
	//????????
		public void downloadorgFile(HttpServletRequest request,
				HttpServletResponse response) throws IOException, SQLException{
			
			//??????????????????????
			String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"),"UTF-8");
			String table = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("table"),"UTF-8"),"UTF-8");
			
			//????????????????????????????????
			HBSession sess = HBUtil.getHBSession();
			//String sqlFlie = "select id,fileName,fileurl from "+table+" where id ='"+id+"'";
			//YearCheckFile yearcheckfile = (YearCheckFile) sess.createSQLQuery(sqlFlie).setResultTransformer(Transformers.aliasToBean(YearCheckFile.class)).uniqueResult();
			//YearCheckFile yearcheckfile = (YearCheckFile) sess.createSQLQuery(sqlFlie);
			YearCheckFile yearcheckfile= (YearCheckFile) sess.createQuery("from YearCheckFile where id = '"+id+"'").uniqueResult();
			
			//String filename = yearcheckfile.getFileName();
			String filePath = yearcheckfile.getFileUrl();
			
			//????????
			String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
			/*????????*/
			File file = new File(filePath);
			/*????????????*/
			if (file.exists()) {
				response.reset();
				response.setHeader("pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("application/octet-stream;charset=GBK");
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
					
				int fileLength = (int) file.length();
				response.setContentLength(fileLength);
				/*????????????????0*/
				if (fileLength != 0) {
					/*??????????*/
					InputStream inStream = new FileInputStream(file);
					byte[] buf = new byte[4096];
					/*??????????*/
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
	
}
