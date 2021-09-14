package com.insigma.siis.local.pagemodel.zj.expHzb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.helpers.AttributesImpl;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.SqlUtil;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class ZjXml4HZBNewUtil {

    private static int limit = Integer.MIN_VALUE;

    public static void data2Xml(String sql, String table, String path, Connection conn, int k, String logfilename, String sign, UserVO userVo) throws Exception {
	limit = k;
	if (table.equals("A01")) {
	    A01toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A02")) {
	    A02toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A05")) {
	    A05toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A06")) {
	    A06toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A08")) {
	    A08toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A14")) {
	    A14toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A15")) {
	    A15toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A30")) {
	    A30toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A36")) {
	    A36toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("A57")) {
	    A57toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("B01")) {
	    B01toXml(sql, path, conn, logfilename, sign, userVo);
	} else if (table.equals("A99Z1")) {
	    A99Z1toXml(sql, path, conn, logfilename, sign);
	} else if (table.equals("Extra_Tags")) {
	    TblToXml(table, ZjTYGSsqlUtil.ExtraTags, sql, path, conn, logfilename, sign);
	} else if (table.equals("A0193_Tag")) {
	    TblToXml(table, ZjTYGSsqlUtil.A0193Tag, sql, path, conn, logfilename, sign);
	} else if (table.equals("A0194_Tag")) {
	    TblToXml(table, ZjTYGSsqlUtil.A0194Tag, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Rclx")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagRclx, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Cjlx")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagCjlx, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Sbjysjl")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagSbjysjl, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Kcclfj")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagKcclfj, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Kcclfj2")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagKcclfj2, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Ndkhdjbfj")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagNdkhdjbfj, sql, path, conn, logfilename, sign);
	} else if (table.equals("Tag_Dazsbfj")) {
	    TblToXml(table, ZjTYGSsqlUtil.TagDazsbfj, sql, path, conn, logfilename, sign);
	}
    }

    @SuppressWarnings("unused")
    private static void InfoExttoXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	Element zrow = null;
	try {
	    File xmlFile = new File(path + "Table/" + "INFO_EXTEND.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);

	    w.start();
	    ResultSet rs = null;

	    String query = SqlUtil.getSqlByTableName("INFO_EXTEND", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("INFO_EXTEND","t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();

	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成INFO_EXTEND:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void B01ExttoXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	Element zrow = null;
	try {
	    File xmlFile = new File(path + "Table/" + "B01_EXT.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);
	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);

	    w.start();
	    ResultSet rs = null;

	    String query = SqlUtil.getSqlByTableName("B01_EXT", "t") + " where b0111 like '" + sql + "%'";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("B01_EXT","t")+"
	    // where b0111 like '"+ sql +"%'" +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成B01_EXT:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("rawtypes")
    public static void List2Xml(List list, String table, String path, String sign) {
	if (table.equals("info")) {
	    infotoXml(list, path, sign);
	}

    }

    @SuppressWarnings("rawtypes")
    private static void infotoXml(List list, String path, String sign) {
	// TODO Auto-generated method stub
	Map map = (Map) list.get(0);
	Document document = DocumentHelper.createDocument();
	// 添加元素 root
	Element root = document.addElement("root");
	// 添加元素 root
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
	if ("zip".equals(sign)) {
	    node10.addAttribute("name", "contact");
	} else {
	    node10.addAttribute("name", "linkpsn");
	}
	node10.addAttribute("value", emptoString(map.get("linkpsn")));
	Element node11 = root.addElement("node");
	if ("zip".equals(sign)) {
	    node11.addAttribute("name", "tel");
	} else {
	    node11.addAttribute("name", "linktel");
	}
	node11.addAttribute("value", emptoString(map.get("linktel")));
	Element node12 = root.addElement("node");
	if ("zip".equals(sign)) {
	    node12.addAttribute("name", "notes");
	} else {
	    node12.addAttribute("name", "remark");
	}
	node12.addAttribute("value", emptoString(map.get("remark")));
	if (!emptoString(map.get("tableext")).equals("")) {
	    Element node13 = root.addElement("node");
	    node13.addAttribute("name", "tableext");
	    node13.addAttribute("value", emptoString(map.get("tableext")));
	}
	Element tablelist = root.addElement("tablelist");
	if ("zip".equals(sign) || "7z".equals(sign)) {
	    String tables1[] = { "A01", "A02", "A05", "A06", "A08", "A14", "A15", "A30", "A36", "A57", "B01", "A99Z1", "Extra_Tags", "A0193_Tag", "A0194_Tag" };
	    for (int i = 0; i < tables1.length; i++) {
		String string = tables1[i];
		Element table = tablelist.addElement("tablelist");
		table.addAttribute("name", string);
	    }
	} else {
	    String tables1[] = { "A01", "A02", "A05", "A06", "A08", "A14", "A15", "A30", "A36", "A57", "B01", "A99Z1", "Extra_Tags", "A0193_Tag", "A0194_Tag" };
	    for (int i = 0; i < tables1.length; i++) {
		String string = tables1[i];
		Element table = tablelist.addElement("tablelist");
		table.addAttribute("name", string);
	    }
	}

	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	try {
	    File xmlFile = new File(path + "gwyinfo.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
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

    private static void B01toXml(String sql, String path, Connection conn, String logfilename, String sign, UserVO userVo) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "B01.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query =
	     * "select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
	     * "b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
	     * "b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
	     * "b0233,sortid,used,t.updated,create_user,create_date,update_user," +
	     * "update_date,t.status,b0238,b0239,b0234 "+ " from B01 t where b0111 like '"+
	     * sql +"%'" ;
	     */

	    // pywu 20170608 B01表所有字段
	    // String query = SqlUtil.getSqlByTableName( "B01", "t")+" where b0111 like '"+
	    // sql +"%'" ;
	    String query = "";
	    String newSql = "";
	    if ("7z".equals(sign)) {
		newSql = (TYGSsqlUtil.B01).replace("B0111", "t.B0111");
		query = " select " + newSql + " " + " FROM B01 t ,competence_userdept s " + " where t.b0111=s.b0111 " + " and s.userid='" + userVo.getId() + "' " + " and s.b0111 like '" + sql + "%' ";
	    } else if ("zip".equals(sign)) {
		newSql = (TYGSsqlUtil.B01ZIP).replace("B0111", "t.B0111");
		query = " select " + newSql + " " + " FROM B01 t ,competence_userdept s " + " where t.b0111=s.b0111 " + " and s.userid='" + userVo.getId() + "' " + " and s.b0111 like '" + sql + "%' ";
	    } else {
		query = SqlUtil.getSqlByTableName("B01", "t") + ",competence_userdept s " + " where t.b0111=s.b0111 " + " and s.userid='" + userVo.getId() + "' " + " and s.b0111 like '" + sql + "%' ";
	    }

	    System.out.println("pywu 20170608  B01表所有字段========" + query);

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    w.stop();
	    /*
	     * appendFileContent(logfilename, SqlUtil.getSqlByTableName( "B01", "t") +
	     * "where b0111 like '"+ sql +"%'" +":"+""+w.elapsedTime()+"\n");
	     */
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成B01:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    private static void A57toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "A57.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();

	    ResultSet rs = null;

	    // pywu 20170608 A57表所有字段
	    // String query = SqlUtil.getSqlByTableName("A57", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A57 + " " + " FROM A57 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A57", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A57", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A57:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A68toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "A68.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();

	    ResultSet rs = null;

	    // pywu 20170608 A57表所有字段
	    String query = SqlUtil.getSqlByTableName("A68", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A57", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A57:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A69toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "A69.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();

	    ResultSet rs = null;

	    // pywu 20170608 A57表所有字段
	    String query = SqlUtil.getSqlByTableName("A69", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A57", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A57:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A71toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "A71.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();

	    ResultSet rs = null;

	    // pywu 20170608 A57表所有字段
	    String query = SqlUtil.getSqlByTableName("A71", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A57", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A57:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A53toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "A53.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A53表所有字段
	    String query = SqlUtil.getSqlByTableName("A53", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A53", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where
	    // v.A0000=t.A0000)"+":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A53:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A41toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A41.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A41表所有字段
	    String query = SqlUtil.getSqlByTableName("A41", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A41", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A41:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A37toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A37.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A37表所有字段
	    String query = SqlUtil.getSqlByTableName("A37", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A37", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A37:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    private static void A36toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A36.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query = "select a0000,a3600,a3601,a3604a,a3607,a3611,a3627 ,sortid "+
	     * " FROM A36 t where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)" ;
	     */

	    // pywu 20170608 A36表所有字段
	    // String query = SqlUtil.getSqlByTableName("A36", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
			query = " select * FROM A36 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
	    	query = SqlUtil.getSqlByTableName("A36", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A36", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A36:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A31toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A31.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query = "select a0000,a3101,a3104,a3107,a3117a,a3118,a3137,a3138 " +
	     * ",updated,A3110,A3109,A3108 "+
	     * " FROM A31 t where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)" ;
	     */

	    // pywu 20170608 A31表所有字段
	    String query = SqlUtil.getSqlByTableName("A31", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A31", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where
	    // v.A0000=t.A0000)"+":"+""+w.elapsedTime()+"\n");
	    w.start();

	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成a31xml:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    private static void A30toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A30.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query = "select a0000,a3600,a3601,a3604a,a3607,a3611,a3627 ,sortid "+
	     * " FROM A36 t where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)" ;
	     */

	    // pywu 20170608 A36表所有字段
	    // String query = SqlUtil.getSqlByTableName("A36", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;

	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A30 + " " + " FROM A30 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A30", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A36", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A36:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A29toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A29.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query =
	     * "select a0000,a2907 ,a2911,a2921a ,a2941,a2944,a2947 ,a2949, updated," +
	     * "a2947a,A2921B,A2947B,A2921C,A2921d "+
	     * " FROM A29 t where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)" ;
	     */

	    // pywu 20170608 A29表所有字段
	    String query = SqlUtil.getSqlByTableName("A29", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    // w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A29", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A29:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    private static void A15toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A15.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A15表所有字段
	    // String query = SqlUtil.getSqlByTableName("A15", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A15 + " " + " FROM A15 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A15", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    // w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A15", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A15:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    private static void A14toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A14.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A14表所有字段
	    // String query = SqlUtil.getSqlByTableName("A14", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A14 + " " + " FROM A14 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A14", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A14", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();

	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A14:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A11toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A11.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query =
	     * "select A0000,A1100,A1101,A1104,A1107,A1107A,a1107b ,a1111 ,a1114 ,a1121a ,"+
	     * "a1127 ,a1131 ,a1134 ,a1151 ,updated,A1108,A1107C "+
	     * " FROM A11 t where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)" ;
	     */

	    // pywu 20170608 A11表所有字段
	    String query = SqlUtil.getSqlByTableName("A11", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    // w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A11", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A11:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    private static void A08toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A08.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A08表所有字段
	    // String query = SqlUtil.getSqlByTableName("A08", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign)) {
		query = " select " + TYGSsqlUtil.A08 + " " + " FROM A08 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else if ("zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A08 + " " + " FROM A08 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";// YGSsqlUtil.A08zip,根据部里统一字段后使用A08
	    } else {
		query = SqlUtil.getSqlByTableName("A08", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    // w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A08", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			if ("7z".equals(sign) && md.getColumnName(j).equals("A0899")) {
			    Object obj = rs.getObject(j);
			    if (obj != null) {
				if ("true".equals(obj.toString()) || "1".equals(obj.toString())) {
				    zrow.addAttribute(md.getColumnName(j), "1");
				} else {
				    zrow.addAttribute(md.getColumnName(j), "0");
				}
			    } else {
				zrow.addAttribute(md.getColumnName(j), "0");
			    }
			} else if ("zip".equals(sign) && md.getColumnName(j).equals("A0899")) {
			    Object obj = rs.getObject(j);
			    if (obj != null) {
				if ("true".equals(obj.toString()) || "1".equals(obj.toString())) {
				    zrow.addAttribute("A0898", "1");
				} else {
				    zrow.addAttribute("A0898", "0");
				}
			    } else {
				zrow.addAttribute("A0898", "0");
			    }
			} else {
			    zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			}
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A08:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    private static void A05toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A05.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 a06表所有字段
	    // String query = SqlUtil.getSqlByTableName("A05", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A05 + " " + " FROM A05 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A05", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    // w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A06", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			if (md.getColumnName(j).equals("A0531") && ("7z".equals(sign) || "zip".equals(sign))) {
			    if (rs.getObject(j) != null && "1".equals(rs.getObject(j))) {
				zrow.addAttribute("A0531", "0");
			    } else if (rs.getObject(j) != null && "0".equals(rs.getObject(j))) {
				zrow.addAttribute("A0531", "1");
			    }
			} else {
			    zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			}
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A05:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    private static void A06toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A06.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 a06表所有字段
	    // String query = SqlUtil.getSqlByTableName("A06", "t")+" where EXISTS (SELECT 1
	    // from "+sql+" v where v.A0000=t.A0000)" ;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A06 + " " + " FROM A06 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A06", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    // w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A06", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			if (("7z".equals(sign) || "zip".equals(sign)) && md.getColumnName(j).equals("A0699")) {
			    Object obj = rs.getObject(j);
			    if (obj != null) {
				if ("true".equals(obj.toString()) || "1".equals(obj.toString())) {
				    zrow.addAttribute(md.getColumnName(j), "1");
				} else {
				    zrow.addAttribute(md.getColumnName(j), "0");
				}
			    } else {
				zrow.addAttribute(md.getColumnName(j), "0");
			    }
			} else {
			    zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			}
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A06:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    private static void A02toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A02.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query =
	     * "select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"
	     * +
	     * "t.A0209,t.A0215A,t.A0215B,t.A0216A,t.A0219,t.A0219W,t.A0221,t.A0221W,t.A0222,t.A0223,"+
	     * " t.A0225,t.A0229,t.A0243,t.A0245,t.A0247,t.A0251,t.A0251B,t.A0255,t.A0256,t.A0256A,"
	     * +
	     * "t.A0256B,t.A0256C,t.A0259,t.A0265,t.A0267,t.A0271,t.A0277,t.A0281,t.A0284,t.A0288,"+
	     * "t.A0289,t.A0295,t.A0299,t.A4901,t.A4904,t.A4907,t.updated,t.wage_used,t.a0221a,"
	     * + "t.b0238,t.b0239 "+
	     * "from a02 t where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000) ";
	     */

	    // pywu 20170608 a02表所有字段
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + TYGSsqlUtil.A02 + " " + " FROM A02 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A02", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();

	    // w.stop();
	    // appendFileContent(logfilename,
	    // SqlUtil.getSqlByTableName("A02", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000) "
	    // +":"+""+w.elapsedTime()+"\n");
	    // w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			if (("7z".equals(sign) || "zip".equals(sign)) && md.getColumnName(j).equals("A0281")) {
			    Object obj = rs.getObject(j);
			    if (obj != null) {
				if ("true".equals(obj.toString()) || "1".equals(obj.toString())) {
				    zrow.addAttribute(md.getColumnName(j), "1");
				} else {
				    zrow.addAttribute(md.getColumnName(j), "0");
				}
			    } else {
				zrow.addAttribute(md.getColumnName(j), "0");
			    }
			} else if (("7z".equals(sign) || "zip".equals(sign)) && md.getColumnName(j).equals("A0219")) {
			    Object obj = rs.getObject(j);
			    if (obj != null) {
				if ("1".equals(obj.toString())) {
				    zrow.addAttribute(md.getColumnName(j), "1");
				} else {
				    zrow.addAttribute(md.getColumnName(j), "0");
				}
			    } else {
				zrow.addAttribute(md.getColumnName(j), "0");
			    }
			} else {
			    zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			}
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成a02xml:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");

	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    @SuppressWarnings("unused")
    private static void A01toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	Element zrow = null;
	try {
	    File xmlFile = new File(path + "Table/" + "A01.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    /*
	     * format.setLineSeparator("\n"); format.setSuppressDeclaration(true);
	     * format.setIndent(true); // 设置是否缩进 format.setIndent(" "); // 以空格方式实现缩进
	     * format.setNewlines(true); // 设置是否换行 format.setTrimText(false);
	     */
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);
	    // 添加元素 table

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);

	    // ------------ 数据需导出数量，分批查询，生成xml
	    // ---------------------------------------------------

	    w.start();
	    ResultSet rs = null;
	    /*
	     * String query =
	     * "select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"
	     * +
	     * "t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
	     * "t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
	     * "t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
	     * "t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
	     * "t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
	     * "t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
	     * "t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
	     * "t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u,t.cbdresult,t.ORGID "+
	     * "from "+sql+" t " ;
	     */

	    /*
	     * String query =
	     * "select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0115a,t.a0117,"
	     * +
	     * "t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
	     * "t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
	     * "t.a0192,t.a0192a,t.a0221,t.a0288,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
	     * "t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
	     * "t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
	     * "t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
	     * "t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,t.a0192c,"+
	     * "t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a2949,t.a0122,t.a0194u,t.cbdresult,t.ORGID "
	     * + "from "+sql+" t " ;
	     */

	    // pywu 20170608 a01表所有字段
	    String query = "";
	    if ("7z".equals(sign)) {
		query = "select " + TYGSsqlUtil.A01 + " from " + sql;
	    } else if ("zip".equals(sign)) {
		query = "select " + TYGSsqlUtil.A01zip + " from " + sql;
	    } else {
		query = "select " + SqlUtil.getColomnsByTableName("A01", "t") + "from " + sql + " t ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    /*
	     * appendFileContent(logfilename,
	     * "select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"
	     * +
	     * "t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
	     * "t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
	     * "t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
	     * "t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
	     * "t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
	     * "t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
	     * "t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
	     * "t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u,t.cbdresult,t.ORGID "+
	     * "from "+sql+" t " +":"+"  "+w.elapsedTime() );
	     */
	    // pywu 20170608 a01表所有字段
	    // appendFileContent(logfilename, "select "+SqlUtil.getColomnsByTableName("A01",
	    // "t") + "from "+sql+" t " +":"+" "+w.elapsedTime() );
	    w.start();
	    if (rs != null) {// pywu 报错
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			String str = rs.getObject(j) != null ? rs.getObject(j).toString() : "";
			// System.out.println(md.getColumnName(j)+"======="+str);
			if (md.getColumnName(j).equals("A1701")) {
			    if (DBUtil.getDBType().equals(DBType.MYSQL)) {
				zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			    } else {
				java.sql.Clob clob = rs.getClob("A1701");
				if (clob != null) {
				    Reader inStream = clob.getCharacterStream();
				    char[] c = new char[(int) clob.length()];
				    inStream.read(c);
				    // data是读出并需要返回的数据，类型是String
				    String data = new String(c);
				    inStream.close();
				    zrow.addAttribute(md.getColumnName(j), data);
				} else {
				    zrow.addAttribute(md.getColumnName(j), "");
				}
			    }
			} else {
			    zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			}

		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成a01xml:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();
	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

    private static String emptoString(Object object) {
	if (object != null) {
	    return object.toString();
	} else {
	    return "";
	}
    }

    @SuppressWarnings("unused")
    private static void A64toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	Element col = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	try {
	    File xmlFile = new File(path + "Table/" + "A64.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);
	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A64表所有字段
	    String query = SqlUtil.getSqlByTableName("A64", "t") + "where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A64", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A64:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    try {
		if (out != null)
		    out.close();
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

    @SuppressWarnings("unused")
    private static void A63toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	Element col = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	try {
	    File xmlFile = new File(path + "Table/" + "A63.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);
	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A63表所有字段
	    String query = SqlUtil.getSqlByTableName("A63", "t") + "where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A63", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where
	    // v.A0000=t.A0000)"+":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A63:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    try {
		if (out != null)
		    out.close();
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

    @SuppressWarnings("unused")
    private static void A62toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	Element col = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A62.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);
	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A62表所有字段
	    String query = SqlUtil.getSqlByTableName("A62", "t") + "where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A62", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();

	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A62:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    try {
		if (out != null)
		    out.close();
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

    @SuppressWarnings("unused")
    private static void A61toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	StopWatch w = new StopWatch();
	w.start();
	Element zrow = null;
	Element col = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	try {
	    File xmlFile = new File(path + "Table/" + "A61.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);
	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A61表所有字段
	    String query = SqlUtil.getSqlByTableName("A61", "t") + "where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A61", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A61:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static void A60toXml(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	Element col = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A60.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);
	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;

	    // pywu 20170608 A60表所有字段
	    String query = SqlUtil.getSqlByTableName("A60", "t") + "where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    w.stop();
	    // appendFileContent(logfilename, SqlUtil.getSqlByTableName("A60", "t")
	    // + "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000)"
	    // +":"+""+w.elapsedTime()+"\n");
	    w.start();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    // Element zrow = rsdata.addElement("row");

		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    // appendFileContent(logfilename, "生成A60:"+""+w.elapsedTime()+"\n"+
	    // DateUtil.getTime()+"\n");
	    rs.close();
	    pstmt.close();
	    System.gc();

	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
	    // HBUtil.getHBSession().createSQLQuery("drop table " +
	    // newTable).executeUpdate();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}

    }

    @SuppressWarnings("unused")
    private static String getNo() {
	String no = "";
	String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	for (int i = 0; i < 4; i++) {
	    Random random = new Random();
	    int r = random.nextInt(35);
	    no = no + key[r];
	}
	CommonQueryBS.systemOut(no);
	return no;
    }

    @SuppressWarnings("unused")
    private static void A15toXml3(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch sw = new StopWatch();

	try {
	    File xmlFile = new File(path + "Table/" + "A15.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // 添加元素 table
	    Element SchemaElement = xmlElement.addElement("table");
	    SchemaElement.addAttribute("name", "A15");
	    // 1 添加元素 column
	    Element column1 = SchemaElement.addElement("column");
	    column1.addAttribute("name", "A0000");
	    // 2 添加元素 column
	    Element column2 = SchemaElement.addElement("column");
	    column2.addAttribute("name", "A1500");
	    // 3 添加元素 column
	    Element column3 = SchemaElement.addElement("column");
	    column3.addAttribute("name", "A1517");
	    // 4 添加元素 column
	    Element column4 = SchemaElement.addElement("column");
	    column4.addAttribute("name", "A1521");
	    // 5 添加元素 column
	    Element column5 = SchemaElement.addElement("column");
	    column5.addAttribute("name", "UPDATED");
	    // 6 添加元素 column
	    Element column6 = SchemaElement.addElement("column");
	    column6.addAttribute("name", "A1527");

	    out.write(SchemaElement);
	    SchemaElement.clearContent();
	    // -------------------------------
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */
	    sw.start();
	    for (int i = 0; i < 1000000; i++) {
		zrow = DocumentHelper.createElement("row");
		zrow.addAttribute("A0000", "ed2720f5-5853-4c10-b1de-1ea9edd9629c");
		zrow.addAttribute("A1500", "04697490-8038-421d-a337-9eb3bb57aac7");
		zrow.addAttribute("A1517", "1");
		zrow.addAttribute("A1521", "2008");
		zrow.addAttribute("update", "");
		zrow.addAttribute("A1527", "2");
		out.write(zrow);
		zrow.clearContent();

	    }
	    // --------------------------------------------------
	    // out.writeClose(rsdata);
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    sw.stop();
	    CommonQueryBS.systemOut(sw.elapsedTime());
	    System.gc();

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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    public static void main(String[] args) {
	try {

	    Connection conn = null;
	    Class.forName("com.mysql.jdbc.Driver");
	    String url = "jdbc:mysql://127.0.0.1:3308/zwhzyq";
	    conn = DriverManager.getConnection(url, "root", "admin");
	    conn.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @SuppressWarnings("unused")
    private static void A15toXml32(String sql, String path, Connection conn, String logfilename) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try {
	    File xmlFile = new File(path + "Table/" + "A15.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    // out = new XMLWriter(new FileWriter(xmlFile, true), format);

	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    // 添加元素 table
	    Element SchemaElement = xmlElement.addElement("table");
	    SchemaElement.addAttribute("name", "A15");
	    // 1 添加元素 column
	    Element column1 = SchemaElement.addElement("column");
	    column1.addAttribute("name", "A0000");
	    // 2 添加元素 column
	    Element column2 = SchemaElement.addElement("column");
	    column2.addAttribute("name", "A1500");
	    // 3 添加元素 column
	    Element column3 = SchemaElement.addElement("column");
	    column3.addAttribute("name", "A1517");
	    // 4 添加元素 column
	    Element column4 = SchemaElement.addElement("column");
	    column4.addAttribute("name", "A1521");
	    // 5 添加元素 column
	    Element column5 = SchemaElement.addElement("column");
	    column5.addAttribute("name", "UPDATED");
	    // 6 添加元素 column
	    Element column6 = SchemaElement.addElement("column");
	    column6.addAttribute("name", "A1527");

	    out.write(SchemaElement);
	    SchemaElement.clearContent();
	    // -------------------------------
	    // Element rsdata = xmlElement.addElement("data");
	    // out.writeOpen(rsdata);
	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */
	    Long k = 0L;
	    Long d = 0L;
	    com.fr.third.org.hsqldb.lib.StopWatch w = new com.fr.third.org.hsqldb.lib.StopWatch();
	    w.start();
	    for (int i = 0; i < 600; i++) {
		com.fr.third.org.hsqldb.lib.StopWatch wt = new com.fr.third.org.hsqldb.lib.StopWatch();
		com.fr.third.org.hsqldb.lib.StopWatch wt1 = new com.fr.third.org.hsqldb.lib.StopWatch();
		wt.start();
		Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(500);
		stmt.setFetchDirection(ResultSet.FETCH_REVERSE);
		ResultSet rs = null;
		rs = stmt.executeQuery("select t.a0000, t.a1500, t.a1517, t.a1521, t.updated, t.a1527 from A15 t " + " limit 0,5000");
		wt.stop();
		k = k + wt.elapsedTime();
		wt1.start();
		if (rs != null) {
		    ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		    int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		    while (rs.next()) {
			zrow = DocumentHelper.createElement("row");
			for (int j = 1; j <= columnCount; j++) {
			    zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
			}
			out.write(zrow);
			zrow.clearContent();
		    }
		}

		rs.close();
		stmt.close();
		wt1.stop();
		d = d + wt1.elapsedTime();
		// System.gc();
	    }
	    w.stop();
	    CommonQueryBS.systemOut(w.elapsedTime() + "");
	    CommonQueryBS.systemOut(k + "");
	    CommonQueryBS.systemOut(d + "");
	    // --------------------------------------------------
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();

	    System.gc();

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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    private static void A99Z1toXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A99Z1.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + ZjTYGSsqlUtil.A99Z1 + " " + " FROM A99Z1 t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A99Z1", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    rs.close();
	    pstmt.close();
	    System.gc();
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    @SuppressWarnings("unused")
    private static void ExtraTagstoXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "Extra_Tags.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + ZjTYGSsqlUtil.ExtraTags + " " + " FROM Extra_Tags t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("Extra_Tags", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    rs.close();
	    pstmt.close();
	    System.gc();
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    @SuppressWarnings("unused")
    private static void A0193TagtoXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A0193_Tag.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + ZjTYGSsqlUtil.A0193Tag + " " + " FROM A0193_Tag t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A0193_Tag", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    rs.close();
	    pstmt.close();
	    System.gc();
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    @SuppressWarnings("unused")
    private static void A0194TagtoXml(String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + "A0194_Tag.xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */

	    w.start();
	    ResultSet rs = null;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + ZjTYGSsqlUtil.A0194Tag + " " + " FROM A0194_Tag t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName("A0194_Tag", "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    rs.close();
	    pstmt.close();
	    System.gc();
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }

    private static void TblToXml(String table, String columns, String sql, String path, Connection conn, String logfilename, String sign) throws Exception {
	Element zrow = null;
	XMLWriter out = null;
	BufferedWriter bw = null;
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	StopWatch w = new StopWatch();
	w.start();
	try {
	    File xmlFile = new File(path + "Table/" + table + ".xml");// 输出xml的路径
	    fos = new FileOutputStream(xmlFile);
	    osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
	    bw = new BufferedWriter(osw);

	    // 对xml输出格式化
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding("UTF-8");
	    out = new XMLWriter(fos, format);
	    out.setEscapeText(false);
	    out.startDocument();
	    Document document = DocumentHelper.createDocument();
	    // 添加元素 xml
	    Element xmlElement = document.addElement("xml");
	    out.writeOpen(xmlElement);

	    AttributesImpl a = new AttributesImpl();
	    out.startElement("", "", "data", a);
	    /**
	     * 加载数据
	     */
	    w.start();
	    ResultSet rs = null;
	    String query = "";
	    if ("7z".equals(sign) || "zip".equals(sign)) {
		query = " select " + columns + " FROM " + table + " t where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000)";
	    } else {
		query = SqlUtil.getSqlByTableName(table, "t") + " where EXISTS (SELECT 1 from " + sql + " v where v.A0000=t.A0000) ";
	    }

	    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    pstmt.setFetchSize(limit);
	    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
	    rs = pstmt.executeQuery();
	    if (rs != null) {
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		while (rs.next()) {
		    zrow = DocumentHelper.createElement("row");
		    for (int j = 1; j <= columnCount; j++) {
			zrow.addAttribute(md.getColumnName(j), rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		    }
		    out.write(zrow);
		    zrow.clearContent();
		}
	    }
	    w.stop();
	    rs.close();
	    pstmt.close();
	    System.gc();
	    out.endElement("", "", "data");
	    out.writeClose(xmlElement);
	    xmlElement.clearContent();
	    out.endDocument();
	    out.close();
	    System.gc();
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

	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }
}
