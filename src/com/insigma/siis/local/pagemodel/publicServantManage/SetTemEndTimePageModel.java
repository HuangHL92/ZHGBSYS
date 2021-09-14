package com.insigma.siis.local.pagemodel.publicServantManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.search.ListOutPutPageModel;

import sun.net.www.http.Hurryable;

public class SetTemEndTimePageModel extends PageModel{
	
	
	public SetTemEndTimePageModel(){
	}
	
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("endtime")
	public int endtime() throws RadowException {
		String tpid = this.request.getSession().getAttribute("templateid").toString();
		String time = this.getPageElement("jiezsj").getValue();
		if("".equals(time)){
			this.setMainMessage("请选择相应日期！");
		}else{
			try {
				SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
				boolean dateflag=true;
				try {
					Date date = format.parse(time);
				} catch (Exception e) {
					dateflag=false;
				}finally{
					System.out.println("日期是否满足要求"+dateflag);
				}
				if(dateflag == true){
				System.out.println("update listoutput set endtime = '"+time+"' where tpid in ("+ tpid.replace("|", "'").replace("@", ",") + ")");
				HBUtil.executeUpdate("update listoutput set endtime = '"+time+"' where tpid in ("+ tpid.replace("|", "'").replace("@", ",") + ")");
				} else {
					this.setMainMessage("日期格式不符合要求，请重新填写！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			} catch (AppException e) {
				this.setMainMessage("设置失败！");
				e.printStackTrace();
			}
			this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('endAgeTimeWin').close();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
