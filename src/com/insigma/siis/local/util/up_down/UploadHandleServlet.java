package com.insigma.siis.local.util.up_down;

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
/****************
 * 文件上传处理类
 * @author lee
 *
 */
@SuppressWarnings("serial")
public class UploadHandleServlet extends HttpServlet {
     private static Logger log = Logger.getLogger(UploadHandleServlet.class); 
	 private static final String separator =System.getProperty("file.separator");
	 private static final String sys =System.getProperty("os.name").toUpperCase();
	 private static final String UPLODA_PATH = GlobalNames.sysConfig.get("UPLODA_PATH"); 
	 private static final String UPLODA_PATH_WIN = GlobalNames.sysConfig.get("UPLODA_PATH_WIN"); 
	 public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 response.setContentType("text/html");
		 response.setCharacterEncoding("GBK");
		 PrintWriter localPrintWriter = response.getWriter();
		 List<HashMap<String,Object>>  hlist = new ArrayList<HashMap<String,Object>>();
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
	    	 upload.setFileSizeMax(1024*1024*20);
	    	 //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为200MB
	    	 upload.setSizeMax(1024*1024*20*10);
	    	 //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	    	 @SuppressWarnings("unchecked")
			 List<FileItem> list = upload.parseRequest(request);
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
	    			 String saveFullpath = realSavePath + separator + saveFilename;
	    			 FileOutputStream out = new FileOutputStream(saveFullpath);
	    			 //创建一个缓冲区
	    			 byte buffer[] = new byte[1024];
	    			 //判断输入流中的数据是否已经读完的标识
	    			 int len = 0;
	    			 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	    			 while((len=in.read(buffer))>0){
	    				 //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
	    				 out.write(buffer, 0, len);
	    			 }
	    			 //关闭输入流
	    			 in.close();
	    			 //关闭输出流
	    			 out.close();
	    			 //删除处理文件上传时生成的临时文件
	    			 //item.delete();
	    			 message = "文件上传成功！";
	    			 //将保存路径等信息保存在hlist中
	    			 HashMap<String,Object> map = new HashMap<String,Object>();
	    			 map.put("attachmenttype", 0);
	    			 map.put("attachmentname", saveFilename);
	    			 map.put("attachmentdiaplayname",filename);
	    			 map.put("attachmentbyte", item.getSize());
	    			 map.put("attachmenttime", DateUtil.now());
	    			 map.put("attachmentadds", saveFullpath);
	    			 hlist.add(map);
	    		 }
	    	 }
	     }catch (FileUploadBase.FileSizeLimitExceededException e){
	    	 message = "单个文件不允许超过上限值20M";
	     }catch (FileUploadBase.SizeLimitExceededException e) {
	    	 message = "一次上传文件的总的大小超出限制的最大值200M";
	    	 //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     }catch (Exception e) {
	    	 message= "文件上传失败"+e.getMessage();
	     }
	     request.setAttribute("message",message);
	     //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     //添加其他后续处理
	     try {
			IAfterFileUpload I = (IAfterFileUpload)Class.forName(calssname).newInstance();
			I.DoSomethingElse(hlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 localPrintWriter.println("<script>parent.parent.alert('异常信息："+e.getMessage()+"');</script>"); 
		}
	    localPrintWriter.println("<script>parent.parent.reload();</script>"); 
//	    localPrintWriter.println("<script>parent.info('" + request.getAttribute("message")+ "');</script>");
//	    JSONArray jsonarray = JSONArray.fromObject(hlist);
//	    localPrintWriter.println("<script>parent.setGridvalueList(" + jsonarray+ ");</script>");
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
}
