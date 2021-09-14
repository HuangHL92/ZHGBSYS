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
		this.setMainMessage("�ɹ���");
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
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent","cancelData");//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ�в�ѯ������"); //������ʾ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("cannelEvent")
	public int cannelEvent(String cancelData) throws RadowException{ //���������Զ����¼�
		//this.setMainMessage("grid7��˫���¼��ɹ���");
		this.openWindow("win1", "pages.sample.SimpleWindow"); //�¼��������Ĵ򿪴����¼�
		this.setRadow_parent_data("111");//���ø�ҳ������ݣ����Ӵ���window����ʹ��(�����Ӵ����п���get����ֵ)
		this.setMainMessage("ȡ����ѯ�¼��ɹ�!"+cancelData);
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
		String sql = "select '0' as \"check\",r.resourceid as id,r.name,r.description,'����' as lastchange from smt_resource r";
		PageElement pe = this.getPageElement("panel_content.worker0");
		if(pe!=null&&!pe.getValue().equals("")){
			sql += " where name like '%"+pe.getValue()+"%' ";
		}
		this.pageQuery(sql,"SQL", start, limit); //�����ҳ��ѯ
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
		this.setMainMessage("grid7�е����¼��ɹ���");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	*/
	
	
	@PageEvent("grid7.rowdbclick")
	@GridDataRange
	public int grid7OnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
		//this.setMainMessage("grid7��˫���¼��ɹ���");
		this.openWindow("win1", "pages.sample.SimpleWindow"); //�¼��������Ĵ򿪴����¼�
		this.setRadow_parent_data("111");//���ø�ҳ������ݣ����Ӵ���window����ʹ��(�����Ӵ����п���get����ֵ)
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
/*		this.setMainMessage("2222��");
*/		return EventRtnType.NORMAL_SUCCESS;
	}

}
