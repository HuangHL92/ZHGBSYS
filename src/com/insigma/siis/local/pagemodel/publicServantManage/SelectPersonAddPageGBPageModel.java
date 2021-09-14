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
import com.insigma.siis.local.business.entity.A80;
import com.insigma.siis.local.business.entity.A81;
import com.insigma.siis.local.business.entity.A84;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class SelectPersonAddPageGBPageModel extends PageModel {
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
		List<A81> list = sess.createQuery("from A81 where a0000='"+a0000+"'").list();
		List<A84> list_A84 = sess.createQuery("from A84 where a0000='"+a0000+"' and a84type = '2'").list();
		A81 a81 = new A81();
		if(list.size()!=0){
			a81 = list.get(0);
		}
		A84 a84 = new A84();
		if(list_A84.size()!=0){
			a84 = list_A84.get(0);
		}
		this.getPageElement("g02001").setValue(a81.getG02001());
		this.getPageElement("a29071").setValue(a81.getA29071());
		this.getPageElement("a29072").setValue(a81.getA29072());
		this.getPageElement("a29341").setValue(a81.getA29341());
		if(a81.getA29073()==null||"".equals(a81.getA29073())||a81.getA29073().equals("0")){
			this.getPageElement("a29073_Y").setValue("0");
			this.getPageElement("a29073_M").setValue("0");
		}else{
			long a29073 = a81.getA29073();
			this.getPageElement("a29073_Y").setValue(String.valueOf(a29073/12));
			this.getPageElement("a29073_M").setValue(String.valueOf(a29073%12));
		}
		this.getPageElement("a29344").setValue(a81.getA29344());
		this.getPageElement("a29347a").setValue(a81.getA29347a());
		this.getPageElement("a29347b").setValue(a81.getA29347b());
		this.getPageElement("a29347c").setValue(a81.getA29347c());
		this.getPageElement("a29351b").setValue(a81.getA29351b());
		this.getPageElement("a39067").setValue(a81.getA39067());
		this.getPageElement("a44027").setValue(a81.getA44027());
		this.getPageElement("a39077").setValue(a81.getA39077()==null?"":String.valueOf(a81.getA39077()));
		this.getPageElement("a44031").setValue(a81.getA44031());
		this.getPageElement("a39084").setValue(a81.getA39084()==null?"":String.valueOf(a81.getA39084()));
		this.getPageElement("a03011").setValue(a84.getA03011());
		this.getPageElement("a03021").setValue(a84.getA03021());
		this.getPageElement("a03095").setValue(a84.getA03095());
		this.getPageElement("a03027").setValue(a84.getA03027());
		this.getPageElement("a03014").setValue(a84.getA03014());
		this.getPageElement("a03017").setValue(a84.getA03017());
		this.getPageElement("a03018").setValue(a84.getA03018());
		this.getPageElement("a03024").setValue(a84.getA03024());
		this.getExecuteSG().addExecuteCode("Seta39077();Seta39084();Seta29341();");
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'选调生信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String g02001 = this.getPageElement("g02001").getValue();
			String a29071 = this.getPageElement("a29071").getValue();
			String a29072 = this.getPageElement("a29072").getValue();
			String a29341 = this.getPageElement("a29341").getValue();
			String a29073_Y = this.getPageElement("a29073_Y").getValue();
			String a29073_M = this.getPageElement("a29073_M").getValue();
			String a29344 = this.getPageElement("a29344").getValue();
			String a29347a = this.getPageElement("a29347a").getValue();
			String a29347b = this.getPageElement("a29347b").getValue();
			String a29347c = this.getPageElement("a29347c").getValue();
			String a29351b = this.getPageElement("a29351b").getValue();
			String a39067 = this.getPageElement("a39067").getValue();
			String a44027 = this.getPageElement("a44027").getValue();
			String a39077 = this.getPageElement("a39077").getValue();
			String a44031 = this.getPageElement("a44031").getValue();
			String a39084 = this.getPageElement("a39084").getValue();
			String a03011 = this.getPageElement("a03011").getValue();
			String a03021 = this.getPageElement("a03021").getValue();
			String a03095 = this.getPageElement("a03095").getValue();
			String a03027 = this.getPageElement("a03027").getValue();
			String a03014 = this.getPageElement("a03014").getValue();
			String a03017 = this.getPageElement("a03017").getValue();
			String a03018 = this.getPageElement("a03018").getValue();
			String a03024 = this.getPageElement("a03024").getValue();
			Byte a29073 = YeartoMonths.DataChange(a29073_Y, a29073_M);
			A81 a81 = (A81) session.createQuery("from A81 where a0000='"+a0000+"'").uniqueResult();
			boolean isAdda81 = true;
			A81 a81_old = null;
			if(a81==null){//增加
				a81 = new A81();
				a81.setA0000(a0000);
			}else{//修改日志
				isAdda81 = false;
				a81_old = a81.clone();
			}
			
			A84 a84 = new A84();
			List<A84> list_A84 = session.createQuery("from A84 where a0000='"+a0000+"' and a84type = '2'").list();
			boolean isAdda84 = true;
			A84 a84_old = null;
			if(list_A84.size()==0){//增加
				a84 = new A84();
				a84.setA0000(a0000);
				a84.setA84type("2");//选调生信息集(A81)----2
			}else{
				a84 = list_A84.get(0);
				isAdda84 = false;
				a84_old = a84.clone();
			}
			a81.setA0000(a0000);
			a81.setG02001(g02001);
			a81.setA29071(a29071);
			a81.setA29072(a29072);
			a81.setA29341(a29341);
			a81.setA29073(a29073);
			a81.setA29344(a29344);
			a81.setA29347a(a29347a);
			a81.setA29347b(a29347b);
			a81.setA29347c(a29347c);
			a81.setA29351b(a29351b);
			a81.setA39067(a39067);
			a81.setA44027(a44027);
			if(a39077!=null&&!"".equals(a39077)){
				a81.setA39077(Byte.valueOf(a39077));
			}
			a81.setA44031(a44031);
			if(a39084!=null&&!"".equals(a39084)){
				a81.setA39084(Byte.valueOf(a39084));
			}
			a84.setA0000(a0000);
			a84.setA03011(a03011);
			a84.setA03021(a03021);
			a84.setA03095(a03095);
			a84.setA03027(a03027);
			a84.setA03014(a03014);
			a84.setA03017(a03017);
			a84.setA03018(a03018);
			a84.setA03024(a03024);
			if(isAdda81){
				applog.createLogNew("","选调生新增","选调生",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A81(),a81));
			}else{
				applog.createLogNew("32","选调生修改","选调生",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a81_old,a81));
			}
			if(isAdda84){
				applog.createLogNew("","选调生考试信息新增","选调生考试信息",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A84(),a84));
			}else{
				applog.createLogNew("32","选调生考试信息修改","选调生考试信息",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a84_old,a84));
			}
			session.saveOrUpdate(a81);
			session.saveOrUpdate(a84);
			session.flush();
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'选调生信息集','保存成功！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'选调生信息集','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
