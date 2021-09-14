package com.insigma.siis.local.pagemodel.sysmanager.scene;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;

public class UpdateScenePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		//SceneVO scene = new SceneVO();
		String sceneId = this.getRadow_parent_data();
//		try {
//			PrivilegeManager.getInstance().getISceneControl().querySceneById(sceneId);
//		} catch (PrivilegeException e) {
//			e.printStackTrace();
//			this.setMainMessage("初始化失败"+e.getMessage());
//			this.closeCueWindow("updateScene");
//			return EventRtnType.FAILD;
//		}
		Grid grid =(Grid) this.createPageElement("grid5", ElementType.GRID, true);
		this.getPageElement("sceneid").setValue(grid.getChildElementById("sceneid").getValue());
		this.getPageElement("name").setValue(grid.getChildElementById("name").getValue());
		this.getPageElement("description").setValue(grid.getChildElementById("description").getValue());
		this.getPageElement("onwer").setValue(grid.getChildElementById("onwer").getValue());
		this.getPageElement("status").setValue(grid.getChildElementById("status").getValue());
		/*
		this.getPageElement("id").setValue(grid.getStringValue("sceneid"));
		
		this.getPageElement("name").setValue(grid.getStringValue("name"));
		this.getPageElement("description").setValue(grid.getStringValue("description"));
		this.getPageElement("owner").setValue(grid.getStringValue("onwer"));
		this.getPageElement("status").setValue(grid.getStringValue("status"));
		this.getPageElement("createDate").setValue(grid.getStringValue("createDate"));
		
	
	*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("btnUpdate.onclick")
	@Transaction
	public int updateScene() throws RadowException{
		SceneVO scene = new SceneVO();
		PMPropertyCopyUtil.copyElementsValueToObj(scene,this);
		boolean success;
		try {
			success = PrivilegeManager.getInstance().getISceneControl().updateScene(scene);
			this.closeCueWindow("updateScene");
			Grid grid = (Grid)this.createPageElement("grid5", ElementType.GRID, true);
			grid.reload();
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage("更新失败"+e.getMessage());
			this.closeCueWindow("updateScene");
			return EventRtnType.FAILD;
		}
		return  EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btnClose.onclick")
	@NoRequiredValidate
	public int btnClose(){
		this.closeCueWindowByYes("updateScene");
		this.setMainMessage("确定取消此操作？");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
