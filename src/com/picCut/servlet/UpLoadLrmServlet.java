package com.picCut.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.insigma.odin.framework.radow.util.DateUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.util.up_down.IAfterFileUpload;
/****************
 * �ļ��ϴ�������
 * @author lee
 *
 */
@SuppressWarnings("serial")
public class UpLoadLrmServlet extends HttpServlet {
     private static Logger log = Logger.getLogger(UpLoadLrmServlet.class); 
	 private static final String separator =System.getProperty("file.separator");
	 private static final String sys =System.getProperty("os.name").toUpperCase();
	 private static final String UPLODA_PATH = GlobalNames.sysConfig.get("UPLODA_PATH"); 
	 private static  String UPLODA_PATH_WIN = null; 
	 public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 
		 UPLODA_PATH_WIN = request.getRealPath("/");
		 String method = request.getParameter("method");
		 response.setCharacterEncoding("GBK");
		 String a0000 = UUID.randomUUID().toString();
		 PrintWriter localPrintWriter = response.getWriter();
		 List<HashMap<String,Object>>  hlist = new ArrayList<HashMap<String,Object>>();
		 String a0201b ="";
		 String calssname ="IAfterFileUpload";//Ĭ�Ͻӿ�
		 //�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
	     String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	     //�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
	     String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	     //��ȡ����ϵͳ����
	     log.info(sys);
	     if(sys.startsWith("WIN")){
	    	 savePath = UPLODA_PATH_WIN;
	    	 tempPath = UPLODA_PATH_WIN + "temp";
	     }else{
	    	 savePath = UPLODA_PATH;
	    	 tempPath = UPLODA_PATH + "temp";
	     }
	     File tmpFile = new File(tempPath);
	     if (!tmpFile.exists()) {
	    	 //������ʱĿ¼
	    	 tmpFile.mkdir();
	     }
	     //��Ϣ��ʾ
	     String message = "";
	     try{
	    	 //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
	    	 //1������һ��DiskFileItemFactory����
	    	 DiskFileItemFactory factory = new DiskFileItemFactory();
	    	 //���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
	    	//���û������Ĵ�СΪ20M�������ָ������ô�������Ĵ�СĬ����10KB
	    	 factory.setSizeThreshold(1024*1024*20);
	    	 //�����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
	    	 factory.setRepository(tmpFile);
	    	 //2������һ���ļ��ϴ�������
	    	 ServletFileUpload upload = new ServletFileUpload(factory);
	    	 //�����ļ��ϴ�����
	    	 upload.setProgressListener(new ProgressListener(){
	    		 public void update(long pBytesRead, long pContentLength, int arg2) {
	    			 log.info("�ļ���СΪ��" + pContentLength + ",��ǰ�Ѵ���" + pBytesRead);
	    		 }
	    	 });
             //����ϴ��ļ�������������
	    	 //upload.setHeaderEncoding("UTF-8"); 
	    	 upload.setHeaderEncoding("GBK");
	    	 //3���ж��ύ�����������Ƿ����ϴ���������
	    	 if(!ServletFileUpload.isMultipartContent(request)){
	    		 //���մ�ͳ��ʽ��ȡ����
	    		 return;
	    	 }
	    	 //�����ϴ������ļ��Ĵ�С�����ֵΪ20MB
	    	 upload.setFileSizeMax(1024*1024*20);
	    	 //�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ200MB
	    	 upload.setSizeMax(1024*1024*20*10);
	    	 //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
	    	 @SuppressWarnings("unchecked")
			 List<FileItem> list = upload.parseRequest(request);
	    	 for(FileItem item : list){
	    		 if(item.isFormField()){//���item�з�װ������ͨ�����������
	    			 String name = item.getFieldName();
	    			 //�����ͨ����������ݵ�������������
	    			 //String value = item.getString("UTF-8");
	    			 String value = item.getString("GBK");
	    			 log.info(name + "=" + value);
	    			 if("businessClass".equals(name)){//��ȡ������ΪbusinessClass�ı�ֵ
	    				 calssname = value;
	    			 }
	    			 if("a0201b".equals(name)){//��ȡ������ΪbusinessClass�ı�ֵ
	    				 a0201b = value;
	    				 hlist.get(0).put("a0201b",value);
	    			 }
	    		 }else{//���fileitem�з�װ�����ϴ��ļ�
	    			 //�õ��ϴ����ļ����ƣ�
	    			 String filename = item.getName();
	    			 log.info(filename);
	    			 if(filename==null || filename.trim().equals("")){
	    				 continue;
	    			 }
	    			 //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
	    			 //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
	    			 //filename = filename.substring(filename.lastIndexOf("\\")+1);
	    			 filename = filename.substring(filename.lastIndexOf(separator)+1);
	    			 //�õ��ϴ��ļ�����չ��
	    			 String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
	    			 //�����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
	    			 log.info("�ϴ����ļ�����չ���ǣ�"+fileExtName);
	    			 //��ȡitem�е��ϴ��ļ���������
	    			 InputStream in = item.getInputStream();
	    			 //�õ��ļ����������
	    			 String saveFilename = makeFileName(filename);
	    			 //�õ��ļ��ı���Ŀ¼
	    			 String realSavePath = makePath(saveFilename, savePath);
	    			 //����һ���ļ������
	    			 // out = new FileOutputStream(realSavePath + "\\" + saveFilename);
	    			 String saveFullpath = realSavePath + separator + saveFilename;
	    			 FileOutputStream out = new FileOutputStream(saveFullpath);
	    			 //����һ��������
	    			 byte buffer[] = new byte[1024];
	    			 //�ж��������е������Ƿ��Ѿ�����ı�ʶ
	    			 int len = 0;
	    			 //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
	    			 while((len=in.read(buffer))>0){
	    				 //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
	    				 out.write(buffer, 0, len);
	    			 }
	    			 //�ر�������
	    			 in.close();
	    			 //�ر������
	    			 out.close();
	    			 //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
	    			 //item.delete();
	    			 message = "�ļ��ϴ��ɹ���";
	    			 //������·������Ϣ������hlist��
	    			 HashMap<String,Object> map = new HashMap<String,Object>();
	    			 map.put("attachmenttype", 0);
	    			 map.put("attachmentname", saveFilename);
	    			 map.put("attachmentdiaplayname",filename);
	    			 map.put("attachmentbyte", item.getSize());
	    			 map.put("attachmenttime", DateUtil.now());
	    			 map.put("attachmentadds", saveFullpath);
	    			 map.put("a0201b", a0201b);
	    			 map.put("a0000", a0000);
	    			 hlist.add(map);
	    		 }
	    	 }
	     }catch (FileUploadBase.FileSizeLimitExceededException e){
	    	 message = "�����ļ�������������ֵ20M";
	     }catch (FileUploadBase.SizeLimitExceededException e) {
	    	 message = "һ���ϴ��ļ����ܵĴ�С�������Ƶ����ֵ200M";
	    	 //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     }catch (Exception e) {
	    	 message= "�ļ��ϴ�ʧ��"+e.getMessage();
	     }
	     request.setAttribute("message",message);
	     //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     //���������������
	     hlist.get(0).put("method", method);
	     try {
			IAfterFileUpload I = (IAfterFileUpload)Class.forName(calssname).newInstance();
			Object resp = I.DoSomethingElse(hlist);
			if(resp!=null){
				localPrintWriter.write(resp.toString());
				return;
			}
		}  catch (Exception e) {
			e.printStackTrace();
			localPrintWriter.write("parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');"); 
			localPrintWriter.close();
			return;
		}
	    localPrintWriter.write("parent.closeWin();");
	    localPrintWriter.write("parent.Ext.Msg.hide();");
	    localPrintWriter.write("parent.odin.alert('����ɹ�');");
	    localPrintWriter.write("parent.reloadGrid();"); 
	    localPrintWriter.close();
	 }
	 /****************************
	  * �����ϴ��ļ��� = UUID+"_"+�ļ���ԭʼ����
	  * @param filename ԭʼ�ļ���
	  * @return
	  */
	 private String makeFileName(String filename){
		 //Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		 return UUID.randomUUID().toString() + "_" + filename;
	 }
	/****************************
	 * Ϊ��ֹһ��Ŀ¼�������̫���ļ���Ҫʹ��hash�㷨��ɢ�洢
	 * @param filename �ļ���
	 * @param savePath �洢·��
	 * @return	�µĴ洢Ŀ¼
	 */
	private String makePath(String filename,String savePath){
		//�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4;  //0-15
		//�����µı���Ŀ¼
		//String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
		String dir = savePath  + dir1 + separator + dir2;  //upload\2\3  upload\3\5
		//File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		//���Ŀ¼������
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
