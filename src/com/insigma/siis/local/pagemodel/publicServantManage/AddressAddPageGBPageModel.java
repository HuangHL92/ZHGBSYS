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
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AddressAddPageGBPageModel extends PageModel {

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
		List<A37> list = sess.createQuery("from A37 where a0000='"+a0000+"'").list();
		A37 a37 = new A37();
		if(list.size()!=0){
			a37 = list.get(0);
		}
		this.getPageElement("a3701").setValue(a37.getA3701());
		this.getPageElement("a3707a").setValue(a37.getA3707a());
		this.getPageElement("a3707b").setValue(a37.getA3707b());
		this.getPageElement("a3707c").setValue(a37.getA3707c());
		this.getPageElement("a3711").setValue(a37.getA3711());
		this.getPageElement("a3721").setValue(a37.getA3721());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		try {
			HBSession session = HBUtil.getHBSession();
			A01 a01 = (A01) session.get(A01.class, a0000);
			if(a01==null){
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'住址通讯信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a3701 = this.getPageElement("a3701").getValue();
			String a3707a = this.getPageElement("a3707a").getValue();
			String a3707b = this.getPageElement("a3707b").getValue();
			String a3707c = this.getPageElement("a3707c").getValue();
			String a3711 = this.getPageElement("a3711").getValue();
			String a3721 = this.getPageElement("a3721").getValue();
			A37 a37 = (A37) session.createQuery("from A37 where a0000='"+a0000+"'").uniqueResult();
			if(a37==null){//增加日志
				a37 = new A37();
				a37.setA0000(a0000);
			}else{//修改日志
				
			}
			a37.setA3701(a3701);
			a37.setA3707a(a3707a);
			a37.setA3707b(a3707b);
			a37.setA3707c(a3707c);
			a37.setA3711(a3711);
			a37.setA3721(a3721);
			a37.setA0000(a0000);
			session.saveOrUpdate(a37);
			session.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}
