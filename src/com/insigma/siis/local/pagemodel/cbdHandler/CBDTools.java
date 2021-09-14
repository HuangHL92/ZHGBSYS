package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class CBDTools {

	/**
	 *  修改xml的状态值
	 * @param value:cbd_id@filePath@status
	 * @return
	 * @throws Exception
	 */
	public int changeStatus(String value) {
		
		//分割参数
		String[] values = value.split("@");
		String cbd_id = values[0];
		String filePath = values[1];
		String status = values[2];
		String packXmlStr = null;//总包文件内容字符串
		packXmlStr = FileUtil.readFileByChars(filePath,"UTF-8"); //读取总包文件内容
		if(packXmlStr!=null){
			ZwhzPackDefine zpdefine = null;//总包文件解析对象
			try {
				zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class);
				zpdefine.setCbdStatus(status);
				File file_1 = new File(filePath);
				if(file_1.exists()){
					file_1.delete();
				}
				//创建xml文件
				FileUtil.createFile(filePath,
						JXUtil.Object2Xml(zpdefine, true), false, "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //解析总包文件
		}
		
		return 0;
	}
	
	/**
	 * 获取文件的size
	 * @param f
	 * @return
	 */
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}
	
	/**
	 * 工具方法：获取路径
	 * @return
	 */
	public String getPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";

		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	/**
     * 生产文件 如果文件所在路径不存在则生成路径
     * 
     * @param fileName
     *            文件名 带路径
     * @param isDirectory 是否为路径
     * @return
     * @author yayagepei
     * @date 2008-8-27
     */
    public static File buildFile(String fileName, boolean isDirectory) {
        File target = new File(fileName);
        if (isDirectory) {
            target.mkdirs();
        } else {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
                target = new File(target.getAbsolutePath());
            }
        }
        return target;
    } 
    
    /**
     * 解压缩zip包
     * 
     * @param zipFilePath
     *            zip文件路径
     * @param targetPath
     *            解压缩到的位置，如果为null或空字符串则默认解压缩到跟zip包同目录跟zip包同名的文件夹下
     * @throws IOException
     * @author yayagepei
     * @date 2008-9-28
     */
    public static void unzip(String zipFilePath, String targetPath)
            throws IOException {
        OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFilePath);
            String directoryPath = "";
            if (null == targetPath || "".equals(targetPath)) {
                directoryPath = zipFilePath.substring(0, zipFilePath
                        .lastIndexOf("."));
            } else {
                directoryPath = targetPath;
            }
            Enumeration entryEnum = zipFile.getEntries();
            if (null != entryEnum) {
                ZipEntry zipEntry = null;
                while (entryEnum.hasMoreElements()) {
                    zipEntry = (ZipEntry) entryEnum.nextElement();
                   if (zipEntry.isDirectory()) {
                        directoryPath = directoryPath + File.separator
                                + zipEntry.getName();
                        CommonQueryBS.systemOut(directoryPath);
                        continue;
                    }
                    if (zipEntry.getSize() > 0) {
                        // 文件
                        File targetFile = CBDTools.buildFile(directoryPath
                                + File.separator + zipEntry.getName(), false);
                        os = new BufferedOutputStream(new FileOutputStream(
                                targetFile));
                        is = zipFile.getInputStream(zipEntry);
                        byte[] buffer = new byte[4096];
                        int readLen = 0;
                       while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
                            os.write(buffer, 0, readLen);
                        }

                        os.flush();
                        os.close();
                    } else {
                        // 空目录
                    	CBDTools.buildFile(directoryPath + File.separator
                                + zipEntry.getName(), true);
                    }
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if(null != zipFile){
                zipFile = null;
            }
            if (null != is) {
                is.close();
            }
            if (null != os) {
                os.close();
            }
        }
    }
	    
	
}
