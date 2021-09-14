package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

import org.hibernate.SQLQuery;

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
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A80;
import com.insigma.siis.local.business.entity.N29;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class RegisterAddPageGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		 
		this.setNextEventName("initX");
		this.setNextEventName("RegisterInfoGrid.dogridquery");
	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();
		//人员已审核则锁定 
		if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 保存修改
	 */
	@PageEvent("save.onclick")
	@Transaction
	public int saveProfessSkill()throws RadowException, AppException{		
		N29 n29 = new N29();
		//赋值
		String a2947 = this.getPageElement("a2947").getValue();
		String a2949 = this.getPageElement("a2949").getValue();
		n29.setA2947(a2947);
		n29.setA2949(a2949);
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		n29.setA0000(a0000);
		String n2900 = this.getPageElement("n2900").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			if(n2900==null||"".equals(n2900)){
				applog.createLog("n2901", "N29", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new N29(), n29));
				sess.save(n29);	
			}else{
				n29.setN2900(n2900);
				
				String sql = "select A2947,A2949 from N29 where n2900=:n2900";
				SQLQuery query = sess.createSQLQuery(sql);
				query.setString("n2900", n2900);
				
				Object[] obj_array = (Object[])query.list().get(0);
				N29 n29_old = new N29();
				n29_old.setA2947(obj_array[0].toString());
				n29_old.setA2949(obj_array[1].toString());
				n29_old.setA0000(a0000);
				n29_old.setN2900(n2900);
				applog.createLog("n2902", "N29", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(n29_old, n29));
				//PropertyUtils.copyProperties(a36_old, a36);
				sess.update(n29);	
			}
			sess.flush();
			this.setMainMessage("保存成功!");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("n2900").setValue(n29.getN2900());
		setParentA2949(a0000);
		this.setNextEventName("RegisterInfoGrid.dogridquery");//刷新
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.close();";
	}
	
	private void setParentA2949(String a0000){
		HBSession sess = HBUtil.getHBSession();
		List<N29> list = sess.createQuery("from N29 where a0000='"+a0000+"' order by a2949 desc").list();
		if(list.size()>0){
			N29 n29 = list.get(0);
			this.getExecuteSG().addExecuteCode("realParent.setA2949('"+n29.getA2949()+"')");
		}else{
			this.getExecuteSG().addExecuteCode("realParent.setA2949('')");
		}
	}
	
	/**
	 * 登记信息集列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RegisterInfoGrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a.* from N29 a where a0000='"+a0000+"'";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 登记的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RegisterInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("RegisterInfoGrid").getCueRowIndex();
		String n2900 = this.getPageElement("RegisterInfoGrid").getValue("n2900",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			N29 n29 = (N29)sess.get(N29.class, n2900);
			PMPropertyCopyUtil.copyObjValueToElement(n29, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		//this.setRadow_parent_data("a0600:"+this.getPageElement("professSkillgrid").getValue("a0600",this.getPageElement("professSkillgrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String n2900)throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			N29 n29 = (N29)sess.get(N29.class, n2900);
			A01 a01 = (A01)sess.get(A01.class, n29.getA0000());
			//applog.createLog("n2901", "N29", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new N29(), new N29()));
			
			//删除了哪条
			applog.createLog("n2903", "N29", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(n29, new N29()));
			
			sess.delete(n29);
			sess.flush();
			n29 = new N29();
			n29.setA0000(a0000);
			PMPropertyCopyUtil.copyObjValueToElement(n29, this);
			setParentA2949(a0000);
			this.setNextEventName("RegisterInfoGrid.dogridquery");
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
