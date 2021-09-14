package com.insigma.siis.local.pagemodel.zj.slabel;

import java.util.ArrayList;
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
import com.insigma.siis.local.business.slabel.A1401LTag;
import com.insigma.siis.local.business.slabel.ExtraTags;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 使用方式建议
 * 
 * @author zhubo
 *
 */
public class A1401LTagsAddPagePageModel extends PageModel {

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
		List<A1401LTag> a1401llist = sess.createQuery("from com.insigma.siis.local.business.slabel.A1401LTag where a0000='" + a0000 + "'").list();
		for (A1401LTag at : a1401llist) {
			String a1401l = at.getA1401l();
			this.getPageElement("tag" + a1401l).setValue("1");
//			if (null != at.getA1401l() && !"".equals(at.getA1401l())) {
//				this.getPageElement("tag" + a1401l + "n").setValue(at.getA1401lc());
//			}
//			if ("1809".equals(at.getA1401l())) {
//				this.getExecuteSG().addExecuteCode("$('#tag1809n').attr('disabled',false);");
//			}
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
			long curTime = System.currentTimeMillis(); // 获取服务器系统当前时间戳.
			sess.createSQLQuery("delete from zjs_A1401L_Tag where a0000='" + a0000 + "'").executeUpdate();
			//List<A0195Tag> a0193listold = sess.createQuery("from com.insigma.siis.local.business.slabel.A0195Tag where a0000='" + a0000 + "'").list();
			//tag标签
			List<String> tagList = new ArrayList<String>();
			tagList.add("0101");
			tagList.add("0201");
			tagList.add("0202");
			tagList.add("0203");
			tagList.add("0301");
			tagList.add("0302");
			tagList.add("0303");
			tagList.add("0304");
			tagList.add("0305");
			tagList.add("0306");
			tagList.add("0307");
			tagList.add("0401");
			tagList.add("0402");
			tagList.add("0403");
			tagList.add("0404");
			
			String a1401lc = "";
			for(String tagName:tagList){
				String tags = this.getPageElement("tag"+tagName).getValue();
				if (tags != null && ("1".equals(tags) || "on".equals(tags))) {
					a1401lc = returnA1401L(tagName, a1401lc);
					A1401LTag a1401lTag = new A1401LTag();
					a1401lTag.setA0000(a0000);
					a1401lTag.setTagid(UUID.randomUUID().toString());
					a1401lTag.setCreatedate(curTime);
					a1401lTag.setA1401l(tagName);
					sess.save(a1401lTag);
				}
			}
			
			sess.flush();
			/*for (a0195tag at : a0193listold) {
				HBUtil.executeUpdate("delete from a0193_tag where tagid='" + at.getTagid() + "'");
			}*/
			if (a1401lc.length() > 0) {
				a1401lc = a1401lc.substring(0, a1401lc.length() - 1);
				a1401lc = a1401lc + "。";
			}
			
//			ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
//			if (extratags == null) {
//				extratags = new ExtraTags();
//				extratags.setA0000(a0000);
//				extratags.setA1401LC(a1401lc);
//				applog.createLogNew("3652","使用建议","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new ExtraTags() ,extratags) );
//			}else{
//				ExtraTags extratags1 = new ExtraTags();
//				extratags1.setA1401b(extratags.getA1401b());
//				ExtraTags extratags2 = new ExtraTags();
//				extratags2.setA1401LC(a1401lc);
//				if(extratags.getA1401b() != null && !"".equals(extratags.getA1401b())){
//					if( "".equals(a1401lc) ){
//						applog.createLogNew("3654","使用建议删除","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
//					}else{
//						applog.createLogNew("3653","使用建议修改","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
//					}					
//				}else{
//					applog.createLogNew("3652","使用建议新增","标签信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(extratags1,extratags2 ));
//				}	
//			}	
//			extratags.setA1401LC(a1401lc);
//			extratags.setA0184(a01.getA0184());
			// 人员基本信息界面
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('a1401l').value='" + a1401lc + "'");
			//sess.saveOrUpdate(extratags);
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
	
	private String returnA1401L(String a1401l, String a1401lc) throws AppException {
		String a1401lName = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE",
				"CODE_TYPE='A1401L' and CODE_VALUE='" + a1401l + "'");
		a1401lc += a1401lName + "；";
		return a1401lc;

	}

}
