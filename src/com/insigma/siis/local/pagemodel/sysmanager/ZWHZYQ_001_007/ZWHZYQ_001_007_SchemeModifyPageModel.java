package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.util.HibernateSessionFactory;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceParameter;
import com.insigma.siis.local.business.entity.InterfaceParameterId;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_007_SchemeModifyPageModel extends PageModel{

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	String color = "Grey";												//������ɫΪ��ɫ���ַ���ֵ
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:queryObj.��ʼ�����ݷ��ʽӿڷ�����������ҳ��<br>
	* ������������:2016��03��7��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��7��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:��ʼ�����ݷ��ʽӿڷ�����������ҳ��<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:��ʼ�����ݷ��ʽӿڷ�����������ҳ��
	*====================================================================================================
	*/
	@SuppressWarnings("rawtypes")
	@PageEvent("queryObj")
	@NoRequiredValidate
	public int queryObj() throws RadowException {
		//[NO.001]��ȡҳ��Ԫ�س�ʼ�������б�
		String nodeId = this.getPageElement("nodeId").getValue();                                 //��ǰ���ڵ�Id,ͨ��Http���󴫵ݹ�������JSPҲ�����������
		String[] nodeId_s = nodeId.split("_");                                                    //��ΪnodeId���ΪFA+"_"+�����,����������Ҫȡ�������
		String opmode = this.getPageElement("opmode").getValue();				                  //��ȡ��ǰ����ģʽ���½������޸ı�־
		//[NO.002]����ҳ��Ԫ��Ĭ��ֵ
		String availableStatus = "1";												              //��Ч��״̬����Ϊ'1'
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();                          //ϵͳ��ǰ��¼�û���
		
		if(!"NEW".equals(opmode)) {
			InterfaceConfig interfaceConfig = bs7.getConfigByIsn(nodeId_s[1]);
			String OldLoginName = interfaceConfig.getInterfaceConfigCreateUser();                           //��ȡԭ������½��
			int interfaceConfigSeq = interfaceConfig.getInterfaceConfigSequence();                          //��ȡԭ���������
			String interfaceConfigId = interfaceConfig.getInterfaceConfigId();                              //��ȡԭ�����ı���
			String interfaceConfigName = interfaceConfig.getInterfaceConfigName();                          //��ȡԭ����������
			String interfaceConfigDesc = interfaceConfig.getInterfaceConfigDesc();                          //��ȡԭ����������
			availableStatus = interfaceConfig.getAvailabilityStateId();                                     //��ȡԭ��������Ч��״̬
			String lastLoginName = interfaceConfig.getInterfaceConfigChangeUser();
			String createDate = bs7.formatDate(interfaceConfig.getInterfaceConfigCreateDate());
			String changeDate = bs7.formatDate(interfaceConfig.getInterfaceConfigChangeDate());
			this.getPageElement("interfaceConfigSequence").setValue(interfaceConfigSeq+"");				    //�����ķ�����ż�1��ֵ�����
			this.getPageElement("interfaceConfigCreateUserName").setValue(OldLoginName);	                //��ʼ������������
			this.getPageElement("interfaceConfigChangeUserName").setValue(lastLoginName);	                //��ʼ����������޸���
			this.getPageElement("interfaceConfigCreateDate").setValue(createDate);                          //��ʼ����������ʱ��
			this.getPageElement("interfaceConfigChangeDate").setValue(changeDate);                          //��ʼ����������޸�ʱ��
			this.getPageElement("interfaceConfigStateId").setValue(availableStatus);                        //��ʼ����Ч��״̬
			this.getPageElement("interfaceConfigId").setValue(interfaceConfigId);
			this.getPageElement("interfaceConfigName").setValue(interfaceConfigName);
			this.getPageElement("interfaceConfigDesc").setValue(interfaceConfigDesc);
			
//			if("true".equals(interfaceConfig.getInterfaceConfigIsedit()) && "0".equals(interfaceConfig.getPublishStateId())){
//				this.getExecuteSG().addExecuteCode("document.all.icIsEdit.checked=true;");											//��ҳ����Ƿ�ɱ༭����Ϊ���Ա༭
//				this.getPageElement("interfaceConfigIsedit").setValue("true");														//��ҳ����Ƿ�ɱ༭����Ϊ���Ա༭
//				this.getPageElement("interfaceConfigId").setDisabled(false);
//				this.getPageElement("interfaceConfigStateId").setDisabled(false);
//				this.getPageElement("interfaceConfigSequence").setDisabled(false);
//				this.getPageElement("interfaceConfigName").setDisabled(false);
//				this.getPageElement("interfaceConfigDesc").setDisabled(false);
//				this.getExecuteSG().addExecuteCode("Ext.getCmp('addParam').enable();" + "Ext.getCmp('delParam').enable();" + "Ext.getCmp('refreshBtn').enable();");
//			}else{
//				this.getExecuteSG().addExecuteCode("document.all.icIsEdit.checked=false;");											//��ҳ����Ƿ�ɱ༭����Ϊ���ɱ༭
//				this.getPageElement("interfaceConfigIsedit").setValue("false");														//��ҳ����Ƿ�ɱ༭����Ϊ���ɱ༭
//				this.getPageElement("interfaceConfigStateId").setDisabled(true);
//				this.getPageElement("interfaceConfigId").setDisabled(true);
//				this.getPageElement("interfaceConfigSequence").setDisabled(true);
//				this.getPageElement("interfaceConfigName").setDisabled(true);
//				this.getPageElement("interfaceConfigDesc").setDisabled(true);
//				this.getExecuteSG().addExecuteCode("Ext.getCmp('addParam').disable();" + "Ext.getCmp('delParam').disable();" + "Ext.getCmp('refreshBtn').disable();");
//			}
			Grid grid = (Grid)this.createPageElement("list", ElementType.GRID, false);
			List<HashMap<String, Object>> gridlist = new ArrayList<HashMap<String, Object>>();
			List list = bs7.doQueryParam(interfaceConfig.getInterfaceConfigId());
			String str = null;
			for(int i = 0; i < list.size(); i++){
				HashMap dbhm = (HashMap)list.get(i);
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("interface_parameter_sequence", dbhm.get("interface_parameter_sequence"));
				hm.put("interface_parameter_name", dbhm.get("interface_parameter_name"));
				str = (String)dbhm.get("interface_parameter_type");
				if("1".equals(str)){
					hm.put("interface_parameter_type", "String");
				}else if("3".equals(str)){
					hm.put("interface_parameter_type", "Date");
				}else if("2".equals(str)){
					hm.put("interface_parameter_type", "Double");
				}
       		    hm.put("interface_parameter_desc", dbhm.get("interface_parameter_desc"));
       		    hm.put("interface_config_id", dbhm.get("interface_config_id"));
				gridlist.add(hm);
			}
			grid.setValueList(gridlist);
		} else if("NEW".equals(opmode)) {                                                               //�������ģʽΪNEW�����÷���Ϊ�½��ģ���ִ�����´���
			//[NO.003]��ʼ���½����ݷ��ʽӿڷ�������
			int maxSeq = bs7.getMaxInterfaceConfigSeq();								                //��ȡ���з������������
			int interfaceConfigSeq = maxSeq+1;	                                                        //�ӿڷ������
			this.getPageElement("interfaceConfigSequence").setValue(interfaceConfigSeq+"");				//�����ķ�����ż�1��ֵ�����
			this.getPageElement("interfaceConfigCreateUser").setValue(loginName);			            //����ǰ�û���¼����ֵ���������������ˣ�ҳ��Ԫ��ΪextractSchemeCreateUser
			this.getPageElement("interfaceConfigCreateUserName").setValue(bs7.getUserName(loginName));	//�����û�loginName����ȡ�û����֣�����ǰ�û����ָ�ֵ�������������������֣�ҳ��Ԫ��ΪextractSchemeCreateUser
			this.getPageElement("interfaceConfigChangeUser").setValue(loginName);						//����ǰ�û���¼����ֵ����������޸��ˣ�ҳ��Ԫ��ΪextractSchemeLastMUser
			this.getPageElement("interfaceConfigChangeUserName").setValue(bs7.getUserName(loginName));	//�����û�loginName����ȡ�û����֣�����ǰ�û����ָ�ֵ������޸������֣�ҳ��Ԫ��ΪextractSchemeCreateUser
			this.getPageElement("interfaceConfigCreateDate").setValue(bs7.formatDate(new Date()));      //��ʼ����������ʱ��
			this.getPageElement("interfaceConfigChangeDate").setValue(bs7.formatDate(new Date()));      //��ʼ����������޸�ʱ��
			this.getPageElement("interfaceConfigStateId").setValue(availableStatus);                    //��ʼ����Ч��״̬
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:save.����ҳ�����ݵĵ����Ӧ�¼�<br>
	* ������������:2016��03��7��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��3��7��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:����ҳ�����ݵĵ����Ӧ�¼�<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:�ύ������ҳ����������
	*====================================================================================================
	 * @throws AppException 
	*/
	@PageEvent("save.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int save() throws RadowException, AppException{
		String nodeId = this.getPageElement("nodeId").getValue();						//��ȡ�ڵ����
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();						//��ȡ��ǰ����ģʽ���½������޸ı�־
		
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();	//��ȡ��ȡ��������
		String interfaceConfigSequence = this.getPageElement("interfaceConfigSequence").getValue();
		String interfaceConfigName = this.getPageElement("interfaceConfigName").getValue();

		String availabilityStateId = this.getPageElement("interfaceConfigStateId").getValue();
		String interfaceConfigCreateDate = this.getPageElement("interfaceConfigCreateDate").getValue();
		String interfaceConfigDesc = this.getPageElement("interfaceConfigDesc").getValue();
		String interfaceConfigIsedit = this.getPageElement("interfaceConfigIsedit").getValue();
		if("".equals(interfaceConfigId) || interfaceConfigId == null){
			this.setMainMessage("���ã��ӿڷ������벻��Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(interfaceConfigSequence) || interfaceConfigSequence == null){
			this.setMainMessage("���ã��ӿڷ�����Ų���Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(interfaceConfigName) || interfaceConfigName == null){
			this.setMainMessage("���ã��ӿڷ������Ʋ���Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		InterfaceConfig config = null;					                                
		try {
			config = bs7.getConfigById(interfaceConfigId);                              //�����������û�иı�
		} catch (Exception e) {
			config = bs7.getConfigByIsn(nodeId_s[1]);                                   //�����������ı���
		}
		
		if("NEW".equals(opmode)){														//�жϵ�ǰ�����Ƿ����½��ķ���
			if(null != config){															
				if("1".equals(config.getAvailabilityStateId())){						
					color = "FF6820";
				}
				this.getExecuteSG().addExecuteCode("odin.alert('���ã����ݷ��ʽӿڷ�������\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + interfaceConfigId + "</b></span>\"�Ѵ��ڣ��������������ݷ��ʽӿڷ������롣');");
	            return EventRtnType.NORMAL_SUCCESS;
			}
			config = new InterfaceConfig();												//����������ȡ����
	    }
		
		InterfaceConfig config1 = new InterfaceConfig();
		//��������
		try {
			PropertyUtils.copyProperties(config1, config);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		config.setInterfaceConfigId(interfaceConfigId);
		config.setInterfaceConfigName(interfaceConfigName);
		config.setInterfaceConfigIsn(nodeId_s[1]);										//���ڵ���븳ֵ����ȡ��������
		config.setInterfaceConfigDesc(interfaceConfigDesc);
		config.setInterfaceConfigIsedit(interfaceConfigIsedit);
		config.setPublishStateId("0");                                                  //��һ�α�������Ϊδ����״̬
		Date date = new Date();															//�������ڶ���
		String dateStr = bs7.formatDate(date);										    //��ʽ�����ڶ���Ϊ"yyyy-MM-dd"
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		String userName = bs7.getUserName(loginName);
		if("UPDATE".equals(opmode)){													//�жϵ�ǰ����ģʽ�Ƿ�Ϊ"UPDATE"
			config.setInterfaceConfigCreateDate(bs7.parseDateStr(interfaceConfigCreateDate));
			config.setInterfaceConfigChangeDate(date);									//�����޸�״̬�����·�������޸�����
			config.setInterfaceConfigChangeUser(userName);
		}else{
			config.setInterfaceConfigCreateDate(date);									//���´���״̬��Ϊ�������ڸ�ֵ��ǰʱ��
			config.setInterfaceConfigChangeDate(date);									//��������޸�״̬��Ϊ����޸����ڸ�ֵ��ǰʱ��
			config.setInterfaceConfigCreateUser(userName);
			config.setInterfaceConfigChangeUser(userName);
		}	
		config.setInterfaceConfigSequence(Integer.valueOf(interfaceConfigSequence));
		config.setAvailabilityStateId(availabilityStateId);
		if(null == config.getAvailabilityStateId() || "root".equals(config.getAvailabilityStateId()) || "".equals(config.getAvailabilityStateId())){
			throw new RadowException("���ã����ݷ��ʽӿڷ�����Ч��״̬����Ϊ�ա�");
		}																				
		
		//Session session = HibernateSessionFactory.currentSession();
		HBSession  session = HBUtil.getHBSession();
		//Transaction t = session.beginTransaction();
		//bs7.getSession().save(config);
		//session.save(config);
		session.saveOrUpdate(config);
		session.flush();
		//���淽�����¼��־
		if("UPDATE".equals(opmode)){
			try {
				new LogUtil().createLog("91", "InterfaceConfig",config.getInterfaceConfigIsn(), config.getInterfaceConfigName(), "", new Map2Temp().getLogInfo(config1, config));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("NEW".equals(opmode)){
			try {
				new LogUtil().createLog("90", "InterfaceConfig",config.getInterfaceConfigIsn(), config.getInterfaceConfigName(), "", new Map2Temp().getLogInfo(new InterfaceConfig(), config));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		/** ����������ز��� */
		List<HashMap<String, String>> method = this.getPageElement("list").getStringValueList();

		String str = null;
		for(int i = 0; i < method.size(); i++){
			//�������
			HashMap<String, String> hm = method.get(i);
			InterfaceParameter ip = new InterfaceParameter();
			InterfaceParameter ip1 = new InterfaceParameter();
			InterfaceParameterId ipid = new InterfaceParameterId();
			ipid.setInterfaceConfigId(config.getInterfaceConfigId());
			boolean isHave = false;//ҳ���е����������ݿ��Ƿ��Ѵ��ڵı�־
			//�߼��ж�
			if("".equals(hm.get("interface_parameter_name")) || hm.get("interface_parameter_name") == null){
				this.setMainMessage("���ã��������벻��Ϊ�ա�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			ipid.setInterfaceParameterName(hm.get("interface_parameter_name"));
			if("".equals(hm.get("interface_parameter_type")) || hm.get("interface_parameter_type") == null){
				this.setMainMessage("���ã��������Ͳ���Ϊ�ա�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//���ݷ�����źͷ���������Ų�ѯ���������Ƿ��Ѵ���
			String queryInterfaceparameter = "from InterfaceParameter t where t.id.interfaceConfigId='"+interfaceConfigId+"' and t.interfaceParameterSequence='"+hm.get("interface_parameter_sequence")+"' ";
			List ip_list =   bs7.getSession().createQuery(queryInterfaceparameter).list();
			if(ip_list.size()>0){
				ip = (InterfaceParameter) ip_list.get(0);
				isHave = true;
				//��������
				try {
					PropertyUtils.copyProperties(ip1, ip);
					deleteIp(interfaceConfigId,hm.get("interface_parameter_sequence"));
					ip = new InterfaceParameter();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			ip.setId(ipid);
			str = hm.get("interface_parameter_type").toLowerCase();
			if("string".equals(str) || "1".equals(str)){
				ip.setInterfaceParameterType("1");
			}else if("date".equals(str) || "3".equals(str)){
				ip.setInterfaceParameterType("3");
			}else if("double".equals(str) || "2".equals(str)){
				ip.setInterfaceParameterType("2");
			}
			ip.setInterfaceParameterDesc(hm.get("interface_parameter_desc"));
			ip.setInterfaceParameterSequence(Integer.parseInt(hm.get("interface_parameter_sequence")));
			List list = new ArrayList();
			session.save(ip);
			session.flush();
			//�����¼ʱ����¼��־
			if(isHave){
				try{
					//�Ƚ����������в�ֵͬ���ֶ�
					list = new Map2Temp().getLogInfo(ip1, ip);
					if(list.size()>0){
						new LogUtil().createLog("93", "InterfaceParameter",ip1.getId().getInterfaceParameterName(), ip1.getInterfaceParameterDesc(), "", new Map2Temp().getLogInfo(ip1, ip));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					new LogUtil().createLog("94", "InterfaceParameter",ip.getId().getInterfaceParameterName(), ip.getInterfaceParameterDesc(), "", new Map2Temp().getLogInfo(new InterfaceParameter(), ip));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}	
		//t.commit();
		session.getSession().clear();
		//[NO.005]����UI
		if("NEW".equals(opmode)){																	//�жϲ���ģʽ�Ƿ�ΪNEW����Ϊ�½�����
			this.getPageElement("interfaceConfigChangeDate").setValue(dateStr);						//���ó�ȡ��������޸�ʱ��Ϊ��ǰʱ��
			/** ��¼��־ */
		}else{
			this.getPageElement("interfaceConfigChangeDate").setValue(dateStr);						//��������½���������ֻ���·�������޸�ʱ��
			/** ��¼��־ */
		}
		this.getPageElement("interfaceConfigName").setValue(config.getInterfaceConfigName());		//�������ƣ�Ϊ�հף�Ĭ��ֵ'extractSchemeId'Ӱ��
		this.getPageElement("opmode").setValue("UPDATE");                           				//���ò���ģʽΪ����ģʽ
		this.setMainMessage("���ã�\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"���ݷ��ʽӿڷ����Ѿ�����ɹ���");//�����Ի�����ʾ���ݷ��ʽӿڷ�������ɹ�
		//����ͼ��
		this.getExecuteSG().addExecuteCode("window.parent.updateTab('<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "',0);");//���µ�ǰҳ�����ʾ����
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");							//���ø�ҳ��ˢ�·�����ˢ�µ�ǰҳ��
		this.getExecuteSG().addExecuteCode("window.parent.deletehaveChange('" + nodeId + "');");	//���ø�ҳ��ɾ������
		this.setNextEventName("queryObj");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	*====================================================================================================
	* ��������:delOnclick.����������Ϣҳ����ɾ����ť�ĵ����Ӧ�¼�<br>
	* ������������:2016��03��9��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��04��9��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:����������Ϣҳ����ɾ����ť�ĵ����Ӧ�¼�<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:ɾ��������Ϣ�ͽű���Ϣ
	*====================================================================================================
	*/
	@PageEvent("delParam.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int delOnclick() throws RadowException{
		PageElement pe = this.getPageElement("list");					//��ȡҳ����Ԫ�ض���
		List<HashMap<String, Object>> list = pe.getValueList();			//��ҳ����Ԫ�ش���ڹ�ϣ����
		if(null == list || list.size() == 0){							//�ж��б����Ƿ�������
			this.setMainMessage("��ã����ݷ��ʽӿڷ�����û�в���������Ϣ������ɾ����");					//�����û�����ݣ�������ʾ��
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!isChoosed()){												//�ж��Ƿ�ѡ�б������
			this.setMainMessage("���ã���δѡ�����������Ϣ����ѡ��Ҫɾ���Ĳ���������Ϣ��");	//����Ϊѡ�����������Ϣ��ʾ��
			return EventRtnType.NORMAL_SUCCESS;
		}
		String strParamNames = "";
		for(int i = 0; i < list.size(); i++){							//�Բ�����¼����Ϊ����ѭ��
			HashMap<String, Object> hm = list.get(i);					//ȡ��һ������������Ϣ������ϣ����
			Object selected = hm.get("selected");						//�жϸ���������Ϣ�Ƿ�ѡ��
			if(selected.equals(true) || selected.equals("1")){			//�����ѡ��
				strParamNames += "," + hm.get("interface_parameter_name").toString();//ƴ�ӱ�ѡ�еĲ������봮
			}
		}
		strParamNames = strParamNames.substring(1);							//ɾ���������봮��ǰ��Ķ��š�,��
		if(strParamNames==null || "".equals(strParamNames)) {
			this.setNextEventName("list.dogridquery");	
			return EventRtnType.NORMAL_SUCCESS;
		}
		String message = "���ã���ȷ��Ҫɾ����ǰѡ�еķ���������Ϣ��ѡ��ȷ����ť��ɾ����";		//��ʾɾ����Ϣ
		this.addNextEvent(NextEventValue.YES, "delParam", strParamNames);		//���ȷ������תִ��del�����������ݲ������봮
		this.addNextEvent(NextEventValue.CANNEL, "");					//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM);					//��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(message);									//������ʾ��Ϣ	
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	*====================================================================================================
	* ��������:delParam.ִ�з�������ɾ������<br>
	* ������������:2016��03��9��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��9��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:ִ�з�������ɾ������<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:ɾ����������ɾ������
	*====================================================================================================
	*/
	@PageEvent("delParam")
	@NoRequiredValidate
	public int del(String strParamNames) throws RadowException{
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();	//��ȡ��������
		String[] paramNames = strParamNames.split(",");									//�ö��ŷָ��������봮
		Transaction t = bs7.getSession().getTransaction();								//�����������
		t.begin();																		//����ʼ
		for(String paramName : paramNames){
			//InterfaceParameter ip = bs7.queryConfigParam(interfaceConfigId, paramName);
			bs7.delConfigParam(interfaceConfigId, paramName);							//�����������ݷ��ʽӿڷ������롢�������룬ɾ����Ӧ�����ݷ��ʽӿڷ�����Ӧ�ķ�������������Ϣ
			try {
				new LogUtil().createLog("95", "InterfaceParameter",interfaceConfigId, paramName, "", new ArrayList());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		t.commit();																		//�����ύ
		
		this.setNextEventName("list.dogridquery");										//������һ��������������Ϊlist.dogridquery
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:delBtnOnclick.ɾ��������Ϣ�ͽű���Ϣ�ĵ����Ӧ�¼�<br>
	* ������������:2016��03��9��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��9��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:ɾ��������Ϣ�ͽű���Ϣ�ĵ����Ӧ�¼�<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:ɾ��������Ϣ�ͽű���Ϣ
	*====================================================================================================
	*/
	@PageEvent("delBtn.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int delBtnOnclick() throws RadowException{
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();		//��ȡ���ݷ��ʽӿڷ�������
		String opmode = this.getPageElement("opmode").getValue();							//��ȡ��ǰ����ģʽ
		String message = "";
		if("UPDATE".equals(opmode)){														//�жϲ���ģʽ�Ƿ�Ϊ����
			InterfaceConfig config = bs7.getConfigById(interfaceConfigId);					//���ݷ������룬������ȡ��������
			List<InterfaceScript> ifslist = bs7.getScripts(interfaceConfigId);				//�������ݷ��ʽӿڷ������룬��ȡ�÷����¶�Ӧ���ݷ��ʽӿڷ����ű���Ϣ
			if(ifslist.size() > 0){															//�жϸ��������Ƿ��з����ű�
				this.setMainMessage("���ã������ݷ��ʽӿڷ�����<span style=\"font-family:Arial;display:inline-block;color:red;font-size:11px;\"><b>" + ifslist.size() + "</b></span>���������ݷ��ʽӿڽű���Ϣ��������ɾ����");			//������ʾ��Ϣ
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("1".equals(config.getAvailabilityStateId())){								//�жϷ�����Ч�Ա����Ƿ�Ϊ"1"
				color = "FF6820";
			}
			message = "���ã���ȷ��Ҫɾ��\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"���ݷ��ʽӿڷ��������Ӧ�ķ�������������Ϣ��ѡ��ȷ����ť��ɾ����";//����ɾ��ȷ����ʾ��
		}else{
			message = "���ã���ȷ��Ҫɾ����ǰ�½��е����ݷ��ʽӿڷ�����Ϣ��ѡ��ȷ����ť��ɾ����";						//ɾ���½��ķ�����Ϣ��ʾ��Ϣ
		}
		this.addNextEvent(NextEventValue.YES, "sureClear");									//���ȷ����ť��ִ��sureClear
		this.addNextEvent(NextEventValue.CANNEL, "");										//���ȡ����ť���˳�
		this.setMessageType(EventMessageType.CONFIRM);										//��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(message);														//������ʾ��Ϣ	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:sureClear.ɾ��������Ϣ�ͽű���Ϣ�ĵ����Ӧ�¼�<br>
	* ������������:2016��03��9��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��9��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:ɾ��������Ϣ�ͽű���Ϣ�ĵ����Ӧ�¼�<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:ɾ��������Ϣ�ͽű���Ϣ
	*====================================================================================================
	*/
	@PageEvent("sureClear")
	@NoRequiredValidate
	public int sureClear() throws RadowException{
		String nodeId = this.getPageElement("nodeId").getValue();					//��ȡ��������
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();					//��ȡ��ǰ����ģʽ
		if("UPDATE".equals(opmode)){												//�жϲ���ģʽ�Ƿ��Ǹ���
			InterfaceConfig config = bs7.getConfigByIsn(nodeId_s[1]);						//���ݷ������룬����������Ϣ����
			if("1".equals(config.getAvailabilityStateId())){						//�ж���Ч�Ա����Ƿ�Ϊ"1"
				color = "FF6820";
			}
			try{
				bs7.delConfig(config.getInterfaceConfigId());						//�����������ݷ��ʽӿڷ������룬ɾ����Ӧ�����ݷ��ʽӿڷ�����Ϣ�����Ӧ�ķ�������������Ϣ
				bs7.delConfigParameters(config.getInterfaceConfigId());				//�����������ݷ��ʽӿڷ������룬ɾ����Ӧ�����ݷ��ʽӿڷ�����Ӧ�ķ�������������Ϣ
				/** ɾ��ʱ��¼��־*/
				new LogUtil().createLog("92", "InterfaceConfig",config.getInterfaceConfigIsn(), config.getInterfaceConfigName(), "", new ArrayList());
				
				this.setMainMessage("���ã�ɾ��\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"���ݷ��ʽӿڷ����ɹ���");//����ɾ���ɹ���ʾ��
			}catch(Exception e){
				/** ��¼��־  ����Ӵ���*/
				this.setMainMessage("���ã�ɾ��\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"���ݷ��ʽӿڷ���ʧ�ܡ�");//��������ɾ��ʧ����ʾ��
				throw new RadowException("ɾ��ʧ��:" + e.getMessage());                 //�׳�ɾ��ʧ���쳣
			}
		}
		//[NO.001]ɾ����Ӧ�����ڵ�
		this.getExecuteSG().addExecuteCode("window.parent.delTreeNode('" + nodeId + "');"); //�������ڵ���룬ɾ�������ڵ�
		this.getExecuteSG().addExecuteCode("window.parent.removeTab('" + nodeId + "');");   //���ݽڵ�ȱ��룬�ر�tabҳ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:gridQuery.��ѯ������������<br>
	* ������������:2016��03��9��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��9��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:��ѯ������������<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				   int
	* </table>
	* ����ṹ����:��ѯ�����������ã�ˢ�²����б�
	*====================================================================================================
	*/
	@PageEvent("list.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	@GridDataRange
	public int gridQuery(int start, int limit) throws Exception {
		String configId = this.getPageElement("interfaceConfigId").getValue();			//��ȡ��ǰ��������
		InterfaceConfig config = bs7.getConfigById(configId);							//���ݷ������룬������������
		String sql = "select t.INTERFACE_CONFIG_ID,t.INTERFACE_PARAMETER_SEQUENCE,"
				   + "t.INTERFACE_PARAMETER_NAME,t.INTERFACE_PARAMETER_DESC,t.INTERFACE_PARAMETER_TYPE "
				   + "from interface_parameter t";									    //��ѯ����������Ϣ
		if(null == config)
			sql += " where 1=2";
		else
			sql += " where t.INTERFACE_CONFIG_ID='" + config.getInterfaceConfigId() + "' order by t.INTERFACE_PARAMETER_SEQUENCE";//��ѯ���׷����������
		this.pageQuery(sql, "SQL", start, 100);								            //�����ҳ��ѯ
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * �жϲ����б��в����Ƿ�ѡ��
	 * @return
	 * @throws RadowException
	 */
	private boolean isChoosed() throws RadowException{
		PageElement pe = this.getPageElement("list");				//��ȡҳ�������
		List<HashMap<String, Object>> list = pe.getValueList(); 	//��������ݶ�������ϣ��
		for(int i = 0; i < list.size(); i++){						//����������ѭ��
			HashMap<String, Object> map = list.get(i);				//��ȡ��ϣ���ŵĶ���
			Object selected = map.get("selected");					//��ȡҳ��ѡ���ֶ�Ԫ�ض���
			if(selected == null){
				return false;
			}
			if(selected.equals(true) || selected.equals("1")){		//�ж��Ƿ�ѡ�б�־
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �޸Ĳ�������ʱ��ɾ��������
	 * @param interfaceConfigId
	 * @param seq
	 */
	public void deleteIp(String interfaceConfigId,String seq){
		ZWWebserviceImpl zim = new ZWWebserviceImpl();
		Connection conn = zim.getConn();
		PreparedStatement pst = null;
		String sql = "delete  from Interface_Parameter  where interface_Config_Id=? and interface_Parameter_Sequence=? ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, interfaceConfigId);
			pst.setString(2, seq);
			pst.executeUpdate();
			if (pst != null)pst.close();
			if (conn != null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(pst!=null)pst.close();
			} catch (SQLException e1) {
			}
		}
		
	}
}
