package com.insigma.siis.local.jtrans;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.task.TimerJob;

/**
 * ��ѯ�����ϼ��·��ļ�Job
 * @author gezh
 *
 */
public class HlPolingJob {
	
	/**
	 * ��ʼ��Job
	 * @throws Exception 
	 */
	public static void initJob() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat cudate  = new SimpleDateFormat("yyyy-MM-dd");
		Date firstTime = sdf.parse(cudate.format(new Date())+" "+AppConfig.HLPOLING_FIRSTTIME);
		HlPolingTask task = new HlPolingTask();
		TimerJob.repeatTimerJobAtFixedRate(task, firstTime, AppConfig.HLPOLING_PERIOD);
	}
	
	public static void main(String[] args) throws Exception {
		initJob();
	}
}
