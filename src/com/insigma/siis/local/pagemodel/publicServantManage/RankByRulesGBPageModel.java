package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

import org.hibernate.Hibernate;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class RankByRulesGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		//this.getExecuteSG().addExecuteCode("reShowMsg();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public  int  initX() throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01) sess.get(A01.class,a0000);
		if(a01!=null){
			this.getPageElement("a6506").setValue(a01.getA6506());
			this.getPageElement("a0120").setValue(a01.getA0120());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		String a6506 = this.getPageElement("a6506").getValue();
		String a0120 = this.getPageElement("a0120").getValue();
		try{
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class,a0000);
			if(a01==null){
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'进入管理信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			a01.setA0120(a0120);
			a01.setA6506(a6506);
			sess.update(a01);
			sess.flush();
			this.setMainMessage("保存成功！");
		}catch(Exception e){
			this.setMainMessage("保存失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}

}
