package com.insigma.siis.local.pagemodel.otherdb.dxscg;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Collegevofficail;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class DxscginfoPageModel extends PageModel {
	
	
	/**
	 * ������Ϣ�޸ı���
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save(String id) throws RadowException{
		String cvo000 = this.getPageElement("cvo000").getValue();
		String cvo002c_combo = this.getPageElement("cvo002c_combo").getValue();
		
		Collegevofficail cvo = new Collegevofficail();
		try {
			HBSession sess = HBUtil.getHBSession();
			if(cvo000==null||"".equals(cvo000)){//����
				BigDecimal o = (BigDecimal) sess.createSQLQuery("select max(CVO011) from Collegevofficail ").uniqueResult();
				PMPropertyCopyUtil.copyElementsValueToObj(cvo, this);
				cvo.setCvo011(o==null? 1L :o.longValue());
				cvo.setCvo002(cvo002c_combo);
				sess.save(cvo);
				sess.flush();
				//��ʼ������
				this.getPageElement("cvo000").setValue(cvo.getCvo000());
			}else{
				cvo = (Collegevofficail)sess.get(Collegevofficail.class, cvo000);
				PMPropertyCopyUtil.copyElementsValueToObj(cvo, this);
				cvo.setCvo002(cvo002c_combo);
				sess.update(cvo);
				sess.flush();
			}
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('��Ϣ��ʾ', '����ɹ���', function(e){ if ('ok' == e){saveCallBack('');}});");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String cvo000 = this.getPageElement("cvo000").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			if(cvo000!=null&&!"".equals(cvo000)){
				Collegevofficail cvo = (Collegevofficail)sess.get(Collegevofficail.class, cvo000);
				PMPropertyCopyUtil.copyObjValueToElement(cvo, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ��");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
