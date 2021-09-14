package com.insigma.siis.local.pagemodel.sysmanager.ftpUserManage;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ftpserver.ftplet.FtpException;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jftp.JFtpServer;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;

/**
 * Ӧ����FTP�û���Ϣ����
 * @author gezh
 *
 */
public class FtpUserManagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ѯ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("grid.dogridquery")
	public int dogridQuery(int start,int limit) throws RadowException{
		String sql = "select b.b0101 as depict, a.userid, a.homedirectory,  a.enableflag, a.writepermission, a.idletime,  a.uploadrate / 1024 uploadrate, a.downloadrate / 1024 downloadrate,  a.maxloginnumber,  a.maxloginperip,  a.userid as opid from ftp_user a,b01 b where a.depict=b.b0111  order by depict asc";
//		this.setSelfDefResData(this.pageQuery(sql,"SQL", -1,limit)); 
		this.pageQuery(sql,"SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ������������
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnAdd.onclick")
	public int openAddWin()throws RadowException{
		this.setRadow_parent_data(null);
		this.openWindow("editWin", "pages.sysmanager.ftpUserManage.FtpUserModify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �����༭����
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("editWin")
	public int openEditWin(String userid)throws RadowException{
		this.setRadow_parent_data(userid);
		this.openWindow("editWin", "pages.sysmanager.ftpUserManage.FtpUserModify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ɾ���û���Ϣ
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("delftp")
	public int doDelFtp(String userid) throws RadowException{
		this.addNextEvent(NextEventValue.YES, "dodelete",userid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ��ɾ��������");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ִ��ɾ������
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("dodelete")
	@Transaction
	public int dodelete(String userid)throws RadowException{
		try {
			JFtpServer.deletUser(userid);
			try {
				new LogUtil().createLog("6903", "FTP_USER", "", "", "FTP�û�ɾ��", new ArrayList());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.setMainMessage("ɾ���ɹ�!");
			this.createPageElement("grid", ElementType.GRID, false).reload();
		} catch (FtpException e) {
			throw new RadowException("ɾ���û�ʧ��:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
