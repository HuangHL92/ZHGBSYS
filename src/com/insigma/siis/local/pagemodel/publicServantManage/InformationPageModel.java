package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class InformationPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
    
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException{
		
		String ids = this.getPageElement("id").getValue();
		String[] idarr = ids.split("@@@");
		String id = null;
		String a0000 = null;
		if(idarr.length==2){
			 id = idarr[0];
			 a0000 = idarr[1];
			 this.getPageElement("pid").setValue(id);
		}else{
			 a0000 = idarr[0];
		}
		this.getPageElement("a0000").setValue(a0000);
		if(id==null||"".equals(id)){
			
		}else{
			
			HBSession sess = HBUtil.getHBSession();
			Supervision supervision = (Supervision)sess.get(Supervision.class, id);
			PMPropertyCopyUtil.copyObjValueToElement(supervision, this);  //将值赋值到页面
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
			
	/**
	 * 保存
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save")
	public int save() throws RadowException, Exception {
		/*String ids = this.getPageElement("id").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();
		String[] idarr = ids.split("@@@");
		String id = null;
		String a0000 = null;
		if(idarr.length==2){
			 id = idarr[0];
			 a0000 = idarr[1];
		}else{
			 a0000 = idarr[0];
		}*/
		String id = this.getPageElement("pid").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
		Supervision supervision = new Supervision();
		HBSession sess = HBUtil.getHBSession();
		if(id==null||"".equals(id)){
			PMPropertyCopyUtil.copyElementsValueToObj(supervision, this);
			supervision.setId(UUID.randomUUID().toString());
			supervision.setA0000(a0000);
			sess.save(supervision);
			sess.flush();
			
			this.getExecuteSG().addExecuteCode("saveCallBack('新增成功');");
			//this.setMainMessage("保存成功");
		}else{
			supervision = (Supervision)sess.get(Supervision.class, id);
			PMPropertyCopyUtil.copyElementsValueToObj(supervision, this);
			sess.update(supervision);
			sess.flush();
			this.getExecuteSG().addExecuteCode("saveCallBack('修改成功');");
			//this.setMainMessage("修改成功");
		}		
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
