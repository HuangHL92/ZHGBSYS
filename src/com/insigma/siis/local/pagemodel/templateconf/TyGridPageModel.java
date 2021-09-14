package com.insigma.siis.local.pagemodel.templateconf;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.NaturalStructure;

public class TyGridPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException, UnsupportedEncodingException{
		String param = this.getPageElement("subWinIdBussessId").getValue();
		//order_number+","+project+","+quantity1+","+quantity+","+category+","+one_ticket_veto+","+id;
		if(param!=null && !"".equals(param)){
			String[] paramAll = param.split(",");
			 String order_number=paramAll[0];
			 String project1=paramAll[1];
			 String project=URLDecoder.decode(URLDecoder.decode(project1,"utf-8"), "utf-8");
			 String quantity1=paramAll[2];
			 String quantity=paramAll[3];
			 //String category=paramAll[4];
			 //String remark=paramAll[5];
			 String one_ticket_veto=paramAll[4];
			 String id=paramAll[5];
			 this.getPageElement("order_number").setValue(order_number);
			 this.getPageElement("project").setValue(project);
			 this.getPageElement("quantity1").setValue(quantity1);
			 this.getPageElement("quantity").setValue(quantity);
			// this.getPageElement("category").setValue(category);
			 //this.getPageElement("remark").setValue(remark);
			 this.getPageElement("one_ticket_veto").setValue(one_ticket_veto);
			 this.getPageElement("id").setValue(id);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
	
	@PageEvent("subm4.onclick")
	public int save() throws RadowException{
		String id = this.getPageElement("id").getValue();
		String project = this.getPageElement("project").getValue();
		String quantity1 = this.getPageElement("quantity1").getValue();
		String quantity = this.getPageElement("quantity").getValue();
		String one_ticket_veto = this.getPageElement("one_ticket_veto").getValue();
		String gid = this.getPageElement("gid").getValue();
		String tabtype = this.getPageElement("tabtype").getValue();
		 Integer order_number =Integer.parseInt(this.getPageElement("order_number").getValue());//ÐòºÅ
		 NaturalStructure natural = new NaturalStructure();
		 
		 natural.setProject(project);
		 natural.setQuantity(quantity1+"&"+quantity);
		 natural.setOneTicketVeto(one_ticket_veto);
		 natural.setOrderNumber(order_number);
		 natural.setUnit(gid);
		 natural.setCategory(tabtype);
		 if(id!=null&&!"".equals(id)){
			 natural.setId(id); 
		 }
		 HBSession sess = HBUtil.getHBSession();
		 sess.saveOrUpdate(natural);
		 sess.flush();
		    this.closeCueWindowEX();
		   this.getExecuteSG().addExecuteCode("realParent.refresh4()");
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
}
