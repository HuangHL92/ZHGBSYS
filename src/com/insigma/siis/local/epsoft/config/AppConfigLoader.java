package com.insigma.siis.local.epsoft.config;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.entity.Aa01;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;
import com.insigma.siis.local.pagemodel.cbdHandler.GetCBDFilePageModel;

/**
 * ȫ�ֱ���������
 * 
 * @author gezh
 * 
 */

public class AppConfigLoader {
	private static Log log = LogFactory.getLog(AppConfigLoader.class);
	private static String setupPath ;
	
	/**
	 * 
	 * @param configMap
	 */
	private static void loadConfig(Map configMap) {
		setupPath = getSetupPath();
		AppConfig.IS_FIRST_LOAD = (String) configMap.get("IS_FIRST_LOAD");
		AppConfig.AREA_ID = (String) configMap.get("AREA_ID"); // ��ǰ�����������Ĵ���
		AppConfig.AREA_NAME = (String) configMap.get("AREA_NAME"); // ��ǰ��������������
		AppConfig.TRANS_SERVER_HOST=(String)configMap.get("TRANS_SERVER_HOST");//FTP���������ip
		try {
			AppConfig.TRANS_SERVER_PORT = Integer.valueOf((String) configMap.get("TRANS_SERVER_PORT")).intValue(); // FTP����������˿�
		} catch (RuntimeException e) {
			AppConfig.TRANS_SERVER_PORT = 21;
		}
		AppConfig.TRANS_SERVER_BASEURL= ((String)configMap.get("TRANS_SERVER_BASEURL")).toUpperCase();//ϵͳFTP�����Ŀ¼
		
		AppConfig.LOCAL_FILE_BASEURL= ((String)configMap.get("LOCAL_FILE_BASEURL")).toUpperCase();//�������ݴ�Ÿ�Ŀ¼
		AppConfig.LOCAL_BACKUP_FILE = ((String)configMap.get("LOCAL_BACKUP_FILE")).toUpperCase();//���ر����ļ���Ŀ¼
		AppConfig.CMD_7Z_EXE = (String)configMap.get("CMD_7Z_EXE");//����7zѹ������·��
		AppConfig.CMD_ZZBRMBService_EXE = (String)configMap.get("CMD_ZZBRMBService_EXE");//���������༭������·��
		AppConfig.HLPOLING_FIRSTTIME = (String)configMap.get("HLPOLING_FIRSTTIME");//��ѯ�����ϼ��·��ļ��״ο�ʼʱ��(��ʽ:HH:mm:ss)
		AppConfig.HLPOLING_PERIOD = Long.valueOf((String)configMap.get("HLPOLING_PERIOD"));// ��ѯ�����ϼ��·��ļ����ʱ��(����)
		AppConfig.IS_HLPOLING = (String)configMap.get("IS_HLPOLING");// �Ƿ�����ѯ�ϼ��·�Ŀ¼ 1Ϊ������0Ϊ������
		//zzb3ʹ������
		AppConfig.KING_BD = (String)configMap.get("KING_BD");// 
		AppConfig.KING_BD_USER = (String)configMap.get("KING_BD_USER");// 
		AppConfig.KING_BD_PWD = (String)configMap.get("KING_BD_PWD");// 
		AppConfig.KING_BD_PORT = (String)configMap.get("KING_BD_PORT");// 
		AppConfig.KING_BD_SERVER = (String)configMap.get("KING_BD_SERVER");// 
		AppConfig.ZZB3_PATH = (String)configMap.get("ZZB3_PATH");// 
		AppConfig.PHOTO_PATH = getPhoto_path(configMap);
		AppConfig.HZB_PATH = (String) configMap.get("HZB_PATH"); // ��ǰ�����������Ĵ���
		AppConfig.QUERY_THREAD_NUM = Integer.valueOf((String) configMap.get("QUERY_THREAD_NUM")); 
		AppConfig.PARTITION_FRAGMENT = Integer.valueOf((String) configMap.get("PARTITION_FRAGMENT")); 
		AppConfig.LOG_CONTROL = ((String) configMap.get("LOGOUT_ISUSEFUL")).toUpperCase(); //log��־����
		AppConfig.GBJDWLQH = ((String) configMap.get("GBJDWLQH")).toUpperCase(); //�����л�
		AppConfig.GBJD_ADDR = ((String) configMap.get("GBJD_ADDR")); //�ɼ�ip
		setDataSource();//��������Դ
		initPath();//��ʼ��ͼƬ·��
		setFtpPathList();//��ʼ��FTP�û�Ŀ¼��
		
	}
	

