package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;

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
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.lbs.leaf.BeanHelper;


/**
 * 专业技术职务新增修改页面
 * @author Administrator
 *
 */
public class ProfessSkillAddPageGBPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
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
		this.setNextEventName("professSkillgrid.dogridquery");// 专业技术职务列表
		// a01中的专业技术职务
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if (a01.getA0196() != null) {
				this.getExecuteSG().addExecuteCode("Ext.getCmp('a0196').setValue('" + a01.getA0196() + "')");
				
				
				//对于导入的人员，A0699（输出标识）没有初始化，都为null，需要根据a0196（专业技术职务）进行A0699的初始化
				String[] a0196s = a01.getA0196().split(",");
				for (int i = 0; i < a0196s.length; i++) {
					HBUtil.executeUpdate("update a06 set a0699 = 'true' where a0000 = '"+a0000+"' and a0602 = '"+a0196s[i]+"'");
					sess.flush();
				}
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/***********************************************专业技术职务(a06)*********************************************************************/
	
	/**
	 * 保存修改
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		A06 a06 = new A06();
		this.copyElementsValueToObj(a06, this);
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//专业技术资格不允许为空
		String a0601 = this.getPageElement("a0601").getValue();
		if(a0601 == null || "".equals(a0601)){
			this.setMainMessage("专业技术资格不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//专业技术任职资格名称，不可以有空格或其它非汉字字符
		String a0602 = this.getPageElement("a0602").getValue();
		if(a0602 != null || !"".equals(a0602)){
			if (a0602.indexOf(" ") >=0){
				this.setMainMessage("专业技术资格名称不能包含空格！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		a06.setA0000(a0000);
		String a0600 = this.getPageElement("a0600").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//资格获得时间早于出生年月
			String a0107 = a01.getA0107();//出生年月
			String a0604 = a06.getA0604();//资格获得时间
			if(a0604!=null&&!"".equals(a0604)&&a0107!=null&&!"".equals(a0107)){
				if(a0604.length()==6){
					a0604 += "00";
				}
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0604.compareTo(a0107)<0){
					this.setMainMessage("资格获得时间不能小于出生年月");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			if(a0600==null||"".equals(a0600)){
				applog.createLog("3061", "A06", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A06(), a06));
				a06.setA0699("true");
				String sql = "select max(sortid)+1 from a06 where a0000='"+a0000+"'";
				List<Object> sortid = sess.createSQLQuery(sql).list();//oracle:BigDecimal,mysql:BigInteger
				if(sortid.get(0)==null){
					a06.setSortid(1l);
				}else{
					a06.setSortid(Long.valueOf(sortid.get(0).toString()));
				}
				sess.save(a06);	
			}else{
				A06 a06_old = (A06)sess.get(A06.class, a0600);
				applog.createLog("3062", "A06", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a06_old, a06));
				PropertyUtils.copyProperties(a06_old, a06);
				sess.update(a06_old);	
			}
			sess.flush();
			updateA01(a0000);
			
			this.setMainMessage("保存成功!");
			/*this.setMessageType(EventMessageType.CONFIRM);
			addNextBackFunc(NextEventValue.YES, closeCueWindowEX());*/
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);//保存成功将id返回到页面上。
		getPageElement("a0607").setValue(a06.getA0607());
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('professSkillgrid').getStore().reload()");//刷新专业技术职务列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('worksort')");
		this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.close();";
	}
	private void updateA01(String a0000 ) throws AppException{
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
		
	}
	/**
	 * 专业技术职务列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a.* from A06 a where a0000='"+a0000+"' order by sortid";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 专业技术职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A06 a06 = new A06();
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		this.getPageElement("a0196").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 修改专业技术职务的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("professSkillgrid").getCueRowIndex();
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
			getPageElement("a0607").setValue(a06.getA0607());
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
	public int deleteRow(String a0600)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			A01 a01 = (A01)sess.get(A01.class, a06.getA0000());
			applog.createLog("3063", "A06", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A06(), new A06()));
			sess.delete(a06);
			this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
			sess.flush();
			updateA01(a06.getA0000());
			a06 = new A06();
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
			
			//CustomQueryBS.setA01(a01.getA0000());
	    	//A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
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
	 */
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
	}
	
	@SuppressWarnings("unchecked")
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
	}
	
}
