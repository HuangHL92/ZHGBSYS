package com.insigma.siis.local.pagemodel.search;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.pagemodel.cbdHandler.CBDTools;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ModifyAttachPageModel extends PageModel {

	String uuid ="";
	String flag = "";
	@Override
	public int doInit() throws RadowException {
		
		String value = this.getRadow_parent_data();
		String[] values = value.split("@");
		this.getPageElement("cbd_name").setValue(values[2]);
		this.getPageElement("flag").setValue(values[1]);
		this.getPageElement("cbd_id").setValue(values[0]);
		this.setNextEventName("AttachGrid.dogridquery");
		
		return 0;
	}

	//��ѯ������Ϣ
	@PageEvent("AttachGrid.dogridquery")
	public int attachmentQuery(int start,int limit) throws RadowException{
		
		//�������ݿ����
		HBSession sess = HBUtil.getHBSession();
		
		String value = this.getRadow_parent_data();
		String[] values = value.split("@");
		uuid = values[0];
		flag = values[1];
		StringBuffer sql = new StringBuffer("select ai.id,ai.filename,ai.filepath,ai.uploaddate,ai.beizhu from attachment_info ai where 1=1 ");
		if("0".equals(flag)){
			sql.append(" and ai.personid = '"+uuid+"'");
		}else if("1".equals(flag)){
			sql.append(" and ai.objectid = '"+uuid+"'");
		}else if("2".equals(flag) || "3".equals(flag)){
			List<CBDInfo> list = sess.createQuery("from CBDInfo where objectno = '"+uuid+"' and cbd_path = '"+flag+"'").list();
			if(list.size() > 0 ){
				sql.append(" and ai.objectid = '"+list.get(0).getCbd_id()+"'");
			}
		}
		CommonQueryBS.systemOut(sql.toString());
		this.pageQuery(sql.toString(), "SQL", start, limit);
		
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	/**
	 * ɾ����������
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("deleAttach.onclick")
	public int deleAttach() throws RadowException{
		
		//�����������¼ѡ�м�¼������
		int count = 0;
		//��ȡѡ�еļ�¼
		PageElement pe = this.getPageElement("AttachGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("checked"))&&(Boolean) hm.get("checked")){
				String id = (String) hm.get("id");
				doDel(id);
				count = count+1;
			}
		}
		if(count == 0){
			this.setMainMessage("��ѡ��Ҫɾ���ļ�¼��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setMainMessage("����ɾ���ɹ���");
		this.closeCueWindowByYes("modifyFileWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  ִ��ɾ������
	 * @param value
	 * @return
	 */
	public int doDel(String value){
		
		//�������������·��
		String filePath = "";
		HBSession sess = HBUtil.getHBSession();
		List list=sess.createQuery("from AttachmentInfo where id='"+value+"' ").list();
		if(list.size() > 0){
			AttachmentInfo atta = (AttachmentInfo) list.get(0);
			filePath = atta.getFilepath();
			CBDTools ct = new CBDTools();
			String rootPath = ct.getPath();
			File file = new File(rootPath+filePath);
			//ɾ������
			if(file.exists()){
				file.delete();
			}
			//ɾ�����ݿ��¼
			//sess.delete(atta);
			sess.createSQLQuery("delete from Attachment_Info where id = '"+value+"'").executeUpdate();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("reload")
	public int reload(String value){
		CommonQueryBS.systemOut("------"+value);
		String[] values = value.split("@");
		uuid = values[0];
		flag = values[1];
		this.setNextEventName("AttachGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
