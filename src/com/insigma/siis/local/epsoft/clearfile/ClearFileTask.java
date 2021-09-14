package com.insigma.siis.local.epsoft.clearfile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.task.BaseTimerTask;

public class ClearFileTask extends BaseTimerTask {

	@Override
	public void perform() {
		String rootPath = AppConfig.HZB_PATH+"\\temp\\"; 
		try {
			//--- 删除上传临时目录 ------------------------------
			String upPath = rootPath + "upload";			//获取上传临时路径
			File upfile = new File(upPath);
			if(upfile.exists()){
				File[] files = upfile.listFiles();			//遍历上传临时路径下的子文件
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(!file.getName().equals("wlhxml")){
						Calendar cal = Calendar.getInstance();	//获取文件的最后的修改时间
						Long time = file.lastModified();
						cal.setTimeInMillis(time);
						Date date = cal.getTime();
						if(DateUtil.getDaysBetween(date, DateUtil.getSysDate()) > 15){	//文件生成超过30天删除
							PhotosUtil.removDireCmd(file.getAbsolutePath());			//使用命令删除文件
//							System.out.println("delete==========" + file.getAbsolutePath() +"==" +cal.getTime().toLocaleString());
						}
					}
				}
			}
			//--- 删除生成临时目录 ------------------------------
			String zipPath = rootPath + "zipload";			//获取生成临时目录路径
			File zipfile = new File(zipPath);
			if(zipfile.exists()){
				File[] files = zipfile.listFiles();			//遍历生成临时目录下的子文件
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					Calendar cal = Calendar.getInstance();	//获取文件的最后的修改时间
					Long time = file.lastModified();
					cal.setTimeInMillis(time);
					Date date = cal.getTime();
					if(DateUtil.getDaysBetween(date, DateUtil.getSysDate()) > 15){	//文件生成超过30天删除
						PhotosUtil.removDireCmd(file.getAbsolutePath());			//使用命令删除文件
//						System.out.println("delete==========" + file.getAbsolutePath() +"==" +cal.getTime().toLocaleString());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
