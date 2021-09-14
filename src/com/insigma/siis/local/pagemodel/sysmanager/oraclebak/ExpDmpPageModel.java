package com.insigma.siis.local.pagemodel.sysmanager.oraclebak;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class ExpDmpPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	 * 终止导出oracle数据库
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("stopExp.onclick")
	@Transaction
	@Synchronous(true)
	public int expdmp() throws RadowException {
		CommQuery cqbs=new CommQuery();
		try {
			String sql = "select saddr,sid,serial#,paddr,username,status from v$session where username = 'ZHGBSYS' and program='exp.exe' and module='exp.exe' ";
			List<HashMap<String, String>>  list = cqbs.getListBySQL2(sql);
			if(list.size()>0){
				HashMap<String, String> m = list.get(0);
				HBUtil.executeUpdate("alter system kill session '"+m.get("sid")+","+m.get("serial#")+"'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("执行失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	

}
