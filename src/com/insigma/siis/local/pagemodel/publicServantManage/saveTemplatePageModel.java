package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.search.ListOutPutPageModel;

import sun.net.www.http.Hurryable;

public class saveTemplatePageModel extends PageModel{
	
	
	public saveTemplatePageModel(){
	}
	
	@Override
	public int doInit() throws RadowException {
		this.getPageElement("opentype").setValue((this.request.getSession().getAttribute("opentype")).toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("rename")
	public int rename() throws RadowException {
		String tpid = this.getRadow_parent_data();
		String newname = this.getPageElement("templateName").getValue();
		String zdytpname = (String) request.getSession().getAttribute("zdytpname");
		String houzhui ="";
		if(zdytpname.contains("【") && zdytpname.contains("】")){
			String name = zdytpname.replace("|", "");
			int indexOf = name.indexOf("【");
			int indexOf2 = name.indexOf("】");
			houzhui = name.substring((indexOf),(indexOf2+1));
			request.getSession().removeValue("zdytpname");
		}
		try {
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput t where t.tpname='"+newname+"' group by t.tpid) a").executeQuery();
			while(res.next()){
				int count = Integer.parseInt(res.getString(1));
				if(count>0){
					this.setMainMessage("模板名重复！请更改命名！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			HBUtil.executeUpdate("update listoutput set tpname = '"+newname+houzhui+"' where tpid = '"+tpid+"'");
			//---------------------------更新表powergx 因为里面存有共享的模板的名称-------------------
			String sql3 ="select username from smt_user where userid = '"+SysManagerUtils.getUserId()+"'";
			ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql3);
			re.next();
			String name0 = re.getString(1);//用户姓名
			String tpnamea = newname+"《"+name0+"》";
			String sql = "update powergx set tpname = '"+ tpnamea +"' where modelid = '"+ tpid +"' and userid = '"+SysManagerUtils.getUserId()+"'";
			HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);
			//----------------------------------------------
		} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage("命名失败！");
			return EventRtnType.FAILD;
		} catch (SQLException e) {
			e.printStackTrace();
			this.setMainMessage("命名失败！");
			return EventRtnType.FAILD;
		}
		this.closeCueWindow("renameWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
