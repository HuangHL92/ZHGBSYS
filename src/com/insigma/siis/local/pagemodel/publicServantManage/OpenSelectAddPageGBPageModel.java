package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

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
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A81;
import com.insigma.siis.local.business.entity.A82;
import com.insigma.siis.local.business.entity.A84;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class OpenSelectAddPageGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public  int  initX() throws RadowException{
		String a0000=this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		List<A82> list = sess.createQuery("from A82 where a0000='"+a0000+"'").list();
		A82 a82 = new A82();
		if(list.size()!=0){
			a82 = list.get(0);
		}
			
		this.getPageElement("a02191").setValue(a82.getA02191());
		this.getPageElement("a29301").setValue(a82.getA29301());
		this.getPageElement("a29304").setValue(a82.getA29304());
		this.getPageElement("a29044").setValue(a82.getA29044());
		this.getPageElement("a29307").setValue(a82.getA29307());
		this.getExecuteSG().addExecuteCode("Seta29307()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		try {
			HBSession session = HBUtil.getHBSession();
			A01 a01 = (A01) session.get(A01.class, a0000);
			if(a01==null){
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'公开遴选信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a02191 = this.getPageElement("a02191").getValue();
			String a29301 = this.getPageElement("a29301").getValue();
			String a29304 = this.getPageElement("a29304").getValue();
			String a29044 = this.getPageElement("a29044").getValue();
			String a29307 = this.getPageElement("a29307").getValue();
			A82 a82 = (A82) session.createQuery("from A82 where a0000='"+a0000+"'").uniqueResult();
			boolean isAdd = true;
			A82 a82_old = null;
			if(a82==null){//增加
				a82 = new A82();
				a82.setA0000(a0000);
			}else{
				isAdd = false;
				a82_old = a82.clone();
			}//修改日志
			a82.setA02191(a02191);
			a82.setA29301(a29301);
			a82.setA29304(a29304);
			a82.setA29044(a29044);
			a82.setA29307(a29307);
			
			if(isAdd){
				applog.createLogNew("","公开遴选新增","公开遴选",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A82(),a82));
			}else{
				applog.createLogNew("","公开遴选修改","公开遴选",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a82_old,a82));
			}
			session.saveOrUpdate(a82);
			session.flush();
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'公开遴选信息集','保存成功！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'公开遴选信息集','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
}
	
	
	
	
