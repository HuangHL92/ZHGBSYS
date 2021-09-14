package com.insigma.siis.local.business.datavaerify;

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
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class MySaxHandler implements ElementHandler {
	
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

	public MySaxHandler() {
	}
	
	public MySaxHandler(String docname, String lowerCase, String table,
			String imprecordid, int t_n, String uuid, String from_file,
			String B0111, String deptid, String impdeptid) {
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
	}

	@Override
	public void onEnd(ElementPath ep) {
		Element element = ep.getCurrent(); // 获得当前节点
		if(element!=null){
			Map rowData = new HashMap();
			List<Attribute> attrs = element.attributes();  
			for (Attribute attr : attrs) {  
				rowData.put(attr.getName(), attr.getValue());
            }
			rowData.put("IMPRECORDID", imprecordid);
			rowData.put("IS_QUALIFIED", "0");
			rowData.put("ERROR_INFO", "2");
			HBSession sess = HBUtil.getHBSession();
			try {
				sess.beginTransaction();
				Connection conn1 = sess.connection();
				//--------------------------------------------------------------------------------------------------------------
				StringBuffer colomn_sql = new StringBuffer();            //b01字段连接 如(a0000,A0200......)
				StringBuffer value_sql = new StringBuffer();             //b01字段插入值连接(?,?,?)
				PreparedStatement pstmt1 = null;
				List colomns = null;
				String sql = null;
				if(DBType.MYSQL == DBUtil.getDBType() ){
					sql = "select column_name from information_schema.columns a where table_name = upper('"+table+"_TEMP') and a.TABLE_SCHEMA = 'ZWHZYQ'";
				}else if(DBType.ORACLE == DBUtil.getDBType()){
					sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"+table+"_TEMP')";
				}
				colomns = sess.createSQLQuery(sql).list();
				if(colomns != null){
					for (int j = 0; j < colomns.size(); j++) {
						String column = (String) colomns.get(j);
						colomn_sql.append(column);
						value_sql.append("?");
						if(j != colomns.size()-1){
							colomn_sql.append(",");
							value_sql.append(",");
						}
					}
				} else {
					throw new RadowException("数据库异常，请联系管理员！");
				}
				pstmt1 = conn1.prepareStatement("insert into "+table+"_temp("+colomn_sql+") values("+value_sql+")");
				//--------------------------------------------------------------------------------------------------------------
				if(table.equals("A02")){												//A02 特殊字符处理
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
				} else if(table.equals("A08")){
					String a0899 = rowData.get("A0899")!=null?rowData.get("A0899").toString():"";
					rowData.put("A0899", (a0899!=null &&  a0899.equals("1"))?"true":"false");
					//专业GB16835
					String A0827 = rowData.get("A0827")!=null?rowData.get("A0827").toString():"";
					if(!A0827.equals("")){
						String reg = "^\\d+$"; 
						if(A0827.matches(reg)){
							if(A0827.equals("11")){
								rowData.put("A0827", 7);
							} else {
								Long A0827l =Long.parseLong(A0827);
								rowData.put("A0827", A0827l);
								/*List list = sess.createSQLQuery("select code_value from code_value where code_type='GB16835' and code_value='"+A0827+"'").list();
								if(list!=null && list.size()>0){
									rowData.put("A0827", A0827l);
								} else {
									rowData.put("A0827", Long.parseLong(A0827.substring(0, 2)));
								}
								list.clear();*/
							}
						} else {
							Object obj = sess.createSQLQuery("select code_value from code_value where code_type='GB16835' and code_name='"+A0827+"'").uniqueResult();
							if(obj!=null&&!obj.toString().equals("")){
								rowData.put("A0827", obj);
							} else {
								rowData.put("A0827", "");
							}
						}
					}
				} else if(table.equals("A11")){
					String a1107 = rowData.get("A1107")!=null?rowData.get("A1107").toString():"";
					String a1111 = rowData.get("A1111")!=null?rowData.get("A1111").toString():"";
					if(a1107!=null && !a1107.equals("")
							&& a1111!=null && !a1111.equals("")){
						a1107 = (a1107 + "01").substring(0, 8);
						a1111 = (a1111 + "01").substring(0, 8);
						int d = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyyMMdd"), 
								DateUtil.stringToDate(a1111, "yyyyMMdd"));
						rowData.put("A1107C", d);
					}
				}  else if(table.equals("B01")){
					String b0121 = rowData.get("B0121")!=null?rowData.get("B0121").toString():"";
					String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
					if ((b0121 == null
							|| b0121.equals("") || b0111.equals(B0111))
							&& deptid != null) {
						rowData.put("B0121", deptid.toString());
					}
					String newb0111 = impdeptid + (b0111.substring(B0111.length()));
					Statement stmt2 = conn1.createStatement();
					stmt2.executeUpdate("insert into B01TEMP_B01 values('"+b0111+"','"+newb0111+"','"+imprecordid+"','"+UUID.randomUUID().toString()+"')");
					stmt2.close();
				}  else if(table.equals("A01")){
					String a0155 = rowData.get("A0155")!=null?rowData.get("A0155").toString():"";
					rowData.put("A0155", DateUtil.stringToDate_Size6_8(a0155));
					String TBSJ = rowData.get("TBSJ")!=null?rowData.get("TBSJ").toString():"";
					rowData.put("TBSJ", DateUtil.stringToDate_Size6_8(a0155));
					String XGSJ = rowData.get("XGSJ")!=null?rowData.get("XGSJ").toString():"";
					rowData.put("XGSJ", DateUtil.stringToDate_Size6_8(a0155));
					rowData.put("JSNLSJ", DateUtil.getSysDate());;
					//职务层次
					/*String A0148 = rowData.get("A0148")!=null?rowData.get("A0148").toString():""; 
					if(!A0148.equals("")){
						List list = sess.createSQLQuery("select code_value from code_value where code_type='ZB09' and code_value='"+A0148+"'").list();
						if(list==null || list.size()<1){
							rowData.put("A0148", "01");
						}
						list.clear();
					}*/
					if(lowerCase.equalsIgnoreCase("zb3")){
						String a0107 = rowData.get("A0107")!=null?rowData.get("A0107").toString():"";
						if(a0107!=null && !a0107.equals("")){
							if(a0107.length()==8){
								int age = DateUtil.getAgeByBirthday(DateUtil.stringToDate(a0107, "yyyyMMdd"));
								rowData.put("AGE", Long.parseLong(age+""));
								rowData.put("NL", age+"");
							} else if(a0107.length()==6){
								int age = DateUtil.getAgeByBirthday(DateUtil.stringToDate(a0107+"01", "yyyyMMdd"));
								rowData.put("AGE", Long.parseLong(age+""));
								rowData.put("NL", age+"");
								
							}
						}
						String a0000 = rowData.get("A0000")!=null?rowData.get("A0000").toString():"";
						Statement stmt2 = conn1.createStatement();
						ResultSet rs_a31 = stmt2.executeQuery("select a3101 from a31_temp where a0000='"+a0000+"' and IMPRECORDID='"+imprecordid+"'");
						if(rs_a31 != null && rs_a31.next()){
							String a3101 = rs_a31.getString(1);//离退类别
							if(a3101!=null&&!"".equals(a3101)){
								rowData.put("A0163", "2");
								rowData.put("STATUS", "3");
							}else{
								rowData.put("A0163", "1");
								rowData.put("STATUS", "1");
							}
						} else {
							rowData.put("A0163", "1");
							rowData.put("STATUS", "1");
						}
						rs_a31.close();
						ResultSet rs_a30 = stmt2.executeQuery("select a3001 from a30_temp where a0000='"+a0000+"' and IMPRECORDID='"+imprecordid+"'");
						if(rs_a30 != null && rs_a30.next()){
							String a3001 = rs_a30.getString(1);//调出人员     历史库
							if("1".startsWith(a3001)||"2".startsWith(a3001)){
								rowData.put("A0163", "3");
								rowData.put("STATUS", "2");
							}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
								rowData.put("A0163", "4");
								rowData.put("STATUS", "2");
							}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
								rowData.put("A0163", "2");
								rowData.put("STATUS", "3");
							}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
								rowData.put("A0163", "5");
								rowData.put("STATUS", "2");
							}
						}
						rs_a30.close();
						stmt2.close();
					} else {
						String COMBOXAREA_A0114 = rowData.get("COMBOXAREA_A0114")!=null?rowData.get("COMBOXAREA_A0114").toString():"";
						rowData.put("A0114A", COMBOXAREA_A0114);
						String COMBOXAREA_A0111 = rowData.get("COMBOXAREA_A0111")!=null?rowData.get("COMBOXAREA_A0111").toString():"";
						rowData.put("A0111A", COMBOXAREA_A0111);
					}
					
				}
		    	for (int j = 0; j < colomns.size(); j++) {
	    			pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString())==null||
	    					rowData.get(colomns.get(j).toString()).equals("")?null:rowData.get(colomns.get(j).toString()));
				}
		    	pstmt1.execute();
		    	t_n++;
		    	attrs.clear();
		    	attrs = null;
				rowData.clear();
				rowData=null;
				pstmt1.clearParameters();
				pstmt1.close();
				if(colomns !=null)
					colomns.clear();
				colomns =null;
				conn1.close();
				System.gc();
				sess.flush();
				sess.getTransaction().commit();
				sess.flush();
				System.gc();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		element.detach(); // 记得从内存中移去
	}

	@Override
	public void onStart(ElementPath ep) {
	}

}