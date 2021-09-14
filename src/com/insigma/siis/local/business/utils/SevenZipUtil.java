package com.insigma.siis.local.business.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.sevenzipjbinding.ArchiveFormat;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

//压缩Zip文件加密所需要的类，我也没有找到，并未实现其功能，不过方法下面也列出了，希望解决这个问
//题的人可以留言告诉我，谢谢
//import de.idyl.crypto.zip.AesZipFileEncrypter;

public final class SevenZipUtil {

	private static void writeFile(File file, ZipInputStream zipIn) {
		OutputStream outStream = null;
		try {
			outStream = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int len;
			while ((len = zipIn.read(buff)) > 0) {
				outStream.write(buff, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static FileOutputStream fos;

	public static void extractile(String filepath, final String destinationDir,
			String password) {
		RandomAccessFile randomAccessFile = null;
		IInArchive inArchive = null;
		try {
			randomAccessFile = new RandomAccessFile(filepath, "r");
			inArchive = SevenZip.openInArchive(ArchiveFormat.SEVEN_ZIP, // autodetect
																		// archive
																		// type
					new RandomAccessFileInStream(randomAccessFile), password);
			// Getting simple interface of the archive inArchive
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
			CommonQueryBS.systemOut("  Hash  |  Size  | Filename");
			CommonQueryBS.systemOut("----------+------------+---------");

			for (final ISimpleInArchiveItem item : simpleInArchive
					.getArchiveItems()) {
				final int[] hash = new int[] { 0 };
				if (!item.isFolder()) {
					ExtractOperationResult result;
					final long[] sizeArray = new long[1];

					// 判断是否存在目录
					String subdir = item.getPath();
					if (subdir.lastIndexOf("\\") > 0) {
						subdir = subdir.substring(0, subdir.lastIndexOf("\\"));
						File myfile = new File(destinationDir + subdir);
						if (!myfile.exists()) {
							myfile.mkdirs();
						}
					}

					File file = new File(destinationDir + item.getPath());

					fos = new FileOutputStream(file);

					// 解压缩处理带密码的程序
					result = item.extractSlow(new ISequentialOutStream() {
						public int write(byte[] data) throws SevenZipException {
							// Write to file

							try {
								fos.write(data);
							} catch (IOException e) {

								e.printStackTrace();
							}

							// fos.write(data);
							hash[0] ^= Arrays.hashCode(data);// Consume data
							sizeArray[0] += data.length;
							return data.length; // Return amount of consumed
												// data
						}
					}, password);

					if (result == ExtractOperationResult.OK) {
						fos.close();
//						System.out.println(String.format("%9X | %10s | %s", //
//								hash[0], sizeArray[0], item.getPath()));
					} else {
						System.err.println("Error extracting item: " + result);
					}

				} // if 结束
			} // for 循环

		} catch (Exception e) {
			System.err.println("Error occurs: " + e);
			e.printStackTrace();
//			System.exit(1);
		} finally {
			if (inArchive != null) {
				try {
					inArchive.close();
				} catch (SevenZipException e) {
					System.err.println("Error closing archive: " + e);
				}
			}

			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					System.err.println("Error closing file: " + e);
				}
			}

		}
	}
	
	public static void extractileOne(String filepath, final String destinationDir,
			String password) {
		RandomAccessFile randomAccessFile = null;
		IInArchive inArchive = null;
		try {
			randomAccessFile = new RandomAccessFile(filepath, "r");
			inArchive = SevenZip.openInArchive(ArchiveFormat.SEVEN_ZIP, // autodetect
																		// archive
																		// type
					new RandomAccessFileInStream(randomAccessFile), password);
			// Getting simple interface of the archive inArchive
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
			CommonQueryBS.systemOut("  Hash  |  Size  | Filename");
			CommonQueryBS.systemOut("----------+------------+---------");

			for (final ISimpleInArchiveItem item : simpleInArchive
					.getArchiveItems()) {
				final int[] hash = new int[] { 0 };
				if (!item.isFolder()&& item.getPath().equals("gwyinfo.xml")) {
					ExtractOperationResult result;
					final long[] sizeArray = new long[1];

					// 判断是否存在目录
					String subdir = item.getPath();
					if (subdir.lastIndexOf("\\") > 0) {
						subdir = subdir.substring(0, subdir.lastIndexOf("\\"));
						File myfile = new File(destinationDir + subdir);
						if (!myfile.exists()) {
							myfile.mkdirs();
						}
					}

					File file = new File(destinationDir + item.getPath());

					fos = new FileOutputStream(file);

					// 解压缩处理带密码的程序
					result = item.extractSlow(new ISequentialOutStream() {
						public int write(byte[] data) throws SevenZipException {
							// Write to file

							try {
								fos.write(data);
							} catch (IOException e) {

								e.printStackTrace();
							}

							// fos.write(data);
							hash[0] ^= Arrays.hashCode(data);// Consume data
							sizeArray[0] += data.length;
							return data.length; // Return amount of consumed
												// data
						}
					}, password);

					if (result == ExtractOperationResult.OK) {
						fos.close();
					} else {
						System.err.println("Error extracting item: " + result);
					}
					break;
				} // if 结束
			} // for 循环

		} catch (Exception e) {
			System.err.println("Error occurs: " + e);
			e.printStackTrace();
		} finally {
			if (inArchive != null) {
				try {
					inArchive.close();
				} catch (SevenZipException e) {
					System.err.println("Error closing archive: " + e);
				}
			}

			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					System.err.println("Error closing file: " + e);
				}
			}

		}
	}

