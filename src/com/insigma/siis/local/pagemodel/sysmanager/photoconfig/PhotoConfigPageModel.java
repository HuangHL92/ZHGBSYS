package com.insigma.siis.local.pagemodel.sysmanager.photoconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.config.AppConfigLoader;
import com.insigma.siis.local.pagemodel.customquery.NotePickUpPageModel;
import com.insigma.siis.local.pagemodel.customquery.doWJGL;
import com.insigma.siis.local.pagemodel.sysmanager.photoconfig.util.A17RedoUtil;
import com.insigma.siis.local.pagemodel.sysmanager.photoconfig.util.BQimport;
import com.insigma.siis.local.pagemodel.sysmanager.photoconfig.util.PCimport;

import edu.emory.mathcs.backport.java.util.Arrays;

public class PhotoConfigPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		List<String> list = sess.createSQLQuery("select aaa005 from aa01 where aaa001 in ('HZB_PATH','LOCAL_BACKUP_FILE','LOCAL_FILE_BASEURL','LOGOUT_ISUSEFUL','PHOTO_PATH','TRANS_SERVER_BASEURL') order by AAA001").list();
		this.getPageElement("hzb_path").setValue(list.get(0).toString());
		this.getPageElement("trans_server_baseurl").setValue(list.get(5).toString());
		this.getPageElement("local_backup_file").setValue(list.get(1).toString());
		this.getPageElement("local_file_baseurl").setValue(list.get(2).toString());
		this.getPageElement("logout_isuseful").setValue(list.get(3).toString());
		this.getPageElement("photopath").setValue(list.get(4).toString());
		
		List<String> list2 = sess.createSQLQuery("select aaa005 from aa01 where aaa001 in ('ONE_AUDIT','TTF_AUDIT') order by AAA001").list();
		this.getPageElement("oaudit").setValue(list2.get(0).toString());
		this.getPageElement("ttfaudit").setValue(list2.get(1).toString());
		
		//预加载当前开关设置数据
		String import_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'IMPORT_ISUSEFUL'").uniqueResult().toString();
		this.getPageElement("import_isuseful").setValue(import_isuseful);
		String import_isusefultg = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'IMPORT_ISUSEFULTG'").uniqueResult().toString();
		this.getPageElement("import_isusefultg").setValue(import_isusefultg);
		String mysqlexport_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'MYSQLEXPORT_ISUSEFUL'").uniqueResult().toString();
		this.getPageElement("mysqlexport_isuseful").setValue(mysqlexport_isuseful);
		//直统功能
		String zht_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'ZHT_ISUSEFUL'").uniqueResult().toString();
		this.getPageElement("zht_isuseful").setValue(zht_isuseful);
		//pps
		String pps_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'PPS_ISUSEFUL'").uniqueResult().toString();
		this.getPageElement("pps_isuseful").setValue(pps_isuseful);
		//干部监督网络切换
		String gbjdlqh = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'GBJDWLQH'").uniqueResult().toString();
		this.getPageElement("gbjdlqh").setValue(gbjdlqh);
		
		//check whether the last update was fail
		List<String> errorList = sess.getSession().createSQLQuery("select aaa001 from aa01 where active='0'").list();
		Map<String,String> map =  new HashMap<String, String>();
		map.put("HZB_PATH", list.get(0).toString());
		map.put("LOCAL_BACKUP_FILE", list.get(1).toString());
		map.put("LOCAL_FILE_BASEURL", list.get(5).toString());
		map.put("TRANS_SERVER_BASEURL", list.get(5).toString());
		map.put("PHOTO_PATH", list.get(4).toString());
		if(errorList != null && errorList.size() > 0){
			this.setMainMessage("检测到上次文件迁移时非法退出，系统正恢复上次操作，请等待操作完成！");
			this.getExecuteSG().addExecuteCode("Ext.getBody().mask('正在恢复数据...');");
			for (int i = 0; i < errorList.size(); i++) {
				map.get(errorList.get(i));
				List<String> old = sess.getSession().createSQLQuery("select oldparam from aa01 where aaa001='"+map.get(errorList.get(i))+"'").list();
				FileUtil.copyChildFolder(map.get(errorList.get(i)), old.get(0),errorList.get(i));
				sess.createSQLQuery("update aa01 set aaa005='"+map.get(errorList.get(i))+"' where aaa001='"+errorList.get(i)+"'").executeUpdate();
				this.getExecuteSG().addExecuteCode("Ext.getBody().unmask();");
				doInit();
			}
		}
		
		
		
		File diskPartition = new File(list.get(0).toString()); 
		long usableSpace = diskPartition.getUsableSpace(); 
		
		usableSpace = usableSpace/1024/1024;//可用剩余空间，单位为M 
		this.getPageElement("sy").setValue(usableSpace+"M");
		long useSpace = diskPartition.getTotalSpace(); 
		useSpace = useSpace/1024/1024;//可用剩余空间，单位为M 
		this.getPageElement("ysy").setValue(useSpace-usableSpace+"M");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存配置信息
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnSave.onclick")
	@Transaction
	public int dosaveFtpConf() throws RadowException{
		String photoPath_old = AppConfig.PHOTO_PATH;
		String hzb_path_old = AppConfig.HZB_PATH;
		String trans_server_baseurl_old = AppConfig.TRANS_SERVER_BASEURL;
		String local_backup_file_old = AppConfig.LOCAL_BACKUP_FILE;
		String local_file_baseurl_old = AppConfig.LOCAL_FILE_BASEURL;
		try{
			HBSession sess = HBUtil.getHBSession();
			String photoPath = this.getPageElement("photopath").getValue().toUpperCase();
			//赋予新值
//			String photoPath_1 = photoPath + "/" + photoPath_old.substring(photoPath_old.2lastIndexOf("/")+1, photoPath_old.length());
			String hzb_path = this.getPageElement("hzb_path").getValue().toUpperCase(); //hzb目录
			String local_backup_file = this.getPageElement("local_backup_file").getValue().toUpperCase(); //ftp文件
			String local_file_baseurl = this.getPageElement("local_file_baseurl").getValue().toUpperCase(); //ftp数据
			String logout_isuseful = this.getPageElement("logout_isuseful").getValue().toUpperCase(); //日志输出
			String trans_server_baseurl = this.getPageElement("trans_server_baseurl").getValue().toUpperCase();
			String import_isuseful = this.getPageElement("import_isuseful").getValue().toUpperCase(); //按机构导入数据(HZB1.0)开关
			
			String import_isusefultg = this.getPageElement("import_isusefultg").getValue().toUpperCase(); //按机构导入数据(套改版)开关
			String mysqlexport_isuseful = this.getPageElement("mysqlexport_isuseful").getValue().toUpperCase(); //MYSQL数据库上报开关
			String zht_isuseful = this.getPageElement("zht_isuseful").getValue().toUpperCase(); //直统开关
			String pps_isuseful = this.getPageElement("pps_isuseful").getValue().toUpperCase(); //pps抽样开关
			//干部监督网络切换
			String gbjdlqh = this.getPageElement("gbjdlqh").getValue();;
			
			
			String oaudit = this.getPageElement("oaudit").getValue(); //干部一处审核
			String ttfaudit = this.getPageElement("ttfaudit").getValue(); //干部处审核
			
			
			List<String> list = Arrays.asList(new String[]{hzb_path,local_backup_file,local_file_baseurl,trans_server_baseurl});

			for (int i = 0; i < list.size(); i++) {
				if(!new File(list.get(i).substring(0, 3)).exists()){
					this.setMainMessage("不存在该磁盘："+ list.get(i).substring(0, 3));
					return EventRtnType.NORMAL_SUCCESS;
				}
				for (int j = 0; j < list.size(); j++) {
					if(list.get(i).contains(list.get(j)) && i != j){
						if(hzb_path.equals(list.get(j))){
							continue;
						}
						this.setMainMessage("目录"+list.get(j) + "不能是其他目录的上级目录！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				
			}
			
			//copy from old to new 
			if(!hzb_path_old.equals(hzb_path)){
				this.getExecuteSG().addExecuteCode("Ext.getBody().mask('正在重建数据缓存主目录...');");
				sess.createSQLQuery("update aa01 set oldparam='"+hzb_path+"', active='0' where aaa001='HZB_PATH'").executeUpdate();
				FileUtil.copyChildFolder(hzb_path_old, hzb_path,"HZB_PATH");
			}
			if(!photoPath_old.equals(photoPath)){
				sess.createSQLQuery("update aa01 set oldparam='"+photoPath+"', active='0' where aaa001='PHOTO_PATH'").executeUpdate();
				this.getExecuteSG().addExecuteCode("Ext.getBody().mask('正在迁移照片数据...');");
				FileUtil.copyChildFolder(photoPath_old,photoPath,"PHOTO_PATH");
			}
			if(!trans_server_baseurl_old.equals(trans_server_baseurl)){
				sess.createSQLQuery("update aa01 set oldparam='"+trans_server_baseurl+"', active='0' where aaa001='TRANS_SERVER_BASEURL'").executeUpdate();
				this.getExecuteSG().addExecuteCode("Ext.getBody().mask('正在创建FTP服务根目录...');");
				FileUtil.copyChildFolder(trans_server_baseurl_old,trans_server_baseurl,"TRANS_SERVER_BASEURL");
			}
			if(!local_backup_file_old.equals(local_backup_file)){
				sess.createSQLQuery("update aa01 set oldparam='"+local_backup_file+"', active='0' where aaa001='LOCAL_BACKUP_FILE'").executeUpdate();
				this.getExecuteSG().addExecuteCode("Ext.getBody().mask('正在迁移FTP本地备份文件...');");
				FileUtil.copyChildFolder(local_backup_file_old,local_backup_file,"LOCAL_BACKUP_FILE");
			}
			if(!local_file_baseurl_old.equals(local_file_baseurl)){
				sess.createSQLQuery("update aa01 set oldparam='"+local_file_baseurl+"', active='0' where aaa001='LOCAL_FILE_BASEURL'").executeUpdate();
				this.getExecuteSG().addExecuteCode("Ext.getBody().mask('正在迁移FTP本地数据...');");
				FileUtil.copyChildFolder(local_file_baseurl_old,local_file_baseurl,"LOCAL_FILE_BASEURL");
			}
						
			//修改aa01信息 
			sess.createSQLQuery("update aa01 set aaa005='"+logout_isuseful+"' where aaa001='LOGOUT_ISUSEFUL'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+trans_server_baseurl+"' where aaa001='TRANS_SERVER_BASEURL'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+hzb_path+"' where aaa001='HZB_PATH'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+photoPath +  "' where aaa001='PHOTO_PATH'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+local_backup_file+"' where aaa001='LOCAL_BACKUP_FILE'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+local_file_baseurl+"' where aaa001='LOCAL_FILE_BASEURL'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+import_isuseful+"' where aaa001='IMPORT_ISUSEFUL'").executeUpdate();		//按机构导入数据(HZB1.0)开关
			sess.createSQLQuery("update aa01 set aaa005='"+import_isusefultg+"' where aaa001='IMPORT_ISUSEFULTG'").executeUpdate();		//按机构导入数据(套改版)开关
			sess.createSQLQuery("update aa01 set aaa005='"+mysqlexport_isuseful+"' where aaa001='MYSQLEXPORT_ISUSEFUL'").executeUpdate();		//MYSQL数据库上报开关
			sess.createSQLQuery("update aa01 set aaa005='"+zht_isuseful+"' where aaa001='ZHT_ISUSEFUL'").executeUpdate();		//直统开关
			sess.createSQLQuery("update aa01 set aaa005='"+pps_isuseful+"' where aaa001='PPS_ISUSEFUL'").executeUpdate();		//pps抽样开关
			
			sess.createSQLQuery("update aa01 set aaa005='"+oaudit+"' where aaa001='ONE_AUDIT'").executeUpdate();		//干部一处审核
			sess.createSQLQuery("update aa01 set aaa005='"+ttfaudit+"' where aaa001='TTF_AUDIT'").executeUpdate();		//干部处审核
			
			sess.createSQLQuery("update aa01 set aaa005='"+gbjdlqh+"' where aaa001='GBJDWLQH'").executeUpdate();		//干部监督网络
			
			AppConfig.PHOTO_PATH = photoPath;
			GlobalNames.sysConfig.put("PHOTO_PATH", photoPath);
			PhotosUtil.PHOTO_PATH = photoPath+"/";
			AppConfig.HZB_PATH = hzb_path;
			AppConfig.LOCAL_BACKUP_FILE = local_backup_file;
			AppConfig.LOCAL_FILE_BASEURL = local_file_baseurl;
			AppConfig.LOG_CONTROL = logout_isuseful;
			AppConfig.GBJDWLQH = gbjdlqh;
			
			updateServiceXml(photoPath);
			this.setMainMessage("保存系统参数配置信息成功!");
			this.getExecuteSG().addExecuteCode("Ext.getBody().unmask();");
			doInit();
			return EventRtnType.NORMAL_SUCCESS;
		}catch(Exception e){
			throw new RadowException("保存系统参数配置信息失败:"+e.getMessage());
		}
	}

	private void updateServiceXml(String photoPath) throws Exception {
		String xml = AppConfigLoader.getSetupPath()+"\\tomcat6\\conf\\sever.xml";
//		String xml = "D:\\apache-tomcat-6.0.39\\conf\\server.xml";
		SAXReader reader = new SAXReader();
		Document doc;
		String docBase = "";
		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(new File(xml));  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> listnodes = root.element("Service").element("Engine").element("Host").elements("Context");
	        for (Element element : listnodes) {
	        	String path = element.attributeValue("path");
	        	docBase = element.attributeValue("docBase")==null? "":element.attributeValue("docBase");
	        	if(path!=null && path.equals("/pt")){
	        		if(!docBase.equals(photoPath)){
	        			element.addAttribute("docBase", photoPath);
	        		}
	        	}
			}
	        in.close();
	        if(!docBase.equals(photoPath)){
	        	out = new FileOutputStream(xml);
	        	OutputFormat format = OutputFormat.createPrettyPrint();
	        	format.setEncoding("utf-8");
	        	XMLWriter writer =new XMLWriter(out,format);
	        	writer.write(doc);
	        	writer.close();
	        	System.out.println("执行结束！");
    		}
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if(out!=null){
				out.close();
			}
		}
		
	}
	
	@PageEvent("jianlizs.onclick")
	@Transaction
	public int jianlizs() throws RadowException, AppException, SQLException{
		A17RedoUtil.redoA17All();
		this.setMainMessage("执行成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("initJLForAll.onclick")
	@Transaction
	public int initJLForAll() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		PreparedStatement pst = null;
		//一、初始化简历
		new NotePickUpPageModel().gridPeople(sess,conn,pst,"");

		this.setMainMessage("初始化简历拆分成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("biaoqiandaoru.onclick")
	@Transaction
	public int biaoqiandaoru() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		/*Connection conn = sess.connection();
		PreparedStatement pst = null;*/
		BQimport.importExcel(sess);
		
		this.setMainMessage("完成成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("pingcedaoru.onclick")
	@Transaction
	public int pingcedaoru() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		/*Connection conn = sess.connection();
		PreparedStatement pst = null;*/
		PCimport.importExcel(sess,"E:\\杭州20201106\\智选标签汇总\\区县\\2017年度区、县（市）领导干部测评数据");
		PCimport.importExcel(sess,"E:\\杭州20201106\\智选标签汇总\\区县\\2018年度区、县（市）领导干部测评数据");
		PCimport.importExcel(sess,"E:\\杭州20201106\\智选标签汇总\\区县\\2019年度区、县（市）领导干部测评数据");
		
		this.setMainMessage("完成成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doWJGL.onclick")
	@Transaction
	public int doWJGL() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		PreparedStatement pst = null;
		//一、将制定位置文件存入数据库
		new doWJGL().insertWJ();
		this.setMainMessage("文件归类成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
