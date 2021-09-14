package com.insigma.siis.local.business.datavaerify;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import oracle.sql.BLOB;

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

public class MySaxHandlerA57 implements ElementHandler {
	
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

	public MySaxHandlerA57() {
	}
	
	public MySaxHandlerA57(String docname, String lowerCase, String table,
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
			HBSession sess = HBUtil.getHBSession();
			try {
				if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
					Connection conn1 = sess.connection();
					conn1.setAutoCommit(false); 
					PreparedStatement pstmt1 = conn1.prepareStatement("insert into a57_temp(A0000,A5714,UPDATED,ERROR_INFO,IS_QUALIFIED," +
							"IMPRECORDID,PHOTODATA,PHOTONAME,PHOTSTYPE) values(?,?,?,?,?,  ?,?,?,?)");
					Map rowData = new HashMap();
					List<Attribute> attrs = element.attributes();  
					for (Attribute attr : attrs) {  
						rowData.put(attr.getName(), attr.getValue());
		            }
					if(rowData.get("A0000")!=null && !rowData.get("A0000").toString().equals("")){
						pstmt1.setObject(1, rowData.get("A0000"));
						pstmt1.setObject(2, rowData.get("A5714"));
						pstmt1.setObject(3, "1");
						pstmt1.setObject(4, "");
						pstmt1.setObject(5, "0");
						pstmt1.setObject(6, imprecordid);
						
						String a5714 = rowData.get("A5714")!=null?rowData.get("A5714").toString():"";
						String a5714_arr[] = a5714.split("\\|");
						File file_p = new File(from_file + "/" +(a5714_arr[0].equals("")?(rowData.get("A0000")+".jpg"):a5714_arr[0]));
					    FileInputStream fin = null;
					    if(file_p!=null && file_p.exists()&& file_p.isFile()){
				        	fin = new FileInputStream(file_p);   
				        	pstmt1.setBinaryStream(7, fin, fin.available());  //第二个参数为文件的内容  
				        } else {
				        	pstmt1.setObject(7, null);
				        }
					    pstmt1.setObject(8, "");
					    pstmt1.setObject(9, "jpg");
				    	pstmt1.executeUpdate();
				    	pstmt1.clearParameters();
				    	if(fin!=null)
				    		fin.close();
			            pstmt1.close();
						rowData.clear();
						rowData=null;
					}
					conn1.commit();
					conn1.close();
					attrs.clear();
			    	attrs = null;
					sess.flush();
				
				} else {
					sess.beginTransaction();
					Connection conn1 = sess.connection();
					conn1.setAutoCommit(false); 
					PreparedStatement pstmt1 = conn1.prepareStatement("insert into a57_temp(A0000,A5714,UPDATED,ERROR_INFO,IS_QUALIFIED," +
							"IMPRECORDID,PHOTODATA,PHOTONAME,PHOTSTYPE) values(?,?,?,?,?,  ?,empty_blob(),?,?)");
					Map rowData = new HashMap();
					List<Attribute> attrs = element.attributes();  
					for (Attribute attr : attrs) {  
						rowData.put(attr.getName(), attr.getValue());
		            }
					//--------------------------------------------------------------------------------------------------------------
					if(rowData.get("A0000")!=null && !rowData.get("A0000").toString().equals("")){
						pstmt1.setObject(1, rowData.get("A0000"));
						pstmt1.setObject(2, rowData.get("A5714"));
						pstmt1.setObject(3, "1");
						pstmt1.setObject(4, "");
						pstmt1.setObject(5, "0");
						pstmt1.setObject(6, imprecordid);
						pstmt1.setObject(7, "");
						pstmt1.setObject(8, "jpg");
				    	pstmt1.executeUpdate();
				    	pstmt1 = conn1.prepareStatement("select PHOTODATA from a57_temp where a0000='"+rowData.get("A0000")+"' and imprecordid='"+imprecordid+"' for update") ;
				    	ResultSet rs= pstmt1.executeQuery(); 
				    	BLOB blob = null;   
				        if(rs.next()){   
				           blob = (BLOB)rs.getBlob(1);   
				        }
				        String a5714 = rowData.get("A5714")!=null?rowData.get("A5714").toString():"";
						String a5714_arr[] = a5714.split("\\|");
				        File file_p = new File(from_file + "/" +a5714_arr[0]);   
				        if(file_p!=null && file_p.exists()){
				        	FileInputStream fin = new FileInputStream(file_p);   
				            byte[] temp = new byte[fin.available()];   
				            fin.read(temp);   
				            OutputStream out = blob.getBinaryOutputStream();   
				            out.write(temp);   
				            fin.close();   
				            out.close();  
				        }
			            rs.close(); 
			            pstmt1.execute();
			            pstmt1.clearParameters();
			            pstmt1.close();
						rowData.clear();
						rowData=null;
					}
					attrs.clear();
			    	attrs = null;
					conn1.commit();
					conn1.close();
					sess.flush();
					sess.getTransaction().commit();
					sess.flush();
				}
				
				t_n++;
		    	
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