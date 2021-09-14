package com.insigma.siis.local.pagemodel.sysmanager.pjob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.entity.SjJob;
import com.insigma.odin.framework.comm.entity.SjPjob;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.scheduler.manage.IJobManage;
import com.insigma.odin.framework.scheduler.manage.JobManageFactory;
import com.insigma.odin.framework.scheduler.pjob.manage.IPJobManage;
import com.insigma.odin.framework.scheduler.pjob.manage.PJobManage;

/**
 * 定时任务或JOB管理
 * @author jinwei
 * @date 2011-5-6
 * <p>Description: 核三系统</p>
 * <p>Company: 浙江网新恩普软件有限公司</p>
 */
public class PJobManagePageModel extends PageModel {
	
	@PageEvent("runPJob")
	@NoRequiredValidate
	@Transaction
	public int runPJob(String name)throws RadowException, AppException{
		String pjobName = name;
		String execParam = null;
		if(name.indexOf("@")>0){
			String temp[] = name.split("@");
			pjobName = temp[0];
			execParam = temp[1];
		}
		PJobManage.getInstance().startRunNowPJob(pjobName,execParam);
		this.setMainMessage("任务名为“"+name+"”的并发任务启动运行成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("monitorPJob")
	@NoRequiredValidate
	@Transaction
	public int monitorPJob(String name)throws RadowException, AppException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogriddelete")
	@Transaction
	@NoRequiredValidate
	public int dogriddelete(String name)throws RadowException, AppException{
		PJobManage.getInstance().deleteParallelJob(name);
		this.setNextEventName("jobgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}	

	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveTimeJob()throws RadowException, AppException{
//		String name = this.getPageElement("name").getValue();
//		String title = this.getPageElement("title").getValue();
		String pjobtype = this.getPageElement("pjobtype").getValue();
//		String jobcontent = this.getPageElement("jobcontent").getValue();
//		String pcount = this.getPageElement("pcount").getValue();
//		String param = this.getPageElement("param").getValue();
		
		String expression = this.getPageElement("expression").getValue();
		String everydaytime = this.getPageElement("everydaytime").getValue();
		String weekDay = this.getPageElement("weekDay").getValue();
		String wdaytime = this.getPageElement("wdaytime").getValue();
		String lastDay = this.getPageElement("lastDay").getValue();
		String ldaytime = this.getPageElement("ldaytime").getValue();
		
		SjPjob pjob = new SjPjob();
		this.copyElementsValueToObj(pjob, this);
		pjob.setJobtype(pjobtype);
		IPJobManage pjm = PJobManage.getInstance();
		if(!"".equals(everydaytime)){
			isRightTime(everydaytime,"HH:mm:ss");
			pjm.addEveryDaySomeTimeJob(everydaytime, pjob);
		}else if(!"".equals(weekDay)){
			isRightTime(wdaytime,"HH:mm:ss");
			pjm.addWeekDaySomeTimeJob(weekDay, wdaytime, pjob);
		}else if(!"".equals(lastDay)){
			isRightTime(ldaytime,"HH:mm:ss");
			pjm.addLastDaySomeTimeJob(lastDay, ldaytime, pjob);
		}else if(!"".equals(expression)){
			pjm.addComplexJob(expression, pjob);
		}else{
			pjm.addParallelJob(pjob);
		}
		this.setMainMessage("增加并发任务成功！");
		this.reloadPageByYes();
		this.setNextEventName("jobgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("jobgrid.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	public int queryJob(int start,int limit) throws RadowException{
		String sql = "select id,title,name,jobtype,jobcontent,pcount,status,param,to_char(createdate,'yyyy-mm-dd') createdate from smt_sj_pjob order by createdate desc ";
		if(DBUtil.getDBType().equals(DBType.MYSQL))
			sql = "select id,title,name,jobtype,jobcontent,pcount,status,param,createdate from smt_sj_pjob order by createdate desc ";
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("jobgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void isRightTime(String timeStr,String formatStr) throws AppException{
		SimpleDateFormat f = new SimpleDateFormat(formatStr);
		try {
			f.parse(timeStr);
		} catch (ParseException e) {
			throw new AppException("您的日期格式不对，正确的应该为"+f.format(new Date()));
		}
	}
	
}
