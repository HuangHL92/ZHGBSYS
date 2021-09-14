package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class NewCodeTypeCuePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	private static String nodeId;
	
	@Override
	public int doInit() throws RadowException {
		nodeId = this.getRadow_parent_data();//��Ϊ�½�ʱRadow_parent_dataΪ"NEW",��Ϊ�޸�ʱΪ���뼯����
		if(!"NEW".equals(nodeId)) {
			CodeType codeType = bs6.getCodeTypeById(nodeId);
			this.getPageElement("codeType").setValue(nodeId);
			this.getPageElement("typeName").setValue(codeType.getTypeName());
			this.getPageElement("codeDescription").setValue(codeType.getCodeDescription());
			this.getPageElement("codeType").setDisabled(true);
		} else {
			this.getPageElement("codeType").setValue("KZ");;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("cancel.onclick")
	public int cancel(){
		if("NEW".equals(nodeId)) {
			this.closeCueWindow("NewCodeType");
		} else {
			this.closeCueWindow("UpdateCodeType");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	public int save() throws RadowException{
		if("NEW".equals(nodeId)) {
			String codeTypeId = this.getPageElement("codeType").getValue();
			CodeType codeType = bs6.getCodeTypeById(codeTypeId);
			if(codeType != null) {
				this.setMainMessage(codeTypeId+"�ô��뼯�Ѵ��ڣ�������������뼯��š�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			codeType = new CodeType();
			CommonQueryBS.systemOut(codeTypeId);
			String typeName = this.getPageElement("typeName").getValue();
			String des = this.getPageElement("codeDescription").getValue();
			codeType.setCodeType(codeTypeId);
			codeType.setTypeName(typeName);
			codeType.setCodeDescription(des);
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().save(codeType);
			t.commit();
			
			//����ʱ��¼��־
			try {
				new LogUtil().createLog("79", "CODETYPE", codeType.getCodeType(), codeType.getTypeName(), "�����Զ����´��뼯", new Map2Temp().getLogInfo(new CodeType(),codeType));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");
			this.closeCueWindow("NewCodeType");
		} else{
			CodeType codeType = bs6.getCodeTypeById(nodeId);
			
			//�½������������ԭ��������
			CodeType codeType1 = new CodeType();
			//����ԭ���ݶ���
			try {
				PropertyUtils.copyProperties(codeType1, codeType);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
			String typeName = this.getPageElement("typeName").getValue();
			String des = this.getPageElement("codeDescription").getValue();
			codeType.setTypeName(typeName);
			codeType.setCodeDescription(des);
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().saveOrUpdate(codeType);
			t.commit();
			//�޸�ʱ��¼��־��Ϣ
			try {
				new LogUtil().createLog("80", "CODETYPE", codeType.getCodeType(), codeType.getTypeName(), "�޸��Զ����´��뼯", new Map2Temp().getLogInfo(codeType1,codeType));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");
			this.closeCueWindow("UpdateCodeType");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
