package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class NewCodeTypePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	 * 获取扩展代码集的树
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //获取所有的补充信息类型集合
		StringBuffer jsonStr = new StringBuffer();                                    //创建字符串容器对象，用来传递给EXT树
		String sql = "select t.code_type,t.type_name from code_type t where t.code_type like 'KZ%'";
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();       //得到当前组织信息
		Object[] object = null;
		for(int i=0; i<list.size(); i++){
			object = list.get(i);
			if(i==0){
				jsonStr.append("[");
			}
			jsonStr.append("{\"text\" :\""+object[0]+"."+object[1]+"\" ,\"id\" :\""+object[0]+"\",\"cls\" :\"folder\",\"leaf\":true}");
			if(i!=(list.size()-1)){
				jsonStr.append(",");
			} else {
				jsonStr.append("]");
			}
		}
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
	}
	/**
	 * 打开新建代码集窗口
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("openNewWindow")
	public int openWindow() throws RadowException {
		this.setRadow_parent_data("NEW");
		this.openWindow("NewCodeType","pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003.NewCodeTypeCue");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 打开修改代码集窗口
	 * @param nodeId
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("update")
	public int update(String nodeId) throws RadowException {
		if("S000000".equals(nodeId) || nodeId==null || "".equals(nodeId)) {
			this.setMainMessage("请您选择一个代码集。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		CodeType codeType = bs6.getCodeTypeById(nodeId);
		List<AddValue> list = bs6.getSession().createQuery("from AddValue where codeType=:codeType")
	              .setParameter("codeType", codeType.getCodeType()).list();
		if(list.size()>0) {
			this.setMainMessage(codeType.getCodeType()+"该代码集已经被引用，不能修改。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data(nodeId);
		this.openWindow("UpdateCodeType","pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003.NewCodeTypeCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除代码集
	 * @param nodeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("delete")
	public int delete(String nodeId) {
		if("S000000".equals(nodeId) || nodeId==null || "".equals(nodeId)) {
			this.setMainMessage("请您选择一个代码集。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		CodeType codeType = bs6.getCodeTypeById(nodeId);
		List<AddValue> list = bs6.getSession().createQuery("from AddValue where codeType=:codeType")
	              .setParameter("codeType", codeType.getCodeType()).list();
		if(list.size()>0) {
			this.setMainMessage(codeType.getCodeType()+"该代码集已经被引用，不能删除。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "sureClear",nodeId);									//调用sureClear方法，执行脚本删除
		this.addNextEvent(NextEventValue.CANNEL, "");										        //其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);										        //消息框类型，即confirm类型窗口
		this.setMainMessage("您确定要删除"+nodeId+"该代码集吗？");											//窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 确认删除代码集
	 * @param nodeId
	 * @return
	 */
	@PageEvent("sureClear")
	public int sureClear(String nodeId) {
		CodeType codeType = (CodeType) bs6.getSession().createQuery("from CodeType where codeType = '"+nodeId+"'").list().get(0);
		//删除代码集
		bs6.getSession().createSQLQuery("delete from code_type where code_type='"+nodeId+"'").executeUpdate();
		bs6.getSession().createSQLQuery("delete from code_value where code_type='"+nodeId+"'").executeUpdate();
		try {
			new LogUtil().createLog("81", "CODETYPE", codeType.getCodeType(), codeType.getTypeName(), "删除自定义新代码集", new Map2Temp().getLogInfo(codeType,new CodeType()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.getExecuteSG().addExecuteCode("reloadTree();");
		this.getExecuteSG().addExecuteCode("document.getElementById('dataInterfaceFrame').contentWindow.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
