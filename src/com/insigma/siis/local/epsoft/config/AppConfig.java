package com.insigma.siis.local.epsoft.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

/**
 * ȫ�ֱ�����
 * @author gezh
 *
 */
public class AppConfig {
     
     /**
      * ��ǰ�����������Ĵ���
      */
	 public static String AREA_ID ;

	 /**
      * ��ǰ�����������Ĵ���
      */
	 public static String AREA_NAME ;
	 
	 /**
	  * FTP�������������IP
	  */
	 public static String TRANS_SERVER_HOST;
	 
	 /**
	  * FTP����������˿�
	  */
	 public static int TRANS_SERVER_PORT = 15321; 
	 
	 /**
	  * FTP�������������Ŀ¼
	  */
	 public static String TRANS_SERVER_BASEURL="G:/Tftp"; 
	 
	 /**
	  * FTP�ϴ�����Ŀ¼����
	  */
	 public static List<String> FtpPathList= new ArrayList<String>();
	 
	 /**
	  * �������ݴ�Ÿ�Ŀ¼
	  */
	 public static String LOCAL_FILE_BASEURL="G:/LocalFileStore";
	 
	 /**
	  * ���ر����ļ���Ŀ¼
	  */
	 public static String LOCAL_BACKUP_FILE = "D:/FileBackup";
	 
	 /**
	  * ����7zѹ������·��
	  */
	 public static String CMD_7Z_EXE = "C:\\Program Files (x86)\\7-Zip\\7z.exe";
	 
	 /**
	  * ���������༭��·��
	  */
	 public static String CMD_ZZBRMBService_EXE = "C:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe";
	 
	 /**
	  * ��ѯ�����ϼ��·��ļ��״ο�ʼʱ��(��ʽ:HH:mm:ss)
	  */
	 public static String HLPOLING_FIRSTTIME;
	 
	 /**
	  * ��ѯ�����ϼ��·��ļ����ʱ��(����)
	  */
	 public static Long HLPOLING_PERIOD;
	 
	 /**
	  * �Ƿ�����ѯ�ϼ��·�Ŀ¼ 1Ϊ������0Ϊ������
	  */
	 public static String IS_HLPOLING = "1";
	 
	 /**
	  * ����ԴDataSource
	  */
	 public static DataSource DS;
	 
	 /**
	  * zzb3�ϴ�·��
	  */
	 public static String ZZB3_PATH = "D:/KingbsData";
	 
	 /**
	  * ����Ա��ѹ������
	  */
	 public static String PWD_GWY = "123456";
	 
	 /**
	  * ������ݿ��û���
	  */
	 public static String KING_BD_USER = "SYSTEM";
	 
	 /**
	  * ������ݿ����ݿ�
	  */
	 public static String KING_BD = "SAMPLES";
	 
	 /**
	  * ������ݿ��û�����
	  */
	 public static String KING_BD_PWD = "1234";
	 
	 /**
	  * ������ݿ�˿�
	  */
	 public static String KING_BD_PORT = "54321";
	 
	 /**
	  * ������ݿ������IP
	  */
	 public static String KING_BD_SERVER = "127.0.0.1";
	 
	 /**
	  * ͼƬĿ¼
	  */
	 public static String PHOTO_PATH = "D:/hzbPhotos/";
	 
	 /**
	  * hzb��Ŀ¼
	  */
	 public static String HZB_PATH = "D:/HZB/";
	 
	 /**
	  * ��־�������
	  */
	 public static String LOG_CONTROL;
	 
	 /**
	  * �Ƿ��һ��ϵͳ��������
	  */
	 public static String IS_FIRST_LOAD;
	 
	 /**
	  * zxf
	  * ��ѯ�߳���
	  */
	 public static Integer QUERY_THREAD_NUM ;
	 
	 /**
	  * zxf
	  * ��������
	  */
	 public static Integer PARTITION_FRAGMENT ;
	 
	 /**
	  * �ɲ��ල����
	  */
	 public static String GBJDWLQH ;
	 
	 public static String GBJD_ADDR = "127.0.0.1:8081/gbdjd";
}
