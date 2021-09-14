package com.insigma.siis.local.pagemodel.sysmanager.ftpUserManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jftp.JFtpServer;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class FtpUserModifyPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String ftpid = this.getRadow_parent_data();
		if(ftpid!=null && !"".equals(ftpid)){
			HBSession sess = HBUtil.getHBSession();
			Ftpuser fu = (Ftpuser) sess.get(Ftpuser.class, ftpid);
			fu.setUploadrate(fu.getUploadrate()/1024);
			fu.setDownloadrate(fu.getDownloadrate()/1024);
			this.copyObjValueToElement(fu, this);
			String depictCn = ""; 
			try {
				depictCn= HBUtil.getValueFromTab("b0101", "b01", " b0111='"+fu.getDepict()+"'");
			} catch (AppException e) {
			}
			this.getPageElement("depict_combo").setValue(depictCn);
			this.createPageElement("userid", ElementType.TEXT, false).setDisabled(true);
			
		}else{
			this.createPageElement("userid", ElementType.TEXT, false).setDisabled(false);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	@PageEvent("btnSave.onclick")
//	@Transaction
	public int dosaveFtpConf() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		Ftpuser tmpfu = new Ftpuser();
		try{
			this.copyElementsValueToObj(tmpfu, this);
			Ftpuser fu = new Ftpuser();
			fu = (Ftpuser) sess.get(Ftpuser.class, tmpfu.getUserid());
			if(fu==null){
				JFtpServer.addUser(tmpfu.getUserid(), tmpfu.getUserpassword(), Boolean.valueOf(tmpfu.getEnableflag()), Boolean.valueOf(tmpfu.getWritepermission()), tmpfu.getIdletime());
				fu = (Ftpuser) sess.get(Ftpuser.class, tmpfu.getUserid());
				fu.setDepict(tmpfu.getDepict());
				fu.setUploadrate(tmpfu.getUploadrate()*1024);
				fu.setDownloadrate(tmpfu.getDownloadrate()*1024);
				fu.setMaxloginnumber(tmpfu.getMaxloginnumber());
				fu.setMaxloginperip(tmpfu.getMaxloginperip());
				sess.saveOrUpdate(fu);
				new LogUtil().createLog("6901", "TRANS_CONFIG", "", "", "FTP用户新增", new Map2Temp().getLogInfo(new Ftpuser(), tmpfu));
			} else {
				new LogUtil().createLog("6902", "TRANS_CONFIG", "", "", "FTP用户修改", new Map2Temp().getLogInfo(fu, tmpfu));
				JFtpServer.addUser(tmpfu.getUserid(), tmpfu.getUserpassword(), Boolean.valueOf(tmpfu.getEnableflag()), Boolean.valueOf(tmpfu.getWritepermission()), tmpfu.getIdletime());
				fu = (Ftpuser) sess.get(Ftpuser.class, tmpfu.getUserid());
				fu.setDepict(tmpfu.getDepict());
				fu.setUploadrate(tmpfu.getUploadrate()*1024);
				fu.setDownloadrate(tmpfu.getDownloadrate()*1024);
				fu.setMaxloginnumber(tmpfu.getMaxloginnumber());
				fu.setMaxloginperip(tmpfu.getMaxloginperip());
				sess.saveOrUpdate(fu);
			}
			sess.flush();
			this.createPageElement("grid","grid",true).reload();
			this.setMainMessage("保存FTP用户信息成功!");
			this.closeCueWindowByYes("editWin");
			return EventRtnType.NORMAL_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			try {
				sess.createSQLQuery("delete from ftp_user where userid ='"+tmpfu.getUserid()+"'").executeUpdate();
				sess.flush();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw new RadowException("保存FTP用户信息失败:"+e.getMessage());
		}
	}
}
