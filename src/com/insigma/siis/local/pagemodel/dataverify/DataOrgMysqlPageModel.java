package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class DataOrgMysqlPageModel extends PageModel {

	@PageEvent("exp")
	public int expbtn(String id) throws RadowException{
		expbtn2(id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void expbtn2(String id) throws RadowException {
		
		try {
			
			CommonQueryBS.systemOut(DateUtil.getTime());
			
			//String searchDeptid = this.getPageElement("searchDeptBtn").getValue();//选择机构
				
			String mysqlZipPass = this.getPageElement("mysqlPass").getValue();
				
			String contact = this.getPageElement("linkpsn").getValue();
			String tel = this.getPageElement("linktel").getValue();
			String notes = this.getPageElement("remark").getValue();
			
				
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			//String time = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
			//name、B0101
			
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			KingbsconfigBS.saveImpDetailInit3(id);
			if(!"40288103556cc97701556d629135000f".equals(userVo.getId())){
				KingbsconfigBS.saveImpDetail("1" ,"4","失败："+"您没有此功能的权限，请联系系统管理员",id);
				return;
			} 
			
			ImpMysqlThread thr = new ImpMysqlThread(mysqlZipPass,contact,tel,notes,id,userVo,user);
			new Thread(thr,"MYSQL数据导出线程").start();
			
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail("1" ,"4","失败："+"导入失败",id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException{
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
