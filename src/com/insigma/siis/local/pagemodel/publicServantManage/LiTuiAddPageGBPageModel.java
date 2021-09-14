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
		// ���˼���
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
				this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'��������Ϣ��','���ȱ�����Ա������Ϣ��');");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a3101 = this.getPageElement("a3101").getValue();
			String a3104 = this.getPageElement("a3104").getValue();
			String a3118 = this.getPageElement("a3118").getValue();
			String a3107 = this.getPageElement("a3107").getValue();
			String a3117a = this.getPageElement("a3117a").getValue();
			String a3137 = this.getPageElement("a3137").getValue();
			A31 a31 = (A31)session.get(A31.class, a0000);
			if(a31==null){//������־
				a31 = new A31();
				a31.setA0000(a0000);
			}else{
			}//�޸���־
			a31.setA3101(a3101);
			a31.setA3104(a3104);
			a31.setA3118(a3118);
			a31.setA3107(a3107);
			a31.setA3117a(a3117a);
			a31.setA3137(a3137);
			if(a3101!=null&&!"".equals(a3101)){
				a01.setA0163("2");
				//if(!"4".equals(a01.getStatus())){//4 ��ʱ����
					a01.setStatus("3");
				//}
				
			}else{
				a01.setA0163("1");
				//if(!"4".equals(a01.getStatus())){
					a01.setStatus("1");
				//}
			}
			//1��ְ��Ա 2������Ա 3������Ա 4��ȥ�� 5������Ա              status--0��ȫɾ�� 1���� 2��ʷ�� 3������Ա
			//�˳������� id���ɷ�ʽΪassigned
			A30 a30 = (A30) session.createQuery("from A30 where a0000='"+a0000+"'").uniqueResult();
			if(a30!=null){
				String a3001 = a30.getA3001();
				if(a3001!=null&&!"".equals(a3001)){
					//������Ա     ��ʷ��
					if(a3001.startsWith("1")||a3001.startsWith("2")){
						//a01.setA0163("3");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("2");
						//}
						
					}else if("35".equals(a3001)){//����  ��ʾ����ȥ����       ��ѯ����ʷ��Ա
						//a01.setA0163("4");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("2");
						//}
						
					}else if("31".equals(a3001)){//������ ��ʾ��������Ա��     ��ѯ��������Ա
						//a01.setA0163("2");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("3");
						//}
						
					}else{//����ѡ���˳��Ǽǡ��������������ˡ�����ȥ��ְ���������� ��ʾ��������Ա��     ��ѯ����ʷ��Ա
						//a01.setA0163("5");
						a01.setA0163("2");
						//if(!"4".equals(a01.getStatus())){
						a01.setStatus("2");
						//}
						
					}
				}else{

					//�����ǡ����ˡ���״̬
				}
			}
			
			
			session.save(a01);
			session.saveOrUpdate(a31);
			session.flush();
			
			
			
			
			this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'��������Ϣ��','����ɹ�����');");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'��������Ϣ��','����ʧ��,"+e.getMessage()+"');");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}

}
