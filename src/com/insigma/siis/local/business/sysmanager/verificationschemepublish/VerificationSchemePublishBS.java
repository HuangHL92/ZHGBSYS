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
	 * ��ѯĿ¼���ļ���
	 * 	allFileFlag-true:��ѯȫ�����ļ��������¼�Ŀ¼����������Ŀ¼�����¼�Ŀ¼��ȡ�ļ�
	 * 	allFileFlag-false:��ѯ��ǰָ��Ŀ¼���ļ�����������Ŀ¼��ȡ�ļ�
	 * @param dirPath ָ��Ŀ¼
	 * @param allFileFlag �ǲ���ȫ���ļ��������¼�Ŀ¼��	
	 * @return
	 * @throws AppException 
	 * @author mengl
	 */
	public List<HashMap<String,Object>> getFilesByDirFile(String dirPath,boolean allFileFlag) throws AppException{
		File[] subFiles = null;																//�ļ����Ͷ�Ӧ���õ�Ŀ¼�µ������ļ�
		List<HashMap<String,Object>> gridList = new ArrayList<HashMap<String,Object>>();	//��Ÿ�ֵ��fileGrid�е�����
		HashMap<String,Object> gridMap = null;												//��Ÿ�ֵ��fileGrid��һ������
		List<File> fileList = null;															//��ű���Ŀ¼���ȡ���ļ�
		File file = new File(dirPath);
		if(file.exists()){
			if(file.isDirectory()){
				subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0 ){
					if(allFileFlag){//��ѯȫ���ļ�(�����¼�Ŀ¼���ļ�)
						fileList = getAllFilesByDirFile(file);
						if(fileList!=null && fileList.size()>0){
							for(File subFile : fileList){
								gridMap = new HashMap<String,Object>();
								gridMap.put("fileName", subFile.getName());
								gridMap.put("fileSuffix", subFile.getName().lastIndexOf(".")==-1?"":subFile.getName().substring(subFile.getName().lastIndexOf(".")+1));
								gridMap.put("filePath", subFile.getAbsolutePath()==null?"":subFile.getAbsolutePath());
								gridMap.put("lastModefyTime", new Date(subFile.lastModified()));
								gridMap.put("filePublishType", "");// δ�������ļ����͸�ֵ
								gridList.add(gridMap);
							}
						}
					}else{//��ѯ�ļ�(��ǰָ��Ŀ¼)
						for(File subFile :subFiles){
							if(subFile!=null && subFile.isFile()){
								gridMap = new HashMap<String,Object>();
								gridMap.put("fileName", subFile.getName());
								gridMap.put("fileSuffix", subFile.getName().lastIndexOf(".")==-1?"":subFile.getName().substring(subFile.getName().lastIndexOf(".")+1));
								gridMap.put("filePath", subFile.getAbsolutePath());
								gridMap.put("lastModefyTime", new Date(subFile.lastModified()));
								gridMap.put("filePublishType", "");// δ�������ļ����͸�ֵ
								gridList.add(gridMap);
							}
						}
					}//END IF ���Ƿ�����¼�Ŀ¼
				}//END IF :�¼������ļ�
			}else{
				throw new AppException("���÷������ļ�Ŀ¼����Ŀ¼�ļ���");
			}
		}else{
			file.mkdirs();
		}
		return gridList;
	}
	
	
	/**
	 * ��ѯĿ¼��ȫ���ļ�����������Ŀ¼����������Ŀ¼�����¼�Ŀ¼��ȡ�ļ�
	 * @param filePath
	 * @return
	 * @throws AppException 
	 * @author mengl
	 */
	public List<HashMap<String,Object>> getFilesByDirFile(String dirPath) throws AppException{
		
		return getFilesByDirFile( dirPath,true);
	}
	
	/**
	 * ����ָ��Ŀ¼�ļ���dirFile���������ļ�
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