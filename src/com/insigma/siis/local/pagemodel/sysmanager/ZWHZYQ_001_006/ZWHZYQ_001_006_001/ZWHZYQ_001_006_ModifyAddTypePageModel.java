package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
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
import com.insigma.siis.local.business.entity.CodeTable;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_006_ModifyAddTypePageModel extends PageModel {

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	@Override
	public int doInit() throws RadowException {
		String nodeId = this.getRadow_parent_data();
		AddType addType = bs6.getAddTypeById(nodeId);
		this.getPageElement("addTypeSequence").setValue(addType.getAddTypeSequence()+"");
		this.getPageElement("addTypeName").setValue(addType.getAddTypeName());
		this.getPageElement("addTypeDesc").setValue(addType.getAddTypeDetail());
		List<CodeTable> list = HBUtil.getHBSession().createQuery("from CodeTable where tableCode in('A01','A02') order by tableCode").list();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for(int i=0;i<list.size();i++) {
			map.put(list.get(i).getTableCode(), list.get(i).getTableName());
		}
		Combo table = (Combo)this.getPageElement("table_code");
		table.setValueListForSelect(map);
		this.getPageElement("table_code").setValue(addType.getTableCode());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �����´�������Ϣ������
	 * @return
	 * @throws RadowException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws NoSuchMethodException 
	 */
	@PageEvent("save.onclick")
	@AutoNoMask
	public int saveType() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		AddType addType = new AddType();          
		HBSession session = bs6.getSession();    
		String addTypeId = this.getRadow_parent_data();          //��ȡ��ҳ�汣���������ɵ�AddTypeId
		
		//��ѯ������Ϣ����ԭ����
		addType = (AddType) session.createQuery("from AddType where addTypeId=:addTypeId ")
        .setParameter("addTypeId", addTypeId).list().get(0);
		//��������ԭ���ݵĶ���
		AddType addType1 = new AddType();
		//����ֵ
		PropertyUtils.copyProperties(addType1, addType);
		//��ȡҳ������
		String addTypeDesc = this.getPageElement("addTypeDesc").getValue();
		String addTypeName = this.getPageElement("addTypeName").getValue();
		String tableCode = this.getPageElement("table_code").getValue();
		Integer addTypeSeq = Integer.valueOf(this.getPageElement("addTypeSequence").getValue());

		List<Object[]> list = session.createSQLQuery("select add_type_id,add_type_name from add_type where table_code='"+tableCode+"' and add_type_id!='"+addTypeId+"'").list();
		if(list!=null&&list.size()>0){
			this.getExecuteSG().addExecuteCode("alert('�ò�����Ϣ����"+list.get(0)[1]+"���Ѵ��ڣ����޸�')");;
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		//������ֵ
		addType.setAddTypeId(addTypeId);
		addType.setAddTypeDetail(addTypeDesc);
		addType.setAddTypeName(addTypeName);
		addType.setAddTypeSequence(addTypeSeq);
		addType.setTableCode(tableCode);
		
		//����ʼ
		Transaction t = session.getTransaction();
		t.begin();
		session.saveOrUpdate(addType);
		t.commit();
		//��¼��־
		try {
			try {
				new LogUtil().createLog("72", tableCode, addType1.getAddTypeId(), addType1.getAddTypeName(), "�޸Ĳ�����Ϣ��", new Map2Temp().getLogInfo(addType1,addType));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("alert('�޸ĳɹ���')");;
		this.closeCueWindow("modifyAddType");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException {
		this.closeCueWindow("modifyAddType");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
