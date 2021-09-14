package com.insigma.siis.local.pagemodel.dataverify;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataOrgExpControl {
	private Thread thd1;
    private Thread thd2;
    private int status = 0;				//�����߳�״̬ -1Ϊ�쳣��0���У�1����һ���߳���ɣ�2�����߳���� 
    private int number = 0;				//���������	
    //private int number2 = 28;			//����ʣ������	
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
    
    //����������������һ���߳������� status + 1�������ؽ��
    public synchronized int getStatus(){
    	status++;
    	CommonQueryBS.systemOut("status---------"+status);
    	return status;
    }
    
	//��һ���߳��쳣���޸� status ����������һ���̣߳�
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
