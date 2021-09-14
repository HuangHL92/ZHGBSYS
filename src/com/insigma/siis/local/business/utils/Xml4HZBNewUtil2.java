package com.insigma.siis.local.business.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.DateUtil;
import com.insigma.siis.local.util.SqlUtil;
import com.insigma.siis.local.util.TYGSsqlUtil;
import com.insigma.siis.local.util.TYGSsqlUtilTwo;
/**
 * �����ɲ�ZIP��ʱ�账����ļ�
 * @author hongy
 *
 */
public class Xml4HZBNewUtil2 {
	
//	private static int limit = Integer.MIN_VALUE;
	private static HBSession sess=HBUtil.getHBSession();
	
	public static void data2Xml(String table, String path,Connection conn,String logfilename,String sign,String linkpsnthis,String linktel) throws Exception {
//		limit = k;
		if (table.equals("A01")) {//�ɲ�������Ϣ��
			A01toXml(path, conn, logfilename,sign);
		} else if (table.equals("A02")) {//ְ����Ϣ��
			A02toXml(path, conn, logfilename,sign);
		}else if (table.equals("A39")) {//������Ϣ��
			A39toXml(path, conn, logfilename,sign);
		}else if (table.equals("A36")) {//��ͥ��Ա������ϵ��Ϣ��
			A36toXml(path, conn, logfilename,sign);
		}else if (table.equals("A15")) {//��ȿ�����Ϣ��
			A15toXml(path, conn, logfilename,sign);
		}else if (table.equals("B01")) {//�ɲ����ڻ�����Ϣ��
			B01toXml(path, conn, logfilename,sign);
		}else if (table.equals("FILEINFO")) {//������ϵ���ñ�
			FILEINFOtoXml(path, conn, logfilename,sign);
		}else if (table.equals("Constant")) {//������ϵ���ñ�
			ConstanttoXml(path, conn, logfilename,sign,linkpsnthis,linktel);
		} 

	}
	/**
	 * A01Xml
	 */
	private static void A01toXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		StopWatch w = new StopWatch();
		w.start();
		Element zrow = null;
		Element col = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			File xmlFile = new File(path + "Tables/" + "A01.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);
			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(fos, format);

			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);
			
			
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			ResultSet rs = null;
			String query = "";
			query = "select "+TYGSsqlUtilTwo.A01zip + " from a01 where A0000 in (select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')";
			rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
//			PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			
			//pstmt.setFetchSize(limit); 
//			pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {//pywu ����
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				Object A0000="";
				rs.previous();
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
					ResultSet rs2 = null;
					String query2 = "";
//					PreparedStatement pstmt2 = null;
//					Element zrow = rsdata.addElement("row");
					for (int j = 1; j <= columnCount; j++) {
						//�����Ա��ʶ��Ϣ
						if(md.getColumnName(j).equals("A00")) {
							 A0000 = rs.getObject(j);
						}
						//String str = rs.getObject(j) != null ? rs.getObject(j).toString() : "";
//						System.out.println(md.getColumnName(j)+"======="+str);
						if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0144B")){
							Object obj = rs.getObject(j);
							if(obj!=null){//����A0144B�ֶ�
								if(obj.toString().contains("(")) {
									int begin=obj.toString().indexOf("(")+1;
									int last=obj.toString().indexOf(")");
									zrow.addAttribute(md.getColumnName(j),obj.toString().substring(begin,last)+"��"+obj.toString().substring(0,(begin-1)));
								}else {
									zrow.addAttribute(md.getColumnName(j),obj.toString());
								}
							}else{
								zrow.addAttribute(md.getColumnName(j),"");
							}
						}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A1701")){
							//����A1701��ֵ
							Clob obj = (Clob)rs.getObject(j);
							if(obj!=null){
								zrow.addAttribute(md.getColumnName(j),ClobToString(obj));
							}else {
								zrow.addAttribute(md.getColumnName(j),"");
							}
						}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0128")){
							//����A0127 �� A0128�ֶ�
							//��ȡA0128��ֵ
							Object obj = rs.getObject(j);
							if(rs.getObject(j) != null) {
								//�ж�A0128��ֵ ����A0127��Ӧ��ֵ
								if(obj.toString().equals("����")) {
									zrow.addAttribute("A0127","1");
								}else{
									zrow.addAttribute("A0127","2");
								}
								zrow.addAttribute(md.getColumnName(j), rs.getObject(j).toString());
							}else {
								zrow.addAttribute(md.getColumnName(j), "");
							}
						}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A1401")){
							//������A1401�ֶ� ������A1401 ����ѯtag_sbjysjl��
							zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
							//����A1401A-H������ֵ
							zrow.addAttribute("A1401A","0");
							zrow.addAttribute("A1401B","0");
							zrow.addAttribute("A1401C","0");
							zrow.addAttribute("A1401D","0");
							zrow.addAttribute("A1401F","0");
							zrow.addAttribute("A1401G","0");
							zrow.addAttribute("A1401H","0");
							query2 = "select TAG_SBJYSJL,TAG_SBJYSJL_NOTE from tag_sbjysjl WHERE a0000='"+A0000.toString()+"'";
