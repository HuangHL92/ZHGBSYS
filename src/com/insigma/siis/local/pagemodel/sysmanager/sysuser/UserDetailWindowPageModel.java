package com.insigma.siis.local.pagemodel.sysmanager.sysuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PmHListUtil;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class UserDetailWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String data= this.getRadow_parent_data();
		CommonQueryBS.systemOut(data);
		String userid=data;
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		if(user.getName() != null){
			this.getPageElement("username").setValue(user.getName());
		}
		this.getPageElement("usertype").setValue(user.getUsertype());
		this.getPageElement("useful").setValue(user.getStatus());
		this.getPageElement("loginname").setValue(user.getLoginname());
		this.getPageElement("work").setValue(user.getWork());
		this.getPageElement("mobile").setValue(user.getMobile());
		this.getPageElement("tel").setValue(user.getTel());
		this.getPageElement("email").setValue(user.getEmail());
		if(user.getOtherinfo()!=null){
			String hql = "from B01 t where t.b0111='"+user.getOtherinfo()+"'";
			List list = sess.createQuery(hql).list();
			if(list.size()!=0){
				B01 b = (B01)list.get(0);
				this.getPageElement("ssjg").setValue(b.getB0101());
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
