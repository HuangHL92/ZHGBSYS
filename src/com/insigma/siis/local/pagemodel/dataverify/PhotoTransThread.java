package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class PhotoTransThread implements Runnable {
	
	private String uuid;
	
    public PhotoTransThread(String uuid) {
        this.uuid = uuid;
    }

	@Override
	public void run() {
		String imprecordid = uuid;								// 导入记录id
		String process_run = "1";								// 导入过程序号
		HBSession sess = HBUtil.getHBSession();
		try {
			Connection conn = sess.connection();
			Object obj = sess.createSQLQuery("select count(1) from A57").uniqueResult();
			int time = 1;
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				time = ((BigInteger) obj).intValue()/ 500 + (((BigInteger) obj).intValue() % 500 != 0 ? 1 : 0);
			} else {
				time = ((BigDecimal) obj).intValue()/ 500 + (((BigDecimal) obj).intValue() % 500 != 0 ? 1 : 0);
			}
			int all = 0;
			for (int i = 0; i < time; i++) {
				all++;
				Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(500);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE);  
				CommonQueryBS.systemOut("neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				ResultSet rs = null;
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					rs = stmt.executeQuery("select a0000,a5714,photoname,photodata,photopath from A57 " +
							"order by a0000 limit " + (500*i) +",500" );
				} else {
					rs = stmt.executeQuery("select a0000,a5714,photoname,photodata,photopath from " +
							"( select a0000,a5714,photoname,photodata,photopath,rownum rn from " +
							"(select a0000,a5714,photoname,photodata,photopath from A57 order by a0000) " +
							"where rownum<=" + (500*(i+1))+ " ) where rn>=" + (500*i));
				}
				
				CommonQueryBS.systemOut("neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				if(rs!=null){
					byte[] Buffer = new byte[4096];  
					while (rs.next()) {
						String a0000 = rs.getString("a0000");
						String phoname = rs.getString("photoname");
						Blob Photodata = rs.getBlob("photodata");
//						CommonQueryBS.systemOut(phoname.lastIndexOf("."));
//						String houzhui = (phoname!=null&&!phoname.equals(""))?phoname.substring(phoname.lastIndexOf(".")):"";
//						String photoname = a0000 +"." +(houzhui.equals("")?"jpg":"");
						String photoname = a0000 +"." +"jpg";
						String photopath = "";
					    if (a0000.length() >= 2) {
					    	String str = a0000.substring(0, 2);
					    	if(PhotosUtil.isLetterDigit(str)){
					    		photopath = a0000.charAt(0)+"/"+a0000.charAt(1)+"/";
					    	} else {
					    		photopath = a0000.charAt(0)+"/";
					    	}
						} else if (a0000.length() == 1) {
							photopath = a0000+"/";
						}
						if(a0000!=null && !a0000.equals("") && Photodata!=null){
							File f = new File(PhotosUtil.PHOTO_PATH + photopath + photoname);
							FileOutputStream fos = new FileOutputStream(f);  
							InputStream is = rs.getBinaryStream("photodata");  
							int size = 0;  
							while((size = is.read(Buffer)) != -1){  
								fos.write(Buffer,0,size);  
							}
							fos.close();
							is.close();
						}
						Statement stmt2 = conn.createStatement();
						stmt2.execute("update A57 set photoname='"+photoname+"',photopath='"+photopath+"' where a0000='"+a0000+"'");
						stmt2.close();
					}
				}
				rs.close();
				stmt.close();
				if(all%5000==0){
					System.gc();
				}
			}
			CommonQueryBS.systemOut("neicun1："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			conn.close();
			CommonQueryBS.systemOut("neicun2："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			System.gc();
			CommonQueryBS.systemOut("neicun3："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			KingbsconfigBS.saveImpDetail(process_run ,"2","完成",imprecordid);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
		}

	}
	
	private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
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
