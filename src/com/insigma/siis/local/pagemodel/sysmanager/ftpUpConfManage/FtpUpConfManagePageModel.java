package com.insigma.siis.local.pagemodel.sysmanager.ftpUpConfManage;

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
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class FtpUpConfManagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("grid.dogridquery")
	public int dogridQuery(int start,int limit) throws RadowException{
		String sql = "select id,name,hostname,port,username,status from trans_config t  where t.type='0'";
		this.setSelfDefResData(this.pageQuery(sql,"SQL", -1,limit)); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 弹出新增窗口
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnAdd.onclick")
	public int openAddWin(String id)throws RadowException{
		this.setRadow_parent_data(null);
		this.openWindow("addWin", "pages.sysmanager.ftpUpConfManage.FtpUpConfModify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 弹出编辑窗口
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("editWin")
	public int openEditWin(String id)throws RadowException{
		this.setRadow_parent_data(id);
		this.openWindow("editWin", "pages.sysmanager.ftpUpConfManage.FtpUpConfModify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除FTP配置信息
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("delftp")
	public int doDelFtp(String id) throws RadowException{
		this.addNextEvent(NextEventValue.YES, "dodelete",id);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行删除操作吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 执行删除动作
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("dodelete")
	@Transaction
	public int dodelete(String id)throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("delete from trans_config where id='"+id+"'").executeUpdate();
		try {
			new LogUtil().createLog("6906", "TRANS_CONFIG", "", "", "FTP上行删除", new ArrayList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.setMainMessage("删除成功!");
		this.createPageElement("grid", ElementType.GRID, false).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 连通性测试
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("conntest")
	public int conntest(String id)throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		TransConfig tc = (TransConfig) sess.get(TransConfig.class, id);
		try {
			ZwhzFtpClient.conntest(tc);
		} catch (FtpException e) {
			 throw new RadowException(e.getMessage());
		}
		this.setMainMessage("连通测试成功!");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
