package com.insigma.siis.local.pagemodel.zj.slabel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class TagKcclfjUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		res.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("GBK");
		String upload_file = AppConfig.HZB_PATH + "\\tag\\kcclfjfiles";
		PrintWriter out = null;
		String filename = null;
		String filePath = null;
		try {
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			List<FileItem> fileItems = uploader.parseRequest(req);
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {

				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						// String md5Name = MD5.MD5(filename);
						String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// String md5Name = MD5.MD5(filename);
						filePath = upload_file + "\\" + UUID.randomUUID().toString().replaceAll("-", "") + "."
								+ houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
						// 保存数据库
						/*HBSession sess = HBUtil.getHBSession();
						TagKcclfj tagKcclfj = new TagKcclfj();
						tagKcclfj.setTagid(UUID.randomUUID().toString().replaceAll("-", ""));
						tagKcclfj.setA0000(req.getParameter("A0000"));
						tagKcclfj.setDirectory(filePath);
						tagKcclfj.setFjname(java.net.URLDecoder
								.decode(java.net.URLDecoder.decode(req.getParameter("filename"), "UTF-8"), "UTF-8"));
						sess.save(tagKcclfj);
						sess.flush();*/
					} catch (Exception e) {
						e.printStackTrace();
						writer.print("back");
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}