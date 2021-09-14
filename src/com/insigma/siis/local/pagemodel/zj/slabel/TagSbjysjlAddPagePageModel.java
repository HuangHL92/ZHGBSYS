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
import com.insigma.siis.local.business.slabel.ExtraTags;
import com.insigma.siis.local.business.slabel.TagSbjysjl;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 是否获得省部级以上奖励
 * 
 * @author zhubo
 *
 */
public class TagSbjysjlAddPagePageModel extends PageModel {

	private LogUtil applog = new LogUtil();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			ExtraTags extraTags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (null == extraTags || !"1".equals(extraTags.getA1401a())) {
				return EventRtnType.NORMAL_SUCCESS;
			}
			StringBuffer sql = new StringBuffer("from com.insigma.siis.local.business.slabel.TagSbjysjl where a0000='" + extraTags.getA0000()
					+ "' order by createdate, updatedate asc");
			List<TagSbjysjl> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					TagSbjysjl tagSbjysjl = list.get(i);
					this.getPageElement("tag" + tagSbjysjl.getTagsbjysjl().toLowerCase()).setValue("1");
					if ("S5".equals(tagSbjysjl.getTagsbjysjl())) {
						this.getExecuteSG().addExecuteCode("document.getElementById('tags5n').disabled = false;");
						this.getPageElement("tag" + tagSbjysjl.getTagsbjysjl().toLowerCase() + "n")
								.setValue(tagSbjysjl.getTagsbjysjlnote());
					}
				}
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTagSbjysjl() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}

		try {
			long curTime = System.currentTimeMillis(); // 获取服务器系统当前时间戳.
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			StringBuffer tagsbjysjlzs = new StringBuffer();
			List<TagSbjysjl> tagSbjysjlListOld = sess.createQuery("from com.insigma.siis.local.business.slabel.TagSbjysjl where a0000='" + a0000 + "'")
					.list();
			String tags1 = this.getPageElement("tags1").getValue();
			if (tags1 != null && ("1".equals(tags1) || "on".equals(tags1))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S1");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags2 = this.getPageElement("tags2").getValue();
			if (tags2 != null && ("1".equals(tags2) || "on".equals(tags2))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S2");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags3 = this.getPageElement("tags3").getValue();
			if (tags3 != null && ("1".equals(tags3) || "on".equals(tags3))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S3");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags4 = this.getPageElement("tags4").getValue();
			if (tags4 != null && ("1".equals(tags4) || "on".equals(tags4))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S4");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags6 = this.getPageElement("tags6").getValue();
			if (tags6 != null && ("1".equals(tags6) || "on".equals(tags6))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S6");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags7 = this.getPageElement("tags7").getValue();
			if (tags7 != null && ("1".equals(tags7) || "on".equals(tags7))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S7");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags8 = this.getPageElement("tags8").getValue();
			if (tags8 != null && ("1".equals(tags8) || "on".equals(tags8))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S8");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags9 = this.getPageElement("tags9").getValue();
			if (tags9 != null && ("1".equals(tags9) || "on".equals(tags9))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S9");
				tagSbjysjl.setA0184(a01.getA0184());
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			/*String tags10 = this.getPageElement("tags10").getValue();
			if (tags10 != null && ("1".equals(tags10) || "on".equals(tags10))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S10");
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags11 = this.getPageElement("tags11").getValue();
			if (tags11 != null && ("1".equals(tags11) || "on".equals(tags11))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S11");
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags12 = this.getPageElement("tags12").getValue();
			if (tags12 != null && ("1".equals(tags12) || "on".equals(tags12))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S12");
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}
			String tags13 = this.getPageElement("tags13").getValue();
			if (tags13 != null && ("1".equals(tags13) || "on".equals(tags13))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S13");
				sess.save(tagSbjysjl);
				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				tagsbjysjlzs.append(tagsbjysjlz + "；");
			}*/
			String tags5 = this.getPageElement("tags5").getValue();
			String tags5n = this.getPageElement("tags5n").getValue();
			if (tags5 != null && ("1".equals(tags5) || "on".equals(tags5))) {
				TagSbjysjl tagSbjysjl = new TagSbjysjl();
				tagSbjysjl.setA0000(a0000);
				tagSbjysjl.setTagid(UUID.randomUUID().toString());
				tagSbjysjl.setCreatedate(curTime);
				tagSbjysjl.setTagsbjysjl("S5");
				tagSbjysjl.setA0184(a01.getA0184());
				tagSbjysjl.setTagsbjysjlnote(tags5n);

				String tagsbjysjl = tagSbjysjl.getTagsbjysjl();
				String tagsbjysjlz = HBUtil.getCodeName("TAGSBJYSJL", tagsbjysjl);
				if (null == tags5n || "".equals(tags5n)) {
					tagsbjysjlzs.append(tagsbjysjlz + "；");
				} else {
					tagsbjysjlzs.append(tagsbjysjlz + "：" + tagSbjysjl.getTagsbjysjlnote() + "；");
				}
				sess.save(tagSbjysjl);
			}
			sess.flush();
			for (TagSbjysjl tts : tagSbjysjlListOld) {
				HBUtil.executeUpdate("delete from tag_sbjysjl where tagid='" + tts.getTagid() + "'");
			}

			if (tagsbjysjlzs.length() > 0) {
				tagsbjysjlzs.replace(tagsbjysjlzs.length() - 1, tagsbjysjlzs.length(), "。");
			}
			ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);
				extratags.setCreatedate(curTime);
				extratags.setTagsbjysjlzs(tagsbjysjlzs.toString());
				applog.createLogNew("3658","省部级以上奖励新增","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new ExtraTags() ,extratags) );
			}else{
				ExtraTags extratags1 = new ExtraTags();
				extratags1.setTagsbjysjlzs(extratags.getTagsbjysjlzs());
				ExtraTags extratags2 = new ExtraTags();
				extratags2.setTagsbjysjlzs(tagsbjysjlzs.toString());
				if(extratags.getTagsbjysjlzs() != null && !"".equals(extratags.getTagsbjysjlzs())){
					if( "".equals(tagsbjysjlzs.toString()) ){
						applog.createLogNew("3659","省部级以上奖励删除","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
					}else{
						applog.createLogNew("3660","省部级以上奖励（经历）修改","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
					}					
				}else{
					applog.createLogNew("3658","省部级以上奖励新增","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
				}	
			}
			if (0 >= tagsbjysjlzs.length()) {
				extratags.setA1401a("0");
			} else {
				extratags.setA1401a("1");
			}
			extratags.setTagsbjysjlzs(tagsbjysjlzs.toString());
			extratags.setA0184(a01.getA0184());
			// 人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('sztagsbjysjlzs').value='"
					+ extratags.getTagsbjysjlzs() + "'");
			sess.saveOrUpdate(extratags);
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
