package com.insigma.siis.local.business.repandrec.local;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.GlobalNames;
import com.kingbase.jdbc4.Jdbc4PoolingDataSource;

public class KingbsInitBS {
	public static void initKingbs() throws SQLException, RadowException {
		Jdbc4PoolingDataSource ds = new Jdbc4PoolingDataSource();
		Connection conn = null;
		String KING_BD = GlobalNames.sysConfig.get("KING_BD");
		String KING_BD_USER = GlobalNames.sysConfig.get("KING_BD_USER");
		String KING_BD_PWD = GlobalNames.sysConfig.get("KING_BD_PWD");;
		String KING_BD_PORT = GlobalNames.sysConfig.get("KING_BD_PORT");
		String KING_BD_SERVER = GlobalNames.sysConfig.get("KING_BD_SERVER");
		try {
			if (StringUtil.isEmpty(KING_BD) || StringUtil.isEmpty(KING_BD_USER)|| StringUtil.isEmpty(KING_BD_PWD)
					|| StringUtil.isEmpty(KING_BD_PORT)|| StringUtil.isEmpty(KING_BD_SERVER)) {
				throw new RadowException("金仓数据库配置无效，请联系管理员。");
			}
			ds.setServerName(KING_BD_SERVER);
			ds.setDatabaseName(KING_BD);
			ds.setUser(KING_BD_USER);
			ds.setPassword(KING_BD_PWD);
			ds.setMaxConnections(10);
			ds.setInitialConnections(10);
			ds.setPortNumber(Integer.parseInt(KING_BD_PORT));
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("create user ZWHZYQ with superuser password '1234';");
			stmt.execute("CREATE DATABASE ZWHZYQ with OWNER=ZWHZYQ ENCODING='GBK';");
			stmt.close();
			conn.close();
			ds.close();
		} catch (SQLException e) {
			throw e;
		}
		
	}
}
