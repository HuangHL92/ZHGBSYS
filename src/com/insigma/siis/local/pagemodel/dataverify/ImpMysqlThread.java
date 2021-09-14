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
			KingbsconfigBS.saveImpDetail(process_run,"1","����������ʱ����",id);						//��¼У�����
			DeletePersonTimer.exec();
			
			KingbsconfigBS.saveImpDetail(process_run,"1","���ڽ��л������",id);						//��¼У�����
			//��� ���������˿ں� �Ƿ�ռ�ã���������Ӵ˹��ܣ�
			
			KingbsconfigBS.saveImpDetail(process_run,"2","���",id);						//��¼У�����
			
			process_run = "2";
			KingbsconfigBS.saveImpDetail(process_run,"1","����׼��MYSQL���ݿ�",id);
			//1����MYSQL���Ƶ�ָ����·����DATAS�ļ����£�������ѹ��MYSQL��
			mysqlZipPass = mysqlZipPass + "/MYSQL���ݿ��ϱ�"+System.currentTimeMillis();
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
				throw new RuntimeException("�Ҳ���MYSQL���ݿ⣬�������ã�");
			}
			Zip7z.unzip7zAll(mysqlPass+"/mysql.zip", mysqlPass, null);
			
			sqlPass.delete();
			
			//2����ȡ�����ļ����޸Ķ˿ںţ���������Ӵ˹��ܣ�
			
			//3��ע���������mysql
			//start c:\hzb\mysql\bin\mysqld
			//start c:\hzb\mysql\bin\mysqladmin -u root --password=admin shutdown
			System.out.println("cmd /c start /b "+(mysqlPass+"/mysql/bin/mysqld ").replace("/", "\\"));
			new CommandUtil().executeCommand("cmd /c start /b "+(mysqlPass+"/mysql/bin/mysqld ").replace("/", "\\"));
			/*File my_install = new File(mysqlPass+"/mysql/my_install.bat");
			if(!my_install.exists()){
				throw new RuntimeException("ע�Ტ��������ʧ�ܣ��������ã�");
			}
			new CommandUtil().executeCommand("cmd /c start "+(mysqlPass+"/mysql/my_install.bat ").replace("/", "\\"));*/
			
			getService();//�������Ƿ��Ѿ�����
			
			KingbsconfigBS.saveImpDetail("2","1","׼��MYSQL���ݿ����",id);
			
			//4�������û� ������
			
			//5����ȡoracle���ݣ�����mysql��
			Map<String, String> mapMysql = getMapMysql();
			//��ȡZB01��GB4762
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
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ�ܣ�"+e.getMessage(),id);
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
		
		//�ϴ�·��
		String upload_file = rootPath + "/temp/";
		try {
			File file =new File(upload_file);    
			//����ļ��в������򴴽�    
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
		
		//windows�� 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux�� 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		//�ϴ�·��
		String upload_file = rootPath + "zipload/";
		try {
			File file =new File(upload_file);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//��ѹ·��
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
		
		//windows�� 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux�� 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	
	public void getService() throws Exception{
		Thread.currentThread();
		Thread.sleep(15000);//����
		//��������
		int num=0;
		Connection conn=createConnection(num);
	};	

	/**
	 * ��������
	 * @return
	 * @throws RadowException 
	 */
	public Connection createConnection(int num) throws Exception{
		Connection conn=null;
		String url = "jdbc:mysql://localhost:7953/gwybase?user=gwydb&password=gwydbpwd&useUnicode=true&characterEncoding=UTF8";
		// ��������
		try{
			num++;
			Class.forName("com.mysql.jdbc.Driver");
			// ��ȡ���ݿ�����
			conn = DriverManager.getConnection(url);
			if(conn!=null){
				//���ӳɹ�
			}else{
				throw new Exception("���ݿ��¼ʧ��!");
				//���Ӳ��ɹ�
			}
		}catch(Exception e){
			num++;
			Thread.currentThread();
			Thread.sleep(4000);//����
			if(num>15){
				throw new Exception("mysql��������ʱ������������Ѿ���ֹ��");
			}
			conn=createConnection(num);
		}
		return conn;
	}
	
}
