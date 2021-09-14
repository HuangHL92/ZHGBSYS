package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
/**
 * 补充信息主页面
 * @author huangcheng
 *
 */
public class ZWHZYQ_001_006_MainPageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	String color = "Grey";	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	/**
	*====================================================================================================
	* 方法名称:getTreeJsonData.生成补充信息集树形结构<br>
	* 方法创建日期:2016年03月23日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月23日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:生成补充信息集树的结构<br>
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
	* 结果结构详述:生成补充信息集树形结构页面
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //获取所有的补充信息类型集合
		List<AddType> addTypeList = bs6.getAddTypes();                                    
		StringBuffer jsonStr = new StringBuffer();							          //创建字符串容器对象，用来传递给EXT树
		if(addTypeList != null && !addTypeList.isEmpty()){
			if(addTypeList.size() > 0){
				for(int i = 0; i < addTypeList.size(); i++){
					AddType addType = addTypeList.get(i);                             //链表中的每一个补充信息类型
					color = "red";
					String title =addType.getAddTypeName();
					String isn = addType.getAddTypeId();                              //信息类型的id
					if(i==0) {
						jsonStr.append("[{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn + "\"");
					} else {
						jsonStr.append(",{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn + "\"");
					}
					jsonStr.append(",\"leaf\":true}");
				}
				jsonStr.append("]");
			}
		}
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
	}
	/**
	*====================================================================================================
	* 方法名称:addNewType.新增信息集<br>
	* 方法创建日期:2016年03月23日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月23日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:新增信息集<br>
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
	* 结果结构详述:生成补充信息集树形结构页面
	*====================================================================================================
	*/
	@PageEvent("addNewType")
	public int addNewType(String nodeId) throws RadowException {
		this.setRadow_parent_data(nodeId);
		this.openWindow("addNewType", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.ZWHZYQ_001_006_AddType");//事件处理完后的打开窗口事件
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 根据nodeId删除信息集
	 * @param nodeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("deleteAddType")
	public int deleteAddType(String nodeId) {
		String message = "您好，您确定要删除当前选中的补充信息集？选择确定按钮将删除。";		//提示删除信息
		List<AddValue> list = bs6.getSession().createQuery("from AddValue where addTypeId=:addTypeId and publishStatus='1'")
				              .setParameter("addTypeId", nodeId).list();
		if(list.size()!=0) {
			this.setMainMessage("该信息集下的新增信息项有已经发布的，不可以删除。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(nodeId==null || "".equals(nodeId) || "S000000".equals(nodeId)) {
			this.setMainMessage("请选择一个信息集。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "sureDel", nodeId);		        //点击确定，跳转执行del方法，并传递参数编码串
		this.addNextEvent(NextEventValue.CANNEL, "");					        //其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);					        //消息框类型，即confirm类型窗口
		this.setMainMessage(message);									        //窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 确定删除补充信息集
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	@PageEvent("sureDel")
	public int sureDel(String nodeId) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		HBSession session = bs6.getSession();
		AddType addType = (AddType)session.createQuery("from AddType where addTypeId=:nodeId").setParameter("nodeId", nodeId).uniqueResult();
		Transaction t = session.getTransaction();
		t.begin();
		//删除信息集类型
		session.createSQLQuery("delete from ADD_TYPE where ADD_TYPE_ID=:nodeId").setParameter("nodeId", nodeId).executeUpdate();
		//删除信息集下的所有信息项
		session.createSQLQuery("delete from ADD_VALUE where ADD_TYPE_ID=:nodeId").setParameter("nodeId", nodeId).executeUpdate();
		t.commit();
		this.getExecuteSG().addExecuteCode("reloadTree()");
		this.getExecuteSG().addExecuteCode("clearNodeId()");
		this.reloadPage();
		try {
			try {
				new LogUtil().createLog("71", addType.getTableCode(), addType.getAddTypeId(), addType.getAddTypeName(), "删除补充信息集", new Map2Temp().getLogInfo(addType,new AddType()));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 修改补充信息集
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("modifyAddType")
	public int modifyAddType(String nodeId) throws RadowException {
		if(nodeId==null || "".equals(nodeId) || "S000000".equals(nodeId)) {
			this.setMainMessage("您好,请您选择一个补充信息集的节点.");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<AddValue> list = bs6.getSession().createQuery("from AddValue where addTypeId=:addTypeId and publishStatus='1'")
	              .setParameter("addTypeId", nodeId).list();
		if(list.size()!=0) {
			this.setMainMessage("该信息集下的新增信息项有已经发布的，不可以修改。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data(nodeId);
		this.openWindow("modifyAddType", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.ZWHZYQ_001_006_ModifyAddType");//事件处理完后的打开窗口事件
		return EventRtnType.NORMAL_SUCCESS;
	}
}
