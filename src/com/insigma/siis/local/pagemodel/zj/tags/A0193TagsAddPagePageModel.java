package com.insigma.siis.local.pagemodel.zj.tags;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.AttrLrzw;
import com.insigma.siis.local.business.entity.extra.ExtraTags;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 历任重要职务重要经历
 * 
 * @author zhubo
 *
 */
public class A0193TagsAddPagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<AttrLrzw> list = sess.createQuery("from AttrLrzw where a0000='"+a0000+"'").list();
		AttrLrzw attribute = new AttrLrzw();
		if(list.size()!=0){
			attribute = list.get(0);
		}else{
			attribute.setA0000(a0000);
			sess.saveOrUpdate(attribute);
			sess.flush();
		}
		if(!"1".equals(attribute.getAttr2408())) {
			this.getExecuteSG().addExecuteCode(
					"$('#attr2409').attr('disabled','disabled')");
		}
		
		//this.getPageElement("attr2101").getValue(null);
		PMPropertyCopyUtil.copyObjValueToElement(attribute, this);  //将值赋值到页面
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA0193zInfo() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}

		try {
			HBSession session = HBUtil.getHBSession();
			StringBuilder a0193z = new StringBuilder();
			//Attribute attribute = new Attribute();
			AttrLrzw attribute = (AttrLrzw) session.createQuery("from AttrLrzw where a0000='"+a0000+"'").uniqueResult();
			
			A01 a01 = (A01) session.get(A01.class, a0000);
			boolean isAdd = true;
			AttrLrzw attribute_old = null;
			if(a01==null){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(attribute==null){//增加日志
				attribute = new AttrLrzw();
				attribute.setA0000(a0000);
			}else{//修改日志
				isAdd = false;
				attribute_old = attribute.clone();
			}
			this.copyElementsValueToObj(attribute, this);
						
			attribute.setA0000(a0000);
			session.saveOrUpdate(attribute);
			session.flush();
			/*if(isAdd){
				applog.createLogNew(a0000,"干部属性新增","ATTRIBUTEN",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new Attributen(),attribute));
			}else{
				applog.createLogNew(a0000,"干部属性修改","ATTRIBUTEN",a0000,a01.getA0101(), new Map2Temp().getLogInfo(attribute_old,attribute));
			}*/
			
			
			Map<String, String> attributeMap = Map2Temp.convertBean(attribute);
			for(String k : attrMap.keySet()){
				if(k.toUpperCase().replace("ATTR", "").length()==4){
					if("1".equals(attributeMap.get(k.toUpperCase()))){
						if(k.equals("attr2408")) {
							a0193z.append("任"+attribute.getAttr2409()+"的领导联系人"+"，");
						}else {
							a0193z.append(attrMap.get(k)+"，");
						}
					}
				}
				
			}
			if(a0193z.length()>0){
				a0193z.deleteCharAt(a0193z.length()-1);
			}
			ExtraTags extratags = (ExtraTags) session.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);
			}
			extratags.setA0193z(a0193z.toString());
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('a0193z').value='" + a0193z + "'");
			session.saveOrUpdate(attribute);
			session.saveOrUpdate(extratags);
			session.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	private String returnA0193z(String a0193, String a0193z) throws AppException {
		String a0193Name = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE",
				"CODE_TYPE='TAGZB131' and CODE_VALUE='" + a0193 + "'");
		a0193z += a0193Name + "；";
		return a0193z;

	}
	
	
	public static Map<String, String> attrMap = new LinkedHashMap<String, String>(){
		{
			put("attr2101", "区县市班子成员");
			put("attr2102", "乡镇街道班子");
			put("attr2103", "乡镇街道党政主要负责人");
			put("attr2104", "政法委或政法部门工作经历");
			put("attr2105", "法律工作经历五年以上");
			put("attr2106", "镇街团委书记或区县市团委班子成员");
			put("attr2107", "纪检监察部门");
			put("attr2108", "两年及以上基层工作经历");
			put("attr2201", "民营企业负责人");
			put("attr2202", "民营企业中层");
			put("attr2203", "外资企业中方负责人");
			put("attr2204", "外资企业外方代表");
			put("attr2205", "外资企业中层");
			put("attr2206", "国有企业负责人");
			put("attr2207", "国有企业部门负责人");
			put("attr2208", "事务所及其他社会组织");
			put("attr2209", "国际组织");
			put("attr2301", "正职");
			put("attr2302", "副职");
			put("attr2303", "部门正职");
			put("attr2304", "部门副职");
			put("attr2305", "二级学院党组织书记");
			put("attr2306", "二级学院院长");
			put("attr2307", "二级学院副职");
			put("attr2308", "校团委");
			put("attr2401", "海外学习经历");
			put("attr2402", "海外工作经历");
			put("attr2403", "聘任制公务员");
			put("attr2404", "公开选拔");
			put("attr2405", "大学生村官");
			put("attr2406", "团以下转业退伍");
			put("attr2407", "副师职以上军转");
			put("attr2408", "领导联系人");
			put("attr41", "网络安全与信息");
			put("attr42", "行政综合");
			put("attr43", "基层治理");
			put("attr44", "法律");
			put("attr45", "组织人事和党务");
			put("attr46", "经济金融（党政机关）");
			put("attr31", "企业经营管理");
			put("attr47", "企业金融投资");
			put("attr48", "财务审计");
			put("attr49", "巡视和纪检监察");
			put("attr50", "规划和工程基建");
			put("attr05", "统战");
			put("attr06", "政法");
			put("attr07", "群团");
			put("attr19", "科技");
			put("attr51", "生物医药卫生");
			put("attr52", "新闻媒体和宣传");
			put("attr13", "文化旅游");
			put("attr21", "教育");
			put("attr99", "其他");
			//put("a0000", "主键");

		}
	};
	
	

}
