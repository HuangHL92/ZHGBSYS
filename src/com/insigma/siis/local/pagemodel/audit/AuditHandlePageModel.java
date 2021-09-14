package com.insigma.siis.local.pagemodel.audit;

import java.util.List;
import java.util.Map;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.audit.AuditBatch;
import com.insigma.siis.local.business.audit.AuditPerson;
import com.insigma.siis.local.business.audit.PersonAuditInfo;
//import com.insigma.siis.local.business.helperUtil.SelectUtil;
import com.insigma.siis.local.util.DateUtil;

public class AuditHandlePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("memberGrid.dogridquery");
		this.setNextEventName("MGrid.dogridquery");
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		if("001".equals(user.getDept())&&"0".equals(user.getDept())){
			this.getExecuteSG().addExecuteCode("$(#submitAudit).hide()");
		}
		
		return 0;
	}
	
	
	
	@PageEvent("memberGrid.dogridquery")
	public int memberGridQuery(int start, int limit) throws RadowException {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();

		StringBuffer sb = new StringBuffer("select a.*,(select username from smt_user where userid=a.CREATE_BY) createbyname from AUDIT_BATCH a where 1=1" );
		/*if ("2".equals(SelectUtil.getAuthority(user))) {
			
			sb.append(" and (  (audit_dept in (select roleid from smt_rolemanager where managerid='"+user.getDept()+"')))");
		} else if ("3".equals(SelectUtil.getAuthority(user))) {
			sb.append(" AND create_by='").append(user.getId()).append("' ");
		} else {
			sb.append(" AND 1=1 ");
		}*/
		System.out.println("batchsql:"+sb.toString());
		this.pageQuery(sb.toString(),"SQL",  start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	@PageEvent("MGrid.dogridquery")
	public int MGridQuery(int start, int limit) throws RadowException {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		StringBuffer sb = new StringBuffer("select a.*,(select wm_concat(a0101) from audit_person_family where p_oid=a.oid) family from AUDIT_PERSON a where audit_batch in (select oid from audit_batch)   " );
		
		/*if ("2".equals(SelectUtil.getAuthority(user))) {
			//sb.append(" AND inputorgid='"+user.getDept()+"'");
			sb.append(" and ( a0000 in (select a02.a0000 from a02 where a02.a0281='true' and a0255='1' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+user.getDept()+"')) or (a0000 is null and audit_dept in (select roleid from smt_rolemanager where managerid='"+user.getDept()+"')))");
		} else if ("3".equals(SelectUtil.getAuthority(user))) {
			sb.append(" AND audit_batch in (select oid from AUDIT_BATCH where create_by='"+user.getId()+"')'");
		} else {
			sb.append(" AND 1=1 ");
		}*/
		System.out.println("personsql:"+sb.toString());
		this.pageQuery(sb.toString(),"SQL",  start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("memberGrid.rowdbclick")
	@GridDataRange
	public int BatchGridRowDbClick() throws RadowException{  //打开窗口的实例
		Map<String,Object>	map = (Map<String,Object>)this.getPageElement("memberGrid").getValueList().get(this.getPageElement("memberGrid").getCueRowIndex());
		String id=(String)map.get("oid");
		
		this.getExecuteSG().addExecuteCode("openBatch('"+id+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("submitGJ")
	public int submitGJ(String ids) {
		
		String sql="update AUDIT_BATCH set BATCH_STATUS='1',DEPT_SUB_TIME=sysdate where oid in ("+ids+") and BATCH_STATUS='0'";
		HBSession sess=HBUtil.getHBSession();
		sess.createSQLQuery(sql).executeUpdate();
		this.setMainMessage("提交成功");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("submitAudit")
	public int submitAudit(String ids) {
		HBSession sess=HBUtil.getHBSession();
		
		
		String sql2="select * from audit_person  where audit_batch in (select oid from audit_batch where oid in ("+ids+") and BATCH_STATUS='1')";
		List<AuditPerson> list=sess.createSQLQuery(sql2).addEntity(AuditPerson.class).list();
		if(list==null||list.size()<1) {
			this.setMainMessage("无联审人员信息!");
			return EventRtnType.FAILD;
		}
		for(AuditPerson ap:list) {
			String sql3="select * from PERSON_AUDIT_INFO where a0000='"+ap.getA0000()+"' or (a0101='"+ap.getA0101()+"'   and a0184='"+ap.getA0184()+"')";
			List<PersonAuditInfo> list2=sess.createSQLQuery(sql3).addEntity(PersonAuditInfo.class).list();
			if(list2==null||list2.size()<1) {
				PersonAuditInfo pai=new PersonAuditInfo();
				pai.setOid(UUID.randomUUID().toString());
				pai.setA0000(ap.getA0000());
				pai.setA0101(ap.getA0101());
				pai.setA0184(ap.getA0184());
				pai.setA0192a(ap.getA0192a());
				AuditBatch ab=(AuditBatch) sess.get(AuditBatch.class, ap.getAuditBatch());
				pai.setAuditType(ab.getBatchType());
				sess.save(pai);
				
			}
			
		}
		sess.flush();
		
		String sql="update AUDIT_BATCH set BATCH_STATUS='2',AUDIT_SUB_TIME=sysdate where oid in ("+ids+") and BATCH_STATUS='1'";
		
		sess.createSQLQuery(sql).executeUpdate();
		
		this.setMainMessage("提交成功");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
