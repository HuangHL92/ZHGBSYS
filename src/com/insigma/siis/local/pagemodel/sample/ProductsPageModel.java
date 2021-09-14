package com.insigma.siis.local.pagemodel.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.Products;
import com.insigma.siis.local.business.sample.ProductBS;

public class ProductsPageModel extends PageModel {

	@PageEvent("saveBtn.onclick")
	@Transaction
	@OpLog
	public int saveProduct()throws RadowException, AppException {
		Products p = new Products();
		this.copyElementsValueToObj(p, this);
		new ProductBS().saveProduct(p);
		Grid g = (Grid) this.getPageElement("pgrid");
		g.reload();
		resetProductInfo();
		this.setMainMessage("新增产品信息成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("testBtn.onclick")
	@NoRequiredValidate
	public int testSelfResFunc()throws RadowException{
		Products p = new Products();
		p.setId("1");
		p.setName("test");
		p.setPrice(20.00);
		this.setSelfDefResData(p);
		this.setSelfResponseFunc("selfResFunc");
		this.setMainMessage("测试自定义响应成功了！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("testBtn2.onclick")
	@NoRequiredValidate
	public int testSelfResFunc2()throws RadowException{
		Products p = new Products();
		p.setId("1");
		p.setName("test");
		p.setPrice(20.00);
		this.setMainMessage(JSONObject.fromObject(p).toString());
		this.isShowMsg = false;
		this.setSelfResponseFunc("selfResFunc");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void  resetProductInfo() throws RadowException{
		this.getPageElement("name").setValue("");
		this.getPageElement("price").setValue("");
		this.getPageElement("makedate").setValue("");
		this.getPageElement("effectmonths").setValue("");
		this.getPageElement("description").setValue("");
		this.getPageElement("name").focus();
	}
	
	@PageEvent("isDelProducts.onclick")
	@NoRequiredValidate
	public int isDelProducts()throws RadowException{
		this.setMainMessage("您确定要删除该笔记录么？");
		this.setMessageType(EventMessageType.CONFIRM);
		this.addNextEvent(NextEventValue.YES, "deleteProducts");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteProducts")
	@Transaction
	@OpLog
	@NoRequiredValidate
	public int deleteProducts()throws RadowException{
		Grid g = (Grid) this.getPageElement("pgrid");
		List<HashMap<String, Object>> data = g.getValueList();
		boolean isSelected = false;
		for(Iterator<HashMap<String, Object>> it = data.iterator();it.hasNext();){
			HashMap<String, Object> rowData = it.next();
			if(new Boolean(true).equals(rowData.get("check")) || "1".equals(rowData.get("check"))){
				isSelected = true;
				new ProductBS().deleteProduct((String)rowData.get("id"));
			}
		}
		if(!isSelected){
			this.setMainMessage("请在要删除的产品行前打钩选择！");
		}else{
			g.reload();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("isDelete")
	@NoRequiredValidate
	public int isDelete(String id)throws RadowException{
		this.setMainMessage("您确定要删除该笔记录么？");
		this.setMessageType(EventMessageType.CONFIRM);
		this.addNextEvent(NextEventValue.YES, "deleteProduct",id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteProduct")
	@Transaction
	@OpLog
	@NoRequiredValidate
	public int deleteProduct(String id)throws RadowException{
		new ProductBS().deleteProduct(id);
		Grid g = (Grid) this.getPageElement("pgrid");
		g.reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("openUpdateWin")
	@NoRequiredValidate
	public int openUpdateWin(String id)throws RadowException{
		this.setRadow_parent_data(id);
		this.openWindow("productWin", "pages.sample.ProductUpdate");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("pgrid.dogridquery")
	@NoRequiredValidate
	public int dogrid6Query(int start,int limit) throws RadowException{
		String sql = "from Products";
		this.pageQuery(sql,"HQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("pgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
