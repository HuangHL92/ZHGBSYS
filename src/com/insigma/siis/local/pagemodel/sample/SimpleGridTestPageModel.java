package com.insigma.siis.local.pagemodel.sample;


import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class SimpleGridTestPageModel extends PageModel {

	
	@PageEvent("toolBarBtn3.onclick")
	public int btn3OnClick() throws RadowException{
		String sql = "select * from smt_resource";
		this.getPageElement("grid6").getValueList();
		this.getPageElement("grid6").setValueList((List<HashMap<String, Object>>) this.pageQuery(sql,"SQL", -1, 10).getData());
		this.reloadPageByYes();
		this.setMainMessage("成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("stop.onclick")
	public int menuOnClick(){
		//this.setNextEventName("grid6.dogridquery");
		try {
			Grid g = (Grid)this.getPageElement("grid7");
			Grid g1 = (Grid)this.getPageElement("grid6");
			g.selectRow(2);
			g1.select(1, 2);
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("toolBarBtn1.onclick")
	public int btnOnClick(){
		this.setNextEventName("grid6.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("toolBarBtn2.onclick")
	public int btn2OnClick(){
		this.addNextEvent(NextEventValue.YES, "grid6.dogridquery");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent","cancelData");//其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行查询操作吗？"); //窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("cannelEvent")
	public int cannelEvent(String cancelData) throws RadowException{ //带参数的自定义事件
		//this.setMainMessage("grid7行双击事件成功！");
		this.openWindow("win1", "pages.sample.SimpleWindow"); //事件处理完后的打开窗口事件
		this.setRadow_parent_data("111");//设置父页面的数据，供子窗口window对象使用(即在子窗口中可以get到该值)
		this.setMainMessage("取消查询事件成功!"+cancelData);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid6.afteredit")
	@GridDataRange
	public int dogrid6AfterEdit() throws RadowException{
		/*this.setMainMessage((String)this.getPageElement("grid6").getValue("name"));
		this.getPageElement("grid6").setGridColumnHidden(2, true);
		this.getPMHList("grid6");
		this.autoFillPageByGridElement(this.getPageElement("grid6"),this.getPageElement("grid6").getCueRowIndex(),false);
		*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid6.beforeedit")
	public int dogrid6BeforeEdit() throws RadowException{
		//this.autoFillPageByGridElement(this.getPageElement("grid6"));
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid6.dogridquery")
	@EventDataRange("panel_content")
	@NoRequiredValidate
	public int dogrid6Query(int start,int limit) throws RadowException{
		String sql = "select '0' as \"check\",r.resourceid as id,r.name,r.description,'测试' as lastchange from smt_resource r";
		PageElement pe = this.getPageElement("panel_content.worker0");
		if(pe!=null&&!pe.getValue().equals("")){
			sql += " where name like '%"+pe.getValue()+"%' ";
		}
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid7.dogridquery")
	@EventDataRange("panel_content")
	@NoRequiredValidate
	public int dogrid7Query(int start,int limit) throws RadowException{
		String sql = "select * from smt_resource";
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/*
	@PageEvent("grid7.rowclick")
	public int grid7OnRowClick(){
		this.setMainMessage("grid7行单击事件成功！");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	*/
	
	
	@PageEvent("grid7.rowdbclick")
	@GridDataRange
	public int grid7OnRowDbClick() throws RadowException{  //打开窗口的实例
		//this.setMainMessage("grid7行双击事件成功！");
		this.openWindow("win1", "pages.sample.SimpleWindow"); //事件处理完后的打开窗口事件
		this.setRadow_parent_data("111");//设置父页面的数据，供子窗口window对象使用(即在子窗口中可以get到该值)
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("onchange")
	public int doonchange(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() {		
		this.setNextEventName("grid6.dogridquery");
		this.setNextEventName("grid7.dogridquery");
/*		this.setMainMessage("2222！");
*/		return EventRtnType.NORMAL_SUCCESS;
	}

}
