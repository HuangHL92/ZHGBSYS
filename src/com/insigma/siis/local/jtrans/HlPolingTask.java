package com.insigma.siis.local.jtrans;

import java.util.List;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.epsoft.task.BaseTimerTask;
/**
 * 轮询下载上级下发目录文件TimerTask
 * @author gezh
 */
public class HlPolingTask extends BaseTimerTask{
	
	@Override
	public synchronized void perform() {
		try{
			HBSession sess = HBUtil.getHBSession();
			List<TransConfig> jfccs = sess.createQuery("from TransConfig t where t.type='0' and t.status='1'").list();
			ZwhzFtpClient.hlPoling(jfccs);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
