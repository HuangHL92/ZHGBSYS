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

public class XmlSAXHandler02Zzb extends DefaultHandler { 
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
		public XmlSAXHandler02Zzb() {
		}
		
		public XmlSAXHandler02Zzb(String docname, String lowerCase, String table,
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
					HBSession sess = HBUtil.getHBSession();
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
								String A3601 = rowData.get("A3601")!=null?rowData.get("A3601").toString():"";
								rowData.put("A3601", subStringByByte(A3601 , 36));
								
								String A3604A = rowData.get("A3604A")!=null?rowData.get("A3604A").toString():"";
								//System.out.println(A3604A +"===============" +ConversionIndex.zb09(A3604A));
								rowData.put("A3604A", subStringByByte(A3604A , 10));
								
								String A3607 = rowData.get("A3607")!=null?rowData.get("A3607").toString():"";
								rowData.put("A3607", subStringByByte(A3607 , 8));
								
								String A3611 = rowData.get("A3611")!=null?rowData.get("A3611").toString():"";
								rowData.put("A3611", subStringByByte(A3611 , 200));
							}
						}
						if(table.equals("A02")){	
							//A02 特殊字符处理
							String A0243 = rowData.get("A0243")!=null?rowData.get("A0243").toString():"";
							rowData.put("A0243", subStringByByte(A0243, 8));
							String A0288 = rowData.get("A0288")!=null?rowData.get("A0288").toString():"";
							rowData.put("A0288", subStringByByte(A0288, 8));
							String A0247 = rowData.get("A0247")!=null?rowData.get("A0247").toString():"";
							rowData.put("A0247", subStringByByte(A0247, 2));
							String A0267 = rowData.get("A0267")!=null?rowData.get("A0267").toString():"";
							rowData.put("A0267", subStringByByte(A0267, 24));
							String A0229 = rowData.get("A0229")!=null?rowData.get("A0229").toString():"";
							rowData.put("A0229", subStringByByte(A0229, 120)); 
							String A0272 = rowData.get("A0272")!=null?rowData.get("A0272").toString():"";
							rowData.put("A0272", subStringByByte(A0272, 100));
							String A0225 = rowData.get("A0225")!=null?rowData.get("A0225").toString():"";
							rowData.put("A0225", subStringByByte(A0225, 8));
							if(lowerCase.equalsIgnoreCase("zb3")){
								//免职层次
								String A0221 = rowData.get("A0221")!=null?rowData.get("A0221").toString():"";
								rowData.put("A0221", ConversionIndex.zb09(A0221));
								//免职
								String A0271 = rowData.get("A0271")!=null?rowData.get("A0271").toString():"";
								rowData.put("A0271", ConversionIndex.ZB16(A0271));
								//职务
								//String A0215A = rowData.get("A0215A")!=null?rowData.get("A0215A").toString():"";
								//rowData.put("A0215A", ConversionIndex.ZB08(A0215A.toUpperCase()));
								
								String A0201A = rowData.get("A0201A")!=null?rowData.get("A0201A").toString():"";
								if(!A0201A.equals("")){
									String arr[] = A0201A.split("\\|");
									if(arr.length >0){
										rowData.put("A0201A", subStringByByte(arr[arr.length-1], 200));
									}
									rowData.put("A0201A_ALL", subStringByByte(A0201A, 200));
								}
								//职务名称
								//rowData.put("A0215A", rowData.get("A0215B"));
								rowData.put("A0215A", subStringByByte(rowData.get("A0215B")+"", 100));
							}
							
							String A0215A = rowData.get("A0215A")!=null?rowData.get("A0215A").toString():"";
							rowData.put("A0215A", subStringByByte(A0215A, 100));
							
							if(lowerCase.equalsIgnoreCase("zb3")||lowerCase.equalsIgnoreCase("7z")||lowerCase.equalsIgnoreCase("zip")){
								String A0281 = rowData.get("A0281")!=null?rowData.get("A0281").toString():"";
								rowData.put("A0281", (A0281!=null &&  A0281.equals("1"))?"true":"false");
							}
							if(lowerCase.equalsIgnoreCase("7z")||lowerCase.equalsIgnoreCase("zip")){
								String A0219 = rowData.get("A0219")!=null?rowData.get("A0219").toString():"";
								rowData.put("A0219", (A0219!=null &&  A0219.equals("1"))?"1":"2");
							}
							//String A0215B = rowData.get("A0215B")!=null?rowData.get("A0215B").toString():"";
							//rowData.put("A0215B", subStringByByte(A0215B, 80));a
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
							
							String A0201A = rowData.get("A0201A")!=null?rowData.get("A0201A").toString():"";
							rowData.put("A0201A", subStringByByte(A0201A, 200));
						}else if(table.equals("A05")){
							if(lowerCase.equalsIgnoreCase("7z")||lowerCase.equalsIgnoreCase("zip")){
								String A0531 = rowData.get("A0531")!=null?rowData.get("A0531").toString():"";
								if("0".equals(A0531)){
									rowData.put("A0531", "1");
								}else if("1".equals(A0531)){
									rowData.put("A0531", "0");
								}
							}
						}else if(table.equals("A06")){ 
							String A0611 = rowData.get("A0611")!=null?rowData.get("A0611").toString():"";
							rowData.put("A0611", subStringByByte(A0611, 100)); 
							
							String A0614 = rowData.get("A0614")!=null?rowData.get("A0614").toString():"";
							rowData.put("A0614", subStringByByte(A0614, 1)); 
							
							if(lowerCase.equalsIgnoreCase("zb3")){
								rowData.put("A0699", "true");
							}
							if(lowerCase.equalsIgnoreCase("7z")||lowerCase.equalsIgnoreCase("zip")){
								String A0699 = rowData.get("A0699")!=null?rowData.get("A0699").toString():"";
								rowData.put("A0699", (A0699!=null &&  A0699.equals("1"))?"true":A0699!=null &&  A0699.equals("0")?"false":"true");
							}
						}else if(table.equals("A08")){
							String A0804 = rowData.get("A0804")!=null?rowData.get("A0804").toString():"";
							rowData.put("A0804", subStringByByte(A0804, 8)); 
							
							String A0807 = rowData.get("A0807")!=null?rowData.get("A0807").toString():"";
							rowData.put("A0807", subStringByByte(A0807, 8)); 
							
							String A0811 = rowData.get("A0811")!=null?rowData.get("A0811").toString():"";
							rowData.put("A0811", subStringByByte(A0811, 8)); 
							
							String A0814 = rowData.get("A0814")!=null?rowData.get("A0814").toString():"";
							rowData.put("A0814", subStringByByte(A0814, 120)); 
							
							String A0901A = rowData.get("A0901A")!=null?rowData.get("A0901A").toString():"";
							rowData.put("A0901A", subStringByByte(A0901A, 40)); 
							
							String A0824 = rowData.get("A0824")!=null?rowData.get("A0824").toString():"";
							rowData.put("A0824", subStringByByte(A0824, 40)); 
							
							//专业GB16835
							String A0827 = rowData.get("A0827")!=null?rowData.get("A0827").toString():"";
							if(lowerCase.equalsIgnoreCase("zb3")){
								//教育
								String A0801B = rowData.get("A0801B")!=null?rowData.get("A0801B").toString():"";
								rowData.put("A0801B", ConversionIndex.ZB64(A0801B));
								
								if(!A0827.trim().equals("")){
									String reg = "^\\d+$"; 
									if(A0827.matches(reg)){
										/*if(A0827.equals("1009")){
											rowData.put("A0827", "906");
										} else if (A0827.equals("100901")){
											rowData.put("A0827", "90653");
										} else if (A0827.equals("100999")){
											rowData.put("A0827", "90699");
										} else if (A0827.equals("090651")){
											rowData.put("A0827", "20257");
//										} else if (A0827.equals("071602")){
//											rowData.put("A0827", "071");
										} else if(A0827.equals("11")){
											rowData.put("A0827", "");
										} else {
											rowData.put("A0827", Long.parseLong(A0827)+"");
										}*/
										if(A0827.equals("1009")){
											rowData.put("A0827", "11");
										} else if (A0827.equals("100901")){
											rowData.put("A0827", "110302");
										} else if (A0827.equals("100999")){
											rowData.put("A0827", "110320");
										} else if (A0827.equals("090651")){
											rowData.put("A0827", "");
//										} else if (A0827.equals("071602")){
//											rowData.put("A0827", "071");
										} else if(A0827.equals("11")){
											rowData.put("A0827", "");
										} else {
											rowData.put("A0827", Long.parseLong(A0827)+"");
										}
									} else {
										rowData.put("A0827", "");
									}
								} else {
									rowData.put("A0827", "");
								}
							}
							if(lowerCase.equalsIgnoreCase("zb3") || lowerCase.equalsIgnoreCase("7z")){
								String a0899 = rowData.get("A0899")!=null?rowData.get("A0899").toString():"";
								rowData.put("A0899", (a0899!=null &&  a0899.equals("1"))?"true":"false");
							}
							if(lowerCase.equalsIgnoreCase("zip")){
								String A0898 = rowData.get("A0898")!=null?rowData.get("A0898").toString():"";
								rowData.put("A0899", (A0898!=null &&  A0898.equals("1"))?"true":"false");
							}
							
						} /*else if(table.equals("A37")){
							String A3708 = rowData.get("A3708")!=null?rowData.get("A3708").toString():"";
							if(A3708.length()>60){
								rowData.put("A3708", subStringByByte(A3708, 60));
							}
						} else if(table.equals("A31")){
							if(lowerCase.equalsIgnoreCase("hzb")){
								String A3140 = rowData.get("A3140")!=null?rowData.get("A3140").toString():"";
								String A3141 = rowData.get("A3141")!=null?rowData.get("A3141").toString():"";
								String A3142 = rowData.get("A3142")!=null?rowData.get("A3142").toString():"";
								if(!A3140.equals("")){
									rowData.put("A3110", A3140);
								}
								if(!A3141.equals("")){
									rowData.put("A3109", A3141);
								}
								if(!A3142.equals("")){
									rowData.put("A3108", A3142);
								}
							} else {
								String A3107 = rowData.get("A3107")!=null?rowData.get("A3107").toString():"";
								rowData.put("A3107", ConversionIndex.zb09(A3107));
							}
						}*/ else if(table.equals("A14")){
							if(lowerCase.equalsIgnoreCase("zb3")){
								String A1415 = rowData.get("A1415")!=null?rowData.get("A1415").toString():"";
								rowData.put("A1415", ConversionIndex.zb09(A1415));
								String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
								rowData.put("A1404B", ConversionIndex.ZB65(A1404B));
							}
							String A1411A = rowData.get("A1411A")!=null?rowData.get("A1411A").toString():"";
							rowData.put("A1411A", subStringByByte(A1411A, 60));
							
							String A1404A = rowData.get("A1404A")!=null?rowData.get("A1404A").toString():"";
							rowData.put("A1404A", subStringByByte(A1404A, 40));
							
							String A1407 = rowData.get("A1407")!=null?rowData.get("A1407").toString():"";
							rowData.put("A1407", subStringByByte(A1407, 8));
							
							String A1414 = rowData.get("A1414")!=null?rowData.get("A1414").toString():"";
							rowData.put("A1414", subStringByByte(A1414, 8));
							String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
							rowData.put("A1404B", subStringByByte(A1404B, 8));
						} else if(table.equals("A29")){
							String A2941 = rowData.get("A2941")!=null?rowData.get("A2941").toString():"";
							if(A2941.length()>20){
								rowData.put("A2941", subStringByByte(A2941, 40));
							}
							String A2949 = rowData.get("A2949")!=null?rowData.get("A2949").toString():"";
							if(A2949.length()>8){
								rowData.put("A2949", subStringByByte(A2949, 8));
							}
							if(lowerCase.equalsIgnoreCase("zb3")){
								String A2911 = rowData.get("A2911")!=null?rowData.get("A2911").toString():"";
								rowData.put("A2911", subStringByByte(ConversionIndex.ZB77(A2911), 3));
							}
						} /* else if(table.equals("A30")){
							if(lowerCase.equalsIgnoreCase("zb3")){
								String A3001 = rowData.get("A3001")!=null?rowData.get("A3001").toString():"";
								rowData.put("A3001", ConversionIndex.ZB78(A3001));
							}
						}*/ else if(table.equals("A15")){
							String A1521 = rowData.get("A1521")!=null?rowData.get("A1521").toString():"";
							rowData.put("A1521", subStringByByte(A1521, 4));
							
							String A1517 = rowData.get("A1517")!=null?rowData.get("A1517").toString():"";
							rowData.put("A1517", subStringByByte(A1517, 4));
						} /*else if(table.equals("B01_EXT")){
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
							String A1131 = rowData.get("A1131")!=null?rowData.get("A1131").toString():"";
							if(A1131.length()>30){
								rowData.put("A1131", subStringByByte(A1131, 60));
							}
							if(lowerCase.equalsIgnoreCase("zb3")){
								String A1101 = rowData.get("A1101")!=null?rowData.get("A1101").toString():"";
								rowData.put("A1101", ConversionIndex.ZB29(A1101));
								String A1127 = rowData.get("A1127")!=null?rowData.get("A1127").toString():"";
								rowData.put("A1127", ConversionIndex.ZB27(A1127));
							}
						}*/  else if(table.equals("B01")){
							String b0121 = rowData.get("B0121")!=null?rowData.get("B0121").toString():"";
							String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
							/*if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
										&& deptid != null) {
								rowData.put("B0121", deptid.toString());
							} else {
								rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
							}*/
							/*if(lowerCase.equalsIgnoreCase("7z")){
								if("001.001".equals(b0111)){
									b0121 = "-1";
								}else{
									b0121 = b0111.substring(0, b0111.length()-4);
								}
							}*/
							if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
										&& deptid != null) {
								rowData.put("B0121", deptid.toString());
							} else {
								rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
							}

							String B0131 = rowData.get("B0131")!=null?rowData.get("B0131").toString():"";
							rowData.put("B0131", subStringByByte(B0131, 4));
							
							String B0117 = rowData.get("B0117")!=null?rowData.get("B0117").toString():"";
							rowData.put("B0117", subStringByByte(B0117, 6));
							String B0127 = rowData.get("B0127")!=null?rowData.get("B0127").toString():"";
							rowData.put("B0127", subStringByByte(B0127, 4));
							String B0239 = rowData.get("B0239")!=null?rowData.get("B0239").toString():"";
							rowData.put("B0239", subStringByByte(B0239, 24));
							
							rowData.put("B0111", impdeptid + (b0111.substring(B0111.length()))); 
							if(lowerCase.equalsIgnoreCase("zb3")){
								//String B0131 = rowData.get("B0131")!=null?rowData.get("B0131").toString():"";
								rowData.put("B0131", ConversionIndex.ZB04(B0131));
								String B0124 = rowData.get("B0124")!=null?rowData.get("B0124").toString():"";
								rowData.put("B0124", subStringByByte(ConversionIndex.ZB87(B0124),2));
							} else {
								String CREATE_DATE = rowData.get("CREATE_DATE")!=null?rowData.get("CREATE_DATE").toString():"";
								String UPDATE_DATE = rowData.get("UPDATE_DATE")!=null?rowData.get("UPDATE_DATE").toString():"";
								rowData.put("CREATE_DATE", DateUtil.stringToTime2(CREATE_DATE));
								rowData.put("UPDATE_DATE", DateUtil.stringToTime2(UPDATE_DATE));
							}
							
							String B0191 = rowData.get("B0191")!=null?rowData.get("B0191").toString():"";
							if(B0191.length()>8){
								rowData.put("B0191", subStringByByte(B0191, 8));
							}
						}  else if(table.equals("A01")){ 
							String ZGXLXX = rowData.get("ZGXLXX")!=null?rowData.get("ZGXLXX").toString():"";
							rowData.put("ZGXLXX", subStringByByte(ZGXLXX, 120));
							
							String ZZXLXX = rowData.get("ZZXLXX")!=null?rowData.get("ZZXLXX").toString():"";
							rowData.put("ZZXLXX", subStringByByte(ZZXLXX, 120));
							
							String ZZXWXX = rowData.get("ZZXWXX")!=null?rowData.get("ZZXWXX").toString():"";
							rowData.put("ZZXWXX", subStringByByte(ZZXWXX, 120));
							
							String ZGXWXX = rowData.get("ZGXWXX")!=null?rowData.get("ZGXWXX").toString():"";
							rowData.put("ZGXWXX", subStringByByte(ZGXWXX, 120));
							
							String QRZXLXX = rowData.get("QRZXLXX")!=null?rowData.get("QRZXLXX").toString():"";
							rowData.put("QRZXLXX", subStringByByte(QRZXLXX, 120));
							
							String QRZXWXX = rowData.get("QRZXWXX")!=null?rowData.get("QRZXWXX").toString():"";
							rowData.put("QRZXWXX", subStringByByte(QRZXWXX, 120));
							
							String A14Z101 = rowData.get("A14Z101")!=null?rowData.get("A14Z101").toString():"";
							rowData.put("A14Z101", subStringByByte(A14Z101, 2000));
							
							String ZGXW = rowData.get("ZGXW")!=null?rowData.get("ZGXW").toString():"";
							rowData.put("ZGXW", subStringByByte(ZGXW, 60));
							
							String TCSJSHOW = rowData.get("TCSJSHOW")!=null?rowData.get("TCSJSHOW").toString():"";
							rowData.put("TCSJSHOW", subStringByByte(TCSJSHOW, 16));
							
							String TCFSSHOW = rowData.get("TCFSSHOW")!=null?rowData.get("TCFSSHOW").toString():"";
							rowData.put("TCFSSHOW", subStringByByte(TCFSSHOW, 40));
							
							String A0196 = rowData.get("A0196")!=null?rowData.get("A0196").toString():"";
							rowData.put("A0196", subStringByByte(A0196, 120));
							
							String A2949 = rowData.get("A2949")!=null?rowData.get("A2949").toString():"";
							rowData.put("A2949", subStringByByte(A2949, 8));
							
							String RMLY = rowData.get("RMLY")!=null?rowData.get("RMLY").toString():"";
							rowData.put("RMLY", subStringByByte(RMLY, 100));//RMLY
							
							String A0111A = rowData.get("A0111A")!=null?rowData.get("A0111A").toString():"";
							rowData.put("A0111A", subStringByByte(A0111A, 200));
							
							String A0114A = rowData.get("A0114A")!=null?rowData.get("A0114A").toString():"";
							rowData.put("A0114A", subStringByByte(A0114A, 200));
							
							String A0115A = rowData.get("A0115A")!=null?rowData.get("A0115A").toString():"";
							rowData.put("A0115A", subStringByByte(A0115A, 200));
							
							String A0195 = rowData.get("A0195")!=null?rowData.get("A0195").toString():"";
							if(!A0195.equals("")&& !A0195.equals("XXX") && !A0195.equals("-1")){
								/*if(A0195.length()<B0111.length()){
									rowData.put("A0195", "-1");
								} else {
									rowData.put("A0195", impdeptid + (A0195.substring(B0111.length())));
								}*/
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
							if(lowerCase.equalsIgnoreCase("zb3")){
								String A015A = rowData.get("A015A")!=null?rowData.get("A015A").toString():"";
								rowData.put("A015A", subStringByByte(A015A, 2));
								String a0155 = rowData.get("A0155")!=null?rowData.get("A0155").toString():"";
								rowData.put("A0155", DateUtil.stringToDate_Size6_8(a0155));
								String TBSJ = rowData.get("TBSJ")!=null?rowData.get("TBSJ").toString():"";
								rowData.put("TBSJ", DateUtil.stringToDate_Size6_8(a0155));
								String XGSJ = rowData.get("XGSJ")!=null?rowData.get("XGSJ").toString():"";
								rowData.put("XGSJ", DateUtil.stringToDate_Size6_8(a0155));
								rowData.put("JSNLSJ", null);
								String a0107 = rowData.get("A0107")!=null?rowData.get("A0107").toString():"";
								String A0160 = rowData.get("A0160")!=null?rowData.get("A0160").toString():"";
								String A0163 = rowData.get("A0163")!=null?rowData.get("A0163").toString():"";
								//
								String A0148 = rowData.get("A0148")!=null?rowData.get("A0148").toString():"";
								rowData.put("A0148", ConversionIndex.zb09(A0148));
								//编制类型转换
								if(A0163.equals("1")&&(A0160.equals("1")||A0160.equals("2")||A0160.equals("3")||A0160.equals("5"))){
									rowData.put("A0121", "1");
								} else if(A0163.equals("1")&&(A0160.equals("6"))){
									rowData.put("A0121", "2");
								} else if(A0163.equals("1")&&(A0160.equals("7")||A0160.equals("8"))){
									rowData.put("A0121", "3");
								} else if(A0163.equals("1")&&(A0160.equals("A0")||A0160.equals("A1"))){
									rowData.put("A0121", "4");
								} else {
									rowData.put("A0121", "9");
								}
								if(A0163.equals("1")){
									rowData.put("STATUS", "1");
								} else if(A0163.equals("2")){
									rowData.put("STATUS", "3");
								} else if(A0163.equals("3") || A0163.equals("4") || A0163.equals("5")){
									rowData.put("STATUS", "2");
								}
								rowData.put("A0160", ConversionIndex.A0160(A0160));
								rowData.put("A0163", ConversionIndex.A0163(A0163));
								
							} else {
								if(lowerCase.equalsIgnoreCase("7z")||lowerCase.equalsIgnoreCase("zip")){
									rowData.put("STATUS", "1");
								}
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
							
						}
				    	for (int j = 0; j < colomns.size(); j++) {
				    		if((lowerCase.equalsIgnoreCase("7z")||lowerCase.equalsIgnoreCase("zip"))&&("A0200".equals(colomns.get(j).toString().toUpperCase())
				    				||"A0500".equals(colomns.get(j).toString().toUpperCase())||"A0600".equals(colomns.get(j).toString().toUpperCase())
				    				||"A0800".equals(colomns.get(j).toString().toUpperCase())||"A1400".equals(colomns.get(j).toString().toUpperCase())
				    				||"A1500".equals(colomns.get(j).toString().toUpperCase())||"A3600".equals(colomns.get(j).toString().toUpperCase())
				    				||"A99Z100".equals(colomns.get(j).toString().toUpperCase()))){
				    			pstmt1.setObject(j+1, UUID.randomUUID().toString());
				    		}else{
				    			if("A3038".equalsIgnoreCase(colomns.get(j).toString())) {
				    				pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString().toUpperCase())==null||
				    						rowData.get(colomns.get(j).toString().toUpperCase()).equals("")?null:subStringByByte(rowData.get(colomns.get(j).toString().toUpperCase()).toString(), 100));
				    				
				    			}else {
				    				pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString().toUpperCase())==null||
				    						rowData.get(colomns.get(j).toString().toUpperCase()).equals("")?null:rowData.get(colomns.get(j).toString().toUpperCase()));
				    			}
				    		}
						}
				    	pstmt1.addBatch();
				    	t_n++;
//				    	System.out.println(t_n);
/*				    	if(table.equals("A14")){
				    		batchNum = 1;
				    	}*/
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