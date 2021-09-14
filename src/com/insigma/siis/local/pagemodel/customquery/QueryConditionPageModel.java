package com.insigma.siis.local.pagemodel.customquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONArray;

import org.hibernate.Query;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
/**
 * @author lixy
 *
 */
public class QueryConditionPageModel extends PageModel {
	
	private CustomQueryBS ctcBs=new CustomQueryBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{		
		/*List<Map<String, String>> list=ctcBs.collectionList;

		//加载信息集下拉框
		if(list!=null&&list.size()>0){
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			for (Map<String, String> coll : list) {
				treeMap.put(coll.get("COL_LECTION_NAME".toLowerCase()), coll.get("COL_LECTION_NAME".toLowerCase()));
			}
			( (Combo) this.getPageElement("collection")).setValueListForSelect(treeMap);
		}*/
		
		//初始化，设定信息集可选项  mengl 20160629
		//Combo collection =  (Combo)this.createPageElement("collection", ElementType.SELECT, false);
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		//collection.setValueListForSelect(map);
		
		//表格列初始化 2017年6月5日 zoul
		//Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		StringBuffer dataarrayjs = getComboArray(map);
		//信息集
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});changestore();");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		
		//如果有父窗体有传递可定条件ID则加载相关条件信息到网格
		//String queryId=this.getRadow_parent_data();
		String queryId=this.getPageElement("subWinIdBussessId2").getValue();
		if (!"".equals(queryId)) {
			HBSession sess = HBUtil.getHBSession();
			Customquery cq = (Customquery) sess.get(Customquery.class, queryId);
			String data = null;
			if (cq != null) {
				data = cq.getGridstring();
				if (data != null && !"".equals(data)) {
					this.getPageElement("grid").setValue(cq.getGridstring());
					this.getPageElement("conditionName").setValue(cq.getQueryname());
				}else{
					this.getExecuteSG().addExecuteCode("insertEmptyRow(Ext.getCmp('grid').getStore());");
				}
			}else{
				this.getExecuteSG().addExecuteCode("insertEmptyRow(Ext.getCmp('grid').getStore());");
			}
		}else{
			this.getExecuteSG().addExecuteCode("insertEmptyRow(Ext.getCmp('grid').getStore());");
		}
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private StringBuffer getComboArray(Map<String, String> map){
		StringBuffer dataarrayjs = new StringBuffer("[");
		Set<String> tablecodes = map.keySet();
		//[ 'exists', '存在' ]
		for(String tablecode : tablecodes){
			dataarrayjs.append("['"+tablecode+"','"+map.get(tablecode)+"'],");
		}
		if(map.size()>0){
			dataarrayjs.deleteCharAt(dataarrayjs.length()-1);
		}
		dataarrayjs.append("]");
		return dataarrayjs;
	}
	
	
	
	/**
	 * 设定选中行号到页面上
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("grid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@Synchronous(true)
	public int setCueRow() throws RadowException, AppException{
		int cueRowIndex = this.getPageElement("grid").getCueRowIndex();
		String cueRowIdexStr = this.getPageElement("cueRowIndex").getValue();
		int pRowIndex = -1;
		if(!"".equals(cueRowIdexStr)){
			pRowIndex = Integer.valueOf(cueRowIdexStr);
		}
		
		if(pRowIndex==cueRowIndex){//没换行
			return EventRtnType.NORMAL_SUCCESS;
		}
		PageElement pe = this.getPageElement("grid");
		List<HashMap<String, Object>> glist = pe.getValueList();
		//PageElement colName = this.getPageElement("colName");
		HashMap<String, Object> m = glist.get(0);
		String tablenamecode = m.get("tableName").toString();
		String tablefieldcode =m.get("colNamesValue").toString();
		
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		//collection.setValueListForSelect(map);
		
		//表格列初始化 2017年6月5日 zoul
		//Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		StringBuffer dataarrayjs = getComboArray(map);
		//信息集
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		
		//信息项
		if(tablenamecode!=null&&!"".equals(tablenamecode)){
			setColNames(tablenamecode);
		}
		
		
		//信息项对应的codevalue
		//radow.doEvent('setCodeValue',dataarray[i][0]+"@"+e.record.data.tableName);
		if(tablenamecode!=null&&!"".equals(tablenamecode)&&tablefieldcode!=null&&!"".equals(tablefieldcode)){
			setCodeValue(tablefieldcode+"@"+tablenamecode);
		}
		
		
		
		//左括号
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:4,dataArray:[['(','(']],allowBlank:true});");
		//this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:4,dataArray:[['(','(']],allowBlank:true});");
		
		//右括号
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:13,dataArray:[[')',')']],allowBlank:true});");
		//this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:13,dataArray:[[')',')']],allowBlank:true});");
		
		//逻辑符
		Map<String,String> treeMap = new TreeMap<String,String>();
		treeMap = RuleSqlListBS.getAllMapByCodeType("LOGICSYMBOLS");
		dataarrayjs = getComboArray(treeMap);
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:14,dataArray:"+dataarrayjs+",allowBlank:true});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:14,dataArray:"+dataarrayjs+",allowBlank:true});");
		
		//信息集
		//this.getPageElement("collection").setValue(pe.getValue("tableName").toString());
		
		//信息集onchange
		//collectionChange();
		
		//信息项
		//colName.setValue((String)(pe.getValue("colNamesValue")));
		
		//信息项onchange
		//colNameChane();
		
		
		//this.getPageElement("opeartor").setValue((String)(pe.getValue("opeartors")));
		//this.getPageElement("colValue").setValue((String)(pe.getValue("colValues")));
		//this.getPageElement("logicSymbol").setValue((String)(pe.getValue("logicSymbols")));
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 修改
	 * @return
	 * @author mengl
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("editcond.onclick")
	@AutoNoMask
	public int modify() throws RadowException, AppException{
		Grid pe = (Grid)this.getPageElement("grid");//获取grid对象
		String cueRowIdexStr = this.getPageElement("cueRowIndex").getValue();
		if(StringUtil.isEmpty(cueRowIdexStr)){
			throw new AppException("请先选中一行修改！");
		}
		int cueRowIndex = Integer.valueOf(cueRowIdexStr);
		//获取选中行中各列值
		String collection = this.getPageElement("collection").getValue();
		String colNameValue = this.getPageElement("colName").getValue();
		String opeartor = this.getPageElement("opeartor").getValue();
		String colValue = this.getPageElement("colValue").getValue();
		String logicSymbol = this.getPageElement("logicSymbol").getValue();
		/*String colName = ctcBs.getCtc(colNameValue,ctcBs.ctcList).getColName();
		String tableName=ctcBs.getCtc(colNameValue,ctcBs.ctcList).getTableCode();
		String colValueView=ctcBs.getAaa103(colNameValue, colValue,ctcBs.ctcList);*/
		CodeTableCol codeTableCol = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		String colName = codeTableCol.getColName();
