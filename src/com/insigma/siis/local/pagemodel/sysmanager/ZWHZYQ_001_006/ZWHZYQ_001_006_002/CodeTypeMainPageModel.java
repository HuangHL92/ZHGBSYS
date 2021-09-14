package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002;

import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
/**
 * ������Ϣ��ҳ��
 * @author huangcheng
 *
 */
public class CodeTypeMainPageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	String color = "Grey";	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	*====================================================================================================
	* ��������:getTreeJsonData.���������׼������Ϣ�����νṹ<br>
	* ������������:2016��03��23��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��23��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:���ɲ�����Ϣ�����Ľṹ<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ��� �顾ZWHZYQ_001 ϵͳ����<br>
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
	* ����ṹ����:���ɲ�����Ϣ�����νṹҳ��
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //��ȡ���еĲ�����Ϣ���ͼ���
		StringBuffer jsonStr = new StringBuffer();                            //�����ַ������������������ݸ�EXT��
		//ѧ��ѧλ
		jsonStr.append("[{\"text\" :\"ѧ��ѧλ��Ϣ\" ,\"id\" :\"menu_xlxw\",\"cls\" :\"folder\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"ѧ������\" ,\"id\" :\"leaf_ZB64\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѧλ����\" ,\"id\" :\"leaf_GB6864\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"����������\" ,\"id\" :\"leaf_ZB123\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"רҵ����\" ,\"id\" :\"leaf_GB16835\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		
		//ְ����Ϣ
		jsonStr.append("{\"text\" :\"ְ����Ϣ\" ,\"id\" :\"menu_zw\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"ְ���δ���\" ,\"id\" :\"leaf_ZB09\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ��������\" ,\"id\" :\"leaf_ZB42\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ�����ʹ���\" ,\"id\" :\"leaf_ZB13\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ְ���ʹ���\" ,\"id\" :\"leaf_ZB16\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ְ״̬����\" ,\"id\" :\"leaf_ZB14\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����ȥ�����\" ,\"id\" :\"leaf_ZB74\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������ʽ����\" ,\"id\" :\"leaf_ZB72\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����ԭ�����\" ,\"id\" :\"leaf_ZB73\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѡ�����÷�ʽ����\" ,\"id\" :\"leaf_ZB122\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"רҵ�����ʸ����\" ,\"id\" :\"leaf_GB8561\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ȡ���ʸ�;������\" ,\"id\" :\"leaf_ZB24\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"ְ�����ƴ���\" ,\"id\" :\"leaf_ZB08\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ������\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��λ������\" ,\"id\" :\"leaf_ZB127\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ��ȼ�����\" ,\"id\" :\"leaf_ZB136\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		
		jsonStr.append("{\"text\" :\"��Ա������\" ,\"id\" :\"leaf_ZB129\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//������Ϣ
		jsonStr.append("{\"text\" :\"������Ϣ\" ,\"id\" :\"menu_jg\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"�����������\" ,\"id\" :\"leaf_ZB03\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�����������ʴ���\" ,\"id\" :\"leaf_ZB04\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��λ������ϵ����\" ,\"id\" :\"leaf_ZB87\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//��Ա��Ϣ
		jsonStr.append("{\"text\" :\"��Ա��Ϣ\" ,\"id\" :\"menu_ry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"��������ش���\" ,\"id\" :\"leaf_ZB01\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������ݴ���\" ,\"id\" :\"leaf_GB4762\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��Ա������\" ,\"id\" :\"leaf_ZB125\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��Ա״̬����\" ,\"id\" :\"leaf_ZB126\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѵ������\" ,\"id\" :\"leaf_ZB29\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѵ���״̬����\" ,\"id\" :\"leaf_ZB30\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѵ����������\" ,\"id\" :\"leaf_ZB27\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�������ƴ���\" ,\"id\" :\"leaf_ZB65\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"���뱾��λ��ʽ����\" ,\"id\" :\"leaf_ZB77\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�˳�����ʽ����\" ,\"id\" :\"leaf_ZB78\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����������\" ,\"id\" :\"leaf_ZB132\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ν����\" ,\"id\" :\"leaf_GB4761\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"��׼�������ʴ���\" ,\"id\" :\"leaf_ZB128\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"����������\" ,\"id\" :\"leaf_ZB130\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ������\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�������\" ,\"id\" :\"leaf_ZB134\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"�������ʹ���\" ,\"id\" :\"leaf_ZB135\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѡ����������\" ,\"id\" :\"leaf_ZB137\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ѡ������Դ������\" ,\"id\" :\"leaf_ZB138\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"רҵ�����๫��Ա��ְ�ʸ����\" ,\"id\" :\"leaf_ZB139\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������λ���ʴ���\" ,\"id\" :\"leaf_ZB140\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"���˽������\" ,\"id\" :\"leaf_ZB18\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//������ѡѡ����Ϣ
		jsonStr.append("{\"text\" :\"��ѡѡ����Ϣ\" ,\"id\" :\"menu_lxxd\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"������λ��δ���\" ,\"id\" :\"leaf_ZB141\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѡ��ѡ�����\" ,\"id\" :\"leaf_ZB142\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"��ѡ��ʽ\" ,\"id\" :\"leaf_ZB143\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ԭ��λ���\" ,\"id\" :\"leaf_ZB144\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"ְ�ƴ���\" ,\"id\" :\"leaf_ZB145\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//����¼����Ա��Ϣ
		jsonStr.append("{\"text\" :\"����¼����Ա��Ϣ\" ,\"id\" :\"menu_kslyry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"��Ա��Դ�������\" ,\"id\" :\"leaf_ZB146\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"������Ŀ��Ա����\" ,\"id\" :\"leaf_ZB147\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		this.setSelfDefResData(jsonStr.toString());                           //���ַ������󴫵ݸ������ķ���
		return EventRtnType.XML_SUCCESS;
		
	}
}
