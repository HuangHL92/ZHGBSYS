package com.insigma.siis.local.business.test;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HList;
import com.insigma.odin.framework.commform.local.sys.BusinessLog;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.util.commform.BuildUtil;
import com.insigma.odin.framework.util.commform.BuildUtil.ButtonForToolBar;
import com.insigma.siis.local.comm.BusinessBSSupport;

public class UserSaveBS extends BusinessBSSupport{
	public void buildForm() throws AppException {
		form.newToolBar(new BuildUtil.Div() {
			public HList config() throws AppException {
				HList toolbar = BuildUtil.toolbarConfig();
				toolbar.add(BuildUtil.buttonForToolBarConfig("doSave"));
				toolbar.add(BuildUtil.buttonForToolBarConfig("doReset"));
				toolbar.add(BuildUtil.buttonForToolBarConfig("doCommImp").set(ButtonForToolBar.property, "daoru"));
				return toolbar;
			}
		});

		form.newTable(new BuildUtil.Div() {
			public HList config() throws AppException {
				div_1 = BuildUtil.tableConfig("div_1", "处理条件");
				div_1.add(BuildUtil.colsConfig("query", "psquery", "人员查询", "D", true));
				div_1.add(BuildUtil.colsConfig("text", "aac003", "姓名", "D"));
				div_1.add(BuildUtil.colsConfig("text", "eaz252", "转移项目值", "D"));
				return div_1;
			}
		});
	}
	public void doSave() throws AppException {
		// 保存日志
		BusinessLog.save(form, null, null);
	}
	public void doAudit()  {
		try{
			PrivilegeManager.getInstance(HBUtil.getHBSession().getSession()).reSetHashCodeByTabName("");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
