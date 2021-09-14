package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.Attribute;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AttributeAddPageGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000=this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		List<Attribute> list = sess.createQuery("from Attribute where a0000='"+a0000+"'").list();
		Attribute attribute = new Attribute();
		if(list.size()!=0){
			attribute = list.get(0);
		}else{
			this.copyElementsValueToObj(attribute, this);
			attribute.setId(UUID.randomUUID().toString());
			attribute.setA0000(a0000);
			sess.saveOrUpdate(attribute);
			sess.flush();
		}
		PMPropertyCopyUtil.copyObjValueToElement(attribute, this);  //将值赋值到页面

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
			//Attribute attribute = new Attribute();
			Attribute attribute = (Attribute) session.createQuery("from Attribute where a0000='"+a0000+"'").uniqueResult();
			
			A01 a01 = (A01) session.get(A01.class, a0000);
			if(a01==null){
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'住址通讯信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(attribute==null){//增加日志
				attribute = new Attribute();
				attribute.setId(UUID.randomUUID().toString());
				attribute.setA0000(a0000);
			}else{//修改日志
				
			}
			this.copyElementsValueToObj(attribute, this);
						
			attribute.setA0000(a0000);
			session.saveOrUpdate(attribute);
			session.flush();
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'干部属性','保存成功！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'干部属性','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
