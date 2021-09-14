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

public class AddScenePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btnSave.onclick")
	@Transaction
	public int btnSaveOnclick() throws RadowException{
		SceneVO scene = new SceneVO();
		PMPropertyCopyUtil.copyElementsValueToObj(scene,this);
		try {
			boolean success = PrivilegeManager.getInstance().getISceneControl().saveScene(scene);
			if(success){
				this.closeCueWindow("saveScene");
				Grid grid = (Grid) this.createPageElement("grid5", ElementType.GRID, true);
				grid.reload();
			}
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btnClose.onclick")
	@NoRequiredValidate(true)
	public int btnCloseOnclick(){
		this.closeCueWindowByYes("saveScene");
		this.setMainMessage("ȷ�Ϲرգ�");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

}
