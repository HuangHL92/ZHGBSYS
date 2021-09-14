package com.picCut.servlet;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.siis.local.epsoft.config.AppConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;






import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A57history;
import com.insigma.siis.local.business.entity.PhotoupError;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.DataPadImpDBThread;
import com.insigma.siis.local.util.up_down.IAfterFileUpload;
import com.picCut.teetaa.util.ImageHepler;

@SuppressWarnings("serial")
public class UpLoadPhotoServlet extends HttpServlet{
	private static final String separator =System.getProperty("file.separator");
	private static final String sys =System.getProperty("os.name").toUpperCase();
	private String sPath = "picCut/UploadPhoto";
	private static final String UPLODA_PATH = GlobalNames.sysConfig.get("UPLODA_PATH");
	private static  String UPLODA_PATH_WIN = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String photodate = request.getParameter("photodate");
		UPLODA_PATH_WIN = AppConfig.LOCAL_FILE_BASEURL;
		HBSession sess = HBUtil.getHBSession();
		response.setCharacterEncoding("GBK");
		PrintWriter localPrintWriter = response.getWriter();
		String user = SysManagerUtils.getUserloginName();
		//获取系统当前时间
		String now = DateUtil.getcurdate();
		//上传时生成的临时文件保存目录
		//String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	     //上传时生成的临时文件保存目录
	    String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	    if(sys.startsWith("WIN")){
	    	tempPath = UPLODA_PATH_WIN + "temp"+UUID.randomUUID();
	     }else{
	    	tempPath = UPLODA_PATH + "temp"+UUID.randomUUID();
	     }
	    File tmpFile = new File(tempPath);
	    if (!tmpFile.exists()) {
	    	 //创建临时目录
	    	 tmpFile.mkdir();
	    }
	    //消息提示
	    String message = "";
	    String realSavePath = "";
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
	    	//解决上传文件名的中文乱码
	    	 upload.setHeaderEncoding("GBK");
	    	 //3、判断提交上来的数据是否是上传表单的数据
	    	 if(!ServletFileUpload.isMultipartContent(request)){
	    		 //按照传统方式获取数据
	    		 return;
	    	 }
	    	//设置上传单个文件的大小的最大值为2MB
	    	 //upload.setFileSizeMax(1024*1024*2);
	    	 //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为200MB
	    	 //upload.setSizeMax(1024*1024*2*100);
	    	 //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	    	 @SuppressWarnings("unchecked")
			 List<FileItem> list = upload.parseRequest(request);
	    	 for(FileItem item : list){
	    		 if(item.isFormField()){//如果item中封装的是普通输入项的数据

	    		 }else{//如果fileitem中封装的是上传文件
	    			 //得到上传的文件名称，
	    			 String filename = item.getName();
	    			 if(filename==null || filename.trim().equals("")){
	    				 continue;
	    			 }
	    			 //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
	    			 //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
	    			 filename = filename.substring(filename.lastIndexOf(separator)+1);
	    			 //得到上传文件的扩展名
	    			 String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
	    			 //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
	    			 //获取item中的上传文件的输入流
	    			 InputStream in = item.getInputStream();
	    			 //得到文件保存的名称
	    			 String saveFilename = makeFileName(filename);
	    			 //得到文件的保存目录
	    			 //realSavePath = makePath(saveFilename, tempPath);
	    			 //创建一个文件输出流
	    			 // out = new FileOutputStream(realSavePath + "\\" + saveFilename);
	    			 if("zip".equals(fileExtName)){

	    			 }
   				  sess.createSQLQuery("delete from Photoup_Error ").executeUpdate();
   				  sess.flush();

    				 //批量导入
	    			 String unzippath = tempPath;
					 int len = 0;
					 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					 byte buffer1[] = new byte[1024];
					 FileOutputStream out1 = new FileOutputStream(tempPath + separator + filename);
					 while((len=in.read(buffer1))>0){
						 out1.write(buffer1, 0, len);
					 }
					 out1.close();
					 in.close();
					 //解压上传的zip文件 "
					 String unzip=AppConfig.HZB_PATH+"/Comparison";
					 File tmpFile1 = new File(unzip);
					 if (!tmpFile1.exists()) {
						 //创建临时目录
						 tmpFile1.mkdir();
					 }

					  UpLoadLrmsServlet.decompress(tempPath + separator + filename, unzip, "GBK");
    				  File flist[] = new File(unzip).listFiles();

    				  if (flist == null || flist.length == 0) {
    				      throw new IOException("获取解压文件异常！");
    				  }

    				  if(flist.length == 1 && flist[0].isDirectory()){
						  flist = flist[0].listFiles();
					  }

    				  //记录未导入人员的数量
    				  int num = 0;
    				  for (File f : flist) {
    					  String path = f.getPath();
    					  String fName = f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length());
    					  String phName = f.getName();
    					  //phName = phName.replaceAll("[\\u4e00-\\u9fa5]", "");
    					  phName = getPersonCode(phName);
    				      List a01list = sess.createSQLQuery("select a0000 from a01 where upper(a0184) = upper('"+phName+"')").list();
    					  //只支持jpg
    					  if(fName == null || !((fName.toLowerCase()).equals("jpg"))){
    						  PhotoupError pe = new PhotoupError();
    						  pe.setPer002(phName);
    						  pe.setPer003("照片不为jpg格式");
    						  pe.setPer004(now);
    						  pe.setPer005(user);
    						  sess.save(pe);
    						  num +=1;
    						  continue;
    					  }
    					  if(a01list.size()>1){
    						  PhotoupError pe = new PhotoupError();
    						  pe.setPer002(f.getName());
    						  pe.setPer003("人员匹配重复");
    						  pe.setPer004(now);
    						  pe.setPer005(user);
    						  sess.save(pe);
    						  num +=1;
    						  continue;
    					  }
    					  if(a01list.size()==0||a01list==null){
    						  PhotoupError pe = new PhotoupError();
    						  pe.setPer002(f.getName());
    						  pe.setPer003("人员未找到");
    						  pe.setPer004(now);
    						  pe.setPer005(user);
    						  sess.save(pe);
    						  num +=1;
    						  continue;
    					  }
    					  //查询a57表中该人员的照片记录
    					  A57 a57 = (A57) sess.get(A57.class, a01list.get(0).toString());
    					  //a57中无记录，实行照片存储功能
    					  if(a57==null){
    						Rectangle rect = new Rectangle(0, 0, 272, 340);
    						int width = 272;
    						int height = 340;
    						ImageHepler.cut(path, path, width, height, rect,a01list.get(0).toString());
    						continue;
    					  }
    					  String newPhotoName = UUID.randomUUID().toString()+"."+fName;
    					  newPhotoName.replace("-", "");
    					  //将a57的记录复制到a57_history表中
    					  //String time = System.currentTimeMillis()+"";
    					  A57history a57his = new A57history();
    					  a57his.setA0000(a57.getA0000());
    					  a57his.setPhotousedate(photodate);
    					  a57his.setPhotodate(now);
    					  a57his.setPhotoname(newPhotoName);
    					  a57his.setPhotopath(a57.getPhotopath());
    					  a57his.setPhotostype(a57.getPhotstype());
    					  a57his.setA57h00(UUID.randomUUID().toString().replace("-", ""));
    					  sess.save(a57his);
    					  //将原照片更名
    					  new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname()).renameTo(new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+newPhotoName));
    					  //将上传的照片压缩
    					  try{
    						  DataPadImpDBThread.cut(path, f.getName(),path.substring(path.lastIndexOf(separator)+1, path.length()),"update");
        					 }catch(IOException e) {

							 PhotoupError pe = new PhotoupError();
							  pe.setPer002(f.getName());
							  pe.setPer003("不支持的照片类型");
       						  pe.setPer004(now);
       						  pe.setPer005(user);
       						  sess.save(pe);
       						  num +=1;
       						  e.printStackTrace();
        					 }

