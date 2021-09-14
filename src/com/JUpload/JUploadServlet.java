package com.JUpload;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UploadServlet
 */
public class JUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//文件存放的目录  
        /*File tempDirPath =new File(request.getSession().getServletContext().getRealPath("/")+"\\upload\\");  
        if(!tempDirPath.exists()){  
            tempDirPath.mkdirs();  
        }*/  
		request.setCharacterEncoding("utf-8");   
		String uploadType = request.getParameter("uploadType");
		String file_pk = request.getParameter("file_pk");
		
		if("DELETE".equals(uploadType)){
			String reStr = "";
			try {
				Class clazz = Class.forName("com.insigma.siis.local.pagemodel."+request.getParameter("pageModel").replace("pages.", "")+"PageModel");
				Constructor<?> cons = clazz.getConstructor();
				JUpload obj = (JUpload)cons.newInstance();
				reStr = obj.deleteFile(file_pk);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = null;  
	        try {  
	            out = encodehead(request, response);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        //这个地方不能少，否则前台得不到上传的结果  
	        out.write(reStr);
	        out.close();   
	        return;
		}
		
		//CurrentUser cu = SysUtil.getCacheCurrentUser();
		
		
		
        //创建磁盘文件工厂  
        DiskFileItemFactory fac = new DiskFileItemFactory();      
        //创建servlet文件上传组件  
        ServletFileUpload upload = new ServletFileUpload(fac);   
        upload.setHeaderEncoding("ISO8859_1");
        //文件列表  
        List<FileItem> fileList = null;    
        List<FileItem> fileList_re =  new ArrayList<FileItem>(); 
        Map<String,String> pv = new HashMap<String,String>();
        //解析request从而得到前台传过来的文件  
        try {      
            fileList = upload.parseRequest(request);      
        } catch (FileUploadException ex) {      
            ex.printStackTrace();      
            return;      
        }   
        //保存后的文件名  
        //String imageName = null;  
        //便利从前台得到的文件列表  
        Iterator<FileItem> it = fileList.iterator();     
        while(it.hasNext()){      
            FileItem item =  it.next(); 
            //如果不是普通表单域，当做文件域来处理  
            if(!item.isFormField()){  
            	fileList_re.add(item);
            	//System.out.println(item.getName());
            	//System.out.println(item.getFieldName());  
            }else if (item.isFormField()) {
            	//此处为表单参数
            	pv.put(new String(item.getFieldName().getBytes("ISO8859_1"),"utf-8"), new String(item.getString().getBytes("ISO8859_1"),"utf-8"));
        	
        	
        	}
        }  
        String reStr = "";
        //通过反射调用pagemode 传递数据
        ///gwy/src/com/insigma/siis/local/pagemodel/register/company/RegisterPageModel.java
        uploadType = pv.get("uploadType");
        try {
			Class clazz = Class.forName("com.insigma.siis.local.pagemodel."+pv.get("pageModel").replace("pages.", "")+"PageModel");
			Constructor<?> cons = clazz.getConstructor();
			JUpload obj = (JUpload)cons.newInstance();
			if("UPLOAD".equals(uploadType)){
				Map<String, String> map = obj.getFiles(fileList_re, pv);
				JSONObject jsonObject = JSONObject.fromObject(map);  
				reStr = jsonObject.toString();
			}else{
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException();
		}
        
        
        
        
        
        
         
        PrintWriter out = null;  
        try {  
            out = encodehead(request, response);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //这个地方不能少，否则前台得不到上传的结果  
        out.write(reStr);
        out.close();   
	}
	
	/** 
     * Ajax辅助方法 获取 PrintWriter 
     * @return 
     * @throws IOException  
     * @throws IOException  
     * request.setCharacterEncoding("utf-8"); 
        response.setContentType("text/html; charset=utf-8"); 
     */  
	
	private PrintWriter encodehead(HttpServletRequest request,HttpServletResponse response) throws IOException{  
        //request.setCharacterEncoding("utf-8");  
        response.setContentType("text/html; charset=utf-8");  
        return response.getWriter();  
    }  
}
