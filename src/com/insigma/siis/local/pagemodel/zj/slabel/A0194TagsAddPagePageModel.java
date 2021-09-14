package com.insigma.siis.local.pagemodel.zj.slabel;

import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.slabel.A0194Tag;
import com.insigma.siis.local.business.slabel.ExtraTags;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * ��Ϥ����
 * 
 * @author zhubo
 *
 */
public class A0194TagsAddPagePageModel extends PageModel {

	private LogUtil applog = new LogUtil();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
		if (extratags != null) {
			this.getPageElement("a0194z").setValue(extratags.getA0194z());
			String a0194s = extratags.getA0194zcode();
			if (a0194s != null && !"".equals(a0194s)) {
				String[] num = a0194s.split("��");
				for (int i = 0; i < num.length; i++) {
					this.getPageElement("tag" + num[i]).setValue("1");
				}
				this.getPageElement("a0194s").setValue(a0194s);
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA0194zInfo() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a0194z = this.getPageElement("a0194z").getValue();
		String a0194s = this.getPageElement("a0194s").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			long curTime = System.currentTimeMillis(); // ��ȡ������ϵͳ��ǰʱ���.
			sess.createSQLQuery("delete from zjs_A0194_TAG where a0000='" + a0000 + "'").executeUpdate();
			//List<A0194Tag> a0194listold = sess.createQuery("from com.insigma.siis.local.business.slabel.A0194Tag where a0000='" + a0000 + "'").list();
			if (a0194s != null && !"".equals(a0194s)) {
				String[] num = a0194s.split("��");
				// ѭ������������Ϥ������Ϣ
				for (int i = 0; i < num.length; i++) {
					A0194Tag a0194tag = new A0194Tag();
					a0194tag.setA0000(a0000);
					a0194tag.setTagid(UUID.randomUUID().toString());
					a0194tag.setCreatedate(curTime);
					a0194tag.setA0194(num[i]);
					a0194tag.setA0184(a01.getA0184());
					sess.save(a0194tag);
				}
			}
			sess.flush();
			/*for (A0194Tag at : a0194listold) {
				HBUtil.executeUpdate("delete from a0194_tag where tagid='" + at.getTagid() + "'");
			}*/
			ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);
				extratags.setA0194z(a0194z);
				applog.createLogNew("3655","��Ϥ��������","��ǩ��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new ExtraTags() ,extratags) );
			}else{
				ExtraTags extratags1 = new ExtraTags();
				extratags1.setA0194z(extratags.getA0194z());
				ExtraTags extratags2 = new ExtraTags();
				extratags2.setA0194z(a0194z);
				if( extratags.getA0194z() != null && !"".equals(extratags.getA0194z())){
					if( "".equals(a0194z) ){
						applog.createLogNew("3657","��Ϥ����ɾ��","��ǩ��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
					}else{
						applog.createLogNew("3656","��Ϥ�����޸�","��ǩ��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
					}
				}else{
					applog.createLogNew("3655","��Ϥ��������","��ǩ��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
				}
			}
			extratags.setA0194z(a0194z);
			extratags.setA0194zcode(a0194s);
			extratags.setA0184(a01.getA0184());
			// ��Ա������Ϣ����
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('sza0194z').value='" + extratags.getA0194z() + "'");
			sess.saveOrUpdate(extratags);
			sess.flush();
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
