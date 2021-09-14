package com.insigma.siis.local.pagemodel.train;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.insigma.siis.local.business.entity.TrainPersonnel;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class TrainPersonPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String personnelids = this.getPageElement("subWinIdBussessId2").getValue();
		this.getPageElement("trainid").setValue(personnelids.split("@")[1]);
		String trainid=personnelids.split("@")[1];
		this.getPageElement("personnelid").setValue(personnelids.split("@")[0]);
		String personnelid = personnelids.split("@")[0];
//		try {
//			if(personnelid!=null&&!"".equals(personnelid)){
//				HBSession sess = HBUtil.getHBSession();
//				TrainPersonnel t = (TrainPersonnel)sess.get(TrainPersonnel.class, personnelid);
//				PMPropertyCopyUtil.copyObjValueToElement(t, this);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setMainMessage("≤È—Ø ß∞‹");
//		}
		String sql = "select a.a0101,a.a0104,a.a0184,b.a0192a,a.a1108 from TRAIN_PERSONNEL a  left join a01 b on a.a0184=b.a0184 where personnelid='"+personnelid+"'  ";
		CommQuery commQuery =new CommQuery();
		try {
			List<HashMap<String, Object>> list = commQuery.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("a0101").setValue(isnull(map.get("a0101")));
				this.getPageElement("a0104").setValue(isnull(map.get("a0104")));
				this.getPageElement("a0184").setValue(isnull(map.get("a0184")));
				//this.getPageElement("a1108").setValue(isnull(map.get("a1108")));
				this.getPageElement("a0192a").setValue(isnull(map.get("a0192a")));
			}
		} catch (AppException e) {
			
			e.printStackTrace();
		}
		this.setNextEventName("traingrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save() throws RadowException{
		TrainPersonnel t = new TrainPersonnel();
		HBSession sess = HBUtil.getHBSession();
		String trainid = this.getPageElement("trainid").getValue();
		String personnelid = this.getPageElement("personnelid").getValue();
		String a0101 = this.getPageElement("a0101").getValue();
		String a0104 = this.getPageElement("a0104").getValue();
		String a0184 = this.getPageElement("a0184").getValue();
		String a0177 = this.getPageElement("a0177").getValue();
		String a0192a = this.getPageElement("a0192a").getValue();
		String g11027 = this.getPageElement("g11027").getValue();
		String g11028 = this.getPageElement("g11028").getValue();
		String g11029 = this.getPageElement("g11029").getValue();
		String g11032 = this.getPageElement("g11032").getValue();
		String g02003 = this.getPageElement("g02003").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		if(StringUtils.isEmpty(personnelid)){
			t.setTrainid(trainid);
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setA0192a(a0192a);
			t.setG11027(g11027);
			t.setG11028(g11028);
			t.setG11029(g11029);
			t.setG11032(g11032);
			t.setG02003(g02003);
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			sess.save(t);
			sess.flush();
			this.getPageElement("personnelid").setValue(t.getPersonnelid());
		}else{
			t.setTrainid(trainid);
			t.setPersonnelid(personnelid);
			t.setA0101(a0101);
			t.setA0104(a0104);
			t.setA0184(a0184);
			t.setA0177(a0177);
			t.setA0192a(a0192a);
			t.setG11027(g11027);
			t.setG11028(g11028);
			t.setG11029(g11029);
			t.setG11032(g11032);
			t.setG02003(g02003);
			t.setA1108(Long.valueOf((StringUtils.isEmpty(a1108))?"0":a1108));
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
	
	public String isnull(Object obj) {
		String str="";
		if(obj!=null&&!"".equals(obj)) {
			str=obj.toString();
		}
		return str;
	}
	
	@PageEvent("traingrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		
		String sql = "select a.trainid,g11020,a1131,a1101,g11024,a.g11021 from train a,TRAIN_PERSONNEL b where a.trainid=b.trainid and  a0184 ='"+this.getPageElement("a0184").getValue()+"'  order by to_number(g11020) desc,a1131";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
}
