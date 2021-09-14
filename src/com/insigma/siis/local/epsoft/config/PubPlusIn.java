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
 * ��ʼ������������strust_config������
 * 
 * @author gezh
 * 
 */
public class PubPlusIn implements PlugIn {
	Log log = LogFactory.getLog(PubPlusIn.class);
	public static ActionServlet servlet = new ActionServlet();
	public static ModuleConfig mConfig = null;

	/**
	 * ��ʼ��ȫ�ֱ���
	 */
	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
		setServlet(arg0);
		log.info("��ʼ�������...");
		new CachePlugIn().init(arg0, arg1);
		////�����ʼ�����ѯaa26�ձ����ǲ���ע�ͣ�����ע������Ա��Ϣ��У������ģ��򲻿���
		new OdinPlugIn().init(arg0, arg1);
		
		log.info("��ʼ��ȫ�ֱ���...");
		AppConfigLoader.load();
		/*
		try {
			log.info("����FTP����...");
			JFtpServer.initFtpServer().start();
		} catch (FtpException e) {
			log.error("����FTP����ʧ��",e);
		}
		*/
		if("1".equals(AppConfig.IS_HLPOLING)){//�Ƿ�����ѯ�ϼ��·�Ŀ¼ 1Ϊ������0Ϊ������
			try{
				log.info("������ѯ�����ϼ��·�Ŀ¼����...");
				HlPolingJob.initJob();
			}catch(Exception e){
				log.error("������ѯ�����ϼ��·�Ŀ¼����ʧ��",e);
			}
		}
		
		try{
			log.info("����ɾ����ʱ�ļ�����");
			ClearFileJob.initJob();
		}catch(Exception e){
			log.error("����ɾ����ʱ�ļ�����ʧ��",e);
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
