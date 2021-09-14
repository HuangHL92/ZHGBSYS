package com.insigma.siis.local.pagemodel.sysorg.org;


import java.util.Hashtable;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeValue;

public class ExportOrgColumnPageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	
	public ExportOrgColumnPageModel(){
		
	}
	
	//页面初始化之后执行
	@Override
	public int doInit() {
		this.getExecuteSG().addExecuteCode("checkNode();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sql="from CodeValue where codeType='EXPORT' order by inino";
		List<CodeValue> list = HBUtil.getHBSession().createQuery(sql).list();//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (CodeValue group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[{\"text\" :\"" + group.getCodeName()
							+ "\" ,\"id\" :\"" + group.getCodeValue()
							
							+ "\" ,\"leaf\" :" + "true"
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getCodeName()
							+ "\" ,\"id\" :\"" + group.getCodeValue()
							+ "\" ,\"leaf\" :" + "true"
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group.getCodeName()
							+ "\" ,\"id\" :\"" + group.getCodeValue()
							+ "\" ,\"leaf\" :" + "true"
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + group.getCodeName()
							+ "\" ,\"id\" :\"" + group.getCodeValue()
							+ "\" ,\"leaf\" :" + "true"
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("cancelBtn.onclick")
	public int cancel(String value) throws AppException, RadowException {
		this.closeCueWindow("exportOrgColumn");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("yesBtn.onclick")
	public int yes(String value) throws AppException, RadowException {
		this.closeCueWindow("exportOrgColumn");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