//							pstmt2 = conn.prepareStatement(query2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//							pstmt2.setFetchDirection(ResultSet.FETCH_REVERSE); 
//							rs2 = pstmt2.executeQuery();
							rs2 = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
							//�����û���tag_sbjysjl ���ݱ��д��ڽ������� ����ݽ����������Ӷ�Ӧ�ֶ�����
							if(rs2 != null && rs2.next()){  
								//����Ϊ�� �����ʡ�������Ͻ��� ����A1401A������Ϣ ֵΪ1
								zrow.setAttributeValue("A1401A","1");
								//���ص���һ����¼
								rs2.previous();
								while (rs2.next()) {
									Object result = rs2.getObject(1);
									//����ѯ�Ľ�������ֵΪS1 ��Ϊȫ�����㹲����Ա
									if(result.toString().equals("S1")) {
										zrow.setAttributeValue("A1401B","1");
									}else if(result.toString().equals("S2")) {
										//����ѯ�Ľ�������ֵΪS2 ��Ϊȫ�����㵳ί������
										zrow.setAttributeValue("A1401C","1");
									}else if(result.toString().equals("S3")) {
										//����ѯ�Ľ�������ֵΪS3 ��Ϊȫ��������ί���
										zrow.setAttributeValue("A1401D","1");
									}else if(result.toString().equals("S4")) {
										//����ѯ�Ľ�������ֵΪS4 ��Ϊȫ���Ͷ�ģ��
										zrow.setAttributeValue("A1401F","1");
									}else if(result.toString().equals("S5")) {
										//����ѯ�Ľ�������ֵΪS5 ��Ϊ�������� ������������˵��
										zrow.setAttributeValue("A1401G","1");
										zrow.setAttributeValue("A1401H",rs2.getObject(2) != null ? rs2.getObject(2).toString() : "");
									}
									
								}
							}
							if(rs2!=null) {rs2.close();}
							
						}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0504")){
							//������A0504�ֶ� ������A0504 ����ѯA0193_TAG��
							zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? dateFormate(rs.getObject(j).toString()) : "");
							//����A0193��A0193C������ֵ
							zrow.addAttribute("A0193","");
							zrow.addAttribute("A0193C","");
							query2 = "select A0193,A0193C from A0193_TAG WHERE a0000='"+A0000.toString()+"'";
