package com.insigma.siis.local.pagemodel.zj.tags;

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
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.AttrLrzw;
import com.insigma.siis.local.business.entity.extra.A0194Tag;
import com.insigma.siis.local.business.entity.extra.ExtraTags;

/**
 * 熟悉领域
 * 
 * @author zhubo
 *
 */
public class A0194TagsAddPagePageModel extends PageModel {

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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
		if (extratags != null) {
			this.getPageElement("a0194z").setValue(extratags.getA0194z());
			String a0194s = extratags.getA0194zcode();
			if (a0194s != null && !"".equals(a0194s)) {
				List<AttrLrzw> list = sess.createQuery("from AttrLrzw where a0000='"+a0000+"'").list();
				AttrLrzw attribute = new AttrLrzw();
				if(list.size()!=0){
					attribute = list.get(0);
				}else{
					attribute.setA0000(a0000);
					sess.saveOrUpdate(attribute);
					sess.flush();
				}
				//this.getPageElement("attr2101").getValue(null);
				PMPropertyCopyUtil.copyObjValueToElement(attribute, this);  //将值赋值到页面
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a0194z = this.getPageElement("a0194z").getValue();
		String a0194s = this.getPageElement("a0194s").getValue();
		
		try {
			HBSession sess = HBUtil.getHBSession();
			ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);
			}
			extratags.setA0194z(a0194z);
			extratags.setA0194zcode(a0194s);
			
			
			
			AttrLrzw attribute = (AttrLrzw) sess.createQuery("from AttrLrzw where a0000='"+a0000+"'").uniqueResult();
			
			A01 a01 = (A01) sess.get(A01.class, a0000);
			boolean isAdd = true;
			AttrLrzw attribute_old = null;
			if(a01==null){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(attribute==null){//增加日志
				attribute = new AttrLrzw();
				attribute.setA0000(a0000);
			}else{//修改日志
				isAdd = false;
				attribute_old = attribute.clone();
			}
			this.copyElementsValueToObj(attribute, this);
						
			attribute.setA0000(a0000);
			sess.saveOrUpdate(attribute);
			
			
			

			// 人员基本信息界面
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('a0194z').value='" + extratags.getA0194z() + "'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0194zcode').value='"
					+ extratags.getA0194zcode() + "'");
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
