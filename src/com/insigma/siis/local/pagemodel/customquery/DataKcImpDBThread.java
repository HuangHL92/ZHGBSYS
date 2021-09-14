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
public class DataKcImpDBThread implements Runnable {
	private String uuid;
	private UserVO userVo;
	private String code_value;
	private String userid;
	
	public DataKcImpDBThread(String uuid,UserVO userVo) {
        this.uuid = uuid;
        this.userVo = userVo;
    }
	private static String ATTACHMENTPATH;
	private String zipPath;
	public DataKcImpDBThread(String uuid,UserVO userVo, String code_value, String userid) {
		this.uuid = uuid;
        this.userVo = userVo;
        this.code_value = code_value;
        this.userid = userid;
	}
	@SuppressWarnings({ "rawtypes", "static-access" })
	@Override
	public void run() {
		StopWatch w = new StopWatch();
		String process_run = "1";								// 过程序号
		HBSession sess=HBUtil.getHBSession();
		DataZipExpControl control=new DataZipExpControl();
		try {
			String zipPath=getZipPath();
			String uploadPath = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'HZB_PATH'").uniqueResult();
			ATTACHMENTPATH = uploadPath;
			w.start();
			KingbsconfigBS.saveImpDetail(process_run,"1","数据校验中",uuid);
			KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);
			process_run = "2";
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/exp" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			int fetchsize = 100;
			int number = 0;
			w.stop();
			appendFileContent(logfilename, "线程"+"_1:"+"\n"+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");	
			//附件处理
			List<Object[]> attachmentlist = sess.createSQLQuery("select * from GI where xjqy= '"+code_value+"'").list();
			if(attachmentlist != null && attachmentlist.size()>0) {
				for(Object[] objs:attachmentlist ) {
					if(objs[0]!=null&&objs[0]!=""&&objs[3]!=null&&objs[3]!=""&&objs[4]!=null&&objs[3]!="") {
						File sourcefile = new File(uploadPath+objs[4].toString());
						//判断文件存在 则存入attachment文件夹
						if(sourcefile.exists()) {
							//截取文件名，去掉自动生成的id前缀并拼接需生成文件的文件名
							String filename=objs[3].toString();
							//int begin=objs[3].toString().indexOf("_")+1;
							//int last=objs[3].toString().lastIndexOf(".");
							//String filename=objs[3].toString().substring(begin,last)+objs[3].toString().substring(last);
							String newPath = zipPath+"\\";
							File file4 = new File(filename);
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
					appendFileContent(logfilename, "压缩---"+":"+ DateUtil.getTime()+"\n");	
					w.start();
					String zipfile=zipPath.substring(0, zipPath.length()-1)+".zip";
					//------------------------------------------------
					Zip7z.zip7Z(zipPath.substring(0, zipPath.length()-1), zipfile, null);
					//Zip7z.zip7Z(zippath, zipfile, null);
					w.stop();
					appendFileContent(logfilename, "压缩完成---"+":"+ DateUtil.getTime()+"\n");	
					KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,zipfile.replace("\\", "/"));	
					appendFileContent(logfilename, "压缩完成:"+"\n"+w.elapsedTime()+"\n");
					//------------------------------------------------------------
					//记录页面详情
					String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
					try {
							new LogUtil("412", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.delFolder(zipPath);
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
		}
		
	}	
	
	public String getZipPath() {
		//"D:/HZB//temp/zipload/"+uuid+"/";
		String path=getPath();
		String zipPath=path+"导出考察材料_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
		File file = new File(zipPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println("zipPath:"+zipPath);
		return zipPath;
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
