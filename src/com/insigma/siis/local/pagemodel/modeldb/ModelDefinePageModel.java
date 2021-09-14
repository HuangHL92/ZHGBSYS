package com.insigma.siis.local.pagemodel.modeldb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.siis.local.business.entity.Sublibrariescolumns;
import com.insigma.siis.local.business.entity.SublibrariescolumnsId;
import com.insigma.siis.local.business.entity.Sublibrariescondition;
import com.insigma.siis.local.business.modeldb.ColFuture;
import com.insigma.siis.local.business.modeldb.ModelDB;
import com.insigma.siis.local.business.modeldb.ModelDBDTO;
import com.insigma.siis.local.business.modeldb.ModeldbBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ModelDefinePageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		
		
		
		String id;
		id = this.request.getParameter("initParams");
		if(!"".equals(id)&&id!=null){
			 ModelDB db = ModeldbBS.LoadDB(id);
			 PageModel.copyObjValueToElement(db.getModel(), this);
			 List<HashMap<String,Object>> columnsList = new ArrayList<HashMap<String,Object>>();
			 List<HashMap<String,Object>> conditionlist = new ArrayList<HashMap<String,Object>>();
			 for(Sublibrariescolumns column : db.getColumns()){
				 HashMap<String,Object> map = new HashMap<String,Object>();
				 map.put("information_set",column.getId().getInformation_set().toString());
				 map.put("information_set_field",column.getId().getInformation_set_field().toString());
				 map.put("columns_no",column.getColumns_no());
				 map.put("queryitem", true);
				 columnsList.add(map);
			 }
			 for(Sublibrariescondition condition : db.getConditions()){
				 HashMap<String,Object> map = new HashMap<String,Object>();
				 map.put("information_set",condition.getInformation_set());
				 map.put("information_set_field",condition.getInformation_set_field());
				 map.put("condition_no", condition.getCondition_no());
				 map.put("condition_operator", condition.getCondition_operator());
				 map.put("brackets", condition.getBrackets());
				 map.put("field_value", condition.getField_value());
				 map.put("condition_connector", condition.getCondition_connector());
				 conditionlist.add(map);
				 
			 }
			 this.getPageElement("columnsdefine").setValueList(ModeldbBS.putComments4MapList(columnsList));
			 this.getPageElement("conditionsdefine").setValueList(ModeldbBS.putComments4MapList(conditionlist));
			
		}
		
		//初始化，设定信息集可选项
		Combo vru004 =  (Combo)this.createPageElement("information_set_", ElementType.SELECT, false);
		Combo vsl008 =  (Combo)this.createPageElement("information_set", ElementType.SELECT, false);
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		vru004.setValueListForSelect(map);
		vsl008.setValueListForSelect(map);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("query.onclick")
	@NoRequiredValidate
	public int query() throws RadowException {
		this.setNextEventName("columnsdefine.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("columnsdefine.dogridquery")
	@NoRequiredValidate
	public int grid(int start,int limit) throws RadowException{
		limit=300;
		String tablenames=this.getPageElement("information_set_").getValue();
		StringBuffer sb= new StringBuffer(" select v.information_set,v.information_set_comment,v.information_set_field,v.information_set_field_comment,rownum as columns_no  from  v_tab_cols v where 1=1");
		if(!"".equals(tablenames)){ 
			sb.append(" and instr('"+tablenames+"',v.information_set) >0 ");
		}
		
		//处理MySQL mengl 20160526
		if(DBUtil.getDBType() == DBType.MYSQL){
			sb = new StringBuffer("select @rownum:=@rownum+1 columns_no,information_set,information_set_comment,information_set_field,information_set_field_comment from ")
				.append(" (select information_set,information_set_comment,information_set_field,information_set_field_comment,@rownum:=0  from  v_tab_cols v where 1=1 ");
			if(!"".equals(tablenames)){ 
				tablenames.replace("[", "").replace("]", "");
				String[] tableNameArray = tablenames.split(",");
				StringBuffer tabNameBf = new StringBuffer();
				for(String tabName : tableNameArray){
					tabNameBf.append("'").append(tabName).append("',");
				}
				tablenames = tabNameBf.toString().substring(0, tabNameBf.lastIndexOf(","));
				sb.append(" and  v.information_set in ("+tablenames+") ");
			}
			sb.append(" ) t ");
		}
		
		
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("add.onclick")
	public int add() throws RadowException {
		Sublibrariescondition c = new Sublibrariescondition();
		this.copyElementsValueToObj(c, this);
		List<HashMap<String,Object>> gridlist = this.getPageElement("conditionsdefine").getValueList();
		List<HashMap<String,Object>> templist = new ArrayList<HashMap<String,Object>>();
		for(HashMap<String,Object> map : gridlist){
			HashMap<String,Object> m = new HashMap<String,Object>();
			m.put("information_set",map.get("information_set").toString());
			m.put("information_set_field",map.get("information_set_field").toString());
			m.put("condition_operator",map.get("condition_operator").toString());
			m.put("brackets","".equals(map.get("brackets").toString())||map.get("brackets")==null?"":map.get("brackets").toString());
			m.put("field_value","".equals(map.get("field_value").toString())||map.get("field_value")==null?"":map.get("field_value").toString());
			m.put("condition_connector","".equals(map.get("condition_connector").toString())||map.get("condition_connector")==null?"":map.get("condition_connector").toString());
			templist.add(m);
		}

		HashMap<String,Object> map = new HashMap<String,Object>();//用来保存
		HashMap<String,Object> temp = new HashMap<String,Object>();//用来校验
		map.put("information_set", c.getInformation_set());
		map.put("information_set_field", c.getInformation_set_field());
		map.put("condition_no",gridlist.size()+1);
		map.put("brackets", "".equals(c.getBrackets())||c.getBrackets()==null?"":c.getBrackets());
		map.put("condition_connector","".equals(c.getCondition_connector())||c.getCondition_connector()==null?"":c.getCondition_connector());

		temp.put("information_set", c.getInformation_set());
		temp.put("information_set_field", c.getInformation_set_field());
		temp.put("brackets", "".equals(c.getBrackets())||c.getBrackets()==null?"":c.getBrackets());
		temp.put("condition_connector","".equals(c.getCondition_connector())||c.getCondition_connector()==null?"":c.getCondition_connector());

		String type= this.getPageElement("editer").getValue();
		map.put("condition_operator", c.getCondition_operator());
		temp.put("condition_operator", c.getCondition_operator());
		if("select".equals(type)){
			map.put("field_value","".equals(c.getField_value_select())||c.getField_value_select()==null?"":c.getField_value_select());
			temp.put("field_value","".equals(c.getField_value_select())||c.getField_value_select()==null?"":c.getField_value_select());
		}else if("date".equals(type)){
			map.put("field_value","".equals(c.getField_value_date())||c.getField_value_date()==null?"":c.getField_value_date());
			temp.put("field_value","".equals(c.getField_value_date())||c.getField_value_date()==null?"":c.getField_value_date());
		}else if("number".equals(type)){
			map.put("field_value","".equals(c.getField_value_number())||c.getField_value_number()==null?"":c.getField_value_number());
			temp.put("field_value","".equals(c.getField_value_number())||c.getField_value_number()==null?"":c.getField_value_number());
		}else{
			temp.put("field_value","".equals(c.getField_value_text())||c.getField_value_text()==null?"":c.getField_value_text());
			map.put("field_value","".equals(c.getField_value_text())||c.getField_value_text()==null?"":c.getField_value_text());
		}
		if(templist.contains(temp)){
			throw new RadowException("该条件已经存在,无需重复");
		}
		gridlist.add(map);
		this.getPageElement("conditionsdefine").setValueList(ModeldbBS.putComments4MapList(gridlist));
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("remove.onclick")
	@NoRequiredValidate
	public int remove() throws RadowException {
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridlist = this.getPageElement("conditionsdefine").getValueList();
		for(HashMap<String,Object> map: gridlist){
			if(!"true".equals(map.get("check").toString())&&!"1".equals(map.get("check").toString())){
				list.add(map);
			}
		}
		this.getPageElement("conditionsdefine").setValueList(list);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 信息集修改后，再重新加载信息项
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("information_set.onchange")
	@NoRequiredValidate
	@AutoNoMask
	public int information_set() throws RadowException, AppException {
		String table = this.getPageElement("information_set").getValue();
		TreeMap<String, String> treeMap;
		try {
			treeMap = RuleSqlListBS.getVru005byVru004(table,false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("查询该信息集对应的信息项失败。异常信息："+e.getMessage());
		}
		( (Combo) this.getPageElement("information_set_field")).setValueListForSelect(treeMap);
		this.getPageElement("information_set_field").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("information_set_field.onchange")
	@NoRequiredValidate
	public int information_set_field() throws RadowException, AppException {
		String set = this.getPageElement("information_set").getValue();
		String field = this.getPageElement("information_set_field").getValue();
		String type = "text";
		ColFuture future = ModeldbBS.getColFuture(set,field);
		if(future.getCode_type()!=null&&!"".equals(future.getCode_type())){//如果是代码类型
			type = "select";
			PageElement e = this.createPageElement("field_value_select", ElementType.SELECT,false);
			String filter ="";
		    e.loadDataForSelectStore(future.getCode_type(), "", filter, true);
		}else if("date".equalsIgnoreCase(future.getData_type())){
			type = "date";
		}else if("number".equalsIgnoreCase(future.getData_type())){
			type = "number";
		}
		this.getExecuteSG().addExecuteCode("shift('"+type+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int save() throws Exception {
		
		ModelDB db = new ModelDB();
		db.setId(this.getPageElement("sub_libraries_model_id").getValue());
		ModelDBDTO dbOld = ModeldbBS.getModelDB(db.getId());//记录日志使用
		
		List<Sublibrariescolumns> columns = new ArrayList<Sublibrariescolumns>();
		List<Sublibrariescondition> conditions = new ArrayList<Sublibrariescondition>();
		List<HashMap<String,Object>> columnsList = this.getPageElement("columnsdefine").getValueList();
		List<HashMap<String,Object>> conditionsList = this.getPageElement("conditionsdefine").getValueList();
		if((columnsList==null || columnsList.size()<1) || (conditionsList==null || conditionsList.size()<1)){
			throw new AppException("请选择查询列定义和查询条件定义后载点击【保存】！");
		}
		
		for(HashMap<String,Object> map : columnsList){
			if("true".equals(map.get("queryitem").toString())){
				SublibrariescolumnsId id = new SublibrariescolumnsId(db.getId(),map.get("information_set").toString(),map.get("information_set_field").toString());
				Integer no = null;
				try{no = Integer.parseInt(map.get("columns_no").toString());}catch(Exception e){}
				Sublibrariescolumns column = new Sublibrariescolumns(id,no);
				columns.add(column);
			}
			
		}
		for(HashMap<String,Object> map : conditionsList){
			Sublibrariescondition condition = new Sublibrariescondition();
			BeanUtil.copyHashMap(map, condition);
			condition.setSub_libraries_model_id(db.getId());
			conditions.add(condition);
		}
		db.setColumns(columns);
		db.setConditions(conditions);
		ModeldbBS.saveDB(db);
		
		//记录日志
		db.setModel(ModeldbBS.LoadSublibrariesmodel(db.getId()));
		ModeldbBS.compareChanges4Log(dbOld,db);
		this.setMainMessage("保存完成");
		this.reloadPageByYes();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("test.onclick")
	@NoRequiredValidate
	public int test() throws RadowException {
		String sql = this.getPageElement("sqltext").getValue();
		String ora_err = ModeldbBS.testSQL(sql);
		this.getPageElement("ora_err").setValue(ora_err);
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) { 
		  // TODO Auto-generated method stub 
		 
		  Calendar c = Calendar.getInstance(); 
		  long a = c.getTimeInMillis(); 
		  CommonQueryBS.systemOut(a+""); 
		  CommonQueryBS.systemOut(System.currentTimeMillis()+""); 
		 } 
}
