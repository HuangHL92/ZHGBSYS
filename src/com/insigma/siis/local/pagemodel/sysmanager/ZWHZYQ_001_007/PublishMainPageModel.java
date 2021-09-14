package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.util.List;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;

public class PublishMainPageModel extends PageModel{


	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	String color = "Grey";
	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	*====================================================================================================
	* 方法名称:getTreeJsonData.获取目录树下的数据<br>
	* 方法创建日期:2016年03月04日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:获取目录树下的数据<br>
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】<br>
	* 输入参数
	* <table>
	*  参数序号				参数名称				参数描述				参数数据类型
	*  <li>(01)
	* </table>
	* 返回结果
	* <table>
	*  结果序号				结果名称				结果描述				结果数据类型
	*  <li>(01)		EventRtnType.XML_SUCCESS   返回成功状态				  int
	* </table>
	* 结果结构详述:页面数据以树形目录展开
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{
		List<InterfaceConfig> interfaceConfigs = bs7.getInterfaceConfigTree();                                      //获取所有的方案
		StringBuffer jsonStr = new StringBuffer();							                                        //创建字符串容器对象，用来传递给EXT树
		String contextPath = "module_img/ZWHZYQ_001_007/";	                                                        //存放图片相对路径地址
		String str1 = "";
		String str2 = "</b></span>";
		String configPath = contextPath + "level2_16.gif";                                                                //存放图片的路径
		if(interfaceConfigs != null && !interfaceConfigs.isEmpty()){
			if(interfaceConfigs.size() > 0){
				for(int i = 0; i < interfaceConfigs.size(); i++){
					InterfaceConfig interfaceConfig = interfaceConfigs.get(i);                                      //链表中的每一个接口方案
					if("1".equals(interfaceConfig.getAvailabilityStateId())){                                       //如果接口方案是有效的
						color = "FF6820";                                                                           //颜色设置为红色
					}
					str1 = "<span style='display:inline-block;color:" + color + ";font-size:11px'><b>";
					String title = str1 + interfaceConfig.getInterfaceConfigId() + str2 + "." + interfaceConfig.getInterfaceConfigName();
					String isn_fa = "FA_" + interfaceConfig.getInterfaceConfigIsn();                                //方案的内码，前面加FA_是为了区分脚本
					if(i==0) {
						jsonStr.append("[{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn_fa + "\" ,\"cls\" :\"folder\",\"icon\":\"" + configPath + "\",\"leaf\":true}");
					} else {
						jsonStr.append(",{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn_fa + "\" ,\"cls\" :\"folder\",\"icon\":\"" + configPath + "\",\"leaf\":true}");
					}
				}
				jsonStr.append("]");
			}else{
				jsonStr.append("]");
			}
		}
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
	}
}
