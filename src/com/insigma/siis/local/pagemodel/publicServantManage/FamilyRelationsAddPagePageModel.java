package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A36;


/**
 * 家庭主要成员及重要社会关系新增修改页面
 * @author Administrator
 *
 */
public class FamilyRelationsAddPagePageModel extends PageModel {	
	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		} 
		this.setNextEventName("FamilyRelationsGrid.dogridquery");//家庭主要成员及重要社会关系列表		
		//this.getPageElement("a0000").setValue(a0000);//人员内码
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveFamilyRelations()throws RadowException, AppException{
		A36 a36 = new A36();
		this.copyElementsValueToObj(a36, this);
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a36.setA0000(a0000);
		String a3600 = this.getPageElement("a3600").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(a3600==null||"".equals(a3600)){
				sess.save(a36);	
			}else{
				sess.update(a36);	
			}	
			sess.flush();			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a3600").setValue(a36.getA3600());//保存成功将id返回到页面上。
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('FamilyRelationsGrid').getStore().reload()");//刷新家庭主要成员及重要社会关系列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('FamilyRelationsGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************家庭主要成员及重要社会关系A36*********************************************************************/
	
	/**
	 * 家庭主要成员及重要社会关系
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("FamilyRelationsGrid.dogridquery")
	@NoRequiredValidate
	public int familyRelationsGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getRadow_parent_data();
		String sql = "select * from A36 where a0000='"+a0000+"'";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 家庭主要成员及重要社会关系新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("FamilyRelationsAddBtn.onclick")
	@NoRequiredValidate
	public int familyRelationsAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A36 a36 = new A36();
		a36.setA0000(a0000);	
		PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 家庭主要成员及重要社会关系的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("FamilyRelationsGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int familyRelationsGridOnRowClick() throws RadowException{  
		int index = this.getPageElement("FamilyRelationsGrid").getCueRowIndex();
		String a3600 = this.getPageElement("FamilyRelationsGrid").getValue("a3600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	public int deleteRow()throws RadowException, AppException{
		Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a3600 = this.getPageElement("FamilyRelationsGrid").getValue("a3600",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			sess.delete(a36);
			this.getExecuteSG().addExecuteCode("radow.doEvent('FamilyRelationsGrid.dogridquery')");
			a36 = new A36();
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
