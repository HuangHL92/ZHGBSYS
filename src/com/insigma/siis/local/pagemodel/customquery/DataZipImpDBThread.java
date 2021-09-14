package com.insigma.siis.local.pagemodel.customquery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;



import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBNewUtil2;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.DataZipExpControl;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

/**
 * 导出ZIP数据
 * @author 15084
 *
 */
public class DataZipImpDBThread implements Runnable {
	private static int limit = Integer.MIN_VALUE;
	private String uuid;
	private CurrentUser user;
	private String sign;
	private String linkpsnthis;
	private String linktel;
	private UserVO userVo;
	
	 
	private static String SOURCEFILEPATH;
	private static String ATTACHMENTPATH;
	public DataZipImpDBThread(String uuid,CurrentUser user,String sign,String linkpsnthis,String linktel,UserVO userVo) {
		super();
		this.uuid = uuid;
		this.user = user;
		this.sign = sign;
		this.linkpsnthis = linkpsnthis;
		this.linktel = linktel;
		this.userVo = userVo;
		
	}

	@Override
	public void run() {
		StopWatch w = new StopWatch();
		String infile = "";					 					// 文件
		String process_run = "1";								// 过程序号
		String tables[] = {"A01","A02","A39","A36","A15","B01","FILEINFO","Constant"};
		String no="1";
		HBSession sess=HBUtil.getHBSession();
		DataZipExpControl control=new DataZipExpControl();
		try {
			
			String filePath = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'PHOTO_PATH'").uniqueResult();
			String uploadPath = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'UPLOAD_PATH'").uniqueResult();
			SOURCEFILEPATH = filePath;
			ATTACHMENTPATH = uploadPath;
			w.start();
			KingbsconfigBS.saveImpDetail(process_run,"1","数据校验中",uuid);
			KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);
			process_run = "2";
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/exp" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			String path = getPath();
			String zippath = path + DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
			File file = new File(zippath);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String zippathtable = zippath + "Tables/";
			String photopath = zippath + "Photos/";
			String attachmentpath = zippath + "attachment/";
			File file1 = new File(zippathtable);
			File file2 =new File(photopath);   
			File file3 =new File(attachmentpath);  
			// 如果文件夹不存在则创建
			if (!file1.exists() && !file1.isDirectory()) {
				file1.mkdirs();
			}
			if (!file2.exists() && !file2.isDirectory()) {
				file2.mkdirs();
			}
			if (!file3.exists() && !file3.isDirectory()) {
				file3.mkdirs();
			}
			//Connection conn = sess.connection();
			//PreparedStatement stmt = null;
			String zipfile = "";
			if("zip".equals(sign)) {
				zipfile = "导出数据包_"+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".zip";
			}
			int fetchsize = 100;
//			if(DBUtil.getDBType().equals(DBType.MYSQL)){
//				Context	env = (Context) new InitialContext();
//		        BasicDataSource datasourceRef = (BasicDataSource) env.lookup("java:comp/env/jdbc/insiis");
//		        String url= datasourceRef.getUrl();
//		        Class.forName("com.mysql.jdbc.Driver");
//		        conn=DriverManager.getConnection(url,datasourceRef.getUsername(),datasourceRef.getPassword()); 
//		        fetchsize = Integer.MIN_VALUE;
//			} else {
//				conn = sess.connection();
//			}
			int number = 0;
			w.stop();
			appendFileContent(logfilename, "线程"+no+"_1:"+"\n"+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");	
			while (true) {
				w.start();
				number = control.getNewNumber();
				if(number > 7){
					break;
				}
				String table = tables[number];
				CommonQueryBS.systemOut("thd---"+no +"----"+number+"---"+table);
				appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number)+"："+table+".xml数据生成处理，剩余"+(control.getNumber2())+"",uuid);		//记录导入过程
				Xml4HZBNewUtil2.data2Xml(table, zippath, sess.connection(), logfilename,sign,linkpsnthis,linktel);
				
//				
			}
			//照片处理
			List<A57> photolist = sess.createSQLQuery("select * from A57 where a0000 in(select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')").addEntity(A57.class).list();
			if(photolist != null && photolist.size()>0){
				for (int i = 0; i < photolist.size(); i++) {
					A57 a57 = photolist.get(i);
					if(a57.getA0000()!=null && !a57.getA0000().equals("") && a57.getPhotoname()!=null&& a57.getPhotoname()!=""&& a57.getPhotopath()!=null&& a57.getPhotopath()!=""){
						
						File sourcefile = new File(SOURCEFILEPATH+"\\"+a57.getPhotopath()+a57.getPhotoname());
						File targetFile = new File(photopath +"{"+a57.getA0000() +"}." + "jpg");
						if (sourcefile.exists() && sourcefile.isFile()) {
							UploadHelpFileServlet.copyFile(sourcefile, targetFile);
							
						}
					}
				}
				
			}
			//附件处理
			List<Object[]> attachmentlist = sess.createSQLQuery("select * FROM TABLEFILE WHERE FILETYPE in ('kccl','dazs','ndkh') and A0000 in (select distinct A0000 from A01 where A0221 in ('1A21','XY51','51G') and A0163 like '1%')").list();
			if(attachmentlist != null && attachmentlist.size()>0) {
				for(Object[] objs:attachmentlist ) {
					if(objs[0]!=null&&objs[0]!=""&&objs[3]!=null&&objs[3]!=""&&objs[4]!=null&&objs[3]!="") {
						File sourcefile = new File(ATTACHMENTPATH+"\\"+objs[4].toString()+"\\"+objs[3].toString());
						//判断文件存在 则存入attachment文件夹
						if(sourcefile.exists()) {
							//截取文件名，去掉自动生成的id前缀并拼接需生成文件的文件名
							int begin=objs[3].toString().indexOf("_")+1;
							int last=objs[3].toString().lastIndexOf(".");
							String filename=objs[3].toString().substring(begin,last)+objs[9].toString()+objs[3].toString().substring(last);
							String newPath = attachmentpath+objs[0]+"\\";
							File file4 = new File(newPath);
							if (!file4.exists() && !file4.isDirectory()) {
								file4.mkdirs();
							}
							File targetFile = new File(newPath+filename);
							if (sourcefile.exists() && sourcefile.isFile()) {
								UploadHelpFileServlet.copyFile(sourcefile, targetFile);
							}
						  }
						}
				}
			}
			
			
			if(control.getStatus()==1){
					
					KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
					process_run = "3";
					KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
					infile = zipfile;
					appendFileContent(logfilename, "压缩---"+":"+ DateUtil.getTime()+"\n");	
					w.start();
					//获取追加命令
					//执行命令复制图片
//					MingLing7zUtil.add(zipfile, zippath);
					//页面详情信息-----------------------------------
					String sql3 = "update expinfo set STATUS = '文件压缩中请稍候...' where ID = '"+uuid+"'";
					sess.createSQLQuery(sql3).executeUpdate();
					//------------------------------------------------
					if("7z".equals(sign)){
						Zip7z.zip7Z(zippath, zipfile, "20171020");
					}else if("zip".equals(sign)){
						Zip7z.zip7Z(zippath, zipfile, null);
					}else{
						NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
					}
					w.stop();
					appendFileContent(logfilename, "压缩完成---"+":"+ DateUtil.getTime()+"\n");	
					KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));	
					appendFileContent(logfilename, "压缩完成:"+"\n"+w.elapsedTime()+"\n");
					//------------------------------------------------------------
					//记录页面详情
					String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
					String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '导出完成',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
					sess.createSQLQuery(sql2).executeUpdate();
					try {
						if ("7z".equals(sign)||"zip".equals(sign)) {
							new LogUtil("412", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
						} else {
							new LogUtil("411", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.delFolder(zippath);
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				String sql4 = "update expinfo set STATUS = '文件导出异常!' where ID = '"+uuid+"'";
				sess.createSQLQuery(sql4).executeUpdate();
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(no.equals("1")){
				control.errStatus("1");
			}else {
				control.errStatus("2");
			}
			this.delFolder(control.getZippath());
		}
		
	}	
	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/";
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
	
	public static void appendFileContent(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	// 删除文件夹
		// param folderPath 文件夹完整绝对路径
		public static void delFolder(String folderPath) {
			try {
				delAllFile(folderPath); // 删除完里面所有内容
				String filePath = folderPath;
				filePath = filePath.toString();
				java.io.File myFilePath = new java.io.File(filePath);
				myFilePath.delete(); // 删除空文件夹
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 删除指定文件夹下所有文件
		// param path 文件夹完整绝对路径
		public static boolean delAllFile(String path) {
			boolean flag = false;
			File file = new File(path);
			if (!file.exists()) {
				return flag;
			}
			if (!file.isDirectory()) {
				return flag;
			}
			String[] tempList = file.list();
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					temp.delete();
				}
				if (temp.isDirectory()) {
					delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
					delFolder(path + "/" + tempList[i]);// 再删除空文件夹
					flag = true;
				}
			}
			return flag;
		}
		
}
