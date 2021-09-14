package com.insigma.siis.local.pagemodel.repandrec.local;

public class ImpZzb3Control {
	private Thread thd1;
    private Thread thd2;
    private int status = 0;				//导入线程状态 -1为异常，0进行，1其中一个线程完成，2两个线程完成 
    private boolean isFirst = true;		//运行是否第一个线程，默认为是
    private int number2 = 15;			//导入表计数	
    private int number = 0;			//导入表计数	
    private String tableExt;
    public void setNumber2(int number2) {
		this.number2 = number2;
	}
	private int t_n = 0;				//导入数据量
    
    public String getTableExt() {
		return tableExt;
	}
	public void setTableExt(String tableExt) {
		this.tableExt = tableExt;
	}
	private String unzip;
    private String photo_file;
    
    
    //若程序运行正常，一个线程完成则给 status + 1，并返回结果
    public synchronized int getStatus(){
    	status++;
    	return status;
    }
    
	public String getPhoto_file() {
		return photo_file;
	}
	public void setPhoto_file(String photo_file) {
		this.photo_file = photo_file;
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
    
    public int getT_n(){
    	return t_n;
    }
    
    public synchronized void addT_n(int num){
    	t_n = t_n + num;
    }

	public synchronized boolean getisFirst(){
    	if(isFirst){
    		isFirst = false;
    		return true;
    	} else {
    		return false;
    	}
    }
    
	public synchronized int getNumber(){
    	return number++;
    }
	
    public synchronized int getNumber2(){
    	return number2--;
    }
    
    public void start(){
    	thd1.start();
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
	public String getUnzip() {
		return unzip;
	}
	public void setUnzip(String unzip) {
		this.unzip = unzip;
	}
}
