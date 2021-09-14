package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.CodeTable;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_006_AddTypePageModel extends PageModel {

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	@Override
	public int doInit() throws RadowException {
		String addSeq = String.valueOf(bs6.getMaxSeq()+1);
		this.getPageElement("addTypeSequence").setValue(addSeq);
		List<CodeTable> list = HBUtil.getHBSession().createQuery("from CodeTable where tableCode in('A01','A02') order by tableCode").list();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for(int i=0;i<list.size();i++) {
			map.put(list.get(i).getTableCode(), list.get(i).getTableName());
		}
		Combo table = (Combo)this.getPageElement("table_code");
		table.setValueListForSelect(map);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 保存新创建的信息集类型
	 * @return
	 * @throws RadowException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	@PageEvent("save.onclick")
	public int saveType() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		AddType addType = new AddType();          
		HBSession session = bs6.getSession();    
		String addTypeId = this.getRadow_parent_data();          //获取父页面保存的随机生成的AddTypeId         
		String addTypeDesc = this.getPageElement("addTypeDesc").getValue();
		String addTypeName = this.getPageElement("addTypeName").getValue();
		String tableCode = this.getPageElement("table_code").getValue();
		Integer addTypeSeq = Integer.valueOf(this.getPageElement("addTypeSequence").getValue());

		addType.setAddTypeId(addTypeId);
		addType.setAddTypeDetail(addTypeDesc);
		addType.setAddTypeName(addTypeName);
		addType.setAddTypeSequence(addTypeSeq);
		addType.setTableCode(tableCode);
		
		List<Object[]> list = session.createSQLQuery("select add_type_id,add_type_name from add_type where table_code='"+tableCode+"' and add_type_id!='"+addTypeId+"'").list();
		if(list!=null&&list.size()>0){
			this.getExecuteSG().addExecuteCode("alert('该补充信息集【"+list.get(0)[1]+"】已存在，请修改')");;
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		Transaction t = session.getTransaction();
		t.begin();
		session.save(addType);
		t.commit();
		this.getExecuteSG().addExecuteCode("alert('保存成功!')");
		this.closeCueWindow("addNewType");
		try {
			try {
				new LogUtil().createLog("70", tableCode, addType.getAddTypeId(), addType.getAddTypeName(), "新建补充信息集", new Map2Temp().getLogInfo(new AddType(), addType));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException {
		this.closeCueWindow("addNewType");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
