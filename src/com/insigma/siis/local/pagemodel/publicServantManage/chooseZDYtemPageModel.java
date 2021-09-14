package com.insigma.siis.local.pagemodel.publicServantManage;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;


public class chooseZDYtemPageModel extends PageModel{
	
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
//			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select t.tpid,t.tpname from ((SELECT t.tpid,t.tpname,t.tptype FROM listoutput t WHERE t.tptype='2' AND t.tpkind='3' ) UNION ALL (SELECT t.tpid,t.tpname,t.tptype FROM listoutput t,user_template ut WHERE t.TPID=ut.tpid AND ut.userid='"+SysManagerUtils.getUserId()+"' AND t.TPKind='3' )) t GROUP BY t.tpid,t.tpname order by t.tpname").executeQuery();
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select t.tpid,t.tpname from ((SELECT t.tpid,t.tpname,t.tptype FROM listoutput t WHERE t.tptype='2' AND t.tpkind='3' ) UNION ALL (SELECT t.tpid,t.tpname,t.tptype FROM listoutput t,user_template ut,powergx gx WHERE t.TPID=ut.tpid and t.tpid = gx.modelid AND ut.userid='"+SysManagerUtils.getUserId()+"' AND t.TPKind='3' and ut.userid = gx.userid)) t GROUP BY t.tpid,t.tpname order by t.tpname").executeQuery();
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
