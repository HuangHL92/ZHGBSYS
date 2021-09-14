package com.insigma.siis.local.epsoft.fileList;

import java.io.File;

import com.insigma.odin.framework.radow.RadowException;

public class FileListPathUtil {
	
	/**
	 * @���� ��ȡ��ǰӦ�õĸ�Ŀ¼
	 * @return String
	 */
	public String getFileListPath() throws RadowException{
		String serverRealPath="";
		try {
			serverRealPath = this.getClass().getClassLoader().getResource("/").toURI().getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File f = new File(serverRealPath).getParentFile().getParentFile();
		String path = f.toString()+"/";
		path = path.replace("\\", "/");
		return path;
	}
}
