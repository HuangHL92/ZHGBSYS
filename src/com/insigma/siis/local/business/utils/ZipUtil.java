package com.insigma.siis.local.business.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
  * @Description: 
  * 	ѹ���ͽ�ѹ����
 */
public class ZipUtil {

	/**
	  * @Description: 
	  *     ѹ���ļ�
	  * @param sourcePath ��Ҫѹ�����ļ���Ŀ¼��·������ʹ�þ���·��
	  * @param zipPath ����ѹ���ļ���·������ʹ�þ���·���������·���ԡ�.zip��Ϊ��β��
	  * 		��ѹ���ļ�������Ϊ��·���������·�����ԡ�.zip��Ϊ��β����ѹ���ļ�������
	  * 		Ϊ��·�����Ͻ�Ҫѹ�����ļ���Ŀ¼�����ƣ��ټ����ԡ�.zip����β
	  * @param encoding ѹ������
	  * @param comment ѹ��ע��
	 */
	public static void compress(String sourcePath, String zipPath, String encoding, String comment)
			throws FileNotFoundException, IOException {
		// �ж�Ҫѹ�����ļ��Ƿ����
		File sourceFile = new File(sourcePath);
		if (!sourceFile.exists() || (sourceFile.isDirectory() && sourceFile.list().length == 0)) {
			throw new FileNotFoundException("Ҫѹ�����ļ���Ŀ¼�����ڣ�����Ҫѹ����Ŀ¼Ϊ��");
		}
		// ����ѹ���ļ�·����Ĭ��Ϊ��Ҫѹ����·���ĸ�Ŀ¼Ϊѹ���ļ��ĸ�Ŀ¼
		if (zipPath == null || "".equals(zipPath)) {
			String sourcePathName = sourceFile.getAbsolutePath();
			int index = sourcePathName.lastIndexOf(".");
			zipPath = (index > -1 ? sourcePathName.substring(0, index) : sourcePathName) + ".zip";
		} else {
			// ���ѹ��·��ΪĿ¼����Ҫѹ�����ļ���Ŀ¼����Ϊѹ���ļ������֣�����ѹ��·�����ԡ�.zip��Ϊ��β����Ϊѹ��·��ΪĿ¼
			if(!zipPath.endsWith(".zip")){
				// �����Ҫѹ����·��ΪĿ¼�����Դ�Ŀ¼��Ϊѹ���ļ����������Ҫѹ����·��Ϊ�ļ������Դ��ļ�����ȥ����չ����Ϊѹ���ļ���
				String fileName = sourceFile.getName();
				int index = fileName.lastIndexOf(".");
				zipPath = zipPath + File.separator + (index > -1 ? fileName.substring(0, index) : fileName) + ".zip";
			}
		}
		// ���ý�ѹ����
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		// Ҫ������ѹ���ļ��ĸ�Ŀ¼�����ڣ��򴴽�
		File zipFile = new File(zipPath);
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
		}
		// ����ѹ���ļ������
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(zipPath);
		} catch (FileNotFoundException e) {
			if (fos != null) {
				try{ fos.close(); } catch (Exception e1) {}
			}
		}
		// ʹ��ָ��У��ʹ��������
		CheckedOutputStream csum = new CheckedOutputStream(fos, new CRC32());
		// ����ѹ����
		ZipOutputStream zos = new ZipOutputStream(csum);
		// ���ñ��룬֧������
		zos.setEncoding(encoding);
		// ����ѹ����ע��
		zos.setComment(comment);
		// ����ѹ��
		zos.setMethod(ZipOutputStream.DEFLATED);
		// ����ѹ������Ϊ��ǿѹ��
		zos.setLevel(Deflater.BEST_COMPRESSION);
		// ѹ���ļ�������
		BufferedOutputStream bout = null;
		try {
			// ��װѹ����Ϊ������
			bout = new BufferedOutputStream(zos);
			// ������Դ����ѹ��
			compressRecursive(zos, bout, sourceFile, sourceFile.getParent());
		} finally {
			if (bout != null) {
				try{ bout.close(); } catch (Exception e) {}
			}
		}
	}

	/**
	  * @Description: 
	  *     ѹ���ļ���֧�ֽ�����ļ���Ŀ¼ѹ����ͬһ��ѹ���ļ���
	  * @param sourcePath ��Ҫѹ�����ļ���Ŀ¼��·���ļ��ϣ���ʹ�þ���·��
	  * @param zipPath ����ѹ���ļ���·������ʹ�þ���·������·����Ϊ�գ����ұ����ԡ�.zip��Ϊ��β
	  * @param encoding ѹ������
	  * @param comment ѹ��ע��
	 */
	public static void compress(List<String> sourcePaths, String zipPath, String encoding, String comment)
			throws FileNotFoundException, IOException {
		// ����ѹ���ļ�·����Ĭ��Ϊ��Ҫѹ����·���ĸ�Ŀ¼Ϊѹ���ļ��ĸ�Ŀ¼
		if (zipPath == null || "".equals(zipPath) || !zipPath.endsWith(".zip")) {
			throw new FileNotFoundException("����ָ��һ��ѹ��·�������Ҹ�·��������'.zip'Ϊ��β");
		}
		// ���ý�ѹ����
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		// Ҫ������ѹ���ļ��ĸ�Ŀ¼�����ڣ��򴴽�
		File zipFile = new File(zipPath);
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
		}
		// ����ѹ���ļ������
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(zipPath);
		} catch (FileNotFoundException e) {
			if (fos != null) {
				try{ fos.close(); } catch (Exception e1) {}
			}
		}
		// ʹ��ָ��У��ʹ��������
		CheckedOutputStream csum = new CheckedOutputStream(fos, new CRC32());
		// ����ѹ����
		ZipOutputStream zos = new ZipOutputStream(csum);
		// ���ñ��룬֧������
		zos.setEncoding(encoding);
		// ����ѹ����ע��
		zos.setComment(comment);
		// ����ѹ��
		zos.setMethod(ZipOutputStream.DEFLATED);
		// ����ѹ������Ϊ��ǿѹ��
		zos.setLevel(Deflater.BEST_COMPRESSION);
		// ѹ���ļ�������
		BufferedOutputStream bout = null;
		try {
			// ��װѹ����Ϊ������
			bout = new BufferedOutputStream(zos);
			// ����ѹ��ÿһ��·��
			for (int i=0,len=sourcePaths.size(); i<len; i++) {
				// ��ȡÿһ��ѹ��·��
				File sourceFile = new File(sourcePaths.get(i));
				// ������Դ����ѹ��
				compressRecursive(zos, bout, sourceFile, sourceFile.getParent());
			}
		} finally {
			if (bout != null) {
				try{ bout.close(); } catch (Exception e) {}
			}
		}
	}
	
	/**
	  * @Description: 
	  * 	ѹ���ļ�ʱ����ʹ�õĵ�������
	  * @param zos ѹ�������
	  * @param bout ��װѹ��������Ļ�����
	  * @param sourceFile ��Ҫѹ�����ļ���Ŀ¼��·��
	  * @param prefixDir ������Ҫѹ�����ļ���Ŀ¼�ĸ�Ŀ¼�������ֵΪ�˻�ȡѹ����Ŀ������
	 */
	private static void compressRecursive(ZipOutputStream zos, BufferedOutputStream bout,
            File sourceFile, String prefixDir) throws IOException, FileNotFoundException {
		// ��ȡѹ����Ŀ������ʼʱ��Ҫѹ�����ļ���Ŀ¼�����·��
		String entryName = sourceFile.getAbsolutePath().substring(prefixDir.length() + File.separator.length());
		// �ж����ļ�����Ŀ¼�������Ŀ¼�����������ѹ��
		if (sourceFile.isDirectory()) {
			// �����Ŀ¼������Ҫ��Ŀ¼������Ϸָ���('/')
			//ZipEntry zipEntry = new ZipEntry(entryName + File.separator);
			//zos.putNextEntry(zipEntry);
			// ��ȡĿ¼�е��ļ���Ȼ�����ѹ��
			File[] srcFiles = sourceFile.listFiles();
			for (int i = 0; i < srcFiles.length; i++) {
				// ѹ��
				compressRecursive(zos, bout, srcFiles[i], prefixDir);
			}
		} else {
			// ��ʼд���µ�ZIP�ļ���Ŀ��������λ����Ŀ���ݵĿ�ʼ��
			ZipEntry zipEntry = new ZipEntry(entryName);
			// ��ѹ������д��һ���µ���Ŀ
			zos.putNextEntry(zipEntry);
			// ��ȡ��Ҫѹ�����ļ���������
			BufferedInputStream bin = null;
			try{
				// ��ȡ��������ȡ�ļ�
				bin = new BufferedInputStream(new FileInputStream(sourceFile));
				// ��ȡ�ļ�����д��ѹ����
				byte[] buffer = new byte[1024];
				int readCount = -1;
				while ((readCount = bin.read(buffer)) != -1) {
					bout.write(buffer, 0, readCount);
				}
				// ע����ʹ�û�����дѹ���ļ�ʱ��һ���������һ��Ҫˢ�£���Ȼ�����е����ݾͻ���뵽������Ŀ��ȥ��
				bout.flush();
				// �رյ�ǰZIP��Ŀ����λ����д����һ����Ŀ
				zos.closeEntry();
			} finally {
				if (bin != null) {
					try { bin.close(); } catch (IOException e) {}
				}
			}
		}
	}
	
	/**
	  * @Description: 
	  * 	��ѹ�ļ�
	  * @param zipPath ��ѹ���ļ�����ʹ�þ���·��
	  * @param targetPath ��ѹ·������ѹ����ļ���������Ŀ¼�У���ʹ�þ���·��
	  * 		Ĭ��Ϊѹ���ļ���·���ĸ�Ŀ¼Ϊ��ѹ·��
	  * @param encoding ��ѹ����
	 */
	public static void decompress(String zipPath, String targetPath, String encoding)
			throws FileNotFoundException, ZipException, IOException {
		// ��ȡ�����ļ�
		File file = new File(zipPath);
		if (!file.isFile()) {
			throw new FileNotFoundException("Ҫ��ѹ���ļ�������");
		}
		// ���ý�ѹ·��
		if (targetPath == null || "".equals(targetPath)) {
			targetPath = file.getParent();
		}
		// ���ý�ѹ����
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		// ʵ����ZipFile����
		ZipFile zipFile = new ZipFile(file, encoding);
		// ��ȡZipFile�е���Ŀ
		Enumeration<ZipEntry> files = zipFile.getEntries();
		// �����е�ÿһ����Ŀ
		ZipEntry entry = null;
		// ��ѹ����ļ�
		File outFile = null;
		// ��ȡѹ���ļ���������
		BufferedInputStream bin = null;
		// д���ѹ���ļ��������
		BufferedOutputStream bout = null;
		while (files.hasMoreElements()) {
			// ��ȡ��ѹ��Ŀ
			entry = files.nextElement();
			// ʵ������ѹ���ļ�����
			outFile = new File(targetPath + File.separator + entry.getName());
			// �����ĿΪĿ¼����������һ��
			if (entry.getName().endsWith(File.separator)||entry.getName().endsWith("/")) {
				outFile.mkdirs();
				continue;
			}
			// ����Ŀ¼
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			// �������ļ�
			outFile.createNewFile();
			// �������д����������һ����Ŀ
			if (!outFile.canWrite()) {
				continue;
			}
			try {
				// ��ȡ��ȡ��Ŀ��������
				bin = new BufferedInputStream(zipFile.getInputStream(entry));
				// ��ȡ��ѹ���ļ��������
				bout = new BufferedOutputStream(new FileOutputStream(outFile));
				// ��ȡ��Ŀ����д���ѹ���ļ�
				byte[] buffer = new byte[1024];
				int readCount = -1;
				while ((readCount = bin.read(buffer)) != -1) {
					bout.write(buffer, 0, readCount);
				}
			} finally {
				try {
					bin.close();
					bout.flush();
					bout.close();
				} catch (Exception e) {}
			}
		}
	}
	
	
	
	
}