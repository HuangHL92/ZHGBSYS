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
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.helperUtil.DateUtil;


/**
 * 培训信息新增修改页面
 * @author Administrator
 *
 */
public class TrainingInfoAddPagePageModel extends PageModel {	
	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("TrainingInfoGrid.dogridquery");//培训信息列表		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveTrainingInfo()throws RadowException, AppException{
		A11 a11 = new A11();
		this.copyElementsValueToObj(a11, this);
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a11.setA0000(a0000);
		String a1100 = this.getPageElement("a1100").getValue();
		String a1107 = a11.getA1107();//培训开始时间
		String a1111 = a11.getA1111();//培训结束时间
		if(a1107!=null&&!"".equals(a1107)&&a1111!=null&&!"".equals(a1111)){
			if(a1107.length()==6){
				a1107 += "01";
			}
			if(a1111.length()==6){
				a1111 += "01";
			}
			//计算培训有几月几天。
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"), DateUtil.stringToDate(a1111, "yyyymmdd"));
			int mounthA1107a = days/31;//月
			int dayA1107b = days%31;//天
			a11.setA1107a((long)mounthA1107a);
			a11.setA1107b((long)dayA1107b);
		}else{
			a11.setA1107a(null);
			a11.setA1107b(null);
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(a1100==null||"".equals(a1100)){
				sess.save(a11);	
			}else{
				sess.update(a11);	
			}	
			sess.flush();			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1100").setValue(a11.getA1100());//保存成功将id返回到页面上。
		this.getPageElement("a1107a").setValue(a11.getA1107a()==null?"":a11.getA1107a().toString());//月
		this.getPageElement("a1107b").setValue(a11.getA1107b()==null?"":a11.getA1107b().toString());//日
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//刷新专业技术职务列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************培训信息A11*********************************************************************/
	
	/**
	 * 培训信息列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.dogridquery")
	public int trainingInforGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getRadow_parent_data();
		String sql = "select * from A11 where a0000='"+a0000+"'";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 培训信息新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoAddBtn.onclick")
	@NoRequiredValidate
	public int trainingInforAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A11 a11 = new A11();
		PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 培训信息的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	public int trainingInforGridOnRowClick() throws RadowException{  
		int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String a1100 = this.getPageElement("TrainingInfoGrid").getValue("a1100",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A11 a11 = (A11)sess.get(A11.class, a1100);
			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
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
		String a1100 = this.getPageElement("TrainingInfoGrid").getValue("a1100",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A11 a11 = (A11)sess.get(A11.class, a1100);
			sess.delete(a11);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			a11 = new A11();
			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
