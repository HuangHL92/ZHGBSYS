package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeTypeConvert;
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
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;;

public class AddPersonNewPageModel extends PageModel {
	/**
	 * 人员基本信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int savePerson()throws RadowException, AppException{
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		//出生日期改为日期格式
		//a01.setA0107(DateUtil.date2sqlDate(DateUtil.stringToDate(this.getPageElement("a0107").getValue(), "yyyymmdd")));
		//入党时间
		//a01.setA0144(DateUtil.date2sqlDate(DateUtil.stringToDate(this.getPageElement("a0144").getValue(), "yyyymmdd")));
		//参加工作时间
		//a01.setA0134(DateUtil.date2sqlDate(DateUtil.stringToDate(this.getPageElement("a0134").getValue(), "yyyymmdd")));
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(a0000==null||"".equals(a0000)){
				sess.save(a01);	
				//职务为空
				//A02 a02 = new A02();
				//a02.setA0201b("3307004");
				//a02.setA0201a("其它现职人员");
				//a02.setA0000(a01.getA0000());
				//sess.save(a02);
				//新增保存时父页面设置人员内码参数。eventParameter
				this.getExecuteSG().addExecuteCode("window.parent.radow.doEvent('tabClick','"+a01.getA0000()+"');");
			}else{
				sess.update(a01);	
			}					
			sess.flush();			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0000").setValue(a01.getA0000());//保存成功将id返回到页面上。		
		this.getExecuteSG().addExecuteCode("window.parent.parent.Ext.getCmp('persongrid').getStore().reload()");//刷新人员列表
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 人员其它信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveOthers.onclick")
	@Transaction
	@Synchronous(true)
	public int saveOthers()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//进入管理保存 id生成方式为assigned
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			sess.saveOrUpdate(a29);	
			
			//拟任免保存	id生成方式为uuid 。 如果是新增 将id设置为null
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
			}
			sess.saveOrUpdate(a53);
			this.getPageElement("a5300").setValue(a53.getA5300());
			
			//住址通讯保存 id生成方式为assigned
			A37 a37 = new A37();
			this.copyElementsValueToObj(a37, this);
			sess.saveOrUpdate(a37);	
			
			//离退保存 id生成方式为assigned
			A31 a31 = new A31();
			this.copyElementsValueToObj(a31, this);
			sess.saveOrUpdate(a31);	
			
			//退出管理保存 id生成方式为assigned
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			sess.saveOrUpdate(a30);	
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('persongrid').getStore().reload()");//刷新人员列表
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * a0184身份证号验证
	 * 
	 */
	@PageEvent("a0184.onblur")
	public int a0184onblur(String v)throws RadowException, AppException{
		String idcardno = this.getPageElement("a0184").getValue();
		if(idcardno==null || idcardno.equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!IdCardManageUtil.trueOrFalseIdCard(idcardno)){
			this.setMainMessage("身份证格式有误");
		}else{
			this.getPageElement("a0107").setValue(IdCardManageUtil.getBirthdayFromIdCard(idcardno));//出身年月
			this.getPageElement("a0104").setValue(IdCardManageUtil.getSexFromIdCard(idcardno));//性别
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//打开新增页面，检查是否有人员内码，如果有则是新增，否则是修改。
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A01 where a0000='"+this.getRadow_parent_data()+"'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);			
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			//进入管理加载
			A29 a29 = (A29)sess.get(A29.class, a01.getA0000());
			if(a29!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a29, this);
			}
			//拟任免加载
			String sqlA53 = "from A53 where a0000='"+a0000+"'";
			List listA53 = sess.createQuery(sqlA53).list();			
			if(listA53!=null&&listA53.size()>0){
				A53 a53 = (A53) listA53.get(0);	
				PMPropertyCopyUtil.copyObjValueToElement(a53, this);
			}	
			
			//住址通讯加载
			A37 a37 = (A37)sess.get(A37.class, a01.getA0000());
			if(a37!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a37, this);
			}
			
			//离退加载
			A31 a31 = (A31)sess.get(A31.class, a01.getA0000());
			if(a31!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a31, this);
			}
			
			//退出管理加载
			A30 a30 = (A30)sess.get(A30.class, a01.getA0000());
			if(a30!=null){
				PMPropertyCopyUtil.copyObjValueToElement(a30, this);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}	
		//this.setNextEventName("professSkillgrid.dogridquery");//专业技术职务列表
		//this.setNextEventName("degreesgrid.dogridquery");//学位学历列表
		//this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表
		//this.setNextEventName("RewardPunishGrid.dogridquery");//奖惩情况列表
		//this.setNextEventName("AssessmentInfoGrid.dogridquery");//年度考核情况列表
		//this.setNextEventName("FamilyRelationsGrid.dogridquery");//家庭主要成员及重要社会关系
		//this.setNextEventName("TrainingInfoGrid.dogridquery");//培训信息
		return EventRtnType.NORMAL_SUCCESS;
	}	
}
