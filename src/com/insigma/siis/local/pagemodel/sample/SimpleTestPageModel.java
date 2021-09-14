package com.insigma.siis.local.pagemodel.sample;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.IsFirstDisabled;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.data.PMHList;
import com.insigma.odin.framework.radow.element.CheckBox;
import com.insigma.odin.framework.radow.element.Radio;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class SimpleTestPageModel extends PageModel {

	@PageEvent("tab.tabchange")
	public int doTabChange(String tabId){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab002.onchange")
	@AutoNoMask
	public int aab002OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab030.ontriggerclick")
	public int aab030OnTriggerClick(String value){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab002.onblur")
	public int aab002OnBlur(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab004.onchange")
	@NoRequiredValidate
	public int aab004OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab004.onfocus")
	@NoRequiredValidate
	public int aab004OnFocus(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab003.onchange")
	@EventDataRange("div_1")
	public int aab003OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab003.onblur")
	public int aab003OnBlur(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab005.onclick")
	public int aab005OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab009.onclick")
	public int aab009OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab006.onclick")
	public int aab006OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btn1.onclick")
	@EventDataCustomized("aab001,aab007")  
	@AutoNoMask                            //不显示正在加载数据的层
	@NoRequiredValidate
	@IsFirstDisabled
	/**
	 * 处理完事件后的自动刷新当前页面
	 */
	public int btn1OnClick() throws RadowException{
		this.setRadow_parent_data("100011哈哈"); //设置父页面的数据，供子窗口window对象使用
		//this.setMainMessage("保存成功！");
		//this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btn2.onclick")
	@EventDataRange("div_1")
	@EventDataCustomized("aab001,aab007,data")  
	@OpLog(false)              // 不记录日志标注
	public int btn2OnClick() throws RadowException, AppException{
		PMHList hlist = this.getPMHList("div_1");
		hlist.retrieve("select '111' as aab018, '222' as aab028 from dual ");
		this.autoFillPm(hlist);
		this.getPageElement("aab001").setValue(hlist.getString("aab018"));
		this.getPageElement("data").setValue("data2");
		this.getPageElement("div_1.type").setValue("type2");
		this.setSelfResponseFunc("resFunc");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("toolBarBtn1.onclick")
	@EventDataRange("group1")
	@EventDataCustomized("aab001,aab007")  
	public int toolBarBtn1OnClick(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException { 
		this.getPageElement("div_1.aab018").setValue("test");
		this.getPageElement("div_1.type").setValue("type");
		this.getPageElement("data").setValue("data");
		this.getPageElement("aab003").setValue("200910");
		this.getPageElement("aab008").setDisabled(true);
		this.getPageElement("aab007").setReadonly(true);
		//this.setNextBackFunc(this.getPageElement("data").focus());
		this.getExecuteSG().addExecuteCode("alert(1);");
		((Radio)this.getPageElement("r1")).setChecked("2");
		((CheckBox)this.getPageElement("c1")).setChecked(false);
		((CheckBox)this.getPageElement("c2")).setChecked(true);
		return EventRtnType.NORMAL_SUCCESS;
	}
}
