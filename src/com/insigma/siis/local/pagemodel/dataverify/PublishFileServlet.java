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
 * 信息发布Servlet
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
		
		if("addPolicy".equals(method)){						//政策法规新增
			addPolicy(request, response);
		}else if("addGeneralInspection".equals(method)){						//换届考察新增
			addGeneralInspection(request, response);
		}else if("addMeetingElection".equals(method)){						//选举大会新增
			addMeetingElection(request, response);
		}else if("deleteMeetingFile".equals(method)){						//选举大会删除
			deleteMeetingFile(request, response);
		}else if("deletePolicyFile".equals(method)){		//政策法规删除
			deletePolicyFile(request, response);
		}else if("downloadPolicyFile".equals(method)){		//政策法规文件下载
			downloadPolicyFile(request, response);
		}else if("addNotice".equals(method)){		//通知公告增加
			addNotice(request, response);
		}else if("deleteNotice".equals(method)){		//通知公告删除
			deleteNotice(request, response);
		}else if("updateNotice".equals(method)){		//通知公告修改
			updateNotice(request, response);
		}else if("deleteNoticeFile".equals(method)){		//通知公告附件删除
			deleteNoticeFile(request, response);
		}else if("customPageSize".equals(method)){		//人员信息页面设置每页显示条数，存入session
			
			String pageSize = request.getParameter("pageSize");		//通知公告主键id		
			HttpSession session=request.getSession(); 
			session.setAttribute("pageSize",pageSize); 
			
		}else if("expPBCX".equals(method)){//  导出平板查询数据
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
		}else if("downloadFile".equals(method)){		//政策法规文件下载
			try {
				downloadFile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("playFile".equals(method)){		//政策法规文件下载
			try {
				playFile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("downloadshanghuicailiao".equals(method)){//  导出上会材料
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
		//获得传来的文件相对路径
		String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("checkregfileid"),"UTF-8"),"UTF-8");
		
		//查询出文件的名称、文件二进制信息
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
		if(uuid!=null&&!"".equals(uuid)){//上会系统数据包
			pathInfo = (String[])request.getSession().getAttribute(uuid);
			//request.getSession().removeAttribute(uuid);
		}else if(SPid!=null&&!"".equals(SPid)){//上会系统数据包
			pathInfo = getpathSP(request,response);
			//request.getSession().removeAttribute(uuid);
		}else if(checkregfileid!=null&&!"".equals(checkregfileid)){//检查管理
			pathInfo = getpathCheckRegFile(request,response);
			//request.getSession().removeAttribute(uuid);
		}else{//附件下载
			pathInfo = getpath(request,response);
		}
		path = pathInfo[0];
		String filename = pathInfo[1];
		
        /*读取文件*/
       //File file = new File("C:\\Users\\LENOVO\\Desktop\\436475f1e75cc5266857e85dc1de0063.jpg");
        /*如果文件存在*/
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
            /*如果文件长度大于0*/
            if (file.isFile()) {
            	// 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
            	
                /*创建输出流*/
                ServletOutputStream servletOS = response.getOutputStream();

                servletOS.write(buffer);
                servletOS.flush();
                servletOS.close();
            }
        }
	}
	//下载文件
	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException{
		
		String uuid = request.getParameter("uuid");
		String SPid = request.getParameter("SPid");
		String checkregfileid = request.getParameter("checkregfileid");
		String path = "";
		String[] pathInfo ;
		if(uuid!=null&&!"".equals(uuid)){//上会系统数据包
			pathInfo = (String[])request.getSession().getAttribute(uuid);
			//request.getSession().removeAttribute(uuid);
		}else if(SPid!=null&&!"".equals(SPid)){//上会系统数据包
			pathInfo = getpathSP(request,response);
			//request.getSession().removeAttribute(uuid);
		}else if(checkregfileid!=null&&!"".equals(checkregfileid)){//检查管理
			pathInfo = getpathCheckRegFile(request,response);
			//request.getSession().removeAttribute(uuid);
		}else{//附件下载
			pathInfo = getpath(request,response);
		}
		path = pathInfo[0];
		String filename = pathInfo[1];
		
        /*读取文件*/
       //File file = new File("C:\\Users\\LENOVO\\Desktop\\436475f1e75cc5266857e85dc1de0063.jpg");
        /*如果文件存在*/
        if (filename != null && !filename.equals("")) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("charset=GBK");
			
           
			if (filename.startsWith("gbynrmb")) {
				filename = "近期动议干部议题初步考虑一览表"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbswsjrmb")) {
				filename = "提请省委常委会讨论决定任免干部基本情况一览表"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbcwhrmb")) {
				filename = "提请省委书记专题研究任免干部基本情况一览表"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbynrmbgd")) {
				filename = "近期动议干部议题初步考虑一览表归档"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbswsjrmbgd")) {
				filename = "提请省委常委会讨论决定任免干部基本情况一览表归档"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			}else if (filename.startsWith("gbcwhrmbgd")) {
				filename = "提请省委书记专题研究任免干部基本情况一览表归档"+(new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date())+".xlsx";
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
            /*如果文件长度大于0*/
            if (file.isFile()) {
            	// 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
            	
                /*创建输出流*/
                ServletOutputStream servletOS = response.getOutputStream();

                servletOS.write(buffer);
                servletOS.flush();
                servletOS.close();
            }
        }
       
	}

	private String[] getpath(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//获得传来的文件相对路径
		String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"),"UTF-8");
		
		//查询出文件的名称、文件二进制信息
		HBSession sess = HBUtil.getHBSession();
		JsAtt fileVO = (JsAtt) sess.get(JsAtt.class, id);
		
		String filename = fileVO.getJsa04();
		
		return new String[]{QCJSPageModel.disk+fileVO.getJsa07()+fileVO.getJsa00(),filename};
	}
	
	private String[] getpathSP(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//获得传来的文件相对路径
		String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("SPid"),"UTF-8"),"UTF-8");
		
		//查询出文件的名称、文件二进制信息
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

	

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public void delFolder(String folderPath) {
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
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	
	/**
	 *  政策法规新增
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addPolicy(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String mainPath = AppConfig.HZB_PATH;
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//获取参数:    a0000:人员id   treeId:文件夹id   uname:名称
		String title =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8"),"UTF-8");
		String secret =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("secret"),"UTF-8"),"UTF-8");
		
		String attachPath =  "policy/" + user.getId()+"/"+uid;
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			//String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			/*if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");*/
			String upload_file = mainPath+"/PublishUpload/"+attachPath + "/";// 上传路径
			try {
				//File file = new File(rootPath+upload_file);
				File file = new File(upload_file);
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

						filePath = upload_file + filename + "." + houzhui;
						//File file = new File(rootPath+filePath);
						
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						
						
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
				String filepath = filePath_map.get("excelFile"+i);
				String fileName = filename_map.get("excelFile"+i+"_filename");
				String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
				saveFolder(uid,title,filepath,fileName,fileSize,secret);
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
	 *  政策法规新增
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addMeetingElection(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String mainPath = AppConfig.HZB_PATH;
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//获取参数:    a0000:人员id   treeId:文件夹id   uname:名称
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
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			//String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			String filePath1 = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			/*if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");*/
			String upload_file = mainPath+"/PublishUpload/"+attachPath + "/";// 上传路径
			try {
				//File file = new File(rootPath+upload_file);
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取表单信息
			List<FileItem> fileItems = uploaderl.parseRequest(request); 
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

						filePath = upload_file + filename + "." + houzhui;
						//File file = new File(rootPath+filePath);
						String upload_file1 = "/PublishUpload/"+attachPath + "/";// 上传路径
						filePath1 = upload_file1 + filename + "." + houzhui;
						
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath1);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						
						
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
				
			//out.println("<script>odin.alert('上传成功');</script>");
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
	 *  将上传的政策法规信息保存在数据库中
	 * @param title		标题
	 * @param filepath	文件地址
	 * @param filename	文件名称
	 * @return
	 */
	public int saveMeetingElection(String dhxjid,String filepath,String filename,String fileSize,String filepath1,String filename1,String fileSize1,String filepath2,String filename2,String fileSize2){
		
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//创建附件对象
		DHXJ atta = null;
		
		
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new DHXJ();
		//获取主键
		//String uid = UUID.randomUUID().toString();
		atta = (DHXJ) HBUtil.getHBSession().get(DHXJ.class, dhxjid);
		if("".equals(filename)&&!"".equals(filename1)&&!"".equals(filename2)) {
			atta.setHfilename(filename2);//会议文件名称
			atta.setHfileurl(filepath2);;	//会议文件存储相对路径
			atta.setHfilesize(fileSize2);//会议文件大小
			atta.setJfilename(filename1);//会议结果文件名称
			atta.setJfileurl(filepath1);;	//会议结果文件存储相对路径
			atta.setJfilesize(fileSize1);//会议结果文件大小
		}else if("".equals(filename1)&&!"".equals(filename)&&!"".equals(filename2)) {
			atta.setFilename(filename);	//选举文件名称
			atta.setFilesize(fileSize);		//选举文件大小
			atta.setFileurl(filepath);		//选举文件存储相对路径
			atta.setHfilename(filename2);//会议文件名称
			atta.setHfileurl(filepath2);;	//会议文件存储相对路径
			atta.setHfilesize(fileSize2);//会议文件大小
		}else if("".equals(filename2)&&!"".equals(filename1)&&!"".equals(filename)) {
			atta.setFilename(filename);	//选举文件名称
			atta.setFilesize(fileSize);		//选举文件大小
			atta.setFileurl(filepath);		//选举文件存储相对路径
			atta.setJfilename(filename1);//会议结果文件名称
			atta.setJfileurl(filepath1);;	//会议结果文件存储相对路径
			atta.setJfilesize(fileSize1);//会议结果文件大小
		}else if("".equals(filename)&&"".equals(filename1)) {
			atta.setHfilename(filename2);//会议文件名称
			atta.setHfileurl(filepath2);;	//会议文件存储相对路径
			atta.setHfilesize(fileSize2);//会议文件大小
		}else if("".equals(filename)&&"".equals(filename2)) {
			atta.setJfilename(filename1);//会议结果文件名称
			atta.setJfileurl(filepath1);;	//会议结果文件存储相对路径
			atta.setJfilesize(fileSize1);//会议结果文件大小
		}else if("".equals(filename1)&&"".equals(filename2)) {
			atta.setFilename(filename);	//选举文件名称
			atta.setFilesize(fileSize);		//选举文件大小
			atta.setFileurl(filepath);		//选举文件存储相对路径
		}else {
			atta.setFilename(filename);	//选举文件名称
			atta.setFilesize(fileSize);		//选举文件大小
			atta.setFileurl(filepath);		//选举文件存储相对路径
			atta.setHfilename(filename2);//会议文件名称
			atta.setHfileurl(filepath2);;	//会议文件存储相对路径
			atta.setHfilesize(fileSize2);//会议文件大小
			atta.setJfilename(filename1);//会议结果文件名称
			atta.setJfileurl(filepath1);;	//会议结果文件存储相对路径
			atta.setJfilesize(fileSize1);//会议结果文件大小
			//atta.setWcbz(wcbz);//结果
		}
		
		//执行数据库操作
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	
	/**
	 *  政策法规新增
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addGeneralInspection(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String mainPath = AppConfig.HZB_PATH;
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//获取参数:    a0000:人员id   treeId:文件夹id   uname:名称
		String tjlx =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("tjlx"),"UTF-8"),"UTF-8");
		String xjqy =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("xjqy"),"UTF-8"),"UTF-8");
		
		String attachPath =  "GeneralInspection/" + user.getId()+"/"+uid;
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			//String rootPath = "";
			String filename = "";
			String houzhui = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			String filePath = "";
			String filePath1 = "";
			CommonQueryBS.systemOut("START---------" +DateUtil.getTime());
			/*if ("\\".equals(File.separator)) {// windows下
				rootPath = classPath.substring(1, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {// linux下
				rootPath = classPath.substring(0, classPath
						.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");*/
			String upload_file = mainPath+"/PublishUpload/"+attachPath + "/";// 上传路径
			try {
				//File file = new File(rootPath+upload_file);
				File file = new File(upload_file);
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
						
						filePath = upload_file + filename + "." + houzhui;
						//File file = new File(rootPath+filePath);
						String upload_file1 = "/PublishUpload/"+attachPath + "/";// 上传路径
						filePath1 = upload_file1 + filename + "." + houzhui;
						
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName()+rowlength, filePath1);
						filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
						
						
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
				String filepath = filePath_map.get("excelFile"+i);
				String fileName = filename_map.get("excelFile"+i+"_filename");
				String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
				saveGeneralInspection(uid,filepath,fileName,fileSize,tjlx,xjqy);
			}
			//out.println("<script>odin.alert('上传成功');</script>");
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
	 *  将上传的政策法规信息保存在数据库中
	 * @param title		标题
	 * @param filepath	文件地址
	 * @param filename	文件名称
	 * @return
	 */
	public int saveGeneralInspection(String uid,String filepath,String filename,String fileSize,String tjlx,String xjqy){
		
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		String userid=SysManagerUtils.getUserId();
		//创建附件对象
		GI atta = null;
		
		
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new GI();
		//获取主键
		//String uid = UUID.randomUUID().toString();
		atta.setGiid(uid);
		atta.setFilename(filename);	//文件名称
		atta.setFilesize(fileSize);		//文件大小
		atta.setFileurl(filepath);		//文件存储相对路径
		atta.setXjqy(xjqy);		//区域
		atta.setTjlx(tjlx);	//会议类型
		atta.setUserid(userid); 
		atta.setCreatedon(new Date());//更新时间
		
		
		//执行数据库操作
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	/**
	 *  将上传的政策法规信息保存在数据库中
	 * @param title		标题
	 * @param filepath	文件地址
	 * @param filename	文件名称
	 * @return
	 */
	public int saveFolder(String uid,String title,String filepath,String filename,String fileSize,String secret){
		
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//创建附件对象
		Policy atta = null;
		
		
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new Policy();
		//获取主键
		//String uid = UUID.randomUUID().toString();
		atta.setId(uid);
		atta.setFileName(filename);		//文件名称
		atta.setFileSize(fileSize);		//文件大小
		atta.setFileUrl(filepath);		//文件存储相对路径
		atta.setTitle(title);			//标题
		atta.setUpdateTime(createdate);		//更新时间
		atta.setA0000(user.getId()); 		//操作人id
		atta.setUserName(user.getName()); 	//操作人
		atta.setSecret(secret);
		
		
		//执行数据库操作
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(atta);
		sess.flush();
		ts.commit();
		
		return 0;
	}
	 
	
	//删除政策法规
	public void deletePolicyFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//获得要删除的文件id
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		//删除掉表中的政策法规数据
		if(id!=null && !"".equals(id)){
			Policy policy = (Policy) session.get(Policy.class, id);
			if(policy!=null && !"".equals(policy)){
				session.delete(policy);
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
			e.printStackTrace();
		}
	}
	
	
	//下载文件
	public void downloadMeetingFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		//获得传来的文件相对路径
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		//文件名称
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
		
		//String rootPath = "";
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		/*if ("\\".equals(File.separator)) {// windows下
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
*/		
		
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
	//删除大会选举
		public void deleteMeetingFile(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException{
			
			HBSession session = HBUtil.getHBSession();
			String id = request.getParameter("id");				//获得要删除的文件id
			String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
			String hfilePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("hfilePath"),"UTF-8"),"UTF-8");
			String jfilePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("jfilePath"),"UTF-8"),"UTF-8");
			
			//删除掉表中的大会选举数据
			if(id!=null && !"".equals(id)){
				DHXJ dhxj = (DHXJ) session.get(DHXJ.class, id);
				if(dhxj!=null && !"".equals(dhxj)){
					session.delete(dhxj);
					session.flush();
				}
			}
			
			//删除文件
			try {
				
				this.deleteFile(filePath);
				this.deleteFile(hfilePath);
				this.deleteFile(jfilePath);
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		
		
		//下载文件
		public void downloadPolicyFile(HttpServletRequest request,
				HttpServletResponse response) throws IOException{
			
			//获得传来的文件相对路径
			String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
			//文件名称
			String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
			
			//String rootPath = "";
			String classPath = getClass().getClassLoader().getResource("/").getPath();
			/*if ("\\".equals(File.separator)) {// windows下
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
	*/		
			
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
	
	/**
	 * 通知公告新增
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addNotice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		String uid = UUID.randomUUID().toString();
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//标题
		String title =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8"),"UTF-8");
		//文件
		String isfile =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("file"),"UTF-8"),"UTF-8");
		
		String text =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("text"),"UTF-8"),"UTF-8");
		
		String secret =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("secret"),"UTF-8"),"UTF-8");
		
		text = text.replace("uuiiooopphh", "&nbsp;");
		text = text.replace("hhhjjjkkklll", "&");
		
		
		//保存通知公告信息、接收人信息
		saveNotice(uid,title,text,"","","",secret);
		
		//判断是否上传了附件，没有上传则不进行文件处理
		if(isfile != null && !isfile.equals("")){
			
			String attachPath =  "notice/" + user.getId()+"/"+uid;
			
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
				String upload_file = "PublishUpload/"+attachPath + "/";// 上传路径
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

							filePath = upload_file + filename + "." + houzhui;
							File file = new File(rootPath+filePath);
							
							fi.write(new File(rootPath+filePath));
							fi.getOutputStream().close();
							filePath_map.put(fi.getFieldName()+rowlength, filePath);
							filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
							fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
							
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
					String filepath = filePath_map.get("excelFile"+i);
					String fileName = filename_map.get("excelFile"+i+"_filename");
					String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
					saveNoticeFile(uid,filepath,fileName,fileSize);
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
	}
	
	
	/**
	 *  将上传的通知公告附件信息保存在数据库中
	 * @param title		标题
	 * @param filepath	文件地址
	 * @param filename	文件名称
	 * @return
	 */
	public int saveNoticeFile(String uid,String filepath,String filename,String fileSize){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		
		//创建对象
		NoticeFile atta = new NoticeFile();
		
		//获取主键
		String id = UUID.randomUUID().toString();
		atta.setId(id);
		atta.setNoticeId(uid);  		//通知公告id
		atta.setFileName(filename);		//文件名称
		atta.setFileSize(fileSize);		//文件大小
		atta.setFileUrl(filepath);		//文件存储相对路径
		
		//执行数据库操作
		sess.saveOrUpdate(atta);	//通知公告文件信息
		sess.flush();
		ts.commit();
		return 0;
	}
	
	
	/**
	 *  将上传的通知公告信息保存在数据库中
	 * @param title		标题
	 * @param filepath	文件地址
	 * @param filename	文件名称
	 * @return
	 */
	public int saveNotice(String uid,String title,String text,String filepath,String filename,String fileSize,String secret){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//创建对象
		Notice atta = null;
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		atta = new Notice();
		//获取主键
		//String uid = UUID.randomUUID().toString();
		atta.setId(uid);
	/*	atta.setFileName(filename);		//文件名称
		atta.setFileSize(fileSize);		//文件大小
		atta.setFileUrl(filepath);		//文件存储相对路径
*/		atta.setTitle(title);			//标题
		atta.setText(text); 			//正文
		atta.setUpdateTime(createdate);		//更新时间
		atta.setA0000(user.getId()); 		//操作人id
		atta.setA0000Name(user.getName());  //创建人名称
		atta.setB0111(user.getOtherinfo()); //创建人所属机构ID
		atta.setSecret(secret);
		
		//处理通知公告接收人数据（默认通知公告发给下级）
		String sql = "select a.userid,a.username from smt_user a where a.USEFUL = '1' and a.OTHERINFO like '"+user.getOtherinfo()+"%' and exists (select 1 from COMPETENCE_USERDEPT b where a.USERID = b.USERID and b.b0111 like '"+user.getOtherinfo()+"%')";
		List userList = sess.createSQLQuery(sql).list();
		
		for (Object object : userList) {
			
			Object[] objlist= (Object[]) object;
			
			NoticeRecipent bean = new NoticeRecipent();
			
			String id = UUID.randomUUID().toString();
			bean.setId(id);				//主键id
			bean.setNoticeId(uid);   	//通知公告id
			bean.setRecipientId(objlist[0].toString()); 			//接收人id
			bean.setRecipientName(objlist[1].toString());			//接收人名称
			bean.setSee("0");  					//是否已查看
			
			sess.saveOrUpdate(bean);	
		}
		
		//执行数据库操作
		sess.saveOrUpdate(atta);	//通知公告保存
		
		sess.flush();
		ts.commit();
		return 0;
	}
	
	//删除通知公告
	public void deleteNotice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//获得要删除的通知公告id
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		//删除掉表中的通知公告数据，以及通知公告接收人数据
		if(id!=null && !"".equals(id)){
			Notice notice = (Notice) session.get(Notice.class, id);
			if(notice!=null && !"".equals(notice)){
				session.delete(notice);
				//session.flush();
			}
			
			//删除通知公告接收人数据
			String sql = "delete from NOTICERECIPIENT where NOTICEID = '"+id+"'";
			session.createSQLQuery(sql).executeUpdate();
			
			//删除通知公告附件
			String sql2 = "delete from NoticeFile where NOTICEID = '"+id+"'";
			session.createSQLQuery(sql2).executeUpdate();
			
			session.flush();
		}
		
		/*if(filePath != null && !filePath.equals("")){*/
			String attachPath =  "notice/" + user.getId()+"/"+id;
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
			
			filePath = rootPath + "PublishUpload/"+attachPath;			//拼接文件夹出绝对路径
			
			this.delFolder(filePath);
		/*}*/
	}
	
	
	//删除通知公告附件（单个）
	public void deleteNoticeFile(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		HBSession session = HBUtil.getHBSession();
		String id = request.getParameter("id");				//获得要删除的通知公告附件id
		String filePath = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8"),"UTF-8");
		
		//删除掉表中的通知公告数据，以及通知公告接收人数据
		if(id!=null && !"".equals(id)){
			NoticeFile noticeFile = (NoticeFile) session.get(NoticeFile.class, id);
			if(noticeFile!=null && !"".equals(noticeFile)){
				session.delete(noticeFile);
				//session.flush();
			}
			
			session.flush();
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
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通知公告修改
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void updateNotice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		
		String id = request.getParameter("id");		//通知公告主键id
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//标题
		String title =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8"),"UTF-8");
		//文件
		String isfile =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("file"),"UTF-8"),"UTF-8");
		//正文
		String text =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("text"),"UTF-8"),"UTF-8");
		//
		String secret =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("secret"),"UTF-8"),"UTF-8");
		//旧文件存储相对路径				
		String fileUrl =  java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("fileUrl"),"UTF-8"),"UTF-8");
				
		//String text =  request.getParameter("myEditor");
		text = text.replace("uuiiooopphh", "&nbsp;");
		text = text.replace("hhhjjjkkklll", "&");
		
		//判断是否上传了附件，没有上传则不进行文件处理
		
		//保存通知公告信息、接收人信息
		updateNotice(id,title,text,"","","",1,secret);
				
		//判断是否上传了附件，没有上传则不进行文件处理
		if(isfile != null && !isfile.equals("")){
			
			if(fileUrl != null && !fileUrl.equals("")){		//上传了新文件，并且旧文件存在，则先删除旧文件
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
					
					fileUrl = rootPath + fileUrl;			//拼接出绝对路径
					
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
				String upload_file = "PublishUpload/"+attachPath + "/";// 上传路径
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

							filePath = upload_file + filename + "." + houzhui;
							File file = new File(rootPath+filePath);
							
							fi.write(new File(rootPath+filePath));
							fi.getOutputStream().close();
							filePath_map.put(fi.getFieldName()+rowlength, filePath);
							filename_map.put(fi.getFieldName()+rowlength +"_filename", filename + "." + houzhui);
							fileSize_map.put(fi.getFieldName()+rowlength +"_fileSize", fileSize);
							
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
					String filepath = filePath_map.get("excelFile"+i);
					String fileName = filename_map.get("excelFile"+i+"_filename");
					String fileSize = fileSize_map.get("excelFile"+i+"_fileSize");
					saveNoticeFile(id,filepath,fileName,fileSize);
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
		
	}
	
	
	/**
	 *  将上传的通知公告信息保存在数据库中
	 * @param id		通知公告id
	 * @param title		标题
	 * @param filepath	文件地址
	 * @param filename	文件名称
	 * @return
	 */
	public int updateNotice(String id,String title,String text,String filepath,String filename,String fileSize,int isfile,String secret){
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		//获取当前时间
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		Notice atta = (Notice)sess.get(Notice.class, id);
		
		if(isfile == 2){		//当有附件上传时更新文件信息
			atta.setFileName(filename);		//文件名称
			atta.setFileSize(fileSize);		//文件大小
			atta.setFileUrl(filepath);		//文件存储相对路径
		}
		
		atta.setTitle(title);			//标题
		atta.setText(text); 			//正文
		atta.setSecret(secret); 			//等级
		atta.setUpdateTime(createdate);		//更新时间
		atta.setA0000(user.getId()); 		//操作人id
		atta.setA0000Name(user.getName());  //创建人名称
		
		//执行数据库操作
		sess.saveOrUpdate(atta);	//通知公告保存
		sess.flush();
		ts.commit();
		return 0;
	}
	
	
	//删除通知公告附件（单个）
	public void isOut(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException{
			//获取登录用户的信息
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			
			HBSession session = HBUtil.getHBSession();
			
			applog.createLogNewF("系统退出","","1111111","系统");
			
		}
		
	//下载文件
		public void downloadorgFile(HttpServletRequest request,
				HttpServletResponse response) throws IOException, SQLException{
			
			//获得传来的文件相对路径
			String id = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"),"UTF-8");
			String table = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("table"),"UTF-8"),"UTF-8");
			
			//查询出文件的名称、文件二进制信息
			HBSession sess = HBUtil.getHBSession();
			//String sqlFlie = "select id,fileName,fileurl from "+table+" where id ='"+id+"'";
			//YearCheckFile yearcheckfile = (YearCheckFile) sess.createSQLQuery(sqlFlie).setResultTransformer(Transformers.aliasToBean(YearCheckFile.class)).uniqueResult();
			//YearCheckFile yearcheckfile = (YearCheckFile) sess.createSQLQuery(sqlFlie);
			YearCheckFile yearcheckfile= (YearCheckFile) sess.createQuery("from YearCheckFile where id = '"+id+"'").uniqueResult();
			
			//String filename = yearcheckfile.getFileName();
			String filePath = yearcheckfile.getFileUrl();
			
			//文件名称
			String filename = filePath.substring(filePath.lastIndexOf("/")+1);		
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
	
}
