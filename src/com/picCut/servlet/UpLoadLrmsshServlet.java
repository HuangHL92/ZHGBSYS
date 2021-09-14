package com.picCut.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipException;

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
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.insigma.odin.framework.radow.util.DateUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.ZipUtil;
import com.insigma.siis.local.util.up_down.IAfterFileUpload;
/****************
 * �ļ��ϴ�������
 * @author lee
 *
 */
@SuppressWarnings("serial")
public class UpLoadLrmsshServlet extends HttpServlet {
     private static Logger log = Logger.getLogger(UpLoadLrmsshServlet.class); 
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
	    	 upload.setFileSizeMax(1024*1024*60);
	    	 //�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ200MB
	    	 upload.setSizeMax(1024*1024*20*10);
	    	 //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
	    	 @SuppressWarnings("unchecked")
			 List<FileItem> list = upload.parseRequest(request);
	    	 
	    	 //���a0000��������map
	    	 Map<String, String> a0101map = new HashMap<String, String>();
	    	 
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
	    			 if("zip".equals(fileExtName)){
	    				 //��������
		    			 String unzippath = realSavePath+separator+UUID.randomUUID();
						 int len = 0;
						 //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
						 byte buffer1[] = new byte[1024];
						 FileOutputStream out1 = new FileOutputStream(realSavePath + separator + filename);
						 while((len=in.read(buffer1))>0){
							 //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
							 out1.write(buffer1, 0, len);
						 }
						 out1.close();
						 in.close();
						 
//	    				 SevenZipUtil.unzipDir(realSavePath + separator + filename, unzippath);
						  decompress(realSavePath + separator + filename, unzippath, "ISO8859-1");
						  //decompress(realSavePath + separator + filename, unzippath, "UTF-8");
	    				  File flist[] = new File(unzippath).listFiles();
	    				  
	    				  if (flist == null || flist.length == 0) {
	    				      throw new IOException("��ȡ��ѹ�ļ��쳣��");
	    				  }
	    				  
	    				  if(flist.length == 1 && flist[0].isDirectory()){
							  flist = flist[0].listFiles();
	    					  //throw new IOException("ѹ�����ڲ��ܰ����ļ��У��뽫���ļ�ֱ�Ӵ����ZIP���ݰ����ٵ��룡");
						  }
	    				  for (File f : flist) {
	    					  //String fName = f.getName().substring(f.getName().length() - 4);   //��ȡ����ļ�����
	    					  /*String name = f.getName();
	    					  String gbkname = new String(name.getBytes("ISO8859-1"),"GBK");
	    					  String utf8name = new String(name.getBytes("ISO8859-1"),"UTF-8");
	    					  
	    					  if(gbkname.length()<=utf8name.length()){
	    						  name = gbkname;
	    					  }else{
	    						  name = utf8name;
	    					  }*/
	    					  
	    					  String fName = f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length());
	    					  //ֻ֧��lrmx��lrm
	    					  
	    					  if(fName == null || !(fName.equals("lrmx") || fName.equals("lrm") || fName.equals("pic"))){
	    						  throw new IOException("ѹ�����ļ���֧��lrmx����lrm���룡");
	    					  }
	    					  
	    				      if (f.isDirectory()) {
	    				          //getDirectory(f);
	    				      } else {
	    				          System.out.println("file==>" + f.getAbsolutePath());
//	    				          String saveFullpath = realSavePath + separator + saveFilename;
	    				          String saveFullpath = f.getAbsolutePath();
	/*    							 FileOutputStream out = new FileOutputStream(saveFullpath.replace("\\", "\\\\"));
	    							 //����һ��������
	    							 byte buffer[] = new byte[1024];
	    							 //�ж��������е������Ƿ��Ѿ�����ı�ʶ
	    							  len = 0;
	    							 //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
	    							 while((len=in.read(buffer))>0){
	    								 //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
	    								 out.write(buffer, 0, len);
	    							 }*/

	    							 //�ر������
	 //   							 out.close();
	    							 //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
	    							 //item.delete();
	    							  message = "�ļ��ϴ��ɹ���";
	    							 //������·������Ϣ������hlist��
	    							//��ȡ��Ա����
	      							 String beforeName = f.getName().substring(0,f.getName().lastIndexOf(".")).trim();
	      							 String publish_id = request.getParameter("publish_id");
	      							 String title_id = request.getParameter("title_id");
	    							 HashMap<String,Object> map = new HashMap<String,Object>();
	   								 map.put("attachmenttype", 0);
	   								 map.put("attachmentname", saveFilename);
	   								 map.put("attachmentdiaplayname",filename);
	   								 map.put("attachmentbyte", item.getSize());
	   								 map.put("attachmenttime", DateUtil.now());
	   								 map.put("attachmentadds", saveFullpath.replace("\\", "\\\\"));
	   								 map.put("a0201b", request.getParameter("a0201b"));
	   								 map.put("title_id", request.getParameter("title_id"));
	   								 map.put("publish_id", request.getParameter("publish_id"));
	     							 if(fName.equals("pic")){
	     								 String s = a0101map.get(beforeName);
	     								 map.put("a0000", a0101map.get(beforeName));
	     								 //map.put("a0101" , beforeName.trim());
	     							 }else{
	     								String a0000lrm = UUID.randomUUID().toString();
	     								map.put("a0000",a0000lrm);
	     								
	        							a0101map.put(beforeName, a0000lrm);
	     							 }
	     							hlist.add(map);

	    					     request.setAttribute("message",message);
	    					     //request.getRequestDispatcher("/message.jsp").forward(request, response);
	    					     //���������������
	    					     hlist.get(0).put("method", method);
	    					     try {
	    							IAfterFileUpload I = (IAfterFileUpload)Class.forName("com.picCut.servlet.SaveLrmsshFile").newInstance();
	    							Object resp = I.DoSomethingElse(hlist);
	    							if("".equals(publish_id)||"".equals(title_id)||"null".equals(publish_id)||"null".equals(title_id)) {
	    								StringBuilder a0000sb = new StringBuilder();
	    								for(HashMap<String,Object> m : hlist){
	    									a0000sb.append(m.get("a0000")).append(",");
	    								}
	    								
	    								if(a0000sb.length()>0){
	    									a0000sb.deleteCharAt(a0000sb.length()-1);
	    									localPrintWriter.write("parent.Ext.getCmp('importLrmWinmntp').initialConfig.thisWin.radow.doEvent('queryByNameAndIDS_ZCQ','"+a0000sb+"');");
	        								return;
	    								}
	    								
	    							}
									
	    							/*if(resp!=null){
	    								localPrintWriter.write(resp.toString());
	    								return;
	    							}*/
	    						}  catch (Exception e) {
	    							e.printStackTrace();
	    							localPrintWriter.write("parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');"); 
	    							return;
	    						}

	    				      }
	    				  }
	    			 }else if("lrmx".equals(fileExtName)){
	    				 //��������
		    			 String unzippath = realSavePath+separator+UUID.randomUUID();
						 int len = 0;
						 //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
						 byte buffer1[] = new byte[1024];
						 FileOutputStream out1 = new FileOutputStream(realSavePath + separator + filename);
						 while((len=in.read(buffer1))>0){
							 //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
							 out1.write(buffer1, 0, len);
						 }
						 out1.close();
						 in.close();
						 File flist[] = new File(realSavePath).listFiles();
	    				  if (flist == null || flist.length == 0) {
	    				      throw new IOException("��ȡ�ļ��쳣��");
	    				  }
	    				  
	    				  if(flist.length == 1 && flist[0].isDirectory()){
							  flist = flist[0].listFiles();
	    					  //throw new IOException("ѹ�����ڲ��ܰ����ļ��У��뽫���ļ�ֱ�Ӵ����ZIP���ݰ����ٵ��룡");
						  }
	    				  for (File f : flist) {
	    					  //String fName = f.getName().substring(f.getName().length() - 4);   //��ȡ����ļ�����
	    					  /*String name = f.getName();
	    					  String gbkname = new String(name.getBytes("ISO8859-1"),"GBK");
	    					  String utf8name = new String(name.getBytes("ISO8859-1"),"UTF-8");
	    					  
	    					  if(gbkname.length()<=utf8name.length()){
	    						  name = gbkname;
	    					  }else{
	    						  name = utf8name;
	    					  }*/
	    					  
	    					  String fName = f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length());
	    					  //ֻ֧��lrmx��lrm
	    					  
	    					  if(fName == null || !(fName.equals("lrmx") || fName.equals("lrm") || fName.equals("pic"))){
	    						  throw new IOException("ѹ�����ļ���֧��lrmx����lrm���룡");
	    					  }
	    					  
	    				      if (f.isDirectory()) {
	    				          //getDirectory(f);
	    				      } else {
	    				          System.out.println("file==>" + f.getAbsolutePath());
//	    				          String saveFullpath = realSavePath + separator + saveFilename;
	    				          String saveFullpath = f.getAbsolutePath();
	/*    							 FileOutputStream out = new FileOutputStream(saveFullpath.replace("\\", "\\\\"));
	    							 //����һ��������
	    							 byte buffer[] = new byte[1024];
	    							 //�ж��������е������Ƿ��Ѿ�����ı�ʶ
	    							  len = 0;
	    							 //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
	    							 while((len=in.read(buffer))>0){
	    								 //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
	    								 out.write(buffer, 0, len);
	    							 }*/

	    							 //�ر������
	 //   							 out.close();
	    							 //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
	    							 //item.delete();
	    							  message = "�ļ��ϴ��ɹ���";
	    							 //������·������Ϣ������hlist��
	    							//��ȡ��Ա����
	      							 String beforeName = f.getName().substring(0,f.getName().lastIndexOf(".")).trim();
	      							 String publish_id = request.getParameter("publish_id");
	      							 String title_id = request.getParameter("title_id");
	    							 HashMap<String,Object> map = new HashMap<String,Object>();
	   								 map.put("attachmenttype", 0);
	   								 map.put("attachmentname", saveFilename);
	   								 map.put("attachmentdiaplayname",filename);
	   								 map.put("attachmentbyte", item.getSize());
	   								 map.put("attachmenttime", DateUtil.now());
	   								 map.put("attachmentadds", saveFullpath.replace("\\", "\\\\"));
	   								 map.put("a0201b", request.getParameter("a0201b"));
	   								 map.put("title_id", request.getParameter("title_id"));
	   								 map.put("publish_id", request.getParameter("publish_id"));
	     							 if(fName.equals("pic")){
	     								 String s = a0101map.get(beforeName);
	     								 map.put("a0000", a0101map.get(beforeName));
	     								 //map.put("a0101" , beforeName.trim());
	     							 }else{
	     								String a0000lrm = UUID.randomUUID().toString();
	     								map.put("a0000",a0000lrm);
	     								
	        							a0101map.put(beforeName, a0000lrm);
	     							 }
	     							hlist.add(map);

	    					     request.setAttribute("message",message);
	    					     //request.getRequestDispatcher("/message.jsp").forward(request, response);
	    					     //���������������
	    					     hlist.get(0).put("method", method);
	    					     try {
	    							IAfterFileUpload I = (IAfterFileUpload)Class.forName("com.picCut.servlet.SaveLrmsshFile").newInstance();
	    							Object resp = I.DoSomethingElse(hlist);
	    							if("".equals(publish_id)||"".equals(title_id)||"null".equals(publish_id)||"null".equals(title_id)) {
	    								StringBuilder a0000sb = new StringBuilder();
	    								for(HashMap<String,Object> m : hlist){
	    									a0000sb.append(m.get("a0000")).append(",");
	    								}
	    								
	    								if(a0000sb.length()>0){
	    									a0000sb.deleteCharAt(a0000sb.length()-1);
	    									localPrintWriter.write("parent.Ext.getCmp('importLrmWinmntp').initialConfig.thisWin.radow.doEvent('queryByNameAndIDS_ZCQ','"+a0000sb+"');");
	        								return;
	    								}
	    								
	    							}
									
	    							/*if(resp!=null){
	    								localPrintWriter.write(resp.toString());
	    								return;
	    							}*/
	    						}  catch (Exception e) {
	    							e.printStackTrace();
	    							localPrintWriter.write("parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');"); 
	    							return;
	    						}

	    				      }
	    				  }
	    			 }

