package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A1701entry;

public class ResumeBsSelectPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		HBSession sess = null;
		String a1701EntryId = this.getPageElement("subWinIdBussessId").getValue();
		try {
			sess = HBUtil.getHBSession();
			A1701entry a1701entry = (A1701entry)sess.get(A1701entry.class, a1701EntryId);
			this.getPageElement("a0000").setValue(a1701entry.getA0000());
			this.getPageElement("refBs").setValue(a1701entry.getRefBs());
			this.getPageElement("refBsId").setValue(a1701entry.getRefBsId());
			this.getExecuteSG().addExecuteCode("refreshP('"+a1701entry.getA0000()+"','"+a1701entry.getRefBs()+"')");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("网络连接异常！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 工作单位及职务列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.dogridquery")
	@NoRequiredValidate
	public int workUnitsGridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select * from a02 where a0000='"+a0000+"' order by lpad(a0223,5,'"+0+"') ";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("selectRow")
	@NoRequiredValidate
	public int selectRow() throws RadowException{
		this.getExecuteSG().addExecuteCode("selectRow()");
	    return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 学位学历列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.dogridquery")
	@NoRequiredValidate
	public int degreesgridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select * from A08 where a0000='"+a0000+"' order by a0837,a0801b";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("saveRef")
	@NoRequiredValidate
	@Transaction
	public int saveRef(String bsid) throws RadowException{
		HBSession sess = null;
		String a1701EntryId = this.getPageElement("subWinIdBussessId").getValue();
		String refBs = this.getPageElement("refBs").getValue();
		try {
			sess = HBUtil.getHBSession();
			A1701entry a1701entry = (A1701entry)sess.get(A1701entry.class, a1701EntryId);
			a1701entry.setRefBs(refBs);
			a1701entry.setRefBsId(bsid);
			a1701entry.setIsMatch("2");
			sess.update(a1701entry);
			sess.flush();
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('a1701grid.dogridquery');window.close();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("网络连接异常！");
			return EventRtnType.FAILD;
		}
	    return EventRtnType.NORMAL_SUCCESS;
	}
}
