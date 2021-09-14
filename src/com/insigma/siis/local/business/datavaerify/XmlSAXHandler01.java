package com.insigma.siis.local.business.datavaerify;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class XmlSAXHandler01 extends DefaultHandler {

	public String docname;
	public String lowerCase;
	public String table;
	public String imprecordid;
	public int t_n;
	public int batchNum;
	public String uuid;
	public String from_file;
	public String B0111;
	public String impdeptid;
	public String deptid;
	public PreparedStatement pstmt1;
	public Connection conn1;
	public boolean isError = false;

	public XmlSAXHandler01() {
	}

	public XmlSAXHandler01(String docname, String lowerCase, String table,
			String imprecordid, int t_n, String uuid, String from_file,
			String B0111, String deptid, String impdeptid, Connection conn1,
			PreparedStatement pstmt1, int batchNum) {
		super();

		this.docname = docname;
		this.lowerCase = lowerCase;
		this.table = table;
		this.imprecordid = imprecordid;
		this.t_n = t_n;
		this.uuid = uuid;
		this.from_file = from_file;
		this.B0111 = B0111;
		this.impdeptid = impdeptid;
		this.deptid = deptid;
		this.pstmt1 = pstmt1;
		this.conn1 = conn1;
		this.batchNum = batchNum;
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (isError) {
			return;
		}
		if (qName.equals("z:row") || qName.equals("row")) {
			Map rowData = new HashMap();
			if (attributes.getLength() > 0) {
				for (int i = 0; i < attributes.getLength(); i++) {
					rowData.put(attributes.getQName(i).toUpperCase(), StringEscapeUtils.unescapeXml(attributes.getValue(i)));
				}
				rowData.put("IMPRECORDID", imprecordid);
				rowData.put("IS_QUALIFIED", "0");
				rowData.put("ERROR_INFO", "2");
			}
			HBSession sess = HBUtil.getHBSession();
			try {
				if (rowData.get("A0000") != null && !rowData.get("A0000").toString().equals("")) {
					String a5714 = rowData.get("A5714") != null ? rowData.get("A5714").toString() : "";
					if(a5714.length()>100){
						a5714 = subStringByByte(a5714,100);
					}
					pstmt1.setObject(1, rowData.get("A0000"));
					pstmt1.setObject(2, a5714);
					pstmt1.setObject(3, "1");
//					pstmt1.setObject(4, "");
//					pstmt1.setObject(5, "0");
//					pstmt1.setObject(6, imprecordid);
					//String a5714 = rowData.get("A5714") != null ? rowData.get("A5714").toString() : "";
					String a5714_arr[] = a5714.split("\\|");
					pstmt1.setObject(4, null);
					String photoname = a5714_arr[0].trim().equals("") ? (rowData
							.get("A0000") + ".jpg") : a5714_arr[0].trim();
					if(!photoname.contains(".")){
						photoname = photoname + ".jpg";
					}
					pstmt1.setObject(5, photoname);
					pstmt1.setObject(6, "jpg");
					String photopath = "";
					String subphotoname = photoname.substring(0,
							photoname.indexOf("."));
					if (subphotoname.length() >= 2) {
						String str = subphotoname.substring(0, 2);
						if (PhotosUtil.isLetterDigit(str)) {
							photopath = photoname.charAt(0) + "/"
									+ photoname.charAt(1) + "/";
						} else {
							photopath = photoname.charAt(0) + "/";
						}
					} else if (photoname.substring(0, photoname.indexOf("."))
							.length() == 1) {
						photopath = photoname + "/";
					}
					pstmt1.setObject(7, photopath);
					pstmt1.addBatch();
					rowData.clear();
					rowData = null;
				}
//				sess.flush();
				t_n++;
				if (t_n % batchNum == 0) {
					pstmt1.executeBatch();
//					appendFileContent("D:\\ceShiDaoRuShiJian.txt", "A57每"+batchNum+"次执行一次的时间" +DateUtil.getTime()+"\n");
					pstmt1.clearBatch();
					CommonQueryBS.systemOut(table + "======  NO." + t_n / batchNum + "  ========" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					System.gc();
				}
			} catch (Exception e1) {
				isError = true;
				try {
					KingbsconfigBS.saveImpDetail("3", "4", "失败:" + e1.getMessage(), uuid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
//				new UploadHzbFileBS().rollbackImp(imprecordid);
			}
		} else {
			return;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

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
	private String subStringByByte(String str, int len) {
		 String result = "";
	     if (str != "") {
	    	 byte[] a = str.getBytes();
	    	 if (a.length <= len) {
	    		 result = str;
	    	 } else if (len > 0) {
	    		 result = new String(a, 0, len);
	    		 int length = result.length();
	    		 if (str.charAt(length - 1) != result.charAt(length - 1)) {
	    			 if (length < 2) {
	    				 result = "";
	    			 } else {
	    				 result = result.substring(0, length - 1);
	    			 }
	    		 }
	    	 }
	     }
	     return result;
	 }
}