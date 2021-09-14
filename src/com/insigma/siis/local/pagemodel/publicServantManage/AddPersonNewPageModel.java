package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeTypeConvert;
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
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;;

public class AddPersonNewPageModel extends PageModel {
	/**
	 * ��Ա������Ϣ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int savePerson()throws RadowException, AppException{
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		//�������ڸ�Ϊ���ڸ�ʽ
		//a01.setA0107(DateUtil.date2sqlDate(DateUtil.stringToDate(this.getPageElement("a0107").getValue(), "yyyymmdd")));
		//�뵳ʱ��
		//a01.setA0144(DateUtil.date2sqlDate(DateUtil.stringToDate(this.getPageElement("a0144").getValue(), "yyyymmdd")));
		//�μӹ���ʱ��
		//a01.setA0134(DateUtil.date2sqlDate(DateUtil.stringToDate(this.getPageElement("a0134").getValue(), "yyyymmdd")));
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(a0000==null||"".equals(a0000)){
				sess.save(a01);	
				//ְ��Ϊ��
				//A02 a02 = new A02();
				//a02.setA0201b("3307004");
				//a02.setA0201a("������ְ��Ա");
				//a02.setA0000(a01.getA0000());
				//sess.save(a02);
				//��������ʱ��ҳ��������Ա���������eventParameter
				this.getExecuteSG().addExecuteCode("window.parent.radow.doEvent('tabClick','"+a01.getA0000()+"');");
			}else{
				sess.update(a01);	
			}					
			sess.flush();			
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0000").setValue(a01.getA0000());//����ɹ���id���ص�ҳ���ϡ�		
		this.getExecuteSG().addExecuteCode("window.parent.parent.Ext.getCmp('persongrid').getStore().reload()");//ˢ����Ա�б�
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��Ա������Ϣ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveOthers.onclick")
	@Transaction
	@Synchronous(true)
	public int saveOthers()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//��������� id���ɷ�ʽΪassigned
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			sess.saveOrUpdate(a29);	
			
			//�����Ᵽ��	id���ɷ�ʽΪuuid �� ��������� ��id����Ϊnull
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
			}
			sess.saveOrUpdate(a53);
			this.getPageElement("a5300").setValue(a53.getA5300());
			
			//סַͨѶ���� id���ɷ�ʽΪassigned
			A37 a37 = new A37();
			this.copyElementsValueToObj(a37, this);
			sess.saveOrUpdate(a37);	
			
			//���˱��� id���ɷ�ʽΪassigned
			A31 a31 = new A31();
			this.copyElementsValueToObj(a31, this);
			sess.saveOrUpdate(a31);	
			
			//�˳������� id���ɷ�ʽΪassigned
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			sess.saveOrUpdate(a30);	
			sess.flush();
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('persongrid').getStore().reload()");//ˢ����Ա�б�
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * a0184���֤����֤
	 * 
	 */
	@PageEvent("a0184.onblur")
	public int a0184onblur(String v)throws RadowException, AppException{
		String idcardno = this.getPageElement("a0184").getValue();
		if(idcardno==null || idcardno.equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!IdCardManageUtil.trueOrFalseIdCard(idcardno)){
			this.setMainMessage("���֤��ʽ����");
		}else{
			this.getPageElement("a0107").setValue(IdCardManageUtil.getBirthdayFromIdCard(idcardno));//��������
			this.getPageElement("a0104").setValue(IdCardManageUtil.getSexFromIdCard(idcardno));//�Ա�
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//������ҳ�棬����Ƿ�����Ա���룬����������������������޸ġ�
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A01 where a0000='"+this.getRadow_parent_data()+"'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);			
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			//����������
			A29 a29 = (A29)sess.get(A29.class, a01.getA0000());
			if(a29!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a29, this);
			}
			//���������
			String sqlA53 = "from A53 where a0000='"+a0000+"'";
			List listA53 = sess.createQuery(sqlA53).list();			
			if(listA53!=null&&listA53.size()>0){
				A53 a53 = (A53) listA53.get(0);	
				PMPropertyCopyUtil.copyObjValueToElement(a53, this);
			}	
			
			//סַͨѶ����
			A37 a37 = (A37)sess.get(A37.class, a01.getA0000());
			if(a37!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a37, this);
			}
			
			//���˼���
			A31 a31 = (A31)sess.get(A31.class, a01.getA0000());
			if(a31!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a31, this);
			}
			
			//�˳��������
			A30 a30 = (A30)sess.get(A30.class, a01.getA0000());
			if(a30!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a30, this);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}	
		//this.setNextEventName("professSkillgrid.dogridquery");//רҵ����ְ���б�
		//this.setNextEventName("degreesgrid.dogridquery");//ѧλѧ���б�
		//this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�
		//this.setNextEventName("RewardPunishGrid.dogridquery");//��������б�
		//this.setNextEventName("AssessmentInfoGrid.dogridquery");//��ȿ�������б�
		//this.setNextEventName("FamilyRelationsGrid.dogridquery");//��ͥ��Ҫ��Ա����Ҫ����ϵ
		//this.setNextEventName("TrainingInfoGrid.dogridquery");//��ѵ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}	
}