//							pstmt2 = conn.prepareStatement(query2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//							rs2 = pstmt2.executeQuery();
							rs2 = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
							//�����û���A0193_TAG ���ݱ��д�����Ҫ���� ����ݲ�ѯ�����޸Ķ�Ӧ�ֶ�����
							if(rs2!=null&& rs2.next()){
								String A0193 = "";
								String A0193C = "";
								//���ص���һ����¼
								rs2.previous();
								while (rs2.next()) {
									//�жϾ���˵���Ƿ�Ϊ��
									if(rs2.getObject(1)!=null) {
										if(A0193.equals("")) {
											A0193 = (String)rs2.getObject(1);
										}else {
											A0193 = A0193+","+rs2.getObject(1);
										}
										//�ж��Ƿ���ھ���˵��
										if(rs2.getObject(2) != null) {
											//���Ӿ���˵�����
											if(A0193C.equals("")) {
												//���ӵ�һ�����˵��ֵ
												A0193C = rs2.getObject(1)+":"+rs2.getObject(2);
											}else {
												//���ڶ��ʱƴ��
												A0193C = A0193C+"$"+rs2.getObject(1)+":"+rs2.getObject(2);
											}
											
										}
									}
								}
								zrow.setAttributeValue("A0193",A0193);
								zrow.setAttributeValue("A0193C",A0193C);
								if(rs2!=null) {rs2.close();}
								
							}
							//����A0194��A0194C������ֵ
							zrow.addAttribute("A0194","");
							zrow.addAttribute("A0194C","");
							rs2 = null;
							query2 = "";
							query2 = "select A0194,A0194C from A0194_TAG WHERE a0000='"+A0000.toString()+"'";
//							pstmt2 = conn.prepareStatement(query2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//							rs2 = pstmt2.executeQuery();
							rs2 = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
							//�����û���A0194_TAG ���ݱ��д�����Ϥ���� ����ݲ�ѯ�����޸Ķ�Ӧ�ֶ�����
							if(rs2!=null&& rs2.next()){
								String A0194 = "";
								String A0194C = "";
								//���ص���һ����¼
								rs2.previous();
								while (rs2.next()) {
									//�ж���Ϥ���� �Ƿ�Ϊ��
									if(rs2.getObject(1)!=null) {
										if(A0194.equals("")) {
											A0194 = (String) rs2.getObject(1);
										}else {
											A0194 = A0194+","+rs2.getObject(1);
										}
										
										//�ж��Ƿ������Ϥ���򲹳�
										if(rs2.getObject(2) != null) {
											//������Ϥ���򲹳�
											if(A0194C.equals("")) {
												//���ӵ�һ����Ϥ���򲹳�ֵ
												A0194C = rs2.getObject(1)+":"+rs2.getObject(2);
											}else {
												//���ڶ��ʱƴ��
												A0194C = A0194C+"$"+rs2.getObject(1)+":"+rs2.getObject(2);
											}
											
										}
									}
								}
								zrow.setAttributeValue("A0194",A0194);
								zrow.setAttributeValue("A0194C",A0194C);
								if(rs2!=null) {rs2.close();}
							}
						}else if(("7z".equals(sign)||"zip".equals(sign)) 
								&& (md.getColumnName(j).equals("A0107")||md.getColumnName(j).equals("A0144")||md.getColumnName(j).equals("A0134"))){
							zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? dateFormate(rs.getObject(j).toString()) : "");
						}else if (("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("BJXA0109")){
							//����BJXA0109����ֵ
							Object obj = rs.getObject(j);
							if(rs.getObject(j) != null) {
								if(!obj.toString().equals("1")) {
									zrow.addAttribute(md.getColumnName(j),"5");
								}else {
									zrow.addAttribute(md.getColumnName(j),rs.getObject(j).toString());
								}
							}else {
								zrow.addAttribute(md.getColumnName(j),"");
							}
							
						}else {
							zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
						}
						
					}
					zrow.addAttribute("A0195","");
					out.write(zrow);
					
					zrow.clearContent();
				}
			}
//			appendFileContent(logfilename, "����a01xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
			if(rs!=null) {rs.close();}
			
			System.gc();
			
			/**
			 * ��������
			 */
			//--------------------------------------------------
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);
			xmlElement.clearContent();
			out.endDocument();
			out.close();
			System.gc();
