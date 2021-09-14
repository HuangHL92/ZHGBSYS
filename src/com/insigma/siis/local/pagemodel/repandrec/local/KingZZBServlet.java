package com.insigma.siis.local.pagemodel.repandrec.local;

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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.A01;
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
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.repandrec.local.ImpThread;

public class KingZZBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;

	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("zzb3FileImp")) {//
			zzb3FileImp(request, response); 
		} else if (method.equals("deptSelect")) {//
			deptSelect(request, response); 
		} else if (method.equals("fileSelect")) {//
			fileSelect(request, response); 
		}
		 
	} 
	private void deptSelect(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		PrintWriter out = null;
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// ҵ����bs
		try {
			out= response.getWriter();
			String info = uploadbs.getdeptSelect();
			info = info.substring(0,info.length()-1);
			out.println("7,"+info);
			CommonQueryBS.systemOut("1,"+info);
		} catch (Exception e) {
			out.println("8");
			e.printStackTrace();
		}
	}
	
	private void fileSelect(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		PrintWriter out = null;
		HBSession sess = HBUtil.getHBSession();
		String ZZB3_FILE = "D:/KingbsData";
		try {
			out= response.getWriter();
			List<Dataexchangeconf> list = sess.createQuery("from Dataexchangeconf").list();
			if (list !=null && list.size()>0) {
				ZZB3_FILE = list.get(0).getZzbthreepath();
			} else {
				out.println("2����������Ϣ");
				return;
			}
			File file = new File(ZZB3_FILE);
			String info = "";
			if(file.exists()){
				File[] subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0){
					for (int i = 0; i < subFiles.length; i++) {
						File file0= subFiles[i];
						String name = file0.getName();
						CommonQueryBS.systemOut(name);
						if(name.substring(name.lastIndexOf(".") + 1).equalsIgnoreCase("zzb3")){
							info = info +(file0.getAbsolutePath().replace("\\", "/"))+",";
						}
					}
				}
			}
			info = info.substring(0,info.length()-1);
			CommonQueryBS.systemOut("1,"+info);
			out.println("5,"+info);
		} catch (Exception e) {
			out.println("6");
			e.printStackTrace();
		}
	}

	private void zzb3FileImp(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		PrintWriter out= null;
		try {
			out= response.getWriter();
			String id = UUID.randomUUID().toString().replace("-", "");
			String file =  new String (request.getParameter("file").getBytes("ISO-8859-1"),"UTF-8");
//			String fromfile = new String (request.getParameter("request").getBytes("ISO-8859-1"),"UTF-8");
			String deptid =  new String (request.getParameter("searchDeptid").getBytes("ISO-8859-1"),"UTF-8");
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit(id);
			CurrentUser user = new CurrentUser();
			user.setId("40288103556cc97701556d629135000f");
			UserVO userVo = new UserVO();
			userVo.setLoginname("system");
			ImpThread thr = new ImpThread(file, deptid, id, user, userVo,"");   //�����̵߳���
			new Thread(thr,"zzb3�߳�1").start();
			out.println("9,"+id);
		} catch (Exception e) {
			out.println("10");
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
