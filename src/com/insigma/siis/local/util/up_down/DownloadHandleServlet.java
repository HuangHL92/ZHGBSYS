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
 * �ļ����ش�����
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
     * �ļ�����
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void download(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	//PrintWriter localPrintWriter = response.getWriter();
        //�õ�Ҫ���ص��ļ���
    	String fileName = request.getParameter("filename");
    	fileName = new String(fileName.getBytes("iso8859-1"),"GBK");
    	//�ϴ����ļ����Ǳ�����/WEB-INF/uploadĿ¼�µ���Ŀ¼����
    	String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");
    	if(sys.startsWith("WIN")){
    		fileSaveRootPath = UPLODA_PATH_WIN;
	     }else{
	    	 fileSaveRootPath = UPLODA_PATH;
	     }
    	//ͨ���ļ����ҳ��ļ�������Ŀ¼
    	String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
    	//�õ�Ҫ���ص��ļ�
    	//File file = new File(path + "\\" + fileName);
    	File file = new File(path + separator + fileName);
    	//����ļ�������
    	if(!file.exists()){
    		request.setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ��");
    		request.getRequestDispatcher("/message.jsp").forward(request, response);
    		//localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
    		//localPrintWriter.close();
    		return;
    	}
    	
    	//�����ļ���
    	String realname = fileName.substring(fileName.indexOf("_")+1);
    	//������Ӧͷ��������������ظ��ļ�
    	response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
    	//��ȡҪ���ص��ļ������浽�ļ�������
    	//FileInputStream in = new FileInputStream(path + "\\" + fileName);
    	FileInputStream in = new FileInputStream(path + separator + fileName);
    	//���������
    	OutputStream out = response.getOutputStream();
    	//����������
    	byte buffer[] = new byte[1024];
    	int len = 0;
    	//ѭ�����������е����ݶ�ȡ������������
    	while((len=in.read(buffer))>0){
    		//��������������ݵ��������ʵ���ļ�����
    		out.write(buffer, 0, len);
    	}
    	//�ر��ļ�������
    	in.close();
    	//�ر������
    	out.close();
    }
    /***********
     * �ļ�ɾ��
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void remove(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	PrintWriter localPrintWriter = response.getWriter();
    	//�õ�Ҫ���ص��ļ���
    	String fileName = request.getParameter("filename");
    	fileName = new String(fileName.getBytes("iso8859-1"),"GBK");
    	//�ϴ����ļ����Ǳ�����/WEB-INF/uploadĿ¼�µ���Ŀ¼����
    	String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");
    	if(sys.startsWith("WIN")){
    		fileSaveRootPath = UPLODA_PATH_WIN;
	     }else{
	    	 fileSaveRootPath = UPLODA_PATH;
	     }
    	//ͨ���ļ����ҳ��ļ�������Ŀ¼
    	String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
    	//�õ�Ҫɾ�����ļ�
    	File file = new File(path + separator + fileName);
    	//����ļ�������
    	if(!file.exists()){
    		request.setAttribute("message", "��Ҫɾ������Դ������");
    		//request.getRequestDispatcher("/message.jsp").forward(request, response);
    		localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
    		return;
    	}else{
    		file.delete();
    		request.setAttribute("message", "�ļ�"+fileName+"ɾ�����");
    		//request.getRequestDispatcher("/message.jsp").forward(request, response);
    		localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
    	}
    }
    /*************************
     * ͨ���ļ����ʹ洢�ϴ��ļ���Ŀ¼�ҳ�Ҫ���ص��ļ�������·��
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
    		//����Ŀ¼
    		file.mkdirs();
    	}
    	return dir;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	doGet(request, response);
    }
}