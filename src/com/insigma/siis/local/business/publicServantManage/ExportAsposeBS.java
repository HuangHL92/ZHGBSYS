package com.insigma.siis.local.business.publicServantManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hsqldb.lib.StringUtil;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FieldMergingArgs;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.ImageFieldMergingArgs;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.aspose.words.net.System.Data.DataRelation;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataSet;
import com.aspose.words.net.System.Data.DataTable;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFFont;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;

import com.insigma.siis.demo.MapMailMergeDataSource;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.demo.entity.BbUtils;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;

import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.gbmc.expexcel.ExpTPB;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc1;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc2;
import com.insigma.siis.local.util.AsposeExcelToPdf;
import com.insigma.siis.local.util.ExeclUtil;
import com.lbs.leaf.util.DateUtil;

import net.sourceforge.pinyin4j.PinyinHelper;

public class ExportAsposeBS extends PageModel {

	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader().getResourceAsStream("Aspose.Words.lic"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public File createNewFile(String tempPath, String rPath, String newFileName) {
//      读取模板，并赋值到新文件
		File file = new File(tempPath);
//      rPath: 新的文件名
		// 判断路径是否存在
		File dir = new File(rPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(rPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	public static String getRootPath() {
		// 获取模板路径
		String classPath = FiledownServlet.class.getClassLoader().getResource("/").getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath = "";

		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		rootPath = rootPath + "pages/exportexcel/";

		return rootPath;
	}

	/**
	 * 生成审签单，返回要压缩的zip路径
	 *
	 * @return
	 */
	public String signatureSheet(String[] Flds, String[] Vals, String temple, String filename, String path) {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return "";
		}
		String rootPath = getRootPath();
		String doctpl = rootPath + temple;
		String expFile = "";
		// path不为空，表示传入路径，不用生成新的路径
		if (path != null) {
			expFile = path;
		} else {
			expFile = ExpRar.expFile();
		}
		try {
			Document doc = new Document(doctpl);
			doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
			doc.getMailMerge().execute(Flds, Vals);
			File outFile = new File(expFile + filename);
			// 写入到Word文档中
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			// 保存到输出流中
			doc.save(os, SaveFormat.DOC);
			OutputStream out = new FileOutputStream(outFile);
			out.write(os.toByteArray());
			out.close();
			return expFile;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getexpPath() {
		String expFile = ExpRar.expFile();
		return expFile;
	}

	// 导出发文word
	public String getFwWordByA0000(String a0000, String fwh, String nrzw, String nmzw, String expFile, String js0100) {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return "";
		}
		if (nrzw == null) {
			nrzw = "";
		}
		if (nmzw == null) {
			nmzw = "";
		}
		String rootPath = getRootPath();
		String tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		HBSession sess = HBUtil.getHBSession();
		try {
			Js01 js01 = (Js01) sess.get(Js01.class, js0100);
			String js0122 = js01.getJs0122();
			Map<String, Object> dataMap = FiledownServlet.getMap(a0000, tpid, js0122);
			String a0101 = (String) dataMap.get("a0101");
			String doctpl = rootPath + "gbrmspb_fw.doc";
			Document doc = new Document(doctpl);
			// 增加处理简历和照片程序
			doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
			StringBuffer mapkey = new StringBuffer();
			StringBuffer mapvalue = new StringBuffer();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.equals("image")) {
					if (js0122 != null && (js0122.equals("2") || js0122.equals("3") || js0122.equals("4"))) {
						List<A57> list = sess
								.createSQLQuery(
										"select * from v_js_a57 where a0000='" + a0000 + "' and v_xt='" + js0122 + "' ")
								.addEntity(A57.class).list();
						A57 a57 = null;
						if (list.size() > 0) {
							a57 = list.get(0);
						}
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57, js0122);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + "qcjs/" + js0122 + "/" + a57.getPhotopath()
										+ a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					} else {
						A57 a57 = (A57) sess.get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}

				}
				if (StringUtil.isEmpty(value + "")) {
					value = "@";
				}
				mapkey = mapkey.append(key + "&");
				// 去掉&特殊字符
				value = value.toString().replaceAll("&", "");
				mapvalue = mapvalue.append(value + "&");
			}
			StringBuffer sb = new StringBuffer();
			// 填充发文数据
			mapkey = mapkey.append("nrzw" + "&");
			mapkey = mapkey.append("nmzw" + "&");
			mapkey = mapkey.append("fwh" + "&");
			mapkey.append("sj" + "&");
			if (fwh.indexOf("@") != -1) {
				nrzw = nrzw.substring(0, nrzw.length() - 1);
				nmzw = nmzw.substring(0, nmzw.length() - 1);
				mapvalue = mapvalue.append(nrzw.replace("@", ",") + "&");
				mapvalue = mapvalue.append(nmzw.replace("@", ",") + "&");
				mapvalue = mapvalue.append(fwh.replace("@", ";").replace("&", "") + "。&");
				String[] fwhs = fwh.split("&");
				for (int i = 0; i < fwhs.length; i++) {
					if (fwhs[i].indexOf("〔") != -1) {
						sb.append(fwhs[i]).append("、");
						break;
					}
				}
				fwh = sb.length() == 0 ? fwhs[0] : sb.toString().substring(0, sb.length() - 1);
			} else {
				mapvalue = mapvalue.append(nrzw + "&");
				mapvalue = mapvalue.append(nmzw + "&");
				mapvalue.append(fwh);
				mapvalue.append(!"".equals(nrzw) ? "任" + nrzw : "");
				mapvalue.append(!"".equals(nrzw) && !"".equals(nmzw) ? "," : "");
				mapvalue.append(!"".equals(nmzw) ? "免" + nmzw : "");
				mapvalue.append("。&");
				// mapvalue = mapvalue.append(fwh+"任"+nrzw+",免"+nmzw + "&");
			}
			String now = DateUtil.getCurrentDate_String("yyyy年MM月dd日");
			// String sj = now.substring(0,
			// 3)+"年"+now.substring(4,5)+"月"+now.substring(6,7)+"日";
			mapvalue.append(now + "&");
			// 文本域
			String[] Flds = mapkey.toString().split("&");
			// 值
			String[] Vals = mapvalue.toString().split("&");
			for (int j = 0; j < Vals.length; j++) {
				if (Vals[j].equals("@")) {
					Vals[j] = "";
				}
			}

			// 填充单个数据
			doc.getMailMerge().execute(Flds, Vals);
			File outFile = new File(expFile + "_" + fwh + a0101 + ".doc");
			// 写入到Word文档中
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			// 保存到输出流中
			doc.save(os, SaveFormat.DOC);
			OutputStream out = new FileOutputStream(outFile);
			out.write(os.toByteArray());
			out.close();
			return expFile;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	@SuppressWarnings("unchecked")
	public List getPdfsByA000s_aspose(List<String> a0000s, String tpid, String type, String ids, List<String> result,
									  String[] sub) throws AppException, IOException {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return null;
		}
		String rootPath = getRootPath();
		String doctpl = "";
		Map<String, Object> dataMap = null;
		List<String> paths = new ArrayList<String>();
		String expFile = ExpRar.expFile();
		if (tpid.equals("eebdefc2-4d67-4452-a973-5f7939530a11")) {
			// 干部任免审批表
			type = gbrmAuditForm(a0000s, tpid, type, rootPath, expFile);
			System.out.println("type " + type);
		} else if (tpid.equals("B73E508A87A44EF889430ABA451AC85C") ) {
			// 干部任免审批表(出生年月)
			type = gbrmAuditForm2(a0000s, tpid, type, rootPath, expFile);
		} else if (tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")
				|| tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")) {
			// 公务员登记表||参照登记表
			gwyForm(a0000s, tpid, type, rootPath, expFile);
		} else if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184") || tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")) {
			// 公务员登记备案表||参照登记备案表
			gwyRecordForm(a0000s, tpid, type, ids, result, rootPath, expFile);
		} else if (tpid.equals("a43d8c50-400d-42fe-9e0d-5665ed0b0508")) {
			// 年度考核登记表
			annualAssessmentForm(a0000s, tpid, type, rootPath, expFile);
		} else if (tpid.equals("04f59673-9c3a-4d9c-b016-a5b789d636e2")) {
			// 奖励审批表
			rewardAuditForm(a0000s, tpid, type, rootPath, expFile);
		}else if (tpid.equals("excel")) {
			// 干部名册按机构分组
			try {
				gbmc(ids, expFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RadowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (tpid.equals("47b1011d70f34aefb89365bbfce")) {
			// 会议名册 一人一条
			gbmc2(type, ids, rootPath, expFile);
		}  else if (tpid.equals("3de527c0-ea23-42c4-a66f")) {
			// 公务员录用审批表
			gwyImportAuditPDF(a0000s, tpid, type, rootPath, expFile);
		} else if (tpid.equals("9e7e1226-6fa1-46a1-8270")) {
			// 调任审批表
			dutyChangeAuditPDF(a0000s, tpid, type, rootPath, expFile);
		} else if (tpid.equals("28bc4d39-dccd-4f07-8aa9")) {
			// 公务员职级套转表
			gwyZhiJiTaoZhuanDoc(a0000s, tpid, type, rootPath, expFile);
		}

		// 5.删除目录下非PDF文件，汇总各个PDF文件路径，并返回
		File dec = new File(expFile);
		File[] files = dec.listFiles();
		if (type.equals("pdf")) {
			for (File f0 : files) {
				int indexPoint = f0.getName().lastIndexOf(".") + 1;
				String extStr = f0.getName().length() >= indexPoint ? f0.getName().substring(indexPoint) : "";
				if (extStr.equalsIgnoreCase("pdf")) {
					paths.add(f0.getAbsolutePath());
				} else {
					f0.delete();
				}
			}
		} else {
			paths.add(expFile);
		}

		return paths;
	}

	private String gbrmAuditForm(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 干部任免审批表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);

				String a0101 = (String) dataMap.get("a0101");

				if ("word1".equals(type)) {
					doctpl = rootPath + "gbrmspb_1_1.doc";
				} else if ("pdf1.1".equals(type)) {
					doctpl = rootPath + "gbrmspb_1_1.doc";
					type = "pdf";
				} else {
					doctpl = rootPath + "gbrmspb_1.doc";
				}

				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					// 去掉&特殊字符
					value = value.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&nbsp;", " ")
							.replaceAll("&", "");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据 //此处生成word路径 中文打印不了 转成了pinyin
				doc.getMailMerge().execute(Flds, Vals);
//				File outFile = new File(expFile + (i + 1) + "_" + getHanziInitials(a0101) + ".doc");
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
//					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + getHanziInitials(a0101) + ".doc",
//							expFile + (i + 1) + "_" + a0101 + ".pdf");
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return type;
	}

	private String gbrmAuditForm2(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 干部任免审批表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);

				String a0101 = (String) dataMap.get("a0101");

				if ("word1".equals(type)) {
					doctpl = rootPath + "gbrmbsr_1_1.doc";
				} else if ("pdf1.1".equals(type)) {
					doctpl = rootPath + "gbrmbsr_1_1.doc";
					type = "pdf";
				} else {
					doctpl = rootPath + "gbrmbsr_1.doc";
				}

				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					// 去掉&特殊字符
					value = value.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&nbsp;", " ")
							.replaceAll("&", "");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据 //此处生成word路径 中文打印不了 转成了pinyin
				doc.getMailMerge().execute(Flds, Vals);
//				File outFile = new File(expFile + (i + 1) + "_" + getHanziInitials(a0101) + ".doc");
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + getHanziInitials(a0101) + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return type;
	}
	
	private void gwyForm(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 公务员登记表||参照登记表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				String QRZXLXX = (String) dataMap.get("qrzxlxx");
				String QRZXWXX = (String) dataMap.get("qrzxwxx");
				String ZZXLXX = (String) dataMap.get("zzxlxx");
				String ZZXWXX = (String) dataMap.get("zzxwxx");
				String a0101 = (String) dataMap.get("a0101");
				if (QRZXLXX.equals(QRZXWXX) && !ZZXLXX.equals(ZZXWXX)) {
					// 上一，下二
					if (tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
						doctpl = rootPath + "gwydjb_4.doc";
					} else {
						doctpl = rootPath + "czgwydjb_4.doc";
					}

				} else if (!QRZXLXX.equals(QRZXWXX) && ZZXLXX.equals(ZZXWXX)) {
					// 上二，下一
					if (tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
						doctpl = rootPath + "gwydjb_3.doc";
					} else {
						doctpl = rootPath + "czgwydjb_3.doc";
					}
				} else if (!QRZXLXX.equals(QRZXWXX) && !ZZXLXX.equals(ZZXWXX)) {
					// 上二，下二
					if (tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
						doctpl = rootPath + "gwydjb_2.doc";
					} else {
						doctpl = rootPath + "czgwydjb_2.doc";
					}
				} else if (QRZXLXX.equals(QRZXWXX) && ZZXLXX.equals(ZZXWXX)) {
					// 上一，下一
					if (tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
						doctpl = rootPath + "gwydjb_1.doc";
					} else {
						doctpl = rootPath + "czgwydjb_1.doc";
					}
				}
				if (tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
					doctpl = rootPath + "gwydjb_1.doc";
				} else {
					doctpl = rootPath + "czgwydjb_1.doc";
				}
				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {

						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void gwyRecordForm(List<String> a0000s, String tpid, String type, String ids, List<String> result,
							   String rootPath, String expFile) {
		String doctpl;
		// 公务员登记备案表||参照登记备案表
		ids = ids.replace("@", ",").replace("|", "'");
		String sql = "";
		if (DBUtil.getDBType() == DBType.ORACLE) {
			sql = "select rownum, a01.*                                                           "
					+ "  from (select a0101,                                                          "
					+ "               (select code_name                                               "
					+ "                  from code_value                                              "
					+ "                 where code_type = 'GB2261'                                    "
					+ "                   and code_value.code_value = a01.a0104) a0104,               "
					+ "               substr(a01.a0107, 1, 4) || '.' || substr(a01.a0107, 5, 2) a0107,"
					+ "               (case                                                           "
					+ "                 when (select a08.a0801a                                       "
					+ "                         from a08 a08                                          "
					+ "                        where a08.A0000 = a01.a0000                            "
					+ "                          and a08.a0834 = '1'                                  "
					+ "                          and a08.a0899 = 'true') is null then                 "
					+ "                  null                                                         "
					+ "                 else                                                          "
					+ "                  (select a08.a0801a                                           "
					+ "                     from a08 a08                                              "
					+ "                    where a08.A0000 = a01.a0000                                "
					+ "                      and a08.a0834 = '1'                                      "
					+ "                      and a08.a0899 = 'true')                                  "
					+ "               end) as xl,                                                     "
					+ "               a0192,                                                          "
					+ "               (select code_name                                               "
					+ "                  from code_value                                              "
					+ "                 where code_type = 'ZB134'                                     "
					+ "                   and code_value.code_value = a01.a0120) a0120,               "
					+ "               a01.a0180,                                                      "
					+ "               a01.torgid,                                                     "
					+ "               a01.torder                                                      "
					+ "          from A01 a01                                                         "
					+ "         where a01.a0000 in (" + ids + ")                                          "
					+ "         order by a01.torgid, a01.torder) a01                                  ";
		} else {
			sql = "select (cast(@rowNum := @rowNum + 1 as char)) as rownum,        "
					+ "       a0101,                                                 "
					+ "       (select code_name                                      "
					+ "          from code_value                                     "
					+ "         where code_type = 'GB2261'                           "
					+ "           and code_value.code_value = a01.a0104) a0104,      "
					+ "       CONCAT_WS('.',substr(a01.a0107, 1, 4),substr(a01.a0107, 5, 2)) a0107, "
					+ "       (case                                                  "
					+ "         when (select a08.a0801a                              "
					+ "                 from a08 a08                                 "
					+ "                where a08.A0000 = a01.a0000                   "
					+ "                  and a08.a0834 = '1'                         "
					+ "                  and a08.a0899 = 'true') is null then        "
					+ "          ''                                                  "
					+ "         else                                                 "
					+ "          (select a08.a0801a                                  "
					+ "             from a08 a08                                     "
					+ "            where a08.A0000 = a01.a0000                       "
					+ "              and a08.a0834 = '1'                             "
					+ "              and a08.a0899 = 'true')                         "
					+ "       end) as xl,                                            "
					+ "       a0192,                                                 "
					+ "       (select code_name                                      "
					+ "          from code_value                                     "
					+ "         where code_type = 'ZB134'                            "
					+ "           and code_value.code_value = a01.a0120) a0120,      "
					+ "       a01.a0180,                                             "
					+ "       a01.TORGID,a01.TORDER                                  "
					+ "  from A01 a01, (Select (@rowNum := 0)) b                     " + "  WHERE a01.A0000 in (" + ids
					+ ") " + "  ORDER BY a01.TORGID,a01.TORDER";
		}
		List<Map<String, Object>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, Object>>();
				while (iterator.hasNext()) {

					Map<String, Object> tmp = (Map<String, Object>) iterator.next();
					list.add(tmp);

				}
			}
			String djrys = list.size() + "";

			if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")) {
				doctpl = rootPath + "gwydjbab.doc";
			} else {
				doctpl = rootPath + "czdjbab.doc";
			}

			Document doc = new Document(doctpl);
			// 向模板填充数据源
			doc.getMailMerge().executeWithRegions(new MapMailMergeDataSource(list, "expert"));
			String id = a0000s.get(0);
			// List list2 = HBUtil.getHBSession().createSQLQuery("select A0201A from a02
			// where a0000 = '"+id+"'").list();
			List list1 = session.createSQLQuery("select b0101 from b01 where b0111 = '" + result.get(0) + "'").list();
			String dw = "";
			if (list1.size() > 0) {
				dw = list1.get(0).toString();
			}
			List list2 = session.createSQLQuery("select b0227 from b01 where b0111 = '" + result.get(0) + "'").list();
			String xzbz = "";
			if (list2.size() > 0) {
				xzbz = list2.get(0).toString();
			}
			String syrs = "";
			if (DBUtil.getDBType() == DBType.ORACLE) {
				syrs = session.createSQLQuery("select count(1) from (select a0000 from a02 where a0201b like '"
						+ result.get(0) + "%' and a0255 = '1' group by a0000)").list().get(0).toString();
			} else {
				syrs = session.createSQLQuery("select count(1) from (select a0000 from a02 where a0201b like '"
						+ result.get(0) + "%' and a0255 = '1' group by a0000) as a01").list().get(0).toString();
			}

			// 填充单个数据
			doc.getMailMerge().execute(new String[] { "dw", "djrys", "xzbz", "syrs" },
					new String[] { dw, djrys, xzbz, syrs });
			String expName = "";
			if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")) {
				expName = "公务员登记备案表";
			} else {
				expName = "参照登记备案表";
			}
			File outFile = new File(expFile + expName + ".doc");
			// 写入到Word文档中
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			// 保存到输出流中
			doc.save(os, SaveFormat.DOC);
			OutputStream out = new FileOutputStream(outFile);
			out.write(os.toByteArray());
			out.close();
			if (type.equals("pdf")) {
				TestAspose2Pdf.doc2pdf(expFile + expName + ".doc", expFile + expName + ".pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void annualAssessmentForm(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 年度考核登记表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				String a0101 = (String) dataMap.get("a0101");
				doctpl = rootPath + "ndkhdjb.doc";
				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void rewardAuditForm(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 奖励审批表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				String a0101 = (String) dataMap.get("a0101");
				doctpl = rootPath + "gwyjlspb.doc";
				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void gbmc2(String type, String ids, String rootPath, String expFile) {

		ids = ids.replace("@", ",").replace("|", "'");
		String sql = LongSQL.gbmc2SQL(ids);

		System.out.println(sql);
		List<Gbmc2> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(Gbmc2.class));
			list = sqlQuery.list();
			// 会议名册
			String tempPath = rootPath + "gbhmc1.xls";
			String path = expFile;
			exportHYMC(tempPath, path, list);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void gwyZhiJiTaoZhuanDoc(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 公务员职级套转表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				String a0101 = (String) dataMap.get("a0101");
				doctpl = rootPath + "gwyzjtgb_tz.doc";
				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				os.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void dutyChangeAuditPDF(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 调任审批表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				String a0101 = (String) dataMap.get("a0101");
				doctpl = rootPath + "gwydrspb.doc";
				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void gwyImportAuditPDF(List<String> a0000s, String tpid, String type, String rootPath, String expFile) {
		String doctpl;
		Map<String, Object> dataMap;
		// 公务员录用审批表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				String a0101 = (String) dataMap.get("a0101");
				String QRZXLXX = (String) dataMap.get("qrzxlxx");
				String QRZXWXX = (String) dataMap.get("qrzxwxx");
				String ZZXLXX = (String) dataMap.get("zzxlxx");
				String ZZXWXX = (String) dataMap.get("zzxwxx");
				if (QRZXLXX.equals(QRZXWXX) && !ZZXLXX.equals(ZZXWXX)) {
					// 上一，下二
					doctpl = rootPath + "gwylyb_4.doc";
				} else if (!QRZXLXX.equals(QRZXWXX) && ZZXLXX.equals(ZZXWXX)) {
					// 上二，下一
					doctpl = rootPath + "gwylyb_3.doc";
				} else if (!QRZXLXX.equals(QRZXWXX) && !ZZXLXX.equals(ZZXWXX)) {
					// 上二，下二
					doctpl = rootPath + "gwylyb_2.doc";
				} else if (QRZXLXX.equals(QRZXWXX) && ZZXLXX.equals(ZZXWXX)) {
					// 上一，下一
					doctpl = rootPath + "gwylyb_1.doc";
				}
				doctpl = rootPath + "gwylyb_1.doc";
				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				if (type.equals("pdf")) {
					TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
							expFile + (i + 1) + "_" + a0101 + ".pdf");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param ids 人员主键
	 * @param expFile 导出路径
	 * @throws IOException
	 * @throws AppException
	 * @throws RadowException
	 */
	private void gbmc(String ids, String expFile) throws IOException, RadowException, AppException {
		// 干部名册按机构分组
		ids = ids.replace("@", ",").replace("|", "'");
		String sql = LongSQL.gbmc1SQL(ids);


		expGBMC(sql,expFile);
	}


	/**
	 * 自定义模板导出
	 *
	 */
	public List getPdfsByA000s_asposew(List<String> a0000s, String tpid, String name, String ids, List<String> result,
									   HttpServletRequest request, String rad, String bs) throws AppException {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return null;
		}
		// String rootPath = getRootPath();
		String doctpl = "";
		Map<String, Object> dataMap = null;
		List<String> paths = new ArrayList<String>();
		String expFile = ExpRar.expFile();
		// doctpl =
		// request.getSession().getServletContext().getRealPath("webOffice/word") +"\\"+
		// name;//路径和模板名
		doctpl = AppConfig.HZB_PATH + "/WebOffice/" + name;// 路径和模板名
		File file = new File(doctpl);
		if (file.exists()) {// 模板是否存在
			for (int i = 0; i < a0000s.size(); i++) {
				String a0000 = a0000s.get(i);
				// 创建Document对象，读取Word模板
				try {
					List list = new ArrayList();
					StringBuilder htmlText = new StringBuilder();
					Document doc1 = new Document(doctpl);// 常规导出
					// 增加处理简历和照片程序
					doc1.getMailMerge().setFieldMergingCallback(new HandleMergeField());
					doc1.getMailMerge().execute(new String[] { "htmlField1" }, new String[] { htmlText.toString() });
					// HandleMergeField.list2.removeAll(HandleMergeField.list2);//去掉缓存里面的所有字段
					list = HandleMergeField.list2;// 取所有字段
					list.remove("image");
					String queryCondition = "";
					if (list != null && list.size() != 0) {
						dataMap = FiledownServlet.getMapw(a0000, name, list, queryCondition, rad);
						HandleMergeField.list2.removeAll(HandleMergeField.list2);// 去掉缓存里面的所有字段
						String a0101 = (String) dataMap.get("a01_a0101");
						Document doc = new Document(doctpl);// 常规导出
						// 增加处理简历和照片程序
						doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlobzdy());
						// 向模板填充数据源
						// doc.getMailMerge().executeWithRegions(new
						// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
						StringBuffer mapkey = new StringBuffer();
						StringBuffer mapvalue = new StringBuffer();
						for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
							String key = entry.getKey();
							// System.out.println("key========================"+key);
							Object value = entry.getValue();
							if (key.equals("image")) {
								A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
								if (a57 != null) {
									byte[] image = PhotosUtil.getPhotoData(a57);
									if (image != null) {
										value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
									} else {
										value = "";
									}
								} else {
									value = "";
								}
							}
							if (StringUtil.isEmpty(value + "")) {
								value = "@";
							}
							mapkey = mapkey.append(key + "&");
							mapvalue = mapvalue.append(value + "&");
						}
						// 文本域
						String[] Flds = mapkey.toString().split("&");
						// 值
						String[] Vals = mapvalue.toString().split("&");
						for (int j = 0; j < Vals.length; j++) {
							if (Vals[j].equals("@")) {
								Vals[j] = "";
							}
						}

						// 填充单个数据
						doc.getMailMerge().execute(Flds, Vals);
						File outFile;
						if ("xz".equals(bs)) {
							outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
						} else {
							outFile = new File(expFile + (i + 1) + "_" + "xxx" + ".doc");
						}

						// 写入到Word文档中
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						// 保存到输出流中
						doc.save(os, SaveFormat.DOC);
						OutputStream out = new FileOutputStream(outFile);
						out.write(os.toByteArray());
						out.close();
					} else {
						Document doc = new Document(doctpl);// 常规导出
						// 增加处理简历和照片程序
						// 向模板填充数据源
						// doc.getMailMerge().executeWithRegions(new
						// MapMailMergeDataSource(getMapList2(a0000), "Employees"));

						// 填充单个数据
						File outFile = new File(expFile + "@" + name + ".doc");
						// 写入到Word文档中
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						// 保存到输出流中
						doc.save(os, SaveFormat.DOC);
						OutputStream out = new FileOutputStream(outFile);
						out.write(os.toByteArray());
						out.close();
						paths.add(expFile + "@" + name + ".doc");
						return paths;
					}

					/*
					 * if(type.equals("pdf")){ TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" +
					 * a0101 +".doc", expFile + (i + 1) + "_" + a0101 +".pdf"); }
					 */
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			// System.out.println("================================"+"模板不存在");
			return null;
		}

		// 5.删除目录下非PDF文件，汇总各个PDF文件路径，并返回
		File dec = new File(expFile);
		File[] files = dec.listFiles();
		paths.add(expFile);
		return paths;
	}

	public String getPdfsByA000s_aspose(List<String> a0000s) throws AppException {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return "";
		}
		String rootPath = getRootPath();
		String doctpl = "";
		Map<String, Object> dataMap = null;
		String expFile = ExpRar.expFile();
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000);
				String QRZXLXX = (String) dataMap.get("qrzxlxx");
				String QRZXWXX = (String) dataMap.get("qrzxwxx");
				String ZZXLXX = (String) dataMap.get("zzxlxx");
				String ZZXWXX = (String) dataMap.get("zzxwxx");
				String a0101 = (String) dataMap.get("a0101");
				if (QRZXLXX.equals(QRZXWXX) && !ZZXLXX.equals(ZZXWXX)) {
					// 上一，下二
					doctpl = rootPath + "gbrmspb_4.doc";
				} else if (!QRZXLXX.equals(QRZXWXX) && ZZXLXX.equals(ZZXWXX)) {
					// 上二，下一
					doctpl = rootPath + "gbrmspb_3.doc";
				} else if (!QRZXLXX.equals(QRZXWXX) && !ZZXLXX.equals(ZZXWXX)) {
					// 上二，下二
					doctpl = rootPath + "gbrmspb_2.doc";
				} else if (QRZXLXX.equals(QRZXWXX) && ZZXLXX.equals(ZZXWXX)) {
					// 上一，下一
					doctpl = rootPath + "gbrmspb_1.doc";
				}
				doctpl = rootPath + "gbrmspb_1.doc";
				Document doc = new Document(doctpl);
				// 增加处理简历程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));

				String Image = "";
				A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
				if (a57 != null) {
					byte[] image = PhotosUtil.getPhotoData(a57);
					if (image != null) {
						Image = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
					} else {
						Image = "";
					}
				} else {
					Image = "";
				}
				dataMap.put("image", Image);

				Object[] keys = dataMap.keySet().toArray();
				Object[] valus = dataMap.values().toArray();

				// 文本域
				String[] Flds = new String[keys.length];
				// 值
				String[] Vals = new String[valus.length];
				for (int j = 0; j < valus.length; j++) {
					if (keys[j] != null) {
						Flds[j] = keys[j].toString();
					} else {
						Flds[j] = "";
					}
					if (valus[j] != null) {
						Vals[j] = valus[j].toString();
					} else {
						Vals[j] = "";
					}
				}

				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据
				doc.getMailMerge().execute(Flds, Vals);
				File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
				TestAspose2Pdf.doc2pdf(expFile + (i + 1) + "_" + a0101 + ".doc",
						expFile + (i + 1) + "_" + a0101 + ".pdf");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 5.删除目录下非PDF文件，汇总各个PDF文件路径，并返回
		File dec = new File(expFile);
		File[] files = dec.listFiles();
		CommonQueryBS.systemOut("------------->删除目录下非PDF文件开始:" + DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		for (File f0 : files) {
			int indexPoint = f0.getName().lastIndexOf(".") + 1;
			String extStr = f0.getName().length() >= indexPoint ? f0.getName().substring(indexPoint) : "";
			if (!extStr.equalsIgnoreCase("pdf")) {
				f0.delete();
			}
		}
		CommonQueryBS.systemOut("------------->删除目录下非PDF文件结束:" + DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		return expFile;
	}

	private static class HandleMergeFieldFromBlob implements IFieldMergingCallback {
		public void fieldMerging(FieldMergingArgs args) throws Exception {
			if (args.getDocumentFieldName().equals("a1701")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, true);
			} else if (args.getDocumentFieldName().equals("image")) {
				String image = args.getFieldValue().toString();
				if (!StringUtil.isEmpty(image)) {
					DocumentBuilder builder = new DocumentBuilder(args.getDocument());
					builder.moveToMergeField(args.getFieldName());
					builder.insertImage(args.getFieldValue().toString(), 105, 145);
				}
			} else if (args.getDocumentFieldName().equals("a0192a")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxlxx")) {// 在职学历信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("a14z101")) {// 奖惩情况字体缩放
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxwxx")) {// 在职学位信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxlxx")) {// 全日制学历信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxwxx")) {// 全日制学位信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxlxw")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxlxw")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("a0196")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("fwh")) {
				// 使用DocumentBuilder处理发文
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			}
		}

		public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {

		}
	}








	public static void main(String[] args) {
		String demo = "das,daeda&sdadasd$&#&ad";
		demo = demo.replaceAll("&", "");
		System.out.println(demo);

	}

	private static class HandleMergeFieldFromBlobzdy implements IFieldMergingCallback {
		public void fieldMerging(FieldMergingArgs args) throws Exception {
			if (args.getDocumentFieldName().equals("A01_A1701")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, true);
			} else if (args.getDocumentFieldName().equals("image")) {
				String image = args.getFieldValue().toString();
				if (!StringUtil.isEmpty(image)) {
					DocumentBuilder builder = new DocumentBuilder(args.getDocument());
					builder.moveToMergeField(args.getFieldName());
					// builder.insertImage(args.getFieldValue().toString(),105,145);
					builder.insertImage(args.getFieldValue().toString(), 84, 105);
				}
			} /*
			 * else if (args.getDocumentFieldName().equals("a0192a")){ //
			 * 使用DocumentBuilder处理简历 DocumentBuilder builder = new
			 * DocumentBuilder(args.getDocument());
			 * builder.moveToMergeField(args.getFieldName()); BbUtils.formatArray(builder,
			 * args, true); }
			 */
			else if (args.getDocumentFieldName().equals("zzxlxx")) {// 在职学历信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxwxx")) {// 在职学位信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxlxx")) {// 全日制学历信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxwxx")) {// 全日制学位信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			}
		}

		public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {

		}
	}

	/**
	 * 取word域所有字段
	 */
	private static class HandleMergeField implements IFieldMergingCallback {

		static List list2 = new ArrayList();

		public HandleMergeField() {
			list2.removeAll(list2);
		}

		public void fieldMerging(FieldMergingArgs args) throws Exception {
			String listAll = args.getDocumentFieldName();
			if (listAll != null || listAll != "") {
				list2.add(listAll);
			}

		}

		public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {

		}
	}

	/**
	 * 加空格处理
	 */
	public static String Space(String Value) {
		String text_Value = "";
		if (Value != null) {
			if (Value.length() == 2) {
				StringBuffer sb = new StringBuffer();
				int length = Value.length();
				for (int i1 = 0; i1 < length; i1++) {
					if (length - i1 <= 2) { // 防止ArrayIndexOutOfBoundsException
						sb.append(Value.substring(i1, i1 + 1)).append("  ");
						sb.append(Value.substring(i1 + 1));
						break;
					}
					sb.append(Value.substring(i1, i1 + 1)).append("  ");
				}
				text_Value = sb.toString();
			} else {
				text_Value = Value;
			}
		}
		return text_Value;
	}


	/**
	 * 传入sql生成 会议名册
	 * @param tempPath
	 * @param path
	 * @param sql
	 * @throws AppException
	 */
	public void exportHYMC(String tempPath, String path, String sql) throws AppException {
		exportHYMC(tempPath,path,ExpTPB.querybyid_gbmc2(sql));
	}
	
	/**
	 * 传入sql生成 杭州会议名册 
	 * @param tempPath
	 * @param path
	 * @param sql
	 * @throws AppException
	 */
	public void exportHYMC_HZ(String tempPath, String path, String sql,String name) throws AppException {
		exportHYMC_HZ(tempPath,path,ExpTPB.querybyid_gbmc3(sql),name);
	}
	
	/**
	 * 生成 会议名册
	 *
	 * @param tempPath 模板地址
	 * @param path 文件生成地址
	 * @param list 干部名册数据集合
	 */
	@SuppressWarnings("deprecation")
	public void exportHYMC_HZ(String tempPath, String path, List<Gbmc> list, String name) {

		// 读取excel模板，并复制到新文件中供写入和下载
		File newFile = createNewFileForName(name,tempPath, path);
		InputStream is = null;
		try {
			// 将excel文件转为输入流
			is = new FileInputStream(newFile);
			// 创建个workbook，
			/* XSSFWorkbook workbook = new XSSFWorkbook(is); */
			HSSFWorkbook workbook = new HSSFWorkbook(is); //
			// 获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 打印设置
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);// 设置A4打印
			printSetup.setLandscape(true);// 设置横向打印

			sheet.setHorizontallyCenter(true);// 水平居中
			/* 设置字体样式 */
			Font font = workbook.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 11);// 设置字体大小

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			// 通用单元格样式
			CellStyle Centered = workbook.createCellStyle();
			Centered.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
			Centered.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			Centered.setWrapText(true); // 换行
			Centered.setFont(font);
			Centered.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			Centered.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			Centered.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			Centered.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			CellStyle[] cellStyles = ExeclUtil.getFontSizeStyle(workbook, (short) 3, (short) 11, "宋体",
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);
			// 加粗 //居中
			CellStyle[] cellStyles2 = ExeclUtil.getFontSizeStyle(workbook, (short) 3, (short) 11, "宋体",
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER_SELECTION);

			Map<String, CellStyle[]> StyleMap = new HashMap<String, CellStyle[]>();

			StyleMap.put("normal", cellStyles);
			StyleMap.put("comments", cellStyles2);

			OutputStream fos = new FileOutputStream(newFile);



			for (int i = 0; i < list.size(); i++) {

				insertRowData_HZ(list.get(i), i + 4, sheet, Centered, StyleMap);

			}
			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 生成 会议名册
	 *
	 * @param tempPath 模板地址
	 * @param path 文件生成地址
	 * @param list 干部名册数据集合
	 */
	@SuppressWarnings("deprecation")
	public void exportHYMC(String tempPath, String path, List<Gbmc2> list) {

		// 读取excel模板，并复制到新文件中供写入和下载
		File newFile = createNewFile(tempPath, path);
		InputStream is = null;
		try {
			// 将excel文件转为输入流
			is = new FileInputStream(newFile);
			// 创建个workbook，
			/* XSSFWorkbook workbook = new XSSFWorkbook(is); */
			HSSFWorkbook workbook = new HSSFWorkbook(is); //
			// 获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 打印设置
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);// 设置A4打印
			printSetup.setLandscape(true);// 设置横向打印

			sheet.setHorizontallyCenter(true);// 水平居中
			/* 设置字体样式 */
			Font font = workbook.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 11);// 设置字体大小

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			// 通用单元格样式
			CellStyle Centered = workbook.createCellStyle();
			Centered.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
			Centered.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			Centered.setWrapText(true); // 换行
			Centered.setFont(font);
			Centered.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			Centered.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			Centered.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			Centered.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			CellStyle[] cellStyles = ExeclUtil.getFontSizeStyle(workbook, (short) 3, (short) 11, "宋体",
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);
			// 加粗 //居中
			CellStyle[] cellStyles2 = ExeclUtil.getFontSizeStyle(workbook, (short) 3, (short) 11, "宋体",
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER_SELECTION);

			Map<String, CellStyle[]> StyleMap = new HashMap<String, CellStyle[]>();

			StyleMap.put("normal", cellStyles);
			StyleMap.put("comments", cellStyles2);

			OutputStream fos = new FileOutputStream(newFile);



			for (int i = 0; i < list.size(); i++) {

				insertRowData(list.get(i), i + 4, sheet, Centered, StyleMap);

			}
			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 会议名册插入行逻辑
	 *
	 * @param gbmc      当前行数据集合
	 * @param i        当前行号
	 * @param sheet    当前页
	 * @param Centered 每个单元格的默认样式
	 * @param styleMap 用于字数超过后调整
	 */
	private void insertRowData_HZ(Gbmc gbmc, int i, Sheet sheet, CellStyle Centered,
							   Map<String, CellStyle[]> styleMap) {

		Row row = sheet.createRow(i);
		row.setHeightInPoints(75);
		Cell cell = row.createCell(0);
		cell.setCellStyle(Centered);
		cell.setCellValue((String) gbmc.getA0101());

		Cell cell1 = row.createCell(1);
		cell1.setCellValue((String) gbmc.getA0192a());
		ExeclUtil.setFontSize(cell1, styleMap.get("normal"));

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(Centered);
		cell2.setCellValue((String) gbmc.getA0104());

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(Centered);
		cell3.setCellValue((String) gbmc.getA0117());
		
		Cell cell9 = row.createCell(4);
		cell9.setCellStyle(Centered);
		cell9.setCellValue((String) gbmc.getA0107());

		Cell cell4 = row.createCell(5);
		cell4.setCellStyle(Centered);
		cell4.setCellValue((String) gbmc.getA0111a());
		
		Cell cell10 = row.createCell(6);
		cell10.setCellStyle(Centered);
		cell10.setCellValue((String) gbmc.getA0134());

		Cell cell11 = row.createCell(7);
		cell11.setCellStyle(Centered);
		cell11.setCellValue((String) gbmc.getA0140());

		Cell cell5 = row.createCell(8);
		cell5.setCellStyle(Centered);
		cell5.setCellValue((String) gbmc.getQrzxl());

		Cell cell6 = row.createCell(9);

		cell6.setCellValue((String) gbmc.getQrzxlxx());
		ExeclUtil.setFontSize(cell6, styleMap.get("normal"));

		Cell cell7 = row.createCell(10);
		cell7.setCellStyle(Centered);
		cell7.setCellValue((String) gbmc.getZzxl());

		Cell cell8 = row.createCell(11);

		cell8.setCellValue((String) gbmc.getZzxlxx());
		ExeclUtil.setFontSize(cell8, styleMap.get("normal"));

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(Centered);
		cell12.setCellValue((String) gbmc.getA0196());

		Cell cell22 = row.createCell(13);
		cell22.setCellStyle(Centered);
		cell22.setCellValue((String) gbmc.getA0192f());

		Cell cell23 = row.createCell(14);
		cell23.setCellStyle(Centered);
		cell23.setCellValue((String) gbmc.getA0288());

		Cell cell13 = row.createCell(15);
		cell13.setCellStyle(Centered);
		cell13.setCellValue((String) gbmc.getA0192c());

		Cell cell14 = row.createCell(16);
		cell14.setCellStyle(Centered);
		cell14.setCellValue("");

	}


	/**
	 * 会议名册插入行逻辑
	 *
	 * @param gbmc      当前行数据集合
	 * @param i        当前行号
	 * @param sheet    当前页
	 * @param Centered 每个单元格的默认样式
	 * @param styleMap 用于字数超过后调整
	 */
	private void insertRowData(Gbmc2 gbmc, int i, Sheet sheet, CellStyle Centered,
							   Map<String, CellStyle[]> styleMap) {

		Row row = sheet.createRow(i);
		row.setHeightInPoints(75);
		Cell cell = row.createCell(0);
		cell.setCellStyle(Centered);
		cell.setCellValue((String) gbmc.getA0101());

		Cell cell1 = row.createCell(1);
		cell1.setCellValue((String) gbmc.getA0192a());
		ExeclUtil.setFontSize(cell1, styleMap.get("normal"));

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(Centered);
		cell2.setCellValue((String) gbmc.getA0104());

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(Centered);
		cell3.setCellValue((String) gbmc.getA0117());

		Cell cell4 = row.createCell(4);
		cell4.setCellStyle(Centered);
		cell4.setCellValue((String) gbmc.getA0111a());

		Cell cell5 = row.createCell(5);
		cell5.setCellStyle(Centered);
		cell5.setCellValue((String) gbmc.getQrzxl());

		Cell cell6 = row.createCell(6);

		cell6.setCellValue((String) gbmc.getQrzxlxx());
		ExeclUtil.setFontSize(cell6, styleMap.get("normal"));

		Cell cell7 = row.createCell(7);
		cell7.setCellStyle(Centered);
		cell7.setCellValue((String) gbmc.getZzxl());

		Cell cell8 = row.createCell(8);

		cell8.setCellValue((String) gbmc.getZzxlxx());
		ExeclUtil.setFontSize(cell8, styleMap.get("normal"));

		Cell cell9 = row.createCell(9);
		cell9.setCellStyle(Centered);
		cell9.setCellValue((String) gbmc.getA0107());

		Cell cell10 = row.createCell(10);
		cell10.setCellStyle(Centered);
		cell10.setCellValue((String) gbmc.getA0134());

		Cell cell11 = row.createCell(11);
		cell11.setCellStyle(Centered);
		cell11.setCellValue((String) gbmc.getA0140());

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(Centered);
		cell12.setCellValue((String) gbmc.getA0192f());

		Cell cell13 = row.createCell(13);
		cell13.setCellStyle(Centered);
		cell13.setCellValue((String) gbmc.getA0192c());

		Cell cell14 = row.createCell(14);
		cell14.setCellStyle(Centered);
		cell14.setCellValue((String) gbmc.getComments());

	}
	/**
	 * 干部名册插入行逻辑
	 *
	 * @param gbmc      当前行数据集合
	 * @param i        当前行号
	 * @param sheet    当前页
	 * @param Centered 每个单元格的默认样式
	 * @param styleMap 用于字数超过后调整
	 */
	private void insertRowData(Gbmc1 gbmc, int i, Sheet sheet, CellStyle Centered,
							   Map<String, CellStyle[]> styleMap) {

		Row row = sheet.createRow(i);
		row.setHeightInPoints(75);
		Cell cell = row.createCell(0);
		cell.setCellStyle(Centered);
		cell.setCellValue((String) gbmc.getA0101());

		Cell cell1 = row.createCell(1);
		cell1.setCellValue((String) gbmc.getA0192a());
		ExeclUtil.setFontSize(cell1, styleMap.get("normal"));

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(Centered);
		cell2.setCellValue((String) gbmc.getA0104());

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(Centered);
		cell3.setCellValue((String) gbmc.getA0117());

		Cell cell4 = row.createCell(4);
		cell4.setCellStyle(Centered);
		cell4.setCellValue((String) gbmc.getA0111a());

		Cell cell5 = row.createCell(5);
		cell5.setCellStyle(Centered);
		cell5.setCellValue((String) gbmc.getQrzxl());

		Cell cell6 = row.createCell(6);

		cell6.setCellValue((String) gbmc.getQrzxlxx());
		ExeclUtil.setFontSize(cell6, styleMap.get("normal"));

		Cell cell7 = row.createCell(7);
		cell7.setCellStyle(Centered);
		cell7.setCellValue((String) gbmc.getZzxl());

		Cell cell8 = row.createCell(8);

		cell8.setCellValue((String) gbmc.getZzxlxx());
		ExeclUtil.setFontSize(cell8, styleMap.get("normal"));

		Cell cell9 = row.createCell(9);
		cell9.setCellStyle(Centered);
		cell9.setCellValue((String) gbmc.getA0107());

		Cell cell10 = row.createCell(10);
		cell10.setCellStyle(Centered);
		cell10.setCellValue((String) gbmc.getA0134());

		Cell cell11 = row.createCell(11);
		cell11.setCellStyle(Centered);
		cell11.setCellValue((String) gbmc.getA0140());

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(Centered);
		cell12.setCellValue((String) gbmc.getA0192f());

		Cell cell13 = row.createCell(13);
		cell13.setCellStyle(Centered);
		cell13.setCellValue((String) gbmc.getA0192c());

		Cell cell14 = row.createCell(14);
		cell14.setCellStyle(Centered);
		cell14.setCellValue((String) gbmc.getComments());

	}
	
	/**
	 * 读取excel模板，并复制到新文件中供写入和下载
	 *
	 * @return
	 */
	private File createNewFileForName(String name,String tempPath, String rPath) {
//         读取模板，并赋值到新文件
		File file = new File(tempPath);
//         rPath: 新的文件名
		String newFileName = name+".xls";
		// 判断路径是否存在
		File dir = new File(rPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(rPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 * 读取excel模板，并复制到新文件中供写入和下载
	 *
	 * @return
	 */
	private File createNewFile(String tempPath, String rPath) {
//         读取模板，并赋值到新文件
		File file = new File(tempPath);
//         rPath: 新的文件名
		String newFileName = "干部花名册（一人一行）.xls";
		// 判断路径是否存在
		File dir = new File(rPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(rPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	public File createNewFile1(String tempPath, String rPath) {
//      读取模板，并赋值到新文件
		File file = new File(tempPath);
//      rPath: 新的文件名
		String newFileName = null;

		newFileName = "人员信息.xls";

		// 判断路径是否存在
		File dir = new File(rPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(rPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	public File createNewFile2(String tempPath, String rPath) {
//      读取模板，并赋值到新文件
		File file = new File(tempPath);
//      rPath: 新的文件名
		String newFileName = null;

		newFileName = "导入失败信息.xls";

		// 判断路径是否存在
		File dir = new File(rPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(rPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	public File createNewFile3(String tempPath, String rPath) {
//      读取模板，并赋值到新文件
		File file = new File(tempPath);
//      rPath: 新的文件名
		String newFileName = null;

		newFileName = "导入失败人员模板.xls";

		// 判断路径是否存在
		File dir = new File(rPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(rPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 * 复制文件
	 *
	 * @param s 源文件
	 * @param t 复制到的新文件
	 */
	private void fileChannelCopy(File s, File t) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(s), 1024);
				out = new BufferedOutputStream(new FileOutputStream(t), 1024);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static void TitleLine(HSSFSheet sheet, CellStyle title, CellStyle centered, int rowNum, String titleStr,HSSFWorkbook wb) {
		// 填充每个部门的位置，设置格式
		HSSFRow row = sheet.createRow(rowNum);
		//单位名称
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue(titleStr);
		if (titleStr.length() < 45)
			cell0.setCellStyle(title);
		row.setHeightInPoints(21); //行高21
		//表头第一行
		row = sheet.createRow(rowNum +1);
		// 第二行
		HSSFRow row1 = sheet.createRow(rowNum + 2);

		row.setHeightInPoints(20);
		row1.setHeightInPoints(30);

		HSSFCell cell1 = row1.createCell(5);
		cell1.setCellValue("学历");
		cell1.setCellStyle(centered);

		cell1 = row1.createCell(6);
		cell1.setCellValue("毕业院校\r\n" + "及 专 业");
		cell1.setCellStyle(centered);

		cell1 = row1.createCell(7);
		cell1.setCellValue("学历");
		cell1.setCellStyle(centered);

		cell1 = row1.createCell(8);
		cell1.setCellValue("毕业院校\r\n" + "及 专 业");
		cell1.setCellStyle(centered);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("姓  名");
		cell.setCellStyle(centered);

		cell = row.createCell(1);
		cell.setCellValue("现任职务");
		cell.setCellStyle(centered);

		cell = row.createCell(2);
		cell.setCellValue("性别");
		cell.setCellStyle(centered);

		cell = row.createCell(3);
		cell.setCellValue("民族");
		cell.setCellStyle(centered);

		cell = row.createCell(4);
		cell.setCellValue("籍贯");
		cell.setCellStyle(centered);

		cell = row.createCell(5);
		cell.setCellValue("全日制教育");
		cell.setCellStyle(centered);

		cell = row.createCell(7);
		cell.setCellValue("在职教育");
		cell.setCellStyle(centered);

		cell = row.createCell(9);
		cell.setCellValue("出生\r\n" + "年月");
		cell.setCellStyle(centered);

		cell = row.createCell(10);
		cell.setCellValue("参加工\r\n" + "作时间");
		cell.setCellStyle(centered);

		cell = row.createCell(11);
		cell.setCellValue("入党\r\n" + "时间");
		cell.setCellStyle(centered);

		cell = row.createCell(12);
		cell.setCellValue("任现职\r\n" + "时  间");
		cell.setCellStyle(centered);

		cell = row.createCell(13);
		cell.setCellValue("任现职\r\n" + "级时间");
		cell.setCellStyle(centered);

		cell = row.createCell(14);
		cell.setCellValue("备 注");
		cell.setCellStyle(centered);

		int weizhi1 = rowNum + 1;
		int weizh2 = rowNum + 2;
		CellRangeAddress region = null;

		for (int a = 0; a < 15; a++) {

			if (a == 5 || a == 6 || a == 7 || a == 8) {
				continue;
			}
			region = new CellRangeAddress(weizhi1, weizh2, a, a);
			sheet.addMergedRegion(region);
			setBorderStyle(HSSFCellStyle.BORDER_THIN, region, sheet, wb); // 给合并过的单元格加边框
		}

		CellRangeAddress region2 = new CellRangeAddress(weizhi1, weizhi1, 5, 6);
		sheet.addMergedRegion(region2);
		setBorderStyle(HSSFCellStyle.BORDER_THIN, region2, sheet, wb); // 给合并过的单元格加边框
		CellRangeAddress region3 = new CellRangeAddress(weizhi1, weizhi1, 7, 8);
		sheet.addMergedRegion(region3);
		setBorderStyle(HSSFCellStyle.BORDER_THIN, region3, sheet, wb); // 给合并过的单元格加边框

	}

	@SuppressWarnings("deprecation")
	private static CellStyle style(HSSFWorkbook wb, String result) {
		CellStyle re = null;

		/* 设置字体样式 */
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示

		Font fontTitle = wb.createFont();
		fontTitle.setFontName("黑体");
		fontTitle.setFontHeightInPoints((short) 15);// 设置字体大小
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示

		if (result.equals("centered")) {
			re = wb.createCellStyle();
			re.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
			re.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			re.setWrapText(true); // 换行
			re.setFont(font);
			re.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			re.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			re.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			re.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		} else if (result.equals("left")) {
			re = wb.createCellStyle();
			re.setAlignment(CellStyle.ALIGN_LEFT); // 左边
			re.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			re.setWrapText(true); // 换行
			re.setFont(font);
			re.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			re.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			re.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			re.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		} else if (result.equals("title")) {
			re = wb.createCellStyle();
			re.setAlignment(CellStyle.ALIGN_LEFT); // 左边
			re.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			re.setFont(fontTitle);
			// re.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			// re.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			// re.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			// re.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		}
		return re;
	}



	/**
	 * 复制行
	 *
	 * @param startRowIndex 起始行
	 * @param endRowIndex   结束行
	 * @param pPosition     目标起始行位置
	 */
	public void copyRows(int startRow, int endRow, int pPosition, Sheet currentSheet) {
		int pStartRow = startRow - 1;
		int pEndRow = endRow - 1;
		int targetRowFrom;
		int targetRowTo;
		int columnCount;
		CellRangeAddress region = null;
		int i;
		int j;
		if (pStartRow == -1 || pEndRow == -1) {
			return;
		}
		for (i = 0; i < currentSheet.getNumMergedRegions(); i++) {
			region = currentSheet.getMergedRegion(i);
			if ((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
				targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
				targetRowTo = region.getLastRow() - pStartRow + pPosition;
				CellRangeAddress newRegion = region.copy();
				newRegion.setFirstRow(targetRowFrom);
				newRegion.setFirstColumn(region.getFirstColumn());
				newRegion.setLastRow(targetRowTo);
				newRegion.setLastColumn(region.getLastColumn());
				currentSheet.addMergedRegion(newRegion);
			}
		}
		for (i = pStartRow; i <= pEndRow; i++) {
			Row sourceRow = currentSheet.getRow(i);
			columnCount = sourceRow.getLastCellNum();
			if (sourceRow != null) {
				Row newRow = currentSheet.createRow(pPosition - pStartRow + i);
				newRow.setHeight(sourceRow.getHeight());
				for (j = 0; j < columnCount; j++) {
					Cell templateCell = sourceRow.getCell(j);
					if (templateCell != null) {
						Cell newCell = newRow.createCell(j);
						copyCell(templateCell, newCell);
					}
				}
			}
		}
	}

	private void copyCell(Cell templateCell, Cell newCell) {
		newCell.setCellStyle(templateCell.getCellStyle());
		if (templateCell.getCellComment() != null) {
			newCell.setCellComment(templateCell.getCellComment());
		}
		int srcCellType = templateCell.getCellType();
		newCell.setCellType(srcCellType);
		if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(templateCell)) {
				newCell.setCellValue(templateCell.getDateCellValue());
			} else {
				newCell.setCellValue(templateCell.getNumericCellValue());
			}
		} else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
			newCell.setCellValue(templateCell.getRichStringCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
			// nothing21
		} else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
			newCell.setCellValue(templateCell.getBooleanCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
			newCell.setCellErrorValue(templateCell.getErrorCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
			newCell.setCellFormula(templateCell.getCellFormula());
		} else { // nothing29

		}
	}

	//不足五个时候用null 补充
	public void supplement(List<Gbmc1> datas, int num) {

		for (int a = 0; a < num; a++) {
			datas.add(new Gbmc1());
		}


	}

	public void insertDataOne(List<Map<String, Object>> list, int j, Row row, Sheet sheet, int number,
							  CellStyle Centered, CellStyle left, CellStyle result, Workbook workbook, int count1) {
		// 根据excel模板格式写入数据....
		Map<String, Object> map = list.get(j);

		row = sheet.createRow(number + count1);
		row.setHeightInPoints(75);
		Cell cell = row.createCell(0);
		cell.setCellStyle(Centered);
		cell.setCellValue((String) map.get("a0101"));

		Cell cell1 = row.createCell(1);
		// cell1.setCellStyle(left);
		cell1.setCellValue((String) map.get("a0192a"));
		ExeclUtil.setFontSize(cell1);

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(Centered);
		cell2.setCellValue((String) map.get("xb"));

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(Centered);
		cell3.setCellValue((String) map.get("a0117"));

		Cell cell4 = row.createCell(4);
		cell4.setCellStyle(Centered);
		cell4.setCellValue((String) map.get("a0111a"));

		Cell cell5 = row.createCell(5);
		cell5.setCellStyle(Centered);
		cell5.setCellValue((String) map.get("qrzxl"));

		Cell cell6 = row.createCell(6);
		cell6.setCellValue((String) map.get("qrzxlxx"));
		ExeclUtil.setFontSize(cell6);

		Cell cell7 = row.createCell(7);
		cell7.setCellStyle(Centered);
		cell7.setCellValue((String) map.get("zzxl"));

		Cell cell8 = row.createCell(8);
		cell8.setCellValue((String) map.get("zzxwxx"));
		ExeclUtil.setFontSize(cell8);

		Cell cell9 = row.createCell(9);
		cell9.setCellStyle(Centered);
		cell9.setCellValue((String) map.get("csrq"));

		Cell cell10 = row.createCell(10);
		cell10.setCellStyle(Centered);
		cell10.setCellValue((String) map.get("a0140"));

		Cell cell11 = row.createCell(11);
		cell11.setCellStyle(Centered);
		cell11.setCellValue((String) map.get("cjgz"));

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(Centered);
		cell12.setCellValue((String) map.get("a0192f"));

		Cell cell13 = row.createCell(13);
		cell13.setCellStyle(Centered);
		cell13.setCellValue((String) map.get("a0192c"));

		Cell cell14 = row.createCell(14);
		cell14.setCellStyle(Centered);
		cell14.setCellValue((String) map.get("a0180"));
	}

	public void insertDataTWO(List<Map<String, Object>> list, int j, Row row, Sheet sheet, int number,
							  CellStyle Centered, CellStyle left, CellStyle result, Workbook workbook, int count1) {
		// 根据excel模板格式写入数据....
		Map<String, Object> map = list.get(j);
		row = sheet.createRow(number + count1);
		row.setHeightInPoints(75);
		Cell cell = row.createCell(0);
		cell.setCellStyle(Centered);
		cell.setCellValue((String) map.get("a0101"));

		Cell cell1 = row.createCell(1);
		// cell1.setCellStyle(left);
		cell1.setCellValue((String) map.get("a0192a"));
		ExeclUtil.setFontSize(cell1);

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(Centered);
		cell2.setCellValue((String) map.get("xb"));

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(Centered);
		cell3.setCellValue((String) map.get("a0117"));

		Cell cell4 = row.createCell(4);
		cell4.setCellStyle(Centered);
		cell4.setCellValue((String) map.get("a0111a"));

		Cell cell5 = row.createCell(5);
		cell5.setCellStyle(Centered);
		cell5.setCellValue((String) map.get("qrzxl"));

		Cell cell6 = row.createCell(6);
		cell6.setCellValue((String) map.get("qrzxlxx"));
		ExeclUtil.setFontSize(cell6);

		Cell cell7 = row.createCell(7);
		cell7.setCellStyle(Centered);
		cell7.setCellValue((String) map.get("zzxl"));

		Cell cell8 = row.createCell(8);

		cell8.setCellValue((String) map.get("zzxlxx"));
		ExeclUtil.setFontSize(cell8);

		Cell cell9 = row.createCell(9);
		cell9.setCellStyle(Centered);
		cell9.setCellValue((String) map.get("a0107"));

		Cell cell10 = row.createCell(10);
		cell10.setCellStyle(Centered);
		cell10.setCellValue((String) map.get("a0134"));

		Cell cell11 = row.createCell(11);
		cell11.setCellStyle(Centered);
		cell11.setCellValue((String) map.get("a0140"));

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(Centered);
		cell12.setCellValue((String) map.get("a0192f"));

		Cell cell13 = row.createCell(13);
		cell13.setCellStyle(Centered);
		cell13.setCellValue((String) map.get("a0192c"));

		Cell cell14 = row.createCell(14);
		cell14.setCellStyle(Centered);
		cell14.setCellValue((String) map.get("comments"));
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	public void expGBMC(String sql, String expFile) throws IOException, RadowException, AppException {

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook(this.getClass().getResourceAsStream("gbmc1.xls"));

		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.getSheet("Sheet1");

		// 打印设置
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);// 设置A4打印
		printSetup.setLandscape(true);// 设置横向打印
		// printSetup.setFooterMargin(1);
		sheet.setHorizontallyCenter(true);// 水平居中

		// 设置样式
		CellStyle title = style(wb, "title");

		CellStyle Ce = style(wb, "centered");
		CellStyle left = style(wb, "left");

		// 加粗 //居左
		CellStyle[] cellStyles = ExeclUtil.getFontSizeStyle(wb, (short) 3, (short) 11, "宋体", HSSFFont.BOLDWEIGHT_BOLD,
				HSSFCellStyle.ALIGN_LEFT);
		// 加粗 //居中
		CellStyle[] cellStyles2 = ExeclUtil.getFontSizeStyle(wb, (short) 3, (short) 11, "宋体", HSSFFont.BOLDWEIGHT_BOLD,
				HSSFCellStyle.ALIGN_CENTER_SELECTION);

		CellStyle[] cellStyles3 = ExeclUtil.getFontSizeStyle(wb, (short) 3, (short) 15, "黑体", HSSFFont.BOLDWEIGHT_BOLD,
				HSSFCellStyle.ALIGN_LEFT);
		Map<String, CellStyle[]> StyleMap = new HashMap<String, CellStyle[]>();
		StyleMap.put("normal", cellStyles);
		StyleMap.put("comments", cellStyles2);
		StyleMap.put("title", cellStyles3);
		// 集合分组
		Map<String, List<Gbmc1>> groupList = ExpTPB.querybyid_gbmc(sql);
		// 填充数据
		Set<Entry<String,List<Gbmc1>>> entrySet = groupList.entrySet();
		int rowNum = 0;
		Iterator<Entry<String, List<Gbmc1>>> iterator = entrySet.iterator();
		for(int i = 0 ; iterator.hasNext() ; i++)
		{
			Entry<String, List<Gbmc1>> temp = iterator.next();
			String key = temp.getKey();
			String[] split = key.split("&");
			if(split.length>0)
				key = split[split.length-1];
			List<Gbmc1> list = temp.getValue();
			if(list!=null&&list.size()%5!=0)
				// 不足五条 null填充
				supplement(list,5-(list.size()%5));
			//首先插入一条标题
			TitleLine(sheet, title, Ce, rowNum, key, wb);
			rowNum=rowNum+3;
			for (int j = 0; j < list.size(); j++) {
				insertRowData(list.get(j), rowNum, sheet, Ce, StyleMap);
				rowNum++;
				if(j!= list.size()-1&&(j+1)%5==0)//没五条插入一条标题
				{
					TitleLine(sheet, title, Ce, rowNum, key, wb);
					rowNum=rowNum+3;
				}
			}
		}

		FileOutputStream fout = new FileOutputStream(expFile + "干部花名册（按机构分组）.xls");
		wb.write(fout);
		fout.close();
		try {
			AsposeExcelToPdf.pdfToPdf(expFile + "干部花名册（按机构分组）.xls");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * 给合并的单元设置边框
	 *
	 *
	 */
	@SuppressWarnings("deprecation")
	public static void setBorderStyle(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb) {
		RegionUtil.setBorderBottom(border, region, sheet, wb);// 下边框
		RegionUtil.setBorderLeft(border, region, sheet, wb); // 左边框
		RegionUtil.setBorderRight(border, region, sheet, wb); // 右边框
		RegionUtil.setBorderTop(border, region, sheet, wb); // 上边框
	}

	public void insertDataThere(List list, int i, HSSFRow row, HSSFSheet sheet, int weizhi, CellStyle centered,
								CellStyle left, HSSFWorkbook wb, CellStyle result, int n, Map<String, CellStyle[]> styleMap) {
		Map<String, Object> map = (Map<String, Object>) list.get(i);
		if (i < 5) {
			// add zepeng 20191110 直接被算术题算傻了，增加以下代码填充同事挖的算数坑
			int rownum = weizhi + 3 + i;
			while (sheet.getRow(rownum) != null) {
				rownum++;
			}
			row = sheet.createRow(rownum);

		} else {
			// add zepeng 20191110 直接被算术题算傻了，增加以下代码填充同事挖的算数坑
			int rownum = weizhi + 3 + i + 3 * n;
			while (sheet.getRow(rownum) != null) {
				rownum++;
			}
			row = sheet.createRow(rownum);

		}
		row.setHeightInPoints(75);

		Cell cell = row.createCell(0);
		cell.setCellStyle(centered);
		cell.setCellValue((String) map.get("a0101"));

		Cell cell1 = row.createCell(1);
		cell1.setCellValue((String) map.get("a0283g"));
		ExeclUtil.setFontSize(cell1);

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(centered);
		cell2.setCellValue((String) map.get("xb"));

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(centered);
		cell3.setCellValue((String) map.get("a0117"));

		Cell cell4 = row.createCell(4);
		cell4.setCellStyle(centered);
		cell4.setCellValue((String) map.get("a0111a"));

		Cell cell5 = row.createCell(5);
		cell5.setCellStyle(centered);
		cell5.setCellValue((String) map.get("qrzxl"));

		Cell cell6 = row.createCell(6);

		cell6.setCellValue((String) map.get("qrzxlxx"));
		ExeclUtil.setFontSize(cell6);

		Cell cell7 = row.createCell(7);
		cell7.setCellStyle(centered);
		cell7.setCellValue((String) map.get("zzxl"));

		Cell cell8 = row.createCell(8);
		cell8.setCellValue((String) map.get("zzxwxx"));
		ExeclUtil.setFontSize(cell8);

		Cell cell9 = row.createCell(9);
		cell9.setCellStyle(centered);
		cell9.setCellValue((String) map.get("csrq"));

		Cell cell10 = row.createCell(10);
		cell10.setCellStyle(centered);
		cell10.setCellValue((String) map.get("cjgz"));

		Cell cell11 = row.createCell(11);
		cell11.setCellStyle(centered);
		cell11.setCellValue((String) map.get("a0140"));

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(centered);
		cell12.setCellValue((String) map.get("a0243"));

		Cell cell13 = row.createCell(13);
		cell13.setCellStyle(centered);
		cell13.setCellValue((String) map.get("a0192c"));

		Cell cell14 = row.createCell(14);
		cell14.setCellStyle(centered);
		cell14.setCellValue((String) map.get("a0180"));
	}

	public void insertDatafour(List list, int i, HSSFRow row, HSSFSheet sheet, int weizhi, CellStyle centered,
							   CellStyle left, HSSFWorkbook wb, CellStyle result, int n, Map<String, CellStyle[]> styleMap) {
		Map<String, Object> map = (Map<String, Object>) list.get(i);

		if (i < 5) {
			// add zepeng 20191110 直接被算术题算傻了，增加以下代码填充同事挖的算数坑
			int rownum = weizhi + 3 + i;
			while (sheet.getRow(rownum) != null) {
				rownum++;
			}
			row = sheet.createRow(rownum);

		} else {
			// add zepeng 20191110 直接被算术题算傻了，增加以下代码填充同事挖的算数坑
			int rownum = weizhi + 4 + i + (2 * n);
			while (sheet.getRow(rownum) != null) {
				rownum++;
			}
			row = sheet.createRow(rownum);
			// Cell cell14 = row.createCell(15);
			// cell14.setCellStyle(centered);
			// cell14.setCellValue("@@@@@");
		}
		row.setHeightInPoints(75);

		Cell cell = row.createCell(0);
		cell.setCellStyle(centered);
		cell.setCellValue((String) map.get("a0101"));

		Cell cell1 = row.createCell(1);
		// cell1.setCellStyle(left);
		cell1.setCellValue((String) map.get("a0283g"));
		ExeclUtil.setFontSize(cell1, styleMap.get("normal"));

		Cell cell2 = row.createCell(2);
		cell2.setCellStyle(centered);
		cell2.setCellValue((String) map.get("a0104"));

		Cell cell3 = row.createCell(3);
		cell3.setCellStyle(centered);
		cell3.setCellValue((String) map.get("a0117"));

		Cell cell4 = row.createCell(4);
		cell4.setCellStyle(centered);
		cell4.setCellValue((String) map.get("a0111a"));

		Cell cell5 = row.createCell(5);
		cell5.setCellStyle(centered);
		cell5.setCellValue((String) map.get("qrzxl"));

		Cell cell6 = row.createCell(6);

		cell6.setCellValue((String) map.get("qrzxlxx"));
		ExeclUtil.setFontSize(cell6, styleMap.get("normal"));

		Cell cell7 = row.createCell(7);
		cell7.setCellStyle(centered);
		cell7.setCellValue((String) map.get("zzxl"));

		Cell cell8 = row.createCell(8);


		cell8.setCellValue((String) map.get("zzxlxx"));
		ExeclUtil.setFontSize(cell8, styleMap.get("normal"));

		Cell cell9 = row.createCell(9);
		cell9.setCellStyle(centered);
		cell9.setCellValue((String) map.get("a0107"));

		Cell cell10 = row.createCell(10);
		cell10.setCellStyle(centered);
		cell10.setCellValue((String) map.get("a0134"));

		Cell cell11 = row.createCell(11);
		cell11.setCellStyle(centered);
		cell11.setCellValue((String) map.get("a0140"));

		Cell cell12 = row.createCell(12);
		cell12.setCellStyle(centered);
		cell12.setCellValue((String) map.get("a0243"));

		Cell cell13 = row.createCell(13);
		cell13.setCellStyle(centered);
		cell13.setCellValue((String) map.get("a0192c"));

		Cell cell14 = row.createCell(14);
		cell14.setCellStyle(centered);
		cell14.setCellValue((String) map.get("a0180"));
		ExeclUtil.setFontSize(cell14, styleMap.get("comments"));

	}


	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}

	// 获取中文pinyin
	public static String getHanziInitials(String hanzi) {

		String result = null;

		if (null != hanzi && !"".equals(hanzi)) {

			char[] charArray = hanzi.toCharArray();

			StringBuffer sb = new StringBuffer();

			for (char ch : charArray) {

				// 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）

				String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);

				if (null != stringArray) {

					// sb.append(stringArray[0].charAt(0)); 这个是获取首字母
					// 把第几声这个数字给去掉
					sb.append(stringArray[0].replaceAll("\\d", "")); // 这个是获取全拼

				}

			}

			if (sb.length() > 0) {

				result = sb.toString().toUpperCase();

			}

		}

		return result;

	}

}
