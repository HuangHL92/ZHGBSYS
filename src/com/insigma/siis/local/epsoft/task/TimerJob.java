package com.insigma.siis.local.epsoft.task;

import java.util.Date;
import java.util.Timer;

/**
 * Job������
 * @author gezh
 *
 */
public class TimerJob {
	
	/**
	 * ����ִ��һ��������
	 * @param task
	 */
	public static void  addOnceJob(BaseTimerTask task){
		Timer timer = new Timer();
		timer.schedule(task,0);
	}
	
	/**
	 * ָ��ʱ��ִ��һ������
	 * @param task
	 * @param date ָ��ִ�е�ʱ��
	 */
	public static void addOnceTimerJob(BaseTimerTask task,Date date){
		Timer timer = new Timer();
		timer.schedule(task,date);
	}
	
	/**
	 * ָ��ʱ�俪ʼ���������Է���ִ��(�������ĳ��������������������ﱻ�ӳٺ�1�룬�����������񶼽�������û�г��ֻ��������ִ���ӳ�1��)
	 * @param task
	 * @param firstTime �״�ִ��ʱ��
	 * @param period ����ʱ�䣨���룩
	 */
	public static void repeatTimerJob(BaseTimerTask task, Date firstTime, long period){
		Timer timer = new Timer();
		timer.schedule(task,firstTime,period);
	}
	
	/**
	 * ָ��ʱ�俪ʼ���������Է���ִ��(�������ĳ��������������������ﱻ�ӳٺ�1�룬�����������񶼱���ԭ�м����ʱ��ִ��)
	 * @param task
	 * @param firstTime �״�ִ��ʱ��
	 * @param period ����ʱ�䣨���룩
	 */
	public static void repeatTimerJobAtFixedRate(BaseTimerTask task, Date firstTime, long period){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task,firstTime,period);
	}
	
}
