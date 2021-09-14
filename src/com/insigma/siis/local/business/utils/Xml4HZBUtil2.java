package com.insigma.siis.local.business.utils;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class Xml4HZBUtil2 {

	public static void List2Xml(ResultSet rs, String table, String path, String remark) throws Exception {
		// TODO Auto-generated method stub
		/*if (table.equals("A01")) {
			A01toXml(rs, path);
		} else if (table.equals("A02")) {
			A02toXml(rs, path);
		} else if (table.equals("A06")) {
			A06toXml(rs, path);
		} else if (table.equals("A08")) {
			A08toXml(rs, path);
		} else if (table.equals("A11")) {
			A11toXml(rs, path);
		} else if (table.equals("A14")) {
			A14toXml(rs, path);
		} else if (table.equals("A15")) {
			A15toXml(rs, path);
		} else if (table.equals("A29")) {
			A29toXml(rs, path);
		} else if (table.equals("A30")) {
			A30toXml(rs, path);
		} else if (table.equals("A31")) {
			A31toXml(rs, path);
		} else if (table.equals("A36")) {
			A36toXml(rs, path);
		} else if (table.equals("A37")) {
			A37toXml(rs, path);
		} else if (table.equals("A41")) {
			A41toXml(rs, path);
		} else if (table.equals("A53")) {
			A53toXml(rs, path);
		} else if (table.equals("A57")) {
			A57toXml(rs, path);
		} else if (table.equals("B01")) {
			B01toXml(rs, path);
		} else if (table.equals("B01_EXT")) {
			B01ExttoXml(rs, path);
		} else if (table.equals("INFO_EXTEND")) {
			InfoExttoXml(rs, path);
		} else if (table.equals("A60")) {
			A60toXml(rs, path);
		} else if (table.equals("A61")) {
			A61toXml(rs, path);
		} else if (table.equals("A62")) {
			A62toXml(rs, path);
		} else if (table.equals("A63")) {
			A63toXml(rs, path);
		} else if (table.equals("A64")) {
			A64toXml(rs, path);
		} else {
			rstoXml(rs, path, table);
		/*}*/
			rstoXml(rs, path, table,remark);
	}

	private static void InfoExttoXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "INFO_EXTEND.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "INFO_EXTEND");
	        out.writeOpen(SchemaElement);
	        // 1 ���Ԫ�� column
	        if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				for (int j = 1; j <= columnCount; j++) {
					Element column1 = SchemaElement.addElement("column");
					column1.addAttribute("name", md.getColumnName(j));
					out.write(column1);
				}
			}
	        out.writeClose(SchemaElement);
	        SchemaElement.clearContent();
	      //-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void B01ExttoXml(ResultSet rs, String path) throws Exception {
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		Element zrow = null;
		int count = 0;
		try {
			File xmlFile = new File(path + "Table/" + "B01_EXT.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "B01_EXT");
	        out.writeOpen(SchemaElement);
	        // 1 ���Ԫ�� column
	        if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				for (int j = 1; j <= columnCount; j++) {
					Element column1 = SchemaElement.addElement("column");
					column1.addAttribute("name", md.getColumnName(j));
					out.write(column1);
				}
			}
	        out.writeClose(SchemaElement);
	        SchemaElement.clearContent();
	      //-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	public static void List2Xml(List list, String table, String path) {
		if (table.equals("info")) {
			infotoXml(list, path);
		}

	}

	private static void infotoXml(List list, String path) {
		// TODO Auto-generated method stub
		Map map = (Map) list.get(0);
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� root
		Element root = document.addElement("root");
		// ���Ԫ�� root
		Element node1 = root.addElement("node");
		node1.addAttribute("name", "type");
		node1.addAttribute("value", emptoString(map.get("type")));
		Element node2 = root.addElement("node");
		node2.addAttribute("name", "time");
		node2.addAttribute("value", emptoString(map.get("time")));
		Element node3 = root.addElement("node");
		node3.addAttribute("name", "dataversion");
		node3.addAttribute("value", emptoString(map.get("dataversion")));
		Element node4 = root.addElement("node");
		node4.addAttribute("name", "psncount");
		node4.addAttribute("value", emptoString(map.get("psncount")));
		// Element node5 = root.addElement("node");
		// node5.addAttribute("name", "photodir");
		// node5.addAttribute("value", emptoString(map.get("photodir")));
		Element node6 = root.addElement("node");
		node6.addAttribute("name", "B0101");
		node6.addAttribute("value", emptoString(map.get("B0101")));
		Element node7 = root.addElement("node");
		node7.addAttribute("name", "B0111");
		node7.addAttribute("value", emptoString(map.get("B0111")));
		Element node8 = root.addElement("node");
		node8.addAttribute("name", "B0114");
		node8.addAttribute("value", emptoString(map.get("B0114")));
		Element node9 = root.addElement("node");
		node9.addAttribute("name", "B0194");

		node9.addAttribute("value", emptoString(map.get("B0194")));
		Element node10 = root.addElement("node");
		node10.addAttribute("name", "linkpsn");
		node10.addAttribute("value", emptoString(map.get("linkpsn")));
		Element node11 = root.addElement("node");
		node11.addAttribute("name", "linktel");
		node11.addAttribute("value", emptoString(map.get("linktel")));
		Element node12 = root.addElement("node");
		node12.addAttribute("name", "remark");
		node12.addAttribute("value", emptoString(map.get("remark")));
		Element tablelist = root.addElement("tablelist");
		String tables1[] = { "B01", "A02", "A01", "A08", "A53", "A36", "A14",
				"A15", "A29", "A31", "A30", "A11", "A37", "A57", "A06", "A41" };
		for (int i = 0; i < tables1.length; i++) {
			String string = tables1[i];
			Element table = tablelist.addElement("tablelist");
			table.addAttribute("name", string);
		}

		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			File xmlFile = new File(path + "gwyinfo.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.write(document);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void B01toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "B01.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "B01");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "B0101");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "B0104");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "B0107");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "B0111");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "B0114");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "B0117");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "B0121");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "B0124");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "B0127");
	        // 10 ���Ԫ�� column
	        Element column10 = SchemaElement.addElement("column");
	        column10.addAttribute("name", "B0131");
	        
	        // 11 ���Ԫ�� column
	        Element column11 = SchemaElement.addElement("column");
	        column11.addAttribute("name", "B0140");
	        // 12 ���Ԫ�� column
	        Element column12 = SchemaElement.addElement("column");
	        column12.addAttribute("name", "B0141");
	        // 13 ���Ԫ�� column
	        Element column13 = SchemaElement.addElement("column");
	        column13.addAttribute("name", "B0142");
	        // 14 ���Ԫ�� column
	        Element column14 = SchemaElement.addElement("column");
	        column14.addAttribute("name", "B0143");
	        // 15 ���Ԫ�� column
	        Element column15 = SchemaElement.addElement("column");
	        column15.addAttribute("name", "B0150");
	        // 16 ���Ԫ�� column
	        Element column16 = SchemaElement.addElement("column");
	        column16.addAttribute("name", "B0180");
	        // 17 ���Ԫ�� column
	        Element column17 = SchemaElement.addElement("column");
	        column17.addAttribute("name", "B0183");
	        // 18 ���Ԫ�� column
	        Element column18 = SchemaElement.addElement("column");
	        column18.addAttribute("name", "B0185");
	        // 19 ���Ԫ�� column
	        Element column19 = SchemaElement.addElement("column");
	        column19.addAttribute("name", "B0188");
	        // 20 ���Ԫ�� column
	        Element column20 = SchemaElement.addElement("column");
	        column20.addAttribute("name", "B0189");
	        
	      // 21 ���Ԫ�� column
	        Element column21 = SchemaElement.addElement("column");
	        column21.addAttribute("name", "B0190");
	      // 22 ���Ԫ�� column
	        Element column22 = SchemaElement.addElement("column");
	        column22.addAttribute("name", "B0191");
	      // 23 ���Ԫ�� column
	        Element column23 = SchemaElement.addElement("column");
	        column23.addAttribute("name", "B0191A");
	      // 24 ���Ԫ�� column
	        Element column24 = SchemaElement.addElement("column");
	        column24.addAttribute("name", "B0192");
	      // 25 ���Ԫ�� column
	        Element column25 = SchemaElement.addElement("column");
	        column25.addAttribute("name", "B0193");
	      // 26 ���Ԫ�� column
	        Element column26 = SchemaElement.addElement("column");
	        column26.addAttribute("name", "B0194");
	      // 27 ���Ԫ�� column
	        Element column27 = SchemaElement.addElement("column");
	        column27.addAttribute("name", "B01TRANS");
	      // 28 ���Ԫ�� column
	        Element column28 = SchemaElement.addElement("column");
	        column28.addAttribute("name", "B01IP");
	      // 29 ���Ԫ�� column
	        Element column29 = SchemaElement.addElement("column");
	        column29.addAttribute("name", "B0227");
	      // 30 ���Ԫ�� column
	        Element column30 = SchemaElement.addElement("column");
	        column30.addAttribute("name", "B0232");
	        
	      // 31 ���Ԫ�� column
	        Element column31 = SchemaElement.addElement("column");
	        column31.addAttribute("name", "B0233");
	        // 32 ���Ԫ�� column
	        Element column32 = SchemaElement.addElement("column");
	        column32.addAttribute("name", "SORTID");
	      // 33 ���Ԫ�� column
	        Element column33 = SchemaElement.addElement("column");
	        column33.addAttribute("name", "USED");
	      // 34 ���Ԫ�� column
	        Element column34 = SchemaElement.addElement("column");
	        column34.addAttribute("name", "UPDATED");
	      // 35 ���Ԫ�� column
	        Element column35 = SchemaElement.addElement("column");
	        column35.addAttribute("name", "CREATE_USER");
	      // 36 ���Ԫ�� column
	        Element column36 = SchemaElement.addElement("column");
	        column36.addAttribute("name", "CREATE_DATE");
	      // 37 ���Ԫ�� column
	        Element column37 = SchemaElement.addElement("column");
	        column37.addAttribute("name", "UPDATE_USER");
	      // 38 ���Ԫ�� column
	        Element column38 = SchemaElement.addElement("column");
	        column38.addAttribute("name", "UPDATE_DATE");
	      // 39 ���Ԫ�� column
	        Element column39 = SchemaElement.addElement("column");
	        column39.addAttribute("name", "STATUS"); 

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A57toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A57.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A57");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A5714");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A53toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A53.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A53");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A5300");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A5304");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A5315");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A5317");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A5319");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A5321");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "A5323");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "A5327");
	        // 10 ���Ԫ�� column
	        Element column10 = SchemaElement.addElement("column");
	        column10.addAttribute("name", "A5399");
	        
	        // 11 ���Ԫ�� column
	        Element column11 = SchemaElement.addElement("column");
	        column11.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A41toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A41.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A41");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A4100");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A0000");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A1100");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A4101");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A4102");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A4103");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A4104");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "A4105");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "A4199");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
	}

	private static void A37toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A37.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A37");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A3701");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A3707A");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A3707C");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A3707E");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A3707B");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A3708");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "A3711");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "A3714");
	        // 10 ���Ԫ�� column
	        Element column10 = SchemaElement.addElement("column");
	        column10.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A36toXml(ResultSet rs, String path) throws Exception {

		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		Element zrow = null;
		int count = 0;
		try {
			File xmlFile = new File(path + "Table/" + "A36.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A36");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A3600");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A3601");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A3604A");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A3607");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A3611");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A3627");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "SORTID");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
	}

	private static void A31toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A31.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A31");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A3101");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A3104");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A3107");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A3117A");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A3118");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A3137");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "A3138");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A30toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A30.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A30");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A3001");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A3004");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A3007A");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A3034");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	private static void A29toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A29.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A29");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A2907");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A2911");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A2921A");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A2941");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A2944");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A2947");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "A2949");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A15toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A15.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A15");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A1500");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A1517");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A1521");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "UPDATED");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A1527");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	private static void A14toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A14.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
			Element SchemaElement = xmlElement.addElement("table");
			SchemaElement.addAttribute("name", "A14");
			// 1 ���Ԫ�� column
			Element column1 = SchemaElement.addElement("column");
			column1.addAttribute("name", "A0000");
			// 2 ���Ԫ�� column
			Element column2 = SchemaElement.addElement("column");
			column2.addAttribute("name", "A1400");
			// 3 ���Ԫ�� column
			Element column3 = SchemaElement.addElement("column");
			column3.addAttribute("name", "A1404A");
			// 4 ���Ԫ�� column
			Element column4 = SchemaElement.addElement("column");
			column4.addAttribute("name", "A1404B");
			// 5 ���Ԫ�� column
			Element column5 = SchemaElement.addElement("column");
			column5.addAttribute("name", "A1407");
			// 6 ���Ԫ�� column
			Element column6 = SchemaElement.addElement("column");
			column6.addAttribute("name", "A1411A");
			// 7 ���Ԫ�� column
			Element column7 = SchemaElement.addElement("column");
			column7.addAttribute("name", "A1414");
			// 8 ���Ԫ�� column
			Element column8 = SchemaElement.addElement("column");
			column8.addAttribute("name", "A1415");
			// 9 ���Ԫ�� column
			Element column9 = SchemaElement.addElement("column");
			column9.addAttribute("name", "A1424");
			// 10 ���Ԫ�� column
			Element column10 = SchemaElement.addElement("column");
			column10.addAttribute("name", "A1428");

			// 11 ���Ԫ�� column
			Element column11 = SchemaElement.addElement("column");
			column11.addAttribute("name", "SORTID");
			// 12 ���Ԫ�� column
			Element column12 = SchemaElement.addElement("column");
			column12.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
	}

	private static void A11toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A11.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			 // ���Ԫ�� table
	        Element SchemaElement = xmlElement.addElement("table");
	        SchemaElement.addAttribute("name", "A11");
	        // 1 ���Ԫ�� column
	        Element column1 = SchemaElement.addElement("column");
	        column1.addAttribute("name", "A0000");
	        // 2 ���Ԫ�� column
	        Element column2 = SchemaElement.addElement("column");
	        column2.addAttribute("name", "A1100");
	        // 3 ���Ԫ�� column
	        Element column3 = SchemaElement.addElement("column");
	        column3.addAttribute("name", "A1101");
	        // 4 ���Ԫ�� column
	        Element column4 = SchemaElement.addElement("column");
	        column4.addAttribute("name", "A1104");
	        // 5 ���Ԫ�� column
	        Element column5 = SchemaElement.addElement("column");
	        column5.addAttribute("name", "A1107");
	        // 6 ���Ԫ�� column
	        Element column6 = SchemaElement.addElement("column");
	        column6.addAttribute("name", "A1107A");
	        // 7 ���Ԫ�� column
	        Element column7 = SchemaElement.addElement("column");
	        column7.addAttribute("name", "A1107B");
	        // 8 ���Ԫ�� column
	        Element column8 = SchemaElement.addElement("column");
	        column8.addAttribute("name", "A1111");
	        // 9 ���Ԫ�� column
	        Element column9 = SchemaElement.addElement("column");
	        column9.addAttribute("name", "A1114");
	        // 10 ���Ԫ�� column
	        Element column10 = SchemaElement.addElement("column");
	        column10.addAttribute("name", "A1121A");
	        
	        // 11 ���Ԫ�� column
	        Element column11 = SchemaElement.addElement("column");
	        column11.addAttribute("name", "A1127");
	        // 12 ���Ԫ�� column
	        Element column12 = SchemaElement.addElement("column");
	        column12.addAttribute("name", "A1131");
	        // 13 ���Ԫ�� column
	        Element column13 = SchemaElement.addElement("column");
	        column13.addAttribute("name", "A1134");
	        // 14 ���Ԫ�� column
	        Element column14 = SchemaElement.addElement("column");
	        column14.addAttribute("name", "A1151");
	        // 15 ���Ԫ�� column
	        Element column15 = SchemaElement.addElement("column");
	        column15.addAttribute("name", "UPDATED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
	}

	private static void A08toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A08.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
			Element SchemaElement = xmlElement.addElement("table");
			SchemaElement.addAttribute("name", "A08");
			// 1 ���Ԫ�� column
			Element column1 = SchemaElement.addElement("column");
			column1.addAttribute("name", "A0000");
			// 2 ���Ԫ�� column
			Element column2 = SchemaElement.addElement("column");
			column2.addAttribute("name", "A0800");
			// 3 ���Ԫ�� column
			Element column3 = SchemaElement.addElement("column");
			column3.addAttribute("name", "A0801A");
			// 4 ���Ԫ�� column
			Element column4 = SchemaElement.addElement("column");
			column4.addAttribute("name", "A0801B");
			// 5 ���Ԫ�� column
			Element column5 = SchemaElement.addElement("column");
			column5.addAttribute("name", "A0804");
			// 6 ���Ԫ�� column
			Element column6 = SchemaElement.addElement("column");
			column6.addAttribute("name", "A0807");
			// 7 ���Ԫ�� column
			Element column7 = SchemaElement.addElement("column");
			column7.addAttribute("name", "A0811");
			// 8 ���Ԫ�� column
			Element column8 = SchemaElement.addElement("column");
			column8.addAttribute("name", "A0814");
			// 9 ���Ԫ�� column
			Element column9 = SchemaElement.addElement("column");
			column9.addAttribute("name", "A0824");
			// 10 ���Ԫ�� column
			Element column10 = SchemaElement.addElement("column");
			column10.addAttribute("name", "A0827");

			// 11 ���Ԫ�� column
			Element column11 = SchemaElement.addElement("column");
			column11.addAttribute("name", "A0831");
			// 12 ���Ԫ�� column
			Element column12 = SchemaElement.addElement("column");
			column12.addAttribute("name", "A0832");
			// 13 ���Ԫ�� column
			Element column13 = SchemaElement.addElement("column");
			column13.addAttribute("name", "A0834");
			// 14 ���Ԫ�� column
			Element column14 = SchemaElement.addElement("column");
			column14.addAttribute("name", "A0835");
			// 15 ���Ԫ�� column
			Element column15 = SchemaElement.addElement("column");
			column15.addAttribute("name", "A0837");
			// 16 ���Ԫ�� column
			Element column16 = SchemaElement.addElement("column");
			column16.addAttribute("name", "A0838");
			// 17 ���Ԫ�� column
			Element column17 = SchemaElement.addElement("column");
			column17.addAttribute("name", "A0839");
			// 18 ���Ԫ�� column
			Element column18 = SchemaElement.addElement("column");
			column18.addAttribute("name", "A0898");
			// 19 ���Ԫ�� column
			Element column19 = SchemaElement.addElement("column");
			column19.addAttribute("name", "A0899");
			// 20 ���Ԫ�� column
			Element column20 = SchemaElement.addElement("column");
			column20.addAttribute("name", "A0901A");

			// 21 ���Ԫ�� column
			Element column21 = SchemaElement.addElement("column");
			column21.addAttribute("name", "A0901B");
			// 22 ���Ԫ�� column
			Element column22 = SchemaElement.addElement("column");
			column22.addAttribute("name", "A0904");
			// 23 ���Ԫ�� column
			Element column23 = SchemaElement.addElement("column");
			column23.addAttribute("name", "SORTID");
			// 24 ���Ԫ�� column
			Element column24 = SchemaElement.addElement("column");
			column24.addAttribute("name", "UPDATED");
			// 25 ���Ԫ�� column
			Element column25 = SchemaElement.addElement("column");
			column25.addAttribute("name", "WAGE_USED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	private static void A06toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A06.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			// ���Ԫ�� table
			Element SchemaElement = xmlElement.addElement("table");
			SchemaElement.addAttribute("name", "A06");
			// 1 ���Ԫ�� column
			Element column1 = SchemaElement.addElement("column");
			column1.addAttribute("name", "A0000");
			// 2 ���Ԫ�� column
			Element column2 = SchemaElement.addElement("column");
			column2.addAttribute("name", "A0600");
			// 3 ���Ԫ�� column
			Element column3 = SchemaElement.addElement("column");
			column3.addAttribute("name", "A0601");
			// 4 ���Ԫ�� column
			Element column4 = SchemaElement.addElement("column");
			column4.addAttribute("name", "A0602");
			// 5 ���Ԫ�� column
			Element column5 = SchemaElement.addElement("column");
			column5.addAttribute("name", "A0604");
			// 6 ���Ԫ�� column
			Element column6 = SchemaElement.addElement("column");
			column6.addAttribute("name", "A0607");
			// 7 ���Ԫ�� column
			Element column7 = SchemaElement.addElement("column");
			column7.addAttribute("name", "A0611");
			// 8 ���Ԫ�� column
			Element column8 = SchemaElement.addElement("column");
			column8.addAttribute("name", "A0614");
			// 9 ���Ԫ�� column
			Element column9 = SchemaElement.addElement("column");
			column9.addAttribute("name", "SORTID");
			// 10 ���Ԫ�� column
			Element column10 = SchemaElement.addElement("column");
			column10.addAttribute("name", "UPDATED");
			Element column11 = SchemaElement.addElement("column");
			column11.addAttribute("name", "A0699");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	private static void A02toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A02.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);
			// ���Ԫ�� table
			Element SchemaElement = xmlElement.addElement("table");
			SchemaElement.addAttribute("name", "A02");
			// 1 ���Ԫ�� column
			Element column1 = SchemaElement.addElement("column");
			column1.addAttribute("name", "A0000");
			// 2 ���Ԫ�� column
			Element column2 = SchemaElement.addElement("column");
			column2.addAttribute("name", "A0200");
			// 3 ���Ԫ�� column
			Element column3 = SchemaElement.addElement("column");
			column3.addAttribute("name", "A0201");
			// 4 ���Ԫ�� column
			Element column4 = SchemaElement.addElement("column");
			column4.addAttribute("name", "A0201A");
			// 5 ���Ԫ�� column
			Element column5 = SchemaElement.addElement("column");
			column5.addAttribute("name", "A0201B");
			// 6 ���Ԫ�� column
			Element column6 = SchemaElement.addElement("column");
			column6.addAttribute("name", "A0201C");
			// 7 ���Ԫ�� column
			Element column7 = SchemaElement.addElement("column");
			column7.addAttribute("name", "A0201D");
			// 8 ���Ԫ�� column
			Element column8 = SchemaElement.addElement("column");
			column8.addAttribute("name", "A0201E");
			// 9 ���Ԫ�� column
			Element column9 = SchemaElement.addElement("column");
			column9.addAttribute("name", "A0204");
			// 10 ���Ԫ�� column
			Element column10 = SchemaElement.addElement("column");
			column10.addAttribute("name", "A0207");

			// 11 ���Ԫ�� column
			Element column11 = SchemaElement.addElement("column");
			column11.addAttribute("name", "A0209");
			// 12 ���Ԫ�� column
			Element column12 = SchemaElement.addElement("column");
			column12.addAttribute("name", "A0215A");
			// 13 ���Ԫ�� column
			Element column13 = SchemaElement.addElement("column");
			column13.addAttribute("name", "A0215B");
			// 14 ���Ԫ�� column
			Element column14 = SchemaElement.addElement("column");
			column14.addAttribute("name", "A0216A");
			// 15 ���Ԫ�� column
			Element column15 = SchemaElement.addElement("column");
			column15.addAttribute("name", "A0219");
			// 16 ���Ԫ�� column
			Element column16 = SchemaElement.addElement("column");
			column16.addAttribute("name", "A0219W");
			// 17 ���Ԫ�� column
			Element column17 = SchemaElement.addElement("column");
			column17.addAttribute("name", "A0221");
			// 18 ���Ԫ�� column
			Element column18 = SchemaElement.addElement("column");
			column18.addAttribute("name", "A0221W");
			// 19 ���Ԫ�� column
			Element column19 = SchemaElement.addElement("column");
			column19.addAttribute("name", "A0222");
			// 20 ���Ԫ�� column
			Element column20 = SchemaElement.addElement("column");
			column20.addAttribute("name", "A0223");

			// 21 ���Ԫ�� column
			Element column21 = SchemaElement.addElement("column");
			column21.addAttribute("name", "A0225");
			// 22 ���Ԫ�� column
			Element column22 = SchemaElement.addElement("column");
			column22.addAttribute("name", "A0229");
			// 23 ���Ԫ�� column
			Element column23 = SchemaElement.addElement("column");
			column23.addAttribute("name", "A0243");
			// 24 ���Ԫ�� column
			Element column24 = SchemaElement.addElement("column");
			column24.addAttribute("name", "A0245");
			// 25 ���Ԫ�� column
			Element column25 = SchemaElement.addElement("column");
			column25.addAttribute("name", "A0247");
			// 26 ���Ԫ�� column
			Element column26 = SchemaElement.addElement("column");
			column26.addAttribute("name", "A0251");
			// 27 ���Ԫ�� column
			Element column27 = SchemaElement.addElement("column");
			column27.addAttribute("name", "A0251B");
			// 28 ���Ԫ�� column
			Element column28 = SchemaElement.addElement("column");
			column28.addAttribute("name", "A0255");
			// 29 ���Ԫ�� column
			Element column29 = SchemaElement.addElement("column");
			column29.addAttribute("name", "A0256");
			// 30 ���Ԫ�� column
			Element column30 = SchemaElement.addElement("column");
			column30.addAttribute("name", "A0256A");

			// 31 ���Ԫ�� column
			Element column31 = SchemaElement.addElement("column");
			column31.addAttribute("name", "A0256B");
			// 32 ���Ԫ�� column
			Element column32 = SchemaElement.addElement("column");
			column32.addAttribute("name", "A0256C");
			// 33 ���Ԫ�� column
			Element column33 = SchemaElement.addElement("column");
			column33.addAttribute("name", "A0259");
			// 34 ���Ԫ�� column
			Element column34 = SchemaElement.addElement("column");
			column34.addAttribute("name", "A0265");
			// 35 ���Ԫ�� column
			Element column35 = SchemaElement.addElement("column");
			column35.addAttribute("name", "A0267");
			// 36 ���Ԫ�� column
			Element column36 = SchemaElement.addElement("column");
			column36.addAttribute("name", "A0271");
			// 37 ���Ԫ�� column
			Element column37 = SchemaElement.addElement("column");
			column37.addAttribute("name", "A0277");
			// 38 ���Ԫ�� column
			Element column38 = SchemaElement.addElement("column");
			column38.addAttribute("name", "A0281");
			// 39 ���Ԫ�� column
			Element column39 = SchemaElement.addElement("column");
			column39.addAttribute("name", "A0284");
			// 40 ���Ԫ�� column
			Element column40 = SchemaElement.addElement("column");
			column40.addAttribute("name", "A0288");

			// 41 ���Ԫ�� column
			Element column41 = SchemaElement.addElement("column");
			column41.addAttribute("name", "A0289");
			// 42 ���Ԫ�� column
			Element column42 = SchemaElement.addElement("column");
			column42.addAttribute("name", "A0295");
			// 43 ���Ԫ�� column
			Element column43 = SchemaElement.addElement("column");
			column43.addAttribute("name", "A0299");
			// 44 ���Ԫ�� column
			Element column44 = SchemaElement.addElement("column");
			column44.addAttribute("name", "A4901");
			// 45 ���Ԫ�� column
			Element column45 = SchemaElement.addElement("column");
			column45.addAttribute("name", "A4904");
			// 46 ���Ԫ�� column
			Element column46 = SchemaElement.addElement("column");
			column46.addAttribute("name", "A4907");
			// 47 ���Ԫ�� column
			Element column47 = SchemaElement.addElement("column");
			column47.addAttribute("name", "UPDATED");
			// 48 ���Ԫ�� column
			Element column48 = SchemaElement.addElement("column");
			column48.addAttribute("name", "WAGE_USED");

			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();System.gc();;
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	private static void A01toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			File xmlFile = new File(path + "Table/" + "A01.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = MyOutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			format.setEncoding("UTF-8");
			format.setLineSeparator("\n");
			format.setSuppressDeclaration(true);
			format.setIndent(true); // �����Ƿ�����
			format.setIndent(" "); // �Կո�ʽʵ������
			format.setNewlines(true); // �����Ƿ���
			format.setTrimText(false);
			out = new XMLWriter(fos, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);
			// ���Ԫ�� table
			Element SchemaElement = xmlElement.addElement("table");
			SchemaElement.addAttribute("name", "A01");
			// 1 ���Ԫ�� column
			Element column1 = SchemaElement.addElement("column");
			column1.addAttribute("name", "A0000");
			// 2 ���Ԫ�� column
			Element column2 = SchemaElement.addElement("column");
			column2.addAttribute("name", "A0101");
			// 3 ���Ԫ�� column
			Element column3 = SchemaElement.addElement("column");
			column3.addAttribute("name", "A0104");
			// 4 ���Ԫ�� column
			Element column4 = SchemaElement.addElement("column");
			column4.addAttribute("name", "A0104A");
			// 5 ���Ԫ�� column
			Element column5 = SchemaElement.addElement("column");
			column5.addAttribute("name", "A0107");
			// 6 ���Ԫ�� column
			Element column6 = SchemaElement.addElement("column");
			column6.addAttribute("name", "A0111");
			// 7 ���Ԫ�� column
			Element column7 = SchemaElement.addElement("column");
			column7.addAttribute("name", "A0111A");
			// 8 ���Ԫ�� column
			Element column8 = SchemaElement.addElement("column");
			column8.addAttribute("name", "A0114");
			// 9 ���Ԫ�� column
			Element column9 = SchemaElement.addElement("column");
			column9.addAttribute("name", "A0114A");
			// 10 ���Ԫ�� column
			Element column10 = SchemaElement.addElement("column");
			column10.addAttribute("name", "A0117");

			// 11 ���Ԫ�� column
			Element column11 = SchemaElement.addElement("column");
			column11.addAttribute("name", "A0117A");
			// 12 ���Ԫ�� column
			Element column12 = SchemaElement.addElement("column");
			column12.addAttribute("name", "A0134");
			// 13 ���Ԫ�� column
			Element column13 = SchemaElement.addElement("column");
			column13.addAttribute("name", "A0144");
			// 14 ���Ԫ�� column
			Element column14 = SchemaElement.addElement("column");
			column14.addAttribute("name", "A0144B");
			// 15 ���Ԫ�� column
			Element column15 = SchemaElement.addElement("column");
			column15.addAttribute("name", "A0144C");
			// 16 ���Ԫ�� column
			Element column16 = SchemaElement.addElement("column");
			column16.addAttribute("name", "A0148");
			// 17 ���Ԫ�� column
			Element column17 = SchemaElement.addElement("column");
			column17.addAttribute("name", "A0149");
			// 18 ���Ԫ�� column
			Element column18 = SchemaElement.addElement("column");
			column18.addAttribute("name", "A0151");
			// 19 ���Ԫ�� column
			Element column19 = SchemaElement.addElement("column");
			column19.addAttribute("name", "A0153");
			// 20 ���Ԫ�� column
			Element column20 = SchemaElement.addElement("column");
			column20.addAttribute("name", "A0155");

			// 21 ���Ԫ�� column
			Element column21 = SchemaElement.addElement("column");
			column21.addAttribute("name", "A0157");
			// 22 ���Ԫ�� column
			Element column22 = SchemaElement.addElement("column");
			column22.addAttribute("name", "A0158");
			// 23 ���Ԫ�� column
			Element column23 = SchemaElement.addElement("column");
			column23.addAttribute("name", "A0159");
			// 24 ���Ԫ�� column
			Element column24 = SchemaElement.addElement("column");
			column24.addAttribute("name", "A015A");
			// 25 ���Ԫ�� column
			Element column25 = SchemaElement.addElement("column");
			column25.addAttribute("name", "A0160");
			// 26 ���Ԫ�� column
			Element column26 = SchemaElement.addElement("column");
			column26.addAttribute("name", "A0161");
			// 27 ���Ԫ�� column
			Element column27 = SchemaElement.addElement("column");
			column27.addAttribute("name", "A0162");
			// 28 ���Ԫ�� column
			Element column28 = SchemaElement.addElement("column");
			column28.addAttribute("name", "A0163");
			// 29 ���Ԫ�� column
			Element column29 = SchemaElement.addElement("column");
			column29.addAttribute("name", "A0165");
			// 30 ���Ԫ�� column
			Element column30 = SchemaElement.addElement("column");
			column30.addAttribute("name", "A0184");

			// 31 ���Ԫ�� column
			Element column31 = SchemaElement.addElement("column");
			column31.addAttribute("name", "A0191");
			// 32 ���Ԫ�� column
			Element column32 = SchemaElement.addElement("column");
			column32.addAttribute("name", "A0192");
			// 33 ���Ԫ�� column
			Element column33 = SchemaElement.addElement("column");
			column33.addAttribute("name", "A0192A");
			// 34 ���Ԫ�� column
			Element column34 = SchemaElement.addElement("column");
			column34.addAttribute("name", "A0192B");
			// 35 ���Ԫ�� column
			Element column35 = SchemaElement.addElement("column");
			column35.addAttribute("name", "A0193");
			// 36 ���Ԫ�� column
			Element column36 = SchemaElement.addElement("column");
			column36.addAttribute("name", "A0195");
			// 37 ���Ԫ�� column
			Element column37 = SchemaElement.addElement("column");
			column37.addAttribute("name", "A0196");
			// 38 ���Ԫ�� column
			Element column38 = SchemaElement.addElement("column");
			column38.addAttribute("name", "A0198");
			// 39 ���Ԫ�� column
			Element column39 = SchemaElement.addElement("column");
			column39.addAttribute("name", "A0199");
			// 40 ���Ԫ�� column
			Element column40 = SchemaElement.addElement("column");
			column40.addAttribute("name", "A01K01");

			// 41 ���Ԫ�� column
			Element column41 = SchemaElement.addElement("column");
			column41.addAttribute("name", "A01K02");
			// 42 ���Ԫ�� column
			Element column42 = SchemaElement.addElement("column");
			column42.addAttribute("name", "AGE");
			// 43 ���Ԫ�� column
			Element column43 = SchemaElement.addElement("column");
			column43.addAttribute("name", "CBDW");
			// 44 ���Ԫ�� column
			Element column44 = SchemaElement.addElement("column");
			column44.addAttribute("name", "ISVALID");
			// 45 ���Ԫ�� column
			Element column45 = SchemaElement.addElement("column");
			column45.addAttribute("name", "JSNLSJ");
			// 46 ���Ԫ�� column
			Element column46 = SchemaElement.addElement("column");
			column46.addAttribute("name", "NL");
			// 47 ���Ԫ�� column
			Element column47 = SchemaElement.addElement("column");
			column47.addAttribute("name", "NMZW");
			// 48 ���Ԫ�� column
			Element column48 = SchemaElement.addElement("column");
			column48.addAttribute("name", "NRZW");
			// 49 ���Ԫ�� column
			Element column49 = SchemaElement.addElement("column");
			column49.addAttribute("name", "QRZXL");
			// 50 ���Ԫ�� column
			Element column50 = SchemaElement.addElement("column");
			column50.addAttribute("name", "QRZXLXX");

			// 51 ���Ԫ�� column
			Element column51 = SchemaElement.addElement("column");
			column51.addAttribute("name", "QRZXW");
			// 52 ���Ԫ�� column
			Element column52 = SchemaElement.addElement("column");
			column52.addAttribute("name", "QRZXWXX");
			// 53 ���Ԫ�� column
			Element column53 = SchemaElement.addElement("column");
			column53.addAttribute("name", "RESULTSORTID");
			// 54 ���Ԫ�� column
			Element column54 = SchemaElement.addElement("column");
			column54.addAttribute("name", "RMLY");
			// 55 ���Ԫ�� column
			Element column55 = SchemaElement.addElement("column");
			column55.addAttribute("name", "TBR");
			// 56 ���Ԫ�� column
			Element column56 = SchemaElement.addElement("column");
			column56.addAttribute("name", "TBSJ");
			// 57 ���Ԫ�� column
			Element column57 = SchemaElement.addElement("column");
			column57.addAttribute("name", "USERLOG");
			// 58 ���Ԫ�� column
			Element column58 = SchemaElement.addElement("column");
			column58.addAttribute("name", "XGR");
			// 59 ���Ԫ�� column
			Element column59 = SchemaElement.addElement("column");
			column59.addAttribute("name", "XGSJ");
			// 60 ���Ԫ�� column
			Element column60 = SchemaElement.addElement("column");
			column60.addAttribute("name", "ZZXL");

			// 61 ���Ԫ�� column
			Element column61 = SchemaElement.addElement("column");
			column61.addAttribute("name", "ZZXLXX");
			// 62 ���Ԫ�� column
			Element column62 = SchemaElement.addElement("column");
			column62.addAttribute("name", "ZZXW");
			// 63 ���Ԫ�� column
			Element column63 = SchemaElement.addElement("column");
			column63.addAttribute("name", "ZZXWXX");
			// 64 ���Ԫ�� column
			Element column64 = SchemaElement.addElement("column");
			column64.addAttribute("name", "A3927");
			// 65 ���Ԫ�� column
			Element column65 = SchemaElement.addElement("column");
			column65.addAttribute("name", "A0102");
			// 66 ���Ԫ�� column
			Element column66 = SchemaElement.addElement("column");
			column66.addAttribute("name", "A0128B");
			// 67 ���Ԫ�� column
			Element column67 = SchemaElement.addElement("column");
			column67.addAttribute("name", "A0128");
			// 68 ���Ԫ�� column
			Element column68 = SchemaElement.addElement("column");
			column68.addAttribute("name", "A0140");
			// 69 ���Ԫ�� column
			Element column69 = SchemaElement.addElement("column");
			column69.addAttribute("name", "A0187A");
			// 70 ���Ԫ�� column
			Element column70 = SchemaElement.addElement("column");
			column70.addAttribute("name", "A0148C");

			// 71 ���Ԫ�� column
			Element column71 = SchemaElement.addElement("column");
			column71.addAttribute("name", "A1701");
			// 72 ���Ԫ�� column
			Element column72 = SchemaElement.addElement("column");
			column72.addAttribute("name", "A14Z101");
			// 73 ���Ԫ�� column
			Element column73 = SchemaElement.addElement("column");
			column73.addAttribute("name", "A15Z101");
			// 74 ���Ԫ�� column
			Element column74 = SchemaElement.addElement("column");
			column74.addAttribute("name", "A0141D");
			// 75 ���Ԫ�� column
			Element column75 = SchemaElement.addElement("column");
			column75.addAttribute("name", "A0141");
			// 76 ���Ԫ�� column
			Element column76 = SchemaElement.addElement("column");
			column76.addAttribute("name", "A3921");
			// 77 ���Ԫ�� column
			Element column77 = SchemaElement.addElement("column");
			column77.addAttribute("name", "SORTID");
			// 78 ���Ԫ�� column
			Element column78 = SchemaElement.addElement("column");
			column78.addAttribute("name", "A0180");
			// 79 ���Ԫ�� column
			Element column79 = SchemaElement.addElement("column");
			column79.addAttribute("name", "A0194");
			out.write(SchemaElement);
			SchemaElement.clearContent();
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
//					Element zrow = rsdata.addElement("row");
					for (int j = 1; j <= columnCount; j++) {
						String str = rs.getObject(j) != null ? rs.getObject(j).toString() : "";
//						CommonQueryBS.systemOut(md.getColumnName(j)+"======="+str);
						if(md.getColumnName(j).equals("A1701")){
							if(DBUtil.getDBType().equals(DBType.MYSQL)){
								zrow.addAttribute(md.getColumnName(j),
										rs.getObject(j) != null ? rs.getObject(j)
												.toString() : "");
							} else {
								java.sql.Clob clob = rs.getClob("A1701");
								if(clob!=null){
									Reader inStream = clob.getCharacterStream();
									char[] c = new char[(int) clob.length()];
									inStream.read(c);
									//data�Ƕ�������Ҫ���ص����ݣ�������String
									String data = new String(c);
									inStream.close();
									zrow.addAttribute(md.getColumnName(j),data);
								} else {
									zrow.addAttribute(md.getColumnName(j),"");
								}
							}
						} else {
							zrow.addAttribute(md.getColumnName(j),
									rs.getObject(j) != null ? rs.getObject(j)
											.toString() : "");
						}
						
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("a01--neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("a01--neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
			out.endElement("", "", "data");
			out.writeClose(xmlElement);
			xmlElement.clearContent();
			out.endDocument();
			CommonQueryBS.systemOut("a01--neicun1��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			out.close();
			System.gc();
			CommonQueryBS.systemOut("a01--neicun2��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static int limit = 2000;
	private static String emptoString(Object object) {
		if (object != null) {
			return object.toString();
		} else {
			return "";
		}
	}
	
	private static void A64toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A64.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			Element col = null;
			AttributesImpl a1 = new AttributesImpl();
			a1.addAttribute("", "", "name", "", "A64");
			out.startElement("", "", "table", a1);
			
			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
			for (int j = 1; j <= columnCount; j++) {
				col = DocumentHelper.createElement("column");
				col.addAttribute("name",md.getColumnName(j));
				out.write(col);
				col.clearContent();
			}
			out.endElement("", "", "table");
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) 
					out.close();System.gc();;
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A63toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A63.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			Element col = null;
			AttributesImpl a1 = new AttributesImpl();
			a1.addAttribute("", "", "name", "", "A63");
			out.startElement("", "", "table", a1);
			
			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
			for (int j = 1; j <= columnCount; j++) {
				col = DocumentHelper.createElement("column");
				col.addAttribute("name",md.getColumnName(j));
				out.write(col);
				col.clearContent();
			}
			out.endElement("", "", "table");
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) 
					out.close();System.gc();;
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A62toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A62.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			Element col = null;
			AttributesImpl a1 = new AttributesImpl();
			a1.addAttribute("", "", "name", "", "A62");
			out.startElement("", "", "table", a1);
			
			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
			for (int j = 1; j <= columnCount; j++) {
				col = DocumentHelper.createElement("column");
				col.addAttribute("name",md.getColumnName(j));
				out.write(col);
				col.clearContent();
			}
			out.endElement("", "", "table");
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) 
					out.close();System.gc();;
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A61toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A61.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			Element col = null;
			AttributesImpl a1 = new AttributesImpl();
			a1.addAttribute("", "", "name", "", "A61");
			out.startElement("", "", "table", a1);
			
			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
			for (int j = 1; j <= columnCount; j++) {
				col = DocumentHelper.createElement("column");
				col.addAttribute("name",md.getColumnName(j));
				out.write(col);
				col.clearContent();
			}
			out.endElement("", "", "table");
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) 
					out.close();System.gc();;
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private static void A60toXml(ResultSet rs, String path) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + "A60.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			Element col = null;
			AttributesImpl a1 = new AttributesImpl();
			a1.addAttribute("", "", "name", "", "A60");
			out.startElement("", "", "table", a1);
			
			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
			for (int j = 1; j <= columnCount; j++) {
				col = DocumentHelper.createElement("column");
				col.addAttribute("name",md.getColumnName(j));
				out.write(col);
				col.clearContent();
			}
			
			out.endElement("", "", "table");
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						zrow.addAttribute(md.getColumnName(j),
								rs.getObject(j) != null ? rs.getObject(j)
										.toString() : "");
					}
					out.write(zrow);
					zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) 
					out.close();System.gc();;
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}
	private static void rstoXml(ResultSet rs, String path, String table, String remark) throws Exception {
		Element zrow = null;
		int count = 0;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(path + "Table/" + table+ ".xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);

			ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
			int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
			if("hzb".equals(remark)){
				Element col = null;
				AttributesImpl a1 = new AttributesImpl();
				a1.addAttribute("", "", "name", "", table);
				out.startElement("", "", "table", a1);
				for (int j = 1; j <= columnCount; j++) {
					col = DocumentHelper.createElement("column");
					col.addAttribute("name",md.getColumnName(j));
					out.write(col);
					col.clearContent();
				}
				out.endElement("", "", "table");
			}
			
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			if (rs != null) {
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					for (int j = 1; j <= columnCount; j++) {
						String str = rs.getObject(j) != null ? rs.getObject(j).toString() : "";
//						CommonQueryBS.systemOut(md.getColumnName(j)+"======="+str);
						if(md.getColumnName(j).equals("A1701")){
							if(DBUtil.getDBType().equals(DBType.MYSQL)){
								zrow.addAttribute(md.getColumnName(j),
										rs.getObject(j) != null ? rs.getObject(j)
												.toString() : "");
							} else {
								java.sql.Clob clob = rs.getClob("A1701");
								if(clob!=null){
									Reader inStream = clob.getCharacterStream();
									char[] c = new char[(int) clob.length()];
									inStream.read(c);
									//data�Ƕ�������Ҫ���ص����ݣ�������String
									String data = new String(c);
									inStream.close();
									zrow.addAttribute(md.getColumnName(j),data);
								} else {
									zrow.addAttribute(md.getColumnName(j),"");
								}
							}
						} else if(md.getColumnName(j).equals("A0531")&&"7z".equals(remark)){
							if(rs.getObject(j)!=null && "1".equals(rs.getObject(j))){
	                			zrow.addAttribute("A0531","0");
	                		}else if(rs.getObject(j)!=null && "0".equals(rs.getObject(j))){
	                			zrow.addAttribute("A0531","1");
	                		}
						} else if((md.getColumnName(j).equals("A0281")||md.getColumnName(j).equals("A0699")||md.getColumnName(j).equals("A0899"))&&"7z".equals(remark)){
							Object obj = rs.getObject(j);
							if(obj!=null){
								if("true".equals(obj.toString())){
									zrow.addAttribute(md.getColumnName(j),"1");
								}else if("false".equals(obj.toString())){
									zrow.addAttribute(md.getColumnName(j),"0");
								}else{
									zrow.addAttribute(md.getColumnName(j),"");
								}
							}
						} else {
							zrow.addAttribute(md.getColumnName(j),
									rs.getObject(j) != null ? rs.getObject(j)
											.toString() : "");
						}
						
					}
					out.write(zrow);zrow.clearContent();
					if(count++ % limit ==0){
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						System.gc();
						CommonQueryBS.systemOut("neicun0��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					}
				}
			}
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);xmlElement.clearContent();
			out.endDocument();
			out.close();System.gc();;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (out != null) 
					out.close();System.gc();;
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	public static void List2Xml(ResultSet b01_info, String string, String zippath) {
		// TODO Auto-generated method stub
		
	}
}
