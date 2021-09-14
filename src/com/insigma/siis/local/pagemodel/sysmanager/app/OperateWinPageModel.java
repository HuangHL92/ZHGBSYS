package com.insigma.siis.local.pagemodel.sysmanager.app;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtApp;
import com.insigma.odin.framework.privilege.vo.AppVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class OperateWinPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String appip = this.getRadow_parent_data();
		if(!appip.equals("create")){
			try {
				AppVO app = PrivilegeManager.getInstance().getIAppControl().queryById(appip);
				super.autoFillPage(app, false);
			} catch (PrivilegeException e) {
				throw new RadowException(e.getMessage());
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("createapp")
	public int create() throws RadowException {
		appNameOnChonge(null);
		AppVO app = new AppVO();
		app.setAppCode(this.getPageElement("appCode").getValue());
		app.setAppDesc(this.getPageElement("appDesc").getValue());
		app.setAppName(this.getPageElement("appName").getValue());
		app.setAppTitle(this.getPageElement("appTitle").getValue());
		app.setAppType(this.getPageElement("appType").getValue());
		app.setParent(this.getPageElement("parent").getValue());
		try {
			PrivilegeManager.getInstance().getIAppControl().save(app);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.createPageElement("appgrid", ElementType.GRID, true).reload();
		this.setMainMessage("创建成功");
		this.closeCueWindowByYes("operateWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("updateapp")
	public int update() throws RadowException {
		String appid = this.getPageElement("appid").getValue();
		appNameOnChonge(appid);
		try {
			HBSession sess = HBUtil.getHBSession();
			SmtApp app = (SmtApp)sess.createQuery("from SmtApp where appid=:appid").setString("appid", appid).uniqueResult();
			app.setAppCode(this.getPageElement("appCode").getValue());
			app.setAppDesc(this.getPageElement("appDesc").getValue());
			app.setAppName(this.getPageElement("appName").getValue());
			app.setAppTitle(this.getPageElement("appTitle").getValue());
			app.setAppType(this.getPageElement("appType").getValue());
			app.setParent(this.getPageElement("parent").getValue());
			sess.update(app);
			sess.flush();
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.createPageElement("appgrid", ElementType.GRID, true).reload();
		this.setMainMessage("更新成功");
		this.closeCueWindowByYes("operateWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	public int save() throws RadowException {
		String appip = this.getRadow_parent_data();
		if(appip.equals("create")){
			this.setNextEventName("createapp");
		}else{
			this.setNextEventName("updateapp");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private int appNameOnChonge(String appid) throws RadowException{
		String appName = this.getPageElement("appName").getValue();
		HBSession sess = HBUtil.getHBSession();
		SmtApp smtapp = (SmtApp)sess.createQuery("from SmtApp where appName=:appName").setString("appName", appName).uniqueResult();
		if(smtapp != null){
			if(appid != null){
				if(appid.equals(smtapp.getAppid())){
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			throw new RadowException("应用名称已存在，请重新输入");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