/*						 //�ر�������
						 in.close();*/
    				    localPrintWriter.write("parent.odin.ext.getCmp('importLrmWins').hide();"); 
    				    localPrintWriter.write("parent.Ext.Msg.hide();");
					    localPrintWriter.write("parent.odin.alert('����ɹ�');");
					    localPrintWriter.write("parent.reloadGrid();");
	    		 }
	    	 }
	     }catch (FileUploadBase.FileSizeLimitExceededException e){
	    	 message = "�����ļ�������������ֵ20M";
	    	 localPrintWriter.write("Ext.Msg.hide();parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');"); 
	     }catch (FileUploadBase.SizeLimitExceededException e) {
	    	 message = "һ���ϴ��ļ����ܵĴ�С�������Ƶ����ֵ200M";
	    	 localPrintWriter.write("Ext.Msg.hide();parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');"); 
	    	 //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     }catch (Exception e) {
	    	 message= "�ļ��ϴ�ʧ��"+e.getMessage();
	    	 localPrintWriter.write("Ext.Msg.hide();parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');"); 
	     }finally{
	    	 if(localPrintWriter!=null){
	    		 try {
	    			 localPrintWriter.close();
				} catch (Exception e2) {
				}
	    	 }
	     }
	     
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
	
	/**
	  * @Description: 
	  * 	��ѹ�ļ�
	  * @param zipPath ��ѹ���ļ�����ʹ�þ���·��
	  * @param targetPath ��ѹ·������ѹ����ļ���������Ŀ¼�У���ʹ�þ���·��
	  * 		Ĭ��Ϊѹ���ļ���·���ĸ�Ŀ¼Ϊ��ѹ·��
	  * @param encoding ��ѹ����
	 */
	public static void decompress(String zipPath, String targetPath, String encoding)
			throws FileNotFoundException, ZipException, IOException {
		// ��ȡ�����ļ�
		File file = new File(zipPath);
		if (!file.isFile()) {
			throw new FileNotFoundException("Ҫ��ѹ���ļ�������");
		}
		// ���ý�ѹ·��
		if (targetPath == null || "".equals(targetPath)) {
			targetPath = file.getParent();
		}
		// ���ý�ѹ����
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		// ʵ����ZipFile����
		ZipFile zipFile = new ZipFile(file, encoding);
		// ��ȡZipFile�е���Ŀ
		Enumeration<ZipEntry> files = zipFile.getEntries();
		// �����е�ÿһ����Ŀ
		ZipEntry entry = null;
		// ��ѹ����ļ�
		File outFile = null;
		// ��ȡѹ���ļ���������
		BufferedInputStream bin = null;
		// д���ѹ���ļ��������
		BufferedOutputStream bout = null;
		while (files.hasMoreElements()) {
			// ��ȡ��ѹ��Ŀ
			entry = files.nextElement();
			// ʵ������ѹ���ļ�����
			outFile = new File(targetPath + File.separator + entry.getName());
			// �����ĿΪĿ¼����������һ��
			if (entry.isDirectory()) {
				//outFile.mkdirs();
				continue;
			}
			// ����Ŀ¼
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			// �������ļ�
			outFile.createNewFile();
			// �������д����������һ����Ŀ
			if (!outFile.canWrite()) {
				continue;
			}
			try {
				// ��ȡ��ȡ��Ŀ��������
				bin = new BufferedInputStream(zipFile.getInputStream(entry));
				// ��ȡ��ѹ���ļ��������
				bout = new BufferedOutputStream(new FileOutputStream(outFile));
				// ��ȡ��Ŀ����д���ѹ���ļ�
				byte[] buffer = new byte[1024];
				int readCount = -1;
				while ((readCount = bin.read(buffer)) != -1) {
					bout.write(buffer, 0, readCount);
				}
			} finally {
				try {
					bin.close();
					bout.flush();
					bout.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
