package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class AlterUserTypeWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ҳ�����ݳ�ʼ��
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, PrivilegeException {
		String param = this.getPageElement("subWinIdBussessId").getValue();
		UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(param);
		String usertype = null;
		if(user.getUsertype()==null||"".equals(user.getUsertype())){
			usertype = "δ����";
		}else{
			usertype = user.getUsertype();
		}
		this.getPageElement("userid").setValue(param);
		this.getExecuteSG().addExecuteCode("document.getElementById('username').innerHTML="+"'"+user.getName()+"'");
		this.getPageElement("usertype").setValue(usertype);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("savebut")
	@Transaction
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException{
		String userid = this.getPageElement("userid").getValue();
		String usertype = this.getPageElement("usertype").getValue();
		if(usertype.equals("δ����")){
			usertype="";
		}
		//�ж��ظ���������
		//SmtUser user = (SmtUser) HBUtil.getHBSession().createSQLQuery("select * from smt_user where userid='"+userid+"'").addEntity(SmtUser.class).uniqueResult();
		List<String> list_db  = HBUtil.getHBSession().createSQLQuery("select usertype from smt_user where userid='"+userid+"'").list();
		String usertype_db = null;
		if(list_db.get(0)==null){
			usertype_db = "";
		}
		if(usertype.equals(usertype_db)){
			this.getExecuteSG().addExecuteCode("window.realParent.clickTree()");
			this.closeCueWindowEX();
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�����û�����
		HBUtil.getHBSession().createSQLQuery("update smt_user set usertype = '"+usertype+"' where userid = '"+userid+"'").executeUpdate();
		//�û����ͻ����������ԭ���û������н�ɫȨ��
		HBUtil.getHBSession().createSQLQuery("delete from smt_act where userid = '"+userid+"'").executeUpdate();
		/*SceneVO scene;
		try {
			scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
			List<String> list = HBUtil.getHBSession().createSQLQuery("select roleid from smt_role").list();
			for(String roleid:list){
				//���smt_user��smt_actd������
				PrivilegeManager.getInstance().getIRoleControl().grantRemove(userid, scene.getSceneid(),roleid);
				
			}
			PrivilegeManager.getInstance().getIRoleControl().grantRemove(userid, scene.getSceneid(), "");
		} catch (PrivilegeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		//��Ȩ(1��2��3ʱֱ�Ӹ���ϵͳ����Ȩ��)
		if(usertype.equals("1")||usertype.equals("2")||usertype.equals("3")){
			try {
				SceneVO scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
				/*PrivilegeManager.getInstance().getIRoleControl().grant(userid, scene.getSceneid(), "402881f35e79fa19015e7a00b4190015", true);*/
				SmtAct smtact = new SmtAct();
				smtact.setObjectid(userid);
				smtact.setRoleid("402881f35e79fa19015e7a00b4190015");
				smtact.setUserid(userid);
				smtact.setSceneid(scene.getSceneid());
				smtact.setDispatchauth("0");
				smtact.setObjecttype("0");
				HBUtil.getHBSession().save(smtact);
			} catch (PrivilegeException e) {
				e.printStackTrace();
			}
		}
		this.getExecuteSG().addExecuteCode("window.realParent.clickTree()");
		this.setMainMessage("�����ɹ�");
		this.closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
}