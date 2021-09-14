package com.insigma.siis.local.epsoft.config;

import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.insigma.odin.framework.webcontroller.OdinPlugIn;
import com.insigma.siis.local.epsoft.clearfile.ClearFileJob;
import com.insigma.siis.local.jftp.JFtpServer;
import com.insigma.siis.local.jtrans.HlPolingJob;
import com.lbs.cp.plugin.CachePlugIn;

/**
 * 初始化启动，放在strust_config中启动
 * 
 * @author gezh
 * 
 */
public class PubPlusIn implements PlugIn {
	Log log = LogFactory.getLog(PubPlusIn.class);
	public static ActionServlet servlet = new ActionServlet();
	public static ModuleConfig mConfig = null;

	/**
	 * 初始化全局变量
	 */
	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
		setServlet(arg0);
		log.info("初始化插件类...");
		new CachePlugIn().init(arg0, arg1);
		////这个初始化会查询aa26空表，但是不能注释，这里注释了人员信息和校验配置模块打不开。
		new OdinPlugIn().init(arg0, arg1);
		
		log.info("初始化全局变量...");
		AppConfigLoader.load();
		/*
		try {
			log.info("启动FTP服务...");
			JFtpServer.initFtpServer().start();
		} catch (FtpException e) {
			log.error("启动FTP服务失败",e);
		}
		*/
		if("1".equals(AppConfig.IS_HLPOLING)){//是否开启轮询上级下发目录 1为开启，0为不开启
			try{
				log.info("启动轮询下载上级下发目录服务...");
				HlPolingJob.initJob();
			}catch(Exception e){
				log.error("启动轮询下载上级下发目录服务失败",e);
			}
		}
		
		try{
			log.info("启动删除临时文件任务");
			ClearFileJob.initJob();
		}catch(Exception e){
			log.error("启动删除临时文件任务失败",e);
		}
	}

	public void destroy() {

	}

	public static ActionServlet getServlet() {
		return servlet;
	}

	public static void setServlet(ActionServlet servlet) {
		PubPlusIn.servlet = servlet;
	}

}
