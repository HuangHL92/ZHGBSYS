package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.io.UnsupportedEncodingException;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class ZWHZYQ_001_007_ScriptEditPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:close.�رսű�����ҳ��
	* ������������:2016��03��09��
	* ����������Ա:�Ƴ�
	* ��������޸�����:2016��03��09��
	* ��������޸���Ա:�Ƴ�
	* ������������:�رսű�����ҳ��
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����
	* �������
	* �������                  ��������                                         ��������                                 ������������
	* ���ؽ��
	* ������     	        �������				     �������		             �����������
	*  (01)  EventRtnType.NORMAL_SUCCESS  ���سɹ�״̬                           int             				 
	* ����ṹ����:�رսű����ý��棬����ʾ�Ƿ񱣴�����
	*====================================================================================================
	*/
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException{
		String changeflag=this.getPageElement("changeflag").getValue();			//��ȡ�ı��״̬Ϊ���ж�ҳ��Ԫ��ֵ�Ƿ�ı�
		if("true".equals(changeflag)){											//�ж�ҳ�������Ƿ񱣴棬û��������ʾ����
			this.addNextEvent(NextEventValue.YES, "doColWin");					//confirm���ʹ���,���ȷ��ʱ�����¼�
			this.addNextEvent(NextEventValue.CANNEL, "");						//confirm���ʹ���,���ȡ��ʱ�����¼�
			this.setMessageType(EventMessageType.CONFIRM);						//��Ϣ������(confirm���ʹ���)
			this.setMainMessage("��ã���ǰ�ű��༭ҳ�����޸ĵ�δ���棬��ȷ��Ҫ�رյ�ǰҳ����");	//������Ϣ������(confirm���ʹ�����Ϣ)
		}else{
			this.setNextEventName("doColWin");									//��������ˣ�����ùرմ��ڵķ���
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:doColWin.���ڹر��¼�
	* ������������:2016��03��09��
	* ����������Ա:�Ƴ�
	* ��������޸�����:2016��03��09��
	* ��������޸���Ա:�Ƴ�
	* ������������:���ڹر��¼�
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����
	* �������
	* �������                  ��������                                         ��������                                 ������������
	*  (01)                   
	* ���ؽ��
	* ������     	        �������				     �������		             �����������
	*  (01)  EventRtnType.NORMAL_SUCCESS  ���سɹ�״̬                           int                	      
	* ����ṹ����:�رշ����������ô���
	*====================================================================================================
	*/
	@PageEvent("doColWin")
	@NoRequiredValidate
	public int doColWin(){
		this.closeCueWindow("ScriptEdit");										//���ÿ�ܷ������رյ�ǰ����
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:save.����ҳ�����ݵĵ����Ӧ�¼�
	* ������������:2014��03��09��
	* ����������Ա:�Ƴ�
	* ��������޸�����:2016��03��09��
	* ��������޸���Ա:�Ƴ�
	* ������������:����ҳ�����ݵĵ����Ӧ�¼�
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����
	* �������
	* �������                  ��������                                         ��������                                 ������������
	* ���ؽ��
	* ������     	        �������				     �������		             �����������
	*  (01)  EventRtnType.NORMAL_SUCCESS  ���سɹ�״̬                           int    		 
	* ����ṹ����:�ύ������ҳ����������
	*====================================================================================================
	*/
	@PageEvent("save")
	@NoRequiredValidate
	public int save() throws RadowException, UnsupportedEncodingException {
		String sqlText = this.getPageElement("sqlText").getValue(); 
		this.getExecuteSG().addExecuteCode("window.parent.setValue('" + sqlText + "');");
		this.closeCueWindow("ScriptEdit");                                      //�رյ�ǰ����
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
