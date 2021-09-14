package com.insigma.siis.local.epsoft.clearfile;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.task.TimerJob;
import com.insigma.siis.local.jtrans.HlPolingTask;

public class ClearFileJob {
	/**
	 * ≥ı ºªØJob
	 * @throws Exception 
	 */
	public static void initJob() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat cudate  = new SimpleDateFormat("yyyy-MM-dd");
		Date firstTime = sdf.parse(cudate.format(new Date())+" "+AppConfig.HLPOLING_FIRSTTIME);
		ClearFileTask task = new ClearFileTask();
		TimerJob.repeatTimerJobAtFixedRate(task, firstTime, 7 * 24 * 60 * 60 * 1000);
	}
}
