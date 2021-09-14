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
 * ����ZIP����
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
		String process_run = "1";								// �������
		HBSession sess=HBUtil.getHBSession();
		DataZipExpControl control=new DataZipExpControl();
		try {
			String zipPath=getZipPath();
			String uploadPath = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'HZB_PATH'").uniqueResult();
			ATTACHMENTPATH = uploadPath;
			w.start();
			KingbsconfigBS.saveImpDetail(process_run,"1","����У����",uuid);
			KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid);
			process_run = "2";
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/exp" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			int fetchsize = 100;
			int number = 0;
			w.stop();
			appendFileContent(logfilename, "�߳�"+"_1:"+"\n"+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");	
			//��������
			List<Object[]> attachmentlist = sess.createSQLQuery("select * from GI where xjqy= '"+code_value+"'").list();
			if(attachmentlist != null && attachmentlist.size()>0) {
				for(Object[] objs:attachmentlist ) {
					if(objs[0]!=null&&objs[0]!=""&&objs[3]!=null&&objs[3]!=""&&objs[4]!=null&&objs[3]!="") {
						File sourcefile = new File(uploadPath+objs[4].toString());
						//�ж��ļ����� �����attachment�ļ���
						if(sourcefile.exists()) {
							//��ȡ�ļ�����ȥ���Զ����ɵ�idǰ׺��ƴ���������ļ����ļ���
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
					
					KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid);			//��¼�������
					process_run = "3";
					KingbsconfigBS.saveImpDetail(process_run,"1","ѹ����",uuid);			//��¼�������
					appendFileContent(logfilename, "ѹ��---"+":"+ DateUtil.getTime()+"\n");	
					w.start();
					String zipfile=zipPath.substring(0, zipPath.length()-1)+".zip";
					//------------------------------------------------
					Zip7z.zip7Z(zipPath.substring(0, zipPath.length()-1), zipfile, null);
					//Zip7z.zip7Z(zippath, zipfile, null);
					w.stop();
					appendFileContent(logfilename, "ѹ�����---"+":"+ DateUtil.getTime()+"\n");	
					KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid,zipfile.replace("\\", "/"));	
					appendFileContent(logfilename, "ѹ�����:"+"\n"+w.elapsedTime()+"\n");
					//------------------------------------------------------------
					//��¼ҳ������
					String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
					try {
							new LogUtil("412", "IMP_RECORD", "", "", "���ݵ���", new ArrayList(),userVo).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.delFolder(zipPath);
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				String sql4 = "update expinfo set STATUS = '�ļ������쳣!' where ID = '"+uuid+"'";
				sess.createSQLQuery(sql4).executeUpdate();
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}	
	
	public String getZipPath() {
		//"D:/HZB//temp/zipload/"+uuid+"/";
		String path=getPath();
		String zipPath=path+"�����������_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
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
	
	public static void appendFileContent(String fileName, String content) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	// ɾ���ļ���
		// param folderPath �ļ�����������·��
		public static void delFolder(String folderPath) {
			try {
				delAllFile(folderPath); // ɾ����������������
				String filePath = folderPath;
				filePath = filePath.toString();
				java.io.File myFilePath = new java.io.File(filePath);
				myFilePath.delete(); // ɾ�����ļ���
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ɾ��ָ���ļ����������ļ�
		// param path �ļ�����������·��
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
					delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
					delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
					flag = true;
				}
			}
			return flag;
		}
		
}
