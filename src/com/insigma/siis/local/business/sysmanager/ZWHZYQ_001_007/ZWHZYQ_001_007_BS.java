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
	
	HBSession session = HBUtil.getHBSession();						//�������ݿ�Ự����
	
	public HBSession getSession() {
		return session;
	}

	public void setSession(HBSession session) {
		this.session = session;
	}

	/**
	 * @return ��ǰ�ӿڷ��������������
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
	 * @return ��ǰ�ӿڷ��������������
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
	 * ͨ����½���Ʋ�ѯ���û���
	 * @param  loginName  �û���½��
	 * @return username   �û�����
	 * @throws RadowException
	 */
	public String getUserName(String loginName) throws RadowException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String username = "";
		String sql = "select USERNAME from smt_user where LOGINNAME='" + loginName + "'";	//�����û���¼������ѯ�û�����
		try{
			conn = session.connection();													//�������ݿ�����
			st = conn.createStatement();													//��������
			rs = st.executeQuery(sql);														//ִ�в�ѯ���
			if(rs.next())
				username = rs.getString(1);													//��ȡ��ѯ�����û���
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();																//�رս����
				if(st != null)
					st.close();																//�ر�����
				if(conn != null)
					conn.close();                                                       	//�ر����ݿ�����
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return username;																	//���ز�ѯ�����û�����
	}
	/**
	 * ��ȡת��������ڸ�ʽ�ַ���
	 * @param date
	 * @return
	 * @throws RadowException
	 */
	public String formatDate(Date date) throws RadowException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");						//�������ڸ�ʽΪyyyy-MM-dd
		return sdf.format(date);                                  						//������yyyy��MM-dd��ʽת�����ַ�����ʽ
	}
	
	/**
	 * ����configId��ȡInterfaceConfig����
	 * @param configId
	 * @return
	 */
	public InterfaceConfig getConfigById(String configId){
		InterfaceConfig config = (InterfaceConfig)session.createQuery("from InterfaceConfig where interfaceConfigId=:interfaceConfigId")
		.setParameter("interfaceConfigId", configId).list().get(0);	                            //��hibernate���������ݷ��������ȡ��������
		return config;																			//���ػ�ȡ���ݷ��ʽӿڷ�����������
	}
	
	/**
	 * �������ݷ��ʽӿ����÷��������ȡInterfaceConfig����
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
		return config;																			//���ػ�ȡ���ݷ��ʽӿڷ�����������
	}
	
	/**
	 * ���ַ������͵�����ת��ΪDate����
	 * @param str
	 * @return
	 * @throws RadowException
	 */
	public Date parseDateStr(String str) throws RadowException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");						//�������ڸ�ʽ
		Date date = null;																//�������ڶ���
		try{
			date = sdf.parse(str);														//�������ַ���ת����yyyy��MM-dd��ʽ
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;																	//����ת���������
	}
	
	/**
	 * ��ѯ��
	 * @param node
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceConfig> getInterfaceConfigTree() throws RadowException{
		List<InterfaceConfig> list = new ArrayList<InterfaceConfig>();		//����һ��list���ϣ����ڴ�Žӿ����
		list = session.createQuery("from InterfaceConfig order by interfaceConfigSequence").list();
		return list;
	}
	
	/**
	 * ��ѯĳ��������������нӿڽű�
	 * @param interfaceConfigId  �ӿڷ���ID
	 * @return ĳ�������������еĽӿڽű�
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceScript> getInterfaceScripts(String interfaceConfigId) {
		List<InterfaceScript> list = session.createQuery("from InterfaceScript where interfaceConfigId=:interfaceConfigId order by interfaceScriptSequence")
		.setParameter("interfaceConfigId", interfaceConfigId).list();
		return list;
	}
	/**
	 * ����interfaceConfigId��ѯ�������
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
		    int cols = rsmd.getColumnCount();//��ȡ�е�����
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
					rs.close();						//�رս����
				if(pst != null)
					pst.close();					//�ر����
				if(conn != null)
					conn.close();					//�ر�����
			}catch(Exception e){
				throw new RadowException(e.getMessage());
			}
		}
	}
	
	/**
	 * ɾ����������Ĳ���
	 * @param configId
	 * @param paramName
	 */
	public void delConfigParam(String configId, String paramName){
		String sql = "delete from interface_parameter where INTERFACE_CONFIG_ID='" + configId + "' and INTERFACE_PARAMETER_NAME='" + paramName + "'";//�����������ݷ��ʽӿڷ������롢�������룬ɾ����Ӧ�����ݷ��ʽӿڷ�����Ӧ�ķ�������������Ϣ
		session.createSQLQuery(sql).executeUpdate();                                                               //ִ��ɾ����䣬�Զ��ύ
	}
	/**
	 * ����configId��ѯ�����������еĽű�
	 * @param configId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceScript> getScripts(String configId) {
		String hql = "from InterfaceScript where id.interfaceConfigId=:configId order by interfaceScriptSequence";	//�������ݷ��ʽӿڷ������룬��ȡ��Ӧ���ݷ��ʽӿڷ����ű���Ϣ
		List<InterfaceScript> scripts = session.createQuery(hql).setParameter("configId", configId).list();			//ִ��hql��䣬���÷�������
		return scripts;																								//���ط����ű��б����
	}
	/**
	 * ����configIdɾ���ӿڷ���
	 * @param configId
	 */
	public void delConfig(String configId){
		String sql = "delete from interface_config where INTERFACE_CONFIG_ID='" + configId + "'";	//�����������ݷ��ʽӿڷ������룬ɾ����Ӧ�����ݷ��ʽӿڷ�����Ϣ�����Ӧ�ķ�������������Ϣ
		session.createSQLQuery(sql).executeUpdate();                                   				//ִ��ɾ�����
	}
	/**
	 * ���ݷ���idɾ�������Ĳ���
	 * @param configId
	 */
	public void delConfigParameters(String configId){
		String sql = "delete from interface_parameter where INTERFACE_CONFIG_ID='" + configId + "'";	//�����������ݷ��ʽӿڷ������룬ɾ����Ӧ�����ݷ��ʽӿڷ�����Ӧ�ķ�������������Ϣ
		session.createSQLQuery(sql).executeUpdate();													//ִ��ɾ�����
	}
	/**
	 * ͨ���ű������ѯ�ű�
	 * @param scriptIsn
	 * @return
	 */
	public InterfaceScript getScriptByIsn(String scriptIsn){
		String hql = "From InterfaceScript where interfaceScriptIsn='" + scriptIsn + "'";	//�������ݷ��ʽӿڷ������룬��ȡ���ݷ��ʽӿڷ����ű���������
		InterfaceScript script = (InterfaceScript)session.createQuery(hql).uniqueResult();	//ִ��hibernate��䷵�����ݷ��ʽӿڷ����ű�����
		return script;																		//�������ݷ��ʽӿڷ����ű���������
	}
	
	/**
	 * �������ɽű�Id��
	 * @param configId
	 * @return
	 * @throws RadowException
	 */
	public String getAutoScriptId(String configId) throws RadowException{
		String sql = null;
		if(DBType.MYSQL==DBUtil.getDBType()){
			sql = "select max(cast(substr(INTERFACE_SCRIPT_ID,4,6) as unsigned int)) from interface_script where INTERFACE_CONFIG_ID='" + configId + "'";	//�������ݷ��ʽӿڷ������룬��ѯ���ݷ��ʽӿڷ����ű����
		} else if(DBType.ORACLE==DBUtil.getDBType()) {
			sql = "select max(to_number(substr(INTERFACE_SCRIPT_ID,4,6))) from interface_script where INTERFACE_CONFIG_ID='" + configId + "'";	//�������ݷ��ʽӿڷ������룬��ѯ���ݷ��ʽӿڷ����ű����
		}
		return "JB_" + getAutoId(sql);																												//����ƴ�ӵķ����ű����룬getAutoId(sql)��һ���ű���ţ�������Ǹ÷����½ű���������
	}
	
	public String getAutoId(String sql) throws RadowException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String id = "";                                  						//��ŷ����������
		try{
			conn = session.connection();               							//�������ݿ����Ӷ���
			st = conn.createStatement();             							//������������
		    rs = st.executeQuery(sql);                 							//ִ��SQL���
		    int id1 = 0;
			if(rs.next())
				id1 = rs.getInt(1);            									//��ȡ��ѯ�����
			id = String.format("%1$06d", id1 + 1);          					//��ѯ�����1����ת����ʽ���磺000012
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)
					rs.close();													//�رս����
				if(st != null)
					st.close();													//�ر�����
				if(conn != null)
					conn.close();												//�ر����ݿ�����
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return id;																//���ط���������Ż��߽ű��������
	}
	/**
	 * ͨ�������ͽű�ID�õ��ű�
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
	 * ͨ���ű�����ɾ���ű�
	 */
	public void delScript(String scriptIsn) {
		session.createSQLQuery("delete from interface_script where interface_script_isn='"+scriptIsn+"'").executeUpdate();
	}
	
	/**
	 * ����TaskRegConfigId��ѯ�������
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
		    int cols = rsmd.getColumnCount();//��ȡ�е�����
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
					rs.close();						//�رս����
				if(pst != null)
					pst.close();					//�ر����
				if(conn != null)
					conn.close();					//�ر�����
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
			throw new RadowException("�Ҳ���users.lst�ļ�·��");
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
	 * ��дuser.lst�ļ�
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
