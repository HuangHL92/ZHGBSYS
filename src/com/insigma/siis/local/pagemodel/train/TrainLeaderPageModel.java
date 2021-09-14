package com.insigma.siis.local.pagemodel.train;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.TrainLeader;

public class TrainLeaderPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String trainid = this.getPageElement("subWinIdBussessId2").getValue();
		this.getPageElement("trainid").setValue(trainid);
		this.setNextEventName("leaderGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("leaderGrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String trainid = this.getPageElement("trainid").getValue();
		String sql = "select * from TRAIN_LEADER where trainid='"+trainid+"'";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@Transaction
	@PageEvent("deleteLeader")
	@NoRequiredValidate         
	public int deleteLeader() throws RadowException, AppException, PrivilegeException{
		StringBuffer checkIds = new StringBuffer();
		PageElement pe = this.getPageElement("leaderGrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("pcheck");
				if(usercheck.toString().equals("true")){
					checkIds.append("'").append(map.get("leacerid").toString()).append("',");
				}
			}
		}
		if(checkIds.length() == 0){
			this.setMainMessage("请一个勾选需要删除的人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String leacerid = checkIds.toString();
		leacerid = leacerid.substring(0, leacerid.length()-1);
		HBSession session = HBUtil.getHBSession();
		session.createSQLQuery("delete from train_leader where leacerid in ("+leacerid+")").executeUpdate();
		this.getExecuteSG().addExecuteCode("deleteCallBack();");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
