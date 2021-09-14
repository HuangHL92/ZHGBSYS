package com.insigma.siis.local.pagemodel.zj.tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.extra.TagDazsbfj;
import com.insigma.siis.local.business.entity.extra.TagKcclfj2;
import com.insigma.siis.local.business.entity.extra.TagNdkhdjbfj;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * ��ǩ����Servlet
 * 
 * @author zhub
 *
 */
public class TagFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private ServletConfig config;
	@SuppressWarnings("unused")
	private LogUtil applog = new LogUtil();

	public final static String disk = getPath();

	private static String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/";
		try {
			File file = new File(upload_file);
			// ����ļ��в������򴴽�
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// ��ѹ·��
		return upload_file;
	}

	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");

		if ("addKcclfjFile".equals(method)) { // ������ϸ�������
			addKcclfjFile(request, response);
		} else if ("deleteKcclfjFile".equals(method)) { // ������ϸ���ɾ��
			deleteKcclfjFile(request, response);
		} else if ("downloadKcclfjFile".equals(method)) { // ������ϸ�������
			downloadKcclfjFile(request, response);
		} else if ("addNdkhdjbfjFile".equals(method)) { // ��ȿ��˵ǼǸ�������
			addNdkhdjbfjFile(request, response);
		} else if ("deleteNdkhdjbfjFile".equals(method)) { // ��ȿ��˵ǼǸ���ɾ��
			deleteNdkhdjbfjFile(request, response);
		} else if ("downloadNdkhdjbfjFile".equals(method)) { // ��ȿ��˵ǼǸ�������
			downloadNdkhdjbfjFile(request, response);
		} else if ("addDazsbfjFile".equals(method)) { // ����ר�󸽼�����
			addDazsbfjFile(request, response);
		} else if ("deleteDazsbfjFile".equals(method)) { // ����ר�󸽼�ɾ��
			deleteDazsbfjFile(request, response);
		} else if ("downloadDazsbfjFile".equals(method)) { // ����ר�󸽼�����
			downloadDazsbfjFile(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * ����������ϸ���
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void addKcclfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String tagid = UUID.randomUUID().toString();
		String a0000 = request.getParameter("a0000");
		String note = URLDecoder.decode(URLDecoder.decode(request.getParameter("note"), "UTF-8"), "UTF-8");

		String attachPath = "kcclfj/" + a0000 + "/" + tagid;

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String filename = "";
			String houzhui = "";
			String filePath = "";
			CommonQueryBS.systemOut("START---------" + DateUtil.getTime());
			String upload_file = "TagFileUpload/" + attachPath + "/"; // �ϴ�·��
			try {
				File file = new File(disk + upload_file);
				if (!file.exists() && !file.isDirectory()) { // ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// ��ȡ����Ϣ
			List<FileItem> fileItems = uploader.parseRequest(request);
			CommonQueryBS.systemOut(fileItems.size() + "");
			Iterator<FileItem> iter = fileItems.iterator();
			// �������飬��Ÿ���·���ͱ�ע��Ϣ
			Map<String, String> text_map = new HashMap<String, String>();
			Map<String, String> filePath_map = new HashMap<String, String>();
			Map<String, String> filename_map = new HashMap<String, String>();
			Map<String, String> fileSize_map = new HashMap<String, String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"), "gbk");
					if (fieldName.startsWith("textid_") && !"".equals(fieldvalue)) {
						text_map.put(fieldName, fieldvalue);
					}

				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					// �ж��ϴ��ļ��ĳߴ磬����10M���ļ������ϴ�
					long MAX_SIZE = 10 * 1024 * 1024;

					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";

					if (fi.getSize() < 1048576) {
						fileSize = "0" + fileSize;
					}

					if (fi.getSize() > MAX_SIZE) {
						out.println("<script>window.parent.odin.alert('�ļ���" + path + "�ĳߴ糬��10M�������ϴ���');</script>");
						return;
					}
					try {
						// ��ȡ�ļ���������׺��
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if ("".equals(filename) || filename == null) {
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// ��ȡ�ļ�����������׺��
						filename = filename.substring(0, filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;

						fi.write(new File(disk + filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName() + rowlength, filePath);
						filename_map.put(fi.getFieldName() + rowlength + "_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName() + rowlength + "_fileSize", fileSize);

					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			// ��������Ϣ��������������
			for (int i = 1; i <= rowlength; i++) {
				String filepath = filePath_map.get("excelFile" + i);
				String fileName = filename_map.get("excelFile" + i + "_filename");
				String fileSize = fileSize_map.get("excelFile" + i + "_fileSize");
				saveTagKcclfjFolder(tagid, a0000, filepath, fileName, fileSize, note);
			}
			out.println("<script>window.parent.odin.alert('�ϴ��ɹ�');</script>");
		} catch (Exception e) {
			out.println("<script>window.parent.odin.alert('�ϴ�ʧ�ܣ�');</script>");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * ���ϴ��Ŀ��������Ϣ���������ݿ���
	 * 
	 * @param fileurl
	 *            �ļ���ַ
	 * @param filename
	 *            �ļ�����
	 * @return
	 */
	public int saveTagKcclfjFolder(String tagid, String a0000, String fileurl, String filename, String filesize,
			String note) {

		// �������ݿ����
		HBSession sess = HBUtil.getHBSession();

		// ������������
		TagKcclfj2 tagKcclfj = new TagKcclfj2();
		// ��ȡ����
		tagKcclfj.setTagid(tagid);
		tagKcclfj.setA0000(a0000); // ��Աid
		tagKcclfj.setFilename(filename); // �ļ�����
		tagKcclfj.setFilesize(filesize); // �ļ���С
		tagKcclfj.setFileurl(fileurl); // �ļ��洢���·��
		tagKcclfj.setNote(note); // ��ע
		tagKcclfj.setUpdatedate(System.currentTimeMillis()); // ����ʱ��

		// ִ�����ݿ����
		sess.save(tagKcclfj);
		sess.flush();

		return EventRtnType.NORMAL_SUCCESS;
	}

	// ɾ��������ϸ���
	@SuppressWarnings("unlikely-arg-type")
	public void deleteKcclfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		HBSession session = HBUtil.getHBSession();
		String tagid = request.getParameter("tagid"); // ���Ҫɾ�����ļ�tagid
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");

		// ɾ�������е���ȿ��˵ǼǱ�������
		if (tagid != null && !"".equals(tagid)) {
			TagKcclfj2 tagKcclfj = (TagKcclfj2) session.get(TagKcclfj2.class, tagid);
			if (tagKcclfj != null && !"".equals(tagKcclfj)) {
				session.delete(tagKcclfj);
				session.flush();
			}
		}
		// ɾ���ļ��������ļ���
		this.delFileOrFolder(fileurl, tagid);
	}

	// ���ؿ�����ϸ���
	public void downloadKcclfjFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ��ô������ļ����·��
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");
		// �ļ�����
		String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1);
		fileurl = disk + fileurl; // ƴ�ӳ�����·��

		/* ��ȡ�ļ� */
		File file = new File(fileurl);
		/* ����ļ����� */
		if (file.exists()) {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));

			int fileLength = (int) file.length();
			response.setContentLength(fileLength);
			/* ����ļ����ȴ���0 */
			if (fileLength != 0) {
				/* ���������� */
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];
				/* ��������� */
				ServletOutputStream servletOS = response.getOutputStream();
				int readLength;
				while (((readLength = inStream.read(buf)) != -1)) {
					servletOS.write(buf, 0, readLength);
				}
				inStream.close();
				servletOS.flush();
				servletOS.close();
			}
		}
		file = null;
	}

	/**
	 * ������ȿ��˵ǼǱ���
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void addNdkhdjbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String tagid = UUID.randomUUID().toString();
		String a0000 = request.getParameter("a0000");
		String note = URLDecoder.decode(URLDecoder.decode(request.getParameter("note"), "UTF-8"), "UTF-8");

		String attachPath = "ndkhdjbfj/" + a0000 + "/" + tagid;

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String filename = "";
			String houzhui = "";
			String filePath = "";
			CommonQueryBS.systemOut("START---------" + DateUtil.getTime());
			String upload_file = "TagFileUpload/" + attachPath + "/";// �ϴ�·��
			try {
				File file = new File(disk + upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// ��ȡ����Ϣ
			List<FileItem> fileItems = uploader.parseRequest(request);
			CommonQueryBS.systemOut(fileItems.size() + "");
			Iterator<FileItem> iter = fileItems.iterator();
			// �������飬��Ÿ���·���ͱ�ע��Ϣ
			Map<String, String> text_map = new HashMap<String, String>();
			Map<String, String> filePath_map = new HashMap<String, String>();
			Map<String, String> filename_map = new HashMap<String, String>();
			Map<String, String> fileSize_map = new HashMap<String, String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"), "gbk");
					if (fieldName.startsWith("textid_") && !"".equals(fieldvalue)) {
						text_map.put(fieldName, fieldvalue);
					}

				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					// �ж��ϴ��ļ��ĳߴ磬����10M���ļ������ϴ�
					// CommonQueryBS.systemOut(fi.getSize());
					long MAX_SIZE = 10 * 1024 * 1024;

					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";

					if (fi.getSize() < 1048576) {
						fileSize = "0" + fileSize;
					}

					if (fi.getSize() > MAX_SIZE) {
						out.println("<script>window.parent.odin.alert('�ļ���" + path + "�ĳߴ糬��10M�������ϴ���');</script>");
						return;
					}
					try {
						// ��ȡ�ļ���������׺��
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if ("".equals(filename) || filename == null) {
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// ��ȡ�ļ�����������׺��
						filename = filename.substring(0, filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;

						fi.write(new File(disk + filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName() + rowlength, filePath);
						filename_map.put(fi.getFieldName() + rowlength + "_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName() + rowlength + "_fileSize", fileSize);

					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			// ��������Ϣ��������������
			for (int i = 1; i <= rowlength; i++) {
				String filepath = filePath_map.get("excelFile" + i);
				String fileName = filename_map.get("excelFile" + i + "_filename");
				String fileSize = fileSize_map.get("excelFile" + i + "_fileSize");
				saveTagNdkhdjbfjFolder(tagid, a0000, filepath, fileName, fileSize, note);
			}
			out.println("<script>window.parent.odin.alert('�ϴ��ɹ�');</script>");
		} catch (Exception e) {
			out.println("<script>window.parent.odin.alert('�ϴ�ʧ�ܣ�');</script>");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * ���ϴ�����ȿ��˵ǼǸ�����Ϣ���������ݿ���
	 * 
	 * @param fileurl
	 *            �ļ���ַ
	 * @param filename
	 *            �ļ�����
	 * @return
	 */
	public int saveTagNdkhdjbfjFolder(String tagid, String a0000, String fileurl, String filename, String filesize,
			String note) {

		// �������ݿ����
		HBSession sess = HBUtil.getHBSession();

		// ������������
		TagNdkhdjbfj tagNdkhdjbfj = new TagNdkhdjbfj();
		// ��ȡ����
		tagNdkhdjbfj.setTagid(tagid);
		tagNdkhdjbfj.setA0000(a0000); // ��Աid
		tagNdkhdjbfj.setFilename(filename); // �ļ�����
		tagNdkhdjbfj.setFilesize(filesize); // �ļ���С
		tagNdkhdjbfj.setFileurl(fileurl); // �ļ��洢���·��
		tagNdkhdjbfj.setNote(note); // ��ע
		tagNdkhdjbfj.setUpdatedate(System.currentTimeMillis()); // ����ʱ��

		// ִ�����ݿ����
		sess.save(tagNdkhdjbfj);
		sess.flush();

		return EventRtnType.NORMAL_SUCCESS;
	}

	// ɾ����ȿ��˵ǼǸ���
	@SuppressWarnings("unlikely-arg-type")
	public void deleteNdkhdjbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		HBSession session = HBUtil.getHBSession();
		String tagid = request.getParameter("tagid"); // ���Ҫɾ�����ļ�tagid
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");

		// ɾ�������е���ȿ��˵ǼǱ�������
		if (tagid != null && !"".equals(tagid)) {
			TagNdkhdjbfj tagNdkhdjbfj = (TagNdkhdjbfj) session.get(TagNdkhdjbfj.class, tagid);
			if (tagNdkhdjbfj != null && !"".equals(tagNdkhdjbfj)) {
				session.delete(tagNdkhdjbfj);
				session.flush();
			}
		}
		// ɾ���ļ��������ļ���
		this.delFileOrFolder(fileurl, tagid);
	}

	// ������ȿ��˵ǼǸ���
	public void downloadNdkhdjbfjFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// ��ô������ļ����·��
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");
		// �ļ�����
		String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1);

		fileurl = disk + fileurl; // ƴ�ӳ�����·��

		/* ��ȡ�ļ� */
		File file = new File(fileurl);
		/* ����ļ����� */
		if (file.exists()) {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));

			int fileLength = (int) file.length();
			response.setContentLength(fileLength);
			/* ����ļ����ȴ���0 */
			if (fileLength != 0) {
				/* ���������� */
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];
				/* ��������� */
				ServletOutputStream servletOS = response.getOutputStream();
				int readLength;
				while (((readLength = inStream.read(buf)) != -1)) {
					servletOS.write(buf, 0, readLength);
				}
				inStream.close();
				servletOS.flush();
				servletOS.close();
			}
		}
		file = null;
	}

	/**
	 * ��������ר�󸽼�
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public void addDazsbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String tagid = UUID.randomUUID().toString();
		String a0000 = request.getParameter("a0000");
		String note = URLDecoder.decode(URLDecoder.decode(request.getParameter("note"), "UTF-8"), "UTF-8");

		String attachPath = "dazsbfj/" + a0000 + "/" + tagid;

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			CommonQueryBS.systemOut("��ʼ�ϴ�");
			out = response.getWriter();
			String filename = "";
			String houzhui = "";
			String filePath = "";
			CommonQueryBS.systemOut("START---------" + DateUtil.getTime());
			String upload_file = "TagFileUpload/" + attachPath + "/";// �ϴ�·��
			try {
				File file = new File(disk + upload_file);
				if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
					file.mkdirs();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// ��ȡ����Ϣ
			List<FileItem> fileItems = uploader.parseRequest(request);
			CommonQueryBS.systemOut(fileItems.size() + "");
			Iterator<FileItem> iter = fileItems.iterator();
			// �������飬��Ÿ���·���ͱ�ע��Ϣ
			Map<String, String> text_map = new HashMap<String, String>();
			Map<String, String> filePath_map = new HashMap<String, String>();
			Map<String, String> filename_map = new HashMap<String, String>();
			Map<String, String> fileSize_map = new HashMap<String, String>();
			int rowlength = 0;
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fieldvalue = new String(fi.getString().getBytes("iso-8859-1"), "gbk");
					if (fieldName.startsWith("textid_") && !"".equals(fieldvalue)) {
						text_map.put(fieldName, fieldvalue);
					}

				} else {
					// ���ļ����浽ָ��Ŀ¼
					String path = fi.getName();// �ļ�����
					// �ж��ϴ��ļ��ĳߴ磬����10M���ļ������ϴ�
					long MAX_SIZE = 10 * 1024 * 1024;

					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";

					if (fi.getSize() < 1048576) {
						fileSize = "0" + fileSize;
					}

					if (fi.getSize() > MAX_SIZE) {
						out.println("<script>window.parent.odin.alert('�ļ���" + path + "�ĳߴ糬��10M�������ϴ���');</script>");
						return;
					}
					try {
						// ��ȡ�ļ���������׺��
						filename = path.substring(path.lastIndexOf("\\") + 1);
						if ("".equals(filename) || filename == null) {
							continue;
						}
						++rowlength;
						houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
						// ��ȡ�ļ�����������׺��
						filename = filename.substring(0, filename.lastIndexOf("."));

						filePath = upload_file + filename + "." + houzhui;

						fi.write(new File(disk + filePath));
						fi.getOutputStream().close();
						filePath_map.put(fi.getFieldName() + rowlength, filePath);
						filename_map.put(fi.getFieldName() + rowlength + "_filename", filename + "." + houzhui);
						fileSize_map.put(fi.getFieldName() + rowlength + "_fileSize", fileSize);

					} catch (Exception e) {
						try {
							throw new AppException("�ϴ�ʧ��");
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			// ��������Ϣ��������������
			for (int i = 1; i <= rowlength; i++) {
				String filepath = filePath_map.get("excelFile" + i);
				String fileName = filename_map.get("excelFile" + i + "_filename");
				String fileSize = fileSize_map.get("excelFile" + i + "_fileSize");
				saveTagDazsbfjFolder(tagid, a0000, filepath, fileName, fileSize, note);
			}
			out.println("<script>window.parent.odin.alert('�ϴ��ɹ�');</script>");
		} catch (Exception e) {
			out.println("<script>window.parent.odin.alert('�ϴ�ʧ�ܣ�');</script>");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * ���ϴ��ĵ���ר�󸽼���Ϣ���������ݿ���
	 * 
	 * @param fileurl
	 *            �ļ���ַ
	 * @param filename
	 *            �ļ�����
	 * @return
	 */
	public int saveTagDazsbfjFolder(String tagid, String a0000, String fileurl, String filename, String filesize,
			String note) {

		// �������ݿ����
		HBSession sess = HBUtil.getHBSession();

		// ������������
		TagDazsbfj tagDazsbfj = new TagDazsbfj();
		// ��ȡ����
		tagDazsbfj.setTagid(tagid);
		tagDazsbfj.setA0000(a0000); // ��Աid
		tagDazsbfj.setFilename(filename); // �ļ�����
		tagDazsbfj.setFilesize(filesize); // �ļ���С
		tagDazsbfj.setFileurl(fileurl); // �ļ��洢���·��
		tagDazsbfj.setNote(note); // ��ע
		tagDazsbfj.setUpdatedate(System.currentTimeMillis()); // ����ʱ��

		// ִ�����ݿ����
		sess.save(tagDazsbfj);
		sess.flush();

		return EventRtnType.NORMAL_SUCCESS;
	}

	// ɾ������ר�󸽼�
	@SuppressWarnings("unlikely-arg-type")
	public void deleteDazsbfjFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		HBSession session = HBUtil.getHBSession();
		String tagid = request.getParameter("tagid"); // ���Ҫɾ�����ļ�tagid
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");

		// ɾ�������е���ȿ��˵ǼǱ�������
		if (tagid != null && !"".equals(tagid)) {
			TagDazsbfj tagDazsbfj = (TagDazsbfj) session.get(TagDazsbfj.class, tagid);
			if (tagDazsbfj != null && !"".equals(tagDazsbfj)) {
				session.delete(tagDazsbfj);
				session.flush();
			}
		}
		// ɾ���ļ��������ļ���
		this.delFileOrFolder(fileurl, tagid);
	}

	// ���ص���ר�󸽼�
	public void downloadDazsbfjFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// ��ô������ļ����·��
		String fileurl = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileurl"), "UTF-8"), "UTF-8");
		// �ļ�����
		String filename = fileurl.substring(fileurl.lastIndexOf("/") + 1);

		fileurl = disk + fileurl; // ƴ�ӳ�����·��

		/* ��ȡ�ļ� */
		File file = new File(fileurl);
		/* ����ļ����� */
		if (file.exists()) {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));

			int fileLength = (int) file.length();
			response.setContentLength(fileLength);
			/* ����ļ����ȴ���0 */
			if (fileLength != 0) {
				/* ���������� */
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];
				/* ��������� */
				ServletOutputStream servletOS = response.getOutputStream();
				int readLength;
				while (((readLength = inStream.read(buf)) != -1)) {
					servletOS.write(buf, 0, readLength);
				}
				inStream.close();
				servletOS.flush();
				servletOS.close();
			}
		}
		file = null;
	}

	/*
	 * ��ͨ�÷�����ɾ���ļ��������ļ��У�param filePath �ļ����·���� param folderName �ļ������ļ�������
	 */
	public void delFileOrFolder(String filePath, String folderName) {
		try {
			// ƴ�ӳ��ļ�����·��
			filePath = disk + filePath;
			if (-1 == filePath.indexOf(folderName)) {
				this.delFile(filePath);
			} else {
				// ƴ�ӳ��ļ��о���·��
				String folderPath = filePath.substring(0, filePath.indexOf(folderName) + folderName.length());
				// ɾ���ļ��������ļ���
				this.delFolder(folderPath);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ��ͨ�÷�����ɾ���ļ���param filePath �ļ���������·��
	 */
	public boolean delFile(String filePath) throws AppException {
		File file = new File(filePath);
		boolean ret = true;
		if (file.exists()) {
			ret = file.delete();
		}
		return ret;
	}

	/*
	 * ��ͨ�÷�����ɾ���ļ��У�param folderPath �ļ�����������·��
	 */
	public void delFolder(String folderPath) {
		delAllFile(folderPath); // ɾ����������������
		String filePath = folderPath;
		filePath = filePath.toString();
		File file = new File(filePath);
		file.delete(); // ɾ�����ļ���
	}

	/*
	 * ��ͨ�÷�����ɾ��ָ���ļ����������ļ���param path �ļ�����������·��
	 */
	public boolean delAllFile(String path) {
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
				delAllFile(path + "/" + tempList[i]); // ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]); // ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}

}
