package com.insigma.siis.local.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 



import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
 
/**
 * Function : �ļ�ѹ����zip
 * @author  : lqf
 * @Date    : 2015-12-15
 */
public class ZipCompressing {
    private static final Logger logger = LoggerFactory.getLogger(ZipCompressing.class);
 
    static int k = 1; // ����ݹ��������
    public ZipCompressing() {}
 
 
    /**
     * ѹ��ָ���ĵ��������ļ��������Ŀ¼�������Ŀ¼�������ļ�����ѹ��
     * @param zipFileName ZIP�ļ�������ȫ·��
     * @param files  �ļ��б�
     */
    public static boolean zip(String zipFileName, File... files) {
        logger.info("ѹ��: "+zipFileName);
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            createDir(zipFileName);
            out = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (int i = 0; i < files.length; i++) {
                if (null != files[i]) {
                    zip(out, files[i], files[i].getName());
                }
            }
            out.close(); // ������ر�
            logger.info("ѹ�����");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * ִ��ѹ��
     * @param out ZIP������
     * @param f   ��ѹ�����ļ�
     * @param base  ��ѹ�����ļ���
     */
    private static void zip(ZipOutputStream out, File f, String base) { // ��������
        try {
            if (f.isDirectory()) {//ѹ��Ŀ¼
                try {
                    File[] fl = f.listFiles();
                    if (fl.length == 0) {
                        out.putNextEntry(new ZipEntry(base + "/"));  // ����zipʵ��
                        logger.info(base + "/");
                    }
                    for (int i = 0; i < fl.length; i++) {
                        zip(out, fl[i], base + "/" + fl[i].getName()); // �ݹ�������ļ���
                    }
                    //System.out.println("��" + k + "�εݹ�");
                    k++;
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }else{ //ѹ�������ļ�
                //logger.info(base);
                out.putNextEntry(new ZipEntry(base)); // ����zipʵ��
                FileInputStream in = new FileInputStream(f);
                BufferedInputStream bi = new BufferedInputStream(in);
                int b;
                while ((b = bi.read()) != -1) {
                    out.write(b); // ���ֽ���д�뵱ǰzipĿ¼
                }
                out.closeEntry(); //�ر�zipʵ��
                in.close(); // �������ر�
            }
 
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
 
 
    /**
     * Ŀ¼������ʱ���ȴ���Ŀ¼
     * @param zipFileName
     */
    private static void createDir(String zipFileName){
        String filePath = StringUtils.substringBeforeLast(zipFileName, "/");
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {//Ŀ¼������ʱ���ȴ���Ŀ¼
            targetFile.mkdirs();
        }
    }
 
 
    /**
     * @param args
     */
   /* public static void main(String[] args) {
        try {
            ZipCompressing.zip("d:/test3.zip", new File("d:/��ѵ")); //����ѹ��Ŀ¼
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }*/
    
public static void unZipFiles(File zipFile, String descDir) throws IOException {
		
		ZipFile zip = new ZipFile(zipFile);//��������ļ�������
		String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));
		
		File pathFile = new File(descDir+name);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		
		for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + name +"/"+ zipEntryName).replaceAll("\\*", "/");
			
			// �ж�·���Ƿ����,�������򴴽��ļ�·��
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// �ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// ����ļ�·����Ϣ
//			System.out.println(outPath);

			FileOutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		System.out.println("******************��ѹ���********************");
		return;
	}
}
