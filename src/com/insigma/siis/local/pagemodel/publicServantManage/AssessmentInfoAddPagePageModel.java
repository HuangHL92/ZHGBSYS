package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;


/**
 * 年度考核情况新增修改页面
 * @author Administrator
 *
 */
public class AssessmentInfoAddPagePageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//人员已审核则锁定
		if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}
		
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR)+1;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<80;i++){
			map.put(""+(year-i), year-i);
		}
		((Combo)this.getPageElement("a1521")).setValueListForSelect(map); 
		this.setNextEventName("AssessmentInfoGrid.dogridquery");//年度考核情况列表		
		//a01中年度考核情况
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			String sqlCount = "select count(1) from a15 where a0000 = '"+a0000+"'";
			Object o1 = sess.createSQLQuery(sqlCount).uniqueResult();
			Integer n = Integer.parseInt(o1.toString());
			if(n==0){
				String a15z101 = a01.getA15z101();
				if(a15z101!=null && !"无".equals(a15z101.trim())){
					a01.setA0191("0");
				}
			}
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
			List<A15> list = sess.createQuery(sql.toString()).list();
			if(list!=null&&list.size()>0){
				A15 a15 = list.get(0);
				this.getPageElement("a1527").setValue(a15.getA1527());
			}
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("changedispaly();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveAssessmentInfo()throws RadowException, AppException{
		String a1521s = this.getPageElement("a1521").getValue();
		String a1517 = this.getPageElement("a1517").getValue();
		String [] a1521sNum = a1521s.split(",");
		if(a1521sNum.length>1){
			
			return saveAllInfo();
		}else{
			A15 a15 = new A15();
			this.copyElementsValueToObj(a15, this);
			//String a0000 = this.getRadow_parent_data();
			String a0000 = this.getPageElement("subWinIdBussessId").getValue();
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			a15.setA0000(a0000);
			String a1500 = this.getPageElement("a1500").getValue();
			HBSession sess = null;
			String isconnect = "";
			try {
				sess = HBUtil.getHBSession();
				A01 a01 = (A01)sess.get(A01.class, a0000);
				//检查该年度是否已录入
				StringBuffer sql = new StringBuffer("from A15 where a0000='"+a15.getA0000()+"' and a1521='"+a15.getA1521()+"'");			
				if(a1500!=null&&!"".equals(a1500)){
					sql.append(" and a1500!='"+a1500+"'");
				}
				List list = sess.createQuery(sql.toString()).list();
				if(list!=null&&list.size()>0){
					this.setMainMessage("该年填写重复!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a15.getA1521()!=null&&!"".equals(a15.getA1521())&&a15.getA1517()!=null&&!"".equals(a15.getA1517())){
					// 新增或修改
					if (a1500 == null || "".equals(a1500)) {
						
						applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A15(), a15));
						
						sess.save(a15);
					} else {
						A15 a15_old = (A15)sess.get(A15.class, a1500);
						applog.createLog("3152", "A15", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a15_old, a15));
						PropertyUtils.copyProperties(a15_old, a15);
						
						sess.update(a15_old);
					}	
				}
				sess.flush();
				//更新a01年度考核情况
				String a15z101 = this.getPageElement("a15z101").getValue();
				a01.setA15z101(a15z101);
				
				//是否与列表关联
				isconnect = this.getPageElement("a0191").getValue();
				a01.setA0191(isconnect);
				if("1".equals(isconnect)){
					listAssociation();
				}
				//人员基本信息界面
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
				sess.update(a01);
				sess.flush();
				
				CustomQueryBS.setA01(a0000);
		    	A01 a01F = (A01)sess.get(A01.class, a0000);
				this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

				this.setMainMessage("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("保存失败！");
				return EventRtnType.FAILD;
			}
			
			this.getPageElement("a1500").setValue(a15.getA1500());//保存成功将id返回到页面上。
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	/**
	 * 批量保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveAll.onclick")
	@Transaction
	@Synchronous(true)
	public int saveAllInfo()throws RadowException, AppException{
		String a1500 = this.getPageElement("a1500").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1521s = this.getPageElement("a1521").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1517 = this.getPageElement("a1517").getValue();//考核结果
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//检查该年度是否已录入
			StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
			List<A15> list = sess.createQuery(sql.toString()).list();
			//已录年度
			StringBuffer years = new StringBuffer("");
			if(list!=null&&list.size()>0){
				for(A15 a15y : list){
					years.append(a15y.getA1521()+",");
				}
			}
			String [] num = a1521s.split(",");
			//循环批量新增考核信息
			for(int i=0;i<num.length;i++){
				if(years.indexOf(num[i])!=-1||a1517==null||"".equals(a1517)){
					continue;
				}			
				A15 a15save = new A15();
				a15save.setA1521(num[i]);
				a15save.setA1517(a1517);
				a15save.setA0000(a0000);
				
				applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A15(), a15save));
				
				sess.save(a15save);
			}
			sess.flush();
			//更新a01年度考核情况
			String a15z101 = this.getPageElement("a15z101").getValue();
			a01.setA15z101(a15z101);
			
			//是否与列表关联
			String isconnect = this.getPageElement("a0191").getValue();
			a01.setA0191(isconnect);
			if("1".equals(isconnect)){
				listAssociation();
			}
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
			sess.update(a01);
			sess.flush();			
			
			sess.flush();
			
			CustomQueryBS.setA01(a0000);
	    	A01 a01F = (A01)sess.get(A01.class, a0000);
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
			
			//this.setMainMessage("增加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("增加失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1500").setValue("");//清空页面id。
		this.getExecuteSG().addExecuteCode("Ext.getCmp('AssessmentInfoGrid').getStore().reload()");//刷新列表
		/*A15 a15 = new A15();
		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(a15, this);*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************年度考核情况A15*********************************************************************/
	
	/**
	 * 年度考核情况列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoGrid.dogridquery")
	@NoRequiredValidate
	public int assessmentInfoGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from A15 where a0000='"+a0000+"' order by a1521 desc";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 年度考核情况新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A15 a15 = new A15();
		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 年度考核情况的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int assessmentInfoGridOnRowDbClick() throws RadowException{  
		int index = this.getPageElement("AssessmentInfoGrid").getCueRowIndex();
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A15 a15 = (A15)sess.get(A15.class, a1500);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 考核情况描述
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("listAssociation.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int listAssociation()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1527 = this.getPageElement("a1527").getValue();//选择年度个数
		if(a1527==null||"".equals(a1527)){
			return EventRtnType.NORMAL_SUCCESS;	
		}
		HBUtil.executeUpdate("update a15 set a1527='"+a1527+"' where a0000='"+a0000+"'");
		
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "from A15 where a0000='"+a0000+"' order by a1521 asc";
		List<A15> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		//List<HashMap<String, Object>> list = this.getPageElement("AssessmentInfoGrid").getValueList();
		if(list!=null&&list.size()>0){
			int years = "".equals(a1527)?list.size():Integer.valueOf(a1527);
			if(years>list.size()){
				years = list.size();
			}
			StringBuffer desc = new StringBuffer("");
			for(int i=list.size()-years;i<list.size();i++){
				A15 a15 = list.get(i);
				//考核年度
				String a1521 = a15.getA1521();
				//考核结果
				String a1517 = a15.getA1517();
				String a1517Name = HBUtil.getCodeName("ZB18",a1517);
				if(a1517Name.equals("不定等次")) {
					desc.append(a1521+"年度考核"+a1517Name+"；");
				}else {
					desc.append(a1521+"年度考核"+a1517Name+"；");
				}
			}
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "。");
			}
			this.getPageElement("a15z101").setValue(desc.toString());
			
			a01.setA15z101(desc.toString());
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}else{
			String description = "无";
			this.getPageElement("a15z101").setValue(description);
			a01.setA15z101(description);
			sess.update(a01);
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1500)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A15 a15 = (A15)sess.get(A15.class, a1500);
			A01 a01= (A01)sess.get(A01.class, a15.getA0000());
			String a1527 = a15.getA1527();
			
			//applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			//记录了删除哪条
			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(a15, new A15()));
			
			sess.delete(a15);
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			a15 = new A15();
			a15.setA1527(a1527);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
			
			//判断当前是否与列表关联
			String a0191 = this.getPageElement("a0191").getValue();
			a01.setA0191(a0191);
			if("1".equals(a0191)){
				listAssociation();
			}
			
			CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
