package com.insigma.siis.local.pagemodel.zj.JUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UploadServlet
 */
public class JUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("downloadFile".equals(method)) { // 文件下载
			downloadFile(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 文件存放的目录
		/*
		 * File tempDirPath =new
		 * File(request.getSession().getServletContext().getRealPath("/")+"\\upload\\");
		 * if(!tempDirPath.exists()){ tempDirPath.mkdirs(); }
		 */
		request.setCharacterEncoding("utf-8");
		String uploadType = request.getParameter("uploadType");
		String file_pk = request.getParameter("file_pk");

		if ("DELETE".equals(uploadType)) {
			String reStr = "";
			try {
				Class clazz = Class.forName("com.insigma.siis.local.pagemodel."
						+ request.getParameter("pageModel").replace("pages.", "") + "PageModel");
				Constructor<?> cons = clazz.getConstructor();
				JUpload obj = (JUpload) cons.newInstance();
				reStr = obj.deleteFile(file_pk);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = null;
			try {
				out = encodehead(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 这个地方不能少，否则前台得不到上传的结果
			out.write(reStr);
			out.close();
			return;
		}

		// CurrentUser cu = SysUtil.getCacheCurrentUser();

		// 创建磁盘文件工厂
		DiskFileItemFactory fac = new DiskFileItemFactory();
		// 创建servlet文件上传组件
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("ISO8859_1");
		// 文件列表
		List<FileItem> fileList = null;
		List<FileItem> fileList_re = new ArrayList<FileItem>();
		Map<String, String> pv = new HashMap<String, String>();
		// 解析request从而得到前台传过来的文件
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			ex.printStackTrace();
			return;
		}
		// 保存后的文件名
		// String imageName = null;
		// 便利从前台得到的文件列表
		Iterator<FileItem> it = fileList.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();
			// 如果不是普通表单域，当做文件域来处理
			if (!item.isFormField()) {
				fileList_re.add(item);
				// System.out.println(item.getName());
				// System.out.println(item.getFieldName());
			} else if (item.isFormField()) {
				// 此处为表单参数
				pv.put(new String(item.getFieldName().getBytes("ISO8859_1"), "utf-8"),
						new String(item.getString().getBytes("ISO8859_1"), "utf-8"));

			}
		}
		String reStr = "";
		// 通过反射调用pagemode 传递数据
		/// gwy/src/com/insigma/siis/local/pagemodel/register/company/RegisterPageModel.java
		uploadType = pv.get("uploadType");
		try {
			Class clazz = Class.forName(
					"com.insigma.siis.local.pagemodel." + pv.get("pageModel").replace("pages.", "") + "PageModel");
			Constructor<?> cons = clazz.getConstructor();
			JUpload obj = (JUpload) cons.newInstance();
			if ("UPLOAD".equals(uploadType)) {
				Map<String, String> map = obj.getFiles(fileList_re, pv);
				JSONObject jsonObject = JSONObject.fromObject(map);
				reStr = jsonObject.toString();
			} else {

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = null;
		try {
			out = encodehead(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 这个地方不能少，否则前台得不到上传的结果
		out.write(reStr);
		out.close();
	}

	/**
	 * Ajax辅助方法 获取 PrintWriter
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 *             request.setCharacterEncoding("utf-8");
	 *             response.setContentType("text/html; charset=utf-8");
	 */

	private PrintWriter encodehead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		return response.getWriter();
	}

	// 下载文件
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获得传来的文件相对路径
		String filePath = java.net.URLDecoder
				.decode(java.net.URLDecoder.decode(request.getParameter("filepath"), "UTF-8"), "UTF-8");
		// 文件名称
		String filename = java.net.URLDecoder
				.decode(java.net.URLDecoder.decode(request.getParameter("filename"), "UTF-8"), "UTF-8");

		String rootPath = "";
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		if ("\\".equals(File.separator)) {// windows下
			rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		if ("/".equals(File.separator)) {// linux下
			rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		rootPath = URLDecoder.decode(rootPath, "GBK");
		filePath = rootPath + filePath; // 拼接出绝对路径

		/* 读取文件 */
		File file = new File(filePath);
		/* 如果文件存在 */
		if (file.exists()) {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));

			int fileLength = (int) file.length();
			response.setContentLength(fileLength);
			/* 如果文件长度大于0 */
			if (fileLength != 0) {
				/* 创建输入流 */
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];
				/* 创建输出流 */
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
