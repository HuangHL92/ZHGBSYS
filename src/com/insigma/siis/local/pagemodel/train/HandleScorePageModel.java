package com.insigma.siis.local.pagemodel.train;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainLeader;
import com.insigma.siis.local.business.entity.TrainScore;

public class HandleScorePageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String scoreid = this.getPageElement("subWinIdBussessId2").getValue();
		try {
			if(scoreid!=null&&!"".equals(scoreid)){
				HBSession sess = HBUtil.getHBSession();
				TrainScore t = (TrainScore)sess.get(TrainScore.class, scoreid);
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
		TrainScore t = new TrainScore();
		HBSession sess = HBUtil.getHBSession();
		String scoreid = this.getPageElement("scoreid").getValue();
		String a0101 = this.getPageElement("a0101").getValue();
		String a0104 = this.getPageElement("a0104").getValue();
		String a0184 = this.getPageElement("a0184").getValue();
		String a0177 = this.getPageElement("a0177").getValue();
		String g11020 = this.getPageElement("g11020").getValue();
		String g11039 = this.getPageElement("g11039").getValue();
		String g11040 = this.getPageElement("g11040").getValue();
		String g11041 = this.getPageElement("g11041").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		String a0192a = this.getPageElement("a0192a").getValue();
		String g11027 = this.getPageElement("g11027").getValue();
		if(StringUtils.isEmpty(scoreid)){
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setG11020(Long.valueOf((StringUtils.isEmpty(g11020))?"0":g11020));
			t.setG11039(g11039);
			t.setG11040(g11040);
			t.setG11041(Long.valueOf((StringUtils.isEmpty(g11041))?"0":g11041));
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
			t.setA0192a(a0192a);
			t.setG11027(g11027);
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			sess.save(t);
			sess.flush();
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		}else{
			t.setScoreid(scoreid);
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setG11020(Long.valueOf((StringUtils.isEmpty(g11020))?"0":g11020));
			t.setG11039(g11039);
			t.setG11040(g11040);
			t.setG11041(Long.valueOf((StringUtils.isEmpty(g11041))?"0":g11041));
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
			t.setA0192a(a0192a);
			t.setG11027(g11027);
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			sess.update(t);
			sess.flush();
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}