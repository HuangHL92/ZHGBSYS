package com.insigma.siis.local.pagemodel.dataverify;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A06temp;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A11temp;
import com.insigma.siis.local.business.entity.A14temp;
import com.insigma.siis.local.business.entity.A15temp;
import com.insigma.siis.local.business.entity.A29temp;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A30temp;
import com.insigma.siis.local.business.entity.A31temp;
import com.insigma.siis.local.business.entity.A36temp;
import com.insigma.siis.local.business.entity.A37temp;
import com.insigma.siis.local.business.entity.A41temp;
import com.insigma.siis.local.business.entity.A53temp;
import com.insigma.siis.local.business.entity.A57temp;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01temp;
import com.insigma.siis.local.business.entity.B01tempb01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class UploadHelpFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;

	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
	
	public InputStream getInputStream(File photo) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024*4];
		int n = -1;
		InputStream fileInput = null;
		InputStream inputStream = null;
		try {
			fileInput = new FileInputStream(photo);
			while ((n=fileInput.read(buffer)) != -1) {
				baos.write(buffer, 0, n);
			}
			byte[] byteArray = baos.toByteArray();
			inputStream = new ByteArrayInputStream(byteArray);
			fileInput.close();
			baos.flush();
			baos.close();
			return inputStream;
		} catch (FileNotFoundException e) {
			try {
				if(fileInput !=null)
					fileInput.close();
				if(baos !=null)
					baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			try {
				if(fileInput !=null)
					fileInput.close();
				if(baos !=null)
					baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					if(fileInput !=null)
						fileInput.close();
					if(baos !=null)
						baos.close();
//					if(fileInput !=null)
//					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ��ȡ��id
	 * @param impdeptid
	 * @param b0111
	 * @param tempb 
	 * @return
	 */
	public static String dealB0111(String impdeptid, String b0111, String imprecordid) {
		HBSession sess =HBUtil.getHBSession();
		B01tempb01 tempb = new B01tempb01();
		tempb.setImprecordid(imprecordid);
		tempb.setTempb0111(b0111);
		tempb.setNewb0111(impdeptid);
		sess.save(tempb);
		List<B01temp> b01ts = sess.createQuery(" from B01temp where imprecordid='"+imprecordid+"' and b0121<>b0111 and b0121='"+ b0111 +"'").list();
		if(b01ts!=null && b01ts.size()>0){
			for (int i = 0; i < b01ts.size(); i++) {
				B01temp temp = b01ts.get(i);
				String impdeptidnew = getNewB0111(impdeptid,i);
				dealB0111(impdeptidnew, temp.getB0111(), imprecordid);
			}
		}
		return "1";
	}
	
	public static String getNewB0111(String impdeptid, int i) {
		i = i + 1;
		int num1 = 0;//��
		int num2 = 0;//ʮ
		int num3 = 0;//��
		String[] key={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"
				,"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
				,"Y","Z"};	
		num3 = i%36;
		int num2_1 = i/36;
		if(num2_1>0){
			num2 = num2_1%36;
		}
		int num1_1 = num2_1/36;
		if(num1_1>0){
			num1 = num1_1%36;
		}
		String str = impdeptid + "." + key[num1] + key[num2] + key[num3];
		return str;
	}

	/* �ж��Ƿ������� */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public boolean deleteFile(String filename) throws AppException {
		java.io.File dir = new java.io.File(filename);
		boolean b = true;
		if (dir.exists()) {
			b = dir.delete();
		}
		return b;
	}

	// ����ļ���ʽ
	public String checkFile(FileItem item) {
		String filename = item.getName();
		filename = filename.toLowerCase();
		if (filename.endsWith(".zb3")||filename.endsWith(".hzb")) {
			return "0";
		} else {
			return "-1";
		}
	}

	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}

	private static void copyPhotos(String to_file, String from_file) {
		// TODO Auto-generated method stub
		/**
		 * ȱ���ж��ļ�Ϊ�մ��롣��������������������������
		 */
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(from_file)).listFiles();
		try {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// �����ļ�
					copyFile(file[i], new File(to_file + file[i].getName()));
				}
				if (file[i].isDirectory()) {
					// ����Ŀ¼
					String sorceDir = from_file + File.separator
							+ file[i].getName();
					String targetDir = to_file + File.separator
							+ file[i].getName();
					copyDirectiory(sorceDir, targetDir);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {
		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// �½��ļ���������������л���
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// ��������
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		// ˢ�´˻���������
		outbuff.flush();

		// �ر���
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е��µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());

				copyFile(sourceFile, targetFile);

			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
				String dir2 = targetDir + "/" + file[i].getName();

				copyDirectiory(dir1, dir2);
			}
		}

	}
}
