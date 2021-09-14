package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.util.UUID;

import org.hibernate.Transaction;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.InterfaceUser;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;

public class InterfaceUserCuePageModel extends PageModel{

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	
	@Override
	public int doInit() throws RadowException {
		String id = this.getRadow_parent_data();
		if(!"NEW".equals(id)) {//�������޸�,��ҳ�渽�ϳ�ֵ
			InterfaceUser user = (InterfaceUser) bs7.getSession().createQuery("from InterfaceUser where userId=:userId")
					             .setParameter("userId", id).uniqueResult();
			this.getPageElement("userName").setValue(user.getUserName());
			this.getPageElement("password").setValue(user.getPassword());
			this.getPageElement("realName").setValue(user.getRealName());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	public int save() throws RadowException {
		String id = this.getRadow_parent_data();//�Ӹ�ҳ�洫����һ����־id�����NEW������½�
		InterfaceUser user = new InterfaceUser();
		if("NEW".equals(id)) {//�½��û�
			String userId = UUID.randomUUID().toString();
			String userName = this.getPageElement("userName").getValue();
			String password = this.getPageElement("password").getValue();
			String realName = this.getPageElement("realName").getValue();
			user.setUserId(userId);
			user.setUserName(userName);
			user.setPassword(password);
			user.setRealName(realName);
			Transaction t = bs7.getSession().getTransaction();
			t.begin();
			bs7.getSession().save(user);
			t.commit();
			//���½��û�׷�ӵ�users.lst�ļ���
			this.getExecuteSG().addExecuteCode("window.parent.refresh()");
			this.closeCueWindow("NewInterfaceUser");
		} else {//�޸��û�
			String userName = this.getPageElement("userName").getValue();
			String password = this.getPageElement("password").getValue();
			String realName = this.getPageElement("realName").getValue();
			user.setUserId(id);
			user.setUserName(userName);
			user.setPassword(password);
			user.setRealName(realName);
			Transaction t = bs7.getSession().getTransaction();
			t.begin();
			bs7.getSession().saveOrUpdate(user);
			t.commit();
			this.getExecuteSG().addExecuteCode("window.parent.refresh()");
			this.closeCueWindow("EditInterfaceUser");
		}
		bs7.refreshUsersLst();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("cancel.onclick")
	@NoRequiredValidate
	public int close() throws RadowException {
		String id = this.getRadow_parent_data();
		if("NEW".equals(id)) {
			this.closeCueWindow("NewInterfaceUser");
		} else {
			this.closeCueWindow("EditInterfaceUser");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
