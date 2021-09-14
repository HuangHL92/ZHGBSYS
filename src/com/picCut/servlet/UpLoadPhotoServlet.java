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
		//��ȡϵͳ��ǰʱ��
		String now = DateUtil.getcurdate();
		//�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
		//String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	     //�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
	    String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	    if(sys.startsWith("WIN")){
	    	tempPath = UPLODA_PATH_WIN + "temp"+UUID.randomUUID();
	     }else{
	    	tempPath = UPLODA_PATH + "temp"+UUID.randomUUID();
	     }
	    File tmpFile = new File(tempPath);
	    if (!tmpFile.exists()) {
	    	 //������ʱĿ¼
	    	 tmpFile.mkdir();
	    }
	    //��Ϣ��ʾ
	    String message = "";
	    String realSavePath = "";
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
	    	//����ϴ��ļ�������������
	    	 upload.setHeaderEncoding("GBK");
	    	 //3���ж��ύ�����������Ƿ����ϴ���������
	    	 if(!ServletFileUpload.isMultipartContent(request)){
	    		 //���մ�ͳ��ʽ��ȡ����
	    		 return;
	    	 }
	    	//�����ϴ������ļ��Ĵ�С�����ֵΪ2MB
	    	 //upload.setFileSizeMax(1024*1024*2);
	    	 //�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ200MB
	    	 //upload.setSizeMax(1024*1024*2*100);
	    	 //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
	    	 @SuppressWarnings("unchecked")
			 List<FileItem> list = upload.parseRequest(request);
	    	 for(FileItem item : list){
	    		 if(item.isFormField()){//���item�з�װ������ͨ�����������

	    		 }else{//���fileitem�з�װ�����ϴ��ļ�
	    			 //�õ��ϴ����ļ����ƣ�
	    			 String filename = item.getName();
	    			 if(filename==null || filename.trim().equals("")){
	    				 continue;
	    			 }
	    			 //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
	    			 //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
	    			 filename = filename.substring(filename.lastIndexOf(separator)+1);
	    			 //�õ��ϴ��ļ�����չ��
	    			 String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
	    			 //�����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
	    			 //��ȡitem�е��ϴ��ļ���������
	    			 InputStream in = item.getInputStream();
	    			 //�õ��ļ����������
	    			 String saveFilename = makeFileName(filename);
	    			 //�õ��ļ��ı���Ŀ¼
	    			 //realSavePath = makePath(saveFilename, tempPath);
	    			 //����һ���ļ������
	    			 // out = new FileOutputStream(realSavePath + "\\" + saveFilename);
	    			 if("zip".equals(fileExtName)){

	    			 }
   				  sess.createSQLQuery("delete from Photoup_Error ").executeUpdate();
   				  sess.flush();

    				 //��������
	    			 String unzippath = tempPath;
					 int len = 0;
					 //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					 byte buffer1[] = new byte[1024];
					 FileOutputStream out1 = new FileOutputStream(tempPath + separator + filename);
					 while((len=in.read(buffer1))>0){
						 out1.write(buffer1, 0, len);
					 }
					 out1.close();
					 in.close();
					 //��ѹ�ϴ���zip�ļ� "
					 String unzip=AppConfig.HZB_PATH+"/Comparison";
					 File tmpFile1 = new File(unzip);
					 if (!tmpFile1.exists()) {
						 //������ʱĿ¼
						 tmpFile1.mkdir();
					 }

					  UpLoadLrmsServlet.decompress(tempPath + separator + filename, unzip, "GBK");
    				  File flist[] = new File(unzip).listFiles();

    				  if (flist == null || flist.length == 0) {
    				      throw new IOException("��ȡ��ѹ�ļ��쳣��");
    				  }

    				  if(flist.length == 1 && flist[0].isDirectory()){
						  flist = flist[0].listFiles();
					  }

    				  //��¼δ������Ա������
    				  int num = 0;
    				  for (File f : flist) {
    					  String path = f.getPath();
    					  String fName = f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length());
    					  String phName = f.getName();
    					  //phName = phName.replaceAll("[\\u4e00-\\u9fa5]", "");
    					  phName = getPersonCode(phName);
    				      List a01list = sess.createSQLQuery("select a0000 from a01 where upper(a0184) = upper('"+phName+"')").list();
    					  //ֻ֧��jpg
    					  if(fName == null || !((fName.toLowerCase()).equals("jpg"))){
    						  PhotoupError pe = new PhotoupError();
    						  pe.setPer002(phName);
    						  pe.setPer003("��Ƭ��Ϊjpg��ʽ");
    						  pe.setPer004(now);
    						  pe.setPer005(user);
    						  sess.save(pe);
    						  num +=1;
    						  continue;
    					  }
    					  if(a01list.size()>1){
    						  PhotoupError pe = new PhotoupError();
    						  pe.setPer002(f.getName());
    						  pe.setPer003("��Աƥ���ظ�");
    						  pe.setPer004(now);
    						  pe.setPer005(user);
    						  sess.save(pe);
    						  num +=1;
    						  continue;
    					  }
    					  if(a01list.size()==0||a01list==null){
    						  PhotoupError pe = new PhotoupError();
    						  pe.setPer002(f.getName());
    						  pe.setPer003("��Աδ�ҵ�");
    						  pe.setPer004(now);
    						  pe.setPer005(user);
    						  sess.save(pe);
    						  num +=1;
    						  continue;
    					  }
    					  //��ѯa57���и���Ա����Ƭ��¼
    					  A57 a57 = (A57) sess.get(A57.class, a01list.get(0).toString());
    					  //a57���޼�¼��ʵ����Ƭ�洢����
    					  if(a57==null){
    						Rectangle rect = new Rectangle(0, 0, 272, 340);
    						int width = 272;
    						int height = 340;
    						ImageHepler.cut(path, path, width, height, rect,a01list.get(0).toString());
    						continue;
    					  }
    					  String newPhotoName = UUID.randomUUID().toString()+"."+fName;
    					  newPhotoName.replace("-", "");
    					  //��a57�ļ�¼���Ƶ�a57_history����
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
    					  //��ԭ��Ƭ����
    					  new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname()).renameTo(new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+newPhotoName));
    					  //���ϴ�����Ƭѹ��
    					  try{
    						  DataPadImpDBThread.cut(path, f.getName(),path.substring(path.lastIndexOf(separator)+1, path.length()),"update");
        					 }catch(IOException e) {

							 PhotoupError pe = new PhotoupError();
							  pe.setPer002(f.getName());
							  pe.setPer003("��֧�ֵ���Ƭ����");
       						  pe.setPer004(now);
       						  pe.setPer005(user);
       						  sess.save(pe);
       						  num +=1;
       						  e.printStackTrace();
        					 }

    					  String newPath = path.substring(0, path.lastIndexOf(separator)+1)+a57.getPhotoname();
    					  //��ѹ�������Ƭ����ΪA57��ѯ��������
    					  new File(path).renameTo(new File(newPath));
    					  //��ѹ�������Ƭ���е�ԭ�ȵ�Ŀ¼��
    					  copyFile(newPath, PhotosUtil.PHOTO_PATH+a57.getPhotopath());
    					  //new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+f.getName()).renameTo(new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname()));
    				  }
    				  	sess.flush();
    				  	if(num>0){
    				  		localPrintWriter.write("parent.Ext.Msg.hide();");
    					    localPrintWriter.write("parent.odin.alert(\"����ɹ�������"+num+"��δ���룬��鿴������Ϣ\");");
    				  	}else{
    				  		localPrintWriter.write("parent.Ext.Msg.hide();");
    					    localPrintWriter.write("parent.odin.alert(\"����ɹ�\");");
    				  	}
    				    //localPrintWriter.write("parent.odin.ext.getCmp('importLrmWins').hide();");
    				    //localPrintWriter.write("parent.Ext.Msg.hide();");
					    //localPrintWriter.write("parent.odin.alert(\"����ɹ�\");");
					    //localPrintWriter.write("parent.parent.reloadGrid();");
	    		 }
	    	 }
	    }catch (FileUploadBase.FileSizeLimitExceededException e){
	    	 message = "�����ļ�������������ֵ2M";
	    	 localPrintWriter.write("parent.Ext.Msg.hide();parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');");
	     }catch (FileUploadBase.SizeLimitExceededException e) {
	    	 message = "һ���ϴ��ļ����ܵĴ�С�������Ƶ����ֵ200M";
	    	 localPrintWriter.write("parent.Ext.Msg.hide();parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');");
	    	 //request.getRequestDispatcher("/message.jsp").forward(request, response);
	     }catch (Exception e) {
	    	 e.printStackTrace();
	    	 message= "�ļ��ϴ�ʧ��"+e.getMessage();
	    	 localPrintWriter.write("parent.Ext.Msg.hide();parent.odin.alert('�쳣��Ϣ��"+e.getMessage()+"');");
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
//		System.out.println(us.getPersonCode("�½�32020319720827151X.jpg"));
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
		  /*
         * ʵ���ļ��ļ���
         * @param srcPathStr
         *          Դ�ļ��ĵ�ַ��Ϣ
         * @param desPathStr
         *          Ŀ���ļ��ĵ�ַ��Ϣ
         */
        private static void copyFile(String srcPathStr, String desPathStr) {
            //1.��ȡԴ�ļ�������
            String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //Ŀ���ļ���ַ
            System.out.println(newFileName);
            desPathStr = desPathStr + File.separator + newFileName; //Դ�ļ���ַ
            System.out.println(desPathStr);

            try{
                //2.�����������������
                FileInputStream fis = new FileInputStream(srcPathStr);
                FileOutputStream fos = new FileOutputStream(desPathStr);

                //�������˹���
                byte datas[] = new byte[1024*8];
                //��������
                int len = 0;
                //ѭ����ȡ����
                while((len = fis.read(datas))!=-1){
                    fos.write(datas,0,len);
                }
                //3.�ͷ���Դ
                fis.close();
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
          //ɾ���ļ���ʵ�ּ��в���
          new File(srcPathStr).delete();
        }

        /**
        * ɾ�������ļ�
        * @param   sPath    ��ɾ���ļ����ļ���
        * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
        */
       public boolean deleteFile(String sPath) {
           boolean flag = false;
           File file = new File(sPath);
           // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
           if (file.isFile() && file.exists()) {
               file.delete();
               flag = true;
           }
           return flag;
       }

        /**
         * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
         * @param   sPath ��ɾ��Ŀ¼���ļ�·��
         * @return  Ŀ¼ɾ���ɹ�����true�����򷵻�false
         */
        public boolean deleteDirectory(String sPath) {
            //���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
            if (!sPath.endsWith(File.separator)) {
                sPath = sPath + File.separator;
            }
            File dirFile = new File(sPath);
            //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
            if (!dirFile.exists() || !dirFile.isDirectory()) {
                return false;
            }
            boolean flag = true;
            //ɾ���ļ����µ������ļ�(������Ŀ¼)
            File[] files = dirFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                //ɾ�����ļ�
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag) break;
                } //ɾ����Ŀ¼
                else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag) break;
                }
            }
            if (!flag) return false;
            //ɾ����ǰĿ¼
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
