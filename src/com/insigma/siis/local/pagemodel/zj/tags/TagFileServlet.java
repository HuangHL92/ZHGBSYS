package com.insigma.siis.local.pagemodel.zj.tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.extra.TagDazsbfj;
import com.insigma.siis.local.business.entity.extra.TagKcclfj2;
import com.insigma.siis.local.business.entity.extra.TagNdkhdjbfj;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * 标签附件Servlet
 * 
 * @author zhub
 *
 */
public class TagFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private ServletConfig config;
	@SuppressWarnings("unused")
	private LogUtil applog = new LogUtil();

	public final static String disk = getPath();

	private static String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/";
		try {
			File file = new File(upload_file);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 解压路径
		return upload_file;
	}

	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");

		if ("addKcclfjFile".equals(method)) { // 考察材料附件新增
			addKcclfjFile(request, response);
		} else if ("deleteKcclfjFile".equals(method)) { // 考察材料附件删除
			deleteKcclfjFile(request, response);
		} else if ("downloadKcclfjFile".equals(method)) { // 考察材料附件下载
			downloadKcclfjFile(request, response);
		} else if ("addNdkhdjbfjFile".equals(method)) { // 年度考核登记附件新增
			addNdkhdjbfjFile(request, response);
		} else if ("deleteNdkhdjbfjFile".equals(method)) { // 年度考核登记附件删除
			deleteNdkhdjbfjFile(request, response);
		} else if ("downloadNdkhdjbfjFile".equals(method)) { // 年度考核登记附件下载
			downloadNdkhdjbfjFile(request, response);
		} else if ("addDazsbfjFile".equals(method)) { // 档案专审附件新增
			addDazsbfjFile(request, response);
		} else if ("deleteDazsbfjFile".equals(method)) { // 档案专审附件删除
			deleteDazsbfjFile(request, response);
		} else if ("downloadDazsbfjFile".equals(method)) { // 档案专审附件下载
			downloadDazsbfjFile(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 新增考察材料附件
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void addKcclfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String tagid = UUID.randomUUID().toString();
		String a0000 = request.getParameter("a0000");
		String note = URLDecoder.decode(URLDecoder.decode(request.getParameter("note"), "UTF-8"), "UTF-8");

		String attachPath = "kcclfj/" + a0000 + "/" + tagid;

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String filename = "";
			String houzhui = "";
			String filePath = "";
			CommonQueryBS.systemOut("START---------" + DateUtil.getTime());
			String upload_file = "TagFileUpload/" + attachPath + "/"; // 上传路径
			try {
				File file = new File(disk + upload_file);
				if (!file.exists() && !file.isDirectory()) { // 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 获取表单信息
			List<FileItem> fileItems = uploader.parseRequest(request);
			CommonQueryBS.systemOut(fileItems.size() + "");
			Iterator<FileItem> iter = fileItems.iterator();
			// 定义数组，存放附件路径和备注信息
			Map<String, String> text_map = new HashMap<String, String>();
			Map<String, String> filePath_map = new HashMap<String, String>();
			Map<String, String> filename_map = new HashMap<String, String>();
			Map<String, String> fileSize_map = new HashMap<String, String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"), "gbk");
					if (fieldName.startsWith("textid_") && !"".equals(fieldvalue)) {
						text_map.put(fieldName, fieldvalue);
					}

				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					// 判断上传文件的尺寸，大于10M的文件不能上传
					long MAX_SIZE = 10 * 1024 * 1024;

					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";

					if (fi.getSize() < 1048576) {
						fileSize = "0" + fileSize;
					}

					if (fi.getSize() > MAX_SIZE) {
						out.println("<script>window.parent.odin.alert('文件：" + path + "的尺寸超过10M，不能上传！');</script>");
						return;
					}
					try {
						// 获取文件名（带后缀）
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if ("".equals(filename) || filename == null) {
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// 截取文件名（不带后缀）
						filename = filename.substring(0, filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;

						fi.write(new File(disk + filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName() + rowlength, filePath);
						filename_map.put(fi.getFieldName() + rowlength + "_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName() + rowlength + "_fileSize", fileSize);

					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			// 将附件信息保存至附件表中
			for (int i = 1; i <= rowlength; i++) {
				String filepath = filePath_map.get("excelFile" + i);
				String fileName = filename_map.get("excelFile" + i + "_filename");
				String fileSize = fileSize_map.get("excelFile" + i + "_fileSize");
				saveTagKcclfjFolder(tagid, a0000, filepath, fileName, fileSize, note);
			}
			out.println("<script>window.parent.odin.alert('上传成功');</script>");
		} catch (Exception e) {
			out.println("<script>window.parent.odin.alert('上传失败！');</script>");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * 将上传的考察材料信息保存在数据库中
	 * 
	 * @param fileurl
	 *            文件地址
	 * @param filename
	 *            文件名称
	 * @return
	 */
	public int saveTagKcclfjFolder(String tagid, String a0000, String fileurl, String filename, String filesize,
			String note) {

		// 创建数据库对象
		HBSession sess = HBUtil.getHBSession();

		// 创建附件对象
		TagKcclfj2 tagKcclfj = new TagKcclfj2();
		// 获取主键
		tagKcclfj.setTagid(tagid);
		tagKcclfj.setA0000(a0000); // 人员id
		tagKcclfj.setFilename(filename); // 文件名称
		tagKcclfj.setFilesize(filesize); // 文件大小
		tagKcclfj.setFileurl(fileurl); // 文件存储相对路径
		tagKcclfj.setNote(note); // 备注
		tagKcclfj.setUpdatedate(System.currentTimeMillis()); // 更新时间

		// 执行数据库操作
		sess.save(tagKcclfj);
		sess.flush();

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 删除考察材料附件
	@SuppressWarnings("unlikely-arg-type")
	public void deleteKcclfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		HBSession session = HBUtil.getHBSession();
		String tagid = request.getParameter("tagid"); // 获得要删除的文件tagid
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");

		// 删除掉表中的年度考核登记表附件数据
		if (tagid != null && !"".equals(tagid)) {
			TagKcclfj2 tagKcclfj = (TagKcclfj2) session.get(TagKcclfj2.class, tagid);
			if (tagKcclfj != null && !"".equals(tagKcclfj)) {
				session.delete(tagKcclfj);
				session.flush();
			}
		}
		// 删除文件及所在文件夹
		this.delFileOrFolder(fileurl, tagid);
	}

	// 下载考察材料附件
	public void downloadKcclfjFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获得传来的文件相对路径
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");
		// 文件名称
		String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1);
		fileurl = disk + fileurl; // 拼接出绝对路径

		/* 读取文件 */
		File file = new File(fileurl);
		/* 如果文件存在 */
		if (file.exists()) {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));

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

	/**
	 * 新增年度考核登记表附件
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void addNdkhdjbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String tagid = UUID.randomUUID().toString();
		String a0000 = request.getParameter("a0000");
		String note = URLDecoder.decode(URLDecoder.decode(request.getParameter("note"), "UTF-8"), "UTF-8");

		String attachPath = "ndkhdjbfj/" + a0000 + "/" + tagid;

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String filename = "";
			String houzhui = "";
			String filePath = "";
			CommonQueryBS.systemOut("START---------" + DateUtil.getTime());
			String upload_file = "TagFileUpload/" + attachPath + "/";// 上传路径
			try {
				File file = new File(disk + upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 获取表单信息
			List<FileItem> fileItems = uploader.parseRequest(request);
			CommonQueryBS.systemOut(fileItems.size() + "");
			Iterator<FileItem> iter = fileItems.iterator();
			// 定义数组，存放附件路径和备注信息
			Map<String, String> text_map = new HashMap<String, String>();
			Map<String, String> filePath_map = new HashMap<String, String>();
			Map<String, String> filename_map = new HashMap<String, String>();
			Map<String, String> fileSize_map = new HashMap<String, String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"), "gbk");
					if (fieldName.startsWith("textid_") && !"".equals(fieldvalue)) {
						text_map.put(fieldName, fieldvalue);
					}

				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					// 判断上传文件的尺寸，大于10M的文件不能上传
					// CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;

					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";

					if (fi.getSize() < 1048576) {
						fileSize = "0" + fileSize;
					}

					if (fi.getSize() > MAX_SIZE) {
						out.println("<script>window.parent.odin.alert('文件：" + path + "的尺寸超过10M，不能上传！');</script>");
						return;
					}
					try {
						// 获取文件名（带后缀）
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if ("".equals(filename) || filename == null) {
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// 截取文件名（不带后缀）
						filename = filename.substring(0, filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;

						fi.write(new File(disk + filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName() + rowlength, filePath);
						filename_map.put(fi.getFieldName() + rowlength + "_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName() + rowlength + "_fileSize", fileSize);

					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			// 将附件信息保存至附件表中
			for (int i = 1; i <= rowlength; i++) {
				String filepath = filePath_map.get("excelFile" + i);
				String fileName = filename_map.get("excelFile" + i + "_filename");
				String fileSize = fileSize_map.get("excelFile" + i + "_fileSize");
				saveTagNdkhdjbfjFolder(tagid, a0000, filepath, fileName, fileSize, note);
			}
			out.println("<script>window.parent.odin.alert('上传成功');</script>");
		} catch (Exception e) {
			out.println("<script>window.parent.odin.alert('上传失败！');</script>");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * 将上传的年度考核登记附件信息保存在数据库中
	 * 
	 * @param fileurl
	 *            文件地址
	 * @param filename
	 *            文件名称
	 * @return
	 */
	public int saveTagNdkhdjbfjFolder(String tagid, String a0000, String fileurl, String filename, String filesize,
			String note) {

		// 创建数据库对象
		HBSession sess = HBUtil.getHBSession();

		// 创建附件对象
		TagNdkhdjbfj tagNdkhdjbfj = new TagNdkhdjbfj();
		// 获取主键
		tagNdkhdjbfj.setTagid(tagid);
		tagNdkhdjbfj.setA0000(a0000); // 人员id
		tagNdkhdjbfj.setFilename(filename); // 文件名称
		tagNdkhdjbfj.setFilesize(filesize); // 文件大小
		tagNdkhdjbfj.setFileurl(fileurl); // 文件存储相对路径
		tagNdkhdjbfj.setNote(note); // 备注
		tagNdkhdjbfj.setUpdatedate(System.currentTimeMillis()); // 更新时间

		// 执行数据库操作
		sess.save(tagNdkhdjbfj);
		sess.flush();

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 删除年度考核登记附件
	@SuppressWarnings("unlikely-arg-type")
	public void deleteNdkhdjbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		HBSession session = HBUtil.getHBSession();
		String tagid = request.getParameter("tagid"); // 获得要删除的文件tagid
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");

		// 删除掉表中的年度考核登记表附件数据
		if (tagid != null && !"".equals(tagid)) {
			TagNdkhdjbfj tagNdkhdjbfj = (TagNdkhdjbfj) session.get(TagNdkhdjbfj.class, tagid);
			if (tagNdkhdjbfj != null && !"".equals(tagNdkhdjbfj)) {
				session.delete(tagNdkhdjbfj);
				session.flush();
			}
		}
		// 删除文件及所在文件夹
		this.delFileOrFolder(fileurl, tagid);
	}

	// 下载年度考核登记附件
	public void downloadNdkhdjbfjFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获得传来的文件相对路径
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");
		// 文件名称
		String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1);

		fileurl = disk + fileurl; // 拼接出绝对路径

		/* 读取文件 */
		File file = new File(fileurl);
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

	/**
	 * 新增档案专审附件
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void addDazsbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String tagid = UUID.randomUUID().toString();
		String a0000 = request.getParameter("a0000");
		String note = URLDecoder.decode(URLDecoder.decode(request.getParameter("note"), "UTF-8"), "UTF-8");

		String attachPath = "dazsbfj/" + a0000 + "/" + tagid;

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("开始上传");
			out = response.getWriter();
			String filename = "";
			String houzhui = "";
			String filePath = "";
			CommonQueryBS.systemOut("START---------" + DateUtil.getTime());
			String upload_file = "TagFileUpload/" + attachPath + "/";// 上传路径
			try {
				File file = new File(disk + upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 获取表单信息
			List<FileItem> fileItems = uploader.parseRequest(request);
			CommonQueryBS.systemOut(fileItems.size() + "");
			Iterator<FileItem> iter = fileItems.iterator();
			// 定义数组，存放附件路径和备注信息
			Map<String, String> text_map = new HashMap<String, String>();
			Map<String, String> filePath_map = new HashMap<String, String>();
			Map<String, String> filename_map = new HashMap<String, String>();
			Map<String, String> fileSize_map = new HashMap<String, String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"), "gbk");
					if (fieldName.startsWith("textid_") && !"".equals(fieldvalue)) {
						text_map.put(fieldName, fieldvalue);
					}

				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					// 判断上传文件的尺寸，大于10M的文件不能上传
					long MAX_SIZE = 10 * 1024 * 1024;

					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";

					if (fi.getSize() < 1048576) {
						fileSize = "0" + fileSize;
					}

					if (fi.getSize() > MAX_SIZE) {
						out.println("<script>window.parent.odin.alert('文件：" + path + "的尺寸超过10M，不能上传！');</script>");
						return;
					}
					try {
						// 获取文件名（带后缀）
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if ("".equals(filename) || filename == null) {
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// 截取文件名（不带后缀）
						filename = filename.substring(0, filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;

						fi.write(new File(disk + filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName() + rowlength, filePath);
						filename_map.put(fi.getFieldName() + rowlength + "_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName() + rowlength + "_fileSize", fileSize);

					} catch (Exception e) {
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			// 将附件信息保存至附件表中
			for (int i = 1; i <= rowlength; i++) {
				String filepath = filePath_map.get("excelFile" + i);
				String fileName = filename_map.get("excelFile" + i + "_filename");
				String fileSize = fileSize_map.get("excelFile" + i + "_fileSize");
				saveTagDazsbfjFolder(tagid, a0000, filepath, fileName, fileSize, note);
			}
			out.println("<script>window.parent.odin.alert('上传成功');</script>");
		} catch (Exception e) {
			out.println("<script>window.parent.odin.alert('上传失败！');</script>");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * 将上传的档案专审附件信息保存在数据库中
	 * 
	 * @param fileurl
	 *            文件地址
	 * @param filename
	 *            文件名称
	 * @return
	 */
	public int saveTagDazsbfjFolder(String tagid, String a0000, String fileurl, String filename, String filesize,
			String note) {

		// 创建数据库对象
		HBSession sess = HBUtil.getHBSession();

		// 创建附件对象
		TagDazsbfj tagDazsbfj = new TagDazsbfj();
		// 获取主键
		tagDazsbfj.setTagid(tagid);
		tagDazsbfj.setA0000(a0000); // 人员id
		tagDazsbfj.setFilename(filename); // 文件名称
		tagDazsbfj.setFilesize(filesize); // 文件大小
		tagDazsbfj.setFileurl(fileurl); // 文件存储相对路径
		tagDazsbfj.setNote(note); // 备注
		tagDazsbfj.setUpdatedate(System.currentTimeMillis()); // 更新时间

		// 执行数据库操作
		sess.save(tagDazsbfj);
		sess.flush();

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 删除档案专审附件
	@SuppressWarnings("unlikely-arg-type")
	public void deleteDazsbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		HBSession session = HBUtil.getHBSession();
		String tagid = request.getParameter("tagid"); // 获得要删除的文件tagid
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");

		// 删除掉表中的年度考核登记表附件数据
		if (tagid != null && !"".equals(tagid)) {
			TagDazsbfj tagDazsbfj = (TagDazsbfj) session.get(TagDazsbfj.class, tagid);
			if (tagDazsbfj != null && !"".equals(tagDazsbfj)) {
				session.delete(tagDazsbfj);
				session.flush();
			}
		}
		// 删除文件及所在文件夹
		this.delFileOrFolder(fileurl, tagid);
	}

	// 下载档案专审附件
	public void downloadDazsbfjFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获得传来的文件相对路径
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");
		// 文件名称
		String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1);

		fileurl = disk + fileurl; // 拼接出绝对路径

		/* 读取文件 */
		File file = new File(fileurl);
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

	/*
	 * 【通用方法】删除文件及所在文件夹：param filePath 文件相对路径， param folderName 文件所在文件夹名称
	 */
	public void delFileOrFolder(String filePath, String folderName) {
		try {
			// 拼接出文件绝对路径
			filePath = disk + filePath;
			if (-1 == filePath.indexOf(folderName)) {
				this.delFile(filePath);
			} else {
				// 拼接出文件夹绝对路径
				String folderPath = filePath.substring(0, filePath.indexOf(folderName) + folderName.length());
				// 删除文件及所在文件夹
				this.delFolder(folderPath);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 【通用方法】删除文件：param filePath 文件完整绝对路径
	 */
	public boolean delFile(String filePath) throws AppException {
		File file = new File(filePath);
		boolean ret = true;
		if (file.exists()) {
			ret = file.delete();
		}
		return ret;
	}

	/*
	 * 【通用方法】删除文件夹：param folderPath 文件夹完整绝对路径
	 */
	public void delFolder(String folderPath) {
		delAllFile(folderPath); // 删除完里面所有内容
		String filePath = folderPath;
		filePath = filePath.toString();
		File file = new File(filePath);
		file.delete(); // 删除空文件夹
	}

	/*
	 * 【通用方法】删除指定文件夹下所有文件：param path 文件夹完整绝对路径
	 */
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
				delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]); // 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}
