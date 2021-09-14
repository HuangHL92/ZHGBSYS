package com.insigma.siis.local.pagemodel.publicServantManage;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;


public class ZDYtemPageModel extends PageModel{
	
	public static String temnames = "";
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String init(){
		String script = "";
		script += "<script type='text/javascript'>\n";
		script += "function pageinit(){\n";
		try {
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select t.tpid,t.tpname,t.tptype from ((SELECT t.tpid,t.tpname,t.tptype FROM listoutput t WHERE t.tptype='1' AND t.tpkind='1' GROUP BY t.tpid,t.tpname,t.tptype) UNION ALL (SELECT t.tpid,t.tpname,t.tptype FROM listoutput t,user_template ut WHERE t.TPID=ut.tpid AND ut.userid='"+SysManagerUtils.getUserId()+"' AND t.TPKind='1' GROUP BY t.tpid,t.tpname,t.tptype)) t order by t.tptype").executeQuery();
			while(res.next()){
				script += "document.getElementById('personinfo').options.add(new Option('"+res.getString(2)+"','"+res.getString(1)+"'));\n";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		script += "eval("+script+");";
		script +="}\n";
		script +="</script>";
		return script;
	}
}
