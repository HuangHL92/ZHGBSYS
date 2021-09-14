package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ZWHZYQ_001_006_AddValuePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 显示所有补充信息集下面的信息项
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PageEvent("list.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	@GridDataRange
	public int gridQuery(int start, int limit) throws Exception {
		String nodeId = this.getPageElement("nodeId").getValue();			            //获取当前方案编码
		String sql = null;
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql = "select t.add_value_sequence,t.add_value_name,t.add_value_id,"
					   + "t.add_type_id,t.code_type,"
					   + "t.add_value_detail,"
					   + "decode(publish_status,'0','<img src=\"images/icon/error.gif\"></img>未发布','1','<img  src=\"images/icon/right.gif\"></img>发布') publish_status,"
					   + "decode(t.isused,'0','<img src=\"images/icon/error.gif\"></img>否','1','<img  src=\"images/icon/right.gif\"></img>是') isused,"
					   + "t.multilineshow "
					   + "from add_value t where t.add_type_id='"+nodeId+"' order by t.add_value_sequence";
		} else if(DBType.MYSQL==DBUtil.getDBType()) {
			sql = "select t.add_value_sequence,t.add_value_name,t.add_value_id,"
					   + "t.add_type_id,t.code_type,"
					   + "t.add_value_detail,"
					   + "if(publish_status='0','<img src=\"images/icon/error.gif\"></img>未发布','<img src=\"images/icon/right.gif\"></img>发布') publish_status,"
					   + "if(isused='0','<img src=\"images/icon/error.gif\"></img>否','<img src=\"images/icon/right.gif\"></img>是') isused,"
					   + "t.multilineshow "
					   + "from add_value t where t.add_type_id='"+nodeId+"' order by t.add_value_sequence";
		}
		this.pageQuery(sql, "SQL", start, 100);								            //处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	

	@PageEvent("addAction")
	public int addAction(String nodeId) throws Exception {
		if(nodeId == null || "".equals(nodeId) || "S000000".equals(nodeId)) {
			this.setMainMessage("请选择一个信息集树上的一个节点。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data("NEW_"+nodeId);
		this.openWindow("addNewValue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.AddValueCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("update")
	public int update(String nodeId) throws Exception {
		this.setRadow_parent_data("UPDATE_"+nodeId);
		this.openWindow("updateAddValue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.AddValueCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("disPlayDsa")
	public int disPlayDsa() throws Exception {
		this.setNextEventName("list.dogridquery");	
		return EventRtnType.NORMAL_SUCCESS;
		
	}

	@PageEvent("update.onclick")
	public int updateOne() throws RadowException{
		PageElement pe = this.getPageElement("list");					//获取页面表格元素对象
		List<HashMap<String, Object>> list = pe.getValueList();			//将页面表格元素存放在哈希表中
		if(!isChoosed()){												//判断是否选中表格数据
			this.setMainMessage("您好，您未选择信息项，请选择要修改的信息项。");	    //弹出为选择参数配置信息提示框
			return EventRtnType.NORMAL_SUCCESS;
		}
		int count = 0;//用来记录被勾选中的信息项数量
		String nodeId = "";//唯一被选中的信息项id
		for(int i = 0; i < list.size(); i++){							//以参数记录条数为条件循环
			HashMap<String, Object> hm = list.get(i);					//取出一条参数配置信息存放入哈希表中
			Object selected = hm.get("selected");						//判断该条参数信息是否被选中
			if(selected.equals(true) || selected.equals("1")){			//如果被选中
				count++;
				AddValue addValue = bs6.getAddValueById(hm.get("add_value_id").toString());
				if("1".equals(addValue.getPublishStatus())){
					this.setMainMessage("您好，您选择的信息项中有发布过的，不能修改。");
					return EventRtnType.NORMAL_SUCCESS;
				}
				nodeId += (String) hm.get("add_value_id");
			}
		}
		
		if(count>1) {
			this.setMainMessage("您好，请您选择一条信息项进行修改。");
			return EventRtnType.NORMAL_SUCCESS;
		} 
		
		this.setRadow_parent_data("UPDATE_"+nodeId);
		this.openWindow("updateAddValue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.AddValueCue");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delParam.onclick")
	@AutoNoMask
	public int delete() throws RadowException{
		PageElement pe = this.getPageElement("list");					//获取页面表格元素对象
		List<HashMap<String, Object>> list = pe.getValueList();			//将页面表格元素存放在哈希表中
		if(null == list || list.size() == 0){							//判断列表中是否有数据
			this.setMainMessage("你好，数据访问信息集下没有信息项，无须删除。");		//如果，没有数据，弹出提示框
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!isChoosed()){												//判断是否选中表格数据
			this.setMainMessage("您好，您未选择信息项息，请选择要删除的信息项。");	    //弹出为选择参数配置信息提示框
			return EventRtnType.NORMAL_SUCCESS;
		}
		String strAddValueIds = "";
		for(int i = 0; i < list.size(); i++){							//以参数记录条数为条件循环
			HashMap<String, Object> hm = list.get(i);					//取出一条参数配置信息存放入哈希表中
			Object selected = hm.get("selected");						//判断该条参数信息是否被选中
			if(selected.equals(true) || selected.equals("1")){			//如果被选中
				AddValue addValue = bs6.getAddValueById(hm.get("add_value_id").toString());
				if("1".equals(addValue.getPublishStatus())){
					this.setMainMessage("您好，您选择的信息项中有发布过的，不能删除。");
					return EventRtnType.NORMAL_SUCCESS;
				}
				strAddValueIds += "," + hm.get("add_value_id").toString();//拼接被选中的参数编码串
			}
		}
		strAddValueIds = strAddValueIds.substring(1);						//删除参数编码串最前面的逗号“,”
		String message = "您好，您确定要删除当前选中的方案信息项？选择确定按钮将删除。";		//提示删除信息
		this.addNextEvent(NextEventValue.YES, "sureDel", strAddValueIds);	//点击确定，跳转执行del方法，并传递参数编码串
		this.addNextEvent(NextEventValue.CANNEL, "");					    //其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);					    //消息框类型，即confirm类型窗口
		this.setMainMessage(message);									    //窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("sureDel")
	public int sureDel(String strAddValueIds) throws RadowException{
		String[] addValueIds = strAddValueIds.split(",");								//用逗号分隔参数编码串
		Transaction t = bs6.getSession().getTransaction();								//创建事务对象
		t.begin();																		//事务开始
		for(String addValueId : addValueIds) {
			//查询数据
			AddValue addValue = (AddValue) bs6.getSession().createQuery("FROM AddValue where addValueId=:addValueId").setParameter("addValueId", addValueId).list().get(0);
			bs6.getSession().createQuery("delete from AddValue  where addValueId=:addValueId")
			.setParameter("addValueId", addValueId).executeUpdate();
			//记录日志
			try {
				new LogUtil().createLog("75", "ADDVALUE", addValue.getAddValueId(), addValue.getAddValueName(), "", new Map2Temp().getLogInfo(addValue,new AddValue()));
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
	 * 下发事件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("deliver.onclick")
	public int deliver() throws RadowException {
		this.openWindow("deliverCue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001.DeliverCue");
		return EventRtnType.NORMAL_SUCCESS;
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
	
	@PageEvent("publish")
	public int publish(String addValueId){
		AddValue addValue = bs6.getAddValueById(addValueId);
		if("0".equals(addValue.getIsused())) {
			this.setMainMessage("您好，您选择的信息项尚未使用，请修改使用状态后再进行发布。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("1".equals(addValue.getPublishStatus())) {
			this.setMainMessage("您好，您选择的信息项已经发布。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String message = "您好，您确定要发布当前选中的方案信息项？发布后将不能修改和删除。";		//提示删除信息
		this.addNextEvent(NextEventValue.YES, "suerPub", addValueId);		//点击确定，跳转执行del方法，并传递参数编码串
		this.addNextEvent(NextEventValue.CANNEL, "");					//其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);					//消息框类型，即confirm类型窗口
		this.setMainMessage(message);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("suerPub")
	public int suerPub(String addValueId){
		AddValue addValue = bs6.getAddValueById(addValueId);
		addValue.setPublishStatus("1");
		CodeTableCol codeTableCol = new CodeTableCol();
		UUID uuid = UUID.randomUUID();
		AddType addType = bs6.getAddTypeById(addValue.getAddTypeId());
		codeTableCol.setCtci(uuid.toString());
		codeTableCol.setTableCode(addType.getTableCode());
		codeTableCol.setColCode(addValue.getColCode());
		codeTableCol.setColName(addValue.getAddValueName());
		String codeType = addValue.getCodeType();
		codeTableCol.setCodeType(codeType);
		codeTableCol.setIsNewCodeCol("1");//代表其是新增指标项
		codeTableCol.setColLectionName("自定义信息项集");
		String colType = "0".equals(addValue.getColType())?"VARCHAR2":"1".equals(addValue.getColType())?"NUMBER":"DATE";
		codeTableCol.setColDataTypeShould(colType);
		codeTableCol.setIsZbx("0");
		codeTableCol.setZbxtj("0");
		Transaction t = bs6.getSession().getTransaction();
		t.begin();
		bs6.getSession().saveOrUpdate(addValue);
		bs6.getSession().save(codeTableCol);
		t.commit();
		this.setNextEventName("list.dogridquery");	
		return EventRtnType.NORMAL_SUCCESS;
	}
}
