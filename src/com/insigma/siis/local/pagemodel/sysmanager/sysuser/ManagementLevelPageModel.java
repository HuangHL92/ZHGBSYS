package com.insigma.siis.local.pagemodel.sysmanager.sysuser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ManagementLevelPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveUser")
	public int save(String ids2) throws RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String userid = this.getRadow_parent_data();
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			e.printStackTrace();
		}
		String rate = "";
		String empid = "";
//		Set<String> result = new HashSet<String>();
		Set<String> llset = new HashSet<String>();
		Set<String> whset = new HashSet<String>();
//		String ids2 = this.getPageElement("ryIds").getValue();
		String[] a0165s = ids2.substring(0,ids2.length()-1).split(",");
		for (int i = 0; i < a0165s.length; i++) {
			String value1 = a0165s[i].split(":")[0];//code type
			String hz = value1.substring(value1.length()-1); //end of value1 0:liulan 1:weihu			
			String value2 = a0165s[i].split(":")[1];//checked
			if("true".equals(value2)){
				if("0".equals(hz)){
					llset.add(value1.substring(0, value1.length()-1));
				}else{

					whset.add(value1.substring(0, value1.length()-1));
				}
			}
		}

		rate = StringUtils.join(llset.toArray(),",");
		empid = StringUtils.join(whset.toArray(),",");
		user.setRate(null == rate || "".equals(rate) ? "" : "'"+rate.replace(",", "','")+"'");
		user.setEmpid(null == empid || "".equals(empid) ? "" : "'"+empid.replace(",", "','")+"'");
		
		
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e) {
			
			e.printStackTrace();
		}
		//创建存放旧数据的对象
		UserVO user1 = new UserVO();
		//拷备数据
		PropertyUtils.copyProperties(user1, user);
		try {
			new LogUtil().createLog("65", "SMT_USER",user.getId(), user.getLoginname(), "", new Map2Temp().getLogInfo(user1,user));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMainMessage("保存成功");
		this.closeCueWindowByYes("win_pup");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
