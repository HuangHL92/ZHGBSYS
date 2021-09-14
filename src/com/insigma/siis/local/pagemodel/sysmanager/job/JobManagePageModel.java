package com.insigma.siis.local.pagemodel.sysmanager.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.entity.SjJob;
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

/**
 * ��ʱ�����JOB����
 * @author jinwei
 * @date 2011-5-6
 * <p>Description: ����ϵͳ</p>
 * <p>Company: �㽭���¶���������޹�˾</p>
 */
public class JobManagePageModel extends PageModel {
	
	@PageEvent("pauseOrRestartJob")
	@NoRequiredValidate
	@Transaction
	public int pauseOrRestartJob(String type)throws RadowException, AppException{
		String id = this.getPageElement("id").getValue();
		IJobManage jm = JobManageFactory.getInstance().getIJobManage();
		//jm.updateJObProgress("task1", "99");
		//jm.updateJobStatus("task1", "9");
		if(type.equals("1")){ //pause
			jm.pauseJobById(id);
			this.setMainMessage("��ͣJOB���гɹ���");
		}else if(type.equals("2")){
			jm.restartJobById(id);
			this.setMainMessage("����JOB���гɹ���");
		}
		this.setNextEventName("jobgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogriddelete")
	@Transaction
	@NoRequiredValidate
	public int dogriddelete(String id)throws RadowException, AppException{
		IJobManage jm = JobManageFactory.getInstance().getIJobManage();
		jm.deleteJobById(id);
		this.setNextEventName("jobgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}	

	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveTimeJob()throws RadowException, AppException{
		String name = this.getPageElement("name").getValue();
		String title = this.getPageElement("title").getValue();
		String startdate = this.getPageElement("startdate").getValue();
		String classname = this.getPageElement("classname").getValue();
		String param1 = this.getPageElement("param1").getValue();
		String description = this.getPageElement("description").getValue();
		String intervalType = this.getPageElement("intervalType").getValue();
		String intervalCount = this.getPageElement("intervalCount").getValue();
		String execount = this.getPageElement("execount").getValue();
		String expression = this.getPageElement("expression").getValue();
		String everydaytime = this.getPageElement("everydaytime").getValue();
		String weekDay = this.getPageElement("weekDay").getValue();
		String wdaytime = this.getPageElement("wdaytime").getValue();
		String lastDay = this.getPageElement("lastDay").getValue();
		String ldaytime = this.getPageElement("ldaytime").getValue();
		SjJob job = new SjJob();
		IJobManage jm = JobManageFactory.getInstance().getIJobManage();
		try{
			job.setName(name);
			job.setTitle(title);
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			job.setStartdate(f.parse(startdate));
			job.setClassname(classname);
			job.setParam1(param1);
			job.setDescription(description);
			if(execount!=null && !execount.equals("")){
				job.setCount(Long.parseLong(execount));
			}
			if(!"".equals(intervalType)){
				if(intervalType.equals("3")){ //����
					jm.fireNowJob(job);
				}else{
					if(intervalCount==null || intervalCount.equals("")){
						throw new RadowException("������ִ����������������Ҫ����������");
					}else{
						long count = Long.parseLong(intervalCount);
						if(count<1){
							throw new RadowException("������ִ����������������Ҫ���������ȣ��ұ������0��");
						}
						if(intervalType.equals("0")){
							jm.addJobByIntervalSeconds((int)count, job);
						}else if(intervalType.equals("1")){
							jm.addJobByIntervalMinutes((int)count, job);
						}else if(intervalType.equals("2")){
							jm.addJobByIntervalHours((int)count, job);
						}
					}
				}
			}else if(!"".equals(everydaytime)){
				isRightTime(everydaytime,"HH:mm:ss");
				jm.addEveryDaySomeTimeJob(everydaytime, job);
			}else if(!"".equals(weekDay)){
				isRightTime(wdaytime,"HH:mm:ss");
				jm.addWeekDaySomeTimeJob(weekDay, wdaytime, job);
			}else if(!"".equals(lastDay)){
				isRightTime(ldaytime,"HH:mm:ss");
				jm.addLastDaySomeTimeJob(lastDay, ldaytime, job);
			}else if(!"".equals(expression)){
				jm.addComplexJob(expression, job);
			}
		}catch(ParseException e){
			throw new RadowException("������Ŀ�ʼʱ���ʽ���ԣ��밴��Ĭ��ֵ��ʽ�ġ�",e);
		}
		this.setNextEventName("jobgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("jobgrid.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	public int queryJob(int start,int limit) throws RadowException{
		String sql = "select id,title,to_char(startdate,'yyyy-mm-dd hh24:mi:ss') startdate,status,name,classname,description from smt_sj_job order by startdate desc ";
		if(DBUtil.getDBType().equals(DBType.MYSQL))
			sql = "select id,title,startdate,status,name,classname,description from smt_sj_job order by startdate desc ";
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
			throw new AppException("�������ڸ�ʽ���ԣ���ȷ��Ӧ��Ϊ"+f.format(new Date()));
		}
	}
	
}
