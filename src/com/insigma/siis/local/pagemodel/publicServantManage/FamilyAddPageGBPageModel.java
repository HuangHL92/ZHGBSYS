package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

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
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class FamilyAddPageGBPageModel extends PageModel {
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
		this.setNextEventName("FamilyInfoGrid.dogridquery");// 专业技术职务列表
		// a01中的专业技术职务
		try {
			HBSession sess = HBUtil.getHBSession();
			List<A36> list = sess.createQuery("from A36 where a0000='"+a0000+"' order by sortid").list();
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				PMPropertyCopyUtil.copyObjValueToElement((A36)(sess.get(A36.class, list.get(0).getA3600())), this);
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/***********************************************家庭成员(a36)*********************************************************************/
	
	/**
	 * 保存修改
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		A36 a36 = new A36();
		//赋值
		String a3601 = this.getPageElement("a3601").getValue();
		String a3604A = this.getPageElement("a3604a_combo").getValue();
		String a3607 = this.getPageElement("a3607").getValue();
		String a3627 = this.getPageElement("a3627_combo").getValue();
		String a3611 = this.getPageElement("a3611").getValue();
		String a3645 = this.getPageElement("a3645_combo").getValue();
		String a3617 = this.getPageElement("a3617check").getValue();
		a36.setA3601(a3601);
		a36.setA3604a(a3604A);
		a36.setA3607(a3607);
		a36.setA3627(a3627);
		a36.setA3611(a3611);
		a36.setA3645(a3645);
		a36.setA3617(a3617);
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a36.setA0000(a0000);
		String a3600 = this.getPageElement("a3600").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			if(a3600==null||"".equals(a3600)){
				applog.createLog("3061", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36));
				String sql = "select max(sortid)+1 from a36 where a0000='"+a0000+"'";
				List<Object> sortid = sess.createSQLQuery(sql).list();//oracle:BigDecimal,mysql:BigInteger
				if(sortid.get(0)==null){
					a36.setSortid(new BigDecimal(1));
				}else{
					a36.setSortid(new BigDecimal(sortid.get(0).toString()));
				}
				sess.save(a36);	
			}else{
				a36.setA3600(a3600);
				applog.createLog("3062", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36, a36));
				//PropertyUtils.copyProperties(a36_old, a36);
				sess.update(a36);	
			}
			sess.flush();
			//updateA01(a0000);
			
			this.setMainMessage("保存成功!");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		//PMPropertyCopyUtil.copyObjValueToElement(a36, this);//保存成功将id返回到页面上。
		this.getPageElement("a3600").setValue(a36.getA3600());
//		this.getExecuteSG().addExecuteCode("Ext.getCmp('FamilyInfoGrid').getStore().reload()");//刷新专业技术职务列表
		this.setNextEventName("FamilyInfoGrid.dogridquery");//刷新
//		this.getExecuteSG().addExecuteCode("radow.doEvent('worksort')");
//		this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.close();";
	}
	/*private void updateA01(String a0000) throws AppException{
		//当前页面赋值
		List<String> list = HBUtil.getHBSession().createSQLQuery("select a0602 from a06 where a0000='"+a0000+"' "
				+ " and a0699='true' order by sortid").list();//and sortid=(select max(sortid) from a06 where a0000='"+a0000+"')
		//String a0196 = HBUtil.getValueFromTab("a0602", "a06", "a0000='"+a0000+"' and sortid=(select max(sortid) from a06 where a0000='"+a0000+"')");
		//a0196 = a0196==null?"":a0196;
		StringBuffer a0196s = new StringBuffer();
		for(String a0602 : list){
			a0602 = a0602==null?"":a0602;
			a0196s.append(a0602+"，");
		}
		if(a0196s.length()>0){
			a0196s.deleteCharAt(a0196s.length()-1);
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('a0196').setValue('"+a0196s+"')");
		//人员基本信息字段更新。
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0196').value='"+a0196+"'");
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0196').value='"+a0196s.toString()+"';window.realParent.a0196onblur();");
		
		CustomQueryBS.setA01(a0000);
		
		HBSession sess = HBUtil.getHBSession();
    	A01 a01 = (A01)sess.get(A01.class, a0000);
		//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01));
		
		//更新A10 a0196 专业技术职务字段。   a06 a0602
		HBUtil.executeUpdate("update a01 set a0196='"+a0196s.toString()+"' where a0000='"+a0000+"'");
		
	}*/
	/**
	 * 家庭成員职务列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("FamilyInfoGrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a.* from A36 a where a0000='"+a0000+"' order by sortid";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 家庭成員新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("FamilyAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A36 a36 = new A36();
		PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		this.getPageElement("a3617check").setValue("0");
		this.getExecuteSG().addExecuteCode("changea3617check('0')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 家庭成員的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("FamilyInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("FamilyInfoGrid").getCueRowIndex();
		String a3600 = this.getPageElement("FamilyInfoGrid").getValue("a3600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
			if(a36.getA3617().equals("0")){
				this.getPageElement("a3617check").setValue(a36.getA3617());
				this.getExecuteSG().addExecuteCode("changea3617check('"+a36.getA3617()+"')");
			}else{
				this.getPageElement("a3617check").setValue(a36.getA3617());
				this.getExecuteSG().addExecuteCode("changea3617check('"+a36.getA3617()+"')");
			}
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
	public int deleteRow(String a3600)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			A01 a01 = (A01)sess.get(A01.class, a36.getA0000());
			applog.createLog("3063", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36()));
			sess.delete(a36);
			this.getExecuteSG().addExecuteCode("radow.doEvent('FamilyInfoGrid.dogridquery')");
			sess.flush();
			a36 = new A36();
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
			this.getPageElement("a3617check").setValue("0");
			this.getExecuteSG().addExecuteCode("changea3617check('0')");
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 输出复选框选中事件
	 * @return
	 * @throws RadowException
	 *//*
	@PageEvent("updateA06")
	@Transaction
	@NoRequiredValidate
	public int updateA06(String a0600) throws RadowException {
		
		try{
			if(a0600!=null){
				HBSession sess = HBUtil.getHBSession();
				A06 a06 = (A06)sess.get(A06.class, a0600);
				Boolean checked = "true".equals(a06.getA0699());
				a06.setA0699(String.valueOf(!checked));
				sess.save(a06);
				sess.flush();
				PMPropertyCopyUtil.copyObjValueToElement(a06, this);
				updateA01( a06.getA0000() );
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("更新失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;	
	}*/
	
	/*@SuppressWarnings("unchecked")
	@PageEvent("worksort")
	@NoRequiredValidate
	@Transaction
	public int upsort()throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("professSkillgrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			String a0000 = this.getPageElement("a0000").getValue();
			int i = 0;
			StringBuffer a0196s = new StringBuffer("");
			for(HashMap<String,String> m : list){
				String a0600 = m.get("a0600");//a02 id
				HBUtil.executeUpdate("update a06 set sortid="+i+++" where a0600='"+a0600+"'");		
			}
			updateA01(a0000);
			
			this.setNextEventName("professSkillgrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	//得到最新的排序号
	public long getsortid(String a0000){
		HBSession session = HBUtil.getHBSession();
		String maxSortid = session.createSQLQuery("select max(sortid) from a36 where A0000='"+a0000+"' ORDER BY SORTID").uniqueResult().toString();
		return Long.parseLong(maxSortid);
	}

}
