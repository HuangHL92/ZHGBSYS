package com.JUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public interface JUpload {
	/**
	 * 上传文件方法， 该方法必须返回文件主键id  如map.put("file_pk","id");
	 * @param fileItem  文件list， 一般只有一个文件
	 * @param formDataMap  表单数据
	 * @return
	 * @throws Exception 
	 */
	public abstract Map<String,String> getFiles(List<FileItem> fileItem,Map<String,String> formDataMap) throws Exception;
	/**
	 * 
	 * @param id 文件主键id
	 * @return
	 */
	public abstract String deleteFile(String id);
	/**
	 * 设置页面回显文件信息
	 * 如：
	 * List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
	*	Map<String, String> map = new HashMap<String, String>();
	*	map.put("id", "3232134");
	*	map.put("name", "发'生''<的.phd");
	*	map.put("fileSize", "20MB");
	*	
	*	listmap.add(map);
	*	Map<String, String> map2 = new HashMap<String, String>();
	*	map2.put("id", "324");
	*	map2.put("name", "vsd是‘’发电示''范试'点.phd");
	*	map2.put("fileSize", "20MB");
	*	listmap.add(map2);
	 * @param listmap
	 * @param id 上传组件id
	 */
	public void setFilesInfo(String id, List<Map<String, String>> listmap);
}
