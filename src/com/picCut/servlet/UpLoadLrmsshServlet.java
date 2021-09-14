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
 * 文件上传处理类
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
		 String calssname ="IAfterFileUpload";//默认接口
		 //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
	     String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	     //上传时生成的临时文件保存目录
	     String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	     //获取操作系统名称
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
	    	 //创建临时目录
	    	 tmpFile.mkdir();
	     }
	     //消息提示
	     String message = "";
	     try{
	    	 //使用Apache文件上传组件处理文件上传步骤：
	    	 //1、创建一个DiskFileItemFactory工厂
	    	 DiskFileItemFactory factory = new DiskFileItemFactory();
	    	 //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
	    	//设置缓冲区的大小为20M，如果不指定，那么缓冲区的大小默认是10KB
	    	 factory.setSizeThreshold(1024*1024*20);
	    	 //设置上传时生成的临时文件的保存目录
	    	 factory.setRepository(tmpFile);
	    	 //2、创建一个文件上传解析器
	    	 ServletFileUpload upload = new ServletFileUpload(factory);
	    	 //监听文件上传进度
	    	 upload.setProgressListener(new ProgressListener(){
	    		 public void update(long pBytesRead, long pContentLength, int arg2) {
	    			 log.info("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
	    		 }
	    	 });
             //解决上传文件名的中文乱码
	    	 //upload.setHeaderEncoding("UTF-8"); 
	    	 upload.setHeaderEncoding("GBK");
	    	 //3、判断提交上来的数据是否是上传表单的数据
	    	 if(!ServletFileUpload.isMultipartContent(request)){
	    		 //按照传统方式获取数据
	    		 return;
	    	 }
	    	 //设置上传单个文件的大小的最大值为20MB
	    	 upload.setFileSizeMax(1024*1024*60);
	    	 //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为200MB
	    	 upload.setSizeMax(1024*1024*20*10);
	    	 //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	    	 @SuppressWarnings("unchecked")
			 List<FileItem> list = upload.parseRequest(request);
	    	 
	    	 //存放a0000与姓名的map
	    	 Map<String, String> a0101map = new HashMap<String, String>();
	    	 
	    	 for(FileItem item : list){
	    		 if(item.isFormField()){//如果item中封装的是普通输入项的数据
	    			 String name = item.getFieldName();
	    			 //解决普通输入项的数据的中文乱码问题
	    			 //String value = item.getString("UTF-8");
	    			 String value = item.getString("GBK");
	    			 log.info(name + "=" + value);
	    			 if("businessClass".equals(name)){//获取表单属性为businessClass的表单值
	    				 calssname = value;
	    			 }
	    			 if("a0201b".equals(name)){//获取表单属性为businessClass的表单值
	    				 a0201b = value;
	    				 hlist.get(0).put("a0201b",value);
	    			 }
	    		 }else{//如果fileitem中封装的是上传文件
	    			 //得到上传的文件名称，
	    			 String filename = item.getName();
	    			 log.info(filename);
	    			 if(filename==null || filename.trim().equals("")){
	    				 continue;
	    			 }
	    			 //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
	    			 //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
	    			 //filename = filename.substring(filename.lastIndexOf("\\")+1);
	    			 filename = filename.substring(filename.lastIndexOf(separator)+1);
	    			 //得到上传文件的扩展名
	    			 String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
	    			 //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
	    			 log.info("上传的文件的扩展名是："+fileExtName);
	    			 //获取item中的上传文件的输入流
	    			 InputStream in = item.getInputStream();
	    			 //得到文件保存的名称
	    			 String saveFilename = makeFileName(filename);
	    			 //得到文件的保存目录
	    			 String realSavePath = makePath(saveFilename, savePath);
	    			 //创建一个文件输出流
	    			 // out = new FileOutputStream(realSavePath + "\\" + saveFilename);
	    			 if("zip".equals(fileExtName)){
	    				 //批量导入
		    			 String unzippath = realSavePath+separator+UUID.randomUUID();
						 int len = 0;
						 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
						 byte buffer1[] = new byte[1024];
						 FileOutputStream out1 = new FileOutputStream(realSavePath + separator + filename);
						 while((len=in.read(buffer1))>0){
							 //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
							 out1.write(buffer1, 0, len);
						 }
						 out1.close();
						 in.close();
						 
//	    				 SevenZipUtil.unzipDir(realSavePath + separator + filename, unzippath);
						  decompress(realSavePath + separator + filename, unzippath, "ISO8859-1");
						  //decompress(realSavePath + separator + filename, unzippath, "UTF-8");
	    				  File flist[] = new File(unzippath).listFiles();
	    				  
	    				  if (flist == null || flist.length == 0) {
	    				      throw new IOException("获取解压文件异常！");
	    				  }
	    				  
	    				  if(flist.length == 1 && flist[0].isDirectory()){
							  flist = flist[0].listFiles();
	    					  //throw new IOException("压缩包内不能包含文件夹，请将各文件直接打包成ZIP数据包后再导入！");
						  }
	    				  for (File f : flist) {
	    					  //String fName = f.getName().substring(f.getName().length() - 4);   //截取获得文件类型
	    					  /*String name = f.getName();
	    					  String gbkname = new String(name.getBytes("ISO8859-1"),"GBK");
	    					  String utf8name = new String(name.getBytes("ISO8859-1"),"UTF-8");
	    					  
	    					  if(gbkname.length()<=utf8name.length()){
	    						  name = gbkname;
	    					  }else{
	    						  name = utf8name;
	    					  }*/
	    					  
	    					  String fName = f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length());
	    					  //只支持lrmx、lrm
	    					  
	    					  if(fName == null || !(fName.equals("lrmx") || fName.equals("lrm") || fName.equals("pic"))){
	    						  throw new IOException("压缩的文件仅支持lrmx或者lrm导入！");
	    					  }
	    					  
	    				      if (f.isDirectory()) {
	    				          //getDirectory(f);
	    				      } else {
	    				          System.out.println("file==>" + f.getAbsolutePath());
//	    				          String saveFullpath = realSavePath + separator + saveFilename;
	    				          String saveFullpath = f.getAbsolutePath();
	/*    							 FileOutputStream out = new FileOutputStream(saveFullpath.replace("\\", "\\\\"));
	    							 //创建一个缓冲区
	    							 byte buffer[] = new byte[1024];
	    							 //判断输入流中的数据是否已经读完的标识
	    							  len = 0;
	    							 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	    							 while((len=in.read(buffer))>0){
	    								 //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
	    								 out.write(buffer, 0, len);
	    							 }*/

	    							 //关闭输出流
	 //   							 out.close();
	    							 //删除处理文件上传时生成的临时文件
	    							 //item.delete();
	    							  message = "文件上传成功！";
	    							 //将保存路径等信息保存在hlist中
	    							//获取人员姓名
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
	    					     //添加其他后续处理
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
	    							localPrintWriter.write("parent.odin.alert('异常信息："+e.getMessage()+"');"); 
	    							return;
	    						}

	    				      }
	    				  }
	    			 }else if("lrmx".equals(fileExtName)){
	    				 //单个导入
		    			 String unzippath = realSavePath+separator+UUID.randomUUID();
						 int len = 0;
						 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
						 byte buffer1[] = new byte[1024];
						 FileOutputStream out1 = new FileOutputStream(realSavePath + separator + filename);
						 while((len=in.read(buffer1))>0){
							 //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
							 out1.write(buffer1, 0, len);
						 }
						 out1.close();
						 in.close();
						 File flist[] = new File(realSavePath).listFiles();
	    				  if (flist == null || flist.length == 0) {
	    				      throw new IOException("获取文件异常！");
	    				  }
	    				  
	    				  if(flist.length == 1 && flist[0].isDirectory()){
							  flist = flist[0].listFiles();
	    					  //throw new IOException("压缩包内不能包含文件夹，请将各文件直接打包成ZIP数据包后再导入！");
						  }
	    				  for (File f : flist) {
	    					  //String fName = f.getName().substring(f.getName().length() - 4);   //截取获得文件类型
	    					  /*String name = f.getName();
	    					  String gbkname = new String(name.getBytes("ISO8859-1"),"GBK");
	    					  String utf8name = new String(name.getBytes("ISO8859-1"),"UTF-8");
	    					  
	    					  if(gbkname.length()<=utf8name.length()){
	    						  name = gbkname;
	    					  }else{
	    						  name = utf8name;
	    					  }*/
	    					  
	    					  String fName = f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length());
	    					  //只支持lrmx、lrm
	    					  
	    					  if(fName == null || !(fName.equals("lrmx") || fName.equals("lrm") || fName.equals("pic"))){
	    						  throw new IOException("压缩的文件仅支持lrmx或者lrm导入！");
	    					  }
	    					  
	    				      if (f.isDirectory()) {
	    				          //getDirectory(f);
	    				      } else {
	    				          System.out.println("file==>" + f.getAbsolutePath());
//	    				          String saveFullpath = realSavePath + separator + saveFilename;
	    				          String saveFullpath = f.getAbsolutePath();
	/*    							 FileOutputStream out = new FileOutputStream(saveFullpath.replace("\\", "\\\\"));
	    							 //创建一个缓冲区
	    							 byte buffer[] = new byte[1024];
	    							 //判断输入流中的数据是否已经读完的标识
	    							  len = 0;
	    							 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	    							 while((len=in.read(buffer))>0){
	    								 //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
	    								 out.write(buffer, 0, len);
	    							 }*/

	    							 //关闭输出流
	 //   							 out.close();
	    							 //删除处理文件上传时生成的临时文件
	    							 //item.delete();
	    							  message = "文件上传成功！";
	    							 //将保存路径等信息保存在hlist中
	    							//获取人员姓名
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
	    					     //添加其他后续处理
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
	    							localPrintWriter.write("parent.odin.alert('异常信息："+e.getMessage()+"');"); 
	    							return;
	    						}

	    				      }
	    				  }
	    			 }

