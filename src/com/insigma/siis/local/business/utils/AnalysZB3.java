package com.insigma.siis.local.business.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Administrator
 * 
 * �������ڶ�ȡzb3�ļ��Լ�����zb3�ļ�
 */
public class AnalysZB3 {

	/**
	 * @param filePath
	 *            ��ȡ����zip��
	 * @return ���ظ�ʽ�� {{"A01_TEMP",A01Temp},{"A02_TEMP,A01Temp"}}
	 * 
	 * �������� 1����ʼ��zipread�� 2����ȡ.zb3�ļ� �õ�xml�ļ��� 3������xml����ȡ����Ҫ������
	 * 4����װxml�γ���Ҫ�����ݸ�ʽ����
	 * 
	 */
	public static List<List<Map<String, Object>>> unZipZb3(String filePath,
			String passwd) throws Exception {

		return null;
	}

	/**
	 * 
	 * @param data
	 *            �����ʽ�� {{"A01_TEMP",A01Temp},{"A02_TEMP,A01Temp"}}
	 * @return �����ʽӦ�����ļ�������ʱд�ɱ��汾���ļ� .zb3
	 */
	public static String zipZb3(List<List<Map<String, Object>>> data) {

		return null;
	}

	/**
	 * list<Map<String,Object>> ����һ��xml
	 */
	public static void listToMap() {

	}
	
	/**
	 *  ��ѹ�������zip��
	 * @param zipFile
	 * @param dest
	 * @param passwd
	 * @throws ZipException
	 */
    public static void unzip(File zipFile, String dest, String passwd) throws ZipException {  
        ZipFile zFile = new ZipFile(zipFile);  // ���ȴ���ZipFileָ������ϵ�.zip�ļ�  
        zFile.setFileNameCharset("GBK");       // �����ļ������룬��GBKϵͳ����Ҫ����  
        if (!zFile.isValidZipFile()) {   // ��֤.zip�ļ��Ƿ�Ϸ��������ļ��Ƿ���ڡ��Ƿ�Ϊzip�ļ����Ƿ��𻵵�  
            throw new ZipException("ѹ���ļ����Ϸ�,���ܱ���.");  
        }  
        File destDir = new File(dest);     // ��ѹĿ¼  
        if (destDir.isDirectory() && !destDir.exists()) {  
            destDir.mkdir();  
        }  
        if (zFile.isEncrypted()) {  
            zFile.setPassword(passwd.toCharArray());  // ��������
        }  
        zFile.extractAll(dest);      // ���ļ��������ѹĿ¼(��ѹ)  
    }  
    
    
    public static void main(String[] args) throws Exception{
		unzip(new File("cc.zip"), "xx", "");
	}
}
