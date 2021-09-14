package com.insigma.siis.local.pagemodel.sysmanager.scene;

import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class UpdateSceneWindowPageModel extends PageModel {
	
	private static String owner = null;
	private static String sceneid = null;
	
	@Override
	public int doInit() throws RadowException {
		this.getPageElement("updatename").setValue(this.getRadow_parent_data());
		String	updatename = this.getPageElement("updatename").getValue();
		try {
			List list = PrivilegeManager.getInstance().getISceneControl().queryByName(updatename, true);
			SceneVO scene = (SceneVO) list.get(0);
			owner = scene.getOwner();
			sceneid = scene.getSceneid();
			String description = scene.getDescription();
			if(description==null){
				this.getPageElement("updatedescription").setValue("");
			}else{
				this.getPageElement("updatedescription").setValue(description);
			}
			String status = scene.getStatus();
			this.getPageElement("updatestatus").setValue(status);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Transaction
	@PageEvent("update.onclick")
	public int updateOnClick() throws RadowException{
		String status = null;
		String description = null;
		String name = null;
		description = this.getPageElement("updatedescription").getValue();
		name = this.getPageElement("updatename").getValue();
		status = this.getPageElement("updatestatus").getValue();
		SceneVO scene = new SceneVO();
		scene.setSceneid(sceneid);
		scene.setOwner(owner);
		scene.setName(name);
		scene.setStatus(status);
		if(description==null || description.trim().equals("")){
			scene.setDescription("");
		}else{
			scene.setDescription(description);
		}
		try {
			PrivilegeManager.getInstance().getISceneControl().updateScene(scene);
		} catch (PrivilegeException e) {
			this.isShowMsg=true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		this.closeCueWindow("updateSceneWin");
		this.createPageElement("scenegrid", "grid", true).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
}
