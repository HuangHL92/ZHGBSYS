package com.insigma.siis.local.pagemodel.gbmc;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Js2Yntp;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddYNTPPageModel extends PageModel {
	
	
	/**1
	 * ������Ϣ�޸ı���
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save(String id) throws RadowException{
		String yn_id = this.getPageElement("ynId").getValue();
		Js2Yntp yn = new Js2Yntp();
		
		try {
			HBSession sess = HBUtil.getHBSession();
			if(yn_id==null||"".equals(yn_id)){//����
				yn_id = UUID.randomUUID().toString();
				PMPropertyCopyUtil.copyElementsValueToObj(yn, this);
				yn.setYnId(yn_id);
				yn.setYnUserid(SysManagerUtils.getUserId());
				yn.setYnSysno(BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004"))));
				yn.setYnDate(new Timestamp( new Date().getTime()));
				sess.save(yn);
				Calendar cal = Calendar.getInstance();
		        int year = cal.get(Calendar.YEAR);//��ȡ���
		        int month=cal.get(Calendar.MONTH)+1;//��ȡ�·�
		        int day=cal.get(Calendar.DATE);//��ȡ��
		        String uname = SysManagerUtils.getUserloginName();
				sess.createSQLQuery("insert into js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ1','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
				sess.createSQLQuery("insert into js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ2','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
				sess.createSQLQuery("insert into js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ3','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
				sess.createSQLQuery("insert into js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ4','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
				sess.createSQLQuery("insert into js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ5','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
				sess.flush();
				
				this.getPageElement("ynId").setValue(yn.getYnId());
				this.getPageElement("ynUserid").setValue(yn.getYnUserid());
				this.getExecuteSG().addExecuteCode("saveCallBack('�����ɹ�');");
				//this.setMainMessage("�����ɹ�");
			}else{
				yn = (Js2Yntp)sess.get(Js2Yntp.class, yn_id);
				PMPropertyCopyUtil.copyElementsValueToObj(yn, this);
				sess.update(yn);
				sess.flush();
				this.getExecuteSG().addExecuteCode("saveCallBack('�޸ĳɹ�');");
				//this.setMainMessage("�޸ĳɹ�");
			}
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
		String yn_id = this.getPageElement("ynId").getValue();
		try {
			if(yn_id!=null&&!"".equals(yn_id)){
				HBSession sess = HBUtil.getHBSession();
				Js2Yntp yn = (Js2Yntp)sess.get(Js2Yntp.class, yn_id);
				PMPropertyCopyUtil.copyObjValueToElement(yn, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ��");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
