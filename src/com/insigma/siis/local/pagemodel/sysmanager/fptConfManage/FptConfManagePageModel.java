package com.insigma.siis.local.pagemodel.sysmanager.fptConfManage;

import java.util.List;

import org.apache.ftpserver.ftplet.FtpException;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Fptconfig;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.config.AppConfigLoader;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jftp.JFtpServer;
import com.insigma.siis.local.jtrans.HlPolingJob;

public class FptConfManagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		List<Fptconfig> confs = (List<Fptconfig>) HBUtil.getHBSession().createQuery(" from Fptconfig").list();
		if(confs!=null && confs.size()>0){
			this.copyObjValueToElement(confs.get(0), this);
		} else {
			this.getPageElement("serverbaseurl").setValue(AppConfig.TRANS_SERVER_BASEURL);
			this.getPageElement("backupfile").setValue(AppConfig.LOCAL_BACKUP_FILE);
			this.getPageElement("localfile").setValue(AppConfig.LOCAL_FILE_BASEURL);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存FTP配置信息
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnSave.onclick")
	@Transaction
	public int dosaveFtpConf() throws RadowException{
		String id = null;
		try{
			HBSession sess = HBUtil.getHBSession();
			//保存更新 配置表信息
			Fptconfig conf= new Fptconfig();
			this.copyElementsValueToObj(conf, this);
			conf.setBackupfile(conf.getBackupfile().toUpperCase());
			conf.setLocalfile(conf.getLocalfile().toUpperCase());
			conf.setServerbaseurl(conf.getServerbaseurl().toUpperCase());
			if(conf.getFptconfigid()!=null && !conf.getFptconfigid().equals("")){
				sess.update(conf);
			} else {
				sess.save(conf);
			}
			//修改aa01信息
			sess.createSQLQuery("update aa01 set aaa005='"+conf.getServerbaseurl()+"' where aaa001='TRANS_SERVER_BASEURL'").executeUpdate();
//			sess.createSQLQuery("update aa01 set aaa005='"+conf.getServerport()+"' where aaa001='TRANS_SERVER_PORT'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+conf.getLocalfile()+"' where aaa001='LOCAL_FILE_BASEURL'").executeUpdate();
			sess.createSQLQuery("update aa01 set aaa005='"+conf.getBackupfile()+"' where aaa001='LOCAL_BACKUP_FILE'").executeUpdate();
			//修改appconfig信息
//			try {
//				AppConfig.TRANS_SERVER_PORT = Integer.valueOf(conf.getServerport()).intValue(); // FTP传输服务器端口
//			} catch (RuntimeException e) {
//				AppConfig.TRANS_SERVER_PORT = 21;
//			}
			AppConfig.TRANS_SERVER_BASEURL= conf.getServerbaseurl();//系统FTP服务根目录
			AppConfigLoader.setFtpPathList();//初始化FTP用户目录集
			AppConfig.LOCAL_FILE_BASEURL= conf.getLocalfile();//本地数据存放根目录
			AppConfig.LOCAL_BACKUP_FILE = conf.getBackupfile();//本地备份文件根目录
			//重起ftp 
			/*try {
				if(!JFtpServer.ftpServer.isStopped()){
					JFtpServer.ftpServer.stop();
				}
				JFtpServer.initFtpServer().start();
			} catch (FtpException e) {
				e.printStackTrace();
			}
			
			if("1".equals(AppConfig.IS_HLPOLING)){//是否开启轮询上级下发目录 1为开启，0为不开启
				try{
					HlPolingJob.initJob();
				}catch(Exception e){
					e.printStackTrace();
				}
			}*/
//			new LogUtil().createLog("6904", "TRANS_CONFIG", "", "", "FTP上行新增", new Map2Temp().getLogInfo(new TransConfig(), tc));
			this.setMainMessage("保存FTP参数配置信息成功!");
			return EventRtnType.NORMAL_SUCCESS;
		}catch(Exception e){
			throw new RadowException("保存FTP参数配置信息失败:"+e.getMessage());
		}
	}

}
