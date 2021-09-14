package com.insigma.siis.local.pagemodel.xbrm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.utils.CommonQueryBS;

public class AddJSGLPageModel extends PageModel {
	
	
	/**
	 * 批次信息修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save(String id) throws RadowException{
		String rb_id = this.getPageElement("rbId").getValue();
		RecordBatch rb = new RecordBatch();
		
		try {
			HBSession sess = HBUtil.getHBSession();
			if(rb_id==null||"".equals(rb_id)){//新增
				PMPropertyCopyUtil.copyElementsValueToObj(rb, this);
				String rbidnew=UUID.randomUUID().toString();
				rb.setRbstatus("0");
				rb.setRbId(rbidnew);
				rb.setRbUserid(SysManagerUtils.getUserId());
				rb.setRbSysno(BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004"))));
				sess.save(rb);
				sess.flush();
				
				
				//初始化类型
				//HBUtil.executeUpdate("insert into DEPLOY_CLASSIFY(dc001, rb_id, dc003, dc004 ) select sys_guid(), '"+rb.getRbId()+"', dc003, dc004 from DEPLOY_CLASSIFY_init");
				String dc001=UUID.randomUUID().toString().replaceAll("-", "");
				String rbdeplytype=rb.getRbdeplytype();
				HBUtil.executeUpdate("insert into DEPLOY_CLASSIFY(DC001,RB_ID,DC003,DC004,DC005,DC006) VALUES(?,?,(select CODE_NAME from code_value where CODE_TYPE='DPLX' and CODE_VALUE=?),deploy_classify_dc004.nextval,'1',null)",
						new Object[]{dc001,rbidnew,rbdeplytype});
			
				
				
				this.getPageElement("rbId").setValue(rb.getRbId());
				this.getPageElement("rbUserid").setValue(rb.getRbUserid());
				this.getExecuteSG().addExecuteCode("saveCallBack('新增');");
				//this.setMainMessage("新增成功");
			}else{
				rb = (RecordBatch)sess.get(RecordBatch.class, rb_id);
				PMPropertyCopyUtil.copyElementsValueToObj(rb, this);
				String rbdeplytype=rb.getRbdeplytype();
				List list = sess.createSQLQuery("select CODE_NAME from code_value where CODE_TYPE='DPLX' and CODE_VALUE='"+rbdeplytype+"'").list();
				if(list.size()>0){
					String dc003=list.get(0).toString();
					HBUtil.executeUpdate("update DEPLOY_CLASSIFY set DC003=? where RB_ID=?", new Object[]{dc003,rb_id});
				}
				
				sess.update(rb);
				sess.flush();
				this.getExecuteSG().addExecuteCode("saveCallBack('修改');");
				//this.setMainMessage("修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
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
			this.getPageElement("rbApplicant").setValue(loginname);
			this.getPageElement("rbOrg").setValue(usergroupname);
			
			if(rb_id!=null&&!"".equals(rb_id)){
				RecordBatch rb = (RecordBatch)sess.get(RecordBatch.class, rb_id);
				PMPropertyCopyUtil.copyObjValueToElement(rb, this);
				this.getExecuteSG().addExecuteCode("initselect();");
			} else {
				this.getPageElement("rbNo").setValue(DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmm"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
