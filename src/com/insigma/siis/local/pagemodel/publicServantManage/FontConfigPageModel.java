package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeValue;


public class FontConfigPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String sql="from CodeValue where codeType='FONTCONFIG'";
		
		List<CodeValue> list = sess.createQuery(sql).list();
		String config = ".fontConfig{";
		if(list!=null&&list.size()>0){
			CodeValue cv = list.get(0);
			this.getPageElement("color").setValue(cv.getCodeName2());
			this.getPageElement("bold").setValue(cv.getCodeName());
			this.getExecuteSG().addExecuteCode("$('#color').focus().blur();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveconfig()throws RadowException, AppException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String sql="from CodeValue where codeType='FONTCONFIG'";
		
		List<CodeValue> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			CodeValue cv = list.get(0);
			String color = this.getPageElement("color").getValue();
			String bold = this.getPageElement("bold").getValue();
			cv.setCodeName2(color);
			cv.setCodeName(bold);
		}
		
		this.getExecuteSG().addExecuteCode("realParent.window.location.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	static public String  getFontConfig(){
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String sql="from CodeValue where codeType='FONTCONFIG'";
		
		List<CodeValue> list = sess.createQuery(sql).list();
		String config = ".fontConfig{";
		if(list!=null&&list.size()>0){
			CodeValue cv = list.get(0);
			if("1".equals(cv.getCodeName())){
				config += "font-weight: bold;";
			}
			if(cv.getCodeName2()!=null&&!"".equals(cv.getCodeName2())){
				config += "color:"+cv.getCodeName2()+";";
			}
		}
		config += "}";
		return config;
	}
}
