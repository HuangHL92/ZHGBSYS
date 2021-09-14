package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_007_ScriptModifyPageModel extends PageModel {

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	String color = "Grey";									//设置颜色为灰色
	@Override
	public int doInit() throws RadowException {
		return 0;
	}
	
	/**
	*====================================================================================================
	* 方法名称:queryObj.初始化方案脚本参数配置页面<br>
	* 方法创建日期:2016年03月9日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月9日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:初始化方案脚本参数配置页面<br>
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】<br>
	* 输入参数
	* <table>
	*  参数序号				参数名称				参数描述				参数数据类型
	*  <li>(01)
	* </table>       
	* 返回结果
	* <table>
	*  结果序号				结果名称				结果描述				结果数据类型
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   返回成功状态				  int
	* </table>
	* 结果结构详述:初始化方案脚本参数配置页面
	*====================================================================================================
	*/
	@PageEvent("queryObj")
	@NoRequiredValidate
	@AutoNoMask
	public int queryObj()throws RadowException{	
		String nodeId = this.getPageElement("nodeId").getValue();						//获取方案节点编码
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();						//获取操作模式，为新建或者已建
		String preNodeId = this.getPageElement("preNodeId").getValue();					//获取方案脚本父节点编码
		String[] preNodeId_s = preNodeId.split("_");
		InterfaceConfig config = bs7.getConfigByIsn(preNodeId_s[1]);					//根据父节点编码，获取脚本方案对象
		this.getExecuteSG().addExecuteCode(
		"document.all.gridT.innerHTML='&nbsp;<span style=\"font-weight: normal;\">所属方案：" + 
		config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "</span>';");//现实工具栏上的文本
		//[NO.001]设置页面元素默认值
		String availableStatus = "1";													//"1"代表有效
		this.getPageElement("interfaceConfigId").setValue(config.getInterfaceConfigId());
		if(!"NEW".equals(opmode)){														//判断操作模式是否为新建模式
			InterfaceScript script = bs7.getScriptByIsn(nodeId_s[1]);
			String createUserName = script.getInterfaceScriptCreateUser();
			String changeUserName = script.getInterfaceScriptChangeUser();
			this.getPageElement("interfaceScriptId").setValue(script.getInterfaceScriptId());
			this.getPageElement("interfaceScriptSequence").setValue(script.getInterfaceScriptSequence()+"");
			this.getPageElement("interfaceScriptName").setValue(script.getInterfaceScriptName());
			this.getPageElement("interfaceScriptIsedit").setValue(script.getInterfaceScriptIsedit());
			this.getPageElement("interfaceScriptDesc").setValue(script.getInterfaceScriptDesc());
			this.getPageElement("interfaceScriptStateId").setValue(script.getAvailabilityStateId());
			this.getPageElement("interfaceScriptCreateUserName").setValue(createUserName);
			this.getPageElement("interfaceScriptChangeUserName").setValue(changeUserName);
			this.getPageElement("interfaceScriptCreateDate").setValue(bs7.formatDate(script.getInterfaceScriptCreateDate()));
			this.getPageElement("interfaceScriptChangeDate").setValue(bs7.formatDate(script.getInterfaceScriptChangeDate()));
		    this.getPageElement("targetTableName").setValue(script.getTargetTableName());
//			if("true".equals(script.getInterfaceScriptIsedit()) && "0".equals(config.getPublishStateId())){
//				this.getExecuteSG().addExecuteCode("document.all.isIsEdit.checked=true;");											//将页面的是否可编辑设置为可以编辑
//				this.getPageElement("interfaceScriptIsedit").setValue("true");														//将页面的是否可编辑设置为可以编辑
//				this.getPageElement("interfaceScriptSequence").setDisabled(false);
//				this.getPageElement("interfaceScriptName").setDisabled(false);
//				this.getPageElement("interfaceScriptDesc").setDisabled(false);
//				this.getPageElement("interfaceScriptStateId").setDisabled(false);
//				this.getPageElement("editScript").setDisabled(false);
//				this.getPageElement("targetTableName").setDisabled(false);
//			}else{
//				this.getExecuteSG().addExecuteCode("document.all.isIsEdit.checked=false;");											//将页面的是否可编辑设置为不可编辑
//				this.getPageElement("interfaceScriptIsedit").setValue("false");														//将页面的是否可编辑设置为不可编辑
//				this.getPageElement("interfaceScriptSequence").setDisabled(true);
//				this.getPageElement("interfaceScriptName").setDisabled(true);
//				this.getPageElement("interfaceScriptDesc").setDisabled(true);
//				this.getPageElement("interfaceScriptStateId").setDisabled(true);
//				this.getPageElement("editScript").setDisabled(true);
//				this.getPageElement("targetTableName").setDisabled(true);
//			}
		}else{
			String str = bs7.getAutoScriptId(config.getInterfaceConfigId());														//获取系统自动生成的抽取采集脚本id
			this.getPageElement("interfaceScriptId").setValue(str);																	//将自动获取的抽取脚本id赋值给抽取脚本方案编码
			String seq = bs7.getMaxInterfaceScriptSeq(config.getInterfaceConfigId())+1+"";													//自动获取序号
			if(seq.length() <= 5)																									//序号超出范围保存时会报错
				this.getPageElement("interfaceScriptSequence").setValue(seq);														//获取的序号赋值给页面元素interfaceScriptSequence
			String loginName = SysUtil.getCacheCurrentUser().getLoginname(); 																//系统当前用户登录名
			this.getPageElement("interfaceScriptCreateUser").setValue(loginName);													//设置抽取脚本创建者名称
			this.getPageElement("interfaceScriptCreateUserName").setValue(bs7.getUserName(loginName));								//设置抽取脚本创建者名字
			this.getPageElement("interfaceScriptChangeUser").setValue(loginName);													//设置脚本最后修改者名称
			this.getPageElement("interfaceScriptChangeUserName").setValue(bs7.getUserName(loginName));								//设置脚本最后修改者名字
			this.getPageElement("interfaceScriptCreateDate").setValue(bs7.formatDate(new Date()));
			this.getPageElement("interfaceScriptStateId").setValue(availableStatus);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 编辑脚本按钮事件
	 * @return
	 * @throws RadowException
	 * @throws UnsupportedEncodingException
	 */
	@PageEvent("editScript.onclick")
	@NoRequiredValidate
	@GridDataRange
	public int editScript() throws RadowException, UnsupportedEncodingException{
		this.getExecuteSG().addExecuteCode("window.getSqlValue();");
		this.setNextEventName("open");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 打开编辑脚本页面
	 * @return
	 * @throws RadowException
	 * @throws UnsupportedEncodingException
	 */
	@PageEvent("open")
	@NoRequiredValidate
	@GridDataRange
	public int open() throws RadowException, UnsupportedEncodingException{
		String sqlText = this.getPageElement("sqlText").getValue(); 
		sqlText = java.net.URLEncoder.encode(java.net.URLEncoder.encode(sqlText, "UTF-8"), "UTF-8");//解决中文乱码问题，将工作流名称先用utf-8进行编码
		request.getSession().setAttribute("sqlText", new String(sqlText));
		this.openWindow("ScriptEdit", "pages.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_ScriptEdit");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	*====================================================================================================
	* 方法名称:save.保存页面数据的点击反应事件<br>
	* 方法创建日期:2016年03月11日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月11日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:保存页面数据的点击反应事件<br>
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】<br>
	* 输入参数
	* <table>
	*  参数序号				参数名称				参数描述				参数数据类型
	*  <li>(01)
	* </table>
	* 返回结果
	* <table>
	*  结果序号				结果名称				结果描述				结果数据类型
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   返回成功状态				  int
	* </table>
	* 结果结构详述:提交保存页面所有数据
	*====================================================================================================
	*/
	@PageEvent("save")
	@AutoNoMask
	public int save() throws RadowException{
		String nodeId = this.getPageElement("nodeId").getValue();										//获取当前方案脚本内码
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();										//获取当前页面的操作模式，新建或其他
		String preNodeId = this.getPageElement("preNodeId").getValue();									//获取原方案脚本内码，用于记录日志
		String[] preNodeId_s = preNodeId.split("_");
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();
		String interfaceScriptId = this.getPageElement("interfaceScriptId").getValue();					//获取抽取脚本编码
		String interfaceScriptSequence = this.getPageElement("interfaceScriptSequence").getValue();
		String interfaceScriptName = this.getPageElement("interfaceScriptName").getValue();
		String targetTableName = this.getPageElement("targetTableName").getValue();                     //获取数据集名称
		String sqlText = this.getPageElement("sqlText").getValue();									    //获取查询语句文本
		
		String interfaceScriptStateId = this.getPageElement("interfaceScriptStateId").getValue();
		String preInterfaceScriptCreateDate = this.getPageElement("interfaceScriptCreateDate").getValue();
		
		InterfaceConfig config = bs7.getConfigByIsn(preNodeId_s[1]);									//通过抽取采集方案内码，获取抽取采集方案单个对象，用于记录日志
		
		if("".equals(interfaceScriptSequence) || interfaceScriptSequence == null){
			this.setMainMessage("您好，接口脚本序号不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(interfaceScriptName) || interfaceScriptName == null){
			this.setMainMessage("您好，接口脚本名称不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(sqlText.trim()) || sqlText == null){												//判断查询语句为空，为空则抛出异常提示框
			this.setMainMessage("您好，接口脚本不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(targetTableName) || targetTableName == null){												//判断查询语句为空，为空则抛出异常提示框
			this.setMainMessage("您好，数据集不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		InterfaceScript script = bs7.getScriptByIsn(nodeId_s[1]);				                        //通过方案和脚本id获取到脚本对象
		InterfaceScript script1 = new InterfaceScript();
		
		if("NEW".equals(opmode)){ 																		//判断操作模式是否为新建
			if(null != script){																			//判断脚本对象是否为空
				if("1".equals(script.getAvailabilityStateId())){										//判断有效性状态编码是否为1
					color = "767702";
				}
				this.getExecuteSG().addExecuteCode("odin.alert('您好，数据访问接口方案脚本编码\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + interfaceScriptId + "</b></span>\"已存在，系统将自动重置数据访问接口方案脚本编码。');");
				String str = this.getPageElement("interfaceScriptId").getValue();									//获取系统自动生成的脚本编码
				this.getPageElement("interfaceScriptId").setValue(str);									//将系统自动生成的脚本编码赋值给页面字段interfaceScriptId
	            return EventRtnType.NORMAL_SUCCESS;
			}
			if("1".equals(config.getPublishStateId())){
				config.setPublishStateId("0");
				this.setMainMessage("需要重新发布接口访问方案");
			}
			script = new InterfaceScript();																//重新创建脚本对象
	    }else{
	    	//拷备数据
			try {
				PropertyUtils.copyProperties(script1, script);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	    }
		script.setInterfaceScriptSql(sqlText);															//将查询语句脚本同步到数据库中
		script.setTargetTableName(targetTableName);
		script.setInterfaceScriptName(interfaceScriptName);												//将抽取脚本名称同步到数据库中
		script.setInterfaceConfigId(interfaceConfigId);													//将脚本编码同步到数据库中
		script.setInterfaceScriptId(interfaceScriptId);
		script.setInterfaceScriptIsn(nodeId_s[1]);														//将方案脚本内码同步到数据库中
		script.setInterfaceScriptDesc(this.getPageElement("interfaceScriptDesc").getValue());
		script.setInterfaceScriptIsedit(this.getPageElement("interfaceScriptIsedit").getValue());
		Date date = new Date();																			//创建日期对象
		String dateStr = bs7.formatDate(date);															//用于初始化创建修改访问日期		
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();                                //获取当前系统登陆名
		String userName = bs7.getUserName(loginName);                                                   //获取用户名称
		script.setInterfaceScriptChangeDate(date);
		script.setInterfaceScriptChangeUser(userName);
		script.setAvailabilityStateId(interfaceScriptStateId);
		if("UPDATE".equals(opmode)){																	//判断操作类型是否为UPDATE
			script.setInterfaceScriptCreateDate(bs7.parseDateStr(preInterfaceScriptCreateDate));
		}else{
			script.setInterfaceScriptCreateDate(date);													//如果不是更新update，那就是NEW新建的方案脚本，将当前日期设置为方案脚本创建日期
			script.setInterfaceScriptCreateUser(userName);
			this.getPageElement("preInterfaceScriptCreateDate").setValue(bs7.formatDate(date));
		}
		if(null == script.getAvailabilityStateId() || "root".equals(script.getAvailabilityStateId()) || "".equals(script.getAvailabilityStateId())){//判断抽取方案脚本有效性编码是否为空或为root
			throw new RadowException("您好，数据访问接口方案脚本有效性状态不能为空。");									//弹出提示框
		}
		
		config.setInterfaceConfigChangeDate(script.getInterfaceScriptChangeDate());						//将抽取方案最后修改时间设置为方案脚本的最后修改时间
		config.setInterfaceConfigChangeUser(script.getInterfaceScriptChangeUser());						//将抽取方案最后修改人设置为方案脚本的最后修改人
		script.setInterfaceScriptSequence(Integer.valueOf(interfaceScriptSequence));
		Transaction t = bs7.getSession().getTransaction();							//获取事务处理对象
		t.begin( );																	//事务开始
		bs7.getSession().save(script);										//将脚本对象对应的数据同步到数据库中	
		bs7.getSession().save(config);										//将方案对象对应的数据同步到数据库中
		t.commit();																	//提交事务
		
		//[NO.002]更新UI
		if("1".equals(script.getAvailabilityStateId())){							//判断脚本有效性编码是否为1
			color = "767702";
		}
		try{
			if("NEW".equals(opmode)){													//判断操作类型是否为NEW
				 //[NO.003]更新父页面的tab页面的名称
				 this.getPageElement("interfaceScriptChangeDate").setValue(dateStr);		//设置脚本最后修改日期为当前日期
				 /**记录日志*/
				 //SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.CREATE, "新建" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "数据访问接口方案脚本成功。");//写日志，表明创建脚本成功
				 new LogUtil().createLog("96", "InterfaceScript",script.getInterfaceScriptIsn(), script.getInterfaceScriptName(), "", new Map2Temp().getLogInfo(new InterfaceScript(), script));
			 }else{
				 this.getPageElement("interfaceScriptChangeDate").setValue(dateStr);		//非新建，设置脚本最后修改日期为当前时间
				 /**记录日志*/
				 //SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.MODIFY, "修改" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "数据访问接口方案脚本成功。");//写日志，表明脚本修改成功
				 new LogUtil().createLog("97", "InterfaceScript",script.getInterfaceScriptIsn(), script.getInterfaceScriptName(), "", new Map2Temp().getLogInfo(script1, script));
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		this.getPageElement("interfaceScriptName").setValue(script.getInterfaceScriptName());//更新名称：为空白，默认值为'extractScriptId'
		this.getPageElement("opmode").setValue("UPDATE");							//设置操作类型为UPDATE
		//刷新页面
		this.setMainMessage("您好，\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "\"数据访问接口方案脚本已经保存成功。");//弹出提示框，提示保存成功
		//[NO.004]更新图标
		this.getExecuteSG().addExecuteCode("window.parent.updateTab('<span style=\"font-family:Arial;display:inline-block;color:FF6820;font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName()
				+ "->" + "<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "',1);");
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");							//调用父页面方法reloadTree，刷新方案脚本树
		this.getExecuteSG().addExecuteCode("window.parent.deletehaveChange('" + nodeId + "');");	//调用父页面方法deletehaveChange()
		this.setNextEventName("queryObj");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 删除脚本按钮响应事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("delBtn.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int delBtnOnclick() throws RadowException{
		String interfaceScriptId = this.getPageElement("interfaceScriptId").getValue();		//获取抽取方案脚本编码
		String opmode = this.getPageElement("opmode").getValue();							//获取操作类型
		String interfaceScriptName = this.getPageElement("interfaceScriptName").getValue();
		String message = "";
		if("UPDATE".equals(opmode)){														//判断操作类型是否为更新UPDATE
			
		    message = "您好，您确定要删除\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + interfaceScriptId + "</b></span>." + interfaceScriptName + "\"数据访问接口方案脚本信息？选择确定按钮将删除。";//弹出框提示删除信息
	    }else{
		    message = "您好，您确定要删除当前新建中的数据访问接口方案脚本信息？选择确定按钮将删除。";								//操作类型非更新时删除提示信息
		}
		this.addNextEvent(NextEventValue.YES, "sureClear");									//调用sureClear方法，执行脚本删除
		this.addNextEvent(NextEventValue.CANNEL, "");										//其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);										//消息框类型，即confirm类型窗口
		this.setMainMessage(message);														//窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 确认删除脚本
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("sureClear")
	@NoRequiredValidate
	public int sureClear() throws RadowException{
		String nodeId = this.getPageElement("nodeId").getValue();			//获取方案脚本内码
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();			//获取操作类型字符串
		if("UPDATE".equals(opmode)){										//判断操作类型是否为UPDATE
			InterfaceScript script = bs7.getScriptByIsn(nodeId_s[1]);		//通过抽取采集方案脚本内码，获取抽取采集方案脚本单个对象
			if("1".equals(script.getAvailabilityStateId())){				//判断脚本有效性编码是否为1
				color = "767702";
			}
			try{
				bs7.delScript(nodeId_s[1]);//根据所传抽取采集方案、脚本编码，删除对应的抽取采集脚本及其对应的抽取字段转换配置信息
				//SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.DELETE, "删除" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "数据访问接口方案脚本成功。");//写日志，脚本删除成功
				this.setMainMessage("您好，删除\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "\"数据访问接口方案脚本成功。");//删除方案脚本成功
				new LogUtil().createLog("98", "InterfaceScript",script.getInterfaceScriptIsn(), script.getInterfaceScriptName(), "", new ArrayList());
			}catch(Exception e){												//捕捉方法执行异常信息，并将异常信息写入日志
				//SysLogUtil.addSysOpLog("SJZYPT_009_002", SysOpType.DELETE, "删除" + config.getInterfaceConfigId() + "." + config.getInterfaceConfigName() + "->" + script.getId().getInterfaceScriptId() + "." + script.getInterfaceScriptName() + "数据访问接口方案脚本失败" + e.getMessage());//方案脚本删除失败，记录日志
				this.setMainMessage("您好，删除\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + script.getInterfaceScriptId() + "</b></span>." + script.getInterfaceScriptName() + "\"数据访问接口方案脚本失败。");//弹出删除失败提示框
				throw new RadowException("删除失败:" + e.getMessage());			//抛出异常到控制台
			}
		}
		this.getExecuteSG().addExecuteCode("window.parent.delTreeNode('" + nodeId + "');");	//根据方案脚本内码，删除对应的树节点
		this.getExecuteSG().addExecuteCode("window.parent.removeTab('" + nodeId + "');");	//关闭tab页
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取sql脚本的类型
	 * @param scriptsql
	 * @return
	 */
	public String getSQLType(String scriptsql){
		String sqltype = "";
		if(!"".equals(scriptsql) && scriptsql != null){												//判断脚本语句是否为空
			scriptsql = removeCommnetFromSQL(scriptsql);									        //去除注释和左侧的空格			
			scriptsql = scriptsql.replace("\r", " ").replace("\n", " ").replaceAll(" {2,}", " ");	//去除回车换行符，以及去除多余空格	
			if(scriptsql.indexOf(" ") == -1){														//判断是否有空格
				sqltype = scriptsql;																//没有空格说明该查询语句只有一个关键字，即为语句类型
			}else{
			    sqltype = scriptsql.substring(0, scriptsql.indexOf(" "));							//截取空格前的字符串，取得sql语句类型
			}
		}
		return sqltype.toUpperCase();																//查询语句类型转换成大写字母
	}
	/**
	 * 去掉sql里面的注释 
	 */
	public String removeCommnetFromSQL(String sql){
		Pattern p = Pattern.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/");
		String presult = p.matcher(sql).replaceAll("$1");
		presult=presult.replaceAll("^\\s*", " ");
		return presult.trim();
	}
	
	@PageEvent("testScript.onclick")
	public int test() throws RadowException {
		this.setRadow_parent_data(this.getPageElement("nodeId").getValue());
		this.openWindow("ScriptTest", "pages.sysmanager.ZWHZYQ_001_007.ScriptTest");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
