package com.insigma.siis.local.pagemodel.sysmanager.app;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.entity.SmtApp;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.vo.AppVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class AppPageModel extends PageModel {

	@Override
	public int doInit() {
		this.setNextEventName("appgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btn_query.onclick")
	public int doQuery() throws RadowException {
		this.setNextEventName("appgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btn_create.onclick")
	public int createApp() throws RadowException {
		this.openWindow("operateWin", "pages.sysmanager.app.OperateWin");
		this.setRadow_parent_data("create");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogridgrant")
	public int update(String appid) throws RadowException {
		this.openWindow("operateWin", "pages.sysmanager.app.OperateWin");
		this.setRadow_parent_data(appid);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("appgrid.dogridquery")
	public int dogridQuery(int start, int limit) throws RadowException{
		String name = this.getPageElement("appName").getValue();
		String mess = this.getPageElement("otherMess").getValue();
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(!name.trim().equals("")){
			map.put("name", name.trim());
		}else if(!mess.trim().equals("")){
			map.put("mess", mess.trim());
		}else{
			map = null;
		}
		List apps = getApps(map);
		this.setSelfDefResData(this.getPageQueryData(apps, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("dogriddelete")
	public int delete(String appid) throws RadowException {
		this.addNextEvent(NextEventValue.YES, "dodelete",appid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行删除操作吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("dodelete")
	public int doDelete(String appid) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		SmtApp app = (SmtApp)sess.createQuery("from SmtApp where appid=:appid").setString("appid", appid).uniqueResult();
		sess.delete(app);
		sess.flush();
		this.getPageElement("appgrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private List getApps(HashMap params) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String hql = "from SmtApp ";
		if(params != null){
			if(params.get("name") != null){
				hql += " where appName like '%"+params.get("name")+"%'";
			}else{
				hql += " where parent like '%"+params.get("mess")+"%' or appCode like '%"+
					params.get("mess")+"%' or appTitle like '%"+
					params.get("mess")+"%' or appDesc like '%"+params.get("mess")+"%'";
			}
		}
		List list = sess.createQuery(hql).list();
		List apps = null;
		try {
			apps = PrivilegeUtil.getVOList(list,AppVO.class);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return apps;
	}

	@PageEvent("clean.onclick")
	@NoRequiredValidate
	public int clean() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

}
