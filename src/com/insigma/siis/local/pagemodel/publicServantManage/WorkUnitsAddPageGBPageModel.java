package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
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
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * 工作单位及职务新增修改页面
 * @author Administrator
 *
 */
public class WorkUnitsAddPageGBPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();//this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			HBSession sess = HBUtil.getHBSession();
			List<A02> list = sess.createQuery("from A02 where a0000='"+a0000+"'").list();
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		//a01中工作单位及职务
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
/*			Long a0194 = a01.getA0194();
			if(a0194!=null){
				this.getPageElement("a0194_y").setValue(a0194/12+"");
				this.getPageElement("a0194_m").setValue(a0194%12+"");
			}*/
			
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			
		/*	//统计关系所在单位 名称，获得父页面值
			if(a01.getA0195()!=null){
				String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
				if(a0195!=null){
					this.getPageElement("a0195_combo").setValue(a0195);//机构名称 中文。
				}
			}*/
			/*
			
			if(a01.getA0197() == null || a01.getA0197().equals("")){		//是否具有两年以上基层工作经历为空
				this.getExecuteSG().addExecuteCode("document.getElementById('a0197').checked=false;");
			}*/
			
			//如果任职任职机构的id为null或者"",则将id更新为-1（其他单位）
			//HBUtil.executeUpdate("update a02 set a0201b='-1' where a0000='"+a0000+"' and (a0201b='' or a0201b is null)");
			
			
			//如果统计所在单位页面显示
			/*if(a01.getA0195() != null && !a01.getA0195().equals("") && a01.getA0195().equals("-1")){	
				
				((Combo)this.getPageElement("a0195")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';"
						+ "document.getElementById('a0195_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("setParentA0194Value();");
		this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
		return EventRtnType.NORMAL_SUCCESS;
		/*String a0000 = this.getPageElement("subWinIdBussessId").getValue();//this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		//a01中工作单位及职务
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			Long a0194 = a01.getA0194();
			if(a0194!=null){
				this.getPageElement("a0194_y").setValue(a0194/12+"");
				this.getPageElement("a0194_m").setValue(a0194%12+"");
			}
			
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			
			//统计关系所在单位 名称，获得父页面值
			if(a01.getA0195()!=null){
				String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
				if(a0195!=null){
					this.getPageElement("a0195_combo").setValue(a0195);//机构名称 中文。
				}
			}
			
			
			if(a01.getA0197() == null || a01.getA0197().equals("")){		//是否具有两年以上基层工作经历为空
				this.getExecuteSG().addExecuteCode("document.getElementById('a0197').checked=false;");
			}
			
			//如果任职任职机构的id为null或者"",则将id更新为-1（其他单位）
			//HBUtil.executeUpdate("update a02 set a0201b='-1' where a0000='"+a0000+"' and (a0201b='' or a0201b is null)");
			
			
			//如果统计所在单位页面显示
			if(a01.getA0195() != null && !a01.getA0195().equals("") && a01.getA0195().equals("-1")){	
				
				((Combo)this.getPageElement("a0195")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';"
						+ "document.getElementById('a0195_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("setParentA0194Value();");
		this.getExecuteSG().addExecuteCode("setA0201eDisabled();");*/
	}
	
	
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveWorkUnits()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01)sess.get(A01.class, a0000);
		
		A02 a02 = new A02();
		this.copyElementsValueToObj(a02, this);
		/*if(a02.getA0200() == null || "".equals(a02.getA0200())){
			this.getExecuteSG().addExecuteCode("realParent.addResume('"+a02.getA0201a()+"','"+a02.getA0215a()+"','"+a02.getA0243()+"');");
		}*/
		
		
		/*if(!a01.getA0163().equals("1")){	//非现职人员
			//非现职人员不可有在任职务
			String msgf = "非现职人员不能有在任职务！";
			if(a02.getA0255() != null && a02.getA0255().equals("1")){
				this.setMainMessage(msgf);
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String sqlF = "from A02 where a0000='"+a0000+"' and A0255='1'";	//在任的职务
			List<A02> list = sess.createQuery(sqlF).list();
			if(list.size() != 0){
				this.setMainMessage(msgf);
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}*/
		
		
		String a0279 = a02.getA0279();			//主职务标识
		//判断主职务设置是否正确
		//规则：如果当前保存的职务不是主职务，则查看是否有主职务；如果是主职务，则将其他职务A0279更新为0
		/*if(a0279 == null || a0279.equals("0")){
			
			String sqlOne = null;
			String msg = null;
			//人员管理状态：1（现职人员）
			if(a01.getA0163().equals("1")){
				msg = "现职人员必须有一条在任主职务！";
				sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//在任、主职务的职务
				
				msg = "必须有一条主职务！";
				sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//在任、主职务的职务
				
			}else{			//非现职人员
				
				//msg = "非现职人员必须有一条主职务！";
				
				msg = "必须有一条主职务！";
				//sqlOne = "from A02 where a0000='"+a0000+"' and a0281='true' and a0279='1' order by a0223";//输出、主职务的职务
				sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//主职务的职务
			}
			
			
			List<A02> list = sess.createQuery(sqlOne).list();
			if(list.size() == 0){
				this.setMainMessage(msg);
				return EventRtnType.NORMAL_SUCCESS;
			}
		}*/
		if(a0279 != null && a0279.equals("1")){				//如果是主职务，则将其他职务a0279更新为0
			
			/*if(!a02.getA0255().equals("1")){
				//this.setMainMessage("现职人员必须有一条在任主职务！");
				
				this.setMainMessage("必须有一条主职务！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			HBUtil.executeUpdate("update a02 set a0279 = '"+0+"' where a0000 = '"+a0000+"'");
			sess.flush();
		}
		
		
		String a0201bb = this.getPageElement("a0201b").getValue();
		
		String a0201a = a02.getA0201a();
		//String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
		//a02.setA0201a(a0201a);
		
		

		String aa0201b = this.getPageElement("a0201b").getValue();
		if(aa0201b == null || "".equals(aa0201b)){
			this.setMainMessage("任职机构/工作机构 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String a0255 = this.getPageElement("a0255").getValue();
		if(a0255 == null || "".equals(a0255)){
			this.setMainMessage("该职务任职状态/工作状态 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String b0194 = this.getPageElement("b0194Type").getValue();
		/*if("1".equals(b0194)){*/
			String a0201d = a02.getA0201d();
			
			/*if(a0201d == null || "".equals(a0201d.trim()) || a0201d.equals("0")){
				this.setMainMessage("任职机构为法人单位时，是否班子成员必须选择！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//是否班子成员 为'是'时
			if("1".equals(a0201d)){
				String a0201e = this.getPageElement("a0201e_combo").getValue();//a0201e_combo
				if(a0201e == null || "".equals(a0201e.trim())){
					this.setMainMessage("领导成员 为'是'时，成员类别必须填写！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		/*}*/
		
		/*if("2".equals(b0194)){
			a02.setA0201d(null);
		}
		*/
		//职务名称：必须填写且不可以有空格或其它非汉字字符
		String a0215a = this.getPageElement("a0215a").getValue();
		if(a0215a == null || "".equals(a0215a)){
			this.setMainMessage("职务名称不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//对职务名称的规范性判断
		/*if(a0215a != null || !"".equals(a0215a)){
			if (a0215a.indexOf(" ") >=0){
				this.setMainMessage("职务名称不能包含空格！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//String reg = "[\\u4e00-\\u9fa5]+";//表示+表示一个或多个中文
			String reg = "[\u4E00-\u9FA5a-zA-Z0-9]+";	//只能包括数字，英文和中文
			
			String a0215a_reg=a0215a.replace(",", "").replace("、", "").replace("，", "");
			if(!a0215a_reg.matches(reg)){
				this.setMainMessage("职务名称输入格式不正确！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}*/
		
		//任职文号a0245
		String a0245 = this.getPageElement("a0245").getValue();
		if(a0245 != null || "".equals(a0245)){
			if(a0245.length() > 130){
				this.setMainMessage("任职文号不能超过130字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//免职文号a0267
		String a0267 = this.getPageElement("a0267").getValue();
		if(a0267 != null || "".equals(a0267)){
			if(a0267.length() > 24){
				this.setMainMessage("免职文号不能超过24字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//职务名称a0215a
		if(a0215a.length() > 50){
			this.setMainMessage("职务名称不能超过50字！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//1是，2否
		String aa0219 = a02.getA0219();
		if(aa0219 != null && "0".equals(aa0219)){
			a02.setA0219("2");
		}
		//免职时间不能早于任职时间
		String a0265 = a02.getA0265();//免职时间
		String a0243 = a02.getA0243();//任职时间
		if(a0265!=null&&!"".equals(a0265)&&a0243!=null&&!"".equals(a0243)){
			if(a0265.length()==6){
				a0265 += "00";
			}
			if(a0243.length()==6){
				a0243 += "00";
			}
			if(a0265.compareTo(a0243)<0){
				this.setMainMessage("免职时间不能早于任职时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		a02.setA0000(a0000);
		String a0200 = this.getPageElement("a0200").getValue();
		Boolean updateJGZW = false;
		//HBSession sess = null;
		try {
			
			String a0201b = a02.getA0201b();//任职机构编码。			
			//sess = HBUtil.getHBSession();
			
			//根据任职结构编码获取相同机构下职务的排序值。
			/*if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
				String sql="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sql).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
			}*/
			if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
				String sql="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
				
				Object sqlA0225 = sess.createSQLQuery(sql).uniqueResult();
				
				String maxA0225  =  null;
				if(sqlA0225 != null){
					maxA0225  = sqlA0225.toString();
				}
				
				if(maxA0225!=null && !maxA0225.equals("")){
					Long a0225 = Long.valueOf(maxA0225);
					a02.setA0225(a0225 + 1L);
				}else{		//该机构下第一次有任职人员，初始化a0225
					a02.setA0225(1L);
				}
				
				//如果是添加某人的，同机构下第N（N>1）条职务信息，则a0225需要和这个机构下其他职务的a0225一样(即某人同机构下所有的职务a0225一样)
				String sqlTwo="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sqlTwo).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
			}
			
			
			

			if(a0201bb!=null&&!"".equals(a0201bb)){//获取机构简称
				if("-1".equals(a0201bb)){//其它单位
					a02.setA0201c(a0201a);
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201bb);
					if(b01!=null){
						String a0201c = b01.getB0104();
						a02.setA0201c(a0201c);
					}
				}
				
				
			}
			
			if(a0200==null||"".equals(a0200)){
				
				if(a02.getA0255().equals("0")){
					a02.setA0281("false");//是否输出
					this.getPageElement("a0281").setValue("false");
				}else{
					a02.setA0281("true");//是否输出
					this.getPageElement("a0281").setValue("true");
				}
				
				
				applog.createLog("3021", "A02", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A02(), a02));
				sess.save(a02);	
				updateJGZW = true;
			}else{
				if(a02.getA0281()==null||"".equals(a02.getA0281())){
					
					if(a02.getA0255().equals("0")){
						a02.setA0281("false");//是否输出
						this.getPageElement("a0281").setValue("false");
					}else{
						a02.setA0281("true");//是否输出
						this.getPageElement("a0281").setValue("true");
					}
					
				}
				
				A02 a02_old = (A02)sess.get(A02.class, a0200);
				String jg_old = a02_old.getA0201a();//机构
				String jg =  a02.getA0201a();//机构
				if(jg_old!=null&&!jg_old.equals(jg)){
					updateJGZW = true;
				}else if(jg_old==null&&jg!=null){
					updateJGZW = true;
				}
				String zw_old = a02_old.getA0215a();//职务
				String zw =  a02.getA0215a();//职务
				
				if(zw_old!=null&&!zw_old.equals(zw)){
					updateJGZW = true;
				}else if(zw_old==null&&zw!=null){
					updateJGZW = true;
				}
				String rzzt_old = a02_old.getA0255();//任职状态
				String rzzt = a02.getA0255();//任职状态
				if(rzzt_old!=null&&!rzzt_old.equals(rzzt)){
					updateJGZW = true;
				}else if(rzzt_old==null&&rzzt!=null){
					updateJGZW = true;
				}
				
				String rzsj_old = a02_old.getA0243();//任职时间
				String rzsj = a02.getA0243();//任职时间
				if(rzsj_old!=null&&!rzsj_old.equals(rzsj)){
					updateJGZW = true;
				}else if(rzsj_old==null&&rzsj!=null){
					updateJGZW = true;
				}
				
				
				applog.createLog("3022", "A02", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a02_old, a02));
				String b0111 = a02_old.getA0201b();
				String b0111s = a02.getA0201b();
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111s);
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111);
				PropertyUtils.copyProperties(a02_old, a02);
				
				sess.update(a02_old);	
			}
			
			
			String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //输出职务
			List<A02> list = sess.createQuery(sqlOut).list();
			if(list.size() == 0){
				this.setMainMessage("必须要有一条输出职务");
				return EventRtnType.FAILD;
			}
			
			
			
			//更新名称
			String a0192a = this.getPageElement("a0192a").getValue();
			String a0192 = this.getPageElement("a0192").getValue();
			a01.setA0192a(a0192a);
			a01.setA0192(a0192);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			updateA01(a01,sess);
			sess.update(a01);
			sess.flush();
			
			
			//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
			HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
			HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");  
			
			
			//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//刷新工作单位及职务列表
			/*this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"window.realParent.ajaxSubmit('genResume');");*/
			
			//修改父页面的统计关系所在单位
			/*String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();*/
		/*	if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("realParent.setA0195Value('"+key+"','"+value+"');");
			}*/
			
			CustomQueryBS.setA01(a0000);
	    	A01 a01F = (A01)sess.get(A01.class, a0000);
			/*this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));*/
			
			
			
			if(a0279 == null || a0279.equals("0")){
				
				String sqlOne = null;
				String msg = null;
				//人员管理状态：1（现职人员）
				if(a01.getA0163().equals("1")){
					/*msg = "现职人员必须有一条在任主职务！";
					sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//在任、主职务的职务
*/					
					msg = "必须有一条主职务！";
					sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//在任、主职务的职务
					
				}else{			//非现职人员
					
					//msg = "非现职人员必须有一条主职务！";
					
					msg = "必须有一条主职务！";
					//sqlOne = "from A02 where a0000='"+a0000+"' and a0281='true' and a0279='1' order by a0223";//输出、主职务的职务
					sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//主职务的职务
				}
				
				
				List<A02> list2 = sess.createQuery(sqlOne).list();
				if(list2.size() == 0){
					this.setMainMessage(msg);
					return EventRtnType.FAILD;
				}
			}
			
			
			this.getPageElement("a0200").setValue(a02.getA0200());//保存成功将id返回到页面上。
			if(updateJGZW){
				this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否用本职务信息重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
						"if(id=='ok'){" +
							"radow.doEvent('UpdateTitleBtn.onclick');	" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}else{
				this.setMainMessage("保存成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void updateA01(A01 a01, HBSession sess){
		//更新a01 职务层次。 a0148 a0149     a02 a0221
		String sql="select a0221 from A02 a where a0000='"+a01.getA0000()+"' and a0255='1'";
		List<String> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Collections.sort(list, new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					if(o1==null||"".equals(o1)){
						return 1;
					}
					if(o2==null||"".equals(o2)){
						return -1;
					}
					return o1.compareTo(o2);
				}
			});
			
			a01.setA0148(list.get(0));
			/*//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0148').value='"+(a01.getA0148()==null?"":a01.getA0148())+"';");*/
		}else{
			//职务为空
			a01.setA0148("");
		/*	this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0148').value='';");*/
		}
		
	}
	
	
	public String reverse(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];

		return reverse;
	}
/** *********************************************工作单位及职务(a02)******************************************************************** */
	
	/**
	 * 工作单位及职务列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.dogridquery")
	@NoRequiredValidate
	public int workUnitsGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select * from a02 where a0000='"+a0000+"' order by lpad(a0223,5,'"+0+"') ";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 工作单位及职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsAddBtn.onclick")
	@NoRequiredValidate
	public int workUnitsWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A02 a02 = new A02();
		this.getPageElement("a0201b_combo").setValue("");
		
		PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		this.getPageElement("a0000").setValue(a0000);
		this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');document.getElementById('a0201d').checked=false;document.getElementById('a0251b').checked=false;"
				+ "document.getElementById('a0219').checked=false;document.getElementById('a0255').value='1';document.getElementById('a0279').checked=false;"
				+ "document.getElementById('a02551').checked=true;setA0201eDisabled();a0255SelChange()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 工作单位及职务修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int workUnitsGridOnRowClick() throws RadowException{ 
		//获取选中行index
		int index = this.getPageElement("WorkUnitsGrid").getCueRowIndex();
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();
		HBSession sess = null;
		try {
			
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			String a0219 = a02.getA0219();
			if(a0219 != null && "2".equals(a0219)){
				a02.setA0219("0");
			}
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
			setZB08Code(a02.getA0201b());
			
			//fujun
			/*if(a02.getA0201b() != null && !a02.getA0201b().equals("")){		//任职机构id不为空
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。
			}*/
			
			this.getPageElement("a0201a").setValue(a02.getA0201a());	//任职机构名称
			
			this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
			
			//任职状态,特殊处理
			if(a02.getA0255() != null && a02.getA0255().equals("0")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=false;document.getElementById('a02550').checked=true;a0255SelChange();");
			}
			if(a02.getA0255() != null && a02.getA0255().equals("1")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=true;document.getElementById('a02550').checked=false;a0255SelChange();");
			}
			
			
			//如果任职机构为-1，则页面不显示
			/*if(a02.getA0201b() != null && !a02.getA0201b().equals("") && a02.getA0201b().equals("-1")){	
				
				((Combo)this.getPageElement("a0201b")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201b').value='';"
						+ "document.getElementById('a0201b_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			
			//如果“主职务（a0279）”、“领导职务(a0219)”、“领导成员(a0201d)”、“破格提拔(a0251b)”数据不为1，则页面不勾选
			if(a02.getA0279() == null || !a02.getA0279().equals("1")){		//主职务为空
				this.getExecuteSG().addExecuteCode("document.getElementById('a0279').checked=false;");
			}
			
			if(a02.getA0219() == null || !a02.getA0219().equals("1")){		//领导职务为空
				this.getExecuteSG().addExecuteCode("document.getElementById('a0219').checked=false;");
			}
			
			if(a02.getA0201d() == null || !a02.getA0201d().equals("1")){		//领导成员
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201d').checked=false;");
			}
			
			if(a02.getA0251b() == null || !a02.getA0251b().equals("1")){		//破格提拔
				this.getExecuteSG().addExecuteCode("document.getElementById('a0251b').checked=false;");
			}
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 输出复选框选中事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("workUnitsgridchecked")
	@Transaction
	@NoRequiredValidate
	public int workUnitsgridchecked(String listString) throws RadowException {
		Map map = this.getRequestParamer();
		//String a0200 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		
		String[] listF = listString.split(",");
		
		String a0200 =  listF[0];
		String a0281 = listF[1];
		
		try{
			if(a0200!=null){
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02)sess.get(A02.class, a0200);
				/*Boolean checked = Boolean.valueOf(a02.getA0281());
				a02.setA0281(String.valueOf(!checked));*/
				
				a02.setA0281(a0281);
				sess.save(a02);
				PMPropertyCopyUtil.copyObjValueToElement(a02, this);
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。
				//this.getExecuteSG().addExecuteCode("a0222SelChange();");
				
				//刷新列表
				//this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
				
				String a0000 = this.getPageElement("a0000").getValue();
				String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //输出职务
				List<A02> list = sess.createQuery(sqlOut).list();
				if(list.size() == 0){
					//this.getPageElement("a0281").setValue("true");
					this.getExecuteSG().addExecuteCode("changebox();");
					this.setMainMessage("必须要有一条输出职务");
					return EventRtnType.FAILD;
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		
		//this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");
		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"  " +
					"}" +
				"});");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * 更新名称
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException {
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("a0000").getValue();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//职务名称
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
										
										
									}
									//简称
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
						}else{
							desc_full.put(key_full, value_full + "、" + zwmc);
						}
						
						//简称
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "、" +  zwmc);
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}else{//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(原"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(原"+ymjc+")";
			}
			//this.getPageElement("a0192a").setValue(zrqm+ymqm);
			//this.getPageElement("a0192").setValue(zrjc+ymjc);
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
		}else{
			//this.getPageElement("a0192a").setValue("");
			//this.getPageElement("a0192").setValue("");
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='';");
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
			
		}
		
		this.UpdateTimeBtn(id);
		this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表	
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * 更新名称对应的时间
	 * @return
	 * @throws RadowException
	 */
	@Transaction
	@NoRequiredValidate
	public int UpdateTimeBtn(String id) throws RadowException {
		
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("a0000").getValue();
		} catch (RuntimeException e) {
			
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				//jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				jgmcList.add("");
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//职务名称
				
				String zwrzshj = "";//职务任职时间
				if(a02.getA0243() != null && a02.getA0243().length() >= 6 && a02.getA0243().length() <= 8){
					zwrzshj = a02.getA0243().substring(0,4) + "." + a02.getA0243().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "、" + zwrzshj);
										
									}
									
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							//desc_full.put(key_full, zwmc+zwrzshj);//key 编码_$_机构名称_$_是否已免
							desc_full.put(key_full, zwrzshj);//key 编码_$_机构名称_$_是否已免
						}else{
							//desc_full.put(key_full, value_full + "、" + zwmc+zwrzshj);
							desc_full.put(key_full, value_full + "、" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "("+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "("+ymjc+")";
			}
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(zrqm+ymqm);
			sess.update(a01);
			
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
		}else{
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(null);
			sess.update(a01);
			
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a0200)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			
			A01 a01 = (A01)sess.get(A01.class, a02.getA0000());
			applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A02(), new A02()));
			sess.delete(a02);
			sess.flush();
			//更新人员状态
			updateA01(a01, sess);
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
			
			//刷新列表，并且更新简历信息
			/*this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"window.realParent.ajaxSubmit('genResume');");*/
			
			a02 = new A02();
			this.getPageElement("a0201b_combo").setValue("");
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
			
			CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
			
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		
		//提示是否更新全称、简称
		/*this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否用本职务信息重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");*/
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("worksort")
	@NoRequiredValidate
	@Transaction
	public int upsort(String id)throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("WorkUnitsGrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			int i = 0, j = 0;
			for(HashMap<String,String> m : list){
				String a0200 = m.get("a0200");//a02 id
				String a0255 = m.get("a0255");//任职 状态
				/*if("1".equals(a0255)){//在职
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}*/
				HBUtil.executeUpdate("update a02 set a0223="+ j++ +" where a0200='"+a0200+"'");
				
			}
			
			
			this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("sortUseTime")
	@NoRequiredValidate
	@Transaction
	public int sortUseTime(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "From A02 where a0000='"+a0000+"' order by a0243 desc ";//任职状态  任职时间降序
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			
			List<A02> list = sess.createQuery(sql).list();
			int i = 0,j = 0;
			if(list!=null&&list.size()>0){
				for(A02 a02 : list){
					String a0200 = a02.getA0200();//a02 id
					String a0255 = a02.getA0255();//任职 状态
					/*if("1".equals(a0255)){//在职
						HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
					}else{
						HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
					}*/
					HBUtil.executeUpdate("update a02 set a0223="+ j++ +" where a0200='"+a0200+"'");
				}
			}
			/*for(A02 a02 : list){
				String a0200 = a02.getA0200();//a02 id
				String a0255 = a02.getA0255();//任职 状态
				if("1".equals(a0255)){//在职
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}
			}*/
			this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("setZB08Code")
	@NoRequiredValidate
	@Transaction
	public int setZB08Code(String id)throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+id+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		
		if(B0194 != null && B0194.equals("3")){	
			
			String msg = "不可选择机构分组单位！";
			
			((Combo)this.getPageElement("a0201b")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','"+msg+"');document.getElementById('a0201b').value='';"
					+ "document.getElementById('a0201b_combo').value='';"
					);
			
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		if(B0194 != null && B0194.equals("2")){								//任职机构如果选择了内设机构，则任职状态和职务名称不可选
			
			/*this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','disabled');document.getElementById('a0201d').checked=false; "
					+ "document.getElementById('a0201e_combo').disabled=true;document.getElementById('a0201e_combo').style.backgroundColor='#EBEBE4';"
					+ "document.getElementById('a0201e_combo').style.backgroundImage='none';Ext.query('#a0201e_combo+img')[0].style.display='none';"
					+ "document.getElementById('a0201e').value='';document.getElementById('a0201e_combo').value='';document.getElementById('b0194Type').value='2';changea0201d(2);"
					);*/
			
			this.getExecuteSG().addExecuteCode(""
					+ "document.getElementById('a0201e_combo').disabled=true;document.getElementById('a0201e_combo').style.backgroundColor='#EBEBE4';"
					+ "document.getElementById('a0201e_combo').style.backgroundImage='none';Ext.query('#a0201e_combo+img')[0].style.display='none';"
					+ "document.getElementById('b0194Type').value='2';changea0201d(2);"
					);
			this.getExecuteSG().addExecuteCode("setA0201eDisabled()");
		}else{
			if(B0194 != null && B0194.equals("1")){
				this.getExecuteSG().addExecuteCode("changea0201d(1);document.getElementById('b0194Type').value='1';");
			}else{
				this.getExecuteSG().addExecuteCode("changea0201d(2);document.getElementById('b0194Type').value='3';");
			}
			/*this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');"
					+ "document.getElementById('a0201e_combo').readOnly=false;document.getElementById('a0201e_combo').disabled=false;"
					+ "document.getElementById('a0201e_combo').style.backgroundColor='#fff';"
					+ "Ext.query('#a0201e_combo+img')[0].style.display='block';"
					);*/
			
			this.getExecuteSG().addExecuteCode(""
					+ "document.getElementById('a0201e_combo').readOnly=false;document.getElementById('a0201e_combo').disabled=false;"
					+ "document.getElementById('a0201e_combo').style.backgroundColor='#fff';"
					+ "Ext.query('#a0201e_combo+img')[0].style.display='block';"
					);
			this.getExecuteSG().addExecuteCode("setA0201eDisabled()");
		}
		
		try {
			String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+id+"'");
			if(v!=null){
				this.getPageElement("a0201b_combo").setValue(v);
			}else{
				//this.getPageElement("a0201b_combo").setValue("");
			}
			/*
			 
			 String v = HBUtil.getValueFromTab("b0131", "B01", "b0111='"+id+"'");
			if(v!=null&&("1001".equals(v)||"1002".equals(v)||"1003".equals(v)||"1004".equals(v)
					||"1005".equals(v)||"1006".equals(v)||"1007".equals(v))){
				this.getPageElement("ChangeValue").setValue(v);
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" +
						"combo.show();");
			}else{
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" +
						"combo.setValue('');" +
						"document.getElementById('a0215a').value='';" +
						"combo.hide();");
			}
			 */
			
			
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		//var newStore = a0255f.getStore();
		//newStore.removeAll();
		//newStore.add(new Ext.data.Record({key:'1',value:'在任'}));
		//newStore.add(new Ext.data.Record(item.two));
		
		/*List<String> jsondata = CodeType2js.getCodeTypeList("ZB08", null, null);
		StringBuffer bf = new StringBuffer("var a0215a = Ext.getCmp('a0215a_combo');var newStore = a0215a.getStore();");
		bf.append("newStore.removeAll();");
		for(String s : jsondata){
			bf.append("newStore.add(new Ext.data.Record("+s+"));");
		}
		this.getExecuteSG().addExecuteCode(bf.toString());*/
		
		//将任职机构名称赋值到页面a0201a
		if(id != null && !id.equals("")){
			String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
			this.getPageElement("a0201a").setValue(a0201a);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/*//检查统计关系所在单位是否为“内设机构”
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		if(B0194 == null){
			B0194 = "";
		}
		
		if(B0194 != null && B0194.equals("2") || B0194.equals("3")){
			
			String msg = "不可选择内设机构单位！";
			if(B0194.equals("3")){
				msg = "不可选择机构分组单位！";
			}
			
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','"+msg+"');document.getElementById('a0195').value='';"
					+ "document.getElementById('a0195key').value = '';document.getElementById('a0195value').value = '';document.getElementById('a0195_combo').value='';"
					);
		}else{
			//修改父页面的统计关系所在单位
			String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("realParent.setA0195Value('"+key+"','"+value+"');");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	
	@PageEvent("a0201bChange")
	@NoRequiredValidate
	public int a0201bChange(String a0201b) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0201b+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();				//机构类型
		
		//没有职务信息时，做联动
		int num = this.getPageElement("WorkUnitsGrid").getStringValueList().size();
		if(num == 0){
			
			/*String a0195 = this.getPageElement("a0195").getValue();		//统计所在单位
*/			
			
				if(B0194 != null && !B0194.equals("2")){				//如果“任职机构”为内设机构，则不作联动
					
					
					/*if((a0195 != null && a0195.equals("")) || num == 0){			//如果“统计所在单位”已经存在，则不作联动
					
						this.getPageElement("a0195").setValue(a0201b);
						String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+a0201b+"'");
						if(v!=null){
							this.getPageElement("a0195_combo").setValue(v);
							this.getPageElement("a0195value").setValue(v);
						}else{
							this.getPageElement("a0195_combo").setValue("");
							this.getPageElement("a0195value").setValue("");
						}
						
						
						this.getPageElement("a0195key").setValue(a0201b);
						
						this.a0195Change(a0201b);
						
					}*/
				
				}else if(B0194.equals("2")){		//如果为内设机构则将上一级法人单位赋值给：统计所在单位
					
					//获取机构树结构
					List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
					
					Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
					for(Object[] treedata : listres){
						TreeNode rootnode = genNode(treedata);
						nodemap.put(rootnode.getId(), rootnode);
					}
					
					TreeNode bcn = nodemap.get(a0201b);
					
					//String sql = "select B0121 from B01 where  B0111 ='"+a0201b+"'";
					String b0194 = "";
					if(bcn != null){
						b0194 = bcn.getText();
						
						
						while(true){
							TreeNode cn = nodemap.get(a0201b);
							TreeNode n = nodemap.get(cn.getParentid());
							if(n == null){
								throw new RadowException("机构读取异常");
							}
							if(n.getText() == null||"".equals(n.getText())){
								throw new RadowException("机构读取异常");
							}
							a0201b = n.getId();
							if(!"1".equals(n.getText())){//不是法人单位,继续往上找机构
								continue;
							}else{			//法人单位
								
								String B0111 = n.getId();			//机构id
								String B0101 = n.getLink(); 		//机构名称
								
								
								/*this.getPageElement("a0195").setValue(B0111);
								this.getPageElement("a0195key").setValue(B0111);
								this.getPageElement("a0195_combo").setValue(B0101);
								this.getPageElement("a0195value").setValue(B0101);*/
								
								/*this.a0195Change(B0111);*/
								
								return EventRtnType.NORMAL_SUCCESS;
							}
						}
						
					}else{
						throw new RadowException("机构读取异常");
					}
					
				}else {
					/*this.getPageElement("a0195").setValue("");
					this.getPageElement("a0195_combo").setValue("");
					
					this.getPageElement("a0195key").setValue("");
					this.getPageElement("a0195value").setValue("");
					this.a0195Change("");*/
				}
			
			
			
		}
				
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*//判断统计机构关系是否合理
	@PageEvent("check")
	@NoRequiredValidate
	public int check() throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String a0195 = this.getPageElement("a0195").getValue();
		String a0201b = this.getPageElement("a0201b").getValue();
		
		if(a0195 == null || "".equals(a0195)){
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
				+ " '提示', "
				+ " '统计关系所在单位为空，是否继续保存？', "
				+ " function (btn){ "
				+ "	if(btn=='yes'){ "
				+ "		radow.doEvent('save'); "
				+ "	} "
				+ "} "
			    + ");");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//获取机构树结构
		List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
		
		Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
		for(Object[] treedata : listres){
			TreeNode rootnode = genNode(treedata);
			nodemap.put(rootnode.getId(), rootnode);
		}
		TreeNode bcn = nodemap.get(a0201b);
		String b0194 = "";
		if(bcn != null){
			b0194 = bcn.getText();
			if(!"2".equals(b0194)){
				this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
						+ " '提示', "
						+ " '统计关系所在单位和任职机构名称不相同，是否继续保存？', "
						+ " function (btn){ "
						+ "	if(btn=='yes'){ "
						+ "		radow.doEvent('save'); "
						+ "	} "
						+ "} "
					    + ");");
					return EventRtnType.NORMAL_SUCCESS;
			}else{
				while(true){
					TreeNode cn = nodemap.get(a0201b);
					TreeNode n = nodemap.get(cn.getParentid());
					if(n == null){
						throw new RadowException("机构读取异常");
					}
					if(n.getText() == null||"".equals(n.getText())){
						throw new RadowException("机构读取异常");
					}
					a0201b = n.getId();
					if(!"1".equals(n.getText())){//不是法人单位,继续往上找机构
						continue;
					}else{//法人单位
						if(a0195.equals(n.getId())){
							this.getExecuteSG().addExecuteCode("radow.doEvent('save');");
							return EventRtnType.NORMAL_SUCCESS;
						}else{
							this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
									+ " '提示', "
									+ " '统计关系所在单位和任职机构名称不相同，是否继续保存？', "
									+ " function (btn){ "
									+ "	if(btn=='yes'){ "
									+ "		radow.doEvent('save'); "
									+ "	} "
									+ "} "
								    + ");");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}else{
			throw new RadowException("机构读取异常");
		}
	}*/
	
	private static TreeNode genNode(Object[] treedata) {
		TreeNode node = new TreeNode();
		node.setId(treedata[0].toString());
		node.setText(treedata[1].toString());
		node.setLink(treedata[3].toString());
		node.setLeaf(true);
		if(treedata[2]!=null)
			node.setParentid(treedata[2].toString());
		node.setOrderno((short)1);

		return node;
	}
	private static void genTree(Map<String, TreeNode> nodemap) {
		for(String key : nodemap.keySet()){
			TreeNode node = nodemap.get(key);
			if(!"-1".equals(node.getParentid())){
				String a = node.getParentid();
				/*TreeNode node2 =  nodemap.get(node.getParentid());
				if(node2!=null){
					node2.addChild(node);
				}*/
			}
		}
	}
}
