package com.insigma.siis.local.epsoft.task;

import java.util.Date;
import java.util.Timer;

/**
 * Job工具类
 * @author gezh
 *
 */
public class TimerJob {
	
	/**
	 * 立即执行一次性任务
	 * @param task
	 */
	public static void  addOnceJob(BaseTimerTask task){
		Timer timer = new Timer();
		timer.schedule(task,0);
	}
	
	/**
	 * 指定时间执行一次任务
	 * @param task
	 * @param date 指定执行的时间
	 */
	public static void addOnceTimerJob(BaseTimerTask task,Date date){
		Timer timer = new Timer();
		timer.schedule(task,date);
	}
	
	/**
	 * 指定时间开始，并周期性反复执行(如果遇到某次虚拟机回收垃圾等事物被延迟后1秒，后续所有任务都将比正常没有出现回收情况的执行延迟1秒)
	 * @param task
	 * @param firstTime 首次执行时间
	 * @param period 周期时间（毫秒）
	 */
	public static void repeatTimerJob(BaseTimerTask task, Date firstTime, long period){
		Timer timer = new Timer();
		timer.schedule(task,firstTime,period);
	}
	
	/**
	 * 指定时间开始，并周期性反复执行(如果遇到某次虚拟机回收垃圾等事物被延迟后1秒，后续所有任务都保持原有计算的时间执行)
	 * @param task
	 * @param firstTime 首次执行时间
	 * @param period 周期时间（毫秒）
	 */
	public static void repeatTimerJobAtFixedRate(BaseTimerTask task, Date firstTime, long period){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task,firstTime,period);
	}
	
}
