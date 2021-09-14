package com.insigma.siis.local.pagemodel.sysmanager.scene;

import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class SceneTestPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("grid5.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("toolBarBtn1.onclick")
	public int btn1Onclick(){
		CommonQueryBS.systemOut(4658456+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("toolBarBtn2.onclick")
	public int btn2Onclick(){
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addScene.onclick")
	public int addScene() throws RadowException{
		this.openWindow("saveScene", "pages.sysmanager.scene.AddScene");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public int deleteScene(){
		this.setMainMessage("È·¶¨ÒªÉ¾³ýÂð£¿");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("grid5.rowdbclick")
	@GridDataRange()
	public int updateScene() throws RadowException{
		this.openWindow("updateScene", "pages.sysmanager.scene.UpdateScene");
		this.setRadow_parent_data(this.getPageElement("grid5").getStringValue("sceneid"));
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid5.dogridquery")
	@EventDataRange("sceneQueryContent")
	public int grid5Event(int start,int limit) throws RadowException{
		try {
			List<Object> scenes = PrivilegeManager.getInstance().getISceneControl().queryByUser(PrivilegeManager.getInstance().getCueLoginUser(),start, limit, null);
			this.setSelfDefResData(this.getPageQueryData(scenes, start, limit));
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
		}
		return EventRtnType.SPE_SUCCESS;
	}

}
