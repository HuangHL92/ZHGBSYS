package com.insigma.siis.local.business.modeldb;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.insigma.siis.local.business.entity.Sublibrariesmodel;


public class SynTask extends TimerTask {
	private Logger log = Logger.getLogger(SynTask.class);
	public static boolean running = true;
	private List<Sublibrariesmodel> taskList;
	
	public List<Sublibrariesmodel> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Sublibrariesmodel> taskList) {
		this.taskList = taskList;
	}
	
	
	public SynTask() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public SynTask(List<Sublibrariesmodel> taskList) {
		super();
		this.taskList = taskList;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 // task to run goes here 
        if(running){
        	log.info("任务开启");
        	ModeldbBS.dataSyn(taskList);
        }else{
        	log.info("任务暂停");
        }
	}

}
