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
* ģ�����:ZWHZYQ_001_007   ģ������:���ܰ���ʽӿڽű�����<br>
* ģ������:��༭�� ��չ���� ��������<br>
* ģ�鴴������:2015��09��15��<br>
* ģ�鴴����Ա:�Ƴ�<br>
* ģ�鹦������:�������ݷ��ʽӿڽű�����<br>
* ���ļ���������:���ݷ��ʽӿڽű�������Ӧ�¼�<br>
* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
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
	 * ���ز���ҳ���ʼֵQUERY_PARAM��������ʾ�ڵ�һ������ϣ����Է����ͽű���Ϣ
	 * @retur n
	 * @throws RadowException
	 */
	@PageEvent("queryObj")
	@NoRequiredValidate
	@AutoNoMask
	public int queryObj() throws RadowException{
		String nodeId = this.getRadow_parent_data().substring(3);												//��ȡ��ǰ�����ķ�������
		this.getPageElement("nodeId").setValue(nodeId);
		//��ȡ�ű�����
		InterfaceScript isf = bs7.getScriptByIsn(nodeId);
		//��ȡ�ű�����INTERFACE_SCRIPT_ID
		String interface_script_id = isf.getInterfaceScriptId();
		this.getPageElement("interface_script_id").setValue(interface_script_id);
		//��ȡ�ű�����INTERFACE_SCRIPT_NAME
		String interface_script_name = isf.getInterfaceScriptName();
		
		//��ȡ��������INTERFACE_CONFIG_ID
		String interface_config_id = isf.getInterfaceConfigId();
		//��÷�������
		InterfaceConfig isc = bs7.getConfigById(interface_config_id) ;
		this.getPageElement("interface_config_id").setValue(interface_config_id);
		//��÷�������INTERFACE_CONFIG_NAME
		String interface_config_name = isc.getInterfaceConfigName();
		//����õ��ķ�������id���ű�����id����
		this.getPageElement("interfaceconfigidname").setValue(interface_config_id+"."+interface_config_name);
		this.getPageElement("interfacescriptidname").setValue(interface_script_id+"."+interface_script_name);
		//���ز���ҳ���ʼֵQUERY_PARAM��������ʾ�ڵ�һ�������
		List<HashMap<String, Object>> gridlist = new ArrayList<HashMap<String, Object>>();       //�������Ϣ
		Grid grid = (Grid)this.createPageElement("list", ElementType.GRID, false);
		List list = bs7.doQueryPrama(interface_config_id);
		for(int i = 0; i < list.size(); i++){
			HashMap dbhm = (HashMap)list.get(i);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("interface_parameter_sequence", dbhm.get("interface_parameter_sequence"));
			hm.put("interface_parameter_name", dbhm.get("interface_parameter_name"));
			hm.put("interface_parameter_type", dbhm.get("interface_parameter_type"));
   		    hm.put("interface_parameter_desc", dbhm.get("interface_parameter_desc"));
   		    hm.put("paramValue", "���������ֵ");
   		    gridlist.add(hm);
		}
		grid.setValueList(gridlist);
		QUERY_PARAM = genereteQUERY_PARAM(gridlist);
		this.getPageElement("QUERY_PARAM").setValue(QUERY_PARAM);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���ִ�а�ť�󣬽��ű������Ľ����ʾ�ڵڶ��������
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
		//��ȡ��ѯ�ݽ�������ݼ�����
		HashMap<String,Head> scripts =  DataAccessKitUtil.getScriptHead(xmlData);
		String eleName = "";
		if(scripts != null) {
		   Set<Entry<String,Head>> set = scripts.entrySet();
		   for(Entry<String,Head> entry : set) {
			   eleName = entry.getKey();
		   }
		}
		//����Data
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
				   gt.setHeader(columns.get(j).getName());												//�����б���Ϊά�ȱ���
				   gt.setDataIndex(columns.get(j).getName());										    //����DataIndex������ԴΪά�ȱ���
				   gt.setWidth("100");																			//���ÿ������
				   gt.setHeaderTitle(columns.get(j).getName());								       	//����ÿ�е�����
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
		//���ص���Ϣ
		String remessage = "";
		InterfaceScript ifs = bs7.getScriptById(interface_config_id, interface_script_id);				//��ø÷����Ľű�
		String originalSql = ifs.getInterfaceScriptSql();												//���ԭʼsql���
		originalSql = originalSql.replaceAll("[\\n]", "\\\\n");													//�����з��滻Ϊ�ո�
		originalSql = originalSql.replaceAll("[\\r]", "");
		//	���ִ��sql
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
		remessage += "ԭʼSQL���:\\n"+originalSql+"\\n";
		remessage += "ִ��SQL���:\\n"+execSql+"\\n\\n";
	
		long t5 = System.currentTimeMillis();
		//��������ͷ
	    List<Head>  heads = DataAccessKitUtil.getFAHead(xmlData);
	    for(Head head : heads) {
	    	String headflag = head.getFlag();
	    	remessage += "����ͷ������Ϣ����:"+headflag+"\\t";
	    	String headmess = head.getMess();
	    	remessage += "����ͷ������Ϣ:"+headmess+"\\n";
	    }
	    //�����ű�ͷ
	   if(scripts != null) {
		   Set<Entry<String,Head>> setEntry = scripts.entrySet();
		   for(Entry<String,Head> entry : setEntry) {
			   Head head = entry.getValue();
			   String scriptFlag = head.getFlag();
			   remessage += "�ű�ͷ������Ϣ����:"+scriptFlag+"\\t";
			   String scriptMess = head.getMess();
			   remessage += "�ű�ͷ������Ϣ:"+scriptMess+"\\n\\n";
		   }
	   }
	   long t6 = System.currentTimeMillis();
	   double analyzeTime = (t6-t5)/1000.0;		//����ʱ��
	   remessage += "������ʱ:"+analyzeTime+"��";
	   String tableMessage = "����"+tableSize+"������,"+"��"+columnSize+"��.";
	   String gridJson = "";
	   if(gts != null) {
		   gridJson = AutoGrid02.getGridJsonData2(gts, dataStr, true, false, remessage, tabName, tableMessage);
	   }
	   //���û�в�ѯ�������
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
		this.closeCueWindow("ScriptTest");												//ִ�йرմ���
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * ����ҳ��������������¼�
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
	 * ��������QUERY_PARAM����
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
			if("���������ֵ".equals(paramValue)) {
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

