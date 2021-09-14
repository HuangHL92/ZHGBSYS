package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_007_ScriptModifyPageModel extends PageModel {

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	String color = "Grey";									//������ɫΪ��ɫ
	@Override
	public int doInit() throws RadowException {
		return 0;
	}
	
	/**
	*====================================================================================================
	* ��������:queryObj.��ʼ�������ű���������ҳ��<br>
	* ������������:2016��03��9��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��9��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:��ʼ�������ű���������ҳ��<br>
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
	* ����ṹ����:��ʼ�������ű���������ҳ��
	*====================================================================================================
	*/
	@PageEvent("queryObj")
	@NoRequiredValidate
	@AutoNoMask
	public int queryObj()throws RadowException{	
		String nodeId = this.getPageElement("nodeId").getValue();						//��ȡ�����ڵ����
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();						//��ȡ����ģʽ��Ϊ�½������ѽ�
		String preNodeId = this.getPageElement("preNodeId").getValue();					//��ȡ�����ű����ڵ����
		String[] preNodeId_s = preNodeId.split("_");
		InterfaceConfig config = bs7.getConfigByIsn(preNodeId_s[1]);					//���ݸ��ڵ���룬��ȡ�ű���������
		this.getExecuteSG().addExecuteCode(
		"document.all.gridT.innerHTML='&nbsp;<span style=\"font-weight: normal;\">����������" + 
		config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "</span>';");//��ʵ�������ϵ��ı�
		//[NO.001]����ҳ��Ԫ��Ĭ��ֵ
		String availableStatus = "1";													//"1"������Ч
		this.getPageElement("interfaceConfigId").setValue(config.getInterfaceConfigId());
		if(!"NEW".equals(opmode)){														//�жϲ���ģʽ�Ƿ�Ϊ�½�ģʽ
			InterfaceScript script = bs7.getScriptByIsn(nodeId_s[1]);
			String createUserName = script.getInterfaceScriptCreateUser();
			String changeUserName = script.getInterfaceScriptChangeUser();
			this.getPageElement("interfaceScriptId").setValue(script.getInterfaceScriptId());
			this.getPageElement("interfaceScriptSequence").setValue(script.getInterfaceScriptSequence()+"");
			this.getPageElement("interfaceScriptName").setValue(script.getInterfaceScriptName());
			this.getPageElement("interfaceScriptIsedit").setValue(script.getInterfaceScriptIsedit());
			this.getPageElement("interfaceScriptDesc").setValue(script.getInterfaceScriptDesc());
			this.getPageElement("interfaceScriptStateId").setValue(script.getAvailabilityStateId());
			this.getPageElement("interfaceScriptCreateUserName").setValue(createUserName);
			this.getPageElement("interfaceScriptChangeUserName").setValue(changeUserName);
			this.getPageElement("interfaceScriptCreateDate").setValue(bs7.formatDate(script.getInterfaceScriptCreateDate()));
			this.getPageElement("interfaceScriptChangeDate").setValue(bs7.formatDate(script.getInterfaceScriptChangeDate()));
		    this.getPageElement("targetTableName").setValue(script.getTargetTableName());
//			if("true".equals(script.getInterfaceScriptIsedit()) && "0".equals(config.getPublishStateId())){
//				this.getExecuteSG().addExecuteCode("document.all.isIsEdit.checked=true;");											//��ҳ����Ƿ�ɱ༭����Ϊ���Ա༭
//				this.getPageElement("interfaceScriptIsedit").setValue("true");														//��ҳ����Ƿ�ɱ༭����Ϊ���Ա༭
//				this.getPageElement("interfaceScriptSequence").setDisabled(false);
//				this.getPageElement("interfaceScriptName").setDisabled(false);
//				this.getPageElement("interfaceScriptDesc").setDisabled(false);
//				this.getPageElement("interfaceScriptStateId").setDisabled(false);
//				this.getPageElement("editScript").setDisabled(false);
//				this.getPageElement("targetTableName").setDisabled(false);
//			}else{
//				this.getExecuteSG().addExecuteCode("document.all.isIsEdit.checked=false;");											//��ҳ����Ƿ�ɱ༭����Ϊ���ɱ༭
//				this.getPageElement("interfaceScriptIsedit").setValue("false");														//��ҳ����Ƿ�ɱ༭����Ϊ���ɱ༭
//				this.getPageElement("interfaceScriptSequence").setDisabled(true);
//				this.getPageElement("interfaceScriptName").setDisabled(true);
//				this.getPageElement("interfaceScriptDesc").setDisabled(true);
//				this.getPageElement("interfaceScriptStateId").setDisabled(true);
//				this.getPageElement("editScript").setDisabled(true);
//				this.getPageElement("targetTableName").setDisabled(true);
//			}
		}else{
			String str = bs7.getAutoScriptId(config.getInterfaceConfigId());														//��ȡϵͳ�Զ����ɵĳ�ȡ�ɼ��ű�id
			this.getPageElement("interfaceScriptId").setValue(str);																	//���Զ���ȡ�ĳ�ȡ�ű�id��ֵ����ȡ�ű���������
			String seq = bs7.getMaxInterfaceScriptSeq(config.getInterfaceConfigId())+1+"";													//�Զ���ȡ���
			if(seq.length() <= 5)																									//��ų�����Χ����ʱ�ᱨ��
				this.getPageElement("interfaceScriptSequence").setValue(seq);														//��ȡ����Ÿ�ֵ��ҳ��Ԫ��interfaceScriptSequence
			String loginName = SysUtil.getCacheCurrentUser().getLoginname(); 																//ϵͳ��ǰ�û���¼��
			this.getPageElement("interfaceScriptCreateUser").setValue(loginName);													//���ó�ȡ�ű�����������
			this.getPageElement("interfaceScriptCreateUserName").setValue(bs7.getUserName(loginName));								//���ó�ȡ�ű�����������
			this.getPageElement("interfaceScriptChangeUser").setValue(loginName);													//���ýű�����޸�������
			this.getPageElement("interfaceScriptChangeUserName").setValue(bs7.getUserName(loginName));								//���ýű�����޸�������
			this.getPageElement("interfaceScriptCreateDate").setValue(bs7.formatDate(new Date()));
			this.getPageElement("interfaceScriptStateId").setValue(availableStatus);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �༭�ű���ť�¼�
	 * @return
	 * @throws RadowException
	 * @throws UnsupportedEncodingException
	 */
	@PageEvent("editScript.onclick")
	@NoRequiredValidate
	@GridDataRange
	public int editScript() throws RadowException, UnsupportedEncodingException{
		this.getExecuteSG().addExecuteCode("window.getSqlValue();");
		this.setNextEventName("open");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �򿪱༭�ű�ҳ��
	 * @return
	 * @throws RadowException
	 * @throws UnsupportedEncodingException
	 */
	@PageEvent("open")
	@NoRequiredValidate
	@GridDataRange
	public int open() throws RadowException, UnsupportedEncodingException{
		String sqlText = this.getPageElement("sqlText").getValue(); 
		sqlText = java.net.URLEncoder.encode(java.net.URLEncoder.encode(sqlText, "UTF-8"), "UTF-8");//��������������⣬����������������utf-8���б���
		request.getSession().setAttribute("sqlText", new String(sqlText));
		this.openWindow("ScriptEdit", "pages.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_ScriptEdit");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	*====================================================================================================
	* ��������:save.����ҳ�����ݵĵ����Ӧ�¼�<br>
	* ������������:2016��03��11��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��11��<br>
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
	* ����ṹ����:�ύ����ҳ����������
	*====================================================================================================
	*/
	@PageEvent("save")
	@AutoNoMask
	public int save() throws RadowException{
		String nodeId = this.getPageElement("nodeId").getValue();										//��ȡ��ǰ�����ű�����
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();										//��ȡ��ǰҳ��Ĳ���ģʽ���½�������
		String preNodeId = this.getPageElement("preNodeId").getValue();									//��ȡԭ�����ű����룬���ڼ�¼��־
		String[] preNodeId_s = preNodeId.split("_");
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();
		String interfaceScriptId = this.getPageElement("interfaceScriptId").getValue();					//��ȡ��ȡ�ű�����
		String interfaceScriptSequence = this.getPageElement("interfaceScriptSequence").getValue();
		String interfaceScriptName = this.getPageElement("interfaceScriptName").getValue();
		String targetTableName = this.getPageElement("targetTableName").getValue();                     //��ȡ���ݼ�����
		String sqlText = this.getPageElement("sqlText").getValue();									    //��ȡ��ѯ����ı�
		
		String interfaceScriptStateId = this.getPageElement("interfaceScriptStateId").getValue();
		String preInterfaceScriptCreateDate = this.getPageElement("interfaceScriptCreateDate").getValue();
		
		InterfaceConfig config = bs7.getConfigByIsn(preNodeId_s[1]);									//ͨ����ȡ�ɼ��������룬��ȡ��ȡ�ɼ����������������ڼ�¼��־
		
		if("".equals(interfaceScriptSequence) || interfaceScriptSequence == null){
			this.setMainMessage("���ã��ӿڽű���Ų���Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(interfaceScriptName) || interfaceScriptName == null){
			this.setMainMessage("���ã��ӿڽű����Ʋ���Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(sqlText.trim()) || sqlText == null){												//�жϲ�ѯ���Ϊ�գ�Ϊ�����׳��쳣��ʾ��
			this.setMainMessage("���ã��ӿڽű�����Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(targetTableName) || targetTableName == null){												//�жϲ�ѯ���Ϊ�գ�Ϊ�����׳��쳣��ʾ��
			this.setMainMessage("���ã����ݼ�����Ϊ�ա�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		InterfaceScript script = bs7.getScriptByIsn(nodeId_s[1]);				                        //ͨ�������ͽű�id��ȡ���ű�����
		InterfaceScript script1 = new InterfaceScript();
		
		if("NEW".equals(opmode)){ 																		//�жϲ���ģʽ�Ƿ�Ϊ�½�
			if(null != script){																			//�жϽű������Ƿ�Ϊ��
				if("1".equals(script.getAvailabilityStateId())){										//�ж���Ч��״̬�����Ƿ�Ϊ1
					color = "767702";
				}
				this.getExecuteSG().addExecuteCode("odin.alert('���ã����ݷ��ʽӿڷ����ű�����\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + interfaceScriptId + "</b></span>\"�Ѵ��ڣ�ϵͳ���Զ��������ݷ��ʽӿڷ����ű����롣');");
				String str = this.getPageElement("interfaceScriptId").getValue();									//��ȡϵͳ�Զ����ɵĽű�����
				this.getPageElement("interfaceScriptId").setValue(str);									//��ϵͳ�Զ����ɵĽű����븳ֵ��ҳ���ֶ�interfaceScriptId
	            return EventRtnType.NORMAL_SUCCESS;
			}
			if("1".equals(config.getPublishStateId())){
				config.setPublishStateId("0");
				this.setMainMessage("��Ҫ���·����ӿڷ��ʷ���");
			}
			script = new InterfaceScript();																//���´����ű�����
	    }else{
	    	//��������
			try {
				PropertyUtils.copyProperties(script1, script);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	    }
		script.setInterfaceScriptSql(sqlText);															//����ѯ���ű�ͬ�������ݿ���
		script.setTargetTableName(targetTableName);
		script.setInterfaceScriptName(interfaceScriptName);												//����ȡ�ű�����ͬ�������ݿ���
		script.setInterfaceConfigId(interfaceConfigId);													//���ű�����ͬ�������ݿ���
		script.setInterfaceScriptId(interfaceScriptId);
		script.setInterfaceScriptIsn(nodeId_s[1]);														//�������ű�����ͬ�������ݿ���
		script.setInterfaceScriptDesc(this.getPageElement("interfaceScriptDesc").getValue());
		script.setInterfaceScriptIsedit(this.getPageElement("interfaceScriptIsedit").getValue());
		Date date = new Date();																			//�������ڶ���
		String dateStr = bs7.formatDate(date);															//���ڳ�ʼ�������޸ķ�������		
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();                                //��ȡ��ǰϵͳ��½��
		String userName = bs7.getUserName(loginName);                                                   //��ȡ�û�����
		script.setInterfaceScriptChangeDate(date);
		script.setInterfaceScriptChangeUser(userName);
		script.setAvailabilityStateId(interfaceScriptStateId);
		if("UPDATE".equals(opmode)){																	//�жϲ��������Ƿ�ΪUPDATE
			script.setInterfaceScriptCreateDate(bs7.parseDateStr(preInterfaceScriptCreateDate));
		}else{
			script.setInterfaceScriptCreateDate(date);													//������Ǹ���update���Ǿ���NEW�½��ķ����ű�������ǰ��������Ϊ�����ű���������
			script.setInterfaceScriptCreateUser(userName);
			this.getPageElement("preInterfaceScriptCreateDate").setValue(bs7.formatDate(date));
		}
		if(null == script.getAvailabilityStateId() || "root".equals(script.getAvailabilityStateId()) || "".equals(script.getAvailabilityStateId())){//�жϳ�ȡ�����ű���Ч�Ա����Ƿ�Ϊ�ջ�Ϊroot
			throw new RadowException("���ã����ݷ��ʽӿڷ����ű���Ч��״̬����Ϊ�ա�");									//������ʾ��
		}
		
		config.setInterfaceConfigChangeDate(script.getInterfaceScriptChangeDate());						//����ȡ��������޸�ʱ������Ϊ�����ű�������޸�ʱ��
		config.setInterfaceConfigChangeUser(script.getInterfaceScriptChangeUser());						//����ȡ��������޸�������Ϊ�����ű�������޸���
		script.setInterfaceScriptSequence(Integer.valueOf(interfaceScriptSequence));
		Transaction t = bs7.getSession().getTransaction();							//��ȡ���������
		t.begin( );																	//����ʼ
		bs7.getSession().save(script);										//���ű������Ӧ������ͬ�������ݿ���	
		bs7.getSession().save(config);										//�����������Ӧ������ͬ�������ݿ���
		t.commit();																	//�ύ����
		
		//[NO.002]����UI
		if("1".equals(script.getAvailabilityStateId())){							//�жϽű���Ч�Ա����Ƿ�Ϊ1
			color = "767702";
		}
		try{
			if("NEW".equals(opmode)){													//�жϲ��������Ƿ�ΪNEW
				 //[NO.003]���¸�ҳ���tabҳ�������
				 this.getPageElement("interfaceScriptChangeDate").setValue(dateStr);		//���ýű�����޸�����Ϊ��ǰ����
				 /**��¼��־*/
				 //SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.CREATE, "�½�" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "���ݷ��ʽӿڷ����ű��ɹ���");//д��־�����������ű��ɹ�
				 new LogUtil().createLog("96", "InterfaceScript",script.getInterfaceScriptIsn(), script.getInterfaceScriptName(), "", new Map2Temp().getLogInfo(new InterfaceScript(), script));
			 }else{
				 this.getPageElement("interfaceScriptChangeDate").setValue(dateStr);		//���½������ýű�����޸�����Ϊ��ǰʱ��
				 /**��¼��־*/
				 //SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.MODIFY, "�޸�" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "���ݷ��ʽӿڷ����ű��ɹ���");//д��־�������ű��޸ĳɹ�
				 new LogUtil().createLog("97", "InterfaceScript",script.getInterfaceScriptIsn(), script.getInterfaceScriptName(), "", new Map2Temp().getLogInfo(script1, script));
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		this.getPageElement("interfaceScriptName").setValue(script.getInterfaceScriptName());//�������ƣ�Ϊ�հף�Ĭ��ֵΪ'extractScriptId'
		this.getPageElement("opmode").setValue("UPDATE");							//���ò�������ΪUPDATE
		//ˢ��ҳ��
		this.setMainMessage("���ã�\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "\"���ݷ��ʽӿڷ����ű��Ѿ�����ɹ���");//������ʾ����ʾ����ɹ�
		//[NO.004]����ͼ��
		this.getExecuteSG().addExecuteCode("window.parent.updateTab('<span style=\"font-family:Arial;display:inline-block;color:FF6820;font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName()
				+ "->" + "<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "',1);");
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");							//���ø�ҳ�淽��reloadTree��ˢ�·����ű���
		this.getExecuteSG().addExecuteCode("window.parent.deletehaveChange('" + nodeId + "');");	//���ø�ҳ�淽��deletehaveChange()
		this.setNextEventName("queryObj");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ɾ���ű���ť��Ӧ�¼�
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("delBtn.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int delBtnOnclick() throws RadowException{
		String interfaceScriptId = this.getPageElement("interfaceScriptId").getValue();		//��ȡ��ȡ�����ű�����
		String opmode = this.getPageElement("opmode").getValue();							//��ȡ��������
		String interfaceScriptName = this.getPageElement("interfaceScriptName").getValue();
		String message = "";
		if("UPDATE".equals(opmode)){														//�жϲ��������Ƿ�Ϊ����UPDATE
			
		    message = "���ã���ȷ��Ҫɾ��\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + interfaceScriptId + "</b></span>." + interfaceScriptName + "\"���ݷ��ʽӿڷ����ű���Ϣ��ѡ��ȷ����ť��ɾ����";//��������ʾɾ����Ϣ
	    }else{
		    message = "���ã���ȷ��Ҫɾ����ǰ�½��е����ݷ��ʽӿڷ����ű���Ϣ��ѡ��ȷ����ť��ɾ����";								//�������ͷǸ���ʱɾ����ʾ��Ϣ
		}
		this.addNextEvent(NextEventValue.YES, "sureClear");									//����sureClear������ִ�нű�ɾ��
		this.addNextEvent(NextEventValue.CANNEL, "");										//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM);										//��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(message);														//������ʾ��Ϣ	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ȷ��ɾ���ű�
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("sureClear")
	@NoRequiredValidate
	public int sureClear() throws RadowException{
		String nodeId = this.getPageElement("nodeId").getValue();			//��ȡ�����ű�����
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();			//��ȡ���������ַ���
		if("UPDATE".equals(opmode)){										//�жϲ��������Ƿ�ΪUPDATE
			InterfaceScript script = bs7.getScriptByIsn(nodeId_s[1]);		//ͨ����ȡ�ɼ������ű����룬��ȡ��ȡ�ɼ������ű���������
			if("1".equals(script.getAvailabilityStateId())){				//�жϽű���Ч�Ա����Ƿ�Ϊ1
				color = "767702";
			}
			try{
				bs7.delScript(nodeId_s[1]);//����������ȡ�ɼ��������ű����룬ɾ����Ӧ�ĳ�ȡ�ɼ��ű������Ӧ�ĳ�ȡ�ֶ�ת��������Ϣ
				//SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.DELETE, "ɾ��" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "���ݷ��ʽӿڷ����ű��ɹ���");//д��־���ű�ɾ���ɹ�
				this.setMainMessage("���ã�ɾ��\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "\"���ݷ��ʽӿڷ����ű��ɹ���");//ɾ�������ű��ɹ�
				new LogUtil().createLog("98", "InterfaceScript",script.getInterfaceScriptIsn(), script.getInterfaceScriptName(), "", new ArrayList());
			}catch(Exception e){												//��׽����ִ���쳣��Ϣ�������쳣��Ϣд����־
				//SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.DELETE, "ɾ��" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "���ݷ��ʽӿڷ����ű�ʧ��" + e.getMessage());//�����ű�ɾ��ʧ�ܣ���¼��־
				this.setMainMessage("���ã�ɾ��\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "\"���ݷ��ʽӿڷ����ű�ʧ�ܡ�");//����ɾ��ʧ����ʾ��
				throw new RadowException("ɾ��ʧ��:" + e.getMessage());			//�׳��쳣������̨
			}
		}
		this.getExecuteSG().addExecuteCode("window.parent.delTreeNode('" + nodeId + "');");	//���ݷ����ű����룬ɾ����Ӧ�����ڵ�
		this.getExecuteSG().addExecuteCode("window.parent.removeTab('" + nodeId + "');");	//�ر�tabҳ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ȡsql�ű�������
	 * @param scriptsql
	 * @return
	 */
	public String getSQLType(String scriptsql){
		String sqltype = "";
		if(!"".equals(scriptsql) && scriptsql != null){												//�жϽű�����Ƿ�Ϊ��
			scriptsql = removeCommnetFromSQL(scriptsql);									        //ȥ��ע�ͺ����Ŀո�			
			scriptsql = scriptsql.replace("\r", " ").replace("\n", " ").replaceAll(" {2,}", " ");	//ȥ���س����з����Լ�ȥ������ո�	
			if(scriptsql.indexOf(" ") == -1){														//�ж��Ƿ��пո�
				sqltype = scriptsql;																//û�пո�˵���ò�ѯ���ֻ��һ���ؼ��֣���Ϊ�������
			}else{
			    sqltype = scriptsql.substring(0, scriptsql.indexOf(" "));							//��ȡ�ո�ǰ���ַ�����ȡ��sql�������
			}
		}
		return sqltype.toUpperCase();																//��ѯ�������ת���ɴ�д��ĸ
	}
	/**
	 * ȥ��sql�����ע�� 
	 */
	public String removeCommnetFromSQL(String sql){
		Pattern p = Pattern.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/");
		String presult = p.matcher(sql).replaceAll("$1");
		presult=presult.replaceAll("^\\s*", " ");
		return presult.trim();
	}
	
	@PageEvent("testScript.onclick")
	public int test() throws RadowException {
		this.setRadow_parent_data(this.getPageElement("nodeId").getValue());
		this.openWindow("ScriptTest", "pages.sysmanager.ZWHZYQ_001_007.ScriptTest");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
