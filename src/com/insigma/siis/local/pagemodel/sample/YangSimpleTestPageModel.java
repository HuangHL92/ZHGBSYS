package com.insigma.siis.local.pagemodel.sample;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.annotation.PageEvent;

public class YangSimpleTestPageModel extends PageModel {

	@PageEvent("aab002.onchange")
	public int aab002OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	 
	@PageEvent("aab002.onblur")
	public int aab002OnBlur(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab004.onchange")
	public int aab004OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab004.onfocus")
	public int aab004OnFocus(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("aab003.onchange")
	public int aab003OnChange(){
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
	public int btn1OnChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() { 
		return EventRtnType.NORMAL_SUCCESS;
	}
}
