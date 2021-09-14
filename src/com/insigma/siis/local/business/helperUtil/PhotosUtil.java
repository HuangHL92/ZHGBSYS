package com.insigma.siis.local.business.helperUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PhotosUtil {
	
	public static String PHOTO_PATH = GlobalNames.sysConfig.get("PHOTO_PATH");// ͼƬ��·��
	
	/**
	 * ������Ƭ��ͼƬ·��
	 * @param in
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public static void savePhoto(InputStream in, String filename) {
		String photourl = "";
		if(filename!=null){
			String filenamesub = filename.substring(0,filename.indexOf("."));
			if(filenamesub.length()>=2){
				photourl = PHOTO_PATH + (filenamesub.charAt(0) +"/" + filenamesub.charAt(1)).toUpperCase() +"/";
			} else if(filenamesub.length()==1){
				photourl = PHOTO_PATH + (filenamesub +"/" + filenamesub).toUpperCase() +"/";
			} else {
				return;
			}
			File file = new File(photourl);
			if(!file.exists()){
				file.mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(photourl + filename);
				byte[] b = new byte[1024*4];
				while((in.read(b)) != -1){
					fos.write(b);
				}
				in.close();
				fos.close();
			} catch (IOException e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡ·��
	 * @param in
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public static String getSavePath(String filename) {
		String photourl = "";
		if(filename!=null){
			String filenamesub = filename.substring(0,filename.indexOf("."));
			if(filenamesub.length()>=2){
				photourl = (filenamesub.charAt(0) +"/" + filenamesub.charAt(1)).toUpperCase() +"/";
			} else if(filenamesub.length()==1){
				photourl = (filenamesub +"/" + filenamesub).toUpperCase() +"/";
			} else {
				return photourl;
			}
		}
		return photourl;
	}
	
	
	
	/**
	 * ��ȡͼƬ��
	 * @param photourl
	 * @return
	 */
	public static InputStream getPhotoStream(String photourl){
		FileInputStream in = null;
		try {
			in = new FileInputStream(photourl);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		}
		return in;
	}
	
	/**
	 * ��ȡͼƬ�ֽ�����
	 * @param photourl
	 * @return
	 */
	public static byte[] getPhotoData(String photourl){
		InputStream in = getPhotoStream(photourl);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024*4];
		int n=0;
        try {
			while ((n=in.read(buffer)) !=-1) {
			    out.write(buffer,0,n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return out.toByteArray();
	}
	
	
	
	/**
	 * ��ȡͼƬ�ֽ�����
	 * @param photourl
	 * @return
	 */
	public static byte[] getPhotoData(A57 a57){
        try {
        	String photourl = a57.getPhotopath();
    		File fileF = new File(PhotosUtil.PHOTO_PATH+photourl.toUpperCase()+a57.getPhotoname());
    		if(fileF.isFile()){
    			long nLen = fileF.length();
    			int nSize = (int) nLen;
    			byte[] data = new byte[nSize];
    			FileInputStream inStream = new FileInputStream(fileF); 
    			inStream.read(data);
    			inStream.close();
    			return data;
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	/**
	 * ��ȡͼƬ�ֽ�����
	 * @param photourl
	 * @return
	 */
	public static byte[] getPhotoData(A57 a57, String xt){
		String p = "";
        try {
        	if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
        		p = "qcjs/"+xt+"/";
        	}
        	String photourl = a57.getPhotopath();
    		File fileF = new File(PhotosUtil.PHOTO_PATH +p+photourl.toUpperCase()+a57.getPhotoname());
    		if(fileF.isFile()){
    			long nLen = fileF.length();
    			int nSize = (int) nLen;
    			byte[] data = new byte[nSize];
    			FileInputStream inStream = new FileInputStream(fileF); 
    			inStream.read(data);
    			inStream.close();
    			return data;
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	/**
	 * ����ͼƬ
	 * @param photourl
	 * @return
	 */
	public static void savePhotoData(A57 a57,HBSession sess,byte[] data){
        try {
        	String fileName = a57.getA0000()+".jpg";
			String photourl = getSavePath(fileName).toUpperCase();
			a57.setPhotopath(photourl);
			a57.setPhotoname(fileName);
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			File fileD = new File(PHOTO_PATH+photourl);
			if(!fileD.isDirectory()){
				fileD.mkdirs();
			}
			File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+fileName);
			FileOutputStream fos = new FileOutputStream(fileF);
			fos.write(data);
			fos.flush();
			fos.close();
			sess.saveOrUpdate(a57);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡͼƬת������ֽ�����
	 * @param photourl
	 * @return
	 */
	public static byte[] getPhotoDataMin (String photourl){
		return null;
	}
	
	/**
	 * ɾ����Ƭ�ļ�
	 * @param photourl
	 */
	public static void deletePhoto (String photourl){
		File file = new File(photourl);
		if(file.exists()&&file.isFile()){
			file.delete();
		}
	}
	
	/**
	 * ɾ����Ƭ�ļ�
	 * @param photourl
	 */
	public static void deletePhoto2 (String a0000){
		deletePhoto(PHOTO_PATH + getSavePath(a0000+".jpg") + a0000 + ".jpg");
	}
	
	/**
	 * ɾ�������Ƭ�ļ�
	 * @param photourl
	 */
	public static void deletePhoto (List<String> list){
		for (String photourl : list) {
			File file = new File(photourl);
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	/**
	 * ʹ������ɾ��ͼƬ·���µĶ����Ƭ�ļ�
	 * @param list  �ṹ�� "A/0/62692681-18c8-45c4-8072-693118acd61f.jpg"
	 */
	public static void removeCmd (List<String> list){
		CommandUtil util = new CommandUtil();
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_DEL_ALL="rm -fr ";
		String WINDOWS_DEL_ALL="cmd /c del /s/q ";
		try {
			if(osname.equals("Linux")){ //��ȷ��
				for (String string : list) {
					util.executeCommand(LINUX_DEL_ALL + (PHOTO_PATH.replace("\\", "/")) + string);
				}
			} if(osname.contains("Windows")){
				if(list!=null && list.size()>0){
					for (String string : list) {
						util.executeCommand(WINDOWS_DEL_ALL +"\""+ PHOTO_PATH+(string.replace("/", "\\"))+"\"");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ʹ������ɾ���ļ���
	 * @param dir
	 */
	public static void removDireCmd (String dir){
		CommandUtil util = new CommandUtil();
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_DEL_ALL="rm -fr ";
		String WINDOWS_DEL_ALL="cmd /c rmdir /S /Q ";
		try {
			if(osname.equals("Linux")){ //��ȷ��
				util.executeCommand(LINUX_DEL_ALL + dir);
			} if(osname.contains("Windows")){
//				System.out.println(WINDOWS_DEL_ALL +"\""+ (dir.replace("/", "\\"))+"\"");
				util.executeCommand(WINDOWS_DEL_ALL +"\""+ (dir.replace("/", "\\"))+"\"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ʹ�������ͼƬ·����ͼƬ��ָ��·�� 
	 * @param list  �ṹ�� "A/0/62692681-18c8-45c4-8072-693118acd61f.jpg"
	 * @param dir
	 */
	public static void copyCmd (List<String> list, String dir){
		CommandUtil util = new CommandUtil();
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_COPY_ALL="cp -f ";
		String WINDOWS_COPY_ALL="cmd /c xcopy /r /y ";
		
		try {
			if(osname.equals("Linux")){ //��ȷ��
				String source = "";
				for (String url : list) {
					source = url + " ";
				}
				util.executeCommand(LINUX_COPY_ALL + source + (dir.replace("\\", "/")));
			} if(osname.contains("Windows")){
				if(list!=null && list.size()>0){
					for (String url : list) {
//						System.out.println(WINDOWS_COPY_ALL + PHOTO_PATH+(url.replace("/", "\\")) +" "+ (dir.replace("/", "\\")));
						util.executeCommand(WINDOWS_COPY_ALL +"\""+ PHOTO_PATH+(url.replace("/", "\\"))+"\"" +" "+"\""+ (dir.replace("/", "\\"))+"\\\"");
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ʹ�������ͼƬ·����ͼƬ��ָ��·�� 
	 * @param list  �ṹ�� "D:/photos/A/0/62692681-18c8-45c4-8072-693118acd61f.jpg"
	 * @param dir
	 */
	public static void copyCmdAbsolutePath (List<String> list, String dir){
		CommandUtil util = new CommandUtil();
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_COPY_ALL="cp -f ";
		String WINDOWS_COPY_ALL="cmd /c xcopy /r /y ";
		
		try {
			if(osname.equals("Linux")){ //��ȷ��
				String source = "";
				for (String url : list) {
					source = url + " ";
				}
				util.executeCommand(LINUX_COPY_ALL + source + (dir.replace("\\", "/")));
			} if(osname.contains("Windows")){
				if(list!=null && list.size()>0){
					for (String url : list) {
//						System.out.println(WINDOWS_COPY_ALL +"\""+ (url.replace("/", "\\"))+"\"" +" "+"\""+ (dir.replace("/", "\\"))+"\"");
						util.executeCommand(WINDOWS_COPY_ALL +"\""+ (url.replace("/", "\\"))+"\"" +" "+"\""+ (dir.replace("/", "\\"))+"\"");
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ʹ�������ƶ��ļ�����ʼ��·���� 
	 * @param imprecordid
	 */
	public static int moveIMPCmd (String imprecordid, String photo_path){
		int isfalse = 0;
		CommandUtil util = new CommandUtil();
		List<String> list = initPath();								//��ʼ��·�� ���� 0/1/,0/2/,A/B/...Z/Z
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_remove_ALL="mv -f ";
		String WINDOWS_remove_ALL="cmd /c move /y ";
		//String photo_path_temp = (AppConfig.HZB_PATH + "/temp/upload\\unzip\\"+imprecordid +"\\Photos\\").replace("/", "\\");
		String photo_path_temp = photo_path.replace("/", "\\");
		try {
			if(osname.equals("Linux")){ //��ȷ��
				for (String url : list) {
					String source = (photo_path_temp + "/"  + (url.replace("/", "")) +"*.* ").replace("/", "\\");
					String dir = PHOTO_PATH + url ;
					util.executeCommand(LINUX_remove_ALL + source + dir);
				}
				
			} if(osname.contains("Windows")){
				for (String url : list) {
					String source = "\""+photo_path_temp + (url.replace("\\", "")) +"*.*\" ";
					String dir = ("\""+PHOTO_PATH + url + "" +"\"").replace("\\\\", "\\");
					int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
					if(i==1){
						isfalse=1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isfalse;
	}
	
	
	/**
	 * ʹ�������ƶ��ļ���ָ��·��
	 * @param imprecordid
	 */
	public static int moveIMPOtherCmd (String imprecordid, String photo_path){
		CommandUtil util = new CommandUtil();
		int isfalse = 0;
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_remove_ALL="mv -f ";
		String WINDOWS_remove_ALL="cmd /c move /y ";
		//String photo_path_temp = (AppConfig.HZB_PATH + "/temp/upload\\unzip\\"+imprecordid +"\\Photos\\").replace("/", "\\");
		String photo_path_temp = photo_path.replace("/", "\\");
		try {
			File file = new File(photo_path_temp);
			String[] filelist = file.list();
			if(osname.equals("Linux") && filelist!=null && filelist.length > 0){ //��ȷ��
				for (String photoname : filelist) {
					String source = photo_path_temp + "/" + photoname +" ";
					String dir = PHOTO_PATH +"/" + (photoname.charAt(0)+"").toUpperCase() + "/";
					File file2 = new File(dir);
					if(!file2.exists()){
						file2.mkdirs();
					}
					util.executeCommand(LINUX_remove_ALL + source + dir);
				}
				
			} if(osname.contains("Windows") && filelist!=null && filelist.length > 0){
				for (String photoname : filelist) {
					String source = "\""+photo_path_temp + photoname+"\"" +" ";
					String dir = "\""+PHOTO_PATH +"" + (photoname.charAt(0) + "").toUpperCase()+"\"" ;
					File file2 = new File(PHOTO_PATH +("" + photoname.charAt(0)).toUpperCase());
					if(!file2.exists()){
						file2.mkdirs();
					}
					int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
					if(i==1){
						isfalse=1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isfalse;
	}
	
	/**
	 * ʹ������ɾ�������ļ���
	 * @param photourl
	 */
	public static int removDirImpCmd (String imprecordid, String fName){
		CommandUtil util = new CommandUtil();
		int isfalse = 0;
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_DEL_ALL="rm -fr ";
		String WINDOWS_DEL_ALL="cmd /c rmdir /S /Q ";
		try {
			String dir = "";
			if("".equals(fName)){
				dir = "\"" + AppConfig.HZB_PATH + "/temp/upload\\unzip\\"+imprecordid+"\"" ;
			}else{
				dir = "\"" + AppConfig.HZB_PATH + "/temp/upload\\"+imprecordid+"\\"+ imprecordid + "\\" + fName +"\"";
			}
			//String dir = "\"" + AppConfig.HZB_PATH + "/temp/upload\\unzip\\"+imprecordid+"\"" ;
			if(osname.equals("Linux")){ //��ȷ��
				util.executeCommand(LINUX_DEL_ALL + dir);
			} if(osname.contains("Windows")){
				CommonQueryBS.systemOut(WINDOWS_DEL_ALL + (dir.replace("/", "\\")));
				int i = util.executeCommand(WINDOWS_DEL_ALL + (dir.replace("/", "\\")));
				if(i==1){
					isfalse=1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isfalse;
	}
	
	private static List<String> initPath() {
//		String[] keys= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"
//				,"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
//				,"Y","Z"};	
		String[] keys= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};	
		List<String> list = new ArrayList<String>();
		String osname = System.getProperties().getProperty("os.name");
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys.length; j++) {
				
				if(osname.equals("Linux")){ //��ȷ��
					String url = "/" + keys[i] + "/" +keys[j] +"/";
					list.add(url);
				} if(osname.contains("Windows")){
					String url = "\\" + keys[i] + "\\" +keys[j] +"\\";
					list.add(url);
				}
			}
		}
		return list;
	}
	
	public static void copyFile(File sourcefile, File targetFile)
		throws IOException {
		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// �½��ļ���������������л���
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);
		// ��������
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// ˢ�´˻���������
		outbuff.flush();
		// �ر���
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();
	}
	
	public static void copyFile(String sourcefile, String targetDir) throws IOException {
		File sourceFile =  new File(PHOTO_PATH + sourcefile);
		if(sourceFile.exists()){
			File targetFile = new File(new File(targetDir)// Ŀ���ļ�
					.getAbsolutePath() + File.separator + (sourcefile.substring(sourcefile.lastIndexOf("/")+1)));
			copyFile(sourceFile, targetFile);
		}
	}
	
	/**
	 * ����ԭ�ļ������ݵ�Ŀ���ļ���
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir)
		throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е��µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	/**
	 * ����ϵͳͼƬ����·���µ�ͼƬ��ָ��·�� ��ʹ�����·��
	 * @param list  �ṹ�� "/A/0/a0692681-18c8-45c4-8072-693118acd61f.jpg"
	 * @param dir
	 */
	public static void copyPhotos(List<String> list, String targetDir) throws IOException {
		File tfile = new File(targetDir);		// Ŀ��Ŀ¼
		if(tfile.exists()){
			tfile.mkdirs();
		}
		if(list!=null && list.size() > 0){
			for (String url : list) {
				File sourceFile =  new File(PHOTO_PATH + url);
				if(sourceFile.exists()){
					// Ŀ���ļ�
					File targetFile = new File(new File(targetDir)
							.getAbsolutePath()
							+ File.separator + (url.substring(url.lastIndexOf("/")+1)));
					copyFile(sourceFile, targetFile);
				}
			}
		}
	}
	/**
	 * ����ָ��·����ͼƬ��ָ��·�� ��ʹ�þ���·��
	 * @param list  �ṹ�� "D:/photos/62692681-18c8-45c4-8072-693118acd61f.jpg"
	 * @param dir
	 */
	public static void copyAbsoluteFiles (List<String> list, String targetDir){
		try {
			File tfile = new File(targetDir);		// Ŀ��Ŀ¼
			if(tfile.exists()){
				tfile.mkdirs();
			}
			if(list!=null && list.size() > 0){
				for (String url : list) {
					File sourceFile =  new File(url);
					if(sourceFile.exists()){
						// Ŀ���ļ�
						File targetFile = new File(new File(targetDir)
								.getAbsolutePath()
								+ File.separator + (sourceFile.getName()));
						copyFile(sourceFile, targetFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean isLetterDigit(String str) throws IOException {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}
	
	public static String getRootPath() {
		String classPath = SevenZipUtil.class.getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		String osname = System.getProperties().getProperty("os.name");
		if(osname.equals("Linux")){ //��ȷ��
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		} if(osname.contains("Windows")){
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		}
		return rootPath;
	}

	public static void main(String[] args) {
		CommonQueryBS.systemOut(80*10/60+"");
		CommonQueryBS.systemOut(80*13/60+"");
		
		CommonQueryBS.systemOut(464*4/60+"");
		CommonQueryBS.systemOut(464*6/60+"");
//		String logfilename = "D:/123456789.txt";
//		File logfile = new File(logfilename);
//		if(!logfile.exists()){
//			try {
//				logfile.createNewFile();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		CommandUtil util = new CommandUtil();
//		List<String> list = initPath();								//��ʼ��·�� ���� 0/1/,0/2/,A/B/...Z/Z
//		String osname = System.getProperties().getProperty("os.name");
//		String LINUX_remove_ALL="mv -f ";
//		String WINDOWS_remove_ALL="xcopy /r /y ";
//		String photo_path_temp =  "D:\\Photos\\";
//		try {
//			for (String url : list) {
//				String source = "\""+photo_path_temp + (url.replace("\\", "")) +"*.*\" ";
//				String dir = "\""+PHOTO_PATH + url +"\"";
//				System.out.println(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
//				appendFileContent(logfilename,WINDOWS_remove_ALL + source + (dir.replace("/", "\\"))+"\n");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void appendFileContent(String fileName, String content) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	/**
	 * ������־ 
	 * @param in
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public static void saveLog(String desc) {
		String aaa005 = AppConfig.LOG_CONTROL;
		if(!"ON".equals(aaa005)){
			return;
		}
		String now = sdf.format(new Date());
		String path = AppConfig.HZB_PATH+"/hzbLog";
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		if(!file.exists()){
			return;
		}
		String filename;
		try {
			filename = SysManagerUtils.getUserloginName()+".txt";
		} catch (Exception e1) {
			filename = "comm.txt";
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(path + "\\" + filename, true);
			fw.write(now + ":" + desc + "\r\n-----------------------------------------------------------------"
					+ "---------------------------------------------------------\r\n\r\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fw!=null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
