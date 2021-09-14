package com.insigma.siis.local.pagemodel.huiyi;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;

import static com.insigma.siis.local.pagemodel.sysmanager.role.addRolePageModel.log;

/**
 * @author genggaopeng
 * @date 2019-12-20 11:16
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String dirPath = "D:\\work\\dfjybt\\out\\artifacts\\dfjybt_war_exploded\\HUIYICAILIAO";
        String dbPath = "D:\\work\\dfjybt\\out\\artifacts\\dfjybt_war_exploded\\中文.txt";
        String filePath = "D:/work/a.zip";


        /*ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(filePath))));
        zos.setEncoding("gbk");
        writeZip(new File(dbPath), "", zos);
        writeZip(new File(dirPath), "", zos);
        zos.close();*/
        File file = new File(dirPath);
        File[] files = file.listFiles();
        String parent = files[0].getParent();
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else {
                    try {
                        zos.putNextEntry(new org.apache.tools.zip.ZipEntry(parentPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }
}
