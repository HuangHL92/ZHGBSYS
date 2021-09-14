package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import org.hibernate.Transaction;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;

public class PublishModifyPageModel extends PageModel{

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	
	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("getSNode();");									//将其信息传递到页面上
		this.setNextEventName("queryLog");													//响应事件queryLog
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("disPlayDsa")
	@AutoNoMask
	public int disPlayDsa(String nodeId) throws RadowException{
		String[] nodeIds = nodeId.split("_");
		InterfaceConfig ifc = bs7.getConfigByIsn(nodeIds[1]);
		this.getExecuteSG().addExecuteCode("document.all.gridT.innerHTML='&nbsp;<span style=\"font-weight: normal;\">汇总版访问接口方案：<span style=font-family:Arial;color:blue;font-weight:bold;>" + ifc.getInterfaceConfigId() + "</span>." + ifc.getInterfaceConfigName() + "</span>';");
		this.setNextEventName("list.dogridquery");											//响应事件list.dogridquery
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("list.dogridquery")
	@AutoNoMask
	public int listquery(int start, int limit) throws RadowException{	
		String nodeId = this.getPageElement("nodeId").getValue();			//获取页面节点信息
		String[] nodeIds = nodeId.split("_");
		StringBuffer sql = new StringBuffer();
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql.append("select "
					+ "interface_config_id,"
					+ "interface_config_name,"
					+ "decode(availability_state_id,'0','<img src=\"images/icon/error.gif\"></img>无效','1','<img src=\"images/icon/right.gif\"></img>有效') availability_state_id,"
					+ "decode(publish_state_id,'0','<img src=\"images/icon/error.gif\"></img>未发布','1','<img  src=\"images/icon/right.gif\"></img>发布') publish_state_id,"
					+ "interface_config_create_user,"
					+ "interface_config_create_date "
					+ "from "
					+ "interface_config where interface_config_isn='"+nodeIds[1]+"'");
		} else if(DBType.MYSQL==DBUtil.getDBType()) {
			sql.append("select "
					+ "interface_config_id,"
					+ "interface_config_name,"
					+ "if(availability_state_id='0','<img src=\"images/icon/error.gif\"></img>无效','<img src=\"images/icon/right.gif\"></img>有效') availability_state_id,"
					+ "if(publish_state_id='0','<img src=\"images/icon/error.gif\"></img>未发布','<img  src=\"images/icon/right.gif\"></img>发布') publish_state_id,"
					+ "interface_config_create_user,"
					+ "interface_config_create_date "
					+ "from "
					+ "interface_config where interface_config_isn='"+nodeIds[1]+"'");
		}
		this.pageQuery(sql.toString(), "sql", -1, limit);	
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 发布按钮响应事件
	 * @param interfaceConfigId
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("publish")
	@AutoNoMask
    public int publish(String interfaceConfigId) throws RadowException {
		InterfaceConfig ifc = bs7.getConfigById(interfaceConfigId);
		if("1".equals(ifc.getPublishStateId())) {
			this.setMainMessage(ifc.getInterfaceConfigName()+"该方案已发布。");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		if("0".equals(ifc.getAvailabilityStateId())){
			this.setMainMessage(ifc.getInterfaceConfigName()+"该方案无效不能发布。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		ifc.setPublishStateId("1");
		Transaction t = bs7.getSession().getTransaction();
		t.begin();
		bs7.getSession().save(ifc);
		t.commit();
		this.getExecuteSG().addExecuteCode("refresh()");
		this.setMainMessage(ifc.getInterfaceConfigName()+"接口方案发布成功。");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("cutout")
	@AutoNoMask
    public int cutout(String interfaceConfigId) throws RadowException {
		InterfaceConfig ifc = bs7.getConfigById(interfaceConfigId);
		if("0".equals(ifc.getPublishStateId())) {
			this.setMainMessage(ifc.getInterfaceConfigName()+"接口方案已被终止。");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		this.addNextEvent(NextEventValue.YES, "sureCut", interfaceConfigId);									//调用sureClear方法，执行脚本删除
		this.addNextEvent(NextEventValue.CANNEL, "");										//其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);										//消息框类型，即confirm类型窗口
		this.setMainMessage("您确定要终止该方案吗？确定该方案将被终止。");								//窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("sureCut")
	@AutoNoMask
    public int sureCut(String interfaceConfigId) throws RadowException {
		InterfaceConfig ifc = bs7.getConfigById(interfaceConfigId);
		ifc.setPublishStateId("0");
		Transaction t = bs7.getSession().getTransaction();
		t.begin();
		bs7.getSession().save(ifc);
		t.commit();
		this.getExecuteSG().addExecuteCode("refresh()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
}
