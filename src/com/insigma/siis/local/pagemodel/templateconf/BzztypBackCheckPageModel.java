package com.insigma.siis.local.pagemodel.templateconf;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class BzztypBackCheckPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		String sqls = this.getPageElement("sqls").getValue();
		PageElement pageElement = this.getPageElement("sql");
		String sql= ""+sqls+"";
		HBSession sess =HBUtil. getHBSession();
		//String sql = "'"+fanc+"'";
		
		 //List list = sess.createQuery(sql).list();
	     //this.setSelfDefResData( this .getPageQueryData(list, start, limit));
	     this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	

}