//			HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	
	
	//////////////////////
	private static void A02toXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
			File xmlFile = new File(path + "Tables/" + "A02.xml");// ���xml��·��
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);

			// ��xml�����ʽ��
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(fos, format);
//			out = new XMLWriter(new FileWriter(xmlFile, true), format);

			out.setEscapeText(false);
			out.startDocument();
			Document document = DocumentHelper.createDocument();
			// ���Ԫ�� xml
			Element xmlElement = document.addElement("xml");
			out.writeOpen(xmlElement);
			
			//-------------------------------
//			Element rsdata = xmlElement.addElement("data");
//			out.writeOpen(rsdata);
			AttributesImpl a = new AttributesImpl();
			out.startElement("", "", "data", a);
			/**
			 * ��������
			 */

			w.start();
			ResultSet rs = null;
			//pywu 20170608  a02�������ֶ�
			String query = "";
				query = " select "+TYGSsqlUtilTwo.A02zip+" "+ " FROM A02 t where EXISTS (SELECT 1 from a01 v where v.A0000=t.A0000) and A0000 in (select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')";
			
//			PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//			rs = pstmt.executeQuery();
			rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			
//			w.stop();
//			appendFileContent(logfilename, 
//			        SqlUtil.getSqlByTableName("A02", "t")
//				+ "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000) " +":"+""+w.elapsedTime()+"\n");
//			w.start();
			if (rs != null && rs.next())  {
				ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
				int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
				//���ص���һ����¼
				rs.previous();
				while (rs.next()) {
					zrow = DocumentHelper.createElement("row");
//					Element zrow = rsdata.addElement("row");
					
					for (int j = 1; j <= columnCount; j++) {
						if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0281")){
							Object obj = rs.getObject(j);
							if(obj!=null){
								if("true".equals(obj.toString())||"1".equals(obj.toString())){
									zrow.addAttribute(md.getColumnName(j),"1");
								}else{
									zrow.addAttribute(md.getColumnName(j),"0");
								}
							}else{
								zrow.addAttribute(md.getColumnName(j),"0");
							}
						}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0219")){
							Object obj = rs.getObject(j);
							if(obj!=null){
								if("1".equals(obj.toString())){
									zrow.addAttribute(md.getColumnName(j),"1");
								}else{
									zrow.addAttribute(md.getColumnName(j),"0");
								}
							}else{
								zrow.addAttribute(md.getColumnName(j),"0");
							}
						}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0243")) {
							//����A02423ʱ���ʽ����
							zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? dateFormate(rs.getObject(j).toString()) : "");
						}else{
							zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
						}
					}
					out.write(zrow);
					zrow.clearContent();
				}
			}
			w.stop();
//			appendFileContent(logfilename, "����a02xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
			
			rs.close();
			System.gc();
		
			//--------------------------------------------------
//			out.writeClose(rsdata);
			out.endElement("", "", "data");
			out.writeClose(xmlElement);
			xmlElement.clearContent();
			out.endDocument();
			out.close();
			System.gc();
//			HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	
	//////////////////////
	private static void A39toXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
		File xmlFile = new File(path + "Tables/" + "A39.xml");// ���xml��·��
		fos = new FileOutputStream(xmlFile);
		osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
		bw = new BufferedWriter(osw);
		
		// ��xml�����ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		out = new XMLWriter(fos, format);
		//out = new XMLWriter(new FileWriter(xmlFile, true), format);
		
		out.setEscapeText(false);
		out.startDocument();
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� xml
		Element xmlElement = document.addElement("xml");
		out.writeOpen(xmlElement);
		
		//-------------------------------
		//Element rsdata = xmlElement.addElement("data");
		//out.writeOpen(rsdata);
		AttributesImpl a = new AttributesImpl();
		out.startElement("", "", "data", a);
		/**
		* ��������
		*/
		
		w.start();
		ResultSet rs = null;
		//pywu 20170608  a02�������ֶ�
		String query = "";
		query = " select "+TYGSsqlUtilTwo.A39zip+ " FROM A01 where A0000 in (select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')";
		
