package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.VerifyProcess;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.VerifySqlList;
import com.insigma.siis.local.business.utils.CustomExcelUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.VerifyDataThread;

public class CustomExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 7538250764094664771L;

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		doPost(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if ("VerificationSchemeExcelExp".equals(method)) {// ���������ļ�����
			String fileName = request.getParameter("fileName");
			String vsc001Exp = request.getParameter("vsc001Exp")==null?"":request.getParameter("vsc001Exp");
			String passwd = request.getParameter("passwd");
			try {
				verificationSchemeExcelExp(response, vsc001Exp, fileName,
						passwd);
			} catch (AppException e) {
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("�����쳣���쳣��Ϣ��"+e.getMessage());
			}
			
			
		} else if ("VerificationSchemeExcelImp".equals(method)) {
			try {
				impFileProcess(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("�����쳣���쳣��Ϣ��"+e.getMessage());
			}
		} else if("verify".equals(method)){
			String vpid=request.getParameter("vpid");
			String b0111=request.getParameter("b0111");
			String bsType=request.getParameter("bsType");
			String ruleids=request.getParameter("ruleids");
			String peopleid=request.getParameter("peopleid") == null?"":request.getParameter("peopleid").replace("^", "'");
			String groupid=request.getParameter("groupid")==null?"":request.getParameter("groupid").replace("^", "'");
			String a0163 = request.getParameter("a0163")==null?"":request.getParameter("a0163");
			PrintWriter printwriter = response.getWriter();
			try {
				VerifyDataThread vdt =null;
				if("1".equals(bsType)){
					vdt = new VerifyDataThread(vpid,b0111, ruleids,bsType,PrivilegeManager.getInstance().getCueLoginUser(),null,groupid);
				}else if("2".equals(bsType)){
					vdt = new VerifyDataThread(vpid,b0111, ruleids,bsType,PrivilegeManager.getInstance().getCueLoginUser());
				}else if("3".equals(bsType)){
					vdt = new VerifyDataThread(vpid,b0111, ruleids,bsType,PrivilegeManager.getInstance().getCueLoginUser(),null,peopleid);
				}else if("4".equals(bsType)){
					vdt = new VerifyDataThread(vpid,b0111, ruleids,bsType,PrivilegeManager.getInstance().getCueLoginUser(),a0163,null);
				}else{
					vdt = new VerifyDataThread(vpid,b0111, ruleids,bsType,PrivilegeManager.getInstance().getCueLoginUser());
				}
				vdt.run();
//				Thread t1=new Thread(vdt,"����У�����");
//				t1.start();
//				t1.join();
			} catch (Exception e) {
				VerifyProcess vp=(VerifyProcess) HBUtil.getHBSession().get(VerifyProcess.class, vpid);
				vp.setResultFlag(-1L);
				e.printStackTrace();
				vp.setProcessMsg("У������з����쳣��" + e.getMessage());
			}
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param response
	 * @param vsc001Exp
	 *            Ҫ�����ķ���Id
	 * @param fileName
	 *            �����ļ���
	 * @throws AppException
	 */
	private void verificationSchemeExcelExp(HttpServletResponse response,
			String vsc001Exp, String fileName, String passwd)
			throws AppException {
		
		Map<String,List<List<Object>>> listContentMap = CustomExcelUtil.getExcelMap(vsc001Exp,fileName);
		// ��List��Ϣд��Excel�У���������
		CustomExcelUtil.exportExcel(response, listContentMap, passwd);
		
		
		//��¼������־
		HBSession sess = HBUtil.getHBSession();
		if(!StringUtil.isEmpty(vsc001Exp)){
			for(String vsc001 :vsc001Exp.split(",")){
				VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
				try {
					if(vs!=null){
						new LogUtil().createLog("630", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null, null);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	

	@SuppressWarnings("unchecked")
	public void impFileProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		boolean flag = true;  //�ϴ��ɹ����
		// ��servlet��������FORM�л�ȡ�ļ����󣬲����ļ�д�뵽���ݿ���
		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> itr0 = items.iterator();
			while (itr0.hasNext()) {
				FileItem item = itr0.next();
				CommonQueryBS.systemOut(item.getName());
				CommonQueryBS.systemOut(item.getFieldName());
				CommonQueryBS.systemOut(item.isFormField()==true?"true":"false");
			}

			Iterator<FileItem> itr = items.iterator();
			String msg = "";
			String fileName = "";
			if (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				fileName = item.getName();
				if (!item.isFormField()) {// �ϴ��ļ�
					if (!StringUtil.isEmpty(fileName) && item.get().length > 0
							&& "excelFile".equals(item.getFieldName())) {
						processUploadedFile(item);
					} else {
						msg = "�ϴ��ļ�Ӧ��ΪExcel�ļ���";
						flag = false;
					}
				}
			}

			if (flag) {
				msg = "�ϴ��ɹ��ļ���" + fileName;
			} else {
				msg = "���ϴ��ɹ��ļ���" + msg;
			}
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			PrintWriter printwriter = response.getWriter();
			// printwriter.println("{success:true,msg:'"+pr99.getPrname()+"'}");
			printwriter.println(msg);
			printwriter.flush();
			printwriter.close();
		} catch (FileUploadException e) {
			flag = false;
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("{success:true,msg:'" + e.getMessage() + "'}");
			printwriter.flush();
			printwriter.close();
			throw new Exception("�ϴ��ļ�����", e);
		}
		
	}

	/**
	 * ������ͨ���
	 * 
	 * @param item
	 * @param pr99
	 * @author mengl
	 * @date 2015��4��21��
	 * 
	 */
	/*
	 * private void processFormField(FileItem item) {
	 * 
	 * String fieldName = item.getFieldName(); CommonQueryBS.systemOut("filedName->"
	 * + fieldName); // ��ñ��������ֵ String value = null; try { value =
	 * item.getString("UTF-8"); } catch (UnsupportedEncodingException e) { }
	 * CommonQueryBS.systemOut("value->" + value);
	 * 
	 * if ("prdesc".equals(fieldName)) { pr99.setPrdesc(value); }else
	 * if("daf001".equals(fieldName)){ pr99.setDaf001(Long.valueOf(value)); }
	 * 
	 * }
	 */

	/**
	 * �ϴ��ļ�����
	 * 
	 * @param item
	 * @throws NumberFormatException
	 * @throws AppException
	 * @throws IOException
	 * @author mengl
	 * @throws Exception
	 * @date 2015��4��21��
	 * 
	 */
	private void processUploadedFile(FileItem item) throws Exception {
		InputStream is = item.getInputStream();
		List<List<Object>> list = CustomExcelUtil.importExcel(is);
		Collection<String> collection = saveFile2DB(list);
		
		//��¼�ϴ���־
		HBSession sess = HBUtil.getHBSession();
		if(collection!=null  ){
			for(String vsc001 :collection){
				VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
				try {
					if(vs!=null){
						new LogUtil().createLog("631", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null, null);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			sess.connection().commit();
		}
	}

	/**
	 * ������Ϣ�����ݿ�
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static Collection<String> saveFile2DB(List<List<Object>> list) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		Map<String, String> vsc001Map = new HashMap<String, String>();// ��vsc001 -- ��vsc001
		Map<String, String> vru001Map = new HashMap<String, String>();// ��vsu001 -- ��vsu001
		try {
			sess.beginTransaction();
			
			//1. ����VerifyScheme
			for (List<Object> listObj : list) {
				for (Object obj : listObj) {
					if (obj instanceof VerifyScheme) {
						VerifyScheme vs = (VerifyScheme) obj;
						String oldVsc001 = vs.getVsc001();
						vs.setVsc005(SysUtil.getCacheCurrentUser().getUserVO()
								.getId());
						vs.setVsc006(new Date());
						vs.setVsc008("1");//������
						vs.setVsc003("0");//����У�鷽����Ϊδ����
						vs.setVsc007("0");//Ĭ����Ϊ���޸�
						sess.save(vs);
						sess.flush();
						vsc001Map.put(oldVsc001, vs.getVsc001());
					}
				}
			}
			//2. ����VerifyRule
			for (List<Object> listObj : list) {
				for (Object obj : listObj) {
					if (obj instanceof VerifyRule ) {
						VerifyRule vr = (VerifyRule) obj;
						if(vsc001Map.containsKey(vr.getVsc001())){
							
							String oldVru001 = vr.getVru001();
							
							vr.setVsc001(vsc001Map.get(vr.getVsc001()));
							if("2".equals(vr.getVru007())){
								vr.setVru007("2");// zxw 2018-11-03 ������Ĺ���״̬Ϊ��2������ʾϵͳĬ�Ϲ��򣬲��ܽ����޸�
							}else{
								vr.setVru007("0");//����У�������Ϊδ����
							}
							sess.save(vr);
							sess.flush();
							vru001Map.put(oldVru001, vr.getVru001());
						}
					}
				}
			}
			//3. ����VerifySqlList
			for (List<Object> listObj : list) {
				for (Object obj : listObj) {
					if (obj instanceof VerifySqlList) {
						VerifySqlList vsl = (VerifySqlList) obj;
						if (vru001Map.containsKey(vsl.getVru001())) {
							vsl.setVru001(vru001Map.get(vsl.getVru001()));
							sess.save(vsl);
							sess.flush();
						}
					}
				}
			}
			sess.flush();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return vsc001Map.values();
	}

}