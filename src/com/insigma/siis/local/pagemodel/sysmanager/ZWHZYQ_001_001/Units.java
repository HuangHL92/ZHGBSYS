package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001;

import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;

public class Units {
	//�õ���ǰ�û���usertype
	public static String getUserType(){
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		return user.getUsertype();
	}

}
