package com.insigma.siis.local.pagemodel.repandrec.local;

public class ImpZzb3Control {
	private Thread thd1;
    private Thread thd2;
    private int status = 0;				//�����߳�״̬ -1Ϊ�쳣��0���У�1����һ���߳���ɣ�2�����߳���� 
    private boolean isFirst = true;		//�����Ƿ��һ���̣߳�Ĭ��Ϊ��
    private int number2 = 15;			//��������	
    private int number = 0;			//��������	
    private String tableExt;
    public void setNumber2(int number2) {
		this.number2 = number2;
	}
	private int t_n = 0;				//����������
    
    public String getTableExt() {
		return tableExt;
	}
	public void setTableExt(String tableExt) {
		this.tableExt = tableExt;
	}
	private String unzip;
    private String photo_file;
    
    
    //����������������һ���߳������� status + 1�������ؽ��
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
	//��һ���߳��쳣���޸� status ����������һ���̣߳�
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
