package com.insigma.siis.local.pagemodel.modeldb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.modeldb.ModeldbBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class LoadGrantPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		String ids = this.getRadow_parent_data();
		this.getPageElement("ids").setValue(ids);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("querybyid")
	@NoRequiredValidate           
	@OpLog
	public int query(String id)throws RadowException, AppException {
		this.getPageElement("checkedgroupid").setValue(id);
		this.setNextEventName("Grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("Grid.dogridquery")
	@NoRequiredValidate   
	public int dogridQuery(int start,int limit) throws RadowException{
		String ids = this.getPageElement("ids").getValue().trim();
		ids = ids.substring(1, ids.length()-1);
		String[] privileges = ids.split(",");
		StringBuffer idsInbf = new StringBuffer();
		String idsIn = "";
		for(String id :privileges){
			idsInbf.append("'").append(id).append("',");
		}
		idsIn = idsInbf.toString().substring(0,idsInbf.lastIndexOf(",") );
		
		String checkedgroupid = this.getPageElement("checkedgroupid").getValue();
		StringBuilder sb = new  StringBuilder();
		sb.append("select s.modelroleid,u.userid,u.loginname,u.username,u.useful,CASE WHEN s.userid = u.userid  THEN 'true'ELSE 'false' END checked  ");
		sb.append("   From Smt_User u Join Smt_Usergroupref a On u.Userid = a.Userid  LEFT Join Sysmodelrole s    On u.Userid = s.Userid ");
		sb.append(" and s.sub_libraries_model_id in ("+idsIn+")");
		sb.append(" where a.groupid ='"+checkedgroupid+"' ");
		CommonQueryBS.systemOut(sb.toString());
		this.setSelfDefResData(this.pageQuery(sb.toString(),"SQL", start, limit)); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("btnSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int btnSave() throws Exception {
		List<HashMap<String, Object>> gridlist = this.getPageElement("Grid").getValueList();
		List<String> userlist = new ArrayList<String>();
		String ids = this.getPageElement("ids").getValue().trim();
		ids = ids.substring(1, ids.length()-1);
		String[] privileges = ids.split(",");
		for(HashMap<String, Object> map : gridlist){
			if("true".equals(map.get("checked").toString())){
				userlist.add(map.get("userid").toString());
			}
		}
		ModeldbBS.SaveGrant(privileges,userlist);
		this.setMainMessage("ÊÚÈ¨³É¹¦");
		this.closeCueWindowByYes("grantWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	

	

}
