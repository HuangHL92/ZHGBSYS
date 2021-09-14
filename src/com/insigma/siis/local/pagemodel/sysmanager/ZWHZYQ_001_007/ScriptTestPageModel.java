package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.epsoft.util.JsonUtil;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.AutoGrid02;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.Column;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.DataAccessKitUtil;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.GridTitle02;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.Head;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.Table;

/**
*====================================================================================================
* 模块编码:ZWHZYQ_001_007   模块名称:汇总版访问接口脚本测试<br>
* 模块类型:○编辑类 ●展现类 ○任务类<br>
* 模块创建日期:2015年09月15日<br>
* 模块创建人员:黄程<br>
* 模块功能描述:管理数据访问接口脚本测试<br>
* 本文件功能描述:数据访问接口脚本测试响应事件<br>
* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】<br>
*====================================================================================================
*/
public class ScriptTestPageModel  extends PageModel{

	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	private static String QUERY_PARAM;
	@Override
	public int doInit() throws RadowException{
		this.setNextEventName("queryObj");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 加载测试页面初始值QUERY_PARAM，将其显示在第一个表格上，回显方案和脚本信息
	 * @retur n
	 * @throws RadowException
	 */
	@PageEvent("queryObj")
	@NoRequiredValidate
	@AutoNoMask
	public int queryObj() throws RadowException{
		String nodeId = this.getRadow_parent_data().substring(3);												//获取当前方案的方案编码
		this.getPageElement("nodeId").setValue(nodeId);
		//获取脚本对象
		InterfaceScript isf = bs7.getScriptByIsn(nodeId);
		//获取脚本编码INTERFACE_SCRIPT_ID
		String interface_script_id = isf.getInterfaceScriptId();
		this.getPageElement("interface_script_id").setValue(interface_script_id);
		//获取脚本名称INTERFACE_SCRIPT_NAME
		String interface_script_name = isf.getInterfaceScriptName();
		
		//获取方案编码INTERFACE_CONFIG_ID
		String interface_config_id = isf.getInterfaceConfigId();
		//获得方案对象
		InterfaceConfig isc = bs7.getConfigById(interface_config_id) ;
		this.getPageElement("interface_config_id").setValue(interface_config_id);
		//获得方案名称INTERFACE_CONFIG_NAME
		String interface_config_name = isc.getInterfaceConfigName();
		//将获得到的方案名称id及脚本名称id回显
		this.getPageElement("interfaceconfigidname").setValue(interface_config_id+"."+interface_config_name);
		this.getPageElement("interfacescriptidname").setValue(interface_script_id+"."+interface_script_name);
		//加载测试页面初始值QUERY_PARAM，将其显示在第一个表格上
		List<HashMap<String, Object>> gridlist = new ArrayList<HashMap<String, Object>>();       //存参数信息
		Grid grid = (Grid)this.createPageElement("list", ElementType.GRID, false);
		List list = bs7.doQueryPrama(interface_config_id);
		for(int i = 0; i < list.size(); i++){
			HashMap dbhm = (HashMap)list.get(i);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("interface_parameter_sequence", dbhm.get("interface_parameter_sequence"));
			hm.put("interface_parameter_name", dbhm.get("interface_parameter_name"));
			hm.put("interface_parameter_type", dbhm.get("interface_parameter_type"));
   		    hm.put("interface_parameter_desc", dbhm.get("interface_parameter_desc"));
   		    hm.put("paramValue", "请输入参数值");
   		    gridlist.add(hm);
		}
		grid.setValueList(gridlist);
		QUERY_PARAM = genereteQUERY_PARAM(gridlist);
		this.getPageElement("QUERY_PARAM").setValue(QUERY_PARAM);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 点击执行按钮后，将脚本解析的结果显示于第二个表格中
	 * @return
	 */
	@PageEvent("doAction")
	@AutoNoMask
	public int doAction() throws Exception {
		String interface_config_id = this.getParameter("interface_config_id");
		String interface_script_id = this.getParameter("interface_script_id");
		ZWWebserviceImpl zwWebservice = new ZWWebserviceImpl();
		zwWebservice.setStatus("1");
		String xmlData = zwWebservice.zwhzyqAccessit(interface_config_id, interface_script_id, QUERY_PARAM);
		//获取查询据结果的数据集名称
		HashMap<String,Head> scripts =  DataAccessKitUtil.getScriptHead(xmlData);
		String eleName = "";
		if(scripts != null) {
		   Set<Entry<String,Head>> set = scripts.entrySet();
		   for(Entry<String,Head> entry : set) {
			   eleName = entry.getKey();
		   }
		}
		//解析Data
		List<GridTitle02> gts = null;
		String dataStr = "[";
	   	String rowDataStr = "";
	   	List<Table> tables = DataAccessKitUtil.praseXml(xmlData, eleName);
	   	int tableSize = 0;
	   	int columnSize = 0;
	   	if(tables != null) {
	   	   tableSize = tables.size();
		   for(int i=0; i<tables.size(); i++) {
			   gts = new ArrayList<GridTitle02>();
			   rowDataStr = rowDataStr + "{'QUOTE_TARGET_DATA_ISN':'"+i+"',";
			   List<Column> columns = tables.get(i).getColumns();
			   columnSize = columns.size();
			   for(int j=0; j<columns.size(); j++) {
				   if(j < columns.size()-1) {
					   rowDataStr += "'"+columns.get(j).getName()+"':'"+columns.get(j).getValue()+"',";
				   } else {
					   rowDataStr += "'"+columns.get(j).getName()+"':'"+columns.get(j).getValue()+"'";
				   }
				   GridTitle02 gt =new GridTitle02();
				   gt.setEditType("Text");
				   gt.setAlign("left");
				   gt.setHeader(columns.get(j).getName());												//设置列标题为维度编码
				   gt.setDataIndex(columns.get(j).getName());										    //设置DataIndex数据来源为维度编码
				   gt.setWidth("100");																			//设置宽度属性
				   gt.setHeaderTitle(columns.get(j).getName());								       	//设置每列的列名
				   gt.setDisabled("false");
				   gts.add(gt);
			   }
			   if(i < tables.size()-1) {
				   rowDataStr += "},";
			   } else {
				   rowDataStr += "}";
			   }
		   }
	   	}
	   	dataStr += rowDataStr+"]";
		//返回的信息
		String remessage = "";
		InterfaceScript ifs = bs7.getScriptById(interface_config_id, interface_script_id);				//获得该方案的脚本
		String originalSql = ifs.getInterfaceScriptSql();												//获得原始sql语句
		originalSql = originalSql.replaceAll("[\\n]", "\\\\n");													//将换行符替换为空格
		originalSql = originalSql.replaceAll("[\\r]", "");
		//	获得执行sql
		String execSql = originalSql;													
		List<Param> params = JsonUtil.JsonToParam(QUERY_PARAM);
		for(Param param:params){
			if(originalSql.contains(param.getName())){
				execSql=originalSql.trim().replace("\r", " ").replace("\n"," ").replaceAll(":\\["+param.getName()+"\\]", param.getValue());
			}
		}
		String tabName = ifs.getTargetTableName();
		originalSql = originalSql.replaceAll("'", "\\\\'");
		execSql = execSql.replaceAll("'", "\\\\'");
		remessage += "原始SQL语句:\\n"+originalSql+"\\n";
		remessage += "执行SQL语句:\\n"+execSql+"\\n\\n";
	
		long t5 = System.currentTimeMillis();
		//解析方案头
	    List<Head>  heads = DataAccessKitUtil.getFAHead(xmlData);
	    for(Head head : heads) {
	    	String headflag = head.getFlag();
	    	remessage += "方案头返回信息编码:"+headflag+"\\t";
	    	String headmess = head.getMess();
	    	remessage += "方案头返回信息:"+headmess+"\\n";
	    }
	    //解析脚本头
	   if(scripts != null) {
		   Set<Entry<String,Head>> setEntry = scripts.entrySet();
		   for(Entry<String,Head> entry : setEntry) {
			   Head head = entry.getValue();
			   String scriptFlag = head.getFlag();
			   remessage += "脚本头返回信息编码:"+scriptFlag+"\\t";
			   String scriptMess = head.getMess();
			   remessage += "脚本头返回信息:"+scriptMess+"\\n\\n";
		   }
	   }
	   long t6 = System.currentTimeMillis();
	   double analyzeTime = (t6-t5)/1000.0;		//解析时间
	   remessage += "解析耗时:"+analyzeTime+"秒";
	   String tableMessage = "返回"+tableSize+"条数据,"+"共"+columnSize+"列.";
	   String gridJson = "";
	   if(gts != null) {
		   gridJson = AutoGrid02.getGridJsonData2(gts, dataStr, true, false, remessage, tabName, tableMessage);
	   }
	   //如果没有查询出结果集
	   if(tables == null) {
		   gridJson = "{'action':true,'message':'"+remessage+"','tabName':'"+tabName+"','tableMessage':'"+tableMessage+"'}";
	   }
	   //System.out.println(gridJson);
	   this.setSelfDefResData(gridJson);
	   zwWebservice.setStatus("0");
	   return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("close.onclick")
	@AutoNoMask
	public int close(){
		this.closeCueWindow("ScriptTest");												//执行关闭窗口
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * 测试页面输入参数保存事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	@AutoNoMask
	public int save() throws RadowException {
		Grid grid = (Grid) this.getPageElement("list");
		List<HashMap<String, Object>> gridlist = grid.getValueList();
		QUERY_PARAM = genereteQUERY_PARAM(gridlist);
		this.getPageElement("QUERY_PARAM").setValue(QUERY_PARAM);
		this.getExecuteSG().addExecuteCode("document.getElementById('execute').click();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 用于生成QUERY_PARAM参数
	 * @return
	 * @throws RadowException 
	 */
	public String genereteQUERY_PARAM(List<HashMap<String, Object>> gridlist) throws RadowException {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0; i<gridlist.size(); i++) {
			HashMap<String, Object> hm = gridlist.get(i);
			String interface_parameter_name =  (String)hm.get("interface_parameter_name");
			String interface_parameter_desc = (String)hm.get("interface_parameter_desc");
			String interface_parameter_type = (String)hm.get("interface_parameter_type");
			interface_parameter_type = "1".equals(interface_parameter_type) ? "String" : "2".equals(interface_parameter_type) ? "Double" : "Date";
			String paramValue = (String)hm.get("paramValue");
			if("请输入参数值".equals(paramValue)) {
				paramValue = "";
			}
			if(i != gridlist.size()-1) {
				sb.append("{paramBM:\""+interface_parameter_name+"\",paramValue:\""+paramValue+"\",paramType:\""+interface_parameter_type+"\",paramMC:\""+interface_parameter_desc+"\"},");
			} else {
				sb.append("{paramBM:\""+interface_parameter_name+"\",paramValue:\""+paramValue+"\",paramType:\""+interface_parameter_type+"\",paramMC:\""+interface_parameter_desc+"\"}");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}