    					  String newPath = path.substring(0, path.lastIndexOf(separator)+1)+a57.getPhotoname();
    					  //将压缩后的照片改名为A57查询出的名字
    					  new File(path).renameTo(new File(newPath));
    					  //将压缩后的照片剪切到原先的目录中
    					  copyFile(newPath, PhotosUtil.PHOTO_PATH+a57.getPhotopath());
    					  //new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+f.getName()).renameTo(new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname()));
    				  }
    				  	sess.flush();
    				  	if(num>0){
    				  		localPrintWriter.write("parent.Ext.Msg.hide();");
    					    localPrintWriter.write("parent.odin.alert(\"导入成功，但有"+num+"人未导入，请查看错误信息\");");
    				  	}else{
    				  		localPrintWriter.write("parent.Ext.Msg.hide();");
    					    localPrintWriter.write("parent.odin.alert(\"导入成功\");");
    				  	}
    				    //localPrintWriter.write("parent.odin.ext.getCmp('importLrmWins').hide();");
    				    //localPrintWriter.write("parent.Ext.Msg.hide();");
					    //localPrintWriter.write("parent.odin.alert(\"导入成功\");");
					    //localPrintWriter.write("parent.parent.reloadGrid();");
	    		 }
	    	 }
	    }catch (FileUploadBase.FileSizeLimitExceededException e){
	    	 message = "单个文件不允许超过上限值2M";
	    	 localPrintWriter.write("parent.Ext.Msg.hide();parent.odin.alert('异常信息："+e.getMessage()+"');");
	     }catch (FileUploadBase.SizeLimitExceededException e) {
	    	 message = "一次上传文件的总的大小超出限制的最大值200M";
	    	 localPrintWriter.write("parent.Ext.Msg.hide();parent.odin.alert('异常信息："+e.getMessage()+"');");
	    	 //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     }catch (Exception e) {
	    	 e.printStackTrace();
	    	 message= "文件上传失败"+e.getMessage();
	    	 localPrintWriter.write("parent.Ext.Msg.hide();parent.odin.alert('异常信息："+e.getMessage()+"');");
	     }finally{
	    	 if(localPrintWriter!=null){
	    		 try {
	    			 localPrintWriter.close();
	    			 this.deleteDirectory(tempPath);
	    			 //this.deleteDirectory(realSavePath);
				} catch (Exception e2) {
				}
	    	 }
	     }
	}
