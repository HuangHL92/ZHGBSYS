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
import com.insigma.siis.local.business.slabel.TagRclx;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.AddRmbPageModel;

public class TagRclxAddPagePageModel extends PageModel {
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
		// 获取人员主键a0000
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("TagRclxGrid.dogridquery"); // 人才类型列表
		try {
			HBSession sess = HBUtil.getHBSession();
			ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (null == extraTags) {
				extraTags = updateTagRclxzs();
			}
			PMPropertyCopyUtil.copyObjValueToElement(extraTags, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTagRclx() throws RadowException, AppException {
		TagRclx tagRclx = new TagRclx();
		this.copyElementsValueToObj(tagRclx, this);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		tagRclx.setA0000(a0000);
		String tagid = this.getPageElement("tagid").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			tagRclx.setA0184(a01.getA0184());
			StringBuffer sql = new StringBuffer("from com.insigma.siis.local.business.slabel.TagRclx where a0000='" + tagRclx.getA0000() + "' and tagrclx='"
					+ tagRclx.getTagrclx() + "'");
			List list = sess.createQuery(sql.toString()).list();
			if (list != null && list.size() > 0) {
				this.setMainMessage("该人才类型填写重复!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (null != tagRclx.getTagrclx() && !"".equals(tagRclx.getTagrclx())) {
				// 新增或修改
				if (tagid == null || "".equals(tagid)) {
					tagRclx.setCreatedate(currentTimeMillis);
					tagRclx.setTagid(UUID.randomUUID().toString());
					sess.save(tagRclx);
				} else {
					TagRclx tagRclx_old = (TagRclx) sess.get(TagRclx.class, tagid);
					tagRclx_old.setUpdatedate(currentTimeMillis);
					PropertyUtils.copyProperties(tagRclx_old, tagRclx);

					sess.update(tagRclx_old);
				}
			} else {
				this.setMainMessage("请选择人才类型并保存！");
				return EventRtnType.FAILD;
			}
			sess.flush();

			// 更新extra_tags标签信息
			updateTagRclxzs();

			CustomQueryBS.setA01(a0000);
			A01 a01F = (A01) sess.get(A01.class, a0000);
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}

		this.getPageElement("tagid").setValue(tagRclx.getTagid());// 保存成功将tagid返回到页面上。
		this.getExecuteSG().addExecuteCode("radow.doEvent('TagRclxGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 人才类型文字描述
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
	public ExtraTags updateTagRclxzs() throws RadowException, AppException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = HBUtil.getHBSession();
		ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
		String oldRclx = "";
		A01 a01 = (A01) sess.get(A01.class, a0000);
		boolean isSave = null == extraTags;
		if (isSave) {
			extraTags = new ExtraTags();
			extraTags.setA0000(a0000);
			extraTags.setCreatedate(currentTimeMillis);
		} else {
			extraTags.setUpdatedate(currentTimeMillis);
			oldRclx = extraTags.getTagrclxzs();
		}

		String sql = "from com.insigma.siis.local.business.slabel.TagRclx where a0000='" + a0000 + "' order by tagrclx asc";
		List<TagRclx> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		if (list != null && list.size() > 0) {
			StringBuffer tagrclxzs = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				TagRclx tagRclx = list.get(i);
				String tagrclx = tagRclx.getTagrclx();
				String tagrclxz = HBUtil.getCodeName("TAGRCLX", tagrclx);
				tagrclxzs.append(tagrclxz + "；");
			}
			if (tagrclxzs.length() > 0) {
				tagrclxzs.replace(tagrclxzs.length() - 1, tagrclxzs.length(), "。");
			}
			this.getPageElement("tagrclxzs").setValue(tagrclxzs.toString());

			extraTags.setTagrclxzs(tagrclxzs.toString());

		} else {
			String tagrclxzs = "";
			this.getPageElement("tagrclxzs").setValue(tagrclxzs);
			extraTags.setTagrclxzs(tagrclxzs);
		}

		if (isSave) {
			applog.createLogNew("3661","人才类型新增","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new ExtraTags() ,extraTags) );
			extraTags.setA0184(a01.getA0184());
			sess.save(extraTags);
		} else {
			extraTags.setA0184(a01.getA0184());
			sess.update(extraTags);
			ExtraTags extratags1 = new ExtraTags();
			extratags1.setTagrclxzs(oldRclx);
			ExtraTags extratags2 = new ExtraTags();
			extratags2.setTagrclxzs(extraTags.getTagrclxzs());
			applog.createLogNew("3662","人才类型修改","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1 ,extratags2) );
		}

		// 人员基本信息--标签信息界面
		this.getExecuteSG().addExecuteCode(
				"window.realParent.document.getElementById('sztagrclxzs').value='" + extraTags.getTagrclxzs() + "'");
		sess.flush();
		return extraTags;
	}

	/**
	 * 人才类型列表
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TagRclxGrid.dogridquery")
	@NoRequiredValidate
	public int assessmentInfoGridQuery(int start, int limit) throws RadowException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select tagid, tag_rclx tagrclx from zjs_tag_rclx where a0000='" + a0000
				+ "' order by tag_rclx asc";
		this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 人才类型新增按钮
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TagRclxAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id) throws RadowException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		TagRclx tagRclx = new TagRclx();
		PMPropertyCopyUtil.copyObjValueToElement(tagRclx, this);
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
			TagRclx tagRclx = (TagRclx) sess.get(TagRclx.class, tagid);
			A01 a01 = (A01) sess.get(A01.class, tagRclx.getA0000());
			sess.delete(tagRclx);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TagRclxGrid.dogridquery')");
			tagRclx = new TagRclx();
			PMPropertyCopyUtil.copyObjValueToElement(tagRclx, this);

			updateTagRclxzs();

			CustomQueryBS.setA01(a01.getA0000());
			A01 a01F = (A01) sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
