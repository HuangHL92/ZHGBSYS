package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class CustomizeStatisticsPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//���ͨ��ͳ��ͼƬ
	@PageEvent("tytp.onclick")
	public int tytp() throws RadowException{
		this.setNextEventName("ty");
		return EventRtnType.NORMAL_SUCCESS;
	}
		
	//���ͨ��ͳ�ư�ť
	@PageEvent("ty")
	public int tytj() throws RadowException{
		String AllId = this.getRadow_parent_data().replace("'", "|");
		this.getExecuteSG().addExecuteCode("tytj('"+AllId+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�����άͳ��ͼƬ
	@PageEvent("ewtp.onclick")
	public int ewtp() throws RadowException{
		this.setNextEventName("ew");
		return EventRtnType.NORMAL_SUCCESS;
	}
		
	//�����άͳ�ư�ť
	@PageEvent("ew")
	public int ewtj() throws RadowException{
		String AllId = this.getRadow_parent_data().replace("'", "|");
		this.getExecuteSG().addExecuteCode("zdytj('"+AllId+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