//		PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//		rs = pstmt.executeQuery();
		rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		
		//w.stop();
		//appendFileContent(logfilename, 
		//    SqlUtil.getSqlByTableName("A02", "t")
		//+ "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000) " +":"+""+w.elapsedTime()+"\n");
		//w.start();
		if (rs != null && rs.next()) {
		ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
		int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
		//����һ��
		rs.previous();
		while (rs.next()) {
			zrow = DocumentHelper.createElement("row");
		//	Element zrow = rsdata.addElement("row");
			
			for (int j = 1; j <= columnCount; j++) {
				if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0281")){
					Object obj = rs.getObject(j);
					if(obj!=null){
						if("true".equals(obj.toString())||"1".equals(obj.toString())){
							zrow.addAttribute(md.getColumnName(j),"1");
						}else{
							zrow.addAttribute(md.getColumnName(j),"0");
						}
					}else{
						zrow.addAttribute(md.getColumnName(j),"0");
					}
				}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0219")){
					Object obj = rs.getObject(j);
					if(obj!=null){
						if("1".equals(obj.toString())){
							zrow.addAttribute(md.getColumnName(j),"1");
						}else{
							zrow.addAttribute(md.getColumnName(j),"0");
						}
					}else{
						zrow.addAttribute(md.getColumnName(j),"0");
					}
				}else{
					zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
				}
			}
			out.write(zrow);
			zrow.clearContent();
		}
		}
		w.stop();
		//appendFileContent(logfilename, "����a02xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
		
		rs.close();
		System.gc();
		
		//--------------------------------------------------
		//out.writeClose(rsdata);
		out.endElement("", "", "data");
		out.writeClose(xmlElement);
		xmlElement.clearContent();
		out.endDocument();
		out.close();
		System.gc();
		//HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	//////////////////////
	private static void A36toXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
		File xmlFile = new File(path + "Tables/" + "A36.xml");// ���xml��·��
		fos = new FileOutputStream(xmlFile);
		osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
		bw = new BufferedWriter(osw);
		
		// ��xml�����ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		out = new XMLWriter(fos, format);
		//out = new XMLWriter(new FileWriter(xmlFile, true), format);
		
		out.setEscapeText(false);
		out.startDocument();
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� xml
		Element xmlElement = document.addElement("xml");
		out.writeOpen(xmlElement);
		
		//-------------------------------
		//Element rsdata = xmlElement.addElement("data");
		//out.writeOpen(rsdata);
		AttributesImpl a = new AttributesImpl();
		out.startElement("", "", "data", a);
		/**
		* ��������
		*/
		
		w.start();
		ResultSet rs = null;
		//pywu 20170608  a02�������ֶ�
		String query = "";
		query = " select "+TYGSsqlUtilTwo.A36zip+ " FROM A36 A inner join code_value B \r\n" + 
				"  on A.a3627=B.code_name\r\n" + 
				"  and A.a0000 in(select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%') ";
		
