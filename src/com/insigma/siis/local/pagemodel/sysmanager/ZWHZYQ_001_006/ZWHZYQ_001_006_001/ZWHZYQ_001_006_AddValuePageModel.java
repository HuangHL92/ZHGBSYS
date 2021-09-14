package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_006_AddValuePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * ��ʾ���в�����Ϣ���������Ϣ��
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PageEvent("list.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	@GridDataRange
	public int gridQuery(int start, int limit) throws Exception {
		String nodeId = this.getPageElement("nodeId").getValue();			            //��ȡ��ǰ��������
		String sql = null;
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql = "select t.add_value_sequence,t.add_value_name,t.add_value_id,"
					   + "t.add_type_id,t.code_type,"
					   + "t.add_value_detail,"
					   + "decode(publish_status,'0','<img src=\"images/icon/error.gif\"></img>δ����','1','<img  src=\"images/icon/right.gif\"></img>����') publish_status,"
					   + "decode(t.isused,'0','<img src=\"images/icon/error.gif\"></img>��','1','<img  src=\"images/icon/right.gif\"></img>��') isused,"
					   + "t.multilineshow "
					   + "from add_value t where t.add_type_id='"+nodeId+"' order by t.add_value_sequence";
		} else if(DBType.MYSQL==DBUtil.getDBType()) {
			sql = "select t.add_value_sequence,t.add_value_name,t.add_value_id,"
					   + "t.add_type_id,t.code_type,"
					   + "t.add_value_detail,"
					   + "if(publish_status='0','<img src=\"images/icon/error.gif\"></img>δ����','<img src=\"images/icon/right.gif\"></img>����') publish_status,"
					   + "if(isused='0','<img src=\"images/icon/error.gif\"></img>��','<img src=\"images/icon/right.gif\"></img>��') isused,"
					   + "t.multilineshow "
					   + "from add_value t where t.add_type_id='"+nodeId+"' order by t.add_value_sequence";
		}
		this.pageQuery(sql, "SQL", start, 100);								            //�����ҳ��ѯ
		return EventRtnType.SPE_SUCCESS;
	}
	

	@PageEvent("addAction")
	public int addAction(String nodeId) throws Exception {
		if(nodeId == null || "".equals(nodeId) || "S000000".equals(nodeId)) {
			this.setMainMessage("��ѡ��һ����Ϣ�����ϵ�һ���ڵ㡣");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data("NEW_"+nodeId);
		this.openWindow("addNewValue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.AddValueCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("update")
	public int update(String nodeId) throws Exception {
		this.setRadow_parent_data("UPDATE_"+nodeId);
		this.openWindow("updateAddValue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.AddValueCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("disPlayDsa")
	public int disPlayDsa() throws Exception {
		this.setNextEventName("list.dogridquery");	
		return EventRtnType.NORMAL_SUCCESS;
		
	}

	@PageEvent("update.onclick")
	public int updateOne() throws RadowException{
		PageElement pe = this.getPageElement("list");					//��ȡҳ����Ԫ�ض���
		List<HashMap<String, Object>> list = pe.getValueList();			//��ҳ����Ԫ�ش���ڹ�ϣ����
		if(!isChoosed()){												//�ж��Ƿ�ѡ�б������
			this.setMainMessage("���ã���δѡ����Ϣ���ѡ��Ҫ�޸ĵ���Ϣ�");	    //����Ϊѡ�����������Ϣ��ʾ��
			return EventRtnType.NORMAL_SUCCESS;
		}
		int count = 0;//������¼����ѡ�е���Ϣ������
		String nodeId = "";//Ψһ��ѡ�е���Ϣ��id
		for(int i = 0; i < list.size(); i++){							//�Բ�����¼����Ϊ����ѭ��
			HashMap<String, Object> hm = list.get(i);					//ȡ��һ������������Ϣ������ϣ����
			Object selected = hm.get("selected");						//�жϸ���������Ϣ�Ƿ�ѡ��
			if(selected.equals(true) || selected.equals("1")){			//�����ѡ��
				count++;
				AddValue addValue = bs6.getAddValueById(hm.get("add_value_id").toString());
				if("1".equals(addValue.getPublishStatus())){
					this.setMainMessage("���ã���ѡ�����Ϣ�����з������ģ������޸ġ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				nodeId += (String) hm.get("add_value_id");
			}
		}
		
		if(count>1) {
			this.setMainMessage("���ã�����ѡ��һ����Ϣ������޸ġ�");
			return EventRtnType.NORMAL_SUCCESS;
		} 
		
		this.setRadow_parent_data("UPDATE_"+nodeId);
		this.openWindow("updateAddValue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.AddValueCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delParam.onclick")
	@AutoNoMask
	public int delete() throws RadowException{
		PageElement pe = this.getPageElement("list");					//��ȡҳ����Ԫ�ض���
		List<HashMap<String, Object>> list = pe.getValueList();			//��ҳ����Ԫ�ش���ڹ�ϣ����
		if(null == list || list.size() == 0){							//�ж��б����Ƿ�������
			this.setMainMessage("��ã����ݷ�����Ϣ����û����Ϣ�����ɾ����");		//�����û�����ݣ�������ʾ��
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!isChoosed()){												//�ж��Ƿ�ѡ�б������
			this.setMainMessage("���ã���δѡ����Ϣ��Ϣ����ѡ��Ҫɾ������Ϣ�");	    //����Ϊѡ�����������Ϣ��ʾ��
			return EventRtnType.NORMAL_SUCCESS;
		}
		String strAddValueIds = "";
		for(int i = 0; i < list.size(); i++){							//�Բ�����¼����Ϊ����ѭ��
			HashMap<String, Object> hm = list.get(i);					//ȡ��һ������������Ϣ������ϣ����
			Object selected = hm.get("selected");						//�жϸ���������Ϣ�Ƿ�ѡ��
			if(selected.equals(true) || selected.equals("1")){			//�����ѡ��
				AddValue addValue = bs6.getAddValueById(hm.get("add_value_id").toString());
				if("1".equals(addValue.getPublishStatus())){
					this.setMainMessage("���ã���ѡ�����Ϣ�����з������ģ�����ɾ����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				strAddValueIds += "," + hm.get("add_value_id").toString();//ƴ�ӱ�ѡ�еĲ������봮
			}
		}
		strAddValueIds = strAddValueIds.substring(1);						//ɾ���������봮��ǰ��Ķ��š�,��
		String message = "���ã���ȷ��Ҫɾ����ǰѡ�еķ�����Ϣ�ѡ��ȷ����ť��ɾ����";		//��ʾɾ����Ϣ
		this.addNextEvent(NextEventValue.YES, "sureDel", strAddValueIds);	//���ȷ������תִ��del�����������ݲ������봮
		this.addNextEvent(NextEventValue.CANNEL, "");					    //���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM);					    //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(message);									    //������ʾ��Ϣ	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("sureDel")
	public int sureDel(String strAddValueIds) throws RadowException{
		String[] addValueIds = strAddValueIds.split(",");								//�ö��ŷָ��������봮
		Transaction t = bs6.getSession().getTransaction();								//�����������
		t.begin();																		//����ʼ
		for(String addValueId : addValueIds) {
			//��ѯ����
			AddValue addValue = (AddValue) bs6.getSession().createQuery("FROM AddValue where addValueId=:addValueId").setParameter("addValueId", addValueId).list().get(0);
			bs6.getSession().createQuery("delete from AddValue  where addValueId=:addValueId")
			.setParameter("addValueId", addValueId).executeUpdate();
			//��¼��־
			try {
				new LogUtil().createLog("75", "ADDVALUE", addValue.getAddValueId(), addValue.getAddValueName(), "", new Map2Temp().getLogInfo(addValue,new AddValue()));
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
	 * �·��¼�
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("deliver.onclick")
	public int deliver() throws RadowException {
		this.openWindow("deliverCue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.DeliverCue");
		return EventRtnType.NORMAL_SUCCESS;
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
	
	@PageEvent("publish")
	public int publish(String addValueId){
		AddValue addValue = bs6.getAddValueById(addValueId);
		if("0".equals(addValue.getIsused())) {
			this.setMainMessage("���ã���ѡ�����Ϣ����δʹ�ã����޸�ʹ��״̬���ٽ��з�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("1".equals(addValue.getPublishStatus())) {
			this.setMainMessage("���ã���ѡ�����Ϣ���Ѿ�������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String message = "���ã���ȷ��Ҫ������ǰѡ�еķ�����Ϣ������󽫲����޸ĺ�ɾ����";		//��ʾɾ����Ϣ
		this.addNextEvent(NextEventValue.YES, "suerPub", addValueId);		//���ȷ������תִ��del�����������ݲ������봮
		this.addNextEvent(NextEventValue.CANNEL, "");					//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM);					//��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(message);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("suerPub")
	public int suerPub(String addValueId){
		AddValue addValue = bs6.getAddValueById(addValueId);
		addValue.setPublishStatus("1");
		CodeTableCol codeTableCol = new CodeTableCol();
		UUID uuid = UUID.randomUUID();
		AddType addType = bs6.getAddTypeById(addValue.getAddTypeId());
		codeTableCol.setCtci(uuid.toString());
		codeTableCol.setTableCode(addType.getTableCode());
		codeTableCol.setColCode(addValue.getColCode());
		codeTableCol.setColName(addValue.getAddValueName());
		String codeType = addValue.getCodeType();
		codeTableCol.setCodeType(codeType);
		codeTableCol.setIsNewCodeCol("1");//������������ָ����
		codeTableCol.setColLectionName("�Զ�����Ϣ�");
		String colType = "0".equals(addValue.getColType())?"VARCHAR2":"1".equals(addValue.getColType())?"NUMBER":"DATE";
		codeTableCol.setColDataTypeShould(colType);
		codeTableCol.setIsZbx("0");
		codeTableCol.setZbxtj("0");
		Transaction t = bs6.getSession().getTransaction();
		t.begin();
		bs6.getSession().saveOrUpdate(addValue);
		bs6.getSession().save(codeTableCol);
		t.commit();
		this.setNextEventName("list.dogridquery");	
		return EventRtnType.NORMAL_SUCCESS;
	}
}
