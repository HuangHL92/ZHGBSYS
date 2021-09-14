package com.insigma.siis.local.pagemodel.dataimpexcel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A06temp;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A11temp;
import com.insigma.siis.local.business.entity.A14temp;
import com.insigma.siis.local.business.entity.A15temp;
import com.insigma.siis.local.business.entity.A29temp;
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
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.VerifySqlList;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class DataImpExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 7538250764094664771L;

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		doPost(paramHttpServletRequest, paramHttpServletResponse);
	}

//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String method = request.getParameter("method");
//		if ("VerificationSchemeExcelExp".equals(method)) {// 单个文件下载
//			String fileName = request.getParameter("fileName");
//			String vsc001Exp = request.getParameter("vsc001Exp")==null?"":request.getParameter("vsc001Exp");
//			String passwd = request.getParameter("passwd");
//			try {
//				verificationSchemeExcelExp(response, vsc001Exp, fileName,
//						passwd);
//			} catch (AppException e) {
//				e.printStackTrace();
//			}
//		} else if ("VerificationSchemeExcelImp".equals(method)) {
//			try {
//				impFileProcess(request, response);
//			} catch (Exception e) {
//				e.printStackTrace();
//				PrintWriter out = response.getWriter();
//				out.println("导入异常！异常信息："+e.getMessage());
//			}
//		}
//	}

	/**
	 * 导出文件
	 * 
	 * @param response
	 * @param vsc001Exp
	 *            要导出的方案Id
	 * @param fileName
	 *            导出文件名
	 * @throws AppException
	 */
