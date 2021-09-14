package com.insigma.siis.local.pagemodel.FamilyMember;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.server.ObjID;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A36_FILE;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.dataverify.ProblemDownServlet;
import com.insigma.siis.local.util.AsposeExcelToPdf;
import com.insigma.siis.local.util.DateUtil;

/**
家庭成员导出
 * 
 * @author tongsn
 *
 */
public class addFamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(addFamServlet.class);
	private static final String separator = System.getProperty("file.separator");
	/**
	 * Get方法，获取Get请求并依据参数method值选择执行需要执行的方法
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		PrintWriter out = response.getWriter();
		if ("downFileSys".equals(method)) {
			downFileSys(request, response);
			return;
		}else if("expExcel".equals(method)) {
			
			try {
				printExcel(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}else if("upload".equals(method)) {
			String message=saveFile(request,response,method);
			out.write(message);
		}else if("downloadfile".equals(method)) {
			downloadFile(request,response);
			return;
		}else if("downloadExcel".equals(method)) {
			String fileName = URLDecoder.decode(request.getParameter("fileName") + "", "UTF-8");
			 String downloadfileName = request.getParameter("downloadfileName");
			downloadExcel(request, response, downloadfileName,fileName);
		}
	}

	/**
	 * Post方法，获取Post请求并依据参数method值选择执行需要执行的方法
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
	

	

	public void downFileSys(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"), "UTF-8"),
				"UTF-8");
		String ss = File.separatorChar + "";
		path = path.replace("@@", ss);
		String filename = path.substring(path.lastIndexOf("/") + 1);

		/* 读取文件 */
		File file = new File(path);
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
	
	//打印
	
	public  void printExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"), "UTF-8"),
					"UTF-8");
			String ss = File.separatorChar + "";
			path = path.replace("@@", ss);
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/pdf");
			FileInputStream in = new FileInputStream(new File(path));
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[512];
			while ((in.read(b))!=-1) {
			out.write(b);
			}
			out.flush();
			in.close();
			out.close();
			} catch (Exception e) {
			e.printStackTrace();
						}

			
	
		
		

		
	}
	
		private String saveFile(HttpServletRequest req,HttpServletResponse res,String method) throws IOException {
				//req.setCharacterEncoding("utf-8");
				String a0000=req.getParameter("a0000");
		/*
		 * String t_ime=req.getParameter("sbny"); String
		 * txtarea=req.getParameter("txtarea"); txtarea =
		 * URLDecoder.decode(URLDecoder.decode(txtarea,"utf-8"),"utf-8");
		 */
				String filePath=uploadfile(req,res,method);
				HBSession sess=HBUtil.getHBSession();
				String sql="select * from a36_file where a0000='"+a0000+"' order by to_number(t_ime) desc";
				List<A36_FILE> list= sess.createSQLQuery(sql).addEntity(A36_FILE.class).list();
				A36_FILE a36file=null;
				if(list.size()<1) {
					a36file=new A36_FILE();
					a36file.setT_ime(DateUtil.formatDateStr(new Date(),"yyyyMM"));
				}else {
					a36file= list.get(0);
				}
				
				File file=new File(filePath);
				a36file.setA0000(a0000);
				a36file.setFilename(file.getName());
				a36file.setFilepath(filePath);
		/*
		 * a36file.setFiletext(txtarea);
		 * 
		 * a36file.setT_ime(t_ime);
		 */
				sess.saveOrUpdate(a36file);
				sess.flush();
				return "success";
		}
	
		@SuppressWarnings("unchecked")
		private String uploadfile(HttpServletRequest request, HttpServletResponse response,String method) throws IOException {

			String uuid=UUID.randomUUID().toString();
			
			String tmpurl = "tempFile";
			if ("upload".equals(method)) {
				tmpurl = "familymember";
			} 
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload uploader = new ServletFileUpload(factory);
			List<FileItem> fileItems = null;
			Iterator<FileItem> iter;
			String filePath = "";

			try {
				fileItems = uploader.parseRequest(request); // 文件本身
			} catch (FileUploadException e) {
				e.printStackTrace();
			}

			if (!fileItems.isEmpty()) {
				iter = fileItems.iterator();
				String upload_file = AppConfig.HZB_PATH + "/temp/upload/"+tmpurl+"/"+uuid+"/";// 上传路径
				File file = new File(upload_file);
				if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
					file.mkdirs();
				}
				
				while (iter.hasNext()) {

					FileItem fi = iter.next();
					if(fi.getSize()==0){
						continue;
					}
						String name = fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
						filePath = upload_file + name;
					
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
	
		private void downloadFile(HttpServletRequest req,HttpServletResponse res) throws IOException {
			String uuid=UUID.randomUUID().toString();
			String a0000=req.getParameter("a0000");
			//String t_ime=req.getParameter("sbny");
			HBSession sess=HBUtil.getHBSession();
			List<File> files = new ArrayList<File>();
			String sql ="select a.filepath,b.a0101,b.a0184 from a36_file a,a01 b "
					+ "where "
					+ " 1=1 "
					+ " and a.a0000=b.a0000"
					+ " and filepath!=' ' and a.a0000='"+a0000+"' "
							+ "order by to_number(t_ime) desc";
			System.out.println(sql);
			List<Object[]> list=sess.createSQLQuery(sql).list();
			File fos=null;
			String filename=null;
			if(list.size()<1) {
				return ;
			}else{
				for(int i=0;i<list.size();i++) {
					String path=(String) list.get(0)[0];
					File file=new File(path);
					files.add(file);
					break;
				}
				if(files.size()==1) {
					fos=files.get(0);
					filename=list.get(0)[1].toString()+list.get(0)[2].toString()+"_"+fos.getName();
				}else {
					String pathfile=AppConfig.HZB_PATH+ "/temp/upload/"+uuid+"/附件.zip" ;
					filename=pathfile.substring(pathfile.lastIndexOf("/")+1);
					fos=new File(pathfile);
					FileOutputStream fos3 = new FileOutputStream(fos);
					toZip(files,fos3);
				}
				
			}
			
			if (fos.exists()) {
				res.reset();
				res.setHeader("pragma", "no-cache");
				res.setDateHeader("Expires", 0);
				res.setContentType("application/octet-stream;charset=GBK");
				res.addHeader("Content-Disposition",
						"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));
				int fileLength = (int) fos.length();
				res.setContentLength(fileLength);
				/* 如果文件长度大于0 */
				if (fileLength != 0) {
					/* 创建输入流 */
					InputStream inStream = new FileInputStream(fos);
					byte[] buf = new byte[4096];
					/* 创建输出流 */
					ServletOutputStream servletOS = res.getOutputStream();
					int readLength;
					while (((readLength = inStream.read(buf)) != -1)) {
						servletOS.write(buf, 0, readLength);
					}
					inStream.close();
					servletOS.flush();
					servletOS.close();
				}
			}
			fos = null;
			
			
			
		}
	
	
		public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
			long start = System.currentTimeMillis();
			ZipOutputStream zos = null;
			try {
				zos = new ZipOutputStream(out);
				zos.setEncoding("GBK");
				for (File srcFile : srcFiles) {
					byte[] buf = new byte[2 * 1024];
					zos.putNextEntry(new ZipEntry(srcFile.getName()));
					int len;
					FileInputStream in = new FileInputStream(srcFile);
					while ((len = in.read(buf)) != -1) {
						zos.write(buf, 0, len);
					}
					zos.closeEntry();
					in.close();
				}
				long end = System.currentTimeMillis();
				System.out.println("压缩时间" + (end - start) + " ms");
			} catch (Exception e) {
				throw new RuntimeException("zip error from ZipUtils", e);
			} finally {
				if (zos != null) {
					try {
						zos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}	

	private void downloadExcel(HttpServletRequest req,HttpServletResponse response,String downloadfileName,String fileName) {
		ServletContext event = req.getSession().getServletContext();
		File file = null;
		try {
		if("jtxxplxz".equals(downloadfileName)) {
			file=new File(event.getRealPath("pages/FamilyMember/jtxxcjb.xls"));
		}
		response.reset();

		// 设置response的Header
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		response.setContentType("application/msexcel");

		DataInputStream in = new DataInputStream(new FileInputStream(file));

		OutputStream out = response.getOutputStream();

		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		out.close();
		in.close();

		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

		toClient.write(bufferOut);
		toClient.flush();
		toClient.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

		
	}
	
		
		
}

