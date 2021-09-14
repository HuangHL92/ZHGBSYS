package com.insigma.siis.local.pagemodel.repandrec.plat;

import iaik.security.random.RandomException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeDownload;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.business.utils.CustomExcelUtil;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf.CustomExcelServlet;

public class QueryRecievePageModel extends PageModel {
	
	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	/**
	 * 初始化本机接收目录
	 * @return
	 */
	private static List<String> initDirPaths(String ftpHomeDir){
		List<String> dirPaths = new ArrayList<String>();
		dirPaths.add(ftpHomeDir+ZwhzFtpPath.HZB_UP);
		dirPaths.add(ftpHomeDir+ZwhzFtpPath.FK_UP);
		dirPaths.add(ftpHomeDir+ZwhzFtpPath.OTHER_UP);
		dirPaths.add(ftpHomeDir+ZwhzFtpPath.DM_UP);
		dirPaths.add(ftpHomeDir+ZwhzFtpPath.JC_UP);
		dirPaths.add(ftpHomeDir+ZwhzFtpPath.ZB_UP);
		
		dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.HZB_DOWN);
		dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.FK_DOWN);
		dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN);
		dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.DM_DOWN);
		dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.JC_DOWN);
		dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.ZB_DOWN);
		return dirPaths;
	}
	@Override
	public int doInit() throws RadowException {
		try {
			String filename ="";
			try {
				filename = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("initParams"),"UTF-8"),"UTF-8");
			} catch (Exception e) {
				filename = this.getRadow_parent_data();
			}
			if(filename == null){
				this.setMainMessage("系统异常，请联系管理员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String[] fnames = null;
			fnames = filename.split("_");
			if(fnames.length >= 5 && fnames[1].equals("按机构导出文件")){
				String imprecordid = "";
				//导入
//				imprecordid = dodataIMp(filename);
				try{
//					boolean flag = DataVerifyBS.dataVerifyByBSType(imprecordid, null,"2", null,PrivilegeManager.getInstance().getCueLoginUser(),"0");
				}catch (Exception e) {
					e.printStackTrace();
					throw new AppException(e.getMessage());
				}
				this.getExecuteSG().addExecuteCode("window.location.href='" +request.getContextPath() +  
						"/radowAction.do?method=doEvent&pageModel=pages.repandrec.plat.ImpDetailTwo&initParams="+imprecordid+"'");
				//this.backupFile(filename);
				return EventRtnType.NORMAL_SUCCESS;
			}else if("信息校验方案".equals(fnames[1])){
				verifySchemeImp(filename);
			}else if("扩展代码集发布".equals(fnames[1])){
				codeValueRecieveCue(filename);
			}else if("补充信息项发布".equals(fnames[1])){
				addValueRecieveCue(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RandomException(e.getMessage());
		} 
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void backupFile(String filename) {
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
			for (Ftpuser ftpuser : ftpus) {
				String zhbupfile = ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +filename; 
				String bakeupPath = "D:/FileBackup/"; 
				String bakeupPfile = bakeupPath + filename; 
				File pfile = new File(zhbupfile);
				if (pfile.exists() && pfile.isFile()) {
					String packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); //读取总包文件内容
					if(packXmlStr!=null){
						ZwhzPackDefine zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
						List<SFileDefine> files = zpdefine.getSfile();
						if(files!=null && files.size()>0){
							for (SFileDefine fileDefine : files) {
								File datafile = new File(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName());
								if(!datafile.exists() || datafile.isDirectory()){
//									this.setMainMessage("上传数据文件不全！");
								} else {
									String datafilename = bakeupPath + fileDefine.getName();
									this.forChannel(datafile, new File(datafilename));
								}
							}
						}
					}
					this.forChannel(pfile, new File(bakeupPfile));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
//	private String dodataIMp(String filename) {
//		UploadHzbFileBS uploadbs = new UploadHzbFileBS();
//		String packXmlStr = null;//总包文件内容字符串
//		ZwhzPackDefine zpdefine = null;//总包文件解析对象
//	    String rootpath = getrPath();
//	    HBSession sess = HBUtil.getHBSession();
//	    String imprecordid = "";
//	    List<String> delps = new ArrayList<String>();
//	    int t_n = 0;
//		try {
////			sess.beginTransaction();
//			List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
//			for (Ftpuser ftpuser : ftpus) {
//				String zhbupfile = ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +filename; 
//				File pfile = new File(zhbupfile);
//				if (pfile.exists() && pfile.isFile()) {
//					packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); //读取总包文件内容
//					if(packXmlStr!=null){
//						zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
//						List<SFileDefine> files = zpdefine.getSfile();
//						Imprecord imprecord = null;
//						if(files!=null && files.size()>0){
//							for (SFileDefine fileDefine : files) {
//								File datafile = new File(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName());
//								if(!datafile.exists() || datafile.isDirectory()){
//									this.setMainMessage("上传数据文件不全！");
//								}
//							}
//							String uuid = UUID.randomUUID().toString().replace("-", "");
//							for (SFileDefine fileDefine : files) {
//								String unzip = rootpath + "unzip/" + uuid + "/";
//								File file = new File(unzip);
//								if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
//									file.mkdirs();
//								}
//								delps.add(unzip);
////								SevenZipUtil.extractile(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName(), unzip, "1234");
//								NewSevenZipUtil.extractilenew(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName(), unzip, "1");
//								//获取导入imprecord信息。
//								List<Map<String, String>> headlist = Dom4jUtil
//										.gwyinfoF(rootpath + "unzip/" + uuid + "/gwyinfo.xml");
//								String from_file = unzip + "Photos/";
//								CurrentUser user = SysUtil.getCacheCurrentUser();
//								List<B01> grps = HBUtil.getHBSession().createQuery(
//										"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
//												+ user.getId() + "')").list();
//								B01 gr = null;
//								if (grps != null && grps.size() > 0) {
//									gr = grps.get(0);
//								}
//								List<B01> detps = null;
//								String B0111 = headlist.get(0).get("B0111");// 根节点上级机构id
//								String deptid = "";// 根节点上级机构id
//								String impdeptid = "";//根节点机构id
//								detps = HBUtil.getHBSession().createQuery(
//										"from B01 t where t.b0101='" + headlist.get(0).get("B0101")
//												+ "' and t.b0114='" + headlist.get(0).get("B0114")
//												+ "'").list();
//								if (detps != null && detps.size() > 0) {
//									impdeptid = detps.get(0).getB0111();
//									deptid = detps.get(0).getB0121();
//								} else {
//									this.setMainMessage("未匹配到导入机构。");
//									throw new Exception("未匹配到导入机构。");
//								}
//								List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);
//								HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ uuid +"')").executeUpdate();
//								if (imprecords != null && imprecords.size() > 0) {
//									if(imprecord == null){
//										imprecord = imprecords.get(0);
//										imprecord.setImptime(DateUtil.getTimestamp());
//										imprecord.setImpuserid(user.getId());
//										if (gr != null) {
//											imprecord.setImpgroupid(gr.getB0111());
//											imprecord.setImpgroupname(gr.getB0101());
//										}
//										imprecord.setIsvirety("1");
//										imprecord.setFilename(filename);
//										imprecord.setFiletype("hzb");
//										imprecord.setImptype("1");
//										imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
//										imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
//										imprecord.setImpdeptid(impdeptid);
//										imprecord.setImpstutas("1");
//										imprecord.setTotalnumber(0 + "");
//										imprecord.setWrongnumber(0 + "");
//										imprecord.setImprecordid(uuid);
//										sess.update(imprecord);
//									}
//									
//									imprecordid = imprecord.getImprecordid();
//									//分别读取xml，倒入数据
//									String tables1[] = { "A02", "A06", "A08", "A11", "A14",
//											"A15", "A29", "A30", "A31", "A36", "A37", "A41", "A53",
//											"A57", "B01", "A01" };
//									for (int i = 0; i < tables1.length; i++) {
//										t_n = uploadbs.saveData("hzb", tables1[i], imprecordid, t_n, uuid,
//												from_file, B0111, deptid, impdeptid);
//										
//									}
//									
//									imprecord.setTotalnumber(t_n + Long.parseLong(imprecord.getTotalnumber()) +"");
//									sess.update(imprecord);
//								}
//								UploadHelpFileServlet.delFolder(unzip+"Table");
//								UploadHelpFileServlet.delFolder(unzip+"gwyinfo.xml");
//								new LogUtil().createLog("441", "IMP_RECORD", "", "", "数据接收", new ArrayList());
//							}
//						} else {
//							this.setMainMessage("文件异常或已损坏！");
//						}
//						
//					}
//				} 
//			}
////			if(delps!=null && delps.size()>0){
////				for (int i = 0; i < delps.size(); i++) {
////					UploadHelpFileServlet.delFolder(delps.get(i));
////				}
////			}
//			
////			sess.getTransaction().commit();
//		} catch (Exception e) {
//			if(delps!=null && delps.size()>0){
//				for (int i = 0; i < delps.size(); i++) {
//					UploadHelpFileServlet.delFolder(delps.get(i));
//				}
//			}
//			e.printStackTrace();
//			uploadbs.rollbackImp(imprecordid);
//			if(sess != null)
//				try {
//					sess.getTransaction().rollback();
//				} catch (Exception e2) {
//					e.printStackTrace();
//				}
//				
//		}
//		return imprecordid;
//	}
	/*private String dodataIMp(String filename) {
		String packXmlStr = null;//总包文件内容字符串
		ZwhzPackDefine zpdefine = null;//总包文件解析对象
	    String rootpath = getrPath();
	    HBSession sess = HBUtil.getHBSession();
	    String imprecordid = "";
		try {
			sess.beginTransaction();
			List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
			for (Ftpuser ftpuser : ftpus) {
				String zhbupfile = ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +filename; 
				File pfile = new File(zhbupfile);
				if (pfile.exists() && pfile.isFile()) {
					packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); //读取总包文件内容
					if(packXmlStr!=null){
						zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
						List<SFileDefine> files = zpdefine.getSfile();
						Imprecord imprecord = null;
						if(files!=null && files.size()>0){
							for (SFileDefine fileDefine : files) {
								File datafile = new File(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName());
								if(!datafile.exists() || datafile.isDirectory()){
									this.setMainMessage("上传数据文件不全！");
								}
							}
							for (SFileDefine fileDefine : files) {
								String uuid = UUID.randomUUID().toString().replace("-", "");
								String unzip = rootpath + "unzip/" + uuid + "/";
								File file = new File(unzip);
								// 如果文件夹不存在则创建
								if (!file.exists() && !file.isDirectory()) {
									file.mkdirs();
								}
								SevenZipUtil.extractile(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName(), unzip, "1234");
								List<List<Map<String, String>>> dataAll = new ArrayList<List<Map<String, String>>>();
								List<Map<String, String>> headlist = Dom4jUtil
										.gwyinfo("../../upload/unzip/" + uuid + "/gwyinfo.xml");
								dataAll.add(headlist);
								String tables1[] = { "A01", "A02", "A06", "A08", "A11", "A14",
										"A15", "A29", "A30", "A31", "A36", "A37", "A41", "A53",
										"A57", "B01" };
								for (int i = 0; i < tables1.length; i++) {
									List<Map<String, String>> data = Dom4jUtil
											.gwyA_B("../../upload/unzip/" + uuid + "/Table/"
													+ tables1[i] + ".xml");
									dataAll.add(data);
								}
								String from_file = unzip + "Photos/";
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
								String B0111 = headlist.get(0).get("B0111");
								// 根节点上级机构id
								String deptid = "";
								//根节点机构id
								String impdeptid = "";
								detps = HBUtil.getHBSession().createQuery(
										"from B01 t where t.b0101='" + headlist.get(0).get("B0101")
												+ "' and t.b0114='" + headlist.get(0).get("B0114")
												+ "'").list();
								if (detps != null && detps.size() > 0) {
									impdeptid = detps.get(0).getB0111();
									deptid = detps.get(0).getB0121();
								} else {
									this.setMainMessage("未匹配到导入机构。");
									throw new Exception("未匹配到导入机构。");
								}
								List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", dataAll
										.get(0));
								List<A01temp> a01s = Map2Temp.toTemp("A01", dataAll.get(1));
								List<A02temp> a02s = Map2Temp.toTemp("A02", dataAll.get(2));
								List<A06temp> a06s = Map2Temp.toTemp("A06", dataAll.get(3));
								List<A08temp> a08s = Map2Temp.toTemp("A08", dataAll.get(4));
								List<A11temp> a11s = Map2Temp.toTemp("A11", dataAll.get(5));
								List<A14temp> a14s = Map2Temp.toTemp("A14", dataAll.get(6));
								List<A15temp> a15s = Map2Temp.toTemp("A15", dataAll.get(7));
								List<A29temp> a29s = Map2Temp.toTemp("A29", dataAll.get(8));
								List<A30temp> a30s = Map2Temp.toTemp("A30", dataAll.get(9));
								List<A31temp> a31s = Map2Temp.toTemp("A31", dataAll.get(10));
								List<A36temp> a36s = Map2Temp.toTemp("A36", dataAll.get(11));
								List<A37temp> a37s = Map2Temp.toTemp("A37", dataAll.get(12));
								List<A41temp> a41s = Map2Temp.toTemp("A41", dataAll.get(13));
								List<A53temp> a53s = Map2Temp.toTemp("A53", dataAll.get(14));
								List<A57temp> a57s = Map2Temp.toTemp("A57", dataAll.get(15));
								List<B01temp> b01s = Map2Temp.toTemp("B01", dataAll.get(16));
								if (imprecords != null && imprecords.size() > 0) {
									if(imprecord == null){
										imprecord = imprecords.get(0);
										imprecord.setImptime(DateUtil.getSysDate());
										imprecord.setImpuserid(user.getId());
										if (gr != null) {
											imprecord.setImpgroupid(gr.getB0111());
											imprecord.setImpgroupname(gr.getB0101());
										}
										imprecord.setIsvirety("1");
										imprecord.setFilename(filename);
										imprecord.setFiletype("hzb");
										imprecord.setImptype("1");
										imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
										imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
										imprecord.setImpdeptid(impdeptid);
										imprecord.setImpstutas("1");
										imprecord.setTotalnumber(0 + "");
										imprecord.setWrongnumber(0 + "");
										sess.save(imprecord);
									}
									long t_n = 0;
									long e_n = 0;
									imprecordid = imprecord.getImprecordid();
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
									if (a57s != null && a57s.size() > 0)
										for (A57temp temp : a57s) {
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
											if(temp.getA5714()!=null && !temp.getA5714().equals("")){
												String photoname = temp.getA0000() + "."+ (temp.getPhotoname().split("\\.")[1]);
												File photo = new File(from_file + "/" + photoname);
												if(photo.exists() && photo.isFile()){
													FileInputStream in = new FileInputStream(photo);
													temp.setPhotodata(Hibernate.createBlob(in));
													temp.setPhotoname(photo.getName());
													temp.setPhotstype(photo.getName().split("\\.")[1]);
//													in.close();
												}
											}
											sess.save(temp);
											t_n++;
											if (temp.getIsqualified() != null
													&& temp.getIsqualified().equals("1")) {
												e_n++;
											}
										}
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
									imprecord.setTotalnumber(t_n + Long.parseLong(imprecord.getTotalnumber()) +"");
									imprecord.setWrongnumber(e_n + Long.parseLong(imprecord.getWrongnumber()) +"");
									sess.update(imprecord);
								}
								
							}
						} else {
							this.setMainMessage("文件异常或已损坏！");
						}
						
					}
				} else {
					this.setMainMessage("文件异常或已损坏！");
				}
			}
			sess.getTransaction().commit();
		} catch (Exception e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		}
		return imprecordid;
	}*/
	private String getrPath() {
		String rootPath = "";
		String filename = "";
		String houzhui = "";
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		// 上传路径
		String upload_file = rootPath + "upload/";
		return upload_file;
	}
	 public static long forChannel(File f1,File f2) throws Exception{
	        int length=2097152;
	        FileInputStream in=new FileInputStream(f1);
	        FileOutputStream out=new FileOutputStream(f2);
	        FileChannel inC=in.getChannel();
	        FileChannel outC=out.getChannel();
	        java.nio.ByteBuffer b=null;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
	                return 0;
	            }
	            if((inC.size()-inC.position())<length){
	                length=(int)(inC.size()-inC.position());
	            }else
	                length=2097152;
	            b=java.nio.ByteBuffer.allocateDirect(length);
	            inC.read(b);
	            b.flip();
	            outC.write(b);
	            outC.force(false);
	        }
	    }
	 
	 
	 /**
	  * 导入校验方案
	  * @param filename
	  * @throws AppException
	  */
	 private void verifySchemeImp(String filename) throws AppException {
			String packXmlStr = null;//总包文件内容字符串
			ZwhzPackDefine zpdefine = null;//总包文件解析对象
		    HBSession sess = HBUtil.getHBSession();
			try {
				List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
				for (Ftpuser ftpuser : ftpus) {
					String zhbupfile = AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.JC_DOWN +"/" +filename; 
					File pfile = new File(zhbupfile);
					if (pfile.exists() && pfile.isFile()) {
						packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); //读取总包文件内容
						if(packXmlStr!=null){
							zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
							List<SFileDefine> files = zpdefine.getSfile();
							if(files!=null && files.size()>0){
								for (SFileDefine fileDefine : files) {
									File datafile = new File(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.JC_DOWN +"/" +fileDefine.getName());
									if(!datafile.exists() || datafile.isDirectory()){
										this.setMainMessage("上传数据文件不全！");
									}
									InputStream is =  new FileInputStream(datafile);
									List<List<Object>> listExcle = CustomExcelUtil.importExcel(is);
									Collection<String> collection = CustomExcelServlet.saveFile2DB(listExcle);
									
									//记录上传日志
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
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new AppException("接收校验方案异常，异常信息："+e.getMessage());
			}
			this.setMainMessage("接收成功！");
			this.closeCueWindowByYes("QueryRecieveWin");
	 }
	 
	 /**
	  * 导入扩充标准代码
	  * @param filename
	  * @throws AppException
	  */
	 private void codeValueRecieveCue(String filename) throws AppException {
		 	String packXmlStr = null;//总包文件内容字符串
			ZwhzPackDefine zpdefine = null;//总包文件解析对象
		    HBSession sess = HBUtil.getHBSession();
			try {
				List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
				for (Ftpuser ftpuser : ftpus) {
					String zhbupfile = AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.DM_DOWN +"/" +filename; 
					File pfile = new File(zhbupfile);
					if (pfile.exists() && pfile.isFile()) {
						packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); //读取总包文件内容
						if(packXmlStr!=null){
							zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
							List<SFileDefine> files = zpdefine.getSfile();
							if(files!=null && files.size()>0){
								for (SFileDefine fileDefine : files) {
									File datafile = new File(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.DM_DOWN +"/" +fileDefine.getName());
									if(!datafile.exists() || datafile.isDirectory()){
										this.setMainMessage("上传数据文件不全！");
									}else{
										setCodeValue(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.DM_DOWN +"/" +fileDefine.getName());
									}
								}
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new AppException("接收扩充标准代码异常，异常信息："+e.getMessage());
			}
			this.setMainMessage("接收成功！");
			this.closeCueWindowByYes("QueryRecieveWin");
	 }
	 
	 
		public void setCodeValue(String filename) throws Exception{
			File file = new File(filename);
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = null;
			HBSession session = HBUtil.getHBSession();
		    try {
		        reader = new BufferedReader(new FileReader(file));
		        String tempString = null;
		        // 一次读入一行，直到读入null为文件结束
		        while ((tempString = reader.readLine()) != null) {
		            // 显示行号
		            sb.append(tempString);
		        }
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (reader != null) {
			        try {
			            reader.close();
			        } catch (IOException e1) {
			        }
		        }
		    }
//		    String xmlStr = com.insigma.siis.local.util.FileUtil.read6String(file);
		    List<CodeValue> list = null;
		    CommonQueryBS.systemOut(sb.toString());
		    try {
				list = parseXml(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		    for(CodeValue cv : list ) {
		    	CodeValue checkValue = (CodeValue) session.createQuery("from CodeValue where codeType=:codeType and codeValue=:codeValue")
		    			.setParameter("codeType", cv.getCodeType()).setParameter("codeValue", cv.getCodeValue()).uniqueResult();//检查有没有重复的codeType+codeValue
		    	CodeValue checkSeq = (CodeValue) session.createQuery("from CodeValue where codeValueSeq=:codeValueSeq")
		    			.setParameter("codeValueSeq", cv.getCodeValueSeq()).uniqueResult();//检查有没有重复的seq
		    	if(checkSeq!=null) {
		    		ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
		    		int seq = bs6.getMaxCodeValueSeq()+1;
		    		cv.setCodeValueSeq(seq);
		    	}
		    	if(checkValue == null) {//接收的机构没有该codevalue
		    		CodeDownload cd = new CodeDownload();
		    		cd.setCodeValueSeq(cv.getCodeValueSeq());
		    		cd.setDownloadStatus("0");
		    		Transaction t = session.getTransaction();
		    		t.begin();
		    		session.save(cv);
		    		session.save(cd);
		    		t.commit();
		    	}
		    }
    		new LogUtil().createLog("761", "接收管理接收扩展代码集", "", "", null, null);
		}
		
		public List<CodeValue> parseXml(String xml) throws Exception {
			SAXReader reader = new SAXReader();
			Document  document = reader.read(new ByteArrayInputStream(xml.getBytes("GBK")));
			Element rootElm = document.getRootElement();
			List<Element> tableElms = rootElm.elements("Data");
			List<CodeValue> list = null;
			if(tableElms!=null && !tableElms.isEmpty()){
				list = new ArrayList<CodeValue>();
			}else{
				return null;
			}
			for (int i = 0; i < tableElms.size(); i++) {
				CodeValue cv = new CodeValue();
				Element tableElm = tableElms.get(i);
				int codeValueSeq = Integer.parseInt(tableElm.element("codeValueSeq").getText());
				cv.setCodeValueSeq(codeValueSeq);
				String codeType = tableElm.element("codeType").getText();
				cv.setCodeType(codeType);
				String codeValue = tableElm.element("codeValue").getText();
				cv.setCodeValue(codeValue);
				String subCodeValue = tableElm.element("subCodeValue").getText();
				cv.setSubCodeValue(subCodeValue);
				String codeName = tableElm.element("codeName").getText();
				cv.setCodeName(codeName);
				String codeName2 = tableElm.element("codeName2").getText();
				cv.setCodeName2(codeName2);
				String codeName3 = tableElm.element("codeName3").getText();
				cv.setCodeName3(codeName3);
				String codeSpelling = tableElm.element("codeSpelling").getText();
				cv.setCodeSpelling(codeSpelling);
				String iscustomize = tableElm.element("iscustomize").getText();
				cv.setIscustomize(iscustomize);
				String codeStatus = tableElm.element("codeStatus").getText();
				cv.setCodeStatus(codeStatus);
				String codeLeaf = tableElm.element("codeLeaf").getText();
				cv.setCodeLeaf(codeLeaf);
				list.add(cv);
			}
			return list;
		}
		 
		 /**
		  * 导入补充信息
		  * @param filename
		  * @throws AppException
		  */
		 private void addValueRecieveCue(String filename) throws AppException {
			 	String packXmlStr = null;//总包文件内容字符串
				ZwhzPackDefine zpdefine = null;//总包文件解析对象
			    HBSession sess = HBUtil.getHBSession();
				try {
					List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
					for (Ftpuser ftpuser : ftpus) {
						String zhbupfile = AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.ZB_DOWN +"/" +filename; 
						File pfile = new File(zhbupfile);
						if (pfile.exists() && pfile.isFile()) {
							packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); //读取总包文件内容
							if(packXmlStr!=null){
								zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
								List<SFileDefine> files = zpdefine.getSfile();
								if(files!=null && files.size()>0){
									for (SFileDefine fileDefine : files) {
										File datafile = new File(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.ZB_DOWN +"/" +fileDefine.getName());
										if(!datafile.exists() || datafile.isDirectory()){
											this.setMainMessage("上传数据文件不全！");
										}else{
											setAddValue(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.ZB_DOWN +"/" +fileDefine.getName());
										}
									}
								}
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					throw new AppException("接收补充信息异常，异常信息："+e.getMessage());
				}
				this.setMainMessage("接收成功！");
				this.closeCueWindowByYes("QueryRecieveWin");
		 }
		 
			public void setAddValue(String filename) throws SQLException {
				File file = new File(filename);
				StringBuffer sb = new StringBuffer();
				BufferedReader reader = null;
				HBSession session = HBUtil.getHBSession();
				try {
			        reader = new BufferedReader(new FileReader(file));
			        String tempString = null;
			        // 一次读入一行，直到读入null为文件结束
			        while ((tempString = reader.readLine()) != null) {
			            // 显示行号
			            sb.append(tempString);
			        }
			        reader.close();
			    } catch (IOException e) {
			        e.printStackTrace();
			    } finally {
			        if (reader != null) {
				        try {
				            reader.close();
				        } catch (IOException e1) {
				        }
			        }
			    }
				Map<AddType,List<AddValue>> map = null;
			    try {
				  map = parseAddTypeXml(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			    for(Map.Entry<AddType, List<AddValue>> entry : map.entrySet()) {
			    	AddType addType = entry.getKey();
			    	List<AddValue> list = entry.getValue();
			    	//存入addType 如果已存在则不存
			    	//存入addValue 如果已存在则不存
			    	Transaction t = session.getTransaction();
			    	t.begin();
			    	AddType addTypeCheck = (AddType) HBUtil.getHBSession().createQuery("from AddType where addTypeId=:addTypeId")
			    			.setParameter("addTypeId", addType.getAddTypeId()).uniqueResult();
			    	if(addTypeCheck == null) {
			    		int seq = bs6.getMaxSeq()+1;
			    		addType.setAddTypeSequence(seq);
			    		session.save(addType);
			    		for(AddValue addValue : list) {
			    			session.save(addValue);
			    		}
			    	} else {
			    		for(AddValue addValue : list) {
			    			AddValue addValueCheck = (AddValue) HBUtil.getHBSession().createQuery("from AddValue where addValueId=:addValueId")
			    	    			.setParameter("addValueId", addValue.getAddValueId()).uniqueResult();
			    			if(addValueCheck == null) {//验证是否已存在
			    				int seq = bs6.getMaxAddValueSeq(addType.getAddTypeId())+1;
			    				addValue.setAddValueSequence(seq);
			    				session.save(addValue);
			    			}
			    		}
			    	}
			    	t.commit();
			    }
		    	new LogUtil().createLog("762", "接收管理接收补充信息项", "", "", null, null);
			}
			
			public Map<AddType,List<AddValue>> parseAddTypeXml(String xml) throws Exception {
				SAXReader reader = new SAXReader();
				Document  document = reader.read(new ByteArrayInputStream(xml.getBytes("GBK")));
				Element rootElm = document.getRootElement();
				List<Element> tableElms = rootElm.elements("Data");
				Map<AddType,List<AddValue>> map = null;
				if(tableElms!=null && !tableElms.isEmpty()){
					map = new HashMap<AddType,List<AddValue>>();
				}else{
					return null;
				}
				for (int i = 0; i < tableElms.size(); i++) {
					AddType at = new AddType();
					List<AddValue> avList = new ArrayList<AddValue>();
					Element tableElm = tableElms.get(i);
					String addTypeId = tableElm.element("AddTypeId").getText();
					at.setAddTypeId(addTypeId);
					String addTypeName = tableElm.element("AddTypeName").getText();
					at.setAddTypeName(addTypeName);
					String addTypeDetail = tableElm.element("AddTypeDetail").getText();
					at.setAddTypeDetail(addTypeDetail);
					String tableCode = tableElm.element("TableCode").getText();
					at.setTableCode(tableCode);
					Element addValueElement = tableElm.element("AddValue");
					List<Element> addValueElms = addValueElement.elements("Data");
					for(int j=0; j<addValueElms.size(); j++) {
						AddValue av = new AddValue();
						Element addValueElm = addValueElms.get(j);
						String addValueId = addValueElm.element("AddValueId").getText();
						av.setAddValueId(addValueId);
						String addTypeId0 = addValueElm.element("AddTypeId").getText();
						av.setAddTypeId(addTypeId0);
						String addValueName = addValueElm.element("AddValueName").getText();
						av.setAddValueName(addValueName);
						String colCode = addValueElm.element("ColCode").getText();
						av.setColCode(colCode);
						String colType = addValueElm.element("ColType").getText();
						av.setColType(colType);
						String publishStatus = addValueElm.element("PublishStatus").getText();
						av.setPublishStatus(publishStatus);
						String isused = addValueElm.element("Isused").getText();
						av.setIsused(isused);
						String multilineshow = addValueElm.element("Multilineshow").getText();
						av.setMultilineshow(multilineshow);
						String addValueDetail = addValueElm.element("AddValueDetail").getText();
						addValueDetail = "null".equalsIgnoreCase(addValueDetail)?"":addValueDetail;
						av.setAddValueDetail(addValueDetail);
						String codeType = addValueElm.element("CodeType").getText();
						codeType = "null".equalsIgnoreCase(codeType)?"":codeType;
						av.setCodeType(codeType);
						avList.add(av);
					}
					map.put(at, avList);
				}
				return map;
			}
}
