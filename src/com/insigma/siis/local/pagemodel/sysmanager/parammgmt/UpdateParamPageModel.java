package com.insigma.siis.local.pagemodel.sysmanager.parammgmt;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Aa01;

public class UpdateParamPageModel extends PageModel {

	@SuppressWarnings("unchecked")
	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String sql = "from Aa01 where aaa001='"+this.getRadow_parent_data()+"'";
		List list = sess.createQuery(sql).list();
		Aa01 aa01 = (Aa01) list.get(0);	
		PMPropertyCopyUtil.copyObjValueToElement(aa01, this);
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * ȡ����ִ�е��¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("cannelEvent")
	public int cannelEvent() throws RadowException{ //���������Զ����¼�
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/**
	 * �޸Ĳ�����Ϣ
	 * 
	 * @throws RadowException
	 * @throws RadowException
	 */
	@PageEvent("saveBtn.onclick")
	public int deleteBntOnClick() throws RadowException {
		this.addNextEvent(NextEventValue.YES, "saveUpdateEvent");
		this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");// ���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪ�����޸ĺõ���Ϣ��"); // ������ʾ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveUpdateEvent")
	public int saveOnClick() throws RadowException{
		Aa01 aa01 = new Aa01();
		HBSession sess = HBUtil.getHBSession();
		try {
			this.copyElementsValueToObj(aa01, this);
			sess.beginTransaction();
			sess.saveOrUpdate(aa01);
			sess.flush();
			sess.getTransaction().commit();
		} catch (AppException e) {
			this.setMainMessage("�޸Ĳ�������");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.createPageElement("codegrid", "grid", true).reload();
		this.closeCueWindow("UpdateWin");
		return EventRtnType.NORMAL_SUCCESS;
	} 
}
