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
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class ExitAddPageGBPageModel extends PageModel {
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
		List<A30> list = sess.createQuery("from A30 where a0000='"+a0000+"'").list();
		A30 a30 = new A30();
		if(list.size()!=0){
			a30 = list.get(0);
		}
		String a3001 = a30.getA3001();
		this.getPageElement("a3001").setValue(a3001);
		this.getPageElement("a3004").setValue(a30.getA3004());
		this.getPageElement("a3034").setValue(a30.getA3034());
		this.getPageElement("a3038").setValue(a30.getA3038());
		this.getPageElement("a3007a").setValue(a30.getA3007a());
		this.getPageElement("a3954a").setValue(a30.getA3954a());
		this.getPageElement("a3954b").setValue(a30.getA3954b());
		this.getExecuteSG().addExecuteCode("setA3954ADisabled()");
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'退出管理信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a3001 = this.getPageElement("a3001").getValue();
			String a3004 = this.getPageElement("a3004").getValue();
			String a3034 = this.getPageElement("a3034").getValue();
			String a3038 = this.getPageElement("a3038").getValue();
			String a3007a = this.getPageElement("a3007a").getValue();
			String a3954a = this.getPageElement("a3954a").getValue();
			String a3954b = this.getPageElement("a3954b").getValue();
			A30 a30 = (A30) session.createQuery("from A30 where a0000='"+a0000+"'").uniqueResult();
			if(a30==null){//增加日志
				a30 = new A30();
				a30.setA0000(a0000);
			}else{//修改日志
				
			}
			a30.setA3001(a3001);
			a30.setA3004(a3004);
			a30.setA3034(a3034);
			a30.setA3038(a3038);
			a30.setA3007a(a3007a);
			a30.setA3954a(a3954a);
			a30.setA3954b(a3954b);
			
			//1现职人员 2离退人员 3调出人员 4已去世 5其他人员       0完全删除 1正常 2历史库 3离退人员
			//退出管理保存 id生成方式为assigned
			
			if(a3001!=null&&!"".equals(a3001)){
				//调出人员     历史库
				if("21,22,23,24,95,96,25,26,81,82,83,84".indexOf(a3001)!=-1){
					//a01.setA0163("3");
					a01.setA0163("2");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("31,32".indexOf(a3001)!=-1){//死亡  显示：已去世。       查询：历史人员
					//a01.setA0163("4");
					a01.setA0163("2");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("11,12".indexOf(a3001)!=-1){//离退休 显示：离退人员。     查询：离退人员
					//a01.setA0163("2");
					a01.setA0163("2");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("3");
					//}
					
				}else if("97,98,99,990".indexOf(a3001)!=-1){//无锡新增：市委协助管理的干部,乡镇街道党政正职,市级机关组织人事处长,外地来锡挂职干部
					a01.setA0163(a3001);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
				}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
					//a01.setA0163("5");
					a01.setA0163("2");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}
			}else{
				a01.setA0163("1");
				a01.setStatus("1");
				//不覆盖【离退】的状态
			}
			HBSession hbSession = HBUtil.getHBSession();
			hbSession.update(a01);
			hbSession.flush();
			session.saveOrUpdate(a30);
			session.flush();
			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
