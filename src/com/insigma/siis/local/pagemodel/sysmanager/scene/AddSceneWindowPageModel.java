package com.insigma.siis.local.pagemodel.sysmanager.scene;

import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class AddSceneWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		return  EventRtnType.NORMAL_SUCCESS;
	}

	
	@PageEvent("name.onchange")
	@NoRequiredValidate
	public int nameOnChonge() throws RadowException{
		String name = this.getPageElement("name").getValue();
		try {
			List list = PrivilegeManager.getInstance().getISceneControl().queryByName(name, true);
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("该场景名已经存在");
		} catch (PrivilegeException e) {
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("saveSceneBtn.onclick")
	public int saveSceneBtnOnClick() throws RadowException{
		String name = this.getPageElement("name").getValue();
		String description = this.getPageElement("description").getValue();
		String status = this.getPageElement("status").getValue();
		SceneVO scene = new SceneVO();
		
		if(status==null){
			scene.setStatus("1");
		}else
		{
			scene.setStatus(status);
		}
		scene.setName(name);
		if(description==null){
			scene.setDescription("");
		}else{
			scene.setDescription(description);
		}
		try {
			PrivilegeManager.getInstance().getISceneControl().saveScene(scene);
			this.createPageElement("scenegrid", "Grid", true).reload();
		} catch (PrivilegeException e) {
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		this.closeCueWindow("addSceneWin");
		this.createPageElement("scenegrid", "grid", true).reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
