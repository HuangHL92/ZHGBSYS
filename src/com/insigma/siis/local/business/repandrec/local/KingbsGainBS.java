package com.insigma.siis.local.business.repandrec.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import oracle.sql.BLOB;


import org.hibernate.Hibernate;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.ConversionIndex;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A06temp;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A11temp;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A14temp;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A15temp;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A29temp;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A30temp;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A31temp;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A36temp;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A37temp;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A41temp;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A53temp;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A57temp;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01temp;
import com.insigma.siis.local.business.entity.B01tempb01;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.kingbs.KingBSImpUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.kingbase.jdbc4.Jdbc4PoolingDataSource;

public class KingbsGainBS {

	/*public static Jdbc4PoolingDataSource ds = getDataSource();
	
	public static Jdbc4PoolingDataSource getDataSource() {
		ds = new Jdbc4PoolingDataSource();
		String KING_BD = "ZWHZYQ";
		String KING_BD_USER = "ZWHZYQ";
		String KING_BD_PWD = "1234";
		String KING_BD_PORT = GlobalNames.sysConfig.get("KING_BD_PORT");
		String KING_BD_SERVER = GlobalNames.sysConfig.get("KING_BD_SERVER");
		ds.setServerName(KING_BD_SERVER);
		ds.setDatabaseName(KING_BD);
		ds.setUser(KING_BD_USER);
		ds.setPassword(KING_BD_PWD);
		ds.setMaxConnections(10);
		ds.setInitialConnections(10);
		ds.setPortNumber(Integer.parseInt(KING_BD_PORT));
		return ds;
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

	public static B01 getB01byParentid(String string) {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from b01 where (b0121 is null or b0121 ='') and b0111<>'XXX'");
			List list = ResultToObject(rs, B01.class);
			if (list != null && list.size() > 0) {
				return (B01) list.get(0);
			}
			list = null;
//			KingBSImpUtil.closeConnection(conn);
		} catch (SQLException e) {
//			KingBSImpUtil.closeConnection(conn);
			e.printStackTrace();
		}catch (Throwable thr){
			thr.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
			System.gc();
		}
		return null;
	}

	public static List<B01> getAllOrg() {
		List<B01> list = null;
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs =  null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from dbo.b01 where b0121 is not null");
			list = ResultToObject(rs, B01.class);
//			KingBSImpUtil.closeConnection(conn);
		} catch (SQLException e) {
//			KingBSImpUtil.closeConnection(conn);
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return list;
	}

	public static List<A01> getAllA01() {
		List<A01> list = null;
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from dbo.a01 ");
			list = ResultToObject(rs, A01.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
			
		}
		return list;
	}

	public static int getAllB01Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		int record_count = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.b01 where b0111 <>'XXX'");
			while (rs.next()) {
				record_count = rs.getInt(1);
			}
			return record_count;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
			
		}
		return 0;
	}
	
	public static int getAllA02Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt =  null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a02 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		
		return 0;
	}

	public static int getAllA06Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a06 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		
		return 0;
	}

	public static int getAllA08Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a08 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA14Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a14 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		
		return 0;
	}

	public static int getAllA11Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a11 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA15Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a15 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA29Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a29 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA30Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a30 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA31Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a31 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA36Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a36 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA37Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a37 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA41Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a41 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA53Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a53 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	public static int getAllA57Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a57 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}

	private static <T> List<T> ResultToObject(ResultSet rs, Class<T> entityClass) {
		List<T> list = new ArrayList<T>();
		ResultSetMetaData m = null;
		Map map = new HashMap();
		try {
			java.lang.reflect.Field[] field = entityClass.getDeclaredFields();
			m = rs.getMetaData();
			int columns = m.getColumnCount();
			// 显示列,表格的表头
			for (int i = 1; i <= columns; i++) {
				map.put(m.getColumnName(i).toLowerCase(), i);
			}
			while (rs.next()) {
				T entity = (T) entityClass.newInstance();
				for (Field it : field) {
					String name = it.getName();
					if (map.containsKey(name.toLowerCase())) {
						String parameterType = it.getType().getSimpleName();
						String methodName = "set"
								+ ((name.charAt(0) + "").toUpperCase())
								+ name.substring(1);
						Method method = entity.getClass().getDeclaredMethod(
								methodName, it.getType());
						if (parameterType.equals("String")) {
							method.invoke(entity, rs.getString(name));
						} else if (parameterType.equals("Character")) {
							method.invoke(entity, rs.getString(name));
						} else if (parameterType.equals("Boolean")) {
							method.invoke(entity, rs.getBoolean(name));
						} else if (parameterType.equals("Short")) {
							method.invoke(entity, rs.getShort(name));
						} else if (parameterType.equals("Integer")) {
							method.invoke(entity, rs.getInt(name));
						} else if (parameterType.equals("Float")) {
							method.invoke(entity, rs.getFloat(name));
						} else if (parameterType.equals("Long")) {
							method.invoke(entity, rs.getLong(name));
						} else if (parameterType.equals("Double")) {
							method.invoke(entity, rs.getDouble(name));
						} else if (parameterType.equals("Date")) {
							method.invoke(entity, DateUtil
									.date2sqlDate(DateUtil.stringToDate(rs
											.getString(name), "yyyyMMdd")));
						}
					}
				}
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void cmdImpKingBs(File absoluteFile) {
		String cmdKingbs = "";
		HBSession sess = HBUtil.getHBSession();
		try {
			List<Dataexchangeconf> list = sess.createQuery("from Dataexchangeconf").list();
			if (list !=null && list.size()>0) {
				cmdKingbs = list.get(0).getKingrestorepath() ;
				KingBSImpUtil.dataDr(cmdKingbs, absoluteFile);
			} else {
				throw new Exception("导入金仓数据库失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable thr){
			thr.printStackTrace();
		} 
	}

	public static void insertData2Temp(List<List<Map<String, String>>> dataAll,
			String photo_file, B01 detp, String filename) throws AppException {
		String impdeptid = detp.getB0111();
		String deptid = detp.getB0121();
		//头文件list
		List<Map<String, String>> headlist = dataAll.get(0);
		//获取当前人员机构
		CurrentUser user = SysUtil.getCacheCurrentUser();
		List<B01> grps = HBUtil.getHBSession().createQuery(
				"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
						+ user.getId() + "')").list();
		B01 gr = null;
		if (grps != null && grps.size() > 0) {
			gr = grps.get(0);
		}
		String B0111 = headlist.get(0).get("B0111");// 根节点上级机构id
		List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", dataAll.get(0));
		List<A01temp> a01s = Map2Temp.toTemp("A01", dataAll.get(1));
		List<A02temp> a02s = Map2Temp.toTemp("A02", dataAll.get(2));
		List<A06temp> a06s = Map2Temp.toTemp("A06", dataAll.get(3));
		List<A08temp> a08s = Map2Temp.toTemp("A08", dataAll.get(4));
		List<A11temp> a11s = Map2Temp.toTemp("A11", dataAll.get(5));
		List<A14temp> a14s = Map2Temp.toTemp("A14", dataAll.get(6));
		List<A15temp> a15s = Map2Temp.toTemp("A15", dataAll.get(7));
		List<A29temp> a29s = Map2Temp.toTemp("A29", dataAll.get(8));
		List<A30temp> a30s = Map2Temp.toTemp("A30", dataAll.get(9));
		List<A31temp> a31s = Map2Temp.toTemp("A31", dataAll.get(10));
		List<A36temp> a36s = Map2Temp.toTemp("A36", dataAll.get(11));
		List<A37temp> a37s = Map2Temp.toTemp("A37", dataAll.get(12));
		List<A41temp> a41s = Map2Temp.toTemp("A41", dataAll.get(13));
		List<A53temp> a53s = Map2Temp.toTemp("A53", dataAll.get(14));
		List<A57temp> a57s = Map2Temp.toTemp("A57", dataAll.get(15));
		List<B01temp> b01s = Map2Temp.toTemp("B01", dataAll.get(16));
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				if (gr != null) {
					imprecord.setImpgroupid(gr.getB0111());
					imprecord.setImpgroupname(gr.getB0101());
				}

				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype("ZZB3");
				imprecord.setImptype("3");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				sess.save(imprecord);
				long t_n = 0;
				long e_n = 0;
				String imprecordid = imprecord.getImprecordid();
				Map<String, String> errorMap = new HashMap<String, String>();
				if (a02s != null && a02s.size() > 0)
					for (A02temp temp : a02s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a06s != null && a06s.size() > 0)
					for (A06temp temp : a06s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a08s != null && a08s.size() > 0)
					for (A08temp temp : a08s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a11s != null && a11s.size() > 0)
					for (A11temp temp : a11s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a14s != null && a14s.size() > 0)
					for (A14temp temp : a14s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a15s != null && a15s.size() > 0)
					for (A15temp temp : a15s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a29s != null && a29s.size() > 0)
					for (A29temp temp : a29s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a30s != null && a30s.size() > 0)
					for (A30temp temp : a30s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a31s != null && a31s.size() > 0)
					for (A31temp temp : a31s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a36s != null && a36s.size() > 0)
					for (A36temp temp : a36s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a37s != null && a37s.size() > 0)
					for (A37temp temp : a37s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a41s != null && a41s.size() > 0)
					for (A41temp temp : a41s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a53s != null && a53s.size() > 0)
					for (A53temp temp : a53s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a57s != null && a57s.size() > 0)
					for (A57temp temp : a57s) {
						temp.setImprecordid(imprecordid);
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							if (errorMap.containsKey(temp.getA0000())) {
								String error = errorMap.get(temp.getA0000());
								StringBuilder b = new StringBuilder(temp
										.getErrorinfo());
								b.append(error);
								errorMap.put(temp.getA0000(), b.toString());
							} else {
								errorMap.put(temp.getA0000(), temp
										.getErrorinfo());
							}
						}
						if(temp.getA5714()!=null && !temp.getA5714().equals("")){
							String a5714[] = temp.getA5714().split("\\|");
							File photo = new File(photo_file + "/" +a5714[0]);
							if(photo.exists() && photo.isFile()){
								FileInputStream in = new FileInputStream(photo);
								temp.setPhotodata(Hibernate.createBlob(in));
								temp.setPhotoname(photo.getName());
								temp.setPhotstype(photo.getName().split("\\.")[1]);
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (b01s != null && b01s.size() > 0)
					for (B01temp temp : b01s) {

						temp.setImprecordid(imprecordid);
						if ((temp.getB0121() == null
								|| temp.getB0121().equals("") || temp
								.getB0111().equals(B0111))
								&& deptid != null) {
							temp.setB0121(deptid.toString());
						}
						B01tempb01 tempb = new B01tempb01();
						tempb.setImprecordid(imprecordid);
						tempb.setTempb0111(temp.getB0111());
						tempb.setNewb0111(impdeptid + temp.getB0111().substring(B0111.length()));
						sess.save(tempb);
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
				if (a01s != null && a01s.size() > 0)
					for (A01temp temp : a01s) {
						temp.setImprecordid(imprecordid);
						if (errorMap.containsKey(temp.getA0000())) {
							String error = errorMap.get(temp.getA0000());
							StringBuilder b = new StringBuilder(temp
									.getErrorinfo());
							b.append(error);
							errorMap.put(temp.getA0000(), b.toString());
							temp.setErrorinfo(error.toString());
							temp.setIsqualified("1");
						}

						A30temp a30 = (A30temp) sess.get(A30temp.class, temp.getA0000());
						A31temp a31 = (A31temp) sess.get(A31temp.class, temp.getA0000());
						if(a31!=null){
							String a3101 = a31.getA3101();//离退类别
							if(a3101!=null&&!"".equals(a3101)){
								temp.setA0163("2");
								temp.setStatus("3");
							}else{
								temp.setA0163("1");
								temp.setStatus("1");
							}
						} else {
							temp.setA0163("1");
							temp.setStatus("1");
						}
						if(a30!=null){
							String a3001 = a30.getA3001();
							if(a3001!=null&&!"".equals(a3001)){
								//调出人员     历史库
								if("1".startsWith(a3001)||"2".startsWith(a3001)){
									temp.setA0163("3");
									temp.setStatus("2");
								}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
									temp.setA0163("4");
									temp.setStatus("2");
								}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
									temp.setA0163("2");
									temp.setStatus("3");
								}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
									temp.setA0163("5");
									temp.setStatus("2");
								}
							}
						}
						sess.save(temp);
						t_n++;
						if (temp.getIsqualified() != null
								&& temp.getIsqualified().equals("1")) {
							e_n++;
						}
					}
//				dealB0111(impdeptid, B0111, imprecordid);
				imprecord.setTotalnumber(t_n + "");
				imprecord.setWrongnumber(e_n + "");
				sess.update(imprecord);
			}
			sess.getTransaction().commit();
		} catch (AppException e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (IOException e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (Exception e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取新id
	 * @param impdeptid
	 * @param b0111
	 * @param tempb 
	 * @return
	 */
	public static String dealB0111(String impdeptid, String b0111, String imprecordid) {
		HBSession sess =HBUtil.getHBSession();
		B01tempb01 tempb = new B01tempb01();
		tempb.setImprecordid(imprecordid);
		tempb.setTempb0111(b0111);
		tempb.setNewb0111(impdeptid);
		sess.save(tempb);
		List<B01temp> b01ts = sess.createQuery(" from B01temp where imprecordid='"+imprecordid+"' and b0121<>b0111 and b0121='"+ b0111 +"'").list();
		if(b01ts!=null && b01ts.size()>0){
			for (int i = 0; i < b01ts.size(); i++) {
				B01temp temp = b01ts.get(i);
				String impdeptidnew = getNewB0111(impdeptid,i);
				dealB0111(impdeptidnew, temp.getB0111(), imprecordid);
			}
		}
		return "1";
	}
	public static String getNewB0111(String impdeptid, int i) {
		i = i + 1;
		int num1 = 0;//百
		int num2 = 0;//十
		int num3 = 0;//个
		String[] key={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"
				,"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
				,"Y","Z"};	
		num3 = i%36;
		int num2_1 = i/36;
		if(num2_1>0){
			num2 = num2_1%36;
		}
		int num1_1 = num2_1/36;
		if(num1_1>0){
			num1 = num1_1%36;
		}
		String str = impdeptid + "." + key[num1] + key[num2] + key[num3];
		return str;
	}

