package com.insigma.siis.local.pagemodel.dataverify.gbjbqk;


import java.awt.AWTException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.third.org.apache.poi.poifs.filesystem.DirectoryEntry;
import com.fr.third.org.apache.poi.poifs.filesystem.DocumentEntry;
import com.fr.third.org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class GbjbqkDownServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GbjbqkDownServlet() {
	}
	
	public void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		doPost(paramHttpServletRequest, paramHttpServletResponse);
	}
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		 String method1 = request.getParameter("wenzims_str");
		 String method=request.getParameter("method");
		 ByteArrayInputStream bais=null;
		 ServletOutputStream ostream =null;
		 
		 try {
             //word����
             String content="<html><body>"+method1+"</body></html>";
             byte b[] = content.getBytes("utf-8");  //�����Ǳ���Ҫ���ñ���ģ���Ȼ�������ľͻ����롣
             bais = new ByteArrayInputStream(b);//���ֽ������װ������  
             /*
             * �ؼ��ط�
             * ����word��ʽ
             */
             POIFSFileSystem poifs = new POIFSFileSystem();  
             DirectoryEntry directory = poifs.getRoot();  
             DocumentEntry documentEntry = directory.createDocument("WordDocument", bais); 
             //����ļ�
             String fileName="";
             if("Gbjbqk".equals(method)){
            	 fileName="�ɲ��������ͳ��";
             }else if("Gbjbqknv".equals(method)){
            	 fileName="�ɲ��������ͳ��(Ů)";
             }else if("Gbjbqkshao".equals(method)){
            	 fileName="�ɲ��������ͳ��(��)";
             }else if("Gbjbqkfei".equals(method)){
            	 fileName="�ɲ��������ͳ��(��)";
             }else if("Gbjbqkxwxl".equals(method)){
            	 fileName="�ɲ��������ͳ��(ѧ��ѧλ)";
             }else if("Gbjbqknl".equals(method)){
            	 fileName="�ɲ��������ͳ��(����)";
             }else if("Gbjbqkxzcc".equals(method)){
            	 fileName="�ɲ��������ͳ��(��ְ���)";
             }else{
            	 fileName="�ɲ��������ͳ��";
             }
             
             request.setCharacterEncoding("utf-8");  
             response.setContentType("application/msword");//����word��ʽ
             response.addHeader("Content-Disposition", "attachment;filename=" +
                      new String( (fileName + ".doc").getBytes(),  
                              "iso-8859-1"));
              
             ostream = (ServletOutputStream) response.getOutputStream(); 
             poifs.writeFilesystem(ostream);  
             bais.close();  
             ostream.close(); 
         }catch(Exception e){
        	 e.printStackTrace();
         } finally{
        	 if(bais!=null){
        		 bais.close();
        	 }
        	 if(ostream!=null){
        		 ostream.close();
        	 }
         }
		
	}
	
	public void impJpg(HttpServletRequest request,
			HttpServletResponse response,String htmlstr) throws MalformedURLException, IOException, URISyntaxException, AWTException,ServletException, IOException{
		
		

	}

}
