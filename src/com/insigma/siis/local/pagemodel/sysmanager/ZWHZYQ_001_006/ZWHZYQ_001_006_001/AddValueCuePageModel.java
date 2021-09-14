package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AddValueCuePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ����½����޸���Ϣ�����Ӧ�¼�
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("queryObj")
	@NoRequiredValidate
	public int queryObj() throws RadowException {
		String nodeId = this.getRadow_parent_data();
		String[] nodeIds = nodeId.split("_");
		String opmode = nodeIds[0];
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		List<CodeType> list = HBUtil.getHBSession().createQuery("from CodeType where iscustomize='0' or codeType like 'KZ%' order by codeType").list();
		for(int i=0;i<list.size();i++) {
			map.put(list.get(i).getCodeType(), list.get(i).getCodeType()+":"+list.get(i).getTypeName());
		}
		Combo table = (Combo)this.getPageElement("reference");
		table.setValueListForSelect(map);
		if("NEW".equals(opmode)) {                                  //������½���Ϣ��nodeId ��ʽΪ opmode+"_"+��Ϣ��Id����Ϣ��Id,��ΪNEWʱΪ��Ϣ��Id
			int seq = bs6.getMaxAddValueSeq(nodeIds[1])+1;
			this.getPageElement("addValueSequence").setValue(""+seq);
		} else if("UPDATE".equals(opmode)){                                                    //����Ǹ�����Ϣ��
			String addValueId = nodeIds[1];
			AddValue addValue = bs6.getAddValueById(addValueId);
			this.getPageElement("addValueSequence").setValue(addValue.getAddValueSequence()+"");
			this.getPageElement("colCode").setValue(addValue.getColCode());
			this.getPageElement("addValueName").setValue(addValue.getAddValueName());
			this.getPageElement("colType").setValue(addValue.getColType());
			this.getPageElement("reference").setValue(addValue.getCodeType());
			this.getPageElement("addValueDesc").setValue(addValue.getAddValueDetail());
			this.getPageElement("isused").setValue(addValue.getIsused());
			this.getPageElement("multilineshow").setValue(addValue.getMultilineshow());
			if("1".equals(addValue.getPublishStatus())) {//����Ѿ�����
				this.getPageElement("addValueSequence").setDisabled(true);
				this.getPageElement("colCode").setDisabled(true);
				this.getPageElement("colType").setDisabled(true);
				this.getPageElement("addValueName").setDisabled(true);
				this.getPageElement("reference").setDisabled(true);
				this.getPageElement("addValueDesc").setDisabled(true);
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��Ϣ���
	 * @return
	 * @throws RadowException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("save.onclick")
	@AutoNoMask
	public int save() throws RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//��ȡ������������ʽΪ"NEW_"+ID��"UPDATE_"+ID
		String nodeId = this.getRadow_parent_data();
		//��������
		String[] nodeIds = nodeId.split("_");
		//��ȡ��������
		String opmode = nodeIds[0];
		//����ֵ����
		AddValue addValue = new AddValue();
		//������½���Ϣ��nodeId ��ʽΪ opmode+"_"+��Ϣ��Id����Ϣ��Id,��ΪNEWʱΪ��Ϣ��Id,��ΪUPDATEʱΪ��Ϣ��Id
		if("NEW".equals(opmode)) {
			String colCode = this.getPageElement("colCode").getValue();
			List<CodeTableCol> list= bs6.getSession().createQuery("from CodeTableCol where colCode=:colCode")
					.setParameter("colCode", colCode).list();
			if(list.size()>0) {
				this.setMainMessage("����Ϣ������Ѵ��ڣ���������������Ϣ����롣");
				return EventRtnType.NORMAL_SUCCESS;
			}
			addValue.setAddValueSequence(Integer.valueOf(this.getPageElement("addValueSequence").getValue()));
			addValue.setAddTypeId(nodeIds[1]);
			addValue.setAddValueDetail(this.getPageElement("addValueDesc").getValue());
			UUID uuid = UUID.randomUUID();
			addValue.setAddValueId(uuid.toString());
			addValue.setAddValueName(this.getPageElement("addValueName").getValue());
			addValue.setColCode(this.getPageElement("colCode").getValue());
			addValue.setColType(this.getPageElement("colType").getValue());
			addValue.setCodeType(this.getPageElement("reference").getValue());
			addValue.setPublishStatus("0");//����Ϊδ����״̬
			String isused = this.getPageElement("isused").getValue();
			addValue.setIsused(isused);
			String multilineshow = this.getPageElement("multilineshow").getValue();
			addValue.setMultilineshow(multilineshow);
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().save(addValue);
			t.commit();
			//��¼������Ϣ�����־
			try {
				new LogUtil().createLog("73", "ADDVALUE", addValue.getAddValueId(), addValue.getAddValueName(), "", new Map2Temp().getLogInfo(new AddValue(),addValue));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setMainMessage("");
			this.closeCueWindow("addNewValue");
		} else if("UPDATE".equals(opmode)){
			addValue = bs6.getAddValueById(nodeIds[1]);
			String colCode = this.getPageElement("colCode").getValue();
			List<CodeTableCol> list= bs6.getSession().createQuery("from CodeTableCol where colCode=:colCode and colCode<>'"+addValue.getColCode()+"'")
					.setParameter("colCode", colCode).list();
			if(list.size()>0) {
				this.setMainMessage("����Ϣ������Ѵ��ڣ���������������Ϣ����롣");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//�������ԭ���ݵ��̶���
			AddValue addValue1 = new AddValue();
			//��������
			PropertyUtils.copyProperties(addValue1, addValue);
			
			//���¶���ֵ
			addValue.setAddValueSequence(Integer.valueOf(this.getPageElement("addValueSequence").getValue()));
			addValue.setAddValueDetail(this.getPageElement("addValueDesc").getValue());
			addValue.setAddValueName(this.getPageElement("addValueName").getValue());
			addValue.setCodeType(this.getPageElement("reference").getValue());
			addValue.setColType(this.getPageElement("colType").getValue());
			addValue.setColCode(this.getPageElement("colCode").getValue());
			String isused = this.getPageElement("isused").getValue();
			addValue.setIsused(isused);
			String multilineshow = this.getPageElement("multilineshow").getValue();
			addValue.setMultilineshow(multilineshow);
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().saveOrUpdate(addValue);
			t.commit();
			//��¼��־
			try {
				new LogUtil().createLog("74", "ADDVALUE", addValue1.getAddValueId(), addValue1.getAddValueName(), "", new Map2Temp().getLogInfo(addValue1,addValue));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.closeCueWindow("updateAddValue");
		}
		this.getExecuteSG().addExecuteCode("window.parent.refresh()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException {
		String nodeId = this.getRadow_parent_data();
		String[] nodeIds = nodeId.split("_");
		String opmode = nodeIds[0];
		if("NEW".equals(opmode)) {
			this.closeCueWindow("addNewValue");
		} else if("UPDATE".equals(opmode)) {
			this.closeCueWindow("updateAddValue");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
