package com.insigma.siis.local.pagemodel.xbrm2.zsrm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	private static final int BUFFER_SIZE = 2 * 1024;

	/**
	 * @param srcDir 压缩文件夹路径
	 * @param out 压缩文件输出流
	 * @param KeepDirStructure 是否保留原来的目录结构,
	 * 			true:保留目录结构;
	 *			false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(String[] srcDir, String outDir,
			boolean KeepDirStructure) throws RuntimeException, Exception {

		OutputStream out = new FileOutputStream(new File(outDir));

		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			List<File> sourceFileList = new ArrayList<File>();
			for (String dir : srcDir) {
				File sourceFile = new File(dir);
				sourceFileList.add(sourceFile);
			}
			compress(sourceFileList, zos, KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 递归压缩方法
	 * @param sourceFile 源文件
	 * @param zos zip输出流
	 * @param name 压缩后的名称
	 * @param KeepDirStructure 是否保留原来的目录结构,
	 * 			true:保留目录结构;
	 *			false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos,
			String name, boolean KeepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			zos.putNextEntry(new ZipEntry(name));
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				if (KeepDirStructure) {
					zos.putNextEntry(new ZipEntry(name + "/"));
					zos.closeEntry();
				}

			} else {
				for (File file : listFiles) {
					if (KeepDirStructure) {
						compress(file, zos, name + "/" + file.getName(),
								KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}

				}
			}
		}
	}

	private static void compress(List<File> sourceFileList,
			ZipOutputStream zos, boolean KeepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		for (File sourceFile : sourceFileList) {
			String name = sourceFile.getName();
			if (sourceFile.isFile()) {
				zos.putNextEntry(new ZipEntry(name));
				int len;
				FileInputStream in = new FileInputStream(sourceFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			} else {
				File[] listFiles = sourceFile.listFiles();
				if (listFiles == null || listFiles.length == 0) {
					if (KeepDirStructure) {
						zos.putNextEntry(new ZipEntry(name + "/"));
						zos.closeEntry();
					}

				} else {
					for (File file : listFiles) {
						if (KeepDirStructure) {
							compress(file, zos, name + "/" + file.getName(),
									KeepDirStructure);
						} else {
							compress(file, zos, file.getName(),
									KeepDirStructure);
						}

					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		List list = new ArrayList();
		list.add("C:\\HZB\\zhgbuploadfiles\\9cef281c-b25a-40c6-baa9-951d58f9d3d6\\8b711e85-0f4b-4f1a-a398-6e1570e39fc0.jpg");
		list.add("C:\\HZB\\zhgbuploadfiles\\9cef281c-b25a-40c6-baa9-951d58f9d3d6\\97e70ca7-4eec-4ce6-8680-398ee941f494.jpg");
		list.add("C:\\HZB\\zhgbuploadfiles\\9cef281c-b25a-40c6-baa9-951d58f9d3d6\\bca3c231-60da-48d8-a69c-3a62342ebfb8.jpg" );
		
		
		String[] srcDir = new String[list.size()];
		list.toArray(srcDir);

		String s = srcDir[0];
		if (s.lastIndexOf("\\")>0) {
			s = s.substring(0,s.lastIndexOf("\\")+1);
		}else {
			s = s.substring(0,s.lastIndexOf("/")+1);
		}
		SimpleDateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
		String outDir = s + UUID.randomUUID().toString().toUpperCase().replaceAll("\\-", "")+ d.format(new Date()) + ".zip";
		//System.out.println(outDir);
		ZipUtil.toZip(srcDir, outDir, true);
	}
} 