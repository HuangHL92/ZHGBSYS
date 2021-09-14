package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.util.List;

import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataOrgRepControl  {
	private Thread thd1;
    private Thread thd2;
    private int status = 0;				//导出线程状态 -1为异常，0进行，1其中一个线程完成，2两个线程完成 
    private int number = 0;				//导出表计数	
    private int number2 = 21;			//导出剩余表计数	
    
    private Long orgrows;
    private String sid;
    private String packageFile;
    private String path;
    private String a01new;
    private int count;
    private int packcount;
    private B01 b01;
    private ZwhzPackDefine info;
    private List<SFileDefine> sfile;
    
    public List<SFileDefine> getSfile() {
		return sfile;
	}
	public void setSfile(List<SFileDefine> sfile) {
		this.sfile = sfile;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPackageFile() {
		return packageFile;
	}
	public void setPackageFile(String packageFile) {
		this.packageFile = packageFile;
	}
	public Long getOrgrows() {
		return orgrows;
	}
	public void setOrgrows(Long orgrows) {
		this.orgrows = orgrows;
	}
	
    public ZwhzPackDefine getInfo() {
		return info;
	}
	public void setInfo(ZwhzPackDefine info) {
		this.info = info;
	}
	public B01 getB01() {
		return b01;
	}
	public void setB01(B01 b01) {
		this.b01 = b01;
	}
	public String getA01new() {
		return a01new;
	}
	public void setA01new(String a01new) {
		this.a01new = a01new;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPackcount() {
		return packcount;
	}
	public void setPackcount(int packcount) {
		this.packcount = packcount;
	}
	
    
    public DataOrgRepControl() {
		super();
	}
    public DataOrgRepControl(Thread thd1, Thread thd2) {
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
    	number++ ;
    	return number;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
