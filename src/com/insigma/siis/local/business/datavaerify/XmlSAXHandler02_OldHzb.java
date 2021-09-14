package com.insigma.siis.local.business.datavaerify;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;

public class XmlSAXHandler02_OldHzb extends DefaultHandler {
		public String docname;
		public String lowerCase;
		public String table;
		public String imprecordid;
		public int t_n;
		public String uuid;
		public String from_file;
		public String B0111;
		public String impdeptid;
		public String deptid;
		public PreparedStatement pstmt1;
		public Connection conn1;
		public List colomns;
		public int batchNum;
		public boolean isError = false;
		public XmlSAXHandler02_OldHzb() {
		}
		
		public XmlSAXHandler02_OldHzb(String docname, String lowerCase, String table,
				String imprecordid, int t_n, String uuid, String from_file,
				String B0111, String deptid, String impdeptid, Connection conn1, PreparedStatement pstmt1, List colomns,int batchNum) {
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
			this.conn1= conn1;
			this.colomns = colomns;
			this.batchNum = batchNum;
		}
		
		@Override
		public void startDocument() throws SAXException {
		}
		
		@Override
		public void endDocument() throws SAXException {
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if(!isError && (qName.equals("z:row")||qName.equals("row"))){
				Map rowData = new HashMap(30);
				if(attributes.getLength()>0){
					for(int i=0;i<attributes.getLength();i++){
						rowData.put(attributes.getQName(i).toUpperCase(), StringEscapeUtils.unescapeXml(attributes.getValue(i)));
					}
					rowData.put("IMPRECORDID", imprecordid);
					rowData.put("IS_QUALIFIED", "0");
					rowData.put("ERROR_INFO", "2");
					try {
						//--------------------------------------------------------------------------------------------------------------
						if(table.equals("A36")){
							if((rowData.get("A3601")==null || rowData.get("A3601").toString().equals(""))
									&&(rowData.get("A3604A")==null || rowData.get("A3604A").toString().equals(""))
									&&(rowData.get("A3607")==null || rowData.get("A3607").toString().equals(""))
									&&(rowData.get("A3611")==null || rowData.get("A3611").toString().equals(""))
									&&(rowData.get("A3627")==null || rowData.get("A3627").toString().equals(""))){
								return;
							} else {
								String A3604A = rowData.get("A3604A")!=null?rowData.get("A3604A").toString():"";
								rowData.put("A3604A", ConversionIndexOldHzb.GB4761_ch(A3604A));
//								System.out.println(A3604A + "----------------" + ConversionIndexOldHzb.GB4761_ch(A3604A));
								
								String A3627 = rowData.get("A3627")!=null?rowData.get("A3627").toString():"";
								rowData.put("A3627", ConversionIndexOldHzb.GB4762(A3627));
//								System.out.println(A3627 + "----------------" + ConversionIndexOldHzb.GB4762(A3627));
							}
						}
						if(table.equals("A02")){												//A02 特殊字符处理
//							String A0201A = rowData.get("A0201A")!=null?rowData.get("A0201A").toString():"";
//							if(A0201A.trim().equals("|")){
//								rowData.put("A0201A", "");
//							} else {
//								rowData.put("A0201A", A0201A);
//							}
							String A0267 = rowData.get("A0267")!=null?rowData.get("A0267").toString():"";
							rowData.put("A0267", subStringByByte(A0267, 24));
							String A0229 = rowData.get("A0229")!=null?rowData.get("A0229").toString():"";
							rowData.put("A0229", subStringByByte(A0229, 120)); 
							//职务名称
							rowData.put("A0215A", rowData.get("A0215B"));
							
							String A0247 = rowData.get("A0247")!=null?rowData.get("A0247").toString():"";
							rowData.put("A0247", ConversionIndexOldHzb.ZB122(A0247));
							//ZB16 免职类别
							String A0271 = rowData.get("A0271")!=null?rowData.get("A0271").toString():"";
							rowData.put("A0271", ConversionIndexOldHzb.ZB16(A0271));
							
							//职务层次为空，获取职务等级 
							String A0221 = rowData.get("A0221")!=null?rowData.get("A0221").toString():"";
							if(A0221.equals("")){
								String A0221A = rowData.get("A0221A")!=null?rowData.get("A0221A").toString():"";
								rowData.put("A0221A", ConversionIndexOldHzb.ZB136(A0221A));
							} else {
								rowData.put("A0221", ConversionIndexOldHzb.ZB09(A0221));
							}
							
							String A0201B = rowData.get("A0201B")!=null?rowData.get("A0201B").toString():"";
							if(!A0201B.equals("") && !A0201B.equals("XXX") && !A0201B.equals("-1")){
								if(A0201B.length()<B0111.length()){
									rowData.put("A0201B", "-1");
								} else {
									if(A0201B.startsWith(B0111)){
										rowData.put("A0201B", impdeptid + (A0201B.substring(B0111.length())));
									}else{
										rowData.put("A0201B", "-1");
									}
								}
							} else if(A0201B.equals("XXX")){
								rowData.put("A0201B", "-1");
							}
							
						} else if(table.equals("A61")){
							String A2970 = rowData.get("A2970")!=null?rowData.get("A2970").toString():"";
							rowData.put("A2970", ConversionIndexOldHzb.ZB137(A2970));
							String A2970A = rowData.get("A2970A")!=null?rowData.get("A2970A").toString():"";
							rowData.put("A2970A", ConversionIndexOldHzb.ZB138(A2970A));
						} else if(table.equals("A63")){
							String A6304 = rowData.get("A6304")!=null?rowData.get("A6304").toString():"";
							rowData.put("A6304", ConversionIndexOldHzb.ZB141(A6304));
							String A6305 = rowData.get("A6305")!=null?rowData.get("A6305").toString():"";
							rowData.put("A6305", ConversionIndexOldHzb.ZB145(A6305));
							
						}  else if(table.equals("A31")){
							String A3107 = rowData.get("A3107")!=null?rowData.get("A3107").toString():"";
							rowData.put("A3107", ConversionIndexOldHzb.ZB09(A3107));
						} else if(table.equals("A14")){
							String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
							rowData.put("A1404B", ConversionIndexOldHzb.ZB65(A1404B));
							
							String A1415 = rowData.get("A1415")!=null?rowData.get("A1415").toString():"";
							rowData.put("A1415", ConversionIndexOldHzb.ZB09(A1415));
						} else if(table.equals("A15")){
							//???/////
							/*String A1521 = rowData.get("A1521")!=null?rowData.get("A1521").toString():"";
							rowData.put("A1521", A1521.equals("")?"000101":A1521);*/
						} else if(table.equals("B01_EXT")){
							String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
							if(!b0111.equals("")){
								rowData.put("B0111", impdeptid + (b0111.substring(B0111.length())));
							}
						} else if(table.equals("A11")){
							String a1107 = rowData.get("A1107")!=null?rowData.get("A1107").toString():"";
							String a1111 = rowData.get("A1111")!=null?rowData.get("A1111").toString():"";
							if(a1107!=null && !a1107.equals("") && (a1107.length()==6||a1107.length()==8)
									&& a1111!=null && !a1111.equals("")&&(a1111.length()==6||a1111.length()==8)){
								a1107 = (a1107 + "01").substring(0, 8);
								a1111 = (a1111 + "01").substring(0, 8);
								int d = 0;
								try {
									d = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyyMMdd"), 
											DateUtil.stringToDate(a1111, "yyyyMMdd"));
								} catch (Exception e) {
								}
								rowData.put("A1107C", d);
							} else {
								rowData.put("A1107C", 0);
							}
							
							String A1127 = rowData.get("A1127")!=null?rowData.get("A1127").toString():"";
							rowData.put("A1127", ConversionIndexOldHzb.ZB27(A1127));
							
						}  else if(table.equals("B01")){
							String b0121 = rowData.get("B0121")!=null?rowData.get("B0121").toString():"";
							String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
							/*if(lowerCase.equalsIgnoreCase("zip")){
								if("001.001".equals(b0111)){
									b0121 = "-1";
								}else{
									b0121 = b0111.substring(0, b0111.length()-4);
								}
							}*/
							if ((b0121 == null || b0121.equals("") || b0111.equals(B0111)) && deptid != null) {
								rowData.put("B0121", deptid.toString());
							} else {
								rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
							}
							rowData.put("B0111", impdeptid + (b0111.substring(B0111.length())));
							
							String B0131 = rowData.get("B0131")!=null?rowData.get("B0131").toString():"";
							rowData.put("B0131", ConversionIndexOldHzb.ZB04(B0131));
							
							String CREATE_DATE = rowData.get("CREATE_DATE")!=null?rowData.get("CREATE_DATE").toString():"";
							String UPDATE_DATE = rowData.get("UPDATE_DATE")!=null?rowData.get("UPDATE_DATE").toString():"";
							rowData.put("CREATE_DATE", DateUtil.stringToTime2(CREATE_DATE));
							rowData.put("UPDATE_DATE", DateUtil.stringToTime2(UPDATE_DATE));
							
						}  else if(table.equals("A01")){
							String A0195 = rowData.get("A0195")!=null?rowData.get("A0195").toString():"";
							if(!A0195.equals("")&& !A0195.equals("XXX") && !A0195.equals("-1")){
								if(A0195.length()<B0111.length()){
									rowData.put("A0195", "-1");
								} else {
									if(A0195.startsWith(B0111)){
										rowData.put("A0195", impdeptid + (A0195.substring(B0111.length())));
									}else{
										rowData.put("A0195", "-1");
									}
								}
								
							} else if(A0195.equals("XXX")){
								rowData.put("A0195", "-1");
							}
							String A0160 = rowData.get("A0160")!=null?rowData.get("A0160").toString():"";
							rowData.put("A0160",ConversionIndexOldHzb.ZB125(A0160));
							String A0163 = rowData.get("A0163")!=null?rowData.get("A0163").toString():"";
							rowData.put("A0163",ConversionIndexOldHzb.ZB126(A0163));
							String A0120 = rowData.get("A0120")!=null?rowData.get("A0120").toString():"";
							rowData.put("A0120",ConversionIndexOldHzb.ZB134(A0120));
							String A0121 = rowData.get("A0121")!=null?rowData.get("A0121").toString():"";
							rowData.put("A0121",ConversionIndexOldHzb.ZB135(A0121));
							String A0192D = rowData.get("A0192D")!=null?rowData.get("A0192D").toString():"";
							rowData.put("A0192D",ConversionIndexOldHzb.ZB133(A0192D));
							
							
							String a0155 = rowData.get("A0155")!=null?rowData.get("A0155").toString():"";
							rowData.put("A0155", DateUtil.stringToTime2(a0155));
							String TBSJ = rowData.get("TBSJ")!=null?rowData.get("TBSJ").toString():"";
							rowData.put("TBSJ", DateUtil.stringToTime2(TBSJ));
							String XGSJ = rowData.get("XGSJ")!=null?rowData.get("XGSJ").toString():"";
							rowData.put("XGSJ", DateUtil.stringToTime2(XGSJ));
							rowData.put("JSNLSJ", null);
							String ORGID = rowData.get("ORGID")!=null?rowData.get("ORGID").toString():"";
							if(!ORGID.equals("")&& !ORGID.equals("XXX") && !ORGID.equals("-1")){
								if(ORGID.length()<B0111.length()){
									rowData.put("ORGID", "-1");
								} else {
									rowData.put("ORGID", impdeptid + (ORGID.substring(B0111.length())));
								}
								
							} else if(ORGID.equals("XXX")){
								rowData.put("ORGID", "-1");
							}
						
							
						}
				    	for (int j = 0; j < colomns.size(); j++) {
				    		if(lowerCase.equalsIgnoreCase("zip")&&("A0200".equals(colomns.get(j).toString().toUpperCase())
				    				||"A0500".equals(colomns.get(j).toString().toUpperCase())||"A0600".equals(colomns.get(j).toString().toUpperCase())
				    				||"A0800".equals(colomns.get(j).toString().toUpperCase())||"A1400".equals(colomns.get(j).toString().toUpperCase())
				    				||"A1500".equals(colomns.get(j).toString().toUpperCase())||"A3600".equals(colomns.get(j).toString().toUpperCase())
				    				||"A99Z100".equals(colomns.get(j).toString().toUpperCase()))){
				    			pstmt1.setObject(j+1, UUID.randomUUID().toString());
				    		}else{
				    			pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString().toUpperCase())==null||
				    					rowData.get(colomns.get(j).toString().toUpperCase()).equals("")?null:rowData.get(colomns.get(j).toString().toUpperCase()));
				    		}
						}
				    	pstmt1.addBatch();
				    	t_n++;
//				    	System.out.println(t_n);
				    	if(t_n % batchNum == 0){
				    		pstmt1.executeBatch();
				    		pstmt1.clearBatch();
//				    		appendFileContent("D:\\ceShiDaoRuShiJian.txt",  table+"每"+batchNum+"时间"+DateUtil.getTime()+"\n");
//				    		System.out.println(table+"======  NO."+t_n/batchNum+"  ========"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				    		System.gc();
				    	}
				    	
						rowData.clear();
						rowData=null;
//						sess.flush();
						
					} catch (Exception e1) {
						isError = true;
						e1.printStackTrace();
						try {
							KingbsconfigBS.saveImpDetail("3" ,"4","失败:"+e1.getMessage(),uuid);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
//						new UploadHzbFileBS().rollbackImp(imprecordid);
					}
				}
			} else {
				return;
			}
			
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
		}
		
		public void appendFileContent(String fileName, String content) {
	        try {
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
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