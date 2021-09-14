package com.insigma.siis.local.pagemodel.sysmanager.pjob;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.entity.SjPjobRunning;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.scheduler.pjob.manage.PJobManage;

/**
 * 定时任务或JOB监控管理
 * @author jinwei
 * <p>时间： 2013-5-17</p>
 * <p>Description: 核三系统</p>
 * <p>Company: 浙江网新恩普软件有限公司</p>
 */
public class PJobMonitorPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		String pjobName = this.getParameter("initParams");
		try {
			List<SjPjobRunning> data = PJobManage.getInstance().getPJobRunning(pjobName);
			if(data.size()>0){
				this.getExecuteSG().addExecuteCode("startProgress("+JSONObject.fromObject(PJobManage.getInstance().getSjPjobByName(pjobName))
						+","+JSONObject.fromObject(data.get(0))+");");
			}
		} catch (AppException e) {
			throw new RadowException(e.getMessage(),e);
		}
		this.setNextEventName("pjobRunGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("pjobRunGrid.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	public int queryJob(int start,int limit) throws RadowException{
		String pjobName = this.getPageElement("pjobName").getValue();
		String sql = "select r.id,to_char(r.startdate,'yyyy-mm-dd hh24:mi:ss') startdate,to_char(r.enddate,'yyyy-mm-dd hh24:mi:ss') enddate," +
				"r.status,r.loginname,u.username,r.changereason from smt_sj_pjob_running r,smt_sj_pjob p,smt_user u " +
				"where r.loginname=u.loginname and p.id = r.pjobid and p.name='"+pjobName+"' order by r.startdate desc";
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("cancelPJob")
	@Transaction
	public int cancelPJobRun(String pjobRunId) throws AppException, RadowException{
		PJobManage.getInstance().cacelPJobRun(pjobRunId, null);
		this.setMainMessage("取消成功！");
		this.getPageElement("pjobRunGrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("updateProgress")
	@AutoNoMask
	public int getPJobProgress() throws AppException, RadowException{
		String pjobName = this.getPageElement("pjobName").getValue();
		HashMap<String, Object> progressInfo = PJobManage.getInstance().getPJobProgress(pjobName);
		this.getExecuteSG().addExecuteCode("updateProgress("+progressInfo.get("progress")+",'"+progressInfo.get("timeMsg")+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