	public static String getSetupPath() {
		String classPath = SevenZipUtil.class.getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
		//rootPath = rootPath.replace("/", "\\"); 
		File file = new File(rootPath);
		String setup = null;
		try {
			setup = file.getParentFile().getParentFile().getParent();
		} catch (Exception e) {
			setup = rootPath.substring(0, rootPath.indexOf(File.separator));
			e.printStackTrace();
		}
		
		return setup.replace("\\", "/");
	}


	private static String getPhoto_path(Map configMap) {
		String photo_path = ((String)configMap.get("PHOTO_PATH")).toUpperCase();
		String photo_path_sub = photo_path.substring(0, photo_path.indexOf("/"));
		File file = new File(photo_path_sub);
		if(AppConfig.IS_FIRST_LOAD.equals("1") && DBUtil.getDBType().equals(DBType.MYSQL)){
			photo_path = setupPath + "/" + "HZBPHOTOS";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+photo_path+"' where aaa001='PHOTO_PATH'").executeUpdate();
		} else if(!file.isDirectory()){
			String root_path = PhotosUtil.getRootPath();
			String root_path_sub = root_path.substring(0, root_path.indexOf(File.separator));
			photo_path = root_path_sub + "/" + "HZBPHOTOS";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+photo_path+"' where aaa001='PHOTO_PATH'").executeUpdate();
		}
		AppConfig.PHOTO_PATH = photo_path;
		return photo_path;
	}


	/**
	 * �����ݿ��ж�ȡȫ�ֱ���
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		HBSession sess = HBUtil.getHBSession();
		try {
			List<Aa01> aa01list = sess.createQuery("from Aa01").list();
			HashMap<String,Object> hm = new HashMap<String,Object>();
			for (Aa01 aa01 : aa01list) {
				hm.put(aa01.getAaa001(), aa01.getAaa005());
			}
			loadConfig(hm);
		} catch (Exception e) {
			log.error("��ʼ��ȫ�ֱ���ʧ��",e);
		} 

	}
	
	/**
	 * ��ʼ��FTP�û�Ŀ¼��
	 */
	public static void setFtpPathList(){
		AppConfig.FtpPathList.add(ZwhzFtpPath.HZB_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.HZB_DOWN);
		AppConfig.FtpPathList.add(ZwhzFtpPath.OTHER_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.OTHER_DOWN);
		AppConfig.FtpPathList.add(ZwhzFtpPath.ZB_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.ZB_DOWN);
		AppConfig.FtpPathList.add(ZwhzFtpPath.DM_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.DM_DOWN);
		AppConfig.FtpPathList.add(ZwhzFtpPath.FK_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.FK_DOWN);
		AppConfig.FtpPathList.add(ZwhzFtpPath.JC_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.JC_DOWN);
		AppConfig.FtpPathList.add(ZwhzFtpPath.CBD_UP);
		AppConfig.FtpPathList.add(ZwhzFtpPath.CBD_DOWN);
	}
	
	/**
	 * ��ʼ������ԴDS
	 */
	public static void setDataSource(){
		try{
			Context	env = (Context) new InitialContext().lookup("java:comp/env");
			AppConfig.DS = (DataSource) env.lookup("jdbc/insiis");
		}catch(Exception e){
			log.error("��������Դ�쳣",e);
		}
	}
	
	/**
	 * ��ʼ��FTP�û�Ŀ¼��
	 */
	public static void initPath(){
		//hzb ��Ŀ¼
		String hzbPath = getSetuporConfPath();
		File hzbhostfile = new File(hzbPath);
		if(!hzbhostfile.exists()){
			hzbhostfile.mkdirs();
		}
		File uploadfile = new File(hzbPath+"/temp/upload");
		if(!uploadfile.exists()){
			uploadfile.mkdirs();
		}
		File ziploadfile = new File(hzbPath+"/temp/zipload");
		if(!ziploadfile.exists()){
			ziploadfile.mkdirs();
		}
		//ftp �ϱ�����Ŀ¼
		File ffile = new File(checkTSB(hzbPath));
		if(!ffile.exists()){
			ffile.mkdirs();
		}
		//ftp ��������Ŀ¼
		File file1 = new File(checkLFB(hzbPath));
		if(!file1.exists()){
			file1.mkdirs();
		}
		//ftp ���ر���Ŀ¼
		File file2 = new File(checkLBF(hzbPath));
		if(!file2.exists()){
			file2.mkdirs();
		}
		String last = AppConfig.PHOTO_PATH.substring(AppConfig.PHOTO_PATH.length()-1);
		String PHOTO_PATH = AppConfig.PHOTO_PATH + ((last.equals("/")||last.equals("\\")) ? "" :"/");
		GlobalNames.sysConfig.put("PHOTO_PATH", PHOTO_PATH);
		PhotosUtil.PHOTO_PATH = PHOTO_PATH;
		String[] keys= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};	
