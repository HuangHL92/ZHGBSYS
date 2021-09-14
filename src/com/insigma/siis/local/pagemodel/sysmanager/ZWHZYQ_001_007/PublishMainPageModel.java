package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.util.List;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;

public class PublishMainPageModel extends PageModel{


	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	String color = "Grey";
	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	*====================================================================================================
	* ��������:getTreeJsonData.��ȡĿ¼���µ�����<br>
	* ������������:2016��03��04��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:��ȡĿ¼���µ�����<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)		EventRtnType.XML_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:ҳ������������Ŀ¼չ��
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{
		List<InterfaceConfig> interfaceConfigs = bs7.getInterfaceConfigTree();                                      //��ȡ���еķ���
		StringBuffer jsonStr = new StringBuffer();							                                        //�����ַ������������������ݸ�EXT��
		String contextPath = "module_img/ZWHZYQ_001_007/";	                                                        //���ͼƬ���·����ַ
		String str1 = "";
		String str2 = "</b></span>";
		String configPath = contextPath + "level2_16.gif";                                                                //���ͼƬ��·��
		if(interfaceConfigs != null && !interfaceConfigs.isEmpty()){
			if(interfaceConfigs.size() > 0){
				for(int i = 0; i < interfaceConfigs.size(); i++){
					InterfaceConfig interfaceConfig = interfaceConfigs.get(i);                                      //�����е�ÿһ���ӿڷ���
					if("1".equals(interfaceConfig.getAvailabilityStateId())){                                       //����ӿڷ�������Ч��
						color = "FF6820";                                                                           //��ɫ����Ϊ��ɫ
					}
					str1 = "<span style='display:inline-block;color:" + color + ";font-size:11px'><b>";
					String title = str1 + interfaceConfig.getInterfaceConfigId() + str2 + "." + interfaceConfig.getInterfaceConfigName();
					String isn_fa = "FA_" + interfaceConfig.getInterfaceConfigIsn();                                //���������룬ǰ���FA_��Ϊ�����ֽű�
					if(i==0) {
						jsonStr.append("[{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn_fa + "\" ,\"cls\" :\"folder\",\"icon\":\"" + configPath + "\",\"leaf\":true}");
					} else {
						jsonStr.append(",{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn_fa + "\" ,\"cls\" :\"folder\",\"icon\":\"" + configPath + "\",\"leaf\":true}");
					}
				}
				jsonStr.append("]");
			}else{
				jsonStr.append("]");
			}
		}
		this.setSelfDefResData(jsonStr.toString());                           //���ַ������󴫵ݸ������ķ���
		return EventRtnType.XML_SUCCESS;
	}
}
