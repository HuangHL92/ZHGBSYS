package com.insigma.siis.local.epsoft.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

/**
 * 全局变量类
 * @author gezh
 *
 */
public class AppConfig {
     
     /**
      * 当前部署行政区的代码
      */
	 public static String AREA_ID ;

	 /**
      * 当前部署行政区的代码
      */
	 public static String AREA_NAME ;
	 
	 /**
	  * FTP传输服务器服务IP
	  */
	 public static String TRANS_SERVER_HOST;
	 
	 /**
	  * FTP传输服务器端口
	  */
	 public static int TRANS_SERVER_PORT = 15321; 
	 
	 /**
	  * FTP传输服务器基础目录
	  */
	 public static String TRANS_SERVER_BASEURL="G:/Tftp"; 
	 
	 /**
	  * FTP上传下载目录集合
	  */
	 public static List<String> FtpPathList= new ArrayList<String>();
	 
	 /**
	  * 本地数据存放根目录
	  */
	 public static String LOCAL_FILE_BASEURL="G:/LocalFileStore";
	 
	 /**
	  * 本地备份文件根目录
	  */
	 public static String LOCAL_BACKUP_FILE = "D:/FileBackup";
	 
	 /**
	  * 运行7z压缩命令路径
	  */
	 public static String CMD_7Z_EXE = "C:\\Program Files (x86)\\7-Zip\\7z.exe";
	 
	 /**
	  * 运行任免表编辑器路径
	  */
	 public static String CMD_ZZBRMBService_EXE = "C:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe";
	 
	 /**
	  * 轮询下载上级下发文件首次开始时间(格式:HH:mm:ss)
	  */
	 public static String HLPOLING_FIRSTTIME;
	 
	 /**
	  * 轮询下载上级下发文件间隔时间(毫秒)
	  */
	 public static Long HLPOLING_PERIOD;
	 
	 /**
	  * 是否开启轮询上级下发目录 1为开启，0为不开启
	  */
	 public static String IS_HLPOLING = "1";
	 
	 /**
	  * 数据源DataSource
	  */
	 public static DataSource DS;
	 
	 /**
	  * zzb3上传路径
	  */
	 public static String ZZB3_PATH = "D:/KingbsData";
	 
	 /**
	  * 公务员及压缩密码
	  */
	 public static String PWD_GWY = "123456";
	 
	 /**
	  * 金仓数据库用户名
	  */
	 public static String KING_BD_USER = "SYSTEM";
	 
	 /**
	  * 金仓数据库数据库
	  */
	 public static String KING_BD = "SAMPLES";
	 
	 /**
	  * 金仓数据库用户密码
	  */
	 public static String KING_BD_PWD = "1234";
	 
	 /**
	  * 金仓数据库端口
	  */
	 public static String KING_BD_PORT = "54321";
	 
	 /**
	  * 金仓数据库服务器IP
	  */
	 public static String KING_BD_SERVER = "127.0.0.1";
	 
	 /**
	  * 图片目录
	  */
	 public static String PHOTO_PATH = "D:/hzbPhotos/";
	 
	 /**
	  * hzb主目录
	  */
	 public static String HZB_PATH = "D:/HZB/";
	 
	 /**
	  * 日志输出控制
	  */
	 public static String LOG_CONTROL;
	 
	 /**
	  * 是否第一次系统加载数据
	  */
	 public static String IS_FIRST_LOAD;
	 
	 /**
	  * zxf
	  * 查询线程数
	  */
	 public static Integer QUERY_THREAD_NUM ;
	 
	 /**
	  * zxf
	  * 分区段数
	  */
	 public static Integer PARTITION_FRAGMENT ;
	 
	 /**
	  * 干部监督网络
	  */
	 public static String GBJDWLQH ;
	 
	 public static String GBJD_ADDR = "127.0.0.1:8081/gbdjd";
}
