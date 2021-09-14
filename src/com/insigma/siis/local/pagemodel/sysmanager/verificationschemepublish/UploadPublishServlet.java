package com.insigma.siis.local.pagemodel.sysmanager.verificationschemepublish;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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

import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
@SuppressWarnings("serial")
public class UploadPublishServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		String savePath = this.getServletConfig().getServletContext()
		.getRealPath("");
		savePath = savePath + "/uploads/"+userid+"/";
		File f1 = new File(savePath);
		CommonQueryBS.systemOut(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}

		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {

			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}

		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		String firstName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {

				name = item.getName();
				long size = item.getSize();
				String type = item.getContentType();
				CommonQueryBS.systemOut(size + " " + type);
				if (name == null || name.trim().equals("")) {
					continue;
				}

				// 扩展名格式：
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf("."));
					firstName = name.substring(0, name.lastIndexOf("."));
				}
				File file = null;
				do {
					// 生成文件名：
					name = firstName+"_"+UUID.randomUUID().toString();
					file = new File(savePath + name + extName);
				} while (file.exists());
				File saveFile = new File(savePath + name + extName);
				try {

					item.write(saveFile);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
		response.getWriter().print(name + extName);
	}

}