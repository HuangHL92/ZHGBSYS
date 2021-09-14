package com.insigma.siis.local.business.desktop;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.entity.SmtDeskTopItem;

public class SmtBS extends BSSupport
{
	@SuppressWarnings("unchecked")
	public boolean saveOrUpdate(SmtDeskTopItem sd)throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
			if(sd.getId()==null || sd.getId().equals(""))
			{
				List<Object> list = sess.createQuery("from SmtDeskTopItem s where s.name=:appid")
					.setParameter("appid", sd.getName())
					.list();
				if(list.size()>0){
					throw new RadowException("该标题英文简称或名称已经存在，不允许再添加！请重新操作!");
				}
				sess.save(sd);
			}else{
				sess.update(sd);
			}
		return true;
	}


}