//	private void verificationSchemeExcelExp(HttpServletResponse response,
//			String vsc001Exp, String fileName, String passwd)
//			throws AppException {
//		
//		Map<String,List<List<Object>>> listContentMap = CustomExcelUtil.getExcelMap(vsc001Exp,fileName);
//		// 把List信息写到Excel中
//		CustomExcelUtil.exportExcel(response, listContentMap, passwd);
//		
//
//	}
	
	

	@SuppressWarnings("unchecked")
	public void impFileProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		HBSession sess = HBUtil.getHBSession();
		String problemIsn = request.getParameter("problemIsn");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 从servlet传过来的FORM中获取文件对象，并将文件写入到数据库中
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
				if (!item.isFormField()) {// 上传文件
					if (!StringUtil.isEmpty(fileName) && item.get().length > 0
							&& "excelFile".equals(item.getFieldName())) {
						processUploadedFile(item);
					} else {
						msg = "上传文件应该为Excel文件！";
					}
				}
			}

			if (!"".equals(msg)) {
				msg = "无上传成功文件！" + msg;
			} else {
				msg = "上传成功文件：" + fileName;
			}
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			PrintWriter printwriter = response.getWriter();
			// printwriter.println("{success:true,msg:'"+pr99.getPrname()+"'}");
			printwriter.println(msg);
			printwriter.flush();
			printwriter.close();
		} catch (FileUploadException e) {

			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("{success:true,msg:'" + e.getMessage() + "'}");
			printwriter.flush();
			printwriter.close();
			throw new Exception("上传文件出错", e);
		}
	}

	/**
	 * 表单的普通项处理
	 * 
	 * @param item
	 * @param pr99
	 * @author mengl
	 * @date 2015年4月21日
	 * 
	 */
	/*
	 * private void processFormField(FileItem item) {
	 * 
	 * String fieldName = item.getFieldName(); System.out.println("filedName->"
	 * + fieldName); // 获得表单项的输入值 String value = null; try { value =
	 * item.getString("UTF-8"); } catch (UnsupportedEncodingException e) { }
	 * System.out.println("value->" + value);
	 * 
	 * if ("prdesc".equals(fieldName)) { pr99.setPrdesc(value); }else
	 * if("daf001".equals(fieldName)){ pr99.setDaf001(Long.valueOf(value)); }
	 * 
	 * }
	 */

	/**
	 * 上传文件处理
	 * 
	 * @param item
	 * @throws NumberFormatException
	 * @throws AppException
	 * @throws IOException
	 * @author mengl
	 * @throws Exception
	 * @date 2015年4月21日
	 * 
	 */
	private void processUploadedFile(FileItem item) throws Exception {
		List<List> list = DataIMpExcelUtil.importExcel(item);
		String path = item.getName();// 文件名称
		String filename = path.substring(path.lastIndexOf("\\") + 1);
		this.saveFile2DB(list,filename);
	}

	/**
	 * 保存信息到数据库
	 * @param filename 
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int saveFile2DB(List<List> list, String filename) throws Exception {
		int result = 0;
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		try {
			sess.beginTransaction();
			List<Map<String, String>> headlist = list.get(15);
			CurrentUser user = SysUtil.getCacheCurrentUser();
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			List<B01> detps = null;
			// 根节点上级机构id
			String B0111 = headlist.get(0).get("b0111");
			// 根节点上级机构id
			String deptid = "";
			//根节点机构id
			String impdeptid = "";
			detps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0101='" + headlist.get(0).get("b0101")
							+ "' and t.b0114='" + headlist.get(0).get("b0114")
							+ "'").list();
			if (detps != null && detps.size() > 0) {
				impdeptid = detps.get(0).getB0111();
				deptid = detps.get(0).getB0121();
			} else {
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);
			List<A01temp> a01s = list.get(0);
			List<A02temp> a02s = list.get(1);
			List<A06temp> a06s = list.get(2);
			List<A08temp> a08s = list.get(3);
			List<A11temp> a11s = list.get(4);
			List<A14temp> a14s = list.get(5);
			List<A15temp> a15s = list.get(6);
			List<A29temp> a29s = list.get(7);
			List<A30temp> a30s = list.get(8);
			List<A31temp> a31s = list.get(9);
			List<A36temp> a36s = list.get(10);
			List<A37temp> a37s = list.get(11);
			List<A41temp> a41s = list.get(12);
			List<A53temp> a53s = list.get(13);
			List<B01temp> b01s = list.get(14);
			list.add(a01s);
			list.add(a02s);
			list.add(a06s);
			list.add(a08s);
			list.add(a11s);//4
			list.add(a14s);
			list.add(a15s);
			list.add(a29s);
			list.add(a30s);
			list.add(a31s);//9
			list.add(a36s);
			list.add(a37s);
			list.add(a41s);
			list.add(a53s);//13
			list.add(b01s);
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				if (gr != null) {
					imprecord.setImpgroupid(gr.getB0111());
					imprecord.setImpgroupname(gr.getB0101());
				}

				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype("XLS");
				imprecord.setImptype("6");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				sess.save(imprecord);
				long t_n = 0;
				long e_n = 0;
				String imprecordid = imprecord.getImprecordid();
				Map<String, String> errorMap = new HashMap<String, String>();
				if (a02s != null && a02s.size() > 0)
					for (A02temp temp : a02s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a02s.clear();
				if (a06s != null && a06s.size() > 0)
					for (A06temp temp : a06s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a06s.clear();
				if (a08s != null && a08s.size() > 0)
					for (A08temp temp : a08s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a08s.clear();
				if (a11s != null && a11s.size() > 0)
					for (A11temp temp : a11s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a11s.clear();
				if (a14s != null && a14s.size() > 0)
					for (A14temp temp : a14s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a14s.clear();
				if (a15s != null && a15s.size() > 0)
					for (A15temp temp : a15s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a15s.clear();
				if (a29s != null && a29s.size() > 0)
					for (A29temp temp : a29s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a29s.clear();
				if (a30s != null && a30s.size() > 0)
					for (A30temp temp : a30s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a30s.clear();
				if (a31s != null && a31s.size() > 0)
					for (A31temp temp : a31s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a31s.clear();
				System.gc();
				if (a36s != null && a36s.size() > 0)
					for (A36temp temp : a36s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a36s.clear();
				if (a37s != null && a37s.size() > 0)
					for (A37temp temp : a37s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a37s.clear();
				if (a41s != null && a41s.size() > 0)
					for (A41temp temp : a41s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a41s.clear();
				if (a53s != null && a53s.size() > 0)
					for (A53temp temp : a53s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a53s.clear();
				System.gc();
				
				if (b01s != null && b01s.size() > 0)
					for (B01temp temp : b01s) {

						temp.setImprecordid(imprecordid);
						if ((temp.getB0121() == null
								|| temp.getB0121().equals("") || temp
								.getB0111().equals(B0111))
								&& deptid != null) {
							temp.setB0121(deptid.toString());
						}
						B01tempb01 tempb = new B01tempb01();
						tempb.setImprecordid(imprecordid);
						tempb.setTempb0111(temp.getB0111());
						tempb.setNewb0111(impdeptid + temp.getB0111().substring(B0111.length()));
						sess.save(tempb);
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				b01s.clear();
				if (a01s != null && a01s.size() > 0)
					for (A01temp temp : a01s) {
						temp.setImprecordid(imprecordid);
						if (errorMap.containsKey(temp.getA0000())) {
							String error = errorMap.get(temp.getA0000());
							StringBuilder b = new StringBuilder(temp
									.getErrorinfo());
							b.append(error);
							errorMap.put(temp.getA0000(), b.toString());
							temp.setErrorinfo(error.toString());
							temp.setIsqualified("1");
						}
						
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				a01s.clear();
				imprecord.setTotalnumber(t_n + "");
				imprecord.setWrongnumber(e_n + "");
				sess.update(imprecord);
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

		return result;
	}

}