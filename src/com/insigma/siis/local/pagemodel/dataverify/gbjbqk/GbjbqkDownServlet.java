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
             //word内容
             String content="<html><body>"+method1+"</body></html>";
             byte b[] = content.getBytes("utf-8");  //这里是必须要设置编码的，不然导出中文就会乱码。
             bais = new ByteArrayInputStream(b);//将字节数组包装到流中  
             /*
             * 关键地方
             * 生成word格式
             */
             POIFSFileSystem poifs = new POIFSFileSystem();  
             DirectoryEntry directory = poifs.getRoot();  
             DocumentEntry documentEntry = directory.createDocument("WordDocument", bais); 
             //输出文件
             String fileName="";
             if("Gbjbqk".equals(method)){
            	 fileName="干部基本情况统计";
             }else if("Gbjbqknv".equals(method)){
            	 fileName="干部基本情况统计(女)";
             }else if("Gbjbqkshao".equals(method)){
            	 fileName="干部基本情况统计(少)";
             }else if("Gbjbqkfei".equals(method)){
            	 fileName="干部基本情况统计(非)";
             }else if("Gbjbqkxwxl".equals(method)){
            	 fileName="干部基本情况统计(学历学位)";
             }else if("Gbjbqknl".equals(method)){
            	 fileName="干部基本情况统计(年龄)";
             }else if("Gbjbqkxzcc".equals(method)){
            	 fileName="干部基本情况统计(现职层次)";
             }else{
            	 fileName="干部基本情况统计";
             }
             
             request.setCharacterEncoding("utf-8");  
             response.setContentType("application/msword");//导出word格式
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
