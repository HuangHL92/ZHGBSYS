package com.insigma.siis.local.pagemodel.dataverify;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.ImpProcess;


public class PhotoInfoPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			List<ImpProcess> list = sess.createQuery(" from ImpProcess where imprecordid='PhotoTrans'").list();
			if(list != null || list.size()> 1){
				this.getPageElement("processstatus").setValue(list.get(0).getProcessstatus().equals("2")?"完成":"处理中");
				this.getPageElement("starttime").setValue(list.get(0).getStarttime().toString());
				this.getPageElement("endtime").setValue(list.get(0).getEndtime()==null?"":list.get(0).getEndtime().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("closeBtn.onclick")
	public int closeBtn() throws RadowException {
		this.closeCueWindow("photoInfoWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
