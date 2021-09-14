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
		//�ļ���ŵ�Ŀ¼  
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
	        //����ط������٣�����ǰ̨�ò����ϴ��Ľ��  
	        out.write(reStr);
	        out.close();   
	        return;
		}
		
		//CurrentUser cu = SysUtil.getCacheCurrentUser();
		
		
		
        //���������ļ�����  
        DiskFileItemFactory fac = new DiskFileItemFactory();      
        //����servlet�ļ��ϴ����  
        ServletFileUpload upload = new ServletFileUpload(fac);   
        upload.setHeaderEncoding("ISO8859_1");
        //�ļ��б�  
        List<FileItem> fileList = null;    
        List<FileItem> fileList_re =  new ArrayList<FileItem>(); 
        Map<String,String> pv = new HashMap<String,String>();
        //����request�Ӷ��õ�ǰ̨���������ļ�  
        try {      
            fileList = upload.parseRequest(request);      
        } catch (FileUploadException ex) {      
            ex.printStackTrace();      
            return;      
        }   
        //�������ļ���  
        //String imageName = null;  
        //������ǰ̨�õ����ļ��б�  
        Iterator<FileItem> it = fileList.iterator();     
        while(it.hasNext()){      
            FileItem item =  it.next(); 
            //���������ͨ���򣬵����ļ���������  
            if(!item.isFormField()){  
            	fileList_re.add(item);
            	//System.out.println(item.getName());
            	//System.out.println(item.getFieldName());  
            }else if (item.isFormField()) {
            	//�˴�Ϊ������
            	pv.put(new String(item.getFieldName().getBytes("ISO8859_1"),"utf-8"), new String(item.getString().getBytes("ISO8859_1"),"utf-8"));
        	
        	
        	}
        }  
        String reStr = "";
        //ͨ���������pagemode ��������
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
        //����ط������٣�����ǰ̨�ò����ϴ��Ľ��  
        out.write(reStr);
        out.close();   
	}
	
	/** 
     * Ajax�������� ��ȡ PrintWriter 
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
