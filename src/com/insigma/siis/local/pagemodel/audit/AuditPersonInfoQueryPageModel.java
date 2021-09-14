package com.insigma.siis.local.pagemodel.audit;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.audit.PersonAuditInfo;

import java.sql.SQLException;

public class AuditPersonInfoQueryPageModel extends PageModel{
	/**
	 * 页面初始化
	 */
	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("changeType('');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 查询选人用人检查及列表数据加载
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("unitreGrid.dogridquery")
	public int policySetgrid(int start,int limit) throws RadowException{
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		 String sql = "/*"+Math.random()+"*/ select oid, " +
				 "    a0000, " +
				 "    a0101, " +
				 "    a0184, " +
				 "    a0192a, " +
				 "    audit_type, " +
				 "    audit_result, " +
				 "    audit_details, " +
				 "    audit_remark, " +
				 "    p_sortid, " +
				 "    jw_result, " +
				 "    zzb_gb_result, " +
				 "    zzb_jd_result, " +
				 "    xf_result, " +
				 "    fy_result, " +
				 "    jcy_result, " +
				 "    fgw_result, " +
				 "    ga_result, " +
				 "    rlsb_result, " +
				 "    zrzy_result, " +
				 "    sthj_result, " +
				 "    wjw_result, " +
				 "    yjgl_result, " +
				 "    sj_result, " +
				 "    scjg_result, " +
				 "    tj_result, " +
				 "    gh_result, " +
				 "    sw_result " +
				 "  from person_audit_info " +
				 " where 1=1 ";

		String queryname = this.getPageElement("queryname").getValue();
		String auditType = this.getPageElement("auditType").getValue();
		//String rpfacts = this.getPageElement("rpfacts").getValue();
		//String handlestartdate = this.getPageElement("handlestartdate").getValue();
		//String handleenddate = this.getPageElement("handleenddate").getValue();
		//String handleresult = this.getPageElement("handleresult").getValue();

		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		//姓名
		if(queryname !=null && !queryname.trim().equals("")){
			sb.append(" and ( a0101 like '%"+queryname+"%'  or a0184 like '%"+queryname+"%' ) ");
		}

		if(auditType !=null && !auditType.trim().equals("")){
			sb.append(" and audit_type= '"+auditType+"' ");
		}


		/*if ("2".equals(SelectUtil.getAuthority(user))) {
			sb.append(" and ( psnkey in (select a02.a0000 from a02 where a02.a0281='true' and a0255='1' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+user.getDept()+"')) or (psnkey is null and creatordept in (select roleid from smt_rolemanager where managerid='"+user.getDept()+"')))");
		} else if ("3".equals(SelectUtil.getAuthority(user))) {
			sb.append(" AND creator='").append(user.getId()).append("' ");
		} else {
			sb.append(" AND 1=1 ");
		}*/
		
		sb.append(" order by p_sortid desc ");
		System.out.println(sb.toString());
		this.pageQuery(sb.toString(),"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}

	


	@PageEvent("unitreGrid.rowdbclick")
	@GridDataRange
	public int unitreGridOnRowDbClick() throws RadowException, AppException, SQLException { // 打开窗口的实例
		String oid = this.getPageElement("unitreGrid").getValue("oid", this.getPageElement("unitreGrid").getCueRowIndex()).toString();
		//XryrInspect xr = (XryrInspect) HBUtil.getHBSession().get(XryrInspect.class, xrid);
		PersonAuditInfo api= (PersonAuditInfo) HBUtil.getHBSession().get(PersonAuditInfo.class,oid);
		if( api != null ) {
			this.getPageElement("lsid").setValue(oid);
			this.getExecuteSG().addExecuteCode("openLsData();");
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			throw new AppException("该记录不在系统中！");
		}
	}
	
}