/*						 //关闭输入流
						 in.close();*/
    				    localPrintWriter.write("parent.odin.ext.getCmp('importLrmWins').hide();"); 
    				    localPrintWriter.write("parent.Ext.Msg.hide();");
					    localPrintWriter.write("parent.odin.alert('导入成功');");
					    localPrintWriter.write("parent.reloadGrid();");
	    		 }
	    	 }
	     }catch (FileUploadBase.FileSizeLimitExceededException e){
	    	 message = "单个文件不允许超过上限值20M";
	    	 localPrintWriter.write("Ext.Msg.hide();parent.odin.alert('异常信息："+e.getMessage()+"');"); 
	     }catch (FileUploadBase.SizeLimitExceededException e) {
	    	 message = "一次上传文件的总的大小超出限制的最大值200M";
	    	 localPrintWriter.write("Ext.Msg.hide();parent.odin.alert('异常信息："+e.getMessage()+"');"); 
	    	 //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     }catch (Exception e) {
	    	 message= "文件上传失败"+e.getMessage();
	    	 localPrintWriter.write("Ext.Msg.hide();parent.odin.alert('异常信息："+e.getMessage()+"');"); 
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
	  * 生成上传文件名 = UUID+"_"+文件的原始名称
	  * @param filename 原始文件名
	  * @return
	  */
	 private String makeFileName(String filename){
		 //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		 return UUID.randomUUID().toString() + "_" + filename;
	 }
	/****************************
	 * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * @param filename 文件名
	 * @param savePath 存储路径
	 * @return	新的存储目录
	 */
	private String makePath(String filename,String savePath){
		//得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4;  //0-15
		//构造新的保存目录
		//String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
		String dir = savePath  + dir1 + separator + dir2;  //upload\2\3  upload\3\5
		//File既可以代表文件也可以代表目录
		File file = new File(dir);
		//如果目录不存在
		if(!file.exists()){
			//创建目录
			file.mkdirs();
		}
		return dir;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	  * @Description: 
	  * 	解压文件
	  * @param zipPath 被压缩文件，请使用绝对路径
	  * @param targetPath 解压路径，解压后的文件将会放入此目录中，请使用绝对路径
	  * 		默认为压缩文件的路径的父目录为解压路径
	  * @param encoding 解压编码
	 */
	public static void decompress(String zipPath, String targetPath, String encoding)
			throws FileNotFoundException, ZipException, IOException {
		// 获取解缩文件
		File file = new File(zipPath);
		if (!file.isFile()) {
			throw new FileNotFoundException("要解压的文件不存在");
		}
		// 设置解压路径
		if (targetPath == null || "".equals(targetPath)) {
			targetPath = file.getParent();
		}
		// 设置解压编码
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		// 实例化ZipFile对象
		ZipFile zipFile = new ZipFile(file, encoding);
		// 获取ZipFile中的条目
		Enumeration<ZipEntry> files = zipFile.getEntries();
		// 迭代中的每一个条目
		ZipEntry entry = null;
		// 解压后的文件
		File outFile = null;
		// 读取压缩文件的输入流
		BufferedInputStream bin = null;
		// 写入解压后文件的输出流
		BufferedOutputStream bout = null;
		while (files.hasMoreElements()) {
			// 获取解压条目
			entry = files.nextElement();
			// 实例化解压后文件对象
			outFile = new File(targetPath + File.separator + entry.getName());
			// 如果条目为目录，则跳向下一个
			if (entry.isDirectory()) {
				//outFile.mkdirs();
				continue;
			}
			// 创建目录
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			// 创建新文件
			outFile.createNewFile();
			// 如果不可写，则跳向下一个条目
			if (!outFile.canWrite()) {
				continue;
			}
			try {
				// 获取读取条目的输入流
				bin = new BufferedInputStream(zipFile.getInputStream(entry));
				// 获取解压后文件的输出流
				bout = new BufferedOutputStream(new FileOutputStream(outFile));
				// 读取条目，并写入解压后文件
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
