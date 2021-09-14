package com.insigma.siis.local.pagemodel.cadremgn.workflow;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 流程申请记录
 * @author desire
 *
 */
public class FlowApplyRecordPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException {
		CommQuery cq = new CommQuery();
		String cid = this.getPageElement("classId").getValue();
		String applyFlag = this.getPageElement("applyFlag").getValue();//0 结束 1在审批中
		String activeField = "";
		JSONObject resultData = new JSONObject();
		JSONArray fieldsNames = new JSONArray();
		JSONArray columModle = new JSONArray();
		JSONArray data = new JSONArray();
		//applyFlag = "1";
		/*if(cid == null || "".equals(cid)){
			cid = "57283f5c823c431289df1db7f99c34c3";
		}*/
		if(StringUtils.isNotEmpty(cid)){
			String applyUser = PrivilegeManager.getInstance().getCueLoginUser().getId();
			String querySetId = "SELECT normal_setid,active_id_field FROM sp_standard_business_class WHERE class_id = '"+cid+"'";
			List<HashMap<String, Object>> setResult = cq.getListBySQL(querySetId);
			if(setResult != null && setResult.size() > 0){
				String approveTbl = setResult.get(0).get("normal_setid")+"_FLOW_APPROVE_TBL";
				activeField = setResult.get(0).get("active_id_field")+"";
				String queryActiveId = "SELECT active_id FROM sp_flow_active WHERE APPLY_PROXY_USER_ID = '"+applyUser+"' and class_id = '"+cid+"' and state_id = '"+applyFlag+"'";
				List<HashMap<String, Object>> activeResult = cq.getListBySQL(queryActiveId);
				if(activeResult != null && activeResult.size() > 0){
					String queryCol = "SELECT COL_CODE,COL_NAME,CODE_TYPE FROM code_table_col WHERE TABLE_CODE = '"+approveTbl+"' ORDER BY COL_CODE";
					List<HashMap<String, Object>> colResult = cq.getListBySQL(queryCol);
					if(colResult != null && colResult.size() > 0){
						//  setup1 组装 fieldsNames{name: '统计'}
						//  setup2 组装 columModle{'header': '统计','dataIndex': '统计','width':80}
						JSONObject temp = null;
						JSONObject temp1 = null;
					
						for (HashMap<String, Object> record : colResult) {
							temp = new JSONObject();
							temp.put("name", record.get("col_code"));
							temp1 = new JSONObject();
							temp1.put("header", record.get("col_name"));
							temp1.put("dataIndex", record.get("col_code"));
							fieldsNames.add(temp);
							columModle.add(temp1);
						}
						//  setup3 组装 data{'统计':'销售收入','安徽':afs,'福建':asf,'湖南':asf,'浙江':asf},
						StringBuffer sb = new StringBuffer();
						for (HashMap<String, Object> record : activeResult) {
							sb.append("'"+record.get("active_id")+"'"+",");
						}
						sb.deleteCharAt(sb.length()-1);
						String queryDataSql = "SELECT * FROM "+approveTbl+" WHERE "+activeField+" in ("+sb+")";
						List<HashMap<String, Object>> dataResult = cq.getListBySQL(queryDataSql);
						JSONObject temp3 = null;
						if(dataResult != null && dataResult.size() > 0){
							for (HashMap<String, Object> record : dataResult) {
								temp3 = new JSONObject();
								for(int i=0;i<fieldsNames.size();i++){
									JSONObject object = (JSONObject) fieldsNames.get(i);
									String key = object.get("name")+"";
									String value = record.get(key.toLowerCase())+"";
									temp3.put(key, value);
								}
								data.add(temp3);
							}
							resultData.put("fieldsNames", fieldsNames);
							resultData.put("columModle", columModle);
							resultData.put("data", data);
							this.getExecuteSG().addExecuteCode("makeGrid("+resultData+");");
						}
					}
				}else{
					String queryCol = "SELECT COL_CODE,COL_NAME,CODE_TYPE FROM code_table_col WHERE TABLE_CODE = '"+approveTbl+"' ORDER BY COL_CODE";
					List<HashMap<String, Object>> colResult = cq.getListBySQL(queryCol);
					if(colResult != null && colResult.size() > 0){
						//  setup1 组装 fieldsNames{name: '统计'}
						//  setup2 组装 columModle{'header': '统计','dataIndex': '统计','width':80}
						JSONObject temp = null;
						JSONObject temp1 = null;
					
						for (HashMap<String, Object> record : colResult) {
							temp = new JSONObject();
							temp.put("name", record.get("col_code"));
							temp1 = new JSONObject();
							temp1.put("header", record.get("col_name"));
							temp1.put("dataIndex", record.get("col_code"));
							fieldsNames.add(temp);
							columModle.add(temp1);
						}
						resultData.put("fieldsNames", fieldsNames);
						resultData.put("columModle", columModle);
						this.getExecuteSG().addExecuteCode("makeGrid("+resultData+");");
					this.getExecuteSG().addExecuteCode("refreshGrid();");}
			}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
