package com.insigma.siis.local.pagemodel.dataverify;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataOrgExpControl {
	private Thread thd1;
    private Thread thd2;
    private int status = 0;				//导出线程状态 -1为异常，0进行，1其中一个线程完成，2两个线程完成 
    private int number = 0;				//导出表计数	
    //private int number2 = 28;			//导出剩余表计数	
    private int number2 = 14;
    
    private String path;
    private String zippath;
    
    public DataOrgExpControl() {
		super();
	}
    public DataOrgExpControl(Thread thd1, Thread thd2) {
		super();
		this.thd1 = thd1;
		this.thd2 = thd2;
	}
    
    //若程序运行正常，一个线程完成则给 status + 1，并返回结果
    public synchronized int getStatus(){
    	status++;
    	CommonQueryBS.systemOut("status---------"+status);
    	return status;
    }
    
	//若一个线程异常，修改 status ，并结束另一个线程，
    public synchronized void errStatus(String thd){
    	status = -1;
    	if(thd.equals("2")){
    		stopThd1();
    	} else {
    		stopThd2();
    	}
    }
    
    public synchronized int getNewNumber(){
    	return number++;
    }
    
    public synchronized int getNumber2(){
    	return number2--;
    }
    
    public void start(){
    	thd1.start();
    	if(thd2!=null)
    		thd2.start();
    }
    
    public void stopThd1(){
    	try {
    		thd1.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void stopThd2(){
    	try {
    		thd2.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void setThd1(Thread thd1) {
		this.thd1 = thd1;
	}

	public void setThd2(Thread thd2) {
		this.thd2 = thd2;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getZippath() {
		return zippath;
	}
	public void setZippath(String zippath) {
		this.zippath = zippath;
	}
    
}