//		PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//		rs = pstmt.executeQuery();
		rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		if (rs != null && rs.next()) {
		ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
		int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
		//����һ��
		rs.previous();
		while (rs.next()) {
		zrow = DocumentHelper.createElement("row");
		//	Element zrow = rsdata.addElement("row");
		
		for (int j = 1; j <= columnCount; j++) {
		if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A3607")){
			zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? dateFormate(rs.getObject(j).toString()) : "");
		}else{
			zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		}
		}
		out.write(zrow);
		zrow.clearContent();
		}
		}
		w.stop();
		//appendFileContent(logfilename, "����a02xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
		
		rs.close();
		System.gc();
		
		//--------------------------------------------------
		//out.writeClose(rsdata);
		out.endElement("", "", "data");
		out.writeClose(xmlElement);
		xmlElement.clearContent();
		out.endDocument();
		out.close();
		System.gc();
		//HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	
	
	//////////////////////
	private static void A15toXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
		File xmlFile = new File(path + "Tables/" + "A15.xml");// ���xml��·��
		fos = new FileOutputStream(xmlFile);
		osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
		bw = new BufferedWriter(osw);
		
		// ��xml�����ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		out = new XMLWriter(fos, format);
		//out = new XMLWriter(new FileWriter(xmlFile, true), format);
		
		out.setEscapeText(false);
		out.startDocument();
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� xml
		Element xmlElement = document.addElement("xml");
		out.writeOpen(xmlElement);
		
		//-------------------------------
		//Element rsdata = xmlElement.addElement("data");
		//out.writeOpen(rsdata);
		AttributesImpl a = new AttributesImpl();
		out.startElement("", "", "data", a);
		/**
		* ��������
		*/
		
		w.start();
		ResultSet rs = null;
		//pywu 20170608  a02�������ֶ�
		String query = "";
		query = " select "+TYGSsqlUtilTwo.A15zip+ " FROM A15 where A0000 in (select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')";
		
//		PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//		rs = pstmt.executeQuery();
		rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		//w.stop();
		//appendFileContent(logfilename, 
		//    SqlUtil.getSqlByTableName("A02", "t")
		//+ "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000) " +":"+""+w.elapsedTime()+"\n");
		//w.start();
		if (rs != null && rs.next()) {
		ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
		int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
		//����һ��
		rs.previous();
		while (rs.next()) {
		zrow = DocumentHelper.createElement("row");
		//	Element zrow = rsdata.addElement("row");
		
		for (int j = 1; j <= columnCount; j++) {
		if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0281")){
			Object obj = rs.getObject(j);
			if(obj!=null){
				if("true".equals(obj.toString())||"1".equals(obj.toString())){
					zrow.addAttribute(md.getColumnName(j),"1");
				}else{
					zrow.addAttribute(md.getColumnName(j),"0");
				}
			}else{
				zrow.addAttribute(md.getColumnName(j),"0");
			}
		}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0219")){
			Object obj = rs.getObject(j);
			if(obj!=null){
				if("1".equals(obj.toString())){
					zrow.addAttribute(md.getColumnName(j),"1");
				}else{
					zrow.addAttribute(md.getColumnName(j),"0");
				}
			}else{
				zrow.addAttribute(md.getColumnName(j),"0");
			}
		}else{
			zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		}
		}
		out.write(zrow);
		zrow.clearContent();
		}
		}
		w.stop();
		//appendFileContent(logfilename, "����a02xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
		
		rs.close();
		System.gc();
		
		//--------------------------------------------------
		//out.writeClose(rsdata);
		out.endElement("", "", "data");
		out.writeClose(xmlElement);
		xmlElement.clearContent();
		out.endDocument();
		out.close();
		System.gc();
		//HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	
	
	//////////////////////
	private static void B01toXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
		File xmlFile = new File(path + "Tables/" + "B01.xml");// ���xml��·��
		fos = new FileOutputStream(xmlFile);
		osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
		bw = new BufferedWriter(osw);
		
		// ��xml�����ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		out = new XMLWriter(fos, format);
		//out = new XMLWriter(new FileWriter(xmlFile, true), format);
		
		out.setEscapeText(false);
		out.startDocument();
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� xml
		Element xmlElement = document.addElement("xml");
		out.writeOpen(xmlElement);
		
		//-------------------------------
		//Element rsdata = xmlElement.addElement("data");
		//out.writeOpen(rsdata);
		AttributesImpl a = new AttributesImpl();
		out.startElement("", "", "data", a);
		/**
		* ��������
		*/
		
		w.start();
		ResultSet rs = null;
		//pywu 20170608  a02�������ֶ�
		String query = "";
		query = " select "+TYGSsqlUtilTwo.B01zip+ " FROM B01 ";
		
