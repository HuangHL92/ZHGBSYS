package com.insigma.siis.local.business.datavaerify;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class MySaxHandlerT implements ElementHandler {
	
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
	public MySaxHandlerT() {
	}
	
	public MySaxHandlerT(String docname, String lowerCase, String table,
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
	public void onStart(ElementPath ep) {
		if(isError){
			return;
		}
		Element element = ep.getCurrent(); // 获得当前节点
		if(element!=null){
			Map rowData = new HashMap();
			List<Attribute> attrs = element.attributes();  
			for (Attribute attr : attrs) {  
				rowData.put(attr.getName().toUpperCase(), attr.getValue());
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
						element.detach(); // 记得从内存中移去
						return;
					}
				}
				if(table.equals("A02")){												//A02 特殊字符处理
					//职务
					String A0215A = rowData.get("A0215A")!=null?rowData.get("A0215A").toString():"";
					if(!A0215A.equals("")){
						rowData.put("A0215A", A0215A.toUpperCase());
					}
					String A0267 = rowData.get("A0267")!=null?rowData.get("A0267").toString():"";
					rowData.put("A0267", subStringByByte(A0267, 24));
					String A0229 = rowData.get("A0229")!=null?rowData.get("A0229").toString():"";
					rowData.put("A0229", subStringByByte(A0229, 120)); 
					if(lowerCase.equalsIgnoreCase("zb3")){
						String A0247 = rowData.get("A0247")!=null?rowData.get("A0247").toString():"";
						if(A0247.equals("1")){
							rowData.put("A0247", "11");
						} else if(A0247.equals("2")){
							rowData.put("A0247", "12");
						} else {
							rowData.put("A0247", "");
						}
						String A0201A = rowData.get("A0201A")!=null?rowData.get("A0201A").toString():"";
						if(!A0201A.equals("")){
							String arr[] = A0201A.split("\\|");
							if(arr.length >0){
								rowData.put("A0201A", arr[arr.length-1]);
							}
						}
			    		String A0281 = rowData.get("A0281")!=null?rowData.get("A0281").toString():"";
						rowData.put("A0281", (A0281!=null &&  A0281.equals("1"))?"true":"false");
						//职务名称
						rowData.put("A0216A", rowData.get("A0215B"));
						
					}
					String A0215B = rowData.get("A0215B")!=null?rowData.get("A0215B").toString():"";
					rowData.put("A0215B", subStringByByte(A0215B, 80));
					//
					String A0201B = rowData.get("A0201B")!=null?rowData.get("A0201B").toString():"";
					if(!A0201B.equals("") && !A0201B.equals("XXX") && !A0201B.equals("-1")){
//						System.out.println("===================="+A0201B);
//						rowData.put("A0201B", impdeptid + (A0201B.substring(B0111.length())));
						if(A0201B.length()<B0111.length()){
							rowData.put("A0201B", "-1");
						} else {
							rowData.put("A0201B", impdeptid + (A0201B.substring(B0111.length())));
						}
					} else if(A0201B.equals("XXX")){
						rowData.put("A0201B", "-1");
					}
					
				} else if(table.equals("A08")){
					//专业GB16835
					String A0827 = rowData.get("A0827")!=null?rowData.get("A0827").toString():"";
					if(lowerCase.equalsIgnoreCase("zb3")){
						String a0899 = rowData.get("A0899")!=null?rowData.get("A0899").toString():"";
						rowData.put("A0899", (a0899!=null &&  a0899.equals("1"))?"true":"false");
						if(!A0827.trim().equals("")){
							String reg = "^\\d+$"; 
							if(A0827.matches(reg)){
								if(A0827.equals("1009")){
									rowData.put("A0827", "906");
								} else if (A0827.equals("100901")){
									rowData.put("A0827", "90653");
								} else if (A0827.equals("100999")){
									rowData.put("A0827", "90699");
								} else if (A0827.equals("090651")){
									rowData.put("A0827", "20257");
//								} else if (A0827.equals("071602")){
//									rowData.put("A0827", "071");
								} else if(A0827.equals("11")){
									rowData.put("A0827", "");
								} else {
									rowData.put("A0827", Long.parseLong(A0827)+"");
								}
							} else {
								Object obj = sess.createSQLQuery("select code_value from code_value where code_type='GB16835' and code_name='"+A0827+"'").uniqueResult();
								if(obj!=null&&!obj.toString().equals("")){
									rowData.put("A0827", obj);
								} else {
									rowData.put("A0827", "");
								}
							}
						} else {
							rowData.put("A0827", "");
						}
					}
					
				} else if(table.equals("A37")){
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
						String A3101 = rowData.get("A3101")!=null?rowData.get("A3101").toString():"";
						if(A3101.equals("01")){
							rowData.put("A2911", "1");
						} else if(A3101.equals("02")){
							rowData.put("A2911", "2");
						} else if(A3101.equals("03")){
							rowData.put("A2911", "3");
						}
					}
				} else if(table.equals("A14")){
					String A1411A = rowData.get("A1411A")!=null?rowData.get("A1411A").toString():"";
					rowData.put("A1411A", subStringByByte(A1411A, 60));
					String A1404A = rowData.get("A1404A")!=null?rowData.get("A1404A").toString():"";
					rowData.put("A1404A", subStringByByte(A1404A, 40));
				} else if(table.equals("A29")){
					String A2941 = rowData.get("A2941")!=null?rowData.get("A2941").toString():"";
					if(A2941.length()>20){
						rowData.put("A2941", subStringByByte(A2941, 40));
					}
					if(lowerCase.equalsIgnoreCase("zb3")){
						String A2911 = rowData.get("A2911")!=null?rowData.get("A2911").toString():"";
//						if(A2911.equals("11") || A2911.equals("12") ||A2911.equals("13")){
//							rowData.put("A2950", "0");
//						} else if(A2911.equals("14") || A2911.equals("15") ) {
//							rowData.put("A2951", "0");
//						}
						if(A2911.equals("4")){
							rowData.put("A2911", "6");
						}
					}
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
					String A1131 = rowData.get("A1131")!=null?rowData.get("A1131").toString():"";
					if(A1131.length()>30){
						rowData.put("A1131", subStringByByte(A1131, 60));
					}
				}  else if(table.equals("B01")){
					String b0121 = rowData.get("B0121")!=null?rowData.get("B0121").toString():"";
					String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
					if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
							&& deptid != null) {
						rowData.put("B0121", deptid.toString());
					} else {
						rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
					}
					rowData.put("B0111", impdeptid + (b0111.substring(B0111.length())));
					if(lowerCase.equalsIgnoreCase("zb3")){
						String b0131 = rowData.get("B0131")!=null?rowData.get("B0131").toString():"";
						if(b0131.equals("3425")|| b0131.equals("3426")){
							rowData.put("B0131", "3450");
						} else if(b0131.equals("3434")|| b0131.equals("3435")){
							rowData.put("B0131", "3451");
						} else if(b0131.equals("36")){
							rowData.put("B0131", "1006");
						} else if(b0131.equals("37")){
							rowData.put("B0131", "1007");
						}
					} else {
						String CREATE_DATE = rowData.get("CREATE_DATE")!=null?rowData.get("CREATE_DATE").toString():"";
						String UPDATE_DATE = rowData.get("UPDATE_DATE")!=null?rowData.get("UPDATE_DATE").toString():"";
						rowData.put("CREATE_DATE", DateUtil.stringToTime2(CREATE_DATE));
						rowData.put("UPDATE_DATE", DateUtil.stringToTime2(UPDATE_DATE));
					}
//					String newb0111 = impdeptid + (b0111.substring(B0111.length()));
//					Statement stmt2 = conn1.createStatement();
//					stmt2.executeUpdate("insert into B01TEMP_B01 values('"+b0111+"','"+newb0111+"','"+imprecordid+"','"+UUID.randomUUID().toString()+"')");
//					stmt2.close();
				}  else if(table.equals("A01")){
					//职务层次
					/*String A0148 = rowData.get("A0148")!=null?rowData.get("A0148").toString():""; 
					if(!A0148.equals("")){
						List list = sess.createSQLQuery("select code_value from code_value where code_type='ZB09' and code_value='"+A0148+"'").list();
						if(list==null || list.size()<1){
							rowData.put("A0148", "01");
						}
						list.clear();
					}*/
					String A0195 = rowData.get("A0195")!=null?rowData.get("A0195").toString():"";
					if(!A0195.equals("")&& !A0195.equals("XXX") && !A0195.equals("-1")){
						if(A0195.length()<B0111.length()){
							rowData.put("A0195", "-1");
						} else {
							rowData.put("A0195", impdeptid + (A0195.substring(B0111.length())));
						}
						
					} else if(A0195.equals("XXX")){
						rowData.put("A0195", "-1");
					}
					if(lowerCase.equalsIgnoreCase("zb3")){
						String a0155 = rowData.get("A0155")!=null?rowData.get("A0155").toString():"";
						rowData.put("A0155", DateUtil.stringToDate_Size6_8(a0155));
						String TBSJ = rowData.get("TBSJ")!=null?rowData.get("TBSJ").toString():"";
						rowData.put("TBSJ", DateUtil.stringToDate_Size6_8(a0155));
						String XGSJ = rowData.get("XGSJ")!=null?rowData.get("XGSJ").toString():"";
						rowData.put("XGSJ", DateUtil.stringToDate_Size6_8(a0155));
						rowData.put("JSNLSJ", "");
						String a0107 = rowData.get("A0107")!=null?rowData.get("A0107").toString():"";
						String A0160 = rowData.get("A0160")!=null?rowData.get("A0160").toString():"";
						String A0163 = rowData.get("A0163")!=null?rowData.get("A0163").toString():"";
						if(A0163.equals("1")&&(A0160.equals("1")||A0160.equals("2")||A0160.equals("3")||A0160.equals("5"))){
							rowData.put("A0121", "1");
						} else if(A0163.equals("1")&&(A0160.equals("6"))){
							rowData.put("A0121", "2");
						} else if(A0163.equals("1")&&(A0160.equals("7")||A0160.equals("8"))){
							rowData.put("A0121", "3");
						} else {
							rowData.put("A0121", "4");
						}
						if(A0163.equals("1")){
							rowData.put("STATUS", "1");
						} else if(A0163.equals("2")){
							rowData.put("STATUS", "3");
						} else if(A0163.equals("3") || A0163.equals("4") || A0163.equals("5")){
							rowData.put("STATUS", "2");
						}
//						if(a0107!=null && !a0107.equals("")){
//							try {
//								if(a0107.length()==8){
//									int age = DateUtil.getAgeByBirthday(DateUtil.stringToDate(a0107, "yyyyMMdd"));
//									rowData.put("AGE", Long.parseLong(age+""));
//									rowData.put("NL", age+"");
//								} else if(a0107.length()==6){
//									int age = DateUtil.getAgeByBirthday(DateUtil.stringToDate(a0107+"01", "yyyyMMdd"));
//									rowData.put("AGE", Long.parseLong(age+""));
//									rowData.put("NL", age+"");
//									
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//						String a0000 = rowData.get("A0000")!=null?rowData.get("A0000").toString():"";
//						Statement stmt2 = conn1.createStatement();
//						ResultSet rs_a31 = stmt2.executeQuery("select a3101 from a31_temp where a0000='"+a0000+"' and IMPRECORDID='"+imprecordid+"'");
//						if(rs_a31 != null && rs_a31.next()){
//							String a3101 = rs_a31.getString(1);//离退类别
//							if(a3101!=null&&!"".equals(a3101)){
//								rowData.put("A0163", "2");
//								rowData.put("STATUS", "3");
//							}else{
//								rowData.put("A0163", "1");
//								rowData.put("STATUS", "1");
//							}
//						} else {
//							rowData.put("A0163", "1");
//							rowData.put("STATUS", "1");
//						}
//						rs_a31.close();
//						ResultSet rs_a30 = stmt2.executeQuery("select a3001 from a30_temp where a0000='"+a0000+"' and IMPRECORDID='"+imprecordid+"'");
//						if(rs_a30 != null && rs_a30.next()){
//							String a3001 = rs_a30.getString(1);//调出人员     历史库
//							if(a3001!=null && !a3001.equals("")){
//								if("1".startsWith(a3001)||"2".startsWith(a3001)){
//									rowData.put("A0163", "3");
//									rowData.put("STATUS", "2");
//								}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
//									rowData.put("A0163", "4");
//									rowData.put("STATUS", "2");
//								}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
//									rowData.put("A0163", "2");
//									rowData.put("STATUS", "3");
//								}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
//									rowData.put("A0163", "5");
//									rowData.put("STATUS", "2");
//								}
//							}
//						}
//						rs_a30.close();
//						stmt2.close();
					} else {
						String a0155 = rowData.get("A0155")!=null?rowData.get("A0155").toString():"";
						rowData.put("A0155", DateUtil.stringToTime2(a0155));
						String TBSJ = rowData.get("TBSJ")!=null?rowData.get("TBSJ").toString():"";
						rowData.put("TBSJ", DateUtil.stringToTime2(TBSJ));
						String XGSJ = rowData.get("XGSJ")!=null?rowData.get("XGSJ").toString():"";
						rowData.put("XGSJ", DateUtil.stringToTime2(XGSJ));
//						String JSNLSJ = rowData.get("JSNLSJ")!=null?rowData.get("JSNLSJ").toString():"";
						rowData.put("JSNLSJ", "");
//						String COMBOXAREA_A0114 = rowData.get("COMBOXAREA_A0114")!=null?rowData.get("COMBOXAREA_A0114").toString():"";
//						rowData.put("A0114A", COMBOXAREA_A0114);
//						String COMBOXAREA_A0111 = rowData.get("COMBOXAREA_A0111")!=null?rowData.get("COMBOXAREA_A0111").toString():"";
//						rowData.put("A0111A", COMBOXAREA_A0111);
					}
					
				}
		    	for (int j = 0; j < colomns.size(); j++) {
	    			pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString().toUpperCase())==null||
	    					rowData.get(colomns.get(j).toString().toUpperCase()).equals("")?null:rowData.get(colomns.get(j).toString().toUpperCase()));
				}
