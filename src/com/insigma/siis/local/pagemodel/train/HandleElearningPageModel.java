package com.insigma.siis.local.pagemodel.train;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.fr.stable.StringUtils;
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
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.TrainElearning;

public class HandleElearningPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String elearningid = this.getPageElement("subWinIdBussessId2").getValue();
		try {
			if(elearningid!=null&&!"".equals(elearningid)){
				HBSession sess = HBUtil.getHBSession();
				TrainElearning t = (TrainElearning)sess.get(TrainElearning.class, elearningid);
				PMPropertyCopyUtil.copyObjValueToElement(t, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("≤È—Ø ß∞‹");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save() throws RadowException{
		TrainElearning t = new TrainElearning();
		HBSession sess = HBUtil.getHBSession();
		String elearningid = this.getPageElement("elearningid").getValue();
		String a0101 = this.getPageElement("a0101").getValue();
		String a0104 = this.getPageElement("a0104").getValue();
		String a0184 = this.getPageElement("a0184").getValue();
		String a0177 = this.getPageElement("a0177").getValue();
		String a0192a = this.getPageElement("a0192a").getValue();
		String g11020 = this.getPageElement("g11020").getValue();
		String g11042 = this.getPageElement("g11042").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		String a1107 = this.getPageElement("a1107").getValue();
		String a1111 = this.getPageElement("a1111").getValue();
		String g11027 = this.getPageElement("g11027").getValue();
		if(StringUtils.isEmpty(elearningid)){
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setA0192a(a0192a);
			t.setG11020(Long.valueOf((StringUtils.isEmpty(g11020))?"0":g11020));
			t.setG11042(g11042);
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
			t.setA1107(a1107);
			t.setA1111(a1111);
			t.setG11027(g11027);
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			sess.save(t);
			sess.flush();
		}else{
			t.setElearningid(elearningid);
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setA0192a(a0192a);
			t.setG11020(Long.valueOf((StringUtils.isEmpty(g11020))?"0":g11020));
			t.setG11042(g11042);
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
			t.setA1107(a1107);
			t.setA1111(a1111);
			t.setG11027(g11027);
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			sess.update(t);
			sess.flush();
		}
		this.getExecuteSG().addExecuteCode("saveCallBack();");
		return EventRtnType.NORMAL_SUCCESS;
	}
}