	public static int getA01Size() {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from dbo.a01 ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				KingBSImpUtil.closeConnection(conn);
			}
		}
		return 0;
	}
	
	
	public static int impTimeData_Table(Imprecord imprecord, String photo_file, String deptid,
			String table, PreparedStatement pstmt1, List colomns, int tabsize) throws Exception {
		String     B0111       = imprecord.getEmpdeptid();
		String     impdeptid   = imprecord.getImpdeptid();
		String     imprecordid = imprecord.getImprecordid();  		//获取导入记录信息id
		HBSession  sess        = HBUtil.getHBSession();				//获取 金仓数据库打开数据库
		Connection conn        = KingBSImpUtil.getConnection3();					//获取 金仓数据库打开数据库连接
		Statement  stmt        = null;								//定义 金仓数据库statement
		ResultSet  rs          = null;								//定义 金仓数据库结果集
		int count = 0;
		try {
			//----------------------------------------------------------------------------------
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->查询表"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			stmt = (Statement)conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(1); 
			stmt.setFetchDirection(ResultSet.FETCH_FORWARD); 
			if(table.equals("B01")){
				rs = stmt.executeQuery("select * from dbo.b01 where b0111 <>'XXX' ");
				CommonQueryBS.systemOut("select * from dbo.b01 where b0111 <>'XXX' ");
			} else {
				rs = stmt.executeQuery("select * from dbo."+table+" ");
				CommonQueryBS.systemOut("select * from dbo."+table+" ");
			}
			
			if (rs == null)
				return 0;   
		    ResultSetMetaData md = rs.getMetaData();                 //得到结果集(rs)的结构信息，比如字段数、字段名等   
		    int columnCount = md.getColumnCount();                   //返回此 ResultSet 对象中的列数   
		    CommonQueryBS.systemOut("   rs 开始"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//----------------------------------------------------------------------------------
			while (rs.next()) {
		    	Map rowData = new HashMap(columnCount); 
		    	for (int j = 1; j <= columnCount; j++) {   
		    		rowData.put(md.getColumnName(j), rs.getObject(j)); 
		        }
		    	rowData.put("IMPRECORDID", imprecordid);
				rowData.put("IS_QUALIFIED", "0");
				rowData.put("ERROR_INFO", "2");
				if(table.equals("A36")){
					if((rowData.get("A3601")==null || rowData.get("A3601").toString().equals(""))
							&&(rowData.get("A3604A")==null || rowData.get("A3604A").toString().equals(""))
							&&(rowData.get("A3607")==null || rowData.get("A3607").toString().equals(""))
							&&(rowData.get("A3611")==null || rowData.get("A3611").toString().equals(""))
							&&(rowData.get("A3627")==null || rowData.get("A3627").toString().equals(""))){
						
						continue;
					}
				} else if(table.equals("A02")){												//A02 特殊字符处理
					
					String A0267 = rowData.get("A0267")!=null?rowData.get("A0267").toString():"";
					rowData.put("A0267", subStringByByte(A0267, 24));
					String A0229 = rowData.get("A0229")!=null?rowData.get("A0229").toString():"";
					rowData.put("A0229", subStringByByte(A0229, 120)); 
					
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
								rowData.put("A0201A", arr[arr.length-1]);
							}
							rowData.put("A0201A_ALL", A0201A);
						}
			    		String A0281 = rowData.get("A0281")!=null?rowData.get("A0281").toString():"";
						rowData.put("A0281", (A0281!=null &&  A0281.equals("1"))?"true":"false");
						//职务名称
						rowData.put("A0215A", rowData.get("A0215B"));

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
					
				} else if(table.equals("A29")){
					if(tabsize == 16){
						String A2911 = rowData.get("A2911")!=null?rowData.get("A2911").toString():"";
						if(A2911.equals("4")){
							rowData.put("A2911", "6");
						}
					}
					
				} else if(table.equals("A08")){
					//专业GB16835
					String A0827 = rowData.get("A0827")!=null?rowData.get("A0827").toString():"";

						//教育
						String A0801B = rowData.get("A0801B")!=null?rowData.get("A0801B").toString():"";
						rowData.put("A0801B", ConversionIndex.ZB64(A0801B));
						
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
								rowData.put("A0827", "");
							}
						} else {
							rowData.put("A0827", "");
						}

					
				} else if(table.equals("A14")){
					
						String A1415 = rowData.get("A1415")!=null?rowData.get("A1415").toString():"";
						rowData.put("A1415", ConversionIndex.zb09(A1415));
						String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
						rowData.put("A1404B", ConversionIndex.ZB65(A1404B));

					String A1411A = rowData.get("A1411A")!=null?rowData.get("A1411A").toString():"";
					rowData.put("A1411A", subStringByByte(A1411A, 60));
					String A1404A = rowData.get("A1404A")!=null?rowData.get("A1404A").toString():"";
					rowData.put("A1404A", subStringByByte(A1404A, 40));
					
				} else if(table.equals("A15")){
					String A1521 = rowData.get("A1521")!=null?rowData.get("A1521").toString():"";
					rowData.put("A1521", A1521.equals("")?"000101":A1521);
				} else if(table.equals("A11")){
					String a1107 = rowData.get("A1107")!=null?rowData.get("A1107").toString():"";
					String a1111 = rowData.get("A1111")!=null?rowData.get("A1111").toString():"";
					if(a1107!=null && !a1107.equals("")&& (a1107.length()==6||a1107.length()==8)
							&& a1111!=null && !a1111.equals("")&&(a1111.length()==6||a1111.length()==8)){
						a1107 = (a1107 + "01").substring(0, 8);
						a1111 = (a1111 + "01").substring(0, 8);
						int d = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyyMMdd"), 
								DateUtil.stringToDate(a1111, "yyyyMMdd"));
						rowData.put("A1107C", d);
					} else {
						rowData.put("A1107C", 0);
					}
					String A1101 = rowData.get("A1101")!=null?rowData.get("A1101").toString():"";
					rowData.put("A1101", ConversionIndex.ZB29(A1101));
					String A1127 = rowData.get("A1127")!=null?rowData.get("A1127").toString():"";
					rowData.put("A1127", ConversionIndex.ZB27(A1127));
					
				} else if(table.equals("B01")){
					String b0121 = rowData.get("B0121")!=null?rowData.get("B0121").toString():"";
					String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
					/*if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
								&& deptid != null) {
						rowData.put("B0121", deptid.toString());
					} else {
						rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
					}*/
					if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
								&& deptid != null) {
						rowData.put("B0121", deptid.toString());
					} else {
						rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
					}
					
					rowData.put("B0111", impdeptid + (b0111.substring(B0111.length())));
					
						String B0131 = rowData.get("B0131")!=null?rowData.get("B0131").toString():"";
						rowData.put("B0131", ConversionIndex.ZB04(B0131));
						String B0124 = rowData.get("B0124")!=null?rowData.get("B0124").toString():"";
						rowData.put("B0124", ConversionIndex.ZB87(B0124));
						String B0127 = rowData.get("B0127")!=null?rowData.get("B0127").toString():"";
						rowData.put("B0127", ConversionIndex.ZB03(B0127));
					
				}  else if(table.equals("A01")){
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
						
					
					
				} else if(table.equals("A31")){
					if(tabsize == 16){
						String A3101 = rowData.get("A3101")!=null?rowData.get("A3101").toString():"";
						if(A3101.equals("01")){
							rowData.put("A2911", "1");
						} else if(A3101.equals("02")){
							rowData.put("A2911", "2");
						} else if(A3101.equals("03")){
							rowData.put("A2911", "3");
						}
					}
					String A3107 = rowData.get("A3107")!=null?rowData.get("A3107").toString():"";
					rowData.put("A3107", ConversionIndex.zb09(A3107));
				} else if(table.equals("A14")){
					String A1415 = rowData.get("A1415")!=null?rowData.get("A1415").toString():"";
					rowData.put("A1415", ConversionIndex.zb09(A1415));
					String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
					rowData.put("A1404B", ConversionIndex.ZB65(A1404B));
				} else if(table.equals("A30")){
					String A3001 = rowData.get("A3001")!=null?rowData.get("A3001").toString():"";
					rowData.put("A3001", ConversionIndex.ZB78(A3001));
				}
		    	for (int j = 0; j < colomns.size(); j++) {
	    			pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString()));
				}
		    	pstmt1.addBatch();
				rowData.clear();
				rowData=null;
				count ++ ;
				if(count%5000 == 0){
					pstmt1.executeBatch();
			    	pstmt1.clearBatch();
			    	CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+table + "---5000一次" + count/5000 +",内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			    	System.gc();      //释
				}
		    }
			
			
		    md = null;
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->关闭内存"+table+"结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		} catch (Throwable t){
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+t.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			CommonQueryBS.systemOut(DateUtil.getTime()+"--->查询表"+table+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t.printStackTrace();
			throw new AppException(t.getMessage());
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				KingBSImpUtil.closeConnection(conn);
			}
		}	
		return count;
	}
	
	public static int impTimeData_A57(Imprecord imprecord, String photo_file,
			String deptid, String table, PreparedStatement pstmt1) throws Exception {
		String     imprecordid = imprecord.getImprecordid();  		//获取导入记录信息id
		HBSession  sess        = HBUtil.getHBSession();				//获取 金仓数据库打开数据库
		Connection conn        = KingBSImpUtil.getConnection3();					//获取 金仓数据库打开数据库连接
		Statement  stmt        = null;								//定义 金仓数据库statement
		ResultSet  rs          = null;								//定义 金仓数据库结果集
		int count = 0;
		try {
			//----------------------------------------------------------------------------------
//			sess.beginTransaction();
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->查询表"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			stmt = (Statement)conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(1);  
			stmt.setFetchDirection(ResultSet.FETCH_FORWARD); 
			rs = stmt.executeQuery("select * from dbo."+table+" ");
			if (rs == null)
				return 0;   
		    ResultSetMetaData md = rs.getMetaData();                 //得到结果集(rs)的结构信息，比如字段数、字段名等   
		    int columnCount = md.getColumnCount();                   //返回此 ResultSet 对象中的列数   
		    CommonQueryBS.systemOut("   rs 开始"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//----------------------------------------------------------------------------------
		    CommonQueryBS.systemOut("   CodeTableCol 开始"+table+"sql拼接完成："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//----------------------------------------------------------------------------------
//			int dd = 0;
			while (rs.next()) {
				Map rowData = new HashMap(columnCount); 
		    	for (int j = 1; j <= columnCount; j++) {   
		    		rowData.put(md.getColumnName(j), rs.getObject(j)); 
		        }
		    	if(rowData.get("A0000")!=null && !rowData.get("A0000").toString().equals("")){
					pstmt1.setObject(1, rowData.get("A0000"));
					pstmt1.setObject(2, rowData.get("A5714"));
					pstmt1.setObject(3, "1");
			        
			        String a5714 = rowData.get("A5714")!=null?rowData.get("A5714").toString():"";
					String a5714_arr[] = a5714.split("\\|");
					String photoname = a5714_arr[0].equals("")?(rowData.get("A0000")+".jpg"):a5714_arr[0];
				    pstmt1.setObject(4, photoname);
				    pstmt1.setObject(5, "jpg");
				    String photopath = "";
				    String subphotoname = photoname.substring(0, photoname.indexOf("."));
				    if (subphotoname.length() >= 2) {
				    	String str = subphotoname.substring(0, 2);
				    	if(PhotosUtil.isLetterDigit(str)){
				    		photopath = photoname.charAt(0)+"/"+photoname.charAt(1)+"/";
				    	} else {
				    		photopath = photoname.charAt(0)+"/";
				    	}
					} else if (photoname.substring(0, photoname.indexOf(".")).length() == 1) {
						photopath = photoname+"/";
					}
				    pstmt1.setObject(6, photopath);
					pstmt1.addBatch();
					count ++ ;
					rowData.clear();
					rowData=null;
				}
		    	if(count%5000 == 0){
					pstmt1.executeBatch();
			    	pstmt1.clearBatch();
			    	CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+table + "---5000一次" + count/5000 +",内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			    	System.gc();      //释
		    	}
		    }
			
			
		    md = null;
//		    rs.close();
//			stmt.close();
//			KingBSImpUtil.closeConnection(conn);
//			System.gc();      //释放内存
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->关闭内存"+table+"结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
//			if(rs!=null)
//				rs.close();
//			if(stmt!=null)
//				stmt.close();
//			if(conn!=null)
//				KingBSImpUtil.closeConnection(conn);
			throw e;
		} catch (Throwable t){
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+t.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			CommonQueryBS.systemOut(DateUtil.getTime()+"--->查询表"+table+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t.printStackTrace();
//			if(rs!=null)
//				rs.close();
//			if(stmt!=null)
//				stmt.close();
//			if(conn!=null)
//				KingBSImpUtil.closeConnection(conn);
//			if(sess != null)
//				sess.getTransaction().rollback();
			throw new AppException(t.getMessage());
		}finally{
     		if(rs != null){
     			rs.close();
     		}
     		if(stmt != null){
     			stmt.close();
     		}
     		if(conn != null){
     			KingBSImpUtil.closeConnection(conn);
     		}
     		
		}
		return count;
	}
	

	public static void impTimeData(int i, int personsize, Imprecord imprecord, String photo_file, String deptid, String table) throws Exception {
		String     B0111       = imprecord.getEmpdeptid();
		String     impdeptid   = imprecord.getImpdeptid();
		String     imprecordid = imprecord.getImprecordid();
		HBSession  sess        = HBUtil.getHBSession();
		Connection conn        = KingBSImpUtil.getConnection3();
		Statement  stmt        = null;
		ResultSet  rs          = null;
		try {
			CommonQueryBS.systemOut("   "+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			stmt = conn.createStatement();
			long t_n = Integer.parseInt(imprecord.getTotalnumber());
			List<Map<String, String>> listmaps = new ArrayList<Map<String,String>>();
//			getTempByTableName(i, personsize,table,listmaps);
			stmt = conn.createStatement();
			if(table.equals("A01")||table.equals("A29")||table.equals("A30")||table.equals("A31")||table.equals("A37")
					||table.equals("A57")){
				rs = stmt.executeQuery("select * from dbo."+table+" order by a0000 limit " + personsize +" OFFSET " +i*personsize);
			} else if(table.equals("B01")){
				rs = stmt.executeQuery("select * from dbo.b01 order by b0111 limit " + personsize +" OFFSET " +i*personsize);
			} else {
				rs = stmt.executeQuery("select * from dbo."+table+" order by "+(table.toLowerCase() + "00,")+"a0000 limit " + personsize +" OFFSET " +i*personsize);
			}
			if (rs == null)
				return ;   
		    ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
		    int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
		    CommonQueryBS.systemOut("   vlistmaps 开始"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		    
 
		    while (rs.next()) {
		    	Map rowData = new HashMap(columnCount); 
		    	for (int j = 1; j <= columnCount; j++) {   
		    		rowData.put(md.getColumnName(j), rs.getObject(j)!=null? rs.getObject(j).toString() :"");   
		        }
		    		    	
		    	listmaps.add(rowData); 
//		    	if(table.equals("A02")){
//			    	rowData.put("A0201", "1");
//			    	}	
//		    	rowData.clear();
		    }
//		    rowData.clear();
//		    rowData = null;
		
		  
		    
		    CommonQueryBS.systemOut("   vlistmaps 结束"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		    
		    md = null;
//		    rs.close();
//			stmt.close();
//			KingBSImpUtil.closeConnection(conn);
			System.gc();
			System.gc();
			
			CommonQueryBS.systemOut("   "+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+table+"结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			
			CommonQueryBS.systemOut("      第"+ i+"次"+table+"开始插入:"+DateUtil.getTime()+"--------开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t_n = toTempSave(table, listmaps, imprecordid, photo_file, B0111, impdeptid, deptid,t_n);
			CommonQueryBS.systemOut("      第"+ i+"次"+table+"插入结束:"+DateUtil.getTime()+"--------结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
	//		listmaps.removeAll(listmaps);
			for(int k=listmaps.size()-1; k>=0 ;k--){
				//listmaps.remove(k);
				Map map = listmaps.get(k);
				map.clear();
				map=null;
			}
			System.gc();
			System.gc();
			CommonQueryBS.systemOut("循环释放listmaps后内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			
			listmaps.clear();
			listmaps = null;
			System.gc();
			System.gc();
			
			CommonQueryBS.systemOut("  "+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->更新表的导入标记，开始时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			imprecord.setTotalnumber(t_n + "");
			CommonQueryBS.systemOut("  "+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->更新表的导入标记，结束时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//释放内存
			System.gc();
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
//			if(rs!=null)
//				rs.close();
//			if(stmt!=null)
//				stmt.close();
//			if(conn!=null)
//				KingBSImpUtil.closeConnection(conn);
//			if(sess != null)
//				sess.getTransaction().rollback();
			throw e;
		} catch (Throwable t){
			CommonQueryBS.systemOut(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+table+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t.printStackTrace();
//			if(rs!=null)
//				rs.close();
//			if(stmt!=null)
//				stmt.close();
//			if(conn!=null)
//				KingBSImpUtil.closeConnection(conn);
//			if(sess != null)
//				sess.getTransaction().rollback();
			throw new AppException(t.getMessage());
		}finally{
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				KingBSImpUtil.closeConnection(conn);
			if(sess != null)
				sess.getTransaction().rollback();
		}
	}
	

	private static long toTempSave(String table,
			List<Map<String, String>> list, String imprecordid, String photo_file, String B0111, String impdeptid, String deptid, long t_n) {
		if(list == null || list.size()==0){
			return t_n;
		} else {
			t_n = t_n + list.size();
		}
		CommonQueryBS.systemOut("1 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));

		HBSession sess1 = HBUtil.getHBSession();
		
		CommonQueryBS.systemOut("2 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));		
		try {
			sess1.beginTransaction();
			if(table.equals("A01")){
				A01temp temp = null;
				Map<String, String> map = null;
				A30temp a30 = null;
				A31temp a31 = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A01temp) Map2Temp.mapToObject(A01temp.class, map);
					temp.setIsqualified("0");
					temp.setImprecordid(imprecordid);
					String a0107 = temp.getA0107();
					if(a0107!=null && !a0107.equals("")){
						if(a0107.length()==8){
							int age = DateUtil.getAgeByBirthday(DateUtil.stringToDate(a0107, "yyyyMMdd"));
							temp.setAge(Long.parseLong(age+""));
							temp.setNl(age+"");
							temp.setJsnlsj(DateUtil.getSysDate());
						} else if(a0107.length()==6){
							int age = DateUtil.getAgeByBirthday(DateUtil.stringToDate(a0107+"01", "yyyyMMdd"));
							temp.setAge(Long.parseLong(age+""));
							temp.setNl(age+"");
							temp.setJsnlsj(DateUtil.getSysDate());
						}
					}
					a30 = (A30temp) sess1.get(A30temp.class, temp.getA0000());
					a31 = (A31temp) sess1.get(A31temp.class, temp.getA0000());
					if(a31!=null){
						String a3101 = a31.getA3101();//离退类别
						if(a3101!=null&&!"".equals(a3101)){
							temp.setA0163("2");
							temp.setStatus("3");
						}else{
							temp.setA0163("1");
							temp.setStatus("1");
						}
					} else {
						temp.setA0163("1");
						temp.setStatus("1");
					}
					if(a30!=null){
						String a3001 = a30.getA3001();
						if(a3001!=null&&!"".equals(a3001)){
							//调出人员     历史库
							if("1".startsWith(a3001)||"2".startsWith(a3001)){
								temp.setA0163("3");
								temp.setStatus("2");
							}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
								temp.setA0163("4");
								temp.setStatus("2");
							}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
								temp.setA0163("2");
								temp.setStatus("3");
							}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
								temp.setA0163("5");
								temp.setStatus("2");
							}
						}
					}
					sess1.save(temp);
					temp =null;
					a30 = null;
					a31 = null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A02")){
				Map<String, String> map = null;
				A02temp temp = null;
			//	Iterator iterator = list.iterator();
				for (int i = 0; i < list.size(); i++) {
					map = (Map<String, String>) list.get(i);
					temp = (A02temp) Map2Temp.mapToObject(A02temp.class, map);
					if(temp.getA0281()!=null && temp.getA0281().equals("1")){
						temp.setA0281("true");
					} else {
						temp.setA0281("false");
					}
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					
				}
				CommonQueryBS.systemOut("5 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				sess1.getTransaction().commit();
				
				
				//sess1.close();
				System.gc();
				System.gc();
				CommonQueryBS.systemOut("6 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			} else if(table.equals("A06")){
				Map<String, String> map = null;
				A06temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A06temp) Map2Temp.mapToObject(A06temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A08")){
				Map<String, String> map = null;
				A08temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A08temp) Map2Temp.mapToObject(A08temp.class, map);
					if(temp.getA0899()!=null && temp.getA0899().equals("1")){
						temp.setA0899("true");
					} else {
						temp.setA0899("false");
					}
					String codechange = temp.getA0827();
					if(codechange!=null && !codechange.equals(""))
						temp.setA0827(Long.parseLong(codechange)+"");
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A11")){
				Map<String, String> map = null;
				A11temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A11temp) Map2Temp.mapToObject(A11temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A14")){
				Map<String, String> map = null;
				A14temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A14temp) Map2Temp.mapToObject(A14temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A15")){
				Map<String, String> map = null;
				A15temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A15temp) Map2Temp.mapToObject(A15temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A29")){
				Map<String, String> map = null;
				A29temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A29temp) Map2Temp.mapToObject(A29temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A30")){
				Map<String, String> map = null;
				A30temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A30temp) Map2Temp.mapToObject(A30temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A31")){
				Map<String, String> map = null;
				A31temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A31temp) Map2Temp.mapToObject(A31temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A36")){
				Map<String, String> map = null;
				A36temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A36temp) Map2Temp.mapToObject(A36temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A37")){
				Map<String, String> map = null;
				A37temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A37temp) Map2Temp.mapToObject(A37temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A41")){
				Map<String, String> map = null;
				A41temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A41temp) Map2Temp.mapToObject(A41temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A53")){
				Map<String, String> map = null;
				A53temp temp = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A53temp) Map2Temp.mapToObject(A53temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					sess1.save(temp);
					temp =null;
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("A57")){
				Map<String, String> map = null;
				A57temp temp = null;
				File photo = null;
				FileInputStream in = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (A57temp) Map2Temp.mapToObject(A57temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					if(temp.getA5714()!=null && !temp.getA5714().equals("")){
						String a5714[] = temp.getA5714().split("\\|");
						photo = new File(photo_file + "/" +a5714[0]);
						if(photo.exists() && photo.isFile()){
							in = new FileInputStream(photo);
							temp.setPhotodata(Hibernate.createBlob(in));
							temp.setPhotoname(photo.getName());
							temp.setPhotstype(photo.getName().split("\\.")[1]);
						}
					}
					sess1.save(temp);
					temp =null;
					photo = null;
					if(in!=null){
						in.close();
						in = null;
					}
					map.clear();
					map = null;
				}
				sess1.flush();
			} else if(table.equals("B01")){
				
				CommonQueryBS.systemOut("3 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				Map<String, String> map = null;
				B01temp temp = null;
				CommonQueryBS.systemOut("4 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					map = (Map<String, String>) iterator.next();
					temp = (B01temp) Map2Temp.mapToObject(B01temp.class, map);
					temp.setImprecordid(imprecordid);
					temp.setIsqualified("0");
					if ((temp.getB0121() == null
							|| temp.getB0121().equals("") || temp
							.getB0111().equals(B0111))
							&& deptid != null) {
						temp.setB0121(deptid.toString());
					}
					B01tempb01 tempb = new B01tempb01();
					tempb.setImprecordid(imprecordid);
					tempb.setTempb0111(temp.getB0111());
					tempb.setNewb0111(impdeptid
							+ temp.getB0111().substring(B0111.length()));
					sess1.save(tempb);
					sess1.save(temp);
					tempb =null;
					temp =null;
					map.clear();
					map = null;
				}
				CommonQueryBS.systemOut("5 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				sess1.getTransaction().commit();
				//sess1.close();
				System.gc();
				CommonQueryBS.systemOut("6 "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t_n;
	}

	private static List resultSetToList(ResultSet rs) throws SQLException {
		if (rs == null)   
            return Collections.EMPTY_LIST;   
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
        List<Map<String, String>> list = new ArrayList();   
        Map rowData = new HashMap();   
        while (rs.next()) {   
         rowData = new HashMap(columnCount);   
         for (int i = 1; i <= columnCount; i++) {   
                 rowData.put(md.getColumnName(i), rs.getObject(i)!=null? rs.getObject(i).toString() :"");   
         }   
         list.add(rowData);   
        }   
        return list;   
	}
	private static List getB01Temp(String imprecordid) throws SQLException {
		List list = null;
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from dbo.b01 where b0121 is not null");
			list = resultSetToList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return list;
	}
	
	
	private static void getTempByTableName(int i, int personsize,String tableName, List<Map<String, String>> listmaps) throws SQLException, AppException {
		CommonQueryBS.systemOut(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+tableName+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			 stmt = conn.createStatement();
			 if(tableName.equals("A01")||tableName.equals("A29")||tableName.equals("A30")||tableName.equals("A31")||tableName.equals("A37")
					 ||tableName.equals("A57")){
				 rs = stmt.executeQuery("select * from dbo."+tableName+" order by a0000 limit " + personsize +" OFFSET " +i*personsize);
			 }  if(tableName.equals("B01")){
				 rs = stmt.executeQuery("select * from dbo.b01 order by b0111 limit " + personsize +" OFFSET " +i*personsize);
			 } else {
				 rs = stmt.executeQuery("select * from dbo."+tableName+" order by "+(tableName.toLowerCase() + "00,")+"a0000 limit " + personsize +" OFFSET " +i*personsize);
			 }
			 if (rs == null)
				 return ;   
		     ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
		     int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
		     Map rowData = new HashMap();
		     while (rs.next()) {
		    	 rowData = new HashMap(columnCount);   
		    	 for (int j = 1; j <= columnCount; j++) {   
		    		 rowData.put(md.getColumnName(j), rs.getObject(j)!=null? rs.getObject(j).toString() :"");   
		         }   
		    	 listmaps.add(rowData);   
		     }
		     rowData.clear();
		     rowData = null;
		} catch (SQLException e) {
			CommonQueryBS.systemOut(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+tableName+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			e.printStackTrace();
			throw e;
		} catch(Throwable e){
			CommonQueryBS.systemOut(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+tableName+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			throw new AppException(e.getMessage());
		}finally{
     		if(rs != null){
     			rs.close();
     		}
     		if(stmt != null){
     			stmt.close();
     		}
     		if(conn != null){
     			conn.close();
     		}
     		
		}
		CommonQueryBS.systemOut(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"--->查询表"+tableName+"结束时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
	}

	public static void dealRollback(String imprecordid,String tableExt) {
		String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
				"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
				"I_E", "B_E","A60", "A61", "A62", "A63", "A64","A05", "A68", "A69", "A71" ,"A99Z1"  };
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			try {
				stmt.execute("delete from imp_record where imp_record_id='"+imprecordid +"'");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(tableExt!=null && !tableExt.equals("")){
				for (int i = 0; i < tables.length; i++) {
					try {
						stmt.execute("drop table "+tables[i] +tableExt);
					} catch (Exception e) {
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(stmt != null){
     			try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
     		if(conn != null){
     			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
     		
		}
		
	}

	public static List<HashMap<String, Object>> getB01ListByPage(int start,
			int limit) {
		List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
		Connection conn        = KingBSImpUtil.getConnection3();					//获取 金仓数据库打开数据库连接
		Statement  stmt        = null;								//定义 金仓数据库statement
		ResultSet  rs          = null;								//定义 金仓数据库结果集
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select b0111,b0101,b0114,b0121 from dbo.b01 where b0111 <>'XXX' and b0194<>'2' order by b0111 limit " + limit +" OFFSET " +start);
			if (rs == null)
				return gridList;
			ResultSetMetaData md = rs.getMetaData();                 //得到结果集(rs)的结构信息，比如字段数、字段名等   
		    int columnCount = md.getColumnCount();                   //返回此 ResultSet 对象中的列数   
		    while (rs.next()) {
		    	HashMap rowData = new HashMap(columnCount); 
		    	for (int j = 1; j <= columnCount; j++) {   
		    		rowData.put(md.getColumnName(j).toLowerCase(), rs.getObject(j)); 
		        }
		    	gridList.add(rowData);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
     			try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
     		if(stmt != null){
     			try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
     		if(conn != null){
     			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
		}
		
		return gridList;
	}
	public static int getAllOrgNotNsjg() {
		List<B01> list = null;
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from dbo.b01 where b0111 <>'XXX' and b0194<>'2' ");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
     			try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
     		if(stmt != null){
     			try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
     		if(conn != null){
     			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
     		}
		}
		return 0;
	}

	public static int impTimeData_Table(
			Imprecord imprecord, String photo_file, String deptid,
			String table, String bzbDeptid, String tableExt, PreparedStatement pstmt1, List colomns, int tabsize) throws Exception {
		String     B0111       = imprecord.getEmpdeptid();
		String     impdeptid   = imprecord.getImpdeptid();
		String     imprecordid = imprecord.getImprecordid();  		//获取导入记录信息id
		HBSession  sess        = HBUtil.getHBSession();				//获取 金仓数据库打开数据库
		Connection conn        = KingBSImpUtil.getConnection3();		//获取 金仓数据库打开数据库连接
		Statement  stmt        = null;								//定义 金仓数据库statement
		ResultSet  rs          = null;								//定义 金仓数据库结果集
		int count = 0;
		try {
			//----------------------------------------------------------------------------------
			StringBuilder sb = new StringBuilder();
			sb.append("select a1.a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000 and a2.A0255='1' and a2.a0201b like '"+ bzbDeptid +"%' ");
//			sess.beginTransaction();
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->查询表"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			stmt = (Statement)conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(1);  
			stmt.setFetchDirection(ResultSet.FETCH_FORWARD); 
			if(table.equals("B01")){
				rs = stmt.executeQuery("select * from dbo.b01 where b0111 like '"+bzbDeptid+"%' ");
				CommonQueryBS.systemOut("select * from dbo.b01 where b0111 like '"+bzbDeptid+"%' ");
			} else {
//				rs = stmt.executeQuery("select * from dbo."+table+" where a0000 in ("+sb.toString()+") ");
				if(!bzbDeptid.equals("001.001")){
					rs = stmt.executeQuery("select * from dbo."+table+" where a0000 in ("+sb.toString()+") ");
					System.out.println("select * from dbo."+table+" where a0000 in ("+sb.toString()+") ");
				} else {
					rs = stmt.executeQuery("select * from dbo."+table+" ");
					System.out.println("select * from dbo."+table+" ");
				}
				CommonQueryBS.systemOut("select * from dbo."+table+" where a0000 in ("+sb.toString()+") ");
			}
			if (rs == null)
				return 0;   
		    ResultSetMetaData md = rs.getMetaData();                 //得到结果集(rs)的结构信息，比如字段数、字段名等   
		    int columnCount = md.getColumnCount();                   //返回此 ResultSet 对象中的列数   
		    CommonQueryBS.systemOut("   rs 开始"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//----------------------------------------------------------------------------------
			while (rs.next()) {
		    	Map rowData = new HashMap(columnCount); 
		    	for (int j = 1; j <= columnCount; j++) {   
		    		rowData.put(md.getColumnName(j), rs.getObject(j)); 
		        }
		    	rowData.put("IMPRECORDID", imprecordid);
				rowData.put("IS_QUALIFIED", "0");
				rowData.put("ERROR_INFO", "2");
				if(table.equals("A36")){
					if((rowData.get("A3601")==null || rowData.get("A3601").toString().equals(""))
							&&(rowData.get("A3604A")==null || rowData.get("A3604A").toString().equals(""))
							&&(rowData.get("A3607")==null || rowData.get("A3607").toString().equals(""))
							&&(rowData.get("A3611")==null || rowData.get("A3611").toString().equals(""))
							&&(rowData.get("A3627")==null || rowData.get("A3627").toString().equals(""))){
						
						continue;
					}
				} else if(table.equals("A02")){												//A02 特殊字符处理
					
					String A0267 = rowData.get("A0267")!=null?rowData.get("A0267").toString():"";
					rowData.put("A0267", subStringByByte(A0267, 24));
					String A0229 = rowData.get("A0229")!=null?rowData.get("A0229").toString():"";
					rowData.put("A0229", subStringByByte(A0229, 120)); 
					
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
			    		String A0281 = rowData.get("A0281")!=null?rowData.get("A0281").toString():"";
						rowData.put("A0281", (A0281!=null &&  A0281.equals("1"))?"true":"false");
						//职务名称
						rowData.put("A0215A", rowData.get("A0215B"));
						
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
					
				} else if(table.equals("A29")){
					if(tabsize == 16){
						String A2911 = rowData.get("A2911")!=null?rowData.get("A2911").toString():"";
						/*if(A2911.equals("4")){*/
							rowData.put("A2911", "6");
						/*}*/
					}
					
				} else if(table.equals("A06")){
					rowData.put("A0699", "true");
				} else if(table.equals("A08")){
					//专业GB16835
					String A0827 = rowData.get("A0827")!=null?rowData.get("A0827").toString():"";
					
						//教育
						String A0801B = rowData.get("A0801B")!=null?rowData.get("A0801B").toString():"";
						rowData.put("A0801B", ConversionIndex.ZB64(A0801B));
						
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
								rowData.put("A0827", "");
							}
						} else {
							rowData.put("A0827", "");
						}
					
					
				} else if(table.equals("A14")){
					
						String A1415 = rowData.get("A1415")!=null?rowData.get("A1415").toString():"";
						rowData.put("A1415", ConversionIndex.zb09(A1415));
						String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
						rowData.put("A1404B", ConversionIndex.ZB65(A1404B));
					
					String A1411A = rowData.get("A1411A")!=null?rowData.get("A1411A").toString():"";
					rowData.put("A1411A", subStringByByte(A1411A, 60));
					String A1404A = rowData.get("A1404A")!=null?rowData.get("A1404A").toString():"";
					rowData.put("A1404A", subStringByByte(A1404A, 40));
				} else if(table.equals("A15")){
					String A1521 = rowData.get("A1521")!=null?rowData.get("A1521").toString():"";
					rowData.put("A1521", subStringByByte(A1521, 4));
				} else if(table.equals("A11")){
					String a1107 = rowData.get("A1107")!=null?rowData.get("A1107").toString():"";
					String a1111 = rowData.get("A1111")!=null?rowData.get("A1111").toString():"";
					if(a1107!=null && !a1107.equals("")&& (a1107.length()==6||a1107.length()==8)
							&& a1111!=null && !a1111.equals("")&&(a1111.length()==6||a1111.length()==8)){
						a1107 = (a1107 + "01").substring(0, 8);
						a1111 = (a1111 + "01").substring(0, 8);
						int d = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyyMMdd"), 
								DateUtil.stringToDate(a1111, "yyyyMMdd"));
						rowData.put("A1107C", d);
					} else {
						rowData.put("A1107C", 0);
					}
					String A1101 = rowData.get("A1101")!=null?rowData.get("A1101").toString():"";
					rowData.put("A1101", ConversionIndex.ZB29(A1101));
					String A1127 = rowData.get("A1127")!=null?rowData.get("A1127").toString():"";
					rowData.put("A1127", ConversionIndex.ZB27(A1127));
					
				} else if(table.equals("B01")){
					String b0121 = rowData.get("B0121")!=null?rowData.get("B0121").toString():"";
					String b0111 = rowData.get("B0111")!=null?rowData.get("B0111").toString():"";
					/*if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
								&& deptid != null) {
						rowData.put("B0121", deptid.toString());
					} else {
						rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
					}*/
					if ((b0121 == null || b0121.equals("") || b0111.equals(B0111))
								&& deptid != null) {
						rowData.put("B0121", deptid.toString());
					} else {
						rowData.put("B0121", impdeptid + (b0121.substring(B0111.length())));
					}
					
					rowData.put("B0111", impdeptid + (b0111.substring(B0111.length())));
					
						String B0131 = rowData.get("B0131")!=null?rowData.get("B0131").toString():"";
						rowData.put("B0131", ConversionIndex.ZB04(B0131));
						String B0124 = rowData.get("B0124")!=null?rowData.get("B0124").toString():"";
						rowData.put("B0124", subStringByByte(ConversionIndex.ZB87(B0124), 2));
						String B0127 = rowData.get("B0127")!=null?rowData.get("B0127").toString():"";
						rowData.put("B0127", subStringByByte(ConversionIndex.ZB03(B0127),4));
					
						String B0191 = rowData.get("B0191")!=null?rowData.get("B0191").toString():"";
						if(B0191.length()>8){
							rowData.put("B0191", subStringByByte(B0191, 8));
						}
						
						String B0117 = rowData.get("B0117")!=null?rowData.get("B0117").toString():"";
						rowData.put("B0117", subStringByByte(B0117, 6));
				}  else if(table.equals("A01")){
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
						
					
				} else if(table.equals("A31")){
					if(tabsize == 16){
						String A3101 = rowData.get("A3101")!=null?rowData.get("A3101").toString():"";
						if(A3101.equals("01")){
							rowData.put("A2911", "1");
						} else if(A3101.equals("02")){
							rowData.put("A2911", "2");
						} else if(A3101.equals("03")){
							rowData.put("A2911", "3");
						}
					}
					String A3107 = rowData.get("A3107")!=null?rowData.get("A3107").toString():"";
					rowData.put("A3107", ConversionIndex.zb09(A3107));
				} else if(table.equals("A14")){
					String A1415 = rowData.get("A1415")!=null?rowData.get("A1415").toString():"";
					rowData.put("A1415", ConversionIndex.zb09(A1415));
					String A1404B = rowData.get("A1404B")!=null?rowData.get("A1404B").toString():"";
					rowData.put("A1404B", ConversionIndex.ZB65(A1404B));
				} else if(table.equals("A30")){
					String A3001 = rowData.get("A3001")!=null?rowData.get("A3001").toString():"";
					rowData.put("A3001", ConversionIndex.ZB78(A3001));
				}
		    	for (int j = 0; j < colomns.size(); j++) {
	    			pstmt1.setObject(j+1, rowData.get(colomns.get(j).toString()));
				}
//		    	pstmt1.execute();
		    	pstmt1.addBatch();
				rowData.clear();
				rowData=null;
				count ++;
				if(count%5000 == 0){
					pstmt1.executeBatch();
			    	pstmt1.clearBatch();
			    	CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+table + "---5000一次" + count/5000 +",内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			    	System.gc();      //释
				}
		    }
			
		
//		    sess.flush();
//		    sess.getTransaction().commit();
		   
		    md = null;
			System.gc();      //释放内存
			CommonQueryBS.systemOut("   bzb-->"+DateUtil.getTime()+"--->关闭内存"+table+"结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			dealRollback(imprecordid, tableExt);
			throw e;
		} catch (Throwable t){
			CommonQueryBS.systemOut(DateUtil.getTime()+"--->查询表"+table+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+t.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			dealRollback(imprecordid, tableExt);
			throw new AppException(t.getMessage());
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				KingBSImpUtil.closeConnection(conn);
			}

		}
	}
	public static void impTimeData_A57(
			Imprecord imprecord, String photo_file, String deptid,
			String table, String bzbDeptid, String tableExt, PreparedStatement pstmt1) throws Exception {
		String     B0111       = imprecord.getEmpdeptid();
		String     impdeptid   = imprecord.getImpdeptid();
		String     imprecordid = imprecord.getImprecordid();  		//获取导入记录信息id
		HBSession  sess        = HBUtil.getHBSession();				//获取 金仓数据库打开数据库
		Connection conn        = KingBSImpUtil.getConnection3();					//获取 金仓数据库打开数据库连接
		Statement  stmt        = null;								//定义 金仓数据库statement
		ResultSet  rs          = null;								//定义 金仓数据库结果集
		int count = 0;
		try {
			//----------------------------------------------------------------------------------
			String dir = AppConfig.HZB_PATH + "/temp/upload/unzip/"+imprecordid +"/Photos/"; //图片临时存放路径。
			List<String> list = new ArrayList<String>();
//			sess.beginTransaction();
			StringBuilder sb = new StringBuilder();
			sb.append("select a1.a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000 and a2.A0255='1' and a2.a0201b like '"+ bzbDeptid +"%' ");
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->查询表"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			stmt = (Statement)conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(1);  
			stmt.setFetchDirection(ResultSet.FETCH_FORWARD); 
			rs = stmt.executeQuery("select * from dbo."+table+" where a0000 in ("+sb.toString()+") ");
			if (rs == null)
				return ;   
		    ResultSetMetaData md = rs.getMetaData();                 //得到结果集(rs)的结构信息，比如字段数、字段名等   
		    int columnCount = md.getColumnCount();                   //返回此 ResultSet 对象中的列数   
		    CommonQueryBS.systemOut("   rs 开始"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//----------------------------------------------------------------------------------
		    CommonQueryBS.systemOut("   CodeTableCol 开始"+table+"sql拼接完成："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			//----------------------------------------------------------------------------------
//			int dd = 0;
			while (rs.next()) {
				
		    	Map rowData = new HashMap(columnCount); 
		    	for (int j = 1; j <= columnCount; j++) {   
		    		rowData.put(md.getColumnName(j), rs.getObject(j)); 
		        }
		    	if(rowData.get("A0000")!=null && !rowData.get("A0000").toString().equals("")){
					pstmt1.setObject(1, rowData.get("A0000"));
					pstmt1.setObject(2, rowData.get("A5714"));
					pstmt1.setObject(3, "1");
					 
			        String a5714 = rowData.get("A5714")!=null?rowData.get("A5714").toString():"";
					String a5714_arr[] = a5714.split("\\|");
					String photoname = a5714_arr[0].equals("")?(rowData.get("A0000")+".jpg"):a5714_arr[0];
				    pstmt1.setObject(4, photoname);
				    pstmt1.setObject(5, "jpg");
				    String photopath = "";
				    String subphotoname = photoname.substring(0, photoname.indexOf("."));
				    if (subphotoname.length() >= 2) {
				    	String str = subphotoname.substring(0, 2);
				    	if(PhotosUtil.isLetterDigit(str)){
				    		photopath = photoname.charAt(0)+"/"+photoname.charAt(1)+"/";
				    	} else {
				    		photopath = photoname.charAt(0)+"/";
				    	}
					} else if (photoname.substring(0, photoname.indexOf(".")).length() == 1) {
						photopath = photoname+"/";
					}
				    pstmt1.setObject(6, photopath);
					pstmt1.addBatch();
					rowData.clear();
					rowData=null;
					//list.add(photo_file + "" +a5714_arr[0]);
					count ++ ;
				}
		    	if(count%5000 == 0){
					pstmt1.executeBatch();
			    	pstmt1.clearBatch();
			    	CommonQueryBS.systemOut("   bzb-->"+DateUtil.getTime()+"---"+table + "---5000一次" + count/5000 +",内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			    	System.gc();      //释
		    	}
		    }
			
//			PhotosUtil.copyCmdAbsolutePath(list, dir);
			PhotosUtil.copyAbsoluteFiles(list, dir);
		    md = null;
//		    rs.close();
//			stmt.close();
//			KingBSImpUtil.closeConnection(conn);
			System.gc();      //释放内存
			CommonQueryBS.systemOut("   "+DateUtil.getTime()+"--->关闭内存"+table+"结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			dealRollback(imprecordid, tableExt);
			throw e;
		} catch (Throwable t){
			CommonQueryBS.systemOut(DateUtil.getTime()+"--->查询表"+table+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+t.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			dealRollback(imprecordid, tableExt);
			throw new AppException(t.getMessage());
		}finally{
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				KingBSImpUtil.closeConnection(conn);
		}
		
	}
	

	public static int getSizeByTable(String talbe, String bzbDeptid) {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			if(talbe.equals("B01")){
				sb.append("select count(*) from dbo.b01 where b0111 like '"+bzbDeptid+"%'");
			} else {
				sb.append("select count(*) from dbo."+talbe+" where a0000 in(");
				sb.append("select a1.a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000 and a2.A0255='1' and a2.a0201b like '"+ bzbDeptid +"%' ");
				sb.append(")");
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				KingBSImpUtil.closeConnection(conn);
		}
		return 0;
	}

	public static B01 getB01byid(String bzbDeptid) {
		Connection conn = KingBSImpUtil.getConnection3();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from b01 where b0111='" +bzbDeptid+ "'");
			List list = ResultToObject(rs, B01.class);
			if (list != null && list.size() > 0) {
				return (B01) list.get(0);
			}
			list = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Throwable thr){
			thr.printStackTrace();
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				KingBSImpUtil.closeConnection(conn);
			System.gc();
		}
		return null;
	}

	public static int impTimeData_Table(String logfilename, Imprecord imprecord,
		String photo_file, String deptid, String table,String tableExt, int tabsize) throws Exception {
		appendFileContent(logfilename, table + "数据导入"+ DateUtil.getTime()+"===>"+"\n");
		HBSession  sess        = HBUtil.getHBSession();			//获取 金仓数据库打开数据库
		StringBuffer colomn_sql = new StringBuffer();           //b01字段连接 如(a0000,A0200......)
		StringBuffer value_sql = new StringBuffer();            //b01字段插入值连接(?,?,?)
		String sql = null;
		List colomns = null;
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		try {
			if(DBType.MYSQL == DBUtil.getDBType() ){
				sql = "select column_name from information_schema.columns a where table_name = upper('"+table+tableExt+"') and a.TABLE_SCHEMA = 'ZWHZYQ'";
			}else if(DBType.ORACLE == DBUtil.getDBType()){
				sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"+table+tableExt+"')";
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
			conn1 = sess.connection();
			conn1.setAutoCommit(true);
			CommonQueryBS.systemOut("=========" + conn1.getAutoCommit());
			pstmt1 = conn1.prepareStatement("insert into "+table+tableExt+"("+colomn_sql+") values("+value_sql+")");
			
			int count = KingbsGainBS.impTimeData_Table(imprecord, photo_file, deptid, table, pstmt1, colomns, tabsize);
			pstmt1.executeBatch();
			pstmt1.clearBatch();
			CommonQueryBS.systemOut("   CodeTableCol 开始"+table+"获取List CodeTableCol："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+",内存"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+"\n");
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+"\n");
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt1 != null)
				pstmt1.close();
			if(conn1 != null)
				conn1.close();
		}
	}
	
	public static void impTimeData_A57(String logfilename, Imprecord imprecord,
			String photo_file, String deptid, String table,String tableExt) throws Exception {
		appendFileContent(logfilename, table + "数据导入"+ DateUtil.getTime()+"===>"+"\n");
		HBSession  sess        = HBUtil.getHBSession();			//获取 金仓数据库打开数据库
		StringBuffer colomn_sql = new StringBuffer();           //b01字段连接 如(a0000,A0200......)
		StringBuffer value_sql = new StringBuffer();            //b01字段插入值连接(?,?,?)
		String sql = null;
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		try {
			conn1 = sess.connection();
			conn1.setAutoCommit(true);
			pstmt1 = conn1.prepareStatement("insert into a57"+tableExt+"(A0000,A5714,UPDATED," +
					"PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,  ?)");
			
			KingbsGainBS.impTimeData_A57(imprecord, photo_file, deptid, table, pstmt1);
			pstmt1.executeBatch();
			pstmt1.clearBatch();
			CommonQueryBS.systemOut("   CodeTableCol 开始"+table+"获取List CodeTableCol："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+",内存"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+"\n");
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt1 != null)
				pstmt1.close();
			if(conn1 != null)
				conn1.close();
		}
		
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

	public static int impTimeData_Table(String logfilename, Imprecord imprecord, String photo_file,
			String deptid, String table, String bzbDeptid, String tableExt, int tabsize) throws Exception {
		appendFileContent(logfilename, table + "数据导入"+ DateUtil.getTime()+"===>"+"\n");
		HBSession  sess        = HBUtil.getHBSession();			//获取 金仓数据库打开数据库
		StringBuffer colomn_sql = new StringBuffer();           //b01字段连接 如(a0000,A0200......)
		StringBuffer value_sql = new StringBuffer();            //b01字段插入值连接(?,?,?)
		String sql = null;
		List colomns = null;
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		try {
			if(DBType.MYSQL == DBUtil.getDBType() ){
				sql = "select column_name from information_schema.columns a where table_name = upper('"+table+tableExt+"')  and a.TABLE_SCHEMA = 'ZWHZYQ'";
			}else if(DBType.ORACLE == DBUtil.getDBType()){
				sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name =upper('"+table+tableExt+"')";
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
			conn1 = sess.connection();
			conn1.setAutoCommit(true);
			CommonQueryBS.systemOut("=========" + conn1.getAutoCommit());
			pstmt1 = conn1.prepareStatement("insert into "+table+tableExt+"("+colomn_sql+") values("+value_sql+")");
			int count = impTimeData_Table(imprecord, photo_file, deptid, table,bzbDeptid,tableExt,pstmt1,colomns, tabsize);
			pstmt1.executeBatch();
			pstmt1.clearBatch();
			CommonQueryBS.systemOut("   CodeTableCol 开始"+table+"获取List CodeTableCol："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+",内存"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+"\n");
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+"\n");
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt1 != null)
				pstmt1.close();
			if(conn1 != null)
				conn1.close();
		}
	}

	public static void impTimeData_A57(String logfilename, Imprecord imprecord, String photo_file,
			String deptid, String table, String bzbDeptid, String tableExt) throws Exception {
		appendFileContent(logfilename, table + "数据导入"+ DateUtil.getTime()+"===>"+"\n");
		HBSession  sess        = HBUtil.getHBSession();			//获取 金仓数据库打开数据库
		StringBuffer colomn_sql = new StringBuffer();           //b01字段连接 如(a0000,A0200......)
		StringBuffer value_sql = new StringBuffer();            //b01字段插入值连接(?,?,?)
		String sql = null;
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		try {
			conn1 = sess.connection();
			conn1.setAutoCommit(true);
			pstmt1 = conn1.prepareStatement("insert into a57"+tableExt+"(A0000,A5714,UPDATED," +
					"PHOTONAME,PHOTSTYPE,PHOTOPATH) values(?,?,?,?,?,  ?)");
			impTimeData_A57(imprecord, photo_file, deptid, table, bzbDeptid, tableExt, pstmt1);
			pstmt1.executeBatch();
			pstmt1.clearBatch();
			CommonQueryBS.systemOut("   CodeTableCol 开始"+table+"获取List CodeTableCol："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+",内存"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+"\n");
			appendFileContent(logfilename, table+"数据导入"+ DateUtil.getTime()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt1 != null)
				pstmt1.close();
			if(conn1 != null)
				conn1.close();
		}
	}

	public static int getTableSize() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		int comm = 0;
		try {
			conn = KingBSImpUtil.getConnection3();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from information_schema.tables where table_name in "
					+ "('A02','A06','A08','A11','A14','A15','A29','A30','A31','A36','A37','A41','A53','A57','B01','A01')");
			if (rs.next()) {
				comm = rs.getInt(1);
				if(comm < 16){
					return comm;
				}
			}
			rs.close();
			rs = stmt.executeQuery("select count(1) from information_schema.tables where table_name in "
					+ "('A60','A61','A62','A63','A64')");
			if (rs.next()) {
				int num = rs.getInt(1);
				if(num == 5){
					return comm + num;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return comm;
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
}
