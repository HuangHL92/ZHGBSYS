package com.JUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public interface JUpload {
	/**
	 * �ϴ��ļ������� �÷������뷵���ļ�����id  ��map.put("file_pk","id");
	 * @param fileItem  �ļ�list�� һ��ֻ��һ���ļ�
	 * @param formDataMap  ������
	 * @return
	 * @throws Exception 
	 */
	public abstract Map<String,String> getFiles(List<FileItem> fileItem,Map<String,String> formDataMap) throws Exception;
	/**
	 * 
	 * @param id �ļ�����id
	 * @return
	 */
	public abstract String deleteFile(String id);
	/**
	 * ����ҳ������ļ���Ϣ
	 * �磺
	 * List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
	*	Map<String, String> map = new HashMap<String, String>();
	*	map.put("id", "3232134");
	*	map.put("name", "��'��''<��.phd");
	*	map.put("fileSize", "20MB");
	*	
	*	listmap.add(map);
	*	Map<String, String> map2 = new HashMap<String, String>();
	*	map2.put("id", "324");
	*	map2.put("name", "vsd�ǡ�������ʾ''����'��.phd");
	*	map2.put("fileSize", "20MB");
	*	listmap.add(map2);
	 * @param listmap
	 * @param id �ϴ����id
	 */
	public void setFilesInfo(String id, List<Map<String, String>> listmap);
}
