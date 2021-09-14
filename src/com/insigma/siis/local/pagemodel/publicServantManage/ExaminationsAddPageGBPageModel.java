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
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A80;
import com.insigma.siis.local.business.entity.A84;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ExaminationsAddPageGBPageModel extends PageModel {
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
		List<A80> list = sess.createQuery("from A80 where a0000='"+a0000+"'").list();
		List<A84> list_A84 = sess.createQuery("from A84 where a0000='"+a0000+"' and a84type = '1'").list();
		A80 a80 = new A80();
		if(list.size()!=0){
			a80 = list.get(0);
		}
		A84 a84 = new A84();
		if(list_A84.size()!=0){
			a84 = list_A84.get(0);
		}
		this.getPageElement("a29314").setValue(a80.getA29314());
		this.getPageElement("a03033").setValue(a80.getA03033());
		this.getPageElement("a29321").setValue(a80.getA29321());
		this.getPageElement("a29324a").setValue(a80.getA29324a());
		this.getPageElement("a29324b").setValue(a80.getA29324b());
		this.getPageElement("a29327a").setValue(a80.getA29327a());
		this.getPageElement("a29327b").setValue(a80.getA29327b());
		if(a80.getA29334()==null||"".equals(a80.getA29334())||a80.getA29334().equals("0")){
			this.getPageElement("a29334_GY").setValue("0");
			this.getPageElement("a29334_GM").setValue("0");
		}else{
			long a29334 = a80.getA29334();
			this.getPageElement("a29334_GY").setValue(String.valueOf(a29334/12));
			this.getPageElement("a29334_GM").setValue(String.valueOf(a29334%12));
		}
		this.getPageElement("a29337").setValue(a80.getA29337());
		this.getPageElement("a39061").setValue(a80.getA39061());
		this.getPageElement("a39064").setValue(a80.getA39064());
		this.getPageElement("a39067").setValue(a80.getA39067());
		this.getPageElement("a39071").setValue(a80.getA39071());
		this.getPageElement("a44027").setValue(a80.getA44027());
		this.getPageElement("a39077").setValue(a80.getA39077()==null?"":String.valueOf(a80.getA39077()));
		this.getPageElement("a44031").setValue(a80.getA44031());
		this.getPageElement("a39084").setValue(a80.getA39084()==null?"":String.valueOf(a80.getA39084()));
		//号码
		this.getPageElement("a03011").setValue(a84.getA03011());
		this.getPageElement("a03021").setValue(a84.getA03021());
		this.getPageElement("a03095").setValue(a84.getA03095());
		this.getPageElement("a03027").setValue(a84.getA03027());
		this.getPageElement("a03014").setValue(a84.getA03014());
		this.getPageElement("a03017").setValue(a84.getA03017());
		this.getPageElement("a03018").setValue(a84.getA03018());
		this.getPageElement("a03024").setValue(a84.getA03024());
		this.getExecuteSG().addExecuteCode("Seta39077();SetA39084();Seta03033();");
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'考试录用信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a29314 = this.getPageElement("a29314").getValue();
			String a03033 = this.getPageElement("a03033").getValue();
			String a29321 = this.getPageElement("a29321").getValue();
			String a29324a = this.getPageElement("a29324a").getValue();
			String a29324b = this.getPageElement("a29324b").getValue();
			String a29327a = this.getPageElement("a29327a").getValue();
			String a29327b = this.getPageElement("a29327b").getValue();
			String a29334_GY = this.getPageElement("a29334_GY").getValue();
			String a29334_GM = this.getPageElement("a29334_GM").getValue();
			String a29337 = this.getPageElement("a29337").getValue();
			String a39061 = this.getPageElement("a39061").getValue();
			String a39064 = this.getPageElement("a39064").getValue();
			String a39067 = this.getPageElement("a39067").getValue();
			String a39071 = this.getPageElement("a39071").getValue();
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
			A80 a80 = (A80) session.createQuery("from A80 where a0000='"+a0000+"'").uniqueResult();
			boolean isAdda80 = true;
			A80 a80_old = null;
			if(a80==null){//增加
				a80 = new A80();
				a80.setA0000(a0000);
			}else{//修改日志
				isAdda80 = false;
				a80_old = a80.clone();
			}
			A84 a84 = new A84();
			List<A84> list_A84 = session.createQuery("from A84 where a0000='"+a0000+"' and a84type = '1'").list();
			boolean isAdda84 = true;
			A84 a84_old = null;
			if(list_A84.size()==0){//增加
				a84 = new A84();
				a84.setA0000(a0000);
				a84.setA84type("1");//考试录入信息集(A80)----1
			}else{
				a84 = list_A84.get(0);
				isAdda84 = false;
				a84_old = a84.clone();
			}
			
			
			Byte a29334 = YeartoMonths.DataChange(a29334_GY, a29334_GM);
			
			a80.setA29314(a29314);
			a80.setA03033(a03033);
			a80.setA29321(a29321);
			a80.setA29324a(a29324a);
			a80.setA29324b(a29324b);
			a80.setA29327a(a29327a);
			a80.setA29327b(a29327b);
			a80.setA29334(a29334);
			a80.setA29337(a29337);
			a80.setA39061(a39061);
			a80.setA39064(a39064);
			a80.setA39067(a39067);
			a80.setA39071(a39071);
			a80.setA44027(a44027);
			if(a39077!=null&&!"".equals(a39077)){
				a80.setA39077(Byte.valueOf(a39077));
			}
			a80.setA44031(a44031);
			if(a39084!=null&&!"".equals(a39084)){
				a80.setA39084(Byte.valueOf(a39084));
			}
			a84.setA03011(a03011);
			a84.setA03021(a03021);
			a84.setA03095(a03095);
			a84.setA03027(a03027);
			a84.setA03014(a03014);
			a84.setA03017(a03017);
			a84.setA03018(a03018);
			a84.setA03024(a03024);
			if(isAdda80){
				applog.createLogNew("","录用信息集新增","录用信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A80(),a80));
			}else{
				applog.createLogNew("32","录用信息集修改","录用信息集管理",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a80_old,a80));
			}
			if(isAdda84){
				applog.createLogNew("","录用考试信息新增","录用考试信息",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A84(),a84));
			}else{
				applog.createLogNew("32","录用考试信息修改","录用考试信息",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a84_old,a84));
			}
			session.saveOrUpdate(a80);
			session.saveOrUpdate(a84);
			session.flush();
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'考试录用信息集','保存成功！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'考试录用信息集','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
