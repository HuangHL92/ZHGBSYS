package com.insigma.siis.local.lrmx;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class ExpRar {
	public static String expFile(){
		String path = getPath();
		String infile = "";
		//文件路径不使用汉字 mengl 20160603
		String zippath = path + "expFiles_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +"/";
		File file =new File(zippath);    
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdirs();    
		}
		return zippath;
	}
	private static String getPath() {
		String classPath = new ExpRar().getClass().getClassLoader().getResource("/").getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
//			classPath = URLDecoder.decode(classPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		//上传路径
		String upload_file = rootPath + "ziploud/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		String zip = upload_file + uuid + "/";
		return zip;
	}
	
	public static String getExeclPath() {
		String classPath = new ExpRar().getClass().getClassLoader().getResource("/").getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
			rootPath = rootPath+"pages\\exportexcel";
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
			rootPath = rootPath+"pages/exportexcel";
		
		}
		
		return rootPath;
	}
	
	
	
	
	private static String getPrintPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/printload/"+ UUID.randomUUID().toString()+"/";
		try {
			File file = new File(upload_file);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return upload_file;
	}
}
