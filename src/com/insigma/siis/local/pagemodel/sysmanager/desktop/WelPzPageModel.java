package com.insigma.siis.local.pagemodel.sysmanager.desktop;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.SmtDeskTopItem;
import com.insigma.siis.local.business.desktop.SmtBS;

public class WelPzPageModel extends PageModel {

	@Override
	
	public int doInit() throws RadowException {
		return 0;
	}
	@PageEvent("doQuery.onclick")
	public int doquery() throws RadowException {
		this.setNextEventName("welGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	} 
	
	@PageEvent("welGrid.dogridquery")
	public int grid1quey(int start,int limit) throws RadowException {
		StringBuffer sql = new StringBuffer("select * from smt_desktopitem where 1=1 ");
		if(getPageElement("titlecx").getValue().trim().length()>0) {
			sql.append(" and title like '%"+getPageElement("titlecx").getValue()+"%'");
		}
		if(getPageElement("englishnamecx").getValue().trim().length()>0) {
			sql.append(" and name like '%"+getPageElement("englishnamecx").getValue()+"%'");
		}
		this.pageQuery(sql.toString(), "SQL", -1, 15);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("doSave.onclick")
	@OpLog
	@Transaction
	public int save()throws RadowException , AppException{
		if(this.getPageElement("title").getValue().trim().length()==0
			|| this.getPageElement("url").getValue().trim().length()==0
			|| this.getPageElement("name").getValue().trim().length()==0)
		{
			this.setMainMessage("请不要在必须输入的信息中输入空白内容!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		SmtDeskTopItem sdti = new SmtDeskTopItem();
		this.copyElementsValueToObj(sdti, this);
		s.saveOrUpdate(sdti);
		//this.setMainMessage("step3"+i);
		if(sdti.getId()==null || sdti.getId().equals("")){
			((Grid)this.getPageElement("welGrid")).reload();
		}else{
		
			this.setMainMessage(sdti.getTitle()+"保存成功！点击确定后将自动刷新。");
			this.setNextEventName("welGrid.dogridquery");	
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("welGrid.rowclick")
	@NoRequiredValidate
	public int welGridClick()throws RadowException{
		this.getPageElement("id").setValue("");
		this.getPageElement("title").setValue("");
		this.getPageElement("url").setValue("");
		this.getPageElement("name").setValue("");
		this.getPageElement("height").setValue("");
		this.getPageElement("description").setValue("");
		this.getPageElement("orderno").setValue("");
		Grid wel = (Grid)this.getPageElement("welGrid");
		this.autoFillPageByGridElement(wel,0,false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doDelete.onclick")
	public int dodelete() throws RadowException, AppException{
		SmtDeskTopItem sdti = new SmtDeskTopItem();
		this.copyElementsValueToObj(sdti, this);
		HBTransaction trans=null;
		HBSession sess = HBUtil.getHBSession();
		trans=sess.beginTransaction();
		sess.delete(sdti);
		sess.flush();//清理缓存执行sql
		trans.commit();
		if(sdti.getId()==null || sdti.getId().equals("")){
			((Grid)this.getPageElement("welGrid")).reload();
		}else{
		
			this.setMainMessage(sdti.getTitle()+"删除成功！点击确定后将自动刷新。");
			this.setNextEventName("welGrid.dogridquery");	
		}
		this.getPageElement("id").setValue("");
		this.getPageElement("title").setValue(" ");
		this.getPageElement("url").setValue(" ");
		this.getPageElement("name").setValue(" ");
		this.getPageElement("height").setValue("");
		this.getPageElement("description").setValue("");
		this.getPageElement("orderno").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doAdd.onclick")
	public int doAdd() throws RadowException{
		this.getPageElement("id").setValue("");
		this.getPageElement("title").setValue("");
		this.getPageElement("url").setValue("");
		this.getPageElement("name").setValue("");
		this.getPageElement("height").setValue("");
		this.getPageElement("description").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private SmtBS s = new SmtBS();

}