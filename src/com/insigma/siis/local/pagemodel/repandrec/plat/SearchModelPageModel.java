package com.insigma.siis.local.pagemodel.repandrec.plat;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class SearchModelPageModel extends PageModel{
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("closeBtn.onclick")
	@NoRequiredValidate           ///??????
	public int closeBtn() throws RadowException{
		this.closeCueWindow("simpleExpWin");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("btnAdd.onclick")
	@NoRequiredValidate           ///??????
	public int btnAdd() throws RadowException{
		Object obj = this.getPageElement("Grid").getValue("sub_libraries_model_id",this.getPageElement("Grid").getCueRowIndex());
		if(obj == null){
			this.setMainMessage("请选择模型!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sub_libraries_model_id = this.getPageElement("Grid").getValue("sub_libraries_model_id",this.getPageElement("Grid").getCueRowIndex()).toString();
		String sub_libraries_model_name = this.getPageElement("Grid").getValue("sub_libraries_model_name",this.getPageElement("Grid").getCueRowIndex()).toString();
		this.createPageElement("searchModelBtn", ElementType.TEXTWITHICON, true).setValue(sub_libraries_model_name);
		this.createPageElement("searchModelId", ElementType.HIDDEN, true).setValue(sub_libraries_model_id);
		this.closeCueWindow("simpleExpWin");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("Grid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		StringBuffer sql = new StringBuffer("select sub_libraries_model_id,sub_libraries_model_name," +
				"create_time,sub_libraries_model_type,self_create_mark,run_state,sub_libraries_model_key" +
				" from SUB_LIBRARIES_MODEL where 1=1 ");
		this.setSelfDefResData(this.pageQuery(sql.toString(),"SQL", start,limit)); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("Grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
