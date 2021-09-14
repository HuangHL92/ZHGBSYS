package com.insigma.siis.local.business.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Administrator
 * 
 * 该类用于读取zb3文件以及生成zb3文件
 */
public class AnalysZB3 {

	/**
	 * @param filePath
	 *            读取本地zip包
	 * @return 返回格式如 {{"A01_TEMP",A01Temp},{"A02_TEMP,A01Temp"}}
	 * 
	 * 程序流程 1、初始化zipread流 2、读取.zb3文件 得到xml文件流 3、解析xml流获取到需要的数据
	 * 4、包装xml形成需要的数据格式返回
	 * 
	 */
	public static List<List<Map<String, Object>>> unZipZb3(String filePath,
			String passwd) throws Exception {

		return null;
	}

	/**
	 * 
	 * @param data
	 *            输入格式如 {{"A01_TEMP",A01Temp},{"A02_TEMP,A01Temp"}}
	 * @return 输出格式应该是文件流，暂时写成保存本地文件 .zb3
	 */
	public static String zipZb3(List<List<Map<String, Object>>> data) {

		return null;
	}

	/**
	 * list<Map<String,Object>> 生成一个xml
	 */
	public static void listToMap() {

	}
	
	/**
	 *  解压带密码的zip包
	 * @param zipFile
	 * @param dest
	 * @param passwd
	 * @throws ZipException
	 */
    public static void unzip(File zipFile, String dest, String passwd) throws ZipException {  
        ZipFile zFile = new ZipFile(zipFile);  // 首先创建ZipFile指向磁盘上的.zip文件  
        zFile.setFileNameCharset("GBK");       // 设置文件名编码，在GBK系统中需要设置  
        if (!zFile.isValidZipFile()) {   // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等  
            throw new ZipException("压缩文件不合法,可能被损坏.");  
        }  
        File destDir = new File(dest);     // 解压目录  
        if (destDir.isDirectory() && !destDir.exists()) {  
            destDir.mkdir();  
        }  
        if (zFile.isEncrypted()) {  
            zFile.setPassword(passwd.toCharArray());  // 设置密码
        }  
        zFile.extractAll(dest);      // 将文件抽出到解压目录(解压)  
    }  
    
    
    public static void main(String[] args) throws Exception{
		unzip(new File("cc.zip"), "xx", "");
	}
}
