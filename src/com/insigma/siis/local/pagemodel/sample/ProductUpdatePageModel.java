package com.insigma.siis.local.pagemodel.sample;


import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.siis.local.business.entity.Products;
import com.insigma.siis.local.business.sample.ProductBS;


public class ProductUpdatePageModel extends PageModel {

	@PageEvent("saveBtn.onclick")
	@Transaction
	public int saveProduct()throws RadowException, AppException {
		Products p = new Products();
		this.copyElementsValueToObj(p, this);
		new ProductBS().updateProduct(p);
		Grid g = (Grid) this.createPageElement("pgrid", ElementType.GRID, true);
		g.reload();
		this.setMainMessage("更新产品信息成功！");
		this.closeCueWindowByYes("productWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		String id = this.getRadow_parent_data();
		Products p = (Products) HBUtil.getHBSession().get(Products.class, id);
		this.autoFillPage(p, false);
		this.getPageElement("makedate").setValue(DateUtil.formatDate(p.getMakedate()));
		return EventRtnType.NORMAL_SUCCESS;
	}

}