//		List<String> list = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys.length; j++) {
				String url = "/" + keys[i] + "/" +keys[j] +"/";
				File file = new File(PHOTO_PATH+url);
				if(!file.exists()){
					file.mkdirs();
				}
			}
		}
		if(AppConfig.IS_FIRST_LOAD.equals("1")){
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='0' where aaa001='IS_FIRST_LOAD'").executeUpdate();
		}
	}
	
	
	private static String checkLBF(String hzbPath) {
		String path = (AppConfig.LOCAL_BACKUP_FILE).toUpperCase();
		String path_sub = path.substring(0, path.indexOf("/"));
		File file = new File(path_sub);
		if(AppConfig.IS_FIRST_LOAD.equals("1") && DBUtil.getDBType().equals(DBType.MYSQL)){
			path = setupPath + "/HZB/FTP/BACKUP";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='LOCAL_BACKUP_FILE'").executeUpdate();
		} else if(!file.isDirectory()){
			path = hzbPath + "/FTP/BACKUP";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='LOCAL_BACKUP_FILE'").executeUpdate();
		}
		AppConfig.LOCAL_BACKUP_FILE = path;
		return path;
	}
	
	private static String checkTSB(String hzbPath) {
		String path = (AppConfig.TRANS_SERVER_BASEURL).toUpperCase();
		String path_sub = path.substring(0, path.indexOf("/"));
		File file = new File(path_sub);
		if(AppConfig.IS_FIRST_LOAD.equals("1") && DBUtil.getDBType().equals(DBType.MYSQL)){
			path = setupPath + "/HZB/FTP/FTPHOST";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='TRANS_SERVER_BASEURL'").executeUpdate();
		} else if(!file.isDirectory()){
			path = hzbPath + "/FTP/FTPHOST";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='TRANS_SERVER_BASEURL'").executeUpdate();
		}
		AppConfig.TRANS_SERVER_BASEURL = path;
		return path;
	}
	
	private static String checkLFB(String hzbPath) {
		String path = (AppConfig.LOCAL_FILE_BASEURL).toUpperCase();
		String path_sub = path.substring(0, path.indexOf("/"));
		File file = new File(path_sub);
		if(AppConfig.IS_FIRST_LOAD.equals("1") && DBUtil.getDBType().equals(DBType.MYSQL)){
			path = setupPath + "/HZB/FTP/LOCALFILE";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='LOCAL_FILE_BASEURL'").executeUpdate();
		} else if(!file.isDirectory()){
			path = hzbPath + "/FTP/LOCALFILE";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='LOCAL_FILE_BASEURL'").executeUpdate();
		}
		AppConfig.LOCAL_FILE_BASEURL = path;
		return path;
	}


	private static String getSetuporConfPath() {
		String path = (AppConfig.HZB_PATH).toUpperCase();
		String path_sub = path.substring(0, path.indexOf("/"));
		File file = new File(path_sub);
		if(AppConfig.IS_FIRST_LOAD.equals("1") && DBUtil.getDBType().equals(DBType.MYSQL)){
			path = setupPath + "/" + "HZB";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='HZB_PATH'").executeUpdate();
		}else if(!file.isDirectory()){
			String root_path = PhotosUtil.getRootPath();
			String root_path_sub = root_path.substring(0, root_path.indexOf(File.separator));
			path = root_path_sub + "/" + "HZB";
			HBUtil.getHBSession().createSQLQuery("update aa01 set aaa005='"+path+"' where aaa001='HZB_PATH'").executeUpdate();
		}
		AppConfig.HZB_PATH = path;
		return path;
	}
}
