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

public class HandleLeaderPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String leacerid = this.getPageElement("subWinIdBussessId2").getValue();
	/*	String leacerid = param.split("@")[0];
		String trainid = param.split("@")[1];
		this.getPageElement("trainid").setValue(trainid);*/
		try {
			if(leacerid!=null&&!"".equals(leacerid)){
				HBSession sess = HBUtil.getHBSession();
				TrainLeader t = (TrainLeader)sess.get(TrainLeader.class, leacerid);
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
		TrainLeader t = new TrainLeader();
		HBSession sess = HBUtil.getHBSession();
		String leacerid = this.getPageElement("leacerid").getValue();
		String trainid = this.getPageElement("trainid").getValue();
		String a0101 = this.getPageElement("a0101").getValue();
		String a0104 = this.getPageElement("a0104").getValue();
		String a0184 = this.getPageElement("a0184").getValue();
		String a0177 = this.getPageElement("a0177").getValue();
		String a0192a = this.getPageElement("a0192a").getValue();
		String g11037 = this.getPageElement("g11037").getValue();
		String g11038 = this.getPageElement("g11038").getValue();
		String a1107c = this.getPageElement("a1107c").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		String g11027 = this.getPageElement("g11027").getValue();
		if(StringUtils.isEmpty(leacerid)){
			t.setTrainid(trainid);
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setA0192a(a0192a);
			t.setG11037(g11037);
			t.setG11038(g11038);
			t.setA1107c(Long.valueOf((StringUtils.isEmpty(a1107c))?"0":a1107c));
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
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
			t.setLeacerid(leacerid);
			t.setTrainid(trainid);
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setA0192a(a0192a);
			t.setG11037(g11037);
			t.setG11038(g11038);
			t.setA1107c(Long.valueOf((StringUtils.isEmpty(a1107c))?"0":a1107c));
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
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