//	public static void main(String[] args) {
//		UpLoadPhotoServlet us = new UpLoadPhotoServlet();
//		System.out.println(us.getPersonCode("陈洁32020319720827151X.jpg"));
//	}
	private String getPersonCode(String phName) {
		Pattern p = Pattern.compile("\\d{18}|\\d{17}(X|x)|\\d{15}");
		  Matcher m = p.matcher(phName);
		  //phName = phName.replaceAll("^\\d{15}|\\d{18}|\\d{17}(X|x)", "");
		  if(m.find()){
			  phName=m.group();
		  }
		return phName;
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
		  /*
         * 实现文件的剪切
         * @param srcPathStr
         *          源文件的地址信息
         * @param desPathStr
         *          目标文件的地址信息
         */
        private static void copyFile(String srcPathStr, String desPathStr) {
            //1.获取源文件的名称
            String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //目标文件地址
            System.out.println(newFileName);
            desPathStr = desPathStr + File.separator + newFileName; //源文件地址
            System.out.println(desPathStr);

            try{
                //2.创建输入输出流对象
                FileInputStream fis = new FileInputStream(srcPathStr);
                FileOutputStream fos = new FileOutputStream(desPathStr);

                //创建搬运工具
                byte datas[] = new byte[1024*8];
                //创建长度
                int len = 0;
                //循环读取数据
                while((len = fis.read(datas))!=-1){
                    fos.write(datas,0,len);
                }
                //3.释放资源
                fis.close();
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
          //删除文件，实现剪切操作
          new File(srcPathStr).delete();
        }

        /**
        * 删除单个文件
        * @param   sPath    被删除文件的文件名
        * @return 单个文件删除成功返回true，否则返回false
        */
       public boolean deleteFile(String sPath) {
           boolean flag = false;
           File file = new File(sPath);
           // 路径为文件且不为空则进行删除
           if (file.isFile() && file.exists()) {
               file.delete();
               flag = true;
           }
           return flag;
       }

        /**
         * 删除目录（文件夹）以及目录下的文件
         * @param   sPath 被删除目录的文件路径
         * @return  目录删除成功返回true，否则返回false
         */
        public boolean deleteDirectory(String sPath) {
            //如果sPath不以文件分隔符结尾，自动添加文件分隔符
            if (!sPath.endsWith(File.separator)) {
                sPath = sPath + File.separator;
            }
            File dirFile = new File(sPath);
            //如果dir对应的文件不存在，或者不是一个目录，则退出
            if (!dirFile.exists() || !dirFile.isDirectory()) {
                return false;
            }
            boolean flag = true;
            //删除文件夹下的所有文件(包括子目录)
            File[] files = dirFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                //删除子文件
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag) break;
                } //删除子目录
                else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag) break;
                }
            }
            if (!flag) return false;
            //删除当前目录
            if (dirFile.delete()) {
                return true;
            } else {
                return false;
            }
       }

		public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			doGet(request, response);
		}


}
