package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.PageEvents;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.VVerifyColVsl006;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.VerifySqlList;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.VerifyRuleDTO;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.VerifySchemeDTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 校验规则配置窗口
 * @author mengl
 *
 */
public class RuleSqlListPageModel extends PageModel{
	
	@PageEvent("btnClose.onclick")
	@NoRequiredValidate
	public int btn1Onclick() throws RadowException {
		String vsc001Str = this.getPageElement("vsc001").getValue();
		this.closeCueWindow("addwin");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('verifyRuleGrideload','"+vsc001Str+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**点击保存触发：
	 * 1.保存 校验规则表（verify_rule）,并与校验方案表（verify_scheme）的主键关联
	 * 2.保存 校验SQL语句表（verify_sql_list）,并与校验规则表（verify_rule）的主键关联
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvents({"btnSave.onclick","btnSaveContinue.onclick"})
	@NoRequiredValidate
	@Transaction
	public int btnSaveOnclick() throws RadowException, AppException {
		String eleName = this.getCueEventElementName();
		String alertMsg = "保存成功！";
		
		String operateType = null; //操作类型（记录日志使用）
		VerifyRuleDTO vrDTO = new VerifyRuleDTO();//校验规则数据传输对象（记录日志使用）
		List<VerifySqlList> vslOldList = new LinkedList<VerifySqlList>();//校验规则详细配置（记录日志使用）
		List<VerifySqlList> vslNewList = new LinkedList<VerifySqlList>();//校验规则详细配置（记录日志使用）
		
		//0. 校验
		
		String vru002 = this.getPageElement("vru002").getValue();
		String vru003 = this.getPageElement("vru003").getValue();
		List<HashMap<String, Object>>  gridList = this.getPageElement("grid").getValueList();
		if(StringUtil.isEmpty(vru002) || StringUtil.isEmpty(vru003) || gridList==null || gridList.size()<1 || gridList.get(0)==null){
			throw new AppException("保存错误！异常信息：校验规则名称、校验类型、校验规则列表均不能为空！");
		}
		
		HBSession sess = HBUtil.getHBSession();
		String vsc001Str = this.getPageElement("vsc001").getValue()==null?"":this.getPageElement("vsc001").getValue();//校验方案表（verify_scheme）的主键
		String vru001Str = this.getPageElement("vru001").getValue()==null?"":this.getPageElement("vru001").getValue();//校验规则表（verify_rule）的主键
		if(StringUtil.isEmpty(vsc001Str)){
			throw new AppException("未获取到对应校验方案信息！");
		}
		try {
			PageElement pe = this.getPageElement("grid");
			VerifyRule verifyRule = null;
			
			//1.保存 校验规则表（verify_rule）
			
				//1.1 判定新增或修改
			
			if(StringUtil.isEmpty(vru001Str)){
			//新增
				verifyRule =  new VerifyRule();
				verifyRule.setVru007("0");//有效(启用)标记：  1-启用；0-未启用
				
				operateType = "638";
			}else{
			//修改
				verifyRule = (VerifyRule) sess.get(VerifyRule.class, vru001Str);
				//verifyRule.setVru007(this.getPageElement("vru007").getValue());//有效(启用)标记：  1-启用；0-未启用
				verifyRule.setVru007("0");
				operateType = "639";
				BeanUtil.copy(verifyRule, vrDTO);
			}
			
				//1.2 赋值保存
			
//			this.copyElementsValueToObj(verifyRule, this);
			String vru008 = this.getPageElement("vru008").getValue();
			String vru004 = this.getPageElement("vru004").getValue();
			String vru005 = this.getPageElement("vru005").getValue();
			if(StringUtil.isEmpty(vru004)||StringUtil.isEmpty(vru005)){
				throw new AppException("保存校验规则时请重新选择【校验信息集】【校验信息项】作为该校验规则主要校验的信息项保存！");
			}
			
			verifyRule.setVru002(this.getPageElement("vru002").getValue());
			verifyRule.setVru003(this.getPageElement("vru003").getValue());
			verifyRule.setVru004(vru004);
			verifyRule.setVru005(vru005);
			verifyRule.setVru006(this.getPageElement("vru006").getValue());
			verifyRule.setVsc001(vsc001Str);
			verifyRule.setVru008((vru008!=null?vru008:"")+verifyRule.getVru002());//返回信息
			verifyRule.setVru009(this.getPageElement("vru009").getValue());//清空拼接好的sql语句
			sess.saveOrUpdate(verifyRule);
			vru001Str = verifyRule.getVru001();
			this.getPageElement("vru001").setValue(vru001Str);
			
			//2. 保存 校验SQL语句表（verify_sql_list）
			
				//2.1 删除该规则方案对应的原校验SQL语句表
			
			String sql = " from VerifySqlList where vru001 = '"+vru001Str+"' ";
			@SuppressWarnings("unchecked")
			List<VerifySqlList> verifySqlLists = sess.createQuery(sql).list();
			for(VerifySqlList verifySqlList:verifySqlLists){
				sess.delete(verifySqlList);
				
				//记录日志使用
				vslOldList.add(verifySqlList);
			}
			
				//2.2 保存Grid中校验SQL语句表
			
			List<HashMap<String, Object>> list = pe.getValueList();
			Field[] fields = VerifySqlList.class.getDeclaredFields();
			long vsl002Count = 0; //左括号数
			long vsl011Count = 0; //右括号数
			for(int i=0;i<list.size();i++){
				VerifySqlList vsl = new VerifySqlList();
				vsl.setVsl014(Long.valueOf(i+1));
				HashMap<String, Object> map = list.get(i);
				Object vsl002 = map.get("vsl002");
				Object vsl011 = map.get("vsl011");
				vsl002Count = vsl002Count + ((vsl002==null||StringUtil.isEmpty(vsl002.toString()))?0L:Long.valueOf(vsl002.toString()));
				vsl011Count = vsl011Count + ((vsl011==null||StringUtil.isEmpty(vsl011.toString()))?0L:Long.valueOf(vsl011.toString()));
				for(Field field : fields){
					String fieldName = field.getName();
					Object fieldValue = map.get(fieldName);
					
					if(map.containsKey(fieldName) && fieldValue!=null && !StringUtil.isEmpty(fieldValue.toString())){
						Method setMethod = VerifySqlList.class.getDeclaredMethod("set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1), field.getType());
						//参数类型异常，通过转化为调用对应对象的String参数构造方法
						try {
							setMethod.invoke(vsl, fieldValue);
						} catch (IllegalArgumentException e) {
							fieldValue = field.getType().getConstructor(String.class).newInstance(fieldValue.toString());
							setMethod.invoke(vsl, fieldValue);
						}
					}
				}
				vsl.setVru001(vru001Str);
				sess.save(vsl);
				
				vslNewList.add(vsl);
			}
			if(vsl002Count!=vsl011Count){
				throw new AppException("拼接的校验规则条件左右括号数不相等！");
			}
			
			//3.记录日志
				//获取改变信息
			List<String[]> changesList = Map2Temp.getLogInfo(vrDTO, verifyRule);
			
			VerifySqlList vslNew = new VerifySqlList();//空对象，新对象比对为空时使用
				//循环次数，取最大值
			int size = vslOldList.size()>vslNewList.size()?vslOldList.size():vslNewList.size();
			for(int i = 0;i<size;i++){
				//超过List范围，则赋值为空对象来比较
				VerifySqlList odlVsl = vslOldList.size()>i && vslOldList.get(i)!=null?vslOldList.get(i):vslNew;
				VerifySqlList newVsl = vslNewList.size()>i && vslNewList.get(i)!=null?vslNewList.get(i):vslNew ;
				changesList.addAll( Map2Temp.getLogInfo(odlVsl,newVsl));
			}
			
				//排除Verify_sql_list主键改变因素
			Iterator<String[]> it = changesList.iterator();
			while(it.hasNext()){
				String[] changeItem = it.next();
				if(changeItem!=null && changeItem.length>0 && "VSL001".equalsIgnoreCase(changeItem[0])){
					it.remove();
				}
			}
			
				//校验规则有变化，则设置方案未启用并记录日志
			if(changesList!=null && changesList.size()>0){
				VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001Str);
				VerifySchemeDTO vsDTO = new VerifySchemeDTO();
				BeanUtil.copy(vs, vsDTO);
				/*vs.setVsc003("0");//0-无效（未启用）
				sess.update(vs);*/
				changesList.addAll( Map2Temp.getLogInfo(vsDTO, vs));
				try {
					new LogUtil().createLog(operateType, "VERIFY_RULE", vru001Str, verifyRule.getVru002(), null, changesList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//此处不能抛出异常，verify_sql_list删除掉重新生成了，需要重新提交
				alertMsg = "保存成功！";
			}
			
			sess.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new AppException("保存失败！异常信息："+e1.getMessage());
		}
		
		//4. 点击【保存】按钮：关闭校验规则添加窗口，父页面提示保存成功； 点击【保存继续】按钮：提示保存成功，刷新  校验规则添加窗口，继续为该校验方案添加校验规则
		
		if("btnSave".equals(eleName)){
			this.closeCueWindow("addwin");
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('success','"+vsc001Str+","+alertMsg+"')");
		}else if("btnSaveContinue".equals(eleName)){
			//this.setMainMessage(alertMsg);
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('success','"+vsc001Str+","+alertMsg+"')");
			this.setNextEventName("btnReload.onclick");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 信息配置修改的按钮事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("loadadd.onclick")
	@GridDataRange
	public int loadonClick() throws RadowException{  
		this.setRadow_parent_data(this.getPageElement("grid").getValue("modelobjid",this.getPageElement("grid").getCueRowIndex()).toString());
		this.openWindow("addwin", "pages.modeldb.mscript.LoadScipt");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	/**
	 * 新增条件到列表
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvents({"savecond.onclick","modefyCond.onclick"})
	@GridDataRange
	@AutoNoMask
	public int savecondonClick() throws RadowException, AppException{  
		PageElement pe = this.getPageElement("grid");
		String eleName = this.getCueEventElementName();
		if(StringUtil.isEmpty(eleName)){
			throw new AppException("程序异常，请联系开发商：调用savecondonClick()方法请通过事件调用！");
		}
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		//修改按钮时，才判断是否选中某行
		if("modefyCond".equals(eleName)){
			if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
				throw new AppException("请先选中一行！");
			}else{
				cueRowIndex = Integer.parseInt(cueRowIndexStr);
			}
		}
		
		String vsl007= this.getPageElement("vsl007").getValue();
		String vsl013= this.getPageElement("vsl013").getValue();
		String vsl009= this.getPageElement("vsl009").getValue();
		String vsl008= this.getPageElement("vsl008").getValue();
		String vsl006= this.getPageElement("vsl006").getValue();//运算操作符
		String vru004= this.getPageElement("vru004").getValue();
		String vru005= this.getPageElement("vru005").getValue();
		List<HashMap<String, Object>> list = pe.getValueList();
		
		//1. 校验
			//1.1 前台验证必输项，后台也添加验证项
		if(StringUtil.isEmpty(vsl006) || StringUtil.isEmpty(vru004) || StringUtil.isEmpty(vru005)){
			throw new AppException("校验信息集、校验信息项、运算符均不能为空！");
		}
			//1.2 比对信息集、比对信息项 必须同时存在
		if((StringUtil.isEmpty(vsl008) && !StringUtil.isEmpty(vsl009)) || (StringUtil.isEmpty(vsl009) && !StringUtil.isEmpty(vsl008))){
			throw new AppException("比对信息集、比对信息项 必须同时为空或同时不为空！");
		}
		
		CodeValue codeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
		if(codeValue==null){
			throw new AppException("获取操作运算符参数异常！异常信息：操作符ID-"+vsl006);
		}
		String subCodeValue = codeValue.getSubCodeValue();
		
			//1.3	① 操作符为“空运算符（subCodeValue=3）（【非空】或【为空】）”，固定取值、选择取值、比对信息项应该均为空；
			//		②若操作符为“比对信息项与固定值相加计算类运算符（subCodeValue=5）”，固定取值、比对信息项均不能为空,选择取值应该为空
			//		③若操作符为“系统日期与固定值相加计算类运算符（subCodeValue=6）”，固定取值不能为空，且选择取值、比对信息项应该均为空；
			//		④操作符不是“空运算符”和“相加计算类运算符”，固定取值、选择取值、比对信息项有且仅有任意一项不为空
		 if("3".equals(subCodeValue)){
			if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("操作符为【非空】或【为空】，固定取值、选择取值、比对信息项应该均为空！");
			}
		}else if("5".equals(subCodeValue)){
			if(StringUtil.isEmpty(vsl009) || StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("操作符为“比对信息项与固定值相加计算类运算符”，固定取值、比对信息项均不能为空,选择取值应该为空！");
			}
		}else if("6".equals(subCodeValue)){
			if(!StringUtil.isEmpty(vsl009) || StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("操作符为“系统日期与固定值相加计算类运算符”，固定取值不能为空，且选择取值、比对信息项应该均为空！");
			}
		}else if("7".equals(subCodeValue)){
			if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("操作符为“正则表达式运算符”，固定取值、选择取值、比对信息项应该均为空！");
			}
		}else if("8".equals(subCodeValue)){
			if("80".equals(codeValue.getCodeValue())||"81".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if("B01".equalsIgnoreCase(vru004)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，不能对单位基本信息使用！");
				}
			}
		}else if("9".equals(subCodeValue)){
			if("99".equals(codeValue.getCodeValue()) || "98".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0184")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【身份证号】！");
				}
			}
			if("90".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0195")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【统计关系所在单位】！");
				}
			}
			/*if("91".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a2911")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【进入本单位变动类别】！");
				}
			}*/
			if("96".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0281")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【输出职务标识】！");
				}
			}
			if("93".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0184")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【公民身份证号码】！");
				}
			}
			if("902".equals(codeValue.getCodeValue())||"932".equals(codeValue.getCodeValue())||"933".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0184")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【公民身份证号码】！");
				}
			}
			if("91".equals(codeValue.getCodeValue())||"934".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("b0114")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【法人单位编码(内设机构编码)】！");
				}
			}
			if("92".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0195")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【统计关系所在单位】！");
				}
			}
			if("901".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0160")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【人员类别】！");
				}
			}
			if("903".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("B0150")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【内设机构领导职数】！");
				}
			}
			if("908".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A1701")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【简历】！");
				}
			}
			if("909".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0195")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【统计所在关系】！");
				}
			}
			if("925".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0243")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【任职时间】！");
				}
			}
			if("921".equals(codeValue.getCodeValue())||"926".equals(codeValue.getCodeValue())||"927".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0501B")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【职务层次（职级层次）】！");
				}
			}
			if("929".equals(codeValue.getCodeValue())||"930".equals(codeValue.getCodeValue())||"931".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A15Z101")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【年度考核结果综述】！");
				}
			}
			if("938".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值、选择取值、比对信息项应该均为空！");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0215A")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【职务名称】！");
				}
			}	
			if("941".equals(codeValue.getCodeValue())){
				if(StringUtil.isEmpty(vsl007)){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，固定取值不应为空！");
				}
				/*if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0215A")){
					throw new AppException("操作符为“"+codeValue.getCodeName()+"”，对应校验信息项只能为【职务名称】！");
				}*/
			}	
		}else{
			int countNotNull = 0;
			if(!StringUtil.isEmpty(vsl007)){//固定值
				countNotNull++;
			}
			if(!StringUtil.isEmpty(vsl013)){//选择值
				countNotNull++;
			}
			if(!StringUtil.isEmpty(vsl009)){//比对信息项
				countNotNull++;
			}
			//姓名 籍贯 出生地判断是否有空格 可以输入空格
			if(countNotNull!=1){
				if(vsl006.equals("11") && (vru005.equalsIgnoreCase("a0111a") || vru005.equalsIgnoreCase("a0114a") || vru005.equalsIgnoreCase("a0101"))){
					
				}else{
					throw new AppException("操作符不是“空运算符”和“相加计算类运算符”，固定取值、选择取值、比对信息项有且仅有任意一项不为空！");
				}
				
			}
		}
		
		//2. grid为空时点击保存数据，设定校验信息集（vru004）校验信息项（vru005）不可修改
		//（取消该功能） mengl 20160426
		/*if(list==null || list.size()<1 || list.isEmpty()){
			this.getPageElement("vru004").setDisabled(true);
			this.getPageElement("vru005").setDisabled(true);
		}*/
		
		//3. 新加脚本到列表中
		HashMap<String, Object> map = new HashMap<String, Object>();
		VerifySqlList vsl = new VerifySqlList();
		this.copyElementsValueToObj(vsl, this);
		
		//3.1通用对象拷贝到Map中
		for(Field field :VerifySqlList.class.getDeclaredFields()){
			Method method;
			try {
				method = VerifySqlList.class.getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1));
				map.put(field.getName(),method.invoke(vsl)==null?"":method.invoke(vsl).toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException("取值出错");
			}
		}
		
		//3.2 特殊显示值赋值，不能使用codeType导致，只能这样显示
		RuleSqlListBS.getCodeNameVerifySqlList(map, vsl);
		if("A3627".equals(vsl.getVsl004().toUpperCase())){
			map.put("vsl013", map.get("vsl013_name"));
		}
		list.add(map);
		
		//3.3 判断不同操作:savecond-新增；modefyCond-修改选中行
		if("savecond".equals(eleName)){
			pe.setValueList(list);
			this.getPageElement("cueRowIndex").setValue("");//新增数据后，选中行清空
		}else if("modefyCond".equals(eleName)){
			((Grid)pe).updateRowData(cueRowIndex, map);//更新该行数据
		}
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 删除一行数据后统计Grid总行数，若总行数为0，设定校验信息集（vru004）校验信息项（vru005）可修改
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("delCount")
	@NoRequiredValidate
	public int delCount() throws RadowException{
		List<HashMap<String, Object>> list = this.getPageElement("grid").getValueList();
		if(list==null || list.size()<1 || list.isEmpty()){
			this.getPageElement("vru004").setDisabled(false);
			this.getPageElement("vru005").setDisabled(false);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 全部删除Grid，设定校验信息集（vru004）校验信息项（vru005）可修改
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("delAll.onclick")
	@NoRequiredValidate
	public int delAll() throws RadowException{
		/*this.createPageElement("vru004", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("vru005", ElementType.SELECT, false).setDisabled(false);*/
		/*this.getPageElement("vru004").setDisabled(false);
		this.getPageElement("vru005").setDisabled(false);*/
		this.getPageElement("grid").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 设定选中行号到页面上,并对选中行回显到编辑页面
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("grid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setCueRow() throws RadowException, AppException{
		PageElement grid = this.getPageElement("grid"); 
		int cueRowIndex = grid.getCueRowIndex();
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		
		String vru004 = (String)grid.getValue("vsl003");
		String vru005 = (String)grid.getValue("vsl004");
		String vsl006 = (String)grid.getValue("vsl006");
		String vsl007 = (String)grid.getValue("vsl007");
		String vsl013 = (String)grid.getValue("vsl013");
		String vsl008 = (String)grid.getValue("vsl008");
		String vsl009 = (String)grid.getValue("vsl009");
		String vsl012 = (String)grid.getValue("vsl012");
		
		//校验信息集
		this.getPageElement("vru004").setValue(vru004);
		this.getPageElement("vsl003").setValue(vru004);
		
		//信息集onchange事件
		this.vru004Change();
		
		//校验信息项
		this.getPageElement("vru005").setValue(vru005);
		this.getPageElement("vsl004").setValue(vru005);
		
		//信息项onchange事件
		this.vru005Change();
		
		//运算符
		this.getPageElement("vsl006").setValue(vsl006);
		
		//运算符onchange事件
		vsl006Onchange();
		
		this.getPageElement("vsl007").setValue(vsl007);
		this.getPageElement("vsl013").setValue(vsl013);
		this.getPageElement("vsl008").setValue(vsl008);
		
		//比对信息集onchange事件
		vsl008Change();
		
		this.getPageElement("vsl009").setValue(vsl009);
		this.getPageElement("vsl012").setValue(vsl012);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行增减括号
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvents({"addLeft.onclick","addRight.onclick","delLeft.onclick","delRight.onclick"})
	@AutoNoMask
	@NoRequiredValidate
	public int bracketsModify() throws RadowException, AppException{
		String eleName = this.getCueEventElementName();
		if(StringUtil.isEmpty(eleName)){
			throw new AppException("程序异常，请联系开发商：调用bracketsModify()方法请通过事件调用！");
		}
		Grid pe = (Grid) this.getPageElement("grid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
		}
		List<HashMap<String, Object>> list = pe.getValueList();
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==cueRowIndex){
				HashMap<String,Object> map = list.get(i);
				Object vsl002 = map.get("vsl002");//左括号数
				Object vsl011 = map.get("vsl011");//右括号数
				int left = (vsl002==null||vsl002.toString().trim().equals(""))?0:Integer.parseInt(vsl002.toString());
				int right = (vsl011==null||vsl011.toString().trim().equals(""))?0:Integer.parseInt(vsl011.toString());
				//1.添加左括号
				if(eleName.equals("addLeft")){
					if(left<5){
						map.put("vsl002", left+1);
					}else{
						throw new AppException("一行语句最多添加5个左括号！");
					}

				//2.添加右括号
				}else if(eleName.equals("addRight")){
					if(right<5){
						map.put("vsl011", right+1);
					}else{
						throw new AppException("一行语句最多添加5个右括号！");
					}
					
				//3.删除左括号
				}else if(eleName.equals("delLeft")){
					if(left>0){
						map.put("vsl002", left-1);
					}
				//4.删除右括号
				}else if(eleName.equals("delRight")){
					if(right>0){
						map.put("vsl011", right-1);
					}
				}
				newList.add(map);
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex);//选中当前操作行
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行提升一行
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("upRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int upRow() throws RadowException, AppException{
		Grid pe = (Grid) this.getPageElement("grid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("已经是第一行，不能上移！");
			}
		}
		List<HashMap<String, Object>> list = pe.getValueList();
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex-1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex-1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex-1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex-1+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行降低一行
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("downRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int downRow() throws RadowException, AppException{
		Grid pe = (Grid) this.getPageElement("grid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(list.size()-1)){
				throw new AppException("已经是最后行，不能下移！");
			}
		}
		
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex+1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex+1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex+1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+1+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	


	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("grid.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery(int start,int limit) throws RadowException{
		String vru001 = this.getPageElement("vru001").getValue();
		String sql = "";
		StringBuffer sqlBf = null;
		sqlBf = new StringBuffer("Select Vsl001,                                                                   ")
						.append("       Vru001,                                                                    ")
						.append("       Vsl002,                                                                    ")
						.append("       (Select Table_Name From Code_Table Where Table_Code = Vsl003) Vsl003_Name, ")
						.append("       (Select Col_Name                                                           ")
						.append("          From Code_Table_Col                                                     ")
						.append("         Where Table_Code = Vsl003                                                ")
						.append("           And Col_Code = Vsl004) Vsl004_Name,                                    ")
						.append("       Vsl003,                                                                    ")
						.append("       Vsl004,                                                                    ")
						.append("       Vsl005,                                                                    ")
						.append("       Vsl006,                                                                    ")
						.append("       Vsl007,                                                                    ")
						.append("       (Select Table_Name From Code_Table Where Table_Code = Vsl008) Vsl008_Name, ")
						.append("       (Select Col_Name                                                           ")
						.append("          From Code_Table_Col                                                     ")
						.append("         Where Table_Code = Vsl008                                                ")
						.append("           And Col_Code = Vsl009) Vsl009_Name,                                    ")
						.append("       Vsl008,                                                                    ")
						.append("       Vsl009,                                                                    ")
						.append("       Vsl010,                                                                    ")
						.append("       Vsl011,                                                                    ")
						.append("       Vsl012,                                                                    ")
						.append("       (Select Aaa103                                                             ")
						.append("          From v_Aa10                                                             ")
						.append("         Where Aaa102 = Vsl013                                                    ")
						.append("           And Aaa100 = (Select Upper(Nvl(Code_Type, Vsl004))                     ")
						.append("                           From Code_Table_Col                                    ")
						.append("                          Where Table_Code = Vsl003                               ")
						.append("                            And Col_Code = Vsl004)) Vsl013_Name,                  ")
						.append("       Vsl013                                                                     ")
						.append("  From Verify_Sql_List                                                            ")
						.append(" Where 1 = 1                                                                      ")
						.append(" and vru001='" + vru001+"'" )
						.append(" order by vsl014 " );
		sql = sqlBf.toString();
		if(DBUtil.getDBType()==DBType.MYSQL){
			sql = sql.replace("nvl(", "ifnull(").replace("Nvl(", "ifnull(");
		}
		this.pageQuery(sql,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 提示类型（vru003）改变后，修改校验名称
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("vru003.onchange")
	@AutoNoMask
	@NoRequiredValidate
	public int vru003Onchange() throws RadowException{
		String vru003 = this.getPageElement("vru003").getValue();
		String vru004 = this.getPageElement("vru004").getValue();
		String vru005 = this.getPageElement("vru005").getValue();
		//设定校验规则名称
		if(!StringUtil.isEmpty(vru003) && !StringUtil.isEmpty(vru004) && !StringUtil.isEmpty(vru005)){
			String vru003Name = CodeManager.getValueByCode("VRU003", vru003);
			String vru004Name = RuleSqlListBS.getTableName(vru004);
			String vru005Name = RuleSqlListBS.getColName(vru004, vru005 );
			//this.getPageElement("vru002").setValue((vru003Name!=null?vru003Name:"")+"校验_"+vru004Name+"_"+vru005Name+"_");//+(vru002!=null ?vru002.trim():""));
			this.getPageElement("vru008").setValue("");
			//this.getPageElement("vru002").setValue(vru005Name);
			//this.getPageElement("vru008").setValue(vru003Name+":");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**vru004校验信息集改变后触发
	 * 
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws AppException 
	 * @throws SQLException
	 */
	@PageEvent("vru004.onchange")
	@NoRequiredValidate       
	@AutoNoMask
	public int vru004Change() throws RadowException, AppException{
		
		String vru004= this.getPageElement("vru004").getValue();
		this.getPageElement("vsl003").setValue(vru004);
		this.getPageElement("vsl004").setValue("");
		this.getPageElement("vru005").setValue("");
		
		TreeMap<String, String> treeMap;
		try {
			treeMap = RuleSqlListBS.getVru005byVru004(vru004,true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("获取信息项可选值异常！异常信息:"+e.getMessage());
		}
		( (Combo) this.getPageElement("vru005")).setValueListForSelect(treeMap);
		this.getPageElement("vsl003").setValue(vru004);
		this.getPageElement("vsl004").setValue("");
		this.getPageElement("vsl005").setValue("");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * vru005校验信息项改变后触发
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws SQLException
	 * @throws AppException 
	 */
	@PageEvent("vru005.onchange")
	@NoRequiredValidate   
	@AutoNoMask
	public int vru005Change() throws RadowException, AppException{
		String vru005= this.getPageElement("vru005").getValue();
		String vru004= this.getPageElement("vru004").getValue();
		String vru003= this.getPageElement("vru003").getValue();
//		String vru002= this.getPageElement("vru002").getValue();
		//1.校验
		if(StringUtil.isEmpty(vru003)){
			this.getPageElement("vru005").setValue("");
			this.getPageElement("vsl004").setValue("");
			this.setMainMessage("请选择校验类别！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(vru004)){
			this.getPageElement("vru005").setValue("");
			this.getPageElement("vsl004").setValue("");
			this.setMainMessage("请先选择校验信息集！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("vsl004").setValue(vru005);
		
		//2.设定校验规则名称
		if(!StringUtil.isEmpty(vru005) && !StringUtil.isEmpty(vru003)){
			CodeValue vru003CodeValue = RuleSqlListBS.getCodeValue("VRU003", vru003);
			String vru003Name = vru003CodeValue!=null?vru003CodeValue.getCodeName():"";
			String vru004Name = RuleSqlListBS.getTableName(vru004);
			String vru005Name = RuleSqlListBS.getColName(vru004, vru005 );
			//this.getPageElement("vru002").setValue((vru003Name!=null?vru003Name:"")+"校验_"+vru004Name+"_"+vru005Name+"_");//+(vru002!=null ?vru002.trim():""));
			this.getPageElement("vru008").setValue("");
			//this.getPageElement("vru002").setValue(vru005Name);
			//this.getPageElement("vru008").setValue(vru003Name+":");
		}
		
		//3.根据配置的参数设定可选 操作运算符
		String vsl006s;
		try {
			vsl006s = RuleSqlListBS.getVsl006(vru004, vru005);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("获取可用运算操作符异常："+e.getMessage());
		}
		
		Combo vsl006Combo = (Combo)this.getPageElement("vsl006");
		vsl006Combo.setValue("");
		if(!StringUtil.isEmpty(vsl006s)){
//			vsl006Combo.loadDataForSelectStore("VSL006","","aaa102 in ("+vsl006s+")",true);
			vsl006Combo.setValueListForSelect(RuleSqlListBS.getAllMapByCodeType("VSL006", "code_value IN  ("+vsl006s+")"));
		}else{
			this.setMainMessage("对应信息集信息项没有配置可使用的运算操作符！");
		}
		
		
		//4. 设定选定校验信息项的数据类型
		VVerifyColVsl006 vcv = null;
		if(!StringUtil.isEmpty(vru005)){
			vcv = RuleSqlListBS.getVverifyColVsl006(vru004, vru005);
			if(vcv==null){
				throw new AppException("无法获取到该信息集的信息项的配置信息！请联系管理员！");
			}
		}
		this.getPageElement("vsl005").setValue(vcv==null?"":vcv.getColDataType());
		
		//5.根据选择的校验项，设定vsl013（选择取值）的可选项
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(vru004, vru005);
		Combo vsl013 = (Combo)this.getPageElement("vsl013");
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			vsl013.setValueListForSelect(RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase()));
		}else if(!StringUtil.isEmpty(vru005)){
			vsl013.setValueListForSelect(RuleSqlListBS.getAllMapByCodeType(vru005.toUpperCase()));
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 运算符改变后如果为【为空】或【不为空】，清空固定取值、选择取值、比对信息项
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("vsl006.onchange")
	@AutoNoMask
	public int vsl006Onchange() throws RadowException, AppException{
		String vsl006 = this.getPageElement("vsl006").getValue();
		PageElement vsl013Ele = this.createPageElement("vsl013",ElementType.SELECT,false);	//比对选择值
		PageElement vsl007Ele = this.createPageElement("vsl007",ElementType.TEXT,false);	//比对固定值
		PageElement vsl009Ele = this.createPageElement("vsl009",ElementType.SELECT,false);	//比对信息项
		PageElement vsl008Ele = this.createPageElement("vsl008",ElementType.SELECT,false);	//比对信息集
		if(StringUtil.isEmpty(vsl006)){
			vsl013Ele.setDisabled(false);
			vsl007Ele.setDisabled(false);
			vsl009Ele.setDisabled(false);
			vsl008Ele.setDisabled(false);
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//初始化都可以修改！
		vsl013Ele.setDisabled(false);
		vsl007Ele.setDisabled(false);
		vsl009Ele.setDisabled(false);
		vsl008Ele.setDisabled(false);
		
		CodeValue codeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
		if(codeValue==null || StringUtil.isEmpty(codeValue.getSubCodeValue())){
			throw new AppException("获取运算操作符配置参数异常！异常信息：运算符值-"+vsl006);
		}
		String subCodeValue = codeValue.getSubCodeValue();
		
		//为空运算:其他比对值都为空
		if("3".equalsIgnoreCase(subCodeValue)){
			vsl013Ele.setValue("");
			vsl007Ele.setValue("");
			vsl009Ele.setValue("");
			vsl008Ele.setValue("");
			vsl013Ele.setDisabled(true);
			vsl007Ele.setDisabled(true);
			vsl009Ele.setDisabled(true);
			vsl008Ele.setDisabled(true);
		}else if("2".equalsIgnoreCase(subCodeValue) || "4".equalsIgnoreCase(subCodeValue) || "6".equalsIgnoreCase(subCodeValue) ){
		//字符模糊查询运算和长度比较运算符、正则表达包含，只使用固定比对值
			vsl013Ele.setValue("");
			vsl009Ele.setValue("");
			vsl008Ele.setValue("");
			vsl013Ele.setDisabled(true);
			vsl009Ele.setDisabled(true);
			vsl008Ele.setDisabled(true);
		}else if("5".equalsIgnoreCase(subCodeValue)){
		//比对信息项+固定值，只使用固定比对值和比对信息集、比对信息项
			vsl013Ele.setValue("");
			vsl013Ele.setDisabled(true);
		}else if("10".equalsIgnoreCase(subCodeValue) || "9".equalsIgnoreCase(subCodeValue) ||  "7".equalsIgnoreCase(subCodeValue) || "8".equalsIgnoreCase(subCodeValue)){
				vsl013Ele.setValue("");
				vsl007Ele.setValue("");
				vsl009Ele.setValue("");
				vsl008Ele.setValue("");
				vsl013Ele.setDisabled(true);
				vsl007Ele.setDisabled(true);
				vsl009Ele.setDisabled(true);
				vsl008Ele.setDisabled(true);
				if("941".equals(codeValue.getCodeValue())){
					vsl007Ele.setValue("");
					vsl007Ele.setDisabled(false);
				}
		}else{
			vsl013Ele.setDisabled(false);
			vsl007Ele.setDisabled(false);
			vsl009Ele.setDisabled(false);
			vsl008Ele.setDisabled(false);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**vsl008比对信息集改变后触发
	 * 比对项的数据类型需要与校验项相同,查询的是VERIFY_COL_VSL006中设定的 【列应该的数据类型】，因为有可能本该是Date类型却设置为varchar2类型的情况
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws SQLException
	 * @throws AppException 
	 */
	@PageEvent("vsl008.onchange")
	@NoRequiredValidate    
	@AutoNoMask
	public int vsl008Change() throws RadowException, AppException{
		String vru004= this.getPageElement("vru004").getValue();
		String vru005= this.getPageElement("vru005").getValue();
		String vsl008= this.getPageElement("vsl008").getValue();
		this.getPageElement("vsl009").setValue("");
		if(StringUtil.isEmpty(vru005) || StringUtil.isEmpty(vru004)){
			this.setMainMessage("请先选择校验信息项！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//过滤条件：比对项的数据类型需要与校验项相同,查询的是VERIFY_COL_VSL006中设定的 【列应该的数据类型】，因为有可能本该是Date类型却设置为varchar2类型的情况
		VVerifyColVsl006 vcv = RuleSqlListBS.getVverifyColVsl006(vru004,vru005);
		String fliter = null;
		try {
			fliter = " colDataTypeShould = '"+vcv.getColDataTypeShould()+"'";
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("获取校验信息集，校验信息项配置文件异常！");
		}
		TreeMap<String, String> treeMap = RuleSqlListBS.getVru005byVru004(vsl008,false,fliter);
		( (Combo) this.getPageElement("vsl009")).setValueListForSelect(treeMap);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 比对值改变后触发，功能：确保固定取值、选择取值、比对信息项只能任选其一
	 * 
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvents({"vsl007.onchange","vsl013.onchange","vsl009.onchange"})
	@NoRequiredValidate       
	@AutoNoMask
	public int compareValueChange() throws RadowException, SQLException{
		String eleName = this.getCueEventElementName();
		String vsl007= this.getPageElement("vsl007").getValue();
		String vsl013= this.getPageElement("vsl013").getValue();
		String vsl009= this.getPageElement("vsl009").getValue();
		String vsl008= this.getPageElement("vsl008").getValue();
		String vru005= this.getPageElement("vru005").getValue();
		String vru004= this.getPageElement("vru004").getValue();
		String vsl006= this.getPageElement("vsl006").getValue();//运算符
		
		if(StringUtil.isEmpty(vru005) || StringUtil.isEmpty(vru004)){
			this.getPageElement("vsl009").setValue("");
			this.setMainMessage("请先选择校验信息集与校验信息项！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(vsl006)){
			this.getPageElement("vsl013").setValue("");
			this.getPageElement("vsl009").setValue("");
			this.getPageElement("vsl008").setValue("");
			this.getPageElement("vsl007").setValue("");
			this.setMainMessage("请先选择运算操作符！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		CodeValue codeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
		String vsl006SubValue = codeValue!=null?codeValue.getSubCodeValue():"";//运算符分类
		
		if("vsl007".equals(eleName)){
			if(!StringUtil.isEmpty(vsl007)){
				
				this.getPageElement("vsl013").setValue("");
				this.getPageElement("vsl009").setValue("");
				this.getPageElement("vsl008").setValue("");
				this.getPageElement("vsl007").setValue(vsl007);
			}
		}else if("vsl013".equals(eleName)){
			if(!StringUtil.isEmpty(vsl013)){
				this.getPageElement("vsl007").setValue("");
				this.getPageElement("vsl009").setValue("");
				this.getPageElement("vsl008").setValue("");
				this.getPageElement("vsl013").setValue(vsl013);
			}
			
		}else if("vsl009".equals(eleName)){
			if(!StringUtil.isEmpty(vsl009)){
				if(StringUtil.isEmpty(vsl008)){
					this.getPageElement("vsl009").setValue("");
					this.setMainMessage("请先选择比对信息集！");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(!"5".equals(vsl006SubValue)){//非比对信息项+固定值(月)
					this.getPageElement("vsl007").setValue("");
					this.getPageElement("vsl013").setValue("");
				}else{
					this.getPageElement("vsl013").setValue("");
				}
				
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 页面重新加载
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnReload.onclick")
	@NoRequiredValidate
	public int reload() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		String parData = this.getRadow_parent_data();
		String[] parDatas = parData.split(",");
		this.getPageElement("vsc001").setValue(parDatas[0]);//设定方案ID
		this.getPageElement("vsl012").setValue("2");//设定默认连接符：或者
		
		//初始化，设定信息集可选项
		Combo vru004 =  (Combo)this.createPageElement("vru004", ElementType.SELECT, false);
		Combo vsl008 =  (Combo)this.createPageElement("vsl008", ElementType.SELECT, false);
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		vru004.setValueListForSelect(map);
		vsl008.setValueListForSelect(map);
		
		if(parDatas.length>1 && !StringUtil.isEmpty(parDatas[1])){
			String vru001 = parDatas[1];
			VerifyRule vr = (VerifyRule) HBUtil.getHBSession().get(VerifyRule.class, vru001);
			//1.赋值校验规则信息，设定校验项不能修改
			PageModel.copyObjValueToElement(vr, this);
			this.getPageElement("vsl003").setValue(vr.getVru004());
			this.getPageElement("vsl004").setValue(vr.getVru005());
			/*this.createPageElement("vru004", ElementType.SELECT, false).setDisabled(true);
			this.createPageElement("vru005", ElementType.SELECT, false).setDisabled(true);*/
			//2.修改界面隐藏【保存并新增】按钮
			this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSaveContinue').setVisible(false);");
			//3. 查询校验规则对应的SQL列表
			this.setNextEventName("grid.dogridquery");
			//4.调用校验项onchange事件
			this.setNextEventName("vru004.onchange");
		}
		if(parDatas.length>1 && !StringUtil.isEmpty(parDatas[0])){
			String vsc001 = parDatas[0];
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			VerifyScheme vs = (VerifyScheme)HBUtil.getHBSession().get(VerifyScheme.class, vsc001);
			//设定该方案不可修改
			if(vs!=null && !StringUtil.isEmpty(vs.getVsc007()) && vs.getVsc007().equals("1") && !"admin".equals(username)){
				//隐藏保存按钮
				this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSaveContinue').setVisible(false);");
				this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSave').setVisible(false);");
			}
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
