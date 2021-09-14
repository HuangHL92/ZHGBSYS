package com.insigma.siis.local.pagemodel.sysmanager.ftpUpConfManage;

import java.util.ArrayList;

import org.codehaus.jackson.map.BeanProperty;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class FtpUpConfModifyPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String ftpid = this.getRadow_parent_data();
		if(ftpid!=null && !"".equals(ftpid)){
			HBSession sess = HBUtil.getHBSession();
			TransConfig tc=  (TransConfig) sess.get(TransConfig.class, ftpid);
			this.copyObjValueToElement(tc, this);
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
			TransConfig tc= new TransConfig();
			this.copyElementsValueToObj(tc, this);
			id = tc.getId();
			tc.setId("".equals(id)?null:id);
			if(id!=null && !"".equals(id)){
				TransConfig tc_old = (TransConfig) sess.get(TransConfig.class, id);
				new LogUtil().createLog("6905", "TRANS_CONFIG", "", "", "FTP上行修改", new Map2Temp().getLogInfo(tc_old, tc));
				tc_old.setHostname(tc.getHostname());
				tc_old.setName(tc.getName());
				tc_old.setPassword(tc.getPassword());
				tc_old.setPort(tc.getPort());
				tc_old.setStatus(tc.getStatus());
				tc_old.setUsername(tc.getUsername());
				tc_old.setType("0");
				sess.saveOrUpdate(tc_old);
			}else{
				new LogUtil().createLog("6904", "TRANS_CONFIG", "", "", "FTP上行新增", new Map2Temp().getLogInfo(new TransConfig(), tc));
				tc.setType("0");
				sess.saveOrUpdate(tc);
			}
			this.createPageElement("grid","grid",true).reload();
			this.setMainMessage("保存FTP配置信息成功!");
			if(!"".equals(id) && id!=null){
				this.closeCueWindowByYes("editWin");
			}else{
				this.closeCueWindowByYes("addWin");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}catch(Exception e){
			throw new RadowException("保存FTP配置信息失败:"+e.getMessage());
		}
	}
}
