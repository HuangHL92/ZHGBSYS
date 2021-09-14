package com.insigma.siis.local.pagemodel.sysmanager.photoconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import sun.applet.Main;

/**
 * 
 * @author zhaoyd
 * @version 1.0 �ļ����ļ��и��ƵĹ�����
 */
public class FileUtil {
	/**
	 * �ƶ�ָ���ļ����ļ���(���������ļ������ļ���)
	 * 
	 * @param fromDir
	 *            Ҫ�ƶ����ļ����ļ���
	 * @param toDir
	 *            Ŀ���ļ���
	 * @throws Exception
	 */
	public static void MoveFolderAndFileWithSelf(String from, String to) throws Exception {
		try {
			File dir = new File(from);
			// Ŀ��
			to += File.separator + dir.getName();
			File moveDir = new File(to);
			if (dir.isDirectory()) {
				if (!moveDir.exists()) {
					moveDir.mkdirs();
				}
			} else {
				File tofile = new File(to);
				dir.renameTo(tofile);
				return;
			}

			// �ļ�һ��
			File[] files = dir.listFiles();
			if (files == null)
				return;

			// �ļ��ƶ�
			for (int i = 0; i < files.length; i++) {
				// System.out.println("�ļ�����"+files[i].getName());
				if (files[i].isDirectory()) {
					MoveFolderAndFileWithSelf(files[i].getPath(), to);
					// �ɹ���ɾ��ԭ�ļ�
					files[i].delete();
				}
				File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());
				// Ŀ���ļ����´��ڵĻ���ɾ��
				if (moveFile.exists()) {
					moveFile.delete();
				}
				files[i].renameTo(moveFile);
			}
			dir.delete();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ���Ƶ����ļ�(�ɸ�������)
	 * 
	 * @param oldPathFile
	 *            ׼�����Ƶ��ļ�Դ
	 * @param newPathFile
	 *            �������¾���·�����ļ���(ע��Ŀ¼·������ļ���)
	 * @return
	 */
	public static void CopySingleFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // �ļ�����ʱ
				InputStream inStream = new FileInputStream(oldPathFile); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���Ƶ����ļ�(ԭ������)
	 * 
	 * @param oldPathFile
	 *            ׼�����Ƶ��ļ�Դ
	 * @param newPathFile
	 *            �������¾���·��
	 * @return
	 */
	public static void CopySingleFileTo(String oldPathFile, String targetPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			String targetfile = targetPath + File.separator + oldfile.getName();
			if (oldfile.exists()) { // �ļ�����ʱ
				InputStream inStream = new FileInputStream(oldPathFile); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(targetfile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������ļ��е�����(������)
	 * 
	 * @param oldPath
	 *            ׼��������Ŀ¼
	 * @param newPath
	 *            ָ������·������Ŀ¼
	 * @return
	 */
	public static void copyFolderWithSelf(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // ����ļ��в����� �������ļ���
			File dir = new File(oldPath);
			// Ŀ��
			newPath += File.separator + dir.getName();
			File moveDir = new File(newPath);
			if (dir.isDirectory()) {
				if (!moveDir.exists()) {
					moveDir.mkdirs();
				}
			}
			String[] file = dir.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) { // ��������ļ���
					copyFolderWithSelf(oldPath + "/" + file[i], newPath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * cmd������ļ����µ��������ݣ�����������
	 */
	public static void copyChildFolder(String oldPath, String newPath,String id) {
		Runtime runtime = Runtime.getRuntime();
		HBSession sess = HBUtil.getHBSession();
		try {
			File saveFile = new File(newPath);
			if (!saveFile.exists()) {// ���Ŀ¼������
				saveFile.mkdirs();// �����ļ���
			}
			Process p = runtime.exec("cmd /c  xcopy " + oldPath.replace("/", "\\\\") + " " + newPath.replace("/", "\\\\") + " /E /Y");
			 final InputStream is1 = p.getInputStream();   
			 //��ȡ���ǵĴ�����  
			 final InputStream is2 = p.getErrorStream();  
			 //���������̣߳�һ���̸߳������׼���������һ���������׼������  
			 new Thread() {  
			    public void run() {  
			       BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));  
			        try {  
			            String line1 = null;  
			            while ((line1 = br1.readLine()) != null) {  
			                  if (line1 != null){}  
			              }  
			        } catch (IOException e) {  
			             e.printStackTrace();  
			        }  
			        finally{  
			             try {  
			               is1.close();  
			             } catch (IOException e) {  
			                e.printStackTrace();  
			            }  
			          }  
			        }  
			     }.start();  
			                                
			   new Thread() {   
			      public void  run() {   
			       BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));   
			          try {   
			             String line2 = null ;   
			             while ((line2 = br2.readLine()) !=  null ) {   
			                  if (line2 != null){}  
			             }   
			           } catch (IOException e) {   
			                 e.printStackTrace();  
			           }   
			          finally{  
			             try {  
			                 is2.close();  
			             } catch (IOException e) {  
			                 e.printStackTrace();  
			             }  
			           }  
			        }   
			      }.start(); 
			      
			      p.waitFor();
			sess.createSQLQuery("update aa01 set  active='1' where aaa001='"+id+"'").executeUpdate();

			CommonQueryBS.systemOut("�ļ��������");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}

