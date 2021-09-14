package com.insigma.siis.local.jftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.DbUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.log4j.Logger;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;


/**
 * Ftp�������˹�����
 * @author gezh
 *
 */
public class JFtpServer {
	
	private static Logger log = Logger.getLogger(JFtpServer.class);
	
	/**
	 * ��̬��Ftp�������
	 */
	public static FtpServer ftpServer= null;
	
	public static final String INSERT_SQL = "INSERT INTO FTP_USER (userid, userpassword,homedirectory, enableflag, writepermission, idletime, uploadrate,downloadrate) VALUES ('{userid}', '{userpassword}', '{homedirectory}','{enableflag}', '{writepermission}', {idletime}, {uploadrate},{downloadrate})";
	public static final String SELECT_SQL = "SELECT userid, userpassword, homedirectory, enableflag, writepermission, idletime, maxloginnumber,maxloginperip,uploadrate, downloadrate  FROM FTP_USER WHERE userid = '{userid}'";
	public static final String UPDATE_SQL = "UPDATE FTP_USER SET userpassword='{userpassword}',homedirectory='{homedirectory}',enableflag='{enableflag}',writepermission='{writepermission}',idletime={idletime},uploadrate={uploadrate},downloadrate={downloadrate} WHERE userid='{userid}'";
	public static final String AUTHENTICATE_SQL = "SELECT userpassword from FTP_USER WHERE userid='{userid}'";
	public static final String DELETE_SQL = "DELETE FROM FTP_USER WHERE userid = '{userid}'";
	public static final String SELECTALL_SQL = "SELECT userid FROM FTP_USER ORDER BY userid";
	public static final String ADMIN_SQL = "SELECT userid FROM FTP_USER WHERE userid='{userid}' AND userid='admin'";
	
