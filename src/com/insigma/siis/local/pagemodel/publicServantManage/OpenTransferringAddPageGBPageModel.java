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
import com.insigma.siis.local.business.entity.A82;
import com.insigma.siis.local.business.entity.A83;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class OpenTransferringAddPageGBPageModel extends PageModel {

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
		List<A83> list = sess.createQuery("from A83 where a0000='"+a0000+"'").list();
		A83 a83 = new A83();
		if(list.size()!=0){
			a83 = list.get(0);
		}
			
		this.getPageElement("a02192").setValue(a83.getA02192());
		this.getPageElement("a29311").setValue(a83.getA29311());
		this.getPageElement("g02002").setValue(a83.getG02002());
		this.getPageElement("a29044").setValue(a83.getA29044());
		this.getPageElement("a29041").setValue(a83.getA29041());
		this.getPageElement("a29354").setValue(a83.getA29354());
		this.getPageElement("a44027").setValue(a83.getA44027());
		this.getPageElement("a39077").setValue(a83.getA39077()==null?"":String.valueOf(a83.getA39077()));
		this.getPageElement("a39084").setValue(a83.getA39084()==null?"":String.valueOf(a83.getA39084()));
		this.getPageElement("a44031").setValue(a83.getA44031());
		this.getExecuteSG().addExecuteCode("Seta39077();Seta39084();Seta29354()");
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'公开选调信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a02192 = this.getPageElement("a02192").getValue();
			String a29311 = this.getPageElement("a29311").getValue();
			String g02002 = this.getPageElement("g02002").getValue();
			String a29044 = this.getPageElement("a29044").getValue();
			String a29041 = this.getPageElement("a29041").getValue();
			String a29354 = this.getPageElement("a29354").getValue();
			String a44027 = this.getPageElement("a44027").getValue();
			String a39077 = this.getPageElement("a39077").getValue();
			String a44031 = this.getPageElement("a44031").getValue();
			String a39084 = this.getPageElement("a39084").getValue();
			A83 a83 = (A83) session.createQuery("from A83 where a0000='"+a0000+"'").uniqueResult();
			if(a83==null){//增加日志
				a83 = new A83();
				a83.setA0000(a0000);
			}else{//修改日志
				
			}
			
			a83.setA02192(a02192);
			a83.setA29311(a29311);
			a83.setG02002(g02002);
			a83.setA29044(a29044);
			a83.setA29041(a29041);
			a83.setA29354(a29354);
			a83.setA44027(a44027);
			if(a39077!=null&&!"".equals(a39077)){
				a83.setA39077(Byte.valueOf(a39077));
			}
			a83.setA44031(a44031);
			if(a39084!=null&&!"".equals(a39084)){
				a83.setA39084(Byte.valueOf(a39084));
			}
			session.saveOrUpdate(a83);
			session.flush();
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'公开选调信息集','保存成功！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'公开选调信息集','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
}
