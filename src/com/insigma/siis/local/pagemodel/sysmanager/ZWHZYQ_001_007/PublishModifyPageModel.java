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
		this.getExecuteSG().addExecuteCode("getSNode();");									//������Ϣ���ݵ�ҳ����
		this.setNextEventName("queryLog");													//��Ӧ�¼�queryLog
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("disPlayDsa")
	@AutoNoMask
	public int disPlayDsa(String nodeId) throws RadowException{
		String[] nodeIds = nodeId.split("_");
		InterfaceConfig ifc = bs7.getConfigByIsn(nodeIds[1]);
		this.getExecuteSG().addExecuteCode("document.all.gridT.innerHTML='&nbsp;<span style=\"font-weight: normal;\">���ܰ���ʽӿڷ�����<span style=font-family:Arial;color:blue;font-weight:bold;>" + ifc.getInterfaceConfigId() + "</span>." + ifc.getInterfaceConfigName() + "</span>';");
		this.setNextEventName("list.dogridquery");											//��Ӧ�¼�list.dogridquery
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("list.dogridquery")
	@AutoNoMask
	public int listquery(int start, int limit) throws RadowException{	
		String nodeId = this.getPageElement("nodeId").getValue();			//��ȡҳ��ڵ���Ϣ
		String[] nodeIds = nodeId.split("_");
		StringBuffer sql = new StringBuffer();
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql.append("select "
					+ "interface_config_id,"
					+ "interface_config_name,"
					+ "decode(availability_state_id,'0','<img src=\"images/icon/error.gif\"></img>��Ч','1','<img src=\"images/icon/right.gif\"></img>��Ч') availability_state_id,"
					+ "decode(publish_state_id,'0','<img src=\"images/icon/error.gif\"></img>δ����','1','<img  src=\"images/icon/right.gif\"></img>����') publish_state_id,"
					+ "interface_config_create_user,"
					+ "interface_config_create_date "
					+ "from "
					+ "interface_config where interface_config_isn='"+nodeIds[1]+"'");
		} else if(DBType.MYSQL==DBUtil.getDBType()) {
			sql.append("select "
					+ "interface_config_id,"
					+ "interface_config_name,"
					+ "if(availability_state_id='0','<img src=\"images/icon/error.gif\"></img>��Ч','<img src=\"images/icon/right.gif\"></img>��Ч') availability_state_id,"
					+ "if(publish_state_id='0','<img src=\"images/icon/error.gif\"></img>δ����','<img  src=\"images/icon/right.gif\"></img>����') publish_state_id,"
					+ "interface_config_create_user,"
					+ "interface_config_create_date "
					+ "from "
					+ "interface_config where interface_config_isn='"+nodeIds[1]+"'");
		}
		this.pageQuery(sql.toString(), "sql", -1, limit);	
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ������ť��Ӧ�¼�
	 * @param interfaceConfigId
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("publish")
	@AutoNoMask
    public int publish(String interfaceConfigId) throws RadowException {
		InterfaceConfig ifc = bs7.getConfigById(interfaceConfigId);
		if("1".equals(ifc.getPublishStateId())) {
			this.setMainMessage(ifc.getInterfaceConfigName()+"�÷����ѷ�����");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		if("0".equals(ifc.getAvailabilityStateId())){
			this.setMainMessage(ifc.getInterfaceConfigName()+"�÷�����Ч���ܷ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		ifc.setPublishStateId("1");
		Transaction t = bs7.getSession().getTransaction();
		t.begin();
		bs7.getSession().save(ifc);
		t.commit();
		this.getExecuteSG().addExecuteCode("refresh()");
		this.setMainMessage(ifc.getInterfaceConfigName()+"�ӿڷ��������ɹ���");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("cutout")
	@AutoNoMask
    public int cutout(String interfaceConfigId) throws RadowException {
		InterfaceConfig ifc = bs7.getConfigById(interfaceConfigId);
		if("0".equals(ifc.getPublishStateId())) {
			this.setMainMessage(ifc.getInterfaceConfigName()+"�ӿڷ����ѱ���ֹ��");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		this.addNextEvent(NextEventValue.YES, "sureCut", interfaceConfigId);									//����sureClear������ִ�нű�ɾ��
		this.addNextEvent(NextEventValue.CANNEL, "");										//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM);										//��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷ��Ҫ��ֹ�÷�����ȷ���÷���������ֹ��");								//������ʾ��Ϣ	
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
