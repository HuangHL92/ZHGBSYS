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
import com.insigma.siis.local.business.slabel.A0195Tag;
import com.insigma.siis.local.business.slabel.ExtraTags;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 历任重要职务重要经历
 * 
 * @author zhubo
 *
 */
public class A0195TagsAddPagePageModel extends PageModel {

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
		List<A0195Tag> a0195list = sess.createQuery("from com.insigma.siis.local.business.slabel.A0195Tag where a0000='" + a0000 + "'").list();
		for (A0195Tag at : a0195list) {
			String a0195 = at.getA0195();
			this.getPageElement("tag" + a0195).setValue("1");
			if (null != at.getA0195c() && !"".equals(at.getA0195c())) {
				this.getPageElement("tag" + a0195 + "n").setValue(at.getA0195c());
			}
			if ("1809".equals(at.getA0195())) {
				this.getExecuteSG().addExecuteCode("$('#tag1809n').attr('disabled',false);");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA0193zInfo() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}

		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			String a0195z = "";
			long curTime = System.currentTimeMillis(); // 获取服务器系统当前时间戳.
			sess.createSQLQuery("delete from zjs_A0195_TAG where a0000='" + a0000 + "'").executeUpdate();
			//List<A0195Tag> a0193listold = sess.createQuery("from com.insigma.siis.local.business.slabel.A0195Tag where a0000='" + a0000 + "'").list();
			// 中央国家机关职务
			String tag0101 = this.getPageElement("tag0101").getValue();
			if (tag0101 != null && ("1".equals(tag0101) || "on".equals(tag0101))) {
				a0195z = returnA0195z("0101", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0101");
				sess.save(a0195tag);
			}
			
			
			// 省区市职务
			String tag0201 = this.getPageElement("tag0201").getValue();
			if (tag0201 != null && ("1".equals(tag0201) || "on".equals(tag0201))) {
				a0195z = returnA0195z("0201", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0201");
				sess.save(a0195tag);
			}
			String tag0202 = this.getPageElement("tag0202").getValue();
			if (tag0202 != null && ("1".equals(tag0202) || "on".equals(tag0202))) {
				a0195z = returnA0195z("0202", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0202");
				sess.save(a0195tag);
			}
			String tag0203 = this.getPageElement("tag0203").getValue();
			if (tag0203 != null && ("1".equals(tag0203) || "on".equals(tag0203))) {
				a0195z = returnA0195z("0203", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0203");
				sess.save(a0195tag);
			}
			String tag0204 = this.getPageElement("tag0204").getValue();
			if (tag0204 != null && ("1".equals(tag0204) || "on".equals(tag0204))) {
				a0195z = returnA0195z("0204", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0204");
				sess.save(a0195tag);
			}
			// 副省级城市职务
			String tag0301 = this.getPageElement("tag0301").getValue();
			if (tag0301 != null && ("1".equals(tag0301) || "on".equals(tag0301))) {
				a0195z = returnA0195z("0301", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0301");
				sess.save(a0195tag);
			}
			String tag0302 = this.getPageElement("tag0302").getValue();
			if (tag0302 != null && ("1".equals(tag0302) || "on".equals(tag0302))) {
				a0195z = returnA0195z("0302", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0302");
				sess.save(a0195tag);
			}
			String tag0303 = this.getPageElement("tag0303").getValue();
			if (tag0303 != null && ("1".equals(tag0303) || "on".equals(tag0303))) {
				a0195z = returnA0195z("0303", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0303");
				sess.save(a0195tag);
			}
			String tag0304 = this.getPageElement("tag0304").getValue();
			if (tag0304 != null && ("1".equals(tag0304) || "on".equals(tag0304))) {
				a0195z = returnA0195z("0304", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0304");
				sess.save(a0195tag);
			}
			String tag0305 = this.getPageElement("tag0305").getValue();
			if (tag0305 != null && ("1".equals(tag0305) || "on".equals(tag0305))) {
				a0195z = returnA0195z("0305", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0305");
				sess.save(a0195tag);
			}
			String tag0306 = this.getPageElement("tag0306").getValue();
			if (tag0306 != null && ("1".equals(tag0306) || "on".equals(tag0306))) {
				a0195z = returnA0195z("0306", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0306");
				sess.save(a0195tag);
			}
			String tag0307 = this.getPageElement("tag0307").getValue();
			if (tag0307 != null && ("1".equals(tag0307) || "on".equals(tag0307))) {
				a0195z = returnA0195z("0307", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0307");
				sess.save(a0195tag);
			}
			String tag0308 = this.getPageElement("tag0308").getValue();
			if (tag0308 != null && ("1".equals(tag0308) || "on".equals(tag0308))) {
				a0195z = returnA0195z("0308", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0308");
				sess.save(a0195tag);
			}
			// 地（市、州、盟）职务
			String tag0401 = this.getPageElement("tag0401").getValue();
			String tag0401n = this.getPageElement("tag0401n").getValue();
			if (tag0401 != null && ("1".equals(tag0401) || "on".equals(tag0401))) {
				a0195z = returnA0195z("0401", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0401");
				a0195tag.setA0195c(tag0401n);
				sess.save(a0195tag);
			}
			String tag0402 = this.getPageElement("tag0402").getValue();
			String tag0402n = this.getPageElement("tag0402n").getValue();
			if (tag0402 != null && ("1".equals(tag0402) || "on".equals(tag0402))) {
				a0195z = returnA0195z("0402", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0402");
				a0195tag.setA0195c(tag0402n);
				sess.save(a0195tag);
			}
			String tag0403 = this.getPageElement("tag0403").getValue();
			if (tag0403 != null && ("1".equals(tag0403) || "on".equals(tag0403))) {
				a0195z = returnA0195z("0403", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0403");
				sess.save(a0195tag);
			}
			String tag0404 = this.getPageElement("tag0404").getValue();
			if (tag0404 != null && ("1".equals(tag0404) || "on".equals(tag0404))) {
				a0195z = returnA0195z("0404", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0404");
				sess.save(a0195tag);
			}
			String tag0405 = this.getPageElement("tag0405").getValue();
			if (tag0405 != null && ("1".equals(tag0405) || "on".equals(tag0405))) {
				a0195z = returnA0195z("0405", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0405");
				
				sess.save(a0195tag);
			}
			String tag0406 = this.getPageElement("tag0406").getValue();
			if (tag0406 != null && ("1".equals(tag0406) || "on".equals(tag0406))) {
				a0195z = returnA0195z("0406", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0406");
				
				sess.save(a0195tag);
			}
/*			String tag0407 = this.getPageElement("tag0407").getValue();
			if (tag0407 != null && ("1".equals(tag0407) || "on".equals(tag0407))) {
				a0195z = returnA0195z("0407", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0407");
				
				sess.save(a0195tag);
			}
			String tag0408 = this.getPageElement("tag0408").getValue();
			if (tag0408 != null && ("1".equals(tag0408) || "on".equals(tag0408))) {
				a0195z = returnA0195z("0408", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0408");
				
				sess.save(a0195tag);
			}
			String tag0409 = this.getPageElement("tag0409").getValue();
			if (tag0409 != null && ("1".equals(tag0409) || "on".equals(tag0409))) {
				a0195z = returnA0195z("0409", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0409");
				
				sess.save(a0195tag);
			}*/
			String tag0410 = this.getPageElement("tag0410").getValue();
			if (tag0410 != null && ("1".equals(tag0410) || "on".equals(tag0410))) {
				a0195z = returnA0195z("0410", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0410");
				
				sess.save(a0195tag);
			}
			String tag0411 = this.getPageElement("tag0411").getValue();
			if (tag0411 != null && ("1".equals(tag0411) || "on".equals(tag0411))) {
				a0195z = returnA0195z("0411", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0411");
				
				sess.save(a0195tag);
			}
			// 县（市、区、旗）职务
			String tag0501 = this.getPageElement("tag0501").getValue();
			String tag0501n = this.getPageElement("tag0501n").getValue();
			if (tag0501 != null && ("1".equals(tag0501) || "on".equals(tag0501))) {
				a0195z = returnA0195z("0501", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0501");
				
				a0195tag.setA0195c(tag0501n);
				sess.save(a0195tag);
			}
			String tag0502 = this.getPageElement("tag0502").getValue();
			String tag0502n = this.getPageElement("tag0502n").getValue();
			if (tag0502 != null && ("1".equals(tag0502) || "on".equals(tag0502))) {
				a0195z = returnA0195z("0502", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0502");
				
				a0195tag.setA0195c(tag0502n);
				sess.save(a0195tag);
			}
			String tag0503 = this.getPageElement("tag0503").getValue();
			if (tag0503 != null && ("1".equals(tag0503) || "on".equals(tag0503))) {
				a0195z = returnA0195z("0503", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0503");
				
				sess.save(a0195tag);
			}
			String tag0504 = this.getPageElement("tag0504").getValue();
			if (tag0504 != null && ("1".equals(tag0504) || "on".equals(tag0504))) {
				a0195z = returnA0195z("0504", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0504");
				
				sess.save(a0195tag);
			}
/*			String tag0505 = this.getPageElement("tag0505").getValue();
			if (tag0505 != null && ("1".equals(tag0505) || "on".equals(tag0505))) {
				a0195z = returnA0195z("0505", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0505");
				
				sess.save(a0195tag);
			}
			String tag0506 = this.getPageElement("tag0506").getValue();
			if (tag0506 != null && ("1".equals(tag0506) || "on".equals(tag0506))) {
				a0195z = returnA0195z("0506", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0506");
				
				sess.save(a0195tag);
			}*/
			String tag0507 = this.getPageElement("tag0507").getValue();
			if (tag0507 != null && ("1".equals(tag0507) || "on".equals(tag0507))) {
				a0195z = returnA0195z("0507", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0507");
				
				sess.save(a0195tag);
			}
			String tag0508 = this.getPageElement("tag0508").getValue();
			if (tag0508 != null && ("1".equals(tag0508) || "on".equals(tag0508))) {
				a0195z = returnA0195z("0508", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0508");
				
				sess.save(a0195tag);
			}
			// 乡镇（街道）职务
			String tag0601 = this.getPageElement("tag0601").getValue();
			if (tag0601 != null && ("1".equals(tag0601) || "on".equals(tag0601))) {
				a0195z = returnA0195z("0601", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0601");
				
				sess.save(a0195tag);
			}
			String tag0602 = this.getPageElement("tag0602").getValue();
			if (tag0602 != null && ("1".equals(tag0602) || "on".equals(tag0602))) {
				a0195z = returnA0195z("0602", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0602");
				
				sess.save(a0195tag);
			}
			// 金融企业职务
			String tag0701 = this.getPageElement("tag0701").getValue();
			if (tag0701 != null && ("1".equals(tag0701) || "on".equals(tag0701))) {
				a0195z = returnA0195z("0701", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0701");
				
				sess.save(a0195tag);
			}
			String tag0702 = this.getPageElement("tag0702").getValue();
			if (tag0702 != null && ("1".equals(tag0702) || "on".equals(tag0702))) {
				a0195z = returnA0195z("0702", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0702");
				
				sess.save(a0195tag);
			}
			String tag0703 = this.getPageElement("tag0703").getValue();
			if (tag0703 != null && ("1".equals(tag0703) || "on".equals(tag0703))) {
				a0195z = returnA0195z("0703", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0703");
				
				sess.save(a0195tag);
			}
			String tag0704 = this.getPageElement("tag0704").getValue();
			if (tag0704 != null && ("1".equals(tag0704) || "on".equals(tag0704))) {
				a0195z = returnA0195z("0704", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0704");
				
				sess.save(a0195tag);
			}
			String tag0705 = this.getPageElement("tag0705").getValue();
			if (tag0705 != null && ("1".equals(tag0705) || "on".equals(tag0705))) {
				a0195z = returnA0195z("0705", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0705");
				
				sess.save(a0195tag);
			}
			String tag0708 = this.getPageElement("tag0708").getValue();
			if (tag0708 != null && ("1".equals(tag0708) || "on".equals(tag0708))) {
				a0195z = returnA0195z("0708", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0708");
				
				sess.save(a0195tag);
			}
			String tag0709 = this.getPageElement("tag0709").getValue();
			if (tag0709 != null && ("1".equals(tag0709) || "on".equals(tag0709))) {
				a0195z = returnA0195z("0709", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0709");
				
				sess.save(a0195tag);
			}
			String tag0712 = this.getPageElement("tag0712").getValue();
			if (tag0712 != null && ("1".equals(tag0712) || "on".equals(tag0712))) {
				a0195z = returnA0195z("0712", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0712");
				
				sess.save(a0195tag);
			}
			String tag0713 = this.getPageElement("tag0713").getValue();
			if (tag0713 != null && ("1".equals(tag0713) || "on".equals(tag0713))) {
				a0195z = returnA0195z("0713", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0713");
				
				sess.save(a0195tag);
			}
			String tag0714 = this.getPageElement("tag0714").getValue();
			if (tag0714 != null && ("1".equals(tag0714) || "on".equals(tag0714))) {
				a0195z = returnA0195z("0714", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0714");
				
				sess.save(a0195tag);
			}
			// 企业职务
			String tag0801 = this.getPageElement("tag0801").getValue();
			if (tag0801 != null && ("1".equals(tag0801) || "on".equals(tag0801))) {
				a0195z = returnA0195z("0801", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0801");
				
				sess.save(a0195tag);
			}
			String tag0802 = this.getPageElement("tag0802").getValue();
			if (tag0802 != null && ("1".equals(tag0802) || "on".equals(tag0802))) {
				a0195z = returnA0195z("0802", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0802");
				
				sess.save(a0195tag);
			}
			String tag0803 = this.getPageElement("tag0803").getValue();
			if (tag0803 != null && ("1".equals(tag0803) || "on".equals(tag0803))) {
				a0195z = returnA0195z("0803", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0803");
				
				sess.save(a0195tag);
			}
			String tag0820 = this.getPageElement("tag0820").getValue();
			if (tag0820 != null && ("1".equals(tag0820) || "on".equals(tag0820))) {
				a0195z = returnA0195z("0820", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0820");
				
				sess.save(a0195tag);
			}
			String tag0804 = this.getPageElement("tag0804").getValue();
			if (tag0804 != null && ("1".equals(tag0804) || "on".equals(tag0804))) {
				a0195z = returnA0195z("0804", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0804");
				
				sess.save(a0195tag);
			}
			String tag0805 = this.getPageElement("tag0805").getValue();
			if (tag0805 != null && ("1".equals(tag0805) || "on".equals(tag0805))) {
				a0195z = returnA0195z("0805", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0805");
				
				sess.save(a0195tag);
			}
			String tag0806 = this.getPageElement("tag0806").getValue();
			if (tag0806 != null && ("1".equals(tag0806) || "on".equals(tag0806))) {
				a0195z = returnA0195z("0806", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0806");
				
				sess.save(a0195tag);
			}
			String tag0813 = this.getPageElement("tag0813").getValue();
			if (tag0813 != null && ("1".equals(tag0813) || "on".equals(tag0813))) {
				a0195z = returnA0195z("0813", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0813");
				
				sess.save(a0195tag);
			}
			String tag0814 = this.getPageElement("tag0814").getValue();
			if (tag0814 != null && ("1".equals(tag0814) || "on".equals(tag0814))) {
				a0195z = returnA0195z("0814", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0814");
				
				sess.save(a0195tag);
			}
			String tag0808 = this.getPageElement("tag0808").getValue();
			if (tag0808 != null && ("1".equals(tag0808) || "on".equals(tag0808))) {
				a0195z = returnA0195z("0808", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0808");
				
				sess.save(a0195tag);
			}
			String tag0809 = this.getPageElement("tag0809").getValue();
			if (tag0809 != null && ("1".equals(tag0809) || "on".equals(tag0809))) {
				a0195z = returnA0195z("0809", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0809");
				
				sess.save(a0195tag);
			}
			String tag0812 = this.getPageElement("tag0812").getValue();
			if (tag0812 != null && ("1".equals(tag0812) || "on".equals(tag0812))) {
				a0195z = returnA0195z("0812", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0812");
				
				sess.save(a0195tag);
			}
			String tag0810 = this.getPageElement("tag0810").getValue();
			if (tag0810 != null && ("1".equals(tag0810) || "on".equals(tag0810))) {
				a0195z = returnA0195z("0810", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0810");
				
				sess.save(a0195tag);
			}
			String tag0811 = this.getPageElement("tag0811").getValue();
			if (tag0811 != null && ("1".equals(tag0811) || "on".equals(tag0811))) {
				a0195z = returnA0195z("0811", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0811");
				
				sess.save(a0195tag);
			}
			// 高校职务
			String tag0901 = this.getPageElement("tag0901").getValue();
			if (tag0901 != null && ("1".equals(tag0901) || "on".equals(tag0901))) {
				a0195z = returnA0195z("0901", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0901");
				
				sess.save(a0195tag);
			}
			String tag0902 = this.getPageElement("tag0902").getValue();
			if (tag0902 != null && ("1".equals(tag0902) || "on".equals(tag0902))) {
				a0195z = returnA0195z("0902", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0902");
				
				sess.save(a0195tag);
			}
			String tag0903 = this.getPageElement("tag0903").getValue();
			if (tag0903 != null && ("1".equals(tag0903) || "on".equals(tag0903))) {
				a0195z = returnA0195z("0903", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0903");
				
				sess.save(a0195tag);
			}
			String tag0904 = this.getPageElement("tag0904").getValue();
			if (tag0904 != null && ("1".equals(tag0904) || "on".equals(tag0904))) {
				a0195z = returnA0195z("0904", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0904");
				
				sess.save(a0195tag);
			}
			String tag0905 = this.getPageElement("tag0905").getValue();
			if (tag0905 != null && ("1".equals(tag0905) || "on".equals(tag0905))) {
				a0195z = returnA0195z("0905", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0905");
				
				sess.save(a0195tag);
			}
			String tag0906 = this.getPageElement("tag0906").getValue();
			if (tag0906 != null && ("1".equals(tag0906) || "on".equals(tag0906))) {
				a0195z = returnA0195z("0906", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0906");
				
				sess.save(a0195tag);
			}
			String tag0907 = this.getPageElement("tag0907").getValue();
			if (tag0907 != null && ("1".equals(tag0907) || "on".equals(tag0907))) {
				a0195z = returnA0195z("0907", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0907");
				
				sess.save(a0195tag);
			}
			String tag0908 = this.getPageElement("tag0908").getValue();
			if (tag0908 != null && ("1".equals(tag0908) || "on".equals(tag0908))) {
				a0195z = returnA0195z("0908", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0908");
				
				sess.save(a0195tag);
			}
			String tag0909 = this.getPageElement("tag0909").getValue();
			if (tag0909 != null && ("1".equals(tag0909) || "on".equals(tag0909))) {
				a0195z = returnA0195z("0909", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0909");
				
				sess.save(a0195tag);
			}
			/*String tag0910 = this.getPageElement("tag0910").getValue();
			if (tag0910 != null && ("1".equals(tag0910) || "on".equals(tag0910))) {
				a0195z = returnA0195z("0910", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0910");
				sess.save(a0195tag);
			}
			String tag0911 = this.getPageElement("tag0911").getValue();
			if (tag0911 != null && ("1".equals(tag0911) || "on".equals(tag0911))) {
				a0195z = returnA0195z("0911", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0911");
				sess.save(a0195tag);
			}
			String tag0912 = this.getPageElement("tag0912").getValue();
			if (tag0912 != null && ("1".equals(tag0912) || "on".equals(tag0912))) {
				a0195z = returnA0195z("0912", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0912");
				sess.save(a0195tag);
			}
			String tag0913 = this.getPageElement("tag0913").getValue();
			if (tag0913 != null && ("1".equals(tag0913) || "on".equals(tag0913))) {
				a0195z = returnA0195z("0913", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0913");
				sess.save(a0195tag);
			}
			String tag0914 = this.getPageElement("tag0914").getValue();
			if (tag0914 != null && ("1".equals(tag0914) || "on".equals(tag0914))) {
				a0195z = returnA0195z("0914", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0914");
				sess.save(a0195tag);
			}
			String tag0915 = this.getPageElement("tag0915").getValue();
			if (tag0915 != null && ("1".equals(tag0915) || "on".equals(tag0915))) {
				a0195z = returnA0195z("0915", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0915");
				sess.save(a0195tag);
			}
			String tag0916 = this.getPageElement("tag0916").getValue();
			if (tag0916 != null && ("1".equals(tag0916) || "on".equals(tag0916))) {
				a0195z = returnA0195z("0916", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0916");
				sess.save(a0195tag);
			}
			String tag0917 = this.getPageElement("tag0917").getValue();
			if (tag0917 != null && ("1".equals(tag0917) || "on".equals(tag0917))) {
				a0195z = returnA0195z("0917", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0917");
				sess.save(a0195tag);
			}
			String tag0918 = this.getPageElement("tag0918").getValue();
			if (tag0918 != null && ("1".equals(tag0918) || "on".equals(tag0918))) {
				a0195z = returnA0195z("0918", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0918");
				sess.save(a0195tag);
			}
			String tag0919 = this.getPageElement("tag0919").getValue();
			if (tag0919 != null && ("1".equals(tag0919) || "on".equals(tag0919))) {
				a0195z = returnA0195z("0919", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0919");
				sess.save(a0195tag);
			}
			String tag0920 = this.getPageElement("tag0920").getValue();
			if (tag0920 != null && ("1".equals(tag0920) || "on".equals(tag0920))) {
				a0195z = returnA0195z("0920", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0920");
				sess.save(a0195tag);
			}
			String tag0921 = this.getPageElement("tag0921").getValue();
			if (tag0921 != null && ("1".equals(tag0921) || "on".equals(tag0921))) {
				a0195z = returnA0195z("0921", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0921");
				sess.save(a0195tag);
			}
			String tag0922 = this.getPageElement("tag0922").getValue();
			if (tag0922 != null && ("1".equals(tag0922) || "on".equals(tag0922))) {
				a0195z = returnA0195z("0922", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0922");
				sess.save(a0195tag);
			}
			String tag0923 = this.getPageElement("tag0923").getValue();
			if (tag0923 != null && ("1".equals(tag0923) || "on".equals(tag0923))) {
				a0195z = returnA0195z("0923", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0923");
				sess.save(a0195tag);
			}
			String tag0924 = this.getPageElement("tag0924").getValue();
			if (tag0924 != null && ("1".equals(tag0924) || "on".equals(tag0924))) {
				a0195z = returnA0195z("0924", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0924");
				sess.save(a0195tag);
			}
			String tag0925 = this.getPageElement("tag0925").getValue();
			if (tag0925 != null && ("1".equals(tag0925) || "on".equals(tag0925))) {
				a0195z = returnA0195z("0925", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0925");
				sess.save(a0195tag);
			}*/
			String tag0926 = this.getPageElement("tag0926").getValue();
			if (tag0926 != null && ("1".equals(tag0926) || "on".equals(tag0926))) {
				a0195z = returnA0195z("0926", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0926");
				
				sess.save(a0195tag);
			}
			String tag0927 = this.getPageElement("tag0927").getValue();
			if (tag0927 != null && ("1".equals(tag0927) || "on".equals(tag0927))) {
				a0195z = returnA0195z("0927", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0927");
				
				sess.save(a0195tag);
			}
			String tag0928 = this.getPageElement("tag0928").getValue();
			if (tag0928 != null && ("1".equals(tag0928) || "on".equals(tag0928))) {
				a0195z = returnA0195z("0928", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0928");
				
				sess.save(a0195tag);
			}
			String tag0929 = this.getPageElement("tag0929").getValue();
			if (tag0929 != null && ("1".equals(tag0929) || "on".equals(tag0929))) {
				a0195z = returnA0195z("0929", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("0929");
				
				sess.save(a0195tag);
			}
			// 科研院所职务
			String tag1001 = this.getPageElement("tag1001").getValue();
			String tag1001n = this.getPageElement("tag1001n").getValue();
			if (tag1001 != null && ("1".equals(tag1001) || "on".equals(tag1001))) {
				a0195z = returnA0195z("1001", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1001");
				
				a0195tag.setA0195c(tag1001n);
				sess.save(a0195tag);
			}
			String tag1002 = this.getPageElement("tag1002").getValue();
			if (tag1002 != null && ("1".equals(tag1002) || "on".equals(tag1002))) {
				a0195z = returnA0195z("1002", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1002");
				
				sess.save(a0195tag);
			}
			String tag1003 = this.getPageElement("tag1003").getValue();
			String tag1003n = this.getPageElement("tag1001n").getValue();
			if (tag1003 != null && ("1".equals(tag1003) || "on".equals(tag1003))) {
				a0195z = returnA0195z("1003", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1003");
				a0195tag.setA0195c(tag1003n);
				
				sess.save(a0195tag);
			}
			String tag1004 = this.getPageElement("tag1004").getValue();
			if (tag1004 != null && ("1".equals(tag1004) || "on".equals(tag1004))) {
				a0195z = returnA0195z("1004", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1004");
				
				sess.save(a0195tag);
			}
			String tag1005 = this.getPageElement("tag1005").getValue();
			if (tag1005 != null && ("1".equals(tag1005) || "on".equals(tag1005))) {
				a0195z = returnA0195z("1005", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1005");
				
				sess.save(a0195tag);
			}
			String tag1006 = this.getPageElement("tag1006").getValue();
			if (tag1006 != null && ("1".equals(tag1006) || "on".equals(tag1006))) {
				a0195z = returnA0195z("1006", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1006");
				
				sess.save(a0195tag);
			}
			// 公立医院职务
			String tag1101 = this.getPageElement("tag1101").getValue();
			String tag1101n = this.getPageElement("tag1101n").getValue();
			if (tag1101 != null && ("1".equals(tag1101) || "on".equals(tag1101))) {
				a0195z = returnA0195z("1101", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1101");
				a0195tag.setA0195c(tag1101n);
				
				sess.save(a0195tag);
			}
			String tag1102 = this.getPageElement("tag1102").getValue();
			if (tag1102 != null && ("1".equals(tag1102) || "on".equals(tag1102))) {
				a0195z = returnA0195z("1102", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1102");
				
				sess.save(a0195tag);
			}
			String tag1103 = this.getPageElement("tag1103").getValue();
			if (tag1103 != null && ("1".equals(tag1103) || "on".equals(tag1103))) {
				a0195z = returnA0195z("1103", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1103");
				
				sess.save(a0195tag);
			}
			String tag1104 = this.getPageElement("tag1104").getValue();
			if (tag1104 != null && ("1".equals(tag1104) || "on".equals(tag1104))) {
				a0195z = returnA0195z("1104", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1104");
				
				sess.save(a0195tag);
			}
			String tag1105 = this.getPageElement("tag1105").getValue();
			if (tag1105 != null && ("1".equals(tag1105) || "on".equals(tag1105))) {
				a0195z = returnA0195z("1105", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1105");
				
				sess.save(a0195tag);
			}
			String tag1106 = this.getPageElement("tag1106").getValue();
			if (tag1106 != null && ("1".equals(tag1106) || "on".equals(tag1106))) {
				a0195z = returnA0195z("1106", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1106");
				
				sess.save(a0195tag);
			}
			String tag1107 = this.getPageElement("tag1107").getValue();
			if (tag1107 != null && ("1".equals(tag1107) || "on".equals(tag1107))) {
				a0195z = returnA0195z("1107", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1107");
				
				sess.save(a0195tag);
			}
			String tag1108 = this.getPageElement("tag1108").getValue();
			if (tag1108 != null && ("1".equals(tag1108) || "on".equals(tag1108))) {
				a0195z = returnA0195z("1108", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1108");
				
				sess.save(a0195tag);
			}
			String tag1109 = this.getPageElement("tag1109").getValue();
			if (tag1109 != null && ("1".equals(tag1109) || "on".equals(tag1109))) {
				a0195z = returnA0195z("1109", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1109");
				
				sess.save(a0195tag);
			}
			String tag1110 = this.getPageElement("tag1110").getValue();
			if (tag1110 != null && ("1".equals(tag1110) || "on".equals(tag1110))) {
				a0195z = returnA0195z("1110", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1110");
				
				sess.save(a0195tag);
			}
			String tag1111 = this.getPageElement("tag1111").getValue();
			if (tag1111 != null && ("1".equals(tag1111) || "on".equals(tag1111))) {
				a0195z = returnA0195z("1111", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1111");
				
				sess.save(a0195tag);
			}
			String tag1112 = this.getPageElement("tag1112").getValue();
			if (tag1112 != null && ("1".equals(tag1112) || "on".equals(tag1112))) {
				a0195z = returnA0195z("1112", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1112");
				
				sess.save(a0195tag);
			}
			// 困难艰苦地区工作经历
			String tag1201 = this.getPageElement("tag1201").getValue();
			String tag1201n = this.getPageElement("tag1201n").getValue();
			if (tag1201 != null && ("1".equals(tag1201) || "on".equals(tag1201))) {
				a0195z = returnA0195z("1201", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1201");
				
				a0195tag.setA0195c(tag1201n);
				sess.save(a0195tag);
			}
			String tag1202 = this.getPageElement("tag1202").getValue();
			String tag1202n = this.getPageElement("tag1202n").getValue();
			if (tag1202 != null && ("1".equals(tag1202) || "on".equals(tag1202))) {
				a0195z = returnA0195z("1202", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1202");
				
				a0195tag.setA0195c(tag1202n);
				sess.save(a0195tag);
			}
			String tag1203 = this.getPageElement("tag1203").getValue();
			String tag1203n = this.getPageElement("tag1203n").getValue();
			if (tag1203 != null && ("1".equals(tag1203) || "on".equals(tag1203))) {
				a0195z = returnA0195z("1203", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1203");
				
				a0195tag.setA0195c(tag1203n);
				sess.save(a0195tag);
			}
			String tag1204 = this.getPageElement("tag1204").getValue();
			String tag1204n = this.getPageElement("tag1204n").getValue();
			if (tag1204 != null && ("1".equals(tag1204) || "on".equals(tag1204))) {
				a0195z = returnA0195z("1204", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1204");
				
				a0195tag.setA0195c(tag1204n);
				sess.save(a0195tag);
			}
			String tag1205 = this.getPageElement("tag1205").getValue();
			if (tag1205 != null && ("1".equals(tag1205) || "on".equals(tag1205))) {
				a0195z = returnA0195z("1205", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1205");
				
				sess.save(a0195tag);
			}
			String tag1206 = this.getPageElement("tag1206").getValue();
			if (tag1206 != null && ("1".equals(tag1206) || "on".equals(tag1206))) {
				a0195z = returnA0195z("1206", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1206");
				
				sess.save(a0195tag);
			}
			String tag1207 = this.getPageElement("tag1207").getValue();
			if (tag1207 != null && ("1".equals(tag1207) || "on".equals(tag1207))) {
				a0195z = returnA0195z("1207", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1207");
				
				sess.save(a0195tag);
			}
			String tag1208 = this.getPageElement("tag1208").getValue();
			if (tag1208 != null && ("1".equals(tag1208) || "on".equals(tag1208))) {
				a0195z = returnA0195z("1208", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1208");
				
				sess.save(a0195tag);
			}
			String tag1209 = this.getPageElement("tag1209").getValue();
			if (tag1209 != null && ("1".equals(tag1209) || "on".equals(tag1209))) {
				a0195z = returnA0195z("1209", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1209");
				
				sess.save(a0195tag);
			}
			// 团口经历
			String tag1301 = this.getPageElement("tag1301").getValue();
			if (tag1301 != null && ("1".equals(tag1301) || "on".equals(tag1301))) {
				a0195z = returnA0195z("1301", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1301");
				
				sess.save(a0195tag);
			}
			String tag1302 = this.getPageElement("tag1302").getValue();
			if (tag1302 != null && ("1".equals(tag1302) || "on".equals(tag1302))) {
				a0195z = returnA0195z("1302", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1302");
				
				sess.save(a0195tag);
			}
			String tag1303 = this.getPageElement("tag1303").getValue();
			if (tag1303 != null && ("1".equals(tag1303) || "on".equals(tag1303))) {
				a0195z = returnA0195z("1303", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1303");
				
				sess.save(a0195tag);
			}
			String tag1304 = this.getPageElement("tag1304").getValue();
			if (tag1304 != null && ("1".equals(tag1304) || "on".equals(tag1304))) {
				a0195z = returnA0195z("1304", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1304");
				
				sess.save(a0195tag);
			}
			String tag1305 = this.getPageElement("tag1305").getValue();
			if (tag1305 != null && ("1".equals(tag1305) || "on".equals(tag1305))) {
				a0195z = returnA0195z("1305", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1305");
				
				sess.save(a0195tag);
			}
			String tag1306 = this.getPageElement("tag1306").getValue();
			if (tag1306 != null && ("1".equals(tag1306) || "on".equals(tag1306))) {
				a0195z = returnA0195z("1306", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1306");
				
				sess.save(a0195tag);
			}
			String tag1307 = this.getPageElement("tag1307").getValue();
			if (tag1307 != null && ("1".equals(tag1307) || "on".equals(tag1307))) {
				a0195z = returnA0195z("1307", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1307");
				
				sess.save(a0195tag);
			}
			String tag1308 = this.getPageElement("tag1308").getValue();
			if (tag1308 != null && ("1".equals(tag1308) || "on".equals(tag1308))) {
				a0195z = returnA0195z("1308", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1308");
				
				sess.save(a0195tag);
			}
			String tag1309 = this.getPageElement("tag1309").getValue();
			if (tag1309 != null && ("1".equals(tag1309) || "on".equals(tag1309))) {
				a0195z = returnA0195z("1309", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1309");
				
				sess.save(a0195tag);
			}
			String tag1310 = this.getPageElement("tag1310").getValue();
			if (tag1310 != null && ("1".equals(tag1310) || "on".equals(tag1310))) {
				a0195z = returnA0195z("1310", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1310");
				
				sess.save(a0195tag);
			}
			String tag1311 = this.getPageElement("tag1311").getValue();
			if (tag1311 != null && ("1".equals(tag1311) || "on".equals(tag1311))) {
				a0195z = returnA0195z("1311", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1311");
				
				sess.save(a0195tag);
			}
			// 曾任两代表一委员情况
			String tag1401 = this.getPageElement("tag1401").getValue();
			if (tag1401 != null && ("1".equals(tag1401) || "on".equals(tag1401))) {
				a0195z = returnA0195z("1401", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1401");
				
				sess.save(a0195tag);
			}
			String tag1402 = this.getPageElement("tag1402").getValue();
			if (tag1402 != null && ("1".equals(tag1402) || "on".equals(tag1402))) {
				a0195z = returnA0195z("1402", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1402");
				
				sess.save(a0195tag);
			}
			String tag1403 = this.getPageElement("tag1403").getValue();
			if (tag1403 != null && ("1".equals(tag1403) || "on".equals(tag1403))) {
				a0195z = returnA0195z("1403", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1403");
				
				sess.save(a0195tag);
			}
			String tag1404 = this.getPageElement("tag1404").getValue();
			if (tag1404 != null && ("1".equals(tag1404) || "on".equals(tag1404))) {
				a0195z = returnA0195z("1404", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1404");
				
				sess.save(a0195tag);
			}
			String tag1405 = this.getPageElement("tag1405").getValue();
			if (tag1405 != null && ("1".equals(tag1405) || "on".equals(tag1405))) {
				a0195z = returnA0195z("1405", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1405");
				
				sess.save(a0195tag);
			}
			String tag1406 = this.getPageElement("tag1406").getValue();
			if (tag1406 != null && ("1".equals(tag1406) || "on".equals(tag1406))) {
				a0195z = returnA0195z("1406", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1406");
				
				sess.save(a0195tag);
			}
			String tag1407 = this.getPageElement("tag1407").getValue();
			if (tag1407 != null && ("1".equals(tag1407) || "on".equals(tag1407))) {
				a0195z = returnA0195z("1407", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1407");
				
				sess.save(a0195tag);
			}
			String tag1408 = this.getPageElement("tag1408").getValue();
			if (tag1408 != null && ("1".equals(tag1408) || "on".equals(tag1408))) {
				a0195z = returnA0195z("1408", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1408");
				
				sess.save(a0195tag);
			}
			String tag1409 = this.getPageElement("tag1409").getValue();
			if (tag1409 != null && ("1".equals(tag1409) || "on".equals(tag1409))) {
				a0195z = returnA0195z("1409", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1409");
				
				sess.save(a0195tag);
			}
			String tag1410 = this.getPageElement("tag1410").getValue();
			if (tag1410 != null && ("1".equals(tag1410) || "on".equals(tag1410))) {
				a0195z = returnA0195z("1410", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1410");
				
				sess.save(a0195tag);
			}
			// 政法口经历
			String tag1511 = this.getPageElement("tag1511").getValue();
			if (tag1511 != null && ("1".equals(tag1511) || "on".equals(tag1511))) {
				a0195z = returnA0195z("1511", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1511");
				sess.save(a0195tag);
			}
			String tag1501 = this.getPageElement("tag1501").getValue();
			if (tag1501 != null && ("1".equals(tag1501) || "on".equals(tag1501))) {
				a0195z = returnA0195z("1501", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1501");
				sess.save(a0195tag);
			}
			String tag1509 = this.getPageElement("tag1509").getValue();
			if (tag1509 != null && ("1".equals(tag1509) || "on".equals(tag1509))) {
				a0195z = returnA0195z("1509", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1509");
				sess.save(a0195tag);
			}
			/*
			String tag1502 = this.getPageElement("tag1502").getValue();
			String tag1502n = this.getPageElement("tag1502n").getValue();
			if (tag1502 != null && ("1".equals(tag1502) || "on".equals(tag1502))) {
				a0195z = returnA0195z("1502", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1502");
				a0195tag.setA0195c(tag1502n);
				sess.save(a0195tag);
			}
			String tag1503 = this.getPageElement("tag1503").getValue();
			String tag1503n = this.getPageElement("tag1503n").getValue();
			if (tag1503 != null && ("1".equals(tag1503) || "on".equals(tag1503))) {
				a0195z = returnA0195z("1503", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1503");
				a0195tag.setA0195c(tag1503n);
				sess.save(a0195tag);
			}
			String tag1504 = this.getPageElement("tag1504").getValue();
			String tag1504n = this.getPageElement("tag1504n").getValue();
			if (tag1504 != null && ("1".equals(tag1504) || "on".equals(tag1504))) {
				a0195z = returnA0195z("1504", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1504");
				a0195tag.setA0195c(tag1504n);
				sess.save(a0195tag);
			}
			String tag1505 = this.getPageElement("tag1505").getValue();
			String tag1505n = this.getPageElement("tag1505n").getValue();
			if (tag1505 != null && ("1".equals(tag1505) || "on".equals(tag1505))) {
				a0195z = returnA0195z("1505", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1505");
				a0195tag.setA0195c(tag1505n);
				sess.save(a0195tag);
			}
			String tag1506 = this.getPageElement("tag1506").getValue();
			String tag1506n = this.getPageElement("tag1506n").getValue();
			if (tag1506 != null && ("1".equals(tag1506) || "on".equals(tag1506))) {
				a0195z = returnA0195z("1506", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1506");
				a0195tag.setA0195c(tag1506n);
				sess.save(a0195tag);
			}
			String tag1507 = this.getPageElement("tag1507").getValue();
			String tag1507n = this.getPageElement("tag1507n").getValue();
			if (tag1507 != null && ("1".equals(tag1507) || "on".equals(tag1507))) {
				a0195z = returnA0195z("1507", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1507");
				a0195tag.setA0195c(tag1507n);
				sess.save(a0195tag);
			}
			String tag1508 = this.getPageElement("tag1508").getValue();
			String tag1508n = this.getPageElement("tag1508n").getValue();
			if (tag1508 != null && ("1".equals(tag1508) || "on".equals(tag1508))) {
				a0195z = returnA0195z("1508", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1508");
				a0195tag.setA0195c(tag1508n);
				sess.save(a0195tag);
			}
			
			String tag1510 = this.getPageElement("tag1510").getValue();
			if (tag1510 != null && ("1".equals(tag1510) || "on".equals(tag1510))) {
				a0195z = returnA0195z("1510", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1510");
				sess.save(a0195tag);
			}
			
			String tag1512 = this.getPageElement("tag1512").getValue();
			String tag1512n = this.getPageElement("tag1512n").getValue();
			if (tag1512 != null && ("1".equals(tag1512) || "on".equals(tag1512))) {
				a0195z = returnA0195z("1512", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1512");
				a0195tag.setA0195c(tag1512n);
				sess.save(a0195tag);
			}
			String tag1513 = this.getPageElement("tag1513").getValue();
			String tag1513n = this.getPageElement("tag1513n").getValue();
			if (tag1513 != null && ("1".equals(tag1513) || "on".equals(tag1513))) {
				a0195z = returnA0195z("1513", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1513");
				a0195tag.setA0195c(tag1513n);
				sess.save(a0195tag);
			}
			String tag1514 = this.getPageElement("tag1514").getValue();
			String tag1514n = this.getPageElement("tag1514n").getValue();
			if (tag1514 != null && ("1".equals(tag1514) || "on".equals(tag1514))) {
				a0195z = returnA0195z("1514", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1514");
				a0195tag.setA0195c(tag1514n);
				sess.save(a0195tag);
			}
			String tag1515 = this.getPageElement("tag1515").getValue();
			if (tag1515 != null && ("1".equals(tag1515) || "on".equals(tag1515))) {
				a0195z = returnA0195z("1515", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1515");
				sess.save(a0195tag);
			}
			String tag1516 = this.getPageElement("tag1516").getValue();
			String tag1516n = this.getPageElement("tag1516n").getValue();
			if (tag1516 != null && ("1".equals(tag1516) || "on".equals(tag1516))) {
				a0195z = returnA0195z("1516", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1516");
				a0195tag.setA0195c(tag1516n);
				sess.save(a0195tag);
			}
			String tag1517 = this.getPageElement("tag1517").getValue();
			String tag1517n = this.getPageElement("tag1517n").getValue();
			if (tag1517 != null && ("1".equals(tag1517) || "on".equals(tag1517))) {
				a0195z = returnA0195z("1517", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1517");
				a0195tag.setA0195c(tag1517n);
				sess.save(a0195tag);
			}
			String tag1518 = this.getPageElement("tag1518").getValue();
			if (tag1518 != null && ("1".equals(tag1518) || "on".equals(tag1518))) {
				a0195z = returnA0195z("1518", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1518");
				sess.save(a0195tag);
			}
			String tag1519 = this.getPageElement("tag1519").getValue();
			if (tag1519 != null && ("1".equals(tag1519) || "on".equals(tag1519))) {
				a0195z = returnA0195z("1519", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1519");
				sess.save(a0195tag);
			}
			String tag1520 = this.getPageElement("tag1520").getValue();
			if (tag1520 != null && ("1".equals(tag1520) || "on".equals(tag1520))) {
				a0195z = returnA0195z("1520", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1520");
				sess.save(a0195tag);
			}
			String tag1521 = this.getPageElement("tag1521").getValue();
			if (tag1521 != null && ("1".equals(tag1521) || "on".equals(tag1521))) {
				a0195z = returnA0195z("1521", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1521");
				sess.save(a0195tag);
			}
			String tag1522 = this.getPageElement("tag1522").getValue();
			if (tag1522 != null && ("1".equals(tag1522) || "on".equals(tag1522))) {
				a0195z = returnA0195z("1522", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1522");
				sess.save(a0195tag);
			}*/
			/*String tag1523 = this.getPageElement("tag1523").getValue();
			if (tag1523 != null && ("1".equals(tag1523) || "on".equals(tag1523))) {
				a0195z = returnA0195z("1523", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1523");
				
				sess.save(a0195tag);
			}*/
			/*String tag1524 = this.getPageElement("tag1524").getValue();
			if (tag1524 != null && ("1".equals(tag1524) || "on".equals(tag1524))) {
				a0195z = returnA0195z("1524", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1524");
				sess.save(a0195tag);
			}
			String tag1525 = this.getPageElement("tag1525").getValue();
			if (tag1525 != null && ("1".equals(tag1525) || "on".equals(tag1525))) {
				a0195z = returnA0195z("1525", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1525");
				sess.save(a0195tag);
			}
			String tag1526 = this.getPageElement("tag1526").getValue();
			if (tag1526 != null && ("1".equals(tag1526) || "on".equals(tag1526))) {
				a0195z = returnA0195z("1526", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1526");
				sess.save(a0195tag);
			}
			String tag1527 = this.getPageElement("tag1527").getValue();
			if (tag1527 != null && ("1".equals(tag1527) || "on".equals(tag1527))) {
				a0195z = returnA0195z("1527", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1527");
				sess.save(a0195tag);
			}
			String tag1528 = this.getPageElement("tag1528").getValue();
			if (tag1528 != null && ("1".equals(tag1528) || "on".equals(tag1528))) {
				a0195z = returnA0195z("1528", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1528");
				sess.save(a0195tag);
			}
			String tag1529 = this.getPageElement("tag1529").getValue();
			if (tag1529 != null && ("1".equals(tag1529) || "on".equals(tag1529))) {
				a0195z = returnA0195z("1529", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1529");
				sess.save(a0195tag);
			}*/
			/*String tag1530 = this.getPageElement("tag1530").getValue();
			if (tag1530 != null && ("1".equals(tag1530) || "on".equals(tag1530))) {
				a0195z = returnA0195z("1530", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1530");
				
				sess.save(a0195tag);
			}*/
			/*String tag1534 = this.getPageElement("tag1534").getValue();
			if (tag1534 != null && ("1".equals(tag1534) || "on".equals(tag1534))) {
				a0195z = returnA0195z("1534", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1534");
				sess.save(a0195tag);
			}
			String tag1535 = this.getPageElement("tag1535").getValue();
			if (tag1535 != null && ("1".equals(tag1535) || "on".equals(tag1535))) {
				a0195z = returnA0195z("1535", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1535");
				sess.save(a0195tag);
			}
			String tag1536 = this.getPageElement("tag1536").getValue();
			if (tag1536 != null && ("1".equals(tag1536) || "on".equals(tag1536))) {
				a0195z = returnA0195z("1536", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1536");
				sess.save(a0195tag);
			}
			String tag1537 = this.getPageElement("tag1537").getValue();
			if (tag1537 != null && ("1".equals(tag1537) || "on".equals(tag1537))) {
				a0195z = returnA0195z("1537", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1537");
				sess.save(a0195tag);
			}
			String tag1538 = this.getPageElement("tag1538").getValue();
			if (tag1538 != null && ("1".equals(tag1538) || "on".equals(tag1538))) {
				a0195z = returnA0195z("1538", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1538");
				sess.save(a0195tag);
			}
			String tag1539 = this.getPageElement("tag1539").getValue();
			if (tag1539 != null && ("1".equals(tag1539) || "on".equals(tag1539))) {
				a0195z = returnA0195z("1539", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1539");
				sess.save(a0195tag);
			}
			String tag1540 = this.getPageElement("tag1540").getValue();
			if (tag1540 != null && ("1".equals(tag1540) || "on".equals(tag1540))) {
				a0195z = returnA0195z("1540", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1540");
				sess.save(a0195tag);
			}
			String tag1541 = this.getPageElement("tag1541").getValue();
			if (tag1541 != null && ("1".equals(tag1541) || "on".equals(tag1541))) {
				a0195z = returnA0195z("1541", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1541");
				sess.save(a0195tag);
			}
			String tag1542 = this.getPageElement("tag1542").getValue();
			if (tag1542 != null && ("1".equals(tag1542) || "on".equals(tag1542))) {
				a0195z = returnA0195z("1542", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1542");
				sess.save(a0195tag);
			}
			String tag1543 = this.getPageElement("tag1543").getValue();
			if (tag1543 != null && ("1".equals(tag1543) || "on".equals(tag1543))) {
				a0195z = returnA0195z("1543", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1543");
				sess.save(a0195tag);
			}
			String tag1545 = this.getPageElement("tag1545").getValue();
			if (tag1545 != null && ("1".equals(tag1545) || "on".equals(tag1545))) {
				a0195z = returnA0195z("1545", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1545");
				sess.save(a0195tag);
			}
			String tag1546 = this.getPageElement("tag1546").getValue();
			if (tag1546 != null && ("1".equals(tag1546) || "on".equals(tag1546))) {
				a0195z = returnA0195z("1546", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1546");
				sess.save(a0195tag);
			}
			String tag1547 = this.getPageElement("tag1547").getValue();
			if (tag1547 != null && ("1".equals(tag1547) || "on".equals(tag1547))) {
				a0195z = returnA0195z("1547", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1547");
				sess.save(a0195tag);
			}
			String tag1548 = this.getPageElement("tag1548").getValue();
			if (tag1548 != null && ("1".equals(tag1548) || "on".equals(tag1548))) {
				a0195z = returnA0195z("1548", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1548");
				sess.save(a0195tag);
			}
			String tag1549 = this.getPageElement("tag1549").getValue();
			if (tag1549 != null && ("1".equals(tag1549) || "on".equals(tag1549))) {
				a0195z = returnA0195z("1549", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1549");
				sess.save(a0195tag);
			}
			String tag1550 = this.getPageElement("tag1550").getValue();
			if (tag1550 != null && ("1".equals(tag1550) || "on".equals(tag1550))) {
				a0195z = returnA0195z("1550", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1550");
				sess.save(a0195tag);
			}
			String tag1552 = this.getPageElement("tag1552").getValue();
			if (tag1552 != null && ("1".equals(tag1552) || "on".equals(tag1552))) {
				a0195z = returnA0195z("1552", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1552");
				sess.save(a0195tag);
			}
			String tag1553 = this.getPageElement("tag1553").getValue();
			if (tag1553 != null && ("1".equals(tag1553) || "on".equals(tag1553))) {
				a0195z = returnA0195z("1553", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1553");
				sess.save(a0195tag);
			}
			String tag1554 = this.getPageElement("tag1554").getValue();
			if (tag1554 != null && ("1".equals(tag1554) || "on".equals(tag1554))) {
				a0195z = returnA0195z("1554", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1554");
				sess.save(a0195tag);
			}
			String tag1555 = this.getPageElement("tag1555").getValue();
			if (tag1555 != null && ("1".equals(tag1555) || "on".equals(tag1555))) {
				a0195z = returnA0195z("1555", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1555");
				sess.save(a0195tag);
			}
			String tag1557 = this.getPageElement("tag1557").getValue();
			if (tag1557 != null && ("1".equals(tag1557) || "on".equals(tag1557))) {
				a0195z = returnA0195z("1557", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1557");
				sess.save(a0195tag);
			}
			String tag1558 = this.getPageElement("tag1558").getValue();
			if (tag1558 != null && ("1".equals(tag1558) || "on".equals(tag1558))) {
				a0195z = returnA0195z("1558", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1558");
				sess.save(a0195tag);
			}
			String tag1560 = this.getPageElement("tag1560").getValue();
			if (tag1560 != null && ("1".equals(tag1560) || "on".equals(tag1560))) {
				a0195z = returnA0195z("1560", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1560");
				sess.save(a0195tag);
			}
			String tag1561 = this.getPageElement("tag1561").getValue();
			if (tag1561 != null && ("1".equals(tag1561) || "on".equals(tag1561))) {
				a0195z = returnA0195z("1561", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1561");
				sess.save(a0195tag);
			}
			String tag1562 = this.getPageElement("tag1562").getValue();
			if (tag1562 != null && ("1".equals(tag1562) || "on".equals(tag1562))) {
				a0195z = returnA0195z("1562", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1562");
				sess.save(a0195tag);
			}
			String tag1563 = this.getPageElement("tag1563").getValue();
			if (tag1563 != null && ("1".equals(tag1563) || "on".equals(tag1563))) {
				a0195z = returnA0195z("1563", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1563");
				sess.save(a0195tag);
			}
			String tag1564 = this.getPageElement("tag1564").getValue();
			if (tag1564 != null && ("1".equals(tag1564) || "on".equals(tag1564))) {
				a0195z = returnA0195z("1564", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1564");
				sess.save(a0195tag);
			}
			String tag1565 = this.getPageElement("tag1565").getValue();
			if (tag1565 != null && ("1".equals(tag1565) || "on".equals(tag1565))) {
				a0195z = returnA0195z("1565", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1565");
				sess.save(a0195tag);
			}
			String tag1566 = this.getPageElement("tag1566").getValue();
			if (tag1566 != null && ("1".equals(tag1566) || "on".equals(tag1566))) {
				a0195z = returnA0195z("1566", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1566");
				sess.save(a0195tag);
			}
			String tag1567 = this.getPageElement("tag1567").getValue();
			if (tag1567 != null && ("1".equals(tag1567) || "on".equals(tag1567))) {
				a0195z = returnA0195z("1567", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1567");
				sess.save(a0195tag);
			}
			String tag1568 = this.getPageElement("tag1568").getValue();
			if (tag1568 != null && ("1".equals(tag1568) || "on".equals(tag1568))) {
				a0195z = returnA0195z("1568", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1568");
				sess.save(a0195tag);
			}
			String tag1569 = this.getPageElement("tag1569").getValue();
			if (tag1569 != null && ("1".equals(tag1569) || "on".equals(tag1569))) {
				a0195z = returnA0195z("1569", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1569");
				sess.save(a0195tag);
			}
			String tag1570 = this.getPageElement("tag1570").getValue();
			if (tag1570 != null && ("1".equals(tag1570) || "on".equals(tag1570))) {
				a0195z = returnA0195z("1570", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1570");
				sess.save(a0195tag);
			}*/
			// 秘书经历
			String tag1601 = this.getPageElement("tag1601").getValue();
			if (tag1601 != null && ("1".equals(tag1601) || "on".equals(tag1601))) {
				a0195z = returnA0195z("1601", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1601");
				
				sess.save(a0195tag);
			}
			String tag1602 = this.getPageElement("tag1602").getValue();
			if (tag1602 != null && ("1".equals(tag1602) || "on".equals(tag1602))) {
				a0195z = returnA0195z("1602", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1602");
				
				sess.save(a0195tag);
			}
			String tag1603 = this.getPageElement("tag1603").getValue();
			if (tag1603 != null && ("1".equals(tag1603) || "on".equals(tag1603))) {
				a0195z = returnA0195z("1603", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1603");
				
				sess.save(a0195tag);
			}
			String tag1604 = this.getPageElement("tag1604").getValue();
			if (tag1604 != null && ("1".equals(tag1604) || "on".equals(tag1604))) {
				a0195z = returnA0195z("1604", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1604");
				
				sess.save(a0195tag);
			}
			String tag1605 = this.getPageElement("tag1605").getValue();
			if (tag1605 != null && ("1".equals(tag1605) || "on".equals(tag1605))) {
				a0195z = returnA0195z("1605", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1605");
				
				sess.save(a0195tag);
			}
			String tag1606 = this.getPageElement("tag1606").getValue();
			if (tag1606 != null && ("1".equals(tag1606) || "on".equals(tag1606))) {
				a0195z = returnA0195z("1606", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1606");
				
				sess.save(a0195tag);
			}
			String tag1607 = this.getPageElement("tag1607").getValue();
			if (tag1607 != null && ("1".equals(tag1607) || "on".equals(tag1607))) {
				a0195z = returnA0195z("1607", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1607");
				
				sess.save(a0195tag);
			}
			String tag1608 = this.getPageElement("tag1608").getValue();
			if (tag1608 != null && ("1".equals(tag1608) || "on".equals(tag1608))) {
				a0195z = returnA0195z("1608", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1608");
				
				sess.save(a0195tag);
			}
			// 开发区、高新区、自贸区等经历
			String tag1701 = this.getPageElement("tag1701").getValue();
			if (tag1701 != null && ("1".equals(tag1701) || "on".equals(tag1701))) {
				a0195z = returnA0195z("1701", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1701");
				
				sess.save(a0195tag);
			}
			String tag1702 = this.getPageElement("tag1702").getValue();
			if (tag1702 != null && ("1".equals(tag1702) || "on".equals(tag1702))) {
				a0195z = returnA0195z("1702", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1702");
				
				sess.save(a0195tag);
			}
			// 海外工作经历
			String tag2001 = this.getPageElement("tag2001").getValue();
			if (tag2001 != null && ("1".equals(tag2001) || "on".equals(tag2001))) {
				a0195z = returnA0195z("2001", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2001");
				
				sess.save(a0195tag);
			}
			String tag2002 = this.getPageElement("tag2002").getValue();
			if (tag2002 != null && ("1".equals(tag2002) || "on".equals(tag2002))) {
				a0195z = returnA0195z("2002", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2002");
				
				sess.save(a0195tag);
			}
			String tag2003 = this.getPageElement("tag2003").getValue();
			if (tag2003 != null && ("1".equals(tag2003) || "on".equals(tag2003))) {
				a0195z = returnA0195z("2003", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2003");
				
				sess.save(a0195tag);
			}
			String tag2004 = this.getPageElement("tag2004").getValue();
			if (tag2004 != null && ("1".equals(tag2004) || "on".equals(tag2004))) {
				a0195z = returnA0195z("2004", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2004");
				
				sess.save(a0195tag);
			}
			String tag2005 = this.getPageElement("tag2005").getValue();
			if (tag2005 != null && ("1".equals(tag2005) || "on".equals(tag2005))) {
				a0195z = returnA0195z("2005", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2005");
				
				sess.save(a0195tag);
			}
			String tag2006 = this.getPageElement("tag2006").getValue();
			if (tag2006 != null && ("1".equals(tag2006) || "on".equals(tag2006))) {
				a0195z = returnA0195z("2006", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2006");
				
				sess.save(a0195tag);
			}
			// 省属企业领导班子工作经历
			String tag2101 = this.getPageElement("tag2101").getValue();
			if (tag2101 != null && ("1".equals(tag2101) || "on".equals(tag2101))) {
				a0195z = returnA0195z("2101", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2101");
				sess.save(a0195tag);
			}
			String tag2102 = this.getPageElement("tag2102").getValue();
			if (tag2102 != null && ("1".equals(tag2102) || "on".equals(tag2102))) {
				a0195z = returnA0195z("2102", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2102");
				sess.save(a0195tag);
			}
			String tag2103 = this.getPageElement("tag2103").getValue();
			if (tag2103 != null && ("1".equals(tag2103) || "on".equals(tag2103))) {
				a0195z = returnA0195z("2103", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2103");
				sess.save(a0195tag);
			}
			String tag2104 = this.getPageElement("tag2104").getValue();
			if (tag2104 != null && ("1".equals(tag2104) || "on".equals(tag2104))) {
				a0195z = returnA0195z("2104", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2104");
				sess.save(a0195tag);
			}
			/*
			String tag2105 = this.getPageElement("tag2105").getValue();
			if (tag2105 != null && ("1".equals(tag2105) || "on".equals(tag2105))) {
				a0195z = returnA0195z("2105", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2105");
				sess.save(a0195tag);
			}
			// 下级企业正职任职经历
			String tag2201 = this.getPageElement("tag2201").getValue();
			if (tag2201 != null && ("1".equals(tag2201) || "on".equals(tag2201))) {
				a0195z = returnA0195z("2201", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2201");
				sess.save(a0195tag);
			}
			String tag2202 = this.getPageElement("tag2202").getValue();
			if (tag2202 != null && ("1".equals(tag2202) || "on".equals(tag2202))) {
				a0195z = returnA0195z("2202", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2202");
				sess.save(a0195tag);
			}
			// 总部职能部门任职经历
			String tag2301 = this.getPageElement("tag2301").getValue();
			if (tag2301 != null && ("1".equals(tag2301) || "on".equals(tag2301))) {
				a0195z = returnA0195z("2301", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2301");
				sess.save(a0195tag);
			}
			String tag2302 = this.getPageElement("tag2302").getValue();
			if (tag2302 != null && ("1".equals(tag2302) || "on".equals(tag2302))) {
				a0195z = returnA0195z("2302", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2302");
				sess.save(a0195tag);
			}
			*/
			
			// 上挂下派工作经历
			String tag2401 = this.getPageElement("tag2401").getValue();
			if (tag2401 != null && ("1".equals(tag2401) || "on".equals(tag2401))) {
				a0195z = returnA0195z("2401", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2401");
				sess.save(a0195tag);
			}
			String tag2402 = this.getPageElement("tag2402").getValue();
			if (tag2402 != null && ("1".equals(tag2402) || "on".equals(tag2402))) {
				a0195z = returnA0195z("2402", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2402");
				sess.save(a0195tag);
			}
			String tag2403 = this.getPageElement("tag2403").getValue();
			if (tag2403 != null && ("1".equals(tag2403) || "on".equals(tag2403))) {
				a0195z = returnA0195z("2403", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2403");
				sess.save(a0195tag);
			}
			String tag2404 = this.getPageElement("tag2404").getValue();
			if (tag2404 != null && ("1".equals(tag2404) || "on".equals(tag2404))) {
				a0195z = returnA0195z("2404", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("2404");
				sess.save(a0195tag);
			}
			// 其他经历
			String tag1801 = this.getPageElement("tag1801").getValue();
			if (tag1801 != null && ("1".equals(tag1801) || "on".equals(tag1801))) {
				a0195z = returnA0195z("1801", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1801");
				
				sess.save(a0195tag);
			}
			String tag1802 = this.getPageElement("tag1802").getValue();
			if (tag1802 != null && ("1".equals(tag1802) || "on".equals(tag1802))) {
				a0195z = returnA0195z("1802", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1802");
				
				sess.save(a0195tag);
			}
			String tag1803 = this.getPageElement("tag1803").getValue();
			if (tag1803 != null && ("1".equals(tag1803) || "on".equals(tag1803))) {
				a0195z = returnA0195z("1803", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1803");
				
				sess.save(a0195tag);
			}
			String tag1805 = this.getPageElement("tag1805").getValue();
			if (tag1805 != null && ("1".equals(tag1805) || "on".equals(tag1805))) {
				a0195z = returnA0195z("1805", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1805");
				
				sess.save(a0195tag);
			}
			String tag1806 = this.getPageElement("tag1806").getValue();
			if (tag1806 != null && ("1".equals(tag1806) || "on".equals(tag1806))) {
				a0195z = returnA0195z("1806", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1806");
				
				sess.save(a0195tag);
			}
			String tag1807 = this.getPageElement("tag1807").getValue();
			if (tag1807 != null && ("1".equals(tag1807) || "on".equals(tag1807))) {
				a0195z = returnA0195z("1807", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1807");
				
				sess.save(a0195tag);
			}
			
			String tag1821 = this.getPageElement("tag1821").getValue();
			if (tag1821 != null && ("1".equals(tag1821) || "on".equals(tag1821))) {
				a0195z = returnA0195z("1821", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1821");
				
				sess.save(a0195tag);
			}
			String tag1822 = this.getPageElement("tag1822").getValue();
			if (tag1822 != null && ("1".equals(tag1822) || "on".equals(tag1822))) {
				a0195z = returnA0195z("1822", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1822");
				
				sess.save(a0195tag);
			}
			String tag1823 = this.getPageElement("tag1823").getValue();
			if (tag1823 != null && ("1".equals(tag1823) || "on".equals(tag1823))) {
				a0195z = returnA0195z("1823", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1823");
				
				sess.save(a0195tag);
			}
			String tag1824 = this.getPageElement("tag1824").getValue();
			if (tag1824 != null && ("1".equals(tag1824) || "on".equals(tag1824))) {
				a0195z = returnA0195z("1824", a0195z);
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1824");
				
				sess.save(a0195tag);
			}
			
			
			String tag1809 = this.getPageElement("tag1809").getValue();
			String tag1809n = this.getPageElement("tag1809n").getValue();
			if (tag1809 != null && ("1".equals(tag1809) || "on".equals(tag1809))) {
				a0195z += tag1809n + "；";
				A0195Tag a0195tag = new A0195Tag();
				a0195tag.setA0000(a0000);
				a0195tag.setTagid(UUID.randomUUID().toString());
				a0195tag.setCreatedate(curTime);
				a0195tag.setA0195("1809");
				a0195tag.setA0195c(tag1809n);
				
				sess.save(a0195tag);
			}
			sess.flush();
			/*for (a0195tag at : a0193listold) {
				HBUtil.executeUpdate("delete from a0193_tag where tagid='" + at.getTagid() + "'");
			}*/
			if (a0195z.length() > 0) {
				a0195z = a0195z.substring(0, a0195z.length() - 1);
				a0195z = a0195z + "。";
			}
			ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);	
				extratags.setA1401b(a0195z);
				applog.createLogNew("3652","历任重要职务（经历）新增","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new ExtraTags() ,extratags) );
			}else{
				ExtraTags extratags1 = new ExtraTags();
				extratags1.setA1401b(extratags.getA1401b());
				ExtraTags extratags2 = new ExtraTags();
				extratags2.setA1401b(a0195z);
				if(extratags.getA1401b() != null && !"".equals(extratags.getA1401b())){
					if( "".equals(a0195z) ){
						applog.createLogNew("3654","历任重要职务（经历）删除","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
					}else{
						applog.createLogNew("3653","历任重要职务（经历）修改","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
					}					
				}else{
					applog.createLogNew("3652","历任重要职务（经历）新增","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
				}	
			}	
			extratags.setA1401b(a0195z);
			extratags.setA0184(a01.getA0184());
			// 人员基本信息界面
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('sza0193z').value='" + extratags.getA1401b() + "'");
			sess.saveOrUpdate(extratags);
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private String returnA0195z(String a0195, String a0195z) throws AppException {
		String a0195Name = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE",
				"CODE_TYPE='TAGZB131' and CODE_VALUE='" + a0195 + "'");
		a0195z += a0195Name + "；";
		return a0195z;

	}

}
