package com.insigma.siis.local.pagemodel.train;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class TrainMessagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		this.getPageElement("a0184param").setValue(param.split(",")[1]);
		this.getPageElement("ndparam").setValue(param.split(",")[2]);
		this.getExecuteSG().addExecuteCode("loadTitle('"+param.split(",")[0]+","+param.split(",")[2]+"');");
		this.setNextEventName("personGrid.dogridquery");
		this.setNextEventName("leaderGrid.dogridquery");
		this.setNextEventName("elearningGrid.dogridquery");
		this.setNextEventName("scoreGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("personGrid.dogridquery")
	@NoRequiredValidate         
	public int personGridQuery(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String a0184 = this.getPageElement("a0184param").getValue();
		String nd = this.getPageElement("ndparam").getValue();
		String sql ="select t.a1131 trainname, p.a0104, p.g11027, p.g11028, p.g11029, p.g11032, p.a1108   "+
"  from train t   "+
"  join train_personnel p   "+
"    on t.trainid = p.trainid   "+
" where t.g11020 =    "+nd+
"   and p.a0184 = '"+a0184+"'";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("leaderGrid.dogridquery")
	@NoRequiredValidate         
	public int leaderGridQuery(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String a0184 = this.getPageElement("a0184param").getValue();
		String nd = this.getPageElement("ndparam").getValue();
		String sql ="select t.a1131 trainname, l.g11037, l.g11038, l.a1107c, l.a1108      "+
"  from train t      "+
"  join Train_Leader l      "+
"    on t.trainid = l.trainid      "+
" where t.g11020 =       "+nd+
"   and l.a0184 = '"+a0184+"'";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("elearningGrid.dogridquery")
	@NoRequiredValidate         
	public int elearningGridQuery(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String a0184 = this.getPageElement("a0184param").getValue();
		String nd = this.getPageElement("ndparam").getValue();
		String sql ="select g11042,a1107,a1111,a1108 from Train_Elearning where g11020="+nd+" and a0184='"+a0184+"'";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("scoreGrid.dogridquery")
	@NoRequiredValidate         
	public int scoreGrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String a0184 = this.getPageElement("a0184param").getValue();
		String nd = this.getPageElement("ndparam").getValue();
		String sql ="select g11039,g11040,g11041,a1108 from Train_Score where g11020="+nd+" and a0184='"+a0184+"'";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
}
