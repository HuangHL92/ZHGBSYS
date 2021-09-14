package com.insigma.siis.local.business.datavaerify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.FilterInputStreamReader;
import org.dom4j.io.SAXReader;
import org.hibernate.Hibernate;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
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
import com.insigma.siis.local.business.entity.ImpProcess;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.comm.BusinessBSSupport;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class UploadHzbFileBS extends BusinessBSSupport {

	public static void appendFileContent(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rollbackImp(String imprecordid) {
		if (imprecordid == null || "".equals(imprecordid.replace(" ", ""))) {
			new AppException("发生异常，原因传入参数为空或空字符串！");
			return;
		}
		if (imprecordid != null && !imprecordid.equals("")) {
			HBSession sess = HBUtil.getHBSession();
			try {
				// sess.beginTransaction();
				String tables1[] = { "A01", "A02", "A06", "A08", "A11", "A14",
						"A15", "A29", "A30", "A31", "A36", "A37", "A41", "A53",
						"A57", "B01" };
				for (int i = 0; i < tables1.length; i++) {
					sess.createSQLQuery(
							" delete from " + tables1[i]
									+ "_temp where imprecordid='" + imprecordid
									+ "'").executeUpdate();
				}
				sess.createSQLQuery(
						" delete from B01TEMP_B01 where imprecordid='"
								+ imprecordid + "'").executeUpdate();
				sess.createSQLQuery(
						" delete from imp_record where IMP_RECORD_ID='"
								+ imprecordid + "'").executeUpdate();

				// sess.getTransaction().commit();
			} catch (Exception e) {
				sess.getTransaction().rollback();
				e.printStackTrace();
			}
		}
	}

	public InputStream getInputStream(File photo) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = -1;
		InputStream fileInput = null;
		InputStream inputStream = null;
		try {
			fileInput = new FileInputStream(photo);
			while ((n = fileInput.read(buffer)) != -1) {
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
				if (fileInput != null)
					fileInput.close();
				if (baos != null)
					baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			try {
				if (fileInput != null)
					fileInput.close();
				if (baos != null)
					baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					if (fileInput != null)
						fileInput.close();
					if (baos != null)
						baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getRootPath() {
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";

		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	public int saveData_SaxHander2(String lowerCase, String table,
			String imprecordid, int t_n, String uuid, String from_file,
			String B0111, String deptid, String impdeptid) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 0;
		int t_n1 = t_n;
		String docname = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid
				+ "/Table/" + table + ".xml";// xml文件路径
		File file = new File(docname);
		if (!file.exists()) {
			return t_n1;
		}
		int k = t_n;
		if (table.equals("A57")) { // A57处理
			try {
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57_temp(A0000,A5714,UPDATED,ERROR_INFO,IS_QUALIFIED,"
								+ "IMPRECORDID,PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?,?,?,?)");
				MySaxHandlerA57T myh = new MySaxHandlerA57T(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXReader reader = new SAXReader();
				reader.setStripWhitespaceText(false);
				reader.addHandler("/xml/data/row", myh);
				reader.addHandler("/xml/data/UPDATE/original/row", myh);
				reader.read(new FileInputStream(docname));
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				k = myh.t_n;
				boolean isError = myh.isError;
				// pstmt1.executeBatch();
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} catch (Exception e) {
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw e;
			}

		} else {
			try {
				// String lname = user.getLoginname();
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = null;
				if (DBType.MYSQL == DBUtil.getDBType()
						|| DBUtil.getDBType().equals(DBType.MYSQL)) {
					sql = "select column_name from information_schema.columns a where table_name = upper('"
							+ table + "_TEMP') and a.TABLE_SCHEMA = 'ZWHZYQ'";
				} else if (DBType.ORACLE == DBUtil.getDBType()) {
					sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"
							+ table + "_TEMP')";
				}
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1
						.prepareStatement("insert into " + table + "_temp("
								+ colomn_sql + ") values(" + value_sql + ")");
				MySaxHandlerT myh = new MySaxHandlerT(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, colomns, batchNum);
				SAXReader reader = new SAXReader();
				reader.setEncoding("UTF-8");
				reader.setStripWhitespaceText(false);
				reader.addHandler("/xml/data/row", myh);
				CommonQueryBS
						.systemOut("reader.read(new FilterInputStreamReader(.......))========start");
				// reader.read(new FileInputStream(docname),"utf-8");
				reader.read(new FilterInputStreamReader(new FileInputStream(
						docname), "utf-8"));
				CommonQueryBS
						.systemOut("reader.read(new FilterInputStreamReader(.......))========end");
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();
				System.gc();
				if (lowerCase.equals("zb3")) {
					if (DBType.ORACLE == DBUtil.getDBType()
							|| DBUtil.getDBType().equals(DBType.ORACLE)) {
						if (table.equals("A36")) {
							sess.createSQLQuery(
									"UPDATE a36_temp t set t.A3604a = (select c.code_value from code_value c where c.code_type = 'GB4761' and c.code_name = t.A3604a) where imprecordid='"
											+ imprecordid
											+ "' and A3604a is not null and exists (select 1 from code_value where code_type='GB4761' and code_name=t.A3604a)")
									.executeUpdate();
							sess.createSQLQuery(
									"UPDATE a36_temp t set t.A3627 = (select c.code_value from code_value c where c.code_type = 'GB4762' and c.code_name = t.A3627) where imprecordid='"
											+ imprecordid
											+ "' and A3627 is not null and exists (select 1 from code_value where code_type='GB4762' and code_name=t.A3627)")
									.executeUpdate();
						} else if (table.equals("A01")) {
							sess.createSQLQuery(
									"UPDATE a01_temp t set t.A0111 = (select min(c.code_value) from code_value c where c.code_type = 'ZB01' and c.code_name3 = t.A0111a) where imprecordid='"
											+ imprecordid
											+ "' and A0111a is not null and exists (select 1 from code_value where code_type='ZB01' and code_name3=t.A0111a)")
									.executeUpdate();
							sess.createSQLQuery(
									"UPDATE a01_temp t set t.A0114 = (select min(c.code_value) from code_value c where c.code_type = 'ZB01' and c.code_name3 = t.A0114a) where imprecordid='"
											+ imprecordid
											+ "' and A0114a is not null and exists (select 1 from code_value where code_type='ZB01' and code_name3=t.A0114a)")
									.executeUpdate();
						}
					} else if (DBType.MYSQL == DBUtil.getDBType()) {
						String time = System.currentTimeMillis() + "";
						String logfilename = AppConfig.HZB_PATH
								+ "/temp/sql_"
								+ DateUtil.timeToString(
										DateUtil.getTimestamp(),
										"yyyyMMddHHmmss") + ".txt";
						File logfile = new File(logfilename);
						if (!logfile.exists()) {
							logfile.createNewFile();
						}
						if (table.equals("A36")) {
							appendFileContent(logfilename, "a36_temp_sql:"
									+ DateUtil.getTime() + "\n");
							sess.createSQLQuery(
									"create TABLE c_GB4761"
											+ time
											+ " as select * from code_value AS k where k.code_type = 'GB4761'")
									.executeUpdate();
							sess.createSQLQuery(
									"UPDATE a36_temp AS t SET t.A3604A =(select k.code_value from c_GB4761"
											+ time
											+ " AS k where k.code_name =t.A3604A) WHERE t.IMPRECORDID='"
											+ imprecordid + "'")
									.executeUpdate();
							sess.createSQLQuery(
									"drop table c_GB4761" + time + "")
									.executeUpdate();
							appendFileContent(logfilename,
									"sql:" + DateUtil.getTime() + "\n");
							sess.createSQLQuery(
									"create TABLE c_GB4762"
											+ time
											+ " as select * from code_value AS k where k.code_type = 'GB4762'")
									.executeUpdate();
							sess.createSQLQuery(
									"UPDATE a36_temp AS t SET t.A3627 =(select k.code_value from c_GB4762"
											+ time
											+ " AS k where k.code_name =t.A3627) WHERE t.IMPRECORDID='"
											+ imprecordid + "'")
									.executeUpdate();
							sess.createSQLQuery(
									"drop table c_GB4762" + time + "")
									.executeUpdate();
							appendFileContent(logfilename,
									"sql:" + DateUtil.getTime() + "\n");
						} else if (table.equals("A01")) {
							appendFileContent(logfilename, "a01_temp_sql:"
									+ DateUtil.getTime() + "\n");
							sess.createSQLQuery(
									"create TABLE c_ZB01"
											+ time
											+ " as select * from code_value AS k where k.code_type = 'ZB01'")
									.executeUpdate();
							sess.createSQLQuery(
									"UPDATE a01_temp AS t,c_ZB01"
											+ time
											+ " AS k SET t.A0111 = k.code_value WHERE t.A0111a = k.code_name3 andt.IMPRECORDID='"
											+ imprecordid
											+ "' AND k.code_type = 'ZB01'")
									.executeUpdate();
							sess.createSQLQuery(
									"UPDATE a01_temp AS t,c_ZB01"
											+ time
											+ " AS k SET t.A0114 = k.code_value WHERE t.A0114a = k.code_name3 andt.IMPRECORDID='"
											+ imprecordid
											+ "' AND k.code_type = 'ZB01'")
									.executeUpdate();
							sess.createSQLQuery("drop table c_ZB01" + time + "")
									.executeUpdate();
						}
					}
				}

				k = myh.t_n;
				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();
			} catch (Exception e) {
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw e;
			}
		}
		return k;
	}

	public String getRefreshInfo(String id) {
		HBSession sess = HBUtil.getHBSession();
		List<ImpProcess> list = sess.createQuery(
				" from ImpProcess where IMPRECORDID='" + id
						+ "' order by processtype asc").list();
		StringBuffer info = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (ImpProcess impProcess : list) {
				info.append(impProcess.getProcessname()
						+ "@"
						+ impProcess.getProcessstatus()
						+ "@"
						+ (impProcess.getProcessinfo() == null ? ""
								: impProcess.getProcessinfo()) + ",");
			}
		}
		return info.toString();
	}

	public String getdeptSelect() {
		HBSession sess = HBUtil.getHBSession();
		List<B01> list = sess
				.createQuery(
						" from B01 where b0111<>'-1' and b0194 in ('1','3') order by b0111 asc")
				.list();
		StringBuffer info = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (B01 b01 : list) {
				info.append(b01.getB0111() + "@" + b01.getB0101() + ",");
			}
		}
		return info.toString();
	}

	public int saveData_SaxHander3(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String B0111,
			String deptid, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 10000;
		int t_n = 0;
		// String docname = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid +
		// "/Table/" +
		// (table.equals("I_E")?"INFO_EXTEND":table.equals("B_E")?"B01_EXT":table)
		// + ".xml";//xml文件路径
		String docname = "";
		/*
		 * if(lowerCase.equalsIgnoreCase("zip")){ File dir = new
		 * File(AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid);
		 * File[] subs = dir.listFiles(); File f = subs[0]; docname =
		 * AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid + "/" +
		 * f.getName() + "/Table/" + table + ".xml";//xml文件路径 }else{
		 */
		docname = AppConfig.HZB_PATH
				+ "/temp/upload/unzip/"
				+ uuid
				+ "/Table/"
				+ (table.equals("I_E") ? "INFO_EXTEND"
						: table.equals("B_E") ? "B01_EXT" : table) + ".xml";// xml文件路径
		/* } */
		File file = new File(docname);
		if (!file.exists()) {
			// file.mkdirs();
			return 0;
		}
		int k = t_n;
		if (table.equals("A57")) { // A57处理
			try {
				// ----------------
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57"
								+ tableExt
								+ "(A0000,A5714,UPDATED,"
								+ "PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?)");
				XmlSAXHandler01 myh = new XmlSAXHandler01(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				// k = myh.t_n;
				boolean isError = myh.isError;
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} catch (Exception e) {
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw e;
			}

		} else {
			try {
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"
						+ table + tableExt + "')";
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}

				pstmt1 = conn1.prepareStatement("insert into " + table
						+ tableExt + "(" + colomn_sql + ") values(" + value_sql
						+ ")");
				System.out.println("table======="+docname+table);
				XmlSAXHandler02 myh = new XmlSAXHandler02(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, colomns, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();

				if (table.equals("A01")) {
					k = myh.t_n;
				}
				// table.equals("A01")?k = myh.t_n:k = 0;
				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();

			} catch (Exception e) {//本库，数据包，姓名，工作单位及职务
				e.printStackTrace();
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				throw e;
			}
		}
		return k;
	}

	public int saveData_SaxHander4(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String B0111,
			String deptid, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 2000;
		int t_n = 0;
		String docname = "";
		docname = AppConfig.HZB_PATH
				+ "/temp/upload/unzip/"
				+ uuid
				+ "/Table/"
				+ (table.equals("I_E") ? "INFO_EXTEND"
						: table.equals("B_E") ? "B01_EXT" : table) + ".xml";// xml文件路径
		/* } */
		File file = new File(docname);
		if (!file.exists()) {
			// file.mkdir();
			return 0;
		}
		int k = t_n;
		try {
			if (table.equals("A57")) { // A57处理
				// ----------------
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57"
								+ tableExt
								+ "(A0000,A5714,UPDATED,"
								+ "PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?)");
				XmlSAXHandler01 myh = new XmlSAXHandler01(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				boolean isError = myh.isError;
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} else {
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = sql = "select column_name from information_schema.columns a where table_name = upper('"
						+ table + tableExt + "') and a.TABLE_SCHEMA = 'ZWHZYQ'";
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1.prepareStatement("insert into " + table
						+ tableExt + "(" + colomn_sql + ") values(" + value_sql
						+ ")");

				XmlSAXHandler02 myh = new XmlSAXHandler02(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, colomns, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();
				if (table.equals("A01")) {
					k = myh.t_n;
				}

				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();

			}
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("3", "4", "失败:" + e.getMessage(),
						uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}

		return k;

	}

	public int saveData_SaxHanderWagesMysql(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 2000;
		int t_n = 0;
		String docname = "";
		docname = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid + "/Table/" + table + ".xml";// xml文件路径
		File file = new File(docname);
		if (!file.exists()) {
			return 0;
		}
		int k = t_n;
		try {
			Connection conn1 = sess.connection();
			// --------------------------------------------------------------------------------------------------------------
			StringBuffer colomn_sql = new StringBuffer();// b01字段连接
															// 如(a0000,A0200......)
			StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
			PreparedStatement pstmt1 = null;
			List colomns = null;
			String sql = sql = "select column_name from information_schema.columns a where table_name = upper('"
					+ table + tableExt + "') and a.TABLE_SCHEMA = 'ZWHZYQ'";
			colomns = sess.createSQLQuery(sql).list();
			CommonQueryBS.systemOut(" CodeTableCol 开始"
					+ table
					+ "获取List column："
					+ (Runtime.getRuntime().totalMemory() - Runtime
							.getRuntime().freeMemory()));
			if (colomns != null) {
				for (int j = 0; j < colomns.size(); j++) {
					String column = (String) colomns.get(j);
					colomn_sql.append(column);
					value_sql.append("?");
					if (j != colomns.size() - 1) {
						colomn_sql.append(",");
						value_sql.append(",");
					}
				}
			} else {
				throw new RadowException("数据库异常，请联系管理员！");
			}
			pstmt1 = conn1.prepareStatement("insert into " + table
					+ tableExt + "(" + colomn_sql + ") values(" + value_sql
					+ ")");
			XmlSAXHandler02 myh = new XmlSAXHandler02(docname, lowerCase,
					table, imprecordid, t_n, uuid, from_file, "",
					"", impdeptid, conn1, pstmt1, colomns, batchNum);
			SAXParserFactory saxParserFactory = SAXParserFactory
					.newInstance();
			// 获取SAXParser分析器的实例
			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new InputSource(new FilterInputStreamReader(
					new FileInputStream(docname), "utf-8")), myh);
			pstmt1.executeBatch();
			pstmt1.clearBatch();
			pstmt1.close();
			if (colomns != null)
				colomns.clear();
			colomns = null;
			conn1.close();

			boolean isError = myh.isError;
			if (isError) {
				throw new Exception("解析数据异常！");
			}
			System.gc();
		} catch (Exception e) {
			System.out.println("导入出错的表==>"+table+"&"+tableExt);
			try {
				KingbsconfigBS.saveImpDetail("3", "4", "失败:" + e.getMessage(),
						uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}

		return k;

	}
	public int saveData_SaxHanderWagesOracle(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String impdeptid, String tableExt) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 2000;
		int t_n = 0;
		String docname = "";
		docname = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid + "/Table/" + table + ".xml";// xml文件路径
		File file = new File(docname);
		if (!file.exists()) {
			return 0;
		}
		int k = t_n;
		try {
			Connection conn1 = sess.connection();
			// --------------------------------------------------------------------------------------------------------------
			StringBuffer colomn_sql = new StringBuffer();// b01字段连接
			// 如(a0000,A0200......)
			StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
			PreparedStatement pstmt1 = null;
			List colomns = null;
			String sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"
					+ table + tableExt + "')";
			colomns = sess.createSQLQuery(sql).list();
			CommonQueryBS.systemOut(" CodeTableCol 开始"
					+ table
					+ "获取List column："
					+ (Runtime.getRuntime().totalMemory() - Runtime
							.getRuntime().freeMemory()));
			if (colomns != null) {
				for (int j = 0; j < colomns.size(); j++) {
					String column = (String) colomns.get(j);
					colomn_sql.append(column);
					value_sql.append("?");
					if (j != colomns.size() - 1) {
						colomn_sql.append(",");
						value_sql.append(",");
					}
				}
			} else {
				throw new RadowException("数据库异常，请联系管理员！");
			}
			pstmt1 = conn1.prepareStatement("insert into " + table
					+ tableExt + "(" + colomn_sql + ") values(" + value_sql
					+ ")");
			XmlSAXHandler02 myh = new XmlSAXHandler02(docname, lowerCase,
					table, imprecordid, t_n, uuid, from_file, "",
					"", impdeptid, conn1, pstmt1, colomns, batchNum);
			SAXParserFactory saxParserFactory = SAXParserFactory
					.newInstance();
			// 获取SAXParser分析器的实例
			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new InputSource(new FilterInputStreamReader(
					new FileInputStream(docname), "utf-8")), myh);
			pstmt1.executeBatch();
			pstmt1.clearBatch();
			pstmt1.close();
			if (colomns != null)
				colomns.clear();
			colomns = null;
			conn1.close();
			if (table.equals("A01")) {
				k = myh.t_n;
			}

			boolean isError = myh.isError;
			if (isError) {
				throw new Exception("解析数据异常！");
			}
			System.gc();
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("3", "4", "失败:" + e.getMessage(),
						uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}

		return k;

	}
	public void createTempTableMake(String tableExt) throws Exception {
		if (tableExt == null || "".equals(tableExt.replace(" ", ""))) {
			new AppException("发生异常，原因传入参数为空或空字符串！");
			return;
		}
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			conn = sess.connection();
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			if (DBUtil.getDBType().equals(DBType.ORACLE)) {
				// 以下代码注释的是字段没有修改之前的
				String a01sql = " create table A01"
						+ tableExt + " AS SELECT * FROM A01 WHERE 1=2";
						//+ " ( a0000 VARCHAR2(120), a0101 VARCHAR2(36), a0102 VARCHAR2(2500), a0104 VARCHAR2(2), a0107 VARCHAR2(8), a0111a VARCHAR2(200), a0114a VARCHAR2(200), a0115a VARCHAR2(120), a0117 VARCHAR2(2), a0128 VARCHAR2(120), a0134 VARCHAR2(8), a0140 VARCHAR2(100), a0141 VARCHAR2(8), a0144 VARCHAR2(8), a3921  VARCHAR2(8), a3927 VARCHAR2(8), a0160 VARCHAR2(8), a0163 VARCHAR2(2), a0165 VARCHAR2(8), a0184 VARCHAR2(18), a0187a VARCHAR2(120), a0192 VARCHAR2(2000), a0192a VARCHAR2(2000), a0221 VARCHAR2(5), a0288 VARCHAR2(8), a0192d VARCHAR2(80), a0192c VARCHAR2(8), a0196 VARCHAR2(120), a0197 VARCHAR2(1), a0195 VARCHAR2(68), a1701 CLOB, a14z101 VARCHAR2(2000), a15z101 VARCHAR2(2000), a0120 VARCHAR2(8), a0121 VARCHAR2(8), a2949 VARCHAR2(8), a0122 VARCHAR2(8), a0104a VARCHAR2(60), a0111 VARCHAR2(8), a0114 VARCHAR2(8), a0117a VARCHAR2(60), a0128b VARCHAR2(120), a0141d VARCHAR2(100), a0144b VARCHAR2(8), a0144c VARCHAR2(8), a0148  VARCHAR2(20), a0148c VARCHAR2(8), a0149  VARCHAR2(80), a0151  VARCHAR2(1), a0153  VARCHAR2(1), a0157  VARCHAR2(120), a0158  VARCHAR2(5), a0159  VARCHAR2(50), a015a  VARCHAR2(2), a0161  VARCHAR2(500), a0162  VARCHAR2(8), a0180  VARCHAR2(1500), a0191  VARCHAR2(1), a0192b VARCHAR2(2000), a0193  VARCHAR2(5), a0194u VARCHAR2(8), a0198  VARCHAR2(200), a0199  VARCHAR2(1), a01k01 VARCHAR2(1), a01k02 VARCHAR2(8), cbdresult VARCHAR2(10), cbdw  VARCHAR2(100), isvalid VARCHAR2(20), nl VARCHAR2(4), nmzw   VARCHAR2(100), nrzw  VARCHAR2(100), orgid  VARCHAR2(500), qrzxl VARCHAR2(60), qrzxlxx VARCHAR2(120), qrzxw VARCHAR2(60), qrzxwxx VARCHAR2(120), rmly   VARCHAR2(100), status VARCHAR2(1), tbr VARCHAR2(36), tbrjg  VARCHAR2(80), userlog VARCHAR2(20), xgr  VARCHAR2(36), zzxl VARCHAR2(60), zzxlxx VARCHAR2(120), zzxw VARCHAR2(60), zzxwxx VARCHAR2(120), a0155 DATE, age NUMBER(4), jsnlsj DATE, resultsortid NUMBER(4), tbsj DATE, xgsj DATE, sortid NUMBER(16), a0194  NUMBER(8), a0192e VARCHAR2(80),a0192f VARCHAR2(200), a0000new VARCHAR2(120),ERROR_INFO  VARCHAR2(200),TORGID VARCHAR2(200),TORDER VARCHAR2(8),ZGXL VARCHAR2(60),ZGXLXX VARCHAR2(120),ZGXW VARCHAR2(60),ZGXWXX VARCHAR2(120),TCSJSHOW VARCHAR2(16),TCFSSHOW VARCHAR2(40),fsj VARCHAR2(1) ) ";
				//add zepeng 20190322  使用原表创建临时表解决总超长
				stmt.execute(a01sql);
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a01sql = "create table A01"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a0101  VARCHAR2(36),a0104  VARCHAR2(2),a0104a  VARCHAR2(60),a0107 VARCHAR2(8),a0111  VARCHAR2(8),a0111a  VARCHAR2(200),a0114 VARCHAR2(8),a0114a  VARCHAR2(200),a0115a  VARCHAR2(120),a0117 VARCHAR2(2),a0117a  VARCHAR2(60),a0134 VARCHAR2(8),a0144 VARCHAR2(8),a0144b  VARCHAR2(8),a0144c  VARCHAR2(8),a0148 VARCHAR2(20),a0149 VARCHAR2(80),a0151 VARCHAR2(1),a0153 VARCHAR2(1),a0155 DATE,a0157 VARCHAR2(120),a0158 VARCHAR2(5),a0159 VARCHAR2(50),a015a VARCHAR2(2),a0160 VARCHAR2(8),a0161 VARCHAR2(500),a0162 VARCHAR2(8),a0163 VARCHAR2(2),a0165 VARCHAR2(8),a0184 VARCHAR2(18),a0191 VARCHAR2(1),a0192 VARCHAR2(2000),a0192a  VARCHAR2(2000),a0221  VARCHAR2(5),a0288  VARCHAR2(8),a0192b  VARCHAR2(2000),a0193 VARCHAR2(5),a0195 VARCHAR2(68),a0196 VARCHAR2(120),a0197 VARCHAR2(1),a0198 VARCHAR2(200),a0199 VARCHAR2(1),a01k01  VARCHAR2(1),a01k02  VARCHAR2(8),age NUMBER(4),cbdw  VARCHAR2(100),isvalid VARCHAR2(20),jsnlsj  DATE,nl  VARCHAR2(4),nmzw  VARCHAR2(100),nrzw  VARCHAR2(100),qrzxl VARCHAR2(60),qrzxlxx VARCHAR2(120),qrzxw VARCHAR2(60),qrzxwxx VARCHAR2(120),resultsortid  NUMBER(4),rmly  VARCHAR2(100),tbr VARCHAR2(36),tbsj  DATE,userlog VARCHAR2(20),xgr VARCHAR2(36),xgsj  DATE,zzxl  VARCHAR2(60),zzxlxx  VARCHAR2(120),zzxw  VARCHAR2(60),zzxwxx  VARCHAR2(120),a3927 VARCHAR2(8),a0102 VARCHAR2(2500),a0128b  VARCHAR2(120),a0128 VARCHAR2(120),a0140 VARCHAR2(100),a0187a  VARCHAR2(120),a0148c  VARCHAR2(8),a14z101 VARCHAR2(2000),a15z101 VARCHAR2(2000),a0141d  VARCHAR2(100),a0141 VARCHAR2(8),a3921 VARCHAR2(8),sortid  NUMBER(16),a0180 VARCHAR2(500),a0194 NUMBER(8),a0192d  VARCHAR2(80),a0192c  VARCHAR2(8),status  VARCHAR2(1) default '1',tbrjg VARCHAR2(80),a0120 VARCHAR2(8),a0121 VARCHAR2(8),a2949 VARCHAR2(8),a0122 VARCHAR2(8),a0194u  VARCHAR2(8),cbdresult VARCHAR2(10),orgid VARCHAR2(500),a1701 CLOB)"
				 * ; stmt.execute(a01sql);
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a02sql = " create table A02"
						+ tableExt + " AS SELECT * FROM A02 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a0200 VARCHAR2(120), a0201a VARCHAR2(200),a0201a_all VARCHAR2(200), a0201b VARCHAR2(199), a0201d VARCHAR2(1), a0201e VARCHAR2(1), a0215a VARCHAR2(100), a0219 VARCHAR2(1), a0223 VARCHAR2(8), a0225 VARCHAR2(8), a0243 VARCHAR2(8), a0245 VARCHAR2(100), a0247 VARCHAR2(2), a0251b VARCHAR2(1), a0255 VARCHAR2(1), a0265 VARCHAR2(8), a0267 VARCHAR2(24), a0272 VARCHAR2(100), a0281 VARCHAR2(8), a0221t NUMBER(8), b0238 VARCHAR2(200), b0239 VARCHAR2(200), a0221a VARCHAR2(8), wage_used NUMBER, updated   VARCHAR2(1), a4907 VARCHAR2(8), a4904 VARCHAR2(8), a4901 VARCHAR2(8), a0299 VARCHAR2(8), a0295 VARCHAR2(8), a0289 VARCHAR2(8), a0288 VARCHAR2(8), a0284 VARCHAR2(8), a0277 VARCHAR2(1), a0271 VARCHAR2(8), a0259 VARCHAR2(8), a0256c NUMBER, a0256b NUMBER, a0256a VARCHAR2(8), a0256 VARCHAR2(8), a0251 VARCHAR2(8), a0229 VARCHAR2(120), a0222 VARCHAR2(20), a0221w VARCHAR2(8), a0221 VARCHAR2(8), a0219w VARCHAR2(8), a0216a VARCHAR2(200), a0215b VARCHAR2(80), a0209 VARCHAR2(8), a0207 VARCHAR2(8), a0204 VARCHAR2(68), a0201c VARCHAR2(200), a0201 VARCHAR2(199),ERROR_INFO  VARCHAR2(200), a0279 VARCHAR2(1) ) ";
				stmt.execute(a02sql);
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a02sql = "create table A02"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a0200  VARCHAR2(120) not null,a0201  VARCHAR2(199),a0201a VARCHAR2(1000),a0201b VARCHAR2(68),a0201c VARCHAR2(200),a0201d VARCHAR2(1),a0201e VARCHAR2(8),a0204  VARCHAR2(68),a0207  VARCHAR2(8),a0209  VARCHAR2(8),a0215a VARCHAR2(80),a0215b VARCHAR2(80),a0216a VARCHAR2(200),a0219  VARCHAR2(8),a0219w VARCHAR2(8),a0221w VARCHAR2(8),a0223  NUMBER,a0225  NUMBER,a0243  VARCHAR2(8),a0245  VARCHAR2(260),a0247  VARCHAR2(8),a0251b VARCHAR2(1),a0255  VARCHAR2(1),a0256  VARCHAR2(8),a0256a VARCHAR2(8),a0256b NUMBER,a0256c NUMBER,a0259  VARCHAR2(8),a0265  VARCHAR2(8),a0267  VARCHAR2(24),a0272  VARCHAR2(100),a0277  VARCHAR2(1),a0281  VARCHAR2(8),a0289  VARCHAR2(8),a0295  VARCHAR2(8),a0299  VARCHAR2(8),UPDATED  VARCHAR2(1) default '1',wage_used  NUMBER,b0239  VARCHAR2(200),b0238  VARCHAR2(200),ERROR_INFO  VARCHAR2(200))"
				 * ; stmt.execute(a02sql);
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a06sql = " create table A06"
						+ tableExt + " AS SELECT * FROM A06 WHERE 1=2";
						//+ "( a0600 VARCHAR2(120), a0000 VARCHAR2(120), a0601 VARCHAR2(8), a0602 VARCHAR2(60), a0604 VARCHAR2(8), a0607 VARCHAR2(8), a0611 VARCHAR2(100), a0614 VARCHAR2(1), a0699 VARCHAR2(8), updated VARCHAR2(1), sortid NUMBER,ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a06sql);
				stmt.execute("ALTER TABLE a06"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a08sql = " create table A08"
						+ tableExt + " AS SELECT * FROM A08 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a0800 VARCHAR2(120), a0801a VARCHAR2(120), a0801b VARCHAR2(8), a0901a VARCHAR2(40), a0901b VARCHAR2(8), a0804 VARCHAR2(8), a0807 VARCHAR2(8), a0904 VARCHAR2(8), a0814 VARCHAR2(120), a0824 VARCHAR2(40), a0827 VARCHAR2(8), a0837 VARCHAR2(1), a0811 VARCHAR2(8), a0898 VARCHAR2(1), a0831 VARCHAR2(1), a0832 VARCHAR2(1), a0834 VARCHAR2(1), a0835 VARCHAR2(1), a0838 VARCHAR2(1), a0839 VARCHAR2(1), a0899 VARCHAR2(8), updated  VARCHAR2(1), sortid NUMBER, wage_used NUMBER ,ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a08sql);
				stmt.execute("ALTER TABLE a08"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a11sql = " create table A11"
						+ tableExt + " AS SELECT * FROM A11 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120) , a1100 VARCHAR2(120), a1101 VARCHAR2(8), a1104 VARCHAR2(8), a1107 VARCHAR2(8), a1107a NUMBER , a1107b NUMBER, a1111 VARCHAR2(8), a1114 VARCHAR2(120), a1121a VARCHAR2(120), a1127 VARCHAR2(8), a1131 VARCHAR2(120), a1134 VARCHAR2(1), a1151 VARCHAR2(1), updated VARCHAR2(1), a1108 NUMBER, a1107c NUMBER,ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a11sql);
				stmt.execute("ALTER TABLE a11"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a11sql = "create table A11"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a1100  VARCHAR2(120) not null,a1101  VARCHAR2(8),a1104  VARCHAR2(8),a1107  VARCHAR2(8),a1107a NUMBER default 0,a1107b NUMBER default 0,a1111  VARCHAR2(8),a1114  VARCHAR2(120),a1121a VARCHAR2(120),a1127  VARCHAR2(8),a1131  VARCHAR2(60),a1134  VARCHAR2(1),a1151  VARCHAR2(1),a1110  VARCHAR2(1),UPDATED  VARCHAR2(1) default '1',a1108  NUMBER,a1107c NUMBER)"
				 * ; stmt.execute(a11sql);
				stmt.execute("ALTER TABLE a11"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a14sql = " create table A14"
						+ tableExt + " AS SELECT * FROM A14 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a1400 VARCHAR2(120), a1404a VARCHAR2(40), a1404b VARCHAR2(8), a1407 VARCHAR2(8), a1411a VARCHAR2(60), a1414 VARCHAR2(8), a1415 VARCHAR2(8), a1424 VARCHAR2(8), a1428 VARCHAR2(8), updated VARCHAR2(1), sortid NUMBER,ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a14sql);
				stmt.execute("ALTER TABLE a14"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a15sql = " create table A15"
						+ tableExt + " AS SELECT * FROM A15 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a1500 VARCHAR2(120), a1517 VARCHAR2(4), a1521 VARCHAR2(4), a1527 VARCHAR2(8), updated VARCHAR2(1) ,ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a15sql);
				stmt.execute("ALTER TABLE a15"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a29sql = " create table A29"
						+ tableExt + " AS SELECT * FROM A29 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a2907 VARCHAR2(8), a2911 VARCHAR2(3), a2921a VARCHAR2(200), a2941 VARCHAR2(40), a2944 VARCHAR2(40), a2921d VARCHAR2(8), a2921c VARCHAR2(8), a2947b NUMBER(4), a2921b VARCHAR2(8), a2947a NUMBER(4), updated VARCHAR2(1), a2949 VARCHAR2(8), a2947 VARCHAR2(8),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a29sql);
				stmt.execute("ALTER TABLE a29"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a29sql = "create table A29"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a2907  VARCHAR2(8),a2911  VARCHAR2(8),a2921a VARCHAR2(60),a2941  VARCHAR2(40),a2944  VARCHAR2(40),a2949  VARCHAR2(8),UPDATED  VARCHAR2(1) default '1',a2921b VARCHAR2(8),a2947b NUMBER(4),a2921c VARCHAR2(8),a2921d VARCHAR2(8))"
				 * ; stmt.execute(a29sql);
				stmt.execute("ALTER TABLE a29"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a30sql = "create table A30"
						+ tableExt + " AS SELECT * FROM A30 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3001 VARCHAR2(8), a3004 VARCHAR2(8), a3117a VARCHAR2(60), a3101 VARCHAR2(1), a3137 VARCHAR2(24), a3034 VARCHAR2(1000), updated VARCHAR2(1), a3007a VARCHAR2(100),a3038 VARCHAR2(100),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a30sql);
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a30sql = "create table A30"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a3001  VARCHAR2(8),a3004  VARCHAR2(8),a3034  VARCHAR2(500),UPDATED  VARCHAR2(1) default '1')"
				 * ; stmt.execute(a30sql);
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a31sql = "create table A31"
						+ tableExt + " AS SELECT * FROM A31 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3101 VARCHAR2(8), a3104 VARCHAR2(8), a3107 VARCHAR2(8), a3117a VARCHAR2(60), a3118 VARCHAR2(40), a3137 VARCHAR2(24), a3138 VARCHAR2(60), updated VARCHAR2(1), a3110 VARCHAR2(80), a3109 VARCHAR2(80), a3108 VARCHAR2(80),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a31sql);
				stmt.execute("ALTER TABLE a31"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a31sql = "create table A31"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a3101  VARCHAR2(8),a3117a VARCHAR2(60),a3137  VARCHAR2(24),a3138  VARCHAR2(60),UPDATED  VARCHAR2(1) default '1')"
				 * ; stmt.execute(a31sql);
				stmt.execute("ALTER TABLE a31"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a36sql = "create table A36"
						+ tableExt + " AS SELECT * FROM A36 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3600 VARCHAR2(120), a3601 VARCHAR2(36), a3604a VARCHAR2(10), a3607 VARCHAR2(8), a3611 VARCHAR2(200), a3627 VARCHAR2(100), sortid NUMBER, updated VARCHAR2(1), a3684 VARCHAR2(18),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a36sql);
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a36sql = "create table A36"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a3600  VARCHAR2(120) not null,a3601  VARCHAR2(36),a3604a VARCHAR2(10),a3607  VARCHAR2(8),a3611  VARCHAR2(200),a3627  VARCHAR2(100),sortid NUMBER,UPDATED  VARCHAR2(1) default '1',a3684  VARCHAR2(18))"
				 * ; stmt.execute(a36sql);
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a37sql = "create table A37"
						+ tableExt + " AS SELECT * FROM A37 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3701 VARCHAR2(120), a3707a VARCHAR2(20), a3707c VARCHAR2(20), a3707e VARCHAR2(20), a3707b VARCHAR2(20), a3708 VARCHAR2(60), a3711 VARCHAR2(120), a3714 VARCHAR2(6), updated VARCHAR2(1),ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a37sql);
				stmt.execute("ALTER TABLE a37"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a41sql = "create table A41"
						+ tableExt + " AS SELECT * FROM A41 WHERE 1=2";
						//+ "( a4100 VARCHAR2(120), a0000 VARCHAR2(120), a1100 VARCHAR2(120), a4101 VARCHAR2(10), a4102 VARCHAR2(10), a4103 VARCHAR2(3), a4104 VARCHAR2(10), a4105 VARCHAR2(200), a4199 VARCHAR2(200),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a41sql);
				stmt.execute("ALTER TABLE a41"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a53sql = "create table A53"
						+ tableExt + " AS SELECT * FROM A53 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a5300 VARCHAR2(120), a5304 VARCHAR2(400), a5315 VARCHAR2(400), a5317 VARCHAR2(400), a5319 VARCHAR2(400), a5321 VARCHAR2(8), a5323 VARCHAR2(8), a5327 VARCHAR2(36), a5399 VARCHAR2(60), updated VARCHAR2(1),ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a53sql);
				stmt.execute("ALTER TABLE a53"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a57sql = "create table A57"
						+ tableExt + " AS SELECT * FROM A57 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a5714 VARCHAR2(100), updated VARCHAR2(1), photodata BLOB, photoname VARCHAR2(400), photstype VARCHAR2(200), photopath VARCHAR2(200), picstatus VARCHAR2(1),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a57sql);
				stmt.execute("ALTER TABLE a57"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a60sql = "create table A60"
						+ tableExt + " AS SELECT * FROM A60 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a6001 VARCHAR2(1), a6002 VARCHAR2(8), a6003 VARCHAR2(2), a6004 VARCHAR2(40), a6005 VARCHAR2(2), a6006 VARCHAR2(40), a6007 VARCHAR2(4), a6008 NUMBER(2), a6009 VARCHAR2(2), a6010 VARCHAR2(1), a6011 VARCHAR2(1), a6012 VARCHAR2(1), a6013 VARCHAR2(1), a6014 VARCHAR2(1), a6015 NUMBER(2), a6016 VARCHAR2(1), a6017 NUMBER(2) ,ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a60sql);
				stmt.execute("ALTER TABLE a60"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a61sql = "create table A61"
						+ tableExt + " AS SELECT * FROM A61 WHERE 1=2";
						//+ "( a0000  VARCHAR2(120), a2970  VARCHAR2(1), a2970a VARCHAR2(1), a2970b VARCHAR2(200), a6104  VARCHAR2(8), a2970c NUMBER(2), a6107  VARCHAR2(2), a6108  VARCHAR2(40), a6109  VARCHAR2(2), a6110  VARCHAR2(40), a6111  VARCHAR2(4), a6112  VARCHAR2(1), a6113  VARCHAR2(1), a6114  NUMBER(2), a6115  VARCHAR2(1), a6116  NUMBER(2),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a61sql);
				stmt.execute("ALTER TABLE a61"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a62sql = "create table A62"
						+ tableExt + " AS SELECT * FROM A62 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a6203 VARCHAR2(1), a6204 VARCHAR2(1), a6205 VARCHAR2(8), a2950 VARCHAR2(1), a6202 VARCHAR2(1),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a62sql);
				stmt.execute("ALTER TABLE a62"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a63sql = "create table A63"
						+ tableExt + " AS SELECT * FROM A63 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a2951 VARCHAR2(2), a6302 VARCHAR2(2), a6303 VARCHAR2(2), a6304 VARCHAR2(2), a6305 VARCHAR2(2), a6306 VARCHAR2(8), a6307 VARCHAR2(2), a6308 VARCHAR2(2), a6309 VARCHAR2(2), a6310 VARCHAR2(20),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a63sql);
				stmt.execute("ALTER TABLE a63"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a64sql = "create table A64"
						+ tableExt + " AS SELECT * FROM A64 WHERE 1=2";
						//+ "( a0000   VARCHAR2(120), a6401   VARCHAR2(50), a6402   VARCHAR2(3), a6403   VARCHAR2(3), a6404   VARCHAR2(3), a6405   VARCHAR2(3), a6406   VARCHAR2(3), a6407   VARCHAR2(3), a6408   VARCHAR2(3), a6400   VARCHAR2(60), a64type VARCHAR2(4),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a64sql);
				stmt.execute("ALTER TABLE a64"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				// 套改职级
				String a65sql = "create table A65"
						+ tableExt + " AS SELECT * FROM A65 WHERE 1=2";
						//+ "( a6500 varchar2(120),a0000 varchar2(120),a6501 varchar2(8),a6502 varchar2(8),a6503 varchar2(8),a6504 varchar2(8),a6505 NUMBER(8),a6506 varchar2(8),a6507 varchar2(8),a6508 varchar2(200),tgpc varchar2(8),a0200 varchar2(120),a6509 varchar2(10),a6510 NUMBER(8),a6511 varchar2(255),a6512 varchar2(8),a6513 varchar2(20),a6514 varchar2(8),a6511a varchar2(255),a6515 varchar2(8),a6516 varchar2(8),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a65sql);
				stmt.execute("ALTER TABLE a65"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String b01sql = "create table B01"
						+ tableExt + " AS SELECT * FROM B01 WHERE 1=2";
						//+ "( b0101 VARCHAR2(200), b0104 VARCHAR2(200), b0107 VARCHAR2(200), b0111 VARCHAR2(199), b0114 VARCHAR2(68), b0117 VARCHAR2(6), b0121 VARCHAR2(199), b0124 VARCHAR2(2), b0127 VARCHAR2(4), b0131 VARCHAR2(4), b0140 VARCHAR2(68), b0141 VARCHAR2(68), b0142 NUMBER, b0143 VARCHAR2(8), b0150 NUMBER, b0180 VARCHAR2(500), b0183 NUMBER, b0185 NUMBER, b0188 NUMBER, b0189 NUMBER, b0190 NUMBER, b0191 VARCHAR2(8), b0191a NUMBER, b0192 NUMBER, b0193 NUMBER, b0194 VARCHAR2(1), b01trans    NUMBER, b01ip VARCHAR2(32), b0227 NUMBER, b0232 NUMBER, b0233 NUMBER, sortid NUMBER, used  VARCHAR2(1), updated     VARCHAR2(1), create_user VARCHAR2(200), create_date DATE, update_user VARCHAR2(200), update_date DATE, status VARCHAR2(1), b0238 VARCHAR2(8), b0239 VARCHAR2(24), b0234 NUMBER, b0235 NUMBER, b0236 NUMBER, b0101new VARCHAR2(200),ERROR_INFO  VARCHAR2(200),psnB0111  VARCHAR2(200) ) ";
				stmt.execute(b01sql);
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a05sql = "create table A05"
						+ tableExt + " AS SELECT * FROM A05 WHERE 1=2";
						//+ "(a0000  VARCHAR2(120) ,a0500  VARCHAR2(120) ,a0531  VARCHAR2(1),a0501b VARCHAR2(6),a0504  VARCHAR2(8),a0511  VARCHAR2(260),a0517  VARCHAR2(8),a0524  VARCHAR2(1),a0525  VARCHAR2(1),a0526  VARCHAR2(1),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(a05sql);
				stmt.execute("ALTER TABLE a05"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A68sql = "create table A68"
						+ tableExt + " AS SELECT * FROM A68 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120) ,a6800 VARCHAR2(120) ,a6801 VARCHAR2(8),a6802 VARCHAR2(2),a6803 VARCHAR2(8),a6804 VARCHAR2(2),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(A68sql);
				stmt.execute("ALTER TABLE A68"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A69sql = "create table A69"
						+ tableExt + " AS SELECT * FROM A69 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120),a6900 VARCHAR2(120),a6901 VARCHAR2(2),a6902 VARCHAR2(2),a6903 VARCHAR2(8),a6904 VARCHAR2(2),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(A69sql);
				stmt.execute("ALTER TABLE A69"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A71sql = "create table A71"
						+ tableExt + " AS SELECT * FROM A71 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120), a7100 VARCHAR2(120),a7101 VARCHAR2(2000),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(A71sql);
				stmt.execute("ALTER TABLE A71"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A99Z1sql = "create table A99Z1"
						+ tableExt + " AS SELECT * FROM A99Z1 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120),a99Z100 VARCHAR2(120),a99Z101 VARCHAR2(1),a99Z102 VARCHAR2(8),a99Z103 VARCHAR2(1),A99Z104 VARCHAR2(8))";
				stmt.execute(A99Z1sql);
				stmt.execute("ALTER TABLE A99Z1"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String b01sql = "create table B01"+tableExt+
				 * "(b0101  VARCHAR2(200),b0104  VARCHAR2(200),b0107  VARCHAR2(200),b0111  VARCHAR2(199) not null,b0114  VARCHAR2(68),b0117  VARCHAR2(8),b0121  VARCHAR2(199),b0124  VARCHAR2(8),b0127  VARCHAR2(8),b0131  VARCHAR2(8),b0140  VARCHAR2(68),b0141  VARCHAR2(68),b0142  NUMBER,b0143  VARCHAR2(8) default '0',b0150  NUMBER,b0180  VARCHAR2(500),b0191  VARCHAR2(10),b0194  VARCHAR2(1),b01trans NUMBER default 1,b01ip  VARCHAR2(32),b0227  NUMBER,b0232  NUMBER,b0233  NUMBER,sortid NUMBER,used VARCHAR2(1),UPDATED  VARCHAR2(1) default '0',create_user  VARCHAR2(200),CREATE_DATE  DATE default SYSDATE,UPDATE_user  VARCHAR2(200),UPDATE_DATE  DATE,status VARCHAR2(1) default '1',b0238  VARCHAR2(8),b0239  VARCHAR2(200),b0234  NUMBER,ERROR_INFO  VARCHAR2(200),psnB0111  VARCHAR2(200))"
				 * ; stmt.execute(b01sql);
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				rs = stmt
						.executeQuery("select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH from user_tab_cols t where t.TABLE_NAME='B01_EXT'");
				if (rs != null) {
					String b01extsql = "create table B_E" + tableExt + "(";
					while (rs.next()) {
						b01extsql += rs.getString(1) + " " + rs.getString(2)
								+ "(" + rs.getString(3) + "),";
					}
					b01extsql = b01extsql.substring(0, b01extsql.length() - 1);
					b01extsql += ")";
					stmt.execute(b01extsql);
				}
				rs1 = stmt
						.executeQuery("select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH from user_tab_cols t where t.TABLE_NAME='INFO_EXTEND'");
				if (rs1 != null) {
					String infextsql = "create table I_E" + tableExt + "(";
					while (rs1.next()) {
						infextsql += rs1.getString(1) + " " + rs1.getString(2)
								+ "(" + rs1.getString(3) + "),";
					}
					infextsql = infextsql.substring(0, infextsql.length() - 1);
					infextsql += ")";
					stmt.execute(infextsql);
				}

			} else {
				// 以下代码注释的是字段没有修改之前的
				String a01sql = " create table A01"
						+ tableExt + " AS SELECT * FROM A01 WHERE 1=2";
						//+ " ( a0000 varchar(120), a0101 varchar(36), a0102 varchar(2500), a0104 varchar(2), a0107 varchar(8), a0111a varchar(200), a0114a varchar(200), a0115a varchar(120), a0117 varchar(2), a0128 varchar(120), a0134 varchar(8), a0140 varchar(100), a0141 varchar(8), a0144 varchar(8), a3921  varchar(8), a3927 varchar(8), a0160 varchar(8), a0163 varchar(2), a0165 varchar(8), a0184 varchar(18), a0187a varchar(120), a0192 varchar(2000), a0192a varchar(2000), a0221 varchar(5), a0288 varchar(8), a0192d varchar(80), a0192c varchar(8), a0196 varchar(120), a0197 varchar(1), a0195 varchar(68), a1701 LONGTEXT, a14z101 varchar(2000), a15z101 varchar(2000), a0120 varchar(8), a0121 varchar(8), a2949 varchar(8), a0122 varchar(8), a0104a varchar(60), a0111 varchar(8), a0114 varchar(8), a0117a varchar(60), a0128b varchar(120), a0141d varchar(100), a0144b varchar(8), a0144c varchar(8), a0148  varchar(20), a0148c varchar(8), a0149  varchar(80), a0151  varchar(1), a0153  varchar(1), a0157  varchar(120), a0158  varchar(5), a0159  varchar(50), a015a  varchar(2), a0161  varchar(500), a0162  varchar(8), a0180  varchar(1500), a0191  varchar(1), a0192b varchar(2000), a0193  varchar(5), a0194u varchar(8), a0198  varchar(200), a0199  varchar(1), a01k01 varchar(1), a01k02 varchar(8), cbdresult varchar(10), cbdw  varchar(100), isvalid varchar(20), nl varchar(4), nmzw   varchar(100), nrzw  varchar(100), orgid  varchar(500), qrzxl varchar(60), qrzxlxx varchar(120), qrzxw varchar(60), qrzxwxx varchar(120), rmly   varchar(100), status varchar(1), tbr varchar(36), tbrjg  varchar(80), userlog varchar(20), xgr  varchar(36), zzxl varchar(60), zzxlxx varchar(120), zzxw varchar(60), zzxwxx varchar(120), a0155 DATEtime, age bigint(4), jsnlsj DATEtime, resultsortid bigint(4), tbsj DATEtime, xgsj DATEtime, sortid bigint(16), a0194  bigint(8), a0192e varchar(80),a0192f VARCHAR(200), a0000new varchar(120),ERROR_INFO  varchar(200),TORGID varchar(200),TORDER varchar(8),ZGXL varchar(60),ZGXLXX varchar(120),ZGXW varchar(60),ZGXWXX varchar(120),TCSJSHOW VARCHAR(16),TCFSSHOW VARCHAR(40),fsj VARCHAR(1) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a01sql.toUpperCase());
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a02sql = " create table A02"
						+ tableExt + " AS SELECT * FROM A02 WHERE 1=2";
						//+ "( a0000 varchar(120), a0200 varchar(120), a0201a varchar(200),a0201a_all VARCHAR(200), a0201b varchar(199), a0201d varchar(1), a0201e varchar(1), a0215a varchar(100), a0219 varchar(1), a0223 varchar(8), a0225 varchar(8), a0243 varchar(8), a0245 varchar(100), a0247 varchar(2), a0251b varchar(1), a0255 varchar(1), a0265 varchar(8), a0267 varchar(24), a0272 varchar(100), a0281 varchar(8), a0221t bigint(8), b0238 varchar(200), b0239 varchar(200), a0221a varchar(8), wage_used bigint, updated   varchar(1), a4907 varchar(8), a4904 varchar(8), a4901 varchar(8), a0299 varchar(8), a0295 varchar(8), a0289 varchar(8), a0288 varchar(8), a0284 varchar(8), a0277 varchar(1), a0271 varchar(8), a0259 varchar(8), a0256c bigint, a0256b bigint, a0256a varchar(8), a0256 varchar(8), a0251 varchar(8), a0229 varchar(120), a0222 varchar(20), a0221w varchar(8), a0221 varchar(8), a0219w varchar(8), a0216a varchar(200), a0215b varchar(80), a0209 varchar(8), a0207 varchar(8), a0204 varchar(68), a0201c varchar(200), a0201 varchar(199),ERROR_INFO  varchar(200),a0279 VARCHAR(1) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a02sql.toUpperCase());
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a06sql = " create table A06"
						+ tableExt + " AS SELECT * FROM A06 WHERE 1=2";
						//+ "( a0600 varchar(120), a0000 varchar(120), a0601 varchar(8), a0602 varchar(60), a0604 varchar(8), a0607 varchar(8), a0611 varchar(100), a0614 varchar(1), a0699 varchar(8), updated varchar(1), sortid bigint,ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a06sql.toUpperCase());
				stmt.execute("ALTER TABLE a06"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a08sql = " create table A08"
						+ tableExt + " AS SELECT * FROM A08 WHERE 1=2";
						//+ "( a0000 varchar(120), a0800 varchar(120), a0801a varchar(120), a0801b varchar(8), a0901a varchar(40), a0901b varchar(8), a0804 varchar(8), a0807 varchar(8), a0904 varchar(8), a0814 varchar(120), a0824 varchar(40), a0827 varchar(8), a0837 varchar(1), a0811 varchar(8), a0898 varchar(1), a0831 varchar(1), a0832 varchar(1), a0834 varchar(1), a0835 varchar(1), a0838 varchar(1), a0839 varchar(1), a0899 varchar(8), updated  varchar(1), sortid bigint, wage_used bigint ,ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a08sql.toUpperCase());
				stmt.execute("ALTER TABLE a08"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a11sql = " create table A11"
						+ tableExt + " AS SELECT * FROM A11 WHERE 1=2";
						//+ "( a0000 varchar(120) , a1100 varchar(120), a1101 varchar(8), a1104 varchar(8), a1107 varchar(8), a1107a bigint , a1107b bigint, a1111 varchar(8), a1114 varchar(120), a1121a varchar(120), a1127 varchar(8), a1131 varchar(120), a1134 varchar(1), a1151 varchar(1), updated varchar(1), a1108 bigint, a1107c bigint,ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a11sql.toUpperCase());
				stmt.execute("ALTER TABLE a11"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a14sql = " create table A14"
						+ tableExt + " AS SELECT * FROM A14 WHERE 1=2";
						//+ "( a0000 varchar(120), a1400 varchar(120), a1404a varchar(40), a1404b varchar(8), a1407 varchar(8), a1411a varchar(60), a1414 varchar(8), a1415 varchar(8), a1424 varchar(8), a1428 varchar(8), updated varchar(1), sortid bigint,ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a14sql.toUpperCase());
				stmt.execute("ALTER TABLE a14"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a15sql = " create table A15"
						+ tableExt + " AS SELECT * FROM A15 WHERE 1=2";
						//+ "( a0000 varchar(120), a1500 varchar(120), a1517 varchar(4), a1521 varchar(4), a1527 varchar(8), updated varchar(1) ,ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a15sql.toUpperCase());
				stmt.execute("ALTER TABLE a15"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a29sql = " create table A29"
						+ tableExt + " AS SELECT * FROM A29 WHERE 1=2";
						//+ "( a0000 varchar(120), a2907 varchar(8), a2911 varchar(3), a2921a varchar(200), a2941 varchar(40), a2944 varchar(40), a2921d varchar(8), a2921c varchar(8), a2947b bigint(4), a2921b varchar(8), a2947a bigint(4), updated varchar(1), a2949 varchar(8), a2947 varchar(8),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a29sql.toUpperCase());
				stmt.execute("ALTER TABLE a29"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a30sql = "create table A30"
						+ tableExt + " AS SELECT * FROM A30 WHERE 1=2";
						//+ "( a0000 varchar(120), a3001 varchar(8), a3004 varchar(8), a3117a varchar(60), a3101 varchar(1), a3137 varchar(24), a3034 varchar(1000), updated varchar(1), a3007a varchar(100),a3038 varchar(100), ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a30sql.toUpperCase());
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a31sql = "create table A31"
						+ tableExt + " AS SELECT * FROM A31 WHERE 1=2";
						//+ "( a0000 varchar(120), a3101 varchar(8), a3104 varchar(8), a3107 varchar(8), a3117a varchar(60), a3118 varchar(40), a3137 varchar(24), a3138 varchar(60), updated varchar(1), a3110 varchar(80), a3109 varchar(80), a3108 varchar(80),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a31sql.toUpperCase());
				stmt.execute("ALTER TABLE a31"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a36sql = "create table A36"
						+ tableExt + " AS SELECT * FROM A36 WHERE 1=2";
						//+ "( a0000 varchar(120), a3600 varchar(120), a3601 varchar(36), a3604a varchar(10), a3607 varchar(8), a3611 varchar(200), a3627 varchar(100), sortid bigint, updated varchar(1), a3684 varchar(18),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a36sql.toUpperCase());
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a37sql = "create table A37"
						+ tableExt + " AS SELECT * FROM A37 WHERE 1=2";
						//+ "( a0000 varchar(120), a3701 varchar(120), a3707a varchar(20), a3707c varchar(20), a3707e varchar(20), a3707b varchar(20), a3708 varchar(60), a3711 varchar(120), a3714 varchar(6), updated varchar(1),ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a37sql.toUpperCase());
				stmt.execute("ALTER TABLE a37"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a41sql = "create table A41"
						+ tableExt + " AS SELECT * FROM A41 WHERE 1=2";
						//+ "( a4100 varchar(120), a0000 varchar(120), a1100 varchar(120), a4101 varchar(10), a4102 varchar(10), a4103 varchar(3), a4104 varchar(10), a4105 varchar(200), a4199 varchar(200),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a41sql.toUpperCase());
				stmt.execute("ALTER TABLE a41"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a53sql = "create table A53"
						+ tableExt + " AS SELECT * FROM A53 WHERE 1=2";
						//+ "( a0000 varchar(120), a5300 varchar(120), a5304 varchar(400), a5315 varchar(400), a5317 varchar(400), a5319 varchar(400), a5321 varchar(8), a5323 varchar(8), a5327 varchar(36), a5399 varchar(60), updated varchar(1),ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a53sql.toUpperCase());
				stmt.execute("ALTER TABLE a53"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a57sql = "create table A57"
						+ tableExt + " AS SELECT * FROM A57 WHERE 1=2";
						//+ "( a0000 varchar(120), a5714 varchar(100), updated varchar(1), photodata BLOB, photoname varchar(400), photstype varchar(200), photopath varchar(200), picstatus varchar(1),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a57sql.toUpperCase());
				stmt.execute("ALTER TABLE a57"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a60sql = "create table A60"
						+ tableExt + " AS SELECT * FROM A60 WHERE 1=2";
						//+ "( a0000 varchar(120), a6001 varchar(1), a6002 varchar(8), a6003 varchar(2), a6004 varchar(40), a6005 varchar(2), a6006 varchar(40), a6007 varchar(4), a6008 bigint(2), a6009 varchar(2), a6010 varchar(1), a6011 varchar(1), a6012 varchar(1), a6013 varchar(1), a6014 varchar(1), a6015 bigint(2), a6016 varchar(1), a6017 bigint(2) ,ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a60sql.toUpperCase());
				stmt.execute("ALTER TABLE a60"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a61sql = "create table A61"
						+ tableExt + " AS SELECT * FROM A61 WHERE 1=2";
						//+ "( a0000  varchar(120), a2970  varchar(1), a2970a varchar(1), a2970b varchar(200), a6104  varchar(8), a2970c bigint(2), a6107  varchar(2), a6108  varchar(40), a6109  varchar(2), a6110  varchar(40), a6111  varchar(4), a6112  varchar(1), a6113  varchar(1), a6114  bigint(2), a6115  varchar(1), a6116  bigint(2),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a61sql.toUpperCase());
				stmt.execute("ALTER TABLE a61"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a62sql = "create table A62"
						+ tableExt + " AS SELECT * FROM A62 WHERE 1=2";
						//+ "( a0000 varchar(120), a6203 varchar(1), a6204 varchar(1), a6205 varchar(8), a2950 varchar(1), a6202 varchar(1),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a62sql.toUpperCase());
				stmt.execute("ALTER TABLE a62"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a63sql = "create table A63"
						+ tableExt + " AS SELECT * FROM A63 WHERE 1=2";
						//+ "( a0000 varchar(120), a2951 varchar(2), a6302 varchar(2), a6303 varchar(2), a6304 varchar(2), a6305 varchar(2), a6306 varchar(8), a6307 varchar(2), a6308 varchar(2), a6309 varchar(2), a6310 varchar(20),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a63sql.toUpperCase());
				stmt.execute("ALTER TABLE a63"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a64sql = "create table A64"
						+ tableExt + " AS SELECT * FROM A64 WHERE 1=2";
						//+ "( a0000   varchar(120), a6401   varchar(50), a6402   varchar(3), a6403   varchar(3), a6404   varchar(3), a6405   varchar(3), a6406   varchar(3), a6407   varchar(3), a6408   varchar(3), a6400   varchar(60), a64type varchar(4),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a64sql.toUpperCase());
				stmt.execute("ALTER TABLE a64"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				// 套改职级
				String a65sql = "create table A65"
						+ tableExt + " AS SELECT * FROM A65 WHERE 1=2";
						//+ "( a6500 varchar(120),a0000 varchar(120),a6501 varchar(8),a6502 varchar(8),a6503 varchar(8),a6504 varchar(8),a6505 int(8),a6506 varchar(8),a6507 varchar(8),a6508 varchar(200),tgpc varchar(8),a0200 varchar(120),a6509 varchar(10),a6510 int(8),a6511 varchar(255),a6512 varchar(8),a6513 varchar(20),a6514 varchar(8),a6511a varchar(255),a6515 varchar(8),a6516 varchar(8),ERROR_INFO  VARCHAR(200) ) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a65sql);
				stmt.execute("ALTER TABLE a65"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String b01sql = "create table B01"
						+ tableExt + " AS SELECT * FROM B01 WHERE 1=2";
						//+ "( b0101 varchar(200), b0104 varchar(200), b0107 varchar(200), b0111 varchar(199), b0114 varchar(68), b0117 varchar(6), b0121 varchar(199), b0124 varchar(2), b0127 varchar(4), b0131 varchar(4), b0140 varchar(68), b0141 varchar(68), b0142 bigint, b0143 varchar(8), b0150 bigint, b0180 varchar(500), b0183 bigint, b0185 bigint, b0188 bigint, b0189 bigint, b0190 bigint, b0191 varchar(8), b0191a bigint, b0192 bigint, b0193 bigint, b0194 varchar(1), b01trans    bigint, b01ip varchar(32), b0227 bigint, b0232 bigint, b0233 bigint, sortid bigint, used  varchar(1), updated     varchar(1), create_user varchar(200), create_DATE DATE, upDATE_user varchar(200), upDATE_DATE DATE, status varchar(1), b0238 varchar(8), b0239 varchar(24), b0234 bigint, b0235 bigint, b0236 bigint, b0101new varchar(200),ERROR_INFO  varchar(200),psnB0111  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(b01sql.toUpperCase());
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a05sql = "create table A05"
						+ tableExt + " AS SELECT * FROM A05 WHERE 1=2";
						//+ "(a0000  varchar(120) ,a0500  varchar(120) ,a0531  varchar(1),a0501b varchar(6),a0504  varchar(8),a0511  varchar(260),a0517  varchar(8),a0524  varchar(1),a0525  varchar(1),a0526  varchar(1),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a05sql.toUpperCase());
				stmt.execute("ALTER TABLE a05"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A68sql = "create table A68"
						+ tableExt + " AS SELECT * FROM A68 WHERE 1=2";
						//+ "(a0000 varchar(120) ,a6800 varchar(120) ,a6801 varchar(8),a6802 varchar(2),a6803 varchar(8),a6804 varchar(2),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A68sql.toUpperCase());
				stmt.execute("ALTER TABLE A68"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A69sql = "create table A69"
						+ tableExt + " AS SELECT * FROM A69 WHERE 1=2";
						//+ "(a0000 varchar(120),a6900 varchar(120),a6901 varchar(2),a6902 varchar(2),a6903 varchar(8),a6904 varchar(2),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A69sql.toUpperCase());
				stmt.execute("ALTER TABLE A69"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A71sql = "create table A71"
						+ tableExt + " AS SELECT * FROM A71 WHERE 1=2";
						//+ "(a0000 varchar(120), a7100 varchar(120),a7101 varchar(2000),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A71sql.toUpperCase());
				stmt.execute("ALTER TABLE A71"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A99Z1sql = "create table A99Z1"
						+ tableExt + " AS SELECT * FROM A99Z1 WHERE 1=2";
						//+ "(a0000 VARCHAR(120),a99Z100 VARCHAR(120),a99Z101 VARCHAR(1),a99Z102 VARCHAR(8),a99Z103 VARCHAR(1),A99Z104 VARCHAR(8)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A99Z1sql.toUpperCase());
				stmt.execute("ALTER TABLE A99Z1"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");
				/*
				 * String b01sql = "create table B01"+tableExt+
				 * "(b0101  VARCHAR2(200),b0104  VARCHAR2(200),b0107  VARCHAR2(200),b0111  VARCHAR2(199) not null,b0114  VARCHAR2(68),b0117  VARCHAR2(8),b0121  VARCHAR2(199),b0124  VARCHAR2(8),b0127  VARCHAR2(8),b0131  VARCHAR2(8),b0140  VARCHAR2(68),b0141  VARCHAR2(68),b0142  NUMBER,b0143  VARCHAR2(8) default '0',b0150  NUMBER,b0180  VARCHAR2(500),b0191  VARCHAR2(10),b0194  VARCHAR2(1),b01trans NUMBER default 1,b01ip  VARCHAR2(32),b0227  NUMBER,b0232  NUMBER,b0233  NUMBER,sortid NUMBER,used VARCHAR2(1),UPDATED  VARCHAR2(1) default '0',create_user  VARCHAR2(200),CREATE_DATE  DATE default SYSDATE,UPDATE_user  VARCHAR2(200),UPDATE_DATE  DATE,status VARCHAR2(1) default '1',b0238  VARCHAR2(8),b0239  VARCHAR2(200),b0234  NUMBER,ERROR_INFO  VARCHAR2(200),psnB0111  VARCHAR2(200))"
				 * ; stmt.execute(b01sql);
				 */

				rs = stmt
						.executeQuery("select a.COLUMN_NAME,a.COLUMN_TYPE from information_schema.columns a where table_name='B01_EXT'  and a.TABLE_SCHEMA=(select DATABASE())");
				if (rs != null) {
					String b01extsql = "create table `B_E" + tableExt + "`(";
					while (rs.next()) {
						b01extsql += "`" + rs.getString(1) + "` "
								+ rs.getString(2) + ",";
					}

					b01extsql = b01extsql.substring(0, b01extsql.length() - 1);
					b01extsql += ")ENGINE=MyISAM DEFAULT CHARSET=utf8";
					stmt.execute(b01extsql.toUpperCase());
				}
				// 加上了distinct 去重2017/05/10
				rs1 = stmt
						.executeQuery("select distinct a.COLUMN_NAME,a.COLUMN_TYPE from information_schema.columns a where table_name='INFO_EXTEND' and a.TABLE_SCHEMA=(select DATABASE())");
				if (rs1 != null) {
					String infextsql = "create table `I_E" + tableExt + "`(";
					while (rs1.next()) {
						infextsql += "`" + rs1.getString(1) + "` "
								+ rs1.getString(2) + ",";
					}
					infextsql = infextsql.substring(0, infextsql.length() - 1);
					infextsql += ")ENGINE=MyISAM DEFAULT CHARSET=utf8";
					stmt.execute(infextsql.toUpperCase());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (rs1 != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void createTempTable(String tableExt) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			conn = sess.connection();
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			if (DBUtil.getDBType().equals(DBType.ORACLE)) {
				// 以下代码注释的是字段没有修改之前的
				String a01sql = " create table A01"
						+ tableExt + " AS SELECT * FROM A01 WHERE 1=2";
						//+ " ( a0000 VARCHAR2(120), a0101 VARCHAR2(36), a0102 VARCHAR2(2500), a0104 VARCHAR2(2), a0107 VARCHAR2(8), a0111a VARCHAR2(200), a0114a VARCHAR2(200), a0115a VARCHAR2(120), a0117 VARCHAR2(2), a0128 VARCHAR2(120), a0134 VARCHAR2(8), a0140 VARCHAR2(100), a0141 VARCHAR2(8), a0144 VARCHAR2(8), a3921  VARCHAR2(8), a3927 VARCHAR2(8), a0160 VARCHAR2(8), a0163 VARCHAR2(2), a0165 VARCHAR2(8), a0184 VARCHAR2(18), a0187a VARCHAR2(120), a0192 VARCHAR2(2000), a0192a VARCHAR2(2000), a0221 VARCHAR2(5), a0288 VARCHAR2(8), a0192d VARCHAR2(80), a0192c VARCHAR2(8), a0196 VARCHAR2(120), a0197 VARCHAR2(1), a0195 VARCHAR2(68), a1701 CLOB, a14z101 VARCHAR2(2000), a15z101 VARCHAR2(2000), a0120 VARCHAR2(8), a0121 VARCHAR2(8), a2949 VARCHAR2(8), a0122 VARCHAR2(8), a0104a VARCHAR2(60), a0111 VARCHAR2(8), a0114 VARCHAR2(8), a0117a VARCHAR2(60), a0128b VARCHAR2(120), a0141d VARCHAR2(100), a0144b VARCHAR2(8), a0144c VARCHAR2(8), a0148  VARCHAR2(20), a0148c VARCHAR2(8), a0149  VARCHAR2(80), a0151  VARCHAR2(1), a0153  VARCHAR2(1), a0157  VARCHAR2(120), a0158  VARCHAR2(5), a0159  VARCHAR2(50), a015a  VARCHAR2(2), a0161  VARCHAR2(500), a0162  VARCHAR2(8), a0180  VARCHAR2(1500), a0191  VARCHAR2(1), a0192b VARCHAR2(2000), a0193  VARCHAR2(5), a0194u VARCHAR2(8), a0198  VARCHAR2(200), a0199  VARCHAR2(1), a01k01 VARCHAR2(1), a01k02 VARCHAR2(8), cbdresult VARCHAR2(10), cbdw  VARCHAR2(100), isvalid VARCHAR2(20), nl VARCHAR2(4), nmzw   VARCHAR2(100), nrzw  VARCHAR2(100), orgid  VARCHAR2(500), qrzxl VARCHAR2(60), qrzxlxx VARCHAR2(120), qrzxw VARCHAR2(60), qrzxwxx VARCHAR2(120), rmly   VARCHAR2(100), status VARCHAR2(1), tbr VARCHAR2(36), tbrjg  VARCHAR2(80), userlog VARCHAR2(20), xgr  VARCHAR2(36), zzxl VARCHAR2(60), zzxlxx VARCHAR2(120), zzxw VARCHAR2(60), zzxwxx VARCHAR2(120), a0155 DATE, age NUMBER(4), jsnlsj DATE, resultsortid NUMBER(4), tbsj DATE, xgsj DATE, sortid NUMBER(16), a0194  NUMBER(8), a0192e VARCHAR2(80),a0192f VARCHAR2(200), a0000new VARCHAR2(120),ERROR_INFO  VARCHAR2(200),TORGID VARCHAR2(200),TORDER VARCHAR2(8),ZGXL VARCHAR2(60),ZGXLXX VARCHAR2(120),ZGXW VARCHAR2(60),ZGXWXX VARCHAR2(120),TCSJSHOW VARCHAR2(16),TCFSSHOW VARCHAR2(40),fsj VARCHAR2(1) ) ";
				stmt.execute(a01sql);
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a01sql = "create table A01"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a0101  VARCHAR2(36),a0104  VARCHAR2(2),a0104a  VARCHAR2(60),a0107 VARCHAR2(8),a0111  VARCHAR2(8),a0111a  VARCHAR2(200),a0114 VARCHAR2(8),a0114a  VARCHAR2(200),a0115a  VARCHAR2(120),a0117 VARCHAR2(2),a0117a  VARCHAR2(60),a0134 VARCHAR2(8),a0144 VARCHAR2(8),a0144b  VARCHAR2(8),a0144c  VARCHAR2(8),a0148 VARCHAR2(20),a0149 VARCHAR2(80),a0151 VARCHAR2(1),a0153 VARCHAR2(1),a0155 DATE,a0157 VARCHAR2(120),a0158 VARCHAR2(5),a0159 VARCHAR2(50),a015a VARCHAR2(2),a0160 VARCHAR2(8),a0161 VARCHAR2(500),a0162 VARCHAR2(8),a0163 VARCHAR2(2),a0165 VARCHAR2(8),a0184 VARCHAR2(18),a0191 VARCHAR2(1),a0192 VARCHAR2(2000),a0192a  VARCHAR2(2000),a0221  VARCHAR2(5),a0288  VARCHAR2(8),a0192b  VARCHAR2(2000),a0193 VARCHAR2(5),a0195 VARCHAR2(68),a0196 VARCHAR2(120),a0197 VARCHAR2(1),a0198 VARCHAR2(200),a0199 VARCHAR2(1),a01k01  VARCHAR2(1),a01k02  VARCHAR2(8),age NUMBER(4),cbdw  VARCHAR2(100),isvalid VARCHAR2(20),jsnlsj  DATE,nl  VARCHAR2(4),nmzw  VARCHAR2(100),nrzw  VARCHAR2(100),qrzxl VARCHAR2(60),qrzxlxx VARCHAR2(120),qrzxw VARCHAR2(60),qrzxwxx VARCHAR2(120),resultsortid  NUMBER(4),rmly  VARCHAR2(100),tbr VARCHAR2(36),tbsj  DATE,userlog VARCHAR2(20),xgr VARCHAR2(36),xgsj  DATE,zzxl  VARCHAR2(60),zzxlxx  VARCHAR2(120),zzxw  VARCHAR2(60),zzxwxx  VARCHAR2(120),a3927 VARCHAR2(8),a0102 VARCHAR2(2500),a0128b  VARCHAR2(120),a0128 VARCHAR2(120),a0140 VARCHAR2(100),a0187a  VARCHAR2(120),a0148c  VARCHAR2(8),a14z101 VARCHAR2(2000),a15z101 VARCHAR2(2000),a0141d  VARCHAR2(100),a0141 VARCHAR2(8),a3921 VARCHAR2(8),sortid  NUMBER(16),a0180 VARCHAR2(500),a0194 NUMBER(8),a0192d  VARCHAR2(80),a0192c  VARCHAR2(8),status  VARCHAR2(1) default '1',tbrjg VARCHAR2(80),a0120 VARCHAR2(8),a0121 VARCHAR2(8),a2949 VARCHAR2(8),a0122 VARCHAR2(8),a0194u  VARCHAR2(8),cbdresult VARCHAR2(10),orgid VARCHAR2(500),a1701 CLOB)"
				 * ; stmt.execute(a01sql);
				 */

				String a02sql = " create table A02"
						+ tableExt + " AS SELECT * FROM A02 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a0200 VARCHAR2(120), a0201a VARCHAR2(200),a0201a_all VARCHAR2(200), a0201b VARCHAR2(199), a0201d VARCHAR2(1), a0201e VARCHAR2(1), a0215a VARCHAR2(100), a0219 VARCHAR2(1), a0223 VARCHAR2(8), a0225 VARCHAR2(8), a0243 VARCHAR2(8), a0245 VARCHAR2(260), a0247 VARCHAR2(2), a0251b VARCHAR2(1), a0255 VARCHAR2(1), a0265 VARCHAR2(8), a0267 VARCHAR2(24), a0272 VARCHAR2(100), a0281 VARCHAR2(8), a0221t NUMBER(8), b0238 VARCHAR2(200), b0239 VARCHAR2(200), a0221a VARCHAR2(8), wage_used NUMBER, updated   VARCHAR2(1), a4907 VARCHAR2(8), a4904 VARCHAR2(8), a4901 VARCHAR2(8), a0299 VARCHAR2(8), a0295 VARCHAR2(8), a0289 VARCHAR2(8), a0288 VARCHAR2(8), a0284 VARCHAR2(8), a0277 VARCHAR2(1), a0271 VARCHAR2(8), a0259 VARCHAR2(8), a0256c NUMBER, a0256b NUMBER, a0256a VARCHAR2(8), a0256 VARCHAR2(8), a0251 VARCHAR2(8), a0229 VARCHAR2(120), a0222 VARCHAR2(20), a0221w VARCHAR2(8), a0221 VARCHAR2(8), a0219w VARCHAR2(8), a0216a VARCHAR2(200), a0215b VARCHAR2(80), a0209 VARCHAR2(8), a0207 VARCHAR2(8), a0204 VARCHAR2(68), a0201c VARCHAR2(200), a0201 VARCHAR2(199),ERROR_INFO  VARCHAR2(200), a0279 VARCHAR2(1) ) ";
				stmt.execute(a02sql);
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a02sql = "create table A02"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a0200  VARCHAR2(120) not null,a0201  VARCHAR2(199),a0201a VARCHAR2(1000),a0201b VARCHAR2(68),a0201c VARCHAR2(200),a0201d VARCHAR2(1),a0201e VARCHAR2(8),a0204  VARCHAR2(68),a0207  VARCHAR2(8),a0209  VARCHAR2(8),a0215a VARCHAR2(80),a0215b VARCHAR2(80),a0216a VARCHAR2(200),a0219  VARCHAR2(8),a0219w VARCHAR2(8),a0221w VARCHAR2(8),a0223  NUMBER,a0225  NUMBER,a0243  VARCHAR2(8),a0245  VARCHAR2(260),a0247  VARCHAR2(8),a0251b VARCHAR2(1),a0255  VARCHAR2(1),a0256  VARCHAR2(8),a0256a VARCHAR2(8),a0256b NUMBER,a0256c NUMBER,a0259  VARCHAR2(8),a0265  VARCHAR2(8),a0267  VARCHAR2(24),a0272  VARCHAR2(100),a0277  VARCHAR2(1),a0281  VARCHAR2(8),a0289  VARCHAR2(8),a0295  VARCHAR2(8),a0299  VARCHAR2(8),UPDATED  VARCHAR2(1) default '1',wage_used  NUMBER,b0239  VARCHAR2(200),b0238  VARCHAR2(200),ERROR_INFO  VARCHAR2(200))"
				 * ; stmt.execute(a02sql);
				 */

				String a06sql = " create table A06"
						+ tableExt + " AS SELECT * FROM A06 WHERE 1=2";
						//+ "( a0600 VARCHAR2(120), a0000 VARCHAR2(120), a0601 VARCHAR2(8), a0602 VARCHAR2(60), a0604 VARCHAR2(8), a0607 VARCHAR2(8), a0611 VARCHAR2(100), a0614 VARCHAR2(1), a0699 VARCHAR2(8), updated VARCHAR2(1), sortid NUMBER,ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a06sql);
				stmt.execute("ALTER TABLE a06"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a08sql = " create table A08"
						+ tableExt + " AS SELECT * FROM A08 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a0800 VARCHAR2(120), a0801a VARCHAR2(120), a0801b VARCHAR2(8), a0901a VARCHAR2(40), a0901b VARCHAR2(8), a0804 VARCHAR2(8), a0807 VARCHAR2(8), a0904 VARCHAR2(8), a0814 VARCHAR2(120), a0824 VARCHAR2(40), a0827 VARCHAR2(8), a0837 VARCHAR2(1), a0811 VARCHAR2(8), a0898 VARCHAR2(1), a0831 VARCHAR2(1), a0832 VARCHAR2(1), a0834 VARCHAR2(1), a0835 VARCHAR2(1), a0838 VARCHAR2(1), a0839 VARCHAR2(1), a0899 VARCHAR2(8), updated  VARCHAR2(1), sortid NUMBER, wage_used NUMBER ,ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a08sql);
				stmt.execute("ALTER TABLE a08"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a11sql = " create table A11"
						+ tableExt + " AS SELECT * FROM A11 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120) , a1100 VARCHAR2(120), a1101 VARCHAR2(8), a1104 VARCHAR2(8), a1107 VARCHAR2(8), a1107a NUMBER , a1107b NUMBER, a1111 VARCHAR2(8), a1114 VARCHAR2(120), a1121a VARCHAR2(120), a1127 VARCHAR2(8), a1131 VARCHAR2(120), a1134 VARCHAR2(1), a1151 VARCHAR2(1), updated VARCHAR2(1), a1108 NUMBER, a1107c NUMBER,ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a11sql);
				stmt.execute("ALTER TABLE a11"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a11sql = "create table A11"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a1100  VARCHAR2(120) not null,a1101  VARCHAR2(8),a1104  VARCHAR2(8),a1107  VARCHAR2(8),a1107a NUMBER default 0,a1107b NUMBER default 0,a1111  VARCHAR2(8),a1114  VARCHAR2(120),a1121a VARCHAR2(120),a1127  VARCHAR2(8),a1131  VARCHAR2(60),a1134  VARCHAR2(1),a1151  VARCHAR2(1),a1110  VARCHAR2(1),UPDATED  VARCHAR2(1) default '1',a1108  NUMBER,a1107c NUMBER)"
				 * ; stmt.execute(a11sql);
				stmt.execute("ALTER TABLE a11"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a14sql = " create table A14"
						+ tableExt + " AS SELECT * FROM A14 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a1400 VARCHAR2(120), a1404a VARCHAR2(40), a1404b VARCHAR2(8), a1407 VARCHAR2(8), a1411a VARCHAR2(60), a1414 VARCHAR2(8), a1415 VARCHAR2(8), a1424 VARCHAR2(8), a1428 VARCHAR2(8), updated VARCHAR2(1), sortid NUMBER,ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a14sql);
				stmt.execute("ALTER TABLE a14"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a15sql = " create table A15"
						+ tableExt + " AS SELECT * FROM A15 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a1500 VARCHAR2(120), a1517 VARCHAR2(4), a1521 VARCHAR2(4), a1527 VARCHAR2(8), updated VARCHAR2(1) ,ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a15sql);
				stmt.execute("ALTER TABLE a15"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a29sql = " create table A29"
						+ tableExt + " AS SELECT * FROM A29 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a2907 VARCHAR2(8), a2911 VARCHAR2(3), a2921a VARCHAR2(200), a2941 VARCHAR2(40), a2944 VARCHAR2(40), a2921d VARCHAR2(8), a2921c VARCHAR2(8), a2947b NUMBER(4), a2921b VARCHAR2(8), a2947a NUMBER(4), updated VARCHAR2(1), a2949 VARCHAR2(8), a2947 VARCHAR2(8),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a29sql);
				stmt.execute("ALTER TABLE a29"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a29sql = "create table A29"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a2907  VARCHAR2(8),a2911  VARCHAR2(8),a2921a VARCHAR2(60),a2941  VARCHAR2(40),a2944  VARCHAR2(40),a2949  VARCHAR2(8),UPDATED  VARCHAR2(1) default '1',a2921b VARCHAR2(8),a2947b NUMBER(4),a2921c VARCHAR2(8),a2921d VARCHAR2(8))"
				 * ; stmt.execute(a29sql);
				stmt.execute("ALTER TABLE a29"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a30sql = "create table A30"
						+ tableExt + " AS SELECT * FROM A30 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3001 VARCHAR2(8), a3004 VARCHAR2(8), a3117a VARCHAR2(60), a3101 VARCHAR2(1), a3137 VARCHAR2(24), a3034 VARCHAR2(1000), updated VARCHAR2(1), a3007a VARCHAR2(100),a3038 VARCHAR2(100), ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a30sql);
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a33sql = "create table A33"
						+ tableExt + " AS SELECT * FROM A33 WHERE 1=2";
				stmt.execute(a33sql);
				stmt.execute("ALTER TABLE a33"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a30sql = "create table A30"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a3001  VARCHAR2(8),a3004  VARCHAR2(8),a3034  VARCHAR2(500),UPDATED  VARCHAR2(1) default '1')"
				 * ; stmt.execute(a30sql);
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

			/*	String a31sql = "create table A31"
						+ tableExt + " AS SELECT * FROM A31 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3101 VARCHAR2(8), a3104 VARCHAR2(8), a3107 VARCHAR2(8), a3117a VARCHAR2(60), a3118 VARCHAR2(40), a3137 VARCHAR2(24), a3138 VARCHAR2(60), updated VARCHAR2(1), a3110 VARCHAR2(80), a3109 VARCHAR2(80), a3108 VARCHAR2(80),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a31sql);
				stmt.execute("ALTER TABLE a31"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");*/
				/*
				 * String a31sql = "create table A31"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a3101  VARCHAR2(8),a3117a VARCHAR2(60),a3137  VARCHAR2(24),a3138  VARCHAR2(60),UPDATED  VARCHAR2(1) default '1')"
				 * ; stmt.execute(a31sql);
				stmt.execute("ALTER TABLE a31"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a36sql = "create table A36"
						+ tableExt + " AS SELECT * FROM A36 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3600 VARCHAR2(120), a3601 VARCHAR2(36), a3604a VARCHAR2(10), a3607 VARCHAR2(8), a3611 VARCHAR2(200), a3627 VARCHAR2(100), sortid NUMBER, updated VARCHAR2(1), a3684 VARCHAR2(18),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a36sql);
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String a36sql = "create table A36"+tableExt+
				 * "(a0000  VARCHAR2(120) not null,a3600  VARCHAR2(120) not null,a3601  VARCHAR2(36),a3604a VARCHAR2(10),a3607  VARCHAR2(8),a3611  VARCHAR2(200),a3627  VARCHAR2(100),sortid NUMBER,UPDATED  VARCHAR2(1) default '1',a3684  VARCHAR2(18))"
				 * ; stmt.execute(a36sql);
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				String a37sql = "create table A37"
						+ tableExt + " AS SELECT * FROM A37 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a3701 VARCHAR2(120), a3707a VARCHAR2(20), a3707c VARCHAR2(20), a3707e VARCHAR2(20), a3707b VARCHAR2(20), a3708 VARCHAR2(60), a3711 VARCHAR2(120), a3714 VARCHAR2(6), updated VARCHAR2(1),ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a37sql);
				stmt.execute("ALTER TABLE a37"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a41sql = "create table A41"
						+ tableExt + " AS SELECT * FROM A41 WHERE 1=2";
						//+ "( a4100 VARCHAR2(120), a0000 VARCHAR2(120), a1100 VARCHAR2(120), a4101 VARCHAR2(10), a4102 VARCHAR2(10), a4103 VARCHAR2(3), a4104 VARCHAR2(10), a4105 VARCHAR2(200), a4199 VARCHAR2(200),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a41sql);
				stmt.execute("ALTER TABLE a41"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a53sql = "create table A53"
						+ tableExt + " AS SELECT * FROM A53 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a5300 VARCHAR2(120), a5304 VARCHAR2(400), a5315 VARCHAR2(400), a5317 VARCHAR2(400), a5319 VARCHAR2(400), a5321 VARCHAR2(8), a5323 VARCHAR2(8), a5327 VARCHAR2(36), a5399 VARCHAR2(60), updated VARCHAR2(1),ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a53sql);
				stmt.execute("ALTER TABLE a53"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a57sql = "create table A57"
						+ tableExt + " AS SELECT * FROM A57 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a5714 VARCHAR2(100), updated VARCHAR2(1), photodata BLOB, photoname VARCHAR2(400), photstype VARCHAR2(200), photopath VARCHAR2(200), picstatus VARCHAR2(1),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a57sql);
				stmt.execute("ALTER TABLE a57"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a60sql = "create table A60"
						+ tableExt + " AS SELECT * FROM A60 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a6001 VARCHAR2(1), a6002 VARCHAR2(8), a6003 VARCHAR2(2), a6004 VARCHAR2(40), a6005 VARCHAR2(2), a6006 VARCHAR2(40), a6007 VARCHAR2(4), a6008 NUMBER(2), a6009 VARCHAR2(2), a6010 VARCHAR2(1), a6011 VARCHAR2(1), a6012 VARCHAR2(1), a6013 VARCHAR2(1), a6014 VARCHAR2(1), a6015 NUMBER(2), a6016 VARCHAR2(1), a6017 NUMBER(2) ,ERROR_INFO  VARCHAR2(200)) ";
				stmt.execute(a60sql);
				stmt.execute("ALTER TABLE a60"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a61sql = "create table A61"
						+ tableExt + " AS SELECT * FROM A61 WHERE 1=2";
						//+ "( a0000  VARCHAR2(120), a2970  VARCHAR2(1), a2970a VARCHAR2(1), a2970b VARCHAR2(200), a6104  VARCHAR2(8), a2970c NUMBER(2), a6107  VARCHAR2(2), a6108  VARCHAR2(40), a6109  VARCHAR2(2), a6110  VARCHAR2(40), a6111  VARCHAR2(4), a6112  VARCHAR2(1), a6113  VARCHAR2(1), a6114  NUMBER(2), a6115  VARCHAR2(1), a6116  NUMBER(2),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a61sql);
				stmt.execute("ALTER TABLE a61"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a62sql = "create table A62"
						+ tableExt + " AS SELECT * FROM A62 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a6203 VARCHAR2(1), a6204 VARCHAR2(1), a6205 VARCHAR2(8), a2950 VARCHAR2(1), a6202 VARCHAR2(1),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a62sql);
				stmt.execute("ALTER TABLE a62"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a63sql = "create table A63"
						+ tableExt + " AS SELECT * FROM A63 WHERE 1=2";
						//+ "( a0000 VARCHAR2(120), a2951 VARCHAR2(2), a6302 VARCHAR2(2), a6303 VARCHAR2(2), a6304 VARCHAR2(2), a6305 VARCHAR2(2), a6306 VARCHAR2(8), a6307 VARCHAR2(2), a6308 VARCHAR2(2), a6309 VARCHAR2(2), a6310 VARCHAR2(20),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a63sql);
				stmt.execute("ALTER TABLE a63"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a64sql = "create table A64"
						+ tableExt + " AS SELECT * FROM A64 WHERE 1=2";
						//+ "( a0000   VARCHAR2(120), a6401   VARCHAR2(50), a6402   VARCHAR2(3), a6403   VARCHAR2(3), a6404   VARCHAR2(3), a6405   VARCHAR2(3), a6406   VARCHAR2(3), a6407   VARCHAR2(3), a6408   VARCHAR2(3), a6400   VARCHAR2(60), a64type VARCHAR2(4),ERROR_INFO  VARCHAR2(200) ) ";
				stmt.execute(a64sql);
				stmt.execute("ALTER TABLE a64"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a65sql = "create table A65"
						+ tableExt + " AS SELECT * FROM A65 WHERE 1=2";
				stmt.execute(a65sql);
				stmt.execute("ALTER TABLE a65"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String b01sql = "create table B01"
						+ tableExt + " AS SELECT * FROM B01 WHERE 1=2";
						//+ "( b0101 VARCHAR2(200), b0104 VARCHAR2(200), b0107 VARCHAR2(200), b0111 VARCHAR2(199), b0114 VARCHAR2(68), b0117 VARCHAR2(6), b0121 VARCHAR2(199), b0124 VARCHAR2(2), b0127 VARCHAR2(4), b0131 VARCHAR2(4), b0140 VARCHAR2(68), b0141 VARCHAR2(68), b0142 NUMBER, b0143 VARCHAR2(8), b0150 NUMBER, b0180 VARCHAR2(500), b0183 NUMBER, b0185 NUMBER, b0188 NUMBER, b0189 NUMBER, b0190 NUMBER, b0191 VARCHAR2(8), b0191a NUMBER, b0192 NUMBER, b0193 NUMBER, b0194 VARCHAR2(1), b01trans    NUMBER, b01ip VARCHAR2(32), b0227 NUMBER, b0232 NUMBER, b0233 NUMBER, sortid NUMBER, used  VARCHAR2(1), updated     VARCHAR2(1), create_user VARCHAR2(200), create_date DATE, update_user VARCHAR2(200), update_date DATE, status VARCHAR2(1), b0238 VARCHAR2(8), b0239 VARCHAR2(24), b0234 NUMBER, b0235 NUMBER, b0236 NUMBER, b0101new VARCHAR2(200),ERROR_INFO  VARCHAR2(200),psnB0111  VARCHAR2(200),b0132 VARCHAR2(8)) ";
				stmt.execute(b01sql);
				//stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD PSNB0111  VARCHAR2(200) ADD ERROR_INFO  VARCHAR2(200)");


				String a05sql = "create table A05"
						+ tableExt + " AS SELECT * FROM A05 WHERE 1=2";
						//+ "(a0000  VARCHAR2(120) ,a0500  VARCHAR2(120) ,a0531  VARCHAR2(1),a0501b VARCHAR2(6),a0504  VARCHAR2(8),a0511  VARCHAR2(260),a0517  VARCHAR2(8),a0524  VARCHAR2(1),a0525  VARCHAR2(1),a0526  VARCHAR2(1),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(a05sql);
				stmt.execute("ALTER TABLE a05"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A68sql = "create table A68"
						+ tableExt + " AS SELECT * FROM A68 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120) ,a6800 VARCHAR2(120) ,a6801 VARCHAR2(8),a6802 VARCHAR2(2),a6803 VARCHAR2(8),a6804 VARCHAR2(2),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(A68sql);
				stmt.execute("ALTER TABLE A68"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A69sql = "create table A69"
						+ tableExt + " AS SELECT * FROM A69 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120),a6900 VARCHAR2(120),a6901 VARCHAR2(2),a6902 VARCHAR2(2),a6903 VARCHAR2(8),a6904 VARCHAR2(2),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(A69sql);
				stmt.execute("ALTER TABLE A69"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A71sql = "create table A71"
						+ tableExt + " AS SELECT * FROM A71 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120), a7100 VARCHAR2(120),a7101 VARCHAR2(2000),ERROR_INFO  VARCHAR2(200))";
				stmt.execute(A71sql);
				stmt.execute("ALTER TABLE A71"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A99Z1sql = "create table A99Z1"
						+ tableExt + " AS SELECT * FROM A99Z1 WHERE 1=2";
						//+ "(a0000 VARCHAR2(120),a99Z100 VARCHAR2(120),a99Z101 VARCHAR2(1),a99Z102 VARCHAR2(8),a99Z103 VARCHAR2(1),A99Z104 VARCHAR2(8))";
				stmt.execute(A99Z1sql);
				stmt.execute("ALTER TABLE A99Z1"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				/*
				 * String b01sql = "create table B01"+tableExt+
				 * "(b0101  VARCHAR2(200),b0104  VARCHAR2(200),b0107  VARCHAR2(200),b0111  VARCHAR2(199) not null,b0114  VARCHAR2(68),b0117  VARCHAR2(8),b0121  VARCHAR2(199),b0124  VARCHAR2(8),b0127  VARCHAR2(8),b0131  VARCHAR2(8),b0140  VARCHAR2(68),b0141  VARCHAR2(68),b0142  NUMBER,b0143  VARCHAR2(8) default '0',b0150  NUMBER,b0180  VARCHAR2(500),b0191  VARCHAR2(10),b0194  VARCHAR2(1),b01trans NUMBER default 1,b01ip  VARCHAR2(32),b0227  NUMBER,b0232  NUMBER,b0233  NUMBER,sortid NUMBER,used VARCHAR2(1),UPDATED  VARCHAR2(1) default '0',create_user  VARCHAR2(200),CREATE_DATE  DATE default SYSDATE,UPDATE_user  VARCHAR2(200),UPDATE_DATE  DATE,status VARCHAR2(1) default '1',b0238  VARCHAR2(8),b0239  VARCHAR2(200),b0234  NUMBER,ERROR_INFO  VARCHAR2(200),psnB0111  VARCHAR2(200))"
				 * ; stmt.execute(b01sql);
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
				 */

				rs = stmt
						.executeQuery("select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH from user_tab_cols t where t.TABLE_NAME='B01_EXT'");
				if (rs != null) {
					String b01extsql = "create table B_E" + tableExt + "(";
					while (rs.next()) {
						b01extsql += rs.getString(1) + " " + rs.getString(2)
								+ "(" + rs.getString(3) + "),";
					}
					b01extsql = b01extsql.substring(0, b01extsql.length() - 1);
					b01extsql += ")";
					stmt.execute(b01extsql);
				}
				rs1 = stmt
						.executeQuery("select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH from user_tab_cols t where t.TABLE_NAME='INFO_EXTEND'");
				if (rs1 != null) {
					String infextsql = "create table I_E" + tableExt + "(";
					while (rs1.next()) {
						infextsql += rs1.getString(1) + " " + rs1.getString(2)
								+ "(" + rs1.getString(3) + "),";
					}
					infextsql = infextsql.substring(0, infextsql.length() - 1);
					infextsql += ")";
					stmt.execute(infextsql);
				}

			} else {
				// 以下代码注释的是字段没有修改之前的
				String a01sql = " create table A01"
						+ tableExt + " AS SELECT * FROM A01 WHERE 1=2";
						//+ " ( a0000 varchar(120), a0101 varchar(36), a0102 varchar(2500), a0104 varchar(2), a0107 varchar(8), a0111a varchar(200), a0114a varchar(200), a0115a varchar(120), a0117 varchar(2), a0128 varchar(120), a0134 varchar(8), a0140 varchar(100), a0141 varchar(8), a0144 varchar(8), a3921  varchar(8), a3927 varchar(8), a0160 varchar(8), a0163 varchar(2), a0165 varchar(8), a0184 varchar(18), a0187a varchar(120), a0192 varchar(2000), a0192a varchar(2000), a0221 varchar(5), a0288 varchar(8), a0192d varchar(80), a0192c varchar(8), a0196 varchar(120), a0197 varchar(1), a0195 varchar(68), a1701 LONGTEXT, a14z101 varchar(2000), a15z101 varchar(2000), a0120 varchar(8), a0121 varchar(8), a2949 varchar(8), a0122 varchar(8), a0104a varchar(60), a0111 varchar(8), a0114 varchar(8), a0117a varchar(60), a0128b varchar(120), a0141d varchar(100), a0144b varchar(8), a0144c varchar(8), a0148  varchar(20), a0148c varchar(8), a0149  varchar(80), a0151  varchar(1), a0153  varchar(1), a0157  varchar(120), a0158  varchar(5), a0159  varchar(50), a015a  varchar(2), a0161  varchar(500), a0162  varchar(8), a0180  varchar(1500), a0191  varchar(1), a0192b varchar(2000), a0193  varchar(5), a0194u varchar(8), a0198  varchar(200), a0199  varchar(1), a01k01 varchar(1), a01k02 varchar(8), cbdresult varchar(10), cbdw  varchar(100), isvalid varchar(20), nl varchar(4), nmzw   varchar(100), nrzw  varchar(100), orgid  varchar(500), qrzxl varchar(60), qrzxlxx varchar(120), qrzxw varchar(60), qrzxwxx varchar(120), rmly   varchar(100), status varchar(1), tbr varchar(36), tbrjg  varchar(80), userlog varchar(20), xgr  varchar(36), zzxl varchar(60), zzxlxx varchar(120), zzxw varchar(60), zzxwxx varchar(120), a0155 DATEtime, age bigint(4), jsnlsj DATEtime, resultsortid bigint(4), tbsj DATEtime, xgsj DATEtime, sortid bigint(16), a0194  bigint(8), a0192e varchar(80),a0192f VARCHAR(200), a0000new varchar(120),ERROR_INFO  varchar(200),TORGID varchar(200),TORDER varchar(8),ZGXL varchar(60),ZGXLXX varchar(120),ZGXW varchar(60),ZGXWXX varchar(120),TCSJSHOW VARCHAR(16),TCFSSHOW VARCHAR(40),fsj VARCHAR(1) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a01sql.toUpperCase());
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a02sql = " create table A02"
						+ tableExt + " AS SELECT * FROM A02 WHERE 1=2";
						//+ "( a0000 varchar(120), a0200 varchar(120), a0201a varchar(200),a0201a_all VARCHAR(200), a0201b varchar(199), a0201d varchar(1), a0201e varchar(1), a0215a varchar(100), a0219 varchar(1), a0223 varchar(8), a0225 varchar(8), a0243 varchar(8), a0245 varchar(260), a0247 varchar(2), a0251b varchar(1), a0255 varchar(1), a0265 varchar(8), a0267 varchar(24), a0272 varchar(100), a0281 varchar(8), a0221t bigint(8), b0238 varchar(200), b0239 varchar(200), a0221a varchar(8), wage_used bigint, updated   varchar(1), a4907 varchar(8), a4904 varchar(8), a4901 varchar(8), a0299 varchar(8), a0295 varchar(8), a0289 varchar(8), a0288 varchar(8), a0284 varchar(8), a0277 varchar(1), a0271 varchar(8), a0259 varchar(8), a0256c bigint, a0256b bigint, a0256a varchar(8), a0256 varchar(8), a0251 varchar(8), a0229 varchar(120), a0222 varchar(20), a0221w varchar(8), a0221 varchar(8), a0219w varchar(8), a0216a varchar(200), a0215b varchar(80), a0209 varchar(8), a0207 varchar(8), a0204 varchar(68), a0201c varchar(200), a0201 varchar(199),ERROR_INFO  varchar(200),a0279 VARCHAR(1) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a02sql.toUpperCase());
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a06sql = " create table A06"
						+ tableExt + " AS SELECT * FROM A06 WHERE 1=2";
						//+ "( a0600 varchar(120), a0000 varchar(120), a0601 varchar(8), a0602 varchar(60), a0604 varchar(8), a0607 varchar(8), a0611 varchar(100), a0614 varchar(1), a0699 varchar(8), updated varchar(1), sortid bigint,ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a06sql.toUpperCase());
				stmt.execute("ALTER TABLE a06"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a08sql = " create table A08"
						+ tableExt + " AS SELECT * FROM A08 WHERE 1=2";
						//+ "( a0000 varchar(120), a0800 varchar(120), a0801a varchar(120), a0801b varchar(8), a0901a varchar(40), a0901b varchar(8), a0804 varchar(8), a0807 varchar(8), a0904 varchar(8), a0814 varchar(120), a0824 varchar(40), a0827 varchar(8), a0837 varchar(1), a0811 varchar(8), a0898 varchar(1), a0831 varchar(1), a0832 varchar(1), a0834 varchar(1), a0835 varchar(1), a0838 varchar(1), a0839 varchar(1), a0899 varchar(8), updated  varchar(1), sortid bigint, wage_used bigint ,ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a08sql.toUpperCase());
				stmt.execute("ALTER TABLE a08"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a11sql = " create table A11"
						+ tableExt + " AS SELECT * FROM A11 WHERE 1=2";
						//+ "( a0000 varchar(120) , a1100 varchar(120), a1101 varchar(8), a1104 varchar(8), a1107 varchar(8), a1107a bigint , a1107b bigint, a1111 varchar(8), a1114 varchar(120), a1121a varchar(120), a1127 varchar(8), a1131 varchar(120), a1134 varchar(1), a1151 varchar(1), updated varchar(1), a1108 bigint, a1107c bigint,ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a11sql.toUpperCase());
				stmt.execute("ALTER TABLE a11"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a14sql = " create table A14"
						+ tableExt + " AS SELECT * FROM A14 WHERE 1=2";
						//+ "( a0000 varchar(120), a1400 varchar(120), a1404a varchar(40), a1404b varchar(8), a1407 varchar(8), a1411a varchar(60), a1414 varchar(8), a1415 varchar(8), a1424 varchar(8), a1428 varchar(8), updated varchar(1), sortid bigint,ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a14sql.toUpperCase());
				stmt.execute("ALTER TABLE a14"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a15sql = " create table A15"
						+ tableExt + " AS SELECT * FROM A15 WHERE 1=2";
						//+ "( a0000 varchar(120), a1500 varchar(120), a1517 varchar(4), a1521 varchar(4), a1527 varchar(8), updated varchar(1) ,ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a15sql.toUpperCase());
				stmt.execute("ALTER TABLE a15"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a29sql = " create table A29"
						+ tableExt + " AS SELECT * FROM A29 WHERE 1=2";
						//+ "( a0000 varchar(120), a2907 varchar(8), a2911 varchar(3), a2921a varchar(200), a2941 varchar(40), a2944 varchar(40), a2921d varchar(8), a2921c varchar(8), a2947b bigint(4), a2921b varchar(8), a2947a bigint(4), updated varchar(1), a2949 varchar(8), a2947 varchar(8),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a29sql.toUpperCase());
				stmt.execute("ALTER TABLE a29"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a30sql = "create table A30"
						+ tableExt + " AS SELECT * FROM A30 WHERE 1=2";
						//+ "( a0000 varchar(120), a3001 varchar(8), a3004 varchar(8), a3117a varchar(60), a3101 varchar(1), a3137 varchar(24), a3034 varchar(1000), updated varchar(1), a3007a varchar(100),a3038  varchar(100), ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a30sql.toUpperCase());
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a33sql = "create table A33"
						+ tableExt + " AS SELECT * FROM A33 WHERE 1=2";
				stmt.execute(a33sql.toUpperCase());
				stmt.execute("ALTER TABLE a33"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a36sql = "create table A36"
						+ tableExt + " AS SELECT * FROM A36 WHERE 1=2";
						//+ "( a0000 varchar(120), a3600 varchar(120), a3601 varchar(36), a3604a varchar(10), a3607 varchar(8), a3611 varchar(200), a3627 varchar(100), sortid bigint, updated varchar(1), a3684 varchar(18),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a36sql.toUpperCase());
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a37sql = "create table A37"
						+ tableExt + " AS SELECT * FROM A37 WHERE 1=2";
						//+ "( a0000 varchar(120), a3701 varchar(120), a3707a varchar(20), a3707c varchar(20), a3707e varchar(20), a3707b varchar(20), a3708 varchar(60), a3711 varchar(120), a3714 varchar(6), updated varchar(1),ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a37sql.toUpperCase());
				stmt.execute("ALTER TABLE a37"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a41sql = "create table A41"
						+ tableExt + " AS SELECT * FROM A41 WHERE 1=2";
						//+ "( a4100 varchar(120), a0000 varchar(120), a1100 varchar(120), a4101 varchar(10), a4102 varchar(10), a4103 varchar(3), a4104 varchar(10), a4105 varchar(200), a4199 varchar(200),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a41sql.toUpperCase());
				stmt.execute("ALTER TABLE a41"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a53sql = "create table A53"
						+ tableExt + " AS SELECT * FROM A53 WHERE 1=2";
						//+ "( a0000 varchar(120), a5300 varchar(120), a5304 varchar(400), a5315 varchar(400), a5317 varchar(400), a5319 varchar(400), a5321 varchar(8), a5323 varchar(8), a5327 varchar(36), a5399 varchar(60), updated varchar(1),ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a53sql.toUpperCase());
				stmt.execute("ALTER TABLE a53"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a57sql = "create table A57"
						+ tableExt + " AS SELECT * FROM A57 WHERE 1=2";
						//+ "( a0000 varchar(120), a5714 varchar(100), updated varchar(1), photodata BLOB, photoname varchar(400), photstype varchar(200), photopath varchar(200), picstatus varchar(1),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a57sql.toUpperCase());
				stmt.execute("ALTER TABLE a57"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a60sql = "create table A60"
						+ tableExt + " AS SELECT * FROM A60 WHERE 1=2";
						//+ "( a0000 varchar(120), a6001 varchar(1), a6002 varchar(8), a6003 varchar(2), a6004 varchar(40), a6005 varchar(2), a6006 varchar(40), a6007 varchar(4), a6008 bigint(2), a6009 varchar(2), a6010 varchar(1), a6011 varchar(1), a6012 varchar(1), a6013 varchar(1), a6014 varchar(1), a6015 bigint(2), a6016 varchar(1), a6017 bigint(2) ,ERROR_INFO  varchar(200))  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a60sql.toUpperCase());
				stmt.execute("ALTER TABLE a60"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a61sql = "create table A61"
						+ tableExt + " AS SELECT * FROM A61 WHERE 1=2";
						//+ "( a0000  varchar(120), a2970  varchar(1), a2970a varchar(1), a2970b varchar(200), a6104  varchar(8), a2970c bigint(2), a6107  varchar(2), a6108  varchar(40), a6109  varchar(2), a6110  varchar(40), a6111  varchar(4), a6112  varchar(1), a6113  varchar(1), a6114  bigint(2), a6115  varchar(1), a6116  bigint(2),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a61sql.toUpperCase());
				stmt.execute("ALTER TABLE a61"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a62sql = "create table A62"
						+ tableExt + " AS SELECT * FROM A62 WHERE 1=2";
						//+ "( a0000 varchar(120), a6203 varchar(1), a6204 varchar(1), a6205 varchar(8), a2950 varchar(1), a6202 varchar(1),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a62sql.toUpperCase());
				stmt.execute("ALTER TABLE a62"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a63sql = "create table A63"
						+ tableExt + " AS SELECT * FROM A63 WHERE 1=2";
						//+ "( a0000 varchar(120), a2951 varchar(2), a6302 varchar(2), a6303 varchar(2), a6304 varchar(2), a6305 varchar(2), a6306 varchar(8), a6307 varchar(2), a6308 varchar(2), a6309 varchar(2), a6310 varchar(20),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a63sql.toUpperCase());
				stmt.execute("ALTER TABLE a63"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a64sql = "create table A64"
						+ tableExt + " AS SELECT * FROM A64 WHERE 1=2";
						//+ "( a0000   varchar(120), a6401   varchar(50), a6402   varchar(3), a6403   varchar(3), a6404   varchar(3), a6405   varchar(3), a6406   varchar(3), a6407   varchar(3), a6408   varchar(3), a6400   varchar(60), a64type varchar(4),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a64sql.toUpperCase());
				stmt.execute("ALTER TABLE a64"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a65sql = "create table A65"
						+ tableExt + " AS SELECT * FROM A65 WHERE 1=2";
						//+ "( a0000   varchar(120), a6401   varchar(50), a6402   varchar(3), a6403   varchar(3), a6404   varchar(3), a6405   varchar(3), a6406   varchar(3), a6407   varchar(3), a6408   varchar(3), a6400   varchar(60), a64type varchar(4),ERROR_INFO  varchar(200) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a65sql.toUpperCase());
				stmt.execute("ALTER TABLE a65"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");


				String b01sql = "create table B01"
						+ tableExt + " AS SELECT * FROM B01 WHERE 1=2";
						//+ "( b0101 varchar(200), b0104 varchar(200), b0107 varchar(200), b0111 varchar(199), b0114 varchar(68), b0117 varchar(6), b0121 varchar(199), b0124 varchar(2), b0127 varchar(4), b0131 varchar(4), b0140 varchar(68), b0141 varchar(68), b0142 bigint, b0143 varchar(8), b0150 bigint, b0180 varchar(500), b0183 bigint, b0185 bigint, b0188 bigint, b0189 bigint, b0190 bigint, b0191 varchar(8), b0191a bigint, b0192 bigint, b0193 bigint, b0194 varchar(1), b01trans    bigint, b01ip varchar(32), b0227 bigint, b0232 bigint, b0233 bigint, sortid bigint, used  varchar(1), updated     varchar(1), create_user varchar(200), create_DATE DATE, upDATE_user varchar(200), upDATE_DATE DATE, status varchar(1), b0238 varchar(8), b0239 varchar(24), b0234 bigint, b0235 bigint, b0236 bigint, b0101new varchar(200),ERROR_INFO  varchar(200),psnB0111  varchar(200),b0132  VARCHAR(8)  )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(b01sql.toUpperCase());
				//stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD (PSNB0111  VARCHAR(200) , ERROR_INFO  VARCHAR(200))");


				String a05sql = "create table A05"
						+ tableExt + " AS SELECT * FROM A05 WHERE 1=2";
						//+ "(a0000  varchar(120) ,a0500  varchar(120) ,a0531  varchar(1),a0501b varchar(6),a0504  varchar(8),a0511  varchar(260),a0517  varchar(8),a0524  varchar(1),a0525  varchar(1),a0526  varchar(1),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a05sql.toUpperCase());
				stmt.execute("ALTER TABLE a05"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A68sql = "create table A68"
						+ tableExt + " AS SELECT * FROM A68 WHERE 1=2";
						//+ "(a0000 varchar(120) ,a6800 varchar(120) ,a6801 varchar(8),a6802 varchar(2),a6803 varchar(8),a6804 varchar(2),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A68sql.toUpperCase());
				stmt.execute("ALTER TABLE A68"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A69sql = "create table A69"
						+ tableExt + " AS SELECT * FROM A69 WHERE 1=2";
						//+ "(a0000 varchar(120),a6900 varchar(120),a6901 varchar(2),a6902 varchar(2),a6903 varchar(8),a6904 varchar(2),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A69sql.toUpperCase());
				stmt.execute("ALTER TABLE A69"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A71sql = "create table A71"
						+ tableExt + " AS SELECT * FROM A71 WHERE 1=2";
						//+ "(a0000 varchar(120), a7100 varchar(120),a7101 varchar(2000),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A71sql.toUpperCase());
				stmt.execute("ALTER TABLE A71"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A99Z1sql = "create table A99Z1"
						+ tableExt + " AS SELECT * FROM A99Z1 WHERE 1=2";
						//+ "(a0000 VARCHAR(120),a99Z100 VARCHAR(120),a99Z101 VARCHAR(1),a99Z102 VARCHAR(8),a99Z103 VARCHAR(1),A99Z104 VARCHAR(8)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A99Z1sql.toUpperCase());
				stmt.execute("ALTER TABLE A99Z1"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");
				/*
				 * String b01sql = "create table B01"+tableExt+
				 * "(b0101  VARCHAR2(200),b0104  VARCHAR2(200),b0107  VARCHAR2(200),b0111  VARCHAR2(199) not null,b0114  VARCHAR2(68),b0117  VARCHAR2(8),b0121  VARCHAR2(199),b0124  VARCHAR2(8),b0127  VARCHAR2(8),b0131  VARCHAR2(8),b0140  VARCHAR2(68),b0141  VARCHAR2(68),b0142  NUMBER,b0143  VARCHAR2(8) default '0',b0150  NUMBER,b0180  VARCHAR2(500),b0191  VARCHAR2(10),b0194  VARCHAR2(1),b01trans NUMBER default 1,b01ip  VARCHAR2(32),b0227  NUMBER,b0232  NUMBER,b0233  NUMBER,sortid NUMBER,used VARCHAR2(1),UPDATED  VARCHAR2(1) default '0',create_user  VARCHAR2(200),CREATE_DATE  DATE default SYSDATE,UPDATE_user  VARCHAR2(200),UPDATE_DATE  DATE,status VARCHAR2(1) default '1',b0238  VARCHAR2(8),b0239  VARCHAR2(200),b0234  NUMBER,ERROR_INFO  VARCHAR2(200),psnB0111  VARCHAR2(200))"
				 * ; stmt.execute(b01sql);
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");
				 */

				rs = stmt
						.executeQuery("select a.COLUMN_NAME,a.COLUMN_TYPE from information_schema.columns a where table_name='B01_EXT'  and a.TABLE_SCHEMA=(select DATABASE())");
				if (rs != null) {
					String b01extsql = "create table `B_E" + tableExt + "`(";
					while (rs.next()) {
						b01extsql += "`" + rs.getString(1) + "` "
								+ rs.getString(2) + ",";
					}

					b01extsql = b01extsql.substring(0, b01extsql.length() - 1);
					b01extsql += ")ENGINE=MyISAM DEFAULT CHARSET=utf8";
					stmt.execute(b01extsql.toUpperCase());
				}
				// 加上了distinct 去重2017/05/10
				rs1 = stmt
						.executeQuery("select distinct a.COLUMN_NAME,a.COLUMN_TYPE from information_schema.columns a where table_name='INFO_EXTEND' and a.TABLE_SCHEMA=(select DATABASE())");
				if (rs1 != null) {
					String infextsql = "create table `I_E" + tableExt + "`(";
					while (rs1.next()) {
						infextsql += "`" + rs1.getString(1) + "` "
								+ rs1.getString(2) + ",";
					}
					infextsql = infextsql.substring(0, infextsql.length() - 1);
					infextsql += ")ENGINE=MyISAM DEFAULT CHARSET=utf8";
					stmt.execute(infextsql.toUpperCase());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (rs1 != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	public void createTempTableWages(String tableExt) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			conn = sess.connection();
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			if (DBUtil.getDBType().equals(DBType.ORACLE)) {
				String a33sql = "create table A33"
						+ tableExt + " AS select  "+TYGSsqlUtil.OnlyA33zip+" FROM A33 t LEFT JOIN a01 on t.a0000=a01.a0000 where 1=2";
				stmt.execute(a33sql);
				stmt.execute("ALTER TABLE a33"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");
			} else {
				String a33sql = "create table A33"
						+ tableExt + " AS select  "+TYGSsqlUtil.OnlyA33zip+" FROM A33 t LEFT JOIN a01 on t.a0000=a01.a0000 where 1=2";
				stmt.execute(a33sql);
				stmt.execute("ALTER TABLE a33"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (rs1 != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	public void createTempTableTYGS(String tableExt) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			conn = sess.connection();
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			if (DBUtil.getDBType().equals(DBType.ORACLE)) {
				// 以下代码注释的是字段没有修改之前的
				String a01sql = " create table A01"
						+ tableExt + " AS SELECT * FROM A01 WHERE 1=2";
				stmt.execute(a01sql);
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a02sql = " create table A02"
						+ tableExt + " AS SELECT * FROM A02 WHERE 1=2";
				stmt.execute(a02sql);
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a06sql = " create table A06"
						+ tableExt + " AS SELECT * FROM A06 WHERE 1=2";
				stmt.execute(a06sql);
				stmt.execute("ALTER TABLE a06"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a08sql = " create table A08"
						+ tableExt + " AS SELECT * FROM A08 WHERE 1=2";
				stmt.execute(a08sql);
				stmt.execute("ALTER TABLE a08"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a14sql = " create table A14"
						+ tableExt + " AS SELECT * FROM A14 WHERE 1=2";
				stmt.execute(a14sql);
				stmt.execute("ALTER TABLE a14"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a15sql = " create table A15"
						+ tableExt + " AS SELECT * FROM A15 WHERE 1=2";
				stmt.execute(a15sql);
				stmt.execute("ALTER TABLE a15"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a36sql = "create table A36"
						+ tableExt + " AS SELECT * FROM A36 WHERE 1=2";
				stmt.execute(a36sql);
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a30sql = "create table A30"
						+ tableExt + " AS SELECT * FROM A30 WHERE 1=2";
				stmt.execute(a30sql);
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a33sql = "create table A33"
						+ tableExt + " AS SELECT * FROM A33 WHERE 1=2";
				stmt.execute(a33sql);
				stmt.execute("ALTER TABLE a33"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a57sql = "create table A57"
						+ tableExt + " AS SELECT * FROM A57 WHERE 1=2";
				stmt.execute(a57sql);
				stmt.execute("ALTER TABLE a57"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String a65sql = "create table A65"
						+ tableExt + " AS SELECT * FROM A65 WHERE 1=2";
				stmt.execute(a65sql);
				stmt.execute("ALTER TABLE a65"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String b01sql = "create table B01"
						+ tableExt + " AS SELECT * FROM B01 WHERE 1=2";
				stmt.execute(b01sql);
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD PSNB0111  VARCHAR2(200) ADD ERROR_INFO  VARCHAR2(200)");

				String a05sql = "create table A05"
						+ tableExt + " AS SELECT * FROM A05 WHERE 1=2";
				stmt.execute(a05sql);
				stmt.execute("ALTER TABLE a05"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

				String A99Z1sql = "create table A99Z1"
						+ tableExt + " AS SELECT * FROM A99Z1 WHERE 1=2";
				stmt.execute(A99Z1sql);
				stmt.execute("ALTER TABLE A99Z1"+ tableExt + " ADD ERROR_INFO  VARCHAR2(200)");

			} else {
				// 以下代码注释的是字段没有修改之前的
				String a01sql = " create table A01"
						+ tableExt + " AS SELECT * FROM A01 WHERE 1=2";
				stmt.execute(a01sql.toUpperCase());
				stmt.execute("ALTER TABLE a01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a02sql = " create table A02"
						+ tableExt + " AS SELECT * FROM A02 WHERE 1=2";
				stmt.execute(a02sql.toUpperCase());
				stmt.execute("ALTER TABLE a02"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a06sql = " create table A06"
						+ tableExt + " AS SELECT * FROM A06 WHERE 1=2";
				stmt.execute(a06sql.toUpperCase());
				stmt.execute("ALTER TABLE a06"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a08sql = " create table A08"
						+ tableExt + " AS SELECT * FROM A08 WHERE 1=2";
				stmt.execute(a08sql.toUpperCase());
				stmt.execute("ALTER TABLE a08"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a14sql = " create table A14"
						+ tableExt + " AS SELECT * FROM A14 WHERE 1=2";
				stmt.execute(a14sql.toUpperCase());
				stmt.execute("ALTER TABLE a14"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a15sql = " create table A15"
						+ tableExt + " AS SELECT * FROM A15 WHERE 1=2";
				stmt.execute(a15sql.toUpperCase());
				stmt.execute("ALTER TABLE a15"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a36sql = "create table A36"
						+ tableExt + " AS SELECT * FROM A36 WHERE 1=2";
				stmt.execute(a36sql.toUpperCase());
				stmt.execute("ALTER TABLE a36"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a30sql = "create table A30"
						+ tableExt + " AS SELECT * FROM A30 WHERE 1=2";
				stmt.execute(a30sql.toUpperCase());
				stmt.execute("ALTER TABLE a30"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a33sql = "create table A33"
						+ tableExt + " AS SELECT * FROM A33 WHERE 1=2";
				stmt.execute(a33sql.toUpperCase());
				stmt.execute("ALTER TABLE a33"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a57sql = "create table A57"
						+ tableExt + " AS SELECT * FROM A57 WHERE 1=2";
				stmt.execute(a57sql.toUpperCase());
				stmt.execute("ALTER TABLE a57"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");


				String a65sql = "create table A65"
						+ tableExt + " AS SELECT * FROM A65 WHERE 1=2";
				stmt.execute(a65sql.toUpperCase());
				stmt.execute("ALTER TABLE a65"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String b01sql = "create table B01"
						+ tableExt + " AS SELECT * FROM B01 WHERE 1=2";
						//+ "( b0101 varchar(200), b0104 varchar(200), b0107 varchar(200), b0111 varchar(199), b0114 varchar(68), b0117 varchar(6), b0121 varchar(199), b0124 varchar(2), b0127 varchar(4), b0131 varchar(4), b0140 varchar(68), b0141 varchar(68), b0142 bigint, b0143 varchar(8), b0150 bigint, b0180 varchar(500), b0183 bigint, b0185 bigint, b0188 bigint, b0189 bigint, b0190 bigint, b0191 varchar(8), b0191a bigint, b0192 bigint, b0193 bigint, b0194 varchar(1), b01trans    bigint, b01ip varchar(32), b0227 bigint, b0232 bigint, b0233 bigint, sortid bigint, used  varchar(1), updated     varchar(1), create_user varchar(200), create_DATE DATE, upDATE_user varchar(200), upDATE_DATE DATE, status varchar(1), b0238 varchar(8), b0239 varchar(24), b0234 bigint, b0235 bigint, b0236 bigint, b0101new varchar(200),ERROR_INFO  varchar(200),psnB0111  varchar(200),b0132 varchar(8) )  ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(b01sql.toUpperCase());
				stmt.execute("ALTER TABLE b01"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String a05sql = "create table A05"
						+ tableExt + " AS SELECT * FROM A05 WHERE 1=2";
						//+ "(a0000  varchar(120) ,a0500  varchar(120) ,a0531  varchar(1),a0501b varchar(6),a0504  varchar(8),a0511  varchar(260),a0517  varchar(8),a0524  varchar(1),a0525  varchar(1),a0526  VARCHAR(1),ERROR_INFO  varchar(200)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(a05sql.toUpperCase());
				stmt.execute("ALTER TABLE a05"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

				String A99Z1sql = "create table A99Z1"
						+ tableExt + " AS SELECT * FROM A99Z1 WHERE 1=2";
						//+ "(a0000 VARCHAR(120),a99Z100 VARCHAR(120),a99Z101 VARCHAR(1),a99Z102 VARCHAR(8),a99Z103 VARCHAR(1),A99Z104 VARCHAR(8)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				stmt.execute(A99Z1sql.toUpperCase());
				stmt.execute("ALTER TABLE A99Z1"+ tableExt + " ADD ERROR_INFO  VARCHAR(200)");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (rs1 != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void rollbackImpTable(String imprecordid, String tableExt) {
		if (tableExt == null || "".equals(tableExt.replace(" ", ""))) {
			new AppException("发生异常，原因传入参数为空或空字符串！");
			return;
		}
		String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
				"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
				"I_E", "B_E", "A60", "A61", "A62", "A63", "A64", "A05", "A68",
				"A69", "A71", "A99Z1" };
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			stmt.execute("delete from imp_record where imp_record_id='"
					+ imprecordid + "'");
			for (int i = 0; i < tables.length; i++) {
				stmt = conn.createStatement();
				try {
					stmt.execute("drop table " + tables[i] + tableExt);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void rollbackImpTableMake(String imprecordid, String tableExt) {
		if (tableExt == null || "".equals(tableExt.replace(" ", ""))) {
			new AppException("发生异常，原因传入参数为空或空字符串！");
			return;
		}
		String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
				"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
				"I_E", "B_E", "A60", "A61", "A62", "A63", "A64", "A65", "A05",
				"A68", "A69", "A71", "A99Z1" };
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			stmt.execute("delete from imp_record where imp_record_id='"
					+ imprecordid + "'");
			for (int i = 0; i < tables.length; i++) {
				stmt = conn.createStatement();
				try {
					stmt.execute("drop table " + tables[i] + tableExt);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public int saveData_SaxHander3_OldHzb(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String B0111,
			String deptid, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 10000;
		int t_n = 0;
		// String docname = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid +
		// "/Table/" +
		// (table.equals("I_E")?"INFO_EXTEND":table.equals("B_E")?"B01_EXT":table)
		// + ".xml";//xml文件路径
		String docname = "";
		if (lowerCase.equalsIgnoreCase("7z")) {
			File dir = new File(AppConfig.HZB_PATH + "/temp/upload/" + uuid
					+ "/" + uuid);
			File[] subs = dir.listFiles();
			File f = subs[0];
			docname = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid
					+ "/" + f.getName() + "/Table/" + table + ".xml";// xml文件路径
		} else {
			docname = AppConfig.HZB_PATH
					+ "/temp/upload/unzip/"
					+ uuid
					+ "/Table/"
					+ (table.equals("I_E") ? "INFO_EXTEND" : table
							.equals("B_E") ? "B01_EXT" : table) + ".xml";// xml文件路径
		}
		File file = new File(docname);
		if (!file.exists()) {
			// file.mkdirs();
			return 0;
		}
		int k = t_n;
		if (table.equals("A57")) { // A57处理
			try {
				// ----------------
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57"
								+ tableExt
								+ "(A0000,A5714,UPDATED,"
								+ "PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?)");
				XmlSAXHandler01 myh = new XmlSAXHandler01(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				// k = myh.t_n;
				boolean isError = myh.isError;
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} catch (Exception e) {
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw e;
			}

		} else {
			try {
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"
						+ table + tableExt + "')";
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1.prepareStatement("insert into " + table
						+ tableExt + "(" + colomn_sql + ") values(" + value_sql
						+ ")");
				XmlSAXHandler02_OldHzb myh = new XmlSAXHandler02_OldHzb(
						docname, lowerCase, table, imprecordid, t_n, uuid,
						from_file, B0111, deptid, impdeptid, conn1, pstmt1,
						colomns, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();
				// System.gc();

				if (table.equals("A01")) {
					k = myh.t_n;
				}
				// table.equals("A01")?k = myh.t_n:k = 0;
				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();

			} catch (Exception e) {
				e.printStackTrace();
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				throw e;
			}
		}
		return k;
	}

	public int saveData_SaxHander4_OldHzb(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String B0111,
			String deptid, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 2000;
		int t_n = 0;
		String docname = "";
		if (lowerCase.equalsIgnoreCase("7z")) {
			File dir = new File(AppConfig.HZB_PATH + "/temp/upload/" + uuid
					+ "/" + uuid);
			File[] subs = dir.listFiles();
			File f = subs[0];
			docname = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid
					+ "/" + f.getName() + "/Table/" + table + ".xml";// xml文件路径
		} else {
			docname = AppConfig.HZB_PATH
					+ "/temp/upload/unzip/"
					+ uuid
					+ "/Table/"
					+ (table.equals("I_E") ? "INFO_EXTEND" : table
							.equals("B_E") ? "B01_EXT" : table) + ".xml";// xml文件路径
		}
		File file = new File(docname);
		if (!file.exists()) {
			// file.mkdir();
			return 0;
		}
		int k = t_n;
		try {
			if (table.equals("A57")) { // A57处理
				// ----------------
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57"
								+ tableExt
								+ "(A0000,A5714,UPDATED,"
								+ "PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?)");
				XmlSAXHandler01 myh = new XmlSAXHandler01(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				boolean isError = myh.isError;
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} else {
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = sql = "select column_name from information_schema.columns a where table_name = upper('"
						+ table + tableExt + "') and a.TABLE_SCHEMA = 'ZWHZYQ'";
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1.prepareStatement("insert into " + table
						+ tableExt + "(" + colomn_sql + ") values(" + value_sql
						+ ")");

				XmlSAXHandler02_OldHzb myh = new XmlSAXHandler02_OldHzb(
						docname, lowerCase, table, imprecordid, t_n, uuid,
						from_file, B0111, deptid, impdeptid, conn1, pstmt1,
						colomns, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();
				// System.gc();

				if (table.equals("A01")) {
					k = myh.t_n;
				}

				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();

			}
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("3", "4", "失败:" + e.getMessage(),
						uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}

		return k;

	}

	public int saveData_SaxHander3_MakeHzb(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String B0111,
			String deptid, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 10000;
		int t_n = 0;
		// String docname = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid +
		// "/Table/" +
		// (table.equals("I_E")?"INFO_EXTEND":table.equals("B_E")?"B01_EXT":table)
		// + ".xml";//xml文件路径
		String docname = "";
		if (lowerCase.equalsIgnoreCase("7z")) {
			File dir = new File(AppConfig.HZB_PATH + "/temp/upload/" + uuid
					+ "/" + uuid);
			File[] subs = dir.listFiles();
			File f = subs[0];
			docname = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid
					+ "/" + f.getName() + "/Table/" + table + ".xml";// xml文件路径
		} else {
			docname = AppConfig.HZB_PATH
					+ "/temp/upload/unzip/"
					+ uuid
					+ "/Table/"
					+ (table.equals("I_E") ? "INFO_EXTEND" : table
							.equals("B_E") ? "B01_EXT" : table) + ".xml";// xml文件路径
		}
		File file = new File(docname);
		if (!file.exists()) {
			// file.mkdirs();
			return 0;
		}
		int k = t_n;
		if (table.equals("A57")) { // A57处理
			try {
				// ----------------
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57"
								+ tableExt
								+ "(A0000,A5714,UPDATED,"
								+ "PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?)");
				XmlSAXHandler01 myh = new XmlSAXHandler01(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				// k = myh.t_n;
				boolean isError = myh.isError;
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} catch (Exception e) {
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw e;
			}

		} else {
			try {
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"
						+ table + tableExt + "')";
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1.prepareStatement("insert into " + table
						+ tableExt + "(" + colomn_sql + ") values(" + value_sql
						+ ")");
				XmlSAXHandler02_MakeHzb myh = new XmlSAXHandler02_MakeHzb(
						docname, lowerCase, table, imprecordid, t_n, uuid,
						from_file, B0111, deptid, impdeptid, conn1, pstmt1,
						colomns, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();
				// System.gc();

				if (table.equals("A01")) {
					k = myh.t_n;
				}
				// table.equals("A01")?k = myh.t_n:k = 0;
				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();

			} catch (Exception e) {
				e.printStackTrace();
				try {
					KingbsconfigBS.saveImpDetail("3", "4",
							"失败:" + e.getMessage(), uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				throw e;
			}
		}
		return k;
	}

	public int saveData_SaxHander4_MakeHzb(String lowerCase, String table,
			String imprecordid, String uuid, String from_file, String B0111,
			String deptid, String impdeptid, String tableExt) throws Exception {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		int batchNum = 2000;
		int t_n = 0;
		String docname = "";
		if (lowerCase.equalsIgnoreCase("7z")) {
			File dir = new File(AppConfig.HZB_PATH + "/temp/upload/" + uuid
					+ "/" + uuid);
			File[] subs = dir.listFiles();
			File f = subs[0];
			docname = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid
					+ "/" + f.getName() + "/Table/" + table + ".xml";// xml文件路径
		} else {
			docname = AppConfig.HZB_PATH
					+ "/temp/upload/unzip/"
					+ uuid
					+ "/Table/"
					+ (table.equals("I_E") ? "INFO_EXTEND" : table
							.equals("B_E") ? "B01_EXT" : table) + ".xml";// xml文件路径
		}
		File file = new File(docname);
		if (!file.exists()) {
			// file.mkdir();
			return 0;
		}
		int k = t_n;
		try {
			if (table.equals("A57")) { // A57处理
				// ----------------
				Connection conn1 = sess.connection();
				conn1.setAutoCommit(false);
				PreparedStatement pstmt1 = conn1
						.prepareStatement("insert into a57"
								+ tableExt
								+ "(A0000,A5714,UPDATED,"
								+ "PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,?,?)");
				XmlSAXHandler01 myh = new XmlSAXHandler01(docname, lowerCase,
						table, imprecordid, t_n, uuid, from_file, B0111,
						deptid, impdeptid, conn1, pstmt1, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				boolean isError = myh.isError;
				pstmt1.close();
				conn1.commit();
				conn1.close();
				if (isError) {
					throw new Exception("解析数据异常！");
				}
			} else {
				Connection conn1 = sess.connection();
				// --------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();// b01字段连接
																// 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer(); // b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = sql = "select column_name from information_schema.columns a where table_name = upper('"
						+ table + tableExt + "') and a.TABLE_SCHEMA = 'ZWHZYQ'";
				colomns = sess.createSQLQuery(sql).list();
				CommonQueryBS.systemOut(" CodeTableCol 开始"
						+ table
						+ "获取List column："
						+ (Runtime.getRuntime().totalMemory() - Runtime
								.getRuntime().freeMemory()));
				if (colomns != null) {
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if (j != colomns.size() - 1) {
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1.prepareStatement("insert into " + table
						+ tableExt + "(" + colomn_sql + ") values(" + value_sql
						+ ")");

				XmlSAXHandler02_MakeHzb myh = new XmlSAXHandler02_MakeHzb(
						docname, lowerCase, table, imprecordid, t_n, uuid,
						from_file, B0111, deptid, impdeptid, conn1, pstmt1,
						colomns, batchNum);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				// 获取SAXParser分析器的实例
				SAXParser saxParser = saxParserFactory.newSAXParser();
				// InputStream inputStream = new FileInputStream(new
				// File(docname));
				saxParser.parse(new InputSource(new FilterInputStreamReader(
						new FileInputStream(docname), "utf-8")), myh);
				pstmt1.executeBatch();
				pstmt1.clearBatch();
				pstmt1.close();
				if (colomns != null)
					colomns.clear();
				colomns = null;
				conn1.close();
				// System.gc();

				if (table.equals("A01")) {
					k = myh.t_n;
				}

				boolean isError = myh.isError;
				if (isError) {
					throw new Exception("解析数据异常！");
				}
				System.gc();

			}
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("3", "4", "失败:" + e.getMessage(),
						uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}

		return k;

	}
}
