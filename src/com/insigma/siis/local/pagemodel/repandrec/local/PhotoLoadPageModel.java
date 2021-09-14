package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.pagemodel.dataverify.PhotoThread;

public class PhotoLoadPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}
	
	@PageEvent("fenfa.onclick")
	public int Imp(String name) throws RadowException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String id = this.getPageElement("adress").getValue();
		if(id==null || id.equals("")){
			this.setMainMessage("路径不能为空,请重新输入.");
			return EventRtnType.NORMAL_SUCCESS;
		}
		File file = new File(id);
		if(file.exists()){
			HBSession sess =  HBUtil.getHBSession();
			conn = sess.connection();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String user = SysUtil.getCacheCurrentUser().getId();
			try {
				stmt = conn.createStatement();
				String sql = "insert into PHOTO_INFO (UUID,STATUSNAME,STATUS,USERQ) values('"+uuid+"','准备','0','"+user+"') ";
				stmt.executeUpdate(sql);
				String sql4 = "select STATUS from PHOTO_INFO where USERQ = '"+user+"'";
				rs = stmt.executeQuery(sql4);
				 if(rs.next()){
					System.out.println(rs.getString(1));
					if(rs.getString(1).equals("0")||rs.getString(1).equals("2")){
						this.getPageElement("user").setValue(user);
						CommandUtil util = new CommandUtil();
						List<String> list = initPath();								//初始化路径 包括 0/1/,0/2/,A/B/...Z/Z
						String sql5 = "update PHOTO_INFO SET STATUS = '1' where  USERQ = '"+user+"'";
						stmt.executeUpdate(sql5);
						this.setMainMessage("请稍候...");
						PhotoThread thr = new PhotoThread(id,util,list,uuid,user); 
						new Thread(thr).start();
					}else{
						this.setMainMessage("您的图片正在分发,请稍候...");
						
					}
				}
				this.getExecuteSG().addExecuteCode("refresh()");
			} catch (SQLException e1) {
				e1.printStackTrace();
				try {
					stmt = conn.createStatement();
//					String sql3 = "update PHOTO_INFO SET STATUS = '0' where  UUID = '"+uuid+"'";
					String sql3 = "delete fronm PHOTO_INFO";
					stmt.executeUpdate(sql3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.setMainMessage("图片分发异常!");
			}finally{
				if(rs != null){
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
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
					}
				}
			}
		}else{
			this.setMainMessage("请检查路径，文件不存在！");
		}
		
		return EventRtnType.NORMAL_SUCCESS;

	}
	@PageEvent("btnsx")
	public int btnsxOnClick()throws RadowException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			HBSession sess =  HBUtil.getHBSession();
			conn = sess.connection();
			stmt = conn.createStatement();
			String user = this.getPageElement("user").getValue();
			String sql5 = "select STATUS from PHOTO_INFO where USERQ = '"+user+"' ";
//			String sql6 = "delete from  photo_info  ";
			rs = stmt.executeQuery(sql5);
			while(rs.next()){
				if(rs.getString(1).equals("2")){
					this.getExecuteSG().addExecuteCode("close1()");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	private static List<String> initPath() {
		String[] keys= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"
				,"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
				,"Y","Z"};	
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys.length; j++) {
				String osname = System.getProperties().getProperty("os.name");
				if(osname.equals("Linux")){ //需确认
					String url = "/" + keys[i] + "/" +keys[j] +"/";
					list.add(url);
				} if(osname.contains("Windows")){
					String url = "\\" + keys[i] + "\\" +keys[j] +"\\";
					list.add(url);
				}
			}
		}
		return list;
	}
	

}
