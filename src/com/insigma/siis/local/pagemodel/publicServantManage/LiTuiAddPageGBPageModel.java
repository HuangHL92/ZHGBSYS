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
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class LiTuiAddPageGBPageModel extends PageModel {
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
		// 离退加载
		A31 a31 = (A31)sess.get(A31.class, a0000);
		if (a31==null) {
			a31 = new A31();
			a31.setA0000(a0000);
		}
		PMPropertyCopyUtil.copyObjValueToElement(a31, this);
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'拟任免信息集','请先保存人员基本信息！');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a3101 = this.getPageElement("a3101").getValue();
			String a3104 = this.getPageElement("a3104").getValue();
			String a3118 = this.getPageElement("a3118").getValue();
			String a3107 = this.getPageElement("a3107").getValue();
			String a3117a = this.getPageElement("a3117a").getValue();
			String a3137 = this.getPageElement("a3137").getValue();
			A31 a31 = (A31)session.get(A31.class, a0000);
			if(a31==null){//增加日志
				a31 = new A31();
				a31.setA0000(a0000);
			}else{
			}//修改日志
			a31.setA3101(a3101);
			a31.setA3104(a3104);
			a31.setA3118(a3118);
			a31.setA3107(a3107);
			a31.setA3117a(a3117a);
			a31.setA3137(a3137);
			if(a3101!=null&&!"".equals(a3101)){
				a01.setA0163("2");
				//if(!"4".equals(a01.getStatus())){//4 临时数据
					a01.setStatus("3");
				//}
				
			}else{
				a01.setA0163("1");
				//if(!"4".equals(a01.getStatus())){
					a01.setStatus("1");
				//}
			}
			//1现职人员 2离退人员 3调出人员 4已去世 5其他人员              status--0完全删除 1正常 2历史库 3离退人员
			//退出管理保存 id生成方式为assigned
			A30 a30 = (A30) session.createQuery("from A30 where a0000='"+a0000+"'").uniqueResult();
			if(a30!=null){
				String a3001 = a30.getA3001();
				if(a3001!=null&&!"".equals(a3001)){
					//调出人员     历史库
					if(a3001.startsWith("1")||a3001.startsWith("2")){
						//a01.setA0163("3");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("2");
						//}
						
					}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
						//a01.setA0163("4");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("2");
						//}
						
					}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
						//a01.setA0163("2");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("3");
						//}
						
					}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
						//a01.setA0163("5");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("2");
						//}
						
					}
				}else{

					//不覆盖【离退】的状态
				}
			}
			
			
			session.save(a01);
			session.saveOrUpdate(a31);
			session.flush();
			
			
			
			
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'拟任免信息集','保存成功！！');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'拟任免信息集','保存失败,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}

}
