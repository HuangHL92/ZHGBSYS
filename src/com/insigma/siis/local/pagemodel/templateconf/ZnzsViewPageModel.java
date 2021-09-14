package com.insigma.siis.local.pagemodel.templateconf;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.DutyNum;

public class ZnzsViewPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException, UnsupportedEncodingException{
		String param = this.getPageElement("subWinIdBussessId").getValue();
		if(param!=null && !"".equals(param)){
			String[] paramAll = param.split(",");
			 String order_number=paramAll[0];
			 String project1=paramAll[1];
			 String project=URLDecoder.decode(URLDecoder.decode(project1,"utf-8"), "utf-8");
			 String duty_category=paramAll[2];
			 String duty_rank=paramAll[3];
			 String quantity=paramAll[4];
			 //String remark=paramAll[5];
			 String one_ticket_veto=paramAll[5];
			 String id=paramAll[6];
			 this.getPageElement("order_number").setValue(order_number);
			 this.getPageElement("project").setValue(project);
			 this.getPageElement("duty_category").setValue(duty_category);
			 this.getPageElement("duty_rank").setValue(duty_rank);
			 this.getPageElement("quantity").setValue(quantity);
			 //this.getPageElement("remark").setValue(remark);
			 this.getPageElement("one_ticket_veto").setValue(one_ticket_veto);
			 this.getPageElement("id").setValue(id);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
	/**
	 * 
	 * 保存
	 * */
	@PageEvent("subm.onclick")
	public int save()throws  RadowException{
		HBSession sess =HBUtil. getHBSession ();
		PageElement pageElement = this.getPageElement("jgname");
		String gid=pageElement.getValue();
	    /*String sql="select creattime from duty_num where unit='"+gid+"'";
	    String creattime=(String) sess.createSQLQuery(sql).uniqueResult();*/
		String id = this.getPageElement("id").getValue();//项目
	    String project = this.getPageElement("project").getValue();//项目
	    String duty_category = this.getPageElement("duty_category").getValue();//职务类别
	    String duty_rank = this.getPageElement("duty_rank").getValue();//职务职级
	    String quantity = this.getPageElement("quantity").getValue();//数量>=或<=
	   // String project1 = this.getPageElement("project1").getValue();//数量，数字
	    String one_ticket_veto = this.getPageElement("one_ticket_veto").getValue();//一票否定
	    String jgname = this.getPageElement("jgname").getValue();//
	    Integer order_number =Integer.parseInt(this.getPageElement("order_number").getValue());//序号
	    Calendar now = Calendar.getInstance();  
	    String year =String.valueOf(now.get(Calendar.YEAR));
	    String month = String.valueOf(now.get(Calendar.MONTH) + 1);
	    DutyNum duty= new DutyNum();
	    /*if(creattime!=null||!"".equals(creattime)){
	    	duty.setCreattime(creattime);
	    }*/
	    duty.setCreattime(year+month);
	    duty.setProject(project);
	    duty.setUnit(jgname);
	    duty.setDutyCategory(duty_category);
	    duty.setDutyRank(duty_rank);
	    if(id!=null&&!"".equals(id)){
	    	duty.setId(id);
	    }
	   // duty.setQuantity(quantity+"&"+project1);
	    duty.setQuantity(quantity);
	    duty.setOneTicketVeto(one_ticket_veto);
	    duty.setOrderNumber(order_number);
	    sess.saveOrUpdate(duty);
	    sess.flush();
	    this.closeCueWindowEX();
	    this.getExecuteSG().addExecuteCode("realParent.refresh()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	

}
