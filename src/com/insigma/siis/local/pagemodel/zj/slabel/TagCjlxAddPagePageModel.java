package com.insigma.siis.local.pagemodel.zj.slabel;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;

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
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.slabel.ExtraTags;
import com.insigma.siis.local.business.slabel.TagCjlx;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.AddRmbPageModel;

public class TagCjlxAddPagePageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	private long currentTimeMillis = System.currentTimeMillis();

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		// ��ȡ��Ա����a0000
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("TagCjlxGrid.dogridquery"); // �ͽ������б�
		try {
			HBSession sess = HBUtil.getHBSession();
			ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (null == extraTags) {
				extraTags = updateTagCjlxzs();
			}
			PMPropertyCopyUtil.copyObjValueToElement(extraTags, this);
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTagCjlx() throws RadowException, AppException {
		TagCjlx tagCjlx = new TagCjlx();
		this.copyElementsValueToObj(tagCjlx, this);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		tagCjlx.setA0000(a0000);
		String tagid = this.getPageElement("tagid").getValue();

		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			StringBuffer sql = new StringBuffer("from com.insigma.siis.local.business.slabel.TagCjlx where a0000='" + tagCjlx.getA0000() + "' and tagcjlx='"
					+ tagCjlx.getTagcjlx() + "'");
			List list = sess.createQuery(sql.toString()).list();
			if (list != null && list.size() > 0) {
				this.setMainMessage("�óͽ�������д�ظ�!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (null != tagCjlx.getTagcjlx() && !"".equals(tagCjlx.getTagcjlx())) {
				// �������޸�
				if (tagid == null || "".equals(tagid)) {
					tagCjlx.setCreatedate(currentTimeMillis);
					tagCjlx.setTagid(UUID.randomUUID().toString());
					sess.save(tagCjlx);
				} else {
					TagCjlx tagCjlx_old = (TagCjlx) sess.get(TagCjlx.class, tagid);
					tagCjlx_old.setUpdatedate(currentTimeMillis);
					PropertyUtils.copyProperties(tagCjlx_old, tagCjlx);

					sess.update(tagCjlx_old);
				}
			} else {
				this.setMainMessage("��ѡ��ͽ����Ͳ����棡");
				return EventRtnType.FAILD;
			}
			sess.flush();

			// ����extra_tags��ǩ��Ϣ
			updateTagCjlxzs();

			CustomQueryBS.setA01(a0000);
			A01 a01F = (A01) sess.get(A01.class, a0000);
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}

		this.getPageElement("tagid").setValue(tagCjlx.getTagid());// ����ɹ���tagid���ص�ҳ���ϡ�
		this.getExecuteSG().addExecuteCode("radow.doEvent('TagCjlxGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �ͽ�������������
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public ExtraTags updateTagCjlxzs() throws RadowException, AppException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();

		HBSession sess = HBUtil.getHBSession();
		ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
		String oldcjlx = "";
		A01 a01 = (A01) sess.get(A01.class, a0000);
		boolean isSave = null == extraTags;
		if (isSave) {
			extraTags = new ExtraTags();
			extraTags.setA0000(a0000);
			extraTags.setCreatedate(currentTimeMillis);
		} else {
			extraTags.setUpdatedate(currentTimeMillis);
		}

		String sql = "from com.insigma.siis.local.business.slabel.TagCjlx where a0000='" + a0000 + "' order by tagcjlx asc";
		List<TagCjlx> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		if (list != null && list.size() > 0) {
			StringBuffer tagcjlxzs = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				TagCjlx tagCjlx = list.get(i);
				String tagcjlx = tagCjlx.getTagcjlx();
				String tagcjlxz = HBUtil.getCodeName("TAGCJLX", tagcjlx);
				tagcjlxzs.append(tagcjlxz + "��");
			}
			if (tagcjlxzs.length() > 0) {
				tagcjlxzs.replace(tagcjlxzs.length() - 1, tagcjlxzs.length(), "��");
			}
			this.getPageElement("tagcjlxzs").setValue(tagcjlxzs.toString());

			extraTags.setTagcjlxzs(tagcjlxzs.toString());

		} else {
			String tagcjlxzs = "";
			this.getPageElement("tagcjlxzs").setValue(tagcjlxzs);
			extraTags.setTagcjlxzs(tagcjlxzs);
		}

		if (isSave) {
			applog.createLogNew("3663","�ͽ���������","��ǩ��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new ExtraTags() ,extraTags) );
			extraTags.setA0184(a01.getA0184());
			sess.save(extraTags);
		} else {
			extraTags.setA0184(a01.getA0184());
			sess.update(extraTags);
			ExtraTags extratags1 = new ExtraTags();
			extratags1.setTagcjlxzs(oldcjlx);
			ExtraTags extratags2 = new ExtraTags();
			extratags2.setTagcjlxzs(extraTags.getTagcjlxzs());
			applog.createLogNew("3664","�ͽ������޸�","��ǩ��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1 ,extratags2) );
		}

		// ��Ա������Ϣ--��ǩ��Ϣ����
		this.getExecuteSG().addExecuteCode(
				"window.realParent.document.getElementById('sztagcjlxzs').value='" + extraTags.getTagcjlxzs() + "'");
		sess.flush();
		return extraTags;
	}

	/**
	 * �ͽ������б�
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TagCjlxGrid.dogridquery")
	@NoRequiredValidate
	public int assessmentInfoGridQuery(int start, int limit) throws RadowException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select tagid, tag_cjlx tagcjlx from zjs_tag_cjlx where a0000='" + a0000
				+ "' order by tag_cjlx asc";
		this.pageQuery(sql, "SQL", start, limit); // �����ҳ��ѯ
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * �ͽ�����������ť
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TagCjlxAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id) throws RadowException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		TagCjlx tagCjlx = new TagCjlx();
		PMPropertyCopyUtil.copyObjValueToElement(tagCjlx, this);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("static-access")
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String tagid) throws RadowException, AppException {
		try {
			HBSession sess = HBUtil.getHBSession();
			TagCjlx tagCjlx = (TagCjlx) sess.get(TagCjlx.class, tagid);
			A01 a01 = (A01) sess.get(A01.class, tagCjlx.getA0000());

			applog.createLog("3153", "TagCjlx", a01.getA0000(), a01.getA0101(), "ɾ����¼",
					new Map2Temp().getLogInfo(new TagCjlx(), new TagCjlx()));

			sess.delete(tagCjlx);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TagCjlxGrid.dogridquery')");
			tagCjlx = new TagCjlx();
			PMPropertyCopyUtil.copyObjValueToElement(tagCjlx, this);

			updateTagCjlxzs();

			CustomQueryBS.setA01(a01.getA0000());
			A01 a01F = (A01) sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
		} catch (Exception e) {
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
