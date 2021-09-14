package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;

public class PersonAddTabPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//������ҳ�棬����Ƿ�����Ա���룬����������������������޸ġ�
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('��Ա��������');");
			//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('��Ա��������');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//���Ĵ�������updateWin.setTitle(title);
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//���� �Ա� ����
			String a0101 = a01.getA0101();//����
			String a0184 = a01.getA0184();//���֤��
			String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
			String age = "";
			if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
				age = IdCardManageUtil.getAge(a0184)+"";
			}
			String title = a0101 + "��" + sex + "��" + age+"��";
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('"+title+"');");
			//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('"+title+"');");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.getActiveTab().setTitle('"+title+"');");
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * ��������ʱ������Ա���롣
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("tabClick")
	public int tabClick(String id) throws RadowException {
		//Map map = this.getRequestParamer();
		//String a0000 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		String a0000 = id;
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//������ҳ�棬����Ƿ�����Ա���룬����������������������޸ġ�
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('��Ա��������');");
			//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('��Ա��������');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//���Ĵ�������updateWin.setTitle(title);
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//���� �Ա� ����
			String a0101 = a01.getA0101();//����
			String a0184 = a01.getA0184();//���֤��
			String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
			String age = "";
			if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
				age = IdCardManageUtil.getAge(a0184)+"";
			}
			String title = a0101 + "��" + sex + "��" + age+"��";
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('"+title+"');");
			//this.getExecuteSG().addExecuteCode("var newperson = window.parent.Ext.getCmp('"+a0000+"');" +
			//		"if(!newperson){newperson=window.parent.Ext.getCmp('add');}newperson.setTitle('"+title+"');");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.getActiveTab().setTitle('"+title+"');");
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
}
