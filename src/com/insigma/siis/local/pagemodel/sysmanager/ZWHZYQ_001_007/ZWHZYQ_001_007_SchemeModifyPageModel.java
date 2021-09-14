package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.util.HibernateSessionFactory;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceParameter;
import com.insigma.siis.local.business.entity.InterfaceParameterId;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_007_SchemeModifyPageModel extends PageModel{

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	String color = "Grey";												//创建颜色为灰色的字符串值
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:queryObj.初始化数据访问接口方案参数配置页面<br>
	* 方法创建日期:2016年03月7日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月7日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:初始化数据访问接口方案参数配置页面<br>
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
	* 结果结构详述:初始化数据访问接口方案参数配置页面
	*====================================================================================================
	*/
	@SuppressWarnings("rawtypes")
	@PageEvent("queryObj")
	@NoRequiredValidate
	public int queryObj() throws RadowException {
		//[NO.001]获取页面元素初始化下拉列表
		String nodeId = this.getPageElement("nodeId").getValue();                                 //当前树节点Id,通过Http请求传递过来存在JSP也面的隐藏域当中
		String[] nodeId_s = nodeId.split("_");                                                    //因为nodeId组合为FA+"_"+随机码,这里我们需要取到随机码
		String opmode = this.getPageElement("opmode").getValue();				                  //获取当前操作模式，新建还是修改标志
		//[NO.002]设置页面元素默认值
		String availableStatus = "1";												              //有效性状态设置为'1'
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();                          //系统当前登录用户名
		
		if(!"NEW".equals(opmode)) {
			InterfaceConfig interfaceConfig = bs7.getConfigByIsn(nodeId_s[1]);
			String OldLoginName = interfaceConfig.getInterfaceConfigCreateUser();                           //获取原方案登陆名
			int interfaceConfigSeq = interfaceConfig.getInterfaceConfigSequence();                          //获取原方案的序号
			String interfaceConfigId = interfaceConfig.getInterfaceConfigId();                              //获取原方案的编码
			String interfaceConfigName = interfaceConfig.getInterfaceConfigName();                          //获取原方案的名称
			String interfaceConfigDesc = interfaceConfig.getInterfaceConfigDesc();                          //获取原方案的描述
			availableStatus = interfaceConfig.getAvailabilityStateId();                                     //获取原方案的有效性状态
			String lastLoginName = interfaceConfig.getInterfaceConfigChangeUser();
			String createDate = bs7.formatDate(interfaceConfig.getInterfaceConfigCreateDate());
			String changeDate = bs7.formatDate(interfaceConfig.getInterfaceConfigChangeDate());
			this.getPageElement("interfaceConfigSequence").setValue(interfaceConfigSeq+"");				    //将最大的方案序号加1赋值给序号
			this.getPageElement("interfaceConfigCreateUserName").setValue(OldLoginName);	                //初始化方案创建人
			this.getPageElement("interfaceConfigChangeUserName").setValue(lastLoginName);	                //初始化方案最后修改人
			this.getPageElement("interfaceConfigCreateDate").setValue(createDate);                          //初始化方案创建时间
			this.getPageElement("interfaceConfigChangeDate").setValue(changeDate);                          //初始化方案最后修改时间
			this.getPageElement("interfaceConfigStateId").setValue(availableStatus);                        //初始化有效性状态
			this.getPageElement("interfaceConfigId").setValue(interfaceConfigId);
			this.getPageElement("interfaceConfigName").setValue(interfaceConfigName);
			this.getPageElement("interfaceConfigDesc").setValue(interfaceConfigDesc);
			
//			if("true".equals(interfaceConfig.getInterfaceConfigIsedit()) && "0".equals(interfaceConfig.getPublishStateId())){
//				this.getExecuteSG().addExecuteCode("document.all.icIsEdit.checked=true;");											//将页面的是否可编辑设置为可以编辑
//				this.getPageElement("interfaceConfigIsedit").setValue("true");														//将页面的是否可编辑设置为可以编辑
//				this.getPageElement("interfaceConfigId").setDisabled(false);
//				this.getPageElement("interfaceConfigStateId").setDisabled(false);
//				this.getPageElement("interfaceConfigSequence").setDisabled(false);
//				this.getPageElement("interfaceConfigName").setDisabled(false);
//				this.getPageElement("interfaceConfigDesc").setDisabled(false);
//				this.getExecuteSG().addExecuteCode("Ext.getCmp('addParam').enable();" + "Ext.getCmp('delParam').enable();" + "Ext.getCmp('refreshBtn').enable();");
//			}else{
//				this.getExecuteSG().addExecuteCode("document.all.icIsEdit.checked=false;");											//将页面的是否可编辑设置为不可编辑
//				this.getPageElement("interfaceConfigIsedit").setValue("false");														//将页面的是否可编辑设置为不可编辑
//				this.getPageElement("interfaceConfigStateId").setDisabled(true);
//				this.getPageElement("interfaceConfigId").setDisabled(true);
//				this.getPageElement("interfaceConfigSequence").setDisabled(true);
//				this.getPageElement("interfaceConfigName").setDisabled(true);
//				this.getPageElement("interfaceConfigDesc").setDisabled(true);
//				this.getExecuteSG().addExecuteCode("Ext.getCmp('addParam').disable();" + "Ext.getCmp('delParam').disable();" + "Ext.getCmp('refreshBtn').disable();");
//			}
			Grid grid = (Grid)this.createPageElement("list", ElementType.GRID, false);
			List<HashMap<String, Object>> gridlist = new ArrayList<HashMap<String, Object>>();
			List list = bs7.doQueryParam(interfaceConfig.getInterfaceConfigId());
			String str = null;
			for(int i = 0; i < list.size(); i++){
				HashMap dbhm = (HashMap)list.get(i);
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("interface_parameter_sequence", dbhm.get("interface_parameter_sequence"));
				hm.put("interface_parameter_name", dbhm.get("interface_parameter_name"));
				str = (String)dbhm.get("interface_parameter_type");
				if("1".equals(str)){
					hm.put("interface_parameter_type", "String");
				}else if("3".equals(str)){
					hm.put("interface_parameter_type", "Date");
				}else if("2".equals(str)){
					hm.put("interface_parameter_type", "Double");
				}
       		    hm.put("interface_parameter_desc", dbhm.get("interface_parameter_desc"));
       		    hm.put("interface_config_id", dbhm.get("interface_config_id"));
				gridlist.add(hm);
			}
			grid.setValueList(gridlist);
		} else if("NEW".equals(opmode)) {                                                               //如果操作模式为NEW，即该方案为新建的，则执行以下代码
			//[NO.003]初始化新建数据访问接口方案界面
			int maxSeq = bs7.getMaxInterfaceConfigSeq();								                //获取所有方案中最大的序号
			int interfaceConfigSeq = maxSeq+1;	                                                        //接口方案序号
			this.getPageElement("interfaceConfigSequence").setValue(interfaceConfigSeq+"");				//将最大的方案序号加1赋值给序号
			this.getPageElement("interfaceConfigCreateUser").setValue(loginName);			            //将当前用户登录名赋值给创建方案创建人，页面元素为extractSchemeCreateUser
			this.getPageElement("interfaceConfigCreateUserName").setValue(bs7.getUserName(loginName));	//根据用户loginName，获取用户名字，将当前用户名字赋值给创建方案创建人名字，页面元素为extractSchemeCreateUser
			this.getPageElement("interfaceConfigChangeUser").setValue(loginName);						//将当前用户登录名赋值给方案最后修改人，页面元素为extractSchemeLastMUser
			this.getPageElement("interfaceConfigChangeUserName").setValue(bs7.getUserName(loginName));	//根据用户loginName，获取用户名字，将当前用户名字赋值给最后修改人名字，页面元素为extractSchemeCreateUser
			this.getPageElement("interfaceConfigCreateDate").setValue(bs7.formatDate(new Date()));      //初始化方案创建时间
			this.getPageElement("interfaceConfigChangeDate").setValue(bs7.formatDate(new Date()));      //初始化方案最后修改时间
			this.getPageElement("interfaceConfigStateId").setValue(availableStatus);                    //初始化有效性状态
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:save.保存页面数据的点击反应事件<br>
	* 方法创建日期:2016年03月7日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年3月7日<br>
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
	* 结果结构详述:提交保存了页面所填数据
	*====================================================================================================
	 * @throws AppException 
	*/
	@PageEvent("save.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int save() throws RadowException, AppException{
		String nodeId = this.getPageElement("nodeId").getValue();						//获取节点编码
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();						//获取当前操作模式，新建还是修改标志
		
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();	//获取抽取方案编码
		String interfaceConfigSequence = this.getPageElement("interfaceConfigSequence").getValue();
		String interfaceConfigName = this.getPageElement("interfaceConfigName").getValue();

		String availabilityStateId = this.getPageElement("interfaceConfigStateId").getValue();
		String interfaceConfigCreateDate = this.getPageElement("interfaceConfigCreateDate").getValue();
		String interfaceConfigDesc = this.getPageElement("interfaceConfigDesc").getValue();
		String interfaceConfigIsedit = this.getPageElement("interfaceConfigIsedit").getValue();
		if("".equals(interfaceConfigId) || interfaceConfigId == null){
			this.setMainMessage("您好，接口方案编码不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(interfaceConfigSequence) || interfaceConfigSequence == null){
			this.setMainMessage("您好，接口方案序号不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(interfaceConfigName) || interfaceConfigName == null){
			this.setMainMessage("您好，接口方案名称不能为空。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		InterfaceConfig config = null;					                                
		try {
			config = bs7.getConfigById(interfaceConfigId);                              //如果方案编码没有改变
		} catch (Exception e) {
			config = bs7.getConfigByIsn(nodeId_s[1]);                                   //如果方案编码改变了
		}
		
		if("NEW".equals(opmode)){														//判断当前方案是否是新建的方案
			if(null != config){															
				if("1".equals(config.getAvailabilityStateId())){						
					color = "FF6820";
				}
				this.getExecuteSG().addExecuteCode("odin.alert('您好，数据访问接口方案编码\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + interfaceConfigId + "</b></span>\"已存在，请重新输入数据访问接口方案编码。');");
	            return EventRtnType.NORMAL_SUCCESS;
			}
			config = new InterfaceConfig();												//创建方案抽取对象
	    }
		
		InterfaceConfig config1 = new InterfaceConfig();
		//拷备数据
		try {
			PropertyUtils.copyProperties(config1, config);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		config.setInterfaceConfigId(interfaceConfigId);
		config.setInterfaceConfigName(interfaceConfigName);
		config.setInterfaceConfigIsn(nodeId_s[1]);										//将节点编码赋值给抽取方案内码
		config.setInterfaceConfigDesc(interfaceConfigDesc);
		config.setInterfaceConfigIsedit(interfaceConfigIsedit);
		config.setPublishStateId("0");                                                  //第一次保存设置为未发布状态
		Date date = new Date();															//创建日期对象
		String dateStr = bs7.formatDate(date);										    //格式化日期对象为"yyyy-MM-dd"
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		String userName = bs7.getUserName(loginName);
		if("UPDATE".equals(opmode)){													//判断当前操作模式是否为"UPDATE"
			config.setInterfaceConfigCreateDate(bs7.parseDateStr(interfaceConfigCreateDate));
			config.setInterfaceConfigChangeDate(date);									//更新修改状态，更新方案最后修改日期
			config.setInterfaceConfigChangeUser(userName);
		}else{
			config.setInterfaceConfigCreateDate(date);									//更新创建状态，为创建日期赋值当前时间
			config.setInterfaceConfigChangeDate(date);									//更新最后修改状态，为最后修改日期赋值当前时间
			config.setInterfaceConfigCreateUser(userName);
			config.setInterfaceConfigChangeUser(userName);
		}	
		config.setInterfaceConfigSequence(Integer.valueOf(interfaceConfigSequence));
		config.setAvailabilityStateId(availabilityStateId);
		if(null == config.getAvailabilityStateId() || "root".equals(config.getAvailabilityStateId()) || "".equals(config.getAvailabilityStateId())){
			throw new RadowException("您好，数据访问接口方案有效性状态不能为空。");
		}																				
		
		//Session session = HibernateSessionFactory.currentSession();
		HBSession  session = HBUtil.getHBSession();
		//Transaction t = session.beginTransaction();
		//bs7.getSession().save(config);
		//session.save(config);
		session.saveOrUpdate(config);
		session.flush();
		//保存方案后记录日志
		if("UPDATE".equals(opmode)){
			try {
				new LogUtil().createLog("91", "InterfaceConfig",config.getInterfaceConfigIsn(), config.getInterfaceConfigName(), "", new Map2Temp().getLogInfo(config1, config));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("NEW".equals(opmode)){
			try {
				new LogUtil().createLog("90", "InterfaceConfig",config.getInterfaceConfigIsn(), config.getInterfaceConfigName(), "", new Map2Temp().getLogInfo(new InterfaceConfig(), config));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		/** 方案参数相关操作 */
		List<HashMap<String, String>> method = this.getPageElement("list").getStringValueList();

		String str = null;
		for(int i = 0; i < method.size(); i++){
			//定义变量
			HashMap<String, String> hm = method.get(i);
			InterfaceParameter ip = new InterfaceParameter();
			InterfaceParameter ip1 = new InterfaceParameter();
			InterfaceParameterId ipid = new InterfaceParameterId();
			ipid.setInterfaceConfigId(config.getInterfaceConfigId());
			boolean isHave = false;//页面中的数据在数据库是否已存在的标志
			//逻辑判断
			if("".equals(hm.get("interface_parameter_name")) || hm.get("interface_parameter_name") == null){
				this.setMainMessage("您好，参数编码不能为空。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			ipid.setInterfaceParameterName(hm.get("interface_parameter_name"));
			if("".equals(hm.get("interface_parameter_type")) || hm.get("interface_parameter_type") == null){
				this.setMainMessage("您好，参数类型不能为空。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//根据方案编号和方案参数序号查询方案参数是否已存在
			String queryInterfaceparameter = "from InterfaceParameter t where t.id.interfaceConfigId='"+interfaceConfigId+"' and t.interfaceParameterSequence='"+hm.get("interface_parameter_sequence")+"' ";
			List ip_list =   bs7.getSession().createQuery(queryInterfaceparameter).list();
			if(ip_list.size()>0){
				ip = (InterfaceParameter) ip_list.get(0);
				isHave = true;
				//拷备数据
				try {
					PropertyUtils.copyProperties(ip1, ip);
					deleteIp(interfaceConfigId,hm.get("interface_parameter_sequence"));
					ip = new InterfaceParameter();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			ip.setId(ipid);
			str = hm.get("interface_parameter_type").toLowerCase();
			if("string".equals(str) || "1".equals(str)){
				ip.setInterfaceParameterType("1");
			}else if("date".equals(str) || "3".equals(str)){
				ip.setInterfaceParameterType("3");
			}else if("double".equals(str) || "2".equals(str)){
				ip.setInterfaceParameterType("2");
			}
			ip.setInterfaceParameterDesc(hm.get("interface_parameter_desc"));
			ip.setInterfaceParameterSequence(Integer.parseInt(hm.get("interface_parameter_sequence")));
			List list = new ArrayList();
			session.save(ip);
			session.flush();
			//保存记录时，记录日志
			if(isHave){
				try{
					//比较两个对象中不同值的字段
					list = new Map2Temp().getLogInfo(ip1, ip);
					if(list.size()>0){
						new LogUtil().createLog("93", "InterfaceParameter",ip1.getId().getInterfaceParameterName(), ip1.getInterfaceParameterDesc(), "", new Map2Temp().getLogInfo(ip1, ip));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					new LogUtil().createLog("94", "InterfaceParameter",ip.getId().getInterfaceParameterName(), ip.getInterfaceParameterDesc(), "", new Map2Temp().getLogInfo(new InterfaceParameter(), ip));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}	
		//t.commit();
		session.getSession().clear();
		//[NO.005]更新UI
		if("NEW".equals(opmode)){																	//判断操作模式是否为NEW，即为新建方案
			this.getPageElement("interfaceConfigChangeDate").setValue(dateStr);						//设置抽取方案最后修改时间为当前时间
			/** 记录日志 */
		}else{
			this.getPageElement("interfaceConfigChangeDate").setValue(dateStr);						//如果不是新建方案，则只更新方案最后修改时间
			/** 记录日志 */
		}
		this.getPageElement("interfaceConfigName").setValue(config.getInterfaceConfigName());		//更新名称：为空白，默认值'extractSchemeId'影响
		this.getPageElement("opmode").setValue("UPDATE");                           				//设置操作模式为更新模式
		this.setMainMessage("您好，\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"数据访问接口方案已经保存成功。");//弹出对话框，提示数据访问接口方案保存成功
		//更新图标
		this.getExecuteSG().addExecuteCode("window.parent.updateTab('<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "',0);");//更新当前页面的显示标题
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");							//调用父页面刷新方法，刷新当前页面
		this.getExecuteSG().addExecuteCode("window.parent.deletehaveChange('" + nodeId + "');");	//调用父页面删除方法
		this.setNextEventName("queryObj");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	*====================================================================================================
	* 方法名称:delOnclick.参数配置信息页面中删除按钮的点击响应事件<br>
	* 方法创建日期:2016年03月9日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年04月9日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:参数配置信息页面中删除按钮的点击响应事件<br>
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
	* 结果结构详述:删除方案信息和脚本信息
	*====================================================================================================
	*/
	@PageEvent("delParam.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int delOnclick() throws RadowException{
		PageElement pe = this.getPageElement("list");					//获取页面表格元素对象
		List<HashMap<String, Object>> list = pe.getValueList();			//将页面表格元素存放在哈希表中
		if(null == list || list.size() == 0){							//判断列表中是否有数据
			this.setMainMessage("你好，数据访问接口方案下没有参数配置信息，无须删除。");					//如果，没有数据，弹出提示框
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!isChoosed()){												//判断是否选中表格数据
			this.setMainMessage("您好，您未选择参数配置信息，请选择要删除的参数配置信息。");	//弹出为选择参数配置信息提示框
			return EventRtnType.NORMAL_SUCCESS;
		}
		String strParamNames = "";
		for(int i = 0; i < list.size(); i++){							//以参数记录条数为条件循环
			HashMap<String, Object> hm = list.get(i);					//取出一条参数配置信息存放入哈希表中
			Object selected = hm.get("selected");						//判断该条参数信息是否被选中
			if(selected.equals(true) || selected.equals("1")){			//如果被选中
				strParamNames += "," + hm.get("interface_parameter_name").toString();//拼接被选中的参数编码串
			}
		}
		strParamNames = strParamNames.substring(1);							//删除参数编码串最前面的逗号“,”
		if(strParamNames==null || "".equals(strParamNames)) {
			this.setNextEventName("list.dogridquery");	
			return EventRtnType.NORMAL_SUCCESS;
		}
		String message = "您好，您确定要删除当前选中的方案参数信息？选择确定按钮将删除。";		//提示删除信息
		this.addNextEvent(NextEventValue.YES, "delParam", strParamNames);		//点击确定，跳转执行del方法，并传递参数编码串
		this.addNextEvent(NextEventValue.CANNEL, "");					//其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);					//消息框类型，即confirm类型窗口
		this.setMainMessage(message);									//窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	*====================================================================================================
	* 方法名称:delParam.执行方案参数删除任务<br>
	* 方法创建日期:2016年03月9日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月9日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:执行方案参数删除任务<br>
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
	* 结果结构详述:删除方案参数删除任务
	*====================================================================================================
	*/
	@PageEvent("delParam")
	@NoRequiredValidate
	public int del(String strParamNames) throws RadowException{
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();	//获取方案编码
		String[] paramNames = strParamNames.split(",");									//用逗号分隔参数编码串
		Transaction t = bs7.getSession().getTransaction();								//创建事务对象
		t.begin();																		//事务开始
		for(String paramName : paramNames){
			//InterfaceParameter ip = bs7.queryConfigParam(interfaceConfigId, paramName);
			bs7.delConfigParam(interfaceConfigId, paramName);							//根据所传数据访问接口方案编码、参数编码，删除对应的数据访问接口方案对应的方案参数配置信息
			try {
				new LogUtil().createLog("95", "InterfaceParameter",interfaceConfigId, paramName, "", new ArrayList());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		t.commit();																		//事务提交
		
		this.setNextEventName("list.dogridquery");										//调用下一个方法，方法名为list.dogridquery
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:delBtnOnclick.删除方案信息和脚本信息的点击响应事件<br>
	* 方法创建日期:2016年03月9日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月9日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:删除方案信息和脚本信息的点击响应事件<br>
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
	* 结果结构详述:删除方案信息和脚本信息
	*====================================================================================================
	*/
	@PageEvent("delBtn.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int delBtnOnclick() throws RadowException{
		String interfaceConfigId = this.getPageElement("interfaceConfigId").getValue();		//获取数据访问接口方案编码
		String opmode = this.getPageElement("opmode").getValue();							//获取当前操作模式
		String message = "";
		if("UPDATE".equals(opmode)){														//判断操作模式是否为更新
			InterfaceConfig config = bs7.getConfigById(interfaceConfigId);					//根据方案编码，创建抽取方案对象
			List<InterfaceScript> ifslist = bs7.getScripts(interfaceConfigId);				//根据数据访问接口方案编码，获取该方案下对应数据访问接口方案脚本信息
			if(ifslist.size() > 0){															//判断给方案下是否有方案脚本
				this.setMainMessage("您好，该数据访问接口方案有<span style=\"font-family:Arial;display:inline-block;color:red;font-size:11px;\"><b>" + ifslist.size() + "</b></span>个下属数据访问接口脚本信息，不允许删除。");			//窗口提示信息
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("1".equals(config.getAvailabilityStateId())){								//判断方案有效性编码是否为"1"
				color = "FF6820";
			}
			message = "您好，您确定要删除\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"数据访问接口方案及其对应的方案参数配置信息？选择确定按钮将删除。";//弹出删除确认提示框
		}else{
			message = "您好，您确定要删除当前新建中的数据访问接口方案信息？选择确定按钮将删除。";						//删除新建的方案信息提示信息
		}
		this.addNextEvent(NextEventValue.YES, "sureClear");									//点击确定按钮，执行sureClear
		this.addNextEvent(NextEventValue.CANNEL, "");										//点击取消按钮，退出
		this.setMessageType(EventMessageType.CONFIRM);										//消息框类型，即confirm类型窗口
		this.setMainMessage(message);														//窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:sureClear.删除方案信息和脚本信息的点击响应事件<br>
	* 方法创建日期:2016年03月9日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月9日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:删除方案信息和脚本信息的点击响应事件<br>
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
	* 结果结构详述:删除方案信息和脚本信息
	*====================================================================================================
	*/
	@PageEvent("sureClear")
	@NoRequiredValidate
	public int sureClear() throws RadowException{
		String nodeId = this.getPageElement("nodeId").getValue();					//获取方案内码
		String[] nodeId_s = nodeId.split("_");
		String opmode = this.getPageElement("opmode").getValue();					//获取当前操作模式
		if("UPDATE".equals(opmode)){												//判断操作模式是否是更新
			InterfaceConfig config = bs7.getConfigByIsn(nodeId_s[1]);						//根据方案内码，创建方案信息对象
			if("1".equals(config.getAvailabilityStateId())){						//判断有效性编码是否为"1"
				color = "FF6820";
			}
			try{
				bs7.delConfig(config.getInterfaceConfigId());						//根据所传数据访问接口方案编码，删除对应的数据访问接口方案信息及其对应的方案参数配置信息
				bs7.delConfigParameters(config.getInterfaceConfigId());				//根据所传数据访问接口方案编码，删除对应的数据访问接口方案对应的方案参数配置信息
				/** 删除时记录日志*/
				new LogUtil().createLog("92", "InterfaceConfig",config.getInterfaceConfigIsn(), config.getInterfaceConfigName(), "", new ArrayList());
				
				this.setMainMessage("您好，删除\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"数据访问接口方案成功。");//弹出删除成功提示框
			}catch(Exception e){
				/** 记录日志  待添加代码*/
				this.setMainMessage("您好，删除\"<span style=\"font-family:Arial;display:inline-block;color:" + color + ";font-size:11px;\"><b>" + config.getInterfaceConfigId() + "</b></span>." + config.getInterfaceConfigName() + "\"数据访问接口方案失败。");//弹出方案删除失败提示框
				throw new RadowException("删除失败:" + e.getMessage());                 //抛出删除失败异常
			}
		}
		//[NO.001]删除对应的树节点
		this.getExecuteSG().addExecuteCode("window.parent.delTreeNode('" + nodeId + "');"); //根据树节点编码，删除方案节点
		this.getExecuteSG().addExecuteCode("window.parent.removeTab('" + nodeId + "');");   //根据节点比编码，关闭tab页
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:gridQuery.查询方案参数配置<br>
	* 方法创建日期:2016年03月9日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月9日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:查询方案参数配置<br>
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】<br>
	* 输入参数
	* <table>
	*  参数序号				参数名称				参数描述				参数数据类型
	*  <li>(01)
	* </table>
	* 返回结果
	* <table>
	*  结果序号				结果名称				结果描述				结果数据类型
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   返回成功状态				   int
	* </table>
	* 结果结构详述:查询方案参数配置，刷新参数列表
	*====================================================================================================
	*/
	@PageEvent("list.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	@GridDataRange
	public int gridQuery(int start, int limit) throws Exception {
		String configId = this.getPageElement("interfaceConfigId").getValue();			//获取当前方案编码
		InterfaceConfig config = bs7.getConfigById(configId);							//根据方案编码，创建方案对象
		String sql = "select t.INTERFACE_CONFIG_ID,t.INTERFACE_PARAMETER_SEQUENCE,"
				   + "t.INTERFACE_PARAMETER_NAME,t.INTERFACE_PARAMETER_DESC,t.INTERFACE_PARAMETER_TYPE "
				   + "from interface_parameter t";									    //查询方案配置信息
		if(null == config)
			sql += " where 1=2";
		else
			sql += " where t.INTERFACE_CONFIG_ID='" + config.getInterfaceConfigId() + "' order by t.INTERFACE_PARAMETER_SEQUENCE";//查询语句追加限制条件
		this.pageQuery(sql, "SQL", start, 100);								            //处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 判断参数列表中参数是否被选中
	 * @return
	 * @throws RadowException
	 */
	private boolean isChoosed() throws RadowException{
		PageElement pe = this.getPageElement("list");				//获取页面表格对象
		List<HashMap<String, Object>> list = pe.getValueList(); 	//将表格数据对象存入哈希表
		for(int i = 0; i < list.size(); i++){						//根据链表长度循环
			HashMap<String, Object> map = list.get(i);				//获取哈希表存放的对象
			Object selected = map.get("selected");					//获取页面选择字段元素对象
			if(selected == null){
				return false;
			}
			if(selected.equals(true) || selected.equals("1")){		//判断是否选中标志
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 修改参数编码时先删除后增加
	 * @param interfaceConfigId
	 * @param seq
	 */
	public void deleteIp(String interfaceConfigId,String seq){
		ZWWebserviceImpl zim = new ZWWebserviceImpl();
		Connection conn = zim.getConn();
		PreparedStatement pst = null;
		String sql = "delete  from Interface_Parameter  where interface_Config_Id=? and interface_Parameter_Sequence=? ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, interfaceConfigId);
			pst.setString(2, seq);
			pst.executeUpdate();
			if (pst != null)pst.close();
			if (conn != null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(pst!=null)pst.close();
			} catch (SQLException e1) {
			}
		}
		
	}
}
