package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.publicServantManage.DeletePersonTimer;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class ImpMysqlThread implements Runnable {
	
	private String mysqlZipPass;
	private String contact;
	private String tel;
	private String notes;
	private String id;
	private CurrentUser user;
	private UserVO userVo;
    public ImpMysqlThread(String mysqlZipPass,String contact,String tel,String notes,String id,UserVO userVo,CurrentUser user) {
        this.mysqlZipPass = mysqlZipPass;
        this.contact = contact;
        this.tel = tel;
        this.notes = notes;
        this.id = id;
        this.user = user;
        this.userVo = userVo;
    }
    
	@Override
	public void run() {
		String process_run = "1";
		try {
			KingbsconfigBS.saveImpDetail(process_run,"1","正在清理临时数据",id);						//记录校验过程
			DeletePersonTimer.exec();
			
			KingbsconfigBS.saveImpDetail(process_run,"1","正在进行环境监测",id);						//记录校验过程
			//检测 服务名、端口号 是否被占用（后面再添加此功能）
			
			KingbsconfigBS.saveImpDetail(process_run,"2","完成",id);						//记录校验过程
			
			process_run = "2";
			KingbsconfigBS.saveImpDetail(process_run,"1","正在准备MYSQL数据库",id);
			//1、将MYSQL复制到指定的路径（DATAS文件夹下），并解压缩MYSQL库
			mysqlZipPass = mysqlZipPass + "/MYSQL数据库上报"+System.currentTimeMillis();
			String mysqlPass = mysqlZipPass + "/Datas";
			File mysqlAdress = new File(mysqlPass);
			if(!mysqlAdress.exists() && !mysqlAdress.isDirectory()){
				mysqlAdress.mkdirs();
			}
			String adress = getRootPath()+"mysql.zip";
			File file = new File(adress);
			if(file.exists()){
				String WINDOWS_remove_ALL="cmd /c xcopy /y "; //xcopy c:\123\ d:\123\
				String photo_path_temp = adress.replace("/", "\\");
				
				/*int i = new CommandUtil().executeCommand(WINDOWS_remove_ALL + photo_path_temp + " " + (mysqlPass.replace("/", "\\")));
				System.out.println(WINDOWS_remove_ALL + photo_path_temp + " " + (mysqlPass.replace("/", "\\")));*/
				int i = new CommandUtil().executeCommand(WINDOWS_remove_ALL + "\"" + photo_path_temp + "\" \"" + (mysqlPass.replace("/", "\\"))+"\"");
				System.out.println(WINDOWS_remove_ALL + "\"" + photo_path_temp + "\" \"" + (mysqlPass.replace("/", "\\"))+"\"");
			}
			
			File sqlPass = new File(mysqlPass+"/mysql.zip");
			if(!sqlPass.exists()){
				throw new RuntimeException("找不到MYSQL数据库，请检查配置！");
			}
			Zip7z.unzip7zAll(mysqlPass+"/mysql.zip", mysqlPass, null);
			
			sqlPass.delete();
			
			//2、读取配置文件，修改端口号（后面再添加此功能）
			
			//3、注册服务并启动mysql
			//start c:\hzb\mysql\bin\mysqld
			//start c:\hzb\mysql\bin\mysqladmin -u root --password=admin shutdown
			System.out.println("cmd /c start /b "+(mysqlPass+"/mysql/bin/mysqld ").replace("/", "\\"));
			new CommandUtil().executeCommand("cmd /c start /b "+(mysqlPass+"/mysql/bin/mysqld ").replace("/", "\\"));
			/*File my_install = new File(mysqlPass+"/mysql/my_install.bat");
			if(!my_install.exists()){
				throw new RuntimeException("注册并启动服务失败，请检查配置！");
			}
			new CommandUtil().executeCommand("cmd /c start "+(mysqlPass+"/mysql/my_install.bat ").replace("/", "\\"));*/
			
			getService();//看服务是否已经启动
			
			KingbsconfigBS.saveImpDetail("2","1","准备MYSQL数据库完毕",id);
			
			//4、创建用户 并建表
			
			//5、抽取oracle数据，导入mysql中
			Map<String, String> mapMysql = getMapMysql();
			//获取ZB01、GB4762
			//Map<String,String> mapCodeValues = getCodeValues();
			
			DataOrgExpMysql control = new DataOrgExpMysql();
			DataOrgExpMysqlThread t1 = new DataOrgExpMysqlThread(id, userVo, control, mapMysql, "1",contact,tel,notes);
			DataOrgExpMysqlThread t2 = new DataOrgExpMysqlThread(id, userVo, control, mapMysql, "2",contact,tel,notes);

			control.setPath(mysqlPass);
			control.setZippath(mysqlZipPass);
			control.setThd1(new Thread(t1, "DataOrgExpMysql_1_" + id));
			control.setThd2(new Thread(t2, "DataOrgExpMysql_2_" + id));
			control.start();
			
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败："+e.getMessage(),id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		

	}
	
	private Map<String, String> getCodeValues() {
		Map<String, String> map = new HashMap<String, String>();
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> list = sess.createSQLQuery("SELECT CODE_NAME3,code_value FROM code_value WHERE CODE_TYPE IN('ZB01','GB4762')").list();
		for(Object[] obj : list){
			map.put(obj[0]+"", obj[1]+"");
		}
		return map;
	}

	private Map<String, String> getMapMysql() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("A01", TYGSsqlUtil.A01MysqlHZB);
		map.put("A02", TYGSsqlUtil.A02MysqlHZB);
		map.put("A05", TYGSsqlUtil.A05MysqlHZB);
		map.put("A06", TYGSsqlUtil.A06MysqlHZB);
		map.put("A08", TYGSsqlUtil.A08MysqlHZB);
		map.put("A14", TYGSsqlUtil.A14MysqlHZB);
		map.put("A15", TYGSsqlUtil.A15MysqlHZB);
		map.put("A36", TYGSsqlUtil.A36MysqlHZB);
		map.put("A57", TYGSsqlUtil.A57MysqlHZB);
		map.put("A99Z1", TYGSsqlUtil.A99Z1MysqlHZB);
		map.put("B01", TYGSsqlUtil.B01MysqlHZB);
		return map;
	}
	
	private String getTempPath() {
		String localp = AppConfig.LOCAL_FILE_BASEURL;
		try {
			localp = URLDecoder.decode(localp, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//上传路径
		String upload_file = rootPath + "/temp/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		String tempp = upload_file + uuid + "/";
		return tempp;
	}
	
	private String getPath() {
		String classPath = getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		//上传路径
		String upload_file = rootPath + "zipload/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		String zip = upload_file + uuid + "/";
		return zip;
	}
	
	private String getRootPath() {
		String classPath = getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	
	public void getService() throws Exception{
		Thread.currentThread();
		Thread.sleep(15000);//毫秒
		//建立连接
		int num=0;
		Connection conn=createConnection(num);
	};	

	/**
	 * 建立连接
	 * @return
	 * @throws RadowException 
	 */
	public Connection createConnection(int num) throws Exception{
		Connection conn=null;
		String url = "jdbc:mysql://localhost:7953/gwybase?user=gwydb&password=gwydbpwd&useUnicode=true&characterEncoding=UTF8";
		// 加载驱动
		try{
			num++;
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			conn = DriverManager.getConnection(url);
			if(conn!=null){
				//连接成功
			}else{
				throw new Exception("数据库登录失败!");
				//连接不成功
			}
		}catch(Exception e){
			num++;
			Thread.currentThread();
			Thread.sleep(4000);//毫秒
			if(num>15){
				throw new Exception("mysql服务启动时间过长，程序已经终止！");
			}
			conn=createConnection(num);
		}
		return conn;
	}
	
}
