package com.insigma.siis.local.pagemodel.audit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.audit.AuditBatch;
import com.insigma.siis.local.business.audit.AuditPerson;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class AddAuditBatchPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	
	@PageEvent("initX")
	public int initX() throws RadowException {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String id=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess=HBUtil.getHBSession();
		if(StringUtil.isEmpty(id)) {
			this.getPageElement("auditBatchNo").setValue(getNo());
			this.getPageElement("auditDept").setValue(user.getDept());
			String deptname=(String) sess.createSQLQuery("select usergroupname from  smt_usergroup where id='"+user.getDept()+"'").uniqueResult();
			this.getPageElement("auditDeptName").setValue(deptname);
			
		}else {
			
			AuditBatch ab=(AuditBatch) sess.get(AuditBatch.class, id);
			this.copyObjValueToElement(ab, this);
			String deptname=(String) sess.createSQLQuery("select usergroupname from  smt_usergroup where id='"+ab.getAuditDept()+"'").uniqueResult();
			this.getPageElement("auditDeptName").setValue(deptname);
			
		}
		this.setNextEventName("gzGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("gzGrid.dogridquery")
	public int BatchGridQuery(int start, int limit) throws RadowException {
		String id=this.getPageElement("subWinIdBussessId").getValue();

		StringBuffer sb = new StringBuffer(
				"select a.*,(select wm_concat(a0101) from audit_person_family where p_oid=a.oid) family from AUDIT_PERSON a  where audit_batch ='"+id+"'  " );
		
		System.out.println("personsql:"+sb.toString());
		this.pageQuery(sb.toString(),"SQL",  start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	@PageEvent("save.onclick")
	public int save() throws RadowException {
		String id=this.getPageElement("subWinIdBussessId").getValue();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		HBSession sess=HBUtil.getHBSession();
		Date time=new Date();
		AuditBatch ab=new AuditBatch();
		if(StringUtil.isEmpty(id)) {
			String uuid=UUID.randomUUID().toString();
			ab.setOid(uuid);
			ab.setCreateBy(user.getId());
			ab.setCreateTime(time);
			ab.setBatchStatus("0");
		}else {
			 ab=(AuditBatch) sess.get(AuditBatch.class, id);
		}
		this.copyElementsValueToObj(ab, this);
		
		sess.saveOrUpdate(ab);
		
		
		List<HashMap<String, Object>>	gridlist = this.getPageElement("gzGrid").getValueList();
		int i=1;
		List<String> oldpersonlist=sess.createSQLQuery("select oid from audit_person where audit_batch='"+ab.getOid()+"'").list();
		sess.createSQLQuery("delete from audit_person where audit_batch='"+ab.getOid()+"'").executeUpdate();
		
		for(HashMap<String, Object> gridmap:gridlist) {
			String personid=S(gridmap.get("oid"));
			AuditPerson ap=new AuditPerson();
			ap.setSortid(i);
			ap.setA0000(S(gridmap.get("a0000")));
			ap.setA0101(S(gridmap.get("a0101")));
			ap.setA0184(S(gridmap.get("a0184")));
			ap.setA0192a(S(gridmap.get("a0192a")));
			ap.setAuditBatch(ab.getOid());
			ap.setOid(personid);
			oldpersonlist.remove(personid);
			sess.save(ap);
			i++;
			
		}
		if(oldpersonlist.size()>0) {
			sess.createSQLQuery("delete from AUDIT_PERSON_FAMILY where p_oid  in (:ids) ").setParameterList("ids",oldpersonlist ).executeUpdate();
		}
		
		
		sess.flush();
		
		this.getExecuteSG().addExecuteCode("saveCallBack('保存成功')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String getNo() throws RadowException {
		
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		String sql="select lpad(max(substr(AUDIT_BATCH_NO,5,3)+1),3,'0') from AUDIT_BATCH where AUDIT_BATCH_NO like '%"+year+"'";
		HBSession sess=HBUtil.getHBSession();
		Object num= sess.createSQLQuery(sql).uniqueResult();
		if(num==null) {
			num=year+"001";
		}else {
			num=year+num;
		}
		return num.toString();
		
	}
	
	private String S(Object v){
		return v==null?"":v.toString();
	}
	
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws AppException, RadowException {

		StringBuffer sql = new StringBuffer();
		sql.append(" select a0000,a0101,a0184,a0192a from a01 ");
		sql.append(" where a0000 in ('-1'");
		listStr = listStr.substring(1, listStr.length()-1);
		List<String> list = Arrays.asList(listStr.split(","));
		for(String id:list){
			sql.append(",'"+id.trim()+"'");			
		}
		sql.append(") ");
		
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
		
		List<HashMap<String, Object>> newlist=new ArrayList<HashMap<String, Object>>();
		
		List<HashMap<String, Object>>	gridlist = this.getPageElement("gzGrid").getValueList();
		
		if(listCode.size()>0) {
			a:for(HashMap m : listCode){
				//uuid
				m.put("oid", UUID.randomUUID().toString());
				
				String a0000=S(m.get("a0000"));
				for(HashMap<String, Object> gridmap:gridlist) {
					if(a0000.equals(gridmap.get("a0000"))) {
						continue a;
					}
				}
				m.put("a0000", a0000);
				//姓名
				String a0101=S(m.get("a0101"));
				m.put("a0101", a0101);
				//性别
				String a0184=S(m.get("a0184"));
				m.put("a0104",a0184);
				//单位职务
				String a0192a=S(m.get("a0192a"));
				m.put("a0192a", a0192a);
				
				newlist.add(m);
			}
			JSONArray  json = JSONArray.fromObject(newlist);
			this.getExecuteSG().addExecuteCode("addColumnWithData("+json.toString()+")");

		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("checkSave")
	public int checkSave(String pid) {
		HBSession sess=HBUtil.getHBSession();
		AuditPerson ap=(AuditPerson) sess.get(AuditPerson.class, pid);
		if(ap==null) {
			this.setMainMessage("请先保存");
		}else {
			//System.out.println("打开家庭成员界面");
			this.getExecuteSG().addExecuteCode("openFamily('"+pid+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
