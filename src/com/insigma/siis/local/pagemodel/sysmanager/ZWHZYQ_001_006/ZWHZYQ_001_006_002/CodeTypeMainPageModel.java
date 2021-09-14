package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002;

import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
/**
 * 补充信息主页面
 * @author huangcheng
 *
 */
public class CodeTypeMainPageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	String color = "Grey";	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	*====================================================================================================
	* 方法名称:getTreeJsonData.生成扩充标准代码信息集树形结构<br>
	* 方法创建日期:2016年03月23日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月23日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:生成补充信息集树的结构<br>
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计 书【ZWHZYQ_001 系统管理】<br>
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
	* 结果结构详述:生成补充信息集树形结构页面
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //获取所有的补充信息类型集合
		StringBuffer jsonStr = new StringBuffer();                            //创建字符串容器对象，用来传递给EXT树
		//学历学位
		jsonStr.append("[{\"text\" :\"学历学位信息\" ,\"id\" :\"menu_xlxw\",\"cls\" :\"folder\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"学历代码\" ,\"id\" :\"leaf_ZB64\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"学位代码\" ,\"id\" :\"leaf_GB6864\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"教育类别代码\" ,\"id\" :\"leaf_ZB123\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"专业代码\" ,\"id\" :\"leaf_GB16835\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		
		//职务信息
		jsonStr.append("{\"text\" :\"职务信息\" ,\"id\" :\"menu_zw\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"职务层次代码\" ,\"id\" :\"leaf_ZB09\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职务类别代码\" ,\"id\" :\"leaf_ZB42\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职动类型代码\" ,\"id\" :\"leaf_ZB13\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"免职类型代码\" ,\"id\" :\"leaf_ZB16\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"任职状态代码\" ,\"id\" :\"leaf_ZB14\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"交流去向代码\" ,\"id\" :\"leaf_ZB74\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"交流方式代码\" ,\"id\" :\"leaf_ZB72\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"交流原因代码\" ,\"id\" :\"leaf_ZB73\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"选拔任用方式代码\" ,\"id\" :\"leaf_ZB122\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"专业技术资格代码\" ,\"id\" :\"leaf_GB8561\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"取得资格途径代码\" ,\"id\" :\"leaf_ZB24\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"职务名称代码\" ,\"id\" :\"leaf_ZB08\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职级代码\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"岗位类别代码\" ,\"id\" :\"leaf_ZB127\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职务等级代码\" ,\"id\" :\"leaf_ZB136\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		
		jsonStr.append("{\"text\" :\"成员类别代码\" ,\"id\" :\"leaf_ZB129\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//机构信息
		jsonStr.append("{\"text\" :\"机构信息\" ,\"id\" :\"menu_jg\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"机构级别代码\" ,\"id\" :\"leaf_ZB03\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"机构级别性质代码\" ,\"id\" :\"leaf_ZB04\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"单位隶属关系代码\" ,\"id\" :\"leaf_ZB87\",\"cls\" :\"folder\",\"leaf\":true}]},");
		//人员信息
		jsonStr.append("{\"text\" :\"人员信息\" ,\"id\" :\"menu_ry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"籍贯出生地代码\" ,\"id\" :\"leaf_ZB01\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"政治身份代码\" ,\"id\" :\"leaf_GB4762\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"人员类别代码\" ,\"id\" :\"leaf_ZB125\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"人员状态代码\" ,\"id\" :\"leaf_ZB126\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"培训类别代码\" ,\"id\" :\"leaf_ZB29\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"培训离岗状态代码\" ,\"id\" :\"leaf_ZB30\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"培训机构类别代码\" ,\"id\" :\"leaf_ZB27\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"奖惩名称代码\" ,\"id\" :\"leaf_ZB65\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"进入本单位方式代码\" ,\"id\" :\"leaf_ZB77\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"退出管理方式代码\" ,\"id\" :\"leaf_ZB78\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"离退类别代码\" ,\"id\" :\"leaf_ZB132\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"称谓代码\" ,\"id\" :\"leaf_GB4761\",\"cls\" :\"folder\",\"leaf\":true},");
		
		//------fujun
		jsonStr.append("{\"text\" :\"批准机关性质代码\" ,\"id\" :\"leaf_ZB128\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"管理类别代码\" ,\"id\" :\"leaf_ZB130\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职级代码\" ,\"id\" :\"leaf_ZB133\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"级别代码\" ,\"id\" :\"leaf_ZB134\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"编制类型代码\" ,\"id\" :\"leaf_ZB135\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"选调生类别代码\" ,\"id\" :\"leaf_ZB137\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"选调生来源类别代码\" ,\"id\" :\"leaf_ZB138\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"专业技术类公务员任职资格代码\" ,\"id\" :\"leaf_ZB139\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"工作单位性质代码\" ,\"id\" :\"leaf_ZB140\",\"cls\" :\"folder\",\"leaf\":true},");
		//------fujun
		
		jsonStr.append("{\"text\" :\"考核结果代码\" ,\"id\" :\"leaf_ZB18\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//公开遴选选调信息
		jsonStr.append("{\"text\" :\"遴选选调信息\" ,\"id\" :\"menu_lxxd\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"工作单位层次代码\" ,\"id\" :\"leaf_ZB141\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"遴选或选调类别\" ,\"id\" :\"leaf_ZB142\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"遴选方式\" ,\"id\" :\"leaf_ZB143\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"原单位类别\" ,\"id\" :\"leaf_ZB144\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职称代码\" ,\"id\" :\"leaf_ZB145\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//考试录用人员信息
		jsonStr.append("{\"text\" :\"考试录用人员信息\" ,\"id\" :\"menu_kslyry\",");
		jsonStr.append("\"children\":[");
		jsonStr.append("{\"text\" :\"人员来源情况代码\" ,\"id\" :\"leaf_ZB146\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"基层项目人员代码\" ,\"id\" :\"leaf_ZB147\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
		
	}
}
