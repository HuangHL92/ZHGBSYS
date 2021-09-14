package com.insigma.siis.local.util.up_down;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.insigma.odin.framework.util.GlobalNames;
/**********************
 * 文件下载处理类
 * @author lee
 *
 */
public class DownloadHandleServlet extends HttpServlet { 
	private static Logger log = Logger.getLogger(UploadHandleServlet.class); 
	private static final String separator =System.getProperty("file.separator");
	private static final String sys =System.getProperty("os.name").toUpperCase();
	private static final String UPLODA_PATH = GlobalNames.sysConfig.get("UPLODA_PATH"); 
	private static final String UPLODA_PATH_WIN = GlobalNames.sysConfig.get("UPLODA_PATH_WIN"); 
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
    	String method = request.getParameter("method");
	    if ("download".equals(method)){
	    	download(request,response);
	    }else if("remove".equals(method)){
	    	remove(request,response);
	    }
    	
    }
    /***************
     * 文件下载
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void download(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	//PrintWriter localPrintWriter = response.getWriter();
        //得到要下载的文件名
    	String fileName = request.getParameter("filename");
    	fileName = new String(fileName.getBytes("iso8859-1"),"GBK");
    	//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
    	String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");
    	if(sys.startsWith("WIN")){
    		fileSaveRootPath = UPLODA_PATH_WIN;
	     }else{
	    	 fileSaveRootPath = UPLODA_PATH;
	     }
    	//通过文件名找出文件的所在目录
    	String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
    	//得到要下载的文件
    	//File file = new File(path + "\\" + fileName);
    	File file = new File(path + separator + fileName);
    	//如果文件不存在
    	if(!file.exists()){
    		request.setAttribute("message", "您要下载的资源已被删除");
    		request.getRequestDispatcher("/message.jsp").forward(request, response);
    		//localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
    		//localPrintWriter.close();
    		return;
    	}
    	
    	//处理文件名
    	String realname = fileName.substring(fileName.indexOf("_")+1);
    	//设置响应头，控制浏览器下载该文件
    	response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
    	//读取要下载的文件，保存到文件输入流
    	//FileInputStream in = new FileInputStream(path + "\\" + fileName);
    	FileInputStream in = new FileInputStream(path + separator + fileName);
    	//创建输出流
    	OutputStream out = response.getOutputStream();
    	//创建缓冲区
    	byte buffer[] = new byte[1024];
    	int len = 0;
    	//循环将输入流中的内容读取到缓冲区当中
    	while((len=in.read(buffer))>0){
    		//输出缓冲区的内容到浏览器，实现文件下载
    		out.write(buffer, 0, len);
    	}
    	//关闭文件输入流
    	in.close();
    	//关闭输出流
    	out.close();
    }
    /***********
     * 文件删除
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void remove(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	PrintWriter localPrintWriter = response.getWriter();
    	//得到要下载的文件名
    	String fileName = request.getParameter("filename");
    	fileName = new String(fileName.getBytes("iso8859-1"),"GBK");
    	//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
    	String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");
    	if(sys.startsWith("WIN")){
    		fileSaveRootPath = UPLODA_PATH_WIN;
	     }else{
	    	 fileSaveRootPath = UPLODA_PATH;
	     }
    	//通过文件名找出文件的所在目录
    	String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
    	//得到要删除的文件
    	File file = new File(path + separator + fileName);
    	//如果文件不存在
    	if(!file.exists()){
    		request.setAttribute("message", "您要删除的资源不存在");
    		//request.getRequestDispatcher("/message.jsp").forward(request, response);
    		localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
    		return;
    	}else{
    		file.delete();
    		request.setAttribute("message", "文件"+fileName+"删除完成");
    		//request.getRequestDispatcher("/message.jsp").forward(request, response);
    		localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
    	}
    }
    /*************************
     * 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
     * @param filename
     * @param saveRootPath
     * @return
     */
    public String findFileSavePathByFileName(String filename,String saveRootPath){
    	int hashcode = filename.hashCode();
    	int dir1 = hashcode&0xf;  //0--15
    	int dir2 = (hashcode&0xf0)>>4;  //0-15
    	//String dir = saveRootPath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
    	String dir = saveRootPath + separator + dir1 + separator + dir2;  //upload\2\3  upload\3\5
    	File file = new File(dir);
    	if(!file.exists()){
    		//创建目录
    		file.mkdirs();
    	}
    	return dir;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	doGet(request, response);
    }
}