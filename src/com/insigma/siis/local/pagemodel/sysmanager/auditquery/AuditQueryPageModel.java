package com.insigma.siis.local.pagemodel.sysmanager.auditquery;


import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class AuditQueryPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("AuditListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("AuditListGrid.dogridquery")
	public int Check(int start,int limit) throws RadowException{
		String sql = "select oa.aaa027,oa.auendflag,oah.cueauflag,oah.cueaudesc,oah.auopseno,sf.title,su.username,oa.opseno,oah.cueaulevel,to_char(oah.cueaudate,'yyyy-mm-dd hh24:mi:ss') aae036,sf.auflag" +
				" from OpauditHistory oah,Opaudit oa,Smt_Function sf,Smt_User su,SBDS_USERLOG sl" +
				" where oa.opseno=oah.opseno and oah.cueauuser=su.loginname and oa.opseno=sl.opseno and sl.functionid=sf.functionid ";
		if(this.getPageElement("querydate").getValue()!=null && !this.getPageElement("querydate").getValue().trim().equals("")){
			sql=sql+"  and oah.cueaudate >=to_date('"+this.getPageElement("querydate").getValue()+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and oah.cueaudate<to_date('"+this.getPageElement("querydate").getValue()+" 00:00:00','yyyy-mm-dd hh24:mi:ss')+1";
		}
		if(this.getPageElement("functiontitle").getValue()!=null && !this.getPageElement("functiontitle").getValue().trim().equals("")){
			sql=sql+" and sf.title='"+this.getPageElement("functiontitle").getValue()+"'";
		}
		sql=sql+"order by oah.opseno desc,oah.cueaulevel asc";
		this.pageQuery(sql,"SQL", -1, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("querydate.onchange")
	public int querydateOnChange() throws RadowException{
		this.setNextEventName("AuditListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("functiontitle.onchange")
	public int functiontitleOnChange() throws RadowException{
		this.setNextEventName("AuditListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
