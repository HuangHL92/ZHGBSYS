package com.insigma.siis.local.pagemodel.sysmanager.parammgmt;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ParammgmtPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("codegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	/**
	 * 修改参数信息的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("codegrid.rowdbclick")
	@GridDataRange
	public int usergridOnRowDbClick() throws RadowException{  //打开窗口的实例
		this.openWindow("UpdateWin","pages.sysmanager.parammgmt.UpdateParam");//事件处理完后的打开窗口事件
		this.setRadow_parent_data(this.getPageElement("codegrid").getValue("aaa001",this.getPageElement("codegrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	/**
	 * 修改按钮事件
	 * @throws RadowException 
	 */
	@PageEvent("dogridedit")
	@GridDataRange
	public int doGridEdit(String aaa001) throws RadowException{
		this.openWindow("UpdateWin","pages.sysmanager.parammgmt.UpdateParam");//事件处理完后的打开窗口事件
		this.setRadow_parent_data(aaa001);
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	/**
	@PageEvent("usergrid.afteredit")
	public int usergridAfterEdit() throws RadowException{
		String aaa001 = null;
		String aaa002 = null;
		String aaa005 = null;
		String aaa105 = null;
		EditorGrid pe  = (EditorGrid)getPageElement("usergrid");
		aaa001 = (String) pe.getValue("aaa001");
		aaa002 = (String) pe.getValue("aaa002");
		aaa005 = (String) pe.getValue("aaa005");
		aaa105 = (String) pe.getValue("aaa105");
		HBSession sess = HBUtil.getHBSession();
		Aa01 aa01 = new Aa01();
		aa01.setAaa001(aaa001);
		aa01.setAaa002(aaa002);
		aa01.setAaa005(aaa005);
		aa01.setAaa105(aaa105);
		sess.saveOrUpdate(aa01);
		return EventRtnType.NORMAL_SUCCESS;
		
	}*/
	
	
	@PageEvent("codegrid.dogridquery")
	public int gridQuery(int start,int limit) throws RadowException{
		String sql = "select * from AA01 where aaa104='1' ";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
}
