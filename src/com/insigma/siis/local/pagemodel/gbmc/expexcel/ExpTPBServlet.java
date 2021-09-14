package com.insigma.siis.local.pagemodel.gbmc.expexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.insigma.odin.framework.AppException;

import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.LongSQL;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.gbmc.TPBPageModel;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc2;


public class ExpTPBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ExpTPBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		PrintWriter out = response.getWriter();
		if("gbmc2".equals(method)) {
			String basesql = request.getParameter("basesql");
			String result;
			try {
				result = gbmc2(request,basesql);
				downFile(request, response, result);
				//out.write(result);
			} catch (RadowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}





	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private String gbmc2(HttpServletRequest request, String basesql) throws RadowException {
		String sid = request.getSession().getId();
		String gbmc2sql = LongSQL.gbmc2SQL(basesql);
		gbmc2sql = gbmc2sql.replace("jgfw", "ZWHZYQ_QD.jgfw");
        System.out.println(gbmc2sql);
		ExportAsposeBS e = new ExportAsposeBS();
	   
	    List<Gbmc2> list = null;
		try {
			list = ExpTPB.querybyid_gbmc2(gbmc2sql);
		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String expFile = ExpRar.expFile();
		String rootPath = e.getRootPath();
		String tempPath = rootPath + "gbhmc1.xls";
		String path = expFile;
		String expName = "干部花名册（一人一行）";
		String infile = expFile + expName + ".xls";
		e.exportHYMC(tempPath, path, list);
		return infile;
	}
	/**
	 * 下载文件(单个文件下载)方法
	 */
	public void downFile(HttpServletRequest request,
			HttpServletResponse response ,String path) throws IOException {
		//String path = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("prid"),"UTF-8"),"UTF-8");
		String filename = path.substring(path.lastIndexOf("/")+1);
		String foldername = path.substring(0,path.lastIndexOf("."));
        /*读取文件*/
        File file = new File(path);
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
//              UploadHelpFileServlet.delFolder(foldername);
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
//            UploadHelpFileServlet.delFolder(foldername);
        }
        file = null;
        
	}
}
