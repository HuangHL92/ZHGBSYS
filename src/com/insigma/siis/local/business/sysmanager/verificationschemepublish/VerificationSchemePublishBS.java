package com.insigma.siis.local.business.sysmanager.verificationschemepublish;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringEscapeUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Aa01;

public class VerificationSchemePublishBS extends BSSupport{
	
	/**
	 * 查询目录下文件：
	 * 	allFileFlag-true:查询全部的文件（包括下级目录），遍历该目录及其下级目录获取文件
	 * 	allFileFlag-false:查询当前指定目录的文件，仅遍历该目录获取文件
	 * @param dirPath 指定目录
	 * @param allFileFlag 是不是全部文件（包括下级目录）	
	 * @return
	 * @throws AppException 
	 * @author mengl
	 */
	public List<HashMap<String,Object>> getFilesByDirFile(String dirPath,boolean allFileFlag) throws AppException{
		File[] subFiles = null;																//文件类型对应配置的目录下的所有文件
		List<HashMap<String,Object>> gridList = new ArrayList<HashMap<String,Object>>();	//存放赋值在fileGrid中的数据
		HashMap<String,Object> gridMap = null;												//存放赋值在fileGrid中一行数据
		List<File> fileList = null;															//存放遍历目录后获取的文件
		File file = new File(dirPath);
		if(file.exists()){
			if(file.isDirectory()){
				subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0 ){
					if(allFileFlag){//查询全部文件(包含下级目录下文件)
						fileList = getAllFilesByDirFile(file);
						if(fileList!=null && fileList.size()>0){
							for(File subFile : fileList){
								gridMap = new HashMap<String,Object>();
								gridMap.put("fileName", subFile.getName());
								gridMap.put("fileSuffix", subFile.getName().lastIndexOf(".")==-1?"":subFile.getName().substring(subFile.getName().lastIndexOf(".")+1));
								gridMap.put("filePath", subFile.getAbsolutePath()==null?"":subFile.getAbsolutePath());
								gridMap.put("lastModefyTime", new Date(subFile.lastModified()));
								gridMap.put("filePublishType", "");// 未给操作文件类型赋值
								gridList.add(gridMap);
							}
						}
					}else{//查询文件(当前指定目录)
						for(File subFile :subFiles){
							if(subFile!=null && subFile.isFile()){
								gridMap = new HashMap<String,Object>();
								gridMap.put("fileName", subFile.getName());
								gridMap.put("fileSuffix", subFile.getName().lastIndexOf(".")==-1?"":subFile.getName().substring(subFile.getName().lastIndexOf(".")+1));
								gridMap.put("filePath", subFile.getAbsolutePath());
								gridMap.put("lastModefyTime", new Date(subFile.lastModified()));
								gridMap.put("filePublishType", "");// 未给操作文件类型赋值
								gridList.add(gridMap);
							}
						}
					}//END IF ：是否遍历下级目录
				}//END IF :下级存在文件
			}else{
				throw new AppException("配置发布的文件目录不是目录文件！");
			}
		}else{
			file.mkdirs();
		}
		return gridList;
	}
	
	
	/**
	 * 查询目录下全部文件（包含其子目录），遍历该目录及其下级目录获取文件
	 * @param filePath
	 * @return
	 * @throws AppException 
	 * @author mengl
	 */
	public List<HashMap<String,Object>> getFilesByDirFile(String dirPath) throws AppException{
		
		return getFilesByDirFile( dirPath,true);
	}
	
	/**
	 * 遍历指定目录文件（dirFile）下所有文件
	 * @param dirFile
	 * @return
	 * @author mengl
	 */
	private List<File> getAllFilesByDirFile(File dirFile){
		List<File> fileList = new ArrayList<File>();
		File[] subFiles = null;
		List<File> fileListTemp = null;
		if(dirFile.exists() && dirFile.isDirectory()){
			subFiles = dirFile.listFiles();
			if(subFiles!=null && subFiles.length>0){
				for(File file : subFiles){
					if( file.isFile()){
						fileList.add(file);
					}else{
						fileListTemp = getAllFilesByDirFile(file);
						if(fileListTemp!=null && fileListTemp.size()>0){
							fileList.addAll(fileListTemp);
						}
					}
				}
			}
		}
		return fileList;
	}
	
} 