	private static void extract(File srcZipfile, File destinationDir)
			throws IOException {
		ZipInputStream zipIn = null;
		try {
			zipIn = new ZipInputStream(new FileInputStream(srcZipfile));
			ZipEntry entry = null;
			while ((entry = zipIn.getNextEntry()) != null) {
				String outFilename = entry.getName();
				if (!new File(destinationDir, outFilename).getParentFile()
						.exists())
					new File(destinationDir, outFilename).getParentFile()
							.mkdirs();
				if (!entry.isDirectory())
					writeFile(new File(destinationDir, outFilename), zipIn);
			}
//			CommonQueryBS.systemOut("Zip文件提取成功...");
		} catch (Exception e) {
			e.printStackTrace();
			// StringWriter writer = new StringWriter();
			// e.printStackTrace(new PrintWriter(writer,true));
			// if(writer.toString().indexOf("encrypted ZIP entry not
			// supported")>-1){
			// System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// //
			// SevenZipUtil.unzipDirWithPassword("E:/TestiZip/zip/Encryption.zip",
			// "E:/TestiZip/unZip", "123456");
			// }
		} finally {
			if (zipIn != null) {
				zipIn.close();
			}
		}
	}

	private static void compressDir(String directory, ZipOutputStream zos,
			String path) throws IOException {
		File zipDir = new File(directory);
		String[] dirList = zipDir.list();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;
		for (int i = 0; i < dirList.length; i++) {
			File f = new File(zipDir, dirList[i]);
			if (f.isDirectory()) {
				String filePath = f.getPath();
				compressDir(filePath, zos, path + f.getName() + "/");
				continue;
			}
			FileInputStream fis = new FileInputStream(f);
			try {
				ZipEntry anEntry = new ZipEntry(path + f.getName());
				zos.putNextEntry(anEntry);
				bytesIn = fis.read(readBuffer);
				while (bytesIn != -1) {
					zos.write(readBuffer, 0, bytesIn);
					bytesIn = fis.read(readBuffer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fis.close();
			}
		}
	}

	public static void zipDir(String dirName, String zipFileName) {
		if (zipFileName == null) {
			File tempFile = new File(dirName);
			zipFileName = tempFile.getAbsoluteFile().getParent()
					+ File.separator + tempFile.getName() + ".zip";
		}
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					zipFileName));
			compressDir(dirName, zos, new File(dirName).getName()
					+ File.separator);
			zos.close();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void unzipDir(String sourceZipFile, String destinationDir) {
		try {
			extract(new File(sourceZipFile), new File(destinationDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unzipDirWithPassword(final String sourceZipFile,
			final String destinationDir, final String password) {

		RandomAccessFile randomAccessFile = null;
		IInArchive inArchive = null;
		try {
			// "r"创建一个指读的文件
			randomAccessFile = new RandomAccessFile(sourceZipFile, "r");
			// autodetect archive type
			inArchive = SevenZip.openInArchive(null,
					new RandomAccessFileInStream(randomAccessFile));
			// Getting simple interface of the archive inArchive
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
			for (final ISimpleInArchiveItem item : simpleInArchive
					.getArchiveItems()) {
				if (!item.isFolder()) {
					if (!"".equals(password) || password != null) {
						unzipDirWithPassword(item, destinationDir, password);
					} else {
						unzipDirWithUnpassword(item, sourceZipFile,
								destinationDir);
					}
				}
			}
		} catch (Exception e) {
			// 未处理的异常
			System.err.println("Error openInArchive archive: " + e);
			e.printStackTrace();
		} finally {
			if (inArchive != null) {
				try {
					inArchive.close();
				} catch (SevenZipException e) {
					System.err.println("Error closing archive: " + e);
					e.printStackTrace();
				}
			}
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					System.err.println("Error closing file: " + e);
					e.printStackTrace();
				}
			}
		}
	}

	protected static ExtractOperationResult unzipDirWithPassword(
			final ISimpleInArchiveItem item, final String destinationDir,
			String password) throws SevenZipException {
		final int[] hash = new int[] { 0 };
		ExtractOperationResult result;
		result = item.extractSlow(new ISequentialOutStream() {
			public int write(final byte[] data) throws SevenZipException {
				return SevenZipUtil.write(item, destinationDir, hash, data);
			}
		}, password); // / password.
		if (result == ExtractOperationResult.OK) {
			CommonQueryBS.systemOut(String.format("%9X | %s", hash[0], item
					.getPath()));
		} else {
			System.err.println("Error extracting item: " + result);
		}
		return result;
	}

	protected static ExtractOperationResult unzipDirWithUnpassword(
			final ISimpleInArchiveItem item, final String sourceZipFile,
			final String destinationDir) throws SevenZipException {
		final int[] hash = new int[] { 0 };
		ExtractOperationResult result;
		result = item.extractSlow(new ISequentialOutStream() {
			public int write(final byte[] data) throws SevenZipException {
				return SevenZipUtil.write(item, destinationDir, hash, data);
			}
		}); // / password.
		if (result == ExtractOperationResult.OK) {
			CommonQueryBS.systemOut(String.format("%9X | %s", hash[0], item
					.getPath()));
			// ExtractOperationResult.DATAERROR
			// rar加密文件,ExtractOperationResult.CRCERROR zip加密文件
		} else if (result == ExtractOperationResult.CRCERROR
				|| result == ExtractOperationResult.DATAERROR) {
			unzipDirWithPassword(sourceZipFile, destinationDir, "123456");
		} else {
			System.err.println("Error extracting item: " + result);
		}
		return result;
	}

	protected static int write(ISimpleInArchiveItem item,
			final String destinationDir, int[] hash, byte[] data) {
		OutputStream out = null;
		try {
			if (item.getPath().indexOf(File.separator) > 0) {
				String path = destinationDir
						+ File.separator
						+ item.getPath().substring(0,
								item.getPath().lastIndexOf(File.separator));
				File folderExisting = new File(path);
				if (!folderExisting.exists())
					new File(path).mkdirs();
			}
			out = new FileOutputStream(destinationDir + File.separator
					+ item.getPath());
			out.write(data);
			out.close();
		} catch (Exception e) {
			// 未处理的异常
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioex2) {
					System.err.println(SevenZipUtil.class.getName()
							+ ".mymethod – 不能关闭输出文件" + ioex2.toString());
				}
			}
		}
		hash[0] |= Arrays.hashCode(data);
		return data.length; // Return amount of proceed data
	}

	public static void zip7z(String dirName, String zipFileName, String password) {
//		String cmdZzb= AppConfig.CMD_7Z_EXE;
		String cmd7z= getRootPath() + "\\7-Zip\\7z.exe";
		CommandUtil util = new CommandUtil();
		try {
			if (password != null) {
//				String cmd="cmd /c \""+cmd7z+"\" a  "+zipFileName+" -p"+password+" -mhe  "+dirName+"\\*";
				String cmd=" \""+cmd7z+"\" a  \""+zipFileName+"\" -p"+password+" -m0=LZMA:d=20 -mhe -ms=off \""+dirName+"\\*\"";
				util.executeCommand(cmd);
				util.printList(util.getErroroutList());
				CommonQueryBS.systemOut("-----");
				util.printList(util.getStdoutList());
			} else { 
				String cmd=" \""+cmd7z+"\" a -tzip \""+zipFileName+"\" \""+dirName+"\\*\"";
				CommonQueryBS.systemOut(cmd);
				util.executeCommand(cmd);
				util.printList(util.getErroroutList());
				CommonQueryBS.systemOut("-----");
				util.printList(util.getStdoutList());
			}
		} catch (Exception ie) {
			ie.printStackTrace();
		}
	}
	public static String getRootPath() {
		String classPath = SevenZipUtil.class.getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath + "softTools";
	}
	public static void zzbRmb(String zippath, String name) {
//		String cmdZzb= AppConfig.CMD_ZZBRMBService_EXE;
		String cmdZzb= getRootPath() + "\\任免表编辑器V3.0\\ZZBRMBService.exe";
		CommandUtil util = new CommandUtil();
		try {
				String cmd="\""+cmdZzb+"\" \""+zippath+name+".lrm\""+" \""+zippath+"\"";
				CommonQueryBS.systemOut(cmd);
				util.executeCommand(cmd);
				util.printList(util.getErroroutList());
				CommonQueryBS.systemOut("-----");
				util.printList(util.getStdoutList());
		} catch (Exception ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
//		unzipDirWithPassword("aa.zb3", "zun", "1234");
//		unzipDirWithPassword("d:/按机构导出文件_D02-112-112_浙江省20160523105841.hzb", "d:/temp", "1234");
//		unzipDirWithPassword("E:/按机构导出文件_D49.514_广东省地方税务局20160216115533.ZB3", "E:/temp", "");
		String cmdZzb= AppConfig.CMD_7Z_EXE;
//		String cmd7z= getRootPath() + "\\7-Zip\\7z.exe";
		CommandUtil util = new CommandUtil();
		try {
				String cmd="\""+cmdZzb+"\"s\"sssaaa.lrm\""+" -1dd \"ssss\"";
				CommonQueryBS.systemOut(cmd);
				util.executeCommand(cmd);
				util.printList(util.getErroroutList());
				CommonQueryBS.systemOut("-----");
				util.printList(util.getStdoutList());
		} catch (Exception ie) {
			ie.printStackTrace();
		}

	}
}