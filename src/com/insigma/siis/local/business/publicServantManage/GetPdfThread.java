package com.insigma.siis.local.business.publicServantManage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.lbs.leaf.util.DateUtil;

public class GetPdfThread extends Thread{
	
	 String a0000;		//��ԱID
	 boolean flag;		//�Ƿ��ӡ��������Ϣ
	 String printTime;	//��ӡʱ��
	 String zippath; 	//�ļ����Ŀ¼��·��
	 CountDownLatch countLatch;//������
	 String userid;
	 public  GetPdfThread(String a0000,boolean flag,String printTime,String zippath,String userid,CountDownLatch countLatch){
		 super(a0000);
		 this.a0000 = a0000;
		 this.flag = flag;
		 this.printTime = printTime;
		 this.zippath = zippath;
		 this.userid = userid;
		 this.countLatch = countLatch;
	 }
	
	@Override
	public void run() {
		CommonQueryBS.systemOut(this.getName()+"------------->��ȡ�����˵�PDF��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		
		String name = UUID.randomUUID().toString().replace("-", ""); //�ļ�����
		
		//1.������Ա��Ϣ��ƴ��lrm��Ϣ��
		String lrm = QueryPersonListBS.createLrmStr(a0000,flag,printTime,userid);
		
		try {
			
			//2.����lrm�ļ�����Ա�ı���Ϣ
			FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
			
			//3.����pic�ļ�����Ա��Ƭ��Ϣ
			@SuppressWarnings("unchecked")
			List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
			if(list15.size()>0){
				A57 a57 = list15.get(0);
				if(a57.getPhotodata()!=null && !a57.getPhotodata().equals("")){
					File f = new File(zippath + name+".pic");
					FileOutputStream fos = new FileOutputStream(f);
					InputStream is = a57.getPhotodata().getBinaryStream();// �������ݺ�ת��Ϊ��������
					byte[] data = new byte[1024];
					while (is.read(data) != -1) {
						fos.write(data);
					}
					fos.close();
					is.close();
				}
			}
			
			//4.����.lrm�ļ���.pic�ļ�������PDF�ļ�
			SevenZipUtil.zzbRmb(zippath, name);
			
		} catch (Exception e) {
			CommonQueryBS.systemOut("1�����ļ���������ϵ����Ա!�쳣��Ϣ��"+e.getMessage()); 
		}finally{
			countLatch.countDown();
		}
		
		CommonQueryBS.systemOut(this.getName()+"------------->��ȡ�����˵�PDF����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
	}
	
}