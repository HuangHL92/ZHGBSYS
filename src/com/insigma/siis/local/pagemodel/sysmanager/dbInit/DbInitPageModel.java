package com.insigma.siis.local.pagemodel.sysmanager.dbInit;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.config.AppConfigLoader;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
/**
 * 
 * @author zhaoyd
 *
 */
public class DbInitPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("confirmonclick")
	@AutoNoMask
	@Synchronous(true)
	public int confirm(String value){
		try {
			String username = SysManagerUtils.getUserloginName();
			if(username==null||!"system".equals(username)){
				this.setMainMessage("您没有权限！");
				return EventRtnType.FAILD;
			} 
			//先删除临时表
			/*String temps = "SELECT IMP_TEMP_TABLE FROM imp_record";
			List list = HBUtil.getHBSession().createSQLQuery(temps).list();
			if(list!=null && list.size()>0){
				for(Object obj : list){
					if(obj!=null){
						deleteImpTable(obj+"");
					}
				}
			}*/
			if("1".equals(value)){//仅清除人员信息
				CallableStatement c=HBUtil.getHBSession().connection().prepareCall("{call CLEAR_PERSON}");
				c.execute();
				PhotosUtil.removDireCmd(PhotosUtil.PHOTO_PATH);
				//清除缓存  HZB temp upload   HZB temp zipload
				removDir();
				AppConfigLoader.initPath();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','初始化成功!',function(){parent.Ext.WindowMgr.getActive().close();});");
				System.out.println("初始化成功——清除人员信息，清除时间： "+DateUtil.getTime());
			}
			if("2".equals(value)){//清除所有信息
				CallableStatement c=HBUtil.getHBSession().connection().prepareCall("{call clear_db}");
				c.execute();
				PhotosUtil.removDireCmd(PhotosUtil.PHOTO_PATH);
				//清除缓存  HZB temp upload   HZB temp zipload
				removDir();
				AppConfigLoader.initPath();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','初始化成功!',function(){parent.Ext.WindowMgr.getActive().close();});");		
				System.out.println("初始化成功——清除所有信息，清除时间： "+DateUtil.getTime());
			}
			if("3".equals(value)){
				Connection connection = HBUtil.getHBSession().connection();
				CallableStatement c=connection.prepareCall("{call CLEAR_ORGANIZATION_PERSON}");
				c.execute();
				PhotosUtil.removDireCmd(PhotosUtil.PHOTO_PATH);
				// 清除缓存  HZB temp upload   HZB temp zipload
				removDir();
				AppConfigLoader.initPath();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','初始化成功!',function(){parent.Ext.WindowMgr.getActive().close();});");		
				System.out.println("初始化成功——清除机构和人员信息，清除时间： "+DateUtil.getTime());
			}
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化失败！");
			return EventRtnType.FAILD;
		}
		
	}
	
	@PageEvent("cancle.onclick")
	public void cancle(){
		
	}
	
	/**
	 * 使用命令删除导入文件夹
	 */
	public static void removDir (){
		CommandUtil util = new CommandUtil();
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_DEL_ALL="rm -fr ";
		String WINDOWS_DEL_ALL="cmd /c rmdir /S /Q ";
		try {
			String dir = "\"" + AppConfig.HZB_PATH + "/temp/upload" ;
			String dir2 = "\"" + AppConfig.HZB_PATH + "/temp/zipload" ;
			String dir3 = "\"" + AppConfig.HZB_PATH + "/zhgboutputfiles" ;
			if(osname.equals("Linux")){ //需确认
				util.executeCommand(LINUX_DEL_ALL + dir);
				util.executeCommand(LINUX_DEL_ALL + dir2);
			} if(osname.contains("Windows")){
				util.executeCommand(WINDOWS_DEL_ALL + (dir.replace("/", "\\")));
				util.executeCommand(WINDOWS_DEL_ALL + (dir2.replace("/", "\\")));
				util.executeCommand(WINDOWS_DEL_ALL + (dir3.replace("/", "\\")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteImpTable(String tableExt) {
		String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
				"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
				"I_E", "B_E","A60", "A61", "A62", "A63", "A64","A05", "A68", "A69", "A71" ,"A99Z1" };
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = sess.connection();
			stmt = conn.createStatement();
			for (int i = 0; i < tables.length; i++) {
				stmt = conn.createStatement();
				try {
					stmt.execute("drop table "+tables[i] +tableExt);
				} catch (Exception e) {
				}
			}
			
			stmt.close();
			conn.close();
		} catch (Exception e) {
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