package com.insigma.siis.local.business.utils;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataToDB {
	
	public void insert(Connection conn, ResultSet rs,String tabelName) throws Exception {
		conn.setAutoCommit(false);
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("insert into " + tabelName + "(");
		for (int j = 1; j <= columnCount; j++) {
			sbuf.append(md.getColumnName(j) + (j!=columnCount?",": ") "));
			System.out.println();
			
			
		}
		System.out.println("sbuf::"+sbuf);
		sbuf.append("values(");
		for (int j = 1; j <= columnCount; j++) {
			sbuf.append("?" + (j!=columnCount?",": ") "));
		}
		PreparedStatement prestmt =conn.prepareStatement(sbuf+"");
		int num=5000;
		int count=0;
		int n=0;
		while (rs.next()) {
			count++;
			for (int j = 1; j <= columnCount; j++) {
				if("CLOB".equals(md.getColumnTypeName(j))){
					Clob clob = rs.getClob(md.getColumnName(j));
					if(clob!=null){
						prestmt.setObject(j,oracleClobToString(clob));
					}else{
						prestmt.setObject(j, "");
					}
				}else{
					prestmt.setObject(j, rs.getObject(j));
				}
			}
			prestmt.addBatch();  
			if(count%num==0){
				prestmt.executeBatch();
				prestmt.clearBatch();
				
				n++;
				CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+tabelName + "---5000一次" + n +",内存："+((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())%(1024*1024)));
				System.gc();
			}
		}
		n++;
		prestmt.executeBatch();
		prestmt.clearBatch(); 
		conn.commit();
		CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+tabelName + "---5000一次" + n +",内存："+((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())%(1024*1024)));
    	System.gc();
		prestmt.close();
		//conn.close();
	}
	//add zepeng 20190926 增加执行sql方法，用于平板导出
	public void execute(Connection conn, String sql) throws Exception {
		conn.setAutoCommit(true);
		PreparedStatement prestmt =conn.prepareStatement(sql);
		try {
			prestmt.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			prestmt.close();
	    	System.gc();
		}
		//conn.close();
	}
	//add zepeng 20190926 增加执行sql方法，用于平板导出
	public void execute(Connection conn, List<String> sqls) throws Exception {
		conn.setAutoCommit(false);
		Statement prestmt =conn.createStatement();
		for(String sql:sqls) {
			prestmt.addBatch(sql);
		}
		try {
			prestmt.executeBatch();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.commit();
			prestmt.close();
	    	System.gc();
		}
		//conn.close();
	}
	
	//将Clob类型转成String
	public String oracleClobToString(Clob clob){
	    try {
	    	Reader characterStream = clob.getCharacterStream();
	    	char[] c=new char[(int)clob.length()];
	    	characterStream.read(c);
	    	return new String(c);
	    } catch (Exception e) {
	        //e.printStackTrace();
	         
	    }
	    return null;
	}
	
	
}
