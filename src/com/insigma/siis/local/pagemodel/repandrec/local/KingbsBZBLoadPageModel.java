package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class KingbsBZBLoadPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.getPageElement("imprecordid").setValue(UUID.randomUUID().toString().replace("-", ""));
//		this.setNextEventName("zzbsearch");
	 	return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("fabsolutepath.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException{
//		this.openWindow("winFile", "pages.repandrec.local.KingbsBZBDept");
		this.getExecuteSG().addExecuteCode("$h.openWin('winFile11','pages.repandrec.local.KingbsBZBDept','选择导入数据文件',750,465,'qq','"+request.getContextPath()+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("impbtn.onclick")
	public int Imp(String name) throws RadowException{
		String id = this.getPageElement("imprecordid").getValue();
		String bzbDeptid = this.getPageElement("bzbDeptid").getValue();
		String deptid = this.getPageElement("searchDeptid").getValue();
		String adress = this.getPageElement("adress").getValue();//照片路径
		if(!new File(adress).exists()){
			this.setMainMessage("照片地址不存在，请检查！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//非现职人员
		String fxz = this.getPageElement("fxz").getValue();
		if("1".equals(fxz)){
			fxz = "true";
		}
		if("0".equals(fxz)){
			fxz = "false";
		}
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit4(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			ImpBZBThread thr = new ImpBZBThread(bzbDeptid, deptid, id, user, userVo, fxz, adress);   //启动线程导入
			new Thread(thr).start();
//			this.getExecuteSG().addExecuteCode("parent.doOpenPupWin('/radowAction.do?method=doEvent&pageModel=pages.repandrec.local.KingbsWinfresh&initParams="+id+"','导入数据进度',600, 400, null);");
			this.getExecuteSG().addExecuteCode("realParent.$h.openWinMin('wi1122','pages.repandrec.local.KingbsWinfresh&initParams="+id+"','导入进度',500,150,'qq','"+request.getContextPath()+"');");
			this.closeCueWindow("win1121");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}
}
