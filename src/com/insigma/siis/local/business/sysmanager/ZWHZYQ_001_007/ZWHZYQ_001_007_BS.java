package com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.entity.InterfaceUser;
import com.insigma.siis.local.epsoft.fileList.FileListPathUtil;


public class ZWHZYQ_001_007_BS {
	
	HBSession session = HBUtil.getHBSession();						//创建数据库会话连接
	
	public HBSession getSession() {
		return session;
	}

	public void setSession(HBSession session) {
		this.session = session;
	}

	/**
	 * @return 当前接口方案当中最大的序号
	 */
	public int getMaxInterfaceConfigSeq() {
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq = 1;
		try {
			ps = conn.prepareStatement("select max(INTERFACE_CONFIG_SEQUENCE) INTERFACE_CONFIG_SEQUENCE from INTERFACE_CONFIG");
			rs = ps.executeQuery();
			if(rs.next()) {
				maxSeq = rs.getInt("INTERFACE_CONFIG_SEQUENCE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxSeq;
	}
	
	/**
	 * @return 当前接口方案当中最大的序号
	 */
	public int getMaxInterfaceScriptSeq(String configId) {
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq = 1;
		try {
			ps = conn.prepareStatement("select max(INTERFACE_SCRIPT_SEQUENCE) INTERFACE_SCRIPT_SEQUENCE from INTERFACE_SCRIPT where INTERFACE_CONFIG_ID=?");
			ps.setString(1, configId);
			rs = ps.executeQuery();
			if(rs.next()) {
				maxSeq = rs.getInt("INTERFACE_SCRIPT_SEQUENCE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxSeq;
	}
	
	/**
	 * 通过登陆名称查询出用户名
	 * @param  loginName  用户登陆名
	 * @return username   用户名称
	 * @throws RadowException
	 */
	public String getUserName(String loginName) throws RadowException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String username = "";
		String sql = "select USERNAME from smt_user where LOGINNAME='" + loginName + "'";	//根据用户登录名，查询用户名字
		try{
			conn = session.connection();													//创建数据库连接
			st = conn.createStatement();													//创建声明
			rs = st.executeQuery(sql);														//执行查询语句
			if(rs.next())
				username = rs.getString(1);													//获取查询到的用户名
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();																//关闭结果集
				if(st != null)
					st.close();																//关闭声明
				if(conn != null)
					conn.close();                                                       	//关闭数据库连接
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return username;																	//返回查询到的用户名字
	}
	/**
	 * 获取转换后的日期格式字符串
	 * @param date
	 * @return
	 * @throws RadowException
	 */
	public String formatDate(Date date) throws RadowException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");						//设置日期格式为yyyy-MM-dd
		return sdf.format(date);                                  						//将日期yyyy—MM-dd格式转换成字符串格式
	}
	
	/**
	 * 根据configId获取InterfaceConfig对象
	 * @param configId
	 * @return
	 */
	public InterfaceConfig getConfigById(String configId){
		InterfaceConfig config = (InterfaceConfig)session.createQuery("from InterfaceConfig where interfaceConfigId=:interfaceConfigId")
		.setParameter("interfaceConfigId", configId).list().get(0);	                            //用hibernate方法，根据方案编码获取方案对象
		return config;																			//返回获取数据访问接口方案单个对象
	}
	
	/**
	 * 根据数据访问接口配置方案内码获取InterfaceConfig对象
	 * @param configIsn
	 * @return
	 */
	public InterfaceConfig getConfigByIsn(String configIsn){
		InterfaceConfig config = null;
		try {
			config = (InterfaceConfig)session.createQuery("from InterfaceConfig where interfaceConfigIsn=:interfaceConfigIsn")
			.setParameter("interfaceConfigIsn", configIsn).list().get(0);
		} catch (Exception e) {
		}
		return config;																			//返回获取数据访问接口方案单个对象
	}
	
	/**
	 * 将字符串类型的日期转换为Date类型
	 * @param str
	 * @return
	 * @throws RadowException
	 */
	public Date parseDateStr(String str) throws RadowException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");						//设置日期格式
		Date date = null;																//创建日期对象
		try{
			date = sdf.parse(str);														//将日期字符串转换成yyyy—MM-dd格式
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;																	//返回转换后的日期
	}
	
	/**
	 * 查询所
	 * @param node
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceConfig> getInterfaceConfigTree() throws RadowException{
		List<InterfaceConfig> list = new ArrayList<InterfaceConfig>();		//定义一个list集合，用于存放接口类别
		list = session.createQuery("from InterfaceConfig order by interfaceConfigSequence").list();
		return list;
	}
	
	/**
	 * 查询某个方案下面的所有接口脚本
	 * @param interfaceConfigId  接口方案ID
	 * @return 某个方案下面所有的接口脚本
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceScript> getInterfaceScripts(String interfaceConfigId) {
		List<InterfaceScript> list = session.createQuery("from InterfaceScript where interfaceConfigId=:interfaceConfigId order by interfaceScriptSequence")
		.setParameter("interfaceConfigId", interfaceConfigId).list();
		return list;
	}
	/**
	 * 根据interfaceConfigId查询输入参数
	 * @param interfaceConfigId
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List doQueryParam(String interfaceConfigId) throws RadowException{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		String sql = "select INTERFACE_PARAMETER_SEQUENCE,INTERFACE_PARAMETER_NAME,"
						+ "INTERFACE_PARAMETER_DESC,INTERFACE_PARAMETER_TYPE,INTERFACE_CONFIG_ID " 
						+ "from interface_parameter "
						+ "where INTERFACE_CONFIG_ID='" + interfaceConfigId + "' order by INTERFACE_PARAMETER_SEQUENCE ASC";
		try{
			conn = session.connection();
			pst = conn.prepareStatement(sql);
		    rs = pst.executeQuery();
		    ResultSetMetaData rsmd = rs.getMetaData();//
		    int cols = rsmd.getColumnCount();//获取列的数量
		    while(rs.next()){
				HashMap hm = new HashMap();
				for(int i = 1; i <= cols; i++){
					if(i == 1){
						hm.put(rsmd.getColumnName(i).toLowerCase(), rs.getInt(i));
					}else{
						hm.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
					}
				}
				list.add(hm);
		    }
			return list;
		}catch(Exception e){
			throw new RadowException(e.getMessage());
		}finally{
			try{
				if(rs!=null)
					rs.close();						//关闭结果集
				if(pst != null)
					pst.close();					//关闭命令集
				if(conn != null)
					conn.close();					//关闭连接
			}catch(Exception e){
				throw new RadowException(e.getMessage());
			}
		}
	}
	
	/**
	 * 删除方案下面的参数
	 * @param configId
	 * @param paramName
	 */
	public void delConfigParam(String configId, String paramName){
		String sql = "delete from interface_parameter where INTERFACE_CONFIG_ID='" + configId + "' and INTERFACE_PARAMETER_NAME='" + paramName + "'";//根据所传数据访问接口方案编码、参数编码，删除对应的数据访问接口方案对应的方案参数配置信息
		session.createSQLQuery(sql).executeUpdate();                                                               //执行删除语句，自动提交
	}
	/**
	 * 根据configId查询方案下面所有的脚本
	 * @param configId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceScript> getScripts(String configId) {
		String hql = "from InterfaceScript where id.interfaceConfigId=:configId order by interfaceScriptSequence";	//根据数据访问接口方案编码，获取对应数据访问接口方案脚本信息
		List<InterfaceScript> scripts = session.createQuery(hql).setParameter("configId", configId).list();			//执行hql语句，设置方案编码
		return scripts;																								//返回方案脚本列表对象
	}
	/**
	 * 根据configId删除接口方案
	 * @param configId
	 */
	public void delConfig(String configId){
		String sql = "delete from interface_config where INTERFACE_CONFIG_ID='" + configId + "'";	//根据所传数据访问接口方案编码，删除对应的数据访问接口方案信息及其对应的方案参数配置信息
		session.createSQLQuery(sql).executeUpdate();                                   				//执行删除语句
	}
	/**
	 * 根据方案id删除方案的参数
	 * @param configId
	 */
	public void delConfigParameters(String configId){
		String sql = "delete from interface_parameter where INTERFACE_CONFIG_ID='" + configId + "'";	//根据所传数据访问接口方案编码，删除对应的数据访问接口方案对应的方案参数配置信息
		session.createSQLQuery(sql).executeUpdate();													//执行删除语句
	}
	/**
	 * 通过脚本内码查询脚本
	 * @param scriptIsn
	 * @return
	 */
	public InterfaceScript getScriptByIsn(String scriptIsn){
		String hql = "From InterfaceScript where interfaceScriptIsn='" + scriptIsn + "'";	//根据数据访问接口方案内码，获取数据访问接口方案脚本单个对象
		InterfaceScript script = (InterfaceScript)session.createQuery(hql).uniqueResult();	//执行hibernate语句返回数据访问接口方案脚本对象
		return script;																		//返回数据访问接口方案脚本单个对象
	}
	
	/**
	 * 用来生成脚本Id。
	 * @param configId
	 * @return
	 * @throws RadowException
	 */
	public String getAutoScriptId(String configId) throws RadowException{
		String sql = null;
		if(DBType.MYSQL==DBUtil.getDBType()){
			sql = "select max(cast(substr(INTERFACE_SCRIPT_ID,4,6) as unsigned int)) from interface_script where INTERFACE_CONFIG_ID='" + configId + "'";	//根据数据访问接口方案编码，查询数据访问接口方案脚本编号
		} else if(DBType.ORACLE==DBUtil.getDBType()) {
			sql = "select max(to_number(substr(INTERFACE_SCRIPT_ID,4,6))) from interface_script where INTERFACE_CONFIG_ID='" + configId + "'";	//根据数据访问接口方案编码，查询数据访问接口方案脚本编号
		}
		return "JB_" + getAutoId(sql);																												//返回拼接的方案脚本编码，getAutoId(sql)是一串脚本序号，该序号是该方案下脚本的最大序号
	}
	
	public String getAutoId(String sql) throws RadowException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String id = "";                                  						//存放方案编码序号
		try{
			conn = session.connection();               							//创建数据库连接对象
			st = conn.createStatement();             							//创建声明对象
		    rs = st.executeQuery(sql);                 							//执行SQL语句
		    int id1 = 0;
			if(rs.next())
				id1 = rs.getInt(1);            									//获取查询结果集
			id = String.format("%1$06d", id1 + 1);          					//查询结果加1，并转换格式例如：000012
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();													//关闭结果集
				if(st != null)
					st.close();													//关闭声明
				if(conn != null)
					conn.close();												//关闭数据库连接
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return id;																//返回方案编码序号或者脚本编码序号
	}
	/**
	 * 通过方案和脚本ID得到脚本
	 * @param configId
	 * @param scriptId
	 * @return
	 */
	public InterfaceScript getScriptById(String configId, String scriptId) {
		InterfaceScript interfaceScript = (InterfaceScript)session.createQuery("from InterfaceScript where interfaceConfigId=:interfaceConfigId and interfaceScriptId=:interfaceScriptId").
		setParameter("interfaceConfigId", configId).setParameter("interfaceScriptId", scriptId).uniqueResult();
		return interfaceScript;
	}
	/**
	 * 通过脚本内码删除脚本
	 */
	public void delScript(String scriptIsn) {
		session.createSQLQuery("delete from interface_script where interface_script_isn='"+scriptIsn+"'").executeUpdate();
	}
	
	/**
	 * 根据TaskRegConfigId查询输入参数
	 * @param TaskRegConfigId
	 * @return
	 * @throws RadowException
	 */
	public List doQueryPrama(String interfaceConfigId) throws RadowException{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		String sql = "select INTERFACE_PARAMETER_SEQUENCE,INTERFACE_PARAMETER_NAME,"
						+ "INTERFACE_PARAMETER_DESC,INTERFACE_PARAMETER_TYPE,INTERFACE_CONFIG_ID " 
						+ "from interface_parameter "
						+ "where INTERFACE_CONFIG_ID='" + interfaceConfigId + "' order by INTERFACE_PARAMETER_SEQUENCE ASC";
		try{
			conn = session.connection();
			pst = conn.prepareStatement(sql);
		    rs = pst.executeQuery();
		    ResultSetMetaData rsmd = rs.getMetaData();//
		    int cols = rsmd.getColumnCount();//获取列的数量
		    while(rs.next()){
				HashMap hm = new HashMap();
				for(int i = 1; i <= cols; i++){
					if(i == 1){
						hm.put(rsmd.getColumnName(i).toLowerCase(), rs.getInt(i));
					}else{
						hm.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
					}
				}
				list.add(hm);
		    }
			return list;
		}catch(Exception e){
			throw new RadowException(e.getMessage());
		}finally{
			try{
				if(rs!=null)
					rs.close();						//关闭结果集
				if(pst != null)
					pst.close();					//关闭命令集
				if(conn != null)
					conn.close();					//关闭连接
			}catch(Exception e){
				throw new RadowException(e.getMessage());
			}
		}
	}
	
	public void refreshUsersLst() throws RadowException {
		List<InterfaceUser> list = session.createQuery("from InterfaceUser").list();
		StringBuilder sb = new StringBuilder();
		FileListPathUtil util = new FileListPathUtil();
		String path = null;
		try {
			path = util.getFileListPath();
		} catch (RadowException e) {
			throw new RadowException("找不到users.lst文件路径");
		}
		path = path + "WEB-INF" +File.separator + "users.lst";
		for(int i=0; i<list.size(); i++) {
			InterfaceUser user = list.get(i);
			sb.append(user.getUserName());
			sb.append(" ");
			sb.append(user.getPassword());
			if(i<list.size()-1) {
				sb.append("\r\n");
			}
		}
		rewriteFile(path,sb.toString());
	}
	
	/**
	 * 重写user.lst文件
	 * @param fileName
	 * @param content
	 */
	public void rewriteFile(String fileName, String content) {
		try {
			File file = new File(fileName);
			FileOutputStream fs = new FileOutputStream(file);
			byte[] contents = content.getBytes();
			fs.write(contents);
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
