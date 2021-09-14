package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.io.UnsupportedEncodingException;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;

public class ZWHZYQ_001_007_ScriptEditPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:close.关闭脚本配置页面
	* 方法创建日期:2016年03月09日
	* 方法创建人员:黄程
	* 方法最后修改日期:2016年03月09日
	* 方法最后修改人员:黄程
	* 方法功能描述:关闭脚本配置页面
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】
	* 输入参数
	* 参数序号                  参数名称                                         参数描述                                 参数数据类型
	* 返回结果
	* 结果序号     	        结果名称				     结果描述		             结果数据类型
	*  (01)  EventRtnType.NORMAL_SUCCESS  返回成功状态                           int             				 
	* 结果结构详述:关闭脚本配置界面，并提示是否保存数据
	*====================================================================================================
	*/
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException{
		String changeflag=this.getPageElement("changeflag").getValue();			//获取改变的状态为，判断页面元素值是否改变
		if("true".equals(changeflag)){											//判断页面数据是否保存，没保存则提示保存
			this.addNextEvent(NextEventValue.YES, "doColWin");					//confirm类型窗口,点击确定时触发事件
			this.addNextEvent(NextEventValue.CANNEL, "");						//confirm类型窗口,点击取消时触发事件
			this.setMessageType(EventMessageType.CONFIRM);						//消息框类型(confirm类型窗口)
			this.setMainMessage("你好，当前脚本编辑页面已修改但未保存，您确定要关闭当前页面吗？");	//弹出消息框类型(confirm类型窗口信息)
		}else{
			this.setNextEventName("doColWin");									//如果保存了，则调用关闭窗口的方法
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:doColWin.窗口关闭事件
	* 方法创建日期:2016年03月09日
	* 方法创建人员:黄程
	* 方法最后修改日期:2016年03月09日
	* 方法最后修改人员:黄程
	* 方法功能描述:窗口关闭事件
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】
	* 输入参数
	* 参数序号                  参数名称                                         参数描述                                 参数数据类型
	*  (01)                   
	* 返回结果
	* 结果序号     	        结果名称				     结果描述		             结果数据类型
	*  (01)  EventRtnType.NORMAL_SUCCESS  返回成功状态                           int                	      
	* 结果结构详述:关闭方案参数配置窗口
	*====================================================================================================
	*/
	@PageEvent("doColWin")
	@NoRequiredValidate
	public int doColWin(){
		this.closeCueWindow("ScriptEdit");										//调用框架方法，关闭当前窗口
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:save.保存页面数据的点击反应事件
	* 方法创建日期:2014年03月09日
	* 方法创建人员:黄程
	* 方法最后修改日期:2016年03月09日
	* 方法最后修改人员:黄程
	* 方法功能描述:保存页面数据的点击反应事件
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】
	* 输入参数
	* 参数序号                  参数名称                                         参数描述                                 参数数据类型
	* 返回结果
	* 结果序号     	        结果名称				     结果描述		             结果数据类型
	*  (01)  EventRtnType.NORMAL_SUCCESS  返回成功状态                           int    		 
	* 结果结构详述:提交保存了页面所填数据
	*====================================================================================================
	*/
	@PageEvent("save")
	@NoRequiredValidate
	public int save() throws RadowException, UnsupportedEncodingException {
		String sqlText = this.getPageElement("sqlText").getValue(); 
		this.getExecuteSG().addExecuteCode("window.parent.setValue('" + sqlText + "');");
		this.closeCueWindow("ScriptEdit");                                      //关闭当前窗口
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
