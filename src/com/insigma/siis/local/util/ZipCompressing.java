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
 * Function : 文件压缩成zip
 * @author  : lqf
 * @Date    : 2015-12-15
 */
public class ZipCompressing {
    private static final Logger logger = LoggerFactory.getLogger(ZipCompressing.class);
 
    static int k = 1; // 定义递归次数变量
    public ZipCompressing() {}
 
 
    /**
     * 压缩指定的单个或多个文件，如果是目录，则遍历目录下所有文件进行压缩
     * @param zipFileName ZIP文件名包含全路径
     * @param files  文件列表
     */
    public static boolean zip(String zipFileName, File... files) {
        logger.info("压缩: "+zipFileName);
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
            out.close(); // 输出流关闭
            logger.info("压缩完成");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * 执行压缩
     * @param out ZIP输入流
     * @param f   被压缩的文件
     * @param base  被压缩的文件名
     */
    private static void zip(ZipOutputStream out, File f, String base) { // 方法重载
        try {
            if (f.isDirectory()) {//压缩目录
                try {
                    File[] fl = f.listFiles();
                    if (fl.length == 0) {
                        out.putNextEntry(new ZipEntry(base + "/"));  // 创建zip实体
                        logger.info(base + "/");
                    }
                    for (int i = 0; i < fl.length; i++) {
                        zip(out, fl[i], base + "/" + fl[i].getName()); // 递归遍历子文件夹
                    }
                    //System.out.println("第" + k + "次递归");
                    k++;
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }else{ //压缩单个文件
                //logger.info(base);
                out.putNextEntry(new ZipEntry(base)); // 创建zip实体
                FileInputStream in = new FileInputStream(f);
                BufferedInputStream bi = new BufferedInputStream(in);
                int b;
                while ((b = bi.read()) != -1) {
                    out.write(b); // 将字节流写入当前zip目录
                }
                out.closeEntry(); //关闭zip实体
                in.close(); // 输入流关闭
            }
 
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
 
 
    /**
     * 目录不存在时，先创建目录
     * @param zipFileName
     */
    private static void createDir(String zipFileName){
        String filePath = StringUtils.substringBeforeLast(zipFileName, "/");
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {//目录不存在时，先创建目录
            targetFile.mkdirs();
        }
    }
 
 
    /**
     * @param args
     */
   /* public static void main(String[] args) {
        try {
            ZipCompressing.zip("d:/test3.zip", new File("d:/培训")); //测试压缩目录
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }*/
    
public static void unZipFiles(File zipFile, String descDir) throws IOException {
		
		ZipFile zip = new ZipFile(zipFile);//解决中文文件夹乱码
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
			
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
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
		System.out.println("******************解压完毕********************");
		return;
	}
}
