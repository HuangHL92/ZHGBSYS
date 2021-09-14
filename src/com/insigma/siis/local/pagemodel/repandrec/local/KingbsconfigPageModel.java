package com.insigma.siis.local.pagemodel.repandrec.local;

import java.util.ArrayList;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class KingbsconfigPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		Dataexchangeconf conf = KingbsconfigBS.getConfig();
		if(conf!=null){
//			conf.setKingbspwd("");
			this.autoFillPage(conf, false);
			//PMPropertyCopyUtil.copyObjValueToElement(conf, this);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��Ϣ�����ʼ��
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	public int save()throws RadowException, AppException{
		Dataexchangeconf conf = new Dataexchangeconf();
		this.copyElementsValueToObj(conf, this);
		try {
//			String pwd = conf.getKingbspwd();
//			String zzbpath = this.getPageElement("zzbthreepath").getValue();
//			String kingrest = this.getPageElement("kingrestorepath").getValue();
//			String pwd2 = this.getPageElement("kingbspwdtwo").getValue();
//			if(!pwd2.equals(pwd)){
//				this.setMainMessage("�����������벻��ͬ����ȷ�ϣ�");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			conf.setZzbthreepath(zzbpath.replace("\\", "\\\\"));
//			conf.setKingrestorepath(kingrest.replace("\\", "\\\\"));
			KingbsconfigBS.saveOrUpdate(conf);
			new LogUtil().createLog("454", "DATA_EXCHANGE_CONF", "", "", "��ʼ������", new ArrayList());
			this.setMainMessage("��ʼ���ɹ�");
			this.closeCueWindowByYes("win2");
		} catch (Exception e) {
			this.setMainMessage("��ʼ��ʧ��:" + e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}


}
