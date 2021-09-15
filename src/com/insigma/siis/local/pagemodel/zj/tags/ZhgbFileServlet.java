package com.insigma.siis.local.pagemodel.zj.tags;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.extra.TagDazsbfj;
import com.insigma.siis.local.business.entity.extra.TagKcclfj2;
import com.insigma.siis.local.business.entity.extra.TagNdkhdjbfj;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.Excel2TableUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 标签附件Servlet
 * 
 * @author zhub
 *
 */
public class ZhgbFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer startNumber =  Integer.parseInt(request.getParameter("startNumber"));
		Integer rowNumber =  Integer.parseInt(request.getParameter("rowNumber"));
		String importType =  URLDecoder.decode(request.getParameter("importType"),"UTF-8");
		String importPath =  "D:/zhgbImport/";
		File importFile =  new File(importPath);
		if(!importFile.exists() || !importFile.isDirectory() ){
			importFile.mkdirs();
		}
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		String fileName = "";
		String houzhui = "";
		File dataFile = null;

		try {
			List<FileItem> item = uploader.parseRequest(request);
			Iterator<FileItem> iterator = item.iterator();
			while(iterator.hasNext()){
				FileItem fileItem = iterator.next();
				if(!fileItem.isFormField()){
					fileName = fileItem.getName();
//					houzhui = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
					// 截取文件名（不带后缀）
//					fileName = fileName.substring(0, fileName.lastIndexOf("."));
					dataFile = new File(importPath+fileName);
					fileItem.write(dataFile);
				}

			}
		}catch(FileUploadException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileInputStream fileInputStream = new FileInputStream(dataFile);
		try{
			Excel2TableUtil.getEcel2Sql("testh",fileInputStream,startNumber,rowNumber);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