//		    	pstmt1.execute();
		    	pstmt1.addBatch();
		    	batchNum ++;
		    	if(batchNum%5000==0){
		    		//=================================================
					StopWatch w = new StopWatch();
					//================================
					w.start();
					pstmt1.executeBatch();
					w.stop();
//					appendFileContent("D:\\ceShiDaoRuShiJian.txt",  table+"每5000时间"+"\n"+w.elapsedTime()+"\n");
		    		pstmt1.clearBatch();
		    		System.gc();
		    	}
		    	
		    	t_n++;
		    	
		    	attrs.clear();
		    	attrs = null;
				rowData.clear();
				rowData=null;
//				pstmt1.clearParameters();
				sess.flush();
				if(t_n%5000==0){
					CommonQueryBS.systemOut(table+"======  NO."+t_n/5000+"  ========"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		    		System.gc();
				}
				
			} catch (Exception e1) {
				isError = true;
				try {
					KingbsconfigBS.saveImpDetail("3" ,"4","失败:"+e1.getMessage(),uuid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
				new UploadHzbFileBS().rollbackImp(imprecordid);
			}
		}
		element.detach(); // 记得从内存中移去
	}

	@Override
	public void onEnd(ElementPath ep) {
//		Element urlNode = ep.getCurrent();  
//        urlNode.detach();  
	}
	
	 private static String subStringByByte(String str, int len) {
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
	 public static void main(String[] args) {
		 String str = "张2是";
		 CommonQueryBS.systemOut(subStringByByte(str, 10));
	}
	 public static void appendFileContent(String fileName, String content) {
	        try {
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	           FileWriter writer = new FileWriter(fileName, true);
	            writer.write(content);
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}