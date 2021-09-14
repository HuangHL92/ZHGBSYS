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
	
	 String a0000;		//人员ID
	 boolean flag;		//是否打印拟任免信息
	 String printTime;	//打印时间
	 String zippath; 	//文件存放目录的路径
	 CountDownLatch countLatch;//计数器
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
		CommonQueryBS.systemOut(this.getName()+"------------->获取单个人的PDF开始:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		
		String name = UUID.randomUUID().toString().replace("-", ""); //文件名称
		
		//1.根据人员信息，拼接lrm信息串
		String lrm = QueryPersonListBS.createLrmStr(a0000,flag,printTime,userid);
		
		try {
			
			//2.生成lrm文件，人员文本信息
			FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
			
			//3.生成pic文件，人员照片信息
			@SuppressWarnings("unchecked")
			List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
			if(list15.size()>0){
				A57 a57 = list15.get(0);
				if(a57.getPhotodata()!=null && !a57.getPhotodata().equals("")){
					File f = new File(zippath + name+".pic");
					FileOutputStream fos = new FileOutputStream(f);
					InputStream is = a57.getPhotodata().getBinaryStream();// 读出数据后转换为二进制流
					byte[] data = new byte[1024];
					while (is.read(data) != -1) {
						fos.write(data);
					}
					fos.close();
					is.close();
				}
			}
			
			//4.根据.lrm文件和.pic文件，生成PDF文件
			SevenZipUtil.zzbRmb(zippath, name);
			
		} catch (Exception e) {
			CommonQueryBS.systemOut("1生成文件错误，请联系管理员!异常信息："+e.getMessage()); 
		}finally{
			countLatch.countDown();
		}
		
		CommonQueryBS.systemOut(this.getName()+"------------->获取单个人的PDF结束:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
	}
	
}