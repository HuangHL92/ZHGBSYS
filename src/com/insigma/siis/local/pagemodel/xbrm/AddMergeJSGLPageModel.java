package com.insigma.siis.local.pagemodel.xbrm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.axis.utils.StringUtils;
import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.entity.RecordBatchMerge;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.utils.CommonQueryBS;

public class AddMergeJSGLPageModel extends PageModel {
	
	
	/**
	 * ���κϲ�����
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save(String id) throws RadowException{
		RecordBatchMerge rbm = new RecordBatchMerge();
		String rbIds=this.getPageElement("subWinIdBussessId").getValue();//�ϲ���rbId
		HBSession sess = HBUtil.getHBSession();
		try {
			PMPropertyCopyUtil.copyElementsValueToObj(rbm, this);
			String rbmidnew=UUID.randomUUID().toString();
			rbm.setRbmId(rbmidnew);
			rbm.setRbmUserid(SysManagerUtils.getUserId());
			rbm.setRbmSysno(BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004"))));
			sess.beginTransaction();
			sess.save(rbm);
			//����rbmId
			String rbidssql="";
			if(!StringUtils.isEmpty(rbIds)){
				rbIds=rbIds.substring(0, rbIds.length()-1);
				rbidssql=rbIds.replaceAll(",", "','");
			}
			String updateRB="update record_batch set RBM_STATUS='2',RBM_ID='"+rbmidnew+"' where rb_id in('"+rbidssql+"')";
			sess.createSQLQuery(updateRB).executeUpdate();
			sess.getTransaction().commit();
			this.getExecuteSG().addExecuteCode("window.realParent.f5rightgird();window.close();");//ˢ�¸�ҳ��grid
			this.setMainMessage("�ϲ��ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			sess.getTransaction().rollback();
			this.setMainMessage("�ϲ�ʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String rb_id = this.getPageElement("rbId").getValue();
		HBSession sess = HBUtil.getHBSession();
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		CommonQueryBS cq=new CommonQueryBS();
		String sql="select smt_usergroup.usergroupname,smt_user.loginname from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
		
		try {
			HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
			String usergroupname=(String)mapBySQL.get("usergroupname");
			String loginname=(String)mapBySQL.get("loginname");
			this.getPageElement("rbmApplicant").setValue(loginname);
			this.getPageElement("rbmOrg").setValue(usergroupname);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ��");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
