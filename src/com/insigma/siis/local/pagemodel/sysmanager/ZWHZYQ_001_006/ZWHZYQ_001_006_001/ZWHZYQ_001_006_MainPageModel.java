package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
/**
 * ������Ϣ��ҳ��
 * @author huangcheng
 *
 */
public class ZWHZYQ_001_006_MainPageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	String color = "Grey";	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	*====================================================================================================
	* ��������:getTreeJsonData.���ɲ�����Ϣ�����νṹ<br>
	* ������������:2016��03��23��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��23��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:���ɲ�����Ϣ�����Ľṹ<br>
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
	* ����ṹ����:���ɲ�����Ϣ�����νṹҳ��
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //��ȡ���еĲ�����Ϣ���ͼ���
		List<AddType> addTypeList = bs6.getAddTypes();                                    
		StringBuffer jsonStr = new StringBuffer();							          //�����ַ������������������ݸ�EXT��
		if(addTypeList != null && !addTypeList.isEmpty()){
			if(addTypeList.size() > 0){
				for(int i = 0; i < addTypeList.size(); i++){
					AddType addType = addTypeList.get(i);                             //�����е�ÿһ��������Ϣ����
					color = "red";
					String title =addType.getAddTypeName();
					String isn = addType.getAddTypeId();                              //��Ϣ���͵�id
					if(i==0) {
						jsonStr.append("[{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn + "\"");
					} else {
						jsonStr.append(",{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn + "\"");
					}
					jsonStr.append(",\"leaf\":true}");
				}
				jsonStr.append("]");
			}
		}
		this.setSelfDefResData(jsonStr.toString());                           //���ַ������󴫵ݸ������ķ���
		return EventRtnType.XML_SUCCESS;
	}
	/**
	*====================================================================================================
	* ��������:addNewType.������Ϣ��<br>
	* ������������:2016��03��23��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��23��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:������Ϣ��<br>
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
	* ����ṹ����:���ɲ�����Ϣ�����νṹҳ��
	*====================================================================================================
	*/
	@PageEvent("addNewType")
	public int addNewType(String nodeId) throws RadowException {
		this.setRadow_parent_data(nodeId);
		this.openWindow("addNewType", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.ZWHZYQ_001_006_AddType");//�¼��������Ĵ򿪴����¼�
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����nodeIdɾ����Ϣ��
	 * @param nodeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("deleteAddType")
	public int deleteAddType(String nodeId) {
		String message = "���ã���ȷ��Ҫɾ����ǰѡ�еĲ�����Ϣ����ѡ��ȷ����ť��ɾ����";		//��ʾɾ����Ϣ
		List<AddValue> list = bs6.getSession().createQuery("from AddValue where addTypeId=:addTypeId and publishStatus='1'")
				              .setParameter("addTypeId", nodeId).list();
		if(list.size()!=0) {
			this.setMainMessage("����Ϣ���µ�������Ϣ�����Ѿ������ģ�������ɾ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(nodeId==null || "".equals(nodeId) || "S000000".equals(nodeId)) {
			this.setMainMessage("��ѡ��һ����Ϣ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "sureDel", nodeId);		        //���ȷ������תִ��del�����������ݲ������봮
		this.addNextEvent(NextEventValue.CANNEL, "");					        //���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM);					        //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(message);									        //������ʾ��Ϣ	
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ȷ��ɾ��������Ϣ��
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	@PageEvent("sureDel")
	public int sureDel(String nodeId) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		HBSession session = bs6.getSession();
		AddType addType = (AddType)session.createQuery("from AddType where addTypeId=:nodeId").setParameter("nodeId", nodeId).uniqueResult();
		Transaction t = session.getTransaction();
		t.begin();
		//ɾ����Ϣ������
		session.createSQLQuery("delete from ADD_TYPE where ADD_TYPE_ID=:nodeId").setParameter("nodeId", nodeId).executeUpdate();
		//ɾ����Ϣ���µ�������Ϣ��
		session.createSQLQuery("delete from ADD_VALUE where ADD_TYPE_ID=:nodeId").setParameter("nodeId", nodeId).executeUpdate();
		t.commit();
		this.getExecuteSG().addExecuteCode("reloadTree()");
		this.getExecuteSG().addExecuteCode("clearNodeId()");
		this.reloadPage();
		try {
			try {
				new LogUtil().createLog("71", addType.getTableCode(), addType.getAddTypeId(), addType.getAddTypeName(), "ɾ��������Ϣ��", new Map2Temp().getLogInfo(addType,new AddType()));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �޸Ĳ�����Ϣ��
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("modifyAddType")
	public int modifyAddType(String nodeId) throws RadowException {
		if(nodeId==null || "".equals(nodeId) || "S000000".equals(nodeId)) {
			this.setMainMessage("����,����ѡ��һ��������Ϣ���Ľڵ�.");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<AddValue> list = bs6.getSession().createQuery("from AddValue where addTypeId=:addTypeId and publishStatus='1'")
	              .setParameter("addTypeId", nodeId).list();
		if(list.size()!=0) {
			this.setMainMessage("����Ϣ���µ�������Ϣ�����Ѿ������ģ��������޸ġ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data(nodeId);
		this.openWindow("modifyAddType", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.ZWHZYQ_001_006_ModifyAddType");//�¼��������Ĵ򿪴����¼�
		return EventRtnType.NORMAL_SUCCESS;
	}
}
