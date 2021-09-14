package com.insigma.siis.local.pagemodel.zj.tags;

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
import com.insigma.siis.local.business.entity.extra.ExtraTags;
import com.insigma.siis.local.business.entity.extra.TagCjlx;
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
		// 获取人员主键a0000
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("TagCjlxGrid.dogridquery"); // 惩戒类型列表
		try {
			HBSession sess = HBUtil.getHBSession();
			ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (null == extraTags) {
				extraTags = updateTagCjlxzs();
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
	public int saveTagCjlx() throws RadowException, AppException {
		TagCjlx tagCjlx = new TagCjlx();
		this.copyElementsValueToObj(tagCjlx, this);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		tagCjlx.setA0000(a0000);
		String tagid = this.getPageElement("tagid").getValue();

		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			StringBuffer sql = new StringBuffer("from TagCjlx where a0000='" + tagCjlx.getA0000() + "' and tagcjlx='"
					+ tagCjlx.getTagcjlx() + "'");
			List list = sess.createQuery(sql.toString()).list();
			if (list != null && list.size() > 0) {
				this.setMainMessage("该惩戒类型填写重复!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (null != tagCjlx.getTagcjlx() && !"".equals(tagCjlx.getTagcjlx())) {
				// 新增或修改
				if (tagid == null || "".equals(tagid)) {
					tagCjlx.setCreatedate(currentTimeMillis);
					tagCjlx.setTagid(UUID.randomUUID().toString());
					applog.createLog("3151", "TagCjlx", a01.getA0000(), a01.getA0101(), "新增记录",
							new Map2Temp().getLogInfo(new TagCjlx(), tagCjlx));

					sess.save(tagCjlx);
				} else {
					TagCjlx tagCjlx_old = (TagCjlx) sess.get(TagCjlx.class, tagid);
					applog.createLog("3152", "TagCjlx", a01.getA0000(), a01.getA0101(), "修改记录",
							new Map2Temp().getLogInfo(tagCjlx_old, tagCjlx));

					tagCjlx_old.setUpdatedate(currentTimeMillis);
					PropertyUtils.copyProperties(tagCjlx_old, tagCjlx);

					sess.update(tagCjlx_old);
				}
			} else {
				this.setMainMessage("请选择惩戒类型并保存！");
				return EventRtnType.FAILD;
			}
			sess.flush();

			// 更新extra_tags标签信息
			updateTagCjlxzs();

			CustomQueryBS.setA01(a0000);
			A01 a01F = (A01) sess.get(A01.class, a0000);
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}

		this.getPageElement("tagid").setValue(tagCjlx.getTagid());// 保存成功将tagid返回到页面上。
		this.getExecuteSG().addExecuteCode("radow.doEvent('TagCjlxGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 惩戒类型文字描述
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public ExtraTags updateTagCjlxzs() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();

		HBSession sess = HBUtil.getHBSession();
		ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
		boolean isSave = null == extraTags;
		if (isSave) {
			extraTags = new ExtraTags();
			extraTags.setA0000(a0000);
			extraTags.setCreatedate(currentTimeMillis);
		} else {
			extraTags.setUpdatedate(currentTimeMillis);
		}

		String sql = "from TagCjlx where a0000='" + a0000 + "' order by tagcjlx asc";
		List<TagCjlx> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		if (list != null && list.size() > 0) {
			StringBuffer tagcjlxzs = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				TagCjlx tagCjlx = list.get(i);
				String tagcjlx = tagCjlx.getTagcjlx();
				String tagcjlxz = HBUtil.getCodeName("TAGCJLX", tagcjlx);
				tagcjlxzs.append(tagcjlxz + "；");
			}
			if (tagcjlxzs.length() > 0) {
				tagcjlxzs.replace(tagcjlxzs.length() - 1, tagcjlxzs.length(), "。");
			}
			this.getPageElement("tagcjlxzs").setValue(tagcjlxzs.toString());

			extraTags.setTagcjlxzs(tagcjlxzs.toString());

		} else {
			String tagcjlxzs = "";
			this.getPageElement("tagcjlxzs").setValue(tagcjlxzs);
			extraTags.setTagcjlxzs(tagcjlxzs);
		}

		if (isSave) {
			sess.save(extraTags);
		} else {
			sess.update(extraTags);
		}

		// 人员基本信息--标签信息界面
		this.getExecuteSG().addExecuteCode(
				"window.realParent.document.getElementById('tagcjlxzs').value='" + extraTags.getTagcjlxzs() + "'");
		sess.flush();
		return extraTags;
	}

	/**
	 * 惩戒类型列表
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
		String sql = "select tagid, tag_cjlx tagcjlx from tag_cjlx where a0000='" + a0000
				+ "' order by tag_cjlx asc";
		this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 惩戒类型新增按钮
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
			this.setMainMessage("请先保存人员基本信息！");
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

			applog.createLog("3153", "TagCjlx", a01.getA0000(), a01.getA0101(), "删除记录",
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
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