	/**
	 * ��ȡ�û�������
	 * @return
	 * @throws FtpException
	 */
	private static UserManager getUserManager() throws FtpException{
		UserManager usermanager = null;
		try{
			DbUserManagerFactory dbUserFactory = new DbUserManagerFactory();
			dbUserFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());  
			dbUserFactory.setDataSource(AppConfig.DS);
			dbUserFactory.setSqlUserInsert(INSERT_SQL);
			dbUserFactory.setSqlUserSelect(SELECT_SQL);
			dbUserFactory.setSqlUserUpdate(UPDATE_SQL);
			dbUserFactory.setSqlUserAuthenticate(AUTHENTICATE_SQL);
			dbUserFactory.setSqlUserDelete(DELETE_SQL);
			dbUserFactory.setSqlUserSelectAll(SELECTALL_SQL);
			dbUserFactory.setSqlUserAdmin(ADMIN_SQL);
			usermanager = dbUserFactory.createUserManager();
		}catch(Exception e){
			log.error("��ȡFTP�û�������ʧ��",e);
			throw new FtpException("��ȡFTP�û�������ʧ��:"+e.getMessage());
		}
	    return usermanager;
	}
	
	/**
	 * ��ʼ��Ftp����
	 * @return
	 * @throws FtpException 
	 */
	public static FtpServer initFtpServer() throws FtpException{
		try{
			FtpServerFactory serverFactory = new FtpServerFactory();
			ListenerFactory factory = new ListenerFactory();
			
			HBSession sess = HBUtil.getHBSession();
			String sql = "select AAA005 from AA01 where  AAA001 ='TRANS_SERVER_PORT'";
			Object port = sess.createSQLQuery(sql).uniqueResult();
			
			// ���ü����˿�
			if(port == null || port.equals("")){
				factory.setPort(AppConfig.TRANS_SERVER_PORT);
			}else{
				factory.setPort(Integer.valueOf(port.toString()));
			}
			
			
			//���÷����ַ
//			factory.setServerAddress(AppConfig.TRANS_SERVER_HOST);
			//Ĭ�ϴ���һ��default�ļ����˿ڣ�����������ַ�ʽ�滻Ĭ�ϵļ����˿�
			serverFactory.addListener("default", factory.createListener());
			//FTP״̬���
			Map<String, Ftplet> ftplets= new HashMap<String, Ftplet>();
			ftplets.put("ftplet", new JFtplet());
			serverFactory.setFtplets(ftplets);
			//����FTP�û�
			UserManager usermanager = getUserManager();
			serverFactory.setUserManager(usermanager);
			// ��������
			ftpServer = serverFactory.createServer();
		}catch(Exception e){
			log.error("��ʼ��FTP������ʧ��",e);
			throw new FtpException("��ʼ��FTP������ʧ��:"+e.getMessage());
		}
		return ftpServer;
	}
	
	/**
	 * ��ʼ���û�ȫ��Ŀ¼�ṹ
	 * @param userName
	 * @return �����û��ĸ�Ŀ¼
	 * @throws FtpException
	 */
	private static String createUserFolder(String userName) throws FtpException{
		try{
			StringBuffer baseUrl = new StringBuffer();
			baseUrl.append(FileUtil.resolveLeftPath(AppConfig.TRANS_SERVER_BASEURL));
			baseUrl.append(FileUtil.resolveLeftPath(userName));
			FileUtil.createFolder(baseUrl.toString());
			String lastPath = null;
			for (String spath : AppConfig.FtpPathList) {
				lastPath = baseUrl.toString() + spath;
				FileUtil.createFolder(lastPath);
			}
			return baseUrl.toString();
		}catch(Exception e){
			log.error("�����û�FTPĿ¼ʧ��",e);
			throw new FtpException("�����û�FTPĿ¼ʧ��",e);
		}
	}
	
	/**
	 * �жϵ�ǰFTP�������Ƿ����û�������
	 * @return
	 */
	public static boolean existsUserConnected(){
		if(JFtplet.fsList.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * ����FTP�û�
	 * �����û���������FTP�������ȼ�⵱ǰ�������Ƿ����û�������
	 * @param userName
	 * @param password
	 * @throws FtpException 
	 */
	public static void addUser(String userName,String passWord,boolean eableld,boolean writePermission,Long idletime) throws FtpException{
		String homeDir = null;
		 try {
			homeDir = createUserFolder(userName.toUpperCase());//�����û��̶���FTP�ļ�Ŀ¼
		    UserManager usermanager = getUserManager();
		    BaseUser user = new BaseUser();  
		    user.setHomeDirectory(homeDir);  
		    user.setName(userName);  
		    user.setPassword(passWord);
		    user.setEnabled(eableld);
		    user.setMaxIdleTime(idletime.intValue());
		    if(writePermission){
			    List<Authority> authorities = new ArrayList<Authority>();
			    authorities.add(new WritePermission());
			    user.setAuthorities(authorities);
		    }
			usermanager.save(user);//user ������ʵ��User�ӿڵ�ʵ����  
		} catch (FtpException e) {
			log.error("����FTP�û���"+userName+"��ʧ��",e);
			throw new FtpException("����FTP�û���"+userName+"��ʧ��:"+e.getMessage());
		}
	}
	
	/** 
	 * ɾ���û� 
	 * @param username 
	 * @throws FtpException  
	 */  
	public static void deletUser(String userName) throws FtpException{  
		try{
		    UserManager usermanager = getUserManager();  
		    usermanager.delete(userName);
		    StringBuffer baseUrl = new StringBuffer();
			baseUrl.append(FileUtil.resolveLeftPath(AppConfig.TRANS_SERVER_BASEURL));
			baseUrl.append(FileUtil.resolveLeftPath(userName.toUpperCase()));
			FileUtil.delAllFile(baseUrl.toString());
		}catch(Exception e){
			log.error("ɾ��FTP�û���"+userName+"��ʧ��",e);
			throw new FtpException("ɾ��FTP�û���"+userName+"��ʧ��",e);
		}
	}  
	
}
