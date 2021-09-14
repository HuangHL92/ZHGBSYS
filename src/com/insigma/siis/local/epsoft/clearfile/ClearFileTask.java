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
			//--- ɾ���ϴ���ʱĿ¼ ------------------------------
			String upPath = rootPath + "upload";			//��ȡ�ϴ���ʱ·��
			File upfile = new File(upPath);
			if(upfile.exists()){
				File[] files = upfile.listFiles();			//�����ϴ���ʱ·���µ����ļ�
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(!file.getName().equals("wlhxml")){
						Calendar cal = Calendar.getInstance();	//��ȡ�ļ��������޸�ʱ��
						Long time = file.lastModified();
						cal.setTimeInMillis(time);
						Date date = cal.getTime();
						if(DateUtil.getDaysBetween(date, DateUtil.getSysDate()) > 15){	//�ļ����ɳ���30��ɾ��
							PhotosUtil.removDireCmd(file.getAbsolutePath());			//ʹ������ɾ���ļ�
//							System.out.println("delete==========" + file.getAbsolutePath() +"==" +cal.getTime().toLocaleString());
						}
					}
				}
			}
			//--- ɾ��������ʱĿ¼ ------------------------------
			String zipPath = rootPath + "zipload";			//��ȡ������ʱĿ¼·��
			File zipfile = new File(zipPath);
			if(zipfile.exists()){
				File[] files = zipfile.listFiles();			//����������ʱĿ¼�µ����ļ�
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					Calendar cal = Calendar.getInstance();	//��ȡ�ļ��������޸�ʱ��
					Long time = file.lastModified();
					cal.setTimeInMillis(time);
					Date date = cal.getTime();
					if(DateUtil.getDaysBetween(date, DateUtil.getSysDate()) > 15){	//�ļ����ɳ���30��ɾ��
						PhotosUtil.removDireCmd(file.getAbsolutePath());			//ʹ������ɾ���ļ�
//						System.out.println("delete==========" + file.getAbsolutePath() +"==" +cal.getTime().toLocaleString());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