//		PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//		rs = pstmt.executeQuery();
		rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		//w.stop();
		//appendFileContent(logfilename, 
		//    SqlUtil.getSqlByTableName("A02", "t")
		//+ "where EXISTS (SELECT 1 from "+sql+" v where v.A0000=t.A0000) " +":"+""+w.elapsedTime()+"\n");
		//w.start();
		if (rs != null && rs.next()) {
		ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
		int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
		//����һ��
		rs.previous();
		while (rs.next()) {
		zrow = DocumentHelper.createElement("row");
		//	Element zrow = rsdata.addElement("row");
		
		for (int j = 1; j <= columnCount; j++) {
		if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0281")){
			Object obj = rs.getObject(j);
			if(obj!=null){
				if("true".equals(obj.toString())||"1".equals(obj.toString())){
					zrow.addAttribute(md.getColumnName(j),"1");
				}else{
					zrow.addAttribute(md.getColumnName(j),"0");
				}
			}else{
				zrow.addAttribute(md.getColumnName(j),"0");
			}
		}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("A0219")){
			Object obj = rs.getObject(j);
			if(obj!=null){
				if("1".equals(obj.toString())){
					zrow.addAttribute(md.getColumnName(j),"1");
				}else{
					zrow.addAttribute(md.getColumnName(j),"0");
				}
			}else{
				zrow.addAttribute(md.getColumnName(j),"0");
			}
		}else{
			zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		}
		}
		out.write(zrow);
		zrow.clearContent();
		}
		}
		w.stop();
		//appendFileContent(logfilename, "����a02xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
		
		rs.close();
		System.gc();
		
		//--------------------------------------------------
		//out.writeClose(rsdata);
		out.endElement("", "", "data");
		out.writeClose(xmlElement);
		xmlElement.clearContent();
		out.endDocument();
		out.close();
		System.gc();
		//HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	
	
	//////////////////////
	private static void FILEINFOtoXml(String path, Connection conn,String logfilename,String sign) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
		File xmlFile = new File(path + "Tables/" + "FILEINFO.xml");// ���xml��·��
		fos = new FileOutputStream(xmlFile);
		osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
		bw = new BufferedWriter(osw);
		
		// ��xml�����ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		out = new XMLWriter(fos, format);
		//out = new XMLWriter(new FileWriter(xmlFile, true), format);
		
		out.setEscapeText(false);
		out.startDocument();
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� xml
		Element xmlElement = document.addElement("xml");
		out.writeOpen(xmlElement);
		
		//-------------------------------
		//Element rsdata = xmlElement.addElement("data");
		//out.writeOpen(rsdata);
		AttributesImpl a = new AttributesImpl();
		out.startElement("", "", "data", a);
		/**
		* ��������
		*/
		
		w.start();
		ResultSet rs = null;
		//pywu 20170608  a02�������ֶ�
		String query = "";
		query = " select "+TYGSsqlUtilTwo.FILEINFOzip+ " FROM TABLEFILE WHERE  FILETYPE in ('kccl','dazs','ndkh') and A0000 in (select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')";
		
