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
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class EnterAddPageGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		//this.setNextEventName("initX");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		this.getExecuteSG().addExecuteCode("reShowMsg();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public  int  initX() throws RadowException{
		String a0000=this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		List<A29> list = sess.createQuery("from A29 where a0000='"+a0000+"'").list();
		A29 a29 = new A29();
		if(list.size()!=0){
			a29 = list.get(0);
		}
		
		this.getPageElement("a2907").setValue(a29.getA2907());
		this.getPageElement("a2911").setValue(a29.getA2911());
		this.getPageElement("a2921a").setValue(a29.getA2921a());
		this.getPageElement("a2941").setValue(a29.getA2941());
		this.getPageElement("a2944").setValue(a29.getA2944());
		if(a29.getA2947a()==null||"".equals(a29.getA2947a())){
			this.getPageElement("a2947a_Y").setValue("0");
			this.getPageElement("a2947a_M").setValue("0");
		}else{
			long a2947a = a29.getA2947a();
			this.getPageElement("a2947a_Y").setValue(String.valueOf(a2947a/12));
			this.getPageElement("a2947a_M").setValue(String.valueOf(a2947a%12));
		}
		
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'进入管理信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a2907 = this.getPageElement("a2907").getValue();
			String a2911 = this.getPageElement("a2911").getValue();
			String a2921a = this.getPageElement("a2921a").getValue();
			String a2941 = this.getPageElement("a2941").getValue();
			String a2947a_Y = this.getPageElement("a2947a_Y").getValue();
			String a2947a_M = this.getPageElement("a2947a_M").getValue();
			long a2947a = YeartoMonths.DataChange(a2947a_Y, a2947a_M);
			String a2944 = this.getPageElement("a2944").getValue();
			A29 a29 = (A29) session.createQuery("from A29 where a0000='"+a0000+"'").uniqueResult();
			A29 a29_old = null;
			boolean isAdd = true;
			if(a29==null){//增加日志
				a29 = new A29();
				a29.setA0000(a0000);
			}else{
				isAdd = false;
				a29_old = a29.clone();
			}//修改日志
			a29.setA2907(a2907);
			a29.setA2911(a2911);
			a29.setA2921a(a2921a);
			a29.setA2941(a2941);
			a29.setA2947a(a2947a);
			a29.setA2944(a2944);
			if(isAdd){
				applog.createLogNew("","进入管理新增","进入管理",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A29(),a29));
			}else{
				applog.createLogNew("","进入管理修改","进入管理",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a29_old,a29));
			}
			session.saveOrUpdate(a29);
			session.flush();
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'进入管理信息集','保存成功！！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'进入管理信息集','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}

}
