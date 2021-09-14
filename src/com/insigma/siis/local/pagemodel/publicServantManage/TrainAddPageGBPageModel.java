package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.util.List;

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
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class TrainAddPageGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();//String a0000 = this.getRadow_parent_data();
		if (a0000 == null || "".equals(a0000)) {//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//人员已审核则锁定
		if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}
		
		this.setNextEventName("TrainInfoGrid.dogridquery");// 专业技术职务列表
		// a01中的专业技术职务
		try {
			HBSession sess = HBUtil.getHBSession();
			List<A11> list = sess.createQuery("from A11 where a0000='"+a0000+"'").list();
			if(list.size()==0){
				A11 a11 = new A11();
				a11.setA0000(a0000);
				PMPropertyCopyUtil.copyObjValueToElement(a11, this);
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				PMPropertyCopyUtil.copyObjValueToElement(list.get(0), this);
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 保存修改
	 */
//	@PageEvent("save.onclick")
//	@Transaction
//	@Synchronous(true)
//	@NoRequiredValidate
//	public int saveProfessSkill()throws RadowException, AppException{		
//		A11 a11 = new A11();
//		String g11021 = this.getPageElement("g11021").getValue();
//		if(g11021==null||"".equals(g11021)){
//			this.setMainMessage("培训类别不能为空！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		String a1107 = this.getPageElement("a1107").getValue();
//		String a1111 = this.getPageElement("a1111").getValue();
//		String a1108 = this.getPageElement("a1108").getValue();
//		String a1114 = this.getPageElement("a1114").getValue();
//		String a1131 = this.getPageElement("a1131").getValue();
//		String g11003 = this.getPageElement("g11003").getValue();
//		String g11006 = this.getPageElement("g11006").getValue();
//		String g11020 = this.getPageElement("g11020").getValue();
//		String g11022 = this.getPageElement("g11022").getValue();
//		String g11023 = this.getPageElement("g11023").getValue();
//		String g11024 = this.getPageElement("g11024").getValue();
//		String g11025 = this.getPageElement("g11025").getValue();
//		
//		a11.setG11021(g11021);
//		a11.setG11020(g11020);
//		a11.setG11022(g11022);
//		a11.setG11023(g11023);
//		a11.setG11024(g11024);
//		a11.setG11025(g11025);
//		a11.setA1107(a1107);
//		a11.setA1111(a1111);
//		if(a1108!=null&&!"".equals(a1108)){
//			a11.setA1108(new BigDecimal(a1108));
//		}
//		a11.setA1114(a1114);
//		a11.setA1131(a1131);
//		a11.setG11003(g11003);
//		a11.setG11006(g11006);
//		String a0000 = this.getPageElement("a0000").getValue();
//		if(a0000==null||"".equals(a0000)){
//			this.setMainMessage("请先保存人员基本信息！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		a11.setA0000(a0000);
//		String a1100 = this.getPageElement("a1100").getValue();
//		HBSession sess = null;
//		try {
//			sess = HBUtil.getHBSession();
//			A01 a01 = (A01)sess.get(A01.class, a0000);
//			
//			if(a1100==null||"".equals(a1100)){
//				applog.createLog("a1101", "A11", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A11(), a11));
//				sess.save(a11);	
//			}else{
//				a11.setA1100(a1100);
//				applog.createLog("a1101", "A01", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a11, a11));
//				//PropertyUtils.copyProperties(a36_old, a36);
//				sess.update(a11);	
//			}
//			sess.flush();
//			this.setMainMessage("保存成功!");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setMainMessage("保存失败！");
//			return EventRtnType.FAILD;
//		}
//		this.getPageElement("a1100").setValue(a11.getA1100());
//		this.setNextEventName("TrainInfoGrid.dogridquery");//刷新
//		return EventRtnType.NORMAL_SUCCESS;
//	}
//	public String closeCueWindowEX(){
//		return "window.close();";
//	}
	/**
	 * 培訓列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainInfoGrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select t.year,t.xrdx05 a1107,t.xrdx10 address,t.xrdx09 a1108,\n" + 
				" (select code_name from code_value where code_type='TRANORG' and code_value=t.xrdx03) a1114,\n" + 
				" t.xrdx01 a1131\n" + 
				" from edu_xrdx t,edu_xrdx_ry t1,a01\n" + 
				" where t.xrdx00=t1.xrdx00 and t1.a0000=a01.a0000\n" + 
				"  and t1.a0000='"+a0000+"'\n" + 
				" order by xrdx05 desc";
		this.pageQuery(sql,"SQL", start, 500); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 培訓新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
//	@PageEvent("TrainAddBtn.onclick")
//	@NoRequiredValidate
//	public int openprofessSkillWin(String id)throws RadowException{
//		String a0000 = this.getPageElement("a0000").getValue();
//		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
//		if(a0000==null||"".equals(a0000)){//
//			this.setMainMessage("请先保存人员基本信息！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		A11 a11 = new A11();
//		a11.setA0000(a0000);
//		PMPropertyCopyUtil.copyObjValueToElement(a11, this);
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * 修改专业技术职务的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
//	@PageEvent("TrainInfoGrid.rowclick")
//	@GridDataRange
//	@NoRequiredValidate
//	public int professSkillOnRowClick() throws RadowException{ 
//		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
//		//获取选中行index
//		int index = this.getPageElement("TrainInfoGrid").getCueRowIndex();
//		String a1100 = this.getPageElement("TrainInfoGrid").getValue("a1100",index).toString();
//		
//		HBSession sess = null;
//		try {
//			sess = HBUtil.getHBSession();
//			A11 a11 = (A11)sess.get(A11.class, a1100);
//			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
//		} catch (Exception e) {
//			this.setMainMessage("查询失败！");
//			return EventRtnType.FAILD;
//		}							
//		//this.setRadow_parent_data("a0600:"+this.getPageElement("professSkillgrid").getValue("a0600",this.getPageElement("professSkillgrid").getCueRowIndex()).toString());
//		return EventRtnType.NORMAL_SUCCESS;		
//	}
	
	
//	@PageEvent("deleteRow")
//	@Transaction
//	@Synchronous(true)
//	@NoRequiredValidate
//	public int deleteRow(String a1100)throws RadowException, AppException{
//		/*Map map = this.getRequestParamer();
//		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
//		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();*/
//		String a0000 = this.getPageElement("a0000").getValue();
//		HBSession sess = null;
//		try {
//			sess = HBUtil.getHBSession();
//			A11 a11 = (A11)sess.get(A11.class, a1100);
//			A01 a01 = (A01)sess.get(A01.class, a11.getA0000());
//			applog.createLog("a1101", "A01", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A01(), new A01()));
//			sess.delete(a11);
//			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainInfoGrid.dogridquery')");
//			sess.flush();
//			a11 = new A11();
//			a11.setA0000(a0000);
//			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
//		} catch (Exception e) {
//			this.setMainMessage("删除失败！");
//			return EventRtnType.FAILD;
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	
	//得到最新的排序号
	public long getsortid(String a0000){
		HBSession session = HBUtil.getHBSession();
		String maxSortid = session.createSQLQuery("select max(sortid) from a36 where A0000='"+a0000+"' ORDER BY SORTID").uniqueResult().toString();
		return Long.parseLong(maxSortid);
	}

}