//		PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//		rs = pstmt.executeQuery();
		rs = sess.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		if (rs != null && rs.next()) {
		ResultSetMetaData md = rs.getMetaData(); // �õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����
		int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
		//����һ��
		rs.previous();
		while (rs.next()) {
		zrow = DocumentHelper.createElement("row");
		//	Element zrow = rsdata.addElement("row");
		
		for (int j = 1; j <= columnCount; j++) {
		if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("FILENAME")){
			//���ļ��������⴦��
			Object obj = rs.getObject(j);
			if(obj!=null){
				int begin=obj.toString().indexOf("_")+1;
				int last=obj.toString().lastIndexOf(".");
				String filename=obj.toString().substring(begin,last)+rs.getObject("ID").toString()+obj.toString().substring(last);
				zrow.addAttribute(md.getColumnName(j),filename);
			}else{
				zrow.addAttribute(md.getColumnName(j),"");
			}
		}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("FILETYPE")){
			//���ļ����������⴦��
			Object obj = rs.getObject(j);
			if(obj!=null){
				if("kccl".equals(obj.toString())){
					zrow.addAttribute(md.getColumnName(j),"�������");
				}
				else if("dazs".equals(obj.toString())){
					zrow.addAttribute(md.getColumnName(j),"����ר���");
				}else{
					zrow.addAttribute(md.getColumnName(j),"��ȿ��˵ǼǱ�");
				}
			}else{
				zrow.addAttribute(md.getColumnName(j),"");
			}
		}else if(("7z".equals(sign)||"zip".equals(sign)) && md.getColumnName(j).equals("CREATEDATE")){
			//�Դ���ʱ�������⴦��
			Object obj = rs.getObject(j);
			if(obj!=null){
				String caeatedate=obj.toString().replace("-", "/");
				zrow.addAttribute(md.getColumnName(j),caeatedate);
			}else{
				zrow.addAttribute(md.getColumnName(j),"");
			}
		}
		else{
			zrow.addAttribute(md.getColumnName(j),rs.getObject(j) != null ? rs.getObject(j).toString() : "");
		}
		}
		out.write(zrow);
		zrow.clearContent();
		}
		}
		w.stop();
		//appendFileContent(logfilename, "����a02xml:"+""+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");
		
		rs.close();
		System.gc();
		
		//--------------------------------------------------
		//out.writeClose(rsdata);
		out.endElement("", "", "data");
		out.writeClose(xmlElement);
		xmlElement.clearContent();
		out.endDocument();
		out.close();
		System.gc();
		//HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	
	//////////////////////
	private static void ConstanttoXml(String path, Connection conn,String logfilename,String sign,String linkpsnthis,String linktel) throws Exception {
		Element zrow = null;
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		StopWatch w = new StopWatch();
		w.start();
		try {
		File xmlFile = new File(path + "Tables/" + "Constant.xml");// ���xml��·��
		fos = new FileOutputStream(xmlFile);
		osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
		bw = new BufferedWriter(osw);
		
		// ��xml�����ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		out = new XMLWriter(fos, format);
		
		out.setEscapeText(false);
		out.startDocument();
		Document document = DocumentHelper.createDocument();
		// ���Ԫ�� xml
		Element xmlElement = document.addElement("xml");
		out.writeOpen(xmlElement);
		
		//-------------------------------
		//Element rsdata = xmlElement.addElement("data");
		//out.writeOpen(rsdata);
		AttributesImpl a = new AttributesImpl();
		out.startElement("", "", "data", a);
		/**
		* ��������
		*/
		
		w.start();
		
		
		zrow = DocumentHelper.createElement("row");
		//	Element zrow = rsdata.addElement("row");
		zrow.addAttribute("Version","V2019.06");
		zrow.addAttribute("DBUpdateDate",DateUtil.getCurrentDate());
		zrow.addAttribute("SubmittedPerson",linkpsnthis != null ? linkpsnthis : "");
		zrow.addAttribute("SubmittedTel",linktel != null ? linktel : "");
		out.write(zrow);
		zrow.clearContent();
		
		w.stop();
		//--------------------------------------------------
		//out.writeClose(rsdata);
		out.endElement("", "", "data");
		out.writeClose(xmlElement);
		xmlElement.clearContent();
		out.endDocument();
		out.close();
		System.gc();
		//HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
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
	//�޸�ʱ���ʽ
	private static String dateFormate(String str) {
		StringBuffer date = new StringBuffer(str);
		if(date.length()==6) {
			date = date.insert(4,"-");
			date.append("-01");
		}else if(date.length()==8) {
			date = date.insert(6,"-");
			date = date.insert(4,"-");
		}
		return date.toString();
	}
	// ����CLOBת��STRING���� 
    public static String ClobToString(Clob clob) throws SQLException, IOException { 
    	
        String reString = ""; 
        Reader is = clob.getCharacterStream();// �õ��� 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING 
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    } 
}