//		String tableName=RuleSqlListBS.getTableName(collection);  //不在保存汉字，保存代码
		String colValueView = CodeManager.getValueByCode(!StringUtil.isEmpty(codeTableCol.getCodeType())?codeTableCol.getCodeType():colNameValue,colValue);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", collection);  //不再保存汉字，保存代码
		map.put("colNames", colName);
		map.put("colNamesValue", colNameValue);
		map.put("opeartors", opeartor);
		map.put("colValues", colValue);
		if(colValueView!=null){
		    map.put("colValuesView", colValueView);
		}else{
		    map.put("colValuesView", colValue);
		}
		map.put("logicSymbols", logicSymbol);
		
		pe.updateRowData(cueRowIndex, map);//更新该行数据
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行提升一行
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("upRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int upRow() throws RadowException, AppException{
		Grid pe = (Grid) this.getPageElement("grid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("已经是第一行，不能上移！");
			}
		}
		List<HashMap<String, Object>> list = pe.getValueList();
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex-1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex-1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex-1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex-1+"");
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行降低一行
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("downRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int downRow() throws RadowException, AppException{
		
		Grid pe = (Grid) this.getPageElement("grid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(list.size()-1)){
				throw new AppException("已经是最后行，不能下移！");
			}
		}
		
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex+1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex+1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex+1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+1+"");
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 当选择信息集时，触发查询指标项
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("collection.onchange")
	@NoRequiredValidate
	public int collectionChange() throws RadowException, AppException {
		//根据选择的信息集加载对应的查询条件数据
		PageElement pe = this.getPageElement("colName");
		String code = this.getPageElement("collection").getValue();
		/*TreeMap<String, String> treeMap=ctcBs.getCtcListByCollectionCode(code,ctcBs.ctcList);
		( (Combo) this.getPageElement("colName")).setValueListForSelect(treeMap);*/
		
		TreeMap<String, String> treeMap = null;
		if(!"A01".equalsIgnoreCase(code)){
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1'  ");//仅查询指标项，且忽略vsl006是否配置 mengl 20160629
		}else{
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1' or  id.columnName = 'AGE' or id.columnName ='A0148' ");//仅查询指标项，且忽略vsl006是否配置 mengl 20160629
		}
		
		( (Combo) this.getPageElement("colName")).setValueListForSelect(treeMap);
		
		pe.setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("colName.onchange")
	@NoRequiredValidate
	public int colNameChane() throws RadowException, AppException {
		//根据所选的查询条件加载对应的值数据
		Combo pe = (Combo)this.getPageElement("colValue");
		String collection = this.getPageElement("collection").getValue();
		String colNameValue = this.getPageElement("colName").getValue();
		
		if(StringUtil.isEmpty(colNameValue)){
			this.getPageElement("collection").setValue("");
			this.getPageElement("colName").setValue("");
			this.setMainMessage("请先选择信息集！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		
		// 设定可选值
		// mengl 20160629
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		Map<String,String> map = new TreeMap<String,String>();
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			map = RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase());
		}else if(!StringUtil.isEmpty(colNameValue)){
			map = RuleSqlListBS.getAllMapByCodeType(colNameValue.toUpperCase());
		}
		pe.setValueListForSelect(map);
		
		//设定运算符
		 Combo opeartorCombo = (Combo)this.getPageElement("opeartor");
		 Map<String,String> mapOperator = RuleSqlListBS.getCodeValuesMapByCodeType("OPERATOR");
		 //可选代码为空，设定操作符，否则去掉
		if(map.isEmpty()){
			//当判断代码值为空的时候，如果是number形式的字段，则剔除相应的运算符
			if(cr.getColDataTypeShould().equals("NUMBER")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			//如果是DATE形式的字段，提出不适合date类型使用的运算符
			}else if (cr.getColDataTypeShould().equals("DATE")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
			//如果是VARCHAR2形式的字段，提出不适合VARCHAR2类型使用的运算符
			}else if (cr.getColDataTypeShould().equals("VARCHAR2")){
				mapOperator.remove(">{v}");
				mapOperator.remove(">={v}");
				mapOperator.remove("<{v}");
				mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}else{
				mapOperator.remove(">{v}");
				mapOperator.remove(">={v}");
				mapOperator.remove("<{v}");
				mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}
			
		}else{
			//特殊代码信息项  放开<、>、<=、>=符号 //--lzy  updatee 
			if("A0221".equals(colNameValue)||"A0801B".equals(colNameValue)||"A0901B".equals(colNameValue)){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}else{
				mapOperator.remove(">{v}");
				mapOperator.remove(">={v}");
				mapOperator.remove("<{v}");
				mapOperator.remove("<={v}");
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}
		}
		if(cr.getColCode().equals("AGE")){
			mapOperator.remove("!={v}");
		}
		opeartorCombo.setValueListForSelect(mapOperator);
		pe.setValue("");
		opeartorCombo.setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("closeWin.onclick")
	@NoRequiredValidate
	public int closeWin() {
		this.closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
	/**
	 * @$comment 保存查询条件
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@PageEvent("btnSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int btnSaveOnclick() throws RadowException, AppException {
		PageElement pe = this.getPageElement("grid");
		String queryName=this.getPageElement("conditionName").getValue();
		List<HashMap<String, Object>> list= pe.getValueList();
		//非空校验
		int ret = -1;
		if((ret = valiEmpty(list))!=-1){
			return ret;
		}
		
		String querySql=ctcBs.createSQL(list);
		String queryDes=ctcBs.createSQLView(list);
		String loginName=SysUtil.getCacheCurrentUser() .getLoginname();
		String data=JSONArray.fromObject(list).toString();
		//String queryId=this.getRadow_parent_data();
		String queryId=this.getPageElement("subWinIdBussessId2").getValue();
		if(queryName==null||"".equals(queryName))
			throw new AppException("查询条件名称不能为空！");
		
		if(queryId==null||"".equals(queryId)){
    	HBSession sess = HBUtil.getHBSession();
    	Query query=sess.createSQLQuery("select 1 from customquery where queryname='"+queryName+"' and  loginname='"+SysManagerUtils.getUserloginName()+"'");
    	List<Object> listc=query.list();
    	if(listc.size()>0)
    		throw new AppException("固定条件名称已存在！");
		}		

		ctcBs.saveOrUodateCq(queryId, queryName, querySql, queryDes, loginName,data);
		//this.closeCueWindowEX();
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gridcq');");//刷新父窗体固定条件网格
		this.getExecuteSG().addExecuteCode("$h.alert('提示','保存成功');setTimeout(function(){ Ext.MessageBox.hide();} , 300);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * @$comment 根据生成查询条件查询     调用双击查询方法   conditionRowdbclick
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */
	
	@SuppressWarnings("static-access")
	@PageEvent("doQuery.onclick")
	@NoRequiredValidate
	@Transaction
	public int doQuery() throws RadowException, AppException {
		PageElement pe = this.getPageElement("grid");
		String queryName="上次查询";//保存为上次查询
		List<HashMap<String, Object>> list= pe.getValueList();
		
		//非空校验
		int ret = -1;
		if((ret = valiEmpty(list))!=-1){
			return ret;
		}
		
		
		String querySql=ctcBs.createSQL(list);
		String queryDes=ctcBs.createSQLView(list);
		String loginName=SysUtil.getCacheCurrentUser() .getLoginname();
//		String queryId=this.getRadow_parent_data();
		ctcBs.delLastTimeCq();//删除上次查询条件
		String data=JSONArray.fromObject(list).toString();
		ctcBs.saveOrUodateCq("", queryName, querySql, queryDes, loginName,data);
		
		//this.createPageElement("sql",ElementType.HIDDEN, true).setValue(querySql);//设置父页面sql值为本次生成sql
		
		this.request.getSession().setAttribute("groupBy", "1");
		this.request.getSession().setAttribute("queryType", "1");
		this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gridcq');");	//刷新父窗体固定条件网格
		this.getExecuteSG().addExecuteCode("realParent.realParent.conditionRowdbclick('"+querySql.replace("'", "\\'")+"',realParent.doQuery());");
		//this.getExecuteSG().addExecuteCode("realParent.realParent.document.getElementById('sql').value='"+querySql.replace("'", "\\'")+"';");
		//this.getExecuteSG().addExecuteCode("realParent.realParent.document.getElementById('orgjson').value=Ext.util.JSON.encode(realParent.doQuery());");
		
		//this.getExecuteSG().addExecuteCode("realParent.realParent.radow.doEvent('peopleInfoGrid.dogridquery');");//父窗体执行查询	
		
		this.closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}

	private int valiEmpty(List<HashMap<String, Object>> list) {
		//非空校验
		int rowidex = 0;
		List<HashMap<String, Object>> listremove = new ArrayList<HashMap<String,Object>>();
    	for(HashMap<String, Object> m:list){
    		rowidex++;
    		String  tableNamed = m.get("tableNamed").toString();//sql中列名
    		String  colNamesInfo = m.get("colNamesInfo").toString();//sql中列名
    		String  opeartorsd = m.get("opeartorsd").toString();//sql中列名
    		String  colValuesView = m.get("colValuesView").toString();//sql中列名
    		String  opeartors = m.get("opeartors").toString();
    		if("".equals(tableNamed)&&"".equals(colNamesInfo)&&"".equals(opeartorsd)&&"".equals(colValuesView)){
    			listremove.add(m);
    			continue;
    		}
    		if("".equals(tableNamed)){
    			this.setMainMessage("第"+rowidex+"行 “表名” 不能为空！");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		if("".equals(colNamesInfo)){
    			this.setMainMessage("第"+rowidex+"行 “信息项” 不能为空！");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		if("".equals(opeartorsd)){
    			this.setMainMessage("第"+rowidex+"行 “运算符” 不能为空！");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		if("".equals(colValuesView)&&!"({c} is null or {c}={v})".equals(opeartors)){
    			this.setMainMessage("第"+rowidex+"行 “值” 不能为空！");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		
    	}
    	list.removeAll(listremove);
    	return -1;
	}
	
	
	
	/**
	 * 设置信息项下拉框 zoul
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@SuppressWarnings("static-access")
	@PageEvent("setColNames")
	@AutoNoMask
	@Synchronous(true)
	public int setColNames(String code) throws RadowException, AppException{  
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		if(!"A01".equalsIgnoreCase(code)){
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1'  ");//仅查询指标项，且忽略vsl006是否配置 mengl 20160629
		}else{
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1' or  id.columnName = 'AGE' or id.columnName ='A0148' ");//仅查询指标项，且忽略vsl006是否配置 mengl 20160629
		}
		Set<String> keys = treeMap.keySet();
		String defaultValue = "";
		if(keys.size()>0){
			defaultValue = keys.iterator().next();
		}
		//表格列初始化 2017年6月5日 zoul
		StringBuffer dataarrayjs = getComboArray(treeMap);
		//信息集
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:6,dataArray:"+dataarrayjs+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:6,dataArray:"+dataarrayjs+"});");
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 创建编辑框
	 * @author zoul
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("createCellEdit")
	@NoRequiredValidate
	//@Synchronous(true)
	public int createCellEdit(String row) throws RadowException, AppException{
		
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		StringBuffer dataarrayjs = getComboArray(map);
		//信息集
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 设置codevalue下拉框 zoul
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@SuppressWarnings("static-access")
	@PageEvent("setCodeValue")
	@Synchronous(true)
	@AutoNoMask
	public int setCodeValue(String code) throws RadowException, AppException{  
		String[] p = code.split("@");
		String collection = p[1];
		String colNameValue = p[0];
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		Map<String,String> treeMap = new TreeMap<String,String>();
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			treeMap = RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase());
		}else if(!StringUtil.isEmpty(colNameValue)){
			treeMap = RuleSqlListBS.getAllMapByCodeType(colNameValue.toUpperCase());
		}
		
		
		Set<String> keys = treeMap.keySet();
		String defaultValue = "";
		String canOutSelectList = "true";
		if(keys.size()>0){
			defaultValue = keys.iterator().next();
			canOutSelectList = "false";
		}
		//表格列初始化 2017年6月5日 zoul
		StringBuffer dataarrayjs = getComboArray(treeMap);
		//信息项对应的代码集
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:11,dataArray:"+dataarrayjs+",canOutSelectList:'"+canOutSelectList+"'});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:11,dataArray:"+dataarrayjs+",canOutSelectList:'"+canOutSelectList+"'});");
		
		Map<String,String> mapOperator = RuleSqlListBS.getCodeValuesMapByCodeType("OPERATOR");
		 //可选代码为空，设定操作符，否则去掉
		if(treeMap.isEmpty()){
			//当判断代码值为空的时候，如果是number形式的字段，则剔除相应的运算符
			if(cr.getColDataTypeShould().equals("NUMBER")){
				mapOperator.remove("like {v%}");
				
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("like {%v%}");
				
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			//如果是DATE形式的字段，提出不适合date类型使用的运算符
			}else if (cr.getColDataTypeShould().equals("DATE")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
			//如果是VARCHAR2形式的字段，提出不适合VARCHAR2类型使用的运算符
			}else if (cr.getColDataTypeShould().equals("VARCHAR2")){
				//mapOperator.remove(">{v}");
				//mapOperator.remove(">={v}");
				//mapOperator.remove("<{v}");
				//mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("not like {%v%}");
			}else{
				//mapOperator.remove(">{v}");
				//mapOperator.remove(">={v}");
				//mapOperator.remove("<{v}");
				//mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("not like {%v%}");
			}
			
		}else{
			//特殊代码信息项  放开<、>、<=、>=符号 //--lzy  updatee 
			if("A0221".equals(colNameValue)||"A0801B".equals(colNameValue)||"A0901B".equals(colNameValue)){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}else{
				//mapOperator.remove(">{v}");
				//mapOperator.remove(">={v}");
				//mapOperator.remove("<{v}");
				//mapOperator.remove("<={v}");
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}
		}
		if(cr.getColCode().equals("AGE")){
			mapOperator.remove("!={v}");
			mapOperator.remove("({c} is null or {c}={v})");
			mapOperator.remove("like {v%}");
			mapOperator.remove("not like {v%}");
			mapOperator.remove("like {%v}");
			mapOperator.remove("not like {%v}");
			mapOperator.remove("like {%v%}");
			mapOperator.remove("not like {%v%}");
		}
		
		StringBuffer oparray = getComboArray(mapOperator);
		//运算符
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:9,dataArray:"+oparray+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:9,dataArray:"+oparray+"});");
		
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 新增条件到列表
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@SuppressWarnings("static-access")
	@PageEvent("savecond")
	@GridDataRange
	public int savecondonClick() throws RadowException, AppException{  
        
		PageElement pe = this.getPageElement("grid");
		String colNameValue = this.getPageElement("colName").getValue();
		String opeartor = this.getPageElement("opeartor").getValue();
		String colValue = this.getPageElement("colValue").getValue();
		//当查询的是身份证时，将查询值中的小写字母转换为大写字母
		if("A0184".equals(colNameValue.toUpperCase())){
			colValue = colValue.toUpperCase();
		}
		String logicSymbol = this.getPageElement("logicSymbol").getValue();
		String colName = ctcBs.getCtc(colNameValue,ctcBs.ctcList).getColName();
		String tableName=ctcBs.getCtc(colNameValue,ctcBs.ctcList).getTableCode();
		String colValueView=ctcBs.getAaa103(colNameValue, colValue,ctcBs.ctcList);
		
		List<HashMap<String, Object>> list = pe.getValueList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("colNames", colName);
		map.put("colNamesValue", colNameValue);
		map.put("opeartors", opeartor);
		map.put("colValues", colValue);
		if(colValueView!=null)
		    map.put("colValuesView", colValueView);
		else
		    map.put("colValuesView", colValue);
		map.put("logicSymbols", logicSymbol);
		list.add(map);
	
		pe.setValueList(list);
		this.getPageElement("cueRowIndex").setValue("");
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * 当grid中的关系列变更时触发
	 * @author caiy
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	//@PageEvent("grid.afteredit")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate
	@AutoNoMask
	public int gridonchange() throws RadowException, AppException{
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 组装打印sql
	 * @author caiy
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	//@PageEvent("grid.afteredit")
	@PageEvent("PrintSQL")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate
	@AutoNoMask
	public int printSql() throws RadowException, AppException{
		String sqlForm = "";
		List<HashMap<String, Object>> gridList = this.getPageElement("grid").getValueList();
		for(int i=0 ; i < gridList.size() ; i++){
			HashMap<String, Object> map = gridList.get(i);
			sqlForm = sqlForm+map.get("leftBracket");
			sqlForm = sqlForm+map.get("colNames");
			sqlForm = sqlForm+" "+map.get("opeartors");
			//sqlForm = sqlForm+map.get("colValuesView");
			sqlForm = sqlForm+map.get("rightBracket");
			sqlForm = sqlForm+map.get("logicSymbols");
			String value = map.get("colValuesView").toString();
			sqlForm = sqlForm.replace("({c} is null or {c}={v})", "为空").replace("trunc(sysdate + {v}) ", "当前时间+"+value+"天").replace("not like {%v%}", "不包含'"+value+"'")
					.replace("not like {%v}", "不以'"+value+"'结尾").replace("not like {v%}", "不以'"+value+"'开头")
					.replace("{v}", value).replace("or", "或者").replace("and", "并且").replace("like {%v%}", "包含("+value+")")
					.replace("like {%v}", "右包含("+value+")").replace("like {v%}", "左包含("+value+")").replace("并且 1=1", "");

		}
		this.getPageElement("aa").setValue(sqlForm);
		//this.getExecuteSG().addExecuteCode("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}