package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class PhotoThread implements Runnable {
	private String adress;
	private CommandUtil util;
	private List<String> list;
	private Statement stmt;
	private String uuid;
	private String user ;
	
	public PhotoThread(String adress, CommandUtil util, List list, String uuid,String user ) {
		this.adress = adress;
		this.util = util;
		this.list = list;
		this.stmt = stmt;
		this.user = user;
		
	}
	@Override
	public void run() {
		Connection conn = null;
		Statement stmt = null;
		
		String WINDOWS_remove_ALL="cmd /c move /y ";
		String photo_path_temp = adress.replace("/", "\\");
			for (String url : list) {
				String source = "\""+photo_path_temp +"\\"+ (url.replace("\\", "")) +"*.*\" ";
				String dir = ("\""+ AppConfig.PHOTO_PATH + url + "" +"\"").replace("\\\\", "\\");
				int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
				System.out.println(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
				
			}
			System.out.println("---------------------------------------------------------");
			File file = new File(photo_path_temp);
			String[] filelist = file.list();
			 if(filelist!=null && filelist.length > 0){
				for (String photoname : filelist) {
					String source = "\""+photo_path_temp +"\\"+ photoname+"\"" +" ";
					String dir = "\""+ AppConfig.PHOTO_PATH +"\\" + photoname.charAt(0) + ""+"\"" ;
					File file2 = new File(dir);
					if(!file2.exists()){
						file2.mkdirs();
					}
					int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
					System.out.println(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
				}
			}
			
			 try {
				 HBSession sess =  HBUtil.getHBSession();
				 conn = sess.connection();
				 stmt = conn.createStatement();
				 String sql1 =  "update PHOTO_INFO SET STATUS = '2' where  USERQ = '"+user+"'";
				stmt.executeUpdate(sql1);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(stmt != null){
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(conn != null){
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
	}